--------------------------------------------------------
--  DDL for Package PTOVENTA_RECAUDACION
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "PTOVENTA"."PTOVENTA_RECAUDACION" AS

  TIPO_COMP		    CHAR(2):='99';
  IND_SI	        CHAR(1):='S';
  IND_NO	        CHAR(1):='N';
  IND_ACTIVO		  CHAR(1):='A';

  IND_PENDIENTE   CHAR(1):='P';
  IND_COBRADO     CHAR(1):='C';
  IND_ANULADO     CHAR(1):='N';

  TIPO_REC_VENTA_CMR CHAR(2):='06';
  
    COD_CIA_MARKET_01 CHAR(3):=  '004';                                     --ASOSA - 03/09/2014

  TYPE FarmaCursor IS REF CURSOR;

  --Descripcion: Llena combo de los tipo de pago de CMR
  --Fecha        Usuario		    Comentario
  --08/04/2013   Luigy Terrazos Creación
  FUNCTION RCD_TIPO_PAGO_CMR
  RETURN FarmaCursor;

  --Descripcion: Retorna correlativo de recaudacion
  --Fecha        Usuario		    Comentario
  --08/04/2013   Luigy Terrazos Creación
  FUNCTION RCD_CORRE_COD_RECAU(cCodGrupoCia_in          IN CHAR,
                               cCod_Cia_in              IN CHAR,
                               cCod_Local_in            IN CHAR)
  RETURN CHAR;

  --Descripcion: Guarda la cabecera de la recaudacion.
  --Fecha        Usuario		Comentario
  --09/04/2013   Luigy Terrazos Creación
  --PROCEDURE RCD_GRAB_CAB(cCodGrupoCia_in          IN CHAR,
  FUNCTION RCD_GRAB_CAB(cCodGrupoCia_in          IN CHAR,
							  			   cCod_Cia_in              IN CHAR,
                         cCod_Local_in            IN CHAR,
                         cNro_Tarjeta_in          IN CHAR,
                         cTipo_Rcd_in             IN CHAR,
                         cTipo_Pago_in            IN CHAR,
                         cEst_Rcd_in              IN CHAR,
                         cEst_Cuenta_in           IN VARCHAR2,
                         cCod_Cliente_in          IN VARCHAR2,
                         cTip_Moneda_in           IN CHAR,
                         nIm_Total_in             IN NUMBER,
                         nIm_Total_Pago_in        IN NUMBER,
                         nIm_Min_Pago_in          IN NUMBER,
                         nVal_Tip_Camb_in         IN NUMBER,
                         cFecha_Venc_Recau_in     IN CHAR,
                         cNom_Cliente_in          IN VARCHAR2,
                         cFec_Ven_Trj_in          IN CHAR,
                         cFec_Crea_Recau_Pago_in  IN CHAR,
                         cUsu_Crea_Recau_Pago_in  IN VARCHAR2,
                         cFec_Mod_Recau_Pago_in   IN CHAR,
                         cUsu_Mod_Recau_Pago_in   IN VARCHAR2,
                         cCod_Autorizacion_in     IN VARCHAR2,
                         cSec_Mov_Caja_in         IN VARCHAR2,
                         cNum_Pedido_in           IN VARCHAR2,
                         cTipo_Prod_Serv_in       IN CHAR,
                         cNum_Recibo_in           IN CHAR)
  RETURN CHAR;

  --Descripcion: Muestra informacion del pedido de la recaudacion
  --Fecha        Usuario		    Comentario
  --08/04/2013   Luigy Terrazos Creación
  FUNCTION RCD_LISTA_RCD_PEND(cCodGrupoCia_in          IN CHAR,
                             cCod_Cia_in              IN CHAR,
                             cCod_Local_in            IN CHAR,
                             cCod_Recau_in            IN CHAR)
  RETURN FarmaCursor;

  --Descripcion: Lista los codigos de las formas de pago
  --Fecha        Usuario		    Comentario
  --11/04/2013   Luigy Terrazos Creación
  FUNCTION RCD_LISTA_COD_FOM_PAGO
  RETURN FarmaCursor;

  --Descripcion: Graba las formas de pago
  --Fecha        Usuario		    Comentario
  --12/04/2013   Luigy Terrazos Creación
  PROCEDURE RCD_GRAB_FOM_PAGO(cCodGrupoCia_in          IN CHAR,
                              cCod_Cia_in              IN CHAR,
                              cCod_Local_in            IN CHAR,
                              cCod_Recau_in            IN CHAR,
                              cCod_Forma_Pago_in       IN VARCHAR2,
                              cImp_Total_in            IN NUMBER,
                              cTip_Moneda_in           IN CHAR,
                              cVal_Tip_Cambio_in       IN NUMBER,
                              cVal_Vuelto              IN NUMBER,
                              cIm_Total_Pago_in        IN NUMBER,
                              cFec_Crea_Recau_Pago_in  IN CHAR,
                              cUsu_Crea_Recau_Pago_in  IN VARCHAR2,
                              cFec_Mod_Recau_Pago_in   IN CHAR,
                              Cusu_Mod_Recau_Pago_In   In Varchar2
                              --,Csec_Mov_Caj_In          In Char
                              );

  --Descripcion: Cambia el estado del pedido de recaudacion
  --Fecha        Usuario		    Comentario
  --12/04/2013   Luigy Terrazos Creación
  PROCEDURE RCD_CAMBIAR_PED_RCD(cCodGrupoCia_in          IN CHAR,
                                cCod_Cia_in              IN CHAR,
                                cCod_Local_in            IN CHAR,
                                cCod_Recau_in            IN CHAR,
                                cInd_Recau_in            IN CHAR,
                                cUsu_Mod_Rcd_in          IN CHAR,
                                cFecha_Mod_Rcd_in        IN CHAR,
                                cEstado_ImpRecaudacion_In IN CHAR,
                                cCod_Trssc_In            IN CHAR,
                                cEst_TrsscSix_in            IN CHAR,
                                cCod_Autorizacion_in     IN CHAR,
                                cSecMovCaja_in           IN CHAR,
                                cFech_Orig_in            IN CHAR,
                                cNro_Cuotas_in           IN CHAR,
                                cFec_Venc_Cuota_in       IN CHAR,
                                cMonto_Pagar_Cuota_in    IN NUMBER);

  --Descripcion: Lista la recaudaciones pagadas
  --Fecha        Usuario		    Comentario
  --18/04/2013   Luigy Terrazos Creación
  FUNCTION RCD_LISTA_RCD_CANCE(cCodGrupoCia_in          IN CHAR,
                               cCod_Cia_in              IN CHAR,
                               cCod_Local_in            IN CHAR,
                               cCod_Rcd_in              IN CHAR,
                               cMonto_Rcd_in            IN NUMBER,
                               cTipoCobro_in            IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: Arma la trama de consulta para Recaudacion Prestamos Citibank
  --Fecha        Usuario		    Comentario
  --14/06/2013   Gilder Fonseca Creación
  FUNCTION RCD_TRAMA_CONSULTA_CITI_PRES(cFlag_Cod_in IN varchar2,
                                        cCod_in IN varchar2,
                                        cTip_Moneda_in IN varchar2,
                                        cTip_trans_in IN varchar2,
                                        cCod_local_in IN varchar2)
  RETURN VARCHAR2;

  --Descripcion: Devuelve una cadena con los valores procesados de la trama recibida del servidor central
  --Fecha        Usuario		    Comentario
  --14/06/2013   Gilder Fonseca Creación
  FUNCTION RCD_PROCESA_TRAMA_PRES_CITI(cTrama_PresCiti_in  IN varchar2)
  RETURN FarmaCursor;

  --Descripcion: Obtiene los Bin permitidos para RECAUDACION
  --Fecha        Usuario		     Comentario
  --17/09/2013   Gilder Fonseca  Creación
  FUNCTION RCD_F_OBTENER_BIN_TARJETA(cCodGrupoCia_in IN CHAR,
                                     cCodTarj_in     IN VARCHAR,
                                     cTipOrigen_in   IN VARCHAR)
                                     RETURN FarmaCursor;

  --Descripcion: Realiza la anulacion de una recaudacion, registrando una copia en negativo y actualizando el estado
  --Fecha        Usuario		    Comentario
  --14/06/2013   Gilder Fonseca Creación
  FUNCTION RCD_ANULAR_RECAUDACION(cCodGrupoCia_in IN CHAR,
                                  cCod_Cia_in IN CHAR,
                                  cCod_Local_in IN CHAR,
                                  cCodRecau_in IN CHAR,
                                  nNumCajaPago_in IN CHAR,
                                  nUsuModRecauPago_in IN CHAR,
                                  nCod_TrsscAnul_in IN CHAR,
								  cCodRecauAnul_in IN CHAR)
  RETURN CHAR;


   --Descripcion: Actualiza el monto cobrado en el caso recaudacion prestamos citibank
  --Fecha        Usuario		    Comentario
  --26/06/2013   Gilder Fonseca Creación
  FUNCTION RCD_ACTUALIZAR_COBRO_PRES_CITI(cCodGrupoCia_in    IN CHAR,
                                 cCod_Cia_in  IN CHAR,
                                 cCod_Local_in       IN CHAR,
                                 cCodRecau_in IN CHAR)
                                 return VARCHAR;

   --Descripcion: Obtiene el estado de una recaudacion
  --Fecha        Usuario		    Comentario
  --09/07/2013   Rudy LLantoy Creación
  FUNCTION RCD_GET_EST_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                          Ccod_Cia_In              In Char,
                          cCod_Local_in            IN CHAR,
                          Cnum_Recaudacion_In        In Char)
  Return VARCHAR;

   --Descripcion: Obtiene el HTML de una recaudacion para imprimir
  --Fecha        Usuario		    Comentario
  --09/07/2013   Rudy LLantoy Creación

  FUNCTION RCD_IMP_COMPAGO_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                          cCod_Cia_in              IN CHAR,
                          cCod_Local_in            IN CHAR,
                          cNum_Recaudacion_in        IN CHAR)
  Return  CLOB; --Varchar2;

   --Descripcion: Actualiza una recaudacion
  --Fecha        Usuario		    Comentario
  --09/07/2013   Rudy LLantoy Creación

   PROCEDURE RCD_UPDATE_EST_IMP_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                          cCod_Cia_In              IN CHAR,
                          cCod_Local_In            In Char,
                          Cnum_Recaudacion_In        In Char,
                          cEstado_ImpRecaudacion_In        In Char);


    --Descripcion: Obtiene el estado de una recaudacion
  --Fecha        Usuario		    Comentario
  --09/07/2013   Rudy LLantoy Creación

	 FUNCTION RCD_GET_EST_IMP_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                          Ccod_Cia_In              In Char,
                          cCod_Local_in            IN CHAR,
                          Cnum_Recaudacion_In        In Char)
  Return VARCHAR2;


  --Descripcion: Obtiene el tiempo de anulacion de una recaudacion
  --Fecha        Usuario		    Comentario
  --09/07/2013   Rudy LLantoy Creación
  FUNCTION RCD_TIEMPO_ANUL_RECAUDACION(cCodGrupoCia_in         IN Char,
                                     cCod_Cia_In             In Char,
                                     cCod_Local_in           IN Char,
                                     cNum_Recaudacion_In     In Char)

  RETURN CHAR;

  --Descripcion: Obtiene el tiempo maximo de anulacion para una recaudacion
  --Fecha        Usuario		    Comentario
  --09/07/2013   Rudy LLantoy Creación
  FUNCTION RCD_TIEMPO_MAX_ANULACION(cTipo_llave in char)
  RETURN CHAR;


  --Descripcion: Obtiene el tiempo maximo de anulacion para una recaudacion
  --Fecha        Usuario		    Comentario
  --09/08/2013   GFonseca Creación
  PROCEDURE RCD_ACTUALIZAR_EST_TRSSC_SIX(cCodGrupoCia_in    IN CHAR,
                                 cCod_Cia_in  IN CHAR,
                                 cCod_Local_in       IN CHAR,
                                 cCodRecau_in IN CHAR,
                                 cEstTrsscSix IN CHAR,
                                 cCodAutoriz IN CHAR);


  --Descripcion: Obtener el DNI de un usuario
  --Fecha        Usuario		    Comentario
  --19/08/2013   GFonseca Creación
  FUNCTION RCD_OBTENER_DNI_USU(cSecUsuLocal_in IN CHAR)
  RETURN CHAR;

  --Descripcion: OBTENER EL CODIGO HOMOLOGO DEL LOCAL
  --Fecha        Usuario		    Comentario
  --02/08/2013   Gilder Fonseca Creación
  FUNCTION RCD_OBTENER_COD_LOCAL_MIGRA(cCodGrupoCia_in IN CHAR,
                                       cCod_Cia_in IN CHAR,
                                       cCod_Local_in IN CHAR) RETURN CHAR;

  --Descripcion: RETORNA EL HTML PARA IMPRESION DE VOUCHER
  --Fecha        Usuario		    Comentario
  --19/08/2013   LLEIVA         CREACION
  --26/09/2013   GFonseca       Se agregó el número de cuotas, el monto a pagar por cuota y la fecha de vencimiento para la cuota.
  FUNCTION RCD_IMPRESION_VOUCHER_CMR(cCodGrupoCia_in IN CHAR,
                                     cCod_Cia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cCod_Recau_in IN CHAR,
                                     cNum_Cuotas_in IN CHAR,
                                     cMonto_Pagar_Cuota_in IN CHAR,
                                     cFecha_Venc_Cuota_in IN CHAR,
                                     cCod_Autorizacion_in IN CHAR,
                                     cCod_Caja_in IN CHAR,
                                     cTip_Copia_in IN CHAR) RETURN CLOB;

  --Descripcion: RETORNA EL MONTO TOTAL DE LAS RECAUDACIONES SEGUN EL MOVIMIENTO DE CAJA
  --Fecha        Usuario		    Comentario
  --19/08/2013   LLEIVA         CREACION
  --11/10/2013   GFONSECA       MODIFICACION / Se añade indicador de anulacion
  FUNCTION RCD_OBTENER_MONTO_TOTAL(cCodGrupoCia_in IN CHAR,
                                      cCod_Cia_in IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cSecMovCaja_in  IN CHAR) RETURN CHAR;


  --Descripcion: RETORNA EL LISTADO DE CUOTAS PARA CMR
  --Fecha        Usuario		    Comentario
  --03/09/2013   GFONSECA         CREACION
  FUNCTION RCD_CUOTAS_CMR    RETURN FarmaCursor;


  --Descripcion: RETORNA DATOS DE UNA RECAUDACION VENTA CMR, PARA SU ANULACION
  --Fecha        Usuario		    Comentario
  --04/09/2013   GFONSECA         CREACION
  FUNCTION RCD_GET_DATOS_ANUL_VENTA_CMR(cCodGrupoCia_in          IN CHAR,
                                       cCod_Cia_in              IN CHAR,
                                       cCod_Local_in            IN CHAR,
                                       cNum_Pedido_in            IN CHAR)
  RETURN FarmaCursor;


  --Descripcion: REALIZA LA ANULACION DE UNA RECAUDACION DE VENTA CMR (NO TIENE FORMAS DE PAGO)
  --Fecha        Usuario		    Comentario
  --04/09/2013   GFONSECA         CREACION
  FUNCTION RCD_ANULAR_RECAU_VENTA_CMR(cCodGrupoCia_in IN CHAR,
                                  cCod_Cia_in IN CHAR,
                                  cCod_Local_in IN CHAR,
                                  cCodRecau_in IN CHAR,
                                  nNumCajaPago_in IN CHAR,
                                  nUsuModRecauPago_in IN CHAR,
                                  nCod_TrsscAnul_in IN CHAR)
         RETURN CHAR;

  --Descripcion: Obtiene las formas de pago
  --Fecha        Usuario		    Comentario
  --27/09/2013   ERIOS         CREACION
  FUNCTION GET_FORMAS_PAGO(cCodGrupoCia_in IN CHAR,
                            cCod_Cia_in IN CHAR,
                            cCod_Local_in IN CHAR,
                            cCodRecau_in IN CHAR) RETURN VARCHAR2;

  --Descripcion: RETORNA EL MONTO TOTAL DE LAS RECAUDACIONES SEGUN EL MOVIMIENTO DE CAJA
  --Fecha        Usuario		    Comentario
  --30/09/2013   ERIOS          CREACION
  FUNCTION RCD_OBTENER_MONTO_TOTAL_DIA(cCodGrupoCia_in IN CHAR,
                                      cCodCia_in IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cFecCierreDia_in  IN CHAR) RETURN CHAR;

  --Descripcion: RETORNA UN LISTADO CONSOLIDADO DE LAS RECAUDACIONES PERTENECIENTES A UNA SECUENCIA DE
  --             MOVIMIENTO DE CAJA
  --Fecha        Usuario		    Comentario
  --25/09/2013   LLEIVA         CREACION
  FUNCTION RCD_REPORTE_CIERRE_TURNO(cCodGrupoCia_in   IN CHAR,
                                    cCodCia_in        IN CHAR,
                                    cCodLocal_in      IN CHAR,
                                    cSecMovCaja_in    IN CHAR)
  RETURN FarmaCursor;
  
  
  --Descripcion: RETORNA EL HTML PARA IMPRESION DE VOUCHER EN ANULACION VENTA CMR
  --Fecha        Usuario		    Comentario
  --25/10/2013   GFONSECA         CREACION
  FUNCTION RCD_IMPRESION_VOUCHER_ANUL_CMR(cCodGrupoCia_in IN CHAR,
                                     cCod_Cia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cCod_Recau_in IN CHAR,
                                     cCod_Caja_in IN CHAR,
                                     cTip_Copia_in IN CHAR) RETURN CLOB;
                                     
  --Descripcion: IMPRESION PARA ANULACION DE PAGO DE TERCEROS
  --Fecha        Usuario		    Comentario
  --27/10/2013   GFONSECA         CREACION                                                                          
 FUNCTION RCD_IMPRESION_VOUCHER_ANUL_RCD(cCodGrupoCia_in IN CHAR,
                                     cCod_Cia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cCod_Caja_in IN CHAR,                                    
                                     cCod_Recau_in IN CHAR,
                                     cTip_Copia_in IN CHAR) RETURN CLOB;

  --Descripcion: RETORNA EL CODIGO DE ALIANZA PARA LA RECAUDACIÓN SEGUN EL BIN CORRESPONDIENTE
  --Fecha        Usuario		    Comentario
  --05/11/2013   LLEIVA         CREACION
  FUNCTION RCD_GET_CODIGO_ALIANZA(cFormaPago_in IN CHAR) RETURN VARCHAR2;
  
  --Descripcion: RETORNA EL NUMERO DE TARJETA Y CODIGO DE AUTORIZACION SEGUN EL PEDIDO
  --Fecha        Usuario		    Comentario
  --06/11/2013   LLEIVA         CREACION
  FUNCTION RCD_GET_TARJ_AUTORIZACION(cNumPedido_in IN CHAR) RETURN FarmaCursor;

  --Descripcion: RETORNA EL NUMERO DE PEDIDO NEGATIVO
  --Fecha        Usuario		    Comentario
  --06/11/2013   LLEIVA         CREACION
  FUNCTION RCD_GET_NUM_PED_NEGATIVO(cNumPedido_in IN CHAR) RETURN VARCHAR2;
  
  --Descripcion: ACTUALIZA EL ESTADO DE CONCILIACION EN LA TABLA LOG
  --Fecha        Usuario		    Comentario
  --18/11/2013   GFONSECA         CREACION
  PROCEDURE RCD_ACT_EST_CONCILIACION(cCodGrupoCia_in IN CHAR,
                                         cCod_Cia_in  IN CHAR,
                                         cCod_Local_in       IN CHAR,                                                                                 
                                         cCod_Autoriz_in IN CHAR,
                                         cFecha_Venta_in IN CHAR,
                                         cTip_Trssc_in IN CHAR,
                                         cPvoucher_in IN CHAR,
                                         cEst_Concili_in IN CHAR);

  --Descripcion: Procesa conciliacion offline
  --Fecha        Usuario		    Comentario
  --04/12/2013   ERIOS         CREACION
  PROCEDURE PROCESA_CONCILIACION_OFFLINE(cCodGrupoCia_in IN CHAR,
                            cCod_Cia_in IN CHAR,
                            cCod_Local_in IN CHAR);

  --Descripcion: Guarda log de recargas
  --Fecha        Usuario	   Comentario
  --12/02/2014   ERIOS         CREACION							
	PROCEDURE GUARDAR_LOG_RECARGAS(
		cCodGrupoCia_in      IN CHAR,
		cCodCia_in           IN CHAR,
		cCodLocal_in         IN CHAR,
		cNumPedVta_in        IN CHAR,
		vCodIdConcetrador_in IN VARCHAR2,
		vNumeroTelefono_in   IN VARCHAR2,
		vCodAutorizacion_in  IN VARCHAR2,
		vCodVendedor_in      IN VARCHAR2,
		vFechaVenta_in       IN VARCHAR2,
		vHoraVenta_in        IN VARCHAR2,
		vNumeroDocumento_in  IN VARCHAR2,
		vCodComercio_in      IN VARCHAR2,
		vCodTerminal_in      IN VARCHAR2,
		nMontoVenta_in       IN NUMBER,
		vIdTransaccion_in    IN VARCHAR2,
		vUsuCrea_in          IN VARCHAR2);

  --Descripcion: Actualiza estado de conciliacion
  --Fecha        Usuario	   Comentario
  --12/02/2014   ERIOS         CREACION									
	PROCEDURE SET_ESTADO_LOG_RECARGAS(
		cCodGrupoCia_in     IN CHAR,
		cCodCia_in          IN CHAR,
		cCodLocal_in        IN CHAR,
		cNumPedVta_in       IN CHAR,
		vEstConciliacion_in IN VARCHAR2,
		vUsuMod_in          IN VARCHAR2);

  --Descripcion: Procesa conciliacion de recargas offline
  --Fecha        Usuario	   Comentario
  --12/02/2014   ERIOS         CREACION									
  PROCEDURE PROCESA_CONC_RECARGAS_OFFLINE(cCodGrupoCia_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodLocal_in IN CHAR);		

  --Descripcion: Genera datos de conciliacion offline
  --Fecha        Usuario	   Comentario
  --26/02/2014   ERIOS         CREACION									
  PROCEDURE GENERA_DATOS_CONCILIACION(cCodGrupoCia_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFechaVenta_in IN VARCHAR2);

  --Descripcion: Genera datos de conciliacion offline - Recaudacion
  --Fecha        Usuario	   Comentario
  --28/03/2014   ERIOS         CREACION									
  PROCEDURE GENERA_DATOS_CONC_RECAU(cCodGrupoCia_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFechaVenta_in IN VARCHAR2);							
END;

/
