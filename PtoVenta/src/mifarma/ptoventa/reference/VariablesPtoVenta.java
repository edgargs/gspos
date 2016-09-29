package mifarma.ptoventa.reference;

import java.util.ArrayList;

import javax.print.PrintService;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : VariablesPtoVenta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      22.02.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class VariablesPtoVenta {
    public VariablesPtoVenta() {
    }

    public static String vCodFiltro = "";
    public static String vDescFiltro = "";

    public static String vTipoFiltro = "";

    public static String vTituloListaMaestros = "";
    public static String vTipoMaestro = "";

    public static String vCodMaestro = "";
    public static String vDescMaestro = "";

    public static String vInd_Filtro = "";
    public static String vDesc_Cat_Filtro = "";

    public static String vCodOperador = "";

    public static String vSecMovCaja = "";
    public static String vNumCaja = "";

    public static String vSecMovCajaOrigen = "";


    public static String vTipOpMovCaja = "";

    public static String vRUCProv = "";

    public static String vIdProv = "";

    public static String vTipListaMaestros = ConstantsPtoVenta.TIP_LIST_MAESTRO_ORD;

    public static String vTipAccesoListaComprobantes = "";

    /**
     * VAlor de NUmero de Dias sin Ventas para el reporte
     * @author : dubilluz
     * @since  : 21.08.2007
     */
    public static String vNumeroDiasSinVentas = " ";

    /**
     * Variable para ver si consultara el IndVerStockLocales
     * @author dubilluz
     * @since  05.11.2007
     */
    public static String vRevisarIndStockLocales = "";

    /**
     * Esta variables se utilizara para saber si se realiza una accion de una funcion
     * y esta no se pueda realizar mas de una vez.
     * @author dubilluz
     * @since  02.12.2008
     */
    public static boolean vEjecutaAccionTecla = false;

    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static PrintService vImpresoraActual;

    public static String vIndExisteImpresoraConsejo =
        ""; //JCHAVEZ 01.07.2009.n variable para obtener la impresora termica
    public static String vTipoImpTermicaxIp =
        ""; //JCHAVEZ 03.07.2009.n variable para obtener el tipo de impresora termica

    public static boolean vIndImprimeRojo = false;

    //JMIRANDA 04/08/09
    public static String vDestEmailErrorCobro = "";
    public static String vDestEmailErrorAnulacion = "";
    public static String vDestEmailErrorImpresion = "";
    public static String vDestEmailErrorConexion = "";
    public static String vDestEmailErrorIntegrador = "";

    //dubilluz 25/08/2009
    public static String vIndVerStockLocales = "";

    //Luigy Terrazos 26/04/2013
    public static String vIndVerReceMagis = "";

    //JCHAVEZ 17122009
    public static String vIndRecepCiega = "";
    public static String vFechaInicioPruebas = "";

    //JMIRANDA 19.01.2010
    public static String vDireccionMatriz = "";
    public static String vDireccionCortaMatriz = "";
    public static boolean vIndDirMatriz = false;

    //ERIOS 12.09.2013
    public static boolean vIndDirLocal = false;
    //ERIOS 12.09.2013
    public static boolean vIndRegistroVentaRestringida = false;

    /**
     * Razon Social de la compania
     * @author ERIOS
     * @since 05.03.2013
     */
    public static String vRazonSocialCia = "";

    /**
     * Telefono de la compania
     * @author ERIOS
     * @since 05.03.2013
     */
    public static String vTelefonoCia = "";

    /**
     * Nombre de la Nombre/Marca Comercial
     * @author ERIOS
     * @since 05.03.2013
     */
    public static String vNombreMarcaCia = "";

    /**
     * Variables de Conexion de FASA
     * @author ERIOS
     * @since 19.06.2013
     */
    public static BeanConexion conexionFasa;

    /**
     * Indicador servicios FarmaSix
     * @author ERIOS
     * @since 16.07.2013
     */
    public static String vIndFarmaSix;

    /**
     * Indicador Pinpad
     * @author ERIOS
     * @since 16.08.2013
     */
    public static String vIndPinpad;

    /**
     * Indicador impresion url web
     * @author ERIOS
     * @since 16.08.2013
     */
    public static String vIndImprWeb;

    /**
     * Impresora Sticker
     * @author ERIOS
     * @since 23.12.2013
     */
    public static PrintService vImpresoraSticker;

    /**
     * Conexion LOCAL - TICO
     * @author RHERRERA - ind Tico
     * @since 2.4.6
     */
    public static BeanConexion conexionTICO;
    public static String vIndTico = "";
    public static String vIndLocalPadre = "";
    public static String vIndConFarma = "";
    public static ArrayList vLocarMarketH = new ArrayList();

    public static BeanConexion conexionRecargas;
    
    public static String vIndAnulacionCambioProducto;

    public static BeanConexion conexionGTX;
    public static String strIndGestorTx = "N";
    
    
    /**
     * Variable para el tipo de venta
     * @author CCASTILLO
     * @since 09/05/2016
     */
    public static String vTipoLocalVenta = "";
    
}
