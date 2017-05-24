package com.gs.opengts.opt.servers.cellocator;

import com.gs.opengts.opt.db.StatusCodes;
import com.gs.opengts.util.StringTools;

/**
 * @author Leonardo D. Gushiken Gibu
 * @version 0.0.1
 * @since 2017-03-24
 */
public class CellocatorEvent {

    private String unitID = null;
    private int statusCode = 0;
    private int eventCode = 0;
    private long updateTime = 0;
    private long fixTime = 0;
    private double longitude = 0.0;
    private double latitude = 0.0;
    private int fixStatus = 0;
    private int satelliteCount = 0;
//    private double hdop = 0.0;
    private double altitudeMeters = 0.0;
    private double speedKPH = 0.0;
    private double headingDegrees = 0.0;
//    private int carrier = 0;
//    private int rssi = 0;
//    private int commState = 0;
//    private long inputMask = -1;
//    private int unitStatus = 0;
//    private int eventIndex = 0;
//    private Vector<Long> accums = null;
//    private int userMessageType = 0;
//    private byte[] userMessage = null;
    private double odometerKM = 0.0;
    private int intUnitIO_1 = 0;
    private int intUnitIO_2 = 0;
    private int intUnitIO_3 = 0;
    private int intUnitIO_4 = 0;
    private double minSpeedKPH = 2.8; //dcserver_cellocator.xml

    public CellocatorEvent(String unitID) {
        this.unitID = unitID;
    }

    public String getUnitID() {
        return unitID;
    }

    public boolean hasUniqueID() {
        return !StringTools.isBlank((String)this.unitID);
    }

    public String getUniqueID(String prefix){
        if (StringTools.isBlank((String)this.unitID)) {
            return "";
        }

        return prefix + this.unitID;
    }

    public void setIntUnitIO_1(int intUnitIO_1) {
        this.intUnitIO_1 = intUnitIO_1;
    }

    public void setIntUnitIO_2(int intUnitIO_2) {
        this.intUnitIO_2 = intUnitIO_2;
    }

    public void setIntUnitIO_3(int intUnitIO_3) {
        this.intUnitIO_3 = intUnitIO_3;
    }

    public void setIntUnitIO_4(int intUnitIO_4) {
        this.intUnitIO_4 = intUnitIO_4;
    }

    public int getIntUnitIO_1() {
        return intUnitIO_1;
    }

    public int getIntUnitIO_2() {
        return intUnitIO_2;
    }

    public int getIntUnitIO_3() {
        return intUnitIO_3;
    }

    public int getIntUnitIO_4() {
        return intUnitIO_4;
    }

    public void setMinSpeedKPH(double minSpeedKPH) {
        this.minSpeedKPH = minSpeedKPH;
    }

    public double getMinSpeedKPH() {
        return minSpeedKPH;
    }

//    private static int _customUserCode(int n) {
//        DCServerConfig dCServerConfig = Main.getServerConfig(null);
//        if (dCServerConfig.getEventCodeEnabled()) {
//            return dCServerConfig.translateStatusCode(n, -999);
//        }
//        return -999;
//    }

