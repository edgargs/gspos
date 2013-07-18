package mifarma.ptoventa.caja.reference;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VariablesCaja {
	
	private static final Logger log = LoggerFactory.getLogger(DBCaja.class);
	
	public VariablesCaja() {
		
	}
    public static ArrayList listaCompsDesfasados = new ArrayList();
   
    public static String vTipMovCaja = "";
    public static String vNumCaja = "";
    public static String vTipComp = "";
    public static String vDesComp = "";
    public static String vNumComp = "";
    public static String vNumPedVta = "";
    public static String vSecMovCaja = "";
    public static String vSecMovCajaOrigen = "";
    public static String vCajero = "";

    public static String vFecIniVerComp="";
    public static String vFecFinVerComp="";
    public static String vSecComprobante="";
    public static String vNumCompDesf="";
   
  /* ******************************************* */
  
  public static String vIndPedidoSeleccionado = "N";
  
  public static String vIndDistrGratuita = "N";
  public static String vIndDeliveryAutomatico = "N";
  public static String vIndPedidoConvenio = "N";
  
  public static String vValTotalPagar = "";
  public static String vNumPedPendiente = "";
  public static String vFecPedACobrar = "";
  
  public static String vCodFormaPago = "";
  public static String vDescFormaPago = "";
  public static String vCodMonedaPago = "";
  public static String vDescMonedaPago = "";
  public static boolean vIndCambioMoneda = false;
  
  public static double vMontoMaxPagoTarjeta = 0.00;
  public static String vCantidadCupon = "";
  
  //public static String vCodOperadorTarjeta = "";
  public static boolean vIndTarjetaSeleccionada = false;
  public static boolean vIndDatosTarjeta = false;
  public static boolean vIndCuponSeleccionado = false;
  
  public static String vFormasPagoImpresion = "";
  
  public static String vValTipoCambioPedido = "";
  public static String vValMontoPagado = "";
  public static String vValTotalPagado = "";
  public static String vValVueltoPedido = "";
  public static String vSaldoPedido = "";
  
  public static boolean vIndTotalPedidoCubierto = false;
  
  public static boolean vIndPedidoCobrado = false;
  
  public static int vNumSecImpresionComprobantes = 0;
  
  public static ArrayList vArrayList_SecCompPago = new ArrayList();
  public static ArrayList vArrayList_DetalleImpr = new ArrayList();
  public static ArrayList vArrayList_TotalesComp = new ArrayList();
  
  //Datos Impresora Boleta
  public static String vSecImprLocalBoleta = "";
  public static String vNumSerieLocalBoleta = "";
  //public static String vNumCompBoleta = "";
  //public static String vRutaImprBoleta = "";
  
  //Datos Impresora Factura
  public static String vSecImprLocalFactura = "";
  public static String vNumSerieLocalFactura = "";
  //public static String vNumCompFactura = "";
  //public static String vRutaImprFactura = "";
  
  //Datos Impresora Guia
  public static String vSecImprLocalGuia = "";
  //public static String vNumSerieLocalGuia = "";
  //public static String vNumCompGuia = "";
  //public static String vRutaImprGuia = "";
  
   public static String vSecImprLocalTicket = "";
    public static String vSerieImprLocalTicket = "";
   
  public static String vNumSerieLocalImprimir = "";
  public static String vNumCompImprimir = "";
  public static String vRutaImpresora = "";
  
  //
  public static String vNumPedVta_Anul = "";
  public static String vTipComp_Anul = "";
  public static String vNumComp_Anul = "";
  public static String vMonto_Anul = "";

  public static String vNumCajaImpreso = "";
  public static String vNumTurnoCajaImpreso = "";
  public static String vNumTurnoCaja = "";
  public static String vNomCajeroImpreso = "";
  public static String vApePatCajeroImpreso = "";
  public static String vNomVendedorImpreso = "";
  public static String vApePatVendedorImpreso = "";
  
  //
  public static String vCodProd_Nota = "";
  public static String vNomProd_Nota = "";
  public static String vUnidMed_Nota = "";
  public static String vNomLab_Nota = "";
  public static String vCant_Nota = "";
  public static String vValFrac_Nota = "";
  public static String vCantIng_Nota = "";
  
  public static String vNumTarjCred = "";
  public static String vNomCliTarjCred = "";
  public static String vFecVencTarjCred = "";
  
   public static String vTipOrdComprobantes = ConstantsCaja.TIP_ORD_NUM_COMP;
  
  public static String vValorMax = "" ;
  public static String vTipoAccion = "" ;
  
  public static boolean vIndPedidoConProdVirtual = false;
  public static String vCodProd = "";
  public static String vTipoProdVirtual = "";
  public static String vPrecioProdVirtual = "";
  public static String vNumeroCelular = "";
  public static String vCodigoProv = "";
  public static String vNumeroTraceOriginal = "";
  public static String vCodAprobacionOriginal = "";
  public static String vTipoTarjeta = "";
  
  public static boolean vIndPedidoConProdVirtualImpresion = false;
  public static boolean vIndAnulacionConReclamoNavsat = false;
  
  public static String vDescripcionDetalleFormasPago = "";
  

   /// Variables para las VAliodaciones de Forma de Pago
  /**
   * Variables para el Cobro de un Convenio de Tipo Credito
   * @author :  
   * @since  : 08.09.2007
   */
  public static ArrayList arrayDetFPCredito = new ArrayList();  
  public static String    cobro_Pedido_Conv_Credito     = "N";
  public static String    uso_Credito_Pedido_N_Delivery = "N";  
   
  /**
   * Variables para Verificar el Credito usado_f
   * @author :  
   * @since  : 26.07.2007
   */
  public static ArrayList arrayPedidoDelivery = new ArrayList();
  public static String   usoConvenioCredito = "";
  public static double   valorCredito_de_PedActual = 0.0;
  public static String   monto_forma_credito_ingresado = "0.00";  
 
  /**
   * Campos necesarios por le nuevo proveedor (Brightstar).
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  public static String vFechaTX = "";
  public static String vHoraTX = ""; 
  
  /**
   * Variable para saber si el pedido es de recarga virtual
   * @author  
   * @since  14.11.2007
   */
   public static String vIndPedidoRecargaVirtual = "";
   
  /**
   * Variable para saber si el pedido es de convenio
   * @author  
   * @since  17.03.2008
   */ 
   public static String vIndConvenio = "";
   public static String vCodConvenio = "";
   public static String vCodCliLocal = "";
   
   /**
   * Variable permite pedido campaña
   * @author  
   * @since  03.07.2008
   */ 
   public static String vPermiteCampaña="N";
   
   // Cantidad de cupones impresos
   public static int vNumCuponesImpresos = 0;
   
   /**
    * Se declara la variable para el listado de cupones usados por el pedido
    * ya sea al momento de cobrar y/o anular el pedido
    * @author  
    * @since  03.09.2008
    */
   public static ArrayList listCuponesUsadosPedido = new ArrayList(); 
   public static String vIndLinea = "";
   public static String vIndLineaMatriz = "";
   public static boolean vIndCommitRemota=false;
   
   
   /**
    * Variable para la cantidad de lineas en el detalle de impresion de la boleta
    * dependiendo de si es o no con convenio
    * @author  
    * @since  03.09.2008
    */
   public static int TOTAL_LINEAS_POR_BOLETA_CONVENIO = 0;
   public static int TOTAL_LINEAS_POR_FACTURA_CONVENIO = 0;
   //  25.03.09
   public static int TOTAL_LINEAS_POR_TICKET = 0;
   
    /**
     * Variable que almacena el motivo de la anulacion
     * @author  
     * @since  01.12.2008
     */
    
    public static String vMotivoAnulacion ="";
    
        
    /**
     * Variable indicador en linea ADMCentral
     * @author  
     * @since  15.12.2008
     */
    public static String vIndLineaADMCentral = "";
    
    /**
        * Variable  indicador de conexion a Matriz 
        * @author  
        * @since  11.12.2008
        */
       public static String  vIndConexion    = "";
       
    /**
        * Variable que almacena el estado de impresion sea boleta ,factura ,etc
        * @author  
        * @since  17.12.2008
        */
       public static String vEstadoSinComprobanteImpreso="S";
    
    /**
        * respuesta de exito de recarga virtual
        * @author  
        * @since  19.12.2008
        */
    public static String vRespuestaExito = "";
    
    /**
     * Anulacion Pedidos Fidelizados
     * @Author  
     * @since  18.12.08
     */ 
    public static String vIndPedFidelizado = "";
    public static String vDniCli = "";
    public static String vIndLineaPtoventaMatriz = "N";
    public static boolean vIndCommitRemotaAnul=false;
    public static boolean vCierreDiaAnul=false;
    
    /**
     * @author  
     * @since  06.01.2008
     * metodo encargado de imprimir todos los valores de los atributos
     * de la clase VariablesCaja
     * */
    public static void mostrarValoresVariables(){
    	log.debug("==================================================");
    	log.debug("         variables de la clase VariablesCaja       ");
    	log.debug("===================================================");
    	log.debug("listaCompsDesfasados:"+listaCompsDesfasados+"");
    	log.debug("vTipMovCaja:"+vTipMovCaja+"");
    	log.debug("vNumCaja:"+vNumCaja+"");
    	log.debug("vTipComp:"+vTipComp+"");
    	log.debug("vDesComp:"+vDesComp+"");
    	log.debug("vNumComp:"+vNumComp+"");
    	log.debug("vNumPedVta:"+vNumPedVta+"");
    	log.debug("vSecMovCaja:"+vSecMovCaja+"");
    	log.debug("vSecMovCajaOrigen:"+vSecMovCajaOrigen+"");
    	log.debug("vCajero:"+vCajero+"");
    	log.debug("vFecIniVerComp:"+vFecIniVerComp+"");
    	log.debug("vFecFinVerComp:"+vFecFinVerComp+"");
    	log.debug("vSecComprobante:"+vSecComprobante+"");
    	log.debug("vNumCompDesf:"+vNumCompDesf+"");
    	log.debug("vIndPedidoSeleccionado:"+vIndPedidoSeleccionado+"");
    	log.debug("vIndDistrGratuita:"+vIndDistrGratuita+"");
    	log.debug("vIndDeliveryAutomatico:"+vIndDeliveryAutomatico+"");
    	log.debug("vIndPedidoConvenio:"+vIndPedidoConvenio+"");
    	log.debug("vValTotalPagar:"+vValTotalPagar+"");
    	log.debug("vNumPedPendiente:"+vNumPedPendiente+"");
    	log.debug("vFecPedACobrar:"+vFecPedACobrar+"");
    	log.debug("vCodFormaPago:"+vCodFormaPago+"");
    	log.debug("vDescFormaPago:"+vDescFormaPago+"");
    	log.debug("vCodMonedaPago:"+vCodMonedaPago+"");
    	log.debug("vDescMonedaPago:"+vDescMonedaPago+"");
    	log.debug("vIndCambioMoneda:"+vIndCambioMoneda+"");
    	log.debug("vMontoMaxPagoTarjeta:"+vMontoMaxPagoTarjeta+"");
    	log.debug("vCantidadCupon:"+vCantidadCupon+"");
    	log.debug("vIndTarjetaSeleccionada:"+vIndTarjetaSeleccionada+"");
    	log.debug("vIndDatosTarjeta:"+vIndDatosTarjeta+"");
    	log.debug("vIndCuponSeleccionado:"+vIndCuponSeleccionado+"");
    	log.debug("vFormasPagoImpresion:"+vFormasPagoImpresion+"");
    	log.debug("vValTipoCambioPedido:"+vValTipoCambioPedido+"");
    	log.debug("vValMontoPagado:"+vValMontoPagado+"");
    	log.debug("vValTotalPagado:"+vValTotalPagado+"");
    	log.debug("vValVueltoPedido:"+vValVueltoPedido+"");
    	log.debug("vSaldoPedido:"+vSaldoPedido+"");
    	log.debug("vIndTotalPedidoCubierto:"+vIndTotalPedidoCubierto+"");
    	log.debug("vIndPedidoCobrado:"+vIndPedidoCobrado+"");
    	log.debug("vNumSecImpresionComprobantes:"+vNumSecImpresionComprobantes+"");
    	log.debug("vArrayList_SecCompPago:"+vArrayList_SecCompPago+"");
    	log.debug("vArrayList_DetalleImpr:"+vArrayList_DetalleImpr+"");
    	log.debug("vArrayList_TotalesComp:"+vArrayList_TotalesComp+"");
    	log.debug("vSecImprLocalBoleta:"+vSecImprLocalBoleta+"");
    	log.debug("vSecImprLocalFactura:"+vSecImprLocalFactura+"");
    	log.debug("vSecImprLocalGuia:"+vSecImprLocalGuia+"");
    	log.debug("vNumSerieLocalImprimir:"+vNumSerieLocalImprimir+"");
    	log.debug("vNumCompImprimir:"+vNumCompImprimir+"");
    	log.debug("vRutaImpresora:"+vRutaImpresora+"");
    	log.debug("vNumPedVta_Anul:"+vNumPedVta_Anul+"");
    	log.debug("vTipComp_Anul:"+vTipComp_Anul+"");
    	log.debug("vNumComp_Anul:"+vNumComp_Anul+"");
    	log.debug("vMonto_Anul:"+vMonto_Anul+"");
    	log.debug("vNumCajaImpreso:"+vNumCajaImpreso+"");
    	log.debug("vNumTurnoCajaImpreso:"+vNumTurnoCajaImpreso+"");
    	log.debug("vNumTurnoCaja:"+vNumTurnoCaja+"");
    	log.debug("vNomCajeroImpreso:"+vNomCajeroImpreso+"");
    	log.debug("vApePatCajeroImpreso:"+vApePatCajeroImpreso+"");
    	log.debug("vNomVendedorImpreso:"+vNomVendedorImpreso+"");
    	log.debug("vApePatVendedorImpreso:"+vApePatVendedorImpreso+"");
    	log.debug("vCodProd_Nota:"+vCodProd_Nota+"");
    	log.debug("vNomProd_Nota:"+vNomProd_Nota+"");
    	log.debug("vUnidMed_Nota:"+vUnidMed_Nota+"");
    	log.debug("vNomLab_Nota:"+vNomLab_Nota+"");
    	log.debug("vCant_Nota:"+vCant_Nota+"");
    	log.debug("vValFrac_Nota:"+vValFrac_Nota+"");
    	log.debug("vCantIng_Nota:"+vCantIng_Nota+"");
    	log.debug("vNumTarjCred:"+vNumTarjCred+"");
    	log.debug("vNomCliTarjCred:"+vNomCliTarjCred+"");
    	log.debug("vFecVencTarjCred:"+vFecVencTarjCred+"");
    	log.debug("vTipOrdComprobantes:"+vTipOrdComprobantes+"");
    	log.debug("vValorMax:"+vValorMax+"");
    	log.debug("vTipoAccion:"+vTipoAccion+"");
    	log.debug("vIndPedidoConProdVirtual:"+vIndPedidoConProdVirtual+"");
    	log.debug("vCodProd:"+vCodProd+"");
    	log.debug("vTipoProdVirtual:"+vTipoProdVirtual+"");
    	log.debug("vPrecioProdVirtual:"+vPrecioProdVirtual+"");
    	log.debug("vNumeroCelular:"+vNumeroCelular+"");
    	log.debug("vCodigoProv:"+vCodigoProv+"");
    	log.debug("vNumeroTraceOriginal:"+vNumeroTraceOriginal+"");
    	log.debug("vCodAprobacionOriginal:"+vCodAprobacionOriginal+"");
    	log.debug("vTipoTarjeta:"+vTipoTarjeta+"");
    	log.debug("vIndPedidoConProdVirtualImpresion:"+vIndPedidoConProdVirtualImpresion+"");
    	log.debug("vIndAnulacionConReclamoNavsat:"+vIndAnulacionConReclamoNavsat+"");
    	log.debug("vDescripcionDetalleFormasPago:"+vDescripcionDetalleFormasPago+"");
    	log.debug("arrayDetFPCredito:"+arrayDetFPCredito+"");
    	log.debug("cobro_Pedido_Conv_Credito:"+cobro_Pedido_Conv_Credito+"");
    	log.debug("uso_Credito_Pedido_N_Delivery:"+uso_Credito_Pedido_N_Delivery+"");
    	log.debug("arrayPedidoDelivery:"+arrayPedidoDelivery+"");
    	log.debug("usoConvenioCredito:"+usoConvenioCredito+"");
    	log.debug("valorCredito_de_PedActual:"+valorCredito_de_PedActual+"");
    	log.debug("monto_forma_credito_ingresado:"+monto_forma_credito_ingresado+"");
    	log.debug("vFechaTX:"+vFechaTX+"");
    	log.debug("vHoraTX:"+vHoraTX+"");
    	log.debug("vIndPedidoRecargaVirtual:"+vIndPedidoRecargaVirtual+"");
    	log.debug("vIndConvenio:"+vIndConvenio+"");
    	log.debug("vCodConvenio:"+vCodConvenio+"");
    	log.debug("vCodCliLocal:"+vCodCliLocal+"");
    	log.debug("vPermiteCampaña:"+vPermiteCampaña+"");
    	log.debug("vNumCuponesImpresos:"+vNumCuponesImpresos+"");
    	log.debug("listCuponesUsadosPedido:"+listCuponesUsadosPedido+"");
    	log.debug("vIndLinea:"+vIndLinea+"");
    	log.debug("vIndLineaMatriz:"+vIndLineaMatriz+"");
    	log.debug("vIndCommitRemota:"+vIndCommitRemota+"");
    	log.debug("TOTAL_LINEAS_POR_BOLETA_CONVENIO:"+TOTAL_LINEAS_POR_BOLETA_CONVENIO+"");
    	log.debug("TOTAL_LINEAS_POR_FACTURA_CONVENIO:"+TOTAL_LINEAS_POR_FACTURA_CONVENIO+"");
    	log.debug("vMotivoAnulacion:"+vMotivoAnulacion+"");
    	log.debug("vIndLineaADMCentral:"+vIndLineaADMCentral+"");
    	log.debug("vIndConexion:"+vIndConexion+"");
    	log.debug("vEstadoSinComprobanteImpreso:"+vEstadoSinComprobanteImpreso+"");
    	log.debug("vRespuestaExito:"+vRespuestaExito+"");
    	log.debug("vIndPedFidelizado:"+vIndPedFidelizado+"");
    	log.debug("vDniCli:"+vDniCli+"");
    	log.debug("vIndLineaPtoventaMatriz:"+vIndLineaPtoventaMatriz+"");
    	log.debug("vIndCommitRemotaAnul:"+vIndCommitRemotaAnul+"");
    	log.debug("vCierreDiaAnul:"+vCierreDiaAnul+"");
    	log.debug("===================FIN de variablesCaja===============================");   	
    }
    
    /**
     * Remitos Prosegur
     * @Author  
     * @since  18.12.08
     */ 
    public static String vFechaIni = "";
    public static String vFechaFin = "";
    public static String NumRemito = "";
    public static String FecRemito = "";
    public static String FechaVta = "";
   // public static String CodLocal = "";
    public static int cPos=0;
    
    public static ArrayList vArrayFechasSeleccinadas = new ArrayList();
    
    public static String vColumna="";
    public static String vOrden="";
    
    public static boolean vIndEnvioRecargar = false;
    
    /**
     * Variables Secuencial de Comprobantes para Aperturar y Cerrar Caja
     * @Author  
     * @since  01.02.09
     */
    public static int  vNumeroBoleta=0;
    public static int  vNumeroFactura=0;
    
    
    
    /**
     * Variables Codigo de Cajero
     * @Author  
     * @since  11.02.09
     */
     public static String vSec_usu_local = "";
    
    /**
     * indicador de parametros en Respuesta Recarga
     * @author  
     * @since  11.02.2009
     */

    public static boolean vRespRecargaPmtros = false;
    
    /**
     * indicador de mostrar Mensaje
     * @author  
     * @since  13.02.2009
     */

    public static boolean vMuestreMensaje = false;
    
    
    public static String vValEfectivo = "";
    public static String vVuelto = "";
   
    /**
     * variable que guarda el secuencial del usuario
     * @author  
     * @since  29.04.2009
     */
    //  24/04/09 metodo imprimir ticket de anulacion            
     public static String vSecuenciaUsoUsuario = "";
    
    
    //  16.06.09  
     public static String vDescripImpr = "";
    
    /**
     * variables ingreso de sobre
     * @AUTHOR  
     * @SINCE 03.11.09
     * */
    
     public static String vCodTipoMon = "";
    public static String vDescTipoMon = "";
    public static String vCodFormaPagoTmp="";
    public static String vDescFormaPagoTmp = "";
    public static String vCodMonedaPagoTmp = "";
    public static String vDescMonedaPagoTMp = "";
    public static String vValMontoPagadoTmp = "";
    public static String vDescMonedaPagoTmp = "";
    public static String vValTotalPagadoTmp = "";
    public static String vSecMovCajaTmp = "";
    public static String vIndSobreTmp = "";
    public static String vCodigoSobreTmp = "";
    public static String vSecTmp = "";
    
    
    //  15.12.09
    //Punto de LLegada para Vta institucional.
    public static String vPuntoLlegada = "";
    public static String vPuntoPartida = "";
    
    //  12.01.2011
    public static String vMostrarMontoComprobante = "S";
    //JQUISPE 13.01.2011
    public static boolean vVerificaCajero = true;
    //  16.09.2011    
    public static boolean vImprimeFideicomizo = false;
    public static String vCadenaFideicomizo = "";
    
    
    
}