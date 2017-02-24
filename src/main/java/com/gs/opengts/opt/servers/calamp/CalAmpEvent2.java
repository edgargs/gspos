/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.opengts.opt.servers.calamp;

import java.util.Vector;

import lombok.Data;

/**
 * Almacena los valores del trama enviada por el dispositivo GPS.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
@Data
public class CalAmpEvent2 {

	/** Tipo de evento. */
	private int eventType = 0;
	/** Tipo de dispotivo. */
	private int mobileIDType = 0;
	/** ID del dispositivo. */
	private String mobileID = null;
	
	// Message Header
	/** Tipo de servicio. */
	private int serviceType = -1;
	/** Tipo de mensaje. */
	private int messageType = -1;
	/** Secuencia del mensaje. */
	private int sequence = 0;

	/** Fecha del dispositivo. */
	private long updateTime = 0;
	/** Fecha de envio. */
	private long fixTime = 0;
	/** Latitud. */
	private double latitude = 0.0;
	/** Longitud. */
	private double longitude = 0.0;
	/** Estado de envio. */
	private int fixStatus = 0;
	/** Cantidad de satelites. */
	private int satelliteCount = 0;
	/** Valor HDOP. */
	private double HDOP = 0.0;
	/** Altitud. */
	private double altitudeMeters = 0.0;
	/** Velocidad. */
	private double speedKPH = 0.0;
	/** Orientacion. */
	private double heading = 0.0;
	/** Operador. */
	private int carrier = 0;
	/** Valor RSSI. */
	private int RSSI = 0;
	/** Estado de comunicacion. */
	private int commState = 0;
	/** Unidad de entrada. */
	private long inputMask = -1;
	/** Estado de unidad. */
	private int unitStatus = 0;
	/** Indice de evento. */
	private int eventIndex = 0;
	/** Codigo de evento. */
	private int eventCode = 0;
	/** Codigo de estado. */
	private int statusCode = 0;
	/** Acumuladores. */
	private Vector<Long> accums = null;
	/** Tipo de mensaje de usuario. */
	private int userMessageType = 0;
	/** Mensaje de usuario. */
	private byte[] userMessage = null;
	/** Determina el nivel de combustible. */
	private double fuel = 0.0;

	
	/**
	 * Constructor que recibe todos las propiedades de la clase.
	 * @param eventType Referencia al tipo de evento.
	 * @param mobileIDType Referencia al tipo de dispositivo.
	 * @param mobileID Referencia al Id del dispositivo.
	 * @param serviceType Tipo de servicio.
	 * @param messageType Tipo de mensaje.
	 * @param sequence Secuencia del mensaje.
	 * @param updateTime Fecha del dispositivo.
	 * @param fixTime Fecha de envio.
	 * @param latitude Latitud.
	 * @param longitude Longitud.
	 * @param fixStatus Estado de envio.
	 * @param satelliteCount Cantidad de satelites.
	 * @param hDOP Valor HDOP.
	 * @param altitudeMeters Altitud.
	 * @param speedKPH Velocidad.
	 * @param heading Orientacion.
	 * @param carrier Operador.
	 * @param rSSI Valor RSSI.
	 * @param commState Estado de comunicacion.
	 * @param inputMask Unidad de entrada.
	 * @param unitStatus Estado de unidad.
	 * @param eventIndex Indice de evento.
	 * @param eventCode Codigo de evento.
	 * @param statusCode Codigo de estado.
	 * @param accums Acumuladores.
	 * @param userMessageType Tipo de mensaje de usuario.
	 * @param userMessage Mensaje de usuario.
	 * @param fuel Nivel de combustible.
	 */
	public CalAmpEvent2(int eventType, int mobileIDType, String mobileID, int serviceType, int messageType,
			int sequence, long updateTime, long fixTime, double latitude, double longitude, int fixStatus,
			int satelliteCount, double hDOP, double altitudeMeters, double speedKPH, double heading, int carrier,
			int rSSI, int commState, long inputMask, int unitStatus, int eventIndex, int eventCode, int statusCode,
			Vector<Long> accums, int userMessageType, byte[] userMessage, double fuel) {
		super();
		this.eventType = eventType;
		this.mobileIDType = mobileIDType;
		this.mobileID = mobileID;
		this.serviceType = serviceType;
		this.messageType = messageType;
		this.sequence = sequence;
		this.updateTime = updateTime;
		this.fixTime = fixTime;
		this.latitude = latitude;
		this.longitude = longitude;
		this.fixStatus = fixStatus;
		this.satelliteCount = satelliteCount;
		HDOP = hDOP;
		this.altitudeMeters = altitudeMeters;
		this.speedKPH = speedKPH;
		this.heading = heading;
		this.carrier = carrier;
		RSSI = rSSI;
		this.commState = commState;
		this.inputMask = inputMask;
		this.unitStatus = unitStatus;
		this.eventIndex = eventIndex;
		this.eventCode = eventCode;
		this.statusCode = statusCode;
		this.accums = accums;
		this.userMessageType = userMessageType;
		this.userMessage = userMessage;
		this.fuel = fuel;
	}
	
	/** Constructor por defecto */
	public CalAmpEvent2() {		
	}
	
	/**
	 * Agrega el acumulador.
	 * @param l Dato del acumulador.
	 */
	public void addAccumulator(long l) {
		if (this.accums == null) {
			this.accums = new Vector<>();
		}
		this.accums.add(new Long(l));
	}

	/**
	 * Obtiene el odometro del dispositivo.
	 * @return Odometro.
	 */
	public double getOdometerKM() {
		if (accums != null && accums.size() > 0) {
			double d = this.accums.get(0);
			return d / 1000.0;
		} else {
			return 0.0;
		}
	}
}
