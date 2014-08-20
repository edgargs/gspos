package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.recaudacion.dao.MapperRecaudacion;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBConvenioBTLMF implements DAOConvenioBTLMF{
    
    private static final Logger log = LoggerFactory.getLogger(MBConvenioBTLMF.class);
    
    private SqlSession sqlSession = null;
    UtilityPtoVenta utilityPtoVenta =new UtilityPtoVenta();
    private MapperConvenioBTLMF mapperTrsscSix = null;
    private MapperConvenioBTLMF mapper = null;

    public ArrayList<ArrayList<String>> listaBenefRemoto(){        
        List<BeanResultado> tmpLista = null;
        ArrayList<ArrayList<String>> tmpArray = new ArrayList<>();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("CCODGRUPOCIA_IN",FarmaVariables.vCodGrupoCia);
        mapParametros.put("CCODLOCAL_IN",FarmaVariables.vCodLocal);
        mapParametros.put("CSECUSULOCAL_IN",FarmaVariables.vNuSecUsu);
        mapParametros.put("VCODCONVENIO_IN",VariablesConvenioBTLMF.vCodConvenio);
        mapParametros.put("VBENIFICIARIO_IN",VariablesConvenioBTLMF.vDescDiagnostico);
                
        try{   
            sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperConvenioBTLMF.class);
            mapperTrsscSix.listaBenefRemoto(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        }catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }finally{   
            sqlSession.close();
        }
        return tmpArray;
    }
    
    public RacVtaPedidoVtaCab getPedidoCabLocal(String pNumPedVta, String pIndicadorNC) throws Exception{
        RacVtaPedidoVtaCab racVtaPedidoVtaCab;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta",pNumPedVta);
        //pIndicadorNC: La tabla es la misma
        racVtaPedidoVtaCab = mapper.getPedidoCabLocal(mapParametros);
        
        return racVtaPedidoVtaCab;
    }

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
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperConvenioBTLMF.class);
    }

    @Override
    public List<RacVtaPedidoVtaDet> getPedidoDetLocal(String pNumPedVta, String pIndicadorNC) throws Exception{
        List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta",pNumPedVta);
        if(pIndicadorNC.equals(FarmaConstants.INDICADOR_N)){
            lstVtaPedidoVtaDet = mapper.getPedidoDetLocalTemp(mapParametros);
        }else{
            lstVtaPedidoVtaDet = mapper.getPedidoDetLocal(mapParametros);
        }
        return lstVtaPedidoVtaDet;
    }
    
    @Override
    public List<RacVtaCompPago> getCompPagoLocal(String pNumPedVta, String pIndicadorNC) throws Exception{
        List<RacVtaCompPago> lstVtaCompPago;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta",pNumPedVta);
        if(pIndicadorNC.equals(FarmaConstants.INDICADOR_N)){
            lstVtaCompPago = mapper.getCompPagoLocalTemp(mapParametros);
        }else{
            lstVtaCompPago = mapper.getCompPagoLocal(mapParametros);
        }
        return lstVtaCompPago;
    }
    
    @Override
    public List<RacVtaFormaPagoPedido> getFormaPagoPedidoLocal(String pNumPedVta, String pIndicadorNC) throws Exception{
        List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta",pNumPedVta);
        if(pIndicadorNC.equals(FarmaConstants.INDICADOR_N)){
            lstVtaFormaPagoPedido = mapper.getFormaPagoPedidoLocalTemp(mapParametros);
        }else{
            lstVtaFormaPagoPedido = mapper.getFormaPagoPedidoLocal(mapParametros);
        }
        return lstVtaFormaPagoPedido;
    }

    @Override
    public List<RacConPedVta> getConPedVtaLocal(String pNumPedVta, String pIndicadorNC) throws Exception{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta",pNumPedVta);
        //pIndicadorNC: La tabla es la misma
        return mapper.getConPedVtaLocal(mapParametros);
    }
}
