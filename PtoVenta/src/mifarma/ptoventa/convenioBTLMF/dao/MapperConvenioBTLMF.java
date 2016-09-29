package mifarma.ptoventa.convenioBTLMF.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.ptoventa.convenioBTLMF.domain.RacConPedVta;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaCompPago;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaFormaPagoPedido;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaCab;
import mifarma.ptoventa.convenioBTLMF.domain.RacVtaPedidoVtaDet;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.StatementType;


public interface MapperConvenioBTLMF {

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectPedidoCabLocal")
    RacVtaPedidoVtaCab getPedidoCabLocal(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectPedidoDetLocalTemp")
    List<RacVtaPedidoVtaDet> getPedidoDetLocalTemp(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectCompPagoLocalTemp")
    List<RacVtaCompPago> getCompPagoLocalTemp(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectFormaPagoPedidoLocalTemp")
    List<RacVtaFormaPagoPedido> getFormaPagoPedidoLocalTemp(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectConPedVtaLocal")
    public List<RacConPedVta> getConPedVtaLocal(Map<String, Object> object);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectPedidoDetLocal")
    List<RacVtaPedidoVtaDet> getPedidoDetLocal(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectCompPagoLocal")
    List<RacVtaCompPago> getCompPagoLocal(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "selectFormaPagoPedidoLocal")
    List<RacVtaFormaPagoPedido> getFormaPagoPedidoLocal(Map<String, Object> mapParametros);

    @Select(value =
            "{call  " + "PTOVENTA.PTOVENTA_CONV_BTLMF.BTLMF_P_ACT_FECHA_PROC_RAC(" + "#{CodGrupoCia_in}," + "#{cCodLocal_in}," +
            "#{cNroPedido}" + ")}")
    public void actualizaPedidoLocal(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=impresion} := " + "PTOVENTA.PTOVENTA_CONV_BTLMF.F_IMPR_VOUCHER(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cNroPedido_in}," + "#{cIndCopiaGuia_in}," + "#{cSecCompPago_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getVoucherCopiaGuia(HashMap<String, Object> mapParametros);
    
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=impresion} := " + "PTOVENTA.PTOVENTA_CONV_BTLMF.F_IMPR_VOUCHER(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cNroPedido_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getVoucher(HashMap<String, Object> mapParametros);
}
