package com.gs.opengts.opt.servers.calamp;

import java.net.DatagramPacket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.StrategyDevice;
import com.gs.hacom.dcs.Util;
import com.gs.opengts.util.Payload;
import com.gs.opengts.util.StringTools;

import hacom.pe.datos.entidades.Event;

public class CalAmpPacketHandler implements StrategyDevice{

	private static final Logger logger = LogManager.getLogger(CalAmpPacketHandler.class);
	
	private CalAmpEvent2 myCalAmpEvent;

    /**
     * Convierte el paquete, en un evento del sistema.
     * @param arrby Trama recepcionada.
     * @return Evento parseado.
     */
	private CalAmpEvent2 getCalAmpEvent(byte[] arrby) {
		
		Payload payload = null;		
		payload = new Payload(arrby);
		payload.readSkip(1);
        
		CalAmpEvent2 calAmpEvent = new CalAmpEvent2();
	    
        int n = (int)payload.readULong(1, 0);
        calAmpEvent.setMobileID(DecodeBCD(payload.readBytes(n)));
        
        calAmpEvent.setMobileIDType((int)payload.readULong((int)payload.readULong(1, 0), 0));
        //Message Header
        calAmpEvent.setServiceType((int)payload.readULong(1, 0));
        calAmpEvent.setMessageType((int)payload.readULong(1, 0));
        calAmpEvent.setSequence((int)payload.readULong(2, 0));
        
        switch (calAmpEvent.getMessageType()) 
        {
			//case 0: {
		        //Print.logDebug((String)("OPTIONS_HEADER: " + StringTools.toHexString((byte[])arrby)), (Object[])new Object[0]);
		        //this.optionsMask = arrby[0] & 255;
		        //this.parseState = this.nextOptionsHeaderState();
		        //return null;
		    //} case 256: {
	            //Print.logDebug((String)("MOBILE_ID: " + StringTools.toHexString((byte[])arrby)), (Object[])new Object[0]);
        		//byte[] arrby0 = java.util.Arrays.copyOfRange(arrby, 1, 1+1+5);
	            //payload = new Payload(arrby0);
	            
	            //Print.logInfo((String)("Mobile ID: " + this.mobileIDString), (Object[])new Object[0]);
	            //this.parseState = this.nextOptionsHeaderState();
	            //return null;
	        //} case 512: {
	            //Print.logDebug((String)("MOBILE_ID_TYPE: " + StringTools.toHexString((byte[])arrby)), (Object[])new Object[0]);
	            //byte[] arrby1 = java.util.Arrays.copyOfRange(arrby, 7, 7+1+1);
	            //payload = new Payload(arrby1);
	            
	            //Print.logInfo((String)("Mobile ID Type: " + this.mobileIDType), (Object[])new Object[0]);
	            //this.parseState = this.nextOptionsHeaderState();
	            //return null;
        	case 2: { //case 2080: {
			    logger.debug("EVENT: [" + arrby.length + "] ");

			    myCalAmpEvent = parseMessage_EVENT(payload,calAmpEvent);
        	} case 10: { //} case 2208: {
                logger.debug("MINI_EVENT: [" + arrby.length + "] ");
                
                myCalAmpEvent = this.parseMessage_MINI_EVENT(payload,calAmpEvent);
            } default: {
            	myCalAmpEvent = calAmpEvent;
            }
			    
	    }
        
        return myCalAmpEvent;
	}
	
	/**
	 * Se emplea para decodificar en el formato BCD.
	 * @since 2010/01/01
	 * @param arrby Texto codificado en bytes
	 * @return cadena decodificada
	 */
	private static String DecodeBCD(byte[] arrby) {
        if ((arrby == null) || (arrby.length == 0))	{
            return "";
        }
        String string = StringTools.toHexString((byte[])arrby);
        return string.endsWith("F") ? string.substring(0, string.length() - 1) : string;
    }
	
