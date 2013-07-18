package mifarma.ptoventa.caja.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;


public class ConstantsCaja {
	public ConstantsCaja() {
	}

  public static final int CANT_MAX_CAJAS_PERMITIDAS=99;  //de 10 a 99

	public static final String MOVIMIENTO_APERTURA = "A";
	public static final String MOVIMIENTO_CIERRE = "C";
	public static final String MOVIMIENTO_REGISTRO_VENTA = "R";

	public static final String ESTADO_PENDIENTE = "P";
	public static final String ESTADO_COBRADO = "C";
	public static final String ESTADO_ANULADO = "N";
  public static final String FORMA_PAGO_EFECTIVO_SOLES = "00001";
  public static final String FORMA_PAGO_EFECTIVO_DOLARES = "00002";
  public static final String FORMA_PAGO_FARMAALIADA = "00013";
  public static final String FORMA_PAGO_DSCT_PERSONAL = "00019";
  public static final int ITEMS_TOTAL_POR_BOLETA = 8;
  public static final int ITEMS_TOTAL_POR_FACTURA = 10;
  public static final int ITEMS_TOTAL_POR_NOTA_CREDITO = 7;
  public static final String ESTADO_SIN_COMPROBANTE_IMPRESO = "S";
  public static final String RESULTADO_COMPROBANTE_EXITOSO = "TRUE";
  public static final String RESULTADO_COMPROBANTE_NO_EXITOSO = "FALSE";
  //SECUENCIAL DE IMPRESORA NOTA CREDITO
  public static final String SEC_IMPR_LOCAL_NOTA_CREDITO = "1";

   public static final String TIP_ORD_NUM_COMP = "1";
   public static final String TIP_ORD_CORRELATIVO = "2";
   
	public static final FarmaColumnData[] columnsListaImpresoras = {
			new FarmaColumnData("Cod. Imp.", 70, JLabel.CENTER),
			new FarmaColumnData("Desc. Imp", 100, JLabel.LEFT),
			new FarmaColumnData("Comprobante", 80, JLabel.LEFT),
			new FarmaColumnData("Serie", 40, JLabel.CENTER),
			new FarmaColumnData("Nro. de comprobante", 120, JLabel.CENTER),
			new FarmaColumnData("Cola de impresión", 160, JLabel.LEFT),
			new FarmaColumnData("Estado", 60, JLabel.CENTER),
			new FarmaColumnData("tip comp", 0, JLabel.LEFT)

	};

	public static final Object[] defaultValuesListaImpresoras = { " ", " "," ", " ", " ", " ", " ", " " };

	public static final FarmaColumnData[] columnsListaPendientes = {
      new FarmaColumnData("Nombre Cliente", 100, JLabel.LEFT),
			new FarmaColumnData("Num Ped Dia", 85, JLabel.CENTER),
			new FarmaColumnData("Numero", 80, JLabel.CENTER),
			new FarmaColumnData("Fecha", 120, JLabel.CENTER),
      new FarmaColumnData("Neto", 80, JLabel.RIGHT),
			new FarmaColumnData("IGV", 80, JLabel.RIGHT),
			new FarmaColumnData("Total S/.", 80, JLabel.RIGHT),
			new FarmaColumnData("Bruto", 0, JLabel.RIGHT),
			new FarmaColumnData("Dscto", 0, JLabel.RIGHT),
			new FarmaColumnData("Redo", 0, JLabel.RIGHT),
			new FarmaColumnData("RUC", 0, JLabel.CENTER),
			new FarmaColumnData("Ped. Dia.",0,JLabel.LEFT),
			new FarmaColumnData("",0,JLabel.LEFT), 
      new FarmaColumnData("Filtro",0,JLabel.LEFT),
      new FarmaColumnData("",0,JLabel.LEFT),//IND_PED_CONVENIO
      new FarmaColumnData("",0,JLabel.LEFT),//COD_CONVENIO
      new FarmaColumnData("",0,JLabel.LEFT),//COD_CLI_LOCAL
    };

