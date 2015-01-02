package mifarma.ptoventa.delivery.reference;

import javax.swing.*;
import mifarma.common.FarmaColumnData;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : ConstantsVentas.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* ERIOS      11.04.2006   Creación<br>
* <br>
* @author Edgar Rios Navarro<br>
* @version 1.0<br>
*
*/
public class ConstantsDelivery
{
  public ConstantsDelivery()
  {
  }
  //DlgListaClientes
    public static final FarmaColumnData[] columnsListaClientes =
    {
      new FarmaColumnData("Código",60,JLabel.LEFT),
      new FarmaColumnData("Nombre",280,JLabel.LEFT),
      new FarmaColumnData("Dni",0,JLabel.LEFT),
      new FarmaColumnData("Codigo Direccion",0,JLabel.LEFT),
      new FarmaColumnData("Paterno",0,JLabel.LEFT),
      new FarmaColumnData("Materno",0,JLabel.LEFT),
      new FarmaColumnData("Nombre",0,JLabel.LEFT),
      new FarmaColumnData("Obs",300,JLabel.LEFT)
      
    };

    public static final Object[] defaultValuesListaClientes = { " ", " ", " ", " ",""};

    //DlgPedido
    public static final FarmaColumnData columnsListaPedidos[] = {
  	    new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
        new FarmaColumnData( "Descripcion", 150, JLabel.LEFT ),
        new FarmaColumnData( "Unidad", 120, JLabel.LEFT ),
  	    new FarmaColumnData( "Precio", 75, JLabel.LEFT ),
        new FarmaColumnData( "Cantidad", 70, JLabel.LEFT ),
        new FarmaColumnData( "% Dscto", 70, JLabel.RIGHT ),
        new FarmaColumnData( "Precio Venta", 80, JLabel.LEFT ),
        new FarmaColumnData( "Total", 75, JLabel.LEFT )
      };

  	public static final Object[] defaultValuesListaPedidos = {" ", " ", " ", " ", " ", " ", " "};

    public static final FarmaColumnData[] columnsListaBusquedaApellido =
    {
      new FarmaColumnData("Codigo",0,JLabel.LEFT),
      new FarmaColumnData("Nombre",240,JLabel.LEFT),
      new FarmaColumnData("Dni",95,JLabel.LEFT),
      new FarmaColumnData("Codigo Direccion",0,JLabel.LEFT),
      new FarmaColumnData("Paterno",0,JLabel.LEFT),
      new FarmaColumnData("Materno",0,JLabel.LEFT),
      new FarmaColumnData("Nombre",0,JLabel.LEFT),
    };

    public static final Object[] defaultValuesListaBusquedaApellido = {" ", " ", " "," "};

    public static final FarmaColumnData[] columnsListaPedidosPendientes = {
  			new FarmaColumnData("Fec. Pedido", 110, JLabel.CENTER),
  			new FarmaColumnData("Num Ped.", 90, JLabel.CENTER),
  			new FarmaColumnData("Nom. Cliente", 140, JLabel.LEFT),
        new FarmaColumnData("Telf. Cliente", 90, JLabel.CENTER),
  			new FarmaColumnData("Monto", 90, JLabel.RIGHT),
  			new FarmaColumnData("Estado", 90, JLabel.CENTER),};

  	public static final Object[] defaultValuesListaPedidosPendientes = {" "," ", " ", " ", " "," " };


  public static final FarmaColumnData[] columnsListaPendientesCab = {
        new FarmaColumnData("Nombre Cliente", 100, JLabel.LEFT),
  			new FarmaColumnData("Num Ped Dia", 85, JLabel.CENTER),
  			new FarmaColumnData("Numero", 80, JLabel.CENTER),
  			new FarmaColumnData("Fecha", 120, JLabel.CENTER),
  			new FarmaColumnData("Bruto", 90, JLabel.RIGHT),
  			new FarmaColumnData("Dscto", 90, JLabel.RIGHT),
  			new FarmaColumnData("Neto", 90, JLabel.RIGHT),
  			new FarmaColumnData("IGV", 90, JLabel.RIGHT),
  			new FarmaColumnData("Total S/.", 140, JLabel.RIGHT),
  			new FarmaColumnData("Redo", 140, JLabel.RIGHT),
  			new FarmaColumnData("RUC", 140, JLabel.CENTER),

      };

  	public static final Object[] defaultListaPendientesCab = { " ", " ", " ", " ", " ", " ", " ", " ", " "," "," "};



