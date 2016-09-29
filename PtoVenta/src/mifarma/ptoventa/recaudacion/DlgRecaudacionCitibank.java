package mifarma.ptoventa.recaudacion;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import com.gs.mifarma.componentes.JTextFieldTarjeta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recaudacion.reference.VariablesRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecaudacionCitibank extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRecaudacionCitibank.class);

    Frame myFrameParent;
    private JLabel lblTipoTarjeta_T = new JLabel();

    private JComboBox cmbEstCta = new JComboBox();
    private JTextFieldSanSerif txtMontoPagar = new JTextFieldSanSerif();

    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JButton btnTarjeta = new JButton();
    private JTextFieldTarjeta txtTarjeta = new JTextFieldTarjeta();
    private JPanel jPanel3 = new JPanel();
    private JLabel lblUsuario = new JLabel();
    private JLabel txtUsuario = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel txtFecha = new JLabel();
    private JLabel lblTipoTarjeta = new JLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel lblEstCta = new JButtonLabel();
    private JButtonLabel jButtonLabel2 = new JButtonLabel();
    private JButton btnTarjeta1 = new JButton();
    private JTextFieldTarjeta txtTarjetaPrimerIngreso = new JTextFieldTarjeta();

    private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();

    UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    public DlgRecaudacionCitibank() {
        this(null, "", false);
    }

    public DlgRecaudacionCitibank(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myFrameParent = parent;
        try {
            jbInit();
            initialize();
            utilityRecaudacion.initMensajesVentana(this, txtFecha, txtUsuario, ConstantsRecaudacion.TIPO_REC_CITI);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(454, 370));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Recaudación Citibank");
        this.setDefaultCloseOperation(0);

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        jPanel1.setLayout(null);
        jPanel1.setForeground(Color.white);
        jPanel1.setBackground(Color.white);
        jPanel1.setSize(new Dimension(452, 290));
        btnTarjeta.setText("Reingrese Tarjeta: ");
        btnTarjeta.setBackground(Color.white);
        btnTarjeta.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnTarjeta.setBorderPainted(false);
        btnTarjeta.setContentAreaFilled(false);
        btnTarjeta.setDefaultCapable(false);
        btnTarjeta.setFocusPainted(false);
        btnTarjeta.setFont(new Font("SansSerif", 1, 11));
        btnTarjeta.setForeground(new Color(255, 130, 14));
        btnTarjeta.setHorizontalAlignment(SwingConstants.LEFT);
        btnTarjeta.setMnemonic('R');
        btnTarjeta.setRequestFocusEnabled(false);
        btnTarjeta.setBounds(new Rectangle(10, 45, 110, 20));
        btnTarjeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTarjeta_actionPerformed(e);
            }
        });

        txtTarjeta.setBounds(new Rectangle(125, 45, 165, 20));

        lblTipoTarjeta_T.setText("Tipo Tarjeta");
        lblTipoTarjeta_T.setBounds(new Rectangle(10, 85, 70, 15));
        lblTipoTarjeta_T.setFont(new Font("SansSerif", 1, 11));
        lblTipoTarjeta_T.setForeground(new Color(255, 130, 14));
        txtMontoPagar.setBounds(new Rectangle(125, 145, 80, 20));
        txtMontoPagar.setLengthText(9);
        txtMontoPagar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMontoPagar_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMontoPagar_keyTyped(e);
            }
        });
        cmbEstCta.setBounds(new Rectangle(125, 110, 80, 20));
        cmbEstCta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbEstCta_keyPressed(e);
            }
        });
        cmbEstCta.addItem("Soles");
        cmbEstCta.addItem("Dolares");
        jPanel3.setBounds(new Rectangle(20, 20, 410, 55));
        jPanel3.setBackground(new Color(43, 141, 39));
        jPanel3.setForeground(new Color(0, 132, 66));
        jPanel3.setLayout(null);
        lblUsuario.setText("Usuario :");
        lblUsuario.setSize(new Dimension(30, 20));
        lblUsuario.setFont(new Font("Dialog", 1, 11));
        lblUsuario.setBounds(new Rectangle(15, 20, 55, 20));
        lblUsuario.setForeground(Color.white);
        txtUsuario.setBounds(new Rectangle(70, 20, 100, 20));
        txtUsuario.setFont(new Font("Dialog", 1, 11));
        txtUsuario.setForeground(Color.white);
        lblFecha.setText("Fecha :");
        lblFecha.setBounds(new Rectangle(190, 20, 50, 20));
        lblFecha.setForeground(Color.white);
        lblFecha.setFont(new Font("Dialog", 1, 11));
        lblFecha.setSize(new Dimension(75, 20));
        txtFecha.setForeground(Color.white);
        txtFecha.setFont(new Font("Dialog", 1, 11));
        txtFecha.setBounds(new Rectangle(240, 20, 155, 20));
        lblTipoTarjeta.setText("[TIPO]");
        lblTipoTarjeta.setForeground(SystemColor.window);
        lblTipoTarjeta.setFont(new Font("SansSerif", 1, 11));
        lblTipoTarjeta.setSize(new Dimension(35, 20));
        lblTipoTarjeta.setVerticalAlignment(SwingConstants.TOP);
        lblTipoTarjeta.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblTipoTarjeta.setBounds(new Rectangle(5, 5, 155, 15));
        jPanelTitle1.setBounds(new Rectangle(125, 75, 165, 25));
        lblEstCta.setText("Estado de Cuenta:");
        lblEstCta.setBounds(new Rectangle(10, 110, 105, 20));
        lblEstCta.setForeground(new Color(255, 130, 14));
        lblEstCta.setMnemonic('e');
        lblEstCta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblEstCta_actionPerformed(e);
            }
        });
        jButtonLabel2.setText("Monto a Pagar:");
        jButtonLabel2.setBounds(new Rectangle(10, 145, 100, 20));
        jButtonLabel2.setForeground(new Color(255, 130, 14));
        jButtonLabel2.setMnemonic('m');
        jButtonLabel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel2_actionPerformed(e);
            }
        });
        btnTarjeta1.setText("Tarjeta: ");
        btnTarjeta1.setBackground(Color.white);
        btnTarjeta1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnTarjeta1.setBorderPainted(false);
        btnTarjeta1.setContentAreaFilled(false);
        btnTarjeta1.setDefaultCapable(false);
        btnTarjeta1.setFocusPainted(false);
        btnTarjeta1.setFont(new Font("SansSerif", 1, 11));
        btnTarjeta1.setForeground(new Color(255, 130, 14));
        btnTarjeta1.setHorizontalAlignment(SwingConstants.LEFT);
        btnTarjeta1.setMnemonic('T');
        btnTarjeta1.setRequestFocusEnabled(false);
        btnTarjeta1.setBounds(new Rectangle(10, 15, 50, 20));
        btnTarjeta1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTarjeta_actionPerformed(e);
            }
        });
        txtTarjetaPrimerIngreso.setBounds(new Rectangle(125, 15, 165, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(240, 300, 90, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(340, 220, 90, 20));
        lblEsc.setBounds(new Rectangle(340, 300, 90, 20));
        jPanel2.setBounds(new Rectangle(20, 80, 410, 205));
        jPanel2.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jPanel2.setBackground(Color.white);
        jPanel2.setLayout(null);
        jPanel2.setForeground(Color.white);
        jPanel3.add(txtFecha, null);
        jPanel3.add(lblFecha, null);

        jPanel3.add(txtUsuario, null);
        jPanel3.add(lblUsuario, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(jPanel3, null);
        jPanel1.add(lblF11, null);
        jPanel1.add(lblEsc, null);
        jPanel2.add(txtTarjetaPrimerIngreso, null);
        jPanel2.add(btnTarjeta1, null);
        jPanel2.add(jButtonLabel2, null);
        jPanelTitle1.add(lblTipoTarjeta, null);
        jPanel2.add(jPanelTitle1, null);
        jPanel2.add(lblTipoTarjeta_T, null);
        jPanel2.add(cmbEstCta, null);
        jPanel2.add(txtMontoPagar, null);
        jPanel2.add(btnTarjeta, null);
        jPanel2.add(txtTarjeta, null);
        jPanel2.add(lblEstCta, null);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        inicializaTxtTarjeta();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void inicializaTxtTarjeta() {
        txtTarjeta.setLblTipTarjD(lblTipoTarjeta);
        //txtTarjeta.setTxtMontoPagar(txtMontoPagar);
        txtTarjeta.setCompNextFocus(cmbEstCta);
        txtTarjeta.setLblF11(lblF11);
        txtTarjeta.setMyParentFrame(this);

        //ERIOS 06.11.2013 Formas de pago
        ArrayList<String> arrayCodFormaPago = new ArrayList<String>();
        arrayCodFormaPago.add(ConstantsRecaudacion.FORMA_PAGO_CITIBANK); // "00082"//TODO Mejorar
        txtTarjeta.setArrayFormPago(arrayCodFormaPago);

        //ERIOS 2.2.8 Validacion de accion F11
        lblF11.setEnabled(false);

        //ERIOS 2.4.3 Validacion de doble digitacion
        txtTarjetaPrimerIngreso.setPrimerIngreso(true);
        txtTarjetaPrimerIngreso.setArrayFormPago(arrayCodFormaPago);
        txtTarjetaPrimerIngreso.setCompNextFocus(txtTarjeta);
        txtTarjetaPrimerIngreso.setMyParentFrame(this);
    }

    private void this_windowOpened(WindowEvent e) {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if (VariablesRecaudacion.vTipoCambioVenta == 0) {
            FarmaUtility.showMessage(this,
                                     "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción",
                                     null);
            cerrarVentana(false);
        } else {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(txtTarjetaPrimerIngreso);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void cmbEstCta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtMontoPagar.requestFocus(true);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtMontoPagar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            cmbEstCta.requestFocus(true);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtMontoPagar_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMontoPagar, e);
    }

    private void lblEstCta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbEstCta);
    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMontoPagar);
    }

    private void btnTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtTarjeta);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e) && lblF11.isEnabled()) {
            if (utilityPtoVenta.validarCampTxtField(this, txtTarjeta) &&
                utilityPtoVenta.validarCampTxtField(this, txtMontoPagar)) {
                //ERIOS 2.4.3 Valida ingreso de tarjetas
                if (!txtTarjeta.getNroTarjeta().equals(txtTarjetaPrimerIngreso.getNroTarjeta())) {
                    FarmaUtility.showMessage(this, "Los numeros ingresados no coinciden.", null);
                    return;
                }
                cobrarRecaudacion();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cobrarRecaudacion() {

        String strEstCta = "";
        String strMonedaPago = "";
        String NumTarjeta = txtTarjeta.getNroTarjeta();
        double dCuantoPaga = FarmaUtility.getDecimalNumber(txtMontoPagar.getText());
        double dMontoPagarSol = 0.00;
        double dMontoPagarDol = 0.00;
        double dTotalVenta = 0.00;

        if (cmbEstCta.getSelectedIndex() == 0) {
            strEstCta = ConstantsRecaudacion.EST_CTA_SOLES;
            strMonedaPago = FarmaConstants.MONEDA_SOLES;
            dMontoPagarSol = dCuantoPaga;
            dTotalVenta = dMontoPagarSol;
        } else if (cmbEstCta.getSelectedIndex() == 1) {
            strEstCta = ConstantsRecaudacion.EST_CTA_DOLARES;
            strMonedaPago = FarmaConstants.MONEDA_DOLARES;
            dMontoPagarDol = dCuantoPaga;
            dTotalVenta = dMontoPagarDol * VariablesRecaudacion.vTipoCambioVenta;
        }

        ArrayList<Object> arrayCabecera = new ArrayList<Object>();
        /*0*/arrayCabecera.add(FarmaVariables.vCodGrupoCia);
        /*1*/arrayCabecera.add(FarmaVariables.vCodCia);
        /*2*/arrayCabecera.add(FarmaVariables.vCodLocal);
        /*3*/arrayCabecera.add(NumTarjeta);
        /*4*/arrayCabecera.add(ConstantsRecaudacion.TIPO_REC_CITI);
        /*5*/arrayCabecera.add("");
        /*6*/arrayCabecera.add(ConstantsRecaudacion.ESTADO_PENDIENTE);
        /*7*/arrayCabecera.add(strEstCta);
        /*8*/arrayCabecera.add("");
        /*9*/arrayCabecera.add(strMonedaPago);
        /*10*/arrayCabecera.add(String.valueOf(dCuantoPaga)); //Monto total(moneda original)
        /*11*/arrayCabecera.add(String.valueOf(dTotalVenta)); //Monto soles
        /*12*/arrayCabecera.add(String.valueOf(dTotalVenta)); //Monto minimo
        /*13*/arrayCabecera.add(VariablesRecaudacion.vTipoCambioVenta); //Tipo cambio
        /*14*/arrayCabecera.add(""); //Fecha Venc Tarj
        /*15*/arrayCabecera.add("");
        /*16*/arrayCabecera.add("");
        /*17*/arrayCabecera.add(ConstantsRecaudacion.FECHA_RCD);
        /*18*/arrayCabecera.add(FarmaVariables.vIdUsu);
        /*19*/arrayCabecera.add("");
        /*20*/arrayCabecera.add("");
        /*21*/arrayCabecera.add(""); //codigo de autorizacion
        /*22*/arrayCabecera.add(""); //mov de caja
        /*23*/arrayCabecera.add(""); //numero de pedido solo valido para VENTA CMR
        /*24*/arrayCabecera.add(""); //tipo producto servicio
        /*25*/arrayCabecera.add(""); //numero de recibo de pago

        String tmpCodigo = facadeRecaudacion.grabaCabeRecau(arrayCabecera,"");
        if (!tmpCodigo.equals("")) {
            if (facadeRecaudacion.cobrarRecaudacion(myFrameParent, tmpCodigo, arrayCabecera,
                                                    ConstantsRecaudacion.TIPO_REC_CITI)) {
                cerrarVentana(true);
            }
        } else {
            FarmaUtility.showMessage(this, "Error al procesar el cobro.", txtTarjeta);
        }
    }

}

