package mifarma.factelec.daemon.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.factelec.daemon.bean.BeanLocal;
import mifarma.factelec.daemon.bean.MonVtaCompPagoE;
import mifarma.factelec.daemon.util.BeanConexion;
import mifarma.factelec.daemon.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBDaemon implements DAODaemon{
    
    private static final Logger log = LoggerFactory.getLogger(MBDaemon.class);
    
    private SqlSession sqlSession = null;
    private MapperDaemon mapper = null;
    private SqlSessionFactory factory = null; 
    private BeanConexion matriz;
        
    public MBDaemon() {
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
        sqlSession = getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperDaemon.class);
    }
    
    public List<MonVtaCompPagoE> getDocumentosPendientes(String pCodGrupoCia, String pCodCia, int pCantidad) throws Exception {
        List<MonVtaCompPagoE> lista = null;
        Map<String,Object>  mapParametros = new HashMap<String,Object>();                        
        
        mapParametros.put("cCodGrupoCia", pCodGrupoCia);
        mapParametros.put("cCodCia", pCodCia);
        mapParametros.put("nCantidad", pCantidad);
        
        lista = mapper.getDocumentosPendientes(mapParametros);
        
        return lista;        
    }
    

    @Override
    public void actualizaDocumentoE(MonVtaCompPagoE pDocumento) throws Exception{
        
        mapper.actualizaDocumentoE(pDocumento);
    }

    private SqlSessionFactory getSqlSessionFactory() {
        if(factory == null){
            factory = MyBatisUtil.getSqlSessionFactory(matriz);
        }
        return factory; 
    }

    @Override
    public void setConexionMatriz(BeanConexion matriz) {
        this.matriz = matriz;
    }

    @Override
    public String getRucCia(String pCodGrupoCia, String pCodCia) throws Exception{
        Map<String,Object>  mapParametros = new HashMap<String,Object>();                        
        
        mapParametros.put("cCodGrupoCia", pCodGrupoCia);
        mapParametros.put("cCodCia", pCodCia);
        
        return mapper.getRucCia(mapParametros);
    }

    @Override
    public List<BeanLocal> getLocales(String pCodGrupoCia, String pCodCia, int pCantidad) throws Exception{
        List<BeanLocal> lista =  null; 
        Map<String,Object>  mapParametros = new HashMap<String,Object>();                        
        
        mapParametros.put("cCodGrupoCia", pCodGrupoCia);
        mapParametros.put("cCodCia", pCodCia);
        mapParametros.put("nCantidad", pCantidad);
        
        lista = mapper.getLocales(mapParametros);
        return lista;
    }

    @Override
    public List<MonVtaCompPagoE> getDocsPendientes(String pCodGrupoCia, String pCodCia, String pCodLocal,
                                                   int pCantidad) {
        List<MonVtaCompPagoE> lista = null;
        Map<String,Object>  mapParametros = new HashMap<String,Object>();                        
        
        mapParametros.put("cCodGrupoCia", pCodGrupoCia);
        mapParametros.put("cCodCia", pCodCia);
        mapParametros.put("cCodLocal", pCodLocal);
        mapParametros.put("nCantidad", pCantidad);
        
        lista = mapper.getDocsPendientes(mapParametros);
        
        return lista;  
    }
}
