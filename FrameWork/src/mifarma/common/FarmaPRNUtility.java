package mifarma.common;

import java.util.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaPRNUtility.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */

public class FarmaPRNUtility {

    public FarmaPRNUtility() {
    }

    /**
     * Alinea la variable tipo int hacia la derecha colocando espacios en blanco
     * a la izquierda según la longitud que se establezca.
     * 
     * @param parmint
     *             Variable int que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la derecha.
     */
    public static String alinearDerecha(int parmint, int parmLen) {
        return alinearDerecha(String.valueOf(parmint), parmLen);
    }

    /**
     * Alinea la variable tipo long hacia la derecha colocando espacios en
     * blanco a la izquierda según la longitud que se establezca.
     * 
     * @param parmlong
     *             Variable long que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la derecha.
     */
    public static String alinearDerecha(long parmlong, int parmLen) {
        return alinearDerecha(String.valueOf(parmlong), parmLen);
    }

    /**
     * Alinea la variable tipo double hacia la derecha colocando espacios en
     * blanco a la izquierda según la longitud que se establezca.
     * 
     * @param parmdouble
     *             Variable double que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la derecha.
     */
    public static String alinearDerecha(double parmdouble, int parmLen) {
        return alinearDerecha(String.valueOf(parmdouble), parmLen);
    }

    /**
     * Alinea el texto hacia la derecha colocando espacios en blanco a la
     * izquierda según la longitud que se establezca.
     * 
     * @param parmString
     *             Cadena String que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la derecha.
     */
    public static String alinearDerecha(String parmString, int parmLen) {

        String tempString;
        int addLen;
        StringBuffer sb = new StringBuffer(parmLen);

        if (parmString.length() > parmLen)
            tempString = 
                    parmString.substring(parmString.length() - parmLen, parmString.length());
        else {

            addLen = parmLen - parmString.length();
            while (addLen > 70) {
                sb = sb.append(FarmaConstants.blanco, 0, 70);
                addLen -= 70;
            }
            tempString = 
                    sb.append(FarmaConstants.blanco, 0, addLen).append(parmString).toString();

        }

        return tempString;

    }

    /**
     * Alinea la variable tipo int hacia la izquierda colocando espacios en
     * blanco a la derecha según la longitud que se establezca.
     * 
     * @param parmint
     *             Variable int que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la izquierda.
     */
    public static String alinearIzquierda(int parmint, int parmLen) {
        return alinearIzquierda(String.valueOf(parmint), parmLen);
    }

    /**
     * Alinea la variable tipo long hacia la izquierda colocando espacios en
     * blanco a la derecha según la longitud que se establezca.
     * 
     * @param parmlong
     *             Variable long que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la izquierda.
     */
    public static String alinearIzquierda(long parmlong, int parmLen) {
        return alinearIzquierda(String.valueOf(parmlong), parmLen);
    }

    /**
     * Alinea la variable tipo double hacia la izquierda colocando espacios en
     * blanco a la derecha según la longitud que se establezca.
     * 
     * @param parmdouble
     *             Variable double que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la izquierda.
     */
    public static String alinearIzquierda(double parmdouble, int parmLen) {
        return alinearIzquierda(String.valueOf(parmdouble), parmLen);
    }

    /**
     * Alinea el texto hacia la izquierda colocando espacios en blanco a la
     * derecha según la longitud que se establezca.
     * 
     * @param parmString
     *             Cadena String que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @return String String alineado a la izquierda.
     */
    public static String alinearIzquierda(String parmString, int parmLen) {

        String tempString;
        int addLen;
        StringBuffer sb = new StringBuffer(parmLen);

        if (parmString.length() > parmLen)
            tempString = parmString.substring(0, parmLen);
        else {
            addLen = parmLen - parmString.length();
            sb = sb.append(parmString);
            while (addLen > 70) {
                sb = sb.append(FarmaConstants.blanco, 0, 70);
                addLen -= 70;
            }
            tempString = 
                    sb.append(FarmaConstants.blanco, 0, addLen).toString();
        }

        return tempString;

    }

