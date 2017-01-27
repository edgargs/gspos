package com.gs.hacom.dcs;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;
import com.gs.opengts.util.Payload;
import com.gs.opengts.util.StringTools;

public class Util {

	private static final Logger logger = LogManager.getLogger(Util.class);
	
	public static String IPBD;
	public static String SID;
	public static String UsuarioBD;
	public static String ClaveBD;
	public static String PuertoBD;
    
	//private int parseState = 2080;
	private byte[] mobileIDBytes = null;
    //private String mobileIDString = null;
    private int mobileIDTypeLen = -1;
    //private int mobileIDType = -1;
    
    public static String[] UNIQUEID_PREFIX = null;
    public static double MINIMUM_SPEED_KPH = 0.0;
    public static long SIMEVENT_DIGITAL_INPUTS = 255;
    public static double MAXIMUM_HDOP = -1.0;
    public static boolean ESTIMATE_ODOMETER = false;
    
	public CalAmpEvent2 getEvent(byte[] arrby) {
		
		Payload payload = null;
	    //int n = 0;
	    
        //switch (this.parseState ) 
        //{
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
	        //} case 2080: {
			    //Print.logDebug((String)("EVENT: [" + arrby.length + "] " + StringTools.toHexString((byte[])arrby)), (Object[])new Object[0]);
	            //byte[] arrby2 = java.util.Arrays.copyOfRange(arrby, 13, arrby.length);
			    payload = new Payload(arrby);

			    return parseMessage_EVENT(payload);
			    //this.parseState = 0;
			    //return null;
			//}        
	    //}			    
	}
	
	private CalAmpEvent2 parseMessage_EVENT(Payload payload) {
        CalAmpEvent2 calAmpEvent = new CalAmpEvent2(); //(0, this.mobileIDType, this.mobileIDString);
        
        payload.readSkip(1);
        
        int n = (int)payload.readULong(1, 0);
        this.mobileIDBytes = payload.readBytes(n);
        calAmpEvent.setMobileID(DecodeBCD(this.mobileIDBytes));
        
        this.mobileIDTypeLen = (int)payload.readULong(1, 0);
        calAmpEvent.setMobileIDType((int)payload.readULong(this.mobileIDTypeLen, 0));
        //Message Header
        calAmpEvent.setServiceType((int)payload.readULong(1, 0));
        calAmpEvent.setMessageType((int)payload.readULong(1, 0));
        calAmpEvent.setSequence((int)payload.readULong(2, 0));
        
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
        //this.insertEventRecord(calAmpEvent);
        logger.debug(calAmpEvent.toString());
        return calAmpEvent;
    }
	
	private static String DecodeBCD(byte[] arrby) {
        if ((arrby == null) || (arrby.length == 0))	{
            return "";
        }
        String string = StringTools.toHexString((byte[])arrby);
        return string.endsWith("F") ? string.substring(0, string.length() - 1) : string;
    }
	
//	public static boolean saveEvent(ConnectMSSQLServer connServer, CalAmpEvent myEvent) {
//		String insertString = "INSERT INTO MTXEvent(accountID) VALUES(?)";
//		Object[] parameters = new Object[] { myEvent.getCarrier() };
//		return connServer.executeSQL(insertString, parameters);
//	}
	

    public static void iniciaConfiguracion(Properties properties) {
        IPBD = properties.getProperty("IPDB");
        SID = properties.getProperty("NameDB");
        UsuarioBD = properties.getProperty("UserDB");
        ClaveBD = properties.getProperty("PasswordDB");        
        PuertoBD= properties.getProperty("PortDB");        
    }
    
    public static Properties loadFromFile(String filename) throws IOException {
        
    	String value = System.getenv("configProgram");
        if (value != null) {
        	Path configLocation = Paths.get(value);
            try (InputStream stream = Files.newInputStream(configLocation)) {
                Properties config = new Properties();
                config.load(stream);
                logger.debug(config);
                return config;
            }
        } else {
        	value = filename;
        	try (Reader reader = Resources.getResourceAsReader(value)) {
                Properties config = new Properties();
                config.load(reader);
                logger.debug(config);
                return config;
            }
        }
    }
    
    public double getMinimumSpeedKPH() {
        return (double)MINIMUM_SPEED_KPH;
    }
    
    public long getSimulateDigitalInputs() {
        return (long)SIMEVENT_DIGITAL_INPUTS & 255;
    }
    
    public double getMaximumHDOP() {
        return (double)MAXIMUM_HDOP;
    }
    
    public boolean getEstimateOdometer() {
        return (boolean)ESTIMATE_ODOMETER;
    }
    
    public byte[] createPacket_ACK(boolean bl, int sequence, int messageType) {
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
}

