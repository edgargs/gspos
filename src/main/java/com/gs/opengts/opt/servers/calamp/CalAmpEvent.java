package com.gs.opengts.opt.servers.calamp;

import java.util.List;
import java.util.Vector;

import com.gs.opengts.util.GeoPoint;
import com.gs.opengts.util.StringTools;

public class CalAmpEvent
/*implements CalAmpConstants*/ {
    public static final int EVENTCODE_LOCATION_00 = 0;
    public static final int EVENTCODE_INITIALIZED = 1;
    public static final int EVENTCODE_CONFIG_RESET = 2;
    public static final int EVENTCODE_QUERY = 10;
    public static final int EVENTCODE_MOTION_START = 17;
    public static final int EVENTCODE_MOTION_IN_MOTION = 18;
    public static final int EVENTCODE_MOTION_STOP = 19;
    public static final int EVENTCODE_MOTION_DORMANT = 20;
    public static final int EVENTCODE_MOTION_IDLE = 21;
    public static final int EVENTCODE_MOTION_EXCESS_SPEED = 22;
    public static final int EVENTCODE_MOTION_EXCESS_IDLE = 23;
    public static final int EVENTCODE_MOTION_EXCESS_BRAKING = 24;
    public static final int EVENTCODE_MOTION_EXCESS_CORNER = 25;
    public static final int EVENTCODE_MOTION_ACCELERATION = 27;
    public static final int EVENTCODE_MOTION_HEADING_CHANGE = 28;
    public static final int EVENTCODE_GEOFENCE_ARRIVE = 33;
    public static final int EVENTCODE_GEOFENCE_DEPART = 34;
    public static final int EVENTCODE_INPUT_ON_00 = 48;
    public static final int EVENTCODE_INPUT_ON_01 = 49;
    public static final int EVENTCODE_INPUT_ON_02 = 50;
    public static final int EVENTCODE_INPUT_ON_03 = 51;
    public static final int EVENTCODE_INPUT_ON_04 = 52;
    public static final int EVENTCODE_INPUT_ON_05 = 53;
    public static final int EVENTCODE_INPUT_ON_06 = 54;
    public static final int EVENTCODE_INPUT_ON_07 = 55;
    public static final int EVENTCODE_INPUT_ON_08 = 56;
    public static final int EVENTCODE_IGNITION_ON = 63;
    public static final int EVENTCODE_INPUT_OFF_00 = 64;
    public static final int EVENTCODE_INPUT_OFF_01 = 65;
    public static final int EVENTCODE_INPUT_OFF_02 = 66;
    public static final int EVENTCODE_INPUT_OFF_03 = 67;
    public static final int EVENTCODE_INPUT_OFF_04 = 68;
    public static final int EVENTCODE_INPUT_OFF_05 = 69;
    public static final int EVENTCODE_INPUT_OFF_06 = 70;
    public static final int EVENTCODE_INPUT_OFF_07 = 71;
    public static final int EVENTCODE_INPUT_OFF_08 = 72;
    public static final int EVENTCODE_IGNITION_OFF = 79;
    public static final int EVENTCODE_ELAPSED_TIMER_00 = 80;
    public static final int EVENTCODE_ELAPSED_TIMER_01 = 81;
    public static final int EVENTCODE_ELAPSED_TIMER_02 = 82;
    public static final int EVENTCODE_ELAPSED_TIMER_03 = 83;
    public static final int EVENTCODE_ELAPSED_TIMER_04 = 84;
    public static final int EVENTCODE_ELAPSED_TIMER_05 = 85;
    public static final int EVENTCODE_ELAPSED_TIMER_06 = 86;
    public static final int EVENTCODE_ELAPSED_TIMER_07 = 87;
    public static final int EVENTCODE_SUSPEND = 96;
    public static final int EVENTCODE_RESUME = 97;
    public static final int EVENTCODE_BATTERY_LEVEL = 102;
    public static final int EVENTCODE_LOCATION_FF = 255;
    public static final int[] InputStatusCodes_ON = new int[]{62496, 62497, 62498, 62499, 62500, 62501, 62502, 62503, 62504};
    public static final int[] InputStatusCodes_OFF = new int[]{62528, 62529, 62530, 62531, 62532, 62533, 62534, 62535, 62536};
    public static final int TYPE_EVENT = 0;
    public static final int TYPE_USER_DATA = 1;
    public static final int TYPE_APP_DATA = 2;
    public static final int TYPE_LOCATE = 3;
    public static final int TYPE_USER_ACCUM = 4;
    public static final int TYPE_MINI_EVENT = 5;
    public static final int TYPE_ACK = 6;
    
    private int eventType = 0;
    private int mobileIDType = 0;
    private String mobileID = null;
    private long updateTime = 0;
    private long fixTime = 0;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private int fixStatus = 0;
    private int satelliteCount = 0;
    private double hdop = 0.0;
    private double altitudeMeters = 0.0;
    private double speedKPH = 0.0;
    private double headingDegrees = 0.0;
    private int carrier = 0;
    private int rssi = 0;
    private int commState = 0;
    private long inputMask = -1;
    private int unitStatus = 0;
    private int eventIndex = 0;
    private int eventCode = 0;
    private int statusCode = 0;
    private Vector<Long> accums = null;
    private int userMessageType = 0;
    private byte[] userMessage = null;
    private double odometerKM = 0.0;

    public CalAmpEvent(int n, int n2, String string) {
        this.eventType = n;
        this.mobileIDType = n2;
        this.mobileID = StringTools.trim((String)string);
    }

    public int getEventType() {
        return this.eventType;
    }

    public int getMobileIDType() {
        return this.mobileIDType;
    }

    public String getMobileID() {
        return this.mobileID;
    }

    public boolean hasUniqueID() {
        return !StringTools.isBlank((String)this.mobileID);
    }

    public String getUniqueID(String string) {
        if (StringTools.isBlank((String)this.mobileID)) {
            return "";
        }
        String string2 = this.mobileID;
        switch (this.mobileIDType) {
            case 0: {
                return string + string2;
            }
            case 1: {
                return string + string2;
            }
            case 2: {
                return string + string2;
            }
            case 3: {
                return string + string2;
            }
            case 4: {
                return string + string2;
            }
            case 5: {
                return string + string2;
            }
            case 6: {
                return string + string2;
            }
        }
        return string + string2;
    }

    /*private static int _customUserCode(int n) {
        DCServerConfig dCServerConfig = Main.getServerConfig(null);
        if (dCServerConfig.getEventCodeEnabled()) {
            return dCServerConfig.translateStatusCode(n, -999);
        }
        return -999;
    }*/

    /*private static int _defaultUserCode(int n) {
        if (n < 0) {
            return 61472;
        }
        if (StatusCodes.IsReserved((int)n)) {
            return n;
        }
        if (n > 255) {
            return 57599;
        }
        switch (n) {
            case 0: {
                return 61472;
            }
            case 1: {
                return 61456;
            }
            case 2: {
                return 64833;
            }
            case 10: {
                return 61504;
            }
            case 17: {
                return 61713;
            }
            case 18: {
                return 61714;
            }
            case 19: {
                return 61715;
            }
            case 20: {
                return 61716;
            }
            case 21: {
                return 61718;
            }
            case 22: {
                return 61722;
            }
            case 23: {
                return 61720;
            }
            case 24: {
                return 63792;
            }
            case 25: {
                return 63799;
            }
            case 27: {
                return 61731;
            }
            case 28: {
                return 61727;
            }
            case 33: {
                return 61968;
            }
            case 34: {
                return 62000;
            }
            case 48: {
                return 62496;
            }
            case 49: {
                return 62497;
            }
            case 50: {
                return 62498;
            }
            case 51: {
                return 62499;
            }
            case 52: {
                return 62500;
            }
            case 53: {
                return 62501;
            }
            case 54: {
                return 62502;
            }
            case 55: {
                return 62503;
            }
            case 56: {
                return 62504;
            }
            case 63: {
                return 62465;
            }
            case 64: {
                return 62528;
            }
            case 65: {
                return 62529;
            }
            case 66: {
                return 62530;
            }
            case 67: {
                return 62531;
            }
            case 68: {
                return 62532;
            }
            case 69: {
                return 62533;
            }
            case 70: {
                return 62534;
            }
            case 71: {
                return 62535;
            }
            case 72: {
                return 62536;
            }
            case 79: {
                return 62467;
            }
            case 80: {
                return 62640;
            }
            case 81: {
                return 62641;
            }
            case 82: {
                return 62642;
            }
            case 83: {
                return 62643;
            }
            case 84: {
                return 62644;
            }
            case 85: {
                return 62645;
            }
            case 86: {
                return 62646;
            }
            case 87: {
                return 62647;
            }
            case 96: {
                return 64840;
            }
            case 97: {
                return 64842;
            }
            case 102: {
                return 64785;
            }
            case 255: {
                return 61472;
            }
        }
        return 57344 | n & 255;
    }*/

    public int getStatusCode() {
        /*if (this.statusCode <= 0) {
            int n = CalAmpEvent._customUserCode(this.eventCode);
            if (n == 0) {
                this.statusCode = this.getSpeedKPH() > 0.0 ? 61714 : 61472;
            } else if (n > 0) {
                this.statusCode = n;
            } else if (n == -1) {
                this.statusCode = 0;
            } else {
                int n2 = CalAmpEvent._defaultUserCode(this.eventCode);
                this.statusCode = TrackClientPacketHandler.XLATE_LOCATON_INMOTION && n2 == 61472 ? (this.getSpeedKPH() > 0.0 ? 61714 : 61472) : n2;
            }
        }*/
        return this.statusCode;
    }

    public void setStatusCode(int n) {
        /*if (!StatusCodes.IsReserved((int)n)) {
            Print.logError((String)("Invalid StatusCode: 0x" + StringTools.toHexString((long)n, (int)16)), (Object[])new Object[0]);
        }*/
        this.statusCode = n;
        this.eventCode = n;
    }

    public void setUpdateTime(long l) {
        this.updateTime = l;
        if (this.fixTime <= 0) {
            this.fixTime = l;
        }
    }

    public void setFixTime(long l) {
        this.fixTime = l;
        if (this.updateTime <= 0) {
            this.updateTime = l;
        }
    }

    public long getTimestamp() {
        /*if (TrackClientPacketHandler.USE_UPDATE_TIMESTAMP) {
            if (this.updateTime > 0) {
                Print.logDebug((String)"Using Timestamp 'UpdateTime': %d (preferred)", (Object[])new Object[]{this.updateTime});
                return this.updateTime;
            }
            Print.logDebug((String)"Using Timestamp 'FixTime': %d", (Object[])new Object[]{this.fixTime});
            return this.fixTime;
        }
        if (this.fixTime > 0) {
            Print.logDebug((String)"Using Timestamp 'FixTime': %d (preferred)", (Object[])new Object[]{this.fixTime});
            return this.fixTime;
        }
        Print.logDebug((String)"Using Timestamp 'UpdateTime': %d", (Object[])new Object[]{this.updateTime});*/
        return this.updateTime;
    }

    public void setLatitude(double d) {
        this.latitude = d;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double d) {
        this.longitude = d;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public boolean isValidGeoPoint() {
        return GeoPoint.isValid((double)this.latitude, (double)this.longitude);
    }

    public GeoPoint getGeoPoint() {
        return new GeoPoint(this.latitude, this.longitude);
    }

    public void setInputMask(long l) {
        this.inputMask = l;
    }

    public long getInputMask() {
        return this.inputMask;
    }

    public boolean hasInputMask() {
        return this.inputMask >= 0;
    }

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

    public void setAltitudeMeters(double d) {
        this.altitudeMeters = d;
    }

    public double getAltitudeMeters() {
        return this.altitudeMeters;
    }

    public void setSatelliteCount(int n) {
        this.satelliteCount = n;
    }

    public int getSatelliteCount() {
        return this.satelliteCount;
    }

    public void setFixStatus(int n) {
        this.fixStatus = n;
    }

    public int getFixStatus() {
        return this.fixStatus;
    }

    public void setCarrier(int n) {
        this.carrier = n;
    }

    public int getCarrier() {
        return this.carrier;
    }

    public boolean hasCarrier() {
        return this.carrier > 0;
    }

    public void setRSSI(int n) {
        this.rssi = n;
    }

    public int getRSSI() {
        return this.rssi;
    }

    public void setCommState(int n) {
        this.commState = n;
    }

    public void setHDOP(double d) {
        this.hdop = d;
    }

    public double getHDOP() {
        return this.hdop;
    }

    public void setUnitStatus(int n) {
        this.unitStatus = n;
    }

    public void setEventIndex(int n) {
        this.eventIndex = n;
    }

    public void setEventCode(int n) {
        this.eventCode = n;
        this.statusCode = -1;
    }

    public void addAccumulator(long l) {
        if (this.accums == null) {
            this.accums = new Vector<>();
        }
        this.accums.add(new Long(l));
    }

    public List<Long> getAccumulators() {
        return this.accums;
    }

    public int getAccumulatorCount() {
        return this.accums != null ? this.accums.size() : 0;
    }

    public long getAccumulator(int n) {
        if ((this.accums == null) || this.accums.isEmpty()) {
            return 0;
        }
        if (n < 0 || n >= this.accums.size()) {
            return 0;
        }
        return this.accums.get(n);
    }

    public void setMessage(int n, byte[] arrby) {
        this.userMessageType = n;
        this.userMessage = arrby;
    }

    public int getMessageType() {
        return this.userMessageType;
    }

    public byte[] getMessage() {
        return this.userMessage;
    }

    /*public boolean hasIdleTimeSec(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("idleTime");
    }

    public long getIdleTimeSec(AccumulatorGroup accumulatorGroup) {
        long l = accumulatorGroup.getAverage("idleTime", this, 0);
        return l;
    }

    public boolean hasWorkTimeSec(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("workTime");
    }

    public long getWorkTimeSec(AccumulatorGroup accumulatorGroup) {
        long l = accumulatorGroup.getAverage("workTime", this, 0);
        return l;
    }

    public boolean hasRunTimeSec(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("runTime");
    }

    public long getRunTimeSec(AccumulatorGroup accumulatorGroup) {
        long l = accumulatorGroup.getAverage("runTime", this, 0);
        return l;
    }

    public boolean hasFuelLevel(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("fuelLevel");
    }

    public double getFuelLevel(AccumulatorGroup accumulatorGroup) {
        double d = accumulatorGroup.getAverage("fuelLevel", this, 0.0);
        return d / 100.0;
    }

    public boolean hasBrakeGForce(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("brakeForce");
    }

    public double getBrakeGForce(AccumulatorGroup accumulatorGroup) {
        double d = accumulatorGroup.getAverage("brakeForce", this, 0.0);
        double d2 = d * 0.01;
        double d3 = d2 * 0.10197162129779283;
        return d3;
    }

    public boolean hasBatteryVolts(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("battery");
    }

    public double getBatteryVolts(AccumulatorGroup accumulatorGroup) {
        double d = accumulatorGroup.getAverage("battery", this, 0.0);
        return d / 1000.0;
    }

    public boolean hasDriverID(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("driverID");
    }

    public long getDriverID(AccumulatorGroup accumulatorGroup) {
        int[] arrn = accumulatorGroup.getAccumulators("driverID");
        if (ListTools.size((int[])arrn) > 0) {
            return this.getAccumulator(arrn[0]);
        }
        return 0;
    }

    public boolean hasRfidTag(AccumulatorGroup accumulatorGroup) {
        return accumulatorGroup.hasAccumulators("rfidTag");
    }

    public long getRfidTag(AccumulatorGroup accumulatorGroup) {
        int[] arrn = accumulatorGroup.getAccumulators("rfidTag");
        if (ListTools.size((int[])arrn) > 0) {
            return this.getAccumulator(arrn[0]);
        }
        return 0;
    }

    public boolean hasTemperature(AccumulatorGroup accumulatorGroup, int n) {
        switch (n) {
            case 0: {
                return accumulatorGroup.hasAccumulators("temp0");
            }
            case 1: {
                return accumulatorGroup.hasAccumulators("temp1");
            }
            case 2: {
                return accumulatorGroup.hasAccumulators("temp2");
            }
            case 3: {
                return accumulatorGroup.hasAccumulators("temp3");
            }
            case 4: {
                return accumulatorGroup.hasAccumulators("temp4");
            }
            case 5: {
                return accumulatorGroup.hasAccumulators("temp5");
            }
            case 6: {
                return accumulatorGroup.hasAccumulators("temp6");
            }
            case 7: {
                return accumulatorGroup.hasAccumulators("temp7");
            }
        }
        return false;
    }

    public double getTemperature(AccumulatorGroup accumulatorGroup, int n) {
        String string = null;
        switch (n) {
            case 0: {
                string = "temp0";
                break;
            }
            case 1: {
                string = "temp1";
                break;
            }
            case 2: {
                string = "temp2";
                break;
            }
            case 3: {
                string = "temp3";
                break;
            }
            case 4: {
                string = "temp4";
                break;
            }
            case 5: {
                string = "temp5";
                break;
            }
            case 6: {
                string = "temp6";
                break;
            }
            case 7: {
                string = "temp7";
                break;
            }
            default: {
                return -9999.0;
            }
        }
        int[] arrn = accumulatorGroup.getAccumulators(string);
        if (ListTools.isEmpty((int[])arrn)) {
            return -9999.0;
        }
        int n2 = 0;
        double d = 0.0;
        for (int i = 0; i < arrn.length; ++i) {
            if (arrn[i] < 0) continue;
            long l = this.getAccumulator(arrn[i]);
            int n3 = (int)l;
            double d2 = (double)n3 * 0.0625;
            d += d2;
            ++n2;
        }
        return n2 > 0 ? d / (double)n2 : -9999.0;
    }

    public boolean hasOdometer(AccumulatorGroup accumulatorGroup) {
        return this.odometerKM > 0.0 || accumulatorGroup.hasAccumulators("odometer");
    }*/

    private double _getOdometerKM() {
        return this.odometerKM;
    }

    /*public double getOdometerKM(AccumulatorGroup accumulatorGroup) {
        if (this.odometerKM > 0.0) {
            return this.odometerKM;
        }
        double d = accumulatorGroup.getAverage("odometer", this, 0.0);
        return d / 1000.0;
    }*/

    public void setOdometerKM(double d) {
        this.odometerKM = d > 0.0 ? d : 0.0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int n = this.getStatusCode();
        String string = ""+n; //StatusCodes.GetHex((int)n);
        stringBuffer.append("EventType : ").append("" + this.eventType + ", code=" + this.eventCode + ", status=" + n + "[" + string + "]\n");
        stringBuffer.append("Mobile    : ").append("id=" + this.mobileID + " [" + this.mobileIDType + "]\n");
        stringBuffer.append("Time      : ").append("Update=" + this.updateTime + ", Fix=" + this.fixTime + "\n");
        stringBuffer.append("GPS       : ").append("" + this.latitude + "/" + this.longitude + ", alt=" + this.altitudeMeters + " [" + this.fixStatus + "]\n");
        stringBuffer.append("Speed     : ").append("" + this.speedKPH + " km/h, " + this.headingDegrees + "\n");
        stringBuffer.append("Odometer* : ").append("" + this._getOdometerKM() * 0.621371192237334 + " miles (non-accum)\n");
        stringBuffer.append("Carrier   : ").append("" + this.getCarrier() + "\n");
        stringBuffer.append("HDOP      : ").append("" + this.getHDOP() + "\n");
        stringBuffer.append("GPIO      : 0x").append(StringTools.toHexString((long)this.getInputMask(), (int)16) + "\n");
        int n2 = this.getAccumulatorCount();
        for (int i = 0; i < n2; ++i) {
            long l = this.getAccumulator(i);
            stringBuffer.append("Accum[" + i + "]  : ").append("" + l + "\n");
        }
        return stringBuffer.toString();
    }
}


