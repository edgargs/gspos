package mifarma.ptoventa.inventario.dao;

import java.util.HashMap;
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
 * LRUIZ      17.05.2013   Creación<br>
 * <br>
 * @author Luis Ruiz Peralta<br>
 * @version 1.0<br>
 *
 */
public interface MapperInventario {
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+ 
                  "PTOVENTA_MDIRECTA.LISTA_ORDEN_COMPRA_CAB(" + 
                    "#{cCodGrupoCia_in},"+
                    "#{cCodCia_in},"+
                    "#{cCodLocal_in},"+                  
                    "#{vFechaIni_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaOrdenCompra(Map mapParametros);
    
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+ 
                   "PTOVENTA_MDIRECTA.LISTA_CAB_ORDEN_COMPRA(" + "#{vCod_OC_in}" + ")}")
     @Options(statementType = StatementType.CALLABLE)
     void getListaCabOrdenCompra(Map mapParametros);
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "
    +"PTOVENTA_MDIRECTA.DETALLE_ORDEN_COMPRA(" + "#{vCodOrdenComp_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListarProdOrdenCompra(Map mapParametros);
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
                  "PTOVENTA_MDIRECTA.LISTAR_DETALLE_ORDEN_COMPRA(" +
                  "#{vCodProducto_in}" +
                  ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getInformacionProducto(Map mapParametros);
    
    @Select(value = "{call PTOVENTA_MDIRECTA.MDIR_GRABA_OC_DET_RECEP(" +
                        "#{cCodGrupoCia_in}," + 
                        "#{cCod_Cia_in}," + 
                        "#{cCod_Local_in}," +
                        "#{vId_User_in}," +
                        "#{cNumer_Ord_Comp_in}," +
                        "#{cCod_Prod_in}," + 
                        "#{nCant_Solict_in}," +
                        "#{nCant_Recep_in}," + 
                        "#{nPrecio_Unit_in}," + 
                        "#{nIGV_in}," +
                        "#{nCant_Recep_Total_in}," +
                        "#{nSec_Det_in}," +
                        "#{cId_Docum_in}," +                                              
                        "#{cSerie_Docm_in}," +
                        "#{cNumer_Docm_in}" +
                    ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarDetRecep(Map mapParametros);   
    
    //CVILCA 25.10.2013
    @Select(value = "{call COMERCIAL.PACTUALIZAR_CODOORDECOMPDETA( "  +
                        "#{PNUMEORDECOMP}," +
                        "#{PCODIMIFA}," + 
                        "#{PCANTRECI}," +                                              
                        "#{Salida, mode=OUT, jdbcType=VARCHAR}" +  
                   ")}")   
    @Options(statementType = StatementType.CALLABLE)
    void actualizarOrdenCompra(Map mapParametros);
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
                  "PTOVENTA_MDIRECTA.LISTAR_DET_ORDEN_COMPRA_RECEP(" +
                  "#{cCodGrupoCia_in},"+
                  "#{cCodCia_in},"+
                  "#{cCodLocal_in},"+
                  "#{cCod_OC_in},"+
                  "#{nSecCab_in}"+
                  ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listarProdDetRecep(Map mapParametros);
    
    
    @Select(value = "{call #{valorB, mode=OUT, jdbcType=CHAR} := "  +
                    "PTOVENTA_MDIRECTA.MDIR_GRABA_OC_CAB_RECEP(" +
                        "#{cCodGrupoCia_in}," + 
                        "#{cCod_Cia_in}," + 
                        "#{cCod_Local_in}," + 
                        "#{vId_User_in}," + 
                        "#{cNumer_Ord_Comp_in}," +
                        "#{cfecha_in}," + 
                        "#{cId_Docum_in}," +                                              
                        "#{cSerie_Docm_in}," +
                        "#{cNumer_Docm_in}," +
                        "#{nCant_Item_in}," +
                        "#{cCod_Prov_in}," +
                        "#{nImport_Total_in}," +
                        "#{nImport_Parc_in}," +
                        "#{nRedondeo_in}" +  
                   ")}")   
    @Options(statementType = StatementType.CALLABLE)
    void grabarCabDetRecep(Map mapParametros);  
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+
                        "PTOVENTA_MDIRECTA.INV_LISTA_DEVOLUCION("+
                        "#{cGrupoCia_in}," + 
                        "#{cCia_in}," + 
                        "#{cCodLocal_in}," + 
                        "#{vFiltro_in}," + 
                        "#{cTipoOrigen}" +                      
                        ")}")
        @Options(statementType = StatementType.CALLABLE)
    void getListarDevoluciones(Map mapParametros);  
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+    
                        "PTOVENTA_INV.INV_GET_DET_TRANSFERENCIA(" +
                        "#{cCodGrupoCia_in}," +
                        "#{cCodLocal_in}," +
                        "#{cNumNota_in}" +
                        ") }")
    @Options(statementType = StatementType.CALLABLE)    
    void cargaDetalleTransferencia(Map<String,Object> mapParametros);
        
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+
                        "PTOVENTA_MDIRECTA.INV_GET_ORDEN_COMPRA("+
                        "#{cGrupoCia_in}," + 
                        "#{cCodLocal_in}," +
                        "#{cProv_in}" +
                        ")}")
        @Options(statementType = StatementType.CALLABLE)
    void getListarOrdenesCompra(Map mapParametros);  
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+ 
                  "PTOVENTA_MDIRECTA.DETALLE_ORDEN_COMPRA_RECEP(" + "#{vOrdenComp_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaDetOrdenCompra(Map mapParametros);
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+
                       "PTOVENTA_MDIRECTA.OBTENER_DETALLE_PRODUCTOS_OC("+
                       "#{cCodOrdenCompr}," +                  
                       "#{cBuscar}" + 
                       ")}")
       @Options(statementType = StatementType.CALLABLE)
    void getListaProductosPorOrdenCompra(Map mapParametros);   
    
    @Select(value="{call #{numeroNotaES, mode=OUT, jdbcType=CHAR} := "+    
                        "PTOVENTA_INV.INV_AGREGA_CAB_TRANSFERENCIA(" +
                        "#{cCodGrupoCia_in}," +
                        "#{cCodLocal_in}," +
                        "#{vTipDestino_in}," +
                        "#{cCodDestino_in}," +
                        "#{cTipMotivo_in}," +
                        "#{vDesEmp_in}," + 
                        "#{vRucEmp_in}," + 
                        "#{vDirEmp_in}," +
                        "#{vDesTran_in}," + 
                        "#{vRucTran_in}," + 
                        "#{vDirTran_in}," +
                        "#{vPlacaTran_in}," +
                        "#{nCantItems_in}," +
                        "#{nValTotal_in}," +
                        "#{vUsu_in}," +
                        "#{cCodMotTransInterno_in}" +
                        ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarCabeceraNotaSalida(Map<String,Object> mapParametros);
    
    @Select(value = "{call PTOVTA_RESPALDO_STK.INV_AGREGA_DET_TRANSFERENCIA (" +
                        "#{cCodGrupoCia_in}," +
                        "#{cCodLocal_in}," +
                        "#{cNumNota_in}," +
                        "#{cCodProd_in}," +
                        "#{nValPrecUnit_in}," +
                        "#{nValPrecTotal_in}," +
                        "#{nCantMov_in}," +
                        "#{vFecVecProd_in}," +
                        "#{vNumLote_in}," +
                        "#{cCodMotKardex_in}," +
                        "#{cTipDocKardex_in}," +
                        "#{vValFrac_in}," +
                        "#{vUsu_in}," +
                        "#{vTipDestino_in}," +
                        "#{cCodDestino_in}," +
                        "#{vIndFrac_in}," +
                        "#{secRespaldo}" +
                    ")}")                     
    @Options(statementType = StatementType.CALLABLE)
    void grabarDetalleNotaSalida(Map<String,Object> mapParametros);
    
    @Select(value = "{call PTOVENTA_INV.INV_GENERA_GUIA_TRANSFERENCIA (" +
                        "#{cGrupoCia_in}," +
                        "#{cCodLocal_in}," +
                        "#{cNumNota_in}," +
                        "#{nCantMAxDet_in}," +
                        "#{nCantItems_in}," +
                        "#{cIdUsu_in}" +
                    ")}")                     
    @Options(statementType = StatementType.CALLABLE)
    void generarGuiaSalida(Map<String,Object> mapParametros);
    
    @Select(value = "{call #{numeroNotaES, mode=OUT, jdbcType=CHAR} := " +
                        "PTOVENTA_INV.INV_AGREGA_CAB_GUIA_INGRESO(" +
                            "#{cCodGrupoCia_in}," + 
                            "#{cCodLocal_in}," + 
                            "#{vFechaGuia_in}," + 
                            "#{cTipDoc_in}," + 
                            "#{cNumDoc_in}," +
                            "#{cTipOrigen_in},"+ 
                            "#{vCodOrigen_in},"+                                              
                            "#{nCantItems_in},"+
                            "#{nValTotal_in},"+
                            "#{vNombreTienda_in}," +
                            "#{vCiudadTienda_in}," +
                            "#{vRucTienda_in}," +
                            "#{vUsu_in}" +                       
                    ")}")       
    @Options(statementType = StatementType.CALLABLE)
    void agregaCabGuiaIngreso(Map mapParametros);  
    
    @Select(value = "{call PTOVENTA_INV.INV_AGREGA_DET_GUIA_INGRESO(" +
                        "#{cCodGrupoCia_in}," + 
                        "#{cCodLocal_in}," + 
                        "#{cNumNota_in}," + 
                        "#{cTipOrigen_in}," + 
                        "#{cCodProd_in}," +
                        "#{nValPrecUnit_in},"+ 
                        "#{nValPrecTotal_in},"+                                              
                        "#{nCantMov_in},"+
                        "#{vFecNota_in},"+
                        "#{vFecVecProd_in}," +
                        "#{vNumLote_in}," +
                        "#{cCodMotKardex_in}," +
                        "#{cTipDocKardex_in}," +
                        "#{vValFrac_in}," +
                        "#{vUsu_in}" +
                ")}")   
    @Options(statementType = StatementType.CALLABLE)
    void agregaDetGuiaIngreso(Map mapParametros);
        
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+ 
                   "PTOVENTA_MDIRECTA.LISTA_CAB_ORDEN_COMPRA_RECEP(" + 
                   "#{cCodGrupoCia_in}," + 
                   "#{cCodCia_in}," + 
                   "#{cCodLocal_in}," + 
                   "#{cCodOC_in}," + 
                   "#{nSecCab_in}" + 
                  ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getCabOrdenCompraRecep(Map mapParametros);
    
    @Select(value="{call #{Respuesta, mode=OUT, jdbcType=CHAR} := " + 
                  "PTOVENTA_MDIRECTA.LISTAR_NUM_GUIA_ANULAR_RECEP(" + 
                      "#{vCodGrupoCia_in}," + 
                      "#{vCodLocal_in}," +
                      "#{vNumerGuia_in}," + 
                      "#{vIdeDocumento_in}," + 
                      "#{vNumeroDocument_in}" +
                  ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anularRecepGuiaIngreso(Map mapParametros);
    
    @Select(value="{call PTOVENTA_INV.INV_ANULA_GUIA_INGRESO("+
                "#{cCodGrupoCia_in},"+
                "#{cCodLocal_in},"+
                "#{cNumNota_in},"+
                "#{cCodMotKardex_in},"+            
                "#{cTipDocKardex_in},"+
                "#{vIdUsu_in}"+
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void anulaDocumentoRecep(Map mapParametros);

    @Select(value="{call PTOVENTA_MDIRECTA.ANULAR_INGRESO_RECEPCION("+
            "#{cCodGrupoCia_in},"+
            "#{cCodCia_in},"+
            "#{cCodLocal_in},"+
            "#{cCodOC_in},"+
            "#{nSecCab_in}"+
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void cambiaEstadoCabRecep(Map mapParametros);
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
            "PTOVENTA_MDIRECTA.LISTAR_DOCUMEN_RECEP("+
            "#{cCodGrupoCia_in}," +
            "#{cCodCia_in}," +
            "#{cCodLocal_in}," +
            "#{cCodOrdComp_in}," +
            "#{cCodProv_in}" +
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaDocumtRecep(Map mapParametros);
    
    @Select (value="{call #{Respuesta, mode=OUT, jdbcType=CHAR} := " + 
                   "PTOVENTA_MDIRECTA.CIERRE_ORD_COMPRA("+
                    "#{cCodGrupoCia_in},"+
                    "#{cCodLocal_in},"+
                    "#{cNumerGuia_in}"+
                   ")}")
    @Options(statementType = StatementType.CALLABLE)
    void cierreOrdCompDocumento(Map mapParametros);
     
    @Select (value="{call " + 
                   "PTOVENTA_MDIRECTA.INV_CONFIRMAR_DEVOL("+
                    "#{cCodGrupoCia_in},"+
                    "#{cCodCia_in},"+
                    "#{cCodLocal_in},"+
                    "#{cNumNotaEs_in},"+
                    "#{vIdUsu_in}"+
                   ")}")
    @Options(statementType = StatementType.CALLABLE)
    void confirmarDevolucion(Map mapParametros);  
    
    //CVILCA 26.10.2013
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
                  "PTOVENTA_MDIRECTA.OBTENER_PRODUCTOS_POR_NOTA(" +
                                                          "#{cCodGrupoCia_in},"+
                                                          "#{cCodCia_in},"+
                                                          "#{cCod_Local_in},"+
                                                          "#{cNumOrdCom_in},"+
                                                          "#{vNumNota_in}"+
                                                          ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerProductosPorNota(Map mapParametros);
    
    
    /**
     * @Author:Cesar Huanes
     * @Descripcion:Lista Ordenes de compra por rango de fecha
     * @Fecha:----
     */
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+ 
                  "PTOVENTA_MDIRECTA.LISTA_ORDEN_COMPRA_CAB(" + 
                    "#{cCodGrupoCia_in},"+
                    "#{cCodCia_in},"+
                    "#{cCodLocal_in},"+    
                    "#{vFechaIni_in},"+
                    "#{vFechaFin_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListaOrdenCompraByFecha(Map mapParametros);

        /**
         * Obtiene el InnerPack de un producto (numero de unidades por paquete)
         * @autor GFONSECA
         * @since 10.12.2013
         * */
        @Select(value ="{call #{valInnerPack, mode=OUT, jdbcType=CHAR} := " +
                          "PTOVENTA_MDIRECTA.GET_PROD_INNER_PACK(" +
                                "#{cCodGrupoCia_in}," + 
                                "#{cCodProd_in}" + 
                            ")}")
        @Options(statementType = StatementType.CALLABLE)
        public void obtenerProdInnerPack(HashMap<String, Object> object);

    /**
     * @Author:Cesar_Huanes
     * @Descripcion:Obtiene datos del Local
     * @Fecha:16/12/2013
     */  
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
            "PTOVENTA_INV.INV_GET_DATOS_LOCAL("+
            "#{cCodGrupoCia_in}," +
            "#{cCodLocal_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void  getDatosLocal(Map mapParametros);
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Graba LGT_NOTA_ES_CAB
     * @Fecha: 16/12/2013
     */
    @Select(value = "{call #{valorB, mode=OUT, jdbcType=CHAR} := "+" PTOVENTA_INV.INV_AGREGA_CAB_TRANSFERENCIA(" +
                        "#{cCodGrupoCia_in}," + 
                        "#{cCodLocal_in}," + 
                        "#{vTipDestino_in}," + 
                        "#{cCodDestino_in}," + 
                        "#{cTipMotivo_in}," +
                        "#{vDesEmp_in},"+ 
                        "#{vRucEmp_in},"+                                              
                        "#{vDirEmp_in},"+
                        "#{vDesTran_in},"+
                        "#{vRucTran_in}," +
                        "#{vDirTran_in}," +
                        "#{vPlacaTran_in}," +
                        "#{nCantItems_in}," +
                        "#{nValTotal_in}," +
                        "#{vUsu_in}," +
                        "#{cCodMotTransInterno_in}" +                   
                ")}")   
    @Options(statementType = StatementType.CALLABLE)  
    void graba_Nota_Es_cab(Map<String,Object> mapParametros);

    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Graba Guias de Remision
     * @Fecha: 17/12/2013
     */
    @Select (value="{call " + 
                   "PTOVENTA_INV.INV_GENERA_GUIA_REMISION("+
                    "#{cCodGrupoCia_in},"+
                    "#{cCodLocal_in},"+
                    "#{cIdUsu_in},"+
                    "#{cNumNota_in}"+
                   
                   ")}")
    @Options(statementType = StatementType.CALLABLE)
    void generarGuiaRemision(Map mapParametros);
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Actualiza texto de Guia de Remision que no mueven stock
     * @Fecha: 17/12/2013
     */
    @Select (value="{call " + 
                   "PTOVENTA_INV.ACTUALIZA_TEXTO_IMPR("+
                    "#{cCodGrupoCia_in},"+
                    "#{cCodLocal_in},"+
                    "#{cNumNota},"+
                    "#{cTexto_Impr}"+                   
                   ")}")
    @Options(statementType = StatementType.CALLABLE)  
    void actualizaTexto(Map mapParametros);
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Lista Guia de Remision que no mueven stock
     * @Fecha: 19/12/2013
     */
     @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+
                         "PTOVENTA_INV.LISTA_GUIA_NO_MUEVE_STOCK("+
                         "#{cCodGrupoCia_in}," + 
                         "#{cCodLocal_in}" +
                         ")}")
         @Options(statementType = StatementType.CALLABLE)
    void getListaGuiaNoMuevenStock(Map mapParametros);
}
