--------------------------------------------------------
--  DDL for Package Body PTOVENTA_MATRIZ_TRANSF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_MATRIZ_TRANSF" is



/****************************************************************************/
 
  FUNCTION TRANSF_F_CHAR_LLEVAR_DESTINO(cCodGrupoCia_in     IN CHAR,
                                       cCodLocalOrigen_in  IN CHAR,
                                       cCodLocalDestino_in IN CHAR,
                                       cNumNotaEs_in       IN CHAR,
                                       vIdUsu_in           IN CHAR)
  RETURN CHAR
  AS
    curTransf FarmaCursor;
    v_vSentencia VARCHAR2(32767);
    v_cCodLocal LGT_NOTA_ES_CAB.COD_LOCAL%TYPE;
    v_cNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_cEstNota LGT_NOTA_ES_CAB.EST_NOTA_ES_CAB%TYPE;

    v_cRespuesta CHAR(1):='N';
    isLocalMigrado number;
    ipLocalDestino varchar2(100);
  BEGIN
    begin
     EXECUTE IMMEDIATE 
	   'BEGIN   
	   INSERT INTO T_LGT_NOTA_ES_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES, '||
       'FEC_NOTA_ES_CAB,EST_NOTA_ES_CAB,TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES, '||
       'CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA, '||
       'RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS , '||
       'TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION, '||
       'USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	, '||
       'IND_NOTA_IMPRESA,FEC_PROCESO_SAP) '||
       'SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES, '||
       'FEC_NOTA_ES_CAB,''M'',TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES, '||
       'CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA, '||
       'RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS, '||
       'TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION, '||
       'USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	, '||
       'IND_NOTA_IMPRESA,FEC_PROCESO_SAP '||
       'FROM PTOVENTA.T_LGT_NOTA_ES_CAB@XE_'||cCodLocalOrigen_in||' C '||
       'WHERE COD_GRUPO_CIA = :1 '||
            'AND COD_LOCAL = :2 '||
           -- 'AND EST_NOTA_ES_CAB = :3 '||
            'AND NUM_NOTA_ES     = :3 ; '||
      'INSERT INTO T_LGT_GUIA_REM(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM, '||
      'NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM, '||
      'EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA) '||
      'SELECT G.COD_GRUPO_CIA,G.COD_LOCAL,G.NUM_NOTA_ES,G.SEC_GUIA_REM, '||
      'G.NUM_GUIA_REM,G.FEC_CREA_GUIA_REM,G.USU_CREA_GUIA_REM,G.FEC_MOD_GUIA_REM,G.USU_MOD_GUIA_REM, '||
      'G.EST_GUIA_REM,G.NUM_ENTREGA,G.IND_GUIA_CERRADA,G.IND_GUIA_IMPRESA '||
      'FROM PTOVENTA.T_LGT_NOTA_ES_CAB@XE_'||cCodLocalOrigen_in||' C, '||
            'PTOVENTA.T_LGT_GUIA_REM@XE_'||cCodLocalOrigen_in||' G '||
      'WHERE C.COD_GRUPO_CIA = :1 '||
           ' AND C.COD_LOCAL = :2 '||
           --' AND C.EST_NOTA_ES_CAB = :3 '||
           ' AND C.NUM_NOTA_ES       = :3 '||
           ' AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA '||
           ' AND C.COD_LOCAL = G.COD_LOCAL '||
           ' AND C.NUM_NOTA_ES = G.NUM_NOTA_ES; '||
      'INSERT INTO T_LGT_NOTA_ES_DET(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES, '||
      'COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET, '||
      'FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR, '||
      'NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET, '||
      'FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA) '||
      'SELECT D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_NOTA_ES,D.SEC_DET_NOTA_ES, '||
      'D.COD_PROD,D.SEC_GUIA_REM,D.VAL_PREC_UNIT,D.VAL_PREC_TOTAL,D.CANT_MOV,D.VAL_FRAC,D.EST_NOTA_ES_DET, '||
      'D.FEC_NOTA_ES_DET,D.DESC_UNID_VTA,D.FEC_VCTO_PROD,D.NUM_LOTE_PROD,D.CANT_ENVIADA_MATR, '||
      'D.NUM_PAG_RECEP,D.IND_PROD_AFEC,D.USU_CREA_NOTA_ES_DET,D.FEC_CREA_NOTA_ES_DET,D.USU_MOD_NOTA_ES_DET, '||
      'D.FEC_MOD_NOTA_ES_DET,D.FEC_PROCESO_SAP,D.POSICION,D.NUM_ENTREGA '||
      'FROM PTOVENTA.T_LGT_NOTA_ES_CAB@XE_'||cCodLocalOrigen_in||' C, '||
      '     PTOVENTA.T_LGT_NOTA_ES_DET@XE_'||cCodLocalOrigen_in||' D '||
      'WHERE C.COD_GRUPO_CIA = :1 '||
            'AND C.COD_LOCAL = :2 '||
            --'AND C.EST_NOTA_ES_CAB = :3 '||
            'AND C.NUM_NOTA_ES     = :3 '||
            'AND C.COD_GRUPO_CIA   = D.COD_GRUPO_CIA '||
            'AND C.COD_LOCAL       = D.COD_LOCAL '||
            'AND C.NUM_NOTA_ES     = D.NUM_NOTA_ES;  '||
     'end;'       
		USING cCodGrupoCia_in,cCodLocalOrigen_in,cNumNotaEs_in;            

		--ERIOS 01.08.2014 Graba registro de transferencias
		PKG_DAEMON_TRANSF.GRABA_REG_TRANSFERENCIA(cCodGrupoCia_in,NULL,cCodLocalOrigen_in,cNumNotaEs_in);
		
    commit;
   exception
   when others then 
    rollback;
    dbms_output.put_line(''||sqlerrm);
   end;
    
    v_cRespuesta:='S';

       RETURN v_cRespuesta;