	public static final Object[] defaultListaPendientes = {" "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "," "," "," "," "," "};

 public static final FarmaColumnData[] columnsDetallePedido = {
    new FarmaColumnData("Codigo",60,JLabel.CENTER),
    new FarmaColumnData("Producto",140,JLabel.LEFT),
    new FarmaColumnData("Unidad",120,JLabel.LEFT),
    //new FarmaColumnData("Precio",0,JLabel.RIGHT),
    //new FarmaColumnData("% Dscto",0,JLabel.RIGHT),
    new FarmaColumnData("P.Venta",50,JLabel.RIGHT),
    new FarmaColumnData("Cantidad",80,JLabel.RIGHT),
    new FarmaColumnData("Total",70,JLabel.RIGHT),
    new FarmaColumnData("Vendedor",90,JLabel.LEFT),
     };

  public static final Object[] defaultDetallePedido = {" ", " ", " ", " "," "," "," "};

    public static final FarmaColumnData[] columnsListaComprobantes = 
  {
			new FarmaColumnData("Correlativo", 90, JLabel.CENTER),
			new FarmaColumnData("Tipo Comp.", 70, JLabel.CENTER),
			new FarmaColumnData("No. Comp.", 90, JLabel.CENTER),
			new FarmaColumnData("Fecha", 120, JLabel.CENTER),
			new FarmaColumnData("Total", 90, JLabel.RIGHT),
			new FarmaColumnData("Estado", 70, JLabel.CENTER),
    new FarmaColumnData("Nom. Vendedor",100,JLabel.LEFT),
  };
  
  public static final Object[] defaultListaComprobantes = {" ", " ", " ", " ", " ", " ", " "};


	public static final FarmaColumnData[] columnsListaComprobantesRangos = {
			new FarmaColumnData("Fecha", 120, JLabel.CENTER),
			new FarmaColumnData("Tipo Comp.", 70, JLabel.CENTER),
			new FarmaColumnData("No. Comp.", 120, JLabel.CENTER),
			new FarmaColumnData("Correlativo", 90, JLabel.CENTER),
			new FarmaColumnData("Total", 60, JLabel.RIGHT),
			new FarmaColumnData("Nom. Vendedor", 100, JLabel.LEFT),
			new FarmaColumnData("Estado", 70, JLabel.CENTER),
			new FarmaColumnData("ORD Numero de Comprobante", 0, JLabel.CENTER),
			new FarmaColumnData("sec", 0, JLabel.CENTER),
			new FarmaColumnData("tipComp", 0, JLabel.CENTER),
      new FarmaColumnData("numcomp", 0, JLabel.CENTER),
      new FarmaColumnData("ORD Correlativo", 0, JLabel.CENTER),

	};

	public static final Object[] defaultListaComprobantesRangos = {" ", " "," ", " "," ", " ", " ", " ", " ", " ", " ", " " };

	public static final FarmaColumnData[] columnsListaComprobantesDesfasados = {
			new FarmaColumnData("Tipo Comp.", 70, JLabel.CENTER),
			new FarmaColumnData("No. Comp.", 120, JLabel.CENTER),
			new FarmaColumnData("Correlativo", 90, JLabel.CENTER),
			new FarmaColumnData("Estado", 70, JLabel.CENTER),
			new FarmaColumnData("ord", 0, JLabel.CENTER),

	};

	public static final Object[] defaultListaComprobantesDesfasados = { " "," ", " ", " ", " " };

  //DlgAnularPedido
  public static final FarmaColumnData columnsListaPedidos[] = {
            new FarmaColumnData( "Pedido", 80, JLabel.CENTER ),
            new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
            new FarmaColumnData( "Total S/.", 80, JLabel.RIGHT ),
            new FarmaColumnData( "R.U.C.", 80, JLabel.LEFT ),
            new FarmaColumnData( "Cliente", 170, JLabel.LEFT ),
            new FarmaColumnData( "Cajero", 130, JLabel.LEFT ),
            new FarmaColumnData( "Convenio", 100, JLabel.LEFT )
            /*new FarmaColumnData( "Tipo Comp", 0, JLabel.LEFT )
            /* new FarmaColumnData( "", 0, JLabel.LEFT ),//Tipo de Pedido
            new FarmaColumnData( "", 0, JLabel.LEFT ),//Indicador Pedido Automatico
            new FarmaColumnData( "", 0, JLabel.LEFT ),//Indicador Pedido Fidelizado
            new FarmaColumnData( "", 0, JLabel.LEFT )//Dni Cliente*/ //  06.07.2009.ec
	  };
	
	public static final Object[] defaultValuesListaPedidos = {" ", " ", " ", " "," ", " ", " ", " "," "," "," "};

        public static final FarmaColumnData columnsListaProductos[] = {
            new FarmaColumnData( "Codigo", 70, JLabel.CENTER ),
            new FarmaColumnData( "Descripcion", 270, JLabel.LEFT ),
            new FarmaColumnData( "Unidad", 110, JLabel.LEFT ),
            new FarmaColumnData( "Pre. Vta.", 80, JLabel.RIGHT ),
            new FarmaColumnData( "Cantidad", 70, JLabel.RIGHT ),
            new FarmaColumnData( "Total", 100, JLabel.RIGHT )
        };
	
	public static final Object[] defaultValuesListaProductos = {" ", " "," ", " ", " "};

  //DlgDetalleAnularPedido
  public static final FarmaColumnData[] columnsUsuariosCaja = 
  {
    new FarmaColumnData("Codigo",70,JLabel.LEFT),
    new FarmaColumnData("Usuario",180,JLabel.LEFT),
    new FarmaColumnData("Caja",60,JLabel.LEFT),
    new FarmaColumnData("Turno",60,JLabel.LEFT)
  };
  
  public static final Object[] defaultUsuariosCaja = {" ", " ", " ", " "};
 
  //Lista de Formas de Pago
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
  
  //Lista de Detalle de Pago Pedido
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
  
  //DlgPedidoBuscar
   public static final FarmaColumnData[] columnsListaPendientesUnir = 
  {
    new FarmaColumnData("Pedido",55,JLabel.LEFT),
    new FarmaColumnData("Correlativo",80,JLabel.LEFT),
    new FarmaColumnData("Fecha",120,JLabel.LEFT),
    new FarmaColumnData("Total",90,JLabel.RIGHT),
    new FarmaColumnData("RUC",90,JLabel.LEFT),
    new FarmaColumnData("Cliente",140,JLabel.LEFT),
    new FarmaColumnData("Vendedor",140,JLabel.LEFT),
    new FarmaColumnData("Tip.Doc.",0,JLabel.LEFT),
    new FarmaColumnData("Tip.Ped.",0,JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.LEFT)//PEDIDO CONVENIO
  };
  
  public static final Object[] defaultListaPendientesUnir = {" "," ", " ", " ", " ", " ", " ", " ", " "," "};
  
   public static final FarmaColumnData[] columnsDetallePedidoUnir = 
  {
    new FarmaColumnData("Codigo",60,JLabel.LEFT),
    new FarmaColumnData("Producto",140,JLabel.LEFT),
    new FarmaColumnData("Unidad",120,JLabel.LEFT),
    new FarmaColumnData("Precio",90,JLabel.RIGHT),
    new FarmaColumnData("Cantidad",70,JLabel.RIGHT),
    new FarmaColumnData("Total",100,JLabel.RIGHT),
  };
  
  public static final Object[] defaultDetallePedidoUnir = {" ", " ", " ", " ", " ", " "};

  //Lista Pedidos No Impresos
  public static final FarmaColumnData[] columnsListaPendientesImpresion = 
  {
    new FarmaColumnData("Tipo",60,JLabel.LEFT),
    new FarmaColumnData("Num.Diario",80,JLabel.CENTER),
    new FarmaColumnData("Pedido",90,JLabel.CENTER),
    new FarmaColumnData("Fecha",120,JLabel.CENTER),
    new FarmaColumnData("Total S/.",100,JLabel.RIGHT),
    new FarmaColumnData("RUC",120,JLabel.CENTER),
    new FarmaColumnData("",0,JLabel.CENTER),
    new FarmaColumnData("",0,JLabel.CENTER),
    new FarmaColumnData("",0,JLabel.CENTER),
    new FarmaColumnData("",0,JLabel.CENTER),
  };
  
  public static final Object[] defaultListaPendientesImpresion = { " ", " ", " ", " ", " ", " ", " ", " ", " "};
  
  //Detalle Pedido No Impreso
  public static final FarmaColumnData[] columnsDetallePedidoNoImpreso = 
  {
    new FarmaColumnData("Sec.Comp.",80,JLabel.CENTER),
    new FarmaColumnData("Codigo",60,JLabel.CENTER),
    new FarmaColumnData("Descripcion",160,JLabel.LEFT),
    new FarmaColumnData("Unidad",100,JLabel.LEFT),
    new FarmaColumnData("P.Venta",70,JLabel.RIGHT),
    new FarmaColumnData("Cant.",60,JLabel.RIGHT),
    new FarmaColumnData("Total",80,JLabel.RIGHT),
  };

  public static final Object[] defaultDetallePedidoNoImpreso = {" ", " ", " ", " ", " ", " ", " "};

  /**
   * Columnas del listado de detalle del pedido a devolver.
   * @author Edgar Rios Navarro
   * @since 29.05.2008
   */
  public static final FarmaColumnData columnsListaProductosNotaCreditoNueva[] =
  { new FarmaColumnData("Codigo", 50, JLabel.LEFT), 
    new FarmaColumnData("Descripcion", 200, JLabel.LEFT), 
    new FarmaColumnData("Unidad", 80, JLabel.LEFT), 
    new FarmaColumnData("Laboratorio", 150, JLabel.LEFT), 
    new FarmaColumnData("Cantidad", 60, JLabel.RIGHT), 
    new FarmaColumnData("Precio", 60, JLabel.RIGHT), 
    new FarmaColumnData("Devolucion", 70, JLabel.RIGHT), 
    new FarmaColumnData("Fraccion", 0, JLabel.RIGHT), 
    new FarmaColumnData("Precio Lista", 0, JLabel.RIGHT), 
    new FarmaColumnData("Val Igv", 0, JLabel.RIGHT), 
    new FarmaColumnData("secPedDet", 0, JLabel.RIGHT) };

  public static final Object[] defaultValuesListaProductosNotaCreditoNueva =
  { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
  
  public static final String NOM_HASHTABLE_CMBIMPRESORA = "cmbImpresora";
  
  //CONSTANTES PARA VENTA PRODUCTOS VIRTUALES
  public static final String NUM_CAJA_FISICA = "1";
  public static final String COD_RESPUESTA_OK_TAR_VIRTUAL = "00";
  
  public static final FarmaColumnData[] columnsListaCabPedVirtual = {
    new FarmaColumnData("Pedido", 90, JLabel.LEFT),
    new FarmaColumnData("Comp.", 85, JLabel.LEFT),
    new FarmaColumnData("Num Comp.", 90, JLabel.LEFT),
    new FarmaColumnData("Fecha", 130, JLabel.LEFT),
    new FarmaColumnData("Total(S/.)", 80, JLabel.RIGHT),
    new FarmaColumnData("Estado", 100, JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.LEFT) 
  };
	public static final Object[] defaultListaCabPedVirtual = {" ", " ", " ", " ", " ", " ", " "};

  public static final FarmaColumnData[] columnsListaDetPedVirtual = {
    new FarmaColumnData("Codigo",60,JLabel.CENTER),
    new FarmaColumnData("Producto",140,JLabel.LEFT),
    new FarmaColumnData("Tipo",68,JLabel.LEFT),
    new FarmaColumnData("Monto",65,JLabel.RIGHT),
    new FarmaColumnData("Cod Aprob.",70,JLabel.CENTER),
    new FarmaColumnData("Tarjeta/Celular",110,JLabel.LEFT),
    new FarmaColumnData("Num Pin",98,JLabel.LEFT),
  };
  public static final Object[] defaultListaDetPedVirtual = {" ", " ", " ", " "," "," "};
  
  //CONSTANTES PARA DESCRIPCION DE FORMAS DE PAGO 
  public static final String COLUMNAS_DETALLE_FORMA_PAGO = "Codigo , Descripcion , Moneda , Monto , Total , Vuelto <BR>";
  
  
  //Constantes para tipo de consulas en cada tipo de metodo
  //Y fucionalidades en Matriz
  //20.08.2008  
  public static final String CONSULTA_ACTUALIZA_CUPON_LOCAL = "ACTUALIZA_CUPON_LOCAL";
  public static final String CONSULTA_ACTUALIZA_MATRIZ = "ACTUALIZA_MATRIZ";
  public static final String CONSULTA_PROCESO_NOTA_CREDITO = "PROCESO_NOTA_CREDITO";
  public static final String CONSULTA_CUPONES_USADOS = "CUPONES_USADOS";
  public static final String CONSULTA_CUPONES_EMITIDOS = "CUPONES_EMITIDOS";  
  public static final String CONSULTA_CUPONES_EMITIDOS_USADOS = "CUPONES_EMITIDOS_USADOS";
    
  
  public static final String CONSULTA_CUPONES_ANUL_SIN_PROC_MATRIZ = "CUPONES_ANULADOS_SIN_PROC_MATRIZ";
  public static final String CONSULTA_VALIDA_CUPONES = "VALIDA_CUPON";  
    
  
  public static final String CUPONES_USADOS = "U";
  public static final String CUPONES_EMITIDOS = "E";
  
  
  public static final String ACCION_COBRO = "C";
  public static final String ACCION_ANULA_PENDIENTE = "N";
  
  /** autor :   
   *  Fecha : 06/01/2009 */
  
    public static final String PROCESO_MOVIMIENTO_CIERRE = "C";
    public static final String PROCESO_MOVIMIENTO_ACTUALIZAR = "U";
    
  /**
   * Modulo Remitos (PROSEGUR)
   * @author :   
   * @since: 13.01.09
   * */  
   
   //Lista remitos
    public static final FarmaColumnData[] columnsListaRemitos = {
                    new FarmaColumnData("Fecha Remito", 120, JLabel.CENTER),
                    new FarmaColumnData("N°Remito", 85, JLabel.RIGHT),
                    new FarmaColumnData("Adm. Local", 120, JLabel.CENTER),
                    new FarmaColumnData("Mont. Soles(S/.)", 120, JLabel.RIGHT),
                    new FarmaColumnData("Mont. Dolares(US$)", 120, JLabel.RIGHT),
                    new FarmaColumnData("Mont. Total(S/.)", 100, JLabel.RIGHT),
                    new FarmaColumnData("Nro. Sobres", 80, JLabel.CENTER),
                    new FarmaColumnData("", 0, JLabel.CENTER),
                    new FarmaColumnData("", 0, JLabel.CENTER)};//Columna Orden FECHA

    public static final Object[] defaultValuesListaRemitos = { " ", " "," ", " ", " ", " ", " "," "," "};

  
    //Lista fechas ventas pendientes para remitos
    public static final FarmaColumnData[] columnsListaFechas = 
    {
      //new FarmaColumnData("Sel",25,JLabel.CENTER),
      new FarmaColumnData("Fech Venta",80,JLabel.CENTER),
    new FarmaColumnData("N°Sobres",70,JLabel.CENTER),
      new FarmaColumnData("Monto Soles(S/.)",120,JLabel.RIGHT),
    new FarmaColumnData("N°Sobres",70,JLabel.CENTER),
      new FarmaColumnData("Monto Dolares(US$)",120,JLabel.RIGHT),
      new FarmaColumnData("N°Sobres",70,JLabel.RIGHT)
    };
    public static final Object[] defaultListaFechas = {" ", " ", " ", " ", " "," "};
    
    //sobres de las fechas ventas pendientes para remitos
    public static final FarmaColumnData[] columnsListaSobresDet = 
    {
    new FarmaColumnData("Fech Venta°",80,JLabel.CENTER),
        new FarmaColumnData("N° Sobre",98,JLabel.CENTER),
        new FarmaColumnData("Moneda",80,JLabel.CENTER),
        new FarmaColumnData("Monto",95,JLabel.RIGHT),
        new FarmaColumnData("Forma Pago",165,JLabel.LEFT),
        new FarmaColumnData("Cajero",85,JLabel.LEFT),
        new FarmaColumnData("Quimico .VB",85,JLabel.LEFT),
        new FarmaColumnData("",0,JLabel.LEFT),//S
        new FarmaColumnData("",0,JLabel.LEFT),//D
         new FarmaColumnData("",0,JLabel.LEFT),//Cant S
          new FarmaColumnData("",0,JLabel.LEFT)}; //Cant D
    public static final Object[] defaultListaSobresDet = {" ", " "," "," "," "," "," "," "," "," "," "};
    
    public static final FarmaColumnData[] columnsListaFechasDet = 
    {
      new FarmaColumnData("Fech Venta",85,JLabel.CENTER),
      new FarmaColumnData("N°Sobres",70,JLabel.CENTER),
      new FarmaColumnData("Monto Soles",100,JLabel.RIGHT),
      new FarmaColumnData("N°Sobres",70,JLabel.CENTER),
      new FarmaColumnData("Monto Dolares",100,JLabel.RIGHT),
      new FarmaColumnData("N°Total Sobres",110,JLabel.CENTER)
    };
    public static final Object[] defaultListaFechasDet = { " ", " ", " ", " "," "," "};
    
    //Lista fechas ventas pendientes para remitos
    /*public static final FarmaColumnData[] columnsListaSobres = 
    {
        new FarmaColumnData("N° Sobre",98,JLabel.CENTER),
        new FarmaColumnData("Moneda",80,JLabel.CENTER),
        new FarmaColumnData("Monto",95,JLabel.RIGHT),
        new FarmaColumnData("Forma Pago",165,JLabel.LEFT),
        new FarmaColumnData("Cajero",85,JLabel.LEFT),
        new FarmaColumnData("Quimico .VB",85,JLabel.LEFT)
    };
    public static final Object[] defaultListaSobres = {" ", " "," "," "," "," "};
    */
    
    public static final FarmaColumnData[] columnsListaSobres = 
    {
    new FarmaColumnData("Fech Venta°",80,JLabel.CENTER),
        new FarmaColumnData("N° Sobre",98,JLabel.CENTER),
        new FarmaColumnData("Moneda",80,JLabel.CENTER),
        new FarmaColumnData("Monto",95,JLabel.RIGHT),
        new FarmaColumnData("Forma Pago",165,JLabel.LEFT),
        new FarmaColumnData("Cajero",85,JLabel.LEFT),
        new FarmaColumnData("Quimico .VB",85,JLabel.LEFT)//,
       /* new FarmaColumnData("",0,JLabel.LEFT),//S
        new FarmaColumnData("",0,JLabel.LEFT),//D
         new FarmaColumnData("",0,JLabel.LEFT),//Cant S
          new FarmaColumnData("",0,JLabel.LEFT)*/}; //Cant D
    public static final Object[] defaultListaSobres = {" ", " "," "," "," "," "," "};//," "," "," "," "};
        
    
    
    
    
    //Variables para el filtro del listado de productos
    public static final String[] vCodColum = {"FECHA_VTA","COD_REMITO","USU_CREADO","MONTO_SOLES","MONTO_DOLARES","MONTO_TOTAL","NRO_SOBRES"};
    public static final String[] vDescColum ={"0","1","2","3","4","5","6"};
    public static final String NOM_HASTABLE_CMBCOLUMNAREMITO ="CMB_COLUMNA_REMITO";
    
    public static final String[] vCodOrden = {"ASC","DESC"};
    public static final String[] vDescOrden ={FarmaConstants.ORDEN_ASCENDENTE,FarmaConstants.ORDEN_DESCENDENTE};
    public static final String NOM_HASTABLE_CMBORDENREMITO ="CMB_ORDERN_REMITO";
    
    //  23/07/09 Destinatarios para recibir Error de Impresion
    public static final String EMAIL_DESTINATARIO_ERROR_IMPRESION = " ";
    public static final String EMAIL_DESTINATARIO_CC_ERROR_IMPRESION = "joliva;operador; ";
    //public static final String EMAIL_DESTINATARIO_CC_ERROR_IMPRESION = "";

     /**
      *Ingreso de Sobres (PROSEGUR)
      * @author :   
      * @since: 03.11.09
      * */  
      public static final String HASHTABLE_MONEDASOBRES = "MONEDA";
      public static final String[] MONEDAS_COD = { "01", "02" };
      public static final String[] MONEDAS_DESC = { "SOLES", "DOLARES" };
      
    //Lista Sobres
     public static final FarmaColumnData[] columnsListaSobresTmp= {
                     new FarmaColumnData("Código", 50, JLabel.CENTER),
                     new FarmaColumnData("Forma de Pago", 130, JLabel.LEFT),
                     new FarmaColumnData("Moneda", 72, JLabel.CENTER),
                     new FarmaColumnData("Monto", 70, JLabel.RIGHT),
                     new FarmaColumnData("Total", 70, JLabel.RIGHT),
                     new FarmaColumnData("Ind. Sobre", 65, JLabel.CENTER),
                     new FarmaColumnData("Sobre", 95, JLabel.CENTER),//sobre
                    /* new FarmaColumnData("", 0, JLabel.CENTER),//SecMovCaja
                     new FarmaColumnData("", 0, JLabel.CENTER),//TipMon
                     new FarmaColumnData("", 0, JLabel.CENTER),*///Sec
                     };

     public static final Object[] defaultValuesListaSobresTmp = {"","","","","","",""};
     
    public static final String EFECTIVO_SOLES = "01";
    public static final String EFECTIVO_DOLARES = "02";

    //  10.03.2010
    public static final String FORMA_PAGO_FONDO_SENCILLO = "00060";
//mensaje constante de INGRESE monto  , 04.03.2010
    public static final String msgIngreso="INGRESE MONTO";
    
    //lista de detalle de un pedido ,   05.03.2010
    public static final FarmaColumnData[] columnsDetallePedidoNew = {
       new FarmaColumnData("Codigo",60,JLabel.CENTER),
       new FarmaColumnData("Producto",140,JLabel.LEFT),
       new FarmaColumnData("Unidad",120,JLabel.LEFT),
       new FarmaColumnData("P.Venta",80,JLabel.RIGHT),
       new FarmaColumnData("Cantidad",80,JLabel.RIGHT),
       new FarmaColumnData("Total",70,JLabel.RIGHT),
       new FarmaColumnData("Vendedor",90,JLabel.LEFT),
        };
     public static final Object[] defaultDetallePedidoNew = {" ", " ", " ", " "," "," "," "};
     
     
    //Variables para el filtro del listado de productos,   19.03.2010
    public static final String[] descmoneda = {"SOLES","DOLARES"};
    public static final String[] codmoneda = {"0","1"};
    public static final String MON_HASTABLE_MONEDA ="CMB_MONEDA";

  
    //Lista Sobres
     public static final FarmaColumnData[] columnsListaSobresControl= {
                     new FarmaColumnData("Cajero", 150, JLabel.LEFT),
                     new FarmaColumnData("Caja", 50, JLabel.CENTER),
                     new FarmaColumnData("Turno", 50, JLabel.CENTER),
                     new FarmaColumnData("CodSobre", 100, JLabel.LEFT),
                     new FarmaColumnData("Fec Dia", 80, JLabel.CENTER),
                     new FarmaColumnData("Tip Moneda", 85, JLabel.CENTER),
                     new FarmaColumnData("Mont Entrega", 100, JLabel.RIGHT),
                     new FarmaColumnData("Mont Total", 100, JLabel.RIGHT)};

     public static final Object[] defaultValuesListaSobresControl = {"","","","","","","",""};

    //Lista Sobres 02,   - 07.06.2010
     public static final FarmaColumnData[] columnsListaSobresControl_02= {
                     new FarmaColumnData("Fec.Crea.", 70, JLabel.LEFT),
                     new FarmaColumnData("Hora.Crea.", 70, JLabel.CENTER),
                     new FarmaColumnData("Nombre", 120, JLabel.CENTER),
                     new FarmaColumnData("Cod. Sobre", 90, JLabel.LEFT),
                     new FarmaColumnData("Num.Caja", 73, JLabel.CENTER),
                     new FarmaColumnData("Num.Turno", 73, JLabel.CENTER),
                     new FarmaColumnData("Moneda", 75, JLabel.CENTER),
                     new FarmaColumnData("Mont Entrega", 72, JLabel.RIGHT),
                     new FarmaColumnData("Mont Total", 72, JLabel.RIGHT)};

     public static final Object[] defaultValuesListaSobresControl_02 = {"","","","","","","","","","","",""};
}
