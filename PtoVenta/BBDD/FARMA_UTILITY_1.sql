--------------------------------------------------------
--  DDL for Package Body FARMA_UTILITY
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "PTOVENTA"."FARMA_UTILITY" IS


  /**
  * Copyright (c) 2006 MiFarma Peru S.A.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_UTILITY
  *
  * Histórico de Creación/Modificación
  * RCASTRO       15.01.2006   Creación
  *
  * @author Rolando Castro
  * @version 1.0
  *
  */

  PROCEDURE LIBERAR_TRANSACCION IS
  BEGIN
    ROLLBACK;
  END;

  PROCEDURE ACEPTAR_TRANSACCION IS
  BEGIN
    COMMIT;
  END;

  FUNCTION COMPLETAR_CON_SIMBOLO(nValor_in    IN NUMBER,
                                 iLongitud_in IN INTEGER,
                   cSimbolo_in  IN CHAR,
                   cUbica_in    IN CHAR)
           RETURN VARCHAR2 IS
    v_vNumeracion VARCHAR2(25);
  v_iCeros      INTEGER;
  BEGIN
    v_vNumeracion := '';
    v_iCeros := iLongitud_in - LENGTH(nValor_in);
  IF ( v_iCeros <= 0 ) THEN
    RETURN TO_CHAR(nValor_in);
  ELSE
      FOR longitud IN 1 .. v_iCeros LOOP
      v_vNumeracion := v_vNumeracion || cSimbolo_in;
    END LOOP;
    IF ( cUbica_in = 'I' ) THEN
      v_vNumeracion := v_vNumeracion || TO_CHAR(nValor_in);
    ELSE
      v_vNumeracion := TO_CHAR(nValor_in) || v_vNumeracion;
    END IF;
    RETURN v_vNumeracion;
  END IF;
  END;


  /* ************************************************************************ */


  FUNCTION OBTENER_REDONDEO(nValor_in IN NUMBER,
                            cTipo_in  IN CHAR)
           RETURN NUMBER IS
    v_nRedondeo NUMBER(3,2);
  v_vTempoc   VARCHAR2(20);
  v_nTempon   NUMBER(1);
  BEGIN
    v_vTempoc := TRIM(TO_CHAR(nValor_in,'999,990.00'));
    v_nTempon := TO_NUMBER(SUBSTR(v_vTempoc,LENGTH(v_vTempoc),1));
  IF ( v_nTempon >= 5 ) THEN
    v_nRedondeo := 0.01*(10-v_nTempon);
  ELSE
    v_nRedondeo := -1*0.01*v_nTempon;
  END IF;
  IF ( cTipo_in = 'R' ) THEN
    RETURN v_nRedondeo;
  ELSE
    RETURN (nValor_in+v_nRedondeo);
  END IF;
  END;

  /* ************************************************************************ */

  FUNCTION OBTENER_NUMERACION(cCodGrupoCia_in   IN CHAR,
                cCodLocal_in     IN CHAR,
                cCodNumera_in    IN CHAR)
  RETURN NUMBER
  IS
    v_nNumero  NUMBER;
  BEGIN
       SELECT NUM.VAL_NUMERA
    INTO   v_nNumero
    FROM   PBL_NUMERA NUM
    WHERE  NUM.COD_GRUPO_CIA = cCodGrupoCia_in
    AND     NUM.COD_LOCAL = cCodLocal_in
    AND    NUM.COD_NUMERA = cCodNumera_in FOR UPDATE;
    IF ( v_nNumero IS NULL OR v_nNumero=0 ) THEN
      v_nNumero := 1;
    END IF;
  RETURN v_nNumero;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
    RETURN 0;
  END;

  /* ************************************************************************ */

  PROCEDURE ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cCodNumera_in     IN CHAR,
                         vIdUsuario_in     IN VARCHAR2) IS
  BEGIN
    UPDATE PBL_NUMERA
       SET VAL_NUMERA = NVL(VAL_NUMERA ,1) + 1,
         USU_MOD_NUMERA = vIdUsuario_in,
       FEC_MOD_NUMERA = SYSDATE
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
   AND   COD_LOCAL = cCodLocal_in
   AND   COD_NUMERA = cCodNumera_in;
  END;

  /* ************************************************************************ */

  PROCEDURE INICIALIZA_NUMERA_SIN_COMMIT(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cCodNumera_in     IN CHAR,
                         vIdUsuario_in     IN VARCHAR2) IS
  BEGIN
    UPDATE PBL_NUMERA
       SET VAL_NUMERA = 1,
         USU_MOD_NUMERA = vIdUsuario_in,
       FEC_MOD_NUMERA = SYSDATE
   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
   AND   COD_LOCAL = cCodLocal_in
   AND   COD_NUMERA = cCodNumera_in;

  END;

  /* ************************************************************************ */

  FUNCTION OBTIENE_TIPO_CAMBIO(cCodGrupoCia_in IN CHAR,
                    cFecCambio_in   IN CHAR)
           RETURN NUMBER IS
    v_nValorTipCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE;
    CURSOR curUtility IS
               SELECT VAL_TIPO_CAMBIO
                FROM    CE_TIP_CAMBIO
                WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
            AND   FEC_INI_VIG <= DECODE(cFecCambio_in,NULL,SYSDATE,TO_DATE((cFecCambio_in || ' 23:59:59'),'dd/MM/yyyy HH24:MI:SS'))
                ORDER BY FEC_INI_VIG DESC;
  BEGIN
       v_nValorTipCambio := 1.00;
     FOR tipocambio_rec IN curUtility
    LOOP
        v_nValorTipCambio := tipocambio_rec.VAL_TIPO_CAMBIO;
      EXIT;
    END LOOP;
    RETURN v_nValorTipCambio;
  END;

  /* ************************************************************************ */

  PROCEDURE EJECUTA_RESPALDO_STK(cCodGrupoCia_in    IN CHAR,
                                  cCodLocal_in       IN CHAR,
                                  cNumIpPc_in       IN CHAR,
                                  cCodProd_in        IN CHAR,
                                 cNumPedVta_in     IN CHAR,
                                 cTipoOperacion_in IN CHAR,
                                  nCantMov_in       IN NUMBER,
                                 nValFracc_in       IN NUMBER,
                                  vIdUsuario_in      IN VARCHAR2,
                                 cModulo_in        IN CHAR) IS
  BEGIN
  IF ( cTipoOperacion_in = 'S' ) THEN
    NULL;
  ELSIF ( cTipoOperacion_in = 'A' ) THEN
    NULL;
  ELSIF ( cTipoOperacion_in = 'P' ) THEN   -- ACTUALIZA PEDIDO
    NULL;
  ELSIF ( cTipoOperacion_in = 'B' ) THEN
    NULL;
  ELSIF ( cTipoOperacion_in = 'E' ) THEN   -- EJECUTA RECUPERACION STOCK PRODUCTO
      --RECUPERACION_RESPALDO_STK(cCodGrupoCia_in,cCodLocal_in,cNumIpPc_in,vIdUsuario_in);
      PTOVTA_RESPALDO_STK.RECUPERACION_RESPALDO_STK(cCodGrupoCia_in,cCodLocal_in,cNumIpPc_in,vIdUsuario_in); --ASOSA, 24.08.2010
  END IF;
  END;

  /* ************************************************************************ */

  PROCEDURE RECUPERACION_RESPALDO_STK(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumIpPc_in     IN CHAR,
                    vIdUsuario_in   IN VARCHAR2) IS

  BEGIN
    NULL;
  END;

  /* ************************************************************************ */

  FUNCTION VERIFICA_RUC_VALIDO(cNumRuc_in IN CHAR)
           RETURN CHAR IS
  numeroruc     CHAR(11);
    serie       CHAR(2);
  rucnumerico   NUMBER;

  suma     NUMBER;
  x        NUMBER;
  i        NUMBER;
  digito     NUMBER;
  resto     NUMBER;
  BEGIN
     numeroruc := LTRIM(RTRIM(cNumRuc_in));
     IF (LENGTH(numeroruc) <> 11) THEN
      RETURN 'FALSE';
   ELSE
      rucnumerico := TO_NUMBER(numeroruc);
     IF (rucnumerico IS NULL) THEN
       RETURN 'FALSE';
     ELSE
        serie := SUBSTR(numeroruc,1,2);
       IF (serie IN ('10','15','20','17')) THEN
          suma := 0;
          x := 6;
        FOR i IN  0..9 LOOP
              IF (i = 4 ) THEN
             x := 8;
          END IF;
              digito := TO_NUMBER(SUBSTR(numeroruc,i+1,1));
              x:=x-1;
              IF ( i=0 ) THEN
             suma := suma + (digito*x);
              ELSE
             suma := suma + (digito*x);
          END IF;
        END LOOP;
          resto := MOD(suma,11);
          resto := 11 - resto;

          IF ( resto >= 10) THEN
           resto := resto - 10;
        END IF;
          IF ( resto = TO_NUMBER(SUBSTR(numeroruc,11,1)) ) THEN
              RETURN 'TRUE';
        ELSE
             RETURN 'FALSE';
          END IF;
       ELSE
         RETURN 'FALSE';
       END IF;
     END IF;
   END IF;
  RETURN 'TRUE';
  EXCEPTION
    WHEN OTHERS THEN
    RETURN 'FALSE';
  END;

  /************************************************************************************/

  FUNCTION GENERA_PEDIDO_SCRIPT(cCodGrupoCia_in    IN CHAR,
        cCodLocal_in        IN CHAR,
        cNumPedVtaCopia_in IN CHAR,
        nDiasRestaFecha_in IN NUMBER)
    RETURN CHAR IS
    v_nNumPedVta VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
  v_nNumPedDiario VTA_PEDIDO_VTA_CAB.NUM_PED_DIARIO%TYPE;
    CURSOR curCabecera IS
                SELECT *
             FROM   VTA_PEDIDO_VTA_CAB VTA_CAB
             WHERE  VTA_CAB.COD_GRUPO_CIA = cCodGrupoCia_in
             AND    VTA_CAB.COD_LOCAL = cCodLocal_in
             AND    VTA_CAB.NUM_PED_VTA = cNumPedVtaCopia_in;

  CURSOR curDetalle IS
                SELECT *
             FROM   VTA_PEDIDO_VTA_DET VTA_DET
             WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
             AND    VTA_DET.COD_LOCAL = cCodLocal_in
             AND    VTA_DET.NUM_PED_VTA = cNumPedVtaCopia_in;

  BEGIN
       v_nNumPedVta := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, '007');
    v_nNumPedVta := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumPedVta, 10, 0, 'I');

    v_nNumPedDiario := Farma_Utility.OBTENER_NUMERACION(cCodGrupoCia_in, cCodLocal_in, '009');
    v_nNumPedDiario := Farma_Utility.COMPLETAR_CON_SIMBOLO(v_nNumPedDiario, 4, 0, 'I');

     FOR cabecera_rec IN curCabecera
    LOOP
        --dbms_output.put_line('1 Diario '|| v_nNumPedDiario);
        --dbms_output.put_line('1 Vta '|| v_nNumPedVta);
        Ptoventa_Vta.VTA_GRABAR_PEDIDO_VTA_CAB(cCodGrupoCia_in,
                             cCodLocal_in,
           v_nNumPedVta,
           cabecera_rec.COD_CLI_LOCAL,
           '',
           cabecera_rec.VAL_BRUTO_PED_VTA,
           cabecera_rec.VAL_NETO_PED_VTA,
           cabecera_rec.VAL_REDONDEO_PED_VTA,
           cabecera_rec.VAL_IGV_PED_VTA,
           cabecera_rec.VAL_DCTO_PED_VTA,
           cabecera_rec.TIP_PED_VTA,
           cabecera_rec.VAL_TIP_CAMBIO_PED_VTA,
           v_nNumPedDiario,
           cabecera_rec.CANT_ITEMS_PED_VTA,
           cabecera_rec.EST_PED_VTA,
           cabecera_rec.TIP_COMP_PAGO,
           cabecera_rec.NOM_CLI_PED_VTA,
           cabecera_rec.DIR_CLI_PED_VTA,
           cabecera_rec.RUC_CLI_PED_VTA,
           'JOLIVA',
           cabecera_rec.IND_DISTR_GRATUITA,
                           cabecera_rec.Ind_Ped_Convenio);

      UPDATE VTA_PEDIDO_VTA_CAB SET FEC_PED_VTA = SYSDATE - nDiasRestaFecha_in,
           FEC_CREA_PED_VTA_CAB = SYSDATE - nDiasRestaFecha_in
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND  COD_LOCAL = cCodLocal_in
      AND  NUM_PED_VTA = v_nNumPedVta;
    END LOOP;
    FOR detalle_rec IN curDetalle
    LOOP

        --dbms_output.put_line('2');
        Ptoventa_Vta.VTA_GRABAR_PEDIDO_VTA_DET(cCodGrupoCia_in,
                                       cCodLocal_in,
               v_nNumPedVta,
               detalle_rec.SEC_PED_VTA_DET,
               detalle_rec.COD_PROD,
               detalle_rec.CANT_ATENDIDA,
               detalle_rec.VAL_PREC_VTA,
               detalle_rec.VAL_PREC_TOTAL,
               detalle_rec.PORC_DCTO_1,
               detalle_rec.PORC_DCTO_2,
               detalle_rec.PORC_DCTO_3,
               detalle_rec.PORC_DCTO_TOTAL,
               detalle_rec.EST_PED_VTA_DET,
               detalle_rec.VAL_TOTAL_BONO,
               detalle_rec.VAL_FRAC,
               '',
               detalle_rec.SEC_USU_LOCAL,
               detalle_rec.VAL_PREC_LISTA,
               detalle_rec.VAL_IGV,
               detalle_rec.UNID_VTA,
               '',
               'JOLIVA',
                                 detalle_rec.val_prec_public,
                                NULL,
                                NULL,
                                NULL,
                                NULL);
      UPDATE VTA_PEDIDO_VTA_DET SET FEC_CREA_PED_VTA_DET = SYSDATE - nDiasRestaFecha_in
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
      AND  COD_LOCAL = cCodLocal_in
      AND  NUM_PED_VTA = v_nNumPedVta;
    END LOOP;

    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, '007', 'JOLIVA');
    Farma_Utility.ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, '009', 'JOLIVA');

    IF ( v_nNumPedDiario = '9999' ) THEN
       Farma_Utility.INICIALIZA_NUMERA_SIN_COMMIT(cCodGrupoCia_in, cCodLocal_in, '009', 'JOLIVA');
    END IF;

    RETURN v_nNumPedVta;
  END;

