package mifarma.electronico.epos.reference;


public class EposVariables {
    //LTAVARA 20/08/2014

    /**Variables para conexion con el EPOS**/
    public static String vIpSocketServidor = "";
    public static Integer vPuertoEPOS = 0;
    public static String vModo = "";
    public static String vRucEmisor = "";
    //public static  String vIdPos="";

    public static Boolean vFlagComprobanteE = false;
    //public static Boolean vIndLocalElectronico=false;

    public EposVariables() {
    }

    public static void limpiaVariables() {
        vIpSocketServidor = "";
        vPuertoEPOS = 0;
        vModo = "";
        vRucEmisor = "";
        //vIdPos="";
        vFlagComprobanteE = false;
    }

    public static void activaElectronico() {
        vFlagComprobanteE = true;
    }

    public static void desactivaElectronico() {
        vFlagComprobanteE = false;
    }


}