    /**
     * Alinea la variable int hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     * 
     * @param parmint
     *             Variable int que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *             String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(int parmint, int parmLen, 
                                           String parmCaracter) {
        return caracterIzquierda(String.valueOf(parmint), parmLen, 
                                 parmCaracter);
    }

    /**
     * Alinea la variable long hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     * 
     * @param parmint
     *             Variable long que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *             String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(long parmint, int parmLen, 
                                           String parmCaracter) {
        return caracterIzquierda(String.valueOf(parmint), parmLen, 
                                 parmCaracter);
    }

    /**
     * Alinea la variable double hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     * 
     * @param parmint
     *             Variable double que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *             String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(double parmint, int parmLen, 
                                           String parmCaracter) {
        return caracterIzquierda(String.valueOf(parmint), parmLen, 
                                 parmCaracter);
    }

    /**
     * Alinea la variable String hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     * 
     * @param parmString
     *             Variable int que será alineada.
     * @param parmLen
     *             Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *             String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(String parmString, int parmLen, 
                                           String parmCaracter) {

        String tempString = parmString;

        if (tempString.length() > parmLen)
            tempString = 
                    tempString.substring(tempString.length() - parmLen, tempString.length());
        else {
            while (tempString.length() < parmLen)
                tempString = parmCaracter + tempString;
        }

        return tempString;

    }

    /**
     * Retorna la Fecha y Hora del día.
     * 
     * @return String String en formato dd/mm/yyyy hh:mm.
     */
    public static String getFechaHoraPC() {

        java.util.Calendar rightNow = java.util.Calendar.getInstance();
        String today = 
            caracterIzquierda(rightNow.get(java.util.Calendar.DAY_OF_MONTH), 2, 
                              "0") + "/" + 
            caracterIzquierda(rightNow.get(java.util.Calendar.MONTH) + 1, 2, 
                              "0") + "/" + 
            rightNow.get(java.util.Calendar.YEAR) + " " + 
            caracterIzquierda(rightNow.get(java.util.Calendar.HOUR_OF_DAY), 2, 
                              "0") + ":" + 
            caracterIzquierda(rightNow.get(java.util.Calendar.MINUTE), 2, 
                              "0") + ":" + 
            caracterIzquierda(rightNow.get(java.util.Calendar.SECOND), 2, "0");
        return today;

    }

    /**
     * Devuelve en letras un monto determinado.
     * 
     * @param monto
     *             Variable int que será mostrada en letras.
     * @return String Monto en letras.
     */
    public static String montoEnLetras(int monto) {
        return valorEnLetras(new Double(String.valueOf(monto)));
    }

    /**
     * Devuelve en letras un monto determinado.
     * 
     * @param monto
     *             Variable long que será mostrada en letras.
     * @return String Monto en Letras.
     */
    public static String montoEnLetras(long monto) {
        return valorEnLetras(new Double(String.valueOf(monto)));
    }

    /**
     * Devuelve en letras un monto determinado.
     * 
     * @param monto
     *             Variable double que será mostrada en letras.
     * @return String Monto en Letras.
     */
    public static String montoEnLetras(double monto) {
        return valorEnLetras(new Double(monto));
    }

    /**
     * Devuelve en letras un monto determinado.
     * 
     * @param monto
     *             Variable String que será mostrada en letras.
     * @return String Monto en Letras.
     */
    public static String montoEnLetras(String monto) {
        return valorEnLetras(new Double(FarmaUtility.getDecimalNumber(monto)));
    }

