--------------------------------------------------------
--  DDL for Package PKG_FASA_INTERF
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PKG_FASA_INTERF" AS

  TYPE FarmaCursor IS REF CURSOR;
  TYPE t_string_table IS TABLE OF VARCHAR2(32767);

  g_cNumIntVentas PBL_NUMERA.COD_NUMERA%TYPE := '018';
  g_cNumIntPed PBL_NUMERA.COD_NUMERA%TYPE := '022';
  g_cNumIntMov2 PBL_NUMERA.COD_NUMERA%TYPE := '021';
  g_cNumGuiaProv PBL_NUMERA.COD_NUMERA%TYPE := '027';
  g_cNumProdQuiebre INTEGER :=450;
  ARCHIVO_TEXTO UTL_FILE.FILE_TYPE;
  --v_gNombreDiretorio VARCHAR2(50) := 'DIR_INTERFACES';
  v_gNombreDiretorio VARCHAR2(50) := 'DIR_FASA_AJ_RD';

  v_gUltimoDoc VTA_COMP_PAGO.NUM_COMP_PAGO%TYPE := NULL;

  g_cCodMatriz PBL_LOCAL.COD_LOCAL%TYPE := '009';

  --- Begin Variables
  vd_Fec_Ini_Gen_Arch DATE;
  vd_Fec_fin_Gen_Arch DATE;

  --- End Variables
  --Descripcion: Calcula los totales de ventas de Boletas en un dia.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_VENTAS_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene las series asignadas al local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_SERIES_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Obtiene la numeracion de boletas emitidas en un dia para una serie en un local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_NUM_DOC_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Obtiene la numeracion de boletas emitidas en un dia para una serie en un local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_NUM_DOC_REF(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: Obtiene los distintos productos vendidos por boletas.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  --04/12/2007  dubilluz  modificacion
  FUNCTION INT_GET_PROD_VENDIDOS_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los totales por producto.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_TOTALES_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2, cTipVenta_in IN CHAR, cCodProd_in IN CHAR,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene los detalles de fraccion del producto.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_DET_FRAC_PROD(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,cPordFrac_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene la cantidad de presentacion.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
   FUNCTION INT_GET_CANT_PRESENT(cCodGrupoCia_in IN CHAR,cCodProd_in IN CHAR,cPordFrac_in IN CHAR,nValFrac_in IN NUMBER) RETURN NUMBER;
  --FACTURAS--
  --Descripcion: Calcula los totales de ventas de Facturas en un dia.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_VENTAS_FACTURAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene las facturas emitidas.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_FACTURAS_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2,cNumSerie_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el detalle de una factura.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  --04/12/2007  DUBILLUZ  Modificacion
  FUNCTION INT_GET_DET_FACT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecComp_in IN CHAR) RETURN FarmaCursor;
  --NOTA DEVOLUCION--
  --Descripcion: Calcula los totales de ventas de Facturas en un dia.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_VENTAS_DEVOLUCION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene las facturas emitidas.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_DEVOLUCION_LOCAL(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, vFecProceso_in IN VARCHAR2,cNumSerie_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: Obtiene el secuencial.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_SEC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR) RETURN NUMBER;

  --Descripcion: Obtiene el correlativo.
  --Fecha       Usuario		Comentario
  --31/03/2006  ERIOS    	Creación
  FUNCTION INT_GET_CORR(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,nNumSecDoc_in IN NUMBER) RETURN CHAR;

  --Descripcion: Graba el redondeo de una serie de boletas en un local.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE SET_REDONDEO_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cNumCompI_in IN CHAR, cNumCompF_in IN CHAR);

  --Descripcion: Graba el redondeo de una factura.
  --Fecha       Usuario		Comentario
  --16/03/2006  ERIOS    	Creación
  PROCEDURE SET_REDONDEO_FACTURA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR);

  /*EJECUTA RESUMEN DE LOCAL*/
  --Descripcion: Genera los datos por dia.
  --Fecha       Usuario		Comentario
  --17/03/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);

  --Descripcion: Procesa el resumen diario.
  --Fecha       Usuario		Comentario
  --30/03/2006  ERIOS    	Creación
  PROCEDURE INT_EJECT_RESUMEN_DIA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);

  --Descripcion: Setea el numero de documento, en las boletas procesadas.
  --Fecha       Usuario		Comentario
  --04/04/2006  ERIOS    	Creación
  PROCEDURE SET_MUN_SEC_PROCESO_BOLETAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2, nNumSecDoc_in IN NUMBER, cNumCompI_in IN CHAR,cNumCompF_in IN CHAR);

  --Descripcion: Setea el numero de documento, en las facturas procesadas.
  --Fecha       Usuario		Comentario
  --04/04/2006  ERIOS    	Creación
  PROCEDURE SET_MUN_SEC_PROCESO_FACTURAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cSecComp_in IN CHAR, nNumSecDoc_in IN NUMBER);

  --Descripcion: Setea el numero de documento, en los comprobantes anulados.
  --Fecha       Usuario		Comentario
  --04/04/2006  ERIOS    	Creación
  PROCEDURE SET_MUN_SEC_PROCESO_DEVOL(cSecComp_in IN CHAR, nNumSecDoc_in IN NUMBER);

  --INTERFAZ INVENTARIO

  --Descripcion: Obtiene el pedido de reposicion a ser enviado.
  --Fecha       Usuario		Comentario
  --05/04/2006  ERIOS    	CreaciÃ³n
  --03/09/2007  ERIOS  Se movio al paquete PTOVENT_INT_PED_REP
  /*PROCEDURE INT_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2, cIndUrgencia_in IN CHAR DEFAULT 'N',
  cIndPedAprov_in IN CHAR DEFAULT 'N');*/

  --Descripcion: Obtiene el la confirmacion de recepcion de productos.
  --Fecha       Usuario		Comentario
  --07/04/2006  ERIOS    	CreaciÃ³n
  PROCEDURE INT_RESUMEN_REC_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --INTERFAZ MOVIMIENTOS

  --Descripcion: Obtiene los pedidos de transferencias a matriz.
  --Fecha       Usuario		Comentario
  --07/04/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_MOV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 0);

  --NOTA DE CREDITO
  --Descripcion: Obtiene el resumen de las notas de credito.
  --Fecha       Usuario		Comentario
  --21/04/2006  ERIOS    	Creación
  --06/07/2006  ERIOS    	Modificación: Agregado la varriable v_vNumDocAnul_A
  PROCEDURE INT_RESUMEN_NOTA_CREDITO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cTipDoc_in IN CHAR, cTipDocOrigen_in IN CHAR, vFecProceso_in IN VARCHAR2);

  --Descripcion: Obtiene el detalle de la nota de credito.
  --Fecha       Usuario		Comentario
  --21/04/2006  ERIOS    	Creación
  FUNCTION INT_GET_DET_NC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,cTipDoc_in IN CHAR,cTipDocOrigen_in IN CHAR, vFecProceso_in IN VARCHAR2) RETURN FarmaCursor;

  --Descripcion: Obtiene el redondeo de la nota de credito.
  --Fecha       Usuario		Comentario
  --26/04/2006  ERIOS    	Creación
  --06/07/2006  ERIOS    	Modificación: vNumDocAnul_in
  PROCEDURE SET_REDONDEO_NC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR,vNumDocAnul_in IN CHAR);

  --INTERFAZ MOVIMIENTOS 2

  --Descripcion: Obtiene los ajustes de kardex.
  --Fecha       Usuario		Comentario
  --27/04/2006  ERIOS    	Creación
  PROCEDURE INT_RESUMEN_MOV2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);

  --Descripcion: Genera archivo de Interface Rep Ped.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS     	Creación
  --03/09/2007  ERIOS  Se movio al paquete PTOVENT_INT_PED_REP
  /*PROCEDURE INT_GET_RESUMEN_PED_REP(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);*/

  --Descripcion: Genera archivo de Interface Conf Rep Ped.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_REC_PROD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Genera archivo de Interface Devoluciones.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_MOV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Genera archivo de Interface Ajustes.
  --Fecha       Usuario		Comentario
  --28/04/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_MOV2(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,a_cTipAjuste IN CHAR);

  --Descripcion: Obtiene el redondeo de una devolucion.
  --Fecha       Usuario		Comentario
  --11/05/2006  ERIOS    	Creación
  PROCEDURE SET_REDONDEO_DEVOLUCION(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nNumSecDoc_in IN NUMBER, cTipDoc_in IN CHAR, cNumSerie_in IN CHAR,vFecProceso_in IN VARCHAR2,cNumDocRef_in IN CHAR,cSecComp_in IN CHAR);

  --Descripcion: Procesa la interface de ventas para un rango de fechas
  --Fecha       Usuario		Comentario
  --12/05/2006  RCASTRO    	Creación
  PROCEDURE INT_EJECT_RESUMEN_DIA_RANGO (codigocompania_in IN CHAR, cCodLocal_in IN CHAR, fechainicio_in IN CHAR, fechafin_in IN CHAR);

  --Descripcion: Genera los archivos de la interface de ventas para un rango de fechas
  --Fecha       Usuario		Comentario
  --12/05/2006  RCASTRO    	Creación
  PROCEDURE INT_GET_RESUMEN_DIA_RANGO (codigocompania_in IN CHAR, cCodLocal_in IN CHAR, fechainicio_in IN CHAR, fechafin_in IN CHAR);

  --Descripcion: Valida si el monto de ventas de la Interface, es igual al reporte de ventas.
  --Fecha       Usuario		Comentario
  --17/05/2006  ERIOS    	Creación
  PROCEDURE VALIDA_MONTO_VENTAS(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,vFecProceso_in IN VARCHAR2);

  --Descripcion: Verifica si existe registros de comprobantes para un dia especificado
  --Fecha       Usuario		Comentario
  --01/06/2006  ERIOS    	Creación
  FUNCTION INT_VERIFICA_COMP_DIA(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,vFecha_in IN VARCHAR2) RETURN NUMBER;

  --Descripcion: Envia correo de informacion de estado de generacion de interface.
  --Fecha       Usuario		Comentario
  --10/07/2006  ERIOS    	Creación
  PROCEDURE INT_ENVIA_CORREO_INFORMACION(cCodGrupoCia_in 	   IN CHAR,
                                        cCodLocal_in    	   IN CHAR,
                                        vAsunto_in IN VARCHAR2,
                                        vTitulo_in IN VARCHAR2,
                                        vMensaje_in IN VARCHAR2);

  --Descripcion: Procedimiento para revertir interfaz de ventas.
  --Fecha       Usuario		Comentario
  --14/09/2006  ERIOS    	Creación
  PROCEDURE INT_REVERTIR_VENTAS(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                vFecha_in IN VARCHAR2,
                                vIdUsu_in IN VARCHAR2);

  --Descripcion: Genera la clasificación de los productos (material).
  --Fecha       Usuario		Comentario
  --16/11/2006  ERIOS    	Creación
  --04/12/2007  DUBILLUZ  MODIFICACION
  PROCEDURE INT_RESUMEN_CLASMAT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Obtiene el archivo de clasificacion material.
  --Fecha       Usuario		Comentario
  --16/11/2006  ERIOS    	Creación
  PROCEDURE INT_GET_RESUMEN_CLASMAT(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Genera las guías para las ventas de productos sin control de stock.
  --Fecha       Usuario		Comentario
  --05/12/2006  ERIOS    	Creación
  PROCEDURE INT_GENERA_GUIA_PROV(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  nDiasAtras_in IN INTEGER DEFAULT 1);

  --Descripcion: Reemplaza los caracteres no validos.
  --Fecha       Usuario		Comentario
  --12/07/2007  ERIOS    	Creacion
  FUNCTION REEMPLAZAR_CARACTERES(vCadena_in IN VARCHAR2)
  RETURN VARCHAR2;

  --Descripcion: Obtiene los ajustes de kardex del tipo CC.
  --Fecha       Usuario		Comentario
  --11/09/2007  ERIOS    	Creacion
  PROCEDURE INT_RESUMEN_CC(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);

  --Descripcion: Obtiene los ajustes de kardex del tipo RD.
  --Fecha       Usuario		Comentario
  --11/10/2007  ERIOS    	Creacion
  PROCEDURE INT_RESUMEN_RD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);

  --Descripcion: Genera archivo de Interface Ajustes - RD.
  --Fecha       Usuario		Comentario
  --11/10/2006  ERIOS    	Creacion
  PROCEDURE INT_GET_RESUMEN_RD(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);

  --Descripcion: Se verifica y corrige el orden del documento.
  --Fecha       Usuario		Comentario
  --30/01/2008  ERIOS    	Creacion
  PROCEDURE VALIDA_ORDEN_DOCUMENTO(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
  vFecProceso_in IN VARCHAR2);

  --Descripcion:
  --Fecha       Usuario		Comentario
  --11/03/2009  JOLIVA    	Creacion
  FUNCTION INT_GET_IND_FRAC_NVO_CC(cCodGrupoCia_in IN CHAR, cCodProdOrigenCC_in IN CHAR, cCodProdDestinoCC_in IN CHAR) RETURN CHAR;


  --
  --21/04/2009 JCORTEZ  Creacion
   PROCEDURE INT_RES_VTS_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                        cCodLocal_in    IN CHAR,
                                        cTipDoc_in      IN CHAR,
                                        vFecProceso_in  IN VARCHAR2);

  --
  --21/04/2009 JCORTEZ  Creacion
   FUNCTION INT_GET_NUM_DOC_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                            cCodLocal_in    IN CHAR,
                                            cTipDoc_in      IN CHAR,
                                            cNumSerie_in    IN CHAR,
                                            vFecProceso_in  IN VARCHAR2)
   RETURN FarmaCursor;

    --
  --21/04/2009 JCORTEZ  Creacion
  FUNCTION INT_GET_NUM_DOC_REF1(cCodGrupoCia_in IN CHAR,
                                cCodLocal_in IN CHAR,
                                cTipDoc_in IN CHAR,
                                cNumSerie_in IN CHAR,
                                vFecProceso_in IN VARCHAR2,
                                cNumCompI_in IN CHAR,
                                cNumCompF_in IN CHAR)
  RETURN VARCHAR2;

   --
  --21/04/2009 JCORTEZ  Creacion
  PROCEDURE SET_REDONDEO_TICKET_BOLETAS(cCodGrupoCia_in IN CHAR,
                                      cCodLocal_in IN CHAR,
                                      nNumSecDoc_in IN NUMBER,
                                      cTipDoc_in IN CHAR,
                                      cNumSerie_in IN CHAR,
                                      vFecProceso_in IN VARCHAR2,
                                      cNumDocRef_in IN CHAR,
                                      cNumCompI_in IN CHAR,
                                      cNumCompF_in IN CHAR);

    --
  --21/04/2009 JCORTEZ  Creacion
   PROCEDURE INT_RES_VTS_TICKET_DEVOLUCION(cCodGrupoCia_in IN CHAR,
                                          cCodLocal_in IN CHAR,
                                          cTipDoc_in IN CHAR,
                                          vFecProceso_in IN VARCHAR2);

    --
  --21/04/2009 JCORTEZ  Creacion
   PROCEDURE INT_RES_NOTA_CRED_TICKET_BOL(cCodGrupoCia_in IN CHAR,
                                   cCodLocal_in IN CHAR,
                                   cTipDoc_in IN CHAR,
                                   cTipDocOrigen_in IN CHAR,
                                   vFecProceso_in IN VARCHAR2);
