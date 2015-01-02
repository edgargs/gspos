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

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
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


public class DlgVentasResumenPorDia extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleVentas.class);

    private FarmaTableModel tableModelVentasPorDia;
    private Frame myParentFrame;

    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel lbllocal = new JButtonLabel();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblVentasPorDIa = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();
    private JLabelFunction jLabelFunction4 = new JLabelFunction();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JLabel lblRegistros1 = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblPedidos = new JLabel();
    private JLabel lblNBoletas = new JLabel();
    private JLabel lblNFacturas = new JLabel();
    private JLabel lblBoletasA = new JLabel();
    private JLabel lblFacturasA = new JLabel();
    private JLabel lblTotal = new JLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgVentasResumenPorDia() {
        this(null, "", false);
    }

    public DlgVentasResumenPorDia(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(782, 448));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Ventas por Dia");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(5, 15, 765, 30));
        pnlCriterioBusqueda.setFocusable(false);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(460, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(330, 5, 101, 19));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(210, 5, 101, 19));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaIni.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtFechaIni_actionPerformed(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(135, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(5, 55, 765, 20));
        pnlTitulo.setFocusable(false);
        lbllocal.setText("xxx");
        lbllocal.setBounds(new Rectangle(455, 0, 270, 20));
        lbllocal.setFocusable(false);
        lbllocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        btnListado.setText("Relacion de Productos Vendidos");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.setFocusable(false);
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(5, 75, 765, 290));
        jScrollPane1.setFocusable(false);
        tblVentasPorDIa.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblVentasPorDIa_keyPressed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(10, 390, 115, 25));
        jLabelFunction1.setText("[ F8 ] Ordenar");
        jLabelFunction1.setFocusable(false);
        jLabelFunction3.setBounds(new Rectangle(655, 390, 115, 25));
        jLabelFunction3.setText("[ ESC ] Salir");
        jLabelFunction3.setFocusable(false);
        jLabelFunction4.setBounds(new Rectangle(530, 390, 115, 25));
        jLabelFunction4.setText("[ F11 ] Imprimir");
        jLabelFunction4.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(5, 365, 765, 20));
        lblRegistros1.setText("0");
        lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
        lblRegistros1.setFont(new Font("SansSerif", 1, 11));
        lblRegistros1.setForeground(Color.white);
        lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(70, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegistros.setFocusable(false);
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(10, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblRegsitros_T.setFocusable(false);
        lblPedidos.setText("0");
        lblPedidos.setBounds(new Rectangle(115, 0, 55, 20));
        lblPedidos.setFont(new Font("SansSerif", 1, 11));
        lblPedidos.setForeground(Color.white);
        lblPedidos.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPedidos.setFocusable(false);
        lblNBoletas.setText("0");
        lblNBoletas.setBounds(new Rectangle(340, 0, 40, 20));
        lblNBoletas.setFont(new Font("SansSerif", 1, 11));
        lblNBoletas.setForeground(Color.white);
        lblNBoletas.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNBoletas.setFocusable(false);
        lblNFacturas.setText("0");
        lblNFacturas.setBounds(new Rectangle(400, 0, 40, 20));
        lblNFacturas.setFont(new Font("SansSerif", 1, 11));
        lblNFacturas.setForeground(Color.white);
        lblNFacturas.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNFacturas.setFocusable(false);
        lblBoletasA.setText("0");
        lblBoletasA.setBounds(new Rectangle(490, 0, 40, 20));
        lblBoletasA.setFont(new Font("SansSerif", 1, 11));
        lblBoletasA.setForeground(Color.white);
        lblBoletasA.setHorizontalAlignment(SwingConstants.RIGHT);
        lblBoletasA.setFocusable(false);
        lblFacturasA.setText("0");
        lblFacturasA.setBounds(new Rectangle(580, 0, 40, 20));
        lblFacturasA.setFont(new Font("SansSerif", 1, 11));
        lblFacturasA.setForeground(Color.white);
        lblFacturasA.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFacturasA.setFocusable(false);
        lblTotal.setText("0");
        lblTotal.setBounds(new Rectangle(650, 0, 80, 20));
        lblTotal.setFont(new Font("SansSerif", 1, 11));
        lblTotal.setForeground(Color.white);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setFocusable(false);
        pnlResultados.add(lblTotal, null);
        pnlResultados.add(lblFacturasA, null);
        pnlResultados.add(lblBoletasA, null);
        pnlResultados.add(lblNFacturas, null);
        pnlResultados.add(lblNBoletas, null);
        pnlResultados.add(lblPedidos, null);
        pnlResultados.add(lblRegistros1, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlTitulo.add(lbllocal, null);
        pnlTitulo.add(btnListado, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        jPanelWhite1.add(pnlResultados, null);
        jPanelWhite1.add(jLabelFunction4, null);
        jPanelWhite1.add(jLabelFunction3, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jScrollPane1.getViewport().add(tblVentasPorDIa, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlTitulo, null);
        jPanelWhite1.add(pnlCriterioBusqueda, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaVentasVendedor();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaVentasVendedor() {
        tableModelVentasPorDia =
                new FarmaTableModel(ConstantsReporte.columnsListaVentasPorDia, ConstantsReporte.defaultValuesListaVentasPorDia,
                                    0);
        FarmaUtility.initSimpleList(tblVentasPorDIa, tableModelVentasPorDia,
                                    ConstantsReporte.columnsListaVentasPorDia);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        }
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        lbllocal.setText(FarmaVariables.vDescLocal);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (tblVentasPorDIa.getRowCount() == 0 || tblVentasPorDIa.getRowCount() > 0) {
            busqueda();
        }
    }

    private void tblVentasPorDIa_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblVentasPorDIa);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            e.consume();
            if (tblVentasPorDIa.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
            else
                muestraVentaPorDiaOrdenar();
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (tblVentasPorDIa.getRowCount() <= 0)
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

    void muestraVentaPorDiaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_DESCRIP_CAMPO =
        { "Fecha", "No Pedidos", "Bol.Ini", "Fac.Ini", "No.Bol", "No.Fac", "No.Bol.Anul", "No.Fac.Anul", "Total" };
        String[] IND_CAMPO = { "9", "1", "2", "3", "4", "5", "6", "7", "8" };
        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLERESUMENPORDIA;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblVentasPorDIa, tableModelVentasPorDia, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            //tblVentasPorDIa.repaint();
        }
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
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE DE VENTAS POR DIA", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("======================================================================================================",
                             true);
            vPrint.printBold("Fecha      No.Ped. Bol.Inicial Fac.Inicial Bol.  Fact. Tick. TF.   Bol.An Fac.An Tic.An TF.An   Total ",
                             true);
            vPrint.printLine("======================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();

            for (int i = 0; i < tblVentasPorDIa.getRowCount(); i++) {
                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblVentasPorDIa.getValueAt(i, 0), 10) +
                                      //Fecha
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 1), 6) + " " + //Cant Ped
                        FarmaPRNUtility.alinearIzquierda((String)tblVentasPorDIa.getValueAt(i, 2), 13) + //Bol.Inicial
                        FarmaPRNUtility.alinearIzquierda((String)tblVentasPorDIa.getValueAt(i, 3), 12) + //Fact.Inicial
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 5), 6) + //Boletas
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 6), 6) + //Facturas
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 7), 6) + //Tickets
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 8), 6) +
                        //Tickets Facturas
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 9), 7) +
                        //Boletas Anuladas
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 10), 7) +
                        //Facturas Anuladas
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 11), 7) +
                        //Tickets Anuladas
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 12), 6) +
                        //Tickets Facturas Anuladas
                        FarmaPRNUtility.alinearDerecha((String)tblVentasPorDIa.getValueAt(i, 13), 9), true); //Total
            }

            vPrint.activateCondensed();
            vPrint.printLine("======================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(tblVentasPorDIa.getRowCount(), ",##0"),
                             false);
            //vPrint.printBold(FarmaPRNUtility.llenarBlancos(40)+ lblTotal, true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
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
                    buscaRegistroVentasporDia(FechaInicio, FechaFin);
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

    private void buscaRegistroVentasporDia(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaVentasporDia();
    }

    private void cargaVentasporDia() {
        try {
            DBReportes.cargaListaVentasPorDia(tableModelVentasPorDia, VariablesReporte.vFechaInicio,
                                              VariablesReporte.vFechaFin);
            if (tblVentasPorDIa.getRowCount() <= 0) {
                lblRegistros.setText("0");
                lblTotal.setText("0.00");
                lblPedidos.setText("0.00");
                lblNBoletas.setText("0.00");
                lblNFacturas.setText("0.00");
                lblBoletasA.setText("0.00");
                lblFacturasA.setText("0.00");
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
            } else {
                FarmaUtility.ordenar(tblVentasPorDIa, tableModelVentasPorDia, 2, FarmaConstants.ORDEN_ASCENDENTE);
                lblRegistros.setText("" + tblVentasPorDIa.getRowCount());
                lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblVentasPorDIa, 8)));
                lblPedidos.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblVentasPorDIa, 1), "#####"));
                lblNBoletas.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblVentasPorDIa, 4),
                                                              "#####"));
                lblNFacturas.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblVentasPorDIa, 5),
                                                               "######"));
                lblBoletasA.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblVentasPorDIa, 6),
                                                              "######"));
                lblFacturasA.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblVentasPorDIa, 7),
                                                               "######"));
                FarmaUtility.moveFocusJTable(tblVentasPorDIa);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar Ventas por Dia : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void txtFechaIni_actionPerformed(ActionEvent e) {
    }
}