    /**
     * Devuelve en letras un monto determinado.
     * 
     * @param valor
     *             Objeto Double que será mostrada en letras.
     * @return String Monto en Letras.
     */
    private static String valorEnLetras(Double valor) {

        String centavos = "00";
        double doubleValor = Double.parseDouble(valor.toString());
        int numero = valor.intValue();
        int posPunto = String.valueOf(valor).indexOf(".");
        int posComa = String.valueOf(valor).indexOf(",");
        double doubleNumero = Double.parseDouble(String.valueOf(numero));
        if (posPunto > 0 || posComa > 0) {
            if (posPunto > 0)
                centavos = String.valueOf(valor).substring(posPunto + 1);
            if (posComa > 0)
                centavos = String.valueOf(valor).substring(posComa + 1);
        } else
            centavos = "00";

        String cadena = "";
        int millon = 0;
        int cienMil = 0;

        if (numero < 1000000000) {

            if (numero > 999999) {
                millon = (new Double(numero / 1000000)).intValue();
                numero = numero - millon * 1000000;
                cadena += 
                        base(millon, true) + (millon > 1 ? " MILLONES " : " MILLON ");
            }
            if (numero > 999) {
                cienMil = (new Double(numero / 1000)).intValue();
                numero = numero - cienMil * 1000;
                cadena += base(cienMil, false) + " MIL ";
            }

            cadena += base(numero, true);

            if (cadena != null && cadena.trim().length() > 0) {
                cadena += " CON ";
            }

            if (centavos.trim().length() == 1)
                centavos += "0";
            cadena += String.valueOf(centavos) + "/100";

        }

        return cadena.trim() + " Nuevos Soles";

    }

    /**
     * Retorna en letras monto concatenado.
     * 
     * @param numero
     *             Variable int que será procesada.
     * @param fin
     *             Indica si existen o no, procesos pendientes.
     * @return String Monto concatenado en letras.
     */
    public static String base(int numero, boolean fin) {

        String cadena = "";
        int unidad = 0;
        int decena = 0;
        int centena = 0;

        if (numero < 1000) {

            if (numero > 99) {
                centena = (new Double(numero / 100)).intValue();
                numero = numero - centena * 100;
                if (centena == 1 && numero == 0)
                    cadena += "CIEN ";
                else
                    cadena += centenas(centena) + " ";
            }

            if (numero > 29) {
                decena = (new Double(numero / 10)).intValue();
                numero = numero - decena * 10;
                if (numero > 0)
                    cadena += 
                            decenas(decena) + " Y " + unidad(numero, false) + " ";
                else
                    cadena += decenas(decena) + " ";
            } else {
                cadena += unidad(numero, fin);
            }
        }

        return cadena.trim();

    }

    /**
     * Retorna en letras la cantidad de unidades de un número dado.
     * 
     * @param numero
     *             Variable int que será procesada.
     * @param fin
     *             Indica si existen o no, procesos pendientes.
     * @return String Número de unidades en letras.
     */
    public static String unidad(int numero, boolean fin) {
        String[] aUnidades = 
        { "UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", 
          "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", 
          "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE", 
          "VEINTIUNO", "VEINTIDOS", "VEINTITRES", "VEINTICUATRO", 
          "VEINTICINCO", "VEINTISEIS", "VEINTISIETE", "VEINTIOCHO", 
          "VEINTINUEVE" };
        String cadena = "";

        if (numero > 0) {
            if (numero == 1 && fin)
                cadena = "UNO";
            else
                cadena = aUnidades[numero - 1];
        }

        return cadena.trim();
    }

    /**
     * Retorna en letras la cantidad de decenas de un número dado.
     * 
     * @param numero
     *             Variable int que será procesada.
     * @return String Número de decenas en letras.
     */
    public static String decenas(int numero) {

        String[] aDecenas = 
        { "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", 
          "SETENTA", "OCHENTA", "NOVENTA" };

        return (numero == 0 ? "" : aDecenas[numero - 1]);

    }

    /**
     * Retorna en letras la cantidad de centenas de un número dado.
     * 
     * @param numero
     *             Variable int que será procesada.
     * @return String Número de centenas en letras.
     */
    public static String centenas(int numero) {

        String[] aCentenas = 
        { "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", 
          "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS" };

        return (numero == 0 ? "" : aCentenas[numero - 1]);

    }

