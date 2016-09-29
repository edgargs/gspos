package mifarma.ptoventa.fidelizacion.dao;

import java.util.HashMap;

import java.util.Map;

import oracle.sql.CHAR;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperFidelizacion {
    
    
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=tipoDocIdentidad} := " + "PTOVENTA.PTOVENTA_FIDELIZACION.FID_CUR_TIPO_DOCUMENTO}")
    @Options(statementType = StatementType.CALLABLE)
    public void getListaTipoDocumento(HashMap<String, Object> object);
    
    @Select(value =
            "{call #{resultado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA.PTOVENTA_FIDELIZACION.FID_GET_COD_TIPO_DOC_CLIENTE("+
            "#{cNroDocumento_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getCodigoTipoDocumentoAfiliado(HashMap<String, Object> object);
    
}
