package mifarma.ptoventa.administracion.cajas;


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

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.cajas.reference.ConstantsCajas;
import mifarma.ptoventa.administracion.cajas.reference.DBCajas;
import mifarma.ptoventa.administracion.cajas.reference.VariablesCajas;
import mifarma.ptoventa.administracion.impresoras.reference.ConstantsImpresoras;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import mifarma.ptoventa.caja.reference.ConstantsCaja;


public class DlgCajasImpresoras extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgCajasImpresoras.class);

    Frame myParentFrame;

    FarmaTableModel tableModelDetallePedido;

    FarmaTableModel tableModelListaPendientes;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanel jContentPane = new JPanel();

    ActionMap actionMap1 = new ActionMap();


    JLabelFunction lblEnter = new JLabelFunction();

    JScrollPane scrListaCajas = new JScrollPane();

    JPanel pnlRelacion = new JPanel();

    XYLayout xYLayout2 = new XYLayout();


    JScrollPane scrDetalle = new JScrollPane();

    JPanel pnlItems = new JPanel();

    XYLayout xYLayout3 = new XYLayout();

    JButton btnDetalle = new JButton();

    JLabel jLabel3 = new JLabel();

    JLabelFunction lblEsc = new JLabelFunction();

    JTable tblDetalle = new JTable();

    JTable tblListaCajas = new JTable();
    private JButtonLabel btnRelacionCajas = new JButtonLabel();

    // JLabel lblModo = new FarmaBlinkJLabel();
    // JLabel lblTipoPedido = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgCajasImpresoras() {
        this(null, "", false);
    }

    public DlgCajasImpresoras(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(668, 268));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Impresoras");
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
        lblEnter.setText("[F3] Modificar");
        lblEnter.setBounds(new Rectangle(80, 215, 120, 20));
        scrListaCajas.setFont(new Font("SansSerif", 0, 11));
        scrListaCajas.setBounds(new Rectangle(10, 255, 635, 10));
        scrListaCajas.setBackground(new Color(255, 130, 14));
        pnlRelacion.setBackground(new Color(255, 130, 14));
        pnlRelacion.setLayout(xYLayout2);
        pnlRelacion.setFont(new Font("SansSerif", 0, 11));
        pnlRelacion.setBounds(new Rectangle(10, 245, 635, 10));
        scrDetalle.setFont(new Font("SansSerif", 0, 11));
        scrDetalle.setBounds(new Rectangle(10, 35, 635, 170));
        scrDetalle.setBackground(new Color(255, 130, 14));
        pnlItems.setBackground(new Color(255, 130, 14));
        pnlItems.setFont(new Font("SansSerif", 0, 11));
        pnlItems.setLayout(xYLayout3);
        pnlItems.setBounds(new Rectangle(10, 10, 635, 25));
        btnDetalle.setText("Lista de Impresoras");
        btnDetalle.setFont(new Font("SansSerif", 1, 11));
        btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
        btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDetalle.setBackground(new Color(43, 141, 39));
        btnDetalle.setForeground(Color.white);
        btnDetalle.setRequestFocusEnabled(false);
        btnDetalle.setMnemonic('i');
        btnDetalle.setBorderPainted(false);
        btnDetalle.setContentAreaFilled(false);
        btnDetalle.setDefaultCapable(false);
        btnDetalle.setFocusPainted(false);
        btnDetalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetalle_actionPerformed(e);
            }
        });
        jLabel3.setText("Opciones :");
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        jLabel3.setBounds(new Rectangle(10, 215, 70, 15));
        lblEsc.setText("[ Esc ]  Cerrar");
        lblEsc.setBounds(new Rectangle(550, 215, 95, 20));
        tblDetalle.setFont(new Font("SansSerif", 0, 11));
        tblDetalle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalle_keyPressed(e);
            }
        });
        tblListaCajas.setFont(new Font("SansSerif", 0, 11));
        tblListaCajas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaPendientes_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaPendientes_keyReleased(e);
            }
        });
        btnRelacionCajas.setText("Lista de Cajas");
        btnRelacionCajas.setMnemonic('C');
        btnRelacionCajas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionCajas_actionPerformed(e);
            }
        });
        scrListaCajas.getViewport();
        scrDetalle.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEnter, null);
        scrListaCajas.getViewport().add(tblListaCajas, null);
        jContentPane.add(scrListaCajas, null);
        pnlRelacion.add(btnRelacionCajas, new XYConstraints(5, 5, 115, 15));
        jContentPane.add(pnlRelacion, null);
        scrDetalle.getViewport().add(tblDetalle, null);
        jContentPane.add(scrDetalle, null);
        pnlItems.add(btnDetalle, new XYConstraints(10, 5, 165, 15));
        jContentPane.add(pnlItems, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(lblEsc, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        //initTableListaCajas();
        //cargarRegCajSeleccionado();
        initTableImpresoras();
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaCajas() {

        tableModelListaPendientes =
                new FarmaTableModel(ConstantsCajas.columnsListaCajas, ConstantsCajas.defaultValuesListaCajas, 0);
        FarmaUtility.initSimpleList(tblListaCajas, tableModelListaPendientes, ConstantsCajas.columnsListaCajas);
        cargarListaCajas();

    };

    private void initTableImpresoras() {
        tableModelDetallePedido =
                new FarmaTableModel(ConstantsImpresoras.columnsListaImpresoras, ConstantsImpresoras.defaultValuesListaImpresoras,
                                    0);
        FarmaUtility.initSimpleList(tblDetalle, tableModelDetallePedido, ConstantsImpresoras.columnsListaImpresoras);
        cargaListaImpresoras();
    };

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblListaPendientes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            FarmaUtility.moveFocus(tblDetalle);
        } else {
            chkKeyPressed(e);
        }

    }

    private void tblDetalle_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            FarmaUtility.moveFocus(tblListaCajas);
        } else {
            chkKeyPressed(e);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblDetalle);
    }

    private void btnRelacionCajas_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaCajas);
    }

    private void btnDetalle_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblDetalle);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {

        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (tieneRegistroSeleccionado(tblDetalle)) {
                // cargarRegCajSeleccionado();
                cargarRegImpSeleccionado();
                //if(VariablesCaja.vTipComp.equalsIgnoreCase("01")||VariablesCaja.vTipComp.equalsIgnoreCase("05")){
                DlgListaImpReemp dlgListaImpReemp = new DlgListaImpReemp(this.myParentFrame, "", true);
                dlgListaImpReemp.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    cargaListaImpresoras();
                }
                /*}else
                FarmaUtility.showMessage(this,"No se puede modificar una asignacion predefinida ",tblDetalle);*/
            }
        }

    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargarListaCajas() {
        try {
            DBCajas.getListaCajas(tableModelListaPendientes);

            if (tblListaCajas.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaCajas, tableModelListaPendientes, 0, "asc");
            log.debug("se cargo la lista de cajas");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener la lista de cajas. \n" +
                    e.getMessage(), tblListaCajas);
        }
    }


    private void tblListaPendientes_keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
            if (tieneRegistroSeleccionado(tblListaCajas)) {
                cargarRegCajSeleccionado();
                cargaListaImpresoras();
            }
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarRegCajSeleccionado() {

        VariablesCajas.vNumCaja = tblListaCajas.getValueAt(tblListaCajas.getSelectedRow(), 0).toString().trim();

    }

    private void cargarRegImpSeleccionado() {
        VariablesCaja.vTipComp = tblDetalle.getValueAt(tblDetalle.getSelectedRow(), 7).toString().trim();
        VariablesCajas.vSecImprLocal = tblDetalle.getValueAt(tblDetalle.getSelectedRow(), 0).toString().trim();
    }


    private void cambiaEstadoPedido(String pNumPed, String pEst) throws SQLException {
        DBCaja.cambiarEstadoPed(pNumPed, pEst);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cargaListaImpresoras() {

        try {
            DBCajas.getListaImpresorasCaja(tableModelDetallePedido);
            if (tblDetalle.getRowCount() > 0)
                FarmaUtility.ordenar(tblDetalle, tableModelDetallePedido, 0, "asc");
            log.debug("se cargo la lista de impresoras");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar la lista de impresoras . \n " + e.getMessage(),
                                     tblListaCajas);
        }

    }


}
