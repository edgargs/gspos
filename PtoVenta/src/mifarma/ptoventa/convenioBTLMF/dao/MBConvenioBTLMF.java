package mifarma.ptoventa.convenioBTLMF.dao;

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
import mifarma.ptoventa.reference.BeanImpresion;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBConvenioBTLMF implements DAOConvenioBTLMF {

    private static final Logger log = LoggerFactory.getLogger(MBConvenioBTLMF.class);

    private SqlSession sqlSession = null;
    private UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    private MapperConvenioBTLMF mapperTrsscSix = null;
    private MapperConvenioBTLMF mapper = null;

    public RacVtaPedidoVtaCab getPedidoCabLocal(String pNumPedVta, String pIndicadorNC) throws Exception {
        RacVtaPedidoVtaCab racVtaPedidoVtaCab;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta", pNumPedVta);
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
    public List<RacVtaPedidoVtaDet> getPedidoDetLocal(String pNumPedVta, String pIndicadorNC) throws Exception {
        List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta", pNumPedVta);
        if (pIndicadorNC.equals(FarmaConstants.INDICADOR_N)) {
            lstVtaPedidoVtaDet = mapper.getPedidoDetLocalTemp(mapParametros);
        } else {
            lstVtaPedidoVtaDet = mapper.getPedidoDetLocal(mapParametros);
        }
        return lstVtaPedidoVtaDet;
    }

    @Override
    public List<RacVtaCompPago> getCompPagoLocal(String pNumPedVta, String pIndicadorNC) throws Exception {
        List<RacVtaCompPago> lstVtaCompPago;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta", pNumPedVta);
        if (pIndicadorNC.equals(FarmaConstants.INDICADOR_N)) {
            lstVtaCompPago = mapper.getCompPagoLocalTemp(mapParametros);
        } else {
            lstVtaCompPago = mapper.getCompPagoLocal(mapParametros);
        }
        return lstVtaCompPago;
    }

    @Override
    public List<RacVtaFormaPagoPedido> getFormaPagoPedidoLocal(String pNumPedVta,
                                                               String pIndicadorNC) throws Exception {
        List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta", pNumPedVta);
        if (pIndicadorNC.equals(FarmaConstants.INDICADOR_N)) {
            lstVtaFormaPagoPedido = mapper.getFormaPagoPedidoLocalTemp(mapParametros);
        } else {
            lstVtaFormaPagoPedido = mapper.getFormaPagoPedidoLocal(mapParametros);
        }
        return lstVtaFormaPagoPedido;
    }

    @Override
    public List<RacConPedVta> getConPedVtaLocal(String pNumPedVta, String pIndicadorNC) throws Exception {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta", pNumPedVta);
        //pIndicadorNC: La tabla es la misma
        return mapper.getConPedVtaLocal(mapParametros);
    }


    public String actualizaFechaProcesoRac(String pCodGrupoCia, String pCodLocal, String pNumPedVta) throws Exception {
        String resultado = "N";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("CodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNroPedido", pNumPedVta);
        mapper.actualizaPedidoLocal(mapParametros);
        resultado = "S";
        return resultado;
    }
    
    /**
     * Imprime voucher copia guia
     * @author ERIOS
     * @since 21.07.2015
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param pSecCompPago
     * @return
     * @throws Exception
     */
    @Override
    public List<BeanImpresion> getVoucherCopiaGuia(String pCodGrupoCia, String pCodCia, String pCodLocal, String pNumPedVta, String pSecCompPago) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", pCodCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNroPedido_in", pNumPedVta);
        mapParametros.put("cIndCopiaGuia_in", "S");
        mapParametros.put("cSecCompPago_in", pSecCompPago);        
        mapper.getVoucherCopiaGuia(mapParametros);
        List<BeanImpresion> lstRetorno = (List<BeanImpresion>)mapParametros.get("listado");
        if(lstRetorno.size()>0){
            return lstRetorno;
        }else{
            return null;
        }
    }
    
    @Override
    public List<BeanImpresion> getVoucher(String pCodGrupoCia, String pCodCia, String pCodLocal, String pNumPedVta) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", pCodCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNroPedido_in", pNumPedVta);
        mapper.getVoucher(mapParametros);
        List<BeanImpresion> lstRetorno = (List<BeanImpresion>)mapParametros.get("listado");
        if(lstRetorno.size()>0){
            return lstRetorno;
        }else{
            return null;
        }
    }
}