    /**
     * Retorna una cadena de blancos según una longitud establecido.
     * 
     * @param parmLen
     *             Variable int que determina la longitud de la cadena.
     * @return String Cadena de blancos.
     */
    public static String llenarBlancos(int parmLen) {

        String tempString = "";
        for (int i = 0; i < parmLen; i++)
            tempString += " ";
        return tempString;

    }

    public static String fillBlanks(int parmLen) {
        String tempString = "";
        for (int i = 0; i < parmLen; i++)
            tempString += " ";
        return tempString;
    }

    /**
     * Metodo que se procesara una cadena para tabular elementos de acuerdo a cada valor
     * que se encuentra en este.
     *  Ejemplo : Se envia la cadena "1/15/10"
     *  Ahora invoco al metodo para tabular cada 4 caracteres
     *           String cadena = "1/15/10"
     *           String result =  tabular(cadena,4,"/");
     *  El valor del resultado seria : "   1/15  /10  "
     * @author dubilluz
     * @since  16.10.2007
     * @param  pcadena
     *              Cadena se tabulara de acuerdo al numespace.
     * @param  pnumespace
     *              Numero de espacios para tabulra entre cada valor.
     * @param  pSeparador
     *              Separador para identificar los valores en la cadena enviada.
     * @return String Cadena tabulada y con el separador entre cada elemento.
     */
    public static String tabular(String pcadena, int pnumespace, 
                                 String pSeparador) {
        String resultado = "";
        if (pcadena.indexOf(pSeparador) != -1) {
            String[] array = pcadena.split(pSeparador);
            String valor = "";
            int numespace = 0;
            for (int i = 0; i < array.length; i++) {
                valor = array[i].trim();
                numespace = pnumespace - valor.length();
                for (int j = 0; j < numespace; j++) {
                    if (i == 0)
                        valor = " " + valor;
                    else
                        valor = valor + " ";
                }
                if (i + 1 == array.length)
                    resultado = resultado + valor;
                else
                    resultado = resultado + valor + pSeparador;
            }
        } else {
            resultado = pcadena;
        }
        return resultado;
    }

    /**
     * Devuelve en letras un monto determinado.
     * 
     * @param monto
     *             Variable String que será mostrada en letras.
     * @return String Monto en Letras.
     */
    public static String montoEnLetrasBs(String monto) {
        return valorEnLetrasBs(new Double(FarmaUtility.getDecimalNumber(monto)));
    }
    /**
     * Devuelve en letras un monto determinado para Bolivia.
     * 
     * @param valor
     *             Objeto Double que será mostrada en letras.
     * @return String Monto en Letras.
     */
    private static String valorEnLetrasBs(Double valor) {

        String centavos = "00";
        double doubleValor = Double.parseDouble(valor.toString());
        int numero = valor.intValue();
        int posPunto = String.valueOf(valor).indexOf(".");
        int posComa = String.valueOf(valor).indexOf(",");
        double doubleNumero = Double.parseDouble(String.valueOf(numero));
        if (posPunto > 0 || posComa > 0) {
            if (posPunto > 0)
                centavos = String.valueOf(valor).substring(posPunto + 1);
            if (posComa > 0)
                centavos = String.valueOf(valor).substring(posComa + 1);
        } else
            centavos = "00";

        String cadena = "";
        int millon = 0;
        int cienMil = 0;

        if (numero < 1000000000) {

            if (numero > 999999) {
                millon = (new Double(numero / 1000000)).intValue();
                numero = numero - millon * 1000000;
                cadena += 
                        base(millon, true) + (millon > 1 ? " MILLONES " : " MILLON ");
            }
            if (numero > 999) {
                cienMil = (new Double(numero / 1000)).intValue();
                numero = numero - cienMil * 1000;
                cadena += base(cienMil, false) + " MIL ";
            }

            cadena += base(numero, true);

            if (cadena != null && cadena.trim().length() > 0) {
                cadena += " CON ";
            }

            if (centavos.trim().length() == 1)
                centavos += "0";
            cadena += String.valueOf(centavos) + "/100";

        }

        return cadena.trim()+" Bs.";

    }
}
