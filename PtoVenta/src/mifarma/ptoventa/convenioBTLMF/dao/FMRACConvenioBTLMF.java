package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaConstants;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ERIOS
 * @since 09.10.2015
 */
public class FMRACConvenioBTLMF implements DAORACConvenioBTLMF {
    
    private static final Logger log = LoggerFactory.getLogger(FMRACConvenioBTLMF.class);
    
    private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        
    @Override
    public void savePedidoCabRAC(RacVtaPedidoVtaCab racVtaPedidoVtaCab) {
    }

    @Override
    public void savePedidoDetRAC(List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet) {
    }

    @Override
    public void saveCompPagoRAC(List<RacVtaCompPago> lstVtaCompPago) {
    }

    @Override
    public void saveFormaPagoPedidoRAC(List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido) {
    }

    @Override
    public void saveConPedVtaRAC(List<RacConPedVta> lstConPedVta) {
    }

    @Override
    public void deletePedidoCabRAC(String pNumPedVta) {
    }

    @Override
    public String cobrarPedidoRAC(String pCodLocal, String pCodGrupoCia, String pNumPedVta, String indNotaCredito) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> listaBenefRemoto() {
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> obtieneBenefRemoto(String pCodCliConv) {
        return null;
    }

    @Override
    public double obtieneComsumoBenif() throws Exception{
        return DBConvenioBTLMF.obtieneComsumoBenif(FarmaConstants.INDICADOR_S);        
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
        UtilityConvenioBTLMF.liberarTransaccionRempota(null, null, FarmaConstants.INDICADOR_S);
    }

    @Override
    public void openConnection() {
    }

    @Override
    public String anularPedidoRac(String pIndNotaCred) throws Exception{
        String indConexionRac;
        try {
            indConexionRac = (facadeRecaudacion.validarConexionRAC()) ? "S" : "N";
        } catch (Exception e) {
            indConexionRac = "N";
        }
        if(indConexionRac.equals("S")){
            indConexionRac = DBConvenioBTLMF.anularPedidoRac(FarmaConstants.INDICADOR_N, FarmaConstants.INDICADOR_N);
        }
        return indConexionRac;
    }

    @Override
    public int isExisteComprobanteEnRAC(String pCodGrupoCia, String pCodLocal, String pTipoComprobante,
                                        String pNumComprobante, String pIndComprobanteElectronico) throws Exception{
        return DBConvenioBTLMF.isExisteComprobanteEnRAC(pCodGrupoCia,pCodLocal,pTipoComprobante, pNumComprobante, pIndComprobanteElectronico);
    }

    @Override
    public String validacionPedidoRAC(String pCodLocal, String pNumPedVta, String pCodConvenio, String pCodCliente,
                                      String pTipoDoc, double pMonto, String pVtaFin) throws Exception{
        String indConexionRac;
        try {
            indConexionRac = (facadeRecaudacion.validarConexionRAC()) ? "S" : "N";
        } catch (Exception e) {
            indConexionRac = "N";
        }
        if(indConexionRac.equals("S")){
            indConexionRac = DBConvenioBTLMF.validacionPedidoRAC(pCodLocal, pNumPedVta, pCodConvenio,
                                              pCodCliente, pTipoDoc, pMonto,
                                              pVtaFin);
        }
        return indConexionRac;
    }
}