/*-------------------------------------------------------------------------------------------------------------------
GOAL : CREAR ARCHIVOS DE TEXTO DE INTERFACE - ORDENES DE COMPRA DIRECTA - MCADERIA GRAL.
DATE : 03-JUL-13
---------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_OCDIR_PROC_ARCH(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);
/*------------------------------------------------------------------------------------------------------------------------
GOAL : ENVIAR EMAIL CON ERRORES
-------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_SEND_MAIL(vAsunto_in  IN VARCHAR2,
                         vTitulo_in  IN VARCHAR2,
                         vMensaje_in IN VARCHAR2);
 /*-------------------------------------------------------------------------------------------------------------
 GOAL : Generar Interface de Devoluciones al Proveedor
 DATE : Crea = 04-JUL-13
 ----------------------------------------------------------------------------------------------------------------*/
  PROCEDURE SP_DEVPRO_INT_FASA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            nDiasAtras_in IN INTEGER DEFAULT 0);
/*-------------------------------------------------------------------------------------------------------------------
GOAL : CREAR ARCHIVOS DE TEXTO DE INTERFACE - DEVOLUCIONES AL PROVEEDOR
DATE : Crea = 04-JUL-13
---------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_DEVPRO_PROC_ARCH(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);
/*------------------------------------------------------------------------------------------------------------------------------
GOAL : PROCESAR DEVOLUCIONES AL CD
DATE : CREA = 04-JUL-13
--------------------------------------------------------------------------------------------------------------------------------- */
PROCEDURE SP_DEVCD_INT_FASA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR,
                            nDiasAtras_in IN INTEGER DEFAULT 0);
