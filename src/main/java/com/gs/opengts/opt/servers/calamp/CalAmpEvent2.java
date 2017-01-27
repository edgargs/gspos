package com.gs.opengts.opt.servers.calamp;

import java.util.Vector;

import lombok.Data;

@Data
public class CalAmpEvent2 {
        
    private int eventType = 0;
    private int mobileIDType = 0;
    private String mobileID = null;
    //Message Header
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
    private double odometerKM = 0.0;

    public void addAccumulator(long l) {
        if (this.accums == null) {
            this.accums = new Vector<>();
        }
        this.accums.add(new Long(l));
    }
    
    public static int _defaultUserCode(int n) {
        if (n < 0) {
            return 61472;
        }
//        if (StatusCodes.IsReserved((int)n)) {
//            return n;
//        }
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
    }
    
    /*public double getAccumOdometerKM() {
    	AccumulatorGroup accumulatorGroup = AccumulatorGroup.GetAccumulatorGroup("");
        if (this.odometerKM > 0.0) {
            return this.odometerKM;
        }
        double d = accumulatorGroup.getAverage("odometer", this, 0.0);
        return d / 1000.0;
    }*/
}


