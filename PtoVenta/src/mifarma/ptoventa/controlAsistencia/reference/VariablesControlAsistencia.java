package mifarma.ptoventa.controlAsistencia.reference;

public class VariablesControlAsistencia {
    
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */    
    public VariablesControlAsistencia() {
        super();
    }
    
    public static String accion="";
    public static String vTipo_Accion="";
    public static String vEstado="";
    public static String vHoraInicio="";
    public static String vHoraFin="";
    public static String vIdTurno="";
    

    public static String vCodFiltro = "";
    public static String vDescFiltro = "";

    public static String vTipoFiltro = "";

    public static String vTituloListaMaestros = "";
    public static String vTipoMaestro = "";

    public static String vCodMaestro = "";
    public static String vDescMaestro = "";

    public static String vInd_Filtro = "";
    public static String vDesc_Cat_Filtro = "";

    public static String vCodCia = "";
    public static String vCodTrab = "";
    public static String vCodHor = "";
    public static String vSugTipo = "";
    public static String vIndicador="";//chuanes 11.03.2015
    public static boolean vflag=false;//chuanes 12.03.2015

    /**
     * Mant Temperatura
     * @AUTHOR JCORTEZ
     * @SINCE 11.02.09
     * */
    public static int vTipoAccionTemp;

    /**
     * Mant Carné Sanidad
     * @AUTHOR ASOLIS
     * @SINCE 25.02.09
     * */
    public static String vFechaVencCarne = "";
    public static String vSecUsu = "";
    public static String vExisteCarne = "N";
    public static String vMensajeTiempoVencimiento = "";
    public static String vEnviaAlerta = "N";

}
