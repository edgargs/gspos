package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
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

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.SwingConstants;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgCorregirGuia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.05.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgCorregirGuia extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgCorregirGuia.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextFieldSanSerif txtNumDocA = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumDocB = new JTextFieldSanSerif();
    private JButtonLabel btnDocumentoA = new JButtonLabel();
    private JButtonLabel btnDocumentoB = new JButtonLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblTipDoc_T = new JLabelWhite();
    private JLabelWhite lblTipDoc = new JLabelWhite();
    private JButtonLabel btnDocumentoA1 = new JButtonLabel();
    private JTextFieldSanSerif txtSerieDocA = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSerieDocB = new JTextFieldSanSerif();
    private JLabelOrange lblSepA = new JLabelOrange();
    private JLabelOrange lblSepB = new JLabelOrange();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgCorregirGuia() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgCorregirGuia(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Corregir Guía");
        this.setSize(new Dimension(314, 192));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(200, 135, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(85, 135, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        txtNumDocA.setBounds(new Rectangle(155, 70, 60, 20));
        txtNumDocA.setEnabled(false);
        txtNumDocB.setBounds(new Rectangle(155, 105, 60, 20));
        txtNumDocB.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumDocB_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNumDocB_keyTyped(e);
            }
        });
        btnDocumentoA.setText("Dice:");
        btnDocumentoA.setBounds(new Rectangle(10, 70, 75, 15));
        btnDocumentoA.setForeground(new Color(255, 130, 14));
        btnDocumentoB.setText("Debe decir:");
        btnDocumentoB.setBounds(new Rectangle(10, 105, 75, 15));
        btnDocumentoB.setForeground(new Color(255, 130, 14));
        btnDocumentoB.setMnemonic('D');
        btnDocumentoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDocumentoB_actionPerformed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 10, 285, 30));
        lblTipDoc_T.setText("Tipo:");
        lblTipDoc_T.setBounds(new Rectangle(15, 5, 35, 20));
        lblTipDoc.setBounds(new Rectangle(60, 5, 95, 20));
        btnDocumentoA1.setText("Nº Comprobante");
        btnDocumentoA1.setBounds(new Rectangle(95, 50, 95, 15));
        btnDocumentoA1.setForeground(new Color(255, 130, 14));
        txtSerieDocA.setBounds(new Rectangle(95, 70, 30, 20));
        txtSerieDocB.setBounds(new Rectangle(95, 105, 30, 20));
        lblSepA.setText("-");
        lblSepA.setBounds(new Rectangle(130, 70, 20, 20));
        lblSepA.setHorizontalAlignment(SwingConstants.CENTER);
        lblSepA.setFont(new Font("SansSerif", 1, 25));
        lblSepB.setText("-");
        lblSepB.setBounds(new Rectangle(130, 105, 20, 20));
        lblSepB.setHorizontalAlignment(SwingConstants.CENTER);
        lblSepB.setFont(new Font("SansSerif", 1, 25));
        jPanelTitle1.add(lblTipDoc, null);
        jPanelTitle1.add(lblTipDoc_T, null);
        jContentPane.add(lblSepB, null);
        jContentPane.add(lblSepA, null);
        jContentPane.add(txtSerieDocB, null);
        jContentPane.add(txtSerieDocA, null);
        jContentPane.add(btnDocumentoA1, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(btnDocumentoB, null);
        jContentPane.add(btnDocumentoA, null);
        jContentPane.add(txtNumDocB, null);
        jContentPane.add(txtNumDocA, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        txtSerieDocB.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSerieDocB_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtSerieDocB_keyTyped(e);
            }
        });
        txtSerieDocA.setLengthText(3);
        txtNumDocA.setLengthText(7);
        txtSerieDocB.setLengthText(3);
        txtNumDocB.setLengthText(7);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initComprobante();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initComprobante() {
        String numero = VariablesCaja.vNumComp.trim();
        lblTipDoc.setText(VariablesCaja.vDesComp);
        txtSerieDocA.setText(numero.substring(0, 3));
        txtNumDocA.setText(numero.substring(4));
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtSerieDocB);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnDocumentoB_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSerieDocB);
    }

    private void txtSerieDocB_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumDocB);
            txtSerieDocB.setText(FarmaUtility.completeWithSymbol(txtSerieDocB.getText(), 3, "0", "I"));
        } else
            chkKeyPressed(e);
    }

    private void txtSerieDocB_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtSerieDocB, e);
    }

    private void txtNumDocB_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtSerieDocB);
            txtNumDocB.setText(FarmaUtility.completeWithSymbol(txtNumDocB.getText(), 7, "0", "I"));
        } else
            chkKeyPressed(e);
    }

    private void txtNumDocB_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumDocB, e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarCampos()) {
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Está seguro de cambiar el Número de Guía?"))
                    if (grabar()) {
                        FarmaUtility.showMessage(this, "Se cambió el Número de Guía", null);
                        cerrarVentana(true);
                    }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private boolean validarCampos() {
        boolean retorno = true;

        txtSerieDocB.setText(FarmaUtility.completeWithSymbol(txtSerieDocB.getText(), 3, "0", "I"));
        txtNumDocB.setText(FarmaUtility.completeWithSymbol(txtNumDocB.getText(), 7, "0", "I"));

        if (txtSerieDocB.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe ingresar la Serie del Documento B.", txtSerieDocB);
            retorno = false;
        } else if (txtSerieDocB.getText().equals("000") || txtSerieDocB.getText().trim().length() != 3) {
            FarmaUtility.showMessage(this, "Debe ingresar la Serie del Documento B, correctamente.", txtSerieDocB);
            retorno = false;
        } else if (txtNumDocB.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe ingresar el Numero del Documento B.", txtNumDocB);
            retorno = false;
        } else if (txtNumDocB.getText().trim().equals("0000000") || txtNumDocB.getText().trim().length() != 7) {
            FarmaUtility.showMessage(this, "Debe ingresar el Numero del Documento B, correctamente.", txtNumDocB);
            retorno = false;
        }

        return retorno;
    }

    private boolean grabar() {
        boolean retorno;
        try {
            DBInventario.corregirGuia(VariablesInventario.vNumNotaEs, VariablesCaja.vSecComprobante,
                                      VariablesCaja.vNumComp, txtSerieDocB.getText() + txtNumDocB.getText(),
                                      VariablesCaja.vTipComp);
            FarmaUtility.aceptarTransaccion();
            retorno = true;
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            retorno = false;

            if (e.getErrorCode() == 20032)
                FarmaUtility.showMessage(this, "No se encuentra el Documento B. Verifique!", txtNumDocB);
            else if (e.getErrorCode() == 20033)
                FarmaUtility.showMessage(this, "Los documentos deben estar en el rango de días válidos para corregir.",
                                         txtNumDocB);
            else {
                log.error("", e);
                FarmaUtility.showMessage(this, "Error al grabar corregir numero de comprobante :\n" +
                        e.getMessage(), txtNumDocA);
            }
        }
        return retorno;
    }

}
