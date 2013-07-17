package mifarma.ptoventa.reportes.reference;

import javax.swing.JLabel;
import mifarma.common.*;
import mifarma.common.FarmaColumnData;

public class ConstantsReporte 
{
  public ConstantsReporte()
  {
  }
  public static final FarmaColumnData columnsListaRegistroVentas[] = {
    new FarmaColumnData( "Correlativo", 85, JLabel.CENTER ),
	new FarmaColumnData( "T", 20, JLabel.CENTER ),
    new FarmaColumnData( "No Comprobante", 100, JLabel.CENTER ),
    new FarmaColumnData( "Fecha", 120, JLabel.CENTER),
	new FarmaColumnData( "Cliente", 180, JLabel.LEFT ),    
    new FarmaColumnData( "Estado", 80, JLabel.LEFT ),    
    new FarmaColumnData( "Monto", 100, JLabel.RIGHT ),
//    new FarmaColumnData( "Direccion", 100, JLabel.LEFT ),
    new FarmaColumnData( "Ruc", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Hora", 0, JLabel.RIGHT ),
    new FarmaColumnData( "Usuario", 0, JLabel.RIGHT ),
    new FarmaColumnData( "Estado", 0, JLabel.RIGHT ),
    new FarmaColumnData( "Fecha", 0, JLabel.CENTER),
    new FarmaColumnData( "T/Compr", 0, JLabel.CENTER),
	};
	
	public static final Object[] defaultValuesListaRegistroVentas = {" "," "," ", " ", " ", " "," ", " ", " "," "," "," "};
    
    /**
     * datos a mostrar en el detalle del reporte de los pedidos de venta
     * @autor jcallo
     * @since 14.10.2008
     * **/
    public static final FarmaColumnData columnsListaDetalleVentas[] = {
        new FarmaColumnData( "Codigo", 50, JLabel.CENTER ),
	new FarmaColumnData( "Cant", 40, JLabel.RIGHT ),
        new FarmaColumnData( "Descripcion", 180, JLabel.LEFT),
        new FarmaColumnData( "Unidad", 90, JLabel.LEFT),
        new FarmaColumnData( "Laboratorio", 120, JLabel.LEFT),
	new FarmaColumnData( "P.Unit", 70, JLabel.RIGHT ),    
        new FarmaColumnData( "Dscto", 70, JLabel.RIGHT ),
        new FarmaColumnData( "Importe", 100, JLabel.RIGHT ),
        new FarmaColumnData( "Total", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Bruto", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Dcto", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Igv", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Neto", 0, JLabel.RIGHT ),
        new FarmaColumnData( "", 0, JLabel.RIGHT ),//usuario
    };
	
	public static final Object[] defaultValuesListaDetalleVentas = {" ", " ", " ", " ", " ", " "," ", " ", " "," "," "," "," "," "};
  
  public static final FarmaColumnData columnsListaComprobantes[] = {
    new FarmaColumnData( "Tipo", 85, JLabel.CENTER ),
	  new FarmaColumnData( "Numero", 100, JLabel.CENTER ),
    new FarmaColumnData( "Fecha", 80, JLabel.CENTER),
	  new FarmaColumnData( "Bruto", 75, JLabel.RIGHT ),    
   // new FarmaColumnData( "Dscto", 100, JLabel.RIGHT ),// 
    new FarmaColumnData( "Neto", 75, JLabel.RIGHT ),
   // new FarmaColumnData( "IGV", 100, JLabel.RIGHT ),// 
    new FarmaColumnData( "Total S/.", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Redondeo", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Convenio", 100, JLabel.RIGHT ),
  };
	
	public static final Object[] defaultValuesListaComprobantes = {" ", " ", " ", " "," ", " ", " "," "," "," "};

  public static final FarmaColumnData columnsListaComprobantesDetalle[] = {
    new FarmaColumnData( "Codigo", 85, JLabel.CENTER ),
	  new FarmaColumnData( "Producto", 180, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 150, JLabel.LEFT),
	  new FarmaColumnData( "Precio", 100, JLabel.RIGHT ),    
    new FarmaColumnData( "% Dcto", 100, JLabel.RIGHT ),
    new FarmaColumnData( "P.Venta", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Cantidad", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Total", 100, JLabel.RIGHT ),
    new FarmaColumnData( "ZAN", 100, JLabel.RIGHT ),
  };
	
	public static final Object[] defaultValuesListaComprobantesDetalle = {" ", " ", " ", " "," ", " ", " "," "," "};
  
  public static final FarmaColumnData columnsListaFormasdePago[] = {
    new FarmaColumnData( "Codigo", 85, JLabel.CENTER ),
	  new FarmaColumnData( "Forma Pago", 150, JLabel.LEFT ),
    new FarmaColumnData( "Total", 80, JLabel.RIGHT),
	};
  
  public static final Object[] defaultValuesListaListaFormasdePago= {" "," "," "};
  
  public static final FarmaColumnData columnsListaDetalledeVentas[] = {
    new FarmaColumnData( "Correlativo", 85, JLabel.CENTER ),
	  new FarmaColumnData( "Tipo", 55, JLabel.CENTER ),
    new FarmaColumnData( "No Comprobante", 100, JLabel.CENTER ),
    new FarmaColumnData( "Fecha", 80, JLabel.CENTER),
    new FarmaColumnData( "Descripcion", 150, JLabel.LEFT),
    new FarmaColumnData( "Unidad", 120, JLabel.LEFT),
    new FarmaColumnData( "Cantidad", 55, JLabel.RIGHT ),
    new FarmaColumnData( "Venta", 60, JLabel.RIGHT ),
    new FarmaColumnData( "Fecha",0, JLabel.CENTER),
	};
  public static final Object[] defaultValuesListaDetalledeVentas= {" "," "," "," "," "," "," "," "," "};
  
  public static final FarmaColumnData columnsListaResumenProductosVendidos[] = {
    new FarmaColumnData( "Codigo", 85, JLabel.CENTER ),
	  new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 150, JLabel.LEFT ),
    new FarmaColumnData( "Cantidad", 70, JLabel.RIGHT),
    new FarmaColumnData( "Venta", 70, JLabel.RIGHT),
	};
  public static final Object[] defaultValuesListaResumenProductosVendidos = {" "," "," "," "," "};
  
