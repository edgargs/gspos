/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History: 
  =======================================================================================
*/
package com.gs.hacom.dcs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.dao.FacadeDaemon;
import com.gs.opengts.util.StringTools;

import hacom.pe.datos.entidades.Event;

/**
 * Servidor UPD.
 * 
 * @version 1.0
 * @since 2017/01/01
 * @see com.gs.hacom.dcs.dao.FacadeDaemon
 */
public class ServerUDPThread extends Thread {
	
	private static final Logger logger = LogManager.getLogger(ServerUDPThread.class);
	
	private DatagramPacket peticion = null;
	private DatagramSocket socketUDP = null;
	Properties propDatabase;
	
	//private CalAmpPacketHandler utilDCS = new CalAmpPacketHandler();
	 List<Event> map = Collections.synchronizedList(new ArrayList<Event>());
	private FacadeDaemon facadeDaemon;
	
	private ContextDevice context = null;
	
	private long time01,time02,time03,time04;
	
    /**
     * Constructor del servicio UDP.
     * @param socketUDP Puerto de inicio.
     * @param udpClient Paquete a procesar.
     * @param propDatabase Propiedades del servicio.
     */
	public ServerUDPThread(DatagramSocket socketUDP, DatagramPacket udpClient, Properties propDatabase, ContextDevice context) {
		time01 = Calendar.getInstance().getTimeInMillis();
		logger.debug("Nuevo Hilo "+time01);
		this.socketUDP = socketUDP;
		this.peticion = udpClient;
		this.propDatabase = propDatabase;
		
		this.context = context;
	}
	
	public ServerUDPThread(Properties propDatabase, ContextDevice context) {
		time01 = Calendar.getInstance().getTimeInMillis();
		logger.debug("Nuevo Hilo "+time01);
		
		this.propDatabase = propDatabase;
		
		this.context = context;
	}

	/**
	 * Procesa el paquete enviado y retorna ACK.
	 */
	public void run() {
		try {
			facadeDaemon = new FacadeDaemon(propDatabase);
		
		
		boolean detener = false;
		while(!detener){
			
			Event myEvent = null;
			synchronized(map) {
				if (map.size() > 0) {
					myEvent = map.remove(0);
				} else {
					detener = true;
					//Thread.sleep(1*1000);
					continue;
				}
			}
				
			if (myEvent != null) {
				this.socketUDP = myEvent.getDatagramSocket();
				this.peticion = myEvent.getPeticion();
				processTrama(myEvent);
			}
			
			// SOLO PARA PRUEBAS
			//Thread.sleep(10*1000);
			
		}
		
		} catch (Exception e1) {
			logger.error("",e1);
			return;
		}
	}
	
	/**
	 * Graba el paquete en base de datos.
	 * @return True si se grabo correctamente.
	 */
	private boolean processTrama(Event myEvent) {
		boolean stateSave = false;
        try  {
                       
        	logger.debug("Datagrama recibido del host: " + peticion.getAddress());
        	logger.debug(" la data: " + StringTools.toHexString(peticion.getData()));
        	logger.debug(" desde el puerto remoto: " + peticion.getPort());
 
			
			// Grabamos en BBDD
        	
			
			time02 = Calendar.getInstance().getTimeInMillis();
			stateSave = facadeDaemon.saveEvent(myEvent);
			time03 = Calendar.getInstance().getTimeInMillis();
			if (!stateSave) {
				logger.warn("Error en BBDD");
			}
         
        } catch (Exception e) {
           logger.error("",e);
        }
        
        if(myEvent != null){
			
			
			byte finalPacket[] = context.createPacket_ACK(stateSave);
			
			// Construimos el DatagramPacket para enviar la respuesta
			DatagramPacket respuesta = new DatagramPacket(finalPacket, finalPacket.length,
					peticion.getAddress(), peticion.getPort());
			logger.debug(String.format("Envio de respuesta: %s %s %s",stateSave,peticion.getAddress(), peticion.getPort()));
			// Enviamos la respuesta
			try {
				socketUDP.send(respuesta);
			} catch (IOException e) {
				logger.error("",e);
			}
			
			time04 = Calendar.getInstance().getTimeInMillis();
			logger.info(String.format("Finaliza transaccion: %d %d %d %d", time01,time02-time01,time03-time02,time04-time03));
		}
        
        return stateSave;
    }

	public void addEvent(Event myEvent) {
		
		map.add(myEvent);
		
	}
	
	
}
