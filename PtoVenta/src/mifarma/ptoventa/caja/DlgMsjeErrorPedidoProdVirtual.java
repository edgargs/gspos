package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;

public class DlgMsjeErrorPedidoProdVirtual extends JDialog {
    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel pnlMensaje = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();

    public DlgMsjeErrorPedidoProdVirtual() {
        this(null, "", false);
    }

    public DlgMsjeErrorPedidoProdVirtual(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(527, 224));
        this.getContentPane().setLayout(cardLayout1);
        this.setResizable(false);
        this.setTitle("Recarga Virtual");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        pnlFondo.setFocusable(false);
        lblF11.setText("[ F11 ] Continuar");
        lblF11.setBounds(new Rectangle(350, 160, 150, 30));
        lblF11.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblF11_keyPressed(e);
                }
            });
        pnlMensaje.setBounds(new Rectangle(15, 10, 485, 140));
        pnlMensaje.setLayout(null);
        pnlMensaje.setBackground(Color.red);
        pnlMensaje.setFocusable(false);
        jLabel1.setText("ERROR: No se pudo realizar la recarga virtual");
        jLabel1.setBounds(new Rectangle(0, 15, 485, 35));
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setFont(new Font("Microsoft Sans Serif", 1, 20));
        jLabel1.setForeground(Color.white);
        jLabel2.setText("");
        jLabel2.setBounds(new Rectangle(5, 55, 475, 20));
        jLabel2.setFont(new Font("SansSerif", 1, 14));
        jLabel2.setForeground(Color.white);
        jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("Si se realizo el pago con tarjeta, es obligatorio realizar");
        jLabel3.setBounds(new Rectangle(5, 85, 475, 20));
        jLabel3.setFont(new Font("SansSerif", 1, 14));
        jLabel3.setForeground(Color.white);
        jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText("la anulación de la transacción");
        jLabel4.setBounds(new Rectangle(5, 105, 475, 20));
        jLabel4.setFont(new Font("SansSerif", 1, 14));
        jLabel4.setForeground(Color.white);
        jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        pnlMensaje.add(jLabel4, null);
        pnlMensaje.add(jLabel3, null);
        pnlMensaje.add(jLabel2, null);
        pnlMensaje.add(jLabel1, null);
        pnlFondo.add(pnlMensaje, null);
        pnlFondo.add(lblF11, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void lblF11_keyPressed(KeyEvent e)
    {   if(UtilityPtoVenta.verificaVK_F11(e))
            cerrarVentana(true);
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

    private void this_windowOpened(WindowEvent e)
    {   lblF11.grabFocus();
    }
}
