package mifarma.ptoventa.inventariociclico.reference;

import java.util.ArrayList;

import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;


public class VariablesInvCiclico {
    public VariablesInvCiclico() {
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

    //Variables Paulo

}
