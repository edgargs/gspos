package mifarma.ptoventa.inventario.reference;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.SQLException;

import mifarma.common.*;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.*;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : UtilityInventario.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* ERIOS      22.03.2005   Creación<br>
* <br>
* @author Edgar Rios Navarro<br>
* @version 1.0<br>
*
*/

public class UtilityInventario {

  private ArrayList myDatos = new ArrayList();
  private int pagComprobante = 1;

  private ArrayList exoneradosIGV = new ArrayList();

  private String nombreTitular = "";
  private String nombrePaciente = "";

	/**
	 * Constructor
	 */
	public UtilityInventario() {
  }
  //imprimeComprobanteGuias
  //JQUISPE 11.05.2010      
  public static void imprimeComprobanteGuias(JDialog pJDialog,
                                            ArrayList pDetalleComprobante,
                                            ArrayList pTotalesComprobante,
                                            String pTipCompPago,
                                            String pNumComprobante,
                                            int pTipoImpresion ) throws Exception {
    String fechaBD = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
    VariablesInventario.vDirecOrigen_Transf = DBInventario.getDireccionOrigenLocal();
    VariablesRecepCiega.vDirecOrigen_Transf = VariablesInventario.vDirecOrigen_Transf;
      //JMIRANDA 16.02.2010
      //MODIFICADO JQUISPE 11.05.2010
      //PEQUEÑA
      if(VariablesInventario.vTipoFormatoImpresion <= 8)
      {
          //validar aqui si es Guia generada en Transf x Recepcion Ciega
          if(!VariablesInventario.vIndTransfRecepCiega){
              imprimeGuia2(pJDialog, fechaBD, pDetalleComprobante,pTipoImpresion);
          }else{
              imprimeGuiaRecepCiega2(pJDialog, fechaBD, pDetalleComprobante,pTipoImpresion);
              VariablesInventario.vIndTransfRecepCiega = false;
          }    
      }
      else{
          // PARA GRANDE
          //JMIRANDA 16.02.2010
          //validar aqui si es Guia generada en Transf x Recepcion Ciega
          if(!VariablesInventario.vIndTransfRecepCiega){
              imprimeGuia(pJDialog, fechaBD, pDetalleComprobante);
          }else{
              imprimeGuiaRecepCiega(pJDialog, fechaBD, pDetalleComprobante);
              VariablesInventario.vIndTransfRecepCiega = false;
          }           
      }
      
  }
        
  public static void imprimeComprobantePago(JDialog pJDialog,
                                            ArrayList pDetalleComprobante,
                                            ArrayList pTotalesComprobante,
                                            String pTipCompPago,
                                            String pNumComprobante) throws Exception {
    String fechaBD = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
    VariablesInventario.vDirecOrigen_Transf = DBInventario.getDireccionOrigenLocal();
    VariablesRecepCiega.vDirecOrigen_Transf = VariablesInventario.vDirecOrigen_Transf;
      //JMIRANDA 16.02.2010
      //validar aqui si es Guia generada en Transf x Recepcion Ciega
      if(!VariablesInventario.vIndTransfRecepCiega){
          imprimeGuia(pJDialog, fechaBD, pDetalleComprobante);
      }else{
          imprimeGuiaRecepCiega(pJDialog, fechaBD, pDetalleComprobante);
          VariablesInventario.vIndTransfRecepCiega = false;
      }    
  }

