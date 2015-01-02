package mifarma.ptoventa.recaudacion.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface MapperRecaudacion {

    @Select(value =
            "{call  #{vIndicador, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_GRAB_CAB(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNro_Tarjeta_in}," + "#{cTipo_Rcd_in}," +
            "#{cTipo_Pago_in}," + "#{cEst_Rcd_in}," + "#{cEst_Cuenta_in}," + "#{cCod_Cliente_in}," +
            "#{cTip_Moneda_in}," + "#{nIm_Total_in}," + "#{nIm_Total_Pago_in}," + "#{nIm_Min_Pago_in}," +
            "#{nVal_Tip_Camb_in}," + "#{cFecha_Venc_Recau_in}," + "#{cNom_Cliente_in}," + "#{cFec_Ven_Trj_in}," +
            "#{cFec_Crea_Recau_Pago_in}," + "#{cUsu_Crea_Recau_Pago_in}," + "#{cFec_Mod_Recau_Pago_in}," +
            "#{cUsu_Mod_Recau_Pago_in}," + "#{cCod_Autorizacion_in}," + "#{cSec_Mov_Caja_in}," + "#{cNum_Pedido_in}," +
            "#{cTipo_Prod_Serv_in}," + "#{cNum_Recibo_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabaCabRecaudacion(Map mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_LISTA_RCD_PEND(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCod_Rcd_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listarRcdPedien(Map mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_LISTA_COD_FOM_PAGO}")
    @Options(statementType = StatementType.CALLABLE)
    void listarCodFormPago(Map mapParametros);

    @Select(value =
            "{call PTOVENTA_RECAUDACION.RCD_GRAB_FOM_PAGO(" + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," +
            "#{cCod_Recau_in}," + "#{cCod_Forma_Pago_in}," + "#{cImp_Total_in}," + "#{cTip_Moneda_in}," +
            "#{cVal_Tip_Cambio_in}," + "#{cVal_Vuelto}," + "#{cIm_Total_Pago_in}," + "#{cFec_Crea_Recau_Pago_in}," +
            "#{cUsu_Crea_Recau_Pago_in}," + "#{cFec_Mod_Recau_Pago_in}," + "#{cUsu_Mod_Recau_Pago_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarFormasPagoRCD(Map mapParametros);

    @Select(value =
            "{call PTOVENTA_RECAUDACION.RCD_CAMBIAR_PED_RCD(" + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," +
            "#{cCod_Recau_in}," + "#{cInd_Recau_in}," + "#{cUsu_Mod_Rcd_in}," + "#{cSecMovCaja_in}," +
            "#{cEstado_ImpRecaudacion_In}," + "#{cCod_Trssc_In}," + "#{cEst_TrsscSix_in}," +
            "#{cCod_Autorizacion_in}," + "#{cSecMovCaja_in}," + "#{cFech_Orig_in}," + "#{cNro_Cuotas_in}," +
            "#{cFec_Venc_Cuota_in}," + "#{cMonto_Pagar_Cuota_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void cambiarEstadoRCD(Map mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_LISTA_RCD_CANCE(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCod_Rcd_in}," + "#{cMonto_Rcd_in}," +
            "#{cTipoCobro_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listarRcdCance(Map mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_DEUDA_PRES_CITI(" +
            "#{cCod_Cli_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerMontoPagarCitiPres(Map mapParametros);

    @Select(value =
            "{call " + "#{trama, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_TRAMA_CONSULTA_CITI_PRES(" +
            "#{cFlag_Cod_in}," + "#{cCod_in}," + "#{cTip_Moneda_in}," + "#{cTip_trans_in}," + "#{cCod_local_in}" +
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getTramaPresCiti(Map mapParametros);

    @Select(value =
            "{call " + "RECTER_OWN.PRECAUDACION_TERCEROS(" + "#{pentrada}," + "#{psalida, mode=OUT, jdbcType=CHAR}" +
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void setTramaPresCiti(Map mapParametros);

    @Select(value =
            "{call #{infoBinRecau, mode=OUT, jdbcType=CURSOR,resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_F_OBTENER_BIN_TARJETA(" +
            "#{cCodGrupoCia_in}," + "#{cCodTarj_in}," + "#{cTipOrigen_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getInfoTarjRecaudacion(Map mapParametros);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_PROCESA_TRAMA_PRES_CITI(" +
            "#{cTrama_PresCiti}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerDatosPagarCitiPres(Map mapParametros);

    /**
     * Invoca el procedimiento RCD_IMP_COMPAGO_RECAUDACION
     * @author RLLANTOY
     * @since 28.06.2013
     */
    @Select(value =
            "{call  #{vIndicador, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_IMP_COMPAGO_RECAUDACION(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Recaudacion_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCompPagoRecau(Map mapParametros);


    /**
     * Invoca el procedimiento RCD_GET_EST_IMP_RECAUDACION
     * @author RLLANTOY
     * @since 28.06.2013
     */
    @Select(value =
            "{call #{vIndicador, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_GET_EST_IMP_RECAUDACION(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Recaudacion_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getEstImpRecaudacion(Map mapParametros);


    /**
     * Invoca el procedimiento RCD_UPDATE_EST_IMP_RECAUDACION
     * @author RLLANTOY
     * @since 28.06.2013
     */
    @Select(value =
            "{call PTOVENTA_RECAUDACION.RCD_UPDATE_EST_IMP_RECAUDACION(" + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," +
            "#{cCod_Local_in}," + "#{cNum_Recaudacion_in}," + "#{cEstado_ImpRecaudacion_In}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void updateEstImpresionRecaudacion(Map mapParametros);

    /**
     * Invoca el procedimiento RCD_ANULAR_RECAUDACION
     * @author GFonseca
     * @since 25.06.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{vCod_Recau, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_ANULAR_RECAUDACION(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCodReca_in}," +
            "#{nNumCajaPago_in}," + "#{nUsuModRecauPago_in}," + "#{nCod_TrsscAnul_in}," + "#{cCodRecauAnul_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anularRecaudacion(Map mapParametros);


    /**
     * REALIZA LA ANULACION DE UNA RECAUDACION DE VENTA CMR (NO TIENE FORMAS DE PAGO)
     * @author GFonseca
     * @since 25.06.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{vCod_Recau, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_ANULAR_RECAU_VENTA_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCodReca_in}," +
            "#{nNumCajaPago_in}," + "#{nUsuModRecauPago_in}," + "#{nCod_TrsscAnul_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anularRecaudacionVentaCMR(Map mapParametros);


    /**
     * Invoca el procedimiento RCD_GET_EST_RECAUDACION
     * @author RLLantoy
     * @since 09.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{Vest_Recau, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_GET_EST_RECAUDACION(" +
            "#{cCodGrupoCia_in}," + "#{Ccod_Cia_In}," + "#{cCod_Local_in}," + "#{Cnum_Recaudacion_In}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getEstRecaudacion(Map mapParametros);

    /**
     * Invoca al procedimiento RCD_TIEMPO_ANUL_RECAUDACION
     * @author RLLantoy
     * @since 11.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{cRepta, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_TIEMPO_ANUL_RECAUDACION(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_In}," + "#{cCod_Local_in}," + "#{cNum_Recaudacion_In}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void valTiempoAnulRecaudacion(Map mapParametros);


    /**
     * Invoca al procedimiento RCD_TIEMPO_MAX_ANULACION
     * @author RLLantoy
     * @since 11.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{vTiempo, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_TIEMPO_MAX_ANULACION(" +
            "#{cTipo_llave}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void tiempoMaximoAnulRecaudacion(Map mapParametros);

    /**
     * Invoca el procedimiento RCD_CAMBIAR_PED_RCD
     * @author GFonseca
     * @since 25.06.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{strMontoPagado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_ACTUALIZAR_COBRO_PRES_CITI(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCodRecau_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void actualizarMontoCobradoPresCiti(Map mapParametros);


    /**
     * Permite actualizar el estado una recaudacion con el six
     * @author GFonseca
     * @since 09.08.2013
     * @param mapParametros
     */
    @Select(value =
            "{call PTOVENTA_RECAUDACION.RCD_ACTUALIZAR_EST_TRSSC_SIX(" + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," +
            "#{cCod_Local_in}," + "#{cCodRecau_in}," + "#{cEstTrsscSix}," + "#{cCodAutoriz}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void actualizarEstRecauTrsscSix(Map mapParametros);


    /**
     * Obtener el DNI de un usuario
     * @author GFonseca
     * @since 19.08.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{vDNI, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_OBTENER_DNI_USU(" + "#{cSecUsuLocal_in}" +
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerDniUsuario(Map mapParametros);


    /**
     * Registrar datos de recaudacion en Fasa, para la conciliación de recaudación
     * @author GFonseca
     * @since 19.08.2013
     * @param mapParametros
     */
    @Select(value =
            "{call COMERCIAL.PRECAUDACION_VENTA_PRAGMA(" + "#{PLOCAL, jdbcType=NUMERIC}," + "#{PID_VENDEDOR, jdbcType=NUMERIC}," +
            //DNI
            "#{PFECHA_VENTA, jdbcType=VARCHAR}," + "#{PMONTO_VENTA, jdbcType=NUMERIC}," +
            "#{PNUM_CUOTAS, jdbcType=NUMERIC}," + //solo en venta
            "#{PCOD_AUTORIZACION, jdbcType=VARCHAR}," + "#{PTRACK2, jdbcType=VARCHAR}," + //solo en venta
            "#{PCOD_AUTORIZACION_PRE, jdbcType=VARCHAR}," + //solo en venta cuando es anulacion
            "#{PFPA_VALORXCUOTA, jdbcType=NUMERIC}," + //solo en venta
            "#{PCAJA, jdbcType=NUMERIC}," + "#{PTIPO_TRANSACCION, jdbcType=NUMERIC}," +
            //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion
            "#{PCODISERV, jdbcType=VARCHAR}," +
            //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank
            "#{PFPA_NUM_OBJ_PAGO, jdbcType=VARCHAR}," + "#{PNOMBCLIE, jdbcType=VARCHAR}," +
            "#{PVOUCHER, jdbcType=NUMERIC}," + //Nro de Comprobante
            "#{PNRO_COMP_ANU, jdbcType=NUMERIC}," + //Anulacion-Nro de Comprobante origen
            "#{PFECH_COMP_ANU, jdbcType=VARCHAR}," + //Anulacion-Fecha Origen del Comprobante
            "#{PCODIAUTOORIG, jdbcType=VARCHAR}," + //Anulacion-Codigo autorizacion Origen
            "#{PFPA_TIPOCAMBIO, jdbcType=NUMERIC}," + //solo en recaudacion si es 2 Dolares
            "#{PFPA_NROTRACE, jdbcType=NUMERIC}," + //trace de la venta o Pago
            "#{PCOD_ALIANZA, jdbcType=NUMERIC}," + "#{PCOD_MONEDA_TRX, jdbcType=NUMERIC}," +
            //Recaudacion 1 Soles , 2 Dolares, en venta tipo 7(CMR),9 Ripley, 5 Tcredit, 6 Tdebito ,
            "#{PMON_ESTPAGO, jdbcType=VARCHAR}," + "#{Salida, mode=OUT, jdbcType=VARCHAR}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void setDatosRecauConciliacion(Map mapParametros);

    /*
    PCL_COD_ID_CONCENTRADOR   VARCHAR2, --052 Claro y 055 Movistar
    PCL_NUMERO_TELEFONO       VARCHAR2,--Nro de Telefono
    PCL_COD_AUTORIZACION      VARCHAR2, --Codigo de Autorizacion
    PCL_COD_VENDEDOR          VARCHAR2, --Codigo de Vendedor
    PCL_FECHA_VENTA           DATE,      -- Eviar solo Fecha deventa dd/mm/yyyy
    PCL_HORA_VENTA            VARCHAR2,  ---> Enviar Hora de Recarga HH:MM:SS
    PCL_NUMERO_DOCUMENTO      VARCHAR2, --Comprobante
    PCL_COD_COMERCIO          VARCHAR2,  --Ccodigo de Local
    PCL_COD_TERMINAL          VARCHAR2,  --Nro de Caja
    PCL_MONTO_VENTA           NUMBER,       --Monto de Recarga
    PCL_ID_TRANSACCION        VARCHAR2,   --ID enviado por la Empresa telefonica
    */

    /**
     * Registrar datos de recaudacion en Fasa, para la conciliación de recargas
     * @author GFonseca
     * @since 19.08.2013
     * @param mapParametros
     */
    @Select(value =
            "{call COMERCIAL.PRECARGAS(" + "#{PCL_COD_ID_CONCENTRADOR, jdbcType=VARCHAR}," + "#{PCL_NUMERO_TELEFONO, jdbcType=VARCHAR}," +
            "#{PCL_COD_AUTORIZACION, jdbcType=VARCHAR}," + "#{PCL_COD_VENDEDOR, jdbcType=VARCHAR}," +

            "#{PCL_FECHA_VENTA, jdbcType=DATE}," + "#{PCL_HORA_VENTA, jdbcType=VARCHAR}," +
            "#{PCL_NUMERO_DOCUMENTO, jdbcType=NUMERIC}," +

            "#{PCL_COD_COMERCIO, jdbcType=VARCHAR}," + "#{PCL_COD_TERMINAL, jdbcType=VARCHAR}," +
            "#{PCL_MONTO_VENTA, jdbcType=NUMERIC}," +

            "#{PCL_ID_TRANSACCION, jdbcType=VARCHAR}," + "#{Salida, mode=OUT, jdbcType=VARCHAR}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void setDatosRecargaConciliacion(Map mapParametros);


    @Select(value =
            "{call  #{vCodLocal, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_OBTENER_COD_LOCAL_MIGRA(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCodLocalMigra(Map mapParametros);


    /**
     * Retorna el formato HTML para realizar la impresión unicamente para el caso de la recaudación VENTA CMR (06)
     * @author GFonseca
     * @since 20.08.2013
     */
    @Select(value =
            "{call  #{vTemplateHTML, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA_RECAUDACION.RCD_IMPRESION_VOUCHER_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCod_Recau_in}," +
            "#{cNum_Cuotas_in}," + "#{cMonto_Pagar_Cuota_in}," + "#{cFecha_Venc_Cuota_in}," +
            "#{cCod_Autorizacion_in}," + "#{cCod_Caja_in}," + "#{cTip_Copia_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCompPagoRecauVentaCMR(Map mapParametros);


    /**
     * Retorna el formato HTML para realizar la impresión anulacion venta CMR (06)
     * @author GFonseca
     * @since 25.10.2013
     */
    @Select(value =
            "{call  #{vTemplateHTML, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA_RECAUDACION.RCD_IMPRESION_VOUCHER_ANUL_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCod_Recau_in}," +
            "#{cCod_Caja_in}," + "#{cTip_Copia_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCompAnulRecauVentaCMR(Map mapParametros);


    /**
     * Impresión para anulacion de recaudación
     * @author GFonseca
     * @since 27.10.2013
     */
    @Select(value =
            "{call  #{vTemplateHTML, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA_RECAUDACION.RCD_IMPRESION_VOUCHER_ANUL_RCD(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cCod_Caja_in}," +
            "#{cCod_Recau_in}," + "#{cTip_Copia_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCompAnulRecau(Map mapParametros);

    /**
     * Retorna el monto total de recaudaciones asociadas a un movimiento de caja
     * @author GFonseca
     * @since 21.08.2013
     */
    @Select(value =
            "{call  #{vMontoTot, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA_RECAUDACION.RCD_OBTENER_MONTO_TOTAL(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCodLocal_in}," + "#{cSecMovCaja_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getMontoTotalRecaudacionByMovCaja(Map mapParametros);

    /**
     * RETORNA DATOS DE UNA RECAUDACION VENTA CMR, PARA SU ANULACION
     * @author GFonseca
     * @since 04.09.2013
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_GET_DATOS_ANUL_VENTA_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cNum_Pedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getDatosAnulacionVentaCMR(Map mapParametros);

    /**
     * Obtiene el monto total de recaudacion por cierre de dia
     * @author ERIOS
     * @since 30.09.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{vMontoTot, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA_RECAUDACION.RCD_OBTENER_MONTO_TOTAL_DIA(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cFecCierreDia_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getMontoTotalRecaudacionByCierreDia(Map mapParametros);

    /**
     * RETORNA UN LISTADO CONSOLIDADO DE LAS RECAUDACIONES PERTENECIENTES A UNA SECUENCIA DE MOVIMIENTO DE CAJA
     * @author LLEIVA
     * @since 25.Sep.2013
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_REPORTE_CIERRE_TURNO(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cSecMovCaja_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listarReporteCierreTurno(Map mapParametros);

    @Select(value =
            "{ CALL PTOVENTA_CE.VTA_GUARDA_LOG_CONCILIACION(#{cCodGrupoCia_in, mode=IN, jdbcType=CHAR}," + "  #{cCodCia_in, mode=IN, jdbcType=CHAR}," +
            "  #{cCodLocal_in, mode=IN, jdbcType=CHAR}," + "  #{cCodProceso_in, mode=IN, jdbcType=CHAR}," +
            "  #{cDescConcepto_in, mode=IN, jdbcType=CHAR}," + "  #{cEstConciliacion_in, mode=IN, jdbcType=CHAR}," +
            "  #{cUsuCreador_in, mode=IN, jdbcType=CHAR}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void guardarLogConciliacion(@Param("cCodGrupoCia_in")
        String strGrupoCia, @Param("cCodCia_in")
        String strCia, @Param("cCodLocal_in")
        String strCodLocal, @Param("cCodProceso_in")
        String codProceso, @Param("cDescConcepto_in")
        String descConcepto, @Param("cEstConciliacion_in")
        String estConciliacion, @Param("cUsuCreador_in")
        String usuCreador);

    @Select(value =
            "{ CALL PTOVENTA_CE.VTA_GUARDA_LOG_CONCILIACION_2(#{cCodGrupoCia_in, mode=IN, jdbcType=CHAR}," + "  #{cCodCia_in, mode=IN, jdbcType=CHAR}," +
            "  #{cCodLocal_in, mode=IN, jdbcType=CHAR}," + "  #{cPidVendedor_in, mode=IN, jdbcType=CHAR}," +
            "  #{cFechaVenta_in, mode=IN, jdbcType=CHAR}," + "  #{cMontoVenta_in, mode=IN, jdbcType=CHAR}," +
            "  #{cNumCuotas_in, mode=IN, jdbcType=CHAR}," + "  #{cCodAutoriz_in, mode=IN, jdbcType=CHAR}," +
            "  #{cTrack2_in, mode=IN, jdbcType=CHAR}," + "  #{cCodAutorizPre_in, mode=IN, jdbcType=CHAR}," +
            "  #{cValorPorCuota_in, mode=IN, jdbcType=CHAR}," + "  #{cCaja_in, mode=IN, jdbcType=CHAR}," +
            "  #{cTipoTrans_in, mode=IN, jdbcType=CHAR}," + "  #{cCodServ_in, mode=IN, jdbcType=CHAR}," +
            "  #{cNumObjPago_in, mode=IN, jdbcType=CHAR}," + "  #{cNomCliente_in, mode=IN, jdbcType=CHAR}," +
            "  #{cCodVoucher_in, mode=IN, jdbcType=CHAR}," + "  #{cNumCompAnu_in, mode=IN, jdbcType=CHAR}," +
            "  #{cFechCompAnu_in, mode=IN, jdbcType=CHAR}," + "  #{cCodAutorizOrig_in, mode=IN, jdbcType=CHAR}," +
            "  #{cTipoCambio_in, mode=IN, jdbcType=CHAR}," + "  #{cNumTrace_in, mode=IN, jdbcType=CHAR}," +
            "  #{cCodAlianza_in, mode=IN, jdbcType=CHAR}," + "  #{cCodMonedaTrx_in, mode=IN, jdbcType=CHAR}," +
            "  #{cMonEstPago_in, mode=IN, jdbcType=CHAR}," + "  #{cDescConcepto_in, mode=IN, jdbcType=CHAR}," +
            "  #{cEstConciliacion_in, mode=IN, jdbcType=CHAR}," + "  #{cUsuCreador_in, mode=IN, jdbcType=CHAR}," +
            "  #{cCodLocalMigra_in, mode=IN, jdbcType=CHAR}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void guardarLogConciliacion2(@Param("cCodGrupoCia_in")
        String strGrupoCia, @Param("cCodCia_in")
        String strCia, @Param("cCodLocal_in")
        String strCodLocal, @Param("cPidVendedor_in")
        String dniVendedor, @Param("cFechaVenta_in")
        String fechaVenta, @Param("cMontoVenta_in")
        double monto, @Param("cNumCuotas_in")
        double numCuotas, @Param("cCodAutoriz_in")
        String codAutoriz, @Param("cTrack2_in")
        String track2, @Param("cCodAutorizPre_in")
        String codAutorizPre, @Param("cValorPorCuota_in")
        double valorPorCuota, @Param("cCaja_in")
        int caja, @Param("cTipoTrans_in")
        String tipoTrans, @Param("cCodServ_in")
        String codServ, @Param("cNumObjPago_in")
        String numObjPago, @Param("cNomCliente_in")
        String nomCliente, @Param("cCodVoucher_in")
        long codVoucher, @Param("cNumCompAnu_in")
        long numCompAnul, @Param("cFechCompAnu_in")
        String fechaCompAnul, @Param("cCodAutorizOrig_in")
        String codAutorizOrig, @Param("cTipoCambio_in")
        double tipoCambio, @Param("cNumTrace_in")
        long numTrace, @Param("cCodAlianza_in")
        int codAlianza, @Param("cCodMonedaTrx_in")
        int codMoneda, @Param("cMonEstPago_in")
        String monEstPago, @Param("cDescConcepto_in")
        String descConcepto, @Param("cEstConciliacion_in")
        String estConciliacion, @Param("cUsuCreador_in")
        String usuCreador, @Param("cCodLocalMigra_in")
        String codLocalMigra);

    @Select(value =
            "{call " + "#{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_CE.VTA_LISTA_CONCILIACION_NOK(" +
            "   #{cCodGrupoCia_in}," + "   #{cCodCia_in}," + "   #{cCodLocal_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaConciliacionNOK(Map<String, Object> mapParametros);

    @Select(value =
            "{call " + "PTOVENTA_CE.VTA_ACT_LOG_CONCILIACION(" + "  #{cCodLogConc_in, mode=IN, jdbcType=CHAR}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void actualizaRegConciliacion(@Param("cCodLogConc_in")
        String codConciliacion);

    @Select(value =
            "{call #{vCodAlianza, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA_RECAUDACION.RCD_GET_CODIGO_ALIANZA(" +
            "#{cFormaPago_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCodigoAlianzaConciliacion(Map<String, Object> mapParametros);


    /**
     * OBTIENE LOS DATOS DE LA FORMA DE PAGO
     * @author CVILCA
     * @since 28.10.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_GRAL.F_GET_FORMAS_PAGO_TICKET(" +
            "#{cCodGrupoCia}," + "#{cCodCia}," + "#{cCodLocal}," + "#{vNumPedVta}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerDetallePedidoFomasPago(Map mapParametros);


    @Select(value =
            "{call #{resultado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_RECAUDACION.RCD_GET_TARJ_AUTORIZACION(" +
            "#{cNumPedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getNumTarjCodAutoriz(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{vNumPedNegativo, mode=OUT, jdbcType=VARCHAR} := " + "PTOVENTA_RECAUDACION.RCD_GET_NUM_PED_NEGATIVO(" +
            "#{cNumPedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getNumPedidoNegativo(Map<String, Object> mapParametros);

    /**
     * Obtiene tipo de cambio
     * @author ERIOS
     * @since 18.11.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{nValorTipoCambio, mode=OUT, jdbcType=NUMERIC} := " + "FARMA_UTILITY.FN_DEV_TIPO_CAMBIO_F(" +
            "#{cCodGrupoCia_in}," + "#{cFecCambio_in}," + "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cTipoRcd}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getTipoCambio(Map<String, Object> mapParametros);


    /**
     * Actualiza el estado de conciliacion en la tabla log
     * @author GFONSECA
     * @since 18.11.2013
     * @param mapParametros
     */
    @Select(value =
            "{call PTOVENTA_RECAUDACION.RCD_ACT_EST_CONCILIACION(" + "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," +
            "#{cCod_Local_in}," + "#{cCod_Autoriz_in}," + "#{cFecha_Venta_in}," + "#{cTip_Trssc_in}," +
            "#{cPvoucher_in}," + "#{cEst_Concili_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void actualizarEstLogConciliacion(Map mapParametros);

    /**
     * Obtiene tipo de cambio
     * @author ERIOS
     * @since 19.12.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{nValorTipoCambio, mode=OUT, jdbcType=NUMERIC} := " + "FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}," + "#{cFecCambio_in}," + "#{cTipo}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getTipoCambio3(Map<String, Object> mapParametros);

    /**
     * @author ERIOS
     * @since 2.2.8
     * @param mapParametros
     */
    @Select(value =
            "{call PTOVENTA_RECAUDACION.GUARDAR_LOG_RECARGAS(" + "#{cCodGrupoCia}," + "#{cCodCia}," + "#{cCodLocal}," +
            "#{cNumPedVta}," + "#{vCodIdConcetrador}," + "#{vNumeroTelefono}," + "#{vCodAutorizacion}," +
            "#{vCodVendedor}," + "#{vFechaVenta}," + "#{vHoraVenta}," + "#{vNumeroDocumento}," + "#{vCodComercio}," +
            "#{vCodTerminal}," + "#{nMontoVenta}," + "#{vIdTransaccion}," + "#{vUsuCrea}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void guardarLogRecargas(Map<String, Object> mapParametros);

    /**
     * @author ERIOS
     * @since 2.2.8
     * @param mapParametros
     */
    @Select(value =
            "{call PTOVENTA_RECAUDACION.SET_ESTADO_LOG_RECARGAS(" + "#{cCodGrupoCia}," + "#{cCodCia}," + "#{cCodLocal}," +
            "#{cNumPedVta}," + "#{vEstConciliacion_in}," + "#{vUsuMod_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void actualizarEstadoLogRecargas(Map<String, Object> mapParametros);

    @Select(value =
            "{call #{vCod_Recau, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_RECAUDACION.RCD_CORRE_COD_RECAU(" + "#{cCodGrupoCia_in}," +
            "#{cCod_Cia_in}," + "#{cCod_Local_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCodRecauAnul(Map mapParametros);
}
