package mifarma.ptoventa.psicotropicos.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;

public class ConstantsPsicotropicos
{
    public ConstantsPsicotropicos()
    {   super();
    }
    
    public static final FarmaColumnData[] columnsListaPsicotropicos =
    {
        new FarmaColumnData("Fecha",80,JLabel.LEFT),
        new FarmaColumnData("Descripción",200,JLabel.CENTER),
        new FarmaColumnData("Tipo Doc.",80,JLabel.LEFT),
        new FarmaColumnData("Num. Doc.",80,JLabel.LEFT),
        new FarmaColumnData("STK. Ant.", 60, JLabel.RIGHT ),
        new FarmaColumnData("Movim.", 40, JLabel.CENTER ),
        new FarmaColumnData("STK Act.", 60, JLabel.CENTER ),
        new FarmaColumnData("Fracc.", 40, JLabel.CENTER ),
        new FarmaColumnData("Cliente", 80, JLabel.CENTER ),
        new FarmaColumnData("Medico", 80, JLabel.CENTER ),
        new FarmaColumnData("Usuario", 80, JLabel.CENTER ),
        new FarmaColumnData("Glosa", 80, JLabel.CENTER ),
        new FarmaColumnData("Secuencia Kardex", 0, JLabel.CENTER )
    };
    
    public static final Object[] defaultListaPsicotropicos = { " ", " ", " ", " ", " ", " ", " ", " ", "", "", " ", " ", " "};
}
