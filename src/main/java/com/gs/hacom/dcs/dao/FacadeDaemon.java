package com.gs.hacom.dcs.dao;

import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.Util;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;

public class FacadeDaemon {
	
	private static final Logger logger = LogManager.getLogger(FacadeDaemon.class);
	
	private DAODaemon dao;
    
    public FacadeDaemon() throws Exception {
        super();
        Properties propDatabase = Util.loadFromFile("config.properties");
        dao = FactoryDaemon.getDAODaemon(FactoryDaemon.Tipo.MYBATIS, propDatabase);
    }

	public boolean saveEvent(CalAmpEvent2 myEvent) {
		logger.info("Graba Evento:"+myEvent.getMobileID());
		boolean booState;
		try{
		    //1.0 Abre conexion
		    dao.openConnection();
		    //2.0 Graba transferencia BV
		    //dao.grabarTransfBV(pLgtNotaEsCab);
		    //3.0 Actualiza envio
		    dao.registraEvento(myEvent);
		    dao.commit();
		    booState = true;
		}catch(Exception e){
		    dao.rollback();
		    logger.error("",e);
		    booState = false;
		}
		return booState;
	}
	
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
}
