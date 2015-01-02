package mifarma.ptoventa.inventariociclico;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventariociclico.reference.ConstantsInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.DBInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.VariablesInvCiclico;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgLaboratoriosPorTomaCiclico extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgLaboratoriosPorTomaCiclico.class);

    FarmaTableModel tableModelLaboratorioTomaCiclico;

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel jContentPane = new JPanel();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();

    private JPanelTitle jPanelTitle1 = new JPanelTitle();

    private JButtonLabel btnLaboratorio = new JButtonLabel();

    private JTextFieldSanSerif txtLaboratorio = new JTextFieldSanSerif();

    private JScrollPane jScrollPane1 = new JScrollPane();

    private JTable tblRelacionLaboratoriosXToma = new JTable();

    private JButtonLabel btnRelacionLaboratoriosToma = new JButtonLabel();

    private JLabelFunction lblF1 = new JLabelFunction();

    private JLabelFunction lblF12 = new JLabelFunction();

    private JLabelFunction lblEscape = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgLaboratoriosPorTomaCiclico() {
        this(null, "", false);
    }

    public DlgLaboratoriosPorTomaCiclico(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(478, 376));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Laboratorios por Toma de Inventario");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jPanelHeader1.setBounds(new Rectangle(10, 10, 445, 30));
        jPanelTitle1.setBounds(new Rectangle(10, 45, 445, 20));
        btnLaboratorio.setText("Laboratorios :");
        btnLaboratorio.setBounds(new Rectangle(15, 5, 85, 20));
        btnLaboratorio.setMnemonic('l');
        btnLaboratorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLaboratorio_actionPerformed(e);
            }
        });
        txtLaboratorio.setBounds(new Rectangle(105, 5, 320, 20));
        txtLaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLaboratorio_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtLaboratorio_keyReleased(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 65, 445, 245));
        btnRelacionLaboratoriosToma.setText("Relacion de Laboratorios por Toma");
        btnRelacionLaboratoriosToma.setBounds(new Rectangle(10, 0, 215, 20));
        btnRelacionLaboratoriosToma.setMnemonic('r');
        btnRelacionLaboratoriosToma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionLaboratoriosToma_actionPerformed(e);
            }
        });
        lblF1.setBounds(new Rectangle(10, 320, 115, 20));
        lblF1.setText("[ F1 ] Ver Detalle");
        lblF12.setBounds(new Rectangle(215, 320, 110, 20));
        lblF12.setText("[ F12 ] Imprimir");
        lblEscape.setBounds(new Rectangle(335, 320, 117, 19));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        jPanelHeader1.add(txtLaboratorio, null);
        jPanelHeader1.add(btnLaboratorio, null);
        jScrollPane1.getViewport().add(tblRelacionLaboratoriosXToma, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(btnRelacionLaboratoriosToma, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(jPanelHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        initTableLaboratoriosToma();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableLaboratoriosToma() {
        tableModelLaboratorioTomaCiclico =
                new FarmaTableModel(ConstantsInvCiclico.columnsLaboratoriosToma, ConstantsInvCiclico.defaultValuesLaboratoriosToma,
                                    0);
        FarmaUtility.initSimpleList(tblRelacionLaboratoriosXToma, tableModelLaboratorioTomaCiclico,
                                    ConstantsInvCiclico.columnsLaboratoriosToma);
        cargaLaboratoriosToma();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtLaboratorio);
    }

    private void txtLaboratorio_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionLaboratoriosXToma, txtLaboratorio, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblRelacionLaboratoriosXToma.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblRelacionLaboratoriosXToma, txtLaboratorio.getText().trim(), 0,
                                                    1))) {
                    FarmaUtility.showMessage(this, "Laboratorio No Encontrado según Criterio de Búsqueda !!!",
                                             txtLaboratorio);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void btnRelacionLaboratoriosToma_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionLaboratoriosXToma);
    }

    private void btnLaboratorio_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtLaboratorio);
    }

    private void txtLaboratorio_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (tieneRegistroSeleccionado(tblRelacionLaboratoriosXToma)) {
                cargarRegistroSeleccionado();
                DlgProductosPorTomaCiclico dlgProductosPorTomaCiclico =
                    new DlgProductosPorTomaCiclico(myParentFrame, "", true);
                dlgProductosPorTomaCiclico.setVisible(true);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtLaboratorio);
            else
                imprimir();
        }
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblRelacionLaboratoriosXToma, txtLaboratorio, 1);
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaLaboratoriosToma() {
        try {
            DBInvCiclico.getListaLabsXToma(tableModelLaboratorioTomaCiclico);
            if (tblRelacionLaboratoriosXToma.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionLaboratoriosXToma, tableModelLaboratorioTomaCiclico, 1,
                                     FarmaConstants.ORDEN_ASCENDENTE);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener los laboratorios de la toma :\n" +
                    sql.getMessage(), txtLaboratorio);
        }
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegistroSeleccionado() {
        VariablesInvCiclico.vCodLab =
                tblRelacionLaboratoriosXToma.getValueAt(tblRelacionLaboratoriosXToma.getSelectedRow(),
                                                        0).toString().trim();
        VariablesInvCiclico.vNomLab =
                tblRelacionLaboratoriosXToma.getValueAt(tblRelacionLaboratoriosXToma.getSelectedRow(),
                                                        1).toString().trim();
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        log.debug(FarmaVariables.vImprReporte);
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtLaboratorio);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE LABORATORIOS POR TOMA DE INVENTARIO ",
                             true);
            vPrint.printBold(" ", true);
            vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);
            vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);
            vPrint.printBold("Toma #: " + VariablesInvCiclico.vSecToma, true);
            vPrint.printBold("Tipo         : " + VariablesInvCiclico.vDescTipoToma, true);
            vPrint.printBold("Fecha Inicio : " + VariablesInvCiclico.vFecIniToma, true);
            vPrint.printBold("Fecha Fin    : " + VariablesInvCiclico.vFecFinToma, true);
            vPrint.printBold("Estado       : " + VariablesInvCiclico.vDescEstadoToma, true);
            vPrint.printBold("Fecha Actual : " + fechaActual, true);
            vPrint.printLine("==============================================================================================",
                             true);
            vPrint.printBold("   Código        Laboratorio                                                           Estado        ",
                             true);
            vPrint.printLine("==============================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblRelacionLaboratoriosXToma.getRowCount(); i++) {

                vPrint.printCondensed("   " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionLaboratoriosXToma.getValueAt(i,
                                                                                                                       0),
                                                                       14) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionLaboratoriosXToma.getValueAt(i,
                                                                                                                       1),
                                                                       70) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionLaboratoriosXToma.getValueAt(i,
                                                                                                                       2),
                                                                       22), true);
            }
            vPrint.activateCondensed();
            vPrint.printLine("==============================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaUtility.formatNumber(tblRelacionLaboratoriosXToma.getRowCount(), ",##0") +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (Exception sqlerr) {
            log.error("", sqlerr);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir :\n" +
                    sqlerr.getMessage(), txtLaboratorio);
        }
    }
}
