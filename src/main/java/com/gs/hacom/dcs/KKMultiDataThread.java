package com.gs.hacom.dcs;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.dao.FacadeDaemon;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;
import com.gs.opengts.util.StringTools;

public class KKMultiDataThread extends Thread {
	
	private static final Logger logger = LogManager.getLogger(KKMultiDataThread.class);
	
	private DatagramPacket peticion = null;
	private DatagramSocket socketUDP = null;
	
    public KKMultiDataThread() {
    	
    }
    
	public KKMultiDataThread(DatagramSocket socketUDP, DatagramPacket udpClient) {
		logger.info("Nuevo Hilo "+Calendar.getInstance().getTimeInMillis());
		this.socketUDP = socketUDP;
		this.peticion = udpClient;
	}

	public void run() {
				
        try  {
                       
        	logger.debug("Datagrama recibido del host: " + peticion.getAddress());
        	logger.debug(" la data: " + StringTools.toHexString(peticion.getData()));
        	logger.debug(" desde el puerto remoto: " + peticion.getPort());
 
			
			// Grabamos en BBDD
        	Util utilDCS = new Util();
			CalAmpEvent2 myEvent = utilDCS.getEvent(peticion.getData());
			FacadeDaemon facadeDaemon = new FacadeDaemon();
			boolean stateSave = facadeDaemon.saveEvent(myEvent);
			
			if (stateSave) {
				byte finalPacket[] = utilDCS.createPacket_ACK(true, myEvent.getSequence(), myEvent.getMessageType());
				// Construimos el DatagramPacket para enviar la respuesta
				DatagramPacket respuesta = new DatagramPacket(finalPacket, finalPacket.length,
						peticion.getAddress(), peticion.getPort());
				
				// Enviamos la respuesta
				socketUDP.send(respuesta);
			} else {
				logger.warn("No hay conexion con BBDD");
			}
         
        } catch (Exception e) {
           logger.error("",e);
        }
    }
	
	
}
