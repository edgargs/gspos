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

import com.gs.hacom.dcs.dao.ConnectMSSQLServer;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;
import com.gs.opengts.util.GeoPoint;
import com.gs.opengts.util.ListTools;
import com.gs.opengts.util.Payload;
import com.gs.opengts.util.Print;
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
    private String mobileIDString = null;
    private int mobileIDTypeLen = -1;
    private int mobileIDType = -1;
    
    public static String[] UNIQUEID_PREFIX = null;
    public static double MINIMUM_SPEED_KPH = 0.0;
    public static long SIMEVENT_DIGITAL_INPUTS = 255;
    public static double MAXIMUM_HDOP = -1.0;
    public static boolean ESTIMATE_ODOMETER = false;
    
	public CalAmpEvent2 getEvent(byte[] arrby) {
		
		Payload payload = null;
	    int n = 0;
	    
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
        payload.readSkip(1);
        //Print.logInfo((String)("Accumulators present: " + l), (Object[])new Object[0]);
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
	
	public static boolean saveEvent(ConnectMSSQLServer connServer, CalAmpEvent myEvent) {
		String insertString = "INSERT INTO MTXEvent(accountID) VALUES(?)";
		Object[] parameters = new Object[] { myEvent.getCarrier() };
		return connServer.executeSQL(insertString, parameters);
	}
	

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
    
    /*private EventData createEventRecord(Device device, AccumulatorGroup accumulatorGroup, long l, int n, Geozone geozone, CalAmpEvent calAmpEvent) {
        int n2;
        String string;
        //Account account = device.getAccount();
        int n3 = n2 = n > 0 ? n : calAmpEvent.getStatusCode();
        if (n2 == 0) {
            return null;
        }
        //String string2 = device.getAccountID();
        //String string3 = device.getDeviceID();
        long l2 = l > 0 ? l : calAmpEvent.getTimestamp();
        double d = calAmpEvent.getSpeedKPH();
        double d2 = calAmpEvent.getHeading();
        if (d <= this.getMinimumSpeedKPH()) {
            d = 0.0;
            d2 = 0.0;
        }
        //EventData.Key key = new EventData.Key(string2, string3, l2, n2);
        //EventData eventData = new EventData(); //(EventData)key.getDBRecord();
        //eventData.setGeozone(geozone);
        //eventData.setGeoPoint(calAmpEvent.getGeoPoint());
        if (calAmpEvent.hasInputMask()) {
            //eventData.setInputMask(calAmpEvent.getInputMask());
        }
        //eventData.setSpeedKPH(d);
        //eventData.setHeading(d2);
        //eventData.setAltitude(calAmpEvent.getAltitudeMeters());
        /*if (calAmpEvent.hasOdometer(accumulatorGroup)) {
            eventData.setOdometerKM(calAmpEvent.getOdometerKM(accumulatorGroup));
        }*/
        /*if (calAmpEvent.hasDriverID(accumulatorGroup)) {
            string = StringTools.format((long)calAmpEvent.getDriverID(accumulatorGroup), (String)"0000000000");
            Print.logInfo((String)("Driver ID : " + string), (Object[])new Object[0]);
            eventData.setDriverID(string);
            device.setDriverID(string);
        }
        if (calAmpEvent.hasRfidTag(accumulatorGroup)) {
            string = StringTools.format((long)calAmpEvent.getRfidTag(accumulatorGroup), (String)"0000000000");
            Print.logInfo((String)("RFID Tag  : " + string), (Object[])new Object[0]);
            eventData.setRfidTag(string);
        }
        for (int i = 0; i < 8; ++i) {
            if (calAmpEvent.hasTemperature(accumulatorGroup, i)) {
                double d3 = calAmpEvent.getTemperature(accumulatorGroup, i);
                Print.logInfo((String)("Temp C    : #" + i + " " + d3), (Object[])new Object[0]);
                eventData.setThermoAverage(i, d3);
                continue;
            }
            eventData.setThermoAverage(i, -9999.0);
        }
        if (calAmpEvent.hasFuelLevel(accumulatorGroup)) {
            double d4 = calAmpEvent.getFuelLevel(accumulatorGroup);
            Print.logInfo((String)("Fuel Level: " + d4), (Object[])new Object[0]);
            eventData.setFuelLevel(d4);
        }
        if (calAmpEvent.hasBrakeGForce(accumulatorGroup)) {
            double d5 = calAmpEvent.getBrakeGForce(accumulatorGroup);
            Print.logInfo((String)("Braking Gs: " + d5), (Object[])new Object[0]);
            eventData.setBrakeGForce(d5);
        }
        if (calAmpEvent.hasBatteryVolts(accumulatorGroup)) {
            double d6 = calAmpEvent.getBatteryVolts(accumulatorGroup);
            Print.logInfo((String)("Battery V : " + d6), (Object[])new Object[0]);
            eventData.setBatteryVolts(d6);
            eventData.setVBatteryVolts(d6);
        }
        if (calAmpEvent.hasIdleTimeSec(accumulatorGroup)) {
            double d7 = (double)calAmpEvent.getIdleTimeSec(accumulatorGroup) / 3600.0;
            Print.logInfo((String)("Idle Hours: " + d7), (Object[])new Object[0]);
            eventData.setIdleHours(d7);
        }
        if (calAmpEvent.hasWorkTimeSec(accumulatorGroup)) {
            double d8 = (double)calAmpEvent.getWorkTimeSec(accumulatorGroup) / 3600.0;
            Print.logInfo((String)("Work Hours : " + d8), (Object[])new Object[0]);
            eventData.setWorkHours(d8);
        }
        if (calAmpEvent.hasRunTimeSec(accumulatorGroup)) {
            double d9 = (double)calAmpEvent.getRunTimeSec(accumulatorGroup) / 3600.0;
            Print.logInfo((String)("Run Hours : " + d9), (Object[])new Object[0]);
            eventData.setEngineHours(d9);
        }
        Print.logInfo((String)("Sat Count : " + calAmpEvent.getSatelliteCount()), (Object[])new Object[0]);
        Print.logInfo((String)("Carrier   : " + calAmpEvent.getCarrier()), (Object[])new Object[0]);
        Print.logInfo((String)("HDOP      : " + calAmpEvent.getHDOP()), (Object[])new Object[0]);
        Print.logInfo((String)("RSSI      : " + calAmpEvent.getRSSI()), (Object[])new Object[0]);
        eventData.setSatelliteCount(calAmpEvent.getSatelliteCount());
        eventData.setMobileNetworkCode(calAmpEvent.getCarrier());
        eventData.setHDOP(calAmpEvent.getHDOP());
        eventData.setSignalStrength((double)calAmpEvent.getRSSI());
        return eventData;
    }*/

    private boolean insertEventRecord(CalAmpEvent calAmpEvent) {
        String string;
        //List<Device.GeozoneTransition> list;
        if (!calAmpEvent.hasUniqueID()) {
            Print.logError((String)"Invalid CalAmpEvent ...", (Object[])new Object[0]);
            return false;
        }
        //DCServerFactory.checkMemoryUsage();
        String string2 = "";
        //this.device = null;
        try {
            if (ListTools.isEmpty((Object[])UNIQUEID_PREFIX)) {
                string2 = calAmpEvent.getUniqueID("");
                //this.device = Transport.loadDeviceByUniqueID((String)string2);
            } else {
                /*for (int i = 0; i < UNIQUEID_PREFIX.length && this.device == null; ++i) {
                    string2 = calAmpEvent.getUniqueID(UNIQUEID_PREFIX[i]);
                    //this.device = Transport.loadDeviceByUniqueID((String)string2);
                }*/
            }
            /*if (this.device == null) {
                Print.logError((String)("Unique-ID not found: " + calAmpEvent.getMobileID()), (Object[])new Object[0]);
                //Print.sysPrintln((String)"NotFound: now=%d id=%s", (Object[])new Object[]{DateTime.getCurrentTimeSec(), calAmpEvent.getMobileID()});
                //DCServerFactory.addUnassignedDevice((String)Constants.DEVICE_CODE(), (String)calAmpEvent.getMobileID(), (String)this.getIPAddress(), (boolean)this.isDuplex(), (double)calAmpEvent.getLatitude(), (double)calAmpEvent.getLongitude());
                return false;
            }*/
        }
        catch (Throwable var3_4) {
            Print.logError((String)("Exception getting Device: " + string2 + " [" + var3_4 + "]"), (Object[])new Object[0]);
            return false;
        }
        /*String string3 = this.device.getAccountID();
        String string4 = this.device.getDeviceID();
        if (!this.device.getAccount().isActive() || !this.device.isActive()) {
            Print.logWarn((String)("Account/Device is inactive: " + string3 + "/" + string4 + " [" + string2 + "]"), (Object[])new Object[0]);
            return false;
        }
        Print.logInfo((String)("UniqueID  : " + string2), (Object[])new Object[0]);
        Print.logInfo((String)("DeviceID  : " + string3 + "/" + string4), (Object[])new Object[0]);*/
        //DataTransport dataTransport = this.device.getDataTransport();
        /*if (this.hasIPAddress() && !dataTransport.isValidIPAddress(this.getIPAddress())) {
            Print.logError((String)("Invalid IP Address for device: " + this.getIPAddress() + " [expecting " + (Object)dataTransport.getIpAddressValid() + "]"), (Object[])new Object[0]);
            return false;
        }*/
        //this.device.setIpAddressCurrent(this.getIPAddress());
        //this.device.setRemotePortCurrent(this.getRemotePort());
        //this.device.setLastTotalConnectTime(DateTime.getCurrentTimeSec());
        /*if (!dataTransport.getDeviceCode().equalsIgnoreCase(Constants.DEVICE_CODE())) {
            dataTransport.setDeviceCode(Constants.DEVICE_CODE());
        }*/
        String string5 = "" + calAmpEvent.getMobileIDType() + ":" + calAmpEvent.getMobileID();
        //this.device.setImeiNumber(string5);
        //AccumulatorGroup accumulatorGroup = AccumulatorGroup.GetAccumulatorGroup(Device.GetDcsPropertiesID((Device)this.device));
        //Print.logInfo((String)("AccumGroup: " + accumulatorGroup.getName()), (Object[])new Object[0]);
        long l = calAmpEvent.getTimestamp();
        double d = calAmpEvent.getHDOP();
        boolean bl = calAmpEvent.isValidGeoPoint();
        double d2 = this.getMaximumHDOP();
        if (!bl) {
            calAmpEvent.setLatitude(0.0);
            calAmpEvent.setLongitude(0.0);
        } else if (d2 > 0.0 && d > d2) {
            Print.logWarn((String)("Invalidating GeoPoint due to excessive HDOP: " + d), (Object[])new Object[0]);
            calAmpEvent.setLatitude(0.0);
            calAmpEvent.setLongitude(0.0);
            bl = false;
        }
        GeoPoint geoPoint = bl ? calAmpEvent.getGeoPoint() : GeoPoint.INVALID_GEOPOINT;
        Print.logInfo((String)("GeoPoint  : " + (Object)geoPoint), (Object[])new Object[0]);
        /*if ((!calAmpEvent.hasOdometer(accumulatorGroup) || calAmpEvent.getOdometerKM(accumulatorGroup) <= 0.0) && bl) {
            double d3 = this.getEstimateOdometer() ? this.device.getNextOdometerKM(geoPoint) : this.device.getLastOdometerKM();
            calAmpEvent.setOdometerKM(d3);
        }
        Print.logInfo((String)("OdomKM    : " + calAmpEvent.getOdometerKM(accumulatorGroup)), (Object[])new Object[0]);*/
        int n = 0;
        /*if (this.getSimulateGeozones() && bl && (list = this.device.checkGeozoneTransitions(l, geoPoint)) != null) {
            for (Device.GeozoneTransition geozoneTransition : list) {
                string = StatusCodes.GetHex((int)geozoneTransition.getStatusCode());
                String string6 = StatusCodes.GetDescription((int)geozoneTransition.getStatusCode(), (BasicPrivateLabel)null);
                Print.logInfo((String)("Geozone   : " + (Object)geozoneTransition), (Object[])new Object[0]);
                Print.logInfo((String)("Status    : [" + string + "] " + string6), (Object[])new Object[0]);
                EventData eventData = this.createEventRecord(this.device, accumulatorGroup, geozoneTransition.getTimestamp(), geozoneTransition.getStatusCode(), geozoneTransition.getGeozone(), calAmpEvent);
                this.device.insertEventData(eventData);
                ++n;
            }
        }*/
        /*if (calAmpEvent.hasInputMask()) {
            long l2;
            long l3 = calAmpEvent.getInputMask();
            long l4 = this.getSimulateDigitalInputs();
            if (l4 > 0 && (l2 = (this.device.getLastInputState() ^ l3) & l4) != 0) {
                for (int i = 0; i <= 7; ++i) {
                    long l5 = 1 << i;
                    if ((l2 & l5) == 0) continue;
                    int n2 = (l3 & l5) != 0 ? CalAmpEvent.InputStatusCodes_ON[i] : CalAmpEvent.InputStatusCodes_OFF[i];
                    String string7 = StatusCodes.GetHex((int)n2);
                    String string8 = StatusCodes.GetDescription((int)n2, (BasicPrivateLabel)null);
                    Print.logInfo((String)("Status    : [" + string7 + "] " + string8), (Object[])new Object[0]);
                    EventData eventData = this.createEventRecord(this.device, accumulatorGroup, l, n2, null, calAmpEvent);
                    this.device.insertEventData(eventData);
                    ++n;
                }
            }
            //this.device.setLastInputState(l3 & 255);
            Print.logDebug((String)("GPIO      : 0x" + StringTools.toHexString((long)(this.device.getLastInputState() & 255), (int)8)), (Object[])new Object[0]);
        } else {
            Print.logDebug((String)("GPIO(last): 0x" + StringTools.toHexString((long)(this.device.getLastInputState() & 255), (int)8)), (Object[])new Object[0]);
        }*/
        int n3 = calAmpEvent.getStatusCode();
        if (n3 != 0) {
            /*String string9 = StatusCodes.GetHex((int)n3);
            String string10 = StatusCodes.GetDescription((int)n3, (BasicPrivateLabel)null);
            Print.logInfo((String)("Status    : [" + (String)string9 + "] " + string10), (Object[])new Object[0]);
            EventData xxxx = this.createEventRecord(this.device, accumulatorGroup, l, n3, null, calAmpEvent);
            this.device.insertEventData(xxxx);*/
            ++n;
        }
        /*try {
            this.device.updateChangedEventFields(new String[]{"driverID"});
        }
        catch (DBException var18_21) {
            Print.logException((String)("Unable to update Device: " + string3 + "/" + string4), (Throwable)var18_21);
        }*/
        return true;
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
    
    byte[] createPacket_ACK(boolean bl, int sequence, int messageType) {
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