	/**
	 * Obtiene el mensaje del tipo EVENT.
	 * @param payload Trama recepcionada.
	 * @param calAmpEvent Evento a completar.
	 * @return Evento del sistema.
	 */
	private CalAmpEvent2 parseMessage_EVENT(Payload payload, CalAmpEvent2 calAmpEvent) {
        //CalAmpEvent2 calAmpEvent = new CalAmpEvent2(); //(0, this.mobileIDType, this.mobileIDString);
        
        
        calAmpEvent.setUpdateTime(payload.readULong(4, 0, "UpdateTime"));
        calAmpEvent.setFixTime(payload.readULong(4, 0, "FixTime"));
        calAmpEvent.setLatitude((double)payload.readLong(4, 0, "Latitude") / 1.0E7);
        calAmpEvent.setLongitude((double)payload.readLong(4, 0, "Longitude") / 1.0E7);
        calAmpEvent.setAltitudeMeters((double)payload.readULong(4, 0, "Altitude") / 100.0);
        calAmpEvent.setSpeedKPH((double)payload.readULong(4, 0, "SpeedKPH") * 3600.0 * 1.0E-5);
        calAmpEvent.setHeading(payload.readULong(2, 0, "Heading"));
        calAmpEvent.setSatelliteCount((int)payload.readULong(1, 0, "SatCount"));
        calAmpEvent.setFixStatus((int)payload.readULong(1, 0, "FixStatus"));
        calAmpEvent.setCarrier((int)payload.readULong(2, 0, "Carrier"));
        calAmpEvent.setRSSI((int)payload.readLong(2, 0, "RSSI"));
        calAmpEvent.setCommState((int)payload.readULong(1, 0, "CommState"));
        calAmpEvent.setHDOP((double)payload.readULong(1, 0, "HDOP") / 10.0);
        calAmpEvent.setInputMask(payload.readULong(1, 0, "InputMask"));
        calAmpEvent.setUnitStatus((int)payload.readULong(1, 0, "UnitStatus"));
        calAmpEvent.setEventIndex((int)payload.readULong(1, 0, "EventIndex"));
        calAmpEvent.setEventCode((int)payload.readULong(1, 0, "EventCode"));
        long l = payload.readULong(1, 0, "AccumCount");
        payload.readSkip(1); //Spare
        logger.debug("Accumulators present: " + l);
        for (long i = 0; i < l; ++i) {
            long l2 = payload.readULong(4, 0, "Accum" + i);
            calAmpEvent.addAccumulator(l2);
        }
        logger.debug(calAmpEvent.toString());
        return calAmpEvent;
    }
	
    /**
	 * Obtiene el mensaje del tipo MINI_EVENT.
	 * @param payload Trama recepcionada.
	 * @param calAmpEvent Evento a completar.
	 * @return Evento del sistema.
	 */
    private CalAmpEvent2 parseMessage_MINI_EVENT(Payload payload, CalAmpEvent2 calAmpEvent) {
        //CalAmpEvent2 calAmpEvent = new CalAmpEvent2(); //(5, this.mobileIDType, this.mobileIDString);
        calAmpEvent.setUpdateTime(payload.readULong(4, 0));
        calAmpEvent.setLatitude((double)payload.readLong(4, 0) / 1.0E7);
        calAmpEvent.setLongitude((double)payload.readLong(4, 0) / 1.0E7);
        calAmpEvent.setHeading(payload.readULong(2, 0));
        calAmpEvent.setSpeedKPH(payload.readULong(1, 0));
        long l = payload.readULong(1, 0);
        calAmpEvent.setSatelliteCount((int)(l & 15));
        long l2 = 0;
        if ((l & 16) != 0) {
            l2 |= 64;
        }
        if ((l & 32) != 0) {
            l2 |= 8;
        }
        if ((l & 64) != 0) {
            l2 |= 4;
        }
        if ((l & 128) != 0) {
            l2 |= 32;
        }
        calAmpEvent.setFixStatus((int)l2);
        long l3 = payload.readULong(1, 0);
        calAmpEvent.setCommState((int)(l3 & 47));
        long l4 = 0;
        if ((l3 & 64) != 0) {
            l4 |= 2;
        }
        if ((l3 & 128) != 0) {
            l4 |= 8;
        }
        calAmpEvent.setUnitStatus((int)l4);
        calAmpEvent.setInputMask(payload.readULong(1, 0));
        calAmpEvent.setEventCode((int)payload.readULong(1, 0));
        long l5 = payload.readULong(1, 0);
        logger.debug("Accumulators present: " + l5);
        for (long i = 0; i < l5; ++i) {
            long l6 = payload.readULong(4, 0);
            calAmpEvent.addAccumulator(l6);
        }
        logger.debug(calAmpEvent.toString());
        return calAmpEvent;
    }
    
