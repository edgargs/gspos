package mifarma.ptoventa.inventariodiario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
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

import mifarma.ptoventa.inventariodiario.reference.ConstantsInvDiario;
import mifarma.ptoventa.inventariodiario.reference.DBInvDiario;
import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaDiferenciasProdDiario extends JDialog {
    FarmaTableModel tableModelDiferenciasProductos;
    private static final Logger log = LoggerFactory.getLogger(DlgListaDiferenciasProdDiario.class);

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();

    private JPanelTitle jPanelTitle1 = new JPanelTitle();

    private JScrollPane jScrollPane1 = new JScrollPane();

    private JTable tblRelacionDiferenciasProductos = new JTable();

    private JButtonLabel btnRelacionProductos = new JButtonLabel();

    private JButtonLabel btnProductos = new JButtonLabel();

    private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();

    private JLabelFunction lblF11 = new JLabelFunction();

    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaDiferenciasProdDiario() {
        this(null, "", false);
    }

    public DlgListaDiferenciasProdDiario(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(653, 393));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Diferencias de Productos");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(15, 10, 620, 30));
        jPanelHeader1.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(15, 45, 620, 25));
        jPanelTitle1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(15, 70, 620, 260));
        btnRelacionProductos.setText("Relacion de Diferencias de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 215, 25));
        btnRelacionProductos.setMnemonic('r');
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        btnProductos.setText("Productos :");
        btnProductos.setMnemonic('p');
        btnProductos.setBounds(new Rectangle(30, 5, 65, 20));
        btnProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProductos_actionPerformed(e);
            }
        });
        txtProductos.setBounds(new Rectangle(115, 5, 305, 20));
        txtProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProductos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProductos_keyReleased(e);
            }
        });
        lblF11.setBounds(new Rectangle(400, 340, 120, 20));
        lblF11.setText("[ F12 ] Imprimir");
        lblEscape.setBounds(new Rectangle(525, 340, 110, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        lblF12.setBounds(new Rectangle(160, 340, 225, 20));
        lblF12.setText("[ F11 ] Imprimir Diferencias en Blanco");
        jScrollPane1.getViewport().add(tblRelacionDiferenciasProductos, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(btnRelacionProductos, null);
        jContentPane.add(jPanelTitle1, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEscape, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        initTableListaDiferenciasProductos();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaDiferenciasProductos() {
        tableModelDiferenciasProductos =
                new FarmaTableModel(ConstantsInvDiario.columnsListaDiferenciasProductos, ConstantsInvDiario.defaultValuesListaDiferenciasProductos,
                                    0);
        FarmaUtility.initSimpleList(tblRelacionDiferenciasProductos, tableModelDiferenciasProductos,
                                    ConstantsInvDiario.columnsListaDiferenciasProductos);
        cargaListaDiferenciasProductos();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionDiferenciasProductos, txtProductos, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblRelacionDiferenciasProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblRelacionDiferenciasProductos, txtProductos.getText().trim(), 0,
                                                    1))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtProductos);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtProductos);
            else
                imprimir();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtProductos);
            else
                imprimirDiferencias();
        }
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblRelacionDiferenciasProductos, txtProductos, 1);
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaDiferenciasProductos() {
        try {
            DBInvDiario.getListaProdsDiferencias(tableModelDiferenciasProductos);
            if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, tableModelDiferenciasProductos, 1,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de de tomas dif");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la lista de diferencias :\n" +
                    sql.getMessage(), txtProductos);
        }
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,FarmaVariables.vImprReporte, true);
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtProductos);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE DE DIFERENCIAS DE PRODUCTOS", true);
            vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);
            vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);
            vPrint.printBold("Toma #          : " + VariablesInvDiario.vSecToma.trim(), true);
            vPrint.printBold("Tipo            : " + VariablesInvDiario.vDescTipoToma.trim(), true);
            vPrint.printBold("Estado          : " + VariablesInvDiario.vDescEstadoToma.trim(), true);
            vPrint.printBold("Laboratorio     : " + VariablesInvDiario.vCodLab + " - " +
                             VariablesInvDiario.vNomLab.trim(), true);
            vPrint.printBold("Fecha Actual    : " + fechaActual, true);


            vPrint.printLine("=================================================================================================",
                             true);
            vPrint.printBold("Código  Descripcion                                 Unidad     Stk Actual   Diferencia    Precio     ",
                             true);
            vPrint.printLine("=================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblRelacionDiferenciasProductos.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                          0),
                                                                       8) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                          1),
                                                                       44) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                          2),
                                                                       10) + " " +
                                      FarmaPRNUtility.alinearDerecha((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                        3),
                                                                     10) + "   " +
                                      FarmaPRNUtility.alinearDerecha((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                        4),
                                                                     10) +
                                      FarmaPRNUtility.alinearDerecha((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                        5),
                                                                     10), true);
            }
            vPrint.activateCondensed();
            vPrint.printLine("=================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaUtility.formatNumber(tblRelacionDiferenciasProductos.getRowCount(), ",##0") +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (Exception sqlerr) {
            log.error("", sqlerr);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sqlerr.getMessage(), txtProductos);
        }
    }

    private void imprimirDiferencias() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtProductos);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE DE DIFERENCIAS DE PRODUCTOS", true);
            vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);
            vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);
            vPrint.printBold("Toma #          : " + VariablesInvDiario.vSecToma.trim(), true);
            vPrint.printBold("Tipo            : " + VariablesInvDiario.vDescTipoToma.trim(), true);
            vPrint.printBold("Estado          : " + VariablesInvDiario.vDescEstadoToma.trim(), true);
            vPrint.printBold("Laboratorio     : " + VariablesInvDiario.vCodLab + " - " +
                             VariablesInvDiario.vNomLab.trim(), true);
            vPrint.printBold("Fecha Actual    : " + fechaActual, true);


            vPrint.printLine("=================================================================================================",
                             true);
            vPrint.printBold("Código  Descripcion                                 Unidad       C.Entera/C.Fraccion     ",
                             true);
            vPrint.printLine("=================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblRelacionDiferenciasProductos.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                          0),
                                                                       8) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                          1),
                                                                       44) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionDiferenciasProductos.getValueAt(i,
                                                                                                                          2),
                                                                       10) + "   " +
                                      FarmaPRNUtility.alinearDerecha("_________/________", 19), true);
            }
            vPrint.activateCondensed();
            vPrint.printLine("=================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaUtility.formatNumber(tblRelacionDiferenciasProductos.getRowCount(), ",##0") +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (Exception sqlerr) {
            log.error("", sqlerr);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sqlerr.getMessage(), txtProductos);
        }
    }


    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionDiferenciasProductos);
    }

}
