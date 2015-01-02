package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.hilos.SubProcesosConvenios;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgComplementarios1.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ       08.04.2008   Creacion <br>
 * <br>
 * @author  Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */

public class DlgComplementarios1 extends JDialog {
    /* ************************************************************************** */
    /*                           DECLARACION PROPIEDADES                          */
    /* ************************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgComplementarios1.class);

    private Frame myParentFrame;

    private FarmaTableModel tableModelListaComplementarios;

    private String descUnidPres = "";
    private String stkProd = "";
    private String valPrecPres = "";
    private String valFracProd = "";
    private String indProdCong = "";
    private String valPrecLista = "";
    private String valPrecVta = "";
    private String descUnidVta = "";
    private String indProdHabilVta = "";
    private String porcDscto_1 = "";

    private String indProdProm = "";
    //JCORTEZ 11/04/08 descuento de oferta
    private String porcDscto_2 = "";
    private String valPrecVtaReal = "";
    private String valAhorro = "";

    //JCORTEZ 15/04/08 descripcion Padre
    private String DescProdPadre = "";
    private String LabProdPadre = "";
    private String DescMotivo = "";

    //Mfajardo 24/04/09
    private boolean vEjecutaAccionTeclaListado = false;

    private String tipoProd = "";
    private String bonoProd = "";

    /**
     * Indicadores de stock en adicional en fraccion del local.
     * @author Edgar Rios Navarro
     * @since 03.06.2008
     */
    private String stkFracLoc = "";
    private String descUnidFracLoc = "";

    /**
     * Si se efectuo algun cambio en pedido venta (seleccion o deseleccion de producto).
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    private boolean vSeleccionProductoAlter;

    /**
     * Columnas de la grilla
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    /* Lista productos alternativos */
    private final int COL_COD = 1;
    private final int COL_DESC = 2;
    private final int COL_UND_VTA = 3;
    private final int COL_LAB = 4;
    private final int COL_STOCK = 5;
    private final int COL_PREC_VTA = 6;
    private final int COL_BONO = 7;
    private final int COL_PREC_LISTA = 10;
    private final int COL_IND_IGV = 11;
    private final int COL_IND_FARMA = 12;
    private final int COL_IND_VIRT = 13;
    private final int COL_TIPO_VIRT = 14;
    private final int COL_IND_REFRIG = 15;
    private final int COL_TIPO_PROD = 16;
    private final int COL_IND_PROM = 17;
    private final int COL_ORD_LISTA = 18;
    private final int COL_IND_ENCARTE = 19;
    private final int COL_ORIG_PROD = 20;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel JPanel3 = new JPanel();
    private JLabel lblPrecio1 = new JLabel();
    private JLabel lblPrecio_T = new JLabel();
    private JLabel lblUnidad = new JLabel();
    private JLabel lblUnidad_T = new JLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel1 = new JPanel();
    private JLabel lblDescLab_Prod = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JPanel pnlIngresarProductos = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextField txtProducto = new JTextField();
    private JButton btnProducto = new JButton();
    private JTable tblProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabel lblProdIgv = new JLabel();
    private JPanel jPanel5 = new JPanel();
    private JLabel lblProdRefrig = new JLabel();
    private JLabel lblProdCong = new JLabel();
    private JLabelWhite lblIndTipoProd = new JLabelWhite();
    private JLabel lblProdProm = new JLabel();
    private JLabel lblProdEncarte = new JLabel();
    private JLabelWhite lblMensaje = new JLabelWhite();
    private JPanel pnlAtencion2 = new JPanel();
    private XYLayout xYLayout8 = new XYLayout();
    private JLabel lblUltimoPedido2 = new JLabel();
    private JLabel lblTipoCambio2 = new JLabel();
    private JLabel lblPrecioDesc = new JLabel();
    private JLabel lblPrecio2 = new JLabel();
    private JLabel lblPrecioAhorro = new JLabel();
    private JLabel lblAhorro = new JLabel();
    private JPanelHeader JPanel4 = new JPanelHeader();
    private JLabel lbl1 = new JLabel();
    private JLabel lbl2 = new JLabel();
    private JLabel lbl3 = new JLabel();
    private JLabel lblDesc = new JLabel();
    private JLabel lblLab = new JLabel();
    private JPanel pnlAtencion3 = new JPanel();
    private XYLayout xYLayout9 = new XYLayout();
    private JLabel lblUltimoPedido3 = new JLabel();
    private JLabel lblTipoCambio3 = new JLabel();
    private JLabelWhite lblmensaje2 = new JLabelWhite();
    private JLabel lblUnidad_T1 = new JLabel();
    private JLabel lblUnidad1 = new JLabel();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextArea txtDescMotivo = new JTextArea();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelWhite lblStockAdic_T = new JLabelWhite();
    private JLabelWhite lblStockAdic = new JLabelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblUnidFracLoc = new JLabelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();


    /* ************************************************************************** */
    /*                              CONSTRUCTORES                                 */
    /* ************************************************************************** */

    public DlgComplementarios1() {
        this(null, "", false);
    }

