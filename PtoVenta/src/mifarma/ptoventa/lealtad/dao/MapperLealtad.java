package mifarma.ptoventa.lealtad.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


/**
 *
 * @author ERIOS
 * @since 05.02.2015
 */
public interface MapperLealtad {
    
    @Select(value =
            "{call #{nRetorno, mode=OUT, jdbcType=NUMERIC} := " + "FARMA_LEALTAD.VERIFICA_ACUMULA_X1(" +
            "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
            "   #{cCodProd_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    void verificaAcumulaX1(Map<String, Object> mapParametros);
    
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_LEALTAD.LISTA_ACUMULA_X1(" +
            "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
            "   #{cCodProd_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listaAcumulaX1(Map<String, Object> mapParametros);
    
    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.IND_IMPR_VOUCHER_X1}")
    @Options(statementType = StatementType.CALLABLE)
    void indImpresionVoucherX1(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_PARAMETROS_VENTA(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getParametrosVenta(HashMap<String, Object> object);

    @Select(value =
            "{call FARMA_LEALTAD.GET_ACTUALIZAR_VENTA(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in},"+ "   #{vIdTransaccion_in},"+ "   #{vNumAutorizacion_in},"+ "   #{vIdUsu_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getActualizarVenta(HashMap<String, Object> object);

    @Select(value =
            "{call FARMA_LEALTAD.GET_ELIMINA_BONIFICA(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void eliminaProdBonificacion(HashMap<String, Object> object);

    @Select(value =
            "{call FARMA_LEALTAD.GET_DESCARTAR_PEDIDO(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void descartarPedido(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_SALDO_PUNTOS(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getSaldoPuntos(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_MULTIPLO_PTO(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}" +
    "   "+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getMultiploPtos(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_MOSTRAR_PUNTOS(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getIndicadoresPuntos(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_USO_NCR(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}, #{cTipoBusqueda_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void verificaUsoNCR(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_FECHA_NCR(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}, #{cFechaNCR_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void verificaFechaNCR(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_CREDITO_NCR(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}, #{cFechaNCR_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void verificaCreditoNCR(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "FARMA_LEALTAD.GET_MONTO_NCR(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cNumPedVta_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getMontoNCR(HashMap<String, Object> object);

    @Select(value =
            "{call PTOVENTA.FARMA_LEALTAD.REGISTRA_INSCRIPCION_TURNO(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cSecMovCaja_in}, #{vCodTarjeta_in}, #{cIdUsu_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void registrarInscripcionTurno(HashMap<String, Object> object);

    @Select(value =
            "{call #{vRetorno, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA.FARMA_LEALTAD.GET_INSCRIPCION_TURNO(" +
    "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}," +
    "   #{cSecMovCaja_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getInscripcionTurno(HashMap<String, Object> object);
    
    @Select(value =
            "{call PTOVENTA.FARMA_PUNTOS.P_RECHAZO_AFILIACION_PTOS(" +
    "   #{cCodGrupoCia_in}," + "   #{cNumDocumento_in}," + "   #{cNumTarjeta_in}," +
    "   #{cSecMovCaja_in}"+ ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void rechazarIncripcionPuntos(HashMap<String, Object> object);
}
