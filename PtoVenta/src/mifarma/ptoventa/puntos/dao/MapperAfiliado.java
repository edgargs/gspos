package mifarma.ptoventa.puntos.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperAfiliado {
    
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=afiliado} := " + "PTOVENTA.PTOVENTA_FIDELIZACION.GET_F_DATOS_CLIENTE(" +
            "   #{cDniCliente_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtenerClienteAfiliado(HashMap<String, Object> object);
}
