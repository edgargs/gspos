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

import mifarma.ptoventa.pinpad.mastercard.HiloReporteDetalladoPinpadMC;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgReporteDetalladoPinpad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgReporteDetalladoPinpad.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanel pnlCuerpo = new JPanel();
    private JPanelTitle pnlMensaje = new JPanelTitle();
    private JLabelWhite lblMensaje = new JLabelWhite();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelOrange lblProveedor = new JLabelOrange();
    private JLabelOrange lblTxtProveedor = new JLabelOrange();

    public DlgReporteDetalladoPinpad() {
        this(null, "", false);
    }

    public DlgReporteDetalladoPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(480, 216));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Reporte Detallado de Lote de Pinpad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
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
        //pnlCuerpo.setSize(new Dimension(315, 75));
        pnlCuerpo.setFocusable(false);
        pnlMensaje.setBounds(new Rectangle(5, 110, 465, 50));
        pnlMensaje.setFocusable(false);
        lblMensaje.setText("INICIANDO LA COMUNICACIÓN CON EL PINPAD");
        lblMensaje.setBounds(new Rectangle(0, 0, 465, 50));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFocusable(false);
        lblMensaje.setFont(new Font("SansSerif", 1, 14));
        lblTitle.setText("Reporte detallado de ultimas operaciones de Pinpad");
        lblTitle.setBounds(new Rectangle(15, 0, 310, 25));
        lblTitle.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(360, 165, 110, 25));
        lblEsc.setSize(new Dimension(110, 25));
        lblEsc.setFocusable(false);
        lblProveedor.setText("Proveedor:");
        lblProveedor.setBounds(new Rectangle(15, 15, 75, 25));
        lblTxtProveedor.setText("Mastercard");
        lblTxtProveedor.setBounds(new Rectangle(95, 15, 160, 25));
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
            reporteDetalladoPinpadMC();
        } else
            cerrarVentana(false);
    }

    private void pnlFondo_focusLost(FocusEvent e) {
        pnlFondo.grabFocus();
    }

    private void reporteDetalladoPinpadMC() {
        HiloReporteDetalladoPinpadMC hilo = new HiloReporteDetalladoPinpadMC();
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
