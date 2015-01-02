package mifarma.ptoventa.administracion.impresoras;


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
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.ConstantsImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.VariablesImpresoras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaImpresoraMaquina extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaImpresoraMaquina.class);
    private String tipoComp = "";
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlHeaderRoles = new JPanelTitle();
    private JScrollPane scrListaRoles = new JScrollPane();
    private JTable tblLista = new JTable();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JButtonLabel btnListaRoles = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaImpresoraMaquina() {
        this(null, "", false);
    }

    public DlgListaImpresoraMaquina(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(543, 267));
        this.getContentPane().setLayout(borderLayout1);

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlHeaderRoles.setBounds(new Rectangle(5, 10, 525, 25));
        scrListaRoles.setBounds(new Rectangle(5, 35, 525, 175));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaRoles_keyPressed(e);
            }
        });
        lblAceptar.setBounds(new Rectangle(355, 215, 90, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblSalir.setBounds(new Rectangle(455, 215, 75, 20));
        lblSalir.setText("[ESC] Cerrar");
        btnListaRoles.setText("Lista Impresoras :");
        btnListaRoles.setBounds(new Rectangle(5, 0, 140, 25));
        btnListaRoles.setMnemonic('l');
        btnListaRoles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaRoles_actionPerformed(e);
            }
        });
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblAceptar, null);
        scrListaRoles.getViewport().add(tblLista, null);
        jContentPane.add(scrListaRoles, null);
        pnlHeaderRoles.add(btnListaRoles, null);
        jContentPane.add(pnlHeaderRoles, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        marcarRoles();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsImpresoras.columnsListaImpr, ConstantsImpresoras.defaultValuesListaImpr,
                                    0);
        FarmaUtility.initSimpleList(tblLista, tableModel, ConstantsImpresoras.columnsListaImpr);
    }

    public void setValores(String tipoComp) {
        this.tipoComp = tipoComp;
    }
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        cargaLista(this.tipoComp);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblLista);
    }

    private void tblListaRoles_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Esta seguro de asignar la IP a la impresora seleccionada?")) {
                actualizarlistaRolesSeleccion();
            }
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            FarmaVariables.vAceptar = false;
            this.setVisible(false);
            this.dispose();
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

    private void cargaLista(String tipoComp) {
        try {
            if ("B".equalsIgnoreCase(tipoComp))
                this.setTitle("Impresoras Boletas");
            else if ("F".equalsIgnoreCase(tipoComp))
                this.setTitle("Impresoras Facturas");
            else
                this.setTitle("Impresoras");

            DBImpresoras.getListaImpr(tableModel, VariablesImpresoras.vSecIP, tipoComp);

            if (tblLista.getRowCount() > 0) {
                FarmaUtility.ordenar(tblLista, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocus(tblLista);
            }
            log.debug("se cargo la lista");
        } catch (SQLException ex) {
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al listar. \n " + ex.getMessage(), tblLista);
        }
    }

    private void actualizarlistaRolesSeleccion() {
        VariablesImpresoras.vSecImpr = tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString().trim();
        VariablesImpresoras.vNumSerie = tblLista.getValueAt(tblLista.getSelectedRow(), 1).toString().trim();
        VariablesImpresoras.vTipoComp = tblLista.getValueAt(tblLista.getSelectedRow(), 5).toString().trim();

        try {
            DBImpresoras.actualizaRelacion(VariablesImpresoras.vSecIP, VariablesImpresoras.vSecImpr,
                                           VariablesImpresoras.vNumSerie, VariablesImpresoras.vTipoComp,
                                           this.tipoComp);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Asignación Exitosa.", tblLista);
            cerrarVentana(true);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error guardar la IP.\n" +
                    sql.getMessage(), tblLista);
        }
    }

    private void eliminarAsignacionesUsuario() throws SQLException {

        //DBUsuarios.limpiaAsignacionUsuario(VariablesUsuarios.vSecUsuLocal);

    }

    private void insertarAsignaciones() throws SQLException {
        /*String[] tmp2 = new String[2];
        for (int i2 = 0; i2 < VariablesUsuarios.listaRoles.size(); i2++) {
            tmp2 = (String[])VariablesUsuarios.listaRoles.get(i2);
            DBUsuarios.agregaAsignacionUsuario(VariablesUsuarios.vSecUsuLocal,
                    tmp2[0]);
        }*/
    }

    private void marcarRoles() {
        /* String[] tmp2 = new String[2];
        ArrayList myArray;
        ArrayList myArray2;
        String tmp = "";

        if (VariablesUsuarios.listaRoles.size() > 0) {
            myArray = new ArrayList();
            myArray2 = new ArrayList();

            for (int i = 0; i < VariablesUsuarios.listaRoles.size(); i++) {
                myArray2 = new ArrayList();
                tmp2 = (String[])VariablesUsuarios.listaRoles.get(i);
                tmp = tmp2[0];
                myArray2.add(tmp);
                myArray.add(myArray2);

            }

            FarmaUtility.ponerCheckJTable(tblLista, 1, myArray, 0);

        }
*/
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnListaRoles_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

}
