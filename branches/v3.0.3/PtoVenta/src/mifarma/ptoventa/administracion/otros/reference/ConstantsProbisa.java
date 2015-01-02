package mifarma.ptoventa.administracion.otros.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsProbisa {
    public ConstantsProbisa() {
    }

    public static final FarmaColumnData[] columnsListaControlesProbisa =
    { new FarmaColumnData("Fecha", 70, JLabel.CENTER), new FarmaColumnData("Usuario", 150, JLabel.LEFT),
      new FarmaColumnData("Cant. Tintes", 80, JLabel.RIGHT), new FarmaColumnData("Cant. Recetas", 85, JLabel.RIGHT),
      new FarmaColumnData("Cant. Atenciones", 110, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaControlesProbisa = { " ", " ", " ", " ", " " };

}
