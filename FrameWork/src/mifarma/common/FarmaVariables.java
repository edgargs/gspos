package mifarma.common;

import java.util.ArrayList;

import mifarma.common.DlgLogin;

import mifarma.ptoventa.reference.BeanConexion;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaVariables.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaVariables {

    /** Usuario de Conexión a la BD */
    public static String vUsuarioBD = "";

    /** Clave de Conexión a la BD */
    public static String vClaveBD = "";

    /** IP de Conexión a la BD */
    public static String vIPBD = "";

    /** SID de Conexión a la BD */
    public static String vSID = "";

    /** PUERTO de Conexión a la BD */
    public static String vPUERTO = "";

    ///** Almacena el Objeto JDialog - DlgLogin - para controlar los accesos */
    public static DlgLogin dlgLogin;

    /**
     *Almacena el modo en el que fué cerrada una Ventana.
     *Si la ventana es cerrada Aceptando se almacena TRUE, en caso contrario,
     *es decir, se cierra la ventana pulsando la tecla [Esc] se almacena FALSE
     */
    public static boolean vAceptar = false;

    /** Almacena el Código del Grupo de la Compañía a la cual pertenece */
    public static String vCodGrupoCia = "";

    /** Almacena el Código de la Compañía a la cual se pertenece */
    public static String vCodCia = "";

    /** Almacena el Nombre de la Compañía a la cual se pertenece */
    public static String vNomCia = "";

    /** Almacena el Código de Compañía usado por la Oficina de Personal */
    public static String vCodCiaRRHH = "";

    /** Almacena el Local donde se ejecuta la Aplicación */
    public static String vCodLocal = "";

    /** Almacena la descripción corta del Local */
    public static String vDescCortaLocal = "";

    /** Almacena la descripción del Local */
    public static String vDescLocal = "";
    
    /** Almacena la descripción corta de la direccion del local */
        public static String vDescCortaDirLocal = "";

    /**
     *Almacena el Tipo de Caja establecido según disposición del Local.
     *Los tipos existentes son :
     *Tradicional : Existen vendedores y un determinado número de cajeros
     *Multifuncional : Existen vendedores - cajeros (ambas funciones)
     */
    public static String vTipCaja = "";

    /**
     *Almacena el Tipo de Local.
     *Los tipos existentes son :
     * Ventas : Locales de venta por mesón
     * Delivery: Local de venta por delivery
     * Almacen: Local de venta en matriz
     */
    public static String vTipLocal = "";


    /** Almacena el Secuencial del Usuario logueado a la Aplicación */
    public static String vNuSecUsu = "";

    /** Almacena el Id del Usuario logueado a la Aplicación */
    public static String vIdUsu = "";

    /** Almacena el Id del Usuario logueado a Caja Electronica */
    public static String vIdUsuCE = "";

    /** Almacena el indicador de habilitado para modificar el precio en el local */
    public static String vIndHabilitado = "";

    /** Almacena el Nombre del Usuario logueado a la Aplicación */
    public static String vNomUsu = "";

    /** Almacena el Apellido Paterno del Usuario logueado a la Aplicación */
    public static String vPatUsu = "";

    /** Almacena el Apellido Materno del Usuario logueado a la Aplicación */
    public static String vMatUsu = "";

    /** Almacena el Tipo de Cambio usado por la Aplicación */
    public static double vTipCambio = 0.00;

    /** Almacena el Código de la Moneda */
    public static String vCodMoneda = FarmaConstants.MONEDA_SOLES;

    /** Almacena el tipo de proceso donde se ejecuta la búsqueda de la Lista de Precios */
    public static String vTipProc = FarmaConstants.BARRA_ACCESO;

    /** Almacena la ruta donde se ubican las imágenes de los productos */
    public static String vImagenProd = "";

    /** Almacena el valor del Impuesto General a las Ventas - 19.00% */
    public static double vIgvPorc = 0.00;

    /** Almacena el valor del Impuesto General a las Ventas para Cálculo - 1.19 */
    public static double vIgvCalculo = 0.00;

    public static int vTipAccion;
    public static String vImprReporte = "LPT1";
    public static String vImprTestigo = "LPT1";

    public static String vImprBoleta = "";
    public static String vImprFactura = "";
    public static String vImprGuia = "";

    public static String vMant = FarmaConstants.MANTENIMIENTO_MODIFICAR;
    public static ArrayList vDataMant = new ArrayList();
    public static String vSQL = "";

    public static String vIpPc = "";
    public static String vNamePc = "";
    public static String vServidorImpresion = "N";

    public static String vMultFormasPago = "N";
    public static String vMultComp = "N";

    /** Indicador de descuento adicional */
    public static boolean vDsctoAdic = false;

    /** Valor de descuento adicional */
    public static double vValDcto = 0.0;

    /** Valor del departamento del local*/
    public static String vDptoLocal = "";

    /** Valor de la provincia del local*/
    public static String vProvLocal = "";

    /** Valor del numero de RUC de la compania del local*/
    public static String vNuRucCia = "";

    public static String vCodMonedaPais = FarmaConstants.MONEDA_SOLES;

    public static String vDescCia = new String("");

    /** Indicador de origen de ingreso a la aplicación de Pto de Venta (Matriz o Local) */
    public static boolean vEconoFar_Matriz = false;

    /** Valor en Minutos para el proceso de anulacion masiva de pedidos pendientes*/
    public static String vMinutosPedidosPendientes = "";

    public static String vCodUsuMatriz = "";
    public static String vClaveMatriz = "";

    public static String vIdUsu_Toma = "";

    /** Variables de carga para el properties de la base de datos de MATRIZ*/
     public static BeanConexion conexionMATRIZ;

    /** Variables de carga para el properties de la base de datos de DELIVERY*/
     public static BeanConexion conexionDELIVERY;

    /** Variables de carga para el properties de la base de datos de ADM Central*/
     public static BeanConexion conexionAPPS;    

    /** Variables de carga para el properties de la base de datos de Rac*/
     public static BeanConexion conexionRAC;


    //Agregado por RHERRERA 15.09.2014
    /** Variables de carga para el properties de la base de datos de Rac*/
    public static String vIdUsuTico      = "";
    public static String vClaveTico      = "";
    public static String vIpServidorTico = "";
    public static String vSidTico        = "";
    
    
    //JMIRANDA 24/07/09
    /**Variable que almacena el Nro de Pedido que no se pudo Imprimir */
    public static String vNroPedidoNoImp = "";
    
    //JMIRANDA 04/08/09
    public static String vEmail_Destinatario_Error_Impresion = "";
    
    //JQUISPE 04/08/2010 Variables de digito control 
    public static  long N_Autorizacion=0L;
    public static  long N_Factura=0L;
    public static  long N_Nit=0L;
    public static  long F_Fecha_Tr=0L;
    public static  long N_Monto_tr=0L;     
    public static  String S_Llave_Dosif="";
    
    
    /**
     *Constructor
     */
    public FarmaVariables() {
    }

}


