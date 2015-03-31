CREATE OR REPLACE PACKAGE PTOVENTA."FARMA_UTILITY" AS

  /**
  * Copyright (c) 2006 MiFarma Peru S.A.
  *
  * Entorno de Desarrollo : Oracle9i
  * Nombre del Paquete    : FARMA_UTILITY
  *
  * Histórico de Creación/Modificación
  * RCASTRO       15.01.2006   Creación
  * LMESIA        10.07.2006   Modificacion stock
  *
  * @author Rolando Castro
  * @version 1.0
  *
  */

  TYPE FarmaCursor IS REF CURSOR;

  PROCEDURE LIBERAR_TRANSACCION;

  PROCEDURE ACEPTAR_TRANSACCION;

  -- Ejemplo de uso :
  --      COMPLETAR_CON_SIMBOLO(54,10,'*','D');
  --      resultado : 54********
  --      COMPLETAR_CON_SIMBOLO(54,10,'0','I');
  --      resultado : 0000000054
  FUNCTION COMPLETAR_CON_SIMBOLO(nValor_in    IN NUMBER,
                                 iLongitud_in IN INTEGER,
                                 cSimbolo_in  IN CHAR,
                                 cUbica_in    IN CHAR,
                                 vCodLocal_in IN VARCHAR2 DEFAULT NULL--LTAVARA 01.10.2014
                                   )
      RETURN VARCHAR2;

  FUNCTION OBTENER_REDONDEO(nValor_in IN NUMBER,
                            cTipo_in  IN CHAR)
           RETURN NUMBER;

  --Descripcion: Obtiene el VALOR DE LA TABLA NUMERACION
  --Fecha       Usuario    Comentario
  --27/01/2006  LMESIA     Creación
  FUNCTION OBTENER_NUMERACION(cCodGrupoCia_in   IN CHAR,
                cCodLocal_in     IN CHAR,
                cCodNumera_in    IN CHAR)
         RETURN NUMBER;

  --Descripcion: Actualiza un registro de la tabla numeracion Sin Commit
  --Fecha       Usuario    Comentario
  --27/01/2006  LMESIA     Creación
  PROCEDURE ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cCodNumera_in     IN CHAR,
                         vIdUsuario_in     IN VARCHAR2);

  --Descripcion: Inicializa un registro de la tabla numeracion Sin Commit
  --Fecha       Usuario    Comentario
  --27/01/2006  LMESIA     Creación
  PROCEDURE INICIALIZA_NUMERA_SIN_COMMIT(cCodGrupoCia_in  IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cCodNumera_in     IN CHAR,
                         vIdUsuario_in     IN VARCHAR2);

  --Descripcion: Obtiene el tipo de cambio para un dia determinado
  --Fecha       Usuario    Comentario
  --03/02/2006  LMESIA     Creación
  FUNCTION OBTIENE_TIPO_CAMBIO(cCodGrupoCia_in IN CHAR,
                    cFecCambio_in   IN CHAR)
           RETURN NUMBER;

  --Descripcion: Ejecuta las acciones en la tabla respaldo stock
  --Fecha       Usuario    Comentario
  --22/02/2006  LMESIA     CreaciÃ³n
  PROCEDURE EJECUTA_RESPALDO_STK(cCodGrupoCia_in   IN CHAR,
                                cCodLocal_in      IN CHAR,
                                cNumIpPc_in         IN CHAR,
                                cCodProd_in       IN CHAR,
                 cNumPedVta_in       IN CHAR,
                 cTipoOperacion_in  IN CHAR,
                                nCantMov_in         IN NUMBER,
                 nValFracc_in    IN NUMBER,
                    vIdUsuario_in     IN VARCHAR2,
                                                                 cModulo_in IN CHAR);

  --Descripcion: Ejecuta la recuperacion de los stocks de los productos
  --Fecha       Usuario    Comentario
  --22/02/2006  LMESIA     Creación
  PROCEDURE RECUPERACION_RESPALDO_STK(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in    IN CHAR,
                                   cNumIpPc_in     IN CHAR,
                    vIdUsuario_in   IN VARCHAR2);

  --Descripcion: Verifica si es valdo o no el Ruc ingresado
  --Fecha       Usuario    Comentario
  --22/02/2006  LMESIA     Creación
  FUNCTION VERIFICA_RUC_VALIDO(cNumRuc_in IN CHAR)
    RETURN CHAR;

  /*******************************************************************/

