package mifarma.ptoventa.delivery.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBMotorizados extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DBMotorizados.class);
    private static ArrayList parametros = new ArrayList();

    public DBMotorizados() {
    }

    public static String ingresaMotorizado(String pNomMot, String pApePat, String pApeMat, String pDNI, String pPlaca,
                                           String pNumNextel, String pFecNac, String pDireccion,
        //Agregado -DUBILLUZ
        String pAlias, String pCodLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaConstants.COD_NUMERA_MOTORIZADO);
        parametros.add(pNomMot);
        parametros.add(pApePat);
        parametros.add(pApeMat);
        parametros.add(pDNI);
        parametros.add(pPlaca);
        parametros.add(pNumNextel);
        parametros.add(pFecNac);
        parametros.add(pDireccion);
        parametros.add(FarmaVariables.vIdUsu);
        /**
     * Para el Alias y CodLocal de Referencia
     * @author : dubilluz
     * @since  : 09.07.2007
     */
        parametros.add(pAlias.trim());
        parametros.add(pCodLocal.trim());

        log.debug("DELIVERY_MOTORIZADO.MOT_AGREGA_MOTORIZADO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + parametros);

        return FarmaDBUtility.executeSQLStoredProcedureStr("DELIVERY_MOTORIZADO.MOT_AGREGA_MOTORIZADO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void modificaMotorizado(String pCodMot, String pNomMot, String pApePat, String pApeMat, String pDNI,
                                          String pPlaca, String pNumNextel, String pFecNac, String pDireccion,
        //Agregado -DUBILLUZ
        String pAlias, String pCodLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodMot);
        parametros.add(pNomMot);
        parametros.add(pApePat);
        parametros.add(pApeMat);
        parametros.add(pDNI);
        parametros.add(pPlaca);
        parametros.add(pNumNextel);
        parametros.add(pFecNac);
        parametros.add(pDireccion);
        parametros.add(FarmaVariables.vIdUsu);
        /**
     * Para el Alias y CodLocal de Referencia
     * @author : dubilluz
     * @since  : 09.07.2007
     */
        parametros.add(pAlias.trim());
        parametros.add(pCodLocal.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "DELIVERY_MOTORIZADO.MOT_MODIFICA_MOTORIZADO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaEstadoMot(String pCodMot, String pEstMot) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodMot);
        parametros.add(pEstMot);
        parametros.add(FarmaVariables.vIdUsu);

        FarmaDBUtility.executeSQLStoredProcedure(null, "DELIVERY_MOTORIZADO.MOT_ACTUALIZA_ESTADO_MOT(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaMotorizados(FarmaTableModel pTableModel, String pEstado) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pEstado);
        log.debug("DELIVERY_MOTORIZADO.MOT_OBTIENE_MOTORIZADOS(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "DELIVERY_MOTORIZADO.MOT_OBTIENE_MOTORIZADOS(?,?,?)",
                                                 parametros, false);

    }

    public static void obtieneInfoMotorizado(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesMotorizados.vCodMot);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "DELIVERY_MOTORIZADO.MOT_OBTIENE_INFO_MOTORIZADO(?,?,?)",
                                                          parametros);
    }

    /**
     * Obtiene informacion del local
     * @author : dubilluz
     * @since  : 09.08.2007
     */
    public static void obtieneLocal(ArrayList pArrayList, String pCodlocal_referencia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodlocal_referencia);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "DELIVERY_MOTORIZADO.MOT_BUSCA_LOCAL(?,?)",
                                                          parametros);
    }
}