  public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus)
  {
    try
    {
      ArrayList secGuia = DBInventario.getSecuencialGuia(VariablesInventario.vNumNotaEs,VariablesCaja.vSecImprLocalGuia);
      for(int i=0; i<secGuia.size(); i++)
      {
      
      //System.out.println("VariablesCaja.vNumCompImprimir="+VariablesCaja.vNumCompImprimir);
      //System.out.println("VariablesCaja.vSecImprLocalGuia="+VariablesCaja.vSecImprLocalGuia);
      /*if(VariablesCaja.vNumCompImprimir.equalsIgnoreCase(""))
      {*/
    	  VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, VariablesCaja.vSecImprLocalGuia, pObjectFocus);
      /*}
      else
      {
      reConfiguraCaja(VariablesCaja.vSecImprLocalGuia,VariablesCaja.vNumCompImprimir.substring(4,VariablesCaja.vNumCompImprimir.length()));
      }*/
        if(VariablesCaja.vNumCompImprimir.equalsIgnoreCase(""))
        {
          FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog, "No se pudo determinar el Numero de Guia. Verifique!!!", pObjectFocus);
          return;
        }
        if(!obtieneDetalleImprComp(pJDialog, ((ArrayList)secGuia.get(i)).get(0).toString().trim(), pObjectFocus)) return;
        //System.out.println("secCompPago : " + String.valueOf(i));
        //ruta de roporte
        VariablesCaja.vRutaImpresora = obtieneRutaImpresora();
        
        actualizaNumComp_Impresora(VariablesCaja.vSecImprLocalGuia);
        
        //System.out.println(VariablesInventario.vNumNotaEs);
        actualizaGuiaImpreso(VariablesInventario.vNumNotaEs,((ArrayList)secGuia.get(i)).get(0).toString().trim(),VariablesCaja.vNumCompImprimir);  
        //AGREGADO 13/06/2006 ERIOS
        actualizaNumGuiaKardex(VariablesInventario.vNumNotaEs,VariablesCaja.vArrayList_DetalleImpr,((ArrayList)secGuia.get(i)).get(0).toString().trim());
        
        //MODIFICADO 14/06/2006 ERIOS
        imprimeComprobantePago(pJDialog,
                               VariablesCaja.vArrayList_DetalleImpr,
                               VariablesCaja.vArrayList_TotalesComp,
                               VariablesVentas.vTip_Comp_Ped,
                               VariablesCaja.vNumCompImprimir);
        
        FarmaUtility.aceptarTransaccion();
        System.out.println("VariablesInventario.vCant 2 : " + VariablesInventario.vCant);
        System.out.println("i : " + (i + 1));
        //Inicio Adicion Paulo
        if (VariablesInventario.vCant.equalsIgnoreCase("")||VariablesInventario.vCant.equalsIgnoreCase("0"))
        {
          continue;
        } else if (Integer.parseInt(VariablesInventario.vCant)== i + 1)
        {
          break;
        }
        //Fin Adicion Paulo
      }
      
      //FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog, "Guias Impresas con Exito",pObjectFocus);
    } catch(SQLException sql){
      FarmaUtility.liberarTransaccion();
      if(sql.getErrorCode() == 20061)
        FarmaUtility.showMessage(pJDialog, "No puede imprimir, porque no cuenta con guías secuenciales disponibles.\nVerifique y corrija el número de guía inicial.",pObjectFocus); 
      else
      {
      sql.printStackTrace();
      FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir las Guias de la Transferencia.\n" + sql,pObjectFocus);
      }
    } catch(Exception e){
      FarmaUtility.liberarTransaccion();
      e.printStackTrace();
      FarmaUtility.showMessage(pJDialog, "Error en la Aplicacion al Imprimir las Guias de la Transferencia.\n" + e,pObjectFocus);
    }
  }
  
  //JQUISPE 11.05.2010 CAMBIOS PARA IMPRESION
    public static void procesoImpresionGuias(JDialog pJDialog, Object pObjectFocus ,int pTipoFormatoImpresion)
    {
      try
      {
        ArrayList secGuia = DBInventario.getSecuencialGuia(VariablesInventario.vNumNotaEs,VariablesCaja.vSecImprLocalGuia);
        for(int i=0; i<secGuia.size(); i++)
        {
        
        //System.out.println("VariablesCaja.vNumCompImprimir="+VariablesCaja.vNumCompImprimir);
        //System.out.println("VariablesCaja.vSecImprLocalGuia="+VariablesCaja.vSecImprLocalGuia);
        /*if(VariablesCaja.vNumCompImprimir.equalsIgnoreCase(""))
        {*/
            VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, VariablesCaja.vSecImprLocalGuia, pObjectFocus);
        /*}
        else
        {
        reConfiguraCaja(VariablesCaja.vSecImprLocalGuia,VariablesCaja.vNumCompImprimir.substring(4,VariablesCaja.vNumCompImprimir.length()));
        }*/
          if(VariablesCaja.vNumCompImprimir.equalsIgnoreCase(""))
          {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(pJDialog, "No se pudo determinar el Numero de Guia. Verifique!!!", pObjectFocus);
            return;
          }
          if(!obtieneDetalleImprComp(pJDialog, ((ArrayList)secGuia.get(i)).get(0).toString().trim(), pObjectFocus)) return;
          //System.out.println("secCompPago : " + String.valueOf(i));
          //ruta de roporte
          VariablesCaja.vRutaImpresora = obtieneRutaImpresora();
          
          actualizaNumComp_Impresora(VariablesCaja.vSecImprLocalGuia);
          
          //System.out.println(VariablesInventario.vNumNotaEs);
          actualizaGuiaImpreso(VariablesInventario.vNumNotaEs,((ArrayList)secGuia.get(i)).get(0).toString().trim(),VariablesCaja.vNumCompImprimir);  
          //AGREGADO 13/06/2006 ERIOS
          actualizaNumGuiaKardex(VariablesInventario.vNumNotaEs,VariablesCaja.vArrayList_DetalleImpr,((ArrayList)secGuia.get(i)).get(0).toString().trim());
          
          //MODIFICADO 14/06/2006 ERIOS
          /*imprimeComprobantePago(pJDialog,
                                 VariablesCaja.vArrayList_DetalleImpr,
                                 VariablesCaja.vArrayList_TotalesComp,
                                 VariablesVentas.vTip_Comp_Ped,
                                 VariablesCaja.vNumCompImprimir);
            
            */
          //MODIFCADO JQUISPE 11/05/2010  
          imprimeComprobanteGuias(pJDialog,
                                 VariablesCaja.vArrayList_DetalleImpr,
                                 VariablesCaja.vArrayList_TotalesComp,
                                 VariablesVentas.vTip_Comp_Ped,
                                 VariablesCaja.vNumCompImprimir,
                                 pTipoFormatoImpresion);  
          
          FarmaUtility.aceptarTransaccion();
          System.out.println("VariablesInventario.vCant 2 : " + VariablesInventario.vCant);
          System.out.println("i : " + (i + 1));
          //Inicio Adicion Paulo
          if (VariablesInventario.vCant.equalsIgnoreCase("")||VariablesInventario.vCant.equalsIgnoreCase("0"))
          {
            continue;
          } else if (Integer.parseInt(VariablesInventario.vCant)== i + 1)
          {
            break;
          }
          //Fin Adicion Paulo
        }
        
        //FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog, "Guias Impresas con Exito",pObjectFocus);
      } catch(SQLException sql){
        FarmaUtility.liberarTransaccion();
        if(sql.getErrorCode() == 20061)
          FarmaUtility.showMessage(pJDialog, "No puede imprimir, porque no cuenta con guías secuenciales disponibles.\nVerifique y corrija el número de guía inicial.",pObjectFocus); 
        else
        {
        sql.printStackTrace();
        FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir las Guias de la Transferencia.\n" + sql,pObjectFocus);
        }
      } catch(Exception e){
        FarmaUtility.liberarTransaccion();
        e.printStackTrace();
        FarmaUtility.showMessage(pJDialog, "Error en la Aplicacion al Imprimir las Guias de la Transferencia.\n" + e,pObjectFocus);
      }
    }
  

  private static void imprimeGuia(JDialog pJDialog, String pFechaBaseDatos, 
                                  ArrayList pDetallePedido)
    throws Exception
  {
    FarmaPrintService vPrint = 
      new FarmaPrintService(66, VariablesCaja.vRutaImpresora, false);
    if (!vPrint.startPrintService())
      throw new Exception("Error en Impresora. Verifique !!!");
	vPrint.activateCondensed();
	String dia = pFechaBaseDatos.substring(0,2);
    String mesLetra = 
      FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 
                                                                                  5)));
	String ano = pFechaBaseDatos.substring(6,10);
	String hora = pFechaBaseDatos.substring(11,19);

	vPrint.setStartHeader();
	vPrint.activateCondensed();
	//vPrint.printLine("",true);
        
	if(VariablesPtoVenta.vIndDirMatriz){
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(45) +VariablesPtoVenta.vDireccionMatriz ,true);	     
	}
    vPrint.printLine("", true); //vNumeroPedido +
    vPrint.printLine("     "+"LOCAL: " + FarmaVariables.vDescCortaLocal + 
                     "          CORR." + VariablesInventario.vNumNotaEs + 
                     "          No. " + 
                     VariablesCaja.vNumCompImprimir.substring(0, 3) + "-" + 
                     VariablesCaja.vNumCompImprimir.substring(3), true);
