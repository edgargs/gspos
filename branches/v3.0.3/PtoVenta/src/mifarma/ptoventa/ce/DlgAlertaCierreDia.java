package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAlertaCierreDia extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgAlertaCierreDia.class);

    private JPanel jPanel1 = new JPanel();
    private String mensaje;
    private int vRetorno;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JTextPane jTextPane1 = new JTextPane();
    private Icon optionIcon = UIManager.getIcon("OptionPane.warningIcon");
    private JLabel dialogIcon = new JLabel(optionIcon);
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JTable tblListaCierreDia = new JTable();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private FarmaTableModel tableModelHistCierreDia;
    private JLabelFunction lblEsc = new JLabelFunction();

    public DlgAlertaCierreDia(Frame dialog, String string, boolean b) {
        super(dialog, string, b);
        mensaje = string;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);

        } catch (Exception e) {
            log.error("", e);
        }
    }


    private void jbInit() throws Exception {
        this.setSize(new Dimension(729, 450));
        this.setDefaultCloseOperation(0);
        this.setTitle("Mensaje del Sistema");
        this.getContentPane().setLayout(null);
        jPanel1.setLayout(borderLayout1);
        jPanel1.setBounds(new Rectangle(0, 0, 920, 495));
        jPanel3.setLayout(null);
        jPanel4.setLayout(null);
        jTextPane1.setFont(new Font("Dialog", 1, 12));
        jTextPane1.setOpaque(false);
        jTextPane1.setEditable(false);
        jTextPane1.setFocusable(false);
        jTextPane1.setText(mensaje);
        jTextPane1.setBounds(new Rectangle(430, 5, 5, 20));
        dialogIcon.setBounds(new Rectangle(10, 5, 45, 45));
        jLabel3.setText(" ");
        jLabel3.setFont(new Font("Dialog", 1, 4));
        jLabel4.setBounds(new Rectangle(440, 25, 0, 0));
        jLabel1.setText("Atenci\u00f3n :");
        jLabel1.setBounds(new Rectangle(65, 15, 145, 30));
        jLabel1.setFont(new Font("Tahoma", 1, 18));
        jLabel2.setText("Existen comprobantes pendientes de anulaci\u00f3n declarados que a\u00fan no han sido regularizados.");
        jLabel2.setBounds(new Rectangle(15, 45, 655, 25));
        jLabel2.setFont(new Font("Tahoma", 1, 14));
        jLabel5.setText("Para poder dar VoBo de QF primero deber\u00e1 regularizarlos.");
        jLabel5.setBounds(new Rectangle(15, 65, 575, 20));
        jLabel5.setFont(new Font("Tahoma", 1, 14));
        jLabel6.setText("Cuadratura: 001 - Anulaci\u00f3n Pendiente de Ingreso al Sistema");
        jLabel6.setBounds(new Rectangle(15, 100, 610, 20));
        jLabel6.setFont(new Font("Tahoma", 1, 14));
        jScrollPane1.setBounds(new Rectangle(15, 150, 695, 220));
        lblEsc.setText("[Esc] Cerrar");
        lblEsc.setBounds(new Rectangle(310, 385, 115, 20));
        lblEsc.setFont(new Font("Tahoma", 1, 13));
        tblListaCierreDia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblHistCierre_keyPressed(e);
            }
        });
        jScrollPane1.getViewport().add(tblListaCierreDia, null);
        jPanel4.add(lblEsc, null);
        jPanel4.add(jScrollPane1, null);
        jPanel4.add(jLabel6, null);
        jPanel4.add(jLabel5, null);
        jPanel4.add(jLabel2, null);
        jPanel4.add(jLabel1, null);
        jPanel4.add(jLabel4, null);
        jPanel4.add(dialogIcon, null);
        jPanel3.add(jTextPane1, null);
        jPanel1.add(jPanel3, BorderLayout.WEST);
        jPanel1.add(jPanel4, BorderLayout.CENTER);
        jPanel1.add(jLabel3, BorderLayout.NORTH);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    private void initTable() {
        tableModelHistCierreDia =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaAnulPendRegularizar, ConstantsCajaElectronica.defaultValuesListaAnulPendRegularizar,
                                    0);
        FarmaUtility.initSimpleList(tblListaCierreDia, tableModelHistCierreDia,
                                    ConstantsCajaElectronica.columnsListaAnulPendRegularizar);
        cargaListaCierreDia();

    }

    private void cargaListaCierreDia() {
        try {
            DBCajaElectronica.cargaHistoricoCierreDia(tableModelHistCierreDia);
            FarmaUtility.moveFocusJTable(tblListaCierreDia);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar lista de cierres de dia de venta.\n" +
                    sql, tblListaCierreDia);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblHistCierre_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

}
