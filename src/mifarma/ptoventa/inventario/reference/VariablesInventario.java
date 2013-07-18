package mifarma.ptoventa.inventario.reference;

import java.awt.event.KeyEvent;

import java.util.*;

import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;

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
    
    //   08.02.2010, Variable para las guias con transportista
    public static ArrayList lista=new ArrayList();
    
    //  08.02.2010, Variables para agregar en la recepcion entrega
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
  //  10.12.09 descripción larga para impresión
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
  
  //  14.12.09
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
   * @Autor:   CESAR AMEGHINO ROJAS
   * @Fecha: 12/07/2007
   * */
  public static String vIndProdVirtual = "";
  
  /**
   * @Autor:   CESAR AMEGHINO ROJAS
   * @Fecha: 12/07/2007
   * */
  public static String vCantAtendida = "" ;
  public static String vNumPedido = "" ;
  /**
   * @Autor:   CESAR AMEGHINO ROJAS
   * @Fecha: 29/08/2007
   * */
  public static String vCodMotKardex = "" ;  
  public static String vIndExclusion = "" ;  
  
  /**
   * Variable para saber si se habilitara 
   * el texto de fraccion
   * @author  
   * @since  15.10.2007
   */
  public static String vIndTextFraccion = "" ;
  
  /**
   * Variables Competencia
   * @author  
   * @since  12.11.2007
   */
  public static String vRucCompetencia = "" ;
  public static String vDescripcionCompetencia = "" ;
  
  /**
   * Variables para la cotizacion 
   * @author  
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
 * @author  
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
   * @author  
   * @since  29.09.2008
   */
  public static boolean vFlagF2Nuevo = false;

  
 /**
 * Variables modulo Pedidos Especiales
 * @author  
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
    
    //  indicador de si es en INGRESAR CANTIDAD NUEVA o modificar cantidad
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
   * @author     
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
  
  //  09.02.10
  public static String vNumEntAfectar = ""; //Entrega a Afectar
  
  //  16.02.10
  public static boolean vIndTransfRecepCiega = false; 
  
  //  25.03.2010
  public static boolean vIndModProdTransf = false; 
  
  public static String vBusquedaProdTransf = "";
  
  public static KeyEvent vKeyPress = null;
  
  public static String fechaVencLoteX=""; // , 14.04.2010
  public static String nroLoteX=""; // , 14.04.2010

  // , 14.07.2010
  public static String secRespStk="";        
  
  public VariablesInventario()
  {
  }
}
