/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.Util;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

import hacom.pe.datos.entidades.Event;

/**
 * Interacciones Mybatis con base de datos.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
public class MBDaemon implements DAODaemon {

	private static final Logger logger = LogManager.getLogger(MBDaemon.class);
    
    private SqlSession sqlSession = null;
    private MapperDaemon mapper = null;
    //private SqlSessionFactory factory = null; 
    
    public MBDaemon(Properties propDatabase) {
		Util.iniciaConfiguracion(propDatabase);
	}

    /**
	 * Confirma la transaccion.
	 */
	@Override
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

	/**
     * Deshace la transaccion.
     */
    @Override
    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }
    
    /**
     * Inicia comunicacion.
     */
    @Override
    public void openConnection() {
        sqlSession = getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperDaemon.class);
    }

    private SqlSessionFactory getSqlSessionFactory() {
        return MyBatisUtil.getSqlSessionFactory();
    }
    
    /**
	 * Registra el evento en base de datos.
	 * @param myEvent Evento del sistema.
	 * @throws Exception Error de base de datos.
	 */
	
	public void registraEvento(CalAmpEvent2 myEvent) throws Exception {
		HashMap<String,Object> params = new HashMap<>();
		params.put("deviceID",myEvent.getMobileID());
		params.put("timestamp",myEvent.getUpdateTime());
		params.put("eventCode",myEvent.getEventCode());
		params.put("latitude",myEvent.getLatitude());
		params.put("longitude",myEvent.getLongitude());
		params.put("speedKPH",myEvent.getSpeedKPH());
		params.put("odometerKm",myEvent.getOdometerKM());
		params.put("fuel",myEvent.getFuel());
		params.put("heading",myEvent.getHeading());
		params.put("HDOP",myEvent.getHDOP());
		params.put("satelliteCount",myEvent.getSatelliteCount());
		params.put("creationTime",Util.TimeEpoch());
		logger.debug(params);
        mapper.registraCalAmpEvent(params);
		
	}

	/**
	 * Obtiene la configuracion de la trama.
	 * @param configMessage Filtro para la consulta.
	 * @return Configuracion de la trama.
	 * @throws Exception Error de base de datos.
	 */
	@Override
	public List<ConfigMessage> getConfigMessage(ConfigMessage configMessage) throws Exception {
		return mapper.getConfigMessage(configMessage);
	}
	
	/**
	 * Registra el evento en base de datos.
	 * @param myEvent Evento del sistema.
	 * @throws Exception Error de base de datos.
	 */
	@Override
	public void registraEvento(Event myEvent) throws Exception {
		mapper.registraEvento(myEvent);
	}

}
