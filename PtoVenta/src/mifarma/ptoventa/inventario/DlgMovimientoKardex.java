package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMovimientoKardex extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMovimientoKardex.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JLabelWhite lblProducto_T = new JLabelWhite();

    private JLabelWhite lblProducto = new JLabelWhite();

    private JLabelWhite lblUnidad_T = new JLabelWhite();

    private JLabelWhite lblStock_T = new JLabelWhite();

    private JLabelWhite lblUnidad = new JLabelWhite();

    private JLabelWhite lblStockEntero = new JLabelWhite();

    private JTextFieldSanSerif txtFechaInicio = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();

    private JButton btnBuscar = new JButton();

    private JButtonLabel btnRangoFechas = new JButtonLabel();

    private JPanelTitle pnllTitle1 = new JPanelTitle();

    private JButtonLabel btnRelacionMovimiento = new JButtonLabel();

    private JScrollPane scrListaProductos = new JScrollPane();

    private JTable tblListaMovs = new JTable();

    private JLabelFunction lblF12 = new JLabelFunction();

    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelWhite lblLaboratorio_T = new JLabelWhite();

    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JLabelWhite lblUnidadFraccion = new JLabelWhite();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JPanelTitle pnllTitle2 = new JPanelTitle();
    private JButtonLabel btnRelacionMovimiento1 = new JButtonLabel();
    private JPanelTitle pnllTitle3 = new JPanelTitle();
    private JLabelWhite lblCantMovimientoT = new JLabelWhite();
    private JLabelWhite lblCantMovieminto = new JLabelWhite();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabel lblExcluido = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMovimientoKardex() {
        this(null, "", false);
    }

    public DlgMovimientoKardex(Frame parent, String title, boolean modal) {
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

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(780, 395));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Movimiento de Kardex");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 760, 80));
        lblProducto_T.setText("Producto:");
        lblProducto_T.setBounds(new Rectangle(10, 10, 65, 20));
        lblProducto.setText("TREUPEL-NF NIÑOS ");
        lblProducto.setBounds(new Rectangle(70, 10, 205, 20));
        lblProducto.setFont(new Font("SansSerif", 0, 11));
        lblUnidad_T.setText("Unidad:");
        lblUnidad_T.setBounds(new Rectangle(285, 10, 45, 20));
        lblStock_T.setText("Stock:");
        lblStock_T.setBounds(new Rectangle(470, 35, 35, 20));
        lblUnidad.setText("CJA/5 SUPOS ");
        lblUnidad.setBounds(new Rectangle(330, 10, 100, 20));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblStockEntero.setText("10");
        lblStockEntero.setBounds(new Rectangle(510, 35, 55, 20));
        lblStockEntero.setFont(new Font("SansSerif", 0, 11));
        txtFechaInicio.setBounds(new Rectangle(125, 45, 85, 20));
        txtFechaInicio.setDocument(new FarmaLengthText(10));
        txtFechaInicio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaInicio_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaInicio_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(225, 45, 85, 20));
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(330, 45, 90, 20));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnRangoFechas.setText("Rango de Fechas:");
        btnRangoFechas.setBounds(new Rectangle(10, 45, 110, 20));
        btnRangoFechas.setMnemonic('F');
        btnRangoFechas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRangoFechas_actionPerformed(e);
            }
        });
        pnllTitle1.setBounds(new Rectangle(10, 105, 760, 25));
        btnRelacionMovimiento.setText("Relación de Movimientos del Producto");
        btnRelacionMovimiento.setBounds(new Rectangle(15, 5, 215, 15));
        btnRelacionMovimiento.setMnemonic('R');
        btnRelacionMovimiento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionMovimiento_actionPerformed(e);
            }
        });
        scrListaProductos.setBounds(new Rectangle(10, 130, 760, 175));
        tblListaMovs.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaMovs_keyReleased(e);
            }
        });
        lblF12.setText("[ F12 ] Imprimir");
        lblF12.setBounds(new Rectangle(555, 340, 105, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(675, 340, 85, 20));
        lblLaboratorio_T.setText("Laboratorio:");
        lblLaboratorio_T.setBounds(new Rectangle(440, 10, 70, 20));
        lblLaboratorio.setText("PERUANO GERMANO (EDMUNDO STAHL)");
        lblLaboratorio.setBounds(new Rectangle(510, 10, 215, 20));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblUnidadFraccion.setBounds(new Rectangle(575, 35, 135, 20));
        lblUnidadFraccion.setFont(new Font("SansSerif", 0, 11));
        jLabelFunction1.setBounds(new Rectangle(10, 340, 100, 20));
        jLabelFunction1.setText("[ F6 ] Filtrar");
        jLabelFunction2.setBounds(new Rectangle(125, 340, 115, 20));
        jLabelFunction2.setText("[ F7] Quitar Filtro");
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setBounds(new Rectangle(250, 340, 135, 20));
        lblF8.setVisible(false);
        pnllTitle2.setBounds(new Rectangle(10, 105, 760, 25));
        btnRelacionMovimiento1.setText("Relación de Movimientos del Producto");
        btnRelacionMovimiento1.setBounds(new Rectangle(15, 5, 215, 15));
        btnRelacionMovimiento1.setMnemonic('R');
        btnRelacionMovimiento1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionMovimiento_actionPerformed(e);
            }
        });
        pnllTitle3.setBounds(new Rectangle(10, 305, 760, 25));
        lblCantMovimientoT.setText("Cantidad de Movimiento :");
        lblCantMovimientoT.setBounds(new Rectangle(10, 5, 145, 15));
        lblCantMovieminto.setBounds(new Rectangle(160, 5, 110, 15));
        lblF9.setText("[ F9 ] Excluir Calculo");
        lblF9.setBounds(new Rectangle(415, 340, 135, 20));
        lblF9.setVisible(true);
        lblExcluido.setBounds(new Rectangle(550, 0, 135, 25));
        lblExcluido.setFont(new Font("SansSerif", 1, 12));
        lblExcluido.setText("Excluido de Reposición");
        lblExcluido.setBackground(new Color(44, 146, 24));
        lblExcluido.setOpaque(true);
        lblExcluido.setHorizontalAlignment(SwingConstants.CENTER);
        lblExcluido.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblExcluido.setForeground(Color.white);
        lblExcluido.setVisible(false);
        pnllTitle2.add(lblExcluido, null);
        pnllTitle2.add(btnRelacionMovimiento1, null);
        pnlHeader1.add(lblUnidadFraccion, null);
        pnlHeader1.add(lblLaboratorio, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        pnlHeader1.add(btnRangoFechas, null);
        pnlHeader1.add(btnBuscar, null);
        pnlHeader1.add(txtFechaFin, null);
        pnlHeader1.add(txtFechaInicio, null);
        pnlHeader1.add(lblStockEntero, null);
        pnlHeader1.add(lblUnidad, null);
        pnlHeader1.add(lblStock_T, null);
        pnlHeader1.add(lblProducto, null);
        pnlHeader1.add(lblProducto_T, null);
        pnlHeader1.add(lblUnidad_T, null);
        pnllTitle3.add(lblCantMovieminto, null);
        pnllTitle3.add(lblCantMovimientoT, null);
        jPanelWhite1.add(lblF9, null);
        jPanelWhite1.add(pnllTitle3, null);
        jPanelWhite1.add(pnllTitle2, null);
        jPanelWhite1.add(lblF8, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(lblF12, null);
        scrListaProductos.getViewport().add(tblListaMovs, null);
        jPanelWhite1.add(scrListaProductos, null);
        pnllTitle1.add(btnRelacionMovimiento, null);
        jPanelWhite1.add(pnllTitle1, null);
        jPanelWhite1.add(pnlHeader1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && FarmaVariables.vEconoFar_Matriz)
            lblF8.setVisible(true);
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaMovsKardex, ConstantsInventario.defaultListaMovsKardex,
                                    0);
        FarmaUtility.initSimpleList(tblListaMovs, tableModel, ConstantsInventario.columnsListaMovsKardex);
        cargarFechas();

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void btnRangoFechas_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaInicio);
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();

        } else {
            chkKeyPressed(e);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaInicio);
        mostrarDatos();
    }

    private void txtFechaInicio_keyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblListaMovs, txtFechaInicio,
        //		0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFechaFin);
        } else {
            chkKeyPressed(e);
        }

    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (datosValidados()) {
            cargarFechas();
            cargaListaMovimientos();


        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtFechaInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicio, e);
        FarmaGridUtils.buscarDescripcion(e, tblListaMovs, txtFechaInicio, 0);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {

            if (tblListaMovs.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "No existe informacion a imprimir", txtFechaInicio);
            else
                imprimir();

        }
        if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (tblListaMovs.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Debe listar Movimientos de Kardex para poder filtrar", txtFechaInicio);
            } else {
                listaFiltroKardex();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            cargaListaMovimientos();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (lblF8.isVisible()) {
                int[] ancho = { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
                FarmaUtility.saveFile(myParentFrame, ConstantsInventario.columnsListaMovsKardex, tblListaMovs, ancho);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                          "Esta seguro de excluir la venta para el calculo del pedido de reposicion"))
                actualizaIndCalMaxMin();
            else
                FarmaUtility.showMessage(this, "Se cancelo la operacion", tblListaMovs);
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

    private void mostrarDatos() {
        lblProducto.setText(VariablesInventario.vCodProd + " " + VariablesInventario.vDescProd);
        lblLaboratorio.setText(VariablesInventario.vNomLab);
        lblUnidad.setText(VariablesInventario.vDescUnidPresent);
        lblStockEntero.setText("" + VariablesInventario.vStock);
        lblUnidadFraccion.setText(VariablesInventario.vDescUnidFrac);
    }

    private boolean datosValidados() {
        if (!FarmaUtility.validaFecha(txtFechaInicio.getText(), "dd/MM/yyyy") ||
            txtFechaInicio.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Inicial correctamente", txtFechaInicio);
            return false;
        }
        if (!FarmaUtility.validaFecha(txtFechaFin.getText(), "dd/MM/yyyy") || txtFechaFin.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha Final correctamente", txtFechaFin);
            return false;
        }
        Date fecIni = FarmaUtility.getStringToDate(txtFechaInicio.getText().trim(), "dd/MM/yyyy");
        Date fecFin = FarmaUtility.getStringToDate(txtFechaFin.getText().trim(), "dd/MM/yyyy");

        if (fecIni.after(fecFin)) {
            FarmaUtility.showMessage(this, "La Fecha Inicial no puede ser posterior a la Fecha Final", txtFechaInicio);
            return false;
        }
        return true;
    }

    private void cargaListaMovimientos() {
        try {
            DBInventario.getListaMovsKardex(tableModel);
            sumaCantidades();
            if (tblListaMovs.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaMovs, tableModel, 10, FarmaConstants.ORDEN_DESCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de movimientos : \n" +
                    sql.getMessage(), txtFechaInicio);
        }
        if (tblListaMovs.getRowCount() == 0) {
            FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtFechaInicio);
            return;
        }
        FarmaUtility.moveFocusJTable(tblListaMovs);

    }

    private void cargarFechas() {
        VariablesInventario.vFecIniMovKardex = txtFechaInicio.getText().trim();
        VariablesInventario.vFecFinMovKardex = txtFechaFin.getText().trim();
    }

    private void iniciarValoresFecha() {
        String fecActual = "";
        try {
            fecActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la fecha del sistema : \n" +
                    sql.getMessage(), txtFechaInicio);
            Date fec = new Date();
            fecActual = fec.toString();
        }
        txtFechaInicio.setText(fecActual);
        txtFechaFin.setText(fecActual);
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtFechaInicio);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE MOVIMIENTOS DE KARDEX " +
                             FarmaPRNUtility.llenarBlancos(20) + "Fecha de Emision : " + fechaActual, true);
            vPrint.printBold(" ", true);
            vPrint.printBold("Codigo           : " + VariablesInventario.vCodProd, true);
            vPrint.printBold("Producto         : " + VariablesInventario.vDescProd, true);
            vPrint.printBold("Laboratorio      : " + VariablesInventario.vNomLab, true);
            vPrint.printBold("Presentacion     : " + VariablesInventario.vDescUnidPresent, true);
            vPrint.printBold("Periodo          : " + VariablesInventario.vFecIniMovKardex + " - " +
                             VariablesInventario.vFecFinMovKardex, true);

            vPrint.printBold(" ", true);
            vPrint.printLine("===========================================================================================================================",
                             true);
            vPrint.printBold("Fecha              Descripcion              Tipo   Num Doc    Stk Ant      Mov  Stk Act Val Frac Usuario   Glosa",
                             true);
            vPrint.printLine("===========================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblListaMovs.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 0), 18) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 1), 24) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 2), 6) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 3), 10) +
                                      " " + FarmaPRNUtility.alinearDerecha((String)tblListaMovs.getValueAt(i, 4), 7) +
                                      " " + FarmaPRNUtility.alinearDerecha((String)tblListaMovs.getValueAt(i, 5), 8) +
                                      " " + FarmaPRNUtility.alinearDerecha((String)tblListaMovs.getValueAt(i, 6), 8) +
                                      " " + FarmaPRNUtility.alinearDerecha((String)tblListaMovs.getValueAt(i, 7), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 8), 8) +
                                      "  " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaMovs.getValueAt(i, 9), 10) +
                                      " ", true);
            }
            vPrint.activateCondensed();
            vPrint.printLine("===========================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(tblListaMovs.getRowCount(), ",##0") +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n " + sql.getMessage(), txtFechaInicio);
        }
    }

    private void btnRelacionMovimiento_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaMovs);
    }

    private void listaFiltroKardex() {
        DlgFiltroDescripcionMovKardex dlgFiltroDescripcionMovKardex =
            new DlgFiltroDescripcionMovKardex(myParentFrame, "", true);
        dlgFiltroDescripcionMovKardex.setVisible(true);
        if (FarmaVariables.vAceptar) {
            try {
                DBInventario.listaFiltroMvsKardex(tableModel);
                sumaCantidades();
                if (tblListaMovs.getRowCount() <= 0) {
                    FarmaUtility.showMessage(this, "El filtro no arrojó resultados.", txtFechaInicio);
                } else
                    FarmaUtility.ordenar(tblListaMovs, tableModel, 10, FarmaConstants.ORDEN_DESCENDENTE);
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al obtener la lista de filtrado del kardex : \n" +
                        sql.getMessage(), txtFechaInicio);
            }
        }
    }

    private void sumaCantidades() {
        lblCantMovieminto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaMovs, 11)));
    }

    private void obtieneDatosProducto() {
        VariablesInventario.vCantAtendida =
                "" + (Integer.parseInt(FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(),
                                                                        5)) * -1);
        VariablesInventario.vNumPedido =
                FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 12);
        VariablesInventario.vIndExclusion =
                FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 13);
        VariablesInventario.vCodMotKardex =
                FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 14);

        log.debug("VariablesInventario.vCantAtendida : " + VariablesInventario.vCantAtendida);
        log.debug("VariablesInventario.vNumPedido : " + VariablesInventario.vNumPedido);
    }

    private void actualizaIndCalMaxMin() {
        try {
            obtieneDatosProducto();
            if (VariablesInventario.vCodMotKardex.equalsIgnoreCase(ConstantsInventario.COD_MOT_VENTA_NORMAL) ||
                VariablesInventario.vCodMotKardex.equalsIgnoreCase(ConstantsInventario.COD_MOT_VENTA_DELIVERY) ||
                VariablesInventario.vCodMotKardex.equalsIgnoreCase(ConstantsInventario.COD_MOT_VENTA_ESPECIAL)) {
                if (VariablesInventario.vIndExclusion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    DBInventario.actualizaIndCalMaxMin(VariablesInventario.vCodProd, VariablesInventario.vCantAtendida,
                                                       VariablesInventario.vNumPedido);
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "La venta se excluyó para el cálculo del pedido de reposición",
                                             tblListaMovs);
                    tblListaMovs.setValueAt(FarmaConstants.INDICADOR_N, tblListaMovs.getSelectedRow(), 13);
                } else
                    FarmaUtility.showMessage(this, "El producto ya fue excluido de la venta.", tblListaMovs);
            } else
                FarmaUtility.showMessage(this,
                                         "El producto no pertenece a una venta. No se puede excluir del calculo de pedido de reposicion",
                                         tblListaMovs);

        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Ocurrio un error al actualizar el indicador de Calculo de Pedido Reposcion",
                                     tblListaMovs);
        }
    }

    private void tblListaMovs_keyReleased(KeyEvent e) {
        muestraIndicadorExcluido();
    }

    private void muestraIndicadorExcluido() {
        if (tblListaMovs.getRowCount() > 0) {
            String indExcluido = FarmaUtility.getValueFieldJTable(tblListaMovs, tblListaMovs.getSelectedRow(), 13);
            if (indExcluido.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                lblExcluido.setVisible(true);
            } else
                lblExcluido.setVisible(false);
        }
    }

}
