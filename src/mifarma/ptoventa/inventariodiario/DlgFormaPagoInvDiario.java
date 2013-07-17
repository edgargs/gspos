package mifarma.ptoventa.inventariodiario;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;


import mifarma.ptoventa.inventariodiario.reference.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gs.mifarma.componentes.JLabelFunction;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgInicioInvDiario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ    23.06.2009   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 * 
 */
public class DlgFormaPagoInvDiario extends JDialog {

    private static final Log log = 
        LogFactory.getLog(DlgFormaPagoInvDiario.class);

    /** Almacena el Objeto Frame de la Aplicación - Ventana Principal */
    private Frame myParentFrame;

    private FarmaTableModel tableModelFormasPago;
    private FarmaTableModel tableModelDetallePago;

    private boolean indPedirLogueo = true;
    private boolean indCerrarPantallaAnularPed = false;
    private boolean indCerrarPantallaCobrarPed = false;
    private String valor = "";
    private double diferencia = 0;

    //JCORTEZ 08.07.08
    private boolean indBorra = false;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel pnlTotales = new JPanel();
    private XYLayout xYLayout5 = new XYLayout();
    private JLabel lblSaldoT = new JLabel();
    private JLabel lblSaldo = new JLabel();
    private JLabel lblVueltoT = new JLabel();
    private JLabel lblVuelto = new JLabel();
    private JPanel pnlFormaPago = new JPanel();
    private JLabel lblTipoComprobante = new JLabel();
    private JLabel lblTipoComprobante_T = new JLabel();
    private JPanel pnlTotal = new JPanel();
    private XYLayout xYLayout2 = new XYLayout();
    private JTextField txtNroPedido = new JTextField();
    private JButton btnPedido = new JButton();
    private JLabel lblDolares = new JLabel();
    private JLabel lblSoles = new JLabel();
    private JLabel lblDolaresT = new JLabel();
    private JLabel lblSolesT = new JLabel();
    private JLabel lblTotalPagar = new JLabel();
    private JScrollPane scrDetallePago = new JScrollPane();
    private JPanel pnlDetallePago = new JPanel();
    private XYLayout xYLayout1 = new XYLayout();
    private JButton btnDetallePago = new JButton();
    private JTable tblDetallePago = new JTable();
    private JLabel lblFecPed = new JLabel();
    private JLabel lblTipoCambioT = new JLabel();
    private JLabel lblTipoCambio = new JLabel();
    private JLabel lblMsjPedVirtual = new JLabel();
    private JLabel lblMsjNumRecarga = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */
    public DlgFormaPagoInvDiario() {
        this(null, "", false);
    }

    /**
     *Constructor
     *@param parent Objeto Frame de la Aplicación.
     *@param title Título de la Ventana.
     *@param modal Tipo de Ventana.
     */
    public DlgFormaPagoInvDiario(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(591, 298));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Cobrar Pedido");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(554, 504));

