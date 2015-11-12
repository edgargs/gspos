CREATE OR REPLACE PACKAGE PTOVENTA."PTOVENTA_RECAUDACION" AS

  TIPO_COMP		    CHAR(2):='99';
  IND_SI	        CHAR(1):='S';
  IND_NO	        CHAR(1):='N';
  IND_ACTIVO		  CHAR(1):='A';

  IND_PENDIENTE   CHAR(1):='P';
  IND_COBRADO     CHAR(1):='C';
  IND_ANULADO     CHAR(1):='N';

  TIPO_REC_VENTA_CMR CHAR(2):='06';

    COD_CIA_MARKET_01 CHAR(3):=  '004';                                     --ASOSA - 03/09/2014
    COD_RCD_RECAU_RAIZ CHAR(2) := '19';   --ASOSA - 06/08/2015 - RAIZ

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
                         cNum_Recibo_in           IN CHAR,
                         vDniCli_in               IN varchar2  --ASOSA - 06/08/2015 - RAIZ
                         )
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
                                        cCod_local_in IN varchar2,
                                        cTipoServ_in in varchar2 DEFAULT NULL --INI ASOSA - 07/08/2015 - RAIZ  
                                        )
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
  --15/06/2015   ERIOS          Se muestran las recaudaciones anuladas  
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

  --Descripcion: Lista de recaudaciones anuladas para impresion de turno
  --Fecha        Usuario	   Comentario
  --15/06/2015   ERIOS         CREACION							
  FUNCTION LISTA_RCDANULADOS_IMPRIME(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cSecMovCaja_in  IN CHAR)                                   
    RETURN FarmaCursor;							

  --Descripcion: Envia correo de anulacion
  --Fecha        Usuario	   Comentario
  --16/06/2015   ERIOS         CREACION								
	PROCEDURE ENV_ALERTA_ANULACION_RCD(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cCodRecau_in IN CHAR);	
END;
/
CREATE OR REPLACE PACKAGE BODY PTOVENTA."PTOVENTA_RECAUDACION" IS
--=============================================================0
  FUNCTION RCD_TIPO_PAGO_CMR
  RETURN FarmaCursor IS
  curListaTipoPago FarmaCursor;
  BEGIN
  OPEN curListaTipoPago FOR
    SELECT  LLAVE_TAB_GRAL|| 'Ã' ||DESC_CORTA
    FROM    PBL_TAB_GRAL
    WHERE   COD_APL = 'PTO_VENTA' and COD_TAB_GRAL = 'TIPO_PAGO_CMR'
    and LLAVE_TAB_GRAL='01';
  RETURN curListaTipoPago;
  END;
--=============================================================
  FUNCTION RCD_CORRE_COD_RECAU(cCodGrupoCia_in          IN CHAR,
                               cCod_Cia_in              IN CHAR,
                               cCod_Local_in            IN CHAR)
	RETURN CHAR
	IS
	  vCod_Recau CHAR(5);
	BEGIN

    --ERIOS 2.4.0 Recupera de secuencia
	select lpad(SEQ_RCD_RECAUDACION_CAB.nextval,5,'0')
	INTO    vCod_Recau
	from dual;

  RETURN vCod_Recau;
  END;

--=============================================================
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
                         cNum_Recibo_in           IN CHAR,
                         vDniCli_in               IN varchar2  --ASOSA - 06/08/2015 - RAIZ
                         )
    RETURN CHAR
    IS
      vCod_Recau CHAR(5):=PTOVENTA_RECAUDACION.RCD_CORRE_COD_RECAU(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in);
    BEGIN

    INSERT INTO RCD_RECAUDACION_CAB
      (COD_GRUPO_CIA,
       COD_CIA,
       COD_LOCAL,
       COD_RECAU,
       SEC_MOV_CAJA,
       NRO_TARJETA,
       TIPO_RCD,
       TIPO_PAGO,
       TIP_COMP_PAGO,
       EST_RCD,
       EST_CUENTA,
       COD_CLIENTE,
       TIP_MONEDA,
       IM_TOTAL,
       IM_TOTAL_PAGO,
       IM_MIN_PAGO,
       VAL_TIP_CAMBIO,
       FECHA_VENC_RECAU,
       NOM_CLIENTE,
       FEC_VEN_TRJ,
       USU_CREA_RECAU_PAGO,
       FEC_MOD_RECAU_PAGO,
       USU_MOD_RECAU_PAGO,
       COD_AUTORIZACION,
       NUM_PED_VTA,
       DNI_CLIENTE  --ASOSA - 06/08/2015 - RAIZ
       )
    VALUES
      (cCodGrupoCia_in,
       cCod_Cia_in,
       cCod_Local_in,
       vCod_Recau,
       cSec_Mov_Caja_in,
       -- KMONCADA 07.09.2015 CIFRADO DE NRO DE TARJETA PARA CASO DE RECAUDACION
       FARMA_UTILITY.F_GET_CIFRAR_NRO_TARJETA(cNro_Tarjeta_in),
       cTipo_Rcd_in,
       cTipo_Pago_in,
       TIPO_COMP,
       cEst_Rcd_in,
       cEst_Cuenta_in,
       cCod_Cliente_in,
       cTip_Moneda_in,
       nIm_Total_in,
       nIm_Total_Pago_in,
       nIm_Min_Pago_in,
       nVal_Tip_Camb_in,
       to_date(cFecha_Venc_Recau_in, 'dd/MM/yyyy'),
       cNom_Cliente_in,
       to_date(cFec_Ven_Trj_in, 'dd/MM/yyyy'),
       cUsu_Crea_Recau_Pago_in,
       sysdate,--cFec_Mod_Recau_Pago_in
       cUsu_Mod_Recau_Pago_in,
       cCod_Autorizacion_in,
       cNum_Pedido_in,
       vDniCli_in  --ASOSA - 06/08/2015 - RAIZ
       );

    RETURN vCod_Recau;
    END;
--=============================================================

  FUNCTION RCD_LISTA_RCD_PEND(cCodGrupoCia_in          IN CHAR,
                             cCod_Cia_in              IN CHAR,
                             cCod_Local_in            IN CHAR,
                             cCod_Recau_in            IN CHAR)

  RETURN FarmaCursor IS
  curListaRcdPend FarmaCursor;
  BEGIN
  OPEN curListaRcdPend FOR
    SELECT
    (COD_RECAU || 'Ã' ||
    TO_CHAR(IM_TOTAL,'999,999,990.00') || 'Ã' ||
    TO_CHAR(IM_MIN_PAGO ,'999,999,990.00') || 'Ã' ||
    TO_CHAR(VAL_TIP_CAMBIO ,'999,999,990.000') || 'Ã' ||
    TIP_COMP_PAGO || 'Ã' ||
    'VOUCHER' || 'Ã' ||
    NOM_CLIENTE || 'Ã' ||
    ' ' || 'Ã' ||
    ' ' || 'Ã' ||
    '01' || 'Ã' ||
    to_char(sysdate,'dd/MM/yy') || 'Ã' ||
    'N' || 'Ã' ||
    'N' || 'Ã' ||
    '1' || 'Ã' ||
    'N' || 'Ã' ||
    ' ' || 'Ã' ||
    ' ') as RESULTADO
    FROM    RCD_RECAUDACION_CAB
    WHERE   COD_GRUPO_CIA = cCodGrupoCia_in
    AND     COD_CIA = cCod_Cia_in
    AND     COD_LOCAL = cCod_Local_in
    AND     COD_RECAU = cCod_Recau_in;

  RETURN curListaRcdPend;
  END;
--=============================================================
  FUNCTION RCD_LISTA_COD_FOM_PAGO
  RETURN FarmaCursor IS
  curListaCodForm FarmaCursor;
  BEGIN
  OPEN curListaCodForm FOR

  SELECT  COD_FORMA_PAGO as RESULTADO
  FROM    VTA_FORMA_PAGO
  WHERE   EST_FORMA_PAGO = IND_ACTIVO
  AND     IND_FORMA_PAGO_EFECTIVO = IND_SI
  AND     IND_TARJ = IND_NO;

  RETURN curListaCodForm;
  END;
--=============================================================
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
                              ) IS

  BEGIN

  INSERT INTO RCD_FORMAS_PAGO
   (COD_GRUPO_CIA,
    COD_CIA,
    COD_LOCAL,
    COD_RECAU,
    COD_FORMA_PAGO,
    IMP_TOTAL,
    TIP_MONEDA,
    VAL_TIP_CAMBIO,
    VAL_VUELTO,
    IM_TOTAL_PAGO,
    USU_CREA_RECAU_PAGO,
    FEC_MOD_RECAU_PAGO,
    Usu_Mod_Recau_Pago)
  VALUES
   (cCodGrupoCia_in,
    cCod_Cia_in,
    cCod_Local_in,
    cCod_Recau_in,
    cCod_Forma_Pago_in,
    cImp_Total_in,
    cTip_Moneda_in,
    cVal_Tip_Cambio_in,
    cVal_Vuelto,
    cIm_Total_Pago_in,
    cUsu_Crea_Recau_Pago_in,
    cFec_Mod_Recau_Pago_in,
    Cusu_Mod_Recau_Pago_In);

    /*  Update    Rcd_Recaudacion_Cab
      SET       SEC_MOV_CAJA = cSec_Mov_Caj_in
      WHERE     COD_GRUPO_CIA = cCodGrupoCia_in
      AND       COD_CIA = cCod_Cia_in
      AND       COD_LOCAL = cCod_Local_in
      AND       COD_RECAU = cCod_Recau_in;
    */
  END;
--=============================================================
  PROCEDURE RCD_CAMBIAR_PED_RCD(cCodGrupoCia_in          IN CHAR,
                                cCod_Cia_in              IN CHAR,
                                cCod_Local_in            IN CHAR,
                                cCod_Recau_in            IN CHAR,
                                cInd_Recau_in            IN CHAR,
                                cUsu_Mod_Rcd_in          IN CHAR,
                                cFecha_Mod_Rcd_in        IN CHAR,
                                cEstado_ImpRecaudacion_In IN CHAR,
                                cCod_Trssc_In            IN CHAR,
                                cEst_TrsscSix_in         IN CHAR,
                                cCod_Autorizacion_in     IN CHAR,
                                cSecMovCaja_in           IN CHAR,
                                cFech_Orig_in            IN CHAR,
                                cNro_Cuotas_in           IN CHAR,
                                cFec_Venc_Cuota_in       IN CHAR,
                                cMonto_Pagar_Cuota_in    IN NUMBER) IS
  BEGIN

   IF cSecMovCaja_in = '-' THEN  -- Aqui no se actualiza el movimiento de caja
        UPDATE RCD_RECAUDACION_CAB
        SET    EST_RCD = cInd_Recau_in,
               Usu_Mod_Recau_Pago = Cusu_Mod_Rcd_In,
               FEC_MOD_RECAU_PAGO = sysdate,
               EST_IMPRESION = cEstado_ImpRecaudacion_In,
               cod_trssc_six = cCod_Trssc_In,
               EST_TRSSC_SIX = cEst_TrsscSix_in,
               COD_AUTORIZACION = cCod_Autorizacion_in
        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_CIA = cCod_Cia_in
        AND    COD_LOCAL = cCod_Local_in
        AND    COD_RECAU = cCod_Recau_in;
    ELSE
       UPDATE RCD_RECAUDACION_CAB
        SET    EST_RCD = cInd_Recau_in,
               Usu_Mod_Recau_Pago = Cusu_Mod_Rcd_In,
               FEC_MOD_RECAU_PAGO = sysdate,
               EST_IMPRESION = cEstado_ImpRecaudacion_In,
               cod_trssc_six = cCod_Trssc_In,
               EST_TRSSC_SIX = cEst_TrsscSix_in,
               COD_AUTORIZACION = cCod_Autorizacion_in,
               SEC_MOV_CAJA = cSecMovCaja_in,
               FECHA_ORIG = cFech_Orig_in,
               FECHA_VENC_CUOTA = cFec_Venc_Cuota_in,
               NRO_CUOTAS = cNro_Cuotas_in,
               IM_CUOTA = cMonto_Pagar_Cuota_in

        WHERE  COD_GRUPO_CIA = cCodGrupoCia_in
        AND    COD_CIA = cCod_Cia_in
        AND    COD_LOCAL = cCod_Local_in
        AND    COD_RECAU = cCod_Recau_in;


    END IF;
  END;

--=============================================================
/**
(1) KMONCADA 09.09.2015: DESCIFRADO DE LOS NRO DE TARJETAS EN CASO DE RECAUDACION
*/
FUNCTION RCD_LISTA_RCD_CANCE(cCodGrupoCia_in          IN CHAR,
                               cCod_Cia_in              IN CHAR,
                               cCod_Local_in            IN CHAR,
                               cCod_Rcd_in              IN CHAR,
                               cMonto_Rcd_in            IN NUMBER,
                               cTipoCobro_in            IN CHAR)
  RETURN FarmaCursor IS
  curListaRcdCan FarmaCursor;
  BEGIN


    IF cCod_Rcd_in = '-' THEN --Cuando no envien codigo se listara toda la consulta
        OPEN curListaRcdCan FOR
           WITH EMP AS(
				select LLAVE_TAB_GRAL TIPO_RCD,DESC_CORTA,DESC_LARGA DESC_LARGA_RCD
				from pbl_tab_gral
				where cod_apl = 'PTO_VENTA'
				AND COD_TAB_GRAL = decode (cTipoCobro_in, 'RCD', 'RCD_TIPO_EMPRE', 'VCMR', 'VENTA_CMR')
				AND EST_TAB_GRAL = 'A')
			SELECT (COD_RECAU || 'Ã' ||
                       TO_CHAR(FEC_CREA_RECAU_PAGO,'dd/MM/yyyy') || 'Ã' ||
                       EMP.DESC_LARGA_RCD ||
                       NVL((SELECT  ' - ' || DESC_CORTA
                            FROM    PBL_TAB_GRAL
                            WHERE   COD_APL = 'PTO_VENTA'
                            AND     COD_TAB_GRAL = 'TIPO_PAGO_CMR'
                            AND     LLAVE_TAB_GRAL = TIPO_PAGO),'') || 'Ã' ||
/*(1)*/                nvl(FARMA_UTILITY.F_GET_DESCIFRAR_NRO_TARJETA(NRO_TARJETA),' ') || 'Ã' || 
                       nvl(COD_CLIENTE,' ') || 'Ã' ||
                       nvl(NOM_CLIENTE,' ') || 'Ã' ||
                       DECODE(TIP_MONEDA,'02','$','S/') || 'Ã' ||
                       To_Char(Im_Total,'999,999,990.00') || 'Ã' ||
                       USU_CREA_RECAU_PAGO || 'Ã' ||
                       EST_RCD || 'Ã' ||
                       DECODE(COD_TRSSC_SIX, null, ' ', COD_TRSSC_SIX) || 'Ã' ||
                       DECODE(EST_TRSSC_SIX, null, ' ', EST_TRSSC_SIX) || 'Ã' ||
                       NVL2(RCD_CAB.IND_ANUL,'ANULADO',DECODE(EST_RCD,'C','COBRADO',EST_RCD)) || 'Ã' ||
                       DECODE(EST_TRSSC_SIX, 'OK','PROCESADO','FA','FALLIDO', NULL, ' -- ') || 'Ã' ||
                       DECODE(COD_AUTORIZACION, null, ' ', COD_AUTORIZACION) || 'Ã' ||
                       NVL(RCD_CAB.IND_ANUL,RCD_CAB.TIPO_RCD) || 'Ã' ||
                       TIP_MONEDA || 'Ã' ||
                       DECODE(RCD_CAB.FECHA_ORIG, null, ' ', RCD_CAB.FECHA_ORIG)
                    ) AS RESULTADO
              FROM     RCD_RECAUDACION_CAB RCD_CAB INNER JOIN EMP ON (RCD_CAB.TIPO_RCD=EMP.TIPO_RCD)
              WHERE    COD_GRUPO_CIA = cCodGrupoCia_in
              AND      COD_CIA = cCod_Cia_in
              AND      COD_LOCAL = cCod_Local_in
              AND      (EST_RCD = IND_COBRADO OR EST_RCD = IND_ANULADO)
              AND      COD_RECAU_ANUL_REF IS NULL
              AND      FEC_CREA_RECAU_PAGO < sysdate
              AND      FEC_CREA_RECAU_PAGO > sysdate - 30
              ORDER BY COD_RECAU DESC;
        RETURN curListaRcdCan;
    ELSE
        OPEN curListaRcdCan FOR
              WITH EMP AS(
				select LLAVE_TAB_GRAL TIPO_RCD,DESC_CORTA,DESC_LARGA DESC_LARGA_RCD
				from pbl_tab_gral
				where cod_apl = 'PTO_VENTA'
				AND COD_TAB_GRAL = decode (cTipoCobro_in, 'RCD', 'RCD_TIPO_EMPRE', 'VCMR', 'VENTA_CMR')
				AND EST_TAB_GRAL = 'A')
			SELECT (COD_RECAU || 'Ã' ||
                       TO_CHAR(FEC_CREA_RECAU_PAGO,'dd/MM/yyyy') || 'Ã' ||
                       EMP.DESC_LARGA_RCD ||
                       NVL((SELECT  ' - ' || DESC_CORTA
                            FROM    PBL_TAB_GRAL
                            WHERE   COD_APL = 'PTO_VENTA'
                            AND     COD_TAB_GRAL = 'TIPO_PAGO_CMR'
                            AND     LLAVE_TAB_GRAL = TIPO_PAGO),'') || 'Ã' ||
/*(1)*/                nvl(FARMA_UTILITY.F_GET_DESCIFRAR_NRO_TARJETA(NRO_TARJETA),' ') || 'Ã' ||
                       nvl(COD_CLIENTE,' ') || 'Ã' ||
                       nvl(NOM_CLIENTE,' ') || 'Ã' ||
                       DECODE(TIP_MONEDA,'02','$','S/') || 'Ã' ||
                       To_Char(Im_total,'999,999,990.00') || 'Ã' ||
                       USU_CREA_RECAU_PAGO || 'Ã' ||
                       EST_RCD || 'Ã' ||
                       DECODE(COD_TRSSC_SIX, null, ' ', COD_TRSSC_SIX) || 'Ã' ||
                       DECODE(EST_TRSSC_SIX, null, ' ', EST_TRSSC_SIX) || 'Ã' ||
                       NVL2(RCD_CAB.IND_ANUL,'ANULADO',DECODE(EST_RCD,'C','COBRADO',EST_RCD)) || 'Ã' ||
                       DECODE(EST_TRSSC_SIX, 'OK','PROCESADO','FA','FALLIDO', NULL, ' -- ') || 'Ã' ||
                       DECODE(COD_AUTORIZACION, null, ' ', COD_AUTORIZACION) || 'Ã' ||
                       NVL(RCD_CAB.IND_ANUL,RCD_CAB.TIPO_RCD) || 'Ã' ||
                       TIP_MONEDA || 'Ã' ||
                       DECODE(RCD_CAB.FECHA_ORIG, null, ' ', RCD_CAB.FECHA_ORIG)
                    ) AS RESULTADO
              FROM     RCD_RECAUDACION_CAB RCD_CAB INNER JOIN EMP ON (RCD_CAB.TIPO_RCD=EMP.TIPO_RCD)
              WHERE    COD_GRUPO_CIA = cCodGrupoCia_in
              AND      COD_CIA = cCod_Cia_in
              AND      COD_LOCAL = cCod_Local_in
              AND      (EST_RCD = IND_COBRADO OR EST_RCD = IND_ANULADO)
              AND      COD_RECAU_ANUL_REF IS NULL
              AND      COD_RECAU like nvl(cCod_Rcd_in,'%')
						  and      (cMonto_Rcd_in=0.0 or IM_TOTAL_PAGO = cMonto_Rcd_in)
              ORDER BY COD_RECAU DESC;
        RETURN curListaRcdCan;
    END IF;
  END;

