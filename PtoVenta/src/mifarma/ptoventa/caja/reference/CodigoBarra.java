package mifarma.ptoventa.caja.reference;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo    : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación  : CodigoBarra.java<br>
 * Descripcion              : Crea los codigos de barra en formato html usando tablas.
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA      08.08.2014   Creación<br>
 * <br>
 * @author Kenny Moncada<br>
 * @version 1.0<br>
 *
 */
public class CodigoBarra {

    private Map<String, Object> configuracion;

    public CodigoBarra() {
        configuracion = new HashMap<String, Object>();
        configuracion.put("output", "css");
        configuracion.put("bgColor", "#FFFFFF");
        configuracion.put("color", "#000000");
        configuracion.put("barWidth", 2);
        configuracion.put("barHeight", 70);
        configuracion.put("moduleSize", 5);
        configuracion.put("posX", 10);
        configuracion.put("posY", 20);
        configuracion.put("addQuietZone", 1);
        configuracion.put("showHRI", true);
        configuracion.put("fontSize", 14);
        configuracion.put("marginHRI", 5);
    }

    public String generarCodeBarra(String code, String type) {
        if (code == null)
            return null;
        if (code.trim().length() == 0)
            return null;

        if (type == null)
            return null;
        if (type.trim().length() == 0)
            return null;

        type = type.toLowerCase();

        String digit = "", hri = "";
        boolean crc = true;
        boolean rect = false;
        boolean b2d = false;

        switch (type) {
        case "ean13":
        case "ean8":
            digit = eanGetDigito(code, type);
            hri = eanCompute(code, type);
            break;
        case "code39":
            digit = code39GetDigit(code);
            hri = code;
            break;
        case "code128":
            configuracion.put("barWidth", 1);
            configuracion.put("barHeight", 50);
            configuracion.put("fontSize", 12);
            digit = code128GetDigit(code);
            hri = code;
            break;
        default:
            return null;
        }

        if (digit == null)
            return (null);
        if (digit.length() == 0)
            return (null);

        if (!b2d && (configuracion.get("addQuietZone") == 1 ? true : false))
            digit = digit + "0000000000";

        return digitosToCss(digit, hri);
    }
    
    public String generarCodeBarraPtos(String code, String type) {
        if (code == null)
            return null;
        if (code.trim().length() == 0)
            return null;

        if (type == null)
            return null;
        if (type.trim().length() == 0)
            return null;

        type = type.toLowerCase();

        String digit = "", hri = "";
        boolean crc = true;
        boolean rect = false;
        boolean b2d = false;

        switch (type) {
        case "ean13":
        case "ean8":
            digit = eanGetDigito(code, type);
            hri = eanCompute(code, type);
            break;
        case "code39":
            configuracion.put("barWidth", 2);
            configuracion.put("barHeight", 50);
            configuracion.put("fontSize", 12);
            digit = code39GetDigit(code);
            hri = code;
            break;
        case "code128":
            configuracion.put("barWidth", 3);
            configuracion.put("barHeight", 50);
            configuracion.put("fontSize", 12);
            digit = code128GetDigit(code);
            hri = code;
            break;
        default:
            return null;
        }

        if (digit == null)
            return (null);
        if (digit.length() == 0)
            return (null);

        if (!b2d && (configuracion.get("addQuietZone") == 1 ? true : false))
            digit = digit + "0000000000";

        return digitosToCssPtos(digit, hri);
    }

    private String eanGetDigito(String code, String type) {
        String[][] encoding =
        { { "0001101", "0100111", "1110010" }, { "0011001", "0110011", "1100110" }, { "0010011", "0011011",
                                                                                      "1101100" },
          { "0111101", "0100001", "1000010" }, { "0100011", "0011101", "1011100" },
          { "0110001", "0111001", "1001110" }, { "0101111", "0000101", "1010000" },
          { "0111011", "0010001", "1000100" }, { "0110111", "0001001", "1001000" },
          { "0001011", "0010111", "1110100" } };

        String[] first =
        { "000000", "001011", "001101", "001110", "010011", "011001", "011100", "010101", "010110", "011010" };

        // verifica longitud (12 ean13, 7 ean8)
        int len = type == "ean8" ? 7 : 12;
        code = code.substring(0, len);
        if (code.length() != len)
            return null;
        // verifica que cada digito sea numero
        char c;
        for (int i = 0; i < code.length(); i++) {
            c = code.charAt(i);
            if ((c < '0') || (c > '9'))
                return null;
        }

        code = eanCompute(code, type);

        // inicia proceso
        String result = "101"; // start

        if (type == "ean8") {

            // procesa cadena izquierda
            for (int i = 0; i < 4; i++) {
                result += encoding[intval(code.charAt(i))][0];
            }

            // barras centrales
            result += "01010";

            // procesa codena derecha
            for (int i = 4; i < 8; i++) {
                result += encoding[intval(code.charAt(i))][2];
            }

        } else { // ean13
            // obtiene primer digito y obtiene sequencia
            String seq = first[intval(code.charAt(0))];

            // procesa parte izquierda
            for (int i = 1; i < 7; i++) {
                result += encoding[intval(code.charAt(i))][intval(seq.charAt(i - 1))];
            }

            // barra central
            result += "01010";

            // procesa parte derecha
            for (int i = 7; i < 13; i++) {
                result += encoding[intval(code.charAt(i))][2];
            }
        } // ean13

        result += "101"; // stop
        return (result);
    }

