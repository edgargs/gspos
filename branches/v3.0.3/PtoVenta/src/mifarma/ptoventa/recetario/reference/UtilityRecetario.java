package mifarma.ptoventa.recetario.reference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityRecetario {
    public UtilityRecetario() {
        super();
    }

    public static boolean validarHoraMin24(String cadena) {
        Pattern pat = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }

    public static boolean validarHoraMinSeg24(String cadena) {
        Pattern pat = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }

    public static boolean validarEmail(String email) {
        Pattern p =
            Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" + "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean validarDDMMYYYY(String fecha) {
        Pattern p =
            Pattern.compile("^(?:(?:0?[1-9]|1\\d|2[0-8])(\\/|-)(?:0?[1-9]|1[0-2]))(\\/|-)(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])$|^(?:(?:31(\\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\\/|-)(?:0?[1,3-9]|1[0-2])))(\\/|-)(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])$|^(29(\\/|-)0?2)(\\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\\d\\d)?(?:0[48]|[2468][048]|[13579][26]))$");
        Matcher m = p.matcher(fecha);
        return m.matches();
    }

    public static boolean validarDecimales(String cadena) {
        if ("".equals(cadena))
            return true;
        Pattern pat = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }

    public static boolean validarNDecimales(String cadena, int posDecimal) {
        if ("".equals(cadena))
            return true;
        Pattern pat = Pattern.compile("^[0-9]+(\\.[0-9]{1," + posDecimal + "})?$");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    }
}