--=============================================================
--=============================================================

  FUNCTION RCD_TRAMA_CONSULTA_CITI_PRES(cFlag_Cod_in IN varchar2,
                                        cCod_in IN varchar2,
                                        cTip_Moneda_in IN varchar2,
                                        cTip_trans_in IN varchar2,
                                        cCod_local_in IN varchar2,
                                        cTipoServ_in in varchar2 DEFAULT NULL --INI ASOSA - 07/08/2015 - RAIZ  
                                        )
   RETURN VARCHAR2 IS
      tramaConsulta VARCHAR2(1000);
      tipoServicio varchar2(2):='18'; --Pago PrestamoCitibank
   BEGIN
   
      --INI ASOSA - 07/08/2015 - RAIZ  
      IF (cTipoServ_in IS NOT NULL) THEN
         tipoServicio := cTipoServ_in;
      END IF;
      --FIN ASOSA - 07/08/2015 - RAIZ
   
      select '001002' || tipoServicio || --Indica el tipo de cliente Citibank Pago PrestamoCitibank
             '002001' || cFlag_Cod_in || --1 indica Codigo de Cliente y 2 Nro de Recibo
             '003015' || LPAD(cCod_in,15,' ')  || --el campo en la tabla de fasa es un varchar2(15) y en el documento la longitud es 20
             '004003' || cTip_Moneda_in ||--Indicador de código de Moneda 001=soles 002=Dólares
             '005001' || cTip_trans_in ||--Indicador Tipo de Transacción P=Pago X=extorno
             '006004' || cCod_local_in   --Código de Local
             into tramaConsulta
             from dual;

      RETURN tramaConsulta;
  END;

--=============================================================

  FUNCTION RCD_PROCESA_TRAMA_PRES_CITI(cTrama_PresCiti_in  IN VARCHAR2)
  RETURN FarmaCursor IS
  curDatosPresCiti FarmaCursor;
    test VARCHAR2(1000);
    codAutori VARCHAR2(1000);
    glosa VARCHAR2(1000);
    tipoCompra VARCHAR2(1000);
    tipoVenta VARCHAR2(1000);
    impCobrar VARCHAR2(1000);
  BEGIN

   test := SUBSTR(cTrama_PresCiti_in,1,3);

   IF test = 'ERR' then
        OPEN curDatosPresCiti FOR
        select ('El codigo no existe, verifique.') AS RESULTADO
        from dual;
   ELSE
        codAutori := SUBSTR(cTrama_PresCiti_in,1,6);
        --dbms_output.put_line('codigo autorizacion: ' || codAutori);
        glosa := SUBSTR(cTrama_PresCiti_in,7,119);
        --dbms_output.put_line('glosa              : ' || glosa);
        tipoCompra := SUBSTR(cTrama_PresCiti_in,127,6);
        --dbms_output.put_line('tipo compra        : ' || tipoCompra);
        tipoVenta := SUBSTR(cTrama_PresCiti_in,133,6);
        --dbms_output.put_line('tipo venta         : ' || tipoVenta);
        impCobrar := SUBSTR(cTrama_PresCiti_in,139,10);
        --dbms_output.put_line('importe a cobrar   : ' || impCobrar);

        OPEN curDatosPresCiti FOR
        select ( (codAutori)   || 'Ã' ||
                 (glosa)       || 'Ã' ||
                 (tipoCompra)  || 'Ã' ||
                 (tipoVenta)   || 'Ã' ||
                 trim(To_Char(impCobrar,'999,999,990.00'))
                ) AS RESULTADO
        from dual;
    END IF;

    RETURN curDatosPresCiti;
  END;

  --=============================================================

  FUNCTION RCD_F_OBTENER_BIN_TARJETA(cCodGrupoCia_in IN CHAR,
                                     cCodTarj_in     IN VARCHAR,
                                     cTipOrigen_in   IN VARCHAR)
  RETURN FarmaCursor
  IS
       cur FarmaCursor;
  BEGIN
       OPEN cur FOR
            SELECT (a.bin  || 'Ã' ||
                   a.desc_prod  || 'Ã' ||
                   a.cod_forma_pago  || 'Ã' ||
                   b.desc_corta_forma_pago)resultado
            FROM vta_fpago_tarj a
            inner join vta_forma_pago b on a.cod_grupo_cia=b.cod_grupo_cia
            WHERE a.cod_grupo_cia=cCodGrupoCia_in
            AND a.cod_forma_pago=b.cod_forma_pago
            AND cCodTarj_in LIKE trim(a.bin)||'%'
            AND TIP_ORIGEN_PAGO = cTipOrigen_in;
       RETURN cur;
  END;


--=============================================================

  FUNCTION RCD_ANULAR_RECAUDACION(cCodGrupoCia_in IN CHAR,
                                  cCod_Cia_in IN CHAR,
                                  cCod_Local_in IN CHAR,
                                  cCodRecau_in IN CHAR,
                                  nNumCajaPago_in IN CHAR,
                                  nUsuModRecauPago_in IN CHAR,
                                  nCod_TrsscAnul_in IN CHAR,
								  cCodRecauAnul_in IN CHAR
								  )
         RETURN CHAR IS  vCod_Recau CHAR(5);
         curPagosCab FarmaCursor;
         curPagosDetalle FarmaCursor;

         --RLLantoy 09.07.2013
         v_cCajaAbierta VTA_CAJA_PAGO.IND_CAJA_ABIERTA%TYPE;
         v_cSecUsuLocal VTA_CAJA_PAGO.SEC_USU_LOCAL%TYPE;
         v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;

         v_dFecCreaRecau RCD_RECAUDACION_CAB.fec_crea_recau_pago%TYPE;
         v_cIndCorrecto CHAR(1):='N';
         vNumRecauOrigen RCD_RECAUDACION_CAB.COD_RECAU_ANUL_REF%TYPE;
  BEGIN

       dbms_output.put_line('codigo recaudacion a anular: ' || cCodRecau_in);

        FOR curPagosCab IN (SELECT *
                            FROM RCD_RECAUDACION_CAB RC
                            WHERE RC.COD_GRUPO_CIA=cCodGrupoCia_in AND
                            RC.COD_CIA=cCod_Cia_in AND
                            RC.COD_LOCAL=cCod_Local_in AND
                            RC.COD_RECAU = cCodRecau_in)
        LOOP
            vCod_Recau := cCodRecauAnul_in;

            IF (curPagosCab.EST_RCD = IND_COBRADO ) THEN
                      --RLLantoy 09.07.2013
                      -- VERIFICA SI LA CAJA NO  HA SIDO CERRADA Y AUN PERMANECE ABIERTA
                      SELECT SEC_USU_LOCAL, SEC_MOV_CAJA
                      INTO v_cSecUsuLocal,v_cSecMovCaja_in
                      FROM VTA_CAJA_PAGO
                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                        AND COD_LOCAL = cCod_Local_in
                        AND NUM_CAJA_PAGO = nNumCajaPago_in;
                        v_cCajaAbierta:= Ptoventa_Caj.CAJ_IND_CAJA_ABIERTA_FORUPDATE(cCodGrupoCia_in,cCod_Local_in, v_cSecUsuLocal, v_cSecMovCaja_in);

                      IF v_cCajaAbierta='N' THEN
                           RAISE_APPLICATION_ERROR(-20021,'LA CAJA NO SE ENCUENTRA ABIERTA.');
                      ELSIF v_cCajaAbierta='S' THEN

                            INSERT INTO RCD_RECAUDACION_CAB (COD_GRUPO_CIA, COD_CIA, COD_LOCAL, COD_RECAU, SEC_MOV_CAJA, NRO_TARJETA,
                                                       TIPO_RCD, TIPO_PAGO, TIP_COMP_PAGO, EST_RCD,
            								                           EST_CUENTA, COD_CLIENTE, TIP_MONEDA, IM_TOTAL,
                                                       IM_TOTAL_PAGO, IM_MIN_PAGO, VAL_TIP_CAMBIO, FECHA_VENC_RECAU,
            								                           NOM_CLIENTE, FEC_VEN_TRJ,
                                                       USU_CREA_RECAU_PAGO,
                                                       EST_IMPRESION,COD_RECAU_ANUL_REF,COD_AUTORIZACION,
                                                       COD_TRSSC_SIX_ANUL)
                            VALUES (curPagosCab.COD_GRUPO_CIA,curPagosCab.COD_CIA,curPagosCab.COD_LOCAL,vCod_Recau,v_cSecMovCaja_in,curPagosCab.NRO_TARJETA,
            						             curPagosCab.TIPO_RCD,curPagosCab.TIPO_PAGO,curPagosCab.TIP_COMP_PAGO,curPagosCab.EST_RCD,
            								         curPagosCab.EST_CUENTA,curPagosCab.COD_CLIENTE,curPagosCab.TIP_MONEDA,(curPagosCab.IM_TOTAL*-1),
            								         (curPagosCab.IM_TOTAL_PAGO*-1),(curPagosCab.IM_MIN_PAGO*-1),curPagosCab.VAL_TIP_CAMBIO,curPagosCab.FECHA_VENC_RECAU,
            								         curPagosCab.NOM_CLIENTE,curPagosCab.FEC_VEN_TRJ,
            								         nUsuModRecauPago_in,curPagosCab.EST_IMPRESION,curPagosCab.COD_RECAU,curPagosCab.COD_AUTORIZACION,
                                     nCod_TrsscAnul_in);

                             dbms_output.put_line('nuevo codigo de recaudacion negativa: ' || vCod_Recau);
                             FOR curPagosDetalle IN (SELECT *
                                                         FROM RCD_FORMAS_PAGO RD
                                                             WHERE RD.COD_GRUPO_CIA=cCodGrupoCia_in AND
                                                                   RD.COD_CIA=cCod_Cia_in AND
                                                                   RD.COD_LOCAL=cCod_Local_in AND
                                                                   RD.COD_RECAU = cCodRecau_in)
                              LOOP
                                  INSERT INTO RCD_FORMAS_PAGO
                                  (COD_GRUPO_CIA,
                                  COD_CIA,
                                  COD_LOCAL,
                                  COD_RECAU,
                                  COD_FORMA_PAGO,
                                  IMP_TOTAL,
                                  TIP_MONEDA,
                                  VAL_TIP_CAMBIO,
                                  VAL_VUELTO,
                                  IM_TOTAL_PAGO,
                                  USU_CREA_RECAU_PAGO)
                                  VALUES (curPagosDetalle.Cod_Grupo_Cia, curPagosDetalle.Cod_Cia, curPagosDetalle.Cod_Local,
                                          vCod_Recau, curPagosDetalle.Cod_Forma_Pago, curPagosDetalle.Imp_Total*-1,
                                          curPagosDetalle.Tip_Moneda, curPagosDetalle.Val_Tip_Cambio, curPagosDetalle.Val_Vuelto*-1,
                                          (curPagosDetalle.Im_Total_Pago*-1),
                                          nUsuModRecauPago_in);
                              END LOOP;
                      ELSE
                              RAISE_APPLICATION_ERROR(-20022,'HA OCURRIDO UN ERROR DESCONOCIDO.');
                      END IF;

                --RLLantoy 09.07.2013
                --OBTENIENDO LA FECHA DEL REGISTRO DEL PEDIDO NEGATIVO
                SELECT cab.fec_crea_recau_pago,cab.cod_recau_anul_ref
                    into   v_dFecCreaRecau,vNumRecauOrigen
                    FROM  RCD_RECAUDACION_CAB cab
                    WHERE cab.cod_grupo_cia = cCodGrupoCia_in
                    AND cab.cod_cia = cCod_Cia_in
                    AND   cab.cod_local     = cCod_Local_in
                    AND   CAB.COD_RECAU   = vCod_Recau;

                --RLLantoy 09.07.2013
                --VERIFICAR SI EL PEDIDO GENERADO CORRESPONDIENTE ESTA CON LA FECHA DE APERTURA
                BEGIN
                    SELECT 'S' INTO v_cIndCorrecto
                    FROM CE_MOV_CAJA C
                    WHERE TRUNC(C.Fec_Dia_Vta) = TRUNC(v_dFecCreaRecau)
                    AND   C.SEC_MOV_CAJA       = v_cSecMovCaja_in;
                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                    v_cIndCorrecto := 'N';
                    RAISE_APPLICATION_ERROR(-20028,'FECHA DE ANULACION INVALIDA YA PASO LAS 24:00 HORAS.');
                END;

            END IF;

              --Actualiza el estado de la recaudacion anulada
              update RCD_RECAUDACION_CAB
              set  IND_ANUL = IND_ANULADO,
				   FEC_MOD_RECAU_PAGO = SYSDATE,
				   USU_MOD_RECAU_PAGO = nUsuModRecauPago_in
              where COD_GRUPO_CIA = cCodGrupoCia_in and
                    COD_CIA = cCod_Cia_in and
                    COD_LOCAL = cCod_Local_in and
                    COD_RECAU = cCodRecau_in;

			--ERIOS 16.06.2015 Envia alerta por correo
			ENV_ALERTA_ANULACION_RCD(cCodGrupoCia_in, cCod_Cia_in, cCod_Local_in, cCodRecau_in);
        END LOOP;

       RETURN vCod_Recau;
  END;

--=============================================================

  FUNCTION RCD_ACTUALIZAR_COBRO_PRES_CITI(cCodGrupoCia_in    IN CHAR,
                                 cCod_Cia_in  IN CHAR,
                                 cCod_Local_in       IN CHAR,
                                 cCodRecau_in IN CHAR) RETURN VARCHAR IS
   vMontoPagado varchar(15);
   vsum_im_total_pago number(9,3):=0;
   vsum_im_total_m_pago number(9,3):=0;
   vEstCta rcd_recaudacion_cab.EST_CUENTA%type;
   vTipo_Recau rcd_recaudacion_cab.tipo_rcd%type;

 BEGIN

      select sum(im_total_pago)-sum(val_vuelto)
      into vsum_im_total_pago
      from rcd_formas_pago
      where cod_grupo_cia = cCodGrupoCia_in
      and cod_cia = cCod_Cia_in
      and cod_local = cCod_Local_in
      and cod_recau = cCodRecau_in;

      select EST_CUENTA
      into vEstCta
      from rcd_recaudacion_cab
      where cod_grupo_cia = cCodGrupoCia_in
      and cod_cia = cCod_Cia_in
      and cod_local = cCod_Local_in
      and cod_recau = cCodRecau_in;

      update rcd_recaudacion_cab
      set im_total_pago = vsum_im_total_pago,
      im_total = vsum_im_total_pago
      where cod_grupo_cia = cCodGrupoCia_in
      and cod_cia = cCod_Cia_in
      and cod_local = cCod_Local_in
      and cod_recau = cCodRecau_in;

      if(vEstCta = 'D')then

          SELECT SUM(ROUND((IM_TOTAL_PAGO-VAL_VUELTO)/VAL_TIP_CAMBIO,2))
                 INTO vsum_im_total_m_pago
          FROM rcd_formas_pago
          where cod_grupo_cia = cCodGrupoCia_in
            and cod_cia = cCod_Cia_in
            and cod_local = cCod_Local_in
            and cod_recau = cCodRecau_in;

        update rcd_recaudacion_cab
               set im_total = vsum_im_total_m_pago
        where cod_grupo_cia = cCodGrupoCia_in
          and cod_cia = cCod_Cia_in
          and cod_local = cCod_Local_in
          and cod_recau = cCodRecau_in;
      end if;

      vMontoPagado := to_char(vsum_im_total_pago,'9,999,999.99');

      return trim(vMontoPagado||'Ã'||trim(to_char(vsum_im_total_m_pago,'9,999,999.99')));

 END;

