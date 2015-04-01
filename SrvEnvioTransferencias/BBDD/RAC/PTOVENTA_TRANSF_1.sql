--------------------------------------------------------
--  DDL for Package Body PTOVENTA_TRANSF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_TRANSF" is

PROCEDURE EJECT_TRANSFERENCIAS(cCodGrupoCia_in IN CHAR,ctipo in char default 'N',ccod_local_ini_trans in char default '300' ,ccod_local_fin_trans in char default '599' )
  --N = normal
  --D = Delivery
  --cod_motivo_interno_trans = 99 lgt_nota_es_cab
  --tip_origen_nota_es=01
  --TIP_MOT_NOTA_ES =205
  AS
    CURSOR curLocales IS
    SELECT COD_LOCAL, IP_SERVIDOR_LOCAL
    FROM PBL_LOCAL P
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND EST_LOCAL = 'A'
          AND TIP_LOCAL = 'V'
          and P.IND_EN_LINEA='S'
--          and p.cod_local in ('022','030')
          and p.cod_local between '300' and '599'
          and p.cod_local<>'160' --local venta mayorista
          and p.est_config_local in ('2','3')
          and p.cod_local between ccod_local_ini_trans and ccod_local_fin_trans --agregado 20100507
    ORDER BY 1;
--destino
    CURSOR curLocales_V2 IS
    SELECT DISTINCT p.COD_LOCAL ,
           t.cod_origen_nota_es cod_origen_nota_es, p.ip_servidor_local
    FROM PBL_LOCAL P,
         t_lgt_nota_es_cab t,
         pbl_local p1
    WHERE p.COD_GRUPO_CIA = cCodGrupoCia_in
          AND p.EST_LOCAL = 'A'
          AND p.TIP_LOCAL = 'V'
          and P.IND_EN_LINEA='S'
          AND p1.EST_LOCAL = 'A'
          AND p1.TIP_LOCAL = 'V'
          and p1.ind_en_linea='S'
          and p.cod_local<>'160' --local venta mayorista
          and p.cod_local between '300' and '599'
          and p1.cod_local=trim(t.cod_origen_nota_es)
          and p.est_config_local in ('2','3')
          AND T.EST_NOTA_ES_CAB='M'
          AND trim(T.Cod_Destino_Nota_Es)=P.COD_LOCAL
          and p.cod_local between ccod_local_ini_trans and ccod_local_fin_trans --agregado 20140705 EMAQUERA                    
    ORDER BY 1;
    
    -- 08/07/2014 - EMAQUERA - Agregado 
    V_SQL VARCHAR2(32767);
    CANT NUMBER;
    curIndice FarmaCursor;
    v_CodGrupoCia CHAR(3); 
    v_Cod_Local CHAR(3);
  BEGIN
    IF ctipo='N' then
        dbms_output.put_line('PROCESO_NORMAL_DELIVERY');
         -- Agregado para reiniciar el indice de transferencias
        INSERT INTO HIST_INDICE_TRANSF 
        SELECT * FROM INDICE_TRANSF WHERE COD_LOCAL BETWEEN ccod_local_ini_trans AND ccod_local_fin_trans;
        COMMIT;
        DELETE FROM INDICE_TRANSF WHERE COD_LOCAL BETWEEN ccod_local_ini_trans AND ccod_local_fin_trans;
        FOR v_rCurLocales IN curLocales
        ----- 08/07/2014 -EMAQUERA- Nuevo Proceso de Transferencia
        LOOP
         BEGIN
           IF CONN_LOCAL.PING(v_rCurLocales.Ip_Servidor_Local,'1521') = 'S' THEN      
             V_SQL:='SELECT COUNT(*) FROM T_LGT_NOTA_ES_CAB@XE_'||v_rCurLocales.COD_LOCAL;
             V_SQL:=V_SQL||' WHERE EST_NOTA_ES_CAB=''C''';
             V_SQL:=V_SQL||' AND FEC_NOTA_ES_CAB>SYSDATE-3';        
            
             EXECUTE IMMEDIATE V_SQL INTO CANT;
                 
             IF CANT>0 THEN
             INSERT INTO INDICE_TRANSF VALUES (v_rCurLocales.COD_LOCAL); 
             DBMS_OUTPUT.PUT_LINE('EL LOCAL '||v_rCurLocales.COD_LOCAL||' CUENTA CON '||CANT);
             COMMIT;
             END IF;
           ELSE
            dbms_output.put_line('Problema de Conexion => LOCAL - ' ||v_rCurLocales.COD_LOCAL);
           END IF;               
         EXCEPTION        
         WHEN OTHERS THEN              
         ROLLBACK;                                   
          DBMS_OUTPUT.PUT_LINE('ERROR EN EL LOCAL '||v_rCurLocales.COD_LOCAL||' : '||SQLCODE||' -ERROR- '||SQLERRM);          
         END;
         END LOOP;
         
         V_SQL:='SELECT B.COD_GRUPO_CIA, A.COD_LOCAL FROM INDICE_TRANSF A, PBL_LOCAL B
                 WHERE  A.COD_LOCAL=B.COD_LOCAL
                 AND A.COD_LOCAL<''600''
                 AND A.COD_LOCAL BETWEEN :1 AND :2';
         
         OPEN curIndice FOR V_SQL USING ccod_local_ini_trans,ccod_local_fin_trans;
         LOOP
              FETCH curIndice INTO v_CodGrupoCia, v_Cod_Local;
              EXIT WHEN curIndice%NOTFOUND;
              
              GET_ORIGEN_TRANSFERENCIA(v_CodGrupoCia,v_Cod_Local);
              dbms_output.put_line('origen2: '||v_Cod_Local);              
              
         END LOOP;
        -----
		--ERIOS 01.08.2014 El envio lo realiza el demonio de transferencias
        /*FOR v_rCurLocales_V2 IN curLocales_V2
        LOOP
         IF CONN_LOCAL.PING(v_rCurLocales_V2.Ip_Servidor_Local,'1521') = 'S' THEN      
          SET_DESTINO_TRANSFERENCIA(cCodGrupoCia_in,v_rCurLocales_V2.COD_LOCAL,null);
          dbms_output.put_line('destino1: '||v_rCurLocales_V2.COD_LOCAL);
         ELSE
          dbms_output.put_line('Problema de Conexion => ' || to_char(sysdate,'DD/MM/YYYY HH24:MI:SS') || ' LOCAL DESTINO - ' ||v_rCurLocales_V2.COD_LOCAL);
         END IF;                   
        END LOOP;*/
    ELSE
		null;
     END IF;
  END;
  /****************************************************************************/
  PROCEDURE GET_ORIGEN_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
  BEGIN
    BEGIN
  --INSERT SELECT 'P'
  --COD_GRUPO_CIA, COD_LOCAL, NUM_NOTA_ES

    --CAB
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
    FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
    CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
    RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS ,
    TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
    USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	,
    IND_NOTA_IMPRESA,FEC_PROCESO_SAP)
    SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
    FEC_NOTA_ES_CAB,''M'',TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
    CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
    RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
    TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
    USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	,
    IND_NOTA_IMPRESA,FEC_PROCESO_SAP
    FROM T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' C
     WHERE COD_GRUPO_CIA = :1
          AND COD_LOCAL = :2
          and fec_nota_es_cab>=trunc(sysdate-5)
          AND EST_NOTA_ES_CAB = :3 ' USING cCodGrupoCia_in,cCodLocal_in,'C';
