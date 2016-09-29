package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaCompSaltos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaCompSaltos.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JScrollPane scrComprobantes = new JScrollPane();

    private JTable tblComprobantes = new JTable();

    private JLabelFunction lblCerrar = new JLabelFunction();

    private ArrayList listaCompsDesfasados = new ArrayList();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaCompSaltos() {
        this(null, "", false,null);
    }

    public DlgListaCompSaltos(Frame parent, String title, boolean modal,ArrayList vLista) {
        super(parent, title, modal);
        myParentFrame = parent;
        listaCompsDesfasados = vLista;
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
        this.setSize(new Dimension(368, 254));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relación de Comprobantes descontinuados");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        scrComprobantes.setBounds(new Rectangle(10, 10, 345, 185));
        tblComprobantes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblComprobantes_keyPressed(e);
            }
        });
        tblComprobantes.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    tblComprobantes_focusLost(e);
                }
            });
        lblCerrar.setBounds(new Rectangle(250, 200, 105, 20));
        lblCerrar.setText("[Esc] Cerrar");
        scrComprobantes.getViewport().add(tblComprobantes, null);
        jContentPane.add(lblCerrar, null);
        jContentPane.add(scrComprobantes, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();

    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaComprobantesDesfasados, ConstantsCajaElectronica.columnsListaComprobantesDesfasados,
                                    0);
        FarmaUtility.initSimpleList(tblComprobantes, tableModel, ConstantsCajaElectronica.columnsListaComprobantesDesfasados);
        cargarListaComprobantes();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        if(listaCompsDesfasados.size()==0){
            cerrarVentana(true);
        }
        else{
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblComprobantes);
            FarmaUtility.showMessage(this, "No se puede cerrar el dia porque tiene comprobantes desfasados hoy.",
                                     tblComprobantes);
        }
    }

    private void tblComprobantes_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargarListaComprobantes() {
        ArrayList myArray = new ArrayList();
        for (int i = 0; i < listaCompsDesfasados.size(); i++) {
            myArray = (ArrayList)listaCompsDesfasados.get(i);
            tableModel.insertRow(myArray);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }



    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblComprobantes_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(tblComprobantes);
    }
}
