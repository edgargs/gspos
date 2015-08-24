package mifarma.ptoventa.puntos.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBPuntos {

    private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(DBPuntos.class);


    public DBPuntos() {
    }


    public static String getCodAutorizacion() throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("FARMA_PUNTOS.F_VAR_KEY_ORBIS(?,?)"+ parametros);                        
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_COD_AUTORIZACION(?,?)", parametros);
        return pRes.trim();
    }
    public static String getWsOrbis() throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("FARMA_PUNTOS.F_VAR_KEY_ORBIS(?,?)"+ parametros);                        
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_WS_ORBIS(?,?)", parametros);
        return pRes.trim();
    }
    public static String getIndActPuntos() throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("FARMA_PUNTOS.F_VAR_IND_ACT_PUNTOS(?,?)"+ parametros);                
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_IND_ACT_PUNTOS(?,?)", parametros);
        return pRes.trim();
    }
    
    public static String isTarjetaValida(String pTarjeta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        if(pTarjeta!=null)
            parametros.add(pTarjeta);
        else
            parametros.add("");
        log.info("FARMA_PUNTOS.F_VAR_TARJ_VALIDA(?,?,?)"+parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_TARJ_VALIDA(?,?,?)", parametros);
        return pRes.trim();
    }    
    
    public static String getDniUsuario() throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.info("FARMA_PUNTOS.F_DNI_USU_LOCAL(?,?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_DNI_USU_LOCAL(?,?,?)", parametros);
        return pRes.trim();
    }

    public static boolean getIndImpLogo() throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("FARMA_PUNTOS.F_VAR_IND_IMPR_LOGO(?,?)"+ parametros);        
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_IND_IMPR_LOGO(?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }    
    
    
    public static List getDatosImprSaldo(String pTarjeta,String pNombreCompleto,double vPuntosAcumulados) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTarjeta);
        parametros.add(pNombreCompleto);
        parametros.add(FarmaUtility.formatNumber(vPuntosAcumulados));
        List pRes = new ArrayList();
        log.info("FARMA_PUNTOS.IMP_SALDO_TARJETA(?,?,?,?,?)"+ parametros);        
        pRes = FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_PUNTOS.IMP_SALDO_TARJETA(?,?,?,?,?)",parametros);
        return pRes;
    }    

    public static List getDatosImprRecuperaPuntos(String pTarjeta,String pNombreCompleto,String pDNI,double vPuntosAcumulados,
                                         boolean vIsOnline,double pPtoSaldoAct,String pNumPedVta
                                         ) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        if(pTarjeta==null)
            log.info("es null la tarjeta");
        else
        parametros.add(pTarjeta);
        if(pNombreCompleto==null)
            log.info("es null la tarjeta");
        else        
        parametros.add(pNombreCompleto);
        if(pDNI==null)
            log.info("es null la tarjeta");
        else        
        parametros.add(pDNI);
        parametros.add(FarmaUtility.formatNumber(vPuntosAcumulados));
        
        if(vIsOnline)
            parametros.add(FarmaConstants.INDICADOR_S);
        else
            parametros.add(FarmaConstants.INDICADOR_N);
        
        
        parametros.add(FarmaUtility.formatNumber(pPtoSaldoAct));
        parametros.add(pNumPedVta.trim());
        
        List pRes = new ArrayList();
        log.info("FARMA_PUNTOS.IMP_RECUPERA_PUNTOS(?,?,?,?,?,?,?,')"+ parametros);        
        pRes = FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_PUNTOS.IMP_RECUPERA_PUNTOS(?,?,?,?,?,?,?,?,?)",parametros);
        return pRes;
    }    
        

    public static String getBuscaValidaPed_Recupera(String pTipoComp, String pMontoNeto,
                                                   String pNumCompPago,String pFecha, String pCodTarjeta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipoComp);
        parametros.add(pMontoNeto);
        parametros.add(pNumCompPago);
        parametros.add(pFecha);
        parametros.add(pCodTarjeta);        
        log.debug("parametros FARMA_PUNTOS.F_GET_PERMITE_RECUPERA(?,?,?,?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_GET_PERMITE_RECUPERA(?,?,?,?,?,?,?)",
                                                           parametros);
    }    

    public static void pRecuperaPuntos(String pTarjeta,String pNumPedVta,boolean pOnline,String idTrx,String nNumAutorizacion) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pTarjeta);
        if(pOnline)
            parametros.add(FarmaConstants.INDICADOR_S);
        else
            parametros.add(FarmaConstants.INDICADOR_N);
        parametros.add(idTrx);
        if(nNumAutorizacion!=null)
        parametros.add(nNumAutorizacion);    
        else
            parametros.add(" ");    
        log.info("FARMA_PUNTOS.P_ASOCIA_TARJ_PED(?,?,?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_PUNTOS.P_ASOCIA_TARJ_PED(?,?,?,?,?,?,?)", parametros,false);
    }   
    
    public static boolean getIndPedidoAsocProdMasUno(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_PED_ASOC_X_MAS_1(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_PED_ASOC_X_MAS_1(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    public static List getPedAsocTarjeta(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.info("FARMA_PUNTOS.F_CUR_PED_ASOC_TARJ_PED(?,?,?,?)"+parametros);
        List pLista = FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_PUNTOS.F_CUR_PED_ASOC_TARJ_PED(?,?,?)", parametros);
        return pLista ;
    }


    public static boolean getIndCobroProcesoOrbis(String pNumPedVta)  throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_PED_PROC_ORBIS(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_PED_PROC_ORBIS(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    public static boolean getIndPedidoRedimio(String pNumPedVta)  throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_PED_REDIMIO(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_PED_REDIMIO(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

   public static boolean getIndPuntosAcum(String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_PED_PUNTOS_ACUM(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_PED_PUNTOS_ACUM(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    public static void pRevierteAcumulaPuntos(String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.info("FARMA_PUNTOS.P_REVERT_ACUM_PUNTOS(?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_PUNTOS.P_REVERT_ACUM_PUNTOS(?,?,?)", parametros,false);        
    }
    
    public static void pGrabaProcesoAnulaOrbis(String pNumPedVtaOriginal,boolean pAnuloOrbis,String vIdTrxAnula,String vNroAutorizaAnula) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVtaOriginal);
        if(pAnuloOrbis)
        parametros.add(FarmaConstants.INDICADOR_S);
        else
        parametros.add(FarmaConstants.INDICADOR_N);
        parametros.add(vIdTrxAnula.trim());
        parametros.add(vNroAutorizaAnula.trim());
        log.info("FARMA_PUNTOS.P_SAVE_TRX_ANULA_ORBIS(?,?,?,?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_PUNTOS.P_SAVE_TRX_ANULA_ORBIS(?,?,?,?,?,?)", parametros,false);        
    }    

    public static ArrayList getListProdPuntosRecupera(String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_CUR_DET_RECUPERA(?,?,?)"+ parametros);
        ArrayList pLista = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista,
                                                          "FARMA_PUNTOS.F_CUR_DET_RECUPERA(?,?,?)",
                                                          parametros);
                
        return pLista;
    }
    
    public static ArrayList getListProdPuntosAnula(String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_CUR_DET_ANULA(?,?,?)"+ parametros);
        ArrayList pLista = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista,
                                                          "FARMA_PUNTOS.F_CUR_DET_ANULA(?,?,?)",
                                                          parametros);
        return pLista;
    }    
    
    public static boolean getIndPedActuaPuntos(String pNumPedVta)  throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_PED_ACTUA_PUNTOS(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_PED_ACTUA_PUNTOS(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }


    public static boolean isPuntosReloadCero(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_IS_PTO_CERO(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_IS_PTO_CERO(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    static boolean isPuntosReloadNulo(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_IS_PTO_NULO(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_IS_PTO_NULO(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    static boolean isPuntosReloadMayorCero(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_IS_PTO_MAYOR_CERO(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_IS_PTO_MAYOR_CERO(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }
    
    static boolean isTieneBonificado(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_IS_BONIFICADO(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_IS_BONIFICADO(?,?,?)", parametros);
        if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    static String getCtdPuntosAcumVta(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("FARMA_PUNTOS.F_VAR_IS_CTD_PTO_PED(?,?)"+ parametros);
        String pRes = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_IS_CTD_PTO_PED(?,?,?)", parametros);
        return pRes.trim();
    }

    public static List getDataTrnxAnula(String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.info("FARMA_PUNTOS.F_VAR_GET_DATA_TRANSAC_ORBIS(?,?,?,?)"+parametros);
        List pLista = FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_PUNTOS.F_VAR_GET_DATA_TRANSAC_ORBIS(?,?,?)", parametros);
        return pLista ;
    }
    
    //DATOS DE PRUEBA
    public static List consultaTarje(String nroTarj)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(nroTarj);
        log.info("FARMA_PUNTOS.CONSULTA_TARJETA(?)"+parametros);
        List pLista = FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_PUNTOS.CONSULTA_TARJETA(?)", parametros);
        return pLista ;
    }
    
    public static boolean registroCliente(String nroTarjeta, String nroDni, String nombre)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(nroTarjeta);
        parametros.add(nroDni);
        parametros.add(nombre);
        log.info("FARMA_PUNTOS.REGISTRO_TARJETA(?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.REGISTRO_TARJETA(?,?,?)", parametros);
        return true;
    }
    
    public static List consultaCliente(String nroTarj)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(nroTarj);
        log.info("FARMA_PUNTOS.CONSULTA_CLIENTE(?)"+parametros);
        List pLista = FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_PUNTOS.CONSULTA_CLIENTE(?)", parametros);
        return pLista ;
    }
    // FIN DE DATOS DE PRUEBA 
    
    /**
     * @author KMONCADA
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @throws Exception
     */
    public static void calculoPuntos(String pCodGrupoCia, String pCodLocal, String pNumPedVta, boolean isRecalculo, boolean isRedime)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        if(isRecalculo)
            parametros.add("S");
        else
            parametros.add("N");
        if(isRedime)
            parametros.add("S");
        else
            parametros.add("N");
        
        log.info("FARMA_PUNTOS.F_I_CALCULA_PUNTOS(?,?,?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_PUNTOS.F_I_CALCULA_PUNTOS(?,?,?,?,?)", parametros, false);
    }
    
    /**
     *
     * @author KMONCADA
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @return
     * @throws Exception
     */
    public static ArrayList obtenerListaProducto(String pCodGrupoCia, String pCodLocal, String pNumPedVta, boolean isAdicionarBonificado)throws Exception{
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        if(isAdicionarBonificado)
            parametros.add("S");
        else
            parametros.add("N");
        log.info("FARMA_PUNTOS.F_CUR_V_LISTA_PRODUCTO(?,?,?,?) "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado, "FARMA_PUNTOS.F_CUR_V_LISTA_PRODUCTO(?,?,?,?)", parametros);
        return resultado;
    }
    
    /**
     *
     * @author KMONCADA
     * @return
     * @throws Exception
     */
    public static String obtenerIndicadorDocBonifica()throws Exception{
        ArrayList parametros = new ArrayList();
        log.info("FARMA_PUNTOS.F_CHAR_IND_DOC_BONIFICA"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_CHAR_IND_DOC_BONIFICA", parametros);
    }
    
    /**
     *
     * @author KMONCADA
     * @return
     * @throws Exception
     */
    public static boolean indicadorMuestrPantallaBonifica()throws Exception{
        ArrayList parametros = new ArrayList();
        log.info("FARMA_PUNTOS.F_CHAR_IND_PANTALLA_BONIFICA"+parametros);
        String indicador = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_CHAR_IND_PANTALLA_BONIFICA", parametros);
        if("S".equalsIgnoreCase(indicador))
            return true;
        else
            return false;
    }
    
    /**
     *
     * @author KMONCADA
     * @param pModel
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param pLstProductoQuote
     * @throws Exception
     */
    public static void verificaBonificados(ArrayList arrayList,  String pCodGrupoCia, String pCodLocal, String pNumPedVta, String pLstProductoQuote)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pLstProductoQuote);
        log.info("FARMA_PUNTOS.F_LST_VALIDA_BONIFICADOS(?,?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(arrayList, "FARMA_PUNTOS.F_LST_VALIDA_BONIFICADOS(?,?,?,?)", parametros);
    }

    
    public static String getTimeOutOrbis()throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("FARMA_PUNTOS.F_VAR_TIME_OUT_ORBIS(?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VAR_TIME_OUT_ORBIS(?,?)", parametros);
    }
    
    public static String getMensajeErrorLealtad(String estadoOperacion)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(ConstantsPuntos.COD_MAESTRO.TIPO_MENSAJE_ERROR_LEALTAD);
        parametros.add(estadoOperacion);
        log.info("FARMA_GRAL.GET_MAESTRO_DETALLE (?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_MAESTRO_DETALLE(?,?)", parametros);
    } 
    
    /**
     *
     * @return
     * @throws Exception
     */
    public static int getTiempoMaxLectora()throws Exception{
        ArrayList parametros = new ArrayList();
        log.info("FARMA_PUNTOS.F_CHAR_TIEMPO_MAX_LECTORA"+parametros);
        return (int)Integer.parseInt(FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_CHAR_TIEMPO_MAX_LECTORA", parametros));
    }
    
    /**
     * Opera la respuesta del quote del programa de puntos, determina productos bonificados y promociones.
     * @author KMONCADA 2015.03.19 
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param pLstProductoQuote
     * @return
     * @throws Exception
     */
    public static String ejecutarQuote(String pCodGrupoCia, String pCodLocal, String pNumPedVta, String pLstProductoQuote)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pLstProductoQuote);
        log.info("FARMA_PUNTOS.F_CHAR_OPERA_QUOTE(?,?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_CHAR_OPERA_QUOTE(?,?,?,?)", parametros);
    }
    
    /**
     * @author KMONCADA
     * @return
     * @throws Exception
     */
    public static String getTextoAhorro() throws Exception{
        ArrayList parametros = new ArrayList();
        log.info("FARMA_PUNTOS.F_TEXTO_AHORRO"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_TEXTO_AHORRO", parametros);
    }
    
    public static void pDescartaAnulaOrbis(String pNumPedVtaOriginal,boolean pDescarta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVtaOriginal);
        if(pDescarta)
        parametros.add(FarmaConstants.INDICADOR_S);
        else
        parametros.add(FarmaConstants.INDICADOR_N);
        log.info("FARMA_PUNTOS.P_DESCARTA_TRX_ANULA_ORBIS(?,?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_PUNTOS.P_DESCARTA_TRX_ANULA_ORBIS(?,?,?,?)", parametros,false);        
    }
    
    /**
     * obtiene la lista de los productos que llevara por promociones del programa de puntos (codigo,cantidad@codigo,cantidad...)
     * @author KMONCADA 2015.03.18
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @return
     * @throws Exception
     */
    public static String getDiferenciasPromociones(String pCodGrupoCia, String pCodLocal, String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        log.info("FARMA_PUNTOS.F_VCHAR_OBTIENE_MODIFICADOS(?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VCHAR_OBTIENE_MODIFICADOS(?,?,?)", parametros);
    }
    
    /**
     * obtiene la cantidad de productos que llevara por promociones del programa de puntos
     * @author KMONCADA 2015.03.18 
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @return
     * @throws Exception
     */
    public static int getCantidadProductoPromociones(String pCodGrupoCia, String pCodLocal, String pNumPedVta)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        log.info("FARMA_PUNTOS.F_INT_CANTIDAD_PROMOCIONES(?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_PUNTOS.F_INT_CANTIDAD_PROMOCIONES(?,?,?)", parametros);
    }
    
    /**
     * Agrega los productos bonificados y por promocion al pedido (valor venta 0) del programa de puntos
     * @author KMONCADA 2015.03.18 
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param pNumPedVtaOrigen para el caso de que copie las promociones de otro pedido, caso contrario enviar el mismo pNumPedVta
     * @return
     * @throws Exception
     */
    public static String agregaPromoBonificados(String pCodGrupoCia, String pCodLocal, String pNumPedVta, String pNumPedVtaOrigen)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pNumPedVtaOrigen);
        log.info("FARMA_PUNTOS.F_CHAR_AGREGA_PROMO_BONIF(?,?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_CHAR_AGREGA_PROMO_BONIF(?,?,?,?)", parametros);
    }
    
    public static String getMensajeSinTarjeta(){
        String mensaje = " ";
        try{
            ArrayList parametros = new ArrayList();
            log.info("FARMA_PUNTOS.F_VARCHAR_MSJ_SIN_TARJETA "+parametros);
            mensaje = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_VARCHAR_MSJ_SIN_TARJETA", parametros);
        }catch(Exception ex){
            log.error(" ", ex);
            
        }
        return mensaje;
    }
    
    public static boolean requerieTarjetaAsociada(){
        boolean isRequiere = true;
        String respuesta = "";
        try{
            ArrayList parametros = new ArrayList();
            log.info("FARMA_PUNTOS.F_IND_REQUIERE_TARJETA "+parametros);
            respuesta = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_PUNTOS.F_IND_REQUIERE_TARJETA", parametros);
        }catch(Exception ex){
            respuesta = ConstantsPuntos.DEFAULT_REQUIERE_TARJETA;
        }
        if(ConstantsPuntos.DEFAULT_REQUIERE_TARJETA.equalsIgnoreCase(respuesta.trim()))
            isRequiere = true;
        else
            isRequiere = false;
        return isRequiere;
    }
}