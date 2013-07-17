package mifarma.ptoventa.caja.reference;

import com.gs.mifarma.MetodosBprepaid;
import com.gs.mifarma.MetodosTXFactory;
import com.gs.mifarma.MetodosTXVirtual;
import mifarma.common.FarmaUtility;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.SQLException;
import java.awt.print.*;
import mifarma.common.*;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.ventas.reference.*;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.caja.reference.*;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import java.text.SimpleDateFormat;
import java.io.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.print.event.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;

//import tarjetas.MetodosG;
//import tarjetas.RespuestaNavSatBean;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.convenio.reference.*;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.ventas.DlgResumenPedido;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.TimerRecarga;
import java.util.Timer;
import java.util.TimerTask;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaPrintServiceTicket;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.TimerRecarga;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import com.gs.mifarma.MetodosBprepaid;
import com.gs.mifarma.MetodosTXFactory;
import com.gs.mifarma.MetodosTXVirtual;

import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;

import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega; //JCHAVEZ 23112009
/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      06.03.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class UtilityCaja {

  private static final Log log = LogFactory.getLog(UtilityCaja.class);
  private static boolean consejo=false;
  
  private static int numeroCorrel = 1;
  /**
   * Constructor
   */
  public UtilityCaja() {
  }
      
    /**
     * Metodo que sirve para validar que existe conexion en matriz
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pCadena
     * @param pParent
     */
    public static void validarConexionMatriz(){
        
        
        VariablesCaja.vIndConexion= FarmaUtility.getIndLineaOnLine(
                                     FarmaConstants.CONECTION_MATRIZ,
                                     FarmaConstants.INDICADOR_N);
        
        
        if(VariablesCaja.vIndConexion.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
           System.out.println("No existe linea se cerrara la conexion ...");
           FarmaConnectionRemoto.closeConnection();
        
        }
        
                         
     
    }

  //private static MetodosG proveedorTarjeta;

  /**
   * El proveedor ahora es Brightstar
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static MetodosBprepaid proveedorTarjetaBprepaid;
  
  /**
   * Se utiliza el patron Factory para invocar a estos metodos.
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static MetodosTXVirtual proveedorTarjetaVirtual;


  public static String obtieneEstadoPedido(JDialog pDialog, String pNumPedVta)
  {
    String estadoPed = "";
    try {
      estadoPed = DBCaja.obtieneEstadoPedido(pNumPedVta);
      return estadoPed;
    } catch (SQLException sqlException) {
      //sqlException.printStackTrace();
      log.error(null,sqlException);
      //FarmaUtility.showMessage(pDialog, "Error al obtener Estado del Pedido !!! - " + sqlException.getErrorCode(), pObjectFocus);
      return estadoPed;
    }
  }
  
  /**
     * Valida si existe impresora asociada al cajero que realiza el cobro
     * @param pDialog
     * @param pObjectFocus
     * @return
     */
  public static boolean existeCajaUsuarioImpresora(JDialog pDialog, Object pObjectFocus) {
        try {
            boolean existeCajaUsuarioImpresora = true;
            int cajaUsuario;
            cajaUsuario = DBCaja.obtieneNumeroCajaUsuario();
            if (cajaUsuario != 0) {
                VariablesCaja.vNumCaja = new Integer(cajaUsuario).toString();
                int cajaAbierta = DBCaja.verificaCajaAbierta();
                if (cajaAbierta == 0) {
                    VariablesCaja.vNumCaja = "";
                    existeCajaUsuarioImpresora = false;
                    FarmaUtility.showMessage(pDialog, 
                                             "La Caja relacionada al Usuario no ha sido Aperturada. Verifique !!!", 
                                             pObjectFocus);
                } else {
                    log.debug("VariablesCaja.vNumCaja = " + 
                              VariablesCaja.vNumCaja);
                    VariablesCaja.vSecMovCaja = 
                            DBCaja.obtieneSecuenciaMovCaja();
                    log.debug("VariablesCaja.vSecMovCaja = " + 
                              VariablesCaja.vSecMovCaja);
                    if (VariablesCaja.vSecMovCaja.equals("0")) {
                        existeCajaUsuarioImpresora = false;
                        VariablesCaja.vSecMovCaja = "";
                        FarmaUtility.showMessage(pDialog, 
                                                 "No se pudo determinar el Movimiento de Caja. Verifique !!!", 
                                                 pObjectFocus);
                    } else {
                        if (!existeImpresorasVenta(pDialog, pObjectFocus))
                            existeCajaUsuarioImpresora = false;
                    }
                }
            } else {
                existeCajaUsuarioImpresora = false;
                FarmaUtility.showMessage(pDialog, 
                                         "El usuario No tiene caja relacionada. Verifique !!!", 
                                         pObjectFocus);
            }
            return existeCajaUsuarioImpresora;
        } catch (SQLException sqlException) {
            //sqlException.printStackTrace();
            log.error(null, sqlException);
            FarmaUtility.showMessage(pDialog, 
                                     "Error al obtener Datos de la Relacion Caja - Usuario - Impresora !!! - " + 
                                     sqlException.getErrorCode(), 
                                     pObjectFocus);
            return false;
        }
  }

  public static boolean existeImpresorasVenta(JDialog pDialog, Object pObjectFocus) throws SQLException {
    boolean existeImpresorasVenta = true;
    //JCORTEZ 24.03.09 No se valida por ahora la relacion de caja impresora obligatoria
    //String tipoComprobanteImpresora = DBCaja.verificaRelacionCajaImpresoras();
   /* if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Boletas. Verifique !!!", pObjectFocus);
    } else if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Facturas. Verifique !!!", pObjectFocus);
    } else if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_GUIA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Guias. Verifique !!!", pObjectFocus);
    } else
    {*/
      ArrayList myArray = new ArrayList();
      DBCaja.obtieneSecuenciaImpresorasVenta(myArray);
      if(myArray.size() <= 0)
      {
        VariablesCaja.vSecImprLocalBoleta = "";
        VariablesCaja.vSecImprLocalFactura = "";
        VariablesCaja.vSecImprLocalGuia = "";
        VariablesCaja.vSecImprLocalTicket="";
        VariablesCaja.vSerieImprLocalTicket="";
        existeImpresorasVenta = false;
        FarmaUtility.showMessage(pDialog, "Error al leer informacion de las impresoras.", pObjectFocus);
      } else
      {
        VariablesCaja.vSecImprLocalBoleta = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
        VariablesCaja.vSecImprLocalFactura = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
        VariablesCaja.vSecImprLocalGuia = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
        VariablesCaja.vSecImprLocalTicket=((String)((ArrayList)myArray.get(0)).get(3)).trim();
        VariablesCaja.vSerieImprLocalTicket=((String)((ArrayList)myArray.get(0)).get(4)).trim();
        System.out.println("VariablesCaja.vSecImprLocalBoleta : " + VariablesCaja.vSecImprLocalBoleta);
        System.out.println("VariablesCaja.vSecImprLocalFactura : " + VariablesCaja.vSecImprLocalFactura);
        System.out.println("VariablesCaja.vSecImprLocalGuia : " + VariablesCaja.vSecImprLocalGuia);
        System.out.println("VariablesCaja.vSecImprLocalTicket : " + VariablesCaja.vSecImprLocalTicket);
        existeImpresorasVenta = true;   
      }
    //}
    return existeImpresorasVenta;
  }

  public static void imprimeComprobantePago(JDialog pJDialog,
                                            ArrayList pDetalleComprobante,
                                            ArrayList pTotalesComprobante,
                                            String pTipCompPago,
                                            String pNumComprobante) throws Exception {

    String pValTotalBruto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(0)).trim();
    String pValTotalNeto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(1)).trim();
    String pValTotalDescuento = ((String)((ArrayList)pTotalesComprobante.get(0)).get(2)).trim();
    String pValTotalImpuesto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(3)).trim();
    String pValTotalAfecto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(4)).trim();
    String pValRedondeo = ((String)((ArrayList)pTotalesComprobante.get(0)).get(5)).trim();
    String pPorcIgv = ((String)((ArrayList)pTotalesComprobante.get(0)).get(6)).trim();
    String pNomImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(7)).trim();
    String pNumDocImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(8)).trim();
    String pDirImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(9)).trim();
    String fechaBD = ((String)((ArrayList)pTotalesComprobante.get(0)).get(10)).trim();
    String pValTotalAhorro = ((String)((ArrayList)pTotalesComprobante.get(0)).get(11)).trim();
    
    pValTotalBruto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalBruto),2);
    pValTotalNeto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalNeto),2);
    pValTotalDescuento = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalDescuento),2);
    pValTotalImpuesto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalImpuesto),2);
    pValTotalAfecto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalAfecto),2);
    pValRedondeo = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValRedondeo),2);
    pPorcIgv = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pPorcIgv),2);

    /**
     * Ruta para la generecion del archivo
     * @author JCORTEZ
     * @since 06.07.09
     * */
        String ruta ="";
        ruta=DBCaja.ObtieneDirectorio();
        
        //Se agrega la Fecha al archivo Impreso.
        //JMIRANDA  07/07/2009
        Date vFecImpr = new Date();
        String fechaImpresion;
              
        String DATE_FORMAT = "yyyyMMdd";
           SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            // System.out.println("Today is " + sdf.format(vFecImpr));
           fechaImpresion =  sdf.format(vFecImpr);                
        
        System.out.println("fecha : " +fechaImpresion);
        
  if ( pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) ){
  
      //ruta=ruta+"B_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
      //JMIRANDA 07/07/09 se agrega FECHA al Nombre
      ruta=ruta+fechaImpresion+"_"+"B_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
      
   //impresion 
      imprimeBoleta(pJDialog,
                    fechaBD,
                    pDetalleComprobante,
                    pValTotalNeto,
                    pValRedondeo,
                    pNumComprobante,
                    pNomImpreso,
                    pDirImpreso,
                    pValTotalAhorro,
                    ruta,true);

                    
  }else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){  //JCORTEZ  25.03.09
   System.out.println("*******JCORTEZ**********");
        System.out.println("PARAMETROS-->");
        System.out.println("pNomImpreso-->"+pNomImpreso);
        System.out.println("pDirImpreso-->"+pDirImpreso);
        //ruta=ruta+"T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        //JMIRANDA 07/07/09 se agrega FECHA al Nombre
        ruta=ruta+fechaImpresion+"_"+"T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        System.out.println("fecha : " +fechaImpresion);
        
        //impresion 
        imprimeBoletaTicket(pJDialog,
                            fechaBD,
                            pDetalleComprobante,
                            pValTotalNeto,
                            pValRedondeo,
                            pNumComprobante,
                            pNomImpreso,
                            pDirImpreso,
                            pValTotalAhorro,
                            ruta,true); 
         
    }else if ( pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) )
    {

      if(VariablesVentas.vTipoPedido.equals(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)&& pNumComprobante.substring(0,1).equalsIgnoreCase("5"))
      {
        System.out.println("*******imprimir factura para venta institucional**********");
          //ruta=ruta+"FI_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
          //JMIRANDA 07/07/09 se agrega FECHA al Nombre
          ruta=ruta+fechaImpresion+"_"+"FI_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        
          
        //impresion 
        imprimeFacturaGuia(pJDialog,
                           fechaBD,
                           pDetalleComprobante,
                           pValTotalBruto,
                           pValTotalNeto,
                           pValTotalAfecto,
                           pValTotalDescuento,
                           pValTotalImpuesto,
                           pPorcIgv,
                           pValRedondeo,
                           pNumComprobante,
                           pNomImpreso,
                           pNumDocImpreso,
                           pDirImpreso,
                           ruta,true);                         
      } else
      {
          //ruta=ruta+"F_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
          //JMIRANDA 07/07/09 se agrega FECHA al Nombre
          ruta=ruta+fechaImpresion+"_"+"F_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        //impresion
        imprimeFactura(pJDialog,
                       fechaBD,
                       pDetalleComprobante,
                       pValTotalBruto,
                       pValTotalNeto,
                       pValTotalAfecto,
                       pValTotalDescuento,
                       pValTotalImpuesto,
                       pPorcIgv,
                       pValRedondeo,
                       pNumComprobante,
                       pNomImpreso,
                       pNumDocImpreso,
                       pDirImpreso,
                       pValTotalAhorro,
                       ruta, true);      
        
      //}
      }
    /*else if ( VariablesCaja.vTiComprobante.equalsIgnoreCase(ConstantsVentas.TIPO_GUIA) )
      imprimeGuia(pJDialog,
                  fechaBaseDatos,
                  pDetallePedido,
                  pVaTotalVenta,
                  pVaTotalDescuento,
                  pVaTotalImpuesto,
                  pVaTotalPrecioVenta,
                  pVaTotalPrecioVentaConvenio,
                  pVaSaldoRedondeo,
                  nombreVendedor,
                  pEsConvenio,
                  pACuenta,
                  pPendiente,
                  pComprobante,
                  pImprimeDescuento,
                  pDetalleImpresion,
                  pDeducible);
    */
    
    } 
  }

  private static void imprimeBoleta(JDialog   pJDialog,
                                    String    pFechaBD,
                                    ArrayList pDetalleComprobante,
                                    String    pValTotalNeto,
                                    String    pValRedondeo,
                                    String    pNumComprobante,
                                    String    pNomImpreso,
                                    String    pDirImpreso,
                                    String    pValTotalAhorro,
                                    String    pRuta,
                                    boolean   bol) throws Exception {
    System.out.println("IMPRIMIR BOLETA No : " + pNumComprobante);
    String indProdVirtual = "";
    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    
    //JCORTEZ 06.07.09 ruta para la genericon de archivo
   // if(bol) VariablesCaja.vRutaImpresora=pRuta;
    
    //FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora + "boleta" + pNumComprobante + ".txt", false);
    FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora, false);

      //JCORTEZ 16.07.09 Se genera archivo linea por linea
      FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
      vPrintArchivo.startPrintService();
      
    System.out.println("Ruta : " + VariablesCaja.vRutaImpresora + "boleta" + VariablesCaja.vNumPedVta + ".txt");
  //  if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
     System.out.println("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
  if ( !vPrint.startPrintService() ) {
      
      
      
                VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                 log.info("**** Fecha :"+ pFechaBD);
                 log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                 log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                 log.info("**** IP :" + FarmaVariables.vIpPc);
                 log.info("ERROR DE IMPRESORA : No se pudo imprimir la boleta");
                    }
  
  else {
      try {
    vPrint.activateCondensed();
          if(VariablesPtoVenta.vIndDirMatriz){
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesPtoVenta.vDireccionCortaMatriz ,true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesPtoVenta.vDireccionCortaMatriz ,true);
          }
   //JMIRANDA 22.08.2011 Cambio para verificar si imprime
   if(UtilityVentas.getIndImprimeCorrelativo()){        
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
      vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
   }
   else{
       vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD ,true);
         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD ,true);
   }
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
      vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
    
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
      vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
    vPrint.printLine(" ",true);
      vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
      vPrintArchivo.printLine(" ",true);
    int linea = 0;
    for (int i=0; i<pDetalleComprobante.size(); i++) {
      //Agregado por DVELIZ 13.10.08
      String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
        System.out.println("valor 1:"+valor);
      if(valor.equals("0.000")) valor = " ";
      //fin DVELIZ
      System.out.println("Deta "+ (ArrayList)pDetalleComprobante.get(i) );
        System.out.println("valor 2:"+valor);
      vPrint.printLine("" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),10) + " " +
                       //Agregado por DVELIZ 10.10.08
                       FarmaPRNUtility.alinearDerecha(valor,8) + "" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10),true);
                       
        vPrintArchivo.printLine("" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),10) + " " +
                       //Agregado por DVELIZ 10.10.08
                       FarmaPRNUtility.alinearDerecha(valor,8) + "" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10),true);                       
      linea += 1;
      indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
      //verifica que solo se imprima un producto virtual en el comprobante
      if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
      else
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    }

    if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
    {
      vPrint.printLine("", true);
       vPrintArchivo.printLine("",true);
      impresionInfoVirtual(vPrint,vPrintArchivo,
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                           VariablesCaja.vNumPedVta,//Se añadio el parametro
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto

      linea = linea + 4;
   }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        linea++;
    }
    //MODIFICADO POR DVELIZ 13.10.08
    //
    if(!VariablesVentas.vEsPedidoConvenio){
        if(pDetalleComprobante.size()< 8){
            for (int j=linea; j<=8; j++) {
                    if(!VariablesCaja.vImprimeFideicomizo){
                            vPrint.printLine(" ",true);
                            vPrintArchivo.printLine(" ",true);
                    }
                }
        }
    }else{
        for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_BOLETA; j++)  if(!VariablesCaja.vImprimeFideicomizo)vPrint.printLine(" ",true);
    }
    
    //*************************************INFORMACION DEL CONVENIO*************************************************//
    //*******************************************INICIO************************************************************//

    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      try
      {
        ArrayList aInfoPedConv = new ArrayList();
        DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

        for(int i=0; i<aInfoPedConv.size(); i++)
        {
          ArrayList registro = (ArrayList) aInfoPedConv.get(i);
        //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
        String Ind_Comp=((String)registro.get(8)).trim();
        if(Ind_Comp.equalsIgnoreCase("N")){
          vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                           //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                           FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                           ,true);
                           
           vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                           //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                           FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                           ,true);                           
          /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
          String vCredDisp = ((String)registro.get(7)).trim();
          if(vCredDisp.equals(""))
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                             ,true);
              vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                               ,true);
          }else
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                             FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                             ,true);
              vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                               FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                               ,true);
          } 
         } 
        }

      }
        //ASOLIS 
                //IMPRIMIR  EL  IP ,NUMERO COMPROBANTE y HORA DE IMPRESIÓN  EN CASO DE ERROR.*/
              catch(SQLException sql)
              {
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";
                  
                //sql.printStackTrace();
                System.out.println("Error de BD "+ sql.getMessage());
                
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE BOLETA:" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al obtener informacion del Pedido Convenio ");
                  log.info("Error al imprimir la BOLETA : ");
                  log.error(null,sql);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
              }
              
                catch(Exception e){
                  
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";
                  
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE BOLETA :" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al imprimir la BOLETA : "+e);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
              }
      
      //vPrint.printLine(" ",true);
    }
     else
    {
    //dveliz 13.10.08
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
    }
    
    //ERIOS 25.07.2008 imprime el monto ahorrado.
    double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
    
    //DUBILLUZ 22.08.2008 MSG DE CUPONES
    String msgCumImpresos = " ";
    if(VariablesCaja.vNumCuponesImpresos>0){
        String msgNumCupon = "";
        if(VariablesCaja.vNumCuponesImpresos==1){
            msgNumCupon = "CUPON";
        }
        else{
            msgNumCupon = "CUPONES";
        }
        msgCumImpresos = " UD. GANO "+VariablesCaja.vNumCuponesImpresos+ " "+
                         msgNumCupon;
    }
      
    //MODIFICADO POR DVELIZ 02.10.08
    //vPrint.printLine(" "+VariablesFidelizacion.vNomClienteImpr, true);
    if(auxTotalDcto > 0)
    {
      /* old 01.09.2009
      vPrint.printLine("                         UD. HA AHORRADO S/. "+
                       pValTotalAhorro+
                       " EN ESTA COMPRA"+
                       msgCumImpresos,
                       true);
        vPrintArchivo.printLine("                         UD. HA AHORRADO S/. "+
                         pValTotalAhorro+
                         " EN ESTA COMPRA"+
                         msgCumImpresos,
                         true);
        */
log.info("Imprimiendo Ahorro");
        
        //JCORTEZ 02.09.2009 Se muestra mensaje distinto si es fidelizado o no.
        String obtenerMensaje="";
        String indFidelizado="";
        log.info("Identificando cliente fidelizado");
        if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
            indFidelizado="S";
        }else 
         { indFidelizado="N"; }
        log.info("Fidelizado--> "+indFidelizado);    
        obtenerMensaje=obtenerMensaAhorro(pJDialog,indFidelizado);
        vPrint.printLine(""+obtenerMensaje+" "+" S/. "+pValTotalAhorro+"  "+msgCumImpresos,true);
          vPrintArchivo.printLine(""+obtenerMensaje+" S/. "+pValTotalAhorro+"  "+msgCumImpresos,true);
         /* vPrint.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA"+msgCumImpresos,true);
             vPrintArchivo.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA"+msgCumImpresos,true);*/
    
    }else
    {
        if(VariablesCaja.vNumCuponesImpresos>0){
           vPrint.printLine("                         "+msgCumImpresos,true);
        vPrintArchivo.printLine("                         "+msgCumImpresos,true);
           //vPrint.printLine(" "+VariablesFidelizacion.vNomClienteImpr+msgCumImpresos,true);
       }else{
           vPrint.printLine(" ",true);
               vPrintArchivo.printLine(" ",true);
           }
    }
    
    //*********************************************FIN*************************************************************//
    //*************************************INFORMACION DEL CONVENIO***********************************************//

    VariablesVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
    VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
    }
    if( VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
        VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) )
    {
      VariablesVentas.vTituloDelivery = "" ;
    } else VariablesVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
    
