package com.gs.hacom.a.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.Util;
import com.gs.hacom.dcs.dao.ConfigMessage;
import com.gs.hacom.dcs.dao.FacadeDaemon;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;
import com.gs.opengts.util.StringTools;

public class ServidorUDP {

	private static final Logger logger = LogManager.getLogger(ServidorUDP.class);
	
	public static void main(String args[]) {

		logger.info("Inicio del servidor");
		
		DatagramSocket socketUDP = null;
		byte[] bufer = new byte[1000];

		
		
		try {
			socketUDP = new DatagramSocket(20700);
			//boolean isConn = connServer.startConnection();

			while (true) {
				// Construimos el DatagramPacket para recibir peticiones
				DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);

				// Leemos una peticion del DatagramSocket
				socketUDP.receive(peticion);

				logger.debug("Datagrama recibido del host: " + peticion.getAddress());
				logger.debug(" la data: " + StringTools.toHexString(peticion.getData()));
				logger.debug(" desde el puerto remoto: " + peticion.getPort());

				FacadeDaemon facadeDaemon = new FacadeDaemon();
				List<ConfigMessage> configMessage = facadeDaemon.getConfigMessage(1);
				
				// Grabamos en BBDD
				Util utilDCS = new Util();
				CalAmpEvent2 myEvent = utilDCS.getEvent(peticion.getData());
				
				boolean stateSave = true; //facadeDaemon.saveEvent(myEvent);

				if (stateSave) {
					byte finalPacket[] = utilDCS.createPacket_ACK(true, myEvent.getSequence(), myEvent.getMessageType());
					// Construimos el DatagramPacket para enviar la respuesta
					DatagramPacket respuesta = new DatagramPacket(finalPacket, finalPacket.length,
							peticion.getAddress(), peticion.getPort());

					// Enviamos la respuesta, que es un eco
					socketUDP.send(respuesta);
				} else {
					logger.warn("No hay conexion con BBDD");
				}
			}

		} catch (SocketException e) {
			logger.error("Socket: " + e.getMessage());
		} catch (IOException e) {
			logger.error("IO: " + e.getMessage());
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			try {
				socketUDP.close();
			} catch (NullPointerException ex) {
			}
			//connServer.endConnection();
		}
	}

}
