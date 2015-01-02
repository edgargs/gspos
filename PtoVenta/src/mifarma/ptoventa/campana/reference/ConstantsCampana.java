package mifarma.ptoventa.campana.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsCampana {
    public ConstantsCampana() {
    }

    public static final FarmaColumnData[] columnsListaDatosCampana =
    { new FarmaColumnData("Desc.", 200, JLabel.LEFT), new FarmaColumnData("Dato", 300, JLabel.RIGHT),
      new FarmaColumnData("Codigo", 0, JLabel.CENTER), new FarmaColumnData("TI_DATO", 0, JLabel.CENTER),
      new FarmaColumnData("IN_SOLO_LECTURA", 0, JLabel.CENTER),
      new FarmaColumnData("IN_OBLIGATORIO", 0, JLabel.CENTER), new FarmaColumnData("IND_BUSQUEDA", 0, JLabel.CENTER) };

    public static final Object[] defaultValuesListaDatosCampana = { " ", " ", " ", " ", " ", " ", " " };

    public static final Boolean[] editableListaDatosCampana =
    { new Boolean(false), new Boolean(true), new Boolean(false), new Boolean(false), new Boolean(false),
      new Boolean(false), new Boolean(false) };

    public static final String CODIGO_CLIENTE = "001";
    public static final String DNI_CLIENTE = "002";
    public static final String NOMBRE_CLIENTE = "003";
    public static final String APEPAT_CLIENTE = "004";
    public static final String APEMAT_CLIENTE = "005";
    public static final String TELEFONO_CLIENTE = "006";
    public static final String NUM_CMP = "007";
    public static final String NOMBRE_MEDICO = "008";
    public static final String SEXO_CLIENTE = "009";
    public static final String FECHA_NAC_CLIENTE = "010";

}
