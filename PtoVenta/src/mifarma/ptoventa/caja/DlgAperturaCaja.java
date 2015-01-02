package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mifarma.common.FarmaUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAperturaCaja extends JDialog {

    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgAperturaCaja.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel9 = new JLabel();

    public DlgAperturaCaja() {
        this(null, "", false);
    }

    public DlgAperturaCaja(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(384, 225));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Apertura de Caja");
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setBounds(new Rectangle(5, 5, 380, 255));
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(382, 195));
        jContentPane.setLayout(null);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(270, 165, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(160, 165, 100, 20));
        jPanel2.setBounds(new Rectangle(20, 45, 340, 105));
        jPanel2.setBackground(Color.white);
        jPanel2.setBorder(BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(null);
        jLabel8.setText("1");
        jLabel8.setBounds(new Rectangle(225, 40, 35, 20));
        jLabel8.setFont(new Font("SansSerif", 1, 12));
        jLabel8.setForeground(new Color(43, 141, 39));
        jLabel7.setText("12/01/2006 10:15:50");
        jLabel7.setBounds(new Rectangle(85, 75, 165, 15));
        jLabel7.setFont(new Font("SansSerif", 1, 12));
        jLabel7.setForeground(new Color(43, 141, 39));
        jLabel6.setText("1");
        jLabel6.setBounds(new Rectangle(85, 45, 40, 15));
        jLabel6.setFont(new Font("SansSerif", 1, 12));
        jLabel6.setForeground(new Color(43, 141, 39));
        jLabel5.setText("Andres Moreno");
        jLabel5.setBounds(new Rectangle(85, 15, 245, 20));
        jLabel5.setFont(new Font("SansSerif", 1, 12));
        jLabel5.setForeground(new Color(43, 141, 39));
        jLabel4.setText("Turno :");
        jLabel4.setBounds(new Rectangle(155, 45, 65, 15));
        jLabel4.setFont(new Font("SansSerif", 1, 12));
        jLabel4.setForeground(new Color(255, 130, 14));
        jLabel3.setText("Fecha :");
        jLabel3.setBounds(new Rectangle(15, 75, 80, 15));
        jLabel3.setFont(new Font("SansSerif", 1, 12));
        jLabel3.setForeground(new Color(255, 130, 14));
        jLabel2.setText("Caja :");
        jLabel2.setBounds(new Rectangle(15, 45, 80, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 12));
        jLabel2.setForeground(new Color(255, 130, 14));
        jLabel1.setText("Usuario :");
        jLabel1.setBounds(new Rectangle(15, 15, 60, 20));
        jLabel1.setFont(new Font("SansSerif", 1, 12));
        jLabel1.setForeground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(20, 15, 340, 30));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        jLabel9.setText("Datos de Usuario y Caja");
        jLabel9.setBounds(new Rectangle(10, 5, 160, 20));
        jLabel9.setFont(new Font("SansSerif", 1, 11));
        jLabel9.setForeground(Color.white);
        jPanel2.add(jLabel8, null);
        jPanel2.add(jLabel7, null);
        jPanel2.add(jLabel6, null);
        jPanel2.add(jLabel5, null);
        jPanel2.add(jLabel4, null);
        jPanel2.add(jLabel3, null);
        jPanel2.add(jLabel2, null);
        jPanel2.add(jLabel1, null);
        jPanel1.add(jLabel9, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jPanel2, null);
        jContentPane.add(jPanel1, null);

    }

    private void initialize() {

    };

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
    }

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            FarmaUtility.showMessage(this, "Caja Aperturara correctamente!", lblF11);
            this.setVisible(false);
        }
    }

}
