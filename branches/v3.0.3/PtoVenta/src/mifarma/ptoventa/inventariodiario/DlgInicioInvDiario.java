package mifarma.ptoventa.inventariodiario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

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
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgInicioInvDiario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.10.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgInicioInvDiario extends JDialog {
    /* ************************************************************************ */
    /*                        DECLARACION PROPIEDADES                           */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgInicioInvDiario.class);

    FarmaTableModel tableModelProductosToma;
    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrPane1 = new JScrollPane();
    private JTable tblRelacionProductos = new JTable();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JButtonLabel btnProductos = new JButtonLabel();
    private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblF7 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelWhite lblItems_T = new JLabelWhite();
    private JLabelWhite lblItems = new JLabelWhite();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgInicioInvDiario() {
        this(null, "", false);
    }

    public DlgInicioInvDiario(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(786, 416));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Productos Inventario Diario");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(5, 10, 770, 50));
        pnlHeader1.setLayout(null);
        pnlTitle1.setBounds(new Rectangle(5, 65, 770, 25));
        pnlTitle1.setLayout(null);
        scrPane1.setBounds(new Rectangle(5, 90, 770, 260));
        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 25));
        btnProductos.setText("Productos :");
        btnProductos.setMnemonic('p');
        btnProductos.setBounds(new Rectangle(30, 15, 65, 20));
        btnProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProductos_actionPerformed(e);
            }
        });
        txtProductos.setBounds(new Rectangle(115, 15, 315, 20));
        txtProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProductos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProductos_keyReleased(e);
            }
        });
        lblF3.setBounds(new Rectangle(200, 360, 90, 20));
        lblF3.setText("[ F3 ] Insertar");
        lblF4.setBounds(new Rectangle(110, 360, 85, 20));
        lblF4.setText("[ F5 ] Borrar");
        lblEsc.setBounds(new Rectangle(680, 360, 90, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF5.setBounds(new Rectangle(295, 360, 95, 20));
        lblF5.setText("[ F11 ] Aceptar");
        lblF6.setBounds(new Rectangle(395, 360, 90, 20));
        lblF6.setText("[ F2 ] Ajustar");
        lblF7.setBounds(new Rectangle(490, 360, 90, 20));
        lblF7.setText("[ F4 ] Retornar");

        lblItems_T.setText("Items:");
        lblItems_T.setBounds(new Rectangle(655, 0, 50, 25));
        lblItems.setText("1000");
        lblItems.setBounds(new Rectangle(695, 0, 35, 25));
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        lblF1.setBounds(new Rectangle(10, 360, 95, 20));
        lblF1.setText("[ F1 ] Inicializar");
        lblF12.setBounds(new Rectangle(585, 360, 90, 20));
        lblF12.setText("[ F12 ] Imprimir");
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF6, null);
        jContentPane.add(lblF5, null);
        scrPane1.getViewport().add(tblRelacionProductos, null);
        jContentPane.add(scrPane1, null);
        pnlTitle1.add(lblItems, null);
        pnlTitle1.add(lblItems_T, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(txtProductos, null);
        pnlHeader1.add(btnProductos, null);
        jContentPane.add(pnlHeader1, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF7, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaProductos() {
        tableModelProductosToma =
                new FarmaTableModel(ConstantsInvDiario.columnsListaProductosInicio, ConstantsInvDiario.defaultValuesListaProductosInicio,
                                    0);
        FarmaUtility.initSelectList(tblRelacionProductos, tableModelProductosToma,
                                    ConstantsInvDiario.columnsListaProductosInicio);
        cargaListaProductosInicio();
    }

    private void cargaListaProductosInicio() {
        try {
            DBInvDiario.getListaProdsInicio(tableModelProductosToma);
            if (tblRelacionProductos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblRelacionProductos, tableModelProductosToma, 2,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            }
            lblItems.setText(tblRelacionProductos.getRowCount() + "");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la lista de productos : \n" +
                    sql.getMessage(), txtProductos);
        }
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
        inicializarInvDiario();
        initTableListaProductos();
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductos, txtProductos, 1);


        if (e.getKeyCode() == KeyEvent.VK_ENTER) {


            e.consume();
            if (tblRelacionProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblRelacionProductos, txtProductos.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtProductos);
                    return;
                }
            }
        }

        chkKeyPressed(e);
    }

    private void txtProductos_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblRelacionProductos, txtProductos, 1);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtProductos);
            else {
                inicializarInvDiario();
                cargaListaProductosInicio();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtProductos);
            else
                insertar();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtProductos);
            else
                borrarProducto();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtProductos);
            else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA)) {
                aceptar();
            } else {
                FarmaUtility.showMessage(this,
                                         "El usuario no tiene el rol para iniciar una toma de inventario cíclico",
                                         txtProductos);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtProductos);
            else
                imprimir();
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

    /**
     * Inicializa los productos para un nuevo inventario Diario.
     */
    private void inicializarInvDiario() {
        try {
            DBInvDiario.initInventarioDiario();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al inicializar el inventario.\n" +
                    e.getMessage(), txtProductos);
        }
    }

    /**
     * Inserta nuevos productos a la toma.
     */
    private void insertar() {
        DlgProductosInvDiario dlgProductosInvDiario = new DlgProductosInvDiario(myParentFrame, "", true);
        dlgProductosInvDiario.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaProductosInicio();
            FarmaVariables.vAceptar = false;
        }
    }

    /**
     * Borra el producto seleccionado de la toma de inventario Diario.
     */
    private void borrarProducto() {
        String codProd;
        int row = tblRelacionProductos.getSelectedRow();
        if (row > -1) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro de borrar el producto?"))
                try {
                    codProd = tblRelacionProductos.getValueAt(row, 0).toString().trim();
                    DBInvDiario.borrarProducto(codProd);
                    FarmaUtility.aceptarTransaccion();
                    cargaListaProductosInicio();
                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", e);
                    FarmaUtility.showMessage(this, "Error al borrar producto.\n" +
                            e.getMessage(), txtProductos);
                }

        }
    }

    /**
     * Grabar nuevo inventario ciclico.
     */
    private void aceptar() {
        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                        "¿Está seguro de grabar el inventario cíclico?"))
            try {
                DBInvDiario.grabarInventarioDiario();
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "El inventario se grabó correctamente.", txtProductos);
                cerrarVentana(true);
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                log.error("", e);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al grabar la toma.\n" +
                        e.getMessage(), txtProductos);
            }
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtProductos);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE DE PRODUCTOS PARA INVENTARIO CICLICO",
                             true);
            vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia.trim(), true);
            vPrint.printBold("Nombre Local : " + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal.trim(),
                             true);
            vPrint.printBold("Fecha Actual    : " + fechaActual, true);


            vPrint.printLine("======================================================================================================================================",
                             true);
            vPrint.printBold("Codigo  Descripcion                               Laboratorio                   Unid Presentacion Unidad Venta   Stock      Stk Fisico",
                             true);
            vPrint.printLine("======================================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblRelacionProductos.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblRelacionProductos.getValueAt(i, 0),
                                                                       8) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionProductos.getValueAt(i, 1),
                                                                       42) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionProductos.getValueAt(i, 2),
                                                                       29) + " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionProductos.getValueAt(i, 3),
                                                                       18) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblRelacionProductos.getValueAt(i, 4),
                                                                       15) +
                                      FarmaPRNUtility.alinearDerecha((String)tblRelacionProductos.getValueAt(i, 5),
                                                                     10) +
                                      FarmaPRNUtility.alinearIzquierda(" __________", 11), true);
            }
            vPrint.activateCondensed();
            vPrint.printLine("==============================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaUtility.formatNumber(tblRelacionProductos.getRowCount(), ",##0") +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(" ", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(33) + "__________________________" +
                             FarmaPRNUtility.llenarBlancos(10) + "_________________________", true);
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(33) + "  Nombre y Firma del QF" +
                             FarmaPRNUtility.llenarBlancos(10) + "   Nombre y Firma del Auditor", true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (Exception sqlerr) {
            log.error("", sqlerr);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sqlerr.getMessage(), txtProductos);
        }
    }

}
