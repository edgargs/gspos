package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgReportePedidosComprobantes extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgReportePedidosComprobantes.class);

    Frame myParentFrame;
    FarmaTableModel tableModelListaComprobantes;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane scrListaPedComp = new JScrollPane();
    private JPanel pnlHeaderLista = new JPanel();
    private JButton btnPedComp = new JButton();
    private JPanel pnlDatosUser = new JPanel();
    private JLabel lblTurno = new JLabel();
    private JLabel lblTurno_T = new JLabel();
    private JLabel lblCaja = new JLabel();
    private JLabel lblUsuario = new JLabel();
    private JLabel lblCaja_T = new JLabel();
    private JLabel lblUsuario_T = new JLabel();
    private JTable tblListaComprobantes = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanel pnlFooterLista = new JPanel();
    private JButton lblTotal = new JButton();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();


    //**************************************************************************
    //Constructores
    //**************************************************************************


    public DlgReportePedidosComprobantes() {
        this(null, "", false);
    }

    public DlgReportePedidosComprobantes(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(707, 509));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relacion Pedidos/Comprobantes");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(494, 346));
        scrListaPedComp.setBounds(new Rectangle(10, 80, 675, 340));
        scrListaPedComp.setBackground(new Color(255, 130, 14));
        pnlHeaderLista.setBounds(new Rectangle(10, 55, 675, 25));
        pnlHeaderLista.setBackground(new Color(255, 130, 14));
        pnlHeaderLista.setLayout(null);
        btnPedComp.setText("Lista Pedidos Comprobantes");
        btnPedComp.setBounds(new Rectangle(10, 0, 180, 25));
        btnPedComp.setBackground(new Color(255, 130, 14));
        btnPedComp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnPedComp.setBorderPainted(false);
        btnPedComp.setContentAreaFilled(false);
        btnPedComp.setFocusPainted(false);
        btnPedComp.setDefaultCapable(false);
        btnPedComp.setFont(new Font("SansSerif", 1, 11));
        btnPedComp.setForeground(Color.white);
        btnPedComp.setHorizontalAlignment(SwingConstants.LEFT);
        btnPedComp.setMnemonic('l');
        btnPedComp.setRequestFocusEnabled(false);
        btnPedComp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButton1_keyPressed(e);
            }
        });
        pnlDatosUser.setBounds(new Rectangle(10, 15, 675, 35));
        pnlDatosUser.setBorder(BorderFactory.createTitledBorder(""));
        pnlDatosUser.setBackground(new Color(43, 141, 39));
        pnlDatosUser.setLayout(null);
        lblTurno.setText("2");
        lblTurno.setBounds(new Rectangle(505, 10, 40, 15));
        lblTurno.setFont(new Font("SansSerif", 1, 11));
        lblTurno.setForeground(Color.white);
        lblTurno_T.setText("Turno :");
        lblTurno_T.setBounds(new Rectangle(440, 10, 55, 15));
        lblTurno_T.setFont(new Font("SansSerif", 1, 11));
        lblTurno_T.setForeground(Color.white);
        lblCaja.setText("1");
        lblCaja.setBounds(new Rectangle(380, 10, 35, 15));
        lblCaja.setFont(new Font("SansSerif", 1, 11));
        lblCaja.setForeground(Color.white);
        lblUsuario.setText(" ");
        lblUsuario.setBounds(new Rectangle(95, 10, 210, 15));
        lblUsuario.setFont(new Font("SansSerif", 1, 11));
        lblUsuario.setForeground(Color.white);
        lblCaja_T.setText("Caja :");
        lblCaja_T.setBounds(new Rectangle(315, 10, 50, 15));
        lblCaja_T.setFont(new Font("SansSerif", 1, 11));
        lblCaja_T.setForeground(Color.white);
        lblUsuario_T.setText("Cajero :");
        lblUsuario_T.setBounds(new Rectangle(20, 10, 60, 15));
        lblUsuario_T.setFont(new Font("SansSerif", 1, 11));
        lblUsuario_T.setForeground(Color.white);
        tblListaComprobantes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaComprobantes_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(565, 445, 120, 20));
        pnlFooterLista.setBounds(new Rectangle(10, 420, 675, 20));
        pnlFooterLista.setBackground(new Color(255, 130, 14));
        pnlFooterLista.setLayout(null);
        lblTotal.setText("0.00");
        lblTotal.setBounds(new Rectangle(365, 0, 90, 20));
        lblTotal.setBackground(new Color(255, 130, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lblTotal.setBorderPainted(false);
        lblTotal.setContentAreaFilled(false);
        lblTotal.setFocusPainted(false);
        lblTotal.setDefaultCapable(false);
        lblTotal.setFont(new Font("SansSerif", 1, 11));
        lblTotal.setForeground(Color.white);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setMnemonic('l');
        lblTotal.setRequestFocusEnabled(false);
        lblTotal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButton1_keyPressed(e);
            }
        });
        lblF2.setText("[F2] Ordenar");
        lblF2.setBounds(new Rectangle(35, 445, 120, 20));
        lblF3.setText("[F3] Pedidos Virtuales");
        lblF3.setBounds(new Rectangle(170, 445, 150, 20));
        scrListaPedComp.getViewport();
        pnlDatosUser.add(lblTurno, null);
        pnlDatosUser.add(lblTurno_T, null);
        pnlDatosUser.add(lblCaja, null);
        pnlDatosUser.add(lblUsuario, null);
        pnlDatosUser.add(lblCaja_T, null);
        pnlDatosUser.add(lblUsuario_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        scrListaPedComp.getViewport().add(tblListaComprobantes, null);
        pnlFooterLista.add(lblTotal, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(pnlFooterLista, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaPedComp, null);
        pnlHeaderLista.add(btnPedComp, null);
        jContentPane.add(pnlHeaderLista, null);
        jContentPane.add(pnlDatosUser, null);
        //this.getContentPane().add(jContentPane, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }


    //**************************************************************************
    //Método "initialize()"
    //**************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        mostrarMontos();
        initTableListaComprobantes();
        mostrarDatos();


    }


    //**************************************************************************
    //Métodos de inicialización
    //**************************************************************************

    private void initTableListaComprobantes() {
        tableModelListaComprobantes =
                new FarmaTableModel(ConstantsCaja.columnsListaComprobantes, ConstantsCaja.defaultListaComprobantes, 0);
        FarmaUtility.initSimpleList(tblListaComprobantes, tableModelListaComprobantes,
                                    ConstantsCaja.columnsListaComprobantes);
        cargaListaComprobantes();
    }


    //**************************************************************************
    //Metodos de eventos
    //**************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);

        if (VariablesPtoVenta.vTipAccesoListaComprobantes.equalsIgnoreCase(ConstantsPtoVenta.TIP_ACC_LISTA_COMP_CAJA)) {
            lblTotal.setVisible(false);
        }
        if (VariablesPtoVenta.vTipAccesoListaComprobantes.equalsIgnoreCase(ConstantsPtoVenta.TIP_ACC_LISTA_COMP_REP)) {
            lblTotal.setVisible(true);
        }

        FarmaUtility.moveFocus(tblListaComprobantes);

    }

    private void tblListaComprobantes_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jButton1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    //**************************************************************************
    //Metodos auxiliares de eventos
    //**************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F2(e)) {
            muestraComprobantesOrd();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            muestraPedidosVirtuales();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        }
    }
    //**************************************************************************
    //Metodos de lógica de negocio
    //**************************************************************************


    private void cargaListaComprobantes() {
        try {
            DBCaja.getListaPedidosComp(tableModelListaComprobantes);

            if (tblListaComprobantes.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaComprobantes, tableModelListaComprobantes, 0,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de ped comp");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar lista de pedidos comprobantes. \n " + e.getMessage(),
                                     tblListaComprobantes);
        }
        //JMIRANDA 12.01.2011 SUMAR SI SE PUEDE MOSTRAR
        if (VariablesCaja.vMostrarMontoComprobante.equalsIgnoreCase("S"))
            sumarTotal();

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void mostrarDatos() {


        ArrayList array = new ArrayList();
        ArrayList tmpArray = new ArrayList();
        try {
            array = DBCaja.getListaPedidosCompDatosCab();
            tmpArray = (ArrayList)array.get(2);
            this.lblUsuario.setText(tmpArray.get(1).toString().trim());
            tmpArray = (ArrayList)array.get(0);
            this.lblCaja.setText(tmpArray.get(1).toString().trim());
            tmpArray = (ArrayList)array.get(1);
            this.lblTurno.setText(tmpArray.get(1).toString().trim());
        } catch (SQLException ex) {
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al obtener los Datos de la cebecera. \n " + ex.getMessage(),
                                     tblListaComprobantes);
            this.lblUsuario.setText("");
            this.lblCaja.setText("");
            this.lblTurno.setText("");
        }

    }

    private void sumarTotal() {
        if (tblListaComprobantes.getRowCount() > 0) {
            double tot = 0;

            for (int i = 0; i < tblListaComprobantes.getRowCount(); i++) {
                tot = tot + FarmaUtility.getDecimalNumber(tblListaComprobantes.getValueAt(i, 4).toString().trim());
            }

            lblTotal.setText(FarmaUtility.formatNumber(tot));
        }

    }

    private void muestraComprobantesOrd() {
        // KMONCADA 19.09.2014 SE COLACA VARIABLES LOCALES PARA CARGAR LA VENTANA DE ORDEN DE CAMPOS
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        dlgOrdenar.setIndOrigenReporte(1);
        String[] IND_DESCRIP_CAMPO = { "Numero Comprobante", "Num. Pedido" };
        String[] IND_CAMPO = { "2", "0" };

        String[] indDescOrden = { "asc", "desc" };
        String[] indCodOrden = { "ASC", "DESC" };

        //VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLECORRECCIONCOMPROBANTES;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), "CAMPOSREPORTEPEDIDOSCOMPROBANTES", IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbOrden(), "ORDENCAMPOS", indDescOrden, indCodOrden, true);

        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            log.debug("Campo " + VariablesReporte.vCampo);
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblListaComprobantes, tableModelListaComprobantes,
                    // VariablesReporte.vCampo,
                    Integer.parseInt(FarmaLoadCVL.getCVLCode("CAMPOSREPORTEPEDIDOSCOMPROBANTES",
                                                             dlgOrdenar.getCmbCampo().getSelectedIndex())),
                    //VariablesReporte.vOrden);
                    FarmaLoadCVL.getCVLCode("ORDENCAMPOS", dlgOrdenar.getCmbOrden().getSelectedIndex()));
            tblListaComprobantes.repaint();
        }
    }

    private void muestraPedidosVirtuales() {
        DlgPedidosVirtuales dlgPedidosVirtuales = new DlgPedidosVirtuales(myParentFrame, "", true);
        dlgPedidosVirtuales.setVisible(true);
    }

    private void mostrarMontos() {
        //JMIRANDA 12.01.2011
        VariablesCaja.vMostrarMontoComprobante = "S";
        if (VariablesPtoVenta.vTipAccesoListaComprobantes.equalsIgnoreCase(ConstantsPtoVenta.TIP_ACC_LISTA_COMP_CAJA))
            VariablesCaja.vMostrarMontoComprobante = "N";

        if (VariablesPtoVenta.vTipAccesoListaComprobantes.equalsIgnoreCase(ConstantsPtoVenta.TIP_ACC_LISTA_COMP_REP))
            VariablesCaja.vMostrarMontoComprobante = "S";

        log.debug("1. VariablesCaja.vMostrarMontoComprobante: " + VariablesCaja.vMostrarMontoComprobante);
    }
}
