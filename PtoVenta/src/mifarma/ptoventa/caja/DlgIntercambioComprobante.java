package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIntercambioComprobante.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.05.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgIntercambioComprobante extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgIntercambioComprobante.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextFieldSanSerif txtNumDocA = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtMonDocA = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumDocB = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtMonDocB = new JTextFieldSanSerif();
    private JButtonLabel btnDocumentoA = new JButtonLabel();
    private JButtonLabel btnDocumentoB = new JButtonLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblTipDoc_T = new JLabelWhite();
    private JLabelWhite lblSerieDoc_T = new JLabelWhite();
    private JLabelWhite lblTipDoc = new JLabelWhite();
    private JLabelWhite lblSerieDoc = new JLabelWhite();
    private JButtonLabel btnDocumentoA1 = new JButtonLabel();
    private JButtonLabel btnDocumentoA2 = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgIntercambioComprobante() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgIntercambioComprobante(Frame parent, String title, boolean modal) {
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
        this.setTitle("Intercambiar Documento");
        this.setSize(new Dimension(314, 192));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
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
        txtNumDocA.setBounds(new Rectangle(95, 70, 95, 20));
        txtNumDocA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumDocA_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNumDocA_keyTyped(e);
            }
        });
        txtMonDocA.setBounds(new Rectangle(195, 70, 100, 20));
        txtMonDocA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonDocA_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonDocA_keyTyped(e);
            }
        });
        txtNumDocB.setBounds(new Rectangle(95, 105, 95, 20));
        txtNumDocB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumDocB_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNumDocB_keyTyped(e);
            }
        });
        txtMonDocB.setBounds(new Rectangle(195, 105, 100, 20));
        txtMonDocB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonDocB_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonDocB_keyTyped(e);
            }
        });
        btnDocumentoA.setText("Documento A");
        btnDocumentoA.setBounds(new Rectangle(10, 70, 75, 15));
        btnDocumentoA.setForeground(new Color(255, 130, 14));
        btnDocumentoA.setMnemonic('A');
        btnDocumentoA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDocumentoA_actionPerformed(e);
            }
        });
        btnDocumentoB.setText("Documento B");
        btnDocumentoB.setBounds(new Rectangle(10, 105, 75, 15));
        btnDocumentoB.setForeground(new Color(255, 130, 14));
        btnDocumentoB.setMnemonic('B');
        btnDocumentoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDocumentoB_actionPerformed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 10, 285, 30));
        lblTipDoc_T.setText("Tipo:");
        lblTipDoc_T.setBounds(new Rectangle(15, 5, 35, 20));
        lblSerieDoc_T.setText("Serie:");
        lblSerieDoc_T.setBounds(new Rectangle(180, 5, 35, 20));
        lblTipDoc.setBounds(new Rectangle(60, 5, 95, 20));
        lblSerieDoc.setBounds(new Rectangle(230, 5, 45, 20));
        btnDocumentoA1.setText("Nº Comprobante");
        btnDocumentoA1.setBounds(new Rectangle(95, 50, 95, 15));
        btnDocumentoA1.setForeground(new Color(255, 130, 14));
        btnDocumentoA1.setMnemonic('A');
        btnDocumentoA1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDocumentoA_actionPerformed(e);
            }
        });
        btnDocumentoA2.setText("Precio");
        btnDocumentoA2.setBounds(new Rectangle(200, 50, 75, 15));
        btnDocumentoA2.setForeground(new Color(255, 130, 14));
        btnDocumentoA2.setMnemonic('A');
        btnDocumentoA2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDocumentoA_actionPerformed(e);
            }
        });
        jPanelTitle1.add(lblSerieDoc, null);
        jPanelTitle1.add(lblTipDoc, null);
        jPanelTitle1.add(lblSerieDoc_T, null);
        jPanelTitle1.add(lblTipDoc_T, null);
        jContentPane.add(btnDocumentoA2, null);
        jContentPane.add(btnDocumentoA1, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(btnDocumentoB, null);
        jContentPane.add(btnDocumentoA, null);
        jContentPane.add(txtMonDocB, null);
        jContentPane.add(txtNumDocB, null);
        jContentPane.add(txtMonDocA, null);
        jContentPane.add(txtNumDocA, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        txtNumDocA.setDocument(new FarmaLengthText(7));
        txtNumDocB.setDocument(new FarmaLengthText(7));
        txtMonDocA.setDocument(new FarmaLengthText(10));
        txtMonDocB.setDocument(new FarmaLengthText(10));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initComprobante();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initComprobante() {
        String serie = VariablesCaja.vNumComp.substring(0, 3);
        String numero = VariablesCaja.vNumComp.substring(3);
        lblTipDoc.setText(VariablesCaja.vDesComp);
        lblSerieDoc.setText(serie);
        txtNumDocA.setText(numero);
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtNumDocA);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnDocumentoA_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumDocA);
    }

    private void btnDocumentoB_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumDocB);
    }

    private void txtNumDocA_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtMonDocA);
            txtNumDocA.setText(FarmaUtility.completeWithSymbol(txtNumDocA.getText(), 7, "0", "I"));
        } else
            chkKeyPressed(e);
    }

    private void txtNumDocA_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumDocA, e);
    }

    private void txtMonDocA_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumDocB);
        } else
            chkKeyPressed(e);
    }

    private void txtMonDocA_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonDocA, e);
    }

    private void txtNumDocB_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtMonDocB);
            txtNumDocB.setText(FarmaUtility.completeWithSymbol(txtNumDocB.getText(), 7, "0", "I"));
        } else
            chkKeyPressed(e);
    }

    private void txtNumDocB_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumDocB, e);
    }

    private void txtMonDocB_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumDocA);
        } else
            chkKeyPressed(e);
    }

    private void txtMonDocB_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonDocB, e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarCampos()) {
                if (grabar()) {
                    FarmaUtility.showMessage(this, "Se Intercambio Correctamente", null);
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
        if (txtNumDocA.getText().trim().length() < 7) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar el Numero del Documento A correctamente.", txtNumDocA);
        } else if (txtMonDocA.getText().trim().length() == 0) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar el Monto del Documento A.", txtMonDocA);
        } else if (!FarmaUtility.validateDecimal(this, txtMonDocA, "Debe ingresar un Monto Valido", true)) {
            retorno = false;
            //FarmaUtility.showMessage(this,"Debe ingresar el Numero del Documento A correctamente.",txtNumDocA);
        } else if (txtNumDocB.getText().trim().length() < 7) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar el Numero del Documento B correctamente.", txtNumDocB);
        } else if (txtMonDocB.getText().trim().length() == 0) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar el Monto del Documento B.", txtMonDocB);
        } else if (!FarmaUtility.validateDecimal(this, txtMonDocB, "Debe ingresar un Monto Valido", true)) {
            retorno = false;
            //FarmaUtility.showMessage(this,"Debe ingresar el Numero del Documento A correctamente.",txtNumDocA);
        }
        return retorno;
    }

    private boolean grabar() {
        boolean retorno = false;
        String secIni = lblSerieDoc.getText().trim() + txtNumDocA.getText().trim();
        String secFin = lblSerieDoc.getText().trim() + txtNumDocB.getText().trim();
        if (UtilityCaja.pValidaComprobantesProcesoSAP(secIni, secFin, this, txtNumDocA, myParentFrame)) {
            try {

                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro que desea efectuar el cambio")) {
                    DBCaja.intercambiarComprobante(lblSerieDoc.getText() + txtNumDocA.getText(), txtMonDocA.getText(),
                                                   lblSerieDoc.getText() + txtNumDocB.getText(), txtMonDocB.getText(),
                                                   VariablesCaja.vTipComp);
                    FarmaUtility.aceptarTransaccion();
                    retorno = true;
                }

            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                retorno = false;

                if (e.getErrorCode() == 20031)
                    FarmaUtility.showMessage(this, "No se encuentra el Documento A. Verifique!", txtNumDocA);
                else if (e.getErrorCode() == 20032)
                    FarmaUtility.showMessage(this, "No se encuentra el Documento B. Verifique!", txtNumDocB);
                else if (e.getErrorCode() == 20033)
                    FarmaUtility.showMessage(this,
                                             "Los documentos deben estar en el rango de días válidos para intercambiar.",
                                             txtNumDocA);
                else if (e.getErrorCode() == 20043)
                    FarmaUtility.showMessage(this, "No puede cambiar un pedido que es Convenio.", txtNumDocA);
                else if (e.getErrorCode() == 20044)
                    FarmaUtility.showMessage(this, "No puede cambiar un pedido que es Convenio.", txtNumDocA);
                else {
                    log.error("", e);
                    FarmaUtility.showMessage(this, "Error al grabar intercambio de comprobante :\n" +
                            e.getMessage(), txtNumDocA);
                }
            }
        }

        return retorno;
    }
}
