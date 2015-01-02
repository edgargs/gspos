package mifarma.ptoventa.psicotropicos.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsPsicotropicos {
    public ConstantsPsicotropicos() {
        super();
    }

    public static final FarmaColumnData[] columnsListaPsicotropicos =
    { new FarmaColumnData("Fecha", 70, JLabel.LEFT), new FarmaColumnData("Codigo", 50, JLabel.LEFT),
      new FarmaColumnData("Producto", 150, JLabel.LEFT), new FarmaColumnData("Presentacion", 120, JLabel.LEFT),
      new FarmaColumnData("Descripción", 200, JLabel.LEFT), new FarmaColumnData("Tipo Doc.", 80, JLabel.LEFT),
      new FarmaColumnData("Num. Doc.", 100, JLabel.LEFT), new FarmaColumnData("STK. Ant.", 60, JLabel.RIGHT),
      new FarmaColumnData("Movim.", 40, JLabel.RIGHT), new FarmaColumnData("STK Act.", 60, JLabel.RIGHT),
      new FarmaColumnData("Fracc.", 40, JLabel.CENTER), new FarmaColumnData("Cliente", 200, JLabel.LEFT),
      new FarmaColumnData("Medico", 200, JLabel.LEFT), new FarmaColumnData("Usuario", 100, JLabel.CENTER),
      new FarmaColumnData("Glosa", 80, JLabel.LEFT), new FarmaColumnData("Secuencia Kardex", 0, JLabel.CENTER) };

    public static final Object[] defaultListaPsicotropicos =
    { " ", " ", " ", " ", " ", " ", " ", " ", "", "", " ", " ", " " };
}
