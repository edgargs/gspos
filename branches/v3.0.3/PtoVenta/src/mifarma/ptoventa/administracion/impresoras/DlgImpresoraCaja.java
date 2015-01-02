package mifarma.ptoventa.administracion.impresoras;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import mifarma.ptoventa.administracion.impresoras.reference.ConstantsImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgImpresoraCaja extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgImpresoraCaja.class);
    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderRelacion = new JPanelTitle();


    private JLabelFunction lblCrear = new JLabelFunction();

    private JLabelFunction lblModificar = new JLabelFunction();

    private JLabelFunction lblActivarDesact = new JLabelFunction();

    private JLabelFunction lblSalir = new JLabelFunction();

    private JScrollPane scrRelacion = new JScrollPane();

    private JTable tblRelacion = new JTable();
    private JButtonLabel btnImpresoraCaja = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgImpresoraCaja() {
        this(null, "", false);
    }

    public DlgImpresoraCaja(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(530, 327));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Relación Impresora - Caja");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlHeaderRelacion.setBounds(new Rectangle(10, 10, 505, 30));
        lblCrear.setBounds(new Rectangle(10, 270, 100, 20));
        lblCrear.setText("[F2] Crear");
        lblModificar.setBounds(new Rectangle(135, 270, 100, 20));
        lblModificar.setText("[F3] Modificar");
        lblActivarDesact.setBounds(new Rectangle(250, 270, 145, 20));
        lblActivarDesact.setText("[F4] Activar / Desactivar");
        lblSalir.setBounds(new Rectangle(415, 270, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        scrRelacion.setBounds(new Rectangle(10, 40, 505, 225));
        btnImpresoraCaja.setText("Relacion Impresora Caja");
        btnImpresoraCaja.setBounds(new Rectangle(5, 10, 135, 15));
        btnImpresoraCaja.setMnemonic('r');
        btnImpresoraCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnImpresoraCaja_actionPerformed(e);
            }
        });
        scrRelacion.getViewport().add(tblRelacion, null);
        jContentPane.add(scrRelacion, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblActivarDesact, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        pnlHeaderRelacion.add(btnImpresoraCaja, null);
        jContentPane.add(pnlHeaderRelacion, null);
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
        tableModel =
                new FarmaTableModel(ConstantsImpresoras.columnsListaImpresorasCaja, ConstantsImpresoras.defaultValuesListaImpresorasCaja,
                                    0);
        FarmaUtility.initSimpleList(tblRelacion, tableModel, ConstantsImpresoras.columnsListaImpresorasCaja);
        cargaListaRelaciones();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyReleased(KeyEvent e) {
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaRelaciones() {
        try {
            DBImpresoras.getListaAsigCajasImpresoras(tableModel);
            if (tblRelacion.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacion, tableModel, 0, "asc");
            log.debug("se cargo la lista de relaciones");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener relacion caja/impresora. \n " + e.getMessage(),
                                     tblRelacion);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void btnImpresoraCaja_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacion);
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
