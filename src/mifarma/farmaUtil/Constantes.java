package mifarma.farmaUtil;

public class Constantes {

    /*solo pruebas*/
    public static final Integer TRACE = 111334;

    /*********************TIPOS DE MENSAJES************************/
    public static final int MSG_200                     =   200;
    public static final int MSG_400                     =   400;
    public static final int MSG_420                     =   420;
    public static final int MSG_800                     =   800;
    public static final int MSG_BYTE_200                =   0X200;
    public static final int MSG_BYTE_400                =   0X400;
    public static final int MSG_BYTE_420                =   0X420;
    public static final int MSG_BYTE_800                =   0X800;

    /**************TIPOS DE MSJ DE SOLICITUD**************/
    public static final String COD_TIPO_SOLICITUD_200 = "200";
    public static final String COD_TIPO_ANULACION_400 = "400";
    public static final String COD_TIPO_EXTORNO_420 = "420";
    
    /*******************TRANSACCIONES SOPORTADAS*******************/
    public static final String TRNS_CMPRA_VNTA          = "00";
    public static final String TRNS_ANU_VNTA            = "02";
    public static final String TRNS_PAG_TARJ            = "17";    
    public static final String TRNS_ANU_PAG             = "47";
    public static final String TRNS_DISP_EFECT          = "01";
    public static final String TRNS_CNSULTA_SRV         = "35";// consulta SIX
    public static final String TRNS_PAG_PRE_AUTORI_SRV  = "94";// pago de servicio CLARO
    public static final String TRNS_PAG_X_AUTORI_SRV    = "95";
    public static final String TRNS_ANU_PAG_SRV         = "96";// Anulacion de pago de servicio CLARO
    public static final String TRNS_RECARGA             = "97";// Recarga
    public static final String TRNS_ANU_RECARGA         = "98";// Anulacion Recarga
    public static final String TRNS_ANU_TRANSF_SALDO    = "19";
    public static final String TRNS_CONSULTA_PVV        = "34";
    public static final String TRNS_EXTORNO_CMR         = "42";

    /******************* CUENTAS***********************/
    public static final String CTA_UNIVERSAL            =   "00";
    public static final String CTA_AHORROS              =   "10";
    public static final String CTA_CORRIENTE            =   "20";
    public static final String CTA_TARJ_CREDITO         =   "30";
    public static final String CTA_SERVICIOS            =   "50";

    /**************TIPOS DE RECAUDACIONES**************/
    public static final String TIPO_RECAUD_CMR         = "01";
    public static final String TIPO_RECAUD_CLARO       = "03";
    public static final String TIPO_RECAUD_MOVISTAR    = "05";
    public static final String TIPO_VENTA_CMR          = "06";
    public static final String TIPO_RECAUD_RIPLEY      = "07";
    public static final String TIPO_VENTA_RIPLEY       = "08";
    
    public static final String TIPO_RECAUD_FONOYA      = "09"; //ASOSA - 14/07/2014
    public static final String TIPO_RECAUD_PLJOVEN     = "10"; //ASOSA - 04/09/2014 - RECAR - PLJOVEN
    
    public static final String TIPO_RECAUD_NEXTEL      = "11"; //ASOSA - 28/10/2014 - RECAR - NEX
    public static final String TIPO_RECAR_BITEL        = "12";

    /****************DATOS GENERALES*******************/
    public static final String ID_CONEXION                      =   "AAAAAAAAAAAAA";
    public static final String FILE_CONFIG                      =   "mifarma/farmaConf/config.xml";
    public static final String FORMATO_DDMMYYYY_HHMMSS          =   "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATO_YYYYMMDD_HHMMSS          =   "yyyyMMdd HHmmss";
    public static final String FORMATO_MMDD                     =   "MMdd";
    public static final String FORMATO_YYMM                     =   "YYMM";
    public static final String FORMATO_DDMMYY                   =   "ddMMyy";
    public static final String FORMATO_YYYYMMDD                 =   "yyyyMMdd";
    public static final String FORMATO_HHMMSS                   =   "HHmmss";
    public static final int    ZERO_INTEGER                     =   0;
    public static final int    ONE_INTEGER                      =   1;
    public static final String EMPTY                            =   "";
    public static final String COD_ID_ADQUIRIENTE               =   "500010";
    public static final String COD_TIPO_NEGOCIO                 =   "5960";
    public static final String COD_TIPO_PTO_SERVICIO            =   "14";
    public static final String MODO_ING_TARJ_CLARO              =   "000";
    public static final String MODO_ING_TARJ_NEXTEL              =   "000";  //ASOSA - 28/10/2014 - RECAR - NEX
    public static final String MODO_ING_TARJ_MANUAL             =   "010";
    public static final String MODO_ING_TARJ_BANDA              =   "900";
    public static final String MODO_ING_TARJ_DESCO              =   "000";
    public static final String PTOVENTA_MODO_ING_TARJ_MANUAL    =   "0";
    public static final String PTOVENTA_MODO_ING_TARJ_BANDA     =   "1";
    public static final String COD_GEN_PPV                      =    "00";
    public static final String COD_GEN_ADQ                      =    "01";
    public static final char   SEPARADOR_CAMPOS                 =   '=';
    public static final String COD_MON_SOLES                    =   "604";
    public static final String COD_MON_DOLARES                  =   "840";
    public static final String COD_PTOVENTA_MON_SOL             =   "01";
    public static final String COD_PTOVENTA_MON_DOL             =   "02";
    public static final String TIP_DOC_ID_DNI                   =   "11";
    public static final char   MODO_PAGO_NORMAL                 =   '0';
    public static final String BOLETA                           =   "1";

