package mifarma.ptoventa.mayorista.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;

public class ConstantsVtaMayorista {
    public ConstantsVtaMayorista() {
        super();
    }
    
    public static final String COD_NUMERA_PROFORMA = "101";
    public static final String COD_NUMERA_PEDIDO_DIARIO_PROFORMA = "102";
    
    public static final FarmaColumnData columnsListaPreciosDscto[] ={ 
        new FarmaColumnData("Cant.Minima", 100, JLabel.CENTER),
        new FarmaColumnData("Cant.Maxima", 0, JLabel.LEFT), 
        new FarmaColumnData("Precio Unit.", 100, JLabel.RIGHT),
        new FarmaColumnData("Codigo Dscto", 0, JLabel.LEFT), 
        new FarmaColumnData("Codigo Det Dscto", 0, JLabel.RIGHT),
        new FarmaColumnData("Dscto", 0, JLabel.RIGHT)};
    
    public static final Object[] defaultValueListaPreciosDscto =
    { " ", " ", " ", " ", " ", " "};
}
