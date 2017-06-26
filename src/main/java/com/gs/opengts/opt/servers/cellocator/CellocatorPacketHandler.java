package com.gs.opengts.opt.servers.cellocator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.StrategyDevice;
import com.gs.hacom.dcs.Util;
import com.gs.opengts.util.Payload;
import com.gs.opengts.util.Print;
import com.gs.opengts.util.StringTools;

import hacom.pe.datos.entidades.Event;

public class CellocatorPacketHandler implements StrategyDevice{

	private static final Logger logger = LogManager.getLogger(CellocatorPacketHandler.class);
			
    private byte[] byteMessageNumerator;
	private byte[] byteUnitID;

	private CellocatorEvent getHandlePacket(byte[] pktBytes) {

        double longitude = 0.0;
        double latitude = 0.0;
        double altitude = 0.0;
        double speedKPH = 0.0;
        double heading = 0.0;
        double odometer = 0.0;
        int intFromPayload = 0;
        int checksum = 0;
        String strFromPayload;
        byte[] bytesFromPayload;
        int intUnitID;
        String strUnitID;
//        int intMessageNumerator;
        int utcTimeYear;
        int utcTimeMonth;
        int utcTimeDay;
        int utcTimeHours;
        int utcTimeMinutes;
        int utcTimeSeconds;
        String strUtcTime;
        int[] intUnitIO = new int[4];
//        int intTransReason;

        /* DATA COMMUNICATION FORMAT:

        Data length: 70 bytes

        Byte 01 - 04 (4 bytes)     [ASCII]                  ---> System Code:   4D434750[HEX] - "MCGP"[ASCII]
        Byte 05      (1 byte )       [DEC]                  ---> Message Type
        Byte 06 - 09 (4 bytes) [INTEL-DEC] (Little Indian)  ---> Unit's ID
        Byte 10 - 11 (2 bytes)                              ---> Communication Control Field
        Byte 12      (1 byte )                              ---> Message numerator (Anti-Tango™)
        Byte 13      (1 byte )                              ---> Unit’s hardware version
        Byte 14      (1 byte )                              ---> Unit’s software version
        Byte 15      (1 byte )                              ---> Protocol Version Identifier
        Byte 16      (1 byte )                              ---> Unit’s status + Current GSM Operator (1st nibble)
        Byte 17      (1 byte )                              ---> Current GSM Operator (2nd and 3rd nibble)
        Byte 18      (1 byte )                              ---> Transmission Reason Specific Data
        Byte 19      (1 byte )                              ---> Transmission reason
        Byte 20      (1 byte )                              ---> Unit’s mode of operation
        Byte 21      (1 byte )                              ---> Unit’s I/O status - byte 1
        Byte 22      (1 byte )                              ---> Unit’s I/O status - byte 2
        Byte 23      (1 byte )                              ---> Unit’s I/O status - byte 3
        Byte 24      (1 byte )                              ---> Unit’s I/O status - byte 4
        Byte 25      (1 byte )                              ---> Current GSM Operator (4th and 5th nibble)
        Byte 26      (1 byte )                              ---> Analog input - 1
        Byte 27      (1 byte )                              ---> Analog input - 2
        Byte 28      (1 byte )                              ---> Analog input - 3
        Byte 29      (1 byte )                              ---> Analog input - 4
        Byte 30 - 32 (3 bytes)                              ---> Mileage counter (total 24 bits)
        Byte 33 - 38 (6 bytes)                              ---> Multi-purpose field: Driver /Passenger/ Group ID, PSP/Keyboard Specific Data, Accelerometer Status or SIM IMSI
        Byte 39 - 40 (2 bytes)                              ---> Last GPS Fix
        Byte 41      (1 byte )                              ---> Location status (from unit)
        Byte 42      (1 byte )                              ---> Mode 1 (from GPS)
        Byte 43      (1 byte )                              ---> Mode 2 (from GPS)
        Byte 44      (1 byte )                              ---> Number of satellites used (from GPS)
        Byte 45 - 48 (4 bytes)                              ---> Longitude
        Byte 49 - 52 (4 bytes)                              ---> Latitude
        Byte 53 - 56 (4 bytes)                              ---> Altitude
        Byte 57 - 60 (4 bytes)                              ---> Ground speed
        Byte 61 - 62 (2 bytes)                              ---> Speed direction (true course)
        Byte 63      (1 byte )                              ---> UTC time  seconds
        Byte 64      (1 byte )                              ---> UTC time  minutes
        Byte 65      (1 byte )                              ---> UTC time  hours
        Byte 66      (1 byte )                              ---> UTC date  day
        Byte 67      (1 byte )                              ---> UTC date  month
        Byte 68 - 69 (2 bytes)                              ---> GPS date  year
        Byte 70      (1 byte )                              ---> 8-bit additive checksum (excluding system code)

        */

        /* check packet length */
        if ((pktBytes == null) || (pktBytes.length == 0)) {

            logger.error((String)"Packet is null.");
            return null;
        }

        logger.debug((String)("Message received: [" + StringTools.toHexString((byte[])pktBytes)) + "]");

        if(pktBytes.length < Constants.MESSAGE_LENGTH_BYTES){
            logger.error((String)"Message size is not %d bytes. Message size: [%d].", Constants.MESSAGE_LENGTH_BYTES, pktBytes.length);
            return null;
        }

        //checksum = pktBytes[Constants.CHECKSUM_INDEX];
        //Print.logInfo("Checksum received: [%d]", checksum);

        //String s = StringTools.toStringValue(pktBytes).trim();
        //Print.logInfo("Recv: " + s);    // debug message

        Payload payload = new Payload(pktBytes);

        //System Code
        strFromPayload = payload.readString(4);
        logger.debug(String.format("System Code: [%s]", strFromPayload));
        if(!strFromPayload.equals(Constants.SYSTEM_CODE)){
            logger.error((String)"Wrong System Code.");
            return null;
        }

        //Message Type
        intFromPayload = (int)payload.readULong(1,0);
        if(intFromPayload != 0){
            logger.error((String)"Wrong Message Type. Message Type received: [%d]", intFromPayload);
            return null;
        }
        Print.logInfo("Message Type: [%d]", intFromPayload);

        //Unit's ID
        //intFromPayload = (int)payload.readULong(4,0,false);
        //Print.logInfo("Unit's ID: [%d]", intFromPayload);
        //intUnitID = intFromPayload;
        this.byteUnitID = payload.readBytes(4);
        ByteBuffer byteBufferUnitID = ByteBuffer.wrap(this.byteUnitID);
        byteBufferUnitID.order(ByteOrder.LITTLE_ENDIAN);
        intUnitID = byteBufferUnitID.getInt();

        //strUnitID = String.format("%012d", intUnitID);
        strUnitID = String.valueOf(intUnitID);
        logger.info("Unit's ID: [" + strUnitID + "]");

        CellocatorEvent cellocatorEvent = new CellocatorEvent(strUnitID);

        //Communication Control Field
        bytesFromPayload = payload.readBytes(2);
        Print.logInfo((String)("Comm. Control Field: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Message numerator
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Message numerator: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        this.byteMessageNumerator = bytesFromPayload;

        //Unit’s hardware version
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Hardware version: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Unit’s software version
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Software version: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Protocol Version Identifier
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Protocol Version ID: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Unit’s status + Current GSM Operator (1st nibble)
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Unit’s status + Current GSM Operator (1st nibble): [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Current GSM Operator (2nd and 3rd nibble)
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Current GSM Operator (2nd and 3rd nibble): [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Transmission Reason Specific Data
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Transmission Reason Specific Data: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Transmission reason
        //bytesFromPayload = payload.readBytes(1);
        //Print.logInfo((String)("Transmission reason: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        //TODO: Verify if this is the correct way to send the event code.
        intFromPayload = (int)payload.readULong(1, 0);
        Print.logInfo("Transmission reason / Event code: [%d]", intFromPayload);
        cellocatorEvent.setEventCode(intFromPayload);
//        intTransReason = intFromPayload;
//        Print.logInfo("Transmission reason: [%d]", intTransReason);

        //Unit’s mode of operation
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Unit’s mode of operation: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Unit’s I/O status - byte 1
//        bytesFromPayload = payload.readBytes(1);
//        Print.logInfo((String)("Unit’s I/O status - byte 1: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(1, 0);
        intUnitIO[0] = intFromPayload;
        Print.logInfo("Unit’s I/O status - byte 1: [%d]", intUnitIO[0]);
        cellocatorEvent.setIntUnitIO_1(intUnitIO[0]);

        //Unit’s I/O status - byte 2
//        bytesFromPayload = payload.readBytes(1);
//        Print.logInfo((String)("Unit’s I/O status - byte 2: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(1, 0);
        intUnitIO[1] = intFromPayload;
        Print.logInfo("Unit’s I/O status - byte 2: [%d]", intUnitIO[1]);
        cellocatorEvent.setIntUnitIO_2(intUnitIO[1]);

        //Unit’s I/O status - byte 3
//        bytesFromPayload = payload.readBytes(1);
//        Print.logInfo((String)("Unit’s I/O status - byte 3: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(1, 0);
        intUnitIO[2] = intFromPayload;
        Print.logInfo("Unit’s I/O status - byte 3: [%d]", intUnitIO[2]);
        cellocatorEvent.setIntUnitIO_3(intUnitIO[2]);

        //Unit’s I/O status - byte 4
//        bytesFromPayload = payload.readBytes(1);
//        Print.logInfo((String)("Unit’s I/O status - byte 4: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(1, 0);
        intUnitIO[3] = intFromPayload;
        Print.logInfo("Unit’s I/O status - byte 4: [%d]", intUnitIO[3]);
        cellocatorEvent.setIntUnitIO_4(intUnitIO[3]);

        //Current GSM Operator (4th and 5th nibble)
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Current GSM Operator (4th and 5th nibble): [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Analog input - 1
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Analog input - 1: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Analog input - 2
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Analog input - 2: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Analog input - 3
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Analog input - 3: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Analog input - 4
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Analog input - 4: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Mileage counter (total 24 bits)
        //bytesFromPayload = payload.readBytes(3);
        //Print.logInfo((String)("Mileage counter: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(3,0,false);
        odometer = intFromPayload;
        Print.logInfo("Odometer: [%.2f]", odometer);
        cellocatorEvent.setOdometerKM(odometer);

        //Multi-purpose field: Driver /Passenger/ Group ID, PSP/Keyboard Specific Data, Accelerometer Status or SIM IMSI
        bytesFromPayload = payload.readBytes(6);
        Print.logInfo((String)("Multi-purpose field: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Last GPS Fix
        bytesFromPayload = payload.readBytes(2);
        Print.logInfo((String)("Last GPS Fix: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Location status (from unit)
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Location status: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Mode 1 (from GPS)
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Mode 1: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Mode 2 (from GPS)
        bytesFromPayload = payload.readBytes(1);
        Print.logInfo((String)("Mode 2: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");

        //Number of satellites used (from GPS)
        //bytesFromPayload = payload.readBytes(1);
        //Print.logInfo((String)("Number of satellites: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int) payload.readULong(1,0);
        Print.logInfo("Number of satellites: [%d]", intFromPayload);
        cellocatorEvent.setSatelliteCount(intFromPayload);

        //Longitude
        //bytesFromPayload = payload.readBytes(4);
        //Print.logInfo((String)("Longitude: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(4,0,false);
        longitude = intFromPayload * (180/Math.PI) * Math.pow(10,-8);
        Print.logInfo("Longitude: [%f]", longitude);
        cellocatorEvent.setLongitude(longitude);

        //Latitude
        //bytesFromPayload = payload.readBytes(4);
        //Print.logInfo((String)("Latitude: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(4,0,false);
        latitude = intFromPayload * (180/Math.PI) * Math.pow(10,-8);
        Print.logInfo("Latitude: [%f].", latitude);
        cellocatorEvent.setLatitude(latitude);

        //Altitude
        //bytesFromPayload = payload.readBytes(4);
        //Print.logInfo((String)("Altitude: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(4,0,false);
        altitude = intFromPayload * 0.01;
        Print.logInfo("Altitude: [%f] mtrs.", altitude);
        cellocatorEvent.setAltitudeMeters(altitude);

        //Ground speed
        //bytesFromPayload = payload.readBytes(4);
        //Print.logInfo((String)("Ground speed: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(4,0,false);
        speedKPH = intFromPayload/(100.*1000.)*3600.;
        Print.logInfo("Speed: [%f] KPH.", speedKPH);
        cellocatorEvent.setSpeedKPH(speedKPH);

        //Speed direction (true course)
        //bytesFromPayload = payload.readBytes(2);
        //Print.logInfo((String)("Speed direction: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int)payload.readULong(2,0,false);
        heading = intFromPayload/1000.*180/Math.PI;
        Print.logInfo("Heading: [%f] grades", heading);
        cellocatorEvent.setHeading(heading);

        //UTC time  seconds
        //bytesFromPayload = payload.readBytes(1);
        //Print.logInfo((String)("UTC time seconds: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int) payload.readULong(1,0);
        Print.logInfo("UTC time seconds: [%d]", intFromPayload);
        utcTimeSeconds = intFromPayload;

        //UTC time  minutes
        //bytesFromPayload = payload.readBytes(1);
        //Print.logInfo((String)("UTC time minutes: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int) payload.readULong(1,0);
        Print.logInfo("UTC time minutes: [%d]", intFromPayload);
        utcTimeMinutes = intFromPayload;

        //UTC time  hours
        //bytesFromPayload = payload.readBytes(1);
        //Print.logInfo((String)("UTC time hours: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int) payload.readULong(1,0);
        Print.logInfo("UTC time hours: [%d]", intFromPayload);
        utcTimeHours = intFromPayload;

        //UTC date  day
        //bytesFromPayload = payload.readBytes(1);
        //Print.logInfo((String)("UTC date day: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int) payload.readULong(1,0);
        Print.logInfo("UTC time day: [%d]", intFromPayload);
        utcTimeDay = intFromPayload;

        //UTC date  month
        //bytesFromPayload = payload.readBytes(1);
        //Print.logInfo((String)("UTC date month: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int) payload.readULong(1,0);
        Print.logInfo("UTC time month: [%d]", intFromPayload);
        utcTimeMonth = intFromPayload;

        //GPS date  year
        //bytesFromPayload = payload.readBytes(2);
        //Print.logInfo((String)("GPS date year: [" + StringTools.toHexString((byte[])bytesFromPayload)) + "](HEX)");
        intFromPayload = (int) payload.readULong(2,0, false);
        Print.logInfo("UTC time year: [%d]", intFromPayload);
        utcTimeYear = intFromPayload;

        strUtcTime = String.format("%04d-%02d-%02d %02d:%02d:%02d GMT", utcTimeYear, utcTimeMonth, utcTimeDay, utcTimeHours,
                utcTimeMinutes, utcTimeSeconds);
        Print.logInfo("UTC time: " + strUtcTime);
        cellocatorEvent.setUpdateTime(convertStringTotimestamp(strUtcTime));
        logger.debug(String.format("timestamp: [%d].", cellocatorEvent.getTimestamp()));

        //Checksum
        //payload.readSkip(1); // Already read above.
        intFromPayload = (int) payload.readULong(1,0);
        checksum = intFromPayload;
        //Verify Checksum
        int checksumCalc = 0;
        for(int i=4; i<pktBytes.length - 1; i++){
            checksumCalc = checksumCalc + (int)pktBytes[i];
        }
        checksumCalc = checksumCalc & 0xFF;
        if( checksum != checksumCalc){
            logger.error(String.format("Checksum error. Receive: [%d] <> Calculated: [%d].", checksum, checksumCalc));
            return null;
        }
        Print.logInfo("Checksum: [%d] OK!", checksum);

//        //Build event code
//        //cellocatorEvent.setEventCode(intFromPayload);
//        switch (intTransReason){
//            case   4: // Emergency (Distress) mode by command
//            case   6: // Engine activated (security event)
//            case   8: // Towing
//            case  11: // Communication idle
//            case  15: // Crash detection
//            case  21: // Coasting detection (speed and RPM)
//            case  22: // Violation of 1st additional GP frequency threshold
//            case  23: // Violation of 2nd additional GP frequency threshold
//            case  25: // Speed detected during ignition off
//            case  31: // Reply to command
//            case  32: // IP changed/connection up
//            case  33: // GPS navigation start
//            case  34: // Over speed start
//            case  35: // Idle speed start
//            case  36: // Distance event
//            case  37: // Engine start; ignition input – active (high)
//            case  38: // GPS factory reset (automatic only)
//            case  41: // GPS navigation end
//            case  42: // Over speed end
//            case  43: // Idle speed end
//            case  44: // Timed event
//            case  47: // Driving without authentication
//            case  48: // Door close
//            case  49: // Shock/Unlock2 inactive
//            case  51: // Volume sensor inactive event
//            case  53: // Driving stop - Ignition on
//                cellocatorEvent.setEventCode(intTransReason);
//                break;
//            case  54: // Distress button inactive
//            case  69: // Driving start - Ignition off
//                cellocatorEvent.setEventCode(intTransReason);
//                break;
//            case 204: // Vector (course) change (curve smoothing event)
//                cellocatorEvent.setEventCode(intTransReason);
//            default:
//        }

        //this.insertEventRecord(cellocatorEvent);

        return cellocatorEvent;
    }
    
    public byte[] createPacket_ACK(boolean b) {
    //TODO: Build here ACK message to send back to unit.

        int checksum = 0;
        int i;
        Payload payload = new Payload();

        //System Code:
        payload.writeString(Constants.SYSTEM_CODE,4);
        //Message Type:
        payload.writeULong(4,1);
        checksum = checksum + 4;
        //UnitID:
        payload.writeBytes(this.byteUnitID, 4);
        for(i=0; i<this.byteUnitID.length; i++){
            checksum = checksum + (int)this.byteUnitID[i];
        }
        //Commander Numerator:
        payload.writeULong(0, 1); //TODO: verify ACK commander numerator
        checksum = checksum + 0;
        //Authentication Code Field
        payload.writeULong(0,4); //TODO: verify ACK Authentication Code Field
        checksum = checksum + 0;
        //Action Code:
        payload.writeULong(0,1); //TODO: verify ACK Action Code
        checksum = checksum + 0;
        //Message Numerator:
        payload.writeBytes(this.byteMessageNumerator, 1);
        for(i=0; i<this.byteMessageNumerator.length; i++) {
            checksum = checksum + (int) this.byteMessageNumerator[i];
        }
        //Unused bytes:
        payload.writeZeroFill(11);
        //Error Detection Code:
        payload.writeULong(checksum&0xFF,1);

        return payload.getBytes();
    }
    
    /**
    *
    * @param strDate "yyyy-MM-dd hh:mm:ss"
    * @return returns timestamp in long integer
    */
   private long convertStringTotimestamp(String strDate){
       try{
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
           Date parsedDate = dateFormat.parse(strDate);
           long unixTime = (long) parsedDate.getTime()/1000;
           return unixTime;
       }catch(Exception e){
           logger.error((String)"Cannot convert Unit's UTC time to timestamp.");
           return 0;
       }
   }

   public Event getEvent(byte[] arrby) {
	    CellocatorEvent myCellocatorEvent = getHandlePacket(arrby);
   	
		Event newEvent = new Event();
		newEvent.setDeviceID(myCellocatorEvent.getUniqueID(""));
	   	newEvent.setTimestamp(myCellocatorEvent.getTimestamp());
	   	newEvent.setEventCode(0);
	   	newEvent.setLatitude(myCellocatorEvent.getLatitude());
	   	newEvent.setLongitude(myCellocatorEvent.getLongitude());
	   	newEvent.setSpeedKPH(myCellocatorEvent.getSpeedKPH());
	   	newEvent.setOdometerKm(myCellocatorEvent.getOdometerKM());
	   	newEvent.setFuel(0);
	   	newEvent.setHeading(myCellocatorEvent.getHeading());
	   	newEvent.setHDOP(0);
	   	newEvent.setSatelliteCount(myCellocatorEvent.getSatelliteCount());
	   	newEvent.setCreationTime(Util.TimeEpoch());
	   	newEvent.setTypeProvider("CELLOCATOR");
	   	newEvent.setEventStatusID(myCellocatorEvent.getStatusCode());
			
	   	return newEvent;
   }
}
