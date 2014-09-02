package com.gs.pos.configuracion.domain;

import com.gs.pos.util.DAOTransaccion;

import com.gs.pos.util.MyBatisUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOConfiguracion implements DAOTransaccion {
    
    private static final Logger log = LoggerFactory.getLogger(DAOConfiguracion.class);
    
    private SqlSession sqlSession = null;
    private MapperConfiguracion mapper = null;
    
    public DAOConfiguracion() {
        super();
    }

    @Override
    public void openConnection() throws Exception{
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperConfiguracion.class);
    }

    @Override
    public void commit() throws Exception{
        sqlSession.commit(true);
        sqlSession.close();
    }

    @Override
    public void rollback() throws Exception{
        sqlSession.rollback(true);
        sqlSession.close();
    }
    
    public Usuario validarLogin(String pLogin) throws Exception{

        Map<String,Object> mapParametros = new HashMap<>();
        mapParametros.put("login", pLogin);
        return mapper.validarLogin(mapParametros);
    }
}