FUNCTION COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in     IN CHAR,
                    cCodLocal_in        IN CHAR,
                 cNumPedVta_in       IN CHAR,
                 cNumPedVtaCopia_in IN CHAR,
                 cSecMovCaja_in    IN CHAR,
                 nDiasRestaFecha_in IN NUMBER)
    RETURN CHAR IS

  v_nSecGrupoImpr NUMBER;
  v_cNumComp     CHAR(10);
  v_cSecComp     CHAR(10);

  v_Res CHAR(10);
    CURSOR curFormaPago IS
               SELECT *
            FROM   VTA_FORMA_PAGO_PEDIDO PAGO_PEDIDO
            WHERE  PAGO_PEDIDO.COD_GRUPO_CIA = cCodGrupoCia_in
            AND     PAGO_PEDIDO.COD_LOCAL = cCodLocal_in
            AND     PAGO_PEDIDO.NUM_PED_VTA = cNumPedVtaCopia_in;

  BEGIN
       --dbms_output.put_line('3 ' || cNumPedVta_in);
    v_nSecGrupoImpr := Ptoventa_Caj.CAJ_AGRUPA_IMPRESION_DETALLE(cCodGrupoCia_in,
                                          cCodLocal_in,
                                   cNumPedVta_in,
                                   8,
                                   'OPER');

     FOR formapago_rec IN curFormaPago
    LOOP
        --dbms_output.put_line('4');
        Ptoventa_Caj.CAJ_GRABAR_FORMA_PAGO_PEDIDO(cCodGrupoCia_in,
                                                   cCodLocal_in,
                                                  formapago_rec.COD_FORMA_PAGO,
                                                   cNumPedVta_in,
                                                   formapago_rec.IM_PAGO,
                                                  formapago_rec.TIP_MONEDA,
                                                  formapago_rec.VAL_TIP_CAMBIO,
                                                   formapago_rec.VAL_VUELTO,
                                                   formapago_rec.IM_TOTAL_PAGO,
                                                  formapago_rec.NUM_TARJ,
                                                  formapago_rec.FEC_VENC_TARJ,
                                                  formapago_rec.NOM_TARJ,
                                                  formapago_rec.CANT_CUPON,
                                                  'JOLIVA');
      UPDATE VTA_FORMA_PAGO_PEDIDO SET FEC_CREA_FORMA_PAGO_PED = SYSDATE - nDiasRestaFecha_in
                     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                  AND  COD_LOCAL = cCodLocal_in
                  AND  COD_FORMA_PAGO = formapago_rec.COD_FORMA_PAGO
                  AND  NUM_PED_VTA = cNumPedVta_in;
    END LOOP;
    --dbms_output.put_line('5');
    v_Res := Ptoventa_Caj.CAJ_COBRA_PEDIDO(cCodGrupoCia_in,
                     cCodLocal_in,
                  cNumPedVta_in,
                  cSecMovCaja_in,
                  '015',
                  '01',
                  '001',
                     '01',
                  '016',
                  'JOLIVA');
     UPDATE VTA_COMP_PAGO SET FEC_CREA_COMP_PAGO = SYSDATE - nDiasRestaFecha_in
                     WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                  AND  COD_LOCAL = cCodLocal_in
                  AND  NUM_PED_VTA = cNumPedVta_in;

     SELECT NVL(VTA_DET.SEC_COMP_PAGO,' ')
     INTO    v_cSecComp
     FROM   VTA_PEDIDO_VTA_DET VTA_DET
     WHERE  VTA_DET.COD_GRUPO_CIA = cCodGrupoCia_in
     AND    VTA_DET.COD_LOCAL = cCodLocal_in
     AND    VTA_DET.NUM_PED_VTA = cNumPedVta_in
     AND    VTA_DET.SEC_GRUPO_IMPR <> 0
     GROUP BY VTA_DET.SEC_GRUPO_IMPR, VTA_DET.SEC_COMP_PAGO
     ORDER BY VTA_DET.SEC_GRUPO_IMPR;

    SELECT IMPR_LOCAL.NUM_SERIE_LOCAL || '' ||
          IMPR_LOCAL.NUM_COMP
    INTO   v_cNumComp
    FROM   VTA_IMPR_LOCAL IMPR_LOCAL
    WHERE  IMPR_LOCAL.COD_GRUPO_CIA  = cCodGrupoCia_in
    AND   IMPR_LOCAL.COD_LOCAL      = cCodLocal_in
    AND   IMPR_LOCAL.SEC_IMPR_LOCAL = 1 FOR UPDATE;

    --dbms_output.put_line('6');
    Ptoventa_Caj.CAJ_ACTUALIZA_COMPROBANTE_IMPR(cCodGrupoCia_in,
                                     cCodLocal_in,
                             cNumPedVta_in,
                          v_cSecComp,
                          '01',
                          v_cNumComp,
                          'JOLIVA');

    --dbms_output.put_line('7');
    Ptoventa_Caj.CAJ_ACTUALIZA_IMPR_NUM_COMP(cCodGrupoCia_in,
                                    cCodLocal_in,
                         1,
                         'JOLIVA');

    --dbms_output.put_line('8');
    Ptoventa_Caj.CAJ_ACTUALIZA_ESTADO_PEDIDO(cCodGrupoCia_in,
                                    cCodLocal_in,
                         cNumPedVta_in,
                         'C',
                         'JOLIVA');

    RETURN cNumPedVta_in;
  END;

  PROCEDURE GENERA_COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in    IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cNumPedVtaCopia_in IN CHAR,
                     cSecMovCaja_in    IN CHAR,
                        nDiasRestaFecha_in IN NUMBER) IS
  v_cNumPedVta     CHAR(10);
  v_cResultado     CHAR(10);
  BEGIN
       v_cNumPedVta := GENERA_PEDIDO_SCRIPT(cCodGrupoCia_in,
                         cCodLocal_in,
                      cNumPedVtaCopia_in,
                      nDiasRestaFecha_in);

     v_cResultado := COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in,
                          cCodLocal_in,
                       v_cNumPedVta,
                       cNumPedVtaCopia_in,
                       cSecMovCaja_in,
                       nDiasRestaFecha_in);

    COMMIT;
      dbms_output.put_line('PEDIDO ' || TRIM(v_cResultado) || ' GENERADO Y COBRADO' );

  EXCEPTION
      WHEN OTHERS THEN
       dbms_output.put_line('ERROR AL GENERAR Y COBRAR PEDIDO' ||  SQLERRM);
       ROLLBACK;
  END;

  FUNCTION VERIFICA_STOCK_RESPALDO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cCodProd_in     IN CHAR)
    RETURN NUMBER IS
  v_nStkComprometido NUMBER;
  v_nSumaRespaldo    NUMBER;
  v_nResultado       NUMBER;
  v_nMultiplo NUMBER;
  BEGIN
    SELECT P.VAL_FRAC_VTA_SUG*L.VAL_FRAC_LOCAL
      INTO v_nMultiplo
    FROM LGT_PROD P,
         LGT_PROD_LOCAL L
    WHERE L.COD_GRUPO_CIA = cCodGrupoCia_in
          AND L.COD_LOCAL = cCodLocal_in
          AND L.COD_PROD = cCodProd_in
          AND L.COD_GRUPO_CIA = P.COD_GRUPO_CIA
          AND L.COD_PROD = P.COD_PROD;

       v_nSumaRespaldo := 0;
       v_nStkComprometido := 0;

       DBMS_OUTPUT.PUT_LINE('v_nStkComprometido: '||v_nStkComprometido);
       DBMS_OUTPUT.PUT_LINE('v_nSumaRespaldo: '||v_nSumaRespaldo);

       IF v_nStkComprometido = (v_nSumaRespaldo) THEN
          v_nResultado := 1;--NO HAY ERROR
       ELSE
          v_nResultado := 2;--HAY ERROR
       END IF;
       RETURN v_nResultado;
  EXCEPTION
      WHEN OTHERS THEN
           v_nResultado := 2;--HAY ERROR
           RETURN v_nResultado;
  END;

  /******************************************************************/

  FUNCTION OBTIENE_CANTIDAD_SESIONES(cNombrPc_in    IN CHAR,
                                     cUsuarioCon_in IN CHAR)
    RETURN NUMBER
  IS
    v_nCant NUMBER;
  BEGIN
       SELECT DBA_SESSION.FP_COUNT_SESSION(cNombrPc_in,cUsuarioCon_in)
       INTO   v_nCant
       FROM   DUAL;
    RETURN v_nCant;
  END;

  PROCEDURE ENVIA_CORREO(cCodGrupoCia_in       IN CHAR,
                         cCodLocal_in          IN CHAR,
                         vReceiverAddress_in   IN CHAR,
                         vAsunto_in            IN CHAR,
                         vTitulo_in            IN CHAR,
                         vMensaje_in           IN CHAR,
                         vCCReceiverAddress_in IN CHAR)
  AS
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
      mesg_body := '<LI> <B>' || 'LOCAL: '||v_vDescLocal  ||CHR(9)||
                                       '</B><BR>'||
                                          '<I>MENSAJE: </I><BR>'||vMensaje_in  ||
                                          '</LI>'  ;

    --ENVIA MAIL
      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               vReceiverAddress_in,
                               vAsunto_in||v_vDescLocal,
                               vTitulo_in,
                               mesg_body,
                               vCCReceiverAddress_in,
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               true);


  END;
 /* ******************************************************************* */
  FUNCTION OBTIEN_NUM_DIA(cDate_in IN DATE)
    RETURN VARCHAR2
  IS
    v_nCant VARCHAR2(2);
  BEGIN
      --lunes = 1  & Domingo = 7
      SELECT DECODE(MOD(TRUNC(cDate_in)-TO_DATE('20080629','YYYYMMDD')+3500,7),0,7,MOD(TRUNC(cDate_in)-TO_DATE('20080629','YYYYMMDD')+3500,7))
      INTO   v_nCant
      FROM   DUAL;

    RETURN v_nCant;
  END;
  /* ***************************************************************** */
