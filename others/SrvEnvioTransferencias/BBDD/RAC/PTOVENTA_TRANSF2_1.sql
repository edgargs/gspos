--------------------------------------------------------
--  DDL for Package Body PTOVENTA_TRANSF2
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_TRANSF2" is

  PROCEDURE EJECT_TRANSFERENCIAS(cCodGrupoCia_in IN CHAR,ctipo in char default 'N',ccod_local_ini_trans in char default '600' ,ccod_local_fin_trans in char default '999' )
  AS
v_indProceso CHAR(1);
msg VARCHAR2(32767);
    --TRAE DE LOCALES
    CURSOR curLocales IS
/*    SELECT F.COD_LOCAL_SAP COD_LOCAL --INTO v_existe
      FROM NUEVO.AUX_MAE_LOCAL F
     WHERE cod_local_SAP >= '600'
     ORDER BY 1;*/
     -- REEMPLAZO EMAQUERA 09.07.2014
    SELECT COD_LOCAL, IP_SERVIDOR_LOCAL
    FROM PBL_LOCAL P
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND EST_LOCAL = 'A'
          AND TIP_LOCAL = 'V'
          and P.IND_EN_LINEA='S'
          and p.cod_local in (
            SELECT F.COD_LOCAL_SAP
            FROM NUEVO.AUX_MAE_LOCAL F
            WHERE cod_local_SAP >= '600'
            AND FCH_MIGRACION IS NOT NULL
            AND FCH_MIGRACION <= trunc(sysdate)
          )
          and p.cod_local<>'160' --local venta mayorista
          and p.est_config_local in ('2','3')
          and p.cod_local between ccod_local_ini_trans and ccod_local_fin_trans
    ORDER BY 1;
     -- REMPLAZO EMAQUERA 09.07.2014

 -- ENVIA A LOCALES
    CURSOR curLocales_V2 IS
    SELECT DISTINCT p.COD_LOCAL ,
           t.cod_origen_nota_es cod_origen_nota_es, t.num_nota_es
    FROM PBL_LOCAL P,
         t_lgt_nota_es_cab t,
         pbl_local p1
    WHERE p.COD_GRUPO_CIA = cCodGrupoCia_in
/*          AND p.EST_LOCAL = 'A'
          AND p.TIP_LOCAL = 'V'
          and P.IND_EN_LINEA='S'
          AND p1.EST_LOCAL = 'A'
          AND p1.TIP_LOCAL = 'V'
          and p1.ind_en_linea='S'       */
          and p.cod_local between '600' and '999'
          and p1.cod_local=t.cod_origen_nota_es
--          and p.est_config_local in ('2','3')
          AND T.EST_NOTA_ES_CAB='M'
          AND T.Cod_Destino_Nota_Es=P.COD_LOCAL
          and p.cod_local between ccod_local_ini_trans and ccod_local_fin_trans --agregado 20140705 EMAQUERA                    
    ORDER BY 1; 

/*CURSOR curLocales_V3 IS
        select distinct tt.Cod_Origen_Nota_Es "COD_ORIGEN",
                        mm.IP_SERVIDOR_LOCAL,
                        mm.IND_MIGRADO
        from   T_LGT_NOTA_ES_CAB tt,
               (
                SELECT MP.COD_LOCAL_SAP,PP.IP_SERVIDOR_LOCAL,
                CASE
                WHEN NVL(MP.FCH_MIGRACION,SYSDATE+10)<= TRUNC(SYSDATE) THEN 'S'
                ELSE 'N'
                END IND_MIGRADO
                FROM   NUEVO.AUX_MAE_LOCAL MP,
                APPS.PBL_LOCAL@XE_999 PP
                WHERE  PP.COD_LOCAL = MP.COD_LOCAL_SAP
                AND    PP.COD_CIA = '003'
               ) MM
        where  tt.cod_grupo_cia = '001'
        and    tt.est_nota_es_cab = 'E'
        and    tt.Cod_Origen_Nota_Es = mm.cod_local_sap
        order by mm.IND_MIGRADO desc,tt.cod_destino_nota_es asc;*/


    v_rCurLocales  curLocales%ROWTYPE;
    v_rCurLocales_V2  curLocales_V2%ROWTYPE;
--    v_rCurLocales_V3  curLocales_V3%ROWTYPE;

    -- 09/07/2014 - EMAQUERA - Nuevas Variables a usar
    V_SQL VARCHAR2(32767);
    CANT NUMBER;
    curIndice FarmaCursor;
    v_CodGrupoCia CHAR(3); 
    v_Cod_Local CHAR(3);
    result_envio char(1);
  BEGIN
    ----
/*    SELECT IND_PROCESO
          INTO v_indProceso
        FROM AUX_IND_EJEC_PROC
        WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS';

        IF v_indProceso = 'S' THEN
                  msg := substr(SQLERRM,1,100);
          farma_email.envia_correo('oracle@mifarma.com.pe','jayala',cSubject_in => 'ERROR TRANSFERENCIAS LOCALES BTL-FV: EL PROCEDIMIENTO SE ENCUENTRA EN PROCESO',ctitulo_in => 'EN ESTE MOMENTO YA SE ENCUENTRA EN PROCESO EL PROCEDIMIENTO:' || 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS',cmensaje_in => msg,cip_servidor => FARMA_EMAIL.GET_EMAIL_SERVER,cin_html => TRUE);
          RAISE_APPLICATION_ERROR(-20001,'EN ESTE MOMENTO YA SE ENCUENTRA EN PROCESO EL PROCEDIMIENTO:' || 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS');
        ELSE
          UPDATE AUX_IND_EJEC_PROC
          SET FEC_INICIO = SYSDATE,
              FEC_FIN = NULL,
              IND_PROCESO = 'S',
              USU_PROCESO = USER,
              IP_PROCESO = SUBSTR(SYS_CONTEXT('USERENV','IP_ADDRESS'),1,50)
          WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS';
          COMMIT;
        END IF;*/
    ----
    IF ctipo='N' then
    
        dbms_output.put_line('PROCESO_NORMAL_DELIVERY BTL FARMAVENTA');
        
        ----- 08/07/2014 -EMAQUERA- Nuevo Proceso de Transferencia
        FOR v_rCurLocales IN curLocales
        LOOP
         BEGIN
           IF CONN_LOCAL.PING (v_rCurLocales.Ip_Servidor_Local,'1521') = 'S' THEN      
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
                 AND A.COD_LOCAL>''600''
                 AND A.COD_LOCAL BETWEEN :1 AND :2';
         
         OPEN curIndice FOR V_SQL USING ccod_local_ini_trans,ccod_local_fin_trans;
         LOOP
              FETCH curIndice INTO v_CodGrupoCia, v_Cod_Local;
              EXIT WHEN curIndice%NOTFOUND;
              
              GET_ORIGEN_TRANSFERENCIA(v_CodGrupoCia,v_Cod_Local);
              dbms_output.put_line('origen2: '||v_Cod_Local);              
              
         END LOOP;
        -----
        /* 30072014 - EMAQUERA -- PROCESO DE TRAER TRANSFERENCIA 
        FOR v_rCurLocales IN curLocales
        ----- 08/07/2014 -EMAQUERA- Nuevo Proceso de Transferencia
        LOOP
         BEGIN
           IF CONN_LOCAL.PING (v_rCurLocales.Ip_Servidor_Local,'1521') = 'S' THEN
               
              GET_ORIGEN_TRANSFERENCIA(cCodGrupoCia_in,v_rCurLocales.COD_LOCAL);
              dbms_output.put_line('origen: '||v_Cod_Local);     

           ELSE
            dbms_output.put_line('Problema de Conexion => LOCAL - ' ||v_rCurLocales.COD_LOCAL);
           END IF;               
         EXCEPTION        
         WHEN OTHERS THEN              
         ROLLBACK;                                   
          DBMS_OUTPUT.PUT_LINE('ERROR EN EL LOCAL '||v_rCurLocales.COD_LOCAL||' : '||SQLCODE||' -ERROR- '||SQLERRM);          
         END;
         END LOOP;
         */
        -----
        
        
       --ERIOS 01.08.2014 El envio lo realiza el demonio de transferencias
        /*FOR v_rCurLocales_V2 IN curLocales_V2
        LOOP
         BEGIN     
          result_envio := TRANSF_F_CHAR_LLEVAR_DESTINO2(cCodGrupoCia_in,
                                                              v_rCurLocales_V2.cod_origen_nota_es,
                                                              v_rCurLocales_V2.cod_local,
                                                              v_rCurLocales_V2.num_nota_es,
                                                              'JOB_BTL');                                                           
           
          dbms_output.put_line('origen: '||v_rCurLocales_V2.cod_origen_nota_es||' - destino : '||v_rCurLocales_V2.cod_local||' IndEnvio - '||result_envio);
         EXCEPTION        
         WHEN OTHERS THEN              
         ROLLBACK;                                   
          DBMS_OUTPUT.PUT_LINE('ERROR AL ENVIAR - EN EL LOCAL '||v_rCurLocales.COD_LOCAL||' : '||SQLCODE||' -ERROR- '||SQLERRM);
         END;
        END LOOP;*/
