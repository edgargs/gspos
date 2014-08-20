package mifarma.ptoventa.reportes.reference;

import java.util.*;
import mifarma.common.*;

import org.jfree.data.category.DefaultCategoryDataset;

public class VariablesReporte 
{
  public VariablesReporte()
  {
  }
  
  public static String vFechaInicio = "";
  public static String vFechaFin = "";
  public static String vCorrelativo = "";  
  public static String vTipo = "";
  public static String vNComprobante = "";
  public static String vFecha = "";
  public static String vCliente = "";
  public static String vMonto = "";
  public static String vDireccion = "";
  public static String vRuc = "";
  public static String vHora = "";
  public static String vUsuario = "";
  public static String vEstado = "";
  public static String vUnidadPresentacion = "";
  public static String vCantidad = "";
  public static String vCodigoUsuario = "" ;
  public static String vNombreUsuario = "";
  public static String vOrdenar ="";
  public static String vOrdenarDetalleVendedor ="";
  public static String vVentas ="";
  
   public static String vInd_Filtro = "";
  public static String vTipoFiltro = "";
  public static String vCodFiltro = "";
  
  public static int vNumColumnOrd=6;
  
  public static String vCodProd = "" ;
  public static String vDesProd = "";
  public static String vNomLab ="";
  public static String vDescUnidPresent =""; 
  public static String vNombreInHashtable ="";
  public static String vNombreInHashtableVal ="";
  public static String[] IND_DESC_FILTRO = {"Boleta","Factura"};
  public static String[] IND_VALOR_FILTRO ={"01","02"};
  public static String[] IND_DESCRIP_CAMPO_VENTAS_DIA_MES = {"Periodos","Pedidos","Venta Inc IGV","Val. Promedio","Und X Vale","Pr. Prom X Und."};
  public static String[] IND_CAMPO_DETALLE_VENTAS_DIA_MES = {"6","1","2","3","4","5"};
  public static int vCampo = 0 ;
  public static String vCampoFiltroLab = "" ;
  public static String vCampoFiltro = "" ;
  public static String vOrden ="";
  public static String vFiltro = "" ;

  public static ArrayList vArrayList_Registro_Ventas = new ArrayList();
  
  /**Nuevas Variables*/
  
  public static DefaultCategoryDataset vDataSet_Reporte = new DefaultCategoryDataset();
  
  /**ASOLIS
   * 26.11.08*/

  public static String ACCION_MOSTRAR_TIPO_VENTA="";  
    
}