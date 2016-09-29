CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_CE_ERN" AS

  TYPE FarmaCursor IS REF CURSOR;
  TIP_MOV_CIERRE  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';
  INDICADOR_SI		     CHAR(1):='S';
  C_CSERVICIOS_BASICOS CHAR(5):='00000';

  TIP_PEDIDO_MESON CHAR(2) := '01';
  TIP_PEDIDO_DELIVERY CHAR(2) := '02';

  C_C_INGRESO_COMP_MANUAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='006';

  C_CODIGO_EF_BCP       CE_ENTIDAD_FINANCIERA.COD_ENTIDAD_FINANCIERA%TYPE :='001';
  C_CODIGO_EF_INTERBANK CE_ENTIDAD_FINANCIERA.COD_ENTIDAD_FINANCIERA%TYPE :='002';
  COD_CUADRATURA_DEL_PERDIDO     CE_CUADRATURA.COD_CUADRATURA%TYPE:='023';

  --Descripcion: Lista las cuadraturas.
  --Fecha       Usuario	  Comentario
  --02/08/2006  ERIOS     Creación
  FUNCTION CE_LISTA_CUADRATURAS(cCodGrupoCia_in	IN CHAR,
                                cCodLocal_in IN CHAR,
                                cSecMovCaja_in IN CHAR)

  RETURN FarmaCursor;

  --Descripcion: Lista los campos de una cuadratura.
  --Fecha       Usuario	  Comentario
  --02/08/2006  ERIOS     Creación
  FUNCTION CE_LISTA_CUADRATURA_CAMPOS(cCodGrupoCia_in	IN CHAR,
                                      cCodCuadratura_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Lista Series del Local
  --Fecha       Usuario	  Comentario
  --04/08/2006  ERIOS     Creación
  FUNCTION CE_LISTA_SERIES_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR) RETURN FarmaCursor;

  --Descripcion: Lista las formas de pago - Cuadratura
  --Fecha       Usuario	  Comentario
  --04/08/2006  ERIOS     Creación
  FUNCTION CE_OBTIENE_FORMAS_PAGO(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in    IN CHAR)  RETURN FarmaCursor;

  --Descripcion: Graba los datos de las cuadraturas
  --Fecha       Usuario	  Comentario
  --07/08/2006  ERIOS     Creación
  PROCEDURE CE_AGREGA_DATOS_CUADRATURA(cCodGrupoCia_in	IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecMovCaja_in IN CHAR,
                                      cCodCuadratura_in IN CHAR,
                                      cSerie_in IN CHAR,
                                      cTipoComp_in IN CHAR,
                                      cNumComp_in IN CHAR,
                                      nMonto_in IN NUMBER,
                                      vVuelto_in IN NUMBER,
                                      cTipoBillete_in IN CHAR,
                                      cTipoMoneda_in IN CHAR,
                                      cFormaPago_in IN CHAR,
                                      vSerieBillete_in IN VARCHAR2,
                                      vIdUsu_in IN VARCHAR2,
                                      vFecha_in VARCHAR2,
                                      vMotivo_in VARCHAR2,
                                      vCodProveedor_in IN CHAR DEFAULT NULL,
                                      vMontoPerdido_in IN NUMBER,
                                      vDescMotivo_in IN VARCHAR2 DEFAULT NULL
                                      );

  --Descripcion: Verifica que los campos correspondientes a cuadratura 001, sean validos.
  --Fecha       Usuario	  Comentario
  --07/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_001(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  cSerie_in         IN CHAR,
                                  cTipoComp_in      IN CHAR,
                                  cNumComp_in       IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  vIdUsu_in         IN VARCHAR2,
                                      vMotivo_in VARCHAR2);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 003, sean validos.
  --Fecha       Usuario	  Comentario
  --07/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_003(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  cSerie_in         IN CHAR,
                                  cTipoComp_in      IN CHAR,
                                  cNumComp_in       IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  vVuelto_in        IN NUMBER,
                                  vIdUsu_in         IN VARCHAR2,
                                      vMotivo_in VARCHAR2);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 006, sean validos.
  --Fecha       Usuario	  Comentario
  --07/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_006(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  cSerie_in         IN CHAR,
                                  cTipoComp_in      IN CHAR,
                                  cNumComp_in       IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  vIdUsu_in         IN VARCHAR2,
                                      vMotivo_in VARCHAR2);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 008, sean validos.
  --Fecha       Usuario	  Comentario
  --08/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_008(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  cTipoBillete_in   IN CHAR,
                                  cTipoMoneda_in    IN CHAR,
                                  vSerieBillete_in  IN VARCHAR2,
                                  vIdUsu_in         IN VARCHAR2,
                                  vFecha_in         IN VARCHAR2,
                                      vMotivo_in VARCHAR2);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 009, sean validos.
  --Fecha       Usuario	  Comentario
  --08/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_009(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  cTipoMoneda_in    IN CHAR,
                                  cFormaPago_in     IN CHAR,
                                  vIdUsu_in         IN VARCHAR2,
                                  vFecha_in         IN VARCHAR2,
                                      vMotivo_in VARCHAR2);

  --Descripcion: Obtiene el secuencial de un movimiento.
  --Fecha       Usuario	  Comentario
  --08/08/2006  ERIOS     Creación
  FUNCTION GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in	IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cSecMovCaja_in IN CHAR) RETURN NUMBER;

  --Descripcion: Obtiene las entidad financieras.
  --Fecha       Usuario	  Comentario
  --25/08/2006  ERIOS     Creación
  FUNCTION CE_OBTIENE_FINANCIERA RETURN FarmaCursor;

  --Descripcion: Obtiene el numero de cuenta.
  --Fecha       Usuario	  Comentario
  --25/08/2006  ERIOS     Creacion
  --20/12/2013  ERIOS     Se agrega parametro cCodCia_in
  FUNCTION CE_OBTIENE_CUENTA(cCodCia_in IN CHAR, cCodEntFinanciera_in IN CHAR, cTipoMoneda_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los servicios.
  --Fecha       Usuario	  Comentario
  --25/08/2006  ERIOS     Creación
  FUNCTION CE_OBTIENE_SERVICIOS RETURN FarmaCursor;

  --Descripcion: Obtiene los trabajadores.
  --Fecha       Usuario	  Comentario
  --25/08/2006  ERIOS     Creación
  --22/11/2007  DUBILLUZ  MODIFICACION
  FUNCTION LISTA_TRABAJADOR(cCodCia_in IN CHAR, cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,cTipoMaestro_in IN CHAR,
                            vFecha_in VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Graba los datos de las cuadraturas: efectivo rendido
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE CE_AGREGA_DATOS_EFECTIVO_REND(cCodGrupoCia_in	IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      vFecha_in VARCHAR2,
                                      cCodCuadratura_in IN CHAR,
                                      cTipoMoneda_in IN CHAR,
                                      nMonto_in IN NUMBER,
                                      cSecEntidadFinanciera_in IN CHAR,
                                      vFechaOperacion IN VARCHAR2,
                                      vNumeroOperacion IN VARCHAR2,
                                      vNomAgencia_in IN VARCHAR2,
                                      vFechaEmision IN VARCHAR2,
                                      cSerie_in IN CHAR,
                                      cTipoComp_in IN CHAR,
                                      cNumComp_in IN CHAR,
                                      vNomTitular_in IN VARCHAR2,
                                      cCodAutorizacion_in IN CHAR,
                                      cCodCia_in IN CHAR,
                                      cCodTrabajador_in IN CHAR,
                                      vDescMotivo_in IN VARCHAR2,
                                      cFormaPago_in IN CHAR,
                                      vSerieBillete_in IN VARCHAR2,
                                      cTipoDinero_in IN CHAR,
                                      vMontoParcial_in IN NUMBER,
                                      vRuc_in IN VARCHAR2,
                                      vRazonSocial_in IN VARCHAR2,
                                      cCodLocalNuevo_in IN CHAR,
                                      cCodServicio_in IN CHAR,
                                      vNumDocumento_in IN VARCHAR2,
                                      vIdUsu_in IN VARCHAR2,
                                      vCodProveedor_in IN CHAR,
                                      vFechaVencimiento_in IN VARCHAR2,
                                      vSecUsuLocal_in IN CHAR,
                                      vSerieDocumento_in CHAR,
                                      vMontoPerdido_in IN NUMBER,
                                      cNumeroPedido_in IN CHAR);

  --Descripcion: Obtiene el secuencial de cierre día.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  FUNCTION GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in	IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  vFecha_in  IN VARCHAR2) RETURN NUMBER;

  --Descripcion: Verifica que los campos correspondientes a cuadratura 011, sean validos.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_011(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cTipoMoneda_in IN CHAR,
                            cSecEntidadFinanciera_in IN CHAR,
                            nMonto_in IN NUMBER,
                            vFechaOperacion IN VARCHAR2,
                            vNumeroOperacion IN VARCHAR2,
                            vNomAgencia_in IN VARCHAR2,
                            vIdUsu_in IN VARCHAR2,
                            v_DescMotivo_in VARCHAR2,
                            cFormaPago_in IN CHAR);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 012, sean validos.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_012(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  vFechaEmision IN VARCHAR2,
                                  vNumDocumento_in IN VARCHAR2,
                                  cCodServicio_in IN CHAR,
                                  vNomTitular_in IN VARCHAR2,
                                  vFechaOperacion IN VARCHAR2,
                                  nMonto_in IN NUMBER,
                                  vIdUsu_in IN VARCHAR2,
                                  cCodProveedor_in IN CHAR,
                                  vFechaVencimiento_in IN VARCHAR2,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 013, sean validos.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_013(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            nMonto_in IN NUMBER,
                            cCodAutorizacion_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 014, sean validos.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_014(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            nMonto_in IN NUMBER,
                            cCodCia_in IN CHAR,
                            cCodTrabajador_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            cCodAutorizacion_in IN CHAR,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 016, sean validos.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_016(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodTrabajador_in IN CHAR,
                            vFechaOperacion IN VARCHAR2,
                            nMonto_in IN NUMBER,
                            vDescMotivo_in IN VARCHAR2,
                            cCodAutorizacion_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 017, sean validos.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_017(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cFormaPago_in IN CHAR,
                            cTipoMoneda_in IN CHAR,
                            nMonto_in IN NUMBER,
                            vIdUsu_in IN VARCHAR2,
                            vSecUsuLocal_in IN CHAR,
                           vDescMotivo_in IN VARCHAR2);


  --Descripcion: Verifica que los campos correspondientes a cuadratura 018, sean validos.
  --Fecha       Usuario	  Comentario
  --31/08/2006  ERIOS     Creación
   PROCEDURE VALIDA_CUADRATURA_018(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cTipoBillete_in IN CHAR,
                                  cTipoMoneda_in IN CHAR,
                                  vSerieBillete_in IN VARCHAR2,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vMontoParcial_in IN NUMBER,
                                  vIdUsu_in IN VARCHAR2,
                                  vDescMotivo_in IN VARCHAR2);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 019, sean validos.
  --Fecha       Usuario	  Comentario
  --29/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_019(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cTipoComp_in IN CHAR,
                            vNumDocumento_in IN VARCHAR2,
                            nMonto_in IN NUMBER,
                            vRuc_in IN VARCHAR2,
                            vRazonSocial_in IN VARCHAR2,
                            vFechaEmision IN VARCHAR2,
                            vFechaOperacion IN VARCHAR2,
                            vIdUsu_in IN VARCHAR2,
                            vSerieDocumento IN CHAR,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 020, sean validos.
  --Fecha       Usuario	  Comentario
  --31/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_020(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cCodLocalNuevo_in IN CHAR,
                            nMonto_in IN NUMBER,
                            cCodAutorizacion_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 021, sean validos.
  --Fecha       Usuario	  Comentario
  --31/08/2006  ERIOS     Creación
  PROCEDURE VALIDA_CUADRATURA_021(cCodGrupoCia_in	IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  --cSerie_in IN CHAR,
                                  cTipoComp_in IN CHAR,
                                  --cNumComp_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vMontoParcial_in IN NUMBER,
                                  vIdUsu_in IN VARCHAR2,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR,
                                  cNumeroPedido_in IN CHAR);

  --Descripcion: Valida el codigo de autorizacion
  --Fecha       Usuario	  Comentario
  --01/09/2006  ERIOS     Creación
  FUNCTION VALIDA_COD_AUTORIZACION(cCodLocal_in IN CHAR,cCodAutorizacion_in IN CHAR,
                                    vFecha_in IN VARCHAR) RETURN INTEGER;

  --Descripcion: Verifica que los campos correspondientes a cuadratura 024, sean validos.
  --Fecha       Usuario	  Comentario
  --01/12/2006  PAULO     Creación
  PROCEDURE VALIDA_CUADRATURA_024(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vIdUsu_in IN VARCHAR2,
                                  cCodAutorizacion_in IN CHAR,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR);
  --Descripcion: Verifica que los campos correspondientes a cuadratura 023, sean validos.
  --Fecha       Usuario	  Comentario
  --01/12/2006  PAULO     Creación
  PROCEDURE VALIDA_CUADRATURA_023(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cTipoMoneda_in IN CHAR,
                                  cSerie_in IN CHAR,
                                  cTipoComp_in IN CHAR,
                                  cNumComp_in IN CHAR,
                                  vDescMotivo_in IN VARCHAR2,
                                  cCodProveedor_in IN CHAR,
                                  vIdUsu_in IN VARCHAR2,
                                  nMontoPerdido_in IN NUMBER);

  --Descripcion: Verifica que los campos correspondientes a cuadratura 025, sean validos.
  --Fecha       Usuario	  Comentario
  --13/07/2007  PAULO     Creación
  PROCEDURE VALIDA_CUADRATURA_025(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vIdUsu_in IN VARCHAR2,
                                  cCodAutorizacion_in IN CHAR,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR);

 --Descripcion: Envia correo a caja electronica
  --Fecha        Usuario	  Comentario
  --03/01/2008   DUBILLUZ    Creación
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR);

  --Descripcion: Graba la cuadratura cotizacion de competencia
  --Fecha        Usuario	  Comentario
  --11/08/2010   ASOSA    Creación
  PROCEDURE CE_P_INS_CUADRA_COTI_COMP(cCodGrupoCia_in    IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cCodCuadratura_in IN CHAR,
                                       cMontoTotal_in    IN NUMBER,
                                       cNumSec_in        IN CHAR,
                                       cUsuCrea_in       IN CHAR,
                                       cGlosa_in         IN CHAR,
                                       secMovCaja_in     IN CHAR);
                                       
  PROCEDURE VALIDA_CUADRATURA_023_NEW(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cSecMovCaja_in    IN CHAR,
                                      vFecha_in         IN VARCHAR2,
                                      cCodCuadratura_in IN CHAR,
                                      nMonto_in         IN NUMBER,
                                      cTipoMoneda_in    IN CHAR,
                                      cSerie_in         IN CHAR,
                                      cTipoComp_in      IN CHAR,
                                      cNumComp_in       IN CHAR,
                                      vDescMotivo_in    IN VARCHAR2,
                                      cCodProveedor_in  IN CHAR,
                                      vIdUsu_in         IN VARCHAR2,
                                      nMontoPerdido_in  IN NUMBER);

  --Descripcion: Muestra mensaje de declaracion deficit en cierre turno
  --Fecha        Usuario	  Comentario
  --08/07/2015   EMAQUERA   Creación
  FUNCTION CE_OBTIENE_MSJE_CT(cCodCajero   IN CHAR,
                              cNomCajero   IN CHAR,
                              cNumCaja     IN CHAR,
                              cMontoDef    IN CHAR) RETURN FarmaCursor;

END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_CE_ERN" AS

  /****************************************************************************/
  FUNCTION CE_LISTA_CUADRATURAS(cCodGrupoCia_in	IN CHAR,
                                cCodLocal_in IN CHAR,
                                cSecMovCaja_in IN CHAR)
  RETURN FarmaCursor
  AS
    curCuadraturas FarmaCursor;
  BEGIN
    OPEN curCuadraturas FOR
    SELECT COD_CUADRATURA||'Ã'||
           DESC_CUADRATURA||'Ã'||
           NVL(V1.SUMA,0.00)||'Ã'||
           TIP_CUADRATURA
    FROM   CE_CUADRATURA,
          (SELECT CUADRATURA_CAJA.COD_CUADRATURA CODIGO,
                  NVL(CUADRATURA.DESC_CUADRATURA,' ') DESCRIPCION,
                  TO_CHAR(
                       -- KMONCADA 2015.03.24 CONSIDERA MONTO DE DELIVERY PERDIDO
                      DECODE(CUADRATURA_CAJA.COD_CUADRATURA,
                             COD_CUADRATURA_DEL_PERDIDO,
                             SUM(CUADRATURA_CAJA.MON_PERDIDO_TOTAL),
                             (SUM(CUADRATURA_CAJA.MON_TOTAL) * CUADRATURA.VAL_SIGNO)),'999,999,990.00') SUMA
           FROM   CE_CUADRATURA_CAJA CUADRATURA_CAJA,
                  CE_CUADRATURA CUADRATURA
           WHERE  CUADRATURA_CAJA.COD_GRUPO_CIA = ccodgrupocia_in
           AND    CUADRATURA_CAJA.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
           AND    CUADRATURA_CAJA.COD_LOCAL = ccodlocal_in
           AND    CUADRATURA_CAJA.SEC_MOV_CAJA = csecmovcaja_in
           AND    CUADRATURA_CAJA.COD_GRUPO_CIA = CUADRATURA.COD_GRUPO_CIA
           AND    CUADRATURA_CAJA.COD_CUADRATURA = CUADRATURA.COD_CUADRATURA
           GROUP BY CUADRATURA_CAJA.COD_CUADRATURA,
                   CUADRATURA.DESC_CUADRATURA,
                   CUADRATURA.VAL_SIGNO)V1

    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    EST_CUADRATURA = 'A'
    --AND    TIP_CUADRATURA IN ('01','02') antes
    AND    TIP_CUADRATURA IN ('01','02','05') --ASOSA, 10.08.2010 para que ahora tambien halla declaracion de cotizacion de competencia en cierre turno
    AND    COD_CUADRATURA = V1.CODIGO(+);
    RETURN curCuadraturas;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_CUADRATURA_CAMPOS(cCodGrupoCia_in	IN CHAR,
                                      cCodCuadratura_in IN CHAR)
  RETURN FarmaCursor
  AS
    curCampos FarmaCursor;
  BEGIN
    OPEN curCampos FOR
    SELECT NOM_CAMPO||'Ã'||' '||'Ã'||CE.COD_CAMPO||'Ã'||IND_TIP_DATO||'Ã'||
            IND_SOLO_LECTURA||'Ã'||IND_OBLIGATORIO
    FROM CE_CAMPOS_CUADRATURA CUA, PBL_CAMPO_CE CE
    WHERE CUA.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CUA.COD_CUADRATURA = cCodCuadratura_in
          AND CUA.COD_CAMPO = CE.COD_CAMPO;
          --ORDER BY ind_obligatorio||ce.cod_campo DESC;
    RETURN curCampos;
  END;

  /****************************************************************************/
  FUNCTION CE_LISTA_SERIES_COMPROBANTE(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR)
  RETURN FarmaCursor
  IS
    curVta FarmaCursor;
  BEGIN
    OPEN curVta FOR
    SELECT DISTINCT TRIM(NUM_SERIE_LOCAL)    || 'Ã' ||
            TRIM(NUM_SERIE_LOCAL)
    FROM   VTA_SERIE_LOCAL
    WHERE  COD_GRUPO_CIA =    cCodGrupoCia_in AND
           COD_LOCAL  	 =    cCodLocal_in;

    RETURN curVta;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_FORMAS_PAGO(cCodGrupoCia_in  IN CHAR,
                                   cCodLocal_in    	IN CHAR)
  RETURN FarmaCursor
  IS
    curCaj FarmaCursor;
  BEGIN
    OPEN curCaj FOR
    SELECT FORMA_PAGO.COD_FORMA_PAGO        || 'Ã' ||
               FORMA_PAGO.DESC_CORTA_FORMA_PAGO
    FROM   VTA_FORMA_PAGO FORMA_PAGO,
               VTA_FORMA_PAGO_LOCAL FORMA_PAGO_LOCAL
    WHERE  FORMA_PAGO_LOCAL.COD_GRUPO_CIA        = cCodGrupoCia_in
    AND	   FORMA_PAGO_LOCAL.COD_LOCAL            = cCodLocal_in
    AND	   FORMA_PAGO_LOCAL.EST_FORMA_PAGO_LOCAL = 'A'
    AND	   FORMA_PAGO.IND_FORMA_PAGO_CUADRATURA = 'S'
    AND	   FORMA_PAGO.COD_GRUPO_CIA              = FORMA_PAGO_LOCAL.COD_GRUPO_CIA
    AND	   FORMA_PAGO.COD_FORMA_PAGO             = FORMA_PAGO_LOCAL.COD_FORMA_PAGO
    ORDER BY FORMA_PAGO.COD_FORMA_PAGO;
    RETURN curCaj;
  END;

  /****************************************************************************/
  PROCEDURE CE_AGREGA_DATOS_CUADRATURA(cCodGrupoCia_in	IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      cSecMovCaja_in IN CHAR,
                                      cCodCuadratura_in IN CHAR, 
                                      cSerie_in IN CHAR,
                                      cTipoComp_in IN CHAR,
                                      cNumComp_in IN CHAR,
                                      nMonto_in IN NUMBER,
                                      vVuelto_in IN NUMBER,
                                      cTipoBillete_in IN CHAR,
                                      cTipoMoneda_in IN CHAR,
                                      cFormaPago_in IN CHAR,
                                      vSerieBillete_in IN VARCHAR2,
                                      vIdUsu_in IN VARCHAR2,
                                      vFecha_in VARCHAR2,
                                      vMotivo_in VARCHAR2,
                                      vCodProveedor_in IN CHAR DEFAULT NULL,
                                      vMontoPerdido_in IN NUMBER,
                                      vDescMotivo_in IN VARCHAR2 DEFAULT NULL
                                      )
  AS
  BEGIN
    IF cCodCuadratura_in = '001' THEN
      VALIDA_CUADRATURA_001(cCodGrupoCia_in,
                            cCodLocal_in,
                            cSecMovCaja_in,
                            cCodCuadratura_in,
                            cSerie_in,
                            cTipoComp_in,
                            cNumComp_in,
                            nMonto_in,
                            vIdUsu_in,
                            vMotivo_in);
    ELSIF cCodCuadratura_in = '003' THEN
      VALIDA_CUADRATURA_003(cCodGrupoCia_in,
                            cCodLocal_in,
                            cSecMovCaja_in,
                            cCodCuadratura_in,
                            cSerie_in,
                            cTipoComp_in,
                            cNumComp_in,
                            nMonto_in,
                            vVuelto_in,
                            vIdUsu_in,
                            vMotivo_in);
    ELSIF cCodCuadratura_in = '006' THEN
      VALIDA_CUADRATURA_006(cCodGrupoCia_in,
                            cCodLocal_in,
                            cSecMovCaja_in,
                            cCodCuadratura_in,
                            cSerie_in,
                            cTipoComp_in,
                            cNumComp_in,
                            nMonto_in,
                            vIdUsu_in,
                            vMotivo_in);
    ELSIF cCodCuadratura_in = '008' THEN
      VALIDA_CUADRATURA_008(cCodGrupoCia_in,
                            cCodLocal_in,
                            cSecMovCaja_in,
                            cCodCuadratura_in,
                            nMonto_in,
                            cTipoBillete_in,
                            cTipoMoneda_in,
                            vSerieBillete_in,
                            vIdUsu_in,
                            vFecha_in,
                            vMotivo_in);
    ELSIF cCodCuadratura_in = '009' THEN
      VALIDA_CUADRATURA_009(cCodGrupoCia_in,
                            cCodLocal_in,
                            cSecMovCaja_in,
                            cCodCuadratura_in,
                            nMonto_in,
                            cTipoMoneda_in,
                            cFormaPago_in,
                            vIdUsu_in,
                            vFecha_in,
                            vMotivo_in);
    ELSIF cCodCuadratura_in = '023' THEN
      VALIDA_CUADRATURA_023_NEW(cCodGrupoCia_in,
                            cCodLocal_in ,
                            cSecMovCaja_in,
                            vFecha_in ,
                            cCodCuadratura_in ,
                            nMonto_in ,
                            cTipoMoneda_in ,
                            cSerie_in ,
                            cTipoComp_in ,
                            cNumComp_in ,
                            vDescMotivo_in ,
                            vCodProveedor_in ,
                            vIdUsu_in,
                            vMontoPerdido_in);
    ELSE
      RAISE_APPLICATION_ERROR(-20091,'CUADRATURA NO ESPECIFICADA.');
    END IF;
  END;

  /****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_001(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  cSerie_in         IN CHAR,
                                  cTipoComp_in      IN CHAR,
                                  cNumComp_in       IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  vIdUsu_in         IN VARCHAR2,
                                      vMotivo_in VARCHAR2)
  AS
    v_cComprobante CE_CUADRATURA_CAJA.NUM_COMP_PAGO%TYPE;

    v_cIndCompAnul VTA_COMP_PAGO.IND_COMP_ANUL%TYPE;
    v_cNumPedAnul VTA_COMP_PAGO.NUM_PEDIDO_ANUL%TYPE;
    v_cNumComp VARCHAR2(20);--VTA_COMP_PAGO.Num_Comp_Pago%TYPE;
    v_cTotal VTA_COMP_PAGO.Val_Neto_Comp_Pago%TYPE;

    v_nCompCuadradatura INTEGER;

    v_cSecCompPago CE_MOV_CAJA.SEC_MOV_CAJA%TYPE;
    v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
  BEGIN
       --v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
       IF LENGTH(cSerie_in) >= 4 THEN
					   v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),8,'0','I');
						ELSE
						v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
					END IF;
						--FAC-ELECTRONICA :09.10.2014

       SELECT IND_COMP_ANUL,
              NUM_PEDIDO_ANUL,
              --COMP.NUM_COMP_PAGO,
              Farma_Utility.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,COMP.NUM_COMP_PAGO_E,COMP.NUM_COMP_PAGO),
					--FAC-ELECTRONICA :09.10.2014
              COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO
       INTO   v_cIndCompAnul,
              v_cNumPedAnul,
              v_cnumcomp,
              v_Ctotal
       FROM   VTA_COMP_PAGO COMP,
              VTA_PEDIDO_VTA_CAB CAB
       WHERE  COMP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COMP.COD_LOCAL = cCodLocal_in
       --AND    COMP.NUM_COMP_PAGO =
       AND     Farma_Utility.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,COMP.NUM_COMP_PAGO_E,COMP.NUM_COMP_PAGO)
                 = cSerie_in||v_cComprobante
                 					--FAC-ELECTRONICA :09.10.2014
       AND    COMP.TIP_COMP_PAGO = cTipoComp_in
       --AND    COMP.VAL_NETO_COMP_PAGO+COMP.VAL_REDONDEO_COMP_PAGO = nMonto_in
       --AND    CAB.TIP_PED_VTA = '01'
       AND    COMP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
       AND    COMP.COD_LOCAL = CAB.COD_LOCAL
       AND    COMP.NUM_PED_VTA = CAB.NUM_PED_VTA;

       IF(v_cnumcomp <> cSerie_in||v_cComprobante) THEN
         RAISE_APPLICATION_ERROR(-20080,'El numero del comprobante no existe. Verifique!.');
       ELSIF v_Ctotal <> nmonto_in THEN
         RAISE_APPLICATION_ERROR(-20079,'El monto del comprobante no es el correcto . Verifique!');
       END IF;

       SELECT COUNT(*)
       INTO   v_nCompCuadradatura
       FROM   CE_CUADRATURA_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    COD_CUADRATURA = cCodCuadratura_in
       AND    NUM_SERIE_LOCAL = cSerie_in
       AND    TIP_COMP = cTipoComp_in
       AND    NUM_COMP_PAGO = v_cComprobante
       AND    EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
       ;

       IF v_nCompCuadradatura > 0 THEN
          RAISE_APPLICATION_ERROR(-20093,'El documento ya está registrado en un cierre.');
       ELSIF v_cIndCompAnul = 'S' THEN
          SELECT SEC_MOV_CAJA
          INTO   v_cSecCompPago
          FROM   VTA_PEDIDO_VTA_CAB
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCodLocal_in
          AND    NUM_PED_VTA = v_cNumPedAnul;

          IF v_cSecCompPago = cSecMovCaja_in THEN
            RAISE_APPLICATION_ERROR(-20092,'¡El documento se encuentra anulado en esta caja y turno.');
          END IF;
       END IF;

       v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);

       --INSERTA CUADRATURA_CAJA
       INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA,
                                      SEC_CUADRATURA_CAJA, COD_CUADRATURA, NUM_SERIE_LOCAL,
                                      TIP_COMP, NUM_COMP_PAGO,	MON_MONEDA_ORIGEN,
                                      MON_TOTAL, USU_CREA_CUADRATURA_CAJA,DESC_MOTIVO)
                               VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                      v_nSecCuadratura, cCodCuadratura_in, cSerie_in,
                                      cTipoComp_in, v_cComprobante, nMonto_in,
                                      nMonto_in, vIdUsu_in,vMotivo_in);
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
         RAISE_APPLICATION_ERROR(-20101,'No se encuentra el documento especificado. ¡Verifique!.');
    WHEN OTHERS THEN
         RAISE;
  END;

  /****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_003(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  cSerie_in         IN CHAR,
                                  cTipoComp_in      IN CHAR,
                                  cNumComp_in       IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  vVuelto_in        IN NUMBER,
                                  vIdUsu_in         IN VARCHAR2,
                                  vMotivo_in in varchar2)
  AS
    v_cComprobante CE_CUADRATURA_CAJA.NUM_COMP_PAGO%TYPE;

    v_cIndCompAnul VTA_COMP_PAGO.IND_COMP_ANUL%TYPE;
    v_cNumPedAnul VTA_COMP_PAGO.NUM_PEDIDO_ANUL%TYPE;
    v_cNumComp VARCHAR2(20);--VTA_COMP_PAGO.Num_Comp_Pago%TYPE;
    v_cTotal VTA_COMP_PAGO.Val_Neto_Comp_Pago%TYPE;

    v_nCompManual NUMBER;
    v_nCompCuadradatura INTEGER;

    v_nVuelto CE_CUADRATURA_CAJA.MON_VUELTO%TYPE;
    v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
  BEGIN
       --v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
       IF LENGTH(cSerie_in) >= 4 THEN
					   v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),8,'0','I');
						ELSE
						v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
					END IF;
						--FAC-ELECTRONICA :09.10.2014

       SELECT IND_COMP_ANUL,
              NUM_PEDIDO_ANUL,
              --COMP.NUM_COMP_PAGO,
              Farma_Utility.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,COMP.NUM_COMP_PAGO_E,COMP.NUM_COMP_PAGO),
					--FAC-ELECTRONICA :09.10.2014
              COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO
       INTO   v_cIndCompAnul,
              v_cNumPedAnul,
              v_cnumcomp,
              v_Ctotal
       FROM   VTA_COMP_PAGO COMP,
              VTA_PEDIDO_VTA_CAB CAB
       WHERE  COMP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COMP.COD_LOCAL = cCodLocal_in
       AND    comp.sec_mov_caja  IN (SELECT c.sec_mov_caja_origen
                                     FROM   ce_mov_caja c
                                     WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                     AND    c.cod_local = ccodlocal_in
                                     AND    c.sec_mov_caja = csecmovcaja_in)
       --AND    COMP.NUM_COMP_PAGO = cSerie_in||v_cComprobante
       AND     Farma_Utility.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,COMP.NUM_COMP_PAGO_E,COMP.NUM_COMP_PAGO)
                 = cSerie_in||v_cComprobante
                 					--FAC-ELECTRONICA :09.10.2014
       AND    COMP.TIP_COMP_PAGO = cTipoComp_in
       --AND    COMP.VAL_NETO_COMP_PAGO+COMP.VAL_REDONDEO_COMP_PAGO = nMonto_in
       AND    CAB.TIP_PED_VTA = TIP_PEDIDO_DELIVERY
       AND    COMP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
       AND    COMP.COD_LOCAL = CAB.COD_LOCAL
       AND    COMP.NUM_PED_VTA = CAB.NUM_PED_VTA;

       IF(v_cnumcomp <> cSerie_in||v_cComprobante) THEN
          RAISE_APPLICATION_ERROR(-20080,'El numero del comprobante no existe. Verifique!.');
          /*SELECT COUNT(1)
          INTO   v_nCompManual
          FROM   CE_CUADRATURA_CAJA C
          WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
          AND    COD_LOCAL = cCodLocal_in
          AND    COD_CUADRATURA = C_C_INGRESO_COMP_MANUAL
          AND    NUM_SERIE_LOCAL = cSerie_in
          AND    TIP_COMP = cTipoComp_in
          AND    NUM_COMP_PAGO = v_cComprobante
          AND    MON_TOTAL = nmonto_in;*/

          /*IF( v_nCompManual < 1) THEN
              RAISE_APPLICATION_ERROR(-20080,'El numero del comprobante no existe. Verifique!.');
          END IF;*/
       ELSIF v_Ctotal <> nmonto_in THEN
          RAISE_APPLICATION_ERROR(-20079,'El monto del comprobante no es el correcto . Verifique!');
       END IF;

       SELECT COUNT(*)
       INTO   v_nCompCuadradatura
       FROM   CE_CUADRATURA_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    COD_CUADRATURA = cCodCuadratura_in--descomentado por Luis Mesia 20060927
       AND    NUM_SERIE_LOCAL = cSerie_in
       AND    TIP_COMP = cTipoComp_in
       AND    NUM_COMP_PAGO = v_cComprobante
       AND    EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
       ;

       IF v_nCompCuadradatura > 0 THEN
          RAISE_APPLICATION_ERROR(-20094,'El documento ya está registrado en un cierre.');
       END IF;

       v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);

       --INSERTA CUADRATURA_CAJA
       INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA,
                                      SEC_CUADRATURA_CAJA, COD_CUADRATURA,	NUM_SERIE_LOCAL,
                                      TIP_COMP, NUM_COMP_PAGO,	MON_MONEDA_ORIGEN,
                                      MON_TOTAL, MON_VUELTO, USU_CREA_CUADRATURA_CAJA,DESC_MOTIVO)
                               VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                      v_nSecCuadratura, cCodCuadratura_in, cSerie_in,
                                      cTipoComp_in, v_cComprobante, nMonto_in,
                                      nMonto_in+vVuelto_in, vVuelto_in, vIdUsu_in,vMotivo_in);

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
         BEGIN
              SELECT COUNT(1)
              INTO   v_nCompManual
              FROM   CE_CUADRATURA_CAJA C
              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
              AND    COD_LOCAL = cCodLocal_in
              AND    C.SEC_MOV_CAJA = cSecMovCaja_in
              AND    COD_CUADRATURA = C_C_INGRESO_COMP_MANUAL
              AND    NUM_SERIE_LOCAL = cSerie_in
              AND    TIP_COMP = cTipoComp_in
              AND    NUM_COMP_PAGO = v_cComprobante
              AND    MON_TOTAL = nmonto_in
              AND    C.EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
              ;

              IF( v_nCompManual < 1) THEN
                  RAISE_APPLICATION_ERROR(-20101,'No se encuentra el documento delivery especificado. ¡Verifique!.');
              END IF;

              SELECT COUNT(*)
              INTO   v_nCompCuadradatura
              FROM   CE_CUADRATURA_CAJA
              WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
              AND    COD_LOCAL = cCodLocal_in
              AND    COD_CUADRATURA = cCodCuadratura_in--descomentado por Luis Mesia 20060927
              AND    NUM_SERIE_LOCAL = cSerie_in
              AND    TIP_COMP = cTipoComp_in
              AND    NUM_COMP_PAGO = v_cComprobante
              AND    EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
              ;

              IF v_nCompCuadradatura > 0 THEN
                 RAISE_APPLICATION_ERROR(-20094,'El documento ya está registrado en un cierre.');
              END IF;

              v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);

              --INSERTA CUADRATURA_CAJA
              INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA,
                                             SEC_CUADRATURA_CAJA, COD_CUADRATURA,	NUM_SERIE_LOCAL,
                                             TIP_COMP, NUM_COMP_PAGO,	MON_MONEDA_ORIGEN,
                                             MON_TOTAL, MON_VUELTO, USU_CREA_CUADRATURA_CAJA)
                                      VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                             v_nSecCuadratura, cCodCuadratura_in, cSerie_in,
                                             cTipoComp_in, v_cComprobante, nMonto_in,
                                             nMonto_in+vVuelto_in, vVuelto_in, vIdUsu_in);

         EXCEPTION
           WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20101,'No se encuentra el documento delivery especificado. ¡Verifique!.');
           WHEN OTHERS THEN
                RAISE;
         END;
    WHEN OTHERS THEN
         RAISE;
  END;

  /****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_006(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  cSerie_in         IN CHAR,
                                  cTipoComp_in      IN CHAR,
                                  cNumComp_in       IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  vIdUsu_in         IN VARCHAR2,
                                  vMotivo_in VARCHAR2)
  AS
    v_cComprobante CE_CUADRATURA_CAJA.NUM_COMP_PAGO%TYPE;

    v_nComp INTEGER;
    v_nCompCuadradatura INTEGER;

    v_cSecCompPago CE_MOV_CAJA.SEC_MOV_CAJA%TYPE;
    v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
  BEGIN

    --v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
    IF LENGTH(cSerie_in) >= 4 THEN
					   v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),8,'0','I');
						ELSE
						v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
					END IF;
						--FAC-ELECTRONICA :09.10.2014

       SELECT COUNT(*)
       INTO   v_nComp
       FROM   VTA_COMP_PAGO COMP, VTA_PEDIDO_VTA_CAB CAB
       WHERE  COMP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COMP.COD_LOCAL = cCodLocal_in
       --AND    COMP.NUM_COMP_PAGO = cSerie_in||v_cComprobante
       AND     Farma_Utility.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,COMP.NUM_COMP_PAGO_E,COMP.NUM_COMP_PAGO)
                 = cSerie_in||v_cComprobante
                 					--FAC-ELECTRONICA :09.10.2014
       AND    COMP.TIP_COMP_PAGO = cTipoComp_in
       --AND    COMP.VAL_NETO_COMP_PAGO+COMP.VAL_REDONDEO_COMP_PAGO = nMonto_in
       AND    comp.sec_mov_caja  IN (SELECT c.sec_mov_caja_origen
                                     FROM   ce_mov_caja c
                                     WHERE  c.cod_grupo_cia = ccodgrupocia_in
                                     AND    c.cod_local = ccodlocal_in
                                     AND    c.sec_mov_caja = csecmovcaja_in)
       --AND    CAB.TIP_PED_VTA = '01'
       AND    COMP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
       AND    COMP.COD_LOCAL = CAB.COD_LOCAL
       AND    COMP.NUM_PED_VTA = CAB.NUM_PED_VTA;

       SELECT COUNT(*)
       INTO   v_nCompCuadradatura
       FROM   CE_CUADRATURA_CAJA
       WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
       AND    COD_LOCAL = cCodLocal_in
       AND    COD_CUADRATURA = cCodCuadratura_in
       AND    NUM_SERIE_LOCAL = cSerie_in
       AND    TIP_COMP = cTipoComp_in
       AND    NUM_COMP_PAGO = v_cComprobante
       AND    EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
       ;

      IF v_nComp > 0 THEN
         RAISE_APPLICATION_ERROR(-20095,'¡El documento ya fue regularizado!');
      ELSIF v_nCompCuadradatura > 0 THEN
         RAISE_APPLICATION_ERROR(-20096,'El documento ya está registrado en un cierre.');
      END IF;

      v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);

      --INSERTA CUADRATURA_CAJA
      INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA,
                                     SEC_CUADRATURA_CAJA, COD_CUADRATURA, NUM_SERIE_LOCAL,
                                     TIP_COMP, NUM_COMP_PAGO, MON_MONEDA_ORIGEN,
                                     MON_TOTAL, USU_CREA_CUADRATURA_CAJA,Desc_Motivo)
                              VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                     v_nSecCuadratura, cCodCuadratura_in, cSerie_in,
                                     cTipoComp_in, v_cComprobante, nMonto_in,
                                     nMonto_in, vIdUsu_in,vMotivo_in);

  END;

  /****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_008(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  cTipoBillete_in   IN CHAR,
                                  cTipoMoneda_in    IN CHAR,
                                  vSerieBillete_in  IN VARCHAR2,
                                  vIdUsu_in         IN VARCHAR2,
                                  vFecha_in         IN VARCHAR2,
                                  vMotivo_in        IN VARCHAR2)
  AS
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
    v_vSerieBillete CE_CUADRATURA_CAJA.SERIE_BILLETE%TYPE := NULL;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    IF cTipoBillete_in = '01' AND vSerieBillete_in IS NULL THEN --BILLETE
       RAISE_APPLICATION_ERROR(-20097,'Debe ingresar la Serie del Billete.');
    ELSIF cTipoBillete_in = '01' AND cTipoMoneda_in ='02' AND NOT
          (nMonto_in = 1 OR nMonto_in = 5 OR nMonto_in = 10 OR nMonto_in = 20 OR nMonto_in = 50 OR nMonto_in = 100)THEN
       RAISE_APPLICATION_ERROR(-20098,'Verifique la Denominación del Billete.');
    ELSIF cTipoBillete_in = '01' AND cTipoMoneda_in ='01' AND NOT
          (nMonto_in = 10 OR nMonto_in = 20 OR nMonto_in = 50 OR nMonto_in = 100 OR nMonto_in = 200)THEN
       RAISE_APPLICATION_ERROR(-20099,'Verifique la Denominación del Billete.');
    ELSIF cTipoBillete_in = '02' AND cTipoMoneda_in ='01' AND NOT --MONEDA
         (nMonto_in = 1 OR nMonto_in = 2 OR nMonto_in = 5 OR nMonto_in = 0.1 OR nMonto_in = 0.50 OR nMonto_in = 0.20)THEN
       RAISE_APPLICATION_ERROR(-20100,'Verifique la Denominación de la Moneda');
    ELSIF cTipoBillete_in = '02' AND cTipoMoneda_in ='02' AND
         (nMonto_in <> 0 )THEN
       RAISE_APPLICATION_ERROR(-20101,'No puede ingresar Monedas en Dolares');
    END IF;

    IF cTipoMoneda_in ='02' THEN --DOLARES
	   SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
       v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
    END IF;

    IF cTipoBillete_in = '01' THEN
       v_vSerieBillete := vSerieBillete_in;
    END IF;

    v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);

    --INSERTA CUADRATURA_CAJA
    INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA,
                                   SEC_CUADRATURA_CAJA, COD_CUADRATURA,	MON_MONEDA_ORIGEN,
                                   MON_TOTAL, TIP_DINERO, TIP_MONEDA,
                                   SERIE_BILLETE, USU_CREA_CUADRATURA_CAJA,DESC_MOTIVO)
                            VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                   v_nSecCuadratura, cCodCuadratura_in, nMonto_in,
                                   nMonto_in*v_nTipoCambio, cTipoBillete_in, cTipoMoneda_in,
                                   v_vSerieBillete, vIdUsu_in,vMotivo_in);
  END;

  /****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_009(cCodGrupoCia_in	  IN CHAR,
                                  cCodLocal_in      IN CHAR,
                                  cSecMovCaja_in    IN CHAR,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in         IN NUMBER,
                                  cTipoMoneda_in    IN CHAR,
                                  cFormaPago_in     IN CHAR,
                                  vIdUsu_in         IN VARCHAR2,
                                  vFecha_in         IN VARCHAR2,
                                  vMotivo_in        IN VARCHAR2)
  AS
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
       IF (cTipoMoneda_in = '01' AND cFormaPago_in = '00002') OR
          (cTipoMoneda_in = '02' AND cFormaPago_in = '00001') THEN
          RAISE_APPLICATION_ERROR(-20100,'Seleccione correctamente la Moneda y la Forma Pago');
       END IF;

       IF cTipoMoneda_in ='02' THEN --DOLARES
          SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
		  v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
       END IF;

       v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);

       --INSERTA CUADRATURA_CAJA
       INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA,
                                      SEC_CUADRATURA_CAJA, COD_CUADRATURA, MON_MONEDA_ORIGEN,
                                      MON_TOTAL, TIP_MONEDA, COD_FORMA_PAGO,
                                      USU_CREA_CUADRATURA_CAJA,DESC_MOTIVO)
                               VALUES(cCodGrupoCia_in, cCodLocal_in, cSecMovCaja_in,
                                      v_nSecCuadratura, cCodCuadratura_in, nMonto_in,
                                      nMonto_in*v_nTipoCambio, cTipoMoneda_in, cFormaPago_in,
                                      vIdUsu_in,vMotivo_in);
  END;

  /****************************************************************************/
  FUNCTION GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in	IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cSecMovCaja_in IN CHAR) RETURN NUMBER
  IS
    v_nSec CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
  BEGIN
    SELECT NVL(MAX(SEC_CUADRATURA_CAJA),0) + 1
      INTO v_nSec
    FROM CE_CUADRATURA_CAJA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND SEC_MOV_CAJA = cSecMovCaja_in
         -- AND    EST_CUADRATURA_CAJA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          ;
    RETURN v_nSec;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_FINANCIERA
  RETURN FarmaCursor
  IS
    curEnt FarmaCursor;
  BEGIN
    OPEN curEnt FOR
    SELECT COD_ENTIDAD_FINANCIERA || 'Ã' || NOM_ENTIDAD_FINANCIERA
    FROM CE_ENTIDAD_FINANCIERA
    WHERE EST_ENTIDAD_FINANCIERA = 'A'
    ORDER BY COD_ENTIDAD_FINANCIERA;--AGREGADO X LUIS MESIA

    RETURN curEnt;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_CUENTA(cCodCia_in IN CHAR, cCodEntFinanciera_in IN CHAR, cTipoMoneda_in IN CHAR)
  RETURN FarmaCursor
  IS
    curCuenta FarmaCursor;
  BEGIN
    OPEN curCuenta FOR
    SELECT SEC_ENT_FINAN_CUENTA || 'Ã' || NUM_CUENTA_BANCO
    FROM CE_ENTIDAD_FINANCIERA_CUENTA
    WHERE COD_ENTIDAD_FINANCIERA = cCodEntFinanciera_in
          AND TIP_MONEDA = cTipoMoneda_in
          AND EST_ENT_FINAN_CUENTA = 'A'
		  AND COD_CIA = cCodCia_in
          AND ROWNUM = 1;
    RETURN curcuenta;
  END;

  /****************************************************************************/
  FUNCTION CE_OBTIENE_SERVICIOS
  RETURN FarmaCursor
  IS
    curServicio FarmaCursor;
  BEGIN
    OPEN curServicio FOR
    SELECT COD_SERVICIO || 'Ã' || DESC_SERVICIO
    FROM CE_SERVICIO;
    RETURN curServicio;
  END;

  /****************************************************************************/
  FUNCTION LISTA_TRABAJADOR(cCodCia_in IN CHAR, cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,cTipoMaestro_in IN CHAR,
                            vFecha_in VARCHAR2)
  RETURN FarmaCursor
  IS
    curGral FarmaCursor;
  BEGIN
    IF(cTipoMaestro_in = '8') THEN --TRABAJADOR CON CARGOS EN ESPECIFICO
      OPEN curGral FOR
      /*SELECT COD_TRAB || 'Ã' || APE_PAT_TRAB||' '||APE_MAT_TRAB||', '||NOM_TRAB|| 'Ã' ||
             NVL(COD_TRAB_RRHH,' ')
      FROM CE_MAE_TRAB
      WHERE COD_CIA = cCodCia_in
      AND   EST_TRAB = 'A'
      --AND   COD_CARGO IN ('001','002','003','004','005');
      --13/12/2007 DUBILLUZ MODIFICACION
      AND   COD_CARGO IN ('02','05','06','43','17');*/
      SELECT T.COD_TRAB || 'Ã' ||
             T.APE_PAT_TRAB||' '||T.APE_MAT_TRAB||', '||T.NOM_TRAB || 'Ã' ||
             NVL(T.COD_TRAB_RRHH,' ')
      FROM   CE_MAE_TRAB   T,
             CE_CUADRATURA C,
             CE_TRABAJADOR_CUADRATURA L
      WHERE  T.COD_CIA = cCodCia_in
      --AND    L.COD_GRUPO_CIA  = T.COD_CIA --ERIOS 18.05.2016 Cambios de JMELGAR
      AND    L.COD_TRAB_RRHH  = T.COD_TRAB_RRHH
      AND    L.COD_GRUPO_CIA  = C.COD_GRUPO_CIA
      AND    L.COD_CUADRATURA = C.COD_CUADRATURA
      AND    L.COD_CUADRATURA = '016'
      AND    L.ESTADO = 'A'
      AND    C.EST_CUADRATURA = 'A';
    ELSIF(cTipoMaestro_in = '9') THEN --TRABAJADOR LOCAL
      OPEN curGral FOR
      SELECT MAE.COD_TRAB|| 'Ã' ||APE_PAT_TRAB||' '||APE_MAT_TRAB||', '||NOM_TRAB|| 'Ã' ||
             NVL(mae.COD_TRAB_RRHH,' ')
      FROM CE_MAE_TRAB MAE, PBL_USU_LOCAL LOC
      WHERE MAE.COD_CIA = cCodCia_in
            AND LOC.COD_GRUPO_CIA = cCodGrupoCia_in
            AND LOC.COD_LOCAL = cCodLocal_in
            AND LOC.EST_USU = 'A'
            AND MAE.COD_TRAB = LOC.COD_TRAB;
    ELSIF(cTipoMaestro_in = '10') THEN --CAJERO
      OPEN curGral FOR
      SELECT MAE.COD_TRAB|| 'Ã' ||APE_PAT_TRAB||' '||APE_MAT_TRAB||', '||NOM_TRAB|| 'Ã' ||
             NVL(mae.COD_TRAB_RRHH,' ')
      FROM CE_MAE_TRAB MAE, PBL_USU_LOCAL LOC
      WHERE MAE.COD_CIA = cCodCia_in
            AND LOC.COD_GRUPO_CIA = cCodGrupoCia_in
            AND LOC.COD_LOCAL = cCodLocal_in
            AND LOC.EST_USU = 'A'
            AND MAE.COD_TRAB = LOC.COD_TRAB
            AND LOC.SEC_USU_LOCAL IN (SELECT DISTINCT SEC_USU_LOCAL
                                      FROM CE_MOV_CAJA MOV
                                      WHERE MOV.COD_GRUPO_CIA = cCodGrupoCia_in
                                            AND MOV.COD_LOCAL = cCodLocal_in
                                            AND MOV.FEC_DIA_VTA = TO_DATE(vFecha_in,'dd/MM/yyyy')
                                            AND MOV.TIP_MOV_CAJA = 'C');
    END IF;
    RETURN curGral;
  END;
  /****************************************************************************/
  PROCEDURE CE_AGREGA_DATOS_EFECTIVO_REND(cCodGrupoCia_in	IN CHAR,
                                      cCodLocal_in    IN CHAR,
                                      vFecha_in VARCHAR2,
                                      cCodCuadratura_in IN CHAR,
                                      cTipoMoneda_in IN CHAR,
                                      nMonto_in IN NUMBER,
                                      cSecEntidadFinanciera_in IN CHAR,
                                      vFechaOperacion IN VARCHAR2,
                                      vNumeroOperacion IN VARCHAR2,
                                      vNomAgencia_in IN VARCHAR2,
                                      vFechaEmision IN VARCHAR2,
                                      cSerie_in IN CHAR,
                                      cTipoComp_in IN CHAR,
                                      cNumComp_in IN CHAR,
                                      vNomTitular_in IN VARCHAR2,
                                      cCodAutorizacion_in IN CHAR,
                                      cCodCia_in IN CHAR,
                                      cCodTrabajador_in IN CHAR,
                                      vDescMotivo_in IN VARCHAR2,
                                      cFormaPago_in IN CHAR,
                                      vSerieBillete_in IN VARCHAR2,
                                      cTipoDinero_in IN CHAR,
                                      vMontoParcial_in IN NUMBER,
                                      vRuc_in IN VARCHAR2,
                                      vRazonSocial_in IN VARCHAR2,
                                      cCodLocalNuevo_in IN CHAR,
                                      cCodServicio_in IN CHAR,
                                      vNumDocumento_in IN VARCHAR2,
                                      vIdUsu_in IN VARCHAR2,
                                      vCodProveedor_in IN CHAR,
                                      vFechaVencimiento_in IN VARCHAR2,
                                      vSecUsuLocal_in IN CHAR,
                                      vSerieDocumento_in CHAR,
                                      vMontoPerdido_in IN NUMBER,
                                      cNumeroPedido_in IN CHAR)

  AS
  BEGIN
    IF cCodCuadratura_in = '011' THEN
      VALIDA_CUADRATURA_011(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            cTipoMoneda_in,
                            cSecEntidadFinanciera_in,
                            nMonto_in,
                            vFechaOperacion,
                            vNumeroOperacion,
                            vNomAgencia_in,
                            vIdUsu_in,
                            vDescMotivo_in,
                            Cformapago_In);
    ELSIF cCodCuadratura_in = '012' THEN
      VALIDA_CUADRATURA_012(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            vFechaEmision,
                            vNumDocumento_in,
                            cCodServicio_in,
                            vNomTitular_in,
                            vFechaOperacion,
                            nMonto_in,
                            vIdUsu_in,
                            vCodProveedor_in,
                            vFechaVencimiento_in ,
                            vdescmotivo_in,
                            cTipoMoneda_in);
    ELSIF cCodCuadratura_in = '013' THEN
      VALIDA_CUADRATURA_013(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            nMonto_in,
                            cCodAutorizacion_in,
                            vIdUsu_in,
                            vdescmotivo_in,
                            Ctipomoneda_In);
    ELSIF cCodCuadratura_in = '014' THEN
      VALIDA_CUADRATURA_014(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            nMonto_in,
                            cCodCia_in,
                            cCodTrabajador_in,
                            vIdUsu_in,
                            Ccodautorizacion_In,
                            Vdescmotivo_in,
                            Ctipomoneda_In);
    ELSIF cCodCuadratura_in = '016' THEN
      VALIDA_CUADRATURA_016(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            cCodCia_in,
                            cCodTrabajador_in,
                            vFechaOperacion,
                            nMonto_in,
                            vDescMotivo_in,
                            cCodAutorizacion_in,
                            vIdUsu_in,
                            ctipomoneda_in);
    ELSIF cCodCuadratura_in = '017' THEN
      VALIDA_CUADRATURA_017(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            cFormaPago_in,
                            cTipoMoneda_in,
                            nMonto_in,
                            vIdUsu_in,
                            vSecUsuLocal_in,
                            vdescmotivo_in);
    ELSIF cCodCuadratura_in = '018' THEN
      VALIDA_CUADRATURA_018(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                                  nMonto_in,
                                  cTipoDinero_in,
                                  cTipoMoneda_in,
                                  vSerieBillete_in,
                                  cCodCia_in,
                                  cCodTrabajador_in,
                                  vMontoParcial_in,
                                  vIdUsu_in,
                                  vdescmotivo_in);
    ELSIF cCodCuadratura_in = '019' THEN
      VALIDA_CUADRATURA_019(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            cTipoComp_in,
                            vNumDocumento_in,
                            nMonto_in,
                            vRuc_in,
                            vRazonSocial_in,
                            vFechaEmision,
                            vFechaOperacion,
                            vIdUsu_in,
                            Vseriedocumento_in,
                            vdescmotivo_in,
                            ctipomoneda_in);
    ELSIF cCodCuadratura_in = '020' THEN
      VALIDA_CUADRATURA_020(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            cCodLocalNuevo_in,
                            nMonto_in,
                            cCodAutorizacion_in,
                            vIdUsu_in,
                            vdescmotivo_in,
                            ctipomoneda_in);
    ELSIF cCodCuadratura_in = '021' THEN
      VALIDA_CUADRATURA_021(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                                  --cSerie_in,
                                  cTipoComp_in,
                                  --cNumComp_in,
                                  nMonto_in,
                                  cCodCia_in,
                                  cCodTrabajador_in,
                                  vMontoParcial_in,
                                  vIdUsu_in,
                                  vdescmotivo_in,
                                  ctipomoneda_in,
                                  cNumeroPedido_in);
    ELSIF cCodCuadratura_in = '024' THEN
      VALIDA_CUADRATURA_024(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            nMonto_in,
                            cCodCia_in,
                            cCodTrabajador_in,
                            vIdUsu_in,
                            Ccodautorizacion_In,
                            Vdescmotivo_in,
                            Ctipomoneda_In);

    ELSIF cCodCuadratura_in = '025' THEN
      VALIDA_CUADRATURA_025(cCodGrupoCia_in,cCodLocal_in,vFecha_in,cCodCuadratura_in,
                            nMonto_in,
                            cCodCia_in,
                            cCodTrabajador_in,
                            vIdUsu_in,
                            Ccodautorizacion_In,
                            Vdescmotivo_in,
                            Ctipomoneda_In);
    ELSIF cCodCuadratura_in = '023' THEN
      VALIDA_CUADRATURA_023(cCodGrupoCia_in,
                            cCodLocal_in ,
                            vFecha_in ,
                            cCodCuadratura_in ,
                            nMonto_in ,
                            cTipoMoneda_in ,
                            Vseriedocumento_in ,
                            cTipoComp_in ,
                            cNumComp_in ,
                            vDescMotivo_in ,
                            vCodProveedor_in ,
                            vIdUsu_in,
                            vMontoPerdido_in);

    END IF;

  END;

  /****************************************************************************/
  FUNCTION GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in	IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  vFecha_in  IN VARCHAR2)
  RETURN NUMBER
  IS
    v_nSec CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
  BEGIN
    SELECT NVL(MAX(SEC_CUADRATURA_CIERRE_DIA),0) + 1
      INTO v_nSec
    FROM CE_CUADRATURA_CIERRE_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND FEC_CIERRE_DIA_VTA = TO_DATE(vFecha_in,'dd/MM/yyyy');
    RETURN v_nSec;
  END;

  /****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_011(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cTipoMoneda_in IN CHAR,
                            cSecEntidadFinanciera_in IN CHAR,
                            nMonto_in IN NUMBER,
                            vFechaOperacion IN VARCHAR2,
                            vNumeroOperacion IN VARCHAR2,
                            vNomAgencia_in IN VARCHAR2,
                            vIdUsu_in IN VARCHAR2,
                            v_DescMotivo_in VARCHAR2,
                            cFormaPago_in IN CHAR)
  AS
    v_nOperacion INTEGER;
    v_cTipMoneda CE_CUADRATURA_CIERRE_DIA.TIP_MONEDA%TYPE;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_ValorSunaT ce_cuadratura.valor_sunat%TYPE;
    V_REFERENCIA CE_CUADRATURA_CIERRE_DIA.NUM_REFERENCIA%TYPE;
    v_suma ce_forma_pago_entrega.mon_entrega%TYPE;
    v_cCodEntidadFinanciera CE_ENTIDAD_FINANCIERA_CUENTA.COD_ENTIDAD_FINANCIERA%TYPE;

    vNumOper varchar2(220);
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    /*SELECT COUNT(*)
      INTO v_nOperacion
    FROM CE_CUADRATURA_CIERRE_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_LOCAL = cCodLocal_in
          AND NUM_OPERACION = vNumeroOperacion
          AND SEC_ENT_FINAN_CUENTA = cSecEntidadFinanciera_in;*/

     SELECT nvl(SUM(F.MON_ENTREGA),0) INTO v_suma
     FROM   CE_FORMA_PAGO_ENTREGA F,
            VTA_FORMA_PAGO P
     WHERE  F.COD_GRUPO_CIA = CCODGRUPOCIA_IN
     AND    F.COD_LOCAL = CCODLOCAL_IN
     AND    F.SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA
                              FROM   CE_MOV_CAJA A
                              WHERE  A.COD_GRUPO_CIA = CCODGRUPOCIA_IN
                              AND    A.COD_LOCAL = CCODLOCAL_IN
                              AND    A.FEC_DIA_VTA = TO_DATE(vFecha_in,'dd/MM/yyyy')
                              AND    A.TIP_MOV_CAJA = TIP_MOV_CIERRE)
     AND    F.COD_GRUPO_CIA = P.COD_GRUPO_CIA
     AND    F.COD_FORMA_PAGO = P.COD_FORMA_PAGO
     AND    F.EST_FORMA_PAGO_ENT = 'A'
     AND    P.IND_FORMA_PAGO_EFECTIVO = INDICADOR_SI
     AND    F.COD_FORMA_PAGO = Cformapago_In
     AND    f.tip_moneda = ctipomoneda_in;

     BEGIN
          SELECT COD_ENTIDAD_FINANCIERA
          INTO   v_cCodEntidadFinanciera
          FROM   CE_ENTIDAD_FINANCIERA_CUENTA
          WHERE  SEC_ENT_FINAN_CUENTA = cSecEntidadFinanciera_in;
     EXCEPTION
          WHEN NO_DATA_FOUND THEN
               v_cCodEntidadFinanciera := '';
     END;

     -----------------------------
     ---SE PEDIRA OBLIGATORIO EL NUMERO DE OPERACION
     -- dubilluz 28.11.2008
     vNumOper := trim(vNumeroOperacion);

     IF vNumOper is null   THEN
        RAISE_APPLICATION_ERROR(
                                -20222,
                                'Debe de ingresar un número de operación. Verifique!'
                               );
     END IF;
     -----------------------------

     IF ( (v_cCodEntidadFinanciera = C_CODIGO_EF_BCP)       AND (LENGTH(vNumeroOperacion) <> 7) ) OR
        ( (v_cCodEntidadFinanciera = C_CODIGO_EF_INTERBANK) AND (LENGTH(vNumeroOperacion) <> 10) ) THEN
      RAISE_APPLICATION_ERROR(-20200,'El número de operación no tiene el formato correcto para la entidad seleccionada. Verifique!'
                                      ||chr(10)||
                                      'El Formato son los siguientes :'||chr(10)||
                                      'BCP       debe ser de :  7 Digitos.'||chr(10)||
                                      'Interbank debe ser de : 10 Digitos.'||chr(10)
                                      );
     END IF;

     IF ( (v_cCodEntidadFinanciera = C_CODIGO_EF_BCP)       AND (LENGTH(vNumeroOperacion) <> 7) ) OR
        ( (v_cCodEntidadFinanciera = C_CODIGO_EF_INTERBANK) AND (LENGTH(vNumeroOperacion) <> 10) ) THEN
      --RAISE_APPLICATION_ERROR(-20200,'El número de operación no tiene el formato correcto para la entidad seleccionada. Verifique!');
      RAISE_APPLICATION_ERROR(-20200,'El número de operación no tiene el formato correcto para la entidad seleccionada. Verifique!'
                                      ||chr(10)||
                                      'El Formato son los siguientes :'||chr(10)||
                                      'BCP       debe ser de :  7 Digitos.'||chr(10)||
                                      'Interbank debe ser de : 10 Digitos.'||chr(10)
                                      );


     END IF;

     /*IF v_nOperacion > 0 THEN
      RAISE_APPLICATION_ERROR(-20093,'Esta operación ya está registrado en un cierre. ¡Verifique!');
     */
     IF Nmonto_In <> v_suma AND NOT (cformapago_in = 00001 OR cformapago_in = 00002) THEN
      RAISE_APPLICATION_ERROR(-20094,'El monto ingresado no corresponde a la forma de pago ¡Verifique!');

     ELSIF cformapago_in = 00001 AND ctipomoneda_in = 02 THEN
      RAISE_APPLICATION_ERROR(-20095,'El tipo de moneda no corresponde a la forma de pago ¡Verifique!');

     ELSIF cformapago_in = 00002 AND ctipomoneda_in = 01 THEN
      RAISE_APPLICATION_ERROR(-20095,'El tipo de moneda no corresponde a la forma de pago ¡Verifique!');
     ELSE

       BEGIN
            SELECT TIP_MONEDA INTO v_cTipMoneda
            FROM CE_ENTIDAD_FINANCIERA_CUENTA
            WHERE SEC_ENT_FINAN_CUENTA = cSecEntidadFinanciera_in;
       EXCEPTION
            WHEN NO_DATA_FOUND THEN
                 v_cTipMoneda := ctipomoneda_in;
       END;

      IF v_cTipMoneda ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

      v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

      SELECT VALOR_SUNAT INTO V_VALORSUNAT
      FROM CE_CUADRATURA
      WHERE COD_GRUPO_CIA = CCODGRUPOCIA_IN
      AND   COD_CUADRATURA = CCODCUADRATURA_IN;

      V_REFERENCIA := V_VALORSUNAT||'-'||CCODLOCAL_IN||'-'||TO_CHAR(to_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'ddMMyyyy');
      -- KMONCADA 19.09.2014 PERMITE INGRESAR VOUCHER DE REMITOS DESDE EL MISMO DIA DE CIERRE.
      IF trunc(to_date(vFecha_in,'dd/mm/yyyy HH24:MI:SS')) > trunc(to_date(vFechaOperacion,'dd/mm/yyyy HH24:MI:SS'))   THEN
