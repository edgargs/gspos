package mifarma.ptoventa.inventariodiario.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaUtility;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsInvdiario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.10.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class ConstantsInvDiario
{
  public ConstantsInvDiario()
  {
  }

  /**
   * Columnas de la lista de productos inicio.
   * @author Edgar Rios Navarro
   * @since 23.10.2006
   */
  public static final FarmaColumnData[] columnsListaProductosInicio =
  { new FarmaColumnData("Codigo", 50, JLabel.CENTER), 
    new FarmaColumnData("Descripcion", 200, JLabel.LEFT), 
    new FarmaColumnData("Laboratorio", 150, JLabel.LEFT), 
    new FarmaColumnData("Presentacion", 100, JLabel.LEFT), 
    new FarmaColumnData("Unid. Venta", 100, JLabel.LEFT), 
//    new FarmaColumnData("U. Vends.", 50, JLabel.RIGHT), 
    new FarmaColumnData("Stock", 75, JLabel.RIGHT), 
    //new FarmaColumnData("Monto Tot.", 70, JLabel.RIGHT), 
    new FarmaColumnData("Tip", 30, JLabel.CENTER),
    new FarmaColumnData("", 0, JLabel.CENTER)//UNIDAD PRESENTACION
    };

  /**
   * Valores por defecto de la lista de productos inicio.
   * @author Edgar Rios Navarro
   * @since 23.10.2006
   */
  public static final Object[] defaultValuesListaProductosInicio =
  { " ", " ", " ", " ", " ", " ", " "," " };

  /**
   * Columnas de la lista de productos restantes para el invetario diario.
   * @author Edgar Rios Navarro
   * @since 24.10.2006
   */
  public static final FarmaColumnData[] columnsListaProductosInv =
  { new FarmaColumnData("Sel.", 30, JLabel.CENTER), 
    new FarmaColumnData("Codigo", 50, JLabel.CENTER), 
    new FarmaColumnData("Descripcion", 200, JLabel.LEFT), 
    new FarmaColumnData("Presentacion", 100, JLabel.LEFT), 
    new FarmaColumnData("Unid. Venta", 100, JLabel.LEFT), 
    new FarmaColumnData("Stock", 75, JLabel.RIGHT), 
    //new FarmaColumnData("U. Vends.", 50, JLabel.RIGHT), 
    //new FarmaColumnData("Monto Tot.", 70, JLabel.RIGHT),
    new FarmaColumnData("CodLab", 0, JLabel.RIGHT ),
    new FarmaColumnData("ValFrac", 0, JLabel.RIGHT) };

  /**
   * Valores por defecto de la lista de productos restantes para el invetario diario.
   * @author Edgar Rios Navarro
   * @since 24.10.2006
   */
  public static final Object[]  defaultValuesListaProductosInv =
  { " ", " ", " ", " ", " ", " "," "," " };
  
  
    public static final FarmaColumnData[] columnsListaDiferenciasConsolidado = {
                          new FarmaColumnData("Sel.", 30, JLabel.CENTER),
                          new FarmaColumnData("Codigo", 55, JLabel.CENTER),
                          new FarmaColumnData("Descripcion", 195, JLabel.LEFT),
                          new FarmaColumnData("Unid Pres.", 105, JLabel.LEFT),                          
                          new FarmaColumnData("Dif.", 60, JLabel.RIGHT),
                          new FarmaColumnData("Stk Fis.", 40, JLabel.RIGHT), 
                          new FarmaColumnData("Precio", 55, JLabel.RIGHT), 
                          new FarmaColumnData("Laboratorio",70, JLabel.LEFT),
                          new FarmaColumnData("Fecha", 55, JLabel.LEFT),
                          new FarmaColumnData("Stk Act.", 55, JLabel.RIGHT)
                          //ocultas
                          /*
                          TILP.SEC_TOMA_INV  || 'Ã' ||  -- 9
                          VK.SEC_KARDEX      || 'Ã' ||  -- 10
                          VK.CANT_MOV_PROD   || 'Ã' ||  -- 11
                          VK.VAL_FRACC_PROD    -- 12
                           * */
    };

          public static final Object[] defaultValuesListaDiferenciasConsolidado = {
                          " ", " ", " ", " ", " ", " "," ",
                          " ", " ", " ", " ", " ", " "," "};
  

  public static String vNombreInHashtableDiferencias = 
    "IND_CAMPO_ORDENAR_DIFERENCIAS";
  public static String[] vCodDiferencia =
  { "0", "1", "2", "3", "4", "5", "6" };
  public static String[] vDescCampoDiferencia =
  { "Codigo", "Descripcion", "Unidad Presentacion", "Stock Actual", 
    "Diferencia", "Precio", "Laboratorio" };

  public static final String TIPO_OPERACION_TOMA_INV = "I";
  public static final String TIPO_OPERACION_CONSULTA_HIST = "H";
  public static final String TIPO_FARMA = "01";
  public static final String TIPO_NO_FARMA = "02";
  public static final String TIPO_TODOS = "03";
  public static final String TIPO_PRINCIPIO_ACTIVO = "1";
  public static final String TIPO_ACCION_TERAPEUTICA = "2";
  public static final String TIPO_LABORATORIO = "3";

  public static final FarmaColumnData[] columnsTomasInventario = { 
    new FarmaColumnData("Nro Toma", 65, JLabel.LEFT), 
    new FarmaColumnData("Tipo Toma", 90, JLabel.LEFT), 
    new FarmaColumnData("Fecha Inicio", 125, JLabel.LEFT), 
    new FarmaColumnData("Estado", 90, JLabel.LEFT), 
    new FarmaColumnData("", 0,JLabel.LEFT), 
    new FarmaColumnData("",0,JLabel.LEFT)}  ;

  public static final Object[] defaultValuesTomasInventario = { " ", " ", " ", " ", " ", " " };
  
  public static final FarmaColumnData[] columnsLaboratoriosToma = {
    new FarmaColumnData("Codigo", 80, JLabel.LEFT),
    new FarmaColumnData("Laboratorio", 250, JLabel.LEFT),
    new FarmaColumnData("Estado", 100, JLabel.LEFT), };

	public static final Object[] defaultValuesLaboratoriosToma = { " ", " "," " };

  public static final FarmaColumnData[] columnsListaProductosXLaboratorio = {
    new FarmaColumnData("Codigo", 70, JLabel.CENTER),
    new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
    new FarmaColumnData("Unid. Presentacion", 120, JLabel.LEFT),
    new FarmaColumnData("C. Ent", 40, JLabel.RIGHT),
    new FarmaColumnData("C. Frac", 40, JLabel.RIGHT),
    new FarmaColumnData("Laboratorio", 150, JLabel.LEFT), 
    new FarmaColumnData("Valor Frac", 0, JLabel.RIGHT),
    new FarmaColumnData("Unid. Venta", 0, JLabel.RIGHT), };
    
	public static final Object[] defaultValuesListaProductosXLaboratorio = {" ", " ", " ", " " ," "," "," "," "};
  
  public static final FarmaColumnData[] columnsListaMovsKardex = { 
    new FarmaColumnData("Fecha",110,JLabel.CENTER),
    new FarmaColumnData("Descripcion",182,JLabel.LEFT),
    new FarmaColumnData("Tip. Doc",60,JLabel.LEFT),
    new FarmaColumnData("Num. Doc.",90,JLabel.CENTER),
    new FarmaColumnData("Mov",40,JLabel.RIGHT),
    new FarmaColumnData("Val. Fracc.",60,JLabel.RIGHT)};
  public static final Object[] defaultListaMovsKardex = {" "," "," ", " ", " ", " "};
  
  public static final FarmaColumnData[] columnsListaDiferenciasProductos = {
			new FarmaColumnData("Codigo", 65, JLabel.CENTER),
			new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
			new FarmaColumnData("Unid Presentacion", 110, JLabel.LEFT),
			new FarmaColumnData("Stock Actual", 75, JLabel.RIGHT),
			new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
			new FarmaColumnData("Precio", 65, JLabel.RIGHT), };

	public static final Object[] defaultValuesListaDiferenciasProductos = {
			" ", " ", " ", " ", " ", " " };


 /**
     * Columnas para el listado de productos al generar el Pedido
     * @author dubilluz
     * @since  15.06.2009
     */
    public static final FarmaColumnData[] columnsListaProductosPedido = 
    { new FarmaColumnData("Codigo", 70, JLabel.CENTER), 
      new FarmaColumnData("Descripcion", 220, JLabel.LEFT), 
      new FarmaColumnData("Unid. Presentacion", 120, JLabel.LEFT), 
      new FarmaColumnData("C. Ent", 40, JLabel.RIGHT), 
      new FarmaColumnData("C. Frac", 40, JLabel.RIGHT), 
      new FarmaColumnData("Laboratorio", 150, JLabel.LEFT), 
      new FarmaColumnData("Valor Frac", 0, JLabel.RIGHT), 
      new FarmaColumnData("Unid. Venta", 0, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaProductosPedido = 
    { " ", " ", " ", " ", " ", " ", " ", " " };

 /**
  * Columna para el listado de trabajadores seleccionados
  * @author dubilluz
  * @since  15.06.2009
  */
    public static final FarmaColumnData[] columnsListaTrabajadores =     
    { 
      new FarmaColumnData("DNI", 70, JLabel.CENTER), //DNI
      new FarmaColumnData("Codigo", 70, JLabel.CENTER), //codigo rrhh
      new FarmaColumnData("NombreCompleto", 220, JLabel.LEFT),
      new FarmaColumnData("Monto", 100, JLabel.RIGHT) 
      //codigo local
    };

    public static final Object[] defaultValuesListaTrabajadores = 
    { " ", " ", " ", " "," "};

     
  /**
   * Columnas para lista de trabajadores y se mostrara el codigo de RR.HH
   * @author dubilluz
   * @since  15.06.2009
   */
  public static final FarmaColumnData columnsListaMaeTrab[] = {
     new FarmaColumnData( "DNI", 70, JLabel.CENTER ),
     new FarmaColumnData( "Descripcion", 250, JLabel.LEFT ),
     new FarmaColumnData( "Codigo", 70, JLabel.CENTER ) //CODIGO RRHH
     //CODIGO DE TRAB SISTEMAS
  };  
  public static final Object[] defaultValuesListaMaeTrab = {" "," " , " "," "};  

    /**
     * 
     */
    public static final FarmaColumnData columnsListaDetTemporal[] = {
    new FarmaColumnData("Codigo",60,JLabel.LEFT),
    new FarmaColumnData("Descripcion",225,JLabel.LEFT),
    new FarmaColumnData("Unidad",88,JLabel.LEFT),
    new FarmaColumnData("Precio Venta",78,JLabel.RIGHT),
    new FarmaColumnData("Cantidad",60,JLabel.RIGHT),
    new FarmaColumnData("Total",68,JLabel.RIGHT)
    // Val Fraccion
    };  
    public static final Object[] defaultValuesListaDetTemporal = {" "," "," ",
                                                                  " "," "," ",
                                                                  " "};  

    //Columnas para el listado de pedidos pendientes DIARIO
    public static final FarmaColumnData[] columnsListaPendientes = {
        new FarmaColumnData("Nombre Cliente", 100, JLabel.LEFT),
        new FarmaColumnData("Num Ped Dia", 85, JLabel.CENTER),
        new FarmaColumnData("Numero", 80, JLabel.CENTER),
        new FarmaColumnData("Fecha", 120, JLabel.CENTER),
        new FarmaColumnData("Neto", 80, JLabel.RIGHT),
        new FarmaColumnData("IGV", 80, JLabel.RIGHT),
        new FarmaColumnData("Total S/.", 80, JLabel.RIGHT)
        /*
         new FarmaColumnData("Bruto", 0, JLabel.RIGHT),
        new FarmaColumnData("Dscto", 0, JLabel.RIGHT),
        new FarmaColumnData("Redo", 0, JLabel.RIGHT),
        new FarmaColumnData("RUC", 0, JLabel.CENTER),
        new FarmaColumnData("Ped. Dia.",0,JLabel.LEFT),
        new FarmaColumnData("",0,JLabel.LEFT), 
        new FarmaColumnData("Filtro",0,JLabel.LEFT)//,
        new FarmaColumnData("",0,JLabel.LEFT),//IND_PED_CONVENIO
        new FarmaColumnData("",0,JLabel.LEFT),//COD_CONVENIO
        new FarmaColumnData("",0,JLabel.LEFT),//COD_CLI_LOCAL
        */
    };
    public static final Object[] 
            defaultListaPendientes = 
                {" "," ", " ", " ", " ", 
                 " ", " ", " ", " ", " ",
                 " ", " "," "," "," "," "," "};
    
    
    public static final FarmaColumnData[] columnsDetallePedido = {
       new FarmaColumnData("Codigo",60,JLabel.CENTER),
       new FarmaColumnData("Producto",140,JLabel.LEFT),
       new FarmaColumnData("Unidad",120,JLabel.LEFT),
       new FarmaColumnData("P.Venta",100,JLabel.RIGHT),
       new FarmaColumnData("Cantidad",80,JLabel.RIGHT),
       new FarmaColumnData("Total",70,JLabel.RIGHT),
       new FarmaColumnData("Vendedor",90,JLabel.LEFT),
        };

     public static final Object[] defaultDetallePedido = {" ", " ", " ", " "," "," "," "};

    public static final FarmaColumnData[] columsListaFormasPago = 
    {
      new FarmaColumnData("Codigo",80,JLabel.CENTER),
      new FarmaColumnData("Forma de Pago",200,JLabel.LEFT),
      new FarmaColumnData("",0,JLabel.LEFT),//CODIGO OPERADOR TARJETA
      new FarmaColumnData("",0,JLabel.LEFT),//INDICADOR DE TARJETA DE CREDITO
      new FarmaColumnData("",0,JLabel.LEFT),//INDICADOR FORMA PAGO CUPON    
      new FarmaColumnData("",0,JLabel.LEFT),//INDICADOR CREDITO
    };
    
    public static final Object[] defaultListaFormasPago = {" ", " ", " "," "};

    public static final FarmaColumnData[] columsListaDetallePago = 
    {
      new FarmaColumnData("Codigo",75,JLabel.LEFT),
      new FarmaColumnData("Forma de Pago",150,JLabel.LEFT),
      new FarmaColumnData("Cant",45,JLabel.RIGHT),//cantidad de cupones
      new FarmaColumnData("Moneda",80,JLabel.LEFT),
      new FarmaColumnData("Monto",100,JLabel.RIGHT),
      new FarmaColumnData("Total",100,JLabel.RIGHT),
      new FarmaColumnData("",0,JLabel.LEFT),//codigo moneda
      new FarmaColumnData("",0,JLabel.LEFT),//
      new FarmaColumnData("",0,JLabel.LEFT),//numero tarjeta
      new FarmaColumnData("",0,JLabel.LEFT),//fec vencimiento tarj
      new FarmaColumnData("",0,JLabel.LEFT),//nombre cliente tarjeta
      new FarmaColumnData("",0,JLabel.LEFT),//CODIGO DE CONVENIO
    };
    
    public static final Object[] defaultListaDetallePago = {" ", " "," "," "," "," "," "," "," "," "," "};
    

}
