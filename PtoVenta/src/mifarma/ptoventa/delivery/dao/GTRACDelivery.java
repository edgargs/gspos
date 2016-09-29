package mifarma.ptoventa.delivery.dao;


import com.mifarma.query.builder.ClienteIntegrador;
import com.mifarma.query.builder.QueryBuilder;
import com.mifarma.query.builder.UpdateBuilder;
import com.mifarma.query.request.QueryParams;
import com.mifarma.query.response.QueryResultRow;
import com.mifarma.query.response.QueryStatus;
import com.mifarma.query.response.UpdateStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GTRACDelivery implements DAORACDelivery {

    private static final Logger log = LoggerFactory.getLogger(GTRACDelivery.class);
    
    private UpdateBuilder updateBuilder = null;
    private ClienteIntegrador clienteIntegrador = null;
    private UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    
    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }

    @Override
    public void openConnection() {
        updateBuilder = new UpdateBuilder()
                                  .codLocalOrgDst(FarmaVariables.vCodLocal, "DLV")
                                  .requestId("delivery-act-estado-proforma");
        
        clienteIntegrador = new ClienteIntegrador(VariablesPtoVenta.conexionGTX.getIPBD()+":"+VariablesPtoVenta.conexionGTX.getPORT());
        clienteIntegrador.bypassGateway(); 
    }
    
    @Override
    public void actualizaProformaRAC(String pCodCia, String pCodLocal, String pNumProforma, String pCodLocalSap,
                                     String pNumComprobantes, String fechaEnvio) {
        //AW 15.09.2015 Start Actualiza with retry                        
        String consulta = "call DELIVERY.PKG_DAEMON_UPD_PROF_FV.P_ACT_ENVIO_PROF(:a_cod_cia, :a_cod_local, :a_num_proforma, :a_cod_local_sap, :a_Comprobantes, :a_ACT_ENVIO_PROF)"; 

        Map<String, Object> paramsMap = new HashMap<String, Object>();  
        paramsMap.put("a_cod_cia", pCodCia);
        paramsMap.put("a_cod_local", pCodLocal);
        paramsMap.put("a_num_proforma", pNumProforma);
        paramsMap.put("a_cod_local_sap", pCodLocalSap);
        paramsMap.put("a_Comprobantes", pNumComprobantes);
        paramsMap.put("a_ACT_ENVIO_PROF", fechaEnvio);

        updateBuilder.addUpdateStatement(consulta, paramsMap ,"delivery-act-estado-proforma");  

        UpdateStatus status = clienteIntegrador.updateRetry(updateBuilder.getParams());  
        status.exceptionOnError();                                 
        //AW 15.08.2015 End Actualiza with retry
    }
    
    @Override
    public ArrayList<ArrayList<String>> cargaListaStockLocalesPreferidos(String pCodprod) throws Exception{
        
        ArrayList<ArrayList<String>> tmpArray;
        List<BeanResultado> tmpLista = null;
        
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "DLV")
            .qrySpCursor("{ ? = call DELIVERY.PTOVENTA_INV.INV_STOCK_LOCALES_PREFERIDOS(?,?,?,?)}", FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, 
                         pCodprod, "S")
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> resultList = status.getResult();

        tmpLista = utilityPtoVenta.retornaBean(resultList);
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        
        return tmpArray;
    }
    
    @Override
    public String obtieneIndicadorStock(String pCodProd) throws Exception{
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, "DLV")
            .qrySpVarchar("{ ? = call DELIVERY.PTOVENTA_REP.INV_OBTIENE_IND_STOCK(?,?)}", FarmaVariables.vCodGrupoCia,
                          pCodProd)
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> result = status.getResult(); 
        Map<String, Object> first = result.get(0);

        String respuesta = String.valueOf(first.get("1"));
                          
        return respuesta;
    }

    @Override
    public String obtieneStockLocal(String pCodProd, String pCodLocalDestino) {
        String strConsulta = "SELECT to_char(stk_fisico) || 'Ã' || NVL(unid_vta, ' ') prod_local" + 
        "                       FROM   ptoventa.lgt_prod_local"+
        "                       WHERE  cod_grupo_cia = '"+FarmaVariables.vCodGrupoCia+"' " + 
        "                       AND    cod_local = '"+pCodLocalDestino+"'" + 
        "                       AND    cod_prod  = '"+pCodProd+"'";
        QueryParams params = new QueryBuilder()
            .codLocalOrgDst(FarmaVariables.vCodLocal, pCodLocalDestino)
            .qryText(strConsulta)            
            .getParams();
        
        QueryStatus status = clienteIntegrador.queryGeneric(params);
        status.exceptionOnError();
        
        List<Map<String, Object>> result = status.getResult(); 
        
        List<QueryResultRow> rows = QueryResultRow.rows(result);
        QueryResultRow row = rows.get(0);
        
        String respuesta = row.getString("prod_local");
        
        return respuesta;
    }
}
