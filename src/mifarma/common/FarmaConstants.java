package mifarma.common;

import javax.swing.JLabel;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaConstants.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaConstants {

    public static final String CODIGO_SUCURSAL_RRHH = "001";

    public static final String CODIGO_SUCURSAL_RRHH_LOS_ANDES = "002";

    public static final int FORMATO_FECHA = 1;

    public static final int FORMATO_HORA = 3;

    public static final int FORMATO_FECHA_HORA = 2;

    /**
     * Valores Retornados por Funcion de Verificación de Login del Usuario
     */
    public static final String LOGIN_USUARIO_OK = "01";

    public static final String LOGIN_USUARIO_INACTIVO = "02";

    public static final String LOGIN_NO_REGISTRADO_LOCAL = "03";

    public static final String LOGIN_CLAVE_ERRADA = "04";

    public static final String LOGIN_USUARIO_NO_EXISTE = "05";
    
    public static final String LOGIN_CLAVE_IGUAL = "06";

    public static final String LOGIN_CUATRO_ULTIMA = "97";//CHUANES 11.03.2015

    public static final String ERROR_VERSION_NO_VALIDA = "98";
    
    public static final String ERROR_CONEXION_BD = "99";
    /**
     * Roles del Sistema
     */
    public static final

    String ROL_OPERADOR_SISTEMAS = "000";
    public static final String ROL_MANTENIMIENTO_PRODUCTO = "002";
    public static final String ROL_MANTENIMIENTO_LABORATORIO = "003";
    public static final String ROL_MANTENIMIENTO_PRECIOS = "004";
    public static final String ROL_MANTENIMIENTO_FRACCIONAMIENTO = "005";
    public static final String ROL_MANTENIMIENTO_PROMOCIONES = "006";
    public static final String ROL_MANTENIMIENTO_PARAMETROS = "007";
    public static final String ROL_CAJERO = "009";
    //public static final String ROL_OPERADOR_DELIVERY = "009";
    public static final String ROL_VENDEDOR = "010";
    public static final String ROL_ADMLOCAL = "011";
    public static final String ROL_AUDITORIA = "012";
    public static final String ROL_RUTEADOR = "013";
    public static final String ROL_LECTURA_REPORTES = "014";
    public static final String ROL_LECTURA_INVENTARIO = "015";
    public static final String ROL_INVENTARIADOR = "016";
    public static final String ROL_GERENCIA_COMERCIAL = "017";
    public static final String ROL_COTIZADOR = "018";
    public static final String ROL_MANTENIMIENTO_TRABAJADORES = "019";
    public static final String ROL_SUPERVISOR_VENTAS = "027";
    public static final String ROL_CARGA_PRECIOS = "038"; //JCHAVEZ 14092009.n
    public static final String ROL_GPRECIOS_SET_COTIZACIONES = "041"; //CLARICO
    public static final String ROL_GPRECIOS_GENERAR_PRECIO = "042"; //CLARICO
    public static final String ROL_GPRECIOS_APROBAR_PUBLICACION = "043"; //CLARICO
    public static final String ROL_GPRECIOS_CONSULTA_HISTORICA = "044"; //CLARICO
    public static final String ROL_GPRECIOS_MANTENIMIENTO = "045"; //CLARICO


    /**Para el Mantenimiento de Locales*/
    public static final String ROL_CONTABILIDAD = "020";
    public static final String ROL_RDM = "021";
    public static final String ROL_ADMIN_ML = "022";
    public static final String ROL_COMPRAS = "023";
    public static final String ROL_MANTENIMIENTO_CONVENIOS = "024";
    public static final String ROL_PEDIDO_DISTRIBUCION = "025";

    public static final String ROL_ASISTENTE_CONTABLE = "026";

    public static final String ROL_SOPORTE = "028";
    public static final String ROL_ADMIN_SOPORTE = "029";
    public static final String ROL_MANTENIMIENTO_FORMAPAGO = "030";

    public static final String ROL_REBATE = "031";
    public static final String ROL_CATEGORIA_PROD = "032";
    public static final String ROL_COTIZADOR_COMP = "033";
    //ROL CONTROL DE MOTORIZADOS EN DELIVERY
    public static final String ROL_ADM_CTRL_MOTORIZADOS = "034";

    /**
     * Mantenimiento de Tablas (forma pre-determinada)
     */
    public static final

    String MANTENIMIENTO_VENTANA_PADRE = "PW";

    public static final String MANTENIMIENTO_VENTANA_HIJO = "CW";

    public static final String MANTENIMIENTO_CREAR = "CR";

    public static final String MANTENIMIENTO_MODIFICAR = "MR";

    public static final int ACCION_INSERT = 1;

    public static final int ACCION_UPDATE = 2;

    public static final String INSTITUCION_HERMES = "006";

    public static final String TIPO_DOC_DNI = "01";

    public static final String TIPO_DOC_CARNET = "02";

    public static final String TIPO_DOC_PASAPORTE = "03";

    public static final String TIPO_DOC_RUC = "04";

    /**
     * Códigos de Monedas
     */
    public static final String MONEDA_SOLES = "01";

    public static final String MONEDA_DOLARES = "02";

    public static final String DINERO_MONEDA = "02";

    public static final String DINERO_BILLETE = "01";

    public static final char[] blanco = 
    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 
      ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 
      ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 
      ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 
      ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };

    public static String NO_ESPECIFICADO = "*** No Especificado ***";

    public static String TIPO_DOCUMENTO_IDENTIDAD = "04";

    public static final String BARRA_ACCESO = "ACCESO";

    public static final String BARRA_PRODUCTOS = "PRODUCTOS";

    public static final String IMPUESTO_EXONERADO = "00";

    public static final String IMPUESTO_IGV = "01";

    public static final int ITEMS_POR_COMPROBANTE_CONVENIO = 6;

    public static final int ITEMS_POR_GUIA = 34;

    /**
     * Mantenimiento de Tablas Simples
     */
    public static final

    FarmaColumnData columnsTwoFields[] = 
    { new FarmaColumnData("Código", 80, JLabel.CENTER), 
      new FarmaColumnData("Descripción", 380, JLabel.LEFT), };

    public static final Object[] defaultValuesTwoFields = { " ", " " };

    public FarmaConstants() {
    }

    public static final String CODIGO_PAIS_PERU = "001";

    public static final String CODIGO_DEPARTAMENTO_LIMA = "014";

    public static final String CODIGO_PROVINCIA_LIMA = "001";

    public static final String GRUPO_MOTIVO_ANULACION_PEDIDO = "07";

    public static final String MOTIVO_ANULACION_NO_COBRADO = "001";

    public static final String MOTIVO_ANULACION_UNIR_PEDIDO = "002";

    public static final String MOTIVO_ANULACION_DEVOLUCION_VENTA = "003";

    public static final String MOTIVO_ANULACION_CORRECION = "001";

    /** tiempo de respuesta del local */
    public static final int TIEMPO_RESPUESTA_MATRIZ = 60;


    /********************************************************************************/
    public static final


    String ORDEN_ASCENDENTE = "asc";
    public static final String ORDEN_DESCENDENTE = "desc";

    public static final String INDICADOR_S = "S";
    public static final String INDICADOR_N = "N";

    public static final String COD_NUMERA_PRODUCTO = "001";
    public static final String COD_NUMERA_USU_LOCAL = "002";
    public static final String COD_NUMERA_CAJA = "005";
    public static final String COD_NUMERA_PEDIDO = "007";
    public static final String COD_NUMERA_PEDIDO_DIARIO = "009";
    public static final String COD_NUMERA_CLIENTE_LOCAL = "012";
    public static final String COD_NUMERA_SEC_COMP_PAGO = "015";
    public static final String COD_NUMERA_SEC_DIRECCION = "019";
    public static final String COD_NUMERA_SEC_TELEFONO = "020";
    public static final String COD_NUMERA_SEC_KARDEX = "016";
    public static final String COD_NUMERA_IMPRESORA = "006";
    public static final String COD_NUMERA_COTIZACION = "024";
    public static final String COD_NUMERA_RECETA = "026";
    public static final String COD_NUMERA_TRACE_VIRTUAL = "028";
    
    //JMIRANDA  01/07/09
    public static final String COD_NUMERA_IMPRESORA_TERMICA = "073";

    /**
     * Autor: Luis Reque
     * Fecha: 20/12/2006
     * Descripcion: nuevo codigo de numeración
     * */
    public static final String COD_NUMERA_MOTORIZADO = "023";

    /**
     * Numeracion para solicitudes de compras.
     * @author Edgar Rios Navarro
     * @since 23.08.2007
     */
    public static final String COD_NUMERA_SOLCOMPRA = "041";

    public static final int LONGITUD_NUM_PED_VTA = 10;

    public static final String[] ESTADOS_CODIGO = { "A", "I" };
    public static final String[] ESTADOS_DESCRIPCION = 
    { "ACTIVO", "INACTIVO" };

    public static final String[] INDICADORES_CODIGO = { "N", "S" };
    public static final String[] INDICADORES_DESCRIPCION = { "NO", "SI" };

    public static final String[] MONEDAS_CODIGO = { "01", "02" , "03"};
    public static final String[] MONEDAS_DESCRIPCION = { "SOLES", "DOLARES", "BOLIVIANOS"};

    public static final String HASHTABLE_MONEDA = "MONEDA";
    public static final String CODIGO_MONEDA_SOLES = "01";
    public static final String CODIGO_MONEDA_DOLARES = "02";
    public static final String CODIGO_MONEDA_BOLVS = "03";

    // PROD - PREPROD TODAS
    public static final String RUTA_PROPERTIES_CLAVE = 
        "/ptoventaid.properties";

    //TEST
    //public static final String RUTA_PROPERTIES_CLAVE = "C:\\EconoFar\\Clave.properties";

    public static final int CODIGO_SATISFACTORIO = 1;
    public static final int CODIGO_ERROR = 0;

    /**
     * Constantes para las Conexiones Remotas
     * @author : dubilluz
     * @since  : 15.08.2007
     */
    public static final int CONECTION_MATRIZ = 0;
    public static final int CONECTION_DELIVERY = 1;
    
    //Agregado por DVELIZ 15.12.2008
    public static final int CONECTION_ADMCENTRAL = 2;

    //Agregado por FRAMIREZ 16.12.2011
    public static final int CONECTION_RAC = 3;
    
    //Agregado por RHERRERA
    public static final int CONECTION_TICO = 4;

    //JMIRANDA 23/07/09 Destinatarios para recibir Error de Impresion
    public static final String EMAIL_DESTINATARIO_ERROR_IMPRESION = "dubilluz;operador;jmiranda;lpanduro";
    public static final String EMAIL_DESTINATARIO_CC_ERROR_IMPRESION = "joliva;operador;daubilluz@gmail.com;jmiranda";
    //public static final String EMAIL_DESTINATARIO_CC_ERROR_IMPRESION = "";
    
    /*
     * jquispe 04.08.2010 Constantes para obtener Digito de Control
     * 
     * */
    public static final String[] array = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
                                     "R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i",
                                     "j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                                     "0","1","2","3","4","5","6","7","8","9","=","#","&","(",")","*","+","-","_",
                                     "/","\"","<",">","@","[","]","{","}","%","$"};
    
    /****************Variables de Transaccion*****************************/
    
    public static long N_Autorizacion= 8004005263848L;//max. 15 digitos
    public static long N_Factura=673173L;//max 12 digitos
    public static long N_Nit= 1666188L;//max 12 digitos
    public static long F_Fecha_Tr= 20080810L;//max 8 digitos
    //se redondea hacia arriba 
    public static long N_Monto_tr=51330L;
    
    public static String S_Llave_Dosif="PNRU4cgz7if)[tr#J69j=yCS57i=uVZ$n@nv6wxaRFP+AUf*L7Adiq3TT[Hw-@wt";//max 256 digitos
    
    //ERIOS 09.12.2014 Cadena de conexion
    public static final String CONNECT_STRING_SID = "jdbc:oracle:thin:%s/%s@%s:%s:%s";
    public static final String CONNECT_STRING_SID_W    = "jdbc:oracle:thin:@%s:%s:%s";
    public static final String CONNECT_STRING_SERVICENAME = "jdbc:oracle:thin:%s/%s@//%s:%s/%s";
    public static final String CONNECT_STRING_SERVICENAME_W    = "jdbc:oracle:thin:@//%s:%s/%s";
        
}
