package mifarma.ptoventa.administracion.mantenimiento.reference;


import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ContantsMantenimiento {
    public ContantsMantenimiento() {
    }

    public static final FarmaColumnData[] columnsListaControlHoras =
    { new FarmaColumnData("Fecha      Hora  ", 130, JLabel.CENTER),
        //new FarmaColumnData("Indicador", 165, JLabel.LEFT),
        new FarmaColumnData("Motivo", 220, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT), };

    public static final Object[] defaultValuesListaControlHoras = { " ", " " };

    public static final String RETORNO_EXITO = "1";
    public static final String RETORNO_ERROR_1 = "2";
    public static final String RETORNO_ERROR_2 = "3";
}
