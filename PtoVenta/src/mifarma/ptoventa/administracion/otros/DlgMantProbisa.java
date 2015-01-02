package mifarma.ptoventa.administracion.otros;


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
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.otros.reference.DBProbisa;
import mifarma.ptoventa.administracion.otros.reference.VariablesProbisa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantProbisa.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.04.2007   Creación<br>
 * <br>
 * @author  Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class DlgMantProbisa extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantProbisa.class);

    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JButtonLabel btnCantTin = new JButtonLabel();
    private JLabelOrange lblCantRec_T = new JLabelOrange();
    private JLabelOrange lblCantAten_t = new JLabelOrange();
    private JTextFieldSanSerif txtCantTint = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCantRec = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCantAten = new JTextFieldSanSerif();
    private JLabelFunction lblFAceptar = new JLabelFunction();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblUsuario_t = new JLabelWhite();
    private JLabelWhite lblUsuario = new JLabelWhite();
    private JLabelWhite lblFecha_t = new JLabelWhite();
    private JLabelWhite lblFecha = new JLabelWhite();
    private JLabelOrange lblReg = new JLabelOrange();
    private JLabelOrange lblUsuReg = new JLabelOrange();
    private JTextFieldSanSerif txtFechaControl = new JTextFieldSanSerif();
    private JLabelOrange lblFechaControl = new JLabelOrange();

    public DlgMantProbisa(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(383, 284));
        this.setTitle("Mantenimiento PROBISA");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(10, 60, 355, 135));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        btnCantTin.setText("Cantidad de tinturaciones:");
        btnCantTin.setBounds(new Rectangle(30, 15, 160, 20));
        btnCantTin.setForeground(new Color(255, 140, 13));
        btnCantTin.setMnemonic('c');
        btnCantTin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantTin_actionPerformed(e);
            }
        });
        lblCantRec_T.setText("Cantidad de recetas entregadas:");
        lblCantRec_T.setBounds(new Rectangle(30, 45, 190, 20));
        lblCantAten_t.setText("Cantidad de atenciones realizadas:");
        lblCantAten_t.setBounds(new Rectangle(30, 75, 205, 20));
        txtCantTint.setBounds(new Rectangle(245, 15, 65, 20));
        txtCantTint.setLengthText(3);
        txtCantTint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantTint_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantTint_keyTyped(e);
            }
        });
        txtCantRec.setBounds(new Rectangle(245, 45, 65, 20));
        txtCantRec.setLengthText(3);
        txtCantRec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantRec_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantRec_keyTyped(e);
            }
        });
        txtCantAten.setBounds(new Rectangle(245, 75, 65, 20));
        txtCantAten.setLengthText(3);
        txtCantAten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantAten_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantAten_keyTyped(e);
            }
        });
        lblFAceptar.setBounds(new Rectangle(10, 230, 105, 20));
        lblFAceptar.setText("[ F11 ] Aceptar");
        lblFCerrar.setBounds(new Rectangle(270, 230, 95, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        jPanelTitle1.setBounds(new Rectangle(10, 10, 355, 40));
        lblUsuario_t.setText("Usuario:");
        lblUsuario_t.setBounds(new Rectangle(15, 10, 60, 20));
        lblUsuario.setBounds(new Rectangle(75, 10, 105, 20));
        lblFecha_t.setText("Fecha:");
        lblFecha_t.setBounds(new Rectangle(215, 10, 45, 20));
        lblFecha.setBounds(new Rectangle(260, 10, 70, 20));
        lblReg.setText("Registrado por:");
        lblReg.setBounds(new Rectangle(15, 205, 100, 20));
        lblReg.setForeground(Color.black);
        lblUsuReg.setBounds(new Rectangle(110, 205, 145, 20));
        lblUsuReg.setForeground(Color.black);
        txtFechaControl.setBounds(new Rectangle(85, 105, 80, 20));
        txtFechaControl.setLengthText(10);
        txtFechaControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaControl_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaControl_keyReleased(e);
            }
        });
        lblFechaControl.setText("Fecha:");
        lblFechaControl.setBounds(new Rectangle(30, 105, 55, 20));
        jPanelWhite1.add(lblFechaControl, null);
        jPanelWhite1.add(txtCantAten, null);
        jPanelWhite1.add(txtCantRec, null);
        jPanelWhite1.add(txtCantTint, null);
        jPanelWhite1.add(lblCantAten_t, null);
        jPanelWhite1.add(lblCantRec_T, null);
        jPanelWhite1.add(btnCantTin, null);
        jPanelWhite1.add(txtFechaControl, null);
        jPanelTitle1.add(lblFecha, null);
        jPanelTitle1.add(lblFecha_t, null);
        jPanelTitle1.add(lblUsuario, null);
        jPanelTitle1.add(lblUsuario_t, null);
        pnlBlanco.add(lblUsuReg, null);
        pnlBlanco.add(lblReg, null);
        pnlBlanco.add(jPanelTitle1, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(lblFAceptar, null);
        pnlBlanco.add(jPanelWhite1, null);
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        if (VariablesProbisa.vAccion == FarmaConstants.ACCION_INSERT) {
            lblReg.setVisible(false);
            lblUsuReg.setVisible(false);
            txtFechaControl.setEnabled(true);
        } else if (VariablesProbisa.vAccion == FarmaConstants.ACCION_UPDATE) {
            txtCantTint.setText(VariablesProbisa.vCantTint);
            txtCantAten.setText(VariablesProbisa.vCantAten);
            txtCantRec.setText(VariablesProbisa.vCantRec);
            txtFechaControl.setText(VariablesProbisa.vFecha);
            lblUsuReg.setText(VariablesProbisa.vUsuario);

            lblReg.setVisible(true);
            lblUsuReg.setVisible(true);
            txtFechaControl.setEnabled(false);
        }

        lblUsuario.setText(FarmaVariables.vIdUsu);
        try {
            lblFecha.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la fecha actual: \n" +
                    sql.getMessage(), txtCantTint);
        }
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************


    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantTint);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea salir?")) {
                this.cerrarVentana(false);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarDatos()) {
                if (VariablesProbisa.vAccion == FarmaConstants.ACCION_INSERT) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Está seguro que desea realizar la operación?"))
                        agregarControl();
                    else
                        FarmaUtility.showMessage(this, "Se canceló la operación.", txtCantTint);
                } else if (VariablesProbisa.vAccion == FarmaConstants.ACCION_UPDATE) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Está seguro que desea realizar la operación?"))
                        actualizarControl();
                    else
                        FarmaUtility.showMessage(this, "Se canceló la operación.", txtCantTint);
                }
            }
        }
    }

    private void txtCantTint_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantRec);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtCantRec_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantAten);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtCantAten_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFechaControl.isEnabled())
                FarmaUtility.moveFocus(txtFechaControl);
            else
                FarmaUtility.moveFocus(txtCantTint);
        } else {
            chkKeyPressed(e);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtCantTint_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantTint, e);
    }

    private void txtCantRec_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantRec, e);
    }

    private void txtCantAten_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantAten, e);
    }

    private void btnCantTin_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantTint);
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private boolean validarDatos() {
        VariablesProbisa.vCantTint = txtCantTint.getText().trim();
        VariablesProbisa.vCantAten = txtCantAten.getText().trim();
        VariablesProbisa.vCantRec = txtCantRec.getText().trim();
        VariablesProbisa.vFechaRegistro = txtFechaControl.getText().trim();

        if (VariablesProbisa.vCantTint.equalsIgnoreCase("") || VariablesProbisa.vCantAten.equalsIgnoreCase("") ||
            VariablesProbisa.vCantRec.equalsIgnoreCase("") || VariablesProbisa.vFechaRegistro.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Debe de ingresar todos los campos.", txtCantTint);
            return false;
        } else {
            return true;
        }
    }

    private void agregarControl() {
        try {
            DBProbisa.agregaControl(VariablesProbisa.vCantTint, VariablesProbisa.vCantAten, VariablesProbisa.vCantRec,
                                    VariablesProbisa.vFechaRegistro);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se realizó la operación satisfactoriamente.", txtCantAten);
            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            if (sql.getErrorCode() == 20200) {
                FarmaUtility.showMessage(this, "Ya existe un registro para el dia ingresado", txtCantTint);
            } else
                FarmaUtility.showMessage(this, "Error en BD: " + sql.getMessage(), txtCantTint);
        }
    }

    private void actualizarControl() {
        try {
            DBProbisa.actualizaControl(VariablesProbisa.vFecha, VariablesProbisa.vCantTint, VariablesProbisa.vCantRec,
                                       VariablesProbisa.vCantAten);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se realizó la operación satisfactoriamente.", txtCantAten);
            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error en BD: " + sql.getMessage(), txtCantTint);
        }
    }

    private void txtFechaControl_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantTint);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFechaControl_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaControl, e);
    }

    private boolean verificaFechaMax() {
        boolean retorno = false;
        try {
            String vResultado = DBProbisa.verificaFecMax();

            if (vResultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                retorno = true;
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al verificar fecha maxima: " + sql.getMessage(), txtCantTint);
        }
        return retorno;
    }

}
