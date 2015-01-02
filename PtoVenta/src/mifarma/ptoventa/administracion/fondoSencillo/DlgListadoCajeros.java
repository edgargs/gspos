package mifarma.ptoventa.administracion.fondoSencillo;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.fondoSencillo.reference.ConstantsFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.DBFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.reference.VariablesFondoSencillo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListadoCajeros extends JDialog {
    //Declarando Variables Globales
    Frame myParentFrame;
    FarmaTableModel tableModelCajeros;
    private static final Logger log = LoggerFactory.getLogger(DlgListadoCajeros.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnlHead = new JPanelHeader();
    private JButtonLabel btnCajero = new JButtonLabel();
    private JTextFieldSanSerif txtCajero = new JTextFieldSanSerif();
    private JPanelTitle pnlListado = new JPanelTitle();
    private JScrollPane scrListaCajero = new JScrollPane();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabelFunction btnEnter = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JTable tblCajeros = new JTable();
    private JLabelFunction btnSalir = new JLabelFunction();

    private boolean indRecienCargado = true;

    //CONSTANTES TABLA
    private static final int ColNombres = 0;
    private static final int ColEstado = 1;
    private static final int ColMonto = 2;
    private static final int ColCaja = 3;
    private static final int ColTurno = 4;
    private static final int ColFecha = 5;
    private static final int ColAdmLocal = 6;
    private static final int ColSecUsuCajero = 7;
    private static final int ColEst = 8;

    private static final int ColSecUsuCaje = 7;
    private static final int ColNombresCaje = 8;
    private static final int ColApePaternoCaje = 9;
    private static final int ColApeMaternoCaje = 10;
    private static final int ColLoginUsuCaje = 11;

    public DlgListadoCajeros() {
        this(null, "", false);
    }

    public DlgListadoCajeros(Frame parent, String title, boolean modal) {
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
        //***Valores Básicos Pantalla ***
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        //***fin ***
        this.setSize(new Dimension(759, 336));
        this.getContentPane().setLayout(borderLayout1);
        pnlHead.add(btnCajero, null);
        pnlHead.add(txtCajero, null);
        jContentPane.add(btnSalir, null);
        jContentPane.add(btnEnter, null);
        jContentPane.add(lblF12, null);
        scrListaCajero.getViewport().add(tblCajeros, null);
        jContentPane.add(scrListaCajero, null);
        pnlListado.add(btnListado, null);
        jContentPane.add(pnlListado, null);
        jContentPane.add(pnlHead, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Fondo de Sencillo - Listado de Cajeros");
        pnlHead.setBounds(new Rectangle(5, 5, 740, 40));
        btnCajero.setText("Cajero:");
        btnCajero.setMnemonic('C');
        btnCajero.setBounds(new Rectangle(15, 10, 55, 20));
        btnCajero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCajero_actionPerformed(e);
            }
        });
        txtCajero.setBounds(new Rectangle(70, 10, 250, 20));
        txtCajero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCajero_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCajero_keyReleased(e);
            }
        });
        pnlListado.setBounds(new Rectangle(5, 45, 740, 20));
        scrListaCajero.setBounds(new Rectangle(5, 65, 740, 215));
        btnListado.setText("Lista de Cajeros");
        btnListado.setBounds(new Rectangle(15, 0, 120, 20));
        btnEnter.setBounds(new Rectangle(5, 285, 135, 20));
        lblF12.setBounds(new Rectangle(145, 285, 135, 20));
        btnEnter.setText("[ Enter ] Asignar");
        lblF12.setText("[ F12 ]  Ver Histórico");
        btnSalir.setBounds(new Rectangle(625, 285, 117, 20));
        btnSalir.setText("[ Esc ] Salir");

    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableListaCajeros();
        indRecienCargado = false;
        //permite que no se muevan las columnas de Jtable
        tblCajeros.getTableHeader().setReorderingAllowed(false);
        //permite que no se cambien el tamaño de la columna del Jtable
        tblCajeros.getTableHeader().setResizingAllowed(false);
        setJTable(tblCajeros, txtCajero);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtCajero);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCajero);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            cerrarVentana(false);
            //} else if (e.getKeyCode() == KeyEvent.VK_ENTER){
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            e.consume();
            funcionEnter();
        }
        //else if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)){
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            funcionF12();
        }
        //funcionF12
    }

    private void setJTable(JTable pJTable, JTextFieldSanSerif pText) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtCajero, 0);
        }
        FarmaUtility.moveFocus(pText);
    }

    private void initTableListaCajeros() {
        tableModelCajeros =
                new FarmaTableModel(ConstantsFondoSencillo.columnsListaCajerosInicial, ConstantsFondoSencillo.defaultValuesListaCajerosInicial,
                                    0);
        FarmaUtility.initSimpleList(tblCajeros, tableModelCajeros, ConstantsFondoSencillo.columnsListaCajerosInicial);
        cargaListaCajeros();
    }

    private void cargaListaCajeros() {

        try {
            DBFondoSencillo.getListaCajerosPrincipal(tableModelCajeros);

            if (!indRecienCargado) {
                if (!tablaTieneRegistro(tblCajeros)) {
                    FarmaUtility.showMessage(this, "No existen registros.", txtCajero);
                }
            }
            if (tblCajeros.getRowCount() > 0) {
                FarmaUtility.moveFocus(tblCajeros);
                //tblSobres.setSelectionMode(0);
                FarmaGridUtils.showCell(tblCajeros, 0, 0);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de Cajeros : \n", txtCajero);
        }
    }

    private boolean tablaTieneRegistro(JTable tbl) {
        boolean flag = true;
        if (tbl.getRowCount() <= 0)
            flag = false;

        return flag;
    }

    private void txtCajero_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCajeros, txtCajero, 0);
        FarmaGridUtils.buscarDescripcion(e, tblCajeros, txtCajero, 0);
        chkKeyPressed(e);
    }

    private void txtCajero_keyReleased(KeyEvent e) {
        // chkKeyPressed(e);
    }

    private void funcionEnter() {
        if (tablaTieneRegistro(tblCajeros)) {
            int vFila = tblCajeros.getSelectedRow();
            log.debug("vFila: " + vFila);

            getDatosCajeros(vFila);

            mostrarHistorico();
            if (FarmaVariables.vAceptar) {
                cargaListaCajeros();
                tblCajeros.repaint();
                setJTable(tblCajeros, txtCajero);
            }
        } else {
            FarmaUtility.showMessage(this, "No existen registros.", txtCajero);
        }
    }

    private void mostrarHistorico() {
        DlgHistoricoFondoSencillo dlgHistorico = new DlgHistoricoFondoSencillo(myParentFrame, "", true);
        dlgHistorico.setVisible(true);
    }

    private void getDatosCajeros(int vFila) {
        VariablesFondoSencillo.vCajNombre =
                FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, ColNombres).trim();
        VariablesFondoSencillo.vCajSecUsuCajero =
                FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, ColSecUsuCajero).trim();
        log.error("VariablesFondoSencillo.vCajNombre: " + VariablesFondoSencillo.vCajNombre);
        log.error("VariablesFondoSencillo.vCajSecUsuCajero: " + VariablesFondoSencillo.vCajSecUsuCajero);
    }

    private void btnCajero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCajero);
    }

    /**
     *
     */
    private void funcionF12() {
        if (UtilityFondoSencillo.validaUsuAdmLocal(this, FarmaVariables.vNuSecUsu, tblCajeros)) {
            asignarFondo();
        } else {
            FarmaUtility.showMessage(this, "Ud. no tiene rol Administrador", tblCajeros);
        }
    }

    private void asignarFondo() {
        if (tblCajeros.getRowCount() <= 0)
            return;
        int vFila = tblCajeros.getSelectedRow();
        log.debug("vFila: " + vFila);
        if (vFila != -1) {
            String estado = FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, 12);

            log.debug("estado:" + estado + "-" + estado.trim().length());
            if (estado.trim().length() == 0) {

                VariablesFondoSencillo.vSecUsuCajero =
                        FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, ColSecUsuCaje).trim();
                VariablesFondoSencillo.vNombresCajero =
                        FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, ColNombresCaje).trim();
                VariablesFondoSencillo.vApePaterno =
                        FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, ColApePaternoCaje).trim();
                VariablesFondoSencillo.vApeMaterno =
                        FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, ColApeMaternoCaje).trim();
                VariablesFondoSencillo.vLoginUsuCaje =
                        FarmaUtility.getValueFieldArrayList(tableModelCajeros.data, vFila, ColLoginUsuCaje).trim();

                log.debug("Var.vSecUsuCajero:" + VariablesFondoSencillo.vSecUsuCajero);
                log.debug("Var.vNombresCajero:" + VariablesFondoSencillo.vNombresCajero);
                log.debug("Var.vApePaterno:" + VariablesFondoSencillo.vApePaterno);
                log.debug("Var.vApeMaterno:" + VariablesFondoSencillo.vApeMaterno);
                log.debug("Var.vLoginUsuCaje:" + VariablesFondoSencillo.vLoginUsuCaje);

                //JMIRANDA 04.05.2010 valida que no tenga asignado monto
                DlgAsignarMontoFondoSencillo dlgAsignarMonto =
                    new DlgAsignarMontoFondoSencillo(myParentFrame, "", true);
                dlgAsignarMonto.setVisible(true);
                cargaListaCajeros();
                VariablesFondoSencillo.vSecUsuCajero = "";
                VariablesFondoSencillo.vNombresCajero = "";
                VariablesFondoSencillo.vApePaterno = "";
                VariablesFondoSencillo.vApeMaterno = "";
                VariablesFondoSencillo.vLoginUsuCaje = "";
            } else {
                FarmaUtility.showMessage(this, "No se le puede asignar monto hasta que Cierre el turno de la caja.",
                                         txtCajero);
            }
        } else {
            FarmaUtility.showMessage(this, "No ha seleccionado ningún cajero.", txtCajero);
        }
    }
}
