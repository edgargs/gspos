package mifarma.ptoventa.caja.reference;

import java.awt.event.KeyEvent;

import java.util.ArrayList;

import mifarma.common.FarmaUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityLectorTarjeta {

    private static final Logger log = LoggerFactory.getLogger(UtilityLectorTarjeta.class);

    int contEnter = 0;
    String concatenado = "";
    int vPrimeSep = 0;
    int vSegunSep = 0;
    int vTerceSep = 0;
    String texto = "";

    private void operaCadena() {
        if (!texto.equals("")) {
            log.debug("Lectura banda magnetica de tarjeta: [" + concatenado + "]");
            //ERIOS 25.11.2013 Standar B
            vPrimeSep = ((concatenado.indexOf("B") == -1) ? concatenado.indexOf("b") : concatenado.indexOf("B")) + 1;
            vSegunSep = ((concatenado.indexOf("&") == -1) ? concatenado.indexOf("^") : concatenado.indexOf("&")) + 1;
            if (vSegunSep == 0)
                vSegunSep =
                        ((concatenado.indexOf("¿") == -1) ? concatenado.indexOf("¡") : concatenado.indexOf("¿")) + 1;
            vTerceSep =
                    ((concatenado.indexOf("&", vSegunSep) == -1) ? concatenado.indexOf("^", vSegunSep) : concatenado.indexOf("&",
                                                                                                                             vSegunSep));
        }
    }

    private String sacarNro() {
        if (concatenado.length() == 16) {
            return concatenado;
        }
        if (vSegunSep == 0)
            vSegunSep = 19;
        return concatenado.substring(vPrimeSep, vSegunSep - 1).trim();
    }

    private String sacarNombre() {
        try {
            return concatenado.substring(vSegunSep, vTerceSep - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    private String sacarFecha() {
        try {
            if (vTerceSep > 16) {
                String mes = concatenado.substring(vTerceSep + 3, vTerceSep + 5);
                String ano = concatenado.substring(vTerceSep + 1, vTerceSep + 3);
                return "01/" + mes + "/20" + ano;
            } else {
                return "";
            }
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    public ArrayList<String> capturaTeclasLector(KeyEvent e, String strTexto) {
        log.info("capturataclaslector--> "+strTexto);
        ArrayList<String> infTarjeta = new ArrayList<>();
        concatenado = strTexto;
        texto = strTexto;
        if (strTexto.length() >= 16) {
            operaCadena();
            infTarjeta.add(sacarNro());
            infTarjeta.add(sacarNombre().trim());
            infTarjeta.add(sacarFecha());
        }
        return infTarjeta;
    }

    /**
     * Se enmascara el numero de tarjeta ingresado
     * @author LLEIVA
     * @since 06.Sep.2013
     */
    public String enmascararTarjeta(String numTarj) {
        String res = "";
        int tam = numTarj.length();
        if (tam > 10) {
            String prim = numTarj.substring(0, 6);
            String ult = numTarj.substring(tam - 4, tam);
            String centro = FarmaUtility.caracterIzquierda("", tam - 10, "*");
            res = prim + centro + ult;
        }
        return res;
    }
}
