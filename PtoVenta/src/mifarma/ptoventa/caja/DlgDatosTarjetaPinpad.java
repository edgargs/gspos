package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
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

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.main.DlgProcesar;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.UtilityLectorTarjeta;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.pinpad.DlgInterfacePinpad;
import mifarma.ptoventa.recaudacion.DlgProcesarVentaCMR;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDatosTarjetaPinpad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDatosTarjetaPinpad.class);

    private FarmaTableModel tableModelFormasPago;

    private String mensajeError =
        "Atención: No se reconocio correctamente el numero de tarjeta.\nIntente nuevamente pasar la tarjeta por el lector o ingrese el numero manualmente";
    private Frame myParentFrame;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanel pnlInfo = new JPanel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private CardLayout cardLayout1 = new CardLayout();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelOrange lblMonto_T = new JLabelOrange();
    private JTextFieldSanSerif txtNroTarjeta = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDocIdentidad = new JTextFieldSanSerif();
    private JComboBox cmbNroCuotas = new JComboBox();
    private JLabelOrange lblTipoTarjeta_T = new JLabelOrange();
    private JLabelOrange lblTipoTarjeta = new JLabelOrange();
    private JLabelOrange lblmon = new JLabelOrange();
    private JLabelOrange lblmoneda = new JLabelOrange();
    private JButtonLabel lblNroTarjeta = new JButtonLabel();
    private JLabelOrange lblmensaje = new JLabelOrange();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    private JButtonLabel lblDocIdentidad = new JButtonLabel();
    private JButtonLabel lblNroCuotas = new JButtonLabel();
    private JComboBox cmbDebitoCredito = new JComboBox();

    private JButtonLabel lblDebitoCredito = new JButtonLabel();
    private DlgNuevoCobro pDlgNuevoCobro;

    //Variables para el pago con tarjeta
    private String bin = "";
    private String desc_prod = "";
    private String strCodFormaPago = "";
    private String strNombrePropTarjeta = "";
    private String strFechaVencTarjeta = "";

    private String strDescFormaPago = "";
    private String nroTarjeta = "";
    private String nroTarjetaBruto = "";

    private String infoTarj = "";
    private int cont = 0;
    private String mestarj = "";
    private String aniotarj = "";

    private ArrayList listInforTarje = new ArrayList();
    private ArrayList<String> arrayCodFormaPago = new ArrayList<>();

    private UtilityLectorTarjeta utilityLectorTarjeta = new UtilityLectorTarjeta();
    private int intCountEnter = 0;
    private String strIndLectorBanda = "N";
    private String vConciliacionOnline;

    FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();

    public DlgDatosTarjetaPinpad() {
        this(null, "", false);
    }

    public DlgDatosTarjetaPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(413, 313));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Pago con Tarjeta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.setFocusable(false);
        pnlInfo.setBounds(new Rectangle(10, 25, 390, 220));
        pnlInfo.setBackground(Color.white);
        pnlInfo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlInfo.setLayout(null);
        pnlInfo.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(10, 5, 390, 20));
        pnlTitle.setFocusable(false);
        jLabelWhite1.setText("Pago con tarjeta");
        jLabelWhite1.setBounds(new Rectangle(5, 0, 150, 20));
        jLabelWhite1.setFocusable(false);
        lblMonto_T.setText("Monto");
        lblMonto_T.setBounds(new Rectangle(15, 180, 80, 20));
        lblMonto_T.setFont(new Font("SansSerif", 1, 18));
        lblMonto_T.setFocusable(false);
        txtNroTarjeta.setBounds(new Rectangle(110, 20, 135, 20));
        txtNroTarjeta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtnrotarj_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtnrotarj_keyPressed(e);
            }
        });
        //txtNroTarjeta.setDocument(new FarmaLengthText(22));
        lblTipoTarjeta_T.setText("Tipo Tarjeta");
        lblTipoTarjeta_T.setBounds(new Rectangle(15, 50, 80, 15));
        lblTipoTarjeta_T.setFocusable(false);
        lblTipoTarjeta.setText("[TIPO]");
        lblTipoTarjeta.setBounds(new Rectangle(115, 50, 265, 15));
        lblTipoTarjeta.setForeground(new Color(43, 141, 39));
        lblTipoTarjeta.setFocusable(false);
        lblmon.setText("[MONTO]");
        lblmon.setBounds(new Rectangle(150, 180, 90, 20));
        lblmon.setForeground(new Color(49, 141, 43));
        lblmon.setFont(new Font("SansSerif", 1, 18));
        lblmon.setFocusable(false);
        lblmoneda.setText("S/.");
        lblmoneda.setBounds(new Rectangle(115, 180, 30, 20));
        lblmoneda.setForeground(new Color(49, 141, 43));
        lblmoneda.setFont(new Font("SansSerif", 1, 18));
        lblmoneda.setFocusable(false);
        lblNroTarjeta.setText("Nro. Tarjeta");
        lblNroTarjeta.setBounds(new Rectangle(15, 20, 75, 15));
        lblNroTarjeta.setForeground(new Color(255, 130, 14));
        lblNroTarjeta.setMnemonic('n');
        lblNroTarjeta.setFocusable(false);
        lblNroTarjeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNroTarjeta_actionPerformed(e);
            }
        });
        lblmensaje.setText("[MENSAJE]");
        lblmensaje.setBounds(new Rectangle(255, 20, 125, 15));
        lblmensaje.setForeground(new Color(49, 141, 43));
        lblmensaje.setFocusable(false);
        pnlTitle.add(jLabelWhite1, null);
        pnlFondo.add(pnlTitle, null);
        pnlFondo.add(pnlInfo, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEscape, null);
        pnlInfo.add(lblDocIdentidad, null);
        pnlInfo.add(lblNroCuotas, null);
        pnlInfo.add(cmbDebitoCredito, null);
        pnlInfo.add(lblDebitoCredito, null);
        pnlInfo.add(txtDocIdentidad, null);
        pnlInfo.add(cmbNroCuotas, null);
        pnlInfo.add(lblmensaje, null);
        pnlInfo.add(lblNroTarjeta, null);
        pnlInfo.add(lblmon, null);
        pnlInfo.add(lblmoneda, null);
        pnlInfo.add(lblTipoTarjeta, null);
        pnlInfo.add(lblTipoTarjeta_T, null);
        pnlInfo.add(lblMonto_T, null);
        pnlInfo.add(txtNroTarjeta, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        lblEscape.setBounds(new Rectangle(140, 255, 105, 25));
        lblEscape.setText("[ ESC ] Cancelar");
        lblEscape.setFocusable(false);
        lblF11.setBounds(new Rectangle(255, 255, 145, 25));
        lblF11.setText("[ F11 ] Cobro PINPAD");
        lblF11.setEnabled(false);
        lblF11.setFocusable(false);
        lblDebitoCredito.setText("Debito/Credito?");
        lblDebitoCredito.setFocusable(false);
        lblDebitoCredito.setBounds(new Rectangle(15, 75, 95, 20));
        lblDebitoCredito.setForeground(new Color(255, 130, 14));
        lblDebitoCredito.setMnemonic('e');
        lblDebitoCredito.setVisible(false);
        lblDebitoCredito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblDebitoCredito_actionPerformed(e);
            }
        });
        cmbDebitoCredito.setVisible(false);
        cmbDebitoCredito.setBounds(new Rectangle(110, 75, 140, 20));
        cmbDebitoCredito.addItem("Credito");
        cmbDebitoCredito.addItem("Debito");
        lblDocIdentidad.setText("Doc. Identidad");
        lblDocIdentidad.setBounds(new Rectangle(15, 110, 90, 20));
        lblDocIdentidad.setForeground(new Color(255, 130, 14));
        lblDocIdentidad.setMnemonic('d');
        lblDocIdentidad.setFocusable(false);
        lblDocIdentidad.setVisible(false);
        txtDocIdentidad.setVisible(false);
        txtDocIdentidad.setBounds(new Rectangle(110, 110, 140, 20));
        txtDocIdentidad.setLengthText(8);
        txtDocIdentidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDocIdentidad_keyPressed(e);
            }
        });
        lblNroCuotas.setText("Nro. Cuotas");
        lblNroCuotas.setBounds(new Rectangle(15, 145, 90, 20));
        lblNroCuotas.setForeground(new Color(255, 130, 14));
        lblNroCuotas.setMnemonic('c');
        lblNroCuotas.setFocusable(false);
        cmbNroCuotas.setBounds(new Rectangle(110, 145, 140, 20));

        lblNroCuotas.setVisible(false);
        cmbNroCuotas.setVisible(false);
        cmbNroCuotas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbNroCuotas_keyPressed(e);
            }
        });
        cmbDebitoCredito.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDebitoCredito_keyPressed(e);
            }
        });
        cmbDebitoCredito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmbDebitoCredito_actionPerformed(e);
            }
        });

    }

    private void inicializarVariables() {
        bin = "";
        desc_prod = "";
        strCodFormaPago = "";
        strDescFormaPago = "";
        nroTarjeta = "";
        txtNroTarjeta.setText("");
        nroTarjetaBruto = "";
        //txtDocIdentidad.setText("");
        //txtLote.setText("");
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
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if (FarmaVariables.vTipCambio == 0) {
            FarmaUtility.showMessage(this,
                                     "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción",
                                     null);
            cerrarVentana(false);
        } else {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(txtNroTarjeta);
            lblmon.setText(VariablesCaja.vValMontoPagado);
            inicializarVariables();
            lblmensaje.setText("");

            vConciliacionOnline = DlgProcesar.cargaIndConciliaconOnline();
            //ERIOS 21.11.2014 Verificar el indicador de SIX
            DlgProcesar.cargaIndServicioFarmaSix();
        }
    }

    private void chkkeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (validaCampos()) {
                log.debug("--Campos llenos--");
                conexionPinpad();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
            //((JComponent)e.getSource()).transferFocus();
            if (validaCampos())
                lblF11.setEnabled(true);
            else
                lblF11.setEnabled(false);

        }
    }

    private void txtnrotarj_keyTyped(KeyEvent e) { //FarmaUtility.admitirDigitos(txtnrotarj,e);
    }

    private void cargar_cmbCuotas() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbNroCuotas, ConstantsRecaudacion.NOM_HASTABLE_CMB_CUOTAS,
                                   "PTOVENTA_RECAUDACION.RCD_CUOTAS_CMR", parametros, true, true);
    }

    private void txtnrotarj_keyPressed(KeyEvent e) {
        String tmpNro = "";
        String tmpNomb = "";
        String tmpFecha = "";
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            intCountEnter++;
        if (e.getKeyCode() == KeyEvent.VK_B)
            strIndLectorBanda = "S";
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (strIndLectorBanda.equals("S") && intCountEnter == 1) {
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
                if (buscarInfotarjeta()) {
                    if (hablitarTipoTarjeta()) {
                        lblTipoTarjeta.setText(desc_prod + "/" + strDescFormaPago);
                        FarmaUtility.moveFocus(cmbDebitoCredito);

                        //LLEIVA 16-Sep-2013 Se desactiva los combos para luego activarlos
                        lblNroCuotas.setVisible(false);
                        cmbNroCuotas.setVisible(false);
                        lblDebitoCredito.setVisible(false);
                        cmbDebitoCredito.setVisible(false);

                        //GFonseca 03/09/2013. Se agrega el campo de cuotas solo cuando es tarjeta CMR
                        //LLEIVA 10-Abr-2014 Solo se procesa en SIX si su indicador esta activo
                        if (strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_CMR) &&
                            VariablesPtoVenta.vIndFarmaSix.equals("S")) {
                            cargar_cmbCuotas();
                            //Temporalmente se deshabilita, hasta que se implemente funcionalidad de calculo de pago por cuota
                            lblNroCuotas.setVisible(true); //lblNroCuotas.setVisible(true);
                            cmbNroCuotas.setVisible(true); //cmbNroCuotas.setVisible(true);

                            //cmbDebitoCredito.setEnabled(false);

                            lblDebitoCredito.setVisible(true);
                            cmbDebitoCredito.setVisible(true);
                            verificarCredDeb();
                        }
                        //LLEIVA 10-Abr-2014 Si es CMR y el indicador de SIX no es activo, se procesa por pinpad
                        else if (ConstantsPtoVenta.FORPAG_MC_PINPAD.equalsIgnoreCase(strCodFormaPago) ||
                                 ConstantsPtoVenta.FORPAG_VISA_PINPAD.equalsIgnoreCase(strCodFormaPago) ||
                                 ConstantsPtoVenta.FORPAG_DINERS.equalsIgnoreCase(strCodFormaPago) ||
                                 ConstantsPtoVenta.FORPAG_AMEX.equalsIgnoreCase(strCodFormaPago) ||
                                 (ConstantsPtoVenta.FORPAG_CMR.equalsIgnoreCase(strCodFormaPago) &&
                                  !VariablesPtoVenta.vIndFarmaSix.equals("S"))) { //LLEIVA 16-Sep-2013 Se habilita el combo de DebitoCredito
                            lblDebitoCredito.setVisible(true);
                            cmbDebitoCredito.setVisible(true);
                            verificarCredDeb();
                        }

                        if (validaCampos()) {
                            lblF11.setEnabled(true);
                        } else {
                            lblF11.setEnabled(false);
                        }
                    } else {
                        FarmaUtility.showMessage(this, mensajeError, null);
                        lblF11.setEnabled(false);
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
                }
            }
        } else {
            chkkeyPressed(e);
        }
    }

    private void lblDebitoCredito_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbDebitoCredito);
    }

    private boolean buscarInfotarjeta() {
        String vNroTarjeta = "";
        if ("".equals(nroTarjetaBruto))
            vNroTarjeta = txtNroTarjeta.getText();
        else
            vNroTarjeta = nroTarjetaBruto;

        boolean flag = true;
        ArrayList lista = new ArrayList();
        try { //DBPtoVenta.obtenerInfoTarjeta(lista,txtNroTarjeta.getText().trim());
            DBPtoVenta.obtenerInfoTarjeta(lista, vNroTarjeta.trim(), ConstantsCaja.TIPO_ORIGEN_PAGO_PINPAD);
        } catch (SQLException e) {
            log.error("", e);
        }
        if (lista.size() > 0) {
            ArrayList reg = (ArrayList)lista.get(0);
            bin = (String)reg.get(0);
            desc_prod = (String)reg.get(1);
            strCodFormaPago = (String)reg.get(2);
            strDescFormaPago = (String)reg.get(3);
            //nroTarjeta=txtNroTarjeta.getText();
            nroTarjeta = vNroTarjeta;
            //LEIVA 06-Sep-2013 Modificacion para enmascaramiento de tarjeta
            nroTarjetaBruto = vNroTarjeta;
            txtNroTarjeta.setText(utilityLectorTarjeta.enmascararTarjeta(vNroTarjeta));
            txtNroTarjeta.setEditable(false);
        } else { //si no se encuentra la información del bin de tarjeta, se lanza una ventan para decidir que forma de pago procesar
            if (vNroTarjeta.length() > 0 && (vNroTarjeta.length() == 15 || vNroTarjeta.length() == 16)) {
                DlgEleccionTarjeta dlgEleccionTarjeta = new DlgEleccionTarjeta(myParentFrame, "", true);
                dlgEleccionTarjeta.setValores(txtNroTarjeta.getText(), "PINPAD");
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
                FarmaUtility.showMessage(this, mensajeError, txtNroTarjeta);
            }
        }

        //ERIOS 2.2.8 Valida numero de tarjeta
        //ERIOS 2.2.9 Diners: 14 digitos
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

    private boolean validaCampos() {
        boolean flag = true;
        if (nroTarjetaBruto.trim().equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta", txtNroTarjeta);
        } else if (Double.parseDouble(nroTarjetaBruto.trim()) == 0) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta valido", txtNroTarjeta);
            txtNroTarjeta.setText("");
        } else if (!(nroTarjetaBruto.trim()).equalsIgnoreCase(nroTarjeta)) {
            flag = false;
            FarmaUtility.showMessage(this, "No debio cambiar el Nro. de Tarjeta", txtNroTarjeta);
            inicializarVariables();
        } else if (bin.equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta valido", txtNroTarjeta);
            txtNroTarjeta.setText("");
        } else if ((txtDocIdentidad.getText().trim().length() == 0 ||
                    txtDocIdentidad.getText().trim().length() != 8) &&
                   cmbDebitoCredito.getSelectedItem().toString().equals("Credito")) {
            flag = false;
            FarmaUtility.showMessage(this, "Si va a ingresar DNI debe ser valido", cmbDebitoCredito);
            txtDocIdentidad.setText("");
        }
        return flag;
    }

    private void lblNroTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroTarjeta);
    }

    private boolean hablitarTipoTarjeta() {
        return arrayCodFormaPago.contains(strCodFormaPago);
    }

    public void setArrayFormPago(ArrayList<String> codFormaPago) {
        this.arrayCodFormaPago = codFormaPago;
    }

    public void setTableModelFormasPago(FarmaTableModel tableModelFormasPago) {
        this.tableModelFormasPago = tableModelFormasPago;
    }

    private void conexionPinpad() {
        String codVoucher = "";

        //LLEIVA 10-Ene-2014 Se añadio validación se Stock antes de iniciar la transaccion con pinpad
        if (!UtilityCaja.existeStockPedido(this, VariablesCaja.vNumPedVta))
            return;

        //INICIO DE VALIDACIONES
        if (!UtilityCaja.validacionesCobroPedido(false, this, this.tableModelFormasPago))
            return;

        try {
            //LLEIVA 16-Ago-2013 - Se verifica si el pago se hace con Pinpad
            //LLEIVA 10-Abr-2014 Si es CMR y el indicador de SIX no es activo, se procesa por pinpad
            if (ConstantsPtoVenta.FORPAG_VISA_PINPAD.equalsIgnoreCase(strCodFormaPago) ||
                ConstantsPtoVenta.FORPAG_MC_PINPAD.equalsIgnoreCase(strCodFormaPago) ||
                ConstantsPtoVenta.FORPAG_DINERS.equalsIgnoreCase(strCodFormaPago) ||
                ConstantsPtoVenta.FORPAG_AMEX.equalsIgnoreCase(strCodFormaPago) ||
                (ConstantsPtoVenta.FORPAG_CMR.equalsIgnoreCase(strCodFormaPago) &&
                 !VariablesPtoVenta.vIndFarmaSix.equals("S"))) {
                /*boolean flag = true;

                //LLEIVA 15-Oct-2013 se verifica si existe conexion con pinpad
                if(ConstantsPtoVenta.FORPAG_VISA_PINPAD.equalsIgnoreCase(strCodFormaPago))
                {
                    ManejadorTramaRetorno mtr = new ManejadorTramaRetorno();
                    flag = mtr.loadLibrary();
                }
                else if(ConstantsPtoVenta.FORPAG_MC_PINPAD.equalsIgnoreCase(strCodFormaPago))
                {
                    flag = true;
                }

                if(flag)
                {*/

                //ERIOS 10.10.2013 Nuevo proceso cobro
                //ERIOS 19.11.2013 Se comenta por si algun dia cambia el procedimiento
                /*if(!VariablesCaja.vIndPedidoCobrado){
                        pDlgNuevoCobro.procesarNuevoCobro();
                        if (!FarmaVariables.vAceptar) {
                            cerrarVentana(false);
                            return;
                        }
                    }*/

                //LLEIVA 05-Julio-2013 - Se llama a la conexion directa con el Pinpad VISA-MC
                DlgInterfacePinpad dlgInterfacePinpad = new DlgInterfacePinpad(myParentFrame, "", true);
                dlgInterfacePinpad.inicializarDatos(txtNroTarjeta.getText(), lblTipoTarjeta.getText(),
                                                    strCodFormaPago);
                dlgInterfacePinpad.setNroTarjetaBruto(nroTarjetaBruto);
                dlgInterfacePinpad.setVisible(true);
                codVoucher = dlgInterfacePinpad.getNumAutorizacion();
                log.debug("Codigo Autorizacion Pinpad:" + codVoucher);
                if (FarmaVariables.vAceptar) {
                    log.debug("Termino cobreo pinpad: " + codVoucher);
                    conciliacionPinpad(dlgInterfacePinpad);
                    VariablesCaja.vValMontoPagadoTarj = VariablesCaja.vValMontoPagado;
                }
                dlgInterfacePinpad.dispose();
                dlgInterfacePinpad = null;
                /*}
                else
                {   FarmaVariables.vAceptar = false;
                    FarmaUtility.showMessage(this,
                                             "No se puede realizar una conexion al pinpad correspondiente.",
                                             null);
                }*/

            } else if (strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_CMR)) {

                //ERIOS 10.10.2013 Nuevo proceso cobro
                //ERIOS 19.11.2013 Se comenta por si algun dia cambian el procedimiento
                /*if(!VariablesCaja.vIndPedidoCobrado){
                    pDlgNuevoCobro.procesarNuevoCobro();
                }*/

                //ERIOS 19.11.2013 Valida tamanio numero tarjeta
                if (!(nroTarjetaBruto.trim().length() == 16)) {
                    FarmaUtility.showMessage(this,
                                             "Verifique el número de la tarjeta.\n Debe contener los 16 dígitos.",
                                             null);
                    return;
                }
                String strNumCuotas =
                    FarmaLoadCVL.getCVLCode(ConstantsRecaudacion.NOM_HASTABLE_CMB_CUOTAS, cmbNroCuotas.getSelectedIndex());

                DlgProcesarVentaCMR dlgProcesarVentaCMR = new DlgProcesarVentaCMR(myParentFrame, "", true);
                dlgProcesarVentaCMR.procesarVentaCMR(myParentFrame, strNumCuotas, nroTarjetaBruto.trim(),
                                                     txtDocIdentidad.getText().trim(),
                                                     cmbDebitoCredito.getSelectedItem().toString());
                dlgProcesarVentaCMR.setStrIndProc(ConstantsRecaudacion.RCD_IND_PROCESO_PAGO);
                dlgProcesarVentaCMR.mostrar();
                codVoucher = dlgProcesarVentaCMR.getNumAutorizacion();

            }
        } catch (Exception e) {
            log.error("Ocurrio un error al realizar el cobro con el pinpad", e);
            FarmaVariables.vAceptar = false;
        }

        if (FarmaVariables.vAceptar) { //txtLote.setText(FarmaUtility.completeWithSymbol(txtLote.getText(),3,"0","I"));
            log.info("Ingreso a grabar forma de pago pinpad");
            VariablesCaja.vCodFormaPago = strCodFormaPago;
            VariablesCaja.vDescFormaPago = strDescFormaPago;
            //VariablesCaja.vNumTarjCred = txtNroTarjeta.getText();
            VariablesCaja.vNumTarjCred = nroTarjetaBruto;

            //VariablesCaja.vTipoTarjeta = desc_prod+"/"+strDescFormaPago;

            VariablesCaja.vFecVencTarjCred = strFechaVencTarjeta; //dlgInterfacePinpad.getFechaExpiracion();
            VariablesCaja.vNomCliTarjCred = strNombrePropTarjeta; //dlgInterfacePinpad.getNombreCliente();

            //20.03.2013 LTERRAZOS Se agrega 3 parametros de pagos con tarjeta
            if (txtDocIdentidad.isEnabled())
                VariablesCaja.vDNITarj = "";
            else
                VariablesCaja.vDNITarj = txtDocIdentidad.getText();

            VariablesCaja.vCodVoucher = codVoucher;
            VariablesCaja.vCodLote = "";
            log.info("Termino de grabar Forma Pago PinPad  ");
            log.info("CodFormaPago--> " + VariablesCaja.vCodFormaPago);
            log.info("DescFormaPago--> " + VariablesCaja.vDescFormaPago);
            log.info("NumTarjCred--> " + VariablesCaja.vNumTarjCred);
            log.info("FecVencTarjCred--> " + VariablesCaja.vFecVencTarjCred);
            log.info("NomCliTarjCred--> " + VariablesCaja.vNomCliTarjCred);
            log.info("DNITarj--> " + VariablesCaja.vDNITarj);
            log.info("CodVoucher--> " + VariablesCaja.vCodVoucher);
            log.info("CodLote--> " + VariablesCaja.vCodLote);

            cerrarVentana(true);
        }
    }

    private void txtDocIdentidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (cmbNroCuotas.isVisible()) {
                FarmaUtility.moveFocus(cmbNroCuotas);
            } else {
                FarmaUtility.moveFocus(cmbDebitoCredito);
            }
        }

        chkkeyPressed(e);
    }

    private void cmbNroCuotas_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbDebitoCredito);
        } else {
            chkkeyPressed(e);
        }
    }

    private void cmbDebitoCredito_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtDocIdentidad.isVisible()) {
                FarmaUtility.moveFocus(txtDocIdentidad);
            }
        } else {
            chkkeyPressed(e);
        }
    }

    private void conciliacionPinpad(DlgInterfacePinpad dlgInterfacePinpad) {
        String vSalida = "";

        //ERIOS 2.2.8 Conciliacion offline
        if (vConciliacionOnline.equals(FarmaConstants.INDICADOR_N)) {
            log.debug("Conciliacilion offline");
            return;
        }

        try {
            String PLOCAL = facadeRecaudacion.getCodLocalMigra();
            String PID_VENDEDOR = facadeRecaudacion.obtenerDniUsuario(FarmaVariables.vNuSecUsu).trim();
            String PFECHA_VENTA = "";
            try {
                PFECHA_VENTA = FarmaSearch.getFechaHoraBD(1);
            } catch (Exception e) {
                log.error("", e);
            }

            double PMONTO_VENTA = FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado);

            String tempNumCuotas =
                (dlgInterfacePinpad.getCantCuotas() == null) ? "0" : dlgInterfacePinpad.getCantCuotas();
            double PNUM_CUOTAS = FarmaUtility.getDecimalNumber(tempNumCuotas); //solo en venta

            String PCOD_AUTORIZACION = dlgInterfacePinpad.getNumAutorizacion();
            String PTRACK2 = ""; //solo en venta
            String PCOD_AUTORIZACION_PRE = ""; //solo en venta cuando es anulacion
            double PFPA_VALORXCUOTA = 0; //solo en venta
            int PCAJA = Integer.parseInt(VariablesCaja.vNumCaja);
            String PTIPO_TRANSACCION =
                ConstantsRecaudacion.RCD_MODO_VENTA; //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion
            String PCODISERV =
                ""; //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank
            String PFPA_NUM_OBJ_PAGO =
                nroTarjetaBruto.trim(); //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
            String PNOMBCLIE = "";
            long PVOUCHER = Long.parseLong(VariablesVentas.vNum_Ped_Vta); //Nro de Comprobante
            long PNRO_COMP_ANU = 0; //Anulacion-Nro de Comprobante origen
            String PFECH_COMP_ANU = ""; //Anulacion-Fecha Origen del Comprobante
            String PCODIAUTOORIG = ""; //Anulacion-Codigo autorizacion Origen
            double PFPA_TIPOCAMBIO = FarmaVariables.vTipCambio;
            long PFPA_NROTRACE = 0; //PARA TODOS LOS CASOS SERA CERO
            /*
            PCOD_ALIANZA      IN NUMBER,      -> Para Recaudacion: Ripley 12,  Citibank 16, Banco Finan 17 , Cmr 7 , Claro 9,
                                                     Para Venta: CMR(7), Claro(9), Visa(10), Ripley (12),Mastercard(13),American (14), Dinners(15),Citibank(16),Banco Finan(17)
             */

            int PCOD_ALIANZA = facadeRecaudacion.getCodigoAlianzaConciliacion(strCodFormaPago);
            int PCOD_MONEDA_TRX =
                Integer.parseInt(cmbDebitoCredito.getSelectedItem().toString().equals("Credito") ? "5" : "6");

            ////Recaudacion 1 Soles , 2 Dolares, en venta tipo 7(CMR),9 Ripley, 5 Tcredit, 6 Tdebito ,
            String PMON_ESTPAGO = ConstantsRecaudacion.RCD_COD_MONEDA_SOLES;


            //LLEIVA 04-Oct-2013 Guarda un log del resultado de conciliacion
            String descProceso = "";
            if (ConstantsPtoVenta.FORPAG_VISA_PINPAD.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_VENTA_PINPAD_VISA;
            else if (ConstantsPtoVenta.FORPAG_MC_PINPAD.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_VENTA_PINPAD_MASTERCARD;
            else if (ConstantsPtoVenta.FORPAG_DINERS.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_VENTA_PINPAD_DINERS;
            else if (ConstantsPtoVenta.FORPAG_AMEX.equals(strCodFormaPago))
                descProceso = ConstantsCajaElectronica.GUARD_CONC_VENTA_PINPAD_AMEX;

            try {
                vSalida =
                        facadeRecaudacion.setDatosRecauConciliacion(PLOCAL, PID_VENDEDOR, PFECHA_VENTA, PMONTO_VENTA, PNUM_CUOTAS,
                                                                    PCOD_AUTORIZACION, PTRACK2, PCOD_AUTORIZACION_PRE,
                                                                    PFPA_VALORXCUOTA, PCAJA, PTIPO_TRANSACCION,
                                                                    PCODISERV, PFPA_NUM_OBJ_PAGO, PNOMBCLIE, PVOUCHER,
                                                                    PNRO_COMP_ANU, PFECH_COMP_ANU, PCODIAUTOORIG,
                                                                    PFPA_TIPOCAMBIO, PFPA_NROTRACE, PCOD_ALIANZA,
                                                                    PCOD_MONEDA_TRX, PMON_ESTPAGO, descProceso);
            } catch (Exception e) {
                log.error("", e);
            }

            log.debug("Resultado conciliacion: " + vSalida);

        } catch (Exception e) {
            vSalida = "NOK";
            log.error("", e);
        }
    }

    private void cmbDebitoCredito_actionPerformed(ActionEvent e) {
        verificarCredDeb();
    }

    private void verificarCredDeb() {
        if (cmbDebitoCredito.getSelectedItem().toString().equals("Credito")) {
            txtDocIdentidad.setEnabled(true);
            txtDocIdentidad.setVisible(true);
            lblDocIdentidad.setVisible(true);
            //LLEIVA 10-Abr-2014 Si es CMR y el indicador de SIX no es activo, se procesa por pinpad
            if (strCodFormaPago.equals(ConstantsPtoVenta.FORPAG_CMR) && VariablesPtoVenta.vIndFarmaSix.equals("S")) {
                lblNroCuotas.setVisible(true);
                cmbNroCuotas.setVisible(true);
            }
            txtDocIdentidad.grabFocus();
        } else {
            txtDocIdentidad.setEnabled(false);
            txtDocIdentidad.setVisible(false);
            lblDocIdentidad.setVisible(false);
            lblNroCuotas.setVisible(false);
            cmbNroCuotas.setVisible(false);
            lblF11.setEnabled(true);
        }
    }

    void setDlgNuevoCobro(DlgNuevoCobro dlgNuevoCobro) {
        this.pDlgNuevoCobro = dlgNuevoCobro;
    }

    public static void reprocesarConciliaciones() {
        log.debug("********** INICIO DE REPROCESO DE CONCILIACIONES *********");

        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        ArrayList<ArrayList<String>> listado = null;

        try {
            listado = facadeRecaudacion.getListaConciliacionNOK();
        } catch (Exception e) {
            log.error("", e);
        }

        if (listado != null) {
            for (int i = 0; i < listado.size(); i++) {
                try {
                    ArrayList<String> listadoTemp = listado.get(i);

                    if (listadoTemp != null) {
                        String identificador = (String)((listadoTemp.get(0) == null) ? "" : listadoTemp.get(0));
                        String PLOCAL = (String)((listadoTemp.get(1) == null) ? "" : listadoTemp.get(1));
                        String PID_VENDEDOR = (String)((listadoTemp.get(2) == null) ? "" : listadoTemp.get(2));
                        String PFECHA_VENTA = (String)((listadoTemp.get(3) == null) ? "" : listadoTemp.get(3));
                        double PMONTO_VENTA =
                            FarmaUtility.getDecimalNumber(((listadoTemp.get(4) == null) ? "" : listadoTemp.get(4)));
                        double PNUM_CUOTAS =
                            FarmaUtility.getDecimalNumber(((listadoTemp.get(5) == null) ? "" : listadoTemp.get(5)));
                        String PCOD_AUTORIZACION = (String)((listadoTemp.get(6) == null) ? "" : listadoTemp.get(6));
                        String PTRACK2 = (String)((listadoTemp.get(7) == null) ? "" : listadoTemp.get(7));
                        String PCOD_AUTORIZACION_PRE =
                            (String)((listadoTemp.get(8) == null) ? "" : listadoTemp.get(8));
                        int PFPA_VALORXCUOTA =
                            Integer.parseInt((listadoTemp.get(9) == null) ? "" : listadoTemp.get(9));
                        int PCAJA = Integer.parseInt((listadoTemp.get(10) == null) ? "" : listadoTemp.get(10));
                        String PTIPO_TRANSACCION = (String)((listadoTemp.get(11) == null) ? "" : listadoTemp.get(11));
                        String PCODISERV = (String)((listadoTemp.get(12) == null) ? "" : listadoTemp.get(12));
                        String PFPA_NUM_OBJ_PAGO = (String)((listadoTemp.get(13) == null) ? "" : listadoTemp.get(13));
                        String PNOMBCLIE = (String)((listadoTemp.get(14) == null) ? "" : listadoTemp.get(14));
                        long PVOUCHER = Long.parseLong((listadoTemp.get(15) == null) ? "" : listadoTemp.get(15));
                        long PNRO_COMP_ANU = Long.parseLong((listadoTemp.get(16) == null) ? "" : listadoTemp.get(16));
                        String PFECH_COMP_ANU = (String)((listadoTemp.get(17) == null) ? "" : listadoTemp.get(17));
                        String PCODIAUTOORIG = (String)((listadoTemp.get(18) == null) ? "" : listadoTemp.get(18));
                        double PFPA_TIPOCAMBIO =
                            FarmaUtility.getDecimalNumber((listadoTemp.get(19) == null) ? "" : listadoTemp.get(19));
                        long PFPA_NROTRACE = Long.parseLong((listadoTemp.get(20) == null) ? "" : listadoTemp.get(20));
                        int PCOD_ALIANZA = Integer.parseInt((listadoTemp.get(21) == null) ? "" : listadoTemp.get(21));
                        int PCOD_MONEDA_TRX =
                            Integer.parseInt((listadoTemp.get(22) == null) ? "" : listadoTemp.get(22));
                        String PMON_ESTPAGO = (String)((listadoTemp.get(23) == null) ? "" : listadoTemp.get(23));

                        String vSalida =
                            facadeRecaudacion.setDatosRecauConciliacion(PLOCAL, PID_VENDEDOR, PFECHA_VENTA,
                                                                        PMONTO_VENTA, PNUM_CUOTAS, PCOD_AUTORIZACION,
                                                                        PTRACK2, PCOD_AUTORIZACION_PRE,
                                                                        PFPA_VALORXCUOTA, PCAJA, PTIPO_TRANSACCION,
                                                                        PCODISERV, PFPA_NUM_OBJ_PAGO, PNOMBCLIE,
                                                                        PVOUCHER, PNRO_COMP_ANU, PFECH_COMP_ANU,
                                                                        PCODIAUTOORIG, PFPA_TIPOCAMBIO, PFPA_NROTRACE,
                                                                        PCOD_ALIANZA, PCOD_MONEDA_TRX, PMON_ESTPAGO,
                                                                        "Reproceso");

                        if ("OK".equalsIgnoreCase(vSalida))
                            facadeRecaudacion.actualizaRegConciliacion(identificador);

                        log.debug("Resultado de conciliacion para registro " + identificador + ": " + vSalida);
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        log.debug("********** FIN DE REPROCESO DE CONCILIACIONES *********");
    }
}
