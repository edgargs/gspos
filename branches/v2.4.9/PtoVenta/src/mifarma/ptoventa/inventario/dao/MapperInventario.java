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
    +"PTOVENTA_MDIRECTA.DETALLE_ORDEN_COMPRA(" + 
                    "#{vCodGrupoCia},"+
                    "#{vCodCia},"+
                    "#{vCodLocal},"+
                    "#{vCodOrdenComp_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListarProdOrdenCompra(Map mapParametros);
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "
    +"PTOVENTA_MDIRECTA.GET_DET_OC_SOBRANTE(" + 
                    "#{vCodGrupoCia},"+
                    "#{vCodCia},"+
                    "#{vCodLocal},"+
                    "#{vCodOrdenComp_in}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getListarProdOrdenCompraSobrante(Map mapParametros);   //ASOSA - 19/08/2014
    
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
                        "#{cNumer_Docm_in}," +
                        "#{nSecOCRcep}" +    
    
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
                        "#{cCodMotTransInterno_in}," +
                        "#{codOc}" +
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
                        "#{secRespaldo}," +
                        "#{cantExceso}," +
                        "#{cantUsadaExceso}," +
                        "#{numeroOc}," +
                        "#{secuencialDetalle}" +
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
                        "#{vUsu_in}," +
                        "#{nCantSolicitado}," +
                        "#{nOrdenCompra}" +
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
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Lista Maestro de Productos
     * @Fecha: 23/12/2013
     */   
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+
                       "PTOVENTA_MDIRECTA.LISTA_MAESTRO_PRODUCTOS("+
                       "#{cCodGrupoCia_in}," +   
                       "#{cCod_Local_in}" +
                       ")}")
     @Options(statementType = StatementType.CALLABLE)
    void getListaMaestroProductos(Map mapParametros);
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Busca Producto por Descripcion
     * @Fecha: 23/12/2013
     */  
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+ 
                  "PTOVENTA_MDIRECTA.BUSCA_PRODUCTO_DESC(" + 
                    "#{cCodGrupoCia_in},"+
                    "#{cCod_Local_in},"+                  
                    "#{vDescripcion}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void  buscaProductoDescripcion(Map mapParametros);
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Busca Producto por Codigo de Barra
     * @Fecha: 23/12/2013
     */
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := "+ 
                  "PTOVENTA_MDIRECTA.F_GET_PRODUC_COD_BARRA(" + 
                    "#{cCodGrupoCia},"+
                    "#{cCodLocal},"+                  
                    "#{cCodBarra}" + ")}")
    @Options(statementType = StatementType.CALLABLE)
    void obtenerProductoCodigobarra(Map mapParametros);
    
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Graba la tabla lgt_oc_cab_recep
     * @Fecha: 30/12/2013
     */
    @Select(value = "{call PTOVENTA_MDIRECTA.INV_GRABA_DEVOLUCION(" +
                        "#{cCodGrupoCia_in}," + 
                        "#{cCod_Cia_in}," + 
                        "#{cCod_Local_in}," +
                        "#{cCodOC_in}," +
                        "#{cCod_Prov_in}," +
                         "#{cCant_Item_in}," +
                        "#{cIdUsu_in}," + 
                        "#{cNum_Nota_in}" +                  
                    ")}")
    @Options(statementType = StatementType.CALLABLE)
    void grabarCabRecep(Map mapParametros);  
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: Actualizar datos en el legacy
     * @Fecha: 31/12/2013
     */
    @Select(value = "{call COMERCIAL.PACTUALIZAR_GUIA_DEVOD( "  +
                        "#{PINDIGUIA }," +
                        "#{PCODILOCA}," + 
                        "#{PNUMEGUIA}," +  
                        "#{PNOMBPROV}," +  
                        "#{PCODIMIFA}," + 
                        "#{PCANTRECI}," +
                        "#{Salida, mode=OUT, jdbcType=VARCHAR}" +  
                   ")}")   
    @Options(statementType = StatementType.CALLABLE)
    void actualizarOrdenDevolucion(Map mapParametros);
    
    
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
            "PTOVENTA_MDIRECTA.GET_GUIA_NOENVIO_LEGACY("+
            "#{cCodGrupoCia_in}," +
            "#{cCodCia_in}," +
            "#{cCodLocal_in}," +
           "#{cNum_Nota}" +     
                  
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getGuiaNoEnvioLegacy(Map mapParametros);
    
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
            "PTOVENTA_MDIRECTA.GET_DET_GUIA_LEGACY("+
            "#{cCodGrupoCia_in}," +
            "#{cCodCia_in}," +
            "#{cCodLocal_in}," +
           "#{cNum_Nota}," + 
            "#{cSec_Guia}" +  
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void getGuiaDetLecacy(Map mapParametros);
    
    
    @Select (value="{call " + 
                   "PTOVENTA_MDIRECTA.ACTUALIZAR_INDICADOR("+
                    "#{cCodGrupoCia_in},"+
                     "#{cCod_Cia_in},"+
                    "#{cCodLocal_in},"+
                    "#{cNumGuia},"+ 
                   "#{cSec_Guia}" +  
                   ")}")
    @Options(statementType = StatementType.CALLABLE)  
    void actualizaIndicador(Map mapParametros);
    
    @Select (value="{call " + 
                   "PTOVENTA_MDIRECTA.ACTUALIZA_NUMNOTA("+
                    "#{cCodGrupoCia},"+
                    "#{cCodCia_in},"+
                     "#{cCodLocal},"+
                    "#{cNumNota},"+
                    "#{cCodOC},"+
                   "#{nSecOCRecep}"+
    
                   ")}")
    @Options(statementType = StatementType.CALLABLE)  
    void actualizaNumNota(Map mapParametros);
    
    
    /**
     * @Author:Cesar_Huanes
     * @Descripcion: valida el numero de ajuste para en la anulacion de una o/c
     * @Fecha: 25.02.2014
     */
    @Select(value="{call #{Respuesta, mode=OUT, jdbcType=CHAR} := " +
              "PTOVENTA_MDIRECTA.VALIDA_NUM_AJUSTE("+
               "#{cCodGrupoCia}," +
               "#{cCodLocal}," +
               "#{cCodProd}," +    
               "#{cNumComPago}," +
               "#{cCodMotkardex}," +
               "#{cTipDockardex}" +  
              ")}")
      @Options(statementType = StatementType.CALLABLE)
    void getNumAjuste(Map mapParametros);
   
    /**
     * @Author:Ricardo_Herrera
     * @Descripcion: Obtiene el indicador de estado en la OC
     * @Fecha: 25.02.2014
     */
    @Select(value="{call #{Respuesta, mode=OUT, jdbcType=CHAR} := " +
              "PTOVENTA_MDIRECTA.IND_CIERRE_ORD_COMPRA("+
               "#{cCodGrupoCia_in}," +
               "#{cCodLocal_in}," +
               "#{cNumerOC_in}" +    
              ")}")
      @Options(statementType = StatementType.CALLABLE)
    void getIndicador_OC(Map mapParametros);
    
    

    
}
