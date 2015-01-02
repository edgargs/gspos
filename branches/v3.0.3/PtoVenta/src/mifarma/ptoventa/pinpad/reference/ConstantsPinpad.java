package mifarma.ptoventa.pinpad.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsPinpad {
    public ConstantsPinpad() {
        super();
    }

    public static final FarmaColumnData[] columnsListaPedidoPinpad =
    { new FarmaColumnData("Pedido", 80, JLabel.LEFT), new FarmaColumnData("Fecha", 70, JLabel.LEFT),
      new FarmaColumnData("Monto Pedido", 80, JLabel.RIGHT), new FarmaColumnData("Monto Pinpad", 80, JLabel.RIGHT),
      new FarmaColumnData("Estado", 85, JLabel.LEFT), new FarmaColumnData("Tipo Comprob.", 120, JLabel.LEFT),
      new FarmaColumnData("Usuario", 80, JLabel.LEFT), new FarmaColumnData("Operador", 60, JLabel.LEFT),
      new FarmaColumnData("Cod.Proc.Pinpad", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaPedidoPinpad = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
}
