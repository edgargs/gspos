package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.UtilityLectorTarjeta;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosTarjeta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      18.03.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDatosTarjeta extends JDialog {
    //Variables para el pago con tarjeta
    private static final Logger log = LoggerFactory.getLogger(DlgDatosTarjeta.class);

    private String bin = "";
    protected String desc_prod = "";
    protected String strCodFormaPago = "";
    private String strNombrePropTarjeta = "";
    private String strFechaVencTarjeta = "";

    protected String strDescFormaPago = "";
    private String nroTarjeta = "";

    protected String infoTarj = "";
    protected int cont = 0;
    private String mestarj = "";
    private String aniotarj = "";

    private Frame myParentFrame;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanel pnlInfo = new JPanel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelOrange lblMonto_T = new JLabelOrange();
    protected JTextFieldSanSerif txtNroTarjeta = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDocIdentidad = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JLabelOrange lblTipoTarjeta_T = new JLabelOrange();
    protected JLabelOrange lblTipoTarjeta = new JLabelOrange();
    private JLabelOrange lblmon = new JLabelOrange();
    private JLabelOrange lblmoneda = new JLabelOrange();
    private JButtonLabel lblNroTarjeta = new JButtonLabel();
    private JButtonLabel lblDocIdentidad = new JButtonLabel();
    private JButtonLabel lblLote = new JButtonLabel();
    private JLabelOrange lblmensaje = new JLabelOrange();
    private JButtonLabel lblCodVoucher = new JButtonLabel();
    private JButtonLabel lblDebitoCredito = new JButtonLabel();
    private JTextFieldSanSerif txtCodVoucher = new JTextFieldSanSerif();
    protected JComboBox cmbDebitoCredito = new JComboBox();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    ArrayList listInforTarje = new ArrayList();
    private ArrayList arrayCodFormaPago = new ArrayList();

    UtilityLectorTarjeta utilityLectorTarjeta = new UtilityLectorTarjeta();
    private int intCountEnter = 0;
    private String strIndLectorBanda = "N";

    private String nroTarjetaBruto = "";
    private boolean indValidaDatos = true;
    //KMONCADA 08.04.2015 VALIDA USO DE LECTORA DE BARRAS
    boolean isUsandoLector = false;

    public DlgDatosTarjeta() {
        this(null, "", false);
    }

    public DlgDatosTarjeta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(416, 321));
        this.getContentPane().setLayout(null);
        this.setTitle("Pago con Tarjeta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.setBounds(new Rectangle(0, 0, 415, 300));
        pnlInfo.setBounds(new Rectangle(10, 25, 390, 230));
        pnlInfo.setBackground(Color.white);
        pnlInfo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlInfo.setLayout(null);
        pnlInfo.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(10, 5, 390, 20));
        pnlTitle.setFocusable(false);
        jLabelWhite1.setText("Pago con tarjeta");
        jLabelWhite1.setBounds(new Rectangle(5, 0, 110, 20));
        jLabelWhite1.setFocusable(false);
        lblMonto_T.setText("Monto");
        lblMonto_T.setBounds(new Rectangle(15, 195, 80, 20));
        lblMonto_T.setFont(new Font("SansSerif", 1, 18));
        lblMonto_T.setFocusable(false);
        txtNroTarjeta.setBounds(new Rectangle(115, 10, 135, 20));
        txtNroTarjeta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtnrotarj_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtnrotarj_keyPressed(e);
            }
        });
        //txtNroTarjeta.setDocument(new FarmaLengthText(22));
        txtDocIdentidad.setVisible(false);
        txtDocIdentidad.setBounds(new Rectangle(115, 100, 95, 20));
        txtDocIdentidad.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtdni_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtdni_keyPressed(e);
            }
        });
        txtDocIdentidad.setDocument(new FarmaLengthText(9));

        txtLote.setVisible(false);
        txtLote.setBounds(new Rectangle(115, 160, 95, 20));
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtlote_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }
        });
        txtLote.setDocument(new FarmaLengthText(4));

        lblTipoTarjeta_T.setVisible(false);
        lblTipoTarjeta_T.setText("Tipo Tarjeta");
        lblTipoTarjeta_T.setBounds(new Rectangle(15, 40, 80, 15));
        lblTipoTarjeta_T.setFocusable(false);
        lblTipoTarjeta.setText("[TIPO]");
        lblTipoTarjeta.setBounds(new Rectangle(115, 35, 265, 20));
        lblTipoTarjeta.setForeground(new Color(43, 141, 39));
        lblTipoTarjeta.setFocusable(false);
        lblmon.setText("[MONTO]");
        lblmon.setBounds(new Rectangle(195, 195, 90, 20));
        lblmon.setForeground(new Color(49, 141, 43));
        lblmon.setFont(new Font("SansSerif", 1, 18));
        lblmon.setFocusable(false);
        lblmoneda.setText("S/.");
        lblmoneda.setBounds(new Rectangle(160, 195, 30, 20));
        lblmoneda.setForeground(new Color(49, 141, 43));
        lblmoneda.setFont(new Font("SansSerif", 1, 18));
        lblmoneda.setFocusable(false);
        lblNroTarjeta.setText("Nro. Tarjeta");
        lblNroTarjeta.setBounds(new Rectangle(15, 15, 75, 15));
        lblNroTarjeta.setForeground(new Color(255, 130, 14));
        lblNroTarjeta.setMnemonic('n');
        lblNroTarjeta.setFocusable(false);
        lblNroTarjeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNroTarjeta_actionPerformed(e);
            }
        });
        lblDocIdentidad.setText("Doc. Identidad");
        lblDocIdentidad.setBounds(new Rectangle(15, 105, 90, 15));
        lblDocIdentidad.setForeground(new Color(255, 130, 14));
        lblDocIdentidad.setMnemonic('d');
        lblDocIdentidad.setFocusable(false);
        lblDocIdentidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblDocIdentidad_actionPerformed(e);
            }
        });
        lblDocIdentidad.setVisible(false);
        lblLote.setText("Lote");
        lblLote.setBounds(new Rectangle(15, 165, 95, 15));
        lblLote.setForeground(new Color(255, 130, 14));
        lblLote.setMnemonic('l');
        lblLote.setFocusable(false);
        lblLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblLote_actionPerformed(e);
            }
        });
        lblLote.setVisible(false);
        lblmensaje.setText("[MENSAJE]");
        lblmensaje.setBounds(new Rectangle(255, 15, 125, 15));
        lblmensaje.setForeground(new Color(49, 141, 43));
        lblmensaje.setFocusable(false);
        lblCodVoucher.setText("Codigo Voucher");
        lblCodVoucher.setBounds(new Rectangle(15, 135, 95, 15));
        lblCodVoucher.setForeground(new Color(255, 130, 14));
        lblCodVoucher.setMnemonic('c');
        lblCodVoucher.setFocusable(false);
        lblCodVoucher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblCodVoucher_actionPerformed(e);
            }
        });
        lblCodVoucher.setVisible(false);
        txtCodVoucher.setBounds(new Rectangle(115, 130, 135, 20));
        txtCodVoucher.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtcodvou_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtcodvou_keyPressed(e);
            }
        });
        txtCodVoucher.setVisible(false);
        pnlTitle.add(jLabelWhite1, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEscape, null);
        pnlFondo.add(pnlTitle, null);
        pnlInfo.add(cmbDebitoCredito, null);
        pnlInfo.add(lblDebitoCredito, null);
        pnlInfo.add(txtCodVoucher, null);
        pnlInfo.add(lblCodVoucher, null);
        pnlInfo.add(lblmensaje, null);
        pnlInfo.add(lblLote, null);
        pnlInfo.add(lblDocIdentidad, null);
        pnlInfo.add(lblNroTarjeta, null);
        pnlInfo.add(lblmon, null);
        pnlInfo.add(lblmoneda, null);
        pnlInfo.add(lblTipoTarjeta, null);
        pnlInfo.add(lblTipoTarjeta_T, null);
        pnlInfo.add(txtLote, null);
        pnlInfo.add(txtDocIdentidad, null);
        pnlInfo.add(txtNroTarjeta, null);
        pnlInfo.add(lblMonto_T, null);
        pnlFondo.add(pnlInfo, null);
        this.getContentPane().add(pnlFondo, null);
        txtCodVoucher.setDocument(new FarmaLengthText(20));
        lblEscape.setBounds(new Rectangle(305, 265, 95, 25));
        lblEscape.setText("[ ESC ] Escape");
        lblEscape.setFocusable(false);
        lblF11.setBounds(new Rectangle(195, 265, 100, 25));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setFocusable(false);
        cmbDebitoCredito.setVisible(false);
        cmbDebitoCredito.setBounds(new Rectangle(115, 65, 135, 20));
        cmbDebitoCredito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmbDebitoCredito_actionPerformed(e);
            }
        });
        cmbDebitoCredito.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDebitoCredito_keyPressed(e);
            }
        });
        cmbDebitoCredito.addItem("Credito");
        cmbDebitoCredito.addItem("Debito");
        lblDebitoCredito.setText("Debito/Credito?");
        lblDebitoCredito.setFocusable(false);
        lblDebitoCredito.setBounds(new Rectangle(15, 65, 95, 20));
        lblDebitoCredito.setForeground(new Color(255, 130, 14));
        lblDebitoCredito.setMnemonic('e');
        lblDebitoCredito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblDebitoCredito_actionPerformed(e);
            }
        });
        lblDebitoCredito.setVisible(false);
    }

    protected void inicializarVariables() {

        bin = "";
        desc_prod = "";
        strCodFormaPago = "";
        strDescFormaPago = "";
        nroTarjeta = "";
        nroTarjetaBruto = "";
        txtNroTarjeta.setText("");
        txtDocIdentidad.setText("");
        txtLote.setText("");
        //txtlote.setText("");
        lblTipoTarjeta.setText("");
        infoTarj = "";
        aniotarj = "";
        mestarj = "";

        intCountEnter = 0;
        strIndLectorBanda = "N";
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroTarjeta);
        lblmon.setText(VariablesCaja.vValMontoPagado);
        inicializarVariables();
        lblmensaje.setText("");
    }
    
    private void chkkeyPressed(KeyEvent e) {

        if (UtilityPtoVenta.verificaVK_F11(e) && !isUsandoLector) {
            if (validaCampos()) {
                txtLote.setText(FarmaUtility.completeWithSymbol(txtLote.getText(), 3, "0", "I"));

                VariablesCaja.vCodFormaPago = strCodFormaPago;
                VariablesCaja.vDescFormaPago = strDescFormaPago;
                VariablesCaja.vNumTarjCred = txtNroTarjeta.getText();

                VariablesCaja.vFecVencTarjCred = strFechaVencTarjeta;
                VariablesCaja.vNomCliTarjCred = strNombrePropTarjeta;

                //20.03.2013 LTERRAZOS Se agrega 3 parametros de pagos con tarjeta
                VariablesCaja.vDNITarj = txtDocIdentidad.getText();
                VariablesCaja.vCodVoucher = txtCodVoucher.getText();
                VariablesCaja.vCodLote = txtLote.getText();

                cerrarVentana(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE  && !isUsandoLector) {
            cerrarVentana(false);
        }
    }
    
    private void txtnrotarj_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirDigitos(txtnrotarj,e);
        //KMONCADA 08.04.2015 EN EL CASO DE LECTORA DE BARRAS SE OMITIRAN LAS LECTURAS LUEGO DEL PRIMER ENTER, 
        //SOLO EVALUDA PRIMER GRUPO DE CARACTERES.
        if (strIndLectorBanda.equals("S") && intCountEnter >= 1) {
            e.consume();
        }
    }

    private void txtdni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDocIdentidad, e);
    }

    private void txtcodvou_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCodVoucher, e);
    }

    private void txtlote_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtLote, e);
    }
    
    private void txtnrotarj_keyPressed(KeyEvent e) {
        String tmpNro = "";
        String tmpNomb = "";
        String tmpFecha = "";
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            intCountEnter++;
        if (e.getKeyCode() == KeyEvent.VK_B){
            strIndLectorBanda = "S";
            isUsandoLector = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //KMONCADA 08.04.2015 VALIDA SI EL ENTER ES REALIZO POR  LA LECTORA O UNA INTERRUPCION
            if (strIndLectorBanda.equals("S") && intCountEnter == 1 ) {
                if(!txtNroTarjeta.getText().endsWith("0_")){
                    intCountEnter--; 
                }
            }
            if (strIndLectorBanda.equals("S") && intCountEnter == 1 ) {
                listInforTarje = utilityLectorTarjeta.capturaTeclasLector(e, txtNroTarjeta.getText());
                tmpNro = listInforTarje.get(0).toString();
                tmpNomb = listInforTarje.get(1).toString();
                tmpFecha = listInforTarje.get(2).toString();
                strNombrePropTarjeta = tmpNomb;
                strFechaVencTarjeta = tmpFecha;
                //txtNroTarjeta.setText(tmpNro);
                //LLEIVA 06/Sep/2013 Modificacion para enmascaramiento de tarjeta
                txtNroTarjeta.setText(utilityLectorTarjeta.enmascararTarjeta(tmpNro));
                txtNroTarjeta.setEditable(false);
                nroTarjetaBruto = tmpNro;
            }

            if ((strIndLectorBanda.equals("S") && intCountEnter == 2) ||
                (strIndLectorBanda.equals("N") && intCountEnter == 1)) {
                
                // KMONCADA 08.04.2015 CARGARA VENTANA DE PROGRESS PARA EVITAR ACCIONES DEL TECLADO MIENTRAS VALIDA.
                DlgProcesarDatosTarjeta dlg = new DlgProcesarDatosTarjeta(myParentFrame, "", true);
                dlg.setDlgDatos(this);
                dlg.mostrar();/* 
                
                //KMONCADA 2015.04.06 SE COMENTA YA QUE NO VALIDA TARJETAS LEIDAS POR LECTOR DE BARRAS.
                //nroTarjetaBruto = txtNroTarjeta.getText().trim();
                if (buscarInfotarjeta()) {
                    if (hablitarTipoTarjeta()) {
                        //LLEIVA 16-Sep-2013 Se desactiva los combos para luego activarlos
                        //lblNroCuotas.setVisible(false);
                        //cmbNroCuotas.setVisible(false);
                        lblDocIdentidad.setVisible(false);
                        lblLote.setVisible(false);
                        lblCodVoucher.setVisible(false);
                        txtCodVoucher.setVisible(false);
                        lblTipoTarjeta_T.setVisible(false);
                        lblDebitoCredito.setVisible(false);
                        cmbDebitoCredito.setVisible(false);
                        txtLote.setVisible(false);

                        if (ConstantsPtoVenta.FORPAG_MC_POS.equalsIgnoreCase(strCodFormaPago) ||
                            ConstantsPtoVenta.FORPAG_VISA_POS.equalsIgnoreCase(strCodFormaPago) ||
                            ConstantsPtoVenta.FORPAG_DINNERS_POS.equalsIgnoreCase(strCodFormaPago) ||
                            ConstantsPtoVenta.FORPAG_AMEX_POS.equalsIgnoreCase(strCodFormaPago) ||
                            ConstantsPtoVenta.FORPAG_CMR.equalsIgnoreCase(strCodFormaPago)) {
                            //LLEIVA 16-Sep-2013 Se habilita el combo de DebitoCredito
                            lblDocIdentidad.setVisible(true);
                            lblLote.setVisible(true);
                            lblCodVoucher.setVisible(true);
                            txtCodVoucher.setVisible(true);
                            lblTipoTarjeta_T.setVisible(true);
                            lblDebitoCredito.setVisible(true);
                            cmbDebitoCredito.setVisible(true);
                            txtLote.setVisible(true);
                            verificarCredDeb();
                        }
                        lblTipoTarjeta.setText(desc_prod + "/" + strDescFormaPago);
                        cmbDebitoCredito.grabFocus();
                        
                    } else {
                        FarmaUtility.showMessage(this, "Tipo de tarjeta no admitida.", null);
                        inicializarVariables();
                        cont = 0;
                        infoTarj = "";
                        txtNroTarjeta.setEditable(true);
                        txtNroTarjeta.setText("");
                    }
                } else {
                    inicializarVariables();
                    cont = 0;
                    infoTarj = "";
                    txtNroTarjeta.setEditable(true);
                    txtNroTarjeta.setText("");
                } */
                isUsandoLector = false;
            }
        } else {
            chkkeyPressed(e);
        }
    }
    protected void habilitaCampos(boolean flag){
        lblDocIdentidad.setVisible(flag);
        lblLote.setVisible(flag);
        lblCodVoucher.setVisible(flag);
        txtCodVoucher.setVisible(flag);
        lblTipoTarjeta_T.setVisible(flag);
        lblDebitoCredito.setVisible(flag);
        cmbDebitoCredito.setVisible(flag);
        txtLote.setVisible(flag);
    }
    protected boolean buscarInfotarjeta() {
        boolean flag = true;
        String vNroTarjeta = "";
        ArrayList lista = new ArrayList();

        if ("".equals(nroTarjetaBruto))
            vNroTarjeta = txtNroTarjeta.getText();
        else
            vNroTarjeta = nroTarjetaBruto;

        try {
            //DBPtoVenta.obtenerInfoTarjeta(lista, txtNroTarjeta.getText().trim(), ConstantsCaja.TIPO_ORIGEN_PAGO_POS);
            DBPtoVenta.obtenerInfoTarjeta(lista, vNroTarjeta.trim(), ConstantsCaja.TIPO_ORIGEN_PAGO_POS);
        } catch (SQLException e) {
            log.debug("ERROR en buscarInfotarjeta: " + e.getMessage());
            log.error("", e);
        }
        if (lista.size() > 0) {
            ArrayList reg = (ArrayList)lista.get(0);
            bin = (String)reg.get(0);
            desc_prod = (String)reg.get(1);
            strCodFormaPago = (String)reg.get(2);
            strDescFormaPago = (String)reg.get(3);
            nroTarjetaBruto = vNroTarjeta;
            txtNroTarjeta.setText(utilityLectorTarjeta.enmascararTarjeta(vNroTarjeta));
            txtNroTarjeta.setEditable(false);
        } else { //si no se encuentra la información del bin de tarjeta, se lanza una ventan para decidir que forma de pago procesar
            if (vNroTarjeta.length() > 0 && (vNroTarjeta.length() == 15 || vNroTarjeta.length() == 16)) {
                DlgEleccionTarjeta dlgEleccionTarjeta = new DlgEleccionTarjeta(myParentFrame, "", true);
                dlgEleccionTarjeta.setValores(txtNroTarjeta.getText(), "POS");
                dlgEleccionTarjeta.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    //si se selecciona la forma de pago setear los valores
                    bin = vNroTarjeta.substring(0, 6); //(String)reg.get(0);
                    desc_prod = ""; //(String)reg.get(1);
                    strCodFormaPago = dlgEleccionTarjeta.getFormaPago(); //(String)reg.get(2);
                    strDescFormaPago = dlgEleccionTarjeta.getDescFormaPago(); //(String)reg.get(3);
                    nroTarjeta = vNroTarjeta;

                    //LEIVA 06-Sep-2013 Modificacion para enmascaramiento de tarjeta
                    nroTarjetaBruto = vNroTarjeta;
                    txtNroTarjeta.setText(utilityLectorTarjeta.enmascararTarjeta(vNroTarjeta));
                    txtNroTarjeta.setEditable(false);
                }
            } else {
                bin = "";
                desc_prod = "";
                strCodFormaPago = "";
                strDescFormaPago = "";
                nroTarjeta = "";
                flag = false;
                FarmaUtility.showMessage(this, "Tipo de tarjeta desconocido", txtNroTarjeta);
            }
        }
        
        // KMONCADA 2015.03.18 VALIDA NROS DE TARJETA 
        if (vNroTarjeta.length() > 0) {
            if (!UtilityPtoVenta.validarNumero(vNroTarjeta)) {
                flag = false;
            } else if (strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_AMEX) ||
                       strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_AMEX_POS)) {
                if (!(vNroTarjeta.length() == 15 || vNroTarjeta.length() == 16)) {
                    flag = false;
                }
            } else if (strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_DINERS) ||
                       strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_DINNERS_POS)) {
                if (!(vNroTarjeta.length() == 14 || vNroTarjeta.length() == 16)) {
                    flag = false;
                }
            } else if (!(vNroTarjeta.length() == 16)) {
                flag = false;
            }
            if (!flag) {
                FarmaUtility.showMessage(this, "El número no es correcto. Pase nuevamente la tarjeta.", txtNroTarjeta);
            }
        }


        return flag;
    }

    private void txtdni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            e.getComponent().transferFocus();
        else
            chkkeyPressed(e);
    }

    private void txtcodvou_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtLote);
        else
            chkkeyPressed(e);
    }

    private boolean validaCampos() {
        boolean flag = true;
        if (nroTarjetaBruto.trim().equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta", txtNroTarjeta);
        } else if (Double.parseDouble(nroTarjetaBruto.trim()) == 0) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta valido", txtNroTarjeta);
            txtNroTarjeta.setText("");
        } else if (bin.equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta valido", txtNroTarjeta);
            txtNroTarjeta.setText("");
        } else if (indValidaDatos) {
            if ((txtDocIdentidad.getText().trim().length() == 0 || txtDocIdentidad.getText().trim().length() != 8) &&
                cmbDebitoCredito.getSelectedItem().toString().equals("Credito")) {
                flag = false;
                FarmaUtility.showMessage(this, "Si va a ingresar DNI debe ser valido", txtDocIdentidad);
                txtDocIdentidad.setText("");
            } else if (txtCodVoucher.getText().trim().equalsIgnoreCase("")) {
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese Cod. Voucher", txtCodVoucher);
                txtCodVoucher.setText("");
            } else if (Double.parseDouble(txtCodVoucher.getText().trim()) == 0) {
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese Cod. Voucher valido", txtCodVoucher);
                txtCodVoucher.setText("");
            } else if (txtLote.getText().trim().equalsIgnoreCase("")) {
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese Lote", txtLote);
                txtLote.setText("");
            } else if (Double.parseDouble(txtLote.getText().trim()) == 0) {
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese Lote valido", txtLote);
                txtLote.setText("");
            }
        }
        return flag;
    }

    private void lblNroTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroTarjeta);
    }

    private void lblDocIdentidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDocIdentidad);
    }

    private void lblLote_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtLote);
    }

    private void txtLote_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtLote.setText(FarmaUtility.completeWithSymbol(txtLote.getText(), 4, "0", "I"));
            e.getComponent().transferFocus();
        } else
            chkkeyPressed(e);
    }

    private void lblCodVoucher_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodVoucher);
    }

    protected boolean hablitarTipoTarjeta() {
        boolean contFormTrj = arrayCodFormaPago.contains(strCodFormaPago);
        return contFormTrj;
    }

    public void setArrayFormPago(ArrayList codFormaPago) {
        this.arrayCodFormaPago = codFormaPago;
    }

    private void cmbDebitoCredito_actionPerformed(ActionEvent e) {
        verificarCredDeb();
    }

    protected void verificarCredDeb() {
        if (cmbDebitoCredito.getSelectedItem().toString().equals("Credito") && cmbDebitoCredito.isVisible()) {
            txtDocIdentidad.setEnabled(true);
            txtDocIdentidad.setVisible(true);
            lblDocIdentidad.setVisible(true);
            txtDocIdentidad.grabFocus();
        } else if (cmbDebitoCredito.getSelectedItem().toString().equals("Debito") && cmbDebitoCredito.isVisible()) {
            txtDocIdentidad.setEnabled(false);
            txtDocIdentidad.setVisible(false);
            lblDocIdentidad.setVisible(false);
            lblF11.setEnabled(true);
        }
    }

    private void cmbDebitoCredito_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.getComponent().transferFocus();
        } else
            chkkeyPressed(e);
    }

    private void lblDebitoCredito_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbDebitoCredito);
    }

    public void setIndValidaDatos(boolean indValidaDatos) {
        this.indValidaDatos = indValidaDatos;
    }
}
