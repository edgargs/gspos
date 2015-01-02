package mifarma.ptoventa.retiro.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//ASOSA 04.12.2009.n
/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBRetiro <br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 04.12.2009 Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class DBRetiro {

    private static final Logger log = LoggerFactory.getLogger(DBRetiro.class);

    public DBRetiro() {
    }

    private static ArrayList parametros;

    public static void getListaRetiros(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        //parametros.add(VariablesRetiro.TIP_CIERRE);
        parametros.add(VariablesRetiro.COD_LOCAL);
        parametros.add(VariablesRetiro.FEC_INI);
        parametros.add(VariablesRetiro.FEC_FIN);
        log.debug("PTOVTA_RETIRO_VB.PVTA_F_LIST_RETIRO_VB(?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVTA_RETIRO_VB.PVTA_F_LIST_RETIRO_VB(?,?,?)",
                                                 parametros, false);
    }

    public static void insertarNuevoRetiro() throws SQLException {
        parametros = new ArrayList();
        parametros.add(VariablesRetiro.COD_LOCAL);
        parametros.add(VariablesRetiro.ANIO);
        parametros.add(VariablesRetiro.MES);
        parametros.add(VariablesRetiro.DIA);
        if (VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("T")) {
            parametros.add(VariablesRetiro.COD_TUR);
            parametros.add(VariablesRetiro.COD_CAJA);
        }
        parametros.add(VariablesRetiro.CORREO);
        parametros.add(FarmaVariables.vIdUsu);

        String cadena = "";
        String tipo = VariablesRetiro.TIP_CIERRE;
        if (tipo.equalsIgnoreCase("D"))
            cadena = "PTOVTA_RETIRO_VB.PVTA_P_PROCESAR_RET_DIA(?,?,?,?,?,?)";
        if (tipo.equalsIgnoreCase("C"))
            cadena = "PTOVTA_RETIRO_VB.PVTA_P_PROCESAR_RET_CONTAB(?,?,?,?,?,?)";
        if (tipo.equalsIgnoreCase("T"))
            cadena = "PTOVTA_RETIRO_VB.PVTA_P_PROCESAR_RET_TUR(?,?,?,?,?,?,?,?)";
        log.debug(cadena, parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, cadena, parametros, false);

    }

    public static void procesarRetiros() throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVTA_RETIRO_VB.PVTA_P_PROCESAR_RETIROS", parametros, false);
    }

    public static void getlistaRpteRetiros(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(VariablesRetiro.IND_PROCESADO);
        parametros.add(VariablesRetiro.FEC_INI);
        parametros.add(VariablesRetiro.FEC_FIN);
        log.debug("PTOVTA_RETIRO_VB.PVTA_F_RPTE_RETIRO_TIPO(?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVTA_RETIRO_VB.PVTA_F_RPTE_RETIRO_TIPO(?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerNombreLocal() throws SQLException {
        String nombre = "";
        parametros = new ArrayList();
        parametros.add(VariablesRetiro.COD_LOCAL.trim());
        nombre =
                (String)FarmaDBUtility.executeSQLStoredProcedureStr("PTOVTA_RETIRO_VB.PVTA_F_GET_COD_LOCAL(?)", parametros);
        return nombre;
    }

    public static String validarRetiroDC() throws SQLException {
        String ind = "";
        parametros = new ArrayList();
        parametros.add(VariablesRetiro.TIP_CIERRE.trim());
        parametros.add(VariablesRetiro.COD_LOCAL.trim());
        parametros.add(VariablesRetiro.FEC_INI.trim());
        log.debug("PTOVTA_RETIRO_VB.PVTA_F_VALIDA_RETIRO(?,?,?)", parametros);
        ind =
(String)FarmaDBUtility.executeSQLStoredProcedureStr("PTOVTA_RETIRO_VB.PVTA_F_VALIDA_RETIRO(?,?,?)", parametros);
        return ind;
    }

}
