package mifarma.ptoventa.fidelizacion.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsFidelizacion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DVELIZ      26.09.2008   Creación<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 *
 */

public class ConstantsFidelizacion {
    public ConstantsFidelizacion() {
    }

    public static final FarmaColumnData[] columnsListaDatosFidelizacion =
    { new FarmaColumnData("Desc.", 200, JLabel.LEFT), new FarmaColumnData("Dato", 300, JLabel.RIGHT),
      new FarmaColumnData("Codigo", 0, JLabel.CENTER), new FarmaColumnData("TI_DATO", 0, JLabel.CENTER),
      new FarmaColumnData("IN_SOLO_LECTURA", 0, JLabel.CENTER),
      new FarmaColumnData("IN_OBLIGATORIO", 0, JLabel.CENTER), new FarmaColumnData("IND_BUSQUEDA", 0, JLabel.CENTER) };

    public static final Object[] defaultValuesListaDatosFidelizacion = { " ", " ", " ", " ", " ", " ", " " };

    public static final Boolean[] editableListaDatosFidelizacion =
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

    public static final FarmaColumnData[] columnsDataTarjeta = { new

        FarmaColumnData("Nro.Tarjeta", 210, JLabel.LEFT) };

    public static final Object[] defaultsDtaTarjeta = { "" };

    /**
     * Constante Prefijo para generacion de nueva tarjeta de fidelizacion
     * @author  dveliz
     * @since   13.02.2009
     */
    public static final String PREFIJO_TARJETA_FIDELIZACION = "999";


    public static String NOM_HASTABLE_CMB_TIP_DOC = "CMB_TIP_DOC";

    public static String COD_FP_EFECTIVO_TOTAL = "E0000";
    public static String COD_FP_TARJETA_TOTAL = "T0000";

}
