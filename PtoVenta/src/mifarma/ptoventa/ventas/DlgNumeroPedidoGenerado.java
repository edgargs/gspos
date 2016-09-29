package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgNumeroPedidoGenerado extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgNumeroPedidoGenerado.class);

    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel lblNumeroPedido_T = new JLabel();
    private JLabel lblNumeroPedido = new JLabel();
    private JLabel lblMontoPedido = new JLabel();
    private JLabel lblMontoPedido_T = new JLabel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabel lblMensaje = new JLabel();
    private JLabel lblMensaje1 = new JLabel();

    public DlgNumeroPedidoGenerado() {
        this(null, "", false);
    }

    public DlgNumeroPedidoGenerado(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(391, 282));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedido Generado");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanel1.setBackground(Color.white);
        jPanel1.setLayout(null);
        lblNumeroPedido_T.setText("Número de Pedido:");
        lblNumeroPedido_T.setBounds(new Rectangle(25, 105, 200, 30));
        lblNumeroPedido_T.setFont(new Font("SansSerif", 1, 20));
        lblNumeroPedido_T.setBackground(Color.white);
        lblNumeroPedido_T.setForeground(new Color(255, 130, 14));
        lblNumeroPedido.setText("0043");
        lblNumeroPedido.setBounds(new Rectangle(280, 105, 75, 30));
        lblNumeroPedido.setFont(new Font("SansSerif", 1, 30));
        lblNumeroPedido.setBackground(Color.white);
        lblNumeroPedido.setForeground(new Color(43, 141, 39));
        lblNumeroPedido.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMontoPedido.setText("S/. 24.33");
        lblMontoPedido.setBounds(new Rectangle(180, 150, 180, 30));
        lblMontoPedido.setFont(new Font("SansSerif", 1, 30));
        lblMontoPedido.setBackground(Color.white);
        lblMontoPedido.setForeground(new Color(43, 141, 39));
        lblMontoPedido.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMontoPedido_T.setText("Monto Venta:");
        lblMontoPedido_T.setBounds(new Rectangle(25, 150, 200, 30));
        lblMontoPedido_T.setFont(new Font("SansSerif", 1, 20));
        lblMontoPedido_T.setBackground(Color.white);
        lblMontoPedido_T.setForeground(new Color(255, 130, 14));
        lblEnter.setBounds(new Rectangle(105, 195, 140, 35));
        lblEnter.setText("[ ENTER ] Aceptar");
        lblEnter.setFont(new Font("Arial Black", 0, 12));
        lblMensaje.setBounds(new Rectangle(20, 15, 350, 35));
        lblMensaje.setFont(new Font("SansSerif", 1, 17));
        lblMensaje.setBackground(Color.white);
        lblMensaje.setForeground(new Color(255, 5, 5));
        lblMensaje.setVisible(false);
        lblMensaje1.setBounds(new Rectangle(20, 60, 350, 35));
        lblMensaje1.setFont(new Font("SansSerif", 1, 17));
        lblMensaje1.setBackground(Color.white);
        lblMensaje1.setForeground(new Color(255, 5, 5));
        lblMensaje1.setVisible(false);
        jPanel1.add(lblMensaje1, null);
        jPanel1.add(lblMensaje, null);
        jPanel1.add(lblEnter, null);
        jPanel1.add(lblMontoPedido_T, null);
        jPanel1.add(lblMontoPedido, null);
        jPanel1.add(lblNumeroPedido, null);
        jPanel1.add(lblNumeroPedido_T, null);
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

        muestraInfoPedidoGenerado();
        if(!UtilityPtoVenta.isLocalVentaMayorista()){
            muestraMensajeActual();
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            cerrarVentana(true);
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

    private void muestraInfoPedidoGenerado() {
        lblNumeroPedido.setText(VariablesVentas.vNum_Ped_Diario);
        lblMontoPedido.setText(VariablesVentas.vVal_Neto_Ped);
    }

    private void btnPromocion_actionPerformed(ActionEvent e) {
    }

    private void txtPromocion_keyPressed(KeyEvent e) {
    }

    private void txtPromocion_keyReleased(KeyEvent e) {
    }

    private void muestraMensajeActual() {

        String msg = "00000000000000000000000000000000000000000000000000";
        String cadena = "";
        String[] arraymensajes = null;
        try {
            //DBVentas.listaPromociones(tableModelListaPromociones);
            cadena = DBVentas.obtieneMensajeActual();
            arraymensajes = cadena.split("Ã");
            msg = arraymensajes[0];
            if (msg.trim().length() > 0) {
                lblMensaje.setVisible(true);
                lblMensaje.setText(msg.trim());
            } else
                lblMensaje.setVisible(false);

            msg = arraymensajes[1];

            if (msg.trim().length() > 0) {
                lblMensaje1.setVisible(true);
                lblMensaje1.setText(msg.trim());
            } else
                lblMensaje1.setVisible(false);


        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener el mensaje.\n" +
                    sql.getMessage(), null);
        }

    }


}