 public static final FarmaColumnData columnsListaVentasVendedor[] = {
    /*
    new FarmaColumnData( "Codigo", 60, JLabel.CENTER ),
	  new FarmaColumnData( "Vendedor", 160, JLabel.LEFT ),
	  new FarmaColumnData( "Total Vta.", 80, JLabel.RIGHT ),
    new FarmaColumnData( "Total Bono", 80, JLabel.RIGHT ),
    new FarmaColumnData( "V.Grupo A", 80, JLabel.RIGHT ),    
    new FarmaColumnData( "%Grupo A", 80, JLabel.RIGHT ),
    new FarmaColumnData( "Venta PP", 80, JLabel.RIGHT ),
	  new FarmaColumnData( "%PP", 80, JLabel.RIGHT ),
	  new FarmaColumnData( "Pedidos", 80, JLabel.RIGHT ),
    //new FarmaColumnData( "Correlativo",0, JLabel.RIGHT ),
    new FarmaColumnData( "Farma",80, JLabel.RIGHT ),//JCORTEZ 24/03/08
    new FarmaColumnData( "No Farma",80, JLabel.RIGHT ),//JCORTEZ 24/03/08
    new FarmaColumnData( "Grupo No A",80, JLabel.RIGHT ),//JCORTEZ 24/03/08
    new FarmaColumnData( "Correlativo",0, JLabel.RIGHT )
*/
    new FarmaColumnData( "Codigo", 60, JLabel.CENTER ),// 1
    new FarmaColumnData( "Vendedor", 160, JLabel.LEFT ),// 2
    new FarmaColumnData( "Tot.C.IGV.", 80, JLabel.RIGHT ),// 3
    new FarmaColumnData( "Tot.S.IGV", 80, JLabel.RIGHT ),// 4
    new FarmaColumnData( "GG", 80, JLabel.RIGHT ),    // 5
    new FarmaColumnData( "G", 80, JLabel.RIGHT ),// 6
    new FarmaColumnData( "Otros", 80, JLabel.RIGHT ),// 7
    new FarmaColumnData( "Servicios", 80, JLabel.RIGHT ),// 8
    new FarmaColumnData( "%", 80, JLabel.RIGHT ),// 9
    new FarmaColumnData( "Total.", 80, JLabel.RIGHT ),    // 10
    new FarmaColumnData( "Num.Ped.", 80, JLabel.RIGHT ),// 11
    new FarmaColumnData( "ind_tipo_fila", 0, JLabel.RIGHT ),// 12
    new FarmaColumnData( "Correlativo",0, JLabel.RIGHT ),// 13
    new FarmaColumnData( "orden",0, JLabel.RIGHT ),// 14
    new FarmaColumnData( "login",0, JLabel.RIGHT )// 15
 };
   public static final Object[] defaultValuesListaVentasVendedor = {" "," "," ",
                                                                    " "," "," ",
                                                                    " "," "," ",
                                                                    " "," "," ",
                                                                    " "," "," "};
   //public static final Object[] defaultValuesListaVentasVendedor = {" ", " ", " ", " ", " "," ", " ", " ", " ", " "," "," "," "};
  
