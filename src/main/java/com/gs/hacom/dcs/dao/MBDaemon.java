package com.gs.hacom.dcs.dao;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.Util;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

public class MBDaemon implements DAODaemon {

	private static final Logger logger = LogManager.getLogger(MBDaemon.class);
    
    private SqlSession sqlSession = null;
    private MapperDaemon mapper = null;
    //private SqlSessionFactory factory = null; 
    
    public MBDaemon(Properties propDatabase) {
		Util.iniciaConfiguracion(propDatabase);
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

    private SqlSessionFactory getSqlSessionFactory() {
        return MyBatisUtil.getSqlSessionFactory();
    }

	@Override
	public void registraEvento(CalAmpEvent2 myEvent) throws Exception {
		
        mapper.registraEvento(myEvent);
		
	}

	@Override
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage) throws Exception {
		return mapper.getConfigMessage(configMessage);
	}

}
