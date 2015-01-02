package mifarma.ptoventa.encuesta.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface MapperEncuesta {

    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=encuesta} := " + "PTOVENTA_ENCUESTAS.VERIFICA_ENCUESTA(" +
            "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," + "   #{cCodProd_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void verificaEncuesta(HashMap<String, Object> object);

    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=preguntaEncuesta} := " + "PTOVENTA_ENCUESTAS.OBTENER_PREGUNTAS(" +
            "   #{nCodEncuesta_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtenerPreguntas(HashMap<String, Object> object);

    @Select(value =
            "{call PTOVENTA_ENCUESTAS.GRABA_DETALLE_ENCUESTA(" + "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," +
            "   #{cCodLocal_in}," + "   #{cSecuencial}," + "   #{cCodProd_in}," + "   #{cCodEncuesta_in}," +
            "   #{cSecPregunta_in}," + "   #{cRespuesta}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void grabaDetalleEncuesta(HashMap<String, Object> object);

    @Select(value =
            "{call #{vSecuencial, mode=OUT, jdbcType=NUMERIC} := " + "PTOVENTA_ENCUESTAS.GRABA_CABECERA_ENCUESTA(" +
            "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," + "   #{cUsuId_in}," +
            "   #{cCodProd_in}," + "   #{cCodEncuesta_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarCabeceraEncuesta(Map<String, Object> mapParametros);
}
