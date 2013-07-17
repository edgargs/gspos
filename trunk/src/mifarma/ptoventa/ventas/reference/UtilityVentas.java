package mifarma.ptoventa.ventas.reference;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.awt.Frame;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Calendar;

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
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


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
  
  private static final Log log = LogFactory.getLog(UtilityVentas.class);
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
        System.out.println("valVtaProd: 1 "+ valVtaProd); 
      valVtaProd =  FarmaUtility.getDecimalNumber((FarmaUtility.formatNumber(valVtaProd,4)).trim());
        System.out.println("valVtaProd: 2 "+ valVtaProd); 
      valVtaProd = Math.ceil(valVtaProd);
        System.out.println("valVtaProd: 3 "+ valVtaProd); 
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
        System.out.println("VariablesVentas.vTotalPrecVtaProd: 1"+ VariablesVentas.vTotalPrecVtaProd); 
      VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd/auxCantIngr,3);
        System.out.println("VariablesVentas.vVal_Prec_Vta 2: "+ VariablesVentas.vVal_Prec_Vta);  
    }
    
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesVentas.vCod_Prod); //0
    myArray.add(VariablesVentas.vDesc_Prod);
    myArray.add(VariablesVentas.vUnid_Vta);
    System.out.println("VariablesVentas.vVal_Prec_Vta 3: "+ VariablesVentas.vVal_Prec_Vta);
    myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3));
    myArray.add(VariablesVentas.vCant_Ingresada);
    myArray.add("");//myArray.add(VariablesVentas.vPorc_Dcto_1);se supone que este descuento ya no se aplica
    System.out.println("VariablesVentas.vVal_Prec_Lista: "+VariablesVentas.vVal_Prec_Lista);
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
    //System.out.println("size : " + VariablesVentas.vArrayList_PedidoVenta.size());
    //System.out.println("array : " + VariablesVentas.vArrayList_PedidoVenta);
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
        System.out.println("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE actualizaStkComprometidoProd");
      DBVentas.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk, 
                                            pTipoStkComprometido);
        
        System.out.println("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE ejecutaRespaldoStock");
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
      //sql.printStackTrace();
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
		  log.error(e);
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
      System.out.println("***validaDatoCupon***");
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
        
        System.out.println("vIndLinea " + vIndLinea);
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
    System.out.println("lista CUPONES...");
    //
    System.out.println("...LisCupones:" + VariablesVentas.vArrayList_Cupones);
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
    System.err.println("Valida esCupon");  
    boolean retorno = false;
    boolean isCupon = false;
    int pTamano = pCadena.length();
    
    //obtiene indicador de multiuso de la campaña
    String ind_multiuso="";
    ArrayList aux=new ArrayList();
    try{
     DBVentas.obtieneIndMultiuso(aux,pCadena);
     System.out.println(aux);
     System.err.println("aux:"+aux);  
        if(aux.size()>0){
     ind_multiuso=(String)((ArrayList)aux.get(0)).get(1);
            isCupon = true;
        }
    }catch(SQLException sql){
      sql.printStackTrace();
    }
    
    if(pTamano > 1)
    {
        System.err.println("if(pTamano > 1)"); 
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
           System.err.println(" retorno true");   
           retorno = true;    
            
        }
    }    
      System.err.println("retorno :"+retorno);
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
    System.out.println("El codigo de barra "+cadena+" existe en local");
    }else{
        retorno=true;
        System.out.println("El codigo de barra "+cadena+" No existe en local");
    }
  }catch(SQLException e){
     e.printStackTrace();
  }
  
  System.err.println("validaCodBarraLocal:"+retorno);
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


    public static void eliminaImagenesCodBarra() throws Exception {
        Runtime r = Runtime.getRuntime();
        Process p;
        BufferedReader is;
        String line;
        String cmd;
        //cmd = "del c:\\*.jpg";//
        String dir = DBCaja.ObtieneDirectorio();
        //String comando = "cmd.exe /C del c:\\1*.jpg";
        String comando = "cmd.exe /C del "+dir.trim()+"1*.jpg";
        System.out.println("Elimina .jpg ");

        p = r.exec(comando);
    }
    
    /**
     * Elimina los Archivos de Texto con antiguedad mayor a 2 dias
     * @return
     * @author JMIRANDA
     * @throws Exception
     */
    public static void eliminaArchivoTxt2() throws Exception {
        Runtime r = Runtime.getRuntime();
        Process p,p1,p2,p3,p4;
        BufferedReader is;
        String line;
        String cmd;
        //cmd = "del c:\\*.jpg";//
        String dir = DBCaja.ObtieneDirectorio();
        //String comando = "cmd.exe /C del c:\\1*.jpg";
        
        //Archivos desde hasta ---------
        
        //JMIRANDA  07/07/2009
        Date vFecImpr = new Date();
        
        String fechaImpresion;
        String vDirT = "comprobantes";      
        String DATE_FORMAT = "yyyyMMdd";
           SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            // System.out.println("Today is " + sdf.format(vFecImpr));
           fechaImpresion =  sdf.format(vFecImpr);                
               
        // --------------
        
        /*
        //Se ejecuta Archivo .bat
        String comando = "cmd.exe /C "+dir.trim()+"elimina.bat";
        p = r.exec(comando);
        System.out.println("com: "+comando);
        p.wait(10);
        */
        
        //Se crea el Directorio Temporal
        String comando = "cmd.exe /C mkdir "+dir.trim()+vDirT.trim();
        p = r.exec(comando);
        if (p.waitFor()==1){
            p.destroy();
            System.out.println("Se mata proceso P= "+p.waitFor());
        }
        //p.waitFor();       
        System.out.println("comP: "+comando+" - ");          
        
        //Se copian los Archivos al Directorio Temporal vDirT
        comando = "cmd.exe /C xcopy "+dir.trim()+"20090707*.txt  "+dir.trim()+vDirT.trim()+"\\";
        p1 = r.exec(comando);
        //p1.waitFor();
        //p1.exitValue();
        System.out.println("comP1: "+comando+"\nValor1: "+"p1.waitFor()");
             
        //Se eliminan los Archivos
        comando = "cmd.exe /C del "+dir.trim()+"*.txt";
        p2 = r.exec(comando);
        p2.waitFor();
        p2.exitValue();
        System.out.println("comP2: "+comando+"\nValor2: "+p2.waitFor()+
                           "\nExit: "+p2.exitValue());          
        
        
        //Se copian los Archivos guardados a su Destino Original
        comando = "cmd.exe /C xcopy "+dir.trim()+vDirT.trim()+"\\"+"20090707*.txt "+
           dir.trim();
        p3 = r.exec(comando);
        p3.waitFor();
        p3.exitValue();
        System.out.println("comP3: "+comando+"\nValor3: "+p3.waitFor());          
        
        
        //Se Vacia los Archivos del Directorio Temporal
        comando = "cmd.exe /C del "+dir.trim()+vDirT+"\\"+"*.txt";
        p4 = r.exec(comando);
        p4.waitFor();
        p4.exitValue();
        System.out.println("comP4: "+comando+"\nValor4: "+p4.waitFor()+
                           "\nExit: "+p4.exitValue());          
        
            
        System.out.println("Elimina .txt ");        
        /*mkdir C:\mifarma\comprobantes  -
copy C:\mifarma\20090709*.txt comprobantes\ 
del C:\mifarma\*.txt
copy C:\mifarma\bk\20090709*.txt 
del C:\mifarma\bk\*.txt
                     */
    }
    
    /**
     * Elimina los Archivos de Texto con antiguedad mayor a 2 dias
     * @return
     * @author DUBILLUZ
     * @since 08.07.09
     * @throws Exception
     */
    public static void eliminaArchivoTxt() throws Exception {

        Runtime r = Runtime.getRuntime();
        Process p;
        //valores de BD
        //String dir = "C:\\mifarma\\"; 
        String dir = DBCaja.ObtieneDirectorio();
        //JMIRANDA 17.10.09 Directorio Log
        String dirLog = DBVentas.ObtieneDirectorioLog();
        System.out.println("dirLog: "+dirLog);
        //int ndias = 3;
        int ndias = Integer.parseInt(DBCaja.ObtieneNroDiasEliminar());
        //--
        String vDirT = "comprobantes";
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cFecha = Calendar.getInstance(); // today
        //String pFechAux = "";
        String pListaFecha = "";
        ArrayList pLista = new ArrayList();

        for (int i = ndias * -1; i < 0; i++) {
            cFecha = Calendar.getInstance();
            cFecha.add(Calendar.DATE, i + 1);
            //System.out.println("Fecha: " + sdf.format(cFecha.getTime()));
            pListaFecha = sdf.format(cFecha.getTime());
            pLista.add(pListaFecha);
        }
        System.out.println("pLista: " + pLista);
        // --------------
        //Crea el .bat elimina que eliminará los .txt
        File archivoFinal;
        FileWriter fos;
        BufferedWriter output;
        archivoFinal = new File(dir + "elimina.bat");
        fos = new FileWriter(archivoFinal);
        output = new BufferedWriter(fos);
        output.write("mkdir " + dir.trim() + vDirT.trim());

        for (int i = 0; i < pLista.size(); i++) {
            output.newLine();
            //output.write("xcopy " + dir.trim() + pLista.get(i) + "*.txt  " +
            //        dir.trim() + vDirT.trim() + "\\ " );
            output.write("xcopy " + dir.trim() + pLista.get(i) + "*.txt  " +
                    dir.trim() + vDirT.trim() + "\\ /Y" );            
        }
        output.newLine();
        //output.write("del " + dir.trim() + "*.txt");
        output.write("del /Q " + dir.trim() + "*.txt");
        for (int i = 0; i < pLista.size(); i++) {
            output.newLine();
            //output.write("xcopy " + dir.trim() + vDirT.trim() + "\\" + pLista.
            //        get(i) + "*.txt " + dir.trim());
            output.write("xcopy " + dir.trim() + vDirT.trim() + "\\" + pLista.
                    get(i) + "*.txt " + dir.trim()+"  /Y");
        }
        output.newLine();
        //output.write("del " + dir.trim() + vDirT + "\\" + "*.txt");
        output.write("del /Q " + dir.trim() + vDirT + "\\" + "*.txt");
        output.newLine();
        //output.write("rmdir " + dir.trim() + vDirT);
        output.write("rmdir /S /Q " + dir.trim() + vDirT);
        output.newLine();
        //output.write("del " + dir.trim() + "9*.jpg");
        output.write("del /Q " + dir.trim() + "9*.jpg");
        output.newLine();        
        //JMIRANDA 17.10.09 SE AÑADE LINEA PARA ELIMINAR LOG
        output.write("del /Q " + dirLog.trim()+"*.txt");
        output.newLine();                
        output.write("exit");
        output.newLine();
        output.close();

        fos.close();

        //fin


        //Se ejecuta Archivo .bat

        String comando = "cmd.exe /C start " + dir.trim() + "elimina.bat";

        p = r.exec(comando);

        System.out.println("ejecuta: " + comando);

        System.out.println("Elimina .txt ");

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
            //System.out.println("nD old:"+nD);
            
            long aux = 0L;
            long resto = 0L;
            
            int signo = 1;
            if(nD<0)
                signo = -1;
            
            //System.out.println("signo:"+signo);
            nD = nD * signo;
            //System.out.println("nD pw:"+nD);
                    aux  = (long) FarmaUtility.getDecimalNumber(
                                        FarmaUtility.formatNumber(
                                            Math.pow(10,nDec) * nD,
                                            nDec
                                            )
                                        );
                    
                    //System.out.println("1) aux:"+aux);
                
                    resto = aux%10;
                    //System.out.println("2) resto:"+resto);
                    aux = aux/10;
                    
                    //System.out.println("3) aux:"+aux);
                    
                    if(resto < 5)
                            resto = 0;
                    else
                            resto = 5;
                    
                    //System.out.println("3) aux:"+aux);
                    
                    nD = ((double)(aux*10+resto))/Math.pow(10,nDec);
                    
                    //System.out.println("4) nD:"+nD);
           nD = nD * signo;       
           //System.out.println("nD Nuevo:"+nD);
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
                       System.out.println("Tipo Impresora :" + VariablesPtoVenta.vTipoImpTermicaxIp);  
                     System.out.println("Buscando impresora :"+VariablesPtoVenta.vIndExisteImpresoraConsejo);
                     System.out.println("impresoras..encontradas...");
                     for (int i = 0; i < servicio.length; i++)
                     {
                       PrintService impresora = servicio[i];
                       String pNameImp = impresora.getName().toString().trim();
                       pNombreImpresora = retornaUltimaPalabra(pNameImp,"\\").trim();
                       //if (pNameImp.toUpperCase().indexOf(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase()) != -1)
                       //Buscara el nombre.
                       System.out.println(i+") pNameImp:"+pNameImp);
                       System.out.println(i+") pNombreImpresora:"+pNombreImpresora);
                       System.out.println("**************************************");
                       if (pNombreImpresora.trim().toUpperCase().equalsIgnoreCase(VariablesPtoVenta.vIndExisteImpresoraConsejo.toUpperCase()))
                       {
                         System.err.println("Encotró impresora térmica");
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
        //System.out.println(pCadena);
        //System.out.println(pSeparador);
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
      System.out.println("2. El codigo de barra "+cadena+" existe en local");
      }else{
      System.out.println("2. El codigo de barra "+cadena+" No existe en local");
      }
    }catch(SQLException e){
       e.printStackTrace();
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
                System.out.println("Error al obtener limitador del Mensaje. "+sql);                
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
        System.out.println("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE actualizaStkComprometidoProd");
        DBVentas.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk, 
                                              pTipoStkComprometido);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$ JUSTO ANTES DE ejecutaRespaldoStock");
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
        //sql.printStackTrace();
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
          System.out.println("valVtaProd: 1 "+ valVtaProd); 
        valVtaProd =  FarmaUtility.getDecimalNumber((FarmaUtility.formatNumber(valVtaProd,4)).trim());
          System.out.println("valVtaProd: 2 "+ valVtaProd); 
        valVtaProd = Math.ceil(valVtaProd);
          System.out.println("valVtaProd: 3 "+ valVtaProd); 
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
          System.out.println("VariablesVentas.vTotalPrecVtaProd: 1"+ VariablesVentas.vTotalPrecVtaProd); 
        VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd/auxCantIngr,3);
          System.out.println("VariablesVentas.vVal_Prec_Vta 2: "+ VariablesVentas.vVal_Prec_Vta);  
      }
      
      ArrayList myArray = new ArrayList();
      myArray.add(VariablesVentas.vCod_Prod); //0
      myArray.add(VariablesVentas.vDesc_Prod);
      myArray.add(VariablesVentas.vUnid_Vta);
      System.out.println("VariablesVentas.vVal_Prec_Vta 3: "+ VariablesVentas.vVal_Prec_Vta);
      myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3));
      myArray.add(VariablesVentas.vCant_Ingresada);
      myArray.add("");//myArray.add(VariablesVentas.vPorc_Dcto_1);se supone que este descuento ya no se aplica
      System.out.println("VariablesVentas.vVal_Prec_Lista: "+VariablesVentas.vVal_Prec_Lista);
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
      log.info("Producto agregado al pedidoVenta: "+myArray);
      
      FarmaUtility.operaListaProd(VariablesVentas.vArrayList_PedidoVenta, myArray, valor, 0);
      //System.out.println("size : " + VariablesVentas.vArrayList_PedidoVenta.size());
      //System.out.println("array : " + VariablesVentas.vArrayList_PedidoVenta);
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
    

}


