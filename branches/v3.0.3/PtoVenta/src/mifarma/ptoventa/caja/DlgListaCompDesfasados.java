package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaCompDesfasados extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaCompDesfasados.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JScrollPane scrComprobantes = new JScrollPane();

    private JTable tblComprobantes = new JTable();

    private JLabelFunction lblCerrar = new JLabelFunction();

    private JLabelFunction lblAceptar = new JLabelFunction();
    private JLabelFunction lblAceptar1 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaCompDesfasados() {
        this(null, "", false);
    }

    public DlgListaCompDesfasados(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(491, 285));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relación de Comprobantes descontinuados");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        scrComprobantes.setBounds(new Rectangle(10, 10, 465, 205));
        tblComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblComprobantes_keyPressed(e);
            }
        });
        lblCerrar.setBounds(new Rectangle(365, 225, 105, 20));
        lblCerrar.setText("[Esc] Cerrar");
        lblAceptar.setBounds(new Rectangle(150, 225, 95, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblAceptar1.setBounds(new Rectangle(255, 225, 95, 20));
        lblAceptar1.setText("[F12] Imprimir");
        jContentPane.add(lblAceptar1, null);
        jContentPane.add(lblAceptar, null);
        jContentPane.add(lblCerrar, null);
        scrComprobantes.getViewport().add(tblComprobantes, null);
        jContentPane.add(scrComprobantes, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();

    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsCaja.columnsListaComprobantesDesfasados, ConstantsCaja.columnsListaComprobantesDesfasados,
                                    0);
        FarmaUtility.initSimpleList(tblComprobantes, tableModel, ConstantsCaja.columnsListaComprobantesDesfasados);
        cargarListaComprobantes();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblComprobantes);
    }

    private void tblComprobantes_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        }

        else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e) || e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tieneRegistroSeleccionado(tblComprobantes)) {
                VariablesCaja.vNumCompDesf =
                        tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 4).toString().trim();
                cerrarVentana(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            imprimir();
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargarListaComprobantes() {
        ArrayList myArray = new ArrayList();
        for (int i = 0; i < VariablesCaja.listaCompsDesfasados.size(); i++) {
            myArray = (ArrayList)VariablesCaja.listaCompsDesfasados.get(i);
            tableModel.insertRow(myArray);
        }

        if (tblComprobantes.getRowCount() > 0)
            FarmaUtility.ordenar(tblComprobantes, tableModel, 4, FarmaConstants.ORDEN_ASCENDENTE);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = new FarmaPrintService(36, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);

        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", tblComprobantes);
            return;
        }

        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(30) + " REPORTE DE COMPROBANTES DESFASADOS ", true);
            vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Local : " + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal, true);
            vPrint.printBold("Fecha Actual : " + fechaActual, true);
            vPrint.printBold("Fecha Inicio : " + VariablesCaja.vFecIniVerComp, true);
            vPrint.printBold("Fecha Fin : " + VariablesCaja.vFecFinVerComp, true);
            vPrint.printLine("===================================================", true);
            vPrint.printBold("Tipo Comp  NºComprobante  Correlativo  Estado", true);
            vPrint.printLine("===================================================", true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            if (tblComprobantes.getRowCount() > 0) {
                for (int i = 0; i < tblComprobantes.getRowCount(); i++) {
                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblComprobantes.getValueAt(i, 0),
                                                                           10) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblComprobantes.getValueAt(i, 1),
                                                                           14) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblComprobantes.getValueAt(i, 2),
                                                                           12) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblComprobantes.getValueAt(i, 3),
                                                                           10), true);
                }
            } else
                vPrint.printBold("No existen comprobantes desfasados a imprimir", true);
            vPrint.activateCondensed();
            vPrint.printLine("===================================================", true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblComprobantes.getRowCount(),
                                                                                      ",##0"), 10), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), null);
        }
    }
}