FUNCTION GENERA_PEDIDO_SCRIPT(cCodGrupoCia_in    IN CHAR,
            cCodLocal_in      IN CHAR,
            cNumPedVtaCopia_in IN CHAR,
            nDiasRestaFecha_in IN NUMBER)
RETURN CHAR;

  FUNCTION COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in     IN CHAR,
                    cCodLocal_in        IN CHAR,
                 cNumPedVta_in       IN CHAR,
                 cNumPedVtaCopia_in IN CHAR,
                 cSecMovCaja_in    IN CHAR,
                 nDiasRestaFecha_in IN NUMBER)
    RETURN CHAR;

  PROCEDURE GENERA_COBRA_PEDIDO_SCRIPT(cCodGrupoCia_in    IN CHAR,
                     cCodLocal_in      IN CHAR,
                     cNumPedVtaCopia_in IN CHAR,
                     cSecMovCaja_in    IN CHAR,
                        nDiasRestaFecha_in IN NUMBER);

  /*********************************************************************/

  FUNCTION VERIFICA_STOCK_RESPALDO(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in     IN CHAR,
                                   cCodProd_in     IN CHAR)
    RETURN NUMBER;

  /**********************************************************************/

  FUNCTION OBTIENE_CANTIDAD_SESIONES(cNombrPc_in    IN CHAR,
                                     cUsuarioCon_in IN CHAR)
    RETURN NUMBER;

  /**********************************************************************/

  PROCEDURE ENVIA_CORREO(cCodGrupoCia_in       IN CHAR,
                         cCodLocal_in          IN CHAR,
                         vReceiverAddress_in   IN CHAR,
                         vAsunto_in            IN CHAR,
                         vTitulo_in            IN CHAR,
                         vMensaje_in           IN CHAR,
                         vCCReceiverAddress_in IN CHAR);

  /**********************************************************************/

  FUNCTION OBTIEN_NUM_DIA(cDate_in IN DATE)
    RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Obtiene el Tiempo Estimado de Consulta
  --Fecha       Usuario        Comentario
  --19/08/2008  DUBILLUZ      Creación
  FUNCTION GET_TIEMPOESTIMADO_CONEXION(cCodGrupoCia_in  IN CHAR)
                                           RETURN varchar2;

  /* ****************************************************************** */
  --Descripcion: Obtiene el TIME OUT CONN REMOTA
  --Fecha       Usuario        Comentario
  --19/08/2008  DUBILLUZ      Creación
  FUNCTION GET_TIME_OUT_CONN_REMOTA(cCodGrupoCia_in  IN CHAR)
                                    RETURN varchar2;

  /* ****************************************************************** */
  --Descripcion: Obtiene tipo de cambio del dia de la venta, o ultimo vigente
  --Fecha       Usuario        Comentario
  --19/05/2009  JCORTEZ      Creación
  FUNCTION OBTIENE_TIPO_CAMBIO2(cCodGrupoCia_in IN CHAR,
                                cFecCambio_in   IN CHAR)
  RETURN NUMBER;

function split(input_list   varchar2,
               ret_this_one number,
               delimiter    varchar2) return varchar2;

  --Descripcion: Obtiene tipo de cambio del dia de la venta, o ultimo vigente
  --Fecha       Usuario        Comentario
  --18/1/2013  AESCATE      Creacion
	function FN_DEV_TIPO_CAMBIO_F(cCodGrupoCia_in IN CHAR,
													cFecCambio_in   IN CHAR,
													A_cod_cia       char DEFAULT NULL,
													A_COD_LOCAL     char DEFAULT NULL,
													A_TIPO_RCD      CHAR DEFAULT NULL)
	  RETURN NUMBER;

  /* ****************************************************************** */
  --Descripcion: Obtiene tipo de cambio del dia
  --Fecha       Usuario        Comentario
  --19/12/2013  ERIOS          Creacion
  FUNCTION OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in IN CHAR,
								cCodCia_in IN CHAR,
                                cFecCambio_in   IN CHAR,
								cIndTipo_in IN CHAR)
  RETURN NUMBER;

  /* ****************************************************************** */
  --Descripcion: Obtiene las iniciales del texto ingresado
  --Fecha       Usuario        Comentario
  --03/02/2014  LLEIVA         Creacion
  FUNCTION OBTIENE_INICIALES(cTexto IN CHAR)
  RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: Obtiene la fecha actual del sistema en formato ddmmyyyy
  --Fecha       Usuario        Comentario
  --07/02/2014  LLEIVA         Creacion
  FUNCTION GET_FECHA_ACTUAL
  RETURN VARCHAR2;

  --Descripcion: Ajusta el redondeo sobre el listado de precio de venta
  --Fecha       Usuario        Comentario
  --11.03.2014  CHUANES         Creacion

  FUNCTION OBTENER_REDONDEO2(nValor_in IN NUMBER)

   RETURN NUMBER ;
   /* ****************************************************************** */
  --Descripcion: Convierte numeros de dos o menos digitos a letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion

    FUNCTION DECENAS (decena IN NUMBER) RETURN VARCHAR2;

 /* ****************************************************************** */
  --Descripcion: Convierte numeros de tres o menos digitos a letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion

    FUNCTION CIENTOS (cien IN NUMBER) RETURN VARCHAR2;


 /* ****************************************************************** */
  --Descripcion: Convierte numeros de cuatro o menos digitos a letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion
    FUNCTION MILES (mil IN NUMBER) RETURN VARCHAR2;