/*
    System.out.println("****************DIEGO************************");
    System.out.println("VariablesVentas.vTipoPedido " + VariablesVentas.vTipoPedido);
    System.out.println("VariablesCaja.vFormasPagoImpresion " + VariablesCaja.vFormasPagoImpresion);
    System.out.println("VariablesVentas.vTituloDelivery " + VariablesVentas.vTituloDelivery);
    System.out.println("******************************************************");
*/
    vPrint.printLine(" SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto).trim(),65) + " " +
                     " Total Venta   S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
      vPrintArchivo.printLine(" SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto).trim(),65) + " " +
                     " Total Venta   S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
    vPrint.printLine(" REDO: " + pValRedondeo +
                     " CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                     " CAJA: " + VariablesCaja.vNumCajaImpreso +
                     " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
                     " VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);
      vPrintArchivo.printLine(" REDO: " + pValRedondeo +
                       " CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                       " CAJA: " + VariablesCaja.vNumCajaImpreso +
                       " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
                       " VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);
    vPrint.printLine(" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion  + FarmaPRNUtility.llenarBlancos(11) + VariablesVentas.vTituloDelivery ,true);
      vPrintArchivo.printLine(" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion  + FarmaPRNUtility.llenarBlancos(11) + VariablesVentas.vTituloDelivery ,true);
    /*dubilluz 2011.09.16*/
    if(VariablesCaja.vImprimeFideicomizo){
        String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
        if(lineas.length>0){
            for(int i=0;i<lineas.length;i++){
            vPrint.printLine(""+lineas[i].trim(),true);
            vPrintArchivo.printLine(""+lineas[i].trim(),true);
            }
        }
        else{
        vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
        vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
        }
    }
    /*FIN dubilluz 2011.09.16*/
          
    vPrint.deactivateCondensed();
    vPrint.endPrintService();
      vPrintArchivo.endPrintService();
      
    log.info("Fin al imprimir la boleta: " + pNumComprobante);
    VariablesCaja.vEstadoSinComprobanteImpreso="N";
    
      //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
      DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
      log.debug("Guardando fecha impresion cobro..."+pNumComprobante); 
  }               
                                        catch(SQLException sql)
                                              {
                                                //sql.printStackTrace();
                                                VariablesCaja.vEstadoSinComprobanteImpreso="S";
                                                System.out.println("Error de BD "+ sql.getMessage());
                                                
                                                  log.info("**** Fecha :"+ pFechaBD);
                                                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                                                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                                                  log.info("**** IP :" + FarmaVariables.vIpPc);
                                                  log.info("Error al imprimir la boleta : " + sql.getMessage());
                                                  log.error(null,sql);
                                                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                                                    enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
                                              }
                                              
                                                catch(Exception e){
                                                  VariablesCaja.vEstadoSinComprobanteImpreso="S";
                                                  log.info("**** Fecha :"+ pFechaBD);
                                                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                                                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                                                  log.info("**** IP :" + FarmaVariables.vIpPc);
                                                  log.info("Error al imprimir la boleta: "+e);
                                                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                                                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
                                              } 
                                    
                                    
                                    }
  
  } 

  private static void imprimeFactura(JDialog   pJDialog,
                                     String    pFechaBD,
                                     ArrayList pDetalleComprobante,
                                     String    pValTotalBruto,
                                     String    pValTotalNeto,
                                     String    pValTotalAfecto,
                                     String    pValTotalDcto,
                                     String    pValTotalIgv,
                                     String    pPorcIgv,
                                     String    pValRedondeo,
                                     String    pNumComprobante,
                                     String    pNomImpreso,
                                     String    pNumDocImpreso,
                                     String    pDirImpreso,
                                     String    pValTotalAhorro,
                                     String    pRuta,
                                     boolean   bol) throws Exception {
    System.out.println("IMPRIMIR FACTURA No : " + pNumComprobante);
    String indProdVirtual = "";
    
    //jcortez 06.07.09 Se verifica ruta 
   // if(bol) VariablesCaja.vRutaImpresora=pRuta;
        
    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    //FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt",false);
    FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora,false);
    
      //JCORTEZ 16.07.09 Se genera archivo linea por linea
      FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
      vPrintArchivo.startPrintService();
    

    System.out.println("Ruta : " + VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt");
      if ( !vPrint.startPrintService() ) {
                     VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                     log.info("**** Fecha :"+ pFechaBD);
                     log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                     log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                     log.info("**** IP :" + FarmaVariables.vIpPc);
                     log.info("ERROR DE IMPRESORA : No se pudo imprimir la factura");
          }
    
    
      else{
    
          try{
    String dia = pFechaBD.substring(0,2);
    String mesLetra=FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3,5)));
    String ano = pFechaBD.substring(6,10);
    String hora = pFechaBD.substring(11,19);
    vPrint.activateCondensed();
              
    if(VariablesPtoVenta.vIndDirMatriz){
        ArrayList lstDirecMatriz = FarmaUtility.splitString(VariablesPtoVenta.vDireccionMatriz, 32);
        
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(0).toString() ,true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(0).toString() ,true);
        
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(1).toString(),true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(1).toString(),true);
        
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + 
                        FarmaPRNUtility.alinearIzquierda( FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal ,37)+ 
                        lstDirecMatriz.get(2).toString() ,true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + 
                        FarmaPRNUtility.alinearIzquierda( FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal ,37)+ 
                        lstDirecMatriz.get(2).toString() ,true);
    }else{
        vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);  
        
        vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);   
        
        //JMIRANDA 22.08.2011 Cambio para verificar si imprime
        if(UtilityVentas.getIndImprimeCorrelativo()){
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) + "CORR." + VariablesCaja.vNumPedVta,true);
            vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) + "CORR." + VariablesCaja.vNumPedVta,true);
        }else{
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) ,true);
                vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) ,true);        
        }
    }
    
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),true);
    
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + pDirImpreso.trim(),true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + pDirImpreso.trim(),true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(12) + "     " + pNumDocImpreso.trim() ,true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(12) + "     " + pNumDocImpreso.trim() ,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano + "     " + hora  + FarmaPRNUtility.llenarBlancos(50) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano + "     " + hora  + FarmaPRNUtility.llenarBlancos(50) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
    vPrint.printLine(" ",true);
              vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
              vPrintArchivo.printLine(" ",true);
    int linea = 0;
    double pMontoOld = 0,pMontoNew = 0,pMontoDescuento = 0;
    
    
              System.err.println(""+VariablesVentas.vTipoPedido);          
              
    for (int i=0; i<pDetalleComprobante.size(); i++) {
        vPrint.printLine(" " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),38) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                           ,true
                        ); 
                        
        vPrintArchivo.printLine(" " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),38) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                           ,true
                        ); 

        
      linea += 1;
      indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
      //verifica que solo se imprima un producto virtual en el comprobante
      if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
      else
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    }

    if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
    {
      vPrint.printLine("", true);
        vPrintArchivo.printLine("", true);
      impresionInfoVirtual(vPrint,vPrintArchivo,
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                           VariablesCaja.vNumPedVta,//Se añadio el parametro
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto

      linea = linea + 4;
    }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        linea++;
    }

    //MODIFICADO POR DVELIZ 13.10.08
    //
     if(!VariablesVentas.vEsPedidoConvenio){
         if(pDetalleComprobante.size() < 10){
             for (int j=linea; j<=10; j++) { 
                 vPrint.printLine(" ",true);
                 vPrintArchivo.printLine(" ",true);
             }
         }  
     }else{
         for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  vPrint.printLine(" ",true);
     }
    //*************************************INFORMACION DEL CONVENIO*************************************************//
    //*******************************************INICIO************************************************************//

    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      try
      {
        System.out.println("****Imprimiendo... "+VariablesCaja.vNumPedVta);
        ArrayList aInfoPedConv = new ArrayList();
        DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

        for(int i=0; i<aInfoPedConv.size(); i++)
        {
          ArrayList registro = (ArrayList) aInfoPedConv.get(i);
         //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
         String Ind_Comp=((String)registro.get(8)).trim();
         if(Ind_Comp.equalsIgnoreCase("N")){
          System.out.println("registro "+registro);
          vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                           //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                           FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                           ,true);
                           
             vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                              //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                              FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                              ,true);                           
          /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
          String vCredDisp = ((String)registro.get(7)).trim();
          if(vCredDisp.equals(""))
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                             ,true);
              vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                               ,true);
          }else
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                             FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                             ,true);
            vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                               FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                               ,true);
          }
         }
        }

      }catch(SQLException sql)
      {
        //sql.printStackTrace();
        System.out.println("Error de BD "+ sql.getMessage());
          VariablesCaja.vEstadoSinComprobanteImpreso="S";      
          log.info("**** Fecha :"+ pFechaBD);
          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
          log.info("**** IP :" + FarmaVariables.vIpPc);
          log.info("Error al obtener Informacion Pedido Convenio: ");
          log.info("Error al imprimir la factura : " + sql.getMessage());
          log.error(null,sql);
          
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
      }
      
        catch(Exception e){
          VariablesCaja.vEstadoSinComprobanteImpreso="S";      
          log.info("**** Fecha :"+ pFechaBD);
          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
          log.info("**** IP :" + FarmaVariables.vIpPc);
          log.info("Error al obtener Informacion Pedido Convenio : ");
          log.info("Error al imprimir la factura: "+e);
          
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
      }
      
      
      
      

      //vPrint.printLine(" ",true);
    }else
    {
        //dveliz 13.10.08
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
    }
    //*********************************************FIN*************************************************************//
    //*************************************INFORMACION DEL CONVENIO***********************************************//
    
     //MODIFICADO POR DVELIZ 02.10.08
    //vPrint.printLine(" "+VariablesFidelizacion.vNomClienteImpr, true);
      
      
    //ERIOS 25.07.2008 imprime el monto ahorrado.
    double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
    if(auxTotalDcto > 0)
    {
     /* old
      vPrint.printLine(" UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);
        vPrintArchivo.printLine(" UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);
      */
		
        log.info("Imprimiendo Ahorro");
        
        //JCORTEZ 02.09.2009 Se muestra mensaje distinto si es fidelizado o no.
        String obtenerMensaje="";
        String indFidelizado="";
        log.info("Identificando cliente fidelizado");
        if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
            indFidelizado="S";
        }else 
         { indFidelizado="N"; }
        log.info("Fidelizado--> "+indFidelizado);    
        obtenerMensaje=obtenerMensaAhorro(pJDialog,indFidelizado);
         vPrint.printLine(""+obtenerMensaje+" "+" S/. "+pValTotalAhorro,true);
           vPrintArchivo.printLine(""+obtenerMensaje+" S/. "+pValTotalAhorro,true);
         /* vPrint.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);
             vPrintArchivo.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);*/
  
    }else
    {
      vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);
    }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
        vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
    }
    if( VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
        VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) )
    {
      VariablesVentas.vTituloDelivery = "" ;
    }else VariablesVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;

    //vPrint.printLine(" ",true);
    
             
    vPrint.printLine(" SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67),true);
    vPrintArchivo.printLine(" SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67),true);
    vPrint.printLine(" REDO:" + pValRedondeo +
                     " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                     " CAJA:" + VariablesCaja.vNumCajaImpreso +
                     " TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                     " VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso,true);
    vPrintArchivo.printLine(" REDO:" + pValRedondeo +
                               " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                               " CAJA:" + VariablesCaja.vNumCajaImpreso +
                               " TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                               " VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso,true);
    vPrint.printLine(" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesVentas.vTituloDelivery ,true);
    vPrintArchivo.printLine(" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesVentas.vTituloDelivery ,true);
    
             /* vPrintArchivo.printLine(" XXXXX ",true);
                        vPrint.printLine("XXXXX ",true);
                        vPrint.printLine("YYYYY",true);
                                  vPrintArchivo.printLine("YYYYY",true);*/
    //dubilluz
              if(!VariablesCaja.vImprimeFideicomizo){
        vPrintArchivo.printLine(" ",true);
                  vPrint.printLine(" ",true);
                  vPrint.printLine(" ",true);
                            vPrintArchivo.printLine(" ",true);
              }
              
    //vPrint.printLine(" ",true);
    vPrint.printLine("     " +
                     "00000" + FarmaPRNUtility.llenarBlancos(12) +
                     FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(10) +
                     FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(10) +
                     FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(10) +
                     FarmaPRNUtility.alinearDerecha(pPorcIgv,6) + FarmaPRNUtility.llenarBlancos(11) +
                     FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(8) +
                     "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
     vPrintArchivo.printLine("     " +
                               "00000" + FarmaPRNUtility.llenarBlancos(12) +
                               FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(10) +
                               FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(10) +
                               FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(10) +
                               FarmaPRNUtility.alinearDerecha(pPorcIgv,6) + FarmaPRNUtility.llenarBlancos(11) +
                               FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(8) +
                               "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
      /*dubilluz 2011.09.16*/
      
      if(VariablesCaja.vImprimeFideicomizo){
          String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
          if(lineas.length>0){
              for(int i=0;i<lineas.length;i++){
              vPrint.printLine(""+lineas[i].trim(),true);
              vPrintArchivo.printLine(""+lineas[i].trim(),true);
              }
          }
          else{
          vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
          vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
          }
      }
    
      /*FIN dubilluz 2011.09.16*/
     /* vPrintArchivo.printLine(" XXXXX ",true);
                              vPrint.printLine("XXXXX ",true);
                              vPrint.printLine("YYYYY",true);
                                        vPrintArchivo.printLine("YYYYY",true);*/
    vPrint.endPrintService();
     vPrintArchivo.endPrintService();
     
              //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
              DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
              log.debug("Guardando fecha impresion cobro..."+pNumComprobante); 
    
    log.info("Fin al imprimir la factura: " + pNumComprobante);
              
              VariablesCaja.vEstadoSinComprobanteImpreso="N";      
          }
  
       
         catch(Exception e){
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al imprimir Factura: " + e);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
                 
              }
          
        
     }
  }

  private static void imprimeFacturaGuia(JDialog   pJDialog,
                                         String    pFechaBD,
                                         ArrayList pDetalleComprobante,
                                         String    pValTotalBruto,
                                         String    pValTotalNeto,
                                         String    pValTotalAfecto,
                                         String    pValTotalDcto,
                                         String    pValTotalIgv,
                                         String    pPorcIgv,
                                         String    pValRedondeo,
                                         String    pNumComprobante,
                                         String    pNomImpreso,
                                         String    pNumDocImpreso,
                                         String    pDirImpreso,
                                         String    pRuta,
                                         boolean   bol) throws Exception {
    System.out.println("IMPRIMIR FACTURA No : " + pNumComprobante);

        //jcortez 06.07.09 Se verifica ruta 
      //  if(bol) VariablesCaja.vRutaImpresora=pRuta;

    //FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt",false);
    FarmaPrintService vPrint = new FarmaPrintService(66,VariablesCaja.vRutaImpresora,false);
    
      //JCORTEZ 16.07.09 Se genera archivo linea por linea
      FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
      vPrintArchivo.startPrintService();
      
    System.out.println("Ruta : " + VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt");
   // if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
   
      if ( !vPrint.startPrintService() ) {
                      VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                     log.info("**** Fecha :"+ pFechaBD);
                     log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                     log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                     log.info("**** IP :" + FarmaVariables.vIpPc);
                     log.info("Fin error de impresion: " + pNumComprobante);
                     log.info("ERROR DE IMPRESORA : No se pudo imprimir la factura Guia");
          }
      
      else{
          try{
          //JMIRANDA 15.12.09
            //ALMACENO LA VARIABLE PUNTO DE LLEGADA 
            String vPuntoPartidaLlegada = DBCaja.getPuntoPartidaLlegada();
              log.error("LLegada y Partida: "+vPuntoPartidaLlegada);
          String[] temp;         
          String delimiter = "¦";        
          temp = vPuntoPartidaLlegada.split(delimiter);                
             for(int i=0 ; i < temp.length ; i++){
               System.out.println(temp[i]+"\n");
               if(i==0)                   
                 VariablesCaja.vPuntoPartida = temp[i];
               if(i==1)
                 VariablesCaja.vPuntoLlegada =  temp[i];                   
             }
         if(VariablesCaja.vPuntoPartida.trim().equalsIgnoreCase("X") || 
            VariablesCaja.vPuntoLlegada.trim().equalsIgnoreCase("X")){
             VariablesCaja.vPuntoPartida = " ";   
             VariablesCaja.vPuntoLlegada = " ";
         }
          //VariablesCaja.vPuntoPartida = "abcdefghijklmnopqrstuvwxyzabcdefghijklm";
          //VariablesCaja.vPuntoPartida = "Av. 28 de Julio 1370 - Miraflores";
          //VariablesCaja.vPuntoLlegada = "1234567890123456780123456789012345678901234567890a2345678";
          //VariablesCaja.vPuntoLlegada = "Av. Proceres de la Independencia - San Juan de Lurigancho";    
                                       
         /*if(VariablesCaja.vPuntoLlegada.equalsIgnoreCase("X")){
             VariablesCaja.vPuntoLlegada = " ";      
         }   */  
              log.error("vPuntoPartida: "+ VariablesCaja.vPuntoPartida+ 
                        "  vPuntoLlegada: "+VariablesCaja.vPuntoLlegada);     
    String diaMesAno = pFechaBD.substring(0,10);
    String hora = pFechaBD.substring(11,19);
    vPrint.activateCondensed();          
              
    if(VariablesPtoVenta.vIndDirMatriz){
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(46)+ VariablesPtoVenta.vDireccionMatriz ,true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(46) + VariablesPtoVenta.vDireccionMatriz ,true);
    }else{
        vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);  
    }          
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(47) + "     " + pNumDocImpreso.trim() ,true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(47) + "     " + pNumDocImpreso.trim() ,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(2) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),false);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),false);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(2) + pDirImpreso.trim(),true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2) + pDirImpreso.trim(),true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
   //JMIRANDA 22.08.2011 Cambio para verificar si imprime
   if(UtilityVentas.getIndImprimeCorrelativo()){
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesCaja.vNumPedVta,false);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesCaja.vNumPedVta,false);
   }else{
       vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + " ",false);
             vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + " ",false);          
   }
       
    //vPrint.printLine(FarmaPRNUtility.llenarBlancos(38) + diaMesAno + "       " + hora,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(38) + diaMesAno + "       " + hora,true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(38) + diaMesAno + "       " + hora,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(28) + VariablesCaja.vValTipoCambioPedido,true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(28) + VariablesCaja.vValTipoCambioPedido,true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    /*vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);*/
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    /** JMIRANDA 15.12.09 COMENTADO PARA AGREGAR PUNTO DE PARTIDA Y LLEGADA A LA FACTURA POR VTA INSTITUCIONAL */
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(2)+
                     FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoPartida,40)+
                     "    "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoLlegada,50), true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2)+
                     FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoPartida,40)+
                     "    "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoLlegada,50), true);    
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true); 
    int linea = 0;
    double pMontoOld=0,pMontoNew=0,pMontoDescuento=0;
    for (int i=0; i<pDetalleComprobante.size(); i++) {
        vPrint.printLine("  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + " " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(15)).trim(),40) + " " +
                         //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),13) + "  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + FarmaPRNUtility.llenarBlancos(1) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim(),15) + " " +//nueva columna: numero de lote
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(14)).trim(),10) + " " +//nueva columna: FECHA VENCIMIENTO
                         
                        //--se cambio la logica de que precio mostrar para ventas a QS en base a un precio
                        //  y esto se mostrara en un descuento en soles
                        //  dubilluz 07.05.2009
                        /*
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                         * */ 
                        getDetallePrecio(VariablesVentas.vTipoPedido,pDetalleComprobante,i)
                         ,true
                        );
                        
        vPrintArchivo.printLine("  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + " " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(15)).trim(),40) + " " +
                         //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),13) + "  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + FarmaPRNUtility.llenarBlancos(1) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim(),15) + " " +//nueva columna: numero de lote
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(14)).trim(),10) + " " +//nueva columna: FECHA VENCIMIENTO
                         
                        //--se cambio la logica de que precio mostrar para ventas a QS en base a un precio
                        //  y esto se mostrara en un descuento en soles
                        //  dubilluz 07.05.2009
                        /*
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                         * */ 
                        getDetallePrecio(VariablesVentas.vTipoPedido,pDetalleComprobante,i)
                         ,true
                        );
                       
        if(VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
        {
           pMontoOld += FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,5));
           pMontoNew += FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,18));
        }      
        
      linea += 1;
    }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        linea++;
    }
    

    
      //MODIFICADO POR DVELIZ 13.10.08
      //for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  vPrint.printLine(" ",true);
    //  for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  vPrint.printLine(" ",true);
    //for (int k=0; k<=10; k++)  vPrint.printLine(" ",true);
    //MODIFICADO POR JMIRANDA 16.12.09        
    // TOTAL DE LINEAS - LINEAS IMPRESAS = LINEAS EN BLANCO
    //ConstantsPtoVenta.TOTAL_LINEAS_FACTURA_GUIA
         for (int z=0; z< (ConstantsPtoVenta.TOTAL_LINEAS_FACTURA_GUIA-linea);z++)
              vPrint.printLine(" ",true); 
              System.out.println("linea"+linea);    
    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
        vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
    }
    if( VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
        VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) )
    {
      VariablesVentas.vTituloDelivery = "" ;
    }else VariablesVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;

    //vPrint.printLine(" ",true);
        
    System.err.println("Viendo si da descuento...Institucional");
    if(VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
    {
        pMontoDescuento = pMontoNew - pMontoOld;
        if(pMontoDescuento>0){          
          System.err.println("Descuento S/. "+ pMontoDescuento);
          vPrint.printLine( FarmaPRNUtility.llenarBlancos(40)+FarmaPRNUtility.alinearDerecha("Total Dsto S/."+pMontoDescuento,80),true);
            vPrintArchivo.printLine( FarmaPRNUtility.llenarBlancos(40)+FarmaPRNUtility.alinearDerecha("Total Dsto S/."+pMontoDescuento,80),true);
        }
    }
              
    vPrint.printLine("  SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67),true);
          vPrintArchivo.printLine("  SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67),true);
    vPrint.printLine("  REDO:" + pValRedondeo +
                     "  CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                     "  CAJA:" + VariablesCaja.vNumCajaImpreso +
                     "  TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                     "  VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso,true);
    vPrintArchivo.printLine("  REDO:" + pValRedondeo +
                           "  CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                           "  CAJA:" + VariablesCaja.vNumCajaImpreso +
                           "  TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                           "  VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso,true);                     
    vPrint.printLine("  Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesVentas.vTituloDelivery ,true);
          vPrintArchivo.printLine("  Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesVentas.vTituloDelivery ,true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
                  vPrint.printLine(" ",true);
                  vPrint.printLine(" ",true);
                        vPrintArchivo.printLine(" ",true);
    /*
              if(!VariablesCaja.vImprimeFideicomizo){
          vPrintArchivo.printLine(" ",true);
                  vPrint.printLine(" ",true);
                  vPrint.printLine(" ",true);
                        vPrintArchivo.printLine(" ",true);
              }*/
    vPrint.printLine("     " +
                     FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(5) +
                     FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(45) +
                     FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(15) +
                     FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(17) +
                     FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
    vPrintArchivo.printLine("     " +
                           FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(5) +
                           FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(45) +
                           FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(15) +
                           FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(17) +
                           FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
          /*dubilluz 2011.09.16*/
          if(VariablesCaja.vImprimeFideicomizo){
              vPrint.printLine("",true);
              vPrint.printLine("",true);
              String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
              if(lineas.length>0){
                  for(int i=0;i<lineas.length;i++){
                  vPrint.printLine(""+lineas[i].trim(),true);
                  vPrintArchivo.printLine(""+lineas[i].trim(),true);
                  }
              }
              else{
              vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
              vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
              }
          }
          /*FIN dubilluz 2011.09.16*/          
    vPrint.endPrintService();
          vPrintArchivo.endPrintService();
          
          //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
          DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
          log.debug("Guardando fecha impresion cobro..."+pNumComprobante);           
    
    log.info("Fin al imprimir la factura-guia: " + pNumComprobante);
          VariablesCaja.vEstadoSinComprobanteImpreso="N";               
      }
        catch(Exception e){
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al imprimir la factura-guia: "+e);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
              }         
        
    }
  }

  public static void obtieneInfoVendedor()
  {
    ArrayList myArray = new ArrayList();
    try
    {
      DBCaja.obtenerInfoVendedor(myArray);
      if(myArray.size() == 0) return;
      VariablesCaja.vNomVendedorImpreso    = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesCaja.vApePatVendedorImpreso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
         } catch(SQLException sql){
      //sql.printStackTrace();
      log.error(null,sql);
    }
  }

  public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus)
  {
      long tmpT1,tmpT2;
      long tmpInicio,tmpFinal;
      log.debug("******PROCESO IMPRESION COMPROBANTES********");
      tmpInicio = System.currentTimeMillis();
      
  
     String mensCons="";
    try{
        
      String secImprLocal = "";
      String resultado = "" ;

        log.debug("VariablesVentas.vTip_Comp_Ped -->"+VariablesVentas.vTip_Comp_Ped);
        log.debug("VariablesCaja.vSecImprLocalBoleta -->"+VariablesCaja.vSecImprLocalBoleta);
        log.debug("VariablesCaja.vSecImprLocalTicket -->"+VariablesCaja.vSecImprLocalTicket);
        
        /*if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA))
        {
            if(!VariablesCaja.vSecImprLocalBoleta.equalsIgnoreCase("0"))//sin secuencial
             secImprLocal = VariablesCaja.vSecImprLocalBoleta;
            else{
             secImprLocal = VariablesCaja.vSecImprLocalTicket;
                     VariablesVentas.vTip_Comp_Ped=ConstantsVentas.TIPO_COMP_TICKET;
                 }
        }
        else if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
            //JCORTEZ 25.03.09
            //proximamente se tendra que configurar el ticket Factura
            secImprLocal = VariablesCaja.vSecImprLocalTicket;
        }*/
        //dubilluz 16.09.2011
        VariablesCaja.vImprimeFideicomizo = false;
        VariablesCaja.vCadenaFideicomizo = getMensajeFideicomizo();
        if(VariablesCaja.vCadenaFideicomizo.trim().length()>0) VariablesCaja.vImprimeFideicomizo = true;
        /**
         * @AUTHOR JCORTEZ
         * @SINCE 09.06.09
         * Se valida el tipo de comprobante para obtener el secuencialde impresora
         * */
        if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)||
            VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
             //JMIRANDA 23/07/09 posee Throws SQLException va enviar Error via email
             secImprLocal= DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);
         }
        else
        {
            if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA))
            {
                   // --Inicio
                   if (VariablesVentas.vTipoPedido.equals(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
                   {
                      log.debug("************Entro a institucional*******************");
                      tmpT1 = System.currentTimeMillis();
                       //JMIRANDA 23/07/09 POSEE THROWS EXCEPTION, ENVIA A ERROR X EMAIL 
                      secImprLocal = obtieneSecImprVtaInstitucional(ConstantsVentas.TIPO_COMP_FACTURA);
                      tmpT2 = System.currentTimeMillis();
                      log.debug("Tiempo 1: obtieneSecImprVtaInstitucional:"+(tmpT2 - tmpT1)+" milisegundos");
                      log.debug(".SecImprLocal"+secImprLocal);
                      
                      // --Inicio
                      if(secImprLocal.equalsIgnoreCase(""))
                      {
                        secImprLocal = VariablesCaja.vSecImprLocalFactura;
                        log.debug("secImprLocal Sin vta Institucional: " + secImprLocal);
                      }
                      else
                      {
                        FarmaUtility.showMessage(pJDialog, 
                                                  "Se va a imprimir un documento del tipo Venta Institucional \n " + 
                                                 "Cambie de hoja en la impresora de Reportes para proseguir", 
                                                  pObjectFocus);
                      }
                      // --Fin
                   }
                   else
                   {
                      secImprLocal = VariablesCaja.vSecImprLocalFactura;
                      log.debug("secImprLocal en Else " + secImprLocal);
                   }
                   // --Fin
                   log.debug("SecImpresion Local FINAL : "+ secImprLocal);
              }
              else
              {
                   if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_GUIA)){
                      secImprLocal = VariablesCaja.vSecImprLocalGuia;            
               }
             }
            }
        
        log.debug("Secuencial Impresora de Caja: "+secImprLocal);
        log.debug("VariablesCaja.vNumSecImpresionComprobantes: "+VariablesCaja.vNumSecImpresionComprobantes);
        tmpT1 = System.currentTimeMillis();
        //JMIRANDA 23/07/09 posee Throws SQLException
        resultado = DBCaja.verificaComprobantePago(secImprLocal,VariablesCaja.vNumSecImpresionComprobantes);
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 1: verificaComprobantePago:"+(tmpT2 - tmpT1)+" milisegundos");
        
      if(resultado.equalsIgnoreCase(ConstantsCaja.RESULTADO_COMPROBANTE_NO_EXITOSO))
      {
         FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, 
                                         "El pedido fue Cobrado pero no se imprimió Comprobante(s).\nIngrese a la opción de Reimpresión de Pedido.", 
                                         pObjectFocus);
                return;
      }
      else
      {
          FarmaUtility.liberarTransaccion();
      }
      
      //cambiando el estado de pedido al estado C -- que es estado IMPRESO y COBRADO
      tmpT1 = System.currentTimeMillis();
        //JMIRANDA 23/07/09 posee Throws SQLException 
       
      actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
      
      //JCORTEZ  07.08.09 por motivo de pedido delivery local
      if(VariablesVentas.vEsPedidoDelivery)
      actualizarDatosDelivery(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
      
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 2: Actualiza Estado de Pedido:"+(tmpT2 - tmpT1)+" milisegundos");
        
      log.debug("secImprLocal procesoImpresionComprobante : " + secImprLocal);

      log.debug("****imprimi Cupones****");
      VariablesCaja.vNumCuponesImpresos = 0;
      tmpT1 = System.currentTimeMillis();
      
      //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
      int cantidadCupones = imprimeCupones(pJDialog);
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 3: Obtiene Cantidad de Cupones a Imprimir:"+(tmpT2 - tmpT1)+" milisegundos");
      log.debug("Numero de Cupones a Imprimir:"+cantidadCupones );
      if(cantidadCupones>0){
          VariablesCaja.vNumCuponesImpresos = cantidadCupones;
      }
        
      /**jcallo modificando add lista de numeros de comprobante**/
      ArrayList listaNumCompro = new ArrayList(VariablesCaja.vNumSecImpresionComprobantes);
      log.error("Sec.Impresion..VariablesCaja.vNumSecImpresionComprobantes :" + VariablesCaja.vNumSecImpresionComprobantes);
      
      tmpT1 = System.currentTimeMillis();  
      for(int i=1; i<=VariablesCaja.vNumSecImpresionComprobantes; i++)
      {
        //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email  
        VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus);
        log.debug(""+i+")VariablesCaja.vNumCompImprimir : " + VariablesCaja.vNumCompImprimir);
        if(VariablesCaja.vNumCompImprimir.equalsIgnoreCase(""))
        {
          FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog,
                                   "El pedido fue Cobrado pero no se pudo determinar el Numero de Comprobante. Verifique!!!",
                                   pObjectFocus);
          return;
        }
        
        //obteniendo el detalle del comprobante a imprimir
        //el mensaje de error se encuentra dentro del mismo metodo, tb se hace rollback dentro del mismo metodo
        // T4
           //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
        if(!obtieneDetalleImprComp(pJDialog, String.valueOf(i), pObjectFocus))
        {
        	FarmaUtility.liberarTransaccion();
        	FarmaUtility.showMessage(pJDialog,
                                         "El pedido fue Cobrado pero no se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",
                                         pObjectFocus);
                return;
        }
        
        
        String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i-1))).trim();
        log.debug("Num de SecCompPago : " + secCompPago);
        //obtiene el total del comprobante a imprimir
        //muestra el mensaje de error dentro del mismo metodo, tb hace rollback dentro del mismo metodo
        // T5  
            //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
        if(!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus)){
        	FarmaUtility.liberarTransaccion();
        	FarmaUtility.showMessage(pJDialog, "El pedido fue Cobrado pero no se pudo determinar los Totales del Comprobante. Verifique!!!.", pObjectFocus);
                return;
        }
        
        // T6  
          //JMIRANDA 23/07/09 *Posee try-catch interno. Envia Error via Email
        actualizaComprobanteImpreso(secCompPago, VariablesVentas.vTip_Comp_Ped, VariablesCaja.vNumCompImprimir);
        
        // T7  
          
        actualizaNumComp_Impresora(secImprLocal);

        //agregando los numero de comprobantes
        listaNumCompro.add(VariablesCaja.vNumCompImprimir);

      }
      
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 8: Agrupo producto y Comprobantes:"+(tmpT2 - tmpT1)+" milisegundos");        
      FarmaUtility.aceptarTransaccion();
        
      log.debug("Imprimiendo comprobantes ... ");
      tmpT1 = System.currentTimeMillis();
      for(int i=0; i<listaNumCompro.size() ;i++)
      {
	log.debug("listaNumCompro("+i+")"+i+": "+listaNumCompro.get(i).toString());
	log.debug("listaNumCompro("+i+")"+i+": "+listaNumCompro.get(i).toString());			
          
            //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email   
         if(!obtieneDetalleImprComp(pJDialog, String.valueOf(i+1), pObjectFocus))
         {
         	FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog,
                                         "No se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",
                                         pObjectFocus);
         	return;
         }
       
         String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i))).trim();
         log.debug("secCompPago : " + secCompPago);
            //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
         if(!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus))
         {
	    FarmaUtility.liberarTransaccion();
	    FarmaUtility.showMessage(pJDialog, "No se pudo determinar los Totales del Comprobante. Verifique!!!.", pObjectFocus);
	    return;
	 }
         
         tmpT1 = System.currentTimeMillis();
            //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
         VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());
         tmpT2 = System.currentTimeMillis();
         log.debug("Tiempo 9: Obtiene Ruta Impresora:"+(tmpT2 - tmpT1)+" milisegundos");        
         tmpT1 = System.currentTimeMillis();
         
         /**
          * @author JCORTEZ
          * @since  09.06.09
          * Se valida relacion paquina impresora ticket
          * */
          
          if(VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)||
          VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_BOLETA)){
          System.out.println("Validando IP");
              //FarmaVariables.vIpPc = FarmaUtility.getHostAddress();
              //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
              System.out.println(FarmaVariables.vIpPc+" /"+VariablesVentas.vTip_Comp_Ped);
            if(!validaImpresioPorIP(FarmaVariables.vIpPc,VariablesVentas.vTip_Comp_Ped,pJDialog,pObjectFocus)){
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, "La IP no cuenta con una impresora asignada. Verifique!!!.", pObjectFocus);
                return;            
            }
          }
            //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
         imprimeComprobantePago( pJDialog,
                                 VariablesCaja.vArrayList_DetalleImpr,
                                 VariablesCaja.vArrayList_TotalesComp,
                                 VariablesVentas.vTip_Comp_Ped,
                                 listaNumCompro.get(i).toString()
                                );
          FarmaUtility.aceptarTransaccion();
         tmpT2 = System.currentTimeMillis();
         log.debug("Tiempo 10: Imprime Comprobante:"+(tmpT2 - tmpT1)+" milisegundos"); 
         
        }
        

        log.debug("FIN imprimiendo comprobantes ... ");
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 11: Fin de Impresion de Comprobantes:"+(tmpT2 - tmpT1)+" milisegundos");                
        
        System.out.println("************PEDIDO DELIVERY*********"+VariablesVentas.vEsPedidoDelivery);
        //JCORTEZ 07.08.09
        if(VariablesVentas.vEsPedidoDelivery)
        imprimeDatosDeliveryLocal(pJDialog,VariablesCaja.vNumPedVta); 
      
      //ERIOS 09.05.2008 Se manda a imprimir consejos.
      //JCALLO 19.12.2008
      //este metodo tiene su propio rollback y commit, pero si fallara la impresion de consejos 
      //aparentemente no haria roolback...falta revisar o probar el caso, ya que se quedaria bloqueado
      log.debug("imprimiendo consejos ... ");
      // T12  
      //JMIRANDA 23/07/09
      imprimeConsejos(pJDialog);
      log.debug("FIN imprimiendo consejos ");
       
      //mfajardo -imprime mensaje campana- 13.04.2009
      log.debug("imprimiendo Mensaje de Campana"); 
        //JMIRANDA 23/07/09
      imprimeMensajeCampana(pJDialog,VariablesVentas.vNum_Ped_Vta);
      log.debug("FIN imprimiendo Mensaje de Campana");
      //mfajardo FIN
      
      //JCORTEZ 11.06.2008 Se imprimi cierto datos de la comanda
      log.debug("VariablesCaja.vIndDeliveryAutomatico :"+VariablesCaja.vIndDeliveryAutomatico);
      if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")){
          //JMIRANDA 23/07/09 POSEE SQLEXCEPTION
        String vNumPedDely = DBCaja.obtieneNumPedDelivery(VariablesCaja.vNumPedVta);
        log.debug("vNumPedDely XXX:"+vNumPedDely);
        tmpT1 = System.currentTimeMillis();
          //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
        imprimeDatosDelivery(pJDialog,vNumPedDely);
        tmpT2 = System.currentTimeMillis();
        log.debug("Tiempo 15: Imprimi datos de Delivery:"+(tmpT2 - tmpT1)+" milisegundos");
      }
      
      if(consejo){
       mensCons="\nRecoger Consejo.";
      }

      //JCALLO 19.12.2008
      //se manda imprimir Record Campanias Acumuladas del cliente
      tmpT1 = System.currentTimeMillis();
      String DniClienteImpRecord = obtenerDniPedidoAcumulaVenta(VariablesCaja.vNumPedVta);
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 16: Obtien Dni Pedido Acumulada tu Compra.:"+(tmpT2 - tmpT1)+" milisegundos");
      log.debug("dni del cliente a imprimir record : "+DniClienteImpRecord);
      
      if(DniClienteImpRecord.trim().length()>0)
      {
         log.debug("imprimiendo record de compras, y muestra cuanto le falta comprar para ganar el premio ... ");
         //viendo si tiene indicador linea matriz 
         /*
         if(VariablesCaja.vIndLineaMatriz.length()<1)
         {  //quiere decir que no se validado aun el indicador de linea en matriz
            tmpT1 = System.currentTimeMillis();
            VariablesCaja.vIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            tmpT2 = System.currentTimeMillis();
            log.debug("Tiempo 17: Obtiene Ind Conn MAtriz:"+(tmpT2 - tmpT1)+" milisegundos");
         }
         */
         VariablesCaja.vIndLineaMatriz = FarmaConstants.INDICADOR_N;
    	 if(VariablesCaja.vIndLineaMatriz.trim().equals(FarmaConstants.INDICADOR_S))
         {
    	    tmpT1 = System.currentTimeMillis();
            imprimeUnidRestCampXCliente(pJDialog,DniClienteImpRecord.trim(),VariablesCaja.vNumPedVta);
    	    tmpT2 = System.currentTimeMillis();
    	    log.debug("Tiempo 18: Obtiene Unidades Campa x Cliente matriz:"+(tmpT2 - tmpT1)+" milisegundos");
    	 }
         else
         {
            log.debug("no hay linea solo mostrar cuanto acumulo con la venta");
    	    tmpT1 = System.currentTimeMillis();
            imprimirUnidAcumCampXCliente(pJDialog,DniClienteImpRecord.trim(), VariablesCaja.vNumPedVta);
    	    tmpT2 = System.currentTimeMillis();
    	    log.debug("Tiempo 19: OObtiene unidades Camp.xCliente localmente:"+(tmpT2 - tmpT1)+" milisegundos");
    	 }
         
    	 log.debug("FIN imprimiendo Record Campanias Acumuladas del cliente ... ");
      }
        
      //JCALLO 19.12.2008 fin de imprimir Record Campanias Acumuladas del cliente      
     
        if(!VariablesCaja.vIndPedidoConProdVirtual) {
          //JMIRANDA 24/07/09 VERIFICA IMPRESION COMPROBANTE  
          if(VariablesCaja.vEstadoSinComprobanteImpreso.equalsIgnoreCase("N")){            
              //"Comprobantes Impresos conA Exito\n"+  
            if(cantidadCupones>0)
            {
               tmpFinal = System.currentTimeMillis();
               log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:"+(tmpFinal-tmpInicio)+" milisegundos");
               FarmaUtility.showMessage(pJDialog,"Pedido Cobrado con éxito. \n" +
                         "Comprobantes Impresos con éxito\n"+
                         "Favor de recoger "+cantidadCupones+" cupones"+mensCons
                         ,pObjectFocus);                 
            }
            else
            {//"Comprobantes Impresos conB Exito
               tmpFinal = System.currentTimeMillis();
               log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:"+(tmpFinal-tmpInicio)+" milisegundos");
               FarmaUtility.showMessage(pJDialog,"Pedido Cobrado con éxito. \n" +
                               "Comprobantes Impresos con éxito "+mensCons,pObjectFocus);                
            }
          }
          else{
              if(cantidadCupones>0){
                  tmpFinal = System.currentTimeMillis();
                  log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:"+(tmpFinal-tmpInicio)+" milisegundos");
                  FarmaUtility.showMessage(pJDialog,"Pedido Cobrado con éxito." + 
                             "\nFavor de recoger "+cantidadCupones+" cupones"+mensCons +
                             "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora: "+VariablesCaja.vRutaImpresora+
                             "\nReimprima Comprobante, Correlativo :"+ VariablesCaja.vNumPedVta ,pObjectFocus);  
  
              }
              else{
                  tmpFinal = System.currentTimeMillis();
                  log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:"+(tmpFinal-tmpInicio)+" milisegundos");
                  FarmaUtility.showMessage(pJDialog,"Pedido Cobrado con éxito." +                              
                             "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora: "+VariablesCaja.vRutaImpresora+
                             "\nReimprima Comprobante, Correlativo :"+ VariablesCaja.vNumPedVta ,pObjectFocus);
               
              }
             
          }
        }
        
      VariablesCaja.vNumCuponesImpresos = 0;
      mensCons="";
      consejo=false;
      
      
        
    } catch(SQLException sql){
      FarmaUtility.liberarTransaccion();
      log.error(null,sql);
      FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir los Comprobantes del Pedido.\n" + sql,pObjectFocus);
      
      //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime       
      enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta_Anul);
            
    } catch(Exception e){
      FarmaUtility.liberarTransaccion();
      log.error(null,e);      
      FarmaUtility.showMessage(pJDialog, "Error en la Aplicacion al Imprimir los Comprobantes del Pedido.\n" + e,pObjectFocus);
      
      //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime 
      enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta_Anul);
      
    }
  } 

  private static String obtieneSecImprVtaInstitucional(String pTipComp)  
  {
    
    String secImprVtaInst = "";
    ArrayList myArray = new ArrayList();
    
    try
    {
      DBCaja.obtieneSecuenciaImprVtaInstitucional(myArray, pTipComp);
      if(myArray.size() == 0 || myArray.size() > 1)
      {
        return secImprVtaInst;
      }
      secImprVtaInst = FarmaUtility.getValueFieldArrayList(myArray,0,0);
      System.out.println("secImprVtaInst : " + secImprVtaInst);
      return secImprVtaInst;
    
    } catch(SQLException sql)
    {
      secImprVtaInst = "";
      //sql.printStackTrace();
      
      log.error(null,sql);
        //JMIRANDA 23/07/09 ENVIA ERROR X CORREO
        enviaErrorCorreoPorDB(sql.toString(),null);  
      return secImprVtaInst;
      
    }

  }

  private static String obtieneNumCompPago_ForUpdate(JDialog pJDialog, String pSecImprLocal, Object pObjectFocus)
  {
    String numCompPago = "";
    ArrayList myArray = new ArrayList();
    try
    {
      /*if(VariablesCaja.vNumPedVta_Anul.trim().length()>1)
      DBCaja.obtieneNumCompTip_ForUpdate(myArray, pSecImprLocal,VariablesCaja.vNumPedVta_Anul);
      else*/
      DBCaja.obtieneNumComp_ForUpdate(myArray, pSecImprLocal);
      
        System.out.println("VariablesVentas.vTip_Comp_Ped JCORTEZ : " + VariablesVentas.vTip_Comp_Ped);
        System.out.println("VariablesCaja.vNumPedVta JCORTEZ : " + VariablesCaja.vNumPedVta);
        
      if(myArray.size() == 0)
      {
        return numCompPago;
      }
      numCompPago = ((String)((ArrayList)myArray.get(0)).get(0)).trim() + "" +
                    ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      System.out.println("numCompPago : " + numCompPago);
      return numCompPago;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      numCompPago = "";
      FarmaUtility.showMessage(pJDialog,"Error al validar Agrupacion de Comprobante.",pObjectFocus);
      //sql.printStackTrace();
      log.error(null,sql);
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                   enviaErrorCorreoPorDB(sql.toString().toString(),null);
      return numCompPago;
    }
  }

  private static boolean obtieneDetalleImprComp(JDialog pJDialog, String pSecGrupoImpr, Object pObjectFocus)
  {
    VariablesCaja.vArrayList_DetalleImpr = new ArrayList();
    boolean  valor = true;
    long tmpT1,tmpT2;
    tmpT1 = System.currentTimeMillis();
    try
    {
      DBCaja.obtieneInfoDetalleImpresion(VariablesCaja.vArrayList_DetalleImpr, pSecGrupoImpr);
      if(VariablesCaja.vArrayList_DetalleImpr.size() == 0)
      {
    	//JCALLO 19.12.2008 , debido a que no se hacia rollback si no encontraba el detalle del pedido
    	FarmaUtility.liberarTransaccion();//JCALLO 19.12.2008
        FarmaUtility.showMessage(pJDialog,
                                 "No se pudo determinar el detalle del Pedido. Verifique!!!.",
                                 pObjectFocus);
        valor = false;
      }
      System.err.println("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr.size());
      valor = true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al obtener Detalle de Impresion de Comprobante.",pObjectFocus);
      log.info("Error al obtener Detalle de Impresion de Comprobante imprimir");
      log.error(null,sql);
      valor =false;
      
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
           enviaErrorCorreoPorDB(sql.toString(),null);     
    }
    
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo 4: Det.Comp Pago:"+(tmpT2 - tmpT1)+" milisegundos");
          
    
    return valor;
  } 

  public static boolean obtieneTotalesComprobante(JDialog pJDialog, String pSecCompPago, Object pObjectFocus)
  {
    VariablesCaja.vArrayList_TotalesComp = new ArrayList();
    boolean valor = false;
    long tmpT1,tmpT2;
    tmpT1 = System.currentTimeMillis();
    try
    {
      DBCaja.obtieneInfoTotalesComprobante(VariablesCaja.vArrayList_TotalesComp, pSecCompPago);
      if(VariablesCaja.vArrayList_TotalesComp.size() == 0)
      {
    	//JCALLO 19.12.2008 , debido a que no se hacia rollback si no encontraba el detalle del pedido
    	FarmaUtility.liberarTransaccion();//JCALLO 19.12.2008
        FarmaUtility.showMessage(pJDialog,"No se pudo determinar los Totales del Comprobante. Verifique!!!.",pObjectFocus);
        valor = false;
      }
      log.debug("VariablesCaja.vArrayList_TotalesComp : " + VariablesCaja.vArrayList_TotalesComp.size());
      valor = true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al obtener Totales de Comprobante.",pObjectFocus);
      log.error(null,sql);
      valor = false;
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
          enviaErrorCorreoPorDB(sql.toString(),null);
      
    }
    
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo 5:Calculo de Total de los Comprobantes:"+(tmpT2 - tmpT1)+" milisegundos");
    
    return valor;
      
  }

  public static void actualizaComprobanteImpreso(String pSecCompPago,
                                                  String pTipCompPago,
                                                  String pNumCompPago) throws SQLException
  {
      long tmpT1,tmpT2;
      
      tmpT1 = System.currentTimeMillis();
        try {
            DBCaja.actualizaComprobanteImpreso(pSecCompPago, pTipCompPago, 
                                               pNumCompPago);
        } catch (SQLException sql) {
            //sql.printStackTrace();
            System.out.println("Error de BD " + sql.getMessage());
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumCompPago);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("ERROR DE ACTUALIZAR COMPROBANTE IMPRESO : ");
            log.error(null, sql);
            
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
              enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
            
        } catch (Exception e) {
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumCompPago);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("ERROR DE ACTUALIZAR COMPROBANTE IMPRESO : " + e);
            
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
              enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
        }
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 6: Actualiza Comprobante Impreso:"+(tmpT2 - tmpT1)+" milisegundos");
      

  }

  private static void actualizaNumComp_Impresora(String pSecImprLocal) throws SQLException
  {
    long tmpT1,tmpT2;
    tmpT1 = System.currentTimeMillis();
    DBCaja.actualizaNumComp_Impresora(pSecImprLocal);
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo 7: Actualiza Comprobante Impreso:"+(tmpT2 - tmpT1)+" milisegundos");
  }

  private static String obtieneRutaImpresora(String pSecImprLocal) throws SQLException
  {
    return DBCaja.obtieneRutaImpresoraVenta(pSecImprLocal);
  }

  public static void actualizaEstadoPedido(String pNumPedVta, String pEstPedVta) throws SQLException
  {
    DBCaja.actualizaEstadoPedido(pNumPedVta, pEstPedVta);
  }
  
  /**
   * @AUTHOR  JCORTEZ
   * @SINCE  07.08.09
   * */
    private static void actualizarDatosDelivery(String pNumPedVta, String pEstPedVta) throws SQLException
    {
    
      DBCaja.actualizaDatosDelivery(pNumPedVta, pEstPedVta,
                    VariablesDelivery.vCodCli,VariablesDelivery.vNombreCliente,
                    VariablesDelivery.vNumeroTelefonoCabecera,VariablesDelivery.vDireccion,
                    VariablesDelivery.vNumeroDocumento);
    }

  public static boolean verificaEstadoPedido(JDialog pJDialog, String pNumPedVta, String estadoAEvaluar, Object pObjectFocus)
  {
    String estadoPed = "";
    estadoPed = obtieneEstadoPedido(pJDialog, pNumPedVta);
    log.debug("Estado de Pedido:" + estadoPed);
    if(estadoAEvaluar.equalsIgnoreCase(estadoPed)) return true;
    //dubilluz 13.10.2011 bloqueo NO SE DEBE LIBERAR DEBIDO A Q YA EXISTE UN BLOQUEO DE STOCK DE PRODUCTOS.
    //FarmaUtility.liberarTransaccion();
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_COBRADO))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente de cobro.Verifique!!!", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_SIN_COMPROBANTE_IMPRESO))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido fue Cobrado pero no imprimio Comprobante(s).\nIngrese a la opcion de Reimpresion de Pedido.", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_ANULADO))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido se encuentra Anulado. Verifique!!!", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_PENDIENTE))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido se encuentra pendiente de cobro. Verifique!!!", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(pJDialog, "No se pudo determinar el estado del pedido. Verifique!!!", pObjectFocus);
      return false;
    }
    return true;
  }

  public static void obtieneInfoCajero(String pSecMovCaja)
  {
    ArrayList myArray = new ArrayList();
    try
    {
      DBCaja.obtenerInfoCajero(myArray, pSecMovCaja);
      if(myArray.size() == 0) return;
      VariablesCaja.vNumCajaImpreso = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesCaja.vNumTurnoCajaImpreso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      VariablesCaja.vNomCajeroImpreso = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      VariablesCaja.vApePatCajeroImpreso = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
    } catch(SQLException sql){
      //sql.printStackTrace();
      log.error(null,sql);
    }
  }

  public static boolean validaAgrupacionComprobante(JDialog pJDialog, Object pObjectFocus)
  {
    ArrayList myArray = new ArrayList();
    String secCompPago = "";
    VariablesCaja.vArrayList_SecCompPago = new ArrayList();
    try
    {
      DBCaja.obtieneInfoDetalleAgrupacion(myArray);
      if(myArray.size() == 0)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"No se pudo determinar la agrupacion de Impresion",pObjectFocus);
        return false;
      }
      if(myArray.size() != VariablesCaja.vNumSecImpresionComprobantes)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"Hubo error al agrupar el detalle de Impresion.",pObjectFocus);
        return false;
      }
      for(int j=0; j<myArray.size(); j++)
      {
        secCompPago = ((String)((ArrayList)myArray.get(j)).get(1)).trim();
        if(secCompPago.equalsIgnoreCase(""))
        {
          FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog,"Hubo error al obtener el Secuencial del Comprobante.",pObjectFocus);
          return false;
        }
        VariablesCaja.vArrayList_SecCompPago.add(secCompPago);
      }
      System.out.println("VariablesCaja.vArrayList_SecCompPago : " + VariablesCaja.vArrayList_SecCompPago);
      return true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al validar Agrupacion de Comprobante.",pObjectFocus);
      //sql.printStackTrace();
      log.error(null,sql);
      return false;
    }
  }

  //procesoAsignacionComprobante
  public static void procesoAsignacionComprobante(JDialog pJDialog, Object pObjectFocus) throws SQLException
  {

      String secImprLocal = "";
        secImprLocal = ConstantsCaja.SEC_IMPR_LOCAL_NOTA_CREDITO;
      System.out.println("secImprLocal procesoAsignacionComprobante: " + secImprLocal);
      for(int i=1; i<=VariablesCaja.vNumSecImpresionComprobantes; i++)
      {
        VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus);
        if(VariablesCaja.vNumCompImprimir.equalsIgnoreCase(""))
        {
          FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog, "No se pudo determinar el Numero de Comprobante. Verifique!!!", pObjectFocus);
          return;
        }
        //if(!obtieneDetalleImprComp(pJDialog, String.valueOf(i), pObjectFocus)) return;
        String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i-1))).trim();
        System.out.println("secCompPago : " + secCompPago);
        //if(!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus)) return;
        //VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal);
        actualizaComprobanteImpreso(secCompPago, VariablesVentas.vTip_Comp_Ped, VariablesCaja.vNumCompImprimir);
        actualizaNumComp_Impresora(secImprLocal);
      }
      actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
      //FarmaUtility.aceptarTransaccion();
      //FarmaUtility.showMessage(pJDialog, "Comprobantes Impresos con Exito",pObjectFocus);

  }

  public static void verificaPedidosPendientes(JDialog pJDialog)
  {
    int cantPedidosPendientes = 0;
    try
    {
      cantPedidosPendientes = DBCaja.cantidadPedidosPendAnulMasivo();
      if(cantPedidosPendientes == 0) return;
      FarmaUtility.showMessage(pJDialog, "Existen Pedidos Pendientes de cobro con mas de " + FarmaVariables.vMinutosPedidosPendientes + " minutos.\nEstos pedidos seran anulados.", null);
      //DBCaja.anulaPedidosPendientesMasivo(); --antes - ASOSA, 12.07.2010
        DBCaja.anulaPedidosPendientesMasivo_02(); //ASOSA, 12.07.2010
      FarmaUtility.aceptarTransaccion();
    } catch(SQLException sql){
      FarmaUtility.liberarTransaccion();
      //sql.printStackTrace();
      log.error(null,sql);
    }
  }

  public static void imprimePrueba() throws Exception
  {
    FarmaPrintService vPrint = new FarmaPrintService(10,VariablesCaja.vRutaImpresora,false);
    //FarmaPrintService vPrint = new FarmaPrintService(10,"C:\\ImpresoraReporte.txt",false);
    if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");

    String pFechaBaseDatos = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
    vPrint.activateCondensed();
    String dia = pFechaBaseDatos.substring(0,2);
    String mesLetra=FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3,5)));
    String ano = pFechaBaseDatos.substring(6,10);
    String hora = pFechaBaseDatos.substring(11,19);

    vPrint.activateCondensed();
    vPrint.printLine("",true);
    vPrint.printLine("LOCAL: " + FarmaVariables.vDescCortaLocal,true);
    vPrint.printLine("FECHA: " + dia + " " + mesLetra + " " + ano + "  " + hora,true);
    vPrint.printLine("IMPRESORA - SERIE: " + VariablesCaja.vTipComp,true);
    vPrint.printLine("USUARIO: " + FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu + ", " + FarmaVariables.vNomUsu,true);
    vPrint.printLine("CAJA - TURNO: " + VariablesCaja.vNumCaja + " - " + VariablesCaja.vNumTurnoCaja,true);
    vPrint.printLine(" ",true);
    vPrint.deactivateCondensed();

    vPrint.printCondensed("        PRUEBA DE IMPRESORA        ",true);

    vPrint.endPrintService();
  }

  /**
   * Metodo que procesa la venta de tarjetas virtuales.
   * Este metodo se conectara con el proveedor de servicios a traves de su interfase.
   * Creado x LMesia 12/01/2007
   */
  public static void procesaVentaProductoVirtual(JDialog pDialog, Object pObjectFocus) throws SQLException, Exception
  {
    //colocaVariablesVirtuales();
    colocaVariablesVirtualesBprepaid();
    if(VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA))
    {
      System.out.println("entro a venta de recarga virtual");
      obtieneInfoRecargaVirtualBprepaid();
      //obtieneInfoRecargaVirtual();
      if(!validaHostPuertoProveedor())
      {
        limpiaInfoTransaccionTarjVirtuales();
        throw new Exception("Error al obtener host y puerto de Navsat para recarga automatica");
      }
      /* 27.09.2007 ERIOS Se cambio de proveedor (Brightstar). */
      //ventaRecargaVirtualBprepaid();
      //ventaRecargaVirtual();
      /* 14.12.2007 ERIOS Se utliza el metodo generico. */
      ventaRecargaVirtualTX();
      //captura el error de conexion cuando los valores son nulos
      //16.11.2007  dubilluz modificado
      if(VariablesVirtual.respuestaTXBean.getCodigoRespuesta()==null)
      { 
        throw new Exception("Hubo un error con la conexion. Intentarlo mas tarde.");
      }
      //if(VariablesVirtual.respuestaTXBean.getCodigoRespuesta()!=null)
      colocaInfoRecargaVirtualBprepaid();
      
      //Mostramos el mensaje de respuesta del proveedor
      //05.12.2007  dubilluz  modificacion
      if(!validaCodigoRespuestaTransaccion())
      {
         throw new Exception("Error al realizar la transaccion con el proveedor.\n" + 
                                         VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + " - " + VariablesVirtual.respuestaTXBean.getDescripcion());
      }    

      
    } else
    {
      System.out.println("entro a venta de tarjeta virtual");
      obtieneInfoRecargaVirtualBprepaid();
      //obtieneInfoTarjetaVirtual();
      if(!validaHostPuertoProveedor())
      {
        limpiaInfoTransaccionTarjVirtuales();
        throw new Exception("Error al obtener host y puerto de Navsat para tarjeta virtual");
      }
      /* 27.09.2007 ERIOS Se cambio de proveedor (Brightstar). */
      //ventaTarjetaVirtualBprepaid();
      //ventaTarjetaVirtual();
      /* 14.12.2007 ERIOS Se utiliza el metodo generico. */
      ventaTarjetaVirtualTX();
      colocaInfoTransaccionVirtualBprepaid();
    }
    //colocaInfoTransaccionVirtualBprepaid();
    //colocaInfoTransaccionVitual();
    actualizaInfoPedidoVirtual(pDialog);
    //throw new Exception("NO Error al procesar el pedido virtual - Para prueba");
}

  /**
   * Valida el codigo de respuesta
   * @author dubilluz
   * @since  05.12.2007
   */
  private static boolean validaCodigoRespuestaTransaccion() 
  {
    boolean result = false;
    System.out.println("VariablesVirtual.vCodigoRespuesta 1" + VariablesVirtual.vCodigoRespuesta);
    /*  System.out.println( 
                               "Cod resp "+VariablesVirtual.vCodigoRespuesta+".\n" +
                               " Const " + ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL + " ");*/
    if(VariablesVirtual.vCodigoRespuesta.equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL))
      result = true;
      
    return result;
  }
  
  /**
   *
   * @throws SQLException
   * @deprecated
   */
  private static void colocaVariablesVirtuales() throws SQLException
  {
    /*VariablesVirtual.vCodigoComercio = FarmaUtility.completeWithSymbol(FarmaVariables.vNuRucCia.substring(0,6), 9, "0", "I") + FarmaVariables.vCodLocal;

    //VariablesVirtual.vTipoTarjeta = FarmaUtility.completeWithSymbol(VariablesCaja.vCodProd, 8, "0", "I");//preguntar que info va aca
    VariablesVirtual.vTipoTarjeta = VariablesCaja.vTipoTarjeta;
    VariablesVirtual.vMonto = VariablesCaja.vPrecioProdVirtual.substring(0, VariablesCaja.vPrecioProdVirtual.indexOf(".")) +
                              VariablesCaja.vPrecioProdVirtual.substring(VariablesCaja.vPrecioProdVirtual.indexOf(".") + 1);
    VariablesVirtual.vNumTerminal = FarmaUtility.completeWithSymbol(FarmaVariables.vCodLocal, 4, "0", "I");
    VariablesVirtual.vNumSerie = FarmaVariables.vNuRucCia.substring(0,5) + FarmaVariables.vCodLocal;
    VariablesVirtual.vNumTrace = obtieneNumeroTrace();//preguntar que info va aca
    VariablesVirtual.vNumeroCelular = VariablesCaja.vNumeroCelular;
    VariablesVirtual.vCodigoProv = VariablesCaja.vCodigoProv;
    VariablesVirtual.vNumTraceOriginal = VariablesCaja.vNumeroTraceOriginal;
    VariablesVirtual.vCodAprobacionOriginal = VariablesCaja.vCodAprobacionOriginal;
    System.out.println("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
    System.out.println("VariablesVirtual.vTipoTarjeta : " + VariablesVirtual.vTipoTarjeta);
    System.out.println("VariablesVirtual.vMonto : " + VariablesVirtual.vMonto);
    System.out.println("VariablesVirtual.vNumTerminal : " + VariablesVirtual.vNumTerminal);
    System.out.println("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
    System.out.println("VariablesVirtual.vNumTrace : " + VariablesVirtual.vNumTrace);
    System.out.println("VariablesVirtual.vNumeroCelular : " + VariablesVirtual.vNumeroCelular);
    System.out.println("VariablesVirtual.vCodigoProv : " + VariablesVirtual.vCodigoProv);
    System.out.println("VariablesVirtual.vNumTraceOriginal : " + VariablesVirtual.vNumTraceOriginal);
    System.out.println("VariablesVirtual.vCodAprobacionOriginal : " + VariablesVirtual.vCodAprobacionOriginal);*/
  }

  /**
   * Guarda los variables para la comunicacion con el proveedor (Brightstar).
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  private static void colocaVariablesVirtualesBprepaid() throws SQLException
  {

    //VariablesVirtual.vCodigoComercio = "MFARMATEST";
    VariablesVirtual.vTipoTarjeta = VariablesCaja.vTipoTarjeta;
    VariablesVirtual.vMonto = VariablesCaja.vPrecioProdVirtual;
    VariablesVirtual.vNumTerminal = FarmaUtility.completeWithSymbol(FarmaVariables.vCodLocal, 13, " ", "D");
    //VariablesVirtual.vNumSerie = "LIM";
    VariablesVirtual.vNumTrace = obtieneNumeroTraceBprepaid();
    VariablesVirtual.vNumeroCelular = VariablesCaja.vNumeroCelular;
    VariablesVirtual.vCodigoProv = FarmaUtility.completeWithSymbol(VariablesCaja.vCodigoProv,10," ","D");
    VariablesVirtual.vNumTraceOriginal = VariablesCaja.vNumeroTraceOriginal;
    VariablesVirtual.vCodAprobacionOriginal = VariablesCaja.vCodAprobacionOriginal;
    VariablesVirtual.vFechaTX = VariablesCaja.vFechaTX;
    VariablesVirtual.vHoraTX = VariablesCaja.vHoraTX;
    //System.out.println("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
    System.out.println("VariablesVirtual.vTipoTarjeta : " + VariablesVirtual.vTipoTarjeta);
    System.out.println("VariablesVirtual.vMonto : " + VariablesVirtual.vMonto);
    System.out.println("VariablesVirtual.vNumTerminal : " + VariablesVirtual.vNumTerminal);
    //System.out.println("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
    System.out.println("VariablesVirtual.vNumTrace : " + VariablesVirtual.vNumTrace);
    System.out.println("VariablesVirtual.vNumeroCelular : " + VariablesVirtual.vNumeroCelular);
    System.out.println("VariablesVirtual.vCodigoProv : " + VariablesVirtual.vCodigoProv);
    System.out.println("VariablesVirtual.vNumTraceOriginal : " + VariablesVirtual.vNumTraceOriginal);
    System.out.println("VariablesVirtual.vCodAprobacionOriginal : " + VariablesVirtual.vCodAprobacionOriginal);
    System.out.println("VariablesVirtual.vFechaTX : " + VariablesVirtual.vFechaTX);
    System.out.println("VariablesVirtual.vHoraTX : " + VariablesVirtual.vHoraTX);
  }

  /**
   *
   * @return
   * @throws SQLException
   * @deprecated
   */
  private static String obtieneNumeroTrace() throws SQLException {
    String numTrace = "";
    numTrace = DBCaja.obtieneSecNumeraTrace(4);
    return numTrace;
  }

  /**
   * Obtiene el secuencial de mensajes Bprepaid.
   * @return System Trace
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  private static String obtieneNumeroTraceBprepaid() throws SQLException {
    String numTrace = "";
    numTrace = FarmaVariables.vCodLocal+DBCaja.obtieneSecNumeraTrace(3);
    return numTrace;
  }

  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void ventaTarjetaVirtual() throws Exception
  {

    /*proveedorTarjeta = new MetodosG(VariablesVirtual.vTiempoTXNavsat, VariablesVirtual.vTiempoCXNavsat);
    VariablesVirtual.respuestaNavSatBean =  proveedorTarjeta.VentaTarjetaVirtual(VariablesVirtual.vCodigoComercio,
                                                                                 VariablesVirtual.vTipoTarjeta,
                                                                                 VariablesVirtual.vMonto,
                                                                                 VariablesVirtual.vNumTerminal,
                                                                                 VariablesVirtual.vNumSerie,
                                                                                 VariablesVirtual.vNumTrace,
                                                                                 VariablesVirtual.vIPHost,
                                                                                 VariablesVirtual.vPuertoHost);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaNavSatBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion());
    System.out.println("getNumeroTarjeta(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta());
    System.out.println("getCodigoPIN(): " + VariablesVirtual.respuestaNavSatBean.getCodigoPIN());*/
  }

  /**
   * Se encarga de realizar la venta de una Tarjeta Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static void ventaTarjetaVirtualBprepaid() throws Exception
  {

    /*proveedorTarjetaBprepaid = new MetodosBprepaid();
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaBprepaid.VentaTarjetaVirtual(VariablesVirtual.vCodigoComercio,
                                                                                 VariablesVirtual.vTipoTarjeta,
                                                                                 VariablesVirtual.vMonto,
                                                                                 VariablesVirtual.vNumTerminal,
                                                                                 VariablesVirtual.vNumSerie,
                                                                                 VariablesVirtual.vNumTrace,
                                                                                 VariablesVirtual.vIPHost,
                                                                                 VariablesVirtual.vPuertoHost);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
    System.out.println("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
    System.out.println("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
    System.out.println("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
    System.out.println("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());*/
  }

