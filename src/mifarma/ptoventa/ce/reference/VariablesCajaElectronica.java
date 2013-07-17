package mifarma.ptoventa.ce.reference;

import java.util.*;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : VariablesCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      31.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 * 
 */
 
public class VariablesCajaElectronica 
{
  public VariablesCajaElectronica()
  {
  }
   
  /******************************PAULO*********************************/
   public static ArrayList listaCompsDesfasados = new ArrayList();
   public static ArrayList listaEliminacion = new ArrayList();
   public static ArrayList compMinMax = new ArrayList();
   public static ArrayList compMinMaxCD = new ArrayList();
   public static String vBoletaMin = "";
   public static String vBoletaMax = "";
   public static String vFacturaMin = "";
   public static String vFacturaMax = "";
   public static String vTipMovCaja = "";
   public static String vCantidad = "" ;
   public static String vIndEliminacion = "" ;
   public static String vIndAutomatico = "" ;
   public static String vSecMovCaja = "" ;
   public static String vCodFormaPago = "";
   public static String vTipMoneda = "";
   public static boolean vIndFuncionesHabilitadas = false;
   public static String vNumSerie = "";
   public static String vTipComp = "";
   public static String vNumComp = "";
   public static String vMontMoneda = "";
   public static String vMontTotal = "";
   public static String vMontVuelto = "";
   public static String vCodMoneda = "";
   public static String vNumSerieBillete = "";
   public static String vSecCuadratura = "";
   //public static ArrayList vArrayListEliminacion = new ArrayList();
   public static int vValidaEliminacionCuadratura = 0;
   public static ArrayList vArrayListEliminacionEfectivoRendido = new ArrayList();
   public static ArrayList vArrayListInsertar = new ArrayList();
   public static ArrayList vArrayListInsertarCotizacion = new ArrayList();
   public static String vNumerodeNota = "";
   public static String vDescCuadratura = "";
   public static String vTipCuadratura = "";
   public static String vNumeroCuenta = "";
   public static String vCodServicio = "";
   public static String vGlosa = "";
   public static String vDescFormaPago = "";
   public static boolean vExisteVBQF = false;
   public static String vIndVBContable = "" ;
   public static String vLote = "" ;
   public static String vIndTarjeta = "" ; 
   public static String vIndDebito = "" ;
   
   
  /******************************PAULO*********************************/
  
  /**************LMESIA***************/
  public static String vIndVBCajero = "";
  public static String vIndVBQF = "";
  public static boolean vUsuarioCajero = false;
  public static boolean vUsuarioQF = false;
  public static double vFaltante = 0.00;
  public static String vNombreUsuarioLogueado = "";
  public static String vDiaVenta = "";
  public static String vNumCaja = "";
  public static String vCodCajero = "";
  public static String vNumTurno = "";
  public static String vNombreCajero = "";
  public static String vFecAperturaCaja = "";
  public static String vFecCierreCaja = "";
  public static String vObsCierreTurno = "";
  public static String vFechaDiaCajaTurno = "";
  
  public static String vTipoCambio = "";
  public static String vFechaCierreDia = "";
  public static String vIndVBCierreDia = "";
  public static String vSecUsuLocalCierreDia = "";
  public static boolean vRegistrarCierreDia = false;
  public static double vDiferenciaCierreDia = 0.00;
  public static double vDiferenciaSistemaCierreTurno_CD = 0.00;
  
  public static boolean vExisteFechaCierreDia = false;
  public static boolean vExisteVBCierreDia = false;
  public static boolean vExistenCajasSinVBQFDiaVenta = false;
  public static String vObsCierreDia = "";
  public static String vNombreUsuarioVBCierreDia = "";
  
  //comprobantes EN GENERAL
  public static String vTipCompGeneral = "";
  public static String vNumSerieGeneral = "";
  public static String vNumeroMinGeneral = "";
  public static String vNumeroMaxGeneral = "";
  public static String vIndRangoGrabado = "";
  //comprobantes CIERRE DE TURNO
  public static String vTipCompUsuario = "";
  public static String vNumSerieUsuario = "";
  public static String vNumeroMinUsuario = "";
  public static String vNumeroMaxUsuario = "";
  //comprobantes CIERRE DE DIA
  public static String vTipCompDia = "";
  public static String vNumSerieDia = "";
  public static String vNumeroMinDia = "";
  public static String vNumeroMaxDia = "";
  
