package mifarma.ptoventa.pinpad.mastercard;

public class VariablesPinpadMC {
    public final static String libpathMC = "C:\\farmaventa\\pinpad\\mc";

    public final static String APLICACION = "POS";

    public final static String COD_RESP_OK = "00";
    public final static String COD_RESP_ERROR = "13";
    public final static String COD_RESP_BIN_INVAL = "14";
    public final static String COD_RESP_TERM_INVAL = "89";
    public final static String COD_RESP_HOST_NO_RESP = "91";
    public final static String COD_RESP_IMPOR_MAX_TRX_DESHAB = "06"; //?????
    public final static String COD_RESP_EXC_LIMIT_COMERC = "01";
    public final static String COD_RESP_COMERC_INVAL = "02";
    public final static String COD_RESP_EXC_LIMIT_ACTIV = "03";
    public final static String COD_RESP_EXC_IMPOR_MAX_TRX = "04";
    public final static String COD_RESP_LIMIT_PISO_SIN_TRX = "05";
    public final static String COD_RESP_PROP_YA_INGR = "01";
    public final static String COD_RESP_PUNTOS_INVAL = "02";
    public final static String COD_RESP_LOTE_NO_EXISTE = "03";
    public final static String COD_RESP_MONTO_PROP_INVAL = "04";
    public final static String COD_RESP_TRX_NO_DISPON = "07";
    public final static String COD_RESP_NO_EXIST_TRX_LOTE = "08";
    public final static String COD_RESP_TRX_ANUL = "09";
    public final static String COD_RESP_CAJA_INIC = "10";
    public final static String COD_RESP_LIMIT_ACTIV_DESHAB = "11";
    public final static String COD_RESP_NO_EXIST_TC = "12";
    public final static String COD_RESP_NO_PUEDE_PROC_TRX = "13";
    public final static String COD_RESP_APLIC_XML_NO_ENCON = "14";
    public final static String COD_RESP_TRX_NO_DEFIN = "15";
    public final static String COD_RESP_COMERC_NO_REGIS = "20";
    public final static String COD_RESP_COMERC_DESHAB = "21";
    public final static String COD_RESP_PERF_CARGA_NO_REG = "22";
    public final static String COD_RESP_PERF_CARGA_DESHAB = "23";
    public final static String COD_RESP_EFECT_INSUF_CAJA = "24";
    //public final static String COD_RESP_TERM_OK = "TRI";            //????
    public final static String COD_RESP_TERM_OK = "16"; //???
    public final static String COD_RESP_TRX_FUERA_RANGO_HORA = "RO";
    public final static String COD_RESP_TRX_YA_ANUL = "Z4";
    public final static String COD_RESP_TRX_REQUI_PIN = "A1";
    public final static String COD_RESP_COPIA_YA_IMPRE = "A2";

    //campo requerimiento: ECR_APLICACION
    public final static String ECR_APLI_POS = "POS";

    //campo requerimiento: ECR_TRANSACCION
    public final static String ECR_TRX_COMPRA = "01";
    public final static String ECR_TRX_MULTIPROD = "02";
    public final static String ECR_TRX_DISP_EFECT = "03";
    public final static String ECR_TRX_GASOLIN = "04";
    public final static String ECR_TRX_PROPINA = "05";
    public final static String ECR_TRX_ANUL_COMPRA = "06";
    public final static String ECR_TRX_ANUL_PAGO = "07";
    public final static String ECR_TRX_ANUL_CANJE_PTOS = "08";
    public final static String ECR_TRX_REPORT_DET = "09";
    public final static String ECR_TRX_REPORT_TOT = "10";
    public final static String ECR_TRX_REIMPRESION = "11";
    public final static String ECR_TRX_CIERRE = "12";
    public final static String ECR_TRX_PAGO_SERV = "13";
    public final static String ECR_TRX_CONS_PAGO_SERV = "14";
    public final static String ECR_TRX_CONS_PTOS = "15";
    public final static String ECR_TRX_CANJE_PTOS = "16";
    public final static String ECR_TRX_PREAUTORIZ = "18";
    public final static String ECR_TRX_REPORT_DET_CIERRE = "19";
    public final static String ECR_TRX_REPORT_TOT_CIERRE = "20";
    public final static String ECR_TRX_CONF_PREAUTORIZ = "24";
    public final static String ECR_TRX_ING_PROPINA = "25";
    public final static String ECR_TRX_REPORT_MOZO = "26";
    public final static String ECR_TRX_REIMPR_LOTE = "28";

    //campo requerimiento: ECR_CURRENCY_CODE
    public final static String ECR_CURR_CODE_SOLES = "604";
    public final static String ECR_CURR_CODE_DOLAR = "840";

    //campo requerimiento: ECR_COD_SERVICIO
    public final static String ECR_COD_SERV_PAGO_SOLES = "6001";
    public final static String ECR_COD_SERV_CONS_TARJ = "6066";
    public final static String ECR_COD_SERV_CONS_MOV = "6067";

    //campo requerimiento: ECR_DATA_ADICIONAL3
    public final static String ECR_DAT_ADIC3_SIN_ENV_BIN = "0";
    public final static String ECR_DAT_ADIC3_CON_ENV_BIN = "1";

    //campo respuesta: CURRENCY_CODE
    public final static String CURR_CODE_SOLES = "604";
    public final static String CURR_CODE_DOLAR = "840";

    //campo respuesta: CREDIT_TYPE
    public final static String CRED_TYPE_NO_DIFER = "00";
    public final static String CRED_TYPE_DIFER_2_MES = "20";
    public final static String CRED_TYPE_DIFER_3_MES = "30";
}