    private String eanCompute(String code, String type) {
        int len = type == "ean13" ? 12 : 7;
        code = code.substring(0, len);
        int sum = 0;
        boolean odd = true;
        for (int i = code.length() - 1; i > -1; i--) {
            sum += (odd ? 3 : 1) * intval(code.charAt(i));
            odd = !odd;
        }
        return (code + String.valueOf((10 - sum % 10) % 10));
    }

    private String code39GetDigit(String code) {
        String[] encoding =
        { "101001101101", "110100101011", "101100101011", "110110010101", "101001101011", "110100110101",
          "101100110101", "101001011011", "110100101101", "101100101101", "110101001011", "101101001011",
          "110110100101", "101011001011", "110101100101", "101101100101", "101010011011", "110101001101",
          "101101001101", "101011001101", "110101010011", "101101010011", "110110101001", "101011010011",
          "110101101001", "101101101001", "101010110011", "110101011001", "101101011001", "101011011001",
          "110010101011", "100110101011", "110011010101", "100101101011", "110010110101", "100110110101",
          "100101011011", "110010101101", "100110101101", "100100100101", "100100101001", "100101001001",
          "101001001001", "100101101101" };

        String table = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*";
        int i, index;
        String result = "";
        char intercharacter = '0';

        if (code.indexOf('*') >= 0)
            return null;

        // agrega caracter de inicio y fin : *
        code = ("*" + code + "*").toUpperCase();

        for (i = 0; i < code.length(); i++) {
            index = table.indexOf(code.charAt(i));
            if (index < 0)
                return null;
            if (i > 0)
                result += intercharacter;
            result += encoding[index];
        }
        return (result);
    }

    private String code128GetDigit(String code) {

        String[] encoding =
        { "11011001100", "11001101100", "11001100110", "10010011000", "10010001100", "10001001100", "10011001000",
          "10011000100", "10001100100", "11001001000", "11001000100", "11000100100", "10110011100", "10011011100",
          "10011001110", "10111001100", "10011101100", "10011100110", "11001110010", "11001011100", "11001001110",
          "11011100100", "11001110100", "11101101110", "11101001100", "11100101100", "11100100110", "11101100100",
          "11100110100", "11100110010", "11011011000", "11011000110", "11000110110", "10100011000", "10001011000",
          "10001000110", "10110001000", "10001101000", "10001100010", "11010001000", "11000101000", "11000100010",
          "10110111000", "10110001110", "10001101110", "10111011000", "10111000110", "10001110110", "11101110110",
          "11010001110", "11000101110", "11011101000", "11011100010", "11011101110", "11101011000", "11101000110",
          "11100010110", "11101101000", "11101100010", "11100011010", "11101111010", "11001000010", "11110001010",
          "10100110000", "10100001100", "10010110000", "10010000110", "10000101100", "10000100110", "10110010000",
          "10110000100", "10011010000", "10011000010", "10000110100", "10000110010", "11000010010", "11001010000",
          "11110111010", "11000010100", "10001111010", "10100111100", "10010111100", "10010011110", "10111100100",
          "10011110100", "10011110010", "11110100100", "11110010100", "11110010010", "11011011110", "11011110110",
          "11110110110", "10101111000", "10100011110", "10001011110", "10111101000", "10111100010", "11110101000",
          "11110100010", "10111011110", "10111101110", "11101011110", "11110101110", "11010000100", "11010010000",
          "11010011100", "11000111010" };

        String tableB =
            " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        String result = "";
        int sum = 0;
        int isum = 0;
        int i = 0;
        int j = 0;
        int value = 0;

        // verifica cada caracter
        for (i = 0; i < code.length(); i++) {
            if (tableB.indexOf(code.charAt(i)) == -1)
                return null;
        }

        // verifica primer caracter: check firsts characters : start with C table only if enought numeric
        boolean tableCActivated = code.length() > 1;
        char c = ' ';
        for (i = 0; i < 3 && i < code.length(); i++) {
            c = code.charAt(i);
            tableCActivated &= c >= '0' && c <= '9';
        }

        sum = tableCActivated ? 105 : 104;

        // start : [105] : C table or [104] : B table
        result = encoding[sum];

        i = 0;
        System.err.println(code + " - " + code.length() + " - " + value);
        while (i < code.length()) {
            if (!tableCActivated) {
                System.err.println("z");
                j = 0;
                // check next character to activate C table if interresting
                while ((i + j < code.length()) && (code.charAt(i + j) >= '0') && (code.charAt(i + j) <= '9'))
                    j++;

                // 6 min everywhere or 4 mini at the end
                tableCActivated = (j > 5) || ((i + j - 1 == code.length()) && (j > 3));

                if (tableCActivated) {
                    result += encoding[99]; // C table
                    sum += ++isum * 99;
                }
                //         2 min for table C so need table B
            } /*
            else if ((i == code.length()) || (code.charAt(i) < '0') || (code.charAt(i) > '9') ||
                       (code.charAt(i + 1) < '0') || (code.charAt(i + 1) > '9')) {
                System.err.println("x");
                tableCActivated = false;
                System.out.println("HOLA HOLA");
                result += encoding[100]; // B table
                sum += ++isum * 100;
            }*/
            System.err.println("HOLA HOLA");
            if (tableCActivated && code.length() == 8) {
                value = intval(code.charAt(i) + "" + code.charAt(i + 1)); // Add two characters (numeric)
                i += 2;
                System.err.println("HOLA HOLA1");
            } else {
                value = tableB.indexOf(code.charAt(i)); // Add one character
                i += 2;//1
                System.err.println("HOLA HOLA2");
            }
            System.err.println("HOLA HOLA3");

            System.err.println(tableCActivated + " - " + i + " - " + value + " - " + encoding.length);
            result += encoding[value];
            sum += ++isum * value;
        }

        // Add CRC
        result += encoding[sum % 103];

        // Stop
        result += encoding[106];

        // Termination bar
        result += "11";

        return (result);
    }

