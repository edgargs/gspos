package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgVentasPorHora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      27.03.2005   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgVentasPorHora extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgVentasPorHora.class);

    private FarmaTableModel tableModelVentas;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane scrProductos = new JScrollPane();
    private JTable tblVentas = new JTable();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblCantReg = new JLabel();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblFiltrarProds = new JLabelFunction();
    private JLabelFunction lblQuitarFiltro = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabel lblTotProm = new JLabel();
    private JLabel lblTotPedProm = new JLabel();
    private JLabel lblTotUnid = new JLabel();
    private JLabel lblTotVenta = new JLabel();
    private JLabel lblTotPed = new JLabel();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgVentasPorHora() {
        this(null, "", false);
    }

    public DlgVentasPorHora(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(662, 490));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ventas por Hora");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 640, 55));
        pnlTitulo.setBounds(new Rectangle(10, 70, 640, 20));
        pnlResultados.setBounds(new Rectangle(10, 360, 640, 20));
        scrProductos.setBounds(new Rectangle(10, 90, 640, 270));
        btnRandoFec.setText("Rango de Fechas");
        btnRandoFec.setBounds(new Rectangle(75, 15, 100, 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRandoFec_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(185, 15, 101, 19));
        txtFechaIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(310, 15, 101, 19));
        txtFechaFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(440, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnListado.setText("Listado de Ventas por Hora");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListado_keyPressed(e);
            }
        });
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(15, 0, 75, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblCantReg.setText("0");
        lblCantReg.setBounds(new Rectangle(85, 0, 30, 20));
        lblCantReg.setFont(new Font("SansSerif", 1, 11));
        lblCantReg.setForeground(Color.white);
        lblCantReg.setHorizontalAlignment(SwingConstants.RIGHT);
        lblF8.setBounds(new Rectangle(270, 390, 100, 20));
        lblF8.setText("[F8] Ordenar");
        lblFiltrarProds.setBounds(new Rectangle(15, 390, 130, 20));
        lblFiltrarProds.setText("[F6] Filtrar por Días");
        lblQuitarFiltro.setBounds(new Rectangle(155, 390, 105, 20));
        lblQuitarFiltro.setText("[F7] Quitar Filtro");
        lblSalir.setBounds(new Rectangle(555, 420, 85, 20));
        lblSalir.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(515, 390, 100, 20));
        lblF11.setText("[F11] Imprimir");
        lblTotProm.setText("0");
        lblTotProm.setBounds(new Rectangle(520, 0, 100, 20));
        lblTotProm.setFont(new Font("SansSerif", 1, 11));
        lblTotProm.setForeground(Color.white);
        lblTotProm.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotPedProm.setText("0");
        lblTotPedProm.setBounds(new Rectangle(320, 0, 100, 20));
        lblTotPedProm.setFont(new Font("SansSerif", 1, 11));
        lblTotPedProm.setForeground(Color.white);
        lblTotPedProm.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotUnid.setText("0");
        lblTotUnid.setBounds(new Rectangle(420, 0, 100, 20));
        lblTotUnid.setFont(new Font("SansSerif", 1, 11));
        lblTotUnid.setForeground(Color.white);
        lblTotUnid.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotVenta.setText("0");
        lblTotVenta.setBounds(new Rectangle(220, 0, 100, 20));
        lblTotVenta.setFont(new Font("SansSerif", 1, 11));
        lblTotVenta.setForeground(Color.white);
        lblTotVenta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotPed.setText("0");
        lblTotPed.setBounds(new Rectangle(120, 0, 100, 20));
        lblTotPed.setFont(new Font("SansSerif", 1, 11));
        lblTotPed.setForeground(Color.white);
        lblTotPed.setHorizontalAlignment(SwingConstants.RIGHT);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblQuitarFiltro, null);
        jContentPane.add(lblFiltrarProds, null);
        jContentPane.add(lblF8, null);
        scrProductos.getViewport().add(tblVentas, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlResultados.add(lblTotPed, null);
        pnlResultados.add(lblTotVenta, null);
        pnlResultados.add(lblTotUnid, null);
        pnlResultados.add(lblTotPedProm, null);
        pnlResultados.add(lblTotProm, null);
        pnlResultados.add(lblCantReg, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnRandoFec, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        txtFechaIni.setDocument(new FarmaLengthText(10));
        txtFechaFin.setDocument(new FarmaLengthText(10));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaVentas();
        VariablesReporte.vInd_Filtro = FarmaConstants.INDICADOR_N;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaVentas() {
        tableModelVentas =
                new FarmaTableModel(ConstantsReporte.columnsListaVentasHora, ConstantsReporte.defaultValuesListaVentasHora,
                                    0);
        FarmaUtility.initSimpleList(tblVentas, tableModelVentas, ConstantsReporte.columnsListaVentasHora);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else
            chkKeyPressed(e);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else
            chkKeyPressed(e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
        btnListado.doClick();
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnRandoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblVentas.getRowCount() > 0) {
            FarmaUtility.moveFocus(btnListado);
        }
    }

    private void btnListado_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblVentas, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_F6) {
            cargaListaFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            btnBuscar.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            ordenarFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            /*if(tblVentas.getRowCount()>0)
        exportarDatos(); */
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (tblVentas.getRowCount() > 0)
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

    private void buscaRegistroVentas(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistroVentas();
    }

    private void cargaRegistroVentas() {
        try {
            tableModelVentas.clearTable();
            if (VariablesReporte.vCampoFiltro.equals(""))
                VariablesReporte.vCampoFiltro = "%";

            DBReportes.cargaListaVentasporHora(tableModelVentas, VariablesReporte.vFechaInicio,
                                               VariablesReporte.vFechaFin, VariablesReporte.vCampoFiltro);

            if (tblVentas.getRowCount() > 0)
                FarmaUtility.ordenar(tblVentas, tableModelVentas, 0, FarmaConstants.ORDEN_ASCENDENTE);
            else
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);

            mostrarTotales();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar la lista del registro de ventas : \n" +
                    sql.getMessage(), null);
        }
    }

    private void cargaListaFiltro() {
        if (tblVentas.getRowCount() > 0) {
            DlgFiltroDetalleVentas dlgFiltroDetalleVentas =
                new DlgFiltroDetalleVentas(myParentFrame, "", true, ConstantsReporte.vVentasPorHora,
                                           ConstantsReporte.vCodDiaSemana, ConstantsReporte.vDescDiaSemana);
            dlgFiltroDetalleVentas.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaRegistroVentas();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (txtFechaIni.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Inicio.", txtFechaIni);
        } else if (txtFechaFin.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Fin.", txtFechaFin);
        } else if (!FarmaUtility.validaFecha(txtFechaIni.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha.", txtFechaIni);
        } else if (!FarmaUtility.validaFecha(txtFechaFin.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha.", txtFechaFin);
        } else if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    private void busqueda() {
        if (validarCampos()) {
            VariablesReporte.vCampoFiltro = "";
            buscaRegistroVentas(txtFechaIni.getText().trim(), txtFechaFin.getText().trim());
            FarmaUtility.moveFocus(tblVentas);
        }
    }

    private void mostrarTotales() {
        if (tblVentas.getRowCount() > 0) {
            double totPed, totVtas, totProm, totUnid, totPre;

            totPed = FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.sumColumTable(tblVentas, 1));
            totVtas = FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.sumColumTable(tblVentas, 2));
            totProm = FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.sumColumTable(tblVentas, 3));
            totUnid = FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.sumColumTable(tblVentas, 4));
            totPre = FarmaUtility.getDecimalNumberRedondeado(FarmaUtility.sumColumTable(tblVentas, 5));

            lblCantReg.setText("" + tblVentas.getRowCount());
            lblTotPed.setText("" + totPed);
            lblTotVenta.setText("" + FarmaUtility.formatNumber(totVtas));
            lblTotPedProm.setText("" + FarmaUtility.formatNumber(totProm));
            lblTotUnid.setText("" + FarmaUtility.formatNumber(totUnid));
            lblTotProm.setText("" + FarmaUtility.formatNumber(totPre));
        } else {
            lblCantReg.setText("0");
            lblTotPed.setText("0");
            lblTotVenta.setText("0.00");
            lblTotPedProm.setText("0.00");
            lblTotUnid.setText("0.00");
            lblTotProm.setText("0.00");
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

        FarmaUtility.saveFileRuta(ruta, ConstantsReporte.columnsListaVentasHora, tblVentas,
                                  new int[] { 20, 15, 15, 15, 15, 15 });
        FarmaUtility.showMessage(this, "Los datos se exportaron correctamente", txtFechaIni);
    }

    private void ordenarFiltro() {
        if (tblVentas.getRowCount() > 0) {
            //
            DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
            // los datos de abajo son variables y constantes y tienen q crearlos respectivamente
            VariablesReporte.vNombreInHashtable = ConstantsReporte.vNombreInHashtableporHora;
            FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable,
                                           ConstantsReporte.vCodCampoporHora, ConstantsReporte.vDescCampoporHora,
                                           true);
            dlgOrdenar.setVisible(true);
            //
            if (FarmaVariables.vAceptar) {
                FarmaUtility.ordenar(tblVentas, tableModelVentas, VariablesReporte.vCampo, VariablesReporte.vOrden);
                tblVentas.repaint();
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
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", txtFechaIni);
            return;
        }

        try {

            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE  DE VENTAS POR HORA", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Días: " + devuelveDiaSemana(VariablesReporte.vCampoFiltro), true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("===================================================================================================================",
                             true);
            vPrint.printBold("Hora                  Pedido         Venta        Venta Promedio         Unidades Promedio         Precio Promedio ",
                             true);
            vPrint.printLine("===================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblVentas.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblVentas.getValueAt(i, 0), 22) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 1), 6) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 2), 14) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 3), 22) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 4), 26) +
                                      FarmaPRNUtility.alinearDerecha((String)tblVentas.getValueAt(i, 5), 24),

                        true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("===================================================================================================================",
                             true);
            vPrint.printBold("Registros: " +
                             FarmaPRNUtility.alinearIzquierda(FarmaUtility.formatNumber(tblVentas.getRowCount(),
                                                                                        ",##0"), 11) +
                             FarmaPRNUtility.alinearDerecha(lblTotPed.getText(), 6) +
                             FarmaPRNUtility.alinearDerecha(lblTotVenta.getText(), 14) +
                             FarmaPRNUtility.alinearDerecha(lblTotPedProm.getText(), 22) +
                             FarmaPRNUtility.alinearDerecha(lblTotUnid.getText(), 26) +
                             FarmaPRNUtility.alinearDerecha(lblTotProm.getText(), 24), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), null);
        }

    }

    private String devuelveDiaSemana(String num) {
        String dia = "";
        if (num.equals("") || num.equals("%"))
            dia = "";
        else {
            int val = Integer.parseInt(num);
            val = val - 2;
            if (val < 0)
                val = 6;
            dia = ConstantsReporte.vDescDiaSemana[val];
        }
        return dia;
    }
}
