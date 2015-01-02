package mifarma.ptoventa.administracion.usuarios.reference;

import java.util.ArrayList;

import mifarma.common.FarmaVariables;


public class VariablesUsuarios {
    public static ArrayList listaRoles = new ArrayList();

    public static String vSecUsuLocal = "";

    public static String vSecTrab = "";

    public static String vLoginUsu = "";

    public static String vClaveUsu = "";

    public static String vApePat = "";

    public static String vApeMat = "";

    public static String vNombres = "";

    public static String vDireccion = "";

    public static String vTelefono = "";

    public static String vFecNac = "";

    public static String vCodGrupoCia = "";

    public static String vCodTrab = "";

    public static String vDNI = "";

    public static String vCoLocal = "";

    public static int vColBuscarTrab;

    public VariablesUsuarios() {
    }

    public static boolean vInactivo = false;

    public static void limpiar() {
        vSecUsuLocal = "";
        vSecTrab = "";
        vLoginUsu = "";
        vClaveUsu = "";
        vApePat = "";
        vApeMat = "";
        vNombres = "";
        vDireccion = "";
        vTelefono = "";
        vFecNac = "";
        vDNI = "";
        vCodGrupoCia = FarmaVariables.vCodGrupoCia;
        vCoLocal = FarmaVariables.vCodLocal;

    }

    /** Se valida que pueda cambiar el codigo de trabador
     * @author:  JCortez
     * @since:   03.07.07
     */

    public static int vDiaMax = 0;
    public static boolean vActiCod = false;
    public static String vLogin = "";
    public static boolean vfocus = false;
    public static boolean vF3 = false;


    /**
     * Variable para guardar el codigo de trabajador RR.HH
     * @author dubilluz
     * @since  27.11.2007
     */
    public static String vCodTrab_RRHH = "";
    public static String vModificarUsuario = "";

    public static boolean vAccionCrear = false;


    /**
     * Variable para guardar el nro Carné Seleccionado
     * @author asolis
     * @since  17.02.2009
     */

    public static String vCarne = "";


    /**
     * Variables para guardar el nro Carné ingresado
     * @author asolis
     * @since  17.02.2009
     */

    public static String vNroCarne = "";
    public static String vFechaExpedicion = "";
    public static String vFechaVencimiento = "";
    public static String vModificarCarne = "N";

}