  public static final FarmaColumnData[] columnsDetallePedido = {
      new FarmaColumnData("Codigo",60,JLabel.LEFT),
      new FarmaColumnData("Producto",140,JLabel.LEFT),
      new FarmaColumnData("Unidad",120,JLabel.LEFT),
      new FarmaColumnData("Precio",90,JLabel.RIGHT),
      new FarmaColumnData("% Dscto",70,JLabel.RIGHT),
      new FarmaColumnData("P.Venta",100,JLabel.RIGHT),
      new FarmaColumnData("Cantidad",100,JLabel.RIGHT),
      new FarmaColumnData("Total",100,JLabel.RIGHT),
       };

    public static final Object[] defaultDetallePedido = {" ", " ", " ", " ", " ", " "," "," "};
    public static final FarmaColumnData[] columnsListaPendientes = {
        new FarmaColumnData("Nombre CLiente", 100, JLabel.LEFT),
  			new FarmaColumnData("Num Ped Dia", 85, JLabel.CENTER),
  			new FarmaColumnData("Numero", 80, JLabel.CENTER),
  			new FarmaColumnData("Fecha", 120, JLabel.CENTER),
  			new FarmaColumnData("Bruto", 90, JLabel.RIGHT),
  			new FarmaColumnData("Dscto", 90, JLabel.RIGHT),
  			new FarmaColumnData("Neto", 90, JLabel.RIGHT),
  			new FarmaColumnData("IGV", 90, JLabel.RIGHT),
  			new FarmaColumnData("Total S/.", 140, JLabel.RIGHT),
  			new FarmaColumnData("Redo", 140, JLabel.RIGHT),
  			new FarmaColumnData("RUC", 140, JLabel.CENTER),
  			new FarmaColumnData("Ped. Dia.",0,JLabel.LEFT),
  			new FarmaColumnData("",0,JLabel.LEFT)
      };

  	public static final Object[] defaultListaPendientes = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "," "," "};

     public static final FarmaColumnData columnsListaMaestros[] = {
      new FarmaColumnData( "Codigo", 70, JLabel.LEFT ),
  	  new FarmaColumnData( "Descripcion", 220, JLabel.LEFT ),
  	};
    public static final Object[] defaultValuesListaMaestros = {" ", " "};

    public static final FarmaColumnData[] columsListaFormasPago =
    {
      new FarmaColumnData("Codigo",80,JLabel.CENTER),
      new FarmaColumnData("Forma de Pago",200,JLabel.LEFT),
    };

    public static final Object[] defaultListaFormasPago = {" ", " "};

    public static final FarmaColumnData columnsListaTarjetasClientes[] = {
      new FarmaColumnData( "Operador", 100, JLabel.CENTER ),
      new FarmaColumnData( "Numero", 100, JLabel.LEFT ),
      new FarmaColumnData( "Nombre Tarjeta", 150, JLabel.LEFT ),
      new FarmaColumnData( "Fecha Venc", 100, JLabel.LEFT ),
      new FarmaColumnData( "Estado", 90 , JLabel.LEFT ),
      new FarmaColumnData( "DNI", 90 , JLabel.LEFT ),
      new FarmaColumnData( "Secuencial",0, JLabel.LEFT ),
      new FarmaColumnData( "Codigo", 0 , JLabel.LEFT ),
      new FarmaColumnData( "Mes", 0, JLabel.LEFT ),
      new FarmaColumnData( "Año", 0 , JLabel.LEFT ),
    };
  	public static final Object[] defaultValuesListaTarjetasClientes = {" "," "," "," "," "," "," "," "," "};

   //Lista Clientes Consulta
     public static final FarmaColumnData[] columnsListaClientesConsulta =
    {
      new FarmaColumnData("Codigo",100,JLabel.CENTER),
      new FarmaColumnData("Cliente",350,JLabel.LEFT),
      new FarmaColumnData("DNI",120,JLabel.CENTER),
      new FarmaColumnData("nomCli",0,JLabel.CENTER),
      new FarmaColumnData("apePat",0,JLabel.CENTER),
      new FarmaColumnData("apeMat",0,JLabel.CENTER),
      new FarmaColumnData("tipDoc",0,JLabel.CENTER),
      new FarmaColumnData("indCliJurid",0,JLabel.CENTER),
    };

    public static final Object[] defaultValuesListaClientesConsulta = { " "," "," "," "," "," ", " ", " "};