/*    vPrint.printLine("     "+"MOTIVO: " + 
                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim(), 
                                                      64), true);*/
    //JMIRANDA 10.12.09
    if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
    vPrint.printLine("     "+"MOTIVO: " + 
                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                     +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                      64),true);
	vPrint.printLine("",true);
    }
    else {
        vPrint.printLine("     "+"MOTIVO: " + 
                         FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                         ,64),true);
            vPrint.printLine("",true);    
    }
    vPrint.printLine("                  " + dia + " " + mesLetra + " " + 
                     ano + "  " + "       " + "                  " + dia + 
                     " " + mesLetra + " " + ano + "  " + "       ", true);
    vPrint.printLine("                  " + 
                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDestino_Transf.trim(), 
                                                      64), true);
    vPrint.printLine("                  " + 
                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vRucDestino_Transf.trim(), 
                                                      64) + 
                     "              " + FarmaVariables.vCodLocal.trim() + 
                     " - " + FarmaVariables.vDescCortaLocal.trim(), true);
    vPrint.printLine("                  " + 
                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDirecDestino_Transf.trim(), 
                                                      64) + 
                     "              " + 
                     getDireccion(VariablesInventario.vDirecOrigen_Transf), 
                     true);
    vPrint.printLine("                  " + 
                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 
                                                      64) + 
                     "              " + 
                     VariablesInventario.vCodDestino_Transf.trim() + 
                     " - " + VariablesInventario.vDestino_Transf, true);
    vPrint.printLine("                  " + 
                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vRucTransportista_Transf.trim(), 
                                                      64) + 
                     "              " + 
                     getDireccion(VariablesInventario.vDirecDestino_Transf), 
                     true);
	//vPrint.printLine("                  " + VariablesInventario.vDirecTransportista_Transf.trim(),true);
	//vPrint.printLine("                  " + VariablesInventario.vPlacaTransportista_Transf.trim(),true);
	//vPrint.printLine("                  " + "",true);
 // vPrint.printLine("                  " + VariablesInventario.vDirecDestino_Transf,true);  
	vPrint.printLine(" ",true);
	vPrint.printLine(" ",true);
	vPrint.deactivateCondensed();
	vPrint.setEndHeader();
  
  System.out.println("xxxxx: "+pDetallePedido);
	int linea = 0;
    for (int i = 0; i < pDetallePedido.size(); i++)
    {
            //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetallePedido.get(i)).get(2)).trim(),15) + " " +
      vPrint.printCondensed("   " + 
                            FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(0)).trim(), 
                                                           6) + "  " + 
                            FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(1)).trim(), 
                                                             78 + 10/*15*/) + 
                            " " + 
                            /*FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                             23) + " " + */
                            /*FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(), 
                                                           5), true);*/
                            FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                                                       23) + " " +   
                            FarmaPRNUtility.alinearDerecha( FarmaPRNUtility.tabular( ((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(),5,"/"), 
                                                                                     12), true); 
	linea += 1;
	}
  
    vPrint.activateCondensed();
  
    for (int j = linea; j <= FarmaConstants.ITEMS_POR_GUIA - 1; j++)
      vPrint.printLine(" ", true);
	
    vPrint.printLine(" ", true);
    vPrint.printLine(" ", true);
    vPrint.printLine(" ", true);
    vPrint.printLine(" ", true);
    vPrint.printLine(" ", true);
    vPrint.printLine(" ", true);
    vPrint.printLine(" ", true);
    if (VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL) || 
        VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
    {
      vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                       true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
    }
    else
	{
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                       true);
	}
	
	vPrint.deactivateCondensed();
	vPrint.endPrintService();
}
//JQUISPE 11.05.2010 impresion guias pequeñas
  private static void imprimeGuia2(JDialog pJDialog, String pFechaBaseDatos, 
                                  ArrayList pDetallePedido,int pTipoImpresion)
    throws Exception
  {
        
      
    System.out.println("VariablesInventario.vTipoFormatoImpresion"+VariablesInventario.vTipoFormatoImpresion);  
      
    FarmaPrintService vPrint = 
      new FarmaPrintService(36, VariablesCaja.vRutaImpresora, false);
    if (!vPrint.startPrintService())
      throw new Exception("Error en Impresora. Verifique !!!");
        vPrint.activateCondensed();
        String dia = pFechaBaseDatos.substring(0,2);
    String mesLetra = 
      FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 
                                                                                  5)));
        String ano = pFechaBaseDatos.substring(6,10);
        String hora = pFechaBaseDatos.substring(11,19);

        vPrint.setStartHeader();
        vPrint.activateCondensed();
      
      //FarmaConstants.ITEMS_POR_GUIA = 8;
      System.err.println(" FarmaConstants.ITEMS_POR_GUIA :"+ FarmaConstants.ITEMS_POR_GUIA );
      ///INICIO CABECERA
      //vPrint.printLine("",true);
              
              if(VariablesPtoVenta.vIndDirMatriz){
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(45) +VariablesPtoVenta.vDireccionMatriz ,true);       
              }
          vPrint.printLine("", true); //vNumeroPedido +
          vPrint.printLine("     "+"LOCAL: " + FarmaVariables.vDescCortaLocal + 
                           "          CORR." + VariablesInventario.vNumNotaEs + 
                           "          No. " + 
                           VariablesCaja.vNumCompImprimir.substring(0, 3) + "-" + 
                           VariablesCaja.vNumCompImprimir.substring(3), true);
      /*    vPrint.printLine("     "+"MOTIVO: " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim(), 
                                                            64), true);*/
          //JMIRANDA 10.12.09
          if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
          vPrint.printLine("     "+"MOTIVO: " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                           +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                            64),true);
              vPrint.printLine("",true);
          }
          else {
              vPrint.printLine("     "+"MOTIVO: " + 
                               FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                               ,64),true);
                  vPrint.printLine("",true);    
          }
          vPrint.printLine("                  " + dia + " " + mesLetra + " " + 
                           ano + "  " + "       " + "                  " + dia + 
                           " " + mesLetra + " " + ano + "  " + "       ", true);
          vPrint.printLine("                  " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDestino_Transf.trim(), 
                                                            64), true);
          vPrint.printLine("                  " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vRucDestino_Transf.trim(), 
                                                            64) + 
                           "              " + FarmaVariables.vCodLocal.trim() + 
                           " - " + FarmaVariables.vDescCortaLocal.trim(), true);
          vPrint.printLine("                  " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDirecDestino_Transf.trim(), 
                                                            64) + 
                           "              " + 
                           getDireccion(VariablesInventario.vDirecOrigen_Transf), 
                           true);
          vPrint.printLine("                  " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 
                                                            64) + 
                           "              " + 
                           VariablesInventario.vCodDestino_Transf.trim() + 
                           " - " + VariablesInventario.vDestino_Transf, true);
          vPrint.printLine("                  " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vRucTransportista_Transf.trim(), 
                                                            64) + 
                           "              " + 
                           getDireccion(VariablesInventario.vDirecDestino_Transf), 
                           true);
              //vPrint.printLine("                  " + VariablesInventario.vDirecTransportista_Transf.trim(),true);
              //vPrint.printLine("                  " + VariablesInventario.vPlacaTransportista_Transf.trim(),true);
              //vPrint.printLine("                  " + "",true);
       // vPrint.printLine("                  " + VariablesInventario.vDirecDestino_Transf,true);  
              vPrint.printLine(" ",true);
              vPrint.printLine(" ",true);
              vPrint.deactivateCondensed();
              vPrint.setEndHeader();
              
      ///FIN
        /*
        for (int j = 1; j <= 20; j++) {
            System.err.println("j:"+j);
            vPrint.printLine("linea prueba " + j, true);
        }
        */

        System.out.println("xxxxx: "+pDetallePedido);
                int linea = 0;
            for (int i = 0; i < pDetallePedido.size(); i++)
            {
                    //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetallePedido.get(i)).get(2)).trim(),15) + " " +
              vPrint.printCondensed("   " + 
                                    FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(0)).trim(), 
                                                                   6) + "  " + 
                                    FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(1)).trim(), 
                                                                     78 + 10/*15*/) + 
                                    " " + 
                                    /*FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                                     23) + " " + */
                                    /*FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(), 
                                                                   5), true);*/
                                    FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                                                               23) + " " +   
                                    FarmaPRNUtility.alinearDerecha( FarmaPRNUtility.tabular( ((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(),5,"/"), 
                                                                                             12), true); 
                linea += 1;
                }
          
            vPrint.activateCondensed();
          
            //for (int j = linea; j <= FarmaConstants.ITEMS_POR_GUIA - 1; j++)
            for (int j = linea; j <= pTipoImpresion - 1; j++)
              vPrint.printLine(" ", true);
                 
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);            
            
            
            
            if (VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL) || 
                VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
            {
              vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                               true);
              /*vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);*/
            }
            else
                {
              /*vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);*/
              vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                               true);
                }        
      
        
        vPrint.deactivateCondensed();
        vPrint.endPrintService();
  }   



  private static String obtieneNumCompPago_ForUpdate(JDialog pJDialog, String pSecImprLocal, Object pObjectFocus)
  {
    String numCompPago = "";
    ArrayList myArray = new ArrayList();
    try
    {
      DBCaja.obtieneNumComp_ForUpdate(myArray, pSecImprLocal);
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
      sql.printStackTrace();
      return numCompPago;
    }
  }
  
  private static boolean obtieneDetalleImprComp(JDialog pJDialog, String pSecGuia, Object pObjectFocus)
  {
    VariablesCaja.vArrayList_DetalleImpr = new ArrayList();
    try
    {
      DBInventario.obtieneInfoDetalleImpresionGuia(VariablesCaja.vArrayList_DetalleImpr,VariablesInventario.vNumNotaEs,pSecGuia);
      if(VariablesCaja.vArrayList_DetalleImpr.size() == 0)
      {
        FarmaUtility.showMessage(pJDialog,"No se pudo determinar el detalle del Pedido. Verifique!!!.",pObjectFocus);
        return false;
      }
      System.out.println("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr.size());
      //System.out.println("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr);
      return true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al obtener Detalle de Impresion de Comprobante.",pObjectFocus);
      sql.printStackTrace();
      return false;
    }
  }
   
  private static void actualizaNumComp_Impresora(String pSecImprLocal) throws SQLException
  {
    DBCaja.actualizaNumComp_Impresora(pSecImprLocal);
  }
  
  
   private static void reConfiguraCaja(String pSecImprLocal,String pNumComprob) throws SQLException
  {
   DBInventario.reConfiguraCaja( pSecImprLocal, pNumComprob);
  }
  
  
  
  private static String obtieneRutaImpresora() throws SQLException
  {
	System.out.println(FarmaVariables.vImprReporte);
    return FarmaVariables.vImprReporte;//DBCaja.obtieneRutaImpresoraVenta(pSecImprLocal);
  }

  private static void actualizaGuiaImpreso(String pNumPedVta,String i,String pNumGuia) throws SQLException
  {
    DBInventario.actualizaGuiaImpreso(pNumPedVta,i,pNumGuia);
  }
  
  private static void actualizaNumGuiaKardex(String pNumPedVta,ArrayList pDetallePedido,String secGuia) throws SQLException
  {
    for (int i=0; i<pDetallePedido.size(); i++) 
    {  
      DBInventario.actualizaNumGuiaKardex(pNumPedVta,secGuia,((String)((ArrayList)pDetallePedido.get(i)).get(0)).trim());
    }
  }
  
  public static boolean guardarCantidadIngresada(JDialog dialogo,Object objeto)
  {
    boolean retorno;
    try
    {
      DBInventario.guardarCantPedRepTemp(VariablesInventario.vCodProd_PedRep,VariablesInventario.vCant_PedRep);
      FarmaUtility.aceptarTransaccion();
      retorno = true;
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      retorno = false;
      sql.printStackTrace();
      FarmaUtility.showMessage(dialogo,"Ocurrió un error al guardar la cantidad de reposición : \n" + sql.getMessage(),objeto);
    }
    return retorno;
  }
  
  private static String getDireccion(String direccion)
  {
    String direc = "";
    int longitud = direccion.length();
    if(longitud > 40)
    {
      longitud = 40;
    }
    direc = direccion.substring(0,longitud);
    
    return direc;
  }
  
  public static boolean guardarCantidadIngresadaMatriz(JDialog dialogo,Object objeto)
  {
    boolean retorno;
    try
    {
      DBInventario.guardarCantPedRepMatriz(VariablesInventario.vNroPed_PedRep,VariablesInventario.vCodProd_PedRep,VariablesInventario.vCant_PedRep);
      FarmaUtility.aceptarTransaccion();
      retorno = true;
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      retorno = false;
      sql.printStackTrace();
      FarmaUtility.showMessage(dialogo,"Ocurrió un error al guardar la cantidad de reposición : \n" + sql.getMessage(),objeto);
    }
    return retorno;
  }
  
  public static String verificaRucValido(String ruc)
  {
    String resultado = "";
    try
    {
      resultado = DBCliente.verificaRucValido(ruc);
      return resultado;
    } 
    catch(SQLException sql)
    {
      sql.printStackTrace();
      return ConstantsCliente.RESULTADO_RUC_INVALIDO;
    }
  }
  
  /**Nuevo!!
   * @Autor:  Luis Reque Orellana
   * @Fecha:  20/04/2007
   * */
  public static void guardarCantidadAdicional() throws SQLException
  {
    DBInventario.guardarCantAdicPedRepTemp(VariablesInventario.vCodProd_PedRep,VariablesInventario.vCantAdicional);
  }

  /**NUEVO
   * @Autor: Paulo Cesar Ameghino Rojas
   * @Fecha: 07-06-2007
   * */
  public static void muestraIndTipoProd(int pColumna, JLabel pLabel, JTable myJTable)
  {
    if(myJTable.getRowCount() > 0)
    {
      String indProdRef = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),pColumna))).trim();
      if(indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[0]))
        pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[0]);
      else if(indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[1]))
        pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[1]);
      else if(indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[2]))
        pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[2]);
      else if(indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[3]))
        pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[3]);
    }
  }
  
  /**NUEVO
   * @Autor: Paulo Cesar Ameghino Rojas
   * @Fecha: 20-04-2007
   * */  
   public static boolean obtieneIndLinea(JDialog dialog)
   {
    boolean  vRetorno ;
     try
     {
      String indLinea = DBInventario.obtieneIndLinea();
      
      if(indLinea.equalsIgnoreCase("TRUE"))
      {
        vRetorno = true ;
      } else vRetorno = false ;
      
     } catch (SQLException sql)
     {
       sql.printStackTrace();
       FarmaUtility.showMessage(dialog,"Error al obtener el valor del indicador" , null);
       vRetorno = false ;
     }
     return vRetorno ; 
   }
  
   /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  10.09.08
     * @throws SQLException
     */
   public static void guardarCantidadPedidoAdicionalLocal(String codProd, 
                                                           String cCantSol, 
                                                           String cCantAuto, 
                                                           String cIndAutori) throws SQLException
   {
     
     try{
         DBInventario.guardarCantidadPedidoAdicionalLocal(  codProd, 
                                                            cCantSol, 
                                                            cCantAuto, 
                                                            cIndAutori);
     }catch(SQLException sql){
         sql.printStackTrace();;
     }
   }
   
   /**
     * @author Daniel Fernando Veliz La Rosa
     * @since 10.09.08
     * @param codProd
     * @param cCantAuto
     * @param cIndAutori
     * @throws SQLException
     */
   
    public static void guardarCantidadPedidoAdicionalMatriz(String codProd, 
                                                            String cCantAuto, 
                                                            String cIndAutori) throws SQLException
    {
      
      try{
          DBInventario.guardarCantidadPedidoAdicionalMatriz(  codProd, 
                                                             cCantAuto, 
                                                             cIndAutori);
      }catch(SQLException sql){
          sql.printStackTrace();;
      }
    }
    
    
    public static boolean validaCerosIzquierda(String vCantidad){
        boolean retorno = false;
        if(vCantidad.length() > 0){
            String vCharUno = String.valueOf(vCantidad.charAt(0));
            if(vCantidad.length() > 1 && vCharUno.equals("0")){
                return retorno = true;
            }
        } 
        return retorno;
    }
    
   private static void imprimeGuiaRecepCiega(JDialog pJDialog, String pFechaBaseDatos, 
                                   ArrayList pDetallePedido)
    throws Exception
   {
      FarmaPrintService vPrint = 
      new FarmaPrintService(66, VariablesCaja.vRutaImpresora, false);
      if (!vPrint.startPrintService())
        throw new Exception("Error en Impresora. Verifique !!!");
          vPrint.activateCondensed();
          String dia = pFechaBaseDatos.substring(0,2);
      String mesLetra = 
        FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 
                                                                                    5)));
          String ano = pFechaBaseDatos.substring(6,10);
          String hora = pFechaBaseDatos.substring(11,19);

          vPrint.setStartHeader();
          vPrint.activateCondensed();
          //vPrint.printLine("",true);
          
          if(VariablesPtoVenta.vIndDirMatriz){
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(45) +VariablesPtoVenta.vDireccionMatriz ,true);       
          }
      vPrint.printLine("", true); //vNumeroPedido +
      vPrint.printLine("     "+"LOCAL: " + FarmaVariables.vDescCortaLocal + 
                       "          CORR." + VariablesInventario.vNumNotaEs + 
                       "          No. " + 
                       VariablesCaja.vNumCompImprimir.substring(0, 3) + "-" + 
                       VariablesCaja.vNumCompImprimir.substring(3), true);
    /*    vPrint.printLine("     "+"MOTIVO: " +
                       FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim(), 
                                                        64), true);*/
      //JMIRANDA 10.12.09
      if(!VariablesRecepCiega.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
      vPrint.printLine("     "+"MOTIVO: " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDescMotivo_Transf.trim()
                       +" - "+VariablesRecepCiega.vDescMotivo_Transf_Larga.trim(), 
                                                        64),true);
          vPrint.printLine("",true);
      }
      else {
          vPrint.printLine("     "+"MOTIVO: " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDescMotivo_Transf.trim()
                           ,64),true);
              vPrint.printLine("",true);    
      }
      vPrint.printLine("                  " + dia + " " + mesLetra + " " + 
                       ano + "  " + "       " + "                  " + dia + 
                       " " + mesLetra + " " + ano + "  " + "       ", true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDestino_Transf.trim(), 
                                                        64), true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vRucDestino_Transf.trim(), 
                                                        64) + 
                       "              " + FarmaVariables.vCodLocal.trim() + 
                       " - " + FarmaVariables.vDescCortaLocal.trim(), true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDirecDestino_Transf.trim(), 
                                                        64) + 
                       "              " + 
                       getDireccion(VariablesRecepCiega.vDirecOrigen_Transf), 
                       true);  
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vTransportista_Transf.trim(), 
                                                        64) + 
                       "              " + 
                       VariablesRecepCiega.vCodDestino_Transf.trim() + 
                       " - " + VariablesRecepCiega.vDestino_Transf, true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vRucTransportista_Transf.trim(), 
                                                        64) + 
                       "              " + 
                       getDireccion(VariablesRecepCiega.vDirecDestino_Transf), 
                       true);
          //vPrint.printLine("                  " + VariablesInventario.vDirecTransportista_Transf.trim(),true);
          //vPrint.printLine("                  " + VariablesInventario.vPlacaTransportista_Transf.trim(),true);
          //vPrint.printLine("                  " + "",true);
    // vPrint.printLine("                  " + VariablesInventario.vDirecDestino_Transf,true);
          vPrint.printLine(" ",true);
          vPrint.printLine(" ",true);
          vPrint.deactivateCondensed();
          vPrint.setEndHeader();
    
    System.out.println("xxxxx: "+pDetallePedido);
          int linea = 0;
      for (int i = 0; i < pDetallePedido.size(); i++)
      {
              //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetallePedido.get(i)).get(2)).trim(),15) + " " +
        vPrint.printCondensed("   " + 
                              FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(0)).trim(), 
                                                             6) + "  " + 
                              FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(1)).trim(), 
                                                               78 + 10/*15*/) + 
                              " " + 
                              /*FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                               23) + " " + */
                              /*FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(), 
                                                             5), true);*/
                              FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                                                         23) + " " +   
                              FarmaPRNUtility.alinearDerecha( FarmaPRNUtility.tabular( ((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(),5,"/"), 
                                                                                       12), true); 
          linea += 1;
          }
    
      vPrint.activateCondensed();
    
      for (int j = linea; j <= FarmaConstants.ITEMS_POR_GUIA - 1; j++)
        vPrint.printLine(" ", true);
          
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      if (VariablesRecepCiega.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL) || 
          VariablesRecepCiega.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
      {
        vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                         true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
      }
      else
          {
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                         true);
          }
          
          vPrint.deactivateCondensed();
          vPrint.endPrintService();
    }
