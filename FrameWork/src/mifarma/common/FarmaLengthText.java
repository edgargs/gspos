package mifarma.common;

import javax.swing.text.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaLengthText.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaLengthText extends javax.swing.text.PlainDocument {

    /** Almacena la longitud máxima del contenido del Objeto JTextField */
    int maxLength;

    /**
     *Constructor : Inicializa la longitud del Objeto JTextField.
     *@param pMaxLength Longitud máxima del contenido del Objeto.
     */
    public FarmaLengthText(int pMaxLength) {
        super();
        this.maxLength = pMaxLength;
    }

    /** 
     *Permite el ingreso de caracteres controlando no sobrepasar la longitud
     *máxima del contenido del Objeto.
     *@param pOffSet
     *@param pStr
     *@param pAttribute
     */
    public void insertString(int pOffSet, String pStr, 
                             AttributeSet pAttribute) {
        if (getLength() < maxLength)
            try {
                super.insertString(pOffSet, pStr, pAttribute);
            } catch (Exception e) {
            }
    }

}
