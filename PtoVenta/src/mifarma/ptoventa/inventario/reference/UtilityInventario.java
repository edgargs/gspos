package mifarma.ptoventa.inventario.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    private static final Logger log = LoggerFactory.getLogger(UtilityInventario.class);

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
                                            int pTipoImpresion,
                                               boolean pGuiaRemision) throws Exception 
    {
        String fechaBD = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
        VariablesInventario.vDirecOrigen_Transf = DBInventario.getDireccionOrigenLocal();
        VariablesRecepCiega.vDirecOrigen_Transf = VariablesInventario.vDirecOrigen_Transf;
        //JMIRANDA 16.02.2010
        //MODIFICADO JQUISPE 11.05.2010
        //PEQUEÑA
        if(VariablesInventario.vTipoFormatoImpresion <= 8)
        {
            //validar aqui si es Guia generada en Transf x Recepcion Ciega
            if(!VariablesInventario.vIndTransfRecepCiega)
            {   if("001".equalsIgnoreCase(FarmaVariables.vCodCia))
                    //CHUANES-09.04.2014
                    //SE ADICIONA PARAMETRO pGuiaRemision
                    imprimeGuia2(pJDialog, fechaBD, pDetalleComprobante,pTipoImpresion,pGuiaRemision);
                else if("002".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaFASA(pJDialog, fechaBD, pDetalleComprobante,pGuiaRemision);
                else if("003".equalsIgnoreCase(FarmaVariables.vCodCia))
                imprimeGuiaBTL(pJDialog, fechaBD, pDetalleComprobante,pGuiaRemision);
            }
            else
            {   if("001".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaRecepCiega2(pJDialog, fechaBD, pDetalleComprobante,pTipoImpresion);
                else if("002".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaFASA(pJDialog, fechaBD, pDetalleComprobante,pGuiaRemision);
                   
                else if("003".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaBTL(pJDialog, fechaBD, pDetalleComprobante,pGuiaRemision);
                    
                VariablesInventario.vIndTransfRecepCiega = false;
            }
        }
        else
        {
            // PARA GRANDE
            //JMIRANDA 16.02.2010
            //validar aqui si es Guia generada en Transf x Recepcion Ciega
            if(!VariablesInventario.vIndTransfRecepCiega)
            {   if("001".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuia(pJDialog, fechaBD, pDetalleComprobante);
                else if("002".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaFASA(pJDialog, fechaBD, pDetalleComprobante,pGuiaRemision);
                  
                else if("003".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaBTL(pJDialog, fechaBD, pDetalleComprobante,pGuiaRemision);
            }
            else
            {   if("001".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaRecepCiega(pJDialog, fechaBD, pDetalleComprobante);
                else if("002".equalsIgnoreCase(FarmaVariables.vCodCia))
                   
                    imprimeGuiaRecepCiegaFasa(pJDialog, fechaBD, pDetalleComprobante);
                else if("003".equalsIgnoreCase(FarmaVariables.vCodCia))
                    imprimeGuiaBTL(pJDialog, fechaBD, pDetalleComprobante,pGuiaRemision);
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
      
      //log.debug("VariablesCaja.vNumCompImprimir="+VariablesCaja.vNumCompImprimir);
      //log.debug("VariablesCaja.vSecImprLocalGuia="+VariablesCaja.vSecImprLocalGuia);
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
        //log.debug("secCompPago : " + String.valueOf(i));
        //ruta de roporte
        VariablesCaja.vRutaImpresora = obtieneRutaImpresora();
        
        actualizaNumComp_Impresora(VariablesCaja.vSecImprLocalGuia);
        
        //log.debug(VariablesInventario.vNumNotaEs);
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
        log.debug("VariablesInventario.vCant 2 : " + VariablesInventario.vCant);
        log.debug("i : " + (i + 1));
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
      log.error("",sql);
      FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir las Guias de la Transferencia.\n" + sql,pObjectFocus);
      }
    } catch(Exception e){
      FarmaUtility.liberarTransaccion();
      log.error("",e);
      FarmaUtility.showMessage(pJDialog, "Error en la Aplicacion al Imprimir las Guias de la Transferencia.\n" + e,pObjectFocus);
    }
  }

    public static void procesoImpresionGuias(JDialog pJDialog, Object pObjectFocus, int pTipoFormatoImpresion) {
        procesoImpresionGuias(pJDialog, pObjectFocus, pTipoFormatoImpresion, false);
    }

    /**
     * Impresion de guias
     * 
     * @param pJDialog
     * @param pObjectFocus
     * @param pTipoFormatoImpresion
     * @param pGuiaRemision Imprime guias de remision (salida de valija, documentos, etc)
     */
    public static void procesoImpresionGuias(JDialog pJDialog, Object pObjectFocus ,int pTipoFormatoImpresion, boolean pGuiaRemision)
    {
      try
      {
        ArrayList secGuia = DBInventario.getSecuencialGuia(VariablesInventario.vNumNotaEs,VariablesCaja.vSecImprLocalGuia);
        for(int i=0; i<secGuia.size(); i++)
        {
        
        //log.debug("VariablesCaja.vNumCompImprimir="+VariablesCaja.vNumCompImprimir);
        //log.debug("VariablesCaja.vSecImprLocalGuia="+VariablesCaja.vSecImprLocalGuia);
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
            
            if(!pGuiaRemision){
                if(!obtieneDetalleImprComp(pJDialog, ((ArrayList)secGuia.get(i)).get(0).toString().trim(), pObjectFocus)) return;
            }else{
                //Se obtiene el texto a imprimir
                if(!obtieneDetalleImprGuia(pJDialog, ((ArrayList)secGuia.get(i)).get(0).toString().trim(), pObjectFocus)) return;
            }
          //log.debug("secCompPago : " + String.valueOf(i));
          //ruta de roporte
          VariablesCaja.vRutaImpresora = obtieneRutaImpresora();
          
          actualizaNumComp_Impresora(VariablesCaja.vSecImprLocalGuia);
          
          //log.debug(VariablesInventario.vNumNotaEs);
          actualizaGuiaImpreso(VariablesInventario.vNumNotaEs,((ArrayList)secGuia.get(i)).get(0).toString().trim(),VariablesCaja.vNumCompImprimir);  
          
          if(!pGuiaRemision){
            actualizaNumGuiaKardex(VariablesInventario.vNumNotaEs,VariablesCaja.vArrayList_DetalleImpr,((ArrayList)secGuia.get(i)).get(0).toString().trim());
          }
          
          //MODIFCADO JQUISPE 11/05/2010  
          imprimeComprobanteGuias(pJDialog,
                                 VariablesCaja.vArrayList_DetalleImpr,
                                 VariablesCaja.vArrayList_TotalesComp,
                                 VariablesVentas.vTip_Comp_Ped,
                                 VariablesCaja.vNumCompImprimir,
                                 pTipoFormatoImpresion,
                                  pGuiaRemision);  
          
          FarmaUtility.aceptarTransaccion();
          
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
        log.error("",sql);
        FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir las Guias de la Transferencia.\n" + sql,pObjectFocus);
        }
      } catch(Exception e){
        FarmaUtility.liberarTransaccion();
        log.error("",e);
        FarmaUtility.showMessage(pJDialog, "Error en la Aplicacion al Imprimir las Guias de la Transferencia.\n" + e,pObjectFocus);
      }
    }
  

    /*  Modificado LLEIVA 15-Nov-2013
     * 
     */
    public static void imprimeGuia(JDialog pJDialog, String pFechaBaseDatos, ArrayList pDetallePedido)
    throws Exception
    {
        FarmaPrintService vPrint = new FarmaPrintService(66, VariablesCaja.vRutaImpresora, false);
        //si no se puede iniciar el servicio de impresión
        if (!vPrint.startPrintService())
            throw new Exception("Error en Impresora. Verifique !!!");
	
        //vPrint.setStartHeader();
        vPrint.activateCondensed();
        
        String dia = pFechaBaseDatos.substring(0,2);
        String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 5)));
        String anio = pFechaBaseDatos.substring(6,10);
        

	if(VariablesPtoVenta.vIndDirMatriz)
        {   vPrint.printLine(FarmaPRNUtility.llenarBlancos(45) +VariablesPtoVenta.vDireccionMatriz ,true);	     
        }
        else
        {   vPrint.printLine("", true);
        }
        vPrint.printLine("", true); //vNumeroPedido +
    
        //ERIOS 12.09.2013 Imprime direccion local
        if(VariablesPtoVenta.vIndDirLocal)
        {   vPrint.printLine("     "+"NUEVA DIRECCION: "+FarmaVariables.vDescCortaDirLocal,true);
        }
        else
        {   vPrint.printLine("",true);
        }
    
        vPrint.printLine("     "+"LOCAL: " + FarmaVariables.vDescCortaLocal + 
                         "          CORR." + VariablesInventario.vNumNotaEs + 
                         "          No. " + 
                         VariablesCaja.vNumCompImprimir.substring(0, 3) + "-" + 
                         VariablesCaja.vNumCompImprimir.substring(3), true);
        /*    vPrint.printLine("     "+"MOTIVO: " + 
                         FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim(), 
                                                          64), true);*/
        //JMIRANDA 10.12.09
        if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase(""))
        {   vPrint.printLine("     "+"MOTIVO: " + 
                            FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                            +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                          64),true);	
        }
        else
        {   vPrint.printLine("     "+"MOTIVO: " + 
                             FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                             ,64),true);
        }
        vPrint.printLine("                  " + dia + " " + mesLetra + " " + 
                         anio + "  " + "       " + "                  " + dia + 
                         " " + mesLetra + " " + anio + "  " + "       ", true);
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
  
        log.debug("xxxxx: "+pDetallePedido);
	int linea = 0;
        for (int i = 0; i < pDetallePedido.size(); i++)
        {
            //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetallePedido.get(i)).get(2)).trim(),15) + " " +
            vPrint.printCondensed("   " + 
                            FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(0)).trim(), 6) + "  " + 
                            FarmaPRNUtility.alinearIzquierda(((String) ((ArrayList) pDetallePedido.get(i)).get(1)).trim(), 78 + 10/*15*/) + 
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
            vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), true);
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
            vPrint.printLine("" + FarmaPRNUtility.alinearDerecha("X", 119), true);
        }
        
        vPrint.deactivateCondensed();
        vPrint.endPrintService();
    }

    /*  Creado LLEIVA 15-Nov-2013
     *  Impresion de Guia de transferencia para FASA
     */
    public static void imprimeGuiaFASA(JDialog pJDialog, String pFechaBaseDatos, ArrayList pDetallePedido, boolean pGuiaRemision)
    throws Exception
    {
        FarmaPrintService vPrint = new FarmaPrintService(66, VariablesCaja.vRutaImpresora, false);
        //si no se puede iniciar el servicio de impresión
       if (!vPrint.startPrintService())
            throw new Exception("Error en Impresora. Verifique !!!");
        
        //vPrint.setStartHeader();
        vPrint.activateCondensed();
        
        String dia = pFechaBaseDatos.substring(0,2);
        String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 5)));
        String anio = pFechaBaseDatos.substring(6,10);
        
        //se dejan 3 lineas para la cabecera
       for(int i=0;i<3;i++)
        {   vPrint.printLine("", true); //vNumeroPedido +
        }
        
       
          if (VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL) || 
              VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
          {
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(83)+'X', true); 
              vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);
              //vPrint.printLine(" ", true);
                            
                //Linea 7 - se imprime el motivo de la transferencia si existe
                if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
                vPrint.printLine("     "+"MOTIVO: " + 
                                 FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                 +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                                  64),true);
                }
                else if (!VariablesInventario.vDescMotivo_Transf.trim().equalsIgnoreCase("")){
                    vPrint.printLine("     "+"MOTIVO: " + 
                                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                     ,64),true);  
                } else {
                    vPrint.printLine(" ", true);
                }
                
              //Linea 8 - se imprime la fecha de emision
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                                    FarmaPRNUtility.alinearIzquierda(dia+"/"+mesLetra+"/"+anio, 25), 
                                       true);
              //Linea 9 - se imprime el punto de partida
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                              FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal.trim() + " - " + FarmaVariables.vDescCortaLocal.trim(), 40),
                               true);
              //Linea 10- se imprime el Transportista
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                              FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDirecOrigen_Transf.trim(), 86)+
                              FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 30),
                              true);
          }
          else
          {   vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);
              vPrint.printLine(" ", true);
              //vPrint.printLine(" ", true);
                            
                //Linea 7 - se imprime el motivo de la transferencia si existe
                if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
                vPrint.printLine("     "+"MOTIVO: " + 
                                 FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                 +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                                  64),true);
                }
                else if (!VariablesInventario.vDescMotivo_Transf.trim().equalsIgnoreCase("")){
                    vPrint.printLine("     "+"MOTIVO: " + 
                                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                     ,64),true);  
                } else {
                    vPrint.printLine(" ", true);
                }
              //Linea 8 - se imprime la fecha de emision
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                                    FarmaPRNUtility.alinearIzquierda(dia+"/"+mesLetra+"/"+anio, 25), 
                                       true);
              //Linea 9 - se imprime el punto de partida
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                              FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal.trim() + " - " + FarmaVariables.vDescCortaLocal.trim(), 40),
                               true);
              //Linea 10- se imprime el Transportista
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                               FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDirecOrigen_Transf.trim(), 71)+
                               'X'+
                               FarmaPRNUtility.llenarBlancos(14)+
                               FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 30),
                              true);
          }
               
        //Linea 11- se imprime el RUC de transportista
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(96)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vRucTransportista_Transf.trim(), 14), 
                        true);
        
        //Linea 12- se imprime el punto de llegada y la placa
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDirecDestino_Transf.trim(), 82)+ 
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vPlacaTransportista_Transf.trim(), 12), 
                        true);
        
        //Linea 13- se imprime el destinatario
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vCodDestino_Transf.trim()+" - "+VariablesInventario.vDestino_Transf, 70),
                         true);
        
        //Linea 14- se imprime el DNI
        vPrint.printLine(" ", true);
        
        //Linea 15- se imprime el comprobante de pago
        vPrint.printLine(" ", true);
        
        //se dejan 3 lineas para la cabecera de los detalles
        for(int i=1;i<=3;i++)
        {   vPrint.printLine("", true); //vNumeroPedido +
        }
        
        //Linea 19- se empieza a imprimir el detalle
        for (int i = 0; i < pDetallePedido.size(); i++)
        {
            ArrayList temp = (ArrayList)pDetallePedido.get(i);
            
            if(!pGuiaRemision){
            
                String cod  = ((String)temp.get(0)).trim();
                String desc = ((String)temp.get(1)).trim();
                String pres = ((String)temp.get(2)).trim();
                String cant = ((String)temp.get(3)).trim();           
                
                vPrint.printLine(FarmaPRNUtility.alinearDerecha(cod, 8) +
                                FarmaPRNUtility.llenarBlancos(5)+
                                FarmaPRNUtility.alinearIzquierda(desc, 78) +
                                FarmaPRNUtility.llenarBlancos(3)+
                                FarmaPRNUtility.alinearIzquierda(pres, 8) +
                                FarmaPRNUtility.llenarBlancos(3)+
                                FarmaPRNUtility.alinearIzquierda(cant, 10)
                                , true);
            }else{
                String desc  = ((String)temp.get(0)).trim();
                vPrint.printLine(FarmaPRNUtility.llenarBlancos(8+5)+
                                FarmaPRNUtility.alinearIzquierda(desc, 100)
                                , true);
            }
        }

        int numLineas = 19 + pDetallePedido.size();
        if(numLineas < 40)
        {   for(int i=1;i<=(40-numLineas);i++)
            {   vPrint.printLine("", true);
            }
        }
       
        vPrint.deactivateCondensed();
        vPrint.endPrintServiceSinCompletar();
    }

