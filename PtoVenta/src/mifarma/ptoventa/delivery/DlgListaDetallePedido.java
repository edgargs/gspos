package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.ConstantsDelivery;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaDetallePedido extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaDetallePedido.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelDetallePedido;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle pnlDatosCliente = new JPanelTitle();
    private JLabelWhite lblNPedido = new JLabelWhite();
    private JLabelWhite lblNombreClienteT = new JLabelWhite();
    private JLabelWhite lblNumeroPeditoT = new JLabelWhite();
    private JLabelWhite lblNombreCliente = new JLabelWhite();
    JPanel pnlRelacion = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    private JButtonLabel btnDetallePedido = new JButtonLabel();
    private JScrollPane srcListaDetallePedido = new JScrollPane();
    private JTable tblListaDetallePedido = new JTable();
    JLabelFunction lblEnter = new JLabelFunction();
    JLabelFunction lblFiltro = new JLabelFunction();
    JLabelFunction lblF11 = new JLabelFunction();
    JLabelFunction lblESC = new JLabelFunction();
    private JLabelWhite lblCantItems = new JLabelWhite();
    private JLabelWhite lblCantItems_T = new JLabelWhite();
    // kmoncada 22.08.2014 filtro de productos pend de lotes
    private boolean filtrar = false;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaDetallePedido() {
        this(null, "", false);
    }

    public DlgListaDetallePedido(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(714, 404));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Lista de Detalle del Pedido");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlDatosCliente.setBounds(new Rectangle(10, 10, 680, 70));
        pnlDatosCliente.setBackground(Color.white);
        pnlDatosCliente.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblNPedido.setBounds(new Rectangle(130, 10, 410, 15));
        lblNPedido.setForeground(new Color(255, 130, 14));
        lblNombreClienteT.setText("Nombre del Cliente :");
        lblNombreClienteT.setBounds(new Rectangle(5, 40, 120, 15));
        lblNombreClienteT.setForeground(new Color(255, 130, 14));
        lblNumeroPeditoT.setText("Nº Pedido :");
        lblNumeroPeditoT.setBounds(new Rectangle(5, 10, 90, 15));
        lblNumeroPeditoT.setForeground(new Color(255, 130, 14));
        lblNombreCliente.setBounds(new Rectangle(130, 40, 520, 15));
        lblNombreCliente.setForeground(new Color(255, 130, 14));
        pnlRelacion.setBackground(new Color(255, 130, 14));
        pnlRelacion.setLayout(xYLayout2);
        pnlRelacion.setFont(new Font("SansSerif", 0, 11));
        pnlRelacion.setBounds(new Rectangle(10, 100, 680, 25));
        btnDetallePedido.setText("Detalle del Pedido :");
        btnDetallePedido.setMnemonic('D');
        btnDetallePedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetallePedido_actionPerformed(e);
            }
        });
        srcListaDetallePedido.setBounds(new Rectangle(10, 125, 680, 210));
        tblListaDetallePedido.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaDetallePedido_keyPressed(e);
            }
        });
        lblEnter.setText("[ ENTER ]  Seleccionar");
        lblEnter.setBounds(new Rectangle(10, 345, 140, 20));
        lblFiltro.setText("[ F2 ]  Lotes Pendientes");
        lblFiltro.setBounds(new Rectangle(215, 345, 140, 20));
        lblF11.setText("[ F11 ]  Aceptar");
        lblF11.setBounds(new Rectangle(435, 345, 115, 20));
        lblESC.setText("[ ESC ]  Cerrar");
        lblESC.setBounds(new Rectangle(570, 345, 115, 20));
        lblCantItems.setText("0");
        lblCantItems_T.setText("Cantidad Items :");
        pnlRelacion.add(lblCantItems_T, new XYConstraints(390, 5, 105, 15));
        pnlRelacion.add(lblCantItems, new XYConstraints(505, 5, 40, 15));
        pnlRelacion.add(btnDetallePedido, new XYConstraints(10, 5, 165, 15));
        pnlDatosCliente.add(lblNPedido, null);
        pnlDatosCliente.add(lblNombreClienteT, null);
        pnlDatosCliente.add(lblNumeroPeditoT, null);
        pnlDatosCliente.add(lblNombreCliente, null);
        srcListaDetallePedido.getViewport().add(tblListaDetallePedido, null);
        jPanelWhite1.add(lblESC, null);
        jPanelWhite1.add(lblF11, null);
        jPanelWhite1.add(lblEnter, null);
        jPanelWhite1.add(lblFiltro, null);
        jPanelWhite1.add(srcListaDetallePedido, null);
        jPanelWhite1.add(pnlRelacion, null);
        jPanelWhite1.add(pnlDatosCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableDetallePedido();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableDetallePedido() {
        tableModelDetallePedido =
                new FarmaTableModel(ConstantsDelivery.columnsListaDetPedidos, ConstantsDelivery.defaultListaDetPedidos,
                                    0);
        FarmaUtility.initSelectList(tblListaDetallePedido, tableModelDetallePedido,
                                    ConstantsDelivery.columnsListaDetPedidos);
    }

    private void cargaListaDetallePedido() {
        try {
            DBDelivery.cargaListaDetPedidos(tableModelDetallePedido, VariablesDelivery.vNumeroPedido,
                                            VariablesDelivery.vCodLocalOrigen, filtrar);
            lblCantItems.setText("" + tblListaDetallePedido.getRowCount());
            if (tblListaDetallePedido.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaDetallePedido, tableModelDetallePedido, 3, "asc");
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al cargar detalle de pedidos - \n" +
                    e, tblListaDetallePedido);
            log.error("", e);
        }
    }

    private void muestraInformacion() {
        lblNPedido.setText(VariablesDelivery.vNumeroPedido);
        lblNombreCliente.setText(VariablesDelivery.vNombre);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        muestraInformacion();
        cargaListaDetallePedido();
        //ERIOS 2.4.5 Genera lotes por NumeroEntrega
        cargaLotesNumeroEntrega();
        colocaCheckProdInicio();
        FarmaUtility.moveFocus(tblListaDetallePedido);

        //DUBILLUZ - 16.12.2009
        //Validara si existe ya los lotes ingresados.
        if (isContieneLotesDetallePedidos()) {
            cerrarVentana(true);
        }

    }

    private void tblListaDetallePedido_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaDetallePedido.getRowCount() <= 0)
                return;
            e.consume();
            //verificaCheckJTable();
            //obtieneValores();
            verificaCheckJTable();
            ;
        } else
            chkKeyPressed(e);
    }

    private void btnDetallePedido_actionPerformed(ActionEvent e) {
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (tblListaDetallePedido.getRowCount() > 0) {
                if (!validaChecksTable()) {
                    FarmaUtility.showMessage(this,
                                             "No es posible realizar la operación. Existen productos sin selección de lotes.",
                                             tblListaDetallePedido);
                    return;
                }
                if (JConfirmDialog.rptaConfirmDialog(this, "Está seguro de generar el pedido institucional?"))
                    cerrarVentana(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            // funcionalidad para el filtro de prod pend de lotes.
            filtrar = !filtrar;
            cargaListaDetallePedido();
            if (!filtrar) {
                colocaCheckProdInicio();
                lblFiltro.setText("[ F2 ]  Lotes Pendientes");
            } else {
                lblFiltro.setText("[ F2 ]  Mostrar Todos");
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
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

    private void muestraIngresoCantLote() {
        DlgIngresoCantLote dlgIngresoCantLote = new DlgIngresoCantLote(myParentFrame, "", true);
        dlgIngresoCantLote.setVisible(true);
    }

    private void obtieneValores() {
        VariablesDelivery.vCodProducto =
                FarmaUtility.getValueFieldJTable(tblListaDetallePedido, tblListaDetallePedido.getSelectedRow(), 1);
        VariablesDelivery.vDescProducto =
                FarmaUtility.getValueFieldJTable(tblListaDetallePedido, tblListaDetallePedido.getSelectedRow(), 2);
        VariablesDelivery.vCantSolicitada =
                FarmaUtility.getValueFieldJTable(tblListaDetallePedido, tblListaDetallePedido.getSelectedRow(), 5);
        VariablesDelivery.vUnidVta =
                FarmaUtility.getValueFieldJTable(tblListaDetallePedido, tblListaDetallePedido.getSelectedRow(),
                                                 3); //04.08.2014
    }

    private void verificaCheckJTable() {
        int row = tblListaDetallePedido.getSelectedRow();
        Boolean valor = (Boolean)(tblListaDetallePedido.getValueAt(tblListaDetallePedido.getSelectedRow(), 0));
        if (valor.booleanValue()) {
            if (JConfirmDialog.rptaConfirmDialog(this,
                                                 "Esta acción eliminará todas las cantidades seleccionadas.\nEstá seguro de eliminar?"))
                deseleccionaProducto();
        } else {
            muestraIngresoCantidad();
        }
    }

    private void deseleccionaProducto() {
        String cantidad = "";
        VariablesDelivery.vCodProducto =
                ((String)(tblListaDetallePedido.getValueAt(tblListaDetallePedido.getSelectedRow(), 1))).trim();
        if (!eliminaVtaInstiProd()) {
            return;
        }
        FarmaUtility.setCheckValue(tblListaDetallePedido, false);
    }

    private void muestraIngresoCantidad() {
        if (tblListaDetallePedido.getRowCount() == 0)
            return;
        obtieneValores();
        DlgIngresoCantLote dlgIngresoCantLote = new DlgIngresoCantLote(myParentFrame, "", true);
        dlgIngresoCantLote.setVisible(true);
        if (VariablesDelivery.vCantProdLoteCompleta) {
            seleccionaProducto();
        }
    }

    private void seleccionaProducto() {
        FarmaUtility.setCheckValue(tblListaDetallePedido, false);
    }

    private boolean eliminaVtaInstiProd() {
        try {
            obtieneValores();
            DBDelivery.eliminaDetalleProducto(VariablesDelivery.vNumeroPedido, VariablesDelivery.vCodProducto);
            FarmaUtility.aceptarTransaccion();
            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al eliminar detalle Producto - \n" +
                    sql, tblListaDetallePedido);
            return false;
        }
    }

    private boolean validaChecksTable() {
        boolean valorCheckRow = true;
        Boolean valor;
        for (int i = 0; i < tblListaDetallePedido.getRowCount(); i++) {
            valor = (Boolean)(tblListaDetallePedido.getValueAt(i, 0));
            if (valor.booleanValue())
                continue;
            else
                valorCheckRow = false;
        }
        return valorCheckRow;
    }

    private ArrayList obtieneProductosCantidadTotalSel() {
        ArrayList myArray = new ArrayList();
        try {
            DBDelivery.obtieneProductosSeleccionTotalLote(myArray);
        } catch (SQLException sql) {
            myArray = new ArrayList();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener productos con cantidades totales seleccionadas - \n" +
                    sql, tblListaDetallePedido);
        }
        return myArray;
    }

    private void colocaCheckProdInicio() {
        ArrayList myArray = new ArrayList();
        myArray = obtieneProductosCantidadTotalSel();
        String prodTable = "";
        String prodArray = "";
        if (myArray.size() > 0) {
            for (int i = 0; i < tblListaDetallePedido.getRowCount(); i++) {
                prodTable = FarmaUtility.getValueFieldJTable(tblListaDetallePedido, i, 1);
                for (int j = 0; j < myArray.size(); j++) {
                    prodArray = FarmaUtility.getValueFieldArrayList(myArray, j, 0);
                    if (prodTable.equalsIgnoreCase(prodArray)) {
                        tblListaDetallePedido.setValueAt(new Boolean(true), i, 0);
                        break;
                    }
                }
            }
        }
        tblListaDetallePedido.repaint();
    }

    /**
     * Algoritmo para consultar si tiene todos los productos con LOTE
     * @author dubilluz
     * @since  16.12.2009
     */
    private boolean isContieneLotesDetallePedidos() {
        String pResultado = "N";
        try {
            log.info("VariablesDelivery.vNumeroPedido_bk:" + VariablesDelivery.vNumeroPedido_bk);
            log.info("VariablesDelivery.vCodLocal_bk:" + VariablesDelivery.vCodLocal_bk);
            pResultado =
                    DBDelivery.isContienLotesProductos(VariablesDelivery.vCodLocal_bk, VariablesDelivery.vNumeroPedido_bk);

            log.info("isContieneLotesDetallePedidos():" + pResultado);
        } catch (Exception e) {
            pResultado = "N";
            log.info("Error al obtener ind Productos x Lote del Pedido:" + e.getMessage());
        }

        if (pResultado.trim().equalsIgnoreCase("N")) {
            return false;
        } else {
            FarmaUtility.showMessage(this, "El pedido ya tiene los Lotes Ingresados\n" +
                    "Se procederá a generar el pedido.\n" +
                    "Gracias.", tblListaDetallePedido);

            return true;
        }
    }

    /**
     * Carga los lotes de las entregas
     * @author ERIOS
     * @since 2.4.5
     */
    private void cargaLotesNumeroEntrega() {
        try {
            DBDelivery.cargaLotesNumeroEntrega();
        } catch (SQLException e) {
            log.error("", e);
        }
    }
}
