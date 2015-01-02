package mifarma.ptoventa.caja;

import java.awt.Frame;
import java.awt.Dimension;

import javax.swing.JDialog;

import java.awt.GridLayout;

import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import java.awt.Rectangle;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;

import java.awt.Font;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.event.ActionEvent;

import mifarma.common.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgInformacionTarjeta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * Paulo      06.04.2006   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */

public class DlgInformacionTarjeta extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgInformacionTarjeta.class);

    private Frame myParentFrame;
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JTextFieldSanSerif txtNumeroTarjeta = new JTextFieldSanSerif();
    private JButton btnPedido = new JButton();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */

    public DlgInformacionTarjeta() {
        this(null, "", false);
    }

    /**
     *Constructor
     *@param <b>parent</b> Objeto Frame de la Aplicación.
     *@param <b>title</b> Título de la Ventana.
     *@param <b>modal</b> Tipo de Ventana.
     */
    public DlgInformacionTarjeta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    /**
     *Implementa la Ventana con todos sus Objetos
     */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(301, 144));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Informacion Tarjeta");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 10, 270, 75));
        jLabelFunction1.setBounds(new Rectangle(195, 90, 85, 20));
        jLabelFunction1.setText("[ ESC ] Escape");
        txtNumeroTarjeta.setBounds(new Rectangle(60, 45, 145, 20));
        txtNumeroTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumeroTarjeta_keyPressed(e);
            }
        });
        btnPedido.setText("Deslice Tarjeta");
        btnPedido.setDefaultCapable(false);
        btnPedido.setRequestFocusEnabled(false);
        btnPedido.setBorderPainted(false);
        btnPedido.setFocusPainted(false);
        btnPedido.setContentAreaFilled(false);
        btnPedido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnPedido.setHorizontalAlignment(SwingConstants.LEFT);
        btnPedido.setMnemonic('d');
        btnPedido.setFont(new Font("SansSerif", 1, 22));
        btnPedido.setForeground(Color.white);
        btnPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedido_actionPerformed(e);
            }
        });
        btnPedido.setBounds(new Rectangle(55, 5, 155, 30));
        jPanelTitle1.add(btnPedido, null);
        jPanelTitle1.add(txtNumeroTarjeta, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jPanelTitle1, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void btnPedido_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumeroTarjeta);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtNumeroTarjeta_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNumeroTarjeta);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkkeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


}