//JQUISPE 11.05.2010 impresion guias pequeñas
  private static void imprimeGuia2(JDialog pJDialog, String pFechaBaseDatos, 
                                  ArrayList pDetallePedido,int pTipoImpresion, boolean pGuiaRemision)
    throws Exception
  {
        
      
    log.debug("VariablesInventario.vTipoFormatoImpresion"+VariablesInventario.vTipoFormatoImpresion);  
      
      FarmaPrintService vPrint = new FarmaPrintService(36, VariablesCaja.vRutaImpresora, false);
      if (!vPrint.startPrintService())
        throw new Exception("Error en Impresora. Verifique !!!");
        vPrint.activateCondensed();
        
        String dia = pFechaBaseDatos.substring(0,2);
        String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3,                                                                           5)));
        String ano = pFechaBaseDatos.substring(6,10);
        String hora = pFechaBaseDatos.substring(11,19);

        vPrint.setStartHeader();
        vPrint.activateCondensed();
      
        log.info(" FarmaConstants.ITEMS_POR_GUIA :"+ FarmaConstants.ITEMS_POR_GUIA );
    
        if(VariablesPtoVenta.vIndDirMatriz){
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(45) +VariablesPtoVenta.vDireccionMatriz ,true);       
        }{ vPrint.printLine("", true);
              }
         vPrint.printLine("", true); //vNumeroPedido +
          
          //ERIOS 12.09.2013 Imprime direccion local
        if(VariablesPtoVenta.vIndDirLocal){     
              vPrint.printLine("     "+"NUEVA DIRECCION: "+FarmaVariables.vDescCortaDirLocal,true);
        }else{
              vPrint.printLine("",true);
          }
          
          vPrint.printLine("     "+"LOCAL: " + FarmaVariables.vDescCortaLocal + 
                           "          CORR." + VariablesInventario.vNumNotaEs + 
                           "          No. " + 
                           VariablesCaja.vNumCompImprimir.substring(0, 3) + "-" + 
                           VariablesCaja.vNumCompImprimir.substring(3), true);
          if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
          vPrint.printLine("     "+"MOTIVO: " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                           +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                            64),true);
          }
          else {
              vPrint.printLine("     "+"MOTIVO: " + 
                               FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                               ,64),true);  
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
           
              vPrint.printLine(" ",true);
              vPrint.printLine(" ",true);
              vPrint.deactivateCondensed();
              vPrint.setEndHeader();
              
            //CHUANES 09.04.2014-MODIFICADO
            // SE CONDICIONA LO QUE SE OBTIENE DEL ARRAY SI ES UNA LISTA DE PRODUCTOS FARMACOS O UNA DESCRIPCION DE GUIAS DE QUE NO
            //MUEVEN STOCK  
                int linea = 0;
            for (int i = 0; i < pDetallePedido.size(); i++)
            {
              ArrayList array =(ArrayList)pDetallePedido.get(i); 
                    if(!pGuiaRemision){
                    String cod  = ((String)array.get(0)).trim();
                    String desc = ((String)array.get(1)).trim();
                    String pres = ((String)array.get(2)).trim();
                    String cant = ((String)array.get(3)).trim();     
                    log.info("codigo =\t"+cod); 
                    log.info("descripción =\t"+desc); 
                    log.info("precio =\t"+pres); 
                    log.info("cantidad =\t"+cant);  
                    vPrint.printCondensed("   " + FarmaPRNUtility.alinearDerecha(cod,6) + "  " + FarmaPRNUtility.alinearIzquierda(desc,78 + 10) + 
                    " " +FarmaPRNUtility.alinearIzquierda(pres,23) + " " + FarmaPRNUtility.alinearDerecha( FarmaPRNUtility.tabular(cant,5,"/"),12), true); 
                    }else{
                        String desc=((String)array.get(0)).trim();
                        vPrint.printCondensed(FarmaPRNUtility.llenarBlancos(6+2)+FarmaPRNUtility.alinearIzquierda(desc,78+10), true);
                    }
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
      log.debug("numCompPago : " + numCompPago);
      return numCompPago;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      numCompPago = "";
      FarmaUtility.showMessage(pJDialog,"Error al validar Agrupacion de Comprobante.",pObjectFocus);
      log.error("",sql);
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
      log.debug("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr.size());
      //log.debug("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr);
      return true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al obtener Detalle de Impresion de Comprobante.",pObjectFocus);
      log.error("",sql);
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
	log.debug(FarmaVariables.vImprReporte);
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
      log.error("",sql);
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
      log.error("",sql);
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
      log.error("",sql);
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
       log.error("",sql);
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
         log.error("",sql);;
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
          log.error("",sql);;
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
           }else{
               vPrint.printLine("", true);
           }
       vPrint.printLine("", true); //vNumeroPedido +
       
       //ERIOS 12.09.2013 Imprime direccion local
       if(VariablesPtoVenta.vIndDirLocal){     
           vPrint.printLine("     "+"NUEVA DIRECCION: "+FarmaVariables.vDescCortaDirLocal,true);
       }else{
           vPrint.printLine("",true);
       }
       
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
       }
       else {
           vPrint.printLine("     "+"MOTIVO: " + 
                            FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDescMotivo_Transf.trim()
                            ,64),true);
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
     
     log.debug("xxxxx: "+pDetallePedido);
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
    
    private static void imprimeGuiaRecepCiegaFasa(JDialog pJDialog, 
                                                String pFechaBaseDatos, 
                                                ArrayList pDetallePedido)
    throws Exception
    {
        FarmaPrintService vPrint =  new FarmaPrintService(66, VariablesCaja.vRutaImpresora, false);
        if (!vPrint.startPrintService())
            throw new Exception("Error en Impresora. Verifique !!!");
        vPrint.activateCondensed();
        String dia = pFechaBaseDatos.substring(0,2);
        String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3,5)));
        //LLEIVA 27-Nov-2013 Se recorta la fecha para que cuadre en la guia
        if(mesLetra != null && mesLetra.length()>3)
            mesLetra = mesLetra.substring(0, 3);
        String ano = pFechaBaseDatos.substring(6,10);
        String hora = pFechaBaseDatos.substring(11,19);
        
        vPrint.setStartHeader();
        vPrint.activateCondensed();
        //vPrint.printLine("",true);

        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        
        //se imprime la fecha de emision y de inicio de traslado
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(7)+
                         dia + " " + mesLetra + " " + ano + 
                         "                " + 
                         dia + " " + mesLetra + " " + ano , true);
        //se imprime el punto de partida
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(7)+
                        FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal.trim()+" - "+FarmaVariables.vDescCortaLocal.trim(),85), true);
        
        //se imprime la direccion de partida y el nombre del transportista
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(7)+
                        FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDirecOrigen_Transf.trim(),85)+
                        FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vTransportista_Transf.trim(),40), true);

        //FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vRucDestino_Transf.trim(), 64) + 
        //se imprimer el RUC del transportista
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(92)+
                        FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vRucTransportista_Transf.trim(),40), true); 
        
        //se imprime la direccion de destino y la placa del transportista
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(7)+
                        FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDestino_Transf.trim(),85)+
                        FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vPlacaTransportista_Transf.trim(),40),
                        true);  
        
        //se imprime el nombre del local de destino
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(7)+
                        FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vCodDestino_Transf.trim() + 
                                                        " - " + 
                                                        VariablesRecepCiega.vDirecDestino_Transf,85), 
                        true);
        
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.printLine(" ",true);
        vPrint.deactivateCondensed();
        vPrint.setEndHeader();
    
        log.debug("xxxxx: "+pDetallePedido);
        int linea = 18;
        for (int i = 0; i < pDetallePedido.size(); i++)
        {
            //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetallePedido.get(i)).get(2)).trim(),15) + " " +
            vPrint.printCondensed(FarmaPRNUtility.alinearDerecha(((String) ((ArrayList) pDetallePedido.get(i)).get(0)).trim(), 
                                                                6) 
                                    + "     " + 
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
    
        int restante = 40 - linea;
        if(restante > 0)
        {   for (int j = linea; j <=  restante; j++)
                vPrint.printLine(" ", true);
        }
        
        vPrint.deactivateCondensed();
        vPrint.endPrintServiceSinCompletar();
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
          }else{
              vPrint.printLine("", true);
          }
      vPrint.printLine("", true); //vNumeroPedido +
      
      //ERIOS 12.09.2013 Imprime direccion local
      if(VariablesPtoVenta.vIndDirLocal){     
          vPrint.printLine("     "+"NUEVA DIRECCION: "+FarmaVariables.vDescCortaDirLocal,true);
      }else{
          vPrint.printLine("",true);
      }
      
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
      }
      else {
          vPrint.printLine("     "+"MOTIVO: " + 
                           FarmaPRNUtility.alinearIzquierda(VariablesRecepCiega.vDescMotivo_Transf.trim()
                           ,64),true);
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
    
    log.debug("xxxxx: "+pDetallePedido);
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
            //log.error("",e); 
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
    
    /**
     * Verifica si existen guias pendientes de impresion
     * @author ERIOS
     * @since 17.12.2013
     * @param pJDialog
     * @param vEstadoNota
     * @param btnRelacionProductos
     * @return
     */
    public static boolean validaGuias(JDialog pJDialog,String vEstadoNota,Object btnRelacionProductos, String vNumNota)
    {
      boolean retorno;
      if(!vEstadoNota.equals(FarmaConstants.INDICADOR_N))
      {
        try
        {
          ArrayList secGuia = DBInventario.getSecuencialGuia(vNumNota,"");
          if(secGuia.size() == 0)
          {
            retorno = false;
            FarmaUtility.showMessage(pJDialog,"No existe guías pendientes de impresión.",btnRelacionProductos);
          }else
          {
            retorno = true;
          }
        }catch(SQLException e)
        {
          retorno = false;
          log.error("",e);
          FarmaUtility.showMessage(pJDialog,"No se ha podido determinar las guías pendientes.\n"+e,btnRelacionProductos);
        }
      }else
      {
        retorno = false;
        FarmaUtility.showMessage(pJDialog,"Esta transferencia está anulada.",btnRelacionProductos);
      }
      return retorno;
    }    
    
    /**
     * Reimprime guias pendientes
     * @author ERIOS
     * @since 17.12.2013
     * @param pJDialog
     * @param tblListaProductos
     * @param btnRelacionProductos
     * @return
     */
    public static boolean reimprimir(JDialog pJDialog, JTable tblListaProductos, Object btnRelacionProductos, String vNumNota)
    {
      boolean retorno = false;
      if(cargaCabecera(pJDialog,btnRelacionProductos,vNumNota))
        try
        {
          
          //Imprimir Comprobantes
          VariablesInventario.vNumNotaEs = vNumNota;
          DBInventario.grabaInicioFinGuiasTransferencia(VariablesInventario.vNumNotaEs,"I");
          UtilityInventario.procesoImpresionGuias(pJDialog ,tblListaProductos , VariablesInventario.vTipoFormatoImpresion);  
          DBInventario.grabaInicioFinGuiasTransferencia(VariablesInventario.vNumNotaEs,"F");
          FarmaUtility.aceptarTransaccion();
          retorno = true;
        }/*catch(SQLException sql)
        {
          FarmaUtility.liberarTransaccion();
          log.error("",sql);
          FarmaUtility.showMessage(pJDialog,"Ha ocurrido un error al grabar la transferencia.\n"+sql.getMessage(),btnRelacionProductos);
          retorno = false;
        }*/catch(Exception exc)
        {
          FarmaUtility.liberarTransaccion();
          log.error("",exc);
          FarmaUtility.showMessage(pJDialog, "Error en la aplicacion al grabar la transferencia.\n"+exc.getMessage(),btnRelacionProductos);
          retorno = false;
        }
      return retorno;
    }    
    
    public static boolean cargaCabecera(JDialog pJDialog, Object btnRelacionProductos, String vNumNota)
    {
      boolean retorno;
      try
      {
        ArrayList cabecera = DBInventario.getCabeceraGuiaTrans(vNumNota);
        if(cabecera.size() > 0)
        {
          cabecera = (ArrayList)cabecera.get(0);
          
          VariablesInventario.vDestino_Transf = cabecera.get(0).toString();
          VariablesInventario.vRucDestino_Transf = cabecera.get(1).toString();
          VariablesInventario.vDirecDestino_Transf = cabecera.get(2).toString();
          VariablesInventario.vTransportista_Transf = cabecera.get(3).toString();
          VariablesInventario.vRucTransportista_Transf = cabecera.get(4).toString();
          VariablesInventario.vCodDestino_Transf = cabecera.get(5).toString();
          
          retorno = true;
        }else
        {
          retorno = false;
          FarmaUtility.showMessage(pJDialog,"No se cargó los datos de cabecera de la guía.\n",btnRelacionProductos);    
        }
      }catch(SQLException e)
      {
        log.error("",e);
        retorno = false;
        FarmaUtility.showMessage(pJDialog,"Error al obtener los datos de cabecera de la guía.\n"+e,btnRelacionProductos);
      }
      
      return retorno;
    }    
    
    private static boolean obtieneDetalleImprGuia(JDialog pJDialog, String pSecGuia, Object pObjectFocus)
    {
      VariablesCaja.vArrayList_DetalleImpr = new ArrayList();
      try
      {
        VariablesCaja.vArrayList_DetalleImpr = DBInventario.getDetalleGuiaRemision(VariablesInventario.vNumNotaEs);
        if(VariablesCaja.vArrayList_DetalleImpr.size() == 0)
        {
          FarmaUtility.showMessage(pJDialog,"No se pudo determinar el detalle del Pedido. Verifique!!!.",pObjectFocus);
          return false;
        }
        log.debug("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr.size());
        //log.debug("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr);
        return true;
      } catch(SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"Error al obtener Detalle de Impresion de Comprobante.",pObjectFocus);
        log.error("",sql);
        return false;
      }
    }
    public static void imprimeGuiaBTL(JDialog pJDialog, String pFechaBaseDatos, ArrayList pDetallePedido, boolean pGuiaRemision)
    throws Exception
    {
        FarmaPrintService vPrint = new FarmaPrintService(66, VariablesCaja.vRutaImpresora, false);
        //ERIOS 2.4.3 margen derecho
        int margen = 8;
        String vMargen = FarmaPRNUtility.llenarBlancos(margen);
        //si no se puede iniciar el servicio de impresión
       if (!vPrint.startPrintService())
            throw new Exception("Error en Impresora. Verifique !!!");
        
        //vPrint.setStartHeader();
        vPrint.activateCondensed();
        
        String dia = pFechaBaseDatos.substring(0,2);
        String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3, 5)));
        String anio = pFechaBaseDatos.substring(6,10);
        
        //se dejan 3 lineas para la cabecera
       for(int i=0;i<4;i++)
        {   vPrint.printLine(vMargen+"", true); //vNumeroPedido +
        }
        //FECHA DE EMISION
        vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+
                              FarmaPRNUtility.alinearIzquierda(dia+"/"+mesLetra+"/"+anio, 25), 
                                 true);
        //PUNTO DE PARTIDA EN NUESTRO CASO CODIGO DE LOCAL Y DESCRIPCION CORTA DE LOCAL
        //FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDirecOrigen_Transf.trim()
        vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(20)+
                        FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal.trim() + " - " + FarmaVariables.vDescCortaLocal.trim(), 40),
                         true);
        //DATOS DEL DESTINO(DATO AMBIGUO)
        vPrint.printLine(vMargen+"",true);
        
        // NOMBRE O RAZON SOCIAL (DESTINATARIO)
        vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(17)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vCodDestino_Transf.trim()+" - "+VariablesInventario.vDestino_Transf, 70),
                         true);

       
        //DIRECCION DEL PUNTO DE LLEGADA(DESTINATARIO)
        vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(20)+ FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDirecDestino_Transf.trim(), 82)
                        ,
                        true);
        //RUC DESTINO
        vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+ FarmaPRNUtility.alinearIzquierda(VariablesInventario.vRucDestino_Transf.trim(), 82)
                        ,
                        true);
    
           vPrint.printLine(vMargen+"", true); //vNumeroPedido +
        
      //  int linea=0;
        //Linea 19- se empieza a imprimir el detalle
        for (int i = 0; i < pDetallePedido.size(); i++)
        {
            ArrayList temp = (ArrayList)pDetallePedido.get(i);
            
            if(!pGuiaRemision){
            
                String cod  = ((String)temp.get(0)).trim();
                String desc = ((String)temp.get(1)).trim();
                String pres = ((String)temp.get(2)).trim();
                String cant = ((String)temp.get(3)).trim();           
                
                vPrint.printLine(vMargen+FarmaPRNUtility.alinearDerecha(cod, 8) +
                                FarmaPRNUtility.llenarBlancos(5)+
                                FarmaPRNUtility.alinearIzquierda(desc, 85) +
                                FarmaPRNUtility.llenarBlancos(5)+
                                FarmaPRNUtility.alinearIzquierda(pres, 8) +
                                FarmaPRNUtility.llenarBlancos(5)+
                                FarmaPRNUtility.alinearIzquierda(cant, 10)
                                , true);
            }else{
                String desc  = ((String)temp.get(0)).trim();
                vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(8+5)+
                                FarmaPRNUtility.alinearIzquierda(desc, 100)
                                , true);
            }
         
        }

        int numLineas = 19 + pDetallePedido.size();
        if(numLineas < 47)
        {   for(int i=1;i<=(48-numLineas);i++)
            {   vPrint.printLine("", true);
            }
        }
        
        
        //PLACA DEL TRANSPORTISTA
       /* vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+ 
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vPlacaTransportista_Transf.trim(), 15), 
                        true);*/
       
        //NOMBRE O RAZON SOCIAL DE TRANSPORTISTA
       /* vPrint.printLine(FarmaPRNUtility.llenarBlancos(15)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 15), 
                        true);*/
       
        
        
        
          if (VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL) || 
              VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
          {//PLACA DE TRASNPORTISTA
              vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+ 
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vPlacaTransportista_Transf.trim(),15)+FarmaPRNUtility.llenarBlancos(67)+'X', true); 
              //NUMERO DE LICENCIA DE CONDUCIR
              vPrint.printLine(vMargen+"", true);
              
                if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
                //RAZON SOCIAL    
                vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 15)+FarmaPRNUtility.llenarBlancos(20)+"MOTIVO: " + 
                                 FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                 +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                                  64),true);
                }
                else if (!VariablesInventario.vDescMotivo_Transf.trim().equalsIgnoreCase("")){
                   //RAZON SOCIAL
                    
                    vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 15)+FarmaPRNUtility.llenarBlancos(20)+"MOTIVO: " + 
                                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                     ,64),true);  
                } 
          }
          else
          {           //PLACA DEL TRANSPORTISTA
                   vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(12)+ 
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vPlacaTransportista_Transf.trim(), 15), 
                        true);
              //NUMERO DE LICENCIA DE CONDUCIR
              vPrint.printLine(vMargen+"", true);
                if(!VariablesInventario.vDescMotivo_Transf_Larga.trim().equalsIgnoreCase("")){
                    //RAZON SOCIAL
                    
                vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 15)+ 
                                 FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                 +" - "+VariablesInventario.vDescMotivo_Transf_Larga.trim(), 
                                                                  64),true);
                }
                else if (!VariablesInventario.vDescMotivo_Transf.trim().equalsIgnoreCase("")){
                    //RAZON SOCIAL
                    vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+
                        FarmaPRNUtility.alinearIzquierda(VariablesInventario.vTransportista_Transf.trim(), 15)+ 
                                     FarmaPRNUtility.alinearIzquierda(VariablesInventario.vDescMotivo_Transf.trim()
                                     ,64),true);  
                } 
          }
        //RUC DEL TRANSPORTISTA
                vPrint.printLine(vMargen+"", true);
                vPrint.printLine(vMargen+FarmaPRNUtility.llenarBlancos(15)+
                    FarmaPRNUtility.alinearIzquierda(VariablesInventario.vRucTransportista_Transf.trim(), 15), 
                    true);
      
        vPrint.deactivateCondensed();
        vPrint.endPrintServiceSinCompletar();
    }
}