   public static final FarmaColumnData columnsListaDetalleVentasVendedor[] = {
    new FarmaColumnData( "Correlativo", 80, JLabel.CENTER ),
	  new FarmaColumnData( "Codigo", 60, JLabel.CENTER ),
	  new FarmaColumnData( "Descripcion", 160, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 90, JLabel.LEFT ),
    new FarmaColumnData( "Cantidad", 70, JLabel.RIGHT ),    
    new FarmaColumnData( "Venta", 70, JLabel.RIGHT ),
    new FarmaColumnData( "Zan", 60, JLabel.RIGHT ),
	};
	
	public static final Object[] defaultValuesListaDetalleVentasVendedor = {" ", " ", " ", " ", " "," ", " "};
  
   public static final FarmaColumnData columnsListaVentasProducto[] = {
    new FarmaColumnData( "Codigo", 60, JLabel.CENTER ),
	  new FarmaColumnData( "Descripcion", 205, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),
    new FarmaColumnData( "Laboratorio", 190, JLabel.LEFT ),
    new FarmaColumnData( "Cantidad", 55, JLabel.RIGHT),
    new FarmaColumnData( "Venta", 65, JLabel.RIGHT),
    new FarmaColumnData( "Stock", 65, JLabel.RIGHT),
	};
	
