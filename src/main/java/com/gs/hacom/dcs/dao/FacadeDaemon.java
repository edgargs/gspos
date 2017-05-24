/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

import hacom.pe.datos.entidades.Event;

/**
 * Fachada de interaccion y comunicacion.
 * 
 * @version 1.0
 * @since 2017/01/01
 * @see com.gs.hacom.dcs.dao.FactoryDaemon
 */
public class FacadeDaemon {
	
	private static final Logger logger = LogManager.getLogger(FacadeDaemon.class);
	
	private DAODaemon dao;

	private boolean cancel;
    
    public FacadeDaemon(Properties propDatabase) throws Exception {
        super();
        
        dao = FactoryDaemon.getDAODaemon(FactoryDaemon.Tipo.MYBATIS, propDatabase);
    }

    /**
	 * Graba el paquete en base de datos.
	 * @param myEvent Evento del sistema.
	 * @return True si se grabo correctamente.
	 */
	public boolean saveEvent(CalAmpEvent2 myEvent) {
		logger.info("Graba Evento:"+myEvent.getMobileID());
		boolean booState = false;
		try{
		    //1.0 Abre conexion
		    dao.openConnection();
		    //TimeUnit.SECONDS.sleep(15);
		    //2.0 Graba
		    if(!cancel){
		    	dao.registraEvento(myEvent);
			    dao.commit();
			    booState = true;
		    }else{
		    	logger.warn("Operacion cancelada");
		    }
		}catch(Exception e){
		    dao.rollback();
		    logger.error("",e);
		    booState = false;
		}
		return booState;
	}
	
	/**
	 * Obtiene la configuracion de la trama.
	 * @param intDeviceType Tipo del dispositivo.
	 * @return Configuracion de la trama.
	 */
	public List<ConfigMessage> getConfigMessage(int intDeviceType){
		List<ConfigMessage> configMessage = null;
		try {
			dao.openConnection();
			ConfigMessage myDevice = new ConfigMessage();
			myDevice.setDeviceID(intDeviceType);
			configMessage = dao.getConfigMessage(myDevice);
			dao.commit();
			for (ConfigMessage myConfig : configMessage) {
				logger.debug(String.format("%s - %s", myConfig.getOrderField(), myConfig.getNameField()));
			}
		} catch( Exception e) {
			dao.rollback();
			logger.error("",e);
		}
		return configMessage;
	}
	
	/**
	 * Cancela la operacion.
	 */
	public void cancel() {
		this.cancel = true;
	}
	
	/**
	 * Graba el paquete en base de datos.
	 * @param myEvent Evento del sistema.
	 * @return True si se grabo correctamente.
	 */
	public boolean saveEvent(Event myEvent) {
		//logger.info("Graba Evento:"+myEvent.getMobileID());
		boolean booState = false;
		try{
		    //1.0 Abre conexion
		    dao.openConnection();
		    //TimeUnit.SECONDS.sleep(15);
		    //2.0 Graba
		    if(!cancel){
		    	dao.registraEvento(myEvent);
			    dao.commit();
			    booState = true;
		    }else{
		    	logger.warn("Operacion cancelada");
		    }
		}catch(Exception e){
		    dao.rollback();
		    logger.error("",e);
		    booState = false;
		}
		return booState;
	}
}
