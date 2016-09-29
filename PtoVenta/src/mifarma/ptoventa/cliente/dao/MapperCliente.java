package mifarma.ptoventa.cliente.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperCliente {
    
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=clienteJuridico} := " + 
            "PTOVENTA.PTOVENTA_FIDELIZACION.F_CUR_BUSCA_CLIENTE(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cValorBusqueda_in}," + 
            "#{cTipoBusqueda_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void busquedaClientes(Map<String, Object> mapParametros);
    
    @Select(value =
            "{call " + 
            "PTOVENTA.PTOVENTA_FIDELIZACION.P_MANTENIMIENTO_CLIENTE(" +
            "#{pCodLocal_in}," + 
            "#{pTipoDocumento_in}," + 
            "#{pNumDocumento_in}," + 
            "#{pRazonSocial_in}," + 
            "#{pDireccion_in}," + 
            "#{pIsAgenteRetencion_in}," + 
            "#{pIsAgentePercepcion_in}," + 
            "#{pUser_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void registrarCliente(Map<String, Object> mapParametros);
}
