package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.ConstantsDelivery;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantClienteDireccion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.04.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgMantClienteDireccionNew extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgMantClienteDireccionNew.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelWhite pnlWhite1 = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnCliente = new JButtonLabel();
    private JTextFieldSanSerif txtNomCli = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNomCalle = new JTextFieldSanSerif();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JLabelOrange lbltelefono = new JLabelOrange();
    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();
    private JLabel jLabel1 = new JLabel();
    private JTextFieldSanSerif txtReferencia = new JTextFieldSanSerif();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextFieldSanSerif txtObs = new JTextFieldSanSerif();
    private JComboBox cmbDpto = new JComboBox();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JComboBox cmbProvi = new JComboBox();
    private JComboBox cmbDist = new JComboBox();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JTextFieldSanSerif txtUrba = new JTextFieldSanSerif();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMantClienteDireccionNew() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgMantClienteDireccionNew(Frame parent, String title, boolean modal) {
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
        this.setTitle("Datos del Cliente");
        this.setSize(new Dimension(563, 365));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(455, 310, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(330, 310, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        pnlWhite1.setBounds(new Rectangle(10, 30, 540, 275));
        pnlWhite1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTitle1.setBounds(new Rectangle(10, 10, 540, 20));
        btnCliente.setText("Cliente");
        btnCliente.setBounds(new Rectangle(5, 0, 75, 20));
        txtNomCli.setBounds(new Rectangle(80, 40, 440, 20));
        txtNomCli.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNomCli_focusLost(e);
            }
        });
        txtNomCli.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomCli_keyPressed(e);
            }
        });
        txtNomCalle.setBounds(new Rectangle(80, 95, 440, 20));
        txtNomCalle.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNomCalle_focusLost(e);
            }
        });
        txtNomCalle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomCalle_keyPressed(e);
            }
        });
        btnNombre.setText("Nombre :");
        btnNombre.setBounds(new Rectangle(10, 40, 65, 15));
        btnNombre.setForeground(new Color(255, 130, 14));
        btnNombre.setMnemonic('N');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNombre_actionPerformed(e);
            }
        });
        btnNombre.addKeyListener(new KeyAdapter() {

        });
        lbltelefono.setText("Telefono :");
        lbltelefono.setBounds(new Rectangle(10, 10, 65, 15));
        txtTelefono.setBounds(new Rectangle(80, 10, 110, 20));
        txtTelefono.addKeyListener(new KeyAdapter() {

        });
        txtTelefono.setDocument(new FarmaLengthText(10));
        jLabel1.setText("Referencia:");
        jLabel1.setBounds(new Rectangle(10, 245, 65, 20));
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setBackground(new Color(250, 130, 14));
        jLabel1.setForeground(new Color(250, 130, 14));
        txtReferencia.setBounds(new Rectangle(80, 245, 440, 20));
        txtReferencia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtReferencia_keyPressed(e);
            }
        });
        jButtonLabel1.setText("Direccion :");
        jButtonLabel1.setBounds(new Rectangle(10, 95, 75, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('D');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        jLabel2.setText("Obs Cliente:");
        jLabel2.setBounds(new Rectangle(10, 65, 70, 20));
        jLabel2.setBackground(new Color(255, 130, 14));
        jLabel2.setForeground(new Color(250, 130, 14));
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        txtObs.setBounds(new Rectangle(80, 65, 440, 20));
        txtObs.setLengthText(100);
        txtObs.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtObs_keyPressed(e);
            }
        });
        cmbDpto.setBounds(new Rectangle(100, 125, 185, 20));
        cmbDpto.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmbDpto_itemStateChanged(e);
            }
        });
        cmbDpto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDpto_keyPressed(e);
            }
        });
        jLabel3.setText("Departamento:");
        jLabel3.setBounds(new Rectangle(10, 125, 90, 20));
        jLabel3.setForeground(new Color(255, 130, 14));
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        jLabel4.setText("Provincia:");
        jLabel4.setBounds(new Rectangle(25, 155, 70, 20));
        jLabel4.setFont(new Font("Tahoma", 1, 11));
        jLabel4.setForeground(new Color(255, 130, 14));
        cmbProvi.setBounds(new Rectangle(100, 155, 185, 20));
        cmbProvi.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmbProvi_itemStateChanged(e);
            }
        });
        cmbProvi.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbProvi_keyPressed(e);
            }
        });
        cmbDist.setBounds(new Rectangle(100, 185, 185, 20));
        cmbDist.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDist_keyPressed(e);
            }
        });
        jLabel5.setText("Distrito :");
        jLabel5.setBounds(new Rectangle(30, 185, 50, 20));
        jLabel5.setForeground(new Color(255, 130, 14));
        jLabel5.setFont(new Font("Tahoma", 1, 11));
        jLabel6.setText("Urbanizaci\u00f3n :");
        jLabel6.setBounds(new Rectangle(5, 215, 85, 20));
        jLabel6.setFont(new Font("Tahoma", 1, 11));
        jLabel6.setForeground(new Color(255, 130, 14));
        txtUrba.setBounds(new Rectangle(100, 215, 190, 20));
        txtUrba.setLengthText(100);
        txtUrba.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtUrba_keyPressed(e);
            }
        });
        pnlTitle1.add(btnCliente, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlWhite1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlWhite1.add(txtUrba, null);
        pnlWhite1.add(jLabel6, null);
        pnlWhite1.add(jLabel5, null);
        pnlWhite1.add(cmbDist, null);
        pnlWhite1.add(cmbProvi, null);
        pnlWhite1.add(jLabel4, null);
        pnlWhite1.add(jLabel3, null);
        pnlWhite1.add(cmbDpto, null);
        pnlWhite1.add(txtObs, null);
        pnlWhite1.add(jLabel2, null);
        pnlWhite1.add(jButtonLabel1, null);
        pnlWhite1.add(txtReferencia, null);
        pnlWhite1.add(jLabel1, null);
        pnlWhite1.add(btnNombre, null);
        pnlWhite1.add(txtNomCli, null);
        pnlWhite1.add(lbltelefono, null);
        pnlWhite1.add(txtTelefono, null);
        pnlWhite1.add(txtNomCalle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        txtNomCli.setDocument(new FarmaLengthText(100));
        txtNomCalle.setDocument(new FarmaLengthText(110));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        cargaDptos();
        cargaProvincias();
        cargaDistritos();
        setUbigeoLocal();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {

        FarmaUtility.centrarVentana(this);
        txtTelefono.setEnabled(false);
        txtTelefono.setText(VariablesDelivery.vNumeroTelefono);

        if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR)) {
            FarmaUtility.moveFocus(txtNomCli);
            if (VariablesDelivery.vTipoCampo.equalsIgnoreCase(ConstantsDelivery.CAMPO_CLIENTE)) {
                jButtonLabel1.setVisible(false);
                txtNomCalle.setVisible(true);
                jLabel1.setVisible(false);
                txtReferencia.setVisible(true);
                jLabel2.setVisible(true);
                txtObs.setVisible(true);
                cmbDpto.setVisible(false);
                cmbProvi.setVisible(false);
                cmbDist.setVisible(false);
                txtUrba.setText("");
                txtUrba.setVisible(false);
                jLabel3.setVisible(false);
                jLabel4.setVisible(false);
                jLabel5.setVisible(false);
                jLabel6.setVisible(false);
            } else if (VariablesDelivery.vTipoCampo.equalsIgnoreCase(ConstantsDelivery.CAMPO_DIRECCION)) {
                FarmaUtility.moveFocus(txtNomCalle);
                txtNomCli.setEnabled(false);
                txtNomCli.setText(VariablesDelivery.vNombreCliente);
                txtReferencia.setVisible(true);
                jLabel2.setVisible(false);
                txtObs.setVisible(false);
                cargaDptos();
                cargaProvincias();
                cargaDistritos();
                setUbigeoLocal();
                cmbDpto.setVisible(true);
                cmbProvi.setVisible(true);
                cmbDist.setVisible(true);
                txtUrba.setText("");
                txtUrba.setVisible(true);
                jLabel3.setVisible(true);
                jLabel4.setVisible(true);
                jLabel5.setVisible(true);
                jLabel6.setVisible(true);
                /*btnNombre.setVisible(false);
                txtNomCli.setVisible(false);*/
            }
        } else if (VariablesDelivery.vTipoAccion.equals(ConstantsDelivery.ACCION_MODIFICAR)) {
            if (VariablesDelivery.vTipoCampo.equalsIgnoreCase(ConstantsDelivery.CAMPO_CLIENTE)) {
                cargaDatosCliente();
                FarmaUtility.moveFocus(txtNomCli);
                jButtonLabel1.setVisible(false);
                txtNomCalle.setVisible(false);
                jLabel1.setVisible(false);
                jLabel2.setVisible(true);
                txtObs.setVisible(true);
                txtReferencia.setVisible(false);
                cmbDpto.setVisible(false);
                cmbProvi.setVisible(false);
                cmbDist.setVisible(false);
                txtUrba.setText("");
                txtUrba.setVisible(false);
                jLabel3.setVisible(false);
                jLabel4.setVisible(false);
                jLabel5.setVisible(false);
                jLabel6.setVisible(false);

            } else {
                cmbDpto.setVisible(true);
                cmbProvi.setVisible(true);
                cmbDist.setVisible(true);
                jLabel3.setVisible(true);
                jLabel4.setVisible(true);
                jLabel5.setVisible(true);
                jLabel6.setVisible(true);
                txtUrba.setText("");
                txtUrba.setVisible(true);
                cargaDatosDireccionCliente();
                FarmaUtility.moveFocus(txtNomCalle);
                btnNombre.setVisible(false);
                txtNomCli.setVisible(false);
                txtReferencia.setVisible(true);
                jLabel2.setVisible(false);
                txtObs.setVisible(false);
            }
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtNomCli_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNomCli.setText(txtNomCli.getText().toUpperCase());
            if (txtObs.isVisible()) {
                FarmaUtility.moveFocus(txtObs);
            } else if (txtNomCalle.isVisible()) {
                FarmaUtility.moveFocus(txtNomCalle);
            }
        } else
            chkKeyPressed(e);
    }

    private void txtObs_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtObs.setText(txtObs.getText().toUpperCase());
            if (txtNomCalle.isVisible()) {
                FarmaUtility.moveFocus(txtNomCalle);
            }
        } else
            chkKeyPressed(e);
    }

    private void txtNomCalle_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //FarmaUtility.moveFocus(txtReferencia);
            txtReferencia.setText(txtReferencia.getText().toUpperCase());
            FarmaUtility.moveFocus(cmbDpto);
        } else
            chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {

            guardaValoresCliente();

            //if(!validaDatosClientes()) return;
            String resultadoAgregaCliente = "";
            String resultadoAgregaDireccion = "";
            String resultadoAgregaTelefono = "";
            String resultadoActualizaCliente = "";
            String resultadoActualizaDireccion = "";
            String resultadoAgregaDireccionNueva = "";
            String resultadoAgregaSoloCliente = "";

            if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR)) {
                if (VariablesDelivery.vTipoCampo.equalsIgnoreCase(ConstantsDelivery.CAMPO_CLIENTE)) {
                    resultadoAgregaSoloCliente = agregaCliente(txtTelefono.getText());
                    if (!resultadoAgregaSoloCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                        FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                        FarmaUtility.aceptarTransaccion();
                        cerrarVentana(true);
                    } else {
                        FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                        FarmaUtility.liberarTransaccion();
                    }
                } else if (VariablesDelivery.vTipoCampo.equalsIgnoreCase(ConstantsDelivery.CAMPO_DIRECCION_CLIENTE)) {
                    String rstAgregaTelefono = agregaTelefono();
                    if (rstAgregaTelefono.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                        FarmaUtility.showMessage(this, "Error al grabar el Teléfono", txtNomCli);
                        FarmaUtility.liberarTransaccion();
                        return;
                    }

                    resultadoAgregaSoloCliente = agregaCliente(txtTelefono.getText());

                    if (resultadoAgregaSoloCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                        FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                        FarmaUtility.liberarTransaccion();
                        return;
                    }

                    if (VariablesDelivery.vDptosCodigo.trim().length() == 0 ||
                        VariablesDelivery.vProviCodigo.trim().length() == 0 ||
                        VariablesDelivery.vDistCodigo.trim().length() == 0) {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Los Datos de Ubigeo Son Obligatorios.", cmbDpto);
                        return;
                    }

                    resultadoAgregaDireccionNueva = agregaDireccion(resultadoAgregaSoloCliente);

                    if (resultadoAgregaDireccionNueva.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                        FarmaUtility.showMessage(this, "Error al grabar la direccion", txtNomCli);
                        FarmaUtility.liberarTransaccion();
                        return;
                    }

                    //FarmaUtility.showMessage(this, "Se grabo todo con ", null);
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(true);

                } else {
                    if (VariablesDelivery.vDptosCodigo.trim().length() == 0 ||
                        VariablesDelivery.vProviCodigo.trim().length() == 0 ||
                        VariablesDelivery.vDistCodigo.trim().length() == 0) {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Los Datos de Ubigeo Son Obligatorios.", cmbDpto);
                        return;
                    }
                    resultadoAgregaDireccionNueva = agregaDireccion("");
                    agregaTelefono();
                    if (!resultadoAgregaDireccionNueva.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                        FarmaUtility.showMessage(this, "Se grabo la direccion con Exito", null);
                        FarmaUtility.aceptarTransaccion();
                        cerrarVentana(true);
                    } else {
                        FarmaUtility.showMessage(this, "Error al grabar la direccion", txtNomCli);
                        FarmaUtility.liberarTransaccion();
                    }
                }
            } else if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_MODIFICAR)) {
                if (VariablesDelivery.vTipoCampo.equalsIgnoreCase(ConstantsDelivery.CAMPO_CLIENTE)) {
                    resultadoActualizaCliente = actualizaCliente();
                    if (!resultadoActualizaCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                        FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                        FarmaUtility.aceptarTransaccion();
                        cerrarVentana(true);
                    } else {
                        FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                        FarmaUtility.liberarTransaccion();
                    }
                } else {
                    if (VariablesDelivery.vDptosCodigo.trim().length() == 0 ||
                        VariablesDelivery.vProviCodigo.trim().length() == 0 ||
                        VariablesDelivery.vDistCodigo.trim().length() == 0) {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Los Datos de Ubigeo Son Obligatorios.", cmbDpto);
                        return;
                    }
                    resultadoActualizaDireccion = actualizaDireccion();
                    if (!resultadoActualizaDireccion.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                        FarmaUtility.showMessage(this, "Se grabo la direccion con Exito", null);
                        FarmaUtility.aceptarTransaccion();
                        cerrarVentana(true);
                    } else {
                        FarmaUtility.showMessage(this, "Error al grabar la direccion", txtNomCli);
                        FarmaUtility.liberarTransaccion();
                    }
                }
            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesDelivery.vTipoAccion = "";
            VariablesDelivery.vTipoCampo = "";

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

    private void btnNombre_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNomCli);
    }

    private void guardaValoresCliente() {
        VariablesDelivery.vNombreCliente = txtNomCli.getText().trim().toUpperCase();
        VariablesDelivery.vObsCli = txtObs.getText().trim().toUpperCase();
        VariablesDelivery.vApellidoPaterno = "";
        VariablesDelivery.vApellidoMaterno = "";
        VariablesDelivery.vTipoDocumento = ConstantsDelivery.TIPO_COMP_BOLETA;
        VariablesDelivery.vTipoCliente = ConstantsDelivery.TIPO_CLIENTE_NATURAL;
        VariablesDelivery.vNumeroDocumento = "";
        VariablesDelivery.vNombreCalle = txtNomCalle.getText().trim().toUpperCase();
        VariablesDelivery.vNumeroCalle = "";
        VariablesDelivery.vNombreInterior = "";
        VariablesDelivery.vNombreUrbanizacion = "";
        VariablesDelivery.vNombreDistrito = "";
        VariablesDelivery.vReferencia = txtReferencia.getText();

        VariablesDelivery.vDptosCodigo =
                FarmaLoadCVL.getCVLCode(VariablesDelivery.vDptosInHashTable, cmbDpto.getSelectedIndex());
        VariablesDelivery.vProviCodigo =
                FarmaLoadCVL.getCVLCode(VariablesDelivery.vProviInHashTable, cmbProvi.getSelectedIndex());
        VariablesDelivery.vDistCodigo =
                FarmaLoadCVL.getCVLCode(VariablesDelivery.vDistHashTable, cmbDist.getSelectedIndex());
        VariablesDelivery.vUrbanizacion = txtUrba.getText().toUpperCase().trim();

    }

    private boolean validaDatosClientes() {
        if (VariablesDelivery.vNombreCliente.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese el Nombre del Cliente", txtNomCli);
            return false;
        }
        if (VariablesDelivery.vNombreCalle.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese el Nombre de la Calle para la direccion", txtNomCalle);
            return false;
        }

        return true;
    }

    private String agregaCliente(String pTelefono) {
        String resultadoAgregaCliente = "";
        try {
            resultadoAgregaCliente =
                    DBDelivery.agregaCliente(VariablesDelivery.vNombreCliente, VariablesDelivery.vApellidoPaterno,
                                             VariablesDelivery.vApellidoMaterno, VariablesDelivery.vTipoDocumento,
                                             VariablesDelivery.vTipoCliente, VariablesDelivery.vNumeroDocumento,
                                             pTelefono, VariablesDelivery.vObsCli);
            VariablesDelivery.vCodCli = resultadoAgregaCliente;
            return resultadoAgregaCliente;
        } catch (SQLException sql) {
            log.error("", sql);
            //FarmaUtility.showMessage(this,"Error",null);
            if (sql.getErrorCode() == 00001) {
                FarmaUtility.showMessage(this, "Ya existe el Dni. Verifique!", txtNomCli);
            }
            return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private String agregaDireccion(String pCodCliente) {
        String resultadoAgregaDireccion = "";


        try {
            resultadoAgregaDireccion =
                    DBDelivery.agregaDireccion(".", VariablesDelivery.vNombreCalle, VariablesDelivery.vNumeroCalle,
                                               VariablesDelivery.vNombreUrbanizacion,
                                               VariablesDelivery.vNombreDistrito, VariablesDelivery.vNombreInterior,
                                               VariablesDelivery.vReferencia, VariablesDelivery.vCodCli, pCodCliente,
                                               VariablesDelivery.vDptosCodigo, VariablesDelivery.vProviCodigo,
                                               VariablesDelivery.vDistCodigo, VariablesDelivery.vUrbanizacion);
            VariablesDelivery.vCodigoDireccion = resultadoAgregaDireccion;
            return resultadoAgregaDireccion;
        } catch (SQLException sql) {
            log.error("", sql);
            //FarmaUtility.showMessage(this,"Error",null);
            return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private String agregaTelefono() {
        String resultadoAgregaTelefono = "";
        try {
            resultadoAgregaTelefono = DBDelivery.agregaTelefono();
            VariablesDelivery.vCodTelefono = resultadoAgregaTelefono;
            return resultadoAgregaTelefono;
        } catch (SQLException sql) {
            log.error("", sql);
            //FarmaUtility.showMessage(this,"Error",null);
            return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private void cargaDatosDireccionCliente() {
        txtNomCalle.setText(VariablesDelivery.vDireccion);
        txtReferencia.setText(VariablesDelivery.vReferencia);

        cargaDptos();

        FarmaLoadCVL.setSelectedValueInComboBox(cmbDpto, VariablesDelivery.vDptosInHashTable,
                                                VariablesDelivery.vDptosCodigo);

        cargaProvincias(); // carga provinvias segun dpto
        // 06-NOV-13, TCT, add combo de Provincia y Distrito
        FarmaLoadCVL.setSelectedValueInComboBox(cmbProvi, VariablesDelivery.vProviInHashTable,
                                                VariablesDelivery.vProviCodigo);

        cargaDistritos();

        FarmaLoadCVL.setSelectedValueInComboBox(cmbDist, VariablesDelivery.vDistHashTable,
                                                VariablesDelivery.vDistCodigo);

        txtUrba.setText(VariablesDelivery.vUrbanizacion);

    }

    private void cargaDatosCliente() {
        txtNomCli.setText(VariablesDelivery.vNombreCliente);
        txtObs.setText(VariablesDelivery.vObsCli);
    }

    private String actualizaCliente() {
        String resultado = "";
        try {
            resultado =
                    DBDelivery.actualizaCliente(VariablesDelivery.vCodCli, VariablesDelivery.vNombreCliente, VariablesDelivery.vApellidoPaterno,
                                                VariablesDelivery.vApellidoMaterno, VariablesDelivery.vNumeroDocumento,
                                                VariablesDelivery.vObsCli);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private String actualizaDireccion() {
        String resultado = "";
        try {
            resultado =
                    DBDelivery.actualizaDireccion(VariablesDelivery.vCodigoDireccion, ".", VariablesDelivery.vNombreCalle,
                                                  VariablesDelivery.vNumeroCalle,
                                                  VariablesDelivery.vNombreUrbanizacion,
                                                  VariablesDelivery.vNombreDistrito, VariablesDelivery.vNombreInterior,
                                                  VariablesDelivery.vReferencia, VariablesDelivery.

                        vDptosCodigo, VariablesDelivery.vProviCodigo, VariablesDelivery.vDistCodigo,
                        VariablesDelivery.vUrbanCodigo, VariablesDelivery.vUrbanizacion);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private void txtNomCli_focusLost(FocusEvent e) {
        txtNomCli.setText(txtNomCli.getText().toUpperCase());
    }

    private void txtNomCalle_focusLost(FocusEvent e) {
        txtNomCalle.setText(txtNomCalle.getText().toUpperCase());
    }


    private void txtReferencia_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNomCalle);
        } else
            chkKeyPressed(e);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNomCalle);
    }

    private void cmbDpto_itemStateChanged(ItemEvent e) {
        //log.debug("### Cambio estado de Dpto....");
        cargaProvincias();

    }


    private void cargaDptos() {

        ArrayList parametros = new ArrayList();

        FarmaLoadCVL.loadCVLFromSP(cmbDpto, VariablesDelivery.vDptosInHashTable, "PTOVENTA_DEL_CLI.GET_DPTOS",
                                   parametros, false, true);
    }


    private void cargaProvincias() {
        String vsCodDpto;
        vsCodDpto = FarmaLoadCVL.getCVLCode(VariablesDelivery.vDptosInHashTable, cmbDpto.getSelectedIndex());

        ArrayList parametros = new ArrayList();
        parametros.add(vsCodDpto);

        FarmaLoadCVL.unloadCVL(cmbProvi, VariablesDelivery.vProviInHashTable);
        FarmaLoadCVL.loadCVLFromSP(cmbProvi, VariablesDelivery.vProviInHashTable, "PTOVENTA_DEL_CLI.GET_PROVI(?)",
                                   parametros, false, true);
    }

    private void cargaDistritos() {
        String vsCodDpto, vsCodProvi;
        vsCodDpto = FarmaLoadCVL.getCVLCode(VariablesDelivery.vDptosInHashTable, cmbDpto.getSelectedIndex());
        vsCodProvi = FarmaLoadCVL.getCVLCode(VariablesDelivery.vProviInHashTable, cmbProvi.getSelectedIndex());

        ArrayList parametros = new ArrayList();
        parametros.add(vsCodDpto);
        parametros.add(vsCodProvi);

        FarmaLoadCVL.unloadCVL(cmbDist, VariablesDelivery.vDistHashTable);

        FarmaLoadCVL.loadCVLFromSP(cmbDist, VariablesDelivery.vDistHashTable, "PTOVENTA_DEL_CLI.GET_DIST(?,?)",
                                   parametros, false, true);

    }

    private void cmbProvi_itemStateChanged(ItemEvent e) {
        cargaDistritos();
    }

    private void cmbDpto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbProvi);
    }

    private void cmbProvi_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbDist);
        else
            chkKeyPressed(e);
    }

    private void cmbDist_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtUrba);
        else
            chkKeyPressed(e);
    }

    private void txtUrba_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtUrba.setText(txtUrba.getText().toUpperCase());
            FarmaUtility.moveFocus(txtReferencia);
        }

        else
            chkKeyPressed(e);
    }


    public void setUbigeoLocal() {
        String presultado = "";
        try {
            presultado = DBDelivery.getUbigeoLocal();

            String[] pCadena = presultado.split("@");

            if (pCadena.length == 3) {
                String pCodDep = pCadena[0].trim();
                String pCodProv = pCadena[1].trim();
                String pCodDistri = pCadena[2].trim();

                FarmaLoadCVL.setSelectedValueInComboBox(cmbDpto, VariablesDelivery.vDptosInHashTable, pCodDep);


                // 06-NOV-13, TCT, add combo de Provincia y Distrito
                FarmaLoadCVL.setSelectedValueInComboBox(cmbProvi, VariablesDelivery.vProviInHashTable, pCodProv);


                FarmaLoadCVL.setSelectedValueInComboBox(cmbDist, VariablesDelivery.vDistHashTable, pCodDistri);


            }

        } catch (Exception sql) {
            log.error("", sql);
            //return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }
}