--================================================================================

 FUNCTION RCD_IMP_COMPAGO_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                            cCod_Cia_in              IN CHAR,
                            cCod_Local_in            IN CHAR,
                            cNum_Recaudacion_in        IN CHAR)
    RETURN  CLOB--VARCHAR2
    IS
	--ERIOS 31.07.2013 REC_TIPO_EMPRE
   C_INICIO_MSG  VARCHAR2(2000) := '<html>
                                    <head><style type="text/css"> body { font-family:Arial, Helvetica, sans-serif;} </style></head>
                                    <body><table width="335" border="0"><tr><td><table border="0">';

   C_FILA_VACIA  VARCHAR2(2000) :='<tr><td height="13" colspan="3"></td></tr> ';

   C_FIN_MSG     VARCHAR2(2000) := '</table></td></tr></table></body></html>';


    vMsg_out varchar2(32767):= '';

    vMensajeLocal varchar2(1000):= '';
    vLocal varchar2(50):= '';
    vMensajeRecaudacion varchar2(10000):= '';
    v_cMovCaja RCD_RECAUDACION_CAB.SEC_MOV_CAJA%TYPE;
    vTipoRecaudacion varchar2(100):='';
    Vdescrecaudacion Varchar2(100):='';
    Vmontototalpago Numeric(9,3);
    vcMontoTotalPago VARCHAR2(5000);
    vTipoMonRcd VARCHAR2(5);
    Vmontopago Numeric(9,3);
    Vvalvuelto Numeric(9,3);
    vnumtarjetaT Varchar2(50):='';
    vNumTarjeta varchar2(50):='';
    Vtipodoc Varchar2(20):='';
    vCodCliente varchar2(20):='';
    vCodAutorizacion varchar2(20):='';
    vCod_six number;
    --INI ASOSA - 03/09/2014
      vTextoMarca varchar2(50) := '';
      cCodMarca char(3) := '';
      --FIN ASOSA - 03/09/2014

    --vCodAutorizacion varchar2(20):='';
    BEGIN

          --INI ASOSA - 03/09/2014
          SELECT PL.COD_MARCA
          INTO cCodMarca
          FROM PBL_LOCAL PL
          WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND PL.COD_CIA = cCod_Cia_in
          AND PL.COD_LOCAL = cCod_Local_in;

          IF cCodMarca <> COD_CIA_MARKET_01 THEN
                     vTextoMarca := 'BOTICAS ';
          END IF;
          --FIN ASOSA - 03/09/2014

          SELECT
          --'BOTICAS '||MG.NOM_MARCA ||
          vTextoMarca||MG.NOM_MARCA ||  --ASOSA - 03/09/2014
          '<br>'|| PC.RAZ_SOC_CIA || ' RUC: '||PC.NUM_RUC_CIA ||
          '<br>'|| PC.DIR_CIA
          ,PL.COD_LOCAL
          INTO vMensajeLocal, vlocal
          FROM PBL_LOCAL PL
              JOIN PBL_MARCA_CIA MC ON (PL.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                          PL.COD_MARCA = MC.COD_MARCA AND
                                          PL.COD_CIA = MC.COD_CIA)
              JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                              MG.COD_MARCA = MC.COD_MARCA)
              JOIN PBL_CIA PC ON (PC.COD_CIA = PL.COD_CIA)
          WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
              AND PL.COD_LOCAL = cCod_Local_in;

            WITH EMP AS(
				select LLAVE_TAB_GRAL TIPO_RCD,DESC_CORTA,DESC_LARGA DESC_LARGA_RCD
				from pbl_tab_gral
				where cod_apl = 'PTO_VENTA'
				AND COD_TAB_GRAL = 'RCD_TIPO_EMPRE'
				AND EST_TAB_GRAL = 'A')
            SELECT RCD.TIPO_RCD, EMP.DESC_LARGA_RCD
            INTO vTipoRecaudacion, vDescRecaudacion
            FROM RCD_RECAUDACION_CAB RCD,EMP
            WHERE RCD.COD_GRUPO_CIA =cCodGrupoCia_in
            AND RCD.COD_CIA = cCod_Cia_in
            AND RCD.COD_LOCAL = cCod_Local_in
            AND RCD.COD_RECAU = cNum_Recaudacion_in
            AND RCD.TIPO_RCD=EMP.TIPO_RCD;

        --ERIOS 27.09.2013 Se muestra el monto de recaudacion
        IF vTipoRecaudacion='01' OR vTipoRecaudacion='07' THEN
          Vtipodoc:='TARJETA : ';
          -- KMONCADA 08.09.2015 DESCRIPTARA EL NRO DE TARJETA
          Select FARMA_UTILITY.F_GET_DESCIFRAR_NRO_TARJETA(Rcd.Nro_Tarjeta), 
                 RCD.TIP_MONEDA, 
                 RCD.IM_TOTAL, 
                 Rcd.Cod_Autorizacion, 
                 Sum(Det.Im_Total_Pago), 
                 Sum(Det.Val_Vuelto) --, Rcd.cod_trssc_six
          INTO vnumtarjetaT, 
               vTipoMonRcd, 
               vMontoPago, 
               vCodAutorizacion, 
               vMontoTotalPago, 
               vValVuelto --, vCod_six
          FROM RCD_RECAUDACION_CAB RCD, 
               RCD_FORMAS_PAGO DET
          WHERE RCD.COD_GRUPO_CIA =cCodGrupoCia_in
          AND RCD.COD_CIA = cCod_Cia_in
          AND RCD.COD_LOCAL = cCod_Local_in
          AND RCD.COD_RECAU = cNum_Recaudacion_in
          AND RCD.COD_RECAU = DET.COD_RECAU
          GROUP BY RCD.NRO_TARJETA, RCD.TIP_MONEDA, RCD.IM_TOTAL, Rcd.Cod_Autorizacion;
        vNumTarjeta:=CONCAT(CONCAT(SUBSTR(vnumtarjetaT,1,4),'********'),SUBSTR(vnumtarjetaT,13,16));

        END IF;


        IF vTipoRecaudacion='02' THEN
          Vtipodoc:='TARJETA : ';
          -- KMONCADA 08.09.2015 DESCRIPTARA EL NRO DE TARJETA
          Select FARMA_UTILITY.F_GET_DESCIFRAR_NRO_TARJETA(Rcd.Nro_Tarjeta), 
                 RCD.TIP_MONEDA, 
                 RCD.IM_TOTAL,
                 Sum(Det.Im_Total_Pago), 
                 Sum(Det.Val_Vuelto)
          INTO vnumtarjetaT, 
               vTipoMonRcd, 
               vMontoPago,
               vMontoTotalPago, 
               vValVuelto
          FROM RCD_RECAUDACION_CAB RCD, 
               RCD_FORMAS_PAGO DET
          WHERE RCD.COD_GRUPO_CIA =cCodGrupoCia_in
          AND RCD.COD_CIA = cCod_Cia_in
          AND RCD.COD_LOCAL = cCod_Local_in
          AND RCD.COD_RECAU = cNum_Recaudacion_in
          AND RCD.COD_RECAU = DET.COD_RECAU
          GROUP BY RCD.NRO_TARJETA, RCD.TIP_MONEDA, RCD.IM_TOTAL;
          vNumTarjeta:=CONCAT(CONCAT(SUBSTR(vnumtarjetaT,1,4),'********'),SUBSTR(vnumtarjetaT,13,16));
          vCodAutorizacion := cCod_Local_in || cNum_Recaudacion_in;
        END IF;

        IF vTipoRecaudacion='03' THEN
          vTipoDoc:='TELEFONO : ';
            Select Rcd.Cod_Cliente, RCD.TIP_MONEDA, RCD.IM_TOTAL, Rcd.Cod_Autorizacion, Sum(Det.Im_Total_Pago), Sum(Det.Val_Vuelto)
            INTO vnumtarjeta, vTipoMonRcd, vMontoPago, vCodAutorizacion, vMontoTotalPago, vValVuelto
            FROM RCD_RECAUDACION_CAB RCD, RCD_FORMAS_PAGO DET
            WHERE RCD.COD_GRUPO_CIA =cCodGrupoCia_in
            AND RCD.COD_CIA = cCod_Cia_in
            AND RCD.COD_LOCAL = cCod_Local_in
            AND RCD.COD_RECAU = cNum_Recaudacion_in
            AND RCD.COD_RECAU = DET.COD_RECAU
            GROUP BY Rcd.Cod_Cliente, RCD.TIP_MONEDA, RCD.IM_TOTAL, Rcd.Cod_Autorizacion;
       END IF ;

        IF vTipoRecaudacion='04' OR
          vTipoRecaudacion='19'  --ASOSA - 28/08/2015 - RAIZ
          THEN
          vTipoDoc:='DOCTO : ';
            Select Rcd.Cod_Cliente, RCD.TIP_MONEDA, RCD.IM_TOTAL, Rcd.Cod_Autorizacion, Sum(Det.Im_Total_Pago), Sum(Det.Val_Vuelto)
            INTO vNumTarjeta, vTipoMonRcd, vMontoPago,vCodAutorizacion, vMontoTotalPago, vValVuelto
            FROM RCD_RECAUDACION_CAB RCD, RCD_FORMAS_PAGO DET
            WHERE RCD.COD_GRUPO_CIA =cCodGrupoCia_in
            AND RCD.COD_CIA = cCod_Cia_in
            AND RCD.COD_LOCAL = cCod_Local_in
            AND RCD.COD_RECAU = cNum_Recaudacion_in
            AND RCD.COD_RECAU = DET.COD_RECAU
            GROUP BY Rcd.Cod_Cliente, RCD.TIP_MONEDA, RCD.IM_TOTAL, Rcd.Cod_Autorizacion;
       END IF ;

       --ERIOS 27.09.2013 Se obtiene las formas de pago
       vcMontoTotalPago := GET_FORMAS_PAGO(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in,cNum_Recaudacion_in);

          WITH EMP AS(
				select LLAVE_TAB_GRAL TIPO_RCD,DESC_CORTA,DESC_LARGA DESC_LARGA_RCD
				from pbl_tab_gral
				where cod_apl = 'PTO_VENTA'
				AND COD_TAB_GRAL = 'RCD_TIPO_EMPRE'
				AND EST_TAB_GRAL = 'A')
          SELECT
          '<br>'||'----------------------------------------------------------------------------' ||
          '<br>'||'LOCAL :'|| vlocal ||
          '<br>'||'FECHA :' ||TO_CHAR(rcd.fec_crea_recau_pago,'DD/MM/YYYY') ||' HORA :' || TO_CHAR(SYSDATE,'HH24:MI:SS')||
          '<br>'||'CAJERO :'|| (SELECT USU.NOM_USU || '  ' || USU.APE_PAT
                                FROM PBL_USU_LOCAL USU
                                WHERE USU.COD_GRUPO_CIA = MOV.COD_GRUPO_CIA
                                  AND USU.COD_LOCAL = MOV.COD_LOCAL
                                  AND USU.SEC_USU_LOCAL = MOV.SEC_USU_LOCAL) ||
          '<br>'||'NUM. RECAUDACIÓN: '|| cNum_Recaudacion_in ||
          '<br>'||'MOV.CAJA: '|| RCD.SEC_MOV_CAJA ||
          '<br>'||'----------------------------------------------------------------------------' ||
          '<br>'||'SERVICIO: '|| RCD.TIPO_RCD || '  ' || EMP.DESC_LARGA_RCD ||
          '<br>'|| vTipoDoc || vNumTarjeta ||
          '<br>'||'COD. AUTORIZACIÓN: '|| vCodAutorizacion ||
          '<br>'||
          '<br>'||'MONTO: '||decode(vTipoMonRcd,'02',' $ ','S/.')||'  ***'|| TO_CHAR(vMontoPago,'999,990.00') ||'***' ||
          '<br>'||'----------------------------------------------------------------------------' ||
          vcMontoTotalPago ||
          '<br>'||'VUELTO         S/.'|| TO_CHAR(vValVuelto,'999,990.00') ||
          '<br><span style="font-weight:bold;">'||'EL PAGO SE REFLEJARÁ HASTA EL 2DO. DÍA ÚTIL' ||
          '<br>'||'----------------------------------------------------------------------------'

          INTO vMensajeRecaudacion
         -- vTipoRcaudacion := RCD.TIPO_RCD
          FROM RCD_RECAUDACION_CAB RCD, EMP,
                CE_MOV_CAJA MOV
              WHERE RCD.COD_GRUPO_CIA =cCodGrupoCia_in
              AND RCD.COD_CIA = cCod_Cia_in
              AND RCD.COD_LOCAL = cCod_Local_in
              AND RCD.COD_RECAU = cNum_Recaudacion_in
              AND MOV.COD_GRUPO_CIA(+) = RCD.COD_GRUPO_CIA
              AND MOV.COD_LOCAL(+) = RCD.COD_LOCAL
              AND MOV.SEC_MOV_CAJA(+) = RCD.SEC_MOV_CAJA
              AND RCD.TIPO_RCD=EMP.TIPO_RCD;



     vMsg_out := C_INICIO_MSG ||
                   '<tr><td height="21" colspan="3" align="center"
                            style="font-size:17px;font-weight:bold;">
                            RECAUDACIÓN
                    </td></tr>'||
                   '<tr><td height="21" colspan="3" align="center">'||
                    vMensajeLocal||
                    '</td></tr>'||
                    '<tr><td height="21" colspan="3" align="left">'||
                    vMensajeRecaudacion||
                    '</td></tr>'||
                    '<tr align="center"><td height="21" colspan="3" style="font-weight:bold;">'||'ESTE DOCUMENTO NO ES COMPROBANTE DE PAGO'||'</td></tr>'||
                    '<tr align="center"><td height="21" colspan="3" style="font-weight:bold;">'||'NO TIENE VALOR TRIBUTARIO'||'</td></tr>'||
                   C_FIN_MSG ;

                   return vMsg_out;

    END;
--=================================================================================================================0
    PROCEDURE RCD_UPDATE_EST_IMP_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                            cCod_Cia_In              In Char,
                            cCod_Local_In            In Char,
                            Cnum_Recaudacion_In        In Char,
                            cEstado_ImpRecaudacion_In        In Char) IS

   Begin
        UPDATE RCD_RECAUDACION_CAB CAB
           SET   CAB.EST_IMPRESION = cEstado_ImpRecaudacion_In
           Where
               CAB.COD_GRUPO_CIA = cCodGrupoCia_in AND
               CAB.COD_CIA = cCod_Cia_In AND
               CAB.COD_LOCAL = cCod_Local_In AND
               CAB.COD_RECAU=Cnum_Recaudacion_In;
           DBMS_OUTPUT.put_line('ESTADO ' || cEstado_ImpRecaudacion_In);

      END;

--=============================================================================================
    FUNCTION RCD_GET_EST_IMP_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                            Ccod_Cia_In              In Char,
                            cCod_Local_in            IN CHAR,
                            Cnum_Recaudacion_In        In Char)
    Return VARCHAR2
    Is
    Vest_Imp CHAR(10);
    Begin
        Select Rcd.Est_Impresion
        INTO Vest_Imp
        From
        Rcd_Recaudacion_Cab RCD
        Where
        RCD.COD_GRUPO_CIA =cCodGrupoCia_in
        AND RCD.COD_CIA = cCod_Cia_in
        And Rcd.Cod_Local = Ccod_Local_In
        AND RCD.COD_RECAU = cNum_Recaudacion_in;

    RETURN Vest_Imp;
    END;

--=============================================================================================
FUNCTION RCD_GET_EST_RECAUDACION(cCodGrupoCia_in         IN CHAR,
                            Ccod_Cia_In              In Char,
                            cCod_Local_in            IN CHAR,
                            Cnum_Recaudacion_In        In Char)
  RETURN varchar
    Is
         Vnum_RecauAnul CHAR(5);
         Vest_Recau VARCHAR(2);
    Begin
            Select Rcd.COD_RECAU_ANUL_REF
            INTO Vnum_RecauAnul
            From
              Rcd_Recaudacion_Cab RCD
            Where
              RCD.COD_GRUPO_CIA =cCodGrupoCia_in
              AND RCD.COD_CIA = cCod_Cia_in
              And Rcd.Cod_Local = Ccod_Local_In
              AND RCD.COD_RECAU = cNum_Recaudacion_in;

    IF (Vnum_RecauAnul is not null) THEN
        Vest_Recau:='N';

    ELSIF (Vnum_RecauAnul IS NULL) THEN
            Select NVL(RCD.IND_ANUL,Rcd.Est_Rcd)
              INTO Vest_Recau
              From Rcd_Recaudacion_Cab RCD
             Where RCD.COD_GRUPO_CIA = cCodGrupoCia_in
               AND RCD.COD_CIA = cCod_Cia_in
               And Rcd.Cod_Local = Ccod_Local_In
               AND RCD.COD_RECAU = cNum_Recaudacion_in;

    END IF;

    RETURN Vest_Recau;
    END;
--==============================================================================================================

  FUNCTION RCD_TIEMPO_ANUL_RECAUDACION(cCodGrupoCia_in         IN Char,
                                       cCod_Cia_In             In Char,
                                       cCod_Local_in           IN Char,
                                       cNum_Recaudacion_In     In Char)
  RETURN CHAR
  IS
      TIEMPO_MAXIMO  VARCHAR2(30);

      CURSOR curRecaudacion IS
             SELECT SYSDATE - CAB.FEC_MOD_RECAU_PAGO as FECHA
             FROM   RCD_FORMAS_PAGO   DET,
                    RCD_RECAUDACION_CAB CAB
             WHERE  DET.COD_GRUPO_CIA = cCodGrupoCia_in
             AND    DET.COD_CIA = cCod_Cia_In
             AND    DET.COD_LOCAL = cCod_Local_in
             AND    DET.COD_RECAU = cNum_Recaudacion_In
             AND    DET.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
             AND    DET.COD_CIA = CAB.COD_CIA
             AND    DET.COD_LOCAL = CAB.COD_LOCAL
             AND    DET.COD_RECAU = CAB.COD_RECAU;

      cRepta char(1):= 'S';
  BEGIN
      --se obtiene el tiempo máximo para anular un tipo de recaudación
      --en minutos

      TIEMPO_MAXIMO :=
        PTOVENTA_RECAUDACION.RCD_TIEMPO_MAX_ANULACION('RCD');

       FOR v_curRecaudacion IN curRecaudacion
           LOOP
           dbms_output.put_line('fecha: '||v_curRecaudacion.FECHA);
           dbms_output.put_line('TIEMPO: '||TO_NUMBER(TIEMPO_MAXIMO)/24/60);
             IF v_curRecaudacion.FECHA > (TO_NUMBER(TIEMPO_MAXIMO)/24/60) THEN --Conversion a minutos
                cRepta := 'N';
             END IF;
        END LOOP;

     return cRepta;
  END;

--================================================================================================

   FUNCTION RCD_TIEMPO_MAX_ANULACION(cTipo_llave in char)
    RETURN CHAR
    IS
    vTiempo  VARCHAR2(20);
    BEGIN
        SELECT G.DESC_CORTA
          INTO vTiempo
          FROM   PBL_TAB_GRAL G
          WHERE  G.COD_APL     = 'PTO_VENTA'
          AND    G.COD_TAB_GRAL = 'TIEMPO_ANULACION'
          AND    G.LLAVE_TAB_GRAL = trim(cTipo_llave);

        RETURN vTiempo;
    END;

--================================================================================================

  PROCEDURE RCD_ACTUALIZAR_EST_TRSSC_SIX(cCodGrupoCia_in IN CHAR,
                                 cCod_Cia_in  IN CHAR,
                                 cCod_Local_in       IN CHAR,
                                 cCodRecau_in IN CHAR,
                                 cEstTrsscSix IN CHAR,
                                 cCodAutoriz IN CHAR) IS
 BEGIN

    UPDATE RCD_RECAUDACION_CAB
    SET EST_TRSSC_SIX = cEstTrsscSix,
        COD_AUTORIZACION = cCodAutoriz
    WHERE COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_CIA = cCod_Cia_in
    AND COD_LOCAL = cCod_Local_in
    AND COD_RECAU = cCodRecau_in;

 END;

--================================================================================================

  FUNCTION RCD_OBTENER_DNI_USU(cSecUsuLocal_in IN CHAR)
    RETURN CHAR
    IS
      vDNI CHAR(10):=' ';
    BEGIN

        SELECT DNI_USU
        INTO vDNI
        FROM  pbl_usu_local
        WHERE SEC_USU_LOCAL = cSecUsuLocal_in;

    RETURN vDNI;
    END;
--================================================================================================
  FUNCTION RCD_OBTENER_COD_LOCAL_MIGRA(cCodGrupoCia_in IN CHAR,
                                       cCod_Cia_in IN CHAR,
                                       cCod_Local_in IN CHAR) RETURN CHAR IS
    vCOD_LOCAL_MIGRA CHAR(4);
  BEGIN

      select COD_LOCAL_MIGRA
      INTO vCOD_LOCAL_MIGRA
      from pbl_local
      where COD_GRUPO_CIA = cCodGrupoCia_in
      --and COD_CIA = cCod_Cia_in    --No es llave primaria
      and COD_LOCAL = cCod_Local_in;

    RETURN vCOD_LOCAL_MIGRA;
  END;

