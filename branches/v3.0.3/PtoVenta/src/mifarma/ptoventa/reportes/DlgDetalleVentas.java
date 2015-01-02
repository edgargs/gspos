package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleVentas extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleVentas.class);

    private FarmaTableModel tableModelDetalleVentas;
    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRegistroVentas = new JTable();
    private JTable tblDetalleVentas = new JTable();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JLabel lblRegistros = new JLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    //private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabel lblTotal = new JLabel();
    private JLabel lblRegsitros_T1 = new JLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDetalleVentas() {
        this(null, "", false);
    }

    public DlgDetalleVentas(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(739, 429));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Reporte Detalle de Ventas");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 715, 30));
        pnlCriterioBusqueda.setFocusable(false);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(430, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(300, 5, 101, 19));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(175, 5, 101, 19));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(105, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(10, 50, 715, 20));
        pnlTitulo.setFocusable(false);
        btnListado.setText("Relacion de Productos Vendidos");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.setFocusable(false);
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 70, 715, 270));
        jScrollPane1.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        tblRegistroVentas.setBounds(new Rectangle(-5, 0, 0, 0));
        tblDetalleVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalleVentas_keyPressed(e);
            }
        });
        pnlResultados.setBounds(new Rectangle(10, 340, 715, 20));
        pnlResultados.setFocusable(false);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(90, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegistros.setFocusable(false);
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(15, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblRegsitros_T.setFocusable(false);
        lblEsc.setBounds(new Rectangle(640, 370, 85, 25));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        lblF12.setBounds(new Rectangle(510, 370, 110, 25));
        lblF12.setText("[ F12 ] Imprimir");
        lblF12.setFocusable(false);
        lblF9.setBounds(new Rectangle(395, 370, 85, 25));
        lblF9.setText("[ F9 ] Ordenar");
        //jLabelFunction1.setBounds(new Rectangle(375, 370, 130, 20));
        // jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        lblF9.setFocusable(false);
        lblF2.setBounds(new Rectangle(150, 370, 80, 20));
        lblF2.setText("[ F6] Filtrar");
        lblF1.setBounds(new Rectangle(10, 370, 120, 25));
        lblF1.setText("[ F1 ] Ver Resumen");
        lblF1.setFocusable(false);
        lblF3.setBounds(new Rectangle(0, 0, 80, 25));
        lblF3.setText("[ F6] Filtrar");
        lblF3.setFocusable(false);
        lblF4.setBounds(new Rectangle(265, 370, 105, 25));
        lblF4.setText("[ F7] Quitar Filtro");
        lblF4.setFocusable(false);
        lblTotal.setText("0");
        lblTotal.setBounds(new Rectangle(600, 0, 110, 20));
        lblTotal.setFont(new Font("SansSerif", 1, 11));
        lblTotal.setForeground(Color.white);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setFocusable(false);
        lblRegsitros_T1.setText("Total Venta");
        lblRegsitros_T1.setBounds(new Rectangle(515, 0, 70, 20));
        lblRegsitros_T1.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T1.setForeground(Color.white);
        lblRegsitros_T1.setFocusable(false);
        jScrollPane1.getViewport();
        pnlResultados.add(lblRegsitros_T1, null);
        pnlResultados.add(lblTotal, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        pnlTitulo.add(btnListado, null);
        jPanelWhite1.add(lblF4, null);
        jPanelWhite1.add(lblF1, null);
        lblF2.add(lblF3, null);
        jPanelWhite1.add(lblF2, null);
        jPanelWhite1.add(lblF9, null);
        jPanelWhite1.add(lblF12, null);
        jPanelWhite1.add(lblEsc, null);
        //jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(pnlResultados, null);
        jPanelWhite1.add(tblRegistroVentas, null);
        jScrollPane1.getViewport().add(tblDetalleVentas, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlTitulo, null);
        jPanelWhite1.add(pnlCriterioBusqueda, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaDetalleVentas();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaDetalleVentas() {
        tableModelDetalleVentas =
                new FarmaTableModel(ConstantsReporte.columnsListaDetalledeVentas, ConstantsReporte.defaultValuesListaDetalledeVentas,
                                    0);
        FarmaUtility.initSimpleList(tblDetalleVentas, tableModelDetalleVentas,
                                    ConstantsReporte.columnsListaDetalledeVentas);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
    }

    private void tblRegistroVentas_keyPressed(KeyEvent e) {
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            //FarmaUtility.moveFocus(txtFechaIni);
            btnBuscar.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);

        chkKeyPressed(e);
    }

    private void tblDetalleVentas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        } else if (UtilityPtoVenta.verificaVK_F1(e)) {
            if (tblDetalleVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni);
                return;
            } else
                resumenProductosVendido();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (tblDetalleVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
            else
                muestraDetalleVentaOrdenar();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (tblDetalleVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de filtar", txtFechaIni);
            else
                muestraFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            if (tblDetalleVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "No existe informacion para quitar filtro", txtFechaIni);
            else
                cargaDetalleVentas();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (tblDetalleVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "No existe informacion para guardar el archivo", txtFechaIni);
            else
                ;
            //exportarDatos();
        } else if (UtilityPtoVenta.verificaVK_F12(e)) {
            if (tblDetalleVentas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de imprmir", txtFechaIni);
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

    void muestraDetalleVentaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO =
        { "Correlativo", "Tipo", "Nro Comprobante", "Fecha", "Descripcion", "Unidad", "Cantidad", "Venta" };
        String[] IND_CAMPO = { "0", "1", "2", "8", "4", "5", "6", "7" };

        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEDETALLEVENTAS;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblDetalleVentas, tableModelDetalleVentas, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblDetalleVentas.repaint();
        }

    }

    void muestraFiltro() {
        VariablesReporte.vNombreInHashtable = ConstantsReporte.TIP_FILTRO;
        DlgFiltroDetalleVentas dlgFiltroDetalleVentas = new DlgFiltroDetalleVentas(myParentFrame, "Ordenar", true);
        dlgFiltroDetalleVentas.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaVentasFiltro();
            FarmaVariables.vAceptar = false;
        }
    }

    private void resumenProductosVendido() {
        DlgDetalleResumenVentas dlgDetalleResumenVentas = new DlgDetalleResumenVentas(myParentFrame, "", true);
        dlgDetalleResumenVentas.setVisible(true);
    }

    private void sumaTotal() {
        if (tblDetalleVentas.getRowCount() > 0) {
            double totalVentas = 0;
            for (int i = 0; i < tblDetalleVentas.getRowCount(); i++) {
                totalVentas =
                        totalVentas + FarmaUtility.getDecimalNumber(tblDetalleVentas.getValueAt(i, 7).toString().trim());
            }
            lblTotal.setText("" + totalVentas);
        }
    }

    private void cargaVentasFiltro() {
        try {
            DBReportes.cargaListaFiltroDetalleVentas(tableModelDetalleVentas, VariablesReporte.vFechaInicio,
                                                     VariablesReporte.vFechaFin, VariablesReporte.vCampoFiltro);
            if (tblDetalleVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblRegistros.setText("" + tblDetalleVentas.getRowCount());
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetalleVentas, 7)));
                return;
            }
            FarmaUtility.ordenar(tblDetalleVentas, tableModelDetalleVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
            lblRegistros.setText("" + tblDetalleVentas.getRowCount());
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetalleVentas, 7)));
            //sumaTotal();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar el filtro : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void exportarDatos() {

        File lfFile = new File("C:\\");
        JFileChooser filechooser = new JFileChooser(lfFile);
        filechooser.setDialogTitle("Seleccione el directorio destino");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (filechooser.showSaveDialog(this.myParentFrame) != JFileChooser.APPROVE_OPTION)
            return;
        File fileChoosen = filechooser.getSelectedFile();
        String ruta = fileChoosen.getAbsolutePath();

        FarmaUtility.saveFileRuta(ruta, ConstantsReporte.columnsListaDetalledeVentas, tblDetalleVentas,
                                  new int[] { 20, 10, 20, 20, 50, 50, 10, 30, 0 });

        FarmaUtility.showMessage(this, "Los datos se exportaron correctamente", txtFechaIni);

    }

    private void imprimir() {
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtFechaIni);
            return;
        }
        try {

            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";
            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE DE DETALLE DE VENTAS", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("======================================================================================================================",
                             true);
            vPrint.printBold("NroPedido  Tipo   NroComprobante  Fecha       Descripcion                    Unidad          Cantidad         Venta       ",
                             true);
            vPrint.printLine("=======================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblDetalleVentas.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 0), 11) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 1), 5) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 2), 15) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 3), 11) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 4), 30) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 5), 15) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 6), 10) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblDetalleVentas.getValueAt(i, 7), 15),
                                      true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("=========================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaUtility.formatNumber(tblDetalleVentas.getRowCount(), ",##0"), false);
            //vPrint.printBold(FarmaPRNUtility.llenarBlancos(40)+ lblTotal, true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al imprimir : \n" +
                    sql.getMessage(), null);
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
                    buscaDetalleVentas(FechaInicio, FechaFin);
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;
        return retorno;
    }

    private void buscaDetalleVentas(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaDetalleVentas();
    }

    private void cargaDetalleVentas() {
        try {
            DBReportes.cargaListaDetalledeVentas(tableModelDetalleVentas, VariablesReporte.vFechaInicio,
                                                 VariablesReporte.vFechaFin);
            lblRegistros.setText("" + tblDetalleVentas.getRowCount());
            if (tblDetalleVentas.getRowCount() == 0) {
                lblTotal.setText("0.00");
                FarmaUtility.showMessage(this, "La búsqueda no arrojó resultados.", txtFechaIni);
            } else {
                FarmaUtility.ordenar(tblDetalleVentas, tableModelDetalleVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblDetalleVentas, 7)));
                FarmaUtility.moveFocusJTable(tblDetalleVentas);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el detalle de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
            cerrarVentana(false);
        }
    }
}
