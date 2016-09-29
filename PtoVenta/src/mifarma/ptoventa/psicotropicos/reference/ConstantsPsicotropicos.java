package mifarma.ptoventa.psicotropicos.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsPsicotropicos {
    public ConstantsPsicotropicos() {
        super();
    }

    /**
     * @since 03.12.2015 ERIOS Se agrega el documento de identidad
     */
    public static final FarmaColumnData[] columnsListaPsicotropicos =
    { new FarmaColumnData("Fecha", 70, JLabel.LEFT), new FarmaColumnData("Codigo", 50, JLabel.LEFT),
      new FarmaColumnData("Producto", 150, JLabel.LEFT), new FarmaColumnData("Presentacion", 120, JLabel.LEFT),
      new FarmaColumnData("Descripción", 200, JLabel.LEFT), new FarmaColumnData("Tipo Doc.", 80, JLabel.LEFT),
      new FarmaColumnData("Num. Doc.", 100, JLabel.LEFT), new FarmaColumnData("STK.Ant.", 60, JLabel.RIGHT),
      new FarmaColumnData("Movim.", 40, JLabel.RIGHT), new FarmaColumnData("STK.Final", 60, JLabel.RIGHT),
      new FarmaColumnData("Fracc.", 40, JLabel.CENTER), 
      new FarmaColumnData("Doc. Id.", 80, JLabel.LEFT),
      new FarmaColumnData("Paciente", 200, JLabel.LEFT),      
      //[Desarrollo5] 25.09.2015
      new FarmaColumnData("Cod. Medico", 80, JLabel.CENTER),
      new FarmaColumnData("Medico", 200, JLabel.LEFT), new FarmaColumnData("Usuario", 100, JLabel.CENTER),
      new FarmaColumnData("Glosa", 120, JLabel.LEFT), new FarmaColumnData("Secuencia Kardex", 0, JLabel.CENTER) };

    public static final Object[] defaultListaPsicotropicos =
    { " ", " ", " ", " ", " ", " ", " ", " ", "", "", " ", " ", " ", " " };
    
    public static final Object[] columnPrintablePsicotropicos = {new int[]{11,7,30,15,20,8,13,5,4,5,0},
                                                                 new int[]{10,40,8,35,0,0,0}};
}
