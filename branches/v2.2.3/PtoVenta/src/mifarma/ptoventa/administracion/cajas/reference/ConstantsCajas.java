package mifarma.ptoventa.administracion.cajas.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;

public class ConstantsCajas {
	public ConstantsCajas() {
	}

	public static final FarmaColumnData[] columnsListaCajas = {
			new FarmaColumnData("Cod.", 40, JLabel.CENTER),
			new FarmaColumnData("Descripción", 170, JLabel.LEFT),
			new FarmaColumnData("Estado", 60, JLabel.CENTER),
			new FarmaColumnData("Usuario Asignado", 220, JLabel.LEFT),
			new FarmaColumnData("secUsu", 0, JLabel.CENTER), };

	public static final Object[] defaultValuesListaCajas = { " ", " ", " ",
			" ", " " };

}