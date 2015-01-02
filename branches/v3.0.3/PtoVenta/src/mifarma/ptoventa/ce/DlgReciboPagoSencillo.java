package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.ce.dao.DAOCajaElectronica;
import mifarma.ptoventa.ce.dao.FactoryCajaElectronica;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.FacadeCajaElectronica;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.DlgConsultaXCorrelativo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgReciboPagoSencillo.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * wvillagomez      09.09.2013   Creación<br>
 * <br>
 * @author Wendy Villagómez<br>
 * @version 1.0<br>
 *
 */

public class DlgReciboPagoSencillo extends JDialog {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DlgConsultaXCorrelativo.class);

    private DAOCajaElectronica daoImplCajaElectronica;
    private boolean pagoSencillo;
    private String pAbrir;
    private char etv;

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblTipoComprobante = new JButtonLabel();
    private JComboBox cmbTipoComp = new JComboBox();
    private JButtonLabel lblNroComprobante = new JButtonLabel();
    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JButtonLabel lblTotal = new JButtonLabel();
    private JTextFieldSanSerif txtTotal = new JTextFieldSanSerif();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JButtonLabel lblDiferencia = new JButtonLabel();
    private JTextFieldSanSerif txtDiferencia = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame myParentFrame;

    public DlgReciboPagoSencillo() {
        this(null, "", false);
    }

    public DlgReciboPagoSencillo(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            myParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgReciboPagoSencillo(Frame parent, String title, boolean modal, boolean pagoSencillo) {
        super(parent, title, modal);
        try {
            myParentFrame = parent;
            this.pagoSencillo = pagoSencillo;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(419, 292));
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(5, 10, 400, 215));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblTipoComprobante.setText("ETV:");
        lblTipoComprobante.setMnemonic('E');
        lblTipoComprobante.setBounds(new Rectangle(20, 20, 110, 15));
        lblTipoComprobante.setBackground(Color.white);
        lblTipoComprobante.setForeground(new Color(255, 90, 33));
        cmbTipoComp.setBounds(new Rectangle(160, 20, 220, 20));
        cmbTipoComp.setEnabled(false);
        lblNroComprobante.setText("Folio ETV:");
        lblNroComprobante.setMnemonic('F');
        lblNroComprobante.setBounds(new Rectangle(20, 55, 105, 15));
        lblNroComprobante.setForeground(new Color(255, 90, 33));
        lblNroComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNroComprobante_actionPerformed(e);
            }
        });
        txtNroComprobante.setBounds(new Rectangle(160, 55, 145, 20));
        txtNroComprobante.setLengthText(10);
        txtNroComprobante.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNroComprobante_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNroComprobante_keyTyped(e);
            }
        });
        lblTotal.setText("Total:");
        lblTotal.setMnemonic('T');
        lblTotal.setBounds(new Rectangle(20, 95, 75, 15));
        lblTotal.setForeground(new Color(255, 90, 33));
        lblTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblTotal_actionPerformed(e);
            }
        });
        txtTotal.setBounds(new Rectangle(160, 95, 145, 19));
        txtTotal.setLengthText(10);
        txtTotal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtTotal_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtTotal_keyTyped(e);
            }
        });
        lblMonto.setText("Monto:");
        lblMonto.setMnemonic('M');
        lblMonto.setBounds(new Rectangle(20, 135, 75, 15));
        lblMonto.setForeground(new Color(255, 90, 33));
        lblMonto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblMonto_actionPerformed(e);
            }
        });
        txtMonto.setBounds(new Rectangle(160, 135, 145, 19));
        txtMonto.setLengthText(10);
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonto_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonto_keyTyped(e);
            }
        });
        lblDiferencia.setText("Diferencia:");
        lblDiferencia.setMnemonic('D');
        lblDiferencia.setBounds(new Rectangle(20, 175, 75, 15));
        lblDiferencia.setForeground(new Color(255, 90, 33));
        lblDiferencia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblDiferencia_actionPerformed(e);
            }
        });
        txtDiferencia.setBounds(new Rectangle(160, 175, 145, 19));
        txtDiferencia.setLengthText(10);
        txtDiferencia.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtDiferencia_keyTyped(e);
            }
        });
        btnF11.setBounds(new Rectangle(5, 235, 117, 20));
        btnF11.setText("[F11] Aceptar");
        btnEsc.setBounds(new Rectangle(290, 235, 117, 19));
        btnEsc.setText("[ESC] Cerrar");
        pnlTitle.add(lblTipoComprobante, null);
        pnlTitle.add(cmbTipoComp, null);
        pnlTitle.add(txtNroComprobante, null);
        pnlTitle.add(lblNroComprobante, null);
        pnlTitle.add(lblTotal, null);
        pnlTitle.add(txtTotal, null);
        pnlTitle.add(lblMonto, null);
        pnlTitle.add(txtMonto, null);
        pnlTitle.add(lblDiferencia, null);
        pnlTitle.add(txtDiferencia, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);

        if (isPagoSencillo()) {
            txtTotal.setEnabled(false);
            txtDiferencia.setEnabled(false);
        } else {
            this.setSize(new Dimension(419, 212));
            pnlTitle.setBounds(new Rectangle(5, 10, 400, 130));
            lblMonto.setVisible(false);
            txtMonto.setVisible(false);
            lblDiferencia.setVisible(false);
            txtDiferencia.setVisible(false);
            btnF11.setBounds(new Rectangle(5, 150, 117, 20));
            btnEsc.setBounds(new Rectangle(290, 150, 117, 19));
        }

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setResizable(false);
    }

    private void initialize() {
        daoImplCajaElectronica = FactoryCajaElectronica.getDAOCajaElectronica(FactoryCajaElectronica.Tipo.MYBATIS);
        int idxSel = daoImplCajaElectronica.getETV();
        cargaCombo(idxSel);
        log.debug("ETV: " + etv);
        Map mapParametros = daoImplCajaElectronica.verificaReciboSencillo(etv);
        cargarTotal(mapParametros);
    }

    private void cargarTotal(Map mapParametros) {
        pAbrir = mapParametros.get("vAbrir_out").toString();
        if (pAbrir.equals("N")) {
            txtTotal.setText(mapParametros.get("cTotal_out").toString());
            txtTotal.setText(FarmaUtility.formatNumber(Double.parseDouble(txtTotal.getText().replaceAll(",", ""))));
        }
        log.debug("Tipo pago sencillo: " + isPagoSencillo() + " " + pAbrir + " " + txtTotal.getText());
    }

    public boolean permisoAbrirVentana() {
        if (pAbrir.equals("X")) {
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.OCURRIO_ERROR_CARGAR, null);
            cerrarVentana(false);
            return false;
        }
        if (!isPagoSencillo() && pAbrir.equals("N")) {
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.PAGO_SENCILLO_PENDIENTE, null);
            cerrarVentana(false);
            return false;
        }
        if (isPagoSencillo() && pAbrir.equals("S")) {
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.NO_PAGO_SENCILLO_PENDIENTE, null);
            cerrarVentana(false);
            return false;
        }
        return true;
    }

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (!validarDatos()) {
                return;
            }
            if (!JConfirmDialog.rptaConfirmDialogDefaultNo(this, ConstantsCajaElectronica.CONFIRM_GRABAR)) {
                return;
            }
            int codFonSencillo = grabarReciboPagoFondoSencillo();
            imprimirFondoSencillo(codFonSencillo);
            cerrarVentana(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private int grabarReciboPagoFondoSencillo() {
        try {
            String folio = txtNroComprobante.getText();
            BigDecimal total = new BigDecimal(txtTotal.getText().replaceAll(",", ""));
            BigDecimal monto = BigDecimal.ZERO;
            BigDecimal diferencia = BigDecimal.ZERO;
            char tipFondoSencillo = ConstantsCajaElectronica.TIPO_SENCILLO_RECIBO;
            if (pagoSencillo) {
                tipFondoSencillo = ConstantsCajaElectronica.TIPO_SENCILLO_PAGO;
                monto = new BigDecimal(txtMonto.getText().replaceAll(",", ""));
                diferencia = new BigDecimal(txtDiferencia.getText().replaceAll(",", ""));
            }
            int codFonSencillo =
                daoImplCajaElectronica.grabarReciboPagoSencillo(folio, total, monto, diferencia, tipFondoSencillo,
                                                                etv);
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.EXITO_GRABAR, null);
            return codFonSencillo;
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.OCURRIO_ERROR_GRABAR, null);
            return 0;
        }
    }

    private void imprimirFondoSencillo(int codFonSencillo) {
        String mensaje = daoImplCajaElectronica.imprimirFondoSencillo(codFonSencillo);
        //Impresion Original
        PrintConsejo.imprimirHtml(mensaje, VariablesPtoVenta.vImpresoraActual, VariablesPtoVenta.vTipoImpTermicaxIp);
    }

    private String calcularDiferencia() {
        if (txtMonto.getText().trim().length() == 0) {
            return "";
        }
        txtMonto.setText(FarmaUtility.formatNumber(Double.parseDouble(txtMonto.getText().replaceAll(",", ""))));
        BigDecimal total = new BigDecimal(txtTotal.getText().replaceAll(",", ""));
        BigDecimal monto = new BigDecimal(txtMonto.getText().replaceAll(",", ""));
        total = total.add(monto.negate());
        return FarmaUtility.formatNumber(total.doubleValue());

    }

    private boolean validarDatos() {
        boolean flag = true;
        if (cmbTipoComp.getSelectedObjects().length == 0) {
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.FALTA_INFO, cmbTipoComp);
            return flag = false;
        }
        if (txtNroComprobante.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.FALTA_INFO, txtNroComprobante);
            return flag = false;
        }
        txtTotal.setText(FarmaUtility.formatNumber(Double.parseDouble(txtTotal.getText().replaceAll(",", ""))));
        if (txtTotal.getText().trim().length() == 0 ||
            new BigDecimal(txtTotal.getText().trim().replaceAll(",", "")).compareTo(BigDecimal.ZERO) == 0) {
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.FALTA_INFO, txtTotal);
            return flag = false;
        }
        if (pagoSencillo) {
            if (txtMonto.getText().trim().length() == 0) {
                FarmaUtility.showMessage(this, ConstantsCajaElectronica.FALTA_INFO, txtMonto);
                return flag = false;
            }
            if (txtDiferencia.getText().trim().length() == 0) {
                FarmaUtility.showMessage(this, ConstantsCajaElectronica.FALTA_INFO, txtDiferencia);
                return flag = false;
            }
            if (!validarDiferencia()) {
                return flag = false;
            }
        }
        return flag;
    }

    private boolean validarDiferencia() {
        if (txtMonto.getText().trim().length() == 0) {
            FarmaUtility.moveFocus(txtNroComprobante);
            return false;
        }
        BigDecimal diferencia = new BigDecimal(txtDiferencia.getText().trim().replaceAll(",", ""));
        if (diferencia.compareTo(BigDecimal.ZERO) == -1) {
            FarmaUtility.showMessage(this, ConstantsCajaElectronica.MONTO_MAYOR_TOTAL, txtMonto);
            FarmaUtility.moveFocus(txtMonto);
            return false;
        }
        return true;
    }

    private void cargaCombo(int idxSel) {
        FacadeCajaElectronica f = new FacadeCajaElectronica();
        ArrayList array = f.listarETV();

        ArrayList temp_cod = new ArrayList();
        ArrayList temp_desc = new ArrayList();

        for (int i = 0; i < array.size(); i++) {
            ArrayList t = (ArrayList)array.get(i);
            temp_cod.add(t.get(0).toString());
            temp_desc.add(t.get(1).toString());
        }

        String[] cods = (String[])Arrays.copyOf(temp_cod.toArray(), temp_cod.toArray().length, String[].class);
        String[] descs = (String[])Arrays.copyOf(temp_desc.toArray(), temp_desc.toArray().length, String[].class);

        FarmaLoadCVL.loadCVLfromArrays(cmbTipoComp, ConstantsCajaElectronica.HASHTABLE_TIPOS_ETV, cods, descs, true);
        cmbTipoComp.setSelectedIndex(idxSel);
        etv =
FarmaLoadCVL.getCVLCode(ConstantsCajaElectronica.HASHTABLE_TIPOS_ETV, cmbTipoComp.getSelectedIndex()).charAt(0);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroComprobante);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, ConstantsCajaElectronica.PRESIONE_ESC, txtNroComprobante);
    }

    private void lblNroComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroComprobante);
    }

    private void txtNroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroComprobante, e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            if (isPagoSencillo()) {
                FarmaUtility.moveFocus(txtMonto);
            } else {
                FarmaUtility.moveFocus(txtTotal);
            }
        }
        chkKeyPressed(e);
    }

    private void lblTotal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtTotal);
    }

    private void txtTotal_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNroComprobante);
        }
        chkKeyPressed(e);
    }

    private void txtTotal_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtTotal, e);
    }

    private void lblMonto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMonto);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if ((e.getKeyChar() == KeyEvent.VK_ENTER) || (UtilityPtoVenta.verificaVK_F11(e))) {
            txtDiferencia.setText(calcularDiferencia());
            if (!validarDiferencia()) {
                txtDiferencia.setText("");
            } else {
                FarmaUtility.moveFocus(txtNroComprobante);
            }
        }
        chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    private void lblDiferencia_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDiferencia);
    }

    private void txtDiferencia_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtDiferencia, e);
    }

    public boolean isPagoSencillo() {
        return pagoSencillo;
    }

    public void setPagoSencillo(boolean pagoSencillo) {
        this.pagoSencillo = pagoSencillo;
    }
}