--          AND NOT((SELECT COD_MOTIVO_INTERNO_TRANS FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND TIP_ORIGEN_NOTA_ES=''01'' AND TIP_MOT_NOTA_ES=''205'')

    --GUIA
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_GUIA_REM(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
    NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
    EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA)
    SELECT G.COD_GRUPO_CIA,G.COD_LOCAL,G.NUM_NOTA_ES,G.SEC_GUIA_REM,
    G.NUM_GUIA_REM,G.FEC_CREA_GUIA_REM,G.USU_CREA_GUIA_REM,G.FEC_MOD_GUIA_REM,G.USU_MOD_GUIA_REM,
    G.EST_GUIA_REM,G.NUM_ENTREGA,G.IND_GUIA_CERRADA,G.IND_GUIA_IMPRESA
    FROM T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' C,
          T_LGT_GUIA_REM@XE_'||cCodLocal_in||' G
    WHERE C.COD_GRUPO_CIA = :1
          AND C.COD_LOCAL = :2
          AND C.EST_NOTA_ES_CAB = :3
          and fec_nota_es_cab>=trunc(sysdate-5)
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'C';
--          AND NOT((SELECT COD_MOTIVO_INTERNO_TRANS FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND C.TIP_ORIGEN_NOTA_ES=''01'' AND C.TIP_MOT_NOTA_ES=''205'')

    --DET
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
    COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET,
    FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR,
    NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET,
    FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA)
    SELECT D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_NOTA_ES,D.SEC_DET_NOTA_ES,
    D.COD_PROD,D.SEC_GUIA_REM,D.VAL_PREC_UNIT,D.VAL_PREC_TOTAL,D.CANT_MOV,D.VAL_FRAC,D.EST_NOTA_ES_DET,
    D.FEC_NOTA_ES_DET,D.DESC_UNID_VTA,D.FEC_VCTO_PROD,D.NUM_LOTE_PROD,D.CANT_ENVIADA_MATR,
    D.NUM_PAG_RECEP,D.IND_PROD_AFEC,D.USU_CREA_NOTA_ES_DET,D.FEC_CREA_NOTA_ES_DET,D.USU_MOD_NOTA_ES_DET,
    D.FEC_MOD_NOTA_ES_DET,D.FEC_PROCESO_SAP,D.POSICION,D.NUM_ENTREGA
    FROM T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' C,
         T_LGT_NOTA_ES_DET@XE_'||cCodLocal_in||' D
    WHERE C.COD_GRUPO_CIA = :1
          AND C.COD_LOCAL = :2
          AND C.EST_NOTA_ES_CAB = :3
          and fec_nota_es_det>=trunc(sysdate-5)
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'C';
--          AND NOT((SELECT COD_MOTIVO_INTERNO_TRANS FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND C.TIP_ORIGEN_NOTA_ES=''01'' AND C.TIP_MOT_NOTA_ES=''205'')
    --ACTUALIZA ESTADO EN LOCAL ORIGEN
    --EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_EST_TRANSF_ORIGEN@XE_'||cCodLocal_in||'(:1,:2); END;' USING cCodGrupoCia_in,cCodLocal_in;
	
    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_LOCAL = :2
                            AND EST_NOTA_ES_CAB = :3';
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in,'M';
    LOOP
      FETCH curTransf INTO v_cCodLocal,v_cNumNota,v_cEstNota;
      EXIT WHEN curTransf%NOTFOUND;
      --EN MATRIZ
      --ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in, v_cCodLocal,v_cNumNota,v_cEstNota);
      --EN LOCAL
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_TRANSF_ORIGINAL@XE_'||v_cCodLocal||'(:1,:2,:3,:4); END;'
                          USING cCodGrupoCia_in,v_cCodLocal,v_cNumNota,v_cEstNota;
    END LOOP;

	--ERIOS 01.08.2014 Graba registro de transferencias
	PKG_DAEMON_TRANSF.GRABA_REG_TRANSFERENCIA(cCodGrupoCia_in,NULL,cCodLocal_in,NULL);
	
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('TERMINO EL PROCESO DE TRAER TRANSFERENCIAS DE LOCAL: '||cCodLocal_in);
    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE TRAER TRANSFERENCIAS DE LOCAL: '||cCodLocal_in);
      DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
      INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocal_in,
                              'ERROR EN TRAER TRANSFERENCIAS DE LOCAL: ',
                              'ALERTA',
                              SUBSTR(SQLERRM, 1, 250));
    END;

    BEGIN
  --CURSOR 'A'
 

    --ROLLBACK;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('TERMINO EL PROCESO DE ACTUALIZAR TRANSFERENCIAS DE LOCAL ORIGEN: '||cCodLocal_in);
    EXCEPTION
      WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE ACTUALIZAR TRANSFERENCIAS DE LOCAL ORIGEN: '||cCodLocal_in);
        DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
        INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocal_in,
                                'ERROR EN ACTUALIZAR TRANSFERENCIAS DE LOCAL ORIGEN: ',
                                'ALERTA',
                                SUBSTR(SQLERRM, 1, 250));
        --RAISE;
     END;
  END;

  /****************************************************************************/
  PROCEDURE BORRA_TRANSF_TEMPORAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR)
  AS
  BEGIN
    --GUIA
    DELETE FROM T_LGT_GUIA_REM
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;
    --DET
    DELETE FROM T_LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;
    --CAB
    DELETE FROM T_LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;
  END;

  /****************************************************************************/
  PROCEDURE ACTUALIZA_EST_TRANSF_ORIGEN(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
  BEGIN
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'SISTEMAS',FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = 'M'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND EST_NOTA_ES_CAB = 'C';

    UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'SISTEMAS',FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = 'M'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND EST_NOTA_ES_CAB = 'C';
  END;

  /****************************************************************************/
  PROCEDURE SET_DESTINO_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodLocal_origen IN CHAR)
  AS
    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
  BEGIN
    /*BEGIN*/
	--ERIOS 01.08.2014 El envio lo realiza el demonio de transferencias
  /*--INSERT SELECT 'P' EN LOCAL
    --CAB
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
    FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
    CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
    RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
    TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
    USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	,
    IND_NOTA_IMPRESA,FEC_PROCESO_SAP)
    SELECT distinct COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
    FEC_NOTA_ES_CAB,''L'',TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
    CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
    RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
    TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
    USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	,
    IND_NOTA_IMPRESA,FEC_PROCESO_SAP
    FROM T_LGT_NOTA_ES_CAB C
    WHERE COD_GRUPO_CIA = :1
          AND COD_DESTINO_NOTA_ES = :2
          AND EST_NOTA_ES_CAB = :3 ' USING cCodGrupoCia_in,cCodLocal_in,'M';*/
  /*        AND NOT((SELECT e.COD_MOTIVO_INTERNO_TRANS
                   FROM   LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||' e
                   WHERE  e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
                     AND  e.COD_LOCAL=C.COD_LOCAL
                     AND  e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99''
                  AND TIP_ORIGEN_NOTA_ES=''01''
                  AND TIP_MOT_NOTA_ES=''205'')         */

    --DET
    /*EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_DET@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
    COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET,
    FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR,
    NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET,
    FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA)
    SELECT distinct D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_NOTA_ES,D.SEC_DET_NOTA_ES,
    D.COD_PROD,D.SEC_GUIA_REM,D.VAL_PREC_UNIT,D.VAL_PREC_TOTAL,D.CANT_MOV,D.VAL_FRAC,D.EST_NOTA_ES_DET,
    D.FEC_NOTA_ES_DET,D.DESC_UNID_VTA,D.FEC_VCTO_PROD,D.NUM_LOTE_PROD,D.CANT_ENVIADA_MATR,
    D.NUM_PAG_RECEP,D.IND_PROD_AFEC,D.USU_CREA_NOTA_ES_DET,D.FEC_CREA_NOTA_ES_DET,D.USU_MOD_NOTA_ES_DET,
    D.FEC_MOD_NOTA_ES_DET,D.FEC_PROCESO_SAP,D.POSICION,D.NUM_ENTREGA
    FROM T_LGT_NOTA_ES_CAB C,
          T_LGT_NOTA_ES_DET D
    WHERE C.COD_GRUPO_CIA = :1
          AND C.COD_DESTINO_NOTA_ES = :2
          AND C.EST_NOTA_ES_CAB = :3
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M';*/
 /*          AND NOT((SELECT e.COD_MOTIVO_INTERNO_TRANS
                   FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||' e
                   WHERE e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
                   AND   e.COD_LOCAL=C.COD_LOCAL
                   AND   e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99''
              AND C.TIP_ORIGEN_NOTA_ES=''01''
              AND C.TIP_MOT_NOTA_ES=''205'')
 */
    --GUIA
    /*EXECUTE IMMEDIATE 'INSERT INTO T_LGT_GUIA_REM@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
    NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
    EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA)
    SELECT distinct G.COD_GRUPO_CIA,G.COD_LOCAL,G.NUM_NOTA_ES,G.SEC_GUIA_REM,
    G.NUM_GUIA_REM,G.FEC_CREA_GUIA_REM,G.USU_CREA_GUIA_REM,G.FEC_MOD_GUIA_REM,G.USU_MOD_GUIA_REM,
    G.EST_GUIA_REM,G.NUM_ENTREGA,G.IND_GUIA_CERRADA,G.IND_GUIA_IMPRESA
    FROM T_LGT_NOTA_ES_CAB C,
          T_LGT_GUIA_REM G
    WHERE C.COD_GRUPO_CIA = :1
          AND C.COD_DESTINO_NOTA_ES = :2
          AND C.EST_NOTA_ES_CAB = :3
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M';*/
 /*
          AND NOT((SELECT COD_MOTIVO_INTERNO_TRANS
                   FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||' e
                   WHERE e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
                   AND   e.COD_LOCAL=C.COD_LOCAL
                   AND   e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99''
                AND   C.TIP_ORIGEN_NOTA_ES=''01''
                AND   C.TIP_MOT_NOTA_ES=''205'' )
 */
    --ACTUALIZA ESTADO
    /*dbms_output.PUT_LINE('borrar_el_output.. envio_trans_normal');
    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_DESTINO_NOTA_ES = :2
                            AND COD_LOCAL<''600''
                            AND EST_NOTA_ES_CAB = :3';
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in,'M';
    LOOP
      FETCH curTransf INTO v_cCodLocal,v_cNumNota,v_cEstNota;
      EXIT WHEN curTransf%NOTFOUND;
      --EN MATRIZ
      ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in, v_cCodLocal,v_cNumNota,v_cEstNota);
      --EN LOCAL
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_TRANSF_ORIGINAL@XE_'||v_cCodLocal||'(:1,:2,:3,:4); END;'
                          USING cCodGrupoCia_in,v_cCodLocal,v_cNumNota,v_cEstNota;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('TERMINO EL PROCESO DE ENVIAR TRANSFERENCIAS DE LOCAL: '||cCodLocal_in);
    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE ENVIAR TRANSFERENCIAS DE LOCAL: '||cCodLocal_in);
      DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
      INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocal_in,
                              'ERROR EN ENVIAR TRANSFERENCIAS DE LOCAL: ',
                              'ALERTA',
                              SUBSTR(SQLERRM, 1, 250));
    END;*/

    BEGIN
  --GET TRANSF 'A'
    v_vSentencia := 'SELECT NUM_NOTA_ES,EST_NOTA_ES_CAB
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_LOCAL = :2
                            AND COD_LOCAL<''600''
                            AND EST_NOTA_ES_CAB IN (''A'',''X'',''R'')';
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in;
    LOOP
      FETCH curTransf INTO v_cNumNota,v_cEstNota;
      EXIT WHEN curTransf%NOTFOUND;
      DBMS_OUTPUT.PUT_LINE('entro a borrar');
      --ACTUALIZA TRANSF ORIGINAL
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_TRANSF_ORIGINAL@XE_'||cCodLocal_in||'(:1,:2,:3,:4); END;'
                          USING cCodGrupoCia_in,cCodLocal_in,v_cNumNota,v_cEstNota;
      --ELIMINA TRANSF LOCAL
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.BORRA_TRANSF_TEMPORAL@XE_'||cCodLocal_in||'(:1,:2,:3); END;'
                          USING cCodGrupoCia_in,cCodLocal_in,v_cNumNota;
      --ELIMINA TRANSF MATRIZ
      BORRA_TRANSF_TEMPORAL(cCodGrupoCia_in,cCodLocal_in,v_cNumNota);
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('TERMINO EL PROCESO DE ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: '||cCodLocal_in);
    EXCEPTION
      WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: '||cCodLocal_in);
        DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
        INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocal_in,
                                'ERROR EN ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: ',
                                'ALERTA',
                                SUBSTR(SQLERRM, 1, 250));
        --RAISE;
    END;
  END;

  /****************************************************************************/
  PROCEDURE ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cEstNotaEs_in IN CHAR)
  AS
  BEGIN
    --ACTUALIZA TRANSF
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_M_FASA',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;

    UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_M_FASA',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;
    COMMIT;      
  END;

  /****************************************************************************/
  FUNCTION LISTA_TRANSF_LOCAL(cGrupoCia_in IN CHAR, cCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN FarmaCursor
  IS
    curTransf FarmaCursor;
  BEGIN
    OPEN curTransf FOR
    SELECT C.NUM_NOTA_ES || 'Ã' ||
          NVL(PTOVENTA_INV.INV_GET_DESTINO_TRANSFERENCIA(TIP_ORIGEN_NOTA_ES,COD_ORIGEN_NOTA_ES,cGrupoCia_in,cCia_in),' ') || 'Ã' ||
          NVL(G.NUM_GUIA_REM,'ERROR') || 'Ã' ||
          TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy HH24:MI:SS') || 'Ã' ||
          C.CANT_ITEMS || 'Ã' ||
          C.COD_ORIGEN_NOTA_ES
    FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM g
    WHERE C.COD_GRUPO_CIA = cGrupoCia_in
          AND C.COD_DESTINO_NOTA_ES = cCodLocal_in
          AND C.EST_NOTA_ES_CAB = 'L'
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES;
    RETURN curTransf;
  END;

  /****************************************************************************/
  FUNCTION GET_CAB_TRANSF_LOCAL(cCodGrupoCia_in IN CHAR,cCia_in IN CHAR,
                                cCodLocal_in IN CHAR,cNumNota_in IN CHAR,
                                cCodLocalOrigen_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCab FarmaCursor;
  BEGIN
    OPEN curCab FOR
    SELECT TO_CHAR(C.FEC_NOTA_ES_CAB,'dd/MM/yyyy') || 'Ã' ||
          O.DESC_CORTA || 'Ã' ||
          PTOVENTA_INV.INV_GET_DESTINO_TRANSFERENCIA(C.TIP_ORIGEN_NOTA_ES,C.COD_ORIGEN_NOTA_ES,C.COD_GRUPO_CIA,cCia_in)
    FROM T_LGT_NOTA_ES_CAB C, PBL_TAB_GRAL O
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocalOrigen_in
          AND C.COD_DESTINO_NOTA_ES = cCodLocal_in
          AND C.NUM_NOTA_ES = cNumNota_in
          AND O.COD_APL = PTOVENTA_INV.g_vCodPtoVenta
          AND O.COD_TAB_GRAL = PTOVENTA_INV.g_vGralDestinoNotaEs
          AND O.LLAVE_TAB_GRAL = C.TIP_ORIGEN_NOTA_ES;
    RETURN curCab;
  END;

  /****************************************************************************/
  FUNCTION GET_DET_TRANSF_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                cNumNota_in IN CHAR,cCodLocalOrigen_in IN CHAR)
  RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
    SELECT D.COD_PROD || 'Ã' ||
          P.DESC_PROD || 'Ã' ||
          NVL(D.DESC_UNID_VTA,' ')	 || 'Ã' ||
          B.NOM_LAB || 'Ã' ||
          D.CANT_MOV || 'Ã' ||
          TO_CHAR(D.VAL_PREC_UNIT,'999,990.000') || 'Ã' ||
          NVL(TO_CHAR(D.FEC_VCTO_PROD,'dd/MM/yyyy'),' ')
    FROM T_LGT_NOTA_ES_CAB C,T_LGT_NOTA_ES_DET D, LGT_PROD P, LGT_PROD_LOCAL L, LGT_LAB B
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocalOrigen_in
          AND C.NUM_NOTA_ES = cNumNota_in
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES
          AND D.COD_GRUPO_CIA = L.COD_GRUPO_CIA
          AND C.COD_DESTINO_NOTA_ES = L.COD_LOCAL
          AND D.COD_PROD = L.COD_PROD
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD
          AND P.COD_LAB = B.COD_LAB;

    RETURN curDet;
  END;

  /****************************************************************************/
  PROCEDURE GENERAR_GUIA_INGRESO_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                cCodLocalOrigen_in IN CHAR,cNumNota_in IN CHAR,
                                vIdUsu_in IN CHAR)
  AS
    CURSOR curGuias IS
    SELECT C.FEC_NOTA_ES_CAB,C.TIP_DOC,G.NUM_GUIA_REM,C.TIP_ORIGEN_NOTA_ES,C.COD_LOCAL,C.CANT_ITEMS,C.VAL_TOTAL_NOTA_ES_CAB, G.SEC_GUIA_REM
    FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM G
    WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
          AND C.COD_LOCAL = cCodLocalOrigen_in
          AND C.COD_DESTINO_NOTA_ES = cCodLocal_in
          AND C.NUM_NOTA_ES = cNumNota_in
          AND C.EST_NOTA_ES_CAB = 'L'
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES;
    v_rCurGuias curGuias%ROWTYPE;

    v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;

    CURSOR curDet(nSecGuia_in IN NUMBER) IS
    SELECT COD_PROD,CANT_MOV,VAL_FRAC,VAL_PREC_UNIT,VAL_PREC_TOTAL,FEC_VCTO_PROD,NUM_LOTE_PROD
    FROM T_LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocalOrigen_in
          AND NUM_NOTA_ES = cNumNota_in
          AND SEC_GUIA_REM = nSecGuia_in;
    v_rCurDet curDet%ROWTYPE;

    v_nValFrac LGT_NOTA_ES_DET.VAL_FRAC%TYPE;

    v_eControlFraccion EXCEPTION;

    v_nCant LGT_NOTA_ES_DET.CANT_MOV%TYPE;
  BEGIN
    FOR v_rCurGuias IN curGuias
    LOOP
      --INSERTAR CABECERA
      v_nNumNota := PTOVENTA_INV.INV_AGREGA_CAB_GUIA_INGRESO(cCodGrupoCia_in,cCodLocal_in,
  				    TO_CHAR(v_rCurGuias.FEC_NOTA_ES_CAB,'dd/MM/yyyy'),
                                    v_rCurGuias.TIP_DOC,
                                    v_rCurGuias.NUM_GUIA_REM,
                                    v_rCurGuias.TIP_ORIGEN_NOTA_ES,
                                    v_rCurGuias.COD_LOCAL,
                                    v_rCurGuias.CANT_ITEMS,
                                    v_rCurGuias.VAL_TOTAL_NOTA_ES_CAB,
  				    '','','',
                                    vIdUsu_in);
      DBMS_OUTPUT.PUT_LINE('PEDIDO GENERADO:'||v_nNumNota);
      FOR v_rCurDet IN curDet(v_rCurGuias.SEC_GUIA_REM)
      LOOP
        --VALIDA FRACCION LOCAL Y TRASNF PROD
        SELECT VAL_FRAC_LOCAL INTO v_nValFrac
  	FROM LGT_PROD_LOCAL
  	WHERE COD_GRUPO_CIA = cCodGrupoCia_in
  	      AND COD_LOCAL = cCodLocal_in
  	      AND COD_PROD = v_rCurDet.COD_PROD;

        IF MOD(v_rCurDet.CANT_MOV*v_nValFrac,v_rCurDet.VAL_FRAC) = 0 THEN
          v_nCant := ((v_rCurDet.CANT_MOV*v_nValFrac)/v_rCurDet.VAL_FRAC);
        ELSE
          RAISE v_eControlFraccion;
        END IF;
        --INSERTAR PRODUCTOS POR GUIA
        PTOVENTA_INV.INV_AGREGA_DET_GUIA_INGRESO(cCodGrupoCia_in,cCodLocal_in,v_nNumNota,v_rCurGuias.TIP_ORIGEN_NOTA_ES,
  				    v_rCurDet.COD_PROD, v_rCurDet.VAL_PREC_UNIT, v_rCurDet.VAL_PREC_TOTAL, v_nCant,
                                    TO_CHAR(v_rCurGuias.FEC_NOTA_ES_CAB,'dd/MM/yyyy'), TO_CHAR(v_rCurDet.FEC_VCTO_PROD,'dd/MM/yyyy'),
                                    v_rCurDet.NUM_LOTE_PROD,
                                    '104','02',v_nValFrac,vIdUsu_in);
      END LOOP;

    END LOOP;

    --TRANSFERENCIA ACEPTADA
    ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in, cCodLocalOrigen_in,cNumNota_in,'A');
    COMMIT;
  EXCEPTION
    WHEN v_eControlFraccion THEN
      ROLLBACK;
      --TRANSFERENCIA ERROR
      ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in, cCodLocalOrigen_in,cNumNota_in,'X');
      COMMIT;
      RAISE_APPLICATION_ERROR(-20081,'ALGUNOS PRODUCTOS NO PUEDEN SER INGRESADOS, DEBIDO A LA FRACCION ACTUAL DEL LOCAL.');

      INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocal_in,
                             'ERROR EN ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: ',
                             'ALERTA',
                             'ALGUNOS PRODUCTOS NO PUEDEN SER INGRESADOS, DEBIDO A LA FRACCION ACTUAL DEL LOCAL.' || SUBSTR(SQLERRM, 1, 250));

  END;

  /****************************************************************************/
  PROCEDURE INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_TRANSF;
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(32767);

    v_vDescLocal VARCHAR2(120);
    v_vDescLocalDestino VARCHAR2(120);
  BEGIN
    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = '009';

    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocalDestino
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in ||
                          '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                          ReceiverAddress,
                          vAsunto_in||v_vDescLocalDestino,--'VIAJERO EXITOSO: '||v_vDescLocal,
                          vTitulo_in,--'EXITO',
                          mesg_body,
                          CCReceiverAddress,
                          FARMA_EMAIL.GET_EMAIL_SERVER,
                          true);

  END;

  /****************************************************************************/

  PROCEDURE GET_FRACCION_LOCAL(cCodGrupoCia_in 	   IN CHAR,
                              cCodLocal_in    	   IN CHAR,
                              cCodProd_in   IN CHAR,
                              nCantMov_in IN NUMBER,
                              nValFrac_in IN NUMBER,
                              cInd_out IN OUT CHAR)
  AS
    v_cInd CHAR(1) := 'X';
    v_nValFracLocDestino LGT_PROD_LOCAL.VAL_FRAC_LOCAL%TYPE;
  BEGIN
   begin
    SELECT VAL_FRAC_LOCAL
      INTO v_nValFracLocDestino
    FROM delivery.LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;
   exception
   when no_data_found then
   null;
      insert into delivery.LGT_PROD_LOCAL
      (cod_grupo_cia, cod_local, cod_prod, stk_fisico, stk_comprometido, unid_vta,
            porc_dcto_1, porc_dcto_2, porc_dcto_3, est_prod_loc, ind_prod_cong, usu_crea_prod_local,
            fec_crea_prod_local, usu_mod_prod_local, fec_mod_prod_local, val_frac_local, ind_prod_fraccionado,
            val_prec_vta, val_prec_lista, cant_exhib, ind_reponer, ind_prod_habil_vta)
      select cod_grupo_cia, cCodLocal_in, cod_prod, 0, stk_comprometido, unid_vta,
            porc_dcto_1, porc_dcto_2, porc_dcto_3, est_prod_loc, ind_prod_cong, usu_crea_prod_local,
            fec_crea_prod_local, usu_mod_prod_local, fec_mod_prod_local, val_frac_local, ind_prod_fraccionado,
            val_prec_vta, val_prec_lista, cant_exhib, ind_reponer, ind_prod_habil_vta
      from   delivery.LGT_PROD_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_PROD = cCodProd_in
        and rownum = 1;
        commit;
    SELECT VAL_FRAC_LOCAL
      INTO v_nValFracLocDestino
    FROM delivery.LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;
   end;


    IF MOD(nCantMov_in*v_nValFracLocDestino,nValFrac_in) = 0 THEN
      v_cInd := 'V';
    END IF;

    cInd_out := v_cInd;
  END;
/***************************************/

  /****************************************************************************/

/****************************************************************************/

end PTOVENTA_TRANSF;

/
