package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.DlgProcesar;
import mifarma.ptoventa.administracion.fondoSencillo.DlgDevolverMontoFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.DBFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.VariablesFondoSencillo;
import mifarma.ptoventa.administracion.roles.reference.VariablesRolesTmp;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.lealtad.reference.FacadeLealtad;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//cambio para corregir forma de pago.
//import mifarma.ptoventa.ce.DlgCambioFormaPagoNew;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgCierreCajaTurno.java<br>
 * <br>
 * Histórico de Creación/_fModificación<br>
 * PAULO      31.07.2006   Creación<br>
 * LMESIA     07.08.2006   Modificacion<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */

public class DlgCierreCajaTurno extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private String autoFondoSen = "0";
    private static final Logger log = LoggerFactory.getLogger(DlgCierreCajaTurno.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelFormasPago;
    private FarmaTableModel tableModelCuadraturas;

    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JPanelHeader pnlDatosCaja = new JPanelHeader();
    private JButtonLabel btnDatosGenerales = new JButtonLabel();
    private JLabelWhite lblTipoCambio = new JLabelWhite();
    private JLabelWhite lblMontoTipoCambio = new JLabelWhite();
    private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodigoCajero = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCaja = new JTextFieldSanSerif();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JPanelTitle jPanelTitle3 = new JPanelTitle();
    private JScrollPane scrFormasPago = new JScrollPane();
    private JScrollPane scrCuadraturas = new JScrollPane();
    private JButtonLabel btnFormasPago = new JButtonLabel();
    private JButtonLabel btnCuadraturas = new JButtonLabel();
    private JPanelTitle pnlTotalCuadraturas = new JPanelTitle();
    private JLabelWhite lblSubTotalCuadraturas_T = new JLabelWhite();
    private JLabelWhite lblSubTotalCuadraturas = new JLabelWhite();
    private JPanelTitle pnlTotalSistema = new JPanelTitle();
    private JLabelWhite lblMontoTotalSistema_T = new JLabelWhite();
    private JLabelWhite lblMontoTotalSistema = new JLabelWhite();
    private JPanelTitle pnlTotalFormaPago = new JPanelTitle();
    private JLabelWhite lblSubTotalFormaPago = new JLabelWhite();
    private JLabelWhite lblSubTotalFormaPago_T = new JLabelWhite();
    private JPanelTitle pnlFaltante = new JPanelTitle();
    private JLabelWhite lblMontoTotalCierre = new JLabelWhite();
    private JLabelWhite lblMontoTotalCierre_T = new JLabelWhite();
    private JLabelWhite lblFaltante_T = new JLabelWhite();
    private JLabelWhite lblFaltante = new JLabelWhite();
    private JButton btnBuscar = new JButton();
    private JLabelFunction lblFormasPago = new JLabelFunction();
    private JLabelFunction lblCuadraturas = new JLabelFunction();
    private JLabelFunction lblVBCajero = new JLabelFunction();
    private JLabelFunction lblVBQF = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JTable tblFormasPago = new JTable();
    private JTable tblCuadraturas = new JTable();
    private JComboBox cmbTurno = new JComboBox();
    private JButtonLabel btnDiaVenta = new JButtonLabel();
    private JLabelOrange lblNombre = new JLabelOrange();
    private JLabelOrange lblNombre_T = new JLabelOrange();
    private JButtonLabel btnCaja = new JButtonLabel();
    private JButtonLabel btnTurno = new JButtonLabel();
    private JButtonLabel btnCajero = new JButtonLabel();
    private JLabelOrange lblApertura_T = new JLabelOrange();
    private JLabelOrange lblCierre_T = new JLabelOrange();
    private JLabelOrange lblApertura = new JLabelOrange();
    private JLabelOrange lblCierre = new JLabelOrange();
    private JLabelOrange lblUsuario = new JLabelOrange();
    private JLabelOrange lblUsuario_T = new JLabelOrange();
    private JLabelFunction lblImprimir = new JLabelFunction();
    private JLabelOrange lblMsgVisado_T = new JLabelOrange();
    private JPanelTitle pnlObservaciones = new JPanelTitle();
    private JButtonLabel btnObs = new JButtonLabel();
    private JTextArea txtSObs = new JTextArea();
    private JLabelFunction lblComprobantes_T = new JLabelFunction();
    private JScrollPane scrObs = new JScrollPane();
    private JLabelFunction lblCambioFormaPago = new JLabelFunction();

    private JPanelTitle jPanelTitle4 = new JPanelTitle();
    private JLabelWhite lblFondoSencillo_T = new JLabelWhite();
    private JLabelWhite lblFondoSencillo = new JLabelWhite();

    private JPanelTitle pnlTitleRecaudaciones = new JPanelTitle();
    private JLabelWhite lblTitleRecaudaciones = new JLabelWhite();
    private JScrollPane scrRecaudaciones = new JScrollPane();
    private JTable tblRecaudaciones = new JTable();
    private FarmaTableModel ftm_tableModelRecaudaciones;
    private JPanelHeader pnlSubTotalRecaud = new JPanelHeader();
    private JLabelWhite lblSubTotalRecaud = new JLabelWhite();
    private JLabelWhite lblDatoSubTotalRecaud = new JLabelWhite();
    private JLabelFunction lblAnulados = new JLabelFunction();
    private boolean vIsUsuarioQF = false;
    
    private String vFechaDia = "";
    private String vSecUsuCaja = "";
    private String vTurno = "";
    private JPanelTitle pnlInscripcionTurno = new JPanelTitle();
    private JLabelWhite lblInscripcionTurno_T = new JLabelWhite();
    private JLabelWhite lblInscripcionTurno = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgCierreCajaTurno() {
        this(null, "", false);
    }

    public DlgCierreCajaTurno(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }
    
    public DlgCierreCajaTurno(Frame parent, String title, boolean modal,
                              String FechaDia,
                              String pSecUsuCaja,
                              String pTurno
                              ) {
        super(parent, title, modal);
        myParentFrame = parent;
        vFechaDia = FechaDia;
        vSecUsuCaja = pSecUsuCaja;
        vTurno = pTurno;
        
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
        this.setSize(new Dimension(739, 630));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Cierre de Caja Turno");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 5, 710, 25));
        jPanelTitle1.setFocusable(false);
        pnlDatosCaja.setBounds(new Rectangle(10, 30, 710, 125));
        pnlDatosCaja.setBackground(Color.white);
        btnDatosGenerales.setText("Datos Generales");
        btnDatosGenerales.setBounds(new Rectangle(10, 5, 120, 15));
        btnDatosGenerales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDatosGenerales_actionPerformed(e);
            }
        });
        lblTipoCambio.setText("Tipo Cambio : ");
        lblTipoCambio.setBounds(new Rectangle(500, 5, 80, 15));
        lblTipoCambio.setFocusable(false);
        lblMontoTipoCambio.setBounds(new Rectangle(600, 5, 55, 15));
        txtFecha.setBounds(new Rectangle(100, 35, 110, 20));
        txtFecha.setDocument(new FarmaLengthText(10));
        txtFecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecha_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecha_keyReleased(e);
            }
        });
        txtCodigoCajero.setBounds(new Rectangle(320, 35, 80, 20));
        txtCodigoCajero.setDocument(new FarmaLengthText(3));
        txtCodigoCajero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodigoCajero_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCodigoCajero_keyTyped(e);
            }
        });
        txtCaja.setBounds(new Rectangle(55, 65, 35, 20));
        txtCaja.setDocument(new FarmaLengthText(2));
        txtCaja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCaja_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCaja_keyTyped(e);
            }
        });
        jPanelTitle2.setBounds(new Rectangle(10, 160, 350, 20));
        jPanelTitle3.setBounds(new Rectangle(370, 160, 350, 20));
        scrFormasPago.setBounds(new Rectangle(10, 180, 350, 135));
        scrCuadraturas.setBounds(new Rectangle(370, 180, 350, 135));
        btnFormasPago.setText("Resumen Formas de Pago");
        btnFormasPago.setBounds(new Rectangle(10, 0, 190, 20));
        btnFormasPago.setMnemonic('R');
        btnFormasPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFormasPago_actionPerformed(e);
            }
        });
        btnCuadraturas.setText("Otras Cuadraturas   [  ENTER - Ver Detalle ]");
        btnCuadraturas.setBounds(new Rectangle(5, 0, 275, 20));
        btnCuadraturas.setMnemonic('O');
        btnCuadraturas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCuadraturas_actionPerformed(e);
            }
        });
        pnlTotalCuadraturas.setBounds(new Rectangle(500, 315, 220, 25));
        pnlTotalCuadraturas.setBackground(new Color(43, 141, 39));
        lblSubTotalCuadraturas_T.setText("Sub Total : S/.");
        lblSubTotalCuadraturas_T.setBounds(new Rectangle(10, 5, 80, 15));
        lblSubTotalCuadraturas.setBounds(new Rectangle(100, 5, 105, 15));
        lblSubTotalCuadraturas.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlTotalSistema.setBounds(new Rectangle(370, 345, 350, 55));
        lblMontoTotalSistema_T.setText("Monto Registrado por Sistema : S/.");
        lblMontoTotalSistema_T.setBounds(new Rectangle(5, 10, 195, 15));
        lblMontoTotalSistema.setBounds(new Rectangle(210, 10, 95, 15));
        lblMontoTotalSistema.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlTotalFormaPago.setBounds(new Rectangle(140, 315, 220, 25));
        pnlTotalFormaPago.setBackground(new Color(43, 141, 39));
        lblSubTotalFormaPago.setBounds(new Rectangle(105, 5, 90, 15));
        lblSubTotalFormaPago.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubTotalFormaPago_T.setText("Sub Total : S/.");
        lblSubTotalFormaPago_T.setBounds(new Rectangle(20, 5, 80, 15));
        pnlFaltante.setBounds(new Rectangle(370, 400, 350, 35));
        pnlFaltante.setBackground(new Color(43, 141, 39));
        lblMontoTotalCierre.setBounds(new Rectangle(70, 10, 80, 15));
        lblMontoTotalCierre.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMontoTotalCierre_T.setText("Cierre : S/.");
        lblMontoTotalCierre_T.setBounds(new Rectangle(5, 10, 60, 15));
        lblFaltante_T.setText("con faltante de S/.");
        lblFaltante_T.setBounds(new Rectangle(160, 10, 110, 15));
        lblFaltante.setBounds(new Rectangle(275, 10, 65, 15));
        lblFaltante.setHorizontalAlignment(SwingConstants.RIGHT);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(320, 60, 80, 30));
        btnBuscar.setMnemonic('B');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
                }
        });
        lblFormasPago.setBounds(new Rectangle(10, 515, 155, 25));
        lblFormasPago.setText("[ F1 ] Formas de Pago");
        lblCuadraturas.setBounds(new Rectangle(185, 515, 155, 25));
        lblCuadraturas.setText("[ F2] Otras Cuadraturas");
        lblVBCajero.setBounds(new Rectangle(10, 540, 155, 25));
        lblVBCajero.setText("[ F8 ] VºBº Cajero");
        lblVBQF.setBounds(new Rectangle(185, 540, 155, 25));
        lblVBQF.setText("[ F9] VºBº Q.F.");
        lblEsc.setBounds(new Rectangle(555, 540, 165, 25));
        lblEsc.setText("[ ESC] Salir");
        lblAnulados.setBounds(new Rectangle(10, 565, 155, 30));
        lblAnulados.setText("[ F5] Ver Anulados ");

        pnlInscripcionTurno.setBounds(new Rectangle(10, 490, 350, 20));
        pnlInscripcionTurno.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        lblInscripcionTurno_T.setText("Vouchers por Inscripcion :");
        lblInscripcionTurno_T.setBounds(new Rectangle(10, 0, 160, 20));
        lblInscripcionTurno.setText("0");
        lblInscripcionTurno.setBounds(new Rectangle(190, 0, 70, 20));
        tblFormasPago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblFormasPago_keyPressed(e);
            }
        });
        tblFormasPago.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    tblFormasPago_focusLost(e);
                }
            });
        tblCuadraturas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCuadraturas_keyPressed(e);
            }
        });
        cmbTurno.setBounds(new Rectangle(165, 65, 45, 20));
        cmbTurno.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTurno_keyPressed(e);
            }
        });
        btnDiaVenta.setText("Dia de Venta :");
        btnDiaVenta.setBounds(new Rectangle(10, 35, 85, 20));
        btnDiaVenta.setForeground(new Color(255, 130, 14));
        btnDiaVenta.setMnemonic('d');
        btnDiaVenta.setFocusable(false);
        btnDiaVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDiaVenta_actionPerformed(e);
            }
        });
        lblNombre.setBounds(new Rectangle(120, 100, 240, 15));
        lblNombre_T.setText("Nombre de Cajero : ");
        lblNombre_T.setBounds(new Rectangle(10, 100, 110, 15));
        lblNombre_T.setFocusable(false);
        btnCaja.setText("Caja :");
        btnCaja.setBounds(new Rectangle(10, 65, 35, 20));
        btnCaja.setForeground(new Color(255, 130, 14));
        btnCaja.setMnemonic('c');
        btnCaja.setFocusable(false);
        btnCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCaja_actionPerformed(e);
            }
        });
        btnTurno.setText("Turno :");
        btnTurno.setBounds(new Rectangle(110, 65, 50, 20));
        btnTurno.setForeground(new Color(255, 130, 14));
        btnTurno.setMnemonic('t');
        btnTurno.setFocusable(false);
        btnTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTurno_actionPerformed(e);
            }
        });
        btnCajero.setText("Código Cajero :");
        btnCajero.setBounds(new Rectangle(225, 35, 85, 20));
        btnCajero.setForeground(new Color(255, 130, 14));
        btnCajero.setMnemonic('e');
        btnCajero.setFocusable(false);
        btnCajero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCajero_actionPerformed(e);
            }
        });
        lblApertura_T.setText("Fecha Apertura : ");
        lblApertura_T.setBounds(new Rectangle(430, 40, 95, 15));
        lblApertura_T.setFocusable(false);
        lblCierre_T.setText("Fecha Cierre : ");
        lblCierre_T.setBounds(new Rectangle(430, 65, 95, 15));
        lblCierre_T.setFocusable(false);
        lblApertura.setBounds(new Rectangle(545, 40, 145, 15));
        lblApertura.setFocusable(false);
        lblCierre.setBounds(new Rectangle(545, 65, 145, 15));
        lblCierre.setFocusable(false);
        lblUsuario.setBounds(new Rectangle(140, 10, 300, 15));
        lblUsuario_T.setText("Usuario Conectado: ");
        lblUsuario_T.setBounds(new Rectangle(10, 10, 120, 15));
        lblUsuario_T.setFocusable(false);
        lblImprimir.setBounds(new Rectangle(355, 540, 185, 25));
        lblImprimir.setText("[ F12] Imprimir");
        lblMsgVisado_T.setBounds(new Rectangle(360, 100, 345, 20));
        lblMsgVisado_T.setFont(new Font("SansSerif", 1, 13));
        lblMsgVisado_T.setForeground(new Color(43, 141, 39));
        pnlObservaciones.setBounds(new Rectangle(370, 440, 350, 70));
        pnlObservaciones.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        btnObs.setText("Observ:");
        btnObs.setMnemonic('s');
        btnObs.setBounds(new Rectangle(10, 10, 55, 20));
        btnObs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObs_actionPerformed(e);
            }
        });
        txtSObs.setDocument(new FarmaLengthText(290));
        txtSObs.setRows(2);
        txtSObs.setSelectionEnd(200);
        txtSObs.setSelectionStart(200);
        txtSObs.setTabSize(20);
        txtSObs.setLineWrap(true);
        txtSObs.setWrapStyleWord(true);
        txtSObs.setSize(new Dimension(588, 40));
        txtSObs.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSObs_keyPressed(e);
            }
        });
        lblComprobantes_T.setBounds(new Rectangle(355, 515, 185, 25));
        lblComprobantes_T.setText("[ F3] Ingresar Comprobantes");
        scrObs.setBounds(new Rectangle(65, 5, 275, 60));
        lblCambioFormaPago.setBounds(new Rectangle(555, 515, 165, 25));
        lblCambioFormaPago.setText("[ F4 ] Cambio Forma Pago");
        lblFondoSencillo_T.setText("Monto Fondo de Sencillo : S/.");
        lblFondoSencillo_T.setBounds(new Rectangle(5, 30, 190, 15));
        lblFondoSencillo.setText("");
        lblFondoSencillo.setBounds(new Rectangle(210, 30, 95, 15));
        lblFondoSencillo.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlTitleRecaudaciones.setBounds(new Rectangle(10, 345, 350, 20));
        scrRecaudaciones.setBounds(new Rectangle(10, 365, 350, 95));
        pnlSubTotalRecaud.setBounds(new Rectangle(140, 460, 220, 25));
        pnlSubTotalRecaud.setSize(new Dimension(220, 25));
        lblSubTotalRecaud.setText("Sub Total: S/.");
        lblSubTotalRecaud.setBounds(new Rectangle(15, 5, 80, 15));
        lblSubTotalRecaud.setSize(new Dimension(80, 15));
        lblSubTotalRecaud.setPreferredSize(new Dimension(80, 15));
        lblSubTotalRecaud.setMaximumSize(new Dimension(80, 15));
        lblSubTotalRecaud.setMinimumSize(new Dimension(80, 15));
        lblDatoSubTotalRecaud.setBounds(new Rectangle(105, 0, 90, 25));
        lblDatoSubTotalRecaud.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDatoSubTotalRecaud.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblTitleRecaudaciones.setText("Resumen Recaudaciones");
        lblTitleRecaudaciones.setBounds(new Rectangle(10, 0, 190, 20));
        scrObs.getViewport().add(txtSObs, null);
        pnlObservaciones.add(scrObs, null);
        pnlObservaciones.add(btnObs, null);
        pnlFaltante.add(lblFaltante, null);
        pnlFaltante.add(lblFaltante_T, null);
        pnlFaltante.add(lblMontoTotalCierre, null);
        pnlFaltante.add(lblMontoTotalCierre_T, null);
        pnlTotalFormaPago.add(lblSubTotalFormaPago, null);
        pnlTotalFormaPago.add(lblSubTotalFormaPago_T, null);
        pnlDatosCaja.add(lblMsgVisado_T, null);
        pnlDatosCaja.add(lblUsuario_T, null);
        pnlDatosCaja.add(lblUsuario, null);
        pnlDatosCaja.add(lblCierre, null);
        pnlDatosCaja.add(lblApertura, null);
        pnlDatosCaja.add(lblCierre_T, null);
        pnlDatosCaja.add(lblApertura_T, null);
        pnlDatosCaja.add(btnCajero, null);
        pnlDatosCaja.add(btnTurno, null);
        pnlDatosCaja.add(btnCaja, null);
        pnlDatosCaja.add(lblNombre_T, null);
        pnlDatosCaja.add(lblNombre, null);
        pnlDatosCaja.add(btnDiaVenta, null);
        pnlDatosCaja.add(cmbTurno, null);
        pnlDatosCaja.add(btnBuscar, null);
        pnlDatosCaja.add(txtCaja, null);
        pnlDatosCaja.add(txtFecha, null);
        pnlDatosCaja.add(txtCodigoCajero, null);
        jPanelTitle1.add(lblMontoTipoCambio, null);
        jPanelTitle1.add(lblTipoCambio, null);
        jPanelTitle1.add(btnDatosGenerales, null);
        pnlTotalCuadraturas.add(lblSubTotalCuadraturas, null);
        pnlTotalCuadraturas.add(lblSubTotalCuadraturas_T, null);
        pnlTotalSistema.add(lblFondoSencillo, null);
        pnlTotalSistema.add(lblFondoSencillo_T, null);
        pnlTotalSistema.add(lblMontoTotalSistema, null);
        pnlTotalSistema.add(lblMontoTotalSistema_T, null);
        pnlSubTotalRecaud.add(lblDatoSubTotalRecaud, null);
        pnlSubTotalRecaud.add(lblSubTotalRecaud, null);
        scrRecaudaciones.getViewport().add(tblRecaudaciones, null);
        pnlInscripcionTurno.add(lblInscripcionTurno, null);
        pnlInscripcionTurno.add(lblInscripcionTurno_T, null);
        jPanelWhite1.add(pnlInscripcionTurno, null);
        jPanelWhite1.add(pnlSubTotalRecaud, null);
        jPanelWhite1.add(scrRecaudaciones, null);
        pnlTitleRecaudaciones.add(lblTitleRecaudaciones, null);
        jPanelWhite1.add(pnlTitleRecaudaciones, null);
        jPanelWhite1.add(lblCambioFormaPago, null);
        jPanelWhite1.add(lblComprobantes_T, null);
        jPanelWhite1.add(pnlObservaciones, null);
        jPanelWhite1.add(lblImprimir, null);
        jPanelWhite1.add(lblVBQF, null);
        jPanelWhite1.add(lblVBCajero, null);
        jPanelWhite1.add(lblCuadraturas, null);
        jPanelWhite1.add(lblFormasPago, null);
        jPanelWhite1.add(pnlFaltante, null);
        jPanelWhite1.add(pnlTotalFormaPago, null);
        jPanelWhite1.add(pnlTotalSistema, null);
        jPanelWhite1.add(pnlTotalCuadraturas, null);
        scrCuadraturas.getViewport().add(tblCuadraturas, null);
        jPanelWhite1.add(scrCuadraturas, null);
        scrFormasPago.getViewport().add(tblFormasPago, null);
        jPanelWhite1.add(scrFormasPago, null);
        jPanelTitle3.add(btnCuadraturas, null);
        jPanelWhite1.add(jPanelTitle3, null);
        jPanelTitle2.add(btnFormasPago, null);
        jPanelWhite1.add(jPanelTitle2, null);
        jPanelWhite1.add(pnlDatosCaja, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(lblAnulados, null);
        
        this.getContentPane().add(jPanelWhite1, null);
        FarmaUtility.centrarVentana(this);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        //ERIOS 2.2.8 Problema de cursores abiertos
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();

        mostrarTextFondoSencillo();
        initTableFormasPago();
        initTableCuadraturas();
        initTableRecaudaciones();
        habilitaOpciones(false);
        visualizarEtiquetaFechas(false);
        muestraPanelesTotalesQF(false);
        verificaObservacionCierreTurno(false, false);
        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF); //iniciamos con msg en blanco
        VariablesCajaElectronica.vUsuarioCajero = false;
        VariablesCajaElectronica.vUsuarioQF = false;
        VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_N;
        FarmaVariables.vAceptar = false;
        //cargo el indicador de nueva forma de pago
        habilitaCambioFormaPago();
        habilitarBtnCambioFP();
        
        pre_carga();
    }
    
    public void pre_carga(){
        if(vFechaDia.trim().length()>0&&
           vSecUsuCaja.trim().length()>0&&
           vTurno.trim().length()>0) {
            txtFecha.setText(vFechaDia.trim());
            txtCodigoCajero.setText(vSecUsuCaja.trim());
            VariablesCajaElectronica.vFechaDiaCajaTurno  = vFechaDia.trim();
            txtCaja.setText(buscaCajasAsignadasUsuario_DiaVenta(VariablesCajaElectronica.vFechaDiaCajaTurno,
                                                                vSecUsuCaja.trim()));            
            cargaTurnosCajaAsignadasUsuario_DiaVenta(vFechaDia.trim(), vSecUsuCaja.trim(), txtCaja.getText().trim());
            FarmaLoadCVL.setSelectedValueInComboBox(cmbTurno, ConstantsCajaElectronica.NOM_HASHTABLE_CMBTURNO,vTurno.trim());
            
            
            
            txtFecha.setEditable(false);
            txtCodigoCajero.setEditable(false);
            cmbTurno.setEditable(false);
            cmbTurno.setEnabled(false);
            
            txtCaja.setEditable(false);
            
            //habilitaOpciones(true);
            
            FarmaUtility.moveFocus(tblFormasPago);
            
            
        }
    }
    
    private void tblFormasPago_focusLost(FocusEvent e) {
        if(vFechaDia.trim().length()>0&&
           vSecUsuCaja.trim().length()>0&&
           vTurno.trim().length()>0) {
            FarmaUtility.moveFocus(tblFormasPago);
           }
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableFormasPago() {
        tableModelFormasPago =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaFormasPagoCierre, ConstantsCajaElectronica.defaultListaFormasPagoCierre,
                                    0);
        FarmaUtility.initSimpleList(tblFormasPago, tableModelFormasPago,
                                    ConstantsCajaElectronica.columsListaFormasPagoCierre);
    }

    private void initTableCuadraturas() {
        tableModelCuadraturas =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaCuadraturasCierreTurno, ConstantsCajaElectronica.defaultValuesListaCuadraturasCierreTurno,
                                    0);
        FarmaUtility.initSimpleList(tblCuadraturas, tableModelCuadraturas,
                                    ConstantsCajaElectronica.columnsListaCuadraturasCierreTurno);
    }

    private void initTableRecaudaciones() {
        ftm_tableModelRecaudaciones =
                new FarmaTableModel(ConstantsRecaudacion.columnsCabeceraConsolidadoRecaudacion, ConstantsRecaudacion.defaultCabeceraConsolidadoRecaudacion,
                                    0);
        FarmaUtility.initSimpleList(tblRecaudaciones, ftm_tableModelRecaudaciones,
                                    ConstantsRecaudacion.columnsCabeceraConsolidadoRecaudacion);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if (FarmaVariables.vTipCambio == 0) {
            FarmaUtility.showMessage(this,
                                     "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción",
                                     null);
            cerrarVentana(false);
        } else {
            //FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(txtFecha);
            VariablesCajaElectronica.vIndFuncionesHabilitadas = false;
            
            if(!(vFechaDia.trim().length()>0&&
                   vSecUsuCaja.trim().length()>0&&
                   vTurno.trim().length()>0)) 
            cargaLogin();
            //obtieneTipoCambio();
            lblMontoTipoCambio.setText(FarmaUtility.formatNumber(FarmaVariables.vTipCambio));
            //imp();
        }
        
        if((vFechaDia.trim().length()>0&&
               vSecUsuCaja.trim().length()>0&&
             vTurno.trim().length()>0)){
                 FarmaUtility.moveFocus(tblFormasPago);
                 btnBuscar.doClick();
                 btnBuscar.setEnabled(false);
             }
    }

    private void imp() {
        UtilityCajaElectronica.imprimeSobresDeclarados(this, "0000003457");
    }

    private void txtFecha_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String diaVenta = txtFecha.getText().trim();
            String codCajero = txtCodigoCajero.getText().trim();
            if (txtCodigoCajero.isEnabled())
                FarmaUtility.moveFocus(txtCodigoCajero);
            else {
                if (FarmaUtility.validaFecha(diaVenta, "dd/MM/yyyy") && codCajero.length() == 3)
                    txtCaja.setText(buscaCajasAsignadasUsuario_DiaVenta(diaVenta, codCajero));
                FarmaUtility.moveFocus(txtCaja);
            }
        } else
            chkKeyPressed(e);
    }

    private void btnDatosGenerales_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecha);
    }

    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }

    private void txtCodigoCajero_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            listaCajerosDiaCierreTurno();
            //String diaVenta = txtFecha.getText().trim();
            //String codCajero = FarmaUtility.caracterIzquierda(txtCodigoCajero.getText().trim(),3,"0");
            //txtCodigoCajero.setText(codCajero);
            //if(FarmaUtility.validaFecha(diaVenta,"dd/MM/yyyy") && codCajero.length() == 3 )
            //txtCaja.setText(buscaCajasAsignadasUsuario_DiaVenta(diaVenta, codCajero));
            //FarmaUtility.moveFocus(txtCaja);
        } else
            chkKeyPressed(e);
    }

    private void txtCaja_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String diaVenta = txtFecha.getText().trim();
            String codCajero = txtCodigoCajero.getText().trim();
            String numeroCaja = txtCaja.getText().trim();
            if (FarmaUtility.validaFecha(diaVenta, "dd/MM/yyyy") && codCajero.length() == 3 &&
                FarmaUtility.isInteger(numeroCaja))
                cargaTurnosCajaAsignadasUsuario_DiaVenta(diaVenta, codCajero, numeroCaja);
            else
                FarmaLoadCVL.unloadCVL(cmbTurno, ConstantsCajaElectronica.NOM_HASHTABLE_CMBTURNO);
            FarmaUtility.moveFocus(cmbTurno);
        } else
            chkKeyPressed(e);
    }

    private void cmbTurno_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();
        else
            chkKeyPressed(e);
    }

    public void btnBuscar_actionPerformed(ActionEvent e) {
        if (validaDatosIngreso()) {
            obtieneInfoDatosCaja();
            log.debug("VariablesCajaElectronica.vSecMovCaja : " + VariablesCajaElectronica.vSecMovCaja);
            if (!getDeter_neces_fpFSEN() && autoFondoSen.equalsIgnoreCase("1")) {
                autoFondoSen = "0";
                mandarDeclararFSEN();
            }
        }
    }

    private void btnDiaVenta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecha);
    }

    private void btnCajero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodigoCajero);
    }

    private void btnCaja_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCaja);
    }

    private void btnTurno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTurno);
    }

    private void tblFormasPago_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblCuadraturas_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            cargaDetalleCuadratura();
        } else
            chkKeyPressed(e);
    }

    private void btnFormasPago_actionPerformed(ActionEvent e) {
        if (tblFormasPago.getRowCount() > 0)
            FarmaUtility.moveFocusJTable(tblFormasPago);
        else
            FarmaUtility.moveFocus(tblFormasPago);
    }

    private void btnCuadraturas_actionPerformed(ActionEvent e) {
        if (tblCuadraturas.getRowCount() > 0)
            FarmaUtility.moveFocusJTable(tblCuadraturas);
        else
            FarmaUtility.moveFocus(tblCuadraturas);
    }

    private void btnObs_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSObs);
        txtSObs.selectAll();
    }

    private void txtSObs_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            e.consume();
        else
            chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (VariablesCajaElectronica.vIndFuncionesHabilitadas) {
            if (UtilityPtoVenta.verificaVK_F1(e)) {
                mostrarFormasPago();
            } else if (UtilityPtoVenta.verificaVK_F2(e)) {
                if (FarmaVariables.vEconoFar_Matriz) {
                    FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFecha);
                    return;
                }
                mostrarOtrasCuadraturas();
            } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                ingresarRangoComprobantes();
            } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                //JCORTEZ 23.02.10 VariablesCajaElectronica.vIndVBCajero
                //VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                //                VariablesCajaElectronica.vUsuarioCajero

                log.debug("VariablesCajaElectronica.vIndVBCajero" + VariablesCajaElectronica.vIndVBCajero);
                if (UtilityCajaElectronica.obtieneIndicadorVB_ForUpdate(VariablesCajaElectronica.vSecMovCaja,
                                                                        ConstantsCajaElectronica.TIPO_VB_CAJERO)) {
                    if (VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        FarmaUtility.showMessage(this,
                                                 "No se Puede Cambiar la forma de Pago de un Movimiento de Caja \n con V°B° de Cajero.",
                                                 tblFormasPago);
                    } else {
                        log.debug("CAMBIO FORMA PAGO");
                        if (VariablesCajaElectronica.vIndCambioFormaPago.equals("S")) {
                            cambioFomaPago();
                        } else {
                            FarmaUtility.showMessage(this, "Esta función no esta habilitada.", txtFecha);
                        }
                    }
                } else {
                    FarmaUtility.showMessage(this,
                                             "No se pudo determinar si el movimiento de caja posee V°B° de Cajero o de Dia",
                                             null);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_F8) {
                e.consume(); // GFonseca. Se agrega para que el foco no se pierda
                funcion_F8();
            } else if (e.getKeyCode() == KeyEvent.VK_F9) {
                funcion_F9();
            } else if (UtilityPtoVenta.verificaVK_F12(e)) {
                imprimeCierreTurno();
            }  else if(e.getKeyCode()== KeyEvent.VK_F5) {
             
              lstDocAnulCierreTurno();  
           }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
          
       
      }

    private void cerrarVentana(boolean pAceptar) {
        guardaInformacionMovimientoCaja(true);
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargaLogin() {
        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setVisible(true);
        if (FarmaVariables.vAceptar) {
            //dubilluz 22.11.2013
            if (dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL))
                vIsUsuarioQF = true;
            else
                vIsUsuarioQF = false;

            if ((dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) &&
                 dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO))) {
                txtCodigoCajero.setText(FarmaVariables.vNuSecUsu);
            } else if (dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO)) {
                txtCodigoCajero.setText(FarmaVariables.vNuSecUsu);
                txtCodigoCajero.setEnabled(false);
            } else if (dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
                txtCodigoCajero.setText("");
            } else if (dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES) && FarmaVariables.vEconoFar_Matriz) {
                txtCodigoCajero.setText("");
            } else {
                FarmaUtility.showMessage(this, "El usuario no tiene asignado el rol adecuado!!!", null);
                cerrarVentana(false);
                return;
            }
            lblUsuario.setText(FarmaVariables.vNomUsu + " " + FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu);
            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else
            cerrarVentana(false);
    }

    private void mostrarFormasPago() {
        VariablesCajaElectronica.vFecCierreCajaAux = txtFecha.getText().trim();
        //JMIRANDA 10.03.2010 guarda sec Cajero
        VariablesFondoSencillo.vSecUsuCajeroCierre = txtCodigoCajero.getText().trim();
        VariablesFondoSencillo.vSecMovCajaCierre = VariablesCajaElectronica.vSecMovCaja;
        //VariablesFondoSencillo.vMensajeDevolver = "Nuevo Fondo de Sencillo para Devolver.";
        DlgFormaPagoEntrega dlgFormaPagoEntrega = new DlgFormaPagoEntrega(myParentFrame, "", true);
        dlgFormaPagoEntrega.setVisible(true);
        cargaListaFormasPagoCierre();
    }

    private void mostrarOtrasCuadraturas() {
        DlgCuadraturas dlgCuadraturas = new DlgCuadraturas(myParentFrame, "", true);
        dlgCuadraturas.setVisible(true);
        cargaListaCuadraturasCierre();
    }

    private void obtieneTipoCambio(String pFechaTipoCambio) {

        try {
            (new FacadeRecaudacion()).obtenerTipoCambio();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener Tipo de Cambio del Dia . \n " + sql.getMessage(), null);
        }
    }

    private boolean obtieneInfoDatosCaja() {
        String resultado = "";
        String indResultBuscar = "";
        boolean valor = false;
        String diaVenta = txtFecha.getText().trim();
        String codCajero = txtCodigoCajero.getText().trim();
        String numeroCaja = txtCaja.getText().trim();
        String turnoCaja =
            FarmaLoadCVL.getCVLCode(ConstantsCajaElectronica.NOM_HASHTABLE_CMBTURNO, cmbTurno.getSelectedIndex());
        lblNombre.setText("");
        VariablesCajaElectronica.vExisteFechaCierreDia = false;
        try {
            resultado = DBCajaElectronica.validaDatosCierreCajaTurno(diaVenta, codCajero, numeroCaja, turnoCaja);
        } catch (SQLException sql) {
            VariablesCajaElectronica.vSecMovCaja = "";
            resultado = "";
            valor = false;
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del Cierre Turno Caja.\n" +
                    sql.getMessage(), txtFecha);
            //return valor;
        } finally {
            if (resultado.trim().length() > 0) {
                indResultBuscar = resultado.trim().substring(0, 1);
                if (indResultBuscar.equalsIgnoreCase(ConstantsCajaElectronica.RESULT_BUSCAR_MOV_CAJA_SIN_DATA)) {
                    valor = false;
                    lblMontoTipoCambio.setText("0.00");
                    VariablesCajaElectronica.vSecMovCaja = "";
                    VariablesCajaElectronica.vIndVBCajero = FarmaConstants.INDICADOR_N;
                    VariablesCajaElectronica.vIndVBQF = FarmaConstants.INDICADOR_N;
                    evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF); //no existe VB para el mov de caja
                    verificaObservacionCierreTurno(valor, valor);
                    muestraPanelesTotalesQF(valor);
                    FarmaUtility.showMessage(this,
                                             "No se encontro movimiento de caja para\nlos datos ingresados. Por Favor Verifique!!!",
                                             txtFecha);
                } else {
                    valor = true;
                    VariablesCajaElectronica.vSecMovCaja = resultado.trim().substring(1);
                    //LLEIVA 25-Sep-2013 Se carga el listado de recaudaciones correspondiente
                    cargaListaRecaudaciones();
                    log.debug("VariablesCajaElectronica.vSecMovCaja : " + VariablesCajaElectronica.vSecMovCaja);
                    VariablesCajaElectronica.vIndCompValidos =
                            UtilityCajaElectronica.obtieneIndicadorComprobatesValidosUsuario(this,
                                                                                             VariablesCajaElectronica.vSecMovCaja);
                    log.debug("VariablesCajaElectronica.vIndCompValidos : " +
                              VariablesCajaElectronica.vIndCompValidos);
                    if (VariablesCajaElectronica.vIndCompValidos.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                        ingresarRangoComprobantes();
                        if (VariablesCajaElectronica.vIndCompValidos.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                            valor = false;
                            UtilityCajaElectronica.actualizaIndicadorCompValidosCT(this,
                                                                                   VariablesCajaElectronica.vSecMovCaja,
                                                                                   FarmaConstants.INDICADOR_N);
                            VariablesCajaElectronica.vSecMovCaja = "";
                            cargaListaFormasPagoCierre();
                            cargaListaCuadraturasCierre();

                            habilitaOpciones(valor);
                            visualizarEtiquetaFechas(valor);
                            verificaObservacionCierreTurno(valor, valor);
                            guardaInformacionMovimientoCaja(!valor);
                            muestraPanelesTotalesQF(false);
                            evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF); //msg en blanco
                            lblMontoTipoCambio.setText("0.00");
                            return valor;
                        }
                    }
                    VariablesCajaElectronica.vFechaCuadratura = diaVenta;
                    colocaTipoCambioDiaVenta(VariablesCajaElectronica.vFechaCuadratura);
                    evaluaExistenciaFechaCierreDia(VariablesCajaElectronica.vFechaCuadratura);
                    if (indResultBuscar.equalsIgnoreCase(ConstantsCajaElectronica.RESULT_BUSCAR_MOV_CAJA_CON_VBC)) {
                        VariablesCajaElectronica.vIndVBCajero = FarmaConstants.INDICADOR_S;
                        VariablesCajaElectronica.vIndVBQF = FarmaConstants.INDICADOR_N;
                        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_CAJERO); //existe VB de cajero para el mov de caja
                        FarmaUtility.showMessage(this, "El movimiento de caja ya cuenta con VoBo de Cajero.",
                                                 tblFormasPago);
                        verificaObservacionCierreTurno(true, false);
                    } else if (indResultBuscar.equalsIgnoreCase(ConstantsCajaElectronica.RESULT_BUSCAR_MOV_CAJA_CON_VBQ)) {
                        VariablesCajaElectronica.vIndVBCajero = FarmaConstants.INDICADOR_S;
                        VariablesCajaElectronica.vIndVBQF = FarmaConstants.INDICADOR_S;
                        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_QF); //existe VB de qf para el mov de caja
                        FarmaUtility.showMessage(this, "El movimiento de caja ya cuenta con VoBo de QF.",
                                                 tblFormasPago);
                        verificaObservacionCierreTurno(true, false);
                    } else {
                        VariablesCajaElectronica.vIndVBCajero = FarmaConstants.INDICADOR_N;
                        VariablesCajaElectronica.vIndVBQF = FarmaConstants.INDICADOR_N;
                        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF); //no existe VB para el mov de caja
                        if (!codCajero.equalsIgnoreCase(FarmaVariables.vNuSecUsu)) {
                            valor = false;
                            VariablesCajaElectronica.vSecMovCaja = "";
                            cargaListaFormasPagoCierre();
                            cargaListaCuadraturasCierre();
                            habilitaOpciones(valor);
                            visualizarEtiquetaFechas(valor);
                            verificaObservacionCierreTurno(valor, valor);
                            guardaInformacionMovimientoCaja(!valor);
                            muestraPanelesTotalesQF(false);
                            FarmaUtility.showMessage(this,
                                                     "No es posible realizar la operación.\nEl movimiento de caja aun no cuenta con VB de Cajero.",
                                                     txtFecha);
                            return valor;
                        }
                        verificaObservacionCierreTurno(true, true);
                    }
                    colocaFechaAperturaCierreCaja(VariablesCajaElectronica.vSecMovCaja);
                    lblNombre.setText(obtieneNombreUsuario(codCajero));
                }
            }
            cargaListaFormasPagoCierre();
            cargaListaCuadraturasCierre();
            habilitaOpciones(valor);
            visualizarEtiquetaFechas(valor);
            guardaInformacionMovimientoCaja(!valor);
            //ERIOS 22.06.2015 Se muestra la cantidad de vouchers de inscripcion
            muestraIncripcionTurno();

            //jquispe 20.12.2010, leo la variable indicador para que un Quimico con rol cajero pueda dar VB Cajero y VB QF.
            VariablesCajaElectronica.indUsu_Cierre_Caj_QF = false;
            UtilityCajaElectronica.getIndicadorCierreCajQuimico(this);

            if (valor) {
                log.debug("VariablesCajaElectronica.indUsu_Cierre_Caj_QF>" +
                          VariablesCajaElectronica.indUsu_Cierre_Caj_QF + "<");
                if (VariablesCajaElectronica.indUsu_Cierre_Caj_QF) {
                    if (codCajero.equalsIgnoreCase(FarmaVariables.vNuSecUsu) &&
                        UtilityCajaElectronica.isAdmLocal(FarmaVariables.vNuSecUsu, this)) {
                        muestraPanelesTotalesQF(true);
                        evaluaTotales();
                        VariablesCajaElectronica.vUsuarioCajero = true;
                        VariablesCajaElectronica.vUsuarioQF = true;
                        FarmaUtility.moveFocus(tblFormasPago);
                    } else {
                        if (codCajero.equalsIgnoreCase(FarmaVariables.vNuSecUsu)) {
                            muestraPanelesTotalesQF(false);
                            VariablesCajaElectronica.vUsuarioCajero = true;
                            VariablesCajaElectronica.vUsuarioQF = false;
                        } else {
                            muestraPanelesTotalesQF(true);
                            evaluaTotales();
                            VariablesCajaElectronica.vUsuarioCajero = false;
                            VariablesCajaElectronica.vUsuarioQF = true;
                        }
                        FarmaUtility.moveFocus(tblFormasPago);
                    }
                } else {
                    if (codCajero.equalsIgnoreCase(FarmaVariables.vNuSecUsu)) {
                        muestraPanelesTotalesQF(false);
                        VariablesCajaElectronica.vUsuarioCajero = true;
                        VariablesCajaElectronica.vUsuarioQF = false;
                    } else {
                        muestraPanelesTotalesQF(true);
                        evaluaTotales();
                        VariablesCajaElectronica.vUsuarioCajero = false;
                        VariablesCajaElectronica.vUsuarioQF = true;
                    }
                    FarmaUtility.moveFocus(tblFormasPago);
                }

                // 22.11.2013
                if (vIsUsuarioQF) {
                    if (VariablesCajaElectronica.vIndVBCajero.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        muestraPanelesTotalesQF(true);
                    evaluaTotales();
                    VariablesCajaElectronica.vUsuarioCajero = true;
                    VariablesCajaElectronica.vUsuarioQF = true;
                    FarmaUtility.moveFocus(tblFormasPago);
                }
            }
            return valor;
        }
    }

    private boolean validaDatosIngreso() {
        String diaVenta = txtFecha.getText().trim();
        String codCajero = txtCodigoCajero.getText().trim();
        String numeroCaja = txtCaja.getText().trim();
        if (!FarmaUtility.isFechaValida(this, diaVenta, "Ingrese una Fecha correcta en el Dia de venta"))
        //if( diaVenta.length() == 0 || !FarmaUtility.validaFecha(diaVenta,"dd/MM/yyyy") )
        {
            //FarmaUtility.showMessage(this,"Ingrese una Fecha correcta en el Dia de venta" , txtFecha);
            FarmaUtility.moveFocus(txtFecha);
            return false;
        } else if (codCajero.length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese un Codigo de Cajero", txtCodigoCajero);
            return false;
        } else if (numeroCaja.length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese un numero de Caja", txtCaja);
            return false;
        } else if (cmbTurno.getSelectedItem() == null) {
            FarmaUtility.showMessage(this, "Seleccione un turno para la caja", cmbTurno);
            return false;
        }
        return true;
    }

    private String buscaCajasAsignadasUsuario_DiaVenta(String pDiaVenta, String pCodCajero) {
        ArrayList myArray = new ArrayList();
        String caja = "";
        try {
            DBCajaElectronica.buscaCajasAsignadasUsuario(myArray, pDiaVenta, pCodCajero);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener las cajas asiganadas al usuario . \n " + sql.getMessage(),
                                     null);
        } finally {
            if (myArray.size() > 0)
                caja = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            return caja;
        }
    }

    private void cargaTurnosCajaAsignadasUsuario_DiaVenta(String pDiaVenta, String pCodCajero, String pNumeroCaja) {
        ArrayList myArray = new ArrayList();
        try {
            DBCajaElectronica.buscaTurnosCajaAsignadaUsuario(myArray, pDiaVenta, pCodCajero, pNumeroCaja);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al obtener los turnos de la caja del usuario. \n " + sql.getMessage(),
                                     null);
        } finally {
            FarmaLoadCVL.unloadCVL(cmbTurno, ConstantsCajaElectronica.NOM_HASHTABLE_CMBTURNO);
            FarmaLoadCVL.loadCVLFromArrayList(cmbTurno, ConstantsCajaElectronica.NOM_HASHTABLE_CMBTURNO, myArray,
                                              true);
        }
    }

    private void habilitaOpciones(boolean pValor) {
        VariablesCajaElectronica.vIndFuncionesHabilitadas = pValor;
        lblFormasPago.setEnabled(pValor);
        lblCuadraturas.setEnabled(pValor);
        lblComprobantes_T.setEnabled(pValor);
        lblVBCajero.setEnabled(pValor);
        lblVBQF.setEnabled(pValor);
        lblImprimir.setEnabled(pValor);
        lblCambioFormaPago.setEnabled(pValor);
        lblAnulados.setEnabled(pValor);//CHUANES 25.02.2015
    }

    private void visualizarEtiquetaFechas(boolean pValor) {
        lblApertura.setVisible(pValor);
        lblApertura_T.setVisible(pValor);
        lblCierre.setVisible(pValor);
        lblCierre_T.setVisible(pValor);
    }

    private void verificaObservacionCierreTurno(boolean pVisible, boolean pEnabled) {
        pnlObservaciones.setVisible(pVisible);
        colocaObservacionCierreTurno(pVisible);
        txtSObs.setEditable(pEnabled);
        //txtSObs.setEnabled(pEnabled);
    }

    private void colocaFechaAperturaCierreCaja(String pSecMovCaja) {
        ArrayList myArray = new ArrayList();
        String fechaApertura = new String();
        String fechaCierre = new String();
        try {
            DBCajaElectronica.obtieneFechaAperturaCierreCaja(myArray, pSecMovCaja);
        } catch (SQLException sql) {
            log.error("", sql);
            visualizarEtiquetaFechas(false);
            FarmaUtility.showMessage(this, "Error al obtener las fechas de Apertua y Cierre. \n " + sql.getMessage(),
                                     null);
        } finally {
            if (myArray.size() > 0) {
                String tipoMovCaja = "";
                for (int i = 0; i < myArray.size(); i++) {
                    tipoMovCaja = ((String)((ArrayList)myArray.get(i)).get(0)).trim();
                    if (tipoMovCaja.equalsIgnoreCase(ConstantsCajaElectronica.MOVIMIENTO_APERTURA))
                        fechaApertura = ((String)((ArrayList)myArray.get(i)).get(1)).trim();
                    else if (tipoMovCaja.equalsIgnoreCase(ConstantsCajaElectronica.MOVIMIENTO_CIERRE))
                        fechaCierre = ((String)((ArrayList)myArray.get(i)).get(1)).trim();
                }
                visualizarEtiquetaFechas(true);
            } else
                visualizarEtiquetaFechas(false);
            lblApertura.setText(fechaApertura);
            lblCierre.setText(fechaCierre);
        }
    }

    private void muestraPanelesTotalesQF(boolean pValor) {
        pnlTotalSistema.setVisible(pValor);
        pnlFaltante.setVisible(pValor);
    }

    private String obtieneNombreUsuario(String pSecUsu) {
        String nombre = new String();
        try {
            nombre = DBCajaElectronica.obtenerNombreCompletoUsuario(pSecUsu);
        } catch (SQLException sql) {
            log.error("", sql);
            nombre = "----- ----- -----";
            FarmaUtility.showMessage(this, "Error al obtener Nombre del Usuario. \n " + sql.getMessage(), null);
        }
        return nombre.trim();
    }

    private void cargaListaFormasPagoCierre() {
        try {
            DBCajaElectronica.cargaListaFormasPagoCierre(tableModelFormasPago, VariablesCajaElectronica.vSecMovCaja);
            if (tblFormasPago.getRowCount() > 0)
                FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 4, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar las formas de pago Entrega Realizadas \n" +
                    sql.getMessage(), null);
        } finally {
            lblSubTotalFormaPago.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblFormasPago, 3)));
        }
    }

    private void cargaListaCuadraturasCierre() {
        try {
            DBCajaElectronica.cargaListaCuadraturasCierre(tableModelCuadraturas, VariablesCajaElectronica.vSecMovCaja);
            if (tblCuadraturas.getRowCount() > 0)
                FarmaUtility.ordenar(tblCuadraturas, tableModelCuadraturas, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar las cuadraturas realizadas \n" +
                    sql.getMessage(), null);
        } finally {
            lblSubTotalCuadraturas.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblCuadraturas, 2)));
        }
    }

    private void cargaListaRecaudaciones() {
        FacadeRecaudacion facade = new FacadeRecaudacion();

        try {
            ftm_tableModelRecaudaciones.clearTable();
            ArrayList<ArrayList<String>> resultado =
                facade.listarReporteCierreTurno(VariablesCajaElectronica.vSecMovCaja);
            ArrayList rowsWithOtherColor = new ArrayList();
            
            for (int i = 0; resultado.size() > i; i++) {
                ArrayList<String> tmpArrayRow = new ArrayList<String>();
                String tmpDesc = ((ArrayList)resultado.get(i)).get(0).toString().trim();
                String tmpMoneda = ((ArrayList)resultado.get(i)).get(1).toString().trim();
                String tmpMonto = ((ArrayList)resultado.get(i)).get(2).toString().trim();
                String tmpTotal = ((ArrayList)resultado.get(i)).get(3).toString().trim();
                String indAnul = ((ArrayList)resultado.get(i)).get(4).toString().trim();
                if ( indAnul.trim().equalsIgnoreCase("S")) {
                    rowsWithOtherColor.add(String.valueOf(i));
                }
                
                tmpArrayRow.add(tmpDesc);
                tmpArrayRow.add(tmpMoneda);
                tmpArrayRow.add(tmpMonto);
                tmpArrayRow.add(tmpTotal);
                ftm_tableModelRecaudaciones.insertRow(tmpArrayRow);
            }            
            //ERIOS 15.06.2015 Se muestran las transacciones anuladas.
            FarmaUtility.initSimpleListCleanColumns(tblRecaudaciones, ftm_tableModelRecaudaciones,
                                                    ConstantsRecaudacion.columnsCabeceraConsolidadoRecaudacion, 
                                                    rowsWithOtherColor,Color.white, Color.red, false);

            lblDatoSubTotalRecaud.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRecaudaciones, 3)));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void cambiaVBCajero() {
        evaluaExistenciaVBCierreDia(VariablesCajaElectronica.vFechaCuadratura);
        //JCORTEZ 01.03.2010  Se valida monto efectivo y tarjeta con los declarado
        if (!validaMontoDeclarado()) {
            FarmaUtility.liberarTransaccion();
            return;
        }
        if (VariablesCajaElectronica.vExisteVBCierreDia) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No es posible realizar esta operación.\nExiste VB de Cierre de Dia para la fecha ingresada.\nDebe eliminar aquel VB antes de realizar esta acción.",
                                     txtFecha);
            return;
        }
        if (!UtilityCajaElectronica.obtieneIndicadorVB_ForUpdate(VariablesCajaElectronica.vSecMovCaja,
                                                                 ConstantsCajaElectronica.TIPO_VB_CAJERO)) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No se pudo obtener el Indicador actual de VB de Cajero.\nPor favor, comuniquese con el area de Sistemas.",
                                     txtFecha);
            return;
        }
        if (VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_N) &&
            VariablesCajaElectronica.vUsuarioCajero) {
            if (getDeter_neces_fpFSEN()) { //ASOSA, 21.06.2010
                //UtilityCajaElectronica.obtieneComprobantesMinMaxIngresoUsuario(this, VariablesCajaElectronica.vSecMovCaja);
                //guardaInformacionComprobantes();
                if (!validaRangoComprobantesIngresados()) {
                    UtilityCajaElectronica.actualizaIndicadorCompValidosCT(this, VariablesCajaElectronica.vSecMovCaja,
                                                                           FarmaConstants.INDICADOR_N);
                    FarmaUtility.showMessage(this,
                                             "Los rangos de comprobantes ingresados no coinciden con\nlos registrados en el sistema. Verifique por favor!!!",
                                             txtFecha);
                    return;
                }
                if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Está seguro de otorgar el VB de Cajero?")) {
                    VariablesCajaElectronica.vObsCierreTurno = txtSObs.getText().trim().toUpperCase();
                    txtSObs.setText(VariablesCajaElectronica.vObsCierreTurno);
                    if (actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja, FarmaConstants.INDICADOR_S,
                                             ConstantsCajaElectronica.TIPO_VB_CAJERO)) {
                        FarmaUtility.aceptarTransaccion();
                        actualizaObservacionCierreTurno(VariablesCajaElectronica.vSecMovCaja,
                                                        VariablesCajaElectronica.vObsCierreTurno);
                        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_CAJERO); //existe VB de cajero para el mov de caja
                        verificaObservacionCierreTurno(true, false);
                        //UtilityCajaElectronica.imprimeSobresDeclarados(this,VariablesCajaElectronica.vSecMovCaja); ASOSA, 09.08.2010 - Ya no se imprimiran aca los sobres
                        FarmaUtility.showMessage(this, "Se otorgó correctamente el VB de Cajero", txtFecha);
                        VariablesCajaElectronica.vIndVBCajero = "S";
                        if (vIsUsuarioQF) {
                            if (VariablesCajaElectronica.vIndVBCajero.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                                muestraPanelesTotalesQF(true);
                            evaluaTotales();
                            VariablesCajaElectronica.vUsuarioCajero = true;
                            VariablesCajaElectronica.vUsuarioQF = true;
                            FarmaUtility.moveFocus(tblFormasPago);
                        }
                    }
                }
                FarmaUtility.liberarTransaccion();
            } else { //ASOSA, 21.06.2010
                mandarDeclararFSEN();
            }
        } else if (VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_N) &&
                   VariablesCajaElectronica.vUsuarioQF) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Solo un usuario con Rol Cajero puede otorgar el VB a este Movimiento de Caja.",
                                     txtFecha);
        } else if (VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                   VariablesCajaElectronica.vUsuarioCajero && !VariablesCajaElectronica.vUsuarioQF) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Este movimiento de Caja ya cuenta con VB de Cajero.\nSolo un usuario con Rol Administrador Local puede eliminar el VB.",
                                     txtFecha);
        } else if (VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                   VariablesCajaElectronica.vUsuarioQF) {
            FarmaUtility.liberarTransaccion();
            if (!UtilityCajaElectronica.obtieneIndicadorVB_ForUpdate(VariablesCajaElectronica.vSecMovCaja,
                                                                     ConstantsCajaElectronica.TIPO_VB_QF)) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "No se pudo obtener el Indicador actual de VB de QF.\nPor favor, comuniquese con el area de Sistemas.\n",
                                         txtFecha);
                return;
            }
            if (VariablesCajaElectronica.vIndVBQF.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "No se pudo realizar la operación.\nPrimero elimine el VB de QF.\n",
                                         txtFecha);
            } else {
                evaluaExistenciaFechaCierreDia(VariablesCajaElectronica.vFechaCuadratura);
                if (VariablesCajaElectronica.vExisteFechaCierreDia) {
                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Ya existe un registro de Cierre de Dia Local para el dia de\nventa ingresado. Está seguro de eliminar el VB de Cajero?")) {
                        if (VariablesCajaElectronica.vUsuarioQF || cargaLoginOper()) {
                            if (actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja, FarmaConstants.INDICADOR_N,
                                                     ConstantsCajaElectronica.TIPO_VB_CAJERO)) {
                                FarmaUtility.aceptarTransaccion();
                                evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF); //no existe VB para el mov de caja
                                FarmaUtility.showMessage(this, "Se eliminó correctamente el VB de Cajero", txtFecha);
                                evaluaDeficitAsumidoCajero(VariablesCajaElectronica.vSecMovCaja,
                                                           -1); //-1 = borrar el deficit anterior si hubiese si o si
                                /*cargaListaFormasPagoCierre();
                                cargaListaCuadraturasCierre();
                                evaluaTotales();*/
                                btnBuscar.doClick();
                            }
                        } else {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this,
                                                     "No se realizó la operación. Solo un usuario con Rol de\nOperador de Sistemas puede eliminar el VB de Cajero.",
                                                     txtFecha);
                        }
                    }
                    FarmaUtility.liberarTransaccion();
                } else {
                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Está seguro de eliminar el VB de Cajero?")) {
                        int cant = evaluaCantEliminacionVBCajero(VariablesCajaElectronica.vSecMovCaja);
                        //if( cant < 2 )//siginifica que tiene menos de 2 eliminaciones de VB
                        if (cant < 10) // RHERRERA: Tiene hasta menos de 10 eliminaciones de VB
                        {
                            if (actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja, FarmaConstants.INDICADOR_N,
                                                     ConstantsCajaElectronica.TIPO_VB_CAJERO)) {
                                FarmaUtility.aceptarTransaccion();
                                evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF); //no existe VB para el mov de caja
                                FarmaUtility.showMessage(this, "Se eliminó correctamente el VB de Cajero", txtFecha);
                                evaluaDeficitAsumidoCajero(VariablesCajaElectronica.vSecMovCaja,
                                                           -1); //-1 = borrar el deficit anterior si hubiese si o si
                                /*cargaListaFormasPagoCierre();
                                cargaListaCuadraturasCierre();
                                evaluaTotales();*/
                                btnBuscar.doClick();
                            }
                        } else {
                            if (cargaLoginOper()) {
                                if (actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja,
                                                         FarmaConstants.INDICADOR_N,
                                                         ConstantsCajaElectronica.TIPO_VB_CAJERO)) {
                                    FarmaUtility.aceptarTransaccion();
                                    evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF); //no existe VB para el mov de caja
                                    FarmaUtility.showMessage(this, "Se eliminó correctamente el VB de Cajero",
                                                             txtFecha);
                                    evaluaDeficitAsumidoCajero(VariablesCajaElectronica.vSecMovCaja,
                                                               -1); //-1 = borrar el deficit anterior si hubiese si o si
                                    /*cargaListaFormasPagoCierre();
                                    cargaListaCuadraturasCierre();
                                    evaluaTotales();*/
                                    btnBuscar.doClick();
                                }
                            } else
                                FarmaUtility.showMessage(this,
                                                         "No se realizó la operación. Solo un usuario con Rol de\nOperador de Sistemas puede eliminar el VB de Cajero.",
                                                         txtFecha);
                        }
                    }
                }
                /*int cant = evaluaCantEliminacionVBCajero(VariablesCajaElectronica.vSecMovCaja);
        if( cant == 0 )
        {
        if ( com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Está seguro de eliminar el VB de Cajero?") )
        {
        if( actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja,
                 FarmaConstants.INDICADOR_N,
                 ConstantsCajaElectronica.TIPO_VB_CAJERO) ){
        FarmaUtility.aceptarTransaccion();
        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF);//no existe VB para el mov de caja
        FarmaUtility.showMessage(this,"Se eliminó correctamente el VB de Cajero", txtFecha);
        evaluaDeficitAsumido( VariablesCajaElectronica.vSecMovCaja, -1);//-1 = borrar el deficit anterior si hubiese si o si
        /*cargaListaFormasPagoCierre();
        cargaListaCuadraturasCierre();
        evaluaTotales();
        btnBuscar.doClick();
        }
        }
        } else
        {
        if ( com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Está seguro de eliminar el VB de Cajero?") )
        {
        if( cargaLoginOper() )
        {
        if( actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja,
                   FarmaConstants.INDICADOR_N,
                   ConstantsCajaElectronica.TIPO_VB_CAJERO) ){
        FarmaUtility.aceptarTransaccion();
        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CAJERO_QF);//no existe VB para el mov de caja
        FarmaUtility.showMessage(this,"Se eliminó correctamente el VB de Cajero", txtFecha);
        evaluaDeficitAsumido( VariablesCajaElectronica.vSecMovCaja, -1);//-1 = borrar el deficit anterior si hubiese si o si
        /*cargaListaFormasPagoCierre();
        cargaListaCuadraturasCierre();
        evaluaTotales();
        btnBuscar.doClick();
        }
        } else
        FarmaUtility.showMessage(this,"No se realizó la operación. Solo un usuario con Rol de\nOperador de Sistemas puede eliminar el VB de Cajero.", txtFecha);
        }
        }*/
            }

        } else {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Error al validad Usuario y VB de Cajero.\nComuníquese con el area de Sistema!!!",
                                     txtFecha);
        }
    }

    private void cambiaVBQF() {
        try {
            evaluaExistenciaVBCierreDia(VariablesCajaElectronica.vFechaCuadratura);
            if (VariablesCajaElectronica.vExisteVBCierreDia) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "No es posible realizar esta operación.\nExiste VB de Cierre de Dia para la fecha ingresada.\nDebe eliminar aquel VB antes de realizar esta acción.",
                                         txtFecha);
                return;
            }
            if (!UtilityCajaElectronica.obtieneIndicadorVB_ForUpdate(VariablesCajaElectronica.vSecMovCaja,
                                                                     ConstantsCajaElectronica.TIPO_VB_QF)) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "No se pudo obtener el Indicador actual de VB de QF.\nPor favor, comuníquese con el area de Sistemas.\n",
                                         txtFecha);
                return;
            }
            if (VariablesCajaElectronica.vIndVBQF.equalsIgnoreCase(FarmaConstants.INDICADOR_N) &&
                VariablesCajaElectronica.vUsuarioQF) {
                FarmaUtility.liberarTransaccion();
                if (!UtilityCajaElectronica.obtieneIndicadorVB_ForUpdate(VariablesCajaElectronica.vSecMovCaja,
                                                                         ConstantsCajaElectronica.TIPO_VB_CAJERO)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this,
                                             "No se pudo obtener el Indicador actual de VB de Cajero.\nPor favor, comuniquese con el area de Sistemas.\n",
                                             txtFecha);
                    return;
                }
                if (VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this,
                                             "No se pudo realizar la operación.\nPrimero otorgue el VB de Cajero.\n",
                                             txtFecha);
                } else {
                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Está seguro de otorgar el VB de QF?")) {
                        if (actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja, FarmaConstants.INDICADOR_S,
                                                 ConstantsCajaElectronica.TIPO_VB_QF)) {
                            //***
                            //JMIRANDA 11.03.2010 SE ACEPTA LA DEVOLUCION DE FONDO DE SENCILLO
                            if (UtilityFondoSencillo.indActivoFondo()) {
                                VariablesFondoSencillo.vIndTieneMontoDevolver =
                                        UtilityFondoSencillo.getIndTieneDevFondo(FarmaVariables.vNuSecUsu,
                                                                                 VariablesCajaElectronica.vSecMovCaja,
                                                                                 this, tblFormasPago).trim();
                                if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("F")) {
                                    aceptaDevSencilloQF();
                                    UtilityFondoSencillo.imprimeVoucherDevoluciones(this,
                                                                                    VariablesCajaElectronica.vSecMovCaja,
                                                                                    tblFormasPago);
                                }
                            }
                            FarmaUtility.aceptarTransaccion();
                            evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_QF); //existe VB de qf para el mov de caja
                            FarmaUtility.showMessage(this, "Se otorgó correctamente el VB de QF", txtFecha);
                            evaluaDeficitAsumidoCajero(VariablesCajaElectronica.vSecMovCaja,
                                                       VariablesCajaElectronica.vFaltante); //si faltante > 0, se registra el deficit asuimido
                            cargaListaFormasPagoCierre();
                            cargaListaCuadraturasCierre();
                            evaluaTotales();
                        }
                    }
                    FarmaUtility.liberarTransaccion();
                }
            } else if (VariablesCajaElectronica.vIndVBQF.equalsIgnoreCase(FarmaConstants.INDICADOR_N) &&
                       VariablesCajaElectronica.vUsuarioCajero && !VariablesCajaElectronica.vUsuarioQF) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Solo un usuario con Rol Administrador de Local puede otorgar el VB de QF a este Movimiento de Caja.",
                                         txtFecha);
            } else if (VariablesCajaElectronica.vIndVBQF.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                       VariablesCajaElectronica.vUsuarioCajero && !VariablesCajaElectronica.vUsuarioQF) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Su Rol no le permite eliminar el VB de QF para este movimiento de caja.",
                                         txtFecha);
            } else if (VariablesCajaElectronica.vIndVBQF.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                       VariablesCajaElectronica.vUsuarioQF) {
                if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Está seguro de eliminar el VB de QF?")) {
                    if (actualizaIndicadorVB(VariablesCajaElectronica.vSecMovCaja, FarmaConstants.INDICADOR_N,
                                             ConstantsCajaElectronica.TIPO_VB_QF)) {
                        FarmaUtility.aceptarTransaccion();
                        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_CAJERO); //existe VB de cajero para el mov de caja
                        FarmaUtility.showMessage(this, "Se eliminó correctamente el VB de QF", txtFecha);
                    }
                }
                FarmaUtility.liberarTransaccion();
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Error al validad Usuario y VB de QF.\nComuniquese con el area de Sistema!!!",
                                         txtFecha);
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cambiar VB de QF.\n" +
                    e.getMessage(), txtFecha);
        }
    }

    private boolean actualizaIndicadorVB(String pSecMovCaja, String pIndicadorVB, String pTipVB) {
        try {
            DBCajaElectronica.actualizaIndicadorVB(pSecMovCaja, pIndicadorVB, pTipVB);
            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al cambiar el VB.\nPor favor, comuniquese con el area de Sistemas.\n" +
                    sql.getMessage(), txtFecha);
            return false;
        }
    }

    private String obtieneMontoTotalSistema(String pSecMovCaja) {
        String monTotalSistema = new String();
        try {
            monTotalSistema = DBCajaElectronica.obtenerMontoTotalSistema(pSecMovCaja);
        } catch (SQLException sql) {
            log.error("", sql);
            monTotalSistema = "0.00";
            FarmaUtility.showMessage(this, "Error al obtener Monto Total del Sistema. \n " + sql.getMessage(), null);
        }
        return monTotalSistema;
    }

    private void evaluaTotales() {
        double montoTotalSistema = 0.00;
        double montoTotalCierre = 0.00;
        double montoFaltante = 0.00;
        //JMIRANDA 11.03.2010
        double montoFondoSencillo = 0.00;
        String montoTotalSistema_S = "";
        //suma de total cierre de caja

        //GFonseca 21/08/2013 , sumar la recaudacion al monto total del sistema
        String montoTotalRecaudacion_S = "";
        double montoTotalRecaudacion = 0.00;
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        montoTotalRecaudacion_S =
                facadeRecaudacion.getMontoTotalRecaudacionByMovCaja(VariablesCajaElectronica.vSecMovCaja);

        if (montoTotalRecaudacion_S != null && montoTotalRecaudacion_S != "") {
            montoTotalRecaudacion = FarmaUtility.getDecimalNumber(montoTotalRecaudacion_S.trim());
        }
        log.debug("montoTotalRecaudacion : " + montoTotalRecaudacion);
        //Fin GFonseca
        montoFondoSencillo = FarmaUtility.getDecimalNumber(getFondoSencilloAsignado().trim());
        montoTotalCierre =
                FarmaUtility.getDecimalNumber(lblSubTotalCuadraturas.getText().trim()) + FarmaUtility.getDecimalNumber(lblSubTotalFormaPago.getText().trim());
        montoTotalCierre = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(montoTotalCierre));
        log.debug("montoTotalCierre : " + montoTotalCierre);
        //monto total de sistemas
        montoTotalSistema_S = obtieneMontoTotalSistema(VariablesCajaElectronica.vSecMovCaja);
        montoTotalSistema = FarmaUtility.getDecimalNumber(montoTotalSistema_S.trim());
        log.debug("montoTotalSistema : " + montoTotalSistema);
        //faltante o sobrante
        //JMIRANDA 11.03.2010
        //GFonseca 21/08/2013 , sumar la recaudacion al monto total del sistema
        montoFaltante = (montoTotalSistema + montoTotalRecaudacion + montoFondoSencillo) - montoTotalCierre;
        //Fin GFonseca
        //montoFaltante = montoTotalSistema - montoTotalCierre;
        if (montoFaltante < 0) {
            lblFaltante_T.setText("con sobrante de S/.");
            //montoFaltante = 0.00;
        } else if (montoFaltante > 0) {
            lblFaltante_T.setText("con faltante de S/.");
        }
        VariablesCajaElectronica.vFaltante = montoFaltante;
        montoFaltante = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(Math.abs(montoFaltante)));
        log.debug("montoFaltante : " + montoFaltante);

        //GFonseca 21/08/2013 , sumar la recaudacion al monto total del sistema
        montoTotalSistema = montoTotalSistema + montoTotalRecaudacion;
        //Fin GFonseca
        lblMontoTotalSistema.setText("" + FarmaUtility.formatNumber(montoTotalSistema));
        lblMontoTotalCierre.setText("" + FarmaUtility.formatNumber(montoTotalCierre));
        lblFaltante.setText("" + FarmaUtility.formatNumber(montoFaltante));
        //JMIRANDA 10.03.2010
        lblFondoSencillo.setText("" + FarmaUtility.formatNumber(montoFondoSencillo));
    }

    private void evaluaDeficitAsumidoCajero(String pSecMovCaja, double pMontoDeficit) {
        try {
            log.debug("*********** faltante : " + pMontoDeficit + " *************");
            DBCajaElectronica.evaluaDeficitAsumidoCajero(pSecMovCaja, pMontoDeficit);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al evaluar Deficit asumido de Cajero.\n " + sql.getMessage(), null);
        }
    }

    /*private void obtieneComprobantesMinMax()
  {
    try
    {
      VariablesCajaElectronica.compMinMax = DBCajaElectronica.obtieneComprobanteMinMax
                                            (VariablesCajaElectronica.vSecMovCaja);
    } catch(SQLException sql)
    {
      VariablesCajaElectronica.compMinMax = new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener información de comprobantes : \n" + sql.getMessage(),null);
    }
    if(VariablesCajaElectronica.compMinMax.size()==1)
    {
      VariablesCajaElectronica.vBoletaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(0)).trim();
      VariablesCajaElectronica.vBoletaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(1)).trim();
      VariablesCajaElectronica.vFacturaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(2)).trim();
      VariablesCajaElectronica.vFacturaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(3)).trim();
    } else
    {
      VariablesCajaElectronica.vBoletaMin = "";
      VariablesCajaElectronica.vBoletaMax = "";
      VariablesCajaElectronica.vFacturaMin = "";
      VariablesCajaElectronica.vFacturaMax = "";
    }
  }*/

    private void imprimeCierreTurno() {
        if (!UtilityCajaElectronica.obtieneIndicadorVB_ForUpdate(VariablesCajaElectronica.vSecMovCaja,
                                                                 ConstantsCajaElectronica.TIPO_VB_QF)) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No se pudo obtener el Indicador actual de VB de QF.\nPor favor, comuniquese con el area de Sistemas.\n",
                                     txtFecha);
            return;
        }
        if (VariablesCajaElectronica.vIndVBQF.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No se puede realizar esta operación.\nEl Movimiento de Caja NO cuenta con VB de QF.",
                                     txtFecha);
            return;
        } else if (VariablesCajaElectronica.indUsu_Cierre_Caj_QF) { //jquispe 03.01.2011 Forma Nueva
            if (!VariablesCajaElectronica.vUsuarioQF) {
                if (!vIsUsuarioQF) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this,
                                             "Solo un usuario con Rol Administrador de Local puede realizar esta operación.",
                                             txtFecha);
                    return;
                }
            }

        } else {
            //jquispe 03.01.2011 Forma Antigua
            if (!vIsUsuarioQF && VariablesCajaElectronica.vUsuarioCajero) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Solo un usuario con Rol Administrador de Local puede realizar esta operación.",
                                         txtFecha);
                return;
            }
        }

        FarmaUtility.liberarTransaccion();
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", tblFormasPago);
            return;
        }
        try {
            int COL_DESC_TIP_COMP = 0;
            int COL_NUM_SERIE_LOCAL = 1;
            int COL_RANG_INI = 2;
            int COL_RANG_FIN = 3;
            int COL_MONT_MIN = 4;
            int COL_MONT_FIN = 5;
            int COL_TIP_COMP = 6;
            int COL_IND_FINAL = 7;
            //UtilityCajaElectronica.obtieneComprobantesMinMaxUsuario(this, VariablesCajaElectronica.vSecMovCaja);
            ArrayList myArrayUsuario =
                UtilityCajaElectronica.obtieneRangoComprobantesUsuario(this, ConstantsCajaElectronica.TIP_INGRESO_COMP_CT);
            //obtieneComprobantesMinMax();
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(60) + "REPORTE CIERRE TURNO", true);
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Nombre Compañia : " + FarmaVariables.vNomCia +
                             "      Local : " + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal, true);
            //vPrint.printBold("Local           : " + FarmaVariables.vCodLocal + " - "+FarmaVariables.vDescLocal, true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Dia de Venta    : " +
                             VariablesCajaElectronica.vDiaVenta + FarmaPRNUtility.llenarBlancos(3) +
                             "Tipo de Cambio : " + lblMontoTipoCambio.getText() + "   " + "Cajero: " +
                             VariablesCajaElectronica.vCodCajero + " - " + VariablesCajaElectronica.vNombreCajero +
                             FarmaPRNUtility.llenarBlancos(3) + "Caja : " + VariablesCajaElectronica.vNumCaja +
                             FarmaPRNUtility.llenarBlancos(3) + "Turno: " + VariablesCajaElectronica.vNumTurno, true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Nombre Q.F      : " +
                             VariablesCajaElectronica.vNombreUsuarioLogueado, true);
            /*vPrint.printBold("Cajero: " + VariablesCajaElectronica.vCodCajero + " - " + VariablesCajaElectronica.vNombreCajero + FarmaPRNUtility.llenarBlancos(5) + "Caja : " + VariablesCajaElectronica.vNumCaja + FarmaPRNUtility.llenarBlancos(5)+ "Turno: " +
     VariablesCajaElectronica.vNumTurno, true);*/
            /***********lmr*****************/
            for (int i = 0; i < myArrayUsuario.size(); i++) {
                String descTipoComp = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_DESC_TIP_COMP);
                String tipoComp = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_TIP_COMP);
                String numSerie = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_NUM_SERIE_LOCAL);
                String rangoIni = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_RANG_INI);
                String rangoFin = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_RANG_FIN);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Rango de ==> " + descTipoComp + " De : " +
                                 numSerie + rangoIni + FarmaPRNUtility.llenarBlancos(3) + "Hasta : " + numSerie +
                                 rangoFin, true);
            }
            /***********lmr*****************/
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Fechas   ==> Apertura de Caja : " +
                             VariablesCajaElectronica.vFecAperturaCaja + "       " + "Cierre de Caja   : " +
                             VariablesCajaElectronica.vFecCierreCaja, true);
            //vPrint.printBold("Fecha Cierre de Caja   : " + VariablesCajaElectronica.vFecCierreCaja, true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.setEndHeader();
            //vPrint.printLine(" ",true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "ENTREGAS DE EFECTIVO O EQUIVALENTE DE EFECTIVO ",
                             true);
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "Formas de Pago                 Moneda           Monto    Total Soles", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "-----------------------        --------------------------------------", true);
            for (int i = 0; i < tblFormasPago.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.llenarBlancos(15) +
                                      FarmaPRNUtility.alinearIzquierda(((String)tblFormasPago.getValueAt(i, 0)).trim(),
                                                                       30) + " " +
                                      FarmaPRNUtility.alinearIzquierda(((String)tblFormasPago.getValueAt(i, 1)).trim(),
                                                                       7) + " " +
                                      FarmaPRNUtility.alinearDerecha(((String)tblFormasPago.getValueAt(i, 2)).trim(),
                                                                     14) + " " +
                                      FarmaPRNUtility.alinearDerecha(((String)tblFormasPago.getValueAt(i, 3)).trim(),
                                                                     15), true);
            }
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(72) + "------------", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(55) + "Sub Total S/. " +
                             FarmaPRNUtility.alinearDerecha(lblSubTotalFormaPago.getText(), 15) + " (A)", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            //vPrint.printLine(" ",true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "OTRAS CUADRATURAS ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "Cuadratura                                        Total Soles", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "-------------------------------------           -------------", true);
            String Deficit = "N";
            String MontoDeficit = "0.00";
            for (int i = 0; i < tblCuadraturas.getRowCount(); i++) {
                //EMAQUERA - 28/05/2015 - Indicador para ver si presenta Deficit
                String CodCuadratura = (String)tblCuadraturas.getValueAt(i,0);
                
                if (CodCuadratura.equals("010")){
                    Deficit = "S";
                    MontoDeficit = (String)tblCuadraturas.getValueAt(i,2);
                }
                
                vPrint.printCondensed(FarmaPRNUtility.llenarBlancos(15) +
                                      FarmaPRNUtility.alinearIzquierda(((String)tblCuadraturas.getValueAt(i,
                                                                                                          1)).trim().toUpperCase(),
                                                                       45) + " " +
                                      FarmaPRNUtility.alinearDerecha(((String)tblCuadraturas.getValueAt(i,
                                                                                                        2)).trim().toUpperCase(),
                                                                     15), true);

            }
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(63) + "-------------", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(47) + "Sub Total S/. " +
                             FarmaPRNUtility.alinearDerecha(lblSubTotalCuadraturas.getText(), 15) + " (B)", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(" ", true);
            vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Monto Total Cierre    : " +
                                        lblMontoTotalCierre.getText().trim() + " (A+B)", true);
            vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Monto Total Sistema   : " +
                                        lblMontoTotalSistema.getText().trim(), true);
            //JMIRANDA 12.03.2010
            if (!UtilityFondoSencillo.indActivoFondo()) {
                vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Diferencia   : " +
                                            FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(lblMontoTotalCierre.getText()) -
                                                                      FarmaUtility.getDecimalNumber(lblMontoTotalSistema.getText())),
                                            true);
            } else {

                vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Fondo de Sencillo     : " +
                                            lblFondoSencillo.getText().trim(), true);
                vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Diferencia   : " +
                                            FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(lblMontoTotalCierre.getText()) -
                                                                      (FarmaUtility.getDecimalNumber(lblMontoTotalSistema.getText()) +
                                                                       FarmaUtility.getDecimalNumber(lblFondoSencillo.getText()))),
                                            true);
            }
            vPrint.printLine(" ", true);
            //ERIOS 22.06.2015 Imprime cantidad de inscripcion por turno
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) + "Vouchers por Inscripcion (Monedero) : " + lblInscripcionTurno.getText(), true);
            vPrint.printLine(" ", true);
            //CHUANES 23.02.2015
            //IMPRIME EL DETALLE DE LOS COMPROBANTES ANULADOS          
            ArrayList lstAnulados=new ArrayList();
            DBCajaElectronica.imprimeDocumentosAnulados(lstAnulados,VariablesCajaElectronica.vSecMovCaja);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +"DOCUMENTOS ANULADOS",true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15)+"NOTA CREDITO"+ FarmaPRNUtility.llenarBlancos(8)+"TIPO DOCUMENTO"+FarmaPRNUtility.llenarBlancos(8)+
                             "NUM. DOCUMENTO"+FarmaPRNUtility.llenarBlancos(8)+"USU.ANULA"+FarmaPRNUtility.llenarBlancos(8)+"IMPORTE"+FarmaPRNUtility.llenarBlancos(8)+"OBS.", true);    
            for(int  i=0;i<lstAnulados.size();i++){
            String numDoc=""; 
            String tipDoc="";
            String numDocAnula="";
            String impTotal=""; 
            String usuAnula="";    
            numDoc=((ArrayList)(lstAnulados.get(i))).get(0).toString();
            tipDoc=((ArrayList)(lstAnulados.get(i))).get(1).toString();  
            numDocAnula= ((ArrayList)(lstAnulados.get(i))).get(2).toString();
                
            impTotal=((ArrayList)(lstAnulados.get(i))).get(3).toString(); 
            usuAnula=((ArrayList)(lstAnulados.get(i))).get(4).toString();
             vPrint.printLine(FarmaPRNUtility.llenarBlancos(15)+numDocAnula+ FarmaPRNUtility.llenarBlancos(8)+tipDoc+FarmaPRNUtility.llenarBlancos(8)+
                                 numDoc+FarmaPRNUtility.llenarBlancos(10)+usuAnula+FarmaPRNUtility.llenarBlancos(7)+impTotal+FarmaPRNUtility.llenarBlancos(10)+".......", true);    
                
            
            }
            vPrint.printLine(" ", true);
            //ERIOS 15.06.2015 Imprime recaudaciones anuladas            
            lstAnulados=new ArrayList();
            DBCajaElectronica.imprimeRecaudacionesAnulados(lstAnulados,VariablesCajaElectronica.vSecMovCaja);
            if(lstAnulados.size() > 0){
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +"RECAUDACIONES ANULADAS",true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15)+FarmaPRNUtility.alinearIzquierda("Recaudacion",20)+FarmaPRNUtility.alinearIzquierda("Moneda",22)+
                                 FarmaPRNUtility.alinearIzquierda("Voucher",22)+FarmaPRNUtility.alinearIzquierda("Usu.Anula",17)+FarmaPRNUtility.alinearIzquierda("Importe",15), true);
                for(int  i=0;i<lstAnulados.size();i++){
                    String numDocAnula= ((ArrayList)(lstAnulados.get(i))).get(0).toString();                
                    String tipDoc=((ArrayList)(lstAnulados.get(i))).get(1).toString();  
                    String numDoc=((ArrayList)(lstAnulados.get(i))).get(2).toString();
                    String usuAnula=((ArrayList)(lstAnulados.get(i))).get(3).toString();
                    String impTotal=((ArrayList)(lstAnulados.get(i))).get(4).toString(); 
                    
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(15)+FarmaPRNUtility.alinearIzquierda(numDocAnula,20)+FarmaPRNUtility.alinearIzquierda(tipDoc,22)+
                                     FarmaPRNUtility.alinearIzquierda(numDoc,22)+FarmaPRNUtility.alinearIzquierda(usuAnula,17)+FarmaPRNUtility.alinearIzquierda(impTotal,15), true);    
                }
                vPrint.printLine(" ", true);
            }
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) + "Observaciones : ", true);
            vPrint.printCutLine(txtSObs.getText().trim(), 100, 15);
            //EMAQUERA - 28/05/2015 - Si el indicador es S imprime el mensaje.
            if (Deficit.equals("S")){
            ArrayList myArrayMsje = DBCajaElectronica.obtenerMsjeCierreTurno(VariablesCajaElectronica.vCodCajero, VariablesCajaElectronica.vNombreCajero, VariablesCajaElectronica.vNumCaja, MontoDeficit);              
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            
                for (int i = 0; i < myArrayMsje.size(); i++) {
                    String descMsje = FarmaUtility.getValueFieldArrayList(myArrayMsje, i, 0);
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(10) + descMsje, true);
                }            
            }     
            //
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);            
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + "__________________________" +
                             FarmaPRNUtility.llenarBlancos(10) + "_________________________", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + " Nombre y Firma del Cajero" +
                             FarmaPRNUtility.llenarBlancos(10) + "  Nombre y Firma del QF", true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), tblFormasPago);
        }
    }

    private void guardaInformacionMovimientoCaja(boolean pLimpiaVariables) {
        if (pLimpiaVariables) {
            VariablesCajaElectronica.vNombreUsuarioLogueado = "";
            VariablesCajaElectronica.vDiaVenta = "";
            VariablesCajaElectronica.vNumCaja = "";
            VariablesCajaElectronica.vCodCajero = "";
            VariablesCajaElectronica.vNumTurno = "";
            VariablesCajaElectronica.vNombreCajero = "";
            VariablesCajaElectronica.vFecAperturaCaja = "";
            VariablesCajaElectronica.vFecCierreCaja = "";
            VariablesCajaElectronica.vBoletaMinUsuario = "";
            VariablesCajaElectronica.vBoletaMaxUsuario = "";
            VariablesCajaElectronica.vFacturaMinUsuario = "";
            VariablesCajaElectronica.vFacturaMaxUsuario = "";
        } else {
            VariablesCajaElectronica.vNombreUsuarioLogueado = lblUsuario.getText().trim();
            VariablesCajaElectronica.vDiaVenta = txtFecha.getText().trim();
            VariablesCajaElectronica.vNumCaja = txtCaja.getText().trim();
            VariablesCajaElectronica.vCodCajero = txtCodigoCajero.getText().trim();
            VariablesCajaElectronica.vNumTurno =
                    FarmaLoadCVL.getCVLCode(ConstantsCajaElectronica.NOM_HASHTABLE_CMBTURNO,
                                            cmbTurno.getSelectedIndex());
            VariablesCajaElectronica.vNombreCajero = lblNombre.getText().trim();
            VariablesCajaElectronica.vFecAperturaCaja = lblApertura.getText().trim();
            VariablesCajaElectronica.vFecCierreCaja = lblCierre.getText().trim();
        }
    }

    private int evaluaCantEliminacionVBCajero(String pSecMovCaja) {
        int cant = 1;
        try {
            cant = DBCajaElectronica.evaluaEliminacionVBCajero(pSecMovCaja);
        } catch (SQLException sql) {
            log.error("", sql);
            cant = 1;
            FarmaUtility.showMessage(this, "Error al evaluar eliminacion de VB Cajero.\n" +
                    sql.getMessage(), null);
        }
        return cant;
    }

    private boolean cargaLoginOper() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        }
        return FarmaVariables.vAceptar;
    }

    private void evaluaMsgEtiquetaVB(int pIndicador) {
        switch (pIndicador) {
            //TIENE VB DE CAJERO
        case 1:
            lblMsgVisado_T.setVisible(true);
            lblMsgVisado_T.setText("El movimiento de caja ya cuenta con VoBo de Cajero.");
            break;
            //TIENE VB DE QF
        case 2:
            lblMsgVisado_T.setVisible(true);
            lblMsgVisado_T.setText("El movimiento de caja ya cuenta con VoBo de QF.");
            break;
            //NO TIENE VB DE CAJERO, NI DE QF, NO SE ENCONTRO MOVIMIENTO DE CAJA
        default:
            lblMsgVisado_T.setVisible(false);
            lblMsgVisado_T.setText("");
            break;
        }
    }

    private void cargaDetalleCuadratura() {
        int row = tblCuadraturas.getSelectedRow();
        if (row > -1) {
            VariablesCajaElectronica.vTipCuadratura = "";
            VariablesCajaElectronica.vCodCuadratura = tblCuadraturas.getValueAt(row, 0).toString().trim();
            if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DEFICIT_CAJERO)) {
                FarmaUtility.showMessage(this,
                                         "No es posible realizar la operación.\nEsta cuadratura no contiene detalle.",
                                         tblCuadraturas);
                return;
            }
            VariablesCajaElectronica.vDescCuadratura = tblCuadraturas.getValueAt(row, 1).toString().trim();
            DlgListaEliminacionCuadratura dlgListaEliminacionCuadratura =
                new DlgListaEliminacionCuadratura(myParentFrame, "", true);
            dlgListaEliminacionCuadratura.setVisible(true);
            cargaListaCuadraturasCierre();
        }
    }

    private void ingresarRangoComprobantes() {
        if (VariablesCajaElectronica.vSecMovCaja.trim().equalsIgnoreCase("") ||
            VariablesCajaElectronica.vSecMovCaja.length() == 0) {
            FarmaUtility.showMessage(this, "Debe realizar la busqueda para poder ingresar los comprobantes.",
                                     txtFecha);
            return;
        }
        VariablesCajaElectronica.vIndCompValidos =
                UtilityCajaElectronica.obtieneIndicadorComprobatesValidosUsuario(this,
                                                                                 VariablesCajaElectronica.vSecMovCaja);
        VariablesCajaElectronica.vTipoIngresoComprobantes = ConstantsCajaElectronica.TIP_INGRESO_COMP_CT;

        //JCORTEZ 02/02/2009
        String Ind_Multi = getTipoLocal();
        log.debug("VariablesCajaElectronica.vIndCompValidos-->" + VariablesCajaElectronica.vIndCompValidos);
        if (Ind_Multi.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
            VariablesCajaElectronica.vIndCompValidos.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            ingresaComprobantes();
        } else {
            DlgIngresoRangoComprobantes dlgIngresoRangoComprobantes =
                new DlgIngresoRangoComprobantes(myParentFrame, "", true);
            dlgIngresoRangoComprobantes.setVisible(true);
        }
    }

    /**
     * Ingresa comprobantes
     * */
    private void ingresaComprobantes() {
        obtieneDatosRangoComprobante();
        log.debug("VariablesCajaElectronica.vSecMovCaja-->" + VariablesCajaElectronica.vSecMovCaja);
        try {
            if (VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT) &&
                VariablesCajaElectronica.vIndRangoGrabado.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                DBCajaElectronica.insertaRangoCompMovCaja(VariablesCajaElectronica.vSecMovCaja);
            }

            VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_S;
            if (verificarIndSobresParcialesConcepSobre()) { //ASOSA, 07.06.2010
                grabarSobresAutomaticamente(); //ASOSA, 04.06.2010
            }
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Los rangos de comprobantes se registraron correctamente", null);
            autoFondoSen = "1";
        } catch (SQLException sql) {
            VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_N;
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al insertar los rangos de comprobantes.\n " + sql.getMessage(),
                                     txtFecha);
        } finally {
            if (VariablesCajaElectronica.vTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT)) {
                UtilityCajaElectronica.actualizaIndicadorCompValidosCT(this, VariablesCajaElectronica.vSecMovCaja,
                                                                       VariablesCajaElectronica.vIndCompValidos);
            }
        }
    }

    /**
     *
     * */
    private void obtieneDatosRangoComprobante() {
        VariablesCajaElectronica.vTipCompUsuario = ConstantsCajaElectronica.TIP_INGRESO_COMP_CT;
        VariablesCajaElectronica.vNumSerieUsuario = FarmaVariables.vCodLocal;
        VariablesCajaElectronica.vNumeroMinUsuario = "0000000";
        VariablesCajaElectronica.vNumeroMaxUsuario = "0000000";
        VariablesCajaElectronica.vIndRangoGrabado = FarmaConstants.INDICADOR_N;
    }

    /**
     * Se obtiene el tipo de local caja
     * */
    private String getTipoLocal() {
        String tipo = "";
        try {
            tipo = DBCajaElectronica.getTipoLocal();
            log.debug("tipo : " + tipo);
        } catch (SQLException sql) {
            tipo = "";
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener tipo local.", null);
        }
        return tipo.trim();
    }

    private boolean validaRangoComprobantesIngresados() {
        int COL_DESC_TIP_COMP = 0;
        int COL_NUM_SERIE_LOCAL = 1;
        int COL_RANG_INI = 2;
        int COL_RANG_FIN = 3;
        int COL_MONT_MIN = 4;
        int COL_MONT_FIN = 5;
        int COL_TIP_COMP = 6;
        int COL_IND_FINAL = 7;

        boolean result = true;
        boolean encontroTipoSerie = false;
        ArrayList myArrayCorrecto =
            UtilityCajaElectronica.obtieneRangoComprobantesCorrectos(this, ConstantsCajaElectronica.TIP_INGRESO_COMP_CT);
        ArrayList myArrayUsuario =
            UtilityCajaElectronica.obtieneRangoComprobantesUsuario(this, ConstantsCajaElectronica.TIP_INGRESO_COMP_CT);
        int cantRangosIngresados = myArrayUsuario.size();
        int cantRangosCorrectos = myArrayCorrecto.size();
        for (int i = 0; i < myArrayUsuario.size(); i++) {
            encontroTipoSerie = false;
            String tipoCompTable = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_TIP_COMP);
            String numSerieTable = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_NUM_SERIE_LOCAL);
            String rangoIniTable = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_RANG_INI);
            String rangoFinTable = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_RANG_FIN);
            for (int j = 0; j < myArrayCorrecto.size(); j++) {
                String tipoCompArray = FarmaUtility.getValueFieldArrayList(myArrayCorrecto, j, 0);
                String numSerieArray = FarmaUtility.getValueFieldArrayList(myArrayCorrecto, j, 1);
                String rangoIniArray = FarmaUtility.getValueFieldArrayList(myArrayCorrecto, j, 2);
                String rangoFinArray = FarmaUtility.getValueFieldArrayList(myArrayCorrecto, j, 3);
                if (tipoCompTable.equals(tipoCompArray) && numSerieTable.equals(numSerieArray) &&
                    rangoIniTable.equals(rangoIniArray) && rangoFinTable.equals(rangoFinArray)) {
                    encontroTipoSerie = true;
                    break;
                }
            }
            if (!encontroTipoSerie) {
                result = encontroTipoSerie;
                return result;
            }
        }
        if (cantRangosIngresados != cantRangosCorrectos) {
            result = false;
            return result;
        }
        return result;
    }

    /*private boolean validaComprobantesIngresados()
  {
    UtilityCajaElectronica.obtieneComprobantesMinMaxUsuario(this, VariablesCajaElectronica.vSecMovCaja);
    if( VariablesCajaElectronica.vBoletaMin.equalsIgnoreCase(VariablesCajaElectronica.vBoletaMinUsuario) &&
        VariablesCajaElectronica.vBoletaMax.equalsIgnoreCase(VariablesCajaElectronica.vBoletaMaxUsuario) &&
        VariablesCajaElectronica.vFacturaMin.equalsIgnoreCase(VariablesCajaElectronica.vFacturaMinUsuario) &&
        VariablesCajaElectronica.vFacturaMax.equalsIgnoreCase(VariablesCajaElectronica.vFacturaMaxUsuario) )
      return true;
    return false;
  }*/

    private String obtieneObservacionCierreTurno(String pSecMovCaja) {
        String observacion = "";
        try {
            observacion = DBCajaElectronica.obtenerObservacionCierreTurno(pSecMovCaja);
            log.debug("observacion : " + observacion);
        } catch (SQLException sql) {
            observacion = "";
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la observacion ingresada para el cierre de turno.", null);
        }
        return observacion.trim();
    }

    private void colocaObservacionCierreTurno(boolean pVerObs) {
        if (pVerObs) {
            VariablesCajaElectronica.vObsCierreTurno =
                    obtieneObservacionCierreTurno(VariablesCajaElectronica.vSecMovCaja);
            txtSObs.setText(VariablesCajaElectronica.vObsCierreTurno);
        } else
            VariablesCajaElectronica.vObsCierreTurno = "";
        log.debug("VariablesCajaElectronica.vObsCierreTurno : " + VariablesCajaElectronica.vObsCierreTurno);
    }

    private void actualizaObservacionCierreTurno(String pSecMovCaja, String pObservacion) {
        try {
            DBCajaElectronica.actualizaObservacionCierreTurno(pSecMovCaja, pObservacion);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al actualizar la observacion para el cierre de turno.", null);
        }
    }

    private void colocaTipoCambioDiaVenta(String pFechaTipoCambio) {
        obtieneTipoCambio(pFechaTipoCambio);
        lblMontoTipoCambio.setText(FarmaUtility.formatNumber(FarmaVariables.vTipCambio));
    }

    private boolean existeFechaCierreDia(String pFechaCierreDia) {
        //String diaVenta = VariablesCajaElectronica.vFechaCuadratura;
        int cont = 1;
        if (pFechaCierreDia.length() == 0 || !FarmaUtility.validaFecha(pFechaCierreDia, "dd/MM/yyyy")) {
            FarmaUtility.showMessage(this, "El valor de la fecha no es correcto.", txtFecha);
            return true;
        }
        try {
            cont = DBCajaElectronica.validaFechaCierreDiaRegistrar(pFechaCierreDia);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al validar existencia de fecha de cierre de dia de venta.\n" +
                    sql, txtFecha);
            return true;
        }
        if (cont > 0)
            return true;
        return false;
    }

    private void evaluaExistenciaFechaCierreDia(String pFechaCierreDia) {
        VariablesCajaElectronica.vExisteFechaCierreDia = existeFechaCierreDia(pFechaCierreDia);
    }

    private void guardaInformacionComprobantes() {
        VariablesCajaElectronica.vBoletaMinUsuario = VariablesCajaElectronica.vBoletaMinGeneral;
        VariablesCajaElectronica.vBoletaMaxUsuario = VariablesCajaElectronica.vBoletaMaxGeneral;
        VariablesCajaElectronica.vFacturaMinUsuario = VariablesCajaElectronica.vFacturaMinGeneral;
        VariablesCajaElectronica.vFacturaMaxUsuario = VariablesCajaElectronica.vFacturaMaxGeneral;
    }

    private void listaCajerosDiaCierreTurno() {
        VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_CAJEROS_DIA_VENTA;
        VariablesCajaElectronica.vFechaDiaCajaTurno = txtFecha.getText().trim();
        String codCajero = txtCodigoCajero.getText().trim();
        if (codCajero.length() == 0) {
            DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
            dlgListaMaestros.setVisible(true);
            if (FarmaVariables.vAceptar) {
                FarmaVariables.vAceptar = false;
                txtCodigoCajero.setText(FarmaUtility.caracterIzquierda(VariablesPtoVenta.vCodMaestro.trim(), 3, "0"));
            }
        } else {
            txtCodigoCajero.setText(FarmaUtility.caracterIzquierda(codCajero, 3, "0"));
            codCajero = txtCodigoCajero.getText().trim();
            if (FarmaUtility.validaFecha(VariablesCajaElectronica.vFechaDiaCajaTurno, "dd/MM/yyyy") &&
                codCajero.length() == 3)
                txtCaja.setText(buscaCajasAsignadasUsuario_DiaVenta(VariablesCajaElectronica.vFechaDiaCajaTurno,
                                                                    codCajero));
            FarmaUtility.moveFocus(txtCaja);
        }
    }

    private boolean existeVBCierreDia(String pFechaCierreDia) {
        int cont = 0;
        try {
            cont = DBCajaElectronica.validaVBCierreDia(pFechaCierreDia);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al validar existencia de VB en cierre de dia de venta.\n" +
                    sql, txtFecha);
            return false;
        }
        if (cont > 0)
            return true;
        return false;
    }

    private void evaluaExistenciaVBCierreDia(String pFechaCierreDia) {
        VariablesCajaElectronica.vExisteVBCierreDia = existeVBCierreDia(pFechaCierreDia);
    }

    private void cambioFomaPago() {
        VariablesCajaElectronica.vFechaDia = txtFecha.getText().trim();
        DlgCambioFormaPago dlgcambio = new DlgCambioFormaPago(myParentFrame, "", true);
        dlgcambio.setVisible(true);
    }

    /**
     * Se valida igualdad entre efectivo declarado y existente en sistema, asi como voucher declarado y existente en sistema
     * @AUTHOR JCORTEZ
     * @SINCE 03.03.2010
     * */
    private boolean validaMontoDeclarado() {
        boolean valor = true;
        try {
            log.debug("SEC_MOV_CAJA:::cierre" + VariablesCajaElectronica.vSecMovCaja);
            log.debug("FECHA:::cierre" + VariablesCajaElectronica.vDiaVenta);
            DBCajaElectronica.validaMontoDeclarado(VariablesCajaElectronica.vSecMovCaja,
                                                   VariablesCajaElectronica.vDiaVenta,
                                                   lblSubTotalCuadraturas.getText().trim());
        } catch (SQLException sql) {
            if (sql.getErrorCode() == 20100) {
                //FarmaUtility.showMessage(this,"El MONTO EN EFECTIVO (SOLES/DOLARES) NO ES EL MISMO EN COMPROBANTES.¡Verifique Y corrija!",txtFecha);
                FarmaUtility.showMessage(this, sql.getMessage().substring(9, 72) + "\n" +
                        sql.getMessage().substring(73, 135) + "\n " + " ¡VERIFIQUE Y CORRIJA!", txtFecha);
                valor = false;
            } else if (sql.getErrorCode() == 20101) {
                //FarmaUtility.showMessage(this,"El MONTO EN TARJETAS  NO ES EL MISMO EN COMPROBANTES.¡Verifique Y corrija!",txtFecha);
                FarmaUtility.showMessage(this, sql.getMessage().substring(9, 78) + "\n" +
                        sql.getMessage().substring(79, 142) + "\n " + " ¡VERIFIQUE Y CORRIJA!", txtFecha);
                valor = false;
            } else {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Error al obtener informacion del Cierre Turno Caja.\n" +
                        sql.getMessage(), txtFecha);
                valor = false;
            }
        }
        return valor;
    }

    /**
     * Se valida el indicador
     * @AUTHOR JCORTEZ
     * @SINCE 03.03.2010
     * */
    private void habilitaCambioFormaPago() {

        try {
            VariablesCajaElectronica.vIndCambioFormaPago = DBCajaElectronica.getIndCambioFormaPago();

        } catch (SQLException e) {
            log.error("", e);
            VariablesCajaElectronica.vIndCambioFormaPago = "N";
            FarmaUtility.showMessage(this, "Error al obtener indicador de cambio .\n" +
                    e.getMessage(), txtFecha);
        }

    }

    private void funcion_F8() {
        if (FarmaVariables.vEconoFar_Matriz) {
            FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFecha);
            return;
        }
        //obtieneInfoDatosCaja();
        /*  boolean pPermiteVbCajero = false;
        if (UtilityFondoSencillo.indActivoFondo()) {
            //JMIRANDA 02.03.2010 validar si tiene monto por devolver
            VariablesFondoSencillo.vIndTieneMontoDevolver =
                    UtilityFondoSencillo.getIndTieneDevFondo(FarmaVariables.vNuSecUsu,
                                                             VariablesCajaElectronica.vSecMovCaja,
                                                             this,
                                                             tblFormasPago).trim();
    //        boolean pPermiteVbCajero = false;

            if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("X")) {
                //error
                pPermiteVbCajero = false;
            } else if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("N")) {
                //No tiene ningún pendiente de Aceptar
                pPermiteVbCajero = true;
            } else if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("D")) {
                //tiene devolución pendiente
                VariablesFondoSencillo.vSecMovCajaCierre = VariablesCajaElectronica.vSecMovCaja;
                VariablesFondoSencillo.vMensajeDevolver = "Nuevo Fondo de Sencillo para Devolver.";
                mostrarDevFondoSencillo();
                if(FarmaVariables.vAceptar){
                    FarmaUtility.showMessage(this,"La devolución fue asignada por favor de hablar con el Administrador del Local\n" +
                        "Para aceptar la Devolución del Fondo de Sencillo\n" +
                                         "para que Ud. pueda dar V°B° de Cajero.",tblFormasPago);
                    //imprimeVoucherDevoluciones
                    UtilityFondoSencillo.imprimeVoucherDevoluciones(this,VariablesCajaElectronica.vSecMovCaja,tblFormasPago);
                }
                pPermiteVbCajero = false;
            } else if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("R")) {
                //reingrese monto
                VariablesFondoSencillo.vSecMovCajaCierre = VariablesCajaElectronica.vSecMovCaja;
                VariablesFondoSencillo.vMensajeDevolver = "Debe volver a ingresar Fondo de Sencillo.";
                mostrarDevFondoSencillo();
                if(FarmaVariables.vAceptar){
                    FarmaUtility.showMessage(this,"La devolución fue asignada por favor de hablar con el Administrador del Local\n" +
                        "Para aceptar la Devolución del Fondo de Sencillo\n" +
                                         "para que Ud. pueda dar V°B° de Cajero.",tblFormasPago);
                    //imprime
                    UtilityFondoSencillo.imprimeVoucherDevoluciones(this,VariablesCajaElectronica.vSecMovCaja,tblFormasPago);
                }
                pPermiteVbCajero = false;
            } else if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("S")) {
              //El sencillo fue aceptado
              pPermiteVbCajero = true;
              log.debug("");
            } else if (VariablesFondoSencillo.vIndTieneMontoDevolver.equalsIgnoreCase("F")) {
              pPermiteVbCajero = false;
              FarmaUtility.showMessage(this,"El Administrador del Local debe Aceptar la Devolución del Fondo de Sencillo\n" +
                                       "para que Ud. pueda dar V°B° de Cajero.",tblFormasPago);
            }
        }
        else {
            pPermiteVbCajero = true;
        }
        ////
        if(pPermiteVbCajero) {*/

        //obtieneInfoDatosCaja();
        cambiaVBCajero();
        //  }
    }

    private void mostrarDevFondoSencillo() {
        DlgDevolverMontoFondoSencillo dlgDevMonto = new DlgDevolverMontoFondoSencillo(myParentFrame, "", true);
        dlgDevMonto.setVisible(true);
    }

    private void funcion_F9() {
        if (FarmaVariables.vEconoFar_Matriz) {
            FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtFecha);
            return;
        }
        //obtieneInfoDatosCaja();
        cambiaVBQF();
    }

    private String getFondoSencilloAsignado() {
        String monto = "";
        try {
            monto = DBFondoSencillo.getMontoDevolver(FarmaVariables.vNuSecUsu, VariablesCajaElectronica.vSecMovCaja);

        } catch (SQLException sql) {
            log.debug("no se pudo obtener el Fondo de Sencillo");
        }
        return monto;
    }

    private boolean aceptaDevSencilloQF() {
        boolean flag = false;
        try {
            DBFondoSencillo.aceptaDevolucionSencillo(FarmaVariables.vIdUsu, FarmaVariables.vIpPc,
                                                     VariablesCajaElectronica.vSecMovCaja);
            flag = true;
        } catch (SQLException sql) {
            flag = false;
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al aceptar la devolución de Fondo Sencillo.", tblFormasPago);

        }
        return flag;
    }

    private void mostrarTextFondoSencillo() {
        if (UtilityFondoSencillo.indActivoFondo()) {
            lblFondoSencillo.setVisible(true);
            lblFondoSencillo_T.setVisible(true);
        } else {
            lblFondoSencillo.setVisible(false);
            lblFondoSencillo_T.setVisible(false);
        }
    }

    /**
     * GRABA LOS SOBRES PARCIALES Y LOS APRUEBA(GRABA EN FORMA PAGO ENTREGA Y EN CE_SOBRE
     * @author ASOSA
     * @since 03.06.2010
     */
    private void grabarSobresAutomaticamente() {
        try {
            DBCajaElectronica.grabarSobresAutomaticamente(VariablesCajaElectronica.vSecMovCaja);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);

            if (e.getErrorCode() > 20023) {
                FarmaUtility.showMessage(this, e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                         null);
            } else if (e.getErrorCode() > 20024) {
                FarmaUtility.showMessage(this, e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                         null);
            } else {
                FarmaUtility.showMessage(this, "ERROR en grabarSobresAutomaticamente: " + e.getMessage(), null);
            }
        }
    }

    /**
     * ES verdadero si el indicador de cierre parcial 366 y sobres 317 esta activo
     * @author ASOSA
     * @since 07.06.2010
     * @return
     */
    private boolean verificarIndSobresParcialesConcepSobre() {
        boolean flag = false;
        try {
            flag = DBCaja.obtieneIndicadorControlSobres();
        } catch (SQLException e) {
            log.error("", e);
            flag = false;
            FarmaUtility.showMessage(this, "ERROE en verificar sobres parciales y concepto de sobres", null);
        }
        return flag;
    }
    //JQUISPE 04.06.2010 cambio para cambio de forma de pago

    private void habilitarBtnCambioFP() {
        if (VariablesCajaElectronica.vIndCambioFormaPago.equals("N")) {
            lblCambioFormaPago.setVisible(false);
        } else {
            lblCambioFormaPago.setVisible(true);
        }
    }

    /**
     * Determine si se debe dejar dar VB de cajero, en el caso este activo deberia entonces haberlo declarado como forma de pago entrega
     * @author ASOSA
     * @since 21.06.2010
     * @return
     */
    private boolean getDeter_neces_fpFSEN() {
        boolean flag = false;
        String ind = "N";
        try {
            ind = DBFondoSencillo.getDeter_neces_fpFSEN(VariablesCajaElectronica.vSecMovCaja);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "ERROR en getDeter_neces_fpFSEN de DlgMovCaja: " + e.getMessage(), null);
        }
        if (ind.equalsIgnoreCase("S")) {
            flag = true;
        }
        return flag;
    }

    /**
     * Obliga a que se ingrese el fondo de sencillo desde un principio o cuando quiere dar VB
     * @author ASOSA
     * @since 21.06.2010
     */
    private void mandarDeclararFSEN() {
        FarmaUtility.showMessage(this,
                                 "Ud. acepto un fondo de sencillo que debe declarar\ncomo devuelto para dar visto bueno",
                                 null);
        VariablesCajaElectronica.indAutoFSEN = "S";
        VariablesCajaElectronica.vFecCierreCajaAux = txtFecha.getText().trim();
        //JMIRANDA 10.03.2010 guarda sec Cajero
        VariablesFondoSencillo.vSecUsuCajeroCierre = txtCodigoCajero.getText().trim();
        VariablesFondoSencillo.vSecMovCajaCierre = VariablesCajaElectronica.vSecMovCaja;
        //VariablesFondoSencillo.vMensajeDevolver = "Nuevo Fondo de Sencillo para Devolver.";
        DlgFormaPagoEntrega dlgFormaPagoEntrega = new DlgFormaPagoEntrega(myParentFrame, "", true);
        dlgFormaPagoEntrega.setVisible(true);
        cargaListaFormasPagoCierre();
    }

    private boolean validaEsOperador() {
        boolean vResultado = false;
        if (FarmaVariables.vNuSecUsu.equalsIgnoreCase(FarmaConstants.ROL_OPERADOR_SISTEMAS))
            vResultado = true;
        else
            vResultado = false;
        return vResultado;
    }

    private void txtCodigoCajero_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtCodigoCajero, e);
    }

    private void txtCaja_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtCaja, e);
    }

    /**
     * CHUANES
     * 23.02.2015
     * LISTA DOCUMENTOS ANULADOS POR CIERRE DE TURNO
     * */
    private void lstDocAnulCierreTurno(){
        VariablesRolesTmp.vTipo_Accion="T";
        DlgDocumentosAnulados lstAnulados=new  DlgDocumentosAnulados(myParentFrame," ",true);
        lstAnulados.setVisible(true);
        
    }

    /**
     * Muestra cantidad de vouchers de inscripcion
     * @author ERIOS
     * @since 22.06.2015
     */
    private void muestraIncripcionTurno() {
        FacadeLealtad facadeLealtad = new FacadeLealtad();
        lblInscripcionTurno.setText(facadeLealtad.getInscripcionTurno(VariablesCajaElectronica.vSecMovCaja));            
    }
}
