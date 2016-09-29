package mifarma.ptoventa.lectorHuella.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperLectorHuella {
    
    @Select(value =
            "{call #{listado, mode = OUT, jdbcType = CURSOR, resultMap = usuarioFV} := " + 
            "PTOVENTA.PTOVENTA_ADMIN_USU.F_CUR_USUARIO_FV(" +
            "#{pCodGrupoCia_in}," + 
            "#{pCodLocal_in}," + 
            "#{pSecUsu_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerUsuarioFV(Map<String, Object> mapParametros);
    
    @Select(value =
            "{call #{respuesta, mode=OUT, jdbcType=CHAR} := " + 
            "PTOVENTA.PTOVENTA_ADMIN_USU.F_REGISTRAR_HUELLA(" +
            "#{pCodGrupoCia_in}," + 
            "#{pCodLocal_in}," + 
            "#{pSecUsu_in}," + 
            "#{pHuella_in,jdbcType=BINARY}," + 
            "#{pUsuMod_in}," + 
            "#{pPosicionHuella_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarHuellaDactilar(Map<String, Object> mapParametros);
    
    @Select(value =
            "{call #{respuesta, mode=OUT, jdbcType=CHAR} := " + 
            "PTOVENTA.PTOVENTA_ADMIN_USU.F_NUM_REPITE_HUELLA" +
            "}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerCantidadRepiteHuella(Map<String, Object> mapParametros);
    
    @Select(value =
            "{call #{respuesta, mode=OUT, jdbcType=CHAR} := " + 
            "PTOVENTA.PTOVENTA_ADMIN_USU.F_VAR_SOLICITUD_HUELLAS" +
            "}")
    @Options(statementType = StatementType.CALLABLE)
    void obtieneHuellasSolicitar(Map<String, Object> mapParametros);

}
