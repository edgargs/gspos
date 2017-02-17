/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimización de Transporte Urbano.
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
	
	/**
	 * Constructor que recibe todos las propiedades de la clase.
	 * @param eventType Referencia al tipo de evento.
	 * @param mobileIDType Referencia al tipo de dispositivo.
	 * @param mobileID Referencia al Id del dispositivo.
	 */
	public CalAmpEvent2(int eventType, int mobileIDType, String mobileID) {
		this.eventType = eventType;
		this.mobileIDType = mobileIDType;
		this.mobileID = mobileID;
	}
	
	/** Constructor por defecto */
	public CalAmpEvent2() {		
	}	
	
	// Message Header
	private int serviceType = -1;
	private int messageType = -1;
	private int sequence = 0;

	private long updateTime = 0;
	private long fixTime = 0;
	private double latitude = 0.0;
	private double longitude = 0.0;
	private int fixStatus = 0;
	private int satelliteCount = 0;
	private double HDOP = 0.0;
	private double altitudeMeters = 0.0;
	private double speedKPH = 0.0;
	private double heading = 0.0;
	private int carrier = 0;
	private int RSSI = 0;
	private int commState = 0;
	private long inputMask = -1;
	private int unitStatus = 0;
	private int eventIndex = 0;
	private int eventCode = 0;
	private int statusCode = 0;
	private Vector<Long> accums = null;
	private int userMessageType = 0;
	private byte[] userMessage = null;
	// private double odometerKM = 0.0;
	/**
	 * Determina el nivel de combustible. 
	 */
	private double fuel = 0.0;

	public void addAccumulator(long l) {
		if (this.accums == null) {
			this.accums = new Vector<>();
		}
		this.accums.add(new Long(l));
	}

	public double getOdometerKM() {
		if (accums != null && accums.size() > 0) {
			double d = this.accums.get(0);
			return d / 1000.0;
		} else {
			return 0.0;
		}
	}
}
