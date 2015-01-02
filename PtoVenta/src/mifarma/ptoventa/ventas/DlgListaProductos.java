package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgFiltroProductos;
import mifarma.ptoventa.DlgProcesar;
import mifarma.ptoventa.FrmEconoFar;
import mifarma.ptoventa.campAcumulada.DlgListaCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.convenio.DlgListaConvenios;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.DlgListaConveniosBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.DlgListaClientes;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.encuesta.reference.FacadeEncuesta;
import mifarma.ptoventa.fidelizacion.reference.AuxiliarFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.hilos.SubProcesosConvenios;
import mifarma.ptoventa.recetario.DlgListaGuiasRM;
import mifarma.ptoventa.recetario.DlgResumenRecetarioMagistral;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesReceta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProductos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      28.12.2005   Creación
 * PAULO       28.04.2006   Modificacion <br>
 * DUBILLUZ    14.06.2007   Modificacion <br>
 * ERIOS       01.06.2008   Modificacion <br>
 * DVELIZ      22.08.2008   Modificacion <br>
 * JCALLO      03.03.2009   Modificacion <br>
 * ASOSA       02.02.2010   Modificacion <br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgListaProductos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaProductos.class);

    private Frame myParentFrame;

    private JTable myJTable;

    private FarmaTableModel tableModelListaPrecioProductos;
    private FarmaTableModel tblModelListaSustitutos;

    private String descUnidPres = "";
    private String stkProd = "";
    private String valPrecPres = "";
    private String valFracProd = "";
    private String indProdCong = "";
    //private String valPrecLista = "";
    private String valPrecVta = "";
    private String descUnidVta = "";
    private String indProdHabilVta = "";
    private String porcDscto_1 = "";
    //private String indProdProm = "";
    // kmoncada 02.07.2014 obtiene el estado del producto.
    private String estadoProd = "";

    private String tipoProducto = ""; //ASOSA - 02/10/2014 - PANHD


    /**
     * Indicador de Tipo de Producto
     * @author dubilluz
     * @since  22.10.2007
     */
    private String tipoProd = "";
    private String bonoProd = "";

    private int totalItems = 0;
    private double totalVenta = 0;

    private String tempCodBarra = "";

    /**
     * Indicadores de stock en adicional en fraccion del local.
     * @author Edgar Rios Navarro
     * @since 03.06.2008
     */
    private String stkFracLoc = "";
    private String descUnidFracLoc = "";

    /**
     * Columnas de la grilla
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    private final int COL_COD = 1;
    private final int COL_DESC_PROD = 2;
    private final int COL_STOCK = 5;
    private final int COL_ORD_LISTA = 18;
    private final int COL_IND_ENCARTE = 19;
    private final int COL_ORIG_PROD = 20;

    //JCORTEZ 23.07.08
    private final int COL_RES_CUPON = 25;
    private final int COL_RES_ORIG_PROD = 19;
    private final int COL_RES_VAL_FRAC = 10;
    private final int DIG_PROD = 6;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabel lblItems = new JLabel();
    private JLabel lblItems_T = new JLabel();
    private JLabel lblPrecio = new JLabel();
    private JLabel lblPrecio_T = new JLabel();
    private JLabel lblUnidad = new JLabel();
    private JLabel lblUnidad_T = new JLabel();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JPanel jPanel2 = new JPanel();
    private JSeparator jSeparator2 = new JSeparator();
    private JLabel lblDescLab_Alter = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel1 = new JPanel();
    private JLabel lblDescLab_Prod = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JPanel pnlIngresarProductos = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextField txtProducto = new JTextField();
    private JButton btnProducto = new JButton();
    private JTable tblProductos = new JTable();
    private JTable tblListaSustitutos = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabel lblTotalVenta = new JLabel();
    private JLabel lblTotalVenta_T = new JLabel();
    private JPanel jPanel4 = new JPanel();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblCliente = new JLabelWhite();
    private JLabelWhite lblCliente_T = new JLabelWhite();
    private JLabelWhite lblClienteConv = new JLabelWhite();
    private JLabelWhite lblClienteConv_T = new JLabelWhite();

    private JButtonLabel btnProdAlternativos = new JButtonLabel();

    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelWhite lblMedico = new JLabelWhite();
    private JLabelWhite lblMedicoT = new JLabelWhite();

    private JLabelWhite lblConvenio = new JLabelWhite();
    private JLabelWhite lblConvenioT = new JLabelWhite();


    private JLabel lblProdIgv = new JLabel();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblF13 = new JLabelFunction();
    private JPanel jPanel5 = new JPanel();
    private JLabel lblProdRefrig = new JLabel();
    private JLabel lblProdCong = new JLabel();
    private JLabelWhite lblIndTipoProd = new JLabelWhite();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabel lblProdProm = new JLabel();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabel lblProdEncarte = new JLabel();
    private JLabelWhite lblStockAdic_T = new JLabelWhite();
    private JLabelWhite lblStockAdic = new JLabelWhite();
    private JLabelWhite lblUnidFracLoc = new JLabelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private Object operaCampañasFid;
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelHeader jPanelHeader0 = new JPanelHeader();

    private JLabel lblMensajeCampaña = new JLabel();
    // private JLabelFunction lblF12 = new JLabelFunction();


    private boolean vEjecutaAccionTeclaListado = false;
    //JMIRANDA 16/09/2009
    private JButtonLabel lblMensajeCodBarra = new JButtonLabel();

    private boolean pasoTarjeta = false;

    private int contarCombinacion = 0; //kmoncada contador para validar combinacion de teclas ALT+S o ALT+R o ALT+P

    private JLabel lblDNI_SIN_COMISION = new JLabel();

    String nombCliente = " ";
    String nombConvenio = " ";
    private JLabelFunction lblDeliveryProv = new JLabelFunction();
    private DlgResumenPedido dlgResumenPedido;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaProductos() {
        this(null, "", false);
    }

    public DlgListaProductos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(737, 567));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos y Precios");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setBackground(Color.white);
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
        jPanel3.setBounds(new Rectangle(15, 80, 450, 45));
        jPanel3.setBackground(new Color(43, 141, 39));
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel3.setLayout(null);
        lblItems.setText("0");
        lblItems.setBounds(new Rectangle(145, 5, 80, 15));
        lblItems.setFont(new Font("SansSerif", 1, 14));
        lblItems.setForeground(Color.white);
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        lblItems_T.setText("Items :");
        lblItems_T.setBounds(new Rectangle(15, 5, 65, 15));
        lblItems_T.setFont(new Font("SansSerif", 1, 14));
        lblItems_T.setForeground(Color.white);
        lblPrecio.setBounds(new Rectangle(90, 25, 130, 15));
        lblPrecio.setFont(new Font("SansSerif", 1, 15));
        lblPrecio.setForeground(Color.white);
        lblPrecio_T.setText("Precio : S/.");
        lblPrecio_T.setBounds(new Rectangle(10, 25, 75, 15));
        lblPrecio_T.setFont(new Font("SansSerif", 1, 13));
        lblPrecio_T.setForeground(Color.white);
        lblUnidad.setBounds(new Rectangle(90, 5, 190, 15));
        lblUnidad.setFont(new Font("SansSerif", 1, 11));
        lblUnidad.setForeground(Color.white);
        lblUnidad_T.setText("Unidad :");
        lblUnidad_T.setBounds(new Rectangle(10, 5, 60, 15));
        lblUnidad_T.setFont(new Font("SansSerif", 1, 11));
        lblUnidad_T.setForeground(Color.white);
        lblF6.setText("[ F6 ] Filtrar Productos");
        lblF6.setBounds(new Rectangle(155, 485, 130, 20));
        lblF2.setText("[ F2 ] Ver Alternativos");
        lblF2.setBounds(new Rectangle(290, 460, 130, 20));
        lblF1.setText("[ F1 ] Info Prod");
        lblF1.setBounds(new Rectangle(155, 460, 130, 20));
        jScrollPane2.setBounds(new Rectangle(15, 365, 700, 90));
        jScrollPane2.setBackground(new Color(255, 130, 14));
        jPanel2.setBounds(new Rectangle(15, 345, 700, 20));
        jPanel2.setBackground(new Color(255, 130, 14));
        jPanel2.setLayout(null);
        jSeparator2.setBounds(new Rectangle(200, 0, 15, 20));
        jSeparator2.setBackground(Color.black);
        jSeparator2.setOrientation(SwingConstants.VERTICAL);
        lblDescLab_Alter.setBounds(new Rectangle(225, 0, 375, 20));
        lblDescLab_Alter.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Alter.setForeground(Color.white);
        jScrollPane1.setBounds(new Rectangle(15, 150, 700, 165));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(15, 130, 700, 20));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        lblDescLab_Prod.setBounds(new Rectangle(160, 0, 345, 20));
        lblDescLab_Prod.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Prod.setForeground(Color.white);
        jSeparator1.setBounds(new Rectangle(150, 0, 15, 20));
        jSeparator1.setBackground(Color.black);
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        pnlIngresarProductos.setBounds(new Rectangle(15, 45, 700, 30));
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
        txtProducto.setBounds(new Rectangle(100, 5, 460, 20));
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
        txtProducto.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtProducto_focusLost(e);
            }
        });
        btnProducto.setText("Producto");
        btnProducto.setBounds(new Rectangle(25, 5, 60, 20));
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
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(155, 510, 130, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(565, 510, 150, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 460, 135, 20));
        lblTotalVenta.setText("0.00");
        lblTotalVenta.setBounds(new Rectangle(140, 25, 85, 15));
        lblTotalVenta.setFont(new Font("SansSerif", 1, 15));
        lblTotalVenta.setForeground(Color.white);
        lblTotalVenta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalVenta_T.setText("Total venta : S/.");
        lblTotalVenta_T.setBounds(new Rectangle(15, 25, 120, 15));
        lblTotalVenta_T.setFont(new Font("SansSerif", 1, 15));
        lblTotalVenta_T.setForeground(Color.white);
        jPanel4.setBounds(new Rectangle(475, 80, 240, 45));
        jPanel4.setBackground(new Color(43, 141, 39));
        jPanel4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel4.setLayout(null);
        pnlTitle1.setBounds(new Rectangle(15, 10, 700, 30));
        lblCliente.setBounds(new Rectangle(70, 5, 305, 20));
        lblCliente.setText(" ");
        lblCliente.setFont(new Font("SansSerif", 1, 14));
        lblCliente_T.setText("Cliente:");
        lblCliente_T.setBounds(new Rectangle(5, 5, 60, 20));
        lblCliente_T.setFont(new Font("SansSerif", 1, 14));
        btnProdAlternativos.setText("Productos Sustitutos");
        btnProdAlternativos.setBounds(new Rectangle(10, 0, 150, 20));
        btnProdAlternativos.setMnemonic('s');
        btnProdAlternativos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProdAlternativos_actionPerformed(e);
            }
        });
        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 20));
        btnRelacionProductos.setMnemonic('r');
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        lblF7.setBounds(new Rectangle(290, 485, 130, 20));
        lblF7.setText("[ F7 ] Filtrar Desc.");
        lblF9.setBounds(new Rectangle(565, 485, 150, 20));
        //lblF9.setText("[ F9 ] Asociar Campaña");//lblF9.setText("[ F9 ] Vta. Delivery");//JCALLO 19.12.2008 SE REEMPLAZO PARA OPCION DE CAMP ACUMULADAS
        lblF9.setText("[ F9 ] Camp. Acumulada");
        lblF10.setBounds(new Rectangle(15, 510, 135, 20));
        lblF10.setText("[ F10 ] Venta Perdida");
        lblF8.setBounds(new Rectangle(425, 485, 135, 20));
        lblF8.setText("[ F8 ] Dcto por Receta");
        lblMedico.setBounds(new Rectangle(435, 5, 260, 20));
        lblMedico.setText(" ");
        lblMedicoT.setText("Medico:");
        lblMedicoT.setBounds(new Rectangle(390, 5, 45, 20));
        lblProdIgv.setBounds(new Rectangle(125, 0, 95, 20));
        lblProdIgv.setFont(new Font("SansSerif", 1, 11));
        lblProdIgv.setText("SIN IGV");
        lblProdIgv.setBackground(new Color(44, 146, 24));
        lblProdIgv.setOpaque(true);
        lblProdIgv.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdIgv.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdIgv.setForeground(Color.white);
        lblF12.setBounds(new Rectangle(350, 445, 150, 20));
        lblF12.setText("[ F12 ] Buscar TrjxDNI");
        lblF12.setVisible(true);
        lblF13.setBounds(new Rectangle(425, 460, 135, 20));
        lblF13.setText("[ F3 ] Vta. Convenio");
        jPanel5.setBounds(new Rectangle(15, 315, 700, 20));
        jPanel5.setBackground(new Color(255, 130, 14));
        jPanel5.setLayout(null);
        lblProdRefrig.setBounds(new Rectangle(480, 0, 95, 20));
        lblProdRefrig.setVisible(true);
        lblProdRefrig.setFont(new Font("SansSerif", 1, 11));
        lblProdRefrig.setText("REFRIG");
        lblProdRefrig.setBackground(new Color(44, 146, 24));
        lblProdRefrig.setOpaque(true);
        lblProdRefrig.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdRefrig.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdRefrig.setForeground(Color.white);
        lblProdCong.setBounds(new Rectangle(15, 0, 95, 20));
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
        lblF5.setBounds(new Rectangle(15, 485, 135, 20));
        lblF5.setText("[ F5 ] Pack");
        lblF5.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblF5_keyPressed(e);
            }
        });
        lblProdProm.setBounds(new Rectangle(240, 0, 220, 20));
        lblProdProm.setFont(new Font("SansSerif", 1, 11));
        lblProdProm.setText("PRODUCTO EN PACK");
        lblProdProm.setBackground(new Color(44, 146, 24));
        lblProdProm.setOpaque(true);
        lblProdProm.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdProm.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdProm.setForeground(Color.white);
        lblF4.setBounds(new Rectangle(565, 460, 150, 20));
        if (VariablesPtoVenta.vIndVerStockLocales.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            lblF4.setText("[ F4 ] Stock Locales");
        } else if (VariablesPtoVenta.vIndVerReceMagis.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            lblF4.setText("[ F4 ] Recetario Magistral");
        }
        lblProdEncarte.setBounds(new Rectangle(590, 0, 95, 20));
        lblProdEncarte.setVisible(true);
        lblProdEncarte.setFont(new Font("SansSerif", 1, 11));
        lblProdEncarte.setText("ENCARTE");
        lblProdEncarte.setBackground(new Color(44, 146, 24));
        lblProdEncarte.setOpaque(true);
        lblProdEncarte.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdEncarte.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdEncarte.setForeground(Color.white);
        lblStockAdic_T.setText("Stock adic.:");
        lblStockAdic_T.setBounds(new Rectangle(5, 0, 65, 20));
        lblStockAdic_T.setForeground(new Color(43, 141, 39));
        lblStockAdic.setBounds(new Rectangle(75, 0, 40, 20));
        lblStockAdic.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStockAdic.setForeground(new Color(43, 141, 39));
        lblUnidFracLoc.setBounds(new Rectangle(120, 0, 90, 20));
        lblUnidFracLoc.setForeground(new Color(43, 141, 39));
        jPanelWhite1.setBounds(new Rectangle(225, 25, 225, 20));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelHeader1.setBounds(new Rectangle(0, 0, 385, 30));
        lblMensajeCampaña.setBounds(new Rectangle(350, 0, 350, 30));
        //lblMensajeCampaña.setText("   Promoción: \" Acumula tu Compra \"");
        lblMensajeCampaña.setText("   ");
        lblMensajeCampaña.setBackground(Color.white);
        lblMensajeCampaña.setForeground(Color.red);
        lblMensajeCampaña.setFont(new Font("Dialog", 1, 14));
        // lblProdRefrig.setBackground(new Color(44, 146, 24));
        lblMensajeCampaña.setOpaque(true);
        lblMensajeCampaña.setVisible(false);

        //JMIRANDA 16/09/2009
        lblMensajeCodBarra.setText("");
        lblMensajeCodBarra.setBounds(new Rectangle(285, 0, 160, 20));
        lblMensajeCodBarra.setFont(new Font("SansSerif", 1, 12));
        lblDNI_SIN_COMISION.setText("DNI Inválido. No aplica Prog. Atención al Cliente");
        lblDNI_SIN_COMISION.setBounds(new Rectangle(380, 0, 320, 30));
        lblDNI_SIN_COMISION.setForeground(new Color(231, 0, 0));
        lblDNI_SIN_COMISION.setFont(new Font("Dialog", 3, 14));
        lblDNI_SIN_COMISION.setBackground(Color.white);
        lblDNI_SIN_COMISION.setOpaque(true);
        lblDNI_SIN_COMISION.setVisible(false);
        /*
         *         lblMensajeCampaña.setBackground(Color.white);
        lblMensajeCampaña.setForeground(Color.red);
         * */
        lblDeliveryProv.setBounds(new Rectangle(425, 510, 135, 20));
        lblDeliveryProv.setText("[Alt+D] Pedido Delivery");
        lblF12.setBounds(new Rectangle(290, 510, 130, 20));
        pnlTitle1.add(lblDNI_SIN_COMISION, null);
        pnlTitle1.add(lblMensajeCampaña, null);
        //lblMedico.add(lblMensajeCampaña, null);
        pnlTitle1.add(lblMedicoT, null);
        pnlTitle1.add(lblMedico, null);
        jPanelHeader1.add(lblCliente_T, null);
        jPanelHeader1.add(lblCliente, null);
        pnlTitle1.add(jPanelHeader1, null);
        jPanelWhite1.add(lblStockAdic_T, null);
        jPanelWhite1.add(lblStockAdic, null);
        jPanelWhite1.add(lblUnidFracLoc, null);
        //JMIRANDA 16/09/2009
        jPanel3.add(lblMensajeCodBarra, null);
        jPanel3.add(jPanelWhite1, null);
        jPanel3.add(lblPrecio, null);
        jPanel3.add(lblPrecio_T, null);
        jPanel3.add(lblUnidad, null);
        jPanel3.add(lblUnidad_T, null);
        jScrollPane2.getViewport();
        jPanel2.add(btnProdAlternativos, null);
        jPanel2.add(jSeparator2, null);
        jPanel2.add(lblDescLab_Alter, null);
        jScrollPane1.getViewport();
        jPanel1.add(lblIndTipoProd, null);
        jPanel1.add(btnRelacionProductos, null);
        jPanel1.add(jSeparator1, null);
        jPanel1.add(lblDescLab_Prod, null);
        pnlIngresarProductos.add(btnBuscar, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jPanel4.add(lblTotalVenta_T, null);
        jPanel4.add(lblItems_T, null);
        jPanel4.add(lblItems, null);
        jPanel4.add(lblTotalVenta, null);
        jPanel5.add(lblProdEncarte, null);
        jPanel5.add(lblProdProm, null);
        jPanel5.add(lblProdCong, null);
        jPanel5.add(lblProdRefrig, null);
        jPanel5.add(lblProdIgv, null);
        jContentPane.add(lblDeliveryProv, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(jPanel5, null);
        jContentPane.add(lblF13, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF10, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF7, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(jPanel4, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF1, null);
        jScrollPane2.getViewport().add(tblListaSustitutos, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(jPanel2, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlIngresarProductos, null);
        jContentPane.add(lblF5, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableListaPreciosProductos();
        initTableProductosSustitutos();
        setJTable(tblProductos);
        iniciaProceso(true);
        VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
        //JQUISPE 03.05.2010
        //Inicializo los valores de las posiciones
        VariablesVentas.vPosNew = 0;
        VariablesVentas.vPosOld = 0;

        // Inicio Adicion Delivery 28/04/2006 Paulo
        if (!FarmaVariables.vAceptar) {
            String nombreClienteDeliv =
                VariablesDelivery.vNombreCliente + " " + VariablesDelivery.vApellidoPaterno + " " +
                VariablesDelivery.vApellidoMaterno;
            //Modificado por DVELIZ 30.09.08
            //jcallo 02.10.2008
            if (VariablesFidelizacion.vNumTarjeta.trim().length() <= 0) {
                lblCliente.setText(nombreClienteDeliv);
            } else {
                lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente +
                                   " " + VariablesFidelizacion.vApeMatCliente);
            }
            //fin jcallo 02.10.2008
            lblMedico.setText(VariablesVentas.vNombreListaMed);
            FarmaVariables.vAceptar = true;
        }
        // Fin Adicion Delivery 28/04/2006 Paulo
        FarmaVariables.vAceptar = false;

        //Dveliz 26.08.08
        VariablesCampana.vListaCupones = new ArrayList();

        /** JCALLO 01.10.2008**/
        /*if ( VariablesVentas.HabilitarF9.equalsIgnoreCase(
            ConstantsVentas.ACTIVO) ) {
        lblF9.setVisible(true);
    }else if( VariablesVentas.HabilitarF9.equalsIgnoreCase(
            ConstantsVentas.INACTIVO) ){
        lblF9.setVisible(false);
    }*/
        lblF9.setVisible(true);

        if (VariablesPtoVenta.vIndVerStockLocales.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) { //JCHAVEZ 08102009.sn
            lblF4.setVisible(true);
        } else if (VariablesPtoVenta.vIndVerReceMagis.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            lblF4.setVisible(true);
        } else {
            lblF4.setVisible(false);
        }

        try {
            lblDeliveryProv.setVisible(DBVentas.getIndVerPedidoDelivery());
        } catch (SQLException ex) {
            lblDeliveryProv.setVisible(false);
            log.error("", ex);
        }
        //JCHAVEZ 08102009.en

        VariablesRecetario.strCodigoRecetario = "";
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaPreciosProductos() {
        tableModelListaPrecioProductos =
                new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos,
                                    0);
        clonarListadoProductos();

        FarmaUtility.initSelectList(tblProductos, tableModelListaPrecioProductos,
                                    ConstantsVentas.columnsListaProductos);
        tblProductos.setName(ConstantsVentas.NAME_TABLA_PRODUCTOS);
        if (tableModelListaPrecioProductos.getRowCount() > 0)
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, COL_ORD_LISTA,
                                 FarmaConstants.ORDEN_ASCENDENTE);
    }

    private void initTableProductosSustitutos() {
        tblModelListaSustitutos =
                new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos,
                                    0);
        FarmaUtility.initSelectList(tblListaSustitutos, tblModelListaSustitutos,
                                    ConstantsVentas.columnsListaProductos);
        tblListaSustitutos.setName(ConstantsVentas.NAME_TABLA_SUSTITUTOS);
        muestraProductosSustitutos();
    }

    public void iniciaProceso(boolean pInicializar) {

        for (int i = 0; i < tblProductos.getRowCount(); i++)
            tblProductos.setValueAt(new Boolean(false), i, 0);

        for (int i = 0; i < VariablesVentas.tableModelListaGlobalProductos.getRowCount(); i++)
            VariablesVentas.tableModelListaGlobalProductos.setValueAt(new Boolean(false), i, 0);

        if (pInicializar) {
            VariablesVentas.vArrayList_PedidoVenta = new ArrayList();
            for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++)
                VariablesVentas.vArrayList_PedidoVenta.add((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
        }

        log.debug("VariablesVentas.vArrayList_PedidoVenta : " + VariablesVentas.vArrayList_PedidoVenta.size());
        ArrayList myArray = new ArrayList();
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++)
            myArray.add((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i));

        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++)
            myArray.add((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i));


        FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, myArray, 0);
        //if ( !pInicializar )
        actualizaListaProductosAlternativos();
        muestraNombreLab(4, lblDescLab_Prod);
        muestraProductoInafectoIgv(11, lblProdIgv);

        muestraProductoPromocion(17, lblProdProm);
        //
        muestraProductoRefrigerado(15, lblProdRefrig);
        muestraIndTipoProd(16, lblIndTipoProd);

        // JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);

        muestraInfoProd();
        muestraProductoCongelado(lblProdCong);
        colocaTotalesPedido();


    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {

        VariablesVentas.vValorMultiplicacion = "1"; //ASOSA - 12/08/2014

        // DUBILLUZ 04.02.2013
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();

        if (VariablesFidelizacion.vSIN_COMISION_X_DNI)
            lblDNI_SIN_COMISION.setVisible(true);
        else
            lblDNI_SIN_COMISION.setVisible(false);

        FarmaUtility.centrarVentana(this);
        vEjecutaAccionTeclaListado = false;
        VariablesVentas.vVentanaListadoProductos = true;

        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {
            //ImageIcon icon = new ImageIcon(this.getClass().getResource("logo_mf_btl.JPG"));
            this.setTitle("Lista de Productos y Precios " + VariablesConvenioBTLMF.vNomConvenio);
            log.debug("Nombre Cliente::" + VariablesConvenioBTLMF.vNomCliente);
            lblCliente.setText("" + VariablesConvenioBTLMF.vNomCliente);
            //lblMedicoT.setIcon(icon);
            //lblMedico.setText(" "+VariablesConvenioBTLMF.vNomConvenio );

        } else {
            evaluaTitulo();
        }
        //evaluaSeleccionMedico();
        lblProdIgv.setVisible(false);
        FarmaUtility.moveFocus(txtProducto);

        if (VariablesVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesVentas.vIndPedConProdVirtual = false;

        if (VariablesVentas.vKeyPress != null) {
            if (VariablesVentas.vCodBarra.trim().length() > 0) {
                txtProducto.setText(VariablesVentas.vCodBarra.trim());
                txtProducto_keyPressed(VariablesVentas.vKeyPress);
            } else if (VariablesVentas.vCodProdBusq.trim().length() > 0) {
                txtProducto.setText(VariablesVentas.vCodProdBusq.trim());
                txtProducto_keyPressed(VariablesVentas.vKeyPress);
            } else {
                txtProducto.setText(VariablesVentas.vKeyPress.getKeyChar() + "");
                txtProducto_keyReleased(VariablesVentas.vKeyPress);
            }
        }

        //JCORTEZ 17.04.08
        if (!VariablesVentas.vCodFiltro.equalsIgnoreCase("")) {
            cargaListaFiltro();
        }
        AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña, "L", txtProducto, this);
    }

    // ************************************************************************************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - Evitar le ejecución de 2 teclas a la vez al momento de comprometer stock
    // ************************************************************************************************************************************************

    private void txtProducto_keyPressed(KeyEvent e) {
        //ERIOS 15.01.2014 Correccion para lectura de escaner NCR
        //log.debug("Tecla: "+e.getKeyCode()+" ("+e.getKeyChar()+")");
        if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE ||
              e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT ||
              e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_HOME)) {
            e.consume();

        }

        try {

            FarmaGridUtils.aceptarTeclaPresionada(e, myJTable, txtProducto, 2);
            //log.debug("Caracter: "+String.valueOf(e.getKeyChar())+"   ASCII: "+String.valueOf(e.getKeyCode()));

            if (!vEjecutaAccionTeclaListado) {
                vEjecutaAccionTeclaListado = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    deshabilitaProducto();
                    String vCadenaOriginal = txtProducto.getText().trim();
                    log.debug("!!!!!!!!!!!!!Cadena Original:" + vCadenaOriginal);
                    //ERIOS 03.07.2013 Limpia la caja de texto
                    limpiaCadenaAlfanumerica(txtProducto);

                    //Agregado por FRAMIREZ 24/11/2011
                    //Si es la tarjeta de un cliente, va al modulo de convenio.
                    if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                        UtilityConvenioBTLMF.esTarjetaConvenio(txtProducto.getText())) {

                        if (VariablesConvenioBTLMF.vCodConvenio != null &&
                            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 1) {
                            if (JConfirmDialog.rptaConfirmDialog(this,
                                                                 "¿Esta seguro de cancelar el pedido por Convenio?")) {
                                VariablesConvenioBTLMF.limpiaVariablesBTLMF();
                                if (VariablesVentas.vArrayList_PedidoVenta.size() > 0) {
                                    FarmaUtility.showMessage(this,
                                                             "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                                            "no deben haber productos seleccionados. Verifique!!!", txtProducto);
                                } else {
                                    lblCliente.setText("");
                                    lblMedico.setText("");
                                    this.setTitle("Lista de Productos y Precios /  IP : " + FarmaVariables.vIpPc);
                                    evaluaTitulo();
                                    //jquispe 25.07.2011 se agrego la funcionalidad de listar las campañas sin fidelizar
                                    UtilityFidelizacion.operaCampañasFidelizacion(" ");
                                }
                            }
                        } else {
                            if (VariablesVentas.vArrayList_PedidoVenta.size() > 0) {
                                FarmaUtility.showMessage(this,
                                                         "Existen Productos Seleccionados. Para iniciar un pedido convenio\n" +
                                        "no deben haber productos seleccionados. Verifique!!!", txtProducto);
                            } else {
                                DlgListaConveniosBTLMF convenio = new DlgListaConveniosBTLMF(myParentFrame);
                                convenio.irIngresoDatosConvenio2(txtProducto);

                                if (VariablesConvenioBTLMF.vAceptar) {
                                    VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;

                                    //ImageIcon icon = new ImageIcon(this.getClass().getResource("logo_mf_btl.JPG"));
                                    UtilityConvenioBTLMF.cargarVariablesConvenio(VariablesConvenioBTLMF.vCodConvenio,
                                                                                 this, myParentFrame);

                                    log.debug("VariablesConvenioBTLMF.vCodConvenio:" +
                                              VariablesConvenioBTLMF.vCodConvenio);
                                    log.debug("VariablesConvenioBTLMF.vNomConvenio:" +
                                              VariablesConvenioBTLMF.vNomConvenio);

                                    evaluaTitulo();
                                    txtProducto.setText("");
                                    FarmaGridUtils.showCell(tblProductos, 0, 0);
                                    FarmaUtility.setearActualRegistro(tblProductos, txtProducto, 2);
                                    FarmaUtility.moveFocus(txtProducto);
                                } else {
                                    evaluaTitulo();
                                    VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = false;
                                }
                            }
                        }
                    } else {
                        log.info("Paso Tarjeta : " + pasoTarjeta);
                        if (pasoTarjeta) {
                            txtProducto.setText("");
                            pasoTarjeta = false;
                            return;
                        }

                        //String pCadenaOriginal = txtProducto.getText().trim();
                        //dubilluz 21.07.2011
                        setFormatoTarjetaCredito(txtProducto.getText().trim());
                        //String pCadenaNueva = txtProducto.getText().trim();
                        /*if(!pCadenaOriginal.trim().equalsIgnoreCase(pCadenaNueva.trim())&&pCadenaOriginal.trim().length()>0){
              pasoTarjeta = true;
              log.info("Es tarjeta...");
          }
          else{
              log.info("no es tarjeta");
              pasoTarjeta = false;
          }*/
                        log.info("pasoTarjeta:" + pasoTarjeta);


                        //ini-Agregado FRAMIREZ
                        /*if (VariablesConvenioBTLMF.vCodConvenio.trim().length() >
                            0 && pCadenaNueva.trim().length() == 6) {
                            String pAux =
                                DBConvenioBTLMF.getIndExcluidoConv(pCadenaNueva.trim());
                            if (pAux.equalsIgnoreCase("E")) {
                                log.debug("---Mensaje alerta convenios cobertura----");
                                DlgMensajeCobertura d =
                                    new DlgMensajeCobertura(null,
                                                            "Mensaje Covertura",
                                                            true);
                                d.setLocationRelativeTo(myParentFrame);
                                d.setVisible(true);
                            }
                        }*/
                        //fin-Agregado FRAMIREZ

                        try {
                            //mfajardo 29/04/09 validar ingreso de productos virtuales
                            if (!VariablesVentas.vProductoVirtual) {
                                e.consume();
                                if (myJTable.getSelectedRow() >= 0) {
                                    String productoBuscar = vCadenaOriginal;
                                    //txtProducto.getText().trim().toUpperCase();
                                    String cadena = txtProducto.getText().trim();

                                    //AGREGADO POR DVELIZ 26.09.08
                                    //Cambiar en el futuro esto por una consulta a base de datos.
                                    String formato = "";
                                    if (cadena.trim().length() > 6)
                                        formato = cadena.substring(0, 5);
                                    if (formato.equals("99999")) {
                                        if (UtilityFidelizacion.EsTarjetaFidelizacion(cadena)) {
                                            if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                                                FarmaUtility.showMessage(this, "No puede ingresar mas de una tarjeta.",
                                                                         txtProducto);
                                            else {
                                                validarClienteTarjeta(cadena);
                                                //VariablesFidelizacion.vNumTarjeta = cadena.trim();
                                                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                                                    UtilityFidelizacion.operaCampañasFidelizacion(cadena);

                                                //DAUBILLUZ -- Filtra los DNI anulados
                                                //25.05.2009
                                                VariablesFidelizacion.vDNI_Anulado =
                                                        UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
                                                VariablesFidelizacion.vAhorroDNI_x_Periodo =
                                                        UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,
                                                                                                       VariablesFidelizacion.vNumTarjeta);
                                                VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                                                        UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,
                                                                                                       VariablesFidelizacion.vNumTarjeta);

                                                AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña, "L",
                                                                                             txtProducto, this);
                                            }
                                            //vEjecutaAccionTeclaListado = false;
                                            return;
                                        } else {
                                            FarmaUtility.showMessage(this, "La tarjeta no es valida", null);
                                            txtProducto.setText("");
                                            FarmaUtility.moveFocus(txtProducto);
                                            //vEjecutaAccionTeclaListado = false;
                                            return;
                                        }
                                    }


                                    if (UtilityVentas.esCupon(cadena, this, txtProducto)) {
                                        //agregarCupon(cadena);//metodo reemplazado por lo nuevo
                                        //ERIOS 2.3.2 Valida convenio BTLMF
                                        if (VariablesVentas.vEsPedidoConvenio ||
                                            (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                                             VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)) {
                                            FarmaUtility.showMessage(this,
                                                                     "No puede agregar cupones a un pedido por convenio.",
                                                                     null);
                                            return;
                                        }
                                        validarAgregarCupon(cadena);

                                        //vEjecutaAccionTeclaListado = false;

                                        log.info("es CUpon :");
                                        return;
                                    }
                                    //21.08.2008 Daniel Veliz
                                    VariablesCampana.vFlag = false;

                                    //Dubilluz saber si ingreso una tarjeta y esta en una campaña automatica
                                    //para q aparezca la pantalla de fidelizacion
                                    //inicio dubilluz 15.07.2011
                                    boolean pIsTarjetaEnCampana =
                                        UtilityFidelizacion.isTarjetaPagoInCampAutomatica(cadena.trim());
                                    if (pIsTarjetaEnCampana) {
                                        log.info("es una tarjeta de pago q esta en una campana automatica:");
                                        validaIngresoTarjetaPagoCampanaAutomatica(cadena.trim());
                                        return;
                                    }
                                    //fin dubilluz 15.07.2011

                                    if (productoBuscar.length() == 0) {
                                        //vEjecutaAccionTeclaListado = false;
                                        return;
                                    }

                                    String codigo = "";
                                    // revisando codigo de barra

                                    char primerkeyChar = cadena.charAt(0);
                                    char segundokeyChar;

                                    if (cadena.length() > 1)
                                        segundokeyChar = cadena.charAt(1);
                                    else
                                        segundokeyChar = primerkeyChar;

                                    char ultimokeyChar = cadena.charAt(cadena.length() - 1);
                                    log.info("productoBuscar:" + cadena);
                                    if (cadena.length() > 6 &&
                                        (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) &&
                                                                                (!Character.isLetter(ultimokeyChar))))) {
                                        VariablesVentas.vCodigoBarra = cadena;

                                        log.info("consulta cod barra antes");
                                        productoBuscar = DBVentas.obtieneCodigoProductoBarra();

                                        log.info("consulta cod barra despues");
                                    }


                                    log.info("productoBuscar new:" + productoBuscar);
                                    //JCORTEZ 23.07.2008
                                    ///if (productoBuscar.equalsIgnoreCase("000000")&&UtilityVentas.esCupon(productoBuscar,this,txtProducto)) {
                                    if (productoBuscar.equalsIgnoreCase("000000")) {
                                        FarmaUtility.showMessage(this,
                                                                 "No existe producto relacionado con el Codigo de Barra. Verifique!!!",
                                                                 txtProducto);
                                        //vEjecutaAccionTeclaListado = false;
                                        return;
                                    }

                                    for (int k = 0; k < myJTable.getRowCount(); k++) {
                                        codigo = ((String)myJTable.getValueAt(k, COL_COD)).trim();
                                        if (codigo.equalsIgnoreCase(productoBuscar)) {
                                            FarmaGridUtils.showCell(myJTable, k, 0);
                                            //vEjecutaAccionTeclaListado = false;
                                            break;
                                        }
                                    }

                                    int vFila = myJTable.getSelectedRow();
                                    String actualCodigo = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                                    String actualProducto =
                                        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 2))).trim().toUpperCase();
                                    // Asumimos que codigo de producto ni codigo de barra empiezan con letra
                                    //if (Character.isLetter(primerkeyChar))
                                    /*{
                                    txtProducto.setText(actualProducto);
                                    productoBuscar = actualProducto;
                                }
                                txtProducto.setText(txtProducto.getText().trim());*/


                                    // Comparando codigo y descripcion de la fila actual con el txtProducto
                                    if ((actualCodigo.equalsIgnoreCase(productoBuscar) ||
                                         actualProducto.substring(0, productoBuscar.length()).equalsIgnoreCase(productoBuscar))) {
                                        //aqui

                                        btnBuscar.doClick();
                                        txtProducto.setText(actualProducto.trim());
                                        txtProducto.selectAll();
                                    } else if (productoBuscar.trim().length() <= DIG_PROD &&
                                               UtilityVentas.esCupon(productoBuscar, this, txtProducto)) {
                                        log.debug("productoBuscar.trim().length() " + productoBuscar.trim().length());

                                        FarmaUtility.showMessage(this,
                                                                 "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                                 txtProducto);
                                        //vEjecutaAccionTeclaListado = false;
                                        return;
                                    }

                                }
                            } // producto virtual
                            else {
                                FarmaUtility.showMessage(this, "Ya se selecciono un producto virtual", txtProducto);
                            } // producto virtual
                        } catch (SQLException sql) {
                            //log.error("",sql);
                            //vEjecutaAccionTeclaListado = false;
                            log.error(null, sql);
                            FarmaUtility.showMessage(this, "Error al buscar el Producto.\n" +
                                    sql, txtProducto);
                        }
                    }
                } else {
                    vEjecutaAccionTeclaListado = false;
                    chkKeyPressed(e);

                    //kmoncada
                    // PERMITE RECONOCER LA COMBINACION DE TECLAS ALT + S o ALT + R o ALT + P
                    if (e.getKeyCode() == 18 && contarCombinacion == 0) {
                        contarCombinacion++;
                    } else {
                        if (contarCombinacion == 1) {
                            switch (e.getKeyCode()) {
                            case 83:
                                if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {
                                    btnProdAlternativos.doClick();
                                }
                                break;
                            case 82:
                            case 80:
                                myJTable = tblProductos;
                                txtProducto.setText("");
                                FarmaUtility.setearActualRegistro(tblProductos, txtProducto, 2);
                                muestraInfoProd();
                                muestraProductoInafectoIgv(11, lblProdIgv);
                                muestraProductoCongelado(lblProdCong);
                                muestraProductoRefrigerado(15, lblProdRefrig);
                                muestraIndTipoProd(16, lblIndTipoProd);
                                muestraProductoPromocion(17, lblProdProm);
                                muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
                                break;
                                //DUBILLUZ 30.06.2014
                            case KeyEvent.VK_D:
                                lblPedDelivery();
                                break;
                            case KeyEvent.VK_SPACE:
                                abrePedidosDelivery();
                                break;


                            }
                        }
                        contarCombinacion = 0;
                    }
                }

            }
        } catch (Exception exc) {
            log.error("", exc);
            log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            vEjecutaAccionTeclaListado = false;
        }

        log.info("Fin Enter:" + pasoTarjeta);
    }

    /**
     * @Author    Daniel Fernando Veliz La Rosa
     * @Since     15.08.08
     */
    /* private void ingresarDatosCampana(){
      try{
          log.debug(VariablesCampana.vCodCampana);
          DlgListDatosCampana dlgListDatosCampana
                    = new DlgListDatosCampana(myParentFrame, "Datos Campaña", true);
          //FarmaUtility.centrarVentana(dlgListDatosCampana);
          dlgListDatosCampana.setVisible(true);
          if(FarmaVariables.vAceptar)
          {
            FarmaVariables.vAceptar = false ;

          }
      }catch(Exception e){
          log.error("",e);;
      }
  }*/
    //JQUISPE  03.05.2010 Cambio para modificar la busqueda de productos en la lista.
    private void txtProducto_keyReleased(KeyEvent e) {
        if (tblProductos.getRowCount() >= 0 && tableModelListaPrecioProductos.getRowCount() > 0 &&
            e.getKeyChar() != '+') {
            if (FarmaGridUtils.buscarDescripcion(e, myJTable, txtProducto, 2) ||
                (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_PAGE_UP) ||
                (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) ||
                e.getKeyCode() == KeyEvent.VK_ENTER) {
                VariablesVentas.vPosNew = tblProductos.getSelectedRow();
                if (VariablesVentas.vPosOld == 0 && VariablesVentas.vPosNew == 0) {
                    UpdateReleaseProd(e);
                    VariablesVentas.vPosOld = VariablesVentas.vPosNew;
                } else {
                    if (VariablesVentas.vPosOld != VariablesVentas.vPosNew) {
                        UpdateReleaseProd(e);
                        VariablesVentas.vPosOld = VariablesVentas.vPosNew;
                    }
                }

                /*muestraNombreLab(4, lblDescLab_Prod);
      muestraProductoInafectoIgv(11, lblProdIgv);
      muestraProductoRefrigerado(15,lblProdRefrig);
      muestraProductoPromocion(17,lblProdProm);
      muestraIndTipoProd(16,lblIndTipoProd);
      // JCORTEZ 08.04.2008
      muestraProductoEncarte(COL_IND_ENCARTE,lblProdEncarte);

      muestraInfoProd();
      muestraProductoCongelado(lblProdCong);
      if ( !(e.getKeyCode()==KeyEvent.VK_ESCAPE) ) {
        if ( myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS) ) {

          actualizaListaProductosAlternativos();
        }
      }
      colocaTotalesPedido();

      */
            }
        }


        ///---

        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

        } else {
            if (myJTable.getSelectedRow() >= 0) {
                if (isExisteProdCampana(myJTable.getValueAt(myJTable.getSelectedRow(), COL_COD).toString().trim())) {
                    lblMensajeCampaña.setVisible(true);
                } else {
                    // dubilluz 26.05.2009
                    AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña, "L", txtProducto, this);
                    //lblMensajeCampaña.setVisible(false);
                }
            }
        }
        contarCombinacion = 0; // kmoncada : contador de combinacion de teclas
        ///---

        //JCORTEZ 23.07.2008
        /*String cadena = txtProducto.getText().trim();
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(UtilityVentas.esCupon(cadena,this,txtProducto))
      {
        agregarCupon(cadena);
      }
    }*/
    }

    private void txtProducto_keyTyped(KeyEvent e) {
        //log.debug("e.getKeyCode() presionado:"+e.getKeyCode());
        //log.debug("e.getKeyChar() presionado:"+e.getKeyChar());
        //if (e.getKeyCode() == KeyEvent.VK_PLUS)
        if (e.getKeyChar() == '+') {
            e.consume();
            if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {
                btnProdAlternativos.doClick();
            } else //if(myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS_ALTERNATIVOS)) {
            {
                //btnRelacionProductos.doClick();
                //setJTable(tblProductos);
                myJTable = tblProductos;
                txtProducto.setText("");
                /*if(pJTable.getRowCount() > 0){
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
            muestraInfoProd();
        }*/
                FarmaUtility.setearActualRegistro(tblProductos, txtProducto, 2);
                muestraInfoProd();
                //FarmaUtility.moveFocus(txtProducto);

                muestraProductoInafectoIgv(11, lblProdIgv);
                muestraProductoCongelado(lblProdCong);
                muestraProductoRefrigerado(15, lblProdRefrig);
                muestraIndTipoProd(16, lblIndTipoProd);
                muestraProductoPromocion(17, lblProdProm);
                //JCORTEZ 08.04.2008
                muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
            }
        } else if (e.getKeyChar() == '-') {
            e.consume();
            String lblStock = lblStockAdic.getText().trim();

            if (!lblStock.equals("")) {
                int vFila = myJTable.getSelectedRow();
                Boolean valor = (Boolean)myJTable.getValueAt(vFila, 0);

                if (valor.booleanValue()) {
                    FarmaUtility.showMessage(this,
                                             "Para modificar la venta por tratamiento, debe deseleccionarlo primero.",
                                             txtProducto);
                } else {
                    int auxStk = FarmaUtility.trunc(FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_STOCK));
                    int auxStkFrac = FarmaUtility.trunc(lblStock);

                    if ((auxStk + auxStkFrac) > 0 ||
                        (validaStockDisponible() && !VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))) {
                        //if (validaVentaConMenos()) {
                        mostrarTratamiento();
                        aceptaOperacion();
                        //}
                    }
                }
            }
        }
        //LLEIVA - 21-Nov-2013 No se permite el espacio al inicio de la busqueda
        else if (e.getKeyChar() == ' ') {
            if (txtProducto.getText().length() == 0)
                e.consume();
            txtProducto.setText(txtProducto.getText().trim());
        }
        contarCombinacion = 0; // kmoncada : contador de combinacion de teclas
    }

    private void btnProdAlternativos_actionPerformed(ActionEvent e) {
        setJTable(tblListaSustitutos);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoCongelado(lblProdCong);
        muestraProductoRefrigerado(15, lblProdRefrig);
        muestraIndTipoProd(16, lblIndTipoProd);
        /**
     * Muestra Promocion
     * @author : dubilluz
     * @since  : 28.06.2007
     */
        muestraProductoPromocion(17, lblProdProm);

        //JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        setJTable(tblProductos);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoCongelado(lblProdCong);
        muestraProductoRefrigerado(15, lblProdRefrig);
        muestraIndTipoProd(16, lblIndTipoProd);
        /**
     * Muestra Promocion
     * @author : dubilluz
     * @since  : 28.06.2007
     */
        muestraProductoPromocion(17, lblProdProm);

        //JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        verificaCheckJTable();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    // **************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - evitar le ejecucion de 2 teclas a la vez
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        try {
            if (!vEjecutaAccionTeclaListado) {
                vEjecutaAccionTeclaListado = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                } else if (UtilityPtoVenta.verificaVK_F1(e)) {
                    muestraDetalleProducto();
                } else if (UtilityPtoVenta.verificaVK_F2(e)) {
                    /*if(esProductoFarma())
          {
            FarmaUtility.showMessage(this, "Operación no valida para productos del Tipo Farma", txtProducto);
            return;
          }
          muestraProductosComplementarios();*/
                    muestraProductosAlternativos();
                } else if (e.getKeyCode() == KeyEvent.VK_F6) {
                    cargaListaFiltro();
                } else if (e.getKeyCode() == KeyEvent.VK_F7) {
                    filtroGoogle();
                } else if (e.getKeyCode() == KeyEvent.VK_F8) {
                    //ingresaReceta();
                    //Dubilluz - 06.12.2011
                    ingresaMedicoFidelizado();
                } else if (e.getKeyCode() == KeyEvent.VK_F9) { //add jcallo 15/12/2008 campanias acumuladas.
                    //veririficar que el producto seleccionado tiene el flag de campanias acumuladas.
                    vEjecutaAccionTeclaListado = true;
                    //validar que no sea un pedido por convenio
                    if (VariablesVentas.vEsPedidoConvenio) {
                        FarmaUtility.showMessage(this,
                                                 "No puede asociar clientes a campañas de ventas acumuladas en un " +
                                                 "pedido por convenio.", txtProducto);
                        //     return;
                    } else { //toda la logica para asociar un cliente hacia campañas nuevas
                        //DUBILLUZ - 29.04.2010
                        if (VariablesFidelizacion.vDniCliente.trim().length() > 0) {
                            int rowSelec = myJTable.getSelectedRow();
                            if (rowSelec >= 0)
                            //&& myJTable.getModel().getValueAt(rowSelec,10).toString().equals("S")
                            { //validar si el producto seleccionado tiene alguna campaña asociada
                                String auxCodProd = myJTable.getValueAt(rowSelec, COL_COD).toString().trim();
                                asociarCampAcumulada(auxCodProd);
                            }
                        } else {
                            FarmaUtility.showMessage(this, "No puede ver las campañas:\n" +
                                    "Porque primero debe de fidelizar al cliente con la función F12.", txtProducto);
                        }

                    }

                    //JCALLO 19.12.2008 comentado sobre la opcion de ver pedidos delivery..y usarlo para el tema inscribir cliente a campañas acumuladas
                    /** JCALLO INHABILITAR F9 02.10.2008* **/
                    /*log.debug("HABILITAR F9 : "+VariablesVentas.HabilitarF9);

            if ( VariablesVentas.HabilitarF9.equalsIgnoreCase(
                    ConstantsVentas.ACTIVO) ) {
              if(UtilityVentas.evaluaPedidoDelivery(this, txtProducto, VariablesVentas.vArrayList_PedidoVenta)){
                evaluaTitulo();
                // Inicio Adicion Delivery 28/04/2006 Paulo
                if(VariablesVentas.vEsPedidoDelivery) generarPedidoDelivery();
                // Fin Adicion Delivery 28/04/2006 Paulo
              }
            }*/
                } else if (UtilityPtoVenta.verificaVK_F10(e)) {
                    e.consume();
                    faltacero();
                } else if (UtilityPtoVenta.verificaVK_F11(e)) {
                    VariablesVentas.vIndDireccionarResumenPed = true;
                    VariablesVentas.vIndF11 = true;
                    aceptaOperacion();
                } else if (UtilityPtoVenta.verificaVK_F12(e)) {
                    /*if(!VariablesVentas.vEsPedidoInstitucional)
          {
            if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro que desea iniciar una venta institucional?"))
            {
              if(UtilityVentas.evaluaPedidoInstitucional(this, txtProducto, VariablesVentas.vArrayList_PedidoVenta)){
                evaluaTitulo();
              }
            }
          }
          else
          {
            VariablesVentas.vEsPedidoInstitucional = false;
            evaluaTitulo();
          }*/
                    funcionF12("N");


                } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                    //if(!VariablesFidelizacion.vDniCliente.equalsIgnoreCase("") || VariablesFidelizacion. )
                    //vEjecutaAccionTeclaListado = false;
                    if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)) {
                        //Agregado FRAMIREZ - 20.10.2011
                        cargarConvenioBTL();
                    } else {
                        cargaConvenio();
                    }
                    /* if(UtilityVentas.evaluaPedidoConvenio(this, txtProducto, VariablesVentas.vArrayList_PedidoVenta)){
            evaluaTitulo();
            if(VariablesVentas.vEsPedidoConvenio)generarPedidoConvenio();
          }*/
                } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                    if (VariablesPtoVenta.vIndVerStockLocales.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        vEjecutaAccionTeclaListado = false;

                        //JCORTEZ 24.07.09 Se valida ingreso del Quimico (Admin Loca)
                        if (cargaValidaLogin()) {
                            cargaStockLocales();
                        }
                        /*if (!ValidaRolUsu("011"))
                        {
                             FarmaUtility.showMessage(this,
                                                        "Usted no cuenta con el rol adecuado.",
                                                        txtProducto);
                        }
                        else
                            cargaStockLocales();*/
                        else {
                            FarmaUtility.showMessage(this, "El local no puede usar esta opción.\nGracias.", null);
                        }
                    } else if (VariablesPtoVenta.vIndVerReceMagis.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        //LLEIVA 24-Junio-2013 - El producto virtual se debe vender solo, sin productos adicionales
                        if (VariablesVentas.vArrayList_PedidoVenta.size() > 0 ||
                            VariablesVentas.vArrayList_Prod_Promociones.size() > 0 ||
                            VariablesVentas.vArrayList_Prod_Promociones_temporal.size() > 0 ||
                            VariablesVentas.vArrayList_Promociones.size() > 0 ||
                            VariablesVentas.vArrayList_Promociones_temporal.size() > 0) {
                            FarmaUtility.showMessage(this,
                                                     "La venta de un Producto Virtual debe ser única por pedido. Verifique!!!",
                                                     txtProducto);
                        } else {
                            DlgListaGuiasRM dlgListaGuiasRM = new DlgListaGuiasRM(myParentFrame, "", true);
                            dlgListaGuiasRM.setVisible(true);

                            if (FarmaVariables.vAceptar) {
                                VariablesVentas.vCant_Ingresada = VariablesRecetario.strCant_Recetario;
                                VariablesVentas.vVal_Prec_Lista_Tmp = VariablesRecetario.strPrecioTotal;
                                VariablesVentas.vVal_Bruto_Ped = VariablesRecetario.strPrecioTotal;
                                VariablesVentas.vVal_Prec_Vta = VariablesRecetario.strPrecioTotal;
                                VariablesVentas.vVal_Prec_Lista = VariablesRecetario.strPrecioTotal;

                                seleccionaProducto();

                                VariablesVentas.venta_producto_virtual = true;
                                VariablesVentas.vIndDireccionarResumenPed = true;
                                FarmaVariables.vAceptar = false;
                            } else {
                                VariablesVentas.vIndDireccionarResumenPed = false;
                                VariablesVentas.vProductoVirtual = false;
                            }
                            aceptaOperacion();
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                    if (VariablesVentas.vEsPedidoConvenio ||
                        (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() >
                         1)) {

                        FarmaUtility.showMessage(this,
                                                 "No puede agregar estas promociones a un " + "pedido por convenio.",
                                                 txtProducto);
                        return;
                    } else {

                        //vEjecutaAccionTeclaListado = false;
                        int vFila = myJTable.getSelectedRow();
                        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
                        String indProm = (String)(myJTable.getValueAt(vFila, 17));

                        if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS))
                            VariablesVentas.vCodProdFiltro =
                                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                        else
                            VariablesVentas.vCodProdFiltro = "";

                        if (indProm.equalsIgnoreCase("S")) { //if(!valor.booleanValue())
                            muestraPromocionProd(VariablesVentas.vCodProdFiltro);
                            //else
                            //  FarmaUtility.showMessage(this,"El Producto está en una Promoción ya seleccionada",txtProducto);
                        } else
                            muestraPromocionProd(VariablesVentas.vCodProdFiltro);
                    }
                } /*else if(e.getKeyChar() == '*') {//add jcallo 15/12/2008 campanias acumuladas.
            //veririficar que el producto seleccionado tiene el flag de campanias acumuladas.

            //validar que no sea un pedido por convenio
            if(VariablesVentas.vEsPedidoConvenio)
            {
                  FarmaUtility.showMessage(this,
                                           "No puede asociar clientes a campañas de ventas acumuladas en un " +
                                           "pedido por convenio.", txtProducto);
             //     return;
            }else {//toda la logica para asociar un cliente hacia campañas nuevas

                int rowSelec = myJTable.getSelectedRow();
                if(rowSelec >= 0
                   //&& myJTable.getModel().getValueAt(rowSelec,10).toString().equals("S")
                    ){//validar si el producto seleccionado tiene alguna campaña asociada
                    String auxCodProd = myJTable.getValueAt(rowSelec, COL_COD).toString().trim();
                    asociarCampAcumulada(auxCodProd);

                }
            }

        }*/ else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    //vEjecutaAccionTeclaListado = false;
                    cancelaOperacion();
                    // Inicio Adicion Delivery 28/04/2006 Paulo
                    //limpiaVariables();
                    // Fin Adicion Delivery 28/04/2006 Paulo
                } else if (e.getKeyCode() == KeyEvent.VK_INSERT) { //Inicio ASOSA 02.02.2010 | 03.02.2010
                    VariablesVentas.vIndPrecioCabeCliente = "S";
                    DlgListaProdDIGEMID dlgDIGEMIT = new DlgListaProdDIGEMID(myParentFrame, "", true);
                    dlgDIGEMIT.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        cancelaOperacion();
                        cerrarVentana(true);
                    }
                } //Fin ASOSA 02.02.2010 | 03.02.2010
                //vEjecutaAccionTeclaListado = false;

            }

        } //try
        catch (Exception exc) {
            log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            vEjecutaAccionTeclaListado = false;
            log.debug(" finally: " + vEjecutaAccionTeclaListado);

        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesVentas.vVentanaListadoProductos = false;
        VariablesVentas.vIndDireccionarResumenPed = pAceptar;
        VariablesVentas.vKeyPress = null;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void muestraNombreLab(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            int vFila = myJTable.getSelectedRow();
            String nombreLab = FarmaUtility.getValueFieldJTable(myJTable, vFila, pColumna);
            pLabel.setText(nombreLab);
        }
    }

    private void muestraProductoInafectoIgv(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            int vFila = myJTable.getSelectedRow();
            String inafectoIgv = FarmaUtility.getValueFieldJTable(myJTable, vFila, pColumna);
            if (FarmaUtility.getDecimalNumber(inafectoIgv) == 0.00)
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }


    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        int vFila = myJTable.getSelectedRow();
        String codProd = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        //JMIRANDA 22/09/2009 lleno la variable vCod_Prod
        VariablesVentas.vCod_Prod = codProd;
        try {
            //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
            if (VariablesVentas.vEsPedidoConvenio) {
                DBVentas.obtieneInfoProducto(pArrayList, codProd);
            } else {
                DBVentas.obtieneInfoProductoVta(pArrayList, codProd);
                //log.debug("codProd"+codProd);
            }

        } catch (SQLException sql) {
            //log.error("",sql);
            log.error(null, sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtProducto);
        }
    }


    private void muestraInfoProd() {
        if (myJTable.getRowCount() <= 0)
            return;
        log.info("VariablesVentas.vCodigoBarra 05: " + VariablesVentas.vCodigoBarra);
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        log.debug("Tamaño en muestra info" + myArray.size());
        log.info("VariablesVentas.vCodigoBarra 06: " + VariablesVentas.vCodigoBarra);
        if (myArray.size() == 1) {
            log.info("VariablesVentas.vCodigoBarra 07: " + VariablesVentas.vCodigoBarra);
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            valPrecPres = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            indProdCong = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            valPrecVta = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            descUnidVta = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            indProdHabilVta = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
            porcDscto_1 = ((String)((ArrayList)myArray.get(0)).get(8)).trim();
            //log.info("DLGLISTAPRODUCTOS : porcDscto_1 - " + porcDscto_1);
            tipoProd = ((String)((ArrayList)myArray.get(0)).get(9)).trim();
            muestraIndTipoProd(16, lblIndTipoProd, tipoProd);
            bonoProd = ((String)((ArrayList)myArray.get(0)).get(10)).trim();
            stkFracLoc = FarmaUtility.getValueFieldArrayList(myArray, 0, 11);
            descUnidFracLoc = FarmaUtility.getValueFieldArrayList(myArray, 0, 12);
            estadoProd = ((String)((ArrayList)myArray.get(0)).get(13)).trim();
            tipoProducto = ((String)((ArrayList)myArray.get(0)).get(14)).trim(); //ASOSA - 02/10/2014 - PANHD
            VariablesVentas.tipoProducto = tipoProducto; //ASOSA - 09/10/2014 - PANHD
            log.info("VariablesVentas.vCodigoBarra 08: " + VariablesVentas.vCodigoBarra);


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
            stkFracLoc = "";
            descUnidFracLoc = "";
            estadoProd = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtProducto);
        }
        lblUnidad.setText(descUnidPres);
        lblPrecio.setText(valPrecPres);
        lblStockAdic.setText(stkFracLoc);
        lblUnidFracLoc.setText(descUnidFracLoc);
        myJTable.setValueAt(stkProd, myJTable.getSelectedRow(), 5);
        myJTable.setValueAt(valPrecVta, myJTable.getSelectedRow(), 6);
        myJTable.setValueAt(descUnidVta, myJTable.getSelectedRow(), 3);
        myJTable.setValueAt(bonoProd, myJTable.getSelectedRow(), 7);

        VariablesVentas.vVal_Frac = valFracProd;
        VariablesVentas.vInd_Prod_Habil_Vta = indProdHabilVta;
        VariablesVentas.vPorc_Dcto_1 = porcDscto_1;
        log.info("DLGLISTAPRODUCTOS : VariablesVentas.vPorc_Dcto_1 - " + porcDscto_1);
        myJTable.repaint();
    }

    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
            muestraInfoProd();
        }
        FarmaUtility.moveFocus(txtProducto);
    }

    private void muestraDetalleProducto() {
        vEjecutaAccionTeclaListado = false;
        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesVentas.vDesc_Prod = ((String)(myJTable.getValueAt(vFila, 2))).trim();
        VariablesVentas.vNom_Lab = ((String)(myJTable.getValueAt(vFila, 4))).trim();

        DlgDetalleProducto dlgDetalleProducto = new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);
    }

    private void muestraIngresoCantidad() {
        if (myJTable.getRowCount() == 0)
            return;
        FarmaVariables.vAceptar = true;
        //JMIRANDA 21/09/2009
        boolean flagContinua = true;

        //ERIOS 18.11.2014 Verifica si tiene encuesta a realizar.
        FacadeEncuesta facadeEncuesta = new FacadeEncuesta(myParentFrame); //TODO pensar en singleton
        facadeEncuesta.verificaEncuesta(VariablesVentas.vCod_Prod);

        try {
            //log.debug("vAceptar 1: " + FarmaVariables.vAceptar);

            // Verifica si es obligatorio ingresar codigo de barra
            //log.debug("vAceptar2 : " + FarmaVariables.vAceptar);
            //log.debug("vCod_Prod: " + VariablesVentas.vCod_Prod);
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
                //ENVIO vMensajeProd LLAMAR METODO RETORNAR SMENSAJE CON SALTO DE LINEA
                log.debug(sMensaje);
                //flagContinua = JConfirmDialog.rptaConfirmDialogDefaultNo(this,sMensaje);
                //flagContinua = com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,sMensaje);
                //FarmaVariables.vAceptar = flagContinua;
                //log.debug("Mensaje flagContinua: "+flagContinua);
                //JMIRANDA 26.03.2010
                FarmaUtility.showMessage(this, sMensaje, txtProducto);
            }

            // Valida ID Usuario
            String pInd = DBVentas.getIndSolIdUsu(VariablesVentas.vCod_Prod).trim().toUpperCase();
            //if(DBVentas.getIndSolIdUsu(VariablesVentas.vCod_Prod).equalsIgnoreCase("S")
            if (pInd.equalsIgnoreCase("S") && FarmaVariables.vAceptar) {
                //llamar a Usuario para visualizar
                flagContinua = validaLoginVendedor();
                FarmaVariables.vAceptar = flagContinua;
                log.debug("SolId. flagContinua: " + flagContinua);
                /*if (!flagContinua){
             } */
            } else {
                if (pInd.equalsIgnoreCase("J") && FarmaVariables.vAceptar) {
                    log.debug("*** Valida Producto Venta Cero");
                    //llamar a Usuario para visualizar
                    flagContinua = validaLoginQF();
                    FarmaVariables.vAceptar = flagContinua;
                    log.debug("SolId. flagContinua : " + flagContinua);
                    /*if (!flagContinua){
                 } */
                }
            }

            //ini-Agregado FRAMIREZ
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                VariablesConvenioBTLMF.vCodConvenio != null &&
                VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 &&
                VariablesVentas.vCod_Prod.trim().length() == 6) {
                /*String pAux =
                    DBConvenioBTLMF.getIndExcluidoConv(VariablesVentas.vCod_Prod.trim());*/

                log.debug("VariablesVentas.vEstadoProdConvenio: " + VariablesVentas.vEstadoProdConvenio);

                UtilityConvenioBTLMF.Busca_Estado_ProdConv(this);
                log.debug("VariablesVentas.vEstadoProdConvenio: " + VariablesVentas.vEstadoProdConvenio);
                if (!VariablesVentas.vEstadoProdConvenio.equalsIgnoreCase("I")) {
                    log.debug("---Mensaje alerta convenios cobertura----");
                    FarmaUtility.showMessage(this, "Producto no esta en cobertura del Convenio.", null);
                    flagContinua = false;
                    VariablesVentas.vIndDireccionarResumenPed = false;
                }


            }
            //fin-Agregado FRAMIREZ


        } catch (Exception sql) {
            FarmaUtility.showMessage(this, "Error en Validar Producto: " + sql, txtProducto);
            log.error("", sql);
        }

        if (flagContinua) {

            int vFila = myJTable.getSelectedRow();
            VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
            VariablesVentas.vDesc_Prod = ((String)(myJTable.getValueAt(vFila, 2))).trim();
            VariablesVentas.vNom_Lab = ((String)(myJTable.getValueAt(vFila, 4))).trim();
            VariablesVentas.vPorc_Igv_Prod = ((String)(myJTable.getValueAt(vFila, 11))).trim();
            VariablesVentas.vCant_Ingresada_Temp = "0";
            VariablesVentas.vNumeroARecargar = "";
            VariablesVentas.vVal_Prec_Lista_Tmp = "";
            VariablesVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
            VariablesVentas.vTipoProductoVirtual = "";
            VariablesVentas.vVal_Prec_Pub = ((String)(myJTable.getValueAt(vFila, 6))).trim();
            VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_ORIG_PROD);
            VariablesVentas.vPorc_Dcto_2 = "0";
            VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
            VariablesVentas.vCantxDia = "";
            VariablesVentas.vCantxDias = "";


            //Busca precio de convenio BTLMF
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                VariablesConvenioBTLMF.vCodConvenio != null &&
                VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 &&
                VariablesVentas.vCod_Prod.trim().length() == 6 && VariablesVentas.vEstadoProdConvenio.equals("I")) {

                VariablesConvenioBTLMF.vValidaPrecio = true;
                SubProcesosConvenios precConv = new SubProcesosConvenios();
                precConv.start();
            } else {

                guardaInfoProdVariables();
            }

            //INI ASOSA - 23/10/2014
            if (isProdInPack() && VariablesPtoVenta.vIndTico.equals("S")) //ASOSA - 24/10/2014
            {

                if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                              "El producto seleccionado puede comprarse en pack, \n¿Desea comprarlo en pack?")) {
                    lblF5_keyPressed(null);
                } else {
                    //FIN ASOSA - 23/10/2014
                    log.info("A PUNTO DE ABRIR LA PANTALLA DE CANTIDAD");
                    DlgIngresoCantidad dlgIngresoCantidad = new DlgIngresoCantidad(myParentFrame, "", true);
                    VariablesVentas.vIngresaCant_ResumenPed = false;
                    VariablesVentas.tipoLlamada = "0"; //ASOSA - 15/10/2014
                    dlgIngresoCantidad.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        seleccionaProducto();
                        VariablesVentas.vIndDireccionarResumenPed = true;
                        FarmaVariables.vAceptar = false;
                    } else {
                        VariablesVentas.vIndDireccionarResumenPed = false;
                    }
                }
            } else {
                log.info("A PUNTO DE ABRIR LA PANTALLA DE CANTIDAD");
                DlgIngresoCantidad dlgIngresoCantidad = new DlgIngresoCantidad(myParentFrame, "", true);
                VariablesVentas.vIngresaCant_ResumenPed = false;
                VariablesVentas.tipoLlamada = "0"; //ASOSA - 15/10/2014
                dlgIngresoCantidad.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    seleccionaProducto();
                    VariablesVentas.vIndDireccionarResumenPed = true;
                    FarmaVariables.vAceptar = false;
                } else {
                    VariablesVentas.vIndDireccionarResumenPed = false;
                }
            }

            VariablesVentas.vValorMultiplicacion = "1"; //ASOSA - 12/08/2014
        }

    }

    private void colocaTotalesPedido() {
        calculaTotalVentaPedido();
        totalItems =
                VariablesVentas.vArrayList_PedidoVenta.size() + VariablesVentas.vArrayList_Prod_Promociones_temporal.size() +
                VariablesVentas.vArrayList_Prod_Promociones.size();
        lblItems.setText("" + totalItems);
        lblTotalVenta.setText(FarmaUtility.formatNumber(totalVenta, 2));
    }

    /**
     * Calcula el monto total de venta
     * @author dubilluz
     * @since  18.06.2007
     */
    private void calculaTotalVentaPedido() {
        totalVenta = 0;
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++)
            totalVenta +=
                    FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(7)).trim());

        double totalProd_Prom = 0.00;

        ArrayList aux = new ArrayList();
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size(); i++) {
            aux = (ArrayList)VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i);
            //log.debug(FarmaUtility.getDecimalNumber(""+aux.get(6))+"");
            totalProd_Prom += FarmaUtility.getDecimalNumber("" + aux.get(7));
        }

        ArrayList aux2 = new ArrayList();
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
            aux2 = (ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i);
            //log.debug(FarmaUtility.getDecimalNumber(""+aux.get(6))+"");
            totalProd_Prom += FarmaUtility.getDecimalNumber("" + aux2.get(7));
        }

        totalVenta += totalProd_Prom;
    }

    private void verificaCheckJTable() {
        log.info("VariablesVentas.vCodigoBarra 01: " + VariablesVentas.vCodigoBarra);
        int vFila = myJTable.getSelectedRow();
        String codigo = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        log.info("VariablesVentas.vCodigoBarra 02: " + VariablesVentas.vCodigoBarra);
        if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS))
            actualizaListaProductosAlternativos();
        log.info("VariablesVentas.vCodigoBarra 03: " + VariablesVentas.vCodigoBarra);
        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
        if (valor.booleanValue()) {
            if (!buscar(VariablesVentas.vArrayList_Prod_Promociones, codigo) &&
                !buscar(VariablesVentas.vArrayList_Prod_Promociones_temporal, codigo)) {
                deseleccionaProducto();
            } else {
                FarmaUtility.showMessage(this, "El Producto se encuentra en una Promoción", txtProducto);
                return;
            }
        } else {
            log.info("VariablesVentas.vCodigoBarra 04: " + VariablesVentas.vCodigoBarra);
            muestraInfoProd();
            VariablesVentas.vIndProdVirtual = FarmaUtility.getValueFieldJTable(myJTable, vFila, 13);

            // KMONCADA 02.07.2014 VALIDA ESTADO DEL PRODUCTO
            if (!"A".equalsIgnoreCase(estadoProd)) {
                FarmaUtility.showMessage(this, "El Producto se encuentra inactivo en local.", txtProducto);
                return;
            }

            if (!validaStockDisponible() &&
                !VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //INI ASOSA - 02/10/2014 - PANHD
                if (!isProductoFinal()) {
                    return;
                }
                //FIN ASOSA - 02/10/2014
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

            VariablesVentas.vIndProdVirtual = FarmaUtility.getValueFieldJTable(myJTable, vFila, 13);

            String tipoProdVirtual = FarmaUtility.getValueFieldJTable(myJTable, myJTable.getSelectedRow(), 14);
            /*            if (VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
                VariablesVentas.vIndPedConProdVirtual) {
*/
            //kmoncada: cambio para balanza kieto
            if ((VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
                 VariablesVentas.vIndPedConProdVirtual) && !"B".equalsIgnoreCase(tipoProdVirtual)) {
                //ERIOS 2.4.4 No se permite venta de recarga virtuales por convenios
                if (VariablesVentas.vEsPedidoConvenio ||
                    (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() >
                     1)) {

                    FarmaUtility.showMessage(this, "No puede realizar recargas virtuales en " + "pedido por convenio.",
                                             txtProducto);
                    return;
                }
                //ERIOS 2.4.4 No se permite venta de recarga virtuales por delivery
                if (VariablesVentas.vEsPedidoDelivery) {

                    FarmaUtility.showMessage(this, "No puede realizar recargas virtuales en " + "pedido por delivery.",
                                             txtProducto);
                    return;
                }
                //Modificado para que no pueda comprar Nada si hay Promociones
                if (VariablesVentas.vArrayList_PedidoVenta.size() > 0 ||
                    VariablesVentas.vArrayList_Prod_Promociones.size() > 0 ||
                    VariablesVentas.vArrayList_Prod_Promociones_temporal.size() > 0 ||
                    VariablesVentas.vArrayList_Promociones.size() > 0 ||
                    VariablesVentas.vArrayList_Promociones_temporal.size() > 0) {
                    FarmaUtility.showMessage(this,
                                             "La venta de un Producto Virtual debe ser única por pedido. Verifique!!!",
                                             txtProducto);
                    return;
                } else {
                    VariablesVentas.vIndProdControlStock = false;
                    VariablesVentas.vIndPedConProdVirtual = true;
                    evaluaTipoProdVirtual();
                    VariablesVentas.vIndPedConProdVirtual = false;
                }
            } else {
                //kmoncada balanza keito
                if ((VariablesVentas.vArrayList_PedidoVenta.size() > 0 ||
                     VariablesVentas.vArrayList_Prod_Promociones.size() > 0 ||
                     VariablesVentas.vArrayList_Prod_Promociones_temporal.size() > 0 ||
                     VariablesVentas.vArrayList_Promociones.size() > 0 ||
                     VariablesVentas.vArrayList_Promociones_temporal.size() > 0) &&
                    "B".equalsIgnoreCase(tipoProdVirtual)) {
                    FarmaUtility.showMessage(this,
                                             "La venta de un Producto Virtual debe ser única por pedido. Verifique!!!",
                                             txtProducto);
                    return;
                }

                //kmoncada mostrar pantalla en caso de producto Balanza Keito (Prod Virtual)
                log.info("Resultado de balanza " + ("B".equalsIgnoreCase(tipoProdVirtual)));
                //VariablesVentas.vIndProdControlStock = true;
                VariablesVentas.vIndProdControlStock = !("B".equalsIgnoreCase(tipoProdVirtual)); //
                //
                VariablesVentas.vIndPedConProdVirtual = false;

                muestraIngresoCantidad();

                //kmoncada si ha seleccionado producto evalua si se trata de balanza keito y lo marca como prod virtual
                if (VariablesVentas.vArrayList_PedidoVenta.size() > 0 ||
                    VariablesVentas.vArrayList_Prod_Promociones.size() > 0 ||
                    VariablesVentas.vArrayList_Prod_Promociones_temporal.size() > 0 ||
                    VariablesVentas.vArrayList_Promociones.size() > 0 ||
                    VariablesVentas.vArrayList_Promociones_temporal.size() > 0) {
                    if (!VariablesVentas.vProductoVirtual && ("B".equalsIgnoreCase(tipoProdVirtual))) {
                        VariablesVentas.vProductoVirtual = true;
                    }
                }
            }
        }

        //txtProducto.selectAll();
        muestraNombreLab(4, lblDescLab_Prod);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoCongelado(lblProdCong);
        muestraProductoRefrigerado(15, lblProdRefrig);
        /**
     * Diego
     */
        muestraProductoPromocion(17, lblProdProm);
        muestraIndTipoProd(16, lblIndTipoProd);
        //JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);

        aceptaOperacion();

    }

    private void seleccionaProducto() {
        int vFila = myJTable.getSelectedRow();
        Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));

        double auxCantIng = FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada);
        int aux2CantIng = (int)auxCantIng;
        double auxPrecVta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
        VariablesVentas.vTotalPrecVtaProd = (auxCantIng * auxPrecVta);
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (VariablesVentas.vIndProdControlStock &&
            /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                   aux2CantIng,
                                                   ConstantsVentas.INDICADOR_A,
                                                   ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                   aux2CantIng,
                                                   true,
                                                   this,
                                                   txtProducto,))*/
            !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                //ASOSA, 01.07.2010
                aux2CantIng, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, aux2CantIng,
                true, this, txtProducto, ""))
            return;

        FarmaUtility.setCheckValue(myJTable, false);
        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
        UtilityVentas.operaProductoSeleccionadoEnArrayList_02(valor, VariablesVentas.secRespStk); //ASOSA, 01.07.2010
        pintaCheckOtroJTable(myJTable, valorTmp);
        colocaTotalesPedido();
    }

    private void deseleccionaProducto() {
        String cantidad = "";
        int vFila = myJTable.getSelectedRow();

        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        String indicadorControlStock = FarmaConstants.INDICADOR_S;
        String codigoTmp = "";

        String secRespaldo = ""; //ASOSA, 01.07.2010

        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++) {
            codigoTmp = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(0);
            if (VariablesVentas.vCod_Prod.equalsIgnoreCase(codigoTmp)) {
                indicadorControlStock =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 16);
                cantidad = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(4);
                secRespaldo =
                        (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(26); //ASOSA, 01.07.2010
                VariablesVentas.vVal_Frac =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 10);
                break;
            }
        }

        int aux2CantIng = Integer.parseInt(cantidad);

        Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (indicadorControlStock.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
            /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                    aux2CantIng,
                                                    ConstantsVentas.INDICADOR_A,
                                                    ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                    aux2CantIng,
                                                    true,
                                                    this,
                                                    txtProducto,))*/
            !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                //ASOSA, 01.07.2010
                0, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, 0, true, this,
                txtProducto, secRespaldo)) {
            return;
        }

        FarmaUtility.setCheckValue(myJTable, false);
        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
        UtilityVentas.operaProductoSeleccionadoEnArrayList_02(valor, VariablesVentas.secRespStk); //ASOSA, 01.07.2010

        if (VariablesVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesVentas.vIndPedConProdVirtual = false;

        pintaCheckOtroJTable(myJTable, valorTmp);
        //indicadorItems = FarmaConstants.INDICADOR_N;
        colocaTotalesPedido();
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
        else {
            if (FarmaUtility.isInteger(stkProd) && Integer.parseInt(stkProd) == 0 &&
                (VariablesVentas.vEsPedidoDelivery && UtilityVentas.getIndVtaNegativa()))
                return true;
            else
                return false;
        }
    }


    /**
     * Metodo para determinar si el producto es un producto final y tiene stock.
     * @author ASOSA
     * @since 02/10/2014
     * @return
     */
    private boolean isProductoFinal() {
        boolean flag = false;
        if (tipoProducto.trim().equals(ConstantsVentas.TIPO_PROD_FINAL)) {
            String[] list = UtilityVentas.obtenerInfoRecetaProdFinal(VariablesVentas.vCod_Prod);
            String codRpta = list[0];
            String descRpta = list[1];
            if (codRpta.equals("S")) {
                flag = true;
            } else {
                FarmaUtility.showMessage(this, descRpta, null);
            }
        }
        return flag;
    }

    private void cancelaOperacion() {
        String codProd = "";
        String codProdTmp = "";
        String indControlStk = "";
        boolean existe = false;

        String secRespaldo = ""; //ASOSA, 01.07.2010

        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++) {
            codProd = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(0));
            VariablesVentas.vVal_Frac =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 10);
            String cantidad = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(4));
            indControlStk = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_PedidoVenta, i, 16);

            secRespaldo =
                    (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(26)); //ASOSA, 01.07.2010

            for (int j = 0; j < VariablesVentas.vArrayList_ResumenPedido.size(); j++) {
                codProdTmp = (String)(((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(j)).get(0));

                if (codProd.equalsIgnoreCase(codProdTmp)) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                int aux2CantIng = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad));
                VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                        aux2CantIng,
                                                        ConstantsVentas.INDICADOR_A,
                                                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                        aux2CantIng,
                                                        true,
                                                        this,
                                                        txtProducto,))*/
                    !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                        //ASOSA, 01.07.2010
                        aux2CantIng, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                        aux2CantIng, true, this, txtProducto, secRespaldo))
                    return;
            }

            existe = false;
        }

        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
            codProd = (String)(((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(0));
            VariablesVentas.vVal_Frac =
                    FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 10);
            String cantidad = (String)(((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(4));
            String cantidadTmp = "0";
            indControlStk = FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_ResumenPedido, i, 16);

            secRespaldo =
                    (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(26)); //ASOSA, 01.07.2010

            log.debug("", existe);

            for (int j = 0; j < VariablesVentas.vArrayList_PedidoVenta.size(); j++) {
                codProdTmp = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(j)).get(0));

                if (codProd.equalsIgnoreCase(codProdTmp)) {
                    existe = true;
                    cantidadTmp = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(j)).get(4));
                    break;
                }
            }
            log.debug("", existe);

            if (!existe) {
                int aux2CantIng = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad));
                VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                        aux2CantIng,
                                                        ConstantsVentas.INDICADOR_A,
                                                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                        aux2CantIng,
                                                        true,
                                                        this,
                                                        txtProducto,))*/
                    !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                        //ASOSA, 01.07.2010
                        aux2CantIng, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                        aux2CantIng, true, this, txtProducto, secRespaldo))
                    return;
            } else {
                int aux3CantIng = Integer.parseInt(cantidad);
                int aux4CantIngTmp = Integer.parseInt(cantidadTmp);

                if (aux3CantIng < aux4CantIngTmp) {
                    int aux5CantIng = aux4CantIngTmp - aux3CantIng;
                    VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                    if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                        /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                          aux2CantIng,
                                                          ConstantsVentas.INDICADOR_A,
                                                          ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                          aux2CantIng,
                                                          true,
                                                          this,
                                                          txtProducto,))*/
                        !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                            //ASOSA, 01.07.2010
                            aux5CantIng, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                            aux3CantIng, true, this, txtProducto, secRespaldo))
                        return;
                } else if (aux3CantIng > aux4CantIngTmp) {
                    int aux5CantIng = aux3CantIng - aux4CantIngTmp;
                    VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                    if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                        /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                          aux2CantIng,
                                                          ConstantsVentas.INDICADOR_A,
                                                          ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                          aux2CantIng,
                                                          true,
                                                          this,
                                                          txtProducto,))*/
                        !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                            //ASOSA, 01.07.2010
                            aux5CantIng, ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                            aux3CantIng, true, this, txtProducto, secRespaldo))
                        return;
                }
            }
            existe = false;
        }

        cancela_promociones();
        cerrarVentana(false);
    }

    private void aceptaOperacion() {
        log.info("ENTRO A ACEPTAR OPERACION");
        log.debug("<<TCT 3>> Genera Resumen de Pedido ITEMS= " + VariablesVentas.vArrayList_PedidoVenta.size());
        vEjecutaAccionTeclaListado = false;
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
        //log.debug(VariablesVentas.vArrayList_ResumenPedido.size());

        /*
             * Solo se cerrara si el indicador lo permite
             * dubilluz 11.04.2008
             */
        if (VariablesVentas.vIndDireccionarResumenPed)
            cerrarVentana(true);
        log.debug("<<TCT 3>> Despues de Carga Resumen Hay Prom= " + VariablesVentas.vArrayList_Promociones.size());

    }


    private void cargaListaFiltro() {
        //ERIOS 29.08.2013 Al filtrar, se muestra "[ F7 ] Quitar Filtro"
        vEjecutaAccionTeclaListado = false;

        if (VariablesVentas.vCodFiltro.equalsIgnoreCase("")) {
            DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(myParentFrame, "", true);
            dlgFiltroProductos.setVisible(true);
        } else {
            muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            log.debug("VariablesPtoVenta.vTipoFiltro  : " + VariablesVentas.vCodFiltro);
            VariablesPtoVenta.vTipoFiltro = VariablesVentas.vCodFiltro;
            VariablesPtoVenta.vCodFiltro = "";
            //polimorfismo
            /*VariablesVentas.vCodFiltro_temp=VariablesVentas.vCodFiltro;
      if(!VariablesVentas.vCodFiltro_temp.equalsIgnoreCase("")){
        filtrarTablaProductos(VariablesVentas.vCodFiltro_temp,VariablesPtoVenta.vTipoFiltro,VariablesPtoVenta.vCodFiltro);
      }*/
            FarmaVariables.vAceptar = true;
        }

        if (FarmaVariables.vAceptar) {
            tblModelListaSustitutos.clearTable();
            txtProducto.setText("");

            filtrarTablaProductos(VariablesPtoVenta.vTipoFiltro, VariablesPtoVenta.vCodFiltro);
            setJTable(tblProductos);
            //iniciaProceso(false);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_PedidoVenta, 0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_Prod_Promociones_temporal,
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_Prod_Promociones, 0);

            FarmaVariables.vAceptar = false;

            lblF7.setText("[ F7 ] Quitar Filtro");
        }

        VariablesPtoVenta.vTipoFiltro = "";
        VariablesPtoVenta.vCodFiltro = "";
        VariablesVentas.vCodFiltro = "";
    }


    private void filtrarTablaProductos(String pTipoFiltro, String pCodFiltro) {
        try {
            tableModelListaPrecioProductos.clearTable();
            tableModelListaPrecioProductos.fireTableDataChanged();
            DBVentas.cargaListaProductosVenta_Filtro(tableModelListaPrecioProductos, pTipoFiltro, pCodFiltro);
            if (tableModelListaPrecioProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, COL_ORD_LISTA,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            //else
            //FarmaUtility.showMessage(this, "Resultado vacio", null);
        } catch (SQLException sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(this, "Error al obtener Lista de Productos Filtrado!!!", txtProducto);
        }
    }

    private void muestraProductosAlternativos() {
        vEjecutaAccionTeclaListado = false;
        int vFila = tblProductos.getSelectedRow();

        VariablesVentas.vCodProdOrigen_Alter = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
        //VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos,vFila,1);
        VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 2);
        VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 4);
        VariablesVentas.vUnid_Vta = lblUnidad.getText();

        DlgProductosAlternativos dlgProductosAlternativos = new DlgProductosAlternativos(myParentFrame, "", true);
        dlgProductosAlternativos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesVentas.vIndDireccionarResumenPed = true;
            aceptaOperacion();
            /*log.debug("Se refresca el listado temporal");
      FarmaVariables.vAceptar = false;

      FarmaUtility.ponerCheckJTable(tblProductos,COL_COD,VariablesVentas.vArrayList_PedidoVenta,0);
      FarmaUtility.ponerCheckJTable(tblProductos,COL_COD,VariablesVentas.vArrayList_Prod_Promociones_temporal,0);
      FarmaUtility.ponerCheckJTable(tblProductos,COL_COD,VariablesVentas.vArrayList_Prod_Promociones,0);

      colocaTotalesPedido();

      setJTable(tblProductos);
      FarmaUtility.setearPrimerRegistro(tblProductos,txtProducto,2);
      actualizaListaProductosAlternativos();
      muestraNombreLab(4, lblDescLab_Prod);
      muestraProductoInafectoIgv(11, lblProdIgv);
      muestraProductoPromocion(17,lblProdProm);
      muestraProductoRefrigerado(15,lblProdRefrig);
      muestraIndTipoProd(16,lblIndTipoProd);
      muestraInfoProd();
      muestraProductoCongelado(lblProdCong);*/

        } else {
            VariablesVentas.vIndDireccionarResumenPed = false;
        }

    }

    private void actualizaListaProductosAlternativos() {
        tblModelListaSustitutos.clearTable();
        tblModelListaSustitutos.fireTableDataChanged();
        //ERIOS 09.04.2008 Se muestra los sustitutos para todos los productos.
        //if(esProductoFarma()){
        //muestraProductosAlternativos();
        muestraProductosSustitutos();
        FarmaUtility.ponerCheckJTable(tblListaSustitutos, COL_COD, VariablesVentas.vArrayList_PedidoVenta, 0);
        FarmaUtility.ponerCheckJTable(tblListaSustitutos, COL_COD,
                                      VariablesVentas.vArrayList_Prod_Promociones_temporal, 0);
        FarmaUtility.ponerCheckJTable(tblListaSustitutos, COL_COD, VariablesVentas.vArrayList_Prod_Promociones, 0);
        //}
    }

    private void pintaCheckOtroJTable(JTable pActualJTable, Boolean pValor) {
        //log.debug(pValor.booleanValue());
        if (pActualJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {
            FarmaUtility.setearCheckInRow(tblListaSustitutos, pValor, true, true, VariablesVentas.vCod_Prod, COL_COD);
            tblListaSustitutos.repaint();
        } else if (pActualJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_SUSTITUTOS)) {
            FarmaUtility.setearCheckInRow(tblProductos, pValor, true, true, VariablesVentas.vCod_Prod, COL_COD);
            tblProductos.repaint();
        }
    }

    private void muestraProductosComplementarios() {
        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();
        VariablesVentas.vCodProdOrigen_Comple = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesVentas.vDescProdOrigen_Comple = ((String)(myJTable.getValueAt(vFila, 2))).trim();

        DlgProductosComplementarios dlgProductosComplementarios =
            new DlgProductosComplementarios(myParentFrame, "", true);
        dlgProductosComplementarios.setVisible(true);
        if (FarmaVariables.vAceptar) {
            log.debug("VariablesVentas.vCodProdComplementario : " + VariablesVentas.vCodProdComplementario);
            btnRelacionProductos.doClick();
            buscaCodigoEnJtable(VariablesVentas.vCodProdComplementario);
            FarmaVariables.vAceptar = false;
            VariablesVentas.vIndDireccionarResumenPed = true;
        } else
            VariablesVentas.vIndDireccionarResumenPed = false;
    }

    private void buscaCodigoEnJtable(String pCodBusqueda) {
        String codTmp = "";
        for (int i = 0; i < tblProductos.getRowCount(); i++) {
            codTmp = FarmaUtility.getValueFieldJTable(myJTable, i, COL_COD);
            if (codTmp.equalsIgnoreCase(pCodBusqueda)) {
                FarmaGridUtils.showCell(tblProductos, i, 0);
                FarmaUtility.setearRegistro(tblProductos, i, txtProducto, 2);
                FarmaUtility.moveFocus(txtProducto);
                return;
            }
        }
    }

    private void evaluaTitulo() {
        if (VariablesVentas.vEsPedidoDelivery) {
            this.setTitle("Lista de Productos y Precios - Pedido Delivery" + " /  IP : " + FarmaVariables.vIpPc);
        } else if (VariablesVentas.vEsPedidoInstitucional) {
            lblCliente.setText("");
            this.setTitle("Lista de Productos y Precios - Pedido Institucional" + " /  IP : " + FarmaVariables.vIpPc);
        } else if (VariablesVentas.vEsPedidoConvenio) {
            lblCliente.setText("");
            lblCliente.setText(VariablesConvenio.vTextoCliente);
            this.setTitle("Lista de Productos y Precios - Pedido por Convenio: " + VariablesConvenio.vNomConvenio +
                          " /  IP : " + FarmaVariables.vIpPc);
        } else if (VariablesConvenioBTLMF.vCodConvenio != null &&
                   VariablesConvenioBTLMF.vCodConvenio.trim().length() > 1) {
            lblCliente.setText("");
            lblCliente.setText(VariablesConvenioBTLMF.vNomCliente);
            this.setTitle("Lista de Productos y Precios - Pedido por Convenio: " +
                          VariablesConvenioBTLMF.vNomConvenio + " /  IP : " + FarmaVariables.vIpPc);
        } else {
            VariablesConvenio.vCodConvenio = "";
            VariablesConvenio.vArrayList_DatosConvenio.clear();
            // Solo si el no se ingreso tarjeta
            if (VariablesFidelizacion.vNumTarjeta.trim().length() == 0)
                lblCliente.setText("");
            if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente +
                                   " " + VariablesFidelizacion.vApeMatCliente);

            this.setTitle("Lista de Productos y Precios" + " /  IP : " + FarmaVariables.vIpPc + " / " +
                          FrmEconoFar.tituloBaseFrame);
        }
    }

    private void evaluaSeleccionMedico() {
        if (VariablesVentas.vSeleccionaMedico) {
            lblF8.setText("[F8] Des. Medico");
        } else {
            lblF8.setText("[F8] Sel. Medico");
        }
    }

    private boolean esProductoFarma() {
        int vFila = myJTable.getSelectedRow();
        String indicador = FarmaUtility.getValueFieldJTable(myJTable, vFila, 12);

        if (indicador.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }
    // Inicio Adicion Delivery 28/04/2006 Paulo
    //JCORTEZ  07.08.09 Aun falta completar funcionalidad de mantenimiento de clientes.

    private void generarPedidoDelivery() {
        DlgListaClientes dlgListaClientes = new DlgListaClientes(myParentFrame, "", true);
        dlgListaClientes.setVisible(true);
        log.debug("Indicador Pedido Delivery-->" + VariablesVentas.vEsPedidoDelivery);
        if (FarmaVariables.vAceptar) {
            String nombreCliente =
                VariablesDelivery.vNombreCliente + " " + VariablesDelivery.vApellidoPaterno + " " + VariablesDelivery.vApellidoMaterno;
            lblCliente.setText(nombreCliente);
            log.debug("************DATOS CLIENTE DELIVERY*******");
            VariablesDelivery.vNombreCliente = nombreCliente; //Se setea el valor mostrado en pantalla
            log.debug("CodCliente: " + VariablesDelivery.vCodCli);
            log.debug("Nombre: " + VariablesDelivery.vNombreCliente);
            log.debug("Nunero Telf: " + VariablesDelivery.vNumeroTelefonoCabecera);
            log.debug("Direccion: " + VariablesDelivery.vDireccion);
            log.debug("Nro Documento: " + VariablesDelivery.vNumeroDocumento);
            FarmaVariables.vAceptar = false;
            VariablesVentas.vEsPedidoDelivery = true;
            FarmaUtility.moveFocus(txtProducto);
            log.debug("Indicador Pedido Delivery-->" + VariablesVentas.vEsPedidoDelivery);
        } else {
            if (FarmaVariables.vImprTestigo.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                evaluaTitulo();
                VariablesVentas.vEsPedidoDelivery = false;
            }
        }
    }

    private void generarPedidoConvenio() {

        DlgListaConvenios dlgListaConvenios = new DlgListaConvenios(myParentFrame, "", true);
        dlgListaConvenios.setVisible(true);
        if (FarmaVariables.vAceptar) {

            log.debug("VariablesConvenio.vArrayList_DatosConvenio Lista Productos: " +
                      VariablesConvenio.vArrayList_DatosConvenio);
            String nombreCliente = VariablesConvenio.vArrayList_DatosConvenio.get(COL_DESC_PROD).toString();
            String apellidoPat = VariablesConvenio.vArrayList_DatosConvenio.get(3).toString();
            String apellidoMat = VariablesConvenio.vArrayList_DatosConvenio.get(4).toString();
            lblCliente.setText(nombreCliente + " " + apellidoPat + " " + apellidoMat);
            FarmaVariables.vAceptar = false;
            VariablesVentas.vEsPedidoConvenio = true;

            //jquispe para borrar las campañas cargadas desde el inicio cuando se trata de un campañas sin fidelizar
            //borro
            VariablesFidelizacion.vListCampañasFidelizacion.clear();
            VariablesVentas.vArrayList_Cupones.clear();
        } else {
            if (FarmaVariables.vImprTestigo.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                evaluaTitulo();
                VariablesVentas.vEsPedidoConvenio = false;
            }

        }
    }

    //inicio adicion Paulo 15/06/2006

    private void faltacero() {
        vEjecutaAccionTeclaListado = false;
        if (!validaStockDisponible()) {
            int vFila = myJTable.getSelectedRow();
            VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);

            if (!isExistProdListaFaltaCero(VariablesVentas.vListaProdFaltaCero, VariablesVentas.vCod_Prod)) {
                try {
                    DBVentas.ingresaStockCero();
                    FarmaUtility.aceptarTransaccion();
                    VariablesVentas.vListaProdFaltaCero.add(VariablesVentas.vCod_Prod);
                    FarmaUtility.showMessage(this, "Se ha Registrado el Producto para reposicion", txtProducto);
                } catch (SQLException sqlException) {
                    FarmaUtility.liberarTransaccion();
                    if (sqlException.getErrorCode() == 00001) {
                        FarmaUtility.showMessage(this, "Ya registró el producto con Falta Cero.!", txtProducto);
                    } else
                        FarmaUtility.showMessage(this, "Error al insertar en la tabla falta stock.\n" +
                                sqlException, txtProducto);
                    log.error(null, sqlException);
                }
            } else {
                FarmaUtility.showMessage(this, "Ya registró el producto con Falta Cero!", txtProducto);
            }
        }
    }
    //fin adicion Paulo 15/06/2006
    //inicio adicion paulo 23/06/2006

    private void muestraBuscaMedico() {
        DlgBuscaMedico dlgBuscaMedico = new DlgBuscaMedico(myParentFrame, "", true);
        dlgBuscaMedico.setVisible(true);
    }

    /**
     * Se selecciona el medico y graba receta.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private void ingresaReceta() {

        vEjecutaAccionTeclaListado = false;
        if (!VariablesVentas.vSeleccionaMedico) {
            if (validaSeleccionProductos()) {
                muestraBuscaMedico();
                if (FarmaVariables.vAceptar) {
                    lblMedico.setText(VariablesVentas.vNombreListaMed);
                    evaluaSeleccionMedico();
                    cargarReceta();
                } else {
                    limpiaMedico();
                }
            }
        } else {
            if (JConfirmDialog.rptaConfirmDialog(this,
                                                 "¿Está seguro de quitar la referencia a la receta ingresada?")) {
                limpiaMedico();
                evaluaSeleccionMedico();
            }
        }
    }

    /**
     * Se verifica que no haya productos seleccionados.
     * @return <b>true</b> si no hay productos seleccionados.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private boolean validaSeleccionProductos() {
        boolean retorno = true;
        if (VariablesVentas.vArrayList_PedidoVenta.size() > 0) {
            FarmaUtility.showMessage(this, "Existen Productos Seleccionados. Para ingresar una Receta Medica\n" +
                    "no deben haber productos seleccionados. Verifique!!!", txtProducto);
            retorno = false;
        } else if (VariablesVentas.vArrayList_ResumenPedido.size() > 0) {
            FarmaUtility.showMessage(this,
                                     "Existen Productos Seleccionados en el Resumen de Pedido. Para ingresar una Receta Medica\n" +
                    "no deben haber productos seleccionados. Verifique!!!", txtProducto);
            retorno = false;
        }
        return retorno;
    }

    /**
     * Se revierte la seleccion del medico y la receta generada.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private void limpiaMedico() {
        lblMedico.setText("");
        VariablesVentas.vArrayList_Medicos.clear();
        VariablesVentas.vCodListaMed = "";
        VariablesVentas.vMatriListaMed = "";
        VariablesVentas.vNombreListaMed = "";
        VariablesReceta.vArrayList_PedidoReceta = new ArrayList();
        VariablesReceta.vArrayList_ResumenReceta = new ArrayList();
        VariablesReceta.vNum_Ped_Rec = "";
        VariablesVentas.vSeleccionaMedico = false;
        VariablesReceta.vVerReceta = false;
    }

    /**
     * Se muestra los productos de la receta, en el resumen de pedido.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private void cargarReceta() {
        if (VariablesReceta.vArrayList_ResumenReceta.size() <= 0) {
            log.warn("No hay productos");
        } else {
            ArrayList arrayRow;
            for (int i = 0; i < VariablesReceta.vArrayList_ResumenReceta.size(); i++) {
                try {
                    arrayRow = (ArrayList)VariablesReceta.vArrayList_ResumenReceta.get(i);
                    //log.debug(arrayRow);
                    VariablesVentas.vCod_Prod = arrayRow.get(0).toString().trim();
                    VariablesVentas.vDesc_Prod = arrayRow.get(1).toString().trim();
                    VariablesVentas.vNom_Lab = arrayRow.get(9).toString().trim();
                    VariablesVentas.vPorc_Igv_Prod = arrayRow.get(11).toString().trim();
                    VariablesVentas.vCant_Ingresada = arrayRow.get(4).toString().trim();
                    VariablesReceta.vVal_Frac = arrayRow.get(10).toString().trim();
                    VariablesVentas.vIndOrigenProdVta = ConstantsVentas.IND_ORIGEN_REC;
                    VariablesVentas.vPorc_Dcto_2 = "0";
                    log.info("******JCALLO****** CAMPO INDICADOR TRATAMIENTO :" + arrayRow.get(13).toString().trim());
                    //VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
                    VariablesVentas.vIndTratamiento = arrayRow.get(13).toString().trim();
                    if (VariablesVentas.vIndTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        VariablesVentas.vTotalPrecVtaTra = Double.parseDouble(arrayRow.get(7).toString().trim());
                        VariablesVentas.vCantxDia = "1";
                        VariablesVentas.vCantxDias = "" + VariablesVentas.vCant_Ingresada;
                    } else {
                        VariablesVentas.vCantxDia = "";
                        VariablesVentas.vCantxDias = "";
                    }

                    muestraInfoProd2();

                    if (!validaStockDisponible())
                        continue;
                    if (!validaProductoTomaInventario(0))
                        continue;
                    if (!validaProductoHabilVenta())
                        continue;

                    validaStockActual2();
                    seleccionaProducto2();
                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    //log.error("",e);
                    log.error(null, e);
                }
            }
            VariablesVentas.vIndDireccionarResumenPed = true;
            aceptaOperacion();
        }
    }

    /**
     * Se obtiene informacion detallada del producto seleccionado en la receta.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void muestraInfoProd2() throws SQLException {
        ArrayList myArray = new ArrayList();
        DBVentas.obtieneInfoProducto(myArray, VariablesVentas.vCod_Prod);
        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            indProdCong = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            indProdHabilVta = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            stkProd = "";
            valFracProd = "";
            indProdCong = "";
            indProdHabilVta = "";
            log.warn("Error al obtener Informacion del Producto");
            throw new SQLException("Error al obtener Informacion del Producto");
        }
        VariablesVentas.vVal_Frac = valFracProd;
        VariablesVentas.vInd_Prod_Habil_Vta = indProdHabilVta;

        myArray = new ArrayList();
        DBVentas.obtieneInfoDetalleProducto(myArray, VariablesVentas.vCod_Prod);
        if (myArray.size() == 1) {
            VariablesVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesVentas.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            VariablesVentas.vUnid_Vta = "";
            VariablesVentas.vVal_Bono = "";
            VariablesVentas.vVal_Prec_Lista = "";
            log.warn("Error al obtener Detalle del Producto");
            throw new SQLException("Error al obtener Detalle del Producto");
        }
    }

    /**
     * Se verifica que la cantidad seleccionada en la receta
     * no se mayor que el stock del producto.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void validaStockActual2() throws SQLException {
        obtieneStockProducto2();
        int cantReceta = Integer.parseInt(VariablesVentas.vCant_Ingresada);
        int fracReceta = Integer.parseInt(VariablesReceta.vVal_Frac);
        int fracProd = Integer.parseInt(VariablesVentas.vVal_Frac);
        int cantIngreso = (cantReceta * fracProd) / fracReceta;
        if ((Integer.parseInt(VariablesVentas.vStk_Prod) + 0) < (cantIngreso))
            VariablesVentas.vCant_Ingresada = VariablesVentas.vStk_Prod;
        else
            VariablesVentas.vCant_Ingresada = String.valueOf(cantIngreso);
    }

    /**
     * Se obtiene el stock actual del producto.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void obtieneStockProducto2() throws SQLException {

        ArrayList myArray = new ArrayList();
        DBVentas.obtieneStockProducto_ForUpdate(myArray, VariablesVentas.vCod_Prod, VariablesVentas.vVal_Frac);
        FarmaUtility.liberarTransaccion();
        //quitar bloqueo de stock fisico
        //dubilluz 13.10.2011
        if (myArray.size() == 1) {
            VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
        } else {
            log.warn("Error al obtener Stock del Producto");
            throw new SQLException("Error al obtener Stock del Producto");
        }
    }

    /**
     * Se agrega el producto, en el pedido de venta.
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void seleccionaProducto2() {
        VariablesVentas.vTotalPrecVtaProd =
                (FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada) * FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta));
        VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if ( /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod,
                                      Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                      ConstantsVentas.INDICADOR_A,
                                      ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                      Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                                    true,
                                                    this,
                                                    txtProducto))*/
            !UtilityVentas.operaStkCompProdResp(VariablesVentas.vCod_Prod,
                //ASOSA, 01.07.2010
                Integer.parseInt(VariablesVentas.vCant_Ingresada), ConstantsVentas.INDICADOR_A,
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, Integer.parseInt(VariablesVentas.vCant_Ingresada),
                true, this, txtProducto, ""))
            return;

        VariablesVentas.vNumeroARecargar = ""; //NUMERO TELEFONICO SI ES RECARGA AUTOMATICA
        VariablesVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N; //INDICADOR DE PRODUCTO VIRTUAL
        VariablesVentas.vTipoProductoVirtual = ""; //TIPO DE PRODUCTO VIRTUAL
        VariablesVentas.vIndProdControlStock =
                true; //? FarmaConstants.INDICADOR_S : FarmaConstants.INDICADOR_N);//INDICADOR PROD CONTROLA STOCK
        VariablesVentas.vVal_Prec_Lista_Tmp = ""; //PRECIO DE LISTA ORIGINAL SI ES QUE SE MODIFICO
        VariablesVentas.vVal_Prec_Pub = VariablesVentas.vVal_Prec_Vta;

        Boolean valor = new Boolean(true);
        UtilityVentas.operaProductoSeleccionadoEnArrayList_02(valor, VariablesVentas.secRespStk); //ASOSA, 06.07.2010
        colocaTotalesPedido();
        //FarmaUtility.aceptarTransaccion();
    }

    /**
     * Evalua si el producto es del Tipo Virtual y si se puede seleccionar.
     * @author Luis Mesia Rivera
     * @since 05.01.2007
     */
    private void evaluaTipoProdVirtual() {
        //ERIOS 20.05.2013 Tratamiento de Producto Virtual - Magistral

        int row = myJTable.getSelectedRow();
        String tipoProdVirtual = FarmaUtility.getValueFieldJTable(myJTable, row, 14);

        if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
            muestraIngresoTelefonoMonto();
        } else if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
            int cantidad_ingresada = muestraIngresoCantidad_Tarjeta_Virtual();
            if (cantidad_ingresada != 0) {
                seleccionaProductoTarjetaVirtual(cantidad_ingresada + " ");
                String valorTarj =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta), 2);
                FarmaUtility.showMessage(this,
                                         "Se ha seleccionado una tarjeta virtual " + VariablesVentas.vDesc_Prod + " de S/. " +
                                         valorTarj, txtProducto);
            }
        } else if (tipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_MAGISTRAL)) {
            muestraIngresoRecetarioMagistral();
        }
    }

    /**o no aca nada es
     * Muestra pantalla ingreso de numero telefonico y monto de recarga
     * @author Luis Mesia Rivera
     * @since 05.01.2007
     */
    private void muestraIngresoTelefonoMonto() {
        if (myJTable.getRowCount() == 0)
            return;

        int row = myJTable.getSelectedRow();
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, COL_COD);
        VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(myJTable, row, 4);
        VariablesVentas.vUnid_Vta = FarmaUtility.getValueFieldJTable(myJTable, row, 3);
        VariablesVentas.vVal_Prec_Lista = FarmaUtility.getValueFieldJTable(myJTable, row, 10);
        //VariablesVentas.vPorc_Dcto_1 = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesVentas.vVal_Bono = FarmaUtility.getValueFieldJTable(myJTable, row, 7);
        VariablesVentas.vPorc_Igv_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 11);
        VariablesVentas.vTipoProductoVirtual = FarmaUtility.getValueFieldJTable(myJTable, row, 14);
        VariablesVentas.vMontoARecargar_Temp = "0";
        VariablesVentas.vNumeroARecargar = "";
        VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(myJTable, row, COL_ORIG_PROD);
        VariablesVentas.vPorc_Dcto_2 = "0";
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";

        //mfajardo 29/04/09 validar ingreso de productos virtuales
        VariablesVentas.vProductoVirtual = true;

        DlgIngresoRecargaVirtual dlgIngresoRecargaVirtual = new DlgIngresoRecargaVirtual(myParentFrame, "", true);
        dlgIngresoRecargaVirtual.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesVentas.vVal_Prec_Lista_Tmp = VariablesVentas.vVal_Prec_Lista;
            //VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar));
            //Ahora se grabara S/1.00
            //31.10.2007 dubilluz modificacion
            VariablesVentas.vVal_Prec_Vta =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(ConstantsVentas.PrecioVtaRecargaTarjeta));
            VariablesVentas.vVal_Prec_Lista =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Lista) *
                                              FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar));
            seleccionaProducto();
            log.debug("VariablesVentas.secRespStkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " +
                      VariablesVentas.secRespStk);
            //Actualizando el Indicador de la Venta de Tarjeta Virtual Recarga
            VariablesVentas.venta_producto_virtual = true;
            VariablesVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
        } else {
            VariablesVentas.vIndDireccionarResumenPed = false;
            VariablesVentas.vProductoVirtual = false; //ASOSA 01.02.2010
        }

    }

    /**
     * Selecciona el producto y le asigna la cantidad por defecto 1
     * @author Luis Mesia Rivera
     * @since 05.01.2007
     */
    private void seleccionaProductoTarjetaVirtual(String cantidad) {
        if (myJTable.getRowCount() == 0)
            return;

        int row = myJTable.getSelectedRow();
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, COL_COD);
        VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(myJTable, row, 4);
        VariablesVentas.vUnid_Vta = FarmaUtility.getValueFieldJTable(myJTable, row, 3);
        VariablesVentas.vVal_Prec_Lista = FarmaUtility.getValueFieldJTable(myJTable, row, 10);
        //VariablesVentas.vPorc_Dcto_1 = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesVentas.vVal_Bono = FarmaUtility.getValueFieldJTable(myJTable, row, 7);
        VariablesVentas.vPorc_Igv_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 11);
        VariablesVentas.vVal_Prec_Vta = FarmaUtility.getValueFieldJTable(myJTable, row, 6);
        VariablesVentas.vTipoProductoVirtual = FarmaUtility.getValueFieldJTable(myJTable, row, 14);
        VariablesVentas.vCant_Ingresada = cantidad.trim(); //"1";
        VariablesVentas.vNumeroARecargar = "";
        VariablesVentas.vVal_Prec_Lista_Tmp = "";
        VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(myJTable, row, COL_ORIG_PROD);
        VariablesVentas.vPorc_Dcto_2 = "0";
        VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesVentas.vCantxDia = "";
        VariablesVentas.vCantxDias = "";

        seleccionaProducto();
        //Actualizando el Indicador de la Venta de Tarjeta Virtual
        VariablesVentas.venta_producto_virtual = true;
    }

    private void muestraProductoCongelado(JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            //String indProdCong = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),pColumna))).trim();
            if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }

    /**
     * Muestra si tiene promocion
     * @author Dubilluz
     * @since 15.06.2007
     */
    private void muestraProductoPromocion(int pColumna, JLabel pLabel) {
        String descripcion = " ";
        if (myJTable.getRowCount() > 0) {
            String indProdProm = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 17))).trim();
            if (indProdProm.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //JCHAVEZ 31092009.sn
                int vFila = myJTable.getSelectedRow();
                if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {

                    VariablesVentas.vCodProdFiltro = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                    try {
                        descripcion = DBVentas.getDescPaquete(VariablesVentas.vCodProdFiltro);
                        log.debug("descripcion paquete: " + descripcion);
                    } catch (SQLException sqlException) {
                        log.error("", sqlException);
                    }

                }
                pLabel.setText(descripcion);
                //JCHAVEZ 31092009.en
                pLabel.setVisible(true);
            } else
                pLabel.setVisible(false);
        }
    }

    private void muestraProductoRefrigerado(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            String indProdRef = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), pColumna))).trim();
            if (indProdRef.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }

    private void muestraIndTipoProd(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            String indProdRef = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), pColumna))).trim();
            if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[0]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[0]);
            else if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[1]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[1]);
            else if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[2]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[2]);
            else if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[3]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[3]);
            else if (indProdRef.equalsIgnoreCase(ConstantsVentas.IND_TIPO_PROD_COD[4]))
                pLabel.setText(ConstantsVentas.IND_TIPO_PROD_DESC[4]);

        }
    }

    /**
     * Muestra si es producto de Encarte
     * @param pColumna
     * @param pLabel
     * @author JCORTEZ
     * @since  08.04.2008
     */
    private void muestraProductoEncarte(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            int vFila = myJTable.getSelectedRow();
            String indProdEncarte = FarmaUtility.getValueFieldJTable(myJTable, vFila, pColumna);
            log.debug("indProdEncarte : " + indProdEncarte);
            if (indProdEncarte.length() > 0) {
                pLabel.setVisible(true);
            } else {
                pLabel.setVisible(false);
            }
        }
    }

    private void guardaInfoProdVariables() {
        log.debug("*************************************************");
        if (VariablesVentas.vEsPedidoConvenio) //Se ha seleccionado un convenio
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
                log.error(null, sql);
                FarmaUtility.showMessage(this, mensaje + sql.getMessage(), null);
                FarmaUtility.moveFocus(txtProducto);
            }
        }
    }

    /**
     * Muestra el dialogo de convenios.
     * @author Edgar Rios Navarro
     * @since 24.05.2007
     */


    //ini Agregado FRC
    private void cargarConvenioBTL() {

        log.debug("-Exe F3 Convenio Nuevo MF - BTL -");
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            FarmaUtility.showMessage(this,
                                     "No puede elegir un convenio!!!.\n Porque se ha asociado una tarjeta de fidelizacion.",
                                     txtProducto);
            return;
        }

        if (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 1) {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Esta seguro de cancelar el pedido por Convenio?")) {
                VariablesConvenioBTLMF.limpiaVariablesBTLMF();
                if (VariablesVentas.vArrayList_PedidoVenta.size() > 0) {
                    FarmaUtility.showMessage(this,
                                             "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                            "no deben haber productos seleccionados. Verifique!!!", txtProducto);

                } else {

                    lblCliente.setText("");
                    lblMedico.setText("");
                    this.setTitle("Lista de Productos y Precios" + " /  IP : " + FarmaVariables.vIpPc);
                    evaluaTitulo();
                    //jquispe 25.07.2011 se agrego la funcionalidad de listar las campañas sin fidelizar
                    UtilityFidelizacion.operaCampañasFidelizacion(" ");
                }
            }
        } else {

            if (VariablesVentas.vArrayList_PedidoVenta.size() > 0) {
                FarmaUtility.showMessage(this, "Existen Productos Seleccionados. Para iniciar un pedido convenio\n" +
                        "no deben haber productos seleccionados. Verifique!!!", txtProducto);

            } else {
                log.debug("-Ejecutando F3 VTA CONVENIO BTLMFARMA-");
                DlgListaConveniosBTLMF loginDlg = new DlgListaConveniosBTLMF(myParentFrame, "", true);
                loginDlg.setVisible(true);
                log.debug("Cerramos Convenios" + VariablesConvenioBTLMF.vAceptar + "-" +
                          VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);

                if (VariablesConvenioBTLMF.vAceptar) {


                    VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
                    //11-DIC-13  TCT Begin
                    log.debug("<<TCT 121>>VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF=>" +
                              VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);
                    //FarmaUtility.showMessage(this, "Ingreso a Convenio", null);
                    //11-DIC-13  TCT End

                    //ImageIcon icon = new ImageIcon(this.getClass().getResource("logo_mf_btl.JPG"));
                    UtilityConvenioBTLMF.cargarVariablesConvenio(VariablesConvenioBTLMF.vCodConvenio, this,
                                                                 myParentFrame);

                    log.debug("VariablesConvenioBTLMF.vCodConvenio:" + VariablesConvenioBTLMF.vCodConvenio);
                    log.debug("VariablesConvenioBTLMF.vNomConvenio:" + VariablesConvenioBTLMF.vNomConvenio);

                    /*
                            this.setTitle("Lista de Productos y Precios      "+nombConvenio);
                            lblMedicoT.setText(" ");
                            //lblMedicoT.setIcon(icon);
                            lblMedico.setText(" "+nombConvenio);
                            lblCliente.setText(VariablesConvenioBTLMF.vNomCliente);
                            */

                } else {
                    VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = false;
                }

                evaluaTitulo();
                //SE SELECCIONO CONVENIO
                if (VariablesConvenioBTLMF.vCodConvenio != null &&
                    VariablesConvenioBTLMF.vCodConvenio.trim().length() > 1) {
                    //si se selecciono convenios debe de quitar campañas de fid. automaticas
                    VariablesFidelizacion.vListCampañasFidelizacion = new ArrayList();
                    VariablesVentas.vArrayList_Cupones = new ArrayList();
                }
            }
        }
    }
    //fin Agregado FRC

    private void cargaConvenio() {

        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            FarmaUtility.showMessage(this,
                                     "No puede elegir un convenio!!!.\n Porque se ha asociado una tarjeta de fidelizacion.",
                                     txtProducto);
            return;
        }

        if (!VariablesVentas.vEsPedidoConvenio) {
            if (UtilityVentas.evaluaPedidoConvenio(this, txtProducto, VariablesVentas.vArrayList_PedidoVenta)) {
                if (VariablesVentas.vEsPedidoConvenio)
                    generarPedidoConvenio();

                evaluaTitulo();
            }
        } else {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Esta seguro de cancelar el pedido por Convenio?")) {
                if (VariablesVentas.vArrayList_PedidoVenta.size() > 0) {
                    FarmaUtility.showMessage(this,
                                             "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                            "no deben haber productos seleccionados. Verifique!!!", txtProducto);
                } else {
                    VariablesConvenio.vCodConvenio = "";
                    VariablesConvenio.vArrayList_DatosConvenio.clear();
                    VariablesConvenio.vTextoCliente = "";
                    VariablesConvenio.vCodTrab = "";
                    VariablesConvenio.vNomCliente = "";
                    lblCliente.setText("");
                    this.setTitle("Lista de Productos y Precios" + " /  IP : " + FarmaVariables.vIpPc);
                    VariablesVentas.vEsPedidoConvenio = false;
                    evaluaTitulo();

                    //jquispe 25.07.2011 se agrego la funcionalidad de listar las campañas sin fidelizar
                    UtilityFidelizacion.operaCampañasFidelizacion(" ");
                }
            }
        }
    }


    /**
     * Muestra el Dialogo de Promociones por Producto
     * @author Dubilluz
     * @since  15.06.2007
     */
    private void muestraPromocionProd(String codigo) {
        //codigo es el cod del producto q se utilizara
        //para cargar las promociones q se encuentran en la promocion
        VariablesVentas.vCodProdFiltro = codigo;
        //String indPromocion=(String)myJTable.getValueAt(myJTable.getSelectedRow(),17);
        //  if(indPromocion.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) return;
        // else{
        //valida si esta en ArrayListadeProd promocion
        //falta aun

        DlgListaPromocion dlgListPromocion = new DlgListaPromocion(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = false;
        dlgListPromocion.setVisible(true);

        log.debug("Detalle Promocion" + VariablesVentas.vArrayList_Prod_Promociones);
        if (FarmaVariables.vAceptar) {
            seleccionaProductoPromocion();
            VariablesVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
            aceptaOperacion();

            //JCHAVEZ 29102009 inicio
            try {
                VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();
            } catch (SQLException ex) {
                log.error("", ex);
            }
            if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
                    String codProd =
                        FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Prod_Promociones, i,
                                                            0).toString();
                    double precioUnit =
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Prod_Promociones,
                                                                                          i, 3));
                    double precioVentaUnit =
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Prod_Promociones,
                                                                                          i, 6));
                    double precioVtaTotal =
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Prod_Promociones,
                                                                                          i, 7));
                    double cantidad =
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesVentas.vArrayList_Prod_Promociones,
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
                        ((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).set(3, cprecioUnitRedondeado);
                        ((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).set(6,
                                                                                            cprecioVentaUnitRedondeado);
                        ((ArrayList)VariablesVentas.vArrayList_Prod_Promociones.get(i)).set(7,
                                                                                            cprecioVtaTotalRedondeado);
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
            }
            //JCHAVEZ 29102009 fin
        } else
            VariablesVentas.vIndDireccionarResumenPed = false;
        //  }
    }

    /**
     * Seleccionara los Productos  de la promocion
     * aceptada
     * @author dubilluz
     * @since  15.06.2007
     */
    private void seleccionaProductoPromocion() {
        int vFila = myJTable.getSelectedRow();
        Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));
        log.debug("<<TCT 4>>vArrayList_Promociones" + VariablesVentas.vArrayList_Promociones);
        log.debug("" + VariablesVentas.vArrayList_Prod_Promociones.size());
        log.debug("" + VariablesVentas.vArrayList_Prod_Promociones);
        FarmaUtility.ponerCheckJTable(myJTable, 1, VariablesVentas.vArrayList_Prod_Promociones_temporal, 0);
        String cod_prod_tem = "";
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size(); i++) {
            cod_prod_tem =
                    ((String)((ArrayList)(VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i))).get(0)).trim();
            if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {
                FarmaUtility.setearCheckInRow(tblListaSustitutos, valorTmp, true, true, cod_prod_tem, 1);
                tblListaSustitutos.repaint();
            } else if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_SUSTITUTOS)) {
                FarmaUtility.setearCheckInRow(tblProductos, valorTmp, true, true, cod_prod_tem, 1);
                tblProductos.repaint();
            }
        }
        colocaTotalesPedido();
        //FarmaUtility.ponerCheckJTable(myJTable,1,VariablesVentas.vArrayList_Prod_Promociones,0);

    }

    /**
     * busca si el codigo esta en el array
     * @author dubilluz
     * @since  19.06.2007
     */
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
     * Establece un valor boolean para un objeto en una lista de selección
     * @param pJTable
     * 				Tabla a operar
     * @param pCampoEnJTable
     * 				Campo en la tabla
     * @param pArrayList
     * 				Lista de campos
     * @param pCampoEnArrayList
     * 				Indice del campo en la lista
     */
    public static void quitarCheckJTable(JTable pJTable, int pCampoEnJTable, ArrayList pArrayList,
                                         int pCampoEnArrayList) {
        String myCodigo = "";
        String myCodigoTmp = "";
        for (int i = 0; i < pJTable.getRowCount(); i++) {
            myCodigo = ((String)pJTable.getValueAt(i, pCampoEnJTable)).trim();
            for (int j = 0; j < pArrayList.size(); j++) {
                myCodigoTmp = ((String)(((ArrayList)pArrayList.get(j)).get(pCampoEnArrayList))).trim();
                if (myCodigo.equalsIgnoreCase(myCodigoTmp))
                    pJTable.setValueAt(new Boolean(false), i, 0);
            }
        }
        pJTable.repaint();
    }

    /**
     * Cancela las Promociones Pedidos
     * @author dubilluz
     * @since  04.07.2007
     */
    private void cancela_promociones() {
        String codProm = "";
        String codProd = "";
        String cantidad = "";
        String indControlStk = "";
        ArrayList aux = new ArrayList();
        ArrayList array_promocion = new ArrayList();
        ArrayList prod_Prom = new ArrayList();

        Boolean valor = new Boolean(true);
        ArrayList agrupado = new ArrayList();
        ArrayList atemp = new ArrayList();
        //log.debug("");
        log.debug("!!!!>Promociona antes de Elimin " + VariablesVentas.vArrayList_Promociones_temporal.size());
        log.debug("!!!!Promociona eli " + VariablesVentas.vArrayList_Promociones_temporal);

        for (int j = 0; j < VariablesVentas.vArrayList_Promociones_temporal.size(); j++) {
            array_promocion = (ArrayList)(VariablesVentas.vArrayList_Promociones_temporal.get(j));
            codProm = ((String)(array_promocion.get(0))).trim();
            log.debug(">>>>Promociona eli " + codProm);
            codProd = "";
            cantidad = "";
            indControlStk = "";
            aux = new ArrayList();

            prod_Prom = new ArrayList();
            log.debug(">>>>**Detalles " + VariablesVentas.vArrayList_Prod_Promociones_temporal);
            prod_Prom = detalle_Prom(VariablesVentas.vArrayList_Prod_Promociones_temporal, codProm);
            log.debug(">>>>**detalle de la prmo  " + j + "  :" + prod_Prom);
            valor = new Boolean(true);
            agrupado = new ArrayList();
            atemp = new ArrayList();

            for (int i = 0; i < prod_Prom.size(); i++) {
                atemp = (ArrayList)(prod_Prom.get(i));
                FarmaUtility.operaListaProd(agrupado, (ArrayList)(atemp.clone()), valor, 0);
            }

            agrupado = agrupar(agrupado);
            log.debug(">>>>**Agrupado " + agrupado);
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
                log.debug(indControlStk);
                secRespaldo = ((String)(aux.get(24))).trim(); //ASOSA, 08.07.2010
                VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(codProd, //Antes, ASOSA, 01.07.2010
                                                     Integer.parseInt(cantidad),
                                                     ConstantsVentas.INDICADOR_D,
                                                     ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR,
                                                     Integer.parseInt(cantidad),
                                                     false,
                                                     this,
                                                     txtProducto))*/
                    !UtilityVentas.operaStkCompProdResp(codProd,
                        //ASOSA, 01.07.2010
                        Integer.parseInt(cantidad), ConstantsVentas.INDICADOR_D,
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad), false, this,
                        txtProducto, secRespaldo))

                    return;
                //}
            }
            FarmaUtility.aceptarTransaccion();
            /*        removeItemArray(VariablesVentas.vArrayList_Promociones_temporal,codProm,0);
        removeItemArray(VariablesVentas.vArrayList_Prod_Promociones_temporal,codProm,18);*/
        }

        VariablesVentas.vArrayList_Promociones_temporal = new ArrayList();
        VariablesVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
        log.debug("Resultados despues de Eliminar");
        log.debug("Tamaño de Resumen   :" + VariablesVentas.vArrayList_ResumenPedido.size() + "");
        log.debug("Tamaño de Promocion :" + VariablesVentas.vArrayList_Promociones_temporal.size() + "");
        log.debug("Tamaño de Prod_Promocion :" + VariablesVentas.vArrayList_Prod_Promociones_temporal.size() + "");

    }

    /* ************************************************************************** */
    /*                        Metodos Auxiliares                                  */
    /* ************************************************************************** */

    /**
     * Metodo que elimina items del array ,que sean iguales al paramtro
     * q se le envia , comaprando por campo
     * @author dubilluz
     * @since  20.06.2007
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
     * @author dubilluz
     * @since  03.07.2007
     */
    private ArrayList detalle_Prom(ArrayList array, String codProm) {
        ArrayList nuevo = new ArrayList();
        ArrayList aux = new ArrayList();
        String cod_p = "";
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size(); i++) {
            aux = (ArrayList)(VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i));
            cod_p = (String)(aux.get(18));
            log.debug("cod de detal" + cod_p + ">>>>" + codProm);

            if ((cod_p). /*.trim()*/equalsIgnoreCase(codProm)) {
                log.debug("si son iguales ");
                nuevo.add((ArrayList)(aux.clone()));
            }
        }
        return nuevo;
    }

    /**
     * Agrupa productos que esten en ambos paquetes
     * retorna el nuevoa arreglo
     * @author dubilluz
     * @since 27.06.2007
     */
    private ArrayList agrupar(ArrayList array) {
        ArrayList nuevo = new ArrayList();
        ArrayList aux1 = new ArrayList();
        ArrayList aux2 = new ArrayList();
        int cantidad1 = 0;
        int cantidad2 = 0;
        int suma = 0;
        for (int i = 0; i < array.size(); i++) {
            aux1 = (ArrayList)(array.get(i));
            if (aux1.size() < 23) { //(((String)(aux1.get(19))).trim()).equalsIgnoreCase("Revisado")){
                for (int j = i + 1; j < array.size(); j++) {
                    aux2 = (ArrayList)(array.get(j));
                    if (aux2.size() < 23) {
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

    private void cargaStockLocales() {
        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();
        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesVentas.vDesc_Prod = ((String)(myJTable.getValueAt(vFila, 2))).trim();
        VariablesVentas.vNom_Lab = ((String)(myJTable.getValueAt(vFila, 4))).trim();
        VariablesVentas.vUnid_Vta = lblUnidad.getText();

        DlgStockLocales dlgStockLocales = new DlgStockLocales(myParentFrame, "", true);
        dlgStockLocales.setVisible(true);
    }

    /**
     * Muestra para un Producto de Tarjeta Virtual
     * @author dubilluz
     * @since  29.08.2007
     */
    private int muestraIngresoCantidad_Tarjeta_Virtual() {
        if (myJTable.getRowCount() == 0)
            return 0;

        ArrayList aux = new ArrayList();
        int vFila = myJTable.getSelectedRow();
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD)); // codigo
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 2)); //descripcion
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 4)); //laboratorio
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 3)); //unidad
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 6)); //precio
        log.debug("DIEGO UBILLUZ >>  " + aux);

        VariablesVentas.vArrayList_Prod_Tarjeta_Virtual.clear();
        VariablesVentas.vArrayList_Prod_Tarjeta_Virtual = (ArrayList)aux.clone();
        //(myJTable.getSelectedRow())
        aux.clear();
        log.debug("DIEGO UBILLUZ >>  " + aux);
        DlgIngresoCantidadTarjetaVirtual dlgIngresoCantidad =
            new DlgIngresoCantidadTarjetaVirtual(myParentFrame, "", true);
        dlgIngresoCantidad.setVisible(true);

        if (FarmaVariables.vAceptar) {
            //seleccionaProducto();
            FarmaVariables.vAceptar = false;
            VariablesVentas.vIndDireccionarResumenPed = true;
            return VariablesVentas.cantidad_tarjeta_virtual;
        } else {
            VariablesVentas.vIndDireccionarResumenPed = false;
            return 0;
        }
    }

    /**
     * Coloca el tipo de producto
     * @author dubilluz
     * @since  22.10.2007
     */
    private void muestraIndTipoProd(int pColumna, JLabel pLabel, String tipoProd) {
        if (myJTable.getRowCount() > 0) {
            //String indProdRef = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),pColumna))).trim();
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
     * Se muestra los productos sustitutos
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    private void muestraProductosSustitutos() {
        try {
            int vFila = tblProductos.getSelectedRow();
            if (vFila >= 0) {
                String codigoProducto = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                DBVentas.cargaListaProductosSustitutos(tblModelListaSustitutos, codigoProducto);
                tblListaSustitutos.repaint();
            }
        } catch (SQLException sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(this, "Error al Listar Productos Sustitutos.\n" +
                    sqlException, txtProducto);
        }
    }

    private void quitarFiltro() {
        vEjecutaAccionTeclaListado = false;
        if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            //lblDescFiltro.setText("");
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
            tblModelListaSustitutos.clearTable();
            tableModelListaPrecioProductos.clearTable();
            tableModelListaPrecioProductos.fireTableDataChanged();
            clonarListadoProductos();
            setJTable(tblProductos);
            iniciaProceso(false);
            FarmaUtility.moveFocus(txtProducto);

            lblF7.setText("[ F7 ] Filtrar Desc.");
        }
    }

    /**
   * Se filtran los productos dependiendo del filtro del resumen
   * @author JCORTEZ
   * @since 17.04.08
   */
    /* private void filtrarTablaProductosResumen2(String pTipoFiltro,
                                           String pCodFiltro) {
    try {
      tableModelListaPrecioProductos.clearTable();
      tableModelListaPrecioProductos.fireTableDataChanged();
      DBVentas.cargaListaProductosVenta_Filtro2(tableModelListaPrecioProductos,
                                               pTipoFiltro,
                                               pCodFiltro);
      if(tableModelListaPrecioProductos.getRowCount()>0)
      FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos,COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);
      //else
      //FarmaUtility.showMessage(this, "Resultado vacio", null);
    } catch (SQLException sqlException) {
        log.error("",sqlException);
      FarmaUtility.showMessage(this, "Error al obtener Lista de Productos Filtrado!!!", txtProducto);
    }
  }*/

    /**
     * Busqueda de cadena en el listado
     * @param pLista
     * @param pCadena
     * @return
     */
    private boolean isExistProdListaFaltaCero(ArrayList pLista, String pCadena) {
        if (pLista.size() > 0) {
            for (int i = 0; i < pLista.size(); i++) {
                if (pLista.get(i).toString().trim().equalsIgnoreCase(pCadena.trim())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Para clonar el listado de productos original.
     * @author Edgar Rios Navarro
     * @since 29.05.2008
     */
    private void clonarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; i < VariablesVentas.tableModelListaGlobalProductos.data.size(); i++) {

            ArrayList aux = (ArrayList)((ArrayList)VariablesVentas.tableModelListaGlobalProductos.data.get(i)).clone();
            arrayClone.add(aux);
        }
        tableModelListaPrecioProductos.data = arrayClone;
    }

    /**
     * Se muestra el tratemiento del producto
     * @author JCORTEZ
     * @since 29.05.2008
     */
    private void mostrarTratamiento() {

        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();

        VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesVentas.vDesc_Prod = ((String)(myJTable.getValueAt(vFila, 2))).trim();
        VariablesVentas.vNom_Lab = ((String)(myJTable.getValueAt(vFila, 4))).trim();
        VariablesVentas.vPorc_Igv_Prod = ((String)(myJTable.getValueAt(vFila, 11))).trim();
        VariablesVentas.vCant_Ingresada_Temp = "0";
        VariablesVentas.vNumeroARecargar = "";
        VariablesVentas.vVal_Prec_Lista_Tmp = "";
        VariablesVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
        VariablesVentas.vTipoProductoVirtual = "";
        //VariablesVentas.vVal_Prec_Pub = ((String)(myJTable.getValueAt(vFila,6))).trim();
        VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_ORIG_PROD);
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
            VariablesVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
        } else {
            VariablesVentas.vIndDireccionarResumenPed = false;
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
            //TCT 21.07.2014 Reemplaza Codigo Campana Cupon por Valor Real
            codCampCupon = (String)mapCupon.get("COD_CAMP_CUPON");
            //TCT
            mapCupon.put("COD_CUPON", nroCupon);
        } catch (SQLException e) {
            log.debug("ocurrio un error al obtener datos del cupon:" + nroCupon + " error:" + e);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener datos del cupon.\n" +
                    e.getMessage() +
                    "\n Inténtelo Nuevamente\n si persiste el error comuniquese con operador de sistemas.",
                    txtProducto);
            return;
        }
        log.debug("datosCupon:" + mapCupon);
        //Se verifica si el cupon ya fue agregado tambien verifica si la campaña tambien ya esta agregado
        if (UtilityVentas.existeCuponCampana(nroCupon, this, txtProducto))
            return;

        String indMultiuso = mapCupon.get("IND_MULTIUSO").toString().trim();
        boolean obligarIngresarFP = isFormaPagoUso_x_Cupon(codCampCupon);
        boolean yaIngresoFormaPago = false;
        //validacion de cupon en base de datos vigente y todo lo demas

        //jquispe
        //String vIndFidCupon = "N";//obtiene el ind fid -- codCampCupon
        log.debug("CAMP CUPON:: codCampCupon " + codCampCupon);
        String vIndFidCupon = UtilityFidelizacion.getIndfidelizadoUso(codCampCupon);

        if (vIndFidCupon.trim().equalsIgnoreCase("S")) {

            if (VariablesFidelizacion.vDniCliente.trim().length() == 0) {
                funcionF12(codCampCupon);
                yaIngresoFormaPago = true;
            }


            if (VariablesFidelizacion.vDniCliente.trim().length() > 0) {

                //Consultara si es necesario ingresar forma de pago x cupon
                // si es necesario solicitará el mismo.
                if (obligarIngresarFP) {
                    if (!yaIngresoFormaPago)
                        funcionF12(codCampCupon);
                }


                if (!UtilityVentas.validarCuponEnBD(nroCupon, this, txtProducto, indMultiuso,
                                                    VariablesFidelizacion.vDniCliente)) {
                    return;
                } else {
                    //agregando cupon al listado
                    VariablesVentas.vArrayList_Cupones.add(mapCupon);
                    //24.06.2011
                    //Para Reinicializar todas las formas de PAGO.
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                    VariablesVentas.vMensCuponIngre = "Se ha agregado el cupón " + nroCupon + " de la Campaña \n" +
                            mapCupon.get("DESC_CUPON").toString() + ".";
                    FarmaUtility.showMessage(this, VariablesVentas.vMensCuponIngre, txtProducto);
                }
            }
            txtProducto.setText("");
        } else {

            if (obligarIngresarFP) {
                if (!yaIngresoFormaPago)
                    funcionF12(codCampCupon);
            }

            if (!UtilityVentas.validarCuponEnBD(nroCupon, this, txtProducto, indMultiuso,
                                                VariablesFidelizacion.vDniCliente)) {
                return;
            } else {
                //agregando cupon al listado
                VariablesVentas.vArrayList_Cupones.add(mapCupon);
                //24.06.2011
                //Para Reinicializar todas las formas de PAGO.
                UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                VariablesVentas.vMensCuponIngre = "Se ha agregado el cupón " + nroCupon + " de la Campaña \n" +
                        mapCupon.get("DESC_CUPON").toString() + ".";
                FarmaUtility.showMessage(this, VariablesVentas.vMensCuponIngre, txtProducto);
            }
            txtProducto.setText("");
        }
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
     * Se agrega el cupon ingresado.
     * @param cadena
     * @author Edgar Rios Navarro
     * @since 02.07.2008
     * @deprecated
     */
    private void agregarCupon(String cadena) {

        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this, "No puede agregar cupones a un pedido por convenio.", txtProducto);
            return;
        }


        //JCORTEZ 15.08.08 obtiene indicador de multiuso de la campaña
        String ind_multiuso = "", cod_camp = "";
        ArrayList aux = new ArrayList();
        try {
            DBVentas.obtieneIndMultiuso(aux, cadena);
            if (aux.size() > 0) {
                ind_multiuso = (String)((ArrayList)aux.get(0)).get(1);
                cod_camp = (String)((ArrayList)aux.get(0)).get(0);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener indicador.\n" +
                    sql.getMessage(), txtProducto);
        }

        //Verifica que no exista en el arreglo
        if (UtilityVentas.validaCupones(cadena, this, txtProducto))
            return;

        //Valida que la campana no haya sido agregado al pedido
        if (UtilityVentas.validaCampanaCupon(cadena, this, txtProducto, ind_multiuso, cod_camp))
            return;

        //Valida estructura del cupon en BBDD
        if (!UtilityVentas.validaDatoCupon(cadena, this, txtProducto, ind_multiuso))
            return;


        //txtDescProdOculto.setText("");
        txtProducto.setText("");

        // validaPedidoCupon();


        //dveliz 25.08.08

        VariablesCampana.vCodCupon = cadena;
        //ingresarDatosCampana();
        if (VariablesCampana.vListaCupones.size() > 0)
            VariablesCampana.vFlag = true;

        /*if(VariablesCampana.vNumIngreso==1)
     if(!UtilityVentas.validaDatoCupon(cadena,this,txtProducto,ind_multiuso)) return;  */
        //Fin dveliz
    }

    private void validarClienteTarjeta(String cadena) {
        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this, "No puede agregar una tarjeta a un " + "pedido por convenio.", txtProducto);
            return;
        }
        UtilityFidelizacion.validaLecturaTarjeta(cadena, myParentFrame);
        if (VariablesFidelizacion.vDataCliente.size() > 0) {
            ArrayList array = (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            //JCALLO 02.10.2008
            //VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
            //seteando los datos del cliente en las variables con los datos del array
            UtilityFidelizacion.setVariablesDatos(array);
            if (VariablesFidelizacion.vIndExisteCliente) {
                FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("0"))
                FarmaUtility.showMessage(this, "Se agrego el DNI :" + VariablesFidelizacion.vDniCliente, null);
            else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("2"))
                /*FarmaUtility.showMessage(this,
                                       "Cliente encontrado con DNI " + VariablesFidelizacion.vDniCliente,
                                       null);*/
                FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
            else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("1"))
                FarmaUtility.showMessage(this,
                                         "Se actualizaron los datos del DNI :" + VariablesFidelizacion.vDniCliente,
                                         null);

            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                               VariablesFidelizacion.vApeMatCliente);
            //fin jcallo 02.10.2008
            VariablesFidelizacion.vIndAgregoDNI = "";

            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            log.debug("**************************************");
            log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + VariablesFidelizacion.vDniCliente);
            VariablesVentas.vArrayList_CampLimitUsadosMatriz = new ArrayList();
            log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" +
                      VariablesVentas.vArrayList_CampLimitUsadosMatriz);

            //Ya no validara con Matriz
            //Campaña Limitante 14.04.2009 DUBILLUZ
            /*VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,          																	FarmaConstants.INDICADOR_N);
          log.debug("************************");
          if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ
          	log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente"+VariablesFidelizacion.vDniCliente);
          	VariablesVentas.vArrayList_CampLimitUsadosMatriz = CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);
          	log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz"+VariablesVentas.vArrayList_CampLimitUsadosMatriz);
          }
          */
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            //dubilluz 19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() > 0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),
                                                      VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin
        } else {
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            log.debug("**************************************");
            //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
            VariablesFidelizacion.vIndConexion = FarmaConstants.INDICADOR_N;

            log.debug("************************");
            // if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ  JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
            log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + VariablesFidelizacion.vDniCliente);
            VariablesVentas.vArrayList_CampLimitUsadosMatriz =
                    UtilityFidelizacion.CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);
            log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" +
                      VariablesVentas.vArrayList_CampLimitUsadosMatriz);
            //  }// JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz

            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + VariablesFidelizacion.vApePatCliente + " " +
                               VariablesFidelizacion.vApeMatCliente);
            //fin jcallo 02.10.2008
        }


        txtProducto.setText("");
    }
    /*
    private void mostrarBuscarTarjetaPorDNI() {

        DlgFidelizacionBuscarTarjetas dlgBuscar =
            new DlgFidelizacionBuscarTarjetas(myParentFrame, "", true);
        dlgBuscar.setVisible(true);
        log.debug("vv DIEGO:" + FarmaVariables.vAceptar);
        log.debug("dat_1:" + VariablesFidelizacion.vDataCliente);
        log.debug(" VariablesFidelizacion.vNomCliente_1:" +
                           VariablesFidelizacion.vNomCliente);
        log.debug(" VariablesFidelizacion.vDniCliente_1:" +
                           VariablesFidelizacion.vDniCliente);
        if (FarmaVariables.vAceptar) {
            log.debug("en aceptar");
            log.debug("dat:" + VariablesFidelizacion.vDataCliente);
            ArrayList array =
                (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            log.debug("des 1");
            //JCALLO 02.10.2008
            //VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
            //seteando los datos del cliente en las variables con los datos del array
            UtilityFidelizacion.setVariablesDatos(array);
            log.debug("des 2");

            log.debug(" VariablesFidelizacion.vNomCliente:" +
                               VariablesFidelizacion.vNomCliente);
            log.debug(" VariablesFidelizacion.vDniCliente:" +
                               VariablesFidelizacion.vDniCliente);
            FarmaUtility.showMessage(this, "Bienvenido \n" +
                    VariablesFidelizacion.vNomCliente + " " +
                    VariablesFidelizacion.vApePatCliente + " " +
                    VariablesFidelizacion.vApeMatCliente + "\n" +
                    "DNI: " + VariablesFidelizacion.vDniCliente, null);
            //dubilluz 19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() >
                0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),
                                                      VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin


            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente + " " +
                               VariablesFidelizacion.vApePatCliente + " " +
                               VariablesFidelizacion.vApeMatCliente);
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
                //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                VariablesFidelizacion.vIndConexion =
                        FarmaConstants.INDICADOR_N;
                log.debug("**************************************");
                //if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ   //VER SI HAY LINEA CON MATRIZ  JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
                log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" +
                          VariablesFidelizacion.vDniCliente);
                VariablesVentas.vArrayList_CampLimitUsadosMatriz =
                        CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);

                log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" +
                          VariablesVentas.vArrayList_CampLimitUsadosMatriz);
                // } // JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            } else {
                log.info("Cliente esta invalidado para descuento...");
            }
        }
    }
*/

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

        //cargando las campanas de fidelizacion
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

    private boolean isExisteProdCampana(String pCodProd) {
        //lblMensajeCampaña.setVisible(true);
        String pRespta = "N";
        try {
            lblMensajeCampaña.setText("");
            pRespta = DBVentas.existeProdEnCampañaAcumulada(pCodProd, VariablesFidelizacion.vDniCliente);
            if (pRespta.trim().equalsIgnoreCase("E"))
                lblMensajeCampaña.setText("    Cliente inscrito en Campaña Acumulada.");

            if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                lblMensajeCampaña.setText("   Promoción: \" Acumula tu Compra y Gana \"");
        } catch (SQLException e) {
            log.error(null, e);
            pRespta = "N";
        }

        if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;

        return true;
    }

    /**
     * obtener todas las campañas de fidelizacion automaticas usados en el pedido
     *
     * */
    /*private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            listaCampLimitUsadosMatriz =
                    DBCaja.getListaCampUsadosLocalXCliente(dniCliente); // DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);// JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
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
    */
    /*
    private void setMensajeDNIFidelizado() {
        if (VariablesFidelizacion.vDniCliente.trim().length() > 7 &&
            VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            if (!VariablesFidelizacion.vDNI_Anulado) {
                lblMensajeCampaña.setText("  DNI no afecto a Descuento.");
                lblMensajeCampaña.setVisible(true);
            } else {
                lblMensajeCampaña.setText("");
                lblMensajeCampaña.setVisible(false);
            }

        }
    }
*/

    /**
     * Se valida el rol del usuario
     * @author JCORTEZ
     * @since 24.07.2009
     * */
    private boolean ValidaRolUsu(String vRol) {

        boolean valor = true;
        String result = "";

        log.debug("FarmaVariables.vNuSecUsu : " + FarmaVariables.vNuSecUsu);
        try {
            result = DBVentas.verificaRolUsuario(FarmaVariables.vNuSecUsu, vRol);
            log.debug("result : " + result);
            if (result.equalsIgnoreCase("N"))
                valor = false;
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al validar el rol de usuario .\n" +
                    e.getMessage(), txtProducto);
        }
        return valor;
    }


    private boolean cargaValidaLogin() {
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
        }
        return FarmaVariables.vAceptar;
    }


    private void lblPedDelivery() {

        //JCALLO 19.12.2008 comentado sobre la opcion de ver pedidos delivery..y usarlo para el tema inscribir cliente a campañas acumuladas
        //JCORTEZ 07.08.09 Se habilita nuevamente la opcion de pedido automatico.
        /** JCALLO INHABILITAR F9 02.10.2008* **/
        log.debug("HABILITAR F9 : " + VariablesVentas.HabilitarF9);

        if (VariablesVentas.HabilitarF9.equalsIgnoreCase(ConstantsVentas.ACTIVO)) {
            if (UtilityVentas.evaluaPedidoDelivery(this, txtProducto, VariablesVentas.vArrayList_PedidoVenta)) {
                evaluaTitulo();
                FarmaUtility.moveFocus(txtProducto);
                // Inicio Adicion Delivery 28/04/2006 Paulo
                if (VariablesVentas.vEsPedidoDelivery) {
                    generarPedidoDelivery();
                }
                // Fin Adicion Delivery 28/04/2006 Paulo
            }
        } else
            FarmaUtility.showMessage(this, "Opcion no disponible en local.", txtProducto);
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
    //jquispe 03.05.2010
    //Cambia el proceso de actualizacion en mostrar dagtos del producto.

    private void UpdateReleaseProd(KeyEvent e) {

        muestraNombreLab(4, lblDescLab_Prod);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoRefrigerado(15, lblProdRefrig);
        muestraProductoPromocion(17, lblProdProm);
        muestraIndTipoProd(16, lblIndTipoProd);
        // JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);

        muestraInfoProd();
        muestraProductoCongelado(lblProdCong);
        if (!(e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
            if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {

                actualizaListaProductosAlternativos();
            }
        }
        colocaTotalesPedido();
    }

    public void funcionF12(String pCodCampanaCupon) {
        log.debug("Funcion F12");


        AuxiliarFidelizacion.funcionF12(pCodCampanaCupon, txtProducto, this.myParentFrame, lblMensajeCampaña,
                                        lblCliente, this, "L", lblDNI_SIN_COMISION);

        /*
         * Se cambio para que se encuentre orientado a objetos
         * dubilluz 22.05.2012
        VariablesFidelizacion.tmpCodCampanaCupon = pCodCampanaCupon;
        if (VariablesVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this,
                                     "No puede agregar una tarjeta a un " +
                                     "pedido por convenio.", txtProducto);
            return;
        }
        mostrarBuscarTarjetaPorDNI();
        VariablesFidelizacion.tmpCodCampanaCupon = "";
        */
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
            String pExisteAsociado = UtilityFidelizacion.existeDniAsociado(nroTarjetaFormaPago);
            if (pExisteAsociado.trim().equalsIgnoreCase("S")) {
                //VALIDA EL CLIENTE POR TARJETA 12.01.2011
                String cadena = nroTarjetaFormaPago;
                validarClienteTarjeta(cadena);
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
                    VariablesFidelizacion.vMaximoAhorroDNIxPeriodo =
                            UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,
                                                                           VariablesFidelizacion.vNumTarjeta);

                    log.info("Variable de DNI_ANULADO: " + VariablesFidelizacion.vDNI_Anulado);
                    log.info("Variable de vAhorroDNI_x_Periodo: " + VariablesFidelizacion.vAhorroDNI_x_Periodo);
                    log.info("Variable de vMaximoAhorroDNIxPeriodo: " +
                             VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);

                    AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña, "L", txtProducto, this);
                }
            } else {
                if (VariablesFidelizacion.vDniCliente.trim().length() == 0) {
                    funcionF12("N");
                    yaIngresoFormaPago = true;
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
                txtProducto.setText("");
            }
        }
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

        if (pCadenaNueva.length() > 0) {


            if (!pCadenaNueva.trim().equalsIgnoreCase("N")) {
                log.debug("Es tarjeta");
                txtProducto.setText(pCadenaNueva.trim());
                pasoTarjeta = true;
            } else {
                log.debug("NO ES tarjeta");
                pasoTarjeta = false;
            }


        }


    }

    private boolean validaVentaConMenos() {

        if (myJTable.getRowCount() == 0)
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
            log.error("", sql);
        }

        //if(flagContinua)
        //Continua con el proceso

        return flagContinua;
    }

    //Dubilluz - 06.12.2011

    public void ingresaMedicoFidelizado() {

        AuxiliarFidelizacion.ingresoMedico(this.myParentFrame, lblMedico, lblMensajeCampaña, lblCliente, this, "L",
                                           lblDNI_SIN_COMISION, txtProducto);
        /*
        String pPermiteIngresoMedido =
            UtilityFidelizacion.getPermiteIngresoMedido();

        if (pPermiteIngresoMedido.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (VariablesVentas.vEsPedidoConvenio) {
                FarmaUtility.showMessage(this,
                                         "No puede ingresar el Médido porque tiene" +
                                         "seleccionado convenio.",
                                         txtProducto);
                return;
            }
            DlgBusquedaMedicoCamp dlgLista = new DlgBusquedaMedicoCamp(myParentFrame,"",true,lblMedico,
                                                                       lblMensajeCampaña,lblCliente,this,"L",lblDNI_SIN_COMISION);
            dlgLista.setVisible(true);

            //if(FarmaVariables.vAceptar){
              //  pExiste = "S";
           // }
            //else{
              //  pExiste = "NO_SELECCIONO";
           // }

        }
        else
            FarmaUtility.showMessage(this,"Por el momento no existen promociones por Receta.",txtProducto);
        log.debug("****** ====VARIABLES DE MEDICO ==========================******");
        log.debug("VariablesFidelizacion.V_NUM_CMP:"+VariablesFidelizacion.V_NUM_CMP);
        log.debug("VariablesFidelizacion.V_NOMBRE:"+VariablesFidelizacion.V_NOMBRE);
        log.debug("VariablesFidelizacion.V_DESC_TIP_COLEGIO:"+VariablesFidelizacion.V_DESC_TIP_COLEGIO);
        log.debug("VariablesFidelizacion.V_TIPO_COLEGIO:"+VariablesFidelizacion.V_TIPO_COLEGIO);
        log.debug("VariablesFidelizacion.V_COD_MEDICO:"+VariablesFidelizacion.V_COD_MEDICO);
        log.debug("****** ====VARIABLES DE MEDICO ==========================******");
        */
    }

    /**
     * Muestra ventana de registro de Recetario Magistral
     *
     * @author ERIOS
     * @since 20.05.2013
     */
    private void muestraIngresoRecetarioMagistral() {
        if (VariablesPtoVenta.vIndVerReceMagis.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (myJTable.getRowCount() == 0)
                return;

            int row = myJTable.getSelectedRow();
            VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, COL_COD);
            VariablesVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
            VariablesVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(myJTable, row, 4);
            VariablesVentas.vUnid_Vta = FarmaUtility.getValueFieldJTable(myJTable, row, 3);

            VariablesVentas.vVal_Prec_Lista = FarmaUtility.getValueFieldJTable(myJTable, row, 10);
            //VariablesVentas.vPorc_Dcto_1 = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
            VariablesVentas.vVal_Bono = FarmaUtility.getValueFieldJTable(myJTable, row, 7);
            VariablesVentas.vPorc_Igv_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 11);

            VariablesVentas.vTipoProductoVirtual = FarmaUtility.getValueFieldJTable(myJTable, row, 14);

            VariablesVentas.vMontoARecargar_Temp = "0";
            VariablesVentas.vNumeroARecargar = "";

            VariablesVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(myJTable, row, COL_ORIG_PROD);
            VariablesVentas.vPorc_Dcto_2 = "0";
            VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
            VariablesVentas.vCantxDia = "";
            VariablesVentas.vCantxDias = "";

            VariablesVentas.vProductoVirtual = true;
            VariablesRecetario.strCodigoRecetario = null;

            DlgResumenRecetarioMagistral dlgResumenRecetarioMagistral =
                new DlgResumenRecetarioMagistral(myParentFrame, "", true);
            dlgResumenRecetarioMagistral.setVisible(true);

            VariablesRecetario.vMapDatosPacienteMedico = null;
            VariablesRecetario.vArrayEsteril = null;

            if (FarmaVariables.vAceptar) {
                VariablesVentas.vCant_Ingresada = VariablesRecetario.strCant_Recetario;
                //VariablesRecetario.strCodigoRecetario = strCodigoRecetario;
                VariablesVentas.vVal_Prec_Lista_Tmp = VariablesRecetario.strPrecioTotal;
                VariablesVentas.vVal_Prec_Vta = VariablesRecetario.strPrecioTotal;
                //FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesRecetario.strPrecioTotal));
                VariablesVentas.vVal_Prec_Lista = VariablesRecetario.strPrecioTotal;
                //FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesRecetario.strPrecioTotal));
                seleccionaProducto();

                VariablesVentas.venta_producto_virtual = true;
                VariablesVentas.vIndDireccionarResumenPed = true;
                FarmaVariables.vAceptar = false;
            } else {
                VariablesVentas.vIndDireccionarResumenPed = false;
                VariablesVentas.vProductoVirtual = false;
            }
        } else {
            FarmaUtility.showMessage(this, "El registro de Recetario Magistral no está habilitado.", null);
        }
    }

    /**
     * Se filtra/quita filtro de busqueda
     * @author ERIOS
     * @since 29.08.2013
     */
    private void filtroGoogle() {
        if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            quitarFiltro();
        } else {
            filtrarBusquedaGoogle();
        }
    }

    /**
     * Se filtra el listado de productos segun la descripcion que se ingrese
     * @author ERIOS
     * @since 29.08.2013
     */
    private void filtrarBusquedaGoogle() {
        String condicion = txtProducto.getText().toUpperCase();
        if (!condicion.equals("") && condicion.length() > 0) {
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModelListaPrecioProductos.data;
            ArrayList filteredCollection = new ArrayList();

            Iterator iterator = target.iterator();
            while (iterator.hasNext()) {
                ArrayList fila = (ArrayList)iterator.next();
                String descProd = fila.get(COL_DESC_PROD).toString();
                //if(descProd.startsWith(condicion) || descProd.endsWith(condicion)){
                if (descProd.contains(condicion)) {
                    filteredCollection.add(fila);
                }
            }

            //limpia las tablas auxiliares
            tableModelListaPrecioProductos.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModelListaPrecioProductos.fireTableDataChanged();
            setJTable(tblProductos);
            //iniciaProceso(false);
            tblModelListaSustitutos.clearTable();

            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_PedidoVenta, 0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_Prod_Promociones_temporal,
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesVentas.vArrayList_Prod_Promociones, 0);

            lblF7.setText("[ F7 ] Quitar Filtro");
        }
    }

    /**
     *
     * @author ERIOS
     * @since 26.11.2013
     */
    private void deshabilitaProducto() {
        txtProducto.setEnabled(false);
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000 * 2);
                } catch (InterruptedException e) {
                }
                txtProducto.setEnabled(true);
                pasoTarjeta = false;
                FarmaUtility.moveFocus(txtProducto);
            }
        }.start();
    }

    private void txtProducto_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void abrePedidosDelivery() {

        dlgResumenPedido.abrePedidosDelivery(true);
        cerrarVentana(true);
    }

    void setResumenPedido(DlgResumenPedido dlgResumenPedido) {
        this.dlgResumenPedido = dlgResumenPedido;
    }

    /**
     * Determinar si un producto es parte de algun pack
     * @author ASOSA
     * @since 23/10/2014
     * @return
     */
    public boolean isProdInPack() {
        boolean flag = false;
        int vFila = myJTable.getSelectedRow();
        String codigoProd = FarmaUtility.getValueFieldArrayList(tableModelListaPrecioProductos.data, vFila, COL_COD);
        flag = UtilityVentas.obtenerIndProdInPack(codigoProd);
        return flag;
    }

    /**
     * Metodo para forzar la llamada a el F5 de packs
     * @author ASOSA
     * @since 23/10/2014
     * @param e
     */
    private void lblF5_keyPressed(KeyEvent e) {
        if (VariablesVentas.vEsPedidoConvenio ||
            (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 1)) {

            FarmaUtility.showMessage(this, "No puede agregar estas promociones a un " + "pedido por convenio.",
                                     txtProducto);
            return;
        } else {

            //vEjecutaAccionTeclaListado = false;
            int vFila = myJTable.getSelectedRow();
            Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
            String indProm = (String)(myJTable.getValueAt(vFila, 17));

            if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS))
                VariablesVentas.vCodProdFiltro = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
            else
                VariablesVentas.vCodProdFiltro = "";

            if (indProm.equalsIgnoreCase("S")) { //if(!valor.booleanValue())
                muestraPromocionProd(VariablesVentas.vCodProdFiltro);
                //else
                //  FarmaUtility.showMessage(this,"El Producto está en una Promoción ya seleccionada",txtProducto);
            } else
                muestraPromocionProd(VariablesVentas.vCodProdFiltro);
        }
    }
}
