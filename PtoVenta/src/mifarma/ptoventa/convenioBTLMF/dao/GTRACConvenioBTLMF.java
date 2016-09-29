package mifarma.ptoventa.convenioBTLMF.dao;


import com.mifarma.query.builder.ClienteIntegrador;
import com.mifarma.query.builder.QueryBuilder;
import com.mifarma.query.builder.UpdateBuilder;
import com.mifarma.query.request.QueryParams;
import com.mifarma.query.response.QueryStatus;
import com.mifarma.query.response.UpdateStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.apache.commons.beanutils.BeanMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ERIOS
 * @since 07.10.2015
 */
public class GTRACConvenioBTLMF implements DAORACConvenioBTLMF {
    
    private static final Logger log = LoggerFactory.getLogger(GTRACConvenioBTLMF.class);
    
    private UpdateBuilder updateBuilder = null;
    private ClienteIntegrador clienteIntegrador = null;
    private UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    
    @Override
    public void savePedidoCabRAC(RacVtaPedidoVtaCab racVtaPedidoVtaCab) {
        Map<Object, Object> esCabAsMap = new BeanMap(racVtaPedidoVtaCab);
        
        updateBuilder.addUpdateStatement(new SQLConvenioBTLMFProvider().insertPedidoCabRAC(), esCabAsMap);
        
    }

    @Override
    public void savePedidoDetRAC(List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet) {
        String detalleSql = new SQLConvenioBTLMFProvider().insertPedidoDetRAC();
        for (RacVtaPedidoVtaDet lgtNotaEsDet : lstVtaPedidoVtaDet) {
            Map<Object, Object> esDetasMap = new BeanMap(lgtNotaEsDet);
            updateBuilder.addUpdateStatement(detalleSql, esDetasMap);
        }
    }

    @Override
    public void saveCompPagoRAC(List<RacVtaCompPago> lstVtaCompPago) {
        String detalleSql = new SQLConvenioBTLMFProvider().insertCompPagoRAC();
        for (RacVtaCompPago lgtNotaEsDet : lstVtaCompPago) {
            Map<Object, Object> esDetasMap = new BeanMap(lgtNotaEsDet);
            updateBuilder.addUpdateStatement(detalleSql, esDetasMap);
        }
    }

    @Override
    public void saveFormaPagoPedidoRAC(List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido) {
        String detalleSql = new SQLConvenioBTLMFProvider().insertFormaPagoPedidoRAC();
        for (RacVtaFormaPagoPedido lgtNotaEsDet : lstVtaFormaPagoPedido) {
            Map<Object, Object> esDetasMap = new BeanMap(lgtNotaEsDet);
            updateBuilder.addUpdateStatement(detalleSql, esDetasMap);
        }
    }

    @Override
    public void saveConPedVtaRAC(List<RacConPedVta> lstConPedVta) {
        String detalleSql = new SQLConvenioBTLMFProvider().insertConPedVtaRAC();
        for (RacConPedVta lgtNotaEsDet : lstConPedVta) {
            Map<Object, Object> esDetasMap = new BeanMap(lgtNotaEsDet);
            updateBuilder.addUpdateStatement(detalleSql, esDetasMap);
        }
        
        UpdateStatus status = clienteIntegrador.updateOnline(updateBuilder.getParams());
        status.exceptionOnError();
    }

    @Override
    public void deletePedidoCabRAC(String pNumPedVta) {
        
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta", pNumPedVta);
        
        updateBuilder.addUpdateStatement(new SQLConvenioBTLMFProvider().deletePedidoDatosConvRAC(), mapParametros);        
        updateBuilder.addUpdateStatement(new SQLConvenioBTLMFProvider().deletePedidoFormaPagoRAC(), mapParametros);
        updateBuilder.addUpdateStatement(new SQLConvenioBTLMFProvider().deletePedidoCompPagoRAC(), mapParametros);
        updateBuilder.addUpdateStatement(new SQLConvenioBTLMFProvider().deletePedidoDetRAC(), mapParametros);
        updateBuilder.addUpdateStatement(new SQLConvenioBTLMFProvider().deletePedidoCabRAC(), mapParametros);
        
    }

    @Override
    public String cobrarPedidoRAC(String pCodLocal, String pCodGrupoCia, String pNumPedVta, String indNotaCredito) throws Exception{
        String respuesta;
        
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
            .qrySpVarchar("{ ? = call BTLPROD.PKG_DOCUMENTO_MF.SP_GRABA_DOCUMENTOS(?,?,?,?,?,?)}", pCodGrupoCia, pCodLocal, pNumPedVta, 
                          indNotaCredito, "S", "S")
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> result = status.getResult(); 
        Map<String, Object> first = result.get(0);

        respuesta = String.valueOf(first.get("1"));
        
        return respuesta;
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }

    @Override
    public void openConnection() {
        updateBuilder = new UpdateBuilder()
                                  .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
                                  .requestId("convenio-merge-rac-ped");
        
        clienteIntegrador = new ClienteIntegrador(VariablesPtoVenta.conexionGTX.getIPBD()+":"+VariablesPtoVenta.conexionGTX.getPORT());
        clienteIntegrador.bypassGateway(); 
    }

    @Override
    public ArrayList<ArrayList<String>> listaBenefRemoto() throws Exception{
        
        ArrayList<ArrayList<String>> tmpArray;
        List<BeanResultado> tmpLista = null;
        
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
            .qrySpCursor("{ ? = call PTOVENTA.PTOVENTA_MATRIZ_CONV_BTLMF.BTLMF_F_CUR_LISTA_BENIFICIARIO(?,?,?,?,?)}", FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, 
                         FarmaVariables.vNuSecUsu, VariablesConvenioBTLMF.vCodConvenio, VariablesConvenioBTLMF.vDescDiagnostico)
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> resultList = status.getResult();

        tmpLista = utilityPtoVenta.retornaBean(resultList);
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        
        return tmpArray;
    }
    
    @Override
    public ArrayList<ArrayList<String>> obtieneBenefRemoto(String pCodCliConv) throws Exception{
        ArrayList<ArrayList<String>> tmpArray;
        List<BeanResultado> tmpLista = null;
        
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
            .qrySpCursor("{ ? = call PTOVENTA.PTOVENTA_MATRIZ_CONV_BTLMF.BTLMF_F_CUR_OBT_BENIFICIARIO(?,?,?,?,?)}", FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, 
                         FarmaVariables.vNuSecUsu, VariablesConvenioBTLMF.vCodConvenio, pCodCliConv)
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> resultList = status.getResult();

        tmpLista = utilityPtoVenta.retornaBean(resultList);
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        
        return tmpArray;
    }
    
    @Override
    public double obtieneComsumoBenif() throws Exception{
        
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
            .qrySpVarchar("{ ? = call btlprod.pkg_beneficiario.FN_RET_CONSUMO_BENEFICIARIO(?,?,?,?)}", VariablesConvenioBTLMF.vCodConvenio, VariablesConvenioBTLMF.vCodCliente,
                          "10", "005")
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> result = status.getResult(); 
        Map<String, Object> first = result.get(0);

        String respuesta = String.valueOf(first.get("1"));
        double dblConsumo = FarmaUtility.getDecimalNumber(respuesta);
                          
        return dblConsumo;
    }

    @Override
    public String anularPedidoRac(String pIndNotaCred) {
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
            .qrySpVarchar("{ ? = call BTLPROD.PKG_DOCUMENTO_MF.SP_ANULA_DOCUMENTO(?,?,?,?)}", VariablesConvenioBTLMF.vTipoCompPago, VariablesConvenioBTLMF.vNumCompPago,
                          FarmaVariables.vCodLocal, pIndNotaCred)
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> result = status.getResult(); 
        Map<String, Object> first = result.get(0);

        String respuesta = String.valueOf(first.get("1"));
                          
        return respuesta;
    }

    @Override
    public int isExisteComprobanteEnRAC(String pCodGrupoCia, String pCodLocal, String pTipoComprobante,
                                        String pNumComprobante, String pIndComprobanteElectronico) {
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
            .qrySpVarchar("{ ? = call BTLPROD.PKG_DOCUMENTO_MF.FN_EXISTE_DOCUMENTO(?,?,?,?,?)}", pCodGrupoCia, pCodLocal,
                          pTipoComprobante, pNumComprobante, pIndComprobanteElectronico)
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> result = status.getResult(); 
        Map<String, Object> first = result.get(0);

        String respuesta = String.valueOf(first.get("1"));
        int resultado = Integer.parseInt(respuesta.trim());
        return resultado;
    }

    @Override
    public String validacionPedidoRAC(String pCodLocal, String pNumPedVta, String pCodConvenio, String pCodCliente,
                                      String pTipoDoc, double pMonto, String pVtaFin) throws Exception{
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "CONVENIO")
            .qrySpVarchar("{ ? = call BTLPROD.PKG_DOCUMENTO_MF.VALIDAR_COBRO_PEDIDO(?,?,?,?,?,?,?)}", pTipoDoc, pCodLocal, pCodCliente,
                          pCodConvenio, pVtaFin, pMonto, pNumPedVta)
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> result = status.getResult(); 
        Map<String, Object> first = result.get(0);

        String respuesta = String.valueOf(first.get("1"));
        
        return respuesta;
    }
}