/*  private static void colocaInfoTransaccionNavsat() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaNavSatBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaNavSatBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta();
    VariablesVirtual.vNumeroPin = VariablesVirtual.respuestaNavSatBean.getCodigoPIN();
  }
*/

  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void colocaInfoTransaccionVitual() throws Exception
  {
    /*VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaNavSatBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaNavSatBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta();
    VariablesVirtual.vNumeroPin = VariablesVirtual.respuestaNavSatBean.getCodigoPIN();*/
  }

  /**
   * Guarda la respuesta desde el TXBean
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  private static void colocaInfoTransaccionVirtualBprepaid() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaTXBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaTXBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaTXBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = VariablesVirtual.respuestaTXBean.getNumeroTarjeta();
    VariablesVirtual.vNumeroPin = VariablesVirtual.respuestaTXBean.getCodigoPIN();
    VariablesVirtual.vFechaTX = VariablesVirtual.respuestaTXBean.getFechaTX();
    VariablesVirtual.vHoraTX = VariablesVirtual.respuestaTXBean.getHoraTX();
  }

  /**
   * Guarda la respuesta desde el TXBean de Recarga.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 28.09.2007
   */
  private static void colocaInfoRecargaVirtualBprepaid() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaTXBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaTXBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaTXBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
    VariablesVirtual.vFechaTX = VariablesVirtual.respuestaTXBean.getFechaTX();
    VariablesVirtual.vHoraTX = VariablesVirtual.respuestaTXBean.getHoraTX();
    VariablesVirtual.vDatosImprimir = VariablesVirtual.respuestaTXBean.getDatosImprimir();
   
  }

  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void ventaRecargaVirtual() throws Exception
  {
    /*proveedorTarjeta = new MetodosG(VariablesVirtual.vTiempoTXNavsat, VariablesVirtual.vTiempoCXNavsat);
    VariablesVirtual.respuestaNavSatBean =  proveedorTarjeta.VentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                          VariablesVirtual.vNumeroCelular,
                                                                          VariablesVirtual.vCodigoProv,
                                                                          VariablesVirtual.vMonto,
                                                                          VariablesVirtual.vNumTerminal,
                                                                          VariablesVirtual.vNumSerie,
                                                                          VariablesVirtual.vNumTrace,
                                                                          VariablesVirtual.vIPHost,
                                                                          VariablesVirtual.vPuertoHost);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaNavSatBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion());
    System.out.println("getNumeroTarjeta(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta());
    System.out.println("getCodigoPIN(): " + VariablesVirtual.respuestaNavSatBean.getCodigoPIN());*/
  }

  /**
   * Se encarga de realizar la venta de una Recarga Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static void ventaRecargaVirtualBprepaid() throws Exception
  {
    /*proveedorTarjetaBprepaid = new MetodosBprepaid();
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaBprepaid.VentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                          VariablesVirtual.vNumeroCelular,
                                                                          VariablesVirtual.vCodigoProv,
                                                                          VariablesVirtual.vMonto,
                                                                          VariablesVirtual.vNumTerminal,
                                                                          VariablesVirtual.vNumSerie,
                                                                          VariablesVirtual.vNumTrace,
                                                                          VariablesVirtual.vIPHost,
                                                                          VariablesVirtual.vPuertoHost);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
    System.out.println("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
    System.out.println("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
    System.out.println("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
    System.out.println("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());*/

  }
  
  private static void actualizaInfoPedidoVirtual(JDialog pDialog) throws Exception
  { 
    try{
    DBCaja.actualizaInfoPedidoVirtual();
    }
    /*catch(SQLException sql){
     //System.out.println("Se cayo !!!!!!***");
     //new Exception("Hubo un error con la conexion. Intentarlo mas tarde.");
     //FarmaUtility.showMessage(pDialog, "Hubo un error con la conexion. Intentarlo mas tarde.", null);
     throw new Exception("Error en base datos:" + sql);
    }*/
    catch(Exception ex){
     //System.out.println("Se cayo !!!!!!***");
      log.error(null,ex);
     //new Exception("Hubo un error con la conexion. Intentarlo mas tarde.");
     //FarmaUtility.showMessage(pDialog, "Hubo un error con la conexion. Intentarlo mas tarde.", null);
     throw new Exception("Hubo un error con la conexion. Intentarlo mas tarde."+ex);
    }       

  }

  /*private static void obtieneInfoNavsatRecarga() {
		try {
			ArrayList infoNavsatRecarga = DBCaja.obtieneDatosNavsatRecarga();
      System.out.println("infoNavsatRecarga : " + infoNavsatRecarga);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 3, 0);
      System.out.println("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      System.out.println("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      sqlException.printStackTrace();
		}
	}*/

  /**
   *
   * @deprecated
   */
  private static void obtieneInfoRecargaVirtual() {
		/*try {
			ArrayList infoNavsatRecarga = DBCaja.obtieneDatosNavsatRecarga();
      System.out.println("infoNavsatRecarga : " + infoNavsatRecarga);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 3, 0);
      System.out.println("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      System.out.println("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      sqlException.printStackTrace();
		}*/
	}

  /**
   * Recupera los valores de conexion.
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  private static void obtieneInfoRecargaVirtualBprepaid()
  {
    try
    {
      ArrayList infoTXRecarga = DBCaja.obtieneDatostRecargaBprepaid();
      System.out.println("infoTXRecarga : " + infoTXRecarga);
      VariablesVirtual.vIPHost =
          FarmaUtility.getValueFieldArrayList(infoTXRecarga, 0, 0);
      VariablesVirtual.vPuertoHost =
          FarmaUtility.getValueFieldArrayList(infoTXRecarga, 1, 0);
      //Identificador de canal provisto por Bprepaid
      String vIdentificador = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 2, 0);
      VariablesVirtual.vCodigoComercio = FarmaUtility.completeWithSymbol(vIdentificador,10," ","D");
      //Provincia (Terminal State) del local.
      VariablesVirtual.vNumSerie = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 3, 0);

      System.out.println("VariablesVirtual.vIPHost : " +
                         VariablesVirtual.vIPHost);
      System.out.println("VariablesVirtual.vPuertoHost : " +
                         VariablesVirtual.vPuertoHost);
      System.out.println("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
      System.out.println("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
    }
    catch (SQLException sqlException)
    {
      VariablesVirtual.vCodigoComercio = "";
      VariablesVirtual.vNumSerie = "";
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      //sqlException.printStackTrace();
      log.error(null,sqlException);
    }
  }

/*  private static void obtieneInfoNavsatTarjeta() {
		try {
			ArrayList infoNavsatTarjeta = DBCaja.obtieneDatosNavsatTarjeta();
      System.out.println("infoNavsatTarjeta : " + infoNavsatTarjeta);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 3, 0);
      System.out.println("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      System.out.println("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      sqlException.printStackTrace();
		}
	}
  */

  /**
   *
   * @deprecated
   */
  private static void obtieneInfoTarjetaVirtual() {
		/*try {
			ArrayList infoNavsatTarjeta = DBCaja.obtieneDatosNavsatTarjeta();
      System.out.println("infoNavsatTarjeta : " + infoNavsatTarjeta);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 3, 0);
      System.out.println("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      System.out.println("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      sqlException.printStackTrace();
		}*/
	}


