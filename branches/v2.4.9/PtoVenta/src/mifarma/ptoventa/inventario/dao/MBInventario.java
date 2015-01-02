package mifarma.ptoventa.inventario.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.producto.dao.MapperProducto;
import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.dto.NotaEsCabDetDTO;
import mifarma.ptoventa.inventario.dto.OrdenCompraCabDTO;
import mifarma.ptoventa.recetario.dao.MBRecetario;
import mifarma.ptoventa.recetario.dao.MapperRecetario;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.SqlSession;
import mifarma.ptoventa.inventario.dto.OrdenCompraCabDTO;
import mifarma.ptoventa.recaudacion.dao.MapperRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MBInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.05.2013   Creación<br>
 * <br>
 * @author Luis Ruiz Peralta<br>
 * @version 1.0<br>
 *
 */

  public class MBInventario implements DAOInventario{
    private static final Logger log    = LoggerFactory.getLogger(MBInventario.class);
    private SqlSession sqlSession   = null;
    private MapperInventario mapper = null;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    
    public MBInventario() {
        super();
    }
    
    public ArrayList<ArrayList<String>> listarOrdenesDeCompra(String pFecha) {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);        
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("vFechaIni_in", pFecha);
        openConnection();
        try{
            
            mapper.getListaOrdenCompra(mapParametros); 
        }finally{
            sqlSession.close();
        }
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }
       
    public ArrayList<ArrayList<String>> listarCabOrdComp(String pOrdeCompra) throws SQLException {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("vCod_OC_in", pOrdeCompra);
        openConnection();
        try{
            
            mapper.getListaCabOrdenCompra(mapParametros);
        }finally{
            sqlSession.close();
        }      
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }
    
    public ArrayList<ArrayList<String>> listarProdOrdenCompra(String pCodOrdCompra)throws SQLException{
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("vCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("vCodCia",FarmaVariables.vCodCia);
        mapParametros.put("vCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("vCodOrdenComp_in", pCodOrdCompra);
        openConnection();
        try{
            
            mapper.getListarProdOrdenCompra(mapParametros);          
        }finally{
            sqlSession.close();
        }        
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;

    }
    
    /**
     * @author ASOSA
     * @since 19/08/2014
     * @param pCodOrdCompra
     * @return
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> listarProdOrdenCompraSobrante(String pCodOrdCompra)throws SQLException{
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("vCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("vCodCia",FarmaVariables.vCodCia);
        mapParametros.put("vCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("vCodOrdenComp_in", pCodOrdCompra);
        openConnection();
        try{
            
            mapper.getListarProdOrdenCompraSobrante(mapParametros);          
        }finally{
            sqlSession.close();
        }        
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;

    }
    
    /**
     * 
     **/
    public ArrayList<ArrayList<String>> getCabOrdenCompraRecep(String pCodOrdComp, String pSecRecepcion)throws SQLException{
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        List<BeanResultado> resultado = null;
        ArrayList<ArrayList<String>> cabLstado;
               
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cCodOC_in", pCodOrdComp);
        mapParametros.put("nSecCab_in", pSecRecepcion);
        
        mapper.getCabOrdenCompraRecep(mapParametros);  
        
        resultado = (List<BeanResultado>)mapParametros.get("listado");       
        cabLstado = utilityPtoVenta.parsearResultadoMatriz(resultado);
        
        return cabLstado;
        
    }
    
    public ArrayList<ArrayList<String>> listarProdDetRecep(String pNumOrd,
                                                            String pSecRecep) throws Exception {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cCod_OC_in", pNumOrd);
        mapParametros.put("nSecCab_in", pSecRecep);
        
        mapper.listarProdDetRecep(mapParametros);
               
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
        
}
    
    //public ArrayList<ArrayList<String>> listarProductosPorOrdenCompra(NotaEsCabDetDTO ordenCompraCabDTO) {
    public ArrayList<ArrayList<String>> listarProductosPorOrdenCompra(String pNumeroOC,NotaEsCabDetDTO ordenCompraCabDTO) {
            
            List<BeanResultado> tmpLista = null;
            ArrayList tmpArray = new ArrayList();
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            //TODO codgrupocia
            //TODO codcia
            //TODO codlocal
            mapParametros.put("cCodOrdenCompr",pNumeroOC);
            mapParametros.put("cBuscar",ordenCompraCabDTO.getCodProd()==null?"%":ordenCompraCabDTO.getCodProd());
            getlistaProductosPorOrdenesCompra(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);    
            return tmpArray;
        }
    
    private void getlistaProductosPorOrdenesCompra(Map<String, Object> object) {
            openConnection();
            try{
                mapper.getListaProductosPorOrdenCompra(object);
            }finally{
                sqlSession.close();
            }
        }
    
    public String grabarCabeceraNotaSalida(NotaEsCabDTO notaEsCabDTO,
                                        String oc)throws SQLException{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("vTipDestino_in",notaEsCabDTO.getTipoOrigenNotaEs());
        mapParametros.put("cCodDestino_in",notaEsCabDTO.getCodDestinoNotaEs());
        mapParametros.put("cTipMotivo_in",notaEsCabDTO.getTipoMotiNotaEs());
        mapParametros.put("vDesEmp_in",notaEsCabDTO.getDescEmpresa()); 
        mapParametros.put("vRucEmp_in",notaEsCabDTO.getRucEmpresa()); 
        mapParametros.put("vDirEmp_in",notaEsCabDTO.getDireEmpresa());
        mapParametros.put("vDesTran_in",notaEsCabDTO.getDescTransp()); 
        mapParametros.put("vRucTran_in",notaEsCabDTO.getRucTransp()); 
        mapParametros.put("vDirTran_in",notaEsCabDTO.getDirTransp());
        mapParametros.put("vPlacaTran_in",notaEsCabDTO.getPlacaTransp());
        mapParametros.put("nCantItems_in",notaEsCabDTO.getCantItem());
        mapParametros.put("nValTotal_in",notaEsCabDTO.getValorTotalNotaEsCab());
        mapParametros.put("vUsu_in",FarmaVariables.vIdUsu);
        mapParametros.put("cCodMotTransInterno_in","");
        mapParametros.put("codOc",oc);
        mapper.grabarCabeceraNotaSalida(mapParametros);
        return (String)mapParametros.get("numeroNotaES");
    }
    
    public void grabarDetalleNotaSalida(String pNumera, NotaEsCabDTO notaEsCabDTO, NotaEsCabDetDTO notaEsCabDetDTO) throws SQLException{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNumNota_in",pNumera);
        mapParametros.put("cCodProd_in",notaEsCabDetDTO.getCodProd());
        mapParametros.put("nValPrecUnit_in",FarmaUtility.getDecimalNumber(notaEsCabDetDTO.getPrecVta()));
        mapParametros.put("nValPrecTotal_in",FarmaUtility.getDecimalNumber(notaEsCabDetDTO.getValPrecioTotal()));
        mapParametros.put("nCantMov_in",notaEsCabDetDTO.getCantMov());
        mapParametros.put("vFecVecProd_in",notaEsCabDetDTO.getFecVtoProd());
        mapParametros.put("vNumLote_in",notaEsCabDetDTO.getNumLoteProd());
        mapParametros.put("cCodMotKardex_in",ConstantsPtoVenta.MOT_KARDEX_SALIDA_PROVEEDOR);//MOTIVO DE KARDEX. notaEsCabDTO.getTipoMotiNotaEs()
        mapParametros.put("cTipDocKardex_in",ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        mapParametros.put("vValFrac_in",notaEsCabDetDTO.getValFraccion());
        mapParametros.put("vUsu_in",FarmaVariables.vIdUsu);
        mapParametros.put("vTipDestino_in",notaEsCabDTO.getTipoOrigenNotaEs());
        mapParametros.put("cCodDestino_in",notaEsCabDTO.getCodDestinoNotaEs());
        mapParametros.put("vIndFrac_in","N");
        mapParametros.put("secRespaldo",notaEsCabDetDTO.getSecRespalStock());
        mapParametros.put("cantExceso",notaEsCabDetDTO.getCantExceso());
        mapParametros.put("cantUsadaExceso",notaEsCabDetDTO.getCantUsadoExceso());        
        mapParametros.put("numeroOc",notaEsCabDetDTO.getCodigoOc());
        mapParametros.put("secuencialDetalle",notaEsCabDetDTO.getSecDetalle());
        log.info("PTOVTA_RESPALDO_STK.INV_AGREGA_DET_TRANSFERENCIA" + mapParametros);
        mapper.grabarDetalleNotaSalida(mapParametros);
    }
    
    public void generarGuiaSalida(String pNumera, int cantDetGuia, int cantItems) throws SQLException{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNumNota_in",pNumera);
        mapParametros.put("nCantMAxDet_in",cantDetGuia);
        mapParametros.put("nCantItems_in",cantItems);
        mapParametros.put("cIdUsu_in",FarmaVariables.vIdUsu);
        mapper.generarGuiaSalida(mapParametros);
    }
        
    public ArrayList<ArrayList<String>> listarDevoluciones(NotaEsCabDTO notaEsCabDTO) throws SQLException {            
            List<BeanResultado> tmpLista = null;
            ArrayList<ArrayList<String>> tmpArray;
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            mapParametros.put("cGrupoCia_in",notaEsCabDTO.getCodGrupoCia());
            mapParametros.put("cCia_in",notaEsCabDTO.getCodCia());
            mapParametros.put("cCodLocal_in",notaEsCabDTO.getCodLocal());
            mapParametros.put("vFiltro_in",notaEsCabDTO.getFiltro());
            mapParametros.put("cTipoOrigen",notaEsCabDTO.getTipoOrigenNotaEs());
            getlistaDevolucion(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);        
            return tmpArray;
        }   
                
    public ArrayList<ArrayList<String>> cargaDetalleTransferencia(String pNumeroNota) throws SQLException{
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNumNota_in",pNumeroNota);
        openConnection();
        try{
        mapper.cargaDetalleTransferencia(mapParametros);
        }finally{
        sqlSession.close();
        }
        tmpLista = (List<BeanResultado>)mapParametros.get("listado");
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);        
        return tmpArray;
    }
    
    
    
     public ArrayList listarOrdenesCompra(OrdenCompraCabDTO ordenCompraCabDTO) {
            List<BeanResultado> tmpLista = null;
            ArrayList tmpArray = new ArrayList();
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            mapParametros.put("cGrupoCia_in",ordenCompraCabDTO.getCodGrupoCia());
            mapParametros.put("cCodLocal_in",ordenCompraCabDTO.getCodLocal());
            mapParametros.put("cProv_in",ordenCompraCabDTO.getCodProv());
            getlistaOrdenesCompra(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
            return tmpArray;
        }

    public String grabarCabDetRecep(String vCodGrupoCia, String vCodCia, String vCodLocal, String vIdUsu
                                     , String vNumOrdenCompra, String vFechIngreso, String vIdeDocumento
                                     , String vSerieDocument, String vNumeroDocument, String vCantItem
                                     , String vCodProveedor, String vImporteTotal, String vImportRecep
                                     , String vRedondeo) throws SQLException{
        String bEstado = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();        
        mapParametros.put("cCodGrupoCia_in",vCodGrupoCia);
        mapParametros.put("cCod_Cia_in",vCodCia);
        mapParametros.put("cCod_Local_in",vCodLocal);
        mapParametros.put("vId_User_in",vIdUsu);
        mapParametros.put("cNumer_Ord_Comp_in",vNumOrdenCompra);
        mapParametros.put("cfecha_in", vFechIngreso);  
        mapParametros.put("cId_Docum_in", vIdeDocumento);              
        mapParametros.put("cSerie_Docm_in",vSerieDocument);
        mapParametros.put("cNumer_Docm_in",vNumeroDocument);
        mapParametros.put("nCant_Item_in",vCantItem);
        mapParametros.put("cCod_Prov_in",vCodProveedor);
        mapParametros.put("nImport_Total_in",vImporteTotal);
        mapParametros.put("nImport_Parc_in",vImportRecep);
        mapParametros.put("nRedondeo_in",vRedondeo);  
        
        mapper.grabarCabDetRecep(mapParametros);    
        bEstado = (String)mapParametros.get("valorB");
        return bEstado;
        
    }

    public void grabarDetRecep(String vCodGrupoCia, String vCodCia, String vCodLocal, String vIdUsu
                                  ,String vNumOrdenCompra, String vCodProducto, String vCantPedida
                                  ,String vCantEntregada, String vPrecioUnit
                                  ,String vPrecioIGV, String vImportRecep, String vSegOC, String vIdeDocumento
                                  ,String vSerieDocument, String vNumeroDocument,String vSecOC ) throws SQLException{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in",vCodGrupoCia);
        mapParametros.put("cCod_Cia_in",vCodCia);
        mapParametros.put("cCod_Local_in",vCodLocal);
        mapParametros.put("vId_User_in",vIdUsu);
        mapParametros.put("cNumer_Ord_Comp_in",vNumOrdenCompra);
        mapParametros.put("cCod_Prod_in",vCodProducto);
        mapParametros.put("nCant_Solict_in",vCantPedida);
        mapParametros.put("nCant_Recep_in",vCantEntregada);
        mapParametros.put("nPrecio_Unit_in",vPrecioUnit);
        mapParametros.put("nIGV_in",vPrecioIGV);
        mapParametros.put("nCant_Recep_Total_in", vImportRecep);
        mapParametros.put("nSec_Det_in", vSegOC);
        mapParametros.put("cId_Docum_in", vIdeDocumento);              
        mapParametros.put("cSerie_Docm_in",vSerieDocument);
        mapParametros.put("cNumer_Docm_in",vNumeroDocument);
        mapParametros.put("nSecOCRcep",vSecOC.trim());
        
        log.debug("grabarDetRecep AGREGA DETALLE: " + mapParametros);
        log.info("grabarDetRecep AGREGA DETALLE: " + mapParametros);
        mapper.grabarDetRecep(mapParametros);
    }
    
    //CVILCA 25.10.2013
    public String actualizarOrdenCompra(String vNumOrdenCompra , String vCodProducto,
                                      Integer vCantEntregada) throws SQLException{
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            String salida = "";
            mapParametros.put("PNUMEORDECOMP",vNumOrdenCompra);
            mapParametros.put("PCODIMIFA",vCodProducto);
            mapParametros.put("PCANTRECI",vCantEntregada);
            SqlSession sqlFasaSession = MyBatisUtil.getFasaSqlSessionFactory().openSession();
            try{   
                MapperInventario mapperFasa = sqlFasaSession.getMapper(MapperInventario.class);
                mapperFasa.actualizarOrdenCompra(mapParametros);
                salida = (String)mapParametros.get("Salida");
            }catch(Exception e){   
                log.error("",e);
                sqlFasaSession.rollback();
                throw e;
            }finally{   
                sqlFasaSession.close();            
            }
            return salida;
        }
    
    public String agregaCabGuiaIngreso(String CodGrupoCia, String CodLocal, String FechaGuia, String TipDoc,
                                       String NumDoc, String TipOrigen, String CodOrigen, String CantItems,
                                       String ValTotal, String NombreTienda, String CiudadTienda, String RucTienda,
                                       String User) throws SQLException{
        String strNumGuia = null;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in",CodGrupoCia);
        mapParametros.put("cCodLocal_in", CodLocal);
        mapParametros.put("vFechaGuia_in", FechaGuia);
        mapParametros.put("cTipDoc_in", TipDoc);
        mapParametros.put("cNumDoc_in", NumDoc);
        mapParametros.put("cTipOrigen_in",ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR);
        mapParametros.put("vCodOrigen_in", CodOrigen);
        mapParametros.put("nCantItems_in",new Integer(CantItems));
        mapParametros.put("nValTotal_in", FarmaUtility.getDecimalNumber(ValTotal));
        mapParametros.put("vNombreTienda_in", NombreTienda);
        mapParametros.put("vCiudadTienda_in", CiudadTienda);
        mapParametros.put("vRucTienda_in", RucTienda);
        mapParametros.put("vUsu_in", User);
        log.info("mapParametros AGREGAR GUIA: " + mapParametros);
        mapper.agregaCabGuiaIngreso(mapParametros);
        strNumGuia = (String)mapParametros.get("numeroNotaES");
        
        return strNumGuia;
    }


    public void agregaDetGuiaIngreso(String CodGrupoCia, String CodLocal, String NumGuia, String TipOrigen,
                                     String CodProd, String ValPrecUnit, String ValPrecTotal, String CantMov,
                                     String FecNota, String FecVecProd, String NumLote, String CodMotKardex,
                                     String TipDocKardex, String ValFrac, String Usuer,
                                     String cantSolicitada,  //ASOSA - 15/08/2014
                                     String oc //ASOSA - 24/09/2014
                                     ) throws SQLException{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in",CodGrupoCia);
        mapParametros.put("cCodLocal_in", CodLocal);
        mapParametros.put("cNumNota_in", NumGuia);
        mapParametros.put("cTipOrigen_in",ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR);
        mapParametros.put("cCodProd_in", CodProd);
        mapParametros.put("nValPrecUnit_in", FarmaUtility.getDecimalNumber(ValPrecUnit));
        mapParametros.put("nValPrecTotal_in", FarmaUtility.getDecimalNumber(ValPrecTotal));
        mapParametros.put("nCantMov_in",new Integer(CantMov));
        mapParametros.put("vFecNota_in", FecNota);
        mapParametros.put("vFecVecProd_in", FecVecProd);
        mapParametros.put("vNumLote_in", NumLote);
        mapParametros.put("cCodMotKardex_in", ConstantsPtoVenta.MOT_KARDEX_RECEPCION_PROVEEDOR);
        mapParametros.put("cTipDocKardex_in", ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        mapParametros.put("vValFrac_in", ValFrac);
        mapParametros.put("vUsu_in", Usuer);
        mapParametros.put("nCantSolicitado", cantSolicitada);  
        mapParametros.put("nOrdenCompra", oc);          
        
        mapper.agregaDetGuiaIngreso(mapParametros);
        
    }
    public String anularRecepGuiaIngreso(String CodGrupoCia, String CodLocal
                                         , String NumerGuia, String IdDocumento
                                         , String NumeroDocument) throws SQLException {
        String flag = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("vCodGrupoCia_in",CodGrupoCia);
        mapParametros.put("vCodLocal_in",CodLocal);
        mapParametros.put("vNumerGuia_in",NumerGuia);
        mapParametros.put("vIdeDocumento_in",IdDocumento);
        mapParametros.put("vNumeroDocument_in",NumeroDocument);
        
        mapper.anularRecepGuiaIngreso(mapParametros);
        flag = (String)mapParametros.get("Respuesta");
        
        return flag;
    }
    
    public void anulaDocumentoRecep(String CodGrupoCia,String CodLocal
                                    ,String NumerGuia,String CodMotKardex_in
                                    ,String TipDocKardex_in
                                    ,String User) throws SQLException{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in",CodGrupoCia);
        mapParametros.put("cCodLocal_in", CodLocal);
        mapParametros.put("cNumNota_in", NumerGuia);
        mapParametros.put("cCodMotKardex_in",CodMotKardex_in);
        mapParametros.put("cTipDocKardex_in", TipDocKardex_in);
        mapParametros.put("vIdUsu_in", User);
        
        mapper.anulaDocumentoRecep(mapParametros);
                
    }
    
    public void cambiaEstado(String pCodGrupoCia,
                             String pCodCia,
                             String pCodLocal,
                             String pCodOC,
                             String pSecRecepcion) throws Exception{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
 
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", pCodCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cCodOC_in", pCodOC);
        mapParametros.put("nSecCab_in", pSecRecepcion);
        mapper.cambiaEstadoCabRecep(mapParametros);  
    }
    
    public ArrayList<ArrayList<String>> getListaDocumtRecep(String cCodGrupoCia, String cCodCia, String cCodLocal,
                                                            String vNumOrdCompr, String vCodProv) throws SQLException {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno = null;      
        
        mapParametros.put("cCodGrupoCia_in", cCodGrupoCia);
        mapParametros.put("cCodCia_in", cCodCia);
        mapParametros.put("cCodLocal_in", cCodLocal);
        mapParametros.put("cCodOrdComp_in", vNumOrdCompr);
        mapParametros.put("cCodProv_in", vCodProv);
        openConnection();
        try
        {
            mapper.getListaDocumtRecep(mapParametros);                            
        }
        finally
        {
            commit();            
        } 
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;        
    }
    
    public String cierreOC(String codGrupoCia, String codLocal, String numOrdComp) throws SQLException{
        String flag = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",codGrupoCia);
        mapParametros.put("cCodLocal_in",codLocal);
        mapParametros.put("cNumerGuia_in",numOrdComp);
        //openConnection();
        try{
            mapper.cierreOrdCompDocumento(mapParametros);
            flag = (String)mapParametros.get("Respuesta");
        }finally{
            return flag;
           // commit();
        }
    }
    

    private void getlistaOrdenesCompra(Map<String, Object> object) {
        openConnection();
        try{
            mapper.getListarOrdenesCompra(object);
        }finally{
            sqlSession.close();
        }
    }

    private void getlistaDevolucion(Map<String, Object> object) {
        openConnection();
        try{
            mapper.getListarDevoluciones(object);
        }finally{
            sqlSession.close();
        }
    }    
     
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperInventario.class);
    }     
    
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }   

    /**
     * Confirma la devolucion
     * @author ERIOS
     * @since 22.07.2013
     * @param pNumeroNota
     */
    public void confirmarDevolucion(String pNumeroNota){
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in",FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNumNotaEs_in",pNumeroNota);
        mapParametros.put("vIdUsu_in",FarmaVariables.vIdUsu);
        mapper.confirmarDevolucion(mapParametros);        
    }
    
    //CVILCA 26.10.2013
    public ArrayList<ArrayList<String>> listarProductosPorNota(String numOrdComp, String numNota) throws SQLException{
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in",FarmaVariables.vCodCia);
        mapParametros.put("cCod_Local_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNumOrdCom_in",numOrdComp);
        mapParametros.put("vNumNota_in",numNota);
        mapper.obtenerProductosPorNota(mapParametros);
        tmpLista = (List<BeanResultado>)mapParametros.get("listado");
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        return tmpArray;
    }

    @Override
    public ArrayList<ArrayList<String>> listaOrdenDeCompraByFecha(String pFinFecha, String pIniFecha) {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);        
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("vFechaIni_in", pIniFecha);
        mapParametros.put("vFechaFin_in", pFinFecha);
        
        openConnection();
        try{
            
            //mapper.getListaOrdenCompra(mapParametros);
            mapper.getListaOrdenCompraByFecha(mapParametros);
        }finally{
            sqlSession.close();
        }
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
        
       
    }
    @Override
    public int obtenerProdInnerPack(String cCodGrupoCia, String vCodProv){
        int valInnerPack = 0;
        String strVal="";
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();        
        mapParametros.put("cCodGrupoCia_in", cCodGrupoCia);
        mapParametros.put("cCodProd_in", vCodProv);        
       
        openConnection();
        try{
            mapper.obtenerProdInnerPack(mapParametros);
        }finally{
            sqlSession.close();
        }
        strVal = (String) mapParametros.get("valInnerPack");
        valInnerPack = Integer.parseInt(strVal);
        return valInnerPack;
    }



   
    /**
     * @Author:Cesar_Huanes
     * @Descripcion:Obtiene datos del Local
     * @Fecha:13/12/2013
     */
    @Override
    public ArrayList<ArrayList<String>> obtieneDatosLocal(String cCodGrupoCia,  String cCodLocal) {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);     
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
       
        
        openConnection();
        try{
            mapper.getDatosLocal(mapParametros);
        }finally{
            sqlSession.close();
        }
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }


     /**
      * Graba tabla de lgt_nota_es_cab
      * @author CHUANES
      * @since 16.12.2013
      * @param vCodGrupoCia
      * @param vCodLocal
      * @param etc...
      */
    @Override
    public String graba_Nota_Es_Cab(String CodGrupoCia_in, String CodLocal_in, String TipDestino_in,
                                     String CodDestino_in, String TipMotivo_in, String DesEmp_in, String RucEmp_in,
                                     String DirEmp_in, String DesTran_in, String RucTran_in, String DirTran_in,
                                     String PlacaTran_in, String CantItems_in, String ValTotal_in, String Usu_in,String CodMotTransInterno_in) {
        String flag="";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in",CodGrupoCia_in);
        mapParametros.put("cCodLocal_in", CodLocal_in);
        mapParametros.put("vTipDestino_in", TipDestino_in);
        mapParametros.put("cCodDestino_in",CodDestino_in);
        mapParametros.put("cTipMotivo_in", TipMotivo_in);
        mapParametros.put("vDesEmp_in", DesEmp_in);
        mapParametros.put("vRucEmp_in", RucEmp_in);
        mapParametros.put("vDirEmp_in",DirEmp_in);
        mapParametros.put("vDesTran_in", DesTran_in);
        mapParametros.put("vRucTran_in", RucTran_in);
        mapParametros.put("vDirTran_in", DirTran_in);
        mapParametros.put("vPlacaTran_in", PlacaTran_in);
        mapParametros.put("nCantItems_in", CantItems_in);
        mapParametros.put("nValTotal_in", ValTotal_in);
        mapParametros.put("vUsu_in", Usu_in);
        mapParametros.put("cCodMotTransInterno_in", CodMotTransInterno_in);
        
        try{
            mapper.graba_Nota_Es_cab(mapParametros);
            flag = (String)mapParametros.get("valorB");
        }finally{
            return flag;
        
        }   
    }
    
   
     /**
      * Graba tabla de Guia de Remision lgt_guia_rem
      * @author CHUANES
      * @since 16.12.2013
      * @param vCodGrupoCia
      * @param vCodLocal
      * @param vIdUsu
      * @param pNumNota
      */
    @Override
    public void grabarGuiaRemision(String vCodGrupoCia, String vCodLocal,String vIdUsu,String pNumNota) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",vCodGrupoCia);
        mapParametros.put("cCodLocal_in",vCodLocal);
        mapParametros.put("cIdUsu_in",vIdUsu);
        mapParametros.put("cNumNota_in",pNumNota);
        mapper.generarGuiaRemision(mapParametros);     
    }
    
    
     /**
      * Actualiza campo text_impr en la tabla lgt_nota_es_cab
      * @author CHUANES
      * @since 16.12.2013
      * @param cCodGrupoCia
      * @param cCodCia
      * @param NumNota
      * @param textoImpr
      */
    @Override
    public void actualizaTextoImpresion(String vCodGrupoCia, String CodLocal, String NumNota, String textoImpr) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",vCodGrupoCia);
        mapParametros.put("cCodLocal_in",CodLocal);
        mapParametros.put("cNumNota",NumNota);
        mapParametros.put("cTexto_Impr",textoImpr);
        mapper.actualizaTexto(mapParametros);
    }


     /**
      * lista Guia de Remision que no mueven stock
      * @author CHUANES
      * @since 16.12.2013
      * @param cCodGrupoCia
      * @param cCodCia
      */
    @Override
    public ArrayList<ArrayList<String>> getListaGuiasNoMuevenstock(String cCodGrupoCia, String cCodLocal) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno;
        mapParametros.put("cCodGrupoCia_in",cCodGrupoCia);
        mapParametros.put("cCodLocal_in",cCodLocal);
        openConnection();
        try{
            mapper.getListaGuiaNoMuevenStock(mapParametros); 
        }finally{
            sqlSession.close();
        }
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public ArrayList<ArrayList<String>> getListaMaestroProductos(String cCodGrupoCia ,String cCodLocal) {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno;
        mapParametros.put("cCodGrupoCia_in",cCodGrupoCia);
        mapParametros.put("cCod_Local_in",cCodLocal);
        openConnection();
        try{
            mapper.getListaMaestroProductos(mapParametros);
        }finally{
            sqlSession.close();
        }
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public ArrayList<ArrayList<String>> buscaProductosPorDescripcion(String strDescripcion) {
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno;
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCod_Local_in",FarmaVariables.vCodLocal);
        mapParametros.put("vDescripcion",strDescripcion);
        openConnection();
        try{
            mapper.buscaProductoDescripcion(mapParametros);
        }finally{
            sqlSession.close();
        }
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
        
        
      
    }

    @Override
    public ArrayList obtenerProductoCodigoBarra(String pCodBarra) {
        ArrayList lista = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);    
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cCodBarra",pCodBarra);
        try{               
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperInventario.class);
            mapper.obtenerProductoCodigobarra(mapParametros);
            List<BeanResultado> listaResultado = (List<BeanResultado>) mapParametros.get("listado");
            lista = utilityPtoVenta.parsearResultadoMatriz(listaResultado);            
        }catch(Exception e){   
            log.error("",e);
            lista=null;
        }finally{   
            sqlSession.close();
        }
        return lista;
    }

    @Override
    public void grabaCabRecep(String cCodGrupoCia, String cCodCia, String cCodLocal,String cCodOC, String cCodProv,String cCantItem,
                              String cIdUsu,String cNumNota) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",cCodGrupoCia);
        mapParametros.put("cCod_Cia_in",cCodCia);
        mapParametros.put("cCod_Local_in",cCodLocal);
        mapParametros.put("cCodOC_in",cCodOC);
        mapParametros.put("cCod_Prov_in",cCodProv);
        mapParametros.put("cCant_Item_in",cCantItem);
        mapParametros.put("cIdUsu_in",cIdUsu);
        mapParametros.put("cNum_Nota_in",cNumNota);
        mapper.grabarCabRecep(mapParametros);  
        
    }
    
    
    @Override
    public String  actualizarOrdenDevolucion(SqlSession sqlFasaSession,String pIndiGuia , String pCodLocal, String pNumGuia,String pNomProv,String pCodiMifarma,Integer pCantReci) throws SQLException {
                                              
    Map<String, Object> mapParametros = new HashMap<String, Object>();
    String salida = "";
    mapParametros.put("PINDIGUIA",pIndiGuia);
    mapParametros.put("PCODILOCA",pCodLocal);
    mapParametros.put("PNUMEGUIA",pNumGuia);
    mapParametros.put("PNOMBPROV",pNomProv);
    mapParametros.put("PCODIMIFA",pCodiMifarma);
    mapParametros.put("PCANTRECI",pCantReci);                                          
    
    try{
        MapperInventario mapperFasa = sqlFasaSession.getMapper(MapperInventario.class);   
        mapperFasa.actualizarOrdenDevolucion(mapParametros);
        salida = (String)mapParametros.get("Salida");
       
    }catch(Exception e){
        log.error("",e);
        throw e;        
    }                                          
    return salida;                           
                                              
    }
    
    @Override
    public ArrayList<ArrayList<String>> getGuiaNoEnvioLegacy(String pNumNota){
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList lista=new ArrayList();
        List<BeanResultado> lstRetorno = null;      
        
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNum_Nota",pNumNota);
        openConnection();
        try
        {
            mapper.getGuiaNoEnvioLegacy(mapParametros);                            
        }
        finally
        {
            commit();            
        } 
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lista = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lista;                                                                                                                           
    }
    
    @Override
    public ArrayList<ArrayList<String>> getGuiaDetGuiaLegacy(String pNumNota,String pSecGuia){
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
         ArrayList lista=new ArrayList();
        List<BeanResultado> lstRetorno = null;      
        
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNum_Nota",pNumNota);
        mapParametros.put("cSec_Guia",pSecGuia);
        
        openConnection();
        try
        {
            mapper.getGuiaDetLecacy(mapParametros);                            
        }
        finally
        {
            commit();            
        } 
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lista = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lista;                                                                                                                           
    }

    @Override
    public void actualizaIndicador(String pNumNota, String pSecGuia) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCod_Cia_in",FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNumGuia",pNumNota);
        mapParametros.put("cSec_Guia",pSecGuia);
        mapper.actualizaIndicador(mapParametros);   

        
    }

    @Override
    public void actualizaNumNota(String NumNota,String CodOC,String pSecOCRecep) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in",FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cNumNota",NumNota);
        mapParametros.put("cCodOC",CodOC);
        mapParametros.put("nSecOCRecep",pSecOCRecep);
        
        mapper.actualizaNumNota(mapParametros);   

    }

    @Override
    public String getNumAjuste( String pCodProd,String  pNumComPago,String pCodMotKardex,String pTipDocKardex) {
        String estado=null;
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("cCodProd",pCodProd);
        mapParametros.put("cNumComPago",pNumComPago);
        mapParametros.put("cCodMotkardex",pCodMotKardex);
        mapParametros.put("cTipDockardex",pTipDocKardex);
      
        openConnection();
        try{
            mapper.getNumAjuste(mapParametros);
        }finally{
            sqlSession.close();
        }
        estado = (String)mapParametros.get("Respuesta");
        
        return estado;

    }
    
    @Override
    public String getIndOC( String pNumerOC_in) {
        String estado=null;
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cNumerOC_in",pNumerOC_in);
           
        openConnection();
        try{
            mapper.getIndicador_OC(mapParametros);
        }finally{
            sqlSession.close();
        }
        estado = (String)mapParametros.get("Respuesta");
        
        return estado;

    }
    
    
}
