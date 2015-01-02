package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
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
 * Nombre de la Aplicación : DlgProductoFaltaCero.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      06.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgProductoFaltaCero extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgProductoFaltaCero.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JScrollPane scrProductos = new JScrollPane();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JTable tblProductos = new JTable();
    private JLabelFunction lblF2 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgProductoFaltaCero() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgProductoFaltaCero(Frame parent, String title, boolean modal) {
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
        this.setTitle("Reporte Productos Falta Cero");
        this.setSize(new Dimension(664, 418));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(550, 365, 100, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF10.setBounds(new Rectangle(430, 365, 115, 20));
        lblF10.setText("[F10] Imprimir");
        lblF10.setVisible(false);
        scrProductos.setBounds(new Rectangle(10, 90, 640, 270));
        pnlTitulo.setBounds(new Rectangle(10, 70, 640, 20));
        btnListado.setText("Listado de Falta Cero");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 640, 55));
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
        txtFechaFin.setBounds(new Rectangle(310, 15, 101, 19));
        txtFechaFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaIni.setBounds(new Rectangle(185, 15, 101, 19));
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaIni.setDocument(new FarmaLengthText(10));
        btnRandoFec.setText("Rango de Fechas");
        btnRandoFec.setBounds(new Rectangle(75, 15, 100, 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRandoFec_actionPerformed(e);
            }
        });
        lblF2.setBounds(new Rectangle(10, 365, 140, 20));
        lblF2.setText("[F2] Ver Detalle");
        scrProductos.getViewport();
        pnlTitulo.add(btnListado, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnRandoFec, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.add(pnlTitulo, null);
        scrProductos.getViewport().add(tblProductos, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(lblF10, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //
        txtFechaIni.setLengthText(10);
        txtFechaFin.setLengthText(10);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        VariablesReporte.vInd_Filtro = FarmaConstants.INDICADOR_N;
        initTableListaFaltaCero();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaFaltaCero() {
        tableModel =
                new FarmaTableModel(ConstantsReporte.columnsListaFaltaCero, ConstantsReporte.defaultValuesListaFaltaCero,
                                    0);
        FarmaUtility.initSimpleList(tblProductos, tableModel, ConstantsReporte.columnsListaFaltaCero);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
        FarmaUtility.moveFocus(txtFechaIni);
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

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else
            chkKeyPressed(e);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void btnRandoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            funcionF2();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F10(e)) {
            // Se desabilito la opcion de imprimir.
            //  conversado segun correo 15.09.2008 dubilluz
            /*funcionF10();
       * */
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

    private void busqueda() {
        if (validarCampos()) {
            VariablesReporte.vCampoFiltro = "";
            buscaRegistros(txtFechaIni.getText().trim(), txtFechaFin.getText().trim());
            FarmaUtility.moveFocus(btnBuscar);
        }
    }

    private void buscaRegistros(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistros();
    }

    private void cargaRegistros() {
        try {
            tableModel.clearTable();
            if (VariablesReporte.vCampoFiltro.equals(""))
                VariablesReporte.vCampoFiltro = "%";

            DBReportes.cargaListaFaltaCero(tableModel, VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin,
                                           VariablesReporte.vCampoFiltro);

            if (tblProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblProductos, tableModel, 5, FarmaConstants.ORDEN_ASCENDENTE);
            else
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar la lista del registro de ventas : \n" +
                    sql.getMessage(), txtFechaIni);
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

    private void funcionF2() {
        if (tblProductos.getRowCount() > 0) {
            VariablesReporte.vCodProd = tblProductos.getValueAt(tblProductos.getSelectedRow(), 0).toString();
            DlgDetalleFaltaCero dlgDetalleFaltaCero = new DlgDetalleFaltaCero(myParentFrame, "", true);
            dlgDetalleFaltaCero.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaRegistros();
            }
        } else
            FarmaUtility.showMessage(this, "No existen registros para la busqueda realizada", txtFechaIni);
    }

    private void funcionF10() {
        if (tblProductos.getRowCount() > 0)
            imprimir();
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;

        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        //"C:\\ImpresoraReporte.txt", true);
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
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(27) + " REPORTE DE PRODUCTOS FALTA CERO", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
            vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("===================================================================================================================",
                             true);
            vPrint.printBold("Codigo    Descripcion                   Unidad          Laboratorio              Cantidad",
                             true);
            vPrint.printLine("===================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblProductos.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblProductos.getValueAt(i, 0), 10) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblProductos.getValueAt(i, 1), 30) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblProductos.getValueAt(i, 2), 16) +
                                      FarmaPRNUtility.alinearIzquierda((String)tblProductos.getValueAt(i, 3), 25) +
                                      FarmaPRNUtility.alinearDerecha((String)tblProductos.getValueAt(i, 4), 7), true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("===================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    sql.getMessage(), null);
        }

    }
}