  //comprobantes EN GENERAL
  public static String vBoletaMinGeneral = "";
  public static String vBoletaMaxGeneral = "";
  public static String vFacturaMinGeneral = "";
  public static String vFacturaMaxGeneral = "";
  //comprobantes CIERRE DE TURNO
  public static String vBoletaMinUsuario = "";
  public static String vBoletaMaxUsuario = "";
  public static String vFacturaMinUsuario = "";
  public static String vFacturaMaxUsuario = "";
  //comprobantes CIERRE DE DIA
  public static String vBoletaMinDia = "";
  public static String vBoletaMaxDia = "";
  public static String vFacturaMinDia = "";
  public static String vFacturaMaxDia = "";
  
  public static ArrayList vRangoCompIngresadosCierreDia = new ArrayList();
  
  public static String vIndCompValidos = "";
  public static String vTipoIngresoComprobantes = "";
  
  public static boolean vExistenCompReclamosNavsat = false;
  /**************LMESIA***************/

 /*-----BEGIN ERIOS BLOCK-----*/
  public static String vCodCuadratura = "";
  public static String vFechaCuadratura = "";
  public static String vCodEntidadFinanciera = "";
  public static String vCodTipoMoneda = "";
  /*------END ERIOS BLOCK------*/ 
  
  public static String vMotivoCuadratura = "";
  
  public static String vIsVentanaCierreDiaOpen = "";
  
  /**
   * Variable agregada para ver si la forma de pago 
   * se asociara a un sobre o no
   * @author dubilluz
   * @since  14.01.2008
   */
  public static String vIndSobre = "";
  public static String vSecFPEntrega = "" ;
  public static String vCodigoSobre = "" ;
  
  
  //jcallo 02/02/2009
  public static String vIndChangeSobre = "N" ;
  public static int vCantModSobres = 3 ;
  public static String vFecCierreCajaAux="";
  
  /*****************************Cambio formas de pago****************************/
  
    public static String vIndTipo="%";
    public static String vNumPedVta="%";
    public static String vMonto="%";
    public static String vCodFPago="";
    public static String vMontPago="";
    public static String vTipCambio="";
    public static String vFechaPed="";
    public static String vEstPed="";
    
    public static String vSaldoPago="";
    public static boolean vIndTotalCubierto = false;
    public static String vValVuelto = "";
    public static String vFechaDia = "";
    public static String vIndTarj = "";


/******************************************************************/
    
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
  
   //public static String vTipOrdComprobantes = ConstantsCaja.TIP_ORD_NUM_COMP;
  
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
   * @author : dubilluz
   * @since  : 08.09.2007
   */
  public static ArrayList arrayDetFPCredito = new ArrayList();  
  public static String    cobro_Pedido_Conv_Credito     = "N";
  public static String    uso_Credito_Pedido_N_Delivery = "N";  
   
  /**
   * Variables para Verificar el Credito usado_f
   * @author : dubilluz
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
   * @author dubilluz
   * @since  14.11.2007
   */
   public static String vIndPedidoRecargaVirtual = "";
   
  /**
   * Variable para saber si el pedido es de convenio
   * @author JCORTEZ
   * @since  17.03.2008
   */ 
   public static String vIndConvenio = "";
   public static String vCodConvenio = "";
   public static String vCodCliLocal = "";
   
   /**
   * Variable permite pedido campaña
   * @author JCORTEZ
   * @since  03.07.2008
   */ 
   public static String vPermiteCampaña="N";
   
   // Cantidad de cupones impresos
   public static int vNumCuponesImpresos = 0;
   
   /**
    * Se declara la variable para el listado de cupones usados por el pedido
    * ya sea al momento de cobrar y/o anular el pedido
    * @author dubilluz
    * @since  03.09.2008
    */
   public static ArrayList listCuponesUsadosPedido = new ArrayList(); 
   public static String vIndLinea = "";
   public static String vIndLineaMatriz = "";
   public static boolean vIndCommitRemota=false;
   
   
   /**
    * Variable para la cantidad de lineas en el detalle de impresion de la boleta
    * dependiendo de si es o no con convenio
    * @author dveliz
    * @since  03.09.2008
    */
   public static int TOTAL_LINEAS_POR_BOLETA_CONVENIO = 0;
   public static int TOTAL_LINEAS_POR_FACTURA_CONVENIO = 0;
   //JCORTEZ 25.03.09
   public static int TOTAL_LINEAS_POR_TICKET = 0;
   
    /**
     * Variable que almacena el motivo de la anulacion
     * @author asolis
     * @since  01.12.2008
     */
    
    public static String vMotivoAnulacion ="";
    
        
    /**
     * Variable indicador en linea ADMCentral
     * @author dveliz
     * @since  15.12.2008
     */
    public static String vIndLineaADMCentral = "";
    
    /**
        * Variable  indicador de conexion a Matriz 
        * @author asolis
        * @since  11.12.2008
        */
       public static String  vIndConexion    = "";
       
    /**
        * Variable que almacena el estado de impresion sea boleta ,factura ,etc
        * @author asolis
        * @since  17.12.2008
        */
       public static String vEstadoSinComprobanteImpreso="S";
    
    /**
        * respuesta de exito de recarga virtual
        * @author dveliz
        * @since  19.12.2008
        */
    public static String vRespuestaExito = "";
    
    /**
     * Anulacion Pedidos Fidelizados
     * @Author JCORTEZ
     * @since  18.12.08
     */ 
    public static String vIndPedFidelizado = "";
    public static String vDniCli = "";
    public static String vIndLineaPtoventaMatriz = "N";
    public static boolean vIndCommitRemotaAnul=false;
    public static boolean vCierreDiaAnul=false;
    
    
    
    /**
     * Remitos Prosegur
     * @Author JCORTEZ
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
     * @Author ASOLIS
     * @since  01.02.09
     */
    public static int  vNumeroBoleta=0;
    public static int  vNumeroFactura=0;
    
    
    
    /**
     * Variables Codigo de Cajero
     * @Author ASOLIS
     * @since  11.02.09
     */
     public static String vSec_usu_local = "";
    
    /**
     * indicador de parametros en Respuesta Recarga
     * @author asolis
     * @since  11.02.2009
     */

    public static boolean vRespRecargaPmtros = false;
    
    /**
     * indicador de mostrar Mensaje
     * @author asolis
     * @since  13.02.2009
     */

    public static boolean vMuestreMensaje = false;
    
    
    public static String vValEfectivo = "";
    public static String vVuelto = "";
   
    /**
     * variable que guarda el secuencial del usuario
     * @author mfajardo
     * @since  29.04.2009
     */
    //Mfajardo 24/04/09 metodo imprimir ticket de anulacion            
     public static String vSecuenciaUsoUsuario = "";
    
    
    //JCORTEZ 16.06.09  
     public static String vDescripImpr = "";
    
    /**
     * variables ingreso de sobre
     * @AUTHOR JCORTEZ
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
    
    
    //JMIRANDA 15.12.09
    //Punto de LLegada para Vta institucional.
    public static String vPuntoLlegada = "";
    public static String vPuntoPartida = "";
    
    //Jquispe 04.06.2010
    public static String vIndCambioFormaPago = "";
    public static boolean indExitoCambioFP = false;
    
    //ASOSA, 21.06.2010
    public static String indAutoFSEN = "N";
  
    //dubilluz 27.07.2010
    public static String pSecUsu_APRUEBA_SOBRE = "";
    //ASOSA, 11.08.2010
    public static String idUsu_APRUEBA_SOBRE="";
    
    //JQUISPE 20/12/2010 INDICADOR QUE DEJE GRABAR CIERRE DE TURNO DE UN MISMO CAJERO Y QUIMICO.  
    public static boolean indUsu_Cierre_Caj_QF = false;
}
