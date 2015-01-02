package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProdAdicionales extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProdAdicionales.class);

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelWhite lblZona_ = new JLabelWhite();
    private JLabelWhite lblZona = new JLabelWhite();
    private JScrollPane scrListaLocales = new JScrollPane();
    private JTable tblListaLocales = new JTable();
    private JLabelFunction lblFFiltro = new JLabelFunction();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();

    private JFrame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();

    private FarmaTableModel tableModel;

    public DlgProdAdicionales(JFrame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(660, 361));
        jPanelTitle1.setBounds(new Rectangle(10, 15, 630, 20));
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        btnLista.setText("Listado de Locales");
        btnLista.setBounds(new Rectangle(5, 0, 115, 20));
        btnLista.setMnemonic('L');
        lblZona_.setText("Zona:");
        lblZona_.setBounds(new Rectangle(425, 0, 40, 20));
        lblZona.setBounds(new Rectangle(480, 0, 110, 20));
        scrListaLocales.setBounds(new Rectangle(10, 35, 630, 235));
        tblListaLocales.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        lblFFiltro.setBounds(new Rectangle(10, 300, 145, 20));
        lblFFiltro.setText("[ F2 ] Filtrar por Zona");
        lblFCerrar.setBounds(new Rectangle(530, 300, 110, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        jPanelTitle2.setBounds(new Rectangle(10, 270, 630, 20));
        jPanelTitle1.add(btnLista, null);
        jPanelTitle1.add(lblZona, null);
        jPanelTitle1.add(lblZona_, null);
        scrListaLocales.getViewport().add(tblListaLocales, null);
        jPanelWhite1.add(jPanelTitle2, null);
        jPanelWhite1.add(lblFCerrar, null);
        jPanelWhite1.add(lblFFiltro, null);
        jPanelWhite1.add(scrListaLocales, null);
        jPanelWhite1.add(jPanelTitle1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }

    private void initialize() {
        lblZona.setText(VariablesProducto.vDescZona);
        initTable();
    }

    private void initTable() {
        /*tableModel = new FarmaTableModel(ConstantsDistribucion.columnsListaProductosAdic,
                                     ConstantsDistribucion.defaultValuesListaProductosAdic,
                                     0);
    FarmaUtility.initSimpleList(tblListaLocales,tableModel,
                                ConstantsDistribucion.columnsListaProductosAdic);*/
    }

    /*METODOS DE MANEJO DE EVENTOS*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocusJTable(tblListaLocales);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaLocales);
    }

    private void tblListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e))
            funcion_f2();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /*METODOS DE LOGICA DE NEGOCIO*/

    private void funcion_f2() {
        DlgFiltroZonas dlgFiltro = new DlgFiltroZonas(myParentFrame, "Filtrar Zonas", true);
        dlgFiltro.setVisible(true);
    }
}