/*  private static boolean validaHostPuertoNavsat() {
    boolean result = true;
    if(VariablesVirtual.vIPHost.trim().equalsIgnoreCase("") ||
       VariablesVirtual.vPuertoHost.trim().equalsIgnoreCase(""))
      result = false;
    return result;
	}
*/

  private static boolean validaHostPuertoProveedor() {
    boolean result = true;
    if(VariablesVirtual.vIPHost.trim().equalsIgnoreCase("") ||
       VariablesVirtual.vPuertoHost.trim().equalsIgnoreCase(""))
      result = false;
    return result;
	}

  private static void impresionInfoVirtual(FarmaPrintService pVPrint,
                                           FarmaPrintServiceTicket pVPrintArchivo,
                                           String pTipoProdVirtual,
                                           String pCodigoAprobacion,
                                           String pNumeroTarjeta,
                                           String pNumeroPin,
                                           String pNumeroTelefono,
                                           String pMonto,
                                           String pNumPedido,
                                           String pCodProd)
  {
    if(pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA))
    {
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta, true);
        pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin, true);
        pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);


    } else if(pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA))
    {
      /*pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Telefono : " + pNumeroTelefono, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Monto Recarga   : " + pMonto, true);*/
      /**
       * Imprime los datos de Impresion de Recarga
       * 02.11.2007 dubilluz creacion
       */
      obtieneDescImpresion(pNumPedido,pCodProd);

      ArrayList  array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
      for(int i=0 ; i< array.size() ; i++){
        System.out.println(""+array.get(i));
        if(((String)array.get(i)).trim().length()>0){
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) +
                        ((String)(array.get(i))).trim(), true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) +
                          ((String)(array.get(i))).trim(), true);
        }
      }

    }
	}
        
        
    private static void impresionInfoVirtualTicket(FarmaPrintServiceTicket pVPrint,
                                                    FarmaPrintServiceTicket pVPrintArchivo,
                                                     String pTipoProdVirtual,
                                                     String pCodigoAprobacion,
                                                     String pNumeroTarjeta,
                                                     String pNumeroPin,
                                                     String pNumeroTelefono,
                                                     String pMonto,
                                                     String pNumPedido,
                                                     String pCodProd)
    {
              System.out.println("TIPO_PROD_VIRTUAL_RECARGA: "+pTipoProdVirtual);
      if(pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA))
      {
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta, true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin, true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);

      } else if(pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA))
      {
          System.out.println("TIPO_PROD_VIRTUAL_RECARGA: "+pTipoProdVirtual);
        /*pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Telefono : " + pNumeroTelefono, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Monto Recarga   : " + pMonto, true);*/
        /**
         * Imprime los datos de Impresion de Recarga
         * 02.11.2007 dubilluz creacion
         */
        obtieneDescImpresion(pNumPedido,pCodProd);

        ArrayList  array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
        for(int i=0 ; i< array.size() ; i++){
          System.out.println(""+array.get(i));
          if(((String)array.get(i)).trim().length()>0){
          pVPrint.printLine(FarmaPRNUtility.llenarBlancos(2) +
                          ((String)(array.get(i))).trim(), true);
              pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2) +
                              ((String)(array.get(i))).trim(), true);
          }
        }

      }
          }


  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void anulaVentaRecargaVirtual() throws Exception
  {
    /*proveedorTarjeta = new MetodosG(VariablesVirtual.vTiempoTXNavsat, VariablesVirtual.vTiempoCXNavsat);
    VariablesVirtual.respuestaNavSatBean =  proveedorTarjeta.AnulacionVentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                                   VariablesVirtual.vNumeroCelular,
                                                                                   VariablesVirtual.vCodigoProv,
                                                                                   VariablesVirtual.vMonto,
                                                                                   VariablesVirtual.vNumTerminal,
                                                                                   VariablesVirtual.vNumSerie,
                                                                                   VariablesVirtual.vNumTrace,
                                                                                   VariablesVirtual.vCodAprobacionOriginal,
                                                                                   VariablesVirtual.vNumTraceOriginal,
                                                                                   VariablesVirtual.vIPHost,
                                                                                   VariablesVirtual.vPuertoHost);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaNavSatBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion());*/
  }

  /**
   * Realiza la anulacion de una venta de producto virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static void anulaVentaRecargaVirtualBprepaid() throws Exception
  {
    /*proveedorTarjetaBprepaid = new MetodosBprepaid();
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaBprepaid.AnulacionVentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                                   VariablesVirtual.vNumeroCelular,
                                                                                   VariablesVirtual.vCodigoProv,
                                                                                   VariablesVirtual.vMonto,
                                                                                   VariablesVirtual.vNumTerminal,
                                                                                   VariablesVirtual.vNumSerie,
                                                                                   VariablesVirtual.vNumTrace,
                                                                                   VariablesVirtual.vCodAprobacionOriginal,
                                                                                   VariablesVirtual.vNumTraceOriginal,
                                                                                   VariablesVirtual.vIPHost,
                                                                                   VariablesVirtual.vPuertoHost,
                                                                                   VariablesVirtual.vFechaTX,
                                                                                   VariablesVirtual.vHoraTX);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());*/
  }

  /**
   * Metodo que anula la venta de pedidos virtuales.
   * Este metodo se conectara con el proveedor de servicios a traves de su interfase.
   * Creado x LMesia 22/01/2007
   */
  public static void procesaAnulacionVentaProductoVirtual(JDialog pDialog, Object pObjectFocus) throws SQLException, Exception
  {
    //colocaVariablesVirtuales();
     colocaVariablesVirtualesBprepaid();
    if(VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA))
    {

    /*
      System.err.println("*** entro a anulacion de venta de recarga virtual");
      obtieneInfoRecargaVirtualBprepaid();
   
      if(!validaHostPuertoProveedor())
      {
        throw new Exception("Error al obtener host y puerto de Navsat para recarga automatica");
      }
    
      anulaVentaRecargaVirtualTX();
      colocaInfoTransaccionVirtualBprepaid();
     
     */
      
    
        ProcesoAnulaciónRecarga();
        
    } else
    {
      limpiaInfoTransaccionTarjVirtuales();
      throw new Exception("No se puede anular un producto del tipo Tarjeta Virtual");
    }
  }


  private static void ProcesoAnulaciónRecarga(){

        try {

            //
            //Cierra todas las conexiones remotas
            FarmaConnectionRemoto.closeConnection();

            int codigo = DBCaja.AnularRecargaVirtual();
            System.err.println("codigo Respuesta Recarga Virtual" + codigo);
            String cantIntentosLectura = 
                DBCaja.cantidadIntentosRespuestaRecarga().trim();
            System.out.println("cantIntentosLectura" + cantIntentosLectura);
            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_ADMCENTRAL, 
                                                  FarmaConstants.INDICADOR_N);
            TimerRecarga timerTask = new TimerRecarga();
            timerTask.setCantidadIntentos(Integer.parseInt(cantIntentosLectura));
            timerTask.setCodigoSolicitud(codigo);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 0, 1000);

            do {
                //log.debug("indicador TIMER :"+timerTask.getIndicador());
            } while (timerTask.getIndicador().trim().equalsIgnoreCase("I"));

            log.debug("termino el TIMER DE RECARGA");

            log.debug("timerTask.getIndicador():" + timerTask.getIndicador());

            if (timerTask.getIndicador().equals("T")) {
                log.debug("No se encontro respuesta de la recarga");
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_ADMCENTRAL, 
                                                  FarmaConstants.INDICADOR_N);
        } catch (NumberFormatException e) {
              FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_ADMCENTRAL, 
                                                    FarmaConstants.INDICADOR_N);
          }
          finally {
               FarmaConnectionRemoto.closeConnection();
              log.info("Cierra Conexiones remotas");
          }
         
      
      }