	public static final Object[] defaultValuesListaVentasProducto = {" "," ", " ", " ", " ", " "," "};
  
  
    public static final FarmaColumnData columnsListaVentasDiaMes[] = 
    {
    new FarmaColumnData( "Periodo", 140, JLabel.LEFT ),
	  new FarmaColumnData( "Pedidos", 80, JLabel.RIGHT ),
    new FarmaColumnData( "Vta. Inc. IGV", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Vale Promedio", 100, JLabel.RIGHT ),    
    new FarmaColumnData( "Und X Vale", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Pr Prom x Und", 100, JLabel.RIGHT),
    new FarmaColumnData( "DiaSem", 0, JLabel.RIGHT)
    };
    
	public static final Object[] defaultValuesListaVentasDiaMes = {" ", " ", " ", " ", " "," "," "};
   
  public static final FarmaColumnData columnsListaVentasPorDia[] = {
    new FarmaColumnData( "Fecha", 73, JLabel.CENTER ), //90
    new FarmaColumnData( "Pedidos", 50, JLabel.RIGHT ), //70
    new FarmaColumnData( "Tic.Ini", 79, JLabel.CENTER ), //JCHAVEZ 13.07.2009.n
    new FarmaColumnData( "Bol.Ini", 79, JLabel.CENTER ),
    new FarmaColumnData( "Fac.Ini", 79, JLabel.CENTER ),
    new FarmaColumnData( "No.Tic", 39, JLabel.RIGHT), //JCHAVEZ 13.07.2009.n 60
    new FarmaColumnData( "No.Bol", 39, JLabel.RIGHT),
    new FarmaColumnData( "No.Fac", 39, JLabel.RIGHT),
    new FarmaColumnData( "No.Tic.Anul", 78, JLabel.RIGHT),//JCHAVEZ 13.07.2009.n  80
    new FarmaColumnData( "No.Bol.Anul", 78, JLabel.RIGHT),
    new FarmaColumnData( "No.Fact.Anul", 80, JLabel.RIGHT),
    new FarmaColumnData( "Total", 81, JLabel.RIGHT),
    /**
     * @author : dubilluz
     * @author : 28.08.2007
     */
    new FarmaColumnData( "Fechaordenar",0, JLabel.CENTER)
	};
	public static final Object[] defaultValuesListaVentasPorDia = {" "," ", " ", " ", " ", " "," "," "," "," "," "," "};//JCHAVEZ 13.07.2009.n agregu� 3 elementos
	
  public static final FarmaColumnData columnsListaDetalladoResumenVta[] = {
    new FarmaColumnData( "Fecha", 130, JLabel.CENTER),
	  new FarmaColumnData( "T", 20, JLabel.LEFT ),
    new FarmaColumnData( "Nro Comprobante", 100, JLabel.RIGHT),
    new FarmaColumnData( "Estado", 80, JLabel.CENTER),
    new FarmaColumnData( "Items", 50, JLabel.RIGHT),
    new FarmaColumnData( "Unid Vta", 90, JLabel.LEFT),
    new FarmaColumnData( "Prec Vta", 60, JLabel.RIGHT),
    new FarmaColumnData( "Prec Total", 60, JLabel.RIGHT),
    new FarmaColumnData( "Usuario", 90, JLabel.LEFT)
	};
	public static final Object[] defaultValuesListaDetalladoResumenVta = {" "," ", " ", " ", " ", " "," "," "," "};

   public static final String VNOMBREINHASHTABLEVENDEDOR ="IND_CAMPO_ORDENAR_VENDEDOR";
   public static final String VNOMBREINHASHTABLEREGISTROVENTAS ="IND_CAMPO_ORDENAR_REGISTROVENTAS";
   public static final String VNOMBREINHASHTABLEVTASPRODUCTO ="IND_CAMPO_ORDENAR_VTASPRODUCTO";
   public static final String VNOMBREINHASHTABLEDETALLEVENTAS ="IND_CAMPO_ORDENAR_DETALLEVENTAS";
   public static final String VNOMBREINHASHTABLERESUMENVENTAS ="IND_CAMPO_ORDENAR_RESUMENVENTAS";
   public static final String VNOMBREINHASHTABLEDETALLEVENTASVENDEDOR ="IND_CAMPO_ORDENAR_DETALLEVENTASVENDEDOR";
  public static final String VNOMBREINHASHTABLECORRECCIONCOMPROBANTES ="IND_CAMPO_ORDENAR_CORRECCIONCOMPROBANTES";

   public static final String TIP_COMP_BOLETA = "01";
   public static final String TIP_COMP_FACTURA = "02";
   public static final String TIP_COMP_GUIA = "03";
   public static final String TIP_PEDIDO_DELIVERY = "D";
   public static final String TIP_PEDIDO_INSTITUCIONAL = "I";
    public static final String TIP_COMP_TICKET = "05"; //JCHAVEZ 13.07.2009.n
   
   public static final String TIP_FILTRO = "FILTRAR";
  
   public static final String[] ORDEN = {"ASC","DESC"};
   public static final String[] ORDENVAL ={FarmaConstants.ORDEN_ASCENDENTE,FarmaConstants.ORDEN_DESCENDENTE};
   
   public static final String VNOMBREINHASHTABLERESUMENPORDIA ="IND_CAMPO_ORDENAR_RESUMEN_POR_DIA";
  
  //Hist�rico de Creaci�n/Modificaci�n
  //ERIOS      27.03.2005   Creaci�n
  //DlgVentasPorHora
  public static final FarmaColumnData columnsListaVentasHora[] = {
    new FarmaColumnData( "Horarios", 120, JLabel.CENTER ),
	  new FarmaColumnData( "Pedidos", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Venta", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Vta. Promedio", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Unids. Prom.", 100, JLabel.RIGHT),
    new FarmaColumnData( "Precio Prom.", 100, JLabel.RIGHT)
	};
	
	public static final Object[] defaultValuesListaVentasHora = {" ", " ", " ", " ", " "," "};
  
   
    public static String vVentasDiaMes = "VentasDiasMes";
   
   public static String[] vOrden = {"ASC","DESC"};
   public static String[] vOrdenval ={FarmaConstants.ORDEN_ASCENDENTE,FarmaConstants.ORDEN_DESCENDENTE};

  //Hist�rico de Creaci�n/Modificaci�n
  //ERIOS      27.03.2005   Creaci�n
  //DlgVentasPorHora 
  public static String vVentasPorHora = "VentasporHora";
  public static String[] vCodDiaSemana = {"1","2","3","4","5","6","7"};
  public static String[] vDescDiaSemana = {"Lunes","Martes","Mi�rcoles","Jueves","Viernes","S�bado","Domingo"};
  
  public static String vNombreInHashtableporHora ="IND_CAMPO_ORDENAR_HORA";  
  public static String[] vCodCampoporHora = {"0","1", "2","3" ,"4","5"};
  public static String[] vDescCampoporHora = {"Horarios","Pedidos","Venta", "Vta. Promedio", "Unids. Prom.","Precio Prom."};
  
  //Hist�rico de Creaci�n/Modificaci�n
  //ERIOS      06.07.2006   Creaci�n
  //DlgProductoFaltaCero
  public static final FarmaColumnData columnsListaFaltaCero[] = {
    new FarmaColumnData( "Codigo", 60, JLabel.CENTER ),
	  new FarmaColumnData( "Descripcion", 210, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 105, JLabel.LEFT ),
    new FarmaColumnData( "Laboratorio", 195, JLabel.LEFT ),
    new FarmaColumnData( "Cantidad", 55, JLabel.RIGHT),
    new FarmaColumnData( "DescUnidOrd", 0, JLabel.RIGHT)
	};
	
	public static final Object[] defaultValuesListaFaltaCero = {" ", " ", " ", " ", " ", " "};
  
   public static final FarmaColumnData columnsListaDetFaltaCero[] = {
    new FarmaColumnData( "Solicitante", 210, JLabel.LEFT ),
    new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
    new FarmaColumnData( "FechaOrd", 0, JLabel.LEFT ),
    new FarmaColumnData( "SecUsu", 0, JLabel.LEFT )
	};
	
	public static final Object[] defaultValuesListaDetFaltaCero = {" ", " ", " "," "};
  
  
  public static final FarmaColumnData columnsListaProveedoresProdVta[] = {
    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
    new FarmaColumnData( "Laboratorio", 230, JLabel.LEFT ),
    new FarmaColumnData( "Total", 100, JLabel.RIGHT ),
    new FarmaColumnData( "Cantidad", 100, JLabel.RIGHT )
	};
	public static final Object[] defaultValuesListaProveedoresProdVta = {" ", " ", " "," "};
  
  public static final FarmaColumnData columnsListaConsolidadoVtasProducto[] = {
    new FarmaColumnData( "Vendedor", 180, JLabel.LEFT ),
    new FarmaColumnData( "Unid Vendidas", 120, JLabel.RIGHT ),
    new FarmaColumnData( "Total S/.", 110, JLabel.RIGHT )
	};
	public static final Object[] defaultValuesListaListaConsolidadoVtasProducto = {" ", " ", " "};
	
  
  //Hist�rico de Creaci�n/Modificaci�n
  //ERIOS      17.07.2006   Creaci�n
  //DlgProductosABC
  public static final FarmaColumnData columnsListaProdABC[] = {
    new FarmaColumnData( "Codigo", 60, JLabel.CENTER ),
	  new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 105, JLabel.LEFT ),
    new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
    new FarmaColumnData( "Stock", 70, JLabel.RIGHT),
    new FarmaColumnData( "Unid. Vend.", 55, JLabel.RIGHT),
    new FarmaColumnData( "Monto", 70, JLabel.RIGHT),
    new FarmaColumnData( "Tipo", 40, JLabel.CENTER)
    //new FarmaColumnData( "DescUnidOrd", 0, JLabel.RIGHT),
    
	};
	
	public static final Object[] defaultValuesListaProdABC = {" ", " ", " ", " ", " ", " ", " ", " "};  
  
  //Hist�rico de Creaci�n/Modificaci�n
  //Paulo      29.08.2006   Creaci�n
  //DlgProductosABC
  public static String vProductosABC = "ProductosABC";
  public static String[] vCodTipo = {"A","B","C"};
  public static String[] vDescTipo = {"Tipo A","Tipo B","Tipo C"};

  //Hist�rico de Creaci�n/Modificaci�n
  //Paulo      29.08.2006   Creaci�n
  //DlgProductosABC  
  public static String vNombreInHashtableABC ="IND_CAMPO_ORDENAR_ABC";
  public static String[] vCodABC = {"0","1", "2","3" ,"4","5","6","7"};
  public static String[] vDescCampoABC = {"Codigo","Descripcion","Unidad", "Laboratorio", "Stock","Unid Vend","Monto","Tipo"};

  public static final FarmaColumnData columnsListaPedAnuNoCob[] = {
    new FarmaColumnData("N�mero", 80, JLabel.CENTER ),
	  new FarmaColumnData("Fecha", 120, JLabel.LEFT ),
    new FarmaColumnData("Cliente", 220, JLabel.LEFT ),
    new FarmaColumnData("Monto Neto", 85, JLabel.RIGHT ),
    new FarmaColumnData("", 0, JLabel.LEFT ),
    new FarmaColumnData("", 0, JLabel.LEFT ),
    new FarmaColumnData("", 0, JLabel.LEFT )
	};
	
  public static final Object[] defaultValuesListaPedAnuNoCob = {" "," "," "," "};
  
  public static final FarmaColumnData columnsListaUnidVtaLocal[] = {
    new FarmaColumnData("C�digo", 60, JLabel.CENTER ),
    new FarmaColumnData("Descripci�n", 220, JLabel.LEFT ),
    new FarmaColumnData("Unidad Ptcion.", 90, JLabel.LEFT ),
    new FarmaColumnData("Laboratorio", 150, JLabel.LEFT ),
    new FarmaColumnData("Stock", 70, JLabel.RIGHT ),
    new FarmaColumnData("Farma", 65, JLabel.CENTER ),
    new FarmaColumnData("Periodo", 70, JLabel.RIGHT ),
    new FarmaColumnData("Vta. Periodo", 80, JLabel.RIGHT ),
    new FarmaColumnData("# Dias Vta.", 80, JLabel.RIGHT ),
    new FarmaColumnData("Vta. Rep.", 80, JLabel.RIGHT ),
    new FarmaColumnData("Vta. X D�a", 80, JLabel.RIGHT ),
    new FarmaColumnData("D�as Inv.", 70, JLabel.RIGHT ),
	};
  
  public static final Object[] defaultValuesListaUnidVtaLocal = {" "," "," "," "," "," "," "," "," "," "," "};

  public static final String VNOMBREINHASHTABLEUNIDVTALOCAL ="IND_CAMPO_ORDENAR_UNIDVTALOCAL";

/**
 * Cambio
 * @Autor: Luis Reque
 * @Fecha: 09/05/2007
 * */
 
 /**
  * MOdificado 
  * @author : dubilluz
  * @since  : 21.08.2007
  */
  public static final FarmaColumnData columnsListaProdSinVtaNDias[] = {

	  new FarmaColumnData("C�digo", 60, JLabel.CENTER ),
    new FarmaColumnData("Descripci�n", 220, JLabel.LEFT ),
    new FarmaColumnData("Unidad.Ptcion.", 90, JLabel.LEFT ),
    new FarmaColumnData("Laboratorio", 150, JLabel.LEFT ),
    new FarmaColumnData("Stock", 60, JLabel.RIGHT ),
    new FarmaColumnData("Farma", 60, JLabel.CENTER ),
    new FarmaColumnData(/*"Per. Sin Vta."*/"D�asSinVta", 70, JLabel.RIGHT),
    new FarmaColumnData(/*"Per. Stock"*/"D�asConStk", 80, JLabel.RIGHT ),
    //new FarmaColumnData("Stock Libre", 80, JLabel.RIGHT ),
    //new FarmaColumnData("Stock Tras.", 80, JLabel.RIGHT ),
    new FarmaColumnData("Unid.Vta.", /*80*/0, JLabel.RIGHT ),
    new FarmaColumnData("Per.Vta", /*70*/0, JLabel.RIGHT ),
    new FarmaColumnData("U.V.Per.", /*70*/0, JLabel.RIGHT ),
    //new FarmaColumnData("# Result", 70, JLabel.RIGHT ),
    //new FarmaColumnData("Result", 70, JLabel.RIGHT ),
    
    
	};
  
  public static final Object[] defaultValuesListaProdSinVtaNDias = {" "," "," "," "," "," "," "," "," "," "," "};

  public static final String VNOMBREINHASHTABLEPRODSINVTANDIAS ="IND_CAMPO_ORDENAR_PRODSINVTANDIAS";
  
  /** Descripcion del listado de las formas de pago por pedido
   * @Author: JCORTEZ
   * @Since: 04/0/07
   */
    public static final FarmaColumnData columnsListaFormasPago[] = {
	  new FarmaColumnData("C�digo", 80, JLabel.CENTER ),
    new FarmaColumnData("Descripci�n", 180, JLabel.LEFT ),
    new FarmaColumnData("Importe", 80, JLabel.RIGHT ),
    new FarmaColumnData("Moneda", 80, JLabel.LEFT ),
    new FarmaColumnData("Vuelto", 75, JLabel.RIGHT ),
    new FarmaColumnData("Importe Total", 130, JLabel.RIGHT ),
    new FarmaColumnData("Cajero",100 , JLabel.LEFT),
	};
  
  public static final Object[] defaultValuesListaFormasPago = {" "," "," "," "," "," "," "};
  
    public static String ACCION_MOSTRAR_TOTALES = "TOTALES";
    public static String ACCION_MOSTRAR_MEZON = "MEZON";
    public static String ACCION_MOSTRAR_DELIVERY = "DELIVERY";
    public static String  ACCION_MOSTRAR_INSTITUCIONAL ="INSTITUCIONAL";
    // TIPOS DE VENTA
    // 01 --Mezon
    // 02 --Delivery
    // 03 --Vta Institucional
    public static String Tipo_Venta_Mezon ="01";
    public static String Tipo_Venta_Delivery ="02";
    public static String Tipo_Venta_Institucional ="03";
  
}
 
