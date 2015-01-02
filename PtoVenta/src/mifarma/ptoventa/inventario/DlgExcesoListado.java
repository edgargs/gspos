package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgExcesoListado.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.04.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgExcesoListado extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgExcesoListado.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane srcListado = new JScrollPane();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JTable tblListado = new JTable();
    private JButtonLabel btnListaExcesos = new JButtonLabel();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgExcesoListado() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgExcesoListado(Frame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista  Exceso Productos");
        this.setSize(new Dimension(782, 341));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(675, 285, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        srcListado.setBounds(new Rectangle(10, 35, 760, 240));
        pnlTitle1.setBounds(new Rectangle(10, 10, 760, 25));
        btnListaExcesos.setText("Listado Excesos");
        btnListaExcesos.setBounds(new Rectangle(5, 5, 105, 15));
        btnListaExcesos.setMnemonic('L');
        btnListaExcesos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListaExcesos_keyPressed(e);
            }
        });
        btnListaExcesos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaExcesos_actionPerformed(e);
            }
        });
        lblF3.setBounds(new Rectangle(15, 285, 117, 19));
        lblF3.setText("[ F3 ] Modificar");
        lblF8.setBounds(new Rectangle(145, 285, 115, 20));
        lblF8.setText("[ F8 ] Ordenar");
        lblF9.setBounds(new Rectangle(285, 285, 115, 20));
        lblF9.setText("[ F12 ] Imprimir");
        pnlTitle1.add(btnListaExcesos, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(pnlTitle1, null);
        srcListado.getViewport().add(tblListado, null);
        jContentPane.add(srcListado, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaExcesos, ConstantsInventario.defaultValuesListaExcesos,
                                    0);
        FarmaUtility.initSimpleList(tblListado, tableModel, ConstantsInventario.columnsListaExcesos);
        cargaListaExcesos();
    }

    private void cargaListaExcesos() {
        try {
            DBInventario.cargaListaExcesos(tableModel);
            FarmaUtility.ordenar(tblListado, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar los excesos : \n" +
                    sql.getMessage(), btnListaExcesos);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnListaExcesos);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnListaExcesos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnListaExcesos);
    }

    private void btnListaExcesos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListado, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_F3) {
            modificar();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            ordenar();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (tblListado.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de imprmir", btnListaExcesos);
            else
                imprimir();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

    private void modificar() {
        if (tblListado.getSelectedRow() > -1) {
            int row = tblListado.getSelectedRow();
            if (verificaFecha(row)) {
                cargarDatosSeleccionados(row);
                DlgExcesoProducto dlgExcesoProducto = new DlgExcesoProducto(myParentFrame, "", true, true);
                dlgExcesoProducto.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    cargaListaExcesos();
                }
            } else {
                FarmaUtility.showMessage(this, "Solo puede modificar los registros creados el día de hoy.",
                                         btnListaExcesos);
            }

        }
    }

    private void cargarDatosSeleccionados(int row) {
        VariablesInventario.vCodProd = tblListado.getValueAt(row, 1).toString().trim();
        VariablesInventario.vDescProd = tblListado.getValueAt(row, 2).toString().trim();
        VariablesInventario.vDescUnidPresent = tblListado.getValueAt(row, 3).toString().trim();
        VariablesInventario.vNomLab = tblListado.getValueAt(row, 9).toString().trim();

        VariablesInventario.vSecExcProd = tblListado.getValueAt(row, 0).toString().trim();
        VariablesInventario.vCantExcProd = tblListado.getValueAt(row, 4).toString().trim();
        VariablesInventario.vNumEntExcProd = tblListado.getValueAt(row, 6).toString().trim();
        VariablesInventario.vNumLotExcProd = tblListado.getValueAt(row, 7).toString().trim();
        VariablesInventario.vFecVecExcProd = tblListado.getValueAt(row, 8).toString().trim();

    }

    private boolean verificaFecha(int row) {
        boolean retorno = false;
        String fecha;
        String sysdate;
        try {
            fecha = tblListado.getValueAt(row, 5).toString().trim().substring(0, 10);
            sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            //log.debug(fecha);
            //log.debug(sysdate);
            if (fecha.equals(sysdate))
                retorno = true;
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al verificar la fecha : \n" +
                    sql.getMessage(), btnListaExcesos);
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrió un error al verificar la fecha : \n" +
                    e.getMessage(), btnListaExcesos);
        }
        return retorno;
    }

    private void ordenar() {
        if (tblListado.getRowCount() > 0) {
            //
            DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
            // los datos de abajo son variables y constantes y tienen q crearlos respectivamente
            VariablesReporte.vNombreInHashtable = ConstantsInventario.vNombreInHashtableExceso;
            FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable,
                                           ConstantsInventario.vCodCampoExceso, ConstantsInventario.vDescCampoExceso,
                                           true);
            dlgOrdenar.setVisible(true);
            //
            if (FarmaVariables.vAceptar) {
                FarmaUtility.ordenar(tblListado, tableModel, VariablesReporte.vCampo, VariablesReporte.vOrden);
                tblListado.repaint();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;

        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", btnListaExcesos);
            return;
        }

        try {

            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE  DE EXCESO DE PRODUCTOS", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("===============================================================================================================",
                             true);
            vPrint.printBold("Secuencial   Codigo Prod  Descripcion                   Unidad         Cant  Fecha                Num Entrega       ",
                             true);
            vPrint.printLine("================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblListado.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblListado.getValueAt(i, 0), 11) +
                                      "  " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListado.getValueAt(i, 1), 11) +
                                      "  " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListado.getValueAt(i, 2), 29) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListado.getValueAt(i, 3), 14) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListado.getValueAt(i, 4), 5) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListado.getValueAt(i, 5), 20) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListado.getValueAt(i, 6), 10), true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("==================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblListado.getRowCount(),
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