        lblEsc.setText("[ Esc ]  Cerrar");
        lblEsc.setBounds(new Rectangle(495, 240, 80, 20));
        lblF11.setText("[ F11 ]  Aceptar");
        lblF11.setBounds(new Rectangle(385, 240, 95, 20));
        pnlTotales.setBounds(new Rectangle(10, 190, 565, 40));
        pnlTotales.setFont(new Font("SansSerif", 0, 11));
        pnlTotales.setBackground(new Color(43, 141, 39));
        pnlTotales.setLayout(xYLayout5);
        lblSaldoT.setText("TOTAL A PAGAR :  S/.");
        lblSaldoT.setFont(new Font("SansSerif", 1, 13));
        lblSaldoT.setForeground(Color.white);
        lblSaldo.setText("0.00");
        lblSaldo.setFont(new Font("SansSerif", 1, 13));
        lblSaldo.setForeground(Color.white);
        lblSaldo.setHorizontalAlignment(SwingConstants.LEFT);
        lblVueltoT.setText("Vuelto :  S/.");
        lblVueltoT.setFont(new Font("SansSerif", 1, 13));
        lblVueltoT.setForeground(Color.white);
        lblVuelto.setText("0.00");
        lblVuelto.setFont(new Font("SansSerif", 1, 13));
        lblVuelto.setForeground(Color.white);
        pnlFormaPago.setBounds(new Rectangle(10, 50, 565, 25));
        pnlFormaPago.setFont(new Font("SansSerif", 0, 11));
        pnlFormaPago.setForeground(SystemColor.inactiveCaptionText);
        pnlFormaPago.setBorder(BorderFactory.createTitledBorder(""));
        pnlFormaPago.setBackground(Color.white);
        pnlFormaPago.setLayout(null);
        lblTipoComprobante.setForeground(new Color(43, 141, 39));
        lblTipoComprobante.setFont(new Font("SansSerif", 1, 12));
        lblTipoComprobante.setBounds(new Rectangle(165, 5, 120, 15));
        lblTipoComprobante_T.setText("Tipo de Comprobante :");
        lblTipoComprobante_T.setForeground(new Color(43, 141, 39));
        lblTipoComprobante_T.setFont(new Font("SansSerif", 1, 12));
        lblTipoComprobante_T.setBounds(new Rectangle(10, 5, 145, 15));
        pnlTotal.setBounds(new Rectangle(10, 5, 565, 40));
        pnlTotal.setFont(new Font("SansSerif", 0, 11));
        pnlTotal.setBorder(BorderFactory.createTitledBorder(""));
        pnlTotal.setBackground(new Color(43, 141, 39));
        pnlTotal.setLayout(xYLayout2);
        txtNroPedido.setFont(new Font("SansSerif", 0, 12));
        txtNroPedido.setEditable(false);
        txtNroPedido.setDocument(new FarmaLengthText(4));
        txtNroPedido.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        txtNroPedido_actionPerformed(e);
                    }
                });
        txtNroPedido.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtNroPedido_keyPressed(e);
                    }
                });
        btnPedido.setText("Pedido :");
        btnPedido.setDefaultCapable(false);
        btnPedido.setRequestFocusEnabled(false);
        btnPedido.setBorderPainted(false);
        btnPedido.setFocusPainted(false);
        btnPedido.setContentAreaFilled(false);
        btnPedido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnPedido.setHorizontalAlignment(SwingConstants.LEFT);
        btnPedido.setMnemonic('p');
        btnPedido.setFont(new Font("SansSerif", 1, 13));
        btnPedido.setForeground(Color.white);
        btnPedido.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnPedido_actionPerformed(e);
                    }
                });
        lblDolares.setText("0.00");
        lblDolares.setFont(new Font("SansSerif", 1, 13));
        lblDolares.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDolares.setForeground(Color.white);
        lblSoles.setText("0.00");
        lblSoles.setFont(new Font("SansSerif", 1, 13));
        lblSoles.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSoles.setForeground(Color.white);
        lblDolaresT.setText("US$");
        lblDolaresT.setFont(new Font("SansSerif", 1, 13));
        lblDolaresT.setForeground(Color.white);
        lblSolesT.setText("S/.");
        lblSolesT.setFont(new Font("SansSerif", 1, 13));
        lblSolesT.setForeground(Color.white);
        lblTotalPagar.setText("TOTAL VENTA :");
        lblTotalPagar.setFont(new Font("SansSerif", 1, 13));
        lblTotalPagar.setForeground(Color.white);
        scrDetallePago.setBounds(new Rectangle(10, 110, 565, 80));
        scrDetallePago.setFont(new Font("SansSerif", 0, 11));
        scrDetallePago.setBackground(new Color(255, 130, 14));
        pnlDetallePago.setBounds(new Rectangle(10, 85, 565, 25));
        pnlDetallePago.setFont(new Font("SansSerif", 0, 11));
        pnlDetallePago.setBackground(new Color(255, 130, 14));
        pnlDetallePago.setLayout(xYLayout1);
        btnDetallePago.setText("Detalle de Pago :");
        btnDetallePago.setFont(new Font("SansSerif", 1, 11));
        btnDetallePago.setForeground(Color.white);
        btnDetallePago.setHorizontalAlignment(SwingConstants.LEFT);
        btnDetallePago.setMnemonic('d');
        btnDetallePago.setRequestFocusEnabled(false);
        btnDetallePago.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDetallePago.setBackground(new Color(255, 130, 14));
        btnDetallePago.setContentAreaFilled(false);
        btnDetallePago.setDefaultCapable(false);
        btnDetallePago.setBorderPainted(false);
        btnDetallePago.setFocusPainted(false);
        btnDetallePago.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnDetallePago_actionPerformed(e);
                    }
                });
        tblDetallePago.setFont(new Font("SansSerif", 0, 11));

        tblDetallePago.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblDetallePago_keyPressed(e);
                    }
                });
        lblFecPed.setFont(new Font("SansSerif", 1, 12));
        lblFecPed.setForeground(Color.white);
        lblTipoCambioT.setText("Tipo Cambio : ");
        lblTipoCambioT.setBounds(new Rectangle(355, 5, 85, 15));
        lblTipoCambioT.setForeground(new Color(43, 141, 39));
        lblTipoCambioT.setFont(new Font("SansSerif", 1, 12));
        lblTipoCambio.setBounds(new Rectangle(460, 5, 55, 15));
        lblTipoCambio.setFont(new Font("SansSerif", 1, 12));
        lblTipoCambio.setForeground(new Color(43, 141, 39));
        lblMsjPedVirtual.setForeground(Color.red);
        lblMsjPedVirtual.setFont(new Font("SansSerif", 1, 13));
        lblMsjPedVirtual.setBounds(new Rectangle(10, 80, 445, 20));
        lblMsjNumRecarga.setForeground(new Color(43, 141, 39));
        lblMsjNumRecarga.setFont(new Font("SansSerif", 1, 17));
        lblMsjNumRecarga.setBounds(new Rectangle(445, 80, 125, 20));

        pnlTotales.add(lblSaldoT, new XYConstraints(105, 10, 150, 20));
        pnlTotales.add(lblSaldo, new XYConstraints(255, 10, 105, 20));
        pnlTotales.add(lblVueltoT, new XYConstraints(395, 10, 80, 20));
        pnlTotales.add(lblVuelto, new XYConstraints(480, 10, 70, 20));
        pnlFormaPago.add(lblTipoCambio, null);
        pnlFormaPago.add(lblTipoCambioT, null);
        pnlFormaPago.add(lblTipoComprobante, null);
        pnlFormaPago.add(lblTipoComprobante_T, null);
        pnlTotal.add(lblFecPed, new XYConstraints(130, 5, 70, 20));
        pnlTotal.add(txtNroPedido, new XYConstraints(65, 0, 110, 25));
        pnlTotal.add(btnPedido, new XYConstraints(0, 5, 60, 20));
        pnlTotal.add(lblDolares, new XYConstraints(470, 5, 70, 20));
        pnlTotal.add(lblSoles, new XYConstraints(340, 5, 75, 20));
        pnlTotal.add(lblDolaresT, new XYConstraints(435, 5, 35, 20));
        pnlTotal.add(lblSolesT, new XYConstraints(315, 5, 20, 20));
        pnlTotal.add(lblTotalPagar, new XYConstraints(205, 5, 105, 20));
        scrDetallePago.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblMsjNumRecarga, null);
        jContentPane.add(lblMsjPedVirtual, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlTotales, null);
        jContentPane.add(pnlFormaPago, null);
        jContentPane.add(pnlTotal, null);
        scrDetallePago.getViewport().add(tblDetallePago, null);
        jContentPane.add(scrDetallePago, null);
        pnlDetallePago.add(btnDetallePago, new XYConstraints(10, 5, 115, 15));
        jContentPane.add(pnlDetallePago, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableDetallePago();
        //cargaCombo();
        txtNroPedido.setText(VariablesInvDiario.vNumPedido.trim());
        buscaPedido(VariablesInvDiario.vNumPedido.trim());
        colocaFormaPagoPedido(VariablesInvDiario.vNumPedido.trim());
        verificaMontoPagadoPedido();
        FarmaVariables.vAceptar = false;
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableDetallePago() {
        tableModelDetallePago = 
                new FarmaTableModel(ConstantsInvDiario.columsListaDetallePago, 
                                    ConstantsInvDiario.defaultListaDetallePago, 
                                    0);
        FarmaUtility.initSimpleList(tblDetallePago, tableModelDetallePago, 
                                    ConstantsInvDiario.columsListaDetallePago);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void btnPedido_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroPedido);

    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroPedido);
        lblMsjPedVirtual.setVisible(false);
        lblMsjNumRecarga.setVisible(false);
        if (!UtilityCaja.existeCajaUsuarioImpresora(this, null) || 
            !UtilityCaja.validaFechaMovimientoCaja(this, tblDetallePago)) {
            //No se podrá cobrar por error de caja o Apertura
            cerrarVentana(true);
            limpiarVariables();
            return;
        }
        FarmaVariables.vAceptar = false;
        FarmaUtility.moveFocus(tblDetallePago);
    }

    private void tblDetallePago_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void txtNroPedido_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    private void btnDetallePago_actionPerformed(ActionEvent e) {
        if (tblDetallePago.getRowCount() == 0)
            return;
        FarmaUtility.moveFocusJTable(tblDetallePago);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkkeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            //se guarda valores 
            VariablesCaja.vVuelto = lblVuelto.getText().trim();
            procesar();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            eventoEscape();
        }
    }

    private void cerrarVentana(boolean pAceptar){
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void limpiarVariables(){
        VariablesCaja.vNumPedVta = "";
        VariablesCaja.vIndPedidoSeleccionado = "N";
        VariablesCaja.vIndTotalPedidoCubierto = false;
        VariablesCaja.vIndPedidoCobrado = false;
        VariablesCaja.vIndPedidoConProdVirtual = false;
        VariablesCaja.vIndPedidoConvenio = "";
        VariablesConvenio.vCodCliente = "";
        VariablesConvenio.vCodConvenio = "" ;
        VariablesConvenio.vCodCliente = "" ; 
        VariablesConvenio.vValCredDis = 0.00;
        VariablesCaja.cobro_Pedido_Conv_Credito = "N";
        VariablesCaja.valorCredito_de_PedActual = 0.0; 
        VariablesCaja.arrayDetFPCredito = new ArrayList();
        VariablesCaja.monto_forma_credito_ingresado = "0.00";
        VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";
        VariablesCaja.usoConvenioCredito = "";
        VariablesConvenio.vIndSoloCredito = "" ;
        VariablesCaja.vValEfectivo="";
        VariablesCaja.vVuelto="";
        VariablesCaja.vIndTotalPedidoCubierto = false;
        VariablesCaja.vIndPedidoCobrado = false;
        VariablesVirtual.vCodigoComercio = "";
        VariablesVirtual.vTipoTarjeta = "";
        VariablesVirtual.vMonto = "";
        VariablesVirtual.vNumTerminal = "";
        VariablesVirtual.vNumSerie = "";
        VariablesVirtual.vNumTrace = "";
        VariablesVirtual.vIPHost = "";
        VariablesVirtual.vPuertoHost = "";
        VariablesVirtual.vNumeroCelular = "";
        VariablesVirtual.vCodigoProv = "";
        VariablesVirtual.vCodigoAprobacion = "";
        VariablesVirtual.vNumeroTarjeta = "";
        VariablesVirtual.vNumeroPin = "";
        VariablesVirtual.vCodigoRespuesta = "";
        VariablesVirtual.vDescripcionRespuesta = "";
        VariablesVirtual.vArrayList_InfoProdVirtual.clear();
        VariablesVirtual.vNumTraceOriginal = "";
        VariablesVirtual.vCodAprobacionOriginal = "";
        VariablesVirtual.vFechaTX = "";
        VariablesVirtual.vHoraTX = "";      
        VariablesInvDiario.vNumPedido = "";      
    }


    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void limpiarDatos() {
        lblTipoComprobante.setText("");
        lblSoles.setText("");
        lblDolares.setText("");
        txtNroPedido.setText("");

        lblSaldo.setText("0.00");
        lblVuelto.setText("0.00");
        VariablesCaja.vIndPedidoSeleccionado = "N";
        VariablesCaja.vIndTotalPedidoCubierto = false;
        VariablesCaja.vIndPedidoCobrado = false;
        lblMsjPedVirtual.setVisible(false);
        lblMsjNumRecarga.setVisible(false);
        VariablesCaja.vIndPedidoConProdVirtual = false;
        VariablesCaja.vIndPedidoConvenio = "";
        VariablesConvenio.vCodCliente = "";
        VariablesConvenio.vCodConvenio = "";
        VariablesConvenio.vCodCliente = "";
        VariablesConvenio.vValCredDis = 0.00;
        /**
     * VAriables usadas para Convenio Tipo Credito
     * @author dubilluz
     * @since  08.09.2007
     */
        VariablesCaja.cobro_Pedido_Conv_Credito = "N";
        VariablesCaja.valorCredito_de_PedActual = 0.0;
        VariablesCaja.arrayDetFPCredito = new ArrayList();
        VariablesCaja.monto_forma_credito_ingresado = "0.00";
        VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";
        VariablesCaja.usoConvenioCredito = "";
        VariablesConvenio.vIndSoloCredito = "";

        /**
     * Para mostrar datos en ticket
     * @author JCORTEZ
     * @since 27.03.09
     * */
        VariablesCaja.vValEfectivo = "";
        VariablesCaja.vVuelto = "";
    }

    private void limpiarPagos() {

        tableModelDetallePago.clearTable();
        lblSaldo.setText(lblSoles.getText().trim());
        lblVuelto.setText("0.00");
        VariablesCaja.vIndTotalPedidoCubierto = false;
        VariablesCaja.vIndPedidoCobrado = false;
        verificaMontoPagadoPedido();
        indBorra = false;


    }

    private void verificaMontoPagadoPedido() {
        System.out.println("tblDetallePago.getRowCount(): " + 
                           tblDetallePago.getRowCount());
        System.out.println("vIndTotalPedidoCubierto: " + 
                           VariablesCaja.vIndTotalPedidoCubierto);
        if (tblDetallePago.getRowCount() <= 0)
            return;
        double montoTotal = 0;
        double montoFormaPago = 0;
        for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
            montoFormaPago = 
                    FarmaUtility.getDecimalNumber(((String)tblDetallePago.getValueAt(i, 
                                                                                     5)).trim());
            montoTotal = montoTotal + montoFormaPago;
        }
        System.out.println("VariablesCaja.vValTotalPagar=" + 
                           VariablesCaja.vValTotalPagar);
        System.out.println("montoTotal=" + montoTotal);
        if (FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) > 
            montoTotal) {
            System.out.println("No Cubierto");
            VariablesCaja.vIndTotalPedidoCubierto = false;
            VariablesCaja.vSaldoPedido = 
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) - 
                                              montoTotal);
            VariablesCaja.vValVueltoPedido = "0.00";
        } else {
            System.out.println("Cubierto");
            VariablesCaja.vIndTotalPedidoCubierto = true;
            VariablesCaja.vSaldoPedido = "0.00";
            VariablesCaja.vValVueltoPedido = 
                    FarmaUtility.formatNumber(montoTotal - 
                                              FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar));
        }
        System.out.println("VariablesCaja.vSaldoPedido :" + 
                           VariablesCaja.vSaldoPedido);
        System.out.println("VariablesCaja.vValVueltoPedido :" + 
                           VariablesCaja.vValVueltoPedido);
        lblSaldo.setText(VariablesCaja.vSaldoPedido);
        lblVuelto.setText(VariablesCaja.vValVueltoPedido);
    }

    private void limpiaVariablesFormaPago() {
        VariablesCaja.vCodFormaPago = "";
        VariablesCaja.vDescFormaPago = "";
        VariablesCaja.vDescMonedaPago = "";
        VariablesCaja.vValMontoPagado = "";
        VariablesCaja.vValTotalPagado = "";
        System.out.println("************************LimpiaVariablesFormaPago***********************");
    }

    private void cobrarPedido() {
        VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_S;
        DlgProcesaCobroInvDiario dlgProcesarCobro =
            new DlgProcesaCobroInvDiario(myParentFrame, "", true, tblDetallePago,
                                 lblVuelto, tblDetallePago, txtNroPedido);
        dlgProcesarCobro.setVisible(true);
        if (!FarmaVariables.vAceptar) {
            if (VariablesCaja.vCierreDiaAnul) {
                VariablesCaja.vCierreDiaAnul = false;
            }
        }

    }

    private boolean obtieneIndCajaAbierta_ForUpdate(String pSecMovCaja) {
        boolean cajaAbierta = false;
        String indCajaAbierta = "";
        try {
            indCajaAbierta = 
                    DBCaja.obtieneIndCajaAbierta_ForUpdate(pSecMovCaja);
            if (indCajaAbierta.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                cajaAbierta = true;
            return cajaAbierta;
        } catch (SQLException sqlException) {
            //sqlException.printStackTrace();
            log.error(null, sqlException);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener la fecha de movimiento de caja.", 
                                     tblDetallePago);
            return false;
        }
    }


    private void limpiaVariablesVirtuales() {
        VariablesVirtual.vCodigoComercio = "";
        VariablesVirtual.vTipoTarjeta = "";
        VariablesVirtual.vMonto = "";
        VariablesVirtual.vNumTerminal = "";
        VariablesVirtual.vNumSerie = "";
        VariablesVirtual.vNumTrace = "";
        VariablesVirtual.vIPHost = "";
        VariablesVirtual.vPuertoHost = "";
        VariablesVirtual.vNumeroCelular = "";
        VariablesVirtual.vCodigoProv = "";
        VariablesVirtual.vCodigoAprobacion = "";
        VariablesVirtual.vNumeroTarjeta = "";
        VariablesVirtual.vNumeroPin = "";
        VariablesVirtual.vCodigoRespuesta = "";
        VariablesVirtual.vDescripcionRespuesta = "";
        VariablesVirtual.vArrayList_InfoProdVirtual.clear();
        VariablesVirtual.vNumTraceOriginal = "";
        VariablesVirtual.vCodAprobacionOriginal = "";
        VariablesVirtual.vFechaTX = "";
        VariablesVirtual.vHoraTX = "";
    }

    private void txtNroPedido_actionPerformed(ActionEvent e) {
    }

    private void procesar() {
        log.debug("*Cobro de Pedido Generado en Toma de Inv Diario");
        //JCORTEZ 02.07.2008 la generacion de cupones no aplica convenios

        if (tblDetallePago.getRowCount() > 0) {
            cobrarPedido(); //procesar cobro de pedido
        }

        pedidoCobrado();
    }

    private void pedidoCobrado() {

        if (VariablesCaja.vIndPedidoCobrado) {
            log.info("pedido cobrado !");
            if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) && 
                indCerrarPantallaCobrarPed) {
                limpiarVariables();
                cerrarVentana(true);
            }
            else{
                FarmaUtility.moveFocus(txtNroPedido);    
            }
        }

    }

    private void eventoEscape() {
        cerrarVentana(false);
        limpiarVariables();
    }


    private void colocaFormaPagoPedido(String pNumPedido) {
        try {
            DBInvDiario.cargaFormaPagoPedidoInvDiario(tableModelDetallePago.data, 
                                                      pNumPedido);
            tableModelDetallePago.fireTableDataChanged();
        } catch (SQLException ex) {
            log.error(null, ex);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener forma del Pedido.\n" +
                    ex.getMessage(), tblDetallePago);
        }
    }

    private void buscaPedido(String pNumPedido) {
        ArrayList myArray = new ArrayList();
        try {
            DBInvDiario.obtieneInfoCobrarPedido(myArray, pNumPedido);
            validaInfoPedido(myArray);
        } catch (SQLException sql) {
            log.error(null, sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener Informacion del Pedido.\n" +
                    sql.getMessage(), txtNroPedido);
        }
    }

    private void validaInfoPedido(ArrayList pArrayList) {
        if (pArrayList.size() < 1) {
            FarmaUtility.showMessage(this, 
                                     "El Pedido No existe o No se encuentra pendiente de pago", 
                                     txtNroPedido);
            limpiarDatos();
            limpiarPagos();
            return;
        } else if (pArrayList.size() > 1) {
            FarmaUtility.showMessage(this, "Se encontró más de un pedido.\n" +
                    "Pónganse en contacto con el área de Sistemas.", 
                    txtNroPedido);
            VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_N;
            limpiarDatos();
            limpiarPagos();
            return;
        } else {
            muestraInfoPedido(pArrayList);
            verificaMontoPagadoPedido();
        }
    }

    private void muestraInfoPedido(ArrayList pArrayList) {
        VariablesCaja.vNumPedVta = 
                ((String)((ArrayList)pArrayList.get(0)).get(0)).trim();
        log.info("Pedido cargado para Cobrar: " + VariablesCaja.vNumPedVta);
        VariablesCaja.vValTotalPagar = 
                ((String)((ArrayList)pArrayList.get(0)).get(1)).trim();
        lblSoles.setText(VariablesCaja.vValTotalPagar);
        String valDolares = 
            ((String)((ArrayList)pArrayList.get(0)).get(2)).trim();
        valDolares = 
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(valDolares) + 
                                          FarmaUtility.getRedondeo(FarmaUtility.getDecimalNumber(valDolares)));
        lblDolares.setText(valDolares);
        VariablesCaja.vValTipoCambioPedido = 
                ((String)((ArrayList)pArrayList.get(0)).get(3)).trim();
        lblTipoCambio.setText(VariablesCaja.vValTipoCambioPedido);
        VariablesVentas.vTip_Comp_Ped = 
                ((String)((ArrayList)pArrayList.get(0)).get(4)).trim();
        lblTipoComprobante.setText(((String)((ArrayList)pArrayList.get(0)).get(5)).trim());
        VariablesVentas.vNom_Cli_Ped = 
                ((String)((ArrayList)pArrayList.get(0)).get(6)).trim();
        VariablesVentas.vRuc_Cli_Ped = 
                ((String)((ArrayList)pArrayList.get(0)).get(7)).trim();
        VariablesVentas.vDir_Cli_Ped = 
                ((String)((ArrayList)pArrayList.get(0)).get(8)).trim();
        VariablesVentas.vTipoPedido = 
                ((String)((ArrayList)pArrayList.get(0)).get(9)).trim();

        lblSaldo.setText(VariablesCaja.vValTotalPagar);
        VariablesCaja.vIndDistrGratuita = 
                ((String)((ArrayList)pArrayList.get(0)).get(11)).trim();
        VariablesCaja.vIndDeliveryAutomatico = 
                ((String)((ArrayList)pArrayList.get(0)).get(12)).trim();
        VariablesVentas.vCant_Items_Ped = 
                ((String)((ArrayList)pArrayList.get(0)).get(13)).trim();
        VariablesCaja.vIndPedidoConvenio = 
                ((String)((ArrayList)pArrayList.get(0)).get(14)).trim();
        VariablesConvenio.vCodConvenio = 
                ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
        VariablesConvenio.vCodCliente = 
                ((String)((ArrayList)pArrayList.get(0)).get(16)).trim();
    }

    public void setIndPedirLogueo(boolean pValor) {
        this.indPedirLogueo = pValor;
    }

    public void setIndPantallaCerrarAnularPed(boolean pValor) {
        this.indCerrarPantallaAnularPed = pValor;
    }

    public void setIndPantallaCerrarCobrarPed(boolean pValor) {
        this.indCerrarPantallaCobrarPed = pValor;
    }

}
