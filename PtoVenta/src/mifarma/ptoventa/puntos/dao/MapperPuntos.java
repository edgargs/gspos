package mifarma.ptoventa.puntos.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperPuntos {
    
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=afiliado} := " + "PTOVENTA.PTOVENTA_FIDELIZACION.GET_F_DATOS_CLIENTE(" +
            "   #{cDniCliente_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtenerClienteAfiliado(HashMap<String, Object> object);
    
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=impresion} := " + "PTOVENTA.PTOVENTA_FIDELIZACION.F_IMPR_AFILIACION_PTOS(" +
            "#{cCodGrupoCia_in}," + "#{cCodLocal_in}," + "#{cNumDoc_in}," + "#{cSecUsu_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getVoucherAfiliacionPtos(HashMap<String, Object> object);
    
    @Select(value =
            "{call PTOVENTA.PTOVENTA_FIDELIZACION.F_ACTUALIZA_ESTADO_AFILIA_PTOS(" +
    "   #{cNroTarjeta_in}," + "   #{cNroDocumento_in}," + "   #{cEstadoTrsx_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void actualizarEstadoAfiliacion(HashMap<String, Object> object);
    
    /*
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA.FARMA_PUNTOS.F_EVALUA_TARJETA(" + 
            "#{cNumTarj_in}," + "#{cTipoTarjeta_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void evaluaTipoTarjeta(HashMap<String, Object> object);
*/
    
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA.FARMA_PUNTOS.F_IND_ADICIONA_TARJ_ORBIS}")
    @Options(statementType = StatementType.CALLABLE)
    public void restrigueAsociarTarjetasOrbis(HashMap<String, Object> object);
    
    
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA.FARMA_PUNTOS.F_IS_TARJ_VALIDA_OTRO_PROGRAMA(" + 
            "#{cNumTarj_in}," + "#{cIncluidoPtos_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void isTarjetaOtroPrograma(HashMap<String, Object> object);
    
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=listaproductosptos} := " + "PTOVENTA.FARMA_PUNTOS.F_CUR_V_LISTA_PRODUCTO(" +
            "   #{cCodGrupoCia_in},"+"#{cCodLocal_in},"+ "#{cNumPedVta_in},"+"#{cConBonificado_in}," +"#{cFlag_in},"+"#{cIndAnulaPed_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getListaProductoQuote(HashMap<String, Object> object);
    
    @Select(value =
            "{call #{resultado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA.FARMA_PUNTOS.F_VAR_QUIMICO_BLOQUEO_TARJ}")
    @Options(statementType = StatementType.CALLABLE)
    public void isSolicitaClaveQuimicoBloqueoTarjeta(HashMap<String, Object> object);
    
    @Select(value =
            "{call PTOVENTA.PTOVENTA_FIDELIZACION.P_BLOQUEO_TARJETA(" +
            "#{cNroTarjeta_in}," + "#{cLoginUsu_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void bloqueoTarjeta(HashMap<String, Object> object);
   
}