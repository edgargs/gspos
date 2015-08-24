package mifarma.ptoventa.puntos.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsPuntos {
    public ConstantsPuntos() {
    }
    
    public static final String BONIFICA_CON_TARJETA     = "T";
    public static final String BONIFICA_CON_DNI         = "D";
    public static final String BONIFICA_CON_AMBOS       = "A";
    public static final String BONIFICACION_PRODUCTOS   = "B";
    public static final String REDENCION_PTOS           = "R";
    public static final String SOLICITA_TARJ_OFFLINE    = "O";
    public static final String IND_PROCESO_ORBIS        = "S";
    public static final String TRSX_ORBIS_ENVIADA       = "E";
    public static final String DEFAULT_REQUIERE_TARJETA = "I";
    
    public static final String MNTO_FIDELIZACION = "MOD";    //ASOSA - 28/04/2015 - PTOSYAYAYAYA

    public static final FarmaColumnData columnsListaProductosBonificados[] ={ 
        new FarmaColumnData("Sel", 30, JLabel.CENTER),          //0
        new FarmaColumnData("Código", 50, JLabel.LEFT),         //1
        new FarmaColumnData("Descripción", 200, JLabel.LEFT),   //2    
        new FarmaColumnData("Unidad", 100, JLabel.LEFT),        //3
        new FarmaColumnData("Laboratorio", 150, JLabel.LEFT),   //4
        new FarmaColumnData("Regalo", 50, JLabel.RIGHT),         //5
        new FarmaColumnData("", 0, JLabel.LEFT),                //6
        new FarmaColumnData("", 0, JLabel.RIGHT),               //7
        new FarmaColumnData("", 0, JLabel.RIGHT),               //8
        new FarmaColumnData("", 0, JLabel.RIGHT),               //9
        new FarmaColumnData("LLeva", 50, JLabel.RIGHT),             //10
        new FarmaColumnData("", 0, JLabel.RIGHT),              //11
        new FarmaColumnData("", 0, JLabel.LEFT),              //12
        new FarmaColumnData("", 0, JLabel.LEFT),              //13
        new FarmaColumnData("", 0, JLabel.LEFT)              //14
                                                };
    

    public static final Object[] defaultValuesListaProductosBonificados =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
    
    public static final class COD_MAESTRO {
        public static final String TIPO_MENSAJE_ERROR_LEALTAD = "17";
    }
}

    

