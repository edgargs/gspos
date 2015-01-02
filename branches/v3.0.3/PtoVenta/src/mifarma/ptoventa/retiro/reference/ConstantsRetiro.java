package mifarma.ptoventa.retiro.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsRetiro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      04.12.2009   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class ConstantsRetiro {
    public ConstantsRetiro() {
    }

    public static final FarmaColumnData[] columnsListaRetiro =
    { new FarmaColumnData("Tipo Cierre", 90, JLabel.CENTER), new FarmaColumnData("Local", 150, JLabel.LEFT),
      new FarmaColumnData("Fecha Cierre", 90, JLabel.CENTER), new FarmaColumnData("Turno", 50, JLabel.CENTER),
      new FarmaColumnData("Caja", 40, JLabel.CENTER), new FarmaColumnData("Email Contador", 200, JLabel.LEFT),
      new FarmaColumnData("Fecha Proceso", 90, JLabel.CENTER),
      new FarmaColumnData("Fec envio mail", 90, JLabel.CENTER), new FarmaColumnData("Indicador", 110, JLabel.CENTER),
      new FarmaColumnData("Fec Creacion", 90, JLabel.CENTER),
      new FarmaColumnData("Usu Creacion", 110, JLabel.CENTER) };
    public static final Object[] defaultListaRetiro = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };


    public static final FarmaColumnData[] columnsListaLocales =
    { new FarmaColumnData("Cod. Local", 70, JLabel.CENTER), new FarmaColumnData("Nom. Local", 100, JLabel.LEFT) };
    public static final Object[] defaultListaLocales = { " ", " " };


    public static final FarmaColumnData[] columnsRpteRetiros =
    { new FarmaColumnData("Tipo Cierre", 90, JLabel.CENTER), new FarmaColumnData("Local", 150, JLabel.LEFT),
      new FarmaColumnData("Fecha Cierre", 90, JLabel.CENTER), new FarmaColumnData("Turno", 50, JLabel.CENTER),
      new FarmaColumnData("Caja", 40, JLabel.CENTER), new FarmaColumnData("Email Contador", 200, JLabel.LEFT),
      new FarmaColumnData("Fecha Proceso", 90, JLabel.CENTER),
      new FarmaColumnData("Fec envio mail", 90, JLabel.CENTER), new FarmaColumnData("Indicador", 110, JLabel.CENTER),
      new FarmaColumnData("Fec Creacion", 90, JLabel.CENTER),
      new FarmaColumnData("Usu Creacion", 110, JLabel.CENTER) };
    public static final Object[] defaultRpteRetiros = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };


    public static final String[] FILTROS_RETIROS_IND_VALUE = { "S", "N", " " };
    public static final String[] FILTROS_RETIROS_IND_DESC = { "PROCESADO", "NO PROCESADO", "TODOS" };
    public static final String HASHTABLE_RETIROS_PROC = "IND_PROCESADO";

}
