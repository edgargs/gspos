package mifarma.transf.daemon.dao;

import java.io.Reader;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Properties;

import mifarma.transf.daemon.bean.BeanLocal;
import mifarma.transf.daemon.bean.BeanTransferencia;
import mifarma.transf.daemon.bean.LgtGuiaRem;
import mifarma.transf.daemon.bean.LgtNotaEsCab;
import mifarma.transf.daemon.bean.LgtNotaEsDet;
import mifarma.transf.daemon.util.BeanConexion;
import mifarma.transf.daemon.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.apache.ibatis.session.SqlSessionFactory;

import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
    
    public List<BeanLocal> getLocalesEnvio(String pCodGrupoCia, String pCodCia) throws Exception {
        List<BeanLocal> lista = null;
        Map<String,Object>  mapParametros = new HashMap<String,Object>();                        
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", pCodCia);
        mapper.getLocalesEnvio(mapParametros);
        lista = (List<BeanLocal>)mapParametros.get("listado");
        
        return lista;        
    }
    
    public List<BeanTransferencia> getNotasPendientes(String pCodGrupoCia, String pCodCia, String pCodLocal) throws Exception {
        List<BeanTransferencia> lista = null;
        Map<String,Object>  mapParametros = new HashMap<String,Object>();                        
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", pCodCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapper.getNotasPendientes(mapParametros);
        lista = (List<BeanTransferencia>)mapParametros.get("listado");
        
        return lista;        
    }

    @Override
    public LgtNotaEsCab getNotaEsCab(BeanTransferencia pLgtNotaEsCab) throws Exception{
        LgtNotaEsCab lgtNotaEsCab = null;
        Map<String,Object>  mapParametros = new HashMap<String,Object>(); 
        
        mapParametros.put("cCodGrupoCia", pLgtNotaEsCab.getCodGrupoCia());
        mapParametros.put("cCodLocal", pLgtNotaEsCab.getCodLocal());
        mapParametros.put("cNumNotaEs", pLgtNotaEsCab.getNumNotaEs());
        lgtNotaEsCab = mapper.getNotaEsCab(mapParametros);
        
        return lgtNotaEsCab;
    }

    @Override
    public List<LgtNotaEsDet> getNotaEsDet(BeanTransferencia pLgtNotaEsCab) throws Exception{
        List<LgtNotaEsDet> lstLgtNotaEsDet = null;
        Map<String,Object>  mapParametros = new HashMap<String,Object>(); 
        
        mapParametros.put("cCodGrupoCia", pLgtNotaEsCab.getCodGrupoCia());
        mapParametros.put("cCodLocal", pLgtNotaEsCab.getCodLocal());
        mapParametros.put("cNumNotaEs", pLgtNotaEsCab.getNumNotaEs());
        lstLgtNotaEsDet = mapper.getNotaEsDet(mapParametros);
        
        return lstLgtNotaEsDet;
    }

    @Override
    public List<LgtGuiaRem> getGuiaRem(BeanTransferencia pLgtNotaEsCab) throws Exception{
        List<LgtGuiaRem> lstLgtGuiaRem = null;
        Map<String,Object>  mapParametros = new HashMap<String,Object>(); 
        
        mapParametros.put("cCodGrupoCia", pLgtNotaEsCab.getCodGrupoCia());
        mapParametros.put("cCodLocal", pLgtNotaEsCab.getCodLocal());
        mapParametros.put("cNumNotaEs", pLgtNotaEsCab.getNumNotaEs());
        lstLgtGuiaRem = mapper.getGuiaRem(mapParametros);
        
        return lstLgtGuiaRem;
    }

    @Override
    public void actualizaEnvioDestino(BeanTransferencia pLgtNotaEsCab) {
        Map<String,Object>  mapParametros = new HashMap<String,Object>(); 
        
        mapParametros.put("cCodGrupoCia_in", pLgtNotaEsCab.getCodGrupoCia());
        mapParametros.put("cCodCia_in", pLgtNotaEsCab.getCodCia());
        mapParametros.put("cCodLocal_in", pLgtNotaEsCab.getCodLocal());
        mapParametros.put("cNumNotaEs_in", pLgtNotaEsCab.getNumNotaEs());
        mapper.actualizaEnvioDestino(mapParametros);
    }

    @Override
    public void grabarTransfBV(BeanTransferencia beanTransferencia) {
        Map<String,Object>  mapParametros = new HashMap<String,Object>(); 
        
        mapParametros.put("cCodGrupoCia_in", beanTransferencia.getCodGrupoCia());
        mapParametros.put("cCodLocalOrigen_in", "");
        mapParametros.put("cCodLocalDestino_in", beanTransferencia.getCodLocalDestino());
        mapParametros.put("cNumNotaEs_in", beanTransferencia.getNumNotaEs());
        mapParametros.put("vIdUsu_in", "DAEMON_TRANSF");
        mapper.grabarTransfBV(mapParametros);
    }

    private SqlSessionFactory getSqlSessionFactory() {
        return MyBatisUtil.getSqlSessionFactory(matriz.getDBCIA(), matriz);
    }


    @Override
    public void setConexionMatriz(BeanConexion matriz) {
        this.matriz = matriz;
    }

    @Override
    public String validaConexionLocal(BeanConexion beanConexion) throws Exception{
        String vIndicador;
        Map<String,Object>  mapParametros = new HashMap<String,Object>(); 
        
        mapParametros.put("p_HOST_NAME", beanConexion.getIPBD());
        mapParametros.put("p_PORT", beanConexion.getPuertoBD());
        mapper.validaConexionLocal(mapParametros);
        vIndicador = (String)mapParametros.get("indicador");
        return vIndicador;
    }
}
