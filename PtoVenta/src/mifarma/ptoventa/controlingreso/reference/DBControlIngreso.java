package mifarma.ptoventa.controlingreso.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBControlIngreso.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAMEGHINO      10.10.2007   Creación<br>
 * <br>
 * @author PAULO CESAR AMEGHINO ROJAS<br>
 * @version 1.0<br>
 *
 */
public class DBControlIngreso {
    private static final Logger log = LoggerFactory.getLogger(DBControlIngreso.class);

    private static ArrayList parametros = new ArrayList();

    public DBControlIngreso() {
    }

    public static void cargaListaFiltro(FarmaTableModel pTableModel, String pTipoFiltro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pTipoFiltro);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_GRAL.LISTA_FILTROS(?)", parametros, false);
    }

    public static void cargaListaMaestros(FarmaTableModel pTableModel, String pTipoMaestro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pTipoMaestro);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_GRAL.LISTA_MAESTROS(?)", parametros, false);
    }

    public static ArrayList cargaListaMaestrosArray(String pTipoMaestro) throws SQLException {
        parametros = new ArrayList();
        ArrayList rpta = new ArrayList();
        parametros.add(pTipoMaestro);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(rpta, "PTOVENTA_GRAL.LISTA_MAESTROS(?)", parametros);
        return rpta;
    }

    public static void buscaCodigoListaMaestro(ArrayList pArrayList, String pTipoMaestro,
                                               String pCodBusqueda) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pTipoMaestro);
        parametros.add(pCodBusqueda);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_GRAL.BUSCA_REGISTRO_LISTA_MAESTROS(?,?)",
                                                          parametros);
    }

    public static void getPersonal(ArrayList pArray, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.GET_PERSONAL(?,?,?)",
                                                          parametros);
    }
    /**
     * CHUANES
     * SE ADICIONA EL PARAMETRO INDICADOR EL CUAL INDICA LA FORMA DE MARCACION
     * 03.03.2015
     * **/
    public static void grabarRegistro(String pDni, String pTipo, String pCodCia, String pCodTrab,
                                      String pCodHora,String indicador) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pTipo);
        parametros.add(pCodCia);
        parametros.add(pCodTrab);
        parametros.add(pCodHora);
        parametros.add(indicador);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.GRABA_REG_PERSONAL(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaRegistros(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.GET_LISTA_REGISTROS(?,?)",
                                                 parametros, false);
    }

    /**
     *
     * @param pDni
     * @throws SQLException
     * @since
     */
    public static void validarIngreso(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.GET_VALIDA_TARDANZA(?)", parametros, false);
    }


    //ACTUALIZA_DATOS_TRABAJADOR

    public static void ActualizarDatosIngreso(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.ACTUALIZA_DATOS_TRABAJADOR(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Verificamos la existencia del registro del trabajador en el dia
     * @author JCORTEZ
     * @since 03.10.2007
     * */
    public static void getRegistro(ArrayList pArray, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.TRA_EXIST_REGISTRO(?,?)",
                                                          parametros);
    }

    /**
     * valida que se registre la salida cuando sea necesaria
     * @author JCORTEZ
     * @since 03.10.2007
     * */
    public static void validaSalida(ArrayList pArray, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.TRA_VALIDA_SALIDA(?,?)",
                                                          parametros);
    }

    /**
     * Se lista el historico de temperaturas
     * @author JCORTEZ
     * @since 11.02.2009
     * */
    public static void cargaListaHistoricoTemp(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.LISTA_HISTORICO_TEMP(?,?)",
                                                 parametros, false);
    }


    /**
     * Se Ingresa temperatura
     * @throws SQLException
     * @author JCORTEZ
     * @since 12.02.2009
     */
    public static void ingresaTemperatura(String ValVta, String ValAlm, String ValRefrig) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(ValVta)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(ValAlm)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(ValRefrig)));
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.IND_INGRESA_TEMP(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * validar rol usuario
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author Jorge Cortez Alvarez
     * @since 12.02.2009
     */
    public static String verificaRolUsuario(String SecUsu, String CodRol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRol);
        log.debug("verifica que el usuario tenga el rol adecuado: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_ROL_USU(?,?,?,?)", parametros);
    }

    /**
     * validar ingreso quimico en el dia
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author Jorge Cortez Alvarez
     * @since 12.02.2009
     */
    public static String verificaIngrTemperatura(String SecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        log.debug("verifica ingreso de temperatura en el dia: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_INGR_TEMP_USU(?,?,?)",
                                                           parametros);
    }

    /**
     * validar ingreso quimico en el dia
     * @throws SQLException
     * @author Jorge Cortez Alvarez
     * @since 12.02.2009
     */
    public static String getSecUsuLocal(String Dni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
        log.debug("Obtiene sec_usu_local por dni " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.GET_SEC_USU_X_DNI(?,?,?)", parametros);
    }

    public static void ListarHistoricoTempFiltro(FarmaTableModel pTableModel, String cFecIni_in,
                                                 String cFecFin_in) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cFecIni_in);
        parametros.add(cFecFin_in);
        log.debug("HISTORICO DE TEMPERATURAS");
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.LISTA_HIST_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * validar rol usuario
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author Asolis
     * @since 25.02.2009
     */
    public static String ValidaRolTrabLocal(String SecUsu, String CodRolCajero, String CodRolVendedor,
                                            String CodRolAdministrador) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRolCajero);
        parametros.add(CodRolVendedor);
        parametros.add(CodRolAdministrador);
        log.debug("verifica que el usuario tenga el rol  de trabajador de local : " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_ROL_TRAB_LOCAL(?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Genera cupones de regalo
     * @throws SQLException
     * @author JCORTEZ
     * @since 18.08.2009
     */
    public static void generaCuponesRegalo(String NumDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumDni);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CUPON.CAJ_P_GENERA_CUPON_REGALO(?,?,?,?)", parametros,
                                                 false);
    }


    /**
     * Se obtiene los cupones emitidos por QF
     * @AUTHOR JCORTEZ
     * @AINCE  18.08.09
     */
    public static void obtieneCuponesRegalo(ArrayList cuponesRegalo, String vDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vDni);
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_CUR_CUP_REGALOS(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(cuponesRegalo, "PTOVENTA_CUPON.CUP_F_CUR_CUP_REGALOS(?,?,?)",
                                                          parametros);

    }


    /**
     * validar rol usuario
     * @author Asolis
     * @since 25.02.2009
     */
    public static String existCuponRegalo(String Dni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_VERI_EXIST_CUP(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CUPON.CUP_F_VERI_EXIST_CUP(?,?,?)", parametros);
    }
    
    /**
     * CHUANES
     * 03.03.2015
     * Verifica si permite la marcación del control de ingreso en forma manual o electronica.
     * 
     * **/
    public static String indicadorIngreso() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INDICADOR_MARCACION_INGRESO", parametros);
    }


}
