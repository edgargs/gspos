package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.List;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAORACConvenioBTLMF extends DAOTransaccion{
    
    public void savePedidoCabRAC(RacVtaPedidoVtaCab racVtaPedidoVtaCab) throws Exception;

    public void savePedidoDetRAC(List<RacVtaPedidoVtaDet> lstVtaPedidoVtaDet) throws Exception;
    
    public void saveCompPagoRAC(List<RacVtaCompPago> lstVtaCompPago) throws Exception;
    
    public void saveFormaPagoPedidoRAC(List<RacVtaFormaPagoPedido> lstVtaFormaPagoPedido) throws Exception;
    
    public void saveConPedVtaRAC(List<RacConPedVta> lstConPedVta) throws Exception;
}
