package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JButtonFunction;

import com.gs.mifarma.componentes.JButtonLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.caja.DlgIngresoSobre;
import mifarma.ptoventa.caja.DlgNewCobro;
import mifarma.ptoventa.caja.DlgProcesarCobroNew;
import mifarma.ptoventa.caja.DlgSeleccionTipoComprobante;
import mifarma.ptoventa.caja.reference.BeanDetaPago;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.UtilityNewCobro;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesNewCobro;
import mifarma.ptoventa.campAcumulada.DlgListaCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.campana.DlgListDatosCampana;
import mifarma.ptoventa.campana.reference.DBCampana;
import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.UtilityConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.delivery.DlgListaClientes;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.fidelizacion.reference.AuxiliarFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesReceta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;
import mifarma.ptoventa.reference.VariablesPtoVenta;
//VariablesPtoVenta

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import javax.swing.JOptionPane;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import mifarma.common.FarmaDBUtilityRemoto;

import mifarma.ptoventa.caja.DlgIngresoSobre;
import mifarma.ptoventa.caja.DlgNewCobro;
import mifarma.ptoventa.caja.DlgProcesarCobroNew;
import mifarma.ptoventa.caja.DlgTarjeCred;
import mifarma.ptoventa.caja.reference.BeanDetaPago;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.UtilityNewCobro;
import mifarma.ptoventa.caja.reference.VariablesNewCobro;
import mifarma.ptoventa.convenio.reference.UtilityConvenio;
import mifarma.ptoventa.convenioBTLMF.DlgMensajeRetencion;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.hilos.Fidelizacion;
import  mifarma.ptoventa.fidelizacion.reference.AuxiliarFidelizacion;

/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgResumenPedido.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      29.12.2005   Creación
 * PAULO       28.04.2006   Modificacion<br>
 * JCALLO	   03/03/2009   Modificacion
 * ASOSA        18.12.2009  Modificacion <br>
 * ASOSA        03.02.2010 Modificacion <br>
 * ASOSA        17.02.2010 Modificacion <br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 */

public class DlgResumenPedido extends JDialog {

    private double diferencia = 0;
    private String valor = "";
    JTable tabla01 = new JTable();
    JTable tabla02 = new JTable();
    JTextFieldSanSerif cajita = new JTextFieldSanSerif();
    JLabel lblvuelto = new JLabel();


    private static final Log log = LogFactory.getLog(DlgResumenPedido.class);

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
    private final
    /* Resumen Pedido */
    //JCORTEZ 17.04.08
    int COL_RES_DSCTO = 5;
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
    private JLabelFunction lblF8 = new JLabelFunction();
    private JTable tblProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblCliente_T = new JLabelWhite();
    
    private JLabelWhite lblLCredito_T = new JLabelWhite();
    
    private JLabelWhite lblBeneficiario_T = new JLabelWhite();


    private JLabelWhite lblCliente = new JLabelWhite();
    
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();

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
    private JTextField txtDescProdOculto = new JTextField();

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
    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */
    public DlgResumenPedido() {
        this(null, "", false);
    }

    /**
     *Constructor
     *@param parent Objeto Frame de la Aplicación.
     *@param title Título de la Ventana.
     *@param modal Tipo de Ventana.
     */
    public DlgResumenPedido(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
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
        this.setSize(new Dimension(744, 589));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Resumen de Pedido" + " /  IP : " +
                      FarmaVariables.vIpPc);
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
        lblCreditoT.setText("");

        lblCreditoT.setVisible(false);
        lblCreditoT.setFont(new Font("SansSerif", 1, 14));
        lblCreditoT.setForeground(new Color(43, 141, 39));
        lblCreditoT.setBounds(new Rectangle(195, 315, 120, 20));
        lblCredito.setText("");
        lblCredito.setVisible(false);
        lblCredito.setFont(new Font("SansSerif", 1, 14));
        lblCredito.setForeground(new Color(43, 141, 39));
        lblCredito.setBounds(new Rectangle(330, 315, 75, 20));
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
        btnRelacionProductos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0,
                                                                       0));
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
        lblF3.setText("[ F3 ]  Agregar Prod");
        lblF3.setBounds(new Rectangle(225, 400, 125, 20));
        lblEnter.setText("[ ENTER ]  Cambiar Cantidad");
        lblEnter.setBounds(new Rectangle(10, 425, 180, 20));
        lblF5.setText("[ F5 ]  Borrar Producto");
        lblF5.setBounds(new Rectangle(355, 400, 135, 20));
        lblF1.setText("[ F1 ]  Boleta");
        lblF1.setBounds(new Rectangle(10, 400, 100, 20));
        lblF2.setText("[ F4 ]  Factura");
        lblF2.setBounds(new Rectangle(115, 400, 105, 20));
        lblF8.setText("[ F8 ] Dcto por Receta");
        lblF8.setBounds(new Rectangle(305, 425, 140, 20));
        tblProductos.setFont(new Font("SansSerif", 0, 12));
        tblProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblProductos_keyPressed(e);
                    }
                });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(635, 400, 90, 20));
        pnlTitle1.setBounds(new Rectangle(10, 10, 715, 30));
        lblCliente_T.setText("Cliente:");
        lblCliente_T.setBounds(new Rectangle(15, 5, 55, 20));
        lblCliente_T.setFont(new Font("SansSerif", 1, 14));
        lblCliente.setBounds(new Rectangle(75, 5, 315, 20));
        lblCliente.setFont(new Font("SansSerif", 1, 14));
        lblF9.setBounds(new Rectangle(450, 425, 150, 20));
        // lblF9.setText("[ F9 ] Asociar Campaña");
        //lblF9.setText("[ F9 ] Ver Campañas");
        lblF9.setText("[ F9 ] Camp. Acumulada");
        lblF12.setBounds(new Rectangle(10, 450, 150, 20));
        lblF12.setText("[ F12 ] Buscar TrjxDNI");
        lblF10.setBounds(new Rectangle(605, 425, 120, 20));
        lblF10.setText("[ F10 ] Ver Receta");
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
        lblProdOculto_T.setText("Producto:");
        lblProdOculto_T.setBounds(new Rectangle(5, 5, 60, 20));
        lblF6.setText("[ F6 ] Promociones");
        lblF6.setBounds(new Rectangle(495, 400, 135, 20));
        txtMensajesPedido.setCaretColor(new Color(7, 133, 7));
        txtMensajesPedido.setEditable(false);
        jScrollPane1.setBounds(new Rectangle(10, 475, 720, 55));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jLabel1.setText("Opciones");
        jLabel1.setBounds(new Rectangle(15, 375, 70, 20));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        lblCuponIngr.setText("-");
        lblCuponIngr.setFont(new Font("SansSerif", 1, 12));
        lblCuponIngr.setBounds(new Rectangle(165, 450, 560, 25));
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
        lblF7.setBounds(new Rectangle(195, 425, 95, 20));
        lblF7.setText("[ F8 ]  Info Prod");
        //lblFormaPago.setOpaque(true);
        lblFormaPago.setText("kokokokokoko okokoko okokok ooko okokoko okoko");
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
        pnlAtencion1.add(lblUltimoPedidoT1,
                         new XYConstraints(585, 10, 70, 15));
        pnlAtencion1.add(lblVendedor1, new XYConstraints(245, 10, 70, 15));
        pnlAtencion1.add(lblNombreVendedor1,
                         new XYConstraints(315, 10, 245, 15));
        pnlAtencion1.add(lblTipoCambio1, new XYConstraints(205, 10, 40, 15));
        pnlAtencion1.add(lblFecha1, new XYConstraints(60, 10, 70, 15));
        pnlAtencion1.add(lblTipoCambioT1, new XYConstraints(130, 10, 80, 15));
        pnlAtencion1.add(lblFechaT1, new XYConstraints(10, 10, 50, 15));
        pnlTotalesD.add(lblTotalD, new XYConstraints(615, 5, 80, 20));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));

        pnlTotalesD.add(lblTotalD, new XYConstraints(619, 9, 80, 20));
        pnlTotalesD.add(lblTotalS, new XYConstraints(464, 9, 95, 20));
        pnlTotalesD.add(lblTotalDT, new XYConstraints(579, 9, 35, 20));
        pnlTotalesD.add(lblTotalST, new XYConstraints(379, 9, 80, 20));
        pnlTotalesD.add(lblRedondeoT, new XYConstraints(4, 9, 70, 20));
        pnlTotalesD.add(lblRedondeo, new XYConstraints(79, 9, 65, 20));
        pnlTotalesD.add(lblPorPagarT, new XYConstraints(194, 9, 100, 20));
        pnlTotalesD.add(lblPorPagar, new XYConstraints(300, 9, 100, 20));

        pnlTotalesT.add(lblDsctoPorc, new XYConstraints(545, 5, 15, 15));
        pnlTotalesT.add(lblTotalesT, new XYConstraints(495, 5, 15, 15));
        pnlTotalesT.add(lblBrutoT, new XYConstraints(510, 5, 15, 15));
        pnlTotalesT.add(lblBruto, new XYConstraints(525, 5, 15, 15));
        pnlTotalesT.add(lblIGVT, new XYConstraints(65, 5, 45, 15));
        pnlTotalesT.add(lblIGV, new XYConstraints(120, 5, 95, 15));
        scrProductos.getViewport();
        pnlProductos.add(lblMedico, new XYConstraints(230, 0, 485, 25));
        pnlProductos.add(pnlAtencion1, new XYConstraints(10, 45, 715, 35));
        pnlProductos.add(btnRelacionProductos,
                         new XYConstraints(10, 5, 145, 15));
        pnlProductos.add(lblItemsT, new XYConstraints(185, 5, 40, 15));
        pnlProductos.add(lblItems, new XYConstraints(150, 5, 30, 15));
        pnlAtencion.add(lblDNI_SIN_COMISION,
                        new XYConstraints(384, -1, 330, 30));
        pnlAtencion.add(lblFormaPago, new XYConstraints(454, -1, 260, 30));
        pnlAtencion.add(lblUltimoPedido, new XYConstraints(655, 5, 40, 15));
        pnlAtencion.add(lblUltimoPedidoT, new XYConstraints(585, 5, 70, 15));
        pnlAtencion.add(lblVendedor, new XYConstraints(244, 4, 60, 15));
        pnlAtencion.add(lblNombreVendedor, new XYConstraints(304, 4, 140, 15));
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
        jContentPane.add(lblMensajeCupon, null);
        jContentPane.add(lblF10, null);
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
    }
    // **************************************************************************
    // Método "jbInitBTLMF()"
    // **************************************************************************

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInitBTLMF(){

    	System.out.println("jbInitBTLMF");
    	lblF2.setVisible(false);
    	lblF6.setVisible(false);
    	lblF12.setVisible(false);
    	lblF8.setVisible(false);
    	lblF9.setVisible(false);
    	lblF10.setVisible(false);


        this.setSize(new Dimension(950, 583));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Resumen de Pedido" + " /  IP : " +
                      FarmaVariables.vIpPc);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setBounds(new Rectangle(10, 10, 765, 583));
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
        pnlTotalesD.setBounds(new Rectangle(10, 390, 740, 35));
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
        lblCreditoT.setBounds(new Rectangle(195, 360, 120, 20));
        lblCredito.setText("");
        lblCredito.setVisible(false);
        lblCredito.setFont(new Font("SansSerif", 1, 14));
        lblCredito.setForeground(new Color(43, 141, 39));
        lblCredito.setBounds(new Rectangle(330, 360, 75, 20));
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
        pnlTotalesT.setBounds(new Rectangle(520, 360, 230, 25));
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
        scrProductos.setBounds(new Rectangle(10, 145, 740, 210));
        scrProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setFont(new Font("SansSerif", 0, 12));
        pnlProductos.setLayout(xYLayout2);
        pnlProductos.setBackground(new Color(255, 130, 14));
        pnlProductos.setBounds(new Rectangle(10, 115, 740, 25));
        btnRelacionProductos.setText("Relacion de Productos :");
        btnRelacionProductos.setFont(new Font("SansSerif", 1, 11));
        btnRelacionProductos.setForeground(Color.white);
        btnRelacionProductos.setBackground(new Color(255, 130, 14));
        btnRelacionProductos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0,
                                                                       0));
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
        pnlAtencion.setBounds(new Rectangle(10, 80, 740, 30));
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
        lblF3.setText("[ F3 ]  Agregar Prod");
        lblF3.setBounds(new Rectangle(165, 440, 125, 20));
        lblEnter.setText("[ ENTER ]  Cambiar Cantidad");
        lblEnter.setBounds(new Rectangle(460, 440, 180, 20));
        lblF5.setText("[ F5 ]  Borrar Producto");
        lblF5.setBounds(new Rectangle(310, 440, 135, 20));
        lblF1.setText("[ F1 ]  Cobrar Pedido");
        lblF1.setBounds(new Rectangle(10, 440, 140, 20));
        lblF8.setText("[ F8 ] Dcto por Receta");
        lblF8.setBounds(new Rectangle(240, 465, 140, 20));

        tblProductos.removeAll();

        tblProductos.setFont(new Font("SansSerif", 0, 12));
        tblProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblProductos_keyPressed(e);
                    }
                });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(660, 440, 90, 20));
        pnlTitle1.setBounds(new Rectangle(10, 5, 740, 70));

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
        lblF9.setBounds(new Rectangle(400, 465, 150, 20));
        // lblF9.setText("[ F9 ] Asociar Campaña");
        //lblF9.setText("[ F9 ] Ver Campañas");
        lblF9.setText("[ F9 ] Camp. Acumulada");
        lblF10.setBounds(new Rectangle(570, 465, 120, 20));
        lblF10.setText("[ F10 ] Ver Receta");
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
        jScrollPane1.setBounds(new Rectangle(5, 490, 745, 65));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jLabel1.setText("Opciones");
        jLabel1.setBounds(new Rectangle(15, 420, 70, 20));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        lblCuponIngr.setText("-");
        lblCuponIngr.setFont(new Font("SansSerif", 1, 12));
        lblCuponIngr.setBounds(new Rectangle(165, 475, 560, 25));
        lblCuponIngr.setToolTipText("null");
        pnlTotalesT1.setFont(new Font("SansSerif", 0, 12));
        pnlTotalesT1.setBackground(Color.white);
        pnlTotalesT1.setLayout(xYLayout8);


        pnlTotalesT1.setBounds(new Rectangle(35, 155, 715, 210));
        jPanelHeader1.setBounds(new Rectangle(320, 0, 420, 70));
        lblDNI_Anul.setBackground(Color.white);
        lblDNI_Anul.setFont(new Font("Dialog", 1, 14));
        lblDNI_Anul.setForeground(Color.red);
        lblDNI_Anul.setVisible(false);
        lblTopeAhoro.setForeground(Color.red);
        lblTopeAhoro.setFont(new Font("Dialog", 1, 12));
        lblF7.setBounds(new Rectangle(120, 465, 95, 20));
        lblF7.setText("[ F7 ]  Info Prod");
        //lblFormaPago.setOpaque(true);
        lblFormaPago.setText("kokokokokoko okokoko okokok ooko okokoko okoko");
        lblFormaPago.setFont(new Font("SansSerif", 1, 12));
        lblFormaPago.setForeground(Color.red);
        lblFormaPago.setVisible(false);
        pnlAtencion1.add(lblUltimoPedido1, new XYConstraints(655, 10, 40, 15));
        pnlAtencion1.add(lblUltimoPedidoT1,
                         new XYConstraints(585, 10, 70, 15));
        pnlAtencion1.add(lblVendedor1, new XYConstraints(245, 10, 70, 15));
        pnlAtencion1.add(lblNombreVendedor1,
                         new XYConstraints(315, 10, 245, 15));
        pnlAtencion1.add(lblTipoCambio1, new XYConstraints(205, 10, 40, 15));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));
        //pnlTotalesT.add(lblDsctoPorc, new XYConstraints(90, 5, 15, 15));

        pnlAtencion1.add(lblFecha1, new XYConstraints(60, 10, 70, 15));
        pnlAtencion1.add(lblTipoCambioT1, new XYConstraints(130, 10, 80, 15));
        pnlAtencion1.add(lblFechaT1, new XYConstraints(10, 10, 50, 15));
        pnlTotalesD.add(lblTotalD, new XYConstraints(615, 5, 80, 20));
        pnlTotalesD.add(lblTotalD, new XYConstraints(619, 9, 80, 20));
        pnlTotalesD.add(lblTotalS, new XYConstraints(464, 9, 95, 20));
        pnlTotalesD.add(lblTotalDT, new XYConstraints(579, 9, 35, 20));
        pnlTotalesD.add(lblTotalST, new XYConstraints(379, 9, 80, 20));
        pnlTotalesD.add(lblRedondeoT, new XYConstraints(4, 9, 70, 20));

        pnlTotalesD.add(lblRedondeo, new XYConstraints(79, 9, 65, 20));
        pnlTotalesD.add(lblPorPagarT, new XYConstraints(194, 9, 100, 20));
        pnlTotalesD.add(lblPorPagar, new XYConstraints(300, 9, 100, 20));
        pnlTotalesT.add(lblDsctoPorc, new XYConstraints(545, 5, 15, 15));
        pnlTotalesT.add(lblTotalesT, new XYConstraints(495, 5, 15, 15));
        pnlTotalesT.add(lblBrutoT, new XYConstraints(510, 5, 15, 15));
        pnlTotalesT.add(lblBruto, new XYConstraints(525, 5, 15, 15));
        pnlTotalesT.add(lblIGVT, new XYConstraints(65, 5, 45, 15));
        pnlTotalesT.add(lblIGV, new XYConstraints(120, 5, 95, 15));
        scrProductos.getViewport();
        pnlProductos.add(pnlAtencion1, new XYConstraints(10, 45, 715, 35));
        pnlProductos.add(btnRelacionProductos,
                         new XYConstraints(10, 5, 145, 15));
        pnlProductos.add(lblItemsT, new XYConstraints(185, 5, 40, 15));
        pnlProductos.add(lblItems, new XYConstraints(150, 5, 30, 15));
        pnlAtencion.add(lblFormaPago, new XYConstraints(454, -1, 260, 30));
        pnlAtencion.add(lblUltimoPedido, new XYConstraints(655, 5, 40, 15));
        pnlAtencion.add(lblUltimoPedidoT, new XYConstraints(585, 5, 70, 15));
        pnlAtencion.add(lblVendedor, new XYConstraints(244, 4, 60, 15));
        pnlAtencion.add(lblNombreVendedor, new XYConstraints(304, 9, 140, 15));
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
        jContentPane.add(lblF10, null);
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
        tableModelResumenPedido =
                new FarmaTableModel(ConstantsVentas.columnsListaResumenPedido,
                                    ConstantsVentas.defaultValuesListaResumenPedido,
                                    0);
        FarmaUtility.initSimpleList(tblProductos, tableModelResumenPedido,
                                    ConstantsVentas.columnsListaResumenPedido);
    }

    private void cargaLogin() {
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
                    System.out.println("");
                    cerrarVentana(false);
                } else {
                    //FarmaVariables.dlgLogin = dlgLogin;

                    log.info("******* 2 *********");
                    log.info("Usuario: " + FarmaVariables.vIdUsu);
                    muestraMensajeUsuario();
                    FarmaVariables.vAceptar = false;

                    agregarProducto();
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
        //JCHAVEZ 08102009.sn
        try {
            lblF7.setVisible(DBVentas.getIndVerCupones());


        } catch (SQLException ex) {
            lblF7.setVisible(false);
            ex.printStackTrace();
        }
        //JCHAVEZ 08102009.en

        // Inicio Adicion Delivery 28/04/2006 Paulo
        //if(FarmaVariables.vAceptar)
        //{
        // String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
        // lblCliente.setText(nombreCliente);
        // FarmaVariables.vAceptar = false ;
        // }
        // Fin Adicion Delivery 28/04/2006 Paulo
        FarmaUtility.centrarVentana(this);
        obtieneInfoPedido();
        lblFecha.setText(fechaPedido);
        lblTipoCambio.setText(FarmaUtility.formatNumber(FarmaVariables.vTipCambio));
        VariablesCaja.vVerificaCajero = true;
        cargaLogin();

        //verificaRolUsuario();
        //agregarProducto();
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


        //JCORTEZ 17.04.08
        lblTotalesT.setVisible(false);
        lblBrutoT.setVisible(false);
        lblBruto.setVisible(false);
        //lblDsctoT.setVisible(false);
        //lblDscto.setVisible(false);
        lblDsctoPorc.setVisible(false);

        //JCORTEZ 23.07.2008
        lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);


    }

    private void tblProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        //FarmaUtility.moveFocus(tblProductos);
        FarmaUtility.moveFocus(txtDescProdOculto);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this,
                                 "Debe presionar la tecla ESC para cerrar la ventana.",
                                 null);
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
                //System.out.println("e.getKeyCode() presionado:"+e.getKeyCode());
                //System.out.println("e.getKeyChar() presionado:"+e.getKeyChar());
                vEjecutaAccionTeclaResumen = true;
                System.out.println(" try: " + vEjecutaAccionTeclaResumen);
                if (Character.isLetter(e.getKeyChar())) {
                    //System.out.println("Presiono una letra");
                    //vEjecutaAccionTeclaResumen = false;
                    if (VariablesVentas.vKeyPress == null) {
                        //VariablesVentas.vLetraBusqueda = e.getKeyChar() + "";;
                        //System.out.println("VariablesVentas.vLetraBusqueda  " + VariablesVentas.vLetraBusqueda);
                        VariablesVentas.vKeyPress = e;

                        agregarProducto();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    //vEjecutaAccionTeclaResumen = false;
                    evaluaIngresoCantidad();

                } else if (e.getKeyCode() == KeyEvent.VK_F8) {
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
                     if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null))
	               	 {
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

                } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                    //vEjecutaAccionTeclaResumen = false;

                    if (!VariablesVentas.vProductoVirtual) {

                        agregarProducto();
                    } else {
                        System.out.println("error de producto virtual marco");
                        FarmaUtility.showMessage(this,
                                                 "Ya se selecciono un producto virtual",
                                                 txtDescProdOculto);
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                    //vEjecutaAccionTeclaResumen = false;
                    //validaConvenio(e, VariablesConvenio.vPorcCoPago);
                    //JMIRANDA 23.06.2010
                    //NUEVO VALIDA CONVENIO
                    /*if(cargaLogin_verifica())
                    {*/
                    lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() +
                                              " " +
                                              FarmaVariables.vPatUsu.trim() +
                                              " " +
                                                  FarmaVariables.vMatUsu.trim());
                        // Inicio Adicion Delivery 28/04/2006 Paulo
                        //String nombreCliente = VariablesDelivery.vNombreCliente + " " +VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
                        //lblCliente.setText(nombreCliente);
                        // Fin Adicion Delivery 28/04/2006 Paulo
                        colocaUltimoPedidoDiarioVendedor();
                        //FarmaUtility.moveFocus(tblProductos);
                        FarmaUtility.moveFocus(txtDescProdOculto);

                    validaConvenio_v2(e, VariablesConvenio.vPorcCoPago);
                    FarmaUtility.moveFocus(txtDescProdOculto);
                    //}
                } else if (e.getKeyCode() == KeyEvent.VK_F1) {
                    //vEjecutaAccionTeclaResumen = false;
                   /* if(cargaLogin_verifica())

                    {*/
                    //mfajardo 29/04/09 validar ingreso de productos virtuales
                    VariablesVentas.vProductoVirtual = false;

                    //validaConvenio(e, VariablesConvenio.vPorcCoPago);
                   if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.length() > 0)
                   {
                	  boolean result = true;
                	  if (VariablesConvenioBTLMF.vFlgValidaLincreBenef != null && VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
                	  result = existeSaldoCredDispBenif(this);

                           if(result)  validaConvenio_v2(e, VariablesConvenio.vPorcCoPago);
                   }
                   else
                   {
                    validaConvenio_v2(e, VariablesConvenio.vPorcCoPago);
                   }



                    FarmaUtility.moveFocus(txtDescProdOculto);//}
                } //JCORTEZ 17.04.08
                else if (e.getKeyCode() == KeyEvent.VK_F6) {
                    //vEjecutaAccionTeclaResumen = false;
                	if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                		VariablesConvenioBTLMF.vCodConvenio != null &&
                		VariablesConvenioBTLMF.vCodConvenio.length() > 0)
                	{
                	  //No se hace nada.
                	}
                	else
                	{
                    mostrarFiltro();
                	}
                } else if (e.getKeyCode() ==
                           KeyEvent.VK_F9) {



                	//Agregado por FRAMIREZ 04.05.2012 descativa para el convenio BTLMF
                	 if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null))
                	 {
			                	//add jcallo 15/12/2008 campanias acumuladas.
                    //veririficar que el producto seleccionado tiene el flag de campanias acumuladas.
                    //validar que no sea un pedido por convenio
                    //log.debug("tecleo f9");
                    //vEjecutaAccionTeclaResumen = false;
                    if (VariablesVentas.vEsPedidoConvenio) {
                        FarmaUtility.showMessage(this,
                                                 "No puede asociar clientes a campañas de ventas acumuladas en un " +
                                                 "pedido por convenio.",
                                                 txtDescProdOculto);
                    } else { //toda la logica para asociar un cliente hacia campañas nuevas
                        //DUBILLUZ - 29.04.2010
                        if (VariablesFidelizacion.vDniCliente.trim().length() >
                            0) {
                            int rowSelec = tblProductos.getSelectedRow();
                            String auxCodProd = "";
                            if (rowSelec >=
                                0) { //validar si el producto seleccionado tiene alguna campaña asociada
                                auxCodProd =
                                        tblProductos.getValueAt(rowSelec, 0).toString().trim();
                            }
                            asociarCampAcumulada(auxCodProd);
                            //se agrego el metodo opera resumen pedido para aplicar las campanas de fidelizacion

                            //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
                            neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009
                            //FarmaUtility.setearPrimerRegistro(tblProductos,null,0);
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "No puede ver las campañas:\n" +
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

                } else if (e.getKeyCode() == KeyEvent.VK_F10) {
                    //vEjecutaAccionTeclaResumen = false;
                	//Agregado por FRAMIREZ 04.05.2012 descativa para el convenio BTLMF
	               	 if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null))
	               	 {
                    verReceta();
	               	 }

                    FarmaUtility.moveFocus(txtDescProdOculto);
                } else if (e.getKeyCode() == KeyEvent.VK_F12) {
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
                    funcionF12("N");


                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    //vEjecutaAccionTeclaResumen = false;
                    if (FarmaUtility.rptaConfirmDialog(this,"Está seguro que Desea salir del pedido?"))
                    {
	                    //Agregado por FRAMIREZ 27.03.2012
	                    VariablesConvenioBTLMF.limpiaVariablesBTLMF();

                        cancelaOperacion_02();
                VariablesVentas.vCodProdBusq="";
                VariablesVentas.vCodBarra="";

                    //mfajardo 29/04/09 validar ingreso de productos virtuales
                    VariablesVentas.vProductoVirtual = false;
                    //jquispe 13.01.2011
                    FarmaVariables.vAceptar = false;
                    VariablesCaja.vVerificaCajero = false;
                    }

                } else if (e.getKeyCode() ==
                           KeyEvent.VK_INSERT) { //Inicio ASOSA 03.02.2010
                    VariablesVentas.vIndPrecioCabeCliente = "S";
                    DlgListaProdDIGEMID objDIGEMID =
                        new DlgListaProdDIGEMID(myParentFrame, "", true);
                    objDIGEMID.setVisible(true);
                    cancelaOperacion_02();

                    //mfajardo 29/04/09 validar ingreso de productos virtuales
                    VariablesVentas.vProductoVirtual = false;
                    cerrarVentana(true);
                } //Fin ASOSA 03.02.2010
                //vEjecutaAccionTeclaResumen = false;

                //pruebas de validacion
                //int i=1/0;
                //if(true)
                // return;
            }
        } //try
        catch (Exception exc) {
        	exc.printStackTrace();
            System.out.println("catch" + vEjecutaAccionTeclaResumen);
        } finally {
            vEjecutaAccionTeclaResumen = false;
            //System.out.println(" finally: " + vEjecutaAccionTeclaResumen);
        }
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
        //JMIRANDA 19.08.2010 OBS
        UtilityNewCobro.inicializarVariables();
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void agregarProducto() {
        System.out.println("Entro aca :::: 23.04.2010");
        //FarmaUtility.moveFocus(tblProductos);

        if (VariablesVentas.vCodFiltro.equalsIgnoreCase(ConstantsVentas.IND_OFER)) {
            String vkF = "F6";
            agregarComplementarios(vkF);
        } else {
            DlgListaProductos dlgListaProductos =
                new DlgListaProductos(myParentFrame, "", true);
            dlgListaProductos.setVisible(true);
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
                    /*if(FarmaUtility.rptaConfirmDialog(this,"¿Desea agregar más productos al pedido?"))
            {
            agregarProducto();
            }*/
                }
            }
        }

        txtDescProdOculto.setText("");
        VariablesVentas.vCodFiltro = "";
        VariablesVentas.vIndF11 = false;
    }

    /**
     *@deprecated
     * */
    private void operaResumenPedido() {


        // borramos todo de la tabla
        //Inicio
        while (tableModelResumenPedido.getRowCount() > 0)
            tableModelResumenPedido.deleteRow(0);

        tableModelResumenPedido.fireTableDataChanged();
        //Fin
        //19.02.2009
        // tableModelResumenPedido.clearTable();

        //Normal
        tblProductos.repaint();


        // cargamos los productos desde el ArrayList de Productos
        String prodVirtual = FarmaConstants.INDICADOR_N;
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            tableModelResumenPedido.insertRow((ArrayList)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).clone());
            tableModelResumenPedido.fireTableDataChanged();
            tblProductos.setValueAt(" ", i,
                                    COL_RES_DSCTO); //inicializando la columna de descuestos en blanco
            prodVirtual =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 14);
            if (prodVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                VariablesVentas.vIndPedConProdVirtual = true;
        }
        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size();
             i++) {
            tableModelResumenPedido.insertRow((ArrayList)VariablesVentas.vArrayList_Promociones.get(i));
            tableModelResumenPedido.fireTableDataChanged();
            //System.out.println("Pedido : " + VariablesVentas.vArrayList_Promociones);

            prodVirtual =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 14);
            if (prodVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                VariablesVentas.vIndPedConProdVirtual = true;
        }

        tblProductos.repaint();

        /***todo el tema de opera resumen campañas deberia ser al momento de buscar DNI por la opcion F12
     * O POR LA OPCION DE INGRESAR EL NUMERO DE TARJETA
     * **/

        //jcallo quitar las campañas que ya han terminado de ser usados por el cliente
        log.debug("quitando las campañas limitadas en numeros de usos del cliente");
        log.debug("VariablesVentas.vArrayList_Cupones:" +
                  VariablesVentas.vArrayList_Cupones);
        log.debug("VariablesVentas.vArrayList_CampLimitUsadosMatriz:" +
                  VariablesVentas.vArrayList_CampLimitUsadosMatriz);
        for (int i = 0;
             i < VariablesVentas.vArrayList_CampLimitUsadosMatriz.size();
             i++) {
            String cod_camp_limit =
                VariablesVentas.vArrayList_CampLimitUsadosMatriz.get(i).toString().trim(); //por culpa de diego
            for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size();
                 j++) {
                String cod_camp_cupon =
                    ((Map)VariablesVentas.vArrayList_Cupones.get(j)).get("COD_CAMP_CUPON").toString();
                if (cod_camp_limit.equals(cod_camp_cupon)) {
                    log.debug("quitando cupon que ya no deberia de aplicar");
                    VariablesVentas.vArrayList_Cupones.remove(j);
                    break;
                }
            }
        }

        log.debug("VariablesVentas.vArrayList_Cupones:" +
                  VariablesVentas.vArrayList_Cupones);

        calculaTotalesPedido(); //dentro de esto esta el aplicar los dctos por campanias cupon



        System.out.println(">>>>>>>>VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF: " + VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);

        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this,null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
        {
        	lblCliente.setText(VariablesConvenioBTLMF.vNomCliente);
            this.setTitle("Resumen de Pedido - Pedido por Convenio: " +
                          VariablesConvenioBTLMF.vNomConvenio + " /  IP : " +
                          FarmaVariables.vIpPc);
            System.out.println("------------------------" + this.getTitle());
            System.out.println(VariablesConvenioBTLMF.vCodConvenio+"VariablesConvenio.vTextoCliente : *****" +
                               VariablesConvenioBTLMF.vNomCliente);


            System.out.println("VariablesConvenioBTLMF.vCodConvenio:"+VariablesConvenioBTLMF.vCodConvenio);
            System.out.println("VariablesConvenioBTLMF.vDatoLCredSaldConsumo:"+VariablesConvenioBTLMF.vDatoLCredSaldConsumo);

            lblLCredito_T.setText(VariablesConvenioBTLMF.vDatoLCredSaldConsumo);
            lblBeneficiario_T.setText(getMensajeComprobanteConvenio(VariablesConvenioBTLMF.vCodConvenio));

        }
        else
        {
         evaluaTitulo(); //titulo y datos dependiendo del tipo de pedido que se este haciendo
        }
        FarmaUtility.setearPrimerRegistro(tblProductos, null, 0);
    }

    private void muestraDetalleProducto() {
        if (tblProductos.getRowCount() == 0)
            return;
        VariablesVentas.vCod_Prod =
                ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),
                                                  0))).trim();
        VariablesVentas.vDesc_Prod =
                ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),
                                                  1))).trim();
        VariablesVentas.vNom_Lab =
                ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),
                                                  9))).trim();
        DlgDetalleProducto dlgDetalleProducto =
            new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);
    }

    private void cancelaOperacion() {
        String codProd = "";
        String cantidad = "";
        String indControlStk = "";
        String secRespaldo = ""; //ASOSA, 02.07.2010
        for (int i = 0; i < tblProductos.getRowCount(); i++) {
            codProd = ((String)(tblProductos.getValueAt(i, 0))).trim();
            VariablesVentas.vVal_Frac =
                    FarmaUtility.getValueFieldJTable(tblProductos, i, 10);
            cantidad = ((String)(tblProductos.getValueAt(i, 4))).trim();
            indControlStk = ((String)(tblProductos.getValueAt(i, 16))).trim();
            secRespaldo =
                    (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(26); //ASOSA, 02.07.2010
            VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
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
                    0, ConstantsVentas.INDICADOR_D,
                    ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, true,
                    this, tblProductos, secRespaldo))
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
        cantidad =
                ""; //((String)(tblProductos.getValueAt(filaActual,4))).trim();
        indControlStk =
                ""; // ((String)(tblProductos.getValueAt(filaActual,16))).trim();
        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size();
             i++) {
            agrupado = new ArrayList();
            aux = (ArrayList)(VariablesVentas.vArrayList_Promociones.get(i));
            codProm = ((String)(aux.get(0))).trim();
            agrupado =
                    detalle_Prom(VariablesVentas.vArrayList_Prod_Promociones,
                                 codProm);
            System.out.println("AAAAAAAAAAPRODSDSDSDSDSDS: " + agrupado);
            agrupado = agrupar(agrupado);
            for (int j = 0; j < agrupado.size();
                 j++) //VariablesVentas.vArrayList_Prod_Promociones.size(); j++)
            {
                aux =
(ArrayList)(agrupado.get(j)); //VariablesVentas.vArrayList_Prod_Promociones.get(j));
                //if((((String)(aux.get(18))).trim()).equalsIgnoreCase(codProm)){
                codProd = ((String)(aux.get(0))).trim();
                cantidad = ((String)(aux.get(4))).trim();
                VariablesVentas.vVal_Frac = ((String)(aux.get(10))).trim();
                indControlStk = ((String)(aux.get(16))).trim();
                secRespaldo =
                        ((String)(aux.get(24))).trim(); //ASOSA, 08.07.2010
                VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(codProd,Integer.parseInt(cantidad),ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad), //Antes, ASOSA, 08.07.2010
                                                       false,
                                                       this,
                                                       tblProductos))*/
                    !UtilityVentas.operaStkCompProdResp(codProd,
                                                        //ASOSA, 08.07.2010
                        0, ConstantsVentas.INDICADOR_D,
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0,
                        false, this, tblProductos, secRespaldo))
                    return;
                //}
            }

        }
        FarmaUtility.aceptarTransaccion();
        inicializaArrayList();
        //jcallo: el parametro estaba en false--> se cambio a true
        cerrarVentana(true);
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


    	if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this,null) && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0)
    	{

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
            log.debug("ERROR LISTA CERO SUPUESTAMENTE NO HAY PRODUCTOSSS ..... :p:p");
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
        
        log.debug("LISTADO DE PRODUCTOS RESUMEN " + 
                  VariablesVentas.vArrayList_ResumenPedido);
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            log.debug("--------------------------------------------------------------------");
            log.debug("VariablesVentas.vArrayList_ResumenPedido.get(i):" +
                      VariablesVentas.vArrayList_ResumenPedido.get(i));
            cantidad =
                    Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(4));
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
                if ((getValueColumna(VariablesVentas.vArrayList_ResumenPedido,i,0)).trim().equalsIgnoreCase(pCod)){
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
        log.debug("LISTADO DE PRODUCTOS PROMOCION: " +
                  VariablesVentas.vArrayList_Promociones);
        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size();
             i++) {
            log.debug("--------------------------------------------------------------------");
            log.debug("VariablesVentas.vArrayList_Promociones.get(i):" +
                      VariablesVentas.vArrayList_Promociones.get(i));
            cantidad =
                    Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(4));
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

        log.debug("totalBruto:" + totalBruto);
        //redondeo total bruto a 2 cifras
        totalBrutoRedondeado = UtilityVentas.Redondear(totalBruto, 2);
        log.debug("redondeando a 2 cifras totalBrutoRedondeado:" +
                  totalBrutoRedondeado);
        //total bruto a 2 cifras decimales a favor del cliente multiplo de .05
        totalBrutoRedondeado =
                UtilityVentas.ajustarMonto(totalBrutoRedondeado, 2);
        log.debug("ajustando monto a 2 cifras totalBrutoRedondeado:" +
                  totalBrutoRedondeado);
        
        /// excluyente
        log.debug("totalBruto_excluye:" + totalBruto_excluye);
        //redondeo total bruto a 2 cifras
        totalBrutoRedondeado_excluye = UtilityVentas.Redondear(totalBruto_excluye, 2);
        log.debug("redondeando a 2 cifras totalBrutoRedondeado:" + 
                  totalBrutoRedondeado_excluye);
        //total bruto a 2 cifras decimales a favor del cliente multiplo de .05
        totalBrutoRedondeado_excluye = 
                UtilityVentas.ajustarMonto(totalBrutoRedondeado_excluye, 2);
        log.debug("ajustando monto a 2 cifras totalBrutoRedondeado:" + 
                  totalBrutoRedondeado_excluye);        
        

        log.debug("totalNeto:" + totalNeto);
        //redondeo total neto a 2 cifras
        totalNetoRedondeado =
                UtilityVentas.Redondear(totalNeto, 2); //redondeo a 2 cifras pero no a ajustado a .05 o 0.00
        log.debug("redondeando a 2 cifras totalNetoRedondeado:" +
                  totalNetoRedondeado);
        //total neto a 2 cifras decimales a favor del cliente multiplo de .05
        System.err.println("ajustando monto a 2 cifras totalNetoRedondeado:" +
                           totalNetoRedondeado);
        System.err.println("ajustando monto a 2 cifras totalNetoRedondeado 2:" +
                           UtilityVentas.ajustarMonto(totalNetoRedondeado, 3));

        log.debug("totalNeto_excluye:" + totalNeto_excluye);
        //redondeo total neto a 2 cifras
        totalNetoRedondeado_excluye = 
                UtilityVentas.Redondear(totalNeto_excluye, 2); //redondeo a 2 cifras pero no a ajustado a .05 o 0.00
        log.debug("redondeando a 2 cifras totalNetoRedondeado:" + 
                  totalNetoRedondeado_excluye);
        //total neto a 2 cifras decimales a favor del cliente multiplo de .05
        System.err.println("ajustando monto a 2 cifras totalNetoRedondeado:" + 
                           totalNetoRedondeado_excluye);
        System.err.println("ajustando monto a 2 cifras totalNetoRedondeado 2:" + 
                           UtilityVentas.ajustarMonto(totalNetoRedondeado_excluye, 3));
        


        //totalNetoRedondeado = UtilityVentas.ajustarMonto(totalNetoRedondeado, 3);
        double totalNetoRedNUEVO =
            UtilityVentas.ajustarMonto(totalNetoRedondeado, 3);
        totalNetoRedondeado =
                UtilityVentas.ajustarMonto(totalNetoRedondeado, 2);
        System.err.println("ajustando monto a 2 cifras totalNetoRedondeado:" +
                           totalNetoRedondeado);

        redondeo = totalNetoRedondeado - totalNeto;
        System.err.println("totalBrutoRedondeado:" + totalBrutoRedondeado);
        System.err.println("totalNetoRedondeado:" + totalNetoRedondeado);
        
        /////////////////////////////////////////////////////////////////////////////////////
        double totalNetoRedNUEVO_excluye = 
            UtilityVentas.ajustarMonto(totalNetoRedondeado_excluye, 3);
        totalNetoRedondeado_excluye = 
                UtilityVentas.ajustarMonto(totalNetoRedondeado_excluye, 2);
        System.err.println("ajustando monto a 2 cifras totalNetoRedondeado_excluye:" + 
                           totalNetoRedondeado_excluye);

        redondeo_excluye = totalNetoRedondeado_excluye - totalNeto_excluye;
        System.err.println("totalBrutoRedondeado_excluye:" + totalBrutoRedondeado_excluye);
        System.err.println("totalNetoRedondeado_excluye:" + totalNetoRedondeado_excluye);        
        
        /////////////////////////////////////////////////////////////////////////////////////
        //mfajardo 18.05.09 : si es convenio no debe mostrar ahorro
        if (!VariablesVentas.vEsPedidoConvenio) {
            //totalAhorro = (totalBrutoRedondeado - totalNetoRedondeado);
            totalAhorro = (totalBrutoRedondeado - totalNetoRedNUEVO);
        }
        System.err.println("totalAhorro:" + totalAhorro);
        System.err.println("totalBruto:" + totalBruto);
        System.err.println("totalAhorro_excluye:" + totalAhorro_excluye);
        System.err.println("totalBruto_excluye:" + totalBruto_excluye);        
        
        totalDscto = 
                UtilityVentas.Redondear((totalAhorro * 100.00) / totalBruto, 
                                        2);
        totalDscto_excluye = 
                UtilityVentas.Redondear((totalAhorro_excluye * 100.00) / totalBruto_excluye, 
                                        2);
        System.err.println("totalDscto:" + totalDscto);
        System.err.println("totalDscto_excluye:" + totalDscto_excluye);
        
        log.debug("========TOTALES==========");
        log.debug("totalBruto:" + totalBruto);
        log.debug("totalNeto:" + totalNeto);
        log.debug("totalIGV:" + totalIGV);
        log.debug("totalBrutoRedondeado:" + totalBrutoRedondeado);
        log.debug("totalNetoRedondeado:" + totalNetoRedondeado);
        log.debug("totalAhorro:" + totalAhorro);
        log.debug("totalDscto:" + totalDscto);
        log.debug("redondeo:" + redondeo);
        log.debug("========       ==========");

        log.debug("========TOTALES EXCLUYE==========");
        log.debug("totalBruto_excluye:" + totalBruto_excluye);
        log.debug("totalNeto_excluye:" + totalNeto_excluye);
        log.debug("totalIGV_excluye:" + totalIGV_excluye);
        log.debug("totalBrutoRedondeado_excluye:" + totalBrutoRedondeado_excluye);
        log.debug("totalNetoRedondeado_excluye:" + totalNetoRedondeado_excluye);
        log.debug("totalAhorro_excluye:" + totalAhorro_excluye);
        log.debug("totalDscto_excluye:" + totalDscto_excluye);
        log.debug("redondeo_excluye:" + redondeo_excluye);
        log.debug("========       ==========");        

        //Se verifica el ahorro que se obtiene este ahorro no debe de exceder al Maximo
        //DUBILLUZ 28.05.2009
        System.err.println("totalAhorro old:" + totalAhorro);
        System.err.println("totalAhorro_excluye old:" + totalAhorro_excluye);
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
                    USARSE PARA ACUMULAR EL AHORRO. PERO SE PIDIO MOSTRAR TODO EL AHORRO.
                    totalAhorro = 
                            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo - 
                            VariablesFidelizacion.vAhorroDNI_x_Periodo;
                    */
                    pLlegoTopeDscuento = true;
                }
                System.err.println("totalAhorro new:" + totalAhorro);
                //ya no se muestra el total bruto
                //por si algun dia quiera volver mostrar
                lblBruto.setText(FarmaUtility.formatNumber(totalBrutoRedondeado));
                //jcallo 02.10.2008 se modifico por el tema del texto de ahorro
                System.err.println("pLlegoTopeDscuento:" + pLlegoTopeDscuento);
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
            log.debug("procentaje de dcto total" +
                      FarmaUtility.getDecimalNumber(lblDsctoPorc.getText().trim()));
            lblDsctoPorc.setVisible(true);
        }

        lblIGV.setText(FarmaUtility.formatNumber(totalIGV));

        lblPorPagarT.setVisible(false);
        lblPorPagar.setVisible(false);
        lblCredito.setVisible(false);
        lblCreditoT.setVisible(false);

        VariablesConvenioBTLMF.vImpSubTotal = totalNetoRedondeado;

        //Agregado por FRAMIREZ 26.03.2012 Calcula El monto de credito de tipo convenio COPAGO.
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0)
        {



          double montoCredito = UtilityConvenioBTLMF.obtieneMontoCredito(this, null, new Double(totalNetoRedondeado),"",VariablesConvenioBTLMF.vCodConvenio);
          double porcentajeCredito = (montoCredito/totalNetoRedondeado)*100;

          System.out.println("porcentajeCredito:::"+porcentajeCredito);

          if(porcentajeCredito>0)
           {
        	  double montoaPagar = (totalNetoRedondeado-montoCredito);

              lblPorPagarT.setVisible(true);
              lblPorPagar.setVisible(true);
              lblCredito.setVisible(true);
              lblCreditoT.setVisible(true);

		      lblCredito.setText(FarmaUtility.formatNumber(montoCredito));
		      String porcCoPago = FarmaUtility.formatNumber((montoCredito/totalNetoRedondeado)*100,0);

		      if (montoaPagar == 0)
		      {
		        lblCreditoT.setText("Crédito("+porcCoPago+"%): S/.");
		        lblPorPagarT.setText("");
		        lblPorPagar.setText("");
		      }
		      else
		      {
		    	lblCreditoT.setText("Crédito("+porcCoPago+"%): S/.");
			    lblPorPagarT.setText("CoPago: S/.");
			    lblPorPagar.setText(FarmaUtility.formatNumber(montoaPagar));
		      }
           }
          else
           {
              lblPorPagarT.setVisible(false);
              lblPorPagar.setVisible(false);
              lblCredito.setVisible(false);
              lblCreditoT.setVisible(false);
          }
        }
        else
        {
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
        VariablesVentas.vCod_Prod =
                ((String)(tblProductos.getValueAt(vFila, 0))).trim();
        VariablesVentas.vDesc_Prod =
                ((String)(tblProductos.getValueAt(vFila, 1))).trim();
        VariablesVentas.vVal_Prec_Lista =
                ((String)(tblProductos.getValueAt(vFila, 3))).trim();
        VariablesVentas.vNom_Lab =
                ((String)(tblProductos.getValueAt(vFila, 9))).trim();
        VariablesVentas.vCant_Ingresada_Temp =
                ((String)(tblProductos.getValueAt(vFila, 4))).trim();
        VariablesVentas.vVal_Frac =
                ((String)(tblProductos.getValueAt(vFila, 10))).trim();
        VariablesVentas.vPorc_Igv_Prod =
                ((String)(tblProductos.getValueAt(vFila, 11))).trim();
        VariablesVentas.vPorc_Dcto_1 =
                ((String)(tblProductos.getValueAt(vFila, 5))).trim();
        System.err.println("((String)(tblProductos.getValueAt(vFila,5))).trim() : " +
                           ((String)(tblProductos.getValueAt(vFila,
                                                             5))).trim());
        VariablesVentas.vVal_Prec_Vta =
                ((String)(tblProductos.getValueAt(vFila, 6))).trim();
        //System.out.println("VariablesVentas.vPorc_Igv_Prod : " + VariablesVentas.vPorc_Igv_Prod);
        VariablesVentas.vIndOrigenProdVta =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila,
                                                 COL_RES_ORIG_PROD);
        VariablesVentas.vPorc_Dcto_2 = "0";
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";

        System.out.println("-------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------");
        System.out.println("-------------Metodo: muestraIngresoCantidad------------------");
        System.out.println("-------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------");


        System.out.println("VariablesVentas.vVal_Prec_Vta:"+VariablesVentas.vVal_Prec_Vta);


        //if (VariablesVentas.vEsPedidoConvenio) {
///////////////////////////////////////////////////////////////////////////////////////

            VariablesConvenio.vVal_Prec_Vta_Local =
                    ((String)(tblProductos.getValueAt(vFila, 6))).trim();
            VariablesConvenio.vVal_Prec_Vta_Conv =
                    VariablesVentas.vVal_Prec_Vta;
////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("VariablesConvenio.vVal_Prec_Vta_Local:"+VariablesConvenio.vVal_Prec_Vta_Local);
            System.out.println("VariablesConvenio.vVal_Prec_Vta_Conv:"+VariablesConvenio.vVal_Prec_Vta_Conv);

        //}
        DlgIngresoCantidad dlgIngresoCantidad =
            new DlgIngresoCantidad(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = true;
        dlgIngresoCantidad.setVisible(true);
        if (FarmaVariables.vAceptar) {
            seleccionaProducto(vFila);
            FarmaVariables.vAceptar = false;
            VariablesVentas.vIndDireccionarResumenPed = true;
        } else
            VariablesVentas.vIndDireccionarResumenPed = false;

    }

    /**
     * Muestra el Detalle de Promocion para su modificacion
     * @author : dubilluz
     * @since  : 25.06.2007
     */
    private void muestraDetallePromocion(int row) {
        VariablesVentas.vCodProm =
                FarmaUtility.getValueFieldJTable(tblProductos, row, 0);
        VariablesVentas.vDesc_Prom =
                FarmaUtility.getValueFieldJTable(tblProductos, row, 1);
        VariablesVentas.vCantidad =
                FarmaUtility.getValueFieldJTable(tblProductos, row, 4);
        VariablesVentas.accionModificar = true;
        System.out.println("****Codigo de Prom _antes del detalle : " +
                           VariablesVentas.vCodProm);
        DlgDetallePromocion dlgdetalleprom =
            new DlgDetallePromocion(myParentFrame, "", true);
        dlgdetalleprom.setVisible(true);
        //Se debe colocar false tanto la accion y todo
        //if(FarmaVariables.vAceptar){
        FarmaVariables.vAceptar = false;
        VariablesVentas.accionModificar = false;
        VariablesVentas.vCodProm = "";
        VariablesVentas.vDesc_Prom = "";
        VariablesVentas.vCantidad = "";
        //}
        System.out.println("Accion de Moficar" +
                           VariablesVentas.accionModificar);
        System.out.println("Cantidad de Promocion" +
                           VariablesVentas.vCantidad);

        //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
        neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009

    }

    private void seleccionaProducto(int pFila) {
        //    VariablesVentas.vTotalPrecVtaProd = (Double.parseDouble(VariablesVentas.vCant_Ingresada) * Double.parseDouble(VariablesVentas.vVal_Prec_Vta));
        VariablesVentas.vTotalPrecVtaProd =
                (FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada) *
                 FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta));
        String indicadorControlStock =
            FarmaUtility.getValueFieldJTable(tblProductos, pFila, 16);
        String secRespaldo = ""; //ASOSA, 02.07.2010
        secRespaldo =
                (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(pFila)).get(26); //ASOSA, 02.07.2010
        int cantIngresada =
            FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada));
        int cantIngresada_old =
            FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada_Temp));
        VariablesVentas.vVal_Frac =
                FarmaUtility.getValueFieldJTable(tblProductos, pFila,
                                                 COL_RES_VAL_FRAC);
        System.err.println("ANTES_RES_VariablesVentas.secRespStk:_"+VariablesVentas.secRespStk);
        if (indicadorControlStock.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (cantIngresada_old > cantIngresada) {
                VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
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
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR, 0,
                        true, this, tblProductos, secRespaldo))
                    return;
            } else if (cantIngresada_old < cantIngresada) {
                if (FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vStk_Prod)) ==
                    0)
                    FarmaUtility.showMessage(this,
                                             "No existe Stock disponible. Verifique!!!",
                                             tblProductos);
                else {
                    VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
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
                            ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                            0, true, this, tblProductos, secRespaldo))
                        return;
                }
            }
        }
        //liberando registros
        FarmaUtility.liberarTransaccion();
        System.err.println("Desp_RES_VariablesVentas.secRespStk:_"+VariablesVentas.secRespStk);
        //ERIOS 03.06.2008 Cuando se ingresa por tratamiento, el total es el calculado
        //y el precio de venta unitario se recalcula.
        double auxPrecVta =
            FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
        double auxCantIngr =
            FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);

        if (VariablesVentas.vIndTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            VariablesVentas.vTotalPrecVtaProd =
                    VariablesVentas.vTotalPrecVtaTra;
            VariablesVentas.vVal_Prec_Vta =
                    FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd /
                                              auxCantIngr, 3);
        } else if (!VariablesVentas.vEsPedidoConvenio &&
                   !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) //ERIOS 18.06.2008 Se redondea el total de venta por producto
        {
            //JCHAVEZ 29102009 redondeo inicio
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                try {
                    VariablesVentas.vIndAplicaRedondeo =
                            DBVentas.getIndicadorAplicaRedondedo();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                System.out.println("vIndAplicaRedondeo: " +
                                   VariablesVentas.vIndAplicaRedondeo);
                VariablesVentas.vTotalPrecVtaProd = (auxCantIngr * auxPrecVta);
                VariablesVentas.vVal_Prec_Vta =
                        FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd /
                                                  auxCantIngr, 3);
                System.out.println("VariablesVentas.vTotalPrecVtaProd : " +
                                   VariablesVentas.vTotalPrecVtaProd);
                System.out.println("VariablesVentas.vVal_Prec_Vta : " +
                                   VariablesVentas.vVal_Prec_Vta);
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
                double aux1 =
                    Math.ceil(Math.round(VariablesVentas.vTotalPrecVtaProd *
                                         100)) / 100; //0.01
                System.out.println("VariablesVentas.vTotalPrecVtaProd--------" +
                                   VariablesVentas.vTotalPrecVtaProd);
                double aux2 =
                    Math.ceil(Math.round(VariablesVentas.vTotalPrecVtaProd *
                                         100)) / 10; //0.1
                aux2 =
FarmaUtility.getDecimalNumber((FarmaUtility.formatNumber(aux2, 3)));
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
                        FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd /
                                                  auxCantIngr, 3);
            }


        }

        System.out.println("VariablesVentas.vArrayList_ResumenPedido 0 " +
                           VariablesVentas.vArrayList_ResumenPedido);
        //ESTO PONE DATOS EN EL JTABLE, cosa que estaria de mas
        operaProductoEnJTable(pFila);
        System.out.println("VariablesVentas.vArrayList_ResumenPedido 1 " +
                           VariablesVentas.vArrayList_ResumenPedido);
        //ESTO DE AQUI CAMBIO LOS DATOS EN EL ARRAYLIST DE RESUMEN PEDIDO
        operaProductoEnArrayList(pFila);
        System.out.println("VariablesVentas.vArrayList_ResumenPedido 2 " +
                           VariablesVentas.vArrayList_ResumenPedido);
        //aqui calculato totales pedido SE COMENTO YA QUE NO REFLEJABA LOS CAMBIOS EN EL JTABLE
        //calculaTotalesPedido();se reemplazo con lo siguiente que tiene el reflejar los cambios en el jtable
        neoOperaResumenPedido();
        //seleccionar el producto que se selecciono
        FarmaGridUtils.showCell(tblProductos, pFila, 0);

        System.out.println("VariablesVentas.vArrayList_ResumenPedido 3 " +
                           VariablesVentas.vArrayList_ResumenPedido);
    }

    private void operaProductoEnJTable(int pFila) {
        //tblProductos.setValueAt(VariablesVentas.vVal_Prec_Lista, pFila, 3);//precio de lista
        tblProductos.setValueAt(VariablesVentas.vCant_Ingresada, pFila,
                                4); //cantidad ingresada
        //JCORTEZ 17.04.08
        //tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Dcto_1),2), pFila, 5);//PORC DCTO 1

        //tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta),3), pFila, 6);//PRECIO DE VENTA
        //System.out.println(" FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2) " + FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2));
        //tblProductos.setValueAt(FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2), pFila, 7);//Total Precio Vta
        //String valIgv = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) - (FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) / ( 1 + (FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Igv_Prod) / 100)))) * FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada));
        //System.out.println(valIgv);
        //VariablesVentas.vVal_Igv_Prod = valIgv;
        //tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Igv_Prod),2), pFila, 12);//Total Igv Producto
        tblProductos.setValueAt(VariablesVentas.vNumeroARecargar, pFila,
                                13); //Numero de Recarga

        tblProductos.setValueAt(VariablesVentas.vCantxDia, pFila,
                                COL_RES_CANT_XDIA);
        tblProductos.setValueAt(VariablesVentas.vCantxDias, pFila,
                                COL_RES_CANT_DIAS);

        tblProductos.repaint();
    }

    private void operaProductoEnArrayList(int pFila) {
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(4,
                                                                             VariablesVentas.vCant_Ingresada);
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
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(13,
                                                                             VariablesVentas.vNumeroARecargar);
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(COL_RES_CANT_XDIA,
                                                                             VariablesVentas.vCantxDia);
        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).set(COL_RES_CANT_DIAS,
                                                                             VariablesVentas.vCantxDias);

        //JCHAVEZ 29102009 inicio
        try {
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                VariablesVentas.vIndAplicaRedondeo =
                        DBVentas.getIndicadorAplicaRedondedo();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        ArrayList vAux =
            (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila);
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
            String indicadorPromocion =
                FarmaUtility.getValueFieldJTable(tblProductos, filaActual,
                                                 COL_RES_IND_PACK);
            System.out.println("INDICADOR PROMOCION: " + indicadorPromocion);

            if (indicadorPromocion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                eliminaPromocion(filaActual);
                System.out.println("ELIMINAR PROMOCION");
            } else {
                eliminaProducto(filaActual);
                System.out.println("ELIMINAR PRODUCTO");
            }
            /****************************************************************************************************/
            /*if(indicadorPromocion.equalsIgnoreCase("N")){
                    eliminaProducto(filaActual);
                    System.out.println("ELIMINAR PRODUCTO");
                }else{
                    eliminaPromocion(filaActual);
                    System.out.println("ELIMINAR PROMOCION");
                }*/
            /***************************************************************************************************/
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un Producto",
                                     tblProductos);
        }
    }

    /**
     * Elimina  producto seleccionado
     * @author :dubilluz
     * @since  :19.06.2007
     */
    void eliminaProducto(int filaActual) {

        if (FarmaUtility.rptaConfirmDialog(this,
                                           "Seguro de eliminar el Producto del Pedido?")) {
            String codProd =
                ((String)(tblProductos.getValueAt(filaActual, 0))).trim();
            VariablesVentas.vVal_Frac =
                    FarmaUtility.getValueFieldJTable(tblProductos, filaActual,
                                                     10);
            String cantidad =
                ((String)(tblProductos.getValueAt(filaActual, 4))).trim();
            String indControlStk =
                ((String)(tblProductos.getValueAt(filaActual, 16))).trim();
            String secRespaldo = ""; //ASOSA, 02.07.2010
            secRespaldo =
                    (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(filaActual)).get(26); //ASOSA, 02.07.2010
            VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
            System.err.println("filaActual:_"+filaActual);
            System.err.println("VariablesVentas.vArrayList_PedidoVenta:_"+VariablesVentas.vArrayList_PedidoVenta);
            System.err.println("ANTES_BORRA_VariablesVentas.secRespStk:_"+VariablesVentas.secRespStk);
            System.err.println("secRespaldo:_"+secRespaldo);
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
                    0, ConstantsVentas.INDICADOR_D,
                    ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, true,
                    this, tblProductos, secRespaldo))
                return;
            System.err.println("DESPUES_BORRA_VariablesVentas.secRespStk:_"+VariablesVentas.secRespStk);
            System.err.println("secRespaldo:_"+secRespaldo);
            ArrayList vAux =
                (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(filaActual);
            log.info("Registro eliminado: " + vAux);

            //dubilluz - 26.08.2010
            VariablesVentas.vArrayList_PedidoVenta.remove(filaActual);
            vAux = null;
            //dubilluz - 26.08.2010
            VariablesVentas.vArrayList_ResumenPedido.remove(filaActual);
            tableModelResumenPedido.deleteRow(filaActual);
            tblProductos.repaint();
            System.err.println("000-VariablesVentas.vArrayList_PedidoVenta:_"+VariablesVentas.vArrayList_PedidoVenta);
            //calculaTotalesPedido();comentado para reemplazar por por el neoOperaResumenPedido
            neoOperaResumenPedido();
            System.err.println("001-VariablesVentas.vArrayList_PedidoVenta:_"+VariablesVentas.vArrayList_PedidoVenta);


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
        if (FarmaUtility.rptaConfirmDialog(this,
                                           "Seguro de eliminar la Promocion del Pedido?")) {
            String codProm =
                ((String)(tblProductos.getValueAt(filaActual, 0))).trim();
            String codProd = "";
            String cantidad =
                ""; //((String)(tblProductos.getValueAt(filaActual,4))).trim();
            String indControlStk =
                ""; // ((String)(tblProductos.getValueAt(filaActual,16))).trim();
            ArrayList aux = new ArrayList();

            ArrayList prod_Prom = new ArrayList();
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " +
                               VariablesVentas.vArrayList_Prod_Promociones);
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB: " +
                               VariablesVentas.vCodProm);
            prod_Prom =
                    detalle_Prom(VariablesVentas.vArrayList_Prod_Promociones,
                                 codProm);
            System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC: " +
                               prod_Prom);
            Boolean valor = new Boolean(true);
            ArrayList agrupado = new ArrayList();
            ArrayList atemp = new ArrayList();
            for (int i = 0; i < prod_Prom.size(); i++) {
                System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                atemp = (ArrayList)(prod_Prom.get(i));
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5555 ATEMP: " +
                                   atemp);
                FarmaUtility.operaListaProd(agrupado,
                                            (ArrayList)(atemp.clone()), valor,
                                            0);
            }
            System.out.println("AAA: -> " + agrupado);
            //agrupado=agrupar(agrupado);
            System.out.println(">>>>**Agrupado " + agrupado.size());
            String secRespaldo = ""; //ASOSA, 08.07.2010
            for (int i = 0; i < agrupado.size();
                 i++) //VariablesVentas.vArrayList_Prod_Promociones.size(); i++)
            {
                System.out.println("Entro al for");
                aux =
(ArrayList)(agrupado.get(i)); //VariablesVentas.vArrayList_Prod_Promociones.get(i));
                //if((((String)(aux.get(18))).trim()).equalsIgnoreCase(codProm)){
                System.out.println("Entro");
                codProd = ((String)(aux.get(0))).trim();
                VariablesVentas.vVal_Frac = ((String)(aux.get(10))).trim();
                cantidad = ((String)(aux.get(4))).trim();
                indControlStk = ((String)(aux.get(16))).trim();
                secRespaldo =
                        ((String)(aux.get(24))).trim(); //ASOSA, 08.07.2010
                System.out.println(indControlStk);
                VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
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
                        0, ConstantsVentas.INDICADOR_D,
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0,
                        false, this, tblProductos, secRespaldo))
                    return;
                //}
            }
            FarmaUtility.aceptarTransaccion();
            removeItemArray(VariablesVentas.vArrayList_Promociones, codProm,
                            0);
            removeItemArray(VariablesVentas.vArrayList_Prod_Promociones,
                            codProm, 18);

            System.out.println("Resultados despues de Eliminar");
            System.out.println("Tamaño de Resumen   :" +
                               VariablesVentas.vArrayList_ResumenPedido.size() +
                               "");
            System.out.println("Tamaño de Promocion :" +
                               VariablesVentas.vArrayList_Promociones.size() +
                               "");
            System.out.println("Tamaño de Prod_Promocion :" +
                               VariablesVentas.vArrayList_Prod_Promociones.size() +
                               "");

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
        String fechaTipoCambio = ""; //tipo cambio de la fecha actual
        try {
            fechaPedido =
                    FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            FarmaVariables.vTipCambio =
                    FarmaSearch.getTipoCambio(fechaTipoCambio);
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Error al obtener Tipo de Cambio del Dia . \n " +
                                     sql.getMessage(), null);
        }
    }

    private synchronized void grabarPedidoVenta(String pTipComp) {

        if (pedidoGenerado) {
            log.debug("jcallo: el pedido ya fue generado");
            return;
        }
        if (VariablesVentas.vArrayList_ResumenPedido.size() <= 0 &&
            VariablesVentas.vArrayList_Prod_Promociones.size() <= 0) {
            FarmaUtility.showMessage(this,
                                     "No hay Productos Seleccionados. Verifique!!!",
                                     tblProductos);
            return;
        }

        //se comento por que esto se supone que ya esta calculado JCALLO
        //calculaTotalesPedido();///no entender para que se hace de nuevo algo QUE YA estuvo

        boolean aceptaCupones;
        boolean esConvenioBTLMF = false;

        //aceptaCupones = validaUsoCampanaMonto(lblTotalS.getText().trim());
        //ESTO por ahora siempre devuelve true ya que no hay o no esta bien definido el uso
        //de cupones de tipo de MONTO
        aceptaCupones = validaCampsMontoNetoPedido(lblTotalS.getText().trim());




        //Agregado por FRAMRIEZ 16.12.2011
        System.out.println("------------------------------------");
        System.out.println("----Asignando el Importe total------");
        //System.out.println("------------------------------------" + totalS);
        VariablesConvenioBTLMF.vImpSubTotal = FarmaUtility.getDecimalNumber(lblTotalS.getText());


        //JCORTEZ 24.07.08 se muestra los cupones no utilizados
        //jcallo 11.03.2009
        int cantCuponesNoUsado = 0;
        String estado;
        int pExist = -1;
        Map mapaCupon = new HashMap();
        boolean flagCampAutomatico;
        VariablesVentas.vList_CuponesNoUsados = new ArrayList();
        VariablesVentas.vList_CuponesUsados = new ArrayList();
        System.out.println("VariablesVentas.vList_CuponesUsados:" +
                           VariablesVentas.vList_CuponesUsados);
        if (VariablesVentas.vArrayList_Cupones.size() > 0) {
            for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size();
                 i++) {
                mapaCupon = new HashMap();
                mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(i);
                //ver si es un cupon de campania automatica.
                flagCampAutomatico =
                        //( mapaCupon.get("COD_CAMP_CUPON").toString().indexOf("A") != -1) ? true : false;
                        (

                        mapaCupon.get("COD_CAMP_CUPON").toString().indexOf("A") !=
                        -1 ||
                        mapaCupon.get("COD_CAMP_CUPON").toString().indexOf("L") !=
                        -1) ? true : false;

                log.debug("flagCampAutomatico:" + flagCampAutomatico);
                log.debug("Cod Camp Cupon " +
                          mapaCupon.get("COD_CAMP_CUPON").toString());
                if (!flagCampAutomatico) {
                    //ver si se uso o no en resumen pedido
                    boolean flagUso = false;
                    for (int k = 0;
                         k < VariablesVentas.vArrayList_ResumenPedido.size();
                         k++) {
                        String campUso =
                            (String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(k)).get(25);
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
            log.debug("CUPONES NO USADO cantCuponesNoUsado:" +
                      cantCuponesNoUsado);
            if (cantCuponesNoUsado > 0) {
                DlgCupones dlgCupones =
                    new DlgCupones(myParentFrame, "Cupones No Usados", true);
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
            {
                try {
                    //JCORTEZ 25.03.09
                    //VariablesVentas.vTip_Comp_Ped = pTipComp;
                    log.debug("JCORTEZ: tipo --> " + pTipComp);
                    //VariablesVentas.vTip_Comp_Ped = DBCaja.getObtieneTipoComp(VariablesCaja.vNumCaja,pTipComp);

                    //JCORTEZ 09.06.09  Se obtiene tipo de comrpobante de la relacion maquina - impresora
                    if (pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET) ||
                        pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)) {
                        VariablesVentas.vTip_Comp_Ped =
                                DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc,
                                                               pTipComp);
                        log.debug("JCORTEZ: VariablesVentas.vTip_Comp_Ped--> " +
                                  VariablesVentas.vTip_Comp_Ped);
                        if (VariablesVentas.vTip_Comp_Ped.trim().equalsIgnoreCase("N")) {
                            FarmaUtility.showMessage(this,
                                                     "La IP no cuenta con una impresora asignada de ticket o boleta. Verifique!!!",
                                                     tblProductos);
                            return;
                        }
                    } else if (pTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA))
                        VariablesVentas.vTip_Comp_Ped = pTipComp.trim();

                    log.debug("JCORTEZ: tipo de comprobante por caja--> " +
                              VariablesVentas.vTip_Comp_Ped);


                    double vValorMaximoCompra =
                        Double.parseDouble(DBVentas.getMontoMinimoDatosCliente()); //Se obtiene de BD
                    log.debug("Monto Minimo para validar si pedira datos cliente " +
                              vValorMaximoCompra);
                    double dTotalNeto =
                        FarmaUtility.getDecimalNumber(lblTotalS.getText().trim());
                    VariablesVentas.vIndObligaDatosCliente = false;
                    if (dTotalNeto >= vValorMaximoCompra) {
                        VariablesVentas.vIndObligaDatosCliente = true;
                        log.debug("Debe de ingresar datos obligatorios");
                    }

                    if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&  VariablesConvenioBTLMF.vCodConvenio.length() > 0)
       	         {
                      esConvenioBTLMF = true;
                      VariablesVentas.vIndObligaDatosCliente = false;
       	         }


                    ////
                    log.debug("JCORTEZ: FarmaVariables.vTipCaja--> " +
                              FarmaVariables.vTipCaja);
                    log.debug("JCORTEZ: VariablesVentas.vTip_Comp_Ped--> " +
                              VariablesVentas.vTip_Comp_Ped);
                    log.debug("JCORTEZ: VariablesVentas.vIndObligaDatosCliente--> " +
                              VariablesVentas.vIndObligaDatosCliente);

                    if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) ||
                        (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_TRADICIONAL) &&
                         VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) ||
                        VariablesVentas.vIndObligaDatosCliente) // Pedir datos si pase del monto indicado
                    {
                        /*JOptionPane.showMessageDialog(null,"FarmaVariables.vTipCaja: " + FarmaVariables.vTipCaja + "\n" +
                                                  "VariablesVentas.vTip_Comp_Ped: " + VariablesVentas.vTip_Comp_Ped+ "\n" +
                                                  "VariablesVentas.vIndObligaDatosCliente: "+VariablesVentas.vIndObligaDatosCliente);
               JOptionPane.showMessageDialog(null,"VariablesVentas.vNum_Ped_Vta: "+VariablesVentas.vNum_Ped_Vta);
               JOptionPane.showMessageDialog(null,"VariablesCaja.vNumPedVta: "+VariablesCaja.vNumPedVta);*/
                        muestraSeleccionComprobante();

                        if (VariablesVentas.vIndObligaDatosCliente) {
                            if (VariablesVentas.vRuc_Cli_Ped.trim().length() ==
                                0 ||
                                VariablesVentas.vNom_Cli_Ped.trim().length() ==
                                0) {
                                FarmaUtility.liberarTransaccion();

                                FarmaUtility.showMessage(this,
                                                         "Por favor ingrese Nombre y Dni del cliente para esta venta.\n" +
                                        "Gracias.", tblProductos);
                                return;
                            }

                        }


                        //Se valida si el RUC es valido para cobragenerar el pedido si tiene descuentos en un fidelizado
                        //DAUBILLUZ 29.05.2009
                        if (VariablesVentas.vTip_Comp_Ped.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_FACTURA) &&
                            VariablesFidelizacion.vAhorroDNI_Pedido > 0) {
                            if (!UtilityFidelizacion.isRUCValido(VariablesVentas.vRuc_Cli_Ped.trim())) {
                                FarmaUtility.showMessage(this,
                                                         "Los descuentos son para venta con\n" +
                                        "boleta de venta y para consumo\n" +
                                        "personal. El RUC ingresado queda\n" +
                                        "fuera de la promoción de descuento.",
                                        tblProductos);
                                return;
                            }
                        }
                        //FINS


                        if (!FarmaVariables.vAceptar) {
                            FarmaUtility.liberarTransaccion();
                            return;
                        } else {
                            if (pedidoGenerado) {
                                System.out.println("EL PEDIDO YA FUE GENERADO...");
                                FarmaUtility.liberarTransaccion();
                                return;
                            }
                            /**
	           * Solo si no es multifuncional
	           * @author dubilluz
	           * @since  30.04.2008
	           */
                            if (!FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
                                if (!FarmaUtility.rptaConfirmDialog(this,
                                                                    "Esta seguro de realizar el pedido?")) {
                                    FarmaUtility.liberarTransaccion();
                                    return;
                                }
                        }
                    }
              if(!cargaLogin_verifica())
                    {//
                     FarmaVariables.vAceptar = false;
                     FarmaUtility.liberarTransaccion();
                        return;
                    }



                    //fin Agregado por FRAMIREZ por BTLMF 19.12.2011

                    VariablesVentas.vNum_Ped_Vta =
                            FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO,
                                                           10);
                    VariablesVentas.vNum_Ped_Diario =
                            obtieneNumeroPedidoDiario();
                    log.info("VariablesVentas.vNum_Ped_Vta : " +
                             VariablesVentas.vNum_Ped_Vta);
                    log.info("VariablesVentas.vNum_Ped_Diario : " +
                             VariablesVentas.vNum_Ped_Diario);
                    if (VariablesVentas.vNum_Ped_Vta.trim().length() == 0 ||
                        VariablesVentas.vNum_Ped_Diario.trim().length() == 0)
                        throw new SQLException("Error al obtener Numero de Pedido",
                                               "Error", 9001);
                    //coloca valores de variables de cabecera de pedido
                    guardaVariablesPedidoCabecera();
                    System.out.println("GUARDO VALORES DE PEDIDO CABECERA");
                    DBVentas.grabarCabeceraPedido();
                    System.out.println("GRABO CABECERA DE PEDIDO");


                    int cont = 0;
                    ArrayList array = new ArrayList();

                    for (int i = 0;
                         i < VariablesVentas.vArrayList_ResumenPedido.size();
                         i++) {
                        cont++;
                        array =
                                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
                        log.debug("ConstantsVentas.IND_PROD_SIMPLE " +
                                  ConstantsVentas.IND_PROD_SIMPLE);
                        //grabarDetalle(pTipComp,array,cont,ConstantsVentas.IND_PROD_SIMPLE); //antes, ASOSA, 06.07.2010
                        grabarDetalle_02(pTipComp, array, cont,
                                         ConstantsVentas.IND_PROD_SIMPLE); //ASOSA, 06.07.2010
                    }

                    /**
	       * Grabando detalle de Productos de las Promociones
	       * @author : dubilluz
	       * @since  : 23.06.2007
	       */
                    System.out.println("antes del for de promociones");
                    for (int i = 0;
                         i < VariablesVentas.vArrayList_Prod_Promociones.size();
                         i++) {
                        cont++;
                        array =
                                ((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i));
                        System.out.println(array.get(0) + "");
                        log.debug("ConstantsVentas.IND_PROD_PROM " +
                                  ConstantsVentas.IND_PROD_PROM);
                        //grabarDetalle(pTipComp,array,cont,ConstantsVentas.IND_PROD_PROM);
                        grabarDetalle_02(pTipComp, array, cont,
                                         ConstantsVentas.IND_PROD_PROM); //ASOSA, 07.07.2010
                    }
                    System.out.println("fin del detalle productos");

                    //JCHAVEZ 19102009
                    for (int i = 0;
                         i < VariablesVentas.vArrayList_Promociones.size();
                         i++) {
                        array =
                                ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i));
                        System.out.println(array.get(0) + "");
                        int cantidad =
                            Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(4));
                        String cod_prom =
                            (String)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).get(0);
                        log.debug("promocion " + cod_prom + " | cantidad " +
                                  cantidad);
                        try {
                            DBVentas.grabaPromXPedidoNoAutomaticos(cod_prom,
                                                                   cantidad);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }

                    //dubilluz 09.04.2008 Se procedera a ver si se puede o no acceder a un producto de regalo por el encarte.
                    cont++;
                    VariablesVentas.vIndVolverListaProductos = false;
                    if (!procesoProductoRegalo(VariablesVentas.vNum_Ped_Vta,
                                               cont)) {
                        FarmaUtility.liberarTransaccion();
                        VariablesVentas.vIndVolverListaProductos = true;
                        return;
                    }

                    System.out.println("GRABO DETALLE DE PEDIDO : " +
                                       (cont + 1) + " PRODUCTOS" +
                                       VariablesVentas.vArrayList_ResumenPedido.size());
                    FarmaSearch.setNuSecNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO);

                    // actualización de los Stock's.
                    /* for(int i=0; i<VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
	       array=((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
	        DBVentas.actualizaStkProd_Fis_Comp((String)array.get(0),
	                                           (String)array.get(4));
	      }
	      for(int i=0; i<VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
	        array=((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i));
	        DBVentas.actualizaStkProd_Fis_Comp((String)array.get(0),
	                                           (String)array.get(4));
	      }*/
                    System.out.println("ACTUALIZO STOCK PRODUCTOS");
                    for (int i = 0;
                         i < VariablesVentas.vArrayList_ResumenPedido.size();
                         i++) {
                        String codProd =
                            ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(0)).trim();
                        String secRespaldo =
                            ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(26)).trim(); //ASOSA, 06.07.2010
                        //DBPtoVenta.ejecutaRespaldoStock(codProd, VariablesVentas.vNum_Ped_Vta, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR_PEDIDO, 0, 0,ConstantsPtoVenta.MODULO_VENTAS); antes,//ASOSA, 06.07.2010
                        /*DBVentas.actualizarRespaldoNumPedido(codProd,
                                                             ConstantsPtoVenta.MODULO_VENTAS,
                                                             VariablesVentas.vNum_Ped_Vta,
                                                             secRespaldo); //ASOSA, 06.07.2010
                         */
                    }
                    /**
	       * Actualiza estock
	       * @author : dubilluz
	       * @since  : 23.06.2007
	       */
                    for (int i = 0;
                         i < VariablesVentas.vArrayList_Prod_Promociones.size();
                         i++) {
                        String codProd =
                            ((String)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).get(0)).trim();
                        String secRespaldo =
                            ((String)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).get(24)).trim(); //ASOSA, 08.07.2010
                        /*DBPtoVenta.ejecutaRespaldoStock( //Antes, ASOSA, 09.07.2010
                                                codProd, VariablesVentas.vNum_Ped_Vta,
                                                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR_PEDIDO,
                                                0,
                                                0,
                                                ConstantsPtoVenta.MODULO_VENTAS
                                               );*/
                       /*
                        *                         DBVentas.actualizarRespaldoNumPedido(codProd,
                                                             ConstantsPtoVenta.MODULO_VENTAS,
                                                             VariablesVentas.vNum_Ped_Vta,
                                                             secRespaldo); //ASOSA, 09.07.2010
                        * */
                    }

                    System.out.println("ACTUALIZO RESPALDO NUMERO PEDIDO");
                    FarmaSearch.setNuSecNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);


                  //***********************************CONVENIO BTLMF*****************************************/
                    //***********************************INICIO*************************************************/

                    /**NUEVO
          			  * @Fecha: 14-12-2011
          			  * @Autor: FRAMIREZ
          			 **/
          	         if(esConvenioBTLMF)
          	         {
          	                 if(VariablesConvenioBTLMF.vDatosConvenio != null)
          	                 {
          	                	System.out.println("grabando los datos del convenio BTLMF y el Pedido");

          	                    for (int j = 0; j < VariablesConvenioBTLMF.vDatosConvenio.size(); j++)
          	                    {
          	                    	Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(j);

          	                    	String pCodCampo      = (String)convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO);
          	                    	String pDesCampo      = (String)convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN);
          	                    	String pNombCampo 	  = (String)convenio.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO);
          	                    	String pCodValorCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_COD_VALOR_IN);


          	                     	grabarPedidoConvenioBTLMF(pCodCampo, pDesCampo, pNombCampo,pCodValorCampo);

          	                    }
          	                 }
          	         }
          	         else
          	         {

                    //***********************************CONVENIO*****************************************/
                    //***********************************INICIO*******************************************/
                    /**NUEVO
	       * @Fecha: 20-03-2007
	       * @Autor: Luis
	       * */
                    if (!VariablesConvenio.vCodConvenio.equalsIgnoreCase("")) //Se ha elegido un convenio
                    {
                        System.out.println("Datos Convenio: " +
                                           VariablesConvenio.vArrayList_DatosConvenio);
                        String vCodCli =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(1);
                        String vApePat =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(3);
                        String vApeMat =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(4);
                        String vNumDoc =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(5);
                        String vTelefono =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(6);
                        String vCodInterno =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(7);
                        String vTrabajador =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(8);
                        System.out.println("****Nombre Completo " +
                                           vTrabajador);
                        /**
	         * Para grabar el COD_TRABJADOR_EMPRESA
	         * @author : dubilluz
	         * @since  : 21.08.2007
	         */
                        String vCodTrabEmpresa =
                            "" + VariablesConvenio.vArrayList_DatosConvenio.get(9);
                        System.out.println("****COD trabajadot de Conv >>>" +
                                           vCodTrabEmpresa);

                        /**
	         * Grabando los codigos de trabajador y cliente dependiente
	         * @author dubilluz
	         * @since  05.02.2008
	         */
                        String vCodCliDep =
                            VariablesConvenio.vCodClienteDependiente;
                        String vCodTrabEmpDep =
                            VariablesConvenio.vCodTrabDependiente;

                        //grabarPedidoConvenio(vCodCli,vNumDoc,"",vApePat,vApeMat,"","",vTelefono,"","",vCodInterno,vTrabajador);
                        grabarPedidoConvenio(vCodCli, vNumDoc, vCodTrabEmpresa,
                                             vApePat, vApeMat, "", "",
                                             vTelefono, "", "", vCodInterno,
                                             vTrabajador, vCodCliDep,
                                             vCodTrabEmpDep);


                        double totalS =
                            FarmaUtility.getDecimalNumber(lblTotalS.getText());
                        System.out.println("totalS " + totalS);
                        //VariablesConvenio.vValCoPago = DBConvenio.obtieneCoPagoConvenio(VariablesConvenio.vCodConvenio,vCodCli,FarmaUtility.formatNumber(totalS));
                        System.err.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ" );
                        System.err.println("VariablesConvenio.vValCoPago: " + VariablesConvenio.vValCoPago);
                        if (FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) !=
                            0) {
                            VariablesConvenio.vCredito =
                                    DBConvenio.obtieneCredito(VariablesConvenio.vCodConvenio,
                                                              vCodCli,
                                                              FarmaConstants.INDICADOR_S);
                            VariablesConvenio.vCreditoUtil =
                                    DBConvenio.obtieneCreditoUtil(VariablesConvenio.vCodConvenio,
                                                                  vCodCli,
                                                                  FarmaConstants.INDICADOR_S);
                            System.out.println("VariablesConvenio.vCredito: " +
                                               VariablesConvenio.vCredito);
                            System.out.println("VariablesConvenio.vCreditoUtil: " +
                                               VariablesConvenio.vCreditoUtil);
                            System.out.println("vCoPago: " +
                                               VariablesConvenio.vValCoPago);
                        }
                        if (FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) !=
                            0 &&
                            !VariablesConvenio.vCredito.equalsIgnoreCase(VariablesConvenio.vCreditoUtil)) {
                            //VariablesCaja.vNumPedVta = VariablesVentas.vNum_Ped_Vta;
                            String vFormaPago =
                                DBConvenio.obtieneFormaPagoXConvenio(VariablesConvenio.vCodConvenio);
                            grabarFormaPagoPedido(vFormaPago,
                                                  VariablesVentas.vNum_Ped_Vta,
                                                  VariablesConvenio.vValCoPago,
                                                  FarmaVariables.vCodMoneda,
                                                  FarmaUtility.formatNumber(FarmaVariables.vTipCambio),
                                                  "0",
                                                  VariablesConvenio.vValCoPago,
                                                  "", "", "", "0");
                            System.out.println("graba forma de pago");
                        }
                    }
          	       }
                    System.out.println("CONTINUA!");
                    //*************************************FIN********************************************/
                    //***********************************CONVENIO*****************************************/
                    System.out.println(VariablesVentas.vList_CuponesUsados +
                                       "");
                    //ERIOS 04.07.2008 Se graban los cupones validos.
                    //JCALLO 11.03.2009 modificado por temala nueva estructura de manejode cupones
                    String cadena, vCodCamp, vIndUso, vIndFid;
                    boolean vCampAuto;
                    Map mapaCuponAux = new HashMap();
                    for (int i = 0;
                         i < VariablesVentas.vList_CuponesUsados.size(); i++) {
                        mapaCuponAux =
                                (Map)VariablesVentas.vList_CuponesUsados.get(i);
                        cadena = mapaCuponAux.get("COD_CUPON").toString();
                        vCodCamp =
                                mapaCuponAux.get("COD_CAMP_CUPON").toString(); //FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Cupones,i,1);
                        vIndFid =
                                mapaCuponAux.get("IND_FID").toString(); //FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Cupones,i,8);
                        vIndUso = FarmaConstants.INDICADOR_S;
                        vCampAuto =
                                (vCodCamp.indexOf("A") != -1) ? true : false;
                        log.debug("JCALLO: datos del cupon cadena:" + cadena +
                                  " vCodCamp:" + vCodCamp + " vIndUso : " +
                                  vIndUso);
                        if (!vCampAuto) { //si no es campania automatica graba cupon
                            DBVentas.grabaPedidoCupon(VariablesVentas.vNum_Ped_Vta,
                                                      cadena, vCodCamp,
                                                      vIndUso);
                        }
                    }


                    //Proceso de campañas Acumuladas
                    //dubilluz 17.12.2008
                    //JCORTEZ 07.01.2009
                    //Solo se generara el historico y canje si no hay producto recarga
                    System.err.println("DUBILLUZ-30.11.2009:" +
                                       VariablesVentas.vIndProdVirtual);
                    System.err.println("DUBILLUZ-30.11.2009:" +
                                       VariablesVentas.vIndPedConProdVirtual);
                    if (!VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
                        !VariablesVentas.vIndPedConProdVirtual) {

                        //Proceso de campañas Automaticas.
                    	if(!esConvenioBTLMF)
             	         {
                        procesoPack(VariablesVentas.vNum_Ped_Vta);

                        System.err.println("INICIO - procesoCampañasAcumuladas");
                        //Campañas acumuladas
                        procesoCampañasAcumuladas(VariablesVentas.vNum_Ped_Vta,
                                                  VariablesFidelizacion.vDniCliente);
                        System.err.println("FIN - procesoCampañasAcumuladas");
                        /* Antes de aceptar toda la transaccion se verificara si existen campañas de cupones
	          * y si puede recibir cupones de regalo para sorteo
	          * 10.04.2008 dubilluz  creacion
	          */
                        procesoCampañas(VariablesVentas.vNum_Ped_Vta);

                        //ERIOS 04.07.2008 Aplica Cupones
                        //DUBILLUZ 23.07.2008 MODIFICACION
                        DBVentas.procesaPedidoCupon(VariablesVentas.vNum_Ped_Vta);
                    }
                    }

                    //grabando numero de tarjeta del pedido de cliente fidelizado
                    if (VariablesFidelizacion.vNumTarjeta.length() > 0) {
                        DBFidelizacion.insertarTarjetaPedido();
                    }

                    //actualizando los descto aplicados por cada productos en el detalle del pedido
                    //JCALLO 11.03.2009
                    //el metodo actualizarAhorroProdVentaDet, en base de datos ESTABA haciendo commit
                    Map mapaDctoProd = new HashMap();
                    for (int mm = 0;
                         mm < VariablesVentas.vListDctoAplicados.size();
                         mm++) {
                        mapaDctoProd =
                                (Map)VariablesVentas.vListDctoAplicados.get(mm);

                        DBVentas.guardaDctosDetPedVta(mapaDctoProd.get("COD_PROD").toString(),
                                                      mapaDctoProd.get("COD_CAMP_CUPON").toString(),
                                                      mapaDctoProd.get("VALOR_CUPON").toString(),
                                                      mapaDctoProd.get("AHORRO").toString(),
                                                      mapaDctoProd.get("DCTO_AHORRO").toString(),
                                                      mapaDctoProd.get("SEC_PED_VTA_DET").toString()); //JMIRANDA 30.10.09 ENVIA SEC_DET_

                        //actualizarAhorroProdVentaDet(mapaDctoProd);
                    }

                    if (VariablesVentas.vSeleccionaMedico) {
                        DBVentas.agrega_medico_vta();
                    }
                    /**validando de que total neta = valRedondeo + sum(val_precio_total)**/
                    DBVentas.validarValorVentaNeto(VariablesVentas.vNum_Ped_Vta);

                    FarmaUtility.aceptarTransaccion();

                    pedidoGenerado = true;

                    if (FarmaVariables.vTipCaja.equalsIgnoreCase("") ||
                        FarmaVariables.vTipCaja.equalsIgnoreCase(null)) {
                        ArrayList infoLocal = DBPtoVenta.obtieneDatosLocal();
                        FarmaVariables.vDescCortaLocal =
                                ((String)((ArrayList)infoLocal.get(0)).get(0)).trim();
                        FarmaVariables.vDescLocal =
                                ((String)((ArrayList)infoLocal.get(0)).get(1)).trim();
                        FarmaVariables.vTipLocal =
                                ((String)((ArrayList)infoLocal.get(0)).get(2)).trim();
                        FarmaVariables.vTipCaja =
                                ((String)((ArrayList)infoLocal.get(0)).get(3)).trim();
                    }

                    if (FarmaVariables.vTipCaja.equalsIgnoreCase("") ||
                        FarmaVariables.vTipCaja.equalsIgnoreCase(null)) {
                        FarmaVariables.vTipLocal = "M";
                    }

                    if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) {
                        VariablesCaja.vNumPedPendiente =
                                VariablesVentas.vNum_Ped_Diario;
                        System.out.println("HOLA HOLA HOLA HOLA HOLA HOLA HOLA");
                        if (DBCaja.elegirVentanaCobro()) { //ASOSA 17.02.2010
                            determinarCobro();
                        } else {
                            muestraCobroPedido();
                        }

                        if (VariablesFidelizacion.vRecalculaAhorroPedido) {
                            System.err.println("Recalcula el ahorro del cliente");
                            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                                    UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
                            VariablesFidelizacion.vAhorroDNI_x_Periodo =
                                    UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
                            neoOperaResumenPedido();
                        }
                    } else {
                        if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_TRADICIONAL))
                            muestraPedidoRapido();
                    }


                    //JCORTEZ 04.08.09 Se limpiar cupones usados luego de generar pedido.
                    VariablesVentas.vArrayListCuponesCliente.clear();
                    VariablesVentas.dniListCupon = "";
                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    //sql.printStackTrace();
                    log.error(null, sql);
                    if (sql.getErrorCode() == 20066) {
                        FarmaUtility.showMessage(this,
                                                 "Error en Base de Datos al Generar Pedido.\n Inconsistencia en los montos calculados",
                                                 tblProductos);
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "Error en Base de Datos al grabar el pedido.\n" +
                                sql, tblProductos);
                    }
                } catch (Exception exc) {
                    FarmaUtility.liberarTransaccion();
                    //exc.printStackTrace();
                    log.error(null, exc);
                    FarmaUtility.showMessage(this,
                                             "Error en la aplicacion al grabar el pedido.\n" +
                            exc.getMessage(), tblProductos);
                }
            }
        }
    }

    /*private void grabarDetallePedido(String pTiComprobante, int pFila) throws Exception {

    VariablesVentas.vSec_Ped_Vta_Det = String.valueOf(pFila + 1);
    VariablesVentas.vCod_Prod = ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(0)).trim();
    VariablesVentas.vCant_Ingresada = String.valueOf(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(4)).trim())));
    VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(6)).trim();
    VariablesVentas.vTotalPrecVtaProd = FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(7)).trim());
    VariablesVentas.vPorc_Dcto_1 = String.valueOf(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(5)).trim()));
    VariablesVentas.vPorc_Dcto_Total = VariablesVentas.vPorc_Dcto_1;//cuando es pedido normal, el descuento total siempre es el descuento 1
    VariablesVentas.vVal_Total_Bono = String.valueOf(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(8)).trim()));
    VariablesVentas.vVal_Frac = ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(10)).trim();
    VariablesVentas.vEst_Ped_Vta_Det = ConstantsVentas.ESTADO_PEDIDO_DETALLE_ACTIVO;
    VariablesVentas.vSec_Usu_Local = FarmaVariables.vNuSecUsu;
    VariablesVentas.vVal_Prec_Lista = String.valueOf(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(3)).trim()));
    VariablesVentas.vVal_Igv_Prod = ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(11)).trim();
    VariablesVentas.vUnid_Vta = ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(2)).trim();
    VariablesVentas.vNumeroARecargar = ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(13)).trim();
    System.out.println("***-VariablesVentas.vVal_Prec_Pub "+VariablesVentas.vVal_Prec_Pub);
    VariablesVentas.vVal_Prec_Pub = ((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(pFila)).get(18)).trim();
    VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,pFila,COL_RES_ORIG_PROD);
    DBVentas.grabarDetallePedido();

  }*/


    private String obtieneNumeroPedidoDiario() throws SQLException {

        String feModNumeracion = DBVentas.obtieneFecModNumeraPed();
        String feHoyDia = "";
        String numPedDiario = "";
        if (!(feModNumeracion.trim().length() > 0))
            throw new SQLException("Ultima Fecha Modificacion de Numeración Diaria del Pedido NO ES VALIDA !!!",
                                   "Error", 0001);
        else {
            feHoyDia =
                    FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            feHoyDia = feHoyDia.trim().substring(0, 2);
            feModNumeracion = feModNumeracion.trim().substring(0, 2);
            if (Integer.parseInt(feHoyDia) !=
                Integer.parseInt(feModNumeracion)) {
                FarmaSearch.inicializaNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
                numPedDiario = "0001";
            } else {
                // Obtiene el Numero de Atencion del Día y hace SELECT FOR UPDATE.
                numPedDiario =
                        FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO,
                                                       4);
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
            VariablesVentas.vTip_Ped_Vta =
                    ConstantsVentas.TIPO_PEDIDO_DELIVERY; //verificar q tipo de pedido es...
        else if (VariablesVentas.vEsPedidoInstitucional)
            VariablesVentas.vTip_Ped_Vta =
                    ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL; //verificar q tipo de pedido es...
        else
            VariablesVentas.vTip_Ped_Vta =
                    ConstantsVentas.TIPO_PEDIDO_MESON; //verificar q tipo de pedido es...
        VariablesVentas.vCant_Items_Ped = lblItems.getText().trim();
        VariablesVentas.vEst_Ped_Vta_Cab =
                ConstantsVentas.ESTADO_PEDIDO_PENDIENTE;

    }

    private void muestraPedidoRapido() {
        DlgNumeroPedidoGenerado dlgNumeroPedidoGenerado =
            new DlgNumeroPedidoGenerado(myParentFrame, "", true);
        dlgNumeroPedidoGenerado.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        }
    }

    private void limpiaValoresPedido() {

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
        VariablesVentas.vDescProxProd_Regalo ="";
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
        VariablesFidelizacion.vIndUsoEfectivo  = "NULL";
        VariablesFidelizacion.vIndUsoTarjeta   = "NULL";
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
            log.debug("**** prm 0:" + prms.get(0));
            log.debug("**** prm 1:" + prms.get(1));
            log.debug("**** prm 2:" + prms.get(2));


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


            lblF9.setVisible(true);

        } catch (Exception e) {
            log.debug("ERROR : " + e);
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
            sql.printStackTrace();
            lblUltimoPedido.setText("----");
            FarmaUtility.showMessage(this,
                                     "Error al obtener ultimo pedido diario. \n" +
                    sql.getMessage(), tblProductos);
        }
    }

    private void muestraSeleccionComprobante() {
        DlgSeleccionTipoComprobante dlgSeleccionTipoComprobante =
            new DlgSeleccionTipoComprobante(myParentFrame, "", true);
        dlgSeleccionTipoComprobante.setVisible(true);
    }

    private void muestraCobroPedido() {
        DlgFormaPago dlgFormaPago = new DlgFormaPago(myParentFrame, "", true);
        dlgFormaPago.setIndPedirLogueo(false);
        dlgFormaPago.setIndPantallaCerrarAnularPed(true);
        dlgFormaPago.setIndPantallaCerrarCobrarPed(true);
        dlgFormaPago.setVisible(true);
        System.err.println("XXXXX_FarmaVariables.vAceptar:" +
                           FarmaVariables.vAceptar);
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
            this.setTitle("Resumen de Pedido - Pedido Delivery" + " /  IP : " +
                          FarmaVariables.vIpPc);
            String nombreCliente =
                VariablesDelivery.vNombreCliente + " " + VariablesDelivery.vApellidoPaterno +
                " " + VariablesDelivery.vApellidoMaterno;
            lblCliente.setText(nombreCliente);
        } else if (VariablesVentas.vEsPedidoInstitucional) {
            lblCliente.setText("");
            this.setTitle("Resumen de Pedido - Pedido Institucional" +
                          " /  IP : " + FarmaVariables.vIpPc);
        } else if (VariablesVentas.vEsPedidoConvenio) {
            lblCliente.setText(VariablesConvenio.vTextoCliente);
            this.setTitle("Resumen de Pedido - Pedido por Convenio: " +
                          VariablesConvenio.vNomConvenio + " /  IP : " +
                          FarmaVariables.vIpPc);
            System.out.println("------------------------" + this.getTitle());
            System.out.println("VariablesConvenio.vTextoCliente : *****" +
                               VariablesConvenio.vTextoCliente);


            lblLCredito_T.setText(VariablesConvenioBTLMF.vDatoLCredSaldConsumo);
            lblBeneficiario_T.setText(getMensajeComprobanteConvenio(VariablesConvenioBTLMF.vCodConvenio));

        } else {
            this.setTitle("Resumen de Pedido" + " /  IP : " +
                          FarmaVariables.vIpPc);
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
        DlgListaClientes dlgListaClientes =
            new DlgListaClientes(myParentFrame, "", true);
        dlgListaClientes.setVisible(true);
        if (FarmaVariables.vAceptar) {
            String nombreCliente =
                VariablesDelivery.vNombreCliente + " " + VariablesDelivery.vApellidoPaterno +
                " " + VariablesDelivery.vApellidoMaterno;
            lblCliente.setText(nombreCliente);
            System.out.println("Antes de Evaluar titulo");
            FarmaVariables.vAceptar = false;
            VariablesVentas.vEsPedidoDelivery = true;
        } else {
            if (FarmaVariables.vImprTestigo.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                System.out.println("Evaluando titulo");
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
        VariablesDelivery.vTipoAccionDireccion = "";
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
        VariablesFidelizacion.vIndUsoEfectivo  = "NULL";
        VariablesFidelizacion.vIndUsoTarjeta   = "NULL";
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

    }
    // Fin Adicion Delivery 28/04/2006 Paulo

    //Inicio Adicion Lista Medicos 26/06/2006 Paulo

    private void agregaMedicoVta() {
        try {
            DBVentas.agrega_medico_vta();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al grabar medico \n " +
                                     sql.getMessage(), tblProductos);
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
            DlgResumenReceta dlgResumenReceta =
                new DlgResumenReceta(myParentFrame, "", true);
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
        VariablesVentas.vCod_Prod =
                ((String)(tblProductos.getValueAt(filaActual, 0))).trim();
        VariablesVentas.vDesc_Prod =
                ((String)(tblProductos.getValueAt(filaActual, 1))).trim();
        VariablesVentas.vNom_Lab =
                ((String)(tblProductos.getValueAt(filaActual, 9))).trim();
        VariablesVentas.vVal_Frac =
                ((String)(tblProductos.getValueAt(filaActual, 10))).trim();
        VariablesVentas.vPorc_Igv_Prod =
                ((String)(tblProductos.getValueAt(filaActual, 11))).trim();
        VariablesVentas.vPorc_Dcto_1 =
                ((String)(tblProductos.getValueAt(filaActual, 5))).trim();
        VariablesVentas.vVal_Prec_Vta =
                ((String)(tblProductos.getValueAt(filaActual, 6))).trim();

        //Obtenemos la cantidad que recargo
        //31.10.2007  dubilluz  modificacion
        //VariablesVentas.vMontoARecargar_Temp = ((String)(tblProductos.getValueAt(filaActual,6))).trim();
        VariablesVentas.vMontoARecargar_Temp =
                ((String)(tblProductos.getValueAt(filaActual, 4))).trim();

        VariablesVentas.vNumeroARecargar =
                ((String)(tblProductos.getValueAt(filaActual, 13))).trim();
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";

        DlgIngresoRecargaVirtual dlgIngresoRecargaVirtual =
            new DlgIngresoRecargaVirtual(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = true;
        dlgIngresoRecargaVirtual.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesVentas.vVal_Prec_Lista_Tmp =
                    ((String)(tblProductos.getValueAt(filaActual, 17))).trim();
            //Ahora se grabara S/1.00
            //31.10.2007 dubilluz modificacion
            //VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar));
            VariablesVentas.vVal_Prec_Vta =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(ConstantsVentas.PrecioVtaRecargaTarjeta));
            VariablesVentas.vVal_Prec_Lista =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Lista_Tmp) *
                                              FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar));
            seleccionaProducto(filaActual);

            System.out.println("VariablesVentas.vNumeroARecargar : " +
                               VariablesVentas.vNumeroARecargar);
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
        System.out.println("\n ************** veamos en valod de INDICADORT_S: " +
                           FarmaConstants.INDICADOR_S + " \n ********");
        if (tblProductos.getRowCount() == 0)
            return;
        /*
     *
     * */


        int row = tblProductos.getSelectedRow();
        String indicadorProdVirtual =
            FarmaUtility.getValueFieldJTable(tblProductos, row, 14);
        String indicadorPromocion =
            FarmaUtility.getValueFieldJTable(tblProductos, row,
                                             COL_RES_IND_PACK);
        String indTratamiento =
            FarmaUtility.getValueFieldJTable(tblProductos, row,
                                             COL_RES_IND_TRAT);

        if (indicadorPromocion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            //String codProm=
            //System.out.println("Selecciona promocion "+codprom);
            if (row > -1) {
                muestraDetallePromocion(row);
                aceptaPromocion();
            }
        }

        else {
            if (indicadorProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                if (indTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    muestraIngresoCantidad();
                } else {
                    //FarmaUtility.showMessage(this,"No puede modificar este registro, eliminelo y vuelva a ingresarlo.",null);
                    muestraTratamiento();

                }
            } else {
                String tipoProdVirtual =
                    FarmaUtility.getValueFieldJTable(tblProductos, row, 15);
                if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
                    muestraIngresoTelefonoMonto();
                } else if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
                    muestraIngresoCantidad_Tarjeta_Virtual();
                    //FarmaUtility.showMessage(this, "No es posible cambiar la cantidad de este producto. Verifique!!!", tblProductos);
                    //return;
                }
            }
        }
        if (VariablesVentas.vIndDireccionarResumenPed) {
            /*if(VariablesVentas.vIndPedConProdVirtual)
      if(FarmaUtility.rptaConfirmDialog(this,"¿Desea agregar más productos al pedido?"))
        {
        agregarProducto();
        }*/

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
                double valTotal =
                    FarmaUtility.getDecimalNumber(lblTotalS.getText());
                System.out.println("TotalS: " + lblTotalS.getText());
                String valor =
                    DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio,
                                                pCodCli,
                                                FarmaUtility.formatNumber(valTotal),
                                                FarmaConstants.INDICADOR_S);
                System.out.println("Diferencia: " + valor);
                diferencia = FarmaUtility.getDecimalNumber(valor);
                if (diferencia >= 0) {
                    System.out.println("OK");
                    return true;
                } else {
                    FarmaUtility.showMessage(this,
                                             "No se puede generar el pedido. \nEl cliente excede en S/. " +
                                             FarmaUtility.formatNumber((diferencia *
                                                                        -1)) +
                                             " el limite de su crédito.",
                                             null);
                    //FarmaUtility.moveFocusJTable(tblProductos);
                    FarmaUtility.moveFocus(txtDescProdOculto);
                    return false;
                }
            } catch (SQLException sql) {
                sql.printStackTrace();
                FarmaUtility.showMessage(this, "Error al grabar información.",
                                         null);
                //FarmaUtility.moveFocusJTable(tblProductos);
                FarmaUtility.moveFocus(txtDescProdOculto);
                return false;
            }
        } else { //El convenio no tiene BD
            System.out.println("Arreglo: " +
                               VariablesConvenio.vArrayList_DatosConvenio);
            return true;
        }
    }

    private void grabarPedidoConvenio(String pCodCli, String pNumDocIden,
                                      String pCodTrabEmp, String pApePat,
                                      String pApeMat, String pFecNac,
                                      String pCodSol, String pNumTel,
                                      String pDirecCli, String pNomDist,
                                      String pCodInterno,
                                      String pNomTrabajador, String pCodCliDep,
                                      String pCodTrabEmpDep) {
        try {
            DBConvenio.grabarPedidoConvenio(VariablesVentas.vNum_Ped_Vta,
                                            VariablesConvenio.vCodConvenio,
                                            pCodCli, pNumDocIden, pCodTrabEmp,
                                            pApePat, pApeMat, pFecNac, pCodSol,
                                            VariablesConvenio.vPorcDctoConv,
                                            VariablesConvenio.vPorcCoPago,
                                            pNumTel, pDirecCli, pNomDist,
                                            VariablesConvenio.vValCoPago,
                                            pCodInterno, pNomTrabajador,
                                            pCodCliDep, pCodTrabEmpDep);
            System.out.println("Grabar Pedido Convenio");
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al grabar el la informacion del pedido y el convenio: \n" +
                    sql, null);
            //FarmaUtility.moveFocusJTable(tblProductos);
            FarmaUtility.moveFocus(txtDescProdOculto);
        }
    }


    private void grabarPedidoConvenioBTLMF(String pCodCampo, String pDesCampo,
                                           String pNombCampo, String pCodValorCampo) {
        try {

            DBConvenioBTLMF.grabarPedidoVenta(pCodCampo, pDesCampo, pNombCampo, pCodValorCampo);

            System.out.println("Grabar Pedido Convenio BTL MF");
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al grabar el la informacion del pedido y el convenio BTL MF: \n" +
                    sql, null);
        }
    }


    private void grabarFormaPagoPedido(String pCodFormaPago, String pNumPedVta,
                                       String pImPago, String pTipMoneda,
                                       String pTipoCambio, String pVuelto,
                                       String pImTotalPago, String pNumTarj,
                                       String pFecVencTarj, String pNomCliTarj,
                                       String pCantCupon) {
        try {
            //DBCaja.grabaFormaPagoPedido(pCodFormaPago,pImPago,pTipMoneda,pTipoCambio,pVuelto,pImTotalPago,pNumTarj,pFecVencTarj,pNomCliTarj,pCantCupon);
            DBConvenio.grabaFormaPagoPedido(pCodFormaPago, pNumPedVta, pImPago,
                                            pTipMoneda, pTipoCambio, pVuelto,
                                            pImTotalPago, pNumTarj,
                                            pFecVencTarj, pNomCliTarj,
                                            pCantCupon);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Error al grabar la forma de pago del pedido." +
                                     sql.getMessage(), null);
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
    private double validaCreditoCliente(String pCodCli, String pCodPago,
                                        KeyEvent e) throws SQLException {
        double diferencia = 0;
        double valTotal = FarmaUtility.getDecimalNumber(pCodPago);
        String vkF = "";
        System.out.println("Monto Copago: " + pCodPago);

        String vIndLinea =
            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                           FarmaConstants.INDICADOR_N);


        if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            String valor =
                DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio,
                                            pCodCli,
                                            FarmaUtility.formatNumber(valTotal),
                                            FarmaConstants.INDICADOR_S);
            System.out.println("Diferencia: " + valor);
            diferencia = FarmaUtility.getDecimalNumber(valor);

            if (diferencia < 0) {
                if (VariablesConvenio.vIndSoloCredito.equals(FarmaConstants.INDICADOR_N)) {
                    valTotal = valTotal + diferencia;
                    VariablesConvenio.vValCoPago =
                            FarmaUtility.formatNumber(valTotal);
                    if (FarmaUtility.rptaConfirmDialog(this,
                                                       "El cliente excede en S/. " +
                                                       FarmaUtility.formatNumber((diferencia *
                                                                                  -1)) +
                                                       " el limite de su crédito. \n Está seguro de continuar?")) {
                        if (e.getKeyCode() == KeyEvent.VK_F4) {

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
                        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
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
                        }
                    }
                } else if (VariablesConvenio.vIndSoloCredito.equals(FarmaConstants.INDICADOR_S)) {
                    FarmaUtility.showMessage(this,
                                             "El cliente excede en S/. " +
                                             FarmaUtility.formatNumber((diferencia *
                                                                        -1)) +
                                             " el limite de su crédito. \n ¡No puede realizar la venta!",
                                             tblProductos);
                } else {
                    FarmaUtility.showMessage(this,
                                             "No se pudo determinar el indicador del convenio.",
                                             tblProductos);
                }
            } else {
                //valTotal = valTotal + diferencia;
                VariablesConvenio.vValCoPago =
                        FarmaUtility.formatNumber(valTotal);
                if (e.getKeyCode() == KeyEvent.VK_F4) {
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
                    /*}*/
                } else if (e.getKeyCode() == KeyEvent.VK_F1 ||
                           e.getKeyChar() == '+') {
                    // grabarPedidoVenta(ConstantsVentas.TIPO_COMP_BOLETA);
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

    private void validaConvenio(KeyEvent e, String vCoPagoConvenio) {

        String vkF = "";
        if (pedidoEnProceso) {
            //FarmaUtility.showMessage(this,"Pedido en proceso.",tblProductos);
            return;
        }
        pedidoEnProceso = true;
        if (VariablesVentas.vEsPedidoConvenio) //Ha elegido un convenio y un cliente
        {
            System.out.println("VariablesConvenio.vArrayList_DatosConvenio : " +
                               VariablesConvenio.vArrayList_DatosConvenio);
            String vCodCli =
                "" + VariablesConvenio.vArrayList_DatosConvenio.get(1);
            if (!vCodCli.equalsIgnoreCase("")) {
                String mensaje = "";
                double vCredDisp;
                //1° Obtiene valor de copago
                try {
                    double totalS =
                        FarmaUtility.getDecimalNumber(lblTotalS.getText());
                    mensaje = "Error al obtener el copago del convenio.\n";

                    if (FarmaUtility.getDecimalNumber(vCoPagoConvenio) != 0) {
                        //verificar la conexión con MATRIZ
                        String vIndLinea =
                            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                           FarmaConstants.INDICADOR_N);

                        String vCoPago =
                            DBConvenio.obtieneCoPagoConvenio(VariablesConvenio.vCodConvenio,
                                                             vCodCli,
                                                             FarmaUtility.formatNumber(totalS));

                        if (FarmaUtility.getDecimalNumber(vCoPago) != 0) {
                            //2° Evalua credito disponible
                            vCredDisp =
                                    validaCreditoCliente(vCodCli, vCoPago, e);
                        }
                    } else {
                        if (e.getKeyCode() == KeyEvent.VK_F4) {
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
                            /*}*/
                        } else if (e.getKeyCode() == KeyEvent.VK_F1 ||
                                   e.getKeyChar() == '+') {
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
                            //}
                        }
                    }
                } catch (SQLException sql) {
                    sql.printStackTrace();
                    FarmaUtility.showMessage(this, mensaje + sql.getMessage(),
                                             tblProductos);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    FarmaUtility.showMessage(this, mensaje + ex.getMessage(),
                                             tblProductos);
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_F4) {
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
                } else if (e.getKeyCode() == KeyEvent.VK_F1 ||
                           e.getKeyChar() == '+') {
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
                    /*}*/
                }
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_F4) {
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
            } else if (e.getKeyCode() == KeyEvent.VK_F1 ||
                       e.getKeyChar() == '+') {

                if(cargaLogin_verifica())
                {
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
                }
            }
        }

        pedidoEnProceso = false;
        if (VariablesVentas.vIndVolverListaProductos){

            agregarProducto();
        }
    }

    /**
     * Graba el detalle de Producto por promocion
     * @param pTiComprobante
     * @param array
     * @param pFila
     * @param tipo indica si es Producto simple o de una promocion
     * @throws Exception
     * @author dubilluz
     * @since  19.06.2007
     */
    private void grabarDetalle(String pTiComprobante, ArrayList array,
                               int pFila, int tipo) throws Exception {

        VariablesVentas.vSec_Ped_Vta_Det = String.valueOf(pFila);
        VariablesVentas.vCod_Prod = ((String)(array.get(0))).trim();
        VariablesVentas.vCant_Ingresada =
                String.valueOf(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(((String)(array.get(4))).trim())));
        //VariablesVentas.vVal_Prec_Vta = ((String)(array.get(6))).trim();
        VariablesVentas.vTotalPrecVtaProd =
                FarmaUtility.getDecimalNumber(((String)(array.get(7))).trim());
        VariablesVentas.vVal_Prec_Vta =
                FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd /
                                          Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                          3);
        VariablesVentas.vPorc_Dcto_1 =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(5))).trim()));
        System.err.println("***-VariablesVentas.vPorc_Dcto_1 " +
                           VariablesVentas.vCod_Prod + " - " +
                           VariablesVentas.vPorc_Dcto_1);
        if (tipo == ConstantsVentas.IND_PROD_PROM)
            VariablesVentas.vPorc_Dcto_2 =
                    String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(20))).trim()));
        else
            VariablesVentas.vPorc_Dcto_2 =
                    String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(COL_RES_DSCTO_2))).trim()));

        System.out.println("***-VariablesVentas.desc_2 " +
                           VariablesVentas.vPorc_Dcto_2);
        VariablesVentas.vPorc_Dcto_Total =
                VariablesVentas.vPorc_Dcto_1; //cuando es pedido normal, el descuento total siempre es el descuento 1
        VariablesVentas.vVal_Total_Bono =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(8))).trim()));
        VariablesVentas.vVal_Frac = ((String)(array.get(10))).trim();
        VariablesVentas.vEst_Ped_Vta_Det =
                ConstantsVentas.ESTADO_PEDIDO_DETALLE_ACTIVO;
        VariablesVentas.vSec_Usu_Local = FarmaVariables.vNuSecUsu;
        //VariablesVentas.vVal_Prec_Lista = String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(3))).trim()));
        VariablesVentas.vVal_Prec_Lista =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(6))).trim()));
        VariablesVentas.vVal_Igv_Prod = ((String)(array.get(11))).trim();
        VariablesVentas.vUnid_Vta = ((String)(array.get(2))).trim();
        VariablesVentas.vNumeroARecargar = ((String)(array.get(13))).trim();
        //System.out.println("***-VariablesVentas.vVal_Prec_Pub "+VariablesVentas.vVal_Prec_Pub);
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
        System.out.println("Promo eeeee " + array);
        System.out.println("Promo al detalle : " +
                           VariablesVentas.vCodPromoDet);

        if (tipo == ConstantsVentas.IND_PROD_SIMPLE) {
            VariablesVentas.vIndOrigenProdVta =
                    array.get(COL_RES_ORIG_PROD).toString().trim();
            VariablesVentas.vCantxDia =
                    array.get(COL_RES_CANT_XDIA).toString().trim();
            VariablesVentas.vCantxDias =
                    array.get(COL_RES_CANT_DIAS).toString().trim();
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
        //JCHAVEZ 20102009
        DBVentas.grabarDetallePedido();
        VariablesVentas.vCodPromoDet = "";

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

    /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  15.08.08
     */
    private void agregarClienteCampana(String pCodCampana, String pCodCupon,
                                       String pCodCli, String pDniCliente,
                                       String pNomcliente,
                                       String pApePatCliente,
                                       String pApeMatCliente, String pTelefono,
                                       String pNumCMP, String pMedico,
                                       String pSexo, String pFecNacimiento) {
        try {
            DBCampana.agregarClienteCampana(pCodCampana, pCodCupon, pCodCli,
                                            pDniCliente, pNomcliente,
                                            pApePatCliente, pApeMatCliente,
                                            pTelefono, pNumCMP, pMedico, pSexo,
                                            pFecNacimiento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
  * Elimina la promocion y su detalle
  * @author : dubilluz
  * @since  : 20.06.2007
  */
    /* private void eliminaPromocion(String codProm){
  removeItemArray(VariablesVentas.vArrayList_Promociones,codProm,0);
  //cambiar el "0" por la posicion q coloque Jorge
  removeItemArray(VariablesVentas.vArrayList_Prod_Promociones,codProm,0);
  System.out.println("Tamaño de Resumen   :"+VariablesVentas.vArrayList_ResumenPedido.size()+ "");
  System.out.println("Tamaño de Promocion :"+VariablesVentas.vArrayList_Promociones.size()  + "");
 }
 private void eliminadetallePromocion(String codProm){
  removeItemArray(VariablesVentas.vArrayList_Promociones,codProm,0);
  //cambiar el "0" por la posicion q coloque Jorge
  removeItemArray(VariablesVentas.vArrayList_Prod_Promociones,codProm,0);
  System.out.println("Tamaño de Resumen   :"+VariablesVentas.vArrayList_ResumenPedido.size()+ "");
  System.out.println("Tamaño de Promocion :"+VariablesVentas.vArrayList_Promociones.size()  + "");
 }*/
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
            System.out.println(cod + "<<<<<" + codProm);
            if (cod.equalsIgnoreCase(codProm)) {
                array.remove(i);
                i = -1;
            }
        }
    }

    /**
     * busca si el parametro se encuentra en algun
     * item del Array
     * @author : dubilluz
     * @since  : 21.06.2007
     */
    private
    //lo utilizare para buscar si un producto se pidio en promocion
    //em el Lista de ProdPromociones
    boolean buscaenArray(ArrayList array, String parametro, int pos) {
        String param = "";
        for (int i = 0; i < array.size(); i++) {
            param = (String)((ArrayList)array.get(i)).get(0);
            if (param.equalsIgnoreCase(parametro))
                return true;
        }
        return false;
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
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size();
             i++) {
            aux =
(ArrayList)(VariablesVentas.vArrayList_Prod_Promociones.get(i));
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
            System.out.println("AUXXXXXXXXXXXXXXXXXXXXXXXX 1: " + aux1);
            System.out.println("SIZE: SIZE: " + aux1.size());
            if (aux1.size() <=
                cantCampos) { //cantidad de campos ASOSA, 18.12.2009
                for (int j = i + 1; j < array.size(); j++) {
                    aux2 = (ArrayList)(array.get(j));
                    if (aux2.size() <=
                        cantCampos) { //cantidad de campos ASOSA, 18.12.2009
                        if ((((String)(aux1.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                            cantidad1 =
                                    Integer.parseInt(((String)(aux1.get(4))).trim());
                            cantidad2 =
                                    Integer.parseInt(((String)(aux2.get(4))).trim());
                            suma = cantidad1 + cantidad2;
                            aux1.set(4, suma + "");
                            ((ArrayList)(array.get(j))).add("Revisado");
                        }
                    }
                }
                nuevo.add(aux1);
            }
        }
        System.out.println("Agrupado :" + nuevo.size());
        System.out.println("Aggrup Elment :" + nuevo);
        return nuevo;
    }


    /**
     * Acepta Modificacion de promocion
     * @author : dubilluz
     * @since  : 04.07.2007
     */
    private void aceptaPromocion() {

        for (int i = 0;
             i < VariablesVentas.vArrayList_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Promociones_temporal = new ArrayList();

        for (int i = 0;
             i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size();
             i++)
            VariablesVentas.vArrayList_Prod_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
        //System.out.println(VariablesVentas.vArrayList_ResumenPedido.size());
    }

    /**
     * Muestra el dialogo de Ingreso de Cantidad para Producto Tarjeta Virtual
     * @author : dubilluz
     * @since  : 29.08.2007
     */
    private void muestraIngresoCantidad_Tarjeta_Virtual() {
        if (tblProductos.getRowCount() == 0)
            return;
        System.out.println("DIEGO UBILLUZ >>  " +
                           VariablesVentas.vArrayList_Prod_Tarjeta_Virtual);
        VariablesVentas.cantidad_tarjeta_virtual =
                Integer.parseInt(FarmaUtility.getValueFieldJTable(tblProductos,
                                                                  tblProductos.getSelectedRow(),
                                                                  4));
        //------

        print_variables();
        //-------
        DlgIngresoCantidadTarjetaVirtual dlgIngresoCantidad =
            new DlgIngresoCantidadTarjetaVirtual(myParentFrame, "", true);
        dlgIngresoCantidad.setVisible(true);
        if (FarmaVariables.vAceptar) {
            int fila = tblProductos.getSelectedRow();
            int cantidad_new = VariablesVentas.cantidad_tarjeta_virtual;
            int cantidad_old =
                Integer.parseInt(FarmaUtility.getValueFieldJTable(tblProductos,
                                                                  fila, 4));
            System.out.println("Cantidad Antigua :  " + cantidad_old);
            System.out.println("Cantidad Nueva   :  " + cantidad_new);
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
        System.out.println("VariablesVentas.vTotalPrecVtaProd : " +
                           VariablesVentas.vTotalPrecVtaProd);

        int row = tblProductos.getSelectedRow();
        VariablesVentas.vVal_Prec_Vta =
                FarmaUtility.getValueFieldJTable(tblProductos, row, 6);
        /**
     * Modificado para la Cantidad que se compra
     */
        VariablesVentas.vCant_Ingresada = cantidad.trim();
        VariablesVentas.vTotalPrecVtaProd =
                (FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada) *
                 FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta));
        operaProductoEnJTable(row);
        operaProductoEnArrayList(row);
        calculaTotalesPedido();
        print_variables();
    }

    private void print_variables() {
        ////resultado despues de tener lo q haces
        System.out.println("VariablesVentas.vCant_Ingresada  " +
                           VariablesVentas.vCant_Ingresada);
        System.out.println("VariablesVentas.vPorc_Dcto_1  " +
                           VariablesVentas.vPorc_Dcto_1);
        System.out.println("VariablesVentas.vVal_Prec_Vta  " +
                           VariablesVentas.vVal_Prec_Vta);
        System.out.println("VariablesVentas.vTotalPrecVtaProd  " +
                           VariablesVentas.vTotalPrecVtaProd);
        System.out.println("VariablesVentas.vVal_Igv_Prod  " +
                           VariablesVentas.vVal_Igv_Prod);
        /////////lo q opera en el la parte de abajo
        double totalBruto = 0.00;
        double totalDscto = 0.00;
        double totalIGV = 0.00;
        double totalNeto = 0.00;
        double redondeo = 0.00;
        int cantidad = 0;
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            System.out.println(VariablesVentas.vArrayList_ResumenPedido.get(i));
            cantidad =
                    Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(4));
            totalBruto +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(3)) *
                    cantidad;
            totalIGV +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(12));
            totalNeto +=
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(7));
            System.out.println("Valores del Array " + " cantidad : " +
                               cantidad + "  Tbruto :" + totalBruto +
                               "  TIgv  : " + totalIGV + "  TNeto :" +
                               totalNeto);
        }

        totalDscto = (totalBruto - totalNeto);
        totalDscto =
                FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto,
                                                                        2));

        System.out.println("Total bruto " + totalBruto);
        System.out.println("Total Dscto" + totalDscto);
        System.out.println("Total descto / bruto (" +
                           (totalDscto * 100 / totalBruto) + "%)");
        System.out.println("Total IGV  " + totalIGV);
    }

    /**
     * Proceso para revisar eventos externos para los pedidos
     * como productos de regalo ,cumpones y/o complementarios
     * @author dubilluz
     * @since  08.04.2007
     */
    private boolean procesoProductoRegalo(String pNumped,
                                          int pSecDet) throws Exception {
        //--Se verifica si puede o no acceder al regalo
        ArrayList arrayLista = new ArrayList();
        ArrayList vAEncartesAplicables = new ArrayList();
        DBVentas.obtieneEncartesAplicables(vAEncartesAplicables);
        System.out.println("...Encartes aplicables : " + vAEncartesAplicables);
        String cod_encarte = "";
        ArrayList vMensajesRegalo = new ArrayList();
        for (int e = 0; e < vAEncartesAplicables.size(); e++) {
            cod_encarte =
                    FarmaUtility.getValueFieldArrayList(vAEncartesAplicables,
                                                        e, 0);
            System.out.println("...Procesando Encarte : " + cod_encarte);

            DBVentas.analizaProdEncarte(arrayLista, cod_encarte);
            System.out.println("RESULTADO " + arrayLista);
            if (arrayLista.size() > 1) {
                String[] listEncarte =
                    arrayLista.get(0).toString().substring(1,
                                                           arrayLista.get(0).toString().length() -
                                                           1).split("&");
                System.out.println("**************** " + listEncarte);
                System.out.println("********CON_COLUM_COD_PROD_REGALO* " +
                                   CON_COLUM_COD_PROD_REGALO);
                VariablesVentas.vCodProd_Regalo =
                        listEncarte[CON_COLUM_COD_PROD_REGALO];
                VariablesVentas.vCantidad_Regalo =
                    (int)FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_CANT_MAX_PROD_REGALO]);
                VariablesVentas.vMontoMinConsumoEncarte =
                        FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_MONT_MIN_ENCARTE]);
                VariablesVentas.vVal_Frac =
                        "1"; //ERIOS 04.06.2008 Por definición de Regalo, es en unidad de presentación.

                System.out.println("VariablesVentas.vCodProd_Regalo  " +
                                   VariablesVentas.vCodProd_Regalo);
                System.out.println("VariablesVentas.vCantidad_Regalo  " +
                                   VariablesVentas.vCantidad_Regalo);
                System.out.println("VariablesVentas.vMontoMinConsumoEncarte  " +
                                   VariablesVentas.vMontoMinConsumoEncarte);

                arrayLista.remove(0);
                ArrayList arrayProdEncarte = (ArrayList)arrayLista.clone();
                if (arrayProdEncarte.size() > 0) {
                    System.out.println("arrayProdEncarte " + arrayProdEncarte);
                    System.out.println("listEncarte " + listEncarte);
                    double monto_actual_prod_encarte =
                        calculoMontoProdEncarte(arrayProdEncarte, 2);

                    //Actualizo regalo si es codigo  cero '0'
                    /*if (VariablesVentas.vCodProd_Regalo.equalsIgnoreCase("0")) {
                        VariablesVentas.vCodProd_Regalo = DBVentas.getEligeRegaloMonto(cod_encarte.trim(),VariablesVentas.vMontoMinConsumoEncarte);
                    }*/

                    //Vuelve a verificar el prod. regalo si este da regalo de acuerdo al monto.
                    ArrayList arrayRegMontos = new ArrayList();
                    DBVentas.getDatosRegaloxMonto(arrayRegMontos,cod_encarte.trim(),monto_actual_prod_encarte);

                    if (arrayRegMontos.size()>1)
                                            {
                                                arrayRegMontos.remove(0);
                                                ArrayList arrayDatosRegalo = (ArrayList)arrayRegMontos.clone();

                                                VariablesVentas.vCodProd_Regalo =
                                                                    FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,0);

                                                VariablesVentas.vMontoMinConsumoEncarte =
                                                                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,1).trim());
                                                VariablesVentas.vMontoProxMinConsumoEncarte=
                                                                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,2).trim());
                                                VariablesVentas.vCodProdProxRegalo =
                                                                    FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,3).trim();
                                                VariablesVentas.vDescProxProd_Regalo =
                                                                    FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,4).trim();

                                            } else
                                            {
                                                    VariablesVentas.vMontoProxMinConsumoEncarte=0;
                                                    VariablesVentas.vDescProxProd_Regalo ="";
                                                    VariablesVentas.vCodProdProxRegalo="";
                                            }

                    String desc_prod =
                        DBVentas.obtieneDescProducto(VariablesVentas.vCodProd_Regalo);


                    double stock_regalo=0,stock_prox_regalo=0;

                    if (VariablesVentas.vCodProd_Regalo.trim().length()>0)
                        stock_regalo =
                                FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProd_Regalo).trim());

                    if (VariablesVentas.vCodProdProxRegalo.trim().length()>0)
                        stock_prox_regalo =
                                FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProdProxRegalo).trim());

                    if (stock_regalo > 0) {
                    if (monto_actual_prod_encarte >=
                        VariablesVentas.vMontoMinConsumoEncarte) {
                        System.out.println("...Procesa a añadir producto de regalo");
                        añadirProductoRegalo(VariablesVentas.vCodProd_Regalo,
                                             VariablesVentas.vCantidad_Regalo,
                                                 pNumped, pSecDet, 0,
                                                 desc_prod);
                        pSecDet++;
                        }
                    }
                    if(stock_regalo==0){
                        if (monto_actual_prod_encarte >=
                            VariablesVentas.vMontoMinConsumoEncarte) {
                                      ArrayList array=new ArrayList();

                            DBVentas.getEncarteAplica(array,
                                                      monto_actual_prod_encarte,
                                                      cod_encarte.trim());

                                        if (array.size() > 0) {
                                double stk_prod =
                                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array,
                                                                                                      0,
                                                                                                      4).trim());
                                if (stk_prod > 0) {
                                    añadirProductoRegalo(FarmaUtility.getValueFieldArrayList(array,
                                                                                             0,
                                                                                             0).trim(),
                                                         (int)(FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array,
                                                                                                                                 0,
                                                                                                                                 2).trim())),
                                                         pNumped, pSecDet, 0,
                                                         FarmaUtility.getValueFieldArrayList(array,
                                                                                             0,
                                                                                             1).trim());
                                                                             pSecDet++;

                                                 }
                                        }
                                   }
                    } else {

                    if(stock_regalo==0 && stock_prox_regalo>0){
                            if (monto_actual_prod_encarte <
                                VariablesVentas.vMontoProxMinConsumoEncarte)

                                añadirProductoRegalo(VariablesVentas.vCodProdProxRegalo,
                                                                             VariablesVentas.vCantidad_Regalo,
                                                     pNumped, pSecDet, 0,
                                                     VariablesVentas.vDescProxProd_Regalo);
                                                        pSecDet++;
                            }
                  }

                    if(stock_regalo>0){
                        if (monto_actual_prod_encarte <
                            VariablesVentas.vMontoMinConsumoEncarte) {

                                                          String mensaje =
                                                              "Para llevarse de regalo " + VariablesVentas.vCantidad_Regalo +
                                                              " " + desc_prod + " " + " le faltan S/." +
                                                              FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte -
                                                                                        monto_actual_prod_encarte) +
                                                              ".\n" +
                                                              "¿Desea añadir más productos para llevar el regalo?";
                                                          System.out.println(mensaje);
                                                      }else{

                                               if(stock_prox_regalo>0){
                                if (monto_actual_prod_encarte <
                                    VariablesVentas.vMontoProxMinConsumoEncarte) {

                                                   String mensaje =
                                        "Para llevarse de regalo " +
                                        VariablesVentas.vCantidad_Regalo +
                                        " " +
                                        VariablesVentas.vDescProxProd_Regalo +
                                        " " + " le faltan S/." +
                                                       FarmaUtility.formatNumber(VariablesVentas.vMontoProxMinConsumoEncarte -
                                                                                 monto_actual_prod_encarte) +
                                                       ".\n" +
                                                       "¿Desea añadir más productos para llevar el regalo?";
                                                   System.out.println(mensaje);


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

    private boolean buscaElementArray(ArrayList pArray, String pCodbusq,
                                      int pTipo) {

        if (pTipo == 1) // --busqueda para Capana Cupon
            for (int i = 0; i < pArray.size(); i++) {
                if (pArray.get(i).toString().trim().equalsIgnoreCase(pCodbusq.trim()))
                    return true;
            }
        else if (pTipo == 2) // --busqueda para Multimarca y Encarte
            for (int i = 0; i < pArray.size(); i++) {
                if (pArray.get(i).toString().trim().substring(1,
                                                              pArray.get(i).toString().length() -
                                                              1).equalsIgnoreCase(pCodbusq.trim()))
                    return true;
            }
        return false;
    }

    private double calculoMontoProdEncarte(ArrayList pArray, int pTipo) {
        double totalNeto = 0.00;
        double totalParcial = 0.00;
        int cantidad = 0;
        String cod_prod = "";
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            //System.out.println(VariablesVentas.vArrayList_ResumenPedido.get(i));
            cod_prod =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 0);
            totalParcial =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i,
                                                                                      7));

            //System.out.println(cod_prod + " " +totalParcial);

            if (buscaElementArray(pArray, cod_prod, pTipo))
                totalNeto = totalNeto + totalParcial;
        }
        //System.out.println("*******");
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size();
             i++) {
            //System.out.println(VariablesVentas.vArrayList_Prod_Promociones.get(i));
            cod_prod =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Prod_Promociones,
                                                        i, 0);
            totalParcial =
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).get(7));

            //System.out.println(cod_prod + " " +totalParcial);
            if (buscaElementArray(pArray, cod_prod, pTipo))
                totalNeto = totalNeto + totalParcial;
        }
        //System.out.println("*******");
        //System.out.println("Monto total de productos de encarte " + totalNeto);

        return totalNeto;
    }

    /**
     *
     */
    private void añadirProductoRegalo(String pCodProd, int pCantidad,
                                      String pNumped, int pSecDet,
                                      int pValPrec, String pDescProd) {
        VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
        if ( /*UtilityVentas.actualizaStkComprometidoProd(pCodProd,pCantidad,
                                    ConstantsVentas.INDICADOR_A,
                                    ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                    pCantidad,
                                                  true,
                                                  this,
                                                  tblProductos))*/
            UtilityVentas.operaStkCompProdResp(pCodProd, //ASOSA, 08.07.2010
                pCantidad, ConstantsVentas.INDICADOR_A,
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, pCantidad,
                true, this, tblProductos, "")) {
            ArrayList arrayDatosProd = new ArrayList();
            try {
                //DBVentas.obtieneInfoProducto(arrayDatosProd,pCodProd.trim());
                //DBVentas.grabaProductoRegalo(pNumped,pCodProd,pSecDet,pCantidad,pValPrec); //Antes
                DBVentas.grabaProductoRegalo_02(pNumped, pCodProd, pSecDet,
                                                pCantidad, pValPrec,
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
                String mensaje =
                    "Usted se llevara de regalo " + pCantidad + " ";
                mensaje += pDescProd + ".";
                System.out.println(mensaje);
                //FarmaUtility.showMessage(this,mensaje,null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(" arrayDatosProd " + arrayDatosProd);
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
        DBVentas.procesaCampanaCupon(pNumPed,
                                     ConstantsVentas.TIPO_CAMPANA_CUPON,
                                     VariablesFidelizacion.vDniCliente);
    }
    /* private void procesoCampanaCupon(String pNumPed) throws Exception {
    //--Se verifica si puede o no acceder a las campañas
    ArrayList vACampCuponplicables = new ArrayList();
    DBVentas.obtieneCampCuponAplicables(vACampCuponplicables,
                                        ConstantsVentas.TIPO_CAMPANA_CUPON,
                                        pNumPed
                                        );
    System.out.println("...obtiene campañas con codigo prod : " + vACampCuponplicables);
    String cod_camp_cupon = "";

    ArrayList listaCamProd =  new  ArrayList();
    listaCamProd = (ArrayList)vACampCuponplicables.clone();

    String cod_prod= "",cod_prod_2  = "",cod_prod_elimnar  = "";
    String cod_campana= "",cod_campana_2 = "",cod_campana_eliminar = "";
    String tipo= "",tipo_2  = "";
    double valor=0.0,valor_2  = 0.0;
      System.out.println("****campaña cupon *****");
   //System.out.println("...obtiene campañas con codigo prod : " + vACampCuponplicables);


    System.out.println("** listaCamProd " + listaCamProd);

    ArrayList listaNuevaCampProd =  new  ArrayList();

    for(int i=0;i<vACampCuponplicables.size();i++){

        cod_prod = FarmaUtility.getValueFieldArrayList(vACampCuponplicables,i,P_COL_COD_PROD).trim();
        cod_campana = FarmaUtility.getValueFieldArrayList(vACampCuponplicables,i,P_COL_COD_CAMPANA ).trim();
        tipo =  FarmaUtility.getValueFieldArrayList(vACampCuponplicables,i,P_COL_TIPO_CUPON).trim();
        valor =   FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(vACampCuponplicables,i,P_COL_VALOR_CUPON).trim());

        for(int j=0;j<listaCamProd.size();j++){
            cod_prod_2 = FarmaUtility.getValueFieldArrayList(listaCamProd,j,P_COL_COD_PROD).trim();
            cod_campana_2 = FarmaUtility.getValueFieldArrayList(listaCamProd,j,P_COL_COD_CAMPANA ).trim();
            tipo_2=  FarmaUtility.getValueFieldArrayList(listaCamProd,j,P_COL_TIPO_CUPON).trim();
            valor_2 =   FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(vACampCuponplicables,j,P_COL_VALOR_CUPON).trim());

              if(cod_prod.equalsIgnoreCase(cod_prod_2))
               {
                  if(!cod_campana.equalsIgnoreCase(cod_campana_2)){
                       if(!tipo.equalsIgnoreCase(tipo_2)){
                           if(tipo.equalsIgnoreCase(ConstantsVentas.TIPO_MONTO)){
                              listaCamProd.remove(j);
                              j++;
                           }
                           else
                           {
                               for(int y=0;y<listaCamProd.size();y++){
                                   cod_prod_elimnar = FarmaUtility.getValueFieldArrayList(listaCamProd,y,P_COL_COD_PROD).trim();
                                   cod_campana_eliminar = FarmaUtility.getValueFieldArrayList(listaCamProd,y,P_COL_COD_CAMPANA ).trim();
                                   if(cod_prod_elimnar.equals(cod_prod)&&cod_campana_eliminar.equals(cod_campana))
                                   {
                                      listaCamProd.remove(y);
                                      j++;
                                      break;
                                   }
                               }
                           }
                       }
                       else{
                           if(valor==valor_2){
                              listaCamProd.remove(j);
                              j++;
                           }
                           else{
                               if(valor>valor_2){
                                  listaCamProd.remove(j);
                               }
                               else{
                                   for(int y=0;y<listaCamProd.size();y++){
                                       cod_prod_elimnar = FarmaUtility.getValueFieldArrayList(listaCamProd,y,P_COL_COD_PROD).trim();
                                       cod_campana_eliminar = FarmaUtility.getValueFieldArrayList(listaCamProd,y,P_COL_COD_CAMPANA ).trim();
                                       if(cod_prod_elimnar.equals(cod_prod)&&cod_campana_eliminar.equals(cod_campana))
                                       {
                                          listaCamProd.remove(y);
                                          j++;
                                          break;
                                       }
                                    }
                                  }
                           }
                       }
                  }
               }
            cod_prod_2  = "";
            cod_campana_2 = "";
            tipo_2  = "";
            cod_prod_elimnar  = "";
            cod_campana_eliminar = "";

        }

        cod_prod= "";
        tipo= "";
        cod_campana= "";
        valor=0.0;
        valor_2  = 0.0;
    }

      System.out.println("** listaCamProd " + listaCamProd);

      //listaNuevaCampProd = (ArrayList)listaCamProd.clone();
      ArrayList aux =  new ArrayList();
      for(int k=0 ;k<listaCamProd.size();k++){
          cod_campana = FarmaUtility.getValueFieldArrayList(listaCamProd,k,P_COL_COD_CAMPANA ).trim();
          aux =  new ArrayList();
          aux.add(cod_campana);
          if(!existeElementCampana((ArrayList)listaNuevaCampProd.clone(),cod_campana)){
              for(int i=0 ;i<listaCamProd.size();i++){
                  cod_prod = FarmaUtility.getValueFieldArrayList(listaCamProd,i,P_COL_COD_PROD).trim();
                  cod_campana_2 = FarmaUtility.getValueFieldArrayList(listaCamProd,i,P_COL_COD_CAMPANA ).trim();
                  if(cod_campana.trim().equalsIgnoreCase(cod_campana_2.trim()))
                   if(!existeElemento((ArrayList)aux.clone(),cod_prod))
                      aux.add(cod_prod);
              }

              listaNuevaCampProd.add((ArrayList)aux.clone());
          }
      }

      System.out.println(" listaNuevaCampProd " + listaNuevaCampProd);
      cod_camp_cupon = "";
      System.out.println("Campañas Cupones ");
      for(int i=0 ; i<listaNuevaCampProd.size();i++){

          ArrayList arrayLista = new ArrayList();
          arrayLista = (ArrayList)listaNuevaCampProd.get(i);
          cod_camp_cupon = arrayLista.get(0).toString();
          arrayLista.remove(0);

          System.out.println(i+":"+cod_camp_cupon);
          System.out.println(i+" arrayLista "+arrayLista);
          if(arrayLista.size()>0)
           {
             ArrayList arrayProdCupon = (ArrayList)arrayLista.clone();
             double montConsumoCampana = calculoMontoProdEncarte(arrayProdCupon,1);
             System.out.println(i+":"+montConsumoCampana);
             if(montConsumoCampana>0)
                DBVentas.grabaCuponPedido(pNumPed,
                                          cod_camp_cupon,
                                          0,
                                          ConstantsVentas.TIPO_CAMPANA_CUPON,
                                          FarmaUtility.formatNumber(montConsumoCampana,3));
           }

      }
  }

 private boolean existeElementCampana(ArrayList lista, String cod_camp){
     String cod_camp_lista = "";
     if(lista==null)
         return false;
     else
         for(int i=0;i<lista.size();i++){
             cod_camp_lista = FarmaUtility.getValueFieldArrayList(lista,i,0).trim();
             if(cod_camp_lista.equalsIgnoreCase(cod_camp.trim()))
                 return true;
         }
    return false;
 }

 private boolean existeElemento(ArrayList lista, String cod){
     String cod_lista = "";
     for(int i=0;i<lista.size();i++){
         cod_lista = lista.get(i).toString().trim();
         if(cod_lista.equalsIgnoreCase(cod.trim()))
             return true;
     }

     return false;
 }
  */

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
        DBVentas.obtieneCampCuponAplicables(vACampCuponplicables,
                                            ConstantsVentas.TIPO_MULTIMARCA,
                                            "N");
        System.out.println("...Camp Cupones aplicables : " +
                           vACampCuponplicables);
        String cod_camp_cupon = "";
        ArrayList vMensajesCampCupon = new ArrayList();
        for (int e = 0; e < vACampCuponplicables.size(); e++) {
            cod_camp_cupon =
                    FarmaUtility.getValueFieldArrayList(vACampCuponplicables,
                                                        e, 0);

            ArrayList arrayLista = new ArrayList();
            DBVentas.analizaProdCampCupon(arrayLista, pNumPed, cod_camp_cupon);
            System.out.println("RESULTADO " + arrayLista);
            if (arrayLista.size() > 1) {
                String[] listDatosCupon =
                    arrayLista.get(0).toString().substring(1,
                                                           arrayLista.get(0).toString().length() -
                                                           1).split("&");

                VariablesVentas.vCodCampCupon =
                        listDatosCupon[CON_COLUM_COD_CUPON];
                VariablesVentas.vDescCupon =
                        listDatosCupon[CON_COLUM_DESC_CUPON];
                VariablesVentas.vMontoPorCupon =
                        FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_MONT_CUPON]);

                VariablesVentas.vCantidadCupones =
                        FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_CANT_CUPON]);

                System.out.println("VariablesVentas.vCodCampCupon  " +
                                   VariablesVentas.vCodCampCupon);
                System.out.println("VariablesVentas.vDescCupon  " +
                                   VariablesVentas.vDescCupon);
                System.out.println("VariablesVentas.vMontoPorCupon  " +
                                   VariablesVentas.vMontoPorCupon);

                arrayLista.remove(0);
                ArrayList arrayProdCupon = (ArrayList)arrayLista.clone();
                if (arrayProdCupon.size() > 0) {

                    System.out.println("arrayProdCupon " + arrayProdCupon);
                    System.out.println("listDatosCupon " + listDatosCupon);
                    double monto_actual_prod_cupon =
                        calculoMontoProdEncarte(arrayProdCupon, 2);

                    if (monto_actual_prod_cupon >=
                        VariablesVentas.vMontoPorCupon) {
                        System.out.println("...calculando numero de cupones para llevarse");
                        System.out.println("...monto_actual_prod_cupon " +
                                           monto_actual_prod_cupon);
                        System.out.println("...VariablesVentas.vMontoPorCupon " +
                                           VariablesVentas.vMontoPorCupon);
                        int cantCupones =
                            (int)((monto_actual_prod_cupon / VariablesVentas.vMontoPorCupon)*VariablesVentas.vCantidadCupones);
                        System.out.println("Numero Cupones " + cantCupones);
                        String mensaje = "Usted a ganado " + cantCupones;
                        if (cantCupones == 1)
                            mensaje += " cupon (";
                        else
                            mensaje += " cupones (";

                        mensaje += VariablesVentas.vDescCupon + ")";

                        //FarmaUtility.showMessage(this,mensaje,null);
                        //Se grabara la cantidad de cupones entregados al cupon.
                        DBVentas.grabaCuponPedido(pNumPed,
                                                  VariablesVentas.vCodCampCupon,
                                                  cantCupones,
                                                  ConstantsVentas.TIPO_MULTIMARCA,
                                                  "");
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
        for (int i = 0;
             i < VariablesVentas.vArrayList_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Promociones_temporal = new ArrayList();

        for (int i = 0;
             i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size();
             i++)
            VariablesVentas.vArrayList_Prod_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
    }

    /**
     * se lista productos complementarios
     * @author JCORTEZ
     * @since  10.05.2008
     */
    private void agregarComplementarios(String vkF) {

        boolean mostrar = true;
        String ind_ori = "";

        System.out.println("Seleccion de origen...");

        for (int i = 0; i <= tblProductos.getRowCount(); i++) {
            ind_ori = FarmaUtility.getValueFieldJTable(tblProductos, i, 19);
            System.out.println("FarmaVariables.vAceptar : " +
                               FarmaVariables.vAceptar);
            System.out.println("ind_ori : " + ind_ori);
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
        VariablesVentas.vCodProdOrigen_Comple =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, 1);




        //Agregado por FRAMIREZ 11.02.2012 Flag Para Mostrar productos complementarios para un convenio
        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
        {
        	 if (VariablesConvenioBTLMF.vIndVtaComplentaria.equals("S"))
        	  {
		         DlgComplementarios1 dlgcomplementarios = new DlgComplementarios1(myParentFrame, "", true);
		         dlgcomplementarios.setVisible(true);
		      }
        	 else
        	  {
        		 FarmaVariables.vAceptar = false;
		      }
        }
        else
        {
        if (mostrar) {
            DlgComplementarios1 dlgcomplementarios =
                new DlgComplementarios1(myParentFrame, "", true);
            dlgcomplementarios.setVisible(true);
        } else {
            FarmaVariables.vAceptar = false;
        }
        }

        System.out.println("FarmaVariables.vAceptar : " +
                           FarmaVariables.vAceptar);

        if (FarmaVariables.vAceptar) {
            VariablesVentas.vCodFiltro = ""; //JCORTEZ 25/04/08
            VariablesVentas.vEsProdOferta = false; //JCORTEZ 25/04/08
            aceptaOperacion(); //agrega producto al pedido

            //operaResumenPedido(); REEMPLAZADO POR EL DE ABAJO
            neoOperaResumenPedido(); //nuevo metodo jcallo 10.03.2009


        } else {

          //Agregado Por FRAMIREZ
          if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this,null)
        	 && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.length() > 0)
          {
	            if (vkF.equalsIgnoreCase("F1")) {
	               grabarPedidoVenta(ConstantsVentas.TIPO_COMP_BOLETA);
	               //VariablesVentas.venta_producto_virtual = false;
	            }
          }
          else
          {
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
            System.out.println("...Evaluando pedido si llevará Regalo");
            ArrayList vAEncartesAplicables = new ArrayList();
            DBVentas.obtieneEncartesAplicables(vAEncartesAplicables);
            System.out.println("...Encartes aplicables : " +
                               vAEncartesAplicables);
            String cod_encarte = "";
            ArrayList vMensajesRegalo = new ArrayList();
            for (int e = 0; e < vAEncartesAplicables.size(); e++) {
                cod_encarte =
                        FarmaUtility.getValueFieldArrayList(vAEncartesAplicables,
                                                            e, 0);
                System.out.println("...Procesando Encarte : " + cod_encarte);

                ArrayList arrayLista = new ArrayList();
                DBVentas.analizaProdEncarte(arrayLista, cod_encarte.trim());
                if (arrayLista.size() > 1) {
                    String[] listEncarte =
                        arrayLista.get(0).toString().substring(1,
                                                               arrayLista.get(0).toString().length() -
                                                               1).split("&");

                    VariablesVentas.vCodProd_Regalo =
                            listEncarte[CON_COLUM_COD_PROD_REGALO];
                    VariablesVentas.vCantidad_Regalo =
                            (int)FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_CANT_MAX_PROD_REGALO]);
                    VariablesVentas.vMontoMinConsumoEncarte =
                            FarmaUtility.getDecimalNumber(listEncarte[CON_COLUM_MONT_MIN_ENCARTE]);

                    System.out.println("VariablesVentas.vCodProd_Regalo  " +
                                       VariablesVentas.vCodProd_Regalo);
                    System.out.println("VariablesVentas.vCantidad_Regalo  " +
                                       VariablesVentas.vCantidad_Regalo);
                    System.out.println("VariablesVentas.vMontoMinConsumoEncarte  " +
                                       VariablesVentas.vMontoMinConsumoEncarte);

                    arrayLista.remove(0);
                    ArrayList arrayProdEncarte = (ArrayList)arrayLista.clone();
                    if (arrayProdEncarte.size() > 0) {
                        double monto_actual_prod_encarte =
                            calculoMontoProdEncarte(arrayProdEncarte, 2);

                        //Vuelve a verificar el prod. regalo si este da regalo de acuerdo al monto.
                        ArrayList arrayRegMontos = new ArrayList();
                        DBVentas.getDatosRegaloxMonto(arrayRegMontos,cod_encarte.trim(),monto_actual_prod_encarte);

                        //busco que este encarte tenga regalos por montos.
                        if (arrayRegMontos.size()>1)
                        {
                            arrayRegMontos.remove(0);
                            ArrayList arrayDatosRegalo = (ArrayList)arrayRegMontos.clone();

                            VariablesVentas.vCodProd_Regalo =
                                                FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,0);

                            VariablesVentas.vMontoMinConsumoEncarte =
                                                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,1).trim());
                            VariablesVentas.vMontoProxMinConsumoEncarte=
                                                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,2).trim());
                            VariablesVentas.vCodProdProxRegalo =
                                                FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,3).trim();
                            VariablesVentas.vDescProxProd_Regalo =
                                                FarmaUtility.getValueFieldArrayList(arrayDatosRegalo,0,4).trim();

                        } else
                        {
                                VariablesVentas.vMontoProxMinConsumoEncarte=0;
                                VariablesVentas.vDescProxProd_Regalo ="";
                                VariablesVentas.vCodProdProxRegalo="";
                        }
                        /*

                        */
                        //jquispe 05.12.2011 cambiar si la cantidad del prod. regalo es variable segun el monto
                        /*VariablesVentas.vCantidad_Regalo =
                                            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayRegMontos,0,2).trim());
                        */
                        String desc_prod =
                            DBVentas.obtieneDescProducto(VariablesVentas.vCodProd_Regalo);
                        String mensaje = "";

                        double stock_regalo=0,stock_prox_regalo=0;

                        if (VariablesVentas.vCodProd_Regalo.trim().length()>0)
                          stock_regalo = FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProd_Regalo).trim());

                        if (VariablesVentas.vCodProdProxRegalo.trim().length()>0)
                          stock_prox_regalo = FarmaUtility.getDecimalNumber(DBVentas.getStockProdRegalo(VariablesVentas.vCodProdProxRegalo).trim());

                        /*if(VariablesVentas.vMontoProxMinConsumoEncarte>0)
                        {*/
                          if(stock_regalo>0){

                               if(monto_actual_prod_encarte>=VariablesVentas.vMontoMinConsumoEncarte)
                               {
                                   mensaje =
                                           "Usted se llevará de regalo " + VariablesVentas.vCantidad_Regalo +
                                           " " + desc_prod;
                                   System.out.println(mensaje);
                               }
                          }

                        if(stock_regalo==0 )
                        {
                         if(monto_actual_prod_encarte>=VariablesVentas.vMontoMinConsumoEncarte){
                          ArrayList array=new ArrayList();

                          DBVentas.getEncarteAplica(array ,monto_actual_prod_encarte ,cod_encarte.trim());

                            if (array.size() > 0) {
                                double stk_prod = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array,0,4).trim());
                                     if (stk_prod>0)
                                     {
                                         mensaje =
                                                 "Usted se llevará de regalo " + (int)(FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(array,0,2).trim())) +
                                                 " " + FarmaUtility.getValueFieldArrayList(array,0,1).trim();
                                         System.out.println(mensaje);
                                     }
                            }
                            }else
                            {
                               /*if(monto_actual_prod_encarte<VariablesVentas.vMontoMinConsumoEncarte){
                                        mensaje = mensaje+" / "+
                                                "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte -
                                                                                         monto_actual_prod_encarte) +
                                                "   Para llevarse " +
                                                VariablesVentas.vCantidad_Regalo + " " +
                                         desc_prod;

                                        System.out.println(mensaje);
                                    }else{

                                    */
                                    if(stock_prox_regalo>0){
                                     if(monto_actual_prod_encarte<VariablesVentas.vMontoProxMinConsumoEncarte){
                                         mensaje = mensaje+" / "+
                                                 "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoProxMinConsumoEncarte -
                                                                                          monto_actual_prod_encarte) +
                                                 "   Para llevarse " +
                                                 VariablesVentas.vCantidad_Regalo + " " +
                                          VariablesVentas.vDescProxProd_Regalo;
                                         System.out.println(mensaje);
                                    }
                                 }
                               /*}*/
                            }
                       }


                           if(stock_regalo>0){
                                   if(monto_actual_prod_encarte<VariablesVentas.vMontoMinConsumoEncarte){
                                       mensaje = mensaje+" / "+
                                               "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte -
                                                                                        monto_actual_prod_encarte) +
                                               "   Para llevarse " +
                                               VariablesVentas.vCantidad_Regalo + " " +
                                        desc_prod;

                                       System.out.println(mensaje);
                                   }else{


                            if(stock_prox_regalo>0){
                                    if(monto_actual_prod_encarte<VariablesVentas.vMontoProxMinConsumoEncarte){
                                        mensaje = mensaje+" / "+
                                                "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoProxMinConsumoEncarte -
                                                                                         monto_actual_prod_encarte) +
                                                "   Para llevarse " +
                                                VariablesVentas.vCantidad_Regalo + " " +
                                         VariablesVentas.vDescProxProd_Regalo;
                                        System.out.println(mensaje);
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
                            System.out.println(mensaje);
                            //lblPedidoRegalo2.setText(mensaje.trim());
                        } else {
                            mensaje =
                                    "FALTAN S/." + FarmaUtility.formatNumber(VariablesVentas.vMontoMinConsumoEncarte -
                                                                             monto_actual_prod_encarte) +
                                    "   Para llevarse " +
                                    VariablesVentas.vCantidad_Regalo + " " +
                                    desc_prod + " ";
                            System.out.println(mensaje);
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
            System.out.println("Msn " + vMensajesRegalo);
            String msn = "";
            for (int e = 0; e < vMensajesRegalo.size(); e++) {
                msn =
msn + " / " + FarmaUtility.getValueFieldArrayList(vMensajesRegalo, e, 0);
            }


            if (msn.trim().length() > 1) {
                if (msn.length() > 300)
                    //jquispe 16.12.2011 se cambio para que el mensaje se muestre completo
                    msn = msn.substring(2, 800);
                else
                    msn = msn.substring(2);

                txtMensajesPedido.setLineWrap(true);
                txtMensajesPedido.setWrapStyleWord(true);
                txtMensajesPedido.setFont(new Font("SansSerif", Font.BOLD,
                                                   13));
                txtMensajesPedido.setForeground(new Color(7, 133, 7));
                txtMensajesPedido.setText(msn.trim());
                FarmaUtility.moveFocus(txtMensajesPedido);
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }


    }


    private void evaluaCantidadCupon() {
        lblMensajeCupon.setText("");
        try {
            //--Se verifica si puede o no acceder al regalo
            ArrayList vACampCuponplicables = new ArrayList();
            DBVentas.obtieneCampCuponAplicables(vACampCuponplicables,
                                                ConstantsVentas.TIPO_MULTIMARCA,
                                                "N");
            System.out.println("...Camp Cupones aplicables : " +
                               vACampCuponplicables);
            String cod_camp_cupon = "";
            String tipo_campana = "";
            String ind_mensaje = "";
            ArrayList vMensajesCampCupon = new ArrayList();
            String msg = "";
            for (int e = 0; e < vACampCuponplicables.size(); e++) {
                cod_camp_cupon =
                        FarmaUtility.getValueFieldArrayList(vACampCuponplicables,
                                                            e,
                                                            COL_COD_CAMPANA);
                tipo_campana =
                        FarmaUtility.getValueFieldArrayList(vACampCuponplicables,
                                                            e,
                                                            COL_TIPO_CAMPANA);
                ind_mensaje =
                        FarmaUtility.getValueFieldArrayList(vACampCuponplicables,
                                                            e,
                                                            COL_IND_MENSAJE_CAMPANA);


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
            System.out.println("Msn " + vMensajesCampCupon);
            String msn = "";
            for (int e = 0; e < vMensajesCampCupon.size(); e++) {
                msn =
msn + " / " + FarmaUtility.getValueFieldArrayList(vMensajesCampCupon, e, 0);
            }
            if (msn.length() > 3)
                lblMensajeCupon.setText(msn.trim().substring(2));


        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    private String evaluaCampanaCupon(String cod_camp_cupon) {

        return "N";
    }

    private String evaluaMultimarca(String cod_camp_cupon) throws SQLException {

        String mensaje = FarmaConstants.INDICADOR_N;
        ArrayList arrayLista = new ArrayList();
        DBVentas.analizaProdCampCupon(arrayLista, "N", cod_camp_cupon);
        System.out.println("RESULTADO " + arrayLista);
        if (arrayLista.size() > 1) {
            String[] listDatosCupon =
                arrayLista.get(0).toString().substring(1, arrayLista.get(0).toString().length() -
                                                       1).split("&");

            VariablesVentas.vCodCampCupon =
                    listDatosCupon[CON_COLUM_COD_CUPON];
            VariablesVentas.vDescCupon = listDatosCupon[CON_COLUM_DESC_CUPON];
            VariablesVentas.vMontoPorCupon =
                    FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_MONT_CUPON]);

            VariablesVentas.vCantidadCupones=
                    FarmaUtility.getDecimalNumber(listDatosCupon[CON_COLUM_CANT_CUPON]);


            System.out.println("VariablesVentas.vCodCampCupon  " +
                               VariablesVentas.vCodCampCupon);
            System.out.println("VariablesVentas.vDescCupon  " +
                               VariablesVentas.vDescCupon);
            System.out.println("VariablesVentas.vMontoPorCupon  " +
                               VariablesVentas.vMontoPorCupon);

            arrayLista.remove(0);
            ArrayList arrayProdCupon = (ArrayList)arrayLista.clone();
            if (arrayProdCupon.size() > 0) {
                System.out.println("arrayProdCupon " + arrayProdCupon);
                System.out.println("listDatosCupon " + listDatosCupon);
                double monto_actual_prod_cupon =
                    calculoMontoProdEncarte(arrayProdCupon, 2);

                System.out.println("...monto_actual_prod_cupon " +
                                   monto_actual_prod_cupon);
                System.out.println("...VariablesVentas.vMontoPorCupon " +
                                   VariablesVentas.vMontoPorCupon);
                if (monto_actual_prod_cupon >=
                    VariablesVentas.vMontoPorCupon) {
                    System.out.println("...calculando numero de cupones para llevarse");
                    int cantCupones =
                        ((int)(monto_actual_prod_cupon / VariablesVentas.vMontoPorCupon))*((int)VariablesVentas.vCantidadCupones);
                    System.out.println("Numero Cupones " + cantCupones);
                    mensaje = "Ganó " + cantCupones;
                    if (cantCupones == 1)
                        mensaje += " CUPON  (";
                    else
                        mensaje += " CUPONES  (";

                    mensaje += VariablesVentas.vDescCupon + ")";

                } else {
                    double dif =
                        VariablesVentas.vMontoPorCupon - monto_actual_prod_cupon;
                    if (dif > 0) {
                        /* jquispe 30.11.2011 cambio para dar ams de 1 cupon x camp. multimarca
                          mensaje =
                                "Faltan S/. " + FarmaUtility.formatNumber(dif) +
                                " para 1 CUPON (" +
                                VariablesVentas.vDescCupon + ")";*/

                        mensaje =
                                "Faltan S/. " + FarmaUtility.formatNumber(dif) +
                                " para " + (int)(VariablesVentas.vCantidadCupones) + " CUPON (" +
                                VariablesVentas.vDescCupon + ")";


                    }
                }
            }
        }

        arrayLista = new ArrayList();
        return mensaje;
    }


    private void txtDescProdOculto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos,
                                              txtDescProdOculto, 1);
        String vCadIngresada = txtDescProdOculto.getText();
        if (!UtilityVentas.isNumerico(vCadIngresada)&&vCadIngresada.indexOf("%")!=0){
            /*
            System.err.println("incio enter : " + pasoTarjeta);
            if(pasoTarjeta){
                txtDescProdOculto.setText("");
                pasoTarjeta = false;
                return;
            }
            int ini = vCadIngresada.indexOf("%");
            if(!(ini==0&&e.getKeyCode() == KeyEvent.VK_ENTER))
                return;

            String pCadenaOriginal = txtDescProdOculto.getText().trim();
            //dubilluz 21.07.2011
            setFormatoTarjetaCredito(txtDescProdOculto.getText().trim());
            String pCadenaNueva = txtDescProdOculto.getText().trim();
            System.err.println("pasoTarjeta:"+pasoTarjeta);*/
            tblProductos_keyPressed(e);
        }
        else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                System.err.println("incio enter : " + pasoTarjeta);
                if(pasoTarjeta){
                    txtDescProdOculto.setText("");
                    pasoTarjeta = false;
                    return;
                }

                String pCadenaOriginal = txtDescProdOculto.getText().trim();
                //dubilluz 21.07.2011
                setFormatoTarjetaCredito(txtDescProdOculto.getText().trim());
                String pCadenaNueva = txtDescProdOculto.getText().trim();
                /*
                if(!pCadenaOriginal.trim().equalsIgnoreCase(pCadenaNueva.trim())&&pCadenaOriginal.trim().length()>0){
                    pasoTarjeta = true;
                    System.err.println("Es tarjeta...");
                }
                else{
                    System.err.println("no es tarjeta");
                    pasoTarjeta = false;
                }
                */
                System.err.println("pasoTarjeta:"+pasoTarjeta);


                //JCORTEZ 25/04/08
                String codProd = txtDescProdOculto.getText().trim();
                if (UtilityVentas.isNumerico(codProd)) {

                    // inicio
                    //AGREGADO POR DVELIZ 26.09.08
                    //Cambiar en el futuro esto por una consulta a base de datos.
                    String cadena = codProd.trim();
                    String formato = "";
                    if (cadena.trim().length() > 6)
                        formato = cadena.substring(0, 5);
                    if (formato.equals("99999")) {
                        if (UtilityFidelizacion.EsTarjetaFidelizacion(cadena)) {
                            if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                                0) {
                                FarmaUtility.showMessage(this,
                                                         "No puede ingresar más de una tarjeta.",
                                                         txtDescProdOculto);
                                txtDescProdOculto.setText("");

                            } else {
                                //VALIDA EL CLIENTE POR TARJETA 12.01.2011
                                validarClienteTarjeta(cadena);
                                //VariablesFidelizacion.vNumTarjeta = cadena.trim();
                                if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                                    0) {
                                    System.out.println("RRRR");
                                    UtilityFidelizacion.operaCampañasFidelizacion(cadena);
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

                                    AuxiliarFidelizacion.setMensajeDNIFidelizado(lblDNI_Anul,"R",txtDescProdOculto,this);
                                    neoOperaResumenPedido();
                                }
                            }
                            return;
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "La tarjeta no es valida",
                                                     null);
                            txtDescProdOculto.setText("");
                            FarmaUtility.moveFocus(txtDescProdOculto);
                            return;
                        }
                    }
                    //fin  dubilluz

                    if (cadena.trim().length() > 6)
                        formato = cadena.substring(0, 5);
                    if (formato.equals("99999"))
                        return;


                    if (UtilityVentas.esCupon(cadena, this,
                                              txtDescProdOculto)) {
                        //agregarCupon(cadena);//metodo reemplazado por lo nuevo

                        if (VariablesVentas.vEsPedidoConvenio) {
                            FarmaUtility.showMessage(this,
                                                     "No puede agregar cupones a un pedido por convenio.",
                                                     txtDescProdOculto);
                            return;
                        }
                        validarAgregarCupon(cadena);

                        System.err.println("es CUpon :");
                        return;

                    }
                    ///fin 2
                    //Dubilluz saber si ingreso una tarjeta y esta en una campaña automatica
                    //para q aparezca la pantalla de fidelizacion
                    //inicio dubilluz 15.07.2011
                    boolean pIsTarjetaEnCampana = UtilityFidelizacion.isTarjetaPagoInCampAutomatica(cadena.trim());
                    if(pIsTarjetaEnCampana){
                        System.err.println("es una tarjeta de pago q esta en una campana automatica:");
                       validaIngresoTarjetaPagoCampanaAutomatica(cadena.trim());
                       return;
                    }
                    //fin dubilluz 15.07.2011

                    /***Jquispe 22.04.2010 cambio para leer el codigo de barra************/


                    if (VariablesFidelizacion.vDniCliente != "") {

                        VariablesCampana.vFlag = false;

                        if (codProd.length() == 0) {
                            //vEjecutaAccionTeclaListado = false;
                            return;
                        }


                        if (codProd.length() < 6) {
                            FarmaUtility.showMessage(this,
                                                     "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                     txtDescProdOculto);
                            VariablesVentas.vCodProdBusq = "";
                            VariablesVentas.vKeyPress = null;
                            return;
                        }


                        String codigo = "";
                        // revisando codigo de barra
                        char primerkeyChar = codProd.charAt(0);
                        char segundokeyChar;

                        if (codProd.length() > 1)
                            segundokeyChar = codProd.charAt(1);
                        else
                            segundokeyChar = primerkeyChar;

                        char ultimokeyChar =
                            codProd.charAt(codProd.length() - 1);
                        System.err.println("productoBuscar:" + codProd);
                        if (codProd.length() > 6 &&
                            (!Character.isLetter(primerkeyChar) &&
                             (!Character.isLetter(segundokeyChar) &&
                              (!Character.isLetter(ultimokeyChar))))) {
                            VariablesVentas.vCodigoBarra = codProd;
                            System.err.println("consulta cod barra antes");
                            try {
                                codProd =
                                        DBVentas.obtieneCodigoProductoBarra();
                            } catch (SQLException er) {
                                er.printStackTrace();
                            }
                            System.err.println("consulta cod barra despues");
                        }

                        System.err.println("productoBuscar new:" + codProd);


                        if (codProd.length() == 6) {
                            VariablesVentas.vCodProdBusq = codProd;
                            ArrayList myArray = new ArrayList();
                            obtieneInfoProdEnArrayList(myArray, codProd);
                            System.out.println("info prod resumen " + myArray);
                            if (myArray.size() == 1) {
                                VariablesVentas.vKeyPress = e;

                                agregarProducto();
                                VariablesVentas.vCodProdBusq = "";
                                //codProd = "";
                                VariablesVentas.vKeyPress = null;
                            } else {
                                FarmaUtility.showMessage(this,
                                                         "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                         txtDescProdOculto);
                                VariablesVentas.vCodProdBusq = "";
                                //codProd = "";
                                VariablesVentas.vKeyPress = null;
                            }
                        }
                    } else {

                           /**********************************FIN*************************************/
                        if (codProd.length() == 6) {
                            VariablesVentas.vCodProdBusq = codProd;
                            ArrayList myArray = new ArrayList();
                            obtieneInfoProdEnArrayList(myArray, codProd);
                            System.out.println("info prod resumen " + myArray);
                            if (myArray.size() == 1) {
                                VariablesVentas.vKeyPress = e;

                                agregarProducto();
                                VariablesVentas.vCodProdBusq = "";
                                //codProd = "";
                                VariablesVentas.vKeyPress = null;
                            } else {
                                FarmaUtility.showMessage(this,
                                                         "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                         txtDescProdOculto);
                                VariablesVentas.vCodProdBusq = "";
                                //codProd = "";
                                VariablesVentas.vKeyPress = null;
                            }
                        }


                        if (codProd.length() == 0) {
                            //vEjecutaAccionTeclaListado = false;
                            return;
                        }


                        if (codProd.length() < 6) {
                            FarmaUtility.showMessage(this,
                                                     "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                     txtDescProdOculto);
                            VariablesVentas.vCodProdBusq = "";
                            VariablesVentas.vKeyPress = null;
                            return;
                        }

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
        // System.out.println("en el oculto " + e.getKeyChar());
        String cadena = txtDescProdOculto.getText().trim();
        //System.out.println("cadena " +cadena );
        //varNumero = isNumerico(cadena.trim());
        //System.out.println("es numero "+isNumero);
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
      else*/if (UtilityVentas.isNumerico(cadena) &&
                VariablesFidelizacion.vNumTarjeta.trim().length() == 0 &&
                //!busca_producto_cupon(cadena.trim())
                !UtilityVentas.esCupon(cadena,this,txtDescProdOculto)
            ) {
                //System.out.println("presiono enter");
                e.consume();
                String productoBuscar =
                    txtDescProdOculto.getText().trim().toUpperCase();
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

                char ultimokeyChar =
                    productoBuscar.charAt(productoBuscar.length() - 1);

                if (productoBuscar.length() > 6 &&
                    (!Character.isLetter(primerkeyChar) &&
                     (!Character.isLetter(segundokeyChar) &&
                      (!Character.isLetter(ultimokeyChar))))) {
                    String cod_barra = productoBuscar + "";
                    txtDescProdOculto.setText(cod_barra);
                    // System.out.println("cod_barra "+cod_barra);
                    VariablesVentas.vCodBarra = cod_barra;
                    VariablesVentas.vKeyPress = e;

                    agregarProducto();

                    VariablesVentas.vCodBarra = "";
                    VariablesVentas.vKeyPress = null;
                }

                FarmaUtility.moveFocus(txtDescProdOculto);
                txtDescProdOculto.setText("");
            }

        }
    }

    private void txtDescProdOculto_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            e.consume();
            //validaConvenio(e, VariablesConvenio.vPorcCoPago);
            //JMIRANDA 23.06.2010
            validaConvenio_v2(e, VariablesConvenio.vPorcCoPago);
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
            agregarProducto();
            //FarmaVariables.vAceptar = false;
        }

        FarmaUtility.moveFocus(txtDescProdOculto);

    }

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList,
                                            String codProd) {
        try {
            DBVentas.obtieneInfoProducto(pArrayList, codProd);
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtDescProdOculto);
        }
    }

    private void muestraTratamiento() {
        if (tblProductos.getRowCount() == 0)
            return;


        int vFila = tblProductos.getSelectedRow();

        VariablesVentas.vCod_Prod =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, 0);
        VariablesVentas.vDesc_Prod =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, 1);
        VariablesVentas.vNom_Lab =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, 9);

        VariablesVentas.vCant_Ingresada_Temp =
                ((String)(tblProductos.getValueAt(vFila, 4))).trim();
        VariablesVentas.vVal_Frac =
                ((String)(tblProductos.getValueAt(vFila, 10))).trim();

        VariablesVentas.vCant_Vta =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, 4);
        VariablesVentas.vIndModificacion = FarmaConstants.INDICADOR_S;

        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_S;
        VariablesVentas.vCantxDia =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila,
                                                 COL_RES_CANT_XDIA);
        VariablesVentas.vCantxDias =
                FarmaUtility.getValueFieldJTable(tblProductos, vFila,
                                                 COL_RES_CANT_DIAS);


        DlgTratamiento dlgtratemiento =
            new DlgTratamiento(myParentFrame, "", true);
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
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Error al obtener informacion del producto en Arreglo. \n" +
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
            log.debug("ocurrio un error al obtener datos del cupon:" +
                      nroCupon + " error:" + e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al obtener datos del cupon.\n" +
                    e.getMessage() +
                    "\n intentelo Nuevamente\n si persiste el error comuniquese con operador de sistemas.",
                    txtDescProdOculto);
            return;
        }
        log.debug("datosCupon:" + mapCupon);
        //Se verifica si el cupon ya fue agregado tambien verifica si ya existe la campaña
        if (UtilityVentas.existeCuponCampana(nroCupon, this,
                                             txtDescProdOculto))
            return;

        String indMultiuso = mapCupon.get("IND_MULTIUSO").toString().trim();
        boolean obligarIngresarFP =  isFormaPagoUso_x_Cupon(codCampCupon);//saber si la campaña pide forma de pago
        boolean yaIngresoFormaPago = false;






        //jquispe
        //String vIndFidCupon = "N";//obtiene el ind fid -- codCampCupon
        String vIndFidCupon = UtilityFidelizacion.getIndfidelizadoUso(codCampCupon);
        //if(vIndFidCupon.trim().equalsIgnoreCase("S")){
        //inicio dubilluz 09.06.2011
        if(vIndFidCupon.trim().equalsIgnoreCase("S")) {
            if(VariablesFidelizacion.vDniCliente.trim().length()==0){
               funcionF12(codCampCupon);
                yaIngresoFormaPago = true;
            }

            //fin daubilluz 09.06.2011
            if(VariablesFidelizacion.vDniCliente.trim().length()>0/*&&vIndFidCupon.trim().equalsIgnoreCase("S")*/){

                    //Consultara si es necesario ingresar forma de pago x cupon
                    // si es necesario solicitará el mismo.
                    if(obligarIngresarFP){
                        if(!yaIngresoFormaPago)
                             funcionF12(codCampCupon);

                    }
                    //validacion de cupon en base de datos vigente y todo lo demas
                    if (!UtilityVentas.validarCuponEnBD(nroCupon, this, txtDescProdOculto,
                                                        indMultiuso,
                                                        VariablesFidelizacion.vDniCliente)) {
                        return;
                    } else {
                        evaluaFormaPagoFidelizado();
                        //agregando cupon al listado
                        VariablesVentas.vArrayList_Cupones.add(mapCupon);
                        //24.06.2011
                        //Para Reinicializar todas las formas de PAGO.
                        UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                        VariablesVentas.vMensCuponIngre =
                                "Se ha agregado el cupón " + nroCupon +
                                " de la Campaña\n" +
                                mapCupon.get("DESC_CUPON").toString() + ".";

                        //se comento lo que sigue ya que no se tiene claro pa que se usa
                        /*VariablesCampana.vCodCupon = nroCupon;
                               if(VariablesCampana.vListaCupones.size()>0)
                                       VariablesCampana.vFlag = true;
                               VariablesCampana.vDescCamp = mapCupon.get("DESC_CUPON").toString();*/
                        FarmaUtility.showMessage(this, VariablesVentas.vMensCuponIngre,
                                                 txtDescProdOculto);
                    }
                txtDescProdOculto.setText("");
                lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);
            }

        }
        else {
            //PIDE SI necesita ES FORMA DE PAGO
            //Falta inigresar forma de pago en OTRA PANTALLA
            if(obligarIngresarFP){
                if(!yaIngresoFormaPago)
                     funcionF12(codCampCupon);

            }

            if (!UtilityVentas.validarCuponEnBD(nroCupon, this, txtDescProdOculto,
                                                indMultiuso,
                                                "")) {
                return;
            } else {
                evaluaFormaPagoFidelizado();//SETETA el label de TARJETA UNICA
                //agregando cupon al listado
                VariablesVentas.vArrayList_Cupones.add(mapCupon);
                //24.06.2011
                //Para Reinicializar todas las formas de PAGO.
                /*
                    UtilityFidelizacion.operaCampañasFidelizacion("");
                */
                VariablesVentas.vMensCuponIngre =
                        "Se ha agregado el cupón " + nroCupon +
                        " de la Campaña\n" +
                        mapCupon.get("DESC_CUPON").toString() + ".";

                FarmaUtility.showMessage(this, VariablesVentas.vMensCuponIngre,
                                         txtDescProdOculto);
            }
            txtDescProdOculto.setText("");
            lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);
        }
        //calcular totales pedido despues de haber agregado un nuevo cupon
        //calculaTotalesPedido();
        neoOperaResumenPedido();
    }

    public boolean tieneDatoFormaPagoFidelizado(){
        if(
           VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S")||
           (VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S")&&
           VariablesFidelizacion.vCodFPagoTarjeta.trim().length()>0
           )             )
            return true;
        else
            return false;
    }

    /**
     * Se agrega el cupon ingresado.
     * @param cadena
     * @author Edgar Rios Navarro
     * @since 02.07.2008
     * @deprecated
     */
    private void agregarCupon(String cadena) {
        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this,
                                     "No puede agregar cupones a un pedido por convenio.",
                                     txtDescProdOculto);
            return;
        }

        //obtiene indicador de multiuso de la campaña
        String ind_multiuso = "", cod_camp = "";
        ArrayList aux = new ArrayList();
        try {
            //ind_multiuso=DBVentas.obtieneIndMultiuso(cadena);
            DBVentas.obtieneIndMultiuso(aux, cadena);
            if (aux.size() > 0) {
                ind_multiuso = (String)((ArrayList)aux.get(0)).get(1);
                cod_camp = (String)((ArrayList)aux.get(0)).get(0);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al obtener indicador.\n" +
                    sql.getMessage(), txtDescProdOculto);
        }

        //Verifica que haya productos agregados
        //if(!verificaProdsResumen()) return;

        //Verifica que no exista en el arreglo
        if (UtilityVentas.validaCupones(cadena, this, txtDescProdOculto))
            return;

        //Valida que la campana no haya sido agregado al pedido
        if (UtilityVentas.validaCampanaCupon(cadena, this, txtDescProdOculto,
                                             ind_multiuso, cod_camp))
            return;

        //Valida estructura del cupon en BBDD
        //Se agrego el indicador de Multiplo Uso
        if (!UtilityVentas.validaDatoCupon(cadena, this, txtDescProdOculto,
                                           ind_multiuso))
            return;

        //Valida uso del cupon en el pedido
        //if(!validaPedidoCupon(cadena,false)) return;

        /*txtDescProdOculto.setText("");
    lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);

    //validaPedidoCupon();
    calculaTotalesPedido();*/
        /*DlgCupones dlgCupones = new DlgCupones(myParentFrame,"",true);
    dlgCupones.setVisible(true);*/

        /*//Dveliz 22.08.08
     VariablesCampana.vCodCupon = cadena;
     ingresarDatosCampana();
     if(VariablesCampana.vListaCupones.size()>0)
        VariablesCampana.vFlag = true;*/

        //dveliz 25.08.08

        VariablesCampana.vCodCupon = cadena;
        //ingresarDatosCampana();
        if (VariablesCampana.vListaCupones.size() > 0)
            VariablesCampana.vFlag = true;

        /* if(VariablesCampana.vNumIngreso==1)
      if(!UtilityVentas.validaDatoCupon(cadena,this,txtDescProdOculto,ind_multiuso)) return;*/


        txtDescProdOculto.setText("");
        lblCuponIngr.setText(VariablesVentas.vMensCuponIngre);
        calculaTotalesPedido();
        //Fin dveliz
    }


    /**
     * Se elimina el cupon ingresado.
     * @param cadena
     * @author Edgar Rios Navarro
     * @since 04.07.2008
     */
    private void eliminaCupon(String cadena) {
        for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
            ArrayList aux =
                (ArrayList)VariablesVentas.vArrayList_Cupones.get(i);
            if (aux.contains(cadena)) {
                VariablesVentas.vArrayList_Cupones.remove(i);
                break;
            }
        }
    }

    /**
     * @Author    Daniel Fernando Veliz La Rosa
     * @Since     22.08.08
     */
    private void ingresarDatosCampana() {
        try {
            System.out.println(VariablesCampana.vCodCampana);
            DlgListDatosCampana dlgListDatosCampana =
                new DlgListDatosCampana(myParentFrame, "Datos Campaña", true);
            //FarmaUtility.centrarVentana(dlgListDatosCampana);
            dlgListDatosCampana.setVisible(true);
            if (FarmaVariables.vAceptar) {
                FarmaVariables.vAceptar = false;

            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    /**
     * Verifica que exista productos en el resumen.
     * @return
     * @author Edgar Rios Navarro
     * @since 04.07.2008
     */
    private boolean verificaProdsResumen() {
        boolean retorno = true;
        String vTipoOrigen;
        int cantProds = 0;

        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            vTipoOrigen =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, COL_RES_ORIG_PROD);

            if (!vTipoOrigen.equals(ConstantsVentas.IND_ORIGEN_OFER)) {
                cantProds++;
            }
        }

        if (cantProds == 0) {
            retorno = false;
            txtDescProdOculto.setText("");
            FarmaUtility.showMessage(this,
                                     "Debe agregar productos para utilizar el cupon.",
                                     txtDescProdOculto);
        }

        return retorno;
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
                DlgMensajeUsuario dlgMensajeUsuario =
                    new DlgMensajeUsuario(myParentFrame, "", true);
                dlgMensajeUsuario.setMensaje(mensaje);
                dlgMensajeUsuario.setVisible(true);
                DBUsuarios.actCantVeces(FarmaVariables.vNuSecUsu);
            }
        } catch (SQLException e) {
            log.error(null, e);
        }

    }

    /**
     * metodo anterior de validacion de pedido cupon
     * @deprecated
     * **/
    private boolean validaPedidoCupon() {
        log.debug("OPERA CUPONES CON PRODUCTOS PARA OBTENER LOS DESCUENTOS Y AHORRO");
        //validacion de uso de memoria
        Runtime rt = Runtime.getRuntime();
        boolean retorno = false;
        ArrayList cupon = new ArrayList();
        VariablesVentas.vResumenActDctoDetPedVta = new ArrayList();
        String vCodCupon;
        String vCodCamp;
        String vIndTipoCupon;
        double vValorCupon;
        double vMontoMin;
        double vUnidsMin;
        double vCantProdMax;
        double vMontoMaxDscto;
        String vIndValido;

        String codProd;
        String vTipoOrigen;
        String vIndPordCamp;

        int cantProds = 0, cantidad, fraccion;
        double totalUnid = 0, totalNeto = 0;
        double totalMontoProd = 0, vMontoDscto = 0, vCantUnid = 0;
        String vProdCuponCamp;

        //


        //Limpiar las marcas de prod cupon
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            double auxPrecio =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i,
                                                                                  3));
            double auxCantidad =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i,
                                                                                  4));
            String auxPrecAnt =
                FarmaUtility.formatNumber(auxPrecio * auxCantidad);

            double porcIgv =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i,
                                                                                  11));
            double precioTotal = auxPrecio * auxCantidad;
            double totalIgv =
                precioTotal - (precioTotal / (1 + porcIgv / 100));
            String vTotalIgv = FarmaUtility.formatNumber(totalIgv);
            String cPrecioFinal = FarmaUtility.formatNumber(auxPrecio);
            String cPrecioVta = FarmaUtility.formatNumber(auxPrecio);

            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7,
                                                                             auxPrecAnt);
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(COL_RES_CUPON,
                                                                             "");

            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(12,
                                                                             vTotalIgv);

            tblProductos.setValueAt(cPrecioVta, i, 3); //Precio Vta
            tblProductos.setValueAt(" ", i, COL_RES_DSCTO); //Total Precio Vta
            tblProductos.setValueAt(cPrecioFinal, i,
                                    6); //Total Precio Vta
            tblProductos.setValueAt(auxPrecAnt, i, 7); //Total Precio Vta
        }


        ArrayList auxProdCamp = new ArrayList();
        //Recorre todos los cupones que estan ordenados por prioridad
        //-- PENDIENTE
        //-- SOLO UTILIZAR LOS CUPONES VALIDOS
        log.debug("JCALLO : Cupones a Procesar" +
                  VariablesVentas.vArrayList_Cupones);
        log.debug("JCALLO : uso me memoria total:" + rt.totalMemory());
        log.debug("JCALLO : uso me memoria libre:" + rt.freeMemory());
        for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
            cupon = (ArrayList)VariablesVentas.vArrayList_Cupones.get(j);
            log.debug("cupon:" + cupon);
            vCodCupon = cupon.get(0).toString();
            vCodCamp = cupon.get(1).toString();
            vIndTipoCupon = cupon.get(2).toString();
            vValorCupon =
                    FarmaUtility.getDecimalNumber(cupon.get(3).toString());
            vMontoMin = FarmaUtility.getDecimalNumber(cupon.get(4).toString());
            vUnidsMin = FarmaUtility.getDecimalNumber(cupon.get(5).toString());
            vCantProdMax =
                    FarmaUtility.getDecimalNumber(cupon.get(6).toString());
            vMontoMaxDscto =
                    FarmaUtility.getDecimalNumber(cupon.get(7).toString());
            vIndValido = cupon.get(8).toString();

            log.debug("vMontoMin:" + vMontoMin);
            log.debug("vUnidsMin:" + vUnidsMin);
            log.debug("JCALLO : uso me memoria total:" + rt.totalMemory());
            log.debug("JCALLO : uso me memoria libre:" + rt.freeMemory());

            ArrayList vAuxPordPedCamp = new ArrayList();
            cantProds = 0;
            cantidad = 0;
            fraccion = 0;
            vCantUnid = 0;
            totalUnid = 0;
            totalNeto = 0;
            vMontoDscto = 0;


            //JCORTEZ
            double vTotalValorProd = 0;
            double totalNeto1 = 0;

            try {
                //Recorre todos los producto del pedido
                for (int i = 0;
                     i < VariablesVentas.vArrayList_ResumenPedido.size();
                     i++) {
                    log.debug("JCALLO : uso me memoria totalResumenPedido:" +
                              "{" + i + "}" + rt.totalMemory());
                    log.debug("JCALLO : uso me memoria libreResumenPedido:" +
                              "{" + i + "}" + rt.freeMemory());

                    codProd =
                            FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                i, 0);
                    totalMontoProd =
                            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                              i,
                                                                                              7));
                    vTipoOrigen =
                            FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                i,
                                                                COL_RES_ORIG_PROD);
                    vProdCuponCamp =
                            FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                i,
                                                                COL_RES_CUPON);

                    //Si el producto esta asociado a una campana
                    if (!vProdCuponCamp.equals("")) {
                        //Si el cupon es por Monto y el prod por camp, pertenece a la misma campana.
                        if (vIndTipoCupon.equalsIgnoreCase("M") &&
                            vProdCuponCamp.equalsIgnoreCase(vCodCamp)) {
                            vProdCuponCamp = "";
                        }
                    }


                    //Que no sean ofertas, ni este afectado por otro cupon
                    //if(!vTipoOrigen.equals(ConstantsVentas.IND_ORIGEN_OFER) && vProdCuponCamp.equals(""))
                    //Segun gerencia debe de aplicar tambien a ofertas
                    // Dubilluz 24.11.2008
                    vIndPordCamp =
                            DBVentas.verificaProdCamp(vCodCamp, codProd);
                    log.debug("JCALLO : vIndPordCamp:" + vIndPordCamp);
                    if (UtilityVentas.isAplicoPrecioCampanaCupon(codProd.trim(),
                                                                 vIndPordCamp) &&
                        vProdCuponCamp.equals("")) {
                        //vIndPordCamp = DBVentas.verificaProdCamp(vCodCamp,codProd);
                        if (vIndPordCamp.equals(FarmaConstants.INDICADOR_S)) {
                            cantProds++; //cantidad de productos
                            cantidad =
                                    Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(4));
                            fraccion =
                                    Integer.parseInt((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(COL_RES_VAL_FRAC));
                            vCantUnid = (cantidad * 1.00) / fraccion;
                            totalUnid += vCantUnid;
                            totalNeto +=
                                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(7));
                            //System.out.println("cantidad " + cantidad + " / fraccion " + fraccion);
                            log.debug("JCALLO : vCantUnid " + vCantUnid);
                            log.debug("JCALLO : vCantProdMax " + vCantProdMax);

                            if (vIndTipoCupon.equalsIgnoreCase("P")) {
                                if (vCantUnid > vCantProdMax) {
                                    vMontoDscto =
                                            (((totalMontoProd * vCantProdMax) /
                                              vCantUnid) * vValorCupon) / 100;
                                } else {
                                    vMontoDscto =
                                            (totalMontoProd * vValorCupon) /
                                            100;
                                }

                                vMontoMaxDscto -= vMontoDscto;

                                if (vMontoMaxDscto < 0) {
                                    vMontoDscto = vMontoDscto + vMontoMaxDscto;
                                    if (vMontoDscto < 0) {
                                        vMontoDscto = 0;
                                    }
                                }

                                /**MONTO DESCUENTO DESPUES DE OPERAR***/
                                //double porcIgv = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,i,11));

                                /** precio con descuento**/
                                double precioConDcto =
                                    (totalMontoProd - vMontoDscto) / cantidad;
                                /** precio de venta**/
                                double precioVenta =
                                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(3));

                                /*double pConDctoSinIgv = precioConDcto/(1+porcIgv/100);

                String sConDctoSinIgv = Double.toString(pConDctoSinIgv);*/
                                double precioFinal = precioConDcto;
                                String sPrecioFinal =
                                    Double.toString(precioFinal); ///solo para inicializar la variable

                                try {
                                    sPrecioFinal =
                                            DBVentas.getPrecioFinalCampania(codProd,
                                                                            vCodCamp,
                                                                            precioConDcto,
                                                                            precioVenta,
                                                                            fraccion).trim();
                                } catch (Exception e) {
                                    log.debug("*********ERRRORR al obtener precioFinal: " +
                                              e.getMessage());
                                    sPrecioFinal =
                                            Double.toString(precioFinal); //si por x motivos no se obtiene el precio final se asignara el precio comun
                                }
                                log.debug("JCALLO==> CUPONES PRECIO :precioConDcto=" +
                                          precioConDcto + " sPrecioFinal=" +
                                          sPrecioFinal);
                                //verificar si el precio final cambio
                                precioFinal = Double.parseDouble(sPrecioFinal);
                                if (precioFinal > precioConDcto) {
                                    vMontoDscto =
                                            totalMontoProd - precioFinal *
                                            cantidad;
                                }
                            }

                            ArrayList aux = new ArrayList();
                            aux.add(codProd);
                            aux.add("" + totalMontoProd);
                            aux.add("" + vValorCupon);
                            aux.add(FarmaUtility.formatNumber(vMontoDscto));
                            aux.add(FarmaUtility.formatNumber(totalMontoProd -
                                                              vMontoDscto));
                            vAuxPordPedCamp.add(aux);
                            log.debug("despues de ver si aplica por debajo del costo promedio");
                            log.debug("descuento por producto aplicado" +
                                      vAuxPordPedCamp);
                        }
                    }

                }
                log.debug("VER SI EL VALIDO EL USO DEL CUPON");
                log.debug("=================================");
                log.debug("cantProds:" + cantProds + " > 0");
                log.debug("totalNeto:" + totalNeto + " > vMontoMin:" +
                          vMontoMin);
                log.debug("totalUnid:" + totalUnid + " >= vUnidsMin" +
                          vUnidsMin);
                if ((cantProds > 0) && (totalNeto > vMontoMin) &&
                    (totalUnid >= vUnidsMin)) {
                    vIndValido = FarmaConstants.INDICADOR_S;
                } else {
                    vIndValido = FarmaConstants.INDICADOR_N;
                }
                log.debug("APLICAR EL DESCUENTO:" + vIndValido);
                cupon.set(8, vIndValido);

                if (vIndValido.equals(FarmaConstants.INDICADOR_S)) {
                    log.debug("RECORRIENDO LOS PRODUCTOS POR CUPON :" +
                              vAuxPordPedCamp);
                    for (int k = 0; k < vAuxPordPedCamp.size(); k++) {

                        ArrayList prodCupon = new ArrayList();
                        prodCupon = (ArrayList)vAuxPordPedCamp.get(k);
                        log.debug("XXXXprodCupon(" + k + "):" + prodCupon);
                        String auxCodProd = prodCupon.get(0).toString();
                        log.debug("XXXX1111");
                        double montoDescuento =
                            FarmaUtility.getDecimalNumber(prodCupon.get(3).toString());
                        log.debug("XXXX 22222");
                        String totalProd = prodCupon.get(4).toString();
                        log.debug("XXX333montoDescuento : " + montoDescuento);
                        log.debug("****vIndTipoCupon : " + vIndTipoCupon);
                        if (montoDescuento > 0 ||
                            vIndTipoCupon.equalsIgnoreCase("M")) {
                            log.debug("****VariablesVentas.vArrayList_ResumenPedido:" +
                                      VariablesVentas.vArrayList_ResumenPedido);
                            for (int i = 0;
                                 i < VariablesVentas.vArrayList_ResumenPedido.size();
                                 i++) {
                                log.debug("%%% VariablesVentas.vArrayList_ResumenPedido %%%:" +
                                          VariablesVentas.vArrayList_ResumenPedido);
                                codProd =
                                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                            i,
                                                                            0);
                                log.debug("%%% codProdo %%%:" + codProd);
                                if (auxCodProd.equals(codProd)) {
                                    log.debug("%%% auxCodProd %%%:" +
                                              auxCodProd + " codProd:" +
                                              codProd);
                                    log.debug("%%% vIndTipoCupon %%%:" +
                                              vIndTipoCupon);
                                    if (vIndTipoCupon.equalsIgnoreCase("P")) {
                                        log.debug("&&&& 1 &&&");
                                        double porcIgv =
                                            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                                              i,
                                                                                                              11));
                                        log.debug("&&&& 2 &&&");
                                        double precVta =
                                            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                                              i,
                                                                                                              3));
                                        log.debug("&&&& 3 &&&");
                                        double precioTotal =
                                            FarmaUtility.getDecimalNumber(totalProd);
                                        log.debug("&&&& 4 &&&");
                                        double precioFinal =
                                            precioTotal / cantidad; //
                                        log.debug("&&&& 5 &&&");
                                        double totalIgv =
                                            precioTotal - (precioTotal /
                                                           (1 + porcIgv /
                                                            100));
                                        log.debug("&&&& 6 &&&");
                                        String vTotalIgv =
                                            FarmaUtility.formatNumber(totalIgv);
                                        log.debug("&&&& 7 &&&");
                                        String cValorCupon =
                                            FarmaUtility.formatNumber(vValorCupon);
                                        log.debug("&&&& 8 &&&");
                                        String cPrecioFinal =
                                            FarmaUtility.formatNumber(precioFinal);
                                        log.debug("&&&& 9 &&&");
                                        String cPrecioVta =
                                            FarmaUtility.formatNumber(precVta);
                                        log.debug("&&&& 10 &&&");
                                        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7,
                                                                                                         totalProd);
                                        log.debug("&&&& 11 &&&");
                                        ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(12,
                                                                                                         vTotalIgv);
                                        log.debug("&&&& 12 &&&");
                                        tblProductos.setValueAt(cPrecioVta, i,
                                                                3); //Precio Vta
                                        log.debug("&&&& 13 &&&");
                                        tblProductos.setValueAt(cValorCupon, i,
                                                                COL_RES_DSCTO); //Descuento
                                        log.debug("&&&& 14 &&&");
                                        tblProductos.setValueAt(cPrecioFinal,
                                                                i,
                                                                6); //Total Precio Final
                                        log.debug("&&&& 15 &&&");
                                        tblProductos.setValueAt(totalProd, i,
                                                                7); //Total Precio Vta
                                        log.debug("&&&& 16 &&&");

                                        //Modificado por DVELIZ 09.10.08
                                        double ahorro = montoDescuento;
                                        log.debug("&&&& 17 &&&");
                                        String porc_dcto_1 = cValorCupon;
                                        log.debug("&&&& 18 &&&");
                                        double dctocalc =
                                            ((precVta - precioFinal) /
                                             precVta) * 100;
                                        log.debug("&&&& 19 &&&");

                                        log.debug("log de las variables ");
                                        log.debug("=================== ");
                                        log.debug("auxCodProd : " +
                                                  auxCodProd);
                                        log.debug("vCodCamp : " + vCodCamp);
                                        log.debug("cValorCupon: " +
                                                  cValorCupon);
                                        log.debug("String.valueOf(ahorro) : " +
                                                  String.valueOf(ahorro));
                                        log.debug("String.valueOf(dctocalc) " +
                                                  String.valueOf(dctocalc));

                                        log.debug("666.AGREGANDO EL DESCUENTO");
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        /*
                    UtilityVentas.obtieneDctosActualizaPedidoDetalle(auxCodProd,
                                                                     vCodCamp,
                                                                     cValorCupon,
                                                                     String.valueOf(ahorro),
                                                                     String.valueOf(dctocalc));
                    */

                                        log.debug("diego 11 ");
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        ArrayList vActDctoDetPedVta =
                                            new ArrayList();
                                        log.debug("diego 22 vActDctoDetPedVta" +
                                                  vActDctoDetPedVta);
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        vActDctoDetPedVta.add(auxCodProd);
                                        log.debug("diego 33 vActDctoDetPedVta" +
                                                  vActDctoDetPedVta);
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        vActDctoDetPedVta.add(vCodCamp);
                                        log.debug("diego 44 vActDctoDetPedVta" +
                                                  vActDctoDetPedVta);
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        vActDctoDetPedVta.add(cValorCupon);
                                        log.debug("diego 55 vActDctoDetPedVta" +
                                                  vActDctoDetPedVta);
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        vActDctoDetPedVta.add(String.valueOf(ahorro));
                                        log.debug("diego 66 vActDctoDetPedVta" +
                                                  vActDctoDetPedVta);
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        vActDctoDetPedVta.add(String.valueOf(dctocalc));
                                        log.debug("obtieneDctosActualizaPedidoDetalle " +
                                                  vActDctoDetPedVta);
                                        log.debug("VariablesVentas.vResumenActDctoDetPedVta " +
                                                  VariablesVentas.vResumenActDctoDetPedVta);
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        log.debug("666 : 24.02.2009");
                                        try {
                                            VariablesVentas.vResumenActDctoDetPedVta.add(vActDctoDetPedVta);
                                            log.debug("777 : 1da");
                                            log.debug("777 : uso me memoria total :" +
                                                      "{" + i + "}" +
                                                      rt.totalMemory());
                                            log.debug("777 : uso me memoria libre :" +
                                                      "{" + i + "}" +
                                                      rt.freeMemory());
                                            log.debug("VariablesVentas.vResumenActDctoDetPedVta " +
                                                      VariablesVentas.vResumenActDctoDetPedVta);
                                            log.debug("777 : uso me memoria total :" +
                                                      "{" + i + "}" +
                                                      rt.totalMemory());
                                            log.debug("777 : uso me memoria libre :" +
                                                      "{" + i + "}" +
                                                      rt.freeMemory());
                                            log.debug("777 : 24.02.2009");
                                            log.debug("DESPUE DE XXXXXXGREGANDO EL DESCUENTO");
                                        } catch (Exception e) {
                                            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                                                          FarmaVariables.vCodLocal,
                                                    //"jcallo",
                                                    VariablesPtoVenta.vDestEmailErrorCobro,
                                                    "ERROR VALOR NULO , 6 LOCALES CON EL CAMBIO ",
                                                    "ERROR VALOR NULO , 6 LOCALES CON EL CAMBIO ",
                                                    "Error de valor nulo, en calculo de descuento : vCodCamp=" +
                                                    vCodCamp + ", ipPC=" +
                                                    FarmaVariables.vIpPc +
                                                    "MEMORIA LIBRE:" +
                                                    rt.freeMemory() +
                                                    " MEMORIA TOTAL:" +
                                                    rt.totalMemory(),
                                                    //"dubilluz"
                                                    "");
                                            log.error("ERRORDUBILLUZ aplicar descuento1  " +
                                                      e.getStackTrace());
                                            log.error("ERRORDUBILLUZ aplicar descuento2  " +
                                                      e.getMessage());
                                        }
                                        log.debug("666 : 2da");
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        log.debug("VariablesVentas.vResumenActDctoDetPedVta " +
                                                  VariablesVentas.vResumenActDctoDetPedVta);
                                        log.debug("666 : uso me memoria total :" +
                                                  "{" + i + "}" +
                                                  rt.totalMemory());
                                        log.debug("666 : uso me memoria libre :" +
                                                  "{" + i + "}" +
                                                  rt.freeMemory());
                                        log.debug("666 : 24.02.2009");
                                        log.debug("DESPUE DE XXXXXXGREGANDO EL DESCUENTO");
                                    }

                                    log.debug("ANTES DE AGREGAR EL CODIGO DEL CUPON : VariablesVentas.vArrayList_ResumenPedido" +
                                              VariablesVentas.vArrayList_ResumenPedido);
                                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(COL_RES_CUPON,
                                                                                                     vCodCamp);
                                    log.debug("ANTES DE AGREGAR EL CODIGO DEL CUPON : VariablesVentas.vArrayList_ResumenPedido" +
                                              VariablesVentas.vArrayList_ResumenPedido);

                                    break;

                                }
                            }
                        }
                    }
                }

            } catch (SQLException e) {
                retorno = false;
                log.error("ERRORSQL al aplicar descuentos1 " + e);
                log.error("ERRORSQL al aplicar descuentos2 " + e.getMessage());
            } catch (Exception e) {
                retorno = false;
                FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                              FarmaVariables.vCodLocal,
                        //"jcallo",
                        VariablesPtoVenta.vDestEmailErrorCobro,
                        "Error de valor nulo ptoventa con los cambios actuale y el log al calcular los descuentos, tema del stringtokenizer ",
                        "Error de valor nulo, en calculo de descuento",
                        "Error de valor nulo, en calculo de descuento : vCodCamp=" +
                        vCodCamp + ", ipPC=" + FarmaVariables.vIpPc,
                        //"dubilluz"
                        "");
                log.debug("666 : 24.02.2009");
                log.error("ERRORJCALLO aplicar descuento1  " +
                          e.getStackTrace());
                log.error("ERRORJCALLO aplicar descuento2  " + e.getMessage());
            }
        }

        //if(VariablesVentas.vArrayList_Cupones.size()>0)
        //montoAhorro();

        tblProductos.repaint();
        //calculaTotalesPedido();


        return retorno;
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
                vValorCupon =
                        FarmaUtility.getDecimalNumber(cupon.get(3).toString());
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
                if (FarmaUtility.rptaConfirmDialog(this,
                                                   "El monto del pedido es menor que" +
                                                   " la suma de los cupones.\n" +
                        "¿Desea generar el pedido y " +
                        "perder la diferencia?")) {
                    for (int j = 0;
                         j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                        cupon =
                                (ArrayList)VariablesVentas.vArrayList_Cupones.get(j);
                        vCodCupon = cupon.get(0).toString();
                        vCodCamp = cupon.get(1).toString();
                        vIndTipoCupon = cupon.get(2).toString();
                        if (vIndTipoCupon.equalsIgnoreCase(ConstantsVentas.TIPO_MONTO)) {
                            vValorCupon =
                                    FarmaUtility.getDecimalNumber(cupon.get(3).toString());
                            vIndValido = cupon.get(8).toString();
                            if (vIndValido.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                                if (indNoUsaCupones)
                                    cupon.set(8, FarmaConstants.INDICADOR_N);
                                else {
                                    vNetoPedido -= vValorCupon;
                                    if (vNetoPedido <= 0)
                                        indNoUsaCupones = true;
                                    System.out.println("vNetoPedido " +
                                                       vNetoPedido);
                                }

                            }
                        }

                    }
                    System.out.println("VariablesVentas.vArrayList_Cupones " +
                                       VariablesVentas.vArrayList_Cupones);
                    return true;
                } else
                    return false;
            } else
                return true;
        } else
            return true;


    }

    /**
     * JCORTEZ
     * metodo ya no se usa. ya que para calcular el ahorro insertaba en tablas temporales
     * los productos con las campanias
     * @deprecated
     */
    private double montoAhorro() {
        String vCodCupon;
        String vCodCamp;
        String vIndTipoCupon;
        ArrayList cupon = new ArrayList();
        double vValorCupon;
        double totalMontoProd;
        String vIndPordCamp = "", codProd = "", vTipoOrigen = "", vIndValido;
        double totalAhorro1 = 0;

        log.debug("OBTENIENDO AHORRO con lo cupones");
        try {
            log.debug("VariablesVentas.vArrayList_Cupones: " +
                      VariablesVentas.vArrayList_Cupones);
            for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size();
                 j++) {
                cupon = (ArrayList)VariablesVentas.vArrayList_Cupones.get(j);
                log.debug("cupon " + cupon);
                vCodCupon = cupon.get(0).toString();
                vCodCamp = cupon.get(1).toString();
                vIndTipoCupon = cupon.get(2).toString();
                log.debug("vCodCupon " + vCodCupon);
                log.debug("vCodCamp " + vCodCamp);
                log.debug("vIndTipoCupon " + vIndTipoCupon);
                if (vIndTipoCupon.equalsIgnoreCase(ConstantsVentas.TIPO_MONTO)) {
                    vValorCupon =
                            FarmaUtility.getDecimalNumber(cupon.get(3).toString());
                    vIndValido = cupon.get(8).toString();
                    log.debug("vValorCupon " + vValorCupon);
                    log.debug("vIndValido " + vIndValido);
                    if (vIndValido.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        log.debug("GRABANDO CUPON");
                        DBVentas.insertCampCupon(vCodCamp, vCodCupon);
                        for (int i = 0;
                             i < VariablesVentas.vArrayList_ResumenPedido.size();
                             i++) {
                            codProd =
                                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                        i, 0);
                            totalMontoProd =
                                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                                      i,
                                                                                                      7));
                            vTipoOrigen =
                                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                        i,
                                                                        COL_RES_ORIG_PROD);
                            vIndPordCamp =
                                    DBVentas.verificaProdCamp(vCodCamp, codProd);
                            if (vIndPordCamp.equals(FarmaConstants.INDICADOR_S)) {
                                log.debug("GRABANDO PRODUCTO CUPON ... codProd:" +
                                          codProd + ", totalMontoProd:" +
                                          totalMontoProd);
                                DBVentas.insertProdCamp(codProd,
                                                        "" + totalMontoProd);
                            }
                        }
                    }

                }
            }

            String totalAhorro = "", totalPrecio = "";
            totalPrecio = lblTotalS.getText().trim();

            totalAhorro = DBVentas.obtieneTotalAhorro(totalPrecio).trim();
            totalAhorro1 = FarmaUtility.getDecimalNumber((totalAhorro.trim()));
            log.debug("totalAhorro: " + totalAhorro);
            log.debug("totalAhorro1: " + totalAhorro1);

            FarmaUtility.aceptarTransaccion();
            if (totalAhorro1 > 0) {
                /*System.out.println("tot");
          lblDscto.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber((totalAhorro))));
          lblDsctoT.setVisible(true);
          lblDscto.setVisible(true);*/
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalAhorro1;
    }


    private void validarClienteTarjeta(String cadena) {
        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this,
                                     "No puede agregar una tarjeta a un " +
                                     "pedido por convenio.",
                                     txtDescProdOculto);
            txtDescProdOculto.setText("");
            return;
        }
        UtilityFidelizacion.validaLecturaTarjeta(cadena, myParentFrame);
        if (VariablesFidelizacion.vDataCliente.size() > 0) {
            ArrayList array =
                (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            UtilityFidelizacion.setVariablesDatos(array);
            //fin jcallo 02.10.2008
            if (VariablesFidelizacion.vIndExisteCliente) {
                FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " +
                        VariablesFidelizacion.vApePatCliente + " " +
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("0")) {
                FarmaUtility.showMessage(this,
                                         "Se agrego el DNI :" + VariablesFidelizacion.vDniCliente,
                                         null);
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("2")) {
                /*FarmaUtility.showMessage(this,
                                         "Cliente encontrado con DNI " + VariablesFidelizacion.vDniCliente,
                                         null);*/
                FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " +
                        VariablesFidelizacion.vApePatCliente + " " +
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("1")) {
                FarmaUtility.showMessage(this,
                                         "Se actualizaron los datos del DNI :" +
                                         VariablesFidelizacion.vDniCliente,
                                         null);

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
            if(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length()>0){
               UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),VariablesFidelizacion.vDniCliente);
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
        System.out.println("vv:" + FarmaVariables.vAceptar);
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
     * @author  dveliz
     * @since   09.10.08
     * @param array
     * @deprecated
     */
    private void guardaDctosDetPedVta(ArrayList array) {
        try {
            DBVentas.guardaDctosDetPedVta(String.valueOf(array.get(0)),
                                          String.valueOf(array.get(1)),
                                          String.valueOf(array.get(2)),
                                          String.valueOf(array.get(3)),
                                          String.valueOf(array.get(4)),
                                          String.valueOf(array.get(5))); //JMIRANDA 30.10.09
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                                          mProdDcto.get("VALOR_CUPON").toString(),
                                          mProdDcto.get("AHORRO").toString(),
                                          mProdDcto.get("DCTO_AHORRO").toString(),
                                          mProdDcto.get("SEC_PED_VTA_DET").toString());
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            //FarmaUtility.aceptarTransaccion();
            e.printStackTrace();
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
            System.out.println("No es fidelizado...");
            return;
        }
        System.err.println("inicio operaAcumulacionCampañas");
        //--1.Se procesa si acumula unidades
        operaAcumulacionCampañas(pNumPed, pDniCli);
        System.err.println("FIN operaAcumulacionCampañas");
        // Se inserta los pedidos de campañas acumuladas en el local a Matriz
        //--2.Se valida si hay linea
        String pIndLinea = FarmaConstants.INDICADOR_N;
        /*
                             * FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                           FarmaConstants.INDICADOR_N);
                             */
        //--3.No hay linea todo el proceso concluye y solo acumula
        System.err.println("pIndLinea:" + pIndLinea);

        if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            FarmaConnectionRemoto.closeConnection();
            System.err.println("PASO 1");
        }
        /*
        else{
        System.err.println("Evalua Campaña Acumulada");
          // Se inserta los pedidos de campañas acumuladas en el local a Matriz
          enviaUnidadesAcumuladasLocalMatriz(pNumPed,pDniCli,pIndLinea);

          // Se opera el intento de canje y se acumula en el local
          operaRegaloCampañaAcumulada(pNumPed,pDniCli);

        }
        */
        System.err.println("INICIO operaRegaloCampañaAcumulada");
        // Se opera el intento de canje y se acumula en el local
        operaRegaloCampañaAcumulada(pNumPed, pDniCli);
        System.err.println("FIN operaRegaloCampañaAcumulada");
        //Actualiza los datos en la cabecera si acumulo o gano algun canje para poder
        //indentificar si el pedido es de fidelizado y campaña acumulada
        System.out.println("Actualiza datos");
        actualizaDatoPedidoCabecera(pNumPed, pDniCli);

    }

    private void actualizaDatoPedidoCabecera(String pNumPed, String pDniCli) {
        try {
            DBVentas.actualizaPedidoXCampanaAcumulada(pNumPed, pDniCli);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }


    /**
     * Envia unidades acumulaciones local matriz
     * @author Dubilluz
     * @param  pNumPedido
     * @param  pDniCli
     * @param  pIndLinea
     */
    private void enviaUnidadesAcumuladasLocalMatriz(String pNumPedido,
                                                    String pDniCli,
                                                    String pIndLinea) {
        ArrayList pListaAcumulados = new ArrayList();
        int COL_DNI = 0, COL_CIA = 1, COL_COD_CAMP = 2, COL_LOCAL =
            3, COL_NUM_PED = 4, COL_FECH_PED = 5, COL_SEC_PED_VTA =
            6, COL_COD_PROD = 7, COL_CANT_PED = 8, COL_VAL_FRAC_PED =
            9, COL_ESTADO = 10, COL_VAL_FRAC_MIN = 11, COL_USU_CREA =
            12, COL_CANT_RESTANTE = 13;

        boolean indInsertMatriz = false;

        String pDni, pCodCia, pCodCamp, pCodLocal, pNumPed, pFechPed, pSecDet, pCodProd, pCantPed, pValFrac, pEstado, pValFracMin, pUsuCrea, pCantRestante;

        try {
            DBVentas.getListaUnidadesAcumuladas(pListaAcumulados, pNumPedido,
                                                pDniCli);

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
                            System.out.println("..envia a matriz..");


                        }

              a matriz
                                  // Si envio DBVentas.actualizaProcesoMatrizHistorico(pNumPedido,pDniCli,FarmaConstants.INDICADOR_S);
                    }
                    else{
                        System.out.println("Acumula unidades y no envia a Matriz");
                        // No envia a Matriz
                        DBVentas.actualizaProcesoMatrizHistorico(pNumPedido,pDniCli,FarmaConstants.INDICADOR_N);
                    }
                 */
                System.out.println("Acumula unidades y no envia a Matriz");
                DBVentas.actualizaProcesoMatrizHistorico(pNumPedido, pDniCli,
                                                         FarmaConstants.INDICADOR_N);
            } else
                System.out.println("No acumulo ninguna unidad...");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (indInsertMatriz) // -- indica de linea
            {
                if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                          FarmaConstants.INDICADOR_S);
                }
            }
        }

    }

    private void operaRegaloCampañaAcumulada(String pNumPed, String pDniCli) {
        ArrayList listaPedOrigen = new ArrayList();
        int COL_COD_CAMP = 0, COL_LOCAL_ORIGEN = 1, COL_NUM_PED_ORIGEN =
            2, COL_SEC_PED_ORIGEN = 3, COL_COD_PED_ORIGEN = 4, COL_CANT_USO =
            5, COL_VAL_FRAC_MIN = 6;

        String pCodCamp, pCodLocalOrigen, pNumPedOrigen, pSecDetOrigen, pCodProdOrigen, pCantUsoOrigen, pValFracMinOrigen;


        try {
            DBVentas.operaIntentoRegaloCampañaAcumulada(listaPedOrigen,
                                                        pNumPed, pDniCli);

            ArrayList listaCAEfectivas = new ArrayList();
            boolean vIndAgregaElmento = true;

            //1. Opera el listado auxiliares y se obtienen las campañas que regalaran
            for (int j = 0; j < listaPedOrigen.size(); j++) {
                vIndAgregaElmento = true;
                pCodCamp =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, j,
                                                            COL_COD_CAMP).trim();
                for (int k = 0; k < listaCAEfectivas.size(); k++) {
                    if (pCodCamp.trim().equalsIgnoreCase(listaCAEfectivas.get(k).toString().trim())) {
                        vIndAgregaElmento = false;
                        break;
                    }
                }
                if (vIndAgregaElmento)
                    listaCAEfectivas.add(pCodCamp);
            }
            System.err.println("Lista CAEfectivas " + listaCAEfectivas);
            //2. Agrega Productos regalo de cada campañas
            for (int i = 0; i < listaCAEfectivas.size(); i++) {
                //obtiene la campaña para añadir los regalos
                pCodCamp = listaCAEfectivas.get(i).toString().trim();
                //DBVentas.añadeRegaloCampaña(pNumPed,pDniCli,pCodCamp); antes
                DBVentas.añadeRegaloCampaña_02(pNumPed, pDniCli,
                                               pCodCamp); //ASOSA, 07.07.2010
            }


            //se obtiene las campañas de regalo efectiva realizadas
            ArrayList listaCanjesRealizados = new ArrayList();
            DBCaja.getPedidosCanj(pDniCli, pNumPed, listaCanjesRealizados);
            ArrayList listaCanjesPosibles =
                (ArrayList)listaCAEfectivas.clone();
            String codCampana = "";
            for (int i = 0; i < listaCanjesRealizados.size(); i++) {

                codCampana =
                        FarmaUtility.getValueFieldArrayList(listaCanjesRealizados,
                                                            i, 0);

                for (int j = 0; j < listaCanjesPosibles.size(); j++) {
                    //obtiene la campaña para añadir los regalos
                    pCodCamp = listaCanjesPosibles.get(j).toString().trim();
                    if (pCodCamp.trim().equalsIgnoreCase(codCampana.trim())) {
                        listaCanjesPosibles.remove(j);
                        break;
                    }
                }

            }
            System.out.println("Camapañas no realizadas:" +
                               listaCanjesPosibles);
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
                pCodCamp =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, i,
                                                            COL_COD_CAMP).trim();
                pCodLocalOrigen =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, i,
                                                            COL_LOCAL_ORIGEN).trim();
                pNumPedOrigen =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, i,
                                                            COL_NUM_PED_ORIGEN).trim();
                pSecDetOrigen =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, i,
                                                            COL_SEC_PED_ORIGEN).trim();
                pCodProdOrigen =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, i,
                                                            COL_COD_PED_ORIGEN).trim();
                pCantUsoOrigen =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, i,
                                                            COL_CANT_USO).trim();
                pValFracMinOrigen =
                        FarmaUtility.getValueFieldArrayList(listaPedOrigen, i,
                                                            COL_VAL_FRAC_MIN).trim();

                for (int j = 0; j < listaCanjesPosibles.size(); j++) {
                    codCampana = listaCanjesPosibles.get(j).toString().trim();
                    if (pCodCamp.trim().equalsIgnoreCase(codCampana.trim())) {
                        vEfectivo = false;
                        break;
                    }
                }

                if (vEfectivo) {
                    DBVentas.añadePedidosOrigenCanje(pDniCli, pCodCamp,
                                                     pNumPed, pCodLocalOrigen,
                                                     pNumPedOrigen,
                                                     pSecDetOrigen,
                                                     pCodProdOrigen,
                                                     pCantUsoOrigen,
                                                     pValFracMinOrigen);
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
            e.printStackTrace();
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
        System.err.println("VariablesCampAcumulada.vCodProdFiltro:" +
                           VariablesCampAcumulada.vCodProdFiltro);

        FarmaVariables.vAceptar = false;

        //lanzar dialogo las campañas por asociar
        DlgListaCampAcumulada dlgListaCampAcumulada =
            new DlgListaCampAcumulada(myParentFrame, "", true);
        dlgListaCampAcumulada.setVisible(true);
        //cargas las campañas de fidelizacion
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            log.debug("INVOCANDO CARGAR CAMPAÑAS DEL CLIENTES ..:" +
                      VariablesFidelizacion.vNumTarjeta);
            UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta.trim());
            log.debug("FIN INVOCANDO CARGAR CAMPAÑAS DEL CLIENTES ..");

            /**mostranto el nombre del cliente **/
            lblCliente.setText(VariablesFidelizacion.vNomCliente); /*+" "
                                   +VariablesFidelizacion.vApePatCliente+" "
                                   +VariablesFidelizacion.vApeMatCliente);*/
            //VariablesFidelizacion.vApeMatCliente = Variables

            log.debug("imprmiendo todas las variables de fidelizacion");
            log.debug("VariablesFidelizacion.vApeMatCliente:" +
                      VariablesFidelizacion.vApeMatCliente);
            log.debug("VariablesFidelizacion.vApePatCliente:" +
                      VariablesFidelizacion.vApePatCliente);
            log.debug("VariablesFidelizacion.vCodCli:" +
                      VariablesFidelizacion.vCodCli);
            log.debug("VariablesFidelizacion.vCodGrupoCia:" +
                      VariablesFidelizacion.vCodGrupoCia);
            log.debug("VariablesFidelizacion.vDataCliente:" +
                      VariablesFidelizacion.vDataCliente);
            log.debug("VariablesFidelizacion.vDireccion:" +
                      VariablesFidelizacion.vDireccion);
            log.debug("VariablesFidelizacion.vDniCliente:" +
                      VariablesFidelizacion.vDniCliente);
            log.debug("VariablesFidelizacion.vDocValidos:" +
                      VariablesFidelizacion.vDocValidos);
            log.debug("VariablesFidelizacion.vEmail:" +
                      VariablesFidelizacion.vEmail);
            log.debug("VariablesFidelizacion.vFecNacimiento:" +
                      VariablesFidelizacion.vFecNacimiento);
            log.debug("VariablesFidelizacion.vIndAgregoDNI:" +
                      VariablesFidelizacion.vIndAgregoDNI);
            log.debug("VariablesFidelizacion.vIndConexion:" +
                      VariablesFidelizacion.vIndConexion);
            log.debug("VariablesFidelizacion.vIndEstado:" +
                      VariablesFidelizacion.vIndEstado);
            log.debug("VariablesFidelizacion.vIndExisteCliente:" +
                      VariablesFidelizacion.vIndExisteCliente);
            log.debug("VariablesFidelizacion.vListCampañasFidelizacion:" +
                      VariablesFidelizacion.vListCampañasFidelizacion);
            log.debug("VariablesFidelizacion.vNomCliente:" +
                      VariablesFidelizacion.vNomCliente);
            log.debug("VariablesFidelizacion.vNomClienteImpr:" +
                      VariablesFidelizacion.vNomClienteImpr);
            log.debug("VariablesFidelizacion.vNumTarjeta:" +
                      VariablesFidelizacion.vNumTarjeta);
            log.debug("VariablesFidelizacion.vSexo:" +
                      VariablesFidelizacion.vSexo);
            log.debug("VariablesFidelizacion.vSexoExists:" +
                      VariablesFidelizacion.vSexoExists);
            log.debug("VariablesFidelizacion.vTelefono:" +
                      VariablesFidelizacion.vTelefono);
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
                listaCampLimitUsadosMatriz =
                        (ArrayList)listaCampLimitUsadosMatriz.get(0);
            }
            log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> " +
                      listaCampLimitUsadosMatriz);
        } catch (Exception e) {
            log.debug("error al obtener las campañas limitadas ya usados por cliente en MATRIZ : " +
                      e.getMessage());
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

        List listaCodProds =
            new ArrayList(); //listaTemporal para tener el listado de codigo de productos
        Map mapaAux;
        String codProdAux = "";
        String codCampAux = "";
        //dubilluz 21.06.2011
        double totalProducto = 0.0;
        System.err.println("LISTA PROD:" +
                           VariablesVentas.vArrayList_ResumenPedido);
        ///////////////////////////////////////////////////////////////////
        VariablesVentas.vListProdExcluyeAcumAhorro =  new ArrayList();
        VariablesVentas.vListProdExcluyeAcumAhorro.clear();
        ///////////////////////////////////////////////////////////////////
        //Limpiar las marcas de prod cupon
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {

            /**agregar al arreglo de cod_productos*/
            mapaAux = new HashMap();
            codProdAux =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 0).toString();
            // precio de venta total x producto.
            // dubilluz 21.06.2011
            totalProducto =
            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                i, 3).toString())*
            FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                i, 4).toString());
            // fin dubilluz 21.06.2011
            mapaAux.put("COD_PROD", codProdAux);
            mapaAux.put("TOTAL_PROD", totalProducto+"");
            listaCodProds.add(mapaAux);
            /**fin de agregar al arreglo de cod_productos**/

            double auxPrecio =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i,
                                                                                  3));
            double auxCantidad =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i,
                                                                                  4));

            //JCHAVEZ 29102009 inicio
            String auxPrecAnt;
            try {
                if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                    VariablesVentas.vIndAplicaRedondeo =
                            DBVentas.getIndicadorAplicaRedondedo();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                auxPrecAnt =
                        FarmaUtility.formatNumber(auxPrecio * auxCantidad, 3);
            } else {
                auxPrecAnt =
                        FarmaUtility.formatNumber(auxPrecio * auxCantidad);
            }
            //JCHAVEZ 29102009 fin
            double porcIgv =
                FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                  i,
                                                                                  11));
            double precioTotal = auxPrecio * auxCantidad;
            double totalIgv =
                precioTotal - (precioTotal / (1 + porcIgv / 100));
            String vTotalIgv = FarmaUtility.formatNumber(totalIgv);
            //aqui donde cambian el precio del producto
            //version anterior era a 2 decimales
            //String cPrecioFinal = FarmaUtility.formatNumber(auxPrecio);
            //String cPrecioVta = FarmaUtility.formatNumber(auxPrecio);
            //version actual a 3 decimales
            String cPrecioFinal = FarmaUtility.formatNumber(auxPrecio, 3);
            String cPrecioVta = FarmaUtility.formatNumber(auxPrecio, 3);


            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(12,
                                                                             vTotalIgv);
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(COL_RES_CUPON,
                                                                             "");
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(5,
                                                                             ""); //columna de porcentaje descuento
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(3,
                                                                             cPrecioVta);
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(6,
                                                                             cPrecioFinal);
            ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7,
                                                                             auxPrecAnt);

            /*tblProductos.setValueAt(cPrecioVta, i, 3);//Precio Vta
	        tblProductos.setValueAt(" ", i, COL_RES_DSCTO);//Total Precio Vta
	        tblProductos.setValueAt(cPrecioFinal, i, 6);//Total Precio Vta
	        tblProductos.setValueAt(auxPrecAnt, i, 7);//Total Precio Vta*/

        }


        /**********
    	 * recorriendo todos los productos y calculando el descuento que es aplicable a por cada campaña.
    	 * ************/
        log.debug("listaCodProds:" + listaCodProds);
        log.debug("VariablesVentas.vArrayList_Cupones:" +
                  VariablesVentas.vArrayList_Cupones);

        VariablesVentas.vListDctoAplicados =
                new ArrayList(); //el detalle de los dscto Aplicados

        List prodsCampanias = new ArrayList();
        if (listaCodProds.size() > 0 &&
            VariablesVentas.vArrayList_Cupones.size() > 0) {
            prodsCampanias =
                    UtilityVentas.prodsCampaniasAplicables(listaCodProds,
                                                           VariablesVentas.vArrayList_Cupones,
                                                           VariablesVentas.vArrayList_ResumenPedido);
        }
        log.debug("prodsCampanias:" + prodsCampanias);
        //INICIALIZANDO TODAS LAS CAMPANIAS APLICABLES A LOS PRODUCTOS
        //para conservar el acumulado de ahorros totales
        List listaCampAhorro =
            new ArrayList(); //lista de campañas descuento por producto
        Map mapaTemp;
        String codCampTemp = "";

        List prodsCampaniasNUEVA = new ArrayList();
        for (int u = 0; u < prodsCampanias.size(); u++) {
            codCampTemp =
                    ((Map)prodsCampanias.get(u)).get("COD_CAMP_CUPON").toString();
            // dubilluz 01.06.2012
            if (UtilityFidelizacion.getPermiteCampanaTarj(codCampTemp,
                                                          VariablesFidelizacion.vDniCliente,
                                                          VariablesFidelizacion.vNumTarjeta))
               {
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
            }
            else {
                log.debug("la campana ingresada NO ES VALIDA para la tarjeta " + codCampTemp);
            }

        }

        System.err.println("prodsCampanias>>>"+prodsCampanias.size());
        prodsCampanias = prodsCampaniasNUEVA;
        System.err.println("prodsCampanias>>>"+prodsCampanias.size());
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

        double cantUnidAcumulado =
            0.0; //contador de unidades aplicados a una camapania
        double ahorroAcumulado =
            0.0; //contador monto ahorro acumulado a una campania

        int cantAplicable = 0, cantAplicableAux = 0;


        int cantPed = 0; // cantidad del producto dentro de resumen pedido
        int fraccionPëd = 0; // cantidad del producto dentro de resumen pedido
        double cantUnidPed =
            0.0; // cantidad en unidades del pedido
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
        System.err.println("ProdCamp.:" + prodsCampanias);
        for (int i = 0; i < prodsCampanias.size(); i++) {
            valorCuponNuevo = 0.0;
            mapaAux = (Map)prodsCampanias.get(i);
            codProdAux = (String)mapaAux.get("COD_PROD");
            codCampAux = (String)mapaAux.get("COD_CAMP_CUPON");
            ////////////////////////////
            if(((String)mapaAux.get("IND_EXCLUYE_ACUM_AHORRO")).trim().equalsIgnoreCase("S") )
                indExcluyeProd_AHORRO_ACUM = true;
            else
                indExcluyeProd_AHORRO_ACUM = false;
            ////////////////////////////
            log.debug("analizando el prod:" + codProdAux + ",cod_camp_cupon:" + 
                      codCampAux);
            //verifica si existe la campaña en el listado y asi operarlo
            //DUBILLUZ 05.06.2012

            //BUSCANDO EL INDICE DEL PRODUCTO EN ARREGLO AL CUAL APLICAR EL DSCTO;
            int indiceProducto = -1;
            ArrayList listaDatosProd = new ArrayList();
            for (int m = 0;
                 m < VariablesVentas.vArrayList_ResumenPedido.size(); m++) {
                listaDatosProd =
                        (ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(m);
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
            for (int m = 0; m < VariablesVentas.vArrayList_Cupones.size();
                 m++) {
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
                cantUnidPed =
                        (cantPed * 1.00) / fraccionPëd; //cantidad en unidades

                totalXProd =
                        FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(7));

                //obtiendo el mapa del cupon a aplicar
                mapaCupon =
                        (Map)VariablesVentas.vArrayList_Cupones.get(indiceCamp); //mapa del cupon
                Map mapaCampProd =
                    (Map)listaCampAhorro.get(indiceProdCamp); //mapa camp ahorro


                /**APLICANDO EL DSCTO AL PRODUCTO CON ESCA CAMPANIA**/
                //obtieniendo el acumulado de unidades y acumulado de ahorros que se tiene
                ahorroAcumulado =
                        Double.parseDouble(mapaCampProd.get("AHORRO_ACUM").toString());
                //cantUnidAcumulado = Double.parseDouble(mapaCampProd.get("UNID_ACUM").toString());


                //CALCULANDO EL DCTO AL PRODUCTO CON LA CAMPANA
                double unidMinUso =
                    Double.parseDouble(mapaCupon.get("UNID_MIN_USO").toString());
                double montMinUso =
                    Double.parseDouble(mapaCupon.get("MONT_MIN_USO").toString());
                //unidades aplicables de la campania
                //double unidMaximaAplicable = Double.parseDouble(mapaCupon.get("UNID_MAX_PROD").toString());
                //Obtiene el maximo de unidades a la compra del producto
                //DUBILLUZ 28.05.2009
                double unidMaximaAplicable =
                    UtilityFidelizacion.getMaxUnidDctoProdCampana(codCampAux,
                                                                  codProdAux);
                if (unidMaximaAplicable == -1) {
                    unidMaximaAplicable =
                            Double.parseDouble(mapaCupon.get("UNID_MAX_PROD").toString());
                }

                //Obtiene el porcentaje descuento personalizado de producto
                //DUBILLUZ 01.06.2010
                valorCuponNuevo = Double.parseDouble( mapaCupon.get("VALOR_CUPON").toString());
                /*
                 Esto se comentara porque NO RECUERDAN CUANDO PIDIERON ESTO.
                valorCuponNuevo =
                        UtilityFidelizacion.getDescuentoPersonalizadoProdCampana(codCampAux,
                                                                                 codProdAux);
                if (valorCuponNuevo == -1) {
                    valorCuponNuevo =
                            Double.parseDouble(mapaCupon.get("VALOR_CUPON").toString());
                }
                */

                double ahorroAplicable =
                    Double.parseDouble(mapaCupon.get("MONTO_MAX_DESCT").toString()) -
                    ahorroAcumulado;
                if (cantUnidPed > unidMaximaAplicable) {
                    cantAplicable =
                            Math.round((float)UtilityVentas.Truncar(unidMaximaAplicable *
                                                                    fraccionPëd,
                                                                    0));
                } else {
                    cantAplicable =
                            Math.round((float)(cantUnidPed * fraccionPëd));
                }
                log.debug("unidMaximaAplicable:" + unidMaximaAplicable +
                          ",cantAplicable:" + cantAplicable + ", cantPed:" +
                          cantPed);

                //precioVtaConDcto = ( precioVtaOrig * (( 100.0-Double.parseDouble( mapaCupon.get("VALOR_CUPON").toString() ) )/100.0) );
                //Cambiado para usar la variable
                precioVtaConDcto =
                        (precioVtaOrig * ((100.0 - valorCuponNuevo) / 100.0));
                log.debug("precioVtaOriginal:" + precioVtaOrig);
                log.debug("VALOR_CUPON:" +
                          mapaCupon.get("VALOR_CUPON").toString());
                log.debug("valorCuponNuevo:" + valorCuponNuevo);
                log.debug("precioVtaConDcto:" + precioVtaConDcto);
                //SI LA CAMPANIA PERMITE VENDER POR DEBAJO DEL COSTO PROMEDIO
                String sPrecioFinal = "";
                if (mapaCupon.get("IND_VAL_COSTO_PROM").toString().equals(FarmaConstants.INDICADOR_N)) {
                    //VERIFICANDO SI ESTA EN N:NO PERMITIR VENDER POR DEBAJO DEL COSTO PROMEDIO
                    try {
                        sPrecioFinal =
                                DBVentas.getPrecioFinalCampania(codProdAux,
                                                                codCampAux,
                                                                precioVtaConDcto,
                                                                precioVtaOrig,
                                                                fraccionPëd).trim();

                        if (Double.parseDouble(sPrecioFinal) >
                            precioVtaConDcto) {
                            precioVtaConDcto =
                                    Double.parseDouble(sPrecioFinal);
                        }
                    } catch (Exception e) {
                        log.debug("Exception e:" + e);
                    }
                }
                //fin de verificacion de venta por debajo del costo promedio.

                //calculando el nuevo total
                neoTotalXProd =
                        (precioVtaConDcto * cantAplicable) + (precioVtaOrig *
                                                              (cantPed -
                                                               cantAplicable));
                ahorro = (precioVtaOrig - precioVtaConDcto) * cantAplicable;
                log.debug("ahorro:" + ahorro + ",ahorroAplicable:" +
                          ahorroAplicable);
                if (ahorro >
                    ahorroAplicable) { //se volvera a calcular si el ahorro es superior al aplicable
                    //int cantUnidAplicables = Math.round( (float)( ( ( totalXProd - ahorroAplicable ) / precioVtaConDcto ) * fraccionPëd ));

                    precioVtaConDcto =
                            precioVtaOrig - (ahorroAplicable) / cantAplicable;
                    int cantAplicableAux2;
                    cantAplicableAux2 = cantAplicable;

                    cantAplicable =
                            Math.round((float)UtilityVentas.Truncar((ahorroAplicable /
                                                                     (precioVtaOrig -
                                                                      precioVtaConDcto)),
                                                                    0));

                    if (cantAplicableAux2 > cantAplicable) {
                        cantAplicable = cantAplicableAux2;
                        System.err.println("cantidad cambiada");
                    }

                    neoTotalXProd =
                            (precioVtaConDcto * cantAplicable) + (precioVtaOrig *
                                                                  (cantPed -
                                                                   cantAplicable));
                    log.debug("precioVtaConDcto:" + precioVtaConDcto);
                    log.debug("precioVtaOrig:" + precioVtaOrig);
                    log.debug("cantAplicable:" + cantAplicable);
                    ahorro =
                            (precioVtaOrig - precioVtaConDcto) * cantAplicable;
                    log.debug("ahorro:" + ahorro);
                }
                log.debug("ahorro 2:" + ahorro);
                /*
                                //--INICIO de verificar maximo descuento por dia del DNI
                                // DUBILLUZ 27.05.2009


                                if( (acumuladoDesctoPedido + ahorro) > VariablesFidelizacion.vMaximoAhorroDNIxPeriodo ){

                                    //ahorro = (precioVtaOrig - precioVtaConDcto)*cantAplicable;
                                    System.err.println("precioVtaOrigl "+precioVtaOrig);
                                    System.err.println("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo "+VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                                    System.err.println("acumuladoDesctoPedido "+acumuladoDesctoPedido);
                                    System.err.println("cantAplicable "+cantAplicable);
                                    precioVtaConDcto = precioVtaOrig - (VariablesFidelizacion.vMaximoAhorroDNIxPeriodo-acumuladoDesctoPedido)/cantAplicable;
                                    System.err.println("**precioVtaConDcto "+precioVtaConDcto);

                                    System.err.println("**ahorro "+ahorro);
                                    System.err.println("**ahorroAplicable "+ahorroAplicable);
                                    System.err.println("**precioVtaOrig "+precioVtaOrig);
                                    System.err.println("**precioVtaConDcto "+precioVtaConDcto);
                                    cantAplicableAux=cantAplicable;
                                    cantAplicable = Math.round( (float) UtilityVentas.Truncar( ( (VariablesFidelizacion.vMaximoAhorroDNIxPeriodo-acumuladoDesctoPedido)/(precioVtaOrig-precioVtaConDcto) ) , 0)  );
                                    if(cantAplicableAux>cantAplicable)
                                        cantAplicable = cantAplicableAux;


                                    System.err.println("**cantAplicable "+cantAplicable);
                                    System.err.println("-----");
                                    System.err.println("**precioVtaConDcto "+precioVtaConDcto);
                                    System.err.println("**precioVtaOrig "+precioVtaOrig);
                                    System.err.println("**cantPed "+cantPed);
                                    neoTotalXProd = (precioVtaConDcto * cantAplicable) + (precioVtaOrig * (cantPed - cantAplicable) );
                                    System.err.println("**neoTotalXProd "+neoTotalXProd);
                                    ahorro = (precioVtaOrig - precioVtaConDcto)*cantAplicable;
                                    System.err.println("-----");
                                    System.err.println("**precioVtaOrig "+precioVtaOrig);
                                    System.err.println("**precioVtaConDcto "+precioVtaConDcto);
                                    System.err.println("**cantAplicable "+cantAplicable);
                                }
                                acumuladoDesctoPedido += ahorro;
                                System.err.println("Descuento Parcial "+ahorro);
                                System.err.println("Descuento acumulado "+acumuladoDesctoPedido);
                                // Fin de Validacion de Maximo -- DUBILLUZ

                                 * */

                /** hasta aqui se tiene cantidad al cual se va aplicar el dcto
				 *  ahorro en monto
				 *  nuevo total por producto
				 * **/

                neoPrecioVtaXProd =
                        UtilityVentas.Truncar(neoTotalXProd / cantPed, 3);
                //por si deseara saber la diferencia
                double diferencia =
                    neoTotalXProd - UtilityVentas.Truncar(neoPrecioVtaXProd *
                                                          cantPed, 2);
                neoTotalXProd =
                        UtilityVentas.Truncar(neoPrecioVtaXProd * cantPed, 2);
                neoDctoPorcentaje =
                        UtilityVentas.Truncar(((precioVtaOrig - neoPrecioVtaXProd) /
                                               precioVtaOrig) * 100.0, 2);

                //ver si el ahorro calculado es mayor al que se tenia anteriormente
                if (ahorro > mayorAhorro && cantUnidPed >= unidMinUso )
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
            if ((i + 1) == 
                prodsCampanias.size()) { //si es el ultimo calcular aplicar el mayor dscto al producto
                flagAplicarMayorDcto = true;

            } else { //quiere decir que no es el ultimo prod y campana a analizar
                /**verificar si el cod_prod siguiente es diferente al que se tiene*/
                mapaAux2 = (Map)prodsCampanias.get(i + 1);
                if (!mapaAux2.get("COD_PROD").toString().equals(codProdAux)) { //si el producto siguiente es diferente tonces aplicar mayor dcto 
                    flagAplicarMayorDcto = true;
                }
            }

            log.debug("flagAplicarMayorDcto:" + flagAplicarMayorDcto + 
                      ",mayorAhorro:" + mayorAhorro);
            /**verificar si se aplica el mayor dscto**/
            if (flagAplicarMayorDcto && 
                mayorAhorro > 0.0) { //se agrego este flag para no repetir codigo


                //--INICIO de verificar maximo descuento por dia del DNI
                // DUBILLUZ 27.05.2009
                if (((Map)VariablesVentas.vArrayList_Cupones.get(indiceCamp)).get("IND_FID").toString().trim().equals(FarmaConstants.INDICADOR_S) | 
                    VariablesFidelizacion.vDniCliente.trim().length() > 0) {
                    if(!indExcluyeProd_AHORRO_ACUM) {
                        if ((acumuladoDesctoPedido + mayorAhorro) > VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) {
                            System.err.println("**mayorAhorro old " + mayorAhorro);

                            //ahorro = (precioVtaOrig - precioVtaConDcto)*cantAplicable;
                            System.err.println("precioVtaOrigl " + precioVtaOrig);
                            System.err.println("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo " +
                                               VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                            System.err.println("acumuladoDesctoPedido " + acumuladoDesctoPedido);
                            System.err.println("cantAplicable " + cantAplicable);
                            mayorAhorro = VariablesFidelizacion.vMaximoAhorroDNIxPeriodo - acumuladoDesctoPedido;
                            precioVtaConDcto =
                                    precioVtaOrig - (VariablesFidelizacion.vMaximoAhorroDNIxPeriodo - acumuladoDesctoPedido) /
                                    cantAplicable;
                            System.err.println("**precioVtaConDcto " + precioVtaConDcto);

                            System.err.println("**ahorro " + ahorro);
                            System.err.println("**precioVtaOrig " + precioVtaOrig);
                            System.err.println("**precioVtaConDcto " + precioVtaConDcto);
                            cantAplicableAux = cantAplicable;
                            cantAplicable =
                                    Math.round((float)UtilityVentas.Truncar(((VariablesFidelizacion.vMaximoAhorroDNIxPeriodo -
                                                                              acumuladoDesctoPedido) /
                                                                             (precioVtaOrig - precioVtaConDcto)), 0));
                            if (cantAplicableAux > cantAplicable) {
                                cantAplicable = cantAplicableAux;
                                System.err.println("cantidad cambiada");
                            }

                            System.err.println("**cantAplicable " + cantAplicable);
                            System.err.println("-----");
                            System.err.println("**precioVtaConDcto " + precioVtaConDcto);
                            System.err.println("**precioVtaOrig " + precioVtaOrig);
                            System.err.println("**cantPed " + cantPed);
                            neoTotalXProd =
                                    (precioVtaConDcto * cantAplicable) + (precioVtaOrig * (cantPed - cantAplicable));
                            System.err.println("**neoTotalXProd " + neoTotalXProd);
                            ahorro = (precioVtaOrig - precioVtaConDcto) * cantAplicable;
                            System.err.println("-----");
                            System.err.println("**precioVtaOrig " + precioVtaOrig);
                            System.err.println("**precioVtaConDcto " + precioVtaConDcto);
                            System.err.println("**cantAplicable " + cantAplicable);

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


                            System.err.println("**mayorAhorro new " + mayorAhorro);


                        }
                    }
                    
                    acumuladoDesctoPedido += mayorAhorro;
                    /*
                    if(!indExcluyeProd_AHORRO_ACUM)
                        VariablesFidelizacion.vAhorroDNI_Pedido += mayorAhorro;
                    */
                    System.err.println("Descuento Parcial " + mayorAhorro);
                    System.err.println("Descuento acumulado " + 
                                       acumuladoDesctoPedido);
                    // Fin de Validacion de Maximo -- DUBILLUZ
                }


                log.debug("aplicando el dcto al producto : " +
                          ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(0).toString());
                //duda si hacer esto esta bien la parecer al hacer set solo estaria referenciando direcion de memoria a la
                //variable señalada ahi podria haber problemas, es solo una suposicion. JCALLO

                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(COL_RES_CUPON,
                                                                                              campMayorAhorro);
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(COL_RES_DSCTO,
                                                                                              "" +
                                                                                              dctoVtaMayorAhorro);
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(6,
                                                                                              FarmaUtility.formatNumber(precioVtaMayorAhorro,
                                                                                                                        3));
                ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).set(7,
                                                                                              FarmaUtility.formatNumber(totalVtaMayorAhorro,
                                                                                                                        2));
                // dubilluz 07.11.2012
                if(indExcluyeProd_AHORRO_ACUM){
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
                System.out.println("JM 30.10.09, SEC_PED_VTA_DET " +
                                   (indiceProducto + 1));
                VariablesVentas.vListDctoAplicados.add(mapaDctoProd);
                //calculando el nuevo igv por producto con el dcto
                //obteniendo el procentaje de igv a aplicar
                double valorIgv =
                    FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(indiceProducto)).get(11));
                //
                double totalIgvProd =
                    totalVtaMayorAhorro - totalVtaMayorAhorro /
                    ((100.0 + valorIgv) / 100.0);
                log.debug("TOTALX_PRODUCTO:" + totalVtaMayorAhorro +
                          ",valorIgv:" + valorIgv + ",totalIgvProd:" +
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
                    log.debug("ver si :" +
                              (String)mapProdCampApli.get("COD_CAMP_CUPON") +
                              ":=:" + campMayorAhorro);
                    if (((String)mapProdCampApli.get("COD_CAMP_CUPON")).equals(campMayorAhorro)) { //ve si existe un valor en mapa si tiene cod_camp_cupon
                        indiceProdCampApli = m;
                        break;
                    }
                }
                //obtiendo la campania al cual se ve aumentar las unididas como el ahorro acumulado por campania
                mapProdCampApli = new HashMap();
                mapProdCampApli = (Map)listaCampAhorro.get(indiceProdCampApli);

                double acuAhorro =
                    Double.parseDouble(mapProdCampApli.get("AHORRO_ACUM").toString());
                //double acuUnidad = Double.parseDouble(mapProdCampApli.get("UNID_ACUM").toString());

                acuAhorro += mayorAhorro;
                //acuUnidad   += cantPedMayorAhorro/fraccionPëd;//a la cant del pedido diviendo entre el valorFracion del producto para obtener la UNIDADES

                ////todo esto se podria solo reemplazar por un simple put
                //eliminando el mapa del listado de campanaDctos
                listaCampAhorro.remove(indiceProdCampApli);
                mapProdCampApli.remove("AHORRO_ACUM");
                //mapProdCampApli.remove("UNID_ACUM");
                System.err.println("acuAhorro:" + acuAhorro);
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
        System.err.println("Ahorror Actual Total del Pedido " +
                           VariablesFidelizacion.vAhorroDNI_Pedido);

        //JCHAVEZ 29102009 inicio

        try {
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                VariablesVentas.vIndAplicaRedondeo =
                        DBVentas.getIndicadorAplicaRedondedo();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
            //JCHAVEZ 29102009 inicio Redondeando montos
            for (int i = 0;
                 i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
                String codProd =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 0).toString();
                double precioUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i,
                                                                                      3));
                double precioVentaUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i,
                                                                                      6));
                double precioVtaTotal =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i,
                                                                                      7));
                double cantidad =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                                                      i,
                                                                                      4));
                System.out.println("precioVtaTotal: " + precioVtaTotal);
                System.out.println("precioVentaUnit: " + precioVentaUnit);
                try {
                    double precioVtaTotalRedondeado =
                        DBVentas.getPrecioRedondeado(precioVtaTotal);
                    double precioVentaUnitRedondeado =
                        precioVtaTotalRedondeado / cantidad;
                    double precioUnitRedondeado =
                        DBVentas.getPrecioRedondeado(precioUnit);
                    System.out.println("precioVtaTotalRedondeado: " +
                                       precioVtaTotalRedondeado);
                    System.out.println("precioVentaUnitRedondeado: " +
                                       precioVentaUnitRedondeado);
                    double pAux2;
                    double pAux5;
                    pAux2 =
                            UtilityVentas.Redondear(precioVentaUnitRedondeado, 2);
                    System.out.println("pAux2: " + pAux2);
                    if (pAux2 < precioVentaUnitRedondeado) {
                        pAux5 = (pAux2 * Math.pow(10, 2) + 1) / 100;
                        System.out.println("pAux5: " + pAux5);
                    } else {
                        pAux5 = pAux2;
                    }


                    String cprecioVtaTotalRedondeado =
                        FarmaUtility.formatNumber(precioVtaTotalRedondeado, 3);
                    String cprecioVentaUnitRedondeado =
                        FarmaUtility.formatNumber(pAux5) /*precioVentaUnitRedondeado,3*/;
                    String cprecioUnitRedondeado =
                        FarmaUtility.formatNumber(precioUnitRedondeado, 3);
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(3,
                                                                                     cprecioUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(6,
                                                                                     cprecioVentaUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).set(7,
                                                                                     cprecioVtaTotalRedondeado);
                    System.out.println("precioVtaTotalRedondeado: " + "" +
                                       cprecioVtaTotalRedondeado);
                    System.out.println("precioVentaUnitRedondeado: " +
                                       cprecioVentaUnitRedondeado);
                    System.out.println("precioUnitRedondeado: " +
                                       cprecioUnitRedondeado);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.out.println("codProd: " + codProd);
                System.out.println("precioUnit: " + precioUnit);
                System.out.println("precioVentaUnit: " + precioVentaUnit);
                System.out.println("precioVtaTotal: " + precioVtaTotal);
                System.out.println("cantidad: " + cantidad);
            }

            //JCHAVEZ 29102009 fin
        }
        return true; //por ahora dejando asi nomas jeje

    } //fin del metodo de calculo y aplicacion de descuentos por CAMPANAS CUPONES

    /**
     * nuevo metodo encargado de resumen pedido
     * @author jcallo
     * **/
    private void neoOperaResumenPedido() {

        if(VariablesFidelizacion.vSIN_COMISION_X_DNI)lblDNI_SIN_COMISION.setVisible(true);
        else lblDNI_SIN_COMISION.setVisible(false);

        if(VariablesFidelizacion.V_NUM_CMP.trim().length()>0){
            lblMedico.setText("Receta de : "+VariablesFidelizacion.V_NUM_CMP+"-"+VariablesFidelizacion.V_NOMBRE+" / "+VariablesFidelizacion.V_DESC_TIP_COLEGIO);
            lblMedico.setVisible(true);
        }
        else{
            lblMedico.setText("");
            lblMedico.setVisible(false);
        }


        //jcallo quitar las campañas que ya han terminado de ser usados por el cliente
        log.debug("quitando las campañas limitadas en numeros de usos del cliente");
        log.debug("VariablesVentas.vArrayList_Cupones:" +
                  VariablesVentas.vArrayList_Cupones);
        log.debug("VariablesVentas.vArrayList_CampLimitUsadosMatriz:" +
                  VariablesVentas.vArrayList_CampLimitUsadosMatriz);
        for (int i = 0;
             i < VariablesVentas.vArrayList_CampLimitUsadosMatriz.size();
             i++) {
            String cod_camp_limit =
                VariablesVentas.vArrayList_CampLimitUsadosMatriz.get(i).toString().trim(); //por culpa de diego
            for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size();
                 j++) {
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


        System.out.println("VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF: " + VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);

		if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this,null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
		{
			lblCliente.setText(VariablesConvenioBTLMF.vNomCliente);
		    this.setTitle("Resumen de Pedido - Pedido por Convenio: OK " +
		                  VariablesConvenioBTLMF.vNomConvenio + " /  IP : " +
		                  FarmaVariables.vIpPc);
		    System.out.println("------------------------" + this.getTitle());
		    System.out.println("VariablesConvenio.vTextoCliente : *****" +
		                       VariablesConvenioBTLMF.vNomCliente);

		    lblLCredito_T.setText(VariablesConvenioBTLMF.vDatoLCredSaldConsumo);
            lblBeneficiario_T.setText(getMensajeComprobanteConvenio(VariablesConvenioBTLMF.vCodConvenio));

		}
		else
		{
        evaluaTitulo(); //titulo y datos dependiendo del tipo de pedido que se este haciendo
		}

        //refrescando los nuevos datos en tabla de resumen pedido
        tableModelResumenPedido.clearTable();
        tableModelResumenPedido.fireTableDataChanged();
        tblProductos.repaint();

        // cargamos los productos desde el ArrayList de Productos
        String prodVirtual = FarmaConstants.INDICADOR_N;
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            tableModelResumenPedido.insertRow((ArrayList)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).clone());
            tableModelResumenPedido.fireTableDataChanged();
        }

        //JCHAVEZ 29102009 inicio Redondeando montos
        System.out.println("VariablesVentas.vArrayList_Promociones: " +
                           VariablesVentas.vArrayList_Promociones);
        try {
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                VariablesVentas.vIndAplicaRedondeo =
                        DBVentas.getIndicadorAplicaRedondedo();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
            for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size();
                 i++) {
                double precioUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i,
                                                                                      3));
                double precioVentaUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i,
                                                                                      6));
                double precioVtaTotal =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i,
                                                                                      7));
                double cantidad =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Promociones,
                                                                                      i,
                                                                                      4));
                System.out.println("precioVtaTotal: " + precioVtaTotal);
                System.out.println("precioVentaUnit: " + precioVentaUnit);
                try {
                    double precioVtaTotalRedondeado =
                        DBVentas.getPrecioRedondeado(precioVtaTotal);
                    double precioVentaUnitRedondeado =
                        precioVtaTotalRedondeado / cantidad;
                    double precioUnitRedondeado =
                        DBVentas.getPrecioRedondeado(precioUnit);
                    System.out.println("precioVtaTotalRedondeado: " +
                                       precioVtaTotalRedondeado);
                    System.out.println("precioVentaUnitRedondeado: " +
                                       precioVentaUnitRedondeado);
                    double pAux2;
                    double pAux5;
                    pAux2 =
                            UtilityVentas.Redondear(precioVentaUnitRedondeado, 2);
                    System.out.println("pAux2: " + pAux2);
                    if (pAux2 < precioVentaUnitRedondeado) {
                        pAux5 = (pAux2 * Math.pow(10, 2) + 1) / 100;
                        System.out.println("pAux5: " + pAux5);
                    } else {
                        pAux5 = pAux2;
                    }


                    String cprecioVtaTotalRedondeado =
                        FarmaUtility.formatNumber(precioVtaTotalRedondeado, 3);
                    String cprecioVentaUnitRedondeado =
                        FarmaUtility.formatNumber(pAux5);
                    String cprecioUnitRedondeado =
                        FarmaUtility.formatNumber(precioUnitRedondeado, 3);
                    ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).set(3,
                                                                                   cprecioUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).set(6,
                                                                                   cprecioVentaUnitRedondeado);
                    ((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).set(7,
                                                                                   cprecioVtaTotalRedondeado);
                    System.out.println("precioVtaTotalRedondeado: " + "" +
                                       cprecioVtaTotalRedondeado);
                    System.out.println("precioVentaUnitRedondeado: " +
                                       cprecioVentaUnitRedondeado);
                    System.out.println("precioUnitRedondeado: " +
                                       cprecioUnitRedondeado);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                System.out.println("precioUnit: " + precioUnit);
                System.out.println("precioVentaUnit: " + precioVentaUnit);
                System.out.println("precioVtaTotal: " + precioVtaTotal);
                System.out.println("cantidad: " + cantidad);
            }
        }
        //JCHAVEZ 29102009 fin

        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size();
             i++) {
            tableModelResumenPedido.insertRow((ArrayList)((ArrayList)VariablesVentas.vArrayList_Promociones.get(i)).clone());
            tableModelResumenPedido.fireTableDataChanged();
        }
        tblProductos.repaint();
        FarmaUtility.setearPrimerRegistro(tblProductos, null, 0);
        //Seteando el valor de Si da o no Descuento al cliente.
        //daubilluz 26.05.2009
        AuxiliarFidelizacion.setMensajeDNIFidelizado(lblDNI_Anul,"R",txtDescProdOculto,this);

        evaluaFormaPagoFidelizado();
        
        pintaFilasProdExcluyeAhorroAcum();

    }
    
    private void pintaFilasProdExcluyeAhorroAcum() {
        ArrayList aux = new ArrayList();
        for (int i = 0; i < tableModelResumenPedido.data.size(); i++) {
            for (int j = 0; j < VariablesVentas.vListProdExcluyeAcumAhorro.size(); j++) {
                String pCod = VariablesVentas.vListProdExcluyeAcumAhorro.get(j).toString();
                if ((getValueColumna(tableModelResumenPedido.data,i,0)).trim().equalsIgnoreCase(pCod)){
                    aux.add(String.valueOf(i));
                }
            }
        }
        
        FarmaUtility.initSimpleListCleanColumns(tblProductos,
                                                tableModelResumenPedido,
                                                ConstantsVentas.columnsListaResumenPedido,
                                                aux, Color.white, Color.red,
                                                false);
    }

    public String getValueColumna(ArrayList tbl,int Fila, int Columna) {
        String pValor =
            FarmaUtility.getValueFieldArrayList(tbl,
                                                Fila, Columna);
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
/*
    private void setMensajeDNIFidelizado() {

        if (VariablesFidelizacion.vDniCliente.trim().length() > 0) {
            System.err.println("VariablesFidelizacion.vDniCliente: " +
                               VariablesFidelizacion.vDniCliente);
            System.err.println("VariablesVentas.vDNI_Anulado: " +
                               VariablesFidelizacion.vDNI_Anulado);
            if (VariablesFidelizacion.vDniCliente.trim().length() > 7) {
                if (!VariablesFidelizacion.vDNI_Anulado) {
                    lblDNI_Anul.setText("  DNI no afecto a Descuento.");
                    lblDNI_Anul.setVisible(true);
                } else {
                    lblDNI_Anul.setText("");
                    lblDNI_Anul.setVisible(false);
                    //Se evalua si ya esta en el limite de ahorro diario
                    //DUBILLUZ 28.05.2009
                    System.err.println("VariablesFidelizacion.vAhorroDNI_Pedido:" +
                                       VariablesFidelizacion.vAhorroDNI_Pedido);
                    System.err.println("VariablesFidelizacion.vAhorroDNI_x_Periodo:" +
                                       VariablesFidelizacion.vAhorroDNI_x_Periodo);
                    System.err.println("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo:" +
                                       VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                    System.err.println("VariablesFidelizacion.vIndComprarSinDcto:" +
                                       VariablesFidelizacion.vIndComprarSinDcto);
                    if (VariablesFidelizacion.vAhorroDNI_Pedido +
                        VariablesFidelizacion.vAhorroDNI_x_Periodo >=
                        VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) {
                        if (!VariablesFidelizacion.vIndComprarSinDcto) {

                            FarmaUtility.showMessage(this,
                                                     "El tope de descuento por persona es de S/ " +
                                                     FarmaUtility.formatNumber(VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) +
                                                     "\n" +
                                    "El cliente ya llegó a su tope.",
                                    txtDescProdOculto);
                            VariablesFidelizacion.vIndComprarSinDcto = true;
                        }

                    } else {
                        VariablesFidelizacion.vIndComprarSinDcto = false;
                    }
                }

            }


        }

    }
*/
    /**
     * Se carga cupones emitidos por el cliente fidelizado.
     * @author JCORTEZ
     * @since  05.08.09
     * */
    private void cargarCupones() {
        DlgListaCupones dlglista =
            new DlgListaCupones(myParentFrame, "", true);
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
        for (int i = 0; i < VariablesVentas.vArrayListCuponesCliente.size();
             i++) {
            cadena =
                    ((String)((ArrayList)VariablesVentas.vArrayListCuponesCliente.get(i)).get(1)).trim();
            if (UtilityVentas.esCupon(cadena, this, txtDescProdOculto)) {
                if (VariablesVentas.vEsPedidoConvenio) {
                    FarmaUtility.showMessage(this,
                                             "No puede agregar cupones a un pedido por convenio.",
                                             txtDescProdOculto);
                    return;
                }
                validarAgregarCupon(cadena);
            }
        }

    }

    /**
     * Determina si se llama a la nueva ventana de cobro o se cobra defrente
     * @author ASOSA
     * @since 17.02.2010
     */
    private void determinarCobro() {
        boolean flag = true;
        try {
            flag = DBCaja.llamarNewVentanaCobro(VariablesVentas.vNum_Ped_Vta);
        } catch (SQLException e) {
            System.out.println("ERROR en determinarCobro : " + e.getMessage());
            e.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "ERROR en determinarCobro() en el resumen del pedido: " +
                                     e.getMessage(), null);
        }
        if (flag) {
            System.out.println("JIJIJIJJIJIJIJJJJJJJJJJJJJJJJJJJJJJ");
            mostrarNewDlgCobro();
        } else {
            System.out.println("JOJOJOJOKOKOKOKJOJOJOKOKOKOOJOJOJKOKO");
            procesarCobroSinForm();
        }
    }

    /**
     * muestra la nueva ventana de cobro
     * @author ASOSA
     * @since 17.02.2010
     */
    private void mostrarNewDlgCobro() {
        DlgNewCobro dlgFormaPago = new DlgNewCobro(myParentFrame, "", true);
        dlgFormaPago.setIndPantallaCerrarAnularPed(true);
        /*dlgFormaPago.setIndPedirLogueo(false);        */
        dlgFormaPago.setIndPantallaCerrarCobrarPed(true);
        dlgFormaPago.setVisible(true);
        System.err.println("XXXXX_FarmaVariables.vAceptar:" +
                           FarmaVariables.vAceptar);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        } else
            pedidoGenerado = false;
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * procesa en cobro sin llamar a la nueva ventana de cobro
     * @author ASOSA
     * @since 17.02.2010
     */
    private void procesarCobroSinForm() {
        /********HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA***********/
        VariablesCaja.vIndPedidoConProdVirtual = false;
        VariablesFidelizacion.vRecalculaAhorroPedido = false;
        VariablesCaja.arrayDetFPCredito = new ArrayList();
        VariablesCaja.cobro_Pedido_Conv_Credito = "N";
        VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";
        VariablesCaja.arrayPedidoDelivery = new ArrayList();
        VariablesCaja.usoConvenioCredito = "";
        VariablesCaja.valorCredito_de_PedActual = 0.0;
        VariablesCaja.monto_forma_credito_ingresado = "0.00";
        /********ADIOSSSSSSSSSSSSSS******************************/
        UtilityNewCobro.inicializarVariables();
        /*txttarj.setEnabled(false);
        txtdol.setEnabled(false);
        lblvuelto.setText("0.00");*/
        VariablesNewCobro.numpeddiario = VariablesCaja.vNumPedPendiente;
        VariablesNewCobro.numpedvta = VariablesVentas.vNum_Ped_Vta;
        /*FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtsol);*/
        if (!UtilityCaja.existeCajaUsuarioImpresora(this, null) ||
            !UtilityCaja.validaFechaMovimientoCaja(this, null)) {
            FarmaUtility.showMessage(this,
                                     "El Pedido sera Anulado. Vuelva a generar uno nuevo.",
                                     null);
            try {
                DBCaja.anularPedidoPendiente(VariablesNewCobro.numpedvta);
                anularAcumuladoCanje();
                //HOLA
                VariablesCaja.vCierreDiaAnul = false;
                //ADIOS
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Pedido Anulado Correctamente",
                                         null);
                //cerrarVentana(true);
                return;
            } catch (SQLException sql) {
                FarmaUtility.liberarTransaccion();
                sql.printStackTrace();
                FarmaUtility.showMessage(this,
                                         "procesar sin abrir dlg new cobro - Error al Anular el Pedido.\n" +
                        sql.getMessage(), null);
                //cerrarVentana(true);
                return;
            }
        }
        buscaPedidoDiario();
        //HOLA
        VariablesCaja.vNumPedPendiente = "";
        VariablesCaja.vFecPedACobrar = "";
        FarmaVariables.vAceptar = false;
        //ADIOS


        String esCredito = "";
        String creditoSaldo = "";
        String fpago = "";
        try {
            esCredito =
                    DBConvenio.obtenerPorcentajeCopago(VariablesNewCobro.codconv);
        } catch (SQLException e) {
            e.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Error al saber si el convenio fue credito ",
                                     null);
        }
        System.out.println("VariablesNewCobro.codconv: " +
                           VariablesNewCobro.codconv);
        System.out.println("VariablesNewCobro.codcli: " +
                           VariablesNewCobro.codcli);
        if (esCredito.equalsIgnoreCase("S")) {
            /////////// JMIRANDA 01.07.2010 COMENTADO PARA NUEVO PROCESO DE CONVENIO
/*
                try {
                    creditoSaldo =
                            DBConvenio.obtieneConvenioCredito(VariablesNewCobro.codconv,
                                                              VariablesNewCobro.codcli,
                                                              FarmaConstants.INDICADOR_S);
                } catch (SQLException e) {
                    e.printStackTrace();
                    FarmaUtility.showMessage(this,
                                             "Error al saber si tiene saldo de credito aún",
                                             null);
                }
                if (creditoSaldo.equalsIgnoreCase("S")) {
                    String mon_cred =
                        UtilityNewCobro.obtenerCreditoDisponible(VariablesNewCobro.codcli,
                                                                 VariablesNewCobro.codconv,
                                                                 this);
                    mon_cred = mon_cred.replace(',', '.');
                    //FarmaUtility.showMessage(this,"mon_cred "+mon_cred,null);
                    //FarmaUtility.showMessage(this,"VariablesNewCobro.montoTotal "+VariablesNewCobro.montoTotal,null);
                    if (Double.parseDouble(mon_cred) >=
                        VariablesNewCobro.montoTotal) {
                        VariablesNewCobro.indCredito = "S";
                        try {
                            fpago =
                                    DBCaja.obtenerFPagoConvenio(VariablesNewCobro.codconv);
                        } catch (SQLException e) {
                            System.out.println("ERROR al obtener la forma de pago convenio: " +
                                               e.getMessage());
                        }
                        if (!fpago.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                            String list[] = fpago.split(",");
                            agregarDetalle(list[0], list[1], "SOLES",
                                           VariablesNewCobro.montoTotal,
                                           VariablesNewCobro.montoTotal, "01");
                        }
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "El monto excede el saldo disponible de " +
                                                 mon_cred + " soles", null);
                    }
                } else {
                    FarmaUtility.showMessage(this,
                                             "No hay saldo disponible en el credito del cliente",
                                             null);
                }
*/
            ///////////
            //JMIRANDA 01.07.2010

            boolean indExisteConv = false;
            boolean indMontoValido = false;
            try {
                //verificar la conexión con MATRIZ
                String vIndLinea =
                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                   FarmaConstants.INDICADOR_N);

                if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    System.out.println("Existe conexion a Matriz");
                    //Paso 1 valida que exista el convenio
                    indExisteConv =
                            UtilityConvenio.getIndClienteConvActivo(this,
                                                                    txtDescProdOculto,
                                                                    VariablesNewCobro.codconv,
                                                                    "", //DNI
                                                                    VariablesNewCobro.codcli);
                    if (indExisteConv) {
                        //Paso 2 validar el monto disponible
                        indMontoValido =
                                UtilityConvenio.getIndValidaMontoConvenio(this,
                                                                          txtDescProdOculto,
                                                                          VariablesNewCobro.codconv,
                                                                          "",
                                    //DNI
                                                                          VariablesNewCobro.montoTotal,
                                                                          VariablesNewCobro.codcli);
                        if (indMontoValido) {
                            creditoSaldo =
                                    DBConvenio.obtieneConvenioCredito(VariablesNewCobro.codconv,
                                                                      VariablesNewCobro.codcli,
                                                                      FarmaConstants.INDICADOR_S);

                            /////
                            if (creditoSaldo.equalsIgnoreCase("S")) {
                                String mon_cred =
                                    UtilityNewCobro.obtenerCreditoDisponible(VariablesNewCobro.codcli,
                                                                             VariablesNewCobro.codconv,
                                                                             this);
                                mon_cred = mon_cred.replace(',', '.');
                                if (Double.parseDouble(mon_cred) >=
                                    VariablesNewCobro.montoTotal) {
                                    VariablesNewCobro.indCredito = "S";

                                    fpago =
                                            DBCaja.obtenerFPagoConvenio(VariablesNewCobro.codconv);
                                    System.err.println("NOOOOOOOOOO, fpago"+fpago);
                                    if (!fpago.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                                        String list[] = fpago.split(",");
                                        agregarDetalle(list[0], list[1],
                                                       "SOLES",
                                                       VariablesNewCobro.montoTotal,
                                                       VariablesNewCobro.montoTotal,
                                                       "01");
                                        System.err.println("NOOOOOOOOOO 2, list[]"+list[0]+" - "+list[1]);
                                    }
                                } else {
                                    FarmaUtility.showMessage(this,
                                                             "El monto excede el saldo disponible de " +
                                                             mon_cred +
                                                             " soles", txtDescProdOculto);
                                }
                            } else {
                                FarmaUtility.showMessage(this,
                                                         "No hay saldo disponible en el credito del cliente",
                                                         txtDescProdOculto);
                            }
                            /////
                        }
                    }
                }

            } catch (SQLException sql) {
                //sql.printStackTrace();
                if (sql.getErrorCode() > 20000) {
                    FarmaUtility.showMessage(this,
                                             sql.getMessage().substring(10,
                                                                        sql.getMessage().indexOf("ORA-06512")),
                                             txtDescProdOculto);
                } else {
                    FarmaUtility.showMessage(this,
                                             "Ocurrió un error al validar el convenio.\n" +
                            sql.getMessage(), txtDescProdOculto);
                }
            }


        } else {
            VariablesNewCobro.indCredito = "N";
            try {
                fpago = DBCaja.obtenerFPagoConvenio(VariablesNewCobro.codconv);
            } catch (SQLException e) {
                System.out.println("ERROR al obtener la forma de pago convenio: " +
                                   e.getMessage());
            }
            if (!fpago.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                String list[] = fpago.split(",");
                agregarDetalle(list[0], list[1], "SOLES",
                               VariablesNewCobro.montoTotal,
                               VariablesNewCobro.montoTotal, "01");
            }
        }
    }

    private void anularAcumuladoCanje() {
        try {
            String pIndLineaMatriz =
                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                               FarmaConstants.INDICADOR_N);
            log.debug("pIndLineaMatriz " + pIndLineaMatriz);
            boolean pRspCampanaAcumulad =
                UtilityCaja.realizaAccionCampanaAcumulada(pIndLineaMatriz,
                                                          VariablesNewCobro.numpedvta,
                                                          this,
                                                          ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                          null,
                                                          FarmaConstants.INDICADOR_S) //Aqui si liberara stock al regalo
            ;

            System.out.println("pRspCampanaAcumulad " + pRspCampanaAcumulad);
            if (!pRspCampanaAcumulad) {
                System.out.println("Se recupero historico y canje  XXX");
                FarmaUtility.liberarTransaccion();
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);
            }
            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_S);
            FarmaUtility.aceptarTransaccion();
            log.info("Pedido anulado sin quitar respaldo.");
            //JMIRANDA 05.07.2010
            log.error("nooooOOOOOOOOOOOOOOOO. entro aqui");
            cerrarVentana(false);
        } catch (Exception sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_S);
        } finally {
            FarmaConnectionRemoto.closeConnection();
        }
    }

    private void buscaPedidoDiario() {
        ArrayList myArray = new ArrayList();
        VariablesNewCobro.numpeddiario =
                FarmaUtility.completeWithSymbol(VariablesNewCobro.numpeddiario,
                                                4, "0", "I");
        try {
            DBCaja.obtieneInfoCobrarPedido(myArray,
                                           VariablesNewCobro.numpeddiario,
                                           VariablesCaja.vFecPedACobrar);
            validaInfoPedido(myArray);
        } catch (SQLException sql) {
            log.error(null, sql);
            FarmaUtility.showMessage(this,
                                     "Error al obtener Informacion del Pedido.\n" +
                    sql.getMessage(), null);
        }
    }

    private void validaInfoPedido(ArrayList pArrayList) {
        if (pArrayList.size() < 1) {
            FarmaUtility.showMessage(this,
                                     "El Pedido No existe o No se encuentra pendiente de pago",
                                     null);
            VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_N;
            limpiarDatos();
            return;
        } else if (pArrayList.size() > 1) {
            FarmaUtility.showMessage(this, "Se encontro mas de un pedido.\n" +
                    "Ponganse en contacto con el area de Sistemas.", null);
            VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_N;
            limpiarDatos();
            return;
        } else {
            //HOLA
            VariablesCaja.vCodFormaPago = "";
            VariablesCaja.vDescFormaPago = "";
            VariablesCaja.vDescMonedaPago = "";
            VariablesCaja.vValMontoPagado = "";
            VariablesCaja.vValTotalPagado = "";
            //ADIOS
            VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_S;
            muestraInfoPedido(pArrayList);
            //verificaMontoPagadoPedido();
        }
    }

    private void limpiarDatos() {
        VariablesCaja.vIndPedidoSeleccionado = "N";
        VariablesCaja.vIndTotalPedidoCubierto = false;
        VariablesCaja.vIndPedidoCobrado = false;
        VariablesCaja.vIndPedidoConProdVirtual = false;
        VariablesCaja.vIndPedidoConvenio = "";
        VariablesConvenio.vCodCliente = "";
        VariablesConvenio.vCodConvenio = "";
        VariablesConvenio.vCodCliente = "";
        VariablesConvenio.vValCredDis = 0.00;
    }

    private void muestraInfoPedido(ArrayList pArrayList) {
        VariablesNewCobro.numpedvta =
                ((String)((ArrayList)pArrayList.get(0)).get(0)).trim();
        if (!UtilityCaja.verificaEstadoPedido(this,
                                              VariablesNewCobro.numpedvta,
                                              ConstantsCaja.ESTADO_PENDIENTE,
                                              txtDescProdOculto)) {
            VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_N;
            return;
        }
        FarmaUtility.liberarTransaccion();
        String dinero01 =
            ((String)((ArrayList)pArrayList.get(0)).get(1)).trim();
        VariablesNewCobro.montoTotal = FarmaUtility.getDecimalNumber(dinero01);
        String dinero02 =
            ((String)((ArrayList)pArrayList.get(0)).get(2)).trim();
        dinero02 =
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(dinero02) +
                                          FarmaUtility.getRedondeo(FarmaUtility.getDecimalNumber(dinero02)));
        VariablesNewCobro.montoDolar = Double.parseDouble(dinero02);
        VariablesNewCobro.tipoCambio =
                ((String)((ArrayList)pArrayList.get(0)).get(3)).trim();
        //lbltipcambio.setText(VariablesNewCobro.tipoCambio);
        VariablesNewCobro.codtipoCompPed =
                ((String)((ArrayList)pArrayList.get(0)).get(4)).trim();
        VariablesNewCobro.desctipoCompPed =
                ((String)((ArrayList)pArrayList.get(0)).get(5)).trim();
        VariablesNewCobro.nomcli =
                ((String)((ArrayList)pArrayList.get(0)).get(6)).trim();
        VariablesNewCobro.ruccli =
                ((String)((ArrayList)pArrayList.get(0)).get(7)).trim();
        VariablesNewCobro.dircli =
                ((String)((ArrayList)pArrayList.get(0)).get(8)).trim();
        VariablesNewCobro.tipoPed =
                ((String)((ArrayList)pArrayList.get(0)).get(9)).trim();
        /*if(VariablesNewCobro.codtipoCompPed.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA))
            jLabelWhite1.setText("Razon Social :");
        else
            jLabelWhite1.setText("Cliente :");
        */
        VariablesNewCobro.indDistribGratel =
                ((String)((ArrayList)pArrayList.get(0)).get(11)).trim();
        //ASOSA VariablesCaja.vIndDeliveryAutomatico = ((String)((ArrayList)pArrayList.get(0)).get(12)).trim();
        //JMIRANDA 05.07.2010
        VariablesCaja.vIndDeliveryAutomatico = ((String)((ArrayList)pArrayList.get(0)).get(12)).trim();
        VariablesNewCobro.cantItemsPed =
                ((String)((ArrayList)pArrayList.get(0)).get(13)).trim();
        //indicador de Convenio
        VariablesNewCobro.indPedConv =
                ((String)((ArrayList)pArrayList.get(0)).get(14)).trim();
        VariablesNewCobro.codconv =
                ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
        VariablesNewCobro.codcli =
                ((String)((ArrayList)pArrayList.get(0)).get(16)).trim();
        evaluaPedidoProdVirtual(VariablesNewCobro.numpedvta);
        if (VariablesNewCobro.indDistribGratel.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
            VariablesNewCobro.montoIngreso >= VariablesNewCobro.montoTotal) {
            VariablesNewCobro.flagPedCubierto = true;
        } else {
            VariablesNewCobro.flagPedCubierto = false;
        }
        /*lblmon.setText(""+VariablesNewCobro.montoTotal);
        lblcli.setText(VariablesNewCobro.nomcli);
        */
    }

    private void evaluaPedidoProdVirtual(String pNumPedido) {
        int cantProdVirtualesPed = 0;
        String tipoProd = "";
        cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
        if (cantProdVirtualesPed <= 0) {
            VariablesNewCobro.flagPedVirtual = false;
        } else {
            tipoProd = obtieneTipoProductoVirtual(pNumPedido);
            if (tipoProd.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA))
                VariablesNewCobro.vlblMsjPedVirtual =
                        "El pedido contiene una Tarjeta Virtual. Si lo cobra, No podrá ser anulado.";
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
                VariablesNewCobro.vlblMsjPedVirtual =
                        "Recarga Virtual.Sólo podrá anularse dentro de " +
                        time_max(pNumPedido) + " minutos." + " Telefono: ";
                VariablesNewCobro.nroTelf = "" + num_telefono(pNumPedido);
            } else {
                VariablesNewCobro.vlblMsjPedVirtual = "";
            }
            VariablesNewCobro.flagPedVirtual = true;
        }
        //lbltelf.setText(VariablesNewCobro.nroTelf);
    }

    private int cantidadProductosVirtualesPedido(String pNumPedido) {
        int cant = 0;
        try {
            cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
        } catch (SQLException ex) {
            log.error(null, ex);
            cant = 0;
            FarmaUtility.showMessage(this,
                                     "Error al obtener cantidad de productos virtuales.\n" +
                    ex.getMessage(), null);
        }
        return cant;
    }

    private String obtieneTipoProductoVirtual(String pNumPedido) {
        String tipoProd = "";
        try {
            tipoProd = DBCaja.obtieneTipoProductoVirtualPedido(pNumPedido);
        } catch (SQLException ex) {
            log.error(null, ex);
            tipoProd = "";
            FarmaUtility.showMessage(this,
                                     "Error al obtener cantidad de productos virtuales.\n" +
                    ex.getMessage(), null);
        }
        return tipoProd;
    }

    private String time_max(String pNumPedido) {
        String valor = "";
        try {
            valor = DBCaja.getTimeMaxAnulacion(pNumPedido);
        } catch (SQLException e) {
            log.error(null, e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" +
                    e.getMessage(), null);
        }
        return valor;
    }

    private String num_telefono(String numPed) {
        String num_telefono = "";
        try {
            num_telefono = DBCaja.getNumeroRecarga(numPed);
        } catch (SQLException e) {
            log.error(null, e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al obtener el numero de telefono de recarga.\n" +
                    e.getMessage(), null);
        }
        return num_telefono;
    }

    private void agregarDetalle(String codFP, String descFP, String moneda,
                                double monto, double total, String codmoneda) {
        BeanDetaPago objDPB = null;
        objDPB = new BeanDetaPago();
        objDPB.setCod_fp(codFP);
        objDPB.setDesc_fp(descFP);
        objDPB.setCant("0");
        objDPB.setMoneda(moneda);
        objDPB.setMonto(FarmaUtility.formatNumber(monto));
        objDPB.setTotal(FarmaUtility.formatNumber(total));
        objDPB.setCod_moneda(codmoneda);
        objDPB.setComodin("0.00");
        objDPB.setNrotarj("");
        objDPB.setFecventarj("");
        objDPB.setNomclitarj("");
        objDPB.setCodconv("");
        objDPB.setDnix("");
        objDPB.setCodvou("");
        objDPB.setLote("");
        System.out.println("monto: " + monto);
        System.out.println("total: " + total);
        VariablesNewCobro.listDeta.add(objDPB);
        VariablesNewCobro.montoIngreso =
                VariablesNewCobro.montoIngreso + total;
        verificaMontoPagadoPedido();
        /*if(!VariablesNewCobro.flagPedCubierto && codmoneda.equalsIgnoreCase("01")){
            txtdol.setEnabled(true);
            FarmaUtility.moveFocus(txtdol);
        }
        if(!VariablesNewCobro.flagPedCubierto && codmoneda.equalsIgnoreCase("02")){
            txttarj.setText(String.valueOf(VariablesNewCobro.saldo));
            DlgTarjeCred objTC=new DlgTarjeCred(myParentFrame,"",true);
            objTC.setVisible(true);
            if(FarmaVariables.vAceptar){
                VariablesNewCobro.flagPedCubierto=true;
            }else{
                VariablesNewCobro.flagPedCubierto=false;
                irAlComienzo();
            }
        }*/
        if (VariablesNewCobro.flagPedCubierto) {
            cobrar();
        }
    }

    private void verificaMontoPagadoPedido() {
        if (VariablesNewCobro.montoTotal > VariablesNewCobro.montoIngreso) {
            System.out.println("No Cubierto");
            VariablesNewCobro.flagPedCubierto = false;
            VariablesNewCobro.saldo =
                    VariablesNewCobro.montoTotal - VariablesNewCobro.montoIngreso;
            //lblvuelto.setText("0.00");
        } else {
            System.out.println("Cubierto");
            VariablesNewCobro.flagPedCubierto = true;
            VariablesNewCobro.saldo = 0;
            //lblvuelto.setText(FarmaUtility.formatNumber(VariablesNewCobro.montoIngreso-VariablesNewCobro.montoTotal));
        }
        //VariablesNewCobro.vuelto=lblvuelto.getText();
    }

    private void cobrar() {
        try {
            DBCaja.grabaInicioFinProcesoCobroPedido(VariablesNewCobro.numpedvta,
                                                    "I");
            System.out.println("Grabo el tiempo de inicio de cobro");
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            System.out.println("Error al grabar el tiempo de inicio de cobro");
        }
        procesar();
    }

    private void procesar() {
        //verificar si es un pedido con convenio
        /* if (VariablesNewCobro.indPedConv.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
              !VariablesNewCobro.codcli.equalsIgnoreCase("")) {
           //if(VariablesVentas.vEsPedidoDelivery){   //Se valida tipo delivery, aunque sea convenio.
              //log.debug("***************COBRO PEDIDO TIPO DELIVERY**********************");
              //procesoCobroDelivery();
        }else*/if (VariablesNewCobro.indCredito.trim().equalsIgnoreCase("S")) {
            procesoCobroCredito002();
        } else {
            log.debug("*Cobro de Pedido Normal");
            //la generacion de cupones no aplica convenios
            VariablesNewCobro.indPermitCampana =
                    verificaPedidoCamp(VariablesNewCobro.numpedvta);
            if (VariablesNewCobro.indPermitCampana.trim().equalsIgnoreCase("S") &&
                VariablesNewCobro.listDeta.size() > 0) {
                if (validarFormasPagoCupones(VariablesNewCobro.numpedvta)) {
                    // Se valida las formas de pago de las campañas
                    // de tipo Monto Descuento.
                    // Se verificara si puede permitir cobrar
                    cobrarPedido(); //procesar cobro de pedido
                }
            } else {
                cobrarPedido(); //procesar cobro de pedido
            }
            pedidoCobrado();
        }
        //UtilityNewCobro.inicializarVariables(); , ASOSA, 06.07.2010
        //Si la variable indica que de escape y recalcule todo el ahorro del cliente
        if (VariablesFidelizacion.vRecalculaAhorroPedido) { //ASOSA, lo comento porque al parecer ya ni entraria jamas a este if
            eventoEscape();
        }
    }

    private void procesoCobroCredito002() {
        String valido = "S";
        int f_fp_convenio = 2;
        VariablesCaja.usoConvenioCredito = "S";
        if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
            String pValidaCredito =
                validaCreditoCliente(f_fp_convenio).trim(); //aqui abre conexion remota
            if (pValidaCredito.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) { //ver si se execedio del credito disponible
                valido = "N";
                FarmaUtility.showMessage(this,
                                         "No se puede cobrar el Pedido. \n" +
                        "El cliente excede en S/. " + (diferencia * -1) +
                        " el limite de su crédito.", null);
                return;
            } else {
                if (pValidaCredito.equalsIgnoreCase("OUT")) { // NO hay conexion con matriz y no se puede cobrar el pedido
                    FarmaUtility.showMessage(this,
                                             "En este momento no hay linea con matriz.\n " +
                                             "Si el problema persiste comunicarse con el operador de sistema",
                                             null);
                    return;
                }
            }
        }
        log.debug("estado de la validacion de credito=" + valido);
        if (valido.equalsIgnoreCase("S")) {
            cobrarPedido();
            pedidoCobrado();
        }
    }


    /**
     * Carga la el Pedido pero en ARRAY
     */
    private void colocaFormaPagoDeliveryArray(String pNumPedido) {
        try {
            VariablesCaja.arrayPedidoDelivery = new ArrayList();
            DBCaja.colocaFormaPagoDeliveryArray(VariablesCaja.arrayPedidoDelivery,
                                                pNumPedido);
        } catch (SQLException ex) {
            log.error(null, ex);
            FarmaUtility.showMessage(this,
                                     "Error al obtener forma de pago delivery automatico.\n" +
                    ex.getMessage(), null);
        }
    }

    /** Obtiene el Codigo de Forma del Convenio
     */
    private String obtieneCodFormaConvenio(String pConvenio) {
        String codForma = "";
        try {
            codForma = DBCaja.cargaFormaPagoConvenio(pConvenio);
            System.out.println("CodFormaConve ***" + codForma);
        } catch (SQLException ex) {
            //ex.printStackTrace();
            log.error(null, ex);
            FarmaUtility.showMessage(this,
                                     "Error al obtener el Codigo de la forma de pago del Convenio.\n" +
                    ex.getMessage(), null);
        }
        return codForma.trim();
    }

    /**
     * Valida si uso el Credito
     */
    private int uso_Credito(String codFormaPago) {
        if (codFormaPago.trim().equalsIgnoreCase("N")) {
            if (VariablesCaja.uso_Credito_Pedido_N_Delivery.trim().equalsIgnoreCase("S"))
                return 2;
            else
                return -1;
        } else {
            ArrayList aux = new ArrayList();
            for (int i = 0; i < VariablesCaja.arrayPedidoDelivery.size();
                 i++) {
                aux = (ArrayList)VariablesCaja.arrayPedidoDelivery.get(i);
                System.out.println("VAriables de formaPago >>>" + aux);
                System.out.println("Comparando >>" +
                                   ((String)aux.get(0)).trim() + "xxxx" +
                                   codFormaPago.trim());
                if (((String)aux.get(0)).trim().equalsIgnoreCase(codFormaPago.trim()))
                    return i;
            }
            return -1;
        }
    }

    private String validaCreditoCliente(int f_fp_convenio) {
        String vRes = "";
        try {
            if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("N")) {
                VariablesConvenio.vValCoPago =
                        String.valueOf(VariablesNewCobro.montoIngreso); //VariablesCaja.monto_forma_credito_ingresado;
            } else if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S")) {
                /*VariablesConvenio.vValCoPago = FarmaUtility.getValueFieldJTable(tblDetallePago, //ASOSA
                                                                                  f_fp_convenio,
                                                                                  4).trim();*/
                BeanDetaPago objobj =
                    (BeanDetaPago)VariablesNewCobro.listDeta.get(f_fp_convenio);
                VariablesConvenio.vValCoPago = objobj.getMonto();
            }
            log.debug("VariablesConvenio.vValCoPago=" +
                      VariablesConvenio.vValCoPago);
            if (FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) !=
                0) {
                log.debug("jcallo: va usar credito por convenio");
                //verificar si hay linea con matriz y no cerrar la conexion
                String vIndLinea =
                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                   FarmaConstants.INDICADOR_N);
                //si hay linea
                if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    valor =
                            DBConvenio.validaCreditoCli(VariablesNewCobro.codconv,
                                                        VariablesNewCobro.codcli,
                                                        VariablesConvenio.vValCoPago,
                                                        FarmaConstants.INDICADOR_S);
                    log.debug("diferencia de credito que le quedaria al cliente por convenio: " +
                              valor);
                    diferencia = FarmaUtility.getDecimalNumber(valor);
                    if (diferencia < 0) {
                        log.debug("credito insuficiente del cliente, ya que se excederia en " +
                                  diferencia);
                        vRes = "S";
                    } else { //quiere decir que tiene saldo suficiente
                        VariablesConvenio.vValCredDis =
                                FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) +
                                diferencia;

                        VariablesConvenio.vCredito =
                                DBConvenio.obtieneCredito(VariablesNewCobro.codconv,
                                                          VariablesNewCobro.codcli,
                                                          FarmaConstants.INDICADOR_S);
                        log.debug("VariablesConvenio.vCredito: " +
                                  VariablesConvenio.vCredito);
                        VariablesConvenio.vCreditoUtil =
                                DBConvenio.obtieneCreditoUtil(VariablesNewCobro.codconv,
                                                              VariablesNewCobro.codcli,
                                                              FarmaConstants.INDICADOR_S);
                        log.debug("VariablesConvenio.vCreditoUtil: " +
                                  VariablesConvenio.vCreditoUtil);

                        VariablesConvenio.vValCredDis =
                                FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago.trim()); //FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago)  + diferencia ;

                        vRes = "N";
                    }
                } else { //quiere decir que no hay linea con matriz
                    vRes = "OUT";
                }
            } else {
                vRes = "N";
            }
        } catch (SQLException sql) {
            log.error(sql);
            FarmaUtility.showMessage(this,
                                     "Error al validar limite de credito.",
                                     null);
            //FarmaUtility.moveFocus(txtNroPedido);
            vRes = "N";
        }

        return vRes;
    }

    /**
     * Se valida que el pedido tenga productos de campaña
     */
    private String verificaPedidoCamp(String numPed) {
        String resp = "";
        try {
            resp = DBCaja.verificaPedidoCamp(numPed);
        } catch (SQLException e) {
            e.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al validar pedido por campaña.\n" +
                    e.getMessage(), null);
        }
        return resp;
    }

    private void cobrarPedido() {
        UtilityNewCobro.igualarVariables();
        cajita.setText(VariablesNewCobro.numpeddiario);
        DlgProcesarCobroNew dlgproc =
            new DlgProcesarCobroNew(myParentFrame, "", true, tabla01,
                                    lblvuelto, tabla02, cajita,
                                    VariablesNewCobro.listDeta);
        dlgproc.setVisible(true);
        if (!FarmaVariables.vAceptar) {
            if (VariablesCaja.vCierreDiaAnul) {
                anularAcumuladoCanje();
                //HOLA
                VariablesCaja.vCierreDiaAnul = false;
                //ADIOS
            }
        }
        /* Cierra la conexion si se utilizo credito */
        if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) { //ASOSA, lo comento debido a que es para convenio credito
            FarmaConnection.closeConnection();
            FarmaConnection.anularConnection();
        }
        VariablesCaja.vNumPedVta = "";
    }

    /**
     * Se valida los montos por productos que esten en una campaña para llevarse los cupones ganados
     * */
    private boolean validarFormasPagoCupones(String numPedVta) {

        boolean valor = true;
        String codSel, codobtenido = "";
        String monto1, monto2, descrip = "", desccamp = "", indAcep =
            "", codCamp = "", numPed = "";
        ArrayList array = new ArrayList();
        int maxform = 0, cant = 0;

        if (VariablesNewCobro.listDeta.size() > 0 &&
            VariablesNewCobro.saldo == 0) {

            obtieneFormaPagoCampaña(array, numPedVta);
            System.out.println("array::: " + array);
            if (array.size() > 0) {
                for (int j = 0; j < array.size(); j++) {
                    numPed = ((String)((ArrayList)array.get(j)).get(0)).trim();
                    codobtenido =
                            ((String)((ArrayList)array.get(j)).get(1)).trim();
                    monto1 = ((String)((ArrayList)array.get(j)).get(2)).trim();
                    desccamp =
                            ((String)((ArrayList)array.get(j)).get(3)).trim();
                    indAcep =
                            ((String)((ArrayList)array.get(j)).get(4)).trim();
                    codCamp =
                            ((String)((ArrayList)array.get(j)).get(5)).trim();

                    for (int i = 0; i < VariablesNewCobro.listDeta.size();
                         i++) {
                        maxform =
                                VariablesNewCobro.listDeta.size(); //ultima forma de pago restar vuelto
                        /*codSel=((String) tblDetallePago.getValueAt(i,0)).trim(); //ASOSA
                descrip=((String) tblDetallePago.getValueAt(i,1)).trim();*/

                        BeanDetaPago objx =
                            (BeanDetaPago)VariablesNewCobro.listDeta.get(i);
                        codSel = objx.getCod_fp();
                        descrip = objx.getDesc_fp();

                        if (VariablesNewCobro.listDeta.size() > 0) {

                            if (codSel.trim().equalsIgnoreCase(codobtenido.trim())) {
                                //monto2=((String) tblDetallePago.getValueAt(i,5)).trim(); //ASOSA
                                monto2 = objx.getTotal();

                                System.out.println("monto pagado :" +
                                                   Double.parseDouble(monto2));
                                System.out.println("monto exacto :" +
                                                   Double.parseDouble(monto1));

                                if (maxform == i + 1) {
                                    System.out.println("leyendo ultima forma de pago");
                                    monto2 =
                                            FarmaUtility.formatNumber(Double.parseDouble(monto2) -
                                                                      VariablesNewCobro.saldo);
                                    System.out.println("supuesto pago sin vuelto: " +
                                                       monto2);
                                }

                                cant = cant + 1;
                                if (cant == 1)
                                    actualizaPedidoCupon(codCamp, numPed, "S",
                                                         "N");
                                else if (cant > 1)
                                    actualizaPedidoCupon(codCamp, numPed, "N",
                                                         "N");

                            } else {
                                System.out.println("forma pago diferente");
                                valor = true;
                            }
                        }
                    }
                }
            }
            procesaCampSinFormaPago(VariablesNewCobro.numpedvta);
        } else {
            valor = true;
        }
        return valor;
    }

    /**
     * Se actualiza el estado del pedido cupon para emision
     * */
    private void actualizaPedidoCupon(String codCamp, String vtaNumPed,
                                      String estado, String indtodos) {
        try {
            DBCaja.actualizaIndImpre(codCamp, vtaNumPed, estado, indtodos);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            //e.printStackTrace();
            log.error(null, e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al validar la forma de pago del pedido.\n" +
                    e.getMessage(), null);
        }
    }

    /**
     * Se valida la impresion de las campañas que no tengan forma de de pago relacionadas
     * */
    private void procesaCampSinFormaPago(String vtaNumPed) {
        try {
            DBCaja.procesaCampSinFormaPago(vtaNumPed);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            //e.printStackTrace();
            log.error(null, e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al procesar campañas sin forma de pago.\n" +
                    e.getMessage(), null);
        }
    }

    /**
     * Se obtiene la informacion de campaña por pedido
     * */
    private void obtieneFormaPagoCampaña(ArrayList array, String vtaNumPed) {

        String result = "";
        array.clear();
        try {
            DBCaja.getFormaPagoCampaña(array, vtaNumPed);
        } catch (SQLException e) {
            //e.printStackTrace();
            log.error(null, e);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al validar la forma de pago del pedido.\n" +
                    e.getMessage(), null);
        }
    }

    private void pedidoCobrado() {

        if (VariablesCaja.vIndPedidoCobrado) {
            log.info("pedido cobrado !");
            if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) /*&&
                          indCerrarPantallaCobrarPed*/ {
                //Se valida ingreso de sobre
                System.out.println("VariablesCaja.vSecMovCaja-->" +
                                   VariablesCaja.vSecMovCaja);
                if (validaIngresoSobre()) {
                    //dubilluz 20.07.2010
                    //if(FarmaUtility.rptaConfirmDialog(this, "Existe efectivo suficiente. Desea ingresar sobres en su turno?")){
                    if(FarmaUtility.rptaConfirmDialog(this, "Ha excedido el importe máximo de dinero en su caja. \n" +
                                                            "Desea hacer entrega de un nuevo sobre?\n")){

                        mostrarIngresoSobres();
                    }
                }
                cerrarVentana(true);
            }
            limpiarDatos(); //ASOSA, a lo mejor se tendra que hacer algo parecido pero con mis variabes
            //limpiarPagos(); ASOSA
            //limpiaVariablesVirtuales(); ASOSA
            //FarmaUtility.moveFocus(txtNroPedido); ASOSA
            System.out.println("-********************LIMPIANDO VARIABLES***********************-");
        }
    }

    /**
     * Se valida el ingreso de sobre en local
     * */
    private boolean validaIngresoSobre() {
        boolean valor = false;
        String ind = "";
        try {
            System.out.println("VariablesCaja.vSecMovCaja-->" +
                               VariablesCaja.vSecMovCaja);
            ind = DBCaja.permiteIngreSobre(VariablesCaja.vSecMovCaja);
            System.out.println("indPermiteSobre-->" + ind);
            if (ind.trim().equalsIgnoreCase("S")) {
                valor = true;
            }

        } catch (SQLException sql) {
            valor = false;
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error validar ingreso de sobre.\n" +
                    sql.getMessage(), null);
        }
        return valor;
    }

    /**
     * Se da la opcion de ingresar sobre
     * */
    private void mostrarIngresoSobres() {

        DlgIngresoSobre dlgsobre =
            new DlgIngresoSobre(myParentFrame, "", true);
        dlgsobre.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(true);
        }
    }

    private void eventoEscape() {
        //se deja el indicador de impresio de cupon por pedido en N
        if (!VariablesNewCobro.numpedvta.equalsIgnoreCase("")) {
            VariablesNewCobro.indPermitCampana =
                    verificaPedidoCamp(VariablesNewCobro.numpedvta);
            if (VariablesNewCobro.indPermitCampana.trim().equalsIgnoreCase("S")) {
                actualizaPedidoCupon("", VariablesNewCobro.numpedvta, "N",
                                     "S");
            }
        }
        if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL)) {
            if ( /*indCerrarPantallaAnularPed && */VariablesNewCobro.indPedidoEncontrado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //Se anulara el pedido
                if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    /*if(FarmaUtility.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
                        try{
                            DBCaja.anularPedidoPendiente(VariablesCaja.vNumPedVta);
                            FarmaUtility.aceptarTransaccion();
                            log.info("Pedido anulado.");
                            FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                            cerrarVentana(true);
                        } catch(SQLException sql){
                            FarmaUtility.liberarTransaccion();
                            log.error(null,sql);
                            if(sql.getErrorCode()==20002)
                                FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null);
                            else if(sql.getErrorCode()==20003)
                                FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null);
                            else
                                FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                                cerrarVentana(true);
                        }
                    } */
                } else {
                    System.out.println("SALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIO");
                    try {
                        UtilityCaja.liberaProdRegalo(VariablesNewCobro.numpedvta,
                                                     ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                     FarmaConstants.INDICADOR_S);

                        DBCaja.anularPedidoPendienteSinRespaldo(VariablesNewCobro.numpedvta);
                        ///-- inicio de validacion de Campaña
                        String pIndLineaMatriz = FarmaConstants.INDICADOR_N;
                        boolean pRspCampanaAcumulad =
                            UtilityCaja.realizaAccionCampanaAcumulada(pIndLineaMatriz,
                                                                      VariablesNewCobro.numpedvta,
                                                                      this,
                                                                      ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                                      null,
                                                                      FarmaConstants.INDICADOR_S) //Aqui si liberara stock al regalo
                        ;

                        if (!pRspCampanaAcumulad) {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                  FarmaConstants.INDICADOR_S);
                        }
                        if (pIndLineaMatriz.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                  FarmaConstants.INDICADOR_S);
                        }
                        FarmaUtility.aceptarTransaccion();
                      log.error("aaaaaaaaaaaaaaaaa - NO SALIO BIEN");
                        log.info("Pedido anulado sin quitar respaldo.");
                        cerrarVentana(false);
                    } catch (SQLException sql) {
                        FarmaUtility.liberarTransaccion();
                        log.error(null, sql);
                        if (sql.getErrorCode() == 20002)
                            FarmaUtility.showMessage(this,
                                                     "El pedido ya fue anulado!!!",
                                                     null);
                        else if (sql.getErrorCode() == 20003)
                            FarmaUtility.showMessage(this,
                                                     "El pedido ya fue cobrado!!!",
                                                     null);
                        else
                            FarmaUtility.showMessage(this,
                                                     "Error al Anular el Pedido.\n" +
                                    sql.getMessage(), null);
                        cerrarVentana(true); //ASOSA, 06.07.2010, estaba en false
                    }
                }

            } else
                cerrarVentana(false);
        } else
            cerrarVentana(false);
    }

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
        if (VariablesVentas.vEsPedidoConvenio) //Ha elegido un convenio y un cliente
        {
            //-----------INI PEDIDO CONVENIO
            System.out.println("VariablesConvenio.vArrayList_DatosConvenio : " +
                               VariablesConvenio.vArrayList_DatosConvenio);
            String vCodCli =
                "" + VariablesConvenio.vArrayList_DatosConvenio.get(1);
            String vDniCli =
                "" + VariablesConvenio.vArrayList_DatosConvenio.get(5);
            String vCodConvenio =
                "" + VariablesConvenio.vArrayList_DatosConvenio.get(0);
            System.out.println("vCodConvenio " +
                               VariablesConvenio.vArrayList_DatosConvenio.get(0));
            System.out.println("vDniCLi " +
                               VariablesConvenio.vArrayList_DatosConvenio.get(5));
            if (!vCodCli.equalsIgnoreCase("")) {
                //--INI TIENE CODCLI
                String mensaje = "";
                //1° Obtiene valor de copago
                try {
                    double totalS =
                        FarmaUtility.getDecimalNumber(lblTotalS.getText());

                    if (FarmaUtility.getDecimalNumber(vCoPagoConvenio) != 0) {
                        //verificar la conexión con MATRIZ
                        String vIndLinea =
                            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                           FarmaConstants.INDICADOR_N);

                        if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            System.out.println("Existe conexion a Matriz");
                            //Paso 1 valida que exista el convenio
                            indExisteConv =
                                    UtilityConvenio.getIndClienteConvActivo(this,
                                                                            txtDescProdOculto,
                                                                            vCodConvenio,
                                                                            vDniCli,
                                                                            vCodCli);
                            if (indExisteConv) {
                                //Paso 2 validar el monto disponible
                                indMontoValido =
                                        UtilityConvenio.getIndValidaMontoConvenio(this,
                                                                                  txtDescProdOculto,
                                                                                  vCodConvenio,
                                                                                  vDniCli,
                                                                                  totalS,
                                                                                  vCodCli);
                                if (indMontoValido) {
                                    if(colocaVariablesDU(VariablesConvenio.vCodCliente,lblTotalS.getText())){
                                        //El convenio está activo y el monto a usar es correcto
                                        continuarCobroPedido(e);
                                    }
                                    else{

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
                    sql.printStackTrace();
                    if(sql.getErrorCode()>20000)
                    {
                      FarmaUtility.showMessage(this,sql.getMessage().substring(10,sql.getMessage().indexOf("ORA-06512")),txtDescProdOculto);
                    }else
                    {
                      FarmaUtility.showMessage(this,"Ocurrió un error al validar el convenio.\n"+sql,txtDescProdOculto);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
        if (VariablesVentas.vIndVolverListaProductos){

            agregarProducto();
        }
    }

    //JMIRANDA 23.06.2010

    public void continuarCobroPedido(KeyEvent e) {
        String vkF = "";
        if (e.getKeyCode() == KeyEvent.VK_F4) {

            vkF = "F4";
            agregarComplementarios(vkF);

        } else if (e.getKeyCode() == KeyEvent.VK_F1 || e.getKeyChar() == '+') {

            vkF = "F1";
            agregarComplementarios(vkF);
        }
    }


    public boolean colocaVariablesDU(String vCodCli,String totalS){
        boolean pResultado = false;
        String pCodPago = "";
        double diferencia = 0.0;
        double valTotal = 0.0;
        double totalS_double = 0.0;
            FarmaUtility.getDecimalNumber(totalS.trim());
        try {
            totalS_double =  FarmaUtility.getDecimalNumber(totalS);

            pCodPago =
                DBConvenio.obtieneCoPagoConvenio(VariablesConvenio.vCodConvenio,
                                                 vCodCli,
                                                 FarmaUtility.formatNumber(totalS_double));

            diferencia = 0.0;
            valTotal = FarmaUtility.getDecimalNumber(pCodPago);
            System.out.println("Monto Copago: " + pCodPago);
            String valor =
                DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio,
                                            vCodCli,
                                            FarmaUtility.formatNumber(valTotal),
                                            FarmaConstants.INDICADOR_S);
            System.out.println("Diferencia: " + valor);
            diferencia = FarmaUtility.getDecimalNumber(valor);
            System.out.println("VariablesConvenio.vIndSoloCredito: " + VariablesConvenio.vIndSoloCredito);
            if (diferencia < 0) {
                if (VariablesConvenio.vIndSoloCredito.equals(FarmaConstants.INDICADOR_N)) {
                    valTotal = valTotal + diferencia;
                }
            }

            VariablesConvenio.vValCoPago =
                    FarmaUtility.formatNumber(valTotal);
            System.err.println("0000000000000000000000:");
            System.out.println("VariablesConvenio.vValCoPago: " + VariablesConvenio.vValCoPago);
            System.err.println("0000000000000000000000:");
            if(VariablesConvenio.vValCoPago.trim().length()>0){
                pResultado = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
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
    private void grabarDetalle_02(String pTiComprobante, ArrayList array,
                                  int pFila, int tipo) throws Exception {

        VariablesVentas.vSec_Ped_Vta_Det = String.valueOf(pFila);
        VariablesVentas.vCod_Prod = ((String)(array.get(0))).trim();
    	System.out.println("grabarDetalle_02.vCod_Prod::"+VariablesVentas.vCod_Prod);

        VariablesVentas.vCant_Ingresada =
                String.valueOf(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(((String)(array.get(4))).trim())));
        //VariablesVentas.vVal_Prec_Vta = ((String)(array.get(6))).trim();
        VariablesVentas.vTotalPrecVtaProd =
                FarmaUtility.getDecimalNumber(((String)(array.get(7))).trim());
        VariablesVentas.vVal_Prec_Vta =
                FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd /
                                          Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                          3);
        VariablesVentas.vPorc_Dcto_1 =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(5))).trim()));
        System.err.println("***-VariablesVentas.vPorc_Dcto_1 " +
                           VariablesVentas.vCod_Prod + " - " +
                           VariablesVentas.vPorc_Dcto_1);
        if (tipo == ConstantsVentas.IND_PROD_PROM)
            VariablesVentas.vPorc_Dcto_2 =
                    String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(20))).trim()));
        else
            VariablesVentas.vPorc_Dcto_2 =
                    String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(COL_RES_DSCTO_2))).trim()));

        System.out.println("***-VariablesVentas.desc_2 " +
                           VariablesVentas.vPorc_Dcto_2);
        VariablesVentas.vPorc_Dcto_Total =
                VariablesVentas.vPorc_Dcto_1; //cuando es pedido normal, el descuento total siempre es el descuento 1
        VariablesVentas.vVal_Total_Bono =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(8))).trim()));
        VariablesVentas.vVal_Frac = ((String)(array.get(10))).trim();
        VariablesVentas.vEst_Ped_Vta_Det =
                ConstantsVentas.ESTADO_PEDIDO_DETALLE_ACTIVO;
        VariablesVentas.vSec_Usu_Local = FarmaVariables.vNuSecUsu;
        //VariablesVentas.vVal_Prec_Lista = String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(3))).trim()));
        VariablesVentas.vVal_Prec_Lista =
                String.valueOf(FarmaUtility.getDecimalNumber(((String)(array.get(6))).trim()));
        VariablesVentas.vVal_Igv_Prod = ((String)(array.get(11))).trim();
        VariablesVentas.vUnid_Vta = ((String)(array.get(2))).trim();
        VariablesVentas.vNumeroARecargar = ((String)(array.get(13))).trim();
        String secrespaldo = ""; //ASOSA, 05.07.2010

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
        System.out.println("WAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa: "+posSecRespaldo);
        secrespaldo =
                ((String)(array.get(posSecRespaldo))).trim(); //ASOSA, 05.07.2010

        //System.out.println("***-VariablesVentas.vVal_Prec_Pub "+VariablesVentas.vVal_Prec_Pub);
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
        System.out.println("Promo eeeee " + array);
        System.out.println("Promo al detalle : " +
                           VariablesVentas.vCodPromoDet);

        if (tipo == ConstantsVentas.IND_PROD_SIMPLE) {
            VariablesVentas.vIndOrigenProdVta =
                    array.get(COL_RES_ORIG_PROD).toString().trim();
            VariablesVentas.vCantxDia =
                    array.get(COL_RES_CANT_XDIA).toString().trim();
            VariablesVentas.vCantxDias =
                    array.get(COL_RES_CANT_DIAS).toString().trim();
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
        System.out.println("VariablesVentas.vCod_Prod:"+ VariablesVentas.vCod_Prod);
        System.out.println("VariablesVentas.tipo:"+ tipo);


        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.length() > 0)
	     {
	        //JCHAVEZ 20102009
        	if (tipo == ConstantsVentas.IND_PROD_SIMPLE)
        	{

		        DBVentas.grabarDetallePedido_02(secrespaldo);
		        VariablesVentas.vCodPromoDet = "";
        	}
	     }
        else
         {
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
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size();
             i++) {
            codProd =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 0);
            VariablesVentas.vVal_Frac =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 10);
            cantidad =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 4);
            indControlStk =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i, 16);
            secRespaldo =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido,
                                                        i,
                                                        26); //ASOSA, 02.07.2010
            VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
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
                    0, ConstantsVentas.INDICADOR_D,
                    ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, true,
                    this, tblProductos, secRespaldo))
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
        cantidad =
                ""; //((String)(tblProductos.getValueAt(filaActual,4))).trim();
        indControlStk =
                ""; // ((String)(tblProductos.getValueAt(filaActual,16))).trim();
        for (int i = 0; i < VariablesVentas.vArrayList_Promociones.size();
             i++) {
            agrupado = new ArrayList();
            aux = (ArrayList)(VariablesVentas.vArrayList_Promociones.get(i));
            codProm = ((String)(aux.get(0))).trim();
            agrupado =
                    detalle_Prom(VariablesVentas.vArrayList_Prod_Promociones,
                                 codProm);
            System.out.println("AAAAAAAAAAPRODSDSDSDSDSDS: " + agrupado);
            //agrupado=agrupar(agrupado);
            for (int j = 0; j < agrupado.size();
                 j++) //VariablesVentas.vArrayList_Prod_Promociones.size(); j++)
            {
                aux =
(ArrayList)(agrupado.get(j)); //VariablesVentas.vArrayList_Prod_Promociones.get(j));
                //if((((String)(aux.get(18))).trim()).equalsIgnoreCase(codProm)){
                codProd = ((String)(aux.get(0))).trim();
                cantidad = ((String)(aux.get(4))).trim();
                VariablesVentas.vVal_Frac = ((String)(aux.get(10))).trim();
                indControlStk = ((String)(aux.get(16))).trim();
                secRespaldo =
                        ((String)(aux.get(24))).trim(); //ASOSA, 08.07.2010
                VariablesVentas.secRespStk=""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(codProd,Integer.parseInt(cantidad),ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad), //Antes, ASOSA, 08.07.2010
                                                         false,
                                                         this,
                                                         tblProductos))*/
                    !UtilityVentas.operaStkCompProdResp(codProd,
                                                        //ASOSA, 08.07.2010
                        0, ConstantsVentas.INDICADOR_D,
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0,
                        false, this, tblProductos, secRespaldo))
                    return;
                //}
            }

        }
        FarmaUtility.aceptarTransaccion();
        inicializaArrayList();
        //jcallo: el parametro estaba en false--> se cambio a true
        cerrarVentana(true);
    }

    /**
     * Grabar el pedidio convenio generado
     * @author ASOSA
     * @since 17.08.2010
     * @param pCodCli
     * @param pNumDocIden
     * @param pCodTrabEmp
     * @param pApePat
     * @param pApeMat
     * @param pFecNac
     * @param pCodSol
     * @param pNumTel
     * @param pDirecCli
     * @param pNomDist
     * @param pCodInterno
     * @param pNomTrabajador
     * @param pCodCliDep
     * @param pCodTrabEmpDep
     * @param pCodAutori
     */
 /*   private void grabarPedidoConvenio_02(String pCodCli, String pNumDocIden,
                                      String pCodTrabEmp, String pApePat,
                                      String pApeMat, String pFecNac,
                                      String pCodSol, String pNumTel,
                                      String pDirecCli, String pNomDist,
                                      String pCodInterno,
                                      String pNomTrabajador, String pCodCliDep,
                                      String pCodTrabEmpDep, String pCodAutori) {
        try {
            DBConvenio.grabarPedidoConvenio_02(VariablesVentas.vNum_Ped_Vta,
                                            VariablesConvenio.vCodConvenio,
                                            pCodCli, pNumDocIden, pCodTrabEmp,
                                            pApePat, pApeMat, pFecNac, pCodSol,
                                            VariablesConvenio.vPorcDctoConv,
                                            VariablesConvenio.vPorcCoPago,
                                            pNumTel, pDirecCli, pNomDist,
                                            VariablesConvenio.vValCoPago,
                                            pCodInterno, pNomTrabajador,
                                            pCodCliDep, pCodTrabEmpDep,pCodAutori);
            System.out.println("Grabar Pedido Convenio");
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al grabar el la informacion del pedido y el convenio: \n" +
                    sql, null);
            //FarmaUtility.moveFocusJTable(tblProductos);
            FarmaUtility.moveFocus(txtDescProdOculto);
        }
    }
    */



 private boolean cargaLogin_verifica() {
     VariablesVentas.vListaProdFaltaCero = new ArrayList();
     VariablesVentas.vListaProdFaltaCero.clear();
boolean band = false;
     //limpiando variables de fidelizacion
     //UtilityFidelizacion.setVariables();

     //JCORTEZ 04.08.09 Se limpiar cupones.
     VariablesVentas.vArrayListCuponesCliente.clear();
     VariablesVentas.dniListCupon = "";

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("-------------------------Login para para cobros(DlgLogin)------------------");
        System.out.println("---------------------------------------------------------------------------");


     DlgLogin dlgLogin =
         new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
     dlgLogin.setRolUsuario(FarmaConstants.ROL_VENDEDOR);
     dlgLogin.setVisible(true);
     if (FarmaVariables.vAceptar) {

            //Agregado por FRAMIREZ 09/11/2011
            //Muestra mensaje de retencion de un convenio.
            System.out.println("<<<<<<<<Ingresando al mensaje de Retencion>>>>>>>>>");
            System.out.println("vCodConvenio :" +
                               VariablesConvenioBTLMF.vCodConvenio);

            if (VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
                mostrarMensajeRetencion();
            }

         log.info("******* JCORTEZ *********");
         if (UtilityCaja.existeIpImpresora(this, null)) {
             if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) &&
                 !UtilityCaja.existeCajaUsuarioImpresora(this, null)) {
                 //linea agrega pàra corregir el error al validar los roles de los usuarios
                 FarmaVariables.dlgLogin = dlgLogin;
                 System.out.println("");
                 VariablesCaja.vVerificaCajero = false;
                 //FarmaUtility.showMessage(this,"No tiene Asociada Caja esa Impresora",txtDescProdOculto);
                 //cerrarVentana(false);
             } else {
                 FarmaVariables.dlgLogin = dlgLogin;
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
             FarmaVariables.dlgLogin = dlgLogin;
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


        UtilityConvenioBTLMF.listaMensaje(htmlDerecho,
                                          VariablesConvenioBTLMF.vCodConvenio,
                                          ConstantsConvenioBTLMF.FLG_DOC_RETENCION,
                                          this, null);

        System.out.println("Tamaño:" + htmlDerecho);
        if (htmlDerecho.size() != 0) {
            DlgMensajeRetencion dlg =
                new DlgMensajeRetencion(myParentFrame, "", false);
            dlg.setVisible(true);
        }

    }

    public void funcionF12(String pCodCampanaCupon) {
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

        AuxiliarFidelizacion.funcionF12(pCodCampanaCupon,txtDescProdOculto,this.myParentFrame,lblDNI_Anul,lblCliente,this,"R",lblDNI_SIN_COMISION);
        neoOperaResumenPedido();
        FarmaUtility.moveFocus(txtDescProdOculto);
        VariablesFidelizacion.tmpCodCampanaCupon = "N";
        //Inicio - dubilluz 15.06.2011
        evaluaFormaPagoFidelizado();
        //Fin - dubilluz 15.06.2011
    }

    public void evaluaFormaPagoFidelizado(){

        if(VariablesFidelizacion.vDniCliente.trim().length()>=1){
            //Inicio - dubilluz 15.06.2011
            lblFormaPago.setVisible(false);
            lblFormaPago.setOpaque(false);
            if(VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S")||
               VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S")
                )
            if(!VariablesFidelizacion.vNamePagoTarjeta.trim().equalsIgnoreCase("N")){
                lblFormaPago.setVisible(true);
                lblFormaPago.setBackground(Color.white);
                lblFormaPago.setOpaque(true);
                 if(VariablesFidelizacion.vCodFPagoTarjeta.trim().equalsIgnoreCase("T0000")){
                    lblFormaPago.setText(" Pago con Todas las Tarjetas");
                }
                else{
                lblFormaPago.setText(" Pago con "+VariablesFidelizacion.vNamePagoTarjeta);
                }
                lblFormaPago.setText("  "+lblFormaPago.getText().trim().toUpperCase());
            }
            //Fin - dubilluz 15.06.2011
        }
    }

    public boolean isFormaPagoUso_x_Cupon(String codCampCupon){
        String valor = "N";
        try {
            valor =
                    DBFidelizacion.isValidaFormaPagoUso_x_Campana(codCampCupon).trim();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(valor.trim().equalsIgnoreCase("S"))
            return true;

        return false;
    }

    private void validaIngresoTarjetaPagoCampanaAutomatica(String nroTarjetaFormaPago) {
             if (isNumerico(nroTarjetaFormaPago)) {
                 Map mapCupon;
                 boolean obligarIngresarFP =  false;
                 boolean yaIngresoFormaPago = false;

                 VariablesFidelizacion.tmp_NumTarjeta_unica_Campana = nroTarjetaFormaPago;

                 //verifica si la tarjeta ya esta asociada a un DNI
                 String pExisteAsociado = UtilityFidelizacion.existeDniAsociado(nroTarjetaFormaPago);

                 if(pExisteAsociado.trim().equalsIgnoreCase("S")){
                     //VALIDA EL CLIENTE POR TARJETA 12.01.2011
                     String cadena = nroTarjetaFormaPago;
                     validarClienteTarjeta(cadena);
                     //VariablesFidelizacion.vNumTarjeta = cadena.trim();
                     if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                         0) {
                         System.out.println("RRRR");
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
                                 UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
                         // 01.06.2012 dubilluz
                         log.info("Variable de DNI_ANULADO: " +
                                  VariablesFidelizacion.vDNI_Anulado);
                         log.info("Variable de vAhorroDNI_x_Periodo: " +
                                  VariablesFidelizacion.vAhorroDNI_x_Periodo);
                         log.info("Variable de vMaximoAhorroDNIxPeriodo: " +
                                  VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);

                         AuxiliarFidelizacion.setMensajeDNIFidelizado(lblDNI_Anul,"R",txtDescProdOculto,this);
                     }
                 }
                 else
                 {
                     if(VariablesFidelizacion.vDniCliente.trim().length()==0){
                        funcionF12("N");
                         yaIngresoFormaPago = true;
                     }


                    }
                 }
                 //cargar las campañas de tipo automatica
                 String cadenaTarjeta = UtilityFidelizacion.getDatosTarjetaFormaPago(nroTarjetaFormaPago.trim());
                 String[] datos = cadenaTarjeta.split("@");
                 if(datos.length==2){
                     VariablesFidelizacion.vIndUsoEfectivo  = "N";
                     VariablesFidelizacion.vIndUsoTarjeta   = "S";
                     VariablesFidelizacion.vCodFPagoTarjeta = datos[0].toString().trim();
                     VariablesFidelizacion.vNamePagoTarjeta = datos[1].toString().trim();

                     //if(VariablesFidelizacion.vDNI_Anulado)
                     //{
                         if(VariablesFidelizacion.vNumTarjeta.trim().length()>0)
                            UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                         VariablesFidelizacion.vNumTarjetaCreditoDebito_Campana = nroTarjetaFormaPago.trim();
                     //}
                     evaluaFormaPagoFidelizado();
                     txtDescProdOculto.setText("");
             }

             neoOperaResumenPedido();
    }


    public boolean isNumerico(String pCadena){
        int numero = 0;
        boolean pRes = false;
        try {
            for(int i=0;i<pCadena.length();i++){
                numero = Integer.parseInt(pCadena.charAt(i)+"");
                pRes = true;
            }
        } catch (NumberFormatException e) {
            pRes = false;
        }
        return pRes;
    }

    public void setFormatoTarjetaCredito(String pCadena){
        String pCadenaNueva =  UtilityFidelizacion.pIsTarjetaVisaRetornaNumero(pCadena).trim();
        if(!pCadenaNueva.trim().equalsIgnoreCase("N")){
            System.out.println("Es tarjeta");
            txtDescProdOculto.setText(pCadenaNueva.trim());
            pasoTarjeta = true;
        }
        else{
            System.out.println("NO ES tarjeta");
            pasoTarjeta = false;
        }

    }

    //Dubilluz - 06.12.2011

    public void ingresaMedicoFidelizado() {
        AuxiliarFidelizacion.ingresoMedico(this.myParentFrame,lblMedico,lblDNI_Anul,lblCliente,this,"R",lblDNI_SIN_COMISION,txtDescProdOculto);
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
            System.out.println("pIngresoMedido:" + pIngresoMedido);
            if (pIngresoMedido.trim().length() > 0){
                System.out.println("valida si existe el medico");
                String pExisteMedico =
                    UtilityFidelizacion.getExisteMedido(this.myParentFrame,pIngresoMedido.trim());
                System.out.println("pExisteMedico:" + pExisteMedico);

                if (pExisteMedico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                        0) {
                        System.out.println(">>> ya existe DNI ingresado");
                    } else {
                        System.out.println(">>> NO EXISTE DNI ingresado");
                        funcionF12("N");
                    }

                    System.out.println(">>>VariablesFidelizacion.vNumTarjeta.trim().length()+"+VariablesFidelizacion.vNumTarjeta.trim().length());
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                        0) {
                        System.out.println(">>> BUSCA campañas para agregar las q tiene asociado ese tipo de colegio");
                        UtilityFidelizacion.agregaCampanaMedicoAuto(VariablesFidelizacion.vNumTarjeta,
                                                                    VariablesFidelizacion.V_TIPO_COLEGIO.trim(),
                                                                    VariablesFidelizacion.V_OLD_TIPO_COLEGIO);
                        //VariablesFidelizacion.vColegioMedico = pIngresoMedido.trim();
                        ///////////////////////////////////////////////
                        VariablesFidelizacion.vColegioMedico = VariablesFidelizacion.V_NUM_CMP;
                        ///////////////////////////////////////////////
                        System.out.println(">>> agrego campna..");
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
   //Agregado Por FRAMIREZ para convenio BTLMF
    public boolean existeSaldoCredDispBenif(JDialog dialog)
    {
        boolean ret = true;
        if(VariablesConvenioBTLMF.vImpSubTotal > FarmaUtility.getDecimalNumber(VariablesConvenioBTLMF.vMontoSaldo))
        {

            FarmaUtility.showMessage(dialog, "El importe " + VariablesConvenioBTLMF.vImpSubTotal+ " supera el saldo de credito del Benificiario!!",
                                     "");
            ret = false;
        }

        return ret;
    }


    public String getMensajeComprobanteConvenio(String pCodConvenio){
        String pCadena = "";
        try {
            pCadena = DBConvenioBTLMF.getMsgComprobante(pCodConvenio,VariablesConvenioBTLMF.vImpSubTotal);
            System.out.println("XXXX");
        } catch (SQLException e) {
            pCadena = "N";
            System.out.println("yyy");
            e.printStackTrace();
        }
        return pCadena;
    }

    private void txtDescProdOculto_actionPerformed(ActionEvent e) {
    }
}