    //TODO: Complete default eventcodes vs statuscodes _defaultUserCode
    private static int _defaultUserCode(int n) {
        if (n < 0) {
            return 61472;
        }
        /*if (StatusCodes.IsReserved((int)n)) {
            return n;
        }*/
        if (n > 255) {
            return 57599;
        }
        switch (n) {
            case   0: {
                return StatusCodes.STATUS_LOCATION;
            }
            case  11: { // Communication idle
                return StatusCodes.STATUS_LOCATION;
            }
            case  32: { // IP changed/connection up
                return StatusCodes.STATUS_LOCATION;
            }
            case  33: { // GPS navigation start
                return StatusCodes.STATUS_LOCATION;
            }
            case  34: { // Over speed start
                return StatusCodes.STATUS_MOTION_EXCESS_SPEED;
            }
            case  35: { // Idle speed start
                return StatusCodes.STATUS_LOCATION;
            }
            case  36: { // Distance event
                return StatusCodes.STATUS_LOCATION;
            }
            case  41: { // GPS navigation end
                return StatusCodes.STATUS_LOCATION;
            }
            case  42: { // Over speed end
                return StatusCodes.STATUS_LOCATION;
            }
            case  44: { // Timed event
                return StatusCodes.STATUS_LOCATION;
            }
            case  47: { // Driving without authentication
                return StatusCodes.STATUS_LOCATION;
            }
            case  49: { // Shock/Unlock2 inactive - Panic Button
                return StatusCodes.STATUS_LOCATION;
            }
            case  53: { // Driving stop - Ignition on
                return StatusCodes.STATUS_IGNITION_OFF;
            }
            case  65: { // Shock/Unlock2
                return StatusCodes.STATUS_PANIC_ON;
            }
            case  69: { // Driving start - Ignition off
                return StatusCodes.STATUS_IGNITION_ON;
            }
            case  80: { // Main power disconnected
                return StatusCodes.STATUS_POWER_FAILURE;
            }
            case  87: { // Main power connected (unconditionally logged upon an initial power up)
                return StatusCodes.STATUS_POWER_RESTORED;
            }
            case 204: { // Vector (course) change (curve smoothing event)
//                return StatusCodes.STATUS_MOTION_IN_MOTION;
                return StatusCodes.STATUS_LOCATION;
            }
            case 207: { // Radio off mode
                return StatusCodes.STATUS_LOCATION;
            }
        }
//        return 57344 | n & 255;
        return 49152 | n & 255; // 49152 = 0xC000. This identifies cellocator status codes.
    }

    public int getStatusCode() {
        int ignStatus;
        int accelerometerStatus = this.getIntUnitIO_2() & 64;;

        if (this.statusCode <= 0) {
            int n = -2; /*CellocatorEvent._customUserCode(this.eventCode);*/
            if (n == 0) { // According to motion status
//                this.statusCode = this.getSpeedKPH() > 0.0 ? 61714 : StatusCodes.STATUS_LOCATION;
//                this.statusCode = this.getSpeedKPH() > 0.0 ? StatusCodes.STATUS_MOTION_IN_MOTION : StatusCodes.STATUS_LOCATION;
//                this.statusCode = this.getSpeedKPH() > this.minSpeedKPH ? StatusCodes.STATUS_MOTION_IN_MOTION : StatusCodes.STATUS_LOCATION;
                if(this.getSpeedKPH() > this.minSpeedKPH && accelerometerStatus == 64) {
                    this.statusCode = StatusCodes.STATUS_MOTION_IN_MOTION;
                }else{
                    this.statusCode = StatusCodes.STATUS_LOCATION;
                }
            } else if (n > 0) {
                this.statusCode = n;
            } else if (n == -1) {
                this.statusCode = 0;
            } else {
                int n2 = CellocatorEvent._defaultUserCode(this.eventCode);
//                this.statusCode = TrackClientPacketHandler.XLATE_LOCATON_INMOTION &&
//                        n2 == StatusCodes.STATUS_LOCATION ? (this.getSpeedKPH() > 0.0 ? StatusCodes.STATUS_MOTION_IN_MOTION : StatusCodes.STATUS_LOCATION) : n2;
                if (/*TrackClientPacketHandler.XLATE_LOCATON_INMOTION &&*/ n2 == StatusCodes.STATUS_LOCATION){
                    ignStatus = this.getIntUnitIO_2() & 128;
//                    if(this.getSpeedKPH() > 0.0){
//                    if(this.getSpeedKPH() > this.minSpeedKPH){
                    if(this.getSpeedKPH() > this.minSpeedKPH && accelerometerStatus == 64) {
                        this.statusCode = StatusCodes.STATUS_MOTION_IN_MOTION;
                    }
//                    else if(this.getSpeedKPH() == 0.0) {
//                    else if(this.getSpeedKPH() <= this.minSpeedKPH) {
                    else if(this.getSpeedKPH() <= this.minSpeedKPH && accelerometerStatus == 0) {
                        if (ignStatus == 128) {
                            this.statusCode = StatusCodes.STATUS_MOTION_IDLE;
                        } else {
                            this.statusCode = StatusCodes.STATUS_MOTION_DORMANT;
                        }
                    }
                    else {
                        this.statusCode = StatusCodes.STATUS_LOCATION;
                    }
                }
                else {
                    this.statusCode = n2;
                }
            }
        }
        return this.statusCode;
    }