FUNCTION GET_TIEMPOESTIMADO_CONEXION(cCodGrupoCia_in  IN CHAR)
                                           RETURN varchar2
   IS
     V_TIME_ESTIMADO varchar2(2121);
   BEGIN
    SELECT LLAVE_TAB_GRAL INTO V_TIME_ESTIMADO
    FROM   PBL_TAB_GRAL
    WHERE  ID_TAB_GRAL = 111
    AND    COD_APL = 'PTO_VENTA'
    AND    COD_TAB_GRAL = 'TIEMPO';
   return  V_TIME_ESTIMADO;
  END;


  /* ***************************************************************** */
    FUNCTION GET_TIME_OUT_CONN_REMOTA(cCodGrupoCia_in  IN CHAR)
                                               RETURN varchar2
       IS
         V_TIME_ESTIMADO varchar2(2121);
       BEGIN

        BEGIN
        SELECT LLAVE_TAB_GRAL INTO V_TIME_ESTIMADO
        FROM   PBL_TAB_GRAL
        WHERE  ID_TAB_GRAL = 234
        AND    COD_APL = 'PTO_VENTA'
        AND    COD_TAB_GRAL = 'TIME_OUT_CONN_REMOTA';
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        V_TIME_ESTIMADO := '2';
        END;

       return  V_TIME_ESTIMADO;
      END;

 /* ************************************************************************ */
  FUNCTION OBTIENE_TIPO_CAMBIO2(cCodGrupoCia_in IN CHAR,
                                cFecCambio_in   IN CHAR)
  RETURN NUMBER IS

    v_nValorTipCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE;
