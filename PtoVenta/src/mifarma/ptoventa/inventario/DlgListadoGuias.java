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

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListadoGuias extends JDialog {


    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListadoGuias.class);
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private String filtro;
    private String CodOrigenTranf;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaTransferencias = new JScrollPane();
    private JTable tblListaTransferencias = new JTable();
    private JButtonLabel btnRelacionTransferencias = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JButtonLabel lblCantTransfereniasT = new JButtonLabel();
    private JButtonLabel lblCantTransferencias = new JButtonLabel();
    private JLabelFunction lblF10 = new JLabelFunction();


    transient FacadeInventario facadeInventario = new FacadeInventario();
    private Object e;

    private final int COL_NUM_NOTA = 0;
    private final int COL_EST_IMPR = 5;


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListadoGuias() {
        this(null, "", false);
    }

    public DlgListadoGuias(Frame parent, String title, boolean modal) {
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

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(641, 373));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relacion de Guias No Mueven Stock");
        this.setDefaultCloseOperation(0);
        //this.setBounds(new Rectangle(10, 10, 790, 394));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 610, 25));
        scrListaTransferencias.setBounds(new Rectangle(10, 35, 610, 260));
        tblListaTransferencias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaTransferencias_keyPressed(e);
            }
        });
        btnRelacionTransferencias.setText("Relacion de Guias");
        btnRelacionTransferencias.setBounds(new Rectangle(5, 5, 165, 15));
        btnRelacionTransferencias.setMnemonic('R');
        btnRelacionTransferencias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionTransferencias_actionPerformed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(530, 310, 90, 20));

        lblF1.setText("[ F1 ] Nueva Transferencia");
        lblF1.setBounds(new Rectangle(15, 310, 160, 20));
        lblCantTransfereniasT.setBounds(new Rectangle(555, 5, 45, 15));
        lblCantTransferencias.setText("Cantidad de Guias");
        lblCantTransferencias.setBounds(new Rectangle(385, 5, 160, 15));

        lblF10.setText("[ F10 ] Reimprimir");
        lblF10.setBounds(new Rectangle(185, 310, 160, 20));
        scrListaTransferencias.getViewport().add(tblListaTransferencias, null);

        jContentPane.add(lblF10, null);
        jContentPane.add(lblF1, null);

        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaTransferencias, null);
        pnlTitle1.add(lblCantTransferencias, null);
        pnlTitle1.add(lblCantTransfereniasT, null);
        pnlTitle1.add(btnRelacionTransferencias, null);
        jContentPane.add(pnlTitle1, null);
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
        tblListaTransferencias.getTableHeader().setReorderingAllowed(false);
        tblListaTransferencias.getTableHeader().setResizingAllowed(false);
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaGuiasNoMuevenStock, ConstantsInventario.defaultListaGuiasNoMuevenStock,
                                    0);
        FarmaUtility.initSimpleList(tblListaTransferencias, tableModel,
                                    ConstantsInventario.columnsListaGuiasNoMuevenStock);

        cargaListaTransferencias();
    }

    public void cargaListaTransferencias() {
        ArrayList listaGuias = new ArrayList();
        tableModel.clearTable();

        try {
            listaGuias =
                    facadeInventario.listarGuiasNoMuevenStock(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal);
            if (listaGuias != null) {
                tableModel.data = listaGuias;
                if (tableModel.data.size() > 0) {
                    tblListaTransferencias.setRowSelectionInterval(0, 0);
                    FarmaUtility.ordenar(tblListaTransferencias, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
                }
            }
            cantRegistros();
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar las Guias de Remision: \n" +
                    sql.getMessage(), btnRelacionTransferencias);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionTransferencias_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaTransferencias);
    }

    private void tblListaTransferencias_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaTransferencias);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            cargarFormulario();

        }
        if (UtilityPtoVenta.verificaVK_F10(e)) {
            reimprimir();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void cargarFormulario() {
        DlgGuiasSalida dlgGuiaSalida = new DlgGuiasSalida(this.myParentFrame, "", true);
        dlgGuiaSalida.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaTransferencias();
        }
    }

    private void cantRegistros() {
        int cant = tblListaTransferencias.getRowCount();
        String valor = Integer.toString(cant);
        this.lblCantTransfereniasT.setText(valor);
    }

    private void reimprimir() {
        int row = tblListaTransferencias.getSelectedRow();
        if (row >= 0) {
            String estImpr = FarmaUtility.getValueFieldJTable(tblListaTransferencias, row, COL_EST_IMPR);
            if (estImpr.equals("N")) {
                VariablesInventario.vNumNotaEs =
                        FarmaUtility.getValueFieldJTable(tblListaTransferencias, row, COL_NUM_NOTA);
                DlgListaImpresorasInv dlgListaImpresorasInv = new DlgListaImpresorasInv(this.myParentFrame, "", true);
                dlgListaImpresorasInv.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    UtilityInventario.cargaCabecera(this, null, VariablesInventario.vNumNotaEs);
                    UtilityInventario.procesoImpresionGuias(this, tblListaTransferencias,
                                                            VariablesInventario.vTipoFormatoImpresion, true);
                    try {
                        facadeInventario.confirmarDevolucion(VariablesInventario.vNumNotaEs);
                    } catch (Exception f) {
                        log.debug("", f);
                    }
                    cargaListaTransferencias();
                }
            } else {
                FarmaUtility.showMessage(this, "La guia ya fue impresa.", tblListaTransferencias);
            }
        }
    }
}
