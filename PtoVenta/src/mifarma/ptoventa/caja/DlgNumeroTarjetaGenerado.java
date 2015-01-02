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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesVirtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgNumeroTarjetaGenerado extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgNumeroTarjetaGenerado.class);

    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel lblNumeroTarjeta_T = new JLabel();
    private JLabel lblNumeroTarjeta = new JLabel();
    private JLabel lblNumeroPin = new JLabel();
    private JLabel lblNumeroPin_T = new JLabel();
    private JLabelFunction lblEnter = new JLabelFunction();

    public DlgNumeroTarjetaGenerado() {
        this(null, "", false);
    }

    public DlgNumeroTarjetaGenerado(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(427, 185));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Tarjeta Virtual");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanel1.setBackground(Color.white);
        jPanel1.setLayout(null);
        lblNumeroTarjeta_T.setText("Número de Tarjeta:");
        lblNumeroTarjeta_T.setBounds(new Rectangle(20, 10, 200, 30));
        lblNumeroTarjeta_T.setFont(new Font("SansSerif", 1, 20));
        lblNumeroTarjeta_T.setBackground(Color.white);
        lblNumeroTarjeta_T.setForeground(new Color(255, 130, 14));
        lblNumeroTarjeta.setText("000000000000");
        lblNumeroTarjeta.setBounds(new Rectangle(245, 10, 150, 30));
        lblNumeroTarjeta.setFont(new Font("SansSerif", 1, 20));
        lblNumeroTarjeta.setBackground(Color.white);
        lblNumeroTarjeta.setForeground(new Color(43, 141, 39));
        lblNumeroTarjeta.setHorizontalAlignment(SwingConstants.LEFT);
        lblNumeroPin.setText("000000000000");
        lblNumeroPin.setBounds(new Rectangle(245, 55, 150, 30));
        lblNumeroPin.setFont(new Font("SansSerif", 1, 20));
        lblNumeroPin.setBackground(Color.white);
        lblNumeroPin.setForeground(new Color(43, 141, 39));
        lblNumeroPin.setHorizontalAlignment(SwingConstants.LEFT);
        lblNumeroPin_T.setText("Numero PIN:");
        lblNumeroPin_T.setBounds(new Rectangle(20, 55, 200, 30));
        lblNumeroPin_T.setFont(new Font("SansSerif", 1, 20));
        lblNumeroPin_T.setBackground(Color.white);
        lblNumeroPin_T.setForeground(new Color(255, 130, 14));
        lblEnter.setBounds(new Rectangle(140, 105, 140, 35));
        lblEnter.setText("[ ENTER ] Aceptar");
        lblEnter.setFont(new Font("Arial Black", 0, 12));
        jPanel1.add(lblEnter, null);
        jPanel1.add(lblNumeroPin_T, null);
        jPanel1.add(lblNumeroPin, null);
        jPanel1.add(lblNumeroTarjeta, null);
        jPanel1.add(lblNumeroTarjeta_T, null);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(this);
        muestraInfoTarjetaGenerado();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ENTER para cerrar la ventana.", this);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Debe anotar el número de tarjeta y pin generados.\nEstá seguro de continuar?")){
            cerrarVentana(true);
            //}
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void muestraInfoTarjetaGenerado() {
        lblNumeroTarjeta.setText(VariablesVirtual.vNumeroTarjeta);
        lblNumeroPin.setText(VariablesVirtual.vNumeroPin);
    }

}