--      IF trunc(to_date(vFecha_in,'dd/mm/yyyy HH24:MI:SS')) >= trunc(to_date(vFechaOperacion,'dd/mm/yyyy HH24:MI:SS'))   THEN
        RAISE_APPLICATION_ERROR(
                                -20201,
--                                'No puede ingresar una fecha de voucher que sea igual o menor al de dia de la venta, Verifique!');
                                'No puede ingresar una fecha de voucher que sea menor al dia de la venta, Verifique!');
      END IF;

      IF  to_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS') < SYSDATE-30 THEN
          RAISE_APPLICATION_ERROR(-20100,'La fecha de operacion debe estar en el rango de 30 dias atrás. ¡Verifique!');
      END IF;

      --INSERTA CUADRATURA_CIERRE_DIA
      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA ,
                                           COD_LOCAL ,
                                           FEC_CIERRE_DIA_VTA	,
                                           SEC_CUADRATURA_CIERRE_DIA	,
                                           COD_CUADRATURA	,
                                           TIP_MONEDA	,
                                           MON_MONEDA_ORIGEN	,
                                           MON_TOTAL	,
                                           SEC_ENT_FINAN_CUENTA	,
                                           FEC_OPERACION	,
                                           NUM_OPERACION	,
                                           NOM_AGENCIA	,
                                           MON_PARCIAL,
                                           DESC_MOTIVO,
                                           MES_PERIODO,
                                           ANO_EJERCICIO,
                                           NUM_REFERENCIA,
                                           USU_CREA_CUADRA_CIERRE_DIA,
                                           COD_FORMA_PAGO)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_nTipoCambio,
            cSecEntidadFinanciera_in,TO_DATE(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS'),vNumeroOperacion,
            vNomAgencia_in,nMonto_in*v_nTipoCambio,v_Descmotivo_In,To_char(TO_date(Vfechaoperacion,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfechaoperacion,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),V_REFERENCIA ,vIdUsu_in,Cformapago_In);
     END IF;
  END;

  /****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_012(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  vFechaEmision IN VARCHAR2,
                                  vNumDocumento_in IN VARCHAR2,
                                  cCodServicio_in IN CHAR,
                                  vNomTitular_in IN VARCHAR2,
                                  vFechaOperacion IN VARCHAR2,
                                  nMonto_in IN NUMBER,
                                  vIdUsu_in IN VARCHAR2,
                                  cCodProveedor_in IN CHAR,
                                  vFechaVencimiento_in IN VARCHAR2,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR)
  IS
    v_nServicio INTEGER;
    v_nProveedor INTEGER;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_ValorSunaT ce_cuadratura.valor_sunat%TYPE;
    V_REFERENCIA CE_CUADRATURA_CIERRE_DIA.NUM_REFERENCIA%TYPE;
    v_numDocumento ce_cuadratura_cierre_dia.num_documento%TYPE;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    SELECT COUNT(*)
      INTO v_nServicio
    FROM CE_CUADRATURA_CIERRE_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          AND COD_LOCAL = cCodLocal_in
          AND COD_SERVICIO = cCodServicio_in
          AND NUM_DOCUMENTO= vNumDocumento_in;

    SELECT COUNT(*) INTO v_nproveedor
    FROM   ce_servicio_proveedor c
    WHERE  c.cod_servicio = ccodservicio_in
    AND    c.cod_proveedor = ccodproveedor_in;

    IF  to_date(vFechaEmision,'dd/MM/yyyy HH24:MI:SS') > to_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS') THEN
        RAISE_APPLICATION_ERROR(-20095,'La fecha del documento no puede ser mayor a la fecha de operacion. ¡Verifique!');
    END IF;

    IF  to_date(vFechaVencimiento_in,'dd/MM/yyyy HH24:MI:SS') < to_date(vFechaEmision,'dd/MM/yyyy HH24:MI:SS') THEN
        RAISE_APPLICATION_ERROR(-20099,'La fecha de vencimiento no puede ser menor que la fecha de emision. ¡Verifique!');
    END IF;


    IF v_nServicio > 0 THEN
      RAISE_APPLICATION_ERROR(-20093,'Este recibo ya está registrado en un cierre. ¡Verifique!');

    ELSIF v_Nproveedor = 0 THEN
      RAISE_APPLICATION_ERROR(-20094,'El proveedor no corresponde al servicio indicado. ¡Verifique!');
    ELSE

      IF  to_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS') < SYSDATE-30 THEN
          RAISE_APPLICATION_ERROR(-20100,'La fecha de operacion debe estar en el rango de 30 dias atrás. ¡Verifique!');
      END IF;


      SELECT VALOR_SUNAT INTO V_VALORSUNAT
      FROM CE_CUADRATURA
      WHERE COD_GRUPO_CIA = CCODGRUPOCIA_IN
      AND   COD_CUADRATURA = CCODCUADRATURA_IN;

      IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;


      v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

      IF length(vNumDocumento_in)> 7 THEN
      v_numDocumento := substr(vNumDocumento_in,length(vNumDocumento_in)-6);
      ELSE
      v_numDocumento :=  FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(vNumDocumento_in),7,'0','I');
      END IF ;

      V_REFERENCIA := V_VALORSUNAT||'-'||C_CSERVICIOS_BASICOS||'-'||v_numDocumento;

      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            FEC_OPERACION,
                                            FEC_EMISION	,
                                            NOM_TITULAR_SERVICIO	,
                                            MON_PARCIAL	,
                                            COD_SERVICIO,
                                            USU_CREA_CUADRA_CIERRE_DIA	,
                                            NUM_DOCUMENTO,
                                            Cod_Proveedor,
                                            Fec_Vencimiento,
                                            Desc_Motivo,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,
                                            NUM_REFERENCIA)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,TO_DATE(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS'),
            TO_DATE(vFechaEmision,'dd/MM/yyyy'),vNomTitular_in,nMonto_in*v_nTipoCambio,cCodServicio_in,
            vIdUsu_in,vNumDocumento_in,ccodproveedor_in,TO_DATE(Vfechavencimiento_in,'dd/MM/yyyy'),Vdescmotivo_in,
            To_char(TO_date(Vfechaoperacion,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfechaoperacion,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),V_REFERENCIA);

    END IF;
  END;

  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_013(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            nMonto_in IN NUMBER,
                            cCodAutorizacion_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR)
  IS
    v_nAutorizacion INTEGER;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nValor INTEGER;
    V_REFERENCIA CE_CUADRATURA_CIERRE_DIA.NUM_REFERENCIA%TYPE;
    v_ValorSunaT ce_cuadratura.valor_sunat%TYPE;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    v_nValor := VALIDA_COD_AUTORIZACION(cCodLocal_in,cCodAutorizacion_in,vFecha_in);

    /*SELECT COUNT(*)
      INTO v_nAutorizacion
    FROM CE_CUADRATURA_CIERRE_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_AUTORIZACION = cCodAutorizacion_in;

    IF v_nAutorizacion > 0 THEN
      RAISE_APPLICATION_ERROR(-20093,'Este códgio ya está registrado en un cierre. ¡Verifique!');*/
    IF v_nValor <> 1 THEN
      RAISE_APPLICATION_ERROR(-20105,'EL CODIGO NO TIENE EL FORMATO INDICADO.');
    ELSE

      IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;


      SELECT VALOR_SUNAT INTO V_VALORSUNAT
      FROM CE_CUADRATURA
      WHERE COD_GRUPO_CIA = CCODGRUPOCIA_IN
      AND   COD_CUADRATURA = CCODCUADRATURA_IN;
      v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

      V_REFERENCIA := V_VALORSUNAT||'-'||CCODLOCAL_IN||'-'||TO_CHAR(to_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'ddMMyyyy');

      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_AUTORIZACION	,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,
                                            NUM_REFERENCIA,
                                            Desc_Motivo)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,cCodAutorizacion_in,
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),V_REFERENCIA,vdescmotivo_in);

    END IF;
  END;
  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_014(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            nMonto_in IN NUMBER,
                            cCodCia_in IN CHAR,
                            cCodTrabajador_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            cCodAutorizacion_in IN CHAR,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR)
  IS
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nValor INTEGER;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    v_nValor := VALIDA_COD_AUTORIZACION(cCodLocal_in,cCodAutorizacion_in,vFecha_in);
    IF v_nValor <> 1 THEN
      RAISE_APPLICATION_ERROR(-20105,'EL CODIGO NO TIENE EL FORMATO INDICADO.');
    END IF;

    v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);
     IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

    INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_CIA	,
                                            COD_TRAB		,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,Desc_Motivo,Cod_Autorizacion)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,cCodCia_in,cCodTrabajador_in,
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),vdescmotivo_in,ccodautorizacion_in);

  END;
  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_016(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodTrabajador_in IN CHAR,
                            vFechaOperacion IN VARCHAR2,
                            nMonto_in IN NUMBER,
                            vDescMotivo_in IN VARCHAR2,
                            cCodAutorizacion_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR)
  IS
    v_nAutorizacion INTEGER;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nValor INTEGER;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    v_nValor := VALIDA_COD_AUTORIZACION(cCodLocal_in,cCodAutorizacion_in,vFecha_in);

    /*SELECT COUNT(*)
      INTO v_nAutorizacion
    FROM CE_CUADRATURA_CIERRE_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_AUTORIZACION = cCodAutorizacion_in;

    IF v_nAutorizacion > 0 THEN
      RAISE_APPLICATION_ERROR(-20093,'Este códgio ya está registrado en un cierre. ¡Verifique!');*/
    IF v_nValor <> 1 THEN
      RAISE_APPLICATION_ERROR(-20105,'EL CODIGO NO TIENE EL FORMATO INDICADO.');
    ELSE
      v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

       IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

      IF  to_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS') < SYSDATE-30 THEN
          RAISE_APPLICATION_ERROR(-20100,'La fecha de operacion debe estar en el rango de 30 dias atrás. ¡Verifique!');
      END IF;

      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_CIA	,
                                            COD_TRAB	,
                                            FEC_OPERACION	,
                                            DESC_MOTIVO	,
                                            COD_AUTORIZACION	,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,cCodCia_in,cCodTrabajador_in,
            TO_DATE(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS'),vDescMotivo_in,cCodAutorizacion_in,
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS'),'yyyy'));
    END IF;
  END;
  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_017(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cFormaPago_in IN CHAR,
                            cTipoMoneda_in IN CHAR,
                            nMonto_in IN NUMBER,
                            vIdUsu_in IN VARCHAR2,
                            vSecUsuLocal_in IN CHAR,
                           vDescMotivo_in IN VARCHAR2)
  IS
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
    v_cCodCiaTrab             PBL_USU_LOCAL.COD_CIA%TYPE;
    v_cCodTrab                PBL_USU_LOCAL.COD_TRAB%TYPE;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    IF (cTipoMoneda_in = '01' AND cFormaPago_in = '00002') OR
        (cTipoMoneda_in = '02' AND cFormaPago_in = '00001') THEN
      RAISE_APPLICATION_ERROR(-20100,'Seleccione correctamente la Moneda y la Forma Pago');
    END IF;

    IF cTipoMoneda_in ='02' THEN --DOLARES
      SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
      v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
    END IF;

    SELECT NVL(U.COD_CIA,''),
           NVL(U.COD_TRAB,'')
    INTO   v_cCodCiaTrab,
           v_cCodTrab
    FROM   PBL_USU_LOCAL U
    WHERE  U.COD_GRUPO_CIA = cCodGrupoCia_in
    AND    U.COD_LOCAL = cCodLocal_in
    AND    U.SEC_USU_LOCAL = vSecUsuLocal_in;

    v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_FORMA_PAGO	,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,
                                            COD_CIA,
                                            COD_TRAB,Desc_Motivo)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_nTipoCambio,cFormaPago_in,
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(vFecha_in,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(vFecha_in,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),v_ccodciatrab,v_ccodtrab,vDescMotivo_in);
  END;

  /****************************************************************************/

   PROCEDURE VALIDA_CUADRATURA_018(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cTipoBillete_in IN CHAR,
                                  cTipoMoneda_in IN CHAR,
                                  vSerieBillete_in IN VARCHAR2,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vMontoParcial_in IN NUMBER,
                                  vIdUsu_in IN VARCHAR2,
                                  vDescMotivo_in IN VARCHAR2)
  AS
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_vSerieBillete CE_CUADRATURA_CAJA.SERIE_BILLETE%TYPE := '';
    v_nSumaParcial CE_CUADRATURA_CIERRE_DIA.MON_PARCIAL%TYPE;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN

    IF cTipoBillete_in = '01' AND vSerieBillete_in IS NULL THEN --BILLETE
      RAISE_APPLICATION_ERROR(-20097,'Debe ingresar la Serie del Billete.');
    ELSIF cTipoBillete_in = '01' AND cTipoMoneda_in ='02' AND NOT
          (nMonto_in = 1 OR nMonto_in = 5 OR nMonto_in = 10 OR nMonto_in = 20 OR nMonto_in = 50 OR nMonto_in = 100)THEN
      RAISE_APPLICATION_ERROR(-20098,'Verifique la Denominación del Billete.');
    ELSIF cTipoBillete_in = '01' AND cTipoMoneda_in ='01' AND NOT
          (nMonto_in = 10 OR nMonto_in = 20 OR nMonto_in = 50 OR nMonto_in = 100 OR nMonto_in = 200)THEN
      RAISE_APPLICATION_ERROR(-20099,'Verifique la Denominación del Billete.');
   ELSIF cTipoBillete_in = '02' AND cTipoMoneda_in ='01' AND NOT --MONEDA
         (nMonto_in = 1 OR nMonto_in = 2 OR nMonto_in = 5 OR nMonto_in = 0.1 OR nMonto_in = 0.50 OR nMonto_in = 0.20)THEN
      RAISE_APPLICATION_ERROR(-20100,'Verifique la Denominación de la Moneda');
    ELSIF cTipoBillete_in = '02' AND cTipoMoneda_in ='02' AND
         (nMonto_in <> 0 )THEN
      RAISE_APPLICATION_ERROR(-20101,'No puede ingresar Monedas en Dolares');
    END IF;

    IF cTipoMoneda_in ='02' THEN --DOLARES
      SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
      v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
    END IF;

    IF cTipoBillete_in = '01' THEN
      v_vSerieBillete := vSerieBillete_in;
    END IF;

    IF vMontoParcial_in > (nMonto_in*v_nTipoCambio)  THEN
      RAISE_APPLICATION_ERROR(-20100,'No debe ingresar un monto a descontar, mayor al valor del dinero ingresado.');
    ELSE
      SELECT NVL(SUM(MON_PARCIAL),0)
        INTO v_nSumaParcial
      FROM CE_CUADRATURA_CIERRE_DIA
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND FEC_CIERRE_DIA_VTA = TO_DATE(vFecha_in,'dd/MM/yyyy')
            AND TIP_DINERO = cTipoBillete_in
            AND SERIE_BILLETE = v_vSerieBillete
            AND TIP_MONEDA = cTipoMoneda_in
            AND MON_MONEDA_ORIGEN = nMonto_in;

      IF (v_nSumaParcial+vMontoParcial_in) > (nMonto_in*v_nTipoCambio) THEN
        RAISE_APPLICATION_ERROR(-20100,'La suma de montos parciales a descontar, supera el valor del dinero ingresado.');
      END IF;
    END IF;

    v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);
    INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_CIA	,
                                            COD_TRAB	,
                                            SERIE_BILLETE	,
                                            TIP_DINERO	,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,Desc_Motivo)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_nTipoCambio,
            cCodCia_in,cCodTrabajador_in,v_vSerieBillete,cTipoBillete_in,
            vMontoParcial_in,vIdUsu_in,To_char(TO_date(vFecha_in,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(vFecha_in,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),vdescmotivo_in);
   END;
  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_019(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cTipoComp_in IN CHAR,
                            vNumDocumento_in IN VARCHAR2,
                            nMonto_in IN NUMBER,
                            vRuc_in IN VARCHAR2,
                            vRazonSocial_in IN VARCHAR2,
                            vFechaEmision IN VARCHAR2,
                            vFechaOperacion IN VARCHAR2,
                            vIdUsu_in IN VARCHAR2,
                            vSerieDocumento IN CHAR,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR)
  IS
    v_nDoc INTEGER;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nSerie ce_cuadratura_cierre_dia.num_serie_documento%TYPE;
    v_nNumDocumento ce_cuadratura_cierre_dia.num_documento%TYPE;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSerieRefer CHAR(5);
    v_nNumDocRefer CHAR(7);
    v_nNumReferencia CHAR(20);
    --AÑADIDO
    n_Cant   NUmber;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    SELECT COUNT(*)
      INTO v_nDoc
    FROM CE_CUADRATURA_CIERRE_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
          AND TIP_COMP = cTipoComp_in
          AND NUM_DOCUMENTO = vNumDocumento_in
          AND DESC_RUC = vRuc_in;

    IF v_nDoc > 0 THEN
      RAISE_APPLICATION_ERROR(-20093,'Este documento ya está registrado en un cierre. ¡Verifique!');
    ELSIF vRazonSocial_in IS NULL THEN
      RAISE_APPLICATION_ERROR(-20103,'Debe ingresar la razón social.');
    ELSIF vRuc_in IS NULL THEN
      RAISE_APPLICATION_ERROR(-20103,'Debe ingresar el ruc.');
    ELSE

      IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

      v_nSerie := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(Vseriedocumento),4,'0','I');
      v_nSerieRefer := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(Vseriedocumento),5,'0','I');

      IF length(Vnumdocumento_In) < 9 THEN
      v_nNumDocumento := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(Vnumdocumento_In),8,'0','I');
      ELSIF length(Vnumdocumento_In) > 8 THEN
      v_nNumDocumento := Vnumdocumento_In;
      END IF;

      IF length(vNumDocumento_in)> 7 THEN
      v_nNumDocRefer := substr(vNumDocumento_in,length(vNumDocumento_in)-6);
      ELSE
      v_nNumDocRefer :=  FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(vNumDocumento_in),7,'0','I');
      END IF ;

      v_Nnumreferencia := v_nSerieRefer ||'-'||v_nNumDocRefer ;

      v_nSecCuadratura:= GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

      IF  to_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS') < SYSDATE-30 THEN
          RAISE_APPLICATION_ERROR(-20100,'La fecha de operacion debe estar en el rango de 30 dias atrás. ¡Verifique!');
      END IF;

      IF  trunc(to_date(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS')) > to_date(vFecha_in,'dd/MM/yyyy') THEN
        RAISE_APPLICATION_ERROR(-20095,'La fecha de operacion no puede ser mayor a la fecha del cierre. ¡Verifique!');
      END IF;

      IF  to_date(vFechaEmision,'dd/MM/yyyy') > to_date(vFecha_in,'dd/MM/yyyy') THEN
        RAISE_APPLICATION_ERROR(-20096,'La fecha de emision no puede ser mayor a la fecha del cierre. ¡Verifique!');
      END IF;




      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            TIP_DOCUMENTO	,
                                            NUM_DOCUMENTO	,
                                            DESC_RUC	,
                                            DESC_RAZON_SOCIAL	,
                                            FEC_OPERACION	,
                                            FEC_EMISION	,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,
                                            Num_Serie_Documento,
                                            Num_Referencia,
                                            Desc_Motivo)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,
            cTipoComp_in,v_nNumDocumento,vRuc_in,vRazonSocial_in,
            TO_DATE(vFechaOperacion,'dd/MM/yyyy HH24:MI:SS'),TO_DATE(vFechaEmision,'dd/MM/yyyy'),
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(Vfechaoperacion,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfechaoperacion,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),v_nSerie,TRIM(v_Nnumreferencia),vdescmotivo_in);

        --Añadido para enviar Email si el RUC no existe

    select count(*) into n_Cant
    from   ce_mae_prov
    where  desc_ruc = vRuc_in;

    if n_Cant = 0 THEN

    PTOVENTA_CE_ERN.INT_ENVIA_CORREO_INFORMACION
       (cCodGrupoCia_in,cCodLocal_in,
       ' NUEVO RUC EN LOCAL ',
       'ALERTA',
       'RUC INGRESADO EN EL LOCAL NO EXISTE EN EL MAESTRO DE PROVEEDORES DE QS .'||
       '<BR>USUARIOS DE CAJA ELECTRONICA , POR FAVOR COORDINAR PARA QUE SE ENVIE A CREAR EL RUC EN QS'||'</B>'||
       '<BR><BR> FECHA DE REGISTRO  : '|| TO_DATE(vFecha_in,'dd/MM/yyyy') ||--|| '</B>'||
       '<BR> RUC &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp  &nbsp  : '|| vRuc_in  || --'</B>'||--||' <BR>'||
       '<BR> RAZON SOCIAL   &nbsp &nbsp  &nbsp &nbsp &nbsp : '|| vRazonSocial_in  );--|| '</B>'||);--||'<BR>' );
    end if;

    END IF;
  END;

  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_020(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFecha_in IN VARCHAR2,
                            cCodCuadratura_in IN CHAR,
                            cCodLocalNuevo_in IN CHAR,
                            nMonto_in IN NUMBER,
                            cCodAutorizacion_in IN CHAR,
                            vIdUsu_in IN VARCHAR2,
                            vDescMotivo_in IN VARCHAR2,
                            cTipoMoneda_in IN CHAR)
  AS
    v_nAutorizacion INTEGER;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nValor INTEGER;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    v_nValor := VALIDA_COD_AUTORIZACION(cCodLocal_in,cCodAutorizacion_in,vFecha_in);

    /*SELECT COUNT(*)
      INTO v_nAutorizacion
    FROM CE_CUADRATURA_CIERRE_DIA
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
          AND COD_AUTORIZACION = cCodAutorizacion_in;

    IF v_nAutorizacion > 0 THEN
      RAISE_APPLICATION_ERROR(-20093,'Este código ya está registrado en un cierre. ¡Verifique!');*/
    IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

    IF v_nValor <> 1 THEN
      RAISE_APPLICATION_ERROR(-20105,'EL CODIGO NO TIENE EL FORMATO INDICADO.');
    ELSE
      v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);

      INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_AUTORIZACION	,
                                            COD_LOCAL_NUEVO	,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,Desc_Motivo)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,
            cCodAutorizacion_in,cCodLocalNuevo_in,
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),vdescmotivo_in);
    END IF;
  END;

  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_021(cCodGrupoCia_in	IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  --cSerie_in IN CHAR,
                                  cTipoComp_in IN CHAR,
                                  --cNumComp_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vMontoParcial_in IN NUMBER,
                                  vIdUsu_in IN VARCHAR2,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR,
                                  cNumeroPedido_in IN CHAR)
  AS
