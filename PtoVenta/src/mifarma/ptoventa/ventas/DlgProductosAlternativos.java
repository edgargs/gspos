package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.DBConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProductosAlternativos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS       08.04.2008   Creacion <br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class DlgProductosAlternativos extends JDialog {
    /* ************************************************************************** */
    /*                           DECLARACION PROPIEDADES                          */
    /* ************************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgProductosAlternativos.class);

    private Frame myParentFrame;

    private FarmaTableModel tableModelListaAlternativos;

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

    private String tipoProd = "";
    private String bonoProd = "";

    // kmoncada 02.07.2014 obtiene el estado del producto.
    private String estadoProd = "";


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
    //private final int COL_PREC_LISTA = 10;
    private final int COL_IND_IGV = 11;
    //private final int COL_IND_FARMA = 12;
    private final int COL_IND_VIRT = 13;
    //private final int COL_TIPO_VIRT = 14;
    private final int COL_IND_REFRIG = 15;
    private final int COL_TIPO_PROD = 16;
    private final int COL_IND_PROM = 17;
    //private final int COL_ORD_LISTA = 18;
    private final int COL_IND_ENCARTE = 19;
    private final int COL_ORIG_PROD = 20;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabel lblPrecio = new JLabel();
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
    private JLabel lblPrecio1 = new JLabel();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblProdOrigen_T = new JLabelWhite();
    private JLabelWhite lblLabOrigen_T = new JLabelWhite();
    private JLabelWhite lblProdOrigen = new JLabelWhite();
    private JLabelWhite lblLabOrigen = new JLabelWhite();
    private JLabelWhite lblStockAdic_T = new JLabelWhite();
    private JLabelWhite lblStockAdic = new JLabelWhite();
    private JLabelWhite lblUnidFracLoc = new JLabelWhite();


    /* ************************************************************************** */
    /*                              CONSTRUCTORES                                 */
    /* ************************************************************************** */

    public DlgProductosAlternativos() {
        this(null, "", false);
    }

    public DlgProductosAlternativos(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(737, 441));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos Alternativos");
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
        jPanel3.setBounds(new Rectangle(15, 110, 505, 45));
        jPanel3.setBackground(new Color(43, 141, 39));
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel3.setLayout(null);
        lblPrecio.setBounds(new Rectangle(90, 25, 130, 15));
        lblPrecio.setFont(new Font("SansSerif", 1, 15));
        lblPrecio.setForeground(Color.white);
        lblPrecio_T.setText("Precio : S/.");
        lblPrecio_T.setBounds(new Rectangle(10, 25, 75, 15));
        lblPrecio_T.setFont(new Font("SansSerif", 1, 13));
        lblPrecio_T.setForeground(Color.white);
        lblUnidad.setBounds(new Rectangle(65, 5, 190, 15));
        lblUnidad.setFont(new Font("SansSerif", 1, 11));
        lblUnidad.setForeground(Color.white);
        lblUnidad_T.setText("Unidad :");
        lblUnidad_T.setBounds(new Rectangle(10, 5, 60, 15));
        lblUnidad_T.setFont(new Font("SansSerif", 1, 11));
        lblUnidad_T.setForeground(Color.white);
        lblF1.setText("[ F1 ] Info Prod");
        lblF1.setBounds(new Rectangle(165, 380, 110, 20));
        jScrollPane1.setBounds(new Rectangle(15, 185, 700, 165));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(15, 165, 700, 20));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        lblDescLab_Prod.setBounds(new Rectangle(160, 0, 345, 20));
        lblDescLab_Prod.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Prod.setForeground(Color.white);
        jSeparator1.setBounds(new Rectangle(150, 0, 15, 20));
        jSeparator1.setBackground(Color.black);
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        pnlIngresarProductos.setBounds(new Rectangle(15, 70, 700, 30));
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
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(630, 380, 85, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 380, 145, 20));
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
        jPanel5.setBounds(new Rectangle(15, 350, 700, 20));
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
        jPanelHeader1.setBounds(new Rectangle(15, 15, 695, 45));
        lblProdOrigen_T.setText("Producto:");
        lblProdOrigen_T.setBounds(new Rectangle(10, 5, 60, 15));
        lblLabOrigen_T.setText("Laboratorio:");
        lblLabOrigen_T.setBounds(new Rectangle(10, 25, 70, 15));
        lblProdOrigen.setBounds(new Rectangle(75, 5, 480, 15));
        lblLabOrigen.setBounds(new Rectangle(85, 25, 375, 15));
        lblStockAdic_T.setText("Stock adic.:");
        lblStockAdic_T.setBounds(new Rectangle(235, 25, 70, 15));
        lblStockAdic.setBounds(new Rectangle(305, 25, 55, 15));
        lblStockAdic.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUnidFracLoc.setBounds(new Rectangle(365, 25, 100, 15));
        jPanel3.add(lblUnidFracLoc, null);
        jPanel3.add(lblStockAdic, null);
        jPanel3.add(lblStockAdic_T, null);
        jPanel3.add(lblPrecio, null);
        jPanel3.add(lblPrecio_T, null);
        jPanel3.add(lblUnidad, null);
        jPanel3.add(lblUnidad_T, null);
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
        jPanelHeader1.add(lblLabOrigen, null);
        jPanelHeader1.add(lblProdOrigen, null);
        jPanelHeader1.add(lblLabOrigen_T, null);
        jPanelHeader1.add(lblProdOrigen_T, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(jPanel5, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(lblF1, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlIngresarProductos, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************** */
    /*                            METODO initialize                               */
    /* ************************************************************************** */

    private void initialize() {
        initTableListaAlternativos();
        iniciaProceso(true);
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************** */
    /*                          METODOS INICIALIZACION                            */
    /* ************************************************************************** */

    private void initTableListaAlternativos() {
        tableModelListaAlternativos =
                new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos,
                                    0);
        FarmaUtility.initSelectList(tblProductos, tableModelListaAlternativos, ConstantsVentas.columnsListaProductos);
        listaProductosAlternativos();
    }

    private void listaProductosAlternativos() {
        try {
            DBVentas.cargaListaProductosAlternativos(tableModelListaAlternativos,
                                                     VariablesVentas.vCodProdOrigen_Alter);
            FarmaUtility.setearPrimerRegistro(tblProductos, txtProducto, COL_DESC);
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(this, "Error al Listar Productos Alternativos.\n" +
                    sqlException, txtProducto);
        }
    }

    public void iniciaProceso(boolean pInicializar) {
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

        lblProdOrigen.setText(VariablesVentas.vDesc_Prod + " / " + VariablesVentas.vUnid_Vta);
        lblLabOrigen.setText(VariablesVentas.vNom_Lab);

        lblProdIgv.setVisible(false);
        lblProdProm.setVisible(false);
        lblProdEncarte.setVisible(false);
        lblProdCong.setVisible(false);
        lblProdRefrig.setVisible(false);
        FarmaUtility.moveFocus(txtProducto);

        if (VariablesVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesVentas.vIndPedConProdVirtual = false;
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos, txtProducto, COL_DESC);
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
        }
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        if (tblProductos.getRowCount() >= 0 && tableModelListaAlternativos.getRowCount() > 0) {
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
                    if (UtilityVentas.validaVentaConMenos(this,myParentFrame,tblProductos,txtProducto)) {
                        UtilityVentas.mostrarTratamiento(this,myParentFrame,tblProductos,COL_COD,COL_ORIG_PROD);
                        aceptaOperacion();
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
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            UtilityVentas.muestraDetalleProducto(myParentFrame,tblProductos,COL_COD,COL_DESC,COL_LAB);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (vSeleccionProductoAlter) {
                cerrarVentana(true);
            } else {
                cerrarVentana(false);
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************** */
    /*                       METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************** */

    private void muestraInfoProd() {
        if (tblProductos.getRowCount() <= 0)
            return;
        ArrayList myArray = new ArrayList();
        UtilityVentas.obtieneInfoProdEnArrayList(this,tblProductos,myArray,txtProducto,COL_COD,false);
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
            UtilityVentas.muestraIndTipoProd(tblProductos, lblIndTipoProd, tipoProd);
            bonoProd = ((String)((ArrayList)myArray.get(0)).get(10)).trim();
            stkFracLoc = FarmaUtility.getValueFieldArrayList(myArray, 0, 11);
            descUnidFracLoc = FarmaUtility.getValueFieldArrayList(myArray, 0, 12);
            estadoProd = ((String)((ArrayList)myArray.get(0)).get(13)).trim();
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

        int vFila = tblProductos.getSelectedRow();
        tblProductos.setValueAt(stkProd, vFila, COL_STOCK);
        tblProductos.setValueAt(valPrecVta, vFila, COL_PREC_VTA);
        tblProductos.setValueAt(descUnidVta, vFila, COL_UND_VTA);
        tblProductos.setValueAt(bonoProd, vFila, COL_BONO);

        VariablesVentas.vVal_Frac = valFracProd;
        VariablesVentas.vInd_Prod_Habil_Vta = indProdHabilVta;
        VariablesVentas.vPorc_Dcto_1 = porcDscto_1;
        tblProductos.repaint();
    }

    private void muestraIngresoCantidad() {
        
        boolean flagContinua = true;        
        flagContinua = UtilityVentas.validaVentaConMenos(this, myParentFrame, tblProductos, txtProducto);

        if (flagContinua) {
            //------------
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

            //Busca precio de convenio BTLMF
            UtilityConvenioBTLMF.obtienePrecioConvenio(this,txtProducto);

            DlgIngresoCantidad dlgIngresoCantidad;
            dlgIngresoCantidad = new DlgIngresoCantidad(myParentFrame, "", true);
            VariablesVentas.vIngresaCant_ResumenPed = false;
            dlgIngresoCantidad.setVisible(true);
            if (FarmaVariables.vAceptar) {
                UtilityVentas.seleccionaProducto(this, tblProductos, txtProducto);
                FarmaVariables.vAceptar = false;
                cerrarVentana(true);
            }
        }
    }

    private void verificaCheckJTable() {
        //String indProdProm = (String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),17));
        int vFila = tblProductos.getSelectedRow();
        String codigo = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);

        Boolean valor = (Boolean)(tblProductos.getValueAt(vFila, 0));
        if (valor.booleanValue()) {
            if (!UtilityVentas.buscar(VariablesVentas.vArrayList_Prod_Promociones, codigo) &&
                !UtilityVentas.buscar(VariablesVentas.vArrayList_Prod_Promociones_temporal, codigo)) {
                UtilityVentas.deseleccionaProducto(this,tblProductos, COL_COD);
                vSeleccionProductoAlter = true;
            } else {
                FarmaUtility.showMessage(this, "El Producto se encuentra en una Promoción", txtProducto);
                return;
            }
        } else {
            muestraInfoProd();
            VariablesVentas.vIndProdVirtual = FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_IND_VIRT);

            // KMONCADA 02.07.2014 VALIDA ESTADO DEL PRODUCTO
            if (!"A".equalsIgnoreCase(estadoProd)) {
                FarmaUtility.showMessage(this, "El Producto se encuentra inactivo en local.", txtProducto);
                return;
            }

            if (!UtilityVentas.validaStockDisponible(stkProd) &&
                !VariablesVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                return;
            }
            if (!UtilityVentas.validaProductoTomaInventario(indProdCong)) {
                FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario",
                                         txtProducto);
                return;
            }
            if (!UtilityVentas.validaProductoHabilVenta()) {
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

    /**
     * Muestra los indicadores del producto seleccionado.
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    private void muestraIndicadoresProducto() {
        UtilityVentas.muestraNombreLab(tblProductos,COL_LAB, lblDescLab_Prod);
        UtilityVentas.muestraProductoInafectoIgv(tblProductos,COL_IND_IGV, lblProdIgv);
        UtilityVentas.muestraProductoPromocion(tblProductos,COL_IND_PROM, lblProdProm,0);
        UtilityVentas.muestraProductoRefrigerado(tblProductos,COL_IND_REFRIG, lblProdRefrig);
        UtilityVentas.muestraIndTipoProd(tblProductos,COL_TIPO_PROD, lblIndTipoProd);
        muestraInfoProd();
        UtilityVentas.muestraProductoCongelado(tblProductos,indProdCong,lblProdCong);
        UtilityVentas.muestraProductoEncarte(tblProductos,COL_IND_ENCARTE, lblProdEncarte);
    }

    private void aceptaOperacion() {
        if (FarmaVariables.vAceptar) {
            UtilityVentas.seleccionaProducto(this, tblProductos, txtProducto);
            FarmaVariables.vAceptar = false;
            cerrarVentana(true);
        }
    }
}
