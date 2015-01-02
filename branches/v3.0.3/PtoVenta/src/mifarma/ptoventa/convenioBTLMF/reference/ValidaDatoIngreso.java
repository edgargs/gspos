package mifarma.ptoventa.convenioBTLMF.reference;

import java.sql.SQLException;

import java.util.regex.Pattern;

import javax.swing.JDialog;

import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ValidaDatoIngreso.java<br>
 * <br>
 * Util para validar datos de ingreso del convenio<br>
 * FRAMIREZ     05.12.2011   Creación<br>
 * <br>
 * @AUTHOR FREDY RAMIREZ C<br>
 * @VERSION 1.0<br>
 *
 */

public class ValidaDatoIngreso extends javax.swing.text.PlainDocument {
    private static final Logger log = LoggerFactory.getLogger(ValidaDatoIngreso.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String pClaseObj = "0";
    JDialog parentDialog;
    int maxLongitud = 0;

    /**
     * Método al que llama el editor cada vez que se intenta insertar caracteres.
     * Sólo debemos verificar arg1, que es la cadena que se quiere insertar en el JTextField
     */
    public ValidaDatoIngreso(JDialog dialogo, String pClaseObj, int maxLongitud) {
        this.pClaseObj = pClaseObj;
        parentDialog = dialogo;
        this.maxLongitud = maxLongitud;
    }

    public void insertString(int arg0, String arg1,
                             javax.swing.text.AttributeSet arg2) throws javax.swing.text.BadLocationException {
        log.debug("Arg1==" + arg1);
        if (arg1.length() == 1) {
            if (pClaseObj.equals(ConstantsConvenioBTLMF.CLASE_DNI)) {
                if (!validarDni(arg1))
                    return;
                //Si es digito y cumple con la longitud definida ingresamos en la caja de texto
                if (getLength() < maxLongitud)
                    super.insertString(arg0, arg1, arg2);
            } else if (pClaseObj.equals(ConstantsConvenioBTLMF.CLASE_FECHA_NAC)) {
                if (!validarFecha(arg1))
                    return;
                //Si es caracter valido de email ingresamos en la caja de texto
                if (getLength() < maxLongitud) {
                    if (arg0 == 1) {
                        super.insertString(arg0, arg1 + "/", arg2);
                    } else if (arg0 == 4) {
                        super.insertString(arg0, arg1 + "/19", arg2);
                    } else {
                        super.insertString(arg0, arg1, arg2);
                    }
                }
            } else if (pClaseObj.equals(ConstantsConvenioBTLMF.CLASE_FECHA)) {
                if (!validarFecha(arg1))
                    return;
                try {
                    String anhoBD = FarmaSearch.getFechaHoraBD(1).trim().substring(6, 10);
                    if (getLength() < maxLongitud) {
                        if (arg0 == 1 && !arg1.equals("/")) {
                            super.insertString(arg0, arg1 + "/", arg2);
                        } else if (arg0 == 4 && !arg1.equals("/")) {
                            super.insertString(arg0, arg1 + "/" + anhoBD, arg2);
                        } else {
                            super.insertString(arg0, arg1, arg2);
                        }
                    }
                } catch (SQLException sql) {
                    FarmaUtility.showMessage(parentDialog,
                                             "Ocurio un error al obtener la Fecha \n " + sql.getMessage(), arg1);
                }
            } else if (pClaseObj.equals(ConstantsConvenioBTLMF.CLASE_TELEFONO)) {
                if (!validarTelefono(arg1))
                    return;
                // Si todos son digit, insertamos el texto en el JTextField
                if (getLength() < maxLongitud)
                    super.insertString(arg0, arg1, arg2);
            } else if (pClaseObj.equals(ConstantsConvenioBTLMF.CLASE_DATO_ADICIONAL)) {
                if (!validarAlfaNumerico(arg1))
                    return;
                // Si todos son digit, insertamos el texto en el JTextField
                if (getLength() < maxLongitud)
                    super.insertString(arg0, arg1, arg2);
            }
        } else {
            super.insertString(arg0, arg1, arg2);
        }

    }

    public static boolean validarDni(String caracter) {
        boolean space = false;
        if (caracter.equals(" ")) {
            space = true;
        }
        boolean retorno = false;
        boolean numero = Pattern.matches("[0-9]", caracter);
        if (numero || space) {
            retorno = true;
        }
        log.debug("Retorno:" + retorno);
        return retorno;
    }

    public static boolean validarFecha(String cadena) {
        boolean retorno = false;
        boolean numero = Pattern.matches("[0-9]", cadena);
        if (numero) {
            retorno = true;
        }
        log.debug("Retorno=" + retorno);
        return retorno;
    }

    public static boolean validarTelefono(String cadena) {
        boolean retorno = false;
        boolean numero = Pattern.matches("[0-9]", cadena);
        if (numero) {
            retorno = true;
        }
        log.debug("Retorno=" + retorno);
        return retorno;
    }

    public static boolean validarAlfaNumerico(String cadena) {
        boolean space = false;
        boolean punto = false;
        if (cadena.equals(" ")) {
            space = true;
        }
        if (cadena.equals(".")) {
            punto = true;
        }
        boolean retorno = false;
        boolean letra = Pattern.matches("[a-zA-ZñÑ-]", cadena);
        boolean numero = Pattern.matches("[0-9]", cadena);
        if (letra || numero || space || punto) {
            retorno = true;
        }
        log.debug("Retorno:" + retorno);
        return retorno;
    }

}

