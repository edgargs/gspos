package mifarma.ptoventa.administracion.fondoSencillo.reference;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VariablesFondoSencillo {
    private static final Log log = LogFactory.getLog(VariablesFondoSencillo.class);
    
    public VariablesFondoSencillo() {
    }
    
    public static ArrayList vArrayListaCajeros = new ArrayList();
    
    public static String vSecUsuCajero = "";
    public static String vNombresCajero = "";
    public static String vApePaterno = "";
    public static String vApeMaterno = "";
    public static String vLoginUsuCaje = "";
    public static String vMonto = "";
    public static String vMontoAsignado = ""; //monto para mensaje    
    
    public static String vSecFondoSen = ""; 
    
    public static String vIndTieneFondoSencillo = ""; // 
    
    public static String vIndTieneMontoDevolver = ""; // 
    public static String vMensajeDevolver = "";
    
    public static String vSecAdmLocal = "";
    
    public static String vSecMovCajaCierre = "";
    
    
    /* ******************************/
    public static String vHistoEstado = "";
    public static String vHistoTipo = "";
    public static String vHisSecFondoSen = "";
    public static String vHisSecUsuOrigen = "";
    public static String vHisSecUsuDestino= "";    
    
    public static String vFiltroTipo = "";
    
    public static String vFiltroSecUsuOrigen = "";
    public static String vFiltroSecUsuDestino = "";
    
    public static String vSecUsuCajeroCierre = "";
       
    /* *************************** */
    public static String vCajNombre = "";
    public static String vCajSecUsuCajero = "";
    //public static String vCajSecUsuCajero = "";
    
    public static boolean vImprimeVoucherFondoSencillo = false;
}