/*  private static void limpiaInfoTransaccionNavsat() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = "";
    VariablesVirtual.vDescripcionRespuesta = "";
    VariablesVirtual.vNumTrace = "";
    VariablesVirtual.vCodigoAprobacion = "";
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
  }*/
  private static void limpiaInfoTransaccionTarjVirtuales() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = "";
    VariablesVirtual.vDescripcionRespuesta = "";
    VariablesVirtual.vNumTrace = "";
    VariablesVirtual.vCodigoAprobacion = "";
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
    VariablesVirtual.vFechaTX = "";
    VariablesVirtual.vHoraTX = "";
  }


  public static void actualizaInfoPedidoVirtualAnulado(String pNumPedOrigen) throws SQLException
  {
    DBCaja.actualizaInfoPedidoAnuladoVirtual(pNumPedOrigen);
  }
  /**
   * Coloca la descripcion de la impresion que el proveedor requiere en la boleta
   * @author dubilluz
   * @since  02.11.2007
   */
  public static void obtieneDescImpresion(String pNumped,String pCodProd)
  {
    try
    {
     DBCaja.obtieneInfImpresionRecarga(VariablesVirtual.vArrayList_InfoProvRecarga,
                                       pNumped,pCodProd);
     System.out.println("xxxx : " + VariablesVirtual.vArrayList_InfoProvRecarga);
    }
    catch(SQLException e)
    {
      //System.out.println("Ocurrio un error al obtener la informacio del Proveedor :\n " + e);
      log.error(null,e);
    }
  }

  /**
   * Se encarga de realizar la venta de una Tarjeta Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static void ventaTarjetaVirtualTX() throws Exception
  {

    proveedorTarjetaVirtual = MetodosTXFactory.getMetodosTXVirtual(MetodosTXFactory.METODO_BPCLIENTWS);
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaVirtual.VentaTarjetaVirtual(VariablesVirtual.vCodigoComercio,
                                                                                 VariablesVirtual.vTipoTarjeta,
                                                                                 VariablesVirtual.vMonto,
                                                                                 VariablesVirtual.vNumTerminal,
                                                                                 VariablesVirtual.vNumSerie,
                                                                                 VariablesVirtual.vNumTrace,
                                                                                 VariablesVirtual.vIPHost,
                                                                                 VariablesVirtual.vPuertoHost);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
    System.out.println("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
    System.out.println("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
    System.out.println("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
    System.out.println("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());
  }
  
  /**
   * Se encarga de realizar la venta de una Recarga Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static void ventaRecargaVirtualTX() throws Exception
  {
    proveedorTarjetaVirtual = MetodosTXFactory.getMetodosTXVirtual(MetodosTXFactory.METODO_BPCLIENTWS);
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaVirtual.VentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                          VariablesVirtual.vNumeroCelular,
                                                                          VariablesVirtual.vCodigoProv,
                                                                          VariablesVirtual.vMonto,
                                                                          VariablesVirtual.vNumTerminal,
                                                                          VariablesVirtual.vNumSerie,
                                                                          VariablesVirtual.vNumTrace,
                                                                          VariablesVirtual.vIPHost,
                                                                          VariablesVirtual.vPuertoHost,
                                                                          FarmaVariables.vCodLocal,
                                                                          VariablesCaja.vNumPedVta);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
    System.out.println("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
    System.out.println("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
    System.out.println("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
    System.out.println("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());

  }
  
  /**
   * Realiza la anulacion de una venta de producto virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static void anulaVentaRecargaVirtualTX() throws Exception
  {
    proveedorTarjetaVirtual = MetodosTXFactory.getMetodosTXVirtual(MetodosTXFactory.METODO_BPCLIENTWS);
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaVirtual.AnulacionVentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                                   VariablesVirtual.vNumeroCelular,
                                                                                   VariablesVirtual.vCodigoProv,
                                                                                   VariablesVirtual.vMonto,
                                                                                   VariablesVirtual.vNumTerminal,
                                                                                   VariablesVirtual.vNumSerie,
                                                                                   VariablesVirtual.vNumTrace,
                                                                                   VariablesVirtual.vCodAprobacionOriginal,
                                                                                   VariablesVirtual.vNumTraceOriginal,
                                                                                   VariablesVirtual.vIPHost,
                                                                                   VariablesVirtual.vPuertoHost,
                                                                                   VariablesVirtual.vFechaTX,
                                                                                   VariablesVirtual.vHoraTX,
                                                                                   FarmaVariables.vCodLocal,
                                                                                   VariablesCaja.vNumPedVta_Anul);
    System.out.println("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    System.out.println("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    System.out.println("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    System.out.println("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
  }
  
  /**
   * Se evalua la fecha de movimiento de la caja.
   * @param pDialogo
   * @param pObject
   * @return 
   * @author Edgar Rios Navarro
   * @since 07.01.2007
   */
  public static boolean validaFechaMovimientoCaja(JDialog pDialogo,Object pObject)
  {
    try{
      String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      String fechaMovCaja = DBCaja.obtieneFechaMovCaja();
      if ( fechaMovCaja.trim().length()>0 && !(fechaMovCaja.substring(0, 5).equalsIgnoreCase(fechaSistema.substring(0, 5))) ) {
        FarmaUtility.showMessage(pDialogo, "Debe CERRAR su caja para empezar un NUEVO DIA.\n" +
          "La Fecha actual no coincide con la Fecha de Apertura de Caja.",pObject);
        return false;
      }
      return true;
    } catch (SQLException sqlException) {
      //sqlException.printStackTrace();
      log.error(null,sqlException);
      FarmaUtility.showMessage(pDialogo, "Error al obtener la fecha de movimiento de caja.",pObject);
      return false;
    }
  }

  /**
   * Se imprime los consejos asociados al pedido.
   * @author Edgar Rios Navarro
   * @since 09.05.2008
   */
   
  //MARCO FAJARDO 08/04/09 MEJORA TIEMPO DE RESPUESTA DE IMPRESORA TERMICA 
  private static void imprimeConsejos(JDialog pDialogo)
  {
    long tmpT1,tmpT2;  
    
      try
      {    
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
        //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP         
               
              tmpT1 = System.currentTimeMillis();
              String htmlConsejo = DBCaja.obtieneConsejos(VariablesCaja.vNumPedVta, 
                                     FarmaVariables.vIPBD);
              tmpT2 = System.currentTimeMillis();
              log.debug("Tiempo 12: Obtiene Cadena para Consejo:"+(tmpT2 - tmpT1)+" milisegundos");
            if (!htmlConsejo.equals("N"))
            {
                tmpT1 = System.currentTimeMillis();
              PrintConsejo.imprimirHtml(htmlConsejo, VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                tmpT2 = System.currentTimeMillis();
                log.debug("Tiempo 13: Obtiene Cadena para Consejo:"+(tmpT2 - tmpT1)+" milisegundos");
              consejo=true;//Se muestra mensaje de impresion de consejos
              // -- Se hace COMMIT si se imprimio el consejo
              FarmaUtility.aceptarTransaccion();
            }
            /*
             //NO hay problema de no liberar.
                Ya que la tabla temporal usada se liberara en el proximo commit
                
            else
            {
              // -- Se libera por no dejar nigun bloqueo de IND_IMP_CONSEJO
              FarmaUtility.liberarTransaccion();
            }
            */
      }
      catch (SQLException sqlException)
      {
        //sqlException.printStackTrace();
        log.error(null,sqlException);
        FarmaUtility.showMessage(pDialogo, 
                                 "Error al obtener los consejos.", null);
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sqlException.toString(),"\nError al imprimir CONSEJO");

      }catch(Exception e){
    	  System.err.println("error imprimir consejo:"+e.getMessage());
    	  FarmaUtility.liberarTransaccion();
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(e.toString(),"\nError al imprimir CONSEJO");
          
      }
    }
  
   /**
   * Se imprime los cierto datos de comanda al cobrar algun pedido de delivery
   * @author Jorge Cortez Alvarez
   * @since 13.06.2008
   */
    //MARCO FAJARDO 08/04/09 MEJORA TIEMPO DE RESPUESTA DE IMPRESORA TERMICA
  public static void imprimeDatosDelivery(JDialog pDialogo,String NumPed)
  {
    
    
      try
      {
        //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
       // String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP         
                               
           String vIndImpre = DBCaja.obtieneIndImpresion();
           System.out.println("vIndImpre :"+vIndImpre);
            if (!vIndImpre.equals("N"))
            {
              String htmlDelivery = DBCaja.obtieneDatosDelivery(NumPed,FarmaVariables.vIPBD);
			  //System.out.println(datos);
              PrintConsejo.imprimirHtml(htmlDelivery,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              //break;
            }
          
        
      }catch (SQLException sqlException)
      {
        //sqlException.printStackTrace();
        log.error(null,sqlException);
        FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de delivery.:"+sqlException.getMessage(), null);
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sqlException.toString(),NumPed);
      }
    

  }
  
  /**
     * Metodo que verifica si el pedido tiene cupones para imprimir 
     * @author Diego Ubilluz Carrillo
     * @since  03.07.2008
     * @param pDialogo
     */
  private static int imprimeCupones(JDialog pDialogo){
    ArrayList listaCupones = new ArrayList();
    int cant_cupones_impresos  = 0;
    try{
          DBCaja.obtieneCuponesPedidoImpr(listaCupones,VariablesCaja.vNumPedVta);
          System.out.println("Lista cupones .... " + listaCupones );
           
          if(listaCupones.size()>0)
          {
              String cod_cupon = "";
              for(int i=0;i<listaCupones.size();i++){
                  cod_cupon = ((ArrayList)(listaCupones.get(i))).get(0).toString();
                  if(cod_cupon.trim().length()>0)
                     cant_cupones_impresos = cant_cupones_impresos + imprimeCupon(pDialogo,cod_cupon);
              }
          }
       }
       catch (SQLException sqlException)
       {
        //sqlException.printStackTrace();
        log.error(null,sqlException);
        FarmaUtility.showMessage(pDialogo, 
                                "Error al verificar si tiene cupones el pedido.\n"+
                                 sqlException.getMessage(), null);
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
           enviaErrorCorreoPorDB(sqlException.toString(),null);
       }
    
    /*if(cant_cupones_impresos>0){
        FarmaUtility.showMessage(pDialogo, 
                                "Favor de recoger "+cant_cupones_impresos+" cupones.",
                                 null);
    }*/
    
    return cant_cupones_impresos;
       
   }
   
  
  /**
   * Se imprime cupones que tenga el pedido cobrado
   * @author Diego Ubilluz
   * @since 03.07.2008
   */
   //MARCO FAJARDO 08/04/09 MEJORA TIEMPO DE RESPUESTA DE IMPRESORA TERMICA
  private static int imprimeCupon(JDialog pDialogo,String vCodeCupon)
  {
    int cant_cupones_impresos = 0;
    
      try
      {
        //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
        //String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
        
        int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
              
            
            String vCupon = DBCaja.obtieneImprCupon(VariablesCaja.vNumPedVta, 
                                                    FarmaVariables.vIPBD,
                                                    vCodeCupon);
            if(!vCupon.equals("N"))
            {
            	log.debug("cupon a imprimir : "+vCupon);
            	PrintConsejo.imprimirCupon(vCupon,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp,vCodeCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
            	log.debug("despues a imprimir");
            	// -- Proceso autonomo que tiene COMMIT
            	DBCaja.cambiaIndImpresionCupon(VariablesCaja.vNumPedVta,vCodeCupon);
            	cant_cupones_impresos ++;
            }   


      }
      catch (SQLException sqlException)
      {
       //sqlException.printStackTrace();
        log.error(null,sqlException);
       FarmaUtility.showMessage(pDialogo, 
                               "Error al obtener los consejos.", null);

      }
      
    
    
    return cant_cupones_impresos;
   }   
  
    /**
       * Metodo encargado de anular los cupones en Matriz si estos no fueron 
       * enviados aun por el JOB ó crea los cupones con el estado Anulado
       * si estos aun no fueron enviados a Matriz
       * @param pNumPed
       * @author Diego Ubilluz
       * @since  21.08.2008
       */
    public static void anulaCuponesPedido(String pNumPed,JDialog pDialogos,
                                          Object pObject){
        System.out.println("Anular Cupones en Matriz");
        System.out.println("**INICIO**");
        VariablesCaja.vIndLinea = "";
        ArrayList listaCuponesAnulados = new ArrayList();
        int COL_COD_CUPON = 0;
        int COL_COD_FECHA_INI = 1;
        int COL_COD_FECHA_FIN = 2;   
        String vEstCuponMatriz = "";
        String vCodCupon = "";
        String vRetorno  = "";
        String vFechIni ="";
        String vFechFin ="";
        String indMultiUso ="";
        boolean vIndModify = false;
        try{
            
            DBCaja.getcuponesPedido(pNumPed,
                                    FarmaConstants.INDICADOR_N,
                                    listaCuponesAnulados,
                                    ConstantsCaja.CONSULTA_CUPONES_ANUL_SIN_PROC_MATRIZ);
            System.out.println("**listaCuponesAnulados00**"+ listaCuponesAnulados);
            if(listaCuponesAnulados.size()>0){
                 String vValor = "";
                 vValor = FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                              0,
                                                              COL_COD_CUPON);
                 
                if(!vValor.equalsIgnoreCase("NULO")){
                    VariablesCaja.vIndLinea = 
                                  FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                                 FarmaConstants.INDICADOR_N);
                    
                    
                    //-- Si no hay linea se cierra la conexion con Matriz
                    if(VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                       System.out.println("No existe linea se cerrara la conexion ...");
                       FarmaConnectionRemoto.closeConnection();
                       VariablesCaja.vIndLinea = "";
                    }
                    else{
                      //--Si hay conexion se procedera anular los cupones en Matriz
                      //--Y crearlos si no existe pero con estado Anulado
                        for(int i=0;i<listaCuponesAnulados.size();i++){
                            vCodCupon = 
                                FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                                    i,COL_COD_CUPON);
                            vFechIni  =
                                FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                                    i,COL_COD_FECHA_INI);
                            
                            vFechFin  =
                                FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                                    i,COL_COD_FECHA_FIN);                          
                            
                            indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPed,
                                                                        vCodCupon).trim();                          
                            
                            System.out.println("**indMultiUso**"+ indMultiUso);
                            if(indMultiUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                               {
                                vIndModify = true;
                                  vEstCuponMatriz = 
                                          DBCaja.getEstadoCuponEnMatriz(vCodCupon, 
                                                                        FarmaConstants.INDICADOR_S).trim();

                                  System.out.println("vEstCuponMatriz "+ vEstCuponMatriz);  
                                  //--Si valor de retorno es "0" es porque el cupon
                                  //  no existe asi que se creara en Matriz
                                  if (vEstCuponMatriz.equalsIgnoreCase("0")) {
                                      vRetorno = 
                                              DBCaja.grabaCuponEnMatriz(vCodCupon, 
                                                                        vFechIni, 
                                                                        vFechFin, 
                                                                        FarmaConstants.INDICADOR_N).trim();
                                  }
                                  vRetorno = 
                                          DBCaja.actualizaEstadoCuponEnMatriz(vCodCupon, 
                                                                              FarmaConstants.INDICADOR_N, 
                                                                              FarmaConstants.INDICADOR_N).trim();

                                  //--Si la actualizacion se realizo con exito se actualiza
                                  //  en el local que el cupon ya se proceso en Matriz
                                  if (vRetorno.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                      DBCaja.actualizaCuponGeneral(vCodCupon.trim(), 
                                                                   ConstantsCaja.CONSULTA_ACTUALIZA_MATRIZ);
                                }
                            }
                            
                        }
                        //--fin de FOR
                        //--Se acepta la transaccion en la Conexion a Matriz y Local
                        if(vIndModify){
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_N);
                        FarmaUtility.aceptarTransaccion();
                        }
                    }
                }

            }
            
        }
        catch(SQLException e) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_N);
            e.printStackTrace();
            FarmaUtility.showMessage(pDialogos, 
                                     "Error al momento de anular cupones en Matriz.\n" + 
                                     e.getMessage(), pObject);          
        }
        finally{
            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            if(VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
               System.out.println("Se cierrara la conexion..");
               FarmaConnectionRemoto.closeConnection();
            }
            VariablesCaja.vIndLinea = "";
        }
        
        System.out.println("**FIN**");
    }
  
    /**
       * Retorna el numero de cupones emitidos
       * @author DUbilluz
       * @param pNumPed
       * @return
       */
    public static int getCuponesEmitidosUsados(String pNumPed,JDialog pDialogo,
                                               Object pObject){
        String vCodCupon = "";
        int vNumCuponesEmitidos = 0;
        ArrayList listCuponesEmitidos = new ArrayList();
        try{
            //-- inicio validacion cupones
             //Se consulta para obtener los cupones usados en el pedido
            
             DBCaja.getcuponesPedido(pNumPed,
                                     FarmaConstants.INDICADOR_N,
                                     listCuponesEmitidos,
                                     ConstantsCaja.CONSULTA_CUPONES_EMITIDOS_USADOS);
             
             if(listCuponesEmitidos.size()>0){
                 
                  String vValor = "";
                  vValor = FarmaUtility.getValueFieldArrayList(listCuponesEmitidos,
                                                               0,
                                                               0);
                 if(vValor.equalsIgnoreCase("NULO")){
                    return  0;
                 }
                 vNumCuponesEmitidos = listCuponesEmitidos.size();
             }

        }catch(SQLException e)
        {
          e.printStackTrace();
          FarmaUtility.showMessage(pDialogo, 
                                   "Error al obtener cupones emitidos usados.\n" + 
                                   e.getMessage(), pObject);
        }
        
        return vNumCuponesEmitidos;
    }
    
    /**
     * 
     * @param pSecNumIni
     * @param pSecNumFin
     * @return
     */
    public static boolean isExistsCompProcesoSAP(String pSecNumIni,String pSecNumFin){
        boolean pResultado = false;
        String pCadena = "";
        try{
             pCadena = DBCaja.isExistCompProcesoSAP(pSecNumIni,pSecNumFin);
            if(pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                 pResultado = true;
            else
                 pResultado = false;
            
        }catch(SQLException e)
        {
          e.printStackTrace();
        }
    
        return pResultado;
    }

    /**
     * @author dubilluz
     * @param secIni
     * @param secFin
     * @return
     */
    public static boolean pValidaComprobantesProcesoSAP(String secIni, 
                                                        String secFin,
                                                        Object pDialogo,
                                                        Object pObjeto,
                                                        Frame pFrame) {
        
        
        if (isExistsCompProcesoSAP(secIni.trim(), secFin.trim())) {
            FarmaUtility.showMessage((JDialog)pDialogo, 
                   "Atención:\n"+
                   "No podrá corregir los correlativos de los comprobantes\n"+
                   "porque alguno de ellos ya han sido transferidos a contabilidad.\n"+
                   "Debe comunicarse con su contador para que justifique\n"+
                   "el motivo para regularizar los comprobantes.",
                                     pObjeto);

            if (!cargaLoginOper(pFrame,pDialogo)) {
                return false;
            }
        }

        return true;
    }
    
    private static boolean cargaLoginOper(Frame pFrame,Object pDialog)
    {
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      
        try{
          DlgLogin dlgLogin = new DlgLogin(pFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
          dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
          dlgLogin.setVisible(true);
          FarmaVariables.vNuSecUsu  = numsec ;
          FarmaVariables.vIdUsu  = idusu ;
          FarmaVariables.vNomUsu  = nomusu ;
          FarmaVariables.vPatUsu  = apepatusu ;
          FarmaVariables.vMatUsu  = apematusu ;
        } catch (Exception e)
        {
          FarmaVariables.vNuSecUsu  = numsec ;
          FarmaVariables.vIdUsu  = idusu ;
          FarmaVariables.vNomUsu  = nomusu ;
          FarmaVariables.vPatUsu  = apepatusu ;
          FarmaVariables.vMatUsu  = apematusu ;
          FarmaVariables.vAceptar = false;
          e.printStackTrace();
          FarmaUtility.showMessage((JDialog)pDialog,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
        }
        
      return FarmaVariables.vAceptar;
    }
    
    /**
     * Revisa si el pedido es de Delivery para enviar un alerta de Anulacion     * 
     * @author Dubilluz
     * @since  26.11.2008
     * @param pCadena
     */
    public static void alertaPedidoDelivery(String pCadena){
        if(!pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
            String pCodCia = "",pCodLocalDel="",pNumPedDel = "";
            String[] pDatosDel = pCadena.trim().split("%");
            if(pDatosDel.length==5){
                pCodCia = pDatosDel[0].trim();
                pCodLocalDel = pDatosDel[1].trim();
                pNumPedDel = pDatosDel[2].trim();
                
                try{
                    DBCaja.enviaAlertaDelivery(pCodCia,pCodLocalDel,pNumPedDel);
                }catch(SQLException e)
                {
                  //e.printStackTrace();
                    System.out.println("Error de envio de alerta delivery");
                }               
            }
            
        }
    }
    
    /**
     * Validar si existen comprobantes desfasados
     * @author Dubilluz
     * @since  27.11.2008
     * @param pFechaDia
     * @return
     */
    public static boolean validaCompDesfase(String pFechaDia){
        String pRes = "";
        try
        {
            pRes = DBCaja.validaCompDesfase(pFechaDia).trim();
        } catch(SQLException sql)
        {
            sql.printStackTrace();
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    
    /**
     * 
     * @author dubilluz
     * @since  28.11.2008
     * @return
     */
    public static boolean validaDelPendSinReg(String pFechaDia)
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.validaDelPendSinReg(pFechaDia).trim();    
        } catch(SQLException sql)
        {
            sql.printStackTrace();
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }

    public static boolean validaAnulPedSinReg(String pFechaDia)
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.validaAnulPeddSinReg(pFechaDia).trim();    
        } catch(SQLException sql)
        {
            sql.printStackTrace();
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    /**
     * 
     * @author dubilluz
     * @since  28.11.2008
     * @return
     */
    public static boolean validaRegPedManual(String pFechaDia)
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.validaPedidosManualesSinReg(pFechaDia).trim();
        } catch(SQLException sql)
        {
            sql.printStackTrace();
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    
    /**
     * Prueba Impresora Termica
     * @author Diego Ubilluz
     * @since  01.12.2008
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void pruebaImpresoraTermica(JDialog pDialogo,Object pObject)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      numeroCorrel++;
      String numAux = "000"+numeroCorrel;
      String pCodCupon = "9999999999"+numAux.substring(numAux.length()-3, numAux.length());
      int cant_cupones_impresos = 0;
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
          
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP         
          
          int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
          
          
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{
              
              String vCupon = DBCaja.pruebaImpresoraTermica(pCodCupon);
              log.debug(" prueba de impresion termica...");
              
              PrintConsejo.imprimirCupon(vCupon,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp,pCodCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              //break;
            //}
          //}
          FarmaUtility.showMessage(pDialogo, 
                                  "Se realizó la prueba de impresión, recoja la impresión.", pObject);

        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al realizar prueba de impresion.",pObject);

        }
        
      //}
    }
    
    public static void activaCuponesMatriz(String pNumPed, JDialog pDialogos, 
                                           Object pObject) {
        System.out.println("activa cupones usados en Matriz");
        System.out.println("**INICIO**");
        VariablesCaja.vIndLinea = "";
        ArrayList listaCuponesUsados = new ArrayList();
        int COL_COD_CUPON = 0;
        try {
            VariablesCaja.vIndLinea = 
                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                   FarmaConstants.INDICADOR_N);
            
            if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                
                
                DBCaja.getcuponesUsadosPedido(pNumPed, listaCuponesUsados);
                System.out.println("**listaCuponesUsados**" + 
                                   listaCuponesUsados);            
                if (listaCuponesUsados.size() > 0) {
                    String vValor = "";
                    for (int i = 0; i < listaCuponesUsados.size(); i++) {
                        vValor = 
                                FarmaUtility.getValueFieldArrayList(listaCuponesUsados, 
                                                                    i, 
                                                                    COL_COD_CUPON);
    
                        DBCaja.activaCuponenMatriz(vValor.trim(),FarmaConstants.INDICADOR_N);
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, 
                                                              FarmaConstants.INDICADOR_N);
                    }
    
                }
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, 
                                                  FarmaConstants.INDICADOR_N);
            e.printStackTrace();
            FarmaUtility.showMessage(pDialogos, 
                                     "Error al momento de activar cupones en Matriz.\n" +
                    e.getMessage(), pObject);
        } finally {
            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                System.out.println("Se cierrara la conexion..");
                FarmaConnectionRemoto.closeConnection();
            }
            VariablesCaja.vIndLinea = "";
        }

        System.out.println("**FIN**");
    }
   
    /**
     * obtener DNI del cliente si se trata de una venta que acumula ventas
     * @author JCALLO
     * @param  pNumPed
     * @return
     */
    public static String obtenerDniPedidoAcumulaVenta(String pNumPed) {
        System.out.println("ver si el pedido acumula ventas");
        System.out.println("**INICIO**");
        
        String Dni = "";
        
        try {
        	Dni = DBCaja.getDniPedidoAcumulaVenta(pNumPed);
        	return Dni.trim();
        } catch (SQLException e) {            
            e.printStackTrace();
            return "";
        } finally {
        }
    }

    /**
     * Analiza si el pedido es de camapana acumulada
     * y revertira todo o confirmara segun sea el caso
     * @author dubilluz
     * @since  19.12.2008
     * @param  pNumPed
     * @param  pDialogos
     * @param  pObject
     */
    public static boolean realizaAccionCampanaAcumulada
                                    (
                                     String pIndLinea,
                                     String pNumPed, JDialog pDialogos,
                                     String pAccion,
                                     Object pObject,
                                     String pIndEliminaRespaldo
                                     ) {
        String sDNI = "";
        String existeRegalo = "";
        try{
            sDNI = DBCaja.getDniFidPedidoCampana(pNumPed).trim();
            System.out.println("DNI realizaAccionCAmpanaAcu: "+sDNI);
            if(sDNI.length()>0)
            {
                existeRegalo = DBCaja.getExistRegaloCampanaAcumulada(sDNI,pNumPed);
                System.out.println("Existe REGALO: "+existeRegalo);
                /* //Se comento para evitar insertOrigenMatriz e insertCanjMatriz 
                 * //que se encuentra dentro de enviaRegaloMatriz. Todo el Proceso
                 * //de acumulado se hace en local.
                if(existeRegalo.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {                   
                    //Si no hay linea no deja cobrar
                    if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                    {   
                        if(pAccion.trim().equalsIgnoreCase(ConstantsCaja.ACCION_COBRO))
                            return false;
                    }
                    else
                    if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        if(pAccion.trim().equalsIgnoreCase(ConstantsCaja.ACCION_COBRO))
                           enviaRegaloMatriz(sDNI,pNumPed);                                      
                }
                */
                //JMIRANDA 17/07/09 
                DBCaja.analizaCanjeLocal(sDNI,pNumPed,pAccion,pIndEliminaRespaldo);
               /* if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {   
                DBCaja.analizaCanjeMatriz(sDNI,pNumPed,pAccion);
                }
                */
                //JMIRANDA 16/07/09
               DBCaja.analizaCanjeMatriz(sDNI,pNumPed,pAccion);
                System.out.println("TRUE 1 ANALIZACANJEMATRIZ");
                return true;
                                
            }
            else
                System.out.println("TRUE 2 ANALIZACANJEMATRIZ");
                return true;
            
        }catch (SQLException e){
            e.printStackTrace();
            log.debug("Envia error ANALIZACANJEMATRIZ");
            return false;
            
        }
    }
    
    /**
     * Se imprime el las campañas acumuladas del cliente
     * @author JAVIER CALLO QUISPE
     * @since 12.10.2008
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    private static void imprimeUnidRestCampXCliente(JDialog pDialogo, String pDni, String pNumPedVta)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
 
         //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP        
                
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

                StringBuffer html = new StringBuffer("");
                
                ArrayList listaMatriz = new ArrayList();
                //obteniendo la suma de las unidades acumuladas en las campañas
                //en la compra
                DBCaja.getListCampRestPremioXCliente(listaMatriz,pDni, pNumPedVta);
             
                //obteniendo los premios que gano en el pedido
                ArrayList listaPremios = new ArrayList();
                DBCaja.getListCampPremiosPedidoCliente(listaPremios,pDni, pNumPedVta);
                
                //obteniendo la cabecera del html
                String cab_html = DBCaja.getCabHtmlCampAcumXCliente(pDni).trim();
                
                //obteniendo la pie del html
                String pie_html = DBCaja.getPieHtmlCampAcumXCliente(pNumPedVta).trim();
                
                /**generando el html**/
                html.append(cab_html);
                String auxCodCamp = "";
                boolean flag = false;
                for(int k=0; k<listaMatriz.size() ;k++){//recorriendo por cada campaña del local
                    
                    auxCodCamp = ((ArrayList)listaMatriz.get(k)).get(0).toString().trim();
                    //buscando si dicha campaña 
                    flag = false;
                    for(int p = 0 ; p < listaPremios.size() ;p++){
                        if(auxCodCamp.equals( ((ArrayList)listaPremios.get(p)).get(0).toString() ) ){
                            flag = true;
                            listaPremios.remove(p);//quitanto la campaña
                            break;
                        }
                    }
                    if(flag){
                        html.append("<tr><td><b>Ud. Gan&oacute premio de la campaña</b><br>");
                    }else{
                        html.append("<tr><td>");
                    }
                    
                    int cant = 0;
                    
                    try{
                        cant = Integer.parseInt(((ArrayList)listaMatriz.get(k)).get(10).toString().trim());
                    }catch(Exception e){
                        cant = 0;
                    }
                    
                    if( cant > 0 ){
                        
                        html.append( ((ArrayList)listaMatriz.get(k)).get(7).toString().trim() )//mensaje
                            .append("<br>")
                            .append("Le faltan ")
                            .append( ((ArrayList)listaMatriz.get(k)).get(10).toString().trim() )
                            .append("&nbsp;&nbsp;")
                            .append( ((ArrayList)listaMatriz.get(k)).get(6).toString().trim() )
                            .append(" de compra para ganar el premio</td></tr> ");
                        
                        
                    }else{
                        html.append( ((ArrayList)listaMatriz.get(k)).get(7).toString().trim() )
                            .append("<br>")
                            .append("Acumul&oacute; ")
                            .append( ((ArrayList)listaMatriz.get(k)).get(9).toString().trim() )
                            .append("&nbsp;&nbsp;")
                            .append( ((ArrayList)listaMatriz.get(k)).get(6).toString().trim() )
                            .append(" en total de sus compras </td></tr> ");
                    
                    }
                    
                    
                }
                
                html.append("</table>").append(pie_html).append("</td></tr></table></body></html>");
                
                
                if (html.toString().length()>0)
                {
                  System.err.println("htmlImprimir:"+html.toString());
                  PrintConsejo.imprimirHtml(html.toString(), VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                }
               // break;
            //}
          //}

        }
        catch (SQLException sqlException)
        {
          //sqlException.printStackTrace();
          log.error(null,sqlException);
        }catch(Exception e){
      	  System.err.println("error imprimir consejo:"+e.getMessage());
        }
      //}
    }
    
    /**
     * imprime la cantidad de unidades que acumulo para las campañas
     * en la compra
     * @author JAVIER CALLO QUISPE
     * @since 22.10.2008
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    private static void imprimirUnidAcumCampXCliente(JDialog pDialogo, String pDni, String pNumPedVta)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
          
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
         
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

              StringBuffer html = new StringBuffer("");
              
              ArrayList listaLocal = new ArrayList();
              //obteniendo la suma de las unidades acumuladas en las campañas
              //en la compra
              DBCaja.getListCampAcumuladaXCliente(listaLocal,pDni, pNumPedVta);
              
              //obteniendo los premios que gano en el pedido
              ArrayList listaPremios = new ArrayList();
              DBCaja.getListCampPremiosPedidoCliente(listaPremios,pDni, pNumPedVta);
              
              //obteniendo la cabecera del html
              String cab_html = DBCaja.getCabHtmlCampAcumXCliente(pDni).trim();
              
              //obteniendo la pie del html
              String pie_html = DBCaja.getPieHtmlCampAcumXCliente(pNumPedVta).trim();
              
              /**generando el html**/
              html.append(cab_html);
              String auxCodCamp = "";
              boolean flag = false;
              for(int k=0; k<listaLocal.size() ;k++){//recorriendo por cada campaña del local
                  
                  auxCodCamp = ((ArrayList)listaLocal.get(k)).get(0).toString().trim();
                  //buscando si dicha campaña 
                  flag = false;
                  for(int p = 0 ; p < listaPremios.size() ;p++){
                      if(auxCodCamp.equals( ((ArrayList)listaPremios.get(p)).get(0).toString() ) ){
                          flag = true;
                          listaPremios.remove(p);//quitanto la campaña
                          break;
                      }
                  }
                  if(flag){
                    html.append("<tr><td><b>Ud. Gan&oacute premio de la campaña</b><br>");
                  }else{
                    html.append("<tr><td>");
                  }
                  html.append( ((ArrayList)listaLocal.get(k)).get(7).toString().trim() )
                      .append("<br>")
                      .append("Acumul&oacute; ")
                      .append( ((ArrayList)listaLocal.get(k)).get(9).toString().trim() )
                      .append("&nbsp;&nbsp;")
                      .append( ((ArrayList)listaLocal.get(k)).get(6).toString().trim() )
                      .append(" en su compra</td></tr> ");
              }
              
              html.append("</table>").append(pie_html).append("</td></tr></table></body></html>");
              
              
              if (html.toString().length()>0)
              {
                System.err.println("htmlImprimir:"+html.toString());
                PrintConsejo.imprimirHtml(html.toString(), VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              }
             // break;
            //}
          //}

        }
        catch (SQLException sqlException)
        {
          //sqlException.printStackTrace();
          log.error(null,sqlException);
        }catch(Exception e){
          System.err.println("error imprimir consejo:"+e.getMessage());
        }
      //}
    }
    
    private static void enviaRegaloMatriz(String pDNI,
                                          String pNumPed)throws SQLException{
        
        
        ArrayList listaCanjes = new ArrayList();
        DBCaja.getPedidosCanj(pDNI,pNumPed,listaCanjes);
        
        if(listaCanjes.size()>0){
            String codCampana,fechaPedVta,secPedVta,codProd,cantAtendia,valFrac;
            
            //envia pedidos de regalo
            for(int i=0; i<listaCanjes.size();i++){
                codCampana = FarmaUtility.getValueFieldArrayList(listaCanjes,i,0);
                fechaPedVta = FarmaUtility.getValueFieldArrayList(listaCanjes,i,1);
                secPedVta = FarmaUtility.getValueFieldArrayList(listaCanjes,i,2);
                codProd = FarmaUtility.getValueFieldArrayList(listaCanjes,i,3);
                cantAtendia = FarmaUtility.getValueFieldArrayList(listaCanjes,i,4);
                valFrac = FarmaUtility.getValueFieldArrayList(listaCanjes,i,5);                

                DBCaja.insertCanjMatriz(pDNI,pNumPed,codCampana,secPedVta,
                        codProd, cantAtendia,
                        valFrac,"A",fechaPedVta);  
                
            }
            
            
            ArrayList listaOrigenCanjes = new ArrayList();
            
            DBCaja.getOrigenPedCanj(pDNI,pNumPed,listaOrigenCanjes);
            
            if(listaOrigenCanjes.size()>0){
            //envia pedidos origen
            String codCamp,fechaPed,secPed,codProdCanj,
                   codLocalOrigen,numPedOrigen,SecOrigen,codProdOrigen,
                   cantUso,valFracMin;
                for(int i=0; i<listaOrigenCanjes.size();i++){
                    codCamp = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,0);
                    fechaPed = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,1);
                    secPed = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,2);
                    codProdCanj = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,3);
                    codLocalOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,4);
                    numPedOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,5);                
                    SecOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,6);                
                    codProdOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,7);
                    cantUso = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,8);
                    valFracMin = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,9);

                    
                    DBCaja.insertOrigenMatriz(pDNI,pNumPed,
                                            codCamp,secPed,
                                            codProdCanj,
                                            valFracMin,"A",
                                            codLocalOrigen,
                                            numPedOrigen,                                          
                                            SecOrigen,
                                            codProdOrigen,
                                            cantUso,
                                            fechaPed);

                } 
            
            }            
            
        }
        
    }

    /**
    * Se imprime VOUCHER para el remito 
    * @author JCORTEZ
    * @since 14.01.09
    */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void imprimeVoucherRemito(JDialog pDialogo,String NumRemito)
    {

     //Impresion en la matricial tal como es en Mifarma
     if(UtilityCajaElectronica.getIndImpreRemito_Matricial())
        imprimeMatricialRemito(pDialogo,NumRemito);
        //impresion en papael Termico
        imprimeTermicaRemito(pDialogo,NumRemito);     
    }
    /**
     * @author Dubilluz 
     * @since  02.05.2012
     * @param pDialogo
     * @param NumRemito
     */
    public static void imprimeMatricialRemito(JDialog pDialogo,String NumRemito){
        VariablesCaja.vRutaImpresora = FarmaVariables.vImprReporte;
         System.out.println("<<<< >>>>> "+VariablesCaja.vRutaImpresora);
        FarmaPrintService vPrint = new FarmaPrintService(26, VariablesCaja.vRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
               VariablesCaja.vEstadoSinComprobanteImpreso="S";
               log.info("**** IP :" + FarmaVariables.vIpPc);
               log.info("ERROR DE IMPRESORA : No se pudo imprimir el remito");
        }
        else
        {
            try {
                String[] pDatos = DBCajaElectronica.getDatosRemitoMatricial(NumRemito).split("@");
                /*
                Remito - 028 - 0010099676
                <<Razon Social y Nombre Local>> Mifarma S.A.C 028-LA MOLINA-PRE_PROD
                <<   Direccion del Local     >> AV. LA MOLINA MZA. J LOTE. 21 URB. RINCONADA DEL LAGO (NO INDICA) LIMA LIMA LA MOLINA
                << El envio se hace para     >> Boveda Prosegur - Banco Citibank
                <<        NUmero de Sobres   >> 61
                <<        Monto Soles        >> 20,486.75
                <<        Monto Dolares      >> 390.00
                */    
                if(pDatos.length==10){
                    String pNombreEmpresaLocal = pDatos[0].trim();
                    String pDirecLocal         = pDatos[1].trim();
                    String pParaBanco          = pDatos[2].trim();
                    String pNumeroSobres  = pDatos[3].trim();
                    String pMontSoles     = pDatos[4].trim();
                    String pMontDolares   = pDatos[5].trim();
                    String pCliente   = pDatos[6].trim();
                    String pFecha   = pDatos[7].trim();
                    String pPrecinto   = pDatos[8].trim();                    
                    String pCuenta   = pDatos[9].trim(); 
                    System.out.println("<<<<  pNombreEmpresaLocal >>>>> "+pNombreEmpresaLocal);
                    System.out.println("<<<<  pDirecLocal >>>>> "+pDirecLocal);
                    System.out.println("<<<<  pParaBanco >>>>> "+pParaBanco);
                    System.out.println("<<<<  pNumeroSobres >>>>> "+pNumeroSobres);
                    System.out.println("<<<<  pMontSoles >>>>> "+pMontSoles);
                    System.out.println("<<<<  pMontDolares >>>>> "+pMontDolares);
                    System.out.println("<<<<  pCliente >>>>> "+pCliente);
                    System.out.println("<<<<  pFecha >>>>> "+pFecha);                    
                    System.out.println("<<<<  pPrecinto >>>>> "+pPrecinto);                                        
                    System.out.println("<<<<  pCuenta >>>>> "+pCuenta);                                                            
                    vPrint.activateCondensed();
                    /*
                    vPrint.printLine("linea 1",true);
                    vPrint.printLine("linea 2",true);
                    vPrint.printLine("",true);
                    vPrint.printLine("",true);
                    vPrint.printLine("linea final",true);
*/          
                    //for(int i=0;i<=6;i++)
                    /*for(int i=0;i<=25;i++)
                       vPrint.printLine("----------------------"+i,true);*/
                    
                    //vPrint.printLine(FarmaPRNUtility.llenarBlancos(52)+pFecha,true);// -- 1
                    vPrint.printLine("",true);// -- 1
                    vPrint.printLine("",true);// -- 2
                    vPrint.printLine("",true);// -- 3
                    vPrint.printLine("",true);// -- 4
                    vPrint.printLine("",true);// -- 5
                    vPrint.printLine("",true);// -- 6
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(52)+pCuenta,true);// -- 7
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(51)+pCliente,true);// -- 8
                    vPrint.printLine("",true);// -- 9
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(55)+pNombreEmpresaLocal,true);// -- 10
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(49)+pParaBanco,true);// -- 11
                    vPrint.printLine("",true);// -- 12
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(51)+pDirecLocal,true);// -- 13
                    vPrint.printLine("",true);// -- 14
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(46)+"Cantidad Sobres => "+pNumeroSobres,true);// -- 15
                    vPrint.printLine("",true);// -- 16
                    vPrint.printLine("",true);// -- 17
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                                     FarmaPRNUtility.alinearIzquierda(valorEnLetras(pMontSoles).trim()+ " NUEVOS SOLES" ,65) + "" + 
                                     FarmaPRNUtility.llenarBlancos(15)+  "S/. "+
                                     FarmaPRNUtility.alinearIzquierda(pMontSoles,15) ,true);// -- 18
                    vPrint.printLine("",true);// -- 19
                    vPrint.printLine("",true);// -- 20
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                                     FarmaPRNUtility.alinearIzquierda(valorEnLetras(pMontDolares).trim()+ " DOLARES AMERICANOS" ,65) + "" + 
                                     FarmaPRNUtility.llenarBlancos(15)+ "$/. "+
                                     FarmaPRNUtility.alinearIzquierda(pMontDolares,15) ,true);// -- 21
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(15)+pPrecinto,true);// -- 22

                    vPrint.endPrintService();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    private static String valorEnLetras(String pCadena ) {
        
        Double valor = new Double(FarmaUtility.getDecimalNumber(pCadena));
        
        String centavos = "00";
        double doubleValor = Double.parseDouble(valor.toString());
        int numero = valor.intValue();
        int posPunto = String.valueOf(valor).indexOf(".");
        int posComa = String.valueOf(valor).indexOf(",");
        double doubleNumero = Double.parseDouble(String.valueOf(numero));
        if (posPunto > 0 || posComa > 0) {
            if (posPunto > 0)
                centavos = String.valueOf(valor).substring(posPunto + 1);
            if (posComa > 0)
                centavos = String.valueOf(valor).substring(posComa + 1);
        } else
            centavos = "00";

        String cadena = "";
        int millon = 0;
        int cienMil = 0;

        if (numero < 1000000000) {

            if (numero > 999999) {
                millon = (new Double(numero / 1000000)).intValue();
                numero = numero - millon * 1000000;
                cadena += 
                        base(millon, true) + (millon > 1 ? " MILLONES " : " MILLON ");
            }
            if (numero > 999) {
                cienMil = (new Double(numero / 1000)).intValue();
                numero = numero - cienMil * 1000;
                cadena += base(cienMil, false) + " MIL ";
            }

            cadena += base(numero, true);

            if (cadena != null && cadena.trim().length() > 0) {
                cadena += " CON ";
            }

            if (centavos.trim().length() == 1)
                centavos += "0";
            cadena += String.valueOf(centavos) + "/100";

        }

        return cadena.trim() + "";

    }    
    public static void imprimeTermicaRemito(JDialog pDialogo,String NumRemito){
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

        //if(servicio != null)
        //{
          try
          {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
            
             //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
            
            //for (int i = 0; i < servicio.length; i++)
            //{
              //PrintService impresora = servicio[i];
              //String pNameImp = impresora.getName().toString().trim(); 
              //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
              //{
              
               String vIndImpre = DBCaja.obtieneIndImpresion();
               System.out.println("vIndImpre :"+vIndImpre);
                if (!vIndImpre.equals("N"))
                {
                  //String htmlRemitos = DBCaja.obtieneDatosVoucherRem(NumRemito,FarmaVariables.vIPBD);
                   String htmlRemitos=DBCajaElectronica.getHTML_VOUCHER_REMITO(NumRemito); //ASOSA, 22.04.2010
                  log.debug("htmlRemitos:"+htmlRemitos);
                  PrintConsejo.imprimirHtml(htmlRemitos,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                    FarmaUtility.showMessage(pDialogo, "Se asignó las fechas al nuevo remito con éxito \n" +
                     "Voucher impreso con éxito.", null);
                    //FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito.",null);            
                  //break;
                }
              //}
            //}
          }catch (SQLException sqlException)
          {
            //sqlException.printStackTrace();
           /* if(sqlException.getErrorCode() == 06502){
            FarmaUtility.showMessage(pDialogo,"error: Existen demasiados valores para impresion.",null);
            }else */{log.error(null,sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", null);
            }
            
          }
        //}        
    }
    
    
    /**
     * Se imprime ticket al consultar recarga virtual 
     * @author Asolis
     * @since 11.02.2009
     */

   // public static void imprimeTicket(JDialog pDialogo,String pNumPedVta,String pFechaVenta,String pProveedor,String pTelefono,int pMonto,String pRespRecarga ,String pComunicado)
   
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void imprimeTicket(JDialog pDialogo,String pNumPedVta,int pMonto)
    
   
    {
     //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

     //if(servicio != null)
     //{
       try
       {
         //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
         
           //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
           
         //for (int i = 0; i < servicio.length; i++)
         //{
           //PrintService impresora = servicio[i];
           //String pNameImp = impresora.getName().toString().trim(); 
           //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
           //{
           
            String vIndImpre = DBCaja.obtieneIndImpresion();
            System.out.println("vIndImpre :"+vIndImpre);
             if (!vIndImpre.equals("N"))
             {
                
               String htmlTicket = DBCaja.obtieneDatosTicket(pNumPedVta,pMonto);
                  
               log.debug("htmlRemitos:"+htmlTicket);
               PrintConsejo.imprimirHtml(htmlTicket,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                 FarmaUtility.showMessage(pDialogo, "Ticket impreso con éxito.",null);            
               //break;
             }
           //}
         //}
       }catch (SQLException sqlException)
       {
         {log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de Ticket.", null);
         }
         
       }
     //}
    }
    
    public static void bloqueoCajaApertura(String pSecCaja){
        
        try{
            log.debug("Inicio de bloqueo para el cobro...");
            DBCaja.bloqueoCaja(pSecCaja.trim());
           }catch (SQLException e)
          {
            log.debug("Error:"+e);
              FarmaUtility.liberarTransaccion();
          }
        log.debug("Fin de bloqueo continua bloqueado y s");
    }
    
    /**
     * Se imprime formato por IP
     * @AUTHOR JCORTEZ
     * @SINCE 24.03.09
     * */
    
    public static void imprimePruebaTermicaPorIP(JDialog   pJDialog,
                                                  String    ruta,
                                                  String   nombreTicket){

    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(66, ruta, false);
    System.out.println("Ruta : " + VariablesCaja.vRutaImpresora + "boleta" + VariablesCaja.vNumPedVta + ".txt");
    System.out.println("VnombreTicket:" + nombreTicket);
    if ( !vPrint.startPrintService() ) {
        VariablesCaja.vEstadoSinComprobanteImpreso="S";      
         log.info("**** IP :" + FarmaVariables.vIpPc);
         log.info("**** RUTA :" + ruta);
         log.info("ERROR DE IMPRESORA : No se pudo imprimir ticket");
        FarmaUtility.showMessage(pJDialog, "ERROR DE IMPRESORA : No se pudo imprimir ticket de prueba", null);
    }else{
     // vPrint.activateCondensed();
         //JCHAVEZ 03.07.2009.sn
         System.out.println("Seteando el Color ...");
         Date fechaJava = new Date();
         System.out.println("fecha : " +fechaJava);
         System.out.println(fechaJava.getDate());                                        
         int dia=fechaJava.getDate();
         int resto= dia % 2;
         System.out.println("resto : " +resto);
         
         if(resto ==0&&VariablesPtoVenta.vIndImprimeRojo)
            vPrint.printLine((char)27+"4",true );  //rojo
         else
            vPrint.printLine((char)27+"5",true );  //negro
         //JCHAVEZ 03.07.2009.en
        log.info("**** RUTA :" + ruta);
      vPrint.printLine("***************************************",true);
      vPrint.printLine("PRUEBA DE IMPRESION TICKETERA",true);
      vPrint.printLine("NOMBRE : "+nombreTicket,true);
      vPrint.printLine(" ",true);
      vPrint.printLine("***************************************",true);
     // vPrint.deactivateCondensed();
      vPrint.endPrintService();
    }
    } 
    
    /**
     * CENTRA LA CADENA SEGUN EL TAMAÑO QUE MANDEN
     * @param pCadena
     * @param pLongitud
     * @param pCaracter
     * @return
     */
    public static String pFormatoLetra(String pCadena,int pLongitud,String pCaracter){
        int pTamaño = pCadena.trim().length();
        int numeroPos = (int)Math.floor((pLongitud - pTamaño)/2);
        String pCadenaNew = "";
        //(pLongitud - pTamaño)/2
        //Math.floor(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
        //System.out.println(Math.floor(7/2));
        for(int i=0;i<numeroPos;i++){
            pCadenaNew += pCaracter;
        }
        pCadenaNew += pCadena.trim();
        pTamaño =  pLongitud - pCadenaNew.length();
        
        for(int i=0;i<pTamaño;i++){
            pCadenaNew += pCaracter;
        }
        
        //System.out.println("cadena:"+pCadena);
        //System.out.println("numeroPos:"+numeroPos);
        //System.out.println("pCadenaNew:"+pCadenaNew);
        return  pCadenaNew;
    }    
    /**
     * Se ajusta posiciones para ticketera
     * @AUTHOR JCORTEZ
     * @SINCE 23.03.09
     * */
    private static void imprimeBoletaTicket(JDialog   pJDialog,
                                              String    pFechaBD,
                                              ArrayList pDetalleComprobante,
                                              String    pValTotalNeto,
                                              String    pValRedondeo,
                                              String    pNumComprobante,
                                              String    pNomImpreso,
                                              String    pDirImpreso,
                                              String    pValTotalAhorro,
                                              String    pruta,
                                              boolean   bol) throws Exception {
                                              
                                              
      System.out.println("IMPRIMIR TICKET No : " + pNumComprobante);
      log.info("Inicio de IMpresion TICKET");
      String indProdVirtual = "";
      VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
      
      //jcortez 06.07.09 Se verifica ruta 
        //if(bol) VariablesCaja.vRutaImpresora=pruta;
        
      FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, VariablesCaja.vRutaImpresora, false);
      
        //JCORTEZ 16.07.09 Se genera archivo linea por linea
        FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pruta, false);
        vPrintArchivo.startPrintService();
        
      log.info("Ruta : " + VariablesCaja.vRutaImpresora  + "   boleta" + VariablesCaja.vNumPedVta + ".txt");
       System.out.println("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
   
    //JMIRANDA 24/07/09 NRO DE PEDIDO ( CORRELATIVO) PARA ENVIAR X CORREO SI EXIS ERROR
        FarmaVariables.vNroPedidoNoImp = VariablesCaja.vNumPedVta;           
    if ( !vPrint.startPrintService() ) {               
                VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("ERROR DE IMPRESORA 1: No se pudo imprimir la boleta");
                                     
         }
        
    
    else { 
       /* vPrint.activateCondensed();
        log.info("**** RUTA :" + ruta);
        vPrint.printLine("PRUEBA DE IMPRESION TERMICA",true);
        //vPrint.printLine(nombreTicket,true);
        vPrint.printLine(" ",true);
        vPrint.deactivateCondensed();
        vPrint.endPrintService();*/
        
        try {
       
         //JCHAVEZ 03.07.2009.sn
         System.out.println("Seteando el Color ...");
         Date fechaJava = new Date();
         System.out.println("fecha : " +fechaJava);
         System.out.println(fechaJava.getDate());                                        
         int dia=fechaJava.getDate();
         int resto= dia % 2;
         System.out.println("resto : " +resto);
         if (resto ==0&&VariablesPtoVenta.vIndImprimeRojo)
            vPrint.printLine((char)27+"4",true );  //rojo
         else
            vPrint.printLine((char)27+"5",true );  //negro
        //JCHAVEZ 03.07.2009.en         
       
        log.info("imprime datos de cabecera de impresion");
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+ " BOTICAS MIFARMA"+FarmaPRNUtility.llenarBlancos(12),true);
     vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(12)+ " BOTICAS MIFARMA"+FarmaPRNUtility.llenarBlancos(12),true);
//        vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ " TICKET - MIFARMA S.A.C" + " " +  "RUC: "+FarmaVariables.vNuRucCia,true);
//     vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1)+ " TICKET - MIFARMA S.A.C" + " " +  "RUC: "+FarmaVariables.vNuRucCia,true);
  //JMIRANDA 13.11.09
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ " TICKET - MIFARMA S.A.C." + " " +  "RUC: "+FarmaVariables.vNuRucCia,true);
     vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1)+ " TICKET - MIFARMA S.A.C." + " " +  "RUC: "+FarmaVariables.vNuRucCia,true);
     
     vPrint.printLine(VariablesPtoVenta.vDireccionCortaMatriz,true);
     vPrintArchivo.printLine(VariablesPtoVenta.vDireccionCortaMatriz,true);
            
