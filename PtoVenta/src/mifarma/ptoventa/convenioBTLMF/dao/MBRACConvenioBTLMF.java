package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.reference.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Persistencia al RAC
 * @author ERIOS
 * @since 2.4.4
 */
public class MBRACConvenioBTLMF implements DAORACConvenioBTLMF {
    
    private static final Logger log = LoggerFactory.getLogger(MBRACConvenioBTLMF.class);

    private SqlSession sqlSession = null;
    private MapperConvenioBTLMF mapper = null;
    
    @Override
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    @Override
    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperConvenioBTLMF.class);
    }

    @Override
    public void savePedidoCabRAC(RacVtaPedidoVtaCab racVtaPedidoVtaCab) throws Exception{
        mapper.savePedidoCabRAC(racVtaPedidoVtaCab);
    }

    @Override
    public void savePedidoDetRAC(List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet) throws Exception{
        for(RacVtaPedidoVtaDet racVtaPedidoVtaDet:lstVtaPedidoVtaDet){
            mapper.savePedidoDetRAC(racVtaPedidoVtaDet);
        }
    }

    @Override
    public void saveCompPagoRAC(List<RacVtaCompPago> lstVtaCompPago) throws Exception{
        for(RacVtaCompPago racVtaCompPago:lstVtaCompPago){
            mapper.saveCompPagoRAC(racVtaCompPago);
        }
    }

    @Override
    public void saveFormaPagoPedidoRAC(List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido) throws Exception{
        for(RacVtaFormaPagoPedido racVtaFormaPagoPedido:lstVtaFormaPagoPedido){
            mapper.saveFormaPagoPedidoRAC(racVtaFormaPagoPedido);
        }
    }

    @Override
    public void saveConPedVtaRAC(List<RacConPedVta> lstConPedVta) throws Exception{
        for(RacConPedVta racConPedVta:lstConPedVta){
            mapper.saveConPedVtaRAC(racConPedVta);
        }
    }
    
    public void deletePedidoCabRAC(String pNumPedVta) throws Exception{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta",pNumPedVta);
        
        mapper.deleteDatosConvPedVtaRAC(mapParametros);
        mapper.deleteFormaPagoPedidoRAC(mapParametros);
        mapper.deleteCompPagoRAC(mapParametros);
        mapper.deletePedidoDetRAC(mapParametros);
        mapper.deletePedidoCabRAC(mapParametros);
    }
    
    public String cobrarPedidoRAC(String pCodLocal, String pCodGrupoCia, String pNumPedVta, String indNotaCredito)throws Exception{        
        String resultado = "N";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("pCodGrupo_Cia_in",pCodGrupoCia);
        mapParametros.put("pCod_local_in",pCodLocal);
        mapParametros.put("pNum_Ped_Vta_in",pNumPedVta);
        mapParametros.put("pIndNotaCred",indNotaCredito);
        mapParametros.put("pIndGrabaPed","S");
        mapParametros.put("pIndCreditoSeparado","S");
        log.info("CobraPedido RAC "+mapParametros);
        mapper.cobraPedidoRAC(mapParametros);
        resultado = (String)mapParametros.get("respuesta");
        return resultado;
    }
    
    public void actualizaProformaRAC(String pCodCia, String pCodLocal, String pNumProforma, String pCodLocalSap, String pNumComprobantes, String fechaEnvio) throws Exception{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("a_cod_cia",pCodCia);
        mapParametros.put("a_cod_local",pCodLocal);
        mapParametros.put("a_num_proforma",pNumProforma);
        mapParametros.put("a_cod_local_sap",pCodLocalSap);
        mapParametros.put("a_Comprobantes",pNumComprobantes);
        mapParametros.put("a_ACT_ENVIO_PROF",fechaEnvio);
        
        mapper.actualizaProforma(mapParametros);
    }    
}
