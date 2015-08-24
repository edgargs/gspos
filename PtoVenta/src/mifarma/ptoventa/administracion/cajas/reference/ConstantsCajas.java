package mifarma.ptoventa.administracion.cajas.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsCajas {
    public ConstantsCajas() {
    }

    public static final FarmaColumnData[] columnsListaCajas =
    { new FarmaColumnData("Cod.", 40, JLabel.CENTER), new FarmaColumnData("Descripción", 170, JLabel.LEFT),
      new FarmaColumnData("Estado", 60, JLabel.CENTER), new FarmaColumnData("Usuario Asignado", 220, JLabel.LEFT),
      new FarmaColumnData("secUsu", 0, JLabel.CENTER), };

    public static final Object[] defaultValuesListaCajas = { " ", " ", " ", " ", " " };

    /**
     * Cajas aperturadas
     * @author ERIOS
     * @since 2.2.8
     */
    public static final FarmaColumnData[] columnsListaCajasAperturadas =
    { new FarmaColumnData("Cajero", 120, JLabel.LEFT), new FarmaColumnData("Caja", 50, JLabel.CENTER),
      new FarmaColumnData("Turno", 50, JLabel.CENTER), new FarmaColumnData("Cierre", 60, JLabel.CENTER),
      new FarmaColumnData("VB Caj", 60, JLabel.CENTER), new FarmaColumnData("VB QF", 60, JLabel.CENTER) };

    public static final Object[] defaultValuesListaCajasAperturadas = { " ", " ", " ", " ", " ", " " };
    
    public static final FarmaColumnData[] columnsListaValidaAperturaCaja =
    { new FarmaColumnData("Fecha", 120, JLabel.CENTER), 
      new FarmaColumnData("Turno", 50, JLabel.CENTER),
    new FarmaColumnData("Obs", 160, JLabel.CENTER)
    };

    public static final Object[] defaultValuesValidaAperturaCaja = { " ", " " ,""};
    
}
