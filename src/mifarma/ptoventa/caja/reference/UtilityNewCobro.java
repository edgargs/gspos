package mifarma.ptoventa.caja.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityNewCobro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      01.03.2010   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class UtilityNewCobro {
    
    private static final Log log = LogFactory.getLog(UtilityNewCobro.class);
    
    public UtilityNewCobro() {
    }
    
    /**
     * Obtiene el saldo disponible de credito del cliente
     */
    public static String obtenerCreditoDisponible(String codCliente,String codConvenio,JDialog obj){
       String resultado = "";
       try
       {
           resultado = DBCaja.getSaldoCredClienteMatriz(codCliente,codConvenio, FarmaConstants.INDICADOR_S);
           FarmaUtility.aceptarTransaccion();
           log.debug("credito del cliente : "+resultado);
       }catch(SQLException ex)
       {
           FarmaUtility.liberarTransaccion();
           log.error(null,ex);
           FarmaUtility.showMessage(obj,"Error al Obtener credito disponible del Cliente Actual.\n" + ex.getMessage(), null);
       }    
       return resultado;
    }
    
    public static void reemplazarObjeto(String codfp){
        for(int c=0;c<VariablesNewCobro.listDeta.size();c++){
            BeanDetaPago objk=(BeanDetaPago)VariablesNewCobro.listDeta.get(c);
            if(objk.getCod_fp().equalsIgnoreCase(codfp)){
                VariablesNewCobro.listDeta.remove(c);
                break;
            }
        }
    }
    
    public static boolean buscaTarjeta(){
        boolean flag=false;
        for(int c=0;c<VariablesNewCobro.listDeta.size();c++){
            BeanDetaPago objk=(BeanDetaPago)VariablesNewCobro.listDeta.get(c);
            if(!objk.getCod_fp().equalsIgnoreCase("00001") && !objk.getCod_fp().equalsIgnoreCase("00002")){
                flag=true;
            }
        }
        return flag;
    }
    
    public static void inicializarVariables(){
        VariablesNewCobro.numpeddiario="";
        VariablesNewCobro.numpedvta="";
        VariablesNewCobro.indPedidoEncontrado="";
        VariablesNewCobro.montoTotal=0;
        VariablesNewCobro.montoDolar=0;
        VariablesNewCobro.tipoCambio="";
        VariablesNewCobro.codtipoCompPed="";
        VariablesNewCobro.desctipoCompPed="";
        VariablesNewCobro.indDistribGratel="";
        VariablesNewCobro.cantItemsPed="";
        VariablesNewCobro.indPedConv="";
        VariablesNewCobro.codconv="";
        VariablesNewCobro.codcli="";
        VariablesNewCobro.nomcli="";
        VariablesNewCobro.ruccli="";
        VariablesNewCobro.dircli="";
        VariablesNewCobro.tipoPed="";
        VariablesNewCobro.montoIngreso=0;
        VariablesNewCobro.flagPedCubierto=false;
        VariablesNewCobro.flagPedVirtual=false;
        VariablesNewCobro.nroTelf="";
        VariablesNewCobro.listDeta.clear();
        VariablesNewCobro.vlblMsjPedVirtual="";
        VariablesNewCobro.indPermitCampana="N";
        
        VariablesNewCobro.saldo=0;
        
        VariablesNewCobro.indCredito="N";
        
        VariablesNewCobro.montoSoles=0;
        VariablesNewCobro.montoDolares=0;
        VariablesNewCobro.montoTarj=0;
        
        VariablesNewCobro.codfpHOLA="";
    }
    
    /**
     * Metodo encargado de validacion del documento de identificacion ( DNI, CARNE DE EXTRANJERIA)     * 
     */
    public static boolean validarDocIndentificacion( String docIden ) { 
        boolean flag = false;       
        String paramDocVal = VariablesNewCobro.docsValidos;
        if(paramDocVal != null ){
            String valores[] = paramDocVal.split(",");
            for( int i =0; i<valores.length ; i++ ){
                if( Integer.parseInt( valores[i].trim() ) == docIden.length() ){
                    log.info("ok");
                    flag = true;
                    break;
                }
            }
        }
       
     return flag;
    }
    
    public static void igualarVariables(){
        VariablesCaja.vIndPedidoSeleccionado=VariablesNewCobro.indPedidoEncontrado;
        VariablesCaja.vIndTotalPedidoCubierto=VariablesNewCobro.flagPedCubierto;
        VariablesCaja.vNumPedVta=VariablesNewCobro.numpedvta;
        VariablesCaja.vValTipoCambioPedido=VariablesNewCobro.tipoCambio;
        VariablesCaja.vValTotalPagar=String.valueOf(VariablesNewCobro.montoTotal);
        VariablesCaja.vValTipoCambioPedido=VariablesNewCobro.tipoCambio;
        VariablesVentas.vTip_Comp_Ped=VariablesNewCobro.codtipoCompPed;
        VariablesVentas.vNom_Cli_Ped = VariablesNewCobro.nomcli;
        VariablesVentas.vRuc_Cli_Ped = VariablesNewCobro.ruccli;
        VariablesVentas.vDir_Cli_Ped = VariablesNewCobro.dircli;
        VariablesVentas.vTipoPedido =  VariablesNewCobro.tipoPed;
        VariablesCaja.vIndDistrGratuita = VariablesNewCobro.indDistribGratel;
        //VariablesCaja.vIndDeliveryAutomatico = ;
        VariablesVentas.vCant_Items_Ped = VariablesNewCobro.cantItemsPed;
        VariablesCaja.vIndPedidoConvenio = VariablesNewCobro.indPedConv;
        VariablesConvenio.vCodConvenio = VariablesNewCobro.codconv;
        VariablesConvenio.vCodCliente = VariablesNewCobro.codcli;
        VariablesCaja.vSaldoPedido=String.valueOf(VariablesNewCobro.saldo);
        VariablesCaja.vValVueltoPedido=VariablesNewCobro.vuelto;
        VariablesCaja.vPermiteCampaña=VariablesNewCobro.indPermitCampana;
        
        VariablesCaja.usoConvenioCredito=VariablesNewCobro.indCredito;
        VariablesCaja.vIndPedidoConProdVirtual=VariablesNewCobro.flagPedVirtual;
    }
    
}
