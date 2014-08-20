package mifarma.ptoventa.inventario.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.dto.NotaEsCabDetDTO;
import mifarma.ptoventa.inventario.dto.OrdenCompraCabDTO;
import mifarma.ptoventa.reference.DAOTransaccion;

import org.apache.ibatis.session.SqlSession;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DAOInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LRUIZ      17.05.2013   Creación<br>
 * <br>
 * @author Luis Ruiz Peralta<br>
 * @version 1.0<br>
 *
 */
public interface DAOInventario extends DAOTransaccion{
    
    public ArrayList<ArrayList<String>> listarOrdenesDeCompra(String pFecha) throws SQLException;
    
    public ArrayList<ArrayList<String>> listarCabOrdComp(String pOrdCompra) throws SQLException;
    
    public ArrayList listarProdOrdenCompra(String pCodOrdCompra) throws SQLException;
    
    public ArrayList<ArrayList<String>> listarProductosPorOrdenCompra(String pNumeroOC,NotaEsCabDetDTO ordenCompraCabDTO) throws SQLException;
          
    public String   grabarCabDetRecep(String vCodGrupoCia, String vCodCia, String vCodLocal
                                     , String vIdUsu, String vNumOrdenCompra, String vFechIngreso
                                     , String vIdeDocumento, String vSerieDocument
                                     , String vNumeroDocument, String vCantItem
                                     , String vCodProveedor, String vImporteTotal
                                     , String vImportRecep, String vRedondeo) throws SQLException;
    
    public void grabarDetRecep(String vCodGrupoCia, String vCodCia, String vCodLocal, String vIdUsu
                                  , String vNumOrdenCompra , String vCodProducto
                                  , String vCantPedida, String vCantEntregada, String vPrecioUnit
                                  , String vPrecioIGV,String vImportRecep, String vSegOC, String vIdeDocumento
                                  , String vSerieDocument, String vNumeroDocument,String vSecOC
                                  ) throws SQLException;
    
    //CVILCA 25.10.2013
    public String actualizarOrdenCompra(String vNumOrdenCompra , String vCodProducto,
                                  Integer vCantEntregada) throws SQLException;
    
    public ArrayList<ArrayList<String>> listarDevoluciones(NotaEsCabDTO notaEsCabDTO) throws SQLException;
        
    public ArrayList<ArrayList<String>> cargaDetalleTransferencia(String pNumeroNota) throws SQLException;
    
    public ArrayList<ArrayList<String>> listarOrdenesCompra(OrdenCompraCabDTO ordenCompraCabDTO) throws SQLException;

    public String grabarCabeceraNotaSalida(NotaEsCabDTO notaEsCabDTO)throws SQLException;
    
    public ArrayList<ArrayList<String>> getCabOrdenCompraRecep(String pCodOrComp, String pSecRecepcion) throws SQLException;
    
    public void grabarDetalleNotaSalida(String pNumera, NotaEsCabDTO notaEsCabDTO, NotaEsCabDetDTO notaEsCabDetDTO) throws SQLException;

    public void generarGuiaSalida(String pNumera, int cantDetGuia, int cantItems) throws SQLException;
    
    public String agregaCabGuiaIngreso(String CodGrupoCia, String CodLocal, String FechaGuia,
                        String TipDoc, String NumDoc, String TipOrigen, String CodOrigen,
                        String CantItems, String ValTotal, String NombreTienda,
                        String CiudadTienda, String RucTienda, String User
                    ) throws SQLException;
    
    public void agregaDetGuiaIngreso(String CodGrupoCia, String CodLocal, String NumGuia,
                        String TipOrigen, String CodProd, String ValPrecUnit, String ValPrecTotal,
                        String CantMov, String FecNota, String FecVecProd, String NumLote,
                        String CodMotKardex, String TipDocKardex, String ValFrac, String Usuer
                    ) throws SQLException;
    
    public ArrayList<ArrayList<String>> listarProdDetRecep(String pNumOrd, String pSecRecep) throws Exception;
    
    public String anularRecepGuiaIngreso(String vCodGrupoCia, String vCodLocal
                                         , String vNumerGuia, String vIdeDocumento
                                         , String vNumeroDocument) throws SQLException;
    
    public void anulaDocumentoRecep(String vCodGrupoCia, String vCodLocal, String vNumerGuia
                                    ,String cCodMotKardex_in, String cTipDocKardex_in
                                    ,String vUser) throws SQLException;
    
  public void cambiaEstado(String pCodGrupoCia,
                             String pCodCia,
                             String pCodLocal,
                             String pCodOC,
                             String pSecRecepcion) throws Exception;
  
  public ArrayList<ArrayList<String>> getListaDocumtRecep(String cCodGrupoCia, String cCodCia, String cCodLocal,
                                                          String vNumOrdCompr, String vCodProv) throws SQLException;
  
  public String cierreOC(String codGrupoCia, String codLocal, String numOrdComp) throws SQLException;

    /**
     * Confirma la devolucion
     * @author ERIOS
     * @since 22.07.2013
     * @param pNumeroNota
     */
    public void confirmarDevolucion(String pNumeroNota);
    
    //CVILCA 26.10.2013
    public ArrayList<ArrayList<String>> listarProductosPorNota(String numOrdComp,String numNota) throws SQLException;
    