    public DlgComplementarios1(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************** */
    /*                              METODO jbInit                                 */
    /* ************************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(737, 442));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 439));
        jContentPane.setForeground(Color.white);
        JPanel3.setBounds(new Rectangle(10, 50, 705, 65));
        JPanel3.setBackground(new Color(43, 141, 39));
        JPanel3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        JPanel3.setLayout(null);
        lblPrecio1.setBounds(new Rectangle(130, 5, 100, 15));
        lblPrecio1.setFont(new Font("SansSerif", 1, 15));
        lblPrecio1.setForeground(Color.white);
        lblPrecio_T.setText("Precio Normal : S/.");
        lblPrecio_T.setBounds(new Rectangle(5, 5, 120, 15));
        lblPrecio_T.setFont(new Font("SansSerif", 1, 13));
        lblPrecio_T.setForeground(Color.white);
        lblUnidad.setBounds(new Rectangle(410, 35, 265, 15));
        lblUnidad.setFont(new Font("SansSerif", 1, 11));
        lblUnidad.setForeground(Color.white);
        lblUnidad_T.setText("Unidad :");
        lblUnidad_T.setBounds(new Rectangle(355, 35, 60, 15));
        lblUnidad_T.setFont(new Font("SansSerif", 1, 11));
        lblUnidad_T.setForeground(Color.white);
        lblF1.setText("[ F1 ] / + Generar Pedido");
        lblF1.setBounds(new Rectangle(160, 385, 150, 20));
        jScrollPane1.setBounds(new Rectangle(10, 140, 705, 180));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(10, 120, 705, 20));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        lblDescLab_Prod.setBounds(new Rectangle(160, 0, 345, 20));
        lblDescLab_Prod.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Prod.setForeground(Color.white);
        jSeparator1.setBounds(new Rectangle(150, 0, 15, 20));
        jSeparator1.setBackground(Color.black);
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        pnlIngresarProductos.setBounds(new Rectangle(10, 15, 705, 30));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlIngresarProductos.setBackground(new Color(43, 141, 39));
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(585, 5, 90, 20));
        btnBuscar.setBackground(SystemColor.control);
        btnBuscar.setMnemonic('b');
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtProducto.setBounds(new Rectangle(90, 5, 460, 20));
        txtProducto.setFont(new Font("SansSerif", 1, 11));
        txtProducto.setForeground(new Color(32, 105, 29));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);

            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtProducto_keyTyped(e);
            }
        });
        btnProducto.setText("Producto");
        btnProducto.setBounds(new Rectangle(10, 5, 60, 20));
        btnProducto.setMnemonic('p');
        btnProducto.setFont(new Font("SansSerif", 1, 11));
        btnProducto.setDefaultCapable(false);
        btnProducto.setRequestFocusEnabled(false);
        btnProducto.setBackground(new Color(50, 162, 65));
        btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnProducto.setFocusPainted(false);
        btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnProducto.setContentAreaFilled(false);
        btnProducto.setBorderPainted(false);
        btnProducto.setForeground(Color.white);
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        tblProductos.setFont(new Font("SansSerif", 0, 12));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(630, 385, 85, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(10, 385, 145, 20));
        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 20));
        lblProdIgv.setBounds(new Rectangle(155, 0, 110, 20));
        lblProdIgv.setFont(new Font("SansSerif", 1, 11));
        lblProdIgv.setText("INAFECTO IGV");
        lblProdIgv.setBackground(new Color(44, 146, 24));
        lblProdIgv.setOpaque(true);
        lblProdIgv.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdIgv.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdIgv.setForeground(Color.white);
        jPanel5.setBounds(new Rectangle(10, 320, 705, 20));
        jPanel5.setBackground(new Color(255, 130, 14));
        jPanel5.setLayout(null);
        lblProdRefrig.setBounds(new Rectangle(425, 0, 120, 20));
        lblProdRefrig.setVisible(true);
        lblProdRefrig.setFont(new Font("SansSerif", 1, 11));
        lblProdRefrig.setText("REFRIGERADO");
        lblProdRefrig.setBackground(new Color(44, 146, 24));
        lblProdRefrig.setOpaque(true);
        lblProdRefrig.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdRefrig.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdRefrig.setForeground(Color.white);
        lblProdCong.setBounds(new Rectangle(25, 0, 110, 20));
        lblProdCong.setVisible(true);
        lblProdCong.setFont(new Font("SansSerif", 1, 11));
        lblProdCong.setText("CONGELADO");
        lblProdCong.setBackground(new Color(44, 146, 24));
        lblProdCong.setOpaque(true);
        lblProdCong.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdCong.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdCong.setForeground(Color.white);
        lblIndTipoProd.setBounds(new Rectangle(545, 0, 140, 20));
        lblIndTipoProd.setFont(new Font("SansSerif", 1, 12));
        lblProdProm.setBounds(new Rectangle(285, 0, 120, 20));
        lblProdProm.setFont(new Font("SansSerif", 1, 11));
        lblProdProm.setText("PRODUCTO EN PACK");
        lblProdProm.setBackground(new Color(44, 146, 24));
        lblProdProm.setOpaque(true);
        lblProdProm.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdProm.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdProm.setForeground(Color.white);
        lblProdEncarte.setBounds(new Rectangle(565, 0, 120, 20));
        lblProdEncarte.setVisible(true);
        lblProdEncarte.setFont(new Font("SansSerif", 1, 11));
        lblProdEncarte.setText("EN ENCARTE");
        lblProdEncarte.setBackground(new Color(44, 146, 24));
        lblProdEncarte.setOpaque(true);
        lblProdEncarte.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdEncarte.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdEncarte.setForeground(Color.white);
        lblMensaje.setBounds(new Rectangle(365, 5, 330, 20));
        lblMensaje.setFont(new Font("SansSerif", 1, 20));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setForeground(new Color(27, 121, 53));
        pnlAtencion2.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion2.setLayout(xYLayout8);
        pnlAtencion2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion2.setBackground(new Color(255, 254, 254));
        pnlAtencion2.setBounds(new Rectangle(355, 0, 350, 30));
        lblUltimoPedido2.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedido2.setForeground(Color.white);
        lblTipoCambio2.setFont(new Font("SansSerif", 1, 11));
        lblTipoCambio2.setForeground(Color.white);
        lblPrecioDesc.setText("Precio Dscto: S/.");
        lblPrecioDesc.setBounds(new Rectangle(5, 25, 110, 15));
        lblPrecioDesc.setFont(new Font("SansSerif", 1, 13));
        lblPrecioDesc.setForeground(Color.white);
        lblPrecio2.setBounds(new Rectangle(110, 25, 85, 15));
        lblPrecio2.setFont(new Font("SansSerif", 1, 15));
        lblPrecio2.setForeground(Color.white);
        lblPrecioAhorro.setBounds(new Rectangle(280, 25, 75, 15));
        lblPrecioAhorro.setFont(new Font("SansSerif", 1, 15));
        lblPrecioAhorro.setForeground(Color.white);
        lblAhorro.setText("Ahorro : S/.");
        lblAhorro.setBounds(new Rectangle(205, 25, 75, 15));
        lblAhorro.setFont(new Font("SansSerif", 1, 13));
        lblAhorro.setForeground(Color.white);
        //JPanel4.setBounds(new Rectangle(5, 45, 705, 65));
        JPanel4.setBounds(new Rectangle(10, 50, 705, 65));
        lbl1.setText("Este producto es complementario de :");
        lbl1.setBounds(new Rectangle(5, 5, 345, 15));
        lbl1.setFont(new Font("SansSerif", 1, 11));
        lbl1.setForeground(Color.white);
        lbl2.setText("Descripcion :");
        lbl2.setBounds(new Rectangle(5, 25, 80, 15));
        lbl2.setFont(new Font("SansSerif", 1, 11));
        lbl2.setForeground(Color.white);
        lbl3.setText("Laboratorio :");
        lbl3.setBounds(new Rectangle(5, 45, 75, 15));
        lbl3.setFont(new Font("SansSerif", 1, 11));
        lbl3.setForeground(Color.white);
        lblDesc.setBounds(new Rectangle(90, 25, 260, 15));
        lblDesc.setFont(new Font("SansSerif", 1, 15));
        lblDesc.setForeground(Color.white);
        lblLab.setBounds(new Rectangle(85, 45, 260, 15));
        lblLab.setFont(new Font("SansSerif", 1, 15));
        lblLab.setForeground(Color.white);
        pnlAtencion3.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion3.setLayout(xYLayout9);
        pnlAtencion3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion3.setBackground(new Color(255, 254, 254));
        pnlAtencion3.setBounds(new Rectangle(355, 0, 350, 30));
        lblUltimoPedido3.setFont(new Font("SansSerif", 0, 11));
        lblUltimoPedido3.setForeground(Color.white);
        lblTipoCambio3.setFont(new Font("SansSerif", 1, 11));
        lblTipoCambio3.setForeground(Color.white);
        lblmensaje2.setBounds(new Rectangle(365, 5, 330, 20));
        lblmensaje2.setFont(new Font("SansSerif", 1, 20));
        lblmensaje2.setHorizontalAlignment(SwingConstants.CENTER);
        lblmensaje2.setForeground(new Color(27, 121, 53));
        lblUnidad_T1.setText("Unidad :");
        lblUnidad_T1.setBounds(new Rectangle(360, 30, 60, 15));
        lblUnidad_T1.setFont(new Font("SansSerif", 1, 11));
        lblUnidad_T1.setForeground(Color.white);
        lblUnidad1.setBounds(new Rectangle(415, 30, 265, 15));
        lblUnidad1.setFont(new Font("SansSerif", 1, 11));
        lblUnidad1.setForeground(Color.white);
        jScrollPane2.setBounds(new Rectangle(10, 340, 705, 35));
        lblF8.setBounds(new Rectangle(315, 385, 117, 20));
        lblF8.setText("[ F8 ] Info Prod");
        lblStockAdic_T.setText("Stock Adic.:");
        lblStockAdic_T.setBounds(new Rectangle(5, 0, 65, 20));
        lblStockAdic_T.setForeground(new Color(43, 141, 39));
        lblStockAdic.setBounds(new Rectangle(70, 0, 40, 20));
        lblStockAdic.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStockAdic.setForeground(new Color(43, 141, 39));
        lblStockAdic.setSize(new Dimension(40, 15));
        lblUnidFracLoc.setBounds(new Rectangle(120, 0, 105, 20));
        lblUnidFracLoc.setForeground(new Color(43, 141, 39));
        jPanelWhite1.setBounds(new Rectangle(355, 45, 350, 20));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion3.add(lblUltimoPedido3, new XYConstraints(655, 10, 40, 15));
        pnlAtencion3.add(lblTipoCambio3, new XYConstraints(205, 10, 40, 15));
        pnlAtencion2.add(lblUltimoPedido2, new XYConstraints(655, 10, 40, 15));
        pnlAtencion2.add(lblTipoCambio2, new XYConstraints(205, 10, 40, 15));
        JPanel3.add(lblAhorro, null);
        JPanel3.add(lblPrecioAhorro, null);
        JPanel3.add(lblPrecio2, null);
        JPanel3.add(lblPrecioDesc, null);
        JPanel3.add(lblMensaje, null);
        JPanel3.add(pnlAtencion2, null);
        JPanel3.add(lblPrecio1, null);
        JPanel3.add(lblPrecio_T, null);
        JPanel3.add(lblUnidad, null);
        JPanel3.add(lblUnidad_T, null);
        jScrollPane1.getViewport();
        jPanel1.add(lblIndTipoProd, null);
        jPanel1.add(btnRelacionProductos, null);
        jPanel1.add(lblDescLab_Prod, null);
        jPanel1.add(jSeparator1, null);
        pnlIngresarProductos.add(btnBuscar, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        jPanel5.add(lblProdProm, null);
        jPanel5.add(lblProdCong, null);
        jPanel5.add(lblProdRefrig, null);
        jPanel5.add(lblProdIgv, null);
        jPanel5.add(lblProdEncarte, null);
        JPanel4.add(lblUnidad1, null);
        JPanel4.add(lblUnidad_T1, null);
        jPanelWhite1.add(lblStockAdic_T, null);
        jPanelWhite1.add(lblStockAdic, null);
        jPanelWhite1.add(lblUnidFracLoc, null);
        JPanel4.add(lblmensaje2, null);
        JPanel4.add(pnlAtencion3, null);
        JPanel4.add(lblLab, null);
        JPanel4.add(lblDesc, null);
        JPanel4.add(lbl3, null);
        JPanel4.add(lbl2, null);
        JPanel4.add(lbl1, null);
        JPanel4.add(jPanelWhite1, null);
        jScrollPane2.getViewport().add(txtDescMotivo, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(jPanel5, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(JPanel3, null);
        jContentPane.add(lblF1, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlIngresarProductos, null);
        jContentPane.add(JPanel4, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************** */
    /*                            METODO initialize                               */
    /* ************************************************************************** */

    private void initialize() {
        initTableListaComplementarios();
        iniciaProceso(true);
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************** */
    /*                          METODOS INICIALIZACION                            */
    /* ************************************************************************** */

    private void initTableListaComplementarios() {
        tableModelListaComplementarios =
                new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos,
                                    0);
        FarmaUtility.initSelectList(tblProductos, tableModelListaComplementarios,
                                    ConstantsVentas.columnsListaProductos);
        listaProductosComplementarios();
    }

    private void listaProductosComplementarios() {
        try {
            // FarmaVariables.vIPBD
            String titulo;
            titulo = "Lista de Productos Complementarios";
            lblMensaje.setVisible(false);

            //VariablesVentas.vEsProdOferta=false;
            //JCORTEZ 25/04/08
            if (!VariablesVentas.vEsProdOferta) {
                DBVentas.cargaListaProductosComplementarios1(tableModelListaComplementarios,
                                                             VariablesVentas.vCodProdOrigen_Comple1);
            }

            if (tblProductos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblProductos, tableModelListaComplementarios, COL_ORD_LISTA,
                                     FarmaConstants.ORDEN_DESCENDENTE);
                JPanel3.setVisible(false);
                JPanel4.setVisible(true);
                lblMensaje.setText("VENTA COMPLEMENTARIA");
                lblmensaje2.setText("VENTA COMPLEMENTARIA");
            } else if (tblProductos.getRowCount() == 0) {
                VariablesVentas.vEsProdOferta = true;
                VariablesVentas.vVentanaOferta = true;
                DBVentas.cargaListaProductosOferta(tableModelListaComplementarios,
                                                   VariablesVentas.vCodProdOrigen_Comple1);
                titulo = "Lista de Productos Oferta";
                JPanel4.setVisible(false);
                JPanel3.setVisible(true);
                lblMensaje.setText("PRODUCTOS EN OFERTA");
                lblmensaje2.setText("PRODUCTOS EN OFERTA");
            }

            if (tblProductos.getRowCount() > 0) {
                this.setTitle(titulo);
                lblMensaje.setVisible(true);
                FarmaUtility.setearPrimerRegistro(tblProductos, txtProducto, COL_DESC);
            }
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(this, "Error al Listar Productos Complementarios.\n" +
                    sqlException, txtProducto);
        }
    }