--    v_cComprobante CE_CUADRATURA_CAJA.NUM_COMP_PAGO%TYPE;
    v_cNumPedido Ce_Cuadratura_Cierre_Dia.Num_Ped_Vta%TYPE;
    v_nPedido INTEGER;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nSumaParcial CE_CUADRATURA_CIERRE_DIA.MON_PARCIAL%TYPE;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
    v_cNumPedido := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumeroPedido_in),10,'0','I');

    SELECT COUNT(*)
      INTO v_nPedido
    FROM  VTA_PEDIDO_VTA_CAB CAB
    WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
          AND CAB.COD_LOCAL = cCodLocal_in
          AND CAB.NUM_PED_VTA = v_cNumPedido
          AND CAB.VAL_NETO_PED_VTA = nMonto_in
          AND CAB.TIP_COMP_PAGO = cTipoComp_in
          AND CAB.TIP_PED_VTA = '01'
          AND CAB.EST_PED_VTA = 'C'
          AND CAB.IND_PEDIDO_ANUL = 'N';

    IF v_nPedido <> 1 THEN
      RAISE_APPLICATION_ERROR(-20101,'No se encuentra el numero de Pedido.');
    END IF;

    IF vMontoParcial_in > nMonto_in  THEN
      RAISE_APPLICATION_ERROR(-20100,'No debe ingresar un monto a descontar, mayor al valor del comprobante ingresado.');
    ELSE

      IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

      SELECT NVL(SUM(MON_PARCIAL),0)
        INTO v_nSumaParcial
      FROM CE_CUADRATURA_CIERRE_DIA
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND EST_CUADRAT_C_DIA='A' -- RHERRERA: FILTRAR LAS CUADRATURAS PARA ESTADO ACTIVO
            AND COD_LOCAL = cCodLocal_in
            AND COD_CUADRATURA = cCodCuadratura_in
            --AND NUM_SERIE_LOCAL = cSerie_in
            AND TIP_COMP = cTipoComp_in
            AND NUM_PED_VTA = v_cNumPedido;

      IF (v_nSumaParcial+vMontoParcial_in) > nMonto_in THEN
        RAISE_APPLICATION_ERROR(-20100,'La suma de montos parciales a descontar, supera el valor del numero de pedido ingresado.');
      END IF;

      v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);
    INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_CIA	,
                                            COD_TRAB	,
                                            --NUM_SERIE_LOCAL	,
                                            TIP_COMP	,
                                            --NUM_COMP_PAGO,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,Desc_Motivo,
                                            NUM_PED_VTA)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,
            cCodCia_in,cCodTrabajador_in/*,
            cSerie_in*/,cTipoComp_in,/*v_cComprobante,*/
            vMontoParcial_in,vIdUsu_in,To_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),vDescMotivo_in,v_cNumPedido);
    END IF;
  END;

  FUNCTION VALIDA_COD_AUTORIZACION(cCodLocal_in IN CHAR,cCodAutorizacion_in IN CHAR,vFecha_in IN VARCHAR)
  RETURN INTEGER
  IS
    v_nValor INTEGER;
    v_eControlFormato EXCEPTION;
    v_cPrefijo CHAR(3);   
    v_cLocal CHAR(3);
    v_cAnho CHAR(2);
    v_cMes CHAR(2);
    v_cDia CHAR(2);
    v_cGuion CHAR(1);
    v_cSecuencial CHAR(4);
  BEGIN
       IF LENGTH(cCodAutorizacion_in) <> 14 THEN
          RAISE v_eControlFormato;
       ELSE
          v_cPrefijo := SUBSTR(cCodAutorizacion_in,1,3);     
			--ERIOS 18.05.2016 Cambios de JMELGAR
          IF v_cPrefijo = 'ALB' THEN          
             v_cDia    := SUBSTR(cCodAutorizacion_in,4,2);
             v_cMes := SUBSTR(cCodAutorizacion_in,6,2);
             v_cAnho := SUBSTR(cCodAutorizacion_in,8,2); 
             v_cGuion:= SUBSTR(cCodAutorizacion_in,10,1); 
             v_cSecuencial := SUBSTR(cCodAutorizacion_in,11,4);             
             IF v_cPrefijo <> 'ALB' THEN
                RAISE v_eControlFormato;
             ELSIF (TO_DATE(vFecha_in,'dd/MM/yyyy')-TO_DATE(v_cAnho||v_cMes||v_cDia, 'yyMMdd'))>2 OR  (TO_DATE(vFecha_in,'dd/MM/yyyy')-TO_DATE(v_cAnho||v_cMes||v_cDia, 'yyMMdd'))<0 THEN                      
                RAISE v_eControlFormato;
             ELSIF v_cGuion<>'-' THEN
                  RAISE v_eControlFormato;
             ELSIF TO_NUMBER(v_cSecuencial,'9999') <= 0 THEN
               RAISE v_eControlFormato;
             ELSE
               v_nValor := 1;         
             END IF;             
          ELSE
              v_cLocal := SUBSTR(cCodAutorizacion_in,4,3);
              v_cAnho := SUBSTR(cCodAutorizacion_in,7,2);
              v_cMes := SUBSTR(cCodAutorizacion_in,9,2);
              v_cSecuencial := SUBSTR(cCodAutorizacion_in,11,4);    
              IF v_cPrefijo <> 'TES' THEN
                RAISE v_eControlFormato;
              ELSIF cCodLocal_in <>  v_cLocal THEN
                RAISE v_eControlFormato;
              ELSIF TO_DATE(v_cAnho||v_cMes, 'yyMM') <> TRUNC(TO_DATE(vFecha_in,'dd/MM/yyyy'), 'MONTH') THEN
                RAISE v_eControlFormato;
              ELSIF TO_NUMBER(v_cSecuencial,'9999') <= 0 THEN
                RAISE v_eControlFormato;
              ELSE
                v_nValor := 1;
              END IF;    
          END IF;
       END IF;
    RETURN v_nValor;
  EXCEPTION
    WHEN v_eControlFormato THEN
      RAISE_APPLICATION_ERROR(-20105,'EL CODIGO NO TIENE EL FORMATO INDICADO.');
  END;

  /****************************************************************************/

  PROCEDURE VALIDA_CUADRATURA_024(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vIdUsu_in IN VARCHAR2,
                                  cCodAutorizacion_in IN CHAR,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR)
  IS
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nValor INTEGER;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    v_nValor := VALIDA_COD_AUTORIZACION(cCodLocal_in,cCodAutorizacion_in,vFecha_in);
    IF v_nValor <> 1 THEN
      RAISE_APPLICATION_ERROR(-20105,'EL CODIGO NO TIENE EL FORMATO INDICADO.');
    END IF;

    v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);
     IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

    INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_CIA	,
                                            COD_TRAB		,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,Desc_Motivo,Cod_Autorizacion)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,cCodCia_in,cCodTrabajador_in,
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),vdescmotivo_in,ccodautorizacion_in);

  END;

  PROCEDURE VALIDA_CUADRATURA_025(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cCodCia_in IN CHAR,
                                  cCodTrabajador_in IN CHAR,
                                  vIdUsu_in IN VARCHAR2,
                                  cCodAutorizacion_in IN CHAR,
                                  vDescMotivo_in IN VARCHAR2,
                                  cTipoMoneda_in IN CHAR)
  IS
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_nValor INTEGER;
	vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    v_nValor := VALIDA_COD_AUTORIZACION(cCodLocal_in,cCodAutorizacion_in,vFecha_in);
    IF v_nValor <> 1 THEN
      RAISE_APPLICATION_ERROR(-20105,'EL CODIGO NO TIENE EL FORMATO INDICADO.');
    END IF;

    v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);
     IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
      END IF;

    INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            COD_CIA	,
                                            COD_TRAB		,
                                            MON_PARCIAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,Desc_Motivo,Cod_Autorizacion)
      VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
            cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,cCodCia_in,cCodTrabajador_in,
            nMonto_in*v_nTipoCambio,vIdUsu_in,To_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'MM'),
            to_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),vdescmotivo_in,ccodautorizacion_in);

  END;


  PROCEDURE VALIDA_CUADRATURA_023(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in IN CHAR,
                                  vFecha_in IN VARCHAR2,
                                  cCodCuadratura_in IN CHAR,
                                  nMonto_in IN NUMBER,
                                  cTipoMoneda_in IN CHAR,
                                  cSerie_in IN CHAR,
                                  cTipoComp_in IN CHAR,
                                  cNumComp_in IN CHAR,
                                  vDescMotivo_in IN VARCHAR2,
                                  cCodProveedor_in IN CHAR,
                                  vIdUsu_in IN VARCHAR2,
                                  nMontoPerdido_in IN NUMBER)
   IS

   v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
   v_nTipoCambio CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
   v_cComprobante CE_CUADRATURA_CAJA.NUM_COMP_PAGO%TYPE;
   v_nTotalImporte VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
   v_nComp INTEGER;
   vCodCia PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    --v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
    IF LENGTH(cSerie_in) >= 4 THEN
					   v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),8,'0','I');
						ELSE
						v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
					END IF;
						--FAC-ELECTRONICA :09.10.2014
    v_nSecCuadratura := GET_SECUENCIAL_CIERRE_DIA(cCodGrupoCia_in,cCodLocal_in,vFecha_in);
     IF cTipoMoneda_in ='02' THEN --DOLARES
        SELECT COD_CIA INTO vCodCia FROM PBL_LOCAL WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in;
        v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in,vCodCia,vFecha_in,'V');
     END IF;

     SELECT COUNT(*)INTO v_nComp
     FROM   VTA_COMP_PAGO COMP, VTA_PEDIDO_VTA_CAB CAB
     WHERE  COMP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND    COMP.COD_LOCAL = cCodLocal_in
     --AND    COMP.NUM_COMP_PAGO = cSerie_in||v_cComprobante
     AND     Farma_Utility.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,COMP.NUM_COMP_PAGO_E,COMP.NUM_COMP_PAGO)
                 = cSerie_in||v_cComprobante
                 					--FAC-ELECTRONICA :09.10.2014
     AND    COMP.TIP_COMP_PAGO = cTipoComp_in
     AND    COMP.VAL_NETO_COMP_PAGO+COMP.VAL_REDONDEO_COMP_PAGO = nMonto_in
     AND    CAB.TIP_PED_VTA = '02'
     AND    COMP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
     AND    COMP.COD_LOCAL = CAB.COD_LOCAL
     AND    COMP.NUM_PED_VTA = CAB.NUM_PED_VTA;

     SELECT FPP.IM_TOTAL_PAGO INTO v_nTotalImporte
     FROM   VTA_FORMA_PAGO_PEDIDO FPP,
            VTA_COMP_PAGO COMP_PAGO
     WHERE  --COMP_PAGO.NUM_COMP_PAGO = cSerie_in||v_cComprobante
            Farma_Utility.GET_T_COMPROBANTE(
               COMP_PAGO.COD_TIP_PROC_PAGO,COMP_PAGO.NUM_COMP_PAGO_E,COMP_PAGO.NUM_COMP_PAGO)
                 = cSerie_in||v_cComprobante
                 					--FAC-ELECTRONICA :09.10.2014
     AND    FPP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND    FPP.COD_LOCAL = cCodLocal_in
     AND    COMP_PAGO.TIP_COMP_PAGO = cTipoComp_in
     AND    FPP.COD_GRUPO_CIA = COMP_PAGO.COD_GRUPO_CIA
     AND    FPP.COD_LOCAL = COMP_PAGO.COD_LOCAL
     AND    FPP.NUM_PED_VTA = COMP_PAGO.NUM_PED_VTA;

    IF v_nComp <> 1 THEN
      RAISE_APPLICATION_ERROR(-20101,'Documento no encontrado.');
    END IF;

    IF nMontoPerdido_in > v_nTotalImporte THEN
      RAISE_APPLICATION_ERROR(-20102,'El monto perdido no debe ser mayor al valor total de pago');
    END IF ;

       INSERT INTO CE_CUADRATURA_CIERRE_DIA(COD_GRUPO_CIA	,
                                            COD_LOCAL	,
                                            FEC_CIERRE_DIA_VTA	,
                                            SEC_CUADRATURA_CIERRE_DIA	,
                                            COD_CUADRATURA	,
                                            TIP_MONEDA	,
                                            MON_MONEDA_ORIGEN	,
                                            MON_TOTAL	,
                                            USU_CREA_CUADRA_CIERRE_DIA,
                                            MES_PERIODO,
                                            ANO_EJERCICIO,
                                            Desc_Motivo,
                                            COD_PROVEEDOR,
                                            NUM_SERIE_LOCAL,
                                            TIP_COMP,
                                            NUM_COMP_PAGO,
                                            MON_PARCIAL,
                                            Mon_Perdido,MON_PERDIDO_TOTAL)
       VALUES(cCodGrupoCia_in,cCodLocal_in,TO_DATE(vFecha_in,'dd/MM/yyyy'),v_nSecCuadratura,
              cCodCuadratura_in,cTipoMoneda_in,nMonto_in,nMonto_in*v_ntipocambio,
              vIdUsu_in,
              To_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'MM'),
              to_char(TO_date(Vfecha_In,'dd/MM/yyyy HH24:MI:SS'),'yyyy'),
              vdescmotivo_in,CCODPROVEEDOR_IN,
              Cserie_In,CTIPOCOMP_IN,V_CCOMPROBANTE,nMonto_in*v_nTipoCambio,nMontoPerdido_in,nMontoPerdido_in*v_nTipoCambio);
   END;
  /*****************************************************************************/
  PROCEDURE VALIDA_CUADRATURA_023_NEW(cCodGrupoCia_in   IN CHAR,
                                      cCodLocal_in      IN CHAR,
                                      cSecMovCaja_in    IN CHAR,
                                      vFecha_in         IN VARCHAR2,
                                      cCodCuadratura_in IN CHAR,
                                      nMonto_in         IN NUMBER,
                                      cTipoMoneda_in    IN CHAR,
                                      cSerie_in         IN CHAR,
                                      cTipoComp_in      IN CHAR,
                                      cNumComp_in       IN CHAR,
                                      vDescMotivo_in    IN VARCHAR2,
                                      cCodProveedor_in  IN CHAR,
                                      vIdUsu_in         IN VARCHAR2,
                                      nMontoPerdido_in  IN NUMBER) IS
  
    v_nSecCuadratura CE_CUADRATURA_CIERRE_DIA.SEC_CUADRATURA_CIERRE_DIA%TYPE;
    v_nTipoCambio    CE_TIP_CAMBIO.VAL_TIPO_CAMBIO%TYPE := 1;
    v_cComprobante   CE_CUADRATURA_CAJA.NUM_COMP_PAGO%TYPE;
    v_nTotalImporte  VTA_FORMA_PAGO_PEDIDO.IM_TOTAL_PAGO%TYPE;
    v_nComp          INTEGER;
    vCodCia          PBL_LOCAL.COD_CIA%TYPE;
  BEGIN
    --VALIDA CUADRATURA
    --v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in),7,'0','I');
    IF LENGTH(cSerie_in) >= 4 THEN
      v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in), 8, '0', 'I');
    ELSE
      v_cComprobante := FARMA_UTILITY.COMPLETAR_CON_SIMBOLO(TO_NUMBER(cNumComp_in), 7, '0', 'I');
    END IF;
    --FAC-ELECTRONICA :09.10.2014
    v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,cSecMovCaja_in);
    
    IF cTipoMoneda_in = '02' THEN
      --DOLARES
      SELECT COD_CIA
        INTO vCodCia
        FROM PBL_LOCAL
       WHERE COD_GRUPO_CIA = cCodGrupoCia_in
         AND COD_LOCAL = cCodLocal_in;
      v_nTipoCambio := FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(cCodGrupoCia_in, vCodCia, vFecha_in, 'V');
    END IF;
  
    SELECT COUNT(*)
      INTO v_nComp
      FROM VTA_COMP_PAGO COMP, VTA_PEDIDO_VTA_CAB CAB
     WHERE COMP.COD_GRUPO_CIA = cCodGrupoCia_in
       AND COMP.COD_LOCAL = cCodLocal_in
          --AND    COMP.NUM_COMP_PAGO = cSerie_in||v_cComprobante
       AND Farma_Utility.GET_T_COMPROBANTE(COMP.COD_TIP_PROC_PAGO,
                                           COMP.NUM_COMP_PAGO_E,
                                           COMP.NUM_COMP_PAGO) =
           cSerie_in || v_cComprobante
          --FAC-ELECTRONICA :09.10.2014
       AND COMP.TIP_COMP_PAGO = cTipoComp_in
       AND COMP.VAL_NETO_COMP_PAGO + COMP.VAL_REDONDEO_COMP_PAGO =
           nMonto_in
       AND CAB.TIP_PED_VTA = '02'
       AND COMP.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
       AND COMP.COD_LOCAL = CAB.COD_LOCAL
       AND COMP.NUM_PED_VTA = CAB.NUM_PED_VTA;
  
    SELECT SUM(FPP.IM_TOTAL_PAGO)
      INTO v_nTotalImporte
      FROM VTA_FORMA_PAGO_PEDIDO FPP, VTA_COMP_PAGO COMP_PAGO
     WHERE --COMP_PAGO.NUM_COMP_PAGO = cSerie_in||v_cComprobante
     Farma_Utility.GET_T_COMPROBANTE(COMP_PAGO.COD_TIP_PROC_PAGO,
                                     COMP_PAGO.NUM_COMP_PAGO_E,
                                     COMP_PAGO.NUM_COMP_PAGO) =
     cSerie_in || v_cComprobante
    --FAC-ELECTRONICA :09.10.2014
     AND FPP.COD_GRUPO_CIA = cCodGrupoCia_in
     AND FPP.COD_LOCAL = cCodLocal_in
     AND COMP_PAGO.TIP_COMP_PAGO = cTipoComp_in
     AND FPP.COD_GRUPO_CIA = COMP_PAGO.COD_GRUPO_CIA
     AND FPP.COD_LOCAL = COMP_PAGO.COD_LOCAL
     AND FPP.NUM_PED_VTA = COMP_PAGO.NUM_PED_VTA;
  
    IF v_nComp <> 1 THEN
      RAISE_APPLICATION_ERROR(-20101, 'Documento no encontrado.');
    END IF;
  
    IF nMontoPerdido_in > v_nTotalImporte THEN
      RAISE_APPLICATION_ERROR(-20102, 'El monto perdido no debe ser mayor al valor total de pago');
    END IF;
  /*
(COD_GRUPO_CIA, COD_LOCAL, SEC_MOV_CAJA,
SEC_CUADRATURA_CAJA, COD_CUADRATURA, MON_MONEDA_ORIGEN,
MON_TOTAL, TIP_MONEDA, COD_FORMA_PAGO,
USU_CREA_CUADRATURA_CAJA,DESC_MOTIVO)
  */
    INSERT INTO CE_CUADRATURA_CAJA
      (COD_GRUPO_CIA,
       COD_LOCAL,
       SEC_MOV_CAJA,
       SEC_CUADRATURA_CAJA,
       COD_CUADRATURA,
       TIP_MONEDA,
       MON_MONEDA_ORIGEN,
       MON_TOTAL,
       USU_CREA_CUADRATURA_CAJA,
       Desc_Motivo,
       COD_PROVEEDOR,
       NUM_SERIE_LOCAL,
       TIP_COMP,
       NUM_COMP_PAGO,
       MON_PARCIAL,
       Mon_Perdido,
       MON_PERDIDO_TOTAL,
       EST_CUADRATURA_CAJA)
    
    VALUES
      (cCodGrupoCia_in,
       cCodLocal_in,
       cSecMovCaja_in,
       v_nSecCuadratura,
       cCodCuadratura_in,
       cTipoMoneda_in,
       nMonto_in,
       nMonto_in*v_ntipocambio,
       vIdUsu_in,
       vdescmotivo_in,
       CCODPROVEEDOR_IN,
       Cserie_In,
       CTIPOCOMP_IN,
       V_CCOMPROBANTE,
       nMonto_in * v_nTipoCambio,
       nMontoPerdido_in,
       nMontoPerdido_in * v_nTipoCambio,
       'A');
  END;
  /****************************************************************************/
 PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in     IN CHAR,
                                        vAsunto_in       IN CHAR,
                                        vTitulo_in       IN CHAR,
                                        vMensaje_in      IN CHAR)
  AS

    ReceiverAddress VARCHAR2(30) := FARMA_EMAIL.GET_RECEIVER_ADDRESS_INTER_CE;
    CCReceiverAddress VARCHAR2(120) := NULL;
    mesg_body VARCHAR2(32767);
    v_vDescLocal VARCHAR2(120);
  BEGIN

    --DESCRIPCION DE LOCAL
    SELECT COD_LOCAL ||' - '|| DESC_LOCAL
    INTO   v_vDescLocal
    FROM   PBL_LOCAL
    WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
    AND    COD_LOCAL = cCodLocal_in;

    --ENVIA MAIL
    mesg_body := '<L><B>' || vMensaje_in || '</B></L>'  ;

    FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                             ReceiverAddress,
                             vAsunto_in||v_vDescLocal,--'VIAJERO EXITOSO: '||v_vDescLocal,
                             vTitulo_in,--'EXITO',
                             mesg_body,
                             CCReceiverAddress,
                             FARMA_EMAIL.GET_EMAIL_SERVER,
                             true);

  END;

  /****************************************************************************/

  PROCEDURE CE_P_INS_CUADRA_COTI_COMP(cCodGrupoCia_in    IN CHAR,
                                       cCodLocal_in      IN CHAR,
                                       cCodCuadratura_in IN CHAR,
                                       cMontoTotal_in    IN NUMBER,
                                       cNumSec_in        IN CHAR,
                                       cUsuCrea_in       IN CHAR,
                                       cGlosa_in         IN CHAR,
                                       secMovCaja_in     IN CHAR)
   IS
     v_nSecCuadratura CE_CUADRATURA_CAJA.SEC_CUADRATURA_CAJA%TYPE;
     v_ValorSunaT ce_cuadratura.valor_sunat%TYPE;
     V_REFERENCIA CE_CUADRATURA_CIERRE_DIA.NUM_REFERENCIA%TYPE;
     fechaTurno ce_mov_caja.fec_dia_vta%TYPE;
   BEGIN

    SELECT VALOR_SUNAT INTO V_VALORSUNAT
    FROM  CE_CUADRATURA
    WHERE COD_GRUPO_CIA = CCODGRUPOCIA_IN
    AND   COD_CUADRATURA = CCODCUADRATURA_IN;

    SELECT a.fec_dia_vta
    INTO fechaTurno
    FROM ce_mov_caja a
    WHERE a.sec_mov_caja = secMovCaja_in
    AND a.cod_grupo_cia = cCodGrupoCia_in
    AND a.cod_local = cCodLocal_in;

    V_REFERENCIA := V_VALORSUNAT||'-'||CCODLOCAL_IN||'-'||TO_CHAR(fechaTurno,'ddMMyyyy');

    v_nSecCuadratura := GET_SECUENCIAL_CUADRATURA(cCodGrupoCia_in,cCodLocal_in,secMovCaja_in);
    /*
    CAMPOS PARA CIERRE DIA          CAMPOS EQUIVALENTES PARA CIERRE TURNO
    ======================================================================
    COD_GRUPO_CIA                   lleno - COD_GRUPO_CIA
    COD_LOCAL                       lleno - COD_LOCAL
    FEC_CIERRE_DIA_VTA              lleno - *****no hay*****
    SEC_CUADRATURA_CIERRE_DIA       lleno - SEC_CUADRATURA_CAJA
    COD_CUADRATURA                  lleno - COD_CUADRATURA
    TIP_MONEDA                      lleno - TIP_MONEDA
    MON_MONEDA_ORIGEN               lleno - MON_MONEDA_ORIGEN
    MON_TOTAL                       lleno - MON_TOTAL
    NUM_NOTA_ES                     lleno - *****no hay*****
    Mon_Parcial                     lleno - *****no hay*****
    USU_CREA_CUADRA_CIERRE_DIA      lleno - USU_CREA_CUADRATURA_CAJA
    Desc_Motivo                     lleno - DESC_MOTIVO
    MES_PERIODO                     lleno - *****no hay*****
    ANO_EJERCICIO                   lleno - *****no hay*****
    NUM_REFERENCIA                  lleno - *****no hay*****
    */
      INSERT INTO CE_CUADRATURA_CAJA(COD_GRUPO_CIA, COD_LOCAL, COT_FEC_CIERRE_DIA_VTA,
                                     SEC_CUADRATURA_CAJA,COD_CUADRATURA,
                                     TIP_MONEDA,MON_MONEDA_ORIGEN,MON_TOTAL,COT_NUM_NOTA_ES,
                                     COT_MON_PARCIAL,USU_CREA_CUADRATURA_CAJA,Desc_Motivo,
                                     COT_MES_PERIODO,
                                     COT_ANO_EJERCICIO,COT_NUM_REFERENCIA,
                                     Sec_Mov_Caja)
                            VALUES   (ccodgrupocia_in,ccodlocal_in,fechaTurno,
                                      V_NSECCUADRATURA,ccodcuadratura_in,'01',cmontototal_in,
                                      cmontototal_in,Cnumsec_In,cmontototal_in,
                                      cusucrea_in,cglosa_in,To_char(fechaTurno,'MM'),
                                      to_char(fechaTurno,'yyyy'),v_referencia,
                                      secMovCaja_in);
    END;

    /****************************************************************************/
    --## Autor     : Ever Maquera
    --## Creado    : 08/07/2015
    --## Proposito : 
  FUNCTION CE_OBTIENE_MSJE_CT(cCodCajero   IN CHAR,
                              cNomCajero   IN CHAR,
                              cNumCaja     IN CHAR,
                              cMontoDef    IN CHAR) RETURN FarmaCursor
    AS
    MSJE varchar2(1000);
    MSJE_NEW varchar2(1000);
    V_SQL varchar2(1000):='';
    POS_F  NUMBER;
    vDni   CHAR(10);
    FILA   NUMBER:=1;
    curMsje FarmaCursor;
    BEGIN
    
    SELECT DNI_USU INTO vDni FROM PBL_USU_LOCAL
    WHERE SEC_USU_LOCAL = cCodCajero;
    
    msje :='Yo, '||UPPER(cNomCajero)||', identificado con DNI Nro '||TRIM(vDni)||', DEJO CONSTANCIA que la Liquidación de la Caja '||cNumCaja||',\'||
           'de la que soy responsable,  presenta un faltante por la suma de S/. '||TRIM(cMontoDef)||' , en tal sentido, AUTORIZO \'||
           'a mi empleador proceda a descontar de mi remuneración mensual, liquidación de beneficios sociales de darse el caso y/o\'||
           'hasta de mis Utilidades (si es que la empresa los genera) el monto de este faltante.\';

    SELECT INSTR(msje, '\',1, 1) INTO POS_F FROM DUAL;

      WHILE POS_F > 0
        LOOP
        SELECT SUBSTR(msje, 1, POS_F-1) INTO MSJE_NEW FROM DUAL;
    --    DBMS_OUTPUT.PUT_LINE(MSJE_NEW);    
        SELECT SUBSTR(msje, POS_F+1) INTO MSJE FROM DUAL;
        
        SELECT INSTR(msje, '\',1, 1) INTO POS_F FROM DUAL;
        
          IF POS_F > 0 THEN
             V_SQL:=V_SQL||'SELECT '||FILA||' ORDEN,'''||MSJE_NEW||''' MENSAJE FROM DUAL UNION ';
          ELSE 
             V_SQL:=V_SQL||'SELECT '||FILA||' ORDEN,'''||MSJE_NEW||''' MENSAJE FROM DUAL ';
          END IF;        
          
        FILA := FILA + 1;
        
        END LOOP;
        
        V_SQL:='SELECT MENSAJE FROM ('||V_SQL||') ORDER BY ORDEN ';
        
        OPEN curMsje FOR V_SQL;
        
        RETURN curMsje;
--        DBMS_OUTPUT.PUT_LINE(V_SQL);
    END;
    /****************************************************************************/  
END;
/
