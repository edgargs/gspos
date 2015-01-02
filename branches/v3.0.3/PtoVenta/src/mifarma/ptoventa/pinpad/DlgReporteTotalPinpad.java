package mifarma.ptoventa.pinpad;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import mifarma.ptoventa.pinpad.mastercard.HiloReporteTotalPinpadMC;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgReporteTotalPinpad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgReporteTotalPinpad.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanel pnlCuerpo = new JPanel();
    private JPanelTitle pnlMensaje = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelOrange lblProveedor = new JLabelOrange();
    private JLabelOrange lblTxtProveedor = new JLabelOrange();
    private JLabelWhite lblMensaje = new JLabelWhite();

    public DlgReporteTotalPinpad() {
        this(null, "", false);
    }

    public DlgReporteTotalPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(480, 221));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Reporte Total de Lote de Pinpad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.setBounds(new Rectangle(115, 50, 55, 50));
        pnlFondo.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                pnlFondo_focusLost(e);
            }
        });
        pnlFondo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlFondo_keyPressed(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(5, 10, 465, 25));
        pnlTitle.setFocusable(false);
        pnlCuerpo.setBounds(new Rectangle(5, 35, 465, 75));
        pnlCuerpo.setLayout(null);
        pnlCuerpo.setOpaque(false);
        pnlCuerpo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pnlCuerpo.setFocusable(false);
        pnlMensaje.setBounds(new Rectangle(5, 110, 465, 50));
        pnlMensaje.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(360, 165, 110, 25));
        lblEsc.setFocusable(false);
        lblTitle.setText("Reporte total de ultimas operaciones de Pinpad");
        lblTitle.setBounds(new Rectangle(20, 0, 275, 25));
        lblTitle.setFocusable(false);
        lblProveedor.setText("Proveedor:");
        lblProveedor.setBounds(new Rectangle(20, 15, 75, 25));
        lblProveedor.setFocusable(false);
        lblTxtProveedor.setText("Mastercard");
        lblTxtProveedor.setBounds(new Rectangle(100, 15, 160, 25));
        lblTxtProveedor.setFocusable(false);
        lblMensaje.setText("INICIANDO LA COMUNICACIÓN CON EL PINPAD");
        lblMensaje.setBounds(new Rectangle(0, 0, 465, 50));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFont(new Font("SansSerif", 1, 14));
        pnlMensaje.add(lblMensaje, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(pnlMensaje, null);
        pnlCuerpo.add(lblTxtProveedor, null);
        pnlCuerpo.add(lblProveedor, null);
        pnlFondo.add(pnlCuerpo, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowOpened(WindowEvent e) {
        if (UtilityPinpad.validarLibrerias()) {
            pnlFondo.grabFocus();
            reporteTotalPinpadMC();
        } else
            cerrarVentana(false);
    }

    private void pnlFondo_focusLost(FocusEvent e) {
        pnlFondo.grabFocus();
    }

    private void reporteTotalPinpadMC() {
        HiloReporteTotalPinpadMC hilo = new HiloReporteTotalPinpadMC();
        hilo.lblEsc = lblEsc;
        hilo.lblMensaje = lblMensaje;
        hilo.pnlMensaje = pnlMensaje;
        hilo.pnlFondo = pnlFondo;

        hilo.start();
    }

    private void chkkeyPressed(KeyEvent e) {
        if (lblEsc.isEnabled() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }
}
