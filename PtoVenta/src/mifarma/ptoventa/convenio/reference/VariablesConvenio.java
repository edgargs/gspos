package mifarma.ptoventa.convenio.reference;

import java.util.ArrayList;


public class VariablesConvenio {
    public VariablesConvenio() {
    }

    public static ArrayList vArrayList_ListaConvenio = new ArrayList();
    public static ArrayList vArrayList_DatosConvenio = new ArrayList();
    public static String vCodConvenio = "";
    public static String vCodCliente = "";
    public static String vNomConvenio = "";
    public static String vPorcDctoConv = "";
    public static String vPorcCoPago = "";
    public static String vTextoCliente = "";
    public static String vCodTrab = "";
    public static String vNomCliente = "";

    public static String vVal_Prec_Vta_Conv = "";

    public static String vVal_Prec_Vta_Local = "";

    public static String vValCoPago = "";

    public static double vValCredDis = 0;

    public static String vCredito = "";

    public static String vCreditoUtil = "";

    /**
     * Variables para el listado y seleccion de clientes
     * dependientes de otros respecto a un convenio
     * @author dubilluz
     * @since  30.01.2008
     */
    public static String vCodClienteBusqueda = "";
    public static String vDatosClienteBusqueda = "";
    public static String vIndConvCliDependiente = "";
    public static String vCodClienteDependiente = "";
    public static String vCodTrabDependiente = "";

    /**
     * varibale para el nombre del dependiente
     * @author JCORTEZ
     * @since  13.03.2008
     */
    public static String vNomTrabDependiente = "";

    /**
     * Variable para el tipo de filtro por dni
     * @author JCORTEZ
     * @since  26.02.2008
     */
    public static String vNumDocIdent = "";

    public static String vTipoFiltro = "";

    /**
     * Indicador del convenio solo se vende por credito.
     * @author Edgar Rios Navarro
     * @since 24.03.2008
     */
    public static String vIndSoloCredito = "";
}
