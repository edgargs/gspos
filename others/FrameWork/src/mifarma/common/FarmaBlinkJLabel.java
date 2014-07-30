package mifarma.common;

import javax.swing.JLabel;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaBlinkJLabel.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaBlinkJLabel extends JLabel implements Runnable {

    /** Hilo que pondrá en visible e invisible el objeto JLabel */
    Thread t;

    /** Variable boolean que determina visibilidad del objeto */
    boolean visible = true;

    /**
     * Variable que determina el fin de la ejecución.
     */
    boolean terminar = false;

    /**
     *Constructor : Inicializa el objeto JLabel para controlar su visibilidad.
     */
    public FarmaBlinkJLabel() {
        super();
        t = new Thread(this);
        t.start();
    }

    /**
     *Constructor : Inicializa el objeto JLabel para controlar su visibilidad.
     *@param pText String que contiene el Texto a mostrar.
     */
    public FarmaBlinkJLabel(String pText) {
        super(pText);
        t = new Thread(this);
        t.start();
    }

    public void terminar() {
        terminar = true;
    }

    /**
     *Método ejecutable que determina la visibilidad o no del objeto JLabel.
     */
    public void run() {
        while (true && !terminar) {
            setVisible(visible);
            if (visible)
                visible = false;
            else
                visible = true;
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        System.out.println("Terminando FarmaBlinkJLabel.........");
    }

}
