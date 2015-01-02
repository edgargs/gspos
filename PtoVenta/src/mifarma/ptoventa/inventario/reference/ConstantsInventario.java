package mifarma.ptoventa.inventario.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.02.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class ConstantsInventario
{
  public ConstantsInventario()
  {
  }
  //DlgGuiaIngresoProductos
  public static final FarmaColumnData[] columnsListaGuiaIngresoProductos =
  {
    new FarmaColumnData("Sel",30,JLabel.LEFT),
    new FarmaColumnData("Codigo",50,JLabel.LEFT),
    new FarmaColumnData("Descripcion",250,JLabel.LEFT),
    new FarmaColumnData("Unidad",120,JLabel.LEFT),
    new FarmaColumnData("Laboratorio",210,JLabel.LEFT),
    new FarmaColumnData("Stock",0,JLabel.LEFT),
    new FarmaColumnData( "Precio", 0, JLabel.RIGHT ),
    new FarmaColumnData( "Zan", 0, JLabel.RIGHT ),
    new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador producto congelado
    new FarmaColumnData( "", 0, JLabel.RIGHT )//valor de fraccion de local
  };

  public static final Object[] defaultListaGuiaIngresoProductos = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};

  //DlgGuiaIngresoResumen
  public static final FarmaColumnData[] columnsListaResumenPedido =
  {
    new FarmaColumnData("Codigo",50,JLabel.LEFT),
    new FarmaColumnData("Descripcion",200,JLabel.LEFT),
    new FarmaColumnData("Unidad",80,JLabel.LEFT),
    new FarmaColumnData("Laboratorio",150,JLabel.LEFT),
    new FarmaColumnData("Cantidad",60,JLabel.RIGHT),
    new FarmaColumnData("Pre. U.",50,JLabel.RIGHT),
    new FarmaColumnData("Total",50,JLabel.RIGHT),
    new FarmaColumnData("Lote",0,JLabel.RIGHT),
    new FarmaColumnData("Fec Vec",0,JLabel.RIGHT),
    new FarmaColumnData("Val Frac",0,JLabel.RIGHT)
  };

  public static final Object[] defaultListaResumenPedido = {" ", " ", " "," ", " ", " ", " ", " ", " ", " "};

  //DlgGuiasIngresosRecibidas
  public static final FarmaColumnData columnsListaTransferencias[] ={ 
    new FarmaColumnData("No. Nota", 80, JLabel.CENTER), 
      new FarmaColumnData( "Tipo", 70, JLabel.LEFT ),
      new FarmaColumnData( "Numero Doc.", 90, JLabel.LEFT ),
	    new FarmaColumnData( "Origen", 100, JLabel.LEFT ),
    new FarmaColumnData("Fec. Doc.", 70, JLabel.CENTER), 
    new FarmaColumnData("F. Cierre", 70, JLabel.CENTER), 
      new FarmaColumnData( "Items", 40, JLabel.RIGHT ),
      new FarmaColumnData( "Estado", 70, JLabel.CENTER ),
    new FarmaColumnData("Num. Entrega", 90, JLabel.LEFT), 
    new FarmaColumnData("Tip.Nota", 0, JLabel.CENTER),
    new FarmaColumnData("Tip.Nota Origen", 0, JLabel.CENTER),
  };

  public static final Object[] defaultValuesListaTransferencias = {" ", " ", " ", " ", " ", " ", " ", " ", " "," "};

  //DlgKardex
  public static final FarmaColumnData columnsListaProductos[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
	    new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
	    new FarmaColumnData( "Stock", 60, JLabel.RIGHT ),
            new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT )
	  };

	public static final Object[] defaultValuesListaProductos = {" ", " ", " ", " "," "};

  //DlgKardexFiltro
  public static final FarmaColumnData columnsListaProductosKardexFiltro[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 300, JLabel.LEFT )
	  };

	public static final Object[] defaultValuesListaProductosKardexFiltro = {" ", " "};

  //DlgMovimientoKardex
  public static final FarmaColumnData columnsListaProductosMovimientoKardex[] = {
	    new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
	    new FarmaColumnData( "Motivo.", 80, JLabel.LEFT ),
      new FarmaColumnData( "N. Documento", 100, JLabel.LEFT ),
	    new FarmaColumnData( "Stock Ant.", 75, JLabel.RIGHT ),
	    new FarmaColumnData( "Movimiento", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Stock Act.", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Fraccion Mov.", 90, JLabel.LEFT ),
      new FarmaColumnData( "Unidad Mov.", 130, JLabel.LEFT ),
    };

	public static final Object[] defaultValuesListaProductosMovimientoKardex = {" ", " "," ", " ", " ", " "," "," "," "};

  //DlgPedidoAdicionalBusquedaClientes
  public static final FarmaColumnData columnsListaClientes[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
      new FarmaColumnData( "Tipo", 60, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
      new FarmaColumnData( "Doc. 1", 40, JLabel.LEFT ),
      new FarmaColumnData( "No. Doc.", 80, JLabel.LEFT ),
      new FarmaColumnData( "Doc. 2", 40, JLabel.LEFT ),
      new FarmaColumnData( "No. Doc.", 80, JLabel.LEFT )
  };

	public static final Object[] defaultValuesListaClientes = {" ", " ", " ", " ", " ", " ", " "};

  //DlgPedidoAdicionalListaProductos
  public static final FarmaColumnData columnsListaProductosPedidoAdicional[] = {
	    new FarmaColumnData( "Sel", 30, JLabel.LEFT ),
      new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
	    new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
	    new FarmaColumnData( "Stock", 50, JLabel.RIGHT ),
      new FarmaColumnData( "Pre. U.", 50, JLabel.RIGHT )
    };

	public static final Object[] defaultValuesListaProductosPedidoAdicional = {" ", " ", " ", " ", " "," ", " "};

  //DlgPedidoAdicionalNuevo
  public static final FarmaColumnData columnsListaProductosPedidoAdicionalNuevo[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
	    new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
      new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
	    new FarmaColumnData( "Stock", 50, JLabel.RIGHT )
    };

	public static final Object[] defaultValuesListaProductosPedidoAdicionalNuevo = {" ", " ", " ", " ", " "};

  //DlgPedidoAdicionalRealizados
  public static final FarmaColumnData columnsListaPedidos[] = {
	    new FarmaColumnData( "No Pedido", 60, JLabel.LEFT ),
	    new FarmaColumnData( "Fecha", 70, JLabel.LEFT ),
      new FarmaColumnData( "Motivo", 200, JLabel.LEFT ),
	    new FarmaColumnData( "Items", 40, JLabel.LEFT ),
      new FarmaColumnData( "Cliente", 150, JLabel.RIGHT )
    };

	public static final Object[] defaultValuesListaPedidos = {" ", " ", " ", " ", " "};

  //DlgPedidoReposicionDetalle
  /*public static final FarmaColumnData columnsListaProductosPedidoReposicionDetalle[] =
  { new FarmaColumnData("Codigo", 50, JLabel.LEFT), 
    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
    new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),
    new FarmaColumnData("Solic.", 40, JLabel.RIGHT), 
    new FarmaColumnData("Suger.", 40, JLabel.RIGHT), 
    new FarmaColumnData("Stock", 50, JLabel.RIGHT), 
    new FarmaColumnData( "Vends.", 50, JLabel.RIGHT ),
    new FarmaColumnData( "Min", 50, JLabel.RIGHT ),
    new FarmaColumnData( "Max", 50, JLabel.RIGHT ),
    new FarmaColumnData( "Rotac.", 50, JLabel.RIGHT ),
    new FarmaColumnData( "Max Dia", 50, JLabel.RIGHT ),
    new FarmaColumnData( "Exhib", 0, JLabel.RIGHT ),
    new FarmaColumnData( "Laboratorio", 0, JLabel.RIGHT ),
    new FarmaColumnData( "Transito", 0, JLabel.RIGHT ),
    new FarmaColumnData("Rotacion", 0, JLabel.RIGHT), 
    new FarmaColumnData("", 0, JLabel.RIGHT) , // 30 dias
    new FarmaColumnData("", 0, JLabel.RIGHT) , // 60 dias
    new FarmaColumnData("", 0, JLabel.RIGHT) , // 90 dias
    new FarmaColumnData("", 0, JLabel.RIGHT) , // 120 dias
    new FarmaColumnData("", 0, JLabel.RIGHT) ,// tipo
    new FarmaColumnData("", 0, JLabel.RIGHT) };  // cantidad Adicional

 public static final Object[] defaultValuesListaProductosPedidoReposicionDetalle = 
                {" "," ", " ", " ", " ", " ", " ", " ", " "," ", " "," "};*/

   //Modificado por DVELIZ 16.09.08
    public static final FarmaColumnData columnsListaProductosPedidoReposicionDetalle[] =
      { new FarmaColumnData("Codigo", 50, JLabel.LEFT), //0
        new FarmaColumnData( "Descripcion", 400, JLabel.LEFT ),//1
        new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),//2
        new FarmaColumnData("Solic.", 60, JLabel.RIGHT), //3
        //new FarmaColumnData("Suger.", 40, JLabel.RIGHT), 
        new FarmaColumnData("Stock", 60, JLabel.RIGHT), //4
        //new FarmaColumnData( "Vends.", 50, JLabel.RIGHT ),
        //new FarmaColumnData( "Min", 50, JLabel.RIGHT ),
        //new FarmaColumnData( "Max", 60, JLabel.RIGHT ),//5
        new FarmaColumnData( "PVM", 60, JLabel.RIGHT ),//5
        //new FarmaColumnData( "Rotac.", 50, JLabel.RIGHT ),
        //new FarmaColumnData( "Max Dia", 50, JLabel.RIGHT ),
        new FarmaColumnData( "Exhib", 0, JLabel.RIGHT ),//6
        new FarmaColumnData( "Laboratorio", 0, JLabel.RIGHT ),//7
        new FarmaColumnData( "Transito", 0, JLabel.RIGHT ),//8
        //new FarmaColumnData("Rotacion", 0, JLabel.RIGHT), 
        new FarmaColumnData("", 0, JLabel.RIGHT) , // 30 dias //9
        new FarmaColumnData("", 0, JLabel.RIGHT) , // 60 dias //10
        new FarmaColumnData("", 0, JLabel.RIGHT) , // 90 dias //11
        new FarmaColumnData("", 0, JLabel.RIGHT) , // 120 dias //12
        new FarmaColumnData("", 0, JLabel.RIGHT) ,// tipo //13
        new FarmaColumnData("", 0, JLabel.RIGHT),// cantidad Adicional //14
        //JCORTEZ 16/10/2008
        new FarmaColumnData("", 0, JLabel.RIGHT) , //MM 30 dias
        new FarmaColumnData("", 0, JLabel.RIGHT) , //MM 60 dias
        new FarmaColumnData("", 0, JLabel.RIGHT) ,//MM 90 dias
        new FarmaColumnData("", 0, JLabel.RIGHT)};  //MM 120 dias

     public static final Object[] defaultValuesListaProductosPedidoReposicionDetalle = 
                    {" ", " ", " ", " ", " ", " ", " ", " ", " "," "," "," "," "," "};

  //DlgPedidoReposicionNuevo
  /**
   * Cambio!
   * @Autor:  Luis Reque Orellana
   * @Fecha:  20/04/2007
   **/
  public static final FarmaColumnData columnsListaProductosPedidoReposicionNuevo[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),//0
     /**
       * Tipo "ABC"
       * @author : dubilluz
       * @since  : 09.07.2007
       */
      new FarmaColumnData( " Tipo", 50, JLabel.CENTER),//01
	    new FarmaColumnData( "Descripcion", 240, JLabel.LEFT ),//2
	    new FarmaColumnData( "Unidad", 115, JLabel.LEFT ),//3
      //new FarmaColumnData( "Min", 50, JLabel.RIGHT ),
	    //new FarmaColumnData( "Max", 50, JLabel.RIGHT ),
	    new FarmaColumnData( "Stock", 65, JLabel.RIGHT ),//4
      new FarmaColumnData( "Pedir", 50, JLabel.LEFT ),//5
	    new FarmaColumnData( "Calc.", 50, JLabel.RIGHT ),//6
      new FarmaColumnData( "Solic.", 50, JLabel.RIGHT ),//7

      
      new FarmaColumnData( "Adicional", 0, JLabel.RIGHT ),//8
      //
      new FarmaColumnData( "Laboratorio", 0, JLabel.RIGHT ),//9
      new FarmaColumnData( "Min Exhib", 0, JLabel.RIGHT ),//10
      new FarmaColumnData( "Transito", 0, JLabel.RIGHT ),//11
      new FarmaColumnData( "Min Dias", 0, JLabel.RIGHT ),//12
      new FarmaColumnData( "Max Dias", 0, JLabel.RIGHT ),//13
      new FarmaColumnData( "Nro Dias", 0, JLabel.RIGHT ),//14
      new FarmaColumnData( "Rotacion", 0, JLabel.RIGHT ),//15
      new FarmaColumnData( "Cant Ant", 0, JLabel.RIGHT ),//16

      //
      new FarmaColumnData( "30", 0, JLabel.RIGHT ),//17
      new FarmaColumnData( "60", 0, JLabel.RIGHT ),//18
      new FarmaColumnData( "90", 0, JLabel.RIGHT ),//19
      new FarmaColumnData( "120", 0, JLabel.RIGHT ),//20
      //
      new FarmaColumnData( "Fraccion", 0, JLabel.RIGHT ),//21
      new FarmaColumnData( "Cant Max", 0, JLabel.RIGHT ),//22
      new FarmaColumnData( "tipo", 0, JLabel.RIGHT ),//tipo de producto//23
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//Stock Locales 24
      new FarmaColumnData( "", 0, JLabel.RIGHT )//Stock Almacen 25
    };

	public static final Object[] defaultValuesListaProductosPedidoReposicionNuevo = {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "};

  //DlgPedidoReposicionRealizados
  // MODIFICADO dubilluz 16/10/2008
  public static final FarmaColumnData columnsListaPedidosPedidoReposicionRealizados[] = {
	    new FarmaColumnData( "No Pedido", 120, JLabel.LEFT ),
	    new FarmaColumnData( "Fecha", 150, JLabel.LEFT ),
	    new FarmaColumnData( "Items", 116, JLabel.RIGHT )/*,
            new FarmaColumnData( "Min Días", 60, JLabel.RIGHT ),
            new FarmaColumnData( "Max Días", 60, JLabel.RIGHT ),
            new FarmaColumnData( "No. Días Rot.. ", 60, JLabel.RIGHT )*/
    };
    public static final Object[] defaultValuesListaPedidosPedidoReposicionRealizados = {" ", " ", " "};
    
	//public static final Object[] defaultValuesListaPedidosPedidoReposicionRealizados = {" ", " ", " ", " "," ", " "};

  //DlgPedidoReposicionVer
  /**Modificado
   * @Autor:  Luis Reque Orellana
   * @Fecha:  25/04/2007
   * */
  public static final FarmaColumnData columnsListaProductosPedidoReposicionVer[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),//0
	    new FarmaColumnData( "Descripcion", 180, JLabel.LEFT ), //1
	    new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),//2
      new FarmaColumnData( "Min", 50, JLabel.RIGHT ),//3
	    new FarmaColumnData( "Max", 50, JLabel.RIGHT ),//4
	    new FarmaColumnData( "Stock", 70, JLabel.RIGHT ),//5
      new FarmaColumnData( "N. Pedido", 50, JLabel.LEFT ),//6
	    new FarmaColumnData( "Calc.", 50, JLabel.RIGHT ),//7
      new FarmaColumnData( "Solic.", 50, JLabel.RIGHT ),//8
      new FarmaColumnData( "Adic.", 50, JLabel.RIGHT ), /**Nueva Columna!!*/ //9
      //
      new FarmaColumnData( "Laboratorio", 0, JLabel.RIGHT ),//10
      new FarmaColumnData( "Min Exhib", 0, JLabel.RIGHT ),//11
      new FarmaColumnData( "Transito", 0, JLabel.RIGHT ),//12
      new FarmaColumnData( "Min Dias", 0, JLabel.RIGHT ),//13
      new FarmaColumnData( "Max Dias", 0, JLabel.RIGHT ),//14
      new FarmaColumnData( "Nro Dias", 0, JLabel.RIGHT ),//15
      new FarmaColumnData( "Rotacion", 0, JLabel.RIGHT ),//16
      new FarmaColumnData( "Cant Ant", 0, JLabel.RIGHT ),//17
      /**
       * 
       */
      //
      new FarmaColumnData( "30", 0, JLabel.RIGHT ),//18
      new FarmaColumnData( "60", 0, JLabel.RIGHT ),//19
      new FarmaColumnData( "90", 0, JLabel.RIGHT ),//20
      new FarmaColumnData( "120", 0, JLabel.RIGHT ),//21
      //
      new FarmaColumnData( "Fraccion", 0, JLabel.RIGHT ),//22
      new FarmaColumnData( "Cant Max", 0, JLabel.RIGHT ),//23
      new FarmaColumnData( "tipo", 0, JLabel.RIGHT ),//  24
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//Stock Locales 25
      new FarmaColumnData( "", 0, JLabel.RIGHT )//Stock Almacen 26
    };

	public static final Object[] defaultValuesListaProductosPedidoReposicionVer = {" ", " ", " ", " ", " "," ", " "," ", " ", " ", " "," ", " "," ", " ", " ", " "," ", " ", " ", " ", " ", " "," "," "," "," "};

  //DlgRecepcionProductosDetalleGuia
	public static final FarmaColumnData columnsListaProductosRecepcion[] = {
			new FarmaColumnData("Codigo", 50, JLabel.LEFT),
			new FarmaColumnData("Descripcion", 240, JLabel.LEFT),
			new FarmaColumnData("Unidad", 120, JLabel.LEFT),
			new FarmaColumnData("Laboratorio", 165, JLabel.LEFT),
			new FarmaColumnData("Cant.", 40, JLabel.RIGHT),
			new FarmaColumnData("OK", 40, JLabel.RIGHT),
			new FarmaColumnData("Difer.", 40, JLabel.RIGHT),
			new FarmaColumnData("Afect.", 40, JLabel.CENTER),
			new FarmaColumnData("Guia", 0, JLabel.CENTER),
			new FarmaColumnData("Val Frac.", 0, JLabel.CENTER),
			new FarmaColumnData("stk fis", 0, JLabel.CENTER),
			new FarmaColumnData("sec det", 0, JLabel.CENTER), 
			new FarmaColumnData("", 0, JLabel.LEFT), 
      new FarmaColumnData("Pag.", 0, JLabel.CENTER)
  };

	public static final Object[] defaultValuesListaProductosRecepcion = { " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " "," " };

  //DlgRecepcionProductosGuias
  public static final FarmaColumnData columnsListaGuiasRecepcion[] = {
			new FarmaColumnData("Sel", 30, JLabel.CENTER),
      new FarmaColumnData("Guia", 80, JLabel.CENTER),
      new FarmaColumnData("Pag.", 30, JLabel.CENTER),
      new FarmaColumnData("Items", 40, JLabel.CENTER),
      new FarmaColumnData("Prods", 40, JLabel.CENTER),
      new FarmaColumnData("Num. Entrega", 80, JLabel.CENTER),
      new FarmaColumnData("Fecha", 120, JLabel.CENTER),
      new FarmaColumnData("Tipo Ped. Rep.", 60, JLabel.CENTER),
      new FarmaColumnData("Num. Nota", 0, JLabel.CENTER)                        
  };

	public static final Object[] defaultValuesListaGuiasRecepcion = {" ", " ", " ", " ", " ", " "," ", " "};
  
  //DlgRecepcionProductosGuiasPendientes
	public static final FarmaColumnData columnsListaGuiasPendientes[] = {
			new FarmaColumnData("Num. Nota", 80, JLabel.CENTER),
			new FarmaColumnData("Fecha", 140, JLabel.CENTER),
			new FarmaColumnData("Estado", 50, JLabel.CENTER),
			new FarmaColumnData("Items", 60, JLabel.RIGHT),
			new FarmaColumnData("Tipo Origen", 90, JLabel.RIGHT),
			new FarmaColumnData("Est. Recepcion", 90, JLabel.CENTER),
			new FarmaColumnData("Est", 0, JLabel.CENTER),
	};

	public static final Object[] defaultValuesListaGuiasPendientes = { " "," ", " ", " ", " ", " ", " " };

  //DlgTransferenciasBusquedaLocales
  public static final FarmaColumnData columnsListaLocales[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 300, JLabel.LEFT )
  };

	public static final Object[] defaultValuesListaLocales = {" ", " "};

  //DlgTransferenciasListaProductos
  public static final FarmaColumnData columnsListaProductosTransferencias[] = {
	    new FarmaColumnData( "Sel", 30, JLabel.LEFT ),
      new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 250, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 120, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 210, JLabel.LEFT ),
      new FarmaColumnData( "Stock", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Precio", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Zan.", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Ind Cong", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor de fraccion de local
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor precio lista
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//valor igv producto
    };

	public static final Object[] defaultValuesListaProductosTransferencias = {"", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " "};

  //DlgTransferenciasNueva
  public static final FarmaColumnData columnsListaProductosTransferenciasNueva[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
      new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Precio", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Fec. Vto.", 70, JLabel.RIGHT ),
      new FarmaColumnData( "Lote", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Stock", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Valor Frac.", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Total", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Sysdate", 0, JLabel.RIGHT )
	  };

	public static final Object[] defaultValuesListaProductosTransferenciasNueva = {" ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "};

  //DlgTransferenciasRealizadas
  public static final FarmaColumnData columnsListaTransferenciasRealizadas[] = {
      new FarmaColumnData( "No. Transf.", 80, JLabel.LEFT ),
      new FarmaColumnData( "Local Dest.", 100, JLabel.LEFT ),
      new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
      new FarmaColumnData( "Items", 40, JLabel.RIGHT ),
      new FarmaColumnData( "Estado", 110, JLabel.LEFT ),
      new FarmaColumnData( "No. Guia", 80, JLabel.LEFT ),
      new FarmaColumnData( "Imp.", 30, JLabel.CENTER ),
      new FarmaColumnData( "Motivo", 160, JLabel.LEFT ),
      new FarmaColumnData( "Proc.", 30, JLabel.CENTER ),
      new FarmaColumnData( "", 0, JLabel.LEFT ),//tipo nota
      new FarmaColumnData( "", 0, JLabel.LEFT )//tipo nota origen
    };

	public static final Object[] defaultValuesListaTransferenciasRealizadas = { " ", " ", " ", " ", " "," ", " "};