/*
        -- ACTUALIZA ESTADO ENVIADO EN LOCAL ORIGEN
        FOR v_rCurLocales_V3 IN curLocales_V3
        LOOP
          UPD_ORIGEN_TRANSFERENCIA(cCodGrupoCia_in,
                                     v_rCurLocales_V3.COD_ORIGEN,
                                     v_rCurLocales_V3.IP_SERVIDOR_LOCAL,
                                     v_rCurLocales_V3.IND_MIGRADO);
          dbms_output.put_line('destino1: '||v_rCurLocales_V3.COD_ORIGEN);
        END LOOP;*/

     END IF;
     ----
/*     UPDATE AUX_IND_EJEC_PROC
          SET FEC_FIN = SYSDATE,
              IND_PROCESO = 'N',
              USU_PROCESO = USER,
              IP_PROCESO = SUBSTR(SYS_CONTEXT('USERENV','IP_ADDRESS'),1,50)
          WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS';

          UPDATE AUX_IND_EJEC_PROC
          SET FEC_FIN = SYSDATE,
              IND_PROCESO = 'N'
          WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS';

          COMMIT;*/
     ----
  END;
  /* **************************************************************************/  
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
  --- ACTUALIZA A ESTADO "L" LOCAL ORIGEN -- **** EMAQUERA
    v_vSentencia := 'UPDATE T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||'
                      SET EST_NOTA_ES_CAB=''L''
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_LOCAL = :2
                            AND COD_LOCAL>''600''
                            AND EST_NOTA_ES_CAB=''C''' ; 
    EXECUTE IMMEDIATE v_vSentencia USING cCodGrupoCia_in,cCodLocal_in;
    
    v_vSentencia := 'UPDATE LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||'
                      SET EST_NOTA_ES_CAB=''L''
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_LOCAL = :2
                            AND COD_LOCAL>''600''
                            AND EST_NOTA_ES_CAB=''C''' ; 
    EXECUTE IMMEDIATE v_vSentencia USING cCodGrupoCia_in,cCodLocal_in;                                                 
  --- **** EMAQUERA
  
  
  
/* -EMAQUERA-01/08/2014--PROCESO QUE ACTUALIZA ESTADO DE AFECTADO
    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,EST_NOTA_ES_CAB
                      FROM T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||'
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_DESTINO_NOTA_ES = :2
                            AND COD_LOCAL>''600''
                            AND EST_NOTA_ES_CAB IN (''A'',''X'',''R'')' ;
    --DBMS_OUTPUT.PUT_LINE(v_vSentencia);
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in;
    LOOP
      FETCH curTransf INTO v_cCodLocal,v_cNumNota,v_cEstNota;
      EXIT WHEN curTransf%NOTFOUND;
      --ACTUALIZA MATRIZ
      UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_GET_BTL',FEC_MOD_NOTA_ES_CAB = SYSDATE,
            EST_NOTA_ES_CAB = v_cEstNota
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = v_cCodLocal
            AND COD_LOCAL > '600'
            AND NUM_NOTA_ES = v_cNumNota;
      --DELETE LOCAL ORIGEN
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.BORRA_TRANSF_TEMPORAL@XE_'||cCodLocal_in||'(:1,:2,:3); END;'
                          USING cCodGrupoCia_in,v_cCodLocal,v_cNumNota;
    END LOOP;
*/

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
  /* **************************************************************************/
  PROCEDURE SET_DESTINO_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodLocal_origen IN CHAR)
  AS
    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
  BEGIN
    BEGIN
  --INSERT SELECT 'P' EN LOCAL
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
          AND EST_NOTA_ES_CAB = :3 
          AND COD_ORIGEN_NOTA_ES = :4 ' USING cCodGrupoCia_in,cCodLocal_in,'M',cCodLocal_origen;
  /*        AND NOT((SELECT e.COD_MOTIVO_INTERNO_TRANS
                   FROM   LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||' e
                   WHERE  e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
                     AND  e.COD_LOCAL=C.COD_LOCAL
                     AND  e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99''
                  AND TIP_ORIGEN_NOTA_ES=''01''
                  AND TIP_MOT_NOTA_ES=''205'')         */

    --DET
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_DET@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
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
          AND C.COD_DESTINO_NOTA_ES = :4          
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M',cCodLocal_origen;
 /*          AND NOT((SELECT e.COD_MOTIVO_INTERNO_TRANS
                   FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||' e
                   WHERE e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
                   AND   e.COD_LOCAL=C.COD_LOCAL
                   AND   e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99''
              AND C.TIP_ORIGEN_NOTA_ES=''01''
              AND C.TIP_MOT_NOTA_ES=''205'')
 */
    --GUIA
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_GUIA_REM@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
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
          AND C.COD_ORIGEN_NOTA_ES = :4          
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M',cCodLocal_origen;
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
    dbms_output.PUT_LINE('borrar_el_output.. envio_trans_normal');
    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_DESTINO_NOTA_ES = :2
                            AND EST_NOTA_ES_CAB = :3
                            AND COD_ORIGEN_NOTA_ES = :4';
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in,'M',cCodLocal_origen;
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
    END;

    BEGIN
  --GET TRANSF 'A'
    v_vSentencia := 'SELECT NUM_NOTA_ES,EST_NOTA_ES_CAB
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_LOCAL = :2
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
  /* **************************************************************************/
PROCEDURE UPD_ORIGEN_TRANSFERENCIA(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cIpServLocal_in IN CHAR,
                                   cIndMigrado_in  IN CHAR)
  AS

  CURSOR cur_cab(codi_local IN CHAR) IS
 SELECT c.cod_origen_nota_es COD_ORIGEN,
        c.cod_destino_nota_es COD_DESTINO,
        C.NUM_NOTA_ES        NUMERO_NOTA,
        G.NUM_GUIA_REM       NUMERO_GUIA,
        C.FEC_NOTA_ES_CAB    FECHA_NOTA,
        c.usu_crea_nota_es_cab USU_NOTA,
        C.CANT_ITEMS NUM_ITEMS,
        TO_CHAR(C.FEC_NOTA_ES_CAB,'YYYYMM') COD_PERIODO
   FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM G
  WHERE C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
    AND C.COD_LOCAL = G.COD_LOCAL
    AND C.NUM_NOTA_ES = G.NUM_NOTA_ES
    AND C.EST_NOTA_ES_CAB = 'M'
    AND C.TIP_NOTA_ES IN ('01', '02')
    AND C.COD_GRUPO_CIA = '001'
    AND C.COD_LOCAL = codi_local
  ORDER BY NUMERO_NOTA, NUMERO_GUIA;

    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
    v_resultPrev VARCHAR2(5000);
    v_result VARCHAR2(2000);
    v_notaTemp VARCHAR2(100);
    v_notaFinal VARCHAR2(100);
    n_cont NUMBER;
    --INICIO FGUILLEN/19112013
    v_existe NUMBER;
    v_existeOrigen NUMBER;
    v_sec NUMBER;
    C_NUM_INTERNO VARCHAR(20);
    --FIN FGUILLEN/19112013

  BEGIN

    BEGIN

    v_result := 'OK';
    n_cont := 1;

    if cIndMigrado_in = 'S' then
       IF  ptoventa_transf2.ping(cIpServLocal_in) = 'S' THEN
                    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                                      FROM T_LGT_NOTA_ES_CAB
                                      WHERE COD_GRUPO_CIA = :1
                                            AND COD_LOCAL = :2
                                            AND EST_NOTA_ES_CAB = :3';
                    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in,'E';
                    LOOP
                      FETCH curTransf INTO v_cCodLocal,v_cNumNota,v_cEstNota;
                      EXIT WHEN curTransf%NOTFOUND;
                      ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in, v_cCodLocal,v_cNumNota,'L');
                        EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_TRANSF_ORIGINAL@XE_'||v_cCodLocal||
                                          '(:1,:2,:3,:4); END;'
                                            USING cCodGrupoCia_in,v_cCodLocal,v_cNumNota,'L';
                      COMMIT;
                    END LOOP;
      END IF;
    -- SI DESTINO ES BTLVENTA
    ELSE
        BEGIN

            FOR CUR1 IN CUR_CAB(cCodLocal_in)
            LOOP
              ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in, CUR1.COD_ORIGEN,CUR1.NUMERO_NOTA,'L');
              commit;
            END LOOP;
            EXCEPTION
            WHEN OTHERS THEN

            v_result:= SQLERRM;

            ROLLBACK;
             FARMA_EMAIL.envia_correo(FARMA_EMAIL.GET_SENDDOR_ADDRESS,
            'DUBILLUZ',
            'ERROR EN pcargar_guia_traspaso: ',
            'ERROR EN pcargar_guia_traspaso: ',
            SQLERRM,
            '',
            FARMA_EMAIL.GET_EMAIL_SERVER,
            TRUE);

            END;
    END IF;
    ---------------------------------------------------------------------------
    ---------------------------------------------------------------------------
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
    END;
  END;


  /****************************************************************************/
  PROCEDURE ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cEstNotaEs_in IN CHAR)
  AS
  BEGIN
    --ACTUALIZA TRANSF
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'SISTEMAS',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;

    UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'SISTEMAS',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;
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
    --CCReceiverAddress VARCHAR2(120) := NULL;
    CCReceiverAddress VARCHAR2(120) := 'dubilluz';
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
                          ReceiverAddress, --descomentar
                          vAsunto_in||v_vDescLocalDestino,--'VIAJERO EXITOSO: '||v_vDescLocal,
                          vTitulo_in,--'EXITO',
                          mesg_body,
                          CCReceiverAddress, --descomentar
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
    SELECT VAL_FRAC_LOCAL
      INTO v_nValFracLocDestino
    FROM LGT_PROD_LOCAL
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_PROD = cCodProd_in;

    IF MOD(nCantMov_in*v_nValFracLocDestino,nValFrac_in) = 0 THEN
      v_cInd := 'V';
    END IF;

    cInd_out := v_cInd;
  END;
