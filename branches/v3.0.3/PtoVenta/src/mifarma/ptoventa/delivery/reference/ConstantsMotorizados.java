package mifarma.ptoventa.delivery.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsMotorizados {
    public ConstantsMotorizados() {
    }
    public static final String ACCION_INSERTAR = "I";
    public static final String ACCION_MODIFICAR = "M";

    public static final FarmaColumnData columnsListaMotorizados[] =
    { new FarmaColumnData("Código", 70, JLabel.CENTER), new FarmaColumnData("Ape. Paterno", 110, JLabel.LEFT),
      new FarmaColumnData("Ape. Materno", 110, JLabel.LEFT), new FarmaColumnData("Nombre", 140, JLabel.LEFT),
      new FarmaColumnData("DNI", 80, JLabel.LEFT), new FarmaColumnData("Estado", 85, JLabel.CENTER),
        /**
     * Para el Alias y Cod Local de Referencia
     * @author : dubilluz
     * @since  : 09.08.2007
     */
        new FarmaColumnData("Alias", 0, JLabel.CENTER), new FarmaColumnData("CodLocal", 0, JLabel.CENTER),
        new FarmaColumnData("DescLocal", 0, JLabel.CENTER) };

    public static final Object[] defaultValuesListaMotorizados = { " ", " ", " ", " ", " ", " ", " ", " ", " " };

}
