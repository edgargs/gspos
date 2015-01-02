--------------------------------------------------------
--  DDL for Package PTOVENTA_NEW_INT_VTA
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_NEW_INT_VTA" AS

  TYPE FarmaCursor IS REF CURSOR;

  nFch_Ini_New_int_vta date;
  /* ************************************************************** */
  -- COMPROBANTES DE PRE PRODUCCION
  /*
    01	BOLETA
    02	FACTURA
    03	GUIA
    04	NOTA CREDITO
    05	TICKET BOLETA
  */
  COMP_BOLETA CHAR(2) := '01';
  COMP_TK_BOL CHAR(2) := '05';
  COMP_FACTURA CHAR(2) := '02';
  COMP_GUIA CHAR(2) := '03';
  COMP_NC   CHAR(2) := '04';
  /* ************************************************************** */
  -- ESTANDAR para el campo "Tip_Comp_Cliente" de la tabla VTA_COMP_PAGO
  DOC_NORMAL char(1) := '0';
  --- esto es para ventas convenio ----
  DOC_BENEFICIARIO char(1) := '1';
  DOC_EMPRESA char(1) := '2';
  /* ************************************************************** */
  -- CONSTANTES DE INT VENTA.
  C_MANDANTE VARCHAR2(10):= '000';
  C_UNIDAD VARCHAR2(10):= 'UN';
  C_MONEDA_SOLES VARCHAR2(10):= 'PEN';
  C_CONDICION_PAGO VARCHAR2(10):= '    ';

  C_POS_VENTA CHAR(1) := '1';
  C_POS_AJUSTE CHAR(1) := '2';

  TIP_COMP_SAP_SERVICIO  varchar2(5) := '13';
  /* ************************************************************** */

  PROCEDURE INT_EJECT_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);

  FUNCTION AUX_REEMPLAZAR_CHAR(vCadena_in IN VARCHAR2)
  RETURN VARCHAR2;

  PROCEDURE AUX_SAVE_DET_X_COMPROBANTE (
                                    cCodGrupoCia_in in char,
                                    cCodLocal_in in char,
                                    cTipComp_in in char,
                                    cSerie_in in char,
                                    cFechProceso_in in char
                                   );

  PROCEDURE AUX_SAVE_DET_ANULA_X_COMP (
                                      cCodGrupoCia_in in char,
                                      cCodLocal_in in char,
                                      cTipComp_in in char,
                                      cSerie_in in char,
                                      cFechProceso_in in char
                                      );

  PROCEDURE AUX_SAVE_DET_NOTA_CREDITO (
                                        cCodGrupoCia_in in char,
                                        cCodLocal_in in char,
                                        cTipComp_in in char,
                                        cSerie_in in char,
                                        cFechProceso_in in char
                                        );

  /* ********************************************************* */
  -- =======================================================  --
  -- Metodo recursivo de agrupacion para los comprobantes que se deben agrupar.
  -- tanto boletas como Ticket Boletas.
  -- =======================================================  --
  /* ********************************************************* */
  PROCEDURE AUX_AGRUP_0105_MUEVE_KARDEX(
                           cCodGrupoCia_in in char,
                           cCodLocal_in    in char,
                           cTipComp_in     in char,
                           cSerie_in       in char,
                           cCodCliSAP_in   in char
                           );

