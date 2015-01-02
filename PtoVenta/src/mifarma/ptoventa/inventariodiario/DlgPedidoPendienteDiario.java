package mifarma.ptoventa.inventariodiario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.inventariodiario.reference.ConstantsInvDiario;
import mifarma.ptoventa.inventariodiario.reference.DBInvDiario;
import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.DlgMensajeUsuario;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgInicioInvDiario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ    23.06.2009   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */
public class DlgPedidoPendienteDiario extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgPedidoPendienteDiario.class);
    Frame myParentFrame;
    FarmaTableModel tableModelDetallePedido;
    FarmaTableModel tableModelListaPendientes;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    ActionMap actionMap1 = new ActionMap();
    JLabelFunction lblEnter = new JLabelFunction();
    JScrollPane scrPendientes = new JScrollPane();
    JPanel pnlRelacion = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    JScrollPane scrDetalle = new JScrollPane();
    JPanel pnlItems = new JPanel();
    XYLayout xYLayout3 = new XYLayout();
    JButton btnDetalle = new JButton();
    JLabelFunction lblEsc = new JLabelFunction();
    JTable tblDetalle = new JTable();
    JTable tblListaPendientes = new JTable();
    private JButtonLabel btnPedidosPendeintes = new JButtonLabel();
    // JLabel lblModo = new FarmaBlinkJLabel();
    // JLabel lblTipoPedido = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgPedidoPendienteDiario() {
        this(null, "", false);
    }

    public DlgPedidoPendienteDiario(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(677, 375));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedidos Pendientes generados en Toma Inv. Diario");
        this.setFont(new Font("SansSerif", 0, 11));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(657, 361));
        jContentPane.setBackground(Color.white);
        jContentPane.setForeground(Color.white);
        lblEnter.setText("[ F1 ] Cobrar Pedido");
        lblEnter.setBounds(new Rectangle(435, 320, 125, 20));
        scrPendientes.setFont(new Font("SansSerif", 0, 11));
        scrPendientes.setBounds(new Rectangle(5, 40, 655, 100));
        scrPendientes.setBackground(new Color(255, 130, 14));
        pnlRelacion.setBackground(new Color(255, 130, 14));
        pnlRelacion.setLayout(xYLayout2);
        pnlRelacion.setFont(new Font("SansSerif", 0, 11));
        pnlRelacion.setBounds(new Rectangle(5, 15, 655, 25));
        scrDetalle.setFont(new Font("SansSerif", 0, 11));
        scrDetalle.setBounds(new Rectangle(5, 170, 655, 140));
        scrDetalle.setBackground(new Color(255, 130, 14));
        pnlItems.setBackground(new Color(255, 130, 14));
        pnlItems.setFont(new Font("SansSerif", 0, 11));
        pnlItems.setLayout(xYLayout3);
        pnlItems.setBounds(new Rectangle(5, 145, 655, 25));
        btnDetalle.setText("Detalle del Pedido :");
        btnDetalle.setFont(new Font("SansSerif", 1, 11));
        btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
        btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDetalle.setBackground(new Color(43, 141, 39));
        btnDetalle.setForeground(Color.white);
        btnDetalle.setRequestFocusEnabled(false);
        btnDetalle.setMnemonic('d');
        btnDetalle.setBorderPainted(false);
        btnDetalle.setContentAreaFilled(false);
        btnDetalle.setDefaultCapable(false);
        btnDetalle.setFocusPainted(false);
        btnDetalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetalle_actionPerformed(e);
            }
        });
        lblEsc.setText("[ Esc ]  Cerrar");
        lblEsc.setBounds(new Rectangle(565, 320, 95, 20));
        tblDetalle.setFont(new Font("SansSerif", 0, 11));
        tblDetalle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalle_keyPressed(e);
            }
        });
        tblListaPendientes.setFont(new Font("SansSerif", 0, 11));
        tblListaPendientes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaPendientes_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaPendientes_keyReleased(e);
            }
        });
        btnPedidosPendeintes.setText("Pedidos Pendientes de Cobro");
        btnPedidosPendeintes.setMnemonic('p');
        btnPedidosPendeintes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedidosPendeintes_actionPerformed(e);
            }
        });
        scrPendientes.getViewport();
        scrDetalle.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        scrPendientes.getViewport().add(tblListaPendientes, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(scrPendientes, null);
        pnlRelacion.add(btnPedidosPendeintes, new XYConstraints(10, 5, 245, 15));
        jContentPane.add(pnlRelacion, null);
        scrDetalle.getViewport().add(tblDetalle, null);
        jContentPane.add(scrDetalle, null);
        pnlItems.add(btnDetalle, new XYConstraints(10, 5, 125, 15));
        jContentPane.add(pnlItems, null);
        jContentPane.add(lblEsc, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableListaPendientes();
        cargarRegSeleccionado();
        initTableDetallePedido();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaPendientes() {
        tableModelListaPendientes =
                new FarmaTableModel(ConstantsInvDiario.columnsListaPendientes, ConstantsInvDiario.defaultListaPendientes,
                                    0);
        FarmaUtility.initSimpleList(tblListaPendientes, tableModelListaPendientes,
                                    ConstantsInvDiario.columnsListaPendientes);

    }

    private void initTableDetallePedido() {
        tableModelDetallePedido =
                new FarmaTableModel(ConstantsInvDiario.columnsDetallePedido, ConstantsInvDiario.defaultDetallePedido,
                                    0);
        FarmaUtility.initSimpleList(tblDetalle, tableModelDetallePedido, ConstantsInvDiario.columnsDetallePedido);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblListaPendientes_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblDetalle_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaPendientes);
        //Aqui se cargara la pantalla de logeo de cajero
        cargaLogin();
    }

    private void btnPedidosPendeintes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPendientes);
    }

    private void btnDetalle_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblDetalle);
    }
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionaPedido();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            muestraCobroPedido();
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaPendientes() {
        try {
            DBInvDiario.getListaPedidosPendientesInvDiario(tableModelListaPendientes);
            if (tblListaPendientes.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaPendientes, tableModelListaPendientes, 13,
                                     FarmaConstants.ORDEN_DESCENDENTE);
            }
            log.debug("Se listaron los pedidos pendientes ");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar pedidos pendientes. \n " + e.getMessage(), tblDetalle);
        }
    }

    private void cargaDetallePedido() {
        tableModelDetallePedido.clearTable();
        tblDetalle.repaint();
        tblDetalle.removeAll();
        try {
            DBInvDiario.getListaDetallePedidoInvDiario(tableModelDetallePedido);
            if (tblDetalle.getRowCount() > 0)
                FarmaUtility.ordenar(tblDetalle, tableModelDetallePedido, 0, FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de detalle");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargra lista detalle pedido. \n " + e.getMessage(), tblDetalle);
        }
    }

    private void tblListaPendientes_keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
            if (tieneRegistroSeleccionado(tblListaPendientes)) {
                cargarRegSeleccionado();
                cargaDetallePedido();
            }
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegSeleccionado() {
        if (tieneRegistroSeleccionado(tblListaPendientes))
            VariablesCaja.vNumPedVta =
                    tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(), 2).toString().trim();

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void seleccionaPedido() {
        int vFila = tblListaPendientes.getSelectedRow();

        if (tblListaPendientes.getRowCount() <= 0)
            return;
        VariablesCaja.vNumPedVta =
                FarmaUtility.getValueFieldArrayList(tableModelListaPendientes.data, vFila, 2).trim();
        VariablesCaja.vNumPedPendiente =
                FarmaUtility.getValueFieldArrayList(tableModelListaPendientes.data, vFila, 1).trim();

        VariablesCaja.vFecPedACobrar =
                FarmaUtility.getValueFieldArrayList(tableModelListaPendientes.data, vFila, 12).trim();

        VariablesCaja.vIndConvenio =
                FarmaUtility.getValueFieldArrayList(tableModelListaPendientes.data, vFila, 14).trim();

        VariablesCaja.vCodConvenio =
                FarmaUtility.getValueFieldArrayList(tableModelListaPendientes.data, vFila, 15).trim();

        VariablesCaja.vCodCliLocal =
                FarmaUtility.getValueFieldArrayList(tableModelListaPendientes.data, vFila, 16).trim();


        //cerrarVentana(true);
    }

    private void cargaLogin() {
        VariablesVentas.vListaProdFaltaCero = new ArrayList();
        VariablesVentas.vListaProdFaltaCero.clear();
        //limpiando variables de fidelizacion
        UtilityFidelizacion.setVariables();
        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_VENDEDOR);
        dlgLogin.setVisible(true);
        if (FarmaVariables.vAceptar) {
            if (UtilityCaja.existeIpImpresora(this, null)) {
                if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) &&
                    !UtilityCaja.existeCajaUsuarioImpresora(this, null)) {
                    FarmaVariables.dlgLogin = dlgLogin;
                    cerrarVentana(false);
                } else {
                    FarmaVariables.dlgLogin = dlgLogin;
                    log.info("******* 2 *********");
                    log.info("Usuario: " + FarmaVariables.vIdUsu);
                    FarmaVariables.vAceptar = false;
                    cargaListaPendientes();
                    cargarRegSeleccionado();
                    cargaDetallePedido();
                }
            } else {
                FarmaVariables.dlgLogin = dlgLogin;
                cerrarVentana(false);
            }
        } else
            cerrarVentana(false);
    }

    /**
     * Se muestra el mensaje personalizado al usuario.
     */
    private void muestraMensajeUsuario() {
        ArrayList vAux = new ArrayList();
        String mensaje;
        try {
            DBUsuarios.getMensajeUsuario(vAux, FarmaVariables.vNuSecUsu);
            if (vAux.size() > 0) {
                log.debug("Se muestra mensaje al usuario");
                mensaje = FarmaUtility.getValueFieldArrayList(vAux, 0, 0);
                DlgMensajeUsuario dlgMensajeUsuario = new DlgMensajeUsuario(myParentFrame, "", true);
                dlgMensajeUsuario.setMensaje(mensaje);
                dlgMensajeUsuario.setVisible(true);
                DBUsuarios.actCantVeces(FarmaVariables.vNuSecUsu);
            }
        } catch (SQLException e) {
            log.error(null, e);
        }

    }

    /**
     * Se muestra la ventana de Cobro de Pedido
     */
    private void muestraCobroPedido() {
        int vFila = tblListaPendientes.getSelectedRow();
        if (tblListaPendientes.getRowCount() > 0) {
            if (vFila >= 0) {

                VariablesInvDiario.vNumPedido =
                        FarmaUtility.getValueFieldArrayList(tableModelListaPendientes.data, vFila, 2);

                seleccionaPedido();

                DlgFormaPagoInvDiario dlgFormaPago = new DlgFormaPagoInvDiario(myParentFrame, "", true);
                dlgFormaPago.setIndPedirLogueo(false);
                dlgFormaPago.setIndPantallaCerrarAnularPed(true);
                dlgFormaPago.setIndPantallaCerrarCobrarPed(true);
                dlgFormaPago.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    FarmaVariables.vAceptar = false;
                    //cerrarVentana(true);
                }
            } else {
                FarmaUtility.showMessage(this, "Debe de seleccionar un Pedido.", tblListaPendientes);
            }
        }
    }
}
