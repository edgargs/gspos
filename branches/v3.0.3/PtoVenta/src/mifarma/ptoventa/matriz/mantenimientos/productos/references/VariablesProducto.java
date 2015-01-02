package mifarma.ptoventa.matriz.mantenimientos.productos.references;

import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaTableModel;


public class VariablesProducto {


    public static String vCodProducto = "";
    public static String vDescProducto = "";
    public static String vUnidVenta = "";
    public static String vCodZona = "";
    public static String vDescZona = "";

    public static String vOpcion = "";
    public static boolean vAbreNueva = true;

    public static int vCantidadStock = 0;

    /*Variables para Filtro*/ /*
  public static String vCampoFiltro = "" ;
  public static String vFiltro = "" ;
  public static String vNombreInHashtable ="";
  public static String vNombreInHashtableVal ="";*/

    public static String vNombreInHashtable = "";

    public static String vOpcion_Prod_Nuevos = "ProductosNuevos";
    public static String vOpcion_Prod_Adicionales = "ProductosAdicionales";

    public static String[] IND_DESC_FILTRO = { "Zona 1", "Zona 2", "Zona 3", "Zona 4" };
    public static String[] IND_VALOR_FILTRO = { "001", "002", "003", "004" };


    /**
     * Variables del modulo de distribucion
     * @author JORGE CORTEZ
     * @since  08.11.2007
     */

    public static String vNumPedidoDist = "";
    public static String vNumPedidoDistGenerado = "";
    public static String vNumPedidoDistGenerado_aux = "";
    public static String vFechaPedido = "";
    public static String vEstadoPedido = "";

    public static String cCodLab = "";
    public static String cDesLab = "";
    public static String cFiltroDescrip = "";
    public static String cFiltroListado = "";

    public static String cCodProd = "";
    public static String cDescProd = "";
    public static String cUnidPres = "";
    public static int vCantStock = 0;

    public static String vIndNuevo = "";
    public static boolean estadoAcccion = false;


    public static String[] mesesUnidVend = { "Mes0", "Mes1", "Mes2", "Mes3" };

    /**
     * Columnas de los locales donde esta el producto
     * @author JCORTEZ
     * @since  12.11.2007
     */
    public static FarmaColumnData columnsListaProdLocal[] =
    { new FarmaColumnData("Codigo", 50, JLabel.CENTER), new FarmaColumnData("Local", 205, JLabel.LEFT),
      new FarmaColumnData("Stock", 70, JLabel.RIGHT), new FarmaColumnData("Stock Transito", 83, JLabel.RIGHT),
        /*
    new FarmaColumnData(VariablesProducto.mesesUnidVend[0], 60, JLabel.RIGHT),
    new FarmaColumnData(VariablesProducto.mesesUnidVend[1], 60, JLabel.RIGHT),
    new FarmaColumnData(VariablesProducto.mesesUnidVend[2], 60, JLabel.RIGHT),*/

        //Modificado por DVELIZ 19.09.08
        new FarmaColumnData("Ind.Rep.", 83, JLabel.RIGHT),
        new FarmaColumnData(VariablesProducto.mesesUnidVend[3], 60, JLabel.RIGHT),
        new FarmaColumnData(VariablesProducto.mesesUnidVend[2], 60, JLabel.RIGHT),
        new FarmaColumnData(VariablesProducto.mesesUnidVend[1], 60, JLabel.RIGHT),
        new FarmaColumnData(VariablesProducto.mesesUnidVend[0], 60, JLabel.RIGHT),
        new FarmaColumnData("Reponer", 70, JLabel.RIGHT) };