/* ****************************************************************** */
  --Descripcion: Convierte numeros de cinco o menos digitos a letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion

    FUNCTION MILLONES (millon IN NUMBER) RETURN VARCHAR2;

/* ****************************************************************** */
  --Descripcion: Convierte los numero decenas a letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion

    FUNCTION NOMBRE_DECENAS (digito IN NUMBER, digito2 IN NUMBER) RETURN VARCHAR2;

    /* ****************************************************************** */
  --Descripcion: Convierte los numero desde 0 hasta 9 a letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion

    FUNCTION UNIDADES (unidad IN NUMBER) RETURN VARCHAR2;

 /* ****************************************************************** */
  --Descripcion: Convierte los numero desde 0 hasta 9 a letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion

    FUNCTION UNIDADES_2 (unidad IN NUMBER) RETURN VARCHAR2;

    /* ****************************************************************** */
  --Descripcion: Convierte el monto numerico en letras
  --Fecha       Usuario        Comentario
  --15/07/2014  LTAVARA         Creacion

    FUNCTION LETRAS (numero IN NUMBER) RETURN VARCHAR2;



  /* ****************************************************************** */
  --Descripcion: OBTIENE DESCRIPCION DEL TIPO DE COMPROBANTE DE PAGO
  --Fecha       Usuario        Comentario
  --18/09/2014  KMONCADA         Creacion

    FUNCTION GET_DESC_COMPROBANTE(C_COD_GRUPO_CIA IN CHAR,
                              C_TIP_COMP_PAGO_IN IN CHAR)RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: VALIDA SI ES COMPROBANTE ELECTRONICO Y CONCATENA
  --Fecha       Usuario        Comentario
  --09/10/2014  RHERRERA         Creacion
    FUNCTION GET_T_COMPROBANTE(cTipoCompPago CHAR,
                               cNumCompElec  VARCHAR2,
                               cNumComp      CHAR) RETURN VARCHAR2;

  /* ****************************************************************** */
  --Descripcion: OBTIENE CORRELATIVO DEL COMPROBANTE
  --Fecha       Usuario        Comentario
  --09/10/2014  RHERRERA         Creacion
  FUNCTION GET_T_CORR_COMPROBA(cTipoCompPago CHAR,
                               cNumCompElec  VARCHAR2,
                               cNumComp      CHAR) return varchar2;
  /* ****************************************************************** */
  --Descripcion: OBTIENE SERIA DEL COMPROBANTE SI ES ELECTRONICO
  --Fecha       Usuario        Comentario
  --09/10/2014  RHERRERA         Creacion
  FUNCTION GET_T_SERIE_COMPROBA(cTipoCompPago CHAR,
                               cNumCompElec  VARCHAR2,
                               cNumComp      CHAR) return varchar2 ;

  /* ****************************************************************** */
  --Descripcion: VALIDA SI ES COMPROBANTE ELECTRONICO Y CONCATENA CON GUION AL CONTINGENCIA
  --Fecha       Usuario        Comentario
  --09/10/2014  RHERRERA         Creacion
  FUNCTION GET_T_COMPROBANTE_2(cTipoCompPago CHAR,
                             cNumCompElec  VARCHAR2,
                             cNumComp      CHAR)
  RETURN  VARCHAR2 ;