--/*
  BEGIN
    v_nValorTipCambio := 1.00;
    BEGIN
    SELECT A.VAL_TIPO_CAMBIO INTO v_nValorTipCambio
    FROM    CE_TIP_CAMBIO A
    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DECODE(cFecCambio_in,NULL,SYSDATE,TO_DATE((cFecCambio_in || ' 23:59:59'),'dd/MM/yyyy HH24:MI:SS'))
    BETWEEN A.FEC_INI_VIG AND decode(A.FEC_FIN_VIG,NULL,SYSDATE+1,A.FEC_FIN_VIG)
    ORDER BY A.SEC_TIPO_CAMBIO DESC;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
       RETURN v_nValorTipCambio;
    END;
    RETURN v_nValorTipCambio;
  END;
--*/
/*
  CURSOR curUtility IS
  SELECT A.VAL_TIPO_CAMBIO,A.SEC_TIPO_CAMBIO
    FROM    CE_TIP_CAMBIO A
    WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
    AND DECODE(cFecCambio_in,NULL,SYSDATE,TO_DATE((cFecCambio_in || ' 23:59:59'),'dd/MM/yyyy HH24:MI:SS'))
--    BETWEEN A.FEC_INI_VIG AND A.FEC_FIN_VIG
    BETWEEN A.FEC_INI_VIG AND decode(A.FEC_FIN_VIG,NULL,SYSDATE+1,A.FEC_FIN_VIG)
    ORDER BY A.SEC_TIPO_CAMBIO DESC;

  BEGIN
       v_nValorTipCambio := 1.00;

      SELECT A.VAL_TIPO_CAMBIO INTO v_nValorTipCambio
      FROM    CE_TIP_CAMBIO A
      WHERE  A.COD_GRUPO_CIA = cCodGrupoCia_in
      AND A.FEC_FIN_VIG IS NULL;

    BEGIN
     FOR tipocambio_rec IN curUtility
    LOOP
        v_nValorTipCambio := tipocambio_rec.VAL_TIPO_CAMBIO;
    END
    LOOP;
       EXCEPTION
      WHEN NO_DATA_FOUND THEN
       RETURN v_nValorTipCambio;
    END;

    RETURN v_nValorTipCambio;

  END;
*/
function split(input_list   varchar2,
               ret_this_one number,
               delimiter    varchar2) return varchar2 is

  v_list         varchar2(32767) := delimiter || input_list;
  start_position number;
  end_position   number;
