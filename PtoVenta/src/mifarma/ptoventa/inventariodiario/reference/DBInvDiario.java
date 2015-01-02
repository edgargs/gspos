package mifarma.ptoventa.inventariodiario.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBInvDiario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.10.2006   Creación<br>
 * <br>
 * @author MARCO FAJARDO<br>
 * @version 1.0<br>
 *
 */
public class DBInvDiario {
    private static final Logger log = LoggerFactory.getLogger(DBInvDiario.class);

    private static ArrayList parametros;
    private Object VariablesInvCiclico;

    public DBInvDiario() {
    }

    /**
     * Lista los productos de inicio de un inventario diario.
     * @param pTableModel
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 25.05.2009
     */
    public static void getListaProdsInicio(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.GET_LISTA_PROD_TOMA_DIA(?,?)",
                                                 parametros, false);
    }

    /**
     * Inicializa los productos para el inventario diario.
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 25.05.2009
     */
    public static void initInventarioDiario() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.DIA_INICIALIZA_TOMA(?,?)", parametros,
                                                 false);
    }

    /**
     * Lista los productos de restantes de un inventario Diario.
     * @param pTableModel
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 25.05.2009
     */
    public static void getListaProdsInv(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma);
        log.debug("PTOVENTA_TOMA_DIA.GET_LISTA_PRODS(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.GET_LISTA_PRODS(?,?,?)", parametros,
                                                 true); //JCHAVEZ 09102009
    }

    /**
     * Inserta producto a la lista de inventario Diario.
     * @param codProd
     * @param unids
     * @param montoTotal
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 25.05.2009
     */
    public static void agregarProducto(String codProd, String unids, String stock, String codLab) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(codLab);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(unids); //FALTA
        parametros.add(stock); //FALTA
        log.debug("maroc:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.DIA_INSERTA_PROD(?,?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Actualiza el estado de carga del producto en la toma.
     * @param codProd
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 15.06.2009
     */
    public static void actualizaFlagCargaProducto(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(codProd);
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.debug("flag carga productos:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_ACT_EST_CARGA_PROD(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Inserta cero en la cantidad del producto a contar por segunda vez
     * @param codProd
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 15.06.2009
     */
    public static void actualizaCantProdConteo(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(codProd);
        parametros.add(FarmaVariables.vIdUsu.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_ACT_CANT_PROD_CONTEO(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Inserta nuevo cruce a la lista de cruces por producto en toma.
     * @param codProd
     * @param codlab
     * @param codprodcruce
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 25.05.2009
     */
    public static void agregarCruce(String codProd, String codProdCruce, String codLab, String UnidPres1,
                                    String UnidPres2, String ValFrac1, String ValFrac2, String Cant1, String Cant2,
                                    String Accion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(codProdCruce);
        parametros.add(codLab);
        parametros.add(UnidPres1);
        parametros.add(UnidPres2);
        parametros.add(ValFrac1);
        parametros.add(ValFrac2);
        parametros.add(Cant1);
        parametros.add(Cant2);
        parametros.add(Accion);
        parametros.add(VariablesInvDiario.vSecToma.trim());

        log.debug("PARAMETROS DE NUEVO CRUCE   :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_TOMA_DIA.DIA_INSERTA_PROD_CRUCE(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerStockUsadoCruce(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(codProd);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_DIA.TI_OBTIENE_STOCK_USADO_CRUCE(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Borra un producto a la lista de inventario Diario.
     * @param codProd
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 25.05.2009
     */
    public static void borrarProducto(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.DIA_BORRA_PROD(?,?,?)", parametros, false);
    }


    /**
     * Graba los productos seleccionados, en una nueva toma de inventario.
     * @throws SQLException
     * @author MARCO FAJARDO
     * @since 25.05.2009
     */
    public static void grabarInventarioDiario() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        if (FarmaVariables.vEconoFar_Matriz)
            parametros.add("1");
        else
            parametros.add("0");
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.DIA_GRABA_TOM_INV(?,?,?,?)", parametros,
                                                 false);
    }

    public static void getListaTomasInv(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_LISTA_TOMAS_INV(?,?)", parametros,
                                                 false);
    }

    public static void getListaLabsXToma(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_LISTA_LABS_TOMA_INV(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaProdsXLabXToma(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        //parametros.add(VariablesInvDiario.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_LISTA_PROD_LAB_TOMA_INV(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaProdsXLabXTomaConteo(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        //parametros.add(VariablesInvDiario.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_LISTA_PROD_LAB_TOMA_CONTEO(?,?,?)",
                                                 parametros, false);
    }


    public static void ingresaCantidadProdInv(String pCantToma, String pHora) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(VariablesInvDiario.vCodLab.trim());
        parametros.add(VariablesInvDiario.vCodProd.trim());
        parametros.add(pCantToma);
        parametros.add(pHora);
        log.debug("MARCO - PARAMETROS INGRESA CANTIDAD : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_INGRESA_CANT_PROD_TI(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void ingresaCantidadProdInvConteo(String pCantToma, String pHora,
                                                    String pusuario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(VariablesInvDiario.vCodLab.trim());
        parametros.add(VariablesInvDiario.vCodProd.trim());
        parametros.add(pCantToma);
        parametros.add(pHora);
        parametros.add(pusuario);
        log.debug("MARCO - PARAMETROS INGRESA CANTIDAD : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_TOMA_DIA.TI_INGRESA_CANT_PROD_TI_CONTEO(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    public static void getListaMovsKardex(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vCodProd);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_LISTA_MOVS_KARDEX(?,?,?)",
                                                 parametros, false);
    }

    public static ArrayList obtieneTotalItemsToma(String pSecToma) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecToma);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_TOMA_DIA.TI_TOTAL_ITEMS_TOMA(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static ArrayList obtieneInformacionValorizada(String pSecToma) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecToma);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,
                                                          "PTOVENTA_TOMA_DIA.TI_INFORMACION_VALORIZADA(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static void listaDiferenciasConsolidado(FarmaTableModel pTableModel,
                                                   String pSecTomaInv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_DIFERENCIAS_CONSOLIDADO(?,?,?)",
                                                 parametros, false);
    }

    public static void listaDiferenciasConsolidadoFiltro(FarmaTableModel pTableModel,
                                                         String pSecTomaInv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        parametros.add(VariablesPtoVenta.vCodFiltro);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_DIF_CONSOLIDADO_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargarToma() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_CARGA_TOMA_INV(?,?,?,?)", parametros,
                                                 false);
    }

    public static void cargarCruces() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_CARGA_CRUCES_INV(?,?,?,?)", parametros,
                                                 false);
    }


    public static String obtenerIndTomaIncompleta() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_DIA.TI_OBTIENE_IND_TOMA_INCOMPLETA(?,?,?)",
                                                           parametros);
    }

    public static String obtenerTiempoModifcacionToma() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_DIA.TI_OBTIENE_TIEMPO_MOD_TOMA(?,?)",
                                                           parametros);
    }

    public static String obtenerIndStockComprometido(String prod, String cant) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(prod);
        parametros.add(cant);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_DIA.TI_OBTIENE_IND_STOCK_COMP(?,?,?,?)",
                                                           parametros);
    }

    public static void obtieneIndTomaInvForUpdate(ArrayList pArrayList, String pSecTomaInv,
                                                  String pIndProceso) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        parametros.add(pIndProceso);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_TOMA_DIA.TI_OBTIENE_IND_FOR_UPDATE(?,?,?,?)",
                                                          parametros);
    }

    public static void anularToma() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_ANULA_TOMA_INV(?,?,?,?)", parametros,
                                                 false);
    }

    public static ArrayList getListaLabsTomaIncompleta() throws SQLException {
        parametros = new ArrayList();
        ArrayList rpta = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        FarmaDBUtility.executeSQLStoredProcedureArrayList(rpta,
                                                          "PTOVENTA_TOMA_DIA.TI_LISTA_LABS_TOMA_INCOMPLETA(?,?,?)",
                                                          parametros);
        return rpta;
    }

    public static void rellenaCantNoIngresadas() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        //parametros.add(VariablesInvDiario.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_RELLENA_CERO_LAB_TOMA_INV(?,?,?)",
                                                 parametros, false);
    }

    public static void rellenaCantNoIngresadasConteo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        //parametros.add(VariablesInvDiario.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_RELLENA_CERO_CONTEO(?,?,?)", parametros,
                                                 false);
    }

    public static void getListaProdsDiferencias(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(VariablesInvDiario.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_TOMA_DIA.TI_LISTA_DIF_PROD_LAB_TOMA_INV(?,?,?,?)",
                                                 parametros, false);
    }

    public static ArrayList obtieneCodigoLaboratorios() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,
                                                          "PTOVENTA_TOMA_DIA.TI_LISTA_COD_LABORATORIOS(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static ArrayList obtieneProductosPorLaboratotio(String pCodLab) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodLab);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,
                                                          "PTOVENTA_TOMA_DIA.TI_LISTA_PROD_IMPRESION(?,?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static void envioTomaLocal(String pSecToma) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_ENVIA_TOMA_LOCAL(?,?,?)", parametros,
                                                 false);
    }

    /**
     * metodo que revierte el producto segun el motivo
     * @param pCodProd
     * @param pSecToma
     * @throws SQLException
     */


    public static void pRevertirProducto(String pCodProd, String pSecToma, String pCodMotivo,

        String pSecKardex, String pCantMov, String pValFrac) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pSecToma.trim());
        parametros.add(pCodProd.trim());
        parametros.add(pCodMotivo.trim());
        parametros.add(pSecKardex.trim());
        parametros.add(pCantMov.trim());
        parametros.add(pValFrac.trim());
        log.debug("revertir PTOVENTA_TOMA_DIA.TI_P_REVERTIR_PROD(?,?,?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_P_REVERTIR_PROD(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void listaDiferenciasDiario(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("Lista Diferencias Diario PTOVENTA_TOMA_DIA.TI_F_CUR_DIFERENCIAS_DIARIO(?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_F_CUR_DIFERENCIAS_DIARIO(?,?)",
                                                 parametros, true);
    }

    public static void cargaListaTrabajadores(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("Lista trabajadores : PTOVENTA_TOMA_DIA.TI_F_CUR_LISTA_TRAB(?,?,?) : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_F_CUR_LISTA_TRAB(?,?,?)",
                                                 parametros, false);
    }
    //TI_F_VAR2_GET_TRABAJADOR

    public static String getDatosTrabajador(String pDNI) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDNI.trim());
        log.debug("Lista trabajadores : PTOVENTA_TOMA_DIA.TI_F_VAR2_GET_TRABAJADOR(?,?,?,?) : " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_DIA.TI_F_VAR2_GET_TRABAJADOR(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Se ajusta los productos seleccionados
     * @AUTHOR JCORTEZ
     * @SINCE 15.06.09
     * */
    public static void ajustarProd(String pCodProd, String pSecToma, String pCodMotivo, String pSecKardex,
                                   String pCantMov, String pValFrac, String pMotivo,
                                   String cCodAjuste) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pSecToma.trim());
        parametros.add(pCodProd.trim());
        parametros.add(pCodMotivo.trim());
        parametros.add(pSecKardex.trim());
        parametros.add(pCantMov.trim());
        parametros.add(pValFrac.trim());
        parametros.add(pMotivo.trim());
        parametros.add(cCodAjuste.trim());
        log.debug("revertir PTOVENTA_TOMA_DIA.TI_AJUSTE(?,?,?,?,?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_AJUSTE(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }


    public static int ObtenerValFracMaxProd(String prod) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(prod);
        log.debug("Lista parametros" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_TOMA_DIA.TI_OBTIENE_IND_STOCK_COMP(?,?)",
                                                           parametros);
    }

    /**
     * Se genera el ajuste
     * @AUTHOR JCORTEZ
     * @SINCE 15.06.09
     * */
    public static void grabarAjuste(String pCodMotivo, String pCodAjuste) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodMotivo.trim());
        parametros.add(pCodAjuste.trim());
        log.debug("revertir PTOVENTA_TOMA_DIA.GRABAR_AJUSTE(?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.GRABAR_AJUSTE(?,?,?,?,?)", parametros,
                                                 false);
    }


    /**
     * Se guarda ajuste por productos
     * @AUTHOR JCORTEZ
     * @SINCE 15.06.09
     * */
    public static void grabarAjusteProd(String cCodAjuste, String cCotToma, String cCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(cCodAjuste.trim());
        parametros.add(cCotToma.trim());
        parametros.add(cCodProd.trim());
        log.debug("revertir PTOVENTA_TOMA_DIA.GRABAR_AJUSTE_PROD(?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.GRABAR_AJUSTE_PROD(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se guarda ajuste por productos
     * @AUTHOR JCORTEZ
     * @SINCE 15.06.09
     * */
    public static void grabarAjusteTrab(String cCodAjust, String cCodTrabRRHH, String cCodTrab,
                                        String Monto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(cCodAjust.trim());
        parametros.add(cCodTrab.trim());
        parametros.add(cCodTrabRRHH.trim());
        parametros.add(new Double(Monto.trim()));
        log.debug("revertir PTOVENTA_TOMA_DIA.GRABAR_AJUSTE_TRAB(?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.GRABAR_AJUSTE_TRAB(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Graba el pedido Temporal Nuevo y retorna el numero de Pedido del Temporal
     * @author dubilluz
     * @param pDNI
     * @return
     * @throws SQLException
     */
    public static String getGeneraTemporalPedidoInvDiario() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("Genera Pedido temporal : PTOVENTA_TOMA_DIA.TI_P_GRABA_PED_CAB(?,?,?) : " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_DIA.TI_P_GRABA_PED_CAB(?,?,?)", parametros);
    }

    /**
     * Añade el producto al pedido Temporal
     * @author dubilluz
     * @param pSecPedido
     * @param pSecDetalle
     * @param pCodProd
     * @param pCantMov
     * @param pValFrac
     * @throws SQLException
     */
    public static void agregaProdDetalleTemporal(String pSecPedido, String pSecDetalle, String pCodProd,
                                                 String pCantMov, String pValFrac, String pSecToma, String pSecKardex,
                                                 String pCodMotivo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecPedido.trim());
        parametros.add(pSecDetalle.trim());
        parametros.add(pCodProd.trim());
        parametros.add(pCantMov.trim());
        parametros.add(pValFrac.trim());
        parametros.add(pSecToma.trim());
        parametros.add(pSecKardex.trim());
        parametros.add(pCodMotivo.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("graba Detalle Temporal PTOVENTA_TOMA_DIA.TI_P_GRABA_PED_DET(?,?,?,?,?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_P_GRABA_PED_DET(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }


    public static void cargaDetallePedTemporal(FarmaTableModel pTableModel,
                                               String pNumPedTemporal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedTemporal.trim());
        log.debug("Lista trabajadores : PTOVENTA_TOMA_DIA.TI_F_CUR_DET_PED_TEMPORAL(?,?,?) : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_F_CUR_DET_PED_TEMPORAL(?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaDetallePedTemporal(String pNumPedTemporal, String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedTemporal.trim());
        parametros.add(pCodProd.trim());
        log.debug("Lista trabajadores : PTOVENTA_TOMA_DIA.TI_P_ELIMINA_DET_PED_TEMP(?,?,?,?) : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_P_ELIMINA_DET_PED_TEMP(?,?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaPedTemporal(String pNumPedTemporal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedTemporal.trim());
        log.debug("Lista trabajadores : PTOVENTA_TOMA_DIA.TI_P_ELIMINA_PED_TEMP(?,?,?) : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_P_ELIMINA_PED_TEMP(?,?,?)", parametros,
                                                 false);
    }

    public static void generaPedidoInvDiario(String pNumPedTemporal, String pNeto, String pRedondeo,
                                             ArrayList pListaTrabajadores) throws SQLException {

        //Primero registraremos los trabajadores asignados con sus descuentos
        for (int i = 0; i < pListaTrabajadores.size(); i++) {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pNumPedTemporal.trim());
            parametros.add(FarmaUtility.getValueFieldArrayList(pListaTrabajadores, i, 0));
            parametros.add(FarmaUtility.getValueFieldArrayList(pListaTrabajadores, i, 1));
            parametros.add(FarmaUtility.getValueFieldArrayList(pListaTrabajadores, i, 4));
            parametros.add(FarmaUtility.getValueFieldArrayList(pListaTrabajadores, i, 3));
            parametros.add(FarmaVariables.vIdUsu);
            log.debug(i + " - Graba Trabajor DCTO : PTOVENTA_TOMA_DIA.TI_P_INSERT_TRAB_DCTO(?,?,?,?,?,?,?,?) : " +
                      parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_P_INSERT_TRAB_DCTO(?,?,?,?,?,?,?,?)",
                                                     parametros, false);
        }

        //Ahora se generará el Pedido
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedTemporal.trim());
        parametros.add(pNeto.trim());
        parametros.add(pRedondeo.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("Lista trabajadores : PTOVENTA_TOMA_DIA.TI_P_GRABA_PEDIDO(?,?,?,?,?,?,?,?) : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_P_GRABA_PEDIDO(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaPedidosPendientesInvDiario(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("Lista Ped Pendientes : PTOVENTA_TOMA_DIA.TI_F_CUR_LISTA_PED_PENDIENTE(?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_F_CUR_LISTA_PED_PENDIENTE(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaDetallePedidoInvDiario(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        log.debug("Lista Ped Pendientes : PTOVENTA_TOMA_DIA.TI_F_CUR_DET_PED_PENDIENTE(?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_DIA.TI_F_CUR_DET_PED_PENDIENTE(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaFormaPagoPedidoInvDiario(ArrayList pArrayList, String pNumPedido) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido.trim());
        log.debug("Lista Ped Pendientes : PTOVENTA_TOMA_DIA.TI_P_CUR_FORMA_PAGO_PEDIDO(?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_TOMA_DIA.TI_P_CUR_FORMA_PAGO_PEDIDO(?,?,?)",
                                                          parametros);
    }

    public static void obtieneInfoCobrarPedido(ArrayList pArrayList, String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.debug("Lista Ped Pendientes : PTOVENTA_TOMA_DIA.TI_F_CUR_INFO_PEDIDO(?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_TOMA_DIA.TI_F_CUR_INFO_PEDIDO(?,?,?)",
                                                          parametros);
    }

    public static void obtieneTrabDescuento(ArrayList pArrayList, String pSecPedTmp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecPedTmp.trim());
        log.debug("Lista Ped Pendientes : PTOVENTA_TOMA_DIA.TI_F_CUR_TRAB_DCTO_PEDIDO(?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_TOMA_DIA.TI_F_CUR_TRAB_DCTO_PEDIDO(?,?,?)",
                                                          parametros);
    }

    public static String obtieneNetoPedido(String pSecPedTmp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecPedTmp.trim());
        log.debug("Lista Ped Pendientes : PTOVENTA_TOMA_DIA.TI_F_VAR2_MONTO_PEDIDO(?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_DIA.TI_F_VAR2_MONTO_PEDIDO(?,?,?)",
                                                           parametros);
    }

    //JMIRANDA 03/08/09

    public static void modificaSegConteo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvDiario.vSecToma.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_DIA.TI_P_ACT_IND_SEG_CONTEO(?,?,?,?)",
                                                 parametros, false);
    }

}
