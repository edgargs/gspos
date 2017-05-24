package com.gs.opengts.opt.servers.cellocator;

/**
 * @author Leonardo D. Gushiken Gibu
 * @version 0.0.1
 * @since 2017-03-21
 */


public class Constants {
    public static final long MIN_XPIRE = 0;
    public static final long XPIRE_ = 0;
    public static final long PROBATION_XPIRE = 0;
    public static final long SERVICE_LIMIT = 0;
    public static final boolean SERVICE_KEY = false;
    public static final String TITLE_NAME = "Cellocator";
    public static final String VERSION = "0.0.1";
    public static final String COPYRIGHT = "Copyright HACOM S.A.C.";
    private static String _DEVICE_CODE = null;


//    public static final boolean ASCII_PACKETS = false;
//    public static final int[] ASCII_LINE_TERMINATOR = new int[]{13, 10};
//    public static final int[] ASCII_IGNORE_CHARS = null;
    //public static final int MIN_PACKET_LENGTH = 70*8;
    //public static final int MAX_PACKET_LENGTH = 70*8;
    public static final int MESSAGE_LENGTH_BYTES = 70;
    public static final int MESSAGE_LENGTH = MESSAGE_LENGTH_BYTES * 8; //10*8; //TEST - REAL VALUE: 70*8
    public static final int MIN_PACKET_LENGTH = MESSAGE_LENGTH;
    public static final int MAX_PACKET_LENGTH = MESSAGE_LENGTH;
    public static final int CHECKSUM_BYTE_ORDER = 70;
    public static final int CHECKSUM_INDEX = CHECKSUM_BYTE_ORDER - 1;
    public static final int MESSAGE_NUMERATOR_BYTE_ORDER = 12;
    public static final int MESSAGE_NUMERATOR_INDEX = MESSAGE_NUMERATOR_BYTE_ORDER - 1;
    public static final boolean TERMINATE_ON_TIMEOUT = true;
    public static final long TIMEOUT_TCP_IDLE = 10000;
    public static final long TIMEOUT_TCP_PACKET = 4000;
    public static final long TIMEOUT_TCP_SESSION = 15000;
    public static final long TIMEOUT_UDP_IDLE = 5000;
    public static final long TIMEOUT_UDP_PACKET = 4000;
    public static final long TIMEOUT_UDP_SESSION = 60000;
    public static final int LINGER_ON_CLOSE_SEC = 5;
    public static final double MINIMUM_SPEED_KPH = 3.0;
    public static final String SYSTEM_CODE = "MCGP";
    public static final int SYSTEM_CODE_LENGTH = 4;
    public static final int MESSAGE_TYPE_LENGTH = 1;
    public static final int UNIT_ID_LENGTH = 1;
    public static final int COMM_CONTROL_FIELD_LENGTH = 2;


    //TODO: Add special constants.

    public static String DEVICE_CODE() {
//        if (_DEVICE_CODE == null) {
//            String string = RTConfig.getString((String)"cellocator.name", (String)"cellocator").toLowerCase();
//            _DEVICE_CODE = string.startsWith("cellocator") ? string : "cellocator";
//        }
        return _DEVICE_CODE;
    }

    public static void main(String[] arrstring) {
        //Print.sysPrintln((String)"1.0.0", (Object[])new Object[0]);
    }
}

