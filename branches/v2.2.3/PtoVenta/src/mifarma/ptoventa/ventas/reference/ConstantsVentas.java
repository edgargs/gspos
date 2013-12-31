package mifarma.ptoventa.ventas.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsVentas.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      14.02.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class ConstantsVentas {

  public ConstantsVentas() {
  }
  
  //Lista de Productos
  public static final FarmaColumnData columnsListaProductos[] = {
    new FarmaColumnData( "Sel", 30, JLabel.CENTER ),
    new FarmaColumnData( "Código", 50, JLabel.LEFT ),
    new FarmaColumnData( "Descripción", 200, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),
    new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
    new FarmaColumnData( "Stock", 45, JLabel.RIGHT ),
    new FarmaColumnData( "Precio", 65, JLabel.RIGHT ),
    new FarmaColumnData( "Zan", 40, JLabel.RIGHT ),
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador producto congelado
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor de fraccion de local
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor precio lista
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor igv producto
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador de producto farma
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//indicador prod virtual
    new FarmaColumnData( "", 0, JLabel.LEFT ),//tipo de prod virtual
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador prod refrigerado
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador tipo producto
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador  producto promocion
    new FarmaColumnData( "ordLista", 0, JLabel.LEFT ),
    new FarmaColumnData( "indProdEncarte", 0, JLabel.LEFT ),
    new FarmaColumnData( "indOrigen", 0, JLabel.LEFT )
        
  };

  public static final Object[] defaultValuesListaProductos =
  { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", 
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " "};
  
  //Lista de Productos Alternativos
  /*public static final FarmaColumnData columnsListaProductosAlternativos[] = {
    new FarmaColumnData( "Sel", 30, JLabel.CENTER ),
    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 85, JLabel.LEFT ),
    new FarmaColumnData( "Laboratorio", 165, JLabel.LEFT ),
    new FarmaColumnData( "Stock", 45, JLabel.RIGHT ),
    new FarmaColumnData( "Precio", 65, JLabel.RIGHT ),
    new FarmaColumnData( "Zan", 40, JLabel.RIGHT ),
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador producto congelado
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor de fraccion de local
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor precio lista
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor igv producto
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador de producto farma
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//indicador prod virtual
    new FarmaColumnData( "", 0, JLabel.LEFT ),//tipo de prod virtual
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//indicador prod refrigerado
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador tipo de prod 
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador de orden
  };
	
	public static final Object[] defaultValuesListaProductosAlternativos = {" "," ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," "," "};
  */
  //Lista de Productos Complementarios
  public static final FarmaColumnData columnsListaProductosComplementarios[] = {
    new FarmaColumnData( "Código", 50, JLabel.LEFT ),
    new FarmaColumnData( "Descripción", 200, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 85, JLabel.LEFT ),
    new FarmaColumnData( "Laboratorio", 165, JLabel.LEFT ),
    new FarmaColumnData( "Stock", 45, JLabel.RIGHT ),
    new FarmaColumnData( "Precio", 65, JLabel.RIGHT ),
    new FarmaColumnData( "Zan", 40, JLabel.RIGHT ),
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador producto congelado
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor de fraccion de local
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor precio lista
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor igv producto
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador de producto farma
    new FarmaColumnData( "", 0, JLabel.RIGHT ),//indicador prod virtual
    new FarmaColumnData( "", 0, JLabel.LEFT ),//tipo de prod virtual
  };
	
	public static final Object[] defaultValuesListaProductosComplementarios = {" "," ", " ", " ", " "," ", " ", " ", " ", " ", " "," "};
  
  //lista de relacion de acciones terapeuticas para productos complementarios
  public static final FarmaColumnData columnsListaRelacionAccTerap[] = {
    new FarmaColumnData( "Código", 90, JLabel.LEFT ),
    new FarmaColumnData( "Descripción", 250, JLabel.LEFT ),
  };
		
  public static final Object[] defaultValuesListaRelacionAccTerap = {" ", " "};
  
  //Lista de Resumen de Pedido
  public static final FarmaColumnData columnsListaResumenPedido[] = 
  {
    new FarmaColumnData("Código",60,JLabel.LEFT),
    new FarmaColumnData("Descripción",225,JLabel.LEFT),
    new FarmaColumnData("Unidad",88,JLabel.LEFT),
    new FarmaColumnData("Precio",60,JLabel.RIGHT),
    new FarmaColumnData("Cantidad",60,JLabel.RIGHT),
    new FarmaColumnData("%Dscto",58,JLabel.RIGHT),
    new FarmaColumnData("Precio Venta",78,JLabel.RIGHT),
    new FarmaColumnData("Total",68,JLabel.RIGHT),
    new FarmaColumnData("",0,JLabel.RIGHT),//valor del bono
    new FarmaColumnData("",0,JLabel.RIGHT),//nombre de laboratorio
    new FarmaColumnData("",0,JLabel.RIGHT),//valor de fraccion
    new FarmaColumnData("",0,JLabel.RIGHT),//porcentaje de igv producto
    new FarmaColumnData("",0,JLabel.RIGHT),//valor de igv producto
    new FarmaColumnData("",0,JLabel.RIGHT),//numero telefonico si es recarga automatica
    new FarmaColumnData("",0,JLabel.RIGHT ),//indicador prod virtual
    new FarmaColumnData("",0,JLabel.LEFT ),//tipo de prod virtual
    new FarmaColumnData("",0,JLabel.LEFT ),//indicador prod controla stock
    new FarmaColumnData("",0,JLabel.LEFT ),//precio de lista original
    new FarmaColumnData("",0,JLabel.LEFT ),//indica si esta en promocion
    new FarmaColumnData("indOrigen",0,JLabel.LEFT ),
    new FarmaColumnData("",0,JLabel.LEFT ),//indica si esta en promocion
    new FarmaColumnData("porDcto2",0,JLabel.LEFT ),
    new FarmaColumnData("indTratamiento",0,JLabel.LEFT ),
    new FarmaColumnData("cantxDiaTra",0,JLabel.LEFT ),
    new FarmaColumnData("cantDiasTra",0,JLabel.LEFT ),
    new FarmaColumnData("codCupon",0,JLabel.LEFT )
  };
  
  public static final Object[] defaultValuesListaResumenPedido =
  { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", 
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " "};
  
  //Lista de Principios Activos
  public static final FarmaColumnData columnsListaPrincipiosActivos[] = {
    new FarmaColumnData( "Código", 80, JLabel.CENTER ),
    new FarmaColumnData( "Descripción", 230, JLabel.LEFT ),
  };

  public static final Object[] defaultValuesListaPrincipiosActivos = {" ", " "};
	
     //Lista de Acciones Terapeuticas 
  public static final FarmaColumnData columnsListaAccionesTerapeuticas[] = {
    new FarmaColumnData( "Código", 80, JLabel.CENTER ),
    new FarmaColumnData( "Descripción", 230, JLabel.LEFT ),
  };
	
  public static final Object[] defaultValuesListaAccionesTerapeuticas = {" ", " "};
  
  //lista de los medicos
  public static final FarmaColumnData columnsListaMedicos[] = {
    new FarmaColumnData( "Código", 70, JLabel.CENTER ),
    new FarmaColumnData( "Matricula", 70, JLabel.LEFT ),
    new FarmaColumnData( "Nombre", 280, JLabel.LEFT ),
  };
  public static final Object[] defaultValuesListaMedicos = {" ", " "," "};
  
  
  public static final String INDICADOR_A = "A";
  public static final String INDICADOR_D = "D";
  
  public static final String TIPO_PEDIDO_MESON = "01";
  public static final String TIPO_PEDIDO_DELIVERY = "02";
  public static final String TIPO_PEDIDO_INSTITUCIONAL = "03";
  
  public static final String COD_LOCAL_DELIVERY = "999";
  public static final String COD_LOCAL_INSTITUCIONAL = "998";
  
  public static final String TIPO_COMP_BOLETA = "01";
  public static final String TIPO_COMP_FACTURA = "02";
  public static final String TIPO_COMP_GUIA = "03";
  public static final String TIPO_COMP_NOTA_CREDITO = "04";
  public static final String TIPO_COMP_TICKET = "05";
  
  public static final String ESTADO_PEDIDO_PENDIENTE = "P";
  public static final String ESTADO_PEDIDO_COBRADO = "C";
  public static final String ESTADO_PEDIDO_ANULADO = "N";
  public static final String ESTADO_PEDIDO_SIN_COMPROBANTE = "S";
  
  public static final String ESTADO_PEDIDO_DETALLE_ACTIVO = "A";
  public static final String ESTADO_PEDIDO_DETALLE_ANULADO = "N";
  
  public static final String TIPO_MATRICULA = "1";
  public static final String TIPO_APELLIDO = "2";
  
  public static final String NAME_TABLA_PRODUCTOS = "tblProductos";
  public static final String NAME_TABLA_SUSTITUTOS = "tblListaSustitutos";
 
  public static final String NAME_TABLA_PRODUCTOS_RECETA = "tblProductosReceta";
  public static final String NAME_TABLA_PRODUCTOS_ALTERNATIVOS_RECETA = "tblListaProductosAlternativosReceta";
  
  public static final String TIPO_PROD_VIRTUAL_RECARGA = "R";
  public static final String TIPO_PROD_VIRTUAL_TARJETA = "T";
  public static final String TIPO_PROD_VIRTUAL_MAGISTRAL= "M";  
  
  public static final String TARJ_RECARGA_MOVISTAR_VIRTUAL = "510558";
  public static final String TARJ_RECARGA_CLARO_VIRTUAL = "510559";

  public static final String NAME_TABLA_PROD_PRINC_ACT = "tblPrincAct";
  public static final String NAME_TABLA_PROD_COMPLEMENT = "tblProductosComplementarios";


    public static final String[] IND_TIPO_PROD_COD = {"V","A","D","N","O"};
    public static final String[] IND_TIPO_PROD_DESC = {"VIGENTE","AGOTADO","DESCONTINUADO","NO DISPONIBLE","VENDER OTRO"};
    
  /**
   * Constantes de idicador si es Prod_SImple o de una Promocion
   * @author : dubilluz
   * @since  : 03.07.2007
   */
  public static final int IND_PROD_SIMPLE = 1;
  public static final int IND_PROD_PROM   = 2;
  
 /**
  * Cantidad maxima de tarjetas virtual que pueden comprar
  * @author dubilluz
  * @since  28.09.2007
  */
  public static final int CANT_PROD_TARJ_VIRTUAL_MAXIMO = 1;  
  
  /**
   * Se describe las columnas de la tabla VTA_PROMOCION.
   * @author Jorge Cortez
   * @since  13.06.2007
   */   
  public static final FarmaColumnData columnsListaPromociones[] =
  { 
    // Se modifico 28.02.2007 Dubilluz
    new FarmaColumnData("Cod Prom",60, JLabel.CENTER), 
    new FarmaColumnData("Descripción", 271, JLabel.LEFT), 
    new FarmaColumnData("Precio", 80, JLabel.RIGHT), 
    new FarmaColumnData("Stock", 50, JLabel.RIGHT), 
    new FarmaColumnData("OBS", 120, JLabel.LEFT), 
    new FarmaColumnData("indPermitido",0, JLabel.LEFT),             
    new FarmaColumnData("Descripción Corta",0, JLabel.LEFT), 
    new FarmaColumnData("Paquete 1",0, JLabel.CENTER),
    new FarmaColumnData("Paquete 2",0, JLabel.CENTER),
    new FarmaColumnData("",0, JLabel.CENTER),//Desc Promocion
    };
    
  public static final Object[] defaultValuesListaPromociones = { " ", " ", " ", " ", " "," "," "," ", " ", " "};
  
    
  /**
   * Se describe las columnas de los paquetes de la promocion 
   * @author Jorge Cortez
   * @since  13.06.2007
   */   
   
  public static final FarmaColumnData columnsDetallePromocion[] =
  { 
    new FarmaColumnData("Código",53, JLabel.CENTER), 
    new FarmaColumnData("Descripción",212, JLabel.LEFT), 
    new FarmaColumnData("Unidad",85, JLabel.CENTER),
    new FarmaColumnData("Laboratorio",219, JLabel.LEFT), 
    new FarmaColumnData("Precio",65, JLabel.CENTER),
    new FarmaColumnData("Unidades",60, JLabel.CENTER),
    new FarmaColumnData("Disponible",0, JLabel.CENTER),
    new FarmaColumnData("",0, JLabel.CENTER),
    new FarmaColumnData("",0, JLabel.CENTER),
    new FarmaColumnData("",0, JLabel.CENTER),
    new FarmaColumnData("",0, JLabel.CENTER)};
       
    
    
  public static final Object[] defaultValuesDetallePromocions = {" "," "," "," "," "," "," "," "," "," "};
  
  public static  String ESTADO_LISTADO = "P";//lista promociones por producto, y "T" lista todas las promociones
 

  //DlgStockLocales
  public static final FarmaColumnData columnsListaStockLocalesPreferidos[] = {
	    new FarmaColumnData( "Código", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripción", 150, JLabel.LEFT ),
      new FarmaColumnData( "Stock Local", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Unidad Venta", 120, JLabel.LEFT ),
	};   
  public static final Object[] defaultValuesListaStockLocalesPreferidos = {" ", " ", " ", " "};

  public static final FarmaColumnData columnsListaStockDemasLocales[] = {
	    new FarmaColumnData( "Código", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripción", 150, JLabel.LEFT ),
      new FarmaColumnData( "Stock Local", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Unidad Venta", 120, JLabel.LEFT ),
	};   
  public static final Object[] defaultValuesListaStockDemasLocales = {" ", " ", " ", " "};
 
  /**
   * Constante para el Precio de Venta 
   * @autho dubilluz
   * @since 31.10.2007
   */
  public static String PrecioVtaRecargaTarjeta  = "1";
  
  
  /**
   * Constantes que indican el listado de origen del producto
   * @author JCORTEZ
   * @since  15.04.2008
   */
  public static final String IND_ORIGEN_COMP = "4";
  public static final String IND_ORIGEN_OFER = "5";
  public static final String IND_OFER = "6";
  public static final String IND_ORIGEN_REC = "7"; //rios
  
  /**
   * Variables para el Filtro desde resumen (Encarte, Cupon)
   * @author  JCORTEZ
   * @since   17.04.2008
   * */
    public static String NOM_HASTABLE_CMBFILTROTIPO = "CMB_FILTRO_TIPO";
    public static final String[] vCodColumna ={ "4","5"};
    public static final String[] vDescColumna ={"Prod.Encarte","Prod.Cupon"};
    
  /**
   * Variables para el Filtro desde resumen (Encarte, Cupon)
   * @author  JCORTEZ
   * @since   17.04.2008
   * */
    public static final FarmaColumnData columnsListaTipoFiltro[] = {
	    new FarmaColumnData( "Descripcion", 300, JLabel.LEFT ),
      new FarmaColumnData( "", 0, JLabel.LEFT )
	  };   
  public static final Object[] defaultValuesListaTipoFiltro = {" "," "};
  
  /**
   * Columnas del listado de cupones.
   * @author Edgar Rios Navarro
   * @since 02.07.2008
   */
  public static final FarmaColumnData columnsListaCupones[] =
  { 
    new FarmaColumnData("Mensaje",690, JLabel.LEFT)};
   /* new FarmaColumnData("Campaña",0, JLabel.LEFT), //50
    new FarmaColumnData("Tipo",0, JLabel.LEFT), //20
    new FarmaColumnData("Valor",0, JLabel.CENTER)};//50*/
    
  public static final Object[] defaultValuesListaCupones = {" "};
  
  /**
   * Constantes para definir los tipos de campañas
   * @author dubilluz
   * @since  10.07.2008
   */
  public static final String TIPO_MULTIMARCA = "M";
  public static final String TIPO_CAMPANA_CUPON = "C";

  public static final String TIPO_PORCENTAJE = "P";
  public static final String TIPO_MONTO = "M";
  
  /**
   * @autor JCALLO
   * @since 01.10.2008
   * */
  public static final String ACTIVO = "ACTIVO";
  public static final String INACTIVO = "INACTIVO";
  
  
  public static final String LINEAS_BOLETA_SIN_CONVENIO = "230";
  public static final String LINEAS_BOLETA_CON_CONVENIO = "231";
  public static final String LINEAS_FACTURA_SIN_CONVENIO = "232";
  public static final String LINEAS_FACTURA_CON_CONVENIO = "233";
  public static final String LINEAS_TICKET = "269";
  
  /**
   *@AUTHOR  JCORTEZ
   *@SINCE  04.08.09
   * */
   
   //Lista de cupones a usar
   public static final FarmaColumnData columnsListaCuponesUsados[] = {
    new FarmaColumnData( "Sel", 25, JLabel.CENTER ),
    new FarmaColumnData( "Descripción", 340, JLabel.LEFT ),
    new FarmaColumnData( "Valor", 40, JLabel.CENTER ),
    new FarmaColumnData( "Fecha Emisión", 110, JLabel.CENTER ),
    new FarmaColumnData( "Código", 100, JLabel.LEFT ),
    new FarmaColumnData( "", 0, JLabel.LEFT )};//TEXTO CUPON
   
   public static final Object[] defaultValuesListaCuponesUsados = {" "," "," "," "," "," "};
   
   //INI ASOSA
   
    //Lista de Productos Cliente Perú
    public static final FarmaColumnData columnsListProds[] = {      
      //new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
      new FarmaColumnData( "Descripción", 200, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 130, JLabel.LEFT ),      
      //new FarmaColumnData( "VVF", 65, JLabel.RIGHT ),
      new FarmaColumnData( "Normal", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Club Lunes", 85, JLabel.RIGHT ),
      new FarmaColumnData( "Club 50 años", 85, JLabel.RIGHT )
    };

    public static final Object[] defaultValuesListProds =
    { " ", " ", " ", " ", " ", " ", " "};
    
    //INI ASOSA, 27.09.2010
    
    //Lista de Productos Cliente Bolivia
    public static final FarmaColumnData columnsListProdsBol[] = {      
      //new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
      new FarmaColumnData( "Descripción", 200, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 130, JLabel.LEFT ),      
      //new FarmaColumnData( "VVF", 65, JLabel.RIGHT ),
      new FarmaColumnData( "Normal", 75, JLabel.RIGHT )/*,
      new FarmaColumnData( "Club Lunes", 85, JLabel.RIGHT ),
      new FarmaColumnData( "Club 50 años", 85, JLabel.RIGHT )*/
    };

    public static final Object[] defaultValuesListProdsBol =
    { " ", " ", " ", " ", " "};
    
    public static final String COD_CIA_PERU = "001";
    public static final String COD_CIA_BOL = "002";
    
    //FIN ASOSA, 27.09.2010
    
    //FIN ASOSA

    //INI - ASOSA, 28.09.2010
    public static final String HASHTABLE_TIP_DIGEMID = "TIP_DIGEMID";
    public static final String[] TIP_DIGEMID_COD = { "1", "2"};
    public static final String[] TIP_DIGEMID_DESC = { "PRODUCTO", "DCI"};
    
    //Lista de Pincipios Activos
    public static final FarmaColumnData columnsListPrincAct[] = {      
      new FarmaColumnData( "Descripción", 250, JLabel.LEFT )
    };

    public static final Object[] defaultValuesListPrincAct =
    { " ", " "};
    
    //FIN - ASOSA, 28.09.2010

    //Lista de Productos Cliente Perú
    public static final FarmaColumnData columnsListProdsCDI[] = {            
      new FarmaColumnData( "Descripción", 200, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 130, JLabel.LEFT ),            
      new FarmaColumnData( "Normal", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Club Lunes", 85, JLabel.RIGHT ),
      new FarmaColumnData( "Club 50 años", 85, JLabel.RIGHT )
      //COD_PROD
      //IND_ACTIVO
    };

    public static final Object[] defaultValuesListProdsCDI =
    { " ", " ", " ", " ", " ", " ", " ", " "};
    
    public static final String HASHTABLE_TIPOS_COMPROBANTES = "TIPOS_COMPROBANTES";
    
    public static final String[] TIPOS_COMPROBANTES_CODIGO = {"05","01","02","03"};
    public static final String[] TIPOS_COMPROBANTES_DESCRIPCION = {"TICKET BOLETA","BOLETA","FACTURA","GUIA"};
    
    public static final FarmaColumnData columnsListaBusquedaMedico[] =
    { 
      new FarmaColumnData("CMP",100, JLabel.CENTER), 
      new FarmaColumnData("Nombre Completo", 300, JLabel.LEFT), 
      new FarmaColumnData("Tipo de Colegio", 190, JLabel.LEFT)
      };
      
    public static final Object[] defaultValuesListaBusquedaMedico = { " ", " ", " ", " ", " "};

    public static final FarmaColumnData columnsListaCopago[] = {
                new FarmaColumnData( "Descripcion", 300, JLabel.LEFT ),
          new FarmaColumnData( "", 0, JLabel.LEFT )
              };   
        public static final Object[] defaultValuesListaTipoCopago = {" "," "};
            
}
 