/*
  EXCEPTION
      WHEN OTHERS THEN
           v_cRespuesta:='N';
       RETURN v_cRespuesta;*/

  END;
  
 /* ***********************************************************************************  */
PROCEDURE TRANS_P_ANULAR_TRANSFERENCIA(cCodGrupoCia_in  IN CHAR,
                                       cCodLocal_in     IN CHAR,
                                       cNumNota_in      IN CHAR,
                                       cCodMotKardex_in IN CHAR,
                                       cTipDocKardex_in IN CHAR,
                                       vIdUsu_in        IN VARCHAR2)

 AS
  v_bTransferenciaAceptada BOOLEAN := false;
  v_vCodLocalDestino       LGT_NOTA_ES_CAB.COD_DESTINO_NOTA_ES%TYPE;

BEGIN
  dbms_output.put_line('inicio de anular');

  EXECUTE IMMEDIATE 'SELECT COD_DESTINO_NOTA_ES ' ||
                    ' FROM LGT_NOTA_ES_CAB@XE_' || cCodLocal_in ||
                    ' WHERE COD_GRUPO_CIA = :1 ' ||
                    ' AND COD_LOCAL       = :2 ' ||
                    ' AND NUM_NOTA_ES     = :3 '
    INTO v_vCodLocalDestino
    USING cCodGrupoCia_in, cCodLocal_in, cNumNota_in;

  v_bTransferenciaAceptada := TRANSF_F_BOOL_VER_TRANS_ACEP(cCodGrupoCia_in,
                                                           cCodLocal_in,
                                                           cNumNota_in,
                                                           v_vCodLocalDestino);

  IF v_bTransferenciaAceptada THEN
    --verifica si la transferencia ya fue aceptada en el destino
    RAISE_APPLICATION_ERROR(-20000,
                            'La transferencia ya ha sido aceptada por el local destino.');
  ELSE
    TRANSF_P_ELIM_TRANSF_ENLOCDEST(cCodGrupoCia_in,
                                   cCodLocal_in,
                                   cNumNota_in,
                                   v_vCodLocalDestino);
    TRANSF_P_ELIM_TRANSF_EN_MATRIZ(cCodGrupoCia_in,
                                   cCodLocal_in,
                                   cNumNota_in,
                                   v_vCodLocalDestino);
    /*PTOVENTA_INV.INV_ANULA_TRANSFERENCIA(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,
    cCodMotKardex_in, cTipDocKardex_in,
    vIdUsu_in);     */
  END IF;

END;
/****************************************************************************************************/
FUNCTION TRANSF_F_BOOL_VER_TRANS_ACEP(cGrupoCia_in      IN CHAR,
                                      cCodLocal_origen  IN CHAR,
                                      cNumNotaEs_in     IN CHAR,
                                      cCodLocal_destino IN CHAR)
  RETURN BOOLEAN IS

  v_nCantTransAceptada NUMBER := 0;
  v_bResultado         BOOLEAN := FALSE;
BEGIN
  dbms_output.put_line('inicio de  verificar para anular');

  EXECUTE IMMEDIATE ' SELECT COUNT(*) ' || ' FROM T_LGT_NOTA_ES_CAB@XE_' ||
                    cCodLocal_destino || ' WHERE COD_GRUPO_CIA = :1 ' ||
                    ' AND COD_LOCAL       = :2 ' ||
                    ' AND NUM_NOTA_ES     = :3 ' ||
                    ' AND COD_DESTINO_NOTA_ES = :4 ' ||
                    ' AND EST_NOTA_ES_CAB =''A'' '
    INTO v_nCantTransAceptada
    USING cGrupoCia_in, cCodLocal_origen, cNumNotaEs_in, cCodLocal_destino;

  IF (v_nCantTransAceptada > 0) THEN
    v_bResultado := TRUE;
  ELSE
    v_bResultado := FALSE;
  END IF;

  RETURN v_bResultado;

