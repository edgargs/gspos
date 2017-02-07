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
    //private double odometerKM = 0.0;
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


