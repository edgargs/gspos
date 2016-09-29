package mifarma.ptoventa.administracion.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsAdministracion {
    public ConstantsAdministracion() {
    }


    public static final FarmaColumnData columnsListaMovimientos[] =
    { new FarmaColumnData("Codigo", 60, JLabel.CENTER), new FarmaColumnData("Forma Pago", 120, JLabel.LEFT),
      new FarmaColumnData("Moneda", 45, JLabel.LEFT), new FarmaColumnData("Monto", 70, JLabel.RIGHT),
      new FarmaColumnData("Total S/.", 70, JLabel.RIGHT), new FarmaColumnData("TipMon", 0, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaMovimientos = { " ", " ", " ", " ", " ", " " };
}
