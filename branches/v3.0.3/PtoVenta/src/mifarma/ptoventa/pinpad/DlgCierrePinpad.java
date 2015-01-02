package mifarma.ptoventa.pinpad;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.pinpad.mastercard.HiloProcesoCierrePinpadMC;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgCierrePinpad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgCierrePinpad.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanel pnlCuerpo = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlMensajePinpad = new JPanelTitle();
    private JLabelWhite lblTitulo = new JLabelWhite();
    private JLabelOrange jLabel1 = new JLabelOrange();
    private JLabelOrange jLabel2 = new JLabelOrange();
    private JLabelWhite lblMensajePinpad = new JLabelWhite();
    private JPanelTitle pnlTituloRetorno = new JPanelTitle();
    private JPanel pnlCuerpoRetorno = new JPanel();
    private JLabelWhite lblTituloRetorno = new JLabelWhite();
    private JLabelOrange lblMensaje = new JLabelOrange();
    private JLabel lblDatoMensaje = new JLabel();

    public DlgCierrePinpad() {
        this(null, "", false);
    }

    public DlgCierrePinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(480, 322));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(0);
        this.setTitle("Cierre Pinpad Mastercard");
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
        pnlTitulo.setBounds(new Rectangle(5, 15, 460, 25));
        pnlTitulo.setFocusable(false);
        pnlCuerpo.setBounds(new Rectangle(5, 40, 460, 160));
        pnlCuerpo.setLayout(null);
        pnlCuerpo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pnlCuerpo.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(345, 260, 120, 30));
        lblEsc.setFocusable(false);
        lblEsc.setEnabled(false);
        pnlMensajePinpad.setBounds(new Rectangle(5, 200, 460, 55));
        pnlMensajePinpad.setFocusable(false);
        lblTitulo.setText("Datos de Cierre");
        lblTitulo.setBounds(new Rectangle(10, 0, 230, 25));
        lblTitulo.setFocusable(false);
        jLabel1.setText("Proveedor:");
        jLabel1.setBounds(new Rectangle(15, 15, 65, 20));
        jLabel1.setFocusable(false);
        jLabel2.setText("Mastercard");
        jLabel2.setBounds(new Rectangle(85, 15, 180, 20));
        jLabel2.setFocusable(false);
        lblMensajePinpad.setText("REALIZANDO LA COMUNICACI\u00d3N CON PINPAD");
        lblMensajePinpad.setBounds(new Rectangle(0, 0, 460, 55));
        lblMensajePinpad.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensajePinpad.setFocusable(false);
        lblMensajePinpad.setFont(new Font("SansSerif", 1, 14));
        pnlTituloRetorno.setBounds(new Rectangle(15, 60, 380, 25));
        pnlCuerpoRetorno.setBounds(new Rectangle(15, 85, 380, 55));
        pnlCuerpoRetorno.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pnlCuerpoRetorno.setLayout(null);
        lblTituloRetorno.setText("Datos retornados por el Pinpad");
        lblTituloRetorno.setBounds(new Rectangle(20, 0, 285, 20));
        lblMensaje.setText("Mensaje:");
        lblMensaje.setBounds(new Rectangle(25, 15, 70, 20));
        lblDatoMensaje.setBounds(new Rectangle(95, 15, 275, 20));
        pnlMensajePinpad.add(lblMensajePinpad, null);
        pnlFondo.add(pnlMensajePinpad, null);
        pnlFondo.add(lblEsc, null);
        pnlCuerpoRetorno.add(lblDatoMensaje, null);
        pnlCuerpoRetorno.add(lblMensaje, null);
        pnlTituloRetorno.add(lblTituloRetorno, null);
        pnlCuerpo.add(pnlCuerpoRetorno, null);
        pnlCuerpo.add(pnlTituloRetorno, null);
        pnlCuerpo.add(jLabel2, null);
        pnlCuerpo.add(jLabel1, null);
        pnlFondo.add(pnlCuerpo, null);
        pnlTitulo.add(lblTitulo, null);
        pnlFondo.add(pnlTitulo, null);
        this.getContentPane().add(pnlFondo, BorderLayout.CENTER);
        FarmaUtility.centrarVentana(this);
    }

    private void procesoCierrePinpadMC() {
        HiloProcesoCierrePinpadMC hilo = new HiloProcesoCierrePinpadMC();

        hilo.pnlMensajePinpad = pnlMensajePinpad;
        hilo.lblMensajePinpad = lblMensajePinpad;
        hilo.lblEsc = lblEsc;
        hilo.lblDatoMensaje = lblDatoMensaje;
        hilo.start();
    }

    private void this_windowOpened(WindowEvent e) {
        if (UtilityPinpad.validarLibrerias()) {
            pnlFondo.grabFocus();
            procesoCierrePinpadMC();
        } else
            cerrarVentana(false);
    }

    private void pnlFondo_focusLost(FocusEvent e) {
        pnlFondo.grabFocus();
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
