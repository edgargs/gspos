CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_TRANSF" is

  -- Author  : ERIOS
  -- Created : 12/09/2006 10:09:58 a.m.
  -- Purpose : TRANSFERENCIAS AUTOMATICAS

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Ejecuta el procedimiento de transferencias entre locales.
  --Fecha       Usuario	  Comentario
  --24/07/2006  ERIOS     CreaciÃ³n
  PROCEDURE EJECT_TRANSFERENCIAS(cCodGrupoCia_in IN CHAR);

  --Descripcion: Obtiene las transferencias de los locales.
  --Fecha       Usuario	  Comentario
  --24/07/2006  ERIOS     CreaciÃ³n
  PROCEDURE GET_ORIGEN_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Borra una transferencia de la tabla temporal.
  --Fecha       Usuario	  Comentario
  --24/07/2006  ERIOS     CreaciÃ³n
  PROCEDURE BORRA_TRANSF_TEMPORAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR);

  --Descripcion: Actualiza estado de las transferencias de origen.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     CreaciÃ³n
  PROCEDURE ACTUALIZA_EST_TRANSF_ORIGEN(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Envia las transferencias a los locales.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     CreaciÃ³n
  PROCEDURE SET_DESTINO_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Actualiza la transferencia original en el local de origen.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     CreaciÃ³n
  PROCEDURE ACTUALIZA_TRANSF_ORIGINAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumNotaEs_in IN CHAR,cEstNotaEs_in IN CHAR);

  --Descripcion: Obtiene las transferencias recibidas.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     CreaciÃ³n
  FUNCTION LISTA_TRANSF_LOCAL(cGrupoCia_in IN CHAR, cCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la cabacera de una transferencia recibida.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     CreaciÃ³n
  FUNCTION GET_CAB_TRANSF_LOCAL(cCodGrupoCia_in IN CHAR,cCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cCodLocalOrigen_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de una transferencia recibida.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     CreaciÃ³n
  FUNCTION GET_DET_TRANSF_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,cNumNota_in IN CHAR,cCodLocalOrigen_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Genera guia de ingreso en el local.
  --Fecha       Usuario	  Comentario
  --25/07/2006  ERIOS     CreaciÃ³n
  PROCEDURE GENERAR_GUIA_INGRESO_LOCAL(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                cCodLocalOrigen_in IN CHAR,cNumNota_in IN CHAR,
                                vIdUsu_in IN CHAR);

  --Descripcion: Envia correo de alerta.
  --Fecha       Usuario	  Comentario
  --26/07/2006  ERIOS     Creación
  PROCEDURE INT_ENVIA_CORREO_ERROR(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);

  --Descripcion: Obtiene la fracción de un producto para un local.
  --Fecha       Usuario	  Comentario
  --07/09/2006  ERIOS     Creación
  PROCEDURE GET_FRACCION_LOCAL(cCodGrupoCia_in 	   IN CHAR,
                              cCodLocal_in    	   IN CHAR,
                              cCodProd_in   IN CHAR,
                              nCantMov_in IN NUMBER,
                              nValFrac_in IN NUMBER,
                              cInd_out IN OUT CHAR);

  --Descripcion: Obtiene la fracción de un producto para un local.
  --Fecha       Usuario	  Comentario
  --07/09/2008  JCALLO    Creación
  PROCEDURE TRANSF_P_ACTUALIZAR_ESTADO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       cEstado_in      IN CHAR,
                                       vIdUsu_in       IN CHAR);

  --Descripcion: Inserta nuevo lote
  --Fecha       Usuario	  Comentario
  --14/04/2010  ASOSA    Creación
  PROCEDURE TRANS_P_INS_LOTE(cCodCia_in IN CHAR,
                             cCodProd_in IN CHAR,
                             vNumLote_in IN VARCHAR2,
                             dFecVenc_in IN VARCHAR2 DEFAULT NULL,
                             cCodLocal_in IN CHAR,
                             vUsu_in IN VARCHAR2);

  --Descripcion: Inserta nuevo lote
  --Fecha       Usuario	  Comentario
  --15/04/2010  ASOSA    Creación
  FUNCTION TRANS_F_GET_IND_LOTE
  RETURN CHAR;

  --Descripcion: Elimina un lote verificando si corresponde o no
  --Fecha       Usuario	  Comentario
  --15/04/2010  ASOSA    Creación
  FUNCTION TRANS_P_DEL_LOTE(cCodCia_in IN CHAR,
                             cCodProd_in IN CHAR,
                             vNumLote_in IN VARCHAR2,
                             cCodLocal_in IN CHAR,
                             vUsu_in IN VARCHAR2)
  RETURN VARCHAR2;

end PTOVENTA_TRANSF;
/

CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_TRANSF" is

  PROCEDURE EJECT_TRANSFERENCIAS(cCodGrupoCia_in IN CHAR)
  AS
    CURSOR curLocales IS
    SELECT COD_LOCAL
    FROM PBL_LOCAL P
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND EST_LOCAL = 'A'
          AND TIP_LOCAL = 'V'
          and P.IND_EN_LINEA='S'
          and p.est_config_local in ('2','3')
    ORDER BY 1;
    v_rCurLocales curLocales%ROWTYPE;
  BEGIN
    FOR v_rCurLocales IN curLocales
    LOOP
      GET_ORIGEN_TRANSFERENCIA(cCodGrupoCia_in,v_rCurLocales.COD_LOCAL);
    END LOOP;

    FOR v_rCurLocales IN curLocales
    LOOP
      SET_DESTINO_TRANSFERENCIA(cCodGrupoCia_in,v_rCurLocales.COD_LOCAL);
    END LOOP;
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
    FROM T_LGT_NOTA_ES_CAB@XE_'||cCodLocal_in||'
     WHERE COD_GRUPO_CIA = :1
          AND COD_LOCAL = :2
          AND EST_NOTA_ES_CAB = :3 ' USING cCodGrupoCia_in,cCodLocal_in,'C';

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
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'C';

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
          AND C.COD_GRUPO_CIA = D.COD_GRUPO_CIA
          AND C.COD_LOCAL = D.COD_LOCAL
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'C';
    --ACTUALIZA ESTADO EN LOCAL ORIGEN
    EXECUTE IMMEDIATE 'BEGIN PTOVENTA_TRANSF.ACTUALIZA_EST_TRANSF_ORIGEN@XE_'||cCodLocal_in||'(:1,:2); END;' USING cCodGrupoCia_in,cCodLocal_in;

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
  PROCEDURE SET_DESTINO_TRANSFERENCIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
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
    SELECT COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,
    FEC_NOTA_ES_CAB,''L'',TIP_DOC,NUM_DOC,COD_ORIGEN_NOTA_ES,
    CANT_ITEMS,VAL_TOTAL_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,DESC_EMPRESA,
    RUC_EMPRESA,DIR_EMPRESA,DESC_TRANS,RUC_TRANS,DIR_TRANS,PLACA_TRANS,
    TIP_NOTA_ES,TIP_ORIGEN_NOTA_ES,TIP_MOT_NOTA_ES,EST_RECEPCION,
    USU_CREA_NOTA_ES_CAB,FEC_CREA_NOTA_ES_CAB,USU_MOD_NOTA_ES_CAB,FEC_MOD_NOTA_ES_CAB	,
    IND_NOTA_IMPRESA,FEC_PROCESO_SAP
    FROM T_LGT_NOTA_ES_CAB
    WHERE COD_GRUPO_CIA = :1
          AND COD_DESTINO_NOTA_ES = :2
          AND EST_NOTA_ES_CAB = :3 ' USING cCodGrupoCia_in,cCodLocal_in,'M';
    --DET
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_NOTA_ES_DET@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_DET_NOTA_ES,
    COD_PROD,SEC_GUIA_REM,VAL_PREC_UNIT,VAL_PREC_TOTAL,CANT_MOV,VAL_FRAC,EST_NOTA_ES_DET,
    FEC_NOTA_ES_DET,DESC_UNID_VTA,FEC_VCTO_PROD,NUM_LOTE_PROD,CANT_ENVIADA_MATR,
    NUM_PAG_RECEP,IND_PROD_AFEC,USU_CREA_NOTA_ES_DET,FEC_CREA_NOTA_ES_DET,USU_MOD_NOTA_ES_DET,
    FEC_MOD_NOTA_ES_DET,FEC_PROCESO_SAP,POSICION,NUM_ENTREGA)
    SELECT D.COD_GRUPO_CIA,D.COD_LOCAL,D.NUM_NOTA_ES,D.SEC_DET_NOTA_ES,
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
          AND C.NUM_NOTA_ES = D.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M';
    --GUIA
    EXECUTE IMMEDIATE 'INSERT INTO T_LGT_GUIA_REM@XE_'||cCodLocal_in||'(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES,SEC_GUIA_REM,
    NUM_GUIA_REM,FEC_CREA_GUIA_REM,USU_CREA_GUIA_REM,FEC_MOD_GUIA_REM,USU_MOD_GUIA_REM,
    EST_GUIA_REM,NUM_ENTREGA,IND_GUIA_CERRADA,IND_GUIA_IMPRESA)
    SELECT G.COD_GRUPO_CIA,G.COD_LOCAL,G.NUM_NOTA_ES,G.SEC_GUIA_REM,
    G.NUM_GUIA_REM,G.FEC_CREA_GUIA_REM,G.USU_CREA_GUIA_REM,G.FEC_MOD_GUIA_REM,G.USU_MOD_GUIA_REM,
    G.EST_GUIA_REM,G.NUM_ENTREGA,G.IND_GUIA_CERRADA,G.IND_GUIA_IMPRESA
    FROM T_LGT_NOTA_ES_CAB C,
          T_LGT_GUIA_REM G
    WHERE C.COD_GRUPO_CIA = :1
          AND C.COD_DESTINO_NOTA_ES = :2
          AND C.EST_NOTA_ES_CAB = :3
          AND C.COD_GRUPO_CIA = G.COD_GRUPO_CIA
          AND C.COD_LOCAL = G.COD_LOCAL
          AND C.NUM_NOTA_ES = G.NUM_NOTA_ES' USING cCodGrupoCia_in,cCodLocal_in,'M';
    --ACTUALIZA ESTADO
    v_vSentencia := 'SELECT COD_LOCAL,NUM_NOTA_ES,''L''
                      FROM T_LGT_NOTA_ES_CAB
                      WHERE COD_GRUPO_CIA = :1
                            AND COD_DESTINO_NOTA_ES = :2
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

    v_CantidadDetalle INTEGER;
  BEGIN

    -- KMONCADA 02.07.2014 VERIFICAMOS EXISTENCIA DEL DETALLE DEL DOCUMENTO DE TRANSFERENCIA
    BEGIN
       SELECT COUNT(1)
       INTO   v_CantidadDetalle
       FROM   T_LGT_NOTA_ES_DET
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocalOrigen_in
       AND    NUM_NOTA_ES = cNumNota_in;

       IF v_CantidadDetalle = 0 THEN
          RAISE_APPLICATION_ERROR(-20081,'NO EXISTE DETALLE DE TRANSFERENCIA, VERIFIQUE!!! ');
       END IF;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
           raise_application_error(-20081,'NO EXISTE DETALLE DE TRANSFERENCIA, VERIFIQUE!!! ');
    END;

    -- KMONCADA 02.07.2014 VERIFICAMOS EXISTENCIA DE GUIA DE REMISION PARA EL LOCAL
    BEGIN
       SELECT COUNT(1)
       INTO   v_CantidadDetalle
       FROM   T_LGT_NOTA_ES_CAB A
              , T_LGT_GUIA_REM B
       WHERE  A.COD_GRUPO_CIA=B.cod_grupo_cia
       AND    A.COD_LOCAL=B.COD_LOCAL
       AND    A.NUM_NOTA_ES=B.NUM_NOTA_ES
       AND    A.COD_DESTINO_NOTA_ES=cCodLocal_in;

       IF v_CantidadDetalle = 0 THEN
          RAISE_APPLICATION_ERROR(-20081,'NO EXISTE GUIA DE REMISION PARA LA TRANSFERENCIA');
       END IF;

       -- VALIDAMOS QUE LA GUIA DE REMISION NO SE HALLA USADO ANTES EN EL LOCAL.
       SELECT COUNT(1)
       INTO   v_CantidadDetalle
       FROM   T_LGT_NOTA_ES_CAB C,
       T_LGT_GUIA_REM B,
       LGT_GUIA_REM A
       WHERE  C.COD_GRUPO_CIA=B.COD_GRUPO_CIA
       AND C.COD_LOCAL=B.COD_LOCAL
       AND C.NUM_NOTA_ES = B.NUM_NOTA_ES
       AND C.COD_GRUPO_CIA = cCodGrupoCia_in
       AND C.COD_DESTINO_NOTA_ES = cCodLocal_in
       AND C.COD_LOCAL = cCodLocalOrigen_in
       AND C.NUM_NOTA_ES = cNumNota_in
       AND B.NUM_GUIA_REM =A.NUM_GUIA_REM;

       IF v_CantidadDetalle > 0 THEN
          RAISE_APPLICATION_ERROR(-20081,'GUIA DE REMISION YA SE UTILIZO ANTERIORMENTE');
       END IF;

    EXCEPTION
       WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20081,'NO EXISTE GUIA DE REMISION PARA LA TRANSFERENCIA');

    END;

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

    /****************************************************************************/
  PROCEDURE TRANSF_P_ACTUALIZAR_ESTADO(cCodGrupoCia_in IN CHAR,
                                       cCodLocal_in    IN CHAR,
                                       cNumNota_in     IN CHAR,
                                       cEstado_in      IN CHAR,
                                       vIdUsu_in       IN CHAR)
  AS
  BEGIN
    UPDATE LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in,FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = cEstado_in
    WHERE COD_GRUPO_CIA   = cCodGrupoCia_in
          AND COD_LOCAL   = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    UPDATE T_LGT_NOTA_ES_CAB SET USU_MOD_NOTA_ES_CAB = vIdUsu_in,FEC_MOD_NOTA_ES_CAB = SYSDATE,
          EST_NOTA_ES_CAB = cEstado_in
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;
  END;

  /****************************************************************************/

  PROCEDURE TRANS_P_INS_LOTE(cCodCia_in IN CHAR,
                             cCodProd_in IN CHAR,
                             vNumLote_in IN VARCHAR2,
                             dFecVenc_in IN VARCHAR2 DEFAULT NULL,
                             cCodLocal_in IN CHAR,
                             vUsu_in IN VARCHAR2)
  AS
  vIp VARCHAR2(15):='';
  BEGIN
       SELECT SYS_CONTEXT ('USERENV', 'IP_ADDRESS') INTO vIp  FROM dual;
       INSERT INTO LGT_MAE_LOTE_PROD(COD_GRUPO_CIA,
          COD_PROD,
          NUM_LOTE_PROD,
          FEC_VENC_LOTE,
          FEC_CREA_NUM_LOTE,
          USU_CREA_NUM_LOTE,
          COD_LOCAL_INS,
          IP_INS,
          USU_INS,
          FEC_INS_LOTE)
       VALUES(cCodCia_in,
              cCodProd_in,
              vNumLote_in,
              nvl2(dFecVenc_in,to_date(dFecVenc_in,'dd/MM/yyyy'),NULL),
              SYSDATE,
              'SP_INS_LOTE',
              cCodLocal_in,
              vIp,
              vUsu_in,
              SYSDATE);
  END;

  /****************************************************************************/

  FUNCTION TRANS_F_GET_IND_LOTE
  RETURN CHAR
  IS
  indlote CHAR(1):='';
  BEGIN
    SELECT nvl(a.llave_tab_gral,' ') INTO indlote
    FROM pbl_tab_gral a
    WHERE a.id_tab_gral='354';
    RETURN indlote;
  EXCEPTION
           WHEN NO_DATA_FOUND THEN
           raise_application_error(20000,'No hay indicador de ingreso lotes');
  END;

  /****************************************************************************/

  FUNCTION TRANS_P_DEL_LOTE(cCodCia_in IN CHAR,
                             cCodProd_in IN CHAR,
                             vNumLote_in IN VARCHAR2,
                             cCodLocal_in IN CHAR,
                             vUsu_in IN VARCHAR2)
  RETURN VARCHAR2
  AS
  nroDias NUMBER(8);
  cant01 NUMBER(8);
  cant02 NUMBER(8);
  msg VARCHAR2(200);
  BEGIN
       SELECT to_number(a.llave_tab_gral,'999,999,990') INTO nroDias
       FROM pbl_tab_gral a
       WHERE a.id_tab_gral='355';

       SELECT COUNT(1) INTO cant01
       FROM lgt_mae_lote_prod b
       WHERE b.cod_grupo_cia=cCodCia_in
       AND b.cod_prod=cCodProd_in
       AND b.num_lote_prod=vNumLote_in
       AND b.cod_local_ins=cCodLocal_in
       AND b.usu_ins=vUsu_in
       AND b.usu_crea_num_lote='SP_INS_LOTE';
       IF cant01=0 THEN
          msg:='Usted no agrego dicho lote en este local para este producto';
       ELSIF cant01=1 THEN
           SELECT COUNT(1) INTO cant02
           FROM lgt_mae_lote_prod c
           WHERE c.cod_grupo_cia=cCodCia_in
           AND c.cod_prod=cCodProd_in
           AND c.num_lote_prod=vNumLote_in
           AND c.cod_local_ins=cCodLocal_in
           AND trim(c.usu_ins)=vUsu_in
           AND c.usu_crea_num_lote='SP_INS_LOTE'
           AND trunc(c.fec_ins_lote+(nroDias-1))>=trunc(SYSDATE);
           IF cant02=0 THEN
              msg:='No puede elimnar un lote luego de '||nroDias||' dias contando el dia de ingreso';
           ELSIF cant02=1 THEN
               DELETE FROM lgt_mae_lote_prod d
               WHERE d.cod_grupo_cia=cCodCia_in
               AND d.cod_prod=cCodProd_in
               AND d.num_lote_prod=vNumLote_in
               AND d.cod_local_ins=cCodLocal_in
               AND TRIM(d.usu_ins)=vUsu_in
               AND d.usu_crea_num_lote='SP_INS_LOTE';
               msg:='Lote eliminado exitosamente';
           END IF;
       END if;
       RETURN msg;
  END;

  /****************************************************************************/

end PTOVENTA_TRANSF;
/

