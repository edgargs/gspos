package mifarma.ptoventa.reference;

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

public class VariablesPtoVenta
{
  public VariablesPtoVenta()
  {
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
   
    public static String vIndExisteImpresoraConsejo=""; //JCHAVEZ 01.07.2009.n variable para obtener la impresora termica 
    public static String  vTipoImpTermicaxIp="";//JCHAVEZ 03.07.2009.n variable para obtener el tipo de impresora termica 
    
   public static boolean  vIndImprimeRojo=false;
   
    //JMIRANDA 04/08/09
    public static String vDestEmailErrorCobro = "";
    public static String vDestEmailErrorAnulacion = "";
    public static String vDestEmailErrorImpresion = "";
    
    //dubilluz 25/08/2009
    public static String vIndVerStockLocales = "";
    
    //JCHAVEZ 17122009
    public static String vIndRecepCiega="";
    public static String vFechaInicioPruebas="";
    
    //JMIRANDA 19.01.2010
    public static String vDireccionMatriz = "";
    public static String vDireccionCortaMatriz = "";
    public static boolean vIndDirMatriz = false;
}
