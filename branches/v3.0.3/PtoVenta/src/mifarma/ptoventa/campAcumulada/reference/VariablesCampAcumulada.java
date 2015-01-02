package mifarma.ptoventa.campAcumulada.reference;

import java.util.ArrayList;

public class VariablesCampAcumulada {
    public VariablesCampAcumulada() {
    }

    public static ArrayList vListaCampanias;


    // JCALLO doc validos de identificacion
    public static String vDocValidos = "";

    public static boolean vIndExisteCliente = false;

    public static String vIndConexion = "";
    public static boolean vSexoExists;
    public static boolean vFlagMandatory;

    public static String vCodProdFiltro = "";

    public static boolean vFlagFiltro = false;

    public static boolean vFlagDialogAbierto = false;

    public static ArrayList vDataCliente = new ArrayList();

    /**varibles auxiliar usado en registro de campañas de ventas acumuladas**/
    public static String vNroTarjeta = "";

    /**campos y/o datos de los cliente**/
    public static String vDniCliente = "";
    public static String vNomCliente = "";
    public static String vApePatCliente = "";
    public static String vApeMatCliente = "";
    public static String vSexo = "";
    public static String vFecNacimiento = "";
    public static String vDireccion = "";
    public static String vTelefono = "";
    public static String vCelular = "";
    public static String vEmail = "";

    public static String vIndEstado = "";
    /**fin campos y/o datos de los cliente**/

}
