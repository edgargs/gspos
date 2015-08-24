package mifarma.ptoventa.recaudacion.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface MapperRecaudacionTrsscSix {


    /**
     * REGISTRAR UNA CONSULTA DE DEUDA PARA RECAUDACION CLARO
     * @author GFonseca
     * @since 04.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_REGTR_CONSU_DEUDA_CLARO(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," + "#{cEst_Trscc_in}," +
            "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," + "#{cTerminal_in}," +

            "#{cComercio_in}," + "#{cSucursal_in}," + "#{cUbicacion_in}," + "#{cTelefono_in}," +

            "#{cTipoProdServ_in}," +

            "#{cUsu_Crea_in}" +

            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarTrsscConsultaDeudaClaro(Map mapParametros);


    /**
     * REGISTRAR UNA CONSULTA DE DEUDA PARA RECAUDACION CMR
     * @author RLLANTOY
     * @since 24.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_REGTR_CONSU_DEUDA_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," + "#{cEst_Trscc_in}," +
            "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," + "#{cNro_Tarjeta_in}," + "#{cNro_Cuotas_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarTrsscConsultaDeudaCMR(Map mapParametros);

    /**
     * Invoca el procedimiento ADMCENTRAL_RECAUDACION.RCD_OBTENER_EST_TRNSCC (SIX)
     * @author GFonseca
     * @since 09.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{estTrssc, mode=OUT, jdbcType=CHAR} := " + "ADMCENTRAL_RECAUDACION.RCD_OBTENER_EST_TRNSCC(" +
            "#{cCod_Trscc_in}," + "#{cModo_Trscc_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerEstTrsscReacudacion(Map mapParametros);

    /**
     * OBTENER LOS DATOS DE RESPUESTA DEL SIX
     * @author GFonseca
     * @since 10.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{datosTrsscClaro, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "ADMCENTRAL_RECAUDACION.RCD_OBT_DATOS_SIX(" +
            "#{cCod_Trscc_in}," + "#{cModo_Trscc_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerDatosTrsscSix(Map mapParametros);

    /**
     * REGISTRAR UN PAGO CMR
     * @author GFonseca
     * @since 31.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_REGTR_PAGO_DEUDA_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," +

            "#{cEst_Trscc_in}," + "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," +

            "#{cNro_Trjt_in}," + "#{cMonto_in}," + "#{cTerminal_in}," +

            "#{cComercio_in}," + "#{cUbicacion_in}," + "#{cNro_Cuotas_in}," +

            "#{cNro_Caja_in}," + "#{cId_cajero_in}," + "#{cUsu_Crea_in}," +

            "#{cCodRecau_in}," +

            "#{vDniUsu_in}," + "#{nTipoCambio_in}," + "#{cTipoMoneda_in}," + "#{nRecaudOnline_in}," +
            "#{nTotalPagar_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarTrsscPagoDeudaCMR(Map mapParametros);

    /**
     * REGISTRAR UN PAGO RIPLEY
     * @author CVILCA
     * @since 10.10.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_REGTR_PAGO_DEUDA_RIPLEY(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," + "#{cEst_Trscc_in}," +
            "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," + "#{cNro_Trjt_in}," + "#{cMonto_in}," + "#{cTerminal_in}," +
            "#{cComercio_in}," + "#{cUbicacion_in}," + "#{cNro_Cuotas_in}," + "#{cNro_Caja_in}," +
            "#{cId_cajero_in}," + "#{cUsu_Crea_in}," + "#{cCodRecau_in}," + "#{vDniUsu_in}," + "#{nTipoCambio_in}," +
            "#{cTipoMoneda_in}," + "#{nRecaudOnline_in}," + "#{nTotalPagar_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarTrsscPagoDeudaRipley(Map mapParametros);

    /**
     * REGISTRAR UN PAGO CLARO
     * @author GFonseca
     * @since 31.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_REGTR_PAGO_DEUDA_CLARO(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," +

            "#{cEst_Trscc_in}," + "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," +

            "#{cMonto_in}," + "#{cTerminal_in}," +

            "#{cComercio_in}," + "#{cUbicacion_in}," + "#{cTelefono_in}," +

            "#{cTipoProdServ_in}," +

            "#{cNumRecibo_in}," + "#{cUsu_Crea_in}," +

            "#{cCodRecau_in}," +

            "#{vDniUsu_in}," + "#{nTipoCambio_in}," + "#{cTipoMoneda_in}," + "#{nRecaudOnline_in}," +
            "#{nTotalPagar_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarTrsscPagoDeudaClaro(Map mapParametros);

    /**
     * Invoca al procedimiento ADMCENTRAL_RECAUDACION.RCD_ANUL_PAGO_TARJ_CMR
     * @author RLLANTOY
     * @since 31.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_ANUL_PAGO_TARJ_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," + "#{cEst_Trscc_in}," +
            "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," + "#{cNro_Tarjeta_in}," + "#{cNro_Cuotas_in}," +
            "#{cMonto_in}," + "#{cNum_Caja_in}," + "#{cId_Cajero_in}," + "#{cMotvExtorno_in}," +
            "#{cCod_Trssc_Six_in}," + "#{cTerminal_in}," +

            "#{cComercio_in}," + "#{cUbicacion_in}," + "#{cUsu_Crea_in}," + "#{cCodRecau_in}," +

            "#{vDniUsu_in}," + "#{nTipoCambio_in}," + "#{cTipoMoneda_in}," + "#{cCodRecauAnul_in}," +

            "#{cFechaRecauAnul_in}," + "#{cCodAutotizAnul_in}," + "#{nRecaudOnline_in}," + "#{nTotalPagar_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anularPagoTarjetaCMR(Map mapParametros);


    /**
     * Registra una transaccion de anulacion Ripley en ADM.
     * @author GFONSECA
     * @since 28.10.2013
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_ANUL_PAGO_TARJ_RIPLEY(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," + "#{cEst_Trscc_in}," +
            "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," + "#{cNro_Tarjeta_in}," + "#{cNro_Cuotas_in}," +
            "#{cMonto_in}," + "#{cNum_Caja_in}," + "#{cId_Cajero_in}," +
            // "#{cMotvExtorno_in}," +
            "#{cCod_Trssc_Six_in}," + "#{cFecha_Origen_in}," + "#{cCod_auditoria_in}," + "#{cTerminal_in}," +

            "#{cComercio_in}," + "#{cUbicacion_in}," + "#{cUsu_Crea_in}," + "#{cCodRecau_in}," +

            "#{vDniUsu_in}," + "#{nTipoCambio_in}," + "#{cTipoMoneda_in}," + "#{cCodRecauAnul_in}," +

            "#{cFechaRecauAnul_in}," + "#{cCodAutotizAnul_in}," + "#{nRecaudOnline_in}," + "#{nTotalPagar_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anularPagoTarjetaRipley(Map mapParametros);


    /**
     * REALIZAR UNA ANULACION DE VENTA CMR
     * @author GFONSECA
     * @since 05.09.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{idTrssc, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_ANUL_VENTA_TARJ_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," + "#{cEst_Trscc_in}," +
            "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," + "#{cNro_Tarjeta_in}," +

            "#{cMonto_in}," +
            //auditoria se obtiene en el procedimiento
            "#{cTerminal_in}," + "#{cComercio_in}," + "#{cUbicacion_in}," + "#{cNro_Cuotas_in}," +
            //sucursal se obtiene en el procedimiento
            "#{cId_Cajero_in}," + "#{cNum_Doc_in}," + "#{cId_Anular_in}," + "#{cUsu_Crea_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anularPagoTarjetaVentaCMR(Map mapParametros);


    /**
     * Invoca al procedimiento ADMCENTRAL_RECAUDACION.RCD_ANUL_PAGO_SERV_CLARO
     * @author RLLANTOY
     * @since 31.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_ANUL_PAGO_SERV_CLARO(" +
            "#{cAuditoriaOrig_in}," + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," +
            "#{cTip_Msj_in}," +

            "#{cEst_Trscc_in}," + "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," +

            "#{cMonto_in}," + "#{cTerminal_in}," + "#{cComercio_in}," +

            "#{cUbicacion_in}," + "#{cCod_sucursal_in}," + "#{cUsuCrea_in}," + "#{cCodRecau_in}," +

            "#{vDniUsu_in}," + "#{nTipoCambio_in}," + "#{cTipoMoneda_in}," + "#{cCodRecauAnul_in}," +

            "#{cFechaRecauAnul_in}," + "#{cCodAutotizAnul_in}," + "#{nRecaudOnline_in}," + "#{nTotalPagar_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anularPagoServicioCLARO(Map mapParametros);

    /**
     * OBTENER EL ESTADO DE LA TRANSACCION PROCESADA POR EL DEMONIO
     * @author GFonseca
     * @since 08.08.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{vEstado, mode=OUT, jdbcType=CHAR} := " + "ADMCENTRAL_RECAUDACION.RCD_OBT_ESTADO_TRSSC(" + "#{cCod_Trscc_in}," +
            "#{cModo_Trscc_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerEstadoTrssc(Map mapParametros);


    /**
     * RETORNA DATOS DE UNA RECAUDACION VENTA CMR, PARA SU ANULACION
     * @author GFonseca
     * @since 04.09.2013
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "ADMCENTRAL_RECAUDACION.RCD_GET_DATOS_ANUL_VENTA_CMR(" +
            "#{cCod_TrsscSix_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getDatosAnulacionVentaCMR_SIX(Map mapParametros);

    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "BTLPROD.ADMCENTRAL_RECAUDACION.RCD_REGTR_PAGO_CITIBANK(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cTipRcd_in}," +

            "#{nTotalPagar_in}," + "#{cCodRecau_in}," + "#{vCodCliente_in}," +

            "#{vNroTarjeta_in}," + "#{vCodAutorizacion_in}," +

            "#{vIdUsu_in}," +

            "#{vDniUsu_in}," +

            "#{nTipoCambio_in}," + "#{cTipoMoneda_in}," + "#{nRecaudOnline_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void registrarTrsscPagoCitibank(Map<String, Object> object);

    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "BTLPROD.ADMCENTRAL_RECAUDACION.RCD_ANULR_PAGO_CITIBANK(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cTipRcd_in}," +

            "#{nTotalPagar_in}," + "#{cCodRecau_in}," + "#{vCodCliente_in}," +

            "#{vNroTarjeta_in}," + "#{vCodAutorizacion_in}," +

            "#{vIdUsu_in}," +

            "#{vDniUsu_in}," +

            "#{nTipoCambio_in}," + "#{cTipoMoneda_in}," + "#{cCodRecauAnul_in}," +

            "#{cFechaRecauAnul_in}," + "#{cCodAutotizAnul_in}," + "#{nRecaudOnline_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void anularTrsscPagoCitibank(Map<String, Object> object);

    /**
     * ANULA RECAUDACIONES AUTOMATICAMENTE
     * @author kmoncada
     * @since 11.06.2014
     * @param mapParametros
     */
    @Select(value =
            "{call PTOVENTA.PKG_DAEMON_EXTORNO_RCD.RCD_ANUL_PAGOS(" + "#{cCodGrupoCia_in}," + "#{cCodLocal_in}," +
            "#{cCodTrssc_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anulaTrsccPagoRecaudacion(Map mapParametros);
};