--================================================================================================

  FUNCTION RCD_IMPRESION_VOUCHER_CMR(cCodGrupoCia_in IN CHAR,
                                     cCod_Cia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cCod_Recau_in IN CHAR,
                                     cNum_Cuotas_in IN CHAR,
                                     cMonto_Pagar_Cuota_in IN CHAR,
                                     cFecha_Venc_Cuota_in IN CHAR,
                                     cCod_Autorizacion_in IN CHAR,
                                     cCod_Caja_in IN CHAR,
                                     cTip_Copia_in IN CHAR) RETURN CLOB
  IS
    impr1 VARCHAR(1000);
    impr2 CLOB;

    nomMarca varchar2(100);
    razonSocial varchar2(100);
    numRuc varchar2(16);
    direccion varchar2(200);

    codLocal varchar(3);
    codCaja varchar(2);
    fechaRecau varchar(10);
    ticket varchar(9);
    horaRecau varchar(8);
    codCajero varchar(6);
    nombreCajero varchar(22);
    numTarjeta varchar(16);
    campo1 varchar(4);
    campo2 varchar(2);
    campo3 varchar(6);
    campo4 varchar(2);
    campo5 varchar(1);
    monto varchar(9);
    numCuotas varchar(2); -- := cNum_Cuotas_in;
    montoCuotas varchar(9); --:= cMonto_Pagar_Cuota_in;
    fechaVenc varchar(10); -- := cFecha_Venc_Cuota_in;
    tipoCuota varchar(15);
    nombreCliente varchar(38);
    dniCliente varchar(8);
    codAutoriz varchar(13);

    --INI ASOSA - 03/09/2014
      vTextoMarca varchar2(50) := '';
      cCodMarca char(3) := '';
      --FIN ASOSA - 03/09/2014
  BEGIN

   --INI ASOSA - 03/09/2014
   SELECT PL.COD_MARCA
   INTO cCodMarca
   FROM PBL_LOCAL PL
   WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
   AND PL.COD_CIA = cCod_Cia_in
   AND PL.COD_LOCAL = cCod_Local_in;

          IF cCodMarca <> COD_CIA_MARKET_01 THEN
                     vTextoMarca := 'BOTICAS ';
          END IF;
          --FIN ASOSA - 03/09/2014

       --se consulta la cabecera
       SELECT MG.NOM_MARCA,
              PC.RAZ_SOC_CIA,
              PC.NUM_RUC_CIA,
              REPLACE(PC.DIR_CIA,' ','{ESP}')
          INTO nomMarca, razonSocial, numRuc, direccion
       FROM PBL_LOCAL PL
       JOIN PBL_MARCA_CIA MC ON (PL.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                 PL.COD_MARCA = MC.COD_MARCA AND
                                 PL.COD_CIA = MC.COD_CIA)
       JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                       MG.COD_MARCA = MC.COD_MARCA)
       JOIN PBL_CIA PC ON (PC.COD_CIA = PL.COD_CIA)
       WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
             AND PL.COD_LOCAL = cCod_Local_in;

       --se consultan los valores del cuerpo
       select substr(RC.COD_LOCAL,-3),                          --3 caracteres
             SUBSTR(cCod_Caja_in,-2),                           --2 caracteres
             TO_CHAR(RC.Fec_Crea_Recau_Pago,'DD/MM/YYYY'),      --DD/MM/YYYY
             LPAD(cCod_Recau_in,5,'0'),                         --9 caracteres
             TO_CHAR(RC.Fec_Crea_Recau_Pago,'HH:mi:ss'),        --HH:mm:SS
             LPAD(UL.COD_TRAB,6,'0'),                           --6 caract
             SUBSTR( UL.NOM_USU ||' '||
                     UL.APE_PAT ||' '||
                     UL.APE_MAT,0,21),                          --21 caracteres
             DECODE(LENGTH(RC.NRO_TARJETA),
                    16,
                    '************'||SUBSTR(RC.NRO_TARJETA,-4)), --16 caracteres
             '----',--????,                                     --4 caracteres
             '--',--??,                                         --2 caracteres
             '------',--??????,                                 --6 caracteres
             '--',--??,                                         --2 caracteres
             '-',--?,                                           --1 caracter
             TO_CHAR(RC.IM_TOTAL,'9,990.00'),                   --9 caracteres
             --'01',--num_cuotas                                  --2 caracteres
             --TO_CHAR(RC.IM_TOTAL,'99990.00'),                   --9 caracteres
             --TO_CHAR(RC.Fecha_Venc_Recau,'DD/MM/YYYY'),         --DD/MM/YYYY
             'NORMAL',--cuota
             SUBSTR(RC.NOM_CLIENTE,-38),                        --38 caracteres
             LPAD(RC.COD_CLIENTE,8,' '),                        --8 caracteres
             RC.FECHA_VENC_CUOTA,
             RC.NRO_CUOTAS,
             TO_CHAR(RC.IM_CUOTA,'9,990.00'),
             trim(RC.COD_AUTORIZACION)
             into codLocal,
                  codCaja,
                  fechaRecau,
                  ticket,
                  horaRecau,
                  codCajero,
                  nombreCajero,
                  numTarjeta,
                  campo1,
                  campo2,
                  campo3,
                  campo4,
                  campo5,
                  monto,
                  --numCuotas,
                  --montoCuotas,
                  --fechaVenc,
                  tipoCuota,
                  nombreCliente,
                  dniCliente,
                  fechaVenc,
                  numCuotas,
                  montoCuotas,
                  codAutoriz
      from RCD_RECAUDACION_CAB RC
      inner join PBL_USU_LOCAL UL on UL.LOGIN_USU=RC.USU_CREA_RECAU_PAGO
      where RC.COD_RECAU = cCod_Recau_in AND
            RC.COD_GRUPO_CIA = cCodGrupoCia_in AND
            RC.COD_CIA = cCod_Cia_in AND
            RC.COD_LOCAL = cCod_Local_in;

      IF numCuotas = '01' THEN
        montoCuotas := monto;
      END IF;

      --si el registro existe devolver el texto en HTML para la impresión
      --<div>{NUM_TARJETA}/{CAMPO_1}/{CAMPO_2}/{CAMPO_3}/{CAMPO_4}/{CAMPO_5}</div>
      impr1:='<html><head><style>
              		.tipoLetraA
              		{	color: black;
              			font: 11px Consolas;
              			width: 235px;
              		}
              	</style>
              </head>';
      impr2:= '<body><div{ESP}class="tipoLetraA">
<div{ESP}align="center">COMPROBANTE CREDITO</div>
<br/>
<div{ESP}align="center">' || vTextoMarca || ' {MARCA}</div>
<div>{RAZON_SOCIAL} RUC:{NUM_RUC}</div>
<div{ESP}align="center">{DIRECCION}</div>
<hr/>
<div>Local: {LOCAL} Caja: {CAJA}  Fecha: {FECHA}</div>
<div>Nro Ope : {TICKET}  Hora : {HORA}</div>
<div>Vendedor: {COD_VENDEDOR}-{NOMBRE_VENDEDOR}</div>
<div>Tarjeta : {NUM_TARJETA}</div>
<div>Cod. Autorización: {COD_AUTORIZ}</div>
<br/>
<div>Cr&eacute;dito              S/.{MONTO}</div>
<div>{CANT_CUOTAS} Cuotas de           S/.{MONTO_CUOTA}</div>
<div>  Venc. : {FECHA_VENC}</div>
<div>  Cuota : {TIPO_CUOTA}</div>
<div>  MONTOS EXPRESADOS EN NUEVOS SOLES</div>
<div>   ACEPTO PAGAR SEGUN CONTRATO CON</div>
<div>           BANCO FALABELLA</div>
<br/>
<br/>';
       IF (cTip_Copia_in = 'O') THEN
            impr2:= impr2 ||
'<div>  Firma: ___________________________</div>
<div{ESP}align="center"> {NOMBRE_CLIENTE} </div>
<div{ESP}align="center"> {DNI_CLIENTE} </div>
<br/>
<div{ESP}align="center">*** ORIGINAL EMISOR ***</div>';
       ELSE
            impr2:= impr2 ||
            '<div{ESP}align="center">*** COPIA CLIENTE ***</div>';
       END IF;
impr2:= impr2 ||
'</div>
</body>
</html>';

      --reemplazamos los valores en la plantilla
      select REPLACE (impr2, '{MARCA}', nomMarca) into impr2 from dual;
      select REPLACE (impr2, '{RAZON_SOCIAL}', razonSocial) into impr2 from dual;
      select REPLACE (impr2, '{NUM_RUC}', numRuc) into impr2 from dual;
      select REPLACE (impr2, '{DIRECCION}', direccion) into impr2 from dual;

      select REPLACE (impr2, '{LOCAL}', codLocal) into impr2 from dual;
      select REPLACE (impr2, '{CAJA}', codCaja) into impr2 from dual;
      select REPLACE (impr2, '{FECHA}', fechaRecau) into impr2 from dual;
      select REPLACE (impr2, '{TICKET}', ticket) into impr2 from dual;
      select REPLACE (impr2, '{HORA}', horaRecau) into impr2 from dual;
      select REPLACE (impr2, '{COD_VENDEDOR}', codCajero) into impr2 from dual;
      select REPLACE (impr2, '{NOMBRE_VENDEDOR}', nombreCajero) into impr2 from dual;
      select REPLACE (impr2, '{NUM_TARJETA}', numTarjeta) into impr2 from dual;
      select REPLACE (impr2, '{COD_AUTORIZ}', codAutoriz) into impr2 from dual;
      /*select REPLACE (impr2, '{CAMPO_1}', campo1) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_2}', campo2) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_3}', campo3) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_4}', campo4) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_5}', campo5) into impr2 from dual;*/
      select REPLACE (impr2, '{MONTO}', monto) into impr2 from dual;
      select REPLACE (impr2, '{CANT_CUOTAS}', numCuotas) into impr2 from dual;
      select REPLACE (impr2, '{MONTO_CUOTA}', montoCuotas) into impr2 from dual;
      select REPLACE (impr2, '{FECHA_VENC}', fechaVenc) into impr2 from dual;
      select REPLACE (impr2, '{TIPO_CUOTA}', tipoCuota) into impr2 from dual;
      select REPLACE (impr2, '{NOMBRE_CLIENTE}', nombreCliente) into impr2 from dual;
      select REPLACE (impr2, '{DNI_CLIENTE}', dniCliente) into impr2 from dual;

	    --reemplazamos los valores en blanco por la etiqueta &nbsp;
      select REPLACE (impr2, ' ', '&nbsp;') into impr2 from dual;

      --incluye espacios en blanco necesarios
      select REPLACE (impr2, '{ESP}', ' ') into impr2 from dual;

      return impr1 || impr2;
  EXCEPTION
      WHEN OTHERS THEN
      return null;
  END;


 FUNCTION RCD_OBTENER_MONTO_TOTAL(cCodGrupoCia_in IN CHAR,
                                  cCod_Cia_in IN CHAR,
  		   						              cCodLocal_in	  IN CHAR,
 		  						                cSecMovCaja_in  IN CHAR)
    RETURN CHAR
  IS
    v_cTotalRecaudacion CHAR(20);
  BEGIN

        SELECT TO_CHAR(SUM(NVL(IM_TOTAL_PAGO,0)),'999,990.00')
        INTO   v_cTotalRecaudacion
        FROM RCD_RECAUDACION_CAB
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_CIA = cCod_Cia_in
        AND COD_LOCAL = cCodLocal_in
        AND SEC_MOV_CAJA = (SELECT sec_mov_caja_origen
                                         FROM   ce_mov_caja c
                                         WHERE  c.cod_grupo_cia = cCodGrupoCia_in
                                         AND    c.cod_local = cCodLocal_in
                                         AND    c.sec_mov_caja = cSecMovCaja_in)
        AND EST_RCD = IND_COBRADO
        AND TIPO_RCD != TIPO_REC_VENTA_CMR
        AND COD_RECAU_ANUL_REF IS NULL --Se añade indicador de anulacion
        AND IND_ANUL IS NULL; --Se añade indicador de anulacion

     RETURN v_cTotalRecaudacion;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cTotalRecaudacion := '0.00';--NO SE ENCONTRO MONTO TOTAL DE RECAUDACION
		 RETURN v_cTotalRecaudacion;
  END;


  FUNCTION RCD_CUOTAS_CMR
    RETURN FarmaCursor  IS  curCuotas FarmaCursor;

    BEGIN

         OPEN curCuotas FOR

              SELECT LLAVE_TAB_GRAL || 'Ã' || DESC_CORTA
              FROM PBL_TAB_GRAL
              WHERE COD_TAB_GRAL='CUOTA_CMR' and
                    COD_APL='PTO_VENTA' ORDER BY ID_TAB_GRAL;

         RETURN curCuotas ;
  END;

  --================================================================================================

 FUNCTION RCD_GET_DATOS_ANUL_VENTA_CMR(cCodGrupoCia_in          IN CHAR,
                                       cCod_Cia_in              IN CHAR,
                                       cCod_Local_in            IN CHAR,
                                       cNum_Pedido_in            IN CHAR)
  RETURN FarmaCursor IS
  curListaRcdPend FarmaCursor;
  BEGIN
  OPEN curListaRcdPend FOR
      select (est_trssc_six || 'Ã' ||
              cod_recau || 'Ã' ||
              cod_trssc_six  || 'Ã' ||
              tip_moneda || 'Ã' ||
              decode(cod_autorizacion,null,' ',cod_autorizacion) || 'Ã' ||
              to_char(fec_crea_recau_pago,'dd/mm/yyyy') || 'Ã' ||
              tipo_pago
             ) AS RESULTADO
            from rcd_recaudacion_cab where
            cod_grupo_cia = cCodGrupoCia_in and
            cod_cia = cCod_Cia_in and
            cod_local = cCod_Local_in and
            tipo_rcd='06' and
            est_rcd='C' and
            num_ped_vta=cNum_Pedido_in
            ORDER BY cod_recau;

   RETURN curListaRcdPend;
  END;

  --================================================================================================

  FUNCTION RCD_ANULAR_RECAU_VENTA_CMR(cCodGrupoCia_in IN CHAR,
                                  cCod_Cia_in IN CHAR,
                                  cCod_Local_in IN CHAR,
                                  cCodRecau_in IN CHAR,
                                  nNumCajaPago_in IN CHAR,
                                  nUsuModRecauPago_in IN CHAR,
                                  nCod_TrsscAnul_in IN CHAR)
         RETURN CHAR IS  vCod_Recau CHAR(5);
         curPagosCab FarmaCursor;
         curPagosDetalle FarmaCursor;

         --RLLantoy 09.07.2013
         v_cCajaAbierta VTA_CAJA_PAGO.IND_CAJA_ABIERTA%TYPE;
         v_cSecUsuLocal VTA_CAJA_PAGO.SEC_USU_LOCAL%TYPE;
         v_cSecMovCaja_in VTA_CAJA_PAGO.SEC_MOV_CAJA%TYPE;

         v_dFecCreaRecau RCD_RECAUDACION_CAB.fec_crea_recau_pago%TYPE;
         v_cIndCorrecto CHAR(1):='N';
         vNumRecauOrigen RCD_RECAUDACION_CAB.COD_RECAU_ANUL_REF%TYPE;
  BEGIN

       dbms_output.put_line('codigo recaudacion a anular: ' || cCodRecau_in);

        FOR curPagosCab IN (SELECT *
                            FROM RCD_RECAUDACION_CAB RC
                            WHERE RC.COD_GRUPO_CIA=cCodGrupoCia_in AND
                            RC.COD_CIA=cCod_Cia_in AND
                            RC.COD_LOCAL=cCod_Local_in AND
                            RC.COD_RECAU = cCodRecau_in)
        LOOP
            vCod_Recau := PTOVENTA_RECAUDACION.RCD_CORRE_COD_RECAU(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in);

            IF (curPagosCab.EST_RCD = IND_COBRADO ) THEN

                      -- VERIFICA SI LA CAJA NO  HA SIDO CERRADA Y AUN PERMANECE ABIERTA
                      SELECT SEC_USU_LOCAL, SEC_MOV_CAJA
                             INTO v_cSecUsuLocal,v_cSecMovCaja_in
                      FROM VTA_CAJA_PAGO
                      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
                        AND COD_LOCAL = cCod_Local_in
                        AND NUM_CAJA_PAGO = nNumCajaPago_in;

                      /*v_cCajaAbierta:= Ptoventa_Caj.CAJ_IND_CAJA_ABIERTA_FORUPDATE(cCodGrupoCia_in,cCod_Local_in, v_cSecUsuLocal, v_cSecMovCaja_in);

                      IF v_cCajaAbierta='N' THEN
                           RAISE_APPLICATION_ERROR(-20021,'LA CAJA NO SE ENCUENTRA ABIERTA.');
                      END IF;
                      */
                            INSERT INTO RCD_RECAUDACION_CAB
                              (COD_GRUPO_CIA,
                               COD_CIA,
                               COD_LOCAL,
                               COD_RECAU,
                               SEC_MOV_CAJA,
                               NRO_TARJETA,
                               TIPO_RCD,
                               TIPO_PAGO,
                               TIP_COMP_PAGO,
                               EST_RCD,
                               EST_CUENTA,
                               COD_CLIENTE,
                               TIP_MONEDA,
                               IM_TOTAL,
                               IM_TOTAL_PAGO,
                               IM_MIN_PAGO,
                               VAL_TIP_CAMBIO,
                               FECHA_VENC_RECAU,
                               NOM_CLIENTE,
                               FEC_VEN_TRJ,
                               USU_CREA_RECAU_PAGO,
                               USU_MOD_RECAU_PAGO,
                               EST_IMPRESION,
                               COD_RECAU_ANUL_REF,
                               COD_AUTORIZACION,
                               EST_TRSSC_SIX,
                               COD_TRSSC_SIX_ANUL,
                               NUM_PED_VTA)
                            VALUES
                              (curPagosCab.COD_GRUPO_CIA,
                               curPagosCab.COD_CIA,
                               curPagosCab.COD_LOCAL,
                               vCod_Recau,
                               v_cSecMovCaja_in,
                               curPagosCab.NRO_TARJETA,
                               curPagosCab.TIPO_RCD,
                               curPagosCab.TIPO_PAGO,
                               curPagosCab.TIP_COMP_PAGO,
                               curPagosCab.EST_RCD,
                               curPagosCab.EST_CUENTA,
                               curPagosCab.COD_CLIENTE,
                               curPagosCab.TIP_MONEDA,
                               (curPagosCab.IM_TOTAL * -1),
                               (curPagosCab.IM_TOTAL_PAGO * -1),
                               (curPagosCab.IM_MIN_PAGO * -1),
                               curPagosCab.VAL_TIP_CAMBIO,
                               curPagosCab.FECHA_VENC_RECAU,
                               curPagosCab.NOM_CLIENTE,
                               curPagosCab.FEC_VEN_TRJ,
                               curPagosCab.USU_CREA_RECAU_PAGO,
                               nUsuModRecauPago_in,
                               curPagosCab.EST_IMPRESION,
                               curPagosCab.COD_RECAU,
                               curPagosCab.COD_AUTORIZACION,
                               'OK',
                               nCod_TrsscAnul_in,
                               ''--curPagosCab.NUM_PED_VTA
							   );

                             dbms_output.put_line('nuevo codigo de recaudacion negativa: ' || vCod_Recau);

                            --Actualiza el estado de la recaudacion anulada

                            update RCD_RECAUDACION_CAB
                            set   IND_ANUL = IND_ANULADO,
                          			  FEC_MOD_RECAU_PAGO = SYSDATE,
                          			  USU_MOD_RECAU_PAGO = nUsuModRecauPago_in
                            where COD_GRUPO_CIA = cCodGrupoCia_in and
                            COD_CIA = cCod_Cia_in and
                            COD_LOCAL = cCod_Local_in and
                            COD_RECAU = cCodRecau_in;
            END IF;

        END LOOP;

       RETURN vCod_Recau;
  END;
  --================================================================================================
  FUNCTION GET_FORMAS_PAGO(cCodGrupoCia_in IN CHAR,
                            cCod_Cia_in IN CHAR,
                            cCod_Local_in IN CHAR,
                            cCodRecau_in IN CHAR)
  RETURN VARCHAR2

  IS
    vTipoMoneda rcd_recaudacion_cab.tip_moneda%type;
    vCount number;
    cursor rcd_fpagos(pTipoMoneda CHAR, pCount NUMBER) is
    select ('<br>'||
          DECODE(rownum,1,'EFECTIVO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
                          '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'||'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'||'&nbsp;') ||
          DECODE(tip_moneda,'02',' $ &nbsp;','S/.') ||to_char(imp_total,'999,990.00') ||'&nbsp;&nbsp;'  ||
          decode(val,1,'',
                       DECODE(pTipoMoneda,'02',' $ &nbsp;','S/.') ||to_char(val,'999,990.00')) ||
          '&nbsp;&nbsp;'  ||
          --T.C.
          decode(tc,1,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
                      '[T.C:'||to_char(tc,'999,990.00')||']')
      )
    campo
