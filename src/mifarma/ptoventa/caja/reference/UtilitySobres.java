package mifarma.ptoventa.caja.reference;

import javax.swing.JTable;

import mifarma.common.FarmaUtility.*;
import java.sql.SQLException;

import mifarma.ptoventa.caja.DlgControlSobres;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Frame;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgIngresoSobre;
import mifarma.ptoventa.caja.DlgIngresoSobreParcial;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.caja.reference.ConstantsSobres;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.reference.VariablesPtoVenta;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilitySobres.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ  07.06.2010   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */
public class UtilitySobres {

    private static final Log log = LogFactory.getLog(UtilitySobres.class);
    
    private static int numeroCorrel = 1;
    /**
     * Constructor
     */
    
    public UtilitySobres() {
    }

    /**
     * Metodo que realiza la accion del sobre
     * @author dubilluz
     * @since  07.06.2010
     * @param pTipoAccion
     * @param pSec
     * @param pCodigoSobre
     * @param pSecMovCaja
     * @param pCodFormaPago
     * @param pTipMoneda
     * @param pMontEntrega
     * @param pMontEntregaTot
     * @param jfoco
     */
    public static void accionSobre(String pTipoAccion,
                                   String pSec,
                                   String pCodigoSobre,
                                   String pSecMovCaja,
                                   JTable jfoco,
                                   DlgControlSobres pDialogo,
                                   Frame parent
                                  ){
        
      String pEstadoSobre = "X";
      String pResultadoAccion = "X";
      String pMensaje = "";
      boolean pDefinido = true;
      boolean pNuevo = false;
      boolean pRealizaAccion = false;
      if(pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_INGRESO)){
         pMensaje = "agregar";
         pNuevo=true; 
      }
      else{
          if(pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_MODIFICA)){
              pMensaje = "modificar";
          }
          else{
              if(pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_ELIMINA)){
                  pMensaje = "eliminar";
              }
              else{
                  if(pTipoAccion.toUpperCase().trim().equalsIgnoreCase(ConstantsSobres.ACC_APRUEBA)){
                      pMensaje = "aprobar";
                  }
                  else{
                      pMensaje = "Acción no definida verifique.";
                      pDefinido = false;
                  }
              }
          }
      }
       
       if (pDefinido) {
           
             if(pNuevo){
                 if(getSecMovCaja()){
                    pRealizaAccion = mostrarIngresoSobresParciales(parent);
                 }
                 else{
                     pRealizaAccion = false;
                     FarmaUtility.showMessage(pDialogo,"No puede agregar sobres por no tener caja abierta.",jfoco);  
                 }
             }
             else{
                 if(FarmaUtility.rptaConfirmDialog(pDialogo,"¿Esta seguro de "+pMensaje+" sobre?")){
                     VariablesSobre.vSecMovCaja = pSecMovCaja.trim();
                     pRealizaAccion = true;
                 }
                 else
                    pRealizaAccion = false;
             }
             
             // inicio de realizar la accion seleccionada    
               if (pRealizaAccion) {
                   try {
                       
                       if(!pNuevo){
                           pEstadoSobre = 
                                   DBCaja.getEstadoBloqueo(ConstantsSobres.TIPO_TEMPORAL, pSec, pCodigoSobre, 
                                                           pSecMovCaja);
                       }
                       
                       System.err.println("VariablesSobre.vSecMovCaja"+":"+VariablesSobre.vSecMovCaja);
                       System.err.println("VariablesSobre.vCodFormaPagoTmp"+":"+VariablesSobre.vCodFormaPagoTmp);
                       System.err.println("VariablesSobre.vCodTipoMon"+":"+VariablesSobre.vCodTipoMon);
                       System.err.println("VariablesSobre.pMontEntrega"+":"+VariablesSobre.vValMontoPagadoTmp);
                       System.err.println("VariablesSobre.pMontEntregaTot"+":"+VariablesSobre.vValTotalPagadoTmp);
                       
                       pResultadoAccion = DBCaja.getRealizaAccionSobreTMP_02(pTipoAccion,
                                                                              pSec,
                                                                              pCodigoSobre,
                                                                              VariablesSobre.vSecMovCaja,
                                                                              VariablesSobre.vCodFormaPagoTmp,
                                                                              VariablesSobre.vCodTipoMon,
                                                                              VariablesSobre.vValMontoPagadoTmp,
                                                                              VariablesSobre.vValTotalPagadoTmp,
                                                                              VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE
                                                                             );
                           
                       FarmaUtility.aceptarTransaccion();
                       FarmaUtility.showMessage(pDialogo,"Se pudo "+pMensaje+" sobre con éxito",jfoco); 
                       /*INICION DE IMPRESION DE SOBRES EN VOUCHER ASOSA, 26.07.2010*/
                       imprimeSobresDeclarados(null,VariablesSobre.vSecMovCaja,pResultadoAccion); 
                       /*FIN DE IMPRESION DE SOBE4S EN VOUCHER*/
                       
                   } catch(SQLException e) {
                       FarmaUtility.liberarTransaccion();
                       e.printStackTrace();
                       if(e.getErrorCode()>20000)
                       {
                         FarmaUtility.showMessage(pDialogo,e.getMessage().substring(10,e.getMessage().indexOf("ORA-06512")),jfoco);  
                       }else
                       {
                         FarmaUtility.showMessage(pDialogo,"Ocurrió un error al "+pMensaje+" sobre.\n"+e,jfoco);
                       }             
             
                   }
                   catch(Exception q) {
                       FarmaUtility.liberarTransaccion();
                       q.printStackTrace();
                       FarmaUtility.showMessage(pDialogo,"Ocurrió un error al "+pMensaje+" sobre.\n"+q,jfoco);
                   }
                   finally {
                       log.debug("Fin de Metodo UtilitySobres.accionSobre");
                   }
               }
              // fin de realizar la accion seleccionada    
           
       }
       else {
           FarmaUtility.showMessage(pDialogo,pMensaje,jfoco);
       }
    }
    

    private static boolean mostrarIngresoSobresParciales(Frame parent){
        DlgIngresoSobreParcial dlgsobreparciales = new DlgIngresoSobreParcial(parent,"",true);
        dlgsobreparciales.setVisible(true);
        return FarmaVariables.vAceptar;
    }
    
    private static boolean getSecMovCaja(){

        try {
            VariablesSobre.vSecMovCaja = DBCaja.getSecMovCaja();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public static double getTipodeCambio(){
        double ptipoCambio=0.0;
        try {
            ptipoCambio = DBCaja.getTipoCambioDU();
        } catch (SQLException e) {
            ptipoCambio=0.0;
        }        
        
        System.out.println("ptipoCambio:"+ptipoCambio);
        return ptipoCambio;
    }
    
    /**
     * @author ASOSA
     * @since 26.07.2010
     */
    public static void imprimeSobresDeclarados(JDialog pDialogo,String pSecMovCaja, String pSecSobre)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      String pIndProsegur = FarmaConstants.INDICADOR_N;
      boolean indImp = false;
      //if(servicio != null)
      //{
        try
        {
            //SEG_F_CHAR_IND_PROSEGUR
            pIndProsegur = FarmaConstants.INDICADOR_S;
            if(pIndProsegur.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            {
                String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                 String pCodSobre = "";      
                      String vIndImpre = FarmaConstants.INDICADOR_S;
                      System.out.println("vIndImpre :"+vIndImpre);
                      if (!vIndImpre.equals("N")) {
                         ArrayList pLista =  new ArrayList();
                         DBCajaElectronica.getSobreDeclarados_02(pSecMovCaja,pLista,pSecSobre);
                         for(int f=0;f<pLista.size();f++){
                             //pCodSobre = FarmaUtility.getValueFieldArrayList(pLista,f,0);
                             String html = DBCajaElectronica.getHtmlSobreDeclarados_02(pSecMovCaja,pSecSobre);                             
                             PrintConsejo.imprimirHtml(html.trim(),VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                             indImp = true;
                         }
                         if(indImp)
                            FarmaUtility.showMessage(pDialogo,
                                                     "Recoger Voucher de sobres declarados.",
                                                     null);
                         //break;
                     }
                  //}
                //}              
            }

        }
        catch (SQLException sqlException)
        {          
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al obtener los datos de los sobres a imprimir.", null);
        }
        
      //}
     }
    
    /**
     * Reimprime un voucher en control de sobres
     * @author ASOSA
     * @since 04.08.2010
     * @param pSecMovCaja
     * @param pSecSobre
     */
    public static void reimprimirSobre(JDialog pDialogo,String pSecMovCaja, String pSecSobre)
    {
      boolean indImp = false;
        try
        {
            String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos(); 
            String vIndImpre = FarmaConstants.INDICADOR_S;
            if (!vIndExisteImpresora.trim().equalsIgnoreCase("")) {
                String html = DBCajaElectronica.getHtmlSobreDeclarados_03(pSecMovCaja,pSecSobre);
                PrintConsejo.imprimirHtml(html.trim(),VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                indImp = true;
                if(indImp)
                    FarmaUtility.showMessage(pDialogo,
                                            "Recoger Voucher de sobres declarados.",
                                             null);
                }
        }
        catch (SQLException sqlException)
        {          
            FarmaUtility.showMessage(pDialogo, 
                                    "Error al obtener los datos de los sobres a imprimir.", 
                                     null);
        }
     }
    
    /**
     * Reimprime un voucher en forma de pago entrega
     * @author ASOSA
     * @since 09.08.2010
     * @param pSecMovCaja
     * @param pSecSobre
     */
    public static void reimprimirSobre_02(JDialog pDialogo,String pSecMovCaja, String pSecSobre)
    {
      boolean indImp = false;
        try
        {
            String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos(); 
            String vIndImpre = FarmaConstants.INDICADOR_S;
            if (!vIndExisteImpresora.trim().equalsIgnoreCase("")) {
                String html = DBCajaElectronica.getHtmlSobreDeclarados_04(pSecMovCaja,pSecSobre);
                PrintConsejo.imprimirHtml(html.trim(),VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                indImp = true;
                if(indImp)
                    FarmaUtility.showMessage(pDialogo,
                                            "Recoger Voucher de sobres declarados.",
                                             null);
                }
        }
        catch (SQLException sqlException)
        {          
            FarmaUtility.showMessage(pDialogo, 
                                    "Error al obtener los datos de los sobres a imprimir.", 
                                     null);
        }
     }

}
