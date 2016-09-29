package mifarma.ptoventa.caja.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MapperCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface MapperCaja {

    /**
     * REGISTRAR UNA VENTA CON TARJETA CMR
     * @author GFonseca
     * @since 31.07.2013
     * @param mapParametros
     */
    @Select(value =
            "{call  #{tmpCodigo, mode=OUT, jdbcType=BIGINT} := " + "ADMCENTRAL_RECAUDACION.RCD_REGTR_VENTA_CMR(" +
            "#{cCodGrupoCia_in}," + "#{cCod_Cia_in}," + "#{cCod_Local_in}," + "#{cTip_Msj_in}," +

            "#{cEst_Trscc_in}," + "#{cTipo_Trssc_in}," + "#{cTipo_Rcd_in}," +

            "#{cNro_Trjt_in}," + "#{cMonto_in}," + "#{cTerminal_in}," +

            "#{cComercio_in}," + "#{cUbicacion_in}," + "#{cNro_Cuotas_in}," +

            "#{cId_cajero_in}," + "#{cNro_Doc}," + "#{cUsu_Crea_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void registrarTrsscVentaCMR(Map mapParametros);


    /**
     * OBTIENE LAS OPCIONES BLOQUEADAS DEL SISTEMA
     * @author CVILCA
     * @since 18.10.2013
     * @param mapParametros
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_GRAL.GET_OPCION_BLOQUEADA(" +
            "#{cCodGrupoCia_in}," + "#{cCodCia_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerOpcionesBloqueadas(Map mapParametros);

    /**
     * Obtener las formas de pago de un pedido, para abrir la gabeta en caso sea efectivo.
     * @author GFonseca
     * @since 27.Dic.2013
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_CAJ.GET_FPAGO_PEDIDO(" +
            "#{cNum_Pedido_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getFormasPagoPedido(Map mapParametros);


    /**
     * Proceso Cobro.
     * @author RHERRERA
     * @since 04.Abri.2014
     */
    @Select(value =
            "{ call #{valor_out, mode=OUT, jdbcType=NUMERIC} " + ":= PTOVENTA_CAJ_COBRO.CAJ_PROC_COBRO_PED(" + "#{V_ERROR_MENSAJE_out,  mode=OUT, jdbcType=VARCHAR}," +
            "#{V_NUC_SEC_out,        mode=OUT, jdbcType=NUMERIC}," +
            //"#{V_SEC_COM_PAGO_out,   mode=OUT, jdbcType=CURSOR, resultMap=resultado }," +
            //ARRAYS//
            "#{a_CodFormaPago, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_monto, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_CodMoneda, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_XXX, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_ImpTotal, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_NumTarjeta, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_FecVecTarjeta, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_NomCliTarjeta, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_CantCupon, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_DniTarjeta, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_CodBouch, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            "#{a_CodLote, mode=IN, typeHandler=mifarma.ptoventa.reference.ArrayTypeHandler}, " +
            //ARRAYS FIN//
            "#{C_COD_GRUPOCIA_IN}," + "#{C_COD_LOCAL_IN}," + "#{C_NUM_PTO_VTA_IN}," + "#{C_TIP_COM_PED_IN}," +
            "#{C_USU_CAJA}," + "#{vIndPedidoSeleccionado}," + "#{vNuSecUsu}," + "#{vRuc_Cli_Ped}," +
            "#{vCod_Cli_Local}," + "#{vNom_Cli_Ped}," + "#{vDir_Cli_Ped}," + "#{VDni_Fid}," + "#{vNumTarjeta_Fidel}," +
            "#{pTipConsulta}," + "#{cCodNumera_in}," + "#{cCodMotKardex_in}," + "#{cTipDocKardex_in}," +
            "#{cCodNumeraKardex_in}," + "#{cDescDetalleForPago_in}," + "#{cPermiteCampana}," + "#{cValVueltoPedido}," +
            "#{cTipoCambio}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarNuevoCobro_m(Map mapParametros);

    @Select(value =
            "{call FARMA_SECURITY.SET_MODULO_VERSION(" +
            "#{cCodGrupoCia_in},#{cCodCia_in},#{cCodLocal_in},#{vModulo_in},#{vVersion_in},#{vCompilacion_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void setVersion(HashMap<String, Object> object);
}