from (
select    tip_moneda,
          imp_total,
          --T.C.
        case when pTipoMoneda = '01' then
              case when tip_moneda = '01' then 1
                   when tip_moneda = '02' then val_tip_cambio
              end
        when pTipoMoneda = '02' then
             case when tip_moneda = '01' then val_tip_cambio
                  when tip_moneda = '02' then 1
              end
        end      tc,
        case when pTipoMoneda = '01' then
              case when tip_moneda = '01' then 1
                   when tip_moneda = '02' then trunc(imp_total*val_tip_cambio,2)
              end
        when pTipoMoneda = '02' then
             case when tip_moneda = '01' then round(imp_total/val_tip_cambio,2)
                  when tip_moneda = '02' then 1
              end
        end      val

    from rcd_formas_pago
    where
    COD_GRUPO_CIA = cCodGrupoCia_in
    AND COD_CIA = cCod_Cia_in
    AND COD_LOCAL = cCod_Local_in
    AND COD_RECAU = cCodRecau_in
    order by tip_moneda );

    rcd_pago rcd_fpagos%ROWtype;
    CADENA varchar2(5000);
  begin

    --saber estado de cuenta o tipo de moneda
    select tip_moneda
           into vTipoMoneda
    from rcd_recaudacion_cab
    where cod_recau = cCodRecau_in;

    --saber si pago en ambas formas de pago
    select count(*)
           into vCount
    from rcd_formas_pago where cod_recau = cCodRecau_in order by tip_moneda desc;

    FOR rcd_pago in rcd_Fpagos(vTipoMoneda,vCount)

    loop
      CADENA:=CADENA||rcd_pago.campo;
    end loop;

    RETURN CADENA;
  END;


  --================================================================================================
  FUNCTION RCD_OBTENER_MONTO_TOTAL_DIA(cCodGrupoCia_in IN CHAR,
                                      cCodCia_in IN CHAR,
                                      cCodLocal_in	  IN CHAR,
                                      cFecCierreDia_in  IN CHAR) RETURN CHAR
  IS
    v_cTotalRecaudacion CHAR(20);
  BEGIN

        SELECT TO_CHAR(NVL(SUM(IM_TOTAL_PAGO),0),'999,990.00')
        INTO   v_cTotalRecaudacion
        FROM RCD_RECAUDACION_CAB
        WHERE COD_GRUPO_CIA = cCodGrupoCia_in
        AND COD_CIA = cCodCia_in
        AND COD_LOCAL = cCodLocal_in
        AND SEC_MOV_CAJA IN (SELECT SEC_MOV_CAJA_ORIGEN
                            FROM CE_MOV_CAJA
                            WHERE COD_GRUPO_CIA = cCodGrupoCia_in AND COD_LOCAL = cCodLocal_in
                            AND FEC_DIA_VTA = TO_DATE(cFecCierreDia_in,'DD/MM/YYYY'))
        AND EST_RCD = IND_COBRADO
        AND TIPO_RCD != TIPO_REC_VENTA_CMR;

     RETURN v_cTotalRecaudacion;
   EXCEPTION
  	 WHEN NO_DATA_FOUND THEN
          v_cTotalRecaudacion := '0.00';--NO SE ENCONTRO MONTO TOTAL DE RECAUDACION
		 RETURN v_cTotalRecaudacion;
  END;
  --================================================================================================

  --Descripcion: RETORNA UN LISTADO CONSOLIDADO DE LAS RECAUDACIONES PERTENECIENTES A UNA SECUENCIA DE
  --             MOVIMIENTO DE CAJA
  --Fecha        Usuario		    Comentario
  --25/09/2013   LLEIVA         CREACION
  --11/10/2013   GFONSECA       MODIFICACION / Se resta el vuelto para tener el monto que se pago realmente
  --15/06/2015   ERIOS          Se muestran las recaudaciones anuladas
  FUNCTION RCD_REPORTE_CIERRE_TURNO(cCodGrupoCia_in   IN CHAR,
                                    cCodCia_in        IN CHAR,
                                    cCodLocal_in      IN CHAR,
                                    cSecMovCaja_in    IN CHAR)
  RETURN FarmaCursor
  IS
        curListaRcd FarmaCursor;
	v_cSecMovCajaOrigen PTOVENTA.ce_mov_caja.sec_mov_caja_origen%TYPE;
  BEGIN
  
	SELECT sec_mov_caja_origen
		INTO v_cSecMovCajaOrigen
	 FROM   PTOVENTA.ce_mov_caja c
	 WHERE  c.cod_grupo_cia = cCodGrupoCia_in
	 AND    c.cod_local = cCodLocal_in
	 AND    c.sec_mov_caja = cSecMovCaja_in;
	 
        OPEN curListaRcd FOR
        WITH EMP AS(
				select LLAVE_TAB_GRAL TIPO_RCD,DESC_CORTA,DESC_LARGA DESC_LARGA_RCD
				from PTOVENTA.pbl_tab_gral
				where cod_apl = 'PTO_VENTA'
				AND COD_TAB_GRAL = 'RCD_TIPO_EMPRE'
				AND EST_TAB_GRAL = 'A')
            SELECT ( TR.DESC_LARGA_RCD  || 'Ã' ||
                   DECODE(RFP.TIP_MONEDA,'01','SOLES','02','DOLARES')  || 'Ã' ||
                   COUNT(RFP.IM_TOTAL_PAGO)  || 'Ã' ||
                   --TRIM(TO_CHAR (SUM(RFP.IM_TOTAL_PAGO),'99,999,999,990.00')
                   TRIM(TO_CHAR (SUM(RFP.IM_TOTAL_PAGO - RFP.Val_Vuelto) ,'99,999,999,990.00' )) || 'Ã' || --Se resta el vuelto
				   NVL2(rc.COD_RECAU_ANUL_REF,'S','N')
                   ) as resultado
            FROM PTOVENTA.rcd_formas_pago RFP
            INNER JOIN PTOVENTA.rcd_recaudacion_cab RC ON (RC.COD_GRUPO_CIA = RFP.COD_GRUPO_CIA
                                                            AND RC.COD_CIA = RFP.COD_CIA
                                                            AND RC.COD_LOCAL = RFP.COD_LOCAL
                                                            AND RC.COD_RECAU = RFP.COD_RECAU)
            INNER JOIN EMP TR ON TR.TIPO_RCD=RC.TIPO_RCD
            WHERE RC.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND RC.COD_CIA = cCodCia_in
                  AND RC.COD_LOCAL = cCodLocal_in
                  AND RC.EST_RCD = 'C'
                  AND RC.SEC_MOV_CAJA = v_cSecMovCajaOrigen
                  --rc.COD_RECAU_ANUL_REF IS NULL and
                  --rc.IND_ANUL IS NULL --Se añade indicador de anulacion
            GROUP BY TR.DESC_LARGA_RCD, RFP.TIP_MONEDA, RC.TIPO_RCD,NVL2(rc.COD_RECAU_ANUL_REF,'S','N')
			ORDER BY RC.TIPO_RCD,NVL2(rc.COD_RECAU_ANUL_REF,'S','N');
        RETURN curListaRcd;
  END;


  --================================================================================================

  FUNCTION RCD_IMPRESION_VOUCHER_ANUL_CMR(cCodGrupoCia_in IN CHAR,
                                     cCod_Cia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cCod_Recau_in IN CHAR,
                                     cCod_Caja_in IN CHAR,
                                     cTip_Copia_in IN CHAR) RETURN CLOB
  IS
    impr1 VARCHAR(1000);
    impr2 CLOB;

    nomMarca varchar2(100);
    razonSocial varchar2(100);
    numRuc varchar2(16);
    direccion varchar2(200);

    codLocal varchar(3);
    codCaja varchar(2);
    fechaRecau varchar(10);
    ticket varchar(9);
    horaRecau varchar(8);
    codCajero varchar(6);
    nombreCajero varchar(22);
    numTarjeta varchar(16);
    campo1 RCD_RECAUDACION_CAB.COD_RECAU_ANUL_REF%TYPE;
    campo2 varchar(2);
    campo3 varchar(6);
    campo4 varchar(2);
    campo5 varchar(1);
    monto varchar(9);
    --numCuotas varchar(2) := cNum_Cuotas_in;
    --montoCuotas varchar(9) := cMonto_Pagar_Cuota_in;
    --fechaVenc varchar(10) := cFecha_Venc_Cuota_in;
    tipoCuota varchar(15);
    nombreCliente varchar(38);
    dniCliente varchar(8);
     --INI ASOSA - 03/09/2014
      vTextoMarca varchar2(50) := '';
      cCodMarca char(3) := '';
      --FIN ASOSA - 03/09/2014
  BEGIN

   --INI ASOSA - 03/09/2014
         SELECT PL.COD_MARCA
         INTO cCodMarca
         FROM PBL_LOCAL PL
         WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
         AND PL.COD_CIA = cCod_Cia_in
         AND PL.COD_LOCAL = cCod_Local_in;

          IF cCodMarca <> COD_CIA_MARKET_01 THEN
                     vTextoMarca := 'BOTICAS ';
          END IF;
          --FIN ASOSA - 03/09/2014

       --se consulta la cabecera
       SELECT MG.NOM_MARCA,
              PC.RAZ_SOC_CIA,
              PC.NUM_RUC_CIA,
              REPLACE(PC.DIR_CIA,' ','{ESP}')
          INTO nomMarca, razonSocial, numRuc, direccion
       FROM PBL_LOCAL PL
       JOIN PBL_MARCA_CIA MC ON (PL.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                 PL.COD_MARCA = MC.COD_MARCA AND
                                 PL.COD_CIA = MC.COD_CIA)
       JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                       MG.COD_MARCA = MC.COD_MARCA)
       JOIN PBL_CIA PC ON (PC.COD_CIA = PL.COD_CIA)
       WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
             AND PL.COD_LOCAL = cCod_Local_in;

       --se consultan los valores del cuerpo
       select substr(RC.COD_LOCAL,-3),                          --3 caracteres
             SUBSTR(cCod_Caja_in,-2),                           --2 caracteres
             TO_CHAR(RC.Fec_Crea_Recau_Pago,'DD/MM/YYYY'),      --DD/MM/YYYY
             RC.COD_RECAU,                         --9 caracteres
             TO_CHAR(RC.Fec_Crea_Recau_Pago,'HH:mi:ss'),        --HH:mm:SS
             LPAD(UL.COD_TRAB,6,'0'),                           --6 caract
             SUBSTR( UL.NOM_USU ||' '||
                     UL.APE_PAT ||' '||
                     UL.APE_MAT,0,21),                          --21 caracteres
			 DECODE(LENGTH(RC.NRO_TARJETA),16,'************'||SUBSTR(RC.NRO_TARJETA,-4)), --16 caracteres
             RC.COD_RECAU_ANUL_REF,                                     --4 caracteres
             '--',--??,                                         --2 caracteres
             '------',--??????,                                 --6 caracteres
             '--',--??,                                         --2 caracteres
             '-',--?,                                           --1 caracter
             TO_CHAR((RC.IM_TOTAL*(-1)),'99990.00'),                   --9 caracteres
             --'01',--num_cuotas                                  --2 caracteres
             --TO_CHAR(RC.IM_TOTAL,'99990.00'),                   --9 caracteres
             --TO_CHAR(RC.Fecha_Venc_Recau,'DD/MM/YYYY'),         --DD/MM/YYYY
             'NORMAL',--cuota
             SUBSTR(RC.NOM_CLIENTE,-38),                        --38 caracteres
             LPAD(RC.COD_CLIENTE,8,' ')                         --8 caracteres
             into codLocal,
                  codCaja,
                  fechaRecau,
                  ticket,
                  horaRecau,
                  codCajero,
                  nombreCajero,
                  numTarjeta,
                  campo1,
                  campo2,
                  campo3,
                  campo4,
                  campo5,
                  monto,
                  --numCuotas,
                  --montoCuotas,
                  --fechaVenc,
                  tipoCuota,
                  nombreCliente,
                  dniCliente
      from RCD_RECAUDACION_CAB RC
      inner join PBL_USU_LOCAL UL on UL.LOGIN_USU=RC.USU_CREA_RECAU_PAGO
      where RC.COD_RECAU = cCod_Recau_in AND
            RC.COD_GRUPO_CIA = cCodGrupoCia_in AND
            RC.COD_CIA = cCod_Cia_in AND
            RC.COD_LOCAL = cCod_Local_in;

      /*IF numCuotas = '01' THEN
        montoCuotas := monto;
      END IF;*/

      --si el registro existe devolver el texto en HTML para la impresión
      --<div>{NUM_TARJETA}/{CAMPO_1}/{CAMPO_2}/{CAMPO_3}/{CAMPO_4}/{CAMPO_5}</div>
      impr1:='<html><head><style>
              		.tipoLetraA
              		{	color: black;
              			font: 11px Consolas;
              			width: 235px;
              		}
              	</style>
              </head>';
      impr2:= '<body><div{ESP}class="tipoLetraA">
<div{ESP}align="center">ANULACION VENTA CMR</div>
<br/>
<div{ESP}align="center">' || vTextoMarca || ' {MARCA}</div>
<div>{RAZON_SOCIAL} RUC:{NUM_RUC}</div>
<div{ESP}align="center">{DIRECCION}</div>
<hr/>
<div>Local: {LOCAL} Caja: {CAJA}  Fecha: {FECHA}</div>
<div>Nro Ope : {TICKET}  Hora : {HORA}</div>
<div>Vendedor: {COD_VENDEDOR}-{NOMBRE_VENDEDOR}</div>
<div>Tarjeta : {NUM_TARJETA}</div>
<div>Cr&eacute;dito              S/.{MONTO}</div>
<div>Doc Anulado : {COD_RECAU_ANUL_REF}</div>
<br/>
<br/>';
       --IF (cTip_Copia_in = 'O') THEN
            impr2:= impr2 ||
'----------------------------------------------------------------------------'||
'<div><br/>  Firma: ___________________________</div>
<br/>
<div>  Nombre: __________________________</div>
<br/>
<div>  D.N.I: ___________________________</div>'||
'<br>'||'----------------------------------------------------------------------------'||
'<div align="center" style="font-weight:bold;">'||'ESTE DOCUMENTO NO ES COMPROBANTE DE PAGO'||'</div>'||
'<div align="center" style="font-weight:bold;">'||'NO TIENE VALOR TRIBUTARIO'||'</div>';
/*<div{ESP}align="center">*** ORIGINAL EMISOR ***</div>';
       ELSE
            impr2:= impr2 ||
            '<div{ESP}align="center">*** COPIA CLIENTE ***</div>';
       END IF;*/
impr2:= impr2 ||
'</div>
</body>
</html>';

--<div>Cod. Autorización: {COD_AUTORIZ}</div>

      --reemplazamos los valores en la plantilla
      select REPLACE (impr2, '{MARCA}', nomMarca) into impr2 from dual;
      select REPLACE (impr2, '{RAZON_SOCIAL}', razonSocial) into impr2 from dual;
      select REPLACE (impr2, '{NUM_RUC}', numRuc) into impr2 from dual;
      select REPLACE (impr2, '{DIRECCION}', direccion) into impr2 from dual;

      select REPLACE (impr2, '{LOCAL}', codLocal) into impr2 from dual;
      select REPLACE (impr2, '{CAJA}', codCaja) into impr2 from dual;
      select REPLACE (impr2, '{FECHA}', fechaRecau) into impr2 from dual;
      select REPLACE (impr2, '{TICKET}', ticket) into impr2 from dual;
      select REPLACE (impr2, '{HORA}', horaRecau) into impr2 from dual;
      select REPLACE (impr2, '{COD_VENDEDOR}', codCajero) into impr2 from dual;
      select REPLACE (impr2, '{NOMBRE_VENDEDOR}', nombreCajero) into impr2 from dual;
      select REPLACE (impr2, '{NUM_TARJETA}', numTarjeta) into impr2 from dual;
      --select REPLACE (impr2, '{COD_AUTORIZ}', cCod_Autorizacion_in) into impr2 from dual;
      select REPLACE (impr2, '{COD_RECAU_ANUL_REF}', campo1) into impr2 from dual;
      /*select REPLACE (impr2, '{CAMPO_2}', campo2) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_3}', campo3) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_4}', campo4) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_5}', campo5) into impr2 from dual;*/
      select REPLACE (impr2, '{MONTO}', monto) into impr2 from dual;
      --select REPLACE (impr2, '{CANT_CUOTAS}', numCuotas) into impr2 from dual;
      --select REPLACE (impr2, '{MONTO_CUOTA}', montoCuotas) into impr2 from dual;
      --select REPLACE (impr2, '{FECHA_VENC}', fechaVenc) into impr2 from dual;
      select REPLACE (impr2, '{TIPO_CUOTA}', tipoCuota) into impr2 from dual;
      select REPLACE (impr2, '{NOMBRE_CLIENTE}', nombreCliente) into impr2 from dual;
      select REPLACE (impr2, '{DNI_CLIENTE}', dniCliente) into impr2 from dual;

	    --reemplazamos los valores en blanco por la etiqueta &nbsp;
      select REPLACE (impr2, ' ', '&nbsp;') into impr2 from dual;

      --incluye espacios en blanco necesarios
      select REPLACE (impr2, '{ESP}', ' ') into impr2 from dual;

      return impr1 || impr2;
  EXCEPTION
      WHEN OTHERS THEN
      return null;
  END;

  --================================================================================================

