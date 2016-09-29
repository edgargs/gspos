package mifarma.ptoventa.administracion.mantenimiento.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBMantenimiento {
    private static final Logger log = LoggerFactory.getLogger(DBMantenimiento.class);

    public DBMantenimiento() {
    }

    private static ArrayList parametros = new ArrayList();

    public static void getParametrosLocal(ArrayList parametrosLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(parametrosLocal,
                                                          "PTOVENTA_ADMIN_MANT.GET_PARAMETROS_LOCAL(?,?)", parametros);
    }

    public static void actualizaParametrosLocal(String impReporte, String minPendientes, String pIndCambioPrecio,
                                                String pIndCambioModeloImpresora) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(impReporte);
        parametros.add(new Integer(minPendientes));
        parametros.add(pIndCambioPrecio);
        parametros.add(pIndCambioModeloImpresora);
        parametros.add(FarmaVariables.vIdUsu);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_MANT.ACTUALIZAR_PARAMETROS_LOCAL(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaControlHoras(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_ADMIN_MANT.ADMMANT_OBTIENE_CONTROL_HORAS(?,?,?)",
                                                 parametros, false);
    }

    public static void grabaControlHoras(String pCodMotivo, String pObservaciones) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodMotivo);
        parametros.add(pObservaciones);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_MANT.ADMMANT_INGRESA_CONTROL(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String verificaIngresoControlHoras(String pCodMotivo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodMotivo);
        parametros.add(FarmaVariables.vNuSecUsu);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_MANT.ADMMANT_VERIFICA_INGRESO_CTRL(?,?,?,?)",
                                                           parametros);
    }
    
    /**
     * @author ERIOS
     * @since 18.07.2016
     * @param pTableModel
     * @throws SQLException
     */
    public static void cargaListaProgramaRentables(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RENTABLES.F_GET_PROGRAMA_RENTABLES(?,?)",
                                                 parametros, false);
    }

    /**
     * @author ERIOS
     * @since 18.07.2016
     * @param pTableModel
     * @param pCodProg
     * @throws SQLException
     */
    public static void cargaListaPresupuestoVendedor(FarmaTableModel pTableModel, String pCodProg)  throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProg);
        parametros.add(FarmaVariables.vCodLocal);        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RENTABLES.F_GET_PRESUPUESTO_VENDEDOR(?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author ERIOS
     * @since 19.07.2016
     * @param pTableModel
     * @throws SQLException
     */
    public static void getListaVendedores(FarmaTableModel pTableModel, String pCodProg) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(pCodProg);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_RENTABLES.F_LISTA_USUARIOS_LOCAL(?,?,?)",
                                                 parametros, false);
    }
    
    /**
     * @author ERIOS
     * @since 19.07.2016
     * @param pNumUsu
     * @return
     * @throws SQLException
     */
    public static ArrayList getDatosVendedorArray(String pNumUsu) throws SQLException {
        ArrayList listaUsers = new ArrayList();

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumUsu);
        
        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaUsers,
                                                          "PTOVENTA_RENTABLES.F_DATOS_USUARIOS_LOCAL(?,?,?)",
                                                          parametros);
        return listaUsers;
    }

    /**
     * @author ERIOS
     * @since 19.07.2016
     * @param pCodProg
     * @throws SQLException
     */
    public static void limpiarPresupuestoVendedor(String pCodProg) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProg);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RENTABLES.P_LIMPIA_PRESUPUESTO_VENDEDOR(?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author ERIOS
     * @since 19.07.2016
     * @param pCodProg
     */
    public static void grabarPresupuestoVendedor(String pCodProg, ArrayList<String> fila) throws SQLException {
        parametros = new ArrayList();
        String pSecUsu = fila.get(0).trim();
        String pVolumen = fila.get(2).trim();
        String pLLEE = fila.get(3).trim();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProg);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(pSecUsu);        
        parametros.add(FarmaUtility.getDecimalNumber(pVolumen));        
        parametros.add(FarmaUtility.getDecimalNumber(pLLEE));        
        parametros.add(FarmaVariables.vIdUsu);        
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RENTABLES.P_SET_PRESUPUESTO_VENDEDOR(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author ERIOS
     * @since 19.07.2016
     * @param pCodProg
     */
    public static void validarPresupuestoVendedor(String pCodProg, String strIsOperador) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProg);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(strIsOperador);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_RENTABLES.P_VALIDA_PRESUPUESTO_VENDEDOR(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author ERIOS
     * @since 21.07.2016
     * @param pCodProg
     * @return
     * @throws SQLException
     */
    public static String getIndValidaDiasRegistrar(String pCodProg) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProg);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RENTABLES.F_VALIDA_DIAS_REGISTRAR(?,?,?)",
                                                           parametros);
    }
}