/***************************************/

  /****************************************************************************/



/****************************************************************************/
FUNCTION PING(p_HOST_NAME VARCHAR2)
  RETURN VARCHAR2 IS
  tcpConnection UTL_TCP.CONNECTION;
  C_PING_OK    CONSTANT VARCHAR2(10) := 'S';
  C_PING_ERROR CONSTANT VARCHAR2(10) := 'N';
  p_PORT      NUMBER := 1521;
BEGIN
  /*
(RESULT)
REMOTE_HOST 0
REMOTE_PORT
LOCAL_HOST
LOCAL_PORT
IN_BUFFER_SIZE
OUT_BUFFER_SIZE
CHARSET
NEWLINE
TX_TIMEOUT
  */
    RETURN conn_local.ping(p_HOST_NAME);
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

PROCEDURE GET_ORIGEN_TRANSFERENCIA_DEL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  AS
    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
  --COD_MOTIVO_INTERNO_TRANS_trans = 02 lgt_nota_es_cab
  --tip_origen_nota_es=01
  --TIP_MOT_NOTA_ES =205

  BEGIN
    BEGIN
  --INSERT SELECT 'P'
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
          AND (SELECT COD_MOTIVO_INTERNO_TRANS FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND TIP_ORIGEN_NOTA_ES=''01'' AND TIP_MOT_NOTA_ES=''205''
          AND EST_NOTA_ES_CAB = :3 ' USING cCodGrupoCia_in,cCodLocal_in,'C';
    dbms_output.put_line('01');
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
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND (SELECT COD_MOTIVO_INTERNO_TRANS FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND C.TIP_ORIGEN_NOTA_ES=''01'' AND C.TIP_MOT_NOTA_ES=''205''
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'C';

    --DET
    dbms_output.put_line('02');
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
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND (SELECT COD_MOTIVO_INTERNO_TRANS FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||' WHERE COD_GRUPO_CIA=C.COD_GRUPO_CIA AND COD_LOCAL=C.COD_LOCAL AND NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND C.TIP_ORIGEN_NOTA_ES=''01'' AND C.TIP_MOT_NOTA_ES=''205''
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'C';
    --ACTUALIZA ESTADO EN LOCAL ORIGEN
    EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_EST_TRANSF_ORIGEN@XE_'||cCodLocal_in||'(:1,:2); END;' USING cCodGrupoCia_in,cCodLocal_in;
    dbms_output.put_line('03');

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
    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,EST_NOTA_ES_CAB
                      FROM T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||'
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_DESTINO_NOTA_ES = :2
                            AND EST_NOTA_ES_CAB IN (''A'',''X'',''R'')' ;
    --DBMS_OUTPUT.PUT_LINE(v_vSentencia);
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in;
    LOOP
      FETCH curTransf INTO v_cCodLocal,v_cNumNota,v_cEstNota;
      EXIT WHEN curTransf%NOTFOUND;
      --ACTUALIZA MATRIZ
      UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'SISTEMAS',FEC_MOD_NOTA_ES_CAB = SYSDATE,
            EST_NOTA_ES_CAB = v_cEstNota
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = v_cCodLocal
            AND NUM_NOTA_ES = v_cNumNota;
      --DELETE LOCAL ORIGEN
      EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.BORRA_TRANSF_TEMPORAL@XE_'||cCodLocal_in||'(:1,:2,:3); END;'
                          USING cCodGrupoCia_in,v_cCodLocal,v_cNumNota;
    END LOOP;

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
PROCEDURE SET_DESTINO_TRANSFERENCIA_DEL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cCodLocal_origen IN CHAR)
  AS
    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
  BEGIN
    BEGIN
  --INSERT SELECT 'P' EN LOCAL
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
          AND (SELECT e.COD_MOTIVO_INTERNO_TRANS
               FROM  LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||'  e
               WHERE e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
               AND   e.COD_LOCAL=C.COD_LOCAL
               AND   e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND TIP_ORIGEN_NOTA_ES=''01'' AND TIP_MOT_NOTA_ES=''205''
          AND EST_NOTA_ES_CAB = :3 ' USING cCodGrupoCia_in,cCodLocal_in,'M';
    --DET
        dbms_output.put_line('111');
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_DET@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
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
          AND (SELECT COD_MOTIVO_INTERNO_TRANS
               FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||' e
               WHERE e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
               AND e.COD_LOCAL=C.COD_LOCAL
               AND e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND C.TIP_ORIGEN_NOTA_ES=''01'' AND C.TIP_MOT_NOTA_ES=''205''
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M';
    --GUIA
        dbms_output.put_line('112');
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_GUIA_REM@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
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
          AND (SELECT COD_MOTIVO_INTERNO_TRANS
               FROM LGT_NOTA_ES_CAB@XE_'||cCodLocal_origen||' e
               WHERE e.COD_GRUPO_CIA=C.COD_GRUPO_CIA
               AND e.COD_LOCAL=C.COD_LOCAL
               AND e.NUM_NOTA_ES=C.NUM_NOTA_ES)=''99'' AND C.TIP_ORIGEN_NOTA_ES=''01'' AND C.TIP_MOT_NOTA_ES=''205''
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M';
    --ACTUALIZA ESTADO
    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_DESTINO_NOTA_ES = :2
                            AND EST_NOTA_ES_CAB = :3
                            and cod_local=:4';--
       dbms_output.put_line('113');
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in,'M',cCodLocal_origen  ;
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
    DBMS_OUTPUT.PUT_LINE('TERMINO EL PROCESO DE ENVIAR TRANSFERENCIAS DE LOCAL: '||cCodLocal_in||'-'||cCodLocal_origen);
    EXCEPTION
      WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE ENVIAR TRANSFERENCIAS DE LOCAL: '||cCodLocal_in||'-'||cCodLocal_origen);
      DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
      INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocal_in,
                              'ERROR EN ENVIAR TRANSFERENCIAS DE LOCAL: ',
                              'ALERTA',
                              SUBSTR(SQLERRM, 1, 250));
    END;

    BEGIN
  --GET TRANSF 'A'
    v_vSentencia := 'SELECT NUM_NOTA_ES,EST_NOTA_ES_CAB
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_LOCAL = :2
                            AND EST_NOTA_ES_CAB IN (''A'',''X'',''R'')';
    OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocal_in;
    LOOP
      FETCH curTransf INTO v_cNumNota,v_cEstNota;
      EXIT WHEN curTransf%NOTFOUND;
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
--        dbms_output.put_line(SQLERRM);
        DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: '||cCodLocal_in);
        DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
        INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocal_in,
                                'ERROR EN ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: ',
                                'ALERTA',
                                SUBSTR(SQLERRM, 1, 250));
        --RAISE;*/
    END;
  END;
  
  /* *********************************************************************************** */
PROCEDURE ENVIO_ONLINE_TRANS_BTL_FV 
  AS
v_indProceso CHAR(1);
msg VARCHAR2(32767);
    --TRAE DE LOCALES
    CURSOR curLocalesTrans IS
      SELECT cia,cod_local,est_guia,g.cod_origen,g.cod_destino,
             (
             select mm.cod_local_sap
             from   nuevo.aux_mae_local mm
             where  mm.cod_local = g.cod_destino
             ) COD_DESTINO_SAP,
             g.fch_emision,g.num_guia
      FROM   BTLCERO.CAB_GUIA_CLIENTE g
      where  g.cia = '10'
      and    g.fch_emision >= trunc(sysdate-7)
      and    g.est_guia = 'TRA'
      and    g.cod_destino in (
                              select m.cod_local
                              from   nuevo.aux_mae_local m,
                                     apps.pbl_local@xe_999 l
                              where  m.fch_migracion is not null
                              and    m.fch_migracion <= trunc(sysdate)
                              and    m.cod_local_sap = l.cod_local
                              and    l.cod_cia = '003'
                              )
      AND    NOT EXISTS (
                         SELECT 1
                         FROM   btlprod.REL_TRANS_ENVIO_BTL_FV RR
                         WHERE  RR.CIA = G.CIA
                         AND    RR.COD_LOCAL = G.COD_LOCAL 
                         AND    RR.NUM_GUIA =  G.NUM_GUIA
                         );
    
    v_rCurLocales  curLocalesTrans%ROWTYPE;
    ctipo CHAR(1) := 'N';

  BEGIN
/*    ----
    SELECT IND_PROCESO
          INTO v_indProceso
        FROM AUX_IND_EJEC_PROC
        WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.ONLINE_BTL_FV';

        IF v_indProceso = 'S' THEN
                  msg := substr(SQLERRM,1,100);
          farma_email.envia_correo('oracle@mifarma.com.pe','jayala,DUBILLUZ',cSubject_in => 'ERROR TRANSFERENCIAS LOCALES BTL-FV: EL PROCEDIMIENTO SE ENCUENTRA EN PROCESO',ctitulo_in => 'EN ESTE MOMENTO YA SE ENCUENTRA EN PROCESO EL PROCEDIMIENTO:' || 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS',cmensaje_in => msg,cip_servidor => FARMA_EMAIL.GET_EMAIL_SERVER,cin_html => TRUE);
          RAISE_APPLICATION_ERROR(-20001,'EN ESTE MOMENTO YA SE ENCUENTRA EN PROCESO EL PROCEDIMIENTO:' || 'PTOVENTA_TRANSF2.EJECT_TRANSFERENCIAS');
        ELSE
          UPDATE AUX_IND_EJEC_PROC
          SET FEC_INICIO = SYSDATE,
              FEC_FIN = NULL,
              IND_PROCESO = 'S',
              USU_PROCESO = USER,
              IP_PROCESO = SUBSTR(SYS_CONTEXT('USERENV','IP_ADDRESS'),1,50)
          WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.ONLINE_BTL_FV';
          COMMIT;
        END IF;
    ----*/
     dbms_output.put_line('ENVIO_ONLINE_TRANS_BTL_FV BTL');
    IF ctipo='N' then
        dbms_output.put_line('ENVIO_ONLINE_TRANS_BTL_FV BTL');

        --TRAE DE LOCALES
        FOR v_rCurLocales IN curLocalesTrans
        LOOP
          begin
          INSERT INTO btlprod.REL_TRANS_ENVIO_BTL_FV
          (cia, cod_local, est_guia, cod_origen, cod_destino, cod_destino_sap, 
          fch_emision, num_guia)
          VALUES
          (v_rCurLocales.cia, v_rCurLocales.cod_local, v_rCurLocales.est_guia, v_rCurLocales.cod_origen, 
           v_rCurLocales.cod_destino, v_rCurLocales.cod_destino_sap, v_rCurLocales.fch_emision, 
           v_rCurLocales.num_guia);
           
           --COMMIT;     
           -- BEGIN
             p_envia_guia_btl_a_fv(v_rCurLocales.CIA,
                                                          v_rCurLocales.cod_local,
                                                          v_rCurLocales.num_guia);
             COMMIT;                                                          
          
           EXCEPTION
           WHEN OTHERS THEN 
           ROLLBACK;
           dbms_output.put_line(sqlerrm);
                        farma_email.envia_correo('oracle@mifarma.com.pe','jayala,DUBILLUZ',
                        cSubject_in => 'ERROR TRANSFERENCIAS LOCALES BTL-FV: revisar',
                        ctitulo_in => 'EN ESTE MOMENTO p_envia_guia_btl_a_fv:' || 
                                       SQLERRM||
                                      '',cmensaje_in => msg,cip_servidor => FARMA_EMAIL.GET_EMAIL_SERVER,cin_html => TRUE);
           END;                                               
                                                        
           dbms_output.put_line('origen2: '||v_rCurLocales.COD_LOCAL);
        END LOOP;
  
     END IF;
/*     ----
     UPDATE AUX_IND_EJEC_PROC
          SET FEC_FIN = SYSDATE,
              IND_PROCESO = 'N',
              USU_PROCESO = USER,
              IP_PROCESO = SUBSTR(SYS_CONTEXT('USERENV','IP_ADDRESS'),1,50)
          WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.ONLINE_BTL_FV';

          UPDATE AUX_IND_EJEC_PROC
          SET FEC_FIN = SYSDATE,
              IND_PROCESO = 'N'
          WHERE NOM_PROCESO = 'PTOVENTA_TRANSF2.ONLINE_BTL_FV';

          COMMIT;
     ----*/
  END;
 /* ***********************************************************************************  */
  -- Autor : EMAQUERA
  -- Fecha : 11/07/2014
  -- Proposito: Se replico la funcion TRANSF_F_CHAR_LLEVAR_DESTINO para adapatarla en transferencias BTL Ventas - BTL Farmaventas
 /* ***********************************************************************************  */
  FUNCTION TRANSF_F_CHAR_LLEVAR_DESTINO2(cCodGrupoCia_in     IN CHAR,
                                       cCodLocalOrigen_in  IN CHAR,
                                       cCodLocalDestino_in IN CHAR,
                                       cNumNotaEs_in       IN CHAR,
                                       vIdUsu_in           IN CHAR)
  RETURN CHAR
  AS
    isCiaBTL number;
    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;

    v_cRespuesta CHAR(1):='N';
    isLocalMigrado number;
    ipLocalDestino varchar2(100);



  CURSOR cur_cab(codi_local IN CHAR,
  cNumNotaEs in char) IS
 SELECT c.cod_origen_nota_es COD_ORIGEN,
        c.cod_destino_nota_es COD_DESTINO,
        C.NUM_NOTA_ES        NUMERO_NOTA,
        G.NUM_GUIA_REM       NUMERO_GUIA,
        C.FEC_NOTA_ES_CAB    FECHA_NOTA,
        c.usu_crea_nota_es_cab USU_NOTA,
        C.CANT_ITEMS NUM_ITEMS,
        TO_CHAR(C.FEC_NOTA_ES_CAB,'YYYYMM') COD_PERIODO
   FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM G
  WHERE C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
    AND C.COD_LOCAL = G.COD_LOCAL
    AND C.COD_ORIGEN_NOTA_ES> '600' -- Filtro de Locales BTL 
    AND C.COD_DESTINO_NOTA_ES> '600' -- Filtro de Locales BTL 
    AND C.NUM_NOTA_ES = G.NUM_NOTA_ES
    AND C.EST_NOTA_ES_CAB = 'M'
    AND C.TIP_NOTA_ES IN ('01', '02')
    AND C.COD_GRUPO_CIA = '001'
    AND C.COD_DESTINO_NOTA_ES = codi_local
    and c.num_nota_es = cNumNotaEs
    and not exists (
                    select 1
                    from  btlcero.cab_guia_cliente gc
                    where  gc.num_guia = g.num_guia_rem
                    and    gc.fch_emision >= trunc(sysdate)-1
                    )
  ORDER BY NUMERO_NOTA, NUMERO_GUIA;


  CURSOR cur_det(codi_local IN CHAR, NUM_NOTA IN VARCHAR) IS
  SELECT A.COD_PRODUCTO COD_PROD,
         TRUNC(D.CANT_MOV/D.VAL_FRAC) CANT_PROD,
         (MOD(D.CANT_MOV,D.VAL_FRAC)*A1.CTD_FRACCIONAMIENTO)/D.VAL_FRAC CANT_PROD_FRAC,
         DECODE(MOD(D.CANT_MOV,D.VAL_FRAC),0,0,1) FLG_FRAC,
         A1.CTD_FRACCIONAMIENTO CANT_FRAC,
         D.val_prec_unit PREC_VTA,
         case when trim(a3.cod_igv)='00' then 0 else round((val_prec_total/1.19),2) end MTO_BASE,
         case when trim(a3.cod_igv)='00' then val_prec_total else 0 end MTO_EXO,
         case when trim(a3.cod_igv)='00' then 0 else round((0.19/1.19)*val_prec_total,2) end MTO_IMP,
         val_prec_total MTO_TOTAL
   FROM T_LGT_NOTA_ES_DET D, CMR.AUX_MAE_PRODUCTO_COM A,
        CMR.MAE_PRODUCTO_COM A1, LGT_PROD A3
  WHERE D.COD_GRUPO_CIA = '001'
    AND D.COD_PROD = A.COD_CODIGO_SAP
    and D.NUM_NOTA_ES = NUM_NOTA
    AND D.COD_LOCAL = CODI_LOCAL
    AND A.COD_PRODUCTO = A1.COD_PRODUCTO
    AND A.COD_CODIGO_SAP = A3.COD_PROD
  ORDER BY NUM_NOTA_ES;
  
    C_NUM_INTERNO VARCHAR(20);
    v_sec NUMBER;        

    cCodOrigenBTL varchar2(20);
    cCodDestinBTL varchar2(20); 
    
    nExisteSTKUBI  number; 
    nExisteSTKZONA number;  
    
  BEGIN
   
    SELECT COUNT(1)
    INTO   isCiaBTL 
    FROM   apps.pbl_local@xe_999 l
    WHERE  l.cod_local = cCodLocalDestino_in
    and    l.cod_cia = '003';
  
  if isCiaBTL = 0 then
    -- NO ES CIA BTL sino es un MF o FASA
    -- DEBE INTENTAR EVIAR AL DESTINO EN LINEA
    select p.ip_servidor_local
          into   ipLocalDestino
          from   apps.pbl_local@xe_999 p
          where  p.cod_local = cCodLocalDestino_in;
          
          if ping(ipLocalDestino) = 'S' then    
/*          execute immediate   
        'begin 
        INSERT INTO PTOVENTA.T_LGT_NOTA_ES_CAB@XE_'||cCodLocalDestino_in||' (COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES, '||
       'FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES, '||
       'CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA, '||
       'RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS , '||
       'TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION, '||
       'USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	, '||
       'IND_NOTA_IMPRESA,FEC_PROCESO_SAP) '||
       'SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES, '||
       'FEC_NOTA_ES_CAB,''L'',TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES, '||
       'CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA, '||
       'RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS, '||
       'TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION, '||
       'USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	, '||
       'IND_NOTA_IMPRESA,FEC_PROCESO_SAP '||
       'FROM T_LGT_NOTA_ES_CAB C '||
       'WHERE COD_GRUPO_CIA = :1 '||
            'AND COD_LOCAL = :2 '||
            --'AND EST_NOTA_ES_CAB = :3 '||
            'AND NUM_NOTA_ES     = :3; ' ||         
	  'INSERT INTO PTOVENTA.T_LGT_GUIA_REM@XE_'||cCodLocalDestino_in||' (COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM, '||
      'NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM, '||
      'EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA) '||
      'SELECT G.COD_GRUPO_CIA,G.COD_LOCAL,G.NUM_NOTA_ES,G.SEC_GUIA_REM, '||
      'G.NUM_GUIA_REM,G.FEC_CREA_GUIA_REM,G.USU_CREA_GUIA_REM,G.FEC_MOD_GUIA_REM,G.USU_MOD_GUIA_REM, '||
      'G.EST_GUIA_REM,G.NUM_ENTREGA,G.IND_GUIA_CERRADA,G.IND_GUIA_IMPRESA '||
      'FROM T_LGT_NOTA_ES_CAB C, '||
       '     T_LGT_GUIA_REM G '||
      'WHERE C.COD_GRUPO_CIA = :1 '||
            'AND C.COD_LOCAL = :2 '||
            --'AND C.EST_NOTA_ES_CAB = :3 '||
            'AND C.NUM_NOTA_ES     = :3 '||
            'AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA '||
            'AND C.COD_LOCAL = G.COD_LOCAL '||
            'AND C.NUM_NOTA_ES = G.NUM_NOTA_ES; '||          
      	  'INSERT INTO PTOVENTA.T_LGT_NOTA_ES_DET@XE_'||cCodLocalDestino_in||' (COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES, '|| 
            'COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET, '|| 
            'FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR, '|| 
            'NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET, '|| 
            'FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA) '|| 
            'SELECT D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_NOTA_ES,D.SEC_DET_NOTA_ES, '|| 
            'D.COD_PROD,D.SEC_GUIA_REM,D.VAL_PREC_UNIT,D.VAL_PREC_TOTAL,D.CANT_MOV,D.VAL_FRAC,D.EST_NOTA_ES_DET, '|| 
            'D.FEC_NOTA_ES_DET,D.DESC_UNID_VTA,D.FEC_VCTO_PROD,D.NUM_LOTE_PROD,D.CANT_ENVIADA_MATR, '|| 
            'D.NUM_PAG_RECEP,D.IND_PROD_AFEC,D.USU_CREA_NOTA_ES_DET,D.FEC_CREA_NOTA_ES_DET,D.USU_MOD_NOTA_ES_DET, '|| 
            'D.FEC_MOD_NOTA_ES_DET,D.FEC_PROCESO_SAP,D.POSICION,D.NUM_ENTREGA '|| 
            'FROM T_LGT_NOTA_ES_CAB C, '|| 
             '    T_LGT_NOTA_ES_DET D '|| 
            'WHERE C.COD_GRUPO_CIA = :1 '|| 
                  'AND C.COD_LOCAL = :2 '|| 
                  --'AND C.EST_NOTA_ES_CAB = :3 '|| 
                  'AND C.NUM_NOTA_ES     = :3 '|| 
                  'AND C.COD_GRUPO_CIA   = D.COD_GRUPO_CIA '|| 
                  'AND C.COD_LOCAL       = D.COD_LOCAL '|| 
                  'AND C.NUM_NOTA_ES     = D.NUM_NOTA_ES; '|| 
      		'END;'
      		USING cCodGrupoCia_in,cCodLocalOrigen_in,cNumNotaEs_in;*/
          commit;    -- COMENTADO YA QUE ESTE PROCEDIMIENTO SOLO SIRVE PARA ENVIAR BTL
          end if;
  else
            
    SELECT COUNT(1)
    INTO   isLocalMigrado
    FROM   NUEVO.AUX_MAE_LOCAL M
    WHERE  M.FCH_MIGRACION IS NOT NULL
    AND    M.FCH_MIGRACION <= TRUNC(SYSDATE)
    AND    M.COD_LOCAL_SAP = cCodLocalDestino_in;
         dbms_output.put_line('cCodLocalDestino_in>'||cCodLocalDestino_in);
     dbms_output.put_line('isLocalMigrado>'||isLocalMigrado);
    if isLocalMigrado >= 1 then 
          select p.ip_servidor_local
          into   ipLocalDestino
          from   apps.pbl_local@xe_999 p
          where  p.cod_local = cCodLocalDestino_in;
          
          if ping(ipLocalDestino) = 'S' then    
          
          dbms_output.put_line('PIN >');
          BEGIN
          --CAB
          EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_CAB@XE_'||cCodLocalDestino_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
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
                AND COD_LOCAL = :2
                AND NUM_NOTA_ES = :3 
                AND COD_DESTINO_NOTA_ES = :4
                AND FEC_NOTA_ES_CAB>=TRUNC(SYSDATE-16)
                AND EST_NOTA_ES_CAB = :5' USING cCodGrupoCia_in,cCodLocalOrigen_in,cNumNotaEs_in,cCodLocalDestino_in,'M';

          --GUIA
          EXECUTE IMMEDIATE 'INSERT INTO T_LGT_GUIA_REM@XE_'||cCodLocalDestino_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
          NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
          EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA)
          SELECT distinct G.COD_GRUPO_CIA,G.COD_LOCAL,G.NUM_NOTA_ES,G.SEC_GUIA_REM,
          G.NUM_GUIA_REM,G.FEC_CREA_GUIA_REM,G.USU_CREA_GUIA_REM,G.FEC_MOD_GUIA_REM,G.USU_MOD_GUIA_REM,
          G.EST_GUIA_REM,G.NUM_ENTREGA,G.IND_GUIA_CERRADA,G.IND_GUIA_IMPRESA
          FROM T_LGT_NOTA_ES_CAB C,
                T_LGT_GUIA_REM G
          WHERE C.COD_GRUPO_CIA = :1
                AND C.COD_LOCAL = :2
                AND C.NUM_NOTA_ES = :3
                AND C.COD_DESTINO_NOTA_ES = :4
                AND C.EST_NOTA_ES_CAB = :5
                AND FEC_CREA_GUIA_REM>=TRUNC(SYSDATE-16)                
                AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                AND C.COD_LOCAL = G.COD_LOCAL
                AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocalOrigen_in,cNumNotaEs_in,cCodLocalDestino_in,'M';
      
          --DET
          EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_DET@XE_'||cCodLocalDestino_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
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
                AND C.COD_LOCAL = :2
                AND C.NUM_NOTA_ES = :3
                AND C.COD_DESTINO_NOTA_ES = :4
                AND C.EST_NOTA_ES_CAB = :5
                AND FEC_NOTA_ES_DET>=TRUNC(SYSDATE-16)
                AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                AND C.COD_LOCAL = D.COD_LOCAL
                AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocalOrigen_in,cNumNotaEs_in,cCodLocalDestino_in,'M';                                         
          
          --ACTUALIZA ESTADO
          dbms_output.PUT_LINE('borrar_el_output.. envio_trans_normal');
          v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                            FROM T_LGT_NOTA_ES_CAB
                            WHERE COD_GRUPO_CIA = :1
                                  AND COD_DESTINO_NOTA_ES = :2
                                  AND COD_LOCAL>''600''
                                  AND EST_NOTA_ES_CAB = :3
                                  AND NUM_NOTA_ES = :4';
          OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocalDestino_in,'M',cNumNotaEs_in;
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
          DBMS_OUTPUT.PUT_LINE('TERMINO EL PROCESO DE ENVIAR TRANSFERENCIAS DE LOCAL: '||cCodLocalDestino_in);
          EXCEPTION
            WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE ENVIAR TRANSFERENCIAS DE LOCAL: '||cCodLocalDestino_in);
            DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
            INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocalDestino_in,
                                    'ERROR EN ENVIAR TRANSFERENCIAS DE LOCAL: ',
                                    'ALERTA',
                                    SUBSTR(SQLERRM, 1, 250));
          END;          
          
          BEGIN
        --GET TRANSF 'A'
          v_vSentencia := 'SELECT NUM_NOTA_ES,EST_NOTA_ES_CAB
                            FROM T_LGT_NOTA_ES_CAB
                            WHERE COD_GRUPO_CIA = :1
                                  AND COD_LOCAL = :2
                                  AND COD_LOCAL > ''600''
                                  AND EST_NOTA_ES_CAB IN (''A'',''X'',''R'')';
          OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodLocalDestino_in;
          LOOP
            FETCH curTransf INTO v_cNumNota,v_cEstNota;
            EXIT WHEN curTransf%NOTFOUND;
            DBMS_OUTPUT.PUT_LINE('entro a borrar');
            --ACTUALIZA TRANSF ORIGINAL
            EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_TRANSF_ORIGINAL@XE_'||cCodLocalDestino_in||'(:1,:2,:3,:4); END;'
                                USING cCodGrupoCia_in,cCodLocalDestino_in,v_cNumNota,v_cEstNota;
            --ELIMINA TRANSF LOCAL
            --EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.BORRA_TRANSF_TEMPORAL@XE_'||cCodLocalDestino_in||'(:1,:2,:3); END;'
            --                    USING cCodGrupoCia_in,cCodLocalDestino_in,v_cNumNota;
            --ELIMINA TRANSF MATRIZ
            --BORRA_TRANSF_TEMPORAL(cCodGrupoCia_in,cCodLocalDestino_in,v_cNumNota);
          END LOOP;
      
          COMMIT;
          DBMS_OUTPUT.PUT_LINE('TERMINO EL PROCESO DE ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: '||cCodLocalDestino_in);
          EXCEPTION
            WHEN OTHERS THEN
              ROLLBACK;
              DBMS_OUTPUT.PUT_LINE('ERROR EN EL PROCESO DE ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: '||cCodLocalDestino_in);
              DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM, 1, 250));
              INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in,cCodLocalDestino_in,
                                      'ERROR EN ACTUALIZAR TRANSFERENCIAS DE LOCAL DESTINO: ',
                                      'ALERTA',
                                      SUBSTR(SQLERRM, 1, 250));
              --RAISE;
          END;          
                 
          end if;
    else
       -- no es migrado debe grabar la guia de BTL en VENTA 
           BEGIN
           dbms_output.put_line('cCodLocalDestino_in>'||cCodLocalDestino_in);
              FOR CUR1 IN CUR_CAB(cCodLocalDestino_in,cNumNotaEs_in)
          
              LOOP
          
              SELECT 'DL' || LPAD(TO_CHAR(NUEVO.SEQ_NUM_INTERNO.NEXTVAL), 8, '0') INTO C_NUM_INTERNO FROM DUAL;
          
                  select m.cod_local
                into   cCodOrigenBTL
                from   nuevo.aux_mae_local m
                where  m.cod_local_sap = CUR1.COD_ORIGEN;
                 
            
                select m.cod_local
                into   cCodDestinBTL
                from   nuevo.aux_mae_local m
                where  m.cod_local_sap = CUR1.COD_DESTINO;    
            dbms_output.put_line('cCodOrigenBTL>'||cCodOrigenBTL);
                        dbms_output.put_line('cCodDestinBTL>'||cCodDestinBTL);
                          dbms_output.put_line(' CUR1.NUMERO_GUIA>'|| CUR1.NUMERO_GUIA);
                INSERT INTO btlcero.cab_guia_cliente
                  (COD_BTL,
                   NUM_GUIA,
                   EST_GUIA,
                   FCH_EMISION,
                   NUM_INTERNO,
                   USU_EMISION,
                   TOT_ITEM,
                   MTO_VALOR_GUIA,
                   COD_TIPODOC,
                   COD_MOTIVO_GUIA,
                   COD_ORIGEN,
                   COD_DESTINO,
                   COD_PERIODO,
                   FLG_KARDEX,
                   COD_ZONA_ORIG,
                   COD_ZONA_DEST,
                   COD_TIPO,
                   COD_MOTIVO,
                   MTO_BASE_IMP,
                   MTO_IMPUESTO,
                   MTO_EXONERADO,
                   MTO_TOTAL,
                   PCT_IGV,
                   PCT_COPAGO,
                   IMP_COPAGO,
                   CIA,
                   COD_FORMATO,
                   COD_LOCAL)
                VALUES
                  (cCodOrigenBTL,--CUR1.COD_ORIGEN,
                   CUR1.NUMERO_GUIA,
                   'TRA',
                   sysdate,--CUR1.FECHA_NOTA,
                   C_NUM_INTERNO,
                   -- DUBILLUZ 18.06.2014
                   nvl((select b.cod_usuario
                    from pbl_usu_local l, nuevo.mae_usuario_btl b
                   where l.dni_usu = b.num_doc_identidad
                     AND L.EST_USU = 'A'
                     and l.login_usu = CUR1.USU_NOTA
                     and b.est_usuario = 'ACT'
                     and b.cod_btl=TO_CHAR(LPAD(TO_NUMBER(CUR1.COD_ORIGEN-600),3,0))),'02123'),
                   CUR1.NUM_ITEMS,
                   0,
                   'GRL',
                   '001',
                   cCodOrigenBTL,--CUR1.COD_ORIGEN,
                   cCodDestinBTL,--CUR1.COD_DESTINO,
                   CUR1.COD_PERIODO,
                   0,
                   cCodOrigenBTL,--CUR1.COD_ORIGEN,
                   cCodDestinBTL,--CUR1.COD_DESTINO,
                   'VTA',
                   'VT1',
                   0,
                   0,
                   0,
                   0,
                   0,
                   0,
                   0,
                   '10',
                   '005',
                   cCodOrigenBTL);--CUR1.COD_ORIGEN);
            
                 v_sec:= 1;
            
                 FOR CUR2 IN CUR_DET(CUR1.COD_ORIGEN, CUR1.NUMERO_NOTA)
            
                    LOOP
            
                    insert into btlcero.det_guia_cliente
                    (num_guia, --1
                     num_item, --2
                     cod_producto, --3
                     ctd_producto, --4
                     ctd_producto_frac, --5
                     flg_fracciono, --6
                     ctd_fracciono, --7
                     prc_kairos, --8
                     prc_venta, --9
                     pct_igv, --10
                     mto_base_imp, --11
                     mto_exonerado, --12
                     mto_impuesto, --13
                     mto_total, --14
                     pct_copago, --15
                     imp_copago, --16
                     cia, --17
                     cod_local --18
                     )
                    values
                    (CUR1.NUMERO_GUIA, --1
                     V_SEC, --2
                     CUR2.COD_PROD, --3
                     CUR2.CANT_PROD, --4
                     CUR2.CANT_PROD_FRAC, --5
                     CUR2.FLG_FRAC, --6
                     CUR2.CANT_FRAC, --7,                                         --7
                     0, --8
                     CUR2.PREC_VTA, --9
                     0.19, --10
                     CUR2.MTO_BASE, --11
                     CUR2.MTO_EXO, --12
                     CUR2.MTO_IMP, --13
                     CUR2.MTO_TOTAL, --14
                     0, --15
                     0, --16
                     '90', --17
                     cCodOrigenBTL--CUR1.COD_ORIGEN --18
                     );
                  v_sec:= v_sec +1;
                  
                  select count(1)
                  into   nExisteSTKUBI
                  from  NUEVO.AUX_STOCK_UBICACION a
                  WHERE A.COD_PRODUCTO = CUR2.COD_PROD
                  AND A.cod_ubicacion = 'TRA';
                  
                  if nExisteSTKUBI > 0 then 
                      update NUEVO.AUX_STOCK_UBICACION a
                      set A.CTD_PRODUCTO_FRAC = A.CTD_PRODUCTO_FRAC + CUR2.CANT_PROD_FRAC,
                          A.CTD_PRODUCTO = A.CTD_PRODUCTO + CUR2.CANT_PROD
                      WHERE A.COD_PRODUCTO = CUR2.COD_PROD
                      AND A.cod_ubicacion = 'TRA';
                  else
                      insert into NUEVO.AUX_STOCK_UBICACION
                      (cod_producto, cod_ubicacion, ctd_producto, ctd_producto_frac, ctd_producto_sep, ctd_producto_frac_sep)
                      values
                      (CUR2.COD_PROD,'TRA',CUR2.CANT_PROD,CUR2.CANT_PROD_FRAC,0,0);
                  end if;    
                  

                  select count(1)
                  into   nExisteSTKZONA
                  from  NUEVO.AUX_STOCK_ZONA a
                  WHERE A.COD_PRODUCTO = CUR2.COD_PROD
                  AND A.cod_zona = 'TRA';
                 
                 if nExisteSTKZONA > 0 then           
                  update NUEVO.AUX_STOCK_ZONA b
                  set b.CTD_PRODUCTO_FRAC = b.CTD_PRODUCTO_FRAC + CUR2.CANT_PROD_FRAC,
                      b.CTD_PRODUCTO = b.CTD_PRODUCTO + CUR2.CANT_PROD
                  WHERE b.COD_PRODUCTO = CUR2.COD_PROD
                  AND b.cod_zona = 'TRA';
                 
                 else
                  insert into NUEVO.AUX_STOCK_ZONA
                  (cod_zona, cod_producto, ctd_producto, ctd_producto_frac)
                  values
                  ('TRA',CUR2.COD_PROD,CUR2.CANT_PROD,CUR2.CANT_PROD_FRAC);                 
                 end if; 
          
                  END LOOP;

                  ACTUALIZA_TRANSF_RAC(cCodGrupoCia_in,CUR1.COD_ORIGEN,CUR1.NUMERO_NOTA,CUR1.COD_DESTINO);
                  
                  -- ACTUALIZA ESTADO EN LOCAL
