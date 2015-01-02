package mifarma.ptoventa.ventas.reference;


import com.gs.mifarma.RespuestaTXBean;

import java.util.ArrayList;

public class VariablesVirtual {
    public VariablesVirtual() {
    }

    //public static RespuestaNavSatBean respuestaNavSatBean = new RespuestaNavSatBean();

    /**
     * Bean del nuevo proveedor (Brightstar).
     * @author Edgar Rios Navarro
     * @since 27.09.2007
     */
    public static RespuestaTXBean respuestaTXBean = new RespuestaTXBean();

    public static String vCodigoComercio = "";
    public static String vTipoTarjeta = "";
    public static String vMonto = "";
    public static String vNumTerminal = "";
    public static String vNumSerie = "";
    public static String vNumTrace = "";
    public static String vIPHost = "";
    public static String vPuertoHost = "";
    public static String vNumeroCelular = "";
    public static String vCodigoProv = "";
    public static String vCodigoAprobacion = "";
    public static String vNumeroTarjeta = "";
    public static String vNumeroPin = "";
    public static String vNumTraceOriginal = "";
    public static String vCodAprobacionOriginal = "";

    public static String vCodigoRespuesta = "";
    public static String vDescripcionRespuesta = "";

    public static ArrayList vArrayList_InfoProdVirtual = new ArrayList();
    public static int vTiempoCXNavsat = 60; //ponemos por defecto 60
    public static int vTiempoTXNavsat = 60; //ponemos por defecto 60

    /**
     * Campos necesarios por le nuevo proveedor (Brightstar).
     * @author Edgar Rios Navarro
     * @since 27.09.2007
     */
    public static String vFechaTX = "";
    public static String vHoraTX = "";
    public static String vDatosImprimir = "";

    /**
     * Array que guarda la cadena de la informacion requerida por el proveedor
     * @author dubilluz
     * @since  02.11.2007
     */
    public static ArrayList vArrayList_InfoProvRecarga = new ArrayList();

    /**
     * indicador de pedido con productos virtual
     * @author dveliz
     * @since  05.01.2009
     */

    public static boolean vConProductoVirtual = false;
}
