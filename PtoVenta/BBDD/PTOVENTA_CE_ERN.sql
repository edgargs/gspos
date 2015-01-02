--------------------------------------------------------
--  DDL for Package PTOVENTA_CE_ERN
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_CE_ERN" AS

  TYPE FarmaCursor IS REF CURSOR;
  TIP_MOV_CIERRE  	   CE_MOV_CAJA.TIP_MOV_CAJA%TYPE:='C';
  INDICADOR_SI		     CHAR(1):='S';
  C_CSERVICIOS_BASICOS CHAR(5):='00000';

  TIP_PEDIDO_MESON CHAR(2) := '01';
  TIP_PEDIDO_DELIVERY CHAR(2) := '02';

  C_C_INGRESO_COMP_MANUAL CE_CUADRATURA.COD_CUADRATURA%TYPE :='006';

  C_CODIGO_EF_BCP       CE_ENTIDAD_FINANCIERA.COD_ENTIDAD_FINANCIERA%TYPE :='001';
  C_CODIGO_EF_INTERBANK CE_ENTIDAD_FINANCIERA.COD_ENTIDAD_FINANCIERA%TYPE :='002';

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
                                      vMotivo_in VARCHAR2
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

END;

/
