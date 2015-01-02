package mifarma.ptoventa.matriz.mantenimientos.productos.references;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBProducto {
    private static final Logger log = LoggerFactory.getLogger(DBProducto.class);

    private static ArrayList parametros;

    public static void cargaListaProductosNvos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_PROD_NVOS.P_NVOS_OBTIENE_PROD_NVOS",
                                                 parametros, false);
    }

    public static void cargaListaLocalesXProd(FarmaTableModel pTableModel, String pCodProducto) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProducto);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_PROD_NVOS.P_NVOS_OBTIENE_LOCALES_X_PROD(?,?)",
                                                 parametros, false);
    }

    public static void insertaProdNuevos(String pCodGrupoCia, String pCodLocal, String pCodProducto, String pCantidad,
                                         String pUsuario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pCodProducto);
        parametros.add(new Integer(pCantidad));
        parametros.add(pUsuario);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_MATRIZ_PROD_NVOS.P_NVOS_INSERTA_PROD_NVOS_LOCAL(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaZonas(ArrayList pListaZonas) throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaZonas,
                                                          "PTOVENTA_MATRIZ_PROD_ADIC.P_ADIC_OBTIENE_ZONAS",
                                                          parametros);
    }

    public static void cargaListaLocalesXZona(FarmaTableModel pTableModel, String pCodZona) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(pCodZona);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_PROD_ADIC.P_ADIC_OBTIENE_LOCALES_X_ZONA(?)",
                                                 parametros, false);
    }

    public static void cargaListaProductosAdic(FarmaTableModel pTableModel, String pCodGrupoCia,
                                               String pCodZona) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodZona);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_PROD_ADIC.P_ADIC_OBTIENE_PROD_ADIC(?,?)", parametros,
                                                 false);
    }

    public static void insertaProdAdicionales(String pCodGrupoCia, String pCodLocal, String pCodProducto,
                                              String pCantidad, String pUsuario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pCodProducto);
        parametros.add(new Integer(pCantidad));
        parametros.add(pUsuario);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_MATRIZ_PROD_ADIC.P_ADIC_INSERTA_PROD_ADIC_LOCAL(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obiene el listado de los pedidos de distribucion
     * @author: JCORTEZ
     * @since 08.11.2007
     */
    public static void cargaPedidosDistribucion(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.LISTADO_PEDIDOS_DISTRIBUCION(?,?)",
                                                 parametros, false);
    }


    /**
     * Se obiene el listado de los pedidos de distribucion
     * @author: JCORTEZ
     * @since 08.11.2007
     */
    public static void cargaPedidosPorFecha(FarmaTableModel pTableModel, String FechaIncio,
                                            String FechaFin) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FechaIncio);
        parametros.add(FechaFin);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.LISTADO_PEDIDOS_POR_FECHA(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene el detalle de los Productos
     * @author: JCORTEZ
     * @since 09.11.2007
     */
    public static void cargaDetalleProductos(FarmaTableModel pTableModel, String cNumPedido) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cNumPedido);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.LISTADO_DETALLE_PRODUCTOS(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene el detalle de los Locales
     * @author: JCORTEZ
     * @since 09.11.2007
     */
    public static void cargaDetalleLocales(FarmaTableModel pTableModel, String cNumPedido,
                                           String cCodProd) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cNumPedido);
        parametros.add(cCodProd);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.LISTADO_DETALLE_LOCALES(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene los productos para generar el pedido
     * @author: JCORTEZ
     * @since 09.11.2007
     */
    public static void cargaListaProductosDist(FarmaTableModel pTableModel, String Filtro, String IndNuevo,
                                               String OrderCol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Filtro);
        parametros.add(IndNuevo);
        parametros.add(OrderCol);
        log.debug("ENVIANDO FILTRO : " + Filtro);
        log.debug("IND NUEVO : " + IndNuevo);
        log.debug("ORDER COLUM : " + OrderCol);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.LISTADO_PRODUCTOS_PEDIDO(?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se obtiene el listado de laboratorios
     * @author: JCORTEZ
     * @since 12.11.2007
     */
    public static void cargaListaLaboratorios(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_DIST_PROD.LISTADO_LABORATORIOS(?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene el listado de laboratorios
     * @author: JCORTEZ
     * @since 12.11.2007
     */
    public static void obtieneDescripcionLaboratorio(ArrayList pArrayList, String vCodLab) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(vCodLab);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_MATRIZ_DIST_PROD.DESCRIPCION_LABORATORIOS(?,?)",
                                                          parametros);
    }

    /**
     * Obtiene los meses de Unid. Vend.
     * @param pArray
     * @throws SQLException
     */
    public static void getMesesUnidVend(ArrayList pArray) throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_MATRIZ_DIST_PROD.CO_GET_MESES_UNID_VEND",
                                                          parametros);
    }

    /**
     * Se carga locales por producto
     * @author: JCORTEZ
     * @since 12.11.2007
     */
    public static void cargaListaLocalesProd(FarmaTableModel pTableModel, String pCodProducto,
                                             String pIndProv) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProducto);
        parametros.add(pIndProv);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.OBTIENE_LOCALES_POR_PROD(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se genera el pedido de distribucion final
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static void insertarPedidoDistribucion(String NumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumPedido);
        log.debug("INGRESANDO CABECERA....");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.INSER_CABECERA(?,?,?,?)", parametros,
                                                 false);
        log.debug("INGRESO EXITOSO....");
    }

    /**
     * Se Ingresa la cabecera del Pedido
     * @author: JCORTEZ
     * @since 12.11.2007
     */
    /* public static void insertarCabeceraPedido(String NumPedido,
                                             String CantItems,
                                             String CantProds) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(NumPedido);
    parametros.add(new Integer(CantItems));
    parametros.add(new Integer(CantProds));
    log.debug("INGRESANDO CABECERA....");
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_MATRIZ_DIST_PROD.TRANSFERENCIAS_INSER_CABECERA(?,?,?,?,?,?)",parametros,false);
    log.debug("INGRESO EXITOSO....");
  }*/

    public static void insertarCabeceraPedidoAux(String NumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumPedido);
        log.debug("INGRESANDO CABECERA....");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.INSER_CABECERA_AUX(?,?,?,?)",
                                                 parametros, false);
        log.debug("INGRESO EXITOSO....");
    }


    /**
     * Se Ingresa la detalle del Pedido
     * @author: JCORTEZ
     * @since 12.11.2007
     */
    /* public static void insertarDetallePedido(String vNumPedido,
                                            String vLocal,
                                            String vCodProd,
                                            String vStockLocal,
                                            String vCantidad) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(vNumPedido);
    parametros.add(vLocal);
    parametros.add(vCodProd);
    parametros.add(new Integer(vStockLocal));
    parametros.add(vCantidad);
    log.debug("INGRESANDO DETALLE....");
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_MATRIZ_DIST_PROD.TRANSFERENCIAS_INSER_DETALLE(?,?,?,?,?,?,?,?)",parametros,false);
    log.debug("INGRESO EXITOSO....");
  }*/


    //AUXILIAR
    public static void insertarDetallePedido(String vNumPedido, String vLocal, String vCodProd, String vStockLocal,
                                             String vCantidad) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(vNumPedido);
        parametros.add(vLocal);
        parametros.add(vCodProd);
        parametros.add(new Integer(vStockLocal));
        parametros.add(vCantidad);
        log.debug("INGRESANDO DETALLE....");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.INSER_DETALLE_AUX(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
        log.debug("INGRESO EXITOSO....");
    }


    /**
     * Se valida la anulacion del pedido
     * @author: JCORTEZ
     * @since 12.11.2007
     */
    public static void validaEstado(String NumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(NumPedido);
        log.debug("VALIDA ESTADO DEL PEDIDO...");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.PEDIDO_VALIDA_ESTADO(?,?,?)",
                                                 parametros, false);
    }


    public static void aceptarPedido(String cNumero) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(cNumero);
        log.debug("ACEPTANDO PEDIDO................................");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.ACEPTAR_PEDIDO(?,?)", parametros,
                                                 false);
        log.debug("CAMBIO DE ESTADO EXITOSO................................");
    }

    public static void anularPedido(String cNumero) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(cNumero);
        log.debug("ANULANDO PEDIDO................................");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.ANULAR_PEDIDO(?,?)", parametros,
                                                 false);
        log.debug("CAMBIO DE ESTADO EXITOSO................................");
    }


    /**
     * Se carga Lista OC Pendientes
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static void cargaListaOCPendiente(FarmaTableModel pTableModel, String pCodProducto) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProducto);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_DIST_PROD.CO_GET_OC_PENDIENTES(?,?)",
                                                 parametros, false);
    }

    /**
     * Se carga Lista OC Ingresados
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static void cargaListaOCIngresos(FarmaTableModel pTableModel, String pCodProducto) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProducto);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_DIST_PROD.CO_GET_INGRESOS_PROD(?,?)",
                                                 parametros, false);
    }

    /**
     * Se carga las unidades vendidas
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static void listarUnidVendProd(FarmaTableModel pTableModel, String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_DIST_PROD.CO_GET_RES_UNID_VEND(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Obtiene el resumen de Saldos
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static void listarSaldosProd(FarmaTableModel pTableModel, String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_DIST_PROD.CO_GET_RES_SALDOS(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Retorna el saldo en almacen QS.
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static String listarSaldoQSProd(String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        log.debug("", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_DIST_PROD.CO_GET_RES_SALDO_QS(?,?,?)",
                                                           parametros);
    }


    /**
     * Se se verifica la existencia de pedidos temporales aceptados
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static String getExistPedTemporalAcep() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_DIST_PROD.VALIDA_TEMPORAL_ACEPTADO(?,?)",
                                                           parametros);
    }

    /**
     * Se se verifica la existencia de pedidos temporales pendientes
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static String getExistPedTemporalPend() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_DIST_PROD.VALIDA_TEMPORAL_PENDIENTE(?,?)",
                                                           parametros);
    }


    /**
     * Obtiene cantidad de productos por local en pedidos temporales
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static void obtieneCantidadProducto(ArrayList pArrayList, String CodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(CodProd);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_MATRIZ_DIST_PROD.GET_CANT_PROD_PEDTEMP(?,?)",
                                                          parametros);
    }


    //eliminaDistribucion

    public static void eliminaDistribucion(String cCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cCodProd);
        log.debug("ELIMINAR DISTRIBUCION................................");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.ELIMINAR_DISTRIBUCION(?,?,?)",
                                                 parametros, false);
        log.debug("ELIMINACION EXITOSA................................");
    }

    //getExistTemporalProd

    /**
     * Se  verifica la existencia del producto en el pedido temporal
     * @author: JCORTEZ
     * @since 15.11.2007
     */
    public static String getExistProdTemporal(String cCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cCodProd);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_DIST_PROD.VALIDA_TEMPORAL_PROD(?,?,?)",
                                                           parametros);
    }

    /**
     * Se obtiene los productos para generar el pedido
     * @author: JCORTEZ
     * @since:  16.01.2008
     */
    public static void cargaListaProductosAlmc(FarmaTableModel pTableModel, String Filtro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Filtro);
        log.debug("ENVIANDO FILTRO : " + Filtro);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.LISTADO_PRODUCTOS_ALMACEN(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se anula la distribucion del producto en local
     * @author: JCORTEZ
     * @since:  04.02.2008
     */
    public static void anularPedidoLocal(String cNumPedido, String cCodProducto,
                                         String cCodLocalDist) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(cNumPedido);
        parametros.add(cCodProducto);
        parametros.add(cCodLocalDist);
        log.debug("DATOS :" + parametros);
        log.debug("ANULACION POR LOCAL................................");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.ANULAR_PED_PROD_LOCAL(?,?,?,?,?,?)",
                                                 parametros, false);
        log.debug("ANULACION POR LOCAL EXITOSA................................");
    }


    /**
     * Se valida la anulacion de la distribucion en local
     * @author: JCORTEZ
     * @since 04.02.2008
     */
    public static void validaEstadoProdLocal(String cNumPedido, String cCodProd,
                                             String cCodLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cNumPedido);
        parametros.add(cCodProd);
        parametros.add(cCodLocal);
        log.debug("VALIDA ESTADO DEL PEDIDO...");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.VALIDA_ESTADO_PROD_LOCAL(?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se actualiza o ingresa el parametro para el calculo de pedidos de reposicion
     * @author: JCORTEZ
     * @since 11.09.2008
     */
    public static void insertarParametroRepLocal(String vNumPedido, String vLocal, String vCodProd, String vStockLocal,
                                                 String vCantidad) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(vLocal);
        parametros.add(vCodProd);
        parametros.add(new Integer(vCantidad));
        log.debug("INGRESANDO PARAMETROS...." + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_DIST_PROD.INSER_PARAMETROS_LOCAL(?,?,?,?,?,?)",
                                                 parametros, false);
        log.debug("INGRESO EXITOSO....");
    }

    /**
     * Lista los locales con la cantidad de pvm actualizado.
     * @author DVELIZ
     * @since  22.09.08
     * @throws SQLException
     */
    public static void listarLocalesPVM(FarmaTableModel pTableModel, String filtro) throws SQLException {

        parametros = new ArrayList();
        parametros.add(new Integer(filtro));
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PVM_LOCAL(?)",
                                                 parametros, false);
    }

    /**
     * Lista los PVM de los productos de cierto local
     * @author DVELIZ
     * @since  22.09.08
     * @param pTableModel
     * @throws SQLException
     */
    public static void cargaListaProductosPedidoAdicionalMatriz(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesProducto.vCodLocal_PedAdic);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PROD_PED_MAT(?,?)",
                                                 parametros, false);

    }

    public static void cargaDetalleProductoPVMMatriz(ArrayList pArray, String pCodProd) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesProducto.vCodLocal_PedAdic);
        parametros.add(pCodProd);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,
                                                          "PTOVENTA_PED_ADICIONAL.PED_F_CUR_DET_PROD_PED_MAT (?,?,?)",
                                                          parametros);
    }

    /**
     * Lista los pedidos especiales
     * @param pTableModel
     * @param pFechaIni
     * @param pFechaFin
     * @throws SQLException
     */
    public static void cargaListaPedidosEspeciales(FarmaTableModel pTableModel, String pFechaIni, String pFechaFin,
                                                   String pEstado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaIni.trim());
        parametros.add(pFechaFin.trim());
        parametros.add(pEstado.trim());
        log.debug("lista pedidos parametros " + parametros);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_F_CUR_LISTA_PEDIDOS(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author DUBILLUZ
     * @param pTableModel
     * @throws SQLException
     */
    public static void cargaListaProductosEspeciales(FarmaTableModel pTableModel) throws SQLException {
        //pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesProducto.vCodLocal);
        parametros.add(VariablesProducto.vNumPedEspecial);
        log.debug("parametros " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_F_CUR_LISTA_PED_DET(?,?,?)",
                                                 parametros, false);
    }

    //PED_ESPC_P_UPT_PROD_DET

    /**
     *
     * @author DUbilluz
     * @param NumPedEsp
     * @param ArrayProd
     * @throws SQLException
     */
    public static void actualizaProdDetEspecial(String pCodlocal, String pNumPedEsp, String pCodProd, String pCantidad,
                                                String pEstado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodlocal.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pNumPedEsp.trim());
        parametros.add(pCodProd.trim());
        parametros.add(pCantidad.trim());
        parametros.add(pEstado.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_P_UPT_PROD_DET(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     *
     * @author Dubilluz
     * @param pNumPedEsp
     * @param pCodLocal
     * @param pEstado
     * @throws SQLException
     */
    public static void operaPedidoEspecial(String pNumPedEsp, String pCodLocal, String pEstado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pNumPedEsp.trim());
        parametros.add(pEstado.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_P_UPT_CAB(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author JCORTEZ
     * @param pTableModel
     * @throws SQLException
     */
    public static void getDatosProducto(String cCodProd, ArrayList aux) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesProducto.vCodLocal);
        parametros.add(cCodProd);
        log.debug("parametros " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(aux,
                                                          "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_GET_DATOS_PROD_PED(?,?,?)",
                                                          parametros);
    }

    /**
     * Se obtiene el historico del ultimo PVM autorizado por local
     * @author JCORTEZ
     * @since  19.09.2008
     */
    public static void cargaHistoricoParamLocal(FarmaTableModel pTableModel, String cCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(cCodProd);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_MATRIZ_DIST_PROD.GET_HISTORICO_PARAM_LOCAL(?,?)",
                                                 parametros, false);
    }


    /************************PEDIDO ESPECIAL MATRIZ****************************************/

    /**
     * Se obtiene los productos para generar el pedido
     * @author JCORTEZ
     * @since  04.02.10
     */
    public static void cargaListaProdEspecialMatriz(FarmaTableModel pTableModel, String Filtro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(Filtro);
        log.debug("ENVIANDO FILTRO : " + Filtro);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_F_LISTA_PROD_M(?,?)",
                                                 parametros, true);
    }


    /**
     * Se guarda la cabecera del pedido Especial Matriz
     * @throws SQLException
     * @author JCORTEZ
     * @since   04.02.10
     */
    public static void agregarCabeceraPedEsp(String NumPedEsp, String CantItems,
                                             String CodLocalDestino) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(CodLocalDestino);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumPedEsp);
        parametros.add(CantItems.trim());
        log.debug("invocando a PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_P_ING_CAB_PED_ESP_M(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_P_ING_CAB_PED_ESP_M(?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se guarda el detalle del pedido Especial Matriz
     * @throws SQLException
     * @author JCORTEZ
     * @since  04.02.10
     */
    public static void agregarDetallePedEsp(ArrayList ArrayProd, String NumPedEsp,
                                            String CodLocalDestino) throws SQLException {
        int sec = 1;
        for (int i = 0; i < ArrayProd.size(); i++) {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(CodLocalDestino);
            parametros.add(FarmaVariables.vIdUsu);
            parametros.add(NumPedEsp);
            parametros.add("" + sec);
            sec++;
            parametros.add(((String)((ArrayList)ArrayProd.get(i)).get(0)).trim());
            parametros.add(((String)((ArrayList)ArrayProd.get(i)).get(5)).trim());
            log.debug("invocando a PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_P_ING_DET_PED_ESP_M(?,?,?,?,?,?,?)::" +
                      parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null,
                                                     "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_P_ING_DET_PED_ESP_M(?,?,?,?,?,?,?)",
                                                     parametros, false);
            log.debug("INGRESO EXITOSO...................................");
        }
    }

    /***************************NUEVO MODULO DISTRIBUCION**********************************/

    /**
     * validar rol para creacion de pedidos
     * @AUTHOR JCORTEZ
     * @SINCE 10.02.10
     */
    public static String verificaRolUsuario(String SecUsu, String CodRol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRol);
        log.debug("verifica que el usuario tenga el rol adecuado: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("ADMCENTRAL_PREC.VERIFICA_ROL_USU(?,?,?,?)", parametros);
    }


}
