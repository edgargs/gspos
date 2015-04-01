package mifarma.factelec.daemon.dao;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import mifarma.factelec.daemon.bean.MonVtaCompPagoE;
import mifarma.factelec.daemon.util.BeanConexion;
import mifarma.factelec.daemon.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBPtoVenta implements DAOPtoVenta {
    
    private static final Logger log = LoggerFactory.getLogger(MBPtoVenta.class);
    
    private SqlSession sqlSession = null;
    private MapperDaemon mapper = null;
    private BeanConexion conexion;

    public MBPtoVenta() {
        super();
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
        sqlSession = MyBatisUtil.getSqlPtoVentaSessionFactory(conexion).openSession();
        mapper = sqlSession.getMapper(MapperDaemon.class);
    }

    @Override
    public void setConexion(BeanConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void saveMonVtaCompPagoE(MonVtaCompPagoE pDocumento) throws Exception{
        Map<String,Object>  mapParametros = new HashMap<String,Object>(); 
        
        mapParametros.put("cCodGrupoCia", pDocumento.getCod_grupo_cia());
        mapParametros.put("cCodCia", pDocumento.getCod_cia());
        mapParametros.put("cCodLocal", pDocumento.getCod_local());
        mapParametros.put("cNumPedVta", pDocumento.getNum_ped_vta());
        mapParametros.put("cSecCompPago", pDocumento.getSec_comp_pago());
        
        //mapper.borraDocumentoE(mapParametros);
        //mapper.insertaDocumentoE(pDocumento);
        mapper.mergeDocumentoE(pDocumento);
    }
}
