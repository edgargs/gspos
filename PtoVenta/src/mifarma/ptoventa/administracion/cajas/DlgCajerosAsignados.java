package mifarma.ptoventa.administracion.cajas;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgCajerosAsignados extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgCajerosAsignados.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlNumCaja = new JPanelTitle();

    private JPanelTitle pnlHeaderRelacionCajeros = new JPanelTitle();

    private JScrollPane scrListaCajeros = new JScrollPane();

    private JTable tblListaCajeros = new JTable();

    private JLabelFunction lblAceptar = new JLabelFunction();

    private JLabelFunction lblSalir = new JLabelFunction();

    private JLabelFunction lbSeleccionarCajero = new JLabelFunction();

    private JLabelWhite lblNumCaja_T = new JLabelWhite();

    private JLabelWhite lblNumCaja = new JLabelWhite();

    private JLabelWhite lblRelacionCajeros_T = new JLabelWhite();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgCajerosAsignados() {
        this(null, "", false);
    }

    public DlgCajerosAsignados(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(478, 292));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Cajeros Asignados");
        jContentPane.setLayout(null);
        pnlNumCaja.setBounds(new Rectangle(320, 10, 145, 35));
        pnlHeaderRelacionCajeros.setBounds(new Rectangle(5, 50, 460, 25));
        scrListaCajeros.setBounds(new Rectangle(5, 75, 460, 155));
        lblAceptar.setBounds(new Rectangle(260, 235, 95, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblSalir.setBounds(new Rectangle(375, 235, 90, 20));
        lblSalir.setText("[ESC] Cerrar");
        lbSeleccionarCajero.setBounds(new Rectangle(5, 235, 165, 20));
        lbSeleccionarCajero.setText("[F2] Seleccionar Cajero");
        lblNumCaja_T.setText("Nro. de Caja :");
        lblNumCaja_T.setBounds(new Rectangle(5, 5, 80, 20));
        lblNumCaja.setText("1");
        lblNumCaja.setBounds(new Rectangle(105, 5, 35, 20));
        lblRelacionCajeros_T.setText("Relación de Cajeros");
        lblRelacionCajeros_T.setBounds(new Rectangle(5, 5, 130, 20));
        jContentPane.add(lbSeleccionarCajero, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblAceptar, null);
        scrListaCajeros.getViewport().add(tblListaCajeros, null);
        jContentPane.add(scrListaCajeros, null);
        pnlHeaderRelacionCajeros.add(lblRelacionCajeros_T, null);
        jContentPane.add(pnlHeaderRelacionCajeros, null);
        jContentPane.add(pnlNumCaja, null);
        pnlNumCaja.add(lblNumCaja, null);
        pnlNumCaja.add(lblNumCaja_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
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
        // tableModel = new
        // FarmaTableModel(columnsListaProductos,defaultValuesListaProductos,0);
        // FarmaUtility.initSelectList(tblProductos,tableModel,columnsListaProductos);
        // cargaListaProductos();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyReleased(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        }

        else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

        }

    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaCajeros() {
    }

}