FUNCTION getEmitioCOMPElectronico(cCodGrupoCia  VARCHAR2,
                                  cCodLocal      CHAR)
                             RETURN  VARCHAR2;

    END Farma_Utility;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."FARMA_UTILITY" IS


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
  */

  PROCEDURE LIBERAR_TRANSACCION IS
  BEGIN
    ROLLBACK;
  END;

  PROCEDURE ACEPTAR_TRANSACCION IS
  BEGIN
    COMMIT;
  END;

  /* ************************************************************************ */

    FUNCTION COMPLETAR_CON_SIMBOLO(nValor_in    IN NUMBER,
                                   iLongitud_in IN INTEGER,
                                   cSimbolo_in  IN CHAR,
                                   cUbica_in    IN CHAR,
                                   vCodLocal_in IN VARCHAR2 DEFAULT NULL) RETURN VARCHAR2 IS
        v_vNumeracion VARCHAR2(25);
        v_iCeros      INTEGER;
        v_vSerie      VARCHAR2(3);
    BEGIN
        /*
        v_vNumeracion := '';
        v_iCeros      := iLongitud_in - LENGTH(nValor_in);
        IF (v_iCeros <= 0) THEN
            RETURN TO_CHAR(nValor_in);
        ELSE
            FOR longitud IN 1 .. v_iCeros LOOP
                v_vNumeracion := v_vNumeracion || cSimbolo_in;
            END LOOP;
            IF (cUbica_in = 'I') THEN
                v_vNumeracion := v_vNumeracion || TO_CHAR(nValor_in);
            ELSE
                v_vNumeracion := TO_CHAR(nValor_in) || v_vNumeracion;
            END IF;
            RETURN v_vNumeracion;
        END IF;
        */
        v_vNumeracion := '';

        IF vCodLocal_in IS NULL THEN
            v_iCeros := iLongitud_in - LENGTH(nValor_in);
        ELSE
            v_iCeros := (iLongitud_in - LENGTH(vCodLocal_in)) -
                        LENGTH(nValor_in);
        END IF;

        v_vSerie := CHR(65 + substr(vCodLocal_in, 1, 1)) ||
                    CHR(65 + substr(vCodLocal_in, 2, 1)) ||
                    CHR(65 + substr(vCodLocal_in, 3, 1));

        IF (v_iCeros < 0) THEN
            v_iCeros := iLongitud_in - LENGTH(nValor_in);
            v_vSerie := '';
        END IF;

        FOR longitud IN 1 .. v_iCeros LOOP
            v_vNumeracion := v_vNumeracion || cSimbolo_in;
        END LOOP;

        IF (cUbica_in = 'I') THEN
            v_vNumeracion := v_vSerie || v_vNumeracion || TO_CHAR(nValor_in);
        ELSE
            v_vNumeracion := v_vSerie || TO_CHAR(nValor_in) || v_vNumeracion;
        END IF;

        RETURN v_vNumeracion;

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

  /* ****************************************************************** */
  --Descripcion: Obtiene las iniciales del texto ingresado
  --Fecha       Usuario        Comentario
  --03/02/2014  LLEIVA         Creacion
  FUNCTION OBTIENE_INICIALES(cTexto IN CHAR)
  RETURN VARCHAR2
  IS
     respuesta VARCHAR2(50);
     temp VARCHAR2(500);
     inicial VARCHAR2(1);
     pos INTEGER;
  BEGIN

    temp := cTexto;

    --se obtiene la primera inicial del texto y la posicion del siguiente espacio en blanco
    SELECT UPPER(SUBSTR(temp,0,1)) INTO inicial FROM dual;
    SELECT INSTR(temp,' ') INTO pos FROM dual;
    respuesta :=  respuesta || inicial;

    --mientras la posicion del siguiente espacio en blanco sea diferente de cero
    WHILE(pos != 0)
    LOOP
         --se corta el texto, se vuelve a obtener la posicion del espacio y la letra inicial
         SELECT UPPER(SUBSTR(temp,pos + 1, 1)) INTO inicial FROM dual;
         SELECT SUBSTR(temp,pos + 1) INTO temp from dual;
         SELECT INSTR(temp,' ') INTO pos FROM dual;

         respuesta :=  respuesta || inicial;
    END LOOP;

    return respuesta;
  END;
  /* ****************************************************************** */

    /* ****************************************************************** */
  --Descripcion: Obtiene la fecha actual del sistema en formato ddmmyyyy
  --Fecha       Usuario        Comentario
  --07/02/2014  LLEIVA         Creacion
  FUNCTION GET_FECHA_ACTUAL
  RETURN VARCHAR2
  IS
      vFecha varchar2(10);
  BEGIN
      SELECT TO_CHAR(SYSDATE,'ddmmyyyy') into vFecha from dual;
      return vFecha;
  END;
   --Descripcion: Ajusta el redondeo sobre el listado de precio de venta
  --Fecha       Usuario        Comentario
  --11.03.2014  CHUANES         Creacion

   FUNCTION OBTENER_REDONDEO2(nValor_in IN NUMBER)

   RETURN NUMBER IS
    v_valVtaProd NUMBER;
    v_aux1 NUMBER;
    v_aux2 NUMBER;
    v_aux21 INT;
    v_aux3  INT;
    v_aux4  INT;
    v_aux5 NUMBER;
  BEGIN
      v_valVtaProd:=nValor_in*100;
      v_valVtaProd:=TO_NUMBER(TRIM(TO_CHAR(v_valVtaProd,'999,990.00')),'999,990.00');
      v_valVtaProd:=CEIL(v_valVtaProd);
      v_aux1:=v_valVtaProd/100;
      v_aux2:=v_valVtaProd/10;
      SELECT CAST (v_aux2*10 AS INTEGER) INTO v_aux21  FROM DUAL;
      v_aux3:=TRUNC(v_aux2)*10;
      v_aux4:=0;
      IF(v_aux3=0) THEN
      v_aux4 :=0;
      ELSE
      v_aux4:=MOD(v_aux21,v_aux3);
       END IF;


       IF(v_aux4 = 0) THEN
        v_aux5 := 0;
       ELSE IF(v_aux4 <= 5) THEN
        v_aux5 := (5.0 - v_aux4)/100;
       ELSE
       v_aux5 := (10.0 - v_aux4)/100;
       END IF;

      END IF;
      RETURN (v_aux1+v_aux5);

  END;


