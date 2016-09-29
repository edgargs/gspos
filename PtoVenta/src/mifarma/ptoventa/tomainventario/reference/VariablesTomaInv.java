package mifarma.ptoventa.tomainventario.reference;

import java.util.ArrayList;

public class VariablesTomaInv {
    public VariablesTomaInv() {
    }

    public static ArrayList listaLabsSeleccion = new ArrayList();

    /** Almacena el Secuencial de Toma de Inventario */
    public static String vSecToma = "";

    /** Almacena el Codigo de Lab de Toma de Inventario */
    public static String vCodLab = "";

    /** Almacena el Codigo de Producto */
    public static String vCodProd = "";

    /** Almacena la desc. de Producto */
    public static String vDesProd = "";

    /** Almacena la desc. de la unidad de venta de Producto */
    public static String vUnidVta = "";

    /** Almacena la cantidad Enterta */
    public static String vCantEntera = "";

    /** Almacena la cantidad Fraccion */
    public static String vCantFraccion = "";

    /** Almacena la cantidad Fraccion */
    public static String vUnidPresentacion = "";


    /** Almacena el Codigo de Lab de Toma de Inventario */
    public static String vNomLab = "";

    /**Alamacena el valos del laboratorio si es farma o no
     *22/03/2006   Paulo   Variable para guardra el valor del tipo de laboratorio */
    public static String vLaboratorio = "";


    /**
     * Almacena el Tipo de operacion que se realiza con los formularios: -Toma
     * de Inventario -Consulta de Historico
     */
    public static String vTipOp = ConstantsTomaInv.TIPO_OPERACION_TOMA_INV;

    /** Almacena la ubicacion actual en la tabla de toma de inventario */
    public static int vRegActual = 0;


    /** Almacena la descripcion del tipo de Toma de Inventario */
    public static String vDescTipoToma = "";

    /** Almacena la Fecha de Inicio de Toma de Inventario */
    public static String vFecIniToma = "";

    /** Almacena la Fecha de Fin de Toma de Inventario */
    public static String vFecFinToma = "";

    /** Almacena la descripcion del estado de Toma de Inventario */
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

    /**
     * Para el Usuario Modificacion de Toma
     * @author : dubilluz
     * @since  : 11.07.2007
     */
    public static String vIdUsu_Toma = "";

    /**
     * Para colocar el filtro de estado de Laboratorios
     * @author dubilluz
     * @since  08.01.2008
     */
    public static String vCodFiltroEstado = "";

    /**
     * Variables declaradas para ingreso de codBarra en Inventario Tradicional
     * @author dubilluz
     * @since  21.12.2009
     */
    public static String vCodBarraIngresado = "";
    public static String vCodProducto = "";
    public static String vDescripcion = "";
    public static String vUnidadPresentacion = "";
    public static String vFraccionLocal = "";
    public static String vUnidadVenta = "";
    public static String vLaboratorio_Barra = "";
    public static String vCantEnteraIngresada = "";
    public static String vCantFracIngresada = "";
    public static String vIndLoteMayorista="N";//LTAVARA 15/01/2016 PROYECTO M 
    public static String vLoteIngresado = "";//LTAVARA 15/01/2016 PROYECTO M 
    public static String vFechaLoteIngresado = "";//LTAVARA 15/01/2016 PROYECTO M 
    public static Integer vValFracMasterPack=0;//LTAVARA 01/02/2016 PROYECTO M 
    public static String vIndIngresaProd = "N";
    public static int vSecAuxConteo = 0;
    public static int vContadorFila = 0;
    public static String vIndProcesoToma = "N";

    public static String vCodBarraTemp = "";
    public static String vCodProductoTemp = "";
    public static String vDescripcionTemp = "";
    public static String vUnidadPresentacionTemp = "";
    public static String vFraccionLocalTemp = "";
    public static String vCantEnteraTemp = "";
    public static String vCantFracTemp = "";
    public static String vSecAuxConteoTemp = "";
    public static String vLoteIngresadoTemp = "";//LTAVARA 15/01/2016 PROYECTO M 
    public static String vFechaLoteIngresadoTemp = "";//LTAVARA 15/01/2016 PROYECTO M 

    public static String vNomLabConteo = "";

    public static String vIndActualizaProd = "";
    public static String vIndEliminaProd = "";
}
