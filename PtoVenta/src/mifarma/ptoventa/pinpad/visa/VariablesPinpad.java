package mifarma.ptoventa.pinpad.visa;

import java.util.HashMap;

public class VariablesPinpad {
    public final static String carpeta = "C:\\farmaventa\\pinpad\\visa";

    public final static int TIMEOUT = 500;
    public final static int CICLOS_MAX = 2 * (60 * 4); //Intentos*Segundos

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
    public final static String OP_MONEDA_NACIONAL = "0";
    public final static String OP_MONEDA_EXTRANJERA = "1";

    public final static String TIPO_MENSJ_PINPAD_SERV = "S";
    public final static String TIPO_MENSJ_PINPAD_IMPR = "I";
    public final static String TIPO_MENSJ_PINPAD_CAJA = "C";
    public final static String TIPO_MENSJ_HACIA_PINPAD = "P";

    public final static String IND_ULTIMO_MENSAJE = "1";
    public final static String IND_HAY_MAS_MENSAJE = "0";

    public final static String SEPARADOR = String.valueOf((char)28);
    public final static String INICIO_TRAMA = String.valueOf((char)2);
    public final static String FIN_TRAMA = String.valueOf((char)3);
    public final static String COD_MONEDA_NACIONAL = "0";
    public final static String COD_MONEDA_EXTRANJERA = "1";

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

    public final static String PREF_ENVIO_MONTO = "A";
    public final static String PREF_ENVIO_PROPINA = "B";
    public final static String PREF_ENVIO_TIPO_MONEDA = "C";
    public final static String PREF_ENVIO_COD_TIENDA = "D";
    public final static String PREF_ENVIO_COD_CAJA = "E";

    public final static String PREF_RETOR_COD_RESP = "A";
    public final static String PREF_RETOR_MENS_FIN_OPE = "B";
    public final static String PREF_RETOR_NOMBRE_CLIENTE = "C";
    public final static String PREF_RETOR_NUM_AUTORIZ = "D";
    public final static String PREF_RETOR_NUM_REFERENCIA = "E";
    public final static String PREF_RETOR_NUM_TARJETA = "F";
    public final static String PREF_RETOR_FECHA_EXP = "G";
    public final static String PREF_RETOR_FECHA_TRANSACC = "H";
    public final static String PREF_RETOR_HORA_TRANSACC = "I";
    public final static String PREF_RETOR_COD_OPERACION = "J";
    public final static String PREF_RETOR_CORTE_PAPEL = "K";
    public final static String PREF_RETOR_PROPINA = "L";
    public final static String PREF_RETOR_NUM_MOZO = "M";
    public final static String PREF_RETOR_EMPRESA = "N";
    public final static String PREF_RETOR_TERMINAL = "O";
    public final static String PREF_RETOR_CUOTAS = "P";
    public final static String PREF_RETOR_PAGO_DIFERIDO = "Q";
    public final static String PREF_RETOR_FLAG_PIDEPIN = "R";
    public final static String PREF_RETOR_ID_UNICO = "S";
    public final static String PREF_RETOR_MONTO_CUOTA = "T";

    public final static String RETOR_COD_OPERACION_VTA = "01";
    public final static String RETOR_COD_OPERACION_VTA_CASHBACK = "02";
    public final static String RETOR_COD_OPERACION_VTA_CUOTAS = "03";
    public final static String RETOR_COD_OPERACION_ANULACION = "04";
    public final static String RETOR_COD_OPERACION_PROPINA = "06";
    public final static String RETOR_COD_OPERACION_POS = "08";
    public final static String RETOR_COD_OPERACION_SIMULACION = "10";

    public static HashMap<String, Integer> longSubCamposEnvio = new HashMap<String, Integer>() {
        {
            put(PREF_ENVIO_MONTO, 12); //A
            put(PREF_ENVIO_PROPINA, 12); //B
            put(PREF_ENVIO_TIPO_MONEDA, 1); //C
            put(PREF_ENVIO_COD_TIENDA, 8); //D
            put(PREF_ENVIO_COD_CAJA, 7); //E
        }
    };

    public static HashMap<String, Integer> longSubCamposRetorno = new HashMap<String, Integer>() {
        {
            put(PREF_RETOR_COD_RESP, 2); //A
            put(PREF_RETOR_MENS_FIN_OPE, 100); //B
            put(PREF_RETOR_NOMBRE_CLIENTE, 1); //C
            put(PREF_RETOR_NUM_AUTORIZ, 6); //D
            put(PREF_RETOR_NUM_REFERENCIA, 4); //E
            put(PREF_RETOR_NUM_TARJETA, 19); //F
            put(PREF_RETOR_FECHA_EXP, 4); //G
            put(PREF_RETOR_FECHA_TRANSACC, 8); //H
            put(PREF_RETOR_HORA_TRANSACC, 5); //I
            put(PREF_RETOR_COD_OPERACION, 2); //J
            put(PREF_RETOR_CORTE_PAPEL, 1); //K
            put(PREF_RETOR_PROPINA, 12); //L
            put(PREF_RETOR_NUM_MOZO, 7); //M
            put(PREF_RETOR_EMPRESA, 2); //N
            put(PREF_RETOR_TERMINAL, 8); //O
            put(PREF_RETOR_CUOTAS, 2); //P
            put(PREF_RETOR_PAGO_DIFERIDO, 2); //Q
            put(PREF_RETOR_FLAG_PIDEPIN, 2); //R
            put(PREF_RETOR_ID_UNICO, 15); //S
            put(PREF_RETOR_MONTO_CUOTA, 12); //T
        }
    };

}