/*-------------------------------------------------------------------------------------------------------------------
GOAL : CREAR ARCHIVOS DE TEXTO DE INTERFACE - ORDENES DE COMPRA DIRECTA - CONVENIO
DATE : Crea = 05-JUL-13
---------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_CDIRECONV_PROC_ARCH(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR);
 /*----------------------------------------------------------------------------------------------------------------------
 GOAL: GENERAR INTERFACES DE ORDEN COMPRA DIRECTA
 DATE: Crea = 03-JUL-13
       Modi = 04-JUL-13, procesar compras directa mercaderia general y convenio
 ------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_OCDIR_INT_FASA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);
/*---------------------------------------------------------------------------------------------------------------------
GOAL : DEVOLVER FACTURA_PREIMPRESA  PARA PRODUCTO
DATE : 05-JUL-13
------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_NUM_FACT_PROD(a_cCod_Prod VARCHAR2, a_cNumLote VARCHAR2,a_cFecRef varchar2)
RETURN VARCHAR2;
/*---------------------------------------------------------------------------------------------------------------------
GOAL : DEVOLVER  FECHA DE FACTURA PARA PRODUCTO
DATE : 05-JUL-13
------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_FECH_FACT_PROD(a_cCod_Prod VARCHAR2, a_cNumLote VARCHAR2,a_cFecRef varchar2)
RETURN VARCHAR2;
/*-------------------------------------------------------------------------------------------------------------------------
GOAL : Generar Archivos de Texto para interfaces de Devoluciones al CD
DATE : 12-JUL-13
---------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_DEVCD_INT_GEN_TXT(dFecha IN CHAR DEFAULT TO_CHAR(SYSDATE,'DD/MM/YYYY'));
 /*---------------------------------------------------------------------------------------------------------------
   GOAL : Generar Archivos de Interface de Recarga Virtual
   DATE : Crea = 02-JUL-13
          Mofi = 18-JUL-14
 ---------------------------------------------------------------------------------------------------------------*/
 PROCEDURE SP_RVIRTUAL_INT_FASA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);