/* AC INICIO */ 
  //DlgTransferenciasRealizadas
  public static final FarmaColumnData columnsListaDevolucionesRealizadas[] = {
      new FarmaColumnData( "No. Devoluc.", 75, JLabel.LEFT ),
      new FarmaColumnData( "Proveedor.", 180, JLabel.LEFT ),
      new FarmaColumnData( "Fecha Envio", 120, JLabel.LEFT ),
      new FarmaColumnData( "Items", 35, JLabel.RIGHT ),
      new FarmaColumnData( "Estado", 110, JLabel.LEFT ),
      new FarmaColumnData( "No. Guia", 80, JLabel.LEFT ),
      new FarmaColumnData( "Imp.", 30, JLabel.CENTER ),
      new FarmaColumnData( "Motivo", 135, JLabel.LEFT ),      
      new FarmaColumnData( "Cod.Estado", 0, JLabel.LEFT )
    };

	public static final Object[] defaultValuesListaDevolucionesRealizadas = { " ", " ", " "," ", " "," "," ", " ", " "};        
        
  //DlgListaOrdenesCompra
  public static final FarmaColumnData columnsListaOrdenesCompra[] = {
      new FarmaColumnData( "Codigo.", 100, JLabel.LEFT ),
      new FarmaColumnData( "Proveedor", 260, JLabel.LEFT ),
      new FarmaColumnData( "Fec. Venci.", 100, JLabel.RIGHT ),
      new FarmaColumnData( "Cant. Dias", 70, JLabel.LEFT ),
      new FarmaColumnData( "Fecha Entrega", 100, JLabel.LEFT ),
      new FarmaColumnData( "Cant. Items.", 70, JLabel.CENTER ),
    };    

    public static final Object[] defaultValuesListaOrdenesCompra = { " ", " ", " "," ", " ",""};
    
    //DlgDevolucionDetalle
    public static final FarmaColumnData columnsListaOrdenesCompraDet[] = {
        new FarmaColumnData( "Codigo.", 120, JLabel.LEFT ),
        new FarmaColumnData( "Producto", 280, JLabel.LEFT ),
        new FarmaColumnData( "Cant. Unid.", 100, JLabel.LEFT ),
        new FarmaColumnData( "Stock", 100, JLabel.LEFT ),
      };    

      public static final Object[] defaultValuesListaOrdenesCompraDet = { " ", " ", " "," "," "," "," "," "};    
      
      
    public static final FarmaColumnData columnsListaProductosDevolucionNueva[] = {
        new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
        new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
        new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
        new FarmaColumnData( "Laboratorio", 200, JLabel.LEFT ),
        new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
        new FarmaColumnData( "Precio", 60, JLabel.RIGHT ),
        new FarmaColumnData( "Fec. Vto.", 85, JLabel.RIGHT ),
        new FarmaColumnData( "Lote", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Stock", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Valor Frac.", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Total", 0, JLabel.RIGHT ),
        new FarmaColumnData( "Sysdate", 0, JLabel.RIGHT ),
        new FarmaColumnData( "", 0, JLabel.RIGHT )
     };

     public static final Object[] defaultValuesListaProductosDevolucionNueva = {" ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " "};
     
     
    public static final FarmaColumnData columnsListaProductosDevolucionVer[] = {
        new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
        new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
        new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
        new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
        new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
        new FarmaColumnData( "Precio", 60, JLabel.RIGHT ),
        new FarmaColumnData( "Fec. Vto.", 70, JLabel.RIGHT )
          };     
    
    public static final Object[] defaultValuesListaProductosDevolucionVer = {" ", " ", " ", " ", " ", " ", " "};    
      
/* AC FIN */

  //DlgTransferenciasVer
  public static final FarmaColumnData columnsListaProductosTransferenciasVer[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
      new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Precio", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Fec. Vto.", 70, JLabel.RIGHT )
	};

 public static final FarmaColumnData columnsListaProductosAK[] = {
            new FarmaColumnData( "Codigo", 55, JLabel.CENTER ),
            new FarmaColumnData( "Descripcion", 230, JLabel.LEFT ),
            new FarmaColumnData( "Unidad", 110, JLabel.LEFT ),
            new FarmaColumnData( "Laboratorio", 170, JLabel.LEFT ),
            new FarmaColumnData( "Unidad Frac", 100, JLabel.LEFT ),
            new FarmaColumnData( "C. Ent", 45, JLabel.RIGHT ),
            new FarmaColumnData( "C. Frac", 45, JLabel.RIGHT ),
            new FarmaColumnData( "Frac", 0, JLabel.RIGHT ),
            new FarmaColumnData( "", 0, JLabel.RIGHT ),// TIPO PROD VIRTUAL      
            new FarmaColumnData( "", 0, JLabel.RIGHT )//estado
	  };
    public static final Object[] defaultValuesListaProductosAK = {" "," ", " ", " ", " "," "," "," "," "};
    
    
    public static final FarmaColumnData columnsListaProductosInsmo[] = {
                new FarmaColumnData( "Codigo", 55, JLabel.CENTER ),
                new FarmaColumnData( "Descripcion", 230, JLabel.LEFT ),
                new FarmaColumnData( "Unidad", 110, JLabel.LEFT ),
                new FarmaColumnData( "Laboratorio", 170, JLabel.LEFT ),
                new FarmaColumnData( "Unidad Frac", 100, JLabel.LEFT ),
                new FarmaColumnData( "C. Ent", 45, JLabel.RIGHT ),
                new FarmaColumnData( "C. Frac", 45, JLabel.RIGHT ),
                new FarmaColumnData( "Frac", 0, JLabel.RIGHT ),
                new FarmaColumnData( "", 0, JLabel.RIGHT ),// TIPO PROD VIRTUAL      
                new FarmaColumnData( "", 0, JLabel.RIGHT ),//estado
                new FarmaColumnData( "Ult.Fec.Manual", 80, JLabel.LEFT )//ultima fecha manual
               
             };
	
	public static final Object[] defaultValuesListaProductosInsu = {" "," "," ", " ", " ", " "," "," "," "," "};

  public static final FarmaColumnData columnsListaMovsAK[] = {
	    new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
	    new FarmaColumnData( "Motivo.", 80, JLabel.LEFT ),
      new FarmaColumnData( "N. Documento", 100, JLabel.LEFT ),
	    new FarmaColumnData( "Stock Ant.", 75, JLabel.RIGHT ),
	    new FarmaColumnData( "Movimiento", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Stock Act.", 75, JLabel.RIGHT )
     
    };
	
	public static final Object[] defaultValuesListaMovsAK = {" ", " "," ", " ", " ", " "," ",};
 
 
	public static final Object[] defaultValuesListaProductosTransferenciasVer = {" ", " ", " ", " ", " ", " ", " "};

  public static final FarmaColumnData[] columnsListaMovsKardex = 
  { 
    new FarmaColumnData("Fecha",120,JLabel.CENTER),
    new FarmaColumnData("Descripcion",180,JLabel.LEFT),
    new FarmaColumnData("Tip. Doc",60,JLabel.LEFT),
    new FarmaColumnData("Num. Doc.",100,JLabel.CENTER),
    new FarmaColumnData("Stk.Ant",40,JLabel.RIGHT),
    new FarmaColumnData("Mov",40,JLabel.RIGHT),
    new FarmaColumnData("Stk.Act",40,JLabel.RIGHT),
    new FarmaColumnData("Frac.",40,JLabel.RIGHT),
    new FarmaColumnData("Usuario",90,JLabel.LEFT), 
    new FarmaColumnData("Glosa",100,JLabel.LEFT), 
    new FarmaColumnData("ord",0,JLabel.CENTER) ,
    new FarmaColumnData("suma",0,JLabel.CENTER),
    new FarmaColumnData("",0,JLabel.CENTER),//NUMERO PEDIDO
    new FarmaColumnData("",0,JLabel.CENTER),//INDICADOR EXCLUIDO
    new FarmaColumnData("",0,JLabel.CENTER)//COD MOT KARDEX
  
  };
  
  public static final Object[] defaultListaMovsKardex = {" "," "," "," ", " ", " ", " ", " ", " ", " "," "," "," "," "};
  
  
  public static final FarmaColumnData[] columnsListaAjustesporFecha = 
  { 
    new FarmaColumnData("Codigo",60,JLabel.LEFT),
    new FarmaColumnData("Descripcion",200,JLabel.LEFT),
    new FarmaColumnData("Fecha",120,JLabel.CENTER),
    new FarmaColumnData("Motivo",182,JLabel.LEFT),
    new FarmaColumnData("Stk.Ant",40,JLabel.RIGHT),
    new FarmaColumnData("Mov",40,JLabel.RIGHT),
    new FarmaColumnData("Stk.Act",40,JLabel.RIGHT),
    new FarmaColumnData("Val. Fracc.",60,JLabel.RIGHT),
    new FarmaColumnData("ord",0,JLabel.CENTER),
    new FarmaColumnData("Laboratorio",0,JLabel.LEFT),
    new FarmaColumnData("Usuario",0,JLabel.LEFT), 
    new FarmaColumnData("Glosa",0,JLabel.LEFT), 
    
  };
  
  public static final Object[] defaultListaAjustesporFecha = {" "," "," ", " ", " ", " ", " ", " ", " "," "," "," "};
  
   //DlgListaImpresoras
  public static final FarmaColumnData columnsListaImpresoras[] = {
	    new FarmaColumnData( "Sec.", 60, JLabel.LEFT ),
      new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
	    new FarmaColumnData( "Num. Comp.", 100, JLabel.LEFT ),     
  };

	public static final Object[] defaultValuesListaImpresoras = {" ", " ", " "};
  
  //DlgExcesoListado
  public static final FarmaColumnData columnsListaExcesos[] = {
	    new FarmaColumnData( "Sec", 80, JLabel.LEFT ),
      new FarmaColumnData( "Cod", 60, JLabel.LEFT ),
      new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 150, JLabel.LEFT ),
	    new FarmaColumnData( "Cant", 50, JLabel.RIGHT ),     
      new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
	    new FarmaColumnData( "Num. Entrega", 80, JLabel.LEFT ),     
	    new FarmaColumnData( "Numero Lote", 0, JLabel.LEFT ),     
      new FarmaColumnData( "Fecha Venc.", 0, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 0, JLabel.LEFT )
  };

	public static final Object[] defaultValuesListaExcesos = {" ", " "," ", " "," ", " ", " ", " ", " ", " "};
  
  //DlgExcesoListado
  public static final String vNombreInHashtableExceso = "cmbOrdenarExceso";
  public static final String[] vCodCampoExceso = {"0", "1","5", "6"};
  public static final String[] vDescCampoExceso = {"Sec", "Cod","Fecha", "Num. Entrega"};
  
  //DlgExcesoListado
  public static final String vNombreInHashtableGuiaRecep = "cmbOrdenarGuiaRecep";
  public static final String[] vCodCampoGuiaRecep = {"1","5"};
  public static final String[] vDescCampoGuiaRecep = {"Guia", "Num. Entrega"};
  
  //DlgGuiasRemision
  public static final FarmaColumnData[] columnsListaGuiasRemision = {
			new FarmaColumnData("Correlativo", 90, JLabel.CENTER),
      new FarmaColumnData("Sec.", 30, JLabel.RIGHT),
      new FarmaColumnData("Tipo Nota", 120, JLabel.CENTER),
      new FarmaColumnData("No. Guía", 90, JLabel.CENTER),
      new FarmaColumnData("Fecha", 120, JLabel.CENTER),
			new FarmaColumnData("Estado", 80, JLabel.CENTER),
      new FarmaColumnData("Ord. Guia", 0, JLabel.CENTER),
      new FarmaColumnData("Tipo Nota", 0, JLabel.CENTER)
	};

	public static final Object[] defaultListaGuiasRemision = {" ", " "," ", " "," "," "," "};
  
  //DlgTransferenciasPorConfirmar
  public static final FarmaColumnData columnsListaTransfPorConfirmar[] = {
      new FarmaColumnData( "No. Transf.", 80, JLabel.LEFT ),
      new FarmaColumnData( "Local Dest.", 100, JLabel.LEFT ),
      new FarmaColumnData( "No. Guia.", 80, JLabel.LEFT ),
      new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
      new FarmaColumnData( "Items", 40, JLabel.RIGHT ),
      new FarmaColumnData( "Estado", 60, JLabel.LEFT )
    };
    
  public static final Object[] defaultValuesListaTransfPorConfirmar = { " ", " ", " ", " "," ", " "};
  
  //DlgGuiasRemision
  public static final String vNombreInHashtableGuiaRem = "cmbOrdenarGuiaRem";
  public static final String[] vCodCampoGuiaRem = {"6","0"};
  public static final String[] vDescCampoGuiaRem = {"Numero Guía Remisión", "Correlativo"};
    
  //DlgPedidoReposicionDetalle
  public static final String vNombreInHashtablePedRep = "cmbOrdenarReposicion";
  public static final String[] vCodCampoPedRep = {"0","1","2","3","4","5"};
 /* public static final String[] vDescCampoPedRep = {"Codigo", "Descripcion","Unidad","Solicitud",
                                                   "Sugerido","Stock","Vendidos","Minimo","Maximo",
                                                   "Rotacion","Max Dia"};*/
  public static final String[] vDescCampoPedRep = {"Codigo", "Descripcion","Unidad","Solicitud",
                                                    "Stock","PVM"};

    
  public static final String TIP_OPERACION_RESPALDO_SUMAR = "S";
  public static final String TIP_OPERACION_RESPALDO_BORRAR = "B";
  //public static final String TIP_OPERACION_RESPALDO_LIMPIAR = "L";
  public static final String TIP_OPERACION_RESPALDO_EJECUTAR = "E";
  public static final String TIP_OPERACION_RESPALDO_ACTUALIZAR = "A";
  
  public static final String INDICADOR_A = "A";
  public static final String INDICADOR_D = "D";

  //
  public static final String NOM_HASHTABLE_CMBTIPO = "cmbTipoDoc";
  public static final String NOM_HASHTABLE_CMBGUIA = "cmbTipoGuia";
  
  public static final String IND_PORCENTAJE = "%";
  
  public static final String NOM_HASHTABLE_CMBTIPO_TRANSF = "cmbTipoTransf";
  public static final String NOM_HASHTABLE_CMBMOTIVO_TRANSF = "cmbMotivoTransf";
  
  //Cesar Huanes
  public static final String NOM_HASHTABLE_CMBMOTIVO_DEVOLUCION = "cmbMotivo";
  
  //Agregado por Paulo
  public static final String NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO = "cmbTipoTransfInterno";

  
  public static final String HASHTABLE_ORDENAR_RECEPCION = "HASHTABLE_ORDENAR_RECEPCION";
  
  //Histórico de Creación/Modificación
  //ERIOS      19.07.2006   Creación
  //DlgGuiasIngreso
  public static String NOM_HASTABLE_CMBFILTRO_GUIA = "cmbFiltroGuiaIngreso";
  public static String[] COD_TIPO_GUIA = {"01","04","02"};
  public static String[] DESC_TIPO_GUIA = {"Local","Competencia","Matriz"};
  
  //Histórico de Creación/Modificación
  //ERIOS      25.07.2006   Creación
  //DlgTransferenciasLocal
  public static final FarmaColumnData columnsListaTransfLocal[] = {
      new FarmaColumnData( "No. Transf.", 80, JLabel.LEFT ),
      new FarmaColumnData( "Local Origen.", 100, JLabel.LEFT ),
      new FarmaColumnData( "No. Guia.", 80, JLabel.LEFT ),
      new FarmaColumnData( "Fecha", 120, JLabel.LEFT ),
      new FarmaColumnData( "Items", 40, JLabel.RIGHT ),
      new FarmaColumnData( "CodLocal", 0, JLabel.RIGHT )
    };

	public static final Object[] defaultValuesListaTransfLocal = { " ", " ", " ", " ", " ", " "};

  //DlgTransferenciasVer
  public static final FarmaColumnData columnsListaProductosTransfLocalVer[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
      new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Precio", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Fec. Vto.", 70, JLabel.RIGHT )
	}; 
  
  public static final Object[] defaultValuesListaProductosTransfLocalVer = {" ", " ", " ", " ", " ", " ", " "};
  
  //DlgTransferenciasRealizadas
  public static String NOM_HASTABLE_CMBFILTRO_TRANSF = "cmbFiltroMotivoTransf";
      
  //DlgStockLocales
  public static final FarmaColumnData columnsListaStockLocalesPreferidos[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 150, JLabel.LEFT ),
      new FarmaColumnData( "Stock Local", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Unidad Venta", 120, JLabel.LEFT ),
	};   
  public static final Object[] defaultValuesListaStockLocalesPreferidos = {" ", " ", " ", " "};

  public static final FarmaColumnData columnsListaStockDemasLocales[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 150, JLabel.LEFT ),
      new FarmaColumnData( "Stock Local", 75, JLabel.RIGHT ),
      new FarmaColumnData( "Unidad Venta", 120, JLabel.LEFT ),
	};   
  public static final Object[] defaultValuesListaStockDemasLocales = {" ", " ", " ", " "};
  
  /**
   * Variables para el listado de Competencias
   * @author dubilluz
   * @since  12.11.2007
   */
  //Lista Competencia
  public static final FarmaColumnData columnsListaCompetencias[] = {
	  new FarmaColumnData( "RUC", 100, JLabel.LEFT ),
    new FarmaColumnData( "Descripcion", 220, JLabel.LEFT )    
	};
  public static final Object[] defaultValuesListaCompetencias = {" ", " "};
  
  
  public static final String TIP_NOTA_GUIA = "01";
  public static final String TIP_NOTA_ORIGEN_GUIA = "04";
  
  public static final String TIP_NOTA_TRANS = "02";
  public static final String TIP_NOTA_ORIGEN_TRANS = "02";
  
  /**
   * Autor: Luis Reque Orellana
   * Fecha: 25/01/2007
   * */
  /*Arreglos para Ordenamiento*/
  public static final String[] ORDEN = {"ASC","DESC"};
  public static final String[] ORDENVAL ={FarmaConstants.ORDEN_ASCENDENTE,FarmaConstants.ORDEN_DESCENDENTE};

  public static final String VNOMBREINHASHTABLEPEDIDOREP ="IND_CAMPO_ORDENAR_PEDIDO_REP";
  
  public static final String VNOMBREINHASHTABLEPEDIDOREPFINAL ="IND_CAMPO_ORDENAR_PEDIDO_REP_FINAL";
  
  public static final String COD_MOT_VENTA_NORMAL = "001";
  
  public static final String COD_MOT_VENTA_DELIVERY = "002";
  
  public static final String COD_MOT_VENTA_ESPECIAL = "003";
  
  
  /**
   * Se declaran las variables para las tablas Pedido Adicional
   * @author Dubilluz
   * @since  09.09.2008
   */
  
  public static final FarmaColumnData[] columnsListaParamProdLocal =
  {
    new FarmaColumnData("Unid.Autorizada", 0, JLabel.RIGHT ) ,
    new FarmaColumnData("Codigo",50,JLabel.LEFT),
    new FarmaColumnData("Descripcion",250,JLabel.LEFT),
    new FarmaColumnData("Unidad Presentacion",120,JLabel.LEFT),
    new FarmaColumnData("Laboratorio",210,JLabel.LEFT),
    new FarmaColumnData("Unid.Solicitada",100,JLabel.LEFT),
    new FarmaColumnData("Unid.Autorizada",100, JLabel.RIGHT )
    
  };

  public static final Object[] defaultListaParamProdLocal = { " "," ", " ", " ", " ", " ", " "};
  
  
  /**
   * @author  Daniel Fernando Veliz La Rosa
   * @since   10.09.08
   */

   //DlgPedidoReposicionDetalle
   public static final String vNombreInHashtablePedPVM = "cmbOrdenarAdicional";
   public static final String[] vCodCampoPedPVM = {"0","1","2","3","4","5","6","7"};
   /* public static final String[] vDescCampoPedRep = {"Codigo", "Descripcion","Unidad","Solicitud",
                                                    "Sugerido","Stock","Vendidos","Minimo","Maximo",
                                                    "Rotacion","Max Dia"};*/
   public static final String[] vDescCampoPedPVM = {"Codigo", "Estado",
                                                    "Descripcion","Unidad",
                                                     "Stk.Linea","PVM",
                                                    "Solic","Autoriz"};
   
   public static final FarmaColumnData columnsListaProductosPedidoReposicionAdicional[] = {
      new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),//0
      new FarmaColumnData( "Estado", 110, JLabel.LEFT),//01
      new FarmaColumnData( "Descripcion", 240, JLabel.LEFT ),//2
      new FarmaColumnData( "Unidad", 115, JLabel.LEFT ),//3
      new FarmaColumnData( "Stk.Linea", 70, JLabel.RIGHT ),//4
      new FarmaColumnData( "PVM", 51, JLabel.RIGHT ),//5
      new FarmaColumnData( "Solic.", 51, JLabel.RIGHT ),//6
      new FarmaColumnData( "Autoriz.", 51, JLabel.RIGHT )/*,//7
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//Laboratorio
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//Stock Fisico
      new FarmaColumnData( "", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//Laboratorio
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//Stock Fisico
      new FarmaColumnData( "", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT ),//Stock Fisico
      new FarmaColumnData( "", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT )*/
   };

    public static final Object[] defaultListaProductosPedidoReposicionAdicional
       = { " "," ", " ", " ", " ", " ", " ", " "};//," ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
                        
   
   /**
    * Variables de cabceras de historico de PVM Local.
    * @author DVELIZ
   *  @since  16.09.2008
    */
    public static final FarmaColumnData columnsHistorialPedidoAdicional[] = {
       new FarmaColumnData( "Solici.", 50, JLabel.LEFT ),//0
       new FarmaColumnData( "Autori.", 50, JLabel.LEFT),//01
       new FarmaColumnData( "Registrado Por", 150, JLabel.LEFT ),//2
       new FarmaColumnData( "Fecha Registro", 130, JLabel.LEFT ),//3
       new FarmaColumnData( "Lugar", 100, JLabel.LEFT )
    };

     public static final Object[] defaultHistorialPedidoAdicional
                         = { " "," ", " ", " ", " "};

  
  /**
   * Se declaran las variables para el listado de pedidos especiales
   * @author JCORTEZ
   * @since  09.09.2008
   */
  public static final FarmaColumnData columnsListaPedidosEspeciales[] = {
      new FarmaColumnData( "No Pedido", 90, JLabel.LEFT ),
      new FarmaColumnData( "Fecha", 130, JLabel.LEFT ),
      new FarmaColumnData( "Items", 50, JLabel.RIGHT ),
      new FarmaColumnData( "Estado", 90, JLabel.RIGHT),
      new FarmaColumnData( "Usuario", 90, JLabel.RIGHT),
      new FarmaColumnData( "Fecha Envio", 130, JLabel.LEFT),
      new FarmaColumnData( "", 0, JLabel.RIGHT)};
      
  public static final Object[] defaultListaPedidosEspeciales 
                        = { " "," ", " "," "," "," "," "," "," "};

  
   //DlgEspecialListaProductos
  public static final FarmaColumnData columnsListaProductosEspeciales[] = {
      new FarmaColumnData( "Sel.", 30, JLabel.LEFT ),
      new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
      new FarmaColumnData( "Descripcion", 230, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 110, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 180, JLabel.LEFT ),
      new FarmaColumnData( "Stock", 70, JLabel.RIGHT ),
      new FarmaColumnData( "Cant.Sol.", 60, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT )
    };

  public static final Object[] defaultValuesListaProductosEspeciales 
                    = {" ", " ", " "," ", " ", " ", " ", " ", " "};
  
  public static final String SEC_PED_ESP = "046";
  
    //DlgTransferenciasVer
  public static final FarmaColumnData columnsListaProductosPedEspVer[] = {
	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
	    new FarmaColumnData( "Descripcion", 210, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 100, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
      new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Estado", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT )};
      
      public static final Object[] defaultValuesListaProductosPedEspVer = {" ", " ", " "," "," "," "," "};

  public static final String MENSAJE_MATRIZ_1 = "Esta función no está habilitada en Local";
  public static final String EST_PENDIENTE = "P";
  public static final String EST_CONFIRMADO = "C";
  public static final String EST_ANULADO = "N";
  public static final String EST_MODIFICADO = "M";
  
  //Se crea las constantes para la validacion de ingreso pedido especial
  public static final int CONS_ING_PROD_ESPC = 0;
  public static final int CONS_ING_PED_ESPC = 1;
  
  
  //jcallo 16.10.2008 DlgResumenPedidoEsp
  public static final FarmaColumnData columnsListaResumenPedidoEsp[] = {      
      new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
      new FarmaColumnData( "Descripcion", 230, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 110, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 180, JLabel.LEFT ),
      new FarmaColumnData( "Stock", 70, JLabel.RIGHT ),
      new FarmaColumnData( "Cant.Sol.", 60, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.RIGHT )
    };

  public static final Object[] defaultValuesListaResumenPedidoEsp 
                    = {" ", " "," ", " ", " ", " ", " ", " "};
  
    public static final String ESTADO_PENDIENTE_PEDIDO_ESPECIAL = "PENDIENTE";
    public static final String ESTADO_MODIFICADO_PEDIDO_ESPECIAL = "MODIFICADO";
    
    public static final String TIPO_PED_REG_ZREG = "ZREG";
    public static final String TIPO_PED_REG_UB = "UB";
    public static final String TIPO_PED_REG_ZTNL = "ZTNL";
    public static final String TIPO_PED_REG_UB_L = "UB-L";
    
    //DUBILLUZ
    public static final boolean IND_CAMBIO_DU = true;
    
    /**
     * Variables para DlgOrdenCompraResumen
     * @Author Luis Ruiz.
     * @since  21.05.2013. 
     * 
     */
    public static final FarmaColumnData[] columnsListaResumenOrden = 
    {
        new FarmaColumnData("Nº O/C",100,JLabel.CENTER),
        new FarmaColumnData("Local",35,JLabel.RIGHT),
        new FarmaColumnData("RUC",85,JLabel.RIGHT),
        new FarmaColumnData("Proveedor",150,JLabel.LEFT),
        new FarmaColumnData("Nº Items",60,JLabel.RIGHT),
        new FarmaColumnData("Importe Total",90,JLabel.RIGHT),
        new FarmaColumnData("Fec. Emisión.",85,JLabel.CENTER),
        new FarmaColumnData("Fec. Entrega.",85,JLabel.CENTER),
        new FarmaColumnData("Cod. Proveedor",0,JLabel.RIGHT),
        new FarmaColumnData("Cod. Forma de Pago",0,JLabel.RIGHT),
        new FarmaColumnData("Forma de Pago",0,JLabel.RIGHT)
    }; 
    public static final Object[] defaultListaResumenOrden = {" ", " ", " "," ", " ", " ", " ", " ", " ", " "};
    
    
    //INI ASOSA - 26/08/2014
    public static final FarmaColumnData[] columnsListaOrdenPendRegul = 
    {
        new FarmaColumnData("Nº O/C",100,JLabel.CENTER),
        new FarmaColumnData("Local",35,JLabel.RIGHT),
        new FarmaColumnData("RUC",85,JLabel.RIGHT),
        new FarmaColumnData("Proveedor",200,JLabel.LEFT),
        new FarmaColumnData("Nº Items",60,JLabel.RIGHT),
        new FarmaColumnData("Importe Total",90,JLabel.RIGHT),
        new FarmaColumnData("Fec. Emisión.",85,JLabel.CENTER),
        new FarmaColumnData("Fec. Entrega.",85,JLabel.CENTER)
    }; 
    public static final Object[] defaultListaOrdenPendRegul = {" ", " ", " ", " ", " ", " ", " ", " "};
    //FIN ASOSA - 26/08/2014
    
    public static final FarmaColumnData[] columnsListaItemOrden = {
        new FarmaColumnData("Sel",35,JLabel.LEFT),
        new FarmaColumnData("Cod. Prod.",70,JLabel.RIGHT),
        new FarmaColumnData("Descripción",385,JLabel.LEFT),
        new FarmaColumnData("Cant. Ped.",70,JLabel.RIGHT),
        new FarmaColumnData("Cant. Rec.",70,JLabel.RIGHT),
        new FarmaColumnData("Precio",0,JLabel.RIGHT),//No se desea mostrar el precio
        new FarmaColumnData("IGV",0,JLabel.RIGHT),//No se desea mostrar el igv
        new FarmaColumnData("CANT_PRESENT",0,JLabel.RIGHT),
        new FarmaColumnData("VAL_FRAC_LOCAL",0,JLabel.RIGHT)
    };
    public static final Object[] defaultListaItemOrden = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}; //ASOSA - 30/09/2014
    
    //INI ASOSA - 19/08/2014
    public static final FarmaColumnData[] columnsListaItemOrdenSobr = {
        new FarmaColumnData("Sel",35,JLabel.LEFT), 
        new FarmaColumnData("Cod. Prod.",70,JLabel.RIGHT),
        new FarmaColumnData("Descripción",385,JLabel.LEFT),
        new FarmaColumnData("Cant. Exceso",80,JLabel.RIGHT),
        new FarmaColumnData("Cant. Devol.",70,JLabel.RIGHT),
        new FarmaColumnData("Precio",0,JLabel.RIGHT),//No se desea mostrar el precio
        new FarmaColumnData("IGV",0,JLabel.RIGHT),//No se desea mostrar el igv
        new FarmaColumnData("CANT_PRESENT",0,JLabel.RIGHT),
        new FarmaColumnData("VAL_FRAC_LOCAL",0,JLabel.RIGHT), //8
        new FarmaColumnData("Laboratorio",0,JLabel.RIGHT),
        new FarmaColumnData("StockDisponible",0,JLabel.RIGHT),
        new FarmaColumnData("FechaVencimiento",0,JLabel.RIGHT),
        new FarmaColumnData("NumeroLote",0,JLabel.RIGHT), //12
        new FarmaColumnData("cantidad adicional",0,JLabel.RIGHT),
        new FarmaColumnData("innerPack",0,JLabel.RIGHT),
        new FarmaColumnData("estado",0,JLabel.RIGHT),
        new FarmaColumnData("subtotal",0,JLabel.RIGHT),//16
        new FarmaColumnData("codOrdenCompra",0,JLabel.RIGHT),
        new FarmaColumnData("secuencialDetalle",0,JLabel.RIGHT)
            
    };
    public static final Object[] defaultListaItemOrdenSobr = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
    //FIN ASOSA - 19/08/2014
    
    
    
    public static final FarmaColumnData[] columnsListaProductoOrden = 
    {
        new FarmaColumnData("Cod. Producto",97,JLabel.RIGHT),
        new FarmaColumnData("Descripción",195,JLabel.LEFT),
        new FarmaColumnData("Cant. Pedida",85,JLabel.RIGHT),
        new FarmaColumnData("Cant. Recibida",85,JLabel.RIGHT),
        new FarmaColumnData("Precio",95,JLabel.RIGHT),
        new FarmaColumnData("IGV",33,JLabel.RIGHT)
    }; 
    public static final Object[] defaultListaProductoOrden = {" ", " ", " "," ", " ", " ", " ", " ", " ", " "};
    
    public static final FarmaColumnData[] columnsListaDocumentos = 
    {
        new FarmaColumnData("Ord. Compra",85,JLabel.RIGHT),
        new FarmaColumnData("Num. Guia", 80, JLabel.RIGHT),
        new FarmaColumnData("Tipo.",70,JLabel.LEFT),
        new FarmaColumnData("Serie",50,JLabel.RIGHT),
        new FarmaColumnData("Num. Docum.",100,JLabel.RIGHT),
        new FarmaColumnData("Proveedor",150,JLabel.LEFT),
        new FarmaColumnData("Fec. Ingreso",75,JLabel.RIGHT),
        new FarmaColumnData("Redondeo",67,JLabel.RIGHT),
        new FarmaColumnData("Importe Docum.",90,JLabel.RIGHT),
        new FarmaColumnData("Estado",0,JLabel.RIGHT),
        new FarmaColumnData("TipComp",0,JLabel.RIGHT),
        new FarmaColumnData("CodProv",0,JLabel.RIGHT),
        new FarmaColumnData("SecRecep",0,JLabel.RIGHT)        
    };
    
    public static final Object[] defaultListaDocumentos = {" ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " "};  
    
    public static final FarmaColumnData[] columnsListaGuiasNoMuevenStock= {
    new FarmaColumnData("N° Nota",100,JLabel.RIGHT),
    new FarmaColumnData("Num. Guia Rem", 100, JLabel.RIGHT),
    new FarmaColumnData("Fecha Crea. Guia.",120,JLabel.LEFT),
    new FarmaColumnData("Usu. Crea. Guia",100,JLabel.LEFT),
    new FarmaColumnData("Estado",120,JLabel.LEFT),
    new FarmaColumnData("Impre.",50,JLabel.CENTER)
        };
    
    public static final Object[] defaultListaGuiasNoMuevenStock = {" ", " ", " "," ", " ", " "};  
    
    
    public static final FarmaColumnData[] columnsListaMaestroProductos = {
      //  new FarmaColumnData("Sel",30,JLabel.LEFT),
        new FarmaColumnData( "Codigo.", 120, JLabel.LEFT ),
        new FarmaColumnData( "Producto", 280, JLabel.LEFT ),
        new FarmaColumnData( "Cant. Unid.", 100, JLabel.LEFT ),
        new FarmaColumnData( "Stock", 100, JLabel.LEFT ),
        new FarmaColumnData( "Uni.Venta",0, JLabel.LEFT ),       
        new FarmaColumnData( " ", 0, JLabel.LEFT ),//Ind prod Fraccionable
        new FarmaColumnData( " ", 0, JLabel.LEFT ),//Nom laboratorio
        new FarmaColumnData( " ", 0, JLabel.LEFT ),//Ind prod cong
        new FarmaColumnData( " ", 0, JLabel.LEFT ),//Fecha
        new FarmaColumnData( " ", 0, JLabel.LEFT ),//valor precio venta
        new FarmaColumnData( " ", 0, JLabel.LEFT ),//valor frac local
        new FarmaColumnData( " ", 0, JLabel.LEFT ),//precio de venta vigente
        new FarmaColumnData( " ", 0, JLabel.LEFT )//descripcion unidad de venta
        
      };    

      public static final Object[] defaultValuesListaMaestroProducto = { " ", " "," "," "," "," "," "," "," "," "," "," "," "};    
   
      public static final String COD_MOT_KDX_SOBRANTE = "112";  //ASOSA - 18/08/2014
      
      public static final int COD_IND_ING_MANUAL = 522; //ASOSA - 26/08/2014
      public static final int COD_NUM_CANT_MULT = 523; //ASOSA - 27/08/2014
}