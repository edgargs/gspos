package com.gs.hacom.dcs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.dao.FacadeDaemon;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;
import com.gs.opengts.util.StringTools;

public class ServerUDPThread extends Thread {
	
	private static final Logger logger = LogManager.getLogger(ServerUDPThread.class);
	
	private DatagramPacket peticion = null;
	private DatagramSocket socketUDP = null;
	
	private Util utilDCS = new Util();
	private CalAmpEvent2 myEvent = null;
	private FacadeDaemon facadeDaemon;
	
    /*public ServerUDPThread() {
    	
    }*/
    
	public ServerUDPThread(DatagramSocket socketUDP, DatagramPacket udpClient) {
		logger.info("Nuevo Hilo "+Calendar.getInstance().getTimeInMillis());
		this.socketUDP = socketUDP;
		this.peticion = udpClient;
	}

	public void run() {
		try {
			facadeDaemon = new FacadeDaemon();
		} catch (Exception e1) {
			logger.error("",e1);
			return;
		}
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		//Future<Boolean> future = executorService.submit(()->processTrama());
		Future<Boolean> future = executorService.submit(new Callable<Boolean>(){
			public Boolean call(){
				return processTrama();
			}
		});
		boolean stateSave = false;
		try {
			stateSave = future.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			facadeDaemon.cancel();
			logger.error("",e);
		}

		if(myEvent != null){
			byte finalPacket[] = utilDCS.createPacket_ACK(stateSave, myEvent.getSequence(), myEvent.getMessageType());
			
			// Construimos el DatagramPacket para enviar la respuesta
			DatagramPacket respuesta = new DatagramPacket(finalPacket, finalPacket.length,
					peticion.getAddress(), peticion.getPort());
			
			// Enviamos la respuesta
			try {
				socketUDP.send(respuesta);
			} catch (IOException e) {
				logger.error("",e);
			}
		}
	}
	
	private boolean processTrama() {
		boolean stateSave = false;
        try  {
                       
        	logger.debug("Datagrama recibido del host: " + peticion.getAddress());
        	logger.debug(" la data: " + StringTools.toHexString(peticion.getData()));
        	logger.debug(" desde el puerto remoto: " + peticion.getPort());
 
			
			// Grabamos en BBDD
        	
			myEvent = utilDCS.getEvent(peticion.getData());
			
			stateSave = facadeDaemon.saveEvent(myEvent);
			
			if (!stateSave) {
				logger.warn("Error en BBDD");
			}
         
        } catch (Exception e) {
           logger.error("",e);
        }
        
        return stateSave;
    }
	
	
}
