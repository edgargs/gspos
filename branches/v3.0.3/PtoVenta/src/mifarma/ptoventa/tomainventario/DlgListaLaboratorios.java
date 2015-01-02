package mifarma.ptoventa.tomainventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import java.util.ArrayList;

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

import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaLaboratorios extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaLaboratorios.class);

    FarmaTableModel tableModel;

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel jContentPane = new JPanel();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();

    private JPanelTitle jPanelTitle1 = new JPanelTitle();

    private JButtonLabel btnLaboratorio = new JButtonLabel();

    private JTextFieldSanSerif txtLaboratorio = new JTextFieldSanSerif();

    private JScrollPane scrListaLabs = new JScrollPane();

    private JTable tblRelacionLaboratorios = new JTable();

    private JLabelFunction lblF1 = new JLabelFunction();


    private JLabelFunction lblEscape = new JLabelFunction();

    private JLabelWhite lblIngCrit = new JLabelWhite();

    private JLabelWhite jLabelWhite1 = new JLabelWhite();

    private JPanelTitle jPanelTitle2 = new JPanelTitle();

    private JButtonLabel btnRelacionLaboratoriosToma1 = new JButtonLabel();

    private JButtonLabel btnRelacionLaboratoriosToma2 = new JButtonLabel();

    private JLabelWhite lblCantReg_T = new JLabelWhite();

    private JLabelWhite lblCantReg = new JLabelWhite();

    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaLaboratorios() {
        this(null, "", false);
    }

    public DlgListaLaboratorios(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(760, 398));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Items por Laboratorio");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jPanelHeader1.setBounds(new Rectangle(10, 10, 735, 40));
        jPanelTitle1.setBounds(new Rectangle(10, 55, 735, 25));
        btnLaboratorio.setText("Laboratorio :");
        btnLaboratorio.setBounds(new Rectangle(110, 15, 80, 20));
        btnLaboratorio.setMnemonic('l');
        btnLaboratorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLaboratorio_actionPerformed(e);
            }
        });
        txtLaboratorio.setBounds(new Rectangle(195, 15, 320, 20));
        txtLaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLaboratorio_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtLaboratorio_keyReleased(e);
            }
        });
        scrListaLabs.setBounds(new Rectangle(10, 80, 735, 230));
        lblF1.setBounds(new Rectangle(10, 345, 120, 20));
        lblF1.setText("[ F5 ] Ver Productos");
        lblEscape.setBounds(new Rectangle(645, 345, 100, 20));
        lblEscape.setText("[ Esc ] Cerrar");
        lblIngCrit.setText("Ingrese Criterio de búsqueda");
        lblIngCrit.setBounds(new Rectangle(5, 0, 170, 15));
        jLabelWhite1.setText("Relación de laboratorios :");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 140, 15));
        jPanelTitle2.setBounds(new Rectangle(10, 310, 735, 25));
        jPanelTitle2.setLayout(null);
        btnRelacionLaboratoriosToma1.setMnemonic('r');
        btnRelacionLaboratoriosToma2.setMnemonic('r');
        lblCantReg_T.setText("Cantidad de registros :");
        lblCantReg_T.setBounds(new Rectangle(5, 5, 135, 15));
        lblCantReg.setText("0");
        lblCantReg.setBounds(new Rectangle(145, 5, 45, 15));
        lblF12.setBounds(new Rectangle(525, 345, 105, 20));
        lblF12.setText("[ F12 ] Imprimir");
        lblF9.setBounds(new Rectangle(355, 345, 155, 20));
        lblF9.setText("[ F9] Imprimir Completo");
        jPanelHeader1.add(lblIngCrit, null);
        jPanelHeader1.add(txtLaboratorio, null);
        jPanelHeader1.add(btnLaboratorio, null);
        jPanelTitle2.add(lblCantReg, null);
        jPanelTitle2.add(lblCantReg_T, null);
        jPanelTitle2.add(btnRelacionLaboratoriosToma2, null);
        jPanelTitle2.add(btnRelacionLaboratoriosToma1, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(jPanelTitle2, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblF1, null);
        scrListaLabs.getViewport().add(tblRelacionLaboratorios, null);
        jContentPane.add(scrListaLabs, null);
        jPanelTitle1.add(jLabelWhite1, null);
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
        mostrarDatos();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableLaboratoriosToma() {
        tableModel =
                new FarmaTableModel(ConstantsTomaInv.columnsListaLaboratoriosIxL, ConstantsTomaInv.defaultValuesListaLaboratoriosIxL,
                                    0);
        FarmaUtility.initSimpleList(tblRelacionLaboratorios, tableModel, ConstantsTomaInv.columnsListaLaboratoriosIxL);
        cargaLaboratoriosToma();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS)) {
            lblF9.setVisible(true);
        } else
            lblF9.setVisible(false);
        FarmaUtility.moveFocus(txtLaboratorio);
    }

    private void txtLaboratorio_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionLaboratorios, txtLaboratorio, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblRelacionLaboratorios.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblRelacionLaboratorios, txtLaboratorio.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Laboratorio No Encontrado según Criterio de Búsqueda !!!",
                                             txtLaboratorio);
                    return;
                }
            }
        }
        chkKeyPressed(e);
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
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tieneRegistroSeleccionado(tblRelacionLaboratorios)) {
                cargarRegistroSeleccionado();
                DlgListaItemsLaboratorio dlgListaItemsLaboratorio =
                    new DlgListaItemsLaboratorio(myParentFrame, "", true);
                dlgListaItemsLaboratorio.setVisible(true);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            imprimir();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS)) {
                if (FarmaVariables.vEconoFar_Matriz)
                    imprimirProductosPorLaboratorio();
                else
                    FarmaUtility.showMessage(this, "Usted se encuentra en una aplicacion de un local de PtoVenta",
                                             txtLaboratorio);
            } else
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción",
                                         txtLaboratorio);
        }
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblRelacionLaboratorios, txtLaboratorio, 1);
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaLaboratoriosToma() {
        try {
            DBTomaInv.getListaLabsItemsLab(tableModel);
            if (tblRelacionLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionLaboratorios, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de de tomas hist");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de laboratorios : \n" +
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
        VariablesTomaInv.vCodLab =
                tblRelacionLaboratorios.getValueAt(tblRelacionLaboratorios.getSelectedRow(), 0).toString().trim();
        VariablesTomaInv.vNomLab =
                tblRelacionLaboratorios.getValueAt(tblRelacionLaboratorios.getSelectedRow(), 1).toString().trim();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void mostrarDatos() {
        lblCantReg.setText("" + tblRelacionLaboratorios.getRowCount());
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtLaboratorio);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE LABORATORIOS", true);
            vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);
            vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printLine("==================================================================================================================================",
                             true);
            vPrint.printBold("Codigo        Descripción                                                 Dirección                                        Teléfono                    ",
                             true);
            vPrint.printLine("==================================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblRelacionLaboratorios.getRowCount(); i++) {
                //			for (int i = 0; i < 20; i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblRelacionLaboratorios.getValueAt(i,
                                                                                                                  0),
                                                                       14) +
                        //                        + "."
                        FarmaPRNUtility.alinearIzquierda((String)tblRelacionLaboratorios.getValueAt(i, 1), 50) +
                        //                        + "."
                        FarmaPRNUtility.alinearIzquierda((String)tblRelacionLaboratorios.getValueAt(i, 2), 40) +
                        //                      + "."
                        FarmaPRNUtility.alinearIzquierda((String)tblRelacionLaboratorios.getValueAt(i, 3), 20),
                        //                        + "."
                        true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("==================================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaUtility.formatNumber(tblRelacionLaboratorios.getRowCount(), ",##0") +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (Exception sqlerr) {
            log.error("", sqlerr);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sqlerr.getMessage(), txtLaboratorio);
        }
    }

    private void obtieneListaCodigosLaboratorios() {
        try {
            VariablesTomaInv.vListaCodLab = DBTomaInv.obtieneCodigoLaboratorios();
            FarmaUtility.ordenar(VariablesTomaInv.vListaCodLab, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            VariablesTomaInv.vListaCodLab = new ArrayList();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener información de comprobantes : \n" +
                    sql.getMessage(), null);
        }
    }

    private void obtieneProductosPorLaboratorio() {
        try {
            VariablesTomaInv.vListaProductos.clear();
            VariablesTomaInv.vListaProductos = DBTomaInv.obtieneProductosPorLaboratotio(VariablesTomaInv.vCodLab);
            FarmaUtility.ordenar(VariablesTomaInv.vListaProductos, 1, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            VariablesTomaInv.vListaProductos = new ArrayList();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener información de comprobantes : \n" +
                    sql.getMessage(), null);
        }
    }

    private void imprimirProductosPorLaboratorio() {
        obtieneListaCodigosLaboratorios();
        if (VariablesTomaInv.vListaCodLab.size() == 0) {
            FarmaUtility.showMessage(this, "No se encuentran Laboratorios para imprimir", txtLaboratorio);
            return;
        }
        log.debug("VariablesTomaInv.vListaCodLab.size() " + VariablesTomaInv.vListaCodLab.size());
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        for (int i = 0; i < VariablesTomaInv.vListaCodLab.size(); i++) {
            VariablesTomaInv.vCodLab = ((String)((ArrayList)VariablesTomaInv.vListaCodLab.get(i)).get(0)).trim();
            VariablesTomaInv.vNomLab = ((String)((ArrayList)VariablesTomaInv.vListaCodLab.get(i)).get(1)).trim();
            obtieneProductosPorLaboratorio();

            FarmaPrintService vPrint =
                new FarmaPrintService(66, "C:\\TomaInventario\\" + FarmaVariables.vCodLocal + "\\" +
                                      FarmaVariables.vCodLocal + "_" +
                    //i
                    FarmaUtility.caracterIzquierda(i, 5, "0") + "_" + VariablesTomaInv.vCodLab + ".txt", false);
            if (!vPrint.startPrintService()) {
                FarmaUtility.showMessage(this,
                                         "No se pudo inicializar el proceso de impresión\n para el laboratorio " +
                                         VariablesTomaInv.vCodLab + " - " + VariablesTomaInv.vNomLab, txtLaboratorio);
                return;
            }
            try {
                String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
                vPrint.setStartHeader();
                vPrint.activateCondensed();
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(30) + " REPORTE PRODUCTOS POR LABORATORIO", true);
                vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);
                vPrint.printBold("Nombre Local : " + FarmaVariables.vDescLocal.trim(), true);
                vPrint.printBold("Laboratorio: " + VariablesTomaInv.vNomLab.trim() + " - " + VariablesTomaInv.vCodLab,
                                 true);
                vPrint.printBold("Fecha: " + fechaActual, true);

                vPrint.printLine("=============================================================================================================",
                                 true);
                vPrint.printBold("Codigo  Descripcion                                Unid Presentacion  Unidad Venta       Entero    Fraccion  ",
                                 true);
                vPrint.printLine("=============================================================================================================",
                                 true);
                vPrint.deactivateCondensed();
                vPrint.setEndHeader();
                for (int x = 0; x < VariablesTomaInv.vListaProductos.size(); x++) {
                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesTomaInv.vListaProductos.get(x)).get(0)),
                                                                           8) +
                                          FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesTomaInv.vListaProductos.get(x)).get(1)),
                                                                           44) +
                                          FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesTomaInv.vListaProductos.get(x)).get(2)),
                                                                           19) +
                                          FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)VariablesTomaInv.vListaProductos.get(x)).get(3)),
                                                                           18) + " " +
                                          FarmaPRNUtility.alinearIzquierda("______    ________", 18), true);
                }
                vPrint.activateCondensed();
                vPrint.printLine("=============================================================================================================",
                                 true);
                vPrint.printBold("Registros Impresos: " +
                                 FarmaUtility.formatNumber(VariablesTomaInv.vListaProductos.size(), ",##0"), true);
                vPrint.deactivateCondensed();
                vPrint.endPrintServiceSinCompletar();
            } catch (Exception sqlerr) {
                log.error("", sqlerr);
                FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                        sqlerr.getMessage(), txtLaboratorio);
            }
        }
    }
}