    //Lista Direcciones Consulta
     public static final FarmaColumnData[] columnsListaDireccionesConsulta =
    {
       new FarmaColumnData("Teléfono",0,JLabel.CENTER),
       new FarmaColumnData("Dirección",380,JLabel.LEFT),
        new FarmaColumnData("NomDist",0,JLabel.CENTER),
        new FarmaColumnData("CodDir",0,JLabel.CENTER),
       new FarmaColumnData("NomCalle",0,JLabel.CENTER),
       new FarmaColumnData("NroCalle",0,JLabel.CENTER),
        new FarmaColumnData("NroInterior",0,JLabel.CENTER),
       new FarmaColumnData("NomUrb",0,JLabel.CENTER),
              new FarmaColumnData("Referencia",200,JLabel.LEFT),       
       new FarmaColumnData("DesTipCalle",0,JLabel.CENTER),
        new FarmaColumnData("TipoCalle",0,JLabel.CENTER),
        new FarmaColumnData("Principal",70,JLabel.CENTER),

        new FarmaColumnData("Departamento",0,JLabel.LEFT),       
        new FarmaColumnData("Provincia",0,JLabel.CENTER),
        new FarmaColumnData("Distrito",120,JLabel.CENTER),
        new FarmaColumnData("Urb.",0,JLabel.CENTER),

        new FarmaColumnData("CodigoDepartamento",0,JLabel.LEFT),
        new FarmaColumnData("CodigoProvincia",0,JLabel.CENTER),
        new FarmaColumnData("CodigoDistrito",0,JLabel.CENTER),
        new FarmaColumnData("CodigoUrb.",0,JLabel.CENTER)       
    };
    public static final Object[] defaultValuesListaDireccionesConsulta = {" "," ", " "," "," "," "," "," "," "," "," "," "
                                                                            ," "," "," "," "
                                                                            ," "," "," "," "
                                                                          };

  public static final FarmaColumnData[] columnsListaCabUltimosPedidos = {
    new FarmaColumnData("Local", 0, JLabel.LEFT),
    new FarmaColumnData("Nro Pedido", 100, JLabel.LEFT),
    new FarmaColumnData("F. Envio Local", 150, JLabel.LEFT),
    new FarmaColumnData("Total S/.", 98, JLabel.RIGHT),
    new FarmaColumnData("Estado", 100, JLabel.LEFT),
    
    new FarmaColumnData("Tipo Doc.", 250, JLabel.LEFT),
    new FarmaColumnData("", 0, JLabel.LEFT),//cliente
    new FarmaColumnData("", 0, JLabel.LEFT),//direccion
    new FarmaColumnData("", 0, JLabel.LEFT),//telefono
    new FarmaColumnData("", 0, JLabel.LEFT),//codigo de local
    
    new FarmaColumnData("", 0, JLabel.LEFT),//columna de ordenamiento
    new FarmaColumnData("Conv.", 0, JLabel.LEFT),//indicador de convenio
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador de convenio,
    new FarmaColumnData("", 0, JLabel.LEFT)
  };

  public static final Object[] defaultListaCabUltimosPedidos = { " "," "," "," "," ",
                                                                 " "," "," "," "," ",
                                                                 " "," "," "," "," ",
                                                                 " "," "," "," "," ",
                                                                 " "," "};

  public static final FarmaColumnData[] columnsListaDetUltimosPedidos = {
    new FarmaColumnData("Codigo", 60, JLabel.LEFT),
    new FarmaColumnData("Descripcion", 190, JLabel.LEFT),
    new FarmaColumnData("Unidad Vta.", 90, JLabel.LEFT),
    new FarmaColumnData("Precio", 70, JLabel.RIGHT),
    new FarmaColumnData("Cant.", 60, JLabel.RIGHT),//NUEVA CANTIDAD
    new FarmaColumnData("Total S/.", 77, JLabel.RIGHT),
    new FarmaColumnData("Laboratorio", 0, JLabel.LEFT),
    new FarmaColumnData("", 0, JLabel.LEFT),//STOCK REAL
    new FarmaColumnData("", 0, JLabel.LEFT),//INDICADOR DE FRACCION
    new FarmaColumnData("", 0, JLabel.LEFT),//VALOR DE FRACCION -- rherrera 02.08.2014
    new FarmaColumnData("", 0, JLabel.LEFT),//ESTADO DEL PRODUCTO EN LOCAL -- rherrera 02.08.2014
    new FarmaColumnData("Unid Local", 0, JLabel.LEFT)
  };

	public static final Object[] defaultListaDetUltimosPedidos = {" ", " ", " ", " ", " ", " "," "," ", " "};