---------------------------------------------------------------------------------------------
--@INICIO @FUNCIONES DE AYUDA PARA LA CONVERION DE NUMEROS A LETRAS
---------------------------------------------------------------------------------------------
FUNCTION CIENTOS (cien IN NUMBER) RETURN VARCHAR2 IS
-- convierte numeros de tres o menos digitos a letras
  numero   VARCHAR2(3);
  digitos  NUMBER;
  digito1  NUMBER;
  digito23 NUMBER;
BEGIN
  numero  := TO_CHAR(cien);
  digitos := NVL(LENGTH(numero), 0);
  IF digitos <= 2 THEN     -- En el caso de que el numero sea de uno
    RETURN(decenas(cien)); -- o dos digitos
  ELSIF digitos = 3 THEN   -- En el caso de que sean tres digitos
    digito1  := TO_NUMBER(SUBSTR(numero,1,1));
    digito23 := TO_NUMBER(SUBSTR(numero,2,2));
    IF digito1 = 1 THEN     -- El caso del cien
      IF digito23 = 0 THEN
         RETURN('cien');
      ELSE
         RETURN('ciento '||decenas(digito23));
      END IF;
    ELSIF digito1 = 5 THEN  -- El caso de los quinientos
      IF digito23 = 0 THEN
         RETURN('quinientos');
      ELSE
         RETURN('quinientos '||decenas(digito23));
      END IF;
    ELSIF digito1 = 9 THEN  -- El caso de los novecientos
      IF digito23 = 0 THEN
         RETURN('novecientos');
      ELSE
         RETURN('novecientos '||decenas(digito23));
      END IF;
    ELSE                    -- El resto de los casos
      IF digito23 = 0 THEN
        RETURN(unidades_2(digito1)||'cientos');
      ELSE
        RETURN(unidades_2(digito1)||'cientos '||decenas(digito23));
      END IF;
    END IF;
  ELSE
    RETURN('');
  END IF;
END;
---------------------------------------------------------------------------------------------
FUNCTION DECENAS (decena IN NUMBER) RETURN VARCHAR2 IS
-- Convierte numeros de dos digitos a letras
  numero  VARCHAR2(2);
  digitos NUMBER;
  digito1 NUMBER;
  digito2 NUMBER;

  wtemp   VARCHAR2(20);

