package mifarma.ptoventa.lealtad.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;

/**
 * 
 * @author ERIOS
 * @since 05.02.2015
 */
public class ConstantsLealtad {
    
    public static final FarmaColumnData[] columnsListaAcumula =
    { new FarmaColumnData("Codigo", 50, JLabel.CENTER), 
      new FarmaColumnData("Descripcion", 400, JLabel.LEFT),
      new FarmaColumnData("EquivalenteAcu", 0, JLabel.LEFT), 
      new FarmaColumnData("CodMatrizAcu", 0, JLabel.LEFT)
    } ;

    public static final Object[] defaultValuesListaAcumula = { " ", " ", " ", " " };
}
