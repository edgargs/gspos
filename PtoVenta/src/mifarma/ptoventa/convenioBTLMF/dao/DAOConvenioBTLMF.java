package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.ArrayList;

import java.util.List;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;
import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAOConvenioBTLMF extends DAOTransaccion{
    
    public ArrayList<ArrayList<String>> listaBenefRemoto();
    
    public RacVtaPedidoVtaCab getPedidoCabLocal(String pNumPedVta, String pIndicadorNC) throws Exception;

    public List<RacVtaPedidoVtaDet> getPedidoDetLocal(String pNumPedVta, String pIndicadorNC) throws Exception;
    
    public List<RacVtaCompPago> getCompPagoLocal(String pNumPedVta, String pIndicadorNC) throws Exception;
    
    public List<RacVtaFormaPagoPedido> getFormaPagoPedidoLocal(String pNumPedVta, String pIndicadorNC) throws Exception;

    public List<RacConPedVta> getConPedVtaLocal(String pNumPedVta, String pIndicadorNC) throws Exception;
}
