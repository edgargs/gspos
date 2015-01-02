package mifarma.ptoventa.psicotropicos.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MapperPsicotropicos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LLEIVA      23.Sep.2013   Creación<br>
 * <br>
 * @author Luis Leiva Bazán<br>
 * @version 1.0<br>
 *
 */
public interface MapperPsicotropicos {
    /**
     * Retorna listado de reporte de psicotropicos entre pFecInicial y pFecFinal
     * @author LLEIVA
     * @since 23.Sep.2013
     * @param mapParametros
     */
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_PSICOTROPICOS.REPORTE_PSICOTROPICOS(" +
            "#{pCodGruCia_in}," + "#{pCodCia_in}," + "#{pCodLocal_in}," + "#{pFecInicial_in}," + "#{pFecFinal_in}," +
            "#{pCodProd_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerListadoPsicotropicos(Map<String, Object> mapParametros);

    /**
     * Retorna listado de reporte de psicotropicos entre pFecInicial y pFecFinal
     * @author LLEIVA
     * @since 23.Sep.2013
     * @param mapParametros
     */
    @Select(value =
            "{ call #{vIndicador, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_PSICOTROPICOS.VERIFICA_PROD_PSICOTROPICO(" +
            "#{pCodProd_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void verificaProdPsicotropicos(Map<String, Object> mapParametros);
}
