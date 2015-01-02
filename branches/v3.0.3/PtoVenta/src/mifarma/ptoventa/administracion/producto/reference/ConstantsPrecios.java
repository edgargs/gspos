package mifarma.ptoventa.administracion.producto.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaIPSImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JSANTIVANEZ 05.01.2011 Modificación<br>
 * <br>
 * @version 1.0<br>
 *
 */
public class ConstantsPrecios {
    public ConstantsPrecios() {
    }

    public static final FarmaColumnData[] columnsListaPrecios =
    { new FarmaColumnData("Cod Local", 50, JLabel.CENTER), new FarmaColumnData("Cod Producto", 60, JLabel.CENTER),
      new FarmaColumnData("Descripción", 120, JLabel.CENTER), new FarmaColumnData("Precio", 70, JLabel.LEFT),
      new FarmaColumnData("Ind. Prod. Fracc.", 50, JLabel.LEFT), new FarmaColumnData("Valor Fracc.", 50, JLabel.LEFT),
      new FarmaColumnData("Fecha cambio.", 130, JLabel.LEFT), new FarmaColumnData("Ind. impresión", 50, JLabel.LEFT),
      new FarmaColumnData("Fecha impresión.", 130, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT), };

    public static final FarmaColumnData[] columnsProductosImpresion =
    { new FarmaColumnData("Código", 55, JLabel.CENTER), new FarmaColumnData("Descripción", 300, JLabel.LEFT),
      new FarmaColumnData("Unidad", 70, JLabel.LEFT), new FarmaColumnData("Precio", 70, JLabel.RIGHT),
      new FarmaColumnData("", 0, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaPrecios =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    public static final Object[] defaultValuesProductosImpresion = { " ", " ", " ", " ", " ", " " };

}
