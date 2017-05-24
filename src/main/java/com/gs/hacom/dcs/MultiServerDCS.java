/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
	2017/02/01  Edgar Rios
	  [REQ]-[001] Se agrega control para reinicio. Espera 3 minutos. 
  =======================================================================================
*/
package com.gs.hacom.dcs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Inicio del servidor.
 * 
 * @version 1.0
 * @since 2017/01/01
 * @see com.gs.hacom.dcs.ServerUDPThread
 */
public class MultiServerDCS {

	private static final Logger logger = LogManager.getLogger(MultiServerDCS.class);

	/**
	 * Ejecuta el servidor.
	 * @param args Puerto de inicio.
	 */
	public static void main(String[] args) {
		String strPort = "";
		if (args.length == 0) {
			strPort = "20700";
		} else if (args.length == 1) {
			strPort = args[0];
		} else if (args.length != 1) {
			logger.error("Usage: java KKMultiServer <port number>");
			System.exit(1);
		}
		
		//Lee propiedades
		Properties propDatabase = null;
		try {
			propDatabase = Util.loadFromFile();
		} catch (IOException e1) {
			logger.error("",e1);
			System.exit(1);
		}

		logger.info("Inicio del programa.");
		while (true) {
			int portNumber = Integer.parseInt(strPort);
			boolean listening = true;
			boolean isUDP = true;
			if (!isUDP) {
				/*
				 * try (ServerSocket serverSocket = new
				 * ServerSocket(portNumber)) { while (listening) { new
				 * KKMultiServerThread(serverSocket.accept()).start(); } } catch
				 * (IOException e) {
				 * System.err.println("Could not listen on port " + portNumber);
				 * System.exit(-1); }
				 */
			} else {
				int lengthBuffer = 1024;
				String typeProvider = propDatabase.getProperty("Provider", "CALAMP");
				if (typeProvider.equals("CELLOCATOR")){
					lengthBuffer = 70;
				}
				try (DatagramSocket datagramSocket = new DatagramSocket(portNumber)) {
					while (listening) {
						byte bufer[] = new byte[lengthBuffer];
						DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
						datagramSocket.receive(peticion);
						new ServerUDPThread(datagramSocket, peticion, propDatabase).start();
					}
				} catch (IOException e) {
					logger.error("Could not listen on port " + portNumber, e);
				}
			}
			try {
				logger.warn("Espera antes de volver a re-iniciar.");
				Thread.sleep(3 * 60 * 1000);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
	}
}
