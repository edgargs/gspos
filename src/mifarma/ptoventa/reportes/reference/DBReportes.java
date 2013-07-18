package mifarma.ptoventa.reportes.reference;
import java.sql.*;
import java.util.*;

import mifarma.common.*;
import mifarma.ptoventa.reference.*;

public class DBReportes 
{
  private static ArrayList parametros = new ArrayList();
  public DBReportes()
  {
  }
   public static void cargaListaRegistroVentas(FarmaTableModel pTableModel,
                                               String pFechaInicio,
                                               String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_REGISTRO_VENTA(?,?,?,?)",parametros,false);
  }
  public static void obtieneDetalleRegistroVentas(FarmaTableModel pTableModel,
                                          String pCodigo) throws SQLException {
                                               
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodigo);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_REGISTRO_VENTA(?,?,?)",parametros,false);
  }
  
  public static void obtieneComprobantes_Venta(FarmaTableModel pTableModel,
                                               String pCodigo) throws SQLException {
                                               
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodigo);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_COMPROBANTES_VENTA(?,?,?)",parametros,false);
  }
  
  public static void obtieneComprobantes_Venta_Detalle(FarmaTableModel pTableModel,
                                               String pCodigo) throws SQLException {
                                               
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodigo);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_COMPROBANTE(?,?,?)",parametros,false);
  }
   public static ArrayList obtieneInfoResumen(String pFechaIni,String pFechaFin) throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(pFechaIni);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_REPORTE.REPORTE_RESUMEN_VENTA(?,?,?,?)",parametros);
    return pOutParams;
  }
  
  public static ArrayList obtieneInfoResumenNotaCredito(String pFechaIni,String pFechaFin) throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(pFechaIni);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_REPORTE.REPORTE_RESUMEN_VTA_NOT_CREDIT(?,?,?,?)",parametros);
    return pOutParams;
  }
  
  public static void cargaListaFormadePago (FarmaTableModel pTableModel,
                                            String pFechaInicio,
                                            String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_FORMAS_DE_PAGO(?,?,?,?)",parametros,false);
  }
  
   public static void cargaListaDetalledeVentas(FarmaTableModel pTableModel,
                                               String pFechaInicio,
                                               String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_VENTAS(?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaResumenProductosVendidos(FarmaTableModel pTableModel,
                                                        String pFechaInicio,
                                                        String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_RESUMEN_PRODUCTOS_VEND(?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaFiltroDetalleVentas(FarmaTableModel pTableModel,
                                                   String pFechaInicio,
                                                   String pFechaFin,
                                                   String pFiltro) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pFiltro);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_FILTRO_PRODUCTOS_VEND(?,?,?,?,?)",parametros,false);
  }
  
   public static void cargaListaVentasporVendedor(FarmaTableModel pTableModel,
                                                  String pFechaInicio,
                                                  String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VENDEDOR(?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaDetalleVentasporVendedor(FarmaTableModel pTableModel,
                                                        String pFechaInicio,
                                                        String pFechaFin,
                                                        String pUsuario) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pUsuario);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_VENTAS_VEND(?,?,?,?,?)",parametros,false);
  }
  
    public static void cargaListaVentasporProducto(FarmaTableModel pTableModel,
                                                  String pFechaInicio,
                                                  String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_PRODUCTO(?,?,?,?)",parametros,false);
  }
  
   public static void cargaListaVentasporProductoFiltro(FarmaTableModel pTableModel,
                                                  String pFechaInicio,
                                                  String pFechaFin,
                                                  String pTipoFiltro,
                                                  String pCodFiltro) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pTipoFiltro);
    parametros.add(pCodFiltro);
    
    System.out.println(parametros);
    
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_PRODUCTO_F(?,?,?,?,?,?)",parametros,false);
  }

   public static void cargaListaVentasPorDia(FarmaTableModel pTableModel,
                                             String pFechaInicio,
                                             String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VETAS_POR_DIA(?,?,?,?)",parametros,false);
  }

  public static void cargaListaResumenVentasDetallado(FarmaTableModel pTableModel,
                                                      String pFechaInicio,
                                                      String pFechaFin,
                                                      String pCodProd) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pCodProd);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLADO_RESUMEN_VTA(?,?,?,?,?)",parametros,false);
  }

  //Histórico de Creación/Modificación
  //ERIOS      27.03.2005   Creación
  //DlgVentasPorHora 
  public static void cargaListaVentasporHora(FarmaTableModel pTableModel,String pFechaInicio,String pFechaFin, String filtroDia) throws SQLException 
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(filtroDia);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_VENTAS_POR_HORA(?,?,?,?,?)",parametros,false);      
  }

 public static void cargaListaVentasDiaMes(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesReporte.vFechaInicio );
    parametros.add(VariablesReporte.vFechaFin);
    parametros.add(VariablesReporte.vCodFiltro);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_MHR.REPORTE_VENTAS_DIA_MES(?,?,?,?,?)",parametros,false);
  }  
  
  //Histórico de Creación/Modificación
  //ERIOS      06.07.2006   Creación
  //DlgProductoFaltaCero
  public static void cargaListaFaltaCero(FarmaTableModel pTableModel,String pFechaInicio,String pFechaFin, String filtroDia) throws SQLException 
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(filtroDia);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_PRODUCTOS_FALTA_CERO(?,?,?,?,?)",parametros,false);      
  }
  
  public static void cargaListaDetFaltaCero(FarmaTableModel pTableModel,String codProd,String pFechaInicio,String pFechaFin) throws SQLException 
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(codProd);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_DET_FALTA_CERO(?,?,?,?,?)",parametros,false);      
  }
  
   public static void listaVentasporProductoLab(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VTA_PRODUCTO_LAB(?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaProductosABC(FarmaTableModel pTableModel,
                                            String filtro,
                                            String ind,
                                            String fechaIni,
                                            String fechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(filtro);
    parametros.add(ind);
    parametros.add(fechaIni);
    parametros.add(fechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_PRODUCTO_ABC(?,?,?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaProductosABCFiltro(FarmaTableModel pTableModel,
                                                  String filtro,
                                                  String ind, 
                                                  String filtroTipo,
                                                  String fechaIni,
                                                  String fechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(filtro);
    parametros.add(ind);
    parametros.add(filtroTipo);
    parametros.add(fechaIni);
    parametros.add(fechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_FILTRO_PRODUCTO_ABC(?,?,?,?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaConsolidadoVtasProd(FarmaTableModel pTableModel,
                                                   String fechaIni,
                                                   String fechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(fechaIni);
    parametros.add(fechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_CONSOLIDADO_VTA_PROD(?,?,?,?)",parametros,false);
  }
                                                  
  public static void cargaListaVentasporProductoVirtuales(FarmaTableModel pTableModel,
                                                          String pFechaInicio,
                                                          String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VTA_PRODUCTO_VIRTUAL(?,?,?,?)",parametros,false);
  }  
                                                  
  public static void borrarRegistroDetFaltaCero(String pCodProd,
                                                String pSecUsuLocal,
                                                String pFecha) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(pFecha);
    parametros.add(pSecUsuLocal);
    System.out.println("borrarRegistroDetFaltaCero "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_REPORTE.REPORTE_BORRAR_DET_FALTA_CERO(?,?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaPedidosAnulNoCob(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    System.out.println("cargaListaPedidosAnulNoCob "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_PEDIDOS_ANUL_NO_COB(?,?,?,?)",parametros,false);
  }

  public static void cargaListaUnidadVtaLocal(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    System.out.println("cargaListaUnidadVtaLocal "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_UNID_VTA_LOCAL",parametros,false);
  }
  
  public static void cargaListaUnidadVtaLocalXFiltro(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(VariablesPtoVenta.vTipoFiltro);
    parametros.add(VariablesPtoVenta.vCodFiltro);
    System.out.println("cargaListaUnidadVtaLocalXFiltro "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_UNID_VTA_LOCAL_FILTRO(?,?)",parametros,false);
  }
  //
  public static void cargaListaProdSinVtaNDias(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodLocal);
    System.out.println("cargaListaProdSinVtaNDias "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_PROD_SIN_VTA_N_DIAS(?)",parametros,false);
  }
  
  public static void cargaListaProdSinVtaNDiasXFiltro(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesPtoVenta.vTipoFiltro);
    parametros.add(VariablesPtoVenta.vCodFiltro);
    System.out.println("cargaListaProdSinVtaNDiasXFiltro "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_PROD_SIN_VTA_NDIAS_FIL(?,?,?)",parametros,false);
  }
  
  /** Listado de formas de pago por pedido
   *@author:   
   *@since: 05/08/07
   */
    
  public static void cargaListaFormasPago(FarmaTableModel pTableModel, String nroPedido) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(nroPedido);
    System.out.println("carga lista de fornmas de pago "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_FORMAS_PAGO(?,?,?)",parametros,false);
  }
  
  /**
   * Obtiene el Numero de  Dias Sin Ventas 
   * @author :  
   * @since  : 21.08.2007
   */
  public static String obtieneNumeroDiasSinVentas() throws SQLException {
    parametros = new ArrayList();
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REPORTE.NUMERO_DIAS_SIN_VENTAS",parametros);
  }
  
    
  public static ArrayList cargaListaVV_Impr(String pFechaIni,String pFechaFin) throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(pFechaIni);
    parametros.add(pFechaFin);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_REPORTE.REPORTE_VENTAS_VENDEDOR_IMP(?,?,?,?)",parametros);
    return pOutParams;
  }

/**
 * Obtiene el Reporte Detalle del vendedor por tipo de Venta
 * @author :  -
 * @since  : 26.11.2008
 * */
  
    public static void cargaListaDetalleVentasporVendedorTipo(FarmaTableModel pTableModel,
                                                        String pFechaInicio,
                                                        String pFechaFin,
                                                        String pUsuario,
                                                        String pTipo) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pUsuario);
    parametros.add(pTipo);
      
    System.out.println(parametros);
   FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DET_VENTAS_VEND_TIPO(?,?,?,?,?,?)",parametros,false);
 


  }

    /**
     * Obtiene el Reporte  del vendedor por tipo de Venta según parametro enciado 
     * @author :  
     * @since  : 26.11.2008
     * */
      

    public static void cargaListaVentasporVendedorTipo(FarmaTableModel pTableModel,
                                                   String pFechaInicio,
                                                   String pFechaFin,
                                                   String pTipo) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pTipo);
        
     System.out.println(parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VEND_TIPO(?,?,?,?,?)",parametros,false);
    }

}