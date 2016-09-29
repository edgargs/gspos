package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.ArrayList;
import java.util.List;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAORACConvenioBTLMF extends DAOTransaccion {

    public void savePedidoCabRAC(RacVtaPedidoVtaCab racVtaPedidoVtaCab) throws Exception;

    public void savePedidoDetRAC(List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet) throws Exception;

    public void saveCompPagoRAC(List<RacVtaCompPago> lstVtaCompPago) throws Exception;

    public void saveFormaPagoPedidoRAC(List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido) throws Exception;

    public void saveConPedVtaRAC(List<RacConPedVta> lstConPedVta) throws Exception;

    public void deletePedidoCabRAC(String pNumPedVta) throws Exception;

    public String cobrarPedidoRAC(String pCodLocal, String pCodGrupoCia, String pNumPedVta,
                                  String indNotaCredito) throws Exception;
    
    public ArrayList<ArrayList<String>> listaBenefRemoto() throws Exception;
    
    public ArrayList<ArrayList<String>> obtieneBenefRemoto(String pCodCliConv) throws Exception;

    public double obtieneComsumoBenif() throws Exception;

    public String anularPedidoRac(String pIndNotaCred) throws Exception;

    public int isExisteComprobanteEnRAC(String pCodGrupoCia, String pCodLocal, String pTipoComprobante, String pNumComprobante,
                                         String pIndComprobanteElectronico) throws Exception;

    public String validacionPedidoRAC(String pCodLocal, String pNumPedVta, String pCodConvenio,
                                              String pCodCliente, String pTipoDoc, double pMonto,
                                              String pVtaFin) throws Exception;
}
