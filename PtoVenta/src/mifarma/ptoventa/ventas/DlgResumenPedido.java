package mifarma.ptoventa.ventas;


import com.gs.mifarma.TestKeyEvents;
import com.gs.mifarma.TestKeyEvents.Key;
import com.gs.mifarma.TestKeyEvents.KeySequence;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import farmapuntos.bean.TarjetaBean;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgProcesar;
import mifarma.ptoventa.FrmEconoFar;
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.caja.DlgIngresoCompManual;
import mifarma.ptoventa.caja.DlgNuevoCobro;
import mifarma.ptoventa.caja.DlgSeleccionTipoComprobante;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.campAcumulada.DlgListaCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.campana.reference.DBCampana;
import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.cliente.reference.VariablesCliente;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.UtilityConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.DlgListaPantallaBTLMF;
import mifarma.ptoventa.convenioBTLMF.DlgMensajeRetencion;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.DlgListaClientes;
import mifarma.ptoventa.delivery.DlgUltimosPedidos;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.fidelizacion.reference.AuxiliarFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.puntos.DlgMensajeBienvenida;
import mifarma.ptoventa.puntos.reference.DBPuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesReceta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgResumenPedido.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      29.12.2005   Creación<br>
 * PAULO       28.04.2006   Modificacion<br>
 * ASOSA        17.02.2010 Modificacion <br>
 * ERIOS       03.07.2013   Modificacion<br>
 * ASOSA       03.07.2014   Modificacion <br>
 * LTAVARA     28.08.2014  Modificacion <br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 */

public class DlgResumenPedido extends JDialog {

    private double diferencia = 0;
    private String valor = "";


    private static final Logger log = LoggerFactory.getLogger(DlgResumenPedido.class);

    private String fechaPedido = "";

    //flag que controla la toma del pedido
    private boolean pedidoGenerado = false;

    /** Almacena el Objeto Frame de la Aplicación - Ventana Principal */
    private Frame myParentFrame;

    /** Almacena el Objeto TableModel de los Productos del Pedido */
    private FarmaTableModel tableModelResumenPedido;

    /**
     * Columnas de la grilla
     * @author Edgar Rios Navarro
     * @since 10.04.2008
     */
    /* Resumen Pedido */
    //JCORTEZ 17.04.08
    private final int COL_RES_DSCTO = 5;
    private final int COL_RES_VAL_FRAC = 10;
    private final int COL_RES_ORIG_PROD = 19;
    private final int COL_RES_IND_PACK = 20;
    private final int COL_RES_DSCTO_2 = 21;
    private final int COL_RES_IND_TRAT = 22;
    private final int COL_RES_CANT_XDIA = 23;
    private final int COL_RES_CANT_DIAS = 24;
    private final int COL_RES_CUPON = 25;

    // DUBILLUZ 09.07.2008
    private final int COL_COD_CAMPANA = 0;
    private final int COL_TIPO_CAMPANA = 1;
    private final int COL_IND_MENSAJE_CAMPANA = 2;

    // COLUMNAS DE RESULATADO PARA PROCESAR CAMPAÑAS CUPON
    private final int P_COL_COD_PROD = 0;
    private final int P_COL_COD_CAMPANA = 1;
    private final int P_COL_PRIORIDAD = 2;
    private final int P_COL_VALOR_CUPON = 3;
    private final int P_COL_TIPO_CUPON = 4;


    /**
     * Flag para determinar el estado de generacion del pedido.
     * @author Edgar Rios Navarro
     * @since 14.04.2008
     */
    private boolean pedidoEnProceso = false;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel pnlTotalesD = new JPanel();
    private XYLayout xYLayout6 = new XYLayout();
    private JLabel lblTotalD = new JLabel();
    private JLabel lblTotalS = new JLabel();
    private JLabel lblTotalDT = new JLabel();
    private JLabel lblTotalST = new JLabel();
    private JLabel lblRedondeoT = new JLabel();
    private JLabel lblRedondeo = new JLabel();
    private JLabel lblCreditoT = new JLabel();
    private JLabel lblCredito = new JLabel();
    private JLabel lblPorPagarT = new JLabel();
    private JLabel lblPorPagar = new JLabel();
    private JPanel pnlTotalesT = new JPanel();
    private XYLayout xYLayout5 = new XYLayout();
    private JLabel lblDsctoPorc = new JLabel();
    private JLabel lblTotalesT = new JLabel();
    private JLabel lblBrutoT = new JLabel();
    private JLabel lblBruto = new JLabel();
    private JLabel lblDsctoT = new JLabel();
    private JLabel lblDscto = new JLabel();
    private JLabel lblIGVT = new JLabel();
    private JLabel lblIGV = new JLabel();
    private JScrollPane scrProductos = new JScrollPane();
    private JPanel pnlProductos = new JPanel();
    private XYLayout xYLayout2 = new XYLayout();
    private JButton btnRelacionProductos = new JButton();
    private JLabel lblItemsT = new JLabel();
    private JLabel lblItems = new JLabel();
    private JPanel pnlAtencion = new JPanel();
    private XYLayout xYLayout4 = new XYLayout();
    private JLabel lblUltimoPedido = new JLabel();
    private JLabel lblUltimoPedidoT = new JLabel();
    private JLabel lblVendedor = new JLabel();
    private JLabel lblNombreVendedor = new JLabel();
    private JLabel lblTipoCambio = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel lblTipoCambioT = new JLabel();
    private JLabel lblFechaT = new JLabel();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction(); //LTAVARA 28.08.2014
    private JTable tblProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblCliente_T = new JLabelWhite();

    private JLabelWhite lblLCredito_T = new JLabelWhite();

    private JLabelWhite lblBeneficiario_T = new JLabelWhite();


    private JLabelWhite lblCliente = new JLabelWhite();

    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();

    private int CON_COLUM_COD_PROD_REGALO = 2;
    private int CON_COLUM_MONT_MIN_ENCARTE = 3;
    private int CON_COLUM_CANT_MAX_PROD_REGALO = 4;

    private int CON_COLUM_COD_CUPON = 1;
    private int CON_COLUM_DESC_CUPON = 2;
    private int CON_COLUM_MONT_CUPON = 3;
    private int CON_COLUM_CANT_CUPON = 4;

    //jquispe 22.04.2010 cambio para leer cod de barra
    private final int DIG_PROD = 6;
    private final int COL_COD = 1;

    private JLabel lblMensajeCupon = new JLabel();
    private JPanel pnlAtencion1 = new JPanel();
    private XYLayout xYLayout7 = new XYLayout();
    private JLabel lblUltimoPedido1 = new JLabel();
    private JLabel lblUltimoPedidoT1 = new JLabel();
    private JLabel lblVendedor1 = new JLabel();
    private JLabel lblNombreVendedor1 = new JLabel();
    private JLabel lblTipoCambio1 = new JLabel();
    private JLabel lblFecha1 = new JLabel();
    private JLabel lblTipoCambioT1 = new JLabel();
    private JLabel lblFechaT1 = new JLabel();
    private JTextFieldSanSerif txtDescProdOculto = new JTextFieldSanSerif();

    private boolean varNumero = false;
    private JLabelWhite lblProdOculto_T = new JLabelWhite();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JTextArea txtMensajesPedido = new JTextArea();
    private JScrollPane jScrollPane1 = new JScrollPane();

    private JLabel jLabel1 = new JLabel();
    private JLabel lblCuponIngr = new JLabel();
    private JPanel pnlTotalesT1 = new JPanel();
    private XYLayout xYLayout8 = new XYLayout();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();

    private boolean vEjecutaAccionTeclaResumen = false;
    private JLabel lblDNI_Anul = new JLabel();
    private JLabel lblTopeAhoro = new JLabel();
    private JLabelFunction lblF7 = new JLabelFunction();

    Border border = LineBorder.createGrayLineBorder();
    private JLabel lblFormaPago = new JLabel();
    private boolean pasoTarjeta = false;
    private JLabel lblDNI_SIN_COMISION = new JLabel();
    private JLabel lblMedico = new JLabel();
    //private FacadeRecetario facadeRecetario = new FacadeRecetario();
    private FrmEconoFar frmEconoFar;
    private boolean vIndPedidosDelivery;

    //kmoncada 16.07.2014 variable para la combinacion de teclas.
    private int contarCombinacion = 0;

    //Dubilluz 13.10.2014
    private boolean pIngresoComprobanteManual = false;
    
    //KMONCADA 2015.03.19
    private FarmaTableModel tableModelResumenPedidoAux;
    private ArrayList lstAuxResumenPedido = new ArrayList();
    private boolean actuaRobot = false;
    private String numPedVtaRobot = "";
    private String cadenaTex = "";
    

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */
    public DlgResumenPedido() {
        this(null, "", false, false);
    }

    /**
     *Constructor
     *@param parent Objeto Frame de la Aplicación.
     *@param title Título de la Ventana.
     *@param modal Tipo de Ventana.
     */
    public DlgResumenPedido(Frame parent, String title, boolean modal, boolean vRegulaManual) {
        super(parent, title, modal);
        myParentFrame = parent;
        pIngresoComprobanteManual = vRegulaManual;
        try {
            jbInit();
            initialize();
            //      lblItems.setText("0");
            lblBruto.setText("0.00");
            lblDscto.setText("0.00");
            lblIGV.setText("0.00");
            lblTotalS.setText("0.00");
            lblTotalD.setText("0.00");
            lblCuponIngr.setText("");
            lblMensajeCupon.setText("");
            lblCreditoT.setText("");
            lblCredito.setText("");
            lblPorPagarT.setText("");
            lblPorPagar.setText("");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(744, 589));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Resumen de Pedido" + " /  IP : " + FarmaVariables.vIpPc);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBounds(new Rectangle(10, 10, 744, 583));
        this.setBackground(Color.white);
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
        jContentPane.setSize(new Dimension(742, 423));
        pnlTotalesD.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesD.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTotalesD.setLayout(xYLayout6);
        pnlTotalesD.setBounds(new Rectangle(10, 340, 715, 35));
        pnlTotalesD.setBackground(new Color(43, 141, 39));
        lblTotalD.setText("9,990.00");
        lblTotalD.setFont(new Font("SansSerif", 1, 14));
        lblTotalD.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalD.setForeground(Color.white);
        lblTotalS.setText("99,990.00");
        lblTotalS.setFont(new Font("SansSerif", 1, 14));
        lblTotalS.setForeground(Color.white);
        lblTotalS.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalDT.setText("US$");
        lblTotalDT.setFont(new Font("SansSerif", 1, 14));
        lblTotalDT.setForeground(Color.white);
        lblTotalST.setText("TOTAL : S/.");
        lblTotalST.setFont(new Font("SansSerif", 1, 14));
        lblTotalST.setForeground(Color.white);
        lblRedondeoT.setText("Red. S/.");
        lblRedondeoT.setFont(new Font("SansSerif", 1, 14));
        lblRedondeoT.setForeground(Color.white);
        lblRedondeo.setText("-0.00");
        lblRedondeo.setFont(new Font("SansSerif", 1, 14));
        lblRedondeo.setForeground(Color.white);
        lblCreditoT.setText("Crédito(100%): S/.");

        //lblCreditoT.setVisible(false);
        lblCreditoT.setFont(new Font("SansSerif", 1, 14));
        lblCreditoT.setForeground(new Color(43, 141, 39));
        lblCreditoT.setBounds(new Rectangle(225, 315, 160, 20));
        lblCredito.setText("100.00");
        //lblCredito.setVisible(false);
        lblCredito.setFont(new Font("SansSerif", 1, 14));
        lblCredito.setForeground(new Color(43, 141, 39));
        lblCredito.setBounds(new Rectangle(390, 315, 75, 20));
        lblPorPagarT.setText("CoPago: S/.");
        //lblPorPagarT.setVisible(false);
        lblPorPagarT.setFont(new Font("SansSerif", 1, 14));
        lblPorPagarT.setForeground(Color.white);
        lblPorPagar.setText("99.99");
        //lblPorPagar.setVisible(false);
        lblPorPagar.setFont(new Font("SansSerif", 1, 14));
        lblPorPagar.setForeground(Color.white);
        pnlTotalesT.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesT.setBackground(new Color(255, 130, 14));
        pnlTotalesT.setLayout(xYLayout5);
        pnlTotalesT.setBounds(new Rectangle(495, 315, 230, 25));
        lblDsctoPorc.setText("(00.00%)");
        lblDsctoPorc.setFont(new Font("SansSerif", 1, 12));
        lblDsctoPorc.setForeground(Color.white);
        lblTotalesT.setText("Totales :");
        lblTotalesT.setFont(new Font("SansSerif", 1, 12));
        lblTotalesT.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotalesT.setForeground(Color.white);
        lblBrutoT.setText("Bruto :");
        lblBrutoT.setFont(new Font("SansSerif", 1, 12));
        lblBrutoT.setForeground(Color.white);
        lblBruto.setText("99,990.00");
        lblBruto.setFont(new Font("SansSerif", 1, 12));
        lblBruto.setForeground(Color.white);
        lblBruto.setHorizontalAlignment(SwingConstants.LEFT);
        lblDsctoT.setText("Ud. ha ahorrado S/.");
        lblDsctoT.setFont(new Font("SansSerif", 1, 12));
        lblDsctoT.setForeground(new Color(255, 114, 48));
        lblDscto.setText("999999.00  ");
        lblDscto.setFont(new Font("SansSerif", 1, 12));
        lblDscto.setForeground(new Color(255, 124, 37));
        lblDscto.setHorizontalAlignment(SwingConstants.LEFT);
        //lblDscto.setMaximumSize(new Dimension(100, 100));
        //lblDscto.setSize(new Dimension(500, 25));
        lblIGVT.setText("I.G.V. :");
        lblIGVT.setFont(new Font("SansSerif", 1, 12));
        lblIGVT.setForeground(Color.white);
        lblIGV.setText("9,990.00");
        lblIGV.setFont(new Font("SansSerif", 1, 12));
        lblIGV.setForeground(Color.white);
        lblIGV.setHorizontalAlignment(SwingConstants.LEFT);
        scrProductos.setFont(new Font("SansSerif", 0, 12));
        scrProductos.setBounds(new Rectangle(10, 105, 715, 210));
        scrProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setFont(new Font("SansSerif", 0, 12));
        pnlProductos.setLayout(xYLayout2);
        pnlProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setBounds(new Rectangle(10, 80, 715, 25));
        btnRelacionProductos.setText("Relacion de Productos :");
        btnRelacionProductos.setFont(new Font("SansSerif", 1, 11));
        btnRelacionProductos.setForeground(Color.white);
        btnRelacionProductos.setBackground(new Color(255, 130, 14));
        btnRelacionProductos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRelacionProductos.setHorizontalAlignment(SwingConstants.LEFT);
        btnRelacionProductos.setRequestFocusEnabled(false);
        btnRelacionProductos.setMnemonic('r');
        btnRelacionProductos.setBorderPainted(false);
        btnRelacionProductos.setContentAreaFilled(false);
        btnRelacionProductos.setDefaultCapable(false);
        btnRelacionProductos.setFocusPainted(false);
        btnRelacionProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        lblItemsT.setText("items");
        lblItemsT.setFont(new Font("SansSerif", 1, 11));
        lblItemsT.setForeground(Color.white);
        lblItems.setText("0");
        lblItems.setFont(new Font("SansSerif", 1, 11));
        lblItems.setForeground(Color.white);
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlAtencion.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion.setLayout(xYLayout4);
        pnlAtencion.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion.setBackground(new Color(43, 141, 39));
        pnlAtencion.setBounds(new Rectangle(10, 45, 715, 30));
        lblUltimoPedido.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedido.setForeground(Color.white);
        lblUltimoPedidoT.setText("Ult. Pedido :");
        lblUltimoPedidoT.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedidoT.setForeground(Color.white);
        lblVendedor.setText("Vendedor :");
        lblVendedor.setFont(new Font("SansSerif", 0, 11));
        lblVendedor.setForeground(Color.white);
        lblNombreVendedor.setFont(new Font("SansSerif", 1, 11));
        lblNombreVendedor.setForeground(Color.white);
        lblTipoCambio.setFont(new Font("SansSerif", 1, 11));
        lblTipoCambio.setForeground(Color.white);
        lblFecha.setFont(new Font("SansSerif", 1, 11));
        lblFecha.setForeground(Color.white);
        lblTipoCambioT.setText("Tipo Cambio :");
        lblTipoCambioT.setFont(new Font("SansSerif", 0, 11));
        lblTipoCambioT.setForeground(Color.white);
        lblFechaT.setText("Fecha :");
        lblFechaT.setFont(new Font("SansSerif", 0, 11));
        lblFechaT.setForeground(Color.white);
        lblF3.setText("<HTML><CENTER>[F3] Más<BR>Productos</CENTER></HTML>");
        lblF3.setBounds(new Rectangle(167, 400, 70, 35));
        lblEnter.setText("[ ENTER ]  Cambiar Cantidad");
        lblEnter.setBounds(new Rectangle(445, 445, 180, 20));
        lblF5.setText("<HTML><CENTER>[F5] Borrar<BR>Producto</CENTER></HTML>");
        lblF5.setBounds(new Rectangle(246, 400, 70, 35));
        lblF1.setText("<HTML><CENTER>[F1]<BR>Boleta</CENTER></HTML>");
        lblF1.setBounds(new Rectangle(10, 400, 70, 35));
        lblF2.setText("<HTML><CENTER>[F4]<BR>Factura</CENTER></HTML>");
        lblF2.setBounds(new Rectangle(89, 400, 70, 35));
        lblF8.setText("<HTML><CENTER>[F8] Dcto<BR>Receta</CENTER></HTML>");
        lblF8.setBounds(new Rectangle(481, 400, 70, 35));
        tblProductos.setFont(new Font("SansSerif", 0, 12));
        tblProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblProductos_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(630, 445, 90, 20));
        pnlTitle1.setBounds(new Rectangle(10, 10, 715, 30));
        lblCliente_T.setText("Cliente:");
        lblCliente_T.setBounds(new Rectangle(15, 5, 55, 20));
        lblCliente_T.setFont(new Font("SansSerif", 1, 14));
        lblCliente.setBounds(new Rectangle(75, 5, 315, 20));
        lblCliente.setFont(new Font("SansSerif", 1, 14));
        lblF9.setBounds(new Rectangle(560, 400, 70, 35));
        lblF9.setText("<HTML><CENTER>[F9] Camp. Acumulada</CENTER></HTML>");
        lblF12.setBounds(new Rectangle(640, 400, 80, 35));
        lblF12.setText("<HTML><CENTER>[F12] Buscar X DNI</CENTER></HTML>");
        lblMensajeCupon.setText("[MENSAJE CUPON]");
        lblMensajeCupon.setFont(new Font("SansSerif", 1, 11));
        lblMensajeCupon.setBounds(new Rectangle(115, 380, 605, 15));
        lblMensajeCupon.setBackground(new Color(232, 236, 230));
        lblMensajeCupon.setForeground(new Color(230, 23, 39));
        pnlAtencion1.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion1.setLayout(xYLayout7);
        pnlAtencion1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion1.setBackground(new Color(43, 141, 39));
        lblUltimoPedido1.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedido1.setForeground(Color.white);
        lblUltimoPedidoT1.setText("Ult. Pedido :");
        lblUltimoPedidoT1.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedidoT1.setForeground(Color.white);
        lblVendedor1.setText("Vendedor :");
        lblVendedor1.setFont(new Font("SansSerif", 0, 11));
        lblVendedor1.setForeground(Color.white);
        lblNombreVendedor1.setFont(new Font("SansSerif", 1, 11));
        lblNombreVendedor1.setForeground(Color.white);
        lblTipoCambio1.setFont(new Font("SansSerif", 1, 11));
        lblTipoCambio1.setForeground(Color.white);
        lblFecha1.setFont(new Font("SansSerif", 1, 11));
        lblFecha1.setForeground(Color.white);
        lblTipoCambioT1.setText("Tipo Cambio :");
        lblTipoCambioT1.setFont(new Font("SansSerif", 0, 11));
        lblTipoCambioT1.setForeground(Color.white);
        lblFechaT1.setText("Fecha :");
        lblFechaT1.setFont(new Font("SansSerif", 0, 11));
        lblFechaT1.setForeground(Color.white);
        txtDescProdOculto.setBounds(new Rectangle(65, 5, 250, 20));
        txtDescProdOculto.setFont(new Font("SansSerif", 1, 11));
        txtDescProdOculto.setForeground(new Color(32, 105, 29));
        txtDescProdOculto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescProdOculto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDescProdOculto_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtDescProdOculto_keyTyped(e);
            }
        });
        txtDescProdOculto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtDescProdOculto_actionPerformed(e);
            }
        });
        txtDescProdOculto.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtDescProdOculto_focusLost(e);
            }
        });
        lblProdOculto_T.setText("Producto:");
        lblProdOculto_T.setBounds(new Rectangle(5, 5, 60, 20));
        lblF6.setText("<HTML><CENTER>[F6]<BR>Promociones</CENTER></HTML>");
        lblF6.setBounds(new Rectangle(324, 400, 70, 35));
        txtMensajesPedido.setCaretColor(new Color(7, 133, 7));
        txtMensajesPedido.setEditable(false);
        jScrollPane1.setBounds(new Rectangle(10, 475, 720, 55));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jLabel1.setText("Opciones");
        jLabel1.setBounds(new Rectangle(15, 375, 70, 20));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        lblCuponIngr.setText("[MENSAJE CUPON]");
        lblCuponIngr.setFont(new Font("SansSerif", 1, 12));
        lblCuponIngr.setBounds(new Rectangle(10, 445, 430, 25));
        lblCuponIngr.setToolTipText("null");
        pnlTotalesT1.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesT1.setBackground(Color.white);
        pnlTotalesT1.setLayout(xYLayout8);
        pnlTotalesT1.setBounds(new Rectangle(10, 315, 185, 25));


        jPanelHeader1.setBounds(new Rectangle(320, 0, 395, 30));
        lblDNI_Anul.setBackground(Color.white);
        lblDNI_Anul.setFont(new Font("Dialog", 1, 14));
        lblDNI_Anul.setForeground(Color.red);
        lblDNI_Anul.setVisible(false);
        lblTopeAhoro.setForeground(Color.red);
        lblTopeAhoro.setFont(new Font("Dialog", 1, 12));
        lblF7.setBounds(new Rectangle(403, 400, 70, 35));
        lblF7.setText("<HTML><CENTER>[F7]<BR>Info Prod</CENTER></HTML>");
        //lblFormaPago.setOpaque(true);
        //lblFormaPago.setText("kokokokokoko okokoko okokok ooko okokoko okoko");
        lblFormaPago.setFont(new Font("SansSerif", 1, 12));
        lblFormaPago.setForeground(Color.red);
        lblFormaPago.setVisible(false);

        lblDNI_SIN_COMISION.setText("DNI Inválido. No aplica Prog. Atención al Cliente");
        lblDNI_SIN_COMISION.setForeground(new Color(231, 0, 0));
        lblDNI_SIN_COMISION.setFont(new Font("Dialog", 3, 14));
        lblDNI_SIN_COMISION.setBackground(Color.white);
        lblDNI_SIN_COMISION.setOpaque(true);
        lblDNI_SIN_COMISION.setVisible(false);

        lblMedico.setForeground(Color.white);
        lblMedico.setFont(new Font("Dialog", 3, 11));
        lblMedico.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        lblMedico.setVisible(false);
        pnlAtencion1.add(lblUltimoPedido1, new XYConstraints(655, 10, 40, 15));
        pnlAtencion1.add(lblUltimoPedidoT1, new XYConstraints(585, 10, 70, 15));
        pnlAtencion1.add(lblVendedor1, new XYConstraints(245, 10, 70, 15));
        pnlAtencion1.add(lblNombreVendedor1, new XYConstraints(315, 10, 245, 15));
        pnlAtencion1.add(lblTipoCambio1, new XYConstraints(205, 10, 40, 15));
        pnlAtencion1.add(lblFecha1, new XYConstraints(60, 10, 70, 15));
        pnlAtencion1.add(lblTipoCambioT1, new XYConstraints(130, 10, 80, 15));
        pnlAtencion1.add(lblFechaT1, new XYConstraints(10, 10, 50, 15));
        pnlTotalesD.add(lblTotalD, new XYConstraints(615, 9, 80, 20));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));

        pnlTotalesD.add(lblTotalD, new XYConstraints(619, 9, 80, 20));
        pnlTotalesD.add(lblTotalS, new XYConstraints(464, 9, 95, 20));
        pnlTotalesD.add(lblTotalDT, new XYConstraints(579, 9, 35, 20));
        pnlTotalesD.add(lblTotalST, new XYConstraints(379, 9, 80, 20));
        pnlTotalesD.add(lblRedondeoT, new XYConstraints(4, 9, 70, 20));
        pnlTotalesD.add(lblRedondeo, new XYConstraints(79, 9, 65, 20));

        pnlTotalesD.add(lblPorPagarT, new XYConstraints(149, 9, 100, 20));
        pnlTotalesD.add(lblPorPagar, new XYConstraints(259, 9, 100, 20));
        pnlTotalesT.add(lblDsctoPorc, new XYConstraints(545, 5, 15, 15));
        pnlTotalesT.add(lblTotalesT, new XYConstraints(495, 5, 15, 15));
        pnlTotalesT.add(lblBrutoT, new XYConstraints(510, 5, 15, 15));
        pnlTotalesT.add(lblBruto, new XYConstraints(525, 5, 15, 15));
        pnlTotalesT.add(lblIGVT, new XYConstraints(65, 5, 45, 15));
        pnlTotalesT.add(lblIGV, new XYConstraints(120, 5, 95, 15));
        scrProductos.getViewport();
        pnlProductos.add(lblMedico, new XYConstraints(230, 0, 485, 25));
        pnlProductos.add(pnlAtencion1, new XYConstraints(10, 45, 715, 35));
        pnlProductos.add(btnRelacionProductos, new XYConstraints(10, 5, 145, 15));
        pnlProductos.add(lblItemsT, new XYConstraints(185, 5, 40, 15));
        pnlProductos.add(lblItems, new XYConstraints(150, 5, 30, 15));
        pnlAtencion.add(lblDNI_SIN_COMISION, new XYConstraints(384, -1, 330, 30));
        pnlAtencion.add(lblFormaPago, new XYConstraints(454, -1, 260, 30));
        pnlAtencion.add(lblUltimoPedido, new XYConstraints(655, 5, 40, 15));
        pnlAtencion.add(lblUltimoPedidoT, new XYConstraints(584, 4, 70, 15));
        pnlAtencion.add(lblVendedor, new XYConstraints(244, 4, 60, 15));
        pnlAtencion.add(lblNombreVendedor, new XYConstraints(304, 4, 235, 15));
        pnlAtencion.add(lblTipoCambio, new XYConstraints(205, 5, 40, 15));
        pnlAtencion.add(lblFecha, new XYConstraints(60, 5, 70, 15));
        pnlAtencion.add(lblTipoCambioT, new XYConstraints(130, 5, 80, 15));
        pnlAtencion.add(lblFechaT, new XYConstraints(10, 5, 50, 15));
        jPanelHeader1.add(lblCliente_T, null);
        jPanelHeader1.add(lblCliente, null);
        pnlTitle1.add(jPanelHeader1, null);
        pnlTitle1.add(lblProdOculto_T, null);

        pnlTitle1.add(txtDescProdOculto, null);
        pnlTotalesT1.add(lblDNI_Anul, new XYConstraints(0, 0, 290, 25));
        pnlTotalesT1.add(lblDscto, new XYConstraints(120, 0, 85, 25));
        pnlTotalesT1.add(lblDsctoT, new XYConstraints(5, 5, 115, 15));
        pnlTotalesT1.add(lblTopeAhoro, new XYConstraints(195, 0, 290, 25));
        jContentPane.add(lblF7, null);
        jContentPane.add(pnlTotalesT1, null);
        jContentPane.add(jLabel1, null);
        jScrollPane1.getViewport().add(txtMensajesPedido, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblMensajeCupon, null); //LTAVARA 28.08.2014
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTotalesD, null);
        jContentPane.add(pnlTotalesT, null);
        scrProductos.getViewport().add(tblProductos, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(pnlProductos, null);
        jContentPane.add(pnlAtencion, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblCuponIngr, null);
        jContentPane.add(lblCreditoT, null);
        jContentPane.add(lblCredito, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().add(jContentPane, null);
        FarmaUtility.centrarVentana(this);
    }
    // **************************************************************************
    // Método "jbInitBTLMF()"
    // **************************************************************************

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInitBTLMF() {

        log.debug("jbInitBTLMF");
        lblF2.setVisible(false);
        lblF6.setVisible(false);
        lblF12.setVisible(false);
        lblF8.setVisible(false);
        lblF9.setVisible(false);
        
        //this.setSize(new Dimension(950, 583));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Resumen de Pedido" + " /  IP : " + FarmaVariables.vIpPc);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //this.setBounds(new Rectangle(10, 10, 765, 583));
        this.setBackground(Color.white);
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
        jContentPane.setSize(new Dimension(742, 423));
        pnlTotalesD.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesD.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTotalesD.setLayout(xYLayout6);
        //pnlTotalesD.setBounds(new Rectangle(10, 390, 740, 35));
        pnlTotalesD.setBackground(new Color(43, 141, 39));
        lblTotalD.setText("9,990.00");
        lblTotalD.setFont(new Font("SansSerif", 1, 14));
        lblTotalD.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalD.setForeground(Color.white);
        lblTotalS.setText("99,990.00");
        lblTotalS.setFont(new Font("SansSerif", 1, 14));
        lblTotalS.setForeground(Color.white);
        lblTotalS.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalDT.setText("US$");
        lblTotalDT.setFont(new Font("SansSerif", 1, 14));
        lblTotalDT.setForeground(Color.white);
        lblTotalST.setText("TOTAL : S/.");
        lblTotalST.setFont(new Font("SansSerif", 1, 14));
        lblTotalST.setForeground(Color.white);
        lblRedondeoT.setText("Red. S/.");
        lblRedondeoT.setFont(new Font("SansSerif", 1, 14));
        lblRedondeoT.setForeground(Color.white);
        lblRedondeo.setText("-0.00");
        lblRedondeo.setFont(new Font("SansSerif", 1, 14));
        lblRedondeo.setForeground(Color.white);
        lblCreditoT.setText("");

        lblCreditoT.setVisible(false);
        lblCreditoT.setFont(new Font("SansSerif", 1, 14));
        lblCreditoT.setForeground(new Color(43, 141, 39));
        //lblCreditoT.setBounds(new Rectangle(195, 360, 160, 20));
        lblCredito.setText("");
        lblCredito.setVisible(false);
        lblCredito.setFont(new Font("SansSerif", 1, 14));
        lblCredito.setForeground(new Color(43, 141, 39));
        //lblCredito.setBounds(new Rectangle(360, 360, 75, 20));
        lblPorPagarT.setText("");
        lblPorPagarT.setVisible(false);
        lblPorPagarT.setFont(new Font("SansSerif", 1, 14));
        lblPorPagarT.setForeground(Color.white);
        lblPorPagar.setText("");
        lblPorPagar.setVisible(false);
        lblPorPagar.setFont(new Font("SansSerif", 1, 14));
        lblPorPagar.setForeground(Color.white);
        lblPorPagar.setBounds(new Rectangle(525, 110, 715, 210));
        pnlTotalesT.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesT.setBackground(new Color(255, 130, 14));
        pnlTotalesT.setLayout(xYLayout5);
        //pnlTotalesT.setBounds(new Rectangle(520, 360, 230, 25));
        lblDsctoPorc.setText("(00.00%)");
        lblDsctoPorc.setFont(new Font("SansSerif", 1, 12));
        lblDsctoPorc.setForeground(Color.white);
        lblTotalesT.setText("Totales :");
        lblTotalesT.setFont(new Font("SansSerif", 1, 12));
        lblTotalesT.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotalesT.setForeground(Color.white);
        lblBrutoT.setText("Bruto :");
        lblBrutoT.setFont(new Font("SansSerif", 1, 12));
        lblBrutoT.setForeground(Color.white);
        lblBruto.setText("99,990.00");
        lblBruto.setFont(new Font("SansSerif", 1, 12));
        lblBruto.setForeground(Color.white);
        lblBruto.setHorizontalAlignment(SwingConstants.LEFT);
        lblDsctoT.setText("Ud. ha ahorrado S/.");
        lblDsctoT.setFont(new Font("SansSerif", 1, 12));
        lblDsctoT.setForeground(new Color(255, 114, 48));
        lblDsctoT.setBounds(new Rectangle(10, 370, 115, 15));
        lblDscto.setText("999999.00  ");
        lblDscto.setFont(new Font("SansSerif", 1, 12));
        lblDscto.setForeground(new Color(255, 124, 37));
        lblDscto.setHorizontalAlignment(SwingConstants.LEFT);
        //lblDscto.setMaximumSize(new Dimension(100, 100));
        //lblDscto.setSize(new Dimension(500, 25));
        lblDscto.setBounds(new Rectangle(125, 365, 85, 25));
        lblIGVT.setText("I.G.V. :");
        lblIGVT.setFont(new Font("SansSerif", 1, 12));
        lblIGVT.setForeground(Color.white);
        lblIGV.setText("9,990.00");
        lblIGV.setFont(new Font("SansSerif", 1, 12));
        lblIGV.setForeground(Color.white);
        lblIGV.setHorizontalAlignment(SwingConstants.LEFT);
        scrProductos.setFont(new Font("SansSerif", 0, 12));
        scrProductos.setBounds(new Rectangle(10, 145, 715, 170));
        scrProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setFont(new Font("SansSerif", 0, 12));
        pnlProductos.setLayout(xYLayout2);
        pnlProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setBounds(new Rectangle(10, 115, 715, 25));
        btnRelacionProductos.setText("Relacion de Productos :");
        btnRelacionProductos.setFont(new Font("SansSerif", 1, 11));
        btnRelacionProductos.setForeground(Color.white);
        btnRelacionProductos.setBackground(new Color(255, 130, 14));
        btnRelacionProductos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRelacionProductos.setHorizontalAlignment(SwingConstants.LEFT);
        btnRelacionProductos.setRequestFocusEnabled(false);
        btnRelacionProductos.setMnemonic('r');
        btnRelacionProductos.setBorderPainted(false);
        btnRelacionProductos.setContentAreaFilled(false);
        btnRelacionProductos.setDefaultCapable(false);
        btnRelacionProductos.setFocusPainted(false);
        btnRelacionProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        lblItemsT.setText("items");
        lblItemsT.setFont(new Font("SansSerif", 1, 11));
        lblItemsT.setForeground(Color.white);
        lblItems.setText("0");
        lblItems.setFont(new Font("SansSerif", 1, 11));
        lblItems.setForeground(Color.white);
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlAtencion.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion.setLayout(xYLayout4);
        pnlAtencion.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion.setBackground(new Color(43, 141, 39));
        pnlAtencion.setBounds(new Rectangle(10, 80, 715, 30));
        lblUltimoPedido.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedido.setForeground(Color.white);
        lblUltimoPedidoT.setText("Ult. Pedido :");
        lblUltimoPedidoT.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedidoT.setForeground(Color.white);
        lblVendedor.setText("Vendedor :");
        lblVendedor.setFont(new Font("SansSerif", 0, 11));
        lblVendedor.setForeground(Color.white);
        lblNombreVendedor.setFont(new Font("SansSerif", 1, 11));
        lblNombreVendedor.setForeground(Color.white);
        lblTipoCambio.setFont(new Font("SansSerif", 1, 11));
        lblTipoCambio.setForeground(Color.white);
        lblFecha.setFont(new Font("SansSerif", 1, 11));
        lblFecha.setForeground(Color.white);
        lblTipoCambioT.setText("Tipo Cambio :");
        lblTipoCambioT.setFont(new Font("SansSerif", 0, 11));
        lblTipoCambioT.setForeground(Color.white);
        lblFechaT.setText("Fecha :");
        lblFechaT.setFont(new Font("SansSerif", 0, 11));
        lblFechaT.setForeground(Color.white);
        //lblF3.setText("[ F3 ]  Agregar Prod");
        //lblF3.setBounds(new Rectangle(165, 440, 125, 20));
        //lblEnter.setText("[ ENTER ]  Cambiar Cantidad");
        //lblEnter.setBounds(new Rectangle(460, 440, 180, 20));
        //lblF5.setText("[ F5 ]  Borrar Producto");
        //lblF5.setBounds(new Rectangle(310, 440, 135, 20));
        lblF1.setText("<HTML><CENTER>[F1] Cobrar<BR>Pedido</CENTER></HTML>");
        //lblF1.setBounds(new Rectangle(10, 440, 140, 20));
        //lblF8.setText("[ F8 ] Dcto por Receta");
        //lblF8.setBounds(new Rectangle(240, 465, 140, 20));

        tblProductos.removeAll();

        tblProductos.setFont(new Font("SansSerif", 0, 12));
        tblProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblProductos_keyPressed(e);
            }
        });
        //lblEsc.setText("[ ESC ] Cerrar");
        //lblEsc.setBounds(new Rectangle(660, 440, 90, 20));
        pnlTitle1.setBounds(new Rectangle(10, 5, 715, 70));

        lblCliente_T.setText("Cliente:");
        lblCliente_T.setBounds(new Rectangle(10, 5, 55, 20));
        lblCliente_T.setFont(new Font("SansSerif", 1, 14));


        //        if(VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0)
        //        {
        lblLCredito_T.setText("");
        lblLCredito_T.setBounds(new Rectangle(10, 25, 380, 20));
        lblLCredito_T.setFont(new Font("SansSerif", 1, 14));

        lblBeneficiario_T.setText("");
        lblBeneficiario_T.setBounds(new Rectangle(10, 45, 380, 20));
        lblBeneficiario_T.setFont(new Font("SansSerif", 1, 14));
        //}

        lblCliente.setBounds(new Rectangle(75, 5, 315, 20));
        lblCliente.setFont(new Font("SansSerif", 1, 14));
        //lblF9.setBounds(new Rectangle(400, 465, 150, 20));
        lblMensajeCupon.setFont(new Font("SansSerif", 1, 11));
        //lblMensajeCupon.setBounds(new Rectangle(115, 380, 605, 15));
        lblMensajeCupon.setBackground(new Color(232, 236, 230));
        lblMensajeCupon.setForeground(new Color(230, 23, 39));
        pnlAtencion1.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion1.setLayout(xYLayout7);
        pnlAtencion1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion1.setBackground(new Color(43, 141, 39));
        lblUltimoPedido1.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedido1.setForeground(Color.white);
        lblUltimoPedidoT1.setText("Ult. Pedido :");
        lblUltimoPedidoT1.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedidoT1.setForeground(Color.white);
        lblVendedor1.setText("Vendedor :");
        lblVendedor1.setFont(new Font("SansSerif", 0, 11));
        lblVendedor1.setForeground(Color.white);
        lblNombreVendedor1.setFont(new Font("SansSerif", 1, 11));
        lblNombreVendedor1.setForeground(Color.white);
        lblTipoCambio1.setFont(new Font("SansSerif", 1, 11));
        lblTipoCambio1.setForeground(Color.white);
        lblFecha1.setFont(new Font("SansSerif", 1, 11));
        lblFecha1.setForeground(Color.white);
        lblTipoCambioT1.setText("Tipo Cambio :");
        lblTipoCambioT1.setFont(new Font("SansSerif", 0, 11));
        lblTipoCambioT1.setForeground(Color.white);
        lblFechaT1.setText("Fecha :");
        lblFechaT1.setFont(new Font("SansSerif", 0, 11));
        lblFechaT1.setForeground(Color.white);

        txtDescProdOculto.setBounds(new Rectangle(65, 25, 250, 20));
        txtDescProdOculto.setFont(new Font("SansSerif", 1, 11));
        txtDescProdOculto.setForeground(new Color(32, 105, 29));
        //        txtDescProdOculto.addKeyListener(new KeyAdapter() {
        //                    public void keyPressed(KeyEvent e) {
        //                        txtDescProdOculto_keyPressed(e);
        //                    }
        //
        //                    public void keyReleased(KeyEvent e) {
        //                        txtDescProdOculto_keyReleased(e);
        //                    }
        //
        //                    public void keyTyped(KeyEvent e) {
        //                        txtDescProdOculto_keyTyped(e);
        //                    }
        //                });
        lblProdOculto_T.setText("Producto:");
        lblProdOculto_T.setBounds(new Rectangle(5, 25, 60, 20));
        txtMensajesPedido.setCaretColor(new Color(7, 133, 7));
        txtMensajesPedido.setEditable(false);
        txtMensajesPedido.setBounds(new Rectangle(5, 515, 745, 40));
        //jScrollPane1.setBounds(new Rectangle(5, 490, 745, 65));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jLabel1.setText("Opciones");
        //jLabel1.setBounds(new Rectangle(15, 420, 70, 20));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        lblCuponIngr.setText("-");
        lblCuponIngr.setFont(new Font("SansSerif", 1, 12));
        //lblCuponIngr.setBounds(new Rectangle(165, 475, 560, 25));
        lblCuponIngr.setToolTipText("null");
        pnlTotalesT1.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesT1.setBackground(Color.white);
        pnlTotalesT1.setLayout(xYLayout8);


        //pnlTotalesT1.setBounds(new Rectangle(35, 155, 715, 210));
        jPanelHeader1.setBounds(new Rectangle(320, 0, 395, 70));
        lblDNI_Anul.setBackground(Color.white);
        lblDNI_Anul.setFont(new Font("Dialog", 1, 14));
        lblDNI_Anul.setForeground(Color.red);
        lblDNI_Anul.setVisible(false);
        lblTopeAhoro.setForeground(Color.red);
        lblTopeAhoro.setFont(new Font("Dialog", 1, 12));
        //lblF7.setBounds(new Rectangle(120, 465, 95, 20));
        //lblF7.setText("[ F7 ]  Info Prod");
        //lblFormaPago.setOpaque(true);
        lblFormaPago.setText("kokokokokoko okokoko okokok ooko okokoko okoko");
        lblFormaPago.setFont(new Font("SansSerif", 1, 12));
        lblFormaPago.setForeground(Color.red);
        lblFormaPago.setVisible(false);
        pnlAtencion1.add(lblUltimoPedido1, new XYConstraints(655, 10, 40, 15));
        pnlAtencion1.add(lblUltimoPedidoT1, new XYConstraints(585, 10, 70, 15));
        pnlAtencion1.add(lblVendedor1, new XYConstraints(245, 10, 70, 15));
        pnlAtencion1.add(lblNombreVendedor1, new XYConstraints(315, 10, 245, 15));
        pnlAtencion1.add(lblTipoCambio1, new XYConstraints(205, 10, 40, 15));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));

        pnlAtencion1.add(lblFecha1, new XYConstraints(60, 10, 70, 15));
        pnlAtencion1.add(lblTipoCambioT1, new XYConstraints(130, 10, 80, 15));
        pnlAtencion1.add(lblFechaT1, new XYConstraints(10, 10, 50, 15));
        pnlTotalesD.add(lblTotalD, new XYConstraints(615, 9, 80, 20));
        pnlTotalesD.add(lblTotalD, new XYConstraints(619, 9, 80, 20));
        pnlTotalesD.add(lblTotalS, new XYConstraints(464, 9, 95, 20));
        pnlTotalesD.add(lblTotalDT, new XYConstraints(579, 9, 35, 20));
        pnlTotalesD.add(lblTotalST, new XYConstraints(379, 9, 80, 20));
        pnlTotalesD.add(lblRedondeoT, new XYConstraints(4, 9, 70, 20));

        pnlTotalesD.add(lblRedondeo, new XYConstraints(79, 9, 65, 20));
        pnlTotalesD.add(lblPorPagarT, new XYConstraints(149, 9, 100, 20));
        pnlTotalesD.add(lblPorPagar, new XYConstraints(259, 9, 100, 20));
        pnlTotalesT.add(lblDsctoPorc, new XYConstraints(545, 5, 15, 15));
        pnlTotalesT.add(lblTotalesT, new XYConstraints(495, 5, 15, 15));
        pnlTotalesT.add(lblBrutoT, new XYConstraints(510, 5, 15, 15));
        pnlTotalesT.add(lblBruto, new XYConstraints(525, 5, 15, 15));
        pnlTotalesT.add(lblIGVT, new XYConstraints(65, 5, 45, 15));
        pnlTotalesT.add(lblIGV, new XYConstraints(120, 5, 95, 15));
        scrProductos.getViewport();
        pnlProductos.add(pnlAtencion1, new XYConstraints(10, 45, 715, 35));
        pnlProductos.add(btnRelacionProductos, new XYConstraints(10, 5, 145, 15));
        pnlProductos.add(lblItemsT, new XYConstraints(185, 5, 40, 15));
        pnlProductos.add(lblItems, new XYConstraints(150, 5, 30, 15));
        pnlAtencion.add(lblFormaPago, new XYConstraints(454, -1, 260, 30));
        pnlAtencion.add(lblUltimoPedido, new XYConstraints(655, 5, 40, 15));
        pnlAtencion.add(lblUltimoPedidoT, new XYConstraints(585, 5, 70, 15));
        pnlAtencion.add(lblVendedor, new XYConstraints(244, 4, 60, 15));
        pnlAtencion.add(lblNombreVendedor, new XYConstraints(304, 4, 235, 15));
        pnlAtencion.add(lblTipoCambio, new XYConstraints(205, 5, 40, 15));

        pnlAtencion.add(lblFecha, new XYConstraints(60, 5, 70, 15));
        pnlAtencion.add(lblTipoCambioT, new XYConstraints(130, 5, 80, 15));
        pnlAtencion.add(lblFechaT, new XYConstraints(10, 5, 50, 15));

        jPanelHeader1.add(lblCliente_T, null);
        jPanelHeader1.add(lblLCredito_T, null);
        jPanelHeader1.add(lblCliente, null);
        jPanelHeader1.add(lblBeneficiario_T, null);
        pnlTitle1.add(jPanelHeader1, null);
        pnlTitle1.add(lblProdOculto_T, null);
        pnlTitle1.add(txtDescProdOculto, BorderLayout.CENTER);
        pnlTotalesT1.add(lblDNI_Anul, new XYConstraints(0, 0, 290, 25));
        pnlTotalesT1.add(lblTopeAhoro, new XYConstraints(195, 0, 290, 25));
        jContentPane.add(lblF7, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(lblMensajeCupon, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTotalesD, null);
        jContentPane.add(pnlTotalesT, null);
        scrProductos.getViewport().remove(tblProductos);
        scrProductos.getViewport().add(tblProductos, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(pnlProductos, null);
        jContentPane.add(pnlAtencion, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblCuponIngr, null);
        jContentPane.add(lblCreditoT, null);
        jContentPane.add(lblCredito, null);
        jContentPane.add(lblDsctoT, null);
        jContentPane.add(lblDscto, null);
        jContentPane.add(txtMensajesPedido, null);
        jContentPane.add(pnlTotalesT1, null);

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setLocationRelativeTo(myParentFrame);
        this.setVisible(true);

        //this.getContentPane().add(jContentPane, null);
    }


    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        //jcallo 02.10.2008
        lblDscto.setVisible(false);
        lblDsctoT.setVisible(false);
        //fin jcallo 02.10.2008
        initTableResumenPedido();
        limpiaValoresPedido();
        //dubilluz - 28.03.2012 inicio
        VariablesConvenioBTLMF.limpiaVariablesBTLMF();
        //dubilluz - 28.03.2012 fin
        // Inicio Adicion Delivery 28/04/2006 Paulo
        limpiaVariables();
        // Fin Adicion Delivery 28/04/2006 Paulo
        FarmaVariables.vAceptar = false;

        //jquispe 25.07.2011 se agrego la funcionalidad de listar las campañas sin fidelizar
        UtilityFidelizacion.operaCampañasFidelizacion(" ");

    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableResumenPedido() {
        tableModelResumenPedido = new FarmaTableModel(ConstantsVentas.columnsListaResumenPedido, ConstantsVentas.defaultValuesListaResumenPedido, 0);
        FarmaUtility.initSimpleList(tblProductos, tableModelResumenPedido, ConstantsVentas.columnsListaResumenPedido);
        
        tableModelResumenPedidoAux = new FarmaTableModel(ConstantsVentas.columnsListaResumenPedido, ConstantsVentas.defaultValuesListaResumenPedido, 0);
        
    }

    private void cargaLogin() {
        // DUBILLUZ 04.02.2013
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();

        VariablesVentas.vListaProdFaltaCero = new ArrayList();
        VariablesVentas.vListaProdFaltaCero.clear();

        //limpiando variables de fidelizacion
        UtilityFidelizacion.setVariables();

        //JCORTEZ 04.08.09 Se limpiar cupones.
        VariablesVentas.vArrayListCuponesCliente.clear();
        VariablesVentas.dniListCupon = "";

        /*DlgLogin dlgLogin =
            new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_VENDEDOR);
        dlgLogin.setVisible(true);*/

        FarmaVariables.vAceptar = true;

        if (FarmaVariables.vAceptar) {
            log.info("******* JCORTEZ *********");
            if (UtilityCaja.existeIpImpresora(this, null)) {
                if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) &&
                    !UtilityCaja.existeCajaUsuarioImpresora(this, null)) {
                    //linea agrega pàra corregir el error al validar los roles de los usuarios
                    //FarmaVariables.dlgLogin = dlgLogin;
                    VariablesCaja.vVerificaCajero = false;
                    log.debug("");
                    cerrarVentana(false);
                } else {
                    //FarmaVariables.dlgLogin = dlgLogin;

                    log.info("******* 2 *********");
                    log.info("Usuario: " + FarmaVariables.vIdUsu);
                    muestraMensajeUsuario();
                    FarmaVariables.vAceptar = false;

                    //agregarProducto();
                    agregarProducto(null); //ASOSA - 10/10/2014 - PANHD
                }
            } else {
                //no se genera venta sin impresora asignada (Boleta/ Ticket)
                //FarmaVariables.dlgLogin = dlgLogin;
                VariablesCaja.vVerificaCajero = false;
                cerrarVentana(false);
            }
        } else
            cerrarVentana(false);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        obtieneInfoPedido();

        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if (FarmaVariables.vTipCambio == 0) {
            FarmaUtility.showMessage(this,
                                     "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción",
                                     null);
            cerrarVentana(false);
        } else {
            // DUBILLUZ 13.10.2014
            if (pIngresoComprobanteManual) {
                DlgIngresoCompManual dlgIngManual = new DlgIngresoCompManual(myParentFrame, "", true);
                dlgIngManual.setVisible(true);
                if (!FarmaVariables.vAceptar) {
                    cerrarVentana(true);
                    return;
                } else {
                    log.info("Ingreso Comprobante Manual");
                    cambiaFunciones();
                }
            }
            // DUBILLUZ 13.10.2014

            //JCHAVEZ 08102009.sn
            /*try {
                lblF7.setVisible(DBVentas.getIndVerCupones());


            } catch (SQLException ex) {
                lblF7.setVisible(false);
                log.error("", ex);
            }*/
            //JCHAVEZ 08102009.en

            // Inicio Adicion Delivery 28/04/2006 Paulo
            //if(FarmaVariables.vAceptar)
            //{
            // String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
            // lblCliente.setText(nombreCliente);
            // FarmaVariables.vAceptar = false ;
            // }
            // Fin Adicion Delivery 28/04/2006 Paulo
            txtDescProdOculto.grabFocus();

            lblFecha.setText(fechaPedido);
            lblTipoCambio.setText(FarmaUtility.formatNumber(FarmaVariables.vTipCambio));
            VariablesCaja.vVerificaCajero = true;
            cargaLogin();

            //verificaRolUsuario();
            //agregarProducto();
            lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " + FarmaVariables.vPatUsu.trim() + " " +
                                      FarmaVariables.vMatUsu.trim());
            // Inicio Adicion Delivery 28/04/2006 Paulo
            //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
            //lblCliente.setText(nombreCliente);
            // Fin Adicion Delivery 28/04/2006 Paulo
            colocaUltimoPedidoDiarioVendedor();
            //FarmaUtility.moveFocus(tblProductos);
            FarmaUtility.moveFocus(txtDescProdOculto);


            //JCORTEZ 17.04.08
            lblTotalesT.setVisible(false);
            lblBrutoT.setVisible(false);
            lblBruto.setVisible(false);
            //lblDsctoT.setVisible(false);
            //lblDscto.setVisible(false);
            lblDsctoPorc.setVisible(false);

            //JCORTEZ 23.07.2008
            lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);

            FrmEconoFar.obtieneIndRegistroVentaRestringida();
        }
    }

    private void tblProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        //FarmaUtility.moveFocus(tblProductos);
        FarmaUtility.moveFocus(txtDescProdOculto);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    // ************************************************************************************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - Evitar le ejecución de 2 teclas a la vez al momento de comprometer stock
    // ************************************************************************************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        try {
            if (!vEjecutaAccionTeclaResumen) {
                //log.debug("e.getKeyCode() presionado:"+e.getKeyCode());
                //log.debug("e.getKeyChar() presionado:"+e.getKeyChar());
                vEjecutaAccionTeclaResumen = true;
                log.debug(" try: " + vEjecutaAccionTeclaResumen);
                if (Character.isLetter(e.getKeyChar())) {
                    //LLEIVA 12/Julio/2013 - se añade validaciones para producto virtual
                    if (!VariablesVentas.vProductoVirtual) {
                        //log.debug("Presiono una letra");
                        //vEjecutaAccionTeclaResumen = false;
                        if (VariablesVentas.vKeyPress == null) {
                            //VariablesVentas.vLetraBusqueda = e.getKeyChar() + "";;
                            //log.debug("VariablesVentas.vLetraBusqueda  " + VariablesVentas.vLetraBusqueda);
                            VariablesVentas.vKeyPress = e;
                            //agregarProducto(null);
                            
                            if (!existsProdRimac()) { //ASOSA - 19/01/2015 - RIMAC   //ASOSA - 12/01/2015 - RIMAC
                            agregarProducto(null); //ASOSA - 10/10/2014 - PANHD
                            } else {
                                FarmaUtility.showMessage(this, "Ya se selecciono un producto rimac", txtDescProdOculto);
                            }
                        }
                    } else {
                        FarmaUtility.showMessage(this, "Ya se selecciono un producto virtual", txtDescProdOculto);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    //vEjecutaAccionTeclaResumen = false;
                    evaluaIngresoCantidad();
                } else if (e.getKeyCode() == KeyEvent.VK_F8 && !pIngresoComprobanteManual) {
                    //Se ha comentado este metodo solo de manera temporal
                    //debe de colocarte una funcion para habilitar esto en tab gral
                    //dubilluz 16.09.2009
                    /*if (lblF7.isVisible()) {
                        //JCORTEZ 04.08.09
                        log.info("VariablesVentas.dniListCupon  " +
                                 VariablesVentas.dniListCupon);
                        if (VariablesVentas.dniListCupon.trim().length() < 1)
                            FarmaUtility.showMessage(this,
                                                     "No es pedido fidelizado.",
                                                     txtDescProdOculto);
                        else
                            cargarCupones();
                    }*/
                    //SE QUITO LO ANTERIOR para que pueda ingresar el MEDICO
                    //dubilluz 07.12.2011

                    //Agregado por FRAMIREZ 04.05.2012 descativa para el convenio BTLMF
                    if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)) {
                        ingresaMedicoFidelizado();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                    //vEjecutaAccionTeclaResumen = false;
                    muestraDetalleProducto();
                } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                    //vEjecutaAccionTeclaResumen = false;
                    eliminaItemResumenPedido();
                    FarmaUtility.moveFocus(txtDescProdOculto);
                    //mfajardo 29/04/09 validar ingreso de productos virtuales
                    VariablesVentas.vProductoVirtual = false;
                    VariablesRecetario.strCodigoRecetario = "";
                    
                    //INI ASOSA - 13/01/2015 - RIMAC
                    if (!VariablesVentas.vDniRimac.equals("")){
                        VariablesVentas.vDniRimac = "";
                        VariablesVentas.vCantMesRimac = 0;
                    }
                    //FIN ASOSA - 13/01/2015 - RIMAC

                } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                    //vEjecutaAccionTeclaResumen = false;

                    if (!VariablesVentas.vProductoVirtual) {
                        //agregarProducto();
                        if (!existsProdRimac()) { //ASOSA - 19/01/2015 - RIMAC   //ASOSA - 12/01/2015 - RIMAC
                            agregarProducto(null); //ASOSA - 10/10/2014 - PANHD
                        } else {
                            FarmaUtility.showMessage(this, "Ya se selecciono un producto rimac", txtDescProdOculto);
                        }
                    } else {
                        //log.debug("error de producto virtual marco");
                        FarmaUtility.showMessage(this, "Ya se selecciono un producto virtual", txtDescProdOculto);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F4 && !pIngresoComprobanteManual) {
                    //vEjecutaAccionTeclaResumen = false;
                    //validaConvenio(e, VariablesConvenio.vPorcCoPago);
                    //JMIRANDA 23.06.2010
                    //NUEVO VALIDA CONVENIO
                    /*if(cargaLogin_verifica())
                    {*/
                    lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " + FarmaVariables.vPatUsu.trim() +
                                              " " + FarmaVariables.vMatUsu.trim());
                    // Inicio Adicion Delivery 28/04/2006 Paulo
                    //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
                    //lblCliente.setText(nombreCliente);
                    // Fin Adicion Delivery 28/04/2006 Paulo
                    //FarmaUtility.moveFocus(tblProductos);

                    colocaUltimoPedidoDiarioVendedor();
                    FarmaUtility.moveFocus(txtDescProdOculto);
                    validaConvenio_v2(e, VariablesConvenio.vPorcCoPago);
                    FarmaUtility.moveFocus(txtDescProdOculto);
                    if(actuaRobot){
                        procesarToto(VariablesVentas.vNum_Ped_Vta, "F4");
                    }
                    //}
                } else if (UtilityPtoVenta.verificaVK_F1(e) && !pIngresoComprobanteManual) {
                    //vEjecutaAccionTeclaResumen = false;
                    /* if(cargaLogin_verifica())
                    {*/
                    //mfajardo 29/04/09 validar ingreso de productos virtuales
                    //VariablesVentas.vProductoVirtual = false;

                    //validaConvenio(e, VariablesConvenio.vPorcCoPago);
                    validaConvenio_v3(e, VariablesConvenio.vPorcCoPago);
                    
                    FarmaUtility.moveFocus(txtDescProdOculto); //}
                    if(actuaRobot){
                        procesarToto(VariablesVentas.vNum_Ped_Vta, "F1");
                        
                    }
                }
                //JCORTEZ 17.04.08
                else if (e.getKeyCode() == KeyEvent.VK_F6 && !pIngresoComprobanteManual) {
                    //vEjecutaAccionTeclaResumen = false;
                    if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                        VariablesConvenioBTLMF.vCodConvenio != null &&
                        VariablesConvenioBTLMF.vCodConvenio.length() > 0) {
                        //No se hace nada.
                    } else {
                        mostrarFiltro();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F9 && !pIngresoComprobanteManual) {
                    //Agregado por FRAMIREZ 04.05.2012 descativa para el convenio BTLMF
                    if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)) {
                        //add jcallo 15/12/2008 campanias acumuladas.
                        //veririficar que el producto seleccionado tiene el flag de campanias acumuladas.
                        //validar que no sea un pedido por convenio
                        //log.debug("tecleo f9");
                        //vEjecutaAccionTeclaResumen = false;
                        if (VariablesVentas.vEsPedidoConvenio) {
                            FarmaUtility.showMessage(this,
                                                     "No puede asociar clientes a campañas de ventas acumuladas en un " +
                                                     "pedido por convenio.", txtDescProdOculto);
                        } else { //toda la logica para asociar un cliente hacia campañas nuevas
                            //DUBILLUZ - 29.04.2010
                            if (VariablesFidelizacion.vDniCliente.trim().length() > 0) {
                                int rowSelec = tblProductos.getSelectedRow();
                                String auxCodProd = "";
                                if (rowSelec >=
                                    0) { //validar si el producto seleccionado tiene alguna campaña asociada
                                    auxCodProd = tblProductos.getValueAt(rowSelec, 0).toString().trim();
                                }
                                asociarCampAcumulada(auxCodProd);
                                //se agrego el metodo opera resumen pedido para aplicar las campanas de fidelizacion
                                //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
                                neoOperaResumenPedido();
                                //nuevo metodo jcallo 10.03.2009
                                //FarmaUtility.setearPrimerRegistro(tblProductos,null,0);
                            } else {
                                FarmaUtility.showMessage(this, "No puede ver las campañas:\n" +
                                        "Porque primero debe de fidelizar al cliente con la función F12.",
                                        txtDescProdOculto);
                            }
                        }

                        //JCALLO 19.12.2008 comentado sobre la opcion de ver pedidos delivery..y usarlo para el tema inscribir cliente a campañas acumuladas
                        /** JCALLO INHABILITAR F9 02.10.2008* **/
                        /*log.debug("HABILITAR F9 : " + VariablesVentas.HabilitarF9);
                         if (VariablesVentas.HabilitarF9.equalsIgnoreCase(ConstantsVentas.ACTIVO)) {
                             if (UtilityVentas.evaluaPedidoDelivery(this, tblProductos,
                                                                    VariablesVentas.vArrayList_ResumenPedido)) {
                                 evaluaTitulo();
                             // Inicio Adicion Delivery 28/04/2006 Paulo
                             if (VariablesVentas.vEsPedidoDelivery)
                                 generarPedidoDelivery();
                             // Fin Adicion Delivery 28/04/2006 Paulo
                         }
                         FarmaUtility.moveFocus(txtDescProdOculto);
                        }*/
                    }
                } else if (UtilityPtoVenta.verificaVK_F10(e) && !pIngresoComprobanteManual) {
                    //vEjecutaAccionTeclaResumen = false;
                    //Agregado por FRAMIREZ 04.05.2012 descativa para el convenio BTLMF
                    if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)) {
                        verReceta();
                    }
                    FarmaUtility.moveFocus(txtDescProdOculto);
                } else if (UtilityPtoVenta.verificaVK_F12(e)) {
                    // KMONCADA 2015.02.13 VALIDACION DE REINGRESO O CAMBIO DE CLIENTE
                    TarjetaBean tarjetaPuntosOld = null;
                    if(UtilityPuntos.isActivoFuncionalidad()){
                        if(VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                            if(JConfirmDialog.rptaConfirmDialog(this, "Programa Puntos:\nYa registro un cliente, ¿desea cambiarlo?")){
                                tarjetaPuntosOld = VariablesPuntos.frmPuntos.getTarjetaBean();
                                VariablesFidelizacion.vNumTarjeta = "";
                                VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                                
                            }else{
                                return;
                            }
                        }
                    }
                    funcionF12("N",true);
                    if(tarjetaPuntosOld!=null){
                        UtilityPuntos.transferirInscripcionBonificados(myParentFrame, this, tarjetaPuntosOld);
                    }
                    //vEjecutaAccionTeclaResumen = false;
                    /*if(UtilityVentas.evaluaPedidoInstitucional(this, tblProductos, VariablesVentas.vArrayList_ResumenPedido)){
                            evaluaTitulo();
                       }
                       FarmaUtility.moveFocus(txtDescProdOculto);*/
                    // no podran ver vta institucional segun GERENCIA
                    /*
                        if (VariablesVentas.vEsPedidoConvenio) {
                            FarmaUtility.showMessage(this,
                                                     "No puede agregar una tarjeta a un " +
                                                     "pedido por convenio.",
                                                     txtDescProdOculto);
                            return;
                        }

                        if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                            0) {
                            FarmaUtility.showMessage(this,
                                                     "No puede ingresar mas de una tarjeta.",
                                                     txtDescProdOculto);
                            txtDescProdOculto.setText("");
                        } else
                            mostrarBuscarTarjetaPorDNI();
                        */
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    //vEjecutaAccionTeclaResumen = false;
                    if (JConfirmDialog.rptaConfirmDialog(this, "Está seguro que Desea salir del pedido?")) {
                        //Agregado por FRAMIREZ 27.03.2012
                        VariablesConvenioBTLMF.limpiaVariablesBTLMF();

                        cancelaOperacion_02();
                        VariablesVentas.vCodProdBusq = "";
                        VariablesVentas.vCodBarra = "";

                        //mfajardo 29/04/09 validar ingreso de productos virtuales
                        VariablesVentas.vProductoVirtual = false;
                        //jquispe 13.01.2011
                        FarmaVariables.vAceptar = false;
                        VariablesCaja.vVerificaCajero = false;
                        
                        if(VariablesPuntos.frmPuntos!=null){//validacion cuando es null 01/04/2015
                             VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                        }
                       
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_INSERT) { //Inicio ASOSA 03.02.2010
                    VariablesVentas.vIndPrecioCabeCliente = "S";
                    DlgListaProdDIGEMID objDIGEMID = new DlgListaProdDIGEMID(myParentFrame, "", true);
                    objDIGEMID.setVisible(true);
                    cancelaOperacion_02();

                    //mfajardo 29/04/09 validar ingreso de productos virtuales
                    VariablesVentas.vProductoVirtual = false;
                    cerrarVentana(true);
                } else if (UtilityPtoVenta.verificaVK_F11(e) && pIngresoComprobanteManual) {
                    //COBRA PEDIDO MANUAL
                    //dubilluz 13.10.2014
                    cobraPedidoManual();
                }
                //Fin ASOSA 03.02.2010
                //vEjecutaAccionTeclaResumen = false;
                //pruebas de validacion
                //int i=1/0;
                //if(true)
                // return;
            }
        }
        //try
        catch (Exception exc) {
            log.error("", exc);
            log.debug("catch" + vEjecutaAccionTeclaResumen);
        } finally {
            vEjecutaAccionTeclaResumen = false;
            //log.debug(" finally: " + vEjecutaAccionTeclaResumen);
        }
    }

    /** Valida que usuario logueado sea del Rol Quimico
     * LTAVARA
     * 28.08.2014
     * */

    private boolean cargaLoginAdmLocal() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);

            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);

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
            FarmaUtility.showMessage(this, "Ocurrió un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        }
        return FarmaVariables.vAceptar;
    }

    /**
     * Se verifica si se hizo un cierre de DIGEMID para cerrarlo o no
     * @author ASOSA
     * @since 03.02.2010
     */
    private void verificarDIGEMID() {
        if (VariablesVentas.vIndPrecioCabeCliente.equalsIgnoreCase("S")) { //Inicio ASOSA 03.02.2010
            VariablesVentas.vIndPrecioCabeCliente = "N";
            cancelaOperacion_02();

            //mfajardo 29/04/09 validar ingreso de productos virtuales
            VariablesVentas.vProductoVirtual = false;
            cerrarVentana(true);
        } //Fin ASOSA 03.02.2010
    }

    private void cerrarVentana(boolean pAceptar) {
        VariablesVentas.vListaProdFaltaCero = new ArrayList();
        VariablesVentas.vListaProdFaltaCero.clear();
        FarmaVariables.vAceptar = pAceptar;

        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void agregarProducto(String codigo) {
        log.debug("Entro aca :::: 23.04.2010");
        //FarmaUtility.moveFocus(tblProductos);

        if (VariablesVentas.vCodFiltro.equalsIgnoreCase(ConstantsVentas.IND_OFER)) {
            String vkF = "F6";
            agregarComplementarios(vkF);
        } else { //if (!VariablesVentas.vProductoVirtual)

            //INI ASOSA - 10/10/2014 - PANHD
            boolean flag = false;
            /* SE COMENTO X MIENTRAS PORQUE QUIEREN YAYAYAYA QUE SEA TICO Y SE COMPORTE COMO FARMA - ASOSA 23/10/2014
            if (VariablesPtoVenta.vIndTico.equals("S")) {
                if(aumentarCantidad(codigo)){
                    flag = true;
                }
            }
           */
            //FIN ASOSA - 10/10/2014 - PANHD

            //if(!flag)  //ASOSA - 10/10/2014 - PANHD
            if (true) {
                if (!flag) {
                    DlgListaProductos dlgListaProductos = new DlgListaProductos(myParentFrame, "", true);
                    dlgListaProductos.setResumenPedido(this);
                    dlgListaProductos.setVisible(true);
                }
                verificarDIGEMID(); //ASOSA 03.02.2010

                //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
                neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009

                if (VariablesConvenio.vCodConvenio.equalsIgnoreCase("")) {
                    lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);
                } else {
                    VariablesVentas.vMensCuponIngre = "";
                    lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);
                }

                FarmaVariables.vAceptar = false;

                if (VariablesVentas.vIndDireccionarResumenPed) {
                    if (!VariablesVentas.vIndF11) {
                        /*if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Desea agregar más productos al pedido?"))
                {
                agregarProducto();
                }*/
                    }
                }

                if (vIndPedidosDelivery) {
                    vIndPedidosDelivery = false;
                    abrePedidosDelivery();
                }
            } else {
                //INI ASOSA - 10/10/2014 - PANHD

                //if (VariablesPtoVenta.vIndTico.equals("N")) {SE COMENTO X MIENTRAS PORQUE QUIEREN YAYAYAYA QUE SEA TICO Y SE COMPORTE COMO FARMA - ASOSA 23/10/2014
                if (false) {
                    FarmaUtility.showMessage(this, "Ya se selecciono un producto virtual", txtDescProdOculto);
                }
                //FIN ASOSA - 10/10/2014 - PANHD
            }
        }

        txtDescProdOculto.setText("");
        VariablesVentas.vCodFiltro = "";
        VariablesVentas.vIndF11 = false;
    }

    /**
     * Metodo que determina si un producto ya existe en la lista, si existe le aumenta 1.
     * @author ASOSA
     * @since 10/10/2014
     * @param codigo
     * @return
     */
    public boolean aumentarCantidad(String codigo) {
        log.info("VariablesVentas.vCodigoBarra AUMENTARCANTIDAD: " + VariablesVentas.vCodigoBarra);
        log.info("LONGITUD DE ARRAY: " + VariablesVentas.vArrayList_ResumenPedido.size() + " MULTIPLO " +
                 FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, 0, 27).toString());
        boolean flag = false;
        int pFila = -1;
        String textoCod = "";
        Integer cantAnt = 0;
        log.info("HITO 01 - aumentarCantidad " + codigo);
        if (codigo != null && !codigo.equals("") && codigo.length() == 6) {
            log.info("HITO 02 - aumentarCantidad");
            for (int c = 0; c < VariablesVentas.vArrayList_ResumenPedido.size(); c++) {
                log.info("HITO 03 - aumentarCantidad");
                textoCod = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, c, 0);
                log.info("HITO 04 - aumentarCantidad");
                if (textoCod.trim().equals(codigo)) {
                    flag = true;
                    pFila = c;
                    String valFracFila = "1";
                    if (!determinarIgualdadMultiplo(pFila)) {
                        if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                                      "Esta escaneando producto ya existente en diferente presentación, \nSi continúa se elminara el anterior, \n¿desea continuar?")) {
                            VariablesVentas.vArrayList_ResumenPedido.remove(pFila);
                            flag =
false; //para que luego de borrar el anterior busque el producto y lo muestre en su nueva forma de presentacion.
                        }
                    } else {
                        if (((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).size() >= 28) {
                            valFracFila =
                                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, pFila,
                                                                        27).toString();
                        }
                        cantAnt =
                                Integer.parseInt(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                     c, 4).toString());
                        cantAnt =
                                cantAnt + Integer.parseInt(valFracFila); //puede ser 1 o puede ser otro numero debido a un SIX pack x ejemplo
                        if (existeStockOtraVez(textoCod, cantAnt,
                                               pFila)) { //VALIDA EL STOCK ACA Y NO EN "DLG INGRESO CANTIDAD" PORQUE EN ESTE CASO DEFRENTE AUMENTA CANTIDAD SIN PASAR A LA SIGUIENTE VENTANA.
                            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(4,
                                                                                                 cantAnt.toString());
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Determina si existe stock de un producto normal o final
     * @author ASOSA
     * @since 15/10/2014
     * @param codigo
     * @param cant
     * @param fila
     * @return
     */
    private boolean existeStockOtraVez(String codigo, Integer cant, int fila) {
        String valFrac =
            FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, fila, 10).toString().trim();
        String tipoProducto =
            FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, fila, 28).toString().trim();

        boolean flag = true;

        if (tipoProducto.trim().equals(ConstantsVentas.TIPO_PROD_FINAL)) {
            if (!UtilityVentas.existsStockComp(codigo, cant)) {
                FarmaUtility.showMessage(this, "No hay suficiente stock para vender el producto", txtDescProdOculto);
                flag = false;
            }
        } else {
            if (!existeStockProductoNormal(codigo, cant.toString(), valFrac)) {
                FarmaUtility.showMessage(this, "No hay suficiente stock para vender el producto.", txtDescProdOculto);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Determina si existe stock de un producto normal
     * @author ASOSA
     * @since 15/10/2014
     * @param pCodProd
     * @param pCantidad
     * @param pValFrac
     * @return
     */
    private boolean existeStockProductoNormal(String pCodProd, String pCantidad, String pValFrac) {
        ArrayList lista = new ArrayList();
        int valFracLocal = 0;
        int stockLocal = 0;
        boolean flag = false;
        try {
            DBVentas.obtieneInfoProductoVta(lista, pCodProd);
        } catch (Exception nfe) {
            log.error("", nfe);
        }
        stockLocal = Integer.parseInt(((String)((ArrayList)lista.get(0)).get(0)).trim());
        valFracLocal = Integer.parseInt(((String)((ArrayList)lista.get(0)).get(2)).trim());
        int cantidadPedido = (Integer.parseInt(pCantidad) * valFracLocal) / Integer.parseInt(pValFrac);

        if (cantidadPedido <= stockLocal) {
            flag = true;
        }
        return flag;
    }

    /**
     * Determina la igualdad del valor multiplicador entre el doble registro de un producto con unidades de agrupacion diferente(SIX PACK, FOUR PACK, etc).
     * @author ASOSA
     * @since 15/10/2014
     * @param fila
     * @return
     */
    private boolean determinarIgualdadMultiplo(int fila) {
        boolean flag = false;
        if (((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(fila)).size() >= 28) {
            if (VariablesVentas.vCodigoBarra == null || VariablesVentas.vCodigoBarra.trim().equals("")) {
                flag = true;
            } else {
                String[] list = UtilityVentas.obtenerArrayValoresBd(ConstantsVentas.TIPO_VAL_ADIC_COD_BARRA);
                String new_frac = "1";
                if (!list[0].equals("THERE ISNT")) {
                    new_frac = list[0];
                }
                String valFracFila =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, fila, 27).toString();
                if (new_frac.trim().equals(valFracFila.trim())) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }

    private void muestraDetalleProducto() {
        if (tblProductos.getRowCount() == 0)
            return;
        VariablesVentas.vCod_Prod = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), 0))).trim();
        VariablesVentas.vDesc_Prod = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), 1))).trim();
        VariablesVentas.vNom_Lab = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), 9))).trim();
        DlgDetalleProducto dlgDetalleProducto = new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);
    }

    private void inicializaArrayList() {
        VariablesVentas.vArrayList_PedidoVenta = new ArrayList();
        VariablesVentas.vArrayList_ResumenPedido = new ArrayList();
        /**
     * Reinicia Array
     * @author : dubilluz
     * @since  : 19.06.2007
     */
        VariablesVentas.vArrayList_Promociones = new ArrayList();
        VariablesVentas.vArrayList_Prod_Promociones = new ArrayList();
    }

    /***
     * calcular montos totales del resumen pedido
     * **/
    private void calculaTotalesPedido() {

        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

            jbInitBTLMF();
        }

        log.debug("calculando montos totales");
        //lero lero reemplazando el anterior por el nuevo
        //validaPedidoCupon();
        //el nuevo metodo de calculo de dcto por campana cupones
        calculoDctosPedidoXCupones();

        if (lblDscto.getText().trim().equalsIgnoreCase("0.00")) {
            lblDsctoT.setVisible(false);
            lblDscto.setVisible(false);
        }

        if (VariablesVentas.vArrayList_ResumenPedido.size() <= 0 &&
            VariablesVentas.vArrayList_Promociones.size() <= 0) {
            log.debug("LISTA CERO SUPUESTAMENTE NO HAY PRODUCTOS");
            //FarmaUtility.showMessage(this,"NO HAY PRODUCTOS EN EL LISTADO RESUMEN\ncomuniquese con operador de sistemas!",tblProductos);
            lblBruto.setText("0.00");
            lblDscto.setText("0.00");
            lblDsctoPorc.setText("(0.00%)");
            lblIGV.setText("0.00");
            lblTotalS.setText("0.00");
            lblTotalD.setText("0.00");
            lblRedondeo.setText("0.00");
            lblItems.setText("0");
            evaluaProductoRegalo();
            evaluaCantidadCupon();

            lblCredito.setVisible(false);
            lblCreditoT.setVisible(false);
            lblPorPagarT.setVisible(false);
            lblPorPagar.setVisible(false);

            return;
        }

        double totalBruto = 0.00;
        double totalBrutoRedondeado = 0.00;
        double totalAhorro = 0.00;
        double totalDscto = 0.00;
        double totalIGV = 0.00;
        double totalNeto = 0.00;
        double totalNetoRedondeado = 0.00;
        double redondeo = 0.00;
        int cantidad = 0;
        // valores de Productos EXCLUYENTES ACUMULA AHORRO DIARIO
        int cantidad_excluye = 0;
        double totalBruto_excluye = 0.00;
        double totalIGV_excluye = 0.00;
        double totalNeto_excluye = 0.00;
        double totalBrutoRedondeado_excluye = 0.00;
        double totalNetoRedondeado_excluye = 0.00;
        double redondeo_excluye = 0.00;
        double totalAhorro_excluye = 0.00;
        double totalDscto_excluye = 0.00;

        log.debug("LISTADO DE PRODUCTOS RESUMEN " + VariablesVentas.vArrayList_ResumenPedido);
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
            log.debug("--------------------------------------------------------------------");
            log.debug("VariablesVentas.vArrayList_ResumenPedido.get(i):" +
                      VariablesVentas.vArrayList_ResumenPedido.get(i));
            cantidad = Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(4));
            totalBruto +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(3)) *
                    cantidad;
            totalIGV +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(12));
            totalNeto +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(7));

            ///////////////////////////////////////////////////////////////////////////////////////
            for (int j = 0; j < VariablesVentas.vListProdExcluyeAcumAhorro.size(); j++) {
                String pCod = VariablesVentas.vListProdExcluyeAcumAhorro.get(j).toString();
                if ((getValueColumna(VariablesVentas.vArrayList_ResumenPedido, i, 0)).trim().equalsIgnoreCase(pCod)) {
                    cantidad_excluye = cantidad;
                    totalBruto_excluye += totalBruto;
                    totalIGV_excluye += totalIGV;
                    totalNeto_excluye += totalNeto;
                    log.debug("cantidad_excluye:" + cantidad_excluye);
                    log.debug("totalBruto_excluye:" + totalBruto_excluye);
                    log.debug("totalIGV_excluye:" + totalIGV_excluye);
                    log.debug("totalNeto_excluye:" + totalNeto_excluye);
                }
            }

            log.debug("cantidad:" + cantidad);
            log.debug("totalBrutoAcumulado:" + totalBruto);
            log.debug("totalIGVAcumulado:" + totalIGV);
            log.debug("totalNetoAcumulado:" + totalNeto);
        }
        log.debug("LISTADO DE PRODUCTOS PROMOCION: " + VariablesVentas.vArrayList_Promociones);
        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size(); i++) {
            log.debug("--------------------------------------------------------------------");
            log.debug("VariablesVentas.vArrayList_Promociones.get(i):" +
                      VariablesVentas.vArrayList_Promociones.get(i));
            cantidad = Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(4));
            totalBruto +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(3)) *
                    cantidad;
            totalNeto +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(7));
            totalIGV +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(12));
            log.debug("cantidad:" + cantidad);
            log.debug("totalBrutoAcumulado:" + totalBruto);
            log.debug("totalIGVAcumulado:" + totalIGV);
            log.debug("totalNetoAcumulado:" + totalNeto);
        }
        //hasta aqui se tiene la suma de los subtotales
        //totalBruto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto,2));//suma del precio tales como aparecen en la lista sin dctos
        //totalNeto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto,2));//suma de subtotales aplicando dctos
        //totalIGV = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2));//total igv, basado en los subtotales con dctos

        //redondeo total bruto a 2 cifras
        totalBrutoRedondeado = UtilityVentas.Redondear(totalBruto, 2);

        /// excluyente
        //redondeo total bruto a 2 cifras
        totalBrutoRedondeado_excluye = UtilityVentas.Redondear(totalBruto_excluye, 2);
        //total bruto a 2 cifras decimales a favor del cliente multiplo de .05
        totalBrutoRedondeado_excluye = UtilityVentas.ajustarMonto(totalBrutoRedondeado_excluye, 2);

        //redondeo total neto a 2 cifras
        totalNetoRedondeado =
                UtilityVentas.Redondear(totalNeto, 2); //redondeo a 2 cifras pero no a ajustado a .05 o 0.00

        //total neto a 2 cifras decimales a favor del cliente multiplo de .05
        //redondeo total neto a 2 cifras
        totalNetoRedondeado_excluye =
                UtilityVentas.Redondear(totalNeto_excluye, 2); //redondeo a 2 cifras pero no a ajustado a .05 o 0.00
        //total neto a 2 cifras decimales a favor del cliente multiplo de .05

        //totalNetoRedondeado = UtilityVentas.ajustarMonto(totalNetoRedondeado, 3);
        double totalNetoRedNUEVO = UtilityVentas.ajustarMonto(totalNetoRedondeado, 3);
        //ERIOS 2.4.7 Para pedido convenio, no ajusta el total
        if ( //UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
            VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.length() > 0) {
            ;
        } else {
            //total bruto a 2 cifras decimales a favor del cliente multiplo de .05
            totalBrutoRedondeado = UtilityVentas.ajustarMonto(totalBrutoRedondeado, 2);
            totalNetoRedondeado = UtilityVentas.ajustarMonto(totalNetoRedondeado, 2);
        }

        redondeo = totalNetoRedondeado - totalNeto;

        /////////////////////////////////////////////////////////////////////////////////////
        //double totalNetoRedNUEVO_excluye = UtilityVentas.ajustarMonto(totalNetoRedondeado_excluye, 3);
        totalNetoRedondeado_excluye = UtilityVentas.ajustarMonto(totalNetoRedondeado_excluye, 2);

        redondeo_excluye = totalNetoRedondeado_excluye - totalNeto_excluye;

        /////////////////////////////////////////////////////////////////////////////////////
        //mfajardo 18.05.09 : si es convenio no debe mostrar ahorro
        if (!VariablesVentas.vEsPedidoConvenio) {
            //totalAhorro = (totalBrutoRedondeado - totalNetoRedondeado);
            totalAhorro = (totalBrutoRedondeado - totalNetoRedNUEVO);
            //Se comento en Convenios: totalAhorro_excluye = (totalBrutoRedondeado_excluye - totalNetoRedNUEVO_excluye);
        }

        totalDscto = UtilityVentas.Redondear((totalAhorro * 100.00) / totalBruto, 2);
        totalDscto_excluye = UtilityVentas.Redondear((totalAhorro_excluye * 100.00) / totalBruto_excluye, 2);

        //Se verifica el ahorro que se obtiene este ahorro no debe de exceder al Maximo
        //DUBILLUZ 28.05.2009
        boolean pLlegoTopeDscuento = false;
        lblTopeAhoro.setText("");

        if (VariablesFidelizacion.vDniCliente.trim().length() > 0) {
            if (VariablesFidelizacion.vAhorroDNI_Pedido > 0)
                totalAhorro = VariablesFidelizacion.vAhorroDNI_Pedido - totalAhorro_excluye;

            if (totalAhorro > 0) {
                if (totalAhorro + VariablesFidelizacion.vAhorroDNI_x_Periodo - totalAhorro_excluye >=
                    VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) {
                    /*
                    SE COMENTO ESTA PARTE PARA QUE EL MENSAJE QUE SE MUESTRE SEA SIEMPRE EL AHORRO EXITENTE
                    YA QUE DEBIDO A LA CAMPAÑA CMR A BELACTA 1 DEBE DE EXCEDER LOS 50SOLES DIARIO PERO NO DEBE DE
                    USARSE PARA ACUMULAR EL AHORRO. PERO SE PIDIO MOSTRAR TO_DO EL AHORRO.
                    totalAhorro =
                            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo -
                            VariablesFidelizacion.vAhorroDNI_x_Periodo;
                    */
                    pLlegoTopeDscuento = true;
                }
                //ya no se muestra el total bruto
                //por si algun dia quiera volver mostrar
                lblBruto.setText(FarmaUtility.formatNumber(totalBrutoRedondeado));
                //jcallo 02.10.2008 se modifico por el tema del texto de ahorro
                try{
                    lblDsctoT.setText(DBPuntos.getTextoAhorro());
                }catch(Exception ex){
                    log.error(" ",ex);
                }
                if (pLlegoTopeDscuento) {
                    lblDscto.setText(FarmaUtility.formatNumber(totalAhorro));
                    //lblDscto.setText(FarmaUtility.formatNumber(totalAhorro)+"-"+FarmaUtility.formatNumber(VariablesFidelizacion.vAhorroDNI_Pedido));
                    lblTopeAhoro.setText(" (Llegó al tope de descuento S/ " +
                                         FarmaUtility.formatNumber(VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) +
                                         " )");
                } else {
                    lblDscto.setText(FarmaUtility.formatNumber(totalAhorro));
                    //lblDscto.setText(FarmaUtility.formatNumber(totalAhorro)+"-"+FarmaUtility.formatNumber(VariablesFidelizacion.vAhorroDNI_Pedido));
                    lblTopeAhoro.setText("");
                }
            }
        } else {
            try{
                lblDsctoT.setText(DBPuntos.getTextoAhorro());
            }catch(Exception ex){
                log.error(" ",ex);
            }
            //ya no se muestra el total bruto
            //por si algun dia quiera volver mostrar
            lblBruto.setText(FarmaUtility.formatNumber(totalBrutoRedondeado));
            //jcallo 02.10.2008 se modifico por el tema del texto de ahorro
            lblDscto.setText(FarmaUtility.formatNumber(totalAhorro));
        }


        if (totalAhorro > 0.0) {
            lblDsctoT.setVisible(true);
            lblDscto.setVisible(true);
        } else {
            lblDsctoT.setVisible(false);
            lblDscto.setVisible(false);
        }
        //fin jcallo 02.10.2008
        lblDsctoPorc.setText(FarmaUtility.formatNumber(totalDscto));
        if (FarmaUtility.getDecimalNumber(lblDsctoPorc.getText().trim()) > 0) {
            lblDsctoPorc.setVisible(true);
        }

        lblIGV.setText(FarmaUtility.formatNumber(totalIGV));

        lblPorPagarT.setVisible(false);
        lblPorPagar.setVisible(false);
        lblCredito.setVisible(false);
        lblCreditoT.setVisible(false);

        VariablesConvenioBTLMF.vImpSubTotal = totalNetoRedondeado;

        //Agregado por FRAMIREZ 26.03.2012 Calcula El monto de credito de tipo convenio COPAGO.
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

            double montoCredito;

            if (VariablesConvenioBTLMF.vValorSelCopago == -1) {
                montoCredito =
                        UtilityConvenioBTLMF.obtieneMontoCredito(this, null, new Double(totalNetoRedondeado), "",
                                                                 VariablesConvenioBTLMF.vCodConvenio,
                                                                 VariablesConvenioBTLMF.vValorSelCopago);
            } else {

                //VariablesConvenio.vPorcCoPago=String.valueOf(100-VariablesConvenioBTLMF.vValorSelCopago);
                montoCredito = ((100 - VariablesConvenioBTLMF.vValorSelCopago) / 100) * totalNetoRedondeado;
            }
            double porcentajeCredito = (montoCredito / totalNetoRedondeado) * 100;

            //log.debug("porcentajeCredito:::"+porcentajeCredito);

            if (porcentajeCredito > 0) {
                double montoaPagar = (totalNetoRedondeado - montoCredito);

                lblPorPagarT.setVisible(true);
                lblPorPagar.setVisible(true);
                lblCredito.setVisible(true);
                lblCreditoT.setVisible(true);

                lblCredito.setText(FarmaUtility.formatNumber(montoCredito));
                //ERIOS 2.4.6 Redondeo por calculo de porcentaje
                String porcCoPago = FarmaUtility.formatNumber((montoCredito / totalNetoRedondeado) * 100, 2);

                if (montoaPagar == 0) {
                    lblCreditoT.setText("Crédito(" + porcCoPago + "%): S/.");
                    lblPorPagarT.setText("");
                    lblPorPagar.setText("");
                } else {
                    lblCreditoT.setText("Crédito(" + porcCoPago + "%): S/.");
                    lblPorPagarT.setText("CoPago: S/.");
                    lblPorPagar.setText(FarmaUtility.formatNumber(montoaPagar));
                }
            } else {
                lblPorPagarT.setVisible(false);
                lblPorPagar.setVisible(false);
                lblCredito.setVisible(false);
                lblCreditoT.setVisible(false);
            }
        } else {
            lblCreditoT.setText(" ");
            lblCredito.setText(" ");
            lblPorPagar.setText(" ");
            lblPorPagarT.setText(" ");
        }

        lblTotalS.setText(FarmaUtility.formatNumber(totalNetoRedondeado));
        lblTotalD.setText(FarmaUtility.formatNumber(totalNetoRedondeado /
                                                    FarmaVariables.vTipCambio)); //obtener el tipo de cambio del dia
        lblRedondeo.setText(FarmaUtility.formatNumber(redondeo));
        lblItems.setText(String.valueOf(VariablesVentas.vArrayList_ResumenPedido.size() +
                                        VariablesVentas.vArrayList_Prod_Promociones.size()));

        evaluaProductoRegalo();
        evaluaCantidadCupon();

        /*
    //Se evalua si ya esta en el limite de ahorro diario
    //DUBILLUZ 28.05.2009
    if(VariablesFidelizacion.vAhorroDNI_Pedido + VariablesFidelizacion.vAhorroDNI_x_Periodo>=VariablesFidelizacion.vMaximoAhorroDNIxPeriodo)
    {
        FarmaUtility.showMessage(this,"El tope de descuento diario por persona es de S/."+VariablesFidelizacion.vMaximoAhorroDNIxPeriodo+". \n"+
                                      "El cliente ya llego a su tope diario"
                                      , tblProductos);
    }
    */

    }

    private void muestraIngresoCantidad() {
        if (tblProductos.getRowCount() == 0)
            return;
        int vFila = tblProductos.getSelectedRow();
        VariablesVentas.vCod_Prod = ((String)(tblProductos.getValueAt(vFila, 0))).trim();
        VariablesVentas.vDesc_Prod = ((String)(tblProductos.getValueAt(vFila, 1))).trim();
        VariablesVentas.vVal_Prec_Lista = ((String)(tblProductos.getValueAt(vFila, 3))).trim();
        VariablesVentas.vNom_Lab = ((String)(tblProductos.getValueAt(vFila, 9))).trim();
        VariablesVentas.vCant_Ingresada_Temp = ((String)(tblProductos.getValueAt(vFila, 4))).trim();
        VariablesVentas.vVal_Frac = ((String)(tblProductos.getValueAt(vFila, 10))).trim();
        VariablesVentas.vPorc_Igv_Prod = ((String)(tblProductos.getValueAt(vFila, 11))).trim();
        VariablesVentas.vPorc_Dcto_1 = ((String)(tblProductos.getValueAt(vFila, 5))).trim();
        log.info("((String)(tblProductos.getValueAt(vFila,5))).trim() : " +
                 ((String)(tblProductos.getValueAt(vFila, 5))).trim());
        /*
        VariablesVentas.vVal_Prec_Vta =
                ((String)(tblProductos.getValueAt(vFila, 6))).trim();
        */
        //INI ASOSA - 03/09/2014 - CORRECCION

        double precio = 0.0;
        String valMulti = "1";
        VariablesVentas.vValorMultiplicacion = "1";

        if (tableModelResumenPedido.getRow(vFila).size() >= 28) {
            valMulti = FarmaUtility.getValueFieldArrayList(tableModelResumenPedido.data, vFila, 27);
        }
        //INI ASOSA - 09/10/2014
        String tipoProducto = "";
        if (tableModelResumenPedido.getRow(vFila).size() >= 29) {
            tipoProducto = FarmaUtility.getValueFieldArrayList(tableModelResumenPedido.data, vFila, 28);
        }
        //FIn ASOSA - 09/10/2014

        precio =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(tableModelResumenPedido.data, vFila, 6)) *
                FarmaUtility.getDecimalNumber(valMulti); //ASOSA - 12/08/2014

        VariablesVentas.vValorMultiplicacion = valMulti; //ASOSA - 12/08/2014 , old
        VariablesVentas.tipoProducto = tipoProducto; //ASOSA - 09/10/2014 - PANHD

        //INI ASOSA - 03/09/2014 - CORRECCION

        VariablesVentas.vVal_Prec_Vta = "" + precio; //ASOSA - 12/08/2014

        //log.debug("VariablesVentas.vPorc_Igv_Prod : " + VariablesVentas.vPorc_Igv_Prod);
        VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_RES_ORIG_PROD);
        VariablesVentas.vPorc_Dcto_2 = "0";
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";

        log.debug("-------------------------------------------------------------");
        log.debug("-------------------------------------------------------------");
        log.debug("-------------Metodo: muestraIngresoCantidad------------------");
        log.debug("-------------------------------------------------------------");
        log.debug("-------------------------------------------------------------");
        log.debug("-------------------------------------------------------------");


        log.debug("VariablesVentas.vVal_Prec_Vta:" + VariablesVentas.vVal_Prec_Vta);


        //if (VariablesVentas.vEsPedidoConvenio) {
        ///////////////////////////////////////////////////////////////////////////////////////

        VariablesConvenio.vVal_Prec_Vta_Local = ((String)(tblProductos.getValueAt(vFila, 6))).trim();
        VariablesConvenio.vVal_Prec_Vta_Conv = VariablesVentas.vVal_Prec_Vta;
        VariablesConvenioBTLMF.vNew_Prec_Conv = VariablesVentas.vVal_Prec_Vta;
        ////////////////////////////////////////////////////////////////////////////////////////
        log.debug("VariablesConvenio.vVal_Prec_Vta_Local:" + VariablesConvenio.vVal_Prec_Vta_Local);
        log.debug("VariablesConvenio.vVal_Prec_Vta_Conv:" + VariablesConvenio.vVal_Prec_Vta_Conv);

        //}
        
        //INI ASOSA - 06/01/2015 - RIMAC
        if(UtilityInventario.isExistProdRimac(VariablesVentas.vCod_Prod)){   
            DlgIngresoProductoRimac dlgIngresoProductoRimac = new DlgIngresoProductoRimac(myParentFrame, "", true);
            dlgIngresoProductoRimac.setVisible(true);
            /*
            if (!FarmaVariables.vAceptar) {
                VariablesVentas.vCantMesRimac = 0;
                VariablesVentas.vDniRimac = "";
            }
            */
        } 
        //FIN ASOSA - 06/01/2015 - RIMAC
        
            DlgIngresoCantidad dlgIngresoCantidad = new DlgIngresoCantidad(myParentFrame, "", true);
            VariablesVentas.vIngresaCant_ResumenPed = true;
            dlgIngresoCantidad.setTipoProducto(tipoProducto); //ASOSA - 09/10/2014
            VariablesVentas.tipoLlamada = "1"; //ASOSA - 09/10/2014
            dlgIngresoCantidad.setVisible(true);
            if (FarmaVariables.vAceptar) {
                seleccionaProducto(vFila);
                FarmaVariables.vAceptar = false;
                VariablesVentas.vIndDireccionarResumenPed = true;
            } else
                VariablesVentas.vIndDireccionarResumenPed = false;
            
        
        VariablesVentas.vValorMultiplicacion = "1"; //ASOSA - 12/08/2014
    }

    /**
     * Muestra el Detalle de Promocion para su modificacion
     * @author : dubilluz
     * @since  : 25.06.2007
     */
    private void muestraDetallePromocion(int row) {
        VariablesVentas.vCodProm = FarmaUtility.getValueFieldJTable(tblProductos, row, 0);
        VariablesVentas.vDesc_Prom = FarmaUtility.getValueFieldJTable(tblProductos, row, 1);
        VariablesVentas.vCantidad = FarmaUtility.getValueFieldJTable(tblProductos, row, 4);
        VariablesVentas.accionModificar = true;
        log.debug("****Codigo de Prom _antes del detalle : " + VariablesVentas.vCodProm);
        DlgDetallePromocion dlgdetalleprom = new DlgDetallePromocion(myParentFrame, "", true);
        dlgdetalleprom.setVisible(true);
        //Se debe colocar false tanto la accion y to_do
        //if(FarmaVariables.vAceptar){
        FarmaVariables.vAceptar = false;
        VariablesVentas.accionModificar = false;
        VariablesVentas.vCodProm = "";
        VariablesVentas.vDesc_Prom = "";
        VariablesVentas.vCantidad = "";
        //}
        log.debug("Accion de Moficar" + VariablesVentas.accionModificar);
        log.debug("Cantidad de Promocion" + VariablesVentas.vCantidad);

        //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
        neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009

    }

    private void seleccionaProducto(int pFila) {
        //    VariablesVentas.vTotalPrecVtaProd = (Double.parseDouble(VariablesVentas.vCant_Ingresada) * Double.parseDouble(VariablesVentas.vVal_Prec_Vta));
        VariablesVentas.vTotalPrecVtaProd =
                (FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada) * FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta));
        String indicadorControlStock = FarmaUtility.getValueFieldJTable(tblProductos, pFila, 16);
        String secRespaldo = ""; //ASOSA, 02.07.2010
        secRespaldo =
                (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(pFila)).get(26); //ASOSA, 02.07.2010
        int cantIngresada = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada));
        int cantIngresada_old =
            FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada_Temp));
        VariablesVentas.vVal_Frac = FarmaUtility.getValueFieldJTable(tblProductos, pFila, COL_RES_VAL_FRAC);
        log.info("ANTES_RES_VariablesVentas.secRespStk:_" + VariablesVentas.secRespStk);
        if (indicadorControlStock.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (cantIngresada_old > cantIngresada) {
                VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if ( /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod,   //antes - ASOSA, 02.07.2010
                                                     (cantIngresada_old-cantIngresada),
                                                     ConstantsVentas.INDICADOR_D,
                                                     ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                                     cantIngresada,
                                                     true,
                                                     this,
                                                     tblProductos))*/
                    !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                        //ASOSA, 02.07.2010
                        cantIngresada, ConstantsVentas.INDICADOR_D,
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR, 0, true, this, tblProductos, secRespaldo))
                    return;
            } else if (cantIngresada_old < cantIngresada) {
                if (FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vStk_Prod)) == 0 &&
                    !(VariablesVentas.vEsPedidoDelivery && UtilityVentas.getIndVtaNegativa())) {
                    if (!VariablesVentas.tipoProducto.equals(ConstantsVentas.TIPO_PROD_FINAL)) { //ASOSA - 09/10/2014
                        FarmaUtility.showMessage(this, "No existe Stock disponible. Verifique!!!", tblProductos);
                    }
                } else {
                    VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                    if ( /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod,  //antes - ASOSA, 02.07.2010
                                                       (cantIngresada-cantIngresada_old),
                                                       ConstantsVentas.INDICADOR_A,
                                                       ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                                       cantIngresada,
                                                       true,
                                                       this,
                                                       tblProductos))*/
                        !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                            //ASOSA, 02.07.2010
                            cantIngresada, ConstantsVentas.INDICADOR_A,
                            ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR, 0, true, this, tblProductos,
                            secRespaldo))
                        return;
                }
            }
        }
        //liberando registros
        FarmaUtility.liberarTransaccion();
        log.info("Desp_RES_VariablesVentas.secRespStk:_" + VariablesVentas.secRespStk);
        //ERIOS 03.06.2008 Cuando se ingresa por tratamiento, el total es el calculado
        //y el precio de venta unitario se recalcula.
        double auxPrecVta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
        double auxCantIngr = FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);

        if (VariablesVentas.vIndTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            VariablesVentas.vTotalPrecVtaProd = VariablesVentas.vTotalPrecVtaTra;
            VariablesVentas.vVal_Prec_Vta =
                    FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd / auxCantIngr, 3);
        } else if (!VariablesVentas.vEsPedidoConvenio &&
                   !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) //ERIOS 18.06.2008 Se redondea el total de venta por producto
        {
            //JCHAVEZ 29102009 redondeo inicio
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                try {
                    VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();

                } catch (SQLException ex) {
                    log.error("", ex);
                }
            }

            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                log.debug("vIndAplicaRedondeo: " + VariablesVentas.vIndAplicaRedondeo);
                VariablesVentas.vTotalPrecVtaProd = (auxCantIngr * auxPrecVta);
                VariablesVentas.vVal_Prec_Vta =
                        FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd / auxCantIngr, 3);
                log.debug("VariablesVentas.vTotalPrecVtaProd : " + VariablesVentas.vTotalPrecVtaProd);
                log.debug("VariablesVentas.vVal_Prec_Vta : " + VariablesVentas.vVal_Prec_Vta);
            }
            //JCHAVEZ 29102009 redondeo fin
            else {
                VariablesVentas.vTotalPrecVtaProd = (auxCantIngr * auxPrecVta);
                //El redondeo se ha dos digitos hacia arriba ha 0.05.
                /*TO_CHAR( CEIL(VAL_PREC_VTA*100)/100 +
                                 CASE WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) = 0.0 THEN 0.0
                                      WHEN (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) <= 0.5 THEN
                                           (0.5 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10
                                      ELSE (1.0 -( (CEIL(VAL_PREC_VTA*100)/10)-TRUNC(CEIL(VAL_PREC_VTA*100)/10) ))/10 END ,'999,990.000') || 'Ã' ||*/
                double aux1 = Math.ceil(Math.round(VariablesVentas.vTotalPrecVtaProd * 100)) / 100; //0.01
                log.debug("VariablesVentas.vTotalPrecVtaProd--------" + VariablesVentas.vTotalPrecVtaProd);
                double aux2 = Math.ceil(Math.round(VariablesVentas.vTotalPrecVtaProd * 100)) / 10; //0.1
                aux2 = FarmaUtility.getDecimalNumber((FarmaUtility.formatNumber(aux2, 3)));
                int aux21 = (int)(aux2 * 10);
                int aux3 = FarmaUtility.trunc(aux2) * 10;
                //int aux4 = aux21%aux3;
                int aux4 = 0;
                double aux5;

                //JCORTEZ 01/10/2008
                if (aux3 < 1)
                    aux4 = 0;
                else
                    aux4 = aux21 % aux3;

                if (aux4 == 0) {
                    aux5 = 0;
                } else if (aux4 <= 5) {
                    aux5 = (5.0 - aux4) / 100;
                } else {
                    aux5 = (10.0 - aux4) / 100;
                }

                VariablesVentas.vTotalPrecVtaProd = aux1 + aux5;

                VariablesVentas.vVal_Prec_Vta =
                        FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd / auxCantIngr, 3);
            }


        }

        log.debug("VariablesVentas.vArrayList_ResumenPedido 0 " + VariablesVentas.vArrayList_ResumenPedido);
        //ESTO PONE DATOS EN EL JTABLE, cosa que estaria de mas
        operaProductoEnJTable(pFila);
        log.debug("VariablesVentas.vArrayList_ResumenPedido 1 " + VariablesVentas.vArrayList_ResumenPedido);
        //ESTO DE AQUI CAMBIO LOS DATOS EN EL ARRAYLIST DE RESUMEN PEDIDO
        operaProductoEnArrayList(pFila);
        log.debug("VariablesVentas.vArrayList_ResumenPedido 2 " + VariablesVentas.vArrayList_ResumenPedido);
        //aqui calculato totales pedido SE COMENTO YA QUE NO REFLEJABA LOS CAMBIOS EN EL JTABLE
        //calculaTotalesPedido();se reemplazo con lo siguiente que tiene el reflejar los cambios en el jtable
        neoOperaResumenPedido();
        //seleccionar el producto que se selecciono
        FarmaGridUtils.showCell(tblProductos, pFila, 0);

        log.debug("VariablesVentas.vArrayList_ResumenPedido 3 " + VariablesVentas.vArrayList_ResumenPedido);
    }

    private void operaProductoEnJTable(int pFila) {
        //tblProductos.setValueAt(VariablesVentas.vVal_Prec_Lista, pFila, 3);//precio de lista
        tblProductos.setValueAt(VariablesVentas.vCant_Ingresada, pFila, 4); //cantidad ingresada
        //JCORTEZ 17.04.08
        //tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Dcto_1),2), pFila, 5);//PORC DCTO 1

        //tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3), pFila, 6);//PRECIO DE VENTA
        //log.debug(" FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2) " + FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2));
        //tblProductos.setValueAt(FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2), pFila, 7);//Total Precio Vta
        //String valIgv = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) - (FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) / ( 1 + (FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Igv_Prod) / 100)))) * FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada));
        //log.debug(valIgv);
        //VariablesVentas.vVal_Igv_Prod = valIgv;
        //tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Igv_Prod),2), pFila, 12);//Total Igv Producto
        tblProductos.setValueAt(VariablesVentas.vNumeroARecargar, pFila, 13); //Numero de Recarga

        tblProductos.setValueAt(VariablesVentas.vCantxDia, pFila, COL_RES_CANT_XDIA);
        tblProductos.setValueAt(VariablesVentas.vCantxDias, pFila, COL_RES_CANT_DIAS);

        tblProductos.repaint();
    }

    private void operaProductoEnArrayList(int pFila) {

        //INI ASOSA - 12/08/2014
        VariablesVentas.vCant_Ingresada =
                "" + Integer.parseInt(VariablesVentas.vCant_Ingresada) * Integer.parseInt(VariablesVentas.vValorMultiplicacion);
        VariablesVentas.vVal_Prec_Vta =
                "" + FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) / Integer.parseInt(VariablesVentas.vValorMultiplicacion);
        //FIN ASOSA - 12/08/2014

        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(4, VariablesVentas.vCant_Ingresada);
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(5,
                                                                             FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Dcto_1),
                                                                                                       2));
        //((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(6, FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3));
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(3,
                                                                             FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),
                                                                                                       3));
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(7,
                                                                             FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,
                                                                                                       2));
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(12,
                                                                             FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Igv_Prod),
                                                                                                       2));
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(13, VariablesVentas.vNumeroARecargar);
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(COL_RES_CANT_XDIA,
                                                                             VariablesVentas.vCantxDia);
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(COL_RES_CANT_DIAS,
                                                                             VariablesVentas.vCantxDias);

        //JCHAVEZ 29102009 inicio
        try {
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();
            }
        } catch (SQLException ex) {
            log.error("", ex);
        }
        if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(6,
                                                                                 FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),
                                                                                                           3));
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(7,
                                                                                 FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,
                                                                                                           3));
        }
        //JCHAVEZ 29102009 fin
        ArrayList vAux = (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila);
        log.info("Registro modificado: " + vAux);
    }

    /**
     * elimina elemento seleccionado
     * @author : dubilluz
     * @since  : 19.06.2007
     */
    private void eliminaItemResumenPedido() {
        txtDescProdOculto.setText("");
        int filaActual = tblProductos.getSelectedRow();
        if (filaActual >= 0) {
            String indicadorPromocion = FarmaUtility.getValueFieldJTable(tblProductos, filaActual, COL_RES_IND_PACK);
            log.debug("INDICADOR PROMOCION: " + indicadorPromocion);

            if (indicadorPromocion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                eliminaPromocion(filaActual);
                log.debug("ELIMINAR PROMOCION");
            } else {
                eliminaProducto(filaActual);
                log.debug("ELIMINAR PRODUCTO");
            }
            /****************************************************************************************************/
            /*if(indicadorPromocion.equalsIgnoreCase("N")){
                    eliminaProducto(filaActual);
                    log.debug("ELIMINAR PRODUCTO");
                }else{
                    eliminaPromocion(filaActual);
                    log.debug("ELIMINAR PROMOCION");
                }*/
            /***************************************************************************************************/
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un Producto", tblProductos);
        }
    }

    /**
     * Elimina  producto seleccionado
     * @author :dubilluz
     * @since  :19.06.2007
     */
    void eliminaProducto(int filaActual) {

        if (JConfirmDialog.rptaConfirmDialog(this, "Seguro de eliminar el Producto del Pedido?")) {
            String codProd = ((String)(tblProductos.getValueAt(filaActual, 0))).trim();
            VariablesVentas.vVal_Frac = FarmaUtility.getValueFieldJTable(tblProductos, filaActual, 10);
            String cantidad = ((String)(tblProductos.getValueAt(filaActual, 4))).trim();
            String indControlStk = ((String)(tblProductos.getValueAt(filaActual, 16))).trim();
            String secRespaldo = ""; //ASOSA, 02.07.2010
            secRespaldo =
                    (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(filaActual)).get(26); //ASOSA, 02.07.2010
            VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
            log.info("filaActual:_" + filaActual);
            log.info("VariablesVentas.vArrayList_PedidoVenta:_" + VariablesVentas.vArrayList_PedidoVenta);
            log.info("ANTES_BORRA_VariablesVentas.secRespStk:_" + VariablesVentas.secRespStk);
            log.info("secRespaldo:_" + secRespaldo);
            if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                /*!UtilityVentas.actualizaStkComprometidoProd(codProd,
                                                       Integer.parseInt(cantidad),
                                                       ConstantsVentas.INDICADOR_D,
                                                       ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR,
                                                       Integer.parseInt(cantidad),
                                                      true,
                                                      this,
                                                      tblProductos))*/
                !UtilityVentas.operaStkCompProdResp(codProd,
                    //ASOSA, 02.07.2010
                    0, ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, true, this,
                    tblProductos, secRespaldo))
                return;
            log.info("DESPUES_BORRA_VariablesVentas.secRespStk:_" + VariablesVentas.secRespStk);
            log.info("secRespaldo:_" + secRespaldo);
            ArrayList vAux = (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(filaActual);
            log.info("Registro eliminado: " + vAux);

            //dubilluz - 26.08.2010
            VariablesVentas.vArrayList_PedidoVenta.remove(filaActual);
            vAux = null;
            //dubilluz - 26.08.2010
            VariablesVentas.vArrayList_ResumenPedido.remove(filaActual);
            tableModelResumenPedido.deleteRow(filaActual);
            tblProductos.repaint();
            log.info("000-VariablesVentas.vArrayList_PedidoVenta:_" + VariablesVentas.vArrayList_PedidoVenta);
            //calculaTotalesPedido();comentado para reemplazar por por el neoOperaResumenPedido
            neoOperaResumenPedido();
            log.info("001-VariablesVentas.vArrayList_PedidoVenta:_" + VariablesVentas.vArrayList_PedidoVenta);


            if (tableModelResumenPedido.getRowCount() > 0) {
                if (filaActual > 0)
                    filaActual--;
                FarmaGridUtils.showCell(tblProductos, filaActual, 0);
            }

            //Setea e INdicador de Venta de Producto Virtual
            VariablesVentas.venta_producto_virtual = false;
        }
    }

    /**
     * Elimina la promocion y su detalle del Pedido
     * @author : dubilluz
     * @since  : 19.06.2007
     */
    void eliminaPromocion(int filaActual) {
        if (JConfirmDialog.rptaConfirmDialog(this, "Seguro de eliminar la Promocion del Pedido?")) {
            String codProm = ((String)(tblProductos.getValueAt(filaActual, 0))).trim();
            String codProd = "";
            String cantidad = ""; //((String)(tblProductos.getValueAt(filaActual,4))).trim();
            String indControlStk = ""; // ((String)(tblProductos.getValueAt(filaActual,16))).trim();
            ArrayList aux = new ArrayList();

            ArrayList prod_Prom = new ArrayList();
            log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " +
                      VariablesVentas.vArrayList_Prod_Promociones);
            log.debug("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB: " + VariablesVentas.vCodProm);
            prod_Prom = detalle_Prom(VariablesVentas.vArrayList_Prod_Promociones, codProm);
            log.debug("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC: " + prod_Prom);
            Boolean valor = new Boolean(true);
            ArrayList agrupado = new ArrayList();
            ArrayList atemp = new ArrayList();
            for (int i = 0; i < prod_Prom.size(); i++) {
                log.debug("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                atemp = (ArrayList)(prod_Prom.get(i));
                log.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5555 ATEMP: " + atemp);
                FarmaUtility.operaListaProd(agrupado, (ArrayList)(atemp.clone()), valor, 0);
            }
            log.debug("AAA: -> " + agrupado);
            //agrupado=agrupar(agrupado);
            log.debug(">>>>**Agrupado " + agrupado.size());
            String secRespaldo = ""; //ASOSA, 08.07.2010
            for (int i = 0; i < agrupado.size(); i++) //VariablesVentas.vArrayList_Prod_Promociones.size(); i++)
            {
                log.debug("Entro al for");
                aux = (ArrayList)(agrupado.get(i)); //VariablesVentas.vArrayList_Prod_Promociones.get(i));
                //if((((String)(aux.get(18))).trim()).equalsIgnoreCase(codProm)){
                log.debug("Entro");
                codProd = ((String)(aux.get(0))).trim();
                VariablesVentas.vVal_Frac = ((String)(aux.get(10))).trim();
                cantidad = ((String)(aux.get(4))).trim();
                indControlStk = ((String)(aux.get(16))).trim();
                secRespaldo = ((String)(aux.get(24))).trim(); //ASOSA, 08.07.2010
                log.debug(indControlStk);
                VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(codProd,
                                                       Integer.parseInt(cantidad),
                                                       ConstantsVentas.INDICADOR_D,
                                                       ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR,
                                                       Integer.parseInt(cantidad),
                                                       false,
                                                       this,
                                                       tblProductos))*/
                    !UtilityVentas.operaStkCompProdResp(codProd,
                        //ASOSA, 08.07.2010
                        0, ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, false,
                        this, tblProductos, secRespaldo))
                    return;
                //}
            }
            FarmaUtility.aceptarTransaccion();
            removeItemArray(VariablesVentas.vArrayList_Promociones, codProm, 0);
            removeItemArray(VariablesVentas.vArrayList_Prod_Promociones, codProm, 18);

            log.debug("Resultados despues de Eliminar");
            log.debug("Tamaño de Resumen   :" + VariablesVentas.vArrayList_ResumenPedido.size() + "");
            log.debug("Tamaño de Promocion :" + VariablesVentas.vArrayList_Promociones.size() + "");
            log.debug("Tamaño de Prod_Promocion :" + VariablesVentas.vArrayList_Prod_Promociones.size() + "");

            tableModelResumenPedido.deleteRow(filaActual);
            tblProductos.repaint();
            calculaTotalesPedido();
            if (tableModelResumenPedido.getRowCount() > 0) {
                if (filaActual > 0)
                    filaActual--;
                FarmaGridUtils.showCell(tblProductos, filaActual, 0);
            }
        }
    }

    private void obtieneInfoPedido() {

        try {
            fechaPedido = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            (new FacadeRecaudacion()).obtenerTipoCambio();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener Tipo de Cambio del Dia . \n " + sql.getMessage(), null);
        }
    }

    /**
     * VALIDA NRO DE RUC INGRESADO.
     * @author KMONCADA
     * @since 01.04.2015
     * @param nroRuc
     * @return
     */
    private String verificaRucValido(String nroRuc) {
        String resultado = "";
        try {
            resultado = DBCliente.verificaRucValido(nroRuc);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsCliente.RESULTADO_RUC_INVALIDO;
        }
    }

    /*
     *  Se graba el pedido de venta, segun sea el comprobante
     */
    private synchronized void grabarPedidoVenta(String pTipComp) {
        if (pedidoGenerado) {
            log.debug("El pedido ya fue generado");
            return;
        }

        if (VariablesVentas.vArrayList_ResumenPedido.size() <= 0 &&
            VariablesVentas.vArrayList_Prod_Promociones.size() <= 0) {
            FarmaUtility.showMessage(this, "No hay Productos Seleccionados. Verifique!!!", tblProductos);
            return;
        }

        //LLEIVA 24-Junio-2013 - Se añade la validación para que no cobre los pedidos con precio cero
        if (FarmaUtility.getDecimalNumber(lblTotalS.getText()) <= 0) {
            FarmaUtility.showMessage(this, "El precio del pedido es S/. 0.00. Verifique!!!", tblProductos);
            return;
        }
        boolean aceptaCupones;
        boolean esConvenioBTLMF = false;

        aceptaCupones = validaCampsMontoNetoPedido(lblTotalS.getText().trim());

        //Agregado por FRAMRIEZ 16.12.2011
        log.debug("------------------------------------");
        log.debug("----Asignando el Importe total------");
        //log.debug("------------------------------------" + totalS);
        VariablesConvenioBTLMF.vImpSubTotal = FarmaUtility.getDecimalNumber(lblTotalS.getText());

        int cantCuponesNoUsado = 0;
        Map mapaCupon = new HashMap();
        boolean flagCampAutomatico;
        VariablesVentas.vList_CuponesNoUsados = new ArrayList();
        VariablesVentas.vList_CuponesUsados = new ArrayList();

        //Si existen cupones
        if (VariablesVentas.vArrayList_Cupones.size() > 0) {
            for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
                mapaCupon = new HashMap();
                mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(i);
                //ver si es un cupon de campania automatica.
                flagCampAutomatico =
                        (mapaCupon.get("COD_CAMP_CUPON").toString().indexOf("A") != -1 || mapaCupon.get("COD_CAMP_CUPON").toString().indexOf("L") !=
                         -1) ? true : false;

                if (!flagCampAutomatico) {
                    //ver si se uso o no en resumen pedido
                    boolean flagUso = false;
                    for (int k = 0; k < VariablesVentas.vArrayList_ResumenPedido.size(); k++) {
                        String campUso = (String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(k)).get(25);
                        if (mapaCupon.get("COD_CAMP_CUPON").toString().equals(campUso)) {
                            flagUso = true;
                            break;
                        }
                    }
                    if (!flagUso) {
                        VariablesVentas.vList_CuponesNoUsados.add(mapaCupon);
                        cantCuponesNoUsado++;
                    } else {
                        VariablesVentas.vList_CuponesUsados.add(mapaCupon);
                    }
                }
            }

            if (cantCuponesNoUsado > 0) {
                DlgCupones dlgCupones = new DlgCupones(myParentFrame, "Cupones No Usados", true);
                dlgCupones.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    FarmaVariables.vAceptar = false;
                    aceptaCupones = true;
                } else {
                    aceptaCupones = false;
                }
            }
        }

        if (aceptaCupones) {
            // Valida si el monto del pedido es menor de la suma de los cupones que
            // ingreso, Retorna TRUE si se generara el pedido.
            //if(validaUsoCampanaMonto(lblTotalS.getText().trim()))
            //{

            boolean isAuxiliar = false;
            if (pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_GUIA)) {
                isAuxiliar = true;
                pTipComp = ConstantsVentas.TIPO_COMP_BOLETA;
            }
            try {

                //Se obtiene tipo de comprobante de la relacion maquina - impresora
                if (pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET) ||
                    pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) ||
                    pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
                    pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)) {
                    try {
                        VariablesVentas.vTip_Comp_Ped = DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc, pTipComp);
                    } catch (SQLException sqle) {
                        log.error("", sqle);
                        VariablesVentas.vTip_Comp_Ped = "N";
                    }

                    if (VariablesVentas.vTip_Comp_Ped.trim().equalsIgnoreCase("N")) {
                        if (pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET) ||
                            pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA))
                            FarmaUtility.showMessage(this,
                                                     "La IP no cuenta con una impresora asignada de ticket o boleta. Verifique!!!",
                                                     tblProductos);
                        else
                            FarmaUtility.showMessage(this,
                                                     "La IP no cuenta con una impresora asignada de Factura o Ticket Factura. Verifique!!!",
                                                     tblProductos);

                        return;
                    }
                }
                //else if ((pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) ||
                //         (pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)))
                //{
                //    VariablesVentas.vTip_Comp_Ped = pTipComp.trim();
                //}

                double vValorMaximoCompra = Double.parseDouble(DBVentas.getMontoMinimoDatosCliente());
                double dTotalNeto = FarmaUtility.getDecimalNumber(lblTotalS.getText().trim());
                VariablesVentas.vIndObligaDatosCliente = false;

                if (dTotalNeto >= vValorMaximoCompra) {
                    VariablesVentas.vIndObligaDatosCliente = true;
                }

                if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                    VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.length() > 0) {
                    esConvenioBTLMF = true;
                    if (isAuxiliar)
                        VariablesVentas.vIndObligaDatosCliente = false;
                }

                if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) || (

                    FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_TRADICIONAL))
                    /*&&
                         (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
                          VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)*/
                    )
                // ||

                //VariablesVentas.vIndObligaDatosCliente


                {
                    if (VariablesVentas.vIndObligaDatosCliente ||
                        (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
                         VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT))) {
                        // KMONCADA 01.04.2015 VALIDA DATOS INGRESADOS ANTERIORMENTE
                        if(ConstantsCliente.RESULTADO_RUC_INVALIDO.equalsIgnoreCase(verificaRucValido(VariablesVentas.vRuc_Cli_Ped)) &&
                            (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) ||
                             VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)
                            )
                        ){
                            log.info("RUC INVALIDO");
                            VariablesVentas.vRuc_Cli_Ped = "";
                            VariablesVentas.vNom_Cli_Ped = "";
                        }
                        // Pedir datos si pase del monto indicado
                        if (VariablesVentas.vRuc_Cli_Ped.trim().length() == 0 ||
                            VariablesVentas.vNom_Cli_Ped.trim().length() == 0) {
                            muestraSeleccionComprobante(esConvenioBTLMF);
                        } else {
                            VariablesVentas.vRuc_Cli_Ped = VariablesVentas.vRuc_Cli_Ped.trim();
                            VariablesVentas.vNom_Cli_Ped = VariablesVentas.vNom_Cli_Ped.trim();
                            FarmaVariables.vAceptar = true;
                        }

                        if (VariablesVentas.vRuc_Cli_Ped.trim().length() == 0 &&
                            VariablesVentas.vNom_Cli_Ped.trim().length() == 0) {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this,
                                                     "Por favor ingrese Nombre y Dni del cliente para esta venta.\n" +
                                    "Gracias.", tblProductos);
                            return;
                        }

                    } else
                        FarmaVariables.vAceptar = true;

                    //Se valida si el RUC es valido para cobrar/generar el pedido si tiene descuentos en un fidelizado
                    //LLEIVA 28-Ene-2014 Se añadio la opcion de ticket factura
                    if ((VariablesVentas.vTip_Comp_Ped.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_FACTURA) ||
                         VariablesVentas.vTip_Comp_Ped.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET_FACT)) &&
                        VariablesFidelizacion.vAhorroDNI_Pedido > 0) {
                        if (!UtilityFidelizacion.isRUCValido(VariablesVentas.vRuc_Cli_Ped.trim())) {
                            FarmaUtility.showMessage(this, "Los descuentos son para venta con\n" +
                                    "boleta de venta y para consumo\n" +
                                    "personal. El RUC ingresado queda\n" +
                                    "fuera de la promoción de descuento.", tblProductos);
                            return;
                        }
                    }

                    if (!FarmaVariables.vAceptar) {
                        FarmaUtility.liberarTransaccion();
                        return;
                    } else {
                        if (pedidoGenerado) {
                            FarmaUtility.liberarTransaccion();
                            return;
                        }

                        if (!FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) {
                            if (!JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de realizar el pedido?")) {
                                FarmaUtility.liberarTransaccion();
                                return;
                            }
                        }
                    }
                }
                if (!cargaLogin_verifica()) {
                    FarmaVariables.vAceptar = false;
                    FarmaUtility.liberarTransaccion();
                    return;
                }

                VariablesVentas.vNum_Ped_Vta = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO, 10);
                VariablesVentas.vNum_Ped_Diario = obtieneNumeroPedidoDiario();

                if (VariablesVentas.vNum_Ped_Vta.trim().length() == 0 ||
                    VariablesVentas.vNum_Ped_Diario.trim().length() == 0)
                    throw new SQLException("Error al obtener Numero de Pedido", "Error", 9001);
                //coloca valores de variables de cabecera de pedido
                guardaVariablesPedidoCabecera();
                DBVentas.grabarCabeceraPedido(pIngresoComprobanteManual, VariablesCaja.pManualTipCompPago,
                                              VariablesCaja.pManualSerieComprobante, VariablesCaja.pManualNumCompPago);

                int cont = 0;
                ArrayList array = new ArrayList();

                for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
                    cont++;
                    array = ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
                    //Begin 16-AGO-13, TCT, Prueba Graba Detalles
                    log.debug("<<TCT 10 >> Detalle Resumen Pedido: " + array);
                    //End 16-AGO-13, TCT, Prueba Graba Detalles

                    grabarDetalle_02(pTipComp, array, cont, ConstantsVentas.IND_PROD_SIMPLE);
                }

                for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
                    cont++;
                    array = ((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i));
                    grabarDetalle_02(pTipComp, array, cont, ConstantsVentas.IND_PROD_PROM);
                }

                for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size(); i++) {
                    array = ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i));
                    int cantidad =
                        Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(4));
                    String cod_prom = (String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(0);

                    try {
                        DBVentas.grabaPromXPedidoNoAutomaticos(cod_prom, cantidad);
                    } catch (SQLException e) {
                        log.error("", e);
                    }
                }

                //Se procedera a ver si se puede o no acceder a un producto de regalo por el encarte.
                cont++;
                VariablesVentas.vIndVolverListaProductos = false;
                if (!procesoProductoRegalo(VariablesVentas.vNum_Ped_Vta, cont)) {
                    FarmaUtility.liberarTransaccion();
                    VariablesVentas.vIndVolverListaProductos = true;
                    return;
                }

                FarmaSearch.setNuSecNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO);
                FarmaSearch.setNuSecNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);

                //***********************************CONVENIO BTLMF*****************************************/
                //***********************************INICIO*************************************************/

                /**NUEVO
                     * @Fecha: 14-12-2011
                     * @Autor: FRAMIREZ
                     **/
                if (esConvenioBTLMF) {
                    if (VariablesConvenioBTLMF.vDatosConvenio != null) {
                        log.debug("grabando los datos del convenio BTLMF y el Pedido");

                        for (int j = 0; j < VariablesConvenioBTLMF.vDatosConvenio.size(); j++) {
                            Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(j);

                            String pCodCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO);
                            String pDesCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN);
                            String pNombCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO);
                            String pCodValorCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_COD_VALOR_IN);

                            grabarPedidoConvenioBTLMF(pCodCampo, pDesCampo, pNombCampo, pCodValorCampo);
                        }
                    }
                } else {
                    //***********************************CONVENIO*****************************************/
                    //***********************************INICIO*******************************************/

                    if (!VariablesConvenio.vCodConvenio.equalsIgnoreCase("")) {
                        String vCodCli = "" + VariablesConvenio.vArrayList_DatosConvenio.get(1);
                        String vApePat = "" + VariablesConvenio.vArrayList_DatosConvenio.get(3);
                        String vApeMat = "" + VariablesConvenio.vArrayList_DatosConvenio.get(4);
                        String vNumDoc = "" + VariablesConvenio.vArrayList_DatosConvenio.get(5);
                        String vTelefono = "" + VariablesConvenio.vArrayList_DatosConvenio.get(6);
                        String vCodInterno = "" + VariablesConvenio.vArrayList_DatosConvenio.get(7);
                        String vTrabajador = "" + VariablesConvenio.vArrayList_DatosConvenio.get(8);
                        String vCodTrabEmpresa = "" + VariablesConvenio.vArrayList_DatosConvenio.get(9);
                        String vCodCliDep = VariablesConvenio.vCodClienteDependiente;
                        String vCodTrabEmpDep = VariablesConvenio.vCodTrabDependiente;

                        grabarPedidoConvenio(vCodCli, vNumDoc, vCodTrabEmpresa, vApePat, vApeMat, "", "", vTelefono,
                                             "", "", vCodInterno, vTrabajador, vCodCliDep, vCodTrabEmpDep);

                        double totalS = FarmaUtility.getDecimalNumber(lblTotalS.getText());

                        if (FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0) {
                            VariablesConvenio.vCredito =
                                    DBConvenio.obtieneCredito(VariablesConvenio.vCodConvenio, vCodCli,
                                                              FarmaConstants.INDICADOR_S);
                            VariablesConvenio.vCreditoUtil =
                                    DBConvenio.obtieneCreditoUtil(VariablesConvenio.vCodConvenio, vCodCli,
                                                                  FarmaConstants.INDICADOR_S);
                        }
                        if (FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0 &&
                            !VariablesConvenio.vCredito.equalsIgnoreCase(VariablesConvenio.vCreditoUtil)) {
                            String vFormaPago = DBConvenio.obtieneFormaPagoXConvenio(VariablesConvenio.vCodConvenio);
                            grabarFormaPagoPedido(vFormaPago, VariablesVentas.vNum_Ped_Vta,
                                                  VariablesConvenio.vValCoPago, FarmaVariables.vCodMoneda,
                                                  FarmaUtility.formatNumber(FarmaVariables.vTipCambio), "0",
                                                  VariablesConvenio.vValCoPago, "", "", "", "0");
                        }
                    }
                }
                //*************************************FIN********************************************/
                //***********************************CONVENIO*****************************************/

                //Se graban los cupones validos.
                String cadena, vCodCamp, vIndUso, vIndFid;
                boolean vCampAuto;
                Map mapaCuponAux = new HashMap();
                for (int i = 0; i < VariablesVentas.vList_CuponesUsados.size(); i++) {
                    mapaCuponAux = (Map)VariablesVentas.vList_CuponesUsados.get(i);
                    cadena = mapaCuponAux.get("COD_CUPON").toString();
                    vCodCamp = mapaCuponAux.get("COD_CAMP_CUPON").toString();
                    vIndFid = mapaCuponAux.get("IND_FID").toString();
                    vIndUso = FarmaConstants.INDICADOR_S;
                    vCampAuto = (vCodCamp.indexOf("A") != -1) ? true : false;

                    if (!vCampAuto) { //si no es campania automatica graba cupon
                        DBVentas.grabaPedidoCupon(VariablesVentas.vNum_Ped_Vta, cadena, vCodCamp, vIndUso);
                    }
                }

                //Proceso de campañas Acumuladas
                //Solo se generara el historico y canje si no hay producto recarga
                if (!VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
                    !VariablesVentas.vIndPedConProdVirtual) {

                    //Proceso de campañas Automaticas.
                    if (!esConvenioBTLMF) {
                        procesoPack(VariablesVentas.vNum_Ped_Vta);

                        //Campañas acumuladas
                        procesoCampañasAcumuladas(VariablesVentas.vNum_Ped_Vta, VariablesFidelizacion.vDniCliente);

                        procesoCampañas(VariablesVentas.vNum_Ped_Vta);

                        DBVentas.procesaPedidoCupon(VariablesVentas.vNum_Ped_Vta);
                    }
                }
                
                //INI ASOSA - 15/01/2015 - RIMAC
                boolean indRimac = false;
                if (UtilityCaja.get_ind_ped_con_rimac(VariablesVentas.vNum_Ped_Vta)) {
                    indRimac = UtilityCaja.insProdRegaloRimac(VariablesVentas.vNum_Ped_Vta);
                    if (!indRimac) {
                        FarmaUtility.showMessage(this, "Ya se selecciono un producto rimac", txtDescProdOculto);
                        FarmaUtility.liberarTransaccion();
                        return;
                    }
                }
                //FIN ASOSA - 15/01/2015 - RIMAC

                //grabando numero de tarjeta del pedido de cliente fidelizado
                if (VariablesFidelizacion.vNumTarjeta.length() > 0) {
                    DBFidelizacion.insertarTarjetaPedido();
                }

                //actualizando los descto aplicados por cada productos en el detalle del pedido
                Map mapaDctoProd = new HashMap();
                for (int mm = 0; mm < VariablesVentas.vListDctoAplicados.size(); mm++) {
                    mapaDctoProd = (Map)VariablesVentas.vListDctoAplicados.get(mm);

                    DBVentas.guardaDctosDetPedVta(mapaDctoProd.get("COD_PROD").toString(),
                                                  mapaDctoProd.get("COD_CAMP_CUPON").toString(),
                                                  mapaDctoProd.get("VALOR_CUPON").toString(),
                                                  mapaDctoProd.get("AHORRO").toString(),
                                                  mapaDctoProd.get("DCTO_AHORRO").toString(),
                                                  mapaDctoProd.get("SEC_PED_VTA_DET").toString()); //JMIRANDA 30.10.09 ENVIA SEC_DET_
                }

                if (VariablesVentas.vSeleccionaMedico) {
                    DBVentas.agrega_medico_vta();
                }

                DBVentas.validarValorVentaNeto(VariablesVentas.vNum_Ped_Vta);

                //LLEIVA - 22/Mayo/2013 - Se actualiza, si existe el recetario magistral adjunto al presente pedido
                if (VariablesVentas.vNum_Ped_Vta != "" && VariablesRecetario.strCodigoRecetario != null &&
                    !VariablesRecetario.strCodigoRecetario.equals("")) {
                    DBRecetario.asignarPedidoRM(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia,
                                                FarmaVariables.vCodLocal, VariablesRecetario.strCodigoRecetario,
                                                VariablesVentas.vNum_Ped_Vta);
                    //facadeRecetario.asignarPedidoRM(VariablesRecetario.strCodigoRecetario,
                    //                                VariablesVentas.vNum_Ped_Vta);
                }
                //FIN LLEIVA

                FarmaUtility.aceptarTransaccion();

                //ERIOS 03.09.2013 Determina registro de productos restringidos
                if (VariablesPtoVenta.vIndRegistroVentaRestringida) {
                    if (UtilityVentas.getVentaRestringida(VariablesVentas.vNum_Ped_Vta)) {
                        if (!UtilityVentas.registroDatosRestringidos(myParentFrame)) {
                            pedidoGenerado = false;
                            throw new Exception("No se registraron los datos de venta restringida");
                        }
                    }
                }

                FarmaUtility.aceptarTransaccion();
                
                // PROGRAMA DE PUNTOS 
                try{
                    if(UtilityPuntos.isActivoFuncionalidad()){
                        TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
                        if(tarjetaPuntos!=null){
                            // CALCULO DE PUNTOS
                            Long t1 = System.currentTimeMillis();
                            UtilityPuntos.calculaPuntos(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesVentas.vNum_Ped_Vta, false);
                            Long t2 = System.currentTimeMillis();
                            log.info("PROGRAMA DE PUNTOS [PUNTOS]: TIEMPO DE CALCULO DE PUNTOS: "+(t2-t1));
                            // INICIO DE METODO QUOTE
                            log.info("********************************************************************************");
                            log.info("****** PROGRAMA PUNTOS [QUOTE]: INICIO DE LOGICA PARA ENVIO DE METODO QUOTE");
                            log.info("********************************************************************************");
                            FarmaUtility.aceptarTransaccion();
                            boolean isLlevaPromociones = false;
                            //procesarToto(VariablesVentas.vNum_Ped_Vta);
                            int cantPromociones =  0;
                            if(actuaRobot){
                                DBPuntos.agregaPromoBonificados(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesVentas.vNum_Ped_Vta, numPedVtaRobot);
                                FarmaUtility.aceptarTransaccion();
                                actuaRobot = false;
                                numPedVtaRobot = "";
                                FarmaVariables.vAceptar = true;
                            }else{
                                UtilityPuntos.enviarPedidoPuntos(this, VariablesVentas.vNum_Ped_Vta, myParentFrame, isLlevaPromociones);
                                FarmaUtility.aceptarTransaccion();
                                cantPromociones = DBPuntos.getCantidadProductoPromociones(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesVentas.vNum_Ped_Vta);
                            }
                            /*FarmaUtility.aceptarTransaccion();
                            int cantPromociones = DBPuntos.getCantidadProductoPromociones(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesVentas.vNum_Ped_Vta);
                            */
                            if(cantPromociones>0){
                                actuaRobot = true;
                                FarmaVariables.vAceptar = false;
                            }else{
                                actuaRobot = false;
                                DBPuntos.agregaPromoBonificados(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesVentas.vNum_Ped_Vta, VariablesVentas.vNum_Ped_Vta);
                                FarmaUtility.aceptarTransaccion();
                            }
                            if(!FarmaVariables.vAceptar)
                                return;
                            else
                                UtilityPuntos.calculaPuntos(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesVentas.vNum_Ped_Vta, true);
                        }
                    }
                }catch(Exception ex){
                    log.error("PROGRAMA DE PUNTOS : ERROR EN PROCESO DE QUOTE "+ex.getMessage());
                    log.error("", ex);
                    if(VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                        VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                        log.error("PROGRAMA DE PUNTOS : SE ELIMINO LA TARJETA DEL PROGRAMA");
                    }
                }
                
                pedidoGenerado = true;

                //ERIOS 2.2.8 Carga variables
                frmEconoFar.obtieneInfoLocal();

                if (FarmaVariables.vTipCaja.equalsIgnoreCase("") || FarmaVariables.vTipCaja.equalsIgnoreCase(null)) {
                    FarmaVariables.vTipLocal = "M";
                }

                if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) {
                    VariablesCaja.vNumPedPendiente = VariablesVentas.vNum_Ped_Diario;

                    if (DBPtoVenta.getIndicadorNuevoCobro()) {
                        mostrarNuevoCobro();
                    } else {
                        muestraCobroPedido();
                    }
                    
                    if(!pedidoGenerado){

                            log.info("regresando");
                            log.info(""+tableModelResumenPedidoAux.data);
                            log.info("goggoogogogoog");
                            log.info(""+tableModelResumenPedido.data);
                            
                            /*tableModelResumenPedido.data = (ArrayList)tableModelResumenPedidoAux.data.clone();
                            VariablesVentas.vArrayList_ResumenPedido = (ArrayList)lstAuxResumenPedido.clone();
                            */
                            if(tableModelResumenPedidoAux.getRowCount()>0){
                                tableModelResumenPedido.clearTable();
                                for(int i=0;i<tableModelResumenPedidoAux.getRowCount();i++){
                                    tableModelResumenPedido.insertRow((ArrayList)((ArrayList)tableModelResumenPedidoAux.data.get(i)).clone());
                                }
                                
                                VariablesVentas.vArrayList_ResumenPedido.clear();
                                for(int i=0;i<lstAuxResumenPedido.size();i++){
                                    VariablesVentas.vArrayList_ResumenPedido.add((ArrayList)((ArrayList)lstAuxResumenPedido.get(i)).clone());
                                }
                                
                                tblProductos.repaint();
                                neoOperaResumenPedido();
                            }
                        
                    }

                    if (VariablesFidelizacion.vRecalculaAhorroPedido) {
                        VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                                UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,
                                                                               VariablesFidelizacion.vNumTarjeta);
                        VariablesFidelizacion.vAhorroDNI_x_Periodo =
                                UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,
                                                                               VariablesFidelizacion.vNumTarjeta);
                        neoOperaResumenPedido();
                    }
                } else {
                    if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_TRADICIONAL))
                        muestraPedidoRapido();
                }

                VariablesVentas.vArrayListCuponesCliente.clear();
                VariablesVentas.dniListCupon = "";

            } catch (SQLException sql) {
                FarmaUtility.liberarTransaccion();
                //log.error("",sql);
                log.error(null, sql);
                if (sql.getErrorCode() == 20066) {
                    FarmaUtility.showMessage(this,
                                             "Error en Base de Datos al Generar Pedido.\n Inconsistencia en los montos calculados",
                                             tblProductos);
                } else {
                    FarmaUtility.showMessage(this, "Error en Base de Datos al grabar el pedido.\n" +
                            sql, tblProductos);
                }
            } catch (Exception exc) {
                FarmaUtility.liberarTransaccion();
                log.error(null, exc);
                FarmaUtility.showMessage(this, "Error en la aplicacion al grabar el pedido.\n" +
                        exc.getMessage(), tblProductos);
            }
        }
        //}
    }

    private void validaConvenio_v3(KeyEvent e, String vCoPagoConvenio) {
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
            VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.length() > 0) {
            boolean result = true;
            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef != null &&
                VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                result = existeSaldoCredDispBenif(this);
            }
            if (result)
                validaConvenio_v2(e, VariablesConvenio.vPorcCoPago);
        } else {
            validaConvenio_v2(e, VariablesConvenio.vPorcCoPago);
        }
    }


    public class KeyDispatcher implements Runnable {
        @Override
        public void run() {
            log.info("antes del hilo");
            log.info("auxiliar --> "+tableModelResumenPedidoAux.data);
            log.info("goggoogogogoog");
            log.info("original --> "+tableModelResumenPedido.data);
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
            //dispatchKeyEventsViaEventQueue();
            dispatchKeyEventsViaRobot();
            
            log.info("termino el hilo");
            log.info("auxiliar --> "+tableModelResumenPedidoAux.data);
            log.info("goggoogogogoog");
            log.info("original --> "+tableModelResumenPedido.data);
        }

        protected void dispatchKeyEventsViaEventQueue() {
            if (EventQueue.isDispatchThread()) {
                String text = "This is a key sequence dispatched via the event queue\n";
                KeySequence keySequence = TestKeyEvents.getKeySequence(text);
                List<KeyEvent> events = new ArrayList<KeyEvent>();
                List<Integer> modifers = new ArrayList<Integer>();
                for (Key key : keySequence) {
                    events.clear();
                    System.out.println(key);
                    switch (key.getStrokeType()) {
                        case Press:
                            switch (key.getKeyCode()) {
                                case KeyEvent.VK_SHIFT:
                                case KeyEvent.VK_ALT:
                                case KeyEvent.VK_CONTROL:
                                case KeyEvent.VK_META:
                                    if (!modifers.contains(key.getKeyCode())) {
                                        modifers.add(key.getKeyCode());
                                    }
                                    break;
                                default:
                                    events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), TestKeyEvents.getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                                    break;
                            }
                            break;
                        case Release:
                            switch (key.getKeyCode()) {
                                case KeyEvent.VK_SHIFT:
                                case KeyEvent.VK_ALT:
                                case KeyEvent.VK_CONTROL:
                                case KeyEvent.VK_META:
                                    if (!modifers.contains(key.getKeyCode())) {
                                        modifers.remove(key.getKeyCode());
                                    }
                                    break;
                                default:
                                    events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), TestKeyEvents.getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                                    break;
                            }
                            break;
                        case Type:
                            events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), TestKeyEvents.getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                            events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), TestKeyEvents.getModifiers(modifers), key.getKeyCode(), key.getKeyChar()));
                            events.add(new KeyEvent(new JPanel(), KeyEvent.KEY_TYPED, System.currentTimeMillis(), TestKeyEvents.getModifiers(modifers), KeyEvent.VK_UNDEFINED, key.getKeyChar()));
                            break;
                    }

                    for (KeyEvent evt : events) {
                        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(evt);
                    }
                }
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            dispatchKeyEventsViaEventQueue();
                        }
                    });
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        }

        protected void dispatchKeyEventsViaRobot() {
            try {
                Robot robot = new Robot();
                log.info("cadena a operar por el robot --> "+cadenaTex);
                String text = cadenaTex;
                //@ --> F5 , * --> ENTER, ! --> F1, $ --> F4  , ?--> FLECHA ARRIBA , ¿ FLECHA ABAJO
                KeySequence keySequence = TestKeyEvents.getKeySequence(text);
                List<KeyEvent> events = new ArrayList<>();
                for (Key key : keySequence) {
                    log.info("ejecucion del hilo");
                    log.info("auxiliar --> "+tableModelResumenPedidoAux.data);
                    log.info("goggoogogogoog");
                    log.info("original --> "+tableModelResumenPedido.data);
                    events.clear();
                    System.out.println(key);
                    switch (key.getStrokeType()) {
                        case Press:
                            robot.keyPress(key.getKeyCode());
                            break;
                        case Release:
                            robot.keyRelease(key.getKeyCode());
                            break;
                        case Type:
                            robot.keyPress(key.getKeyCode());
                            robot.keyRelease(key.getKeyCode());
                            break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (AWTException exp) {
                exp.printStackTrace();
            }
            FarmaUtility.moveFocus(tblProductos);
        }
    }
    
    
    
    private void procesarToto(String pNumPedVta, String vkF)throws Exception{
        
        /*tableModelResumenPedidoAux.clearTable();
        for(int i=0;i<tblProductos.getRowCount();i++){
            tableModelResumenPedidoAux.insertRow((ArrayList)((ArrayList)tableModelResumenPedido.data.get(i)).clone());
        }*/
        
        tableModelResumenPedidoAux.clearTable();
        for(int i=0;i<tblProductos.getRowCount();i++){
            tableModelResumenPedidoAux.insertRow((ArrayList)((ArrayList)tableModelResumenPedido.data.get(i)).clone());
        }

        lstAuxResumenPedido.clear();
        for(int i=0;i<VariablesVentas.vArrayList_ResumenPedido.size();i++){
            lstAuxResumenPedido.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).clone());
        }
        
        
        String lista = DBPuntos.getDiferenciasPromociones(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumPedVta);
        log.info("PROGRAMA DE PUNTOS : LISTA DE PRODUCTOS DE PROMOCIONES "+lista);
        String array[] = lista.split("@");
        //@ --> F5 , * --> ENTER, ! --> F1, $ --> F4  , ?--> FLECHA ARRIBA , ¿ FLECHA ABAJO
        cadenaTex = "¿";
        int posAnterior = tblProductos.getSelectedRow();
        for(int i=0;i<array.length;i++){
            String[] prods = array[i].split(","); // codigo , cantidad
            String codProd = prods[0].trim();
            int cantidad = (int)Integer.parseInt(prods[1].trim());
            int k=0;
            boolean isSelecciono = false;
            if(posAnterior>0){
                for(int j=0;j<posAnterior;j++){
                    cadenaTex = cadenaTex+"?";
                }
            }
            while(k<tblProductos.getRowCount() && !isSelecciono ){
                if(codProd.equalsIgnoreCase(FarmaUtility.getValueFieldArrayList(tableModelResumenPedido.data, k, 0))){
                    isSelecciono = true;
                    //tblProductos.getSelectionModel().setSelectionInterval(k,k);
                    posAnterior = k;
                }
                if(k>0){
                    cadenaTex = cadenaTex+"¿";
                }
                k++;
            }
            
            if(isSelecciono){
                //vEjecutaAccionTeclaResumen = false;
                
                if(cantidad==0){
                    //realizar f5
                    cadenaTex = cadenaTex+"@*";
                }else{
                    // realizar enter
                    cadenaTex = cadenaTex+"*"+cantidad+"*";
                }
                //new Thread(new KeyDispatcher()).start();
            }
        }
        numPedVtaRobot = pNumPedVta;
        if("F1".equalsIgnoreCase(vkF)){
            cadenaTex = cadenaTex+"!";
        }else{
            if("F4".equalsIgnoreCase(vkF)){
                cadenaTex = cadenaTex+"$";
            }
        }
        new Thread(new KeyDispatcher()).start();
    }

    private String obtieneNumeroPedidoDiario() throws SQLException {

        String feModNumeracion = DBVentas.obtieneFecModNumeraPed();
        String feHoyDia = "";
        String numPedDiario = "";
        if (!(feModNumeracion.trim().length() > 0))
            throw new SQLException("Ultima Fecha Modificacion de Numeración Diaria del Pedido NO ES VALIDA !!!",
                                   "Error", 0001);
        else {
            feHoyDia = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            feHoyDia = feHoyDia.trim().substring(0, 2);
            feModNumeracion = feModNumeracion.trim().substring(0, 2);
            if (Integer.parseInt(feHoyDia) != Integer.parseInt(feModNumeracion)) {
                FarmaSearch.inicializaNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
                numPedDiario = "0001";
            } else {
                // Obtiene el Numero de Atencion del Día y hace SELECT FOR UPDATE.
                numPedDiario = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO, 4);
            }
        }
        return numPedDiario;
    }

    private void guardaVariablesPedidoCabecera() {
        VariablesVentas.vIndDistrGratuita = FarmaConstants.INDICADOR_N;
        VariablesVentas.vVal_Bruto_Ped = lblBruto.getText().trim();
        VariablesVentas.vVal_Neto_Ped = lblTotalS.getText().trim();
        VariablesVentas.vVal_Redondeo_Ped = lblRedondeo.getText().trim();
        VariablesVentas.vVal_Igv_Ped = lblIGV.getText().trim();
        VariablesVentas.vVal_Dcto_Ped = lblDscto.getText().trim();
        if (VariablesVentas.vEsPedidoDelivery)
            VariablesVentas.vTip_Ped_Vta = ConstantsVentas.TIPO_PEDIDO_DELIVERY; //verificar q tipo de pedido es...
        else if (VariablesVentas.vEsPedidoInstitucional)
            VariablesVentas.vTip_Ped_Vta =
                    ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL; //verificar q tipo de pedido es...
        else
            VariablesVentas.vTip_Ped_Vta = ConstantsVentas.TIPO_PEDIDO_MESON; //verificar q tipo de pedido es...
        VariablesVentas.vCant_Items_Ped = lblItems.getText().trim();
        VariablesVentas.vEst_Ped_Vta_Cab = ConstantsVentas.ESTADO_PEDIDO_PENDIENTE;

    }

    private void muestraPedidoRapido() {
        DlgNumeroPedidoGenerado dlgNumeroPedidoGenerado = new DlgNumeroPedidoGenerado(myParentFrame, "", true);
        dlgNumeroPedidoGenerado.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        }
    }

    private void limpiaValoresPedido() {

        VariablesVentas.vValorMultiplicacion = "1"; //ASOSA - 12/08/2014

        VariablesVentas.vQFApruebaVTANEGATIVA = "";
        VariablesVentas.vCodSolicitudVtaNegativa = "";
        VariablesVentas.vQFApruebaVTANEGATIVA = "";

        VariablesVentas.vCod_Prod = "";
        VariablesVentas.vDesc_Prod = "";
        VariablesVentas.vNom_Lab = "";
        VariablesVentas.vUnid_Vta = "";
        VariablesVentas.vVal_Prec_Vta = "";
        VariablesVentas.vPorc_Dcto_1 = "";
        VariablesVentas.vVal_Bono = "";
        VariablesVentas.vDesc_Acc_Terap = "";
        VariablesVentas.vStk_Prod = "";
        VariablesVentas.vStk_Prod_Fecha_Actual = "";
        VariablesVentas.vVal_Frac = "";
        VariablesVentas.vVal_Prec_Lista = "";
        VariablesVentas.vVal_Igv_Prod = "";
        VariablesVentas.vPorc_Igv_Prod = "";
        VariablesVentas.vEst_Ped_Vta_Cab = "";

        VariablesVentas.vCant_Ingresada = "";
        VariablesVentas.vCant_Ingresada_Temp = "";
        VariablesVentas.vTotalPrecVtaProd = 0;

        VariablesVentas.vArrayList_PedidoVenta = new ArrayList();
        VariablesVentas.vArrayList_ResumenPedido = new ArrayList();

        VariablesVentas.vArrayList_Promociones = new ArrayList();
        VariablesVentas.vArrayList_Prod_Promociones = new ArrayList();

        VariablesVentas.vNum_Ped_Vta = "";
        VariablesVentas.vVal_Bruto_Ped = "";
        VariablesVentas.vVal_Neto_Ped = "";
        VariablesVentas.vVal_Redondeo_Ped = "";
        VariablesVentas.vVal_Igv_Ped = "";
        VariablesVentas.vVal_Dcto_Ped = "";

        VariablesVentas.vNom_Cli_Ped = "";
        VariablesVentas.vDir_Cli_Ped = "";
        VariablesVentas.vRuc_Cli_Ped = "";
        VariablesVentas.vTip_Comp_Ped = "";
        VariablesVentas.vCant_Items_Ped = "";
        VariablesVentas.vNum_Ped_Diario = "";
        VariablesVentas.vTip_Ped_Vta = "";
        VariablesVentas.vCod_Cli_Local = "";
        VariablesVentas.vSec_Ped_Vta_Det = "";
        VariablesVentas.vPorc_Dcto_Total = "";
        VariablesVentas.vEst_Ped_Vta_Det = "";
        VariablesVentas.vVal_Total_Bono = "";
        VariablesVentas.vSec_Usu_Local = "";

        VariablesVentas.vEsPedidoDelivery = false;
        VariablesVentas.vEsPedidoConvenio = false;
        VariablesVentas.vEsPedidoInstitucional = false;
        VariablesVentas.vIngresaCant_ResumenPed = false;

        VariablesVentas.vIndPedConProdVirtual = false;
        VariablesVentas.vIndProdControlStock = true;
        VariablesVentas.vNumeroARecargar = "";
        VariablesVentas.vMontoARecargar = "";
        VariablesVentas.vMontoARecargar_Temp = "";
        VariablesVentas.vIndProdVirtual = "";
        VariablesVentas.vTipoProductoVirtual = "";
        VariablesVentas.vVal_Prec_Lista_Tmp = "";

        VariablesConvenio.vCodConvenio = "";
        VariablesConvenio.vCodCliente = "";
        VariablesConvenio.vPorcCoPago = "";
        VariablesConvenio.vPorcDctoConv = "";
        VariablesConvenio.vNomConvenio = "";
        VariablesConvenio.vValCoPago = "";
        VariablesConvenio.vCreditoUtil = "";
        //VariablesConvenio.vTextoCliente = "" ;

        VariablesVentas.vCodProd_Regalo = "";
        VariablesVentas.vCantidad_Regalo = 0;
        VariablesVentas.vMontoMinConsumoEncarte = 0.00;
        VariablesVentas.vDescProxProd_Regalo = "";
        VariablesVentas.vMontoProxMinConsumoEncarte = 0.00;
        VariablesVentas.vIndVolverListaProductos = false;

        VariablesVentas.vCodCampCupon = "";
        VariablesVentas.vDescCupon = "";
        VariablesVentas.vMontoPorCupon = 0.00;

        VariablesVentas.vIndOrigenProdVta = "";
        VariablesVentas.vPorc_Dcto_2 = "";
        VariablesVentas.vIndDireccionarResumenPed = false;
        VariablesVentas.vIndF11 = false;
        VariablesVentas.vKeyPress = null;

        VariablesVentas.vArrayList_Cupones = new ArrayList();
        //SE LIMPIAN LAS VARABLES DE FIDELIZACION
        //29.09.2008 DUBILLUZ
        VariablesFidelizacion.vNumTarjeta = "";
        VariablesFidelizacion.vNomCliente = "";
        VariablesFidelizacion.vApeMatCliente = "";
        VariablesFidelizacion.vApePatCliente = "";
        VariablesFidelizacion.vDataCliente = new ArrayList();
        VariablesFidelizacion.vDireccion = "";
        VariablesFidelizacion.vDniCliente = "";
        VariablesFidelizacion.vEmail = "";
        VariablesFidelizacion.vFecNacimiento = "";
        VariablesFidelizacion.vIndAgregoDNI = "N";
        VariablesFidelizacion.vIndExisteCliente = false;
        VariablesFidelizacion.vNomClienteImpr = "";
        VariablesFidelizacion.vSexo = "";
        VariablesFidelizacion.vSexoExists = false;
        VariablesFidelizacion.vTelefono = "";
        VariablesFidelizacion.vSIN_COMISION_X_DNI = false;
        VariablesFidelizacion.vListCampañasFidelizacion = new ArrayList();

        VariablesVentas.vMensCuponIngre = "";

        //Limpia Variables de Fidelizacion de FORMA DE PAGO
        //dubilluz 09.06.2011
        VariablesFidelizacion.vIndUsoEfectivo = "NULL";
        VariablesFidelizacion.vIndUsoTarjeta = "NULL";
        VariablesFidelizacion.vCodFPagoTarjeta = "NULL";
        VariablesFidelizacion.tmpCodCampanaCupon = "N";
        lblFormaPago.setText("");
        lblFormaPago.setVisible(false);

        /**
     * jcallo inhabilitar delivery cuando el parametro sea NO
     * ***/
        try {
            ArrayList lprms = new ArrayList();
            DBVentas.getParametrosLocal(lprms); //[0]:impresora,[1]:max minutos,[2]:ind_activo
            ArrayList prms = (ArrayList)lprms.get(0);

            // JCORTEZ 06.08.09
            if (prms.get(2).toString().equals("NO")) {
                VariablesVentas.HabilitarF9 = ConstantsVentas.INACTIVO;
                //lblF9.setVisible(false);
            } else {
                VariablesVentas.HabilitarF9 = ConstantsVentas.ACTIVO;
                //lblF9.setVisible(true);
            }

            //JCORTEZ 07.08.09 Se limpia variables tipo pedido Delivery
            VariablesDelivery.vCodCli = "";
            VariablesDelivery.vNombreCliente = "";
            VariablesDelivery.vNumeroTelefonoCabecera = "";
            VariablesDelivery.vDireccion = "";
            VariablesDelivery.vNumeroDocumento = "";
            VariablesDelivery.vObsCli = "";

            lblF9.setVisible(true);

        } catch (Exception e) {
            log.debug("ERROR : " + e);
        }

        // KMONCADA 2015.02.13 PROGRAMA DE PUNTOS
        if(UtilityPuntos.isActivoFuncionalidad()){
            VariablesPuntos.frmPuntos.eliminarTarjetaBean();
        }
    }

    private void colocaUltimoPedidoDiarioVendedor() {
        String pedDiario = "";
        try {
            pedDiario = DBVentas.obtieneUltimoPedidoDiario();
            if (pedDiario.equalsIgnoreCase("0000"))
                pedDiario = "----";
            lblUltimoPedido.setText(pedDiario);
        } catch (SQLException sql) {
            log.error("", sql);
            lblUltimoPedido.setText("----");
            FarmaUtility.showMessage(this, "Error al obtener ultimo pedido diario. \n" +
                    sql.getMessage(), tblProductos);
        }
    }

    private void muestraSeleccionComprobante(boolean esConvenioBTLMF) {
        DlgSeleccionTipoComprobante dlgSeleccionTipoComprobante =
            new DlgSeleccionTipoComprobante(myParentFrame, "", true);
        dlgSeleccionTipoComprobante.setIsActivaF2(!esConvenioBTLMF);
        dlgSeleccionTipoComprobante.setNroDocumentoCliente(VariablesVentas.vRuc_Cli_Ped);
        dlgSeleccionTipoComprobante.setNombreCliente(VariablesVentas.vNom_Cli_Ped);
        dlgSeleccionTipoComprobante.setDireccionCliente(VariablesVentas.vDir_Cli_Ped);
        dlgSeleccionTipoComprobante.setVisible(true);
    }

    private void muestraCobroPedido() {
        // DUBILLUZ 04.02.2013
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();

        DlgFormaPago dlgFormaPago = new DlgFormaPago(myParentFrame, "", true);
        dlgFormaPago.setIndPedirLogueo(false);
        dlgFormaPago.setIndPantallaCerrarAnularPed(true);
        dlgFormaPago.setIndPantallaCerrarCobrarPed(true);

        //INI ASOSA - 03.07.2014
        String descProd = FarmaUtility.getValueFieldArrayList(tableModelResumenPedido.data, 0, 1);
        log.info("producto cobro pedido: " + descProd);
        dlgFormaPago.setDescProductoRecVirtual(descProd);
        dlgFormaPago.setDescProductoRimac(descProd);    //ASOSA - 12/01/2015 - RIMAC
        //FIN ASOSA - 03.07.2014

        dlgFormaPago.setVisible(true);
        log.info("XXXXX_FarmaVariables.vAceptar:" + FarmaVariables.vAceptar);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        } else
            pedidoGenerado = false;
    }

    private void evaluaTitulo() {
        //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
        //lblCliente.setText(nombreCliente);

        if (VariablesVentas.vEsPedidoDelivery) {
            this.setTitle("Resumen de Pedido - Pedido Delivery" + " /  IP : " + FarmaVariables.vIpPc);
            String nombreCliente =
                VariablesDelivery.vNombreCliente + " " + VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
            lblCliente.setText(nombreCliente);
        } else if (VariablesVentas.vEsPedidoInstitucional) {
            lblCliente.setText("");
            this.setTitle("Resumen de Pedido - Pedido Institucional" + " /  IP : " + FarmaVariables.vIpPc);
        } else if (VariablesVentas.vEsPedidoConvenio) {
            lblCliente.setText(VariablesConvenio.vTextoCliente);
            this.setTitle("Resumen de Pedido - Pedido por Convenio: " + VariablesConvenio.vNomConvenio + " /  IP : " +
                          FarmaVariables.vIpPc);
            log.debug("------------------------" + this.getTitle());
            log.debug("VariablesConvenio.vTextoCliente : *****" + VariablesConvenio.vTextoCliente);

            lblLCredito_T.setText(VariablesConvenioBTLMF.vDatoLCredSaldConsumo);
            lblBeneficiario_T.setText(getMensajeComprobanteConvenio(VariablesConvenioBTLMF.vCodConvenio));

        } else {
            this.setTitle("Resumen de Pedido" + " /  IP : " + FarmaVariables.vIpPc + " / " + FrmEconoFar.tituloBaseFrame);
            log.debug("VariablesConvenio.vTextoCliente vacio");
            if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                //jcallo 02.10.2008
                lblCliente.setText(VariablesFidelizacion.vNomCliente); /*+" "
                             +VariablesFidelizacion.vApePatCliente+" "
                             +VariablesFidelizacion.vApeMatCliente);*/
                //fin jcallo 02.10.2008
            } else {
                lblCliente.setText("");
            }
        }
    }

    // Inicio Adicion Delivery 28/04/2006 Paulo

    private void generarPedidoDelivery() {
        DlgListaClientes dlgListaClientes = new DlgListaClientes(myParentFrame, "", true);
        dlgListaClientes.setVisible(true);
        if (FarmaVariables.vAceptar) {
            String nombreCliente =
                VariablesDelivery.vNombreCliente + " " + VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
            lblCliente.setText(nombreCliente);
            log.debug("Antes de Evaluar titulo");
            FarmaVariables.vAceptar = false;
            VariablesVentas.vEsPedidoDelivery = true;
        } else {
            if (FarmaVariables.vImprTestigo.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                log.debug("Evaluando titulo");
                evaluaTitulo();
                VariablesVentas.vEsPedidoDelivery = false;
            }
        }
    }

    private void limpiaVariables() {

        VariablesDelivery.vNumeroTelefono = "";
        VariablesDelivery.vNombreInHashtable = "";
        VariablesDelivery.vNombreInHashtableVal = "";
        VariablesDelivery.vCampo = "";
        VariablesDelivery.vCantidadLista = "";
        VariablesDelivery.vDni_Apellido_Busqueda = "";
        VariablesDelivery.vTipoBusqueda = "";
        VariablesDelivery.vNumeroTelefonoCabecera = "";
        VariablesDelivery.vDireccion = "";
        VariablesDelivery.vDistrito = "";
        VariablesDelivery.vCodigoDireccion = "";
        VariablesDelivery.vInfoClienteBusqueda = new ArrayList();
        VariablesDelivery.vInfoClienteBusquedaApellidos = new ArrayList();
        VariablesDelivery.vCodCli = "";
        VariablesDelivery.vObsCli = "";
        VariablesDelivery.vCodDirTable = "";
        VariablesDelivery.vCodDirTmpTable = "";
        VariablesDelivery.vNombreCliente = "";
        VariablesDelivery.vApellidoPaterno = "";
        VariablesDelivery.vApellidoMaterno = "";
        VariablesDelivery.vTipoDocumento = "";
        VariablesDelivery.vTipoCliente = "";
        VariablesDelivery.vNumeroDocumento = "";
        VariablesDelivery.vTipoCalle = "";
        VariablesDelivery.vNombreCalle = "";
        VariablesDelivery.vNumeroCalle = "";
        VariablesDelivery.vNombreUrbanizacion = "";
        VariablesDelivery.vNombreDistrito = "";
        VariablesDelivery.vReferencia = "";
        VariablesDelivery.vNombreInterior = "";
        VariablesDelivery.vModificacionDireccion = "";
        VariablesDelivery.vTipoAccion = "";
        VariablesDelivery.vTipoCampo = "";
        VariablesDelivery.vCodTelefono = "";
        VariablesVentas.vArrayList_Medicos = new ArrayList();
        VariablesVentas.vCodMed = "";
        VariablesVentas.vMatriculaApe = "";
        VariablesVentas.vCodListaMed = "";
        VariablesVentas.vMatriListaMed = "";
        VariablesVentas.vNombreListaMed = "";
        VariablesVentas.vSeleccionaMedico = false;
        VariablesReceta.vNum_Ped_Rec = "";
        VariablesReceta.vVerReceta = false;
        VariablesVentas.vEsPedidoConvenio = false;
        VariablesVentas.venta_producto_virtual = false;
        //limpia Variables de Cliente Dependiente
        VariablesConvenio.vCodClienteDependiente = "";
        VariablesConvenio.vCodTrabDependiente = "";
        // VariablesConvenio
        VariablesConvenio.vTextoCliente = "";
        VariablesConvenio.vCodTrab = "";
        VariablesConvenio.vNomCliente = "";
        //VariablesConvenio.vVal_Prec_Vta_Conv = "";
        //Limipia Variables De DNI para control de maximo ahorro
        //DUBILLUZ 28.05.2009
        VariablesFidelizacion.vDNI_Anulado = false;
        VariablesFidelizacion.vAhorroDNI_x_Periodo = 0;
        VariablesFidelizacion.vMaximoAhorroDNIxPeriodo = 0;
        VariablesFidelizacion.vAhorroDNI_Pedido = 0;
        VariablesFidelizacion.vIndComprarSinDcto = false;
        VariablesFidelizacion.vRecalculaAhorroPedido = false;
        //jquispe 01.08.2011 limpia variables de fidelizacion
        //VariablesFidelizacion.limpiaVariables();

        VariablesFidelizacion.vNumTarjeta = "";
        VariablesFidelizacion.vNomCliente = "";
        VariablesFidelizacion.vApeMatCliente = "";
        VariablesFidelizacion.vApePatCliente = "";
        VariablesFidelizacion.vDataCliente = new ArrayList();
        VariablesFidelizacion.vDireccion = "";
        VariablesFidelizacion.vDniCliente = "";
        VariablesFidelizacion.vEmail = "";
        VariablesFidelizacion.vFecNacimiento = "";
        VariablesFidelizacion.vIndAgregoDNI = "N";
        VariablesFidelizacion.vIndExisteCliente = false;
        VariablesFidelizacion.vNomClienteImpr = "";
        VariablesFidelizacion.vSexo = "";
        VariablesFidelizacion.vSexoExists = false;
        VariablesFidelizacion.vTelefono = "";

        VariablesFidelizacion.vListCampañasFidelizacion = new ArrayList();

        VariablesVentas.vMensCuponIngre = "";

        //Limpia Variables de Fidelizacion de FORMA DE PAGO
        //dubilluz 09.06.2011
        VariablesFidelizacion.vIndUsoEfectivo = "NULL";
        VariablesFidelizacion.vIndUsoTarjeta = "NULL";
        VariablesFidelizacion.vCodFPagoTarjeta = "NULL";
        VariablesFidelizacion.tmpCodCampanaCupon = "N";
        VariablesFidelizacion.vNumTarjetaCreditoDebito_Campana = "";
        VariablesFidelizacion.tmp_NumTarjeta_unica_Campana = "";

        ///////////////////////////////////////////////
        VariablesFidelizacion.V_NUM_CMP = "";
        VariablesFidelizacion.V_NOMBRE = "";
        VariablesFidelizacion.V_DESC_TIP_COLEGIO = "";
        VariablesFidelizacion.V_TIPO_COLEGIO = "";
        VariablesFidelizacion.V_COD_MEDICO = "";
        VariablesFidelizacion.vColegioMedico = "";
        VariablesFidelizacion.vSIN_COMISION_X_DNI = false;

        ///////////////////////////////////////////////
        
        
        tableModelResumenPedidoAux.clearTable();
        tableModelResumenPedidoAux.fireTableDataChanged();
        actuaRobot = false;
        numPedVtaRobot = "";
        cadenaTex = "";

    }
    // Fin Adicion Delivery 28/04/2006 Paulo

    //Inicio Adicion Lista Medicos 26/06/2006 Paulo

    private void agregaMedicoVta() {
        try {
            DBVentas.agrega_medico_vta();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al grabar medico \n " + sql.getMessage(), tblProductos);
        }
    }
    //Fin Adicion Lista Medicos 26/06/2006 Paulo

    /**
     * Se muestra el resumen de la receta generada.
     * @author Edgar Rios Navarro
     * @since 14.12.2006
     */
    private void verReceta() {
        if (VariablesVentas.vSeleccionaMedico) {
            VariablesReceta.vVerReceta = true;
            DlgResumenReceta dlgResumenReceta = new DlgResumenReceta(myParentFrame, "", true);
            dlgResumenReceta.setVisible(true);
        }
    }

    /**
     * Muestra pantalla ingreso de numero telefonico y monto de recarga
     * @author Luis Mesia Rivera
     * @since 08.01.2007
     */
    private void muestraIngresoTelefonoMonto() {
        if (tblProductos.getRowCount() == 0)
            return;
        int filaActual = tblProductos.getSelectedRow();
        VariablesVentas.vCod_Prod = ((String)(tblProductos.getValueAt(filaActual, 0))).trim();
        VariablesVentas.vDesc_Prod = ((String)(tblProductos.getValueAt(filaActual, 1))).trim();
        VariablesVentas.vNom_Lab = ((String)(tblProductos.getValueAt(filaActual, 9))).trim();
        VariablesVentas.vVal_Frac = ((String)(tblProductos.getValueAt(filaActual, 10))).trim();
        VariablesVentas.vPorc_Igv_Prod = ((String)(tblProductos.getValueAt(filaActual, 11))).trim();
        VariablesVentas.vPorc_Dcto_1 = ((String)(tblProductos.getValueAt(filaActual, 5))).trim();
        VariablesVentas.vVal_Prec_Vta = ((String)(tblProductos.getValueAt(filaActual, 6))).trim();

        //Obtenemos la cantidad que recargo
        //31.10.2007  dubilluz  modificacion
        //VariablesVentas.vMontoARecargar_Temp = ((String)(tblProductos.getValueAt(filaActual,6))).trim();
        VariablesVentas.vMontoARecargar_Temp = ((String)(tblProductos.getValueAt(filaActual, 4))).trim();

        VariablesVentas.vNumeroARecargar = ((String)(tblProductos.getValueAt(filaActual, 13))).trim();
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";

        DlgIngresoRecargaVirtual dlgIngresoRecargaVirtual = new DlgIngresoRecargaVirtual(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = true;
        dlgIngresoRecargaVirtual.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesVentas.vVal_Prec_Lista_Tmp = ((String)(tblProductos.getValueAt(filaActual, 17))).trim();
            //Ahora se grabara S/1.00
            //31.10.2007 dubilluz modificacion
            //VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar));
            VariablesVentas.vVal_Prec_Vta =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(ConstantsVentas.PrecioVtaRecargaTarjeta));
            VariablesVentas.vVal_Prec_Lista =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Lista_Tmp) *
                                              FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar));
            seleccionaProducto(filaActual);

            log.debug("VariablesVentas.vNumeroARecargar : " + VariablesVentas.vNumeroARecargar);
            VariablesVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
        } else
            VariablesVentas.vIndDireccionarResumenPed = false;
    }

    /**
     * Evalua cual debera ser el ingreso de cantidad dependiendo del tipo de producto.
     * @author Luis Mesia Rivera
     * @since 08.01.2007
     */
    private void evaluaIngresoCantidad() {
        //ERIOS 20.05.2013 Tratamiento de Producto Virtual - Magistral

        if (tblProductos.getRowCount() == 0)
            return;

        int row = tblProductos.getSelectedRow();
        String indicadorProdVirtual = FarmaUtility.getValueFieldJTable(tblProductos, row, 14);
        String indicadorPromocion = FarmaUtility.getValueFieldJTable(tblProductos, row, COL_RES_IND_PACK);
        String indTratamiento = FarmaUtility.getValueFieldJTable(tblProductos, row, COL_RES_IND_TRAT);

        if (indicadorPromocion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (row > -1) {
                muestraDetallePromocion(row);
                aceptaPromocion();
            }
        } else {
            if (indicadorProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                if (indTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    muestraIngresoCantidad();
                } else {
                    //FarmaUtility.showMessage(this,"No puede modificar este registro, eliminelo y vuelva a ingresarlo.",null);
                    muestraTratamiento();
                }
            } else {
                String tipoProdVirtual = FarmaUtility.getValueFieldJTable(tblProductos, row, 15);
                if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
                    muestraIngresoTelefonoMonto();
                } else if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
                    muestraIngresoCantidad_Tarjeta_Virtual();
                    //FarmaUtility.showMessage(this, "No es posible cambiar la cantidad de este producto. Verifique!!!", tblProductos);
                    //return;
                } else if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_MAGISTRAL)) {
                    FarmaUtility.showMessage(this,
                                             "El Recetario Magistral ya fue ingresado. No puede modificar esta información.",
                                             tblProductos);
                }
            }
        }
        txtDescProdOculto.setText("");
    }

    /**NUEVO
     * @Autor:  Luis Reque
     * @Fecha:  16-03-2007
     * */
    private boolean validaCreditoCliente(String pCodCli) {
        if (!pCodCli.equalsIgnoreCase("")) //Existe un codigo de trabajador
        {
            double diferencia;
            try {
                double valTotal = FarmaUtility.getDecimalNumber(lblTotalS.getText());
                log.debug("TotalS: " + lblTotalS.getText());
                String valor =
                    DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, pCodCli, FarmaUtility.formatNumber(valTotal),
                                                FarmaConstants.INDICADOR_S);
                log.debug("Diferencia: " + valor);
                diferencia = FarmaUtility.getDecimalNumber(valor);
                if (diferencia >= 0) {
                    log.debug("OK");
                    return true;
                } else {
                    FarmaUtility.showMessage(this,
                                             "No se puede generar el pedido. \nEl cliente excede en S/. " + FarmaUtility.formatNumber((diferencia *
                                                                                                                                       -1)) +
                                             " el limite de su crédito.", null);
                    //FarmaUtility.moveFocusJTable(tblProductos);
                    FarmaUtility.moveFocus(txtDescProdOculto);
                    return false;
                }
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Error al grabar información.", null);
                //FarmaUtility.moveFocusJTable(tblProductos);
                FarmaUtility.moveFocus(txtDescProdOculto);
                return false;
            }
        } else { //El convenio no tiene BD
            log.debug("Arreglo: " + VariablesConvenio.vArrayList_DatosConvenio);
            return true;
        }
    }

    private void grabarPedidoConvenio(String pCodCli, String pNumDocIden, String pCodTrabEmp, String pApePat,
                                      String pApeMat, String pFecNac, String pCodSol, String pNumTel, String pDirecCli,
                                      String pNomDist, String pCodInterno, String pNomTrabajador, String pCodCliDep,
                                      String pCodTrabEmpDep) {
        try {
            DBConvenio.grabarPedidoConvenio(VariablesVentas.vNum_Ped_Vta, VariablesConvenio.vCodConvenio, pCodCli,
                                            pNumDocIden, pCodTrabEmp, pApePat, pApeMat, pFecNac, pCodSol,
                                            VariablesConvenio.vPorcDctoConv, VariablesConvenio.vPorcCoPago, pNumTel,
                                            pDirecCli, pNomDist, VariablesConvenio.vValCoPago, pCodInterno,
                                            pNomTrabajador, pCodCliDep, pCodTrabEmpDep);
            log.debug("Grabar Pedido Convenio");
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al grabar el la informacion del pedido y el convenio: \n" +
                    sql, null);
            //FarmaUtility.moveFocusJTable(tblProductos);
            FarmaUtility.moveFocus(txtDescProdOculto);
        }
    }

    private void grabarPedidoConvenioBTLMF(String pCodCampo, String pDesCampo, String pNombCampo,
                                           String pCodValorCampo) {
        try {

            DBConvenioBTLMF.grabarPedidoVenta(pCodCampo, pDesCampo, pNombCampo, pCodValorCampo);

            log.debug("Grabar Pedido Convenio BTL MF");
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al grabar el la informacion del pedido y el convenio BTL MF: \n" +
                    sql, null);
        }
    }

    private void grabarFormaPagoPedido(String pCodFormaPago, String pNumPedVta, String pImPago, String pTipMoneda,
                                       String pTipoCambio, String pVuelto, String pImTotalPago, String pNumTarj,
                                       String pFecVencTarj, String pNomCliTarj, String pCantCupon) {
        try {
            //DBCaja.grabaFormaPagoPedido(pCodFormaPago,pImPago,pTipMoneda,pTipoCambio,pVuelto,pImTotalPago,pNumTarj,pFecVencTarj,pNomCliTarj,pCantCupon);
            DBConvenio.grabaFormaPagoPedido(pCodFormaPago, pNumPedVta, pImPago, pTipMoneda, pTipoCambio, pVuelto,
                                            pImTotalPago, pNumTarj, pFecVencTarj, pNomCliTarj, pCantCupon);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al grabar la forma de pago del pedido." + sql.getMessage(), null);
            //FarmaUtility.moveFocusJTable(tblProductos);
            FarmaUtility.moveFocus(txtDescProdOculto);
        }
    }

    /**
     * Determina el credito disponible.
     * @param pCodCli
     * @return <b>double</b> credito disponible
     * @author Edgar Rios Navarro
     * @since 23.05.2007
     */
    private double validaCreditoCliente(String pCodCli, String pCodPago, KeyEvent e) throws SQLException {
        double diferencia = 0;
        double valTotal = FarmaUtility.getDecimalNumber(pCodPago);
        String vkF = "";
        log.debug("Monto Copago: " + pCodPago);

        String vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);


        if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            String valor =
                DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, pCodCli, FarmaUtility.formatNumber(valTotal),
                                            FarmaConstants.INDICADOR_S);
            log.debug("Diferencia: " + valor);
            diferencia = FarmaUtility.getDecimalNumber(valor);

            if (diferencia < 0) {
                if (VariablesConvenio.vIndSoloCredito.equals(FarmaConstants.INDICADOR_N)) {
                    valTotal = valTotal + diferencia;
                    VariablesConvenio.vValCoPago = FarmaUtility.formatNumber(valTotal);
                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                         "El cliente excede en S/. " + FarmaUtility.formatNumber((diferencia *
                                                                                                                  -1)) +
                                                         " el limite de su crédito. \n Está seguro de continuar?")) {
                        if (e.getKeyCode() == KeyEvent.VK_F4 && !pIngresoComprobanteManual) {

                            //VariablesVentas.venta_producto_virtual = false;
                            //grabarPedidoVenta(ConstantsVentas.TIPO_COMP_FACTURA);
                            /*if(cargaLogin_verifica())
                            {*/
                            lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " +
                                                      FarmaVariables.vPatUsu.trim() + " " +
                                                      FarmaVariables.vMatUsu.trim());
                            // Inicio Adicion Delivery 28/04/2006 Paulo
                            //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
                            //lblCliente.setText(nombreCliente);
                            // Fin Adicion Delivery 28/04/2006 Paulo
                            colocaUltimoPedidoDiarioVendedor();
                            //FarmaUtility.moveFocus(tblProductos);
                            FarmaUtility.moveFocus(txtDescProdOculto);

                            vkF = "F4";
                            agregarComplementarios(vkF);
                            //}
                        } else if (UtilityPtoVenta.verificaVK_F1(e) && !pIngresoComprobanteManual) {
                            //VariablesVentas.venta_producto_virtual = false;
                            //grabarPedidoVenta(ConstantsVentas.TIPO_COMP_BOLETA);
                            /*if(cargaLogin_verifica())

                            {*/
                            lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " +
                                                      FarmaVariables.vPatUsu.trim() + " " +
                                                      FarmaVariables.vMatUsu.trim());
                            // Inicio Adicion Delivery 28/04/2006 Paulo
                            //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
                            //lblCliente.setText(nombreCliente);
                            // Fin Adicion Delivery 28/04/2006 Paulo
                            colocaUltimoPedidoDiarioVendedor();
                            //FarmaUtility.moveFocus(tblProductos);
                            FarmaUtility.moveFocus(txtDescProdOculto);

                            vkF = "F1";
                            agregarComplementarios(vkF);
                            ///}
                        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
                            //COBRA PEDIDO MANUAL
                            //dubilluz 13.10.2014
                            cobraPedidoManual();
                        }
                    }
                } else if (VariablesConvenio.vIndSoloCredito.equals(FarmaConstants.INDICADOR_S)) {
                    FarmaUtility.showMessage(this,
                                             "El cliente excede en S/. " + FarmaUtility.formatNumber((diferencia *
                                                                                                      -1)) +
                                             " el limite de su crédito. \n ¡No puede realizar la venta!",
                                             tblProductos);
                } else {
                    FarmaUtility.showMessage(this, "No se pudo determinar el indicador del convenio.", tblProductos);
                }
            } else {
                //valTotal = valTotal + diferencia;
                VariablesConvenio.vValCoPago = FarmaUtility.formatNumber(valTotal);
                if (e.getKeyCode() == KeyEvent.VK_F4 && !pIngresoComprobanteManual) {
                    //grabarPedidoVenta(ConstantsVentas.TIPO_COMP_FACTURA);

                    /*if(cargaLogin_verifica())
                    {*/
                    lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " + FarmaVariables.vPatUsu.trim() +
                                              " " + FarmaVariables.vMatUsu.trim());
                    // Inicio Adicion Delivery 28/04/2006 Paulo
                    //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
                    //lblCliente.setText(nombreCliente);
                    // Fin Adicion Delivery 28/04/2006 Paulo
                    colocaUltimoPedidoDiarioVendedor();
                    //FarmaUtility.moveFocus(tblProductos);
                    FarmaUtility.moveFocus(txtDescProdOculto);

                    vkF = "F4";
                    agregarComplementarios(vkF);
                    /*}*/
                } else if (UtilityPtoVenta.verificaVK_F1(e) || (e.getKeyChar() == '+' && !pIngresoComprobanteManual)) {
                    // grabarPedidoVenta(ConstantsVentas.TIPO_COMP_BOLETA);
                    /*if(cargaLogin_verifica())
                    {*/
                    lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " + FarmaVariables.vPatUsu.trim() +
                                              " " + FarmaVariables.vMatUsu.trim());
                    // Inicio Adicion Delivery 28/04/2006 Paulo
                    //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
                    //lblCliente.setText(nombreCliente);
                    // Fin Adicion Delivery 28/04/2006 Paulo
                    colocaUltimoPedidoDiarioVendedor();
                    //FarmaUtility.moveFocus(tblProductos);
                    FarmaUtility.moveFocus(txtDescProdOculto);

                    vkF = "F1";
                    agregarComplementarios(vkF);
                    /*}*/
                }
            }
            FarmaUtility.moveFocus(txtDescProdOculto);
        } else
            FarmaUtility.showMessage(this,
                                     "No hay linea con matriz.\n Inténtelo nuevamente si el problema persiste comuníquese con el Operador de Sistemas.",
                                     txtDescProdOculto);

        return valTotal;
    }


    /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  15.08.08
     */
    private void agregarClienteCampana(String pCodCampana, String pCodCupon, String pCodCli, String pDniCliente,
                                       String pNomcliente, String pApePatCliente, String pApeMatCliente,
                                       String pTelefono, String pNumCMP, String pMedico, String pSexo,
                                       String pFecNacimiento) {
        try {
            DBCampana.agregarClienteCampana(pCodCampana, pCodCupon, pCodCli, pDniCliente, pNomcliente, pApePatCliente,
                                            pApeMatCliente, pTelefono, pNumCMP, pMedico, pSexo, pFecNacimiento);
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    /* ************************************************************************** */
    /*                        Metodos Auxiliares                                  */
    /* ************************************************************************** */

    /**
     * Metodo que elimina items del array ,que sean iguales al paramtro
     * q se le envia , comaprando por campo
     * @author :dubilluz
     * @since  :20.06.2007
     */
    private void removeItemArray(ArrayList array, String codProm, int pos) {
        String cod = "";
        codProm = codProm.trim();
        for (int i = 0; i < array.size(); i++) {
            cod = ((String)((ArrayList)array.get(i)).get(pos)).trim();
            log.debug(cod + "<<<<<" + codProm);
            if (cod.equalsIgnoreCase(codProm)) {
                array.remove(i);
                i = -1;
            }
        }
    }

    /**
     * Retorna el detalle de una promocion
     * @author : dubilluz
     * @since  : 03.07.2007
     */
    private ArrayList detalle_Prom(ArrayList array, String codProm) {
        ArrayList nuevo = new ArrayList();
        ArrayList aux = new ArrayList();
        String cod_p = "";
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
            aux = (ArrayList)(VariablesVentas.vArrayList_Prod_Promociones.get(i));
            cod_p = (String)(aux.get(18));
            if (cod_p.equalsIgnoreCase(codProm)) {
                nuevo.add((ArrayList)(aux.clone()));
            }
        }
        return nuevo;
    }

    /**
     * Agrupa productos que esten en ambos paquetes
     * retorna el nuevoa arreglo
     * @author : dubilluz
     * @since : 27.06.2007
     * @modificacion: ASOSA, 18.12.2009
     */
    private ArrayList agrupar(ArrayList array) {
        int cantCampos = ((ArrayList)array.get(0)).size();
        ArrayList nuevo = new ArrayList();
        ArrayList aux1 = new ArrayList();
        ArrayList aux2 = new ArrayList();
        int cantidad1 = 0;
        int cantidad2 = 0;
        int suma = 0;
        for (int i = 0; i < array.size(); i++) {
            aux1 = (ArrayList)(array.get(i));
            log.debug("AUXXXXXXXXXXXXXXXXXXXXXXXX 1: " + aux1);
            log.debug("SIZE: SIZE: " + aux1.size());
            if (aux1.size() <= cantCampos) { //cantidad de campos ASOSA, 18.12.2009
                for (int j = i + 1; j < array.size(); j++) {
                    aux2 = (ArrayList)(array.get(j));
                    if (aux2.size() <= cantCampos) { //cantidad de campos ASOSA, 18.12.2009
                        if ((((String)(aux1.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                            cantidad1 = Integer.parseInt(((String)(aux1.get(4))).trim());
                            cantidad2 = Integer.parseInt(((String)(aux2.get(4))).trim());
                            suma = cantidad1 + cantidad2;
                            aux1.set(4, suma + "");
                            ((ArrayList)(array.get(j))).add("Revisado");
                        }
                    }
                }
                nuevo.add(aux1);
            }
        }
        log.debug("Agrupado :" + nuevo.size());
        log.debug("Aggrup Elment :" + nuevo);
        return nuevo;
    }


    /**
     * Acepta Modificacion de promocion
     * @author : dubilluz
     * @since  : 04.07.2007
     */
    private void aceptaPromocion() {

        for (int i = 0; i < VariablesVentas.vArrayList_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Promociones_temporal = new ArrayList();

        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Prod_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
        //log.debug(VariablesVentas.vArrayList_ResumenPedido.size());
    }

    /**
     * Muestra el dialogo de Ingreso de Cantidad para Producto Tarjeta Virtual
     * @author : dubilluz
     * @since  : 29.08.2007
     */
    private void muestraIngresoCantidad_Tarjeta_Virtual() {
        if (tblProductos.getRowCount() == 0)
            return;
        log.debug("DIEGO UBILLUZ >>  " + VariablesVentas.vArrayList_Prod_Tarjeta_Virtual);
        VariablesVentas.cantidad_tarjeta_virtual =
                Integer.parseInt(FarmaUtility.getValueFieldJTable(tblProductos, tblProductos.getSelectedRow(), 4));
        //------

        //-------
        DlgIngresoCantidadTarjetaVirtual dlgIngresoCantidad =
            new DlgIngresoCantidadTarjetaVirtual(myParentFrame, "", true);
        dlgIngresoCantidad.setVisible(true);

        if (FarmaVariables.vAceptar) {
            int fila = tblProductos.getSelectedRow();
            int cantidad_new = VariablesVentas.cantidad_tarjeta_virtual;
            int cantidad_old = Integer.parseInt(FarmaUtility.getValueFieldJTable(tblProductos, fila, 4));
            log.debug("Cantidad Antigua :  " + cantidad_old);
            log.debug("Cantidad Nueva   :  " + cantidad_new);
            if (cantidad_new != 0)
                seleccionaProductoTarjetaVirtual(cantidad_new + "");
            VariablesVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
        } else
            VariablesVentas.vIndDireccionarResumenPed = false;
    }

    /**
     * Seleccionando el Producto de tajeta Virtual
     * @author : dubilluz
     * @since  : 29.08.2007
     */
    private void seleccionaProductoTarjetaVirtual(String cantidad) { //VariablesVentas.vTotalPrecVtaProd  75.0
        if (tblProductos.getRowCount() == 0)
            return;
        log.debug("VariablesVentas.vTotalPrecVtaProd : " + VariablesVentas.vTotalPrecVtaProd);

        int row = tblProductos.getSelectedRow();
        VariablesVentas.vVal_Prec_Vta = FarmaUtility.getValueFieldJTable(tblProductos, row, 6);
        /**
     * Modificado para la Cantidad que se compra
     */
        VariablesVentas.vCant_Ingresada = cantidad.trim();
        VariablesVentas.vTotalPrecVtaProd =
                (FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada) * FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta));
        operaProductoEnJTable(row);
        operaProductoEnArrayList(row);
        calculaTotalesPedido();

    }

    /**
     * Proceso para revisar eventos externos para los pedidos
     * como productos de regalo ,cumpones y/o complementarios
     * @author dubilluz
     * @since  08.04.2007
     */
    private boolean procesoProductoRegalo(String pNumped, int pSecDet) throws Exception {
        //--Se verifica si puede o no acceder al regalo
        ArrayList arrayLista = new ArrayList();
        ArrayList vAEncartesAplicables = new ArrayList();
        DBVentas.obtieneEncartesAplicables(vAEncartesAplicables);
        log.debug("...Encartes aplicables : " + vAEncartesAplicables);
        String cod_encarte = "";
        ArrayList vMensajesRegalo = new ArrayList();
        for (int e = 0; e < vAEncartesAplicables.size(); e++) {
            cod_encarte = FarmaUtility.getValueFieldArrayList(vAEncartesAplicables, e, 0);
            log.debug("...Procesando Encarte : " + cod_encarte);

            DBVentas.analizaProdEncarte(arrayLista, cod_encarte);
            log.debug("RESULTADO " + arrayLista);
            if (arrayLista.size() > 1) {
                String[] listEncarte =
                    arrayLista.get(0).toString().substring(1, arrayLista.get(0).toString().length() - 1).split("&");
                log.debug("**************** " + listEncarte);
                log.debug("********CON_COLUM_COD_PROD_REGALO* " + CON_COLUM_COD_PROD_REGALO);
                VariablesVentas.vCodProd_Regalo = listEncarte[CON_COLUM_COD_PROD_REGALO];
                VariablesVentas.vCantidad_Regalo =
                        (int)FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_CANT_MAX_PROD_REGALO]);
                VariablesVentas.vMontoMinConsumoEncarte =
                        FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_MONT_MIN_ENCARTE]);
                VariablesVentas.vVal_Frac =
                        "1"; //ERIOS 04.06.2008 Por definición de Regalo, es en unidad de presentación.

                log.debug("VariablesVentas.vCodProd_Regalo  " + VariablesVentas.vCodProd_Regalo);
                log.debug("VariablesVentas.vCantidad_Regalo  " + VariablesVentas.vCantidad_Regalo);
                log.debug("VariablesVentas.vMontoMinConsumoEncarte  " + VariablesVentas.vMontoMinConsumoEncarte);

                arrayLista.remove(0);
                ArrayList arrayProdEncarte = (ArrayList)arrayLista.clone();
                if (arrayProdEncarte.size() > 0) {
                    log.debug("arrayProdEncarte " + arrayProdEncarte);
                    log.debug("listEncarte " + listEncarte);
                    double monto_actual_prod_encarte = calculoMontoProdEncarte(arrayProdEncarte, 2);

                    //Actualizo regalo si es codigo  cero '0'
                    /*if (VariablesVentas.vCodProd_Regalo.equalsIgnoreCase("0")) {
                        VariablesVentas.vCodProd_Regalo = DBVentas.getEligeRegaloMonto(cod_encarte.trim(),VariablesVentas.vMontoMinConsumoEncarte);
                    }*/

                    //Vuelve a verificar el prod. regalo si este da regalo de acuerdo al monto.
                    ArrayList arrayRegMontos = new ArrayList();
                    DBVentas.getDatosRegaloxMonto(arrayRegMontos, cod_encarte.trim(), monto_actual_prod_encarte);

                    if (arrayRegMontos.size() > 1) {
                        arrayRegMontos.remove(0);
                        ArrayList arrayDatosRegalo = (ArrayList)arrayRegMontos.clone();

                        VariablesVentas.vCodProd_Regalo = FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0, 0);

                        VariablesVentas.vMontoMinConsumoEncarte =
                                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0,
                                                                                                  1).trim());
                        VariablesVentas.vMontoProxMinConsumoEncarte =
                                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0,
                                                                                                  2).trim());
                        VariablesVentas.vCodProdProxRegalo =
                                FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0, 3).trim();
                        VariablesVentas.vDescProxProd_Regalo =
                                FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0, 4).trim();

                    } else {
                        VariablesVentas.vMontoProxMinConsumoEncarte = 0;
                        VariablesVentas.vDescProxProd_Regalo = "";
                        VariablesVentas.vCodProdProxRegalo = "";
                    }

                    String desc_prod = DBVentas.obtieneDescProducto(VariablesVentas.vCodProd_Regalo);


                    double stock_regalo = 0, stock_prox_regalo = 0;

                    if (VariablesVentas.vCodProd_Regalo.trim().length() > 0)
                        stock_regalo =
                                FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProd_Regalo).trim());

                    if (VariablesVentas.vCodProdProxRegalo.trim().length() > 0)
                        stock_prox_regalo =
                                FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProdProxRegalo).trim());

                    if (stock_regalo > 0) {
                        if (monto_actual_prod_encarte >= VariablesVentas.vMontoMinConsumoEncarte) {
                            log.debug("...Procesa a añadir producto de regalo");
                            añadirProductoRegalo(VariablesVentas.vCodProd_Regalo, VariablesVentas.vCantidad_Regalo,
                                                 pNumped, pSecDet, 0, desc_prod);
                            pSecDet++;
                        }
                    }
                    if (stock_regalo == 0) {
                        if (monto_actual_prod_encarte >= VariablesVentas.vMontoMinConsumoEncarte) {
                            ArrayList array = new ArrayList();

                            DBVentas.getEncarteAplica(array, monto_actual_prod_encarte, cod_encarte.trim());

                            if (array.size() > 0) {
                                double stk_prod =
                                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array, 0,
                                                                                                      4).trim());
                                if (stk_prod > 0) {
                                    añadirProductoRegalo(FarmaUtility.getValueFieldArrayList(array, 0, 0).trim(),
                                                         (int)(FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array,
                                                                                                                                 0,
                                                                                                                                 2).trim())),
                                                         pNumped, pSecDet, 0,
                                                         FarmaUtility.getValueFieldArrayList(array, 0, 1).trim());
                                    pSecDet++;

                                }
                            }
                        }
                    } else {

                        if (stock_regalo == 0 && stock_prox_regalo > 0) {
                            if (monto_actual_prod_encarte < VariablesVentas.vMontoProxMinConsumoEncarte)

                                añadirProductoRegalo(VariablesVentas.vCodProdProxRegalo,
                                                     VariablesVentas.vCantidad_Regalo, pNumped, pSecDet, 0,
                                                     VariablesVentas.vDescProxProd_Regalo);
                            pSecDet++;
                        }
                    }

                    if (stock_regalo > 0) {
                        if (monto_actual_prod_encarte < VariablesVentas.vMontoMinConsumoEncarte) {

                            String mensaje =
                                "Para llevarse de regalo " + VariablesVentas.vCantidad_Regalo + " " + desc_prod + " " +
                                " le faltan S/." +
                                FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte - monto_actual_prod_encarte) +
                                ".\n" +
                                "¿Desea añadir más productos para llevar el regalo?";
                            log.debug(mensaje);
                        } else {

                            if (stock_prox_regalo > 0) {
                                if (monto_actual_prod_encarte < VariablesVentas.vMontoProxMinConsumoEncarte) {

                                    String mensaje =
                                        "Para llevarse de regalo " + VariablesVentas.vCantidad_Regalo + " " +
                                        VariablesVentas.vDescProxProd_Regalo + " " + " le faltan S/." +
                                        FarmaUtility.formatNumber(VariablesVentas.vMontoProxMinConsumoEncarte -
                                                                  monto_actual_prod_encarte) + ".\n" +
                                        "¿Desea añadir más productos para llevar el regalo?";
                                    log.debug(mensaje);


                                }
                            }
                        }
                    }


                }

            }

            arrayLista = new ArrayList();
        }
        return true;

    }

    private boolean buscaElementArray(ArrayList pArray, String pCodbusq, int pTipo) {

        if (pTipo == 1) // --busqueda para Capana Cupon
            for (int i = 0; i < pArray.size(); i++) {
                if (pArray.get(i).toString().trim().equalsIgnoreCase(pCodbusq.trim()))
                    return true;
            }
        else if (pTipo == 2) // --busqueda para Multimarca y Encarte
            for (int i = 0; i < pArray.size(); i++) {
                if (pArray.get(i).toString().trim().substring(1,
                                                              pArray.get(i).toString().length() - 1).equalsIgnoreCase(pCodbusq.trim()))
                    return true;
            }
        return false;
    }

    private double calculoMontoProdEncarte(ArrayList pArray, int pTipo) {
        double totalNeto = 0.00;
        double totalParcial = 0.00;
        int cantidad = 0;
        String cod_prod = "";
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
            //log.debug(VariablesVentas.vArrayList_ResumenPedido.get(i));
            cod_prod = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 0);
            totalParcial =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 7));

            //log.debug(cod_prod + " " +totalParcial);

            if (buscaElementArray(pArray, cod_prod, pTipo))
                totalNeto = totalNeto + totalParcial;
        }
        //log.debug("*******");
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
            //log.debug(VariablesVentas.vArrayList_Prod_Promociones.get(i));
            cod_prod = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Prod_Promociones, i, 0);
            totalParcial =
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).get(7));

            //log.debug(cod_prod + " " +totalParcial);
            if (buscaElementArray(pArray, cod_prod, pTipo))
                totalNeto = totalNeto + totalParcial;
        }
        //log.debug("*******");
        //log.debug("Monto total de productos de encarte " + totalNeto);

        return totalNeto;
    }

    /**
     *
     */
    private void añadirProductoRegalo(String pCodProd, int pCantidad, String pNumped, int pSecDet, int pValPrec,
                                      String pDescProd) {
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if ( /*UtilityVentas.actualizaStkComprometidoProd(pCodProd,pCantidad,
                                    ConstantsVentas.INDICADOR_A,
                                    ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                    pCantidad,
                                                  true,
                                                  this,
                                                  tblProductos))*/
            UtilityVentas.operaStkCompProdResp(pCodProd, //ASOSA, 08.07.2010
                pCantidad, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, pCantidad,
                true, this, tblProductos, "")) {
            ArrayList arrayDatosProd = new ArrayList();
            try {
                //DBVentas.obtieneInfoProducto(arrayDatosProd,pCodProd.trim());
                //DBVentas.grabaProductoRegalo(pNumped,pCodProd,pSecDet,pCantidad,pValPrec); //Antes
                DBVentas.grabaProductoRegalo_02(pNumped, pCodProd, pSecDet, pCantidad, pValPrec,
                                                VariablesVentas.secRespStk); //ASOSA, 09.07.2010
                //FarmaUtility.showMessage(this, "wawawa", null);
                /*DBPtoVenta.ejecutaRespaldoStock(pCodProd, //Antes, ASOSA, 09.07.2010
                                        pNumped,
                                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR_PEDIDO,
                                        0,
                                        0,
                                        ConstantsPtoVenta.MODULO_VENTAS);*/
                /*
                 *                 DBVentas.actualizarRespaldoNumPedido(pCodProd,
                                                     ConstantsPtoVenta.MODULO_VENTAS,
                                                     pNumped,
                                                     VariablesVentas.secRespStk); //ASOSA, 09.07.2010
                 * */
                //FarmaUtility.showMessage(this, "wowowo", null);
                String mensaje = "Usted se llevara de regalo " + pCantidad + " ";
                mensaje += pDescProd + ".";
                log.debug(mensaje);
                //FarmaUtility.showMessage(this,mensaje,null);
            } catch (SQLException e) {
                log.error("", e);
            }
            log.debug(" arrayDatosProd " + arrayDatosProd);
        }
    }

    /**
     * Procesa las campañas sean multimarca y/o cupones
     * @param pNumPed
     * @author dubilluz
     * @since  10.07.2008
     */
    private void procesoCampañas(String pNumPed) throws Exception {

        // -- Primero se procesan las multimarcas
        procesoMultimarca(pNumPed.trim());
        // -- Luego se validad y procesan las campañas cupon
        procesoCampanaCupon(pNumPed.trim());
    }


    //Modificado por DVELIZ  04.10.08

    private void procesoCampanaCupon(String pNumPed) throws Exception {
        DBVentas.procesaCampanaCupon(pNumPed, ConstantsVentas.TIPO_CAMPANA_CUPON, VariablesFidelizacion.vDniCliente);
    }

    /**
     * Procensa las multimarcas aplicables al pedido actual
     * @param pNumPed
     * @throws Exception
     * @author dubilluz
     * @since  10.07.2008
     */
    private void procesoMultimarca(String pNumPed) throws Exception {
        //--Se verifica si puede o no acceder a las campañas
        ArrayList vACampCuponplicables = new ArrayList();
        DBVentas.obtieneCampCuponAplicables(vACampCuponplicables, ConstantsVentas.TIPO_MULTIMARCA, "N");
        log.debug("...Camp Cupones aplicables : " + vACampCuponplicables);
        String cod_camp_cupon = "";
        ArrayList vMensajesCampCupon = new ArrayList();
        for (int e = 0; e < vACampCuponplicables.size(); e++) {
            cod_camp_cupon = FarmaUtility.getValueFieldArrayList(vACampCuponplicables, e, 0);

            ArrayList arrayLista = new ArrayList();
            DBVentas.analizaProdCampCupon(arrayLista, pNumPed, cod_camp_cupon);
            log.debug("RESULTADO " + arrayLista);
            if (arrayLista.size() > 1) {
                String[] listDatosCupon =
                    arrayLista.get(0).toString().substring(1, arrayLista.get(0).toString().length() - 1).split("&");

                VariablesVentas.vCodCampCupon = listDatosCupon[CON_COLUM_COD_CUPON];
                VariablesVentas.vDescCupon = listDatosCupon[CON_COLUM_DESC_CUPON];
                VariablesVentas.vMontoPorCupon = FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_MONT_CUPON]);

                VariablesVentas.vCantidadCupones = FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_CANT_CUPON]);

                log.debug("VariablesVentas.vCodCampCupon  " + VariablesVentas.vCodCampCupon);
                log.debug("VariablesVentas.vDescCupon  " + VariablesVentas.vDescCupon);
                log.debug("VariablesVentas.vMontoPorCupon  " + VariablesVentas.vMontoPorCupon);

                arrayLista.remove(0);
                ArrayList arrayProdCupon = (ArrayList)arrayLista.clone();
                if (arrayProdCupon.size() > 0) {

                    log.debug("arrayProdCupon " + arrayProdCupon);
                    log.debug("listDatosCupon " + listDatosCupon);
                    double monto_actual_prod_cupon = calculoMontoProdEncarte(arrayProdCupon, 2);

                    if (monto_actual_prod_cupon >= VariablesVentas.vMontoPorCupon) {
                        log.debug("...calculando numero de cupones para llevarse");
                        log.debug("...monto_actual_prod_cupon " + monto_actual_prod_cupon);
                        log.debug("...VariablesVentas.vMontoPorCupon " + VariablesVentas.vMontoPorCupon);
                        int cantCupones =
                            (int)((monto_actual_prod_cupon / VariablesVentas.vMontoPorCupon) * VariablesVentas.vCantidadCupones);
                        log.debug("Numero Cupones " + cantCupones);
                        String mensaje = "Usted a ganado " + cantCupones;
                        if (cantCupones == 1)
                            mensaje += " cupon (";
                        else
                            mensaje += " cupones (";

                        mensaje += VariablesVentas.vDescCupon + ")";

                        //FarmaUtility.showMessage(this,mensaje,null);
                        //Se grabara la cantidad de cupones entregados al cupon.
                        DBVentas.grabaCuponPedido(pNumPed, VariablesVentas.vCodCampCupon, cantCupones,
                                                  ConstantsVentas.TIPO_MULTIMARCA, "");
                    }
                }
            }

        }
    }

    private void aceptaOperacion() {
        VariablesVentas.vArrayList_ResumenPedido = new ArrayList();
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++)
            VariablesVentas.vArrayList_ResumenPedido.add((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i));
        //cargar();
        for (int i = 0; i < VariablesVentas.vArrayList_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Promociones_temporal = new ArrayList();

        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Prod_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
    }

    /**
     * se lista productos complementarios
     * @author JCORTEZ
     * @since  10.05.2008
     */
    private void agregarComplementarios(String vkF) {
        log.debug("Seleccion de origen...");
        boolean mostrar = true;
        VariablesVentas.vEsProdOferta = false;
        String ind_ori = "";

        for (int i = 0; i <= tblProductos.getRowCount(); i++) {
            ind_ori = FarmaUtility.getValueFieldJTable(tblProductos, i, 19);

            if (ind_ori.equalsIgnoreCase(ConstantsVentas.IND_ORIGEN_COMP) ||
                ind_ori.equalsIgnoreCase(ConstantsVentas.IND_ORIGEN_OFER)) {
                mostrar = false;
                break;
            }
        }

        //se fuerza el listado de oferta por el filtro
        if (VariablesVentas.vCodFiltro.equalsIgnoreCase(ConstantsVentas.IND_OFER)) {
            VariablesVentas.vEsProdOferta = true;
            mostrar = true;
        }

        int vFila = tblProductos.getSelectedRow();
        VariablesVentas.vCodProdOrigen_Comple = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 1);

        // dubilluz 14.10.2014
        if (pIngresoComprobanteManual) {
            VariablesConvenioBTLMF.vIndVtaComplentaria = "N";
            mostrar = false;
        }
        // dubilluz 14.10.2014

        //Agregado por FRAMIREZ 11.02.2012 Flag Para Mostrar productos complementarios para un convenio
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
            if (VariablesConvenioBTLMF.vIndVtaComplentaria.equals("S")) {
                DlgComplementarios1 dlgcomplementarios = new DlgComplementarios1(myParentFrame, "", true);
                dlgcomplementarios.setVisible(true);
            } else {
                FarmaVariables.vAceptar = false;
            }
        } else {
            if (mostrar) {
                DlgComplementarios1 dlgcomplementarios = new DlgComplementarios1(myParentFrame, "", true);
                dlgcomplementarios.setVisible(true);
            } else {
                FarmaVariables.vAceptar = false;
            }
        }

        if (FarmaVariables.vAceptar) {
            VariablesVentas.vCodFiltro = ""; //JCORTEZ 25/04/08
            VariablesVentas.vEsProdOferta = false; //JCORTEZ 25/04/08
            aceptaOperacion(); //agrega producto al pedido

            //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
            neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009
        } else {
            //Agregado Por FRAMIREZ
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.length() > 0) {
                if (vkF.equalsIgnoreCase("F1")) {
                    String tipoDocBeneficiario = "N";
                    try {
                        tipoDocBeneficiario =
                                DBConvenioBTLMF.getComprobanteBeneficiario(VariablesConvenioBTLMF.vCodConvenio);
                    } catch (Exception ex) {
                        tipoDocBeneficiario = "N";
                    }
                    tipoDocBeneficiario = tipoDocBeneficiario.trim();
                    if ("BOL".equalsIgnoreCase(tipoDocBeneficiario) || "M".equalsIgnoreCase(tipoDocBeneficiario)) {
                        if (VariablesConvenioBTLMF.vDatosConvenio != null) {
                            for (int j = 0; j < VariablesConvenioBTLMF.vDatosConvenio.size(); j++) {
                                Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(j);
                                String pCodCampo =
                                    (String)convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO); //COD_TIPO_CAMPO
                                String pDesCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN); // NOMBRE
                                String pCodValorCampo =
                                    (String)convenio.get(ConstantsConvenioBTLMF.COL_COD_VALOR_IN); // DNI
                                if ("BOL".equalsIgnoreCase(tipoDocBeneficiario)) {
                                    if (ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO.equalsIgnoreCase(pCodCampo)) {
                                        if (pDesCampo != null) {
                                            VariablesVentas.vNom_Cli_Ped = pDesCampo.trim();
                                        }
                                        if (pCodValorCampo != null) {
                                            VariablesVentas.vRuc_Cli_Ped = pCodValorCampo.trim();
                                        }
                                    }
                                } else {
                                    if (ConstantsConvenioBTLMF.COD_DATO_NOMBRE_BENEFICIARIO.equalsIgnoreCase(pCodCampo)) {
                                        if (pDesCampo != null) {
                                            VariablesVentas.vNom_Cli_Ped = pDesCampo.trim();
                                        }
                                        if (pCodValorCampo != null) {
                                            if (!"0".equalsIgnoreCase(pCodValorCampo.trim()))
                                                VariablesVentas.vRuc_Cli_Ped = pCodValorCampo.trim();
                                        }
                                    }

                                }
                            }
                        }
                        grabarPedidoVenta(ConstantsVentas.TIPO_COMP_BOLETA);
                    } else {
                        if ("FAC".equalsIgnoreCase(tipoDocBeneficiario)) {
                            VariablesVentas.vRuc_Cli_Ped = VariablesConvenioBTLMF.vRuc;
                            VariablesVentas.vNom_Cli_Ped = VariablesConvenioBTLMF.vInstitucion;
                            VariablesVentas.vDir_Cli_Ped = VariablesConvenioBTLMF.vDireccion;
                            grabarPedidoVenta(ConstantsVentas.TIPO_COMP_FACTURA);
                        } else {
                            grabarPedidoVenta(ConstantsVentas.TIPO_COMP_GUIA);
                        }
                    }
                }
            } else {
                if (vkF.equalsIgnoreCase("F1")) {
                    grabarPedidoVenta(ConstantsVentas.TIPO_COMP_BOLETA);
                    //VariablesVentas.venta_producto_virtual = false;
                } else if (vkF.equalsIgnoreCase("F4")) {
                    grabarPedidoVenta(ConstantsVentas.TIPO_COMP_FACTURA);
                    //VariablesVentas.venta_producto_virtual = false;
                }
            }
        }
    }

    /**
     *
     */
    private void evaluaProductoRegalo() {
        //lblPedidoRegalo1.setText("");
        //lblPedidoRegalo2.setText("");
        //lblPedidoRegalo3.setText("");
        txtMensajesPedido.setText("");

        try {
            //--Se verifica si puede o no acceder al regalo
            log.debug("...Evaluando pedido si llevará Regalo");
            ArrayList vAEncartesAplicables = new ArrayList();
            DBVentas.obtieneEncartesAplicables(vAEncartesAplicables);
            log.debug("...Encartes aplicables : " + vAEncartesAplicables);
            String cod_encarte = "";
            ArrayList vMensajesRegalo = new ArrayList();
            for (int e = 0; e < vAEncartesAplicables.size(); e++) {
                cod_encarte = FarmaUtility.getValueFieldArrayList(vAEncartesAplicables, e, 0);
                log.debug("...Procesando Encarte : " + cod_encarte);

                ArrayList arrayLista = new ArrayList();
                DBVentas.analizaProdEncarte(arrayLista, cod_encarte.trim());
                if (arrayLista.size() > 1) {
                    String[] listEncarte =
                        arrayLista.get(0).toString().substring(1, arrayLista.get(0).toString().length() -
                                                               1).split("&");

                    VariablesVentas.vCodProd_Regalo = listEncarte[CON_COLUM_COD_PROD_REGALO];
                    VariablesVentas.vCantidad_Regalo =
                            (int)FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_CANT_MAX_PROD_REGALO]);
                    VariablesVentas.vMontoMinConsumoEncarte =
                            FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_MONT_MIN_ENCARTE]);

                    log.debug("VariablesVentas.vCodProd_Regalo  " + VariablesVentas.vCodProd_Regalo);
                    log.debug("VariablesVentas.vCantidad_Regalo  " + VariablesVentas.vCantidad_Regalo);
                    log.debug("VariablesVentas.vMontoMinConsumoEncarte  " + VariablesVentas.vMontoMinConsumoEncarte);

                    arrayLista.remove(0);
                    ArrayList arrayProdEncarte = (ArrayList)arrayLista.clone();
                    if (arrayProdEncarte.size() > 0) {
                        double monto_actual_prod_encarte = calculoMontoProdEncarte(arrayProdEncarte, 2);

                        //Vuelve a verificar el prod. regalo si este da regalo de acuerdo al monto.
                        ArrayList arrayRegMontos = new ArrayList();
                        DBVentas.getDatosRegaloxMonto(arrayRegMontos, cod_encarte.trim(), monto_actual_prod_encarte);

                        //busco que este encarte tenga regalos por montos.
                        if (arrayRegMontos.size() > 1) {
                            arrayRegMontos.remove(0);
                            ArrayList arrayDatosRegalo = (ArrayList)arrayRegMontos.clone();

                            VariablesVentas.vCodProd_Regalo =
                                    FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0, 0);

                            VariablesVentas.vMontoMinConsumoEncarte =
                                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,
                                                                                                      0, 1).trim());
                            VariablesVentas.vMontoProxMinConsumoEncarte =
                                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,
                                                                                                      0, 2).trim());
                            VariablesVentas.vCodProdProxRegalo =
                                    FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0, 3).trim();
                            VariablesVentas.vDescProxProd_Regalo =
                                    FarmaUtility.getValueFieldArrayList(arrayDatosRegalo, 0, 4).trim();

                        } else {
                            VariablesVentas.vMontoProxMinConsumoEncarte = 0;
                            VariablesVentas.vDescProxProd_Regalo = "";
                            VariablesVentas.vCodProdProxRegalo = "";
                        }
                        /*

                        */
                        //jquispe 05.12.2011 cambiar si la cantidad del prod. regalo es variable segun el monto
                        /*VariablesVentas.vCantidad_Regalo =
                                            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayRegMontos,0,2).trim());
                        */
                        String desc_prod = DBVentas.obtieneDescProducto(VariablesVentas.vCodProd_Regalo);
                        String mensaje = "";

                        double stock_regalo = 0, stock_prox_regalo = 0;

                        if (VariablesVentas.vCodProd_Regalo.trim().length() > 0)
                            stock_regalo =
                                    FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProd_Regalo).trim());

                        if (VariablesVentas.vCodProdProxRegalo.trim().length() > 0)
                            stock_prox_regalo =
                                    FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProdProxRegalo).trim());

                        /*if(VariablesVentas.vMontoProxMinConsumoEncarte>0)
                        {*/
                        if (stock_regalo > 0) {

                            if (monto_actual_prod_encarte >= VariablesVentas.vMontoMinConsumoEncarte) {
                                mensaje =
                                        "Usted se llevará de regalo " + VariablesVentas.vCantidad_Regalo + " " + desc_prod;
                                log.debug(mensaje);
                            }
                        }

                        if (stock_regalo == 0) {
                            if (monto_actual_prod_encarte >= VariablesVentas.vMontoMinConsumoEncarte) {
                                ArrayList array = new ArrayList();

                                DBVentas.getEncarteAplica(array, monto_actual_prod_encarte, cod_encarte.trim());

                                if (array.size() > 0) {
                                    double stk_prod =
                                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array, 0,
                                                                                                          4).trim());
                                    if (stk_prod > 0) {
                                        mensaje =
                                                "Usted se llevará de regalo " + (int)(FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array,
                                                                                                                                                        0,
                                                                                                                                                        2).trim())) +
                                                " " + FarmaUtility.getValueFieldArrayList(array, 0, 1).trim();
                                        log.debug(mensaje);
                                    }
                                }
                            } else {
                                /*if(monto_actual_prod_encarte<VariablesVentas.vMontoMinConsumoEncarte){
                                        mensaje = mensaje+" / "+
                                                "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte -
                                                                                         monto_actual_prod_encarte) +
                                                "   Para llevarse " +
                                                VariablesVentas.vCantidad_Regalo + " " +
                                         desc_prod;

                                        log.debug(mensaje);
                                    }else{

                                    */
                                if (stock_prox_regalo > 0) {
                                    if (monto_actual_prod_encarte < VariablesVentas.vMontoProxMinConsumoEncarte) {
                                        mensaje =
                                                mensaje + " / " + "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoProxMinConsumoEncarte -
                                                                                                           monto_actual_prod_encarte) +
                                                "   Para llevarse " + VariablesVentas.vCantidad_Regalo + " " +
                                                VariablesVentas.vDescProxProd_Regalo;
                                        log.debug(mensaje);
                                    }
                                }
                                /*}*/
                            }
                        }


                        if (stock_regalo > 0) {
                            if (monto_actual_prod_encarte < VariablesVentas.vMontoMinConsumoEncarte) {
                                mensaje =
                                        mensaje + " / " + "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte -
                                                                                                   monto_actual_prod_encarte) +
                                        "   Para llevarse " + VariablesVentas.vCantidad_Regalo + " " + desc_prod;

                                log.debug(mensaje);
                            } else {


                                if (stock_prox_regalo > 0) {
                                    if (monto_actual_prod_encarte < VariablesVentas.vMontoProxMinConsumoEncarte) {
                                        mensaje =
                                                mensaje + " / " + "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoProxMinConsumoEncarte -
                                                                                                           monto_actual_prod_encarte) +
                                                "   Para llevarse " + VariablesVentas.vCantidad_Regalo + " " +
                                                VariablesVentas.vDescProxProd_Regalo;
                                        log.debug(mensaje);
                                    }
                                }
                            }
                        }
                        /*
                        }else{

                        if (monto_actual_prod_encarte >=
                            VariablesVentas.vMontoMinConsumoEncarte) {
                            mensaje =
                                    "Usted se llevará de regalo " + VariablesVentas.vCantidad_Regalo +
                                    " " + desc_prod;
                            log.debug(mensaje);
                            //lblPedidoRegalo2.setText(mensaje.trim());
                        } else {
                            mensaje =
                                    "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte -
                                                                             monto_actual_prod_encarte) +
                                    "   Para llevarse " +
                                    VariablesVentas.vCantidad_Regalo + " " +
                                    desc_prod + " ";
                            log.debug(mensaje);
                            //lblPedidoRegalo2.setText(mensaje.trim());
                        }
                        }*/
                        ArrayList varray = new ArrayList();
                        varray.add(mensaje);
                        vMensajesRegalo.add((ArrayList)varray.clone());
                    }
                }
                arrayLista = new ArrayList();
            }
            log.debug("Msn " + vMensajesRegalo);
            String msn = "";
            for (int e = 0; e < vMensajesRegalo.size(); e++) {
                msn = msn + " / " + FarmaUtility.getValueFieldArrayList(vMensajesRegalo, e, 0);
            }


            if (msn.trim().length() > 1) {
                if (msn.length() > 300)
                    //jquispe 16.12.2011 se cambio para que el mensaje se muestre completo
                    msn = msn.substring(2, 800);
                else
                    msn = msn.substring(2);

                txtMensajesPedido.setLineWrap(true);
                txtMensajesPedido.setWrapStyleWord(true);
                txtMensajesPedido.setFont(new Font("SansSerif", Font.BOLD, 13));
                txtMensajesPedido.setForeground(new Color(7, 133, 7));
                txtMensajesPedido.setText(msn.trim());
                // FarmaUtility.moveFocus(txtMensajesPedido);
                FarmaUtility.moveFocus(txtDescProdOculto);
            }

        } catch (SQLException sql) {
            log.error("", sql);
        }


    }


    private void evaluaCantidadCupon() {
        lblMensajeCupon.setText("");
        try {
            //--Se verifica si puede o no acceder al regalo
            ArrayList vACampCuponplicables = new ArrayList();
            DBVentas.obtieneCampCuponAplicables(vACampCuponplicables, ConstantsVentas.TIPO_MULTIMARCA, "N");
            log.debug("...Camp Cupones aplicables : " + vACampCuponplicables);
            String cod_camp_cupon = "";
            String tipo_campana = "";
            String ind_mensaje = "";
            ArrayList vMensajesCampCupon = new ArrayList();
            String msg = "";
            for (int e = 0; e < vACampCuponplicables.size(); e++) {
                cod_camp_cupon = FarmaUtility.getValueFieldArrayList(vACampCuponplicables, e, COL_COD_CAMPANA);
                tipo_campana = FarmaUtility.getValueFieldArrayList(vACampCuponplicables, e, COL_TIPO_CAMPANA);
                ind_mensaje = FarmaUtility.getValueFieldArrayList(vACampCuponplicables, e, COL_IND_MENSAJE_CAMPANA);


                if (ind_mensaje.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                    if (tipo_campana.equalsIgnoreCase("M"))
                        msg = evaluaMultimarca(cod_camp_cupon);
                //else if(tipo_campana.equalsIgnoreCase("C"))
                //  msg = evaluaCampanaCupon(cod_camp_cupon);

                if (!msg.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    ArrayList varray = new ArrayList();
                    varray.add(msg);
                    vMensajesCampCupon.add((ArrayList)varray.clone());
                }
            }
            // Evaluando el mensaje
            log.debug("Msn " + vMensajesCampCupon);
            String msn = "";
            for (int e = 0; e < vMensajesCampCupon.size(); e++) {
                msn = msn + " / " + FarmaUtility.getValueFieldArrayList(vMensajesCampCupon, e, 0);
            }
            if (msn.length() > 3)
                lblMensajeCupon.setText(msn.trim().substring(2));


        } catch (SQLException sql) {
            log.error("", sql);
        }
    }

    private String evaluaCampanaCupon(String cod_camp_cupon) {

        return "N";
    }

    private String evaluaMultimarca(String cod_camp_cupon) throws SQLException {

        String mensaje = FarmaConstants.INDICADOR_N;
        ArrayList arrayLista = new ArrayList();
        DBVentas.analizaProdCampCupon(arrayLista, "N", cod_camp_cupon);
        log.debug("RESULTADO " + arrayLista);
        if (arrayLista.size() > 1) {
            String[] listDatosCupon =
                arrayLista.get(0).toString().substring(1, arrayLista.get(0).toString().length() - 1).split("&");

            VariablesVentas.vCodCampCupon = listDatosCupon[CON_COLUM_COD_CUPON];
            VariablesVentas.vDescCupon = listDatosCupon[CON_COLUM_DESC_CUPON];
            VariablesVentas.vMontoPorCupon = FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_MONT_CUPON]);

            VariablesVentas.vCantidadCupones = FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_CANT_CUPON]);


            log.debug("VariablesVentas.vCodCampCupon  " + VariablesVentas.vCodCampCupon);
            log.debug("VariablesVentas.vDescCupon  " + VariablesVentas.vDescCupon);
            log.debug("VariablesVentas.vMontoPorCupon  " + VariablesVentas.vMontoPorCupon);

            arrayLista.remove(0);
            ArrayList arrayProdCupon = (ArrayList)arrayLista.clone();
            if (arrayProdCupon.size() > 0) {
                log.debug("arrayProdCupon " + arrayProdCupon);
                log.debug("listDatosCupon " + listDatosCupon);
                double monto_actual_prod_cupon = calculoMontoProdEncarte(arrayProdCupon, 2);

                log.debug("...monto_actual_prod_cupon " + monto_actual_prod_cupon);
                log.debug("...VariablesVentas.vMontoPorCupon " + VariablesVentas.vMontoPorCupon);
                if (monto_actual_prod_cupon >= VariablesVentas.vMontoPorCupon) {
                    log.debug("...calculando numero de cupones para llevarse");
                    int cantCupones =
                        ((int)(monto_actual_prod_cupon / VariablesVentas.vMontoPorCupon)) * ((int)VariablesVentas.vCantidadCupones);
                    log.debug("Numero Cupones " + cantCupones);
                    mensaje = "Ganó " + cantCupones;
                    if (cantCupones == 1)
                        mensaje += " CUPON  (";
                    else
                        mensaje += " CUPONES  (";

                    mensaje += VariablesVentas.vDescCupon + ")";

                } else {
                    double dif = VariablesVentas.vMontoPorCupon - monto_actual_prod_cupon;
                    if (dif > 0) {
                        /* jquispe 30.11.2011 cambio para dar ams de 1 cupon x camp. multimarca
                          mensaje =
                                "Faltan S/. " + FarmaUtility.formatNumber(dif) +
                                " para 1 CUPON (" +
                                VariablesVentas.vDescCupon + ")";*/

                        mensaje =
                                "Faltan S/. " + FarmaUtility.formatNumber(dif) + " para " + (int)(VariablesVentas.vCantidadCupones) +
                                " CUPON (" + VariablesVentas.vDescCupon + ")";


                    }
                }
            }
        }

        arrayLista = new ArrayList();
        return mensaje;
    }
    
    // KMONCADA 2015.02.25 PROGRAMA DE PUNTOS
    private boolean isLectoraLazer, isCodigoBarra, isEnter;
    private long tiempoTeclaInicial ,tiempoTeclaFinal,OldtmpT2;
    private ArrayList lstTiempoTecla = new ArrayList();
    
    
    private boolean isNumero(char ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            //nfe.printStackTrace();
            return false;
        }
    }
    
    private void txtDescProdOculto_keyPressed(KeyEvent e) {
        if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE ||
              e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT ||
              e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_HOME)) {
            e.consume();

        }
        
        if(isNumero(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_ENTER ){
            if (isLectoraLazer) {
                isLectoraLazer = false;
            }
            isEnter = false;
            isLectoraLazer = false;
            if (tiempoTeclaInicial == 0){
                tiempoTeclaInicial = System.currentTimeMillis();
            }
            isCodigoBarra = true;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int maxTiempoLectora = UtilityPuntos.obtieneTiempoMaximoLectora();
                isLectoraLazer = false;
                tiempoTeclaFinal = System.currentTimeMillis();
                log.info("Tiem 2 " + (tiempoTeclaInicial));
                log.info("Tiem 1 " + (tiempoTeclaFinal));
                log.info("Tiempo de ingreso y ENTER " + (tiempoTeclaFinal - tiempoTeclaInicial));
                boolean validaFinal = true;
                for(int k=0;k<txtDescProdOculto.getText().toCharArray().length;k++){
                    validaFinal = validaFinal && isNumero(txtDescProdOculto.getText().toCharArray()[k]);
                }
                if ((tiempoTeclaFinal - tiempoTeclaInicial) <= maxTiempoLectora && txtDescProdOculto.getText().length() > 0 && validaFinal) {
                    isLectoraLazer = true;
                    tiempoTeclaInicial = 0;
                    log.info("ES CODIGO DE BARRA");
                    isCodigoBarra = true;
                    isEnter = true;
                    
                    if(UtilityPuntos.isActivoFuncionalidad()){
                        TarjetaBean tarjetaPuntosOld = null;
                        log.info("FUNCIONALIDAD DE PUNTOS ACTIVA");
                        if(VariablesPuntos.frmPuntos != null && UtilityPuntos.isTarjetaValida(txtDescProdOculto.getText().trim())){
                            if(VariablesPuntos.frmPuntos.getTarjetaBean()!=null){
                                if(JConfirmDialog.rptaConfirmDialog(this, "Programa Puntos:\nYa registro un cliente, ¿desea cambiarlo?")){
                                    tarjetaPuntosOld = VariablesPuntos.frmPuntos.getTarjetaBean();
                                    VariablesFidelizacion.vNumTarjeta = "";
                                    VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                                }else{
                                    return;
                                }
                            }
                            // KMONCADA 10.02.2015 INIT DE CONSULTA DE TARJETA DESLIZADA
                            boolean isAfiliado = UtilityPuntos.consultarTarjetaOrbis(myParentFrame, this, txtDescProdOculto, isLectoraLazer, false);
                            
                            TarjetaBean tarjetaCliente = VariablesPuntos.frmPuntos.getTarjetaBean();
                            if(tarjetaCliente!=null){
                                //valida cliente como fidelizado
                                funcionF12("N",!isAfiliado);
                                if(tarjetaPuntosOld != null){
                                    UtilityPuntos.transferirInscripcionBonificados(myParentFrame, this, tarjetaPuntosOld);
                                }
                                txtDescProdOculto.setText("");
                                return ;
                            }else{
                                txtDescProdOculto_keyPressed2(e);
                            }
                        }else{
                            if(VariablesPuntos.frmPuntos == null){
                                FarmaUtility.showMessage(this, "Programa de Lealtad:\nError al inicializar variables.", txtDescProdOculto);
                            }
                            if(!UtilityPuntos.isTarjetaValida(txtDescProdOculto.getText().trim())){
                                log.info("FORMATO DE TARJETA INVALIDO "+txtDescProdOculto.getText().trim());
                                txtDescProdOculto_keyPressed2(e);
                            }
                        }    
                    }else{
                        log.info("FUNCIONALIDAD DE PUNTOS NO ACTIVA");
                        txtDescProdOculto_keyPressed2(e);
                    }
                    
                } else {
                    isLectoraLazer = false;
                    tiempoTeclaInicial = 0;
                    log.info("FLUJO NORMAL");
                    isCodigoBarra = false;
                    txtDescProdOculto_keyPressed2(e);
                }
                //FarmaUtility.moveFocus(txtTarjeta);
            }
        }else{
            isCodigoBarra = false;
            log.info("FLUJO NORMAL");
            txtDescProdOculto_keyPressed2(e);
        }
    }

    public void cargaTarjFidelizacion(String cadena){
        boolean pPermite = false;
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            /*FarmaUtility.showMessage(this, "No puede ingresar más de una tarjeta.",
                                     txtDescProdOculto);*/
            if(JConfirmDialog.rptaConfirmDialog(this, "Programa club de descuentos:\nYa registro una tarjeta, ¿desea cambiarlo?")){            
                VariablesFidelizacion.limpiaVariables();
                pPermite=true;
            }
            else
                pPermite=false;
        }
        else
            pPermite=true;
        
        
        if(pPermite){
        validarClienteTarjeta(cadena, true);
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            UtilityFidelizacion.operaCampañasFidelizacion(cadena);
            VariablesFidelizacion.vDNI_Anulado =
                    UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
            VariablesFidelizacion.vAhorroDNI_x_Periodo =
                    UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,
                                                                   VariablesFidelizacion.vNumTarjeta);
            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                    UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,
                                                                   VariablesFidelizacion.vNumTarjeta);

            AuxiliarFidelizacion.setMensajeDNIFidelizado(lblDNI_Anul, "R", txtDescProdOculto,
                                                         this);
            neoOperaResumenPedido();
        }       
        }
        txtDescProdOculto.setText("");
    }
    
    private void txtDescProdOculto_keyPressed2(KeyEvent e) {
        //if (e.getKeyCode() == KeyEvent.VK_ALT) {e.consume();return;}

        //ERIOS 15.01.2014 Correccion para lectura de escaner NCR
        //log.debug("Tecla: " + e.getKeyCode() + " (" + e.getKeyChar() + ")");
        if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE ||
              e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT ||
              e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_HOME)) {
            e.consume();
        }

        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos, txtDescProdOculto, 1);
        String vCadIngresada = txtDescProdOculto.getText();

        //kmoncada 16.07.2014 RECONOCE CONVINACION DE TECLAS ALT +
        if (e.getKeyCode() == KeyEvent.VK_ALT && contarCombinacion == 0) {
            contarCombinacion++;
        } else {
            if (contarCombinacion == 1) {
                switch (e.getKeyCode()) {

                    //SOLICITARON QUE NO EXISTA Y QUE SEA DE ACUERDO A LA CONEXION o NO.
                    /*case KeyEvent.VK_C: // ALT + C // LTAVARA 29.08.2014 activa Contingencia
                        if(cargaLoginAdmLocal()){

                            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"Está seguro que Desea el Activar Proceso Contingencia?"))
                            {
                            EposVariables.vFlagComprobanteE= false;
                            }

                        }
                    break;*/
                case KeyEvent.VK_SPACE: // ALT + BARRA ESPACIADORA
                    abrePedidosDelivery();
                    break;


                }
                contarCombinacion = 0;
                return;
            }
            contarCombinacion = 0;
        }

        if (!UtilityVentas.isNumerico(vCadIngresada) && vCadIngresada.indexOf("%") != 0) {
            tblProductos_keyPressed(e);
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                //ERIOS 03.07.2013 Limpia la caja de texto
                limpiaCadenaAlfanumerica(txtDescProdOculto);
                if (pasoTarjeta) {
                    txtDescProdOculto.setText("");
                    pasoTarjeta = false;
                    return;
                }

                setFormatoTarjetaCredito(txtDescProdOculto.getText().trim());
                String codProd = txtDescProdOculto.getText().trim();
                if (UtilityVentas.isNumerico(codProd)) {

                    String cadena = codProd.trim();
                    String formato = "";
                    if (cadena.trim().length() > 6)
                        formato = cadena.substring(0, 5);
                    if (formato.equals("99999")) {
                        if (UtilityFidelizacion.EsTarjetaFidelizacion(cadena)) {
                            //dubilluz 22.04.2015
                            cargaTarjFidelizacion(cadena);
                            return;
                        } else {
                            FarmaUtility.showMessage(this, "La tarjeta no es valida", null);
                            txtDescProdOculto.setText("");
                            FarmaUtility.moveFocus(txtDescProdOculto);
                            return;
                        }
                    }

                    if (cadena.trim().length() > 6) {
                        formato = cadena.substring(0, 5);
                    }
                    if (formato.equals("99999")) {
                        return;
                    }

                    if (UtilityVentas.esCupon(cadena, this, txtDescProdOculto)) {
                        //ERIOS 2.3.2 Valida convenio BTLMF
                        if (VariablesVentas.vEsPedidoConvenio ||
                            (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                             VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)) {
                            FarmaUtility.showMessage(this, "No puede agregar cupones a un pedido por convenio.",
                                                     txtDescProdOculto);
                            return;
                        }
                        validarAgregarCupon(cadena);
                        return;
                    }

                    boolean pIsTarjetaEnCampana = UtilityFidelizacion.isTarjetaPagoInCampAutomatica(cadena.trim());
                    if (pIsTarjetaEnCampana) {
                        validaIngresoTarjetaPagoCampanaAutomatica(cadena.trim());
                        return;
                    }

                    if (VariablesFidelizacion.vDniCliente != "") {
                        VariablesCampana.vFlag = false;

                        if (codProd.length() == 0) {
                            return;
                        }

                        if (codProd.length() < 6) {
                            FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                     txtDescProdOculto);
                            VariablesVentas.vCodProdBusq = "";
                            VariablesVentas.vKeyPress = null;
                            return;
                        }

                        buscaProducto(codProd, e);
                    } else {

                        buscaProducto(codProd, e);

                        if (codProd.length() == 0) {
                            return;
                        }

                        if (codProd.length() < 6) {
                            FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                     txtDescProdOculto);
                            VariablesVentas.vCodProdBusq = "";
                            VariablesVentas.vKeyPress = null;
                            return;
                        }

                    }
                }

            }
        }

        if (vCadIngresada.length() > 6 && vCadIngresada != null) {
            if (Character.isLetter(vCadIngresada.charAt(0)) && (!Character.isLetter(vCadIngresada.charAt(1)))) {
                vCadIngresada = UtilityPtoVenta.getCodBarraSinCarControl(vCadIngresada);
                String vTemp = UtilityPtoVenta.getCadenaAlfanumerica(vCadIngresada);
                log.debug(vTemp);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    try {
                        log.debug(vTemp);
                        buscaProducto(vTemp, e);

                    } catch (Exception ex) {
                        log.error(" ", ex);
                    }
                }


            }


        }
    }

    /**
     * BUSCANDO EN EL ARREGLO DE CUPONES o campanias que no haya agregado anteriormente
     * @author JMINAYA
     * @since  01.09.2008
     */
    public boolean busca_producto_cupon(String vcodigo) {
        //empieza la busqueda del producto en el arrayList
        //String codigo;
        Map mapaCupon = new HashMap();
        for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
            //codigo = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Cupones,i,0);
            mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(i);
            if (mapaCupon.get("COD_CUPON").toString().trim().equals(vcodigo.trim()))
                return true;
        }
        return false;
    }

    private void txtDescProdOculto_keyReleased(KeyEvent e) {
        if (1 == 1)
            return;
        // log.debug("en el oculto " + e.getKeyChar());
        String cadena = txtDescProdOculto.getText().trim();
        //log.debug("cadena " +cadena );
        //varNumero = isNumerico(cadena.trim());
        //log.debug("es numero "+isNumero);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            /*String formato = "";
        if(cadena.trim().length()>6)
           formato = cadena.substring(0, 5);
        if (formato.equals("99999"))
            return;


      if(UtilityVentas.esCupon(cadena,this,txtDescProdOculto))
      {
        agregarCupon(cadena);
      }
      else*/
            limpiaCadenaAlfanumerica(txtDescProdOculto);
            if (UtilityVentas.isNumerico(cadena) && VariablesFidelizacion.vNumTarjeta.trim().length() == 0 &&
                //!busca_producto_cupon(cadena.trim())
                !UtilityVentas.esCupon(cadena, this, txtDescProdOculto)) {
                //log.debug("presiono enter");
                e.consume();
                String productoBuscar = txtDescProdOculto.getText().trim().toUpperCase();

                txtDescProdOculto.setText(productoBuscar);
                if (productoBuscar.length() == 0)
                    return;

                String codigo = "";
                // revisando codigo de barra
                char primerkeyChar = productoBuscar.charAt(0);
                char segundokeyChar;

                if (productoBuscar.length() > 1)
                    segundokeyChar = productoBuscar.charAt(1);
                else
                    segundokeyChar = primerkeyChar;

                char ultimokeyChar = productoBuscar.charAt(productoBuscar.length() - 1);

                if (productoBuscar.length() > 6 &&
                    (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) &&
                                                            (!Character.isLetter(ultimokeyChar))))) {
                    String cod_barra = productoBuscar + "";
                    txtDescProdOculto.setText(cod_barra);
                    // log.debug("cod_barra "+cod_barra);
                    VariablesVentas.vCodBarra = cod_barra;
                    VariablesVentas.vKeyPress = e;

                    //agregarProducto();
                    agregarProducto(null); //ASOSA - 10/10/2014 - PANHD

                    VariablesVentas.vCodBarra = "";
                    VariablesVentas.vKeyPress = null;
                }

                FarmaUtility.moveFocus(txtDescProdOculto);
                txtDescProdOculto.setText("");
            }

        }
    }

    private void txtDescProdOculto_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '+' && !pIngresoComprobanteManual) {
            e.consume();
            //validaConvenio(e, VariablesConvenio.vPorcCoPago);
            //JMIRANDA 23.06.2010
            validaConvenio_v3(e, VariablesConvenio.vPorcCoPago);
            FarmaUtility.moveFocus(txtDescProdOculto);
        }
    }

    /**
     * Se muestra filtro para filtrar los productos
     * @author JCORTEZ
     * @since  17.04.2008
     */
    private void mostrarFiltro() {

        DlgFiltro dlgFiltro = new DlgFiltro(myParentFrame, "", true);
        dlgFiltro.setVisible(true);
        if (FarmaVariables.vAceptar) {
            //agregarProducto();
            agregarProducto(null); //ASOSA - 10/10/2014 - PANHD
            //FarmaVariables.vAceptar = false;
        }

        FarmaUtility.moveFocus(txtDescProdOculto);

    }

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList, String codProd) {
        try {
            DBVentas.obtieneInfoProducto(pArrayList, codProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtDescProdOculto);
        }
    }

    private void muestraTratamiento() {
        if (tblProductos.getRowCount() == 0)
            return;


        int vFila = tblProductos.getSelectedRow();

        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 0);
        VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 1);
        VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 9);

        VariablesVentas.vCant_Ingresada_Temp = ((String)(tblProductos.getValueAt(vFila, 4))).trim();
        VariablesVentas.vVal_Frac = ((String)(tblProductos.getValueAt(vFila, 10))).trim();

        VariablesVentas.vCant_Vta = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 4);
        VariablesVentas.vIndModificacion = FarmaConstants.INDICADOR_S;

        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_S;
        VariablesVentas.vCantxDia = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_RES_CANT_XDIA);
        VariablesVentas.vCantxDias = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_RES_CANT_DIAS);


        DlgTratamiento dlgtratemiento = new DlgTratamiento(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = false;
        dlgtratemiento.setVisible(true);

        if (FarmaVariables.vAceptar) {
            seleccionaProducto(vFila);
            FarmaVariables.vAceptar = false;
            VariablesVentas.vIndDireccionarResumenPed = true;
        } else
            VariablesVentas.vIndDireccionarResumenPed = false;


        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";
        VariablesVentas.vCant_Vta = "";
        VariablesVentas.vIndTratamiento = "";

    }

    private void procesoPack(String pNumPed) {
        try {
            //DBVentas.procesoPackRegalo(pNumPed.trim()); Antes
            DBVentas.procesoPackRegalo_02(pNumPed.trim()); //ASOSA, 06.07.2010
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtDescProdOculto);
        }
    }


    /**
     * metodo de encargado de validar y agregar los cupones
     * @param nroCupon
     * @author Javier Callo Quispe
     * @since 04.03.2009
     */
    private void validarAgregarCupon(String nroCupon) {
        Map mapCupon;
        String codCampCupon = nroCupon.substring(0, 5);
        try {
            mapCupon = DBVentas.getDatosCupon(codCampCupon, nroCupon);
            mapCupon.put("COD_CUPON", nroCupon);
        } catch (SQLException e) {
            log.debug("ocurrio un error al obtener datos del cupon:" + nroCupon + " error:" + e);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener datos del cupon.\n" +
                    e.getMessage() +
                    "\n Inténtelo Nuevamente\n si persiste el error comuniquese con operador de sistemas.",
                    txtDescProdOculto);
            return;
        }
        log.debug("datosCupon:" + mapCupon);
        //Se verifica si el cupon ya fue agregado tambien verifica si ya existe la campaña
        if (UtilityVentas.existeCuponCampana(nroCupon, this, txtDescProdOculto))
            return;

        String indMultiuso = mapCupon.get("IND_MULTIUSO").toString().trim();
        boolean obligarIngresarFP = isFormaPagoUso_x_Cupon(codCampCupon); //saber si la campaña pide forma de pago
        boolean yaIngresoFormaPago = false;


        //jquispe
        //String vIndFidCupon = "N";//obtiene el ind fid -- codCampCupon
        String vIndFidCupon = UtilityFidelizacion.getIndfidelizadoUso(codCampCupon);
        //if(vIndFidCupon.trim().equalsIgnoreCase("S")){
        //inicio dubilluz 09.06.2011
        if (vIndFidCupon.trim().equalsIgnoreCase("S")) {
            if (VariablesFidelizacion.vDniCliente.trim().length() == 0) {
                funcionF12(codCampCupon,true);
                yaIngresoFormaPago = true;
            }

            //fin daubilluz 09.06.2011
            if (VariablesFidelizacion.vDniCliente.trim().length() >
                0) /*&&vIndFidCupon.trim().equalsIgnoreCase("S")*/ {

                //Consultara si es necesario ingresar forma de pago x cupon
                // si es necesario solicitará el mismo.
                if (obligarIngresarFP) {
                    if (!yaIngresoFormaPago)
                        funcionF12(codCampCupon,true);

                }
                //validacion de cupon en base de datos vigente y to_do lo demas
                if (!UtilityVentas.validarCuponEnBD(nroCupon, this, txtDescProdOculto, indMultiuso,
                                                    VariablesFidelizacion.vDniCliente)) {
                    return;
                } else {
                    evaluaFormaPagoFidelizado();
                    //agregando cupon al listado
                    VariablesVentas.vArrayList_Cupones.add(mapCupon);
                    //24.06.2011
                    //Para Reinicializar todas las formas de PAGO.
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                    VariablesVentas.vMensCuponIngre = "Se ha agregado el cupón " + nroCupon + " de la Campaña\n" +
                            mapCupon.get("DESC_CUPON").toString() + ".";

                    //se comento lo que sigue ya que no se tiene claro pa que se usa
                    /*VariablesCampana.vCodCupon = nroCupon;
                               if(VariablesCampana.vListaCupones.size()>0)
                                       VariablesCampana.vFlag = true;
                               VariablesCampana.vDescCamp = mapCupon.get("DESC_CUPON").toString();*/
                    FarmaUtility.showMessage(this, VariablesVentas.vMensCuponIngre, txtDescProdOculto);
                }
                txtDescProdOculto.setText("");
                lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);
            }

        } else {
            //PIDE SI necesita ES FORMA DE PAGO
            //Falta inigresar forma de pago en OTRA PANTALLA
            if (obligarIngresarFP) {
                if (!yaIngresoFormaPago)
                    funcionF12(codCampCupon,true);

            }

            if (!UtilityVentas.validarCuponEnBD(nroCupon, this, txtDescProdOculto, indMultiuso, "")) {
                return;
            } else {
                evaluaFormaPagoFidelizado(); //SETETA el label de TARJETA UNICA
                //agregando cupon al listado
                VariablesVentas.vArrayList_Cupones.add(mapCupon);
                //24.06.2011
                //Para Reinicializar todas las formas de PAGO.
                /*
                    UtilityFidelizacion.operaCampañasFidelizacion("");
                */
                VariablesVentas.vMensCuponIngre = "Se ha agregado el cupón " + nroCupon + " de la Campaña\n" +
                        mapCupon.get("DESC_CUPON").toString() + ".";

                FarmaUtility.showMessage(this, VariablesVentas.vMensCuponIngre, txtDescProdOculto);
            }
            txtDescProdOculto.setText("");
            lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);
        }
        //calcular totales pedido despues de haber agregado un nuevo cupon
        //calculaTotalesPedido();
        neoOperaResumenPedido();
    }

    public boolean tieneDatoFormaPagoFidelizado() {
        if (VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S") ||
            (VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S") &&
             VariablesFidelizacion.vCodFPagoTarjeta.trim().length() > 0))
            return true;
        else
            return false;
    }

    /**
     * Se muestra el mensaje personalizado al usuario.
     */
    private void muestraMensajeUsuario() {
        ArrayList vAux = new ArrayList();
        String mensaje;
        try {
            DBUsuarios.getMensajeUsuario(vAux, FarmaVariables.vNuSecUsu);
            if (vAux.size() > 0) {
                log.debug("Se muestra mensaje al usuario");
                mensaje = FarmaUtility.getValueFieldArrayList(vAux, 0, 0);
                DlgMensajeUsuario dlgMensajeUsuario = new DlgMensajeUsuario(myParentFrame, "", true);
                dlgMensajeUsuario.setMensaje(mensaje);
                dlgMensajeUsuario.setVisible(true);
                DBUsuarios.actCantVeces(FarmaVariables.vNuSecUsu);
            }
        } catch (SQLException e) {
            log.error(null, e);
        }

    }

    /**
     * Valida monto de cupones del pedido
     * se reempn el nuevo metodo validaCampsMontoNetoPedido
     * @author dubilluz
     * @since  23.07.2008, modif 11.03.2009
     * @param  pNetoPedido
     * @deprecated
     * @return boolean
     */
    private boolean validaUsoCampanaMonto(String pNetoPedido) {
        String vCodCupon;
        String vCodCamp;
        String vIndTipoCupon;
        double vValorCupon;

        double vValorAcumuladoCupones = 0.0;
        double vNetoPedido = FarmaUtility.getDecimalNumber(pNetoPedido.trim());

        String vIndValido;

        ArrayList cupon = new ArrayList();
        for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
            cupon = (ArrayList)VariablesVentas.vArrayList_Cupones.get(j);
            vCodCupon = cupon.get(0).toString();
            vCodCamp = cupon.get(1).toString();
            vIndTipoCupon = cupon.get(2).toString();
            if (vIndTipoCupon.equalsIgnoreCase(ConstantsVentas.TIPO_MONTO)) {
                vValorCupon = FarmaUtility.getDecimalNumber(cupon.get(3).toString());
                vIndValido = cupon.get(8).toString();
                if (vIndValido.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                    vValorAcumuladoCupones += vValorCupon;

                }
            }

        }

        // VALIDANDO EL MONTO DEL PEDIDO CON LA SUMA DE CUPONES
        boolean indNoUsaCupones = false;
        if (vValorAcumuladoCupones > 0) {
            if (vNetoPedido < vValorAcumuladoCupones) {
                if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                "El monto del pedido es menor que" +
                                                                                " la suma de los cupones.\n" +
                        "¿Desea generar el pedido y " + "perder la diferencia?")) {
                    for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                        cupon = (ArrayList)VariablesVentas.vArrayList_Cupones.get(j);
                        vCodCupon = cupon.get(0).toString();
                        vCodCamp = cupon.get(1).toString();
                        vIndTipoCupon = cupon.get(2).toString();
                        if (vIndTipoCupon.equalsIgnoreCase(ConstantsVentas.TIPO_MONTO)) {
                            vValorCupon = FarmaUtility.getDecimalNumber(cupon.get(3).toString());
                            vIndValido = cupon.get(8).toString();
                            if (vIndValido.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                                if (indNoUsaCupones)
                                    cupon.set(8, FarmaConstants.INDICADOR_N);
                                else {
                                    vNetoPedido -= vValorCupon;
                                    if (vNetoPedido <= 0)
                                        indNoUsaCupones = true;
                                    log.debug("vNetoPedido " + vNetoPedido);
                                }

                            }
                        }

                    }
                    log.debug("VariablesVentas.vArrayList_Cupones " + VariablesVentas.vArrayList_Cupones);
                    return true;
                } else
                    return false;
            } else
                return true;
        } else
            return true;


    }

    private void validarClienteTarjeta(String cadena, boolean isTarjetaAntigua) {
        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this, "No puede agregar una tarjeta a un " + "pedido por convenio.",
                                     txtDescProdOculto);
            txtDescProdOculto.setText("");
            return;
        }
        // KMONCADA 2015.02.27 REALIZA LA CONSULTA DE LOS DATOS DE RENIEC.
        try{
            String nroDni = DBFidelizacion.getDniClienteFidelizado(cadena);
            if(nroDni != null){
                ArrayList array = new ArrayList();
                DBFidelizacion.buscarTarjetasXDNI(array, nroDni, null);
            }
        }catch(Exception ex){
            log.error("",ex);
        }
        UtilityFidelizacion.validaLecturaTarjeta(cadena, myParentFrame);
        if (VariablesFidelizacion.vDataCliente.size() > 0) {
            ArrayList array = (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            UtilityFidelizacion.setVariablesDatos(array);
            // KMONCADA 12.02.2015 FUNCIONALIDAD DE PROGRAMA DE PUNTOS PARA TARJETAS ANTERIORES
            try{
                if(UtilityPuntos.isActivoFuncionalidad()){
                    if(isTarjetaAntigua){
                        txtDescProdOculto.setText(cadena);
                        UtilityPuntos.consultarTarjetaOrbis(myParentFrame, this, txtDescProdOculto, false, true);
                    }
                }
            }catch(Exception ex){
                log.error("",ex);
                FarmaUtility.showMessage(this, "ERROR EN PROGRAMA DE PUNTOS Y LEALTAD", null);
            }
            //fin jcallo 02.10.2008
            if (VariablesFidelizacion.vIndExisteCliente) {
                if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                    DlgMensajeBienvenida.showMensajeBienvenida(myParentFrame);
                }else{
                    FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
                }
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("0")) {
                if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                    DlgMensajeBienvenida.showMensajeBienvenida(myParentFrame);
                }else{
                    FarmaUtility.showMessage(this, "Se agrego el DNI :" + VariablesFidelizacion.vDniCliente, null);
                }
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("2")) {
                /*FarmaUtility.showMessage(this,
                                         "Cliente encontrado con DNI " + VariablesFidelizacion.vDniCliente,
                                         null);*/
                if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                    DlgMensajeBienvenida.showMensajeBienvenida(myParentFrame);
                }else{
                    FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
                }
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("1")) {
                if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                    DlgMensajeBienvenida.showMensajeBienvenida(myParentFrame);
                }else{
                    FarmaUtility.showMessage(this,
                                         "Se actualizaron los datos del DNI :" + VariablesFidelizacion.vDniCliente,
                                         null);
                }
            }

            lblCliente.setText(VariablesFidelizacion.vNomCliente); /*+" "
                               +VariablesFidelizacion.vApePatCliente+" "
                               +VariablesFidelizacion.vApeMatCliente);*/


            VariablesVentas.vArrayList_CampLimitUsadosMatriz = new ArrayList();
            //Ya no validara en Matriz
            //14.04.2009 DUBILLUZ
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            /*log.debug("**************************************");
            VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
            																	FarmaConstants.INDICADOR_N);
            log.debug("************************");
            if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ
            	log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente"+VariablesFidelizacion.vDniCliente);
            	VariablesVentas.vArrayList_CampLimitUsadosMatriz = CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);
            	log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz"+VariablesVentas.vArrayList_CampLimitUsadosMatriz);
            }
            */
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz

            //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
            neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009


            VariablesFidelizacion.vIndAgregoDNI = "";
            //dubilluz 19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() > 0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),
                                                      VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin
        } else {
            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente); /*+" "
                               +VariablesFidelizacion.vApePatCliente+" "
                               +VariablesFidelizacion.vApeMatCliente);*/
            //fin jcallo 02.10.2008
        }
        txtDescProdOculto.setText("");
    }
    /*
    private void mostrarBuscarTarjetaPorDNI() {

        DlgFidelizacionBuscarTarjetas dlgBuscar =
            new DlgFidelizacionBuscarTarjetas(myParentFrame, "", true);
        dlgBuscar.setVisible(true);
        log.debug("vv:" + FarmaVariables.vAceptar);
        if (FarmaVariables.vAceptar) {
            log.debug("despues de haber encontrado el cliente");
            log.debug("CLIENTE......:" + VariablesFidelizacion.vDataCliente);
            ArrayList array =
                (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            UtilityFidelizacion.setVariablesDatos(array);
            FarmaUtility.showMessage(this, "Bienvenido \n" +
                    VariablesFidelizacion.vNomCliente + " " +
                    VariablesFidelizacion.vApePatCliente + " " +
                    VariablesFidelizacion.vApeMatCliente + "\n" +
                    "DNI: " + VariablesFidelizacion.vDniCliente, null);
            //dubilluz 19.07.2011 - inicio
            if(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length()>0){
               UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin
            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente);
            //fin jcallo 02.10.2008
            //DAUBILLUZ -- Filtra los DNI anulados
            //25.05.2009
            VariablesFidelizacion.vDNI_Anulado =
                    UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
            VariablesFidelizacion.vAhorroDNI_x_Periodo =
                    UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                    UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);


            log.info("Variable de DNI_ANULADO: " +
                     VariablesFidelizacion.vDNI_Anulado);
            log.info("Variable de vAhorroDNI_x_Periodo: " +
                     VariablesFidelizacion.vAhorroDNI_x_Periodo);
            log.info("Variable de vMaximoAhorroDNIxPeriodo: " +
                     VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
            setMensajeDNIFidelizado();
            if (VariablesFidelizacion.vDNI_Anulado) {
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
                log.debug("**************************************");
                //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                VariablesFidelizacion.vIndConexion =
                        FarmaConstants.INDICADOR_N;

                log.debug("************************");
                //      if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ   // JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
                log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" +
                          VariablesFidelizacion.vDniCliente);
                VariablesVentas.vArrayList_CampLimitUsadosMatriz =
                        CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);
                log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" +
                          VariablesVentas.vArrayList_CampLimitUsadosMatriz);
                //     } // JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local


                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            } else {
                log.info("Cliente esta invalidado para descuento...");
            }

            //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
            neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009


        }
        FarmaUtility.moveFocus(txtDescProdOculto);
    }*/

    /**
     * el procedimiento en  BASE DE DATOS esta haciendo commit cuando no debe
     * @author  author JCALLO
     * @since   09.10.08
     * @param array
     */
    private void actualizarAhorroProdVentaDet(Map mProdDcto) {
        try {
            DBVentas.guardaDctosDetPedVta(mProdDcto.get("COD_PROD").toString(),
                                          mProdDcto.get("COD_CAMP_CUPON").toString(),
                                          mProdDcto.get("VALOR_CUPON").toString(), mProdDcto.get("AHORRO").toString(),
                                          mProdDcto.get("DCTO_AHORRO").toString(),
                                          mProdDcto.get("SEC_PED_VTA_DET").toString());
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    /* *************************************************************************** */
    //Inicio de Campañas Acumuladas DUBILLUZ 18.12.2008

    /**
     * @author Dubilluz
     * @since  17.12.2008
     * @param  pNumPed,pDniCli
     */
    private void procesoCampañasAcumuladas(String pNumPed, String pDniCli) {
        if (pDniCli.trim().length() == 0) {
            log.debug("No es fidelizado...");
            return;
        }
        log.info("inicio operaAcumulacionCampañas");
        //--1.Se procesa si acumula unidades
        operaAcumulacionCampañas(pNumPed, pDniCli);
        log.info("FIN operaAcumulacionCampañas");
        // Se inserta los pedidos de campañas acumuladas en el local a Matriz
        //--2.Se valida si hay linea
        String pIndLinea = FarmaConstants.INDICADOR_N;
        /*
                             * FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                           FarmaConstants.INDICADOR_N);
                             */
        //--3.No hay linea to_do el proceso concluye y solo acumula
        log.info("pIndLinea:" + pIndLinea);

        if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            FarmaConnectionRemoto.closeConnection();
            log.info("PASO 1");
        }
        /*
        else{
        log.info("Evalua Campaña Acumulada");
          // Se inserta los pedidos de campañas acumuladas en el local a Matriz
          enviaUnidadesAcumuladasLocalMatriz(pNumPed,pDniCli,pIndLinea);

          // Se opera el intento de canje y se acumula en el local
          operaRegaloCampañaAcumulada(pNumPed,pDniCli);

        }
        */
        log.info("INICIO operaRegaloCampañaAcumulada");
        // Se opera el intento de canje y se acumula en el local
        operaRegaloCampañaAcumulada(pNumPed, pDniCli);
        log.info("FIN operaRegaloCampañaAcumulada");
        //Actualiza los datos en la cabecera si acumulo o gano algun canje para poder
        //indentificar si el pedido es de fidelizado y campaña acumulada
        log.debug("Actualiza datos");
        actualizaDatoPedidoCabecera(pNumPed, pDniCli);

    }

    private void actualizaDatoPedidoCabecera(String pNumPed, String pDniCli) {
        try {
            DBVentas.actualizaPedidoXCampanaAcumulada(pNumPed, pDniCli);
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    /**
     *
     * @param pNumPed
     * @param pDniCli
     */
    private void operaAcumulacionCampañas(String pNumPed, String pDniCli) {
        try {
            DBVentas.operaAcumulaUnidadesCampaña(pNumPed, pDniCli);
        } catch (SQLException e) {
            log.error("", e);
        }
    }


    /**
     * Envia unidades acumulaciones local matriz
     * @author Dubilluz
     * @param  pNumPedido
     * @param  pDniCli
     * @param  pIndLinea
     */
    private void enviaUnidadesAcumuladasLocalMatriz(String pNumPedido, String pDniCli, String pIndLinea) {
        ArrayList pListaAcumulados = new ArrayList();
        int COL_DNI = 0, COL_CIA = 1, COL_COD_CAMP = 2, COL_LOCAL = 3, COL_NUM_PED = 4, COL_FECH_PED =
            5, COL_SEC_PED_VTA = 6, COL_COD_PROD = 7, COL_CANT_PED = 8, COL_VAL_FRAC_PED = 9, COL_ESTADO =
            10, COL_VAL_FRAC_MIN = 11, COL_USU_CREA = 12, COL_CANT_RESTANTE = 13;

        boolean indInsertMatriz = false;

        String pDni, pCodCia, pCodCamp, pCodLocal, pNumPed, pFechPed, pSecDet, pCodProd, pCantPed, pValFrac, pEstado, pValFracMin, pUsuCrea, pCantRestante;

        try {
            DBVentas.getListaUnidadesAcumuladas(pListaAcumulados, pNumPedido, pDniCli);

            if (pListaAcumulados.size() > 0) {
                /*
                if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                        for(int i=0;i<pListaAcumulados.size();i++){

                            pDni  = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_DNI).trim();
                            pCodCia = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_CIA).trim();
                            pCodCamp = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_COD_CAMP).trim();
                            pCodLocal = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_LOCAL).trim();
                            pNumPed = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_NUM_PED).trim();
                            pFechPed = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_FECH_PED).trim();
                            pSecDet = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_SEC_PED_VTA).trim();
                            pCodProd = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_COD_PROD).trim();
                            pCantPed = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_CANT_PED).trim();
                            pValFrac = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_VAL_FRAC_PED).trim();
                            pEstado = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_ESTADO).trim();
                            pValFracMin = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_VAL_FRAC_MIN).trim();
                            pUsuCrea = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_USU_CREA).trim();
                            pCantRestante = FarmaUtility.getValueFieldArrayList(pListaAcumulados,i,COL_CANT_RESTANTE).trim();
                           DBVentas.insertaAcumuladosEnMatriz(
                                                              pDni, pCodCia, pCodCamp,
                                                              pCodLocal, pNumPed, pFechPed,
                                                              pSecDet, pCodProd, pCantPed,
                                                              pValFrac, pEstado, pValFracMin,
                                                              pUsuCrea,pCantRestante
                                                             );
                            indInsertMatriz = true;
                            log.debug("..envia a matriz..");


                        }

              a matriz
                                  // Si envio DBVentas.actualizaProcesoMatrizHistorico(pNumPedido,pDniCli,FarmaConstants.INDICADOR_S);
                    }
                    else{
                        log.debug("Acumula unidades y no envia a Matriz");
                        // No envia a Matriz
                        DBVentas.actualizaProcesoMatrizHistorico(pNumPedido,pDniCli,FarmaConstants.INDICADOR_N);
                    }
                 */
                log.debug("Acumula unidades y no envia a Matriz");
                DBVentas.actualizaProcesoMatrizHistorico(pNumPedido, pDniCli, FarmaConstants.INDICADOR_N);
            } else
                log.debug("No acumulo ninguna unidad...");
        } catch (SQLException e) {
            log.error("", e);
        } finally {
            if (indInsertMatriz) // -- indica de linea
            {
                if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                }
            }
        }

    }

    private void operaRegaloCampañaAcumulada(String pNumPed, String pDniCli) {
        ArrayList listaPedOrigen = new ArrayList();
        int COL_COD_CAMP = 0, COL_LOCAL_ORIGEN = 1, COL_NUM_PED_ORIGEN = 2, COL_SEC_PED_ORIGEN =
            3, COL_COD_PED_ORIGEN = 4, COL_CANT_USO = 5, COL_VAL_FRAC_MIN = 6;

        String pCodCamp, pCodLocalOrigen, pNumPedOrigen, pSecDetOrigen, pCodProdOrigen, pCantUsoOrigen, pValFracMinOrigen;


        try {
            DBVentas.operaIntentoRegaloCampañaAcumulada(listaPedOrigen, pNumPed, pDniCli);

            ArrayList listaCAEfectivas = new ArrayList();
            boolean vIndAgregaElmento = true;

            //1. Opera el listado auxiliares y se obtienen las campañas que regalaran
            for (int j = 0; j < listaPedOrigen.size(); j++) {
                vIndAgregaElmento = true;
                pCodCamp = FarmaUtility.getValueFieldArrayList(listaPedOrigen, j, COL_COD_CAMP).trim();
                for (int k = 0; k < listaCAEfectivas.size(); k++) {
                    if (pCodCamp.trim().equalsIgnoreCase(listaCAEfectivas.get(k).toString().trim())) {
                        vIndAgregaElmento = false;
                        break;
                    }
                }
                if (vIndAgregaElmento)
                    listaCAEfectivas.add(pCodCamp);
            }
            log.info("Lista CAEfectivas " + listaCAEfectivas);
            //2. Agrega Productos regalo de cada campañas
            for (int i = 0; i < listaCAEfectivas.size(); i++) {
                //obtiene la campaña para añadir los regalos
                pCodCamp = listaCAEfectivas.get(i).toString().trim();
                //DBVentas.añadeRegaloCampaña(pNumPed,pDniCli,pCodCamp); antes
                DBVentas.añadeRegaloCampaña_02(pNumPed, pDniCli, pCodCamp); //ASOSA, 07.07.2010
            }


            //se obtiene las campañas de regalo efectiva realizadas
            ArrayList listaCanjesRealizados = new ArrayList();
            DBCaja.getPedidosCanj(pDniCli, pNumPed, listaCanjesRealizados);
            ArrayList listaCanjesPosibles = (ArrayList)listaCAEfectivas.clone();
            String codCampana = "";
            for (int i = 0; i < listaCanjesRealizados.size(); i++) {

                codCampana = FarmaUtility.getValueFieldArrayList(listaCanjesRealizados, i, 0);

                for (int j = 0; j < listaCanjesPosibles.size(); j++) {
                    //obtiene la campaña para añadir los regalos
                    pCodCamp = listaCanjesPosibles.get(j).toString().trim();
                    if (pCodCamp.trim().equalsIgnoreCase(codCampana.trim())) {
                        listaCanjesPosibles.remove(j);
                        break;
                    }
                }

            }
            log.debug("Camapañas no realizadas:" + listaCanjesPosibles);
            ////

            //3. Agrega los pedido de productos origen
            /*
            añadePedidosOrigenCanje(
                                                            String pDniCli,
                                                            String pCodCamp,
                                                            String pNumPedidoVenta,
                                                            String pCodLocalOrigen,
                                                            String pNumPedOrigen,
                                                            String pSecPedOrigen,
                                                            String pCodProdOrigen,
                                                            String pCantUsoOrigen,
                                                            String pValFracMinOrigen
             * */
            boolean vEfectivo = true;
            for (int i = 0; i < listaPedOrigen.size(); i++) {
                //obtiene la campaña para añadir los regalos
                pCodCamp = FarmaUtility.getValueFieldArrayList(listaPedOrigen, i, COL_COD_CAMP).trim();
                pCodLocalOrigen = FarmaUtility.getValueFieldArrayList(listaPedOrigen, i, COL_LOCAL_ORIGEN).trim();
                pNumPedOrigen = FarmaUtility.getValueFieldArrayList(listaPedOrigen, i, COL_NUM_PED_ORIGEN).trim();
                pSecDetOrigen = FarmaUtility.getValueFieldArrayList(listaPedOrigen, i, COL_SEC_PED_ORIGEN).trim();
                pCodProdOrigen = FarmaUtility.getValueFieldArrayList(listaPedOrigen, i, COL_COD_PED_ORIGEN).trim();
                pCantUsoOrigen = FarmaUtility.getValueFieldArrayList(listaPedOrigen, i, COL_CANT_USO).trim();
                pValFracMinOrigen = FarmaUtility.getValueFieldArrayList(listaPedOrigen, i, COL_VAL_FRAC_MIN).trim();

                for (int j = 0; j < listaCanjesPosibles.size(); j++) {
                    codCampana = listaCanjesPosibles.get(j).toString().trim();
                    if (pCodCamp.trim().equalsIgnoreCase(codCampana.trim())) {
                        vEfectivo = false;
                        break;
                    }
                }

                if (vEfectivo) {
                    DBVentas.añadePedidosOrigenCanje(pDniCli, pCodCamp, pNumPed, pCodLocalOrigen, pNumPedOrigen,
                                                     pSecDetOrigen, pCodProdOrigen, pCantUsoOrigen, pValFracMinOrigen);
                }

            }

            //Se revierte los canjes posibles de la camapaña que se realizo en matriz
            //pero que no se hizo por falta de stock
            for (int j = 0; j < listaCanjesPosibles.size(); j++) {
                codCampana = listaCanjesPosibles.get(j).toString().trim();
                //Se revierten los canjes que eran posibles en matriz
                //pero que no se realizaron en el local
                //posiblemente por erro de fraccion, stock.
                DBVentas.revertirCanjeMatriz(pDniCli, codCampana, pNumPed);
            }

        } catch (SQLException e) {
            log.error("", e);
        }

    }

    /**
     * metodo encargado de registrar y/o asociar cliente a las campanias de acumulacion
     * @param
     * @author Javier Callo Quispe
     * @since 15.12.2008
     */
    private void asociarCampAcumulada(String codProd) {
        VariablesCampAcumulada.vCodProdFiltro = codProd;
        log.info("VariablesCampAcumulada.vCodProdFiltro:" + VariablesCampAcumulada.vCodProdFiltro);

        FarmaVariables.vAceptar = false;

        //lanzar dialogo las campañas por asociar
        DlgListaCampAcumulada dlgListaCampAcumulada = new DlgListaCampAcumulada(myParentFrame, "", true);
        dlgListaCampAcumulada.setVisible(true);
        //cargas las campañas de fidelizacion
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            log.debug("INVOCANDO CARGAR CAMPAÑAS DEL CLIENTES ..:" + VariablesFidelizacion.vNumTarjeta);
            UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta.trim());
            log.debug("FIN INVOCANDO CARGAR CAMPAÑAS DEL CLIENTES ..");

            /**mostranto el nombre del cliente **/
            lblCliente.setText(VariablesFidelizacion.vNomCliente); /*+" "
                                   +VariablesFidelizacion.vApePatCliente+" "
                                   +VariablesFidelizacion.vApeMatCliente);*/
            //VariablesFidelizacion.vApeMatCliente = Variables

            log.debug("imprmiendo todas las variables de fidelizacion");
            log.debug("VariablesFidelizacion.vApeMatCliente:" + VariablesFidelizacion.vApeMatCliente);
            log.debug("VariablesFidelizacion.vApePatCliente:" + VariablesFidelizacion.vApePatCliente);
            log.debug("VariablesFidelizacion.vCodCli:" + VariablesFidelizacion.vCodCli);
            log.debug("VariablesFidelizacion.vCodGrupoCia:" + VariablesFidelizacion.vCodGrupoCia);
            log.debug("VariablesFidelizacion.vDataCliente:" + VariablesFidelizacion.vDataCliente);
            log.debug("VariablesFidelizacion.vDireccion:" + VariablesFidelizacion.vDireccion);
            log.debug("VariablesFidelizacion.vDniCliente:" + VariablesFidelizacion.vDniCliente);
            log.debug("VariablesFidelizacion.vDocValidos:" + VariablesFidelizacion.vDocValidos);
            log.debug("VariablesFidelizacion.vEmail:" + VariablesFidelizacion.vEmail);
            log.debug("VariablesFidelizacion.vFecNacimiento:" + VariablesFidelizacion.vFecNacimiento);
            log.debug("VariablesFidelizacion.vIndAgregoDNI:" + VariablesFidelizacion.vIndAgregoDNI);
            log.debug("VariablesFidelizacion.vIndConexion:" + VariablesFidelizacion.vIndConexion);
            log.debug("VariablesFidelizacion.vIndEstado:" + VariablesFidelizacion.vIndEstado);
            log.debug("VariablesFidelizacion.vIndExisteCliente:" + VariablesFidelizacion.vIndExisteCliente);
            log.debug("VariablesFidelizacion.vListCampañasFidelizacion:" +
                      VariablesFidelizacion.vListCampañasFidelizacion);
            log.debug("VariablesFidelizacion.vNomCliente:" + VariablesFidelizacion.vNomCliente);
            log.debug("VariablesFidelizacion.vNomClienteImpr:" + VariablesFidelizacion.vNomClienteImpr);
            log.debug("VariablesFidelizacion.vNumTarjeta:" + VariablesFidelizacion.vNumTarjeta);
            log.debug("VariablesFidelizacion.vSexo:" + VariablesFidelizacion.vSexo);
            log.debug("VariablesFidelizacion.vSexoExists:" + VariablesFidelizacion.vSexoExists);
            log.debug("VariablesFidelizacion.vTelefono:" + VariablesFidelizacion.vTelefono);
            log.debug("fin de imprmir todas las variables de fidelizacion");

        }


    }

    /**
     * obtener todas las campañas de fidelizacion automaticas usados en el pedido
     *
     * */
    private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            listaCampLimitUsadosMatriz =
                    DBCaja.getListaCampUsadosLocalXCliente(dniCliente); //DBCaja.getListaCampUsadosMatrizXCliente(dniCliente); // JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
            if (listaCampLimitUsadosMatriz.size() > 0) {
                listaCampLimitUsadosMatriz = (ArrayList)listaCampLimitUsadosMatriz.get(0);
            }
            log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> " + listaCampLimitUsadosMatriz);
        } catch (Exception e) {
            log.debug("error al obtener las campañas limitadas ya usados por cliente en MATRIZ : " + e.getMessage());
        }
        return listaCampLimitUsadosMatriz;
    }

    /**
     * metodo nuevo de calculo y/o aplicacion de descuentos de acuerdo
     * a las campañas o cupones usados en el pedido.
     * @author Javier Callo Quispe
     * @since   05/03/2009
     * **/
    private boolean calculoDctosPedidoXCupones() {
        //El ahorro acumulado del pedido se coloca en 0
        //para reiniciar todo el calculo.


        VariablesFidelizacion.vAhorroDNI_Pedido = 0;

        log.debug("JCALLO: nuevo metodo de calculo de descuento");
        long timeIni = System.currentTimeMillis();

        List listaCodProds = new ArrayList(); //listaTemporal para tener el listado de codigo de productos
        Map mapaAux;
        String codProdAux = "";
        String codCampAux = "";
        //dubilluz 21.06.2011
        double totalProducto = 0.0;
        log.info("LISTA PROD:" + VariablesVentas.vArrayList_ResumenPedido);
        ///////////////////////////////////////////////////////////////////
        VariablesVentas.vListProdExcluyeAcumAhorro = new ArrayList();
        VariablesVentas.vListProdExcluyeAcumAhorro.clear();
        ///////////////////////////////////////////////////////////////////
        //Limpiar las marcas de prod cupon
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {

            /**agregar al arreglo de cod_productos*/
            mapaAux = new HashMap();
            codProdAux =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 0).toString();
            // precio de venta total x producto.
            // dubilluz 21.06.2011
            totalProducto =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 3).toString()) *
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 4).toString());
            ;

            // fin dubilluz 21.06.2011
            mapaAux.put("COD_PROD", codProdAux);
            mapaAux.put("TOTAL_PROD", totalProducto + "");
            listaCodProds.add(mapaAux);
            /**fin de agregar al arreglo de cod_productos**/

            double auxPrecio =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i, 3));
            double auxCantidad =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i, 4));

            //JCHAVEZ 29102009 inicio
            String auxPrecAnt;
            try {
                if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                    VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();
                }
            } catch (SQLException ex) {
                log.error("", ex);
            }
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                auxPrecAnt = FarmaUtility.formatNumber(auxPrecio * auxCantidad, 3);
            } else {
                auxPrecAnt = FarmaUtility.formatNumber(auxPrecio * auxCantidad);
            }
            //JCHAVEZ 29102009 fin
            double porcIgv =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i, 11));
            double precioTotal = auxPrecio * auxCantidad;
            double totalIgv = precioTotal - (precioTotal / (1 + porcIgv / 100));
            String vTotalIgv = FarmaUtility.formatNumber(totalIgv);
            //aqui donde cambian el precio del producto
            //version anterior era a 2 decimales
            //String cPrecioFinal = FarmaUtility.formatNumber(auxPrecio);
            //String cPrecioVta = FarmaUtility.formatNumber(auxPrecio);
            //version actual a 3 decimales
            String cPrecioFinal = FarmaUtility.formatNumber(auxPrecio, 3);
            String cPrecioVta = FarmaUtility.formatNumber(auxPrecio, 3);


            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(12, vTotalIgv);
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(COL_RES_CUPON, "");
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(5, ""); //columna de porcentaje descuento
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(3, cPrecioVta);
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(6, cPrecioFinal);
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7, auxPrecAnt);

            /*tblProductos.setValueAt(cPrecioVta, i, 3);//Precio Vta
	        tblProductos.setValueAt(" ", i, COL_RES_DSCTO);//Total Precio Vta
	        tblProductos.setValueAt(cPrecioFinal, i, 6);//Total Precio Vta
	        tblProductos.setValueAt(auxPrecAnt, i, 7);//Total Precio Vta*/

        }


        /**********    	
    	 * recorriendo todos los productos y calculando el descuento que es aplicable a por cada campaña.
         * * ************/

        log.debug("listaCodProds:" + listaCodProds);


        VariablesVentas.vListDctoAplicados = new ArrayList(); //el detalle de los dscto Aplicados

        List prodsCampanias = new ArrayList();
        if (listaCodProds.size() > 0 && VariablesVentas.vArrayList_Cupones.size() > 0) {
            prodsCampanias =
                    UtilityVentas.prodsCampaniasAplicables(listaCodProds, VariablesVentas.vArrayList_Cupones, VariablesVentas.vArrayList_ResumenPedido);
        }
        log.debug("prodsCampanias:" + prodsCampanias);
        //INICIALIZANDO TODAS LAS CAMPANIAS APLICABLES A LOS PRODUCTOS
        //para conservar el acumulado de ahorros totales
        List listaCampAhorro = new ArrayList(); //lista de campañas descuento por producto
        Map mapaTemp;
        String codCampTemp = "";

        List prodsCampaniasNUEVA = new ArrayList();
        for (int u = 0; u < prodsCampanias.size(); u++) {
            codCampTemp = ((Map)prodsCampanias.get(u)).get("COD_CAMP_CUPON").toString();
            // dubilluz 01.06.2012
            if (UtilityFidelizacion.getPermiteCampanaTarj(codCampTemp, VariablesFidelizacion.vDniCliente,
                                                          VariablesFidelizacion.vNumTarjeta)) {
                boolean existe = false;
                for (int p = 0; p < listaCampAhorro.size(); p++) {
                    if (((Map)listaCampAhorro.get(p)).get("COD_CAMP_CUPON").toString().equalsIgnoreCase(codCampTemp)) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    mapaTemp = new HashMap();

                    mapaTemp.put("COD_CAMP_CUPON", codCampTemp);
                    mapaTemp.put("AHORRO_ACUM", "0.0");
                    //mapaTemp.put("UNID_ACUM", "0.0");
                    listaCampAhorro.add(mapaTemp);
                }
                prodsCampaniasNUEVA.add((Map)prodsCampanias.get(u));
            } else {
                log.debug("la campana ingresada NO ES VALIDA para la tarjeta " + codCampTemp);
            }

        }

        log.info("prodsCampanias>>>" + prodsCampanias.size());
        prodsCampanias = prodsCampaniasNUEVA;
        log.info("prodsCampanias>>>" + prodsCampanias.size());
        //// dubilluz 05.06.2012
        log.debug("listaCampAhorro:" + listaCampAhorro);
        /*calculando los descuentos por cada productos para todas las cammpanias**/
        //variables auxiliares usados para calcular
        mapaAux = new HashMap();
        Map mapaAux2 = new HashMap();
        String codProdAux2 = "";
        String codCampAux2 = "";
        //


        double mayorAhorro = 0.0;
        String campMayorAhorro = "";
        double precioVtaMayorAhorro = 0.0;
        double totalVtaMayorAhorro = 0.0;
        double dctoVtaMayorAhorro = 0.0;
        double valorCuponMayorAhorro = 0.0;
        int cantPedMayorAhorro = 0;
        Map dctosAplicado = new HashMap();

        double ahorro = 0.0;
        double ahorroTotal = 0.0;

        double cantUnidAcumulado = 0.0; //contador de unidades aplicados a una camapania    	
        double ahorroAcumulado = 0.0; //contador monto ahorro acumulado a una campania

        int cantAplicable = 0, cantAplicableAux = 0;


        int cantPed = 0; // cantidad del producto dentro de resumen pedido
        int fraccionPëd = 0; // cantidad del producto dentro de resumen pedido
        double cantUnidPed = 0.0; // cantidad en unidades del pedido    	
        double precioVtaOrig = 0.0; //
        double totalXProd = 0.0;
        double precioVtaConDcto = 0.0; //

        double neoTotalXProd = 0.0; //nuevo total por producto
        double neoPrecioVtaXProd = 0.0; //nuevo precio venta por producto
        double neoDctoPorcentaje = 0.0;

        Map mapaCupon;

        double acumuladoDesctoPedido = 0;
        acumuladoDesctoPedido += VariablesFidelizacion.vAhorroDNI_x_Periodo;
        log.info("Descuento maximo utilizado: " + acumuladoDesctoPedido);

        // dubilluz 01.06.2010
        double valorCuponNuevo = 0.0;

        boolean indExcluyeProd_AHORRO_ACUM = false;


        log.debug("analizando productos con CUPONES TIPO porcentaje");
        log.info("ProdCamp.:" + prodsCampanias);
        for (int i = 0; i < prodsCampanias.size(); i++) {
            valorCuponNuevo = 0.0;
            mapaAux = (Map)prodsCampanias.get(i);
            codProdAux = (String)mapaAux.get("COD_PROD");
            codCampAux = (String)mapaAux.get("COD_CAMP_CUPON");
            ////////////////////////////
            if (((String)mapaAux.get("IND_EXCLUYE_ACUM_AHORRO")).trim().equalsIgnoreCase("S"))
                indExcluyeProd_AHORRO_ACUM = true;
            else
                indExcluyeProd_AHORRO_ACUM = false;
            ////////////////////////////
            log.debug("analizando el prod:" + codProdAux + ",cod_camp_cupon:" + codCampAux);

            //BUSCANDO EL INDICE DEL PRODUCTO EN ARREGLO AL CUAL APLICAR EL DSCTO;
            int indiceProducto = -1;
            ArrayList listaDatosProd = new ArrayList();
            for (int m = 0; m < VariablesVentas.vArrayList_ResumenPedido.size(); m++) {
                listaDatosProd = (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(m);
                if (((String)listaDatosProd.get(0)).equalsIgnoreCase(codProdAux)) { //si codigo del producto a buscar coincide con el que se aplicar dcto
                    indiceProducto = m;
                    break;
                }
            }
            log.debug("JCALLO:indiceProducto:" + indiceProducto);
            //hasta aqui se tiene el indice donde se encuentra el producto al cual se le aplicar el dcto

            //BUSCANDO EL INDICE DE LA CAMPANA CUPON del listado de campanas cupones
            int indiceCamp = -1;
            Map mapTemp2 = new HashMap();
            for (int m = 0; m < VariablesVentas.vArrayList_Cupones.size(); m++) {
                mapTemp2 = (Map)VariablesVentas.vArrayList_Cupones.get(m);
                if (((String)mapTemp2.get("COD_CAMP_CUPON")).equals(codCampAux)) { //ve si existe un valor en mapa si tiene cod_camp_cupon
                    indiceCamp = m;
                    break;
                }
            }
            log.debug("JCALLO:indiceCamp:" + indiceCamp);
            //hasta aqui tenemos el indice donde se encuentra la campana cupon a aplicar


            //el calculo de los descuentoS solo se aplica para cupones tipo  PORCENTAJE
            if (((Map)VariablesVentas.vArrayList_Cupones.get(indiceCamp)).get("TIP_CUPON").toString().equals(ConstantsVentas.TIPO_PORCENTAJE)) {

                int indiceProdCamp = -1;
                Map mapTemp3 = new HashMap();
                log.debug("listaCampAhorro:" + listaCampAhorro);
                for (int m = 0; m < listaCampAhorro.size(); m++) {
                    mapTemp3 = (Map)listaCampAhorro.get(m);
                    log.debug("mapTemp3:" + mapTemp3);
                    if (((String)mapTemp3.get("COD_CAMP_CUPON")).equals(codCampAux)) { //ve si existe un valor en mapa si tiene cod_camp_cupon
                        indiceProdCamp = m;
                        break;
                    }
                }
                log.debug("JCALLO:indiceProdCamp:" + indiceProdCamp);
                //hasta aqui se tiene el indice de de los datos de  montoAhorro acumulado por campana a aplicar
                //obteniendo datos principales del producto del resumen pedido
                cantPed =
                        Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(4));
                fraccionPëd =
                        Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(COL_RES_VAL_FRAC));
                precioVtaOrig =
                        FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(3)); //    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,indiceProducto,3));//precio venta
                cantUnidPed = (cantPed * 1.00) / fraccionPëd; //cantidad en unidades

                totalXProd =
                        FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(7));

                //obtiendo el mapa del cupon a aplicar
                mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(indiceCamp); //mapa del cupon
                Map mapaCampProd = (Map)listaCampAhorro.get(indiceProdCamp); //mapa camp ahorro


                /**APLICANDO EL DSCTO AL PRODUCTO CON ESCA CAMPANIA**/
                //obtieniendo el acumulado de unidades y acumulado de ahorros que se tiene
                ahorroAcumulado = Double.parseDouble(mapaCampProd.get("AHORRO_ACUM").toString());
                //cantUnidAcumulado = Double.parseDouble(mapaCampProd.get("UNID_ACUM").toString());


                //CALCULANDO EL DCTO AL PRODUCTO CON LA CAMPANA
                double unidMinUso = Double.parseDouble(mapaCupon.get("UNID_MIN_USO").toString());
                double montMinUso = Double.parseDouble(mapaCupon.get("MONT_MIN_USO").toString());
                //unidades aplicables de la campania
                //double unidMaximaAplicable = Double.parseDouble(mapaCupon.get("UNID_MAX_PROD").toString());
                //Obtiene el maximo de unidades a la compra del producto
                //DUBILLUZ 28.05.2009
                double unidMaximaAplicable = UtilityFidelizacion.getMaxUnidDctoProdCampana(codCampAux, codProdAux);
                if (unidMaximaAplicable == -1) {
                    unidMaximaAplicable = Double.parseDouble(mapaCupon.get("UNID_MAX_PROD").toString());
                }

                //Obtiene el porcentaje descuento personalizado de producto
                //DUBILLUZ 01.06.2010
                //valorCuponNuevo = Double.parseDouble(mapaCupon.get("VALOR_CUPON").toString());
                // Descomentado porque ya se acuerdan que SI PIDIERON 
                // SE PARAMETRIZARA con 
                // Esto se comentara porque NO RECUERDAN CUANDO PIDIERON ESTO.
                valorCuponNuevo =
                        UtilityFidelizacion.getDescuentoPersonalizadoProdCampana(codCampAux,
                                                                                 codProdAux);
                if (valorCuponNuevo == -1) {
                    valorCuponNuevo =
                            Double.parseDouble(mapaCupon.get("VALOR_CUPON").toString());
                }
                

                double ahorroAplicable =
                    Double.parseDouble(mapaCupon.get("MONTO_MAX_DESCT").toString()) - ahorroAcumulado;
                if (cantUnidPed > unidMaximaAplicable) {
                    cantAplicable = Math.round((float)UtilityVentas.Truncar(unidMaximaAplicable * fraccionPëd, 0));
                } else {
                    cantAplicable = Math.round((float)(cantUnidPed * fraccionPëd));
                }
                log.debug("unidMaximaAplicable:" + unidMaximaAplicable + ",cantAplicable:" + cantAplicable +
                          ", cantPed:" + cantPed);

                //precioVtaConDcto = ( precioVtaOrig * (( 100.0-Double.parseDouble( mapaCupon.get("VALOR_CUPON").toString() ) )/100.0) );
                //Cambiado para usar la variable
                precioVtaConDcto = (precioVtaOrig * ((100.0 - valorCuponNuevo) / 100.0));
                log.debug("precioVtaOriginal:" + precioVtaOrig);
                log.debug("VALOR_CUPON:" + mapaCupon.get("VALOR_CUPON").toString());
                log.debug("valorCuponNuevo:" + valorCuponNuevo);
                log.debug("precioVtaConDcto:" + precioVtaConDcto);
                //SI LA CAMPANIA PERMITE VENDER POR DEBAJO DEL COSTO PROMEDIO
                String sPrecioFinal = "";
                if (mapaCupon.get("IND_VAL_COSTO_PROM").toString().equals(FarmaConstants.INDICADOR_N)) {
                    //VERIFICANDO SI ESTA EN N:NO PERMITIR VENDER POR DEBAJO DEL COSTO PROMEDIO
                    try {
                        sPrecioFinal =
                                DBVentas.getPrecioFinalCampania(codProdAux, codCampAux, precioVtaConDcto, precioVtaOrig,
                                                                fraccionPëd).trim();

                        if (Double.parseDouble(sPrecioFinal) > precioVtaConDcto) {
                            precioVtaConDcto = Double.parseDouble(sPrecioFinal);
                        }
                    } catch (Exception e) {
                        log.debug("Exception e:" + e);
                    }
                }
                //fin de verificacion de venta por debajo del costo promedio.

                //calculando el nuevo total
                neoTotalXProd = (precioVtaConDcto * cantAplicable) + (precioVtaOrig * (cantPed - cantAplicable));
                ahorro = (precioVtaOrig - precioVtaConDcto) * cantAplicable;
                log.debug("ahorro:" + ahorro + ",ahorroAplicable:" + ahorroAplicable);
                if (ahorro > ahorroAplicable) { //se volvera a calcular si el ahorro es superior al aplicable
                    //int cantUnidAplicables = Math.round( (float)( ( ( totalXProd - ahorroAplicable ) / precioVtaConDcto ) * fraccionPëd ));

                    precioVtaConDcto = precioVtaOrig - (ahorroAplicable) / cantAplicable;
                    int cantAplicableAux2;
                    cantAplicableAux2 = cantAplicable;

                    cantAplicable =
                            Math.round((float)UtilityVentas.Truncar((ahorroAplicable / (precioVtaOrig - precioVtaConDcto)),
                                                                    0));

                    if (cantAplicableAux2 > cantAplicable) {
                        cantAplicable = cantAplicableAux2;
                        log.info("cantidad cambiada");
                    }

                    neoTotalXProd = (precioVtaConDcto * cantAplicable) + (precioVtaOrig * (cantPed - cantAplicable));
                    log.debug("precioVtaConDcto:" + precioVtaConDcto);
                    log.debug("precioVtaOrig:" + precioVtaOrig);
                    log.debug("cantAplicable:" + cantAplicable);
                    ahorro = (precioVtaOrig - precioVtaConDcto) * cantAplicable;
                    log.debug("ahorro:" + ahorro);
                }
                log.debug("ahorro 2:" + ahorro);
                /*
                                //--INICIO de verificar maximo descuento por dia del DNI
                                // DUBILLUZ 27.05.2009


                                if( (acumuladoDesctoPedido + ahorro) > VariablesFidelizacion.vMaximoAhorroDNIxPeriodo ){

                                    //ahorro = (precioVtaOrig - precioVtaConDcto)*cantAplicable;
                                    log.info("precioVtaOrigl "+precioVtaOrig);
                                    log.info("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo "+VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                                    log.info("acumuladoDesctoPedido "+acumuladoDesctoPedido);
                                    log.info("cantAplicable "+cantAplicable);
                                    precioVtaConDcto = precioVtaOrig - (VariablesFidelizacion.vMaximoAhorroDNIxPeriodo-acumuladoDesctoPedido)/cantAplicable;
                                    log.info("**precioVtaConDcto "+precioVtaConDcto);

                                    log.info("**ahorro "+ahorro);
                                    log.info("**ahorroAplicable "+ahorroAplicable);
                                    log.info("**precioVtaOrig "+precioVtaOrig);
                                    log.info("**precioVtaConDcto "+precioVtaConDcto);
                                    cantAplicableAux=cantAplicable;
                                    cantAplicable = Math.round( (float) UtilityVentas.Truncar( ( (VariablesFidelizacion.vMaximoAhorroDNIxPeriodo-acumuladoDesctoPedido)/(precioVtaOrig-precioVtaConDcto) ) , 0)  );
                                    if(cantAplicableAux>cantAplicable)
                                        cantAplicable = cantAplicableAux;


                                    log.info("**cantAplicable "+cantAplicable);
                                    log.info("-----");
                                    log.info("**precioVtaConDcto "+precioVtaConDcto);
                                    log.info("**precioVtaOrig "+precioVtaOrig);
                                    log.info("**cantPed "+cantPed);
                                    neoTotalXProd = (precioVtaConDcto * cantAplicable) + (precioVtaOrig * (cantPed - cantAplicable) );
                                    log.info("**neoTotalXProd "+neoTotalXProd);
                                    ahorro = (precioVtaOrig - precioVtaConDcto)*cantAplicable;
                                    log.info("-----");
                                    log.info("**precioVtaOrig "+precioVtaOrig);
                                    log.info("**precioVtaConDcto "+precioVtaConDcto);
                                    log.info("**cantAplicable "+cantAplicable);
                                }
                                acumuladoDesctoPedido += ahorro;
                                log.info("Descuento Parcial "+ahorro);
                                log.info("Descuento acumulado "+acumuladoDesctoPedido);
                                // Fin de Validacion de Maximo -- DUBILLUZ

                                 * */

                /** hasta aqui se tiene cantidad al cual se va aplicar el dcto
				 *  ahorro en monto
				 *  nuevo total por producto
				 * **/

                neoPrecioVtaXProd = UtilityVentas.Truncar(neoTotalXProd / cantPed, 3);
                //por si deseara saber la diferencia
                double diferencia = neoTotalXProd - UtilityVentas.Truncar(neoPrecioVtaXProd * cantPed, 2);
                neoTotalXProd = UtilityVentas.Truncar(neoPrecioVtaXProd * cantPed, 2);
                neoDctoPorcentaje =
                        UtilityVentas.Truncar(((precioVtaOrig - neoPrecioVtaXProd) / precioVtaOrig) * 100.0, 2);


                //                neoPrecioVtaXProd =
                //                        UtilityVentas.Redondear(neoTotalXProd / cantPed, 3);
                //                //por si deseara saber la diferencia
                //                double diferencia =
                //                    neoTotalXProd - UtilityVentas.Redondear(neoPrecioVtaXProd *
                //                                                          cantPed, 2);
                //                neoTotalXProd =
                //                        UtilityVentas.Redondear(neoPrecioVtaXProd * cantPed, 2);
                //                neoDctoPorcentaje =
                //                        UtilityVentas.Redondear(((precioVtaOrig - neoPrecioVtaXProd) /
                //                                               precioVtaOrig) * 100.0, 2);
                //ver si el ahorro calculado es mayor al que se tenia anteriormente
                if (ahorro > mayorAhorro && cantUnidPed >= unidMinUso)
                // el mont minimo de uso debe ser de todos los productos USO de la campana
                // dubilluz 21.06.2011
                //&&totalXProd >= montMinUso)
                {
                    mayorAhorro = ahorro;
                    campMayorAhorro = codCampAux;
                    precioVtaMayorAhorro = neoPrecioVtaXProd;
                    totalVtaMayorAhorro = neoTotalXProd;
                    dctoVtaMayorAhorro = neoDctoPorcentaje;
                    cantPedMayorAhorro = cantAplicable;

                    //valorCuponMayorAhorro = Double.parseDouble( mapaCupon.get("VALOR_CUPON").toString() );
                    //dubilluz 01.06.2010
                    valorCuponMayorAhorro = valorCuponNuevo;
                }
                //fin de ver el maximo ahorro

            } //fin calculo de ahorro para cupones campana tipo PROCENTAJE


            /**aqui verificar si el producto y al campana a analizar es el ultimo**/
            boolean flagAplicarMayorDcto = false;
            if ((i + 1) == prodsCampanias.size()) { //si es el ultimo calcular aplicar el mayor dscto al producto
                flagAplicarMayorDcto = true;

            } else { //quiere decir que no es el ultimo prod y campana a analizar
                /**verificar si el cod_prod siguiente es diferente al que se tiene*/
                mapaAux2 = (Map)prodsCampanias.get(i + 1);
                if (!mapaAux2.get("COD_PROD").toString().equals(codProdAux)) { //si el producto siguiente es diferente tonces aplicar mayor dcto
                    flagAplicarMayorDcto = true;
                }
            }

            log.debug("flagAplicarMayorDcto:" + flagAplicarMayorDcto + ",mayorAhorro:" + mayorAhorro);
            /**verificar si se aplica el mayor dscto**/
            if (flagAplicarMayorDcto && mayorAhorro > 0.0) { //se agrego este flag para no repetir codigo


                //--INICIO de verificar maximo descuento por dia del DNI
                // DUBILLUZ 27.05.2009
                if (((Map)VariablesVentas.vArrayList_Cupones.get(indiceCamp)).get("IND_FID").toString().trim().equals(FarmaConstants.INDICADOR_S) |
                    VariablesFidelizacion.vDniCliente.trim().length() > 0) {
                    if (!indExcluyeProd_AHORRO_ACUM) {
                        if ((acumuladoDesctoPedido + mayorAhorro) > VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) {
                            log.info("**mayorAhorro old " + mayorAhorro);

                            //ahorro = (precioVtaOrig - precioVtaConDcto)*cantAplicable;
                            log.info("precioVtaOrigl " + precioVtaOrig);
                            log.info("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo " +
                                     VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                            log.info("acumuladoDesctoPedido " + acumuladoDesctoPedido);
                            log.info("cantAplicable " + cantAplicable);
                            mayorAhorro = VariablesFidelizacion.vMaximoAhorroDNIxPeriodo - acumuladoDesctoPedido;

                            //ERIOS 2.4.5 Evalua cantidad aplicable
                            if (cantAplicable == 0)
                                cantAplicable = 1;

                            precioVtaConDcto =
                                    precioVtaOrig - (VariablesFidelizacion.vMaximoAhorroDNIxPeriodo - acumuladoDesctoPedido) /
                                    cantAplicable;

                            log.info("**precioVtaConDcto " + precioVtaConDcto);

                            log.info("**ahorro " + ahorro);
                            log.info("**precioVtaOrig " + precioVtaOrig);
                            log.info("**precioVtaConDcto " + precioVtaConDcto);
                            cantAplicableAux = cantAplicable;
                            cantAplicable =
                                    Math.round((float)UtilityVentas.Truncar(((VariablesFidelizacion.vMaximoAhorroDNIxPeriodo -
                                                                              acumuladoDesctoPedido) /
                                                                             (precioVtaOrig - precioVtaConDcto)), 0));
                            if (cantAplicableAux > cantAplicable) {
                                cantAplicable = cantAplicableAux;
                                log.info("cantidad cambiada");
                            }

                            log.info("**cantAplicable " + cantAplicable);
                            log.info("-----");
                            log.info("**precioVtaConDcto " + precioVtaConDcto);
                            log.info("**precioVtaOrig " + precioVtaOrig);
                            log.info("**cantPed " + cantPed);
                            neoTotalXProd =
                                    (precioVtaConDcto * cantAplicable) + (precioVtaOrig * (cantPed - cantAplicable));
                            log.info("**neoTotalXProd " + neoTotalXProd);
                            ahorro = (precioVtaOrig - precioVtaConDcto) * cantAplicable;
                            log.info("-----");
                            log.info("**precioVtaOrig " + precioVtaOrig);
                            log.info("**precioVtaConDcto " + precioVtaConDcto);
                            log.info("**cantAplicable " + cantAplicable);

                            neoPrecioVtaXProd = UtilityVentas.Truncar(neoTotalXProd / cantPed, 3);
                            //por si deseara saber la diferencia
                            double diferencia = neoTotalXProd - UtilityVentas.Truncar(neoPrecioVtaXProd * cantPed, 2);
                            neoTotalXProd = UtilityVentas.Truncar(neoPrecioVtaXProd * cantPed, 2);

                            neoDctoPorcentaje =
                                    UtilityVentas.Truncar(((precioVtaOrig - neoPrecioVtaXProd) / precioVtaOrig) *
                                                          100.0, 2);


                            //mayorAhorro          = ahorro;
                            campMayorAhorro = codCampAux;
                            precioVtaMayorAhorro = neoPrecioVtaXProd;
                            totalVtaMayorAhorro = neoTotalXProd;
                            dctoVtaMayorAhorro = neoDctoPorcentaje;
                            cantPedMayorAhorro = cantAplicable;


                            log.info("**mayorAhorro new " + mayorAhorro);


                        }
                    }

                    acumuladoDesctoPedido += mayorAhorro;
                    /*
                    if(!indExcluyeProd_AHORRO_ACUM)
                        VariablesFidelizacion.vAhorroDNI_Pedido += mayorAhorro;
                    */
                    log.info("Descuento Parcial " + mayorAhorro);
                    log.info("Descuento acumulado " + acumuladoDesctoPedido);
                    // Fin de Validacion de Maximo -- DUBILLUZ
                }


                log.debug("aplicando el dcto al producto : " +
                          ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(0).toString());
                //duda si hacer esto esta bien la parecer al hacer set solo estaria referenciando direcion de memoria a la
                //variable señalada ahi podria haber problemas, es solo una suposicion. JCALLO
                if(dctoVtaMayorAhorro>0)
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(COL_RES_CUPON,
                                                                                              campMayorAhorro);
                else
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(COL_RES_CUPON,
                                                                                                  "");
                
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(COL_RES_DSCTO,
                                                                                              "" + dctoVtaMayorAhorro);
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(6,
                                                                                              FarmaUtility.formatNumber(precioVtaMayorAhorro,
                                                                                                                        3));
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(7,
                                                                                              FarmaUtility.formatNumber(totalVtaMayorAhorro,
                                                                                                                        2));
                // dubilluz 07.11.2012
                if (indExcluyeProd_AHORRO_ACUM) {
                    VariablesVentas.vListProdExcluyeAcumAhorro.add(((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(0).toString());
                }

                //
                //guardado el detalle de los dcto aplicados por producto
                Map mapaDctoProd = new HashMap();
                mapaDctoProd.put("COD_PROD",
                                 ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(0).toString());
                mapaDctoProd.put("COD_CAMP_CUPON", campMayorAhorro);
                mapaDctoProd.put("VALOR_CUPON", "" + valorCuponMayorAhorro);
                mapaDctoProd.put("AHORRO", "" + mayorAhorro);
                mapaDctoProd.put("DCTO_AHORRO", "" + dctoVtaMayorAhorro);
                //JMIRANDA 30.10.09 AÑADE SEC DETALLE PEDIDO
                mapaDctoProd.put("SEC_PED_VTA_DET", "" + (indiceProducto + 1));
                log.debug("JM 30.10.09, SEC_PED_VTA_DET " + (indiceProducto + 1));
                if(dctoVtaMayorAhorro>0)
                VariablesVentas.vListDctoAplicados.add(mapaDctoProd);
                //calculando el nuevo igv por producto con el dcto
                //obteniendo el procentaje de igv a aplicar
                double valorIgv =
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(11));
                //
                double totalIgvProd = totalVtaMayorAhorro - totalVtaMayorAhorro / ((100.0 + valorIgv) / 100.0);
                log.debug("TOTALX_PRODUCTO:" + totalVtaMayorAhorro + ",valorIgv:" + valorIgv + ",totalIgvProd:" +
                          totalIgvProd);
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(12,
                                                                                              FarmaUtility.formatNumber(totalIgvProd,
                                                                                                                        3));


                /**actualizando la cantidad unidad como monto ahorro acumulado por campania aplicada*/
                //buscado el mapa en el listado de campanas, la campania que mayor dcto le da al producto
                int indiceProdCampApli = -1;
                Map mapProdCampApli = new HashMap();
                for (int m = 0; m < listaCampAhorro.size(); m++) {
                    mapProdCampApli = (Map)listaCampAhorro.get(m);
                    log.debug("ver si :" + (String)mapProdCampApli.get("COD_CAMP_CUPON") + ":=:" + campMayorAhorro);
                    if (((String)mapProdCampApli.get("COD_CAMP_CUPON")).equals(campMayorAhorro)) { //ve si existe un valor en mapa si tiene cod_camp_cupon
                        indiceProdCampApli = m;
                        break;
                    }
                }
                //obtiendo la campania al cual se ve aumentar las unididas como el ahorro acumulado por campania
                mapProdCampApli = new HashMap();
                mapProdCampApli = (Map)listaCampAhorro.get(indiceProdCampApli);

                double acuAhorro = Double.parseDouble(mapProdCampApli.get("AHORRO_ACUM").toString());
                //double acuUnidad = Double.parseDouble(mapProdCampApli.get("UNID_ACUM").toString());

                acuAhorro += mayorAhorro;
                //acuUnidad   += cantPedMayorAhorro/fraccionPëd;//a la cant del pedido diviendo entre el valorFracion del producto para obtener la UNIDADES

                ////todo esto se podria solo reemplazar por un simple put
                //eliminando el mapa del listado de campanaDctos
                listaCampAhorro.remove(indiceProdCampApli);
                mapProdCampApli.remove("AHORRO_ACUM");
                //mapProdCampApli.remove("UNID_ACUM");
                log.info("acuAhorro:" + acuAhorro);
                mapProdCampApli.put("AHORRO_ACUM", "" + acuAhorro);
                //mapProdCampApli.put("UNID_ACUM", ""+acuUnidad);
                //agregando el campDcto
                listaCampAhorro.add(indiceProdCampApli, mapProdCampApli);

                //inicializando las variable de mayor ahorro por producto    			
                mayorAhorro = 0.0;
                campMayorAhorro = "";
                precioVtaMayorAhorro = 0.0;
                totalVtaMayorAhorro = 0.0;
                dctoVtaMayorAhorro = 0.0;
                cantPedMayorAhorro = 0;
                //dubilluz -01.06.2010
                valorCuponNuevo = 0.0;
            } //fin de aplicar el mejor descuento descuento

        } //fin de recorrer todas las productos campanias aplicables


        //ANALIZANDO TODOS LOS PRODUCTOS CON CAMPANIAS TIPO DE MONTO
        //no se completo la logica, ya que no se tenia bien definido el manejo de este tipo de cupones
        //hasta la fecha 11.03.2009.

        /* DESCOMENTAR sE va implementar la logica de cupones tipo MONTO
    	  log.debug("analizando productos con CUPONES TIPO monto");
    	for( int i = 0; i < prodsCampanias.size(); i++){
    		mapaAux    = (Map)prodsCampanias.get(i);
    		codProdAux = (String)mapaAux.get("COD_PROD");
    		codCampAux = (String)mapaAux.get("COD_CAMP_CUPON");
    		log.debug("JCALLO:analizando el prod:"+codProdAux+",cod_camp_cupon:"+codCampAux);
    		
    		//BUSCANDO EL INDICE DEL PRODUCTO EN EL ARREGLO DE RESUMEN PEDIDO AL CUAL APLICAR EL CUPON;
    		int indiceProducto = -1;
    		ArrayList listaDatosProd = new ArrayList();
    		for(int m=0;m<VariablesVentas.vArrayList_ResumenPedido.size();m++){
    			listaDatosProd = (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(m);
    			if ( ((String)listaDatosProd.get(0)).equalsIgnoreCase(codProdAux) ){//si codigo del producto a buscar coincide con el que se aplicar dcto
    				indiceProducto = m;
    				break;
    			}
    		}
    		log.debug("JCALLO:indiceProducto:"+indiceProducto);
    		//hasta aqui se tiene el indice donde se encuentra el producto al cual se le aplicar el dcto
    		
    		//BUSCANDO EL INDICE DE LA CAMPANA CUPON del listado de campanas cupones
    		int indiceCamp = -1;
    		Map mapTemp2 = new HashMap();
    		for(int m=0; m < VariablesVentas.vArrayList_Cupones.size() ; m++){
    			mapTemp2 = (Map)VariablesVentas.vArrayList_Cupones.get(m);
    			if ( ((String)mapTemp2.get("COD_CAMP_CUPON")).equals(codCampAux) ){//ve si existe un valor en mapa si tiene cod_camp_cupon
    				indiceCamp = m;
    				break;
    			}
    		}
    		log.debug("JCALLO:indiceCamp:"+indiceCamp);
    		//hasta aqui tenemos el indice donde se encuentra la campana cupon a aplicar
    		
    		//verificando los cupones de tipo MONTO
    		if( ((Map)VariablesVentas.vArrayList_Cupones.get(indiceCamp)).get("TIP_CUPON").toString()
    				.equals(ConstantsVentas.TIPO_MONTO) ) {
    			//AQUI DEBE IR COMO APLICAR LOS CUPONES TIPO DE MONTO POR PRODUCTO.
    		}
    	}
    	*/
        log.info("Ahorror Actual Total del Pedido " + VariablesFidelizacion.vAhorroDNI_Pedido);

        //JCHAVEZ 29102009 inicio

        try {
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();
            }
        } catch (SQLException ex) {
            log.error("", ex);
        }
        if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
            //JCHAVEZ 29102009 inicio Redondeando montos
            for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
                String codProd =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 0).toString();
                double precioUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 3));
                double precioVentaUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 6));
                double precioVtaTotal =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 7));
                double cantidad =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 4));


                log.debug("precioVtaTotal: " + precioVtaTotal);
                log.debug("precioVentaUnit: " + precioVentaUnit);
                try {
                    double precioVtaTotalRedondeado = DBVentas.getPrecioRedondeado(precioVtaTotal);
                    double precioVentaUnitRedondeado = precioVtaTotalRedondeado / cantidad;
                    double precioUnitRedondeado = DBVentas.getPrecioRedondeado(precioUnit);
                    log.debug("precioVtaTotalRedondeado: " + precioVtaTotalRedondeado);
                    log.debug("precioVentaUnitRedondeado: " + precioVentaUnitRedondeado);
                    double pAux2;
                    double pAux5;
                    pAux2 = UtilityVentas.Redondear(precioVentaUnitRedondeado, 2);
                    log.debug("pAux2: " + pAux2);
                    if (pAux2 < precioVentaUnitRedondeado) {
                        pAux5 = (pAux2 * Math.pow(10, 2) + 1) / 100;
                        log.debug("pAux5: " + pAux5);
                    } else {
                        pAux5 = pAux2;
                    }


                    String cprecioVtaTotalRedondeado = FarmaUtility.formatNumber(precioVtaTotalRedondeado, 3);
                    String cprecioVentaUnitRedondeado =
                        FarmaUtility.formatNumber(pAux5); /*precioVentaUnitRedondeado,3*/
                    String cprecioUnitRedondeado = FarmaUtility.formatNumber(precioUnitRedondeado, 3);
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(3, cprecioUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(6, cprecioVentaUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7, cprecioVtaTotalRedondeado);
                    log.debug("precioVtaTotalRedondeado: " + "" + cprecioVtaTotalRedondeado);
                    log.debug("precioVentaUnitRedondeado: " + cprecioVentaUnitRedondeado);
                    log.debug("precioUnitRedondeado: " + cprecioUnitRedondeado);

                } catch (SQLException ex) {
                    log.error("", ex);
                }

                log.debug("codProd: " + codProd);
                log.debug("precioUnit: " + precioUnit);
                log.debug("precioVentaUnit: " + precioVentaUnit);
                log.debug("precioVtaTotal: " + precioVtaTotal);
                log.debug("cantidad: " + cantidad);
            }

            //JCHAVEZ 29102009 fin
        }

        //04-DIC-13 TCT Begin Aplicacion de Precio  Fijo x Campaña
        //FarmaUtility.showMessage(this, "Antes de Aplicar Ultimo Descuento",null);
        log.debug("### TCT 001 VariablesVentas.vEsPedidoConvenio: " + VariablesVentas.vEsPedidoConvenio);
        log.debug("### TCT 001 VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF: " +
                  VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);
        // 10.- Begin if no convenios
        if (!VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
            for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
                String codProd =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 0).toString();
                double precioUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 3));
                double precioVentaUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 6));
                double precioVtaTotal =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 7));
                double cantidad =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i, 4));
                int fraccionPed =
                    Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(COL_RES_VAL_FRAC));

                // Lectura de Datos de Campaña con el Mejor Precio de Promocion (el mas bajo)
                Map mapCampPrec = new HashMap();
                try {
                    mapCampPrec = DBVentas.getDatosCampPrec(codProd);


                } catch (SQLException e) {

                    FarmaUtility.showMessage(this, "Ocurrio un error al obtener datos de la Campaña por Precio.\n" +
                            e.getMessage() +
                            "\n Inténtelo Nuevamente\n si persiste el error comuniquese con operador de sistemas.",
                            null);

                }
                log.debug("######## TCT 10: mapCampPrec:" + mapCampPrec);

                // Verificar si el Precio de Campaña x Precio es < al Precio ya Calculado => Calcular Nuevamente
                if (mapCampPrec.size() > 0) {
                    double vd_Val_Prec_Prom, vd_Val_Prec_Prom_frac;
                    String vs_Val_Prec_Prom = (String)mapCampPrec.get("VAL_PREC_PROM_ENT");
                    String vs_Cod_Camp_Prec = (String)mapCampPrec.get("COD_CAMP_CUPON");
                    log.debug("###### TCT 11 : vs_Val_Prec_Prom: " + vs_Val_Prec_Prom);

                    //ERIOS 03.01.2013 Logica de tratamiento
                    vd_Val_Prec_Prom = FarmaUtility.getDecimalNumber(vs_Val_Prec_Prom);
                    int enteros = (new Double(cantidad)).intValue() / fraccionPed;
                    int fracciones = (new Double(cantidad)).intValue() % fraccionPed;
                    vd_Val_Prec_Prom_frac = UtilityVentas.Redondear(vd_Val_Prec_Prom / fraccionPed, 2);
                    double totalVentaCamp = enteros * vd_Val_Prec_Prom + fracciones * vd_Val_Prec_Prom_frac;

                    //&&&&&&&&&&&&&&&& CAMBIAR DATOS DE LINEA DE PRODUCTO X CAMPAÑA DE PRECIO &&&&&&&&&&&&&&&&&&&&&&&&
                    if (precioVtaTotal > totalVentaCamp) {
                        double valAhorro = precioVtaTotal-totalVentaCamp;
                        dctoVtaMayorAhorro = UtilityVentas.Redondear((1 - totalVentaCamp / precioVtaTotal) * 100, 2);
                        precioVtaTotal = totalVentaCamp;

                        //ahorroAcumulado =  Double.parseDouble(mapaCampProd.get("AHORRO_ACUM").toString());
                        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(COL_RES_CUPON,
                                                                                         vs_Cod_Camp_Prec);
                        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(COL_RES_DSCTO,
                                                                                         "" + dctoVtaMayorAhorro);

                        double porcIgv =
                            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                              i, 11));

                        double totalIgv = precioVtaTotal - (precioVtaTotal / (1 + porcIgv / 100));
                        String vTotalIgv = FarmaUtility.formatNumber(totalIgv);


                        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(12, vTotalIgv);
                        
                        ////////////////////dubilluz 14.04.2015 //////////////////////////////////////////
                        Map mapAux = new HashMap();
                        String pCodigo  = "";
                        // 1- quitando el map de ahorro producto x camapana
                        for(int j=0;j<VariablesVentas.vListDctoAplicados.size();j++){
                            mapAux = (Map)VariablesVentas.vListDctoAplicados.get(j);
                            pCodigo = mapAux.get("COD_PROD").toString().trim();
                            if(pCodigo.trim().equalsIgnoreCase(codProd)){
                                VariablesVentas.vListDctoAplicados.remove(j);
                                break;
                            }
                        }
                        // 2- agrega el ahorro de prod campana
                        mapAux = new HashMap();
                        mapAux.put("COD_PROD",codProd);
                        mapAux.put("COD_CAMP_CUPON", vs_Cod_Camp_Prec);   
                        mapAux.put("VALOR_CUPON", "" + dctoVtaMayorAhorro);  
                        mapAux.put("AHORRO", "" + "" +valAhorro);   
                        mapAux.put("DCTO_AHORRO", "" +dctoVtaMayorAhorro); 
                        //JMIRANDA 30.10.09 AÑADE SEC DETALLE PEDIDO
                        mapAux.put("SEC_PED_VTA_DET", "" + (i + 1));
                        VariablesVentas.vListDctoAplicados.add(mapAux);
                        ////////////////////dubilluz 14.04.2015 //////////////////////////////////////////                        

                    }

                }

                log.debug("precioVtaTotal: " + precioVtaTotal);
                log.debug("precioVentaUnit: " + precioVentaUnit);
                try {
                    double precioVtaTotalRedondeado = DBVentas.getPrecioRedondeado(precioVtaTotal);
                    double precioVentaUnitRedondeado = precioVtaTotalRedondeado / cantidad;
                    double precioUnitRedondeado = DBVentas.getPrecioRedondeado(precioUnit);
                    log.debug("precioVtaTotalRedondeado: " + precioVtaTotalRedondeado);
                    log.debug("precioVentaUnitRedondeado: " + precioVentaUnitRedondeado);

                    double pAux2;
                    double pAux5;
                    pAux2 = UtilityVentas.Redondear(precioVentaUnitRedondeado, 2);
                    log.debug("pAux2: " + pAux2);
                    if (pAux2 < precioVentaUnitRedondeado) {
                        pAux5 = (pAux2 * Math.pow(10, 2) + 1) / 100;
                        log.debug("pAux5: " + pAux5);
                    } else {
                        pAux5 = pAux2;
                    }


                    String cprecioVtaTotalRedondeado = FarmaUtility.formatNumber(precioVtaTotalRedondeado, 3);
                    String cprecioVentaUnitRedondeado =
                        FarmaUtility.formatNumber(pAux5); /*precioVentaUnitRedondeado,3*/
                    String cprecioUnitRedondeado = FarmaUtility.formatNumber(precioUnitRedondeado, 3);

                    /*
                 * TCT Seteos Originales
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(3,
                                                                                 cprecioUnitRedondeado);
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(6,
                                                                                 cprecioVentaUnitRedondeado);
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7,
                                                                                 cprecioVtaTotalRedondeado);

                */

                    // Get Datos de Precio de Promocion si Existe


                    log.debug("#### 01.- TCT VariablesVentas.vArrayList_ResumenPedido: " +
                              VariablesVentas.vArrayList_ResumenPedido);

                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(3, cprecioUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(6,
                                                                                     cprecioVentaUnitRedondeado); // precio venta unit con dscto
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7,
                                                                                     cprecioVtaTotalRedondeado); // precio venta total

                    log.debug("#### 02.- TCT VariablesVentas.vListDctoAplicados: " +
                              VariablesVentas.vListDctoAplicados);
                    log.debug("#### 03.- TCT VariablesVentas.vArrayList_ResumenPedido: " +
                              VariablesVentas.vArrayList_ResumenPedido);
                    log.debug("#### 04.- TCT  listaCampAhorro: " + listaCampAhorro);

                    log.debug("precioVtaTotalRedondeado: " + "" + cprecioVtaTotalRedondeado);
                    log.debug("precioVentaUnitRedondeado: " + cprecioVentaUnitRedondeado);
                    log.debug("precioUnitRedondeado: " + cprecioUnitRedondeado);

                } catch (SQLException ex) {
                    log.error("", ex);
                }

                log.debug("codProd: " + codProd);
                log.debug("precioUnit: " + precioUnit);
                log.debug("precioVentaUnit: " + precioVentaUnit);
                log.debug("precioVtaTotal: " + precioVtaTotal);
                log.debug("cantidad: " + cantidad);
            }
        } // if no convenio convenio
        //04-DIC-13 End

        return true; //por ahora dejando asi nomas jeje

    } //fin del metodo de calculo y aplicacion de descuentos por CAMPANAS CUPONES

    /**
     * nuevo metodo encargado de resumen pedido
     * @author jcallo
     * **/
    private void neoOperaResumenPedido() {

        if (VariablesFidelizacion.vSIN_COMISION_X_DNI)
            lblDNI_SIN_COMISION.setVisible(true);
        else
            lblDNI_SIN_COMISION.setVisible(false);

        if (VariablesFidelizacion.V_NUM_CMP.trim().length() > 0) {
            lblMedico.setText("Receta de : " + VariablesFidelizacion.V_NUM_CMP + "-" + VariablesFidelizacion.V_NOMBRE +
                              " / " + VariablesFidelizacion.V_DESC_TIP_COLEGIO);
            lblMedico.setVisible(true);
        } else {
            lblMedico.setText("");
            lblMedico.setVisible(false);
        }

        //16-AGO-13 TCT Imprime Promociones Habilitadas
        log.debug("<<TCT 2>>Lista Promociones Habilitadas");
        log.debug("VariablesVentas.VariablesVentas.vArrayList_Promociones=>" + VariablesVentas.vArrayList_Promociones);

        //jcallo quitar las campañas que ya han terminado de ser usados por el cliente
        log.debug("quitando las campañas limitadas en numeros de usos del cliente");

        log.debug("VariablesVentas.vArrayList_CampLimitUsadosMatriz:" +
                  VariablesVentas.vArrayList_CampLimitUsadosMatriz);
        for (int i = 0; i < VariablesVentas.vArrayList_CampLimitUsadosMatriz.size(); i++) {
            String cod_camp_limit =
                VariablesVentas.vArrayList_CampLimitUsadosMatriz.get(i).toString().trim(); //por culpa de diego
            for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                String cod_camp_cupon =
                    ((Map)VariablesVentas.vArrayList_Cupones.get(j)).get("COD_CAMP_CUPON").toString();
                if (cod_camp_limit.equals(cod_camp_cupon)) {
                    log.debug("quitando cupon que ya no deberia de aplicar");
                    VariablesVentas.vArrayList_Cupones.remove(j);
                    break;
                }
            }
        }

        calculaTotalesPedido(); //dentro de esto esta el aplicar los dctos por campanias cupon


        log.debug("VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF: " +
                  VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);

        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
            lblCliente.setText(VariablesConvenioBTLMF.vNomCliente);
            VariablesVentas.vCod_Cli_Local = "";
            VariablesVentas.vNom_Cli_Ped = VariablesConvenioBTLMF.vNomCliente;
            VariablesVentas.vDir_Cli_Ped = "";
            VariablesVentas.vRuc_Cli_Ped = "";
            this.setTitle("Pedido por Convenio: OK " + VariablesConvenioBTLMF.vNomConvenio +
                          " /  IP : " + FarmaVariables.vIpPc);
            log.debug("------------------------" + this.getTitle());
            log.debug("VariablesConvenio.vTextoCliente : *****" + VariablesConvenioBTLMF.vNomCliente);

            lblLCredito_T.setText(VariablesConvenioBTLMF.vDatoLCredSaldConsumo);
            lblBeneficiario_T.setText(getMensajeComprobanteConvenio(VariablesConvenioBTLMF.vCodConvenio));

        } else {
            evaluaTitulo(); //titulo y datos dependiendo del tipo de pedido que se este haciendo
        }

        //refrescando los nuevos datos en tabla de resumen pedido
        tableModelResumenPedido.clearTable();
        tableModelResumenPedido.fireTableDataChanged();
        tblProductos.repaint();

        // cargamos los productos desde el ArrayList de Productos
        String prodVirtual = FarmaConstants.INDICADOR_N;
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
            tableModelResumenPedido.insertRow((ArrayList)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).clone());
            tableModelResumenPedido.fireTableDataChanged();
        }

        //JCHAVEZ 29102009 inicio Redondeando montos
        log.debug("VariablesVentas.vArrayList_Promociones: " + VariablesVentas.vArrayList_Promociones);
        try {
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();
            }
        } catch (SQLException ex) {
            log.error("", ex);
        }
        if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
            for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size(); i++) {
                double precioUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i, 3));
                double precioVentaUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i, 6));
                double precioVtaTotal =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i, 7));
                double cantidad =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i, 4));
                log.debug("precioVtaTotal: " + precioVtaTotal);
                log.debug("precioVentaUnit: " + precioVentaUnit);
                try {
                    double precioVtaTotalRedondeado = DBVentas.getPrecioRedondeado(precioVtaTotal);
                    double precioVentaUnitRedondeado = precioVtaTotalRedondeado / cantidad;
                    double precioUnitRedondeado = DBVentas.getPrecioRedondeado(precioUnit);
                    log.debug("precioVtaTotalRedondeado: " + precioVtaTotalRedondeado);
                    log.debug("precioVentaUnitRedondeado: " + precioVentaUnitRedondeado);
                    double pAux2;
                    double pAux5;
                    pAux2 = UtilityVentas.Redondear(precioVentaUnitRedondeado, 2);
                    log.debug("pAux2: " + pAux2);
                    if (pAux2 < precioVentaUnitRedondeado) {
                        pAux5 = (pAux2 * Math.pow(10, 2) + 1) / 100;
                        log.debug("pAux5: " + pAux5);
                    } else {
                        pAux5 = pAux2;
                    }


                    String cprecioVtaTotalRedondeado = FarmaUtility.formatNumber(precioVtaTotalRedondeado, 3);
                    String cprecioVentaUnitRedondeado = FarmaUtility.formatNumber(pAux5);
                    String cprecioUnitRedondeado = FarmaUtility.formatNumber(precioUnitRedondeado, 3);
                    ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).set(3, cprecioUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).set(6, cprecioVentaUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).set(7, cprecioVtaTotalRedondeado);
                    log.debug("precioVtaTotalRedondeado: " + "" + cprecioVtaTotalRedondeado);
                    log.debug("precioVentaUnitRedondeado: " + cprecioVentaUnitRedondeado);
                    log.debug("precioUnitRedondeado: " + cprecioUnitRedondeado);

                } catch (SQLException ex) {
                    log.error("", ex);
                }


                log.debug("precioUnit: " + precioUnit);
                log.debug("precioVentaUnit: " + precioVentaUnit);
                log.debug("precioVtaTotal: " + precioVtaTotal);
                log.debug("cantidad: " + cantidad);
            }
        }
        //JCHAVEZ 29102009 fin

        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size(); i++) {
            tableModelResumenPedido.insertRow((ArrayList)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).clone());
            tableModelResumenPedido.fireTableDataChanged();
        }
        tblProductos.repaint();
        FarmaUtility.setearPrimerRegistro(tblProductos, null, 0);
        //Seteando el valor de Si da o no Descuento al cliente.
        //daubilluz 26.05.2009
        AuxiliarFidelizacion.setMensajeDNIFidelizado(lblDNI_Anul, "R", txtDescProdOculto, this);

        evaluaFormaPagoFidelizado();

        pintaFilasProdExcluyeAhorroAcum();

    }

    private void pintaFilasProdExcluyeAhorroAcum() {
        ArrayList aux = new ArrayList();
        for (int i = 0; i < tableModelResumenPedido.data.size(); i++) {
            for (int j = 0; j < VariablesVentas.vListProdExcluyeAcumAhorro.size(); j++) {
                String pCod = VariablesVentas.vListProdExcluyeAcumAhorro.get(j).toString();
                if ((getValueColumna(tableModelResumenPedido.data, i, 0)).trim().equalsIgnoreCase(pCod)) {
                    aux.add(String.valueOf(i));
                }
            }
        }

        FarmaUtility.initSimpleListCleanColumns(tblProductos, tableModelResumenPedido,
                                                ConstantsVentas.columnsListaResumenPedido, aux, Color.white, Color.red,
                                                false);
    }

    public String getValueColumna(ArrayList tbl, int Fila, int Columna) {
        String pValor = FarmaUtility.getValueFieldArrayList(tbl, Fila, Columna);
        return pValor;
    }

    /**
     * FALTA TERMINAR IMPLEMENTAR PARA EL CASO DE CUPONES TIPO MONTO
     * Valida monto de cupones del pedido
     * verifica el uso de cupones de tipo MONTO, el valor de los ingresados no se mayor al monto del pedido
     * @author JCALLO
     * @since  11.03.2009
     * @param  pNetoPedido
     * @return boolean
     */
    private boolean validaCampsMontoNetoPedido(String pNetoPedido) {
        boolean flag = false;
        //AQUI IMPLEMENTAR EL CODIGO DE VALIDACION DE LA SUMA DE CUPONES TIPO MONTO
        //APLICADOS COMPARADOS CON EL TOTAL NETO DEL PEDIDO
        //POR AHORA POR DEFECTO SE PUSO QUE DEVUELVA SIEMPRE TRUE;
        return true;
    }

    /**
     * Se carga cupones emitidos por el cliente fidelizado.
     * @author JCORTEZ
     * @since  05.08.09
     * */
    private void cargarCupones() {
        DlgListaCupones dlglista = new DlgListaCupones(myParentFrame, "", true);
        dlglista.setVisible(true);
        if (FarmaVariables.vAceptar) {
            log.info("***********JCORTEZ--- procesando cupones clientes*********");
            operaCupones();
        }
    }


    /**
     * Se procesa los cupones cargados
     * @author JCORTEZ
     * @since 05.08.09
     * */
    private void operaCupones() {

        String cadena = "";
        //asumiendo que solo se cargara un cupon por campaña
        for (int i = 0; i < VariablesVentas.vArrayListCuponesCliente.size(); i++) {
            cadena = ((String)((ArrayList)VariablesVentas.vArrayListCuponesCliente.get(i)).get(1)).trim();
            if (UtilityVentas.esCupon(cadena, this, txtDescProdOculto)) {
                //ERIOS 2.3.2 Valida convenio BTLMF
                if (VariablesVentas.vEsPedidoConvenio ||
                    (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)) {
                    FarmaUtility.showMessage(this, "No puede agregar cupones a un pedido por convenio.",
                                             txtDescProdOculto);
                    return;
                }
                validarAgregarCupon(cadena);
            }
        }

    }

    /**
     * Nuestra la nueva ventana de Cobro
     * @author Edgar Rios Navarro
     * @since 01.04.2013
     */
    private void mostrarNuevoCobro() {
        // DUBILLUZ 04.02.2013
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();

        DlgNuevoCobro dlgFormaPago = new DlgNuevoCobro(myParentFrame, "", true);

        dlgFormaPago.setIndPedirLogueo(false);
        dlgFormaPago.setIndPantallaCerrarAnularPed(true);
        dlgFormaPago.setIndPantallaCerrarCobrarPed(true);

        //INI ASOSA - 03.07.2014
        String descProd = FarmaUtility.getValueFieldJTable(tblProductos, 0, 1);
        log.info("producto nuevo cobro: " + descProd);
        dlgFormaPago.setDescProductoRecVirtual(descProd);
        dlgFormaPago.setDescProductoRimac(descProd);    //ASOSA - 12/01/2015 - RIMAC
        //FIN ASOSA - 03.07.2014

        dlgFormaPago.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        } else
            pedidoGenerado = false;
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================


    //JMIRANDA 23.06.2010
    //NUEVO PROCESO PARA VALIDAR CONVENIO

    private void validaConvenio_v2(KeyEvent e, String vCoPagoConvenio) {
        String vkF = "";
        boolean indExisteConv = false;
        boolean indMontoValido = false;

        if (pedidoEnProceso) {
            return;
        }
        pedidoEnProceso = true;
        if (false) //Ha elegido un convenio y un cliente
        {
            //-----------INI PEDIDO CONVENIO
            log.debug("VariablesConvenio.vArrayList_DatosConvenio : " + VariablesConvenio.vArrayList_DatosConvenio);
            String vCodCli = "" + VariablesConvenio.vArrayList_DatosConvenio.get(1);
            String vDniCli = "" + VariablesConvenio.vArrayList_DatosConvenio.get(5);
            String vCodConvenio = "" + VariablesConvenio.vArrayList_DatosConvenio.get(0);

            log.debug("vCodConvenio " + VariablesConvenio.vArrayList_DatosConvenio.get(0));
            log.debug("vDniCLi " + VariablesConvenio.vArrayList_DatosConvenio.get(5));
            if (!vCodCli.equalsIgnoreCase("")) {
                //--INI TIENE CODCLI
                String mensaje = "";
                //1° Obtiene valor de copago
                try {
                    double totalS = FarmaUtility.getDecimalNumber(lblTotalS.getText());

                    if (FarmaUtility.getDecimalNumber(vCoPagoConvenio) != 0) {
                        //verificar la conexión con MATRIZ
                        String vIndLinea =
                            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);

                        if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            log.debug("Existe conexion a Matriz");
                            //Paso 1 valida que exista el convenio
                            indExisteConv =
                                    UtilityConvenio.getIndClienteConvActivo(this, txtDescProdOculto, vCodConvenio,
                                                                            vDniCli, vCodCli);
                            if (indExisteConv) {
                                //Paso 2 validar el monto disponible
                                indMontoValido =
                                        UtilityConvenio.getIndValidaMontoConvenio(this, txtDescProdOculto, vCodConvenio,
                                                                                  vDniCli, totalS, vCodCli);
                                if (indMontoValido) {
                                    if (colocaVariablesDU(VariablesConvenio.vCodCliente, lblTotalS.getText())) {
                                        //El convenio está activo y el monto a usar es correcto
                                        continuarCobroPedido(e);
                                    } else {
                                        FarmaUtility.showMessage(this,
                                                                 "Ocurrió un problema al obtener variables convenio.",
                                                                 txtDescProdOculto);
                                        return;
                                    }
                                }
                            }
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "No hay linea con matriz.\n Inténtelo nuevamente si el problema persiste comuníquese con el Operador de Sistemas.",
                                                     txtDescProdOculto);
                        }
                    } else {
                        continuarCobroPedido(e);
                    }
                } catch (SQLException sql) {
                    log.error("", sql);
                    if (sql.getErrorCode() > 20000) {
                        FarmaUtility.showMessage(this,
                                                 sql.getMessage().substring(10, sql.getMessage().indexOf("ORA-06512")),
                                                 txtDescProdOculto);
                    } else {
                        FarmaUtility.showMessage(this, "Ocurrió un error al validar el convenio.\n" +
                                sql, txtDescProdOculto);
                    }
                } catch (Exception ex) {
                    log.error("", ex);
                    FarmaUtility.showMessage(this, mensaje + ex.getMessage(), tblProductos);
                } finally {
                    //cerrar conexión
                    FarmaConnectionRemoto.closeConnection();
                }
                //--FIN TIENE CODCLI
            } else {
                continuarCobroPedido(e);
            }
            //-----------FIN PEDIDO CONVENIO
        } else {
            continuarCobroPedido(e);
        }

        pedidoEnProceso = false;
        if (VariablesVentas.vIndVolverListaProductos) {
            //agregarProducto();
            agregarProducto(null); //ASOSA - 10/10/2014 - PANHD
        }
    }

    //JMIRANDA 23.06.2010

    public void continuarCobroPedido(KeyEvent e) {
        String vkF = "";
        if (e.getKeyCode() == KeyEvent.VK_F4 && !pIngresoComprobanteManual) {
            vkF = "F4";
            agregarComplementarios(vkF);
        } else if ((UtilityPtoVenta.verificaVK_F1(e)) || e.getKeyChar() == '+' && !pIngresoComprobanteManual) {
            vkF = "F1";
            agregarComplementarios(vkF);
        }
    }

    public boolean colocaVariablesDU(String vCodCli, String totalS) {
        boolean pResultado = false;
        String pCodPago = "";
        double diferencia = 0.0;
        double valTotal = 0.0;
        double totalS_double = 0.0;
        FarmaUtility.getDecimalNumber(totalS.trim());
        try {
            totalS_double = FarmaUtility.getDecimalNumber(totalS);

            pCodPago =
                    DBConvenio.obtieneCoPagoConvenio(VariablesConvenio.vCodConvenio, vCodCli, FarmaUtility.formatNumber(totalS_double));

            diferencia = 0.0;
            valTotal = FarmaUtility.getDecimalNumber(pCodPago);
            log.debug("Monto Copago: " + pCodPago);
            String valor =
                DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, vCodCli, FarmaUtility.formatNumber(valTotal),
                                            FarmaConstants.INDICADOR_S);
            log.debug("Diferencia: " + valor);
            diferencia = FarmaUtility.getDecimalNumber(valor);
            log.debug("VariablesConvenio.vIndSoloCredito: " + VariablesConvenio.vIndSoloCredito);
            if (diferencia < 0) {
                if (VariablesConvenio.vIndSoloCredito.equals(FarmaConstants.INDICADOR_N)) {
                    valTotal = valTotal + diferencia;
                }
            }

            VariablesConvenio.vValCoPago = FarmaUtility.formatNumber(valTotal);
            log.info("0000000000000000000000:");
            log.debug("VariablesConvenio.vValCoPago: " + VariablesConvenio.vValCoPago);
            log.info("0000000000000000000000:");
            if (VariablesConvenio.vValCoPago.trim().length() > 0) {
                pResultado = true;
            }
        } catch (SQLException e) {
            log.error("", e);
        }
        return pResultado;
    }

    /**
     * Graba el detalle de Producto por promocion
     * @param pTiComprobante
     * @param array
     * @param pFila
     * @param tipo indica si es Producto simple o de una promocion
     * @throws Exception
     * @author ASOSA
     * @since  05.07.2010
     */
    private void grabarDetalle_02(String pTiComprobante, ArrayList array, int pFila, int tipo) throws Exception {

        VariablesVentas.vSec_Ped_Vta_Det = String.valueOf(pFila);
        VariablesVentas.vCod_Prod = ((String)(array.get(0))).trim();
        VariablesVentas.vCant_Ingresada =
                String.valueOf(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(((String)(array.get(4))).trim())));
        //VariablesVentas.vVal_Prec_Vta = ((String)(array.get(6))).trim();
        VariablesVentas.vTotalPrecVtaProd = FarmaUtility.getDecimalNumber(((String)(array.get(7))).trim());
        VariablesVentas.vVal_Prec_Vta =
                FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd / Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                          3);
        VariablesVentas.vPorc_Dcto_1 = String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(5))).trim()));
        log.info("***-VariablesVentas.vPorc_Dcto_1 " + VariablesVentas.vCod_Prod + " - " +
                 VariablesVentas.vPorc_Dcto_1);
        if (tipo == ConstantsVentas.IND_PROD_PROM)
            VariablesVentas.vPorc_Dcto_2 =
                    String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(20))).trim()));
        else
            VariablesVentas.vPorc_Dcto_2 =
                    String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(COL_RES_DSCTO_2))).trim()));

        log.debug("***-VariablesVentas.desc_2 " + VariablesVentas.vPorc_Dcto_2);
        VariablesVentas.vPorc_Dcto_Total =
                VariablesVentas.vPorc_Dcto_1; //cuando es pedido normal, el descuento total siempre es el descuento 1
        VariablesVentas.vVal_Total_Bono =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(8))).trim()));
        VariablesVentas.vVal_Frac = ((String)(array.get(10))).trim();
        VariablesVentas.vEst_Ped_Vta_Det = ConstantsVentas.ESTADO_PEDIDO_DETALLE_ACTIVO;
        VariablesVentas.vSec_Usu_Local = FarmaVariables.vNuSecUsu;
        //VariablesVentas.vVal_Prec_Lista = String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(3))).trim()));
        VariablesVentas.vVal_Prec_Lista =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(6))).trim()));
        VariablesVentas.vVal_Igv_Prod = ((String)(array.get(11))).trim();
        VariablesVentas.vUnid_Vta = ((String)(array.get(2))).trim();
        VariablesVentas.vNumeroARecargar = ((String)(array.get(13))).trim();
        String secrespaldo = ""; //ASOSA, 05.07.2010

        //INI ASOSA - 03/09/2014 - CORRECCION
        VariablesVentas.vValorMultiplicacion = "1";
        log.info("TAMAÑO DETALLE: " + array.size());
        if (array.size() >= 28) {
            VariablesVentas.vValorMultiplicacion = ((String)(array.get(27))).trim(); //ASOSA - 11/08/2014
        }
        //FIN ASOSA - 03/09/2014 - CORRECCION

        //ConstantsVentas.IND_PROD_SIMPLE
        // numero 24
        int posSecRespaldo = 0;
        if (tipo == ConstantsVentas.IND_PROD_SIMPLE)
            posSecRespaldo = 26;
        else if (tipo == ConstantsVentas.IND_PROD_PROM)
            posSecRespaldo = 24;
        /*FarmaUtility.showMessage(this, "posSecRespaldo: " + posSecRespaldo,
                                 null);
        FarmaUtility.showMessage(this,
                                 "posSecRespaldo: " + ((String)(array.get(posSecRespaldo))).trim(),
                                 null);*/
        log.debug("WAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa: " +
                  posSecRespaldo);
        secrespaldo = ((String)(array.get(posSecRespaldo))).trim(); //ASOSA, 05.07.2010

        //log.debug("***-VariablesVentas.vVal_Prec_Pub "+VariablesVentas.vVal_Prec_Pub);
        if (tipo == ConstantsVentas.IND_PROD_SIMPLE)
            VariablesVentas.vVal_Prec_Pub = ((String)(array.get(18))).trim();
        if (tipo == ConstantsVentas.IND_PROD_PROM)
            VariablesVentas.vVal_Prec_Pub = ((String)(array.get(21))).trim();
        /*
       Para grabar la promocion  en el detalle dubilluz 28.02.2008
       */

        if (tipo == ConstantsVentas.IND_PROD_PROM)
            VariablesVentas.vCodPromoDet = ((String)(array.get(18))).trim();
        else
            VariablesVentas.vCodPromoDet = "";
        log.debug("Promo eeeee " + array);
        log.debug("Promo al detalle : " + VariablesVentas.vCodPromoDet);

        if (tipo == ConstantsVentas.IND_PROD_SIMPLE) {
            VariablesVentas.vIndOrigenProdVta = array.get(COL_RES_ORIG_PROD).toString().trim();
            VariablesVentas.vCantxDia = array.get(COL_RES_CANT_XDIA).toString().trim();
            VariablesVentas.vCantxDias = array.get(COL_RES_CANT_DIAS).toString().trim();
        } else {

            VariablesVentas.vIndOrigenProdVta =
                    array.get(23).toString().trim(); //JCHAVEZ 20102009 se asignaba cadena nula ""
            VariablesVentas.vCantxDia = "";
            VariablesVentas.vCantxDias = "";
        }

        //JCHAVEZ 20102009
        if (tipo == ConstantsVentas.IND_PROD_PROM) {
            VariablesVentas.vAhorroPack = ((String)(array.get(22))).trim();


        }
        log.debug("VariablesVentas.vCod_Prod:" + VariablesVentas.vCod_Prod);
        log.debug("VariablesVentas.tipo:" + tipo);


        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.length() > 0) {
            //JCHAVEZ 20102009
            if (tipo == ConstantsVentas.IND_PROD_SIMPLE) {

                DBVentas.grabarDetallePedido_02(secrespaldo);
                VariablesVentas.vCodPromoDet = "";
            }
        } else {
            //JCHAVEZ 20102009
            DBVentas.grabarDetallePedido_02(secrespaldo);
            VariablesVentas.vCodPromoDet = "";
        }

        //dveliz 15.08.08
        //DUBILLUZ 22.08.2008
        /* if(VariablesCampana.vFlag){
              for(int i =0; i<VariablesCampana.vListaCupones.size();i++){
                  ArrayList myList = (ArrayList)VariablesCampana.vListaCupones.get(i);
                  agregarClienteCampana(myList.get(0).toString(),
                                            myList.get(1).toString(),
                                            myList.get(2).toString(),
                                            myList.get(3).toString(),
                                            myList.get(4).toString(),
                                            myList.get(5).toString(),
                                            myList.get(6).toString(),
                                            myList.get(7).toString(),
                                            myList.get(8).toString(),
                                            myList.get(9).toString(),
                                            myList.get(10).toString(),
                                            myList.get(11).toString());
              }*/

    }

    /************************************************************ INI - ASOSA, 09.07.2010 ***************************************************************/
    private void cancelaOperacion_02() {
        String codProd = "";
        String cantidad = "";
        String indControlStk = "";
        String secRespaldo = ""; //ASOSA, 02.07.2010
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
            codProd = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 0);
            VariablesVentas.vVal_Frac =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 10);
            cantidad = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 4);
            indControlStk = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 16);
            secRespaldo =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 26); //ASOSA, 02.07.2010
            VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
            if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                /*!UtilityVentas.actualizaStkComprometidoProd(codProd, //ANTES-ASOSA, 02.07.2010
                                                       Integer.parseInt(cantidad),
                                                       ConstantsVentas.INDICADOR_D,
                                                       ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR,
                                                       Integer.parseInt(cantidad),
                                                       true,
                                                       this,
                                                       tblProductos))*/
                !UtilityVentas.operaStkCompProdResp(codProd,
                    //ASOSA, 02.07.2010
                    0, ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, true, this,
                    tblProductos, secRespaldo))
                return;
        }
        /**
       * Actualiza comprometido a Arra Promociones
       * @author : dubilluz
       * @since  : 25.06.2007
       */
        ArrayList aux = new ArrayList();
        ArrayList agrupado = new ArrayList();
        String codProm = "";
        codProd = "";
        cantidad = ""; //((String)(tblProductos.getValueAt(filaActual,4))).trim();
        indControlStk = ""; // ((String)(tblProductos.getValueAt(filaActual,16))).trim();
        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size(); i++) {
            agrupado = new ArrayList();
            aux = (ArrayList)(VariablesVentas.vArrayList_Promociones.get(i));
            codProm = ((String)(aux.get(0))).trim();
            agrupado = detalle_Prom(VariablesVentas.vArrayList_Prod_Promociones, codProm);
            log.debug("AAAAAAAAAAPRODSDSDSDSDSDS: " + agrupado);
            //agrupado=agrupar(agrupado);
            for (int j = 0; j < agrupado.size(); j++) //VariablesVentas.vArrayList_Prod_Promociones.size(); j++)
            {
                aux = (ArrayList)(agrupado.get(j)); //VariablesVentas.vArrayList_Prod_Promociones.get(j));
                //if((((String)(aux.get(18))).trim()).equalsIgnoreCase(codProm)){
                codProd = ((String)(aux.get(0))).trim();
                cantidad = ((String)(aux.get(4))).trim();
                VariablesVentas.vVal_Frac = ((String)(aux.get(10))).trim();
                indControlStk = ((String)(aux.get(16))).trim();
                secRespaldo = ((String)(aux.get(24))).trim(); //ASOSA, 08.07.2010
                VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(codProd,Integer.parseInt(cantidad),ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad), //Antes, ASOSA, 08.07.2010
                                                         false,
                                                         this,
                                                         tblProductos))*/
                    !UtilityVentas.operaStkCompProdResp(codProd,
                        //ASOSA, 08.07.2010
                        0, ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, false,
                        this, tblProductos, secRespaldo))
                    return;
                //}
            }

        }
        FarmaUtility.aceptarTransaccion();
        inicializaArrayList();
        //jcallo: el parametro estaba en false--> se cambio a true
        cerrarVentana(true);
    }


    private boolean cargaLogin_verifica() {
        VariablesVentas.vListaProdFaltaCero = new ArrayList();
        VariablesVentas.vListaProdFaltaCero.clear();
        boolean band = false;
        //limpiando variables de fidelizacion
        //UtilityFidelizacion.setVariables();

        //JCORTEZ 04.08.09 Se limpiar cupones.
        VariablesVentas.vArrayListCuponesCliente.clear();
        VariablesVentas.dniListCupon = "";

        //dubilluz
        //22.10.2014
        if (!UtilityPtoVenta.getIndLoginCajUnicaVez()) {
            // KMONCADA 2015.03.19
            if(!actuaRobot){
                DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
                dlgLogin.setRolUsuario(FarmaConstants.ROL_VENDEDOR);
                dlgLogin.setVisible(true);
            }else{
                FarmaVariables.vAceptar = true;
            }
        } else
            FarmaVariables.vAceptar = true;

        if (FarmaVariables.vAceptar) {

            //Agregado por FRAMIREZ 09/11/2011
            //Muestra mensaje de retencion de un convenio.
            log.debug("<<<<<<<<Ingresando al mensaje de Retencion>>>>>>>>>");
            log.debug("vCodConvenio :" + VariablesConvenioBTLMF.vCodConvenio);

            if (VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
                mostrarMensajeRetencion();
            }

            log.info("******* JCORTEZ *********");
            if (UtilityCaja.existeIpImpresora(this, null)) {
                if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) &&
                    !UtilityCaja.existeCajaUsuarioImpresora(this, null)) {
                    //linea agrega pàra corregir el error al validar los roles de los usuarios
                    //FarmaVariables.dlgLogin = dlgLogin;
                    log.debug("");
                    VariablesCaja.vVerificaCajero = false;
                    //FarmaUtility.showMessage(this,"No tiene Asociada Caja esa Impresora",txtDescProdOculto);
                    //cerrarVentana(false);
                } else {
                    //FarmaVariables.dlgLogin = dlgLogin;
                    log.info("******* 2 *********");
                    log.info("Usuario: " + FarmaVariables.vIdUsu);
                    muestraMensajeUsuario();
                    FarmaVariables.vAceptar = false;
                    VariablesCaja.vVerificaCajero = true;
                    band = true;
                    //agregarProducto();
                }
            } else {
                //no se genera venta sin impresora asignada (Boleta/ Ticket)
                //FarmaVariables.dlgLogin = dlgLogin;
                VariablesCaja.vVerificaCajero = false;
                //FarmaUtility.showMessage(this,"No tiene Asociada ninguna Impresora",txtDescProdOculto);
                //cerrarVentana(false);
            }

        } //else
        //cerrarVentana(false);
        return band;
    }

    //Agregado Por FRAMIREZ.

    public void mostrarMensajeRetencion() {
        ArrayList htmlDerecho = new ArrayList();


        UtilityConvenioBTLMF.listaMensaje(htmlDerecho, VariablesConvenioBTLMF.vCodConvenio,
                                          ConstantsConvenioBTLMF.FLG_DOC_RETENCION, this, null);

        log.debug("Tamaño:" + htmlDerecho);
        if (htmlDerecho.size() != 0) {
            DlgMensajeRetencion dlg = new DlgMensajeRetencion(myParentFrame, "", false);
            dlg.setVisible(true);
        }

    }
    
    public void funcionF12(String pCodCampanaCupon,boolean vTeclaF12) {
        funcionF12(pCodCampanaCupon,vTeclaF12, false);
    }
    
    public void funcionF12(String pCodCampanaCupon,boolean vTeclaF12, boolean isPasoTarjeta) {
        /*
        VariablesFidelizacion.tmpCodCampanaCupon = pCodCampanaCupon;
        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this,
                                     "No puede agregar una tarjeta a un " +
                                     "pedido por convenio.",
                                     txtDescProdOculto);
            return;
        }
        mostrarBuscarTarjetaPorDNI();
        */

        AuxiliarFidelizacion.funcionF12(pCodCampanaCupon, txtDescProdOculto, this.myParentFrame, lblDNI_Anul,
                                        lblCliente, this, "R", lblDNI_SIN_COMISION,vTeclaF12, isPasoTarjeta);

        neoOperaResumenPedido();
        FarmaUtility.moveFocus(txtDescProdOculto);
        VariablesFidelizacion.tmpCodCampanaCupon = "N";
        //Inicio - dubilluz 15.06.2011
        evaluaFormaPagoFidelizado();
        //Fin - dubilluz 15.06.2011
    }

    public void evaluaFormaPagoFidelizado() {

        if (VariablesFidelizacion.vDniCliente.trim().length() >= 1) {
            //Inicio - dubilluz 15.06.2011
            lblFormaPago.setVisible(false);
            lblFormaPago.setOpaque(false);
            if (VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S") ||
                VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S"))
                if (!VariablesFidelizacion.vNamePagoTarjeta.trim().equalsIgnoreCase("N")) {
                    lblFormaPago.setVisible(true);
                    lblFormaPago.setBackground(Color.white);
                    lblFormaPago.setOpaque(true);
                    if (VariablesFidelizacion.vCodFPagoTarjeta.trim().equalsIgnoreCase("T0000")) {
                        lblFormaPago.setText(" Pago con Todas las Tarjetas");
                    } else {
                        lblFormaPago.setText(" Pago con " + VariablesFidelizacion.vNamePagoTarjeta);
                    }
                    lblFormaPago.setText("  " + lblFormaPago.getText().trim().toUpperCase());
                }
            //Fin - dubilluz 15.06.2011
        }
    }

    public boolean isFormaPagoUso_x_Cupon(String codCampCupon) {
        String valor = "N";
        try {
            valor = DBFidelizacion.isValidaFormaPagoUso_x_Campana(codCampCupon).trim();
        } catch (SQLException e) {
            log.error("", e);
        }

        if (valor.trim().equalsIgnoreCase("S"))
            return true;

        return false;
    }

    private void validaIngresoTarjetaPagoCampanaAutomatica(String nroTarjetaFormaPago) {
        if (isNumerico(nroTarjetaFormaPago)) {
            Map mapCupon;
            boolean obligarIngresarFP = false;
            boolean yaIngresoFormaPago = false;

            VariablesFidelizacion.tmp_NumTarjeta_unica_Campana = nroTarjetaFormaPago;

            //verifica si la tarjeta ya esta asociada a un DNI
            String pExisteAsociado = UtilityFidelizacion.existeDniAsociado(nroTarjetaFormaPago);

            if (pExisteAsociado.trim().equalsIgnoreCase("S")) {
                //VALIDA EL CLIENTE POR TARJETA 12.01.2011
                String cadena = nroTarjetaFormaPago;
                validarClienteTarjeta(cadena, false);
                //VariablesFidelizacion.vNumTarjeta = cadena.trim();
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                    log.debug("RRRR");
                    UtilityFidelizacion.operaCampañasFidelizacion(cadena);
                    //DAUBILLUZ -- Filtra los DNI anulados
                    //25.05.2009
                    VariablesFidelizacion.vDNI_Anulado =
                            UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
                    VariablesFidelizacion.vAhorroDNI_x_Periodo =
                            UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,
                                                                           VariablesFidelizacion.vNumTarjeta);
                    // envio sl numero de tarjeta
                    // 01.06.2012 dubilluz
                    VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                            UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,
                                                                           VariablesFidelizacion.vNumTarjeta);
                    // 01.06.2012 dubilluz
                    log.info("Variable de DNI_ANULADO: " + VariablesFidelizacion.vDNI_Anulado);
                    log.info("Variable de vAhorroDNI_x_Periodo: " + VariablesFidelizacion.vAhorroDNI_x_Periodo);
                    log.info("Variable de vMaximoAhorroDNIxPeriodo: " +
                             VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);

                    AuxiliarFidelizacion.setMensajeDNIFidelizado(lblDNI_Anul, "R", txtDescProdOculto, this);
                }
            } else {
                if (VariablesFidelizacion.vDniCliente.trim().length() == 0) {
                    funcionF12("N",true, true);
                    yaIngresoFormaPago = true;
                }


            }
        }
        //cargar las campañas de tipo automatica
        String cadenaTarjeta = UtilityFidelizacion.getDatosTarjetaFormaPago(nroTarjetaFormaPago.trim());
        String[] datos = cadenaTarjeta.split("@");
        if (datos.length == 2) {
            VariablesFidelizacion.vIndUsoEfectivo = "N";
            VariablesFidelizacion.vIndUsoTarjeta = "S";
            VariablesFidelizacion.vCodFPagoTarjeta = datos[0].toString().trim();
            VariablesFidelizacion.vNamePagoTarjeta = datos[1].toString().trim();

            //if(VariablesFidelizacion.vDNI_Anulado)
            //{
            if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
            VariablesFidelizacion.vNumTarjetaCreditoDebito_Campana = nroTarjetaFormaPago.trim();
            //}
            evaluaFormaPagoFidelizado();
            txtDescProdOculto.setText("");
        }

        neoOperaResumenPedido();
    }


    public boolean isNumerico(String pCadena) {
        int numero = 0;
        boolean pRes = false;
        try {
            for (int i = 0; i < pCadena.length(); i++) {
                numero = Integer.parseInt(pCadena.charAt(i) + "");
                pRes = true;
            }
        } catch (NumberFormatException e) {
            pRes = false;
        }
        return pRes;
    }

    public void setFormatoTarjetaCredito(String pCadena) {
        String pCadenaNueva = UtilityFidelizacion.pIsTarjetaVisaRetornaNumero(pCadena).trim();
        if (!pCadenaNueva.trim().equalsIgnoreCase("N")) {
            log.debug("Es tarjeta");
            txtDescProdOculto.setText(pCadenaNueva.trim());
            pasoTarjeta = true;
        } else {
            log.debug("NO ES tarjeta");
            pasoTarjeta = false;
        }

    }

    //Dubilluz - 06.12.2011

    public void ingresaMedicoFidelizado() {
        AuxiliarFidelizacion.ingresoMedico(this.myParentFrame, lblMedico, lblDNI_Anul, lblCliente, this, "R",
                                           lblDNI_SIN_COMISION, txtDescProdOculto);
        neoOperaResumenPedido();
        /*
        String pPermiteIngresoMedido =
            UtilityFidelizacion.getPermiteIngresoMedido();

        if (pPermiteIngresoMedido.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (VariablesVentas.vEsPedidoConvenio) {
                FarmaUtility.showMessage(this,
                                         "No puede ingresar el Médido porque tiene" +
                                         "seleccionado convenio.",
                                         txtDescProdOculto);
                return;
            }

            String pIngresoMedido =
                FarmaUtility.ShowInput(this, "Ingrese el Colegio Médico:");
            log.debug("pIngresoMedido:" + pIngresoMedido);
            if (pIngresoMedido.trim().length() > 0){
                log.debug("valida si existe el medico");
                String pExisteMedico =
                    UtilityFidelizacion.getExisteMedido(this.myParentFrame,pIngresoMedido.trim());
                log.debug("pExisteMedico:" + pExisteMedico);

                if (pExisteMedico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                        0) {
                        log.debug(">>> ya existe DNI ingresado");
                    } else {
                        log.debug(">>> NO EXISTE DNI ingresado");
                        funcionF12("N");
                    }

                    log.debug(">>>VariablesFidelizacion.vNumTarjeta.trim().length()+"+VariablesFidelizacion.vNumTarjeta.trim().length());
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                        0) {
                        log.debug(">>> BUSCA campañas para agregar las q tiene asociado ese tipo de colegio");
                        UtilityFidelizacion.agregaCampanaMedicoAuto(VariablesFidelizacion.vNumTarjeta,
                                                                    VariablesFidelizacion.V_TIPO_COLEGIO.trim(),
                                                                    VariablesFidelizacion.V_OLD_TIPO_COLEGIO);
                        //VariablesFidelizacion.vColegioMedico = pIngresoMedido.trim();
                        ///////////////////////////////////////////////
                        VariablesFidelizacion.vColegioMedico = VariablesFidelizacion.V_NUM_CMP;
                        ///////////////////////////////////////////////
                        log.debug(">>> agrego campna..");
                    }
                    else
                    {
                        ///////////////////////////////////////////////
                        UtilityFidelizacion.limpiaVariablesMedico();
                        ///////////////////////////////////////////////
                    }
                }
                else{
                    if(VariablesFidelizacion.vColegioMedico.trim().length()>0&&VariablesFidelizacion.vNumTarjeta.trim().length()>0)
                      UtilityFidelizacion.quitarCampanaMedico(VariablesFidelizacion.vNumTarjeta,VariablesFidelizacion.V_TIPO_COLEGIO);
                   FarmaUtility.showMessage(this,"No existe el Médico Seleccionado. Verifique!!",txtDescProdOculto);

                    UtilityFidelizacion.limpiaVariablesMedico();
                }
            }
        }*/

    }

    /**
     * @since 2.4.8 ERIOS Se consulta el limite de credito en linea.
     * @param dialog
     * @return
     */
    public boolean existeSaldoCredDispBenif(JDialog dialog) {
        boolean ret = true;
        try {
            FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
            ArrayList<String> beneficiario =
                facadeConvenioBTLMF.obtieneBeneficRemoto(VariablesConvenioBTLMF.vCodCliente);
            VariablesConvenioBTLMF.vLineaCredito = beneficiario.get(DlgListaPantallaBTLMF.COLUMN_LINEA_CREDITO).trim();
            String vEstado = beneficiario.get(3).trim();
            if (vEstado.equals("N")) {
                FarmaUtility.showMessage(dialog,
                                         "El Beneficiario ha sido Bloqueado en Matriz. Comuníquese con el área de Convenios.",
                                         "");
                ret = false;
            } else {
                UtilityConvenioBTLMF.consultarSaldCreditoBenif(dialog);
                if (VariablesConvenioBTLMF.vImpSubTotal >
                    FarmaUtility.getDecimalNumber(VariablesConvenioBTLMF.vMontoSaldo)) {

                    FarmaUtility.showMessage(dialog,
                                             "El importe " + VariablesConvenioBTLMF.vImpSubTotal + " supera el saldo de credito del Benificiario!!",
                                             "");
                    ret = false;
                }
            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(dialog, "Ocurrió un error al verificar Límite de Crédito en línea.\n" +
                    e.getMessage(), null);
            ret = false;
        }

        return ret;
    }

    public String getMensajeComprobanteConvenio(String pCodConvenio) {
        String pCadena = "";
        try {
            pCadena =
                    DBConvenioBTLMF.getMsgComprobante(pCodConvenio, VariablesConvenioBTLMF.vImpSubTotal, VariablesConvenioBTLMF.vValorSelCopago);
        } catch (SQLException e) {
            pCadena = "N";
            log.error("", e);
        }
        return pCadena;
    }

    private void txtDescProdOculto_actionPerformed(ActionEvent e) {
    }

    /**
     * Busca el producto ingresado
     * @author ERIOS
     * @since 03.07.2013
     * @param codProd
     * @param e
     */
    private void buscaProducto(String codProd, KeyEvent e) {
        char primerkeyChar = codProd.charAt(0);
        char segundokeyChar;

        if (codProd.length() > 1)
            segundokeyChar = codProd.charAt(1);
        else
            segundokeyChar = primerkeyChar;

        char ultimokeyChar = codProd.charAt(codProd.length() - 1);
        if (codProd.length() > 6 &&
            (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) && (!Character.isLetter(ultimokeyChar))))) {
            VariablesVentas.vCodigoBarra = codProd;

            try {
                FarmaUtility.moveFocus(txtDescProdOculto);
                codProd = DBVentas.obtieneCodigoProductoBarra();
            } catch (SQLException er) {
                log.error("", er);
            }
        }

        if (codProd.length() == 6) {
            VariablesVentas.vCodProdBusq = codProd;
            ArrayList myArray = new ArrayList();
            obtieneInfoProdEnArrayList(myArray, codProd);

            if (myArray.size() == 1) {
                VariablesVentas.vKeyPress = e;
                // kmoncada evalua si hay producto virtual
                if (!VariablesVentas.vProductoVirtual) {
                    //agregarProducto(null);
                    
                    if (!existsProdRimac()) { //ASOSA - 19/01/2015 - RIMAC   //ASOSA - 12/01/2015 - RIMAC
                        agregarProducto(null); //ASOSA - 10/10/2014 - PANHD
                    } else {
                        FarmaUtility.showMessage(this, "Ya se selecciono un producto rimac", txtDescProdOculto);
                    }

                } else {
                    FarmaUtility.showMessage(this, "Ya se selecciono un producto virtual.", txtDescProdOculto);
                }

                VariablesVentas.vCodProdBusq = "";
                VariablesVentas.vKeyPress = null;
            } else {
                FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                         txtDescProdOculto);
                VariablesVentas.vCodProdBusq = "";
                VariablesVentas.vKeyPress = null;
            }
        }
    }

    public void setFrameEconoFar(FrmEconoFar frmEconoFar) {
        this.frmEconoFar = frmEconoFar;
    }

    private void txtDescProdOculto_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtDescProdOculto);
    }

    public void abrePedidosDelivery(boolean vIndicador) {
        this.vIndPedidosDelivery = vIndicador;
    }

    private void abrePedidosDelivery() {
        DlgUltimosPedidos dlgUltimosPedidos = new DlgUltimosPedidos(myParentFrame, "", true);
        dlgUltimosPedidos.setTipoVenta(ConstantsVentas.TIPO_PEDIDO_DELIVERY);
        dlgUltimosPedidos.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        }
    }

    private void cambiaFunciones() {
        lblF1.setVisible(false);
        lblF2.setVisible(false);
        lblF6.setVisible(false);
        lblF8.setVisible(false);
        lblF9.setVisible(false);
        
        lblF1.setText("[F11] Cobrar");
        lblF1.setVisible(true);
    }

    private void cobraPedidoManual() {
        /*if(vTipoComp.trim().equalsIgnoreCase("01")||vTipoComp.trim().equalsIgnoreCase("02")){
            VariablesCaja.pManualSerieComprobante = txtSerie.getText().trim();
            VariablesCaja.pManualNumCompPago = txtNroComprobante.getText().trim(); ;
            VariablesCaja.pManualTipCompPago = vTipoComp;
            cerrarVentana(true);
        }*/
        String vkF = "F1";
        if (VariablesCaja.pManualTipCompPago.trim().equalsIgnoreCase("01")) {
            vkF = "F1";
            FarmaUtility.showMessage(this, "Se va Ingresar la :\n" +
                    "BOLETA : " + VariablesCaja.pManualSerieComprobante + VariablesCaja.pManualNumCompPago,
                    txtDescProdOculto);
        } else if (VariablesCaja.pManualTipCompPago.trim().equalsIgnoreCase("02")) {
            vkF = "F4";
            FarmaUtility.showMessage(this, "Se va Ingresar la :\n" +
                    "FACTURA : " + VariablesCaja.pManualSerieComprobante + VariablesCaja.pManualNumCompPago,
                    txtDescProdOculto);
        }
        agregarComplementarios(vkF);
    }
    
    /**
     * Si existe algun producto rimac en el resumen de pedido
     * @author ASOSA
     * @since 19/01/2015
     * @kind RIMAC
     * @return
     */
    public boolean existsProdRimac(){
        boolean flag = false;
        int cant = 0;
        for (int c = 0; c < tableModelResumenPedido.data.size(); c++) {
            String codProd = FarmaUtility.getValueFieldArrayList(tableModelResumenPedido.data, c, 0);            
            boolean flag02 = UtilityInventario.isExistProdRimac(codProd);
            if (flag02 == true) {
                cant = cant + 1;
            }            
        }
        if (cant > 0) {
            flag = true;
        }
        return flag;
    }
    
}
