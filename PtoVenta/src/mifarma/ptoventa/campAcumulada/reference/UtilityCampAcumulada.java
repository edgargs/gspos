package mifarma.ptoventa.campAcumulada.reference;

import java.util.regex.Pattern;

import mifarma.ptoventa.otros.reference.UtilityOtros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityCampAcumulada {
    public UtilityCampAcumulada() {
    }

    private static final Logger log = LoggerFactory.getLogger(UtilityOtros.class);

    /**
     * Metodo encargado de validar formato de correo.
     *@autor jcallo
     *@since 15.12.2008
     */
    public static boolean validarEmail(String email) {

        boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);

        return b;
    }

    /**
     * Metodo encargado de validacion del documento de identificacion ( DNI, CARNE DE EXTRANJERIA)     *
     *@autor jcallo
     *@since 15.12.2008
     *@param String docIden , String valoresValidos
     */
    public static boolean validarDocIndentificacion(String docIden, String docValidos) {
        log.debug("doc a validar :" + docIden);
        log.debug("validar con :" + docValidos);
        boolean flag = false;
        String paramDocVal = docValidos;
        if (paramDocVal != null) {
            String valores[] = paramDocVal.split(",");
            log.debug("valores :  " + valores);
            for (int i = 0; i < valores.length; i++) {
                //log.debug("izq : "+Integer.parseInt( valores[i].trim() )+" doc: "+docIden+", docIden length: "+docIden.length());
                if (Integer.parseInt(valores[i].trim()) == docIden.length()) {
                    log.debug("ok");
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}

