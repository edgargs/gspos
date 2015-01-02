package mifarma.ptoventa.campAcumulada.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBCampAcumulada {

    private static final Logger log = LoggerFactory.getLogger(DBCampAcumulada.class);
    private static ArrayList parametros = new ArrayList();

    public DBCampAcumulada() {
    }

    /**
     * metodo encargado de cargar todas campañas de acumulacion vigentes.
     * @since 15.12.2008
     * @autor jcallo
     * @param tblModel
     * @throws SQLException
     */
    public static void getCampAcumuladas(FarmaTableModel tblModel, String CodProdFiltro) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(CodProdFiltro);

        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CUR_LISTA_CAMPANIAS(?,?,?) params: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(tblModel, "PTOVENTA_CA_CLIENTE.CA_F_CUR_LISTA_CAMPANIAS(?,?,?)",
                                                 parametros, true);
    }

    /**
     * metodo encargado de cargar todas campañas de acumulacion vigentes.
     * @since 15.12.2008
     * @autor jcallo
     * @param tblModel
     * @throws SQLException
     */
    public static void getCampAcumuladaXCliente(FarmaTableModel tblModelInc, String pDni) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);

        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CUR_CAMP_CLIENTE(?,?,?) params: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(tblModelInc, "PTOVENTA_CA_CLIENTE.CA_F_CUR_CAMP_CLIENTE(?,?,?)",
                                                 parametros, false);
    }

    /**
     * metodo encargado de obtener todos los campos necesarios de los clientes para la campañas de acumulacion
     * @since 15.12.2008
     * @autor jcallo
     * @param pTableModel
     * @throws SQLException
     */
    public static void listarCamposClienteCampAcumulacion(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando al SP PTOVENTA_CA_CLIENTE.FID_F_CUR_LISTA_DATOS_CLI_M_P()");
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CA_CLIENTE.CA_F_CUR_LISTA_DATOS_CLIENTE()",
                                                 parametros, false);
    }

    /**
     * metodo encargado de obtener todos los COD campos necesarios de los clientes para las campañas de acumulacion
     * @since 15.12.2008
     * @autor jcallo
     * @param array
     * @throws SQLException
     */
    public static void getCamposCliente(ArrayList array) throws SQLException {
        parametros = new ArrayList();
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_CA_CLIENTE.CA_F_CUR_CAMPOS_CLIENTE",
                                                          parametros);
    }

    /**
     * metodo encargado de obtener datos del cliente si es que existe
     * @since 15.12.2008
     * @autor jcallo
     * @param array
     * @param pDni
     * @throws SQLException
     */
    public static void getDatosExisteDNI(ArrayList array, String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni.trim());
        log.debug("PTOVENTA_CA_CLIENTE.CA_F_CUR_DATOS_EXISTE_DNI(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                                                          "PTOVENTA_CA_CLIENTE.CA_F_CUR_DATOS_EXISTE_DNI(?,?,?,?)",
                                                          parametros);
    }

    /**
     * Metodo encargado de traer informacion de matriz
     *
     * @param array
     * @param pDNI
     * @param pIndCloseConecction
     * @throws SQLException
     */
    public static void getDatosExisteDNI_Matriz(ArrayList array, String pDNI,
                                                String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        log.debug("", parametros);
        parametros.add(pDNI.trim());
        /* FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(array,
                            "PTOVENTA_MATRIZ_CA_CLI.CA_F_CUR_DATOS_DNI(?)",
                            parametros,
                            FarmaConstants.CONECTION_MATRIZ,pIndCloseConecction);
            */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_CA_CLI.CA_F_CUR_DATOS_DNI(?)", parametros,
                                                 false);

    }

    /**
     * Obtiene parametro de validacion de longitud de doc de identificacion
     * @Author JCALLO
     * @since 15.12.2008
     * @throws SQLException
     */
    public static String obtenerParamDocIden() throws SQLException {

        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_VAR_VAL_DOC_IDEN()",
                                                           parametros);

    }

    /**
     * METODO ENCARGADO DE INSERTAR DATOS DE UN NUEVO CLIENTE o actualizar su informacion
     * @throws SQLException
     */
    public static void insertarCliente() throws SQLException {

        parametros = new ArrayList();
        parametros.add(VariablesCampAcumulada.vDniCliente.trim());
        parametros.add(VariablesCampAcumulada.vNomCliente.trim());
        parametros.add(VariablesCampAcumulada.vApePatCliente.trim());
        parametros.add(VariablesCampAcumulada.vApeMatCliente.trim());
        parametros.add(VariablesCampAcumulada.vSexo.trim());
        parametros.add(VariablesCampAcumulada.vFecNacimiento.trim());
        parametros.add(VariablesCampAcumulada.vDireccion.trim());
        parametros.add(VariablesCampAcumulada.vTelefono.trim());
        parametros.add(VariablesCampAcumulada.vCelular.trim());
        parametros.add(VariablesCampAcumulada.vEmail.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        parametros.add(VariablesCampAcumulada.vIndEstado.trim());
        log.info("INSERTANDO CLIENTE PTOVENTA_CA_CLIENTE.CA_P_INSERT_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?) :" +
                 parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CA_CLIENTE.CA_P_INSERT_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * METODO ENCARGADO DE INSERTAR DATOS DE UN NUEVO CLIENTE o actualizar su informacion
     * en matriz si hubiera linea con matriz
     * @throws SQLException
     */
    public static void insertarClienteMatriz() throws SQLException {

        parametros = new ArrayList();
        parametros.add(VariablesCampAcumulada.vDniCliente.trim());
        parametros.add(VariablesCampAcumulada.vNomCliente.trim());
        parametros.add(VariablesCampAcumulada.vApePatCliente.trim());
        parametros.add(VariablesCampAcumulada.vApeMatCliente.trim());
        parametros.add(VariablesCampAcumulada.vSexo.trim());
        parametros.add(VariablesCampAcumulada.vFecNacimiento.trim());
        parametros.add(VariablesCampAcumulada.vDireccion.trim());
        parametros.add(VariablesCampAcumulada.vTelefono.trim());
        parametros.add(VariablesCampAcumulada.vCelular.trim());
        parametros.add(VariablesCampAcumulada.vEmail.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        parametros.add(VariablesCampAcumulada.vIndEstado.trim());
        log.info("INSERTANDO CLIENTE PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CLIENTE_MATRIZ(?,?,?,?,?,?,?,?,?,?,?,?,?) :" +
                 parametros);
        /*
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CLIENTE_MATRIZ(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                parametros, false,FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
        */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CLIENTE_MATRIZ(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Metodo encargado de obtener todas las tarjetas del cliente si es que las tuviera en local
     * @param pDNI
     * @throws SQLException
     */
    public static void getTarjetasClienteLocal(ArrayList array, String pDNI) throws SQLException {

        parametros = new ArrayList();

        parametros.add(pDNI);

        log.info("obteniendo tarjetas del cliente en local PTOVENTA_CA_CLIENTE.CA_F_CUR_TARJETAS_CLI(?) :" +
                 parametros);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_CA_CLIENTE.CA_F_CUR_TARJETAS_CLI(?)",
                                                          parametros);
    }

    /**
     * Metodo encargado de obtener todas las tarjetas del cliente en matriz
     * @param pDNI
     * @throws SQLException
     */
    public static void getTarjetasClienteMatriz(ArrayList array, String pDNI,
                                                String pIndCloseConecction) throws SQLException {

        parametros = new ArrayList();

        parametros.add(pDNI);

        log.info("obteniendo tarjetas del cliente en matriz PTOVENTA_CA_CLIENTE.CA_F_CUR_TARJETAS_CLI(?) :" +
                 parametros);

        FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(array,
                                                                "PTOVENTA_MATRIZ_CA_CLI.CA_F_CUR_TARJETAS_CLI(?)",
                                                                parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                pIndCloseConecction);
    }


    public static void asociarClienteConTarjeta(String pDNI, String pNroTarjeta) throws SQLException {

        parametros = new ArrayList();

        parametros.add(pNroTarjeta);
        parametros.add(pDNI);
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.info("INSERTANDO CLIENTE PTOVENTA_CA_CLIENTE.CA_P_UPD_TARJETA_CLIENTE(?,?,?,?) :" + parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CA_CLIENTE.CA_P_UPD_TARJETA_CLIENTE(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @param pDNI
     * @param pNroTarjeta
     * @throws SQLException
     */
    public static void asociarClienteConTarjetaMatriz(String pDNI, String pNroTarjeta) throws SQLException {

        parametros = new ArrayList();

        parametros.add(pNroTarjeta);
        parametros.add(pDNI);
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.info("INSERTANDO CLIENTE PTOVENTA_CA_CLIENTE.CA_P_UPD_TARJETA_CLIENTE(?,?,?,?) :" + parametros);
        /*
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                "PTOVENTA_MATRIZ_CA_CLI.CA_P_UPD_TARJETA_CLIENTE(?,?,?,?)",
                        parametros, false,FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
        */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_CA_CLI.CA_P_UPD_TARJETA_CLIENTE(?,?,?,?)",
                                                 parametros, false);
    }

    public static void asociarClienteConCampania(String pDNI, String pCodCampania) throws SQLException {

        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCampania);
        parametros.add(pDNI);
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.info("INSERTANDO CLIENTE PTOVENTA_CA_CLIENTE.CA_P_INSERT_CLI_CAMPANIA(?,?,?,?) :" + parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CA_CLIENTE.CA_P_INSERT_CLI_CAMPANIA(?,?,?,?)",
                                                 parametros, false);
    }

    public static void asociarClienteConCampaniaMatriz(String pDNI, String pCodCampania) throws SQLException {

        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCampania);
        parametros.add(pDNI);
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.info("INSERTANDO CLIENTE PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CLI_CAMPANIA(?,?,?,?) :" + parametros);
        /*
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CLI_CAMPANIA(?,?,?,?)",
                parametros, false,FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
        */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CLI_CAMPANIA(?,?,?,?)",
                                                 parametros, false);
    }

}
