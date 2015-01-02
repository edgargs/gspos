package mifarma.electronico.epos.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBEpos {

    static private final Logger log = LoggerFactory.getLogger(DBEpos.class);


    public DBEpos() {
    }

    /**
     * Método
     * @param cNumPedidoVta       Define el número del pedido del comprobante;
     * @param cSecCompPago        Define el código secuencial del comprobante;
     * @param tipoDocumento       Define el tipo de documento;
     * @param numeroCompPagoE     Define el número de comprobante electrónico;
     * @return respuesta          Define repuesta que da el EPOS cuando se envia el mensaje de ConfirmacionDE.
     */
    public static String getDocumentoElectronico(String cGrupoCia, String cCodLocal, String cNumPedidoVta,
                                                 String cSecCompPago, String cTipoDocumento,
                                                 String cTipoClienteConvenio) throws Exception {
        ArrayList vParameters = new ArrayList();
        vParameters.add(cGrupoCia);
        vParameters.add(cCodLocal);
        vParameters.add(cNumPedidoVta);
        vParameters.add(cSecCompPago);
        vParameters.add(cTipoDocumento);
        vParameters.add(cTipoClienteConvenio);

        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_CAB(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_DOC(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_NOTAS(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_DET(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_RG(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_IG(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_REF(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_MSJ_BF(?,?,?,?,?,?); end;" + vParameters);
        log.info("begin ? := FARMA_EPOS.F_VAR_TRM_GET_MSJ_AF(?,?,?,?,?,?); end;" + vParameters);

        String pUNO_Cab =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_CAB(?,?,?,?,?,?)", vParameters);
        String pDOS_Doc =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_DOC(?,?,?,?,?,?)", vParameters);
        String pTRES_Not =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_NOTAS(?,?,?,?,?,?)", vParameters);
        //String pCUATRO_Det = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_DET(?,?,?,?,?,?)", vParameters);

        String pCUATRO_Det = "";
        List lstDetalle =
            FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_EPOS.F_VAR_TRM_GET_DET(?,?,?,?,?,?)", vParameters);
        if (lstDetalle.size() == 0) {
            throw new Exception("Trama sin Detalle");
        } else {
            Map mapDetalle = new HashMap();
            String valor;
            for (int i = 0; i < lstDetalle.size(); i++) {
                mapDetalle = (HashMap)lstDetalle.get(i);
                valor = (String)mapDetalle.get("VALOR");
                pCUATRO_Det = pCUATRO_Det + valor + "\n";
            }
        }

        // NO SE USARA PORQUE YA EXISTE UN TAG NUEVO DE ANTICIPO
        // PP >> pOCHO_PP
        /*
        String pCINCO_RG =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_RG(?,?,?,?,?,?)", vParameters);
        */
        String pSEIS_IG =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_IG(?,?,?,?,?,?)", vParameters);
        String pSIETE_REF =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_REF(?,?,?,?,?,?)", vParameters);
        String pOCHO_PP =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_PP(?,?,?,?,?,?)", vParameters);

        String pNUEVE_MSJ_BF =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_MSJ_BF(?,?,?,?,?,?)", vParameters);
        String pDIEZ_MSJ_AF =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_MSJ_AF(?,?,?,?,?,?)", vParameters);
        // LTAVARA 17.11.2014 MENSAJES PERZONALIZADOS A LA TRAMA
        String pONCE_MSJ_PERZONALIZADO =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_TRM_GET_MSJ_PERSONALIZA(?,?,?,?,?,?)",
                                                        vParameters);

        if (pUNO_Cab != null)
            log.info(" pUNO_Cab  " + pUNO_Cab.trim().length());
        if (pDOS_Doc != null)
            log.info(" pDOS_Doc  " + pDOS_Doc.trim().length());
        if (pTRES_Not != null)
            log.info(" pTRES_Not  " + pTRES_Not.trim().length());
        if (pCUATRO_Det != null)
            log.info(" pCUATRO_Det  " + pCUATRO_Det.trim().length());
        //
        //if (pCINCO_RG != null)
        //    log.info(" pCINCO_RG  " + pCINCO_RG.trim().length());
        if (pSEIS_IG != null)
            log.info(" pSEIS_IG  " + pSEIS_IG.trim().length());
        if (pSIETE_REF != null)
            log.info(" pSIETE_REF  " + pSIETE_REF.trim().length());
        //if (pOCHO_PP != null)
        //    log.info(" pOCHO_MSJ_BF  " + pOCHO_PP.trim().length());
        if (pNUEVE_MSJ_BF != null)
            log.info(" pNUEVE_MSJ_BF  " + pNUEVE_MSJ_BF.trim().length());
        if (pDIEZ_MSJ_AF != null)
            log.info(" pNUEVE_MSJ_AF  " + pDIEZ_MSJ_AF.trim().length());
        // LTAVARA 17.11.2014 MENSAJES PERZONALIZADOS A LA TRAMA
        if (pONCE_MSJ_PERZONALIZADO != null)
            log.info(" pONCE_MSJ_PERZONALIZA  " + pONCE_MSJ_PERZONALIZADO.trim().length());

        if (pUNO_Cab == null || pUNO_Cab.trim().length() == 0)
            throw new Exception("SIN DATOS trama de Cabecera");
        if (pDOS_Doc == null || pDOS_Doc.trim().length() == 0)
            throw new Exception("SIN DATOS trama de Documento");
        if (pTRES_Not == null || pTRES_Not.trim().length() == 0)
            throw new Exception("SIN DATOS trama de Nota");
        if (pCUATRO_Det == null || pCUATRO_Det.trim().length() == 0)
            throw new Exception("SIN DATOS trama de Detalle");

        //if (pCINCO_RG == null || pCINCO_RG.trim().length() == 0)
        //    pCINCO_RG = ""; //throw new Exception("SIN DATOS trama de Recargo Global");
        if (pSIETE_REF == null || pSIETE_REF.trim().length() == 0)
            pSIETE_REF = ""; // throw new Exception("SIN DATOS trama de Referencia");

        if (pOCHO_PP == null || pOCHO_PP.trim().length() == 0)
            pOCHO_PP = ""; // throw new Exception("SIN DATOS trama de Referencia");

        if (pNUEVE_MSJ_BF == null || pNUEVE_MSJ_BF.trim().length() == 0)
            throw new Exception("SIN DATOS trama de Mensaje Antes");
        if (pDIEZ_MSJ_AF == null || pDIEZ_MSJ_AF.trim().length() == 0)
            throw new Exception("SIN DATOS trama de Mensaje Despues");

        return pUNO_Cab + pDOS_Doc + pTRES_Not + pCUATRO_Det +
            //pCINCO_RG +
            pSEIS_IG + pSIETE_REF + /*+ pOCHO_PP*/pNUEVE_MSJ_BF + pDIEZ_MSJ_AF + pONCE_MSJ_PERZONALIZADO;

    }

    /* *************************************************************************************************************** */


    /* *************************************************************************************************************** */

    public static Integer updateMsjNum_E(String cGrupoCia, String cCodLocal, String cNumPedidoVta, String cSecCompPago,
                                         String tramaRespuesta) throws Exception {
        ArrayList vParameters = new ArrayList();
        vParameters.add(cGrupoCia);
        vParameters.add(cCodLocal);
        vParameters.add(cNumPedidoVta);
        vParameters.add(cSecCompPago);
        vParameters.add(tramaRespuesta);
        log.info("begin ? := FARMA_EPOS.F_NUM_INST_TRM_MSJ_NUM_E(?,?,?,?,?); end;" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_EPOS.F_NUM_INST_TRM_MSJ_NUM_E(?,?,?,?,?)",
                                                           vParameters);
    }


    public static Integer updateMsjConf_E(String cGrupoCia, String cCodLocal, String cNumPedidoVta,
                                          String cSecCompPago, String tramaRespuesta) throws Exception {
        ArrayList vParameters = new ArrayList();
        vParameters.add(cGrupoCia);
        vParameters.add(cCodLocal);
        vParameters.add(cNumPedidoVta);
        vParameters.add(cSecCompPago);
        vParameters.add(tramaRespuesta);
        log.info("begin ? := FARMA_EPOS.F_NUM_INST_TRM_MSJ_CONF_E(?,?,?,?,?); end;" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_EPOS.F_NUM_INST_TRM_MSJ_CONF_E(?,?,?,?,?)",
                                                           vParameters);
    }


    public static String getNumCompEFormato(String numCompE) throws Exception {
        ArrayList vParameters = new ArrayList();
        vParameters.add(numCompE);
        log.info("begin ? := FARMA_EPOS.F_VAR_GET_FORMAT_COMP_E(?); end;" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_GET_FORMAT_COMP_E(?)", vParameters);
    }

    public static String updTipCompPago(String vTipCompPaqo, String cSecCompPago) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(cSecCompPago);
        parametros.add(vTipCompPaqo);
        log.debug("PTOVENTA_CAJ.CAJ_MODIFICAR_TIP_COMP_PAGO(?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_MODIFICAR_TIP_COMP_PAGO(?,?,?,?,?)",
                                                           parametros);
    }

    public static String getNumCompPagoE(String pNumPedVta, String cSecCompPago) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(cSecCompPago);
        log.debug("FARMA_EPOS.F_VAR_GET_NUM_COMP_E(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_GET_NUM_COMP_E(?,?,?,?)", parametros);
    }

    /**
     *
     * **/
    public static String getTipProcesPago(String vNumCompPago) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesVentas.vNumPedVta_new);
        parametros.add(vNumCompPago);
        log.debug("FARMA_EPOS.F_VAR_GET_TIP_PROC_CPAGO(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_GET_TIP_PROC_CPAGO(?,?,?,?)", parametros);
    }


    /**
     * Actualiza el flag de proceso electronico a todos los comprobantes generados
     * */
    public static void updFlagCompElectronico(String numPedidoVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedidoVta);
        String result =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_UPD_FLAG_COMP_E(?,?,?)", parametros);


    }

    /**
     * Obtener indicador si el local emite documento electronico
     * */
    public static String getIndElectronicoLocal() throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        String result =
            FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.F_VAR_IS_ACT_FUNC_E(?,?,?)", parametros);

        return result;
    }


    public static void updDocECommit(String cGrupoCia, String cCodLocal, String cNumPedidoVta, String cSecCompPago,
                                     String numCompPagoE, String pdf417, String pTipComp) throws Exception {

        ArrayList vParameters = new ArrayList();
        vParameters.add(cGrupoCia);
        vParameters.add(cCodLocal);
        vParameters.add(cNumPedidoVta);
        vParameters.add(cSecCompPago);
        vParameters.add(numCompPagoE);
        vParameters.add(pdf417);
        vParameters.add(pTipComp);


        FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_EPOS.FN_MODIFICAR_DOCUMENTO_E(?,?,?,?,?,?,?)",
                                                 vParameters, false);
    }


    public static void updDocE(String cGrupoCia, String cCodLocal, String cNumPedidoVta, String cSecCompPago,
                               String numCompPagoE, String pdf417, String pTipComp) throws Exception {

        ArrayList vParameters = new ArrayList();
        vParameters.add(cGrupoCia);
        vParameters.add(cCodLocal);
        vParameters.add(cNumPedidoVta);
        vParameters.add(cSecCompPago);
        vParameters.add(numCompPagoE);
        vParameters.add(pdf417);
        vParameters.add(pTipComp);


        FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_EPOS.FN_MODIFICAR_DOC_NUM_E(?,?,?,?,?,?,?)", vParameters,
                                                 false);
    }

    /**
     * OBTIENE EL MSJ HA MOSTRAR EN CASOS DE EMISION DE COMPROBANTES EN CONTINGENCIA
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param pCodTipoComprobante
     * @return
     * @throws SQLException
     */
    public static Map obtieneMsgContingencia(String pCodGrupoCia, String pCodLocal, String pNumPedVta,
                                             String pCodTipoComprobante) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pCodTipoComprobante);

        log.info("FARMA_EPOS.F_MSJ_CONTIGENCIA(?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("FARMA_EPOS.F_MSJ_CONTIGENCIA(?,?,?,?)", parametros);
    }

    /**
     * VALIDACION DE DOCUMENTOS PRE-IMPRESOS (CONTIGENCIA) PENDIENTE DE IMPRESION
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pSecCompPago
     * @param pCodTipoComprobante
     * @return
     * @throws SQLException
     */
    public static Map validaImpresionPendiente(String pCodGrupoCia, String pCodLocal, String pSecCompPago,
                                               String pCodTipoComprobante) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pSecCompPago);
        parametros.add(pCodTipoComprobante);

        log.info("FARMA_EPOS.VALIDA_IMPRESION_PENDIENTE(?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("FARMA_EPOS.VALIDA_IMPRESION_PENDIENTE(?,?,?,?)",
                                                           parametros);
    }

    /**
     * DESHABILITA EL FLAG DE ELECTRONICO DEL COMPROBANTE EN CASO DE QUE SE EMITA COMO CONTIGENCIA
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param pSecCompPago
     * @throws Exception
     */
    public static void deshabilitarFlagEComprobante(String pCodGrupoCia, String pCodLocal, String pNumPedVta,
                                                    String pSecCompPago) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pSecCompPago);
        log.info("FARMA_EPOS.COMP_PAGO_CAMBIA_FLAG_E(?,?,?,?) :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_EPOS.COMP_PAGO_CAMBIA_FLAG_E(?,?,?,?)", parametros,
                                                 false);
    }

}