    /**
     * Crea mensaje de ACK.
     * @param bl True si se proceso correctamente.
     * @param sequence Secuencia del mensaje.
     * @param messageType Tipo de mensaje.
     * @return Mensaje de respuesta.
     */
    private byte[] createPacket_ACK(boolean bl, int sequence, int messageType) {
    	int n;
        Payload payload = new Payload();
        /*if (ACK_INCLUDE_ID && !ListTools.isEmpty((byte[])this.mobileIDBytes)) {
            n = 129;
            if (this.mobileIDTypeLen > 0) {
                n |= 2;
            }
            payload.writeULong((long)n, 1);
            payload.writeULong((long)this.mobileIDBytes.length, 1);
            payload.writeBytes(this.mobileIDBytes);
            if (this.mobileIDTypeLen > 0) {
                payload.writeULong((long)this.mobileIDTypeLen, 1);
                payload.writeULong((long)this.mobileIDType, this.mobileIDTypeLen);
            }
        }*/
        //Message Header
        payload.writeULong(2, 1); //Service Type 2 = Response to an Acknowledged Request
        payload.writeULong(1, 1); //Message Type 1 = ACK/NAK message
        payload.writeULong((long)sequence, 2); //Sequence Number (original)
        //Acknowledge Message (Message Type 1)
        n = bl ? 0 : 1;
        payload.writeULong((long)messageType, 1); //Type (original)
        payload.writeULong((long)n, 1); //ACK
        payload.writeZeroFill(1); //Spare
        payload.writeBytes(new byte[]{0, 0, 0}); //App Version
        return payload.getBytes();
    }
    
    public byte[] createPacket_ACK(boolean bl) {
    	return createPacket_ACK(bl, myCalAmpEvent.getSequence(), myCalAmpEvent.getMessageType());
    }
    
    public Event getEvent(byte[] arrby) {
    	getCalAmpEvent(arrby);
    	
    	Event newEvent = new Event();
    	newEvent.setDeviceID(myCalAmpEvent.getMobileID());
    	newEvent.setTimestamp(myCalAmpEvent.getUpdateTime());
    	newEvent.setEventCode(myCalAmpEvent.getEventCode());
    	newEvent.setLatitude(myCalAmpEvent.getLatitude());
    	newEvent.setLongitude(myCalAmpEvent.getLongitude());
    	newEvent.setSpeedKPH(myCalAmpEvent.getSpeedKPH());
    	newEvent.setOdometerKm(myCalAmpEvent.getOdometerKM());
    	newEvent.setFuel(myCalAmpEvent.getFuel());
    	newEvent.setHeading(myCalAmpEvent.getHeading());
    	newEvent.setHDOP(myCalAmpEvent.getHDOP());
    	newEvent.setSatelliteCount(myCalAmpEvent.getSatelliteCount());
    	newEvent.setCreationTime(Util.TimeEpoch());
		
    	return newEvent;
    }
    
    public Event getEvent(DatagramPacket peticion) {
    	return getEvent(peticion.getData());
    }
}
