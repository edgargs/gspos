package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngCodBarraNegativa extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngCodBarraNegativa.class);

    private JPanel jContentPane = new JPanel();
    private JLabelWhite jPanelTitle1 = new JLabelWhite();
    private JButtonLabel btnCodBarra = new JButtonLabel();
    private JTextFieldSanSerif txtCodBarra = new JTextFieldSanSerif();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel lblNombreProducto = new JLabel();
    private JLabel lblMensaje = new JLabel();
    private JLabel lblCantidadVeces = new JLabel();
    //JLabelWhite

    private String pCodProdDlg;
    private String pDescripcionProdDlg;
    private int pCantVecesDlg = 0;
    private int pcantVecesCarga = 0;

    public DlgIngCodBarraNegativa(Frame parent, String title, boolean modal, String pCodProd, String pDescripcionProd,
                                  int pCantVeces) {
        super(parent, title, modal);
        pCodProdDlg = pCodProd;
        pDescripcionProdDlg = pDescripcionProd;
        pCantVecesDlg = pCantVeces;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(369, 188));
        //jContentPane.setBounds(new Rectangle(0, 0, 470, 220));
        this.setTitle("Solicitud Ingreso Codigo de Barra");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(470, 220));
        jContentPane.setForeground(Color.white);
        //this.getContentPane().add(jContentPane, null);
        jContentPane.setBounds(new Rectangle(0, 0, 355, 195));
        jPanelTitle1.setBounds(new Rectangle(10, 10, 345, 150));
        btnCodBarra.setText("Cod. Barra:");
        btnCodBarra.setMnemonic('C');
        btnCodBarra.setBounds(new Rectangle(20, 65, 90, 20));
        btnCodBarra.setForeground(Color.black);
        btnCodBarra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCodBarra_actionPerformed(e);
            }
        });
        txtCodBarra.setBounds(new Rectangle(120, 65, 195, 20));

        txtCodBarra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtCodBarra_actionPerformed(e);
            }
        });
        txtCodBarra.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodBarra_keyPressed(e);
            }
        });
        lblAceptar.setBounds(new Rectangle(35, 115, 117, 20));
        lblEsc.setBounds(new Rectangle(190, 115, 117, 20));
        lblAceptar.setText("[ ENTER ] Aceptar");
        lblEsc.setText("[ ESC ] Cancelar");
        lblNombreProducto.setText(pDescripcionProdDlg);
        lblNombreProducto.setBounds(new Rectangle(0, 0, 350, 25));
        lblNombreProducto.setForeground(new Color(198, 0, 0));
        lblNombreProducto.setFont(new Font("Tahoma", 1, 13));
        lblMensaje.setText("Escanee el c\u00f3digo de barra de las " + pCantVecesDlg + " unidades.");
        lblMensaje.setBounds(new Rectangle(0, 35, 330, 25));
        lblMensaje.setFont(new Font("Tahoma", 1, 12));
        lblCantidadVeces.setText("0/" + pCantVecesDlg);
        lblCantidadVeces.setBounds(new Rectangle(270, 90, 70, 20));
        lblCantidadVeces.setBackground(new Color(198, 0, 0));
        lblCantidadVeces.setForeground(new Color(198, 0, 0));
        lblCantidadVeces.setFont(new Font("Tahoma", 1, 11));
        jPanelTitle1.add(lblCantidadVeces, null);
        jPanelTitle1.add(lblMensaje, null);
        jPanelTitle1.add(lblNombreProducto, null);
        jPanelTitle1.add(lblEsc, null);
        jPanelTitle1.add(lblAceptar, null);
        jPanelTitle1.add(txtCodBarra, null);
        jPanelTitle1.add(btnCodBarra, null);
        jContentPane.add(jPanelTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().setLayout(borderLayout1);

    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCodBarra);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void btnCodBarra_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodBarra);
    }

    private void txtCodBarra_actionPerformed(ActionEvent e) {
        //validaCodBarra();

    }

    private void txtCodBarra_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { //VERIFICA COD BARRA
            validaCodBarra();
            //cerrarVentana(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesVentas.bIndCodBarra = false;
            cerrarVentana(false);
        }
    }

    private void validaCodBarra() {
        //ERIOS 03.07.2013 Limpia la caja de texto
        limpiaCadenaAlfanumerica(txtCodBarra);
        String productoBuscar = txtCodBarra.getText().trim().toUpperCase();
        VariablesVentas.vCodigoBarra = productoBuscar;
        try {
            productoBuscar = DBVentas.obtieneCodigoProductoBarra();
            log.debug("producto: " + productoBuscar);
        } catch (Exception sql) {
            FarmaUtility.showMessage(this, "Error al Buscar Codigo de Barra." + sql, txtCodBarra);
        }

        if (productoBuscar.equalsIgnoreCase("000000")) {
            FarmaUtility.showMessage(this, "No existe producto relacionado con el Codigo de Barra. Verifique!!!",
                                     txtCodBarra);
        } else {
            if (pCodProdDlg.equalsIgnoreCase(productoBuscar)) {
                pcantVecesCarga++;
                if (pcantVecesCarga == pCantVecesDlg)
                    cerrarVentana(true);
                else {
                    lblCantidadVeces.setText(pcantVecesCarga + "/" + pCantVecesDlg);
                    txtCodBarra.setText("");
                }

            } else {
                FarmaUtility.showMessage(this,
                                         "El Código de Barra No Pertenece al Producto Seleccionado. Verifique!!!",
                                         txtCodBarra);
            }
        }
    }

}
