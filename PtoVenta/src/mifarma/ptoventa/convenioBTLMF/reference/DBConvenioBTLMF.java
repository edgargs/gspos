package mifarma.ptoventa.convenioBTLMF.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.sql.ARRAY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBConvenioBTLMF {
    private static final Logger log = LoggerFactory.getLogger(DBConvenioBTLMF.class);
    private static ArrayList parametros = new ArrayList();
    private static ArrayList parametros1 = new ArrayList();
    ARRAY sqlArray = null;

    public DBConvenioBTLMF() {

    }

    /**
     * @param pTableModel
     * @throws SQLException
     */
    public static void listaConvenios(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_CONVENIOS(?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_CONVENIOS(?,?,?)",
                                                 parametros, false);
    }

    /**
     * @param pTableModel
     * @throws SQLException
     */
    public static void listaConveniosBusqueda(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_CONVENIOS(?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_CONVENIOS(?,?,?)",
                                                 parametros, true);
    }

    /**
     * @return
     * @throws SQLException
     */
    public static String pideDatoConvenio(String pCodigoConvenio) throws SQLException {

        //sqlArray = new ARRAY("","","");
        parametros1 = new ArrayList();
        parametros1.add(FarmaVariables.vCodGrupoCia);
        parametros1.add(FarmaVariables.vCodLocal);
        parametros1.add(FarmaVariables.vNuSecUsu);
        parametros1.add(pCodigoConvenio.trim());
        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_PIDE_DATO_CONV(?,?,?,?) :" + parametros1);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_PIDE_DATO_CONV(?,?,?,?)",
                                                           parametros1);
    }


    /**
     * @param pTableModel
     * @throws SQLException
     */
    public static void listaDiagnostico(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vDescDiagnostico);

        log.debug("VariablesConvenioBTLMF.vDescDiagnostico:" + VariablesConvenioBTLMF.vDescDiagnostico);


        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DIAGNOSTICO(?,?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DIAGNOSTICO(?,?,?,?)",
                                                 parametros, true);
    }


    /**
     * @return
     * @throws SQLException
     */
    public static String ObtieneDocVerificacion(String pCodigoConvenio, String pFlgRetencion,
                                                String vNomBenificiario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodigoConvenio.trim());
        parametros.add(pFlgRetencion);
        parametros.add(vNomBenificiario);

        log.debug("invocando  a PTOVENTA_CONV.BTLMF_F_CHAR_OBTIENE_DOC_VERIF(?,?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBTIENE_DOC_VERIF(?,?,?,?,?,?)",
                                                           parametros);
        // return "";
    }


    /**
     * @return
     * @throws SQLException
     */
    public static void listaMensajes(ArrayList lista, String pCodigoConvenio,
                                     String pFlgRetencion) throws SQLException {
        parametros1 = new ArrayList();
        parametros1.add(FarmaVariables.vCodGrupoCia);
        parametros1.add(FarmaVariables.vCodLocal);
        parametros1.add(FarmaVariables.vNuSecUsu);
        parametros1.add(pCodigoConvenio.trim());
        parametros1.add(pFlgRetencion);
        log.debug("invocando  a PTOVENTA_CONV.BTLMF_F_CHAR_OBTIENE_DATO(?,?,?,?,?,?) :" + parametros1);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lista,
                                                          "PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBTIENE_DATO(?,?,?,?,?)",
                                                          parametros1);
    }

    /**
     * @return
     * @throws SQLException
     */
    public static List listaDatosConvenio(String pCodigoConvenio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodigoConvenio.trim());

        log.debug("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DATOS_CONV(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DATOS_CONV(?,?,?,?)",
                                                               parametros);
    }


    /**
     * @return
     * @throws SQLException
     */
    public static Map obtienePantallaMensaje(String pNroResulucion, String posicion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pNroResulucion.trim());
        parametros.add(posicion.trim());

        log.debug("invocando  a PTOVENTA_CONV.BTLMF_F_CUR_LISTA_PANTALLA_MSG(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_PANTALLA_MSG(?,?,?,?,?)",
                                                           parametros);
    }


    /**
     * @param pTableModel
     * @throws SQLException
     */
    public static void listaDatos(FarmaTableModel pTableModel, boolean pConChek) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(VariablesConvenioBTLMF.vCodTipoCampo);
        parametros.add(VariablesConvenioBTLMF.vDescDiagnostico);


        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DATO(?,?,?,?,?,?) :" + parametros + ">>>>>>" +
                  pConChek);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DATO(?,?,?,?,?,?)", parametros,
                                                 pConChek);
    }


    /**
     * @return Map
     * @throws SQLException
     */
    public static Map obtieneBenificiario(String pCodigoConvenio, String pDni) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodigoConvenio.trim());
        parametros.add(pDni.trim());

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBTIENE_BENIF(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBTIENE_BENIF(?,?,?,?,?)",
                                                           parametros);

    }


    /**
     * @return Map
     * @throws SQLException
     */
    public static String existeBenificiario(String pCodigoConvenio, String pDni) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodigoConvenio.trim());
        parametros.add(pDni.trim());

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_EXISTE_BENIF(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_CONV_BTLMF.BTLMF_F_EXISTE_BENIF(?,?,?,?,?)",
                                                              parametros);

    }


    /**
     * @return Map
     * @throws SQLException
     */
    public static Map obtenerTarjeta(String pCodigoBarra) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodigoBarra);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_TARJ(?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_TARJ(?,?,?,?)",
                                                           parametros);

    }


    /**
     * @return Map
     * @throws SQLException
     */
    public static Map obtenerCliente(String pCodCliente) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(pCodCliente);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_CLIENTE(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_CLIENTE(?,?,?,?,?)",
                                                           parametros);

    }


    /**
     * @return Map
     * @throws SQLException
     */
    public static Map obtieneMedico(String pCodigoConvenio, String pCodMedico) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodigoConvenio);
        parametros.add(pCodMedico);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBTIENE_MEDICO(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBTIENE_MEDICO(?,?,?,?,?)",
                                                           parametros);

    }

    /**
     * @return Map
     * @throws SQLException
     */
    public static Map obtenerConvenio(String pCodigoConvenio) throws SQLException {
        parametros1 = new ArrayList();

        parametros1.add(FarmaVariables.vCodGrupoCia);
        parametros1.add(FarmaVariables.vCodLocal);
        parametros1.add(FarmaVariables.vNuSecUsu);

        if (pCodigoConvenio != null && !pCodigoConvenio.trim().equals(""))
            parametros1.add(pCodigoConvenio);
        else
            parametros1.add("0000000000");

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_CONVENIO(?,?,?,?) :" + parametros1);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_CONVENIO(?,?,?,?)",
                                                           parametros1);

    }

    /**
     * @return Map
     * @throws SQLException
     */
    public static Map obtieneDiagnostico(String pCodCIE10) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pCodCIE10);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_DIAGNOSTICO(?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_DIAGNOSTICO(?,?,?,?)",
                                                           parametros);

    }

    /**
     * @param pNombre
     * @param pApellidoPat
     * @param pApellidoMat
     * @param pDni
     * @param pTelefono
     * @param pEmail
     * @param pFechaNac
     * @throws SQLException
     */
    public static void solicitarBenificiario(String pNombre, String pApellidoPat, String pApellidoMat, String pDni,
                                             String pTelefono, String pEmail, String pFechaNac) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(pDni);
        parametros.add(pNombre);
        String pDes_ape_cliente = pApellidoPat + " " + pApellidoMat;
        parametros.add(pDes_ape_cliente);
        parametros.add(pEmail);
        parametros.add(pFechaNac);
        parametros.add(pTelefono);
        if (VariablesConvenioBTLMF.vFlgCreacionCliente.equals("N"))
            parametros.add("");
        else
            parametros.add(VariablesConvenioBTLMF.vFlgCreacionCliente);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_P_INSERT_BENIFICIARIO(?,?,?,?,?,?,?,?,?,?,?) :" +
                  parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONV_BTLMF.BTLMF_P_INSERT_BENIFICIARIO(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * @param pNombre
     * @param pApellidoPat
     * @param pApellidoMat
     * @param pDni
     * @param pTelefono
     * @param pEmail
     * @param pFechaNac
     * @throws SQLException
     */
    public static void crearBenificiarioTemp(String pNombre, String pApellidoPat, String pApellidoMat, String pDni,
                                             String pTelefono, String pEmail, String pFechaNac,
                                             String pCodCliente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(pDni);
        parametros.add(pNombre);
        String pDes_ape_cliente = pApellidoPat + " " + pApellidoMat;
        parametros.add(pDes_ape_cliente);
        parametros.add(pEmail);
        parametros.add(pFechaNac);
        parametros.add(pTelefono);
        parametros.add(VariablesConvenioBTLMF.vFlgCreacionCliente);
        parametros.add(pCodCliente.trim());
        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_P_INSERT_BENIFICIARIO(?,?,?,?,?,?,?,?,?,?,?,?) :" +
                  parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONV_BTLMF.BTLMF_P_INSERT_BENIFICIARIO(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void crearBenificiarioNew(String pNombre, String pApellidoPat, String pApellidoMat, String pDni,
                                             String pTelefono, String pEmail, String pFechaNac,
                                             String pCodCliente, String pLineaCredito, String pNroTarjeta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(pCodCliente.trim());
        parametros.add(pApellidoPat + " " + pApellidoMat);
        parametros.add(pNombre);
        parametros.add("A");
        parametros.add(pDni);
        parametros.add(new Double(pLineaCredito)); //LineaCredito //A_IMP_LIN_CRED
        if(pNroTarjeta != null){
            parametros.add(pNroTarjeta);
        }else{
            parametros.add("");
        }
        
        
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONV_BTLMF.BTLMF_P_INSERT_BENIF_NEW(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }
    
    public static String crearBenificiario(String pNombre, String pApellidoPat, String pApellidoMat, String pDni,
                                           String pTelefono, String pEmail, String pFechaNac, String pCodigoRef,
                                           String pLineaCredito) throws Exception {

        String codCliente = "";
        ArrayList parametros = new ArrayList();

        parametros.add("10");  // A_CIA
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);   //A_COD_CONVENIO
        parametros.add(codCliente); //Retorna Codigo Cliente Null //A_COD_CLIENTE
        parametros.add(pDni); // A_NUM_DNI
        parametros.add(pApellidoPat); // A_APE_PATERNO
        parametros.add(pApellidoMat); // A_APE_MATERNO
        parametros.add(pNombre); // A_DES_NOMBRES
        //parametros.add(" "); //Descripcion Cargo // A_DES_CARGO
        parametros.add(new Double(pLineaCredito)); //LineaCredito //A_IMP_LIN_CRED
        //parametros.add("0"); //A_FLG_CAMB_TEMP_LIN_CRED CMR.MAE_BENEFICIARIO.FLG_CAMB_TEMP_LIN_CRE%TYPE, 0 // A_FLG_CAMB_TEMP_LIN_CRED
        //parametros.add(new Double("0")); //A_IMP_TMP_LIN_CRED       CMR.MAE_BENEFICIARIO.IMP_LINEA_CREDITO%TYPE, 0 // A_IMP_TMP_LIN_CRED
        //parametros.add(" "); //A_FCH_INI_TMP_LIN_CRED   VARCHAR2, //null  // A_FCH_INI_TMP_LIN_CRED
        //parametros.add(" "); //A_FCH_FIN_TMP_LIN_CRED   VARCHAR2, //null  // A_FCH_FIN_TMP_LIN_CRED
        //parametros.add(" "); //A_DES_OBSERVACION        CMR.MAE_BENEFICIARIO.DES_OBSERVACION%TYPE, null  // A_DES_OBSERVACION
        //parametros.add(pCodigoRef); //A_COD_EMPLEADO           CMR.MAE_BENEFICIARIO.COD_REFERENCIA%TYPE, // A_COD_EMPLEADO
        parametros.add("1"); //A_FLG_ACTIVO             CMR.MAE_BENEFICIARIO.FLG_ACTIVO%TYPE, 1 // A_FLG_ACTIVO
        parametros.add("99999"); //A_COD_USUARIO            CMR.MAE_BENEFICIARIO.COD_USUARIO%TYPE, 99999   // A_COD_USUARIO
        //parametros.add("001"); //A_COD_ESTADO_CIVIL       CMR.MAE_CLIENTE.COD_ESTADO_CIVIL%TYPE, null   // A_COD_ESTADO_CIVIL
        parametros.add(pFechaNac); //A_FCH_NACIMIENTO         CHAR,                                     // A_FCH_NACIMIENTO
        parametros.add(pEmail); //A_DES_EMAIL              CMR.MAE_CLIENTE.DES_EMAIL%TYPE,              // A_DES_EMAIL
        parametros.add("S"); //A_FLG_COMMIT             CHAR DEFAULT 'N',                               // A_FLG_COMMIT
        parametros.add("0"); //A_TIPO_TRANSACC          CHAR,                                           // A_TIPO_TRANSACC
        //parametros.add();                                                                               // A_COD_ZONAL
        //parametros.add();                                                                               // A_COD_LOCAL
        parametros.add(FarmaVariables.vCodLocal);
        
        codCliente = FarmaDBUtilityRemoto.executeSQLStoredProcedureStrInOut("BTLPROD.PKG_BENEFICIARIO.SP_GRABA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                       parametros, 3, FarmaConstants.CONECTION_RAC,
                                                                       FarmaConstants.INDICADOR_S);
        
        return codCliente;


    }


    /**
     * @return Map
     * @throws SQLException
     */
    public static String imprimirMensaje(String mensaje) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(mensaje);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_IMPRIMIR(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_IMPRIMIR(?,?,?,?,?)",
                                                              parametros);

    }


    /**
     * @return Map
     * @throws SQLException
     */
    public static Map imprimirVoucher(String pNroPedidoVta, String pCodigoBarra) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(pNroPedidoVta);
        parametros.add(pCodigoBarra);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_IMPR_VOUCHER(?,?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_IMPR_VOUCHER(?,?,?,?,?,?)",
                                                           parametros);

    }

    /**
     * @param pCodCampo
     * @param pDesCampo
     * @throws SQLException
     */
    public static void grabarPedidoVenta(String pCodCampo, String pDesCampo, String pNombCampo,
                                         String pCodValorCampo) throws SQLException {

        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesVentas.vNum_Ped_Vta);
        parametros.add(pCodCampo);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        if (VariablesConvenioBTLMF.vCodCliente == null)
            parametros.add("");
        else
            parametros.add(VariablesConvenioBTLMF.vCodCliente);

        parametros.add(FarmaVariables.vIdUsu);
        if (pDesCampo == null)
            parametros.add("");
        else
            parametros.add(pDesCampo);

        parametros.add(pNombCampo);
        if (pCodValorCampo == null)
            parametros.add("0");
        else
            parametros.add(pCodValorCampo);
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            parametros.add("S");
        }else{
            parametros.add("N");
        }

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_P_INSERT_PED_VTA(?,?,?,?,?,?,?,?,?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CONV_BTLMF.BTLMF_P_INSERT_PED_VTA(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * @param pCodCampo
     * @param pDesCampo
     * @throws SQLException
     */
    public static double obtieneComsumoBenif(String pIndCloseConecction) throws SQLException {


        parametros = new ArrayList();

        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(VariablesConvenioBTLMF.vCodCliente);
        parametros.add("10");
        parametros.add("005");
        log.debug("invocando  a btlprod.pkg_beneficiario.FN_RET_CONSUMO_BENEFICIARIO(?,?,?,?) :" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureDouble("btlprod.pkg_beneficiario.FN_RET_CONSUMO_BENEFICIARIO(?,?,?,?)",
                                                                    parametros, FarmaConstants.CONECTION_RAC,
                                                                    pIndCloseConecction);
    }


    /**
     * @return
     * @throws SQLException
     */
    public static String esActivoConvenioBTLMF() throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(ConstantsConvenioBTLMF.ID_TBL_GENERAL_CONV_BTLMF);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_ES_ACTIVO_CONV(?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_ES_ACTIVO_CONV(?)",
                                                           parametros);
    }

    public static String getPrecioProdConv(String pCodprod) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(pCodprod);
        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBT_PRECIO_CONV(?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBT_PRECIO_CONV(?,?,?,?)",
                                                           parametros);

    }

    public static String getEstadoProdConv(String pCodprod) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCodprod);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        log.debug("parametros PKG_PRODUCTO.FN_DEV_APTO_CONV(?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_PRODUCTO.FN_DEV_APTO_CONV(?,?)", parametros);

    }

    /**
     * Lista las Formas de Pago Convenio BTLMF
     * @author : framirez
     * @since  : 12.01.2012
     * */
    public static void obtieneFormasPagoConvenio(FarmaTableModel pTableModel, String codConvenio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(codConvenio);
        log.debug("invocando a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LIST_FORM_PAG_CONV(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LIST_FORM_PAG_CONV(?,?,?,?)",
                                                 parametros, false);

    }


    public static String cobraPedido() throws SQLException {
        log.debug("VariablesVentas.vTipoPedido : " + VariablesVentas.vTipoPedido);
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(VariablesCaja.vSecMovCaja);
        parametros.add(FarmaConstants.COD_NUMERA_SEC_COMP_PAGO);
        if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON))
            parametros.add(ConstantsPtoVenta.MOT_KARDEX_VENTA_NORMAL);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_DELIVERY))
            parametros.add(ConstantsPtoVenta.MOT_KARDEX_VENTA_DELIVERY);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
            parametros.add(ConstantsPtoVenta.MOT_KARDEX_VENTA_ESPECIAL);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_VENTA);
        parametros.add(FarmaConstants.COD_NUMERA_SEC_KARDEX);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesConvenio.vPorcCoPago);
        //parametros.add(VariablesCaja.vDescripcionDetalleFormasPago);
        parametros.add("");
        parametros.add(UtilityEposTrx.validacionEmiteElectronico() == true ? "1" :
                       "0"); //LTAVARA 27.08.2014 AGREGAR EL FLAG DE PROCESO ELECTRONICO

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_COBRA_PEDIDO (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);

    }

    /**
     * @return Map
     * @throws SQLException
     */
    public static Map obtenerConvenioXPedido(String pNroPedido) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pNroPedido);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_CONV_PEDIDO(?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_OBT_CONV_PEDIDO(?,?,?,?)",
                                                           parametros);

    }


    /**
     * @param  monto
     * @param  nroPedido
     * @param  codConvenio
     * @throws SQLException
     */

    public static double obtieneMontoCredito(Double monto, String nroPedido, String codConvenio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(monto);
        parametros.add(nroPedido);
        parametros.add(codConvenio);

        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_FLOAT_OBT_MONTO_CREDITO(?,?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_CONV_BTLMF.BTLMF_FLOAT_OBT_MONTO_CREDITO(?,?,?,?,?)",
                                                              parametros);

    }


    public static String obtieneFormaPago(String cCodFormaPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(cCodFormaPago);

        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBT_FORM_PAGO(?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBT_FORM_PAGO(?,?,?,?)",
                                                              parametros);

    }

    public static void obtieneCompPagos(ArrayList pArrayList, String pTipClienteConv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pTipClienteConv);
        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_COMP_PAGO(?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_COMP_PAGO (?,?,?,?)",
                                                          parametros);
    }

    public static void obtieneDetalleCompPagos(ArrayList pArrayList, String pSecCompPago, String pTipCompPag,
                                               String pTipClieConv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pSecCompPago);
        parametros.add(pTipCompPag);
        parametros.add(pTipClieConv);
        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LIST_DET_COMP_PAGO(?,?,?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LIST_DET_COMP_PAGO(?,?,?,?,?,?)",
                                                          parametros);
    }


    public static String obtieneRutaImpresoraVenta(String pTipoCompPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipoCompPago);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.CAJ_OBTIENE_RUTA_IMPRESORA(?,?,?)",
                                                           parametros);
    }

    public static List listaDatosConvenioAdicXpedido() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);

        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_PED_VTA(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_PED_VTA(?,?,?)",
                                                               parametros);
    }


    /**
     * @param pCodCampo
     * @param pDesCampo
     * @throws SQLException
     */
    public static String grabarCobrarPedidoRac(String pIndCloseConecction, String pIndicadorNC) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pIndicadorNC);
        parametros.add(FarmaConstants.INDICADOR_S);

        log.debug("BTLPROD.PKG_DOCUMENTO_MF.SP_GRABA_DOCUMENTOS(?,?,?,?,?)" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("BTLPROD.PKG_DOCUMENTO_MF.SP_GRABA_DOCUMENTOS(?,?,?,?,?)",
                                                                 parametros, FarmaConstants.CONECTION_RAC,
                                                                 pIndCloseConecction);

    }

    public static String grabarCobrarPedidoRac(String pIndCloseConecction) throws SQLException {
        return grabarCobrarPedidoRac(pIndCloseConecction, FarmaConstants.INDICADOR_N);
    }

    /**
     * @since 2.4.7 ERIOS Se agrega pIndNotaCred
     * @param pCodCampo
     * @param pDesCampo
     * @throws SQLException
     */
    public static String anularPedidoRac(String pIndNotaCred, String pIndCloseConecction) throws SQLException {

        parametros = new ArrayList();
        parametros.add(VariablesConvenioBTLMF.vTipoCompPago);
        parametros.add(VariablesConvenioBTLMF.vNumCompPago);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pIndNotaCred);
        
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("BTLPROD.PKG_DOCUMENTO_MF.SP_ANULA_DOCUMENTO(?,?,?,?)",
                                                                 parametros, FarmaConstants.CONECTION_RAC,
                                                                 pIndCloseConecction);
    }


    /**
     * @param pCodCampo
     * @param pDesCampo
     * @throws SQLException
     */
    public static void aceptarTransaccionRempota(FarmaTableModel pTableModel,
                                                 String pIndCloseConecction) throws SQLException {

        parametros = new ArrayList();
        log.debug("invocando  a BTLPROD.PKG_DOCUMENTO_MF.ACEPTAR_TRANSACCION :" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(pTableModel, "BTLPROD.PKG_DOCUMENTO_MF.ACEPTAR_TRANSACCION",
                                                       parametros, true, FarmaConstants.CONECTION_RAC,
                                                       pIndCloseConecction);
    }

    /**
     * @param pCodCampo
     * @param pDesCampo
     * @throws SQLException
     */
    public static void liberarTransaccionRempota(FarmaTableModel pTableModel,
                                                 String pIndCloseConecction) throws SQLException {


        parametros = new ArrayList();
        log.debug("invocando  a BTLPROD.PKG_DOCUMENTO_MF.LIBERAR_TRANSACCION :" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(pTableModel, "BTLPROD.PKG_DOCUMENTO_MF.LIBERAR_TRANSACCION",
                                                       parametros, true, FarmaConstants.CONECTION_RAC,
                                                       pIndCloseConecction);

    }


    public static void actualizaFechaProcesoRac() throws SQLException {
        actualizaFechaProcesoRac(VariablesCaja.vNumPedVta);
    }

    /**
     * @author kmoncada
     * @since 26.06.2014
     * @param numPedVtaNC numero del pedido de venta que genera la Nota de Credito.
     * @throws SQLException
     */
    public static void actualizaFechaProcesoRac(String numPedVtaNC) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedVtaNC);
        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_P_ACT_FECHA_PROC_RAC(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CONV_BTLMF.BTLMF_P_ACT_FECHA_PROC_RAC(?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaFechaProcesoAnulaRac() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta_Anul);
        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_P_AC_FEC_PROC_ANU_RAC(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CONV_BTLMF.BTLMF_P_AC_FEC_PROC_ANU_RAC(?,?,?)",
                                                 parametros, false);
    }

    /**
     * @return
     * @throws SQLException
     */
    public static String esDiaVigenciaReceta(String fechaVigenciaReceta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(fechaVigenciaReceta);
        log.debug("invocando  a PTOVENTA_CONV.BTLMF_F_CHAR_ES_DIA_VIG_RECTA(?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_ES_DIA_VIG_RECTA(?)",
                                                           parametros);
    }


    /**
     * @return
     * @throws SQLException
     */
    public static String existeProdConvenio() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_EXISTE_PROD_CONV(?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_EXISTE_PROD_CONV(?,?)",
                                                           parametros);
    }

    public static String getMsgComprobante(String pCodConvenio, double monto, double pSelCopago) throws SQLException {
        //ERIOS 2.2.8 Calculo de copago variable
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodConvenio);
        parametros.add(FarmaUtility.formatNumber(monto));
        parametros.add(pSelCopago);
        log.debug("PTOVENTA_CONV_BTLMF.BTLMF_F_VARCHAR_MSG_COMP(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_VARCHAR_MSG_COMP(?,?,?,?)",
                                                           parametros);
    }

    public static String geTipoConvenio(String pCodConvenio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCodConvenio);

        log.debug("invocando  a PTOVENTA_CONV.BTLMF_F_CHAR_OBT_TIP_CONVENIO(?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBT_TIP_CONVENIO(?)",
                                                           parametros);
    }

    public static Map obtieneMsgCompPagoImpr() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesConvenioBTLMF.vTipoCompPagoAux);
        parametros.add(VariablesCaja.vNumPedVta);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBT_MSJ_COMP_IMPR(?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_OBT_MSJ_COMP_IMPR(?,?,?,?)",
                                                           parametros);
    }

    public static String validaLineaCredito() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);

        log.debug("ptoventa_utility_conv.get_is_ped_valida_rac(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("ptoventa_utility_conv.get_is_ped_valida_rac(?,?,?)",
                                                           parametros);
    }


    public static String esCompCredito() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta_Anul);
        parametros.add(VariablesCaja.vTipComp_Anul);
        parametros.add(VariablesCaja.vNumComp_Anul);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_ES_COMP_CREDITO(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_ES_COMP_CREDITO(?,?,?,?,?)",
                                                           parametros);
    }

    //metodo imprimir ticket de anulacion

    public static String ImprimeMensajeAnulacion(String cajero, String turno, String numpedido, String cod_igv,
                                                 String pIndReimpresion, String numComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cajero);
        parametros.add(turno);
        parametros.add(numpedido);
        parametros.add(cod_igv);
        parametros.add(pIndReimpresion);
        parametros.add(numComp);
        log.debug("PTOVENTA_CONV_BTLMF.CAMP_F_VAR_MSJ_ANULACION(?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.CAMP_F_VAR_MSJ_ANULACION(?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String pideCopagoConvenio(String pCodigoConvenio) throws SQLException {

        //sqlArray = new ARRAY("","","");
        parametros1 = new ArrayList();
        parametros1.add(pCodigoConvenio.trim());
        log.debug("invocando  a PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_PIDE_COPAGO_CONV(?) :" + parametros1);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_F_CHAR_PIDE_COPAGO_CONV(?)",
                                                           parametros1);
    }

    /**
     * Obtiene comprobantes que emite el convenio
     * @author ERIOS
     * @since 23.04.2014
     * @return
     * @throws SQLException
     */
    public static String getCompConvenio() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        log.debug("PTOVENTA_CONV_BTLMF.GET_COMP_CONVENIO(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.GET_COMP_CONVENIO(?,?,?,?)",
                                                           parametros);
    }

    /**
     *
     * @author ERIOS
     * @since 2.3.3
     * @return
     * @throws SQLException
     */
    public static int obtieneCantListaBenefRemoto() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        //parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(VariablesConvenioBTLMF.vDescDiagnostico);

        log.debug("PTOVENTA.PTOVENTA_MATRIZ_CONV_BTLMF.GET_CANT_LISTA_BENEFICIARIO(?,?,?,?,?)" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureInt("PTOVENTA.PTOVENTA_MATRIZ_CONV_BTLMF.GET_CANT_LISTA_BENEFICIARIO(?,?,?,?,?)",
                                                                 parametros, FarmaConstants.CONECTION_RAC,
                                                                 FarmaConstants.INDICADOR_S);
    }

    /**
     *
     * @author ERIOS
     * @since 2.3.3
     * @return
     * @throws SQLException
     */
    public static int obtieneCantListaBenefLocal() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        //parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(VariablesConvenioBTLMF.vDescDiagnostico);

        log.debug("PTOVENTA.PTOVENTA_CONV_BTLMF.GET_CANT_LISTA_BENEFICIARIO(?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA.PTOVENTA_CONV_BTLMF.GET_CANT_LISTA_BENEFICIARIO(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     *
     * @author ERIOS
     * @since 2.3.3
     * @return
     * @throws SQLException
     */
    public static String getIndBeneficiarioLinea() throws SQLException {
        parametros = new ArrayList();

        parametros.add(VariablesConvenioBTLMF.vCodConvenio);

        log.debug("PTOVENTA_CONV_BTLMF.GET_INDICADOR_BENEF_LINEA(?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.GET_INDICADOR_BENEF_LINEA(?)",
                                                           parametros);

    }

    public static void obtieneInfoProductoSug(ArrayList pArrayList, String pCodProd,
                                              String cantVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(new Integer(cantVta));
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        log.debug("PTOVENTA_CONV_BTLMF.VTA_OBTIENE_PROD_SUG(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CONV_BTLMF.VTA_OBTIENE_PROD_SUG(?,?,?,?,?)",
                                                          parametros);
    }


    public static List listaDocumentosVerificaRAC() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);

        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_CONV_BTLMF.COMP_CREDITO_VALIDA_RAC(?,?,?)",
                                                               parametros);
    }


    public static String validacionPedidoRAC(String pCodLocal, String pNumPedVta, String pCodConvenio,
                                              String pCodCliente, String pTipoDoc, double pMonto,
                                              String pVtaFin, String indSoloValida) throws SQLException {

        ArrayList parametros = new ArrayList();
        parametros.add(pTipoDoc);
        parametros.add(pCodLocal);
        parametros.add(pCodCliente);
        parametros.add(pCodConvenio);
        parametros.add(pVtaFin);
        parametros.add(pMonto);
        parametros.add(pNumPedVta);
        parametros.add(indSoloValida);

        String resultado =
            FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("BTLPROD.PKG_DOCUMENTO_MF.VALIDAR_COBRO_PEDIDO(?,?,?,?,?,?,?,?)",
                                                              parametros, FarmaConstants.CONECTION_RAC, "S");
        return resultado;
    }

    /**
     * KMONCADA 20.10.2014 VALIDA SI NUMERO ASIGNADO POR EPOS YA FUE REGISTRADO.
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pTipoComprobante
     * @param pNumComprobante
     * @return true si existe nro de comprobante, false caso contrario.
     * @throws SQLException
     */
    public static int isExisteComprobanteEnRAC(String pCodGrupoCia, String pCodLocal, String pTipoComprobante,
                                                   String pNumComprobante,
                                                   String pIndComprobanteElectronico) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pTipoComprobante);
        parametros.add(pNumComprobante);
        parametros.add(pIndComprobanteElectronico);
        
        int resultado =
            FarmaDBUtilityRemoto.executeSQLStoredProcedureInt("BTLPROD.PKG_DOCUMENTO_MF.FN_EXISTE_DOCUMENTO(?,?,?,?,?)",
                                                              parametros, FarmaConstants.CONECTION_RAC, "S");
        return resultado;
    }

    public static void obtieneCompPagosImpresion(ArrayList pArrayList, String pTipClienteConv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pTipClienteConv);
        log.debug("parametros PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LST_COMP_PAGO_IMPR(?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LST_COMP_PAGO_IMPR (?,?,?,?)",
                                                          parametros);
    }

    public static String getComprobanteBeneficiario(String pCodConvenio) throws Exception {
        ArrayList parametro = new ArrayList();
        parametro.add(pCodConvenio);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.F_CHAR_EMITE_BENEFICIARIO(?)",
                                                           parametro);
    }
    
    public static String getIndPedidoConvenioBTLMF(String pcCodGrupoCia, String pCodLocal, String pNumPedVta)throws Exception{
        ArrayList parametro = new ArrayList();
        parametro.add(pcCodGrupoCia);
        parametro.add(pCodLocal);
        parametro.add(pNumPedVta);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.F_C_IND_PEDIDO_CONVENIO(?,?,?)", parametro);
    }
}
