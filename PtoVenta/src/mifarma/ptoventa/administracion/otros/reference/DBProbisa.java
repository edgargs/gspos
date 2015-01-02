package mifarma.ptoventa.administracion.otros.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBProbisa.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.04.2007   Creación<br>
 * <br>
 * @author  Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class DBProbisa {
    private static final Logger log = LoggerFactory.getLogger(DBProbisa.class);
    private static ArrayList parametros;

    public DBProbisa() {
    }

    public static void listaControlProbisa(FarmaTableModel pTableModel, String pFecIni,
                                           String pFecFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecIni);
        parametros.add(pFecFin);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CONTROL_PROB.CONTROL_PROB_OBTIENE_REGISTROS(?,?,?,?)",
                                                 parametros, false);
    }

    public static void agregaControl(String pCantTint, String pCantRec, String pCantAten,
                                     String pFecha) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pCantTint));
        parametros.add(new Integer(pCantRec));
        parametros.add(new Integer(pCantAten));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pFecha);
        log.debug("agregaControl " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONTROL_PROB.CONTROL_PROB_AGREGA_CONTROL(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaControl(String pFecha, String pCantTint, String pCantRec,
                                        String pCantAten) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecha);
        parametros.add(new Integer(pCantTint));
        parametros.add(new Integer(pCantRec));
        parametros.add(new Integer(pCantAten));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("actualizaControl " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONTROL_PROB.CONTROL_PROB_ACTUALIZA_CONTROL(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String verificaFecMax() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONTROL_PROB.CONTROL_VERIFICA_FEC_MAX(?,?)",
                                                           parametros);
    }
}
