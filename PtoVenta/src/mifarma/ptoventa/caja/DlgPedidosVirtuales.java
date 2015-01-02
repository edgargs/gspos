package mifarma.ptoventa.caja;


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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgPedidosVirtuales extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgPedidosVirtuales.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelDetallePedidosVirtuales;
    private FarmaTableModel tableModelCabeceraPedidosVirtuales;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private String numPedVta = "";
    ActionMap actionMap1 = new ActionMap();
    JScrollPane scrPedidosCab = new JScrollPane();
    JPanel pnlRelacion = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    JScrollPane scrDetalle = new JScrollPane();
    JPanel pnlItems = new JPanel();
    XYLayout xYLayout3 = new XYLayout();
    JButton btnDetalle = new JButton();
    JLabelFunction lblEsc = new JLabelFunction();
    JTable tblDetalle = new JTable();
    JTable tblListaPedidosCab = new JTable();
    private JButtonLabel btnPedidoCab = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgPedidosVirtuales() {
        this(null, "", false);
    }

    public DlgPedidosVirtuales(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(650, 313));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedidos Virtuales");
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
        scrPedidosCab.setFont(new Font("SansSerif", 0, 11));
        scrPedidosCab.setBounds(new Rectangle(10, 35, 620, 95));
        scrPedidosCab.setBackground(new Color(255, 130, 14));
        pnlRelacion.setBackground(new Color(255, 130, 14));
        pnlRelacion.setLayout(xYLayout2);
        pnlRelacion.setFont(new Font("SansSerif", 0, 11));
        pnlRelacion.setBounds(new Rectangle(10, 10, 620, 25));
        scrDetalle.setFont(new Font("SansSerif", 0, 11));
        scrDetalle.setBounds(new Rectangle(10, 165, 620, 75));
        scrDetalle.setBackground(new Color(255, 130, 14));
        pnlItems.setBackground(new Color(255, 130, 14));
        pnlItems.setFont(new Font("SansSerif", 0, 11));
        pnlItems.setLayout(xYLayout3);
        pnlItems.setBounds(new Rectangle(10, 140, 620, 25));
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
        lblEsc.setBounds(new Rectangle(535, 250, 95, 20));
        tblDetalle.setFont(new Font("SansSerif", 0, 11));
        tblDetalle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalle_keyPressed(e);
            }
        });
        tblListaPedidosCab.setFont(new Font("SansSerif", 0, 11));
        tblListaPedidosCab.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                tblListaUltimos_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblListaUltimos_keyPressed(e);
            }
        });
        btnPedidoCab.setText("Relacion Pedidos :");
        btnPedidoCab.setMnemonic('R');
        btnPedidoCab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedidoCab_actionPerformed(e);
            }
        });
        scrPedidosCab.getViewport();
        scrDetalle.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        scrPedidosCab.getViewport().add(tblListaPedidosCab, null);
        jContentPane.add(scrPedidosCab, null);
        pnlRelacion.add(btnPedidoCab, new XYConstraints(10, 5, 115, 15));
        jContentPane.add(pnlRelacion, null);
        scrDetalle.getViewport().add(tblDetalle, null);
        jContentPane.add(scrDetalle, null);
        pnlItems.add(btnDetalle, new XYConstraints(10, 5, 125, 15));
        jContentPane.add(pnlItems, null);
        jContentPane.add(lblEsc, null);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableDetPedidosVirtuales();
        initTableListaCabPedidosVirtuales();
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaCabPedidosVirtuales() {
        tableModelCabeceraPedidosVirtuales =
                new FarmaTableModel(ConstantsCaja.columnsListaCabPedVirtual, ConstantsCaja.defaultListaCabPedVirtual,
                                    0);
        FarmaUtility.initSimpleList(tblListaPedidosCab, tableModelCabeceraPedidosVirtuales,
                                    ConstantsCaja.columnsListaCabPedVirtual);
        cargaListaCabecera();
    }

    private void initTableDetPedidosVirtuales() {
        tableModelDetallePedidosVirtuales =
                new FarmaTableModel(ConstantsCaja.columnsListaDetPedVirtual, ConstantsCaja.defaultListaDetPedVirtual,
                                    0);
        FarmaUtility.initSimpleList(tblDetalle, tableModelDetallePedidosVirtuales,
                                    ConstantsCaja.columnsListaDetPedVirtual);
    }

    private void cargaListaCabecera() {
        try {
            DBCaja.obtieneListaPedidosVirtualesCab(tableModelCabeceraPedidosVirtuales);
            if (tblListaPedidosCab.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaPedidosCab, tableModelCabeceraPedidosVirtuales, 6,
                                     FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocusJTable(tblListaPedidosCab);
                numPedVta =
                        FarmaUtility.getValueFieldJTable(tblListaPedidosCab, tblListaPedidosCab.getSelectedRow(), 0);
                cargaListaDetallePedido(numPedVta);
            } else {
                tableModelDetallePedidosVirtuales.clearTable();
                tableModelDetallePedidosVirtuales.fireTableDataChanged();
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al cargar cabecera pedidos virtuales - \n" +
                    e, tblListaPedidosCab);
            log.error("", e);
        }
    }

    private void cargaListaDetallePedido(String pNumPedido) {
        try {
            DBCaja.obtieneListaPedidosVirtualesDet(tableModelDetallePedidosVirtuales, pNumPedido);
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al cargar detalle pedido virtual - \n" +
                    e, tblListaPedidosCab);
            log.error("", e);
        }
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblListaUltimos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblDetalle_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaPedidosCab);
    }

    private void btnPedidoCab_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPedidosCab);
    }

    private void btnDetalle_actionPerformed(ActionEvent e) {
        if (tblDetalle.getRowCount() > 0)
            FarmaUtility.moveFocusJTable(tblDetalle);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblListaUltimos_keyReleased(KeyEvent e) {
        if (tblListaPedidosCab.getRowCount() > 0) {
            numPedVta = FarmaUtility.getValueFieldJTable(tblListaPedidosCab, tblListaPedidosCab.getSelectedRow(), 0);
            cargaListaDetallePedido(numPedVta);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

}
