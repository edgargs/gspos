package mifarma.electronico.impresion.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface MapperComprobanteElectronico {
    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Obtiene datos de la cabecera de un comprobante electronico
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_EPOS.FN_IMPRIMIR_CABECERA(" +
            "#{cGrupoCia}," + "#{cCodLocal}," + "#{cNumPedidoVta}," + "#{cSecCompPago}," + "#{cTipoDocumento}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeCabecera(Map mapParametros);


    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Obtiene datos del detalle  de un comprobante electronico
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_EPOS.FN_IMPRIMIR_DETALLE(" +
            "#{cGrupoCia}," + "#{cCodLocal}," + "#{cNumPedidoVta}," + "#{cSecCompPago}," + "#{cTipoClienteConvenio}" +
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeDetalle(Map mapParametros);

    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Obtiene los montos globales del comprobanten electronico.
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_EPOS.FN_IMPRIMIR_MONTOS_GLOBAL(" +
            "#{cGrupoCia}," + "#{cCodLocal}," + "#{cNumPedidoVta}," + "#{cSecCompPago}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeMontos(Map mapParametros);

    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Obtiene los datos de convenio.
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CHAR} := " + "FARMA_EPOS.FN_IMPRIMIR_MENSAJE_CONVENIO(" + "#{cGrupoCia}," +
            "#{cCodLocal}," + "#{cNumPedidoVta}," + "#{cSecCompPago}," + "#{cTipoDocumento}," +
            "#{cTipoClienteConvenio}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeDatosConvenio(Map mapParametros);

    /**
     *
     * @Author:Cesar_Huanes
     * @Descripcion: Lista los datos del pie  de pagina.
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT,jdbcType=CHAR} := " + "FARMA_EPOS.FN_IMPRIMIR_PIE_PAGINA(" + "#{cGrupoCia}," +
            "#{cCodLocal}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimePiePagina(Map mapParametros);


    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Obtiene datos de la nota del documento (monto en letras)
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_EPOS.FN_IMPRIME_NOTAS(" +
            "#{cGrupoCia}," + "#{cCodLocal}," + "#{cNumPedidoVta}," + "#{cSecCompPago}," + "#{cTipoDocumento}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeNotas(Map mapParametros);

    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Obtiene las formas de pago del pedido
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_EPOS.IMPRIME_FORMA_PAGO(" +
            "#{cCodGrupoCia}," + "#{cCodLocal}," + "#{cNumPedidoVta}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeFormaPago(Map mapParametros);

    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Lista dato de delibery
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_EPOS.IMPRIME_DATOS_DELIVERY(" +
            "#{cCodGrupoCia}," + "#{cCodLocal}," + "#{cNumPedidoVta}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeDatosDelivery(Map mapParametros);

    /**
     * @param mapParametros
     * @Author:Cesar_Huanes
     * @Descripcion: Lista Datos de Empresa Mayorista
     * @Fecha:21 /08/2014
     */
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "FARMA_EPOS.IMPRIME_DATOS_EMPRESA(" +
            "#{cCodGrupoCia}," + "#{cCodLocal}," + "#{cNumPedidoVta}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void imprimeDatosMayorista(Map mapParametros);


}
