package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaGuiasRM extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaGuiasRM.class);

    private Frame myParentFrame;
    private JPanel jContentPane = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tbl_guias = new JTable();
    private FarmaTableModel ftm_guias;

    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    private FacadeRecetario facade = new FacadeRecetario();
    private JPanelHeader pnl_header = new JPanelHeader();
    private JLabel lblBusqueda = new JLabel();
    private JTextField txtBusqueda = new JTextField();

    public DlgListaGuiasRM() {
        this(null, "", false);
    }

    public DlgListaGuiasRM(Frame parent, String title, boolean modal) {
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

        this.setTitle("Lista de Productos y Precios");

        this.setSize(new Dimension(656, 443));
        this.setResizable(false);
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Listado de Gu\u00edas ");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setBackground(Color.white);
        this.setIconImage(myParentFrame.getIconImage());

        jContentPane.setFocusable(false);
        jContentPane.setBackground(Color.WHITE);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 439));
        jContentPane.setForeground(Color.white);

        jScrollPane1.setBounds(new Rectangle(10, 60, 635, 310));

        tbl_guias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                chkKeyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                chkKeyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                chkKeyPressed(e);
            }
        });

        lblF1.setText("[ F1 ] Asignar Prod. Magistral");
        lblF1.setBounds(new Rectangle(10, 380, 190, 25));
        lblF1.setHorizontalAlignment(SwingConstants.CENTER);
        lblF1.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(525, 380, 120, 25));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        pnl_header.setBounds(new Rectangle(10, 15, 635, 40));
        pnl_header.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        lblBusqueda.setText("Busqueda:");
        lblBusqueda.setBounds(new Rectangle(30, 10, 85, 20));
        lblBusqueda.setLabelFor(txtBusqueda);
        lblBusqueda.setFont(new Font("SansSerif", 1, 11));
        lblBusqueda.setForeground(SystemColor.window);
        txtBusqueda.setBounds(new Rectangle(115, 10, 285, 20));
        txtBusqueda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBusqueda_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBusqueda_keyReleased(e);
            }
        });
        pnl_header.add(txtBusqueda, null);
        pnl_header.add(lblBusqueda, null);
        jContentPane.add(pnl_header, null);

        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF1, null);
        jScrollPane1.getViewport().add(tbl_guias, null);
        jContentPane.add(jScrollPane1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        FarmaUtility.centrarVentana(this);
    }

    private void initialize() {
        ftm_guias =
                new FarmaTableModel(ConstantsRecetario.columnsListaGuiasRM, ConstantsRecetario.defaultListaGuiasRM, 0);
        FarmaUtility.initSimpleList(tbl_guias, ftm_guias, ConstantsRecetario.columnsListaGuiasRM);
        llenarTablaGuia();
        tbl_guias.grabFocus();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            cerrarVentana(false);
        }
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            e.consume();
            anadirProdMagistral();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void anadirProdMagistral() {
        DlgListaGuiaProductoVirtualRM dlgListaGuiaProductoVirtualRM =
            new DlgListaGuiaProductoVirtualRM(myParentFrame, "", true);
        dlgListaGuiaProductoVirtualRM.setGuia(FarmaUtility.getValueFieldJTable(tbl_guias, tbl_guias.getSelectedRow(),
                                                                               0),
                                              FarmaUtility.getValueFieldJTable(tbl_guias, tbl_guias.getSelectedRow(),
                                                                               5));
        dlgListaGuiaProductoVirtualRM.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(FarmaVariables.vAceptar);
        }
    }

    private void llenarTablaGuia() {
        ArrayList<String> tmpArrayRow = null;
        ArrayList<ArrayList<String>> resultado = facade.listarGuiasRM();
        for (int i = 0; resultado.size() > i; i++) {
            tmpArrayRow = new ArrayList<String>();
            String tmpNumGuia = ((ArrayList)resultado.get(i)).get(0).toString().trim();
            String tmpFechaOper = ((ArrayList)resultado.get(i)).get(1).toString().trim();
            String tmpObservOper = ((ArrayList)resultado.get(i)).get(2).toString().trim();
            String tmpCliente = ((ArrayList)resultado.get(i)).get(3).toString().trim();
            String tmpCantidad = ((ArrayList)resultado.get(i)).get(4).toString().trim();
            String tmpOrdenPrep = ((ArrayList)resultado.get(i)).get(5).toString().trim();
            tmpArrayRow.add(tmpNumGuia);
            tmpArrayRow.add(tmpFechaOper);
            tmpArrayRow.add(tmpObservOper);
            tmpArrayRow.add(tmpCliente);
            tmpArrayRow.add(tmpCantidad);
            tmpArrayRow.add(tmpOrdenPrep);
            ftm_guias.insertRow(tmpArrayRow);
        }
        //Cuando se realiza una busqueda, si existe algun registro, se selecciona la primera fila
        if (tbl_guias.getRowCount() > 0 && tbl_guias.getSelectedRow() == -1)
            tbl_guias.setRowSelectionInterval(0, 0);
    }

    private void txtBusqueda_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tbl_guias, txtBusqueda, 3);
    }

    private void txtBusqueda_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tbl_guias, txtBusqueda, 2);
        chkKeyPressed(e);
    }
}
