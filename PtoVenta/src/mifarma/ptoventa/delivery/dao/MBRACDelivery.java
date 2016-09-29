package mifarma.ptoventa.delivery.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mifarma.ptoventa.reference.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;


public class MBRACDelivery implements DAORACDelivery {

    private SqlSession sqlSession = null;
    private MapperRACDelivery mapper = null;
    
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
        mapper = sqlSession.getMapper(MapperRACDelivery.class);
    }
    
    public void actualizaProformaRAC(String pCodCia, String pCodLocal, String pNumProforma, String pCodLocalSap,
                                     String pNumComprobantes, String fechaEnvio) throws Exception {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("a_cod_cia", pCodCia);
        mapParametros.put("a_cod_local", pCodLocal);
        mapParametros.put("a_num_proforma", pNumProforma);
        mapParametros.put("a_cod_local_sap", pCodLocalSap);
        mapParametros.put("a_Comprobantes", pNumComprobantes);
        mapParametros.put("a_ACT_ENVIO_PROF", fechaEnvio);

        mapper.actualizaProforma(mapParametros);
    }

    @Override
    public ArrayList<ArrayList<String>> cargaListaStockLocalesPreferidos(String pCodprod) {
        return null;
    }

    @Override
    public String obtieneIndicadorStock(String pCodProd) {
        return null;
    }

    @Override
    public String obtieneStockLocal(String pCodProd, String pCodLocalDestino) {
        return null;
    }
}
