package mifarma.ptoventa.recetario.reference;


import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsRecetario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author <br>
 * @version 1.0<br>
 *
 */

public class ConstantsRecetario {
    public ConstantsRecetario() {
    }

    public enum Estado {
        PENDIENTE("A"),
        GUIA("G"),
        COBRADO("C"),
        ENVIADO("E"),
        ANULADO("N");
        private String valor;

        Estado(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return this.valor;
        }
    }

    public static final FarmaColumnData[] columnsListaProductos =
    { new FarmaColumnData("Sel", 30, JLabel.LEFT), new FarmaColumnData("Codigo", 60, JLabel.CENTER),
      new FarmaColumnData("Descripcion", 250, JLabel.LEFT), new FarmaColumnData("Unidad Orig.", 110, JLabel.LEFT),
      new FarmaColumnData("Precio (S/.)", 80, JLabel.RIGHT), new FarmaColumnData("Cant.", 80, JLabel.CENTER),
      new FarmaColumnData("Cant. Ingresada", 0, JLabel.CENTER), new FarmaColumnData("Porc. (%)", 0, JLabel.CENTER),
      new FarmaColumnData("Cod. Unidad. Venta", 0, JLabel.CENTER), //antes 0
        new FarmaColumnData("Desc. Unidad. Venta", 0, JLabel.CENTER) //antes 0
        } ;

    public static final Object[] defaultListaProductos = { " ", " ", " ", " ", " ", " ", " ", " ", "", "" };

    public static final FarmaColumnData[] columnsListaResumenPedido = {
        /*new FarmaColumnData("Codigo",50,JLabel.LEFT),
      new FarmaColumnData("Descripcion",200,JLabel.LEFT),
      new FarmaColumnData("Unidad",80,JLabel.LEFT),
      new FarmaColumnData("Laboratorio",150,JLabel.LEFT),
      new FarmaColumnData("Cantidad",60,JLabel.RIGHT),
      new FarmaColumnData("Pre. U.",50,JLabel.RIGHT),
      new FarmaColumnData("Total",50,JLabel.RIGHT),
      new FarmaColumnData("Lote",0,JLabel.RIGHT),
      new FarmaColumnData("Fec Vec",0,JLabel.RIGHT),
      new FarmaColumnData("Val Frac",0,JLabel.RIGHT)*/
        } ;

    public static final Object[] defaultListaResumenPedido = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaMedicos =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Nombre", 200, JLabel.LEFT),
      new FarmaColumnData("Apellido Pat", 80, JLabel.LEFT), new FarmaColumnData("Apellido Mat", 150, JLabel.LEFT),
      new FarmaColumnData("Cantidad", 60, JLabel.RIGHT) };

    public static final Object[] defaultListaMedicos = { " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaLocales =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Descripcion", 80, JLabel.LEFT),
      new FarmaColumnData("Dirección", 200, JLabel.LEFT), };

    public static final Object[] defaultListaLocales = { " ", " ", " " };
    //
    public static final FarmaColumnData[] columnsListaClientes =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Nombre", 80, JLabel.LEFT),
      new FarmaColumnData("Apellido Pat", 80, JLabel.LEFT), new FarmaColumnData("Apellido Mat", 80, JLabel.LEFT),
      new FarmaColumnData("DNI", 60, JLabel.RIGHT) };

    public static final Object[] defaultListaClientes = { " ", " ", " ", " ", " ", };

    public static final FarmaColumnData[] columnsListaRecetarios =
    { new FarmaColumnData("Orden Prep.", 90, JLabel.LEFT), new FarmaColumnData("Fecha", 70, JLabel.LEFT),
      new FarmaColumnData("Num. Recet.", 90, JLabel.LEFT), new FarmaColumnData("Paciente", 200, JLabel.LEFT),
      new FarmaColumnData("Num. Pedido", 80, JLabel.RIGHT), new FarmaColumnData("Estado", 80, JLabel.RIGHT) };

    public static final Object[] defaultListaRecetarios = { " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaDetalleRecetarios =
    { new FarmaColumnData("Insumo", 200, JLabel.LEFT), new FarmaColumnData("Cant.", 70, JLabel.LEFT),
      new FarmaColumnData("Unidad", 70, JLabel.LEFT), new FarmaColumnData("Precio Unit.", 80, JLabel.LEFT),
      new FarmaColumnData("Precio Total", 80, JLabel.RIGHT) };

    public static final Object[] defaultListaDetalleRecetarios = { " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaRecetariosMagistral =
    { new FarmaColumnData("Codigo", 60, JLabel.LEFT), new FarmaColumnData("Descripción", 250, JLabel.LEFT),
      new FarmaColumnData("Unidad Venta", 160, JLabel.LEFT),
      new FarmaColumnData("Precio Unit.(S/.)", 100, JLabel.RIGHT), //antes 0
        new FarmaColumnData("Cantidad", 80, JLabel.CENTER), new FarmaColumnData("Porc.(%)", 0, JLabel.CENTER),
        new FarmaColumnData("Cant. Venta", 0, JLabel.RIGHT), //antes 0
        new FarmaColumnData("Sub-Total(S/.)", 100, JLabel.RIGHT),
        new FarmaColumnData("Cod.Unidad Venta", 0, JLabel.RIGHT), //Codigo Unidad Venta    //antes 0
        new FarmaColumnData("Desc.Unidad Venta", 0, JLabel.RIGHT) //Codigo Unidad Venta    //antes 0
        } ;

    public static final Object[] defaultListaRecetariosMagistral =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaGuiasRM =
    { new FarmaColumnData("Num. Guia", 80, JLabel.CENTER), new FarmaColumnData("Fecha Oper.", 70, JLabel.CENTER),
      new FarmaColumnData("Observaciones", 280, JLabel.LEFT), new FarmaColumnData("Cliente", 150, JLabel.LEFT),
      new FarmaColumnData("Cant.", 50, JLabel.CENTER), new FarmaColumnData("Num_Orden_Preparado", 0, JLabel.CENTER) };

    public static final Object[] defaultListaGuiasRM = { " ", " ", " ", " ", " ", " " };

    public static final String COD_IGV_VIGENTE = "01";

}
