package mifarma.ptoventa.otros.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo     : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBOtros.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      23.10.2008   Creación<br>
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */
public class DBOtros {
    private static final Logger log = LoggerFactory.getLogger(DBOtros.class);

    private static ArrayList parametros = new ArrayList();

    public DBOtros() {
    }

    //DlgListaMedidaPresion

    /**
     * @author jcallo
     * @since 23.10.2008
     * */
    public static void cargaListaMedPresionRegistradas(FarmaTableModel pTableModel, String fechaIni,
                                                       String fechaFin) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fechaIni);
        parametros.add(fechaFin);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CLI_MED_PRESION.MP_F_CUR_LIST_REGISTROS(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Obtiene parametro de validacion de longitud de doc de identificacion
     * @Author JCALLO
     * @since 23.10.2008
     * @throws SQLException
     */
    public static String obtenerParamDocIden() throws SQLException {

        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_VAR_VAL_DOC_IDEN()",
                                                           parametros);

    }

    public static void listarCamposClienteMedPresion(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando al SP PTOVENTA_CLI_MED_PRESION.FID_F_CUR_LISTA_DATOS_CLI_M_P()");
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CLI_MED_PRESION.MP_F_CUR_LISTA_DATOS_CLIENTE()", parametros,
                                                 false);
    }

    public static void getDatosExisteDNI(ArrayList array, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pDni.trim());
        log.debug("parametros:" + parametros);
        log.debug("PTOVENTA_CLI_MED_PRESION.MP_F_CUR_DATOS_EXISTE_DNI(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                                                          "PTOVENTA_CLI_MED_PRESION.MP_F_CUR_DATOS_EXISTE_DNI(?)",
                                                          //JCHAVEZ 06102009
                parametros);
    }

    /**
     * metodo encargado de insertar la informacion del cliente local
     * @throws SQLException
     */
    public static void insertarClienteLocal() throws SQLException {

        parametros = new ArrayList();
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(VariablesOtros.vNomCliente.trim());
        parametros.add(VariablesOtros.vApePatCliente.trim());
        parametros.add(VariablesOtros.vApeMatCliente.trim());
        parametros.add(VariablesOtros.vEmail.trim());
        parametros.add(VariablesOtros.vTelefono.trim());
        parametros.add(VariablesOtros.vSexo.trim());
        parametros.add(VariablesOtros.vDireccion.trim());
        parametros.add(VariablesOtros.vFecNacimiento.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        parametros.add(VariablesOtros.vIndEstado.trim());
        parametros.add(VariablesOtros.Tip_doc.trim());
        parametros.add(VariablesOtros.Usu_Confir.trim());
        log.debug("invocando a PTOVENTA_CLI_MED_PRESION.MP_P_INSERT_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?) :" +
                  parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CLI_MED_PRESION.MP_P_INSERT_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * metodo encargado de insertar o actualizar la informacion del cliente
     * en matriz.
     * @throws SQLException
     */
    public static void insertarClienteEnMatriz(String pIndCloseConecction) throws SQLException {

        parametros = new ArrayList();
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(VariablesOtros.vNomCliente.trim());
        parametros.add(VariablesOtros.vApePatCliente.trim());
        parametros.add(VariablesOtros.vApeMatCliente.trim());
        parametros.add(VariablesOtros.vEmail.trim());
        parametros.add(VariablesOtros.vTelefono.trim());
        parametros.add(VariablesOtros.vSexo.trim());
        parametros.add(VariablesOtros.vDireccion.trim());
        parametros.add(VariablesOtros.vFecNacimiento.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        parametros.add(VariablesOtros.vIndEstado.trim());
        log.debug("invocando a PTOVENTA_MATRIZ_CLI_MP.MP_P_INSERT_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?) :" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA_MATRIZ_CLI_MP.MP_P_INSERT_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_MATRIZ,
                                                       pIndCloseConecction);
    }

    public static void getCamposCliente(ArrayList array) throws SQLException {
        parametros = new ArrayList();
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_CLI_MED_PRESION.MP_F_CUR_CAMPOS_CLIENTE",
                                                          parametros);
    }

    public static void getHistoricoMedPresion(FarmaTableModel tblModel, String pDniCliente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDniCliente);
        log.debug("invocando a PTOVENTA_CLI_MED_PRESION.MP_F_CUR_HIST_MP(?,?,?) params: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(tblModel, "PTOVENTA_CLI_MED_PRESION.MP_F_CUR_HIST_MP(?,?,?)",
                                                 parametros, false);
    }


    public static String insertarMedidaPresionEnLocal() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(new Integer(VariablesOtros.vMaxSistolica.trim()));
        parametros.add(new Integer(VariablesOtros.vMinDiastolica.trim()));
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.debug("invocando a PTOVENTA_CLI_MED_PRESION.MP_F_VAR_INSERT_MED_PRESION(?,?,?,?,?,?):" + parametros);

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_INSERT_MED_PRESION(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void insertarMedidaPresionEnMatriz(String pIndCloseConecction) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesOtros.vNumReg.trim());
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(new Integer(VariablesOtros.vMaxSistolica.trim()));
        parametros.add(new Integer(VariablesOtros.vMinDiastolica.trim()));
        parametros.add(FarmaVariables.vIdUsu.trim());

        log.debug("invocando a PTOVENTA_MATRIZ_CLI_MP.MP_P_INSERT_MED_PRESION(?,?,?,?,?,?,?) :" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA_MATRIZ_CLI_MP.MP_P_INSERT_MED_PRESION(?,?,?,?,?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_MATRIZ,
                                                       pIndCloseConecction);
    }


    public static void actualizarMedidaPresionEnLocal() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesOtros.vNumReg.trim());
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(new Integer(VariablesOtros.vMaxSistolica.trim()));
        parametros.add(new Integer(VariablesOtros.vMinDiastolica.trim()));
        parametros.add(FarmaVariables.vIdUsu.trim());

        log.debug("medida de presion a actualizar :" + parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CLI_MED_PRESION.MP_P_UPDATE_MED_PRESION(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizarMedidaPresionEnMatriz(String pIndCloseConecction) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesOtros.vNumReg.trim());
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(new Integer(VariablesOtros.vMaxSistolica.trim()));
        parametros.add(new Integer(VariablesOtros.vMinDiastolica.trim()));
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.debug("invocando a PTOVENTA_MATRIZ_CLI_MP.MP_P_UPDATE_MED_PRESION(?,?,?,?,?,?,?) :" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA_MATRIZ_CLI_MP.MP_P_UPDATE_MED_PRESION(?,?,?,?,?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_MATRIZ,
                                                       pIndCloseConecction);
    }

    public static void inactivarMedidaPresionEnLocal() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesOtros.vNumReg.trim());
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.info("inactivar medida de presion :" + parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CLI_MED_PRESION.MP_P_INACTIVAR_MED_PRESION(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void inactivarMedidaPresionEnMatriz(String pIndCloseConecction) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesOtros.vNumReg.trim());
        parametros.add(VariablesOtros.vDniCliente.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.debug("invocando a PTOVENTA_MATRIZ_CLI_MP.MP_P_INACTIVAR_MED_PRESION(?,?,?,?,?) :" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA_MATRIZ_CLI_MP.MP_P_INACTIVAR_MED_PRESION(?,?,?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_MATRIZ,
                                                       pIndCloseConecction);
    }


    /***
     * falta modificar dicho procedimiento en MATRIZ
     * **/
    public static void getDatosExisteDNI_Matriz(ArrayList array, String pDNI,
                                                String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        log.debug("", parametros);
        parametros.add(pDNI.trim());
        FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(array, "PTOVENTA_MATRIZ_CLI_MP.MP_F_CUR_DATOS_DNI(?)",
                                                                parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                pIndCloseConecction);
    }


    /***
     * metodo encargado de traer datos de matriz
     * @author jcallo
     * @fecha 28.10.2008
     * **/
    public static void getDatosMedPresMatriz(FarmaTableModel pTableModel, String pDNI,
                                             String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDNI.trim());
        parametros.add(FarmaVariables.vCodLocal);

        log.info("PTOVENTA_MATRIZ_CLI_MP.MP_F_CUR_HIST_MP(?,?,?):" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MATRIZ_CLI_MP.MP_F_CUR_HIST_MP(?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_MATRIZ,
                                                       pIndCloseConecction);
        /*executeSQLStoredProcedureArrayList(farmaTable,
			  "PTOVENTA_MATRIZ_CLI_MP.FID_F_CUR_DATOS_DNI(?)",
			  parametros,
			  FarmaConstants.CONECTION_MATRIZ,pIndCloseConecction);*/
    }

    /**
     * obtener cabecera html a imprimir medida de presion
     * ***/

    public static String getCabHtmlImprimir(String ipServidor, String pDni, String pNombCliente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(ipServidor);
        parametros.add(pDni.trim());
        parametros.add(pNombCliente.trim());
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_IMP_CAB_HIST(?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_IMP_CAB_HIST(?,?,?,?,?,?)",
                                                           parametros);

    }

    /**
     * obtener cabecera html a imprimir medida de presion
     * ***/

    public static String getPieHtmlImprimir() throws SQLException {
        parametros = new ArrayList();

        log.info("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_IMP_PIE_HIST():" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_IMP_PIE_HIST()",
                                                           parametros);

    }

    /**
     * obtener cantidad de items maxima de impresion
     * ***/

    public static String getCantMaxItemsImpresion() throws SQLException {
        parametros = new ArrayList();

        log.info("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_MAX_ITEMS_IMP():" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_MAX_ITEMS_IMP()",
                                                           parametros);

    }

    /**
     * obtener rango de valores validos de la medida presion
     * ***/

    public static String getRangoValoresValidosMP() throws SQLException {
        parametros = new ArrayList();

        log.info("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_MAX_MIN_VAL_MP():" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI_MED_PRESION.MP_F_VAR_MAX_MIN_VAL_MP()",
                                                           parametros);

    }

    /**
     * @author DUBILLUZ 03.10.2009
     * @param pNumPed
     * @param pRuc
     * @return
     * @throws SQLException
     */
    public static boolean isValidaDocumento(String pDni, String pFecha) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni.trim());
        parametros.add(pFecha);
        //crear esl paquete y  el procedimiento
        log.debug("valida Pedido Fidelizado PTOVENTA_FID_RENIEC.FID_F_VALIDA_DNI_REC(?,?,?,?) :" + parametros);
        String pValor =
            FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.FID_F_VALIDA_DNI_REC(?,?,?,?)",
                                                        parametros);

        if (pValor.trim().equalsIgnoreCase("S"))
            return true;
        else
            return false; //Documento no valido no esta en Reniec.//Validara y pediraq clave de Quimico del local.
    }

    /***
     * metodo encargado de traer mensaje en html de cliente
     * @author JCHAVEZ
     * @fecha 05.10.2009
     * **/
    public static String getMsg() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.F_VAR2_GET_MENSAJE(?,?)", vParameters);
    }

    /**
     * Se envia correo de confirmaciony se crea o actualiza cliente
     * @AUTHOR JCORTEZ
     * @SINCE 06.10.09
     * */
    public static void enviaCorreoConfirmacion(String DescDoc, String NumDoc, String Nomcli,
                                               String FecNac) throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(FarmaVariables.vIdUsu);
        vParameters.add(FarmaVariables.vNuSecUsu);
        vParameters.add(DescDoc);
        vParameters.add(NumDoc);
        vParameters.add(Nomcli);
        vParameters.add(FecNac);
        log.debug("parametros " + vParameters);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_FID_RENIEC.ENVIA_CORREO_CONFIRMACION(?,?,?,?,?,?,?,?)",
                                                 vParameters, false);
    }

    /**
     * Se obtiene rol confirmacion
     * @AUTHOR JCORTEZ
     * @SINCE 06.10.09
     * */
    public static String obtieneRolConfirmacin() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.F_GET_ROL_CONFIRMACION(?,?)",
                                                           vParameters);
    }

}
