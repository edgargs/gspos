package mifarma.ptoventa.administracion.fondoSencillo;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.fondoSencillo.reference.ConstantsFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.DBFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.VariablesFondoSencillo;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDevolverMontoFondoSencillo extends JDialog {
    //Declarando Variables Globales
    Frame myParentFrame;

    private static final Logger log = LoggerFactory.getLogger(DlgDevolverMontoFondoSencillo.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();

    private JButtonLabel btnAdmLocal = new JButtonLabel();
    private JButtonLabel btnMonto = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JLabelFunction btnAceptar = new JLabelFunction();
    private JLabelFunction btnSalir = new JLabelFunction();
    private JComboBox cmbAdmLocal = new JComboBox();
    private JLabel lblMensaje = new JLabel();
    private JLabel lblMensajeDev = new JLabel();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    public DlgDevolverMontoFondoSencillo() {
        this(null, "", false);
    }

    public DlgDevolverMontoFondoSencillo(Frame parent, String title, boolean modal) {
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
        //***Valores Básicos Pantalla ***
        this.setSize(new Dimension(404, 175));
        this.getContentPane().setLayout(borderLayout1);

        jContentPane.add(lblMensajeDev, null);
        jContentPane.add(lblMensaje, null);
        jContentPane.add(cmbAdmLocal, null);
        jContentPane.add(btnSalir, null);
        jContentPane.add(btnAceptar, null);
        jContentPane.add(txtMonto, null);
        jContentPane.add(btnMonto, null);
        jContentPane.add(btnAdmLocal, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Devolver Fondo de Sencillo");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        //***fin ***

        btnAdmLocal.setText("Adm. Local:");
        btnAdmLocal.setBounds(new Rectangle(10, 55, 75, 20));
        btnAdmLocal.setForeground(new Color(255, 132, 0));
        btnAdmLocal.setMnemonic('a');
        btnAdmLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAdmLocal_actionPerformed(e);
            }
        });
        btnMonto.setText("Importe: S/.");
        btnMonto.setBounds(new Rectangle(10, 85, 70, 15));
        btnMonto.setForeground(new Color(255, 132, 0));
        btnMonto.setMnemonic('i');
        btnMonto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMonto_actionPerformed(e);
            }
        });
        txtMonto.setBounds(new Rectangle(80, 80, 80, 20));
        txtMonto.setLengthText(7);
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtMonto_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonto_keyTyped(e);
            }
        });
        btnAceptar.setBounds(new Rectangle(75, 115, 117, 20));
        btnAceptar.setText("[ENTER] Aceptar");
        btnSalir.setBounds(new Rectangle(200, 115, 95, 20));
        btnSalir.setText("[ESC] Cerrar");
        cmbAdmLocal.setBounds(new Rectangle(80, 55, 305, 20));
        cmbAdmLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmbAdmLocal_actionPerformed(e);
            }
        });
        cmbAdmLocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbAdmLocal_keyPressed(e);
            }
        });
        lblMensaje.setText("Nuevos Soles");
        lblMensaje.setBounds(new Rectangle(165, 85, 130, 15));
        lblMensaje.setForeground(new Color(255, 132, 0));
        lblMensajeDev.setText("mensaje");
        lblMensajeDev.setBounds(new Rectangle(10, 10, 375, 35));
        lblMensajeDev.setForeground(new Color(231, 0, 0));
        lblMensajeDev.setFont(new Font("SansSerif", 1, 12));

    }

    private void initialize() {
        lblMensajeDev.setText("");
        lblMensajeDev.setText(VariablesFondoSencillo.vMensajeDevolver);
        initCombo();
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", cmbAdmLocal);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbAdmLocal);
        mostrarMontoAsignado(); //ASOSA, 18.06.2010
        txtMonto.setEnabled(false); //ASOSA, 18.06.2010
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            VariablesFondoSencillo.vMonto = "";
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) { //ASOSA, 18.06.2010
            e.consume();
            funcionF11();
        }
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    private void txtMonto_keyReleased(KeyEvent e) {
        chkKeyReleased(e);

    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbAdmLocal);
        }
        chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    private void btnMonto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMonto);
    }

    private boolean validaDatos() {
        boolean flag = true;
        if (txtMonto.getText().trim().length() < 0) {
            flag = false;
            FarmaUtility.showMessage(this, "No ha ingresado Monto.\n¡Verifique¡", txtMonto);
        } else if (!UtilityFondoSencillo.validaMonto(txtMonto.getText().trim())) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Monto Correcto.", txtMonto);
        } else if (!UtilityFondoSencillo.validaDecimal(txtMonto.getText().trim(), 4)) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Máximo 4 enteros.", txtMonto);
        } else if (cmbAdmLocal.getSelectedItem().toString().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Escoger un Administrador de Local.", cmbAdmLocal);
            flag = false;
        } else if (!validaMontoLimite(txtMonto.getText().trim())) {
            flag = false;
            FarmaUtility.showMessage(this, "El monto a devolver es mayor al monto asignado.", txtMonto);
        } else if (!UtilityFondoSencillo.validaMaximoDigitosEnteros(txtMonto.getText().trim(), 4)) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Máximo 4 dígitos enteros.", txtMonto);
        }

        return flag;
    }

    private void initCombo() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaLoadCVL.loadCVLFromSP(cmbAdmLocal, ConstantsFondoSencillo.NOM_HASTABLE_CMBADM_LOCAL,
                                   "PTOVENTA_ADMIN_FONDO_SEN.FONDO_SEN_F_CUR_LIS_QF(?,?)", parametros, true, true);
    }

    private void cmbAdmLocal_actionPerformed(ActionEvent e) {
        // FarmaUtility.moveFocus(txtMonto);
    }

    private void cmbAdmLocal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtMonto);
        }
        chkKeyPressed(e);
    }

    private void funcionF11() {
        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                        "¿Está seguro de confirmar la devolución?")) {
            if (validaDatos()) {
                VariablesFondoSencillo.vSecAdmLocal =
                        FarmaLoadCVL.getCVLCode(ConstantsFondoSencillo.NOM_HASTABLE_CMBADM_LOCAL,
                                                cmbAdmLocal.getSelectedIndex());

                log.debug("paso");
                log.debug("vSecFondoSen: " + VariablesFondoSencillo.vSecFondoSen + "\nvAdmLocal: " +
                          VariablesFondoSencillo.vSecAdmLocal + "\nvSecMovCajaCierre: " +
                          VariablesFondoSencillo.vSecMovCajaCierre + "\nMonto: " +
                          FarmaUtility.getDecimalNumber(txtMonto.getText()));
                //JMIRANDA 02.03.2010 validar si tiene monto por devolver
                VariablesFondoSencillo.vMonto = txtMonto.getText().trim();
                VariablesFondoSencillo.vIndTieneMontoDevolver =
                        UtilityFondoSencillo.getIndTieneDevFondo(FarmaVariables.vNuSecUsu,
                                                                 VariablesCajaElectronica.vSecMovCaja, this,
                                                                 txtMonto).trim();
                if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("D")) {
                    //acá se graba
                    cerrarVentana(true);
                    /*if(ingresaDevMonto(txtMonto.getText().trim())){
                        //monto nuevo
                        //imprimir voucher
                        limpiarVariables();
                        cerrarVentana(true);
                    }*/
                } /*else if(VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("R")){
                    if(updateDevMonto(txtMonto.getText().trim(),VariablesCajaElectronica.vSecMovCaja)){
                        //imprimir voucher
                        //actualizarMonto
                        limpiarVariables();
                        cerrarVentana(true);
                    }
                }    */
            }
        }
    }

    private boolean ingresaDevMonto(String pMonto) {
        boolean flag = false;
        try {
            DBFondoSencillo.insDevolucionSencillo(pMonto, VariablesFondoSencillo.vSecAdmLocal, // DESTINO
                    FarmaVariables.vNuSecUsu, // ORIGEN
                    ConstantsFondoSencillo.EstadoEmitido, ConstantsFondoSencillo.TipoFondoDevuelve,
                    FarmaVariables.vIdUsu, FarmaVariables.vIpPc, VariablesCajaElectronica.vSecMovCaja);

            FarmaUtility.aceptarTransaccion();
            flag = true;
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al devolver Monto a Administrador de Local.", txtMonto);
        }
        return flag;
    }

    private void limpiarVariables() {
        VariablesFondoSencillo.vSecAdmLocal = "";
        VariablesFondoSencillo.vSecMovCajaCierre = "";
    }

    private boolean validaMontoLimite(String pMonto) {
        boolean flag = false;
        double MontoMax = 0.00;
        if (UtilityFondoSencillo.validaMonto(pMonto)) {
            try {
                MontoMax =
                        Double.parseDouble(DBFondoSencillo.getMontoDevolver(FarmaVariables.vNuSecUsu, VariablesCajaElectronica.vSecMovCaja));
                if (Double.parseDouble(pMonto) <= MontoMax) {
                    flag = true;
                }
            } catch (SQLException sql) {

            } catch (Exception e) {

            }
        }
        return flag;
    }

    private boolean updateDevMonto(String pMonto, String pSecMovCaja) {
        boolean flag = false;
        try {
            DBFondoSencillo.updDevolucionSencillo(pMonto, VariablesFondoSencillo.vSecAdmLocal, // DESTINO
                    FarmaVariables.vNuSecUsu, // ORIGEN
                    FarmaVariables.vIdUsu, FarmaVariables.vIpPc, pSecMovCaja);

            FarmaUtility.aceptarTransaccion();
            flag = true;
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al devolver Monto a Administrador de Local.", txtMonto);
        }
        return flag;
    }

    /**
     * Obtiene el monto asignado para que sea devuelto por completo
     * @author ASOSA
     * @since 18.06.2010
     */
    private void mostrarMontoAsignado() {
        String monto = "";
        try {
            monto = DBFondoSencillo.getMontoAceptado(VariablesCajaElectronica.vSecMovCaja);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "ERROR en mostrarMontoAsignado: " + e.getMessage(), cmbAdmLocal);
        }
        monto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(monto));
        txtMonto.setText(monto);
    }

    private void btnAdmLocal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbAdmLocal); //ASOSA, 18.06.2010
    }

}
