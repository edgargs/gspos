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
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_MIFARMA',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;

    UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = 'JOB_MIFARMA',FEC_MOD_NOTA_ES_CAB = SYSDATE,
           IND_TRANS_AUTOMATICA = 'S',
          EST_NOTA_ES_CAB = cEstNotaEs_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNotaEs_in;
  END;

end PTOVENTA_MATRIZ_TRANSF;



/
