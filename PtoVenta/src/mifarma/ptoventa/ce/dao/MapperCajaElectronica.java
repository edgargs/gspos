package mifarma.ptoventa.ce.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MapperCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.03.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface MapperCajaElectronica {

    @Select(value =
            "{call PTOVENTA_CAJ.SAVE_HIST_FORM_PAGO(#{strGrupoCia, mode=IN, jdbcType=CHAR},#{strCodLocal, mode=IN, jdbcType=CHAR}," +
            "#{strNumPed, mode=IN, jdbcType=CHAR},#{strUsuario, mode=IN, jdbcType=CHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void saveHistFormPago(@Param("strGrupoCia")
        String strGrupoCia, @Param("strCodLocal")
        String strCodLocal, @Param("strNumPed")
        String strNumPed, @Param("strUsuario")
        String strUsuario);

    @Select(value =
            "{call PTOVENTA_CAJ.DEL_FORM_PAGO_PED(#{strGrupoCia, mode=IN, jdbcType=CHAR},#{strCodLocal, mode=IN, jdbcType=CHAR}," +
            "#{strNumPed, mode=IN, jdbcType=CHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void elimiFormaPagoPedido(@Param("strGrupoCia")
        String strGrupoCia, @Param("strCodLocal")
        String strCodLocal, @Param("strNumPed")
        String strNumPed);

    @Select(value =
            "{call PTOVENTA_CAJ.CAJ_GRAB_NEW_FORM_PAGO_PEDIDO(#{cCodGrupoCia_in}," + "#{cCodLocal_in}," + "#{cCodFormaPago_in}," +
            "#{cNumPedVta_in}," + "#{nImPago_in}," + "#{cTipMoneda_in}," + "#{nValTipCambio_in}," +
            "#{nValVuelto_in}," + "#{nImTotalPago_in}," + "#{cNumTarj_in}," + "#{cFecVencTarj_in}," +
            "#{cNomTarj_in}," + "#{cCanCupon_in}," + "#{cUsuCreaFormaPagoPed_in}," + "#{cDNI_in}," +
            "#{cCodAtori_in}," + "#{cLote_in})}")
    @Options(statementType = StatementType.CALLABLE)
    void grabaFormaPagoPedido(@Param("cCodGrupoCia_in")
        String strGrupoCia, @Param("cCodLocal_in")
        String strCodLocal, @Param("cCodFormaPago_in")
        String strCodFormaPago, @Param("cNumPedVta_in")
        String strNumPed, @Param("nImPago_in")
        double dblImportePago, @Param("cTipMoneda_in")
        String strTipoMoneda, @Param("nValTipCambio_in")
        double dblTipoCambio, @Param("nValVuelto_in")
        double dblVuelto, @Param("nImTotalPago_in")
        double dblTotalPago, @Param("cNumTarj_in")
        String strNumTarjeta, @Param("cFecVencTarj_in")
        String strFechaTarjeta, @Param("cNomTarj_in")
        String strNomTarjeta, @Param("cCanCupon_in")
        int intCantCupon, @Param("cUsuCreaFormaPagoPed_in")
        String strUsuario, @Param("cDNI_in")
        String strDni, @Param("cCodAtori_in")
        String strCodVoucher, @Param("cLote_in")
        String strCodLote);

    @Select(value =
            "{ call #{vCodFonSencillo_out, mode=OUT, jdbcType=NUMERIC} := PTOVENTA_CE.CE_F_GRABA_REC_PAGO_SENCILLO(#{cCodGrupoCia_in}," +
            "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cFolio_in}," + "#{cTotal_in}," + "#{cMonto_in}," +
            "#{cDiferencia_in}," + "#{cTipFondoSencillo_in}," + "#{cCodETV_in}," + "#{cIdUsu_in} )}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarReciboPagoSencillo(Map mapParametros);

    @Select(value =
            "{ call #{vAbrir_out, mode=OUT, jdbcType=CHAR} := PTOVENTA_CE.CE_F_VERIFICA_REC_SENCILLO(#{cCodGrupoCia_in}," +
            "#{cCodCia_in}," + "#{cCodLocal_in}," + "#{cCodETV_in}," + "#{cTotal_out, mode=OUT, jdbcType=NUMERIC} )}")
    @Options(statementType = StatementType.CALLABLE)
    void verificaReciboSencillo(Map mapParametros);

    @Select(value =
            "{ call #{vCodETV_out, mode=OUT, jdbcType=CHAR} := PTOVENTA_CE.CE_F_GET_ETV(#{cCodGrupoCia_in}," + "#{cCodCia_in}," +
            "#{cCodLocal_in} )}")
    @Options(statementType = StatementType.CALLABLE)
    void getETV(Map mapParametros);

    /*
     *
     */

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_CE.CE_F_GET_LISTA_ETV}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaETVs(Map mapParametros);

    @Select(value =
            "{ call #{vMensaje, mode=OUT, jdbcType=VARCHAR} := PTOVENTA_CE.CE_F_IMP_FONDO_SENCILLO(#{cCodFonSencillo} )}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimirFondoSencillo(Map mapParametros);

    /**
     * Lista cajas aperturadas del dia
     * @author ERIOS
     * @since 2.2.8
     * @param mapParametros
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_CE_LMR.CE_MUESTRA_CAJA_APER(#{cCodGrupoCia_in}," +
            "#{cCodLocal_in}," + "#{cCierreDia_in})}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaCajasAperturadas(Map<String, Object> mapParametros);
}