/*        vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 "+"          "+"CORR. "+VariablesCaja.vNumPedVta,true);
     vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 "+"          "+"CORR. "+VariablesCaja.vNumPedVta,true);   
*/
//JMIRANDA 22.08.2011 Cambio para verificar si imprime
if(UtilityVentas.getIndImprimeCorrelativo()){
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 "+"          "+"CORR. "+VariablesCaja.vNumPedVta,true);
     vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 "+"          "+"CORR. "+VariablesCaja.vNumPedVta,true);   
}
else{
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 ",true);
    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 ",true);
}
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "T"+FarmaVariables.vCodLocal+ " " + FarmaVariables.vDescCortaDirLocal,true);
     vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "T"+FarmaVariables.vCodLocal+ " " + FarmaVariables.vDescCortaDirLocal,true);   
     
        //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "D. "+FarmaVariables.vDescCortaDirLocal,true);
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket,20)+"    " + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim(),true);
     vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket,20)+"    " + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim(),true);   
        //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearDerecha("CORR. "+VariablesCaja.vNumPedVta,16),true);
        //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha:"+pFechaBD + FarmaPRNUtility.alinearDerecha("CAJA:"+VariablesCaja.vNumCaja,7)+" "+FarmaPRNUtility.alinearDerecha("TURNO:"+VariablesCaja.vNumTurnoCajaImpreso,7) ,true);
        //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha:"+pFechaBD + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumTurnoCajaImpreso,7) ,true);

        vPrint.printLine(/*FarmaPRNUtility.llenarBlancos(1) +*/ "Fecha:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro: "+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16) ,true);
        vPrintArchivo.printLine(/*FarmaPRNUtility.llenarBlancos(1) +*/ "Fecha:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro: "+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16),true);
     
        if(pNomImpreso.trim().length()>0)
           vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearIzquierda("CLIENTE:"+pNomImpreso.trim(),41),true);
             vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearIzquierda("CLIENTE:"+pNomImpreso.trim(),41),true);   
        
        //vPrint.printLine("==========================================" ,true);
         vPrint.printLine(" Cant."+"   "+"Descripcion"+"       Dscto"+"   Importe" ,true);
          vPrintArchivo.printLine(" Cant."+"   "+"Descripcion"+"          Dscto"+"   Importe" ,true);
            
        log.info("fin de impresion de cabecera");
            
        int linea = 0;
        log.info("Inicio de impresion Detalle");
            
        for (int i=0; i<pDetalleComprobante.size(); i++)
        {
            //Agregado por DVELIZ 13.10.08
            String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
            log.info("Fila detalle "+ i+ ") "+ valor);
            if(valor.equals("0.000")) valor = " ";
            //fin DVELIZ
            log.info("Detalle "+i+")"+ (ArrayList)pDetalleComprobante.get(i) );
            System.out.println("valor 2:"+valor);
            log.info("valor "+valor);
            //JMIRANDA 06.10.09
            
            double valor1 =  (UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2));
            log.error("valor1: "+valor1);
            if(valor1==0.0){
                valor = "";
            }
            else{
                valor = Double.toString(valor1);
            }
            log.error("valorXXX: "+valor);
            vPrint.printLine("" +
                             //9
                             //FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),9) + "  " +
                             //SE ESTA CENTRANDO LA CANTIDAD COMPRADA 
                             //DUBILLUZ 09/07/2009
                             pFormatoLetra(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9," ")+ "  " +
                             //20
                             
                             //OLD 13.07.2009
                             /*FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),20) + " " +
                             FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)+"    "+//,true);
                             FarmaPRNUtility.alinearIzquierda("      "+((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),15) + "  " +
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),18),true);
                             */
                            //VERSION 1
                            /*
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
                            "                                   "+ FarmaPRNUtility.alinearDerecha( 
                                                            ((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)+"    "+//,true);
                            FarmaPRNUtility.alinearIzquierda("      "+((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),15) + "  " +
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),18),true);
                            */
                            //VERSION 2
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + 
                            "       "+ 
                            //UNIDAD
                            //FarmaPRNUtility.alinearIzquierda( "      "+ ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),15) + "  " +
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + " " +
                            //LAB                             
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),9) + " "+
                            //JMIRANDA 06.10.09
                            //AHORRO         
                            FarmaPRNUtility.alinearDerecha(valor,5) + "  " +
                            //FarmaPRNUtility.alinearDerecha(UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2),5) + "  " +
                    
                            //PRECIO
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                            //FarmaPRNUtility.alinearDerecha("12,151.30",10)
                             ,true);   
                             
                             
            vPrintArchivo.printLine( "" +
                             pFormatoLetra(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9," ")+ "  " +
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + 
                            "       "+ 
                            FarmaPRNUtility.alinearIzquierda( "      "+ ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),15) + "  " +
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),11) + " "+
            //JMIRANDA 06.10.09 
            FarmaPRNUtility.alinearDerecha(UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2),5) + "  " +
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                             ,true);                             
            
            linea += 1;
            indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
            //verifica que solo se imprima un producto virtual en el comprobante
            if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
              VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
            else
              VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        }
        log.info("Fin de impresion Detalle");

        //  RECARGAS VIRTUALES
        if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
        {
            log.info("Inicio de Producto Virtual");
            vPrint.printLine("", true);
            vPrintArchivo.printLine("", true);
            
            impresionInfoVirtualTicket(vPrint,vPrintArchivo,
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                                 VariablesCaja.vNumPedVta,//Se añadio el parametro
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto
    
            log.info("Fin de Producto Virtual");
            linea = linea + 4;
            
        }
        
        
        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
          linea++;
        }
        
        //*************************************INFORMACION DEL CONVENIO*************************************************//
        //*******************************************INICIO************************************************************//

        if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
        log.info("Inicio de COnvenio");
        try
        {
          ArrayList aInfoPedConv = new ArrayList();
          log.info("Obtienes datos de Convenio");
          DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

         vPrint.printLine("------------------------------------------" ,true);
            vPrintArchivo.printLine("------------------------------------------", true);
            
          log.info("INicio de impresion de datos convenio");
          for(int i=0; i<aInfoPedConv.size(); i++)
          {
            ArrayList registro = (ArrayList) aInfoPedConv.get(i);
          //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
          String Ind_Comp=((String)registro.get(8)).trim();
          if(Ind_Comp.equalsIgnoreCase("N")){
            vPrint.printLine(FarmaPRNUtility.alinearIzquierda("Titular Cliente: "+((String)registro.get(4)).trim(),41)+"\n "+
                             FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",20),true);
                             
            vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("Titular Cliente: "+((String)registro.get(4)).trim(),41)+"\n "+
                             FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",20), true);                             
               
            /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
            String vCredDisp = ((String)registro.get(7)).trim();
            if(vCredDisp.equals(""))
            {
              vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                               FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21),true);
                               
              vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                 FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21),true);                               
            }else
            {
              vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                               FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21),true);
                vPrint.printLine("Cred Disp: S/."+vCredDisp,true);
                
              vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                 FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21),true);
                vPrintArchivo.printLine("Cred Disp: S/."+vCredDisp,true);
            } 
           } 
          }
          log.info("Fin impresion de datos convenio");

        }
          //ASOLIS 
                  //IMPRIMIR  EL  IP ,NUMERO COMPROBANTE y HORA DE IMPRESIÓN  EN CASO DE ERROR.*/
                catch(SQLException sql)
                {
                    VariablesCaja.vEstadoSinComprobanteImpreso="S";
                    
                  //sql.printStackTrace();
                  System.out.println("Error de BD "+ sql.getMessage());
                  
                    log.info("**** Fecha :"+ pFechaBD);
                    log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                    log.info("**** NUMERO COMPROBANTE BOLETA:" + pNumComprobante);
                    log.info("**** IP :" + FarmaVariables.vIpPc);
                    log.info("Error al obtener informacion del Pedido Convenio ");
                    log.info("Error al imprimir la BOLETA 2: ");
                    log.error(null,sql);
                    
                    //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                      enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
                }
                
                  catch(Exception e){
                    
                    VariablesCaja.vEstadoSinComprobanteImpreso="S";
                    
                    log.info("**** Fecha :"+ pFechaBD);
                    log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                    log.info("**** NUMERO COMPROBANTE BOLETA :" + pNumComprobante);
                    log.info("**** IP :" + FarmaVariables.vIpPc);
                    log.info("Error al imprimir la BOLETA 3: "+e);
                    
                    //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                      enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
                }
        
        //vPrint.printLine(" ",true);
        }
        
        //ERIOS 25.07.2008 imprime el monto ahorrado.
        double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
        
   //parte 1
   
   
        //*********************************************FIN*************************************************************//
        //*************************************INFORMACION DEL CONVENIO***********************************************//

        VariablesVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
        VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",10),true);
            vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",10),true);
        }
        if( VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
          VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) )
        {
            VariablesVentas.vTituloDelivery = "" ;
        } else VariablesVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
        
        if(auxTotalDcto > 0)
        {
            log.info("Imprimiendo Ahorro");
            /* old 01.09.2009
            vPrint.printLine("Descuentos en esta compra S/. "+pValTotalAhorro,true);
            vPrintArchivo.printLine("Descuentos en esta compra S/. "+pValTotalAhorro,true);
	        */
            
            //JCORTEZ 02.09.2009 Se muestra mensaje distinto si es fidelizado o no .
            String obtenerMensaje="";
            String indFidelizado="";
            log.info("Identificando cliente fidelizado");
            if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
                indFidelizado="S";
            }else 
             { indFidelizado="N"; }
            log.info("Fidelizado--> "+indFidelizado);    
            obtenerMensaje=obtenerMensaAhorro(pJDialog,indFidelizado);
            vPrint.printLine(""+obtenerMensaje+" "+"S/. "+pValTotalAhorro,true);
              vPrintArchivo.printLine(""+obtenerMensaje+" "+pValTotalAhorro,true);
              /*vPrint.printLine("Descuentos en esta compra S/. "+pValTotalAhorro,true);
                 vPrintArchivo.printLine("Descuentos en esta compra S/. "+pValTotalAhorro,true);*/

        }        
        
        
        log.info("Imprimiendo Redondeo y total");
        vPrint.printLine("------------------------------------------" ,true);
            vPrintArchivo.printLine("------------------------------------------",true);
        vPrint.printLine("Red. :S/.  " + pValRedondeo + "    Total:S/.  " + pValTotalNeto,true);
            vPrintArchivo.printLine("Red. :S/.  " + pValRedondeo + "    Total:S/.  " + pValTotalNeto,true);
            
        //vPrint.printLine(FarmaPRNUtility.alinearDerecha("Red. :S/.  " + pValRedondeo,42),true);                               
        vPrint.printLine("==========================================" ,true);
        vPrintArchivo.printLine("==========================================" ,true);
            
        //vPrint.printLine("CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso,true);
        //vPrint.printLine("VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso,true);                       
    
    log.info("Imprimiendo Tipo de Cambio");
    log.info("VariablesCaja.vFormasPagoImpresion:"+ VariablesCaja.vFormasPagoImpresion);
    
    log.info("Imprimiendo Formas de Pago");        
    int pos= VariablesCaja.vFormasPagoImpresion.indexOf("Tipo Cambio: ");
    String tcambio,fpago;
    
    //String pCajero = "CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso;
    String pCajero = "CJ: " + FarmaVariables.vIdUsu ;
    
    if (pos != -1)
    {
        tcambio = 
                VariablesCaja.vFormasPagoImpresion.substring(pos);
        fpago = 
                VariablesCaja.vFormasPagoImpresion.substring(0, pos - 
                                                             1);
        //vPrint.printLine(pCajero+" Forma(s) de pago: " + fpago ,true);
        vPrint.printLine(pCajero+" / " + fpago ,true);
        vPrintArchivo.printLine(pCajero+" / " + fpago ,true);
        vPrint.printLine(tcambio ,true);
        vPrintArchivo.printLine(tcambio ,true);
    }
    else {
        //vPrint.printLine(pCajero+" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion ,true);
        vPrint.printLine(pCajero+" / " + VariablesCaja.vFormasPagoImpresion ,true);
        vPrintArchivo.printLine(pCajero+" / " + VariablesCaja.vFormasPagoImpresion ,true);
    }

    
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(10) + VariablesVentas.vTituloDelivery ,true);
    
            //DUBILLUZ 22.08.2008 MSG DE CUPONES
            String msgCumImpresos = " ";
            if(VariablesCaja.vNumCuponesImpresos>0){
              String msgNumCupon = "";
              if(VariablesCaja.vNumCuponesImpresos==1){
                  msgNumCupon = "CUPON";
              }
              else{
                  msgNumCupon = "CUPONES";
              }
              msgCumImpresos = " UD. GANO "+VariablesCaja.vNumCuponesImpresos+ " "+
                               msgNumCupon;
            }
            
            //MODIFICADO POR DVELIZ 02.10.08
            /*
            if(auxTotalDcto > 0)
            {
            log.info("Imprimiendo Ahorro");
            vPrint.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA   "+msgCumImpresos,true);
            }else
            {
              if(VariablesCaja.vNumCuponesImpresos>0)
                 vPrint.printLine("                         "+msgCumImpresos,true);
            }*/
            
            if(VariablesCaja.vNumCuponesImpresos>0){
               vPrint.printLine("                         "+msgCumImpresos,true);
               vPrintArchivo.printLine("                         "+msgCumImpresos ,true);
            }
         log.info("Imprimiendo el mensaje final de ticket");    
            //vPrint.printLine(" " ,true);
            //vPrint.printLine(" " ,true);
            vPrint.printLine("   No se aceptan devoluciones de dinero." ,true);
            vPrintArchivo.printLine("   No se aceptan devoluciones de dinero.",true);
            //Mensaje JULIO  JMIRANDA 13.11.2009
            vPrint.printLine("  Cambio de mercadería únicamente dentro  " ,true);
            vPrint.printLine("  de las 48 horas siguientes a la compra.",true);
            vPrint.printLine("   Indispensable presentar comprobante",true);
            vPrintArchivo.printLine(" Cambio de mercadería únicamente dentro  " ,true);
            vPrintArchivo.printLine("  de las 48 horas siguientes a la compra.",true);
            vPrintArchivo.printLine("   Indispensable presentar comprobante",true);            
            //vPrint.printLine("      Cualquier cambio de mercaderia se ",true);
            //vPrint.printLine("       realizara unicamente dentro de ",true);
            //vPrint.printLine("     las 48 horas siguientes a la compra." ,true);
            //vPrint.printLine("     Indispensable presentar comprobante" ,true);
            //vPrint.printLine("             www.mifarma.com.pe" ,true);
            //vPrint.printLine("           GRACIAS POR SU COMPRA" ,true);
            //vPrint.printLine("  " ,true);
            //vPrint.printLine("  " ,true);
           
           
           
           //JCORTEZ 07.09.09 Se obtiene mensaje predeterminado
           String mensaje=DBCaja.obtieneMensajeTicket();
           if(!mensaje.equalsIgnoreCase("N")){
               vPrint.printLine("          "+mensaje,true);
               vPrintArchivo.printLine("          "+mensaje,true);
           }
           
            //vPrint.printLine("         Central Delivery 213-0777" ,true);
            //vPrintArchivo.printLine("         Central Delivery 213-0777",true);
            
        //vPrint.printLine( ""+(char)27 + "d"+(char)2,true);
        //vPrint.deactivateCondensed();
        /*dubilluz 2011.09.16*/
        if(VariablesCaja.vImprimeFideicomizo){
            String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
            String pCadena = "";
            if(lineas.length>0){
                for(int i=0;i<lineas.length;i++){
                    pCadena += lineas[i] + " ";
                }
                //PAra ticket debe ser todo en UNA SOLA LINEA
                vPrint.printLine(""+pCadena.trim(),true);
                vPrintArchivo.printLine(""+pCadena.trim(),true);
            }
            else{
            vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
            vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
            }
        }
        /*FIN dubilluz 2011.09.16*/
        log.info("Antes de End PrintService");    
        vPrint.endPrintService();
        vPrintArchivo.endPrintService();
        log.info("Fin al imprimir la boleta: " + pNumComprobante);
        VariablesCaja.vEstadoSinComprobanteImpreso="N";
        
        
            //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
            DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
            log.debug("Guardando fecha impresion cobro..."+pNumComprobante); 
        
        }
                                          catch(SQLException sql)
                                                {
                                                  //sql.printStackTrace();
                                                  VariablesCaja.vEstadoSinComprobanteImpreso="S";
                                                  System.out.println("Error de BD "+ sql.getMessage());
                                                  
                                                    log.info("**** Fecha :"+ pFechaBD);
                                                    log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                                                    log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                                                    log.info("**** IP :" + FarmaVariables.vIpPc);
                                                    log.info("Error al imprimir la boleta 4: " + sql.getMessage());
                                                    log.error(null,sql);
                                                    
                                                    //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                                                      enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
                                                }
                                                
                                                  catch(Exception e){
                                                    VariablesCaja.vEstadoSinComprobanteImpreso="S";
                                                    log.info("**** Fecha :"+ pFechaBD);
                                                    log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                                                    log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                                                    log.info("**** IP :" + FarmaVariables.vIpPc);
                                                    log.info("Error al imprimir la boleta 5: "+e);
                                                    
                                                    //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                                                      enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
                                                } 
                                      
                                      
      }
        
    
    
    
    }

    /**
     * mfajardo 
     * @param pJDialog
     * @param pNumPedVta
     */
     //mfajardo -imprime mensaje campana- 13.04.2009
    private static void imprimeMensajeCampana(JDialog pJDialog,String pNumPedVta) {

          try
          {
           // String pTipoImp = DBCaja.obtieneTipoImprConsejo();
            String vIndImpre = DBCaja.obtieneIndImpresion();
            System.out.println("vIndImpre :"+vIndImpre);
                if (!vIndImpre.equals("N"))
                {
                    String htmlTicket = DBCaja.ObtieneCampanas(pNumPedVta);
                  log.debug("htmlRemitos:"+htmlTicket);
                  if(!htmlTicket.equals("N"))
                  {
                    PrintConsejo.imprimirHtml(htmlTicket,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                  }
                }
          }catch (Exception sqlException)
          {
              {log.error(null,sqlException);
              FarmaUtility.showMessage(pJDialog, "Error al obtener los datos de Ticket.", null);
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(sqlException.toString(),"<br>Error Al Obtener Datos de Ticket");
              }
            
          }
    }
    
    /**
     * mfajardo 
     * @param pJDialog
     * imprime mensaje Ticket Anulacion - 24.04.2009
     */
    //Mfajardo 24/04/09 metodo imprimir ticket de anulacion    
     //JCORTEZ 10.06.09 Se obtiene datos de impresora por relacion ip - impresora
     //jquispe 25/03/2010 agregar el motivo de anulacion en la impresion
   public static boolean imprimeMensajeTicketAnulacion(String cajero, String turno, 
                                       String numpedido, String cod_igv, 
                                       String ruta, boolean valor, 
                                       String pIndReimpresion)throws Exception { 

        // try
        // {
           //String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
           //String vIndImpre = DBCaja.obtieneIndImpresion();
           String vIndImpre  = "S";
           boolean vResultado = false;
               if (!vIndImpre.equals("N"))
               {
                   String htmlTicket = DBCaja.ImprimeMensajeAnulacion(cajero,turno,numpedido,cod_igv,pIndReimpresion);          
                   if (!htmlTicket.equals("N"))                      
                   {
                      ArrayList myArray = null;
                      StringTokenizer st = null;
                      myArray = new ArrayList();
                      st = new StringTokenizer(htmlTicket, "Ã");
                       while (st.hasMoreTokens()) {
                           myArray.add(st.nextToken());
                       }          
                       
                       //try {                    
                           int cajaUsuario;
                           cajaUsuario = DBCaja.obtieneNumeroCajaUsuarioAux();                         
                           VariablesCaja.vNumCaja = new Integer(cajaUsuario).toString();                           
                           boolean existeImpresorasVenta = true;
                           ArrayList myArrayaux = new ArrayList();                                                                                                        
                           System.out.println("cajausuario : " + cajaUsuario);
                           /*DBCaja.obtieneSecuenciaImpresorasVenta(myArrayaux);
                           
                           if(myArray.size() <= 0)
                           {                           
                             VariablesCaja.vSecImprLocalTicket="";
                             VariablesCaja.vSerieImprLocalTicket="";
                             existeImpresorasVenta = false;                             
                           } else
                           {                             
                             VariablesCaja.vSecImprLocalTicket=((String)((ArrayList)myArrayaux.get(0)).get(3)).trim();
                             VariablesCaja.vSerieImprLocalTicket=((String)((ArrayList)myArrayaux.get(0)).get(4)).trim();                            
                             existeImpresorasVenta = true;   
                           }*/                   
                           
                           String secImprLocal = "";
                           secImprLocal = VariablesCaja.vSecImprLocalTicket;                           
                           VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal);                           
                           
                           //JCORTEZ 06.07.09 Se genera archivo 
                           //No se realizará este paso.
                           //dubilluz 14.07.2009
                           /*if(valor)
                           {
                              VariablesCaja.vRutaImpresora =ruta;
                           }
                           */
                           
                           FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(66, VariablesCaja.vRutaImpresora, false);
                           FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(66, ruta, false);
                           log.info("..start impresora ticketera: "+ VariablesCaja.vRutaImpresora);
                           vPrint.startPrintService_DU();
                           log.info("..start ruta Archivo: "+ ruta);
                           vPrintArchivo.startPrintService_DU();
                           //JCHAVEZ 03.07.2009.sn
                           log.info("Seteando el Color ...");
                           Date fechaJava = new Date();
                           log.info("fecha : " +fechaJava);
                           log.info(""+ fechaJava.getDate());                                        
                           int dia=fechaJava.getDate();
                           int resto= dia % 2;
                           log.info("resto : " +resto);
                           if(resto ==0&&VariablesPtoVenta.vIndImprimeRojo){
                               vPrint.printLine((char)27+"4",true ); //rojo 
                               vPrintArchivo.printLine((char)27+"4",true ); //rojo 
                           }
                           else
                           {
                               vPrint.printLine((char)27+"5",true ); //negro
                               vPrintArchivo.printLine((char)27+"5",true ); //negro
                           }
                          //JCHAVEZ 03.07.2009.en
                           
                               log.info("imprime datos de cabecera de impresion");
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);
                              // vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ "                                           ",true);
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Local:  " + FarmaVariables.vCodLocal+" - "+FarmaVariables.vDescCortaLocal,true);   
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Local:  " + FarmaVariables.vCodLocal+" - "+FarmaVariables.vDescCortaLocal,true);   
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de creación: " + myArray.get(7) ,true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de creación: " + myArray.get(7) ,true);
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Numero de Ticket: "+myArray.get(1),true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Numero de Ticket: "+myArray.get(1),true);
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de Anulación: "+myArray.get(2),true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de Anulación: "+myArray.get(2),true);                               
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Caja: "+myArray.get(3) + " Turno: " + myArray.get(4),true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Caja: "+myArray.get(3) + " Turno: " + myArray.get(4),true);
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Usuario: " +myArray.get(5),true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Usuario: " +myArray.get(5),true);
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Monto: " + myArray.get(6) ,true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Monto: " + myArray.get(6) ,true);
                               //JQUISPE 25.03.2010
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Motivo: " + myArray.get(9) ,true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Motivo: " + myArray.get(9) ,true);
                              
                              // vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ "                                           ",true);
                               vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);
                               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);
                               
                               log.info("..End Service Ticketera");
                               vPrint.endPrintService();
                               log.info("..End Service Archivo");
                               vPrintArchivo.endPrintService();
                               
                               vResultado = true;
                               
                       //}
                       //catch(Exception e)
                         /*    {
                                 System.out.println("Error "+ e.getMessage());                
                                 log.info("Error al imprimir ticket de anulación: " + e.getMessage());
                                 log.error(null,e);
                             }                                              
                        */
                        
                          //JCORTEZ 16.07.09 Se guarda fecha de anulacion por comprobantes
                          DBCaja.actualizaFechaImpr(numpedido,""+myArray.get(8),"A");
                       log.info("Guardando fecha impresion Anulacion ..."+myArray.get(8));
                   }
                   
                   
               }
               
        // }catch (Exception e)
        // { 
             /*
              * 
             FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                           FarmaVariables.vCodLocal,
                                           "dubilluz",
                                           "Error de Impresión Ticket Anulado",
                                           "Error de Impresión",
                                           "Error al imprimir ticket Anulado :<br>"+
                                           "Correlativo : " +numpedido+"<br>"+
                                           "Error: " + e,
                                           "operador");
                                           //"joliva;operador;daubilluz@gmail.com");
            log.info("Error Imprimir Ticket Anulado : "+e);
            */
        // }
               
          return vResultado;
    }
    
    private static String getDetallePrecio(String pTipoPedido,ArrayList pListaDetalle,int p){
        String pcadena;
        if(pTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
        {
           pcadena = FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(17)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
           FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(18)).trim(),10)                                ;
        }
        else
        {
           pcadena = FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
           FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(5)).trim(),10) ;
        }
        
        return pcadena;
    }
    
    
    /**
     * Se valida ip de la maquina para la emision de ticket
     * @author  JCORTEZ
     * @since  09.06.09
     * */
    private static boolean  validaImpresioPorIP(String IP,String TipComp,JDialog pJDialog, Object pObjectFocus){
    boolean valor=false;
    String resp="";
    
        try{    
        resp=DBCaja.validaImpresioPorIP(IP,TipComp);
        if(resp.trim().equalsIgnoreCase("S"))
        valor=true;
        
        }catch(SQLException sql){
            sql.printStackTrace();
            FarmaUtility.showMessage(pJDialog,"Ocurrio un error al cargar el reporte.\n"+sql.getMessage(),pObjectFocus);
            
              //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                enviaErrorCorreoPorDB(sql.toString(),null);
          }
        return valor;
    }
    
    
    
    /**
       * Valida si existe impresora asociada a la IP desde donde se realizara el cobro
       * @AUTHOR JCORTEZ
       * @SINCE 09.06.09
       */
    public static boolean existeIpImpresora(JDialog pDialog, Object pObjectFocus) {
        boolean existeImpresoraIP = true;
        String Sec="";
          try {
              log.info("******* FarmaVariables.vIpPc : "+FarmaVariables.vIpPc);
              Sec = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);
              log.info("******* Secuencial de impresora : "+Sec);
                if(Sec.trim().equalsIgnoreCase("N")){
                    existeImpresoraIP=false;
                    FarmaUtility.showMessage(pDialog,"La IP actual no tiene asignada ninguna impresora. Verifique !!!", pObjectFocus);
                }
          } catch (SQLException sqlException) {
              //sqlException.printStackTrace();
              log.error(null, sqlException);
              FarmaUtility.showMessage(pDialog,"Error al obtener relacion impresora - IP " + sqlException.getErrorCode(),pObjectFocus);
          }
        log.info("******* existeImpresoraIP: "+existeImpresoraIP);
        return existeImpresoraIP;
    }


    public static void pruebaImpTermicaPersonalizada(JDialog pDialogo,Object pObject,String pNombreImpresora,String pTipo)
    {
      PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      numeroCorrel++;
      boolean vIndImpresion = false;
      String numAux = "000"+numeroCorrel
          ;
      String pCodCupon = "9999999999"+numAux.substring(numAux.length()-3, numAux.length());
      int cant_cupones_impresos = 0;
      if(servicio != null)
      {
        try
        {
        //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
          int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
          for (int i = 0; i < servicio.length; i++)
          {
            PrintService impresora = servicio[i];
            String pNameImp = impresora.getName().toString().trim();
            String pNombre = retornaUltimaPalabra(pNameImp,"\\");
            //if (retornaUltimaPalabra(pNameImp,"\\").trim().toUpperCase().indexOf(pNombreImpresora.trim().toUpperCase()) != -1)
            if (pNombre.trim().toUpperCase().equalsIgnoreCase(pNombreImpresora.trim().toUpperCase()))  
            {
              vIndImpresion = true;
              String vCupon = DBCaja.pruebaImpresoraTermica(pCodCupon);
              log.debug(" prueba de impresion termica a : "+ impresora.getName());
              log.debug(" pNombreImpresora:"+ pNombreImpresora);
              log.debug(" pTipo:"+ pTipo);
                
              PrintConsejo.imprimirCupon(vCupon,impresora,pTipo,pCodCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              FarmaUtility.showMessage(pDialogo, 
                                        "Se realizó la prueba de impresión a "+pNombreImpresora.trim()+
                                        " , recoja la impresión.", pObject);
              
              break;
            }
          }
          
            if(!vIndImpresion){
                FarmaUtility.showMessage(pDialogo, 
                                         "No existe la impresora térmica "+pNombreImpresora.trim()+
                                          "\nverificar que se encuentre instalada en la PC.", pObject);
            }
        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al realizar prueba de impresion.",pObject);

        }
        
      }
    }
    
    public static String retornaUltimaPalabra(String pCadena,String pSeparador){
        System.out.println(pCadena);
        System.out.println(pSeparador);

        
        String pLetra = "";
        String pPalabraOut="";
        for(int i=pCadena.length()-1;i>=0;i--){
            pLetra = pCadena.charAt(i)+"";
            if(pLetra.trim().equalsIgnoreCase(pSeparador.trim())){
                break;
            }
            else{
                pPalabraOut = pLetra + pPalabraOut;
            }
        }
        return pPalabraOut.trim();
    }
    
    public static void enviaErrorCorreoPorDB(String message, String vCorrelativo)  {
        //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime 
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                      FarmaVariables.vCodLocal,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                      VariablesPtoVenta.vDestEmailErrorImpresion,
                                      "Error al Imprimir Pedido Completo ",
                                      "Error de Impresión StartPrintService",
                                      "Se produjo un error al imprimir un pedido :<br>"+
                                      //"Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                      "Correlativo : " +vCorrelativo+"<br>"+
                                      "IP : " +FarmaVariables.vIpPc+"<br>"+
                                      "Error: " + message ,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_CC_ERROR_IMPRESION
                                      "");
        log.info("Error en BD al Imprimir los Comprobantes del Pedido.\n"+message);
        
    }
    
    public static void enviaErrorCorreoPorDB(Exception message, String vCorrelativo)  {
        //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime 
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                      FarmaVariables.vCodLocal,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                      VariablesPtoVenta.vDestEmailErrorImpresion,
                                      "Error al Imprimir Pedido Completo ",
                                      "Error de Impresión StartPrintService",
                                      "Se produjo un error al imprimir un pedido :<br>"+
                                      //"Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                      "Correlativo : " +vCorrelativo+"<br>"+
                                      "IP : " +FarmaVariables.vIpPc+"<br>"+
                                      "Error: " + message ,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_CC_ERROR_IMPRESION
                                      "");
        log.info("Error en BD al Imprimir los Comprobantes del Pedido.\n"+message);
        
    }
    
    
    /**
    * Se imprime la comanda al cobrar un pedido tipo delivery
    * @AUTHOR JCORTEZ
    * @SINCE 07.08.09
    */
    public static void imprimeDatosDeliveryLocal(JDialog pDialogo,String NumPed)
    {
       try
       {
            String vIndImpre = DBCaja.obtieneIndImpresion();
            System.out.println("vIndImpre :"+vIndImpre);
             if (!vIndImpre.equals("N"))
             { 
               String htmlDelivery = DBCaja.obtieneDatosDeliveryLocal(NumPed,FarmaVariables.vIPBD);
               PrintConsejo.imprimirHtml(htmlDelivery,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
               //break;
             }
       }catch (SQLException sqlException)
       {
         //sqlException.printStackTrace();
         log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, "Error al obtener datos del pedido delivery Local.", null);
       }
    }
    
    
    /**
     * Se elimina productos regalo de encarte vigente dentro del pedido.
     * @author JCORTEZ
     * @since  13.08.2009
     */
    public static void liberaProdRegalo(String pNumPed,
                                             String pAccion,
                                             String pIndEliminaRespaldo) throws SQLException{//ASOSA,13.07.2010 - agregue el throws y quit el try-catch para que no este cun try-catch dentro de otro
        //try{
        
            //JCORTEZ 13.08.09  Se elimina producto regalo
            System.out.println("****************JCORTEZ********************");
            System.out.println("pAccion-->"+pAccion);
            System.out.println("pIndEliminaRespaldo-->"+pIndEliminaRespaldo);
            //DBCaja.eliminaProdRegalo(pNumPed,pAccion,pIndEliminaRespaldo); antes
            DBCaja.eliminaProdRegalo_02(pNumPed,pAccion,pIndEliminaRespaldo); //ASOSA, 13.07.2010
            //return true;
        /*}catch (SQLException e){
            e.printStackTrace();
            log.debug("Error al eliminar productos regalo encarte.");
            //return false;
        }*/
    }
    
             
     /**
      * Se imprime cupones regalo 
      * @AUTHOR JCORTEZ
      * @SINCE 18.07.09
      */
     public static void imprimeCuponRegalo(JDialog pDialogo,String vCodeCupon,String Dni)
     {
       int cant_cupones_impresos = 0;
       
         try
         {
           //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
           //String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
           int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
               String vCupon = DBCaja.obtieneImprCuponRegalo(FarmaVariables.vIPBD,vCodeCupon,Dni);
               if(!vCupon.equals("N"))
               {
                   log.debug("cupon regalo a imprimir : "+vCupon);
                   PrintConsejo.imprimirCupon(vCupon,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp,vCodeCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                   log.debug("despues a imprimir");
                   // -- Proceso autonomo que tiene COMMIT
                   DBCaja.cambiaIndImpresionCupon(VariablesCaja.vNumPedVta,vCodeCupon);
                   cant_cupones_impresos ++;
               }   
         }
         catch (SQLException sqlException)
         {
          //sqlException.printStackTrace();
           log.error(null,sqlException);
          FarmaUtility.showMessage(pDialogo,"Error al obtener los consejos.", null);
         }
            
    }
    
    /**
     * Se obtiene mensaje de ahorro en comprobantes 
     * @AUTHOR JCORTEZ
     * @SINCE  03.09.2009
     * */
   public static String obtenerMensaAhorro(JDialog pDialogo,String indFid){
    
     String mensaje="";
        try
        {   
         mensaje = DBCaja.obtieneMensajeAhorro(indFid);
        }
        catch (SQLException sqlException)
        {
         sqlException.printStackTrace();
          log.error(null,sqlException);
         ///FarmaUtility.showMessage(pDialogo,"Error al obtener mensaje de descuento.", null);
        }
    return mensaje;
    
    }
    
    /**
     * Se valida guias pendientes
     * @AUTHOR JCORTEZ
     * @SINCE  27.10.2009
     */
    public static boolean validaGuiasPendAlmc()
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.ExistsGuiasPendAlmc().trim();
        } catch(SQLException sql){
            sql.printStackTrace();
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
  
    /**
    * Se imprime VOUCHER para diferencias
    * @author JCHAVEZ
    * @since 23.11.09
    */     
    public static void imprimeVoucherDiferencias(JDialog pDialogo)
    {
     //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

     //if(servicio != null)
     //{
       try
       {
         //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
         
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
         
         //for (int i = 0; i < servicio.length; i++)
         //{
           //PrintService impresora = servicio[i];
           //String pNameImp = impresora.getName().toString().trim(); 
           //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
           //{
           
            String vIndImpre = DBCaja.obtieneIndImpresion();
            System.out.println("vIndImpre :"+vIndImpre);
             if (!vIndImpre.equals("N"))
             {
               String htmlDiferencias = DBRecepCiega.obtieneDatosVoucherDiferencias();
               log.debug("htmlDiferencias:"+htmlDiferencias);
               PrintConsejo.imprimirHtml(htmlDiferencias,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);
                 FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito. \n", null);
                 //FarmaUtility.showMessage(pDialogo, "Voucher impreso con éxito.",null);            
               //break;
             }
           //}
         //}
       }catch (SQLException sqlException)
       {
         //sqlException.printStackTrace();
        /* if(sqlException.getErrorCode() == 06502){
         FarmaUtility.showMessage(pDialogo,"error: Existen demasiados valores para impresion.",null);
         }else */{log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", null);
         }
         
       }
     //}

    }  
    /**
     * Se valida guias pendientes de confirmar de locales
     * @AUTHOR JMIRANDA
     * @SINCE  15.12.2009
     */
    public static boolean validaGuiasXConfirmarLocal()
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.ExisteGuiasXConfirmarLocal().trim();
            log.debug("pRes ConfirmarLocal: "+pRes);
        } catch(SQLException sql){
            sql.printStackTrace();
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    
    /**
        * Se imprime VOUCHER por pedido convenio
        * @AUTHOR Jorge Cortez Alvarez
        * @SINCE 07.03.2010
        */
        public static void imprimeDatoConvenio(JDialog pDialogo,
                                                String NumPed,
                                                  String CodConvenio, 
                                                    String CodCli)
        {
           try
           {
                String vIndImpre = DBCaja.obtieneIndImpresion();
                System.out.println("vIndImpre :"+vIndImpre);
                 if (!vIndImpre.equals("N"))
                 {
                     System.out.println("NumPed :"+NumPed);  
                     System.out.println("CodConvenio :"+CodConvenio);  
                     System.out.println("CodCli :"+CodCli);  
                   String htmlDelivery = DBCaja.obtieneDatosConvenio(NumPed,CodConvenio,CodCli,
                                                                        FarmaVariables.vIPBD);
                     System.out.println(htmlDelivery);  
                   PrintConsejo.imprimirHtml(htmlDelivery,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);
                 }
           }catch (SQLException sqlException)
           {
             log.error(null,sqlException);
             FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de convenio.", null);
           }
        }
    
    
    /**
     * Prueba Impresora Termica de Stickers
     * @author Juan Quispe
     * @since  28.12.2010
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static boolean pruebaImpresoraTermicaStk(JDialog pDialogo,Object pObject,double pCantidadFilas,int r_incio,int r_fin)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      numeroCorrel++;
      String numAux = "000"+numeroCorrel;
      String pCodCupon = "9999999999"+numAux.substring(numAux.length()-3, numAux.length());
      int cant_cupones_impresos = 0;
        String vCupon=null;
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
          
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP         
          
          int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
          
          
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{
              
              vCupon = DBCaja.pruebaImpresoraTermStick(pCodCupon,r_incio,r_fin);
            System.out.println(vCupon);
              log.debug(" prueba de impresion termica...");
            //String vCupon = "";
           
            //JSANTIVANEZ 10.01.2011
            //metodo para obtener la ruta de la impresora...
            //si es 'n' no se ha declarado la ip(no hay impresora)
            PrintService temporalNomImpCupon;
            
            temporalNomImpCupon=VariablesPtoVenta.vImpresoraActual;
            
            boolean flag=nombreImpSticker();
            
            if (flag==true)
            
           PrintConsejo.imprimirCuponStick(vCupon,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp,pCodCupon, cantIntentosLectura,
                                             pCantidadFilas);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              //break;
            //}
          //}
          
          
          //JS
          /*FarmaUtility.showMessage(pDialogo, 
                                  "Se realizó la prueba de impresión, recoja la impresión.", pObject);*/
           else
            
                 JOptionPane.showMessageDialog(null, "No se encontró la impresora sticker :"+                                            
                                               "\nVerifique que tenga instalada la impresora.", 
                                               "Mensaje del Sistema", 
                                               JOptionPane.WARNING_MESSAGE);
                           
          VariablesPtoVenta.vImpresoraActual=temporalNomImpCupon;

        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al realizar prueba de impresión.",pObject);

        }
        if (vCupon== null )
            return false;//NO hay datos
        else
                return true;//hay datos
      //}
    }        
    
    //JSANTIVANEZ 10.01.2011
    public static boolean  nombreImpSticker()
    {
        String pNombreImpresora = "";
        String nameImpSticker = ""; //nombre impresora 
        
        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        boolean pEncontroImp = false;
      if(servicio != null)
      {
        try
        {
                  
            //devuelve nombre impresora sticker "esticker01"
                  nameImpSticker=DBCaja.obtieneNameImpSticker();
            VariablesPtoVenta.vTipoImpTermicaxIp = "01"; //tipo epson
                  System.out.println("Tipo Impresora :" + VariablesPtoVenta.vTipoImpTermicaxIp);  
                  System.out.println("Buscando impresora :"+nameImpSticker);
                  System.out.println("impresoras..encontradas...");
                  for (int i = 0; i < servicio.length; i++)
                  {
                    PrintService impresora = servicio[i];
                    String pNameImp = impresora.getName().toString().trim();
                    pNombreImpresora = retornaUltimaPalabra(pNameImp,"\\").trim();
                   
                    //Buscara el nombre.
                    System.out.println(i+") pNameImp:"+pNameImp);
                    System.out.println(i+") pNombreImpresora:"+pNombreImpresora);
                      System.out.println(i+") nameImpSticker:"+nameImpSticker);  
                    System.out.println("**************************************");
                    if (pNombreImpresora.trim().toUpperCase().equalsIgnoreCase(nameImpSticker.toUpperCase()))
                    {
                      System.err.println("Encotró impresora térmica");
                      pEncontroImp = true;
                      VariablesPtoVenta.vImpresoraActual =  impresora;
                      break;
                    }
                  }
        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
        }
        }
        
        return pEncontroImp;
        /*if(!pEncontroImp){
          JOptionPane.showMessageDialog(null, "No se encontró la impresora sticker :"+
                                        nameImpSticker+
                                        "\nVerifique que tenga instalada la impresora.", 
                                        "Mensaje del Sistema", 
                                        JOptionPane.WARNING_MESSAGE);
        }*/
    }
    public static String getMensajeFideicomizo(){
        String pCadena = "";
        try {
            pCadena = DBCaja.getMensajeFideicomizo();
        } catch (SQLException e) {
            e.printStackTrace();
            pCadena = "";
        }
        return pCadena.trim();
    }
    public static String base(int numero, boolean fin) {

        String cadena = "";
        int unidad = 0;
        int decena = 0;
        int centena = 0;

        if (numero < 1000) {

            if (numero > 99) {
                centena = (new Double(numero / 100)).intValue();
                numero = numero - centena * 100;
                if (centena == 1 && numero == 0)
                    cadena += "CIEN ";
                else
                    cadena += centenas(centena) + " ";
            }

            if (numero > 29) {
                decena = (new Double(numero / 10)).intValue();
                numero = numero - decena * 10;
                if (numero > 0)
                    cadena += 
                            decenas(decena) + " Y " + unidad(numero, false) + " ";
                else
                    cadena += decenas(decena) + " ";
            } else {
                cadena += unidad(numero, fin);
            }
        }

        return cadena.trim();

    }    
    public static String centenas(int numero) {

        String[] aCentenas = 
        { "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", 
          "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS" };

        return (numero == 0 ? "" : aCentenas[numero - 1]);

    }    
    public static String decenas(int numero) {

        String[] aDecenas = 
        { "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", 
          "SETENTA", "OCHENTA", "NOVENTA" };

        return (numero == 0 ? "" : aDecenas[numero - 1]);

    }
    public static String unidad(int numero, boolean fin) {
        String[] aUnidades = 
        { "UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", 
          "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", 
          "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE", 
          "VEINTIUNO", "VEINTIDOS", "VEINTITRES", "VEINTICUATRO", 
          "VEINTICINCO", "VEINTISEIS", "VEINTISIETE", "VEINTIOCHO", 
          "VEINTINUEVE" };
        String cadena = "";

        if (numero > 0) {
            if (numero == 1 && fin)
                cadena = "UNO";
            else
                cadena = aUnidades[numero - 1];
        }

        return cadena.trim();
    }
      public static boolean isPedidoConvenioMFBTL(String pNumPedVta){
        String pCadena = "";
        try {
            pCadena = DBCaja.getIndPedConvMFBTL(pNumPedVta);
        } catch (SQLException e) {
            e.printStackTrace();
            pCadena = "N";
        }
        if(pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }
}
