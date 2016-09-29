package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

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
    private MapperRACConvenioBTLMF mapper = null;
    private UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

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
        mapper = sqlSession.getMapper(MapperRACConvenioBTLMF.class);
    }

    @Override
    public void savePedidoCabRAC(RacVtaPedidoVtaCab racVtaPedidoVtaCab) throws Exception {
        mapper.savePedidoCabRAC(racVtaPedidoVtaCab);
    }

    @Override
    public void savePedidoDetRAC(List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet) throws Exception {
        for (RacVtaPedidoVtaDet racVtaPedidoVtaDet : lstVtaPedidoVtaDet) {
            mapper.savePedidoDetRAC(racVtaPedidoVtaDet);
        }
    }

    @Override
    public void saveCompPagoRAC(List<RacVtaCompPago> lstVtaCompPago) throws Exception {
        for (RacVtaCompPago racVtaCompPago : lstVtaCompPago) {
            mapper.saveCompPagoRAC(racVtaCompPago);
        }
    }

    @Override
    public void saveFormaPagoPedidoRAC(List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido) throws Exception {
        for (RacVtaFormaPagoPedido racVtaFormaPagoPedido : lstVtaFormaPagoPedido) {
            mapper.saveFormaPagoPedidoRAC(racVtaFormaPagoPedido);
        }
    }

    @Override
    public void saveConPedVtaRAC(List<RacConPedVta> lstConPedVta) throws Exception {
        for (RacConPedVta racConPedVta : lstConPedVta) {
            mapper.saveConPedVtaRAC(racConPedVta);
        }
    }

    public void deletePedidoCabRAC(String pNumPedVta) throws Exception {
        
        String indConexionRac;
        try {
            indConexionRac = ((new FacadeRecaudacion()).validarConexionRAC()) ? "S" : "N";
        } catch (Exception e) {
            indConexionRac = "N";
        }
        if(indConexionRac.equals("N")){
            throw new Exception("No hay conexión con RAC.");
        }
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedVta", pNumPedVta);

        mapper.deleteDatosConvPedVtaRAC(mapParametros);
        mapper.deleteFormaPagoPedidoRAC(mapParametros);
        mapper.deleteCompPagoRAC(mapParametros);
        mapper.deletePedidoDetRAC(mapParametros);
        mapper.deletePedidoCabRAC(mapParametros);
    }

    public String cobrarPedidoRAC(String pCodLocal, String pCodGrupoCia, String pNumPedVta,
                                  String indNotaCredito) throws Exception {
        String resultado = "N";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("pCodGrupo_Cia_in", pCodGrupoCia);
        mapParametros.put("pCod_local_in", pCodLocal);
        mapParametros.put("pNum_Ped_Vta_in", pNumPedVta);
        mapParametros.put("pIndNotaCred", indNotaCredito);
        mapParametros.put("pIndGrabaPed", "S");
        mapParametros.put("pIndCreditoSeparado", "S");
        log.info("CobraPedido RAC " + mapParametros);
        mapper.cobraPedidoRAC(mapParametros);
        resultado = (String)mapParametros.get("respuesta");
        return resultado;
    }
    
    public ArrayList<ArrayList<String>> listaBenefRemoto() throws Exception{
        List<BeanResultado> tmpLista = null;
        ArrayList<ArrayList<String>> tmpArray;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("CCODGRUPOCIA_IN", FarmaVariables.vCodGrupoCia);
        mapParametros.put("CCODLOCAL_IN", FarmaVariables.vCodLocal);
        mapParametros.put("CSECUSULOCAL_IN", FarmaVariables.vNuSecUsu);
        mapParametros.put("VCODCONVENIO_IN", VariablesConvenioBTLMF.vCodConvenio);
        mapParametros.put("VBENIFICIARIO_IN", VariablesConvenioBTLMF.vDescDiagnostico);

        mapper.listaBenefRemoto(mapParametros);
        tmpLista = (List<BeanResultado>)mapParametros.get("listado");
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        
        return tmpArray;
    }
    
    /**
     * Se obtiene beneficiario remoto
     * @author ERIOS
     * @since 2.4.8
     * @param pCodCliConv
     * @return
     */
    public ArrayList<ArrayList<String>> obtieneBenefRemoto(String pCodCliConv) throws Exception{
        List<BeanResultado> tmpLista = null;
        ArrayList<ArrayList<String>> tmpArray = new ArrayList<>();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("CCODGRUPOCIA_IN", FarmaVariables.vCodGrupoCia);
        mapParametros.put("CCODLOCAL_IN", FarmaVariables.vCodLocal);
        mapParametros.put("CSECUSULOCAL_IN", FarmaVariables.vNuSecUsu);
        mapParametros.put("VCODCONVENIO_IN", VariablesConvenioBTLMF.vCodConvenio);
        mapParametros.put("VCODBENIF_IN", pCodCliConv);

        mapper.obtieneBenefRemoto(mapParametros);
        tmpLista = (List<BeanResultado>)mapParametros.get("listado");
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        
        return tmpArray;
    }

    @Override
    public double obtieneComsumoBenif() {
        return 0.0;
    }

    @Override
    public String anularPedidoRac(String pIndNotaCred) {
        return null;
    }

    @Override
    public int isExisteComprobanteEnRAC(String pCodGrupoCia, String pCodLocal, String pTipoComprobante,
                                        String pNumComprobante, String pIndComprobanteElectronico) {
        return 0;
    }

    @Override
    public String validacionPedidoRAC(String pCodLocal, String pNumPedVta, String pCodConvenio, String pCodCliente,
                                      String pTipoDoc, double pMonto, String pVtaFin) {
        return null;
    }
}
