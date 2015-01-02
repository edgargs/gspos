package mifarma.ptoventa.inventariodiario.reference;

import java.util.ArrayList;

import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;


public class VariablesInvDiario {
    public VariablesInvDiario() {
    }

    public static ArrayList listaLabsSeleccion = new ArrayList();

    /**Almacena el Secuencial de Toma de Inventario
     */
    public static String vSecToma = "";

    /**Almacena el Codigo de Lab de Toma de Inventario
     */
    public static String vCodLab = "";

    /**Almacena el Codigo de Producto
     */
    public static String vCodProd = "";

    /**Almacena las descripcion del laboratorio
     */
    public static String vDesLaboratorio = "";

    /**Almacena la desc. de Producto
     */
    public static String vDesProd = "";

    /**Almacena la desc. de la unidad de venta de Producto
     */
    public static String vUnidVta = "";

    /**Almacena la cantidad Enterta
     */
    public static String vCantEntera = "";

    /**Almacena la cantidad Fraccion
     */
    public static String vCantFraccion = "";

    /**Almacena la cantidad Fraccion
     */
    public static String vUnidPresentacion = "";


    /**Almacena el Codigo de Lab de Toma de Inventario
     */
    public static String vNomLab = "";

    /**Alamacena el valos del laboratorio si es farma o no
     * 22/03/2006   Paulo   Variable para guardra el valor del tipo de laboratorio
     */
    public static String vLaboratorio = "";


    /**
     * Almacena el Tipo de operacion que se realiza con los formularios: -Toma
     * de Inventario -Consulta de Historico
     */
    public static String vTipOp = ConstantsTomaInv.TIPO_OPERACION_TOMA_INV;

    /**Almacena la ubicacion actual en la tabla de toma de inventario
     */
    public static int vRegActual = 0;


    /**Almacena la descripcion del tipo de Toma de Inventario
     */
    public static String vDescTipoToma = "";

    /**Almacena la Fecha de Inicio de Toma de Inventario
     */
    public static String vFecIniToma = "";

    /**Almacena la Fecha de Fin de Toma de Inventario
     */
    public static String vFecFinToma = "";

    /**Almacena la descripcion del estado de Toma de Inventario
     */
    public static String vDescEstadoToma = "";

    public static String vFraccion = "";

    public static ArrayList vListaCodLab = new ArrayList();

    public static ArrayList vListaProductos = new ArrayList();

    public static ArrayList vObtieneTotales = new ArrayList();

    public static ArrayList vObtieneInformacionValorizada = new ArrayList();

    public static String vTotalItems = "";

    public static String vTotalTomados = "";

    public static String vFaltante = "";

    public static String vSobrante = "";

    public static String vIndProceso = "";

    public static String vValFrac = "";

    public static String vCodigoAux = "";
    public static String vNomProdAux = "";
    public static String vUnidPresentaAux = "";
    public static String vValFracAux = "";

    public static long vMinutosTranscurridos = 0;
    public static long vFechaAux;
    public static long vFechaInicial;
    public static boolean vFlagTimer = false;

    /**Almacena el Codigo de Producto cruze
     */
    public static String vCodProdCruce = "";

    /**Almacena el laboratotio del Producto cruze
     */
    public static String vCodLabProdCruce = "";

    /**Almacena el falg que indica el cruce exitoso
     */
    public static boolean vAceptarCruce = false;

    public static String vStockProd = "";

    public static String vStockProdCruce = "";

    public static double vStockProdD = 0;

    public static double vStockProdCruceD = 0;

    //Variables Paulo

    //Listado de Productos en Diferencias
    //Este listado se usara para revertir y/o boletear.
    //daubilluz 11.06.2009
    public static ArrayList vListadoProductosDiferenciaSeleccionadas = new ArrayList();

    public static ArrayList vListadoProductosDiferenciaOriginal = new ArrayList();

    public static String vCodMotivoRevertir = "";
    public static String vDNI = "";
    public static String vCodigoTrab = "";
    public static String vCodRRHH = "";
    public static String vNombreCompleto = "";
    public static String vMontoIngresado = "";

    public static ArrayList vListaTrabSeleccionados = new ArrayList();
    public static ArrayList vListaTrabParaDescuento = new ArrayList();

    public static String vSecPedidoTemporal = "";

    //JCORTEZ 15.06.2009
    public static String vCodMotivoAjuste = "";
    public static String vDescMotivoAjuste = "";
    public static String vAccion = "";
    public static String vCodAjuste = "";
    public static double dTotalProds = 0;
    public static double dTotalDescTrab = 0;


    public static String vNumPedido = "";
    public static String vTotalPedido = "";

    //JMIRANDA 03/08/09
    public static String vIndSegConteo = "";

    //JMIRANDA 25.09.09
    public static boolean vIndSeleccionado = true;

}