    public void iniciaProceso(boolean pInicializar) {
        if (pInicializar) {
            VariablesVentas.vArrayList_PedidoVenta = new ArrayList();
            for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++)
                VariablesVentas.vArrayList_PedidoVenta.add((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
        }

        FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_PedidoVenta, 0);
        FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_Prod_Promociones, 0);
        FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_Prod_Promociones_temporal, 0);
        //if ( !pInicializar )
        muestraIndicadoresProducto();

    }

    /* ************************************************************************** */
    /*                            METODOS DE EVENTOS                              */
    /* ************************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        lblProdIgv.setVisible(false);
        lblProdProm.setVisible(false);
        lblProdEncarte.setVisible(false);
        lblProdCong.setVisible(false);
        lblProdRefrig.setVisible(false);
        FarmaUtility.moveFocus(txtProducto);
        //lblF1.setVisible(false);
        log.debug("Productos pedido : " + VariablesVentas.vArrayList_PedidoVenta);

        if (VariablesVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesVentas.vIndPedConProdVirtual = false;

        if (tblProductos.getRowCount() == 0) {
            cerrarVentana(false);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);

    }

    // **************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - evitar le ejecucion de 2 teclas a la vez
    // **************************************************************************

    private void txtProducto_keyPressed(KeyEvent e) {

        try {

            FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos, txtProducto, COL_DESC);

            if (!vEjecutaAccionTeclaListado) {
                vEjecutaAccionTeclaListado = true;

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //ERIOS 03.07.2013 Limpia la caja de texto
                    limpiaCadenaAlfanumerica(txtProducto);
                    try {
                        e.consume();
                        if (tblProductos.getSelectedRow() >= 0) {
                            String productoBuscar = txtProducto.getText().trim().toUpperCase();

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
                                VariablesVentas.vCodigoBarra = productoBuscar;
                                productoBuscar = DBVentas.obtieneCodigoProductoBarra();
                            }
                            if (productoBuscar.equalsIgnoreCase("000000")) {
                                FarmaUtility.showMessage(this,
                                                         "No existe producto relacionado con el Codigo de Barra. Verifique!!!",
                                                         txtProducto);
                                return;
                            }
                            for (int k = 0; k < tblProductos.getRowCount(); k++) {
                                codigo = ((String)tblProductos.getValueAt(k, COL_COD)).trim();
                                if (codigo.equalsIgnoreCase(productoBuscar)) {
                                    FarmaGridUtils.showCell(tblProductos, k, 0);
                                    break;
                                }
                            }
                            String actualCodigo =
                                ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), COL_COD))).trim();
                            String actualProducto =
                                ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), COL_DESC))).trim().toUpperCase();
                            // Asumimos que codigo de producto ni codigo de barra empiezan con letra
                            if (Character.isLetter(primerkeyChar)) {
                                txtProducto.setText(actualProducto);
                                productoBuscar = actualProducto;
                            }
                            txtProducto.setText(txtProducto.getText().trim());
                            // Comparando codigo y descripcion de la fila actual con el txtProducto
                            if ((actualCodigo.equalsIgnoreCase(productoBuscar) ||
                                 actualProducto.substring(0, productoBuscar.length()).equalsIgnoreCase(productoBuscar))) {
                                btnBuscar.doClick();
                                txtProducto.setText(actualProducto.trim());
                                txtProducto.selectAll();
                            } else {
                                FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                         txtProducto);
                                return;
                            }
                        }
                    } catch (SQLException sql) {
                        log.error("", sql);
                        FarmaUtility.showMessage(this, "Error al buscar el Producto.\n" +
                                sql, txtProducto);
                    }
                } else {
                    vEjecutaAccionTeclaListado = false;
                    chkKeyPressed(e);
                }
            }
        } catch (Exception exc) {
            log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            vEjecutaAccionTeclaListado = false;
        }

    }

    private void txtProducto_keyReleased(KeyEvent e) {
        if (tblProductos.getRowCount() >= 0 && tableModelListaComplementarios.getRowCount() > 0) {
            FarmaGridUtils.buscarDescripcion(e, tblProductos, txtProducto, COL_DESC);
            muestraIndicadoresProducto();
        }
    }

    private void txtProducto_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '-') {
            e.consume();
            String lblStock = lblStockAdic.getText().trim();
            if (!lblStock.equals("")) {
                int vFila = tblProductos.getSelectedRow();
                int auxStk = FarmaUtility.trunc(FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_STOCK));
                int auxStkFrac = FarmaUtility.trunc(lblStock);

                if ((auxStk + auxStkFrac) > 0) {
                    if (validaVentaConMenos()) {
                        mostrarTratamiento();
                        //aceptaOperacion();
                    }
                }
            }
        }
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        verificaCheckJTable();
    }

    /* ************************************************************************** */
    /*                       METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) || e.getKeyChar() == '+') {
            if (vSeleccionProductoAlter) {
                cerrarVentana(true);
            } else {
                cerrarVentana(false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            muestraDetalleProducto();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesVentas.vVentanaOferta = false;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************** */
    /*                       METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************** */

    private void muestraNombreLab(int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String nombreLab = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            pLabel.setText(nombreLab);
        }
    }

    private void muestraProductoInafectoIgv(int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String inafectoIgv = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (FarmaUtility.getDecimalNumber(inafectoIgv) == 0.00)
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }


    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        int vFila = tblProductos.getSelectedRow();
        String codProd = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
        //JMIRANDA 22/09/2009 lleno la variable vCod_Prod
        VariablesVentas.vCod_Prod = codProd;
        try {
            if (!VariablesVentas.vEsProdOferta) {
                log.debug("BUSCA PRODUCTOS INFO");
                //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
                if (VariablesVentas.vEsPedidoConvenio) {
                    DBVentas.obtieneInfoProducto(pArrayList, codProd);
                } else {
                    DBVentas.obtieneInfoProductoVta(pArrayList, codProd);
                }
            } else {
                log.debug("BUSCA PRODUCTOS INFOR OFERTA ");
                DBVentas.obtieneInfoOfertProducto(pArrayList, codProd);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    private void muestraInfoProd() {
        if (tblProductos.getRowCount() <= 0)
            return;

        ArrayList myArray = new ArrayList();
        ArrayList myArray2 = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        log.debug("VariablesVentas.vEsProdOferta : " + VariablesVentas.vEsProdOferta);

        if (!VariablesVentas.vEsProdOferta) {
            obtieneInfoProdPadreComple(myArray2);

            log.debug("obtieneInfoProdPadreComple : " + myArray2);

            if (myArray2.size() == 1) {
                DescProdPadre =
                        ((String)((ArrayList)myArray2.get(0)).get(1)).trim() + " - " + ((String)((ArrayList)myArray2.get(0)).get(2)).trim();
                LabProdPadre = ((String)((ArrayList)myArray2.get(0)).get(3)).trim();
                DescMotivo = ((String)((ArrayList)myArray2.get(0)).get(4)).trim();
            } else {
                DescProdPadre = "";
                LabProdPadre = "";
                FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto Padre", txtProducto);
            }
        }

        //log.debug("Tamaño en muestra info" + myArray.size());
        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            valPrecPres = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            indProdCong = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            valPrecVta = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            descUnidVta = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            indProdHabilVta = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
            porcDscto_1 = ((String)((ArrayList)myArray.get(0)).get(8)).trim();
            tipoProd = ((String)((ArrayList)myArray.get(0)).get(9)).trim();
            muestraIndTipoProd(COL_TIPO_PROD, lblIndTipoProd, tipoProd);
            bonoProd = ((String)((ArrayList)myArray.get(0)).get(10)).trim();


            //JCORTEZ PORC_DSCT OFERTA SOLO SI ES DE ORIGEN OFERTA
            if (VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) {
                porcDscto_2 = ((String)((ArrayList)myArray.get(0)).get(11)).trim();
            } else {
                porcDscto_2 = "";
            }

            if (VariablesVentas.vEsProdOferta) {
                valPrecVtaReal = ((String)((ArrayList)myArray.get(0)).get(12)).trim();
                valAhorro = ((String)((ArrayList)myArray.get(0)).get(13)).trim();
                lblPrecio1.setText(valPrecVtaReal);
                lblPrecio2.setText(tblProductos.getValueAt(tblProductos.getSelectedRow(), 6).toString());
                /*double ahorro=0;
          ahorro=FarmaUtility.getDecimalNumber(valPrecVtaReal) - FarmaUtility.getDecimalNumber(tblProductos.getValueAt(tblProductos.getSelectedRow(),6).toString());*/
                lblPrecioAhorro.setText(valAhorro);
            } else {
                stkFracLoc = FarmaUtility.getValueFieldArrayList(myArray, 0, 11);
                descUnidFracLoc = FarmaUtility.getValueFieldArrayList(myArray, 0, 12);

                tblProductos.setValueAt(valPrecVta, tblProductos.getSelectedRow(), 6);
                lblPrecio1.setText(tblProductos.getValueAt(tblProductos.getSelectedRow(), 6).toString());
                lblPrecio2.setText("0");
                lblPrecioAhorro.setText("0");
                lblPrecio2.setVisible(false);
                lblPrecioAhorro.setVisible(false);
                lblPrecioDesc.setVisible(false);
                lblAhorro.setVisible(false);
            }
        } else {
            stkProd = "";
            descUnidPres = "";
            valFracProd = "";
            valPrecPres = "";
            indProdCong = "";
            valPrecVta = "";
            descUnidVta = "";
            indProdHabilVta = "";
            porcDscto_1 = "";
            tipoProd = "";
            bonoProd = "";
            porcDscto_2 = "";
            stkFracLoc = "";
            descUnidFracLoc = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtProducto);
        }

        lblDesc.setText(DescProdPadre);
        lblLab.setText(LabProdPadre);
        lblStockAdic.setText(stkFracLoc);
        lblUnidFracLoc.setText(descUnidFracLoc);

        //lblDescMotivo.setText(DescMotivo);
        //JCORTEZ 17.04.08
        txtDescMotivo.setLineWrap(true);
        txtDescMotivo.setWrapStyleWord(true);
        txtDescMotivo.setText(DescMotivo);


        lblUnidad.setText(descUnidPres);
        lblUnidad1.setText(descUnidPres);

        int vFila = tblProductos.getSelectedRow();
        tblProductos.setValueAt(stkProd, vFila, COL_STOCK);
        //tblProductos.setValueAt(valPrecVta, vFila, COL_PREC_VTA); //queda precio oferta
        log.debug("valPrecVtaReal :" + valPrecVtaReal);
        log.debug("valPrecVta DESC :" + tblProductos.getValueAt(tblProductos.getSelectedRow(), 6).toString());
        log.debug("valAhorro :" + valAhorro);

        tblProductos.setValueAt(descUnidVta, vFila, COL_UND_VTA);
        tblProductos.setValueAt(bonoProd, vFila, COL_BONO);

        VariablesVentas.vVal_Frac = valFracProd;
        VariablesVentas.vInd_Prod_Habil_Vta = indProdHabilVta;
        VariablesVentas.vPorc_Dcto_1 = porcDscto_1;
        VariablesVentas.vPorc_Dcto_2 = porcDscto_2; //JCORTEZ
        tblProductos.repaint();
    }

    private void muestraDetalleProducto() {
        if (tblProductos.getRowCount() == 0)
            return;
        int vFila = tblProductos.getSelectedRow();
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
        VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_DESC);
        VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_LAB);
        DlgDetalleProducto dlgDetalleProducto = new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);

    }

    private void muestraIngresoCantidad() {
        if (tblProductos.getRowCount() == 0)
            return;

        FarmaVariables.vAceptar = true;
        //JMIRANDA 21/09/2009
        boolean flagContinua = true;
        try {
            // Verifica si es obligatorio ingresar codigo de barra

            if (DBVentas.getIndCodBarra(VariablesVentas.vCod_Prod).equalsIgnoreCase("S") && FarmaVariables.vAceptar &&
                VariablesVentas.vIndEsCodBarra) {

                DlgIngreseCodBarra dlgIngCodBarra = new DlgIngreseCodBarra(myParentFrame, "", true);
                //dlgIngCodBarra.setVisible(true);
                //valida si se ha insertado cod Barra
                if (UtilityVentas.validaCodBarraLocal(txtProducto.getText().trim())) {
                    dlgIngCodBarra.setVisible(true);
                    flagContinua = VariablesVentas.bIndCodBarra;
                }
                if (VariablesVentas.vCodigoBarra.equalsIgnoreCase(txtProducto.getText().trim())) {
                    flagContinua = true;
                }
                //flagContinua = VariablesVentas.bIndCodBarra;
                FarmaVariables.vAceptar = flagContinua;
                log.debug("COD_BArra, flagcontinua: " + flagContinua);
            }

            // Verifica si posee Mensaje de Producto
            // VariablesVentas.vMensajeProd = DBVentas.getMensajeProd(VariablesVentas.vCod_Prod);
            String vMensajeProd = DBVentas.getMensajeProd(VariablesVentas.vCod_Prod);
            if (!vMensajeProd.equalsIgnoreCase("N") && FarmaVariables.vAceptar) {

                String sMensaje = "";
                sMensaje = UtilityVentas.saltoLineaConLimitador(vMensajeProd);
                log.debug(sMensaje);
                //flagContinua = JConfirmDialog.rptaConfirmDialogDefaultNo(this,sMensaje);
                //flagContinua = com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,sMensaje);
                //FarmaVariables.vAceptar = flagContinua;
                //log.debug("Mensaje flagContinua: "+flagContinua);
                //JMIRANDA 26.03.2010
                FarmaUtility.showMessage(this, sMensaje, txtProducto);
            }

            // Valida ID Usuario
            if (DBVentas.getIndSolIdUsu(VariablesVentas.vCod_Prod).equalsIgnoreCase("S") && FarmaVariables.vAceptar) {
                //llamar a Usuario para visualizar
                flagContinua = validaLoginVendedor();
                FarmaVariables.vAceptar = flagContinua;
                log.debug("SolId. flagContinua: " + flagContinua);

            }

        } catch (Exception sql) {
            FarmaUtility.showMessage(this, "Error en Validar Producto: " + sql, txtProducto);
        }

        if (flagContinua) {


            int vFila = tblProductos.getSelectedRow();
            VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
            VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_DESC);
            VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_LAB);
            VariablesVentas.vPorc_Igv_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_IND_IGV);

            VariablesVentas.vCant_Ingresada_Temp = "0";
            VariablesVentas.vNumeroARecargar = "";
            VariablesVentas.vVal_Prec_Lista_Tmp = "";
            VariablesVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
            VariablesVentas.vTipoProductoVirtual = "";

            VariablesVentas.vVal_Prec_Pub = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_PREC_VTA);
            VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_ORIG_PROD);
            VariablesVentas.vPorc_Dcto_2 = "0";
            VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
            VariablesVentas.vCantxDia = "";
            VariablesVentas.vCantxDias = "";


            log.debug("VariablesVentas.vCod_Prod: " + VariablesVentas.vCod_Prod);
            log.debug("VariablesConvenioBTLMF.vCodConvenio: " + VariablesConvenioBTLMF.vCodConvenio);

            /* try {*/
            //ini-Agregado FRAMIREZ
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF &&
                VariablesVentas.vCod_Prod.trim().length() == 6) {
                /*String pAux =
                        DBConvenioBTLMF.getIndExcluidoConv(VariablesVentas.vCod_Prod.trim());*/
                //log.debug("VariablesVentas.vEstadoProdConvenio: "+VariablesVentas.vEstadoProdConvenio);
                /*if (pAux.equalsIgnoreCase("E"))*/

                UtilityConvenioBTLMF.Busca_Estado_ProdConv(this);
                log.debug("VariablesVentas.vEstadoProdConvenio: " + VariablesVentas.vEstadoProdConvenio);
                if (!VariablesVentas.vEstadoProdConvenio.equalsIgnoreCase("I")) {
                    FarmaUtility.showMessage(this, "Producto no esta en cobertura del Convenio.", null);
                    flagContinua = false;
                }

            }
            /*} catch (SQLException e) {
              FarmaUtility.showMessage(this,"Error en Validar Producto: "+e,txtProducto);
          }*/

            //fin-Agregado FRAMIREZ

            if (flagContinua) {
                guardaInfoProdVariables();

                if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                    VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 &&
                    VariablesVentas.vCod_Prod.trim().length() == 6 &&
                    UtilityConvenioBTLMF.existeProductosConvenio(this, null)) {
                    VariablesConvenioBTLMF.vValidaPrecio = true;
                    SubProcesosConvenios precConv = new SubProcesosConvenios();
                    precConv.start();
                }
                DlgIngresoCantidad dlgIngresoCantidad;
                dlgIngresoCantidad = new DlgIngresoCantidad(myParentFrame, "", true);

                VariablesVentas.vIngresaCant_ResumenPed = false;
                //ERIOS 11.04.2008 Si es oferta, no obtiene precio de bbdd.
                if (VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) {
                    //muestraInfoProdOferta();
                    VariablesVentas.vVal_Prec_Vta =
                            FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_PREC_VTA); //((String)(tblProductos.getValueAt(filaActual,6))).trim();
                    //VariablesVentas.vPorc_Dcto_1 = ((String)(tblProductos.getValueAt(filaActual,5))).trim();
                    //VariablesVentas.vPorc_Dcto_2 = ((String)(tblProductos.getValueAt(filaActual,5))).trim();
                    //VariablesVentas.vIngresaCant_ResumenPed = true;}
                    log.debug("VariablesVentas.vPorc_Dcto_1 : " + VariablesVentas.vPorc_Dcto_1);
                    log.debug("VariablesVentas.vPorc_Dcto_2 : " + VariablesVentas.vPorc_Dcto_2);
                }

                dlgIngresoCantidad.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    seleccionaProducto();
                    FarmaVariables.vAceptar = false;
                    cerrarVentana(true); //jcortez
                }
            }
        }
    }

    private void verificaCheckJTable() {
        //String indProdProm = (String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),17));
        int vFila = tblProductos.getSelectedRow();
        String codigo = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);

        Boolean valor = (Boolean)(tblProductos.getValueAt(vFila, 0));
        if (valor.booleanValue()) {
            if (!buscar(VariablesVentas.vArrayList_Prod_Promociones, codigo) &&
                !buscar(VariablesVentas.vArrayList_Prod_Promociones_temporal, codigo)) {
                deseleccionaProducto();
                vSeleccionProductoAlter = true;
            } else {
                FarmaUtility.showMessage(this, "El Producto se encuentra en una Promoción", txtProducto);
                return;
            }
        } else {
            VariablesVentas.vIndOrigenProdVta =
                    FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_ORIG_PROD); //5 JCORTEZ
            muestraInfoProd();
            VariablesVentas.vIndProdVirtual = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_IND_VIRT);
            if (!validaStockDisponible() &&
                !VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                return;
            }
            if (!validaProductoTomaInventario(vFila)) {
                FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario",
                                         txtProducto);
                return;
            }
            if (!validaProductoHabilVenta()) {
                FarmaUtility.showMessage(this, "El Producto NO se encuentra hábil para la venta. Verifique!!!",
                                         txtProducto);
                return;
            }
            /*VariablesVentas.vIndProdVirtual =
          FarmaUtility.getValueFieldJTable(tblProductos, row, 13);*/

            //log.debug("VariablesVentas.vIndPedConProdVirtual " + VariablesVentas.vIndPedConProdVirtual);
            if (VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
                VariablesVentas.vIndPedConProdVirtual) {
                //Modificado para que no pueda comprar Nada si hay Promociones
                if (VariablesVentas.vArrayList_PedidoVenta.size() > 0 ||
                    VariablesVentas.vArrayList_Prod_Promociones.size() > 0 ||
                    VariablesVentas.vArrayList_Prod_Promociones_temporal.size() > 0 ||
                    VariablesVentas.vArrayList_Promociones.size() > 0 ||
                    VariablesVentas.vArrayList_Promociones_temporal.size() > 0) {
                    //log.debug("Se esta validando por compra ");
                    FarmaUtility.showMessage(this,
                                             "La venta de un Producto Virtual debe ser única por pedido. Verifique!!!",
                                             txtProducto);
                    return;
                } else {
                    FarmaUtility.showMessage(this,
                                             "No se puede realizar la venta de un Producto Virtual por este origen.",
                                             txtProducto);
                    return;
                    //VariablesVentas.vIndProdControlStock = false;
                    //VariablesVentas.vIndPedConProdVirtual = true;
                    //evaluaTipoProdVirtual();
                }
            } else {
                VariablesVentas.vIndProdControlStock = true;
                VariablesVentas.vIndPedConProdVirtual = false;
                muestraIngresoCantidad();
            }
        }
        //txtProducto.selectAll();
        muestraIndicadoresProducto();
    }

    private void seleccionaProducto() {
        int vFila = tblProductos.getSelectedRow();
        Boolean valorTmp = (Boolean)(tblProductos.getValueAt(vFila, 0));
        //    VariablesVentas.vTotalPrecVtaProd = (Double.parseDouble(VariablesVentas.vCant_Ingresada) * Double.parseDouble(VariablesVentas.vVal_Prec_Vta));
        /*log.debug(VariablesVentas.vVal_Prec_Vta + " y  " +
                       VariablesVentas.vVal_Prec_Vta);*/
        VariablesVentas.vTotalPrecVtaProd =
                (FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada) * FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta));
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (VariablesVentas.vIndProdControlStock &&
            /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES, 02.07.2010
                                      Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                      ConstantsVentas.INDICADOR_A,
                                      ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                      Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                                    true,
                                                    this,
                                                    txtProducto))*/
            !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod, //ASOSA, 02.07.2010
                Integer.parseInt(VariablesVentas.vCant_Ingresada), ConstantsVentas.INDICADOR_A,
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, Integer.parseInt(VariablesVentas.vCant_Ingresada),
                true, this, tblProductos, ""))
            return;
        FarmaUtility.setCheckValue(tblProductos, false);
        Boolean valor = (Boolean)(tblProductos.getValueAt(vFila, 0));
        UtilityVentas.operaProductoSeleccionadoEnArrayList_02(valor, VariablesVentas.secRespStk); //ASOSA, 06.07.2010
    }

    private void deseleccionaProducto() {
        int vFila = tblProductos.getSelectedRow();
        String cantidad = "";
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
        String indicadorControlStock = FarmaConstants.INDICADOR_S;
        String codigoTmp = "";
        String secRespaldo = ""; //ASOSA, 06.07.2010
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++) {
            codigoTmp = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(0);
            if (VariablesVentas.vCod_Prod.equalsIgnoreCase(codigoTmp)) {
                indicadorControlStock =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 16);
                cantidad = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(4);
                VariablesVentas.vVal_Frac =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 10);
                secRespaldo =
                        (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(26); //ASOSA, 06.07.2010
                break;
            }
        }
        /*Boolean valorTmp =
      (Boolean) (tblProductos.getValueAt(vFila, 0));*/
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (indicadorControlStock.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
            /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES, ASOSA, 02.07.2010
                                      Integer.parseInt(cantidad),
                                      ConstantsVentas.INDICADOR_D,
                                      ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR,
                                      Integer.parseInt(cantidad),
                                                    true,
                                                    this,
                                                    txtProducto))*/
            !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod, //ASOSA, 02.07.2010
                Integer.parseInt(cantidad), ConstantsVentas.INDICADOR_D,
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad), true, this, tblProductos,
                secRespaldo)) {
            return;
        }
        FarmaUtility.setCheckValue(tblProductos, false);
        Boolean valor = (Boolean)(tblProductos.getValueAt(vFila, 0));
        UtilityVentas.operaProductoSeleccionadoEnArrayList_02(valor, VariablesVentas.secRespStk); //ASOSA, 06.07.2010
        if (VariablesVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesVentas.vIndPedConProdVirtual = false;
        //indicadorItems = FarmaConstants.INDICADOR_N;
    }

    private boolean validaProductoTomaInventario(int pRow) {
        if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return true;
        else
            return false;
    }

    private boolean validaProductoHabilVenta() {
        if (VariablesVentas.vInd_Prod_Habil_Vta.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    private boolean validaStockDisponible() {
        if (FarmaUtility.isInteger(stkProd) && Integer.parseInt(stkProd) > 0)
            return true;
        else
            return false;
    }

    private void muestraProductoCongelado(JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            //String indProdCong = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),pColumna))).trim();
            if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }

    private void muestraProductoPromocion(int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String indProdProm = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_IND_PROM);
            if (indProdProm.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }

    private void muestraProductoRefrigerado(int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String indProdRef = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (indProdRef.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }

    private void muestraIndTipoProd(int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String indProdRef = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[0]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[0]);
            else if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[1]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[1]);
            else if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[2]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[2]);
            else if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[3]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[3]);
        }
    }

    private boolean buscar(ArrayList array, String codigo) {
        String codtemp;
        for (int i = 0; i < array.size(); i++) {
            codtemp = ((String)(((ArrayList)array.get(i)).get(0))).trim();
            if (codtemp.equalsIgnoreCase(codigo))
                return true;
        }
        return false;
    }

    /**
     * Coloca el tipo de producto
     * @author dubilluz
     * @since 22.10.2007
     */
    private void muestraIndTipoProd(int pColumna, JLabel pLabel, String tipoProd) {
        if (tblProductos.getRowCount() > 0) {
            //String indProdRef = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),pColumna))).trim();
            if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[0]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[0]);
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[1]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[1]);
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[2]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[2]);
            else if (tipoProd.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[3]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[3]);
        }
    }

    /**
     * Muestra los indicadores del producto seleccionado.
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    private void muestraIndicadoresProducto() {
        muestraNombreLab(COL_LAB, lblDescLab_Prod);
        muestraProductoInafectoIgv(COL_IND_IGV, lblProdIgv);
        muestraProductoPromocion(COL_IND_PROM, lblProdProm);
        muestraProductoRefrigerado(COL_IND_REFRIG, lblProdRefrig);
        muestraIndTipoProd(COL_TIPO_PROD, lblIndTipoProd);
        muestraInfoProd();
        muestraProductoCongelado(lblProdCong);
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
    }

    /**
     * Muestra si es producto de Encarte
     * @author JCORTEZ
     * @since  08.04.2008
     */
    private void muestraProductoEncarte(int pColumna, JLabel pLabel) {
        if (tblProductos.getRowCount() > 0) {
            int vFila = tblProductos.getSelectedRow();
            String indProdEncarte = FarmaUtility.getValueFieldJTable(tblProductos, vFila, pColumna);
            if (indProdEncarte.length() > 0) {
                pLabel.setVisible(true);
            } else {
                pLabel.setVisible(false);
            }
        }
    }


    /**
     * se obtiene la informacion del producto complementario
     * @author JCORTEZ
     * @since 16.04.2008
     * */
    private void obtieneInfoProdPadreComple(ArrayList pArrayList) {

        int vFila = tblProductos.getSelectedRow();
        String codProdComp = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
        try {
            DBVentas.obtieneInfoProdOrigenComp(pArrayList, codProdComp);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto origen. \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    /**
     * Se muestra el tratemiento del producto
     * @author JCORTEZ
     * @since 29.05.2008
     */
    private void mostrarTratamiento() {

        if (tblProductos.getRowCount() == 0)
            return;

        int vFila = tblProductos.getSelectedRow();

        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
        VariablesVentas.vDesc_Prod = ((String)(tblProductos.getValueAt(vFila, 2))).trim();
        VariablesVentas.vNom_Lab = ((String)(tblProductos.getValueAt(vFila, 4))).trim();
        VariablesVentas.vPorc_Igv_Prod = ((String)(tblProductos.getValueAt(vFila, 11))).trim();
        VariablesVentas.vCant_Ingresada_Temp = "0";
        VariablesVentas.vNumeroARecargar = "";
        VariablesVentas.vVal_Prec_Lista_Tmp = "";
        VariablesVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
        VariablesVentas.vTipoProductoVirtual = "";
        //VariablesVentas.vVal_Prec_Pub = ((String)(myJTable.getValueAt(vFila,6))).trim();
        VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_ORIG_PROD);
        VariablesVentas.vPorc_Dcto_2 = "0";
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_S;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";
        VariablesVentas.vIndModificacion = FarmaConstants.INDICADOR_N;
        //guardaInfoProdVariables();

        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF &&
            VariablesVentas.vCod_Prod.trim().length() == 6) {

            UtilityConvenioBTLMF.Busca_Estado_ProdConv(this);

            if (!VariablesVentas.vEstadoProdConvenio.equalsIgnoreCase("I")) {
                FarmaUtility.showMessage(this, "Producto no esta en cobertura del Convenio.", null);
                return;
            }
        }

        DlgTratamiento dlgtratemiento = new DlgTratamiento(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = false;
        dlgtratemiento.setVisible(true);

        if (FarmaVariables.vAceptar) {
            seleccionaProducto();
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        }
    }

    private void guardaInfoProdVariables() {
        log.debug("*************************************************");
        if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)) {
            log.debug("*************************************************");
            if (VariablesVentas.vEsPedidoConvenio &&
                !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) //Se ha seleccionado un convenio
            {
                //String indControlPrecio = "";
                String mensaje = "";
                try {
                    //mensaje = "Error al obtener el indicador de control de precio del producto.\n";
                    //indControlPrecio = DBConvenio.obtieneIndPrecioControl(VariablesVentas.vCod_Prod);

                    VariablesConvenio.vVal_Prec_Vta_Local = VariablesVentas.vVal_Prec_Pub;

                    /* 23.01.2007 ERIOS La validacion de realiza por las listas de exclusion */
                    //if(indControlPrecio.equals(FarmaConstants.INDICADOR_N))
                    if (true) {
                        mensaje = "Error al obtener el nuevo precio del producto.\n";
                        VariablesConvenio.vVal_Prec_Vta_Conv =
                                DBConvenio.obtieneNvoPrecioConvenio(VariablesConvenio.vCodConvenio,
                                                                    VariablesVentas.vCod_Prod,
                                                                    VariablesVentas.vVal_Prec_Pub);
                    } else {

                        VariablesConvenio.vVal_Prec_Vta_Conv = VariablesVentas.vVal_Prec_Pub;
                    }
                } catch (SQLException sql) {
                    //log.error("",sql);
                    log.error("", sql);
                    FarmaUtility.showMessage(this, mensaje + sql.getMessage(), null);
                    FarmaUtility.moveFocus(txtProducto);
                }
            }
        }
    }

    private boolean validaLoginVendedor() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;
        boolean flag = false;
        log.debug("numsec: " + numsec);
        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            //dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            log.debug("FarnaVariables.NumSec: " + FarmaVariables.vNuSecUsu);
            if (FarmaVariables.vAceptar) {
                if (numsec.equalsIgnoreCase(FarmaVariables.vNuSecUsu)) {
                    log.debug("numsec 2: " + numsec);
                    flag = true;
                } else {
                    FarmaUtility.showMessage(this,
                                             "Ud. ha ingresado un usuario diferente al que inicio la Venta." + "\nIngrese Usuario que Inicio venta o vuelva a ingresar otro Usuario.",
                                             txtProducto);
                    flag = false;
                }
            } else {
                flag = false;
            }
        } catch (Exception e) {
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(), null);
            flag = false;
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return flag;
    }

    private boolean validaVentaConMenos() {

        if (tblProductos.getRowCount() == 0)
            return false;

        FarmaVariables.vAceptar = true;
        boolean flagContinua = true;

        try {
            log.debug("vAceptar 1: " + FarmaVariables.vAceptar);

            // Verifica si es obligatorio ingresar codigo de barra
            log.debug("vAceptar2 : " + FarmaVariables.vAceptar);
            log.debug("vCod_Prod: " + VariablesVentas.vCod_Prod);
            if (DBVentas.getIndCodBarra(VariablesVentas.vCod_Prod).equalsIgnoreCase("S") && FarmaVariables.vAceptar &&
                VariablesVentas.vIndEsCodBarra) {

                //valida si se ha insertado cod Barra
                if (UtilityVentas.validaCodBarraLocal(txtProducto.getText().trim())) {
                    DlgIngreseCodBarra dlgIngCodBarra = new DlgIngreseCodBarra(myParentFrame, "", true);
                    dlgIngCodBarra.setVisible(true);
                    flagContinua = VariablesVentas.bIndCodBarra;
                }
                if (VariablesVentas.vCodigoBarra.equalsIgnoreCase(txtProducto.getText().trim())) {
                    flagContinua = true;
                }
                //flagContinua = VariablesVentas.bIndCodBarra;
                FarmaVariables.vAceptar = flagContinua;
                log.debug("COD_BArra, flagcontinua: " + flagContinua);
            }

            // Verifica si posee Mensaje de Producto
            String vMensajeProd = DBVentas.getMensajeProd(VariablesVentas.vCod_Prod);
            if (!vMensajeProd.equalsIgnoreCase("N") && FarmaVariables.vAceptar) {

                String sMensaje = "";
                sMensaje = UtilityVentas.saltoLineaConLimitador(vMensajeProd);
                //ENVIO vMensajeProd LLAMAR METODO RETORNAR SMENSAJE CON SALTO DE LINEA
                log.debug(sMensaje);
                FarmaUtility.showMessage(this, sMensaje, txtProducto);
            }

            // Valida ID Usuario
            String pInd = DBVentas.getIndSolIdUsu(VariablesVentas.vCod_Prod).trim().toUpperCase();
            if (pInd.equalsIgnoreCase("S") && FarmaVariables.vAceptar) {
                //llamar a Usuario para visualizar
                flagContinua = validaLoginVendedor();
                FarmaVariables.vAceptar = flagContinua;
                log.debug("SolId. flagContinua: " + flagContinua);
            } else {
                if (pInd.equalsIgnoreCase("J") && FarmaVariables.vAceptar) {
                    log.debug("*** Valida Producto Venta Cero");
                    //llamar a Usuario para visualizar
                    flagContinua = validaLoginQF();
                    FarmaVariables.vAceptar = flagContinua;
                    log.debug("SolId. flagContinua : " + flagContinua);
                }
            }

        } catch (Exception sql) {
            FarmaUtility.showMessage(this, "Error en Validar Producto: " + sql, txtProducto);
        }

        //if(flagContinua)
        //Continua con el proceso

        return flagContinua;
    }

    private boolean validaLoginQF() {
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
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return FarmaVariables.vAceptar;
    }
}
