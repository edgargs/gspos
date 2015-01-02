package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPanel;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngreseCodBarra extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngreseCodBarra.class);

    private JPanel jContentPane = new JPanel();
    private JLabelWhite jPanelTitle1 = new JLabelWhite();
    private JButtonLabel btnCodBarra = new JButtonLabel();
    private JTextFieldSanSerif txtCodBarra = new JTextFieldSanSerif();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    //JLabelWhite

    public DlgIngreseCodBarra() {
        this(null, "", false);
    }

    public DlgIngreseCodBarra(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(369, 159));
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
        jPanelTitle1.setBounds(new Rectangle(10, 10, 345, 115));
        btnCodBarra.setText("Cod. Barra:");
        btnCodBarra.setMnemonic('C');
        btnCodBarra.setBounds(new Rectangle(20, 30, 90, 20));
        btnCodBarra.setForeground(Color.black);
        btnCodBarra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCodBarra_actionPerformed(e);
            }
        });
        txtCodBarra.setBounds(new Rectangle(120, 30, 195, 20));

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
        lblAceptar.setBounds(new Rectangle(35, 75, 117, 20));
        lblEsc.setBounds(new Rectangle(190, 75, 117, 20));
        lblAceptar.setText("[ ENTER ] Aceptar");
        lblEsc.setText("[ ESC ] Cancelar");
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
        log.debug("varVentas: " + VariablesVentas.vCodigoBarra);
        try {
            productoBuscar = DBVentas.obtieneCodigoProductoBarra();
            log.debug("producto: " + productoBuscar);
        } catch (Exception sql) {
            FarmaUtility.showMessage(this, "Error al Buscar Codigo de Barra." + sql, txtCodBarra);
        }

        if (productoBuscar.equalsIgnoreCase("000000")) {
            FarmaUtility.showMessage(this, "No existe producto relacionado con el Codigo de Barra. Verifique!!!",
                                     txtCodBarra);
            VariablesVentas.bIndCodBarra = false;
        } else {
            log.debug("vvVCod_prod: " + VariablesVentas.vCod_Prod);
            if (VariablesVentas.vCod_Prod.equalsIgnoreCase(productoBuscar)) {
                cerrarVentana(true);
                VariablesVentas.bIndCodBarra = true;
            } else {
                FarmaUtility.showMessage(this,
                                         "El Código de Barra No Pertenece al Producto Seleccionado. Verifique!!!",
                                         txtCodBarra);
            }
        }
    }

}