    public void setStatusCode(int n) {
        //TODO: validate reserved status codes.
//        if (!StatusCodes.IsReserved((int)n)) {
//            Print.logError((String)("Invalid StatusCode: 0x" + StringTools.toHexString((long)n, (int)16)), (Object[])new Object[0]);
//        }
        this.statusCode = n;
        this.eventCode = n;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
        if (this.fixTime <= 0) {
            this.fixTime = updateTime;
        }
    }

    public void setFixTime(long l) {
        this.fixTime = l;
        if (this.updateTime <= 0) {
            this.updateTime = l;
        }
    }

    public long getTimestamp() {
//        if (TrackClientPacketHandler.USE_UPDATE_TIMESTAMP) {
//            if (this.updateTime > 0) {
//                Print.logDebug((String)"Using Timestamp 'UpdateTime': %d (preferred)", (Object[])new Object[]{this.updateTime});
//                return this.updateTime;
//            }
//            Print.logDebug((String)"Using Timestamp 'FixTime': %d", (Object[])new Object[]{this.fixTime});
//            return this.fixTime;
//        }
//        if (this.fixTime > 0) {
//            Print.logDebug((String)"Using Timestamp 'FixTime': %d (preferred)", (Object[])new Object[]{this.fixTime});
//            return this.fixTime;
//        }
//        Print.logDebug((String)"Using Timestamp 'UpdateTime': %d", (Object[])new Object[]{this.updateTime});
//        return this.updateTime;
        return this.updateTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitudeMeters() {
        return altitudeMeters;
    }

    public void setAltitudeMeters(double altitudeMeters) {
        this.altitudeMeters = altitudeMeters;
    }

//    public boolean isValidGeoPoint() {
//        return GeoPoint.isValid((double)this.latitude, (double)this.longitude);
//    }
//
//    public GeoPoint getGeoPoint() {
//        return new GeoPoint(this.latitude, this.longitude);
//    }

//    public void setInputMask(long l) {
//        this.inputMask = l;
//    }

//    public long getInputMask() {
//        return this.inputMask;
//    }

//    public boolean hasInputMask() {
//        return this.inputMask >= 0;
//    }

    public void setSpeedKPH(double d) {
    this.speedKPH = d;
}

    public double getSpeedKPH() {
        return this.speedKPH;
    }

    public void setHeading(double d) {
        this.headingDegrees = d;
    }

    public double getHeading() {
        return this.getSpeedKPH() > 0.0 ? this.headingDegrees : 0.0;
    }

    public void setSatelliteCount(int n) {
        this.satelliteCount = n;
    }

    public int getSatelliteCount() {
        return this.satelliteCount;
    }


//    public void setFixStatus(int n) {
//        this.fixStatus = n;
//    }

//    public int getFixStatus() {
//        return this.fixStatus;
//    }

//    public void setCarrier(int n) {
//        this.carrier = n;
//    }

//    public int getCarrier() {
//        return this.carrier;
//    }

//    public boolean hasCarrier() {
//        return this.carrier > 0;
//    }

//    public void setRSSI(int n) {
//        this.rssi = n;
//    }

//    public int getRSSI() {
//        return this.rssi;
//    }

//    public void setCommState(int n) {
//        this.commState = n;
//    }

//    public void setHDOP(double d) {
//        this.hdop = d;
//    }

//    public double getHDOP() {
//        return this.hdop;
//    }

//    public void setUnitStatus(int n) {
//        this.unitStatus = n;
//    }

//    public void setEventIndex(int n) {
//        this.eventIndex = n;
//    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
        this.statusCode = -1;
    }

//    public void setMessage(int n, byte[] arrby) {
//        this.userMessageType = n;
//        this.userMessage = arrby;
//    }
//
//    public int getMessageType() {
//        return this.userMessageType;
//    }
//
//    public byte[] getMessage() {
//        return this.userMessage;
//    }
//
//    public boolean hasIdleTimeSec(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("idleTime");
//    }
//
//    public long getIdleTimeSec(AccumulatorGroup accumulatorGroup) {
//        long l = accumulatorGroup.getAverage("idleTime", this, 0);
//        return l;
//    }
//
//    public boolean hasWorkTimeSec(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("workTime");
//    }
//
//    public long getWorkTimeSec(AccumulatorGroup accumulatorGroup) {
//        long l = accumulatorGroup.getAverage("workTime", this, 0);
//        return l;
//    }
//
//    public boolean hasRunTimeSec(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("runTime");
//    }
//
//    public long getRunTimeSec(AccumulatorGroup accumulatorGroup) {
//        long l = accumulatorGroup.getAverage("runTime", this, 0);
//        return l;
//    }
//
//    public boolean hasFuelLevel(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("fuelLevel");
//    }
//
//    public double getFuelLevel(AccumulatorGroup accumulatorGroup) {
//        double d = accumulatorGroup.getAverage("fuelLevel", this, 0.0);
//        return d / 100.0;
//    }
//
//    public boolean hasBrakeGForce(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("brakeForce");
//    }
//
//    public double getBrakeGForce(AccumulatorGroup accumulatorGroup) {
//        double d = accumulatorGroup.getAverage("brakeForce", this, 0.0);
//        double d2 = d * 0.01;
//        double d3 = d2 * 0.10197162129779283;
//        return d3;
//    }
//
//    public boolean hasBatteryVolts(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("battery");
//    }
//
//    public double getBatteryVolts(AccumulatorGroup accumulatorGroup) {
//        double d = accumulatorGroup.getAverage("battery", this, 0.0);
//        return d / 1000.0;
//    }
//
//    public boolean hasDriverID(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("driverID");
//    }

//    public long getDriverID(AccumulatorGroup accumulatorGroup) {
//        int[] arrn0 = accumulatorGroup.getAccumulators("iButtonID0");
//        int[] arrn1 = accumulatorGroup.getAccumulators("iButtonID1");
//        if (ListTools.size((int[])arrn0) > 0) {
//            if(ListTools.size((int[])arrn1) > 0) {
//                return ( this.getAccumulator(arrn0[0]) +  ( this.getAccumulator(arrn1[0]) << 32 ) );
//            }
//            else {
//                return ( this.getAccumulator(arrn0[0]) );
//            }
//        }
//        return 0;
//    }

//    public boolean hasRfidTag(AccumulatorGroup accumulatorGroup) {
//        return accumulatorGroup.hasAccumulators("rfidTag");
//    }
//
//    public long getRfidTag(AccumulatorGroup accumulatorGroup) {
//        int[] arrn = accumulatorGroup.getAccumulators("rfidTag");
//        if (ListTools.size((int[])arrn) > 0) {
//            return this.getAccumulator(arrn[0]);
//        }
//        return 0;
//    }
//
//    public boolean hasTemperature(AccumulatorGroup accumulatorGroup, int n) {
//        switch (n) {
//            case 0: {
//                return accumulatorGroup.hasAccumulators("temp0");
//            }
//            case 1: {
//                return accumulatorGroup.hasAccumulators("temp1");
//            }
//            case 2: {
//                return accumulatorGroup.hasAccumulators("temp2");
//            }
//            case 3: {
//                return accumulatorGroup.hasAccumulators("temp3");
//            }
//            case 4: {
//                return accumulatorGroup.hasAccumulators("temp4");
//            }
//            case 5: {
//                return accumulatorGroup.hasAccumulators("temp5");
//            }
//            case 6: {
//                return accumulatorGroup.hasAccumulators("temp6");
//            }
//            case 7: {
//                return accumulatorGroup.hasAccumulators("temp7");
//            }
//        }
//        return false;
//    }
//
//    public double getTemperature(AccumulatorGroup accumulatorGroup, int n) {
//        String string = null;
//        switch (n) {
//            case 0: {
//                string = "temp0";
//                break;
//            }
//            case 1: {
//                string = "temp1";
//                break;
//            }
//            case 2: {
//                string = "temp2";
//                break;
//            }
//            case 3: {
//                string = "temp3";
//                break;
//            }
//            case 4: {
//                string = "temp4";
//                break;
//            }
//            case 5: {
//                string = "temp5";
//                break;
//            }
//            case 6: {
//                string = "temp6";
//                break;
//            }
//            case 7: {
//                string = "temp7";
//                break;
//            }
//            default: {
//                return -9999.0;
//            }
//        }
//        int[] arrn = accumulatorGroup.getAccumulators(string);
//        if (ListTools.isEmpty((int[])arrn)) {
//            return -9999.0;
//        }
//        int n2 = 0;
//        double d = 0.0;
//        for (int i = 0; i < arrn.length; ++i) {
//            if (arrn[i] < 0) continue;
//            long l = this.getAccumulator(arrn[i]);
//            int n3 = (int)l;
//            double d2 = (double)n3 * 0.0625;
//            d += d2;
//            ++n2;
//        }
//        return n2 > 0 ? d / (double)n2 : -9999.0;
//    }
//
//    public boolean hasOdometer(AccumulatorGroup accumulatorGroup) {
//        return this.odometerKM > 0.0 || accumulatorGroup.hasAccumulators("odometer");
//    }
//
//    private double _getOdometerKM() {
//        return this.odometerKM;
//    }
//
//    public double getOdometerKM(AccumulatorGroup accumulatorGroup) {
//        if (this.odometerKM > 0.0) {
//            return this.odometerKM;
//        }
//        double d = accumulatorGroup.getAverage("odometer", this, 0.0);
//        return d / 1000.0;
//    }
//
    public void setOdometerKM(double d) {
        this.odometerKM = d > 0.0 ? d : 0.0;
    }

