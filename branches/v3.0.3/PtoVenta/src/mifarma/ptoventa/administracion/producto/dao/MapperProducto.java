package mifarma.ptoventa.administracion.producto.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MapperProducto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CVILCA      01.10.2013   Creación<br>
 * <br>
 * @author Christian Vilca Quiros<br>
 * @version 1.0<br>
 *
 */
public interface MapperProducto {


    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_GRAL.F_GET_PRODUCTOS(" +
            "#{cCodGrupoCia}," + "#{cCodLocal}," + "#{vDescripcion}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerProductosPorDescripcion(Map mapParametros);

    @Select(value =
            "{call #{epl, mode=OUT, jdbcType=CHAR} := " + "FARMA_GRAL.F_VAR_GET_EPL_STICKER(" + "#{cCodGrupoCia}," +
            "#{cCodLocal}," + "#{cCodProd}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerCodigoEPLPorProducto(Map mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_GRAL.F_GET_PRODUC_COD_BARRA(" +
            "#{cCodGrupoCia}," + "#{cCodLocal}," + "#{cCodBarra}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerProductoCodigobarra(Map mapParametros);
}
