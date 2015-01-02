package mifarma.ptoventa.retiro;


import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.retiro.reference.DBRetiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProcRetiros <br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 07.12.2009 Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class DlgProcRetiros extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProcRetiros.class);

    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgProcRetiros() {
        this(null, "", false);
    }

    public DlgProcRetiros(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(235, 103));
        this.getContentPane().setLayout(null);
        this.setTitle("Procesando Información ...");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(0, 0, 230, 80));
        this.getContentPane().add(jPanelWhite1, null);
        FarmaUtility.centrarVentana(this);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        procesarRetiros();
    }

    public void procesarRetiros() {
        try {
            DBRetiro.procesarRetiros();
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Proceso finalizado exitosamente", null);
            cerrarVentana(true);
        } catch (SQLException f) {
            log.error("", f);
            FarmaUtility.showMessage(this, "ERROR en el proceso de retiro de VB: \n" +
                    f.getMessage(), null);
            FarmaUtility.liberarTransaccion();
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

}