    public double getOdometerKM() {
        return this.odometerKM;
    }
//
//    public String toString() {
//        StringBuffer stringBuffer = new StringBuffer();
//        int n = this.getStatusCode();
//        String string = StatusCodes.GetHex((int)n);
//        stringBuffer.append("EventType : ").append("" + this.eventType + ", code=" + this.eventCode + ", status=" + n + "[" + string + "]\n");
//        stringBuffer.append("Mobile    : ").append("id=" + this.mobileID + " [" + this.mobileIDType + "]\n");
//        stringBuffer.append("Time      : ").append("Update=" + this.updateTime + ", Fix=" + this.fixTime + "\n");
//        stringBuffer.append("GPS       : ").append("" + this.latitude + "/" + this.longitude + ", alt=" + this.altitudeMeters + " [" + this.fixStatus + "]\n");
//        stringBuffer.append("Speed     : ").append("" + this.speedKPH + " km/h, " + this.headingDegrees + "\n");
//        stringBuffer.append("Odometer* : ").append("" + this._getOdometerKM() * 0.621371192237334 + " miles (non-accum)\n");
//        stringBuffer.append("Carrier   : ").append("" + this.getCarrier() + "\n");
//        stringBuffer.append("HDOP      : ").append("" + this.getHDOP() + "\n");
//        stringBuffer.append("GPIO      : 0x").append(StringTools.toHexString((long)this.getInputMask(), (int)16) + "\n");
//        int n2 = this.getAccumulatorCount();
//        for (int i = 0; i < n2; ++i) {
//            long l = this.getAccumulator(i);
//            stringBuffer.append("Accum[" + i + "]  : ").append("" + l + "\n");
//        }
//        return stringBuffer.toString();
//    }

}

