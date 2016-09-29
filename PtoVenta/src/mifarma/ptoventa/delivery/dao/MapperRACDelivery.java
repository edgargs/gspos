package mifarma.ptoventa.delivery.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface MapperRACDelivery {

    @Select(value =
            "{call  " + "DELIVERY.PKG_DAEMON_UPD_PROF_FV.P_ACT_ENVIO_PROF(" + "#{a_cod_cia}," + "#{a_cod_local}," +
            "#{a_num_proforma}," + "#{a_cod_local_sap}," + "#{a_Comprobantes}," + "#{a_ACT_ENVIO_PROF}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizaProforma(Map<String, Object> mapParametros);
}
