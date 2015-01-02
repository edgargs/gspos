package mifarma.ptoventa.pinpad;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.pinpad.mastercard.HiloReimpresionLotePinpadMC;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgReimpresionLotePinpad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgReimpresionLotePinpad.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanel jPanel1 = new JPanel();
    private JPanelTitle pnlMensaje = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelOrange lblProveedor = new JLabelOrange();
    private JLabelOrange lblDatoProveedor = new JLabelOrange();
    private JLabelOrange lblNumLote = new JLabelOrange();
    private JTextFieldSanSerif txtNumLote = new JTextFieldSanSerif();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelWhite lblMensaje = new JLabelWhite();

    public DlgReimpresionLotePinpad() {
        this(null, "", false);
    }

    public DlgReimpresionLotePinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(480, 303));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Reimpresión de Lote");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(10, 15, 460, 30));
        pnlTitle.setFocusable(false);
        jPanel1.setBounds(new Rectangle(10, 45, 460, 125));
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.white);
        jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        jPanel1.setFocusable(false);
        pnlMensaje.setBounds(new Rectangle(10, 170, 460, 65));
        pnlMensaje.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(345, 240, 125, 35));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Reimprimir");
        lblF11.setBounds(new Rectangle(10, 240, 125, 35));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        lblProveedor.setText("Proveedor:");
        lblProveedor.setBounds(new Rectangle(25, 25, 65, 20));
        lblProveedor.setFocusable(false);
        lblNumLote.setText("Num. Lote:");
        lblNumLote.setBounds(new Rectangle(25, 70, 85, 20));
        lblNumLote.setFocusable(false);
        txtNumLote.setBounds(new Rectangle(115, 70, 65, 20));
        txtNumLote.setLengthText(4);
        txtNumLote.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNumLote_focusLost(e);
            }
        });
        txtNumLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumLote_keyPressed(e);
            }
        });
        lblDatoProveedor.setText("Mastercard");
        lblDatoProveedor.setBounds(new Rectangle(115, 25, 165, 20));
        lblDatoProveedor.setFocusable(false);
        lblTitle.setText("Reimpresión de Lote");
        lblTitle.setBounds(new Rectangle(15, 0, 310, 30));
        lblTitle.setFocusable(false);
        lblMensaje.setBounds(new Rectangle(0, 0, 460, 65));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFocusable(false);
        lblMensaje.setFont(new Font("SansSerif", 1, 14));
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlMensaje.add(lblMensaje, null);
        pnlFondo.add(pnlMensaje, null);
        jPanel1.add(lblDatoProveedor, null);
        jPanel1.add(txtNumLote, null);
        jPanel1.add(lblNumLote, null);
        jPanel1.add(lblProveedor, null);
        pnlFondo.add(jPanel1, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowOpened(WindowEvent e) {
        if (UtilityPinpad.validarLibrerias()) {
            txtNumLote.grabFocus();
        } else
            cerrarVentana(false);
    }

    private void txtNumLote_focusLost(FocusEvent e) {
        txtNumLote.grabFocus();
    }

    private void chkkeyPressed(KeyEvent e) {
        if (lblF11.isEnabled() && UtilityPtoVenta.verificaVK_F11(e)) {
            reimpresionLotePinpadMC();
        }
        if (lblEsc.isEnabled() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void txtNumLote_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void reimpresionLotePinpadMC() {
        lblF11.setEnabled(false);
        lblEsc.setEnabled(false);
        txtNumLote.setEnabled(false);
        lblMensaje.setText("INICIANDO LA COMUNICACIÓN CON EL PINPAD");

        HiloReimpresionLotePinpadMC hilo = new HiloReimpresionLotePinpadMC();
        hilo.lblF11 = lblF11;
        hilo.lblEsc = lblEsc;
        hilo.lblMensajePinpad = lblMensaje;
        hilo.pnlMensajePinpad = pnlMensaje;
        hilo.pnlFondo = pnlFondo;
        hilo.txtNumLote = txtNumLote;

        hilo.start();
    }
}