//JQUISPE 11.05.2010 guias pequeñas de recepcion ciega
    private static void imprimeGuiaRecepCiega2(JDialog pJDialog, String pFechaBaseDatos, 
                                    ArrayList pDetallePedido, int pTipoImpresion)
      throws Exception
    {
      FarmaPrintService vPrint = 
        new FarmaPrintService(36, VariablesCaja.vRutaImpresora, false);
      if (!vPrint.startPrintService())
        throw new Exception("Error en Impresora. Verifique !!!");
          vPrint.activateCondensed();
          String dia = pFechaBaseDatos.substring(0,2);
      String mesLetra = 
        FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 
                                                                                    5)));
          String ano = pFechaBaseDatos.substring(6,10);
          String hora = pFechaBaseDatos.substring(11,19);

          vPrint.setStartHeader();
          vPrint.activateCondensed();
          //vPrint.printLine("",true);
          
          if(VariablesPtoVenta.vIndDirMatriz){
          vPrint.printLine(FarmaPRNUtility.llenarBlancos(45) +VariablesPtoVenta.vDireccionMatriz ,true);       
          }
      vPrint.printLine("", true); //vNumeroPedido +
      vPrint.printLine("     "+"LOCAL: " + FarmaVariables.vDescCortaLocal + 
                       "          CORR." + VariablesInventario.vNumNotaEs + 
                       "          No. " + 
                       VariablesCaja.vNumCompImprimir.substring(0, 3) + "-" + 
                       VariablesCaja.vNumCompImprimir.substring(3), true);
    /*    vPrint.printLine("     "+"MOTIVO: " +
                       FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim(), 
                                                        64), true);*/
      //JMIRANDA 10.12.09
      if(!VariablesRecepCiega.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
      vPrint.printLine("     "+"MOTIVO: " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDescMotivo_Transf.trim()
                       +" - "+VariablesRecepCiega.vDescMotivo_Transf_Larga.trim(), 
                                                        64),true);
          vPrint.printLine("",true);
      }
      else {
          vPrint.printLine("     "+"MOTIVO: " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDescMotivo_Transf.trim()
                           ,64),true);
              vPrint.printLine("",true);    
      }
      vPrint.printLine("                  " + dia + " " + mesLetra + " " + 
                       ano + "  " + "       " + "                  " + dia + 
                       " " + mesLetra + " " + ano + "  " + "       ", true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDestino_Transf.trim(), 
                                                        64), true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vRucDestino_Transf.trim(), 
                                                        64) + 
                       "              " + FarmaVariables.vCodLocal.trim() + 
                       " - " + FarmaVariables.vDescCortaLocal.trim(), true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDirecDestino_Transf.trim(), 
                                                        64) + 
                       "              " + 
                       getDireccion(VariablesRecepCiega.vDirecOrigen_Transf), 
                       true);  
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vTransportista_Transf.trim(), 
                                                        64) + 
                       "              " + 
                       VariablesRecepCiega.vCodDestino_Transf.trim() + 
                       " - " + VariablesRecepCiega.vDestino_Transf, true);
      vPrint.printLine("                  " + 
                       FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vRucTransportista_Transf.trim(), 
                                                        64) + 
                       "              " + 
                       getDireccion(VariablesRecepCiega.vDirecDestino_Transf), 
                       true);
          //vPrint.printLine("                  " + VariablesInventario.vDirecTransportista_Transf.trim(),true);
          //vPrint.printLine("                  " + VariablesInventario.vPlacaTransportista_Transf.trim(),true);
          //vPrint.printLine("                  " + "",true);
    // vPrint.printLine("                  " + VariablesInventario.vDirecDestino_Transf,true);
          vPrint.printLine(" ",true);
          vPrint.printLine(" ",true);
          vPrint.deactivateCondensed();
          vPrint.setEndHeader();
    
    System.out.println("xxxxx: "+pDetallePedido);
          int linea = 0;
      for (int i = 0; i < pDetallePedido.size(); i++)
      {
              //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetallePedido.get(i)).get(2)).trim(),15) + " " +
        vPrint.printCondensed("   " + 
                              FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(0)).trim(), 
                                                             6) + "  " + 
                              FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(1)).trim(), 
                                                               78 + 10/*15*/) + 
                              " " + 
                              /*FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                               23) + " " + */
                              /*FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(), 
                                                             5), true);*/
                              FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(2)).trim(), 
                                                                                         23) + " " +   
                              FarmaPRNUtility.alinearDerecha( FarmaPRNUtility.tabular( ((String) ((ArrayList) pDetallePedido.get(i)).get(3)).trim(),5,"/"), 
                                                                                       12), true); 
          linea += 1;
          }
    
      vPrint.activateCondensed();
      
      //for (int j = linea; j <= FarmaConstants.ITEMS_POR_GUIA - 1; j++)
      for (int j = linea; j <=   pTipoImpresion - 1; j++)
        vPrint.printLine(" ", true);
          
      /*vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);*/
      
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);
      vPrint.printLine(" ", true);      

      if (VariablesRecepCiega.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL) || 
          VariablesRecepCiega.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
      {
        vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                         true);
        /*vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);*/
      }
      else
          {
        /*vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);
        vPrint.printLine(" ", true);*/
        vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), 
                         true);
          }
          
          vPrint.deactivateCondensed();
          vPrint.endPrintService();
    }


    public static boolean indNuevaTransf(){    
        boolean flag = false;
        String rpta = "";
        try
        {
          rpta = DBInventario.getIndNuevaTransf();
            if(rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else flag = false;
        }
        catch(SQLException e)
        {
            //e.printStackTrace(); 
            flag = false;
        }
        return flag;
    }  
    
    public static boolean indPideLoteTransf(JDialog pDialog, Object pObj){    
        boolean flag = false;
        String rpta = "";
        try
        {
          rpta = DBInventario.getIndPideLoteTransf();
            if(rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else if(rpta.trim().equalsIgnoreCase("E")){
                flag = false;
                FarmaUtility.showMessage(pDialog,"No existe dato para validar si es obligatorio ingresar el Lote.\n" +
                                                 "Comuníquese con el operador.",pObj);                
            }                
        }
        catch(SQLException e)
        {   
            flag = false;
            FarmaUtility.showMessage(pDialog,"No se pudo validar si es obligatorio ingresar el Lote.\n" +
                "Vuelva a Intentar.",pObj);            
        }
        return flag;
    }  
    
    public static boolean IndPideFechaVencTransf(JDialog pDialog, Object pObj){    
        boolean flag = false;
        String rpta = "";
        try
        {
          rpta = DBInventario.getIndPideFechaVencTransf();
            if(rpta.trim().equalsIgnoreCase("S"))
                flag = true;
            else if(rpta.trim().equalsIgnoreCase("E")){
                flag = false;
                FarmaUtility.showMessage(pDialog,"No existe dato para validar si es obligatorio ingresar la Fecha Vencimiento.\n" +
                                                 "Comuníquese con el operador.",pObj);                
            }                
        }
        catch(SQLException e)
        {   
            flag = false;
            FarmaUtility.showMessage(pDialog,"No se pudo validar si es obligatorio ingresar el Lote.\n" +
                "Vuelva a Intentar.",pObj);            
        }
        return flag;
    } 
}


