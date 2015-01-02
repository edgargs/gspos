package mifarma.ptoventa.recetario.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VariablesRecetario {
    public static String vCantInsumosCodSelec = "";
    public static int vPosOld = 0;
    public static int vPosNew = 0;

    public static ArrayList<ArrayList<Object>> vArrayInsumosSeleccionados = null;

    public static Map<String, String> vMapDatosPacienteMedico = null;

    public static ArrayList<Object> vArrayEsteril = null;
    //LLEIVA: 21/Mayo/2013 - Variables para enlazar producto virtual Preparado magistral
    public static String strCodigoRecetario = "";
    public static String strPrecioTotal = "";
    public static String strCant_Recetario = "";

    //LLEIVA: 23/Mayo/2013 - Variables para enlazar Recetario magistral con guia
    //public static ArrayList vArrayListaGuiasRM = null;

    public static String vValIgv = "";
    public static String vValVenta = "";
    public static String vValVentaSinRed = "";

    //LLEIVA: 13/Junio/2013 - Estados del recetario mag. en los que se podra realizar la anulacion de pedido
    public static HashMap<String, String> vEstadosValidosAnulacion = new HashMap<String, String>() {
        {
            put("A", "Pendiente");
            put("C", "Cobrado");
        }
    };

    //LLEIVA: 28/Junio/2013 - Indicador de que acción se aplico al Recetario Magistral
    //                        (C=Creacion desde Local, A=Pago de RM creado en central, N=Anulacion de RM)
    public static String strAccion = "";

}
