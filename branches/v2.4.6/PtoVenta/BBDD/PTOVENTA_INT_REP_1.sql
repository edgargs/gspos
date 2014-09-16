--------------------------------------------------------
--  DDL for Package Body PTOVENTA_INT_REP
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."PTOVENTA_INT_REP" AS

  PROCEDURE PROCESA_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vUsu_in IN VARCHAR2)
  AS
    /*CURSOR curGuia IS
    SELECT DISTINCT NUM_GUIA_RECEP
    FROM INT_RECEP_PROD_QS
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_MOD_RECEP_PROD_QS IS NULL
    ORDER BY 1 ASC;*/
    CURSOR curGuia IS
    SELECT DISTINCT NUM_GUIA_RECEP
                    ,TIPO_PED_REP --COLOCA TIPO EN GUIA_REM DUBILLUZ  01.03.2009
    FROM INT_RECEP_PROD_QS
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_MOD_RECEP_PROD_QS IS NULL
          AND NUM_ENTREGA NOT IN (SELECT DISTINCT NUM_ENTREGA
                                  FROM INT_RECEP_PROD_QS I
                                        WHERE I.COD_GRUPO_CIA = cCodGrupoCia_in
                                              AND I.COD_LOCAL = cCodLocal_in
                                              AND I.FEC_MOD_RECEP_PROD_QS IS NULL
                                              --AND I.NUM_GUIA_RECEP = '0090003192'
                                              AND I.COD_PROD NOT IN (SELECT COD_PROD FROM LGT_PROD WHERE COD_GRUPO_CIA = cCodGrupoCia_in)
            )
    ORDER BY 1 ASC;

    v_rCurGuia curGuia%ROWTYPE;

    v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
    v_nSecGuia INTEGER := 0;
    err_msg VARCHAR2(250);
  BEGIN
    --GENERAR CABECERA
    --IF v_nSecGuia > 0 THEN
      v_nNumNota := GENERAR_CABECERA(cCodGrupoCia_in,cCodLocal_in,vUsu_in);
    --END IF;

    FOR v_rCurGuia IN curGuia
    LOOP
      v_nSecGuia := v_nSecGuia+1;
      DBMS_OUTPUT.PUT_LINE(v_nSecGuia||'');
      --GENERAR GUIA REMISION
      GENERAR_GUIA_REMISION(
                           cCodGrupoCia_in,cCodLocal_in,v_nNumNota,v_rCurGuia.NUM_GUIA_RECEP,v_nSecGuia,vUsu_in,
                           v_rCurGuia.TIPO_PED_REP
                           );
      --GENERAR DETALLE
      GENERAR_DETALLE(cCodGrupoCia_in,cCodLocal_in,v_nNumNota,v_rCurGuia.NUM_GUIA_RECEP,v_nSecGuia,vUsu_in);
    END LOOP;

    --ACTUALIZAR CABECERA
    IF v_nSecGuia > 0 THEN
      ACTUALIZA_CABECERA(cCodGrupoCia_in,cCodLocal_in,v_nNumNota,vUsu_in);
    ELSE
      ROLLBACK;
    END IF;

      COMMIT;
      --ROLLBACK;

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      err_msg := SUBSTR(SQLERRM, 1, 250);
      DBMS_OUTPUT.PUT_LINE('HA OCURRIDO UN ERROR AL GENERAR LA INTERFACE. VERIFIQUE');
      VIAJ_ENVIA_CORREO_ALERTA(cCodGrupoCia_in,cCodLocal_in,'ERROR AL GENERAR GUIAS: ','ALERTA',err_msg);
      NULL;
  END;
  /****************************************************************************/
  FUNCTION GENERAR_CABECERA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            vUsu_in IN VARCHAR2)
  RETURN CHAR
  IS
    v_nNumNota LGT_NOTA_ES_CAB.NUM_NOTA_ES%TYPE;
  BEGIN

    v_nNumNota := Farma_Utility.COMPLETAR_CON_SIMBOLO(Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs),10,'0','I' );

    INSERT INTO LGT_NOTA_ES_CAB(COD_GRUPO_CIA,COD_LOCAL,NUM_NOTA_ES, TIP_ORIGEN_NOTA_ES,COD_ORIGEN_NOTA_ES,CANT_ITEMS, TIP_NOTA_ES,USU_CREA_NOTA_ES_CAB,COD_DESTINO_NOTA_ES,TIP_DOC)
    VALUES(cCodGrupoCia_in,cCodLocal_in,v_nNumNota, '02',g_cCodMatriz,1, g_cTipoNotaRecepcion,vUsu_in,cCodLocal_in,'03');

    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in,cCodLocal_in,g_cNumNotaEs, vUsu_in);

    RETURN v_nNumNota;
  END;

  /****************************************************************************/
  PROCEDURE GENERAR_GUIA_REMISION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cNumNota_in IN CHAR, cNumGuia_in IN CHAR,
                            nSecGuia_in IN NUMBER,vUsu_in IN VARCHAR2,
                                  vTipo_in IN VARCHAR2)
  AS
  BEGIN
    INSERT INTO LGT_GUIA_REM(COD_GRUPO_CIA,
                             COD_LOCAL,
                             NUM_NOTA_ES,
                             SEC_GUIA_REM,
                             NUM_GUIA_REM,
                             USU_CREA_GUIA_REM,
                             TIPO_PED_REP)
    VALUES(cCodGrupoCia_in,cCodLocal_in,cNumNota_in,nSecGuia_in,cNumGuia_in,vUsu_in,vTipo_in);
  END;
  /****************************************************************************/
  PROCEDURE GENERAR_DETALLE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            cNumNota_in IN CHAR, cNumGuia_in IN CHAR,
                            nSecGuia_in IN NUMBER, vUsu_in IN VARCHAR2)
  AS
    CURSOR curPed IS
    SELECT *
    FROM INT_RECEP_PROD_QS
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_MOD_RECEP_PROD_QS IS NULL
          AND NUM_GUIA_RECEP = cNumGuia_in FOR UPDATE;
    v_rCurPed curPed%ROWTYPE;

    nSec LGT_NOTA_ES_DET.SEC_DET_NOTA_ES%TYPE;
    v_vDescUnidVta LGT_NOTA_ES_DET.DESC_UNID_VTA%TYPE;
    v_cNumEntrega INT_RECEP_PROD_QS.NUM_ENTREGA%TYPE;
  BEGIN
    SELECT COUNT(SEC_DET_NOTA_ES) INTO nSec
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    FOR v_rCurPed IN curPed
    LOOP
      nSec := nSec+1;
      SELECT DESC_UNID_PRESENT INTO v_vDescUnidVta
      FROM LGT_PROD
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_PROD = v_rCurPed.COD_PROD;

      INSERT INTO LGT_NOTA_ES_DET(COD_GRUPO_CIA,
                                  COD_LOCAl,
                                  NUM_NOTA_ES,
                                  SEC_DET_NOTA_ES,
                                  COD_PROD,
                                  SEC_GUIA_REM,
                                  CANT_MOV,
                                  FEC_VCTO_PROD,
                                  NUM_LOTE_PROD,
                                  CANT_ENVIADA_MATR,
                                  NUM_PAG_RECEP,
                                  USU_CREA_NOTA_ES_DET,
                                  VAL_FRAC,
                                  EST_NOTA_ES_DET,
                                  DESC_UNID_VTA,
                                  POSICION,
                                  NUM_ENTREGA)
      VALUES(cCodGrupoCia_in,
            cCodLocal_in,
            cNumNota_in,
            nSec,
            v_rCurPed.COD_PROD,
            nSecGuia_in,
            0,
            DECODE(v_rCurPed.FEC_VENC,'00000000',NULL,TO_DATE(v_rCurPed.FEC_VENC,'yyyyMMdd')),
            v_rCurPed.NUM_LOTE,
            v_rCurPed.CANT_SOLIC,
            nSecGuia_in,
            vUsu_in,
            1,
            'P',
            v_vDescUnidVta,
            v_rCurPed.POSICION,
            v_rCurPed.NUM_ENTREGA);

      v_cNumEntrega := v_rCurPed.NUM_ENTREGA;

      --ACTUALIZA EL MAESTRO DE LOTE
      --AGREGADO 18/05/2006
      --ACTUALIZA_MAE_LOTE(cCodGrupoCia_in,cCodLocal_in,v_rCurPed.COD_PROD,v_rCurPed.NUM_LOTE,v_rCurPed.FEC_VENC,vUsu_in);
    END LOOP;

    UPDATE INT_RECEP_PROD_QS
    SET FEC_MOD_RECEP_PROD_QS = SYSDATE

    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_GUIA_RECEP = cNumGuia_in;

    --ACTUALIZA NUM_ENTREGA EN GUIA
    UPDATE LGT_GUIA_REM
    SET NUM_ENTREGA = v_cNumEntrega,
		USU_MOD_GUIA_REM = vUsu_in,
                FEC_MOD_GUIA_REM = SYSDATE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in
          AND SEC_GUIA_REM = nSecGuia_in;
  END;
  /****************************************************************************/
  PROCEDURE ACTUALIZA_CABECERA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                                cNumNota_in IN CHAR, vUsu_in IN VARCHAR2)
  AS
    vCant LGT_NOTA_ES_CAB.CANT_ITEMS%TYPE;
  BEGIN
    SELECT COUNT(*) INTO vCant
    FROM LGT_NOTA_ES_DET
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;

    UPDATE LGT_NOTA_ES_CAB
    SET CANT_ITEMS = vCant,
        USU_MOD_NOTA_ES_CAB = vUsu_in,
        FEC_MOD_NOTA_ES_CAB = SYSDATE
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_NOTA_ES = cNumNota_in;
  END;
  /****************************************************************************/
  PROCEDURE ACTUALIZA_MAE_LOTE(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                                cCodProd_in IN CHAR,vNumLote_in IN VARCHAR2,dFecVec_in IN VARCHAR2,
                                vUsu_in IN CHAR)
  AS
  BEGIN
    BEGIN
      INSERT INTO LGT_MAE_LOTE_PROD(COD_GRUPO_CIA,
                                  --COD_LOCAL,
                                  COD_PROD,
                                  NUM_LOTE_PROD,
                                  FEC_VENC_LOTE,
                                  USU_CREA_NUM_LOTE)
      VALUES(cCodGrupoCia_in,
              --cCodLocal_in,
              cCodProd_in,
              vNumLote_in,
              DECODE(dFecVec_in,'00000000',NULL,TO_DATE(dFecVec_in,'yyyyMMdd')),
              vUsu_in);
    EXCEPTION
      WHEN DUP_VAL_ON_INDEX THEN
        IF dFecVec_in <> '00000000' THEN
          UPDATE LGT_MAE_LOTE_PROD
          SET FEC_VENC_LOTE = TO_DATE(dFecVec_in,'yyyyMMdd'),
              USU_MOD_NUM_LOTE	= vUsu_in,
              FEC_MOD_NUM_LOTE = SYSDATE
          WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                --AND COD_LOCAL = cCodLocal_in
                AND COD_PROD = cCodProd_in
                AND NUM_LOTE_PROD = vNumLote_in;
        END IF;
      WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR NO CONTROLADO EN MAE_LOTE');
        NULL;
    END;

  END;
  /****************************************************************************/

  PROCEDURE VIAJ_ENVIA_CORREO_ALERTA(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2)
  AS
    --EmailServer     VARCHAR2(30) := '192.168.0.236';
    --EmailServer     VARCHAR2(30) := '10.11.1.252';
    --SendorAddress  VARCHAR2(30)  := ' <oracle@mifarma.com.pe>';
    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTERFACE;
    CCReceiverAddress VARCHAR2(120) := NULL;

    mesg_body VARCHAR2(4000);

    v_vDescLocal VARCHAR2(120);
  BEGIN

    --DESCRIPCION DEL LOCAL
      SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;

    --CREAR CUERPO MENSAJE;
      mesg_body := '<LI> <B>' || 'LOCAL: '||v_vDescLocal	||CHR(9)||
                                       '</B><BR>'||
                                          '<I>ERROR: </I><BR>'||vMensaje_in	||
                                          '</LI>'  ;

    --ENVIA MAIL
      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                            ReceiverAddress,
                            vAsunto_in||v_vDescLocal,
                            vTitulo_in,
                            mesg_body,
                            CCReceiverAddress,
                            FARMA_EMAIL.GET_EMAIL_SERVER,
                            true);


  END;
  /****************************************************************************/

END;

/
