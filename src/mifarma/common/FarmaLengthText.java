package mifarma.common;

import javax.swing.text.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicaci�n : FarmaLengthText.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      07.01.2006   Creaci�n<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaLengthText extends javax.swing.text.PlainDocument {

    /** Almacena la longitud m�xima del contenido del Objeto JTextField */
    int maxLength;

    /**
     *Constructor : Inicializa la longitud del Objeto JTextField.
     *@param pMaxLength Longitud m�xima del contenido del Objeto.
     */
    public FarmaLengthText(int pMaxLength) {
        super();
        this.maxLength = pMaxLength;
    }

    /** 
     *Permite el ingreso de caracteres controlando no sobrepasar la longitud
     *m�xima del contenido del Objeto.
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