PROCEDURE AUX_GRABA_REDONDEO(cCodGrupoCia_in IN CHAR,
                             cCodLocal_in    IN CHAR,
                             cNumSecDoc_in   IN VARCHAR2,
                             cNumDocRef_in   IN VARCHAR2,
                             cTipCompSAP_in   IN VARCHAR2,
                             cTotalDocumento_in   IN NUMBER,
                             cTipCompPago_in   IN VARCHAR2,
                             cSerie_in         IN VARCHAR2,
                             cGrupo_Doc_in     IN NUMBER,
                             cFechaProceso_in IN VARCHAR2,
                             cNumDocAnul_in   in varchar2,
                             cIndAnulado_in   in char default 'N',
                             cSecCompOrigen in char default 'N',
                             cNumPedVta_in char default 'N'
                             );

  FUNCTION GET_NUM_DOC_REF(cCodGrupoCia_in IN CHAR,
                                  cCodLocal_in    IN CHAR,
                                  cTipComp_in      IN CHAR,
                                  cNumSerie_in    IN CHAR,
                                  cNumCompI_in    IN CHAR,
                                  cNumCompF_in    IN CHAR,
                                  cTipDoc_SAP_in  IN CHAR,
                                cIndAnulado_in in char
                                  )
  RETURN VARCHAR2;

  FUNCTION GET_SEC_DOC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR)
  RETURN NUMBER;

    FUNCTION GET_TIP_COMP_SAP(cTipCompPago_in IN CHAR,cIndAnulado in char,
                            cOrigenTipComp in char default 'X')
  return varchar2;

  FUNCTION GET_TIP_VTA_SAP(cTipPedVta_in IN CHAR)
  return varchar2;

  FUNCTION GET_COD_SERVICIO_PEDIDO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cNumPedVta_in in char)
  return varchar2;

  PROCEDURE SET_NUN_DOC_SAP_COMP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                                 cSecComp_in     IN CHAR, nNumSecDoc_in IN NUMBER,
                                 cIndAnulado in char
                                 );

  -------------------------------------------------------------------------
  PROCEDURE C_COBRADOS(cCodGrupoCia_in IN CHAR, cCodLocal_in    IN CHAR,vFecProceso_in IN VARCHAR2);

  -- 0.- PEDIDOS COBRADOS BOLETA(01) Y TICKET BOLETA (5)
  PROCEDURE C_0105_COMP_AGRUP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);
  -- 1.- PEDIDOS COBRADOS FACTURA(02)
  PROCEDURE C_02_COMP_NO_AGRUP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);


  -- 2.- DEVOLUCION DE PRODUCTOS DE TODOS LOS TIPOS DE COMPROBANTES ACTUALES 01-Boleta,02-Factura,05
  PROCEDURE A_DEVOLUCIONES(cCodGrupoCia_in IN CHAR,cCodLocal_in    IN CHAR,vFecProceso_in IN VARCHAR2);
  --------------------------------------------------------------------
  PROCEDURE A_NOTAS_CREDITO(cCodGrupoCia_in IN CHAR,
                            cCodLocal_in    IN CHAR,
                            vFecProceso_in IN VARCHAR2);

  PROCEDURE S_SERVICIOS(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 vFecProceso_in IN VARCHAR2);

  PROCEDURE S_SERVICIOS_COBRADOS(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                        vFecProceso_in IN VARCHAR2);

  PROCEDURE S_SERVICIOS_ANULADOS(cCodGrupoCia_in IN CHAR,
                        cCodLocal_in    IN CHAR,
                        vFecProceso_in IN VARCHAR2);

  PROCEDURE S_SERVICIOS_NOTA_CREDITO(cCodGrupoCia_in IN CHAR,
                                 cCodLocal_in    IN CHAR,
                                 vFecProceso_in IN VARCHAR2);

  PROCEDURE ENVIA_CORREO(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);
   /* *********************************************************************** */
FUNCTION V_PREVIAS_INT_VTA(cCodGrupoCia_in IN CHAR,
                              cCodLocal_in    IN CHAR,
                              vFecProceso_in IN VARCHAR2)
  RETURN CHAR;

  PROCEDURE V_MONTO_VENTAS(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in    IN CHAR,
                           vFecProceso_in IN VARCHAR2);


 PROCEDURE GENERA_ARCHIVO(cCodGrupoCia_in IN CHAR,
                          cCodLocal_in IN CHAR,
                          vFecProceso_in IN VARCHAR2);

FUNCTION OBTENER_NUMERACION(cCodGrupoCia_in	 IN CHAR,
							  cCodLocal_in  	 IN CHAR,
							  cCodNumera_in 	 IN CHAR)
  RETURN NUMBER;

  PROCEDURE ACTUALIZAR_NUMERA_SIN_COMMIT(cCodGrupoCia_in	IN CHAR,
										 cCodLocal_in  		IN CHAR,
										 cCodNumera_in 		IN CHAR,
								         vIdUsuario_in 		IN VARCHAR2);

END PTOVENTA_NEW_INT_VTA;

/
