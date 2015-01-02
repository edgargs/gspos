package mifarma.replicante;

import java.util.List;
import java.util.Map;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.replicante.reference.DBComprobanteElectronico;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityComprobanteE {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityComprobanteE.class);
    
    public static void main(String[] args){

        UtilityComprobanteE.procesarReenvioCompE("001", "001", "071",
                                                    "ptoventa", "mundial265", "10.11.1.204", "1521", "XE",
                                                 "01","B001","00000965");
    }

    public static String procesarReenvioCompE(String pCodGrupoCia, String pCodCia, String pCodLocal,
                                            String pUsuarioBD, String pClaveBD, String pIpBD, String pPuertoBD, String pSidBD,
                                            String pTipoComp, String pSerieComp, String pNumComp){
        
        FarmaVariables.vCodGrupoCia = pCodGrupoCia;
        FarmaVariables.vCodCia = pCodCia;
        FarmaVariables.vCodLocal = pCodLocal;
        
        FarmaVariables.vUsuarioBD = pUsuarioBD;
        FarmaVariables.vClaveBD = pClaveBD;
        FarmaVariables.vIPBD = pIpBD;
        FarmaVariables.vPUERTO = pPuertoBD;
        FarmaVariables.vSID = pSidBD;
        
        String strMensajeProceso = "";
        try{
        if (!UtilityEposTrx.isValidoParamCnxEpos()) {
            strMensajeProceso = "ERROR: Parametros no validos.";
            return strMensajeProceso;
        }
        }catch(Exception ex){
            log.error(ex.getMessage());
            return strMensajeProceso;
        }
        
        //ArrayList<ArrayList<String>> comprobantes = new ArrayList<>();
        //ArrayList comprobantes = new ArrayList();
        log.info("Recupera comprobantes");
        
        try {
            List comprobantes = DBComprobanteElectronico.obtieneReenvioComprobantes(pCodGrupoCia, pCodCia, pCodLocal, pTipoComp, pSerieComp, pNumComp);
        
            for(int i=0;i<comprobantes.size();i++){
                Map comprobante = (Map)comprobantes.get(i);
                    
                String vTipComp = comprobante.get("vTipComp".toUpperCase()).toString();
                String vNumPed = comprobante.get("vNumPed".toUpperCase()).toString();
                String vSecComp = comprobante.get("vSecComp".toUpperCase()).toString();
                String vTipCli = comprobante.get("vTipCli".toUpperCase()).toString().trim();
                
                String vTipCompPed = comprobante.get("vTipCompPed".toUpperCase()).toString();
                
                String vRucCia = comprobante.get("vRucCia".toUpperCase()).toString();
                String vNumCompEAux = comprobante.get("vNumCompEAux".toUpperCase()).toString();
                
                FarmaVariables.vNuRucCia = vRucCia;
                if (!UtilityEposTrx.isConexionEPOS()) {
                   strMensajeProceso = "ERROR: Conexion EPOS no disponible.";
                   return strMensajeProceso;
               }
                
                String rst = UtilityComprobanteE.reenvioComprobanteE(pCodGrupoCia, pCodLocal,
                                                    vTipComp, vNumPed, vSecComp, vTipCli,
                                                    vTipCompPed,
                                                    vRucCia,
                                                     pUsuarioBD, pClaveBD, pIpBD, pPuertoBD, pSidBD,
                                                     "REENVIOCOMP",vNumCompEAux);
                log.info(rst);
            }
            strMensajeProceso = "OK";
        }catch(Exception e){
            FarmaUtility.liberarTransaccion();            
            
            log.error("",e);
            strMensajeProceso = e.getMessage();            
        }
        return strMensajeProceso;
    }    
    
    public static String reenvioComprobanteE(String pCodGrupoCia, String pCodLocal,
                                            String tipoCompPago, String numPedidoVta, String SecCompPago, String tipClientConvenio,
                                            String pTipCompPed,
                                             String pRucCia,
                                             String pUsuarioBD, String pClaveBD, String pIpBD, String pPuertoBD, String pSidBD,
                                             String pIdUsu, String pNumCompAux) throws Exception{
        //Carga de variables
        FarmaVariables.vCodGrupoCia = pCodGrupoCia;
        FarmaVariables.vCodLocal = pCodLocal;
        
        VariablesVentas.vTip_Comp_Ped = pTipCompPed;
        
        FarmaVariables.vUsuarioBD = pUsuarioBD;
        FarmaVariables.vClaveBD = pClaveBD;
        FarmaVariables.vIPBD = pIpBD;
        FarmaVariables.vPUERTO = pPuertoBD;
        FarmaVariables.vSID = pSidBD;
        FarmaVariables.vIdUsu = pIdUsu;
        
        VariablesCaja.vNumPedVta = numPedidoVta;
        log.info("Reenvio :"+numPedidoVta);
        
                
        String[] listaRespuesta = UtilityEposTrx.getNumCompPagoE(tipoCompPago,numPedidoVta,SecCompPago, tipClientConvenio, false, false, false);
        String numero = listaRespuesta[2].toString();
        if(pNumCompAux.equals(numero)){
            
            UtilityEposTrx.enviaConfirmacion(numPedidoVta,SecCompPago,tipoCompPago,numero,listaRespuesta);
            
            //UtilityCaja.actualizaComprobanteImpreso(SecCompPago,tipoCompPago,SecCompPago);
            
            //UtilityCaja.actualizaEstadoPedido(numPedidoVta, ConstantsCaja.ESTADO_COBRADO);
                
            FarmaUtility.aceptarTransaccion();
        }else{
            throw new Exception("Error al reenviar comprobante electronico: Original="+pNumCompAux+" Nuevo="+numero);
        }
        return "Exito al reenviar: "+numero;
        
    }
}