begin
  start_position := instr(v_list, delimiter, 1, ret_this_one);
  if start_position > 0 then
    end_position := instr(v_list, delimiter, 1, ret_this_one + 1);
    if end_position = 0 then
      end_position := length(v_list) + 1;
    end if;
    return(substr(v_list,
                  start_position + 1,
                  end_position - start_position - 1));
  else
    return NULL;
  end if;
end split;

	function FN_DEV_TIPO_CAMBIO_F(cCodGrupoCia_in IN CHAR,
													cFecCambio_in   IN CHAR,
													A_cod_cia       char DEFAULT NULL,
													A_COD_LOCAL     char DEFAULT NULL,
													A_TIPO_RCD      CHAR DEFAULT NULL)
	  RETURN NUMBER IS
	  c_tc NUMBER := 0;
	BEGIN

	  BEGIN
		--por tipo de recaudacion
		SELECT VAL_TIPO_CAMBIO
		  INTO c_tc
		  FROM CE_TIP_CAMBIO_PER
		 WHERE COD_GRUPO_CIA = cCodGrupoCia_in
		   AND TIPO_RCD = A_TIPO_RCD
		   and rownum<=1;
	  EXCEPTION
		WHEN no_data_found THEN
		  BEGIN
			--por local
			SELECT VAL_TIPO_CAMBIO
			  INTO c_tc
			  FROM CE_TIP_CAMBIO_PER
			 WHERE cod_local = A_COD_LOCAL
			 and TIPO_RCD is null
			 and rownum<=1;
		  EXCEPTION
			WHEN no_data_found THEN
			
		  BEGIN
			--por empresa
			SELECT VAL_TIPO_CAMBIO
			  INTO c_tc
			  FROM CE_TIP_CAMBIO_PER
			 WHERE cod_cia = A_cod_cia
			 and cod_local is null
			 and rownum<=1;
		  EXCEPTION
			WHEN no_data_found THEN
			
			  c_tc := PTOVENTA.FARMA_UTILITY.OBTIENE_TIPO_CAMBIO2(cCodGrupoCia_in,
																  cFecCambio_in);
		  END;
		  END;
	  END;

	  return c_tc;
	END;
  /* ****************************************************************** */	
  FUNCTION OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in IN CHAR,
								cCodCia_in IN CHAR,
                                cFecCambio_in   IN CHAR,
								cIndTipo_in IN CHAR)  
  RETURN NUMBER
  IS
	v_nValorTipCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE:=0.0;
  BEGIN
	BEGIN
	  SELECT  DECODE(cIndTipo_in,'V',V.VAL_TIPO_CAMBIO,'C',V.VAL_TIPO_CAMBIO_COMPRA,0.0)
	  INTO    v_nValorTipCambio
	  FROM    (
			  SELECT VAL_TIPO_CAMBIO,t.fec_ini_vig,t.fec_fin_vig,VAL_TIPO_CAMBIO_COMPRA,
					 RANK() OVER (ORDER BY t.fec_fin_vig desc) orden
				FROM CE_TIP_CAMBIO t
			   WHERE COD_GRUPO_CIA = cCodGrupoCia_in
				 AND COD_CIA = cCodCia_in
				 AND FEC_INI_VIG <=
					 DECODE(cFecCambio_in,
							NULL,
							SYSDATE,
							TO_DATE(cFecCambio_in,'DD/MM/YYYY') + 1 - 1/24/60/60
							)
			   ) V
	  WHERE  V.ORDEN = 1;  
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
       NULL;
    END;
    RETURN v_nValorTipCambio;	  
  END;								
  /* ****************************************************************** */  
END Farma_Utility;

/
