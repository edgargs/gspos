package mifarma.ptoventa.recepcionCiega;


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
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDiferenciasFinales extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgDiferenciasFinales.class);

    Frame myParentFrame;
    FarmaTableModel tableModelFaltantes;
    FarmaTableModel tableModelSobrantes;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionProductosFaltantes = new JButtonLabel();
    private JScrollPane srcListaProductosFaltantes = new JScrollPane();
    private JTable tblListaProductosFaltantes = new JTable();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JButtonLabel btnRelacionProductosSobrantes = new JButtonLabel();
    private JScrollPane srcListaProductosSobrantes = new JScrollPane();
    private JTable tblListaProductosSobrantes = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDiferenciasFinales() {
        this(null, "", false);
    }

    public DlgDiferenciasFinales(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(790, 400));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Listado de Diferencias");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 765, 25));
        btnRelacionProductosFaltantes.setText("Faltantes");
        btnRelacionProductosFaltantes.setBounds(new Rectangle(10, 5, 335, 15));
        btnRelacionProductosFaltantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductosFaltantes_actionPerformed(e);
            }
        });
        btnRelacionProductosFaltantes.setMnemonic('F');
        srcListaProductosFaltantes.setBounds(new Rectangle(10, 35, 765, 130));
        tblListaProductosFaltantes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductosFaltantes_keyPressed(e);
            }
        });
        pnlTitle2.setBounds(new Rectangle(10, 175, 765, 25));
        btnRelacionProductosSobrantes.setText("Sobrantes");
        btnRelacionProductosSobrantes.setBounds(new Rectangle(10, 5, 335, 15));
        btnRelacionProductosSobrantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductosSobrantes_actionPerformed(e);
            }
        });
        btnRelacionProductosSobrantes.setMnemonic('S');
        srcListaProductosSobrantes.setBounds(new Rectangle(10, 200, 765, 130));
        tblListaProductosSobrantes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductosSobrantes_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(655, 345, 117, 20));
        lblF11.setBounds(new Rectangle(520, 345, 125, 20));
        lblF11.setText("[ F11 ] Imprimir");
        lblF2.setBounds(new Rectangle(185, 345, 195, 20));
        lblF2.setText("[ F1 ] Fuera de Política de Canje");
        lblF1.setBounds(new Rectangle(390, 345, 120, 20));
        lblF1.setText("[ F2 ] Deteriorado");
        lblEsc.setText("[ Esc ] Salir ");
        pnlTitle1.add(btnRelacionProductosFaltantes, null);
        srcListaProductosFaltantes.getViewport().add(tblListaProductosFaltantes, null);
        pnlTitle2.add(btnRelacionProductosSobrantes, null);
        srcListaProductosSobrantes.getViewport().add(tblListaProductosSobrantes, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(srcListaProductosSobrantes, null);
        jContentPane.add(pnlTitle2, null);
        jContentPane.add(srcListaProductosFaltantes, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {

        tblListaProductosFaltantes.getTableHeader().setReorderingAllowed(false);
        tblListaProductosSobrantes.getTableHeader().setResizingAllowed(false);

        tableModelFaltantes =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaProductosFaltantes, ConstantsRecepCiega.defaultcolumnsListaProductosFaltantes,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductosFaltantes, tableModelFaltantes,
                                    ConstantsRecepCiega.columnsListaProductosFaltantes);

        tableModelSobrantes =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaProductosSobrantes, ConstantsRecepCiega.defaultcolumnsListaProductosSobrantes,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductosSobrantes, tableModelSobrantes,
                                    ConstantsRecepCiega.columnsListaProductosSobrantes);

        cargaListaProductos();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //JMIRANDA 02.12.09
        FarmaUtility.moveFocus(this.tblListaProductosFaltantes);
        // FarmaUtility.moveFocus(this.tblListaProductos.getValueAt(0,0));
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblListaProductosFaltantes_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaProductosSobrantes_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionProductosFaltantes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.tblListaProductosFaltantes);
    }

    private void btnRelacionProductosSobrantes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.tblListaProductosSobrantes);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            log.debug("Imprime");
            if (tblListaProductosFaltantes.getRowCount() == 0 && tblListaProductosSobrantes.getRowCount() == 0)
                FarmaUtility.showMessage(this, "No existen datos para imprimir", null);
            else
                imprimir();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            log.debug("Politica de Canje");
            //JMIRANDA 02.02.10
            if (UtilityRecepCiega.indLimiteTransf(VariablesRecepCiega.vNro_Recepcion)) {
                VariablesRecepCiega.vMotivoTransferencia = "F1";
                DlgConteoProdTransferencia vConteoTransferencia =
                    new DlgConteoProdTransferencia(this.myParentFrame, "Lectura Producto-Productos de Política de Canje",
                                                   true);
                vConteoTransferencia.setVisible(true);
                FarmaVariables.vAceptar = false;
            } else {
                FarmaUtility.showMessage(this,
                                         "Ud. no puede realizar transferencias por Política de Canje. " + "\nEl tiempo límite ha concluido.",
                                         tblListaProductosFaltantes);
            }

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            log.debug("Deteriorado");
            //JMIRANDA 02.02.10
            if (UtilityRecepCiega.indLimiteTransf(VariablesRecepCiega.vNro_Recepcion)) {
                VariablesRecepCiega.vMotivoTransferencia = "F2";
                DlgConteoProdTransferencia vConteoTransferencia =
                    new DlgConteoProdTransferencia(this.myParentFrame, "Lectura Producto-Productos Deteriorados",
                                                   true);
                vConteoTransferencia.setVisible(true);
            } else {
                FarmaUtility.showMessage(this,
                                         "Ud. no puede realizar transferencias por Deteriorados. " + "\nEl tiempo límite ha concluido.",
                                         tblListaProductosFaltantes);
            }
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    public void cargaListaProductos() {
        try {
            DBRecepCiega.getListaProductosFaltantes(tableModelFaltantes);
            if (tblListaProductosFaltantes.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaProductosFaltantes, tableModelFaltantes, 1,
                                     FarmaConstants.ORDEN_ASCENDENTE);

            }

            DBRecepCiega.getListaProductosSobrantes(tableModelSobrantes);
            if (tblListaProductosSobrantes.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaProductosSobrantes, tableModelSobrantes, 1,
                                     FarmaConstants.ORDEN_ASCENDENTE);

            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n", null);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void imprimir() {
        if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", null);
            return;
        }

        try {

            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String campoAlt = "________";

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) +
                             " REPORTE  DE DIFERENCIAS ENCONTRADAS EN LA RECEPCIÓN", true);
            vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
            vPrint.printLine("========================================================================================================================",
                             true);
            vPrint.printBold(
                    //"Desc.Producto        Unidad   Laboratorio         Cant.Cont.  Cant.Ent.  Nro.Entrega" ,
                    "Código  Desc.Producto        Unidad   Laboratorio         Cant.Cont.  Cant.Ent.  Nro.Entrega",
                    true);
            vPrint.printLine("=========================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblListaProductosFaltantes.getRowCount(); i++) {

                vPrint.printCondensed( //FarmaPRNUtility.alinearIzquierda((String) tblListaProductosFaltantes.getValueAt(i, 0), 10)+" "+
                        //JMIRANDA 14.01.10
                        FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(tableModelFaltantes.data,
                                                                                             i, 7), 7) + " " +
                        //COD_PROD
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosFaltantes.getValueAt(i, 0), 20) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosFaltantes.getValueAt(i, 1), 8) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosFaltantes.getValueAt(i, 2), 25) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosFaltantes.getValueAt(i, 3), 7) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosFaltantes.getValueAt(i, 4), 7) +
                        " " +
                        //  FarmaPRNUtility.alinearIzquierda((String) tblListaProductosFaltantes.getValueAt(i,6), 10)+" "+ //diferencia
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosFaltantes.getValueAt(i, 5), 12),
                        true);
            }

            for (int i = 0; i < tblListaProductosSobrantes.getRowCount(); i++) {

                vPrint.printCondensed( //FarmaPRNUtility.alinearIzquierda((String) tblListaProductosSobrantes.getValueAt(i, 0), 10)+" "+
                        //JMIRANDA 14.01.10
                        FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(tableModelSobrantes.data,
                                                                                             i, 7), 7) + " " +
                        //COD_PROD
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosSobrantes.getValueAt(i, 0), 20) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosSobrantes.getValueAt(i, 1), 8) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosSobrantes.getValueAt(i, 2), 25) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosSobrantes.getValueAt(i, 3), 7) +
                        " " +
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosSobrantes.getValueAt(i, 4), 7) +
                        " " +
                        //          FarmaPRNUtility.alinearIzquierda((String) tblListaProductosSobrantes.getValueAt(i,6), 10)+" "+diferencia
                        FarmaPRNUtility.alinearIzquierda((String)tblListaProductosSobrantes.getValueAt(i, 5), 12),
                        true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("==========================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblListaProductosSobrantes.getRowCount() +
                                                                                      tblListaProductosFaltantes.getRowCount(),
                                                                                      ",##0"), 10), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
            FarmaUtility.showMessage(this, "Se imprimió correctamente las diferencias \n", null);
        } catch (SQLException ex) {
            log.error("", ex);
            FarmaUtility.showMessage(this, "Ocurrió un error al imprimir : \n" +
                    ex.getMessage(), null);
        }
    }


}