FUNCTION RCD_IMPRESION_VOUCHER_ANUL_RCD(cCodGrupoCia_in IN CHAR,
                                     cCod_Cia_in IN CHAR,
                                     cCod_Local_in IN CHAR,
                                     cCod_Caja_in IN CHAR,
                                     cCod_Recau_in IN CHAR,
                                     cTip_Copia_in IN CHAR) RETURN CLOB
  IS
    impr1 VARCHAR(1000);
    impr2 CLOB;

    nomMarca varchar2(100);
    razonSocial varchar2(100);
    numRuc varchar2(16);
    direccion varchar2(200);

    codLocal varchar(3);
    codCaja varchar(2);
    fechaRecau varchar(10);
    ticket varchar(9);
    horaRecau varchar(8);
    codCajero varchar(6);
    nombreCajero varchar(22);
    numTarjeta varchar(16);
    campo1 RCD_RECAUDACION_CAB.COD_RECAU_ANUL_REF%TYPE;
    campo2 varchar(2);
    campo3 varchar(6);
    campo4 varchar(2);
    campo5 varchar(1);
    monto varchar(9);
    valTipCambio RCD_RECAUDACION_CAB.val_tip_cambio%type;
    totalPago    RCD_RECAUDACION_CAB.Im_Total_Pago%type;
    tipoRCD varchar(15);
    descRCD varchar(30);
    tipoObjeto varchar(25);
    simbMoneda varchar(5);

    simbMoneda3 varchar(5);

    pagoSoles varchar(9);
    pagoDolares varchar(9);
    vuelto varchar(9);

    numObjeto varchar(25);
    --numCuotas varchar(2) := cNum_Cuotas_in;
    --montoCuotas varchar(9) := cMonto_Pagar_Cuota_in;
    --fechaVenc varchar(10) := cFecha_Venc_Cuota_in;
    tipoCuota varchar(15);
    nombreCliente varchar(38);
    dniCliente varchar(25);
    tipMoneda varchar(2);
    montoMoneOrig varchar(9);

    vcMontoTotalPago VARCHAR2(500);
     --INI ASOSA - 03/09/2014
      vTextoMarca varchar2(50) := '';
      cCodMarca char(3) := '';
      --FIN ASOSA - 03/09/2014
  BEGIN

  --INI ASOSA - 03/09/2014
          SELECT PL.COD_MARCA
          INTO cCodMarca
          FROM PBL_LOCAL PL
          WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
          AND PL.COD_CIA = cCod_Cia_in
          AND PL.COD_LOCAL = cCod_Local_in;

          IF cCodMarca <> COD_CIA_MARKET_01 THEN
                     vTextoMarca := 'BOTICAS ';
          END IF;
          --FIN ASOSA - 03/09/2014

       --se consulta la cabecera
       SELECT MG.NOM_MARCA,
              PC.RAZ_SOC_CIA,
              PC.NUM_RUC_CIA,
              REPLACE(PC.DIR_CIA,' ','{ESP}')
          INTO nomMarca, razonSocial, numRuc, direccion
       FROM PBL_LOCAL PL
       JOIN PBL_MARCA_CIA MC ON (PL.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                 PL.COD_MARCA = MC.COD_MARCA AND
                                 PL.COD_CIA = MC.COD_CIA)
       JOIN PBL_MARCA_GRUPO_CIA MG ON (MG.COD_GRUPO_CIA = MC.COD_GRUPO_CIA AND
                                       MG.COD_MARCA = MC.COD_MARCA)
       JOIN PBL_CIA PC ON (PC.COD_CIA = PL.COD_CIA)
       WHERE PL.COD_GRUPO_CIA = cCodGrupoCia_in
             AND PL.COD_LOCAL = cCod_Local_in;


       --VUELTO
       select  TO_CHAR(sum(val_vuelto),'99990.00')
       into   vuelto
       from rcd_formas_pago
       where cod_recau = cCod_Recau_in;

       vcMontoTotalPago := GET_FORMAS_PAGO(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in,cCod_Recau_in);
      /* --PAGO SOLES
       select TO_CHAR(IMP_TOTAL,'99990.00')
       into pagoSoles
       from rcd_formas_pago where COD_RECAU=cCod_Recau_in AND TIP_MONEDA = '01';

       --PAGO DOLARES
       select TO_CHAR(IMP_TOTAL,'99990.00')
       into pagoDolares
       from rcd_formas_pago where COD_RECAU=cCod_Recau_in AND TIP_MONEDA = '02';
       */

      /*  select  decode(tip_moneda,'01','S/.','$'), TO_CHAR(sum(val_vuelto),'99990.00')
        into    simbMoneda3, vuelto
        from rcd_formas_pago
        where cod_recau=cCod_Recau_in;
        */


       --se consultan los valores del cuerpo
       WITH EMP AS (select LLAVE_TAB_GRAL A, DESC_LARGA B
              				from pbl_tab_gral
              				where cod_apl = 'PTO_VENTA'
              				AND COD_TAB_GRAL = 'RCD_TIPO_EMPRE'
              				AND EST_TAB_GRAL = 'A'
                      --AND LLAVE_TAB_GRAL = '01'
        )
       select substr(RC.COD_LOCAL,-3),                          --3 caracteres
             SUBSTR(cCod_Caja_in,-2),                           --2 caracteres
             TO_CHAR(RC.Fec_Crea_Recau_Pago,'DD/MM/YYYY'),      --DD/MM/YYYY
             RC.COD_RECAU,                         --9 caracteres
             TO_CHAR(RC.Fec_Crea_Recau_Pago,'HH:mi:ss'),        --HH:mm:SS
             LPAD(UL.COD_TRAB,6,'0'),                           --6 caract
             SUBSTR( UL.NOM_USU ||' '||
                     UL.APE_PAT ||' '||
                     UL.APE_MAT,0,21),                          --21 caracteres
             -- KMONCADA 08.09.2015
			       CONCAT(CONCAT(SUBSTR(FARMA_UTILITY.F_GET_DESCIFRAR_NRO_TARJETA(RC.NRO_TARJETA),1,4)
                           ,'********'),
                           SUBSTR(FARMA_UTILITY.F_GET_DESCIFRAR_NRO_TARJETA(RC.NRO_TARJETA),13,16)),
             --DECODE(LENGTH(RC.NRO_TARJETA),16,'************'||SUBSTR(RC.NRO_TARJETA,-4)), --16 caracteres
             RC.COD_RECAU_ANUL_REF,                                     --4 caracteres
             '--',--??,                                         --2 caracteres
             '------',--??????,                                 --6 caracteres
             '--',--??,                                         --2 caracteres
             '-',--?,                                           --1 caracter
             TO_CHAR((RC.IM_TOTAL_PAGO*(-1)),'99990.00'),       --9 caracteres
             --'01',--num_cuotas                                  --2 caracteres
             --TO_CHAR(RC.IM_TOTAL,'99990.00'),                   --9 caracteres
             --TO_CHAR(RC.Fecha_Venc_Recau,'DD/MM/YYYY'),         --DD/MM/YYYY
             'NORMAL',--cuota
             SUBSTR(RC.NOM_CLIENTE,-38),                        --38 caracteres
             --LPAD(RC.COD_CLIENTE,8,' '),                        --8 caracteres
             RC.COD_CLIENTE,
             RC.TIPO_RCD,
             EMP.B,
             RC.TIP_MONEDA,
             TO_CHAR((RC.IM_TOTAL*(-1)),'99990.00'),
             RC.Val_Tip_Cambio,
             RC.Im_Total_Pago
             into codLocal,
                  codCaja,
                  fechaRecau,
                  ticket,
                  horaRecau,
                  codCajero,
                  nombreCajero,
                  numTarjeta,
                  campo1,
                  campo2,
                  campo3,
                  campo4,
                  campo5,
                  monto,
                  --numCuotas,
                  --montoCuotas,
                  --fechaVenc,
                  tipoCuota,
                  nombreCliente,
                  dniCliente,
                  tipoRCD,
                  descRCD,
                  tipMoneda,
                  montoMoneOrig,
                  valTipCambio,
                  totalPago
      from RCD_RECAUDACION_CAB RC, PBL_USU_LOCAL UL, EMP
      where RC.COD_RECAU = cCod_Recau_in AND
            RC.COD_GRUPO_CIA = cCodGrupoCia_in AND
            RC.COD_CIA = cCod_Cia_in AND
            RC.COD_LOCAL = cCod_Local_in and
            UL.LOGIN_USU=RC.USU_CREA_RECAU_PAGO AND
            EMP.A = RC.TIPO_RCD;

      --<div>Efectivo  S/.{PAGO_SOLES}</div>
      --<div>          $  {PAGO_DOLARES}</div>
      /*
      	.tipoLetraA
              		{	color: black;
              			font: 11px Consolas;

              			width: 235px;
              		}

        <head><style type="text/css"> body { font-family:Arial, Helvetica, sans-serif;} </style>
        <head><style type="text/css"> body { font-family:Arial, Helvetica, sans-serif;} </style></head>
      */

      impr1:='<html><head><style type="text/css"> body { font-family:Arial, Helvetica, sans-serif;} </style></head>
              <table width="335" border="0"><tr><td>';
      impr2:= '<body><div{ESP}>
<div{ESP}align="center" style="font-size:17px;font-weight:bold;">ANULACION PAGO DE TERCEROS</div>
<br/>
<div{ESP}align="center">' || vTextoMarca || ' {MARCA}</div>
<div>{RAZON_SOCIAL} RUC:{NUM_RUC}</div>
<div{ESP}align="center">{DIRECCION}</div>
<hr/>
<div>Local: {LOCAL} Caja: {CAJA}  Fecha: {FECHA}</div>
<div>Nro Ope : {TICKET}  Hora : {HORA}</div>
<div>Vendedor: {COD_VENDEDOR}-{NOMBRE_VENDEDOR}</div>
<div>Servicio: {COD_RECAU}-{SERV}</div>
<div>{T_OBJ} : {NUM_OBJ}</div>
<div>Monto   : {SIMB_MON}{MONTO}</div>
<div>Doc Anulado : {COD_RECAU_ANUL_REF}</div>
<hr/>'
   ||    vcMontoTotalPago   ||
'<br/>
<br/>';
       --IF (cTip_Copia_in = 'O') THEN
            impr2:= impr2 ||
'----------------------------------------------------------------------------'||
'<div><br/>  Firma: ___________________________</div>
<br/>
<div>  Nombre: __________________________</div>
<br/>
<div>  D.N.I: ___________________________</div>'||
'<br>'||'----------------------------------------------------------------------------'||
'<div align="center" style="font-weight:bold;">'||'ESTE DOCUMENTO NO ES COMPROBANTE DE PAGO'||'</div>'||
'<div align="center" style="font-weight:bold;">'||'NO TIENE VALOR TRIBUTARIO'||'</div>';
/*<div{ESP}align="center" style="font-size:17px;font-weight:bold;">*** ORIGINAL EMISOR ***</div>';
       ELSE
            impr2:= impr2 ||
            '<div{ESP}align="center" style="font-size:17px;font-weight:bold;">*** COPIA CLIENTE ***</div>';
       END IF;*/
impr2:= impr2 ||
'</div>
</td></tr></table>
</body>
</html>';

      IF(tipMoneda='02') THEN--dolares
        simbMoneda:='$';
      ELSE
        simbMoneda:='S/.';
      END IF;

      IF(tipoRCD='01' or tipoRCD='02' or tipoRCD='07') THEN
        tipoObjeto:='Tarjeta';
        numObjeto := numTarjeta;
      ELSE
        tipoObjeto:='Cod/Número';
        numObjeto:= dniCliente;
      END IF;


      /*IF(tipoRCD='04' or tipoRCD='03') THEN
         --monto := montoMoneOrig;
          IF(tipMoneda='02') THEN--dolares
             simbMoneda:='$';
             totalPago := (totalPago / valTipCambio)*-1;
             monto := TO_CHAR(totalPago,'99990.00');
          ELSE
             simbMoneda:='S/.';
          END IF;
      END IF;   */



      --reemplazamos los valores en la plantilla
      select REPLACE (impr2, '{MARCA}', nomMarca) into impr2 from dual;
      select REPLACE (impr2, '{RAZON_SOCIAL}', razonSocial) into impr2 from dual;
      select REPLACE (impr2, '{NUM_RUC}', numRuc) into impr2 from dual;
      select REPLACE (impr2, '{DIRECCION}', direccion) into impr2 from dual;

      select REPLACE (impr2, '{LOCAL}', codLocal) into impr2 from dual;
      select REPLACE (impr2, '{CAJA}', codCaja) into impr2 from dual;
      select REPLACE (impr2, '{FECHA}', fechaRecau) into impr2 from dual;
      select REPLACE (impr2, '{TICKET}', ticket) into impr2 from dual;
      select REPLACE (impr2, '{HORA}', horaRecau) into impr2 from dual;
      select REPLACE (impr2, '{COD_VENDEDOR}', codCajero) into impr2 from dual;
      select REPLACE (impr2, '{NOMBRE_VENDEDOR}', nombreCajero) into impr2 from dual;
      select REPLACE (impr2, '{T_OBJ}', tipoObjeto) into impr2 from dual;
      select REPLACE (impr2, '{SIMB_MON}', simbMoneda) into impr2 from dual;

      --select REPLACE (impr2, '{PAGO_SOLES}', pagoSoles) into impr2 from dual;
      --select REPLACE (impr2, '{PAGO_DOLARES}', pagoDolares) into impr2 from dual;

      select REPLACE (impr2, '{VUELTO}', vuelto) into impr2 from dual;
      select REPLACE (impr2, '{NUM_OBJ}', numObjeto) into impr2 from dual;
      select REPLACE (impr2, '{COD_RECAU}', tipoRCD) into impr2 from dual;
      select REPLACE (impr2, '{SERV}', descRCD) into impr2 from dual;
      --select REPLACE (impr2, '{COD_AUTORIZ}', cCod_Autorizacion_in) into impr2 from dual;
      select REPLACE (impr2, '{COD_RECAU_ANUL_REF}', campo1) into impr2 from dual;
      /*select REPLACE (impr2, '{CAMPO_2}', campo2) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_3}', campo3) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_4}', campo4) into impr2 from dual;
      select REPLACE (impr2, '{CAMPO_5}', campo5) into impr2 from dual;*/
      select REPLACE (impr2, '{MONTO}', montoMoneOrig) into impr2 from dual;
      --select REPLACE (impr2, '{CANT_CUOTAS}', numCuotas) into impr2 from dual;
      --select REPLACE (impr2, '{MONTO_CUOTA}', montoCuotas) into impr2 from dual;
      --select REPLACE (impr2, '{FECHA_VENC}', fechaVenc) into impr2 from dual;
      select REPLACE (impr2, '{TIPO_CUOTA}', tipoCuota) into impr2 from dual;
      select REPLACE (impr2, '{NOMBRE_CLIENTE}', nombreCliente) into impr2 from dual;
      select REPLACE (impr2, '{DNI_CLIENTE}', dniCliente) into impr2 from dual;

	    --reemplazamos los valores en blanco por la etiqueta &nbsp;
      select REPLACE (impr2, ' ', '&nbsp;') into impr2 from dual;

      --incluye espacios en blanco necesarios
      select REPLACE (impr2, '{ESP}', ' ') into impr2 from dual;

      return impr1 || impr2;
  EXCEPTION
      WHEN OTHERS THEN
      return null;
  END;


  --Descripcion: RETORNA EL CODIGO DE ALIANZA PARA LA RECAUDACIÓN SEGUN EL BIN CORRESPONDIENTE
  --Fecha        Usuario		    Comentario
  --05/11/2013   LLEIVA         CREACION
  FUNCTION RCD_GET_CODIGO_ALIANZA(cFormaPago_in IN CHAR) RETURN VARCHAR2
  IS
    --empPagaTarj VARCHAR2(3);
    cod_alianza VARCHAR2(3);
  BEGIN
