package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
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
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgActaRecepcionMercaderia extends JDialog {

    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */

    private static final Logger log = LoggerFactory.getLogger(DlgActaRecepcionMercaderia.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    String cCodRecepcion, cNumEntrega, cFecha;


    private JPanelHeader pnlHeaderListaCajas = new JPanelHeader();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblNumRecep = new JLabelWhite();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JLabelWhite lblNumRecep2 = new JLabelWhite();
    private JLabelWhite jLabelWhite3 = new JLabelWhite();
    private JLabelWhite lblNumRecep3 = new JLabelWhite();
    private JLabelWhite jLabelWhite4 = new JLabelWhite();
    private JLabelWhite lblNumRecep4 = new JLabelWhite();


    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane scrListaCajas = new JScrollPane();
    private JTable tblListaCajas = new JTable();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */

    public DlgActaRecepcionMercaderia() {
        this(null, "", false);
    }

    public DlgActaRecepcionMercaderia(Frame parent, String title, boolean modal) {
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
    /*                              METODO jbInit                               */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(964, 765));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Acta de Recepción de Productos");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        pnlHeaderListaCajas.setBounds(new Rectangle(10, 10, 555, 60));

        jLabelWhite1.setText("Nro Recepción :");
        jLabelWhite1.setBounds(new Rectangle(10, 5, 85, 20));
        lblNumRecep.setText("000000");
        lblNumRecep.setBounds(new Rectangle(100, 5, 95, 20));
        lblNumRecep.setFont(new Font("SansSerif", 0, 11));

        jLabelWhite3.setText("Nro Entrega :");
        jLabelWhite3.setBounds(new Rectangle(10, 30, 85, 20));
        lblNumRecep3.setText("000000");
        lblNumRecep3.setBounds(new Rectangle(100, 30, 95, 20));
        lblNumRecep3.setFont(new Font("SansSerif", 0, 11));

        jLabelWhite2.setText("Cantidad Items :");
        jLabelWhite2.setBounds(new Rectangle(275, 10, 105, 20));
        lblNumRecep2.setBounds(new Rectangle(380, 10, 95, 20));
        lblNumRecep2.setFont(new Font("SansSerif", 0, 11));

        jLabelWhite4.setText("Fecha :");
        jLabelWhite4.setBounds(new Rectangle(275, 30, 105, 20));
        lblNumRecep4.setBounds(new Rectangle(355, 30, 95, 20));
        lblNumRecep4.setFont(new Font("SansSerif", 0, 11));

        jContentPane.setLayout(null);
        scrListaCajas.setBounds(new Rectangle(10, 80, 930, 595));
        scrListaCajas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                scrListaCajas_keyPressed(e);
            }
        });
        tblListaCajas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaCajas_keyPressed(e);
            }
        });
        lblSalir.setBounds(new Rectangle(770, 700, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        scrListaCajas.getViewport().add(tblListaCajas, null);
        lblF12.setBounds(new Rectangle(620, 700, 110, 20));
        lblF12.setText("[ F12 ] Imprimir");
        lblF12.setFocusable(false);

        pnlHeaderListaCajas.add(jLabelWhite1, null);
        pnlHeaderListaCajas.add(lblNumRecep, null);
        pnlHeaderListaCajas.add(jLabelWhite2, null);
        pnlHeaderListaCajas.add(lblNumRecep2, null);
        pnlHeaderListaCajas.add(jLabelWhite3, null);
        pnlHeaderListaCajas.add(lblNumRecep3, null);
        pnlHeaderListaCajas.add(jLabelWhite4, null);
        pnlHeaderListaCajas.add(lblNumRecep4, null);
        jContentPane.add(pnlHeaderListaCajas, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(scrListaCajas, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaActaRecepcion, ConstantsRecepCiega.defaultListaActaRecepcion,
                                    0);
        FarmaUtility.initSimpleList(tblListaCajas, tableModel, ConstantsRecepCiega.columnsListaActaRecepcion);
        //cargarLista();
    }

    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        lblNumRecep.setText(cCodRecepcion);
        lblNumRecep2.setText("" + tblListaCajas.getRowCount() + "");
        lblNumRecep3.setText(cNumEntrega);
        lblNumRecep4.setText(cFecha);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaCajas);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void scrListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F12(e)) {
            if (tblListaCajas.getRowCount() <= 0)
                FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de imprmir", null);
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
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */

    void setCodRecepcion(String codigo, String vNumEntrega, String vFecha) {
        cCodRecepcion = codigo;
        cNumEntrega = vNumEntrega;
        cFecha = vFecha;
    }

    public void cargarLista() {

        try {
            DBRecepCiega.getListActaRecepcionMercaderia(tableModel, cCodRecepcion, cNumEntrega);

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar Acta de Recepción de Productos : \n" +
                    e.getMessage(), null);
        }

    }

    private void imprimir() {
        if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
            return;

        //FarmaPrintService vPrint = new FarmaPrintService(45,
        FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        log.debug(FarmaVariables.vImprReporte);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, "No se pudo inicializar el proceso de impresión", null);
            return;
        }

        try {

            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);

            vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " ACTA DE RECEPCION DE PRODUCTOS", true);
            vPrint.printBold("Nombre Compañia: " + VariablesPtoVenta.vNombreMarcaCia, true);
            vPrint.printBold("Numero Recepción: " + cCodRecepcion, true);
            vPrint.printBold("Número de entrega: " + cNumEntrega, true);
            vPrint.printBold("Fecha: " + fechaActual, true);
            vPrint.printBold("Fecha de la recepción: " + cFecha, true);
            vPrint.printBold("Local: " + FarmaVariables.vCodLocal, true);
            vPrint.printLine("========================================================================================================================",
                             true);
            /* vPrint
                                          .printBold(
                                                          "NroPedido   Tipo  NroComprobante  Fecha                Cliente          Estado             Monto   Ruc       ",
                                                          true); */
            vPrint.printBold("CODIGO DESCRIPCION                    PRESENTACION CANT  N LOTE   FECH VCT EMBALAJE ENV MDTO  ENV INMDTO " +
                             "ROTULO  CONTENIDO R.S. ", true);
            vPrint.printLine("=========================================================================================================================",
                             true);
            vPrint.deactivateCondensed();
            vPrint.setEndHeader();
            for (int i = 0; i < tblListaCajas.getRowCount(); i++) {

                vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 0), 6) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 1), 30) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 2), 12) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 3), 5) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 4), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 5), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 6), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 7), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 8), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 9), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 10), 8) +
                                      " " +
                                      FarmaPRNUtility.alinearIzquierda((String)tblListaCajas.getValueAt(i, 11), 8),
                                      true);
            }

            vPrint.activateCondensed();
            vPrint.printLine("==========================================================================================================================",
                             true);
            vPrint.printBold("Registros Impresos: " +
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblListaCajas.getRowCount(),
                                                                                      ",##0"), 10) +
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
            FarmaUtility.showMessage(this, "Reporte impreso, recoja el documento.", null);
        } catch (SQLException ex) {
            log.error("", ex);
            //showMessage(this,"Ocurrió un error al imprimir : \n"+ex.getMessage(),null);
        }
    }

}
