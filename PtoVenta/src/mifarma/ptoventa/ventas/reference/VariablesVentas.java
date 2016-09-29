package mifarma.ptoventa.ventas.reference;

import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaTableModel;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : VariablesVentas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      14.02.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class VariablesVentas {
    public VariablesVentas() {
    }

    public static FarmaTableModel tableModelListaGlobalProductos;

    public static String vCod_Prod = "";
    public static String vDesc_Prod = "";
    public static String vNom_Lab = "";
    public static String vUnid_Vta = "";
    public static String vVal_Prec_Vta = "";
    public static String vPorc_Dcto_1 = "";

    /**
     * Para el descuento del Producto de Promocion
     * @author dubilluz
     * @since  02.07.2007
     */
    public static String vPorc_Dcto_2 = "";
    public static String vVal_Bono = "";
    public static String vDesc_Acc_Terap = "";
    public static String vStk_Prod = "";
    public static String vStk_Prod_Fecha_Actual = "";
    public static String vVal_Frac = "";
    public static String vVal_Prec_Lista = "";
    public static String vVal_Igv_Prod = "";
    public static String vPorc_Igv_Prod = "";
    public static String vEst_Ped_Vta_Cab = "";
    public static String vInd_Prod_Habil_Vta = "S";
    public static String vInd_Carga_Prod_Vta = "S";
    public static String vAhorroPack = ""; //JCHAVEZ 20102009
    public static String vInd_Origen_Prod_Prom = ""; //JCHAVEZ 20102009

    /**
     * Se indica el origen del producto: sustitutos, alternativos, etc.
     * @author Edgar Rios Navarro
     * @since 10.04.2008
     */
    public static String vIndOrigenProdVta = "";

    /**
     * Se indica si el ingreso es por tratamiento o venta sugerida.
     * @author Edgar Rios Navarro
     * @since 03.06.2008
     */
    public static String vIndTratamiento = "";
    public static double vTotalPrecVtaTra = 0;

    public static String vCant_Ingresada = "";
    public static String vCant_Ingresada_Temp = "";
    public static double vTotalPrecVtaProd = 0;

    public static ArrayList vArrayList_PedidoVenta = new ArrayList();
    public static ArrayList vArrayList_ResumenPedido = new ArrayList();
    public static ArrayList vArrayList_Medicos = new ArrayList();


    public static boolean vestRCM = false; // Estado del Pedido RCM RUDY 23.05.2013

    /**
     * Array para las Promociones
     * @author : dubilluz
     * @since  : 15.06.2007
     */
    public static ArrayList vArrayList_Prod_Promociones = new ArrayList();

    /**
     * Variables  para las Promociones
     * @author : dubilluz
     * @since  : 15.06.2007
     */
    /* ************************************************************************* */
    /*                   Atributos para las Promociones Pedidas                  */
    /* ************************************************************************* */
    public static String vCod_Promocion = "";
    public static String vDesc_Promocion = "";
    public static String vCant_Promocion = "";
    public static double vTotalPrecVtaProm = 0;
    /* ************************************************************************* */
    /*                   Atributos para los Productos de Promocion               */
    /* ************************************************************************* */
    //tendra un codigo promocion
    /*   Campos de la tabla Prod_Paquete
       cod_paquete , cod_prod , cantidad , porc_dcto
   */
    // public static String vCod_Prod = "";
    public static String vDesc_Prod_Paquete = "";
    // public static String vNom_Lab = "";
    //public static String vUnid_Vta = "";
    //public static String vVal_Prec_Vta = "";
    //public static String vStk_Prod = "";


    /*Atributos de vta_Promocion
  COD_GRUPO_CIA ,COD_PROM , DESC_CORTA_PROM ,DESC_LARGA_PROM
  COD_PAQUETE_PROM_1, COD_PAQUETE_PROM_2, USU_CREA_PROMOCION ,
  FEC_CREA_PROMOCION , USU_MOD_PROMOCION , FEC_MOD_PROMOCION , ESTADO  */

    public static String vCodigoBarra = "";
    //Resumen Pedido
    public static String vNum_Ped_Vta = "";
    public static String vVal_Bruto_Ped = "";
    public static String vVal_Neto_Ped = "";
    public static String vVal_Redondeo_Ped = "";
    public static String vVal_Igv_Ped = "";
    public static String vVal_Dcto_Ped = "";

    public static String vNom_Cli_Ped = "";
    public static String vDir_Cli_Ped = "";
    public static String vRuc_Cli_Ped = "";
    public static String vTip_Comp_Ped = "";
    public static String vCant_Items_Ped = "";
    public static String vNum_Ped_Diario = "";
    public static String vTip_Ped_Vta = "";
    public static String vCod_Cli_Local = "";

    public static String vIndDistrGratuita = "";

    //Detalle de Pedido
    public static String vSec_Ped_Vta_Det = "";
    public static String vPorc_Dcto_Total = "";
    public static String vEst_Ped_Vta_Det = "";
    public static String vVal_Total_Bono = "";
    public static String vSec_Usu_Local = "";

    public static String vCodProdOrigen_Comple = "";
    public static String vDescProdOrigen_Comple = "";
    public static String vCodProdComplementario = "";
    //public static String vPedidoDelivery = "";
    public static String vTipoPedido = "";
    public static String vTituloDelivery = "";
    public static String vNumOrdeCompra = "";
    public static String vPuntoLlegada = "";

    public static boolean vEsPedidoDelivery = false;
    public static boolean vIngresaCant_ResumenPed = false;
    public static boolean vEsPedidoInstitucional = false;
    public static boolean vEsPedidoConvenio = false;

    public static String vTipoBusqueda = "";
    public static String vCodMed = "";
    public static String vMatriculaApe = "";
    public static String vCodListaMed = "";
    public static String vMatriListaMed = "";
    public static String vNombreListaMed = "";
    public static boolean vSeleccionaMedico = false;

    public static boolean vIndPedConProdVirtual = false;
    public static boolean vIndProdControlStock = true;
    public static String vNumeroARecargar = "";
    public static String vMontoARecargar = "";
    public static String vMontoARecargar_Temp = "";
    public static String vIndProdVirtual = "";
    public static String vTipoProductoVirtual = "";
    public static String vVal_Prec_Lista_Tmp = "";
    public static int vMontoMinRecarga = 0;
    public static int vMontoMaxRecarga = 0;
    public static String vTipoProductoRecarga = "";

    /***Nuevo
     * @Autor: Luis Reque
     * @Fecha: 29-03-2007
     */
    public static String vVal_Prec_Pub = "";

    /**
     * AGREGADO TEMPORAL
     *
     * */
    public static String ACCION = "";
    public static String ACCION_CREAR = "CREAR";
    public static String ACCION_MODIFICAR = "MODIFICAR";
    public static String ACCION_ELIMINAR = "ELIMINAR";


    /**
     * Se guarda el codigo de promocion
     * @author Jorge Cortez
     * @since  13.06.2007
     */

    public static String vCodProm = "";
    public static String vCodProdFiltro = "";

    /**
     * Variable para ver la Accion
     * @author : dubilluz
     * @since  : 25.06.2007
     */
    public static String vCantidad = "";
    public static boolean accionModificar = false;


    /**
     * Se guarda las valores de un pedido en un array
     * @author Jorge Cortez
     * @since  18.06.2007
     */
    public static ArrayList vArrayList_Promociones = new ArrayList();
    public static int vcantMaxVta = 0;


    /**
     * Variables  para las Promociones
     * @author : dubilluz
     * @since  : 15.06.2007
     */
    public static String vCod_Prom = "";
    public static String vDesc_Prom = "";
    public static String vUnid_Prom = "";
    public static String vPrec_Prom = "";

    public static String vDes_Prom = "";
    public static String voculto3 = "";
    public static String voculto4 = "";
    public static String voculto5 = "";
    public static String voculto6 = "";
    public static String voculto7 = "";
    public static String voculto8 = "";
    public static String voculto9 = "";


    public static String vVentaTotal = "";
    public static int vCantInGre = 0;

    /**
     * Temporales para el dlgListaProductos
     * @author : dubilluz
     * @since  : 04.07.2007
     */
    public static ArrayList vArrayList_Promociones_temporal = new ArrayList();
    public static ArrayList vArrayList_Prod_Promociones_temporal = new ArrayList();

    public static String vCodLocalDestino = "";

    /**
     * Array para los datos de Producto de Tarjeta Virtual
     * @author : dubilluz
     * @since  : 29/08/2007
     */
    public static ArrayList vArrayList_Prod_Tarjeta_Virtual = new ArrayList();
    public static int cantidad_tarjeta_virtual = 0;
    public static boolean venta_producto_virtual = false;

    /**
     * Codigo de promocion del detalle del pedido
     * @author dubilluz
     * @since  28.02.2008
     */
    public static String vCodPromoDet = "";

    /**
     * Descripcion de la Promocion
     * @author JCORTEZ
     * @since  08.04.2008
     */
    public static String vDescProm = "";

    /**
     * Codigo producto origen para lista alternativos.
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    public static String vCodProdOrigen_Alter = "";

    /**
     * Variables para productos de regalo y cupones de sorteo
     * @author dubilluz
     * @since  09.04.2008
     */
    public static String vCodProd_Regalo = "";
    public static int vCantidad_Regalo = 0;
    public static double vMontoMinConsumoEncarte = 0.00;
    public static double vMontoProxMinConsumoEncarte = 0.00;
    public static boolean vIndVolverListaProductos = false;

    public static String vCodCampCupon = "";
    public static String vDescCupon = "";
    public static double vMontoPorCupon = 0.00;

    /**
     * Codigo de producto para el listado de productos complementarios
     * @author JCORTEZ
     * @since 09.04.2008
     */
    public static String vCodProdOrigen_Comple1 = "";
    public static boolean vEsProdOferta = false;

    /**
     * Variable para verificar si se direccionara a la ventana
     * de resumen de pedido
     * @author dubilluz
     * @since  11.04.2008
     */
    public static boolean vIndDireccionarResumenPed = false;
    public static boolean vIndF11 = false;

    /**
     * Variable para la busqueda de productos desde Resumen Pedido
     * @author dubilluz
     * @since  15.04.2008
     */
    public static KeyEvent vKeyPress = null;

    public static String vCodBarra = "";

    /**
     * Variable para el tipo de filtro desde resumen de pedido (Encarte,Cupon)
     * @author JCORTEZ
     * @since  07.04.2008
     */
    public static String vCodFiltro = "";
    public static String vDescFiltro = "";
    public static String vCodFiltro_temp = "";

    /**
     * Variable para busqueda del producto desde el resumen
     * @author JCORTEZ
     * @since  25.04.2008
     */
    public static String vCodProdBusq = "";


    /**
     * Lista de productos asignados falta Cero
     * @author daubilluz
     * @since  23.05.2008
     */

    public static ArrayList vListaProdFaltaCero = new ArrayList();

    /**
     * venta sugerida
     * @author JCORTEZ
     * @since  29.05.2008
     */
    public static String vVal_Frac_Sug = "";
    public static String vPrecListaSug = "";
    public static String vValFracLocal = "";
    public static String vVal_Prec_Vta_Sug = "";
    public static String vDesc_Unid_Vta_Sug = "";
    public static String vVal_Dsct_cast = "";
    public static String vPrec_Unit_Sug = "";

    public static String vPrecVtaCastLocal = "";

    public static String vCant_Vta = "";
    public static String vTotal_Vta = "";

    //--  Añadido DUBILLUZ
    public static String vCantxDia = "";
    public static String vCantxDias = "";

    public static String vIndModificacion = "";

    /**
     * Arreglo que contiene los cupones a usarse en el pedido.
     * @author Edgar Rios Navarro
     * @since 02.07.2008
     */
    public static ArrayList vArrayList_Cupones = new ArrayList();

    /**
     * @author JCORTEZ
     * @since 16.07.2008
     */
    public static String vIndZan = "";

    /**
     * @author JCORTEZ
     * @since 23.07.2008
     */
    public static String vMensCuponIngre = "";


    /**
     * @autoror JCALLO
     * @since 01.10.2008
     */
    public static String HabilitarF9 = "";


    /**
     * @Author Dveliz
     * @since  09.10.08
     *
     */
    // 19.02.2009 DUBILLUZ
    //public static ArrayList vActDctoDetPedVta;
    public static ArrayList vResumenActDctoDetPedVta;

    public static String vIndAplicaPrecNuevoCampanaCupon = "";
    public static boolean vVentanaOferta = false;
    public static ArrayList vListaProdAplicoPrecioDescuento = new ArrayList();

    public static boolean vVentanaListadoProductos = false;


    /**
     * arreglo de la campañas limitadas
     * **/
    public static ArrayList vArrayList_CampLimitUsadosMatriz = new ArrayList();

    public static boolean vIndObligaDatosCliente = false;

    /**
     * @author JCALLO
     * @since  11.03.2009
     * */
    public static List vList_CuponesNoUsados = new ArrayList();
    public static List vList_CuponesUsados = new ArrayList();
    public static List vListDctoAplicados = new ArrayList();

    public static String vIndVtaDebCostoProm =
        "S"; //por defecto se esya poniendo que permita vender por debajo del costo promedio

    //mfajardo 29/04/09 validar ingreso de productos virtuales
    public static boolean vProductoVirtual = false;


    //JCORTEZ 04.08.09
    //datos para la carga de cupones posibles a usar.
    public static String dniListCupon = "";
    public static String codCupon = "";
    public static ArrayList vArrayListCuponesClienteAux = new ArrayList();
    public static ArrayList vArrayListCuponesCliente = new ArrayList();
    public static int cPos = 0;

    // JMIRANDA 18/09/2009
    public static boolean bIndCodBarra = true;
    public static String vIndCodBarra = "";
    public static boolean vIndEsCodBarra = true;
    public static String vIndSolIdUsu = "";
    public static boolean bIndSolIdUsu = true;

    public static String vIndAplicaRedondeo = ""; //JCHAVEZ 29102009

    //ASOSA 03/02/2010
    //Variable para identificar que se hizo escape a la ventana de precios para el cliente
    public static String vIndPrecioCabeCliente = "N";

    //JQUISPE 03.05.2010
    //Variables para actualización si no cambia de fila en el listado.
    //
    //public static boolean vIndUpdateProd=false;
    //posicion anterior a la actual de lista de productos
    public static int vPosOld = 0;
    //nueva posicion del producto en la tabla de lista de productos
    public static int vPosNew = 0;

    //ASOSA, 01.07.2010
    public static String secRespStk = "";

    //JMIRANDA 02.10.2010
    public static String vCodProdDCI = "";

    //JMIRANDA 22.08.2011
    public static String vNumPedVta_new = "";
    public static String vMontoNeto_new = "";
    public static String vFechaPedVta_new = "";


    //JQUISPE 30.11.2011
    public static double vCantidadCupones = 0.0;

    public static String vDescProxProd_Regalo = "";
    public static String vCodProdProxRegalo = "";
    public static String vEstadoProdConvenio = "";
    public static ArrayList vListProdExcluyeAcumAhorro = new ArrayList();
    // Begin 16-AGO-13, TCT, Agrega campo para Precio Fijo
    public static String vPrecFijo = "";
    // Begin 16-AGO-13, TCT, Agrega campo para Precio Fijo

    public static String vCodSolicitudVtaNegativa = "";
    public static String vQFApruebaVTANEGATIVA = "";

    //Cesar Huanes 31-01-2014
    public static int vNumDocImpreso = 0;
    //CHUANES 22.04.2014 OBTENER EL NUMERO DE COMPROBANTE  PAGO
    public static String numCompPago = "";

    //kmoncada 30.06.2014
    public static String vMensajeBotiquinBTLVtaInstitucional = "";

    public static boolean existenValoresAdicionales = false; //ASOSA - 08/08/2014
    public static String vValorMultiplicacion = "1"; //ASOSA - 08/08/2014

    /**LTAVARA 25.08.2014
     * Proceso de comprobante electronico
     *
     * */
    public static ArrayList vArray_ListaComprobante = new ArrayList();
    public static String vCodTipProcPago = ""; //Obtiene si el comprobante fue generado electronicamente (1)

    //INI ASOSA - 09/10/2014
    public static String tipoLlamada = "";
    public static String tipoProducto = "";
    //FIN ASOSA - 09/10/2014
    
    //INI ASOSA - 06/01/2015 - RIMAC
    public static int vCantMesRimac = 0;
    public static String vDniRimac = "";
    //FIN ASOSA - 06/01/2015 - RIMAC
    
}