BEGIN
  numero  := TO_CHAR(decena);
  digitos := NVL(LENGTH(numero), 0);
  IF digitos = 1 THEN -- Si tiene solo un digito entoces devuelve unidades
    RETURN unidades(decena);
  ELSIF digitos = 2 THEN -- Esto es en el caso de dos digitos
    IF decena = 10 THEN     -- Estos son casos especiales del 10 a 15
      RETURN('diez');
    ELSIF decena = 11 THEN
      RETURN('once');
    ELSIF decena = 12 THEN
      RETURN('doce');
    ELSIF decena = 13 THEN
      RETURN('trece');
    ELSIF decena = 14 THEN
      RETURN('catorce');
    ELSIF decena = 15 THEN
      RETURN('quince');
    ELSIF decena = 20 THEN
      RETURN('veinte');
    ELSE
      digito1 := TO_NUMBER(SUBSTR(numero,1,1));
      digito2 := TO_NUMBER(SUBSTR(numero,2,1));
      IF digito1 = 1 THEN    -- Estos los casos de 16 al 19
         RETURN('dieci'||unidades(digito2));
      ELSIF digito1 = 2 THEN -- Estos son los casos del 21 al 29
         RETURN('veinti'||unidades(digito2));
      ELSE                   -- El resto de los casos
         IF digito2 = 0 THEN
--	   wtemp:=
             return nombre_decenas(digito1, digito2);
           -- RETURN(SUBSTR(wtemp,1,NVL(LENGTH(wtemp), 0)-1)||'a');
         ELSE
           RETURN(nombre_decenas(digito1, digito2)||unidades(digito2));
         END IF;
      END IF;
    END IF;
  ELSE
    RETURN('');
  END IF;
END;
---------------------------------------------------------------------------------------------
FUNCTION MILES (mil IN NUMBER) RETURN VARCHAR2 IS
  numero    VARCHAR(6);
  digitos   NUMBER;
  digito1   NUMBER;
  digito123 NUMBER;
BEGIN
  numero  := TO_CHAR(mil);
  digitos := NVL(LENGTH(numero), 0);
  IF digitos <= 3 THEN
    RETURN(cientos(mil));
  ELSIF digitos <= 6 THEN

--raise_application_error(-20000,'**'||numero||'**'||digitos||'**');
    digito1   := TO_NUMBER(SUBSTR(numero,1,(digitos-3)));
    digito123 := TO_NUMBER(SUBSTR(numero,-3,3));
    IF digito1 = 1 THEN
       IF digito123 = 0 THEN
          RETURN('Un mil');
       ELSE
          RETURN('Un mil '||cientos(digito123));
       END IF;
    ELSE
       IF digito123 = 0 THEN
          RETURN(cientos(digito1)||' mil');
       ELSE
          RETURN(cientos(digito1)||' mil '||cientos(digito123));
       END IF;
    END IF;
    ELSE
    RETURN('');
    END IF;
END;
---------------------------------------------------------------------------------------------
FUNCTION MILLONES (millon IN NUMBER) RETURN VARCHAR2 IS
  numero    VARCHAR(12);
  digitos   NUMBER;
  digito1   NUMBER;
  digito2   NUMBER;
BEGIN
  numero  := TO_CHAR(millon);
  digitos := NVL(LENGTH(numero), 0);
  IF digitos <= 6 THEN
    RETURN(miles(millon));
  ELSIF digitos <= 12 THEN
    digito1   := TO_NUMBER(SUBSTR(numero,1,(digitos-6)));
    digito2   := TO_NUMBER(SUBSTR(numero,-6,6));
    IF digito1 = 1 THEN
       IF digito2 = 0 THEN
          RETURN('un millon');
       ELSE
          RETURN('un millon '||miles(digito2));
       END IF;
    ELSE
       IF digito2 = 0 THEN
          RETURN(miles(digito1)||' millones');
       ELSE
          RETURN(miles(digito1)||' millones '||miles(digito2));
       END IF;
    END IF;
  ELSE
    RETURN('');
  END IF;
END;
---------------------------------------------------------------------------------------------
FUNCTION NOMBRE_DECENAS (digito IN NUMBER, digito2 IN NUMBER) RETURN VARCHAR2 IS
  v_nombre varchar2(30);
