package mifarma.ptoventa.recepcionCiega.reference;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.VariablesInventario;

import oracle.jdbc.OracleTypes;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBRecepCiega {


    private static final Logger log = LoggerFactory.getLogger(DBRecepCiega.class);
    private static ArrayList parametros;

    /**
     * Se obtiene listado de guias por asociar
     * @author JCORTEZ
     * @since 16.11.2009
     */
    public static void getListaGuias(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_OBTIENE_GUIAS_PEND(?,?)", parametros,
                                                 true);
    }

    /**
     * Se obtiene listado de guias asociadas
     * @author JCORTEZ
     * @since 16.11.2009
     */
    public static void getListaGuiaAso(FarmaTableModel pTableModel, String NumIngreso) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(NumIngreso);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_OBTIENE_GUIAS_ASOC(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se lista detalle de las guias
     * @author JCORTEZ
     * @since 16.11.2009
     */
    public static void getListaDetGuias(FarmaTableModel pTableModel, String NumNotaEs,
                                        String NumGuia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(NumNotaEs.trim());
        parametros.add(NumGuia.trim());
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_DET_GUIA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_DET_GUIA(?,?,?,?)", parametros,
                                                 false);

    }


    /**
     * Se lista detalle de las guias
     * @author JCORTEZ
     * @since 16.11.2009
     */
    public static void getListaRecepcionMercaderiaRango(FarmaTableModel pTableModel, String FechaIni,
                                                        String FechaFin) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FechaIni);
        parametros.add(FechaFin);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_MERCADERIA_RANGO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_MERCADERIA_RANGO(?,?,?,?)",
                                                 parametros, false);

    }

    /**
     * Se lista detalle de las guias
     * @author JCORTEZ
     * @since 16.11.2009
     */
    public static void getListaRecepcionMercaderia(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_MERCADERIA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_MERCADERIA(?,?)",
                                                 parametros, false);

    }

    /**
     * Se crea la nueva recepcion
     * @author JCORTEZ
     * @since  16.11.2009
     */
    public static String agregarRecepcion(int cantGuias) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add("" + cantGuias);
        parametros.add(VariablesRecepCiega.vNombreTrans);
        parametros.add(VariablesRecepCiega.vHoraTrans);
        parametros.add(VariablesRecepCiega.vPlacaUnidTrans);
        parametros.add(new Integer(VariablesRecepCiega.vCantBultos));
        parametros.add(new Integer(VariablesRecepCiega.vCantPrecintos));
        //JMIRANDA 05.03.2010 agrega Glosa
        parametros.add(VariablesRecepCiega.vGlosa);
        log.debug("INGRESO EXITOSO...................................");
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_P_NEW_RECEPCION(?,?,?,?,?,?,?,?,?,?)",
                                                           parametros).trim();


    }

    /**
     * Se asocian las guias a la nueva recepcion
     * @author JCORTEZ
     * @since  16.11.2009
     */
    public static void asignarGuias(ArrayList arrayGuias, String NumRecep) throws SQLException {
        for (int i = 0; i < arrayGuias.size(); i++) {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(FarmaVariables.vIdUsu);
            parametros.add(NumRecep);
            parametros.add(((String)((ArrayList)arrayGuias.get(i)).get(2)).trim()); //NumNotaEst
            parametros.add(((String)((ArrayList)arrayGuias.get(i)).get(0)).trim()); //numGuia
            parametros.add(((String)((ArrayList)arrayGuias.get(i)).get(1)).trim()); //numEntrega
            parametros.add(new Integer(((String)((ArrayList)arrayGuias.get(i)).get(3)).trim())); //Sec
            log.debug("parametros " + parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null,
                                                     "PTOVENTA_RECEP_CIEGA_JC.RECEP_P_AGREGA_GUIAS_RECEPCION(?,?,?,?,?,?,?,?)",
                                                     parametros, false);
            log.debug("INGRESO EXITOSO...................................");
        }
    }

    /**
     * Se lista guias asociadas
     * @author JCORTEZ
     * @since 16.11.2009
     */
    public static void getListaDetGuiasEntrega(FarmaTableModel pTableModel, String numEntrega) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numEntrega.trim());
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_OBTIENE_GUIAS_RECEP(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_OBTIENE_GUIAS_RECEP(?,?,?)",
                                                 parametros, false);

    }


    /**
     * Se valida IP para ingreso a funcionalidad
     * @author JCORTEZ
     * @since  16.11.2009
     * */
    public static String permiteIngreso() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.REPCEP_VALIDA_IP(?,?)",
                                                           parametros);
    }


    public DBRecepCiega() {
    }

    public static void getListaProdVerificacionConteo(FarmaTableModel pTableModel,
                                                      String pNroRecepcion) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_PROD(?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaCantidadProductoEnVerificacionConteo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(VariablesRecepCiega.vCantidadVerificaConteo);
        parametros.add(VariablesRecepCiega.vSecConteo);
        // parametros.add(VariablesRecepCiega.vCod_Barra);
        parametros.add(VariablesRecepCiega.vCodProd);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesRecepCiega.vLote);
        parametros.add(VariablesRecepCiega.vFechaVcto);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_CANT_VERF_CONTEO(?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_CANT_VERF_CONTEO(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static boolean verificaExistenGuiasPendientes() throws SQLException {
        boolean retorno;
        int cantidad;
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_INT_CANT_GUIAS_PEND(?,?,?):" + parametros);
        cantidad =
                FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_INT_CANT_GUIAS_PEND(?,?,?)",
                                                            parametros);
        if (cantidad == 0)
            retorno = false;
        else
            retorno = true;
        return retorno;
    }

    public static void getListaGuiasPendientes(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_GUIAS_PEND(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_GUIAS_PEND(?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaGuiasPendienteDeRecepcion() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        //log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ELI_EST_GUIAS_A_PEND(?,?,?):"+parametros);
        //FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ELI_EST_GUIAS_A_PEND(?,?,?)", parametros, false);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ELI_EST_GUIAS_A_PEND(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ELI_EST_GUIAS_A_PEND(?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaCantidadRecepPorEntrega() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_CANT_RECEP_ENTREGA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_CANT_RECEP_ENTREGA(?,?,?,?)",
                                                 parametros, false);
    }

    public static void afectaProductosDeEntregas() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_AFECTA_PRODUCTOS(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_AFECTA_PRODUCTOS(?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaIndSegundoConteo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_IND_SEG_CONTEO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_IND_SEG_CONTEO(?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaProductosFaltantes(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_PROD_FALTAN(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_PROD_FALTAN(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaProductosSobrantes(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_PROD_SOBRANT(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_PROD_SOBRANT(?,?,?)",
                                                 parametros, false);
    }

    public static String obtieneDatosVoucherDiferencias() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_VAR2_IMP_DATOS_DIFE(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_VAR2_IMP_DATOS_DIFE(?,?,?)",
                                                           parametros);
    }

    public static void enviaCorreoDeDiferencias() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        //  parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ENVIA_CORREO_DIFE(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ENVIA_CORREO_DIFE(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Inserta Auxiliar del Conteo
     * @param pSecRecepGuia
     * @param pSecAuxConteo
     * @param pCodBarra
     * @param pCant
     * @param pIndDeteriorado
     * @param pIndFueraLote
     * @param pIndNoFound
     * @throws SQLException
     */
    public static void insertAuxConteo(String pSecRecepGuia, int pSecAuxConteo, String pCodBarra, String pCant,
                                       String pIndDeteriorado, String pIndFueraLote,
                                       String pIndNoFound, String sLote, String sFechaVencimiento) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecRecepGuia);
        parametros.add(new Integer(pSecAuxConteo));
        parametros.add(pCodBarra.trim());
        parametros.add(pCant.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(VariablesRecepCiega.vNroBloque);
        parametros.add(pIndDeteriorado);
        parametros.add(pIndFueraLote);
        parametros.add(pIndNoFound);
        parametros.add(sLote);
        parametros.add(sFechaVencimiento);
        
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_JM.RECEP_P_INS_AUX_CONTEO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    public static void obtieneInfoProductoConteo(ArrayList pArrayList, String pAuxCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pAuxCodProd);
        
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_RECEP_CIEGA_JM.RECEP_F_DATOS_PROD(?,?,?)",
                                                          parametros);
    }


    public static int obtieneNroBloqueConteo(String pSecRecepCiega) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecRecepCiega);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_F_NRO_BLOQUE_CONTEO(?,?,?,?):" + parametros);
        //return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_NRO_BLOQUE_CONTEO(?,?,?,?)",parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_NRO_BLOQUE_CONTEO(?,?,?,?)",
                                                           parametros);

    }

    public static void actualizaEstadoRecep(String pNroRecep, String pEstado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(pEstado);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_P_UPD_CAB(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JM.RECEP_P_UPD_CAB(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String obtieneEstadoRecepCiega(String pSecRecepCiega) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecRecepCiega);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_P_VER_ESTADO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JM.RECEP_P_VER_ESTADO(?,?,?)",
                                                           parametros);

    }

    public static void insertConteo(String pNroRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JM.RECEP_P_INS_PROD_CONTEO(?,?,?,?,?)",
                                                 parametros, false);

    }

    public static void eliminaAuxConteo(String pNroRecep, String pNroBloque,
                                        String pSecAuxConteo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(pNroBloque);
        parametros.add(pSecAuxConteo);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_P_ELIMINA_AUX(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JM.RECEP_P_ELIMINA_AUX(?,?,?,?,?)",
                                                 parametros, false);

    }

    public static void actualizaAuxConteo(String pNroRecep, String pNroBloque, String pSecAuxConteo,
                                          String pCantidad, String sLote, String sFechaVencimiento) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(pNroBloque);
        parametros.add(pSecAuxConteo);
        parametros.add(pCantidad);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(sLote);
        parametros.add(sFechaVencimiento);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_P_UPD_AUX_CONTEO(?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_JM.RECEP_P_UPD_AUX_CONTEO(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static void obtieneListaPrimerConteo(FarmaTableModel pTableModel, String pNroRecep,
                                                String pNroBloque) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(pNroBloque);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_F_CUR_LIS_PRIMER_CONTEO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JM.RECEP_F_CUR_LIS_PRIMER_CONTEO(?,?,?,?)",
                                                 parametros, false);        
    }

    /**
     * Obtiene indicador de opcion deacuerdo al rl del usuario
     * @author JCHAVEZ
     * @since  16.11.2009
     * */
    public static String verificaRolUsuario(String SecUsu, String CodRol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRol);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_BOOL_VERIFICA_ROL_USU(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_BOOL_VERIFICA_ROL_USU(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Verifica el IP para realizar la verificacion de conteo
     * @author JCHAVEZ
     * @since  16.11.2009
     * */
    public static String verificaIPVeriricarConteo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_INDSEGCONTEO_X_IP(?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_INDSEGCONTEO_X_IP(?,?)",
                                                           parametros);
    }

    public static String obtieneCodigoProductoBarra(String pCodBarra) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodBarra);
        log.debug("invocando  a PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?)", parametros);
    }

    public static void enviaCorreoCodBarraNoHallados() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        //parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_P_ENVIA_CORREO_CONTEO(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ENVIA_CORREO_CONTEO(?,?,?)",
                                                 parametros, false);
    }


    public static void enviaErrorCorreoPorDB(String pMensaje, String pNroRecepcion) {
        //JMIRANDA 27/11/09 envia via email correo cod barra no Encontrado
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesRecepCiega.vDestEmailCodBarraNoFound, //destinatario
                //"JMIRANDA",
                "Código de barra No Encontrado en conteo de Recepción Ciega. ", //titulo
                "Alerta Código de Barra no encontrado. ", "Datos de Conteo: <br>" +
                //"Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                "Nro. Recepción : " + pNroRecepcion + "<br>" + "Código Barra: " + VariablesRecepCiega.vLastCodBarra +
                "<br>" + "Usuario : " + FarmaVariables.vIdUsu + "<br>" + "IP : " + FarmaVariables.vIpPc + "<br>",
                //ConstantsCaja.EMAIL_DESTINATARIO_CC_ERROR_IMPRESION
                "");
        log.debug("Envía Alerta por Código de Barra NO FOUND. \n" +
                pNroRecepcion);

    }

    public static String getDestinatarioCodBarraNoHallado() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_F_EMAIL_CB_NO_HALLADO:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_EMAIL_CB_NO_HALLADO",
                                                              parametros);
    }

    public static String verificaRolUsuarioRecep(String SecUsu, String CodRol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRol);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_BOOL_VERIFICA_ROL_USU(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_VERIFICA_ROL_USU(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene información de un producto
     * @author JCHAVEZ
     * @since  27.11.2009
     * */
    public static void obtieneInfoProducto(ArrayList pArrayList, String pCodProducto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProducto);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_DATOS_PRODUCTO(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_DATOS_PRODUCTO(?,?,?)",
                                                          parametros);
    }

    /**
     * Verifica si existe stock disponible para poder realizar la transferencia
     * @author JCHAVEZ
     * @since  27.11.2009
     * */
    public static boolean verificaStockDisponible(String pCodProducto, String pCantidad) throws SQLException {
        String vResultado = "";
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(pCodProducto);
        parametros.add(pCantidad);
        log.debug("invocando  a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_VERIFICA_STOCK(?,?,?,?,?):" + parametros);
        vResultado =
                FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_VERIFICA_STOCK(?,?,?,?,?)",
                                                            parametros);
        if (vResultado.equalsIgnoreCase("S"))
            return true;
        return false;
    }

    public static void getListaImpresorasDisp(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_IMPRESORAS(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_IMPRESORAS(?,?)", parametros,
                                                 false);
    }

    public static String validaRegistroAuxConteo(String pNroRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        log.debug("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_VERIF_AUX_PROD(?,?,?)");
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_VERIF_AUX_PROD(?,?,?)",
                                                           parametros);
    }

    public static String obtenerSegConteo() throws SQLException {
        return obtenerSegConteo(VariablesRecepCiega.vNro_Recepcion);
    }

    /**
     * Lista bultos por producto
     * @author ERIOS
     * @since 09.06.2015
     * @param pTableModel
     * @param pNumeroRecepcion
     * @param pCodProd
     * @throws SQLException
     */
    public static void getListaBultosProd(FarmaTableModel pTableModel, String pNumeroRecepcion, String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumeroRecepcion);
        parametros.add(pCodProd);
        log.debug("PTOVENTA_RECEP_CIEGA_JCG.GET_LISTA_BULTOS(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_RECEP_CIEGA_JCG.GET_LISTA_BULTOS(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author DUBILLUZ
     * @since  07.12.2009
     * @return
     * @throws SQLException
     */
    public static String isValidoIpConteo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_VAR2_IP_CONTEO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_VAR2_IP_CONTEO(?,?,?)",
                                                           parametros);
    }

    /**
     * Se bloqueara el estado de la recepcion
     * @author dubilluz
     * @since  07.12.2009
     * @param pNumRecepcion
     * @throws SQLException
     */
    public static void bloqueoEstado(String pNumRecepcion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecepcion.trim());
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_P_BLOQUEO_RECEPCION(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JM.RECEP_P_BLOQUEO_RECEPCION(?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerSegConteo(String pNumRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecep.trim());
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_INDVERFCONTEO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_INDVERFCONTEO(?,?,?)",
                                                           parametros);
    }

    public static void actualizaIndSegundoConteoParametro(String pNumRecep, String pIndicador) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecep.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pIndicador.trim());
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_IND_SEG_PARAM(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_IND_SEG_PARAM(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author JCHAVEZ
     * @since  09.12.2009
     * @return
     * @throws SQLException
     */
    public static boolean existeProducto(String pCodProducto) throws SQLException {
        String existe = "N";
        boolean resultado;
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProducto);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_EXISTE_PRODUCTO(?,?)" + parametros);
        existe =
                FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_EXISTE_PRODUCTO(?,?)",
                                                            parametros);

        if (existe.equalsIgnoreCase("S")) {
            resultado = true;
        } else {
            resultado = false;
        }

        return resultado;
    }

    /**
     * @author JCHAVEZ
     * @since  09.12.2009
     * @return
     * @throws SQLException
     */
    public static void getDatosMatriz(ArrayList array) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_MATRIZ(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                                                          "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CUR_LISTA_MATRIZ(?)",
                                                          parametros);
    }

    /**
     * @author JCHAVEZ
     * @since  09.12.2009
     * @return
     * @throws SQLException
     */
    public static void rellenaConCerosCantNoIngresada(String pCodProducto, String pSecProducto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(pSecProducto);
        parametros.add(pCodProducto);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_COMPLETA_CON_CEROS(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_COMPLETA_CON_CEROS(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author JCHAVEZ
     * @since  09.12.2009
     * @return
     * @throws SQLException
     */
    public static void insertaDetalleTransferencia(String pCodProd, String pCantidad, String pFechaVcto, String pLote,
                                                   String pNumNotaEs) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(pCodProd);
        parametros.add(new Integer(pCantidad));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pFechaVcto);
        parametros.add(pLote);
        parametros.add(pNumNotaEs);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_CANT_RECEPCIONADA(?,?,?,?,?,?,?,?,?):" +
                  parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_JCG.RECEP_P_ACT_CANT_RECEPCIONADA(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se lista los productos dentro de la recepción que se va a escoger para transferir.
     * @author JMIRANDA
     * @since 07.01.2010
     */
    public static void getListaProductosTransf(FarmaTableModel pTableModel, String pNumRecepcion) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecepcion.trim());
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_LISTA_PROD(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_LISTA_PROD(?,?,?)",
                                                 parametros, true);
    }

    /**
     * Obtiene mensaje de advertencia en la pantalla de asociar entregas
     * @author JMIRANDA
     * @since 07.01.2010
     */
    public static String getMensajePendientes() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_F_GET_MSG_PEND:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_GET_MSG_PEND",
                                                              parametros);
    }
    //JMIRANDA 02.02.10

    public static String getIndLimiteTransf(String pNumRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecep.trim());
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_GET_LIM_TRANSF(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_GET_LIM_TRANSF(?,?,?)",
                                                           parametros);
    }
    //JMIRANDA 11.02.10

    public static String getIndFechaVencTransf(String pCodProd, String pFechaVenc) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(pFechaVenc);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_LIM_FECHA_CANJE(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_LIM_FECHA_CANJE(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Guarda Datos Transportistas
     * @author JMIRANDA
     * @since  16.03.2009
     */
    public static String ingresaDatosTrans(String pCantGuias) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pCantGuias));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesRecepCiega.vNombreTrans);
        parametros.add(VariablesRecepCiega.vPlacaUnidTrans);
        parametros.add(new Integer(VariablesRecepCiega.vCantBultos));
        //parametros.add(new Integer(VariablesRecepCiega.vCantPrecintos));
        // AAMPUERO 14.04.2014
        parametros.add(new Integer(VariablesRecepCiega.vCantBandejas));
        //
        parametros.add(VariablesRecepCiega.vGlosa);
        parametros.add(FarmaVariables.vNuSecUsu);
        // AAMPUERO 14.04.2014 AÑADE ENVIO DE CAMPO  vCantBandejas
        log.debug("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_INS_TRANSPORTISTA(?,?,?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_INS_TRANSPORTISTA(?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
        //
    }

    //JMIRANDA 17.03.2010

    public static void getListaTransp(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_TRANSP(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_TRANSP(?,?)",
                                                 parametros, false);

    }

    //JMIRANDA 17.03.2010

    public static void getListaTranspFecha(FarmaTableModel pTableModel, String pFechaIni,
                                           String pFechaFin) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaIni);
        parametros.add(pFechaFin);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_TRANSP_RANGO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_LISTA_TRANSP_RANGO(?,?,?,?)",
                                                 parametros, false);

    }
    //JMIRANDA IMPRESION VOUCHER TRANSPORTISTA

    public static String getDatosVoucherTransportista(String pNroRecepcion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_VAR2_IMP_VOUCHER(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_VAR2_IMP_VOUCHER(?,?,?)",
                                                           parametros);
    }

    public static int getCantGuias(String pNroRecepcion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_CANT_GUIAS(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_CANT_GUIAS(?,?,?)",
                                                           parametros);
    }

    public static String desasociaEntrega(String pNroRecepcion, String pNroEntrega) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        parametros.add(pNroEntrega);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_DESASOCIA_ENTREGA(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_DESASOCIA_ENTREGA(?,?,?,?)",
                                                           parametros);

    }

    public static int getMaxProdVerificacion() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_MAX_PROD_VERIF:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_MAX_PROD_VERIF",
                                                           parametros);
    }

    public static String getIndLoteValido(String pNroRecepcion, String pCodProd, String pLote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        parametros.add(pCodProd);
        parametros.add(pLote);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_TIENE_LOTE_SAP(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_TIENE_LOTE_SAP(?,?,?,?,?)",
                                                           parametros);

    }

    public static String getIndNoTieneFechaSap(String pNroRecepcion, String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_NO_TIENE_FECHA_SAP(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_NO_TIENE_FECHA_SAP(?,?,?,?)",
                                                           parametros);

    }

    public static String getIndFechaCanjeProd(String pCodProd, String pFecha, String pLote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(pFecha);
        parametros.add(pLote);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_FECHA_CANJE_PROD(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.RECEP_F_CHAR_FECHA_CANJE_PROD(?,?,?,?,?)",
                                                           parametros);

    }

    public static String getIndHabDatosTransp() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_IND_HAB_TRANSP:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_IND_HAB_TRANSP",
                                                           parametros);

    }

    /**
     * Inserta la recepcion adicionalmente con el codigo de empresa de transporte para recepcion ciega
     * @author ASOSA
     * @since 06.04.2010
     * @param pCantGuias
     * @param codTransp
     * @return
     * @throws SQLException
     */
    public static String ingresaDatosTrans_02(String pCantGuias, String codTransp, String nroHojaRes,
                                              //ASOSA - 24.07.2014
        String codValija, //ASOSA - 25.07.2014
        ArrayList aBandejas) //ASOSA - 25.07.2014
        throws SQLException {

        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        parametros.put("cCodLocal", FarmaVariables.vCodLocal);
        parametros.put("cCantGuias", new Integer(pCantGuias));

        parametros.put("cIdUsu_in", FarmaVariables.vIdUsu);
        parametros.put("cNombTransp", VariablesRecepCiega.vNombreTrans);
        parametros.put("cPlaca", VariablesRecepCiega.vPlacaUnidTrans);

        parametros.put("nCantBultos", new Integer(VariablesRecepCiega.vCantBultos));
        parametros.put("nCantBandejas", new Integer(VariablesRecepCiega.vCantBandejas));
        parametros.put("cGlosa", VariablesRecepCiega.vGlosa);

        parametros.put("cSecUsu_in", FarmaVariables.vNuSecUsu);
        parametros.put("cCodTransp", codTransp);
        parametros.put("cNroHojaRes", nroHojaRes); //ASOSA - 24.07.2014

        parametros.put("cCodValija", codValija); //ASOSA - 24.07.2014

        Connection conn = FarmaConnection.getConnection();
        ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);

        ARRAY bandejas = new ARRAY(desc, conn, aBandejas.toArray());
        parametros.put("aBandejas", bandejas);

        String sql = "PTOVENTA_RECEP_CIEGA_AS.RECEP_F_INS_TRANSPORTISTA(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        CallableStatement stmt;
        Connection cnx = FarmaConnection.getConnection();
        stmt = cnx.prepareCall("{ call ? := " + sql + " }");

        stmt.registerOutParameter(1, OracleTypes.VARCHAR);

        stmt.setString(2, (String)parametros.get("cCodGrupoCia_in"));
        stmt.setString(3, (String)parametros.get("cCodLocal"));
        stmt.setInt(4, (Integer)parametros.get("cCantGuias"));

        stmt.setString(5, (String)parametros.get("cIdUsu_in"));
        stmt.setString(6, (String)parametros.get("cNombTransp"));
        stmt.setString(7, (String)parametros.get("cPlaca"));

        stmt.setInt(8, (Integer)parametros.get("nCantBultos"));
        stmt.setInt(9, (Integer)parametros.get("nCantBandejas"));
        stmt.setString(10, (String)parametros.get("cGlosa"));

        stmt.setString(11, (String)parametros.get("cSecUsu_in"));
        stmt.setString(12, (String)parametros.get("cCodTransp"));
        stmt.setString(13, (String)parametros.get("cNroHojaRes"));

        stmt.setString(14, (String)parametros.get("cCodValija"));
        stmt.setArray(15, (oracle.sql.ARRAY)parametros.get("aBandejas"));


        //ejecutando el estoredProcedure
        stmt.execute();
        //Obteniendo el cursor con el resultado

        String resultado = stmt.getString(1);

        log.debug("valor_out>>" + resultado);

        stmt.close();

        return resultado;
    }

    /**
     * Obtiene datos para la impresion de
     * @author ASOSA
     * @since 06.04.2010
     * @param pNroRecepcion
     * @return
     * @throws SQLException
     */
    public static String getDatosVoucherTransportista_02(String pNroRecepcion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_AS.RECEP_F_VAR2_IMP_VOUCHER(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_AS.RECEP_F_VAR2_IMP_VOUCHER(?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene la cantidad de veces que imprimira el voucher transportistas
     * @author JQUISPE
     * @since 05.05.2010
     * @return NumImpresiones
     * @throws SQLException
     */
    public static String getNumeroImpresiones() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_AS.RECEP_F_GET_NUM_IMPRES:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_AS.RECEP_F_GET_NUM_IMPRES",
                                                           parametros);
    }

    public static void afectarEntregasPendientesBD() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_AFECTA_ENT_PENDIENTES(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_AFECTA_ENT_PENDIENTES(?,?,?,?)",
                                                 parametros, false);
    }

    public static void afectarEntregasSobrantesBD() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vNro_Recepcion);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.RECEP_AFECTA_SOBRANTES(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JCG.RECEP_AFECTA_SOBRANTES(?,?,?,?)",
                                                 parametros, false);
    }


    public static String getIndAfectaSobrantesFaltantesNuevo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JCG.INV_F_GET_IND_SOB_AFECTA(?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.INV_F_GET_IND_SOB_AFECTA(?)",
                                                           parametros);
    }


    /**
     * Elimina la recepcion
     * @author DUBILLUZ
     * @since  03.2013
     * @param pNumEntrrega
     * @throws SQLException
     */
    public static void eliminaRecepcion(String pCodigoRecepcion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodigoRecepcion.trim());
        log.debug("Elimina Recepcion.");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_JC.RECEP_P_ELIMINA_CAB_RECEP(?,?,?,?)",
                                                 parametros, false);
    }

    public static String getValidaPermiteIngresar(String pNroRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_PERMITE_INGR(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JC.RECEP_F_PERMITE_INGR(?,?,?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Destinatario ingreso de transportista
     * @author ERIOS
     * @since 2.3.3
     * @return
     * @throws SQLException
     */
    public static String getDestinatarioIngresoTransportista() throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_EMAIL_ING_TRANSP" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_EMAIL_ING_TRANSP",
                                                              parametros);
    }

    /**
     * Devuelve el numero de DT mas la cantidad de bultos que contiene.
     * @author ASOSA
     * @since 18.07.2014
     * @param pTableModel
     * @param pHojaResumen
     * @throws SQLException
     */
    public static void obtenerDocTransporte(FarmaTableModel pTableModel, String pHojaResumen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pHojaResumen);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.RECEP_F_GET_DOC_TRANSPORTE" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_RECEP_CIEGA_AS.RECEP_F_GET_DOC_TRANSPORTE(?)",
                                                 parametros, false);
    }

    /**
     * Devuelve "S" si existe la bandeja asociada a la hoja de resumen (Documento de Transporte), o "N" si no.
     * @author ASOSA
     * @since 18.07.2014
     * @param pHojaResumen
     * @param pNroBandeja
     * @return
     * @throws SQLException
     */
    public static String buscarBandejaHojaResumen(String pHojaResumen, String pNroBandeja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pHojaResumen);
        parametros.add(pNroBandeja);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.RECEP_F_GET_BANDEJA_HR" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.RECEP_F_GET_BANDEJA_HR(?,?)",
                                                              parametros);
    }

    /**
     * Devuelve "S" si existe grabado previamente el numero de hoja de resumen, o "N" si no.
     * @author ASOSA
     * @since 13.08.2014
     * @param pHojaResumen
     * @return
     * @throws SQLException
     */
    public static String buscarHojaResumenGrabada(String pHojaResumen,boolean vIndNvo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pHojaResumen);
        if(vIndNvo)
            parametros.add("S");
        else
            parametros.add("N");
        log.debug("PTOVENTA_RECEP_CIEGA_AS.RECEP_F_EXISTS_HOJA_RES" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.RECEP_F_EXISTS_HOJA_RES(?,?)",
                                                              parametros);
    }

    /**
     * Obtiene lista del reporte de Acta de recepción de productos
     * @author RHERRERA
     * @since 24.11.2014
     * @param numero de recepción
     * @return
     * @throws SQLException
     */
    public static void getListActaRecepcionMercaderia(FarmaTableModel pTableModel, String pNumeroRecepcion,
                                                      String cNumEntrega) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumeroRecepcion);
        parametros.add(cNumEntrega);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JC.RECEP_F_DATOS_ACTA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_RECEP_CIEGA_JC.RECEP_F_DATOS_ACTA(?,?,?,?)",
                                                 parametros, false);
    }
    
    public static String ingresaDatosTransportistaNueva(
                String pCantGuias, 
                String codTransp, 
                String nroHojaRes,
                String codValija, 
                ArrayList aBandejas) 
        throws SQLException {
        
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        parametros.put("cCodLocal", FarmaVariables.vCodLocal);
        parametros.put("cCantGuias", new Integer(pCantGuias));
        parametros.put("cIdUsu_in", FarmaVariables.vIdUsu);
        parametros.put("cNombTransp", VariablesRecepCiega.vNombreTrans);
        parametros.put("cPlaca", VariablesRecepCiega.vPlacaUnidTrans);
        parametros.put("nCantBultos", new Integer(VariablesRecepCiega.vCantBultos));
        parametros.put("nCantBandejas", new Integer(VariablesRecepCiega.vCantBandejas));
        parametros.put("cGlosa", VariablesRecepCiega.vGlosa);
        parametros.put("cSecUsu_in", FarmaVariables.vNuSecUsu);
        parametros.put("cCodTransp", codTransp);
        parametros.put("cNroHojaRes", nroHojaRes);
        parametros.put("cCodValija", codValija); 
        Connection conn = FarmaConnection.getConnection();
        ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);
        ARRAY bandejas = new ARRAY(desc, conn, aBandejas.toArray());
        parametros.put("aBandejas", bandejas);
        String sql = "PTOVENTA_RECEP_CIEGA_AS.RECEP_F_INS_TRANSPORTISTA(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        CallableStatement stmt;
        Connection cnx = FarmaConnection.getConnection();
        stmt = cnx.prepareCall("{ call ? := " + sql + " }");

        stmt.registerOutParameter(1, OracleTypes.VARCHAR);

        stmt.setString(2, (String)parametros.get("cCodGrupoCia_in"));
        stmt.setString(3, (String)parametros.get("cCodLocal"));
        stmt.setInt(4, (Integer)parametros.get("cCantGuias"));
        stmt.setString(5, (String)parametros.get("cIdUsu_in"));
        stmt.setString(6, (String)parametros.get("cNombTransp"));
        stmt.setString(7, (String)parametros.get("cPlaca"));
        stmt.setInt(8, (Integer)parametros.get("nCantBultos"));
        stmt.setInt(9, (Integer)parametros.get("nCantBandejas"));
        stmt.setString(10, (String)parametros.get("cGlosa"));
        stmt.setString(11, (String)parametros.get("cSecUsu_in"));
        stmt.setString(12, (String)parametros.get("cCodTransp"));
        stmt.setString(13, (String)parametros.get("cNroHojaRes"));
        stmt.setString(14, (String)parametros.get("cCodValija"));
        stmt.setArray(15, (oracle.sql.ARRAY)parametros.get("aBandejas"));

        //ejecutando el estoredProcedure
        stmt.execute();
        //Obteniendo el cursor con el resultado

        String resultado = stmt.getString(1);

        log.debug("valor_out>>" + resultado);

        stmt.close();

        return resultado;
    }

    public static boolean getIndActivoNuevoRecepMercaderia () throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IND_ACT_NVO_RCIEGA " + parametros);
        String pResultado = FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IND_ACT_NVO_RCIEGA",
                                                              parametros);
        if(pResultado.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    public static boolean getIndPermiteFaltanteBandeja () throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IND_PERMITE_FALTANTE " + parametros);
        String pResultado = FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IND_PERMITE_FALTANTE",
                                                              parametros);
        if(pResultado.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }
    
    public static boolean getIndPermiteSobranteBandeja () throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IND_PERMITE_SOBRANTE " + parametros);
        String pResultado = FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IND_PERMITE_SOBRANTE",
                                                              parametros);
        if(pResultado.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }    
    

    public static void getListaMotivoNODevolucionBandeja(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_AS.F_CUR_MOTV_NO_DEV_BANDEJA:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_MOTV_NO_DEV_BANDEJA",
                                                 parametros, false);

    }    
    
    public static String isExisteHojaMaestro(String pHojaResumen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pHojaResumen);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_EXISTE_HOJA_MAESTRO" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_EXISTE_HOJA_MAESTRO(?,?)",
                                                              parametros);
    }
    
    
    public static String vDatosHojaResumen(String pHojaResumen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pHojaResumen);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_DATA_HOJA_RES" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_DATA_HOJA_RES(?,?)",
                                                              parametros);
    }

    public static void getListaBandejaIn(ArrayList pTableModel,String pHojaResumen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pHojaResumen);        
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_AS.F_CUR_LIST_BANDEJA_IN(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_LIST_BANDEJA_IN(?,?)",
                                                 parametros);
    }    

    public static void getListaBandejaOut(ArrayList pTableModel,String pHojaResumen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pHojaResumen);        
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_AS.F_CUR_LIST_BANDEJA_OUT(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_LIST_BANDEJA_OUT(?,?)",
                                                 parametros);
    }    
    
    public static String saveNvoRecepTransportista(
                String pCantGuias, 
                String codTransp, 
                String cNameTrans,
                String nroHojaRes,
                String codValija, 
                String cMotNoDevolucion_in,
                ArrayList bandejasRecepcionadas,
                ArrayList bandejasDevueltas,
                boolean isModificar,
                boolean isNuevo,
                String pNumRecep,
                ArrayList borraRecep,
                ArrayList borraDevol,
                ArrayList LazerRecep,
                ArrayList LazerDevol,
                boolean vIdHojaLazer,
                boolean isDevolver
                ) 
        throws SQLException {
        
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        parametros.put("cCodLocal", FarmaVariables.vCodLocal);
        parametros.put("cCantGuias", new Integer(pCantGuias));
        parametros.put("cIdUsu_in", FarmaVariables.vIdUsu);
        parametros.put("cNombTransp", VariablesRecepCiega.vNombreTrans);
        parametros.put("cPlaca", VariablesRecepCiega.vPlacaUnidTrans);
        parametros.put("nCantBultos", 0);
        parametros.put("nCantBandejas", 0);
        parametros.put("cGlosa", VariablesRecepCiega.vGlosa);
        parametros.put("cSecUsu_in", FarmaVariables.vNuSecUsu);
        parametros.put("cCodTransp", codTransp);
        parametros.put("cNameTrans", cNameTrans);
        parametros.put("cNroHojaRes", nroHojaRes);
        parametros.put("cCodValija", codValija); 
        parametros.put("cMotNoDevolucion_in", cMotNoDevolucion_in); 
        Connection conn = FarmaConnection.getConnection();
        ArrayDescriptor desc = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);
        ARRAY bandejas = new ARRAY(desc, conn, bandejasRecepcionadas.toArray());
        parametros.put("aBandejas", bandejas);

        ArrayDescriptor desc2 = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);
        ARRAY bandejas2 = new ARRAY(desc2, conn, bandejasDevueltas.toArray());
        parametros.put("aBandejasDev", bandejas2);
        
        if(isModificar)
            parametros.put("isModificar", "S");
        else
            parametros.put("isModificar", "N");
        
        if(isNuevo)
            parametros.put("isNuevo", "S");
        else
            parametros.put("isNuevo", "N");
        
        parametros.put("pNumRecep",pNumRecep);
        
        ArrayDescriptor bRecep = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);
        ARRAY borraR = new ARRAY(bRecep, conn, borraRecep.toArray());
        parametros.put("aBandejasBorraRecep", borraR);
        
        ArrayDescriptor bDevol = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);
        ARRAY borraD = new ARRAY(bDevol, conn, borraDevol.toArray());
        parametros.put("aBandejasBorraDevol", borraD);
        
        ArrayDescriptor bRecepLazer = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);
        ARRAY borraRLazer = new ARRAY(bRecepLazer, conn, LazerRecep.toArray());
        parametros.put("aBandejasRecepLazer", borraRLazer);
        
        ArrayDescriptor bDevolLazer = ArrayDescriptor.createDescriptor("PTOVENTA.VARCHAR2_TABLE", conn);
        ARRAY borraDLazer = new ARRAY(bDevolLazer, conn, LazerDevol.toArray());
        parametros.put("aBandejasDevolLazer", borraDLazer);
        
        if(vIdHojaLazer)
            parametros.put("isHojaLazer", "S");
        else
            parametros.put("isHojaLazer", "N");
        
        if(isDevolver)
            parametros.put("isDevolver", "S");
        else
            parametros.put("isDevolver", "N");
        
        String sql = "PTOVENTA_RECEP_CIEGA_AS.F_VAR_SAVE_NVO_RECEP_TRANS(?,?,?,?,?," +
                                                                        "?,?,?,?,?," +
                                                                        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        CallableStatement stmt;
        Connection cnx = FarmaConnection.getConnection();
        stmt = cnx.prepareCall("{ call ? := " + sql + " }");

        stmt.registerOutParameter(1, OracleTypes.VARCHAR);

        stmt.setString(2, (String)parametros.get("cCodGrupoCia_in"));
        stmt.setString(3, (String)parametros.get("cCodLocal"));
        stmt.setInt(4, (Integer)parametros.get("cCantGuias"));
        stmt.setString(5, (String)parametros.get("cIdUsu_in"));
        stmt.setString(6, (String)parametros.get("cNombTransp"));
        stmt.setString(7, (String)parametros.get("cPlaca"));
        stmt.setInt(8, (Integer)parametros.get("nCantBultos"));
        stmt.setInt(9, (Integer)parametros.get("nCantBandejas"));
        stmt.setString(10, (String)parametros.get("cGlosa"));
        stmt.setString(11, (String)parametros.get("cSecUsu_in"));
        stmt.setString(12, (String)parametros.get("cCodTransp"));
        stmt.setString(13, (String)parametros.get("cNameTrans"));
        stmt.setString(14, (String)parametros.get("cNroHojaRes"));
        stmt.setString(15, (String)parametros.get("cCodValija"));
        stmt.setString(16, (String)parametros.get("cMotNoDevolucion_in"));
        stmt.setArray(17, (oracle.sql.ARRAY)parametros.get("aBandejas"));
        stmt.setArray(18, (oracle.sql.ARRAY)parametros.get("aBandejasDev"));
        stmt.setString(19, (String)parametros.get("isModificar"));
        stmt.setString(20, (String)parametros.get("isNuevo"));        
        stmt.setString(21, (String)parametros.get("pNumRecep"));
        
        stmt.setArray(22, (oracle.sql.ARRAY)parametros.get("aBandejasBorraRecep"));
        stmt.setArray(23, (oracle.sql.ARRAY)parametros.get("aBandejasBorraDevol"));
        
        stmt.setArray(24, (oracle.sql.ARRAY)parametros.get("aBandejasRecepLazer"));
        stmt.setArray(25, (oracle.sql.ARRAY)parametros.get("aBandejasDevolLazer"));
        
        stmt.setString(26, (String)parametros.get("isHojaLazer"));
        stmt.setString(27, (String)parametros.get("isDevolver"));
        
        //ejecutando el estoredProcedure
        stmt.execute();
        //Obteniendo el cursor con el resultado

        String resultado = stmt.getString(1);

        log.debug("valor_out>>" + resultado);

        stmt.close();

        return resultado;
    }    
        
        
    public static void getListaBandejas(FarmaTableModel pTableModel,String pIni,String pFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pIni.trim());
        parametros.add(pFin.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_RECEP(?,?,?,?)", parametros,
                                                 false);
    }

    public static void saveParaDevolver(String nBandeja, String nRecep)  throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nBandeja.trim());
        parametros.add(" ");
        parametros.add(nRecep.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_AS.P_SAVE_PARA_DEVOLVER(?,?,?,?,?)", parametros,
                                                 false);
    }


    public static void saveParaRevertir(String nBandeja, String nRecep)  throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nBandeja.trim());
        parametros.add(" ");
        parametros.add(nRecep.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_AS.P_SAVE_PARA_REVERTIR(?,?,?,?,?)", parametros,
                                                 false);
    }    
    
    public static String getMsjAccionHojaExistente(String pHojaResumen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(pHojaResumen);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_ACCION_HOJA_EXISTE" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_ACCION_HOJA_EXISTE(?,?,?)",
                                                              parametros);
    }

    public static void getListaBandejasRecep(ArrayList vLista,String pNumRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecep.trim());
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_RECEP" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_RECEP(?,?,?)", parametros);
    }    

    public static void getListaBandejasDevol(ArrayList vLista,String pNumRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecep.trim());
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_DEVOL" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_DEVOL(?,?,?)", parametros);
    }        
    
    
    public static String existeBandejaHoja(String pHojaResumen, String pNroBandeja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pHojaResumen);
        parametros.add(pNroBandeja);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_EXISTE_BANDEJA_RES" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_EXISTE_BANDEJA_RES(?,?,?,?)",
                                                              parametros);
    }

    public static void getListaBandejasDefault(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_RECEP_DEFAULT(?,?)", parametros,
                                                 false);
    }    

    public static void getListaBandejasPorDevolver(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_POR_DEVOL(?,?)", parametros,
                                                 false);
    }
    
    public static boolean existePorDevolver() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        ArrayList vLista = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,
                                                 "PTOVENTA_RECEP_CIEGA_AS.F_CUR_BAND_POR_DEVOL(?,?)", parametros);
        if(vLista.size()>0) return true;
        else return false;
    }

    static String visValidoHojaRes(String pHojaResumen)  throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pHojaResumen);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IS_VALIDO_HOJA" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IS_VALIDO_HOJA(?,?,?)",
                                                              parametros);
    }
    
    public static boolean isValidoBandeja(String pBandeja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pBandeja);
        String pRpt = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IS_VALIDO_BANDEJA(?,?,?)", parametros);
        if(pRpt.trim().equalsIgnoreCase("S")) return true;
        else return false;
    }

    public static void creaBandejaExistePorDevolver(String pBandeja)throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pBandeja);
        parametros.add(FarmaConstants.INDICADOR_S);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_AS.P_SAVE_BANDEJA_POR_DEVOLVER(?,?,?,?)", parametros,
                                                 false);
    }
    
    public static void creaBandejaNoExistePorDevolver(String pBandeja)throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pBandeja);
        parametros.add(FarmaConstants.INDICADOR_N);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_AS.P_SAVE_BANDEJA_POR_DEVOLVER(?,?,?,?)", parametros,
                                                 false);
    }
    
    
    public static boolean isTipoCorrectoBandejaDevol(String pBandeja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pBandeja);
        log.debug("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IS_VALIDO_TIPO_DEVOL(?,?,?)"+parametros);
        String pRpt = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_AS.F_VAR_IS_VALIDO_TIPO_DEVOL(?,?,?)", parametros);
        if(pRpt.trim().equalsIgnoreCase("S")) return true;
        else return false;
    }

        
    //INI ASOSA - 05.06.2015 - RCIEGAM
    /**
     * Actualiza y devolver correlativo de bulto.
     * @author ASOSA
     * @since 05.06.2015
     * @return
     * @throws SQLException
     */
    public static String getCorrBulto(String nroRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);   
        parametros.add(nroRecep);
        log.debug("PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_UPD_NUM_BULTO(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_UPD_NUM_BULTO(?,?,?,?)",
                                                              parametros);
    }
    
    /**
     * Descripcion: Insertar el auxiliar de conteo por cada producto escaneado
     * @author ASOSA
     * @since 05.06.2015
     * @param pSecRecepGuia
     * @param pSecAuxConteo
     * @param pCodBarra
     * @param pCant
     * @param pIndDeteriorado
     * @param pIndFueraLote
     * @param pIndNoFound
     * @param codBulto
     * @param corrBulto
     * @throws SQLException
     */
    public static void insertAuxConteo02(String pSecRecepGuia, int pSecAuxConteo, String pCodBarra, String pCant,
                                       String pIndDeteriorado, String pIndFueraLote,
                                       String pIndNoFound,
                                       String codBulto,
                                       String corrBulto,
                                       String indLectoraBulto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecRecepGuia);
        parametros.add(new Integer(pSecAuxConteo));
        parametros.add(pCodBarra.trim());
        parametros.add(pCant.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(VariablesRecepCiega.vNroBloque);
        parametros.add(codBulto);
        parametros.add(corrBulto);
        parametros.add(indLectoraBulto);
        parametros.add(pIndDeteriorado);
        parametros.add(pIndFueraLote);
        parametros.add(pIndNoFound);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_NEW.RECEP_P_INS_AUX_CONTEO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RECEP_CIEGA_NEW.RECEP_P_INS_AUX_CONTEO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }
    
    /**
     * Listar productos que voy escaneando
     * @author ASOSA
     * @since 08.06.2015
     * @param pTableModel
     * @param pNroRecep
     * @param pNroBloque
     * @throws SQLException
     */
    public static void obtieneListaPrimerConteo02(FarmaTableModel pTableModel, String pNroRecep,
                                                String pNroBloque) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(pNroBloque);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_CUR_LIS_PRIMER_CONTEO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_CUR_LIS_PRIMER_CONTEO(?,?,?,?)",
                                                 parametros, false);

    }
    
    /**
     * Finalizar conteo insertando los conteos de la tabla auxiliar a la final.
     * @author ASOSA
     * @since 08.06.2015
     * @param pNroRecep
     * @throws SQLException
     */
    public static void insertConteo02(String pNroRecep) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecep);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_NEW.RECEP_P_INS_PROD_CONTEO(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RECEP_CIEGA_NEW.RECEP_P_INS_PROD_CONTEO(?,?,?,?,?)",
                                                 parametros, false);
    }
    
    /**
     * Determinar si un bulto existe en una recepcion.
     * @author ASOSA
     * @since 08.06.2015
     * @param pNroRecep
     * @param pNroBulto
     * @return
     * @throws SQLException
     */
    public static String getIndBultoExists(String pNroRecep,
                                      String pNroBulto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(pNroRecep); 
        parametros.add(pNroBulto); 
        log.debug("PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_IND_BULTO_EXISTS(?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_IND_BULTO_EXISTS(?,?,?,?,?)",
                                                              parametros);
    }
    
    /**
     * Determinar si un bulto existe en una recepcion.
     * @author ASOSA
     * @since 09.06.2015
     * @param corrBulto
     * @return
     * @throws SQLException
     */
    public static List imprVouBultoCerrado(String corrBulto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(corrBulto);
        log.debug("PTOVENTA_RECEP_CIEGA_NEW.F_IMPR_VOU_BULTO_CERRADO(?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_RECEP_CIEGA_NEW.F_IMPR_VOU_BULTO_CERRADO(?,?,?,?)",
                                                               parametros);
    }
    
    public static String imprVouBultoCerrado02(String corrBulto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(corrBulto);
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_NEW.F_IMPR_VOU_BULTO_HTML(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_NEW.F_IMPR_VOU_BULTO_HTML(?,?,?,?)",
                                                           parametros);
    }
    
    /**
     * Obtener el indicador del local si se utilizara el beep de la placa o no.
     * @author ASOSA
     * @since 22.06.2015
     * @return
     * @throws SQLException
     */
    public static String getIndLocalBeepBios() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);         
        log.debug("PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_IND_LOCAL_BEEP(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_RECEP_CIEGA_NEW.RECEP_F_IND_LOCAL_BEEP(?,?,?)",
                                                              parametros);
    }
    
    //FIN ASOSA - 22.06.2015 - RCIEGAM
    
    public static String obtieneFechaVencimiento(String sLote) throws SQLException  {
        String sResultado = "";
        
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesRecepCiega.vLastCodBarra);
        parametros.add(VariablesInventario.vCodProducto.trim());
        parametros.add(sLote);        
        log.debug("invocando a PTOVENTA_RECEP_CIEGA_JM.RECEP_F_OBT_FEC_VENC(?,?,?,?,?):" + parametros);
        sResultado = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JM.RECEP_F_OBT_FEC_VENC(?,?,?,?,?)",
                                                           parametros);
        log.info(">>>>>>>> sResultado: " + sResultado);
        return (sResultado == null ? "" : sResultado);

    }

    /**
     * @author ERIOS
     * @since 03.05.2016
     * @param pNroRecepcion
     */
    public static void ingresoCuarentena(String pNroRecepcion) {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        parametros.add(FarmaVariables.vIdUsu);

        try {
            FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CUARENTENA.P_INGRESO_KARDEX_RECEP(?,?,?,?)",
                                                 parametros, false);
        } catch (SQLException e) {
            log.error("",e);
        }
    }

    /**
     * @author ERIOS
     * @since 03.05.2016
     * @param pNroRecepcion
     */
    public static void salidaCuarentena(String pNroRecepcion) {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        parametros.add(FarmaVariables.vIdUsu);

        try {
            FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CUARENTENA.P_SALIDA_KARDEX_RECEP(?,?,?,?)",
                                                 parametros, false);
        } catch (SQLException e) {
            log.error("",e);
        }
    }
}


