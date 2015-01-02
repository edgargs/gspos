package mifarma.ptoventa.caja.reference;


public class ConstantsSobres {
    public ConstantsSobres() {
    }

    /*
  ACC_INGRESO  CHAR(1) := 'I';
  ACC_MODIFICA CHAR(1) := 'M';
  ACC_ELIMINA  CHAR(1) := 'E';
  A
   * */
    public static final String ACC_INGRESO = "I";
    public static final String ACC_MODIFICA = "M";
    public static final String ACC_ELIMINA = "E";
    public static final String ACC_APRUEBA = "A";


    public static final String TIPO_TEMPORAL = "T";

    public static final String HASHTABLE_MONEDASOBRES = "MONEDA";
    public static final String[] MONEDAS_COD = { "01", "02" };
    public static final String[] MONEDAS_DESC = { "SOLES", "DOLARES" };

}
