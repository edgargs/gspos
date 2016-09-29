package mifarma.ptoventa.convenioBTLMF.dao;

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


public interface MapperRACConvenioBTLMF {

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA.PTOVENTA_MATRIZ_CONV_BTLMF.BTLMF_F_CUR_LISTA_BENIFICIARIO(" +
            "#{CCODGRUPOCIA_IN}," + "#{CCODLOCAL_IN}," + "#{CSECUSULOCAL_IN}," + "#{VCODCONVENIO_IN}," +
            "#{VBENIFICIARIO_IN}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listaBenefRemoto(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "insertPedidoCabRAC")
    void savePedidoCabRAC(RacVtaPedidoVtaCab racVtaPedidoVtaCab);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "insertPedidoDetRAC")
    public void savePedidoDetRAC(RacVtaPedidoVtaDet racVtaPedidoVtaDet);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "insertCompPagoRAC")
    public void saveCompPagoRAC(RacVtaCompPago racVtaCompPago);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "insertFormaPagoPedidoRAC")
    public void saveFormaPagoPedidoRAC(RacVtaFormaPagoPedido racVtaFormaPagoPedido);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "insertConPedVtaRAC")
    public void saveConPedVtaRAC(RacConPedVta racConPedVta);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "deletePedidoCabRAC")
    public void deletePedidoCabRAC(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "deletePedidoDetRAC")
    public void deletePedidoDetRAC(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "deletePedidoCompPagoRAC")
    public void deleteCompPagoRAC(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "deletePedidoFormaPagoRAC")
    public void deleteFormaPagoPedidoRAC(Map<String, Object> mapParametros);

    @SelectProvider(type = SQLConvenioBTLMFProvider.class, method = "deletePedidoDatosConvRAC")
    public void deleteDatosConvPedVtaRAC(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{respuesta, mode=OUT, jdbcType=CHAR} := " + "BTLPROD.PKG_DOCUMENTO_MF.SP_GRABA_DOCUMENTOS(" +
            "#{pCodGrupo_Cia_in}," + "#{pCod_local_in}," + "#{pNum_Ped_Vta_in}," + "#{pIndNotaCred}," +
            "#{pIndGrabaPed}," + "#{pIndCreditoSeparado}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void cobraPedidoRAC(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA.PTOVENTA_MATRIZ_CONV_BTLMF.BTLMF_F_CUR_OBT_BENIFICIARIO(" +
            "#{CCODGRUPOCIA_IN}," + "#{CCODLOCAL_IN}," + "#{CSECUSULOCAL_IN}," + "#{VCODCONVENIO_IN}," +
            "#{VCODBENIF_IN}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtieneBenefRemoto(Map<String, Object> mapParametros);

}
