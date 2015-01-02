package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgMensajeUsuario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      03.05.2007   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgMensajeUsuario extends JDialog {
    /* ************************************************************************** */
    /*                           DECLARACION PROPIEDADES                          */
    /* ************************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgMensajeUsuario.class);

    Frame myParentFrame;
    //FarmaTableModel tableModel;

    private String mensaje;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrPane2 = new JScrollPane();
    private JTextPane txtPane1 = new JTextPane();
    private JTextFieldSanSerif txtOculto = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnMensaje = new JButtonLabel();

    /* ************************************************************************** */
    /*                              CONSTRUCTORES                                 */
    /* ************************************************************************** */

    public DlgMensajeUsuario() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgMensajeUsuario(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************** */
    /*                              METODO jbInit                                 */
    /* ************************************************************************** */

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(498, 267));
        this.setDefaultCloseOperation(0);
        this.setTitle("Mensaje");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblF11.setBounds(new Rectangle(370, 210, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrPane2.setBounds(new Rectangle(15, 30, 460, 160));
        txtOculto.setBounds(new Rectangle(500, 10, 155, 20));
        txtOculto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtOculto_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(15, 10, 460, 20));
        btnMensaje.setText("Mensaje");
        btnMensaje.setBounds(new Rectangle(10, 0, 155, 20));
        btnMensaje.setMnemonic('M');
        btnMensaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMensaje_actionPerformed(e);
            }
        });
        scrPane2.getViewport().add(txtPane1, null);
        pnlTitle1.add(btnMensaje, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(txtOculto, null);
        jContentPane.add(scrPane2, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************** */
    /*                            METODO initialize                               */
    /* ************************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************** */
    /*                          METODOS INICIALIZACION                            */
    /* ************************************************************************** */

    /* ************************************************************************** */
    /*                            METODOS DE EVENTOS                              */
    /* ************************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtOculto);
        txtPane1.setText(mensaje);
        txtPane1.setEditable(false);
        //txtPane1.setEnabled(false);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnMensaje_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtOculto);
    }

    private void txtOculto_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************** */
    /*                       METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,1);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************** */
    /*                       METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************** */

    public void setMensaje(String pMensaje) {
        mensaje = pMensaje;
    }

}