     /**
      * Lista Ordenes de Compra por rango de Fechas
      * @author CHUANES
      * @param pFinFecha
      * @param pIniFecha
      * @throws SQLException
      */
    public ArrayList<ArrayList<String>> listaOrdenDeCompraByFecha(String pFinFecha,String pIniFecha) throws SQLException;

    /**
     * Obtiene el InnerPack de un producto (numero de unidades por paquete)
     * @autor GFONSECA
     * @since 10.12.2013
     * @param cCodGrupoCia
     * @param vCodProv
     * */
    public int obtenerProdInnerPack(String cCodGrupoCia, String vCodProv) throws SQLException;

     /**
      * Lista datos del local
      * @author CHUANES
      * @since 13.12.2013
      * @throws SQLException
      */
    public ArrayList<ArrayList<String>> obtieneDatosLocal(String cCodGrupoCia,  String cCodLocal) throws SQLException;
    
     /**
      * Graba tabla lgt_nota_es-cab
      * @author CHUANES
      * @since 13.12.2013
      * @throws SQLException
      */
    public String graba_Nota_Es_Cab( String CodGrupoCia_in, 
    String CodLocal_in,String TipDestino_in, String CodDestino_in, String TipMotivo_in,String DesEmp_in,
    String RucEmp_in,String DirEmp_in,String DesTran_in,String RucTran_in,String DirTran_in,
    String PlacaTran_in,String CantItems_in,String ValTotal_in,String  Usu_in ,String CodMotTransInterno_in)throws SQLException;
    
    /**
     * Graba Guia de Remision
     * @author CHUANES
     * @since 13.12.2013
     * @param vCodGrupoCia
     * @param vCodLocal
     * @param vIdUsu
     * @param pNumNota
     * @throws SQLException
     */
    public void grabarGuiaRemision(String vCodGrupoCia,  String vCodLocal,String vIdUsu,String pNumNota) throws SQLException;
    
    /**
     * Actualiza campo text_imp en la tabla lgt_nota_es_cab
     * @author CHUANES
     * @since 16.12.2013
     * @param vCodGrupoCia
     * @param vCodLocal
     * @param NumNota
     * @param textoImpr
     * @throws SQLException
     */
    public void actualizaTextoImpresion(String vCodGrupoCia,String CodLocal,String NumNota ,String textoImpr ) throws SQLException;
    
    /**
     * Lista Guías de Remisíon que no mueven stock
     * @author CHUANES
     * @since 19.12.2013
     * @param vCodGrupoCia
     * @param vCodLocal
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> getListaGuiasNoMuevenstock(String cCodGrupoCia, String cCodLocal) throws SQLException;
    
    /**
     * Lista Maestro de Productos
     * @author CHUANES
     * @since 23.12.2013
     * @param cCodGrupoCia
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> getListaMaestroProductos(String cCodGrupoCia,String cCodLocal) throws SQLException;
    
    /**
     * Busca Productos por descripcíon
     * @author CHUANES
     * @since 23.12.2013
     * @param strDescripcion
     * @throws SQLException
     */
    
    public ArrayList<ArrayList<String>> buscaProductosPorDescripcion(String strDescripcion)throws SQLException;
    
    /**
     * Busca Productos por Codigo de Barra 
     * @author CHUANES
     * @since 23.12.2013
     * @param strDescripcion
     * @throws SQLException
     */
    public abstract ArrayList obtenerProductoCodigoBarra(String pCodBarra)throws SQLException;
    
    /**
     * Graba la tabla lgt_oc_cab_recep cuando se genera unua devolucíon de producto
     * @author CHUANES
     * @since 30.12.2013
     * @param cCodGrupoCia
     * @param cCodCia
     * @param cCodLocal 
     * @param cCodOC
     * @param cCodProv
     * @param cIdUsu
     * @throws SQLException
     */
    
    public void grabaCabRecep(String cCodGrupoCia, String cCodCia, String cCodLocal,String cCodOC,String cCodProv ,String cCantItem,String cIdUsu,String cNumNota)throws SQLException ;
    /**
     * Actualiza datos en el legaci
     * @author CHUANES
     * @since 31.12.2013
 
     * @throws SQLException
     */
    public String actualizarOrdenDevolucion(SqlSession sqlFasaSession,String pIndiGuia , String pCodLocal, String pNumGuia,String pNomProv,String pCodiMifarma,Integer pCantReci) throws SQLException;



    public ArrayList<ArrayList<String>> getGuiaNoEnvioLegacy(String pNumNota) throws SQLException;
    
    public ArrayList<ArrayList<String>> getGuiaDetGuiaLegacy(String pNumNota,String pSecGuia) throws SQLException;
    
    public void actualizaIndicador(String pNumNota, String pSecGuia) throws SQLException;
    
    public void actualizaNumNota(String NumNota,String CodOC,String pSecOCRecep) throws SQLException;
    
    /**
     * verifica si el numero de ajuste es diferente de nullo
     * @author CHUANES
     * @since 25.02.2014
     * @param pCodProd
     * @param pNumComPago
     * @param pCodMotKardex 
     * @param pTipDocKardex
     * @throws SQLException
     */
    public String  getNumAjuste(String pCodProd,String  pNumComPago,String pCodMotKardex,String pTipDocKardex) throws SQLException;
 
    /**
     * Obtiene indicador si la Orden de Compra esta terminada
     * @author RHERRERA
     * @since 03.03.2014
     * @param pNumerOC_in
     */
    public String getIndOC(String pNumerOC_in) throws SQLException;

}
