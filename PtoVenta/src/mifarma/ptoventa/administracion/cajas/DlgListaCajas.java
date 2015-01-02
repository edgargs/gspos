package mifarma.ptoventa.administracion.cajas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.cajas.reference.ConstantsCajas;
import mifarma.ptoventa.administracion.cajas.reference.DBCajas;
import mifarma.ptoventa.administracion.cajas.reference.VariablesCajas;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaCajas extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaCajas.class);
    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderListaCajas = new JPanelTitle();

    private JScrollPane scrListaCajas = new JScrollPane();

    private JTable tblListaCajas = new JTable();

    private JLabelFunction lblAgregar = new JLabelFunction();

    private JLabelFunction lblModificar = new JLabelFunction();

    private JLabelFunction lblSalir = new JLabelFunction();

    private JLabelFunction lblActivDesactiv = new JLabelFunction();

    private JLabelFunction lblModificar1 = new JLabelFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaCajas() {
        this(null, "", false);
    }

    public DlgListaCajas(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(519, 332));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento de Cajas");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlHeaderListaCajas.setBounds(new Rectangle(10, 10, 495, 30));
        scrListaCajas.setBounds(new Rectangle(10, 40, 495, 205));
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
        lblAgregar.setBounds(new Rectangle(10, 250, 110, 20));
        lblAgregar.setText("[F2] Agregar");
        lblModificar.setBounds(new Rectangle(130, 250, 110, 20));
        lblModificar.setText("[F3] Modificar");
        lblSalir.setBounds(new Rectangle(405, 250, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        lblActivDesactiv.setBounds(new Rectangle(255, 250, 140, 20));
        lblActivDesactiv.setText("[F4] Activar/Desactivar");
        lblModificar1.setBounds(new Rectangle(130, 275, 180, 20));
        lblModificar1.setText("[F5] Relacion Caja-Impresora");
        jButtonLabel1.setText("Lista de Cajas");
        jButtonLabel1.setBounds(new Rectangle(5, 10, 110, 15));
        jContentPane.add(lblModificar1, null);
        jContentPane.add(lblActivDesactiv, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblAgregar, null);
        scrListaCajas.getViewport().add(tblListaCajas, null);
        jContentPane.add(scrListaCajas, null);
        pnlHeaderListaCajas.add(jButtonLabel1, null);
        jContentPane.add(pnlHeaderListaCajas, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();

    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel = new FarmaTableModel(ConstantsCajas.columnsListaCajas, ConstantsCajas.defaultValuesListaCajas, 0);
        FarmaUtility.initSimpleList(tblListaCajas, tableModel, ConstantsCajas.columnsListaCajas);
        cargaListaCajas();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaCajas);
    }

    private void scrListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        }

        else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblListaCajas);
            else {
                if (estaEnLimiteCajas()) {
                    VariablesCajas.limpiar();
                    DlgDatosCaja dlgDatosCaja = new DlgDatosCaja(myParentFrame, "", true);
                    dlgDatosCaja.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        cargaListaCajas();
                        FarmaVariables.vAceptar = false;
                    }
                } else {
                    FarmaUtility.showMessage(this, "No se pueden crear mas cajas, se ha llegado al límite permitido",
                                             tblListaCajas);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblListaCajas);
            else {
                if (tieneRegistroSeleccionado(this.tblListaCajas)) {
                    cargarCajaSeleccionada();
                    int cajaAbierta = 1;
                    try {
                        cajaAbierta = DBCajas.verificaCajaAbierta();
                    } catch (SQLException ex) {
                        log.error("", ex);
                        FarmaUtility.showMessage(this,
                                                 "Error al obtener informacion de la caja. \n " + ex.getMessage(),
                                                 tblListaCajas);
                    }
                    if (cajaAbierta == 0) {
                        //caja cerrada
                        if (validarUsuariosDisp()) {
                            DlgDatosCaja dlgDatosCaja = new DlgDatosCaja(this.myParentFrame, "", true);
                            dlgDatosCaja.setVisible(true);
                            if (FarmaVariables.vAceptar) {
                                cargaListaCajas();
                                FarmaVariables.vAceptar = false;
                            }
                        }
                    } else {
                        //caja abierta
                        FarmaUtility.showMessage(this,
                                                 "No se puede modificar una caja mientras esta se encuentre aperturada.",
                                                 tblListaCajas);
                    }
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, tblListaCajas);
            else {
                if (tieneRegistroSeleccionado(this.tblListaCajas)) {

                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Esta seguro de cambiar el estado a la caja?")) {
                        cargarCajaSeleccionada();
                        try {
                            cambiarEstadoSeleccionado();
                            FarmaUtility.aceptarTransaccion();
                            cargaListaCajas();
                            FarmaUtility.showMessage(this, "La operación se realizó correctamente", tblListaCajas);
                        } catch (SQLException ex) {
                            FarmaUtility.liberarTransaccion();
                            log.error("", ex);
                            String mensaje = "";
                            if (ex.getErrorCode() == 20009) {
                                mensaje = "No se puede inactivar una caja aperturada.";
                            } else {
                                mensaje = ex.getMessage();
                            }

                            FarmaUtility.showMessage(this, "Ocurrio un error en la caja: " + mensaje, tblListaCajas);
                        }
                    }
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {

            //JCORTEZ 17.03.09
            VariablesCajas.vNumCaja = tblListaCajas.getValueAt(tblListaCajas.getSelectedRow(), 0).toString().trim();
            DlgCajasImpresoras dlgCajasImpresoras = new DlgCajasImpresoras(this.myParentFrame, "", true);
            dlgCajasImpresoras.setVisible(true);

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }


    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaCajas() {
        try {
            DBCajas.getListaCajas(tableModel);

            if (tblListaCajas.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaCajas, tableModel, 0, "asc");
            log.debug("se cargo la lista de cajas");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener informacion de la caja. \n " + e.getMessage(),
                                     tblListaCajas);
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarCajaSeleccionada() {
        VariablesCajas.vNumCaja = tblListaCajas.getValueAt(tblListaCajas.getSelectedRow(), 0).toString().trim();
        VariablesCajas.vDesCaja = tblListaCajas.getValueAt(tblListaCajas.getSelectedRow(), 1).toString().trim();
        VariablesCajas.vSecUsuAsig = tblListaCajas.getValueAt(tblListaCajas.getSelectedRow(), 4).toString().trim();
        VariablesCajas.vDesUsuAsig = tblListaCajas.getValueAt(tblListaCajas.getSelectedRow(), 3).toString().trim();

    }

    private void cambiarEstadoSeleccionado() throws SQLException {
        DBCajas.cambiaEstadoCaja(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                 VariablesCajas.vNumCaja.trim());
    }

    private boolean estaEnLimiteCajas() {
        boolean rpta = false;
        if (tblListaCajas.getRowCount() < ConstantsCaja.CANT_MAX_CAJAS_PERMITIDAS) {
            rpta = true;
        }
        return rpta;
    }

    private boolean validarUsuariosDisp() {
        boolean rpta = true;
        int cantDisp = 0;
        try {
            cantDisp = DBCajas.getCantUsuDispCaja();
        } catch (SQLException ex) {
            rpta = false;
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al verificar los usuarios disponibles. Verifique. \n " + ex.getMessage(),
                                     tblListaCajas);
            log.error("", ex);
            cantDisp = -1;
        }

        if (cantDisp == 0) {
            FarmaUtility.showMessage(this, "Actualmente no existen usuarios disponibles", tblListaCajas);
            rpta = false;
        }
        return rpta;


    }

    private void this_windowClosing(WindowEvent e) {
        /**
         * Agregado.
         * @author Rudy Llantoy
         * @since 17.05.2013
         */
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
}