END;
/**************************************************************************************************************/
PROCEDURE TRANSF_P_ELIM_TRANSF_ENLOCDEST(cGrupoCia_in      IN CHAR,
                                         cCodLocal_in      IN CHAR,
                                         cNumNotaEs_in     IN CHAR,
                                         cCodLocal_destino IN CHAR) AS
BEGIN
  dbms_output.put_line('Inicio de eliminacion de transfencia en elocal destino');

  EXECUTE IMMEDIATE ' DELETE T_LGT_GUIA_REM@XE_' || cCodLocal_destino ||
                    ' WHERE COD_GRUPO_CIA = :1 ' ||
                    ' AND   COD_LOCAL     = :2  ' ||
                    ' AND   NUM_NOTA_ES   = :3 '
    USING cGrupoCia_in, cCodLocal_in, cNumNotaEs_in;

  EXECUTE IMMEDIATE ' DELETE T_LGT_NOTA_ES_DET@XE_' || cCodLocal_destino ||
                    ' WHERE COD_GRUPO_CIA = :1 ' ||
                    ' AND   COD_LOCAL     = :2 ' ||
                    ' AND NUM_NOTA_ES     = :3 '
    USING cGrupoCia_in, cCodLocal_in, cNumNotaEs_in;

  EXECUTE IMMEDIATE 'DELETE T_LGT_NOTA_ES_CAB@XE_' || cCodLocal_destino ||
                    ' WHERE COD_GRUPO_CIA        = :1     ' ||
                    ' AND   COD_LOCAL            = :2    ' ||
                    ' AND   NUM_NOTA_ES          = :3    ' ||
                    ' AND   EST_NOTA_ES_CAB      = ''L'' ' ||
                    ' AND   COD_DESTINO_NOTA_ES  = :4 '
    USING cGrupoCia_in, cCodLocal_in, cNumNotaEs_in, cCodLocal_destino;
END;
/**************************************************************************************************************/
PROCEDURE TRANSF_P_ELIM_TRANSF_EN_MATRIZ(cGrupoCia_in      IN CHAR,
                                         cCodLocal_in      IN CHAR,
                                         cNumNotaEs_in     IN CHAR,
                                         cCodLocal_destino IN CHAR) AS
BEGIN
  dbms_output.put_line('Inicio de eliminacion de transfencia en el local destino');
  DELETE T_LGT_GUIA_REM
   WHERE COD_GRUPO_CIA = cGrupoCia_in
     AND COD_LOCAL = cCodLocal_in
     AND NUM_NOTA_ES = cNumNotaEs_in;

  DELETE T_LGT_NOTA_ES_DET
   WHERE COD_GRUPO_CIA = cGrupoCia_in
     AND COD_LOCAL = cCodLocal_in
     AND NUM_NOTA_ES = cNumNotaEs_in;

  DELETE T_LGT_NOTA_ES_CAB
   WHERE COD_GRUPO_CIA = cGrupoCia_in
     AND COD_LOCAL = cCodLocal_in
     AND NUM_NOTA_ES = cNumNotaEs_in
     AND EST_NOTA_ES_CAB = 'L'
     AND COD_DESTINO_NOTA_ES = cCodLocal_destino;
END;
--**************************************************************************************************************/
  PROCEDURE ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cEstNotaEs_in IN CHAR)
  AS
  BEGIN
    --ACTUALIZA TRANSF
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_BTL_FASA',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;

    UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_BTL_FASA',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;
  END;
/* ************************************************************************************ */



    PROCEDURE LLEVAR_DESTINO_BV(cCodGrupoCia_in     IN CHAR,
                                       cCodLocalOrigen_in  IN CHAR,
                                       cCodLocalDestino_in IN CHAR,
                                       cNumNotaEs_in       IN CHAR,
                                       vIdUsu_in           IN CHAR)
    IS
	
		
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
    AND C.NUM_NOTA_ES = G.NUM_NOTA_ES
    --AND C.EST_NOTA_ES_CAB = 'L'
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
		v_nExisteGuia integer;
	BEGIN
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
						  
			--ERIOS 05.08.2014 Verifica que no exista la guia.
			SELECT count(1)  INTO v_nExisteGuia
			FROM btlcero.cab_guia_cliente
			WHERE COD_BTL = cCodOrigenBTL
			   AND NUM_GUIA = CUR1.NUMERO_GUIA;
				   
			IF v_nExisteGuia = 0 THEN	   
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
             END IF;     
			  
                  
              commit;
              END LOOP;
              /*EXCEPTION
              WHEN OTHERS THEN
                ROLLBACK;*/
             END;
	END;
	
end PTOVENTA_MATRIZ_TRANSF;



/
