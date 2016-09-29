package mifarma.ptoventa.mayorista.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface MapperMayorista {
    
    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_PROFORMA.F_LISTA_CAB_PEDIDOS(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cTipoVenta_in}," + 
            "#{cEstadoProforma_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getListaProformas(HashMap<String, Object> object);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_PROFORMA.F_LISTA_DETALLE_PEDIDOS(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cCodLocalAtencion_in}," + 
            "#{cNumPedido_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void getDetalleProforma(HashMap<String, Object> object);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_PROFORMA.F_LISTA_DETALLE_PEDIDOS_INST(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cCodLocalAtencion_in}," + 
            "#{cNumPedido_in}," + 
            "#{cFlagFiltro_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void cargaListaDetPedidos(HashMap<String, Object> object);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_DEL_CLI.CLI_LISTA_PROD_LOTE_SEL(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cNumPedido_in}," + 
            "#{cCodLocalAtencion_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtieneProductosSeleccionTotalLote(HashMap<String, Object> object);

    @Select(value =
            "{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " + "PTOVENTA_DEL_CLI.CLI_LISTA_INST_DET_PROD_LOTE(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cNumPedido_in}," + 
            "#{cCodProd_in}," + 
            "#{nSecDetPed_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void cargaListaDetProductoLote(HashMap<String, Object> object);

    @Select(value =
            "{call " + "PTOVENTA_DEL_CLI.CLI_ELIMINA_PED_INST_PROD_LOTE(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cNumPedido_in}," + 
            "#{cCodProd_in}," + 
            "#{nSecDetPed_in}," + 
            "#{cNumLote_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void eliminaDetalleProducto(HashMap<String, Object> object);

    @Select(value =
            "{call " + "PTOVENTA_DEL_CLI.CLI_ELIMINA_VTA_INSTI_DET(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cNumPedido_in}," + 
            "#{cCodProd_in}," + 
            "#{nSecDetPed_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void eliminaDetalleProductoLotes(HashMap<String, Object> object);

    @Select(value =
            "{call " + "PTOVENTA_DEL_CLI.CLI_AGREGA_VTA_INSTI_DET(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cNumPedido_in}," + 
            "#{cCodProd_in}," + 
            "#{cNumLote_in}," + 
            "#{cCant_in}," + 
            "#{cUsuCrea_in}," + 
            "#{cFechaVencimiento_in}," + 
            "#{nSecDetPed_in}," + 
            "#{nValFracVta_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void agregaDetalleProductoLote(HashMap<String, Object> object);
    
    @Select(value =
            "{call " + "PTOVENTA_PROFORMA.P_ANULAR_PROFORMA(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCodLocal_in}," + 
            "#{cNumPedido_in}," + 
            "#{cUsuCrea_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void anularProforma(HashMap<String, Object> object);
    
    @Select(value =
            "{call #{resultado, mode=OUT, jdbcType=CHAR} := " + "PTOVENTA_VTA_MAYORISTA.OBTIENE_PRECIO_MINIMO(" +
            "#{cCodGrupoCia_in}," + 
            "#{cCod_Local_in}," + 
            "#{cCodProd_in}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    public void obtienePrecioMinimoVenta(HashMap<String, Object> object);
        
}
