package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgFiltroProductos;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgPedidoReposicionNuevo extends JDialog

{
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgPedidoReposicionNuevo.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JPanelTitle pnlTitle3 = new JPanelTitle();
    private JLabelWhite lblMinExhib_T = new JLabelWhite();
    private JPanelWhite pnlWhite2 = new JPanelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JPanelWhite pnlWhite3 = new JPanelWhite();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelWhite pnlWhite4 = new JPanelWhite();
    private JPanelTitle pnlTitle4 = new JPanelTitle();
    private JLabelWhite lblRotacion_T = new JLabelWhite();
    private JLabelWhite lblUltimoPedido_T = new JLabelWhite();
    private JLabelWhite lblNoDias_T = new JLabelWhite();
    private JLabelWhite lblLaboratorio_T = new JLabelWhite();
    private JLabelWhite lblTransito_T = new JLabelWhite();
    private JLabelWhite lblMinDias_T = new JLabelWhite();
    private JLabelWhite lblMaxDias_T = new JLabelWhite();
    private JLabelWhite lblNSemana_T = new JLabelWhite();
    private JLabelWhite lblRotProm_T = new JLabelWhite();
    private JLabelWhite lblCantAnterior_T = new JLabelWhite();
    private JLabelWhite lblMinExhib = new JLabelWhite();
    private JLabelWhite lblTransito = new JLabelWhite();
    private JLabelWhite lblMinDias = new JLabelWhite();
    private JLabelWhite lblMaxDias = new JLabelWhite();
    private JLabelWhite lblNDias = new JLabelWhite();
    private JLabelWhite lblRotProm = new JLabelWhite();
    private JLabelWhite lblCantAnterior = new JLabelWhite();
    private JLabelWhite lblProductos = new JLabelWhite();
    private JLabelWhite lblItems = new JLabelWhite();
    private JLabelWhite lbl120d = new JLabelWhite();
    private JLabelWhite lbl90d = new JLabelWhite();
    private JLabelWhite lbl60d = new JLabelWhite();
    private JLabelWhite lbl30d = new JLabelWhite();
    private JLabelWhite lblItems_T = new JLabelWhite();
    private JLabelWhite lblProductos_T = new JLabelWhite();
    private JLabelWhite lbl120d_T = new JLabelWhite();
    private JLabelWhite lbl90d_T = new JLabelWhite();
    private JLabelWhite lbl60d_T = new JLabelWhite();
    private JLabelWhite lbl30d_T = new JLabelWhite();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabelWhite lblRotacionProm_T = new JLabelWhite();
    private JLabelWhite lblMinimoDias_T = new JLabelWhite();
    private JLabelWhite lblSemanas = new JLabelWhite();
    private JLabelWhite lblMaximoDias_T = new JLabelWhite();
    private JLabelWhite lblRotacionPromedio = new JLabelWhite();
    private JLabelWhite lblMinimoDias = new JLabelWhite();
    private JLabelWhite lblMaximoDias = new JLabelWhite();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblFOrdenar = new JLabelFunction();
    private JButtonLabel lblFCero = new JButtonLabel();
    private JLabelOrange lblMensaje = new JLabelOrange();
    private JPanelWhite pnlWhite5 = new JPanelWhite();
    private JLabelWhite lblStockLocales = new JLabelWhite();
    private JLabelWhite lblStockAlmacen = new JLabelWhite();
    private JPanelTitle pnlTitle5 = new JPanelTitle();
    private JLabelWhite lblStockAlmacenT = new JLabelWhite();
    private JLabelWhite lblStockLocalesT = new JLabelWhite();
    private JLabel lblIndTipoProd = new JLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgPedidoReposicionNuevo() {
        this(null, "", false);
    }

    public DlgPedidoReposicionNuevo(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(764, 523));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedido de Reposicion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 740, 50));
        pnlTitle1.setBounds(new Rectangle(10, 65, 740, 20));
        scrListaProductos.setBounds(new Rectangle(10, 85, 740, 180));
        pnlTitle2.setBounds(new Rectangle(60, 270, 640, 20));
        pnlTitle3.setBounds(new Rectangle(325, 320, 375, 20));
        lblMinExhib_T.setText("Min Exhib");
        lblMinExhib_T.setBounds(new Rectangle(155, 5, 60, 15));
        lblMinExhib_T.setHorizontalAlignment(SwingConstants.CENTER);
        pnlWhite2.setBounds(new Rectangle(60, 290, 640, 20));
        pnlWhite2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblLaboratorio.setBounds(new Rectangle(5, 5, 145, 15));
        lblLaboratorio.setForeground(Color.black);
        pnlWhite3.setBounds(new Rectangle(325, 340, 375, 20));
        pnlWhite3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        btnRelacionProductos.setText("Relación de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 135, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(660, 450, 90, 20));
        pnlWhite4.setBounds(new Rectangle(60, 340, 230, 20));
        pnlWhite4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle4.setBounds(new Rectangle(60, 320, 230, 20));
        lblRotacion_T.setText("Rotación");
        lblRotacion_T.setBounds(new Rectangle(5, 5, 70, 15));
        lblRotacion_T.setForeground(Color.black);
        lblUltimoPedido_T.setText("Último Pedido:");
        lblUltimoPedido_T.setBounds(new Rectangle(5, 5, 85, 15));
        lblNoDias_T.setText("No Días");
        lblNoDias_T.setBounds(new Rectangle(5, 5, 70, 15));
        lblLaboratorio_T.setText("Laboratorio");
        lblLaboratorio_T.setBounds(new Rectangle(5, 5, 70, 15));
        lblTransito_T.setText("Transito");
        lblTransito_T.setBounds(new Rectangle(215, 5, 55, 15));
        lblTransito_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblMinDias_T.setText("Min Días");
        lblMinDias_T.setBounds(new Rectangle(275, 5, 55, 15));
        lblMinDias_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblMaxDias_T.setText("Max Días");
        lblMaxDias_T.setBounds(new Rectangle(335, 5, 55, 15));
        lblMaxDias_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblNSemana_T.setText("N Dias");
        lblNSemana_T.setBounds(new Rectangle(395, 5, 55, 15));
        lblNSemana_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblRotProm_T.setText("Rot Prom");
        lblRotProm_T.setBounds(new Rectangle(460, 5, 55, 15));
        lblRotProm_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblCantAnterior_T.setText("Cant Anterior");
        lblCantAnterior_T.setBounds(new Rectangle(530, 5, 80, 15));
        lblCantAnterior_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblMinExhib.setBounds(new Rectangle(155, 5, 55, 15));
        lblMinExhib.setForeground(Color.black);
        lblMinExhib.setHorizontalAlignment(SwingConstants.CENTER);
        lblTransito.setBounds(new Rectangle(215, 5, 55, 15));
        lblTransito.setForeground(Color.black);
        lblTransito.setHorizontalAlignment(SwingConstants.CENTER);
        lblMinDias.setBounds(new Rectangle(275, 5, 55, 15));
        lblMinDias.setForeground(Color.black);
        lblMinDias.setHorizontalAlignment(SwingConstants.CENTER);
        lblMaxDias.setBounds(new Rectangle(335, 5, 55, 15));
        lblMaxDias.setForeground(Color.black);
        lblMaxDias.setHorizontalAlignment(SwingConstants.CENTER);
        lblNDias.setBounds(new Rectangle(395, 5, 55, 15));
        lblNDias.setForeground(Color.black);
        lblNDias.setHorizontalAlignment(SwingConstants.CENTER);
        lblRotProm.setBounds(new Rectangle(460, 5, 55, 15));
        lblRotProm.setForeground(Color.black);
        lblRotProm.setHorizontalAlignment(SwingConstants.CENTER);
        lblCantAnterior.setBounds(new Rectangle(545, 5, 55, 15));
        lblCantAnterior.setForeground(Color.black);
        lblCantAnterior.setHorizontalAlignment(SwingConstants.CENTER);
        lblProductos.setBounds(new Rectangle(170, 5, 55, 15));
        lblProductos.setForeground(Color.black);
        lblProductos.setHorizontalAlignment(SwingConstants.CENTER);
        lblItems.setBounds(new Rectangle(105, 5, 40, 15));
        lblItems.setForeground(Color.black);
        lblItems.setHorizontalAlignment(SwingConstants.CENTER);
        lbl120d.setBounds(new Rectangle(105, 5, 55, 15));
        lbl120d.setForeground(Color.black);
        lbl120d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90d.setBounds(new Rectangle(160, 5, 55, 15));
        lbl90d.setForeground(Color.black);
        lbl90d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl60d.setBounds(new Rectangle(225, 5, 55, 15));
        lbl60d.setForeground(Color.black);
        lbl60d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl30d.setBounds(new Rectangle(285, 5, 60, 15));
        lbl30d.setForeground(Color.black);
        lbl30d.setHorizontalAlignment(SwingConstants.CENTER);
        lblItems_T.setText("Items");
        lblItems_T.setBounds(new Rectangle(110, 5, 45, 15));
        lblItems_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblProductos_T.setText("Productos");
        lblProductos_T.setBounds(new Rectangle(165, 5, 60, 15));
        lblProductos_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl120d_T.setText("120 d.");
        lbl120d_T.setBounds(new Rectangle(100, 5, 60, 15));
        lbl120d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90d_T.setText("90 d.");
        lbl90d_T.setBounds(new Rectangle(160, 5, 60, 15));
        lbl90d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl60d_T.setText("60 d.");
        lbl60d_T.setBounds(new Rectangle(220, 5, 60, 15));
        lbl60d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl30d_T.setText("30 d.");
        lbl30d_T.setBounds(new Rectangle(285, 5, 60, 15));
        lbl30d_T.setHorizontalAlignment(SwingConstants.CENTER);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(15, 5, 55, 15));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(75, 5, 180, 20));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        lblRotacionProm_T.setText("Rotación Prom:");
        lblRotacionProm_T.setBounds(new Rectangle(285, 5, 90, 15));
        lblMinimoDias_T.setText("Mínimo de Días:");
        lblMinimoDias_T.setBounds(new Rectangle(285, 25, 95, 15));
        lblSemanas.setText("dias(s)");
        lblSemanas.setBounds(new Rectangle(425, 5, 100, 15));
        lblMaximoDias_T.setText("Máximo de Días:");
        lblMaximoDias_T.setBounds(new Rectangle(445, 25, 90, 15));
        lblRotacionPromedio.setBounds(new Rectangle(380, 5, 35, 15));
        lblRotacionPromedio.setFont(new Font("SansSerif", 0, 11));
        lblMinimoDias.setBounds(new Rectangle(385, 25, 50, 15));
        lblMinimoDias.setFont(new Font("SansSerif", 0, 11));
        lblMaximoDias.setBounds(new Rectangle(545, 25, 35, 15));
        lblMaximoDias.setFont(new Font("SansSerif", 0, 11));
        lblF1.setText("[ F1 ] Ver Pedido");
        lblF1.setBounds(new Rectangle(10, 420, 105, 20));
        lblF2.setText("[ F2 ] Actualizar Pedido Reposición");
        lblF2.setBounds(new Rectangle(125, 420, 205, 20));
        lblF6.setText("[ F6 ] Filtrar Productos");
        lblF6.setBounds(new Rectangle(345, 420, 135, 20));
        lblF7.setText("[ F7 ] Eliminar Filtro");
        lblF7.setBounds(new Rectangle(490, 420, 140, 20));
        lblF8.setText("[ F8 ] Falta Cero");
        lblF8.setBounds(new Rectangle(640, 420, 110, 20));
        lblF9.setText("[ F9 ] Asignar Cantidad Maxima");
        lblF9.setBounds(new Rectangle(10, 450, 205, 20));
        lblFOrdenar.setBounds(new Rectangle(225, 450, 130, 20));
        lblFOrdenar.setText("[ F10 ] Ordenar");
        lblFCero.setText("- FALTA CERO");
        lblFCero.setVisible(false);
        lblFCero.setBounds(new Rectangle(135, 0, 80, 20));
        lblMensaje.setBounds(new Rectangle(15, 475, 575, 15));
        lblMensaje.setForeground(Color.red);
        lblMensaje.setLayout(null);
        pnlWhite5.setBounds(new Rectangle(60, 390, 230, 20));
        pnlWhite5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblStockLocales.setBounds(new Rectangle(20, 0, 50, 20));
        lblStockLocales.setForeground(Color.black);
        lblStockLocales.setHorizontalAlignment(SwingConstants.CENTER);
        lblStockAlmacen.setBounds(new Rectangle(135, 0, 55, 20));
        lblStockAlmacen.setForeground(Color.black);
        lblStockAlmacen.setHorizontalAlignment(SwingConstants.CENTER);
        pnlTitle5.setBounds(new Rectangle(60, 370, 230, 20));
        lblStockAlmacenT.setText("Stock Almacen");
        lblStockAlmacenT.setBounds(new Rectangle(115, 5, 100, 15));
        lblStockAlmacenT.setHorizontalAlignment(SwingConstants.CENTER);
        lblStockLocalesT.setText("Stock Locales");
        lblStockLocalesT.setBounds(new Rectangle(5, 5, 85, 15));
        lblIndTipoProd.setBounds(new Rectangle(545, 0, 140, 20));
        lblIndTipoProd.setFont(new Font("SansSerif", 1, 12));
        lblIndTipoProd.setBackground(new Color(44, 146, 24));
        lblIndTipoProd.setOpaque(true);
        lblIndTipoProd.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblIndTipoProd.setForeground(Color.white);
        lblIndTipoProd.setHorizontalAlignment(SwingConstants.CENTER);
        lblIndTipoProd.setHorizontalTextPosition(SwingConstants.CENTER);
        pnlWhite5.add(lblStockLocales, null);
        pnlWhite5.add(lblStockAlmacen, null);
        pnlTitle5.add(lblStockAlmacenT, null);
        pnlTitle5.add(lblStockLocalesT, null);
        pnlWhite2.add(lblCantAnterior, null);
        pnlWhite2.add(lblRotProm, null);
        pnlWhite2.add(lblNDias, null);
        pnlWhite2.add(lblMaxDias, null);
        pnlWhite2.add(lblMinDias, null);
        pnlWhite2.add(lblTransito, null);
        pnlWhite2.add(lblMinExhib, null);
        pnlWhite2.add(lblLaboratorio, null);
        pnlTitle4.add(lblProductos_T, null);
        pnlTitle4.add(lblItems_T, null);
        pnlTitle4.add(lblUltimoPedido_T, null);
        jContentPane.add(pnlTitle5, null);
        jContentPane.add(pnlWhite5, null);
        jContentPane.add(lblMensaje, null);
        jContentPane.add(lblFOrdenar, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblF7, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(pnlTitle4, null);
        jContentPane.add(pnlWhite4, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlWhite3, null);
        pnlWhite4.add(lblItems, null);
        pnlWhite4.add(lblProductos, null);
        jContentPane.add(pnlWhite2, null);
        jContentPane.add(pnlTitle3, null);
        jContentPane.add(pnlTitle2, null);
        pnlWhite3.add(lbl30d, null);
        pnlWhite3.add(lbl60d, null);
        pnlWhite3.add(lbl90d, null);
        pnlWhite3.add(lbl120d, null);
        pnlWhite3.add(lblRotacion_T, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle3.add(lbl30d_T, null);
        pnlTitle3.add(lbl60d_T, null);
        pnlTitle3.add(lbl90d_T, null);
        pnlTitle3.add(lbl120d_T, null);
        pnlTitle3.add(lblNoDias_T, null);
        pnlTitle2.add(lblCantAnterior_T, null);
        pnlTitle2.add(lblRotProm_T, null);
        pnlTitle2.add(lblNSemana_T, null);
        pnlTitle2.add(lblMaxDias_T, null);
        pnlTitle2.add(lblMinDias_T, null);
        pnlTitle2.add(lblTransito_T, null);
        pnlTitle2.add(lblLaboratorio_T, null);
        pnlTitle2.add(lblMinExhib_T, null);
        pnlTitle1.add(lblIndTipoProd, null);
        pnlTitle1.add(lblFCero, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlHeader1, null);
        pnlHeader1.add(lblMaximoDias, null);
        pnlHeader1.add(lblMinimoDias, null);
        pnlHeader1.add(lblRotacionPromedio, null);
        pnlHeader1.add(lblMaximoDias_T, null);
        pnlHeader1.add(lblSemanas, null);
        pnlHeader1.add(lblMinimoDias_T, null);
        pnlHeader1.add(lblRotacionProm_T, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnBuscar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        mostrarFaltaCero(false);
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaProductosPedidoReposicionNuevo, ConstantsInventario.defaultValuesListaProductosPedidoReposicionNuevo,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                    ConstantsInventario.columnsListaProductosPedidoReposicionNuevo);
    }

    private void cargaListaProductos() {
        try {
            tableModel.clearTable();
            DlgProcesaProdReposicion dlg = new DlgProcesaProdReposicion(myParentFrame, "", true);
            dlg.setVariables(tableModel, "%", "%");
            dlg.setVisible(true);
            FarmaUtility.ordenar(tblListaProductos, tableModel, 2, FarmaConstants.ORDEN_ASCENDENTE);
            mostrarDetalles(0);
            UtilityInventario.muestraIndTipoProd(23, lblIndTipoProd, tblListaProductos);
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void initCabecera() {
        try {
            ArrayList array = new ArrayList();
            DBInventario.getCabeceraReposicion(array);
            array = (ArrayList)array.get(0);
            lblRotacionPromedio.setText(array.get(0).toString());
            lblMinimoDias.setText(array.get(1).toString());
            lblMaximoDias.setText(array.get(2).toString());
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la cabecera: \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void initUltimoPedido() {
        try {
            ArrayList array = new ArrayList();
            DBInventario.getUltimoPedidoReposicion(array);
            if (array.size() > 0) {
                array = (ArrayList)array.get(0);
                lblItems.setText(array.get(0).toString());
                lblProductos.setText(array.get(1).toString());
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al incializar el último pedido : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 2))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
                    return;
                }
                mostrarDetalles(tblListaProductos.getSelectedRow());
                UtilityInventario.muestraIndTipoProd(23, lblIndTipoProd, tblListaProductos);
            }
        }

        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 2);
        if (tableModel.getRowCount() > 0 && tblListaProductos.getSelectedRow() > -1) {
            mostrarDetalles(tblListaProductos.getSelectedRow());
            UtilityInventario.muestraIndTipoProd(23, lblIndTipoProd, tblListaProductos);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
        FarmaUtility.centrarVentana(this);
        initMaxMin();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresarCantidad();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            verPedido();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            recalculaMaxMin();
            obtieneFechaCalculo();
            mostrarFaltaCero(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            cargaFiltroProductos();
            mostrarFaltaCero(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            cargaListaProductos();
            mostrarFaltaCero(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            cargaListaProductosStkCero();
            mostrarFaltaCero(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            funcionF9();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F10(e)) {
            funcionF10();
        } /*else if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      if(tblListaProductos.getSelectedRow() >= 0)
        funcionF11();
      else
        FarmaUtility.showMessage(this,"Debe seleccionar un registro de la lista de productos.",txtBuscar);
    }*/ else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void mostrarDetalles(int row) {
        //
        String cantidad;
        /**Nuevo Cambio
     * @Autor: Luis Reque Orellana
     * @Fecha: 20/04/2007
     * */
        /*lblLaboratorio.setText(tblListaProductos.getValueAt(row,9).toString());
    lblMinExhib.setText(tblListaProductos.getValueAt(row,10).toString());
    lblTransito.setText(tblListaProductos.getValueAt(row,11).toString());
    lblMinDias.setText(tblListaProductos.getValueAt(row,12).toString());
    lblMaxDias.setText(tblListaProductos.getValueAt(row,13).toString());
    lblNDias.setText(tblListaProductos.getValueAt(row,14).toString());
    lblRotProm.setText(tblListaProductos.getValueAt(row,15).toString());
    lblCantAnterior.setText(tblListaProductos.getValueAt(row,16).toString());*/
        lblLaboratorio.setText(tblListaProductos.getValueAt(row, 9).toString());
        lblMinExhib.setText(tblListaProductos.getValueAt(row, 10).toString());
        lblTransito.setText(tblListaProductos.getValueAt(row, 11).toString());
        lblMinDias.setText(tblListaProductos.getValueAt(row, 12).toString());
        lblMaxDias.setText(tblListaProductos.getValueAt(row, 13).toString());
        lblNDias.setText(tblListaProductos.getValueAt(row, 14).toString());
        log.debug("RotProm " + tblListaProductos.getValueAt(row, 15).toString());
        lblRotProm.setText(tblListaProductos.getValueAt(row, 15).toString());
        lblCantAnterior.setText(tblListaProductos.getValueAt(row, 16).toString());
        //
        /*lbl30d.setText(tblListaProductos.getValueAt(row,17).toString());
    lbl60d.setText(tblListaProductos.getValueAt(row,18).toString());
    lbl90d.setText(tblListaProductos.getValueAt(row,19).toString());
    lbl120d.setText(tblListaProductos.getValueAt(row,20).toString());*/
        lbl30d.setText(tblListaProductos.getValueAt(row, 17).toString());
        lbl60d.setText(tblListaProductos.getValueAt(row, 18).toString());
        lbl90d.setText(tblListaProductos.getValueAt(row, 19).toString());
        lbl120d.setText(tblListaProductos.getValueAt(row, 20).toString());
        lblStockLocales.setText(tblListaProductos.getValueAt(row, 24).toString());
        lblStockAlmacen.setText(tblListaProductos.getValueAt(row, 25).toString());
        //if (UtilityInventario.obtieneIndLinea(this)) obtieneInfoProd(tblListaProductos.getValueAt(row,0).toString());

        try {
            cantidad = DBInventario.obtieneCantidad(tblListaProductos.getValueAt(row, 0).toString());
            lblCantAnterior.setText(cantidad);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la cantidad del producto", txtBuscar);
        }
    }

    private void cargaFiltroProductos() {
        DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(myParentFrame, "", true);
        dlgFiltroProductos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaProductos_filtro();
            FarmaVariables.vAceptar = false;
        }
    }

    private void recalculaMaxMin() {
        try {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "Se va a ejecutar el cálculo de Pedido de Reposición. \nEsta operación tardará unos minutos. ¿Desea continuar?")) {
                DBInventario.actualizaMaxMin();
                //Actualiza los indicadores de Stock de Locales
                //22.10.2007 dubilluz
                actIndStock();
                cargaListaProductos();
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al recalcular el Pedido de Reposición : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void ingresarCantidad() {
        int row = tblListaProductos.getSelectedRow();
        if (tblListaProductos.getValueAt(row, 5) /*4*/ /*6*/.toString().trim().equals("")) {
            if (validaVentaUltimosMeses(row)) {
                cargarCabecera();
                DlgPedidoReposicionIngresoCantidad dlgPedidoReposicionIngresoCantidad =
                    new DlgPedidoReposicionIngresoCantidad(myParentFrame, "", true);
                dlgPedidoReposicionIngresoCantidad.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    cargarCantidadIngresada();
                    FarmaVariables.vAceptar = false;
                    tblListaProductos.setRowSelectionInterval(VariablesInventario.vPos_PedRep,
                                                              VariablesInventario.vPos_PedRep);
                }
            }

        } else {
            FarmaUtility.showMessage(this, "No puede solicitar este producto", txtBuscar);
        }
    }

    private void cargarCabecera() {
        try {
            VariablesInventario.vFechaHora_PedRep = FarmaSearch.getFechaHoraBD(2);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la fecha del sistema : \n" +
                    sql.getMessage(), txtBuscar);

        }
        int pos = tblListaProductos.getSelectedRow();
        VariablesInventario.vPos_PedRep = pos;
        VariablesInventario.vCodProd_PedRep = tblListaProductos.getValueAt(pos, 0).toString();
        VariablesInventario.vNomProd_PedRep = tblListaProductos.getValueAt(pos, 2).toString();
        VariablesInventario.vUnidMed_PedRep = tblListaProductos.getValueAt(pos, 3).toString();
        /**Nuevo Cambio
     * @Autor: Luis Reque Orellana
     * @Fecha: 20/04/2007
     * */
        /*VariablesInventario.vValFrac_PedRep=tblListaProductos.getValueAt(pos,21).toString();
    VariablesInventario.vNomLab_PedRep=tblListaProductos.getValueAt(pos,9).toString();
    VariablesInventario.vCant_PedRep=tblListaProductos.getValueAt(pos,8).toString();
    VariablesInventario.vStkFisico_PedRep=tblListaProductos.getValueAt(pos,5).toString();
    VariablesInventario.vCantSug_PedRep=tblListaProductos.getValueAt(pos,7).toString();
    VariablesInventario.vCantMax_PedRep=tblListaProductos.getValueAt(pos,22).toString();*/
        VariablesInventario.vValFrac_PedRep = tblListaProductos.getValueAt(pos, 21).toString();
        VariablesInventario.vNomLab_PedRep = tblListaProductos.getValueAt(pos, 9).toString();
        VariablesInventario.vCant_PedRep = tblListaProductos.getValueAt(pos, 7).toString();
        VariablesInventario.vStkFisico_PedRep = tblListaProductos.getValueAt(pos, 4).toString();
        VariablesInventario.vCantSug_PedRep = tblListaProductos.getValueAt(pos, 6).toString();
        VariablesInventario.vCantMax_PedRep = tblListaProductos.getValueAt(pos, 22).toString();
        //Agregado DUBILLUZ 26.09.2007
        VariablesInventario.vCantAdicional = tblListaProductos.getValueAt(pos, 9).toString();
    }

    private void cargarCantidadIngresada() {
        int pos = VariablesInventario.vPos_PedRep;
        tblListaProductos.setValueAt(VariablesInventario.vCant_PedRep, pos, 7) /*6*/ /*8*/;
        tblListaProductos.repaint();
    }

    private void cargaListaProductos_filtro() {
        try {
            tableModel.clearTable();
            DlgProcesaProdReposicion dlg = new DlgProcesaProdReposicion(myParentFrame, "", true);
            dlg.setVariables(tableModel, VariablesPtoVenta.vTipoFiltro, VariablesPtoVenta.vCodFiltro);
            dlg.setVisible(true);

            FarmaUtility.ordenar(tblListaProductos, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
            if (tblListaProductos.getRowCount() == 0)
                mostrarDetallesBlanco();
            else {
                mostrarDetalles(0);
                UtilityInventario.muestraIndTipoProd(22, lblIndTipoProd, tblListaProductos);
            }

        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void verPedido() {
        cargarDetalle();
        DlgPedidoReposicionVer dlgPedidoReposicionVer = new DlgPedidoReposicionVer(myParentFrame, "", true);
        dlgPedidoReposicionVer.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(true);
        }
    }

    private void cargarDetalle() {
        VariablesInventario.vRotProm_PedRep = lblRotacionPromedio.getText();
        VariablesInventario.vMinDias_PedRep = lblMinimoDias.getText();
        VariablesInventario.vMaxDias_PedRep = lblMaximoDias.getText();
        VariablesInventario.vItemsUltPed_PedRep = lblItems.getText();
        VariablesInventario.vProdsUltPed_PedRep = lblProductos.getText();
    }

    private void initMaxMin() {
        String indPedRep;
        try {
            indPedRep = DBInventario.getIndPedRep();
            if (indPedRep.equals(FarmaConstants.INDICADOR_N)) {
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Se va a calcular el Pedido de Reposición. \nEsta operación tardará unos minutos. ¿Desea continuar?")) {
                    DBInventario.actualizaMaxMin();
                    cargaReposicion();
                    VariablesInventario.vFechaCalculoMaxMin = DBInventario.obtieneFechaCalculoMaxMin();
                    /**
           * Actualizando indicadores de stock
           * @author dubilluz
           * @since  22.10.2007
           */
                    actIndStock();
                } else
                    cerrarVentana(false);
            } else {
                VariablesInventario.vFechaCalculoMaxMin = DBInventario.obtieneFechaCalculoMaxMin();
                FarmaUtility.showMessage(this,
                                         "Sr QF, el ultimo calculo de Pedido de Reposición se realizo el dia " + VariablesInventario.vFechaCalculoMaxMin.substring(0,
                                                                                                                                                                   10) +
                                         "\n a las " + VariablesInventario.vFechaCalculoMaxMin.substring(11) +
                                         " horas del dia.", txtBuscar);
                cargaReposicion();
            }
            log.debug("mostrarMensaje");
            obtieneFechaCalculo();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al calcular el Pedido de Reposición : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void mostrarDetallesBlanco() {
        //
        lblLaboratorio.setText("");
        lblMinExhib.setText("");
        lblTransito.setText("");
        lblMinDias.setText("");
        lblMaxDias.setText("");
        lblNDias.setText("");
        lblRotProm.setText("");
        lblCantAnterior.setText("");
        //
        lbl30d.setText("");
        lbl60d.setText("");
        lbl90d.setText("");
        lbl120d.setText("");
    }

    private void cargaListaProductosStkCero() {
        try {
            tableModel.clearTable();
            DlgProcesaProdReposicion dlg = new DlgProcesaProdReposicion(myParentFrame, "", true);
            dlg.setVariables(tableModel, "%", "%");
            dlg.setIndStkCero(true);
            dlg.setVisible(true);
            FarmaUtility.ordenar(tblListaProductos, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
            if (tblListaProductos.getRowCount() > 0) {
                mostrarDetalles(0);
                UtilityInventario.muestraIndTipoProd(23, lblIndTipoProd, tblListaProductos);
            } else
                mostrarDetallesBlanco();
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private boolean validaVentaUltimosMeses(int row) {
        boolean retorno = false;
        double total = 0.0;
        try {
            //total = total + FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row,17).toString().trim());
            //total = total + FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row,18).toString().trim());
            //total = total + FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row,19).toString().trim());
            total = FarmaUtility.getDecimalNumber(tblListaProductos.getValueAt(row, 15).toString().trim());
            if (total > 0.0)
                retorno = true;
            else
                FarmaUtility.showMessage(this, "No se ha encontrado ventas para este producto.", txtBuscar);
        } catch (Exception e) {
            FarmaUtility.showMessage(this, "" + e, txtBuscar);
        }

        return retorno;
    }

    private void cargaReposicion() {
        initCabecera();
        initUltimoPedido();
        cargaListaProductos();
    }

    private void funcionF9() {
        int pos = tblListaProductos.getSelectedRow();
        if (pos > -1) {
            if (validaVentaUltimosMeses(pos)) {
                cargarCabecera();
                VariablesInventario.vCant_PedRep = VariablesInventario.vCantMax_PedRep;
                if (UtilityInventario.guardarCantidadIngresada(this, txtBuscar)) {
                    cargarCantidadIngresada();
                    FarmaVariables.vAceptar = false;
                    tblListaProductos.setRowSelectionInterval(pos, pos);
                }
            }
        }
    }

    private void funcionF10() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        /**Nuevo Cambio
     * @Autor: Luis Reque Orellana
     * @Fecha: 20/04/2007
     * */
        //String [] IND_DESCRIP_CAMPO = {"Código","Descripción","Unidad","Mín.","Máx.","Stock","Pedir","Calc","Solic."};
        //String [] IND_CAMPO = {"0","1","2","3","4","5","6","7","8"};

        String[] IND_DESCRIP_CAMPO = { "Código", "Tipo", "Descripción", "Unidad", "Stock", "Pedir", "Calc", "Solic." };
        //String [] IND_CAMPO = {"0","1","2","3","4","5","6"};
        String[] IND_CAMPO = { "0", "1", "2", "3", "4", "5", "6", "7" };

        VariablesInventario.vNombreInHashtable = ConstantsInventario.VNOMBREINHASHTABLEPEDIDOREP;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesInventario.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblListaProductos, tableModel, VariablesInventario.vCampo,
                                 VariablesInventario.vOrden);
            tblListaProductos.repaint();
        }
    }

    private void mostrarFaltaCero(boolean pFlag) {
        lblFCero.setVisible(pFlag);
    }

    /**NUEVO
     * @Autor: Luis Reque Orellana
     * @Fecha: 30-03-2007
     * */
    private void obtieneFechaCalculo() {
        try {
            VariablesInventario.vFechaCalculoMaxMin = DBInventario.obtieneFechaCalculoMaxMin();
            lblMensaje.setText("Fecha de último cálculo de Pedido de Reposición " +
                               VariablesInventario.vFechaCalculoMaxMin);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la fecha de cálculos.", txtBuscar);
        }
    }

    private void obtieneInfoProd(String pCodProd) {
        String cadena = "";
        String indLocales = "";
        String indAlmacen = "";
        try {
            cadena = DBInventario.obtieneIndicadorStock(pCodProd);
            indLocales = cadena.substring(0, cadena.indexOf("Ã")).trim();
            indAlmacen = cadena.substring(cadena.indexOf("Ã") + 1).trim();

            lblStockLocales.setText(indLocales);
            lblStockAlmacen.setText(indAlmacen);

            log.debug(cadena);
            log.debug(indLocales);
            log.debug(indAlmacen);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtBuscar);
            //lblStockLocales.setText("-");
            //lblStockAlmacen.setText("-");

        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "No se encontro coneccion con el local", txtBuscar);
        }
    }

    /**NUEVO
     * @Autor: Luis Reque Orellana
     * @Fecha: 20-04-2007
     * */
    private void funcionF11() {
        cargarCabecera();
        DlgIngresoCantidadAdicional dlgIngresoCantAdic = new DlgIngresoCantidadAdicional(myParentFrame, "", true);
        dlgIngresoCantAdic.setVisible(true);

        if (FarmaVariables.vAceptar) {
            tblListaProductos.setValueAt(VariablesInventario.vCantAdicional, tblListaProductos.getSelectedRow(), 7);
            tblListaProductos.repaint();
            FarmaVariables.vAceptar = false;
        }
    }

    private void actualizaIndStkPedidoReposicion() {
        try {
            DBInventario.actualizaIndStkPedidoReposicion();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al atualizar la informacion de los indicadores de stock", txtBuscar);
        }
    }

    private void actualizaIndStkPedidoReposicionNull() {
        try {
            DBInventario.actualizaIndStkPedidoReposicionNull();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al atualizar la informacion de los indicadores de stock a Null",
                                     txtBuscar);
        }
    }

    /**
     * Metodo que actualiza indicadores de  Stock
     * @author dubilluz
     * @since  22.10.2007
     */
    private void actIndStock() {
        log.debug("-----------------------------------");
        log.debug("Actualizando Ind Stock locales ");
        log.debug("-----------------------------------");
        log.debug("actualizando el indicador en linea .......");
        actualizaIndLinea();
        if (UtilityInventario.obtieneIndLinea(this)) {
            actualizaIndStkPedidoReposicion();
            log.debug("hay linea ");
        } else {
            log.debug("No hay linea ");
            actualizaIndStkPedidoReposicionNull(); //this.setTitle(this.getTitle() + " - Fecha Ultimo calculo de maximos y minimos " + VariablesInventario.vFechaCalculoMaxMin);
        }
    }

    /**
     * Actualiza el indicador en linea
     * @author dubilluz
     * @since  13.11.2007
     */
    public void actualizaIndLinea() {
        try {
            DBInventario.actualizaIndLinea();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al actualizar el estado en linea : \n" +
                    sql.getMessage(), null);
        }
    }

}
