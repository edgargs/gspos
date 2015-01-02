package mifarma.ptoventa.administracion.producto.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaIPSImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JSANTIVANEZ 05.01.2011 Modificación<br>
 * <br>
 * @version 1.0<br>
 *
 */
public class DBPrecios {

    private static final Logger log = LoggerFactory.getLogger(DBPrecios.class);

    private static ArrayList parametros = new ArrayList();

    public DBPrecios() {
    }

    public static void obtieneListaPrecProdCamb(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_VIAJERO.VIAJ_LISTAR_PRECIOS_CAMBIADOS(?)",
                                                 parametros, false);
    }

    public static void cargaListaProdCambiados(FarmaTableModel pTableModel, String pFechaInicio, String pFechaFin,
                                               String pDescrProd) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio);
        parametros.add(pFechaFin);
        parametros.add(pDescrProd);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_VIAJERO.V_LIST_PREC_CAMB_X_FECHA_PROD(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaProdCambiadosFaltantes(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_VIAJERO.V_LIST_PREC_CAMB_FALTANTE(?,?)",
                                                 parametros, false);
    }
}
