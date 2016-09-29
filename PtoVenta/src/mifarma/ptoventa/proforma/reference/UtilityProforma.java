package mifarma.ptoventa.proforma.reference;

import java.awt.Frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.administracion.impresoras.DlgListaImpresoraTermica;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;

import mifarma.ptoventa.despacho.reference.UtilityDespacho;
import mifarma.ptoventa.matriz.mantenimientos.productos.DlgListado;

import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityProforma {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityProforma.class);
    
    public void imprimirConstanciaPagoProforma(JDialog pJDialog, String nroProforma){
        try{
            List lstConstancia = DBProforma.obtenerConstanciaPagoProforma(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, nroProforma);
            new UtilityImpCompElectronico().impresionTermica(lstConstancia, null);
        }catch(Exception ex){
            log.error("", ex);
            FarmaUtility.showMessage(pJDialog, "Proforma:\nError al imprimir constancia de pago de proforma\n"+ex.getMessage(), null);
        }
    }
    
    public static boolean isValidaVtaEmpresa(String nroProforma)throws Exception{
        FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
        List lista = DBProforma.getDatosValidaRac(nroProforma);
        boolean validado = true;
        for (int i = 0; i < lista.size(); i++) {
            Map datosAdicConv = (Map)lista.get(i);
            String pCodLocal = (String)datosAdicConv.get("COD_LOCAL");
            String pNumPedVta = (String)datosAdicConv.get("NUM_PED_VTA");
            String pCodConvenio = (String)datosAdicConv.get("COD_CONVENIO");
            String pCodCliente = (String)datosAdicConv.get("COD_CLIENTE");
            String pTipoDoc = (String)datosAdicConv.get("TIPO_DOC");
            double pMonto = FarmaUtility.getDecimalNumber(((String)datosAdicConv.get("MONTO")).trim());
            String pVtaFin = (String)datosAdicConv.get("VTAFIN");
            validado = validado && facadeConvenioBTLMF.validacionProformaRAC(pCodLocal, pNumPedVta, pCodConvenio,
                                                                             pCodCliente, pTipoDoc, pMonto, pVtaFin);
        }
        return validado;
    }
    
    public void solicitaTransferenciaADrogueria(JDialog pJDialog, String nroProforma)throws Exception{
        imprimirConstanciaClienteProformaGenerada(pJDialog, nroProforma);
        (new UtilityDespacho()).imprimirComandaTransferenciaDespacho(pJDialog, nroProforma);
    }
    
    private void imprimirConstanciaClienteProformaGenerada(JDialog pJDialog, String nroProforma){
        try{
            List lstConstancia = DBProforma.obtenerConstanciaProformaGenerada(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, nroProforma);
            new UtilityImpCompElectronico().impresionTermica(lstConstancia, null);
        }catch(Exception ex){
            log.error("", ex);
            FarmaUtility.showMessage(pJDialog, "Proforma:\nError al imprimir constancia de pago de proforma\n"+ex.getMessage(), null);
        }
    }
}
