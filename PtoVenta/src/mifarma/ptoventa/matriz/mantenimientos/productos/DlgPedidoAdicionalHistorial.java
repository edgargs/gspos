package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgPedidoAdicionalHistorial extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgPedidoAdicionalHistorial.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaPedidos = new JScrollPane();
    private JTable tblHistorial = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblCodProd = new JLabelWhite();
    private JLabelWhite lblDescProd = new JLabelWhite();
    private JLabel lblCodigo = new JLabel();
    private JLabel lblDescripcion = new JLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgPedidoAdicionalHistorial() {
        this(null, "", false);
    }

    public DlgPedidoAdicionalHistorial(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ********************************************************************** */
    /*                                  METODO jbInit                         */
    /* ********************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(795, 356));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Historial de Promedio de Venta Mensual");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 770, 75));
        lblCodigo.setText("Codigo");
        lblCodigo.setBounds(new Rectangle(35, 15, 55, 20));
        lblCodigo.setFont(new Font("SansSerif", 1, 12));

        lblDescripcion.setText("Nombre");
        lblDescripcion.setBounds(new Rectangle(35, 35, 60, 20));
        lblDescripcion.setFont(new Font("SansSerif", 1, 12));

        lblCodProd.setText("Codigo");
        lblCodProd.setBounds(new Rectangle(100, 15, 70, 20));
        lblCodProd.setFont(new Font("SansSerif", 1, 12));

        lblDescProd.setText("Nombre");
        lblDescProd.setBounds(new Rectangle(100, 35, 410, 20));
        lblDescProd.setFont(new Font("SansSerif", 1, 12));

        tblHistorial.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                tblHistorial_keyPressed(e);
            }
        });

        pnlTitle1.add(lblCodigo);
        pnlTitle1.add(lblDescripcion);
        pnlTitle1.add(lblCodProd);
        pnlTitle1.add(lblDescProd);
        scrListaPedidos.setBounds(new Rectangle(10, 85, 770, 210));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(355, 300, 85, 20));

        scrListaPedidos.getViewport().add(tblHistorial, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaPedidos, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                                  METODO initialize                     */
    /* ********************************************************************** */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
        FarmaUtility.moveFocus(tblHistorial);
    }

    /* ********************************************************************** */
    /*                            METODOS INICIALIZACION                      */
    /* ********************************************************************** */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsHistorialPedidoAdicional, ConstantsInventario.defaultHistorialPedidoAdicional,
                                    0);
        FarmaUtility.initSimpleList(tblHistorial, tableModel, ConstantsInventario.columnsHistorialPedidoAdicional);
        lblCodProd.setText(VariablesProducto.vCodProd_PedAdic);
        lblDescProd.setText(VariablesProducto.vNomProd_PedAdic);
        cargaListaPedidos();
    }

    private void tblHistorial_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cargaListaPedidos() {
        try {
            DBInventario.cargaHistorialPedidoAdicionalMatriz(tableModel, VariablesProducto.vCodProd_PedAdic);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de pedidos : \n " + sql.getMessage(),
                                     null);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblHistorial, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
}
