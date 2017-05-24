package hacom.pe.datos.entidades;
/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizaci�n de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/

import lombok.Data;

/**
 * Evento de vehiculos.
 * @version 1.0
 * @since 2017/02/16
 */
@Data
public class Event {
	
	/** Identificador del evento. */
	private int eventID;
	/** Identificador de la cuenta. */
	private int accountID;
	/** Identificador de la ruta. */
	private int routeID;
	/** Identificador del vehiculo. */
	private int vehicleID;
	/** Identificador del conductor. */
	private int personID;
	/** Identificador del gps. */
	private int gpsID;
	/** Identificador del chip. */
	private int chipID;
	/** Fecha del dispositivo. */
	private Long timestamp;
	/** Identificador del estado. */
	private int eventStatusID;
	/** Latitud. */
	private double latitude;
	/** Longitud. */
	private double longitude;
	/** Velocidad. */
	private double speedKPH;
	/** Direccion. */
	private String address;
	/** Odometro. */
	private double odometerKm;
	/** Fecha de creacion. */
	private Long creationTime;
	/** Gasolina. */
	private double fuel;
	/** Identificador de despacho. */
	private int dispatchID;
	/** Orientacion. */ 
	private double heading;
	/** Distancia recorrida. */
	private float distanceKM;
	/** HDOP. */
	private double HDOP;
	/** Cantidad de satelites. */
	private int satelliteCount;
	/** Descripcion sentido de movimiento. */
	private String headingDescription;

	private String deviceID;
	private int eventCode = 0;
	private String typeProvider = "";
	
}
