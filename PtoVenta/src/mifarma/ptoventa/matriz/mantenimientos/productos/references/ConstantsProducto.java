package mifarma.ptoventa.matriz.mantenimientos.productos.references;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsPedido.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.02.2007   Creación<br>
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class ConstantsProducto {

    public static final int columnEditable_Cantidad_ProdNvo = 3;
    public static final int columnEditable_Cantidad_ProdAdic = 2;

    public static final FarmaColumnData[] columnsListaProductosNvos =
    { new FarmaColumnData("Codigo", 70, JLabel.CENTER), new FarmaColumnData("Descripción", 200, JLabel.LEFT),
      new FarmaColumnData("Unidad P.", 100, JLabel.LEFT), new FarmaColumnData("Stock Libre", 70, JLabel.RIGHT),
      new FarmaColumnData("Stock Trasl.", 70, JLabel.RIGHT), new FarmaColumnData("", 0, JLabel.LEFT) //Laboratorio
        } ;

    public static final Object[] defaultValuesListaProductosNvos = { " ", " ", " ", " ", " ", " " };


    public static final FarmaColumnData[] columnsListaLocales_ProdNvos =
    { new FarmaColumnData("Código", 70, JLabel.CENTER), new FarmaColumnData("Local", 170, JLabel.LEFT),
      new FarmaColumnData("Cantidad", 70, JLabel.RIGHT), new FarmaColumnData("Reponer", 70, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaLocales_ProdNvos = { " ", " ", " ", " " };

    public static final Boolean[] editableListaLocales_ProdNvos =
    { new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(true) };

    public static final FarmaColumnData[] columnsListaProductosAdic = { new

        FarmaColumnData("Codigo", 70, JLabel.CENTER), new FarmaColumnData("Descripción", 200, JLabel.LEFT),
        new FarmaColumnData("Unidad P.", 100, JLabel.LEFT), new FarmaColumnData("Stock Almacén", 70, JLabel.RIGHT),
        new FarmaColumnData("Stock Local", 70, JLabel.RIGHT), new FarmaColumnData("", 0, JLabel.LEFT) //Laboratorio
        } ;

    public static final Object[] defaultValuesListaProductosAdic = { " ", " ", " ", " ", " ", " ", };

    public static final FarmaColumnData[] columnsListaLocales_ProdAdic =
    { new FarmaColumnData("Código", 70, JLabel.CENTER), new FarmaColumnData("Local", 120, JLabel.LEFT),
      new FarmaColumnData("Pedir", 50, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaLocales_ProdAdic = { " ", " ", " " };

    public static final Boolean[] editableListaLocales_ProdAdic =
    { new Boolean(false), new Boolean(false), new Boolean(true) };

    /**
     * Listado de Pedidos de Distribucion
     * @author JORGE CORTEZ
     * @since  08.11.2007
     */
    public static final FarmaColumnData[] columnsListaPedidosDistribucion =
    { new FarmaColumnData("Num.Pedido", 90, JLabel.CENTER), new FarmaColumnData("Fecha", 140, JLabel.LEFT),
      new FarmaColumnData("Cant. Items", 70, JLabel.CENTER), new FarmaColumnData("Cant. Prod ", 70, JLabel.CENTER),
      new FarmaColumnData("Estado", 115, JLabel.LEFT), new FarmaColumnData("Usuario", 90, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT), }; //estado

    public static final Object[] defaultValuesListaPedidosDistribucion = { " ", " ", " ", " ", " ", " ", " " };

    /**
     * Listado de detalle de Pedido de Distribucion-->Productos
     * @author JORGE CORTEZ
     * @since  08.11.2007
     */
    public static final FarmaColumnData[] columnsListaDetalleProductos =
    { new FarmaColumnData("Cod.Prod", 80, JLabel.CENTER), new FarmaColumnData("Descripcion", 285, JLabel.LEFT),
      new FarmaColumnData("Unidad P.", 120, JLabel.LEFT) };

    public static final Object[] defaultValuesListaDetalleProductos = { " ", " ", " " };

    /**
     * Listado de detalle de Pedido de Distribucion-->Locales
     * @author JORGE CORTEZ
     * @since  08.11.2007
     */
    public static final FarmaColumnData[] columnsListaDetalleLocales =
    { new FarmaColumnData("Cod.Local", 80, JLabel.CENTER), new FarmaColumnData("Descripcion", 170, JLabel.LEFT),
      new FarmaColumnData("Stock local", 70, JLabel.CENTER), new FarmaColumnData("Cantidad ", 70, JLabel.CENTER),
      new FarmaColumnData("Estado", 98, JLabel.CENTER), new FarmaColumnData("", 0, JLabel.CENTER) };

    public static final Object[] defaultValuesListaDetalleLocales = { " ", " ", " ", " ", " " };

    /**
     * Listado de Productos para el pedido de distribucion
     * @author JCORTEZ
     * @since 08.11.2007
     */
    public static FarmaColumnData columnsListaProductos[] = { new FarmaColumnData("Codigo", 50, JLabel.LEFT),
        //new FarmaColumnData("ABC", 30, JLabel.CENTER),
        new FarmaColumnData("Est.", 0, JLabel.CENTER), new FarmaColumnData("Producto", 165, JLabel.LEFT),
        new FarmaColumnData("Unidad", 100, JLabel.LEFT), new FarmaColumnData("Laboratorio", 120, JLabel.LEFT),
        new FarmaColumnData("Stk.Almc", 55, JLabel.RIGHT), //Stk.libre
        new FarmaColumnData("Stk.Bloq", 60, JLabel.RIGHT),
        //Agregado por DVELIZ 19.09.08
        new FarmaColumnData("Stk.Loc", 60, JLabel.RIGHT), new

        FarmaColumnData("Locales", 55, JLabel.RIGHT), new FarmaColumnData("Stk.Trans", 60, JLabel.RIGHT),
        new FarmaColumnData("Estado", 78, JLabel.LEFT), new FarmaColumnData("ZAN", 35, JLabel.LEFT),
        new FarmaColumnData("TIPO", 90, JLabel.LEFT) };
    //new FarmaColumnData("Cant.Loca", 60, JLabel.LEFT)};//descripcion del laboratorio
    //new FarmaColumnData(VariablesCompras.mesesUnidVend[0], 60, JLabel.RIGHT),
    //new FarmaColumnData(VariablesCompras.mesesUnidVend[1], 60, JLabel.RIGHT),
    //new FarmaColumnData(VariablesCompras.mesesUnidVend[2], 60, JLabel.RIGHT),
    //new FarmaColumnData("PrecProm", 0, JLabel.RIGHT)};

    public static Object[] defaultValuesListaProductos =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final String vFiltroListaProducto = "%";

    /**
     * Listado de laboratorios
     * @author JCORTEZ
     * @since 08.11.2007
     */
    public static final FarmaColumnData[] columnsListaLaboratorios =
    { new FarmaColumnData("Código", 70, JLabel.CENTER), new FarmaColumnData("Descripcion", 280, JLabel.LEFT) };

    public static final Object[] defaultValuesListaLaboratorios = { " ", " " };

    //filtro para el listado
    public static final String LABORATORIO = "LABORATORIO";
    public static final String PRODUCTO = "PRODUCTO";
    public static final String LOCAL = "LOCALES";

    //columna editable

    //public static final int columnEditable_Cantidad_Distribu = 7;
    //Modificador por DVELIZ 19.09.08
    public static final int columnEditable_Cantidad_Distribu = 9;

    //secuencial del pedido de distribucion
    public static final String SECNUMERA = "043";
    public static final String AUX_SECNUMERA = "044";
    //estados del pedido de distribucion
    public static final String EST_ACEPTADO = "A";
    public static final String EST_ANULADO = "N";
    public static final String EST_PENDIENTE = "P";
    //filtro para productos nuevos

    public static final String IND_NUEVO = "S";
    public static final String IND_NO_NUEVO = "N";


    /**
     * Listado de OC Pendientes.
     * @author JORGE CORTEZ
     * @since  15.11.2007
     */
    public static final FarmaColumnData columnsListaOCPendientes[] =
    { new FarmaColumnData("Nro", 80, JLabel.LEFT), new FarmaColumnData("Pos", 30, JLabel.LEFT),
      new FarmaColumnData("Fecha", 90, JLabel.LEFT), new FarmaColumnData("Cant", 75, JLabel.RIGHT),
      new FarmaColumnData("Costo", 85, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaOCPendientes = { " ", " ", " ", " ", " " };

    /**
     * Listado de OC pendientes del producto
     * @author JORGE CORTEZ
     * @since  15.11.2007
     */
    public static final FarmaColumnData columnsListaIngresos[] =
    { new FarmaColumnData("Nro", 80, JLabel.LEFT), new FarmaColumnData("Pos", 30, JLabel.RIGHT),
      new FarmaColumnData("Fecha", 80, JLabel.LEFT), new FarmaColumnData("Cant. S.", 75, JLabel.RIGHT),
      new FarmaColumnData("Costo Comp", 85, JLabel.RIGHT), new FarmaColumnData("Cant Ing", 75, JLabel.RIGHT),
      new FarmaColumnData("Costo Ing", 85, JLabel.RIGHT), new FarmaColumnData("Fecha Ingr", 80, JLabel.LEFT),
      new FarmaColumnData("Cant. Verif.", 75, JLabel.RIGHT), new FarmaColumnData("Costo Verif.", 85, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaIngresos = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    /**
     * Listado de Saldos
     */
    public static final FarmaColumnData columnsListaSaldos[] =
    { new FarmaColumnData("Zona", 100, JLabel.LEFT), new FarmaColumnData("Disponible", 70, JLabel.RIGHT),
      new FarmaColumnData("Transito", 70, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaSaldos = { " ", " ", " " };

    /**
     * Variables para el Filtro en listad  o de productos
     * @author  JCORTEZ
     * @since   21.11.2007
     *
     * @since   17.01.2008
     * */
    public static String NOM_HASTABLE_CMBFILTROPROD = "CMB_FILTRO_PROD";
    public static final String[] vCodColumna = { "T", "S", "N", "A" };
    public static final String[] vDescColumna = { "Trabajados", "Nuevos", "No nuevos", "Prod.Cadena" };
    /*public static final String[] vCodColumna ={ "S", "N", "A"};
  public static final String[] vDescColumna ={"Nuevos", "No nuevos","Prod.Cadena"};*/

    //Variables para el filtro del listado de productos
    public static final String[] vCodColum =
    { "Codigo", "Producto", "Unidad", "Laboratorio", "Stk.Almacen", "Stk.Bloqueado", "Stk.Locales", "Locales",
      "Stk.Trans", "Estado", "Zan", "Tipo" };
    public static final String[] vDescColum = { "0", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
    public static final String NOM_HASTABLE_CMBCOLUMNAPROD = "CMB_COLUMNA_PROD";

    public static final String[] vCodOrden = { "ASC", "DESC" };
    public static final String[] vDescOrden = { FarmaConstants.ORDEN_ASCENDENTE, FarmaConstants.ORDEN_DESCENDENTE };
    public static final String NOM_HASTABLE_CMBORDENPROD = "CMB_ORDEN_PROD";

    // Variables para el filtro del listado de locales
    public static final String[] vCodColumLoc =
    { "Codigo", "Local", "Stock", "Stock Transito", "1°Mes", "2°Mes", "3°Mes" };
    public static final String[] vDescColumLoc = { "0", "1", "2", "3", "4", "5", "6" };
    public static final String NOM_HASTABLE_CMBCOLUMNALOCAL = "CMB_COLUMNA_LOCAL";

    public static final String[] vCodOrdenLoc = { "ASC", "DESC" };
    public static final String[] vDescOrdenLoc = { FarmaConstants.ORDEN_ASCENDENTE, FarmaConstants.ORDEN_DESCENDENTE };
    public static final String NOM_HASTABLE_CMBORDENLOCAL = "CMB_ORDEN_LOCAL";


    /**
     * Tipos de listado
     * @author  JCORTEZ
     * @since   16.01.2008
     * */

    public static final String listDist = "D";
    public static final String listAlmac = "A";

    /**
     * DlgHistoricoParamLocal
     * @author  JCORTEZ
     * @since   19.09.2008
     * */
    public static final FarmaColumnData[] columnsHistoParam =
    { new FarmaColumnData("Local", 180, JLabel.LEFT), new FarmaColumnData(" RDM ", 45, JLabel.RIGHT),
      new FarmaColumnData("Usuario", 90, JLabel.RIGHT), new FarmaColumnData("Fecha", 125, JLabel.RIGHT) };
    public static final Object[] defaultValuesHistoParam = { " ", " ", " ", " " };

    public static final String[] vCodTipLocal = { "Todos", "Lima", "Provincia" };
    public static final String[] vDesTipLocal = { "%", "N", "S" };
    public static final String NOM_HASTABLE_CMBTIPLOCAL = "CMB_TIP_LOCAL";

    /**
     * Pedido Adioional Matriz
     * @author DVELIZ
     * @since 23.09.08
     */
    public static final FarmaColumnData columnsListaLocales[] =
    { new FarmaColumnData("Codigo", 70, JLabel.LEFT), new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
      new FarmaColumnData("PVM", 30, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaLocales = { " ", " ", " " };

    public static final FarmaColumnData columnsListaProductosPedidoAdicionalMatriz[] =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Estado", 50, JLabel.RIGHT),
      new FarmaColumnData("Descripcion", 210, JLabel.LEFT), new FarmaColumnData("Unidad", 100, JLabel.LEFT),
      new FarmaColumnData("Cant.Sug.", 90, JLabel.RIGHT), new FarmaColumnData("Stock", 90, JLabel.RIGHT),
      new FarmaColumnData("Cant.Sol.", 90, JLabel.RIGHT), new FarmaColumnData("Cant.Auto.", 90, JLabel.RIGHT),
      new FarmaColumnData("", 0, JLabel.RIGHT), //Laboratorio
        new FarmaColumnData("", 0, JLabel.RIGHT), //StockFisico
        new FarmaColumnData("", 0, JLabel.RIGHT) //ValFrac
        } ;

    public static final Object[] defaultValuesProductosPedidoAdicionalMatriz =
    { " ", " ", " ", " ", " ", " ", " ", " " };

    public static String vCantAutorizada = "7";


    /**
     * Columnas de Lista de Pedidos Especiales
     * @author dubilluz
     * @since  22.09.2008
     */
    public static final FarmaColumnData columnsListaPedEspeciales[] =
    { new FarmaColumnData("Local", 170, JLabel.LEFT), new FarmaColumnData("Nro Pedido", 80, JLabel.LEFT),
      new FarmaColumnData("Fech.Generado", 130, JLabel.RIGHT), new FarmaColumnData("Items", 35, JLabel.RIGHT),
      new FarmaColumnData("Estado", 120, JLabel.RIGHT), new FarmaColumnData("Fech.Aprob", 85, JLabel.RIGHT),
      new FarmaColumnData("Usuario", 100, JLabel.RIGHT), new FarmaColumnData(" ", 0, JLabel.RIGHT), //orden
        new FarmaColumnData(" ", 0, JLabel.RIGHT), //local
        new FarmaColumnData(" ", 0, JLabel.RIGHT) //estado
        } ;

    public static final Object[] defaultValuesListaPedEspeciales = { " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final String NOM_HASTABLE_CMBESTADO_PED_ESCP = "cmbEstadoPedidoEspecial";

    /**
     * Columnas para el detalle de pedidos Especiales
     */
    public static final FarmaColumnData columnsListaDetPedEspeciales[] =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Descripcion", 230, JLabel.LEFT),
      new FarmaColumnData("Unidad", 110, JLabel.LEFT), new FarmaColumnData("x Local.", 70, JLabel.RIGHT),
      new FarmaColumnData("x Matriz.", 70, JLabel.RIGHT), new FarmaColumnData("x SAP.", 70, JLabel.RIGHT),
      new FarmaColumnData("Estado", 60, JLabel.RIGHT), new FarmaColumnData("Cant.Sol.", 70, JLabel.LEFT),
      new FarmaColumnData("Aprobar", 70, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaDetPedEspeciales = { " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final Boolean[] editableListaPedEscDet =
    { new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(false),
      new Boolean(false), new Boolean(false), new Boolean(false), new Boolean(true) };

    public static int COLM_EDITABLE_DET_PED_ESPC = 8;

    public static String EST_PROD_CONFIRMADO = "C";
    public static String EST_PROD_CONF_URGENTE = "U";
    public static String EST_PROD_RECHAZADA = "N";
    public static String EST_PROD_PENDIENTE = "P";


    /************************PEDIDO ESPECIAL MATRIZ****************************************/

    /**
     * @AUTHOR JCORTEZ
     * @SINCE 05.02.2010
     * */

    //DlgListaProdEspecialMatriz
    public static final FarmaColumnData columnsListaProductosEspeciales[] =
    { new FarmaColumnData("Sel.", 30, JLabel.LEFT), new FarmaColumnData("Codigo", 50, JLabel.LEFT),
      new FarmaColumnData("Descripcion", 230, JLabel.LEFT), new FarmaColumnData("Unidad", 110, JLabel.LEFT),
      new FarmaColumnData("Laboratorio", 180, JLabel.LEFT), new FarmaColumnData("Stk.Almc", 70, JLabel.RIGHT),
      new FarmaColumnData("Cant.Sol.", 60, JLabel.RIGHT), new FarmaColumnData("", 0, JLabel.RIGHT),
      new FarmaColumnData("", 0, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaProductosEspeciales =
    { " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaResumenPedidoEspMatriz[] =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Descripcion", 230, JLabel.LEFT),
      new FarmaColumnData("Unidad", 110, JLabel.LEFT), new FarmaColumnData("Laboratorio", 180, JLabel.LEFT),
      new FarmaColumnData("Stock", 70, JLabel.RIGHT), new FarmaColumnData("Cant.Sol.", 60, JLabel.RIGHT),
      new FarmaColumnData("", 0, JLabel.RIGHT), new FarmaColumnData("", 0, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaResumenPedidoEspMatriz = { " ", " ", " ", " ", " ", " ", " ", " " };


    /*****************************PEDIDOS DISTRIBUCION MATRIZ***************************/

    /**
     * @AUTHOR JCORTEZ
     * @SINCE 10.02.2010
     * */

    public static FarmaColumnData columnsListaProdDistribucion[] =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Est.", 0, JLabel.CENTER),
      new FarmaColumnData("Producto", 165, JLabel.LEFT), new FarmaColumnData("Unidad", 100, JLabel.LEFT),
      new FarmaColumnData("Laboratorio", 120, JLabel.LEFT), new FarmaColumnData("Stk.Almc", 55, JLabel.RIGHT),
      //Stk.libre_util
        new FarmaColumnData("Stk.Bloq", 60, JLabel.RIGHT), new FarmaColumnData("Stk.Loc", 60, JLabel.RIGHT),
        new FarmaColumnData("Locales", 55, JLabel.RIGHT), new FarmaColumnData("Stk.Trans", 60, JLabel.RIGHT),
        new FarmaColumnData("Estado", 78, JLabel.LEFT), new FarmaColumnData("ZAN", 35, JLabel.LEFT),
        new FarmaColumnData("TIPO", 90, JLabel.LEFT) };

    public static Object[] defaultValuesListaProdDistribucion =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final String vFiltroListaProdDistriucion = "%";

    public static final String ROL_COMPRAS = "034";
    public static final String ROL_INVENTARIADOR = "016";
}