BEGIN
  IF digito = 1 THEN
    v_nombre := 'dieci';
  ELSIF digito = 2 THEN
    v_nombre := 'veinti';
  ELSIF digito = 3 THEN
    v_nombre := 'treinta';
    --RETURN('treinta'||(case when digito2!=0 then ' y' end));
  ELSIF digito = 4 THEN
    v_nombre := 'cuarenta';
--    RETURN('cuarenta');
  ELSIF digito = 5 THEN
    v_nombre := 'cincuenta';
  ELSIF digito = 6 THEN
    v_nombre := 'sesenta';
  ELSIF digito = 7 THEN
    v_nombre := 'setenta';
  ELSIF digito = 8 THEN
    v_nombre := 'ochenta';
  ELSIF digito = 9 THEN
    v_nombre := 'noventa';
  ELSIF digito = 0 THEN
    v_nombre := 'cero';
  ELSE
    RETURN('');
  END IF;
  
  IF (digito>2 and digito2>0) THEN
    v_nombre := v_nombre || ' y ';
  END IF;
  RETURN v_nombre;
END;
---------------------------------------------------------------------------------------------
FUNCTION UNIDADES (unidad IN NUMBER) RETURN VARCHAR2 IS
-- Convierte los numero desde 0 hasta 9 a letras
BEGIN
  IF unidad = 1 THEN
    RETURN('uno');
  ELSIF unidad = 2 THEN
    RETURN('dos');
  ELSIF unidad = 3 THEN
    RETURN('tres');
  ELSIF unidad = 4 THEN
    RETURN('cuatro');
  ELSIF unidad = 5 THEN
    RETURN('cinco');
  ELSIF unidad = 6 THEN
    RETURN('seis');
  ELSIF unidad = 7 THEN
    RETURN('siete');
  ELSIF unidad = 8 THEN
    RETURN('ocho');
  ELSIF unidad = 9 THEN
    RETURN('nueve');
  ELSIF unidad = 0 THEN
    RETURN('cero');
  ELSE
    RETURN('');
  END IF;
END;

---------------------------------------------------------------------------------------------
FUNCTION UNIDADES_2 (unidad IN NUMBER) RETURN VARCHAR2 IS
-- Convierte los numero desde 0 hasta 9 a letras
BEGIN
  IF unidad = 1 THEN
    RETURN('uno');
  ELSIF unidad = 2 THEN
    RETURN('dos');
  ELSIF unidad = 3 THEN
    RETURN('tres');
  ELSIF unidad = 4 THEN
    RETURN('cuatro');
  ELSIF unidad = 5 THEN
    RETURN('cinco');
  ELSIF unidad = 6 THEN
    RETURN('seis');
  ELSIF unidad = 7 THEN
    RETURN('sete');
  ELSIF unidad = 8 THEN
    RETURN('ocho');
  ELSIF unidad = 9 THEN
    RETURN('nove');
  ELSIF unidad = 0 THEN
    RETURN('cero');
  ELSE
    RETURN('');
  END IF;
END;
---------------------------------------------------------------------------------------------
-- @ FUNCION PRINCIPAL PARA LA CONVERSION DE NUMEROS A LETRAS
---------------------------------------------------------------------------------------------
FUNCTION LETRAS (numero IN NUMBER) RETURN VARCHAR2 IS
  --Convierte el monto numerico en letras
  TEMP NUMBER;
BEGIN


  TEMP := TRUNC(numero);

  RETURN( millones(abs(TEMP))||' con '||SUBSTR(TO_CHAR(abs(numero) - abs(TEMP),'0.00'),4)||'/100 ');

--EXCEPTION
--  WHEN OTHERS THEN
--   raise_application_error (-20000,'**'|| temp ||'**'|| numero||'**' );
END;
---------------------------------------------------------------------------------------------
--@FIN @FUNCIONES DE AYUDA PARA LA CONVERION DE NUMEROS A LETRAS
---------------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------
--@FIN @FUNCIONES FORMATEAR EL NUMERO DE COMPROBANTE ELECTRONICO
---------------------------------------------------------------------------------------------


