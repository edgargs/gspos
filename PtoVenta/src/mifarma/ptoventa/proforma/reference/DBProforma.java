package mifarma.ptoventa.proforma.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBProforma {
    
    private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(DBProforma.class);
        
    /**
     * Se obtiene la caja de quien cobro la proforma.
     * @author ERIOS
     * @since 12.01.2016
     * @return
     */
    public static String obtieneSecCajeroProforma(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PROFORMA.F_GET_SECUENCIA_CAJERO(?,?,?)", parametros);        
    }
    
    public static void obtieneInfoCobrarPedido(ArrayList pArrayList, String pNumPedDiario, String pFecPedVta) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedDiario);
        parametros.add(pFecPedVta);
        // KMONCADA 01.09.2014 MODIFICACION PARA MOSTRAR LOS DOC A IMPRIMIR EN CASO DE CONVENIOS
        parametros.add(VariablesConvenioBTLMF.vValorSelCopago);
        log.debug("invoca PTOVENTA_PROFORMA.F_OBTIENE_INFO_PEDIDO(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA.PTOVENTA_PROFORMA.F_OBTIENE_INFO_PEDIDO(?,?,?,?,?)",
                                                          parametros);
    }
    
    public static void grabaFormaPagoPedido(String pCodFormaPago, String pImPago, String pTipMoneda,
                                            String pTipoCambio, String pVuelto, String pImTotalPago, String pNumTarj,
                                            String pFecVencTarj, String pNomCliTarj, String pCantCupon,
                                            String pDNITarj, String pCodAutori, String pCodLote,
                                            String pNumPedVtaNCR) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodFormaPago);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImPago))); //
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pTipoCambio)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pVuelto)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImTotalPago))); //se pagacon esta cantdad
        parametros.add(pNumTarj);
        parametros.add(""); //pFecVencTarj
        parametros.add(pNomCliTarj);
        parametros.add(new Integer(pCantCupon));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pDNITarj);
        parametros.add(pCodAutori);
        parametros.add(pCodLote);
        parametros.add(pNumPedVtaNCR);
        log.debug("PTOVENTA_PROFORMA.P_CAJ_GRAB_FORMA_PAGO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PROFORMA.P_CAJ_GRAB_FORMA_PAGO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }
    
    public static String verificaPagoUsoCampana(String pNumPed) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        log.debug("PTOVENTA_PROFORMA.F_FID_VALIDA_COBRO_PEDIDO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PROFORMA.F_FID_VALIDA_COBRO_PEDIDO(?,?,?)", parametros);
    }
    
    public static String verificaPedidoFormasPago(String pNumPed) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        log.debug("PTOVENTA_PROFORMA.F_CAJ_VERIFICA_PED_FOR_PAG(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PROFORMA.F_CAJ_VERIFICA_PED_FOR_PAG(?,?,?)",
                                                           parametros);
    }
    
    public static void actualizarEstadoProforma(String nroPedido, String estadoNuevoProforma)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroPedido);
        parametros.add(estadoNuevoProforma);
        parametros.add(VariablesCaja.vSecMovCaja);
        log.debug("PTOVENTA_PROFORMA.F_ACTUALIZAR_ESTADO_PEDIDO(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PROFORMA.F_ACTUALIZAR_ESTADO_PEDIDO(?,?,?,?,?)",parametros);
    }
    
    /**
     * @author KMONCADA
     * @since 08.06.2016
     * @param numeroProforma
     * @throws Exception
     */
    public static void asignarLoteProductoProforma(String numeroProforma)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numeroProforma);
        log.debug("PTOVENTA_PROFORMA.P_ASIGNAR_LOTE_PROD_PROFORMA(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PROFORMA.P_ASIGNAR_LOTE_PROD_PROFORMA(?,?,?)",parametros, false);
    }
    
    public static List obtenerCantidadPisoAvisar(String pNroProforma) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroProforma);
        log.info("PTOVENTA_PROFORMA.F_OBTENER_CANT_PISOS_AVISAR(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_PROFORMA.F_OBTENER_CANT_PISOS_AVISAR(?,?,?)", parametros);

    }
    
    public static List obtenerConstanciaPagoProforma(String pCodGrupoCia, String pCodLocal, String pNroProforma) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNroProforma);
        log.info("PTOVENTA_PROFORMA.F_GET_IMPR_CONSTANCIA_PAGO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_PROFORMA.F_GET_IMPR_CONSTANCIA_PAGO(?,?,?)", parametros);

    }
    
    public static void reservarStockTemporal(String nroProforma)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroProforma);
        parametros.add(FarmaVariables.vIdUsu);
        log.info("PTOVENTA_PROFORMA.P_MUEVE_STOCK_TEMPORAL(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PROFORMA.P_MUEVE_STOCK_TEMPORAL(?,?,?,?)", parametros, false);
    }
    
    public static List getDatosValidaRac(String nroProforma)throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroProforma);
        log.info("PTOVENTA_PROFORMA.F_DATOS_VALIDAR_VTA_EMPRESA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_PROFORMA.F_DATOS_VALIDAR_VTA_EMPRESA(?,?,?)", parametros);
    }
    
    public static void anularProforma(String nroProforma)throws Exception{
        
    }
    
    public static List obtenerConstanciaProformaGenerada(String pCodGrupoCia, String pCodLocal, String pNroProforma) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNroProforma);
        log.info("PTOVENTA_PROFORMA.F_IMPR_VOUCHER_PROFORMA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_PROFORMA.F_IMPR_VOUCHER_PROFORMA(?,?,?)", parametros);

    }
}