    //Modificado por DVELIZ 19.09.08
    public static Object[] defaultValuesListaProdLocal = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
    //Modificado por DVELIZ 19.09.08
    public static final Boolean[] editableListaLocales_Prod =
    { new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false),
      new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(true) };
    public static final Object[] defaultValuesListaSaldos = { " ", " ", " " };


    /**
     * Listado de Unidades Vendidas
     */
    public static FarmaColumnData columnsListaUnidadesVendidas[] =
    { new FarmaColumnData("Zona", 70, JLabel.LEFT), new FarmaColumnData(VariablesProducto.mesesUnidVend[0], 70,
                                                                        JLabel.RIGHT),
      new FarmaColumnData(VariablesProducto.mesesUnidVend[1], 70, JLabel.RIGHT),
      new FarmaColumnData(VariablesProducto.mesesUnidVend[2], 70, JLabel.RIGHT),
      new FarmaColumnData(VariablesProducto.mesesUnidVend[3], 70, JLabel.RIGHT) };

    public static Object[] defaultValuesListaUnidadesVendidas = { " ", " ", " ", " " };

    /**
     * Variable para la opcion ordenar de listad de productos y locales
     * @author JCORTEZ
     * @since  13.12.2007
     */
    public static String vColumna = "";
    public static String vOrden = "";
    public static String Tipo_Orden = "";

    /**
     * Variable para el tipo de listado
     * @author JCORTEZ
     * @since  16.01.2008
     */
    public static String vTipoListado = "";
    public static String vTipoValidarFiltro = "";


    /**
     * Variable para el tipo de listado
     * @author JCORTEZ
     * @since  11.09.2008
     */
    public static boolean vTipoParametros = false;


    /**
     * Variable para el tipo de listado de locales(Lima/Provincia)
     * @author JCORTEZ
     * @since  19.09.2008
     */
    public static String Tipo_Local = "";

    /**
     * Variables creadas para Pedido Adicional (Matriz)
     * @author DVELIZ
     * @since  22.09.08
     */
    public static String filtroListaLocalesPVM = "";
    public static String vCodLocal_PedAdic = "";
    public static String vCodProd_PedAdic = "";
    public static String vNomProd_PedAdic = "";
    public static String vUnidMed_PedAdic = "";
    public static int vPos_PedAdic = 0;
    public static String vValFrac_PedAdic = "";
    public static String vNomLab_PedAdic = "";
    public static String vStkFisico_PedAdic = "";
    public static String vCantSug_PedAdic = "";
    public static String vFechaHora_PedAdic = "";
    public static String vCantAutorizado = "";


    public static String vCodLocal = "";
    public static String vNumPedEspecial = "";
    public static String vEstPedEpsc = "";


    /************************PEDIDO ESPECIAL MATRIZ****************************************/

    /**
     * Variables
     * @AUTHOR JCORTEZ
     * @SINCE 04.01.2010
     * */

    public static boolean vFModificar = false;
    public static boolean vFNuevo = false;
    public static boolean vEsModificado = false;
    public static boolean vIrResumen = false;

    public static boolean flag_F3 = false;

    public static FarmaTableModel tableModelEspecialMatriz =
        new FarmaTableModel(ConstantsProducto.columnsListaProductosEspeciales,
                            ConstantsProducto.defaultValuesListaProductosEspeciales, 0);

    public static int vPosi = 0;
    public static String vCantIng = "";
    public static String vCodProd_esp = "";
    public static String vNomProd_esp = "";
    public static String vUnidMed_esp = "";
    public static String vNomLab_esp = "";
    public static String vStkFisico_esp = "";
    public static String vValFrac_esp = "";
    public static String vPrecVta_esp = "";
    public static String vEstado = "";


    //indicador de si es en INGRESAR CANTIDAD NUEVA o modificar cantidad
    public static boolean flag_modificarCantidad = false;

    public static ArrayList vArrayProductosEspeciales = new ArrayList(); //
    //array producto para el diaolgo resumen de pedido especial
    public static ArrayList vArrayListaProdsEsp = new ArrayList(); //
    public static ArrayList vArrayProductosDet = new ArrayList(); //

    public static String vNumPedidoEspecialMatriz = "";
    public static String vFecEmi_esp = "";
    public static String vEstCab_esp = "";

    public static final String SEC_PED_ESP = "046";
    public static String vTipoMaestro = "";
    public static String vCodLocalDestino = "";
    public static String vDescLocalDestino = "";

    public static String vCodFiltro = "";
    public static boolean vIndF11 = false;

    public static KeyEvent vKeyPress = null;


}