/*                  BEGIN          
                  EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_TRANSF_ORIGINAL@XE_'||CUR1.COD_ORIGEN||'(:1,:2,:3,:4); END;'
                                  USING cCodGrupoCia_in,CUR1.COD_ORIGEN,CUR1.NUMERO_NOTA,'L';
                  dbms_output.put_line('Inicia Actualiza Estado en local');
                  EXCEPTION
                  WHEN OTHERS THEN
                  DBMS_OUTPUT.PUT_LINE('No se actualizo correctamente el local origen - '||CUR1.COD_ORIGEN||' : '||SQLCODE||' -ERROR- '||SQLERRM);
                  END;*/

              commit;
              END LOOP;
              /*EXCEPTION
              WHEN OTHERS THEN
                ROLLBACK;*/
              END;       
    end if;
    /*
    INSERT INTO BORRA
    (COD_LOCAL,COD_LOCAL_DESTINO,FECHA)
    VALUES
    (cCodLocalOrigen_in,cCodLocalDestino_in,SYSDATE);
    */
    end if;
    v_cRespuesta:='S';

       RETURN v_cRespuesta;
/*
  EXCEPTION
      WHEN OTHERS THEN
           v_cRespuesta:='N';
       RETURN v_cRespuesta;*/

  END;
-- Agregado Metodo para grabar en la TRANSFERENCIA de MIFARMA sacandola del RAC
  PROCEDURE P_ENVIA_GUIA_BTL_A_FV(cCodCia_in    IN CHAR,
                                 cCodLocal_in  IN CHAR,
                                 cNumGuia_in   IN CHAR)
  AS

    cursor cur_cab IS
      SELECT c.cia,
             c.cod_local,
             c.num_guia,
             A2.COD_LOCAL_SAP local_orig,
             A.COD_LOCAL_SAP  local_dest,
             FCH_EMISION      fech_envio,
             (select count(*) from BTLCERO.DET_GUIA_CLIENTE d where d.num_guia = c.num_guia) total_item
        FROM BTLCERO.CAB_GUIA_CLIENTE C,
             NUEVO.AUX_MAE_LOCAL      A,
             NUEVO.AUX_MAE_LOCAL      A2
       WHERE c.CIA = cCodCia_in
       AND   C.COD_LOCAL = cCodLocal_in
       AND   C.NUM_GUIA = cNumGuia_in
        and c.fch_emision >= TRUNC(sysdate) - 7  -- dubilluz 19.06.2014 AGREGADO USO IDX
         --AND A2.COD_LOCAL_SAP = cCodLocal_in
         AND COD_DESTINO NOT IN ('PRV', 'CLI')
         AND EST_GUIA = 'TRA'
         AND COD_ORIGEN = A2.COD_LOCAL
         AND COD_DESTINO = A.COD_LOCAL
         AND A.FCH_MIGRACION <= TRUNC(SYSDATE);

    cursor cur_det(cCia varchar2,cCodLocal varchar2,cNmguia varchar2,cCOdLocalDestino varchar2) IS
        SELECT p.cod_codigo_sap cod_prod,
               nvl(p3.unid_vta,mp.desc_unid_present) des_uni_vta,
               (((c.ctd_producto*p2.ctd_fraccionamiento) + c.ctd_producto_frac)/p2.ctd_fraccionamiento*p3.val_frac_local) cant_envi,
               p3.val_frac_local frac_vent,
               DECODE(trim(mp.cod_igv),'00','N','S') AFECTO,
               c.cod_producto prod_btl,
               c.ctd_producto cant_btl,
               c.ctd_producto_frac cant_frac_btl
             FROM BTLCERO.DET_GUIA_CLIENTE C,
                     cmr.aux_mae_producto_com p,
                     ptoventa.lgt_prod                 mp,
                     cmr.mae_producto_com p2,
                     -- dubilluz 18.06.2014
                     DELIVERY.lgt_prod_local p3 -- DUBILLUZ 18.06.2014
                     -- lgt_prod_local p3
               where C.CIA  = cCia
                 AND C.COD_LOCAL = cCodLocal
                 AND C.NUM_GUIA =     cNmguia           
                 AND  c.cod_producto = p.cod_producto
                 and mp.cod_grupo_cia = '001'
                 and mp.EST_PROD = 'A'
                 and p3.cod_grupo_cia = '001'
                 and p3.cod_local = cCOdLocalDestino
                 and p.cod_codigo_sap = mp.cod_prod
                 and c.cod_producto = p2.cod_producto
                 and p3.cod_prod = mp.cod_prod;
    --
    cCurGuiaCab BTLCERO.CAB_GUIA_CLIENTE%rowtype;
    cCodOrigenSAP varchar2(20);
    cCodDestinSAP varchar2(20);        
    cIpDestinoSAP varchar2(30);
    isMigradoFV number;
    
    v_guiasec NUMBER;    
    v_nnumsec NUMBER;    
    
    cCodGrupoCia_in CHAR(3):= '001';

    v_vSentencia VARCHAR2(32767);    
        curTransf FarmaCursor;
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;
    vCadena varchar2(32767);    
  BEGIN
           
     SELECT g.*
     into   cCurGuiaCab
     FROM   BTLCERO.CAB_GUIA_CLIENTE g
     where  g.CIA       = cCodCia_in
     and    g.COD_LOCAL = cCodLocal_in
     and    g.NUM_GUIA  = cNumGuia_in;

     select au.cod_local_sap,p.ip_servidor_local
     into   cCodDestinSAP,cIpDestinoSAP
     from   nuevo.aux_mae_local au,
            apps.pbl_local@xe_999 p
     where  au.cod_local = cCurGuiaCab.Cod_Destino
     and    au.cod_local_sap = p.cod_local;

     
     
     select count(1)
     into   isMigradoFV
     from   nuevo.aux_mae_local au,
            apps.pbl_local@xe_999 p
     where  au.cod_local = cCurGuiaCab.Cod_Destino
     and    au.cod_local_sap = p.cod_local
     and    au.fch_migracion is not null
     and    au.fch_migracion <= trunc(sysdate);
     
     -- el local es Migrado
     if isMigradoFV > 0 then
     dbms_output.put_line('cIpDestinoSAP>'||cIpDestinoSAP);
     
       if ping(cIpDestinoSAP) = 'S' then  
        -- AGREGA TEMPORAL --
     dbms_output.put_line('ping>');
            for cur1 in cur_cab loop
               ------------------------------------------------
              execute immediate
              --vCadena :=
                'insert into delivery.lgt_prod_local '||
                '(cod_grupo_cia, cod_local, cod_prod, stk_fisico, unid_vta, porc_dcto_1, porc_dcto_2,  '||
                'porc_dcto_3, est_prod_loc, ind_prod_cong, usu_crea_prod_local, fec_crea_prod_local, usu_mod_prod_local,  '||
                'fec_mod_prod_local, val_frac_local, ind_prod_fraccionado, val_prec_vta, val_prec_lista, cant_exhib,  '||
                'ind_reponer, ind_prod_habil_vta) '||
                'select cod_grupo_cia, cod_local, cod_prod, stk_fisico, unid_vta, porc_dcto_1, porc_dcto_2,  '||
                'porc_dcto_3, est_prod_loc, ind_prod_cong, usu_crea_prod_local, fec_crea_prod_local, usu_mod_prod_local,  '||
                'fec_mod_prod_local, val_frac_local, ind_prod_fraccionado, val_prec_vta, val_prec_lista,  '||
                'cant_exhib, ind_reponer, ind_prod_habil_vta '||
                'from   ptoventa.lgt_prod_local@xe_'||cur1.local_dest|| ' '|| 
                'where  cod_grupo_cia = '||''''||'001'||''''||' '||
                'and    cod_local = '||''''||cur1.local_dest||''''||' '||
                'and    cod_prod in ( '||
                                   ' SELECT p.cod_codigo_sap ' ||
                                   '   FROM BTLCERO.DET_GUIA_CLIENTE C, cmr.aux_mae_producto_com p ' ||
                                   '  where C.CIA =  '||''''||cur1.cia||''''||' '||
                                   '    AND C.COD_LOCAL = '||''''||cCodLocal_in||''''||' '||
                                   '    AND C.NUM_GUIA = '||''''||cur1.num_guia||''''||' '||
                                   '    AND c.cod_producto = p.cod_producto '||
                                   '    and not exists (select 1 '||
                                   '           from DELIVERY.lgt_prod_local p3 '||
                                   '          where p3.cod_grupo_cia =  '||''''||'001'||''''||' '||
                                   '            and p3.cod_local =  '||''''||cur1.local_dest||''''||' '||
                                   '            and p3.cod_prod = p.cod_codigo_sap) '||
                                   ') ';                
               ------------------------------------------------

     dbms_output.put_line('vCadena> '||vCadena);
     dbms_output.put_line('v_guiasec>'||v_nnumsec);
                  v_guiasec := 1;
                  /*
                       dbms_output.put_line('CUR1.local_orig>'||CUR1.local_orig);
                  v_nnumsec := ptoventa.farma_utility.obtener_numeracion('001', CUR1.local_orig, '011');
                                         dbms_output.put_line('CUR1.v_nnumsec>'||v_nnumsec);
                  ptoventa.farma_utility.actualizar_numera_sin_commit('001', CUR1.local_orig, '011', 'SISTEMAS');
                  */
                  v_nnumsec := CUR1.Num_Guia;
                  
                  INSERT INTO ptoventa.t_lgt_nota_es_cab NOLOGGING
                  (
                  cod_grupo_cia, cod_local, num_nota_es, fec_nota_es_cab, est_nota_es_cab,
                  tip_doc,num_doc,cod_origen_nota_es,cant_items,val_total_nota_es_cab,
                  cod_destino_nota_es,desc_empresa,ruc_empresa,dir_empresa,desc_trans,
                  ruc_trans,dir_trans,placa_trans,tip_nota_es,tip_origen_nota_es,tip_mot_nota_es,
                  est_recepcion,usu_crea_nota_es_cab,fec_crea_nota_es_cab,usu_mod_nota_es_cab,
                  fec_mod_nota_es_cab,ind_nota_impresa,fec_proceso_sap)
                  VALUES (
                   '001', cur1.local_orig, LPAD (v_nnumsec, 10, '0'), cur1.fech_envio,
                   'M', '03', NULL, cur1.local_orig, cur1.total_item, 0.00, cur1.local_dest,
                   'BTL SAC', '20302629219', 'Cal.V.Alzamora 147,UrbStaCatalina,LaVictoria,Lima',
                   NULL, NULL, NULL, NULL, '02', '01', NULL, 'P', 'SISTEMAS', SYSDATE,
                   NULL, NULL, 'S', NULL);
             dbms_output.put_line('PASO>'||v_nnumsec);
                  INSERT INTO ptoventa.t_lgt_guia_rem NOLOGGING
                              (
                               cod_grupo_cia, cod_local, num_nota_es, sec_guia_rem, num_guia_rem, fec_crea_guia_rem,
							   usu_crea_guia_rem, fec_mod_guia_rem, usu_mod_guia_rem, est_guia_rem, num_entrega, ind_guia_cerrada, ind_guia_impresa
                              )
                       VALUES (
								'001', cur1.local_orig, LPAD (v_nnumsec, 10, '0'), '1', cur1.num_guia, SYSDATE, 
								'SISTEMAS',NULL, NULL, 'A', NULL, 'N', 'N' );
            
                  for cur2 in cur_det(cur1.cia,cur1.cod_local,cur1.num_guia,cur1.local_dest)
            
                  loop
            
                  INSERT INTO ptoventa.t_lgt_nota_es_det NOLOGGING
                                       (
                                        cod_grupo_cia, cod_local, num_nota_es, sec_det_nota_es, cod_prod, sec_guia_rem, val_prec_unit, val_prec_total
										, cant_mov, val_frac, est_nota_es_det, fec_nota_es_det, desc_unid_vta, fec_vcto_prod, num_lote_prod, cant_enviada_matr
										, num_pag_recep, ind_prod_afec, usu_crea_nota_es_det, fec_crea_nota_es_det, usu_mod_nota_es_det, fec_mod_nota_es_det
										, fec_proceso_sap, posicion, num_entrega )
                                VALUES (
									   '001' , cur1.local_orig , LPAD (v_nnumsec, 10, '0') , v_guiasec , cur2.cod_prod , '1' , 0 , 0 ,
									   cur2.cant_envi , cur2.frac_vent , 'A' , cur1.fech_envio , cur2.des_uni_vta , NULL , NULL
										, cur2.cant_envi , NULL , cur2.afecto , 'SISTEMAS' , SYSDATE , NULL , NULL , NULL , NULL , NULL  );
            
                  v_guiasec := v_guiasec + 1;
            
                   update NUEVO.AUX_STOCK_UBICACION a
                    set A.CTD_PRODUCTO_FRAC = A.CTD_PRODUCTO_FRAC - CUR2.cant_frac_btl,
                        A.CTD_PRODUCTO = A.CTD_PRODUCTO + CUR2.cant_btl
                    WHERE A.COD_PRODUCTO = CUR2.prod_btl
                    AND A.cod_ubicacion = 'TRA';
            
                    update NUEVO.AUX_STOCK_ZONA b
                    set b.CTD_PRODUCTO_FRAC = b.CTD_PRODUCTO_FRAC - CUR2.cant_frac_btl,
                        b.CTD_PRODUCTO = b.CTD_PRODUCTO + CUR2.cant_btl
                    WHERE b.COD_PRODUCTO = CUR2.prod_btl
                    AND b.cod_zona = 'TRA';
            
                  END LOOP;
            

     dbms_output.put_line('jajaj>');
                  end loop;
                      --cCodDestinSAP varchar2(20);
                      dbms_output.put_line(v_nnumsec);
         -- ENVIA TEMPORAL --         
                                  EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_CAB@XE_'||cCodDestinSAP||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
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
                                        AND EST_NOTA_ES_CAB = :3 
                                        and  num_nota_es = :4' USING cCodGrupoCia_in,cCodDestinSAP,'M',LPAD (v_nnumsec, 10, '0');
              
              
                                  EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_DET@XE_'||cCodDestinSAP||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
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
                                        and c.num_nota_es = :4
                                        AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
                                        AND C.COD_LOCAL = D.COD_LOCAL
                                        AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodDestinSAP,'M',LPAD (v_nnumsec, 10, '0');
              
              
                                  EXECUTE IMMEDIATE 'INSERT INTO T_LGT_GUIA_REM@XE_'||cCodDestinSAP||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
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
                                        and c.num_nota_es = :4
                                        AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                                        AND C.COD_LOCAL = G.COD_LOCAL
                                        AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodDestinSAP,'M',LPAD (v_nnumsec, 10, '0');
              
              
                                  dbms_output.PUT_LINE('borrar_el_output.. envio_trans_normal');
              
                                  v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                                                    FROM T_LGT_NOTA_ES_CAB
                                                    WHERE COD_GRUPO_CIA = :1
                                                          AND COD_DESTINO_NOTA_ES = :2
                                                          AND EST_NOTA_ES_CAB = :3
                                                          and num_nota_es = :4';
              
              
                                  OPEN curTransf FOR v_vSentencia USING cCodGrupoCia_in,cCodDestinSAP,'M',LPAD (v_nnumsec, 10, '0');
                                  LOOP
                                    FETCH curTransf INTO v_cCodLocal,v_cNumNota,v_cEstNota;
                                    EXIT WHEN curTransf%NOTFOUND;
                                        UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_ONLINE_BTL',FEC_MOD_NOTA_ES_CAB = SYSDATE,
                                               IND_TRANS_AUTOMATICA = 'S',
                                              EST_NOTA_ES_CAB = 'L'
                                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND COD_LOCAL = cCodLocal_in
                                              AND NUM_NOTA_ES = v_cNumNota;
                                    
                                        UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_ONLINE_BTL',FEC_MOD_NOTA_ES_CAB = SYSDATE,
                                               IND_TRANS_AUTOMATICA = 'S',
                                              EST_NOTA_ES_CAB = 'L'
                                        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND COD_LOCAL = cCodLocal_in
                                              AND NUM_NOTA_ES = v_cNumNota;                                    
                                  END LOOP;
              
                                UPDATE RECEPCION_GUIAS S
                               SET FECHA_LOCAL = SYSDATE
                             WHERE EXISTS
                                   (SELECT 1
                                      FROM T_LGT_NOTA_ES_CAB C, T_LGT_GUIA_REM G
                                     WHERE C.COD_GRUPO_CIA = cCodGrupoCia_in
                                       AND C.COD_DESTINO_NOTA_ES = cCodDestinSAP
                                       AND C.EST_NOTA_ES_CAB = 'L'
                                       AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
                                       AND C.COD_LOCAL = G.COD_LOCAL
                                       AND C.NUM_NOTA_ES = G.NUM_NOTA_ES
                                       AND G.NUM_GUIA_REM = S.NUM_GUIA_REM)
                                 AND FECHA_LOCAL IS NULL;
              
         -- ACTUALIZA GUIA PROCESADA                             
         update btlcero.cab_guia_cliente
         set   est_guia = 'REC'
         WHERE cia   = cCodCia_in
         and   cod_local = cCodLocal_in
         and   num_guia  = cNumGuia_in;
        end if;  
       
     end if;
     
     COMMIT;
 /* EXCEPTION 
  WHEN  OTHERS THEN 
   ROLLBACK;
      INT_ENVIA_CORREO_ERROR('001','010',
                              'ERROR EN ENVIAR TRANSFERENCIA DE BTL A FV : ',
                              'ERROR : '||cCodCia_in||'-'||cCodLocal_in||'-'||cNumGuia_in,
                              SUBSTR(SQLERRM, 1, 250));   */
  
  /*         farma_email.envia_correo(cSendorAddress_in => 'oracle@mifarma.com.pe',
                                     cReceiverAddress_in => 'emaquera, soportedba4',
                                     cSubject_in => 'LOCAL PTOVENTA_MATRIZ_TRANSF-FIN-BEGIN!!!',
                                     ctitulo_in => 'LOCAL PTOVENTA_MATRIZ_TRANSF',
                                     cmensaje_in => 'EXITOSO.Proceso realizado',
                                     cCCReceiverAddress_in => 'emaquera',
                                     cip_servidor => '10.18.1.17',
                                     cin_html => true);*/
                                     
  END;  
 /* ***********************************************************************************  */    
  PROCEDURE ACTUALIZA_TRANSF_RAC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cDestino_in IN CHAR)
  AS
  BEGIN
    --ACTUALIZA TRANSF
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_ONLINE',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = 'L'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_LOCAL > '600'
          AND NUM_NOTA_ES = cNumNotaEs_in
          AND COD_DESTINO_NOTA_ES= cDestino_in;          

    UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_ONLINE',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = 'L'
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND COD_LOCAL > '600'
          AND NUM_NOTA_ES = cNumNotaEs_in
          AND COD_DESTINO_NOTA_ES= cDestino_in;
          
     COMMIT;
  END;
--**************************************************************************************************************/ 

end PTOVENTA_TRANSF2;

/
