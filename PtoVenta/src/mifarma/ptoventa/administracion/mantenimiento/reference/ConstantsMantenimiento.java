package mifarma.ptoventa.administracion.mantenimiento.reference;


import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsMantenimiento {
    public ConstantsMantenimiento() {
    }

    public static final FarmaColumnData[] columnsListaControlHoras =
    { new FarmaColumnData("Fecha      Hora  ", 130, JLabel.CENTER),
        //new FarmaColumnData("Indicador", 165, JLabel.LEFT),
        new FarmaColumnData("Motivo", 220, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT), };

    public static final Object[] defaultValuesListaControlHoras = { " ", " " };

    public static final String RETORNO_EXITO = "1";
    public static final String RETORNO_ERROR_1 = "2";
    public static final String RETORNO_ERROR_2 = "3";
    
    //ERIOS 18.07.2016 Comision Arcangel
    public static final FarmaColumnData[] columnsListaPresupuestoVentas =
    { new FarmaColumnData("Cod. Prog.", 70, JLabel.CENTER),
      new FarmaColumnData("Periodo", 150, JLabel.LEFT),
      new FarmaColumnData("Volumen", 75, JLabel.RIGHT), 
      new FarmaColumnData("LLEE", 75, JLabel.RIGHT),
      new FarmaColumnData("Estado", 100, JLabel.CENTER)};

    public static final Object[] defaultValuesListaPresupuestoVentas = { " ", " ", " ", " ", " " };  
    
    public static final FarmaColumnData[] columnsListaPresupuestoVentasVendedor =
    { new FarmaColumnData("Sec. Usu.", 70, JLabel.CENTER),
      new FarmaColumnData("Nombre", 250, JLabel.LEFT),
      new FarmaColumnData("Volumen", 75, JLabel.RIGHT), 
      new FarmaColumnData("LLEE", 75, JLabel.RIGHT)};

    public static final Object[] defaultValuesListaPresupuestoVentasVendedor = { " ", " ", " ", " " };  
}