    private int intval(Object val) {
        return Integer.parseInt(String.valueOf(val));
    }

    private String digitosToCss(String digit, String hri) {
        int w = intval(configuracion.get("barWidth"));
        int h = intval(configuracion.get("barHeight"));
        return digitosToCssRender(pasarStringToArray(digit), hri, w, h);
    }
    
    private String digitosToCssPtos(String digit, String hri) {
        int w = intval(configuracion.get("barWidth"));
        int h = intval(configuracion.get("barHeight"));
        return digitosToCssRenderPtos(pasarStringToArray(digit), hri, w, h);
    }
    
    private String digitosToCssRenderPtos(char[] digit, String hri, int mw, int mh) {
        int contador = 0;
        int lineas = 1;
        int columnas = digit.length;
        String content = "<tr><td><br></td><td><br></td><td><br></td><td><br></td><td><br></td><td><br></td><td><br></td><td><br></td>";
        String bar0 =
            "<td style=\"float: left; font-size: 0px; background-color: " + configuracion.get("bgColor") + "; height: " +
            mh + "px; width: &Wpx\"></td>";
        //String bar1 = "<td style=\"float: left; font-size: 0px; width: &Wpx; background: " + configuracion.get("color") + "; height: " + mh + "px;\"></td>";
        String bar1 =
            "<td style=\"float: left; font-size: 0px; width:0; border-left: &Wpx solid " + configuracion.get("color") +
            "; height: " + mh + "px;\"></td>";

        int len, current;
        for (int y = 0; y < lineas; y++) {
            len = 0;
            current = digit[0];
            for (int x = 0; x < columnas; x++) {
                if (current == digit[x]) {
                    len++;
                } else {
                    content += (current == '0' ? bar0 : bar1).replace("&W", String.valueOf(len * mw));
                    contador++;
                    current = digit[x];
                    len = 1;
                }
            }
            if (len > 0) {
                content += (current == '0' ? bar0 : bar1).replace("&W", String.valueOf(len * mw));
                contador++;
            }
        }
        if (configuracion.get("showHRI") == true) {
            content += "</tr><tr><td align=\"center\" colspan=\"" + (contador+8) + "\">" + hri + "</td></tr>";
        }
        return content;
    }

    private String digitosToCssRender(char[] digit, String hri, int mw, int mh) {
        int contador = 0;
        int lineas = 1;
        int columnas = digit.length;
        String content = "<tr>";
        String bar0 =
            "<td style=\"float: left; font-size: 0px; background-color: " + configuracion.get("bgColor") + "; height: " +
            mh + "px; width: &Wpx\"></td>";
        //String bar1 = "<td style=\"float: left; font-size: 0px; width: &Wpx; background: " + configuracion.get("color") + "; height: " + mh + "px;\"></td>";
        String bar1 =
            "<td style=\"float: left; font-size: 0px; width:0; border-left: &Wpx solid " + configuracion.get("color") +
            "; height: " + mh + "px;\"></td>";

        int len, current;
        for (int y = 0; y < lineas; y++) {
            len = 0;
            current = digit[0];
            for (int x = 0; x < columnas; x++) {
                if (current == digit[x]) {
                    len++;
                } else {
                    content += (current == '0' ? bar0 : bar1).replace("&W", String.valueOf(len * mw));
                    contador++;
                    current = digit[x];
                    len = 1;
                }
            }
            if (len > 0) {
                content += (current == '0' ? bar0 : bar1).replace("&W", String.valueOf(len * mw));
                contador++;
            }
        }
        if (configuracion.get("showHRI") == true) {
            content += "</tr><tr><td align=\"center\" colspan=\"" + contador + "\">" + hri + "</td></tr>";
        }
        return content;
    }

    private char[] pasarStringToArray(String digit) {
        char[] d = digit.toCharArray();
        return (d);
    };
}
