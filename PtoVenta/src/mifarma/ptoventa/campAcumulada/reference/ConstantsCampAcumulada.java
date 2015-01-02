package mifarma.ptoventa.campAcumulada.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsCampAcumulada {
    public ConstantsCampAcumulada() {
    }

    /**
     * Se declaran las variables para el listado de CAMPANAIAS ACUMULABLES
     * @author JCALLO
     * @since  15.12.2008
     */
    public static final FarmaColumnData columnsListaCampAcumuDisp[] =
    { new FarmaColumnData(" ", 25, JLabel.CENTER), new FarmaColumnData("Campaña", 425, JLabel.LEFT),
      new FarmaColumnData("Fec. Ini", 68, JLabel.CENTER), new FarmaColumnData("Fec. Fin", 68, JLabel.CENTER) };

    public static final Object[] defaultListaListaCampAcumuDisp = { " ", " ", " " };

    public static final FarmaColumnData columnsListaCampAcumuIns[] =
    { new FarmaColumnData("Campaña", 450, JLabel.LEFT), new FarmaColumnData("Fec. Ini", 68, JLabel.CENTER),
      new FarmaColumnData("Fec. Fin", 68, JLabel.CENTER) };

    public static final Object[] defaultListaListaCampAcumuIns = { " ", " ", " " };

    public static final String CODIGO_CLIENTE = "001";
    public static final String DNI_CLIENTE = "002";
    public static final String NOMBRE_CLIENTE = "003";
    public static final String APEPAT_CLIENTE = "004";
    public static final String APEMAT_CLIENTE = "005";
    public static final String TELEFONO_CLIENTE = "006";
    public static final String DIREC_CLIENTE = "007";
    public static final String SEXO_CLIENTE = "008";
    public static final String FECHA_NAC_CLIENTE = "009";
    public static final String EMAIL_CLIENTE = "010";
    public static final String CELULAR_CLIENTE = "011";

    /**
     * datos manejados en el tema registro de datos de cliente   *
     * @autor jcallo
     *
     * **/
    public static final FarmaColumnData[] columnsListaDatosClienteCampAcumulada =
    { new FarmaColumnData("Desc.", 200, JLabel.LEFT), new FarmaColumnData("Dato", 300, JLabel.RIGHT),
        /*new FarmaColumnData("Codigo", 0, JLabel.CENTER),
      new FarmaColumnData("TI_DATO", 0, JLabel.CENTER),
      new FarmaColumnData("IN_SOLO_LECTURA", 0, JLabel.CENTER),
      new FarmaColumnData("IN_OBLIGATORIO", 0, JLabel.CENTER),
      new FarmaColumnData("IND_BUSQUEDA", 0, JLabel.CENTER) */ };

    public static final Object[] defaultValuesListaDatosClienteCampAcumulada =
    { "", "" /*, " ", " ", " ", " ", " "*/ };

    public static final Boolean[] editableListaDatosClienteCampAcumulada = { new Boolean(false), new Boolean(true) /*, new Boolean(false),
      new Boolean(false), new Boolean(false), new Boolean(false),
      new Boolean(false) */ };

}
