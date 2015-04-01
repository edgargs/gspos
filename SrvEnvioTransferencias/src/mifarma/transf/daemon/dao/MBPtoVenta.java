package mifarma.transf.daemon.dao;

import java.util.List;

import mifarma.transf.daemon.bean.LgtGuiaRem;
import mifarma.transf.daemon.bean.LgtNotaEsCab;
import mifarma.transf.daemon.bean.LgtNotaEsDet;
import mifarma.transf.daemon.util.BeanConexion;
import mifarma.transf.daemon.util.MyBatisUtil;

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
    public void saveLgtNotaEsCab(LgtNotaEsCab lgtNotaEsCab) {
        mapper.saveNotaEsCab(lgtNotaEsCab);
    }

    @Override
    public void saveLgtNotaEsDet(List<LgtNotaEsDet> lstNotaEsDet) {
        for(LgtNotaEsDet lgtNotaEsDet:lstNotaEsDet){
            mapper.saveNotaEsDet(lgtNotaEsDet);
        }
    }

    @Override
    public void saveLgtGuiaRem(List<LgtGuiaRem> lstGuiaRem) {
        for(LgtGuiaRem lgtGuiaRem:lstGuiaRem){
            mapper.saveGuiaRem(lgtGuiaRem);
        }
    }
}