/*       select COD_EMP_PAG_TARJETA into empPagaTarj
        from aux_forma_pago
        where COD_BIN_TARJETA like '%'||cBin_in||'%' and
              FLG_ACTIVO ='1'
        order by COD_EMP_PAG_TARJETA;

        if(empPagaTarj='001') then
             cod_alianza := '13';            --mastercard
        elsif(empPagaTarj ='002') then
             cod_alianza := '10';            --visa
        elsif(empPagaTarj ='003') then
             cod_alianza := '7';             --CMR
        elsif(empPagaTarj ='004') then
             cod_alianza := '15';            --Dinners
        elsif(empPagaTarj ='005') then
             cod_alianza := '9';             --Claro
        elsif(empPagaTarj ='006') then
             cod_alianza := '12';            --Ripley
        elsif(empPagaTarj ='007') then
             cod_alianza := '14';            --American Express
        elsif(empPagaTarj ='008') then
             cod_alianza := '16';            --Citibank
        elsif(empPagaTarj ='009') then
             cod_alianza := '15';            --Financiero
        else
             cod_alianza := '15';            --Financiero
        end if;*/

        select COD_ALIANZA into cod_alianza
        from VTA_FORMA_PAGO
        where COD_FORMA_PAGO = cFormaPago_in;

        return cod_alianza;
   EXCEPTION
   WHEN OTHERS THEN
        return '';
   END;


  --Descripcion: RETORNA EL NUMERO DE TARJETA Y CODIGO DE AUTORIZACION SEGUN EL PEDIDO
  --Fecha        Usuario		    Comentario
  --06/11/2013   LLEIVA         CREACION
  FUNCTION RCD_GET_TARJ_AUTORIZACION(cNumPedido_in IN CHAR)
  RETURN FarmaCursor
  IS
      curLista FarmaCursor;
  BEGIN
      open curLista for
      select FPP.NUM_TARJ || 'Ã' ||
             FPP.COD_AUTORIZACION as RESULTADO
      from VTA_FORMA_PAGO_PEDIDO FPP
      inner join VTA_FORMA_PAGO FP on FP.COD_FORMA_PAGO=FPP.COD_FORMA_PAGO
      where FPP.num_ped_vta = cNumPedido_in AND
            FP.IND_TARJ='S';

      return curLista;
  END;

  --Descripcion: RETORNA EL NUMERO DE PEDIDO NEGATIVO
  --Fecha        Usuario		    Comentario
  --06/11/2013   LLEIVA         CREACION
  FUNCTION RCD_GET_NUM_PED_NEGATIVO(cNumPedido_in IN CHAR) RETURN VARCHAR2
  IS
       numPedidoOrig VTA_PEDIDO_VTA_CAB.NUM_PED_VTA%TYPE;
  BEGIN
       select NUM_PED_VTA into numPedidoOrig
        from VTA_PEDIDO_VTA_CAB
        where NUM_PED_VTA_ORIGEN =cNumPedido_in;

       return numPedidoOrig;
  EXCEPTION
  WHEN OTHERS THEN
       return '';
  END;

  --================================================================================================


  PROCEDURE RCD_ACT_EST_CONCILIACION(cCodGrupoCia_in IN CHAR,
                                         cCod_Cia_in  IN CHAR,
                                         cCod_Local_in       IN CHAR,
                                         cCod_Autoriz_in IN CHAR,
                                         cFecha_Venta_in IN CHAR,
                                         cTip_Trssc_in IN CHAR,
                                         cPvoucher_in IN CHAR,
                                         cEst_Concili_in IN CHAR) IS
  BEGIN

      UPDATE VTA_LOG_CONCILIACION
      SET EST_CONCILIACION=cEst_Concili_in
      WHERE
      COD_GRUPO_CIA=cCodGrupoCia_in
      AND COD_CIA=cCod_Cia_in
      AND COD_LOCAL=cCod_Local_in
      AND PCOD_AUTORIZACION=cCod_Autoriz_in
      AND PFECHA_VENTA=cFecha_Venta_in
      AND PTIPO_TRANSACCION=cTip_Trssc_in
      AND PVOUCHER=cPvoucher_in;

  END;

  PROCEDURE PROCESA_CONCILIACION_OFFLINE(cCodGrupoCia_in IN CHAR,
                            cCod_Cia_in IN CHAR,
                            cCod_Local_in IN CHAR)
  IS
    CURSOR curPendientes IS
    SELECT LC.ID
    FROM vta_log_conciliacion LC
   where
   est_conciliacion is null
   order by id;

  CURSOR curConciliaciones(nID NUMBER) IS
    SELECT
    TO_NUMBER(LC.COD_LOCAL_MIGRA) COD_LOCAL_MIGRA,
    TO_NUMBER(LC.PID_VENDEDOR) PID_VENDEDOR,
    LC.PFECHA_VENTA,
    TO_NUMBER(REPLACE(LC.PMONTO_VENTA,',','.'),'9999999.000') PMONTO_VENTA,
    TO_NUMBER(LC.PNUM_CUOTAS) PNUM_CUOTAS,
    LC.PCOD_AUTORIZACION,
    LC.PTRACK2,
    LC.PCOD_AUTORIZACION_PRE,
    TO_NUMBER(REPLACE(LC.PFPA_VALORXCUOTA,',','.'),'9999999.000') PFPA_VALORXCUOTA,
    TO_NUMBER(LC.PCAJA) PCAJA,
    TO_NUMBER(LC.PTIPO_TRANSACCION) PTIPO_TRANSACCION,
    LC.PCODISERV,
    -- KMONCADA 08.09.2015 CIFRADO DE NRO TARJETA PARA EL CASO DE RECAUDACIONES
    CASE 
      WHEN PCODISERV IN ('01','02','07') THEN
        FARMA_UTILITY.F_GET_DESCIFRAR_NRO_TARJETA(LC.PFPA_NUM_OBJ_PAGO)
      ELSE
        LC.PFPA_NUM_OBJ_PAGO 
    END PFPA_NUM_OBJ_PAGO,
    --LC.PFPA_NUM_OBJ_PAGO
    LC.PNOMBCLIE,
    TO_NUMBER(LC.PVOUCHER) PVOUCHER,
    TO_NUMBER(LC.PNRO_COMP_ANU) PNRO_COMP_ANU,
    LC.PFECH_COMP_ANU,
    LC.PCODIAUTOORIG,
    TO_NUMBER(REPLACE(LC.PFPA_TIPOCAMBIO,',','.'),'9999999.00') PFPA_TIPOCAMBIO,
    TO_NUMBER(LC.PFPA_NROTRACE) PFPA_NROTRACE,
    TO_NUMBER(LC.PCOD_ALIANZA) PCOD_ALIANZA,
    TO_NUMBER(LC.PCOD_MONEDA_TRX) PCOD_MONEDA_TRX,
    LC.PMON_ESTPAGO
  FROM  vta_log_conciliacion LC
   where
   est_conciliacion is null
   and LC.ID = nID
   FOR UPDATE;

    vSalida VARCHAR2(500);
	v_vDescLocal VARCHAR2(120);
  BEGIN
      IF(cCod_Cia_in = '002')THEN

        --ERIOS 2.2.8 Conciliacion recontra offline
		GENERA_DATOS_CONCILIACION(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in,TO_CHAR(SYSDATE-1,'DD/MM/YYYY'));
        GENERA_DATOS_CONCILIACION(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in,TO_CHAR(SYSDATE,'DD/MM/YYYY'));
		--ERIOS 2.3.2 Conciliacion recaudacion
		--ERIOS 2.4.1 La conciliacion de recaudacion es centralizado
		IF PTOVENTA_GRAL.GET_IND_RECAUDAC_CENTRA() = 0 THEN
		GENERA_DATOS_CONC_RECAU(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in,TO_CHAR(SYSDATE-1,'DD/MM/YYYY'));
        GENERA_DATOS_CONC_RECAU(cCodGrupoCia_in,cCod_Cia_in,cCod_Local_in,TO_CHAR(SYSDATE,'DD/MM/YYYY'));
        END IF;

          FOR pen IN curPendientes
          LOOP
            BEGIN
            FOR rw IN curConciliaciones(pen.ID)
            LOOP
              BEGIN
                DBMS_OUTPUT.PUT_LINE(rw.PFPA_NUM_OBJ_PAGO);
                EXECUTE IMMEDIATE 'BEGIN  COMERCIAL.PRECAUDACION_VENTA_PRAGMA@DB_FASAPROD(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,:14,:15,:16,:17,:18,:19,:20,:21,:22,:23,:24); END;'
                                    USING rw.COD_LOCAL_MIGRA,
                                          rw.PID_VENDEDOR,
                                          rw.PFECHA_VENTA,
                                          rw.PMONTO_VENTA,
                                          rw.PNUM_CUOTAS,
                                          rw.PCOD_AUTORIZACION,
                                          rw.PTRACK2,
                                          rw.PCOD_AUTORIZACION_PRE,
                                          rw.PFPA_VALORXCUOTA,
                                          rw.PCAJA,--10
                                          rw.PTIPO_TRANSACCION,--11
                                          rw.PCODISERV,--12
                                          rw.PFPA_NUM_OBJ_PAGO, --13
                                          rw.PNOMBCLIE,--14
                                          rw.PVOUCHER,--15
                                          rw.PNRO_COMP_ANU,--16
                                          rw.PFECH_COMP_ANU,--17
                                          rw.PCODIAUTOORIG,--18
                                          rw.PFPA_TIPOCAMBIO,--19
                                          rw.PFPA_NROTRACE,--20
                                          rw.PCOD_ALIANZA,--21
                                          rw.PCOD_MONEDA_TRX,--22
                                          rw.PMON_ESTPAGO,--23
                                          IN OUT vSalida;--24
                DBMS_OUTPUT.PUT_LINE('vSalida:'||vSalida);
				IF vSalida = 'OK' THEN
					UPDATE vta_log_conciliacion
					SET EST_CONCILIACION = vSalida,
						FEC_MOD = SYSDATE,
						USU_MOD = 'CONC_OFFLINE'
					WHERE CURRENT OF curConciliaciones;
                ELSE
					--ERIOS 2.3.3 Envia correo de error
					RAISE_APPLICATION_ERROR(-20000,vSalida);
				END IF;
              EXCEPTION
                WHEN OTHERS THEN
                  --ENVIA MAIL
				  --DESCRIPCION DE LOCAL
				  SELECT COD_LOCAL ||' - '|| DESC_LOCAL
					INTO v_vDescLocal
				  FROM PBL_LOCAL
				  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
						AND COD_LOCAL = cCod_Local_in;
				  FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
										   FARMA_GRAL.F_VAR_GET_FARMA_EMAIL('21'),
										   'Error Enviar Datos Conciliador '||v_vDescLocal,
										   'Error PROCESA_CONCILIACION_OFFLINE',
										   '<L><B>' || SQLERRM ||'</B></L>',
										   '',
										   FARMA_EMAIL.GET_EMAIL_SERVER,
										   true);
              END;
            END LOOP;

            COMMIT;
            END;
          END LOOP;
      END IF;
  END;

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
		vUsuCrea_in          IN VARCHAR2)
	AS
	BEGIN
	  INSERT
	  INTO CONC_LOG_RECARGAS
		(
		  COD_GRUPO_CIA,
		  COD_CIA,
		  COD_LOCAL,
		  NUM_PED_VTA,
		  PCL_COD_ID_CONCENTRADOR,
		  PCL_NUMERO_TELEFONO,
		  PCL_COD_AUTORIZACION,
		  PCL_COD_VENDEDOR,
		  PCL_FECHA_VENTA,
		  PCL_HORA_VENTA,
		  PCL_NUMERO_DOCUMENTO,
		  PCL_COD_COMERCIO,
		  PCL_COD_TERMINAL,
		  PCL_MONTO_VENTA,
		  PCL_ID_TRANSACCION,
		  FEC_CREA,
		  USU_CREA
		)
		VALUES
		(
		  cCodGrupoCia_in,
		  cCodCia_in,
		  cCodLocal_in,
		  cNumPedVta_in,
		  vCodIdConcetrador_in,
		  vNumeroTelefono_in,
		  vCodAutorizacion_in,
		  vCodVendedor_in,
		  TO_DATE(vFechaVenta_in,'DD/MM/YYYY'),
		  vHoraVenta_in,
		  vNumeroDocumento_in,
		  vCodComercio_in,
		  vCodTerminal_in,
		  nMontoVenta_in,
		  vIdTransaccion_in,
		  SYSDATE,
		  vUsuCrea_in
		);
	END;


	PROCEDURE SET_ESTADO_LOG_RECARGAS(
		cCodGrupoCia_in     IN CHAR,
		cCodCia_in          IN CHAR,
		cCodLocal_in        IN CHAR,
		cNumPedVta_in       IN CHAR,
		vEstConciliacion_in IN VARCHAR2,
		vUsuMod_in          IN VARCHAR2)
	AS
	BEGIN
	  UPDATE CONC_LOG_RECARGAS
	  SET EST_CONCILIACION = vEstConciliacion_in,
		FEC_MOD            = SYSDATE,
		USU_MOD            = vUsuMod_in
	  WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
	  AND COD_CIA          = cCodCia_in
	  AND COD_LOCAL        = cCodLocal_in
	  AND NUM_PED_VTA      = cNumPedVta_in;
	END;


  PROCEDURE PROCESA_CONC_RECARGAS_OFFLINE(cCodGrupoCia_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodLocal_in IN CHAR)
  IS
    CURSOR curPendientes IS
    SELECT LC.NUM_PED_VTA
    FROM CONC_LOG_RECARGAS LC
   WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
	  AND COD_CIA          = cCodCia_in
	  AND COD_LOCAL        = cCodLocal_in
	  AND est_conciliacion is null;

  CURSOR curConciliaciones(cNumPedVta_in        IN CHAR) IS
    SELECT
    PCL_COD_ID_CONCENTRADOR,
	PCL_NUMERO_TELEFONO,
	PCL_COD_AUTORIZACION,
	PCL_COD_VENDEDOR,
	PCL_FECHA_VENTA,
	PCL_HORA_VENTA,
	PCL_NUMERO_DOCUMENTO,
	PCL_COD_COMERCIO,
	PCL_COD_TERMINAL,
	PCL_MONTO_VENTA,
	PCL_ID_TRANSACCION
  FROM  CONC_LOG_RECARGAS LC
   WHERE COD_GRUPO_CIA  = cCodGrupoCia_in
  AND COD_CIA          = cCodCia_in
  AND COD_LOCAL        = cCodLocal_in
  AND NUM_PED_VTA = cNumPedVta_in
   FOR UPDATE;

    vSalida VARCHAR2(500);
  BEGIN
      IF(cCodCia_in = '002')THEN
          FOR pen IN curPendientes
          LOOP
            BEGIN
            FOR rw IN curConciliaciones(pen.NUM_PED_VTA)
            LOOP
              BEGIN
                DBMS_OUTPUT.PUT_LINE(rw.PCL_NUMERO_TELEFONO);
                EXECUTE IMMEDIATE 'BEGIN  COMERCIAL.PRECARGAS_PRAGMA@DB_FASAPROD(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12); END;'
                                      USING rw.PCL_COD_ID_CONCENTRADOR,
                                            rw.PCL_NUMERO_TELEFONO,
                                            rw.PCL_COD_AUTORIZACION,
                                            rw.PCL_COD_VENDEDOR,
                                            rw.PCL_FECHA_VENTA,
                                            rw.PCL_HORA_VENTA,
                                            rw.PCL_NUMERO_DOCUMENTO,
                                            rw.PCL_COD_COMERCIO,
                                            rw.PCL_COD_TERMINAL,
                                            rw.PCL_MONTO_VENTA,--10
                                            rw.PCL_ID_TRANSACCION,--11
                                            IN OUT vSalida;--12
                DBMS_OUTPUT.PUT_LINE('vSalida:'||vSalida);
                UPDATE CONC_LOG_RECARGAS
                SET EST_CONCILIACION = vSalida,
                    FEC_MOD = SYSDATE,
                    USU_MOD = 'CONC_OFFLINE'
                WHERE CURRENT OF curConciliaciones;

              EXCEPTION
                WHEN OTHERS THEN
                 NULL;
              END;
            END LOOP;

            COMMIT;
            END;
          END LOOP;
      END IF;
  END;

  PROCEDURE GENERA_DATOS_CONCILIACION(cCodGrupoCia_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFechaVenta_in IN VARCHAR2)
  AS
	v_vDescLocal VARCHAR2(120);
  BEGIN
    --ERIOS 26.03.2014 Nro cuotas en Venta CMR
	--ERIOS 2.4.4 Nro Trace
	--            No enviar Venta CMR: anulado o negativo
    INSERT INTO VTA_LOG_CONCILIACION(COD_GRUPO_CIA,
                                      COD_CIA,
                                      COD_LOCAL,
                                      PID_VENDEDOR,
                                      PFECHA_VENTA,
                                      PNUM_CUOTAS,
                                      PCOD_AUTORIZACION,
                                      PTRACK2,
                                      PCOD_AUTORIZACION_PRE,
                                      PFPA_VALORXCUOTA,
                                      PCAJA,
                                      PTIPO_TRANSACCION,
                                      PCODISERV,
                                      PFPA_NUM_OBJ_PAGO,
                                      PNOMBCLIE,
                                      PVOUCHER,
                                      PNRO_COMP_ANU,
                                      PFECH_COMP_ANU,
                                      PCODIAUTOORIG,
                                      PFPA_TIPOCAMBIO,
                                      PFPA_NROTRACE,
                                      PCOD_ALIANZA,
                                      PCOD_MONEDA_TRX,
                                      PMON_ESTPAGO,
                                      DSC_CONCEPTO,
                                      EST_CONCILIACION,
                                      FEC_CREA,
                                      USU_CREA,
                                      FEC_MOD,
                                      USU_MOD,
                                      PMONTO_VENTA,
                                      COD_LOCAL_MIGRA,
                                      ID)
	SELECT V.*,sq_vta_log_conciliacion.nextval "ID"
	FROM (
    SELECT
                CAB.COD_GRUPO_CIA,
                CAB.COD_CIA,
                CAB.COD_LOCAL,
                (SELECT DNI_USU FROM  pbl_usu_local WHERE COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                AND COD_LOCAL = CAB.COD_LOCAL
                AND LOGIN_USU = CAB.USU_CREA_PED_VTA_CAB) PID_VENDEDOR,
                TO_CHAR(cab.FEC_PED_VTA,'dd/MM/yyyy') PFECHA_VENTA,
                NVL(RCD.nro_cuotas,'0') PNUM_CUOTAS,
                NVL(FPA.COD_AUTORIZACION,'0') PCOD_AUTORIZACION,
                ' ' PTRACK2,
                ' ' PCOD_AUTORIZACION_PRE,
                TRIM(TO_CHAR(NVL(RCD.im_cuota,ABS(FPA.IM_TOTAL_PAGO)),'999990.00')) PFPA_VALORXCUOTA,
                '1' PCAJA,
                case when FPA.IM_TOTAL_PAGO > 0 then 1 else 3 end PTIPO_TRANSACCION,
                ' ' PCODISERV,
                substr(FPA.NUM_TARJ,1,16) PFPA_NUM_OBJ_PAGO,
                '' PNOMBCLIE,
                TO_NUMBER(FPA.NUM_PED_VTA) PVOUCHER,
                TO_NUMBER(NVL(CAB.NUM_PED_VTA_ORIGEN,'0'))PNRO_COMP_ANU ,
                TO_CHAR(COR.FEC_PED_VTA,'DDMMYYYY') PFECH_COMP_ANU,
                FPO.COD_AUTORIZACION PCODIAUTOORIG,
                TRIM(TO_CHAR(ABS(CAB.VAL_TIP_CAMBIO_PED_VTA),'999990.00')) PFPA_TIPOCAMBIO,
                mod(NVL(RCD.COD_TRSSC_SIX,0),10000) PFPA_NROTRACE,
                DECODE(FPA.COD_FORMA_PAGO,'00024',7,fp.COD_ALIANZA) PCOD_ALIANZA,
                case when FPA.IM_TOTAL_PAGO > 0 then 6 else 0 end PCOD_MONEDA_TRX,
                '001' PMON_ESTPAGO,
                NULL DSC_CONCEPTO,
                CASE WHEN FPA.COD_FORMA_PAGO != '00024' THEN NULL
                WHEN RCD.IND_ANUL IS NULL AND CAB.NUM_PED_VTA_ORIGEN IS NULL THEN NULL
                ELSE 'EX' END EST_CONCILIACION, --EX: EXTORNADO
                SYSDATE FEC_CREA,
                cab.usu_crea_ped_vta_cab USU_CREA,
                NULL FEC_MOD,
                NULL USU_MOD,
                ABS(FPA.IM_TOTAL_PAGO) PMONTO_VENTA,
                LOC.COD_LOCAL_MIGRA COD_LOCAL_MIGRA
    FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPA ON (CAB.COD_GRUPO_CIA = FPA.COD_GRUPO_CIA
                                                                  AND CAB.COD_LOCAL = FPA.COD_LOCAL
                                                                  AND CAB.NUM_PED_VTA = FPA.NUM_PED_VTA)
                                  join vta_forma_pago fp on (fp.COD_GRUPO_CIA = FPA.COD_GRUPO_CIA AND
                                                             fp.COD_FORMA_PAGO = FPA.COD_FORMA_PAGO)
        LEFT JOIN VTA_PEDIDO_VTA_CAB COR ON (CAB.COD_GRUPO_CIA = COR.COD_GRUPO_CIA
                                              AND CAB.COD_LOCAL = COR.COD_LOCAL
                                              AND CAB.NUM_PED_VTA_ORIGEN = COR.NUM_PED_VTA)

              left JOIN VTA_FORMA_PAGO_PEDIDO FPO ON (COR.COD_GRUPO_CIA = FPO.COD_GRUPO_CIA
                                                                  AND COR.COD_LOCAL = FPO.COD_LOCAL
                                                                  AND COR.NUM_PED_VTA = FPO.NUM_PED_VTA
																  AND FPO.COD_FORMA_PAGO IN ('00083','00084','00087','00088','00024')
																  )
        JOIN PBL_LOCAL LOC ON (CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
                              AND CAB.COD_LOCAL = LOC.COD_LOCAL)
		left join RCD_RECAUDACION_CAB RCD ON (RCD.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                                                  AND RCD.COD_CIA = CAB.COD_CIA
                                                  AND RCD.COD_LOCAL = CAB.COD_LOCAL
                                                  AND RCD.NUM_PED_VTA = CAB.NUM_PED_VTA
                                                  AND RCD.EST_RCD = 'C'
                                                  AND RCD.TIPO_RCD <> COD_RCD_RECAU_RAIZ  --ASOSA - 06/08/2015 - RAIZ
                                                  )
    WHERE 1=1
      and (FPA.COD_FORMA_PAGO IN ('00083','00084','00087','00088')
      --AND fp.ind_tarj = 'S'
      OR (FPA.COD_FORMA_PAGO = '00024' --ERIOS 2.4.3 Si esta activo FARMA_SIX, envia CMR
            AND PTOVENTA_GRAL.GET_IND_PINPAD(CAB.COD_GRUPO_CIA,CAB.COD_CIA,CAB.COD_LOCAL) = 'S'
            AND CAB.FEC_PED_VTA < sysdate - interval '30' minute))
      AND CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.EST_PED_VTA = 'C'
      AND CAB.FEC_PED_VTA BETWEEN TO_DATE(vFechaVenta_in,'DD/MM/YYYY') AND TO_DATE(vFechaVenta_in,'DD/MM/YYYY') +1
      AND NOT EXISTS (
          select COD_GRUPO_CIA,COD_CIA,COD_LOCAL,LPAD(PVOUCHER,10,'0')
          from vta_log_conciliacion
          where
          COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
          AND COD_CIA = CAB.COD_CIA
          AND COD_LOCAL = CAB.COD_LOCAL
          AND PFECHA_VENTA = vFechaVenta_in
          and ptipo_transaccion in (1,3)
          AND PVOUCHER = TO_NUMBER(CAB.NUM_PED_VTA)
      )
	  order by cab.num_ped_vta) V
    ;
	COMMIT;

	--ERIOS 2.3.3 Anulaciones de pinpad directo
    INSERT INTO VTA_LOG_CONCILIACION(COD_GRUPO_CIA,
                                      COD_CIA,
                                      COD_LOCAL,
                                      PID_VENDEDOR,
                                      PFECHA_VENTA,
                                      PNUM_CUOTAS,
                                      PCOD_AUTORIZACION,
                                      PTRACK2,
                                      PCOD_AUTORIZACION_PRE,
                                      PFPA_VALORXCUOTA,
                                      PCAJA,
                                      PTIPO_TRANSACCION,
                                      PCODISERV,
                                      PFPA_NUM_OBJ_PAGO,
                                      PNOMBCLIE,
                                      PVOUCHER,
                                      PNRO_COMP_ANU,
                                      PFECH_COMP_ANU,
                                      PCODIAUTOORIG,
                                      PFPA_TIPOCAMBIO,
                                      PFPA_NROTRACE,
                                      PCOD_ALIANZA,
                                      PCOD_MONEDA_TRX,
                                      PMON_ESTPAGO,
                                      DSC_CONCEPTO,
                                      EST_CONCILIACION,
                                      FEC_CREA,
                                      USU_CREA,
                                      FEC_MOD,
                                      USU_MOD,
                                      PMONTO_VENTA,
                                      COD_LOCAL_MIGRA,
                                      ID)
	WITH
	PIN AS (SELECT NUM_PED_VTA,COD_FORMA_PAGO,dsc_num_autorizacion_trama,dsc_num_referencia_trama,dsc_num_tarjeta_trama
		  FROM VTA_PROCESO_PINPAD_VISA VI
		  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
			AND COD_CIA = cCodCia_in
			AND COD_LOCAL = cCodLocal_in
			AND TIP_REGISTRO = 'ANU'
			AND NUM_PED_VTA IS NOT NULL
			AND COD_FORMA_PAGO IS NOT NULL
			AND VI.FEC_CREA between to_date(vFechaVenta_in,'DD/MM/YYYY') AND to_date(vFechaVenta_in,'DD/MM/YYYY')+1
		  union
		  SELECT NUM_PED_VTA,nvl(COD_FORMA_PAGO,'00084') COD_FORMA_PAGO,dsc_approval_code,dsc_message,dsc_card
		  FROM VTA_PROCESO_PINPAD_MC MC
		  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
				AND COD_CIA = cCodCia_in
				AND COD_LOCAL = cCodLocal_in
				AND TIP_REGISTRO = 'ANU'
				AND NUM_PED_VTA IS NOT NULL
				--AND COD_FORMA_PAGO IS NOT NULL
				AND MC.FEC_CREA between to_date(vFechaVenta_in,'DD/MM/YYYY') AND to_date(vFechaVenta_in,'DD/MM/YYYY')+1
		  )
	SELECT V.*,sq_vta_log_conciliacion.nextval "ID"
	FROM (
    SELECT
                CAB.COD_GRUPO_CIA,
                CAB.COD_CIA,
                CAB.COD_LOCAL,
                (SELECT DNI_USU FROM  pbl_usu_local WHERE COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                AND COD_LOCAL = CAB.COD_LOCAL
                AND LOGIN_USU = CAB.USU_CREA_PED_VTA_CAB) PID_VENDEDOR,
                TO_CHAR(cab.FEC_PED_VTA,'dd/MM/yyyy') PFECHA_VENTA,
                NVL(RCD.nro_cuotas,'0') PNUM_CUOTAS,
                NVL(FPA.COD_AUTORIZACION,'0') PCOD_AUTORIZACION,
                ' ' PTRACK2,
                ' ' PCOD_AUTORIZACION_PRE,
                TRIM(TO_CHAR(NVL(RCD.im_cuota,ABS(FPO.IM_TOTAL_PAGO)),'999990.00')) PFPA_VALORXCUOTA,
                '1' PCAJA,
                case when FPA.IM_TOTAL_PAGO > 0 then 1 else 3 end PTIPO_TRANSACCION,
                ' ' PCODISERV,
                substr(FPO.NUM_TARJ,1,16) PFPA_NUM_OBJ_PAGO,
                '' PNOMBCLIE,
                TO_NUMBER(FPA.NUM_PED_VTA) PVOUCHER,
                TO_NUMBER(NVL(CAB.NUM_PED_VTA_ORIGEN,'0'))PNRO_COMP_ANU ,
                TO_CHAR(COR.FEC_PED_VTA,'DDMMYYYY') PFECH_COMP_ANU,
                FPO.COD_AUTORIZACION PCODIAUTOORIG,
                TRIM(TO_CHAR(ABS(CAB.VAL_TIP_CAMBIO_PED_VTA),'999990.00')) PFPA_TIPOCAMBIO,
                '0' PFPA_NROTRACE,
                DECODE(FPA.COD_FORMA_PAGO,'00024',7,fp.COD_ALIANZA) PCOD_ALIANZA,
                case when FPA.IM_TOTAL_PAGO > 0 then 6 else 0 end PCOD_MONEDA_TRX,
                '001' PMON_ESTPAGO,
                NULL DSC_CONCEPTO,
                NULL EST_CONCILIACION,
                SYSDATE FEC_CREA,
                cab.usu_crea_ped_vta_cab USU_CREA,
                NULL FEC_MOD,
                NULL USU_MOD,
                ABS(FPO.IM_TOTAL_PAGO) PMONTO_VENTA,
                LOC.COD_LOCAL_MIGRA COD_LOCAL_MIGRA
    FROM VTA_PEDIDO_VTA_CAB CAB JOIN VTA_FORMA_PAGO_PEDIDO FPA ON (CAB.COD_GRUPO_CIA = FPA.COD_GRUPO_CIA
                                                                  AND CAB.COD_LOCAL = FPA.COD_LOCAL
                                                                  AND CAB.NUM_PED_VTA = FPA.NUM_PED_VTA)
                                  --join vta_forma_pago fp on (fp.COD_GRUPO_CIA = FPA.COD_GRUPO_CIA AND
                                  --                           fp.COD_FORMA_PAGO = FPA.COD_FORMA_PAGO)
        LEFT JOIN VTA_PEDIDO_VTA_CAB COR ON (CAB.COD_GRUPO_CIA = COR.COD_GRUPO_CIA
                                              AND CAB.COD_LOCAL = COR.COD_LOCAL
                                              AND CAB.NUM_PED_VTA_ORIGEN = COR.NUM_PED_VTA)

              left JOIN VTA_FORMA_PAGO_PEDIDO FPO ON (COR.COD_GRUPO_CIA = FPO.COD_GRUPO_CIA
                                                                  AND COR.COD_LOCAL = FPO.COD_LOCAL
                                                                  AND COR.NUM_PED_VTA = FPO.NUM_PED_VTA
																  AND FPO.COD_FORMA_PAGO IN ('00083','00084','00087','00088','00024')
																  )
        JOIN PBL_LOCAL LOC ON (CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
                              AND CAB.COD_LOCAL = LOC.COD_LOCAL)
		left join RCD_RECAUDACION_CAB RCD ON (RCD.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                                                  AND RCD.COD_CIA = CAB.COD_CIA
                                                  AND RCD.COD_LOCAL = CAB.COD_LOCAL
                                                  AND RCD.NUM_PED_VTA = CAB.NUM_PED_VTA
                                                  AND RCD.EST_RCD = 'C'
                                                  ANd RCD.TIPO_RCD <> COD_RCD_RECAU_RAIZ  --ASOSA - 06/08/2015 - RAIZ
                                                  ),
          PIN, vta_forma_pago fp
    WHERE 1=1
      --and FPA.COD_FORMA_PAGO IN ('00083','00084','00087','00088','00024')
      --AND fp.ind_tarj = 'S'
      and CAB.num_ped_vta_origen = PIN.num_ped_vta
      --AND FPO.COD_FORMA_PAGO IN ('00083','00084','00087','00088','00024')
      --
      AND fp.COD_FORMA_PAGO = PIN.COD_FORMA_PAGO
      --
      AND CAB.COD_GRUPO_CIA = cCodGrupoCia_in
      AND CAB.COD_LOCAL = cCodLocal_in
      AND CAB.EST_PED_VTA = 'C'
      AND CAB.FEC_PED_VTA BETWEEN TO_DATE(vFechaVenta_in,'DD/MM/YYYY') AND TO_DATE(vFechaVenta_in,'DD/MM/YYYY') +1
      AND NOT EXISTS (
          select COD_GRUPO_CIA,COD_CIA,COD_LOCAL,LPAD(PVOUCHER,10,'0')
          from vta_log_conciliacion
          where
          COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
          AND COD_CIA = CAB.COD_CIA
          AND COD_LOCAL = CAB.COD_LOCAL
          AND PFECHA_VENTA = vFechaVenta_in
          and ptipo_transaccion in (1,3)
          AND PVOUCHER = TO_NUMBER(CAB.NUM_PED_VTA)
      )
	  order by cab.num_ped_vta) V
    ;
    commit;

  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
	  --ENVIA MAIL
	  --DESCRIPCION DE LOCAL
	  SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;
      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               FARMA_GRAL.F_VAR_GET_FARMA_EMAIL('21'),
                               'Error Generar Datos Conc. Ventas '||v_vDescLocal,
                               'Error GENERA_DATOS_CONCILIACION',
                               '<L><B>' || SQLERRM ||'</B></L>',
                               '',
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               true);
  END;


  PROCEDURE GENERA_DATOS_CONC_RECAU(cCodGrupoCia_in IN CHAR,
                            cCodCia_in IN CHAR,
                            cCodLocal_in IN CHAR,
                            vFechaVenta_in IN VARCHAR2)
  AS
	v_vDescLocal VARCHAR2(120);
  BEGIN
    --ERIOS 2.4.4 No se envia Recaudaciones anuladas ni negativos
    INSERT INTO VTA_LOG_CONCILIACION(COD_GRUPO_CIA,
                                      COD_CIA,
                                      COD_LOCAL,
                                      PID_VENDEDOR,
                                      PFECHA_VENTA,
                                      PNUM_CUOTAS,
                                      PCOD_AUTORIZACION,
                                      PTRACK2,
                                      PCOD_AUTORIZACION_PRE,
                                      PFPA_VALORXCUOTA,
                                      PCAJA,
                                      PTIPO_TRANSACCION,
                                      PCODISERV,
                                      PFPA_NUM_OBJ_PAGO,
                                      PNOMBCLIE,
                                      PVOUCHER,
                                      PNRO_COMP_ANU,
                                      PFECH_COMP_ANU,
                                      PCODIAUTOORIG,
                                      PFPA_TIPOCAMBIO,
                                      PFPA_NROTRACE,
                                      PCOD_ALIANZA,
                                      PCOD_MONEDA_TRX,
                                      PMON_ESTPAGO,
                                      DSC_CONCEPTO,
                                      EST_CONCILIACION,
                                      FEC_CREA,
                                      USU_CREA,
                                      FEC_MOD,
                                      USU_MOD,
                                      PMONTO_VENTA,
                                      COD_LOCAL_MIGRA,
                                      ID)
	SELECT V.*,sq_vta_log_conciliacion.nextval "ID"
	FROM (
	SELECT
                CAB.COD_GRUPO_CIA,
                CAB.COD_CIA,
                CAB.COD_LOCAL,
                (SELECT DNI_USU FROM  pbl_usu_local WHERE COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
                AND COD_LOCAL = CAB.COD_LOCAL
                AND LOGIN_USU = CAB.USU_CREA_RECAU_PAGO) PID_VENDEDOR,
                TO_CHAR(cab.FEC_CREA_RECAU_PAGO,'dd/MM/yyyy') PFECHA_VENTA,
                '0' PNUM_CUOTAS,
                case when CAB.IM_TOTAL_PAGO > 0 then CAB.COD_AUTORIZACION else CAB.COD_LOCAL||CAB.COD_RECAU end PCOD_AUTORIZACION,
                ' ' PTRACK2,
                ' ' PCOD_AUTORIZACION_PRE,
                '0' PFPA_VALORXCUOTA,
                '1' PCAJA,
                case when CAB.IM_TOTAL_PAGO > 0 then 8 else 9 end PTIPO_TRANSACCION,
                --solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank
                DECODE(CAB.TIPO_RCD,'01','07','02','02','03','15','04','18','07','04','00') PCODISERV,
                -- KMONCADA 08.09.2015 SE CIFRARA LOS NUMEROS DE TARJETA EN RECAUDACIONES
                NVL(FARMA_UTILITY.F_GET_CIFRAR_NRO_TARJETA(CAB.NRO_TARJETA),CAB.COD_CLIENTE) PFPA_NUM_OBJ_PAGO,
                '' PNOMBCLIE,
                TO_NUMBER(CAB.COD_RECAU) PVOUCHER,
                TO_NUMBER(NVL(CAB.COD_RECAU_ANUL_REF,'0'))PNRO_COMP_ANU ,
                TO_CHAR(ANUL.FEC_CREA_RECAU_PAGO,'DD/MM/YYYY') PFECH_COMP_ANU,
                ANUL.COD_AUTORIZACION PCODIAUTOORIG,
                DECODE(CAB.TIP_MONEDA,'02',TRIM(TO_CHAR(ABS(CAB.VAL_TIP_CAMBIO),'999990.00')),'0') PFPA_TIPOCAMBIO,
                mod(TO_NUMBER(NVL(CAB.COD_TRSSC_SIX,CAB.COD_RECAU)),10000) PFPA_NROTRACE,
                DECODE(CAB.TIPO_RCD,'01',7,'02',16,'03',9,'04',16,'07',12,0) PCOD_ALIANZA,
                TO_NUMBER(CAB.TIP_MONEDA) PCOD_MONEDA_TRX,
                DECODE(CAB.TIP_MONEDA,'02','002','001') PMON_ESTPAGO,
                NULL DSC_CONCEPTO,
                CASE WHEN CAB.IND_ANUL IS NULL AND CAB.COD_RECAU_ANUL_REF IS NULL THEN NULL
                ELSE 'EX'END EST_CONCILIACION, --EX: EXTORNADO
                SYSDATE FEC_CREA,
                CAB.USU_CREA_RECAU_PAGO USU_CREA,
                NULL FEC_MOD,
                NULL USU_MOD,
                DECODE(CAB.TIP_MONEDA,'02',ABS(CAB.IM_MIN_PAGO),ABS(CAB.IM_TOTAL_PAGO)) PMONTO_VENTA,
                LOC.COD_LOCAL_MIGRA COD_LOCAL_MIGRA
	FROM RCD_RECAUDACION_CAB CAB
		  JOIN PBL_LOCAL LOC ON (CAB.COD_GRUPO_CIA = LOC.COD_GRUPO_CIA
								  AND CAB.COD_LOCAL = LOC.COD_LOCAL)
		  LEFT JOIN RCD_RECAUDACION_CAB ANUL ON (ANUL.COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
												AND ANUL.COD_CIA = CAB.COD_CIA
												AND ANUL.COD_LOCAL = CAB.COD_LOCAL
												AND ANUL.COD_RECAU = CAB.COD_RECAU_ANUL_REF)
	WHERE CAB.COD_GRUPO_CIA = cCodGrupoCia_in
	AND CAB.COD_CIA = cCodCia_in
	AND CAB.COD_LOCAL = cCodLocal_in
	AND CAB.EST_RCD = 'C'
	AND CAB.TIPO_RCD NOT IN ('06')
	AND CAB.FEC_CREA_RECAU_PAGO < SYSDATE-INTERVAL '30' MINUTE
	AND CAB.FEC_CREA_RECAU_PAGO BETWEEN TO_DATE(vFechaVenta_in,'DD/MM/YYYY') AND TO_DATE(vFechaVenta_in,'DD/MM/YYYY')+1
	AND NOT EXISTS (
			  select 1
			  from vta_log_conciliacion
			  where
			  COD_GRUPO_CIA = CAB.COD_GRUPO_CIA
			  AND COD_CIA = CAB.COD_CIA
			  AND COD_LOCAL = CAB.COD_LOCAL
			  AND PFECHA_VENTA = vFechaVenta_in
			  and ptipo_transaccion in (8,9)
			  AND PVOUCHER = TO_NUMBER(CAB.COD_RECAU)
		  )
	ORDER BY CAB.COD_RECAU
    ) V
    ;
    commit;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
	  --ENVIA MAIL
	  --DESCRIPCION DE LOCAL
	  SELECT COD_LOCAL ||' - '|| DESC_LOCAL
        INTO v_vDescLocal
      FROM PBL_LOCAL
      WHERE COD_GRUPO_CIA = cCodGrupoCia_in
            AND COD_LOCAL = cCodLocal_in;
      FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
                               FARMA_GRAL.F_VAR_GET_FARMA_EMAIL('21'),
                               'Error Generar Datos Conc. Recaudacion '||v_vDescLocal,
                               'Error GENERA_DATOS_CON_RECAU',
                               '<L><B>' || SQLERRM ||'</B></L>',
                               '',
                               FARMA_EMAIL.GET_EMAIL_SERVER,
                               true);
  END;

  FUNCTION LISTA_RCDANULADOS_IMPRIME(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cSecMovCaja_in  IN CHAR)                                   
    RETURN FarmaCursor
  AS
    curCE FarmaCursor;
	v_cSecMovCajaOrigen PTOVENTA.ce_mov_caja.sec_mov_caja_origen%TYPE;
  BEGIN
  
	SELECT sec_mov_caja_origen
		INTO v_cSecMovCajaOrigen
	 FROM   PTOVENTA.ce_mov_caja c
	 WHERE  c.cod_grupo_cia = cCodGrupoCia_in
	 AND    c.cod_local = cCodLocal_in
	 AND    c.sec_mov_caja = cSecMovCaja_in;
  
    OPEN curCE FOR
        WITH EMP AS(
				select LLAVE_TAB_GRAL TIPO_RCD,DESC_CORTA,DESC_LARGA DESC_LARGA_RCD
				from PTOVENTA.pbl_tab_gral
				where cod_apl = 'PTO_VENTA'
				AND COD_TAB_GRAL = 'RCD_TIPO_EMPRE'
				AND EST_TAB_GRAL = 'A')
            SELECT ( TR.DESC_LARGA_RCD  || 'Ã' ||
                   DECODE(RC.TIP_MONEDA,'01','SOLES','02','DOLARES')  || 'Ã' ||
                   RC.COD_RECAU  || 'Ã' ||
				   RC.USU_MOD_RECAU_PAGO  || 'Ã' ||
                   TRIM(TO_CHAR (RC.IM_TOTAL,'99,999,999,990.00') )
                   ) as resultado
            FROM PTOVENTA.rcd_recaudacion_cab RC 
            INNER JOIN EMP TR ON TR.TIPO_RCD=RC.TIPO_RCD
            WHERE RC.COD_GRUPO_CIA = cCodGrupoCia_in
                  AND RC.COD_CIA = cCodCia_in
                  AND RC.COD_LOCAL = cCodLocal_in
                  AND RC.EST_RCD = 'C'
                  AND RC.SEC_MOV_CAJA = v_cSecMovCajaOrigen
                  AND rc.IND_ANUL = 'N'
			ORDER BY RC.TIPO_RCD,RC.COD_RECAU;
	RETURN curCE;
  END;
  
	PROCEDURE ENV_ALERTA_ANULACION_RCD(cCodGrupoCia_in IN CHAR, cCodCia_in IN CHAR, cCodLocal_in IN CHAR, cCodRecau_in IN CHAR)
	IS
		v_vDescLocal VARCHAR2(120);
		v_vReceiverAddress VARCHAR2(120);
		v_vCCReceiverAddress VARCHAR2(120);
		v_vmesg VARCHAR2( 4000 );
	BEGIN
		--ENVIA MAIL
		IF FARMA_GRAL.F_VAR_GET_FARMA_EMAIL('23') <> 'N' THEN
		  --DESCRIPCION DE LOCAL
		  SELECT COD_LOCAL ||' - '|| DESC_LOCAL,
				MAIL_LOCAL
			INTO v_vDescLocal, v_vReceiverAddress
		  FROM PTOVENTA.PBL_LOCAL
		  WHERE COD_GRUPO_CIA = cCodGrupoCia_in
				AND COD_LOCAL = cCodLocal_in;
			--JEFE ZONA
			SELECT Z.EMAIL_JEFE_ZONA || NVL2(Z.NOM_SUBGERENTE,','||Z.NOM_SUBGERENTE,'')
				INTO v_vCCReceiverAddress
			FROM PTOVENTA.VTA_LOCAL_X_ZONA LZ JOIN PTOVENTA.VTA_ZONA_VTA Z ON (LZ.COD_GRUPO_CIA = Z.COD_GRUPO_CIA
			AND LZ.COD_ZONA_VTA = Z.COD_ZONA_VTA)
			WHERE LZ.COD_GRUPO_CIA = cCodGrupoCia_in
			AND LZ.COD_LOCAL = cCodLocal_in
			;
			--CONTADOR
			BEGIN			
				SELECT v_vCCReceiverAddress||','||EMAIL_TRAB
					INTO v_vCCReceiverAddress
				FROM PTOVENTA.CE_MAE_TRAB
				WHERE 1=1
				and tip_doc_ident = '01'
				AND NUM_DOC_IDEN = (SELECT NUM_DOC_IDEN
									FROM PTOVENTA.PBL_USU_LOCAL_VB
									WHERE COD_LOCAL = cCodLocal_in)
				AND EST_TRAB = 'A'
				AND EMAIL_TRAB IS NOT NULL
				AND ROWNUM = 1
				;
			EXCEPTION
				WHEN NO_DATA_FOUND THEN
					v_vCCReceiverAddress := v_vCCReceiverAddress||','||FARMA_GRAL.F_VAR_GET_FARMA_EMAIL('23');
			END;
			
			WITH EMP AS(
					select LLAVE_TAB_GRAL TIPO_RCD,DESC_CORTA,DESC_LARGA DESC_LARGA_RCD
					from PTOVENTA.pbl_tab_gral
					where cod_apl = 'PTO_VENTA'
					AND COD_TAB_GRAL = 'RCD_TIPO_EMPRE'
					AND EST_TAB_GRAL = 'A')
				SELECT '<tr><td>'|| TR.DESC_LARGA_RCD ||'</td>'||
					   '<td>'||DECODE(RC.TIP_MONEDA,'01','SOLES','02','DOLARES') ||'</td>'||
					   '<td>'||RC.COD_RECAU ||'</td>'||
					   '<td>'||RC.USU_MOD_RECAU_PAGO ||'</td>'||
					   '<td>'||TRIM(TO_CHAR (RC.IM_TOTAL,'99,999,999,990.00') ) ||'</td></tr>'
					   INTO v_vmesg
				FROM PTOVENTA.rcd_recaudacion_cab RC 
				INNER JOIN EMP TR ON TR.TIPO_RCD=RC.TIPO_RCD
				WHERE RC.COD_GRUPO_CIA = cCodGrupoCia_in
					  AND RC.COD_CIA = cCodCia_in
					  AND RC.COD_LOCAL = cCodLocal_in
					  AND RC.EST_RCD = 'C'
					  AND rc.IND_ANUL = 'N'
					  AND RC.COD_RECAU = cCodRecau_in;
				
				v_vmesg := '<table border=1 cellspacing=0>
				   <tr><th>Recaudacion</th><th>Moneda</th><th>Voucher</th><th>Usu.Anula</th><th>Importe</th>
				   </tr>'||v_vmesg;
				v_vmesg := v_vmesg||'</table>';
				   
		  FARMA_EMAIL.envia_correo(v_vDescLocal||FARMA_EMAIL.GET_SENDDOR_ADDRESS,
								   v_vReceiverAddress,
								   'Alerta Anulacion Recaudacion '||v_vDescLocal,
								   'ANULACION',
								   v_vmesg,
								   v_vCCReceiverAddress,
								   FARMA_EMAIL.GET_EMAIL_SERVER,
								   true);
		END IF;
	EXCEPTION
		WHEN OTHERS THEN
			DBMS_OUTPUT.PUT_LINE('ERROR: '||SQLERRM);
	END;
END;
/
