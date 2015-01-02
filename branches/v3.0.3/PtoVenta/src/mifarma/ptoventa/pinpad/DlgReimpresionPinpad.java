package mifarma.ptoventa.pinpad;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.pinpad.mastercard.HiloReimpresionVoucherPinpadMC;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgReimpresionPinpad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgReimpresionPinpad.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanel pnlCuerpo = new JPanel();
    private JLabelOrange lblNumReferencia = new JLabelOrange();
    private JLabelOrange lblTipoTarjeta = new JLabelOrange();
    private JLabelOrange lblTxtTipoTarjeta = new JLabelOrange();
    private JTextFieldSanSerif txtNumReferencia = new JTextFieldSanSerif();
    private JPanelTitle pnlMensaje = new JPanelTitle();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelWhite lblMensaje = new JLabelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();


    public DlgReimpresionPinpad() {
        this(null, "", false);
    }

    public DlgReimpresionPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(480, 244));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Reimpresión de Voucher de Pinpad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 465, 25));
        pnlTitle.setFocusable(false);
        pnlCuerpo.setBounds(new Rectangle(5, 35, 465, 90));
        pnlCuerpo.setLayout(null);
        pnlCuerpo.setOpaque(false);
        pnlCuerpo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pnlCuerpo.setFocusable(false);
        lblNumReferencia.setText("Num. Referencia:");
        lblNumReferencia.setBounds(new Rectangle(15, 50, 100, 20));
        lblNumReferencia.setFocusable(false);
        txtNumReferencia.setLengthText(4);
        txtNumReferencia.setBounds(new Rectangle(120, 50, 85, 20));
        txtNumReferencia.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNumReferencia_focusLost(e);
            }
        });
        txtNumReferencia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumReferencia_keyPressed(e);
            }
        });
        pnlMensaje.setBounds(new Rectangle(5, 125, 465, 55));
        pnlMensaje.setFocusable(false);
        lblTitle.setText("Reimprimir el voucher de pinpad");
        lblTitle.setBounds(new Rectangle(15, 0, 210, 25));
        lblTitle.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(370, 185, 100, 30));
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Reimprimir");
        lblF11.setBounds(new Rectangle(5, 185, 120, 30));
        lblF11.setFocusable(false);
        lblTxtTipoTarjeta.setText("Mastercard");
        lblTxtTipoTarjeta.setBounds(new Rectangle(120, 20, 125, 20));
        lblMensaje.setBounds(new Rectangle(0, 0, 465, 55));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFocusable(false);
        lblMensaje.setFont(new Font("SansSerif", 1, 14));
        lblTipoTarjeta.setText("Tipo Tarjeta:");
        lblTipoTarjeta.setBounds(new Rectangle(15, 20, 95, 20));
        pnlCuerpo.add(lblTxtTipoTarjeta, null);
        pnlCuerpo.add(lblTipoTarjeta, null);
        pnlCuerpo.add(txtNumReferencia, null);
        pnlCuerpo.add(lblNumReferencia, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlMensaje.add(lblMensaje, null);
        pnlFondo.add(pnlMensaje, null);
        pnlFondo.add(pnlCuerpo, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void txtNumReferencia_focusLost(FocusEvent e) {
        txtNumReferencia.grabFocus();
    }

    private void this_windowOpened(WindowEvent e) {
        if (UtilityPinpad.validarLibrerias()) {
            txtNumReferencia.grabFocus();
        } else
            cerrarVentana(false);
    }

    private void txtNumReferencia_keyPressed(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumReferencia, e);
        chkkeyPressed(e);
    }

    private void chkkeyPressed(KeyEvent e) {
        if (lblF11.isEnabled() && UtilityPtoVenta.verificaVK_F11(e)) {
            reimpresionVoucherPinpadMC();
        }
        if (lblEsc.isEnabled() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void reimpresionVoucherPinpadMC() {
        //lblF11.setEnabled(false);
        lblEsc.setEnabled(false);
        txtNumReferencia.setEnabled(false);
        lblMensaje.setText("INICIANDO LA COMUNICACIÓN CON EL PINPAD");

        HiloReimpresionVoucherPinpadMC hilo = new HiloReimpresionVoucherPinpadMC();
        hilo.lblF11 = lblF11;
        hilo.lblEsc = lblEsc;
        hilo.numReferencia = txtNumReferencia.getText();
        hilo.lblMensajePinpad = lblMensaje;
        hilo.pnlMensajePinpad = pnlMensaje;
        hilo.pnlFondo = pnlFondo;
        hilo.txtNumReferencia = txtNumReferencia;

        hilo.start();
    }
}
