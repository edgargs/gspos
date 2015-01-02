package com.mifarma.jni;

/**
 */
public final class PinPadJava {


    public final static String OP_VENTA = "01";
    public final static String OP_VENTA_CUOTAS = "03";
    public final static String OP_ANULACION = "04";
    public final static String OP_CIERRE = "07";
    public final static String OP_SERVICIOS = "08";
    public final static String OP_INICIALIZA_TERMINAL = "09";
    public final static String OP_SIMULACION_CUOTAS = "10";
    public final static String OP_TEST_COMUNICACIONES = "11";
    public final static String OP_DUPLICADO = "12";
    public final static String OP_DETALLE_OPERACIONES = "13";
    public final static String OP_FINANCIERA = "90";
    public final static String OP_NO_FINANCIERA = "91";
    public final static int RET_RUNNING = 1;
    public final static int RET_OK = 0;
    public final static int RET_NOK = -1;
    public final static int ERR_COM = -2;
    public final static int ERR_PPAD_NO_RESP = -3;
    public final static int ERR_TYPE_OPER = -4;
    public final static int ERR_SOCKET = -5;
    public final static int ERR_HOST_NO_RESP = -6;
    public final static int ERR_PRINTER = -7;
    public final static int ERR_NAK_RECEIVED = -8;
    public final static int ERR_LRC_PPAD = -9;

    public native int fiLoadLibrary(String path);

    public native int fiOpenPort(String ConfigFileName);

    public native int fiClosePort();

    public native int fiStartOperation(String pucTipoOperation, int iTimeOut, StringBuffer pucResponse);

    public native int fiGetStatus(StringBuffer sBuffer, int SizeBuffer);

}
