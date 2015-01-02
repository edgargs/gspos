package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleResumenVentas extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleResumenVentas.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelResumenProductos;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblResumenProductos = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();
    private JButtonLabel lbllocal = new JButtonLabel();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JLabel lblRegistros = new JLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblTotalVenta = new JLabel();
    private JLabel lblRegsitros_T1 = new JLabel();

    public DlgDetalleResumenVentas() {
        this(null, "", false);
    }

    public DlgDetalleResumenVentas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(667, 396));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Resumen Detalle Ventas");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(10, 10, 640, 20));
        btnListado.setText("Resumen de Productos Vendidos");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 35, 640, 270));
        tblResumenProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblResumenProductos_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblResumenProductos_keyPressed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(10, 340, 117, 19));
        jLabelFunction1.setText("[ F5] Detallado");
        jLabelFunction2.setBounds(new Rectangle(135, 340, 117, 19));
        jLabelFunction2.setText("[ F8 ] Ordenar");
        jLabelFunction3.setBounds(new Rectangle(530, 340, 117, 19));
        jLabelFunction3.setText("[ ESC ] Escape");
        lbllocal.setText("xxx");
        lbllocal.setBounds(new Rectangle(510, 0, 130, 20));
        lbllocal.setMnemonic('l');
        lbllocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        pnlResultados.setBounds(new Rectangle(5, 305, 645, 20));
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(90, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(15, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblTotalVenta.setText("0");
        lblTotalVenta.setBounds(new Rectangle(530, 0, 80, 20));
        lblTotalVenta.setFont(new Font("SansSerif", 1, 11));
        lblTotalVenta.setForeground(Color.white);
        lblTotalVenta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegsitros_T1.setText("Total Venta");
        lblRegsitros_T1.setBounds(new Rectangle(435, 0, 65, 20));
        lblRegsitros_T1.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T1.setForeground(Color.white);
        pnlResultados.add(lblRegsitros_T1, null);
        pnlResultados.add(lblTotalVenta, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlTitulo.add(lbllocal, null);
        pnlTitulo.add(btnListado, null);
        jScrollPane1.getViewport().add(tblResumenProductos, null);
        jPanelWhite1.add(pnlResultados, null);
        jPanelWhite1.add(jLabelFunction3, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlTitulo, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
    }

    private void initialize() {
        initTableListaResumenProductosVendidos();
    };

    private void initTableListaResumenProductosVendidos() {
        tableModelResumenProductos =
                new FarmaTableModel(ConstantsReporte.columnsListaResumenProductosVendidos, ConstantsReporte.defaultValuesListaResumenProductosVendidos,
                                    0);
        FarmaUtility.initSimpleList(tblResumenProductos, tableModelResumenProductos,
                                    ConstantsReporte.columnsListaResumenProductosVendidos);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblResumenProductos);
        cargaResumenProductosVendidos();
        lbllocal.setText(FarmaVariables.vDescLocal);
        sumaTotal();
    }

    private void cargaResumenProductosVendidos() {
        try {
            DBReportes.cargaListaResumenProductosVendidos(tableModelResumenProductos, VariablesReporte.vFechaInicio,
                                                          VariablesReporte.vFechaFin);
            FarmaUtility.ordenar(tblResumenProductos, tableModelResumenProductos, 1, FarmaConstants.ORDEN_ASCENDENTE);
            lblRegistros.setText("" + tblResumenProductos.getRowCount());
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el resumen de productos vendidos : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblResumenProductos_keyReleased(KeyEvent e) {

    }

    void muestraVentaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO = { "Codigo", "Descripcion", "Unidad", "Cantidad", "Venta" };
        String[] IND_CAMPO = { "0", "1", "2", "3", "4" };
        log.debug("Campo " + IND_DESCRIP_CAMPO[1]);
        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLERESUMENVENTAS;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblResumenProductos, tableModelResumenProductos, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblResumenProductos.repaint();
        }
    }

    private void sumaTotal() {
        if (tblResumenProductos.getRowCount() > 0) {
            double totalVentas = 0;
            for (int i = 0; i < tblResumenProductos.getRowCount(); i++) {
                totalVentas =
                        totalVentas + FarmaUtility.getDecimalNumber(tblResumenProductos.getValueAt(i, 4).toString().trim());
            }
            lblTotalVenta.setText(FarmaUtility.formatNumber(totalVentas));
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cargaRegistroSeleccionado() {
        if (tblResumenProductos.getRowCount() <= 0) {
            FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", null);
            FarmaUtility.moveFocus(btnListado);
        } else
            VariablesReporte.vCodProd =
                    ((String)tblResumenProductos.getValueAt(tblResumenProductos.getSelectedRow(), 0)).trim();
        VariablesReporte.vDesProd =
                ((String)tblResumenProductos.getValueAt(tblResumenProductos.getSelectedRow(), 1)).trim();
        VariablesReporte.vUnidadPresentacion =
                ((String)tblResumenProductos.getValueAt(tblResumenProductos.getSelectedRow(), 2)).trim();
        VariablesReporte.vCantidad =
                ((String)tblResumenProductos.getValueAt(tblResumenProductos.getSelectedRow(), 3)).trim();
        VariablesReporte.vVentas =
                ((String)tblResumenProductos.getValueAt(tblResumenProductos.getSelectedRow(), 4)).trim();
    }

    private void listaReumenDetalleVentasDetallado() {
        DlgResumenDetalleVentasDetallado dlgResumenDetalleVentasDetallado =
            new DlgResumenDetalleVentasDetallado(myParentFrame, "", true);
        dlgResumenDetalleVentasDetallado.setVisible(true);
    }

    private void tblResumenProductos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (tblResumenProductos.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", tblResumenProductos);
            else
                muestraVentaOrdenar();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            cargaRegistroSeleccionado();
            listaReumenDetalleVentasDetallado();

        }
    }
}
