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
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.ConstantsDelivery;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;

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
public class DlgMantClienteDireccion extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgMantClienteDireccion.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelWhite pnlWhite1 = new JPanelWhite();
    private JPanelWhite pnlWhite2 = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnCliente = new JButtonLabel();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JButtonLabel btnDireccion = new JButtonLabel();
    private JTextFieldSanSerif txtNomCli = new JTextFieldSanSerif();
    private JLabelOrange lblApePatCli_T = new JLabelOrange();
    private JTextFieldSanSerif txtApePatCli = new JTextFieldSanSerif();
    private JLabelOrange lblApeMatCli_T = new JLabelOrange();
    private JTextFieldSanSerif txtApeMatCli = new JTextFieldSanSerif();
    private JLabelOrange lblNumDoc_T = new JLabelOrange();
    private JTextFieldSanSerif txtNumDoc = new JTextFieldSanSerif();
    private JLabelOrange lblCliJur_T = new JLabelOrange();
    private JComboBox cmbCliJur = new JComboBox();
    private JLabelOrange lblTipDoc_T = new JLabelOrange();
    private JComboBox cmbTipDoc = new JComboBox();
    private JLabelOrange lblNomUrb_T = new JLabelOrange();
    private JTextFieldSanSerif txtNomUrb = new JTextFieldSanSerif();
    private JLabelOrange lblNumCalle_T = new JLabelOrange();
    private JTextFieldSanSerif txtNumCalle = new JTextFieldSanSerif();
    private JLabelOrange lblNomCalle_T = new JLabelOrange();
    private JTextFieldSanSerif txtNomCalle = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNomDis = new JTextFieldSanSerif();
    private JLabelOrange lblNomDis_T = new JLabelOrange();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JButtonLabel btnTipoCalle = new JButtonLabel();
    private JComboBox cmbTipoCalle = new JComboBox();
    private JTextFieldSanSerif txtReferencia = new JTextFieldSanSerif();
    private JLabelOrange lblReferencia = new JLabelOrange();
    private JTextFieldSanSerif txtInterior = new JTextFieldSanSerif();
    private JLabelOrange lblInterior = new JLabelOrange();
    private JLabelOrange lbltelefono = new JLabelOrange();
    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMantClienteDireccion() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgMantClienteDireccion(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(496, 484));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(380, 425, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(265, 425, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        pnlWhite1.setBounds(new Rectangle(10, 30, 460, 160));
        pnlWhite1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlWhite2.setBounds(new Rectangle(10, 225, 465, 195));
        pnlWhite2.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTitle1.setBounds(new Rectangle(10, 10, 460, 20));
        btnCliente.setText("Cliente");
        btnCliente.setBounds(new Rectangle(5, 0, 75, 20));
        pnlTitle2.setBounds(new Rectangle(10, 205, 465, 20));
        btnDireccion.setText("Direccion");
        btnDireccion.setBounds(new Rectangle(5, 0, 75, 20));
        txtNomCli.setBounds(new Rectangle(125, 15, 255, 20));
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNomCli_focusLost(e);
            }
        });
        txtNomCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomCli_keyPressed(e);
            }
        });
        lblApePatCli_T.setText("Apellido Paterno:");
        lblApePatCli_T.setBounds(new Rectangle(15, 40, 95, 15));
        txtApePatCli.setBounds(new Rectangle(125, 40, 255, 20));
        txtApePatCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtApePatCli_focusLost(e);
            }
        });
        txtApePatCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtApePatCli_keyPressed(e);
            }
        });
        lblApeMatCli_T.setText("Apellido Materno:");
        lblApeMatCli_T.setBounds(new Rectangle(15, 65, 105, 15));
        txtApeMatCli.setBounds(new Rectangle(125, 65, 255, 20));
        txtApeMatCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtApeMatCli_focusLost(e);
            }
        });
        txtApeMatCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtApeMatCli_keyPressed(e);
            }
        });
        lblNumDoc_T.setText("Numero Documento:");
        lblNumDoc_T.setBounds(new Rectangle(15, 125, 120, 15));
        txtNumDoc.setBounds(new Rectangle(135, 125, 130, 20));
        txtNumDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumDoc_keyPressed(e);
            }
        });
        lblCliJur_T.setText("Cliente Juridico:");
        lblCliJur_T.setBounds(new Rectangle(275, 95, 95, 15));
        cmbCliJur.setBounds(new Rectangle(375, 95, 75, 20));
        cmbCliJur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbCliJur_keyPressed(e);
            }
        });
        lblTipDoc_T.setText("Tipo Documento:");
        lblTipDoc_T.setBounds(new Rectangle(15, 95, 100, 15));
        cmbTipDoc.setBounds(new Rectangle(125, 95, 135, 20));
        cmbTipDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipDoc_keyPressed(e);
            }
        });
        lblNomUrb_T.setText("Nombre Urbanizacion:");
        lblNomUrb_T.setBounds(new Rectangle(15, 110, 120, 15));
        txtNomUrb.setBounds(new Rectangle(145, 110, 260, 20));
        txtNomUrb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNomUrb_focusLost(e);
            }
        });
        txtNomUrb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomUrb_keyPressed(e);
            }
        });
        lblNumCalle_T.setText("Numero Calle:");
        lblNumCalle_T.setBounds(new Rectangle(305, 35, 80, 15));
        txtNumCalle.setBounds(new Rectangle(305, 55, 100, 20));
        txtNumCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumCalle_keyPressed(e);
            }
        });
        lblNomCalle_T.setText("Nombre Calle:");
        lblNomCalle_T.setBounds(new Rectangle(10, 35, 80, 15));
        txtNomCalle.setBounds(new Rectangle(10, 55, 275, 20));
        txtNomCalle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtNomCalle_focusLost(e);
            }
        });
        txtNomCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomCalle_keyPressed(e);
            }
        });
        txtNomDis.setBounds(new Rectangle(145, 135, 260, 20));
        txtNomDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomDis_keyPressed(e);
            }
        });
        lblNomDis_T.setText("Nombre Distrito:");
        lblNomDis_T.setBounds(new Rectangle(15, 135, 120, 15));
        btnNombre.setText("Nombre:");
        btnNombre.setBounds(new Rectangle(15, 15, 75, 15));
        btnNombre.setForeground(new Color(255, 130, 14));
        btnNombre.setMnemonic('N');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNombre_actionPerformed(e);
            }
        });
        btnNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnNombre_keyPressed(e);
            }
        });
        btnTipoCalle.setText("Tipo Calle:");
        btnTipoCalle.setBounds(new Rectangle(250, 10, 65, 15));
        btnTipoCalle.setForeground(new Color(255, 130, 14));
        btnTipoCalle.setMnemonic('T');
        btnTipoCalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipoCalle_actionPerformed(e);
            }
        });
        cmbTipoCalle.setBounds(new Rectangle(320, 10, 85, 20));
        cmbTipoCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipoCalle_keyPressed(e);
            }
        });
        txtReferencia.setBounds(new Rectangle(145, 160, 260, 20));
        txtReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomref_keyPressed(e);
            }
        });
        txtReferencia.setDocument(new FarmaLengthText(120));
        lblReferencia.setText("Referencia:");
        lblReferencia.setBounds(new Rectangle(15, 160, 120, 15));
        txtInterior.setBounds(new Rectangle(145, 85, 260, 20));
        txtInterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNomInt_keyPressed(e);
            }
        });
        txtInterior.setDocument(new FarmaLengthText(120));
        txtInterior.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtInterior_focusLost(e);
            }
        });
        lblInterior.setText("Interior:");
        lblInterior.setBounds(new Rectangle(15, 85, 120, 15));
        lbltelefono.setText("Telefono:");
        lbltelefono.setBounds(new Rectangle(10, 10, 65, 15));
        txtTelefono.setBounds(new Rectangle(85, 10, 100, 20));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumCalle_keyPressed(e);
            }
        });
        txtTelefono.setDocument(new FarmaLengthText(10));
        pnlTitle1.add(btnCliente, null);
        pnlTitle2.add(btnDireccion, null);
        jContentPane.add(pnlTitle2, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlWhite2, null);
        pnlWhite2.add(txtTelefono, null);
        pnlWhite2.add(lbltelefono, null);
        pnlWhite2.add(lblInterior, null);
        pnlWhite2.add(txtInterior, null);
        pnlWhite2.add(lblReferencia, null);
        pnlWhite2.add(txtReferencia, null);
        pnlWhite2.add(cmbTipoCalle, null);
        pnlWhite2.add(btnTipoCalle, null);
        pnlWhite2.add(lblNomDis_T, null);
        pnlWhite2.add(txtNomDis, null);
        pnlWhite2.add(txtNomCalle, null);
        pnlWhite2.add(lblNomCalle_T, null);
        pnlWhite2.add(txtNumCalle, null);
        pnlWhite2.add(lblNumCalle_T, null);
        pnlWhite2.add(txtNomUrb, null);
        pnlWhite2.add(lblNomUrb_T, null);
        jContentPane.add(pnlWhite1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlWhite1.add(btnNombre, null);
        pnlWhite1.add(cmbTipDoc, null);
        pnlWhite1.add(lblTipDoc_T, null);
        pnlWhite1.add(cmbCliJur, null);
        pnlWhite1.add(lblCliJur_T, null);
        pnlWhite1.add(txtNumDoc, null);
        pnlWhite1.add(lblNumDoc_T, null);
        pnlWhite1.add(txtApeMatCli, null);
        pnlWhite1.add(lblApeMatCli_T, null);
        pnlWhite1.add(txtApePatCli, null);
        pnlWhite1.add(lblApePatCli_T, null);
        pnlWhite1.add(txtNomCli, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        txtNomCli.setDocument(new FarmaLengthText(30));
        txtApePatCli.setDocument(new FarmaLengthText(30));
        txtApeMatCli.setDocument(new FarmaLengthText(30));
        txtNumDoc.setDocument(new FarmaLengthText(30));
        txtNomCalle.setDocument(new FarmaLengthText(120));
        txtNumCalle.setDocument(new FarmaLengthText(10));
        txtNomUrb.setDocument(new FarmaLengthText(120));
        txtNomDis.setDocument(new FarmaLengthText(120));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCmbEstCli();
        initCmbCliJur();

        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCmbEstCli() {
        //FarmaLoadCVL.loadCVLfromArrays(cmbEstCli,"cmbEstCli",FarmaConstants.ESTADOS_CODIGO,FarmaConstants.ESTADOS_DESCRIPCION,false);
    }

    private void initCmbCliJur() {
        //FarmaLoadCVL.loadCVLfromArrays(cmbCliJur,"cmbCliJur",FarmaConstants.INDICADORES_CODIGO,FarmaConstants.INDICADORES_DESCRIPCION,false);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {

        FarmaUtility.centrarVentana(this);
        cargaCombos();
        cmbCliJur.setEnabled(false);
        cmbTipDoc.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtTelefono.setText(VariablesDelivery.vNumeroTelefono);

        log.debug("VariablesDelivery.vTipoAccion = " + VariablesDelivery.vTipoAccion);
        log.debug("VariablesDelivery.vTipoAccionInsertarSoloCliente = " +
                  VariablesDelivery.vTipoAccionInsertarSoloCliente);
        log.debug("VariablesDelivery.vTipoAccionDireccion = " + VariablesDelivery.vTipoAccionDireccion);

        if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR)) {
            FarmaUtility.moveFocus(txtNomCli);
        } else if (VariablesDelivery.vTipoAccion.equals(ConstantsDelivery.ACCION_MODIFICAR)) {
            cargaDatosDireccionCliente();
            cargaDatosCliente();
            FarmaUtility.moveFocus(txtNomCli);
        } else if (VariablesDelivery.vTipoAccionDireccion.equals(ConstantsDelivery.ACCION_MODIFICAR_DIRECCION)) {
            //FarmaUtility.moveFocus(cmbTipoCalle);
            cargaDatosDireccionClienteModificacion();
            cargaDatosClienteModificacionCliente();
            FarmaUtility.moveFocus(cmbTipoCalle);
        } else if (VariablesDelivery.vTipoAccionDireccion.equals(ConstantsDelivery.ACCION_INSERTAR_DIRECCION)) {
            //FarmaUtility.moveFocus(cmbTipoCalle);
            //cargaDatosDireccionClienteModificacion();
            cargaDatosClienteModificacionCliente();
            FarmaUtility.moveFocus(cmbTipoCalle);
        } else if (VariablesDelivery.vTipoAccionInsertarSoloCliente.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE)) {
            cargaDatosDireccionCliente();
            FarmaUtility.moveFocus(txtNomCli);
        } else if (VariablesDelivery.vTipoAccion.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE)) {
            cargaDatosDireccionCliente();
            FarmaUtility.moveFocus(txtNomCli);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnNombre_keyPressed(KeyEvent e) {
        //FarmaUtility.moveFocus(txtTipoCalle);
    }

    private void txtNomCli_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApePatCli);
            txtNomCli.setText(txtNomCli.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtApePatCli_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApeMatCli);
            txtApePatCli.setText(txtApePatCli.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtApeMatCli_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumDoc);
            txtApeMatCli.setText(txtApeMatCli.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void cmbTipDoc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumDoc);
        } else
            chkKeyPressed(e);
    }

    private void txtNumDoc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR))
                FarmaUtility.moveFocus(cmbTipoCalle);
            else if (VariablesDelivery.vTipoAccionInsertarSoloCliente.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE))
                FarmaUtility.moveFocus(txtNomCli);
        } else
            chkKeyPressed(e);
    }

    private void cmbCliJur_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //FarmaUtility.moveFocus(cmbEstCli);
        } else
            chkKeyPressed(e);
    }

    private void cmbEstCli_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNomCli);
        } else
            chkKeyPressed(e);
    }


    private void txtTipoCalle_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNomCalle);
            //txtTipoCalle.setText(txtTipoCalle.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtNomCalle_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumCalle);
            txtNomCalle.setText(txtNomCalle.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtNumCalle_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtInterior);
            txtNumCalle.setText(txtNumCalle.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtNomUrb_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNomDis);
            txtNomUrb.setText(txtNomUrb.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtNomDis_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtReferencia);
            txtNomDis.setText(txtNomDis.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {

            guardaValoresCliente();
            log.debug("VariablesDelivery.vTipoAccionInsertarSoloCliente : " +
                      VariablesDelivery.vTipoAccionInsertarSoloCliente);
            if (!validaDatosClientes())
                return;
            String resultadoAgregaCliente = "";
            String resultadoAgregaDireccion = "";
            String resultadoAgregaTelefono = "";
            String resultadoActualizaCliente = "";
            String resultadoActualizaDireccion = "";
            String resultadoAgregaDireccionNueva = "";
            String resultadoAgregaSoloCliente = "";

            if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR)) {
                FarmaUtility.moveFocus(txtNomCli);
                resultadoAgregaCliente = agregaCliente();
                resultadoAgregaDireccion = agregaDireccion();
                resultadoAgregaTelefono = agregaTelefono();
                if (!resultadoAgregaCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR) ||
                    !resultadoAgregaDireccion.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR) ||
                    !resultadoAgregaTelefono.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                    FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(true);
                } else {
                    FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                    FarmaUtility.liberarTransaccion();
                }
            } else if (VariablesDelivery.vTipoAccion.equalsIgnoreCase(ConstantsDelivery.ACCION_MODIFICAR)) {
                resultadoActualizaCliente = actualizaCliente();
                if (!resultadoActualizaCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                    FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(true);
                } else {
                    FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNomCli);
                    FarmaUtility.liberarTransaccion();
                }
            } else if (VariablesDelivery.vTipoAccionDireccion.equalsIgnoreCase(ConstantsDelivery.ACCION_MODIFICAR_DIRECCION)) {
                resultadoActualizaDireccion = actualizaDireccion();
                if (!resultadoActualizaDireccion.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                    FarmaUtility.showMessage(this, "Se grabo la direccion con Exito", null);
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(true);
                } else {
                    FarmaUtility.showMessage(this, "Error al grabar la direccion", cmbTipoCalle);
                    FarmaUtility.liberarTransaccion();
                }
            } else if (VariablesDelivery.vTipoAccionDireccion.equalsIgnoreCase(ConstantsDelivery.ACCION_INSERTAR_DIRECCION)) {
                resultadoAgregaDireccionNueva = agregaDireccion();
                agregaTelefono();
                if (!resultadoAgregaDireccionNueva.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                    FarmaUtility.showMessage(this, "Se grabo la direccion con Exito", null);
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(true);
                } else {
                    FarmaUtility.showMessage(this, "Error al grabar la direccion", cmbTipoCalle);
                    FarmaUtility.liberarTransaccion();
                }
            } else if (VariablesDelivery.vTipoAccionInsertarSoloCliente.equals(ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE)) {
                log.debug("VariablesDelivery.vTipoAccionInsertarSoloCliente : " +
                          VariablesDelivery.vTipoAccionInsertarSoloCliente);
                resultadoAgregaSoloCliente = agregaCliente();
                log.debug("paso");
                if (!resultadoAgregaSoloCliente.equalsIgnoreCase(ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR)) {
                    FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(true);
                } else {
                    FarmaUtility.showMessage(this, "Error al grabar el Cliente", cmbTipoCalle);
                    FarmaUtility.liberarTransaccion();
                }
            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesDelivery.vTipoAccion = "";
            VariablesDelivery.vTipoAccionInsertarSoloCliente = "";
            VariablesDelivery.vTipoAccionDireccion = "";

            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
        //VariablesDelivery.vTipoAccionDireccion="";
        VariablesDelivery.vTipoAccion = "";
        log.debug("Variable nula : " + VariablesDelivery.vTipoAccionDireccion);
        log.debug("Variable nula : " + VariablesDelivery.vTipoAccion);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargaCombos() {
        String[] IND_DESCRIP_CAMPO_DNI_RUC = { "DNI", "RUC" };
        String[] IND_CAMPO_DNI_RUC = { "01", "02" };
        VariablesDelivery.vNombreInHashtable = ConstantsDelivery.VNOMBREINHASHTABLETIPODOCUMENTO;
        FarmaLoadCVL.loadCVLfromArrays(getCmbCampoTipoDocumento(), VariablesDelivery.vNombreInHashtable,
                                       IND_CAMPO_DNI_RUC, IND_DESCRIP_CAMPO_DNI_RUC, true);

        String[] IND_DESCRIP_CAMPO_NO_SI = { "NO", "SI" };
        String[] IND_CAMPO_NO_SI = { "N", "S" };
        VariablesDelivery.vNombreInHashtable = ConstantsDelivery.VNOMBREINHASHTABLETIPOCLIENTE;
        FarmaLoadCVL.loadCVLfromArrays(getCmbCampoTipoCliente(), VariablesDelivery.vNombreInHashtable, IND_CAMPO_NO_SI,
                                       IND_DESCRIP_CAMPO_NO_SI, true);

        String[] IND_DESCRIP_CAMPO_CALLE = { "Avenida", "Jiron", "Calle", "Pasaje" };
        String[] IND_CAMPO_CALLE = { "01", "02", "03", "04" };
        VariablesDelivery.vNombreInHashtable = ConstantsDelivery.VNOMBREINHASHTABLETIPOCALLE;
        FarmaLoadCVL.loadCVLfromArrays(getCmbCampoTipoCalle(), VariablesDelivery.vNombreInHashtable, IND_CAMPO_CALLE,
                                       IND_DESCRIP_CAMPO_CALLE, false);

    }

    public JComboBox getCmbCampoTipoDocumento() {
        return this.cmbTipDoc;
    }

    public JComboBox getCmbCampoTipoCliente() {
        return this.cmbCliJur;
    }

    public JComboBox getCmbCampoTipoCalle() {
        return this.cmbTipoCalle;
    }

    private void btnNombre_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNomCli);
    }

    private void btnTipoCalle_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipoCalle);
    }

    private void cmbTipoCalle_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesDelivery.vTipoCalle =
                    FarmaLoadCVL.getCVLCode(VariablesDelivery.vNombreInHashtable, cmbTipoCalle.getSelectedIndex());
            log.debug("Tipo Calle : " + VariablesDelivery.vTipoCalle);
            FarmaUtility.moveFocus(txtNomCalle);
        } else
            chkKeyPressed(e);

    }

    private void guardaValoresCliente() {
        VariablesDelivery.vNombreCliente = txtNomCli.getText().trim().toUpperCase();
        VariablesDelivery.vApellidoPaterno = txtApePatCli.getText().trim().toUpperCase();
        VariablesDelivery.vApellidoMaterno = txtApeMatCli.getText().trim().toUpperCase();
        VariablesDelivery.vTipoDocumento = ConstantsDelivery.TIPO_COMP_BOLETA;
        VariablesDelivery.vTipoCliente = ConstantsDelivery.TIPO_CLIENTE_NATURAL;
        VariablesDelivery.vNumeroDocumento = txtNumDoc.getText().trim().toUpperCase();
        VariablesDelivery.vNombreCalle = txtNomCalle.getText().trim().toUpperCase();
        VariablesDelivery.vNumeroCalle = txtNumCalle.getText().trim().toUpperCase();
        VariablesDelivery.vNombreInterior = txtInterior.getText().trim().toUpperCase();
        VariablesDelivery.vNombreUrbanizacion = txtNomUrb.getText().trim().toUpperCase();
        VariablesDelivery.vNombreDistrito = txtNomDis.getText().trim().toUpperCase();
        VariablesDelivery.vReferencia = txtReferencia.getText().trim().toUpperCase();


        log.debug(VariablesDelivery.vNombreCliente);
        log.debug(VariablesDelivery.vApellidoPaterno);
        log.debug(VariablesDelivery.vApellidoMaterno);
        log.debug(VariablesDelivery.vTipoDocumento);
        log.debug(VariablesDelivery.vTipoCliente);
        log.debug(VariablesDelivery.vNumeroDocumento);
        log.debug(VariablesDelivery.vNombreCalle);
        log.debug(VariablesDelivery.vNumeroCalle);
        log.debug(VariablesDelivery.vNombreInterior);
        log.debug(VariablesDelivery.vNombreUrbanizacion);
        log.debug(VariablesDelivery.vNombreDistrito);
        log.debug(VariablesDelivery.vReferencia);

    }

    private boolean validaDatosClientes() {
        /*
    if(VariablesDelivery.vNumeroDocumento.equalsIgnoreCase("") || VariablesDelivery.vNumeroDocumento.length() < 8 )
    {
      FarmaUtility.showMessage(this, "Ingrese un DNI correcto", txtNumDoc);
      return false;
    }
*/
        if (VariablesDelivery.vNombreCliente.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese el Nombre del Cliente", txtNomCli);
            return false;
        }
        if (VariablesDelivery.vApellidoPaterno.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese el Apellido Paterno del Cliente", txtApePatCli);
            return false;
        }
        if (VariablesDelivery.vNombreCalle.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese el Nombre de la Calle para la direccion", txtNumCalle);
            return false;
        }
        if (VariablesDelivery.vNumeroCalle.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese el Numero de la Calle para la direccion", txtApePatCli);
            return false;
        }
        if (VariablesDelivery.vNombreDistrito.equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Ingrese el Distrito para la direccion", txtNomDis);
            return false;
        }

        if (cmbTipoCalle.getSelectedItem().toString().trim().equals("")) {
            FarmaUtility.showMessage(this, "Seleccione el Tipo de Calle", cmbTipoCalle);
            return false;
        }

        return true;
    }

    private String agregaCliente() {
        String resultadoAgregaCliente = "";
        try {
            resultadoAgregaCliente =
                    DBDelivery.agregaCliente(VariablesDelivery.vNombreCliente, VariablesDelivery.vApellidoPaterno,
                                             VariablesDelivery.vApellidoMaterno, VariablesDelivery.vTipoDocumento,
                                             VariablesDelivery.vTipoCliente, VariablesDelivery.vNumeroDocumento, "",
                                             "");
            VariablesDelivery.vCodCli = resultadoAgregaCliente;
            return resultadoAgregaCliente;
        } catch (SQLException sql) {
            log.error("", sql);
            //FarmaUtility.showMessage(this,"Error",null);
            if (sql.getErrorCode() == 00001) {
                FarmaUtility.showMessage(this, "Ya existe el Dni. Verifique!", txtNomDis);
            }
            return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private String agregaDireccion() {
        String resultadoAgregaDireccion = "";
        try {
            resultadoAgregaDireccion =
                    DBDelivery.agregaDireccion(VariablesDelivery.vTipoCalle, VariablesDelivery.vNombreCalle,
                                               VariablesDelivery.vNumeroCalle, VariablesDelivery.vNombreUrbanizacion,
                                               VariablesDelivery.vNombreDistrito, VariablesDelivery.vNombreInterior,
                                               VariablesDelivery.vReferencia, "", "", "", "", "", "");
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
        txtNomCalle.setText(VariablesDelivery.vNombreCalle);
        txtNumCalle.setText(VariablesDelivery.vNumeroCalle);
        txtNomUrb.setText(VariablesDelivery.vNombreUrbanizacion);
        txtNomDis.setText(VariablesDelivery.vNombreDistrito);
        txtInterior.setText(VariablesDelivery.vNombreInterior);
        txtReferencia.setText(VariablesDelivery.vReferencia);
        cmbTipoCalle.setSelectedItem(VariablesDelivery.vDescTipoCalle);

        cmbTipoCalle.setEnabled(false);
        txtNomCalle.setEnabled(false);
        txtNumCalle.setEnabled(false);
        txtNomUrb.setEnabled(false);
        txtNomDis.setEnabled(false);
        txtInterior.setEnabled(false);
        txtReferencia.setEnabled(false);
    }

    private void cargaDatosDireccionClienteModificacion() {
        txtNomCalle.setText(VariablesDelivery.vNombreCalle);
        txtNumCalle.setText(VariablesDelivery.vNumeroCalle);
        txtNomUrb.setText(VariablesDelivery.vNombreUrbanizacion);
        txtNomDis.setText(VariablesDelivery.vNombreDistrito);
        txtInterior.setText(VariablesDelivery.vNombreInterior);
        txtReferencia.setText(VariablesDelivery.vReferencia);
        cmbTipoCalle.setSelectedItem(VariablesDelivery.vDescTipoCalle);

    }

    private void cargaDatosCliente() {
        txtNomCli.setText(VariablesDelivery.vNombreCliente);
        txtApePatCli.setText(VariablesDelivery.vApellidoPaterno);
        txtApeMatCli.setText(VariablesDelivery.vApellidoMaterno);
        txtNumDoc.setText(VariablesDelivery.vNumeroDocumento);
    }

    private void cargaDatosClienteModificacionCliente() {
        txtNomCli.setText(VariablesDelivery.vNombreCliente);
        txtApePatCli.setText(VariablesDelivery.vApellidoPaterno);
        txtApeMatCli.setText(VariablesDelivery.vApellidoMaterno);
        txtNumDoc.setText(VariablesDelivery.vNumeroDocumento);
        txtNomCli.setEnabled(false);
        txtApePatCli.setEnabled(false);
        txtApeMatCli.setEnabled(false);
        txtNumDoc.setEnabled(false);
    }

    private String actualizaCliente() {
        String resultado = "";
        try {
            resultado =
                    DBDelivery.actualizaCliente(VariablesDelivery.vCodCli, VariablesDelivery.vNombreCliente, VariablesDelivery.vApellidoPaterno,
                                                VariablesDelivery.vApellidoMaterno, VariablesDelivery.vNumeroDocumento,
                                                "");
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
                    DBDelivery.actualizaDireccion(VariablesDelivery.vCodigoDireccion, VariablesDelivery.vTipoCalle, VariablesDelivery.vNombreCalle,
                                                  VariablesDelivery.vNumeroCalle,
                                                  VariablesDelivery.vNombreUrbanizacion,
                                                  VariablesDelivery.vNombreDistrito, VariablesDelivery.vNombreInterior,
                                                  VariablesDelivery.vReferencia, "", "", "", "", "");
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsDelivery.RESULTADO_GRABAR_CLIENTE_ERROR;
        }
    }

    private void txtNomref_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbTipoCalle);
            txtReferencia.setText(txtReferencia.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtNomInt_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNomUrb);
            txtInterior.setText(txtInterior.getText().toUpperCase());
        } else
            chkKeyPressed(e);
    }

    private void txtNomCli_focusLost(FocusEvent e) {
        txtNomCli.setText(txtNomCli.getText().toUpperCase());
    }

    private void txtApePatCli_focusLost(FocusEvent e) {
        txtApePatCli.setText(txtApePatCli.getText().toUpperCase());
    }

    private void txtApeMatCli_focusLost(FocusEvent e) {
        txtApeMatCli.setText(txtApeMatCli.getText().toUpperCase());
    }

    private void txtNomCalle_focusLost(FocusEvent e) {
        txtNomCalle.setText(txtNomCalle.getText().toUpperCase());
    }

    private void txtInterior_focusLost(FocusEvent e) {
        txtInterior.setText(txtInterior.getText().toUpperCase());
    }

    private void txtNomUrb_focusLost(FocusEvent e) {
        txtNomUrb.setText(txtNomUrb.getText().toUpperCase());
    }


}
