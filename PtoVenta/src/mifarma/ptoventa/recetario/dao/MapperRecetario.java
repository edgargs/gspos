package mifarma.ptoventa.recetario.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MapperRecetario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface MapperRecetario {

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := PTOVENTA_RCM.GET_FORMA_FARMAC}")
    @Options(statementType = StatementType.CALLABLE)
    void getListarRecetarios();

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := PTOVENTA_RCM.GET_FORMA_FARMAC}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaFormaFarmac(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RCM.LISTA_INSUMOS}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaInsumos(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RCM.GET_INF_INSUMO(" +
            "#{cCod_Insumo_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getInformacionInsumo(Map<String, Object> mapParametros);

    @Select(value =
            "{ call #{vIndicador, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.RCM_GRAB_CAB(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cInd_Esteril_in}," + "#{cCod_Forma_Farmac_in}," +
            "#{nCant_Contenido_in}," + "#{nCant_Preparados_in}," + "#{cFec_Recetario_in}," + "#{cCod_Paciente_in}," +
            "#{cCod_Medico_in}," + "#{cFec_Entrega_in}," + "#{cHora_Entrega_in}," + "#{cCod_Local_Entrega_in}," +
            "#{cObs_Recetario_in}," + "#{nVal_Bruto_Recetario_in}," + "#{cEst_Pedido_Cab_in}," + "#{cUsu_Crea_in}," +
            "#{cFec_Crea_in}," + "#{cNum_Telefono_in}," + "#{cNum_Guia_in}," + "#{nValIgvRecetario_in}," +
            "#{nValVentaRecetario_in}," + "#{vValVentaRecSinRed_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabaCabRM(Map<String, Object> mapParametros);

    @Select(value =
            "{call PTOVENTA_RCM.RCM_GRAB_DET(" + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," +
            "#{cNum_Recetario_in}," + "#{nCod_Insumo_in}," + "#{nCant_Atendida_in}," + "#{cVal_Prec_Rec_in}," +
            "#{cVal_Prec_Total_in}," + "#{cUsu_Crea_in}," + "#{cFec_Crea_in}," + "#{cCodUnidadVenta_in}," +
            "#{cCant_Porcentaje_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabaDetaRM(Map<String, Object> mapParametros);

    @Select(value = "{call PTOVENTA_RCM.RCM_ASIGNA_PEDIDO_RCM(" + "#{cNum_Recetario}," + "#{cNum_Pedido}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void asignarPedidoRM(Map<String, Object> mapParametros);

    @Select(value =
            "{call  #{vIndicador, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.IMP_HTML_CONTRASENIA(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Recetario_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getHtmlContrasenia(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, " + "resultMap=resultado} := PTOVENTA_RCM.RCM_LISTAR_GUIAS_RCM}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaGuiasRM(Map<String, Object> mapParametros);

    @Select(value =
            "{ call #{vIndicador, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.RCM_GUIA_GRAB_CAB(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Ord_Prep_in}," + "#{cUsu_Crea_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarGuiaRM(Map<String, Object> mapParametros);

    @Select(value =
            "{ call #{vIndicador, mode=OUT, jdbcType=DOUBLE} := " + "PTOVENTA_RCM.GET_PORC_IGV(" + "#{cCodIgv_in}" +
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getPorcIgv(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RCM.LISTA_PROD_VIRTUALES}")
    @Options(statementType = StatementType.CALLABLE)
    void getListarProdVirtual(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RCM.LISTA_UNID_MEDIDA_REL(" +
            "#{ccodunidmed_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListarUnidadesRelac(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.GET_NUMERO_RECETARIO(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNumPedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getNumeroRecetario(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{trama, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.GET_TRAMA_RECETARIO(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Recetario_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getTramaRecetario(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "COMERCIAL.PACTUALIZAR_RECETA_FV(" + "#{Entrada}," + "#{Salida, mode=OUT, jdbcType=CHAR}" +
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void enviaTramaRecetario(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{estado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.GET_EST_RECETARIO_PEDIDO(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Pedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getEstadoRecetarioPedido(Map<String, Object> mapParametros);

    @Select(value =
            "{call PTOVENTA_RCM.ANULA_RECETARIO_PEDIDO(" + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," +
            "#{cNum_Pedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anuladoRecetarioPedido(Map<String, Object> mapParametros);

    @Select(value =
            "{call  #{vFactor, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.GET_FACTOR_CONVERSION(" + "#{cCod_Producto}," +
            "#{cCod_UnidadVenta}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getFactorConversion(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{resultado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RCM.GET_TRAMA_ACTUALIZA_RECETARIO(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Recetario_in}," +
            "#{cAccion_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getTramaActualizaRecetarioFasa(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "#{resultado, mode=OUT, jdbcType=CHAR} := " + "COMERCIAL.KRECETARIO_FASACOM.FACTUALIZA_ESTADO(" +
            "#{pmensaje}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void enviaActualizaRecetarioFasa(Map<String, Object> mapParametros);

    /**
     * Retorna listado de reporte de recetario magistral
     * @author LLEIVA
     * @since 27.Sep.2013
     * @param mapParametros
     */
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RCM.REPORTE_RECETARIO_MAGISTRAL(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cFecInicial_in}," + "#{cFecFinal_in}," +
            "#{cPaciente_in}," + "#{cNumRecetario_in}," + "#{cNumPedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void reporteRecetario(Map<String, Object> mapParametros);

    /**
     * Retorna listado de detalle de reporte de recetario magistral
     * @author LLEIVA
     * @since 27.Sep.2013
     * @param mapParametros
     */
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RCM.LISTADO_DETALLE_RCM(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cNumRecetario_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listadoDetalleReporteRecetario(Map<String, Object> mapParametros);

    /**
     * Retorna detalle de reporte de recetario magistral
     * @author LLEIVA
     * @since 27.Sep.2013
     * @param mapParametros
     */
    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RCM.DETALLE_RCM(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cNumRecetario_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void detalleReporteRecetario(Map<String, Object> mapParametros);
}
