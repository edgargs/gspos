package mifarma.ptoventa.stocknegativo.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsStockNegativo {
    public ConstantsStockNegativo() {
        super();
    }

    public static final FarmaColumnData[] columnsListaStockNeg =
    { new FarmaColumnData("Local", 60, JLabel.CENTER), new FarmaColumnData("Solicitud", 60, JLabel.CENTER),
      new FarmaColumnData("Estado", 80, JLabel.CENTER), new FarmaColumnData("Items", 60, JLabel.CENTER),
      new FarmaColumnData("Solicitante", 100, JLabel.LEFT), new FarmaColumnData("QF. Aprobador", 100, JLabel.LEFT),
      new FarmaColumnData("Fecha Solict.", 100, JLabel.CENTER) };

    public static final Object[] defaultListaStockNeg = { " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaDetStockNeg =
    { new FarmaColumnData("Cod Prod", 65, JLabel.CENTER), new FarmaColumnData("Desc. Prod", 180, JLabel.LEFT),
      new FarmaColumnData("Cant. Prod", 60, JLabel.CENTER), new FarmaColumnData("Unidad", 80, JLabel.CENTER),
      new FarmaColumnData("Stock", 60, JLabel.CENTER), new

        FarmaColumnData("Prod Cruzado", 65, JLabel.CENTER), new FarmaColumnData("Desc.", 180, JLabel.LEFT) };

    public static final Object[] defaultListaDetStockNeg = { " ", " ", " ", " ", " ", " ", " " };

    //Lista de Productos
    public static final FarmaColumnData columnsListaProductos[] =
    { new FarmaColumnData("Sel", 0, JLabel.CENTER), new FarmaColumnData("Código", 50, JLabel.LEFT),
      new FarmaColumnData("Descripción", 200, JLabel.LEFT), new FarmaColumnData("Unidad", 100, JLabel.LEFT),
      new FarmaColumnData("Laboratorio", 150, JLabel.LEFT), new FarmaColumnData("Stock", 45, JLabel.RIGHT),
      new FarmaColumnData("Precio", 65, JLabel.RIGHT), new FarmaColumnData("Zan", 40, JLabel.RIGHT),
      new FarmaColumnData("", 0, JLabel.LEFT), //indicador producto congelado
        new FarmaColumnData("", 0, JLabel.RIGHT), //valor de fraccion de local
        new FarmaColumnData("", 0, JLabel.RIGHT), //valor precio lista
        new FarmaColumnData("", 0, JLabel.RIGHT), //valor igv producto
        new FarmaColumnData("", 0, JLabel.LEFT), //indicador de producto farma
        new FarmaColumnData("", 0, JLabel.RIGHT), //indicador prod virtual
        new FarmaColumnData("", 0, JLabel.LEFT), //tipo de prod virtual
        new FarmaColumnData("", 0, JLabel.LEFT), //indicador prod refrigerado
        new FarmaColumnData("", 0, JLabel.LEFT), //indicador tipo producto
        new FarmaColumnData("", 0, JLabel.LEFT), //indicador  producto promocion
        new FarmaColumnData("ordLista", 0, JLabel.LEFT), new FarmaColumnData("indProdEncarte", 0, JLabel.LEFT),
        new FarmaColumnData("indOrigen", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaProductos =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
}
