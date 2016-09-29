package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProductosGratuitos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      20.03.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgListaProductosGratuitos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaProductosGratuitos.class);

    private Frame myParentFrame;

    private JTable myJTable;

    private FarmaTableModel tableModelListaPrecioProductosGratuitos;
    private FarmaTableModel tableModelListaProductosAlternativosGratuitos;

    private String descUnidPres = "";
    private String stkProd = "";
    private String valPrecPres = "";
    private String valFracProd = "";
    private String indProdCong = "";
    private String valPrecLista = "";
    private String valPrecVta = "";

    private int totalItems = 0;
    private double totalVenta = 0;

    private String indicadorItems = "";

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
    private JTable tblListaProductosAlternativos = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabel lblTotalVenta = new JLabel();
    private JLabel lblTotalVenta_T = new JLabel();
    private JPanel jPanel4 = new JPanel();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblCliente = new JLabelWhite();
    private JLabelWhite lblCliente_T = new JLabelWhite();
    private JButtonLabel btnProdAlternativos = new JButtonLabel();

    //Este es parametro de prueba. erios
    JLabel parametro;
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaProductosGratuitos() {
        this(null, "", false);
    }

    public DlgListaProductosGratuitos(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(737, 514));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos y Precios - Distribucion Gratuita");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
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
        jPanel3.setBounds(new Rectangle(15, 80, 345, 45));
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
        lblF6.setBounds(new Rectangle(455, 435, 140, 20));
        lblF2.setText("[ F2 ] Ver Complementarios");
        lblF2.setBounds(new Rectangle(280, 435, 170, 20));
        lblF1.setText("[ F1 ] Impulso");
        lblF1.setBounds(new Rectangle(165, 435, 110, 20));
        jScrollPane2.setBounds(new Rectangle(15, 340, 700, 90));
        jScrollPane2.setBackground(new Color(255, 130, 14));
        jPanel2.setBounds(new Rectangle(15, 320, 700, 20));
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
        lblDescLab_Prod.setBounds(new Rectangle(225, 0, 380, 20));
        lblDescLab_Prod.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Prod.setForeground(Color.white);
        jSeparator1.setBounds(new Rectangle(200, 0, 15, 20));
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
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
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
        lblF11.setBounds(new Rectangle(520, 465, 100, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(630, 465, 85, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 435, 145, 20));
        lblTotalVenta.setText("0.00");
        lblTotalVenta.setBounds(new Rectangle(140, 25, 85, 15));
        lblTotalVenta.setFont(new Font("SansSerif", 1, 15));
        lblTotalVenta.setForeground(Color.white);
        lblTotalVenta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalVenta_T.setText("Total venta : S/.");
        lblTotalVenta_T.setBounds(new Rectangle(15, 25, 120, 15));
        lblTotalVenta_T.setFont(new Font("SansSerif", 1, 15));
        lblTotalVenta_T.setForeground(Color.white);
        jPanel4.setBounds(new Rectangle(370, 80, 345, 45));
        jPanel4.setBackground(new Color(43, 141, 39));
        jPanel4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel4.setLayout(null);
        pnlTitle1.setBounds(new Rectangle(15, 10, 700, 30));
        lblCliente.setBounds(new Rectangle(60, 5, 205, 20));
        lblCliente.setText(" ");
        lblCliente_T.setText("Cliente:");
        lblCliente_T.setBounds(new Rectangle(10, 5, 55, 20));
        btnProdAlternativos.setText("Productos Alternativos");
        btnProdAlternativos.setBounds(new Rectangle(10, 0, 150, 20));
        btnProdAlternativos.setMnemonic('a');
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
        lblF7.setBounds(new Rectangle(600, 435, 117, 19));
        lblF7.setText("[ F7 ] Quitar Filtro");
        jLabelFunction1.setBounds(new Rectangle(15, 465, 145, 20));
        jLabelFunction1.setText("[ F9 ] Pedido Delivery");
        lblF10.setBounds(new Rectangle(165, 465, 145, 20));
        lblF10.setText("[ F10 ] Falta Cero");
        pnlTitle1.add(lblCliente, null);
        pnlTitle1.add(lblCliente_T, null);
        jPanel3.add(lblPrecio, null);
        jPanel3.add(lblPrecio_T, null);
        jPanel3.add(lblUnidad, null);
        jPanel3.add(lblUnidad_T, null);
        jScrollPane2.getViewport();
        jPanel2.add(btnProdAlternativos, null);
        jPanel2.add(jSeparator2, null);
        jPanel2.add(lblDescLab_Alter, null);
        jScrollPane1.getViewport();
        jPanel1.add(btnRelacionProductos, null);
        jPanel1.add(lblDescLab_Prod, null);
        jPanel1.add(jSeparator1, null);
        pnlIngresarProductos.add(btnBuscar, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jPanel4.add(lblTotalVenta_T, null);
        jPanel4.add(lblItems_T, null);
        jPanel4.add(lblItems, null);
        jPanel4.add(lblTotalVenta, null);
        jContentPane.add(lblF10, null);
        jContentPane.add(jLabelFunction1, null);
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
        jScrollPane2.getViewport().add(tblListaProductosAlternativos, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(jPanel2, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlIngresarProductos, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableListaPreciosProductos();
        initTableProductosAlternativos();
        setJTable(tblProductos);
        iniciaProceso(true);
        FarmaVariables.vAceptar = false;
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaPreciosProductos() {
        tableModelListaPrecioProductosGratuitos =
                new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos,
                                    0);
        tableModelListaPrecioProductosGratuitos.data = VariablesVentas.tableModelListaGlobalProductos.data;
        FarmaUtility.initSelectList(tblProductos, tableModelListaPrecioProductosGratuitos,
                                    ConstantsVentas.columnsListaProductos);
        tblProductos.setName(ConstantsVentas.NAME_TABLA_PRODUCTOS);
    }

    private void initTableProductosAlternativos() {
        /*tableModelListaProductosAlternativosGratuitos = new FarmaTableModel(ConstantsVentas.columnsListaProductosAlternativos,ConstantsVentas.defaultValuesListaProductosAlternativos,0);
    FarmaUtility.initSelectList(tblListaProductosAlternativos,tableModelListaProductosAlternativosGratuitos,ConstantsVentas.columnsListaProductosAlternativos);*/
        tableModelListaProductosAlternativosGratuitos =
                new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos,
                                    0);
        FarmaUtility.initSelectList(tblListaProductosAlternativos, tableModelListaProductosAlternativosGratuitos,
                                    ConstantsVentas.columnsListaProductos);
        tblListaProductosAlternativos.setName(ConstantsVentas.NAME_TABLA_SUSTITUTOS);
        muestraProductosAlternativos();
    }

    public void iniciaProceso(boolean pInicializar) {
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
        FarmaUtility.ponerCheckJTable(tblProductos, 1, myArray, 0);
        //if ( !pInicializar )
        actualizaListaProductosAlternativos();
        muestraNombreLab(4, lblDescLab_Prod);
        muestraInfoProd();
        colocaTotalesPedido();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        evaluaTitulo();
        FarmaUtility.moveFocus(txtProducto);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, myJTable, txtProducto, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //ERIOS 03.07.2013 Limpia la caja de texto
            limpiaCadenaAlfanumerica(txtProducto);
            try {
                e.consume();
                if (myJTable.getSelectedRow() >= 0) {
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
                    for (int k = 0; k < myJTable.getRowCount(); k++) {
                        codigo = ((String)myJTable.getValueAt(k, 1)).trim();
                        if (codigo.equalsIgnoreCase(productoBuscar)) {
                            FarmaGridUtils.showCell(myJTable, k, 0);
                            break;
                        }
                    }
                    String actualCodigo = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 1))).trim();
                    String actualProducto =
                        ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 2))).trim().toUpperCase();
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
                    //muestraNombreLab(4, lblDescLab_Prod);
                    //muestraInfoProd();
                    //btnBuscar.doClick();
                    /*if (!(FarmaUtility.findTextInJTable(myJTable, txtProducto.getText().trim(), 1, 2)) )
          {
            //FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtProducto);
            //return;
          } else{
            muestraNombreLab(4, lblDescLab_Prod);
            muestraInfoProd();
            btnBuscar.doClick();
          }*/
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
        FarmaGridUtils.buscarDescripcion(e, myJTable, txtProducto, 2);
        muestraNombreLab(4, lblDescLab_Prod);
        muestraInfoProd();
        if (!(e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
            if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {
                actualizaListaProductosAlternativos();
            }
        }
        colocaTotalesPedido();
    }

    private void btnProdAlternativos_actionPerformed(ActionEvent e) {
        setJTable(tblListaProductosAlternativos);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        setJTable(tblProductos);
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

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            muestraDetalleProducto();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (esProductoFarma()) {
                FarmaUtility.showMessage(this, "Operación no valida para productos del Tipo Farma", txtProducto);
                return;
            }
            muestraProductosComplementarios();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            cargaListaFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //lblDescFiltro.setText("");
                VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
                tableModelListaProductosAlternativosGratuitos.clearTable();
                tableModelListaPrecioProductosGratuitos.clearTable();
                tableModelListaPrecioProductosGratuitos.fireTableDataChanged();
                tableModelListaPrecioProductosGratuitos.data = VariablesVentas.tableModelListaGlobalProductos.data;
                setJTable(tblProductos);
                iniciaProceso(false);
                FarmaUtility.moveFocus(txtProducto);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (UtilityVentas.evaluaPedidoDelivery(this, txtProducto, VariablesVentas.vArrayList_PedidoVenta))
                evaluaTitulo();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F10(e)) {
            faltacero();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            aceptaOperacion();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cancelaOperacion();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void muestraNombreLab(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            String nombreLab = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), pColumna))).trim();
            pLabel.setText(nombreLab);
        }
    }

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        String codProd = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 1))).trim();
        try {
            DBVentas.obtieneInfoProducto(pArrayList, codProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en arreglo. \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    private void muestraInfoProd() {
        if (myJTable.getRowCount() <= 0)
            return;
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            valPrecPres = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            indProdCong = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            valPrecVta = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
        } else {
            stkProd = "";
            descUnidPres = "";
            valFracProd = "";
            valPrecPres = "";
            indProdCong = "";
            valPrecVta = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtProducto);
        }
        lblUnidad.setText(descUnidPres);
        lblPrecio.setText(valPrecPres);
        myJTable.setValueAt(stkProd, myJTable.getSelectedRow(), 5);
        myJTable.setValueAt(valPrecVta, myJTable.getSelectedRow(), 6);
        VariablesVentas.vVal_Frac = valFracProd;
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
        if (myJTable.getRowCount() == 0)
            return;
        VariablesVentas.vCod_Prod = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 1))).trim();
        VariablesVentas.vDesc_Prod = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 2))).trim();
        VariablesVentas.vNom_Lab = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 4))).trim();
        DlgDetalleProducto dlgDetalleProducto = new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);
    }

    private void muestraIngresoCantidad() {
        if (myJTable.getRowCount() == 0)
            return;
        VariablesVentas.vCod_Prod = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 1))).trim();
        VariablesVentas.vDesc_Prod = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 2))).trim();
        VariablesVentas.vNom_Lab = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 4))).trim();
        VariablesVentas.vPorc_Igv_Prod = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 11))).trim();
        VariablesVentas.vCant_Ingresada_Temp = "0";
        DlgIngresoCantidad dlgIngresoCantidad = new DlgIngresoCantidad(myParentFrame, "", true);
        VariablesVentas.vIngresaCant_ResumenPed = false;
        dlgIngresoCantidad.setVisible(true);
        if (FarmaVariables.vAceptar) {
            seleccionaProducto();
            FarmaVariables.vAceptar = false;
        }
    }

    private void colocaTotalesPedido() {
        calculaTotalVentaPedido();
        totalItems = VariablesVentas.vArrayList_PedidoVenta.size();
        lblItems.setText("" + totalItems);
        lblTotalVenta.setText(FarmaUtility.formatNumber(totalVenta, 2));
    }

    private void calculaTotalVentaPedido() {
        totalVenta = 0;
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++)
            totalVenta +=
                    FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(7)).trim());
    }

    private void verificaCheckJTable() {
        if (myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS))
            actualizaListaProductosAlternativos();
        int row = myJTable.getSelectedRow();
        Boolean valor = (Boolean)(myJTable.getValueAt(myJTable.getSelectedRow(), 0));
        if (valor.booleanValue()) {
            deseleccionaProducto();
        } else {
            muestraInfoProd();
            if (!validaStockDisponible()) {
                return;
            }
            if (!validaProductoTomaInventario(row)) {
                FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario",
                                         txtProducto);
                return;
            }
            muestraIngresoCantidad();
        }
        //txtProducto.selectAll();
        muestraNombreLab(4, lblDescLab_Prod);
        //muestraInfoProd();
    }

    private void seleccionaProducto() {
        Boolean valorTmp = (Boolean)(myJTable.getValueAt(myJTable.getSelectedRow(), 0));
        VariablesVentas.vTotalPrecVtaProd =
                (Double.parseDouble(VariablesVentas.vCant_Ingresada) * Double.parseDouble(VariablesVentas.vVal_Prec_Vta));
        if (!actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                          ConstantsVentas.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR))
            return;
        FarmaUtility.setCheckValue(myJTable, false);
        Boolean valor = (Boolean)(myJTable.getValueAt(myJTable.getSelectedRow(), 0));
        operaProductoSeleccionadoEnArrayList(valor);
        pintaCheckOtroJTable(myJTable, valorTmp);
        //indicadorItems = FarmaConstants.INDICADOR_S;
        colocaTotalesPedido();
        //FarmaUtility.aceptarTransaccion();
    }

    private void deseleccionaProducto() {
        String cantidad = "";
        VariablesVentas.vCod_Prod = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 1))).trim();
        String codigoTmp = "";
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++) {
            codigoTmp = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(0);
            if (VariablesVentas.vCod_Prod.equalsIgnoreCase(codigoTmp)) {
                cantidad = (String)((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(4);
                break;
            }
        }
        Boolean valorTmp = (Boolean)(myJTable.getValueAt(myJTable.getSelectedRow(), 0));
        if (!actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, Integer.parseInt(cantidad),
                                          ConstantsVentas.INDICADOR_D,
                                          ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR))
            return;
        FarmaUtility.setCheckValue(myJTable, false);
        Boolean valor = (Boolean)(myJTable.getValueAt(myJTable.getSelectedRow(), 0));
        operaProductoSeleccionadoEnArrayList(valor);
        pintaCheckOtroJTable(myJTable, valorTmp);
        //indicadorItems = FarmaConstants.INDICADOR_N;
        colocaTotalesPedido();
    }

    private void operaProductoSeleccionadoEnArrayList(Boolean valor) {
        ArrayList myArray = new ArrayList();
        myArray.add(VariablesVentas.vCod_Prod);
        myArray.add(VariablesVentas.vDesc_Prod);
        myArray.add(VariablesVentas.vUnid_Vta);
        myArray.add(VariablesVentas.vVal_Prec_Lista);
        myArray.add(VariablesVentas.vCant_Ingresada);
        myArray.add(VariablesVentas.vPorc_Dcto_1);
        myArray.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta), 3));
        myArray.add(FarmaUtility.formatNumber(0.00, 2)); //colocando subtotal en 0.00
        //String valTotalBono = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Bono) * FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada)) / FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Frac));
        //VariablesVentas.vVal_Bono = valTotalBono;
        myArray.add("0.00"); //colocando el bono en 0.00
        myArray.add(VariablesVentas.vNom_Lab);
        myArray.add(VariablesVentas.vVal_Frac);
        myArray.add(VariablesVentas.vPorc_Igv_Prod);
        String valIgv =
            FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) - (FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) /
                                                                                                       (1 +
                                                                                                        (FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Igv_Prod) /
                                                                                                         100)))) *
                                      FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada));
        log.debug(valIgv);
        VariablesVentas.vVal_Igv_Prod = valIgv;
        myArray.add(VariablesVentas.vVal_Igv_Prod);
        FarmaUtility.operaListaProd(VariablesVentas.vArrayList_PedidoVenta, myArray, valor, 0);
        log.debug("size : " + VariablesVentas.vArrayList_PedidoVenta.size());
        log.debug("array : " + VariablesVentas.vArrayList_PedidoVenta);
    }

    private boolean actualizaStkComprometidoProd(String pCodigoProducto, int pCantidad, String pTipoStkComprometido,
                                                 String pTipoRespaldoStock) {
        try {
            DBVentas.actualizaStkComprometidoProd(pCodigoProducto, pCantidad, pTipoStkComprometido);
            DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", pTipoRespaldoStock, pCantidad,
                                            Integer.parseInt(VariablesVentas.vVal_Frac),
                                            ConstantsPtoVenta.MODULO_VENTAS);
            FarmaUtility.aceptarTransaccion();
            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al Actualizar Stock del Producto.\nPonganse en contacto con el area de Sistemas.\nError - " +
                                     sql.getMessage(), txtProducto);
            return false;
        }
    }

    private boolean validaProductoTomaInventario(int pRow) {
        if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
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

    private void cancelaOperacion() {
        String codProd = "";
        String codProdTmp = "";
        boolean existe = false;
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++) {
            codProd = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(0));
            String cantidad = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i)).get(4));
            for (int j = 0; j < VariablesVentas.vArrayList_ResumenPedido.size(); j++) {
                codProdTmp = (String)(((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(j)).get(0));
                if (codProd.equalsIgnoreCase(codProdTmp)) {
                    existe = true;
                    break;
                }
            }
            if (!existe)
                if (!actualizaStkComprometidoProd(codProd, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),
                                                  ConstantsVentas.INDICADOR_D,
                                                  ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR))
                    return;
            existe = false;
        }
        for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++) {
            codProd = (String)(((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(0));
            String cantidad = (String)(((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(4));
            String cantidadTmp = "";
            for (int j = 0; j < VariablesVentas.vArrayList_PedidoVenta.size(); j++) {
                codProdTmp = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(j)).get(0));
                if (codProd.equalsIgnoreCase(codProdTmp)) {
                    existe = true;
                    cantidadTmp = (String)(((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(j)).get(4));
                    break;
                }
            }
            if (!existe)
                if (!actualizaStkComprometidoProd(codProd, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),
                                                  ConstantsVentas.INDICADOR_A,
                                                  ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR))
                    return;
                else {
                    if (Integer.parseInt(cantidad) < Integer.parseInt(cantidadTmp))
                        if (!actualizaStkComprometidoProd(codProd,
                                                          (Integer.parseInt(cantidadTmp) - Integer.parseInt(cantidad)),
                                                          ConstantsVentas.INDICADOR_D,
                                                          ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR))
                            return;
                        else if (Integer.parseInt(cantidad) > Integer.parseInt(cantidadTmp))
                            if (!actualizaStkComprometidoProd(codProd,
                                                              (Integer.parseInt(cantidad) - Integer.parseInt(cantidadTmp)),
                                                              ConstantsVentas.INDICADOR_A,
                                                              ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR))
                                return;
                }
            existe = false;
        }
        cerrarVentana(false);
    }

    private void aceptaOperacion() {
        VariablesVentas.vArrayList_ResumenPedido = new ArrayList();
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++)
            VariablesVentas.vArrayList_ResumenPedido.add((ArrayList)VariablesVentas.vArrayList_PedidoVenta.get(i));
        cerrarVentana(true);
    }

    private void cargaListaFiltro() {
        DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(myParentFrame, "", true);
        dlgFiltroProductos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            tableModelListaProductosAlternativosGratuitos.clearTable();
            txtProducto.setText("");
            filtrarTablaProductos(VariablesPtoVenta.vTipoFiltro, VariablesPtoVenta.vCodFiltro);
            setJTable(tblProductos);
            iniciaProceso(false);
            FarmaVariables.vAceptar = false;
            //lblDescFiltro.setText("(" + VariablesAdmCentral.vDesc_Cat_Filtro + " - " + VariablesAdmCentral.vDescFiltro + ")");
        }
    }

    private void filtrarTablaProductos(String pTipoFiltro, String pCodFiltro) {
        try {
            tableModelListaPrecioProductosGratuitos.clearTable();
            tableModelListaPrecioProductosGratuitos.fireTableDataChanged();
            DBVentas.cargaListaProductosVenta_Filtro(tableModelListaPrecioProductosGratuitos, pTipoFiltro, pCodFiltro);
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductosGratuitos, 2,
                                 FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(this, "Error al obtener Lista de Productos Filtrado!!!", txtProducto);
        }
    }

    private void muestraProductosAlternativos() {
        try {
            if (tblProductos.getSelectedRow() >= 0) {
                String codigoProducto = (String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), 1));
                DBVentas.cargaListaProductosAlternativos(tableModelListaProductosAlternativosGratuitos,
                                                         codigoProducto);
                FarmaUtility.ordenar(tblListaProductosAlternativos, tableModelListaProductosAlternativosGratuitos, 7,
                                     FarmaConstants.ORDEN_DESCENDENTE);
                tblListaProductosAlternativos.repaint();
            }
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            FarmaUtility.showMessage(this, "Error al Listar Productos Alternativos.\n" +
                    sqlException, txtProducto);
        }
    }

    /*private void actualizaListaProductosAlternativos()
  {
    muestraProductosAlternativos();
    FarmaUtility.ponerCheckJTable(tblListaProductosAlternativos,1,VariablesVentas.vArrayList_PedidoVenta,0);
  }*/

    private void actualizaListaProductosAlternativos() {
        tableModelListaProductosAlternativosGratuitos.clearTable();
        tableModelListaProductosAlternativosGratuitos.fireTableDataChanged();
        if (esProductoFarma()) {
            muestraProductosAlternativos();
            FarmaUtility.ponerCheckJTable(tblListaProductosAlternativos, 1, VariablesVentas.vArrayList_PedidoVenta, 0);
        }
    }

    private void pintaCheckOtroJTable(JTable pActualJTable, Boolean pValor) {
        //log.debug(pValor.booleanValue());
        if (pActualJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS)) {
            FarmaUtility.setearCheckInRow(tblListaProductosAlternativos, pValor, true, true, VariablesVentas.vCod_Prod,
                                          1);
            tblListaProductosAlternativos.repaint();
        } else if (pActualJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_SUSTITUTOS)) {
            FarmaUtility.setearCheckInRow(tblProductos, pValor, true, true, VariablesVentas.vCod_Prod, 1);
            tblProductos.repaint();
        }
    }

    private void muestraProductosComplementarios() {
        if (myJTable.getRowCount() == 0)
            return;
        VariablesVentas.vCodProdOrigen_Comple = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 1))).trim();
        VariablesVentas.vDescProdOrigen_Comple = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 2))).trim();
        DlgProductosComplementarios dlgProductosComplementarios =
            new DlgProductosComplementarios(myParentFrame, "", true);
        dlgProductosComplementarios.setVisible(true);
        if (FarmaVariables.vAceptar) {
            log.debug("VariablesVentas.vCodProdComplementario : " + VariablesVentas.vCodProdComplementario);
            btnRelacionProductos.doClick();
            buscaCodigoEnJtable(VariablesVentas.vCodProdComplementario);
            FarmaVariables.vAceptar = false;
        }
    }

    private void buscaCodigoEnJtable(String pCodBusqueda) {
        String codTmp = "";
        for (int i = 0; i < tblProductos.getRowCount(); i++) {
            codTmp = ((String)(myJTable.getValueAt(i, 1))).trim();
            if (codTmp.equalsIgnoreCase(pCodBusqueda)) {
                FarmaGridUtils.showCell(tblProductos, i, 0);
                FarmaUtility.setearRegistro(tblProductos, i, txtProducto, 2);
                FarmaUtility.moveFocus(txtProducto);
                return;
            }
        }
    }

    private void evaluaTitulo() {
        if (VariablesVentas.vEsPedidoDelivery)
            this.setTitle("Lista de Productos y Precios - Distribucion Gratuita - Pedido Delivery");
        else
            this.setTitle("Lista de Productos y Precios - Distribucion Gratuita");
    }

    private boolean esProductoFarma() {
        String indicador = ((String)myJTable.getValueAt(myJTable.getSelectedRow(), 12)).trim();
        if (indicador.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    //inicio adicion Paulo 15/06/2006

    private void faltacero() {
        if (!validaStockDisponible()) {
            try {
                VariablesVentas.vCod_Prod = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 1))).trim();
                DBVentas.ingresaStockCero();
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se ha Registrado Producto para reposicion", txtProducto);
            } catch (SQLException sqlException) {
                FarmaUtility.liberarTransaccion();
                log.error("", sqlException);
                FarmaUtility.showMessage(this, "Error al Ingresar en la Tabla Falta Cero.\n" +
                        sqlException, txtProducto);
            }
        }
    }
    //fin adicion Paulo 15/06/2006

}
