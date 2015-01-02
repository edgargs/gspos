package mifarma.ptoventa.administracion.fondoSencillo.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBFondoSencillo {
    private static final Logger log = LoggerFactory.getLogger(DBFondoSencillo.class);

    private static ArrayList parametros;

    public DBFondoSencillo() {
    }


    public static void getListaCajerosDisponibles(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_CUR_LISTA_CAJ_DISP(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_CUR_LISTA_CAJ_DISP(?,?)",
                                                 parametros, false);
    }

    public static void getListaHistorico(FarmaTableModel pTableModel, String pTipo) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipo);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_HIST_FON(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_HIST_FON(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaHistoricoFechas(FarmaTableModel pTableModel, String pFechaIni, String pFechaFin,
                                               String pTipo) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaIni.trim());
        parametros.add(pFechaFin.trim());
        parametros.add(pTipo);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_HIST_FE(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_HIST_FE(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void insAsignacionSencillo(String pMonto, String pUsuDestino, String pUsuOrigen, String pEstado,
                                             String pIndTipoFondo, String pUsuCrea, String pIP) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));
        parametros.add(pUsuDestino);
        parametros.add(pUsuOrigen);
        parametros.add(pEstado);
        parametros.add(pIndTipoFondo);
        parametros.add(pUsuCrea);
        parametros.add(pIP);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_INS_ASIGNA(?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_INS_ASIGNA(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String getIndFondoSencilloCajero(String pSecUsu) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_TIENE_FONDO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_TIENE_FONDO(?,?,?)",
                                                           parametros);
    }

    public static void rechazaMonto(String pSecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(pSecUsu);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_UPD_RECHAZO_FONDO(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_UPD_RECHAZO_FONDO(?,?,?,?,?)",
                                                 parametros, false);

    }


    public static String aceptaMontoAsignado(String pSecUsu) throws SQLException {
        //FONDO_SEN_P_UPD_ACEPTA_FONDO
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(pSecUsu);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_UPD_ACEPTA_FONDO(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_UPD_ACEPTA_FONDO(?,?,?,?,?)",
                                                           parametros);
    }

    public static String getMontoAsignado(String pSecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_GET_MONTO_ASIGNADO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_GET_MONTO_ASIGNADO(?,?,?)",
                                                           parametros);
    }

    public static void grabaSecMovCajaFondoSencillo(String pSecUsu, String pNumCaja,
                                                    String pSecFondoSen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        parametros.add(pNumCaja);
        parametros.add(pSecFondoSen);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);

        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_UPD_MOV_CAJA(?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_UPD_MOV_CAJA(?,?,?,?,?,?,?)",
                                                 parametros, false);

    }


    public static String getIndTieneDevPendiente(String pSecUsu, String pSecMov) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu.trim());
        parametros.add(pSecMov.trim());
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_TIENE_DEV(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_TIENE_DEV(?,?,?,?)",
                                                           parametros);
    }

    public static void insDevolucionSencillo(String pMonto, String pUsuDestino, String pUsuOrigen, String pEstado,
                                             String pIndTipoFondo, String pUsuCrea, String pIP,
                                             String pSecMovCajaCierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));
        parametros.add(pUsuDestino);
        parametros.add(pUsuOrigen);
        parametros.add(pEstado);
        parametros.add(pIndTipoFondo);
        parametros.add(pUsuCrea);
        parametros.add(pIP);
        parametros.add(pSecMovCajaCierre);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_INS_DEVOL(?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_INS_DEVOL(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static String getMontoDevolver(String pSecUsu, String pMovCajaCierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        parametros.add(pMovCajaCierre);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_GET_MONTO_DEV(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_GET_MONTO_DEV(?,?,?,?)",
                                                           parametros);
    }

    public static String anulaAsignacion(String pUsuMod, String pIP, String pSecFondoSen, String pEstado,
                                         String pTipo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pUsuMod);
        parametros.add(pIP);
        parametros.add(pSecFondoSen);
        parametros.add(pEstado);
        parametros.add(pTipo);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_ANUL_ASIG(?,?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_ANUL_ASIG(?,?,?,?,?,?,?)",
                                                           parametros);

    }


    public static void updDevolucionSencillo(String pMonto, String pUsuDestino, String pUsuOrigen, String pUsuCrea,
                                             String pIP, String pSecMovCajaCierre) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));
        parametros.add(pUsuDestino);
        parametros.add(pUsuOrigen);
        parametros.add(pUsuCrea);
        parametros.add(pIP);
        parametros.add(pSecMovCajaCierre);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_UPD_DEVOL(?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_UPD_DEVOL(?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static String getDatosVoucherDevolucion(String pSecFondoSen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecFondoSen);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_VAR2_IMP_VOUCHER(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_VAR2_IMP_VOUCHER(?,?,?)",
                                                           parametros);
    }

    public static void aceptaDevolucionSencillo(String pUsuMod, String pIP,
                                                String pSecMovCajaCierre) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pUsuMod);
        parametros.add(pIP);
        parametros.add(pSecMovCajaCierre);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_ACEPTAR_DEVOL_QF(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_ACEPTAR_DEVOL_QF(?,?,?,?,?)",
                                                 parametros, false);

    }

    public static void aceptaDevolucionSencilloHis(String pUsuMod, String pIP,
                                                   String pSecFondoSen) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pUsuMod);
        parametros.add(pIP);
        parametros.add(pSecFondoSen);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_ACEPTAR_DEVOL(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_ACEPTAR_DEVOL(?,?,?,?,?)",
                                                 parametros, false);

    }

    public static String getIndValidaAdmLocal(String pSecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu.trim());
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_VAL_USU_OK(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_VAL_USU_OK(?,?,?)",
                                                           parametros);
    }

    public static String getIndHabilitadoFondo() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_HABILITADO" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_HABILITADO",
                                                           parametros);
    }

    public static String getIndTieneCajAbierta(String pSecUsuCaj) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsuCaj);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_CAJ_DISP(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_IND_CAJ_DISP(?,?,?)",
                                                           parametros);
    }

    /*FONDO_SEN_F_VAR2_IMP_VOUCHER_2*/

    public static String getDatosVoucherDevolucion2(String pSecMovCajaCierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCajaCierre);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_VAR2_IMP_VOUCHER_2(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_VAR2_IMP_VOUCHER_2(?,?,?)",
                                                           parametros);
    }

    public static void eliminaDevolucionSencillo(String pUsuElimina, String pIP,
                                                 String pSecMovCajaCierre) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pUsuElimina);
        parametros.add(pIP);
        parametros.add(pSecMovCajaCierre);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_ELIMINA_DEVOL(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_ELIMINA_DEVOL(?,?,?,?,?)",
                                                 parametros, false);

    }

    public static String getIndDevolucionAceptada(String pSecMovCajaCierre) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCajaCierre);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_FONDO_ACEPTADO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_P_FONDO_ACEPTADO(?,?,?)",
                                                           parametros);
    }


    public static String insAsignacionSencillo2(String pMonto, String pUsuDestino, String pUsuOrigen, String pEstado,
                                                String pIndTipoFondo, String pUsuCrea,
                                                String pIP) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));
        parametros.add(pUsuDestino);
        parametros.add(pUsuOrigen);
        parametros.add(pEstado);
        parametros.add(pIndTipoFondo);
        parametros.add(pUsuCrea);
        parametros.add(pIP);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_INS_ASIGNA(?,?,?,?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_INS_ASIGNA(?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }


    //JMIRANDA 03.06.2010 OBTIENE LISTADO DE CAJEROS

    public static void getListaHistoricoXCajero(FarmaTableModel pTableModel, String pTipo,
                                                String pSecUsuLocal) throws SQLException {

        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipo);
        parametros.add(pSecUsuLocal);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_H_CAJ(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_H_CAJ(?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaHistoricoFechasXCajero(FarmaTableModel pTableModel, String pFechaIni, String pFechaFin,
                                                      String pTipo, String pSecUsuCajero) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaIni.trim());
        parametros.add(pFechaFin.trim());
        parametros.add(pTipo);
        parametros.add(pSecUsuCajero);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_H_FEC_CAJ(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_H_FEC_CAJ(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaCajerosPrincipal(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_CAJ_PRIN(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_CAJ_PRIN(?,?)",
                                                 parametros, false);
    }

    /**
     * Obtiene el monto asignado para que sea devuelto por completo
     * @author ASOSA
     * @since 18.06.2010
     * @param secMovCierre
     * @return
     */
    public static String getMontoAceptado(String secMovCierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(secMovCierre.trim());
        log.debug("INVOCANDO A PTOVENTA_ADMIN_FONDO_SEN.FSEN_F_GET_MONTO_ACEP_AS: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FSEN_F_GET_MONTO_ACEP(?,?,?)",
                                                           parametros);
    }

    /**
     * Determine si abre o no abre caja en caso este activo el fondo de sencillo y si le asignaron o no sencillo
     * @author ASOSA
     * @since 20.06.2010
     * @return
     * @throws SQLException
     */
    public static String getIndOPEN_OR_NOT_OPEN() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("INVOCANDO A PTOVENTA_ADMIN_FONDO_SEN.FSEN_F_OPEN_OR_NOT_OPEN(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FSEN_F_OPEN_OR_NOT_OPEN(?,?,?)",
                                                           parametros);
    }

    /**
     * Determine si se debe dejar dar VB de cajero, en el caso este activo deberia entonces haberlo declarado como forma de pago entrega
     * @author ASOSA
     * @since 21.06.2010
     * @param secmovcajacierre
     * @return
     * @throws SQLException
     */
    public static String getDeter_neces_fpFSEN(String secmovcajacierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(secmovcajacierre.trim());
        log.debug("INVOCANDO A PTOVENTA_ADMIN_FONDO_SEN.FSEN_F_DETERMINAR_NECESIDAD(?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_FONDO_SEN.FSEN_F_DETERMINAR_NECESIDAD(?,?,?,?)",
                                                           parametros);
    }

}
