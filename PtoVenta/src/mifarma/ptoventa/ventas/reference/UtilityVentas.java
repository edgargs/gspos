package mifarma.ptoventa.ventas.reference;


import com.gs.mifarma.componentes.JConfirmDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.awt.Frame;

import java.io.IOException;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;

import java.nio.file.attribute.FileTime;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Calendar;

import java.util.concurrent.TimeUnit;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import mifarma.ptoventa.ventas.DlgIngCodBarraNegativa;
import mifarma.ptoventa.ventas.DlgLoginVtaNegativa;
import mifarma.ptoventa.ventas.DlgRegistroPsicotropico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityVentas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      26.03.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class UtilityVentas {
  
  private static final Logger log = LoggerFactory.getLogger(UtilityVentas.class);
  	/**
	 * Constructor
	 */
	public UtilityVentas() {
  }
  
  public static boolean evaluaPedidoDelivery(JDialog pJDialog, Object pObjectFocus, ArrayList pArrayListVenta)
  {
    if(!VariablesVentas.vEsPedidoDelivery)
    {
      if(pArrayListVenta.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Productos Seleccionados. Para realizar un Pedido Delivery\n" +
          "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
        return false;
      }
      VariablesVentas.vEsPedidoDelivery = true;
    } else
    {
      if(pArrayListVenta.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
          "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
        return false;
      }
      VariablesVentas.vEsPedidoDelivery = false;
    }
    //evaluaTitulo(pJDialog);
    VariablesVentas.vArrayList_ResumenPedido.clear();
    VariablesVentas.vArrayList_PedidoVenta.clear();
    return true;
  }
  
  public static boolean evaluaPedidoConvenio(JDialog pJDialog, Object pObjectFocus, ArrayList pArrayListVenta)
  {
    if(!VariablesVentas.vEsPedidoConvenio)
    {
      if(pArrayListVenta.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Productos Seleccionados. Para realizar un Pedido por Convenio\n" +
          "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
        return false;
      }
      
      /*if(VariablesVentas.vArrayList_Cupones.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Cupones Ingresados. Para realizar un Pedido por Convenio\n" +
          "no deben tener cupones agregados. Verifique!!!", pObjectFocus);
        return false;
      }*/
      
      VariablesVentas.vEsPedidoConvenio = true;
    } else
    {
      if(pArrayListVenta.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
          "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
        return false;
      }
      VariablesVentas.vEsPedidoConvenio = false;
    }
    //evaluaTitulo(pJDialog);
    VariablesVentas.vArrayList_ResumenPedido.clear();
    VariablesVentas.vArrayList_PedidoVenta.clear();
    return true;
  }
  
  
  public static boolean evaluaPedidoInstitucional(JDialog pJDialog, Object pObjectFocus, ArrayList pArrayListVenta)
  {
    if ( !FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) )
    {
      FarmaUtility.showMessage(pJDialog, "No es posible realizar esta operación. Solo un usuario con Rol\n" +
        "Administrador Local puede realizar una venta institucional.", pObjectFocus);
      return false;
    }
    if(!VariablesVentas.vEsPedidoInstitucional)
    {
      if(pArrayListVenta.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Productos Seleccionados. Para realizar un Pedido Institucional\n" +
          "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
        return false;
      }
      VariablesVentas.vEsPedidoInstitucional = true;
    } else
    {
      if(pArrayListVenta.size() > 0)
      {
        FarmaUtility.showMessage(pJDialog, "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
          "no deben haber productos seleccionados. Verifique!!!", pObjectFocus);
        return false;
      }
      VariablesVentas.vEsPedidoInstitucional = false;
    }
    //evaluaTitulo(pJDialog);
    VariablesVentas.vArrayList_ResumenPedido.clear();
    VariablesVentas.vArrayList_PedidoVenta.clear();
    return true;
  }

  /**
   * Agrega/Desagrega los valores seleccionados en el listado de Pedido de Venta
   * (VariablesVentas.vArrayList_PedidoVenta).
   * @param valor 
   * @author Edgar Rios Navarro
   * @since 11.04.2008
   */
  public static void operaProductoSeleccionadoEnArrayList(Boolean valor)
  {
    double auxPrecVta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
    double auxPorcIgv = FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Igv_Prod);
    double auxCantIngr = FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);
    String valIgv = FarmaUtility.formatNumber( (auxPrecVta - (auxPrecVta / ( 1 + (auxPorcIgv / 100))) ) * auxCantIngr);
    VariablesVentas.vVal_Igv_Prod = valIgv;
    
    //ERIOS 03.06.2008 Cuando se ingresa por tratamiento, el total es el calculado
    //y el precio de venta unitario se recalcula.
    if(VariablesVentas.vIndTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      VariablesVentas.vTotalPrecVtaProd = VariablesVentas.vTotalPrecVtaTra;
      VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd/auxCantIngr,3);
    }else if(!VariablesVentas.vEsPedidoConvenio && 
             !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER) )//ERIOS 18.06.2008 Se redondea el total de venta por producto
    {
      VariablesVentas.vTotalPrecVtaProd = (auxCantIngr * auxPrecVta);
      //El redondeo se ha dos digitos hacia arriba ha 0.05.
      /*TO_CHAR( CEIL(VAL_PREC_VTA*100)/100 +
                           CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                                WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                                     (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                                ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/
      
      double valVtaProd =  VariablesVentas.vTotalPrecVtaProd*100;
        log.debug("valVtaProd: 1 "+ valVtaProd); 
      valVtaProd =  FarmaUtility.getDecimalNumber((FarmaUtility.formatNumber(valVtaProd,4)).trim());
        log.debug("valVtaProd: 2 "+ valVtaProd); 
      valVtaProd = Math.ceil(valVtaProd);
        log.debug("valVtaProd: 3 "+ valVtaProd); 
      double aux1 = valVtaProd/100;///Math.ceil(VariablesVentas.vTotalPrecVtaProd*100)/100;
      double aux2 = valVtaProd/10;///Math.ceil(VariablesVentas.vTotalPrecVtaProd*100)/10;
      
    
      int aux21 = (int)(aux2*10);
      int aux3 = FarmaUtility.trunc(aux2)*10;
      int aux4 = 0;

      // --inicio añadido error producto 510991 25.06.2008
      if(aux3==0)
        aux4 = 0;
      else
        aux4 = aux21%aux3;
      //--fin
      
      double aux5;
      if(aux4 == 0)
      {
        aux5 = 0;
      }else if(aux4 <= 5)
      {
        aux5 = (5.0 - aux4)/100;
      }else 
      {
        aux5 = (10.0 - aux4)/100;
      }

      VariablesVentas.vTotalPrecVtaProd = aux1 + aux5;
        log.debug("VariablesVentas.vTotalPrecVtaProd: 1"+ VariablesVentas.vTotalPrecVtaProd); 
      VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd/auxCantIngr,3);
        log.debug("VariablesVentas.vVal_Prec_Vta 2: "+ VariablesVentas.vVal_Prec_Vta);  
    }
    
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesVentas.vCod_Prod); //0
    myArray.add(VariablesVentas.vDesc_Prod);
    myArray.add(VariablesVentas.vUnid_Vta);
    log.debug("VariablesVentas.vVal_Prec_Vta 3: "+ VariablesVentas.vVal_Prec_Vta);
    myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3));
    myArray.add(VariablesVentas.vCant_Ingresada);
    myArray.add("");//myArray.add(VariablesVentas.vPorc_Dcto_1);se supone que este descuento ya no se aplica
    log.debug("VariablesVentas.vVal_Prec_Lista: "+VariablesVentas.vVal_Prec_Lista);
    myArray.add(VariablesVentas.vVal_Prec_Lista);
    myArray.add(FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2));
    myArray.add(VariablesVentas.vVal_Bono); 
    myArray.add(VariablesVentas.vNom_Lab); //9
    myArray.add(VariablesVentas.vVal_Frac);
    myArray.add(VariablesVentas.vPorc_Igv_Prod);
    myArray.add(VariablesVentas.vVal_Igv_Prod);
    myArray.add(VariablesVentas.vNumeroARecargar);//NUMERO TELEFONICO SI ES RECARGA AUTOMATICA
    myArray.add(VariablesVentas.vIndProdVirtual);//INDICADOR DE PRODUCTO VIRTUAL
    myArray.add(VariablesVentas.vTipoProductoVirtual);//TIPO DE PRODUCTO VIRTUAL
    myArray.add(VariablesVentas.vIndProdControlStock ? FarmaConstants.INDICADOR_S : FarmaConstants.INDICADOR_N);//INDICADOR PROD CONTROLA STOCK
    myArray.add(VariablesVentas.vVal_Prec_Lista_Tmp);//PRECIO DE LISTA ORIGINAL SI ES QUE SE MODIFICO
    myArray.add(VariablesVentas.vVal_Prec_Pub);
    myArray.add(VariablesVentas.vIndOrigenProdVta); //19
    myArray.add(FarmaConstants.INDICADOR_N); //20 Indicador Promocion
    myArray.add(VariablesVentas.vPorc_Dcto_2); //21 
    myArray.add(VariablesVentas.vIndTratamiento); //22
    myArray.add(VariablesVentas.vCantxDia); //23 
    myArray.add(VariablesVentas.vCantxDias); //24
    myArray.add(""); //25
    log.info("Producto agregado al pedidoVenta: "+myArray);
    
    FarmaUtility.operaListaProd(VariablesVentas.vArrayList_PedidoVenta, myArray, valor, 0);
    //log.debug("size : " + VariablesVentas.vArrayList_PedidoVenta.size());
    //log.debug("array : " + VariablesVentas.vArrayList_PedidoVenta);
  }
  
  /**
   * Comprometer stock para ventas.
   * @param pCodigoProducto
   * @param pCantidadStk
   * @param pTipoStkComprometido
   * @param pTipoRespaldoStock
   * @param pCantidadRespaldo
   * @param pEjecutaCommit
   * @param pDialogo
   * @param pObjectFocus
   * @return
   * @author Edgar Rios Navarro
   * @since 29.05.2008
   */
  public static boolean actualizaStkComprometidoProd(String pCodigoProducto, 
                                               int pCantidadStk, 
                                               String pTipoStkComprometido, 
                                               String pTipoRespaldoStock, 
                                               int pCantidadRespaldo,
                                               boolean pEjecutaCommit,
                                               JDialog pDialogo,
                                               Object pObjectFocus)
  {
    try
    {
        log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE actualizaStkComprometidoProd");
      DBVentas.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk, 
                                            pTipoStkComprometido);
        
        log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE ejecutaRespaldoStock");
      DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", 
                                      pTipoRespaldoStock, 
                                      pCantidadRespaldo, 
                                      Integer.parseInt(VariablesVentas.vVal_Frac), 
                                      ConstantsPtoVenta.MODULO_VENTAS);
      
      if(pEjecutaCommit)
      {
        FarmaUtility.aceptarTransaccion();
      }
      
      return true;
    }
    catch (SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      //log.error("",sql);
      log.error(null,sql);
      FarmaUtility.showMessage(pDialogo, 
                               "Error al Actualizar Stock del Producto.\n" +
                               "Ponganse en contacto con el area de Sistemas.\n" +
                               "Error - " + 
                               sql.getMessage(), pObjectFocus);
      return false;
    }
  }
  
  /**
   * Se valida los datos del cupon.
   * en local, ya no se valida en matriz
   * @param cadena
   * @return
   * @author javier Callo Quispe
   * @since 04.03.2009
   */
  public static boolean validarCuponEnBD(String nroCupon, JDialog pDialogo,
                                        JTextField pProducto,String indMultiUso, 
                                        String dniCliente){ 
	  // Se modifico la logica de validacion Cupon
	  boolean retorno = true;
          
	  try {
          //Se valida el Cupon en el local
    	  DBVentas.validarCuponEnBD(nroCupon, indMultiUso, dniCliente);
    	  /**se quito la validacion de indicador de linea con matriz y el hecho validar en matriz el cupon**/
        
	  }catch(SQLException e) {
		  retorno = false;
		  log.error("",e);
		  pProducto.setText("");		  
		  switch(e.getErrorCode()) {
			  case 20003: FarmaUtility.showMessage(pDialogo,"La campaña no es valida.",pProducto); break;
			  case 20004: FarmaUtility.showMessage(pDialogo,"Local no valido para el uso del cupon.",pProducto); break;
			  case 20005: FarmaUtility.showMessage(pDialogo,"Local de emisión no valido.",pProducto); break;
			  case 20006: FarmaUtility.showMessage(pDialogo,"Local de emisión no es local de venta.",pProducto); break;
			  case 20007: FarmaUtility.showMessage(pDialogo,"Cupón ya fue usado.",pProducto); break;
			  case 20008: FarmaUtility.showMessage(pDialogo,"Cupón esta anulado.",pProducto); break;
			  case 20009: FarmaUtility.showMessage(pDialogo,"Campaña no valido.",pProducto); break;
			  case 20010: FarmaUtility.showMessage(pDialogo,"Cupon solo de uso para Fidelizados.",pProducto); break;
			  case 20011: FarmaUtility.showMessage(pDialogo,"Cupon no esta vigente .",pProducto); break;
			  default: FarmaUtility.showMessage(pDialogo,"Error al validar el cupon.\n"+e.getMessage(),pProducto); break;
		  }
	  }
	  log.debug("Retorno :" + retorno);
    
    return retorno;
  }
  
  /**
   * Se valida los datos del cupon.
   * @param cadena
   * @return
   * @author Edgar Rios Navarro
   * @since 03.07.2008
   * @deprecated
   */
  public static boolean validaDatoCupon(String cadena, JDialog pDialogo,
                                        JTextField pProducto,String indMultiUso)
  { // Se modifico la logica de valicacion Cupon
    boolean retorno;
    ArrayList arreglo = new ArrayList();
    ArrayList cupon   = new ArrayList();
    ArrayList auxcamp = new ArrayList();
    String valida,descCamp,codCupon;
    String vIndLinea = "";
      log.debug("***validaDatoCupon***");
    try
    {
        //Se valida el Cupon en el local
         //Modificado por DVELIZ 04.10.08
        DBVentas.verificaCupon(cadena,arreglo,indMultiUso, VariablesFidelizacion.vDniCliente);
        valida = FarmaConstants.INDICADOR_S;
        //Se verifica si hay linea para validar el cupon en Matriz
        vIndLinea = FarmaConstants.INDICADOR_N;
                    /*
                     * FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                   FarmaConstants.INDICADOR_N);
                    */
        
        log.debug("vIndLinea " + vIndLinea);
        /*
        if(vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
           valida = DBVentas.verificaCuponMatriz(cadena,indMultiUso,FarmaConstants.INDICADOR_S);
        */
        //--Fin de validacion en Matriz
        
        if(valida.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                VariablesVentas.vArrayList_Cupones.add(arreglo.get(0));
                codCupon = ((String)((ArrayList)arreglo.get(0)).get(0)).trim();
                DBVentas.obtieneInfoCamp(auxcamp, 
                                         ((String)((ArrayList)(arreglo.get(0))).get(1)).trim());
                if (auxcamp.size() > 0){
                    descCamp = 
                            ((String)((ArrayList)auxcamp.get(0)).get(1)).trim();
                    VariablesVentas.vMensCuponIngre = 
                                                    "Se ha agregado el cupón " + codCupon + 
                                                    " de la Campaña " + descCamp + ".";
                    VariablesCampana.vDescCamp = descCamp;
                    FarmaUtility.showMessage(pDialogo, 
                                             VariablesVentas.vMensCuponIngre, 
                                             pProducto);
                }

                FarmaUtility.ordenar(VariablesVentas.vArrayList_Cupones,
                                     9,
                                     FarmaConstants.ORDEN_ASCENDENTE);
                
            retorno = true;
        }
        else if(valida.trim().equalsIgnoreCase("B"))
              {
                retorno = false;
              }else
              {
                retorno = false;
                pProducto.setText("");
                FarmaUtility.showMessage(pDialogo,valida.trim(),pProducto);
              }
        
    }catch(SQLException e)
    {
      retorno = false;
      log.error(null,e);
      pProducto.setText("");
      log.error(null,e);
      switch(e.getErrorCode())
      {
        case 20003: FarmaUtility.showMessage(pDialogo,"La campaña no es valida.",pProducto); break;
        case 20004: FarmaUtility.showMessage(pDialogo,"Local no valido para el uso del cupon.",pProducto); break;
        case 20005: FarmaUtility.showMessage(pDialogo,"Local de emisión no valido.",pProducto); break;
        case 20006: FarmaUtility.showMessage(pDialogo,"Local de emisión no es local de venta.",pProducto); break;
        case 20007: FarmaUtility.showMessage(pDialogo,"Cupón ya fue usado.",pProducto); break;
        case 20008: FarmaUtility.showMessage(pDialogo,"Cupón esta anulado.",pProducto); break;
        case 20009: FarmaUtility.showMessage(pDialogo,"Campaña no valido.",pProducto); break;
        
          //Agregado por DVELIZ 04.10.08
        case 20010: FarmaUtility.showMessage(pDialogo,"Cupon solo de uso para Fidelizados.",pProducto); break;

        case 20011: FarmaUtility.showMessage(pDialogo,"Cupon no esta vigente .",pProducto); break;
        default: FarmaUtility.showMessage(pDialogo,"Error al validar el cupon.\n"+e.getMessage(),pProducto); break;
      }
    }
    log.debug("lista CUPONES...");
    //
    log.debug("...LisCupones:" + VariablesVentas.vArrayList_Cupones);
    return retorno;
  }
  
  
  /**
   * 
   * @param cadena
   * @return
   * @author Edgar Rios Navarro
   * @since 10.07.2008
   */
  public static boolean validaCampanaCupon(String cadena, JDialog pDialogo,JTextField pProducto,String indMultiUso,String codCamp)
{
    boolean retorno = false;
    String vCodCamp="";
    if(indMultiUso.equalsIgnoreCase("N"))
      vCodCamp = cadena.substring(0,5);
    else
      vCodCamp = codCamp;
    
    for(int i=0;i<VariablesVentas.vArrayList_Cupones.size();i++)
    {
      String vAuxCamp = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Cupones,i,1);
      String vTipCupon = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Cupones,i,2);
      
      if(vAuxCamp.equals(vCodCamp) && vTipCupon.equalsIgnoreCase("P"))
      {
        retorno = true;
        pProducto.setText("");
        FarmaUtility.showMessage(pDialogo,"Esta campaña ya fue agregado al pedido.",pProducto);
        break;
      }
    }
    VariablesCampana.vCodCampana = vCodCamp;
    return retorno;
  }
  
  /**
   * Se verifica que el cupon no exista en el arreglo de cupones.
   * @param cadena
   * @return
   * @author Edgar Rios Navarro
   * @since 04.07.2008
   */
  public static boolean validaCupones(String cadena, JDialog pDialogo,JTextField pProducto)
  {
    boolean retorno = false;
    
    for(int i=0;i<VariablesVentas.vArrayList_Cupones.size();i++)
    {
      ArrayList aux = (ArrayList)VariablesVentas.vArrayList_Cupones.get(i);
      if(aux.contains(cadena))
      {
        retorno = true;
        pProducto.setText("");
        FarmaUtility.showMessage(pDialogo,"El cupon ya fue agregado al pedido.",pProducto);
        break;
      }
    }
    
    return retorno;
  }
  
  /**
   * Se verifica que la cadena ingresada corresponda a un cupon.
   * @param pCadena
   * @return
   * @author Edgar Rios Navarro
   * @since 02.07.2008
   */
  public static boolean esCupon(String pCadena, JDialog pDialogo,JTextField pProducto)
  {
    log.info("Valida esCupon");  
    boolean retorno = false;
    boolean isCupon = false;
    int pTamano = pCadena.length();
    
    //obtiene indicador de multiuso de la campaña
    String ind_multiuso="";
    ArrayList aux=new ArrayList();
    try{
     DBVentas.obtieneIndMultiuso(aux,pCadena);
     log.debug("",aux);
     log.info("aux:"+aux);  
        if(aux.size()>0){
     ind_multiuso=(String)((ArrayList)aux.get(0)).get(1);
            isCupon = true;
        }
    }catch(SQLException sql){
      log.error("",sql);
    }
    
    if(pTamano > 1)
    {
        log.info("if(pTamano > 1)"); 
       //se valida el codigo de barra a nivel de local 
      if(
        isCupon // si es CUPON
           && 
           isNumerico(pCadena)  // si es NUMERO
           && 
           pTamano == 13 // si es un posible EAN 13
           && validaCodBarraLocal(pCadena,pDialogo,pProducto) // si NO ES CODIGO DE BARRA
        )
        {
           log.info(" retorno true");   
           retorno = true;    
            
        }
    }    
      log.info("retorno :"+retorno);
    return retorno;
  }
  
  /**
   * Se valida el codigo de barra a nivel de local
   * @since 28.08.08
   * @author JCORTEZ 
   * */
  private static boolean validaCodBarraLocal(String cadena, JDialog pDialogo,JTextField pProducto){
  
  boolean retorno=true;
  String valida="";
  
  try{
    valida= DBVentas.verificaCodBarraLocal(cadena);
    if(valida.equalsIgnoreCase("S")){
    retorno=false;
    log.debug("El codigo de barra "+cadena+" existe en local");
    }else{
        retorno=true;
        log.debug("El codigo de barra "+cadena+" No existe en local");
    }
  }catch(SQLException e){
     log.error("",e);
  }
  
  log.info("validaCodBarraLocal:"+retorno);
  return retorno;
  }
  
  public static boolean isNumerico(String pcadena)
  { 
    char vCaracter ;
    if(pcadena.length()==0)
     return false;
    for(int i=0;i<pcadena.length();i++)
    {
      vCaracter = pcadena.charAt(i);
      if(Character.isLetter(vCaracter))
         return false;
    }
    return true;
  }


    public static void eliminaImagenesCodBarra(){
        try{
        //ERIOS 25.06.2013 Utilizamos el paquete java.nio   
        
        String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
        String carpeta = DBPtoVenta.getDirectorioImagenes();        
        
        Path dir = Paths.get(carpetaRaiz,carpeta);
        if(Files.exists(dir)){
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir,"1*.jpg") ){
                for (Path p : ds) {
                    Files.delete(p);
                }
            } catch (IOException e) {
                log.error("",e);
            }
        }
        
        //ERIOS 2.4.4 Se elimina archivos antiguos
        // Creating the filter
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            
              int ndias = Integer.parseInt(DBCaja.ObtieneNroDiasEliminar());
              
              @Override
              public boolean accept(Path entry) throws IOException {
                  BasicFileAttributes basicAttr = Files.readAttributes(entry, BasicFileAttributes.class);
                   //FileTime creationTime = basicAttr.creationTime();                   
                   FileTime creationTime = basicAttr.lastModifiedTime();
                  long millis = creationTime.to(TimeUnit.MILLISECONDS);
                  
                  Calendar today = Calendar.getInstance();
                  
                  // conseguir la representacion de la fecha en milisegundos
                  long milis1 = millis;
                  long milis2 = today.getTimeInMillis();

                  // calcular la diferencia en milisengundos
                  long diff = milis2 - milis1;

                  // calcular la diferencia en dias
                  long diffDays = diff / (24 * 60 * 60 * 1000);
                  
                  return diffDays > ndias;
              }
          };
        
        if(Files.exists(dir)){
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir,filter) ){
                for (Path p : ds) {
                    Files.delete(p);
                    //log.debug("Elimina "+p.getFileName());
                }
            } catch (IOException e) {
                log.error("",e);
            }
        }
        }catch (Exception e) {
            log.error("",e);
        }  
    }
    
    /**
     * Elimina los Archivos de Texto con antiguedad mayor a 2 dias
     * @return
     * @author DUBILLUZ
     * @since 08.07.09
     * @throws Exception
     */
    public static void eliminaArchivoTxt() throws Exception {
        //ERIOS 25.06.2013 Utilizamos el paquete java.nio   
        
        String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
        String carpetaComprobantes = DBPtoVenta.getDirectorioComprobantes();
        
        Path dir = Paths.get(carpetaRaiz,carpetaComprobantes);
        
        // Creating the filter
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            
            String pListaFecha = "";
            ArrayList<String> pLista = new ArrayList<String>();
            Calendar cFecha = Calendar.getInstance(); 
            String DATE_FORMAT = "yyyyMMdd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            int ndias = Integer.parseInt(DBCaja.ObtieneNroDiasEliminar());
            
            {
                for (int i = ndias * -1; i < 0; i++) {
                    cFecha = Calendar.getInstance();
                    cFecha.add(Calendar.DATE, i + 1);
                
                    pListaFecha = sdf.format(cFecha.getTime());
                    pLista.add(pListaFecha);
                }
            }
            
         public boolean accept(Path entry) throws IOException {
             boolean vRetorno = true;
             String fileName = entry.getFileName().toString();
             for(String fecha:pLista){
                 if(fileName.toLowerCase().endsWith("txt") && fileName.startsWith(fecha)){
                     vRetorno = false;
                     break;
                 }
             }
            return vRetorno;
         }
        };
        
        if(Files.exists(dir)){
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir,filter) ){
                for (Path p : ds) {
                    Files.delete(p);
                    //log.debug("Elimina "+p.getFileName());
                }
            } catch (IOException e) {
                log.error("",e);
            }
        }
    }
    
    
    /**
     * este metodo obtiene los descuentos para actualizacion de pedido vta detalle
     * @author dveliz
     * @since 09.10.08
     * @param codProd
     * @param porcDcto1
     * @param codCampCupon
     * @param ahorro
     * @param porcDctoCalc
     */
    public static void obtieneDctosActualizaPedidoDetalle(String codProd,
                                                          String porcDcto1,
                                                          String codCampCupon,
                                                          String ahorro,
                                                          String porcDctoCalc){
        /*
        VariablesVentas.vActDctoDetPedVta = new ArrayList();
        VariablesVentas.vActDctoDetPedVta.add(codProd);
        VariablesVentas.vActDctoDetPedVta.add(porcDcto1);
        VariablesVentas.vActDctoDetPedVta.add(codCampCupon);
        VariablesVentas.vActDctoDetPedVta.add(ahorro);
        VariablesVentas.vActDctoDetPedVta.add(porcDctoCalc);
        */
        // 19.02.2009 DUBILLUZ
        
        log.debug("diego 11 ");
        ArrayList vActDctoDetPedVta = new ArrayList();
        log.debug("diego 22 vActDctoDetPedVta"+vActDctoDetPedVta);
        vActDctoDetPedVta.add(codProd);
        log.debug("diego 33 vActDctoDetPedVta"+vActDctoDetPedVta);
        vActDctoDetPedVta.add(porcDcto1);
        log.debug("diego 44 vActDctoDetPedVta"+vActDctoDetPedVta);
        vActDctoDetPedVta.add(codCampCupon);
        log.debug("diego 55 vActDctoDetPedVta"+vActDctoDetPedVta);
        vActDctoDetPedVta.add(ahorro);
        log.debug("diego 66 vActDctoDetPedVta"+vActDctoDetPedVta);
        vActDctoDetPedVta.add(porcDctoCalc);        
        log.debug("obtieneDctosActualizaPedidoDetalle "+ vActDctoDetPedVta);
        log.debug("VariablesVentas.vResumenActDctoDetPedVta "+ VariablesVentas.vResumenActDctoDetPedVta);
        VariablesVentas.vResumenActDctoDetPedVta.add(vActDctoDetPedVta);
        log.debug("VariablesVentas.vResumenActDctoDetPedVta "+ VariablesVentas.vResumenActDctoDetPedVta);
    }
    
    /**
     * Retorna true si el producto si aplico el precio de la campana cupon
     * Esto incluye para fidelizacion y campañas automaticas.
     * @param pCodProd
     * @return
     */
    public static boolean isAplicoPrecioCampanaCupon(String pCodProd,String pIndProdCamp){
        
        String pCodAux = "";
        log.debug("jcallo: metodo isAplicoPrecioCampanaCupon() VariablesVentas.vListaProdAplicoPrecioDescuento : "+VariablesVentas.vListaProdAplicoPrecioDescuento);
        if (VariablesVentas.vListaProdAplicoPrecioDescuento.size() > 0) {
            for (int i = 0; 
                 i < VariablesVentas.vListaProdAplicoPrecioDescuento.size(); 
                 i++) {
                pCodAux = 
                        (String)VariablesVentas.vListaProdAplicoPrecioDescuento.get(i);
                if (pCodAux.trim().equalsIgnoreCase(pCodProd.trim())) {
                    return true;
                }
            }
        }
        
        
        if(pIndProdCamp.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
       log.debug("RETORNA FALSE el metodo isAplicoPrecioCampanaCupon");
        
        return false;
    }
    
    /**
     * Se verifica si el cupon ya fue agregado
     * tambien verifica si ya existe la campaña
     * @param nroCupon
     * @author Javier Callo Quispe
     * @since  04.03.2009
     */
    public static boolean existeCuponCampana(String nroCupon, JDialog pDialogo,JTextField pProducto)
    {
      boolean retorno = false;
      Map mapCupon;
      log.debug("nroCupon:"+nroCupon);
  	  String codCampCupon  = nroCupon.substring(0,5);
  	  log.debug("codCampCupon:"+codCampCupon);
  	  String auxCodCupon   = "";
  	  String auxCodCampCupon   = "";
      for(int i=0;i<VariablesVentas.vArrayList_Cupones.size();i++)
      {
    	mapCupon = (Map)VariablesVentas.vArrayList_Cupones.get(i);
    	auxCodCupon = (String)mapCupon.get("COD_CUPON");
    	auxCodCampCupon  = (String)mapCupon.get("COD_CAMP_CUPON");
    	
        if(nroCupon.equalsIgnoreCase(auxCodCupon))
        {
          retorno = true;
          pProducto.setText("");
          FarmaUtility.showMessage(pDialogo,"El cupon ya fue agregado al pedido.",pProducto);
          break;
        }
        
        if(codCampCupon.equalsIgnoreCase(auxCodCampCupon))
        {
          retorno = true;
          pProducto.setText("");
          FarmaUtility.showMessage(pDialogo,"Esta campaña ya fue agregado al pedido."+ VariablesVentas.vArrayList_Cupones.size(),pProducto);
          break;
        }
        
      }
      
      return retorno;
    }
    
    /**
     * productos con campanias aplicables
     * **/
    public static List prodsCampaniasAplicables(List listProds,
                                                List listCamps,
                                                ArrayList pListaProd){
    	List listProdCamps = new ArrayList();
    	try{
             listProdCamps = DBVentas.prodsCampaniasAplicables(listProds, listCamps);
             
    	}catch(SQLException se){
    		log.error("ERRORSQL AL OBTENER EL LISTADO de productos campañas aplicables:"+se);
    	}catch(Exception e){
    		log.error("ERROR AL OBTENER EL LISTADO de productos campañas aplicables:"+e);
    	}
    	
    	return listProdCamps;
    	
    }
    
    /**
     *      
     * metodo encargado de redondear un double nD, a n decimales nDEC
     * @param nD valor a redondear
     * @param nDec cantidad decimales a redondear
     * 
     * */
    public static double Redondear(double nD, int nDec)  
	{  
		return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);  
	}
	
    /**
     *      
     * metodo encargado de truncar un double nD, a n decimales nDEC
     * @param nD valor a truncar
     * @param nDec cantidad decimales a truncar
     * 
     * */
	public static double Truncar(double nD, int nDec)  
	{  
		if(nD > 0)  
			nD = Math.floor(nD * Math.pow(10,nDec))/Math.pow(10,nDec);  
		else  
			nD = Math.ceil(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
		return nD;  
	}
	
	/**
     * metodo encargado de ajustar redondear siempre abajo
     * que termine en 0 ó 5
     * 
     * @param nD valor a ajustar el monto que termine en 0 ó 5  la ultima cifra
     * @param nDec cantidad decimales a ajustar
     * 
     * */
	/*
        public static double ajustarMonto(double nD, int nDec)  
	{  
		
		long aux = 0L;
		long resto = 0L;
		
		if(nD > 0){  
			//nD = Math.floor(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
			aux  = (long)Math.floor(nD * Math.pow(10,nDec));			
			resto = aux%10;
			aux = aux/10;
			if(resto < 5)
				resto = 0;
			else
				resto = 5;
			nD = ((double)(aux*10+resto))/Math.pow(10,nDec);
		} else {  
			//nD = Math.ceil(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
			aux  = (long)Math.ceil(nD * Math.pow(10,nDec));
			aux  = (long)Math.floor(nD * Math.pow(10,nDec));			
			resto = aux%10;
			aux = aux/10;
			if(resto < 5)
				resto = 5;
			else
				resto = 0;
			nD = ((double)(aux*10+resto))/Math.pow(10,nDec);
		}
		return nD;  
	}
    
    */
    //14.10.2009 jcortez
    /*reemplazo de
     * aux  = (long)Math.floor(nD * Math.pow(10,nDec));
     * por
     *                     aux  = (long) FarmaUtility.getDecimalNumber(
                                        FarmaUtility.formatNumber(
                                            Math.pow(10,nDec) * nD,
                                            nDec
                                            )
                                        );
     * */
    /**
     * @author joliva
     * @since  14.10.2009
     * @param nD
     * @param nDec
     * @return
     */
    public static double ajustarMonto(double nD, int nDec)  
    {  
            //log.debug("nD old:"+nD);
            
            long aux = 0L;
            long resto = 0L;
            
            int signo = 1;
            if(nD<0)
                signo = -1;
            
            //log.debug("signo:"+signo);
            nD = nD * signo;
            //log.debug("nD pw:"+nD);
                    aux  = (long) FarmaUtility.getDecimalNumber(
                                        FarmaUtility.formatNumber(
                                            Math.pow(10,nDec) * nD,
                                            nDec
                                            )
                                        );
                    
                    //log.debug("1) aux:"+aux);
                
                    resto = aux%10;
                    //log.debug("2) resto:"+resto);
                    aux = aux/10;
                    
                    //log.debug("3) aux:"+aux);
                    
                    if(resto < 5)
                            resto = 0;
                    else
                            resto = 5;
                    
                    //log.debug("3) aux:"+aux);
                    
                    nD = ((double)(aux*10+resto))/Math.pow(10,nDec);
                    
                    //log.debug("4) nD:"+nD);
           nD = nD * signo;       
           //log.debug("nD Nuevo:"+nD);
        return nD; 
    }    
    
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void carga_impresoras(Frame myParentFrame)
       {
           PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
           boolean pEncontroImp = false;
                 if(servicio != null)
                 {
                   try
                   {
                     String pNombreImpresora = "";
                     //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                     VariablesPtoVenta.vIndExisteImpresoraConsejo = DBCaja.obtieneNameImpConsejos();
                     VariablesPtoVenta.vTipoImpTermicaxIp=DBCaja.obtieneTipoImprConsejoXIp();//JCHAVEZ 03.07.2009 obtiene tipo de imopresora por IP
                       log.debug("Tipo Impresora :" + VariablesPtoVenta.vTipoImpTermicaxIp);  
                     log.debug("Buscando impresora :"+VariablesPtoVenta.vIndExisteImpresoraConsejo);
                     log.debug("impresoras..encontradas...");
                     for (int i = 0; i < servicio.length; i++)
                     {
                       PrintService impresora = servicio[i];
                       String pNameImp = impresora.getName().toString().trim();
                       pNombreImpresora = retornaUltimaPalabra(pNameImp,"\\").trim();
                       //if (pNameImp.toUpperCase().indexOf(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase()) != -1)
                       //Buscara el nombre.
                       log.debug(i+") pNameImp:"+pNameImp);
                       log.debug(i+") pNombreImpresora:"+pNombreImpresora);
                       log.debug("**************************************");
                       if (pNombreImpresora.trim().toUpperCase().equalsIgnoreCase(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase()))
                       {
                         log.info("Encotró impresora térmica");
                         pEncontroImp = true;
                         VariablesPtoVenta.vImpresoraActual =  impresora;
                         break;
                       }
                     }
                     
                     /**0
                      * 03/07/2009 
                      * dubilluz 
                      * se genero error en produccion
                      * String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                     
                     for (int i = 0; i < servicio.length; i++)
                     {
                       PrintService impresora = servicio[i];
                       String pNameImp = impresora.getName().toString().trim();
                       
                       if (pNameImp.indexOf(vIndExisteImpresora) != -1)
                       {
                         VariablesPtoVenta.vImpresoraActual =  impresora;
                         break;
                       }
                     } */
                       
                   }
                   catch (SQLException sqlException)
                   {
                     log.error(null,sqlException);
                   }
                 }
                 if(!pEncontroImp){
                     JOptionPane.showMessageDialog(myParentFrame, "No se encontró la impresora de térmica :"+
                                                   VariablesPtoVenta.vIndExisteImpresoraConsejo+
                                                   "\nVerifique que tenga instalada la impresora.", 
                                                   "Mensaje del Sistema", 
                                                   JOptionPane.WARNING_MESSAGE);
                 }
       }
    
    public static String retornaUltimaPalabra(String pCadena,String pSeparador){
        //log.debug(pCadena);
        //log.debug(pSeparador);
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
   
   //JMIRANDA 23.09.09 
    public static boolean validaCodBarraLocal(String cadena){
    
    boolean retorno=true;
    String valida="";
    
    try{
      valida= DBVentas.verificaCodBarraLocal(cadena);
      if(valida.equalsIgnoreCase("S")){
      retorno=false;
      log.debug("2. El codigo de barra "+cadena+" existe en local");
      }else{
      log.debug("2. El codigo de barra "+cadena+" No existe en local");
      }
    }catch(SQLException e){
       log.error("",e);
    }
    return retorno;
    } 
    
    /**
     * Genera Salto de Linea al traer Mensaje de base de Datos con limitador definido en tab_gral
     * @author JMIRANDA
     * @since  29.09.2009
     * @return sMensaje  Mensaje Editado
     * @param pMensaje   Mensaje al que se le va realizar el salto de linea 
     * */
    public static String saltoLineaConLimitador(String pMensaje){
        String sMensaje = "";
        try {
        String[] temp;         
        //String delimiter = "_";
        String delimiter = DBVentas.getDelimitadorMensaje();
        temp = pMensaje.split(delimiter);
                
           for(int i=0 ; i < temp.length ; i++){
             sMensaje += temp[i]+"\n";                      
           }
        
            }catch(SQLException sql){                
                log.debug("Error al obtener limitador del Mensaje. "+sql);                
            }
        return sMensaje;    
    }
    
    /**
     * Opera el stock comprometido, copia modificada de otro metodo
     * @author ASOSA
     * @since 01.07.2010
     * @param pCodigoProducto
     * @param pCantidadStk
     * @param pTipoStkComprometido
     * @param pTipoRespaldoStock
     * @param pCantidadRespaldo
     * @param pEjecutaCommit
     * @param pDialogo
     * @param pObjectFocus
     * @return
     */
    public static boolean operaStkCompProdResp(String pCodigoProducto, 
                                                 int pCantidadStk, 
                                                 String pTipoStkComprometido, 
                                                 String pTipoRespaldoStock, 
                                                 int pCantidadRespaldo,
                                                 boolean pEjecutaCommit,
                                                 JDialog pDialogo,
                                                 Object pObjectFocus,
                                               String secRespaldo)
    {
      /*try
      {*/
       /*
        log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE actualizaStkComprometidoProd");
        DBVentas.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk, 
                                              pTipoStkComprometido);
        log.debug("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE ejecutaRespaldoStock");
        DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", 
                                        pTipoRespaldoStock, 
                                        pCantidadRespaldo, 
                                        Integer.parseInt(VariablesVentas.vVal_Frac), 
                                        ConstantsPtoVenta.MODULO_VENTAS);
        */
        /*
         VariablesVentas.secRespStk=DBVentas.operarResStkAntesDeCobrar(pCodigoProducto,
                                                                       String.valueOf(pCantidadStk),
                                                                       VariablesVentas.vVal_Frac,
                                                                       secRespaldo,
                                                                       ConstantsPtoVenta.MODULO_VENTAS);
         */
          VariablesVentas.secRespStk = "0";
          boolean flag = true;
        /*boolean flag=true;
          if(VariablesVentas.secRespStk.trim().equalsIgnoreCase("N")){
            FarmaUtility.liberarTransaccion();
            flag=false;
        }else{
            FarmaUtility.aceptarTransaccion();
            flag=true;
        }*/
            
          return flag;
        /*
      }
      catch (SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        //log.error("",sql);
        log.error(null,sql);
        FarmaUtility.showMessage(pDialogo, 
                                 "Error al Actualizar Stock del Producto.\n" +
                                 "Ponganse en contacto con el area de Sistemas.\n" +
                                 "Error - " + 
                                 sql.getMessage(), pObjectFocus);
        return false;
      }
        */
    }    
    
    
    public static void operaProductoSeleccionadoEnArrayList_02(Boolean valor,String secRespStk)
    {
      double auxPrecVta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
      double auxPorcIgv = FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Igv_Prod);
      double auxCantIngr = FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);
      String valIgv = FarmaUtility.formatNumber( (auxPrecVta - (auxPrecVta / ( 1 + (auxPorcIgv / 100))) ) * auxCantIngr);
      VariablesVentas.vVal_Igv_Prod = valIgv;
      
      //ERIOS 03.06.2008 Cuando se ingresa por tratamiento, el total es el calculado
      //y el precio de venta unitario se recalcula.
      if(VariablesVentas.vIndTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
        VariablesVentas.vTotalPrecVtaProd = VariablesVentas.vTotalPrecVtaTra;
        VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd/auxCantIngr,3);
      }else if(!VariablesVentas.vEsPedidoConvenio && 
               !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER) )//ERIOS 18.06.2008 Se redondea el total de venta por producto
      {
        VariablesVentas.vTotalPrecVtaProd = (auxCantIngr * auxPrecVta);
        //El redondeo se ha dos digitos hacia arriba ha 0.05.
        /*TO_CHAR( CEIL(VAL_PREC_VTA*100)/100 +
                             CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                                  WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                                       (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                                  ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/
        
        double valVtaProd =  VariablesVentas.vTotalPrecVtaProd*100;
          log.debug("valVtaProd: 1 "+ valVtaProd); 
        valVtaProd =  FarmaUtility.getDecimalNumber((FarmaUtility.formatNumber(valVtaProd,4)).trim());
          log.debug("valVtaProd: 2 "+ valVtaProd); 
        valVtaProd = Math.ceil(valVtaProd);
          log.debug("valVtaProd: 3 "+ valVtaProd); 
        double aux1 = valVtaProd/100;///Math.ceil(VariablesVentas.vTotalPrecVtaProd*100)/100;
        double aux2 = valVtaProd/10;///Math.ceil(VariablesVentas.vTotalPrecVtaProd*100)/10;
        
      
        int aux21 = (int)(aux2*10);
        int aux3 = FarmaUtility.trunc(aux2)*10;
        int aux4 = 0;

        // --inicio añadido error producto 510991 25.06.2008
        if(aux3==0)
          aux4 = 0;
        else
          aux4 = aux21%aux3;
        //--fin
        
        double aux5;
        if(aux4 == 0)
        {
          aux5 = 0;
        }else if(aux4 <= 5)
        {
          aux5 = (5.0 - aux4)/100;
        }else 
        {
          aux5 = (10.0 - aux4)/100;
        }

        VariablesVentas.vTotalPrecVtaProd = aux1 + aux5;
          log.debug("VariablesVentas.vTotalPrecVtaProd: 1"+ VariablesVentas.vTotalPrecVtaProd); 
        VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd/auxCantIngr,3);
          log.debug("VariablesVentas.vVal_Prec_Vta 2: "+ VariablesVentas.vVal_Prec_Vta);  
      }
      
      ArrayList myArray = new ArrayList();
      myArray.add(VariablesVentas.vCod_Prod); //0
      myArray.add(VariablesVentas.vDesc_Prod);
      myArray.add(VariablesVentas.vUnid_Vta);
      log.debug("VariablesVentas.vVal_Prec_Vta 3: "+ VariablesVentas.vVal_Prec_Vta);
      myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3));
      myArray.add(VariablesVentas.vCant_Ingresada);
      myArray.add("");//myArray.add(VariablesVentas.vPorc_Dcto_1);se supone que este descuento ya no se aplica
      log.debug("VariablesVentas.vVal_Prec_Lista: "+VariablesVentas.vVal_Prec_Lista);
      myArray.add(VariablesVentas.vVal_Prec_Lista);
      myArray.add(FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2));
      myArray.add(VariablesVentas.vVal_Bono); 
      myArray.add(VariablesVentas.vNom_Lab); //9
      myArray.add(VariablesVentas.vVal_Frac);
      myArray.add(VariablesVentas.vPorc_Igv_Prod);
      myArray.add(VariablesVentas.vVal_Igv_Prod);
      myArray.add(VariablesVentas.vNumeroARecargar);//NUMERO TELEFONICO SI ES RECARGA AUTOMATICA
      myArray.add(VariablesVentas.vIndProdVirtual);//INDICADOR DE PRODUCTO VIRTUAL
      myArray.add(VariablesVentas.vTipoProductoVirtual);//TIPO DE PRODUCTO VIRTUAL
      myArray.add(VariablesVentas.vIndProdControlStock ? FarmaConstants.INDICADOR_S : FarmaConstants.INDICADOR_N);//INDICADOR PROD CONTROLA STOCK
      myArray.add(VariablesVentas.vVal_Prec_Lista_Tmp);//PRECIO DE LISTA ORIGINAL SI ES QUE SE MODIFICO
      myArray.add(VariablesVentas.vVal_Prec_Pub);
      myArray.add(VariablesVentas.vIndOrigenProdVta); //19
      myArray.add(FarmaConstants.INDICADOR_N); //20 Indicador Promocion
      myArray.add(VariablesVentas.vPorc_Dcto_2); //21 
      myArray.add(VariablesVentas.vIndTratamiento); //22
      myArray.add(VariablesVentas.vCantxDia); //23 
      myArray.add(VariablesVentas.vCantxDias); //24
      myArray.add(""); //25
      myArray.add(secRespStk); //ASOSA, 01.07.2010
      log.info("<<TCT 1>>Producto agregado al pedidoVenta: "+myArray);
      
      FarmaUtility.operaListaProd(VariablesVentas.vArrayList_PedidoVenta, myArray, valor, 0);
      //log.debug("size : " + VariablesVentas.vArrayList_PedidoVenta.size());
      //log.debug("array : " + VariablesVentas.vArrayList_PedidoVenta);
    }
    
    public static boolean getIndProdFarma(String pCodProd, Object pObj, JDialog pDia){
        boolean flag = false;
        try {
            String rpta =  DBVentas.getIndProdFarma(pCodProd);
            if(rpta.equalsIgnoreCase("S"))
                flag = true;
        }
        catch (SQLException sql) {
            flag = false;
            //FarmaUtility.showMessage(pDia,sql.getMessage(),pObj);
            if (sql.getErrorCode() > 20000) {
               FarmaUtility.showMessage(pDia, 
                                        sql.getMessage().substring(10,sql.getMessage().indexOf("ORA-06512")), 
                                        pObj);
            } else {
               FarmaUtility.showMessage(pDia, 
                                        "Ocurrió un error al validar el convenio.\n" +sql.getMessage(), 
                                        pObj);
            }                                    
        }
        return flag;
    }

    public static boolean getIndImprimeCorrelativo() {        
        boolean flag = true;
        String vInd = "S";
        try{
            vInd = DBVentas.getIndImprimirCorrelativo(); 
            if (vInd.trim().equalsIgnoreCase("N")){
                flag = false;
            }
        }
        catch(SQLException sql){
            flag = false;
            sql.getMessage();
        }
        return flag;
    }
    public static String getProdVendidos(){
        String pCadenaProductosVendidos = "";
        ArrayList array = new ArrayList();
        for (int i = 0;i < VariablesVentas.vArrayList_ResumenPedido.size();i++) {
            array = (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i);
            pCadenaProductosVendidos += ((String)(array.get(0))).trim()+"@";
            }
        
        for (int i = 0;i < VariablesVentas.vArrayList_Prod_Promociones.size();i++) {
            //array = ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
            array = ((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i));
            pCadenaProductosVendidos += ((String)(array.get(0))).trim()+"@";
        }
        
        return pCadenaProductosVendidos.trim();
    }    
    

 /**
     * Abre el formulario de Registro Psicotropico
     * @author wvillagomez
     * @since 03.09.2013
     * @param myParentFrame - tipo Frame
     * @return boolean
     */
    public static boolean registroDatosRestringidos(Frame myParentFrame){
        DlgRegistroPsicotropico dlgindpacmed = new DlgRegistroPsicotropico(myParentFrame,"",true);
        dlgindpacmed.setVisible(true);
        boolean vRetorno = FarmaVariables.vAceptar;
        FarmaVariables.vAceptar = false;
        return vRetorno;
    }

     /**
      * Verifica si el pedido tiene productos con venta restringida
      * @author wvillagomez
      * @since 03.09.2013
      * @param pNumPedVta - tipo String
      * @return boolean
      */
    public static boolean getVentaRestringida(String pNumPedVta) throws SQLException {
        boolean vRetorno = false;
    
        String vtaRestringida = DBVentas.getVentaRestringida(pNumPedVta);
        if(vtaRestringida.equals("S")){
            vRetorno = true;
        }
        return vRetorno;
    }
     
     public static boolean getIndVtaNegativa(){
         String pValor = "";
         boolean pResultado = false;
         try {
            pValor = DBVentas.getIndPermiteVtaNegativa();
        } catch (SQLException sqle) {
            log.error("",sqle);
        }
        
        if(pValor.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            pResultado  = true;
         
        return pResultado ;
     }
    
    public static boolean permiteVtaNegativa(Frame myParentFrame,JDialog pJDialog,String pCodProd, String pCantidad, String pValFrac){
        boolean pResultado = false;
        // Consultara si Permite Venta Negativa en la configuracion del local.
        //VariablesVentas.vCod_Prod,VariablesVentas.vVal_Frac
        String pConsulta = "";


        if (VariablesVentas.vEsPedidoDelivery) {
            
            try {
                pConsulta =
                        DBVentas.getPermiteVtaNegativa(pCodProd, pCantidad, pValFrac);
                FarmaUtility.liberarTransaccion();
            } catch (Exception nfe) {
                log.error("", nfe);
            }
            log.debug(">>><<< " + pConsulta);
            if (pConsulta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                pResultado = false;
            else {
                String[] pListaDatos = pConsulta.trim().split("@");
                //Aqui se colocara lo programado y solicitado     >>
                //47 STREET AT SPRAY FEELING@                     >> 1
                //Usted, desea vender 80 unidades y su stock @    >> 2
                //En Sistema es de 76@                            >> 3
                //¿Desea Vender en Negativo?@                     >> 4
                //Consulte a su Jefe de Local@                    >> 5
                //4                                               >> 6
                String pLineaUno = pListaDatos[0].toString().trim();
                String pLineaDos = pListaDatos[1].toString().trim();
                String pLineaTres = pListaDatos[2].toString().trim();
                String pLineaCuatro = pListaDatos[3].toString().trim();
                String pLineaCinco = pListaDatos[4].toString().trim();
                String pLineaSeis = pListaDatos[5].toString().trim();

                boolean pRespuesta = JConfirmDialog.rptaConfirmDialogDefaultNo(pJDialog, pLineaUno + "\n" +
                        "\n" +
                        pLineaDos + "\n" +
                        pLineaTres);

                if (pRespuesta) {
                    //pResultado = true;
                    // Ingresara los codigo de barra de los tdos los productos
                    // las N veces Posible
                    if (!VariablesVentas.vEsPedidoDelivery) {
                        DlgIngCodBarraNegativa dlgIngresoCantidad =
                            new DlgIngCodBarraNegativa(myParentFrame, "", true, pCodProd, pLineaUno,
                                                       Integer.parseInt(pCantidad));

                        dlgIngresoCantidad.setVisible(true);
                        if (FarmaVariables.vAceptar) {
                            //CLAVE DE QF
                            if (cargaValidaLogin(myParentFrame, pJDialog,
                                                pCodProd, pLineaUno,
                                                 Integer.parseInt(pLineaSeis.trim()))) {
                                log.debug("GRABA SOLICITUD");
                                // ingresa solicitud de VENTA NEGATIVA
                                grabaSolicitud(                                                        pCodProd, 
                                                        pCantidad,
                                                        pValFrac);
                                pResultado = true;
                            }
                        }
                    }
                    else{
                        pResultado = true;
                    }
                } else {
                    pResultado = false;
                }


            }
        }

        return pResultado;
    }
    
    private static boolean cargaValidaLogin(Frame myParentFrame, JDialog pJDialog,
                                     String pCodProd,
                                     String pDescripcionProd,
                                     int    pCantVeces) {
        
        VariablesVentas.vQFApruebaVTANEGATIVA = "";
        
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLoginVtaNegativa dlgLogin = 
                new DlgLoginVtaNegativa(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, 
                             true,pCodProd,pDescripcionProd ,pCantVeces);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            
            if(FarmaVariables.vAceptar){
                // GUARDA EL USUARIO DEL QF
                VariablesVentas.vQFApruebaVTANEGATIVA = FarmaVariables.vIdUsu;
            }
            
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("",e);
            FarmaUtility.showMessage(pJDialog, 
                                     "Ocurrio un error al validar rol de usuariario \n : " + 
                                     e.getMessage(), null);
        }
        return FarmaVariables.vAceptar;
    }
    
    public static void grabaSolicitud(String pCodProd, String pCantidad, String pValFrac){
        //VariablesVentas.vCodSolicitudVtaNegativa = "";
        
        try {
            VariablesVentas.vCodSolicitudVtaNegativa =
                    DBVentas.getGrabaSolcitudVtaNegativa(VariablesVentas.vCodSolicitudVtaNegativa,
                                                        VariablesVentas.vQFApruebaVTANEGATIVA,
                                                        pCodProd, 
                                                        pCantidad,
                                                        pValFrac);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception nfe) {
            FarmaUtility.liberarTransaccion();
            log.error("",nfe);
        }
        
    }
}