  public static final FarmaColumnData[] columnsListaDetPedidos = {
    new FarmaColumnData("Sel", 20, JLabel.LEFT),
    new FarmaColumnData("Codigo", 60, JLabel.LEFT),
    new FarmaColumnData("Descripcion", 190, JLabel.LEFT),
    new FarmaColumnData("Unidad Vta.", 90, JLabel.LEFT),
    new FarmaColumnData("Precio", 70, JLabel.RIGHT),
    new FarmaColumnData("Cant.", 60, JLabel.RIGHT),//NUEVA CANTIDAD
//    new FarmaColumnData("Cant. Tmp", 60, JLabel.RIGHT),//NUEVA CANTIDAD TEMPORAL
    new FarmaColumnData("Total S/.", 77, JLabel.RIGHT),
    new FarmaColumnData("Laboratorio", 0, JLabel.LEFT),
    new FarmaColumnData("", 0, JLabel.LEFT),//STOCK REAL
    new FarmaColumnData("", 0, JLabel.LEFT),//INDICADOR DE FRACCION
  };

	public static final Object[] defaultListaDetPedidos = {" ", " ", " ", " ", " ", " "," "," "};


  public static final FarmaColumnData[] columnsListaDetalleIngreso = {
    new FarmaColumnData("Cantidad", 60, JLabel.LEFT),
    new FarmaColumnData("Num. Lote", 120, JLabel.LEFT),
    new FarmaColumnData("", 0, JLabel.LEFT),//pedido
    new FarmaColumnData("", 0, JLabel.LEFT),//producto
    new FarmaColumnData("", 0, JLabel.LEFT),//indicador
    new FarmaColumnData("", 0, JLabel.LEFT)//vencimiento
  };
  public static final Object[] defaultListaDetalleIngreso = {" "," "," "," "," "};


    public static final String VNOMBREINHASHTABLEBUSCATELEFONO ="IND_CAMPO_ORDENAR_BUSCATELEFONO";
    public static final String VNOMBREINHASHTABLETIPODOCUMENTO ="IND_CAMPO_ORDENAR_TIPODOCUMENTO";
    public static final String VNOMBREINHASHTABLETIPOCLIENTE ="IND_CAMPO_ORDENAR_TIPOCLIENTE";
    public static final String VNOMBREINHASHTABLETIPOCALLE ="IND_CAMPO_ORDENAR_TIPOCALLE";

    public static final String CANTIDADMAYOR1 = "1" ;
    public static final String CANTIDADMENOR1 = "2" ;
    public static final String TIPO_BUSQUEDA_DNI = "1";
    public static final String TIPO_BUSQUEDA_APELLIDO = "2";
    public static final String MODIFICACION = "M";
    public static final String TIPO_COMP_BOLETA = "01";
    public static final String TIPO_CLIENTE_NATURAL = "N";
    
    public static final String ACCION_INSERTAR = "I";
    public static final String ACCION_INSERTAR_SOLO_CLIENTE = "ISL";
    public static final String ACCION_MODIFICAR = "M";
    public static final String CAMPO_CLIENTE = "C";
    public static final String CAMPO_DIRECCION = "D";
    public static final String CAMPO_DIRECCION_CLIENTE = "X";
    public static final String ACCION_INSERTAR_DIRECCION = "ID";
    public static final String ACCION_MODIFICAR_DIRECCION = "MD";

    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String FALTA_DATO_CLIENTE = "FDC";

    public static final String RESULTADO_GRABAR_CLIENTE_ERROR = "2";

    public static final String VALOR_FRACCION_ERROR = "1";

    public static final String CODIGO_LOCAL_VTA_INSTITUCIONAL = "998";
        public static final String LISTA_MOTORIZADO_RUTA = "7";
        
        public static final FarmaColumnData[] columnsListaStockFaltante= {
          new FarmaColumnData("Codigo", 60, JLabel.LEFT),
          new FarmaColumnData("Descripcion", 190, JLabel.LEFT),        
          new FarmaColumnData("Cant.", 60, JLabel.RIGHT),
        new FarmaColumnData("Unidad Vta.", 90, JLabel.LEFT),
        new FarmaColumnData("Stock", 60, JLabel.RIGHT),
        new FarmaColumnData("Unidad Local", 90, JLabel.LEFT),
          new FarmaColumnData("Faltante", 60, JLabel.RIGHT),
        new FarmaColumnData("Unidad Local", 90, JLabel.LEFT)
        };

              public static final Object[] defaultListaStockFaltante = {" ", " ", " ", " ", " ", " ", " ", " "};
        
    }

