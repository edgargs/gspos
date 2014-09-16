package mifarma.ptoventa.inventario.reference;

import java.awt.event.KeyEvent;

import java.util.ArrayList;

import mifarma.common.FarmaTableModel;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : VariablesInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.02.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class VariablesInventario
{
   // public static FarmaTableModel tableModelListaGlobalProductos = new FarmaTableModel(ConstantsInventario.columnsListaMaestroProductos,ConstantsInventario.defaultValuesListaMaestroProducto,0);
    
    public VariablesInventario(){
        
    }
    
    
    //ASOSA  08.02.2010, Variable para las guias con transportista
    public static ArrayList lista=new ArrayList();
    
    //ASOSA 08.02.2010, Variables para agregar en la recepcion entrega
    public static String vNumGuia02="";
    public static String vNumEntrega="";
    public static String vNumNotaEst="";
    public static String vSecGuia="";
    public static int cPos=0;
    
  //Pendiente de eliminar cuando se utilize validacion usuario.
  //public static String vCantMaxDetGuia = "34";

  //DlgGuiaIngresoProductos - DlgGuiaIngresoCantidad
  public static String vCodProd = "";
  public static String vNomProd = "";
  public static String vUnidMed = "";
  public static String vStkFisico = "";
  public static String vNomLab = "";
  public static String vCantGuia = "";
  public static String vCant = "";
  public static String vCantFrac = "";
  public static int vStock = 0;
  public static String vFrac = "";
  public static String vLote = "";
  public static String vFechaVec = "";
  public static String vPrecUnit = "";
  public static String vTotal = "";
  public static String vValFrac_Guia = "";

  public static ArrayList vArrayGuiaIngresoProductos = new ArrayList();

  //DlgGuiaIngresoCabecera
  public static String vFecGuia = "";
  public static String vTipoDoc = "";
    public static String vDescDoc = "";
  public static String vNumDoc = "";
  public static String vTipoOrigen = "";
    public static String vNomOrigen = "";
  public static String vCodOrigen = "";
    public static String vDescOrigen = "";
  public static String vTipoMotivoKardex="";
  
  public static String vNombreTienda = ""; 
  public static String vCiudadTienda = ""; 
  public static String vRucTienda = "";
  
  //DlgGuiaIngresoRecibidas
  public static String vNumNota = "";
  public static String vTipoNota = "";
  public static String vEstadoNota = "";
  public static String vTipoNotaOrigen = "";

  //DlgTransferenciasIngresoCantidad - DlgTransferenciasListaProductos
  public static String vFechaHora_Transf = "";
  public static String vCodProd_Transf = "";
  public static String vNomProd_Transf = "";
  public static String vUnidMed_Transf = "";
  public static String vStkFisico_Transf = "";
  public static String vNomLab_Transf = "";
  public static String vValFrac_Transf = "";
  public static String vPrecVta_Transf = "";

  public static String vCant_Transf = "";
  public static String vCant_Ingresada_Temp = "";  
  public static String vLote_Transf = "";
  public static String vFechaVec_Transf = "";
  public static String vTotal_Transf = "";

  public static String vStk_Prod = "";
  public static String vStk_ModEntero = "";
  public static String vStk_ModFrac = "";
  
  public static ArrayList vArrayTransferenciaProductos = new ArrayList();

  //DlgTransferenciasTransporte
  public static String vTipoDestino_Transf = "";
  public static String vMotivo_Transf_Interno = "";
  public static String vMotivo_Transf = "";
  public static String vDescMotivo_Transf = "";
  //JMIRANDA 10.12.09 descripción larga para impresión
  public static String vDescMotivo_Transf_Larga = "";
  public static String vCodDestino_Transf = "";
  public static String vDestino_Transf = "";
  public static String vRucDestino_Transf = "";
  public static String vDirecDestino_Transf = "";
  public static String vTransportista_Transf = "";
  public static String vRucTransportista_Transf = "";
  public static String vDirecTransportista_Transf = "";
  public static String vPlacaTransportista_Transf = "";
  public static boolean vHistoricoLote = true;
  public static String vDirecOrigen_Transf = "";

  // Recepcion de Productos

	public static String vNumNotaEs = "";
	public static String vCantItems = "";
	public static String vFecCreaNota = "";
	public static String vEstRecepcion = "";
	public static String vDescProd = "";
	public static String vDescUnidPresent = "";
  public static String vDescUnidFrac = "";
	public static String vValorFrac = "";
	public static String vStkFis = "";
	public static String vSecDetNota = "";
	public static String vCantMov = "";
	public static int vSelectedRow = 0;
	public static String vNumPag = "";
  public static String vNumGuia = "";
  
  //JMIRANDA 14.12.09
  public static String vTipoPedRep = "";

  //DlgMovKardex
  public static String vFecIniMovKardex = "";
  public static String vFecFinMovKardex = "";
  public static String vCodFiltro = "";

  //DlgPedidoReposicionIngresoCantidad
  public static String vFechaHora_PedRep = "";
  public static int vPos_PedRep = 0;
  public static int vCantIngreso = 0;
  
  public static String vCodProd_PedRep="";
  public static String vNomProd_PedRep="";
  public static String vUnidMed_PedRep="";
  public static String vValFrac_PedRep="";
  public static String vNomLab_PedRep="";
  public static String vCant_PedRep="";
  public static String vStkFisico_PedRep="";
  public static String vCantSug_PedRep="";
  public static String vCantMax_PedRep="";
  
  //DlgPedidoReposicionVer
  public static String vRotProm_PedRep = "";
  public static String vMinDias_PedRep = "";
  public static String vMaxDias_PedRep = "";
  public static String vItemsUltPed_PedRep = "";
  public static String vProdsUltPed_PedRep = "";
  
  //DlgPedidoReposicionDetalle
  public static String vNroPed_PedRep = "";
  public static String vFecPed_PedRep = "";
    
  //Imprimir Guias
  public static String vNumGuiaImprimir = "";
  
  //Transferencia a Matriz
  public static boolean vTransfMatriz = false;
  
  public static int vPos = 0;
  
  //DlgExcesoListado
  public static String vSecExcProd = "";
  public static String vCantExcProd = "";
  public static String vNumEntExcProd = "";
  public static String vNumLotExcProd = "";
  public static String vFecVecExcProd = "";
    
  public static String vNomInHashtableGuias = "";
  public static String vCodLocalDestino = "";
  
  /**
   * Autor: Luis Reque
   * Fecha: 25/01/2007
   * */
  /*Nuevas variables*/
  public static String vNombreInHashtable ="";
  public static String vNombreInHashtableVal ="";

  public static int vCampo = 0;
  public static String vOrden = "";
  
  public static String vFechaCalculoMaxMin = "";

  /**
   * @Autor: Luis Reque
   * @Fecha: 20/04/2007
   * */
  public static String vCantAdicional = "";
  public static boolean vMostrarAdic = true;
  
  /**
   * @Autor: PAULO CESAR AMEGHINO ROJAS
   * @Fecha: 12/07/2007
   * */
  public static String vIndProdVirtual = "";
  
  /**
   * @Autor: PAULO CESAR AMEGHINO ROJAS
   * @Fecha: 12/07/2007
   * */
  public static String vCantAtendida = "" ;
  public static String vNumPedido = "" ;
  /**
   * @Autor: PAULO CESAR AMEGHINO ROJAS
   * @Fecha: 29/08/2007
   * */
  public static String vCodMotKardex = "" ;  
  public static String vIndExclusion = "" ;  
  
  /**
   * Variable para saber si se habilitara 
   * el texto de fraccion
   * @author dubilluz
   * @since  15.10.2007
   */
  public static String vIndTextFraccion = "" ;
  
  /**
   * Variables Competencia
   * @author dubilluz
   * @since  12.11.2007
   */
  public static String vRucCompetencia = "" ;
  public static String vDescripcionCompetencia = "" ;
  
  /**
   * Variables para la cotizacion 
   * @author dubilluz
   * @since  26.11.2007
   */
  public static ArrayList vArrayDatosCotizacion = new ArrayList();
  public static String vNumNota_Anular = "" ;
  public static String vIndAnularNota  = "" ;
  
  /**
   * @author Daniel Fernando veliz La Rosa
   * @since  10.09.08
   */
   public static String vTituloIngresoCantidadAdicional = "";
   
 /**
 * Variable permite fraccionar
 * @author JCORTEZ
 * @since  17.04.2008
 */
 public static String vCFraccion = "";
 
 /**
  * Variables de Historial de productos pedido adicional
  * @author Daniel Fernando Veliz La Rosa
  * @since  12.09.08
  */
  public static String vCodProdHist = "";
  public static String vDesProdHist = "";
  
 /**
   * Variables modulo Pedidos Especiales
   * Flag de que ya se abrio el dialogo de pedido especial nuevo
   * @author JCALLO
   * @since  29.09.2008
   */
  public static boolean vFlagF2Nuevo = false;

  
 /**
 * Variables modulo Pedidos Especiales
 * @author JCORTEZ
 * @since  09.09.2008
 */
    public static int vPosi = 0;
    public static String vCantIng ="";
    public static String vCodProd_esp ="";
    public static String vNomProd_esp ="";
    public static String vUnidMed_esp ="";
    public static String vNomLab_esp ="";
    public static String vStkFisico_esp ="";
    public static String vValFrac_esp ="";
    public static String vPrecVta_esp ="";
    public static String vEstado ="";
    
    public static boolean ingresoDetalle =false;
    
    //jcallo indicador de si es en INGRESAR CANTIDAD NUEVA o modificar cantidad
    public static boolean flag_modificarCantidad = false;
    
    public static boolean flag_F3 = false;
    
    
  public static String vNumPedidoEspecial="";
  public static String vFecEmi_esp="";
  public static String vEstCab_esp="";
  
  public static ArrayList vArrayProductosEspeciales = new ArrayList(); //
  //array producto para el diaolgo resumen de pedido especial
  public static ArrayList vArrayListaProdsEsp = new ArrayList(); //
  public static ArrayList vArrayProductosDet= new ArrayList(); // 
  
  
  /**
   * Table model global para solo listar una vez los productos y trabajar sobre estos.
   * @author    DVELIZ
   * @since     18.10.08
   * 
   */
  public static FarmaTableModel tableModelEspecial = new FarmaTableModel(ConstantsInventario.columnsListaProductosEspeciales,
                ConstantsInventario.defaultValuesListaProductosEspeciales,0);;
  
  public static boolean vEsModificado;
  
  
  public static boolean vFNuevo = false;
  public static boolean vFModificar = false;
  public static boolean vIrResumen = false;
  
  public static String vIndLineaMatriz = "N";
  
  public static int vTipoFormatoImpresion = 0;
  
  //JMIRANDA 09.02.10
  public static String vNumEntAfectar = ""; //Entrega a Afectar
  
  //JMIRANDA 16.02.10
  public static boolean vIndTransfRecepCiega = false; 
  
  //JMIRANDA 25.03.2010
  public static boolean vIndModProdTransf = false; 
  
  public static String vBusquedaProdTransf = "";
  
  public static KeyEvent vKeyPress = null;
  
  public static String fechaVencLoteX=""; //ASOSA, 14.04.2010
  public static String nroLoteX=""; //ASOSA, 14.04.2010

  //ASOSA, 14.07.2010
  public static String secRespStk="";   
  
    /**
     * Contenido Ordenes de Compra
     * @Author Luis Ruiz
     * @since 21.05.2013 
     * 
     */
  
   public static ArrayList vArrayIngresoMercaderiaDirecta = new ArrayList();
 
   public static String vNumOrdenCompra   = "";
   public static String vTipoDocument     = "";
   public static String vFechaEmision     = "";
   public static String vCodLocal         = "";
   public static String vCantItem         = "";
   public static String vImporteTotal     = "";
   public static String vCodProducto      = "";
   public static String vDescripcion      = "";
   public static String vCodFormaPago     = "";
   public static String vDescFormaPago    = "";
   public static String vCodProveedor     = "";
   public static String vDescProveedor    = "";
   public static String vUnidMedida       = "";
   public static String vRecepTotal       = "";
   public static String vFechIngreso      = "";
   public static String vSerieDocument    = "";
   public static String vNumeroDocument   = "";
   public static String vRUCProveedor     = "";
   public static String vEstadoOrdComp    = "";
   public static String vDescripDocum     = "";
   public static String vIdeDocumento     = "";
   public static String vNumerGuia        = "";
   public static String vSecRecepcion        = "";
   
   public static ArrayList vArrayOrdenCompraDetalle = new ArrayList();
     
   public static String vCantPedida     = "";
   public static String vCantEntregada  = ""; 
   public static String vPrecioUnit     = "";
   public static String vPrecioIGV      = "";
   public static String vIGVDB          = "";
   public static String vRedondeo       = "";
   public static String vCodProdOrdComp = "";
   public static String vCantPedOrdComp = "";
   public static String vSEC            = "";
   public static String vImportRecep    = "";
   
  public static ArrayList vArrayDetalleGuiaRecep = new ArrayList();
  
    public static String vEstadoDoc    = "";
    public static String vCodProv      = ""; 
    public static String vTipDocum     = "";
    public static String vCodigoCia    = "";
          
   
    //GFonseca 16.12.2013 inicializa el array de datos para devolucion de Mercaderia Directa.
    public static ArrayList vArrayProductos = new ArrayList();
    
    /**
     * Contenido Guia de Remision
     * @Author Cesar Huanes
     * @since 16.12.13
     * 
     */  
    
 public static ArrayList vArrayGuiaRemision=new ArrayList();
 public static ArrayList vArrayNota=new ArrayList();
    
    public static String vFecha="";
    public static String vDescLocal="";
    public static String vDireLocal="";
    public static String vRucDestina="";
    public static String vNomDesctina="";
    public static String vDireDestina="";
    public static String vRucTransp="";
    public static String vNomTransp="";
    public static String vDireTransp="";
    public static String vPlacTransp="";
    public static String vTexImpr="";
    
    /**
     * Contenido datos de devolucion
     * @Author Cesar Huanes
     * @since 24.12.13
     * 
     */
   
    public static  ArrayList vArrayDevol=new ArrayList();
    public static String  vfechaStock=" ";
    public static String  vCantUniPrese=" ";
    public static String  vStockDispo=" ";
    public static String  vCodigo=" ";
    public static String  vDescrip=" ";
    public static String  vLaborat=" ";
    public static String  vUnidLab=" ";
    public static String  vValFrac=" "; 
    public static String  vIndProdConf=" ";
    public static String vPrecVtaVig=" ";
    public static String vDescUniPrese="";
    
    public static int  vCantMovi=0;
    public static String  vMunLote=" ";    
    public static String  vFechVentprod=" ";
    public static double vValPrectotal=0;
    public static int secResStock=0;
    public static String vFechVenc=" ";
    
    public static String vCodProve=""; 
    public static String vNomProve=""; 
    public static ArrayList vArrayTemp=new ArrayList();
    public static String datos="";
    //CHUANES 27.02.2014
    //flag de esditar una cant. de devolucion
    public static boolean vFlagCantMov; 

    
    

    
 



}
