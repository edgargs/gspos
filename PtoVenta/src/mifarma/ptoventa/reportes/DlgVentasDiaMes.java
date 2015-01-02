package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableComparator;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgVentasDiaMes extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgVentasDiaMes.class);

    private FarmaTableModel tableModelVentas;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane scrVentas = new JScrollPane();
    private JTable tblVentas = new JTable();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblCantReg = new JLabel();
    private JLabelFunction lblSalir = new JLabelFunction();

    public DlgVentasDiaMes() {
        this(null, "", false);
    }

    public DlgVentasDiaMes(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(780, 455));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ventas por Día - Mes");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 755, 55));
        pnlTitulo.setBounds(new Rectangle(10, 70, 755, 20));
        pnlResultados.setBounds(new Rectangle(10, 360, 755, 20));
        scrVentas.setBounds(new Rectangle(10, 90, 755, 270));
        tblVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblVentasProducto_keyPressed(e);
            }
        });
        btnRandoFec.setText("Rango de Fechas");
        btnRandoFec.setBounds(new Rectangle(80, 5, 100, 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(190, 5, 101, 19));
        txtFechaIni.setDocument(new FarmaLengthText(10));
        txtFechaIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(315, 5, 101, 19));
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(445, 20, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnListado.setText("Relación de Ventas");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(35, 0, 75, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblCantReg.setText("0");
        lblCantReg.setBounds(new Rectangle(95, 0, 40, 20));
        lblCantReg.setFont(new Font("SansSerif", 1, 11));
        lblCantReg.setForeground(Color.white);
        lblCantReg.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSalir.setBounds(new Rectangle(670, 390, 85, 20));
        lblSalir.setText("[ ESC ] Cerrar");
        btnVerResumen.setText("Ver Resumen");
        btnVerResumen.setBounds(new Rectangle(80, 30, 100, 20));
        btnVerResumen.setMnemonic('v');
        btnVerResumen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnVerResumen_actionPerformed(e);
            }
        });
        lblTotPedidos.setText("0");
        lblTotPedidos.setBounds(new Rectangle(160, 0, 55, 20));
        lblTotPedidos.setFont(new Font("SansSerif", 1, 11));
        lblTotPedidos.setForeground(Color.white);
        lblTotPedidos.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotVentaIncIgv.setText("0.0");
        lblTotVentaIncIgv.setBounds(new Rectangle(225, 0, 90, 20));
        lblTotVentaIncIgv.setFont(new Font("SansSerif", 1, 11));
        lblTotVentaIncIgv.setForeground(Color.white);
        lblTotVentaIncIgv.setHorizontalAlignment(SwingConstants.RIGHT);
        cmbResumen.setBounds(new Rectangle(190, 30, 230, 20));
        cmbResumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbResumen_keyPressed(e);
            }
        });
        lblLocal.setText("JOCKEY PLAZA");
        lblLocal.setBounds(new Rectangle(485, 0, 265, 20));
        lblGuardarArchiv1.setBounds(new Rectangle(5, 390, 125, 20));
        lblGuardarArchiv1.setText("[F8] Ordenar");
        lblGuardarArchiv2.setBounds(new Rectangle(530, 390, 125, 20));
        lblGuardarArchiv2.setText("[F11] Imprimir");
        jContentPane.add(lblGuardarArchiv2, null);
        jContentPane.add(lblGuardarArchiv1, null);
        jContentPane.add(lblSalir, null);
        scrVentas.getViewport().add(tblVentas, null);
        jContentPane.add(scrVentas, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(lblLocal, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlResultados.add(lblTotVentaIncIgv, null);
        pnlResultados.add(lblTotPedidos, null);
        pnlResultados.add(lblCantReg, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(cmbResumen, null);
        pnlCriterioBusqueda.add(btnVerResumen, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnRandoFec, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initCombo();
        initTableListaVentas();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaVentas() {
        tableModelVentas =
                new FarmaTableModel(ConstantsReporte.columnsListaVentasDiaMes, ConstantsReporte.defaultValuesListaVentasDiaMes,
                                    0);
        FarmaUtility.initSimpleList(tblVentas, tableModelVentas, ConstantsReporte.columnsListaVentasDiaMes);
    }


    private JButtonLabel btnVerResumen = new JButtonLabel();
    private JLabel lblTotPedidos = new JLabel();
    private JLabel lblTotVentaIncIgv = new JLabel();
    private JComboBox cmbResumen = new JComboBox();
    private JLabelWhite lblLocal = new JLabelWhite();
    private JLabelFunction lblGuardarArchiv1 = new JLabelFunction();
    private JLabelFunction lblGuardarArchiv2 = new JLabelFunction();

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */


    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbResumen);
        } else
            chkKeyPressed(e);
    }

    private void tblVentasProducto_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        mostrarDatos();
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();

    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblVentas.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblVentas);
        }
    }

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (tblVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
            else
                muestraVentanaOrdenar();

        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (tblVentas.getRowCount() > 0) {
                ;
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            imprimir();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void buscaRegistroVentas(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaListaRegistroVentas();
    }

    private void cargaListaRegistroVentas() {
        try {
            VariablesReporte.vCodFiltro = "" + cmbResumen.getSelectedIndex();
            DBReportes.cargaListaVentasDiaMes(tableModelVentas);
            if (tblVentas.getRowCount() > 0) {
                FarmaUtility.ordenar(tblVentas, tableModelVentas, 6, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocus(tblVentas);
            }
            if (tblVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar la Lista de Registro de ventas :\n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
        mostrarTotales();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;
        return retorno;
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else {
            chkKeyPressed(e);
        }
    }

    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    buscaRegistroVentas(FechaInicio, FechaFin);

                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void mostrarTotales() {
        if (tblVentas.getRowCount() > 0) {
            int totPedidos = 0;
            double totVtaIGV = 0;

            for (int i = 0; i < tblVentas.getRowCount(); i++) {
                totPedidos = totPedidos + Integer.parseInt(tblVentas.getValueAt(i, 1).toString().trim());
                totVtaIGV =
                        totVtaIGV + FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.getDecimalNumber(tblVentas.getValueAt(i,
                                                                                                                               2).toString().trim()));
            }

            totVtaIGV = FarmaUtility.getDecimalNumberRedondeado(totVtaIGV);
            lblCantReg.setText("" + tblVentas.getRowCount());
            lblTotPedidos.setText("" + totPedidos);
            lblTotVentaIncIgv.setText(FarmaUtility.formatNumber(totVtaIGV));
        } else {
            lblCantReg.setText("0");
            lblTotPedidos.setText("0");
            lblTotVentaIncIgv.setText("0");
        }
    }

    private void cargarRegistroSeleccionado() {
        VariablesReporte.vCodProd = tblVentas.getValueAt(tblVentas.getSelectedRow(), 0).toString().trim();
        VariablesReporte.vDesProd = tblVentas.getValueAt(tblVentas.getSelectedRow(), 1).toString().trim();
        VariablesReporte.vDescUnidPresent = tblVentas.getValueAt(tblVentas.getSelectedRow(), 2).toString().trim();
        VariablesReporte.vNomLab = tblVentas.getValueAt(tblVentas.getSelectedRow(), 3).toString().trim();
        VariablesReporte.vFechaInicio = txtFechaIni.getText().trim();
        VariablesReporte.vFechaFin = txtFechaFin.getText().trim();

    }


    private void btnVerResumen_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbResumen);
    }

    private void initCombo() {
        cmbResumen.addItem("Por Día de la Semana");
    }

    private void cmbResumen_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else {
            chkKeyPressed(e);
        }
    }

    private void mostrarDatos() {
        this.lblLocal.setText(FarmaVariables.vDescCortaLocal.trim());
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtFechaIni);
            return;
        }
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE DE VENTAS POR DIA - MES ", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescCortaLocal, true);
            vPrint.printBold("Fecha Reporte: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Fin    : " + VariablesReporte.vFechaFin, true);
            vPrint.printLine("===============================================================================================================================",
                             true);
            vPrint.printBold("Periodo                        Pedidos          Vta. Inc IGV          Vale Promed.            Und X Vale         Pr Prom x Und ",
                             true);
            vPrint.printLine("===============================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblVentas.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblVentas.getValueAt(i, 0), 20) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 1), 18) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 2), 22) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 3), 22) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 4), 22) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 5), 22), true);
            }
            vPrint.activateCondensed();
            vPrint.printLine("===============================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(tblVentas.getRowCount(), ",##0") +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.printBold("Total de Pedidos  : " + lblTotPedidos.getText().trim() +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.printBold("Total Venta       : " + lblTotVentaIncIgv.getText().trim() +
                             FarmaPRNUtility.llenarBlancos(11), true);

            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), null);
        }
    }

    void muestraVentanaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO =
        { "Periodo", "#Pedidos", "Venta", "Vale Promedio", "UnidadX Vale", "Precio PromediosXUnid" };
        String[] IND_CAMPO = { "6", "1", "2", "3", "4", "5" };
        VariablesReporte.vNombreInHashtable = "IND_CAMPO_ORDENAR_VENTAS_DIA_MES";
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            Collections.sort(tableModelVentas.data, new FarmaTableComparator(VariablesReporte.vCampo, true));
            if (VariablesReporte.vOrden == FarmaConstants.ORDEN_DESCENDENTE)
                Collections.reverse(tableModelVentas.data);
            tableModelVentas.fireTableDataChanged();
            FarmaGridUtils.showCell(tblVentas, 0, 0);
        }
    }

}
