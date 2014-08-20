package mifarma.transf.daemon.dao;

import java.util.List;
import java.util.Map;

import mifarma.transf.daemon.bean.LgtGuiaRem;
import mifarma.transf.daemon.bean.LgtNotaEsCab;

import mifarma.transf.daemon.bean.LgtNotaEsDet;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.StatementType;


public interface MapperDaemon {

    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=local} := " +
                        "PTOVENTA.PKG_DAEMON_TRANSF.GET_LOCALES_ENVIO(" +
                            "#{cCodGrupoCia_in}," +
                            "#{cCodCia_in}" +
                        ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getLocalesEnvio(Map<String,Object>  mapParametros);
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=transferencia} := " +
                        "PTOVENTA.PKG_DAEMON_TRANSF.GET_NOTAS_PENDIENTES(" +
                            "#{cCodGrupoCia_in}," +
                            "#{cCodCia_in}," +
                            "#{cCodLocal_in}" +
                        ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getNotasPendientes(Map<String,Object>  mapParametros);

    @SelectProvider(type = SQLDaemonProvider.class, method = "selectLgtNotaEsCab")
    public LgtNotaEsCab getNotaEsCab(Map<String, Object> object);

    @SelectProvider(type = SQLDaemonProvider.class, method = "selectLgtNotaEsDet")
    public List<LgtNotaEsDet> getNotaEsDet(Map<String, Object> object);

    @SelectProvider(type = SQLDaemonProvider.class, method = "selectLgtGuiaRem")
    public List<LgtGuiaRem> getGuiaRem(Map<String, Object> object);
    
    @SelectProvider(type = SQLDaemonProvider.class, method = "insertLgtNotaEsCab")
    void saveNotaEsCab(LgtNotaEsCab lgtNotaEsCab);

    @SelectProvider(type = SQLDaemonProvider.class, method = "insertLgtNotaEsDet")
    public void saveNotaEsDet(LgtNotaEsDet lgtNotaEsDet);

    @SelectProvider(type = SQLDaemonProvider.class, method = "insertLgtGuiaRem")
    public void saveGuiaRem(LgtGuiaRem lgtGuiaRem);

    @Select(value="{call " +
                        "PTOVENTA.PKG_DAEMON_TRANSF.UPD_ENVIO_DESTINO(" +
                            "#{cCodGrupoCia_in}," +
                            "#{cCodCia_in}," +
                            "#{cCodLocal_in}," +
                            "#{cNumNotaEs_in}" +
                        ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizaEnvioDestino(Map<String, Object> object);
    
    @Select(value="{call " +
                        "PTOVENTA.PTOVENTA_MATRIZ_TRANSF.LLEVAR_DESTINO_BV(" +
                            "#{cCodGrupoCia_in}," +
                            "#{cCodLocalOrigen_in}," +
                            "#{cCodLocalDestino_in}," +
                            "#{cNumNotaEs_in}," +
                            "#{vIdUsu_in}" +
                        ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void grabarTransfBV(Map<String, Object> object);

    @Select(value="{call #{indicador, mode=OUT, jdbcType=VARCHAR} := " +
                        "CONN_LOCAL.PING(" +
                            "#{p_HOST_NAME}," +
                            "#{p_PORT}" +
                        ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void validaConexionLocal(Map<String, Object> object);
}