    /******** CLARO***********/
    public static final String CLARO_NRO_TARJETA            =   "0000000000000000";
    public static final String COD_PZA_RECAUDADOR           =   "FASA";
    public static final String PRODUCTO_SERVICIO_REC        =   "REC";
    public static final String PRODUCTO_SERVICIO_RPD        =   "RPD";
    public static final String PRODUCTO_SERVICIO_1          =   "1";
    public static final String PRODUCTO_SERVICIO_101        =   "101";
    public static final String PRODUCTO_SERVICIO_102        =   "102";
    public static final String PRODUCTO_SERVICIO_103        =   "103";
    public static final String PREFIJO_23                   =   "23";
    public static final String PREFIJO_NCD                  =   "NCD";
    public static final String PREFIJO_NCS                  =   "NCS";
    public static final String MEDIO_PAGO_EFECTIVO          =   "00";
    public static final String BIN_PROCE_COBRANZA           =   "000002";
    public static final String ID_ACREEDOR                  =   "111111";
    public static final String TIPO_CAMBIO                  =   "00000000000";
    public static final String CTE_UN_DOCUMENTO             =   "10";
    public static final String CTE_UN_SERV_VARIOS_DOCS      =   "20";
    public static final String CTE_VARIOS_SERV_VARIOS_DOCS  =   "30";
    public static final String TAMANIO_MX_BLOQUE            =   "02000";
    public static final String POS_ULTIMO_DOCUMENTO         =   "000";
    public static final String PUNTERO_BD                   =   "0000000000";
    public static final String TIP_ID_DEUDOR                =   "01";
    public static final String ESTADO_DEUDOR                =   "VI";

    /*******MOVISTAR**********/
    public static final String MOVISTAR_NRO_TARJETA     =   "1111110000000001";
    public static final String CADENA_COMERCIAL_ORIGEN  =   "268422732720001";
    
    //INI ASOSA - 28/10/2014 - RECAR - NEX
    public static final String NEXTEL_NRO_TARJETA       =   "1111000000000001";   
    
    public static final String BITEL_NRO_TARJETA        =   "2222220000000000";   

    /********* CMR ***********/    
    public static final String APLICACION_SERVIDORA     =   "SCAD";
    public static final String COD_TRACK2_SERVICIO      =   "000";
    public static final char   COD_TRACK2_PVKI          =   '0';
    public static final String COD_TRACK2_PVV           =   "0000";
    public static final String COD_TRACK2_CVC1          =   "000";
    public static final String COD_TRACK2_LIBRE         =   "00000";    
    public static final String COD_PAIS                 =   "604";
    public static final String COD_COMERCIO             =   "019";
    public static final String COD_FUNCION_PAGO         =   "0217";
    public static final String COD_FUNCION_VENTA        =   "0200";
    public static final String COD_FUNCION_ANUL         =   "0202";
    public static final String COD_FUNCION_EXTORNO      =   "0420";
    public static final char   NUM_ORIGINAL_FASA        =   '7';
    public static final String TERMINAL_CAJA            =   "0000";
    public static final String ORIGEN_TRANSACCION       =   "04";
    public static final String FACTOR_CAMBIO            =   "0000000000";
    public static final char   MODO_CAPTURA_PIN         =   '0';
    public static final String TIP_DOC_ID_DNI_CMR       =   "01";


    /********************* RIPLEY **********************/
    public static final String TIP_DOC_ID_DNI_RIPLEY    =   "01";
    public static final String ID_SUB_ELEM_005          =   "005";
    public static final String ID_SUB_ELEM_006          =   "006";
    public static final String LONG_ELEM_005_SIN_MULTIP =   "008";
    public static final String LONG_ELEM_006            =   "018";
    public static final char   REQUERIMIENTO_CUOTAS     =   '2';
    public static final char   NO_DIFERIDO              =   '0';
    public static final char   ISSUER                   =   'I';
    public static final String PIN_DEFAULT              =   "0000000000000000";
    public static final String COD_AUTORIZ_TRX_DEFAULT  =   "000000";
    public static final String RESERVADO                =   "                                           *                         *";

    /**************** CODIGO FORMATO CLARO Y MOVISTAR *******************/
    public static final String COD_FORMATO_ESTANDAR     =   "01";
    public static final String COD_FORMATO_REDUCIDO     =   "02";

    /**************** RECARGAS CLARO Y MOVISTAR ******************/
    public static final String COD_PROCESADOR           =   "000001";
    public static final String COD_TELCO                =   "111111";
    public static final String COD_PRODUCTO_RECARGA     =   "21020001";//"00000001";
    public static final String TIP_DOC_ID_RECARGA       =   "00";

    /*************** TRAMA 800 ******************/
    public static final String ECHO_TEST                =   "301";
    
    //INI ASOSA - 29/10/2014 - RECAR - NEX
    /**************** NEXTEL *******************/
    //Campo 122 peticion 200. Por ahora esto es statico porque son los unicos valores que actualmente existen.
    public static final String tipoFormato = "001";
    public static final String procesador = "492150";
    public static final String empresa = "610002";
    public static final String prodServ = "0001";
    
    public static final String tipoDocTelefono = "01";
    
    public static final String formato125 = "03";
    public static final String filler125 = " ";    
    public static final String tipoDocumento125 = "00";
    public static final String nroDocumento125 = " ";
    public static final String nomRazSocial125 = " ";
    public static final String tipoComprobante125 = "00";
    public static final String medioPago125 = "00"; //Efectivo
    
    public static final String formato126 = "RV01";
    //FIN ASOSA - 29/10/2014 - RECAR - NEX
    //INI ASOSA - 29/10/2014 - RECAR - NEX
    public static final String canal125 = "00";
    public static final String giro125 = "000";
    public static final String ubigeo125 = "00000001";
    //FIN ASOSA - 29/10/2014 - RECAR - NEX

}
