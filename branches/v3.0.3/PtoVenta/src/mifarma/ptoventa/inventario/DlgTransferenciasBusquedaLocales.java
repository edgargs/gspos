package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgTransferenciasBusquedaLocales extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasBusquedaLocales.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JLabelWhite lblLocal_T = new JLabelWhite();
    private JTextFieldSanSerif txtLocal = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionLocales = new JButtonLabel();
    private JScrollPane scrListaLocales = new JScrollPane();
    private JTable tblListaLocales = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    public DlgTransferenciasBusquedaLocales() {
        this(null, "", false);
    }

    public DlgTransferenciasBusquedaLocales(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(401, 382));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Busqueda de Locales");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnllHeader1.setBounds(new Rectangle(15, 10, 360, 40));
        lblLocal_T.setText("Local:");
        lblLocal_T.setBounds(new Rectangle(10, 10, 45, 15));
        txtLocal.setBounds(new Rectangle(60, 10, 205, 20));
        txtLocal.setPreferredSize(new Dimension(99, 23));
        txtLocal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLocal_keyPressed(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(270, 10, 85, 25));
        btnBuscar.setMnemonic('B');
        pnlTitle1.setBounds(new Rectangle(15, 60, 360, 25));
        btnRelacionLocales.setText("Relacion de Locales");
        btnRelacionLocales.setBounds(new Rectangle(10, 5, 125, 15));
        btnRelacionLocales.setMnemonic('R');
        scrListaLocales.setBounds(new Rectangle(15, 85, 360, 205));
        tblListaLocales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(275, 310, 95, 20));
        lblEnter.setText("[ ENTER ] Seleccionar Local");
        lblEnter.setBounds(new Rectangle(20, 310, 200, 20));
        pnllHeader1.add(btnBuscar, null);
        pnllHeader1.add(txtLocal, null);
        pnllHeader1.add(lblLocal_T, null);
        pnlTitle1.add(btnRelacionLocales, null);
        scrListaLocales.getViewport().add(tblListaLocales, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaLocales, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnllHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {
        initTable();
    };

    private void initTable() {
        tableModel = new FarmaTableModel(columnsListaLocales, defaultValuesListaLocales, 0);
        FarmaUtility.initSimpleList(tblListaLocales, tableModel, columnsListaLocales);
        cargaLista();
    }

    private static final FarmaColumnData columnsListaLocales[] =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Descripcion", 300, JLabel.LEFT) };

    private static final Object[] defaultValuesListaLocales = { " ", " " };

    private void cargaLista() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("001");
        arrayList.add("SAN LUIS");
        tableModel.insertRow(arrayList);
        arrayList = new ArrayList();
        arrayList.add("002");
        arrayList.add("LA MOLINA");
        tableModel.insertRow(arrayList);
        arrayList = new ArrayList();
        arrayList.add("003");
        arrayList.add("MERCADO CENTRAL");
        tableModel.insertRow(arrayList);
        arrayList = new ArrayList();
        arrayList.add("004");
        arrayList.add("FAUCETT");
        tableModel.insertRow(arrayList);
    }

    private void tblListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.setVisible(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        }
    }

    private void txtLocal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtLocal);
    }

}
