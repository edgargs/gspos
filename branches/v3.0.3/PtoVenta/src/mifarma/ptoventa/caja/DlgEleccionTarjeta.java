package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgEleccionTarjeta extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgEleccionTarjeta.class);

    private Map<String, String> formasPago = new HashMap<String, String>();
    private String tipoOper = "";
    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlHeader = new JPanelTitle();
    private JPanel pnlContenido = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelOrange lblNumTarjeta = new JLabelOrange();
    private JLabel lblDatoNumTarjeta = new JLabel();
    private JLabelOrange lblMarcaTarjeta = new JLabelOrange();
    private JComboBox cmbMarcaTarjeta = new JComboBox();
    private JLabelWhite lblMensaje1 = new JLabelWhite();
    private JLabelWhite lblMensaje2 = new JLabelWhite();

    public DlgEleccionTarjeta() {
        this(null, "", false);
    }

    public DlgEleccionTarjeta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(478, 246));
        this.getContentPane().setLayout(cardLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pnlFondo.setFocusable(false);
        pnlHeader.setBounds(new Rectangle(5, 5, 465, 55));
        pnlHeader.setFocusable(false);
        pnlContenido.setBounds(new Rectangle(5, 60, 465, 120));
        pnlContenido.setLayout(null);
        pnlContenido.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlContenido.setBackground(Color.white);
        pnlContenido.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(350, 185, 120, 30));
        lblEsc.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblF11.setText("[ Enter / F11] Aceptar");
        lblF11.setBounds(new Rectangle(5, 185, 135, 30));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setHorizontalTextPosition(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        lblNumTarjeta.setText("Numero Tarjeta:");
        lblNumTarjeta.setBounds(new Rectangle(25, 20, 100, 20));
        lblNumTarjeta.setFocusable(false);
        lblDatoNumTarjeta.setText("---");
        lblDatoNumTarjeta.setBounds(new Rectangle(135, 20, 275, 20));
        lblDatoNumTarjeta.setFocusable(false);
        lblMarcaTarjeta.setText("Operador Tarjeta:");
        lblMarcaTarjeta.setBounds(new Rectangle(25, 60, 100, 20));
        lblMarcaTarjeta.setFocusable(false);
        cmbMarcaTarjeta.setBounds(new Rectangle(135, 60, 295, 20));
        cmbMarcaTarjeta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbMarcaTarjeta_keyPressed(e);
            }
        });
        lblMensaje1.setText("No se pudo identificar el operador de la tarjeta ");
        lblMensaje1.setBounds(new Rectangle(20, 5, 425, 25));
        lblMensaje1.setFont(new Font("SansSerif", 1, 12));
        lblMensaje2.setText("Seleccione un operador del listado para continuar con el proceso de pago");
        lblMensaje2.setBounds(new Rectangle(20, 30, 430, 15));
        lblMensaje2.setFont(new Font("SansSerif", 1, 12));
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(pnlContenido, null);
        pnlContenido.add(cmbMarcaTarjeta, null);
        pnlContenido.add(lblMarcaTarjeta, null);
        pnlContenido.add(lblDatoNumTarjeta, null);
        pnlContenido.add(lblNumTarjeta, null);
        pnlHeader.add(lblMensaje2, null);
        pnlHeader.add(lblMensaje1, null);
        pnlFondo.add(pnlHeader, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    public void setValores(String numTarjeta, String tipoOper) {
        lblDatoNumTarjeta.setText(numTarjeta);
        this.tipoOper = tipoOper;
        cargarMarcasTarjeta();
    }
    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() { //cargarMarcasTarjeta();
    }

    private void cargarMarcasTarjeta() {
        if ("PINPAD".equalsIgnoreCase(tipoOper)) { //se cargan las marcas de tarjeta a procesar
            formasPago.put("VISA PINPAD", ConstantsPtoVenta.FORPAG_VISA_PINPAD);
            formasPago.put("MASTERCARD PINPAD", ConstantsPtoVenta.FORPAG_MC_PINPAD);
            //formasPago.put("Recaudación CMR","00086");
            formasPago.put("DINERS PINPAD", ConstantsPtoVenta.FORPAG_DINERS);
            formasPago.put("AMEX PINPAD", ConstantsPtoVenta.FORPAG_AMEX);
        } else if ("POS".equalsIgnoreCase(tipoOper)) {
            formasPago.put("VISA POS", ConstantsPtoVenta.FORPAG_VISA_POS);
            formasPago.put("MASTERCARD POS", ConstantsPtoVenta.FORPAG_MC_POS);
            //formasPago.put("Recaudación CMR","00086");
            formasPago.put("DINERS POS", ConstantsPtoVenta.FORPAG_DINNERS_POS);
            formasPago.put("AMEX POS", ConstantsPtoVenta.FORPAG_AMEX_POS);
        }

        Set<String> keys = formasPago.keySet();
        for (String k : keys) {
            cmbMarcaTarjeta.addItem(k);
        }
    }

    private boolean procesar() {
        return true;
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkkeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F11(e) || e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (procesar())
                cerrarVentana(true);
        }
    }

    private void cmbMarcaTarjeta_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public String getFormaPago() {
        String formaPago = "";
        try {
            formaPago = formasPago.get(cmbMarcaTarjeta.getSelectedItem());
        } catch (Exception e) {
            log.error("No existe forma de pago", e);
        }
        return formaPago;
    }

    public String getDescFormaPago() {
        return (String)cmbMarcaTarjeta.getSelectedItem();
    }
}
