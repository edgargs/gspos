package mifarma.ptoventa.reportes.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperReporte {
    
    @Select(value =
            "{call #{listado, mode = OUT, jdbcType = CURSOR, resultMap = periodoReporGigante} := " + 
            "PTOVENTA.PTOVENTA_REPORTE.F_CUR_GET_PERIODO_REP_GIGANTE(" +
            "#{cCodGrupoCia_in}," + "#{cCodLocal_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtenerPeriodoReporteGigante(HashMap<String, Object> object);
    
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA.PTOVENTA_REPORTE.F_CHAR_GET_MSJ_GIGANTE("+
            "#{ctipomensaje_in}"+")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtenerInfoComisionGigante(HashMap<String, Object> object);
    
    @Select(value =
            "{call #{listado, mode = OUT, jdbcType = CURSOR, resultMap = resumenReporGigante} := " + 
            "PTOVENTA.PTOVENTA_REPORTE.F_CUR_RESUMEN_REPORTE_GIGANTE(" +
            "#{cCodGrupoCia_in}," + "#{cCodLocal_in}," + "#{cMesId_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtenerResumenReporteGigante(HashMap<String, Object> object);
    
    @Select(value =
            "{call PTOVENTA.PKG_GENE_RESU_VTAS.SP_GIG_LOAD_VENTAS_VEN_LOC(" +
            "#{cCodGrupoCia_in}," +
            "#{cCodLocal_in}," + 
            "#{an_Mes_Id}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void procesarVentasComisionGigante(HashMap<String, Object> object);
    
}