FUNCTION GET_DESC_COMPROBANTE(C_COD_GRUPO_CIA IN CHAR,
                              C_TIP_COMP_PAGO_IN IN CHAR)RETURN VARCHAR2 IS
  DESC_COMP_PAGO VTA_TIP_COMP.DESC_COMP%TYPE;

  BEGIN
    SELECT A.DESC_COMP
    INTO DESC_COMP_PAGO
    FROM VTA_TIP_COMP  A
    WHERE A.COD_GRUPO_CIA = C_COD_GRUPO_CIA
    AND A.TIP_COMP=C_TIP_COMP_PAGO_IN;

    RETURN DESC_COMP_PAGO;
  END;

FUNCTION GET_T_COMPROBANTE(cTipoCompPago CHAR,
                           cNumCompElec  VARCHAR2,
                           cNumComp      CHAR)
  RETURN  VARCHAR2
  IS
  v_num_comp  varchar2(20);
  BEGIN

       select nvl(
              DECODE(NVL(cTipoCompPago,'0'),
              '1', replace(cNumCompElec,'-', ''),
               cNumComp),'')
         into v_num_comp
         from dual;

  return v_num_comp;
  END;

  FUNCTION GET_T_CORR_COMPROBA(cTipoCompPago CHAR,
                               cNumCompElec  VARCHAR2,
                               cNumComp      CHAR) return varchar2 IS

    v_correlatico_E varchar2(10);
    valor_comp      varchar2(10);

  BEGIN

    IF (cNumCompElec IS NOT NULL OR LENGTH(cNumCompElec) > 0) THEN

      SELECT SUBSTR(trim(cNumCompElec),-8)--INSTR(cNumCompElec, '-') + 1)
--      SELECT SUBSTR(cNumCompElec,6)  --SI SE GUARD SIN GUION
        INTO v_correlatico_E
        FROM DUAL;

    END IF;

    SELECT nvl(
             DECODE(NVL(cTipoCompPago,'0'),
                  '1',
                  v_correlatico_E,
                  substr(cNumComp, 4, 10))
                  ,'')
      INTO valor_comp
      FROM DUAL;

    RETURN valor_comp;

  END;

  FUNCTION GET_T_SERIE_COMPROBA(cTipoCompPago CHAR,
                               cNumCompElec  VARCHAR2,
                               cNumComp      CHAR) return varchar2 IS

    v_correlatico_E varchar2(10);
    valor_comp      varchar2(10);

  BEGIN

    IF (cNumCompElec IS NOT NULL OR LENGTH(cNumCompElec) > 0) THEN

      SELECT SUBSTR(cNumCompElec,1,4)
        INTO v_correlatico_E
        FROM DUAL;

    END IF;

    SELECT nvl(
             DECODE(NVL(cTipoCompPago,'0'),
                  '1',
                  v_correlatico_E,
                  substr(cNumComp, 1, 3))
               ,'')
      INTO valor_comp
      FROM DUAL;

    RETURN valor_comp;

  END;


FUNCTION GET_T_COMPROBANTE_2(cTipoCompPago CHAR,
                             cNumCompElec  VARCHAR2,
                             cNumComp      CHAR)
  RETURN  VARCHAR2
  IS
  v_num_comp  varchar2(20);
  BEGIN

      /* select DECODE(cTipoCompPago,
              '1', cNumCompElec,
               SUBSTR(cNumComp,1,3)||'-'||SUBSTR(cNumComp,-7))
         into v_num_comp*/

         select
                GET_T_SERIE_COMPROBA(NVL(cTipoCompPago,'0'),cNumCompElec,cNumComp)
                ||'-'||
                GET_T_CORR_COMPROBA(NVL(cTipoCompPago,'0'),cNumCompElec,cNumComp)

         into v_num_comp
         from dual;

  return v_num_comp;
  END;



FUNCTION getEmitioCOMPElectronico(cCodGrupoCia  VARCHAR2,
                                  cCodLocal      CHAR)
                             RETURN  VARCHAR2
  IS
  vDesc  varchar2(20);
  BEGIN
         begin
         select decode(count(1),0,'N','S')
         into   vDesc
         from   vta_comp_pago cp
         where  cp.cod_grupo_cia = cCodGrupoCia
         and    cp.cod_local = cCodLocal
         and    cp.cod_tip_proc_pago  = '1'
         and    cp.fec_crea_comp_pago >= to_date('01/01/2014','dd/mm/yyyy');
         exception
         when others then 
           vDesc := 'N';
         end ;

  return vDesc;
  END;



END Farma_Utility;
/
