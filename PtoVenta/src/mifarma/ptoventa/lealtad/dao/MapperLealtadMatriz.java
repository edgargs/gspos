package mifarma.ptoventa.lealtad.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**
 * 
 * @author ERIOS
 * @since 10.02.2015
 */
public interface MapperLealtadMatriz {
    
    @Select(value =
            "{call PTOVENTA.FARMA_MATRIZ_LEALTAD.REGISTRA_INSCRIPCION_X1(" +
            "   #{vDniCli_in}," + "   #{vCodMatrizAcu_in}," +
            "   #{vIdUsu_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarInscripcionX1(Map<String, Object> mapParametros);
    
}
