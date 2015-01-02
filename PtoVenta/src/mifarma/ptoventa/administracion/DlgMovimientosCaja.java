package mifarma.ptoventa.administracion;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.reference.VariablesAdministracion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMovimientosCaja extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMovimientosCaja.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite contentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlDiaVenta = new JPanelTitle();
    private JPanelTitle pnlHeaderRelacionMov = new JPanelTitle();
    private JScrollPane scrListaMovimientos = new JScrollPane();
    private JTable tblListaMovimientos = new JTable();
    private JTextFieldSanSerif txtDiaVenta = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabelWhite lblUsuario_T = new JLabelWhite();
    private JLabelWhite lblUsuario = new JLabelWhite();
    private JLabelFunction lblDetalleMov = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JButtonLabel btnDiaVenta = new JButtonLabel();
    private JButtonLabel btnRelacionMovimientos = new JButtonLabel();

    //**************************************************************************
    //Constructores
    //**************************************************************************

    public DlgMovimientosCaja() {
        this(null, "", false);
    }

    public DlgMovimientosCaja(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    //**************************************************************************
    //Método "jbInit()"
    //**************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(735, 332));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Movimientos de Caja");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        contentPane.setLayout(null);
        pnlDiaVenta.setBounds(new Rectangle(10, 10, 710, 50));
        pnlHeaderRelacionMov.setBounds(new Rectangle(10, 70, 710, 30));
        scrListaMovimientos.setBounds(new Rectangle(10, 100, 710, 165));
        tblListaMovimientos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaMovimientos_keyPressed(e);
            }
        });
        txtDiaVenta.setBounds(new Rectangle(105, 10, 100, 20));
        txtDiaVenta.setLengthText(10);
        txtDiaVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDiaVenta_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDiaVenta_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(210, 10, 85, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        lblUsuario_T.setText("Usuario:");
        lblUsuario_T.setBounds(new Rectangle(315, 10, 45, 20));
        lblUsuario.setText("VICTOR REYES");
        lblUsuario.setBounds(new Rectangle(375, 10, 225, 20));
        lblDetalleMov.setBounds(new Rectangle(260, 275, 175, 20));
        lblDetalleMov.setText("[F2] Detalle del Movimiento");
        lblDetalleMov.setFont(new Font("SansSerif", 1, 11));
        lblSalir.setBounds(new Rectangle(525, 275, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        lblSalir.setFont(new Font("SansSerif", 1, 11));
        btnDiaVenta.setText("Día de Venta");
        btnDiaVenta.setBounds(new Rectangle(10, 10, 85, 20));
        btnDiaVenta.setMnemonic('d');
        btnDiaVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDiaVenta_actionPerformed(e);
            }
        });
        btnRelacionMovimientos.setText("Relación de Movimientos");
        btnRelacionMovimientos.setBounds(new Rectangle(5, 5, 205, 20));
        btnRelacionMovimientos.setMnemonic('r');
        btnRelacionMovimientos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionMovimientos_actionPerformed(e);
            }
        });
        scrListaMovimientos.getViewport().add(tblListaMovimientos, null);
        contentPane.add(lblSalir, null);
        contentPane.add(lblDetalleMov, null);
        contentPane.add(scrListaMovimientos, null);
        pnlHeaderRelacionMov.add(btnRelacionMovimientos, null);
        contentPane.add(pnlHeaderRelacionMov, null);
        pnlDiaVenta.add(btnDiaVenta, null);
        pnlDiaVenta.add(lblUsuario, null);
        pnlDiaVenta.add(lblUsuario_T, null);
        pnlDiaVenta.add(btnBuscar, null);
        pnlDiaVenta.add(txtDiaVenta, null);
        contentPane.add(pnlDiaVenta, null);
        this.getContentPane().add(contentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        iniciarValoresFecha();
        initTable();
        btnBuscar.doClick();
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDiaVenta);
    };

    //**************************************************************************
    //Método "initialize()"
    //**************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsPtoVenta.columnsListaMovimientos, ConstantsPtoVenta.defaultValuesListaMovimientos,
                                    0);
        FarmaUtility.initSimpleList(tblListaMovimientos, tableModel, ConstantsPtoVenta.columnsListaMovimientos);
        cargaListaMovimientos();
    }

    //**************************************************************************
    //Métodos de inicialización
    //**************************************************************************

    //**************************************************************************
    //Metodos de eventos
    //**************************************************************************

    private void btnDiaVenta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDiaVenta);
    }

    private void btnRelacionMovimientos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaMovimientos);
    }

    private void tblListaMovimientos_keyPressed(KeyEvent e) {
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDiaVenta);
        mostrarDatos();
    }

    private void txtDiaVenta_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaMovimientos, txtDiaVenta, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            btnBuscar.doClick();
        } else {
            chkKeyPressed(e);
        }
    }

    //**************************************************************************
    //Metodos auxiliares de eventos
    //**************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (tieneRegistroSeleccionado(tblListaMovimientos)) {
                if (esRegistroHabil()) {
                    cargarRegSeleccionado();
                    DlgDetalleMovimientos dlgDetalleMovimientos =
                        new DlgDetalleMovimientos(this.myParentFrame, this.getTitle(), true);
                    dlgDetalleMovimientos.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        cargaListaMovimientos();
                        FarmaVariables.vAceptar = false;
                    }
                }
            }
        }
    }

    //**************************************************************************
    //Metodos de lógica de negocio
    //**************************************************************************

    private void cargaListaMovimientos() {
        try {
            if (VariablesPtoVenta.vTipOpMovCaja.equals(ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_REGISTRAR_VENTA)) {
                DBPtoVenta.cargaListaAperturasDia(tableModel);
            } else if (VariablesPtoVenta.vTipOpMovCaja.equals(ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_CONSULTA)) {
                DBPtoVenta.cargaListaMovsConsult(tableModel);
            }
            if (tblListaMovimientos.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaMovimientos, tableModel, 12, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener consulta de movimientos. \n " + e.getMessage(),
                                     txtDiaVenta);
        }
    }

    private void mostrarDatos() {
        this.lblUsuario.setText(FarmaVariables.vNomUsu + " " + FarmaVariables.vPatUsu);
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegSeleccionado() {
        VariablesPtoVenta.vSecMovCaja =
                tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 0).toString().trim();
        VariablesPtoVenta.vNumCaja =
                tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 2).toString().trim();
        VariablesPtoVenta.vSecMovCajaOrigen =
                tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 11).toString().trim();

        if (VariablesPtoVenta.vSecMovCajaOrigen.trim().equals(""))
            VariablesPtoVenta.vSecMovCajaOrigen =
                    tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 0).toString().trim();

        VariablesAdministracion.vNumCaja =
                tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 2).toString().trim();
        VariablesAdministracion.vNumTurnoCaja =
                tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 3).toString().trim();
        VariablesAdministracion.vCajero =
                tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 5).toString().trim();
        VariablesAdministracion.vFechaCaja =
                tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(), 1).toString().trim();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private boolean esRegistroHabil() {
        boolean rpta = false;
        if (VariablesPtoVenta.vTipOpMovCaja.equals(ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_REGISTRAR_VENTA)) {
            if (tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(),
                                               8).toString().trim().equals(ConstantsPtoVenta.TIP_MOV_CAJA_APERTURA))
                rpta = true;
            else {
                //FarmaUtility.showMessage(this,"Un movimiento de apertura no puede tener detalle",txtDiaVenta);
                rpta = false;
            }
        } else if (VariablesPtoVenta.vTipOpMovCaja.equals(ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_CONSULTA)) {
            if (!tblListaMovimientos.getValueAt(tblListaMovimientos.getSelectedRow(),
                                                8).toString().trim().equals(ConstantsPtoVenta.TIP_MOV_CAJA_APERTURA))
                rpta = true;
            else {
                FarmaUtility.showMessage(this, "Un movimiento de apertura no puede tener detalle", txtDiaVenta);
                rpta = false;
            }
        }
        return rpta;
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (datosValidados()) {
            VariablesAdministracion.vFecDiaVta = txtDiaVenta.getText().trim();
            cargaListaMovimientos();
            FarmaUtility.moveFocus(txtDiaVenta);
        }
    }

    private boolean datosValidados() {
        if (!FarmaUtility.validaFecha(txtDiaVenta.getText(), "dd/MM/yyyy") || txtDiaVenta.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha correctamente", txtDiaVenta);
            return false;
        }
        return true;
    }

    private void iniciarValoresFecha() {
        String fecActual = "";
        try {
            fecActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        } catch (Exception ex) {
            log.error("", ex);
            java.util.Date fec = new java.util.Date();
            fecActual = fec.toString();
            FarmaUtility.showMessage(this, "Error al obtener la fecha actual. \n " + ex.getMessage(), txtDiaVenta);
        }
        txtDiaVenta.setText(fecActual);
    }

    private void txtDiaVenta_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtDiaVenta, e);
    }
}
