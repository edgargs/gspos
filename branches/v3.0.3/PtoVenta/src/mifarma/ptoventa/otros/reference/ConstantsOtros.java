package mifarma.ptoventa.otros.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.02.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class ConstantsOtros {
    public ConstantsOtros() {
    }

    /**
     * Se declaran las variables para el listado de MEDIDAS DE PRESION
     * @author JCALLO
     * @since  09.09.2008
     */
    public static final FarmaColumnData columnsListaMedPresion[] =
    { new FarmaColumnData("Nro Reg.", 80, JLabel.RIGHT), new FarmaColumnData("DNI", 70, JLabel.CENTER),
      new FarmaColumnData("Nomb. Cliente", 183, JLabel.LEFT), new FarmaColumnData("Max. Sistolica", 85, JLabel.RIGHT),
      new FarmaColumnData("Min. Diastolica", 85, JLabel.RIGHT),
      new FarmaColumnData("Fecha Reg.", 117, JLabel.CENTER) };

    public static final Object[] defaultListaListaMedPresion = { " ", " ", " ", " ", " ", " " };

    /**
     * Se declaran las variables para el historico de MEDIDAS DE PRESION
     * @author JCALLO
     * @since  09.09.2008
     */
    public static final FarmaColumnData columnsListaHistMedPresion[] =
    { new FarmaColumnData("Cod. Local", 85, JLabel.CENTER), new FarmaColumnData("Max. Sistolica", 85, JLabel.RIGHT),
      new FarmaColumnData("Min. Diastolica", 85, JLabel.RIGHT),
      new FarmaColumnData("Fecha Reg.", 117, JLabel.CENTER) };

    public static final Object[] defaultListaListaHistMedPresion = { " ", " ", " ", " " };

    /**
     * datos manejados en el tema registro de datos de cliente   *
     * @autor jcallo
     *
     * **/
    public static final FarmaColumnData[] columnsListaDatosClienteMedPresion =
    { new FarmaColumnData("Desc.", 200, JLabel.LEFT), new FarmaColumnData("Dato", 300, JLabel.RIGHT),
        /*new FarmaColumnData("Codigo", 0, JLabel.CENTER),
    new FarmaColumnData("TI_DATO", 0, JLabel.CENTER),
    new FarmaColumnData("IN_SOLO_LECTURA", 0, JLabel.CENTER),
    new FarmaColumnData("IN_OBLIGATORIO", 0, JLabel.CENTER),
    new FarmaColumnData("IND_BUSQUEDA", 0, JLabel.CENTER) */ };

    public static final Object[] defaultValuesListaDatosClienteMedPresion = { "", "" /*, " ", " ", " ", " ", " "*/ };

    public static final Boolean[] editableListaDatosClienteMedPresion =
    { new Boolean(false), new Boolean(true), new Boolean(false), new Boolean(false), new Boolean(false),
      new Boolean(false), new Boolean(false) };


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


    /** PARA LA IMPRESION DEL HISTORIO DE REGISTRO DE PRESIONES **/

    /*public static final String   IMP_C_SIZE_CONSEJO = "18";
  public static final String   IMP_C_SIZE_MSG_FINAL = "10";
  public static final String   IMP_RUTA_IMG_CABECERA ="/MiFarma/mifarma.jpg";

  public static final String   IMP_INI_HTML_IMPRESION = new StringBuffer("<html> ")
  							.append("<head>")
                            .append("<style type='text/css'>")
                            .append(".titulo {font-size: 14;font-weight: bold;font-family: Arial, Helvetica, sans-serif;} ")
                            .append(".cliente {font-size: 12;font-family: Arial, Helvetica, sans-serif;} ")
                            .append(".histcab {font-size: 8;font-family: Arial, Helvetica, sans-serif; font-weight: bold;} ")
                            .append(".historico {font-size: 8;font-family: Arial, Helvetica, sans-serif; } ")
                            .append(".msgfinal {font-size: 14;font-weight: bold;font-family: Arial, Helvetica, sans-serif;} ")
                            .append("</style>")
                            .append("</head>")
                            .append("<body>")
                            .append("<table width='200' border='0'>")
                            .append("<tr>")
                            .append("<td>&nbsp;&nbsp;</td>")
                            .append("<td>")
                            .append("<table width='300' height='841' border='1'>").toString();

  public static final String IMP_C_FILA_VACIA = new StringBuffer("<tr> ")
							.append("<td height='2' colspan='3'></td>")
							.append("</tr>").toString();

  public static final String IMP_C_FIN_MSG = new StringBuffer("</table></td>")
  							.append("</tr>")
  							.append("</table>")
  							.append("<p><br>")
  							.append("<br>")
							.append("</p>")
							.append("</body>")
							.append("</html> ").toString();*/

    /** fin de todas las constantes usados para generar el html de impresion**/


    public static String NOM_HASTABLE_CMB_TIP_DOC = "CMB_TIP_DOC";
    public static final int TIPO_DIALOG_NUEVO_COBRO = 1;
    public static final int TIPO_DIALOG_PAGO_TERCEROS = 2;
    public static final int TIPO_DIALOG_VENTA_CMR = 3;

}
