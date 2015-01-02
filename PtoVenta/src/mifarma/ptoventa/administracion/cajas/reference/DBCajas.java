package mifarma.ptoventa.administracion.cajas.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBCajas {
    private static final Logger log = LoggerFactory.getLogger(DBCajas.class);
    private static ArrayList parametros = new ArrayList();

    public DBCajas() {
    }

    public static void getListaCajas(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_CAJA.CAJ_LISTA_CAJAS(?,?)", parametros,
                                                 false);
    }

    public static void getListaUsuarios(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_CAJA.CAJ_LISTA_USUARIOS_LOCAL(?,?)",
                                                 parametros, false);
    }

    public static ArrayList getListaUsuariosArray(String pNumUsu) throws SQLException {
        ArrayList listaUsers = new ArrayList();

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumUsu);


        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaUsers,
                                                          "PTOVENTA_ADMIN_CAJA.CAJ_OBTENER_DATOS_USU_LOCAL(?,?,?)",
                                                          parametros);
        return listaUsers;
    }

    public static void ingresaCaja(String pCodGrupoCia, String pCodLocal, String pSecUsu,
                                   String pDesCaja) throws SQLException {

        parametros = new ArrayList();

        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pSecUsu);
        parametros.add(pDesCaja);
        parametros.add(FarmaVariables.vIdUsu);

        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_CAJA.CAJ_INGRESA_CAJA(?,?,?,?,?)", parametros,
                                                 false);

    }

    public static void actualizaCaja(String pCodGrupoCia, String pCodLocal, String pNumCaja, String pSecUsu,
                                     String pDesCaja) throws SQLException {

        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(new Integer(pNumCaja.trim()));
        parametros.add(pSecUsu);
        parametros.add(pDesCaja);
        parametros.add(FarmaVariables.vIdUsu);

        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_CAJA.CAJ_MODIFICA_CAJA(?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static void cambiaEstadoCaja(String pCodGrupoCia, String pCodLocal, String pNumCaja) throws SQLException {

        parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(new Integer(pNumCaja.trim()));
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_CAJA.CAJ_CAMBIAESTADO_CAJA(?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaImpresorasCaja(FarmaTableModel pTableModel) throws SQLException {
        ArrayList listaUsers = new ArrayList();

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCajas.vNumCaja);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_CAJA.CAJ_LISTA_IMPRESORAS_CAJA(?,?,?)",
                                                 parametros, false);
    }


    public static void getListaImpresorasReemp(FarmaTableModel pTableModel) throws SQLException {
        ArrayList listaUsers = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCajas.vNumCaja);
        parametros.add(VariablesCaja.vTipComp);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_CAJA.CAJ_LISTA_IMPRESORAS_REEMPLAZO(?,?,?,?)",
                                                 parametros, false);
    }

    public static void reemplazaImpCaja() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(VariablesCajas.vNumCaja.trim()));
        parametros.add(VariablesCajas.vSecImprLocal);
        parametros.add(VariablesCajas.vSecImprLocal2);
        parametros.add(FarmaVariables.vIdUsu);
        log.info("PARAMETROS: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_CAJA.CAJ_MODIFICA_CAJA_IMPRESORA(?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static int verificaCajaAbierta() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCajas.vSecUsuAsig);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICA_CAJA_ABIERTA(?,?,?)",
                                                           parametros);
    }


    public static int getCantUsuDispCaja() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_ADMIN_CAJA.CAJ_CANT_CAJEROS_DISP_LOCAL(?,?)",
                                                           parametros);
    }


}
