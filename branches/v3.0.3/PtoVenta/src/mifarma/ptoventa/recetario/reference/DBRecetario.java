package mifarma.ptoventa.recetario.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBRecetario {

    private static final Logger log = LoggerFactory.getLogger(DBRecetario.class);

    private static ArrayList<String> parametros;

    /**
     * Modifica el recetario magistral indicado, actualizando el numero de pedido
     * @author Luis Leiva
     * @since 22.05.2013
     */
    public static void asignarPedidoRM(String cod_grupo_cia, String cod_cia, String cod_local, String num_recetario,
                                       String num_pedido) throws SQLException {
        parametros = new ArrayList<String>();
        parametros.add(cod_grupo_cia);
        parametros.add(cod_cia);
        parametros.add(cod_local);
        parametros.add(num_recetario);
        parametros.add(num_pedido);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RCM.RCM_ASIGNA_PEDIDO_RCM(?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Obtiene el numero de recetario, si es que corresponde.
     * @author ERIOS
     * @since 30.05.2013
     * @param pNumeroPedido
     * @return
     * @throws SQLException
     */
    public static void getNumeroRecetario(String pNumeroPedido, HashMap<String, String> pResulta) throws SQLException {
        parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumeroPedido);
        String vResulta =
            FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RCM.GET_NUMERO_RECETARIO(?,?,?,?)", parametros);
        String[] dividido = vResulta.split("Ã");
        pResulta.put("NUM_RECETARIO", dividido[0].trim());
        pResulta.put("EST_RECETARIO", dividido[1].trim());
    }

    /**
     * Obtiene la trama de recetario.
     * @author ERIOS
     * @since 30.05.2013
     * @param pNumeroRecetario
     * @return
     * @throws SQLException
     */
    public static String getTramaRecetario(String pNumeroRecetario) throws SQLException {
        parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumeroRecetario);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RCM.GET_TRAMA_RECETARIO(?,?,?,?)", parametros);
    }

    /**
     * Envia la trama de recetario al sistema de Recetario Magistral
     * @author ERIOS
     * @since 30.05.2013
     * @param pTrama
     * @return
     * @throws SQLException
     */
    public static String enviaTramaRecetario(String pTrama) throws SQLException {
        parametros.add(pTrama);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_RCM.ENVIO_RECETARIO(?)", parametros,
                                                                 FarmaConstants.CONECTION_MATRIZ,
                                                                 FarmaConstants.INDICADOR_S);
    }

    /**
     * Actualiza estado de recetario
     * @author ERIOS
     * @since 30.05.2013
     * @param pNumeroRecetario
     * @param pEstado
     * @throws SQLException
     */
    public static void actualizaEstadoRecetario(String pNumeroRecetario,
                                                ConstantsRecetario.Estado pEstado) throws SQLException {
        parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumeroRecetario);
        parametros.add(pEstado.getValor());
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RCM.ACTUALIZA_ESTADO_RECETARIO(?,?,?,?,?,?)",
                                                 parametros, false);
    }


    public static void cambiarEstadoRCM(String numPedVta, String estPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedVta);
        parametros.add(estPed);
        log.debug("PTOVENTA_RCM.VTA_UPDATE_PEDIDO_CAB_RCM(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_RCM.VTA_UPDATE_PEDIDO_CAB_RCM(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static String getEstadoRCM(String pNumCompPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumCompPago);
        log.debug("PTOVENTA_RCM.VTA_GET_VALIDA_RCM(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RCM.VTA_GET_VALIDA_RCM(?,?,?)", parametros);
    }
}
