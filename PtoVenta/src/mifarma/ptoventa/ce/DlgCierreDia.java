package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgProcesar;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.UtilitySobres;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgCierreDia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      24.08.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class DlgCierreDia extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgCierreDia.class);
    private Frame myParentFrame;
    private FarmaTableModel tableModelFormasPago;
    private FarmaTableModel tableModelCuadraturas;
    private FarmaTableModel tableModelEfectivoRecaudado;
    private FarmaTableModel tableModelEfectivoRendido;

    private ArrayList myArray = new ArrayList();
    private ArrayList arrayDesfasados = new ArrayList();
    private ArrayList myArrayV = new ArrayList();
    private ArrayList arrayVirtuales = new ArrayList();

    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JPanelHeader txtNumeroCaja = new JPanelHeader();
    private JButtonLabel btnDatosGenerales = new JButtonLabel();
    private JLabelWhite lblTipoCambio = new JLabelWhite();
    private JLabelWhite lblMontoTipoCambio = new JLabelWhite();
    private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif();
    private JLabelOrange lblNombre_T = new JLabelOrange();
    private JLabelOrange lblNombre = new JLabelOrange();
    private JPanelTitle pnlFormasPago = new JPanelTitle();
    private JPanelTitle pnlOtrasCuadraturas = new JPanelTitle();
    private JScrollPane scrFormasPago = new JScrollPane();
    private JScrollPane scrCuadraturas = new JScrollPane();
    private JButtonLabel btnFormasPago = new JButtonLabel();
    private JButtonLabel btnOtrasCuadraturas = new JButtonLabel();
    private JPanelTitle pnlTotalCuadraturas = new JPanelTitle();
    private JLabelWhite lblSubTotalCuadra_T = new JLabelWhite();
    private JLabelWhite lblSubTotalCuadra = new JLabelWhite();
    private JPanelTitle pnlTotalCierreTurnos = new JPanelTitle();
    private JLabelWhite lblMontoRegistradoSistema_T = new JLabelWhite();
    private JLabelWhite lblMontoRegistradoSistema = new JLabelWhite();
    private JPanelTitle pnlTotalFormasPago = new JPanelTitle();
    private JLabelWhite lblSubTotalFormaPago = new JLabelWhite();
    private JLabelWhite lblSubTotalFormaPago_T = new JLabelWhite();
    private JButton btnRegistrar = new JButton();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JTable tblFormasPago = new JTable();
    private JTable tblCuadraturas = new JTable();
    private JTable tblEfectivoRecaudado = new JTable();
    private JTable tblEfectivoRendido = new JTable();
    private JLabelWhite lblMontoRegistradoCierreTurno = new JLabelWhite();
    private JLabelWhite lblMontoRegistradoCierreTurno_T = new JLabelWhite();
    private JPanelTitle pnlEfecRendido = new JPanelTitle();
    private JButtonLabel btnEfectivoRendido = new JButtonLabel();
    private JScrollPane scrEfectivoRendido = new JScrollPane();
    private JPanelTitle pnlEfecRecaudado = new JPanelTitle();
    private JButtonLabel btnEfectivoRecaudado = new JButtonLabel();
    private JScrollPane scrEfectivoRecaudado = new JScrollPane();
    private JPanelTitle pnlTotalEfecRecaudado = new JPanelTitle();
    private JLabelWhite lblSubTotalEfecRecaudado = new JLabelWhite();
    private JLabelWhite lblSubTotalEfecRecaudado_T = new JLabelWhite();
    private JPanelTitle pnlTotalEfecRendido = new JPanelTitle();
    private JLabelWhite lblSubTotalEfecRendido = new JLabelWhite();
    private JLabelWhite lblSubTotalEfecRendido_T = new JLabelWhite();
    private JPanelTitle pnlDiferenciaCierreLocal = new JPanelTitle();
    private JLabelWhite lblDiferencia_T = new JLabelWhite();
    private JLabelWhite lblDiferencia = new JLabelWhite();
    private JButtonLabel btnDiaVenta = new JButtonLabel();
    private JPanelTitle pnlObservaciones = new JPanelTitle();
    private JTextArea txtSObs = new JTextArea();
    private JButtonLabel btnObs = new JButtonLabel();
    private JLabelOrange lblMsgVisado_T = new JLabelOrange();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelWhite lblMslReclamoNavsat = new JLabelWhite();
    private JLabelFunction lblF7 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgCierreDia() {
        this(null, "", false);
    }

    public DlgCierreDia(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(769, 580));
        this.setResizable(false);
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Cierre de Dia de Venta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 5, 735, 20));
        txtNumeroCaja.setBounds(new Rectangle(10, 30, 735, 30));
        txtNumeroCaja.setBackground(Color.white);
        btnDatosGenerales.setText("Datos Generales");
        btnDatosGenerales.setBounds(new Rectangle(10, 0, 110, 20));
        btnDatosGenerales.setMnemonic('D');
        btnDatosGenerales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDatosGenerales_actionPerformed(e);
            }
        });
        lblTipoCambio.setText("Tipo Cambio : ");
        lblTipoCambio.setBounds(new Rectangle(500, 0, 80, 20));
        lblMontoTipoCambio.setBounds(new Rectangle(600, 0, 45, 20));
        lblMontoTipoCambio.setHorizontalAlignment(SwingConstants.RIGHT);
        txtFecha.setBounds(new Rectangle(100, 5, 85, 20));
        txtFecha.setDocument(new FarmaLengthText(10));
        txtFecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecha_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecha_keyReleased(e);
            }
        });
        lblNombre_T.setText("Nombre QF: ");
        lblNombre_T.setBounds(new Rectangle(315, 5, 75, 20));
        lblNombre.setBounds(new Rectangle(405, 5, 290, 20));
        pnlFormasPago.setBounds(new Rectangle(10, 65, 360, 20));
        pnlOtrasCuadraturas.setBounds(new Rectangle(385, 65, 360, 20));
        scrFormasPago.setBounds(new Rectangle(10, 85, 360, 115));
        scrCuadraturas.setBounds(new Rectangle(385, 85, 360, 115));
        btnFormasPago.setText("Resumen Formas de Pago   [  ENTER - Ver Detalle ]");
        btnFormasPago.setBounds(new Rectangle(10, 0, 285, 20));
        btnFormasPago.setMnemonic('f');
        btnFormasPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFormasPago_actionPerformed(e);
            }
        });
        btnOtrasCuadraturas.setText("Resumen Otras Cuadraturas   [  ENTER - Ver Detalle ]");
        btnOtrasCuadraturas.setBounds(new Rectangle(10, 0, 315, 20));
        btnOtrasCuadraturas.setMnemonic('O');
        btnOtrasCuadraturas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOtrasCuadraturas_actionPerformed(e);
            }
        });
        pnlTotalCuadraturas.setBounds(new Rectangle(505, 200, 240, 25));
        lblSubTotalCuadra_T.setText("Sub Total : S/.");
        lblSubTotalCuadra_T.setBounds(new Rectangle(10, 5, 80, 15));
        lblSubTotalCuadra.setText("0.00");
        lblSubTotalCuadra.setBounds(new Rectangle(105, 5, 110, 15));
        lblSubTotalCuadra.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlTotalCierreTurnos.setBounds(new Rectangle(10, 225, 735, 40));
        pnlTotalCierreTurnos.setBackground(new Color(43, 141, 39));
        lblMontoRegistradoSistema_T.setText("Monto Total Registrado por Sistema : S/.");
        lblMontoRegistradoSistema_T.setBounds(new Rectangle(350, 20, 230, 15));
        lblMontoRegistradoSistema.setBounds(new Rectangle(620, 20, 90, 15));
        lblMontoRegistradoSistema.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMontoRegistradoSistema.setText("0.00");
        pnlTotalFormasPago.setBounds(new Rectangle(140, 200, 230, 25));
        lblSubTotalFormaPago.setText("0.00");
        lblSubTotalFormaPago.setBounds(new Rectangle(110, 5, 100, 15));
        lblSubTotalFormaPago.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubTotalFormaPago_T.setText("Sub Total : S/.");
        lblSubTotalFormaPago_T.setBounds(new Rectangle(10, 5, 80, 15));
        btnRegistrar.setText("Registrar");
        btnRegistrar.setBounds(new Rectangle(200, 5, 85, 20));
        btnRegistrar.setMnemonic('s');
        btnRegistrar.setFont(new Font("SansSerif", 1, 11));
        btnRegistrar.setDefaultCapable(false);
        btnRegistrar.setFocusable(false);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setRequestFocusEnabled(false);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRegistrar_actionPerformed(e);
            }
        });
        lblF12.setBounds(new Rectangle(435, 520, 95, 20));
        lblF12.setText("[ F12 ] Imprimir");
        lblF2.setBounds(new Rectangle(10, 520, 130, 20));
        lblF2.setText("[ F2] Efectivo Rendido");
        lblF9.setBounds(new Rectangle(335, 520, 90, 20));
        lblF9.setText("[ F9] VºBº Q.F.");
        lblEsc.setBounds(new Rectangle(660, 520, 85, 20));
        lblEsc.setText("[ ESC ] Salir");
        tblFormasPago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblFormasPago_keyPressed(e);
            }
        });
        tblCuadraturas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCuadraturas_keyPressed(e);
            }
        });
        tblEfectivoRecaudado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblEfectivoRecaudado_keyPressed(e);
            }
        });
        tblEfectivoRendido.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblEfectivoRendido_keyPressed(e);
            }
        });
        lblMontoRegistradoCierreTurno.setText("0.00");
        lblMontoRegistradoCierreTurno.setBounds(new Rectangle(625, 5, 85, 15));
        lblMontoRegistradoCierreTurno.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMontoRegistradoCierreTurno_T.setText("Monto Total Registrado por Cierres Turno : S/.");
        lblMontoRegistradoCierreTurno_T.setBounds(new Rectangle(350, 5, 265, 15));
        pnlEfecRendido.setBounds(new Rectangle(385, 270, 360, 20));
        btnEfectivoRendido.setText("Resumen Efectivo Rendido   [  ENTER - Ver Detalle ]");
        btnEfectivoRendido.setBounds(new Rectangle(10, 0, 310, 20));
        btnEfectivoRendido.setMnemonic('R');
        btnEfectivoRendido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEfectivoRendido_actionPerformed(e);
            }
        });
        scrEfectivoRendido.setBounds(new Rectangle(385, 290, 360, 115));
        pnlEfecRecaudado.setBounds(new Rectangle(10, 270, 360, 20));
        btnEfectivoRecaudado.setText("Resumen Efectivo Recaudado");
        btnEfectivoRecaudado.setBounds(new Rectangle(10, 0, 190, 20));
        btnEfectivoRecaudado.setMnemonic('E');
        btnEfectivoRecaudado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEfectivoRecaudado_actionPerformed(e);
            }
        });
        scrEfectivoRecaudado.setBounds(new Rectangle(10, 290, 360, 115));
        pnlTotalEfecRecaudado.setBounds(new Rectangle(140, 405, 230, 25));
        lblSubTotalEfecRecaudado.setText("0.00");
        lblSubTotalEfecRecaudado.setBounds(new Rectangle(100, 5, 105, 15));
        lblSubTotalEfecRecaudado.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubTotalEfecRecaudado_T.setText("Sub Total : S/.");
        lblSubTotalEfecRecaudado_T.setBounds(new Rectangle(10, 5, 80, 15));
        pnlTotalEfecRendido.setBounds(new Rectangle(500, 405, 245, 25));
        lblSubTotalEfecRendido.setText("0.00");
        lblSubTotalEfecRendido.setBounds(new Rectangle(100, 5, 115, 15));
        lblSubTotalEfecRendido.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubTotalEfecRendido_T.setText("Sub Total : S/.");
        lblSubTotalEfecRendido_T.setBounds(new Rectangle(10, 5, 80, 15));
        pnlDiferenciaCierreLocal.setBounds(new Rectangle(10, 430, 735, 25));
        pnlDiferenciaCierreLocal.setBackground(new Color(43, 141, 39));
        lblDiferencia_T.setText("Diferencia : S/.");
        lblDiferencia_T.setBounds(new Rectangle(570, 5, 85, 15));
        lblDiferencia.setText("0.00");
        lblDiferencia.setBounds(new Rectangle(665, 5, 65, 15));
        lblDiferencia.setHorizontalAlignment(SwingConstants.RIGHT);
        btnDiaVenta.setText("Dia de Venta :");
        btnDiaVenta.setBounds(new Rectangle(10, 5, 85, 20));
        btnDiaVenta.setForeground(new Color(255, 130, 14));
        btnDiaVenta.setMnemonic('d');
        pnlObservaciones.setBounds(new Rectangle(10, 460, 735, 55));
        pnlObservaciones.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        txtSObs.setDocument(new FarmaLengthText(290));
        txtSObs.setRows(2);
        txtSObs.setSelectionEnd(200);
        txtSObs.setSelectionStart(200);
        txtSObs.setTabSize(20);
        txtSObs.setLineWrap(true);
        txtSObs.setWrapStyleWord(true);
        txtSObs.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSObs_keyPressed(e);
            }
        });
        btnObs.setText("Observaciones :");
        btnObs.setMnemonic('b');
        btnObs.setBounds(new Rectangle(5, 15, 95, 25));
        btnObs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObs_actionPerformed(e);
            }
        });
        lblMsgVisado_T.setBounds(new Rectangle(10, 0, 550, 25));
        lblMsgVisado_T.setFont(new Font("SansSerif", 1, 14));
        lblMsgVisado_T.setForeground(Color.white);
        lblF3.setBounds(new Rectangle(150, 520, 175, 20));
        lblF3.setText("[ F3] Ingresar Comprobantes");
        jScrollPane1.setBounds(new Rectangle(110, 5, 620, 45));
        lblF8.setBounds(new Rectangle(540, 520, 110, 20));
        lblF8.setText("[ F8] VºBº Contable");
        lblF8.setVisible(false);
        lblMslReclamoNavsat.setText("Existen comprobantes por reclamar");
        lblMslReclamoNavsat.setBounds(new Rectangle(5, 10, 210, 20));
        lblMslReclamoNavsat.setFont(new Font("SansSerif", 1, 12));
        lblF7.setBounds(new Rectangle(220, 10, 120, 20));
        lblF7.setText("[ F7 ] Ver Reclamos");
        lblMslReclamoNavsat.setVisible(false);
        lblF7.setVisible(false);
        scrEfectivoRendido.getViewport();
        scrEfectivoRecaudado.getViewport();
        jScrollPane1.getViewport().add(txtSObs, null);
        pnlObservaciones.add(jScrollPane1, null);
        pnlObservaciones.add(btnObs, null);
        pnlDiferenciaCierreLocal.add(lblMsgVisado_T, null);
        pnlDiferenciaCierreLocal.add(lblDiferencia_T, null);
        pnlDiferenciaCierreLocal.add(lblDiferencia, null);
        pnlTotalEfecRecaudado.add(lblSubTotalEfecRecaudado, null);
        pnlTotalEfecRecaudado.add(lblSubTotalEfecRecaudado_T, null);
        pnlTotalEfecRendido.add(lblSubTotalEfecRendido, null);
        pnlTotalEfecRendido.add(lblSubTotalEfecRendido_T, null);
        pnlTotalFormasPago.add(lblSubTotalFormaPago, null);
        pnlTotalFormasPago.add(lblSubTotalFormaPago_T, null);
        txtNumeroCaja.add(btnDiaVenta, null);
        txtNumeroCaja.add(btnRegistrar, null);
        txtNumeroCaja.add(lblNombre, null);
        txtNumeroCaja.add(lblNombre_T, null);
        txtNumeroCaja.add(txtFecha, null);
        jPanelTitle1.add(lblMontoTipoCambio, null);
        jPanelTitle1.add(lblTipoCambio, null);
        jPanelTitle1.add(btnDatosGenerales, null);
        pnlTotalCuadraturas.add(lblSubTotalCuadra, null);
        pnlTotalCuadraturas.add(lblSubTotalCuadra_T, null);
        pnlTotalCierreTurnos.add(lblMslReclamoNavsat, null);
        pnlTotalCierreTurnos.add(lblMontoRegistradoCierreTurno_T, null);
        pnlTotalCierreTurnos.add(lblMontoRegistradoCierreTurno, null);
        pnlTotalCierreTurnos.add(lblMontoRegistradoSistema, null);
        pnlTotalCierreTurnos.add(lblMontoRegistradoSistema_T, null);
        pnlTotalCierreTurnos.add(lblF7, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(pnlObservaciones, null);
        jContentPane.add(pnlDiferenciaCierreLocal, null);
        jContentPane.add(pnlTotalEfecRendido, null);
        jContentPane.add(pnlTotalEfecRecaudado, null);
        scrEfectivoRecaudado.getViewport().add(tblEfectivoRecaudado, null);
        jContentPane.add(scrEfectivoRecaudado, null);
        pnlEfecRecaudado.add(btnEfectivoRecaudado, null);
        jContentPane.add(pnlEfecRecaudado, null);
        scrEfectivoRendido.getViewport().add(tblEfectivoRendido, null);
        jContentPane.add(scrEfectivoRendido, null);
        pnlEfecRendido.add(btnEfectivoRendido, null);
        jContentPane.add(pnlEfecRendido, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(pnlTotalFormasPago, null);
        jContentPane.add(pnlTotalCierreTurnos, null);
        jContentPane.add(pnlTotalCuadraturas, null);
        scrCuadraturas.getViewport().add(tblCuadraturas, null);
        jContentPane.add(scrCuadraturas, null);
        scrFormasPago.getViewport().add(tblFormasPago, null);
        jContentPane.add(scrFormasPago, null);
        pnlOtrasCuadraturas.add(btnOtrasCuadraturas, null);
        jContentPane.add(pnlOtrasCuadraturas, null);
        pnlFormasPago.add(btnFormasPago, null);
        jContentPane.add(pnlFormasPago, null);
        jContentPane.add(txtNumeroCaja, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(lblF9, null);
        this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        //ERIOS 2.2.8 Problema de cursores abiertos
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();

        initTableFormasPago();
        initTableCuadraturas();
        initTableEfectivoRecaudado();
        initTableEfectivoRendido();
        habilitaOpciones(false);
        FarmaUtility.moveFocus(txtFecha);
        evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CIERRE_DIA_QF); //iniciamos con msg en blanco
        VariablesCajaElectronica.vObsCierreDia = "";
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableFormasPago() {
        tableModelFormasPago =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaFormasPago, ConstantsCajaElectronica.defaultListaFormasPago,
                                    0);
        FarmaUtility.initSimpleList(tblFormasPago, tableModelFormasPago,
                                    ConstantsCajaElectronica.columsListaFormasPago);
    }

    private void cargaFormasPago() {
        try {
            DBCajaElectronica.cargaConsolidadoFormaPagoCierreDia(tableModelFormasPago,
                                                                 VariablesCajaElectronica.vFechaCierreDia);
            if (tblFormasPago.getRowCount() > 0)
                FarmaUtility.ordenar(tblFormasPago, tableModelFormasPago, 4, FarmaConstants.ORDEN_ASCENDENTE);
            lblSubTotalFormaPago.setText("" + FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblFormasPago, 3)));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al cargar consolidado de formas de pago entrega de cierre de dia.\n" +
                    sql, null);
        }
    }

    private void initTableCuadraturas() {
        tableModelCuadraturas =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaCuadraturasCierreDia, ConstantsCajaElectronica.defaultValuesListaCuadraturasCierreDia,
                                    0);
        FarmaUtility.initSimpleList(tblCuadraturas, tableModelCuadraturas,
                                    ConstantsCajaElectronica.columnsListaCuadraturasCierreDia);
    }

    private void cargaCuadraturas() {
        try {
            DBCajaElectronica.cargaConsolidadoCuadraturaCierreDia(tableModelCuadraturas,
                                                                  VariablesCajaElectronica.vFechaCierreDia);
            if (tblCuadraturas.getRowCount() > 0)
                FarmaUtility.ordenar(tblCuadraturas, tableModelCuadraturas, 0, FarmaConstants.ORDEN_ASCENDENTE);
            lblSubTotalCuadra.setText("" + FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblCuadraturas, 2)));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar consolidado de cuadraturas de cierre de dia.\n" +
                    sql, null);
        }
    }

    private void initTableEfectivoRecaudado() {
        tableModelEfectivoRecaudado =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaEfectivoRecaudado, ConstantsCajaElectronica.defaultListaEfectivoRecaudado,
                                    0);
        FarmaUtility.initSimpleList(tblEfectivoRecaudado, tableModelEfectivoRecaudado,
                                    ConstantsCajaElectronica.columsListaEfectivoRecaudado);
    }

    private void cargaEfectivoRecaudado() {
        try {
            DBCajaElectronica.cargaConsolidadoEfecRecaudadoCierre(tableModelEfectivoRecaudado,
                                                                  VariablesCajaElectronica.vFechaCierreDia);
            if (tblEfectivoRecaudado.getRowCount() > 0)
                FarmaUtility.ordenar(tblEfectivoRecaudado, tableModelEfectivoRecaudado, 4,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            lblSubTotalEfecRecaudado.setText("" +
                                             FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblEfectivoRecaudado,
                                                                                                  3)));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar consolidado de efectivo recaudado de cierre de dia.\n" +
                    sql, null);
        }
    }

    private void initTableEfectivoRendido() {
        tableModelEfectivoRendido =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaEfectivoRendido, ConstantsCajaElectronica.defaultListaEfectivoRendido,
                                    0);
        FarmaUtility.initSimpleList(tblEfectivoRendido, tableModelEfectivoRendido,
                                    ConstantsCajaElectronica.columsListaEfectivoRendido);
    }

    private void cargaEfectivoRendido() {
        try {
            log.debug(" VariablesCajaElectronica.vFechaCierreDia : " + VariablesCajaElectronica.vFechaCierreDia);
            //DBCajaElectronica.cargaConsolidadoEfecRendidoCierre(tableModelEfectivoRendido, VariablesCajaElectronica.vFechaCierreDia);
            DBCajaElectronica.cargaConsolidadoEfecRendidoCierre_02(tableModelEfectivoRendido,
                                                                   VariablesCajaElectronica.vFechaCierreDia); // ASOSA, 25.04.2010
            if (tblEfectivoRendido.getRowCount() > 0)
                FarmaUtility.ordenar(tblEfectivoRendido, tableModelEfectivoRendido, 0,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            lblSubTotalEfecRendido.setText("" +
                                           FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblEfectivoRendido,
                                                                                                2)));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar consolidado de efectivo rendido de cierre de dia.\n" +
                    sql, null);
        }
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
            FarmaUtility.centrarVentana(this);
            VariablesCajaElectronica.vIsVentanaCierreDiaOpen = FarmaConstants.INDICADOR_S;
            if (FarmaVariables.vEconoFar_Matriz) {
                lblF8.setVisible(true);
            }
            if (!VariablesCajaElectronica.vRegistrarCierreDia) {
                VariablesCajaElectronica.vIndCompValidos =
                        UtilityCajaElectronica.obtieneIndicadorComprobatesValidosDia(this,
                                                                                     VariablesCajaElectronica.vFechaCierreDia);
                cargaDiaVenta();
            } else {
                VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_N;
                verificaInfoCierreDia(false, true);
                FarmaUtility.moveFocus(txtFecha);
            }
        }
    }

    private void btnDatosGenerales_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecha);
    }

    private void btnRegistrar_actionPerformed(ActionEvent e) {
        if (!validaFechaCierreDia())
            return;
        if (!existenCajasAperturadasDiaVenta())
            return;
        if (!validaCajasCerradasDiaVenta())
            return;
        if (registraCierreDiaVenta())
            cargaInfoCierreDia();
    }

    private void btnFormasPago_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblFormasPago);
    }

    private void btnOtrasCuadraturas_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblCuadraturas);
    }

    private void btnEfectivoRecaudado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblEfectivoRecaudado);
    }

    private void btnEfectivoRendido_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblEfectivoRendido);
    }

    private void txtSObs_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            e.consume();
        else
            chkKeyPressed(e);
    }

    private void btnObs_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSObs);
        txtSObs.selectAll();
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (VariablesCajaElectronica.vIndFuncionesHabilitadas) {
            if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
                if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                    FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción",
                                             null);
                } else {
                    if (FarmaVariables.vEconoFar_Matriz) {
                        FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblFormasPago);
                        return;
                    }
                    mostrarEfectivoRendido();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                ingresarRangoComprobantes(false);
                if (FarmaVariables.vAceptar) {
                    try {
                        for (int i = 0; i < VariablesCajaElectronica.vRangoCompIngresadosCierreDia.size(); i++) {
                            guardaInformacionRangoComprobantes(i);
                            if (VariablesCajaElectronica.vIndRangoGrabado.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                                DBCajaElectronica.insertaRangoCompCierreDia(VariablesCajaElectronica.vFechaCierreDia);
                        }
                        VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_S;
                        FarmaUtility.aceptarTransaccion();
                        UtilityCajaElectronica.actualizaIndicadorCompValidosCD(this,
                                                                               VariablesCajaElectronica.vFechaCierreDia,
                                                                               VariablesCajaElectronica.vIndCompValidos);
                    } catch (SQLException sql) {
                        FarmaUtility.liberarTransaccion();
                        log.error("", sql);
                        FarmaUtility.showMessage(this, "Error al registrar los rangos de comprobantes." + sql,
                                                 tblCuadraturas);
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_F9) {
                if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                    FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción",
                                             null);
                } else {
                    if (FarmaVariables.vEconoFar_Matriz) {
                        FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblFormasPago);
                        return;
                    }
                    cambiaVBCierreDia();
                }
            } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
                imprimeCierreDia();
            } else if (e.getKeyCode() == KeyEvent.VK_F8) {
                if (lblF8.isVisible()) {
                    if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                        FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción",
                                                 null);
                    } else {
                        cambiaVBContable();
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                if (VariablesCajaElectronica.vExistenCompReclamosNavsat)
                    verReclamosProveedor();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    private void txtFecha_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnRegistrar.doClick();
        }
        chkKeyPressed(e);
    }

    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }

    private void tblEfectivoRendido_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            cargaDetalleEfectivoRendido();
        } else
            chkKeyPressed(e);
    }

    private void tblFormasPago_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            cargaDetalleFormasPago();
        } else
            chkKeyPressed(e);
    }

    private void tblCuadraturas_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            cargaDetalleOtrasCuadraturas();
        } else
            chkKeyPressed(e);
    }

    private void tblEfectivoRecaudado_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cerrarVentana(boolean pAceptar) {
        limpiaValoresCierreDia();
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void mostrarEfectivoRendido() {
        DlgEfectivoRendido dlgEfectivoRendido = new DlgEfectivoRendido(myParentFrame, "", true);
        dlgEfectivoRendido.setVisible(true);
        cargaEfectivoRendido();
        evaluaTotales();
    }

    private void cargaInfoCierreDia() {
        //ERIOS 30.09.2013 Se suma el monto de recaudaciones
        String montoTotalSistema;
        cargaFormasPago();
        cargaCuadraturas();
        cargaEfectivoRecaudado();
        cargaEfectivoRendido();
        montoTotalSistema = obtieneMontoTotalSistemaCierreDia();

        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        String montoTotalRecaudacion;
        try {
            montoTotalRecaudacion =
                    facadeRecaudacion.getMontoTotalRecaudacionByCierreDia(VariablesCajaElectronica.vFechaCierreDia);
        } catch (Exception e) {
            FarmaUtility.showMessage(this, "" + e.getMessage(), null);
            return;
        }
        montoTotalSistema =
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(montoTotalSistema.trim()) + FarmaUtility.getDecimalNumber(montoTotalRecaudacion.trim()));
        lblMontoRegistradoSistema.setText(montoTotalSistema);
        evaluaTotales();
        habilitaOpciones(true);
        evaluaMsjReclamosNavsat(VariablesCajaElectronica.vFechaCierreDia);
    }

    private void cargaDiaVenta() {
        txtFecha.setText(VariablesCajaElectronica.vFechaCierreDia);
        txtFecha.setEnabled(false);
        lblMontoTipoCambio.setText(VariablesCajaElectronica.vTipoCambio);
        lblNombre.setText(VariablesCajaElectronica.vNombreUsuarioLogueado);
        btnRegistrar.setEnabled(false);
        cargaInfoCierreDia();
        if (VariablesCajaElectronica.vIndVBCierreDia.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            verificaInfoCierreDia(true, false);
        else
            verificaInfoCierreDia(true, true);
        evaluaMsgVBCierreDia(true);
        FarmaUtility.moveFocusJTable(tblEfectivoRendido);
    }

    private boolean registraCierreDiaVenta() {
        VariablesCajaElectronica.vFechaCierreDia = txtFecha.getText().trim();
        //VariablesCajaElectronica.vIndCompValidos = UtilityCajaElectronica.obtieneIndicadorComprobatesValidosDia(this, VariablesCajaElectronica.vFechaCierreDia);
        //if( !VariablesCajaElectronica.vIndCompValidos.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){
        if (VariablesCajaElectronica.vFechaCierreDia.trim().equalsIgnoreCase("") ||
            VariablesCajaElectronica.vFechaCierreDia.length() == 0) {
            FarmaUtility.showMessage(this, "Debe registrar un cierre de dia para poder ingresar los comprobantes.",
                                     tblEfectivoRendido);
            return false;
        }
        ingresarRangoComprobantes(true);
        if (VariablesCajaElectronica.vIndCompValidos.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            VariablesCajaElectronica.vFechaCierreDia = "";
            return false;
        }
        obtieneTipoCambio(VariablesCajaElectronica.vFechaCierreDia);
        VariablesCajaElectronica.vTipoCambio = FarmaUtility.formatNumber(FarmaVariables.vTipCambio);
        lblMontoTipoCambio.setText(VariablesCajaElectronica.vTipoCambio);
        VariablesCajaElectronica.vNombreUsuarioLogueado =
                FarmaVariables.vNomUsu + " " + FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu;
        lblNombre.setText(VariablesCajaElectronica.vNombreUsuarioLogueado);
        VariablesCajaElectronica.vSecUsuLocalCierreDia = FarmaVariables.vNuSecUsu;
        txtFecha.setEnabled(false);
        btnRegistrar.setEnabled(false);
        try {
            DBCajaElectronica.registraCierreDiaVenta(VariablesCajaElectronica.vFechaCierreDia,
                                                     VariablesCajaElectronica.vSecUsuLocalCierreDia,
                                                     VariablesCajaElectronica.vTipoCambio);
            //NO SERA AUTOMATICO
            //PORQUE EL DINERO PARA LA COTIZACION SALE DEL CAJERO DEL TURNO
            //NO SALE DE LA CAJA CHICA DEL DIA
            //DUBILLUZ 06.05.2014
            //DBCajaElectronica.insertaCotizacionCompetenciaAutomatico(VariablesCajaElectronica.vFechaCierreDia);

            //--@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //RHERRERA 16.09.2014
            // envia el cierre de dia al local Farma
            if (VariablesPtoVenta.vIndTico.equals("S")) { //RHERRERA. ENVIA CIERRE DE DÍA AL RAC
                UtilitySobres.insertCierreDiaMarket(VariablesCajaElectronica.vFechaCierreDia,
                                                    VariablesCajaElectronica.vSecUsuLocalCierreDia);
            }
            //--@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se registró correctamente el cierre de dia.", tblEfectivoRendido);
            for (int i = 0; i < VariablesCajaElectronica.vRangoCompIngresadosCierreDia.size(); i++) {
                guardaInformacionRangoComprobantes(i);
                if (VariablesCajaElectronica.vIndRangoGrabado.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                    DBCajaElectronica.insertaRangoCompCierreDia(VariablesCajaElectronica.vFechaCierreDia);
            }
            VariablesCajaElectronica.vIndCompValidos = FarmaConstants.INDICADOR_S;
            FarmaUtility.aceptarTransaccion();
            UtilityCajaElectronica.actualizaIndicadorCompValidosCD(this, VariablesCajaElectronica.vFechaCierreDia,
                                                                   VariablesCajaElectronica.vIndCompValidos);
            /*UtilityCajaElectronica.actualizaInfoComprobantesCierreDia(this, VariablesCajaElectronica.vFechaCierreDia,
                                                                VariablesCajaElectronica.vBoletaMinDia,
                                                                VariablesCajaElectronica.vBoletaMaxDia,
                                                                VariablesCajaElectronica.vFacturaMinDia,
                                                                VariablesCajaElectronica.vFacturaMaxDia,
                                                                FarmaConstants.INDICADOR_S);*/
            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al registrar el Cierre de Dia de Venta.\nPor favor, comunicarse con el area de Sistemas.\n" +
                    sql, txtFecha);
            return false;
        }
    }

    private void obtieneTipoCambio(String pFechaTipoCambio) {
        try {
            (new FacadeRecaudacion()).obtenerTipoCambio();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener Tipo de Cambio del Dia . \n " + sql.getMessage(), null);
        }
    }

    private boolean validaFechaCierreDia() {
        String diaVenta = txtFecha.getText().trim();
        int cont = 1;
        if (!FarmaUtility.isFechaValida(this, diaVenta, "Ingrese una Fecha correcta en el Dia de venta")) {
            FarmaUtility.moveFocus(txtFecha);
            return false;
        }
        /*if( diaVenta.length() == 0 || !FarmaUtility.validaFecha(diaVenta,"dd/MM/yyyy") )
    {
      FarmaUtility.showMessage(this,"Ingrese una Fecha correcta en el Dia de venta" , txtFecha);
      return false;
    }*/
        try {
            cont = DBCajaElectronica.validaFechaCierreDiaRegistrar(diaVenta);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al validar fecha de cierre de dia de venta.\n" +
                    sql, txtFecha);
            return false;
        }
        if (cont > 0) {
            FarmaUtility.showMessage(this, "Ya existe un Cierre de Dia para la fecha ingresada.", txtFecha);
            return false;
        }
        return true;
    }

    private boolean existenCajasAperturadasDiaVenta() {
        String diaVenta = txtFecha.getText().trim();
        int cont = 0;
        try {
            cont = DBCajaElectronica.obtieneCantCajasAperturadasDia(diaVenta);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al validar cajas aperturadas.\n" +
                    sql, txtFecha);
            return false;
        }
        if (cont <= 0) {
            FarmaUtility.showMessage(this, "No existen cajas aperturadas para el dia de venta. Verifique por favor!!!",
                                     txtFecha);
            return false;
        }
        return true;
    }

    private boolean validaCajasCerradasDiaVenta() {
        String diaVenta = txtFecha.getText().trim();
        int cont = 1;
        try {
            cont = DBCajaElectronica.validaCajasConVBCajeroCierreDia(diaVenta);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al validar cajas cerradas con VB de Cajero.\n" +
                    sql, txtFecha);
            return false;
        }
        if (cont != 0) {
            //FarmaUtility.showMessage(this,"Alguna(s) Caja(s) no cuentan con VB de Cajero en\nsu cierre de turno. Verifique por favor!!!", txtFecha);
            //ERIOS 2.2.8 Muestra cajas aperturadas.
            muestraCajasAperturadas("Alguna(s) Caja(s) no cuentan con VB de Cajero en<br>su cierre de turno. Verifique por favor!!!");
            return false;
        }
        return true;
    }

    private void evaluaMsgVBCierreDia(boolean pMuestraVentanaMsg) {
        if (VariablesCajaElectronica.vIndVBContable.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (pMuestraVentanaMsg)
                FarmaUtility.showMessage(this, "El dia de Venta ya cuenta con VB Contable.", tblEfectivoRendido);
            evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_CONTABLE); //existe VB de contabilidad para el dia de venta

        } else if (VariablesCajaElectronica.vIndVBCierreDia.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (pMuestraVentanaMsg)
                FarmaUtility.showMessage(this, "El dia de Venta ya cuenta con VB de Cierre de Dia.",
                                         tblEfectivoRendido);
            evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_CIERRE_DIA_QF); //existe VB de qf para el dia de venta

        } else {
            evaluaMsgEtiquetaVB(ConstantsCajaElectronica.SIN_VB_CIERRE_DIA_QF); //no existe VB de qf para el dia de venta
        }
    }

    private String obtieneMontoTotalSistemaCierreDia() {
        String monTotalSistema = new String();
        try {
            monTotalSistema =
                    DBCajaElectronica.obtenerMontoTotalSistemaCierreDia(VariablesCajaElectronica.vFechaCierreDia);
        } catch (SQLException sql) {
            log.error("", sql);
            monTotalSistema = "0.00";
            FarmaUtility.showMessage(this, "Error al obtener Monto Total del Sistema. \n " + sql.getMessage(), null);
        }
        return monTotalSistema;
    }

    private void evaluaTotales() {
        double montoTotalSistema = 0.00;
        double montoTotalCierreTurnos = 0.00;
        double montoTotalEfecRecaudado = 0.00;
        double montoTotalEfecRendido = 0.00;
        double montoDiferenciaCD = 0.00;
        double montoDiferenciaSistCierreTurno_CD = 0.00;
        //suma de total cierre de caja
        montoTotalCierreTurnos =
                FarmaUtility.getDecimalNumber(lblSubTotalFormaPago.getText().trim()) + FarmaUtility.getDecimalNumber(lblSubTotalCuadra.getText().trim());
        montoTotalCierreTurnos = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(montoTotalCierreTurnos));
        log.debug("montoTotalCierreTurnos : " + montoTotalCierreTurnos);
        //monto total de sistemas
        montoTotalSistema = FarmaUtility.getDecimalNumber(lblMontoRegistradoSistema.getText().trim());
        log.debug("montoTotalSistema : " + montoTotalSistema);
        //suma de total efectivo recaudado
        montoTotalEfecRecaudado = FarmaUtility.getDecimalNumber(lblSubTotalEfecRecaudado.getText().trim());
        log.debug("montoTotalEfecRecaudado : " + montoTotalEfecRecaudado);
        //suma de total efectivo rendido
        montoTotalEfecRendido = FarmaUtility.getDecimalNumber(lblSubTotalEfecRendido.getText().trim());
        log.debug("montoTotalEfecRendido : " + montoTotalEfecRendido);

        //faltante o sobrante entre sistema y Cierre de trueno en Cierre de Dia
        montoDiferenciaSistCierreTurno_CD = montoTotalSistema - montoTotalCierreTurnos;
        montoDiferenciaSistCierreTurno_CD =
                FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(montoDiferenciaSistCierreTurno_CD));
        log.debug("montoDiferenciaSistCierreTurno_CD : " + montoDiferenciaSistCierreTurno_CD);
        VariablesCajaElectronica.vDiferenciaSistemaCierreTurno_CD = montoDiferenciaSistCierreTurno_CD;

        //faltante o sobrante Cierre de Dia
        montoDiferenciaCD = montoTotalEfecRecaudado - montoTotalEfecRendido;
        //if( montoFaltante < 0 ) montoFaltante = 0.00;
        montoDiferenciaCD = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(montoDiferenciaCD));
        log.debug("montoDiferenciaCD : " + montoDiferenciaCD);
        VariablesCajaElectronica.vDiferenciaCierreDia = montoDiferenciaCD;
        lblMontoRegistradoCierreTurno.setText("" + FarmaUtility.formatNumber(montoTotalCierreTurnos));
        lblDiferencia.setText("" + FarmaUtility.formatNumber(montoDiferenciaCD));
    }

    private void cambiaVBCierreDia() {
        evaluaExistenciaCajasSinVBQF(VariablesCajaElectronica.vFechaCierreDia);
        if (VariablesCajaElectronica.vExistenCajasSinVBQFDiaVenta) {
            FarmaUtility.liberarTransaccion();
            //FarmaUtility.showMessage(this,"Alguna(s) Caja(s) no cuentan con VB de QF en su cierre de turno.\nNo es posible realizar esta operación. Verifique por favor!!!", tblEfectivoRendido);
            //ERIOS 2.2.8 Muestra cajas aperturadas
            muestraCajasAperturadas("Alguna(s) Caja(s) no cuentan con VB de QF en su cierre de turno.<br>No es posible realizar esta operación. Verifique por favor!!!");
            return;
        }
        //JMIRANDA 15.12.09 SE VERIFICASI HAY GUIAS PENDIENTES POR CONFIRMAR (TRANSFERENCIA DE LOCAL)
        if (UtilityCaja.validaGuiasXConfirmarLocal()) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No puede dar VoBo del día porque existen transferencias de otros " + "locales pendientes de aceptar.\n" +
                    "Debe aceptar estas transferencias para poder dar VoBo del día.\n", tblEfectivoRendido);
            return;
        }
        //JCORTEZ 27.10.09 Se verifica si hay guias pendientes por confirmar (devolucion,etc)
        if (UtilityCaja.validaGuiasPendAlmc()) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     " No puede dar VoBo del día porque existen devoluciones para almacén pendientes de confirmar.\n Debe confirmar estas devoluciones para poder dar VoBo del día.",
                                     tblEfectivoRendido);
            return;
        }
        if (!UtilityCajaElectronica.obtieneIndicadorVBCierreDia_ForUpdate(VariablesCajaElectronica.vFechaCierreDia)) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No se pudo obtener el Indicador actual de VB de Cierre de Dia.\nPor favor, comuniquese con el area de Sistemas.\n",
                                     tblEfectivoRendido);
            return;
        }
        if (VariablesCajaElectronica.vIndVBContable.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            FarmaUtility.showMessage(this,
                                     "El cierre de dia ya cuenta con VB de Contabilidad.\n No es posible realizar la operacion",
                                     tblEfectivoRendido);
            return;
        }
        if (VariablesCajaElectronica.vIndVBCierreDia.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            //UtilityCajaElectronica.obtieneComprobantesMinMaxIngresoDia(this, VariablesCajaElectronica.vFechaCierreDia);
            if (!validaDsctoPersonal()) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "El monto del efectivo recaudado y efectivo rendido Descuento a Personal\ndebe ser el mismo. No es posible realizar esta operación. Verifique, por favor!!!",
                                         tblEfectivoRendido);
                return;
            }
            //guardaInformacionComprobantes();
            if (!validaRangoComprobantesIngresados()) {
                UtilityCajaElectronica.actualizaIndicadorCompValidosCD(this, VariablesCajaElectronica.vFechaCierreDia,
                                                                       FarmaConstants.INDICADOR_N);
                FarmaUtility.showMessage(this,
                                         "Los rangos de comprobantes ingresados no coinciden con\nlos registrados en el sistema. Verifique por favor!!!",
                                         tblEfectivoRendido);
                return;
            }
            if (VariablesCajaElectronica.vDiferenciaSistemaCierreTurno_CD > 0) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Existe diferencia entre total sistema y total cierre.\nDebe otorgar VB de QF a todas las cajas para\ncrear el deficit asumido de cajero automaticamente.",
                                         tblEfectivoRendido);
                return;
            }

            // Se validan comprobantes desfasados
            // 27.11.2008 DUBILLUZ
            if (UtilityCaja.validaCompDesfase(VariablesCajaElectronica.vFechaCierreDia.trim())) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Atención:\nExisten comprobantes (boletas y/o facturas) que se encuentran pendientes de ingreso al sistema.\nPara poder dar VoBo de QF primero deberá regularizarlos.",
                                         tblEfectivoRendido);
                return;
            }

            if (UtilityCaja.validaDelPendSinReg(VariablesCajaElectronica.vFechaCierreDia.trim())) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Atención:\n" +
                        "Existen delivery pendientes registrados en cierres de días anteriores que aún están pendientes de regularización.\n" +
                        "Para poder dar VoBo de QF primero deberá regularizarlos.", tblEfectivoRendido);
                return;
            }

            if (UtilityCaja.validaAnulPedSinReg(VariablesCajaElectronica.vFechaCierreDia.trim())) {
                FarmaUtility.liberarTransaccion();
                /*FarmaUtility.showMessage(this,"Atención:\n" +
          "Existen comprobantes pendientes de anulación declarados en cierres de días anteriores\n" +
          "y que aún no han sido regularizados.\n"+
          "Para poder dar VoBo de QF primero deberá regularizarlos.", tblEfectivoRendido);
          */
                DlgAlertaCierreDia dlgCierreDia = new DlgAlertaCierreDia(myParentFrame, "", true);
                dlgCierreDia.setVisible(true);
                return;
            }

            if (UtilityCaja.validaRegPedManual(VariablesCajaElectronica.vFechaCierreDia.trim())) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Atención:\n" +
                        "Existen comprobantes manuales registrados en cierres de días anteriores que aún están pendientes de regularización.\n" +
                        "Para poder dar VoBo de QF primero deberá regularizarlos.", tblEfectivoRendido);
                return;
            }

            // 14.04.2010 JQUISPE VALIDACION PARA LA RENDICION DE VISA MANUAL
            /*if(!UtilityCajaElectronica.verificarVisaManual()){
              FarmaUtility.liberarTransaccion();
              FarmaUtility.showMessage(this,"Atención:\n" +
              "No puede dar V°B° Químico porque existen diferencias en \n la Forma de Pago Visa Manual declarados por el cajero\n" +
              "y el ingresado por el Químico.", tblEfectivoRendido);
              return;
      }*/


            if (VariablesCajaElectronica.vDiferenciaCierreDia > 0) {
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Existe diferencia entre Efectivo Recaudado y Efectivo Rendido.\nEstá seguro de otorgar el VB de Cierre de Dia y asumir el deficit?")) {
                    VariablesCajaElectronica.vObsCierreDia = txtSObs.getText().trim().toUpperCase();
                    if (actualizaIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia,
                                                      FarmaConstants.INDICADOR_S,
                                                      VariablesCajaElectronica.vObsCierreDia)) {
                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this, "Se otorgó correctamente el VB de Cierre de Dia",
                                                 tblEfectivoRendido);
                        evaluaDeficitAsumidoQF(VariablesCajaElectronica.vFechaCierreDia,
                                               VariablesCajaElectronica.vDiferenciaCierreDia); //si diferencia > 0, se registra el deficit asuimido
                        actualizaInfoHistVBCierreDia_Autonomous(VariablesCajaElectronica.vFechaCierreDia, "");
                        VariablesCajaElectronica.vIndVBCierreDia = FarmaConstants.INDICADOR_S;
                        //cargaInfoCierreDia();
                        cargaEfectivoRendido();
                        evaluaTotales();
                        verificaInfoCierreDia(true, false);
                        evaluaMsgVBCierreDia(false);
                    }
                } else
                    FarmaUtility.liberarTransaccion();
            } else {
                if (validaMontoRecRen()) { //ini ASOSA, 25/04/2010
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Esta seguro de otorgar el VB de Cierre de Dia?")) {
                        VariablesCajaElectronica.vObsCierreDia = txtSObs.getText().trim().toUpperCase();
                        if (actualizaIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia,
                                                          FarmaConstants.INDICADOR_S,
                                                          VariablesCajaElectronica.vObsCierreDia)) {
                            FarmaUtility.aceptarTransaccion();
                            FarmaUtility.showMessage(this, "Se otorgó correctamente el VB de Cierre de Dia",
                                                     tblEfectivoRendido);
                            evaluaDeficitAsumidoQF(VariablesCajaElectronica.vFechaCierreDia,
                                                   -1); //-1 = borrar el deficit anterior si hubiese si o si
                            actualizaInfoHistVBCierreDia_Autonomous(VariablesCajaElectronica.vFechaCierreDia, "");
                            VariablesCajaElectronica.vIndVBCierreDia = FarmaConstants.INDICADOR_S;
                            //cargaInfoCierreDia();
                            cargaEfectivoRendido();
                            evaluaTotales();
                            verificaInfoCierreDia(true, false);
                            evaluaMsgVBCierreDia(false);
                        }
                    } else
                        FarmaUtility.liberarTransaccion();
                } //fin ASOSA, 25/04/2010
            }
        } else if (VariablesCajaElectronica.vIndVBCierreDia.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "Esta seguro de eliminar el VB de Cierre de Dia?")) {
                if (cargaLoginOper()) {
                    if (actualizaIndicadorVBCierreDia(VariablesCajaElectronica.vFechaCierreDia,
                                                      FarmaConstants.INDICADOR_N,
                                                      VariablesCajaElectronica.vObsCierreDia)) {
                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this, "Se eliminó correctamente el VB de Cierre de Día.",
                                                 tblEfectivoRendido);
                        evaluaDeficitAsumidoQF(VariablesCajaElectronica.vFechaCierreDia,
                                               -1); //-1 = borrar el deficit anterior si hubiese si o si
                        actualizaInfoHistVBCierreDia_Autonomous(VariablesCajaElectronica.vFechaCierreDia, "");
                        VariablesCajaElectronica.vIndVBCierreDia = FarmaConstants.INDICADOR_N;
                        //cargaInfoCierreDia();
                        cargaEfectivoRendido();
                        evaluaTotales();
                        verificaInfoCierreDia(true, true);
                        evaluaMsgVBCierreDia(false);
                    }
                } else {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this,
                                             "No se realizó la operación. Solo un usuario con Rol de\nOperador de Sistemas puede eliminar el VB de Cierre de Día.",
                                             tblEfectivoRendido);
                }
            } else
                FarmaUtility.liberarTransaccion();
        } else {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Error al validar VB y Diferencia de Cierre de Dia.\nComuniquese con el area de Sistema!!!",
                                     tblEfectivoRendido);
        }
        FarmaUtility.liberarTransaccion();
        /*
    // 14.04.2010 JQUISPE VALIDACION PARA LA RENDICION DE VISA MANUAL

    if(!UtilityCajaElectronica.verificarVisaManual()){
          FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(this,"Atención:\n" +
          "No puede dar V°B° Químico porque existen diferencias en \n la Forma de Pago Visa Manual declarados por el cajero\n" +
          "y el ingresado por el Químico.", tblEfectivoRendido);
          return;
    }
    */
    }

    private void actualizaInfoHistVBCierreDia_Autonomous(String pFechaCierreDia, String pDescMotivo) {
        try {
            DBCajaElectronica.actualizaInfoHistVBCierreDia(pFechaCierreDia, pDescMotivo);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al actualizar informacion del historico de VB de Cierre de Dia.\n" +
                    sql.getMessage(), tblEfectivoRendido);
        }
    }

    private boolean actualizaIndicadorVBCierreDia(String pFechaCierreDia, String pIndicadorVBCierreDia,
                                                  String pDescObs) {
        try {
            DBCajaElectronica.actualizaIndicadorVBCierreDia(pFechaCierreDia, pIndicadorVBCierreDia, pDescObs);
            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al cambiar el VB de Cierre de Dia.\nPor favor, comuniquese con el area de Sistemas.\n" +
                    sql.getMessage(), tblEfectivoRendido);
            return false;
        }
    }

    private void habilitaOpciones(boolean pValor) {
        VariablesCajaElectronica.vIndFuncionesHabilitadas = pValor;
        lblF2.setEnabled(pValor);
        lblF3.setEnabled(pValor);
        lblF9.setEnabled(pValor);
        lblF12.setEnabled(pValor);
    }

    private void limpiaValoresCierreDia() {
        VariablesCajaElectronica.vFechaCierreDia = "";
        VariablesCajaElectronica.vNombreUsuarioLogueado = "";
        VariablesCajaElectronica.vSecUsuLocalCierreDia = "";
        VariablesCajaElectronica.vIndVBCierreDia = "";
        VariablesCajaElectronica.vTipoCambio = "";
        VariablesCajaElectronica.vIsVentanaCierreDiaOpen = FarmaConstants.INDICADOR_N;
    }

    //Inicion Adicion Paulo
    /*private void obtieneComprobantesMinMax()
  {
    try
    {
      VariablesCajaElectronica.compMinMaxCD = DBCajaElectronica.obtieneComprobanteMinMaxCD(VariablesCajaElectronica.vFechaCierreDia);
    } catch(SQLException sql)
    {
      VariablesCajaElectronica.compMinMaxCD = new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener información de comprobantes : \n" + sql.getMessage(),null);
    }
    if(VariablesCajaElectronica.compMinMaxCD.size()==1)
    {
      VariablesCajaElectronica.vBoletaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(0)).trim();
      VariablesCajaElectronica.vBoletaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(1)).trim();
      VariablesCajaElectronica.vFacturaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(2)).trim();
      VariablesCajaElectronica.vFacturaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(3)).trim();
    } else
    {
      VariablesCajaElectronica.vBoletaMin = "";
      VariablesCajaElectronica.vBoletaMax = "";
      VariablesCajaElectronica.vFacturaMin = "";
      VariablesCajaElectronica.vFacturaMax = "";
    }
  }*/

    private void imprimeCierreDia() {

        if (!UtilityCajaElectronica.obtieneIndicadorVBCierreDia_ForUpdate(VariablesCajaElectronica.vFechaCierreDia)) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No se pudo obtener el Indicador actual de VB de Cierre de Dia.\nPor favor, comuniquese con el area de Sistemas.\n",
                                     txtFecha);
            return;
        }
        if (VariablesCajaElectronica.vIndVBCierreDia.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "No se puede realizar esta operación.\nEl Dia de Venta NO cuenta con VB de Cierre de Dia.",
                                     tblEfectivoRendido);
            return;
        }
        FarmaUtility.liberarTransaccion();
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaVariables.vImprReporte = "\\\\10.11.1.53\\comprobantes";
        //FarmaPrintService vPrint = new FarmaPrintService(100,FarmaVariables.vImprReporte, true);
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
            //UtilityCajaElectronica.obtieneComprobantesMinMaxDia(this, VariablesCajaElectronica.vFechaCierreDia);
            ArrayList myArrayUsuario =
                UtilityCajaElectronica.obtieneRangoComprobantesUsuario(this, ConstantsCajaElectronica.TIP_INGRESO_COMP_CD);
            //obtieneComprobantesMinMax();
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(45) + "REPORTE CIERRE DIA", true);
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Nombre Compañia : " + FarmaVariables.vNomCia +
                             "  Local : " + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal, true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Dia de Venta : " +
                             VariablesCajaElectronica.vFechaCierreDia + FarmaPRNUtility.llenarBlancos(3) +
                             "Tipo de Cambio : " + lblMontoTipoCambio.getText() + "   Nombre Q.F : " +
                             VariablesCajaElectronica.vNombreUsuarioLogueado, true);
            /***********lmr*****************/
            for (int i = 0; i < myArrayUsuario.size(); i++) {
                String descTipoComp = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_DESC_TIP_COMP);
                String tipoComp = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_TIP_COMP);
                String numSerie = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_NUM_SERIE_LOCAL);
                String rangoIni = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_RANG_INI);
                String rangoFin = FarmaUtility.getValueFieldArrayList(myArrayUsuario, i, COL_RANG_FIN);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Rango de ==> " + descTipoComp + " De : " +
                                 numSerie + "-" + rangoIni + FarmaPRNUtility.llenarBlancos(3) + "Hasta : " + numSerie +
                                 rangoFin, true);
            }
            //vPrint.printBold(FarmaPRNUtility.llenarBlancos(15)+ "Rango de Boletas      De : " + VariablesCajaElectronica.vBoletaMin + FarmaPRNUtility.llenarBlancos(5)+ "Hasta : " + VariablesCajaElectronica.vBoletaMax, true);
            //vPrint.printBold(FarmaPRNUtility.llenarBlancos(15)+ "Rango de Facturas     De : " + VariablesCajaElectronica.vFacturaMin + FarmaPRNUtility.llenarBlancos(5)+ "Hasta : " + VariablesCajaElectronica.vFacturaMax, true);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.setEndHeader();
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "ENTREGAS DE EFECTIVO O EQUIVALENTE DE EFECTIVO ",
                             true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "Formas de Pago                 Moneda           Monto    Total Soles", true);
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
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "OTRAS CUADRATURAS ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "Cuadratura                                        Total Soles", true);
            for (int i = 0; i < tblCuadraturas.getRowCount(); i++) {
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
                             FarmaPRNUtility.alinearDerecha(lblSubTotalCuadra.getText(), 15) + " (B)", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(" ", true);
            vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Monto Total Cierre    : " +
                                        lblMontoRegistradoCierreTurno.getText().trim() + " (A+B)", true);
            vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Monto Total Sistema   : " +
                                        lblMontoRegistradoSistema.getText().trim(), true);
            vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Diferencia   : " +
                                        FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(lblMontoRegistradoCierreTurno.getText()) -
                                                                  FarmaUtility.getDecimalNumber(lblMontoRegistradoSistema.getText())),
                                        true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "EFECTIVO RECAUDADO ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "Formas de Pago                 Moneda           Monto    Total Soles", true);
            for (int i = 0; i < tblEfectivoRecaudado.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.llenarBlancos(15) +
                                      FarmaPRNUtility.alinearIzquierda(((String)tblEfectivoRecaudado.getValueAt(i,
                                                                                                                0)).trim(),
                                                                       30) + " " +
                                      FarmaPRNUtility.alinearIzquierda(((String)tblEfectivoRecaudado.getValueAt(i,
                                                                                                                1)).trim(),
                                                                       7) + " " +
                                      FarmaPRNUtility.alinearDerecha(((String)tblEfectivoRecaudado.getValueAt(i,
                                                                                                              2)).trim(),
                                                                     14) + " " +
                                      FarmaPRNUtility.alinearDerecha(((String)tblEfectivoRecaudado.getValueAt(i,
                                                                                                              3)).trim(),
                                                                     15), true);
            }
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(72) + "------------", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(55) + "Sub Total S/. " +
                             FarmaPRNUtility.alinearDerecha(lblSubTotalEfecRecaudado.getText(), 15) + " (C)", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(" ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "EFECTIVO RENDIDO ", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) +
                             "Cuadratura                                        Total Soles", true);
            for (int i = 0; i < tblEfectivoRendido.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.llenarBlancos(15) +
                                      FarmaPRNUtility.alinearIzquierda(((String)tblEfectivoRendido.getValueAt(i,
                                                                                                              1)).trim().toUpperCase(),
                                                                       45) + " " +
                                      FarmaPRNUtility.alinearDerecha(((String)tblEfectivoRendido.getValueAt(i,
                                                                                                            2)).trim().toUpperCase(),
                                                                     15), true);

            }
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(63) + "-------------", true);
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(47) + "Sub Total S/. " +
                             FarmaPRNUtility.alinearDerecha(lblSubTotalEfecRendido.getText(), 15) + " (D)", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(" ", true);

            /** Imprimimos resumen de productos Virtuales
      * @author: JCORTEZ
      * @since : 10/07/07
      */
            if (cargarProductosVirtuales()) {
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "PRODUCTOS VIRTUALES ", true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Tipo Producto      Cantidad  Total S/. ", true);
                for (int i = 0; i < arrayVirtuales.size(); i++) {
                    vPrint.printCondensed(FarmaPRNUtility.llenarBlancos(15) +
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(arrayVirtuales,
                                                                                                               i, 0),
                                                                           18) + " " +
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(arrayVirtuales,
                                                                                                               i, 1),
                                                                           10) + " " +
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(arrayVirtuales,
                                                                                                               i, 2),
                                                                           8), true);
                }
                vPrint.activateCondensed();
            } else {
                vPrint.printLine(" ", true);
                vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                                 "=======================================================================", true);
                vPrint.printLine(" ", true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "NO SE ENCONTRARON PRODUCTOS VIRTUALES ", true);
                vPrint.printLine(" ", true);
            }

            /** Imprimimos los comprobantes desfasados
      * @author: JCORTEZ
      * @since : 10/07/07
      */
            if (cargarComprobantes()) {
                vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                                 "=======================================================================", true);
                vPrint.printLine(" ", true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "COMPROBANTES DESFASADOS ", true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "Tipo Comp. Nro Comp    Correlativo   Estado",
                                 true);
                for (int i = 0; i < arrayDesfasados.size(); i++) {
                    vPrint.printCondensed(FarmaPRNUtility.llenarBlancos(15) +
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(arrayDesfasados,
                                                                                                               i, 0),
                                                                           10) + " " +
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(arrayDesfasados,
                                                                                                               i, 1),
                                                                           11) + " " +
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(arrayDesfasados,
                                                                                                               i, 2),
                                                                           12) + "  " +
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(arrayDesfasados,
                                                                                                               i, 3),
                                                                           10), true);
                }
                vPrint.activateCondensed();
            } else {
                vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                                 "=======================================================================", true);
                vPrint.printLine(" ", true);
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(15) + "NO SE ENCONTRARON COMPROBANTES DESFASADOS ",
                                 true);
            }
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) +
                             "=======================================================================", true);
            vPrint.printLine(" ", true);
            vPrint.printDoubleWidthMode(FarmaPRNUtility.llenarBlancos(8) + "Diferencia   : " +
                                        FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(lblSubTotalEfecRecaudado.getText()) -
                                                                  FarmaUtility.getDecimalNumber(lblSubTotalEfecRendido.getText())) +
                                        "(C-D)", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(15) + "Observaciones : ", true);
            vPrint.printCutLine(txtSObs.getText().trim(), 100, 15);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + "__________________________", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + "  Nombre y Firma del QF", true);

            vPrint.deactivateCondensed();
            vPrint.endPrintServiceSinCompletar();


        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), tblFormasPago);
        }
    }


    /** Retorna por dia un listado de los comprobantes desfasados
     * @author: JCORTEZ
     * @since : 10/07/07
     */
    private boolean cargarComprobantes() {
        myArray.clear();
        arrayDesfasados.clear();
        boolean rpta = true;
        try {
            double valorAct = 0;
            double valorSig = 0;
            String tipAct = " ";
            String tipSig = " ";
            // JMIRANDA 29.10.2009
            String tipSerieAct = "";
            String tipSerieSig = "";
            //String pCadenaAct = "";
            //String pCadenaSig = "";
            String pSerieAct = "";
            String pSerieSig = "";
            String pTipoCompro = "";
            int indexBusqueda = 10;
            // JMIRANDA 29.10.2009
            if (VariablesCaja.vTipOrdComprobantes.trim().equals(ConstantsCaja.TIP_ORD_CORRELATIVO)) {
                indexBusqueda = 11;
            }
            DBCajaElectronica.getListaPedidosCompRangosCierre(myArray, txtFecha.getText().trim());
            if (myArray.size() > 0) {
                FarmaUtility.ordenar(myArray, 7, FarmaConstants.ORDEN_ASCENDENTE);
            }
            ArrayList elementoLista;

            VariablesCaja.listaCompsDesfasados = new ArrayList();

            for (int i = 0; i < myArray.size() - 1; i++) {
                // JMIRANDA 29.10.2009			
                //pCadenaAct = FarmaUtility.getValueFieldArrayList(myArray,i, indexBusqueda).toString();
                //pCadenaSig = FarmaUtility.getValueFieldArrayList(myArray,i + 1,indexBusqueda).toString();
                tipSerieAct = FarmaUtility.getValueFieldArrayList(myArray, i, 6).toString();
                tipSerieSig = FarmaUtility.getValueFieldArrayList(myArray, i + 1, 6).toString();

                valorAct = Double.parseDouble(FarmaUtility.getValueFieldArrayList(myArray, i, 12)); //indexBusqueda
                valorSig = Double.parseDouble(FarmaUtility.getValueFieldArrayList(myArray, i + 1, 12)); //indexBusqueda
                tipAct = FarmaUtility.getValueFieldArrayList(myArray, i, 9);
                tipSig = FarmaUtility.getValueFieldArrayList(myArray, i + 1, 9);

                pTipoCompro = FarmaUtility.getValueFieldArrayList(myArray, i + 1, 5).toString();
                // JMIRANDA 29.10.2009
                //tipSerieAct  = pCadenaAct.trim().substring(0,3);
                //tipSerieSig  = pCadenaSig.substring(0,3);

                if (valorAct + 1 != valorSig && tipAct.equals(tipSig) && tipSerieAct.equals(tipSerieSig) &&
                    pTipoCompro.equals("0")) {
                    //if (valorAct + 1 != valorSig && tipAct.equals(tipSig)) {
                    elementoLista = new ArrayList();
                    elementoLista.clear();
                    elementoLista.add(FarmaUtility.getValueFieldArrayList(myArray, i, 1).toString().trim());
                    elementoLista.add(FarmaUtility.getValueFieldArrayList(myArray, i, 2).toString().trim());
                    elementoLista.add(FarmaUtility.getValueFieldArrayList(myArray, i, 3).toString().trim());
                    elementoLista.add(FarmaUtility.getValueFieldArrayList(myArray, i, 6).toString().trim());
                    elementoLista.add(FarmaUtility.getValueFieldArrayList(myArray, i, 7).toString().trim());
                    arrayDesfasados.add(elementoLista);
                }
            }
            if (arrayDesfasados.size() == 0) {
                rpta = false;
            }
            if (myArray.size() > 0) {
                FarmaUtility.ordenar(myArray, 4, FarmaConstants.ORDEN_ASCENDENTE);
            }

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener lista de comprobantes desfasados. \n " + e.getMessage(),
                                     txtFecha);
        }
        return rpta;
    }

    /** Retorna por dia un resumen de productos Virtuales
     * @author: JCORTEZ
     * @since : 10/07/07
     */
    private boolean cargarProductosVirtuales() {
        myArrayV.clear();
        arrayVirtuales.clear();
        boolean rpta = true;
        try {
            DBCajaElectronica.getListaProductosVirtuales(myArrayV, txtFecha.getText().trim());
            ArrayList elementoLista;

            //VariablesCaja.listaCompsDesfasados = new ArrayList();

            for (int i = 0; i < myArrayV.size(); i++) {
                elementoLista = new ArrayList();
                elementoLista.clear();
                elementoLista.add(FarmaUtility.getValueFieldArrayList(myArrayV, i, 0).toString().trim());
                elementoLista.add(FarmaUtility.getValueFieldArrayList(myArrayV, i, 1).toString().trim());
                elementoLista.add(FarmaUtility.getValueFieldArrayList(myArrayV, i, 2).toString().trim());
                arrayVirtuales.add(elementoLista);
            }

            if (arrayVirtuales.size() == 0) {
                rpta = false;
            }

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener resumen de productos virtuales . \n " + e.getMessage(),
                                     txtFecha);
        }
        return rpta;
    }

    //Fin Adicion Paulo

    private void evaluaMsgEtiquetaVB(int pIndicador) {
        switch (pIndicador) {
        case 3: //TIENE VB DE CIERRE DIA
            lblMsgVisado_T.setVisible(true);
            lblMsgVisado_T.setText("El día de Venta cuenta con VB otorgado por " +
                                   VariablesCajaElectronica.vNombreUsuarioVBCierreDia + ".");
            break;
        case 6: // TIENE VB DE CONTABILIDAD
            lblMsgVisado_T.setVisible(true);
            lblMsgVisado_T.setText("El día de Venta cuenta con VB de Contabilidad. ");
            break;
        default: //NO TIENE VB DE QF, NO SE ENCONTRO CIERRE DE DIA
            lblMsgVisado_T.setVisible(false);
            lblMsgVisado_T.setText("");
            break;
        }
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

    private void cargaDetalleEfectivoRendido() {
        int row = tblEfectivoRendido.getSelectedRow();
        if (row > -1) {
            VariablesCajaElectronica.vTipCuadratura = "";
            VariablesCajaElectronica.vCodCuadratura = tblEfectivoRendido.getValueAt(row, 0).toString().trim();
            if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DEFICIT_QF)) {
                //FarmaUtility.showMessage(this,"No es posible realizar la operación.\nEste efectivo rendido no contiene detalle.",tblEfectivoRendido);
                FarmaUtility.showMessage(this, "No es posible realizar la operación.\n" +
                        "La cuadratura Deficit Asumido por el QF no tiene detalle para mostrar.", tblEfectivoRendido);
                return;
            }
            VariablesCajaElectronica.vDescCuadratura = tblEfectivoRendido.getValueAt(row, 1).toString().trim();
            DlgListaEliminacionEfectivoRendido dlgListaEliminacionEfectivoRendido =
                new DlgListaEliminacionEfectivoRendido(myParentFrame, "", true);
            dlgListaEliminacionEfectivoRendido.setVisible(true);
            cargaEfectivoRendido();
            evaluaTotales();
        }
    }

    private void cargaDetalleOtrasCuadraturas() {
        int row = tblCuadraturas.getSelectedRow();
        if (row > -1) {
            VariablesCajaElectronica.vCodCuadratura = tblCuadraturas.getValueAt(row, 0).toString().trim();
            /*
      if(VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DEFICIT_CAJERO))
      {
        FarmaUtility.showMessage(this,"No es posible realizar la operación.\nEste efectivo rendido no contiene detalle.",tblCuadraturas);
        return;
      }
      */
            VariablesCajaElectronica.vDescCuadratura = tblCuadraturas.getValueAt(row, 1).toString().trim();
            DlgListaDetalleOtrasCuadraturas dlgListaDetalleOtrasCuadraturas =
                new DlgListaDetalleOtrasCuadraturas(myParentFrame, "", true);
            dlgListaDetalleOtrasCuadraturas.setVisible(true);
        }
    }

    private void cargaDetalleFormasPago() {
        int row = tblFormasPago.getSelectedRow();
        if (row > -1) {
            VariablesCajaElectronica.vCodFormaPago = tblFormasPago.getValueAt(row, 4).toString().trim();
            VariablesCajaElectronica.vDescFormaPago = tblFormasPago.getValueAt(row, 0).toString().trim();
            VariablesCajaElectronica.vTipMoneda = tblFormasPago.getValueAt(row, 1).toString().trim();
            DlgListaDetalleFormaPago dlgListaDetalleFormaPago = new DlgListaDetalleFormaPago(myParentFrame, "", true);
            dlgListaDetalleFormaPago.setVisible(true);
        }
    }

    private void verificaInfoCierreDia(boolean pLeerBD, boolean pEnabled) {
        colocaInfoCierreDia(pLeerBD);
        txtSObs.setEditable(pEnabled);
        //txtSObs.setEnabled(pEnabled);
    }

    private void colocaInfoCierreDia(boolean pLeerBD) {
        if (pLeerBD)
            obtieneInfoCierreDia(VariablesCajaElectronica.vFechaCierreDia);
        log.debug("VariablesCajaElectronica.vObsCierreDia : " + VariablesCajaElectronica.vObsCierreDia);
        log.debug("VariablesCajaElectronica.vNombreUsuarioVBCierreDia : " +
                  VariablesCajaElectronica.vNombreUsuarioVBCierreDia);
        txtSObs.setText(VariablesCajaElectronica.vObsCierreDia);
    }

    private void obtieneInfoCierreDia(String pFechaCierreDia) {
        ArrayList myArray = new ArrayList();
        try {
            DBCajaElectronica.obtenerInfoCierreDia(myArray, pFechaCierreDia);
        } catch (SQLException sql) {
            myArray.clear();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener info del cierre de dia.", tblEfectivoRendido);
        } finally {
            log.debug("myArray.size() : " + myArray.size());
            if (myArray.size() == 1) {
                log.debug("myArray : " + myArray);
                VariablesCajaElectronica.vObsCierreDia = FarmaUtility.getValueFieldArrayList(myArray, 0, 0);
                VariablesCajaElectronica.vNombreUsuarioVBCierreDia =
                        FarmaUtility.getValueFieldArrayList(myArray, 0, 1);
            } else {
                VariablesCajaElectronica.vObsCierreDia = "";
                VariablesCajaElectronica.vNombreUsuarioVBCierreDia = "";
            }
        }
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
            UtilityCajaElectronica.obtieneRangoComprobantesCorrectos(this, ConstantsCajaElectronica.TIP_INGRESO_COMP_CD);
        ArrayList myArrayUsuario =
            UtilityCajaElectronica.obtieneRangoComprobantesUsuario(this, ConstantsCajaElectronica.TIP_INGRESO_COMP_CD);
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
    UtilityCajaElectronica.obtieneComprobantesMinMaxDia(this, VariablesCajaElectronica.vFechaCierreDia);
    if( VariablesCajaElectronica.vBoletaMin.equalsIgnoreCase(VariablesCajaElectronica.vBoletaMinDia) &&
        VariablesCajaElectronica.vBoletaMax.equalsIgnoreCase(VariablesCajaElectronica.vBoletaMaxDia) &&
        VariablesCajaElectronica.vFacturaMin.equalsIgnoreCase(VariablesCajaElectronica.vFacturaMinDia) &&
        VariablesCajaElectronica.vFacturaMax.equalsIgnoreCase(VariablesCajaElectronica.vFacturaMaxDia) )
      return true;
    return false;
  }*/

    /*private void guardaInformacionComprobantes()
  {
    VariablesCajaElectronica.vBoletaMinDia = VariablesCajaElectronica.vBoletaMinGeneral;
    VariablesCajaElectronica.vBoletaMaxDia = VariablesCajaElectronica.vBoletaMaxGeneral;
    VariablesCajaElectronica.vFacturaMinDia = VariablesCajaElectronica.vFacturaMinGeneral;
    VariablesCajaElectronica.vFacturaMaxDia = VariablesCajaElectronica.vFacturaMaxGeneral;
  }*/

    private void ingresarRangoComprobantes(boolean isIngresoComprobantes) {
        VariablesCajaElectronica.vIndCompValidos =
                UtilityCajaElectronica.obtieneIndicadorComprobatesValidosDia(this, VariablesCajaElectronica.vFechaCierreDia);
        VariablesCajaElectronica.vTipoIngresoComprobantes = ConstantsCajaElectronica.TIP_INGRESO_COMP_CD;
        DlgIngresoRangoComprobantes dlgIngresoRangoComprobantes =
            new DlgIngresoRangoComprobantes(myParentFrame, "", true);
        // KMONCADA 31.12.2014 INDICADOR DE MOTIVO DE MOSTRAR VENTANA
        dlgIngresoRangoComprobantes.setIsIngresoComprobantes(isIngresoComprobantes);
        dlgIngresoRangoComprobantes.setVisible(true);
    }

    private void evaluaDeficitAsumidoQF(String pFechaCierreDia, double pMontoDeficit) {
        try {
            log.debug("*********** faltante : " + pMontoDeficit + " *************");
            DBCajaElectronica.evaluaDeficitAsumidoQF(pFechaCierreDia, pMontoDeficit);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al evaluar Deficit asumido de QF.\n " + sql.getMessage(), null);
        }
    }

    private boolean validaVBQFCajasDiaVenta(String pFechaCierreDia) {
        int cont = 1;
        try {
            cont = DBCajaElectronica.validaCajasConVBQFDiaVenta(pFechaCierreDia);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al validar cajas cerradas con VB de QF.\n" +
                    sql, txtFecha);
            cont = 1;
        }
        if (cont == 0)
            return false;
        else
            return true;
    }

    private void evaluaExistenciaCajasSinVBQF(String pFechaCierreDia) {
        VariablesCajaElectronica.vExistenCajasSinVBQFDiaVenta = validaVBQFCajasDiaVenta(pFechaCierreDia);
    }

    private boolean validaDsctoPersonal() {
        boolean rpta = false;
        //validando dscto personal
        String montoEfecRecaudado = "0.00";
        String montoEfecRendido = "0.00";
        String codigoEfecRecaudado = "";
        String codigoEfecRendido = "";
        for (int i = 0; i < tblEfectivoRecaudado.getRowCount(); i++) {
            codigoEfecRecaudado = FarmaUtility.getValueFieldJTable(tblEfectivoRecaudado, i, 4);
            if (codigoEfecRecaudado.equals(ConstantsCajaElectronica.FORMA_PAGO_DSCT_PERSONAL)) {
                montoEfecRecaudado = FarmaUtility.getValueFieldJTable(tblEfectivoRecaudado, i, 3);
                break;
            }
        }
        for (int j = 0; j < tblEfectivoRendido.getRowCount(); j++) {
            codigoEfecRendido = FarmaUtility.getValueFieldJTable(tblEfectivoRendido, j, 0);
            if (codigoEfecRendido.equals(ConstantsCajaElectronica.CUADRATURA_DCTO_PERSONAL)) {
                montoEfecRendido = FarmaUtility.getValueFieldJTable(tblEfectivoRendido, j, 2);
                break;
            }
        }
        if (FarmaUtility.getDecimalNumber(montoEfecRecaudado) == FarmaUtility.getDecimalNumber(montoEfecRendido))
            rpta = true;
        return rpta;
    }

    private void evaluaVBQF(String pFechaCierreDia) {
        VariablesCajaElectronica.vExisteVBQF = validaVBQF(pFechaCierreDia);
    }

    private boolean validaVBQF(String pFechaCierreDia) {
        String ind = "";
        try {
            ind = DBCajaElectronica.obtieneVBQF(pFechaCierreDia);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtner el VB de QF.\n" +
                    sql, txtFecha);
            ind = "";
        }
        if (ind.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;
        else
            return true;
    }

    private void asignaVBContable() {
        try {
            DBCajaElectronica.asignaVBContable(VariablesCajaElectronica.vFechaCierreDia);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se otorgó correctamente el VB Contable", tblEfectivoRendido);
            VariablesCajaElectronica.vIndVBContable = FarmaConstants.INDICADOR_S;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al otorgar el VB contable \n" +
                    sql.getMessage(), tblEfectivoRendido);
        }
    }

    private void cambiaVBContable() {
        evaluaVBQF(VariablesCajaElectronica.vFechaCierreDia);
        if (!VariablesCajaElectronica.vExisteVBQF) {
            FarmaUtility.showMessage(this, "El cierre de dia aun no cuenta con VB de QF  \n", tblEfectivoRendido);
        } else {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "Esta seguro de asignar el VB Contable?")) {
                if (cargaLoginContabilidad()) {
                    asignaVBContable();
                    evaluaMsgEtiquetaVB(ConstantsCajaElectronica.CON_VB_CONTABLE);
                }
            }
        }
    }

    private boolean cargaLoginContabilidad() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_LECTURA_REPORTES);
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

    private int obtieneCantReclamosNavsat(String pFechaCierreDia) {
        int cantidad = 0;
        try {
            cantidad = DBCajaElectronica.validaCompConReclamosNavsat(pFechaCierreDia);
        } catch (SQLException sql) {
            cantidad = 0;
            log.error("", sql);
        }
        return cantidad;
    }

    private void evaluaMsjReclamosNavsat(String pFechaCierreDia) {
        int cantReclamos = 0;
        cantReclamos = obtieneCantReclamosNavsat(pFechaCierreDia);
        if (cantReclamos > 0) {
            VariablesCajaElectronica.vExistenCompReclamosNavsat = true;
            lblMslReclamoNavsat.setVisible(true);
            lblF7.setVisible(true);
        } else {
            VariablesCajaElectronica.vExistenCompReclamosNavsat = false;
            lblMslReclamoNavsat.setVisible(false);
            lblF7.setVisible(false);
        }
    }

    private void verReclamosProveedor() {
        DlgReclamosComprobantes dlgReclamosComprobantes = new DlgReclamosComprobantes(myParentFrame, "", true);
        dlgReclamosComprobantes.setVisible(true);
    }

    private void guardaInformacionRangoComprobantes(int pRow) {
        int COL_DESC_TIP_COMP = 0;
        int COL_NUM_SERIE_LOCAL = 1;
        int COL_RANG_INI = 2;
        int COL_RANG_FIN = 3;
        int COL_MONT_MIN = 4;
        int COL_MONT_FIN = 5;
        int COL_TIP_COMP = 6;
        int COL_IND_FINAL = 7;

        VariablesCajaElectronica.vTipCompDia =
                FarmaUtility.getValueFieldArrayList(VariablesCajaElectronica.vRangoCompIngresadosCierreDia, pRow,
                                                    COL_TIP_COMP);
        VariablesCajaElectronica.vNumSerieDia =
                FarmaUtility.getValueFieldArrayList(VariablesCajaElectronica.vRangoCompIngresadosCierreDia, pRow,
                                                    COL_NUM_SERIE_LOCAL);
        VariablesCajaElectronica.vNumeroMinDia =
                FarmaUtility.getValueFieldArrayList(VariablesCajaElectronica.vRangoCompIngresadosCierreDia, pRow,
                                                    COL_RANG_INI);
        VariablesCajaElectronica.vNumeroMaxDia =
                FarmaUtility.getValueFieldArrayList(VariablesCajaElectronica.vRangoCompIngresadosCierreDia, pRow,
                                                    COL_RANG_FIN);
        VariablesCajaElectronica.vIndRangoGrabado =
                FarmaUtility.getValueFieldArrayList(VariablesCajaElectronica.vRangoCompIngresadosCierreDia, pRow,
                                                    COL_IND_FINAL);
        VariablesCajaElectronica.vMontoMin =
                FarmaUtility.getValueFieldArrayList(VariablesCajaElectronica.vRangoCompIngresadosCierreDia, pRow,
                                                    COL_MONT_MIN);
        VariablesCajaElectronica.vMontoMAx =
                FarmaUtility.getValueFieldArrayList(VariablesCajaElectronica.vRangoCompIngresadosCierreDia, pRow,
                                                    COL_MONT_FIN);
    }

    /**
     * Valida la diferencia entre monto recaudado y rendido soles y dolares
     * @return
     */
    private boolean validaMontoRecRen() {
        //log.debug("HOHOHOHOHOHOLALALALALALALLAJIJIJIJIJIJIJIAHHAHAHAHEHEHEHHEHE");
        boolean flag = true;
        String ind = "";
        try {
            ind = DBCajaElectronica.getValidarMontoRecRen(VariablesCajaElectronica.vFechaCierreDia);
        } catch (SQLException e) {
            log.error("", e);
        }
        if (ind.equals("1")) {
            FarmaUtility.showMessage(this, "Hay mas monto rendido que recaudado, verifique", null);
            flag = false;
        } else if (ind.equals("2")) {
            FarmaUtility.showMessage(this, "Hay mas monto recaudado que rendido, verifique", null);
            flag = false;
        } else if (ind.equals("3")) {
            flag = true;
        }
        return flag;
    }

    public void muestraCajasAperturadas(String pMensaje) {
        DlgListaCajasAperturadas dlgListaCajasAperturadas = new DlgListaCajasAperturadas(myParentFrame, "", true);
        if (VariablesCajaElectronica.vFechaCierreDia.trim().length() > 0)
            dlgListaCajasAperturadas.setFechaCierreDia(VariablesCajaElectronica.vFechaCierreDia);
        else
            dlgListaCajasAperturadas.setFechaCierreDia(txtFecha.getText().trim());
        dlgListaCajasAperturadas.setMensaje(pMensaje);
        dlgListaCajasAperturadas.setVisible(true);
    }
}
