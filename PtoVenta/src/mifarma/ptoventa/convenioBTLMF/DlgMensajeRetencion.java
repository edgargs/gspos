package mifarma.ptoventa.convenioBTLMF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mifarma.common.FarmaSecurity;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMensajeRetencion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez Calderon<br>
 * @version 1.0<br>
 *
 */

public class DlgMensajeRetencion extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMensajeRetencion.class);

    FarmaSecurity vSecurityLogin;
    int parametro = -1;
    JLabel lblParametro;

    JTextField txtUsuario = new JTextField();
    JPasswordField txtClave = new JPasswordField();
    JButton btnAceptar = new JButton();
    JLabel lblClave = new JLabel();

    JPanel pnlArriba = new JPanel();
    JPanel pnlDerecha = new JPanel();
    JEditorPane jEditorPaneDec = new JEditorPane();
    String htmlDerecho = "";

    Dimension pantalla = null;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMensajeRetencion() {
        this(null, "", false);
    }

    Frame myParentFrame;

    public DlgMensajeRetencion(Frame parent, String title, boolean modal) {
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

    private void jbInit() throws Exception {
        this.setFocusable(true);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

        });

        pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        pnlArriba.setBackground(Color.BLUE);
        pnlArriba.setLayout(new GridLayout(1, 0));
        pnlArriba.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlArriba_keyPressed(e);
            }
        });

        jEditorPaneDec.setContentType("text/html");
        jEditorPaneDec.setText(htmlDerecho);
        jEditorPaneDec.setEditable(false);
        jEditorPaneDec.setBorder(BorderFactory.createLineBorder(Color.RED, 9));


        pnlDerecha.setLayout(new GridBagLayout());
        pnlDerecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlDerecha_keyPressed(e);
            }
        });
        jEditorPaneDec.setLayout(new GridLayout());
        jEditorPaneDec.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jEditorPaneDec_keyPressed(e);
            }
        });
        pnlDerecha.add(jEditorPaneDec);
        pnlArriba.add(pnlDerecha);
        this.getContentPane().add(pnlArriba);
        //Panel Abajo
        this.setBounds(new Rectangle(0, 0, 485, 570));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setModal(true);

    }
    // **************************************************************************
    // Initialize
    // **************************************************************************

    private void initialize() {
        // TODO Auto-generated method stub
        cargaMensajes();

    }
    // **************************************************************************
    // Open y Close
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        jEditorPaneDec.requestFocus();
    }

    private void this_windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    // **************************************************************************
    // KEY PRESSED
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        log.debug("tecla  +  " + e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            cerrarVentana(true);
        }
    }

    private void pnlArriba_keyPressed(KeyEvent e) {
        log.debug("tecla Arriba +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void pnlDerecha_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneDec_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    // **************************************************************************
    // LOGICA DE NEGOCIO
    // **************************************************************************

    public void cargaMensajes() {
        String vNombreBenificiario = VariablesConvenioBTLMF.vNombre + " " + VariablesConvenioBTLMF.vApellidoPat;
        htmlDerecho =
                UtilityConvenioBTLMF.obtieneDocVerificacion(VariablesConvenioBTLMF.vCodConvenio, ConstantsConvenioBTLMF.FLG_DOC_RETENCION,
                                                            vNombreBenificiario, this, null);
        htmlDerecho = htmlDerecho.replaceAll(ConstantsConvenioBTLMF.PANTALLA_IZQ_PIXEL_ANCHO, "350" + "px");

        htmlDerecho = htmlDerecho.replaceAll(ConstantsConvenioBTLMF.PANTALLA_IZQ_PIXEL_ALTO, "310" + "px");
        jEditorPaneDec.setText(htmlDerecho);
    }

}