/*-------------------------------------------------------------------------------------------------------------------
GOAL : GENERAR ARCHIVOS DE TEXTO DE RECARGA VIRTUAL
DATE: CREA = 18-JUL-13

----------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_RVIRTUAL_PROC_ARCH(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);
/*----------------------------------------------------------------------------------------------------------------------
  GOAL : GENERAR INTERFAZ MOVIMIENTOS 2 - AJUSTES (AJ,CI=CONSUMO INTERNO,AD=AJUSTE DIFERENCIAS)
  DATE : MODI = 15-JUL-13,
-------------------------------------------------------------------------------------------------------------------------*/
 PROCEDURE SP_MOV2_INT_FASA(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, nDiasAtras_in IN INTEGER DEFAULT 2);
/*-------------------------------------------------------------------------------------------------------------------
GOAL : GENERAR ARCHIVOS DE TEXTO DE : AJ,CI,AD
DATE: CREA = 18-JUL-13
----------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_MOV2_PROC_ARCH(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR);
/*---------------------------------------------------------------------------------------------------------------------
GOAL : DEVOLVER FACTURA_SAP  PARA PRODUCTO
DATE : 05-JUL-13
------------------------------------------------------------------------------------------------------------------------*/
FUNCTION FN_GET_NUM_FACT_SAP(a_cCod_Prod VARCHAR2, a_cNumLote VARCHAR2,a_cFecRef varchar2)
RETURN VARCHAR2;
/*-------------------------------------------------------------------------------------------------------------
GOAL : DEVOLVER CLIENTE SAP DE LOCAL
DATE : 26-JUL-13
--------------------------------------------------------------------------------------------------------------- */
FUNCTION FN_GET_COD_CLIENTE_SAP(COD_LOCAL_SAP_IN IN CHAR) RETURN VARCHAR2;
/*----------------------------------------------------------------------------------------------------------------------
GOAL : Reprocesar la Generacion de los Archivos de Interface Devoluciones al CD, ya que no tenian Factura Preimpresa
DATE : Crea = 13-MAY-13
       Modi = 06-AGO-13, se apdapta a Farma Venta, TCT
------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_REPROC_GEN_TXT_DEV_LOC(dFecha IN CHAR DEFAULT TO_CHAR(SYSDATE,'DD/MM/YYYY'));
/*---------------------------------------------------------------------------------------------------------------------
GOAL       : DEVOLVER FACTURA_PREIMPRESA  PARA PRODUCTO
Ammedments :
When          Who       What
06-AGO-13     TCT       Create Procedure
------------------------------------------------------------------------------------------------------------------------*/
PROCEDURE SP_GET_DATOS_PROD(a_cCod_Prod IN VARCHAR2, a_cNumLote  IN  VARCHAR2,
                            a_cFecRef   IN varchar2, a_list_dat  OUT  t_string_table
                            );
/************************************************************************************************************************/
END;

/
