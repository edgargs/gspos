package mifarma.ptoventa.stocknegativo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface MapperStockNegativo {
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_STOCK_NEG.LISTADO_SOL_STOCK_NEGATIVO(" +
            "#{cnumsolic_in}," + "#{cestado_in}," + "#{csolicitante_in}," + "#{caprobador_in}," + "#{cfechaini_in}," +
            "#{cfechafin_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListarSolicitud(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_STOCK_NEG.LISTADO_DET_STOCK_NEGATIVO(" +
            "#{cnumsolic_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listarDetSolicit(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{resultado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_STOCK_NEG.INV_REGULARIZAR_AJUSTE_KARDEX(" +
            "#{cCodGrupoCia_in}," + "#{cCodLocal_in}," + "#{cCodProd_in}," + "#{cCodProdAnul_in}," +
            "#{cNeoCant_in}," + "#{cCodSol_in}," + "#{cUsu_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void regularizar(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_STOCK_NEG.LISTADO_VER_KARDEX(" +
            "#{cCodProd}," + "#{cCodLocal}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listarKardex(Map<String, Object> mapParametros);
}
