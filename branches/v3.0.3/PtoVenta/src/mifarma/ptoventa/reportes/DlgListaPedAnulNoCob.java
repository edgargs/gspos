package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaPedAnulNoCob extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaPedAnulNoCob.class);

    private FarmaTableModel tableModelListaPed;
    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JScrollPane scrListaPed = new JScrollPane();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblRango_t = new JLabelWhite();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JLabelWhite lblRango = new JLabelWhite();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabelWhite lblCantRegistros = new JLabelWhite();
    private JTable tblListaPedidos = new JTable();
    private JLabelFunction lblFVerDetalle = new JLabelFunction();

    // **************************************************************************
    // Constructor
    // **************************************************************************

    public DlgListaPedAnulNoCob(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(556, 327));
        this.setTitle("Listado de Pedidos Anulados - No Cobrados");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Registro de Ventas");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader.setBounds(new Rectangle(10, 10, 530, 35));
        scrListaPed.setBounds(new Rectangle(10, 75, 530, 170));
        scrListaPed.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle1.setBounds(new Rectangle(10, 55, 530, 20));
        lblRango_t.setText("Rango de Fechas:");
        lblRango_t.setBounds(new Rectangle(70, 10, 100, 15));
        pnlTitle2.setBounds(new Rectangle(10, 245, 530, 20));
        lblRango.setBounds(new Rectangle(180, 10, 195, 15));
        lblFCerrar.setBounds(new Rectangle(445, 275, 95, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        btnListado.setText("Listado de Pedidos");
        btnListado.setBounds(new Rectangle(15, 0, 125, 20));
        btnListado.setMnemonic('l');
        lblCantRegistros.setText("0 Registros");
        lblCantRegistros.setHorizontalAlignment(JLabelWhite.RIGHT); //4
        lblCantRegistros.setBounds(new Rectangle(380, 0, 130, 20));
        lblFVerDetalle.setBounds(new Rectangle(10, 275, 130, 20));
        lblFVerDetalle.setText("[ F2 ] Ver Detalle");
        pnlBlanco.add(lblFVerDetalle, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlTitle2.add(lblCantRegistros, null);
        pnlBlanco.add(pnlTitle2, null);
        pnlTitle1.add(btnListado, null);
        pnlBlanco.add(pnlTitle1, null);
        scrListaPed.getViewport().add(tblListaPedidos, null);
        tblListaPedidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaPedidos_keyPressed(e);
            }
        });
        pnlBlanco.add(scrListaPed, null);
        pnlHeader.add(lblRango, null);
        pnlHeader.add(lblRango_t, null);
        pnlBlanco.add(pnlHeader, null);
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
        lblRango.setText(VariablesReporte.vFechaInicio + " - " + VariablesReporte.vFechaFin);
        cargaListaPedidos();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModelListaPed =
                new FarmaTableModel(ConstantsReporte.columnsListaPedAnuNoCob, ConstantsReporte.defaultValuesListaPedAnuNoCob,
                                    0);
        FarmaUtility.initSimpleList(tblListaPedidos, tableModelListaPed, ConstantsReporte.columnsListaPedAnuNoCob);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblListaPedidos);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblListaPedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            cargaVariables();
            DlgDetalleRegistroVentas dlgDetRegVentas = new DlgDetalleRegistroVentas(myParentFrame, "", true);
            dlgDetRegVentas.setVisible(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargaListaPedidos() {
        try {
            DBReportes.cargaListaPedidosAnulNoCob(tableModelListaPed, VariablesReporte.vFechaInicio,
                                                  VariablesReporte.vFechaFin);
            if (tblListaPedidos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaPedidos, tableModelListaPed, 0, FarmaConstants.ORDEN_DESCENDENTE);
            } else {
                FarmaUtility.showMessage(this,
                                         "No se encontraron pedidos anulados - no cobrados\nen el rango de fecha ingresado.",
                                         null);
                FarmaUtility.moveFocusJTable(tblListaPedidos);
            }
            lblCantRegistros.setText(tblListaPedidos.getRowCount() + " registros.");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar los pedidos: " + sql.getMessage(), null);
            FarmaUtility.moveFocusJTable(tblListaPedidos);
        }
    }

    private void cargaVariables() {
        int filaSelect = tblListaPedidos.getSelectedRow();

        VariablesReporte.vCliente = "" + tblListaPedidos.getValueAt(filaSelect, 2);
        VariablesReporte.vDireccion = "" + tblListaPedidos.getValueAt(filaSelect, 5);
        VariablesReporte.vEstado = "Anulado No Cobrado";
        VariablesReporte.vCorrelativo = "" + tblListaPedidos.getValueAt(filaSelect, 0);
        VariablesReporte.vFecha = "" + tblListaPedidos.getValueAt(filaSelect, 1);
        VariablesReporte.vUsuario = "" + tblListaPedidos.getValueAt(filaSelect, 6);
    }
}
