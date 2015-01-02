

package mifarma.ptoventa.recaudacion;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Dlg muestra lista de cajeros para anular recaudación
 * @author RLLANTOY
 * @since 08.07.2013
 */
public class DlgDetalleAnularRecaudacion extends JDialog {

    //private static final Log log = LogFactory.getLog(DlgDetalleAnularPedido.class);
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleAnularRecaudacion.class);

    FarmaTableModel tableModelUsuariosCaja;
    Frame myParentFrame;
    private boolean esNotaCredito = false;
    private boolean esPrepMagistral = false;

    private JPanel jPanel1 = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabel btnListaUsuarioCaja = new JLabel();
    private JTable tblUsuariosCaja = new JTable();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    private DlgDetalleAnularRecaudacion() {
        this(null, "", false);
    }

    public DlgDetalleAnularRecaudacion(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(423, 237));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle Anulaci\u00f3n de Recaudaci\u00f3n");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        jPanel1.setBounds(new Rectangle(20, 20, 380, 30));
        jPanel1.setForeground(new java.awt.Color(255, 130, 14));
        jPanel1.setBackground(new java.awt.Color(255, 130, 14));
        jPanel1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(20, 50, 380, 125));
        jScrollPane1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jScrollPane1_keyPressed(e);
            }
        });
        btnListaUsuarioCaja.setText("Lista de Usuarios y Cajas Disponibles");
        btnListaUsuarioCaja.setBounds(new Rectangle(10, 5, 215, 20));
        btnListaUsuarioCaja.setBackground(new java.awt.Color(255, 130, 14));
        btnListaUsuarioCaja.setForeground(SystemColor.window);
        btnListaUsuarioCaja.setFont(new Font("SansSerif", 1, 11));
        tblUsuariosCaja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblUsuariosCaja_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(280, 185, 117, 19));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(155, 185, 117, 19));
        lblF11.setText("[ F11 ] Aceptar");
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);

        jScrollPane1.getViewport().add(tblUsuariosCaja, null);
        jPanelWhite1.add(lblF11, null);
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanel1.add(btnListaUsuarioCaja, null);
        jPanelWhite1.add(jPanel1, null);
    }


    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableUsuariosCaja();
    }

    private void initTableUsuariosCaja() {
        tableModelUsuariosCaja =
                new FarmaTableModel(ConstantsCaja.columnsUsuariosCaja, ConstantsCaja.defaultUsuariosCaja, 0);
        FarmaUtility.initSimpleList(tblUsuariosCaja, tableModelUsuariosCaja, ConstantsCaja.columnsUsuariosCaja);
        cargaUsuariosCajaDisponibles();

    }

    private void cargaUsuariosCajaDisponibles() {
        try {
            DBCaja.getListaCajaUsuario(tableModelUsuariosCaja);
            FarmaUtility.ordenar(tblUsuariosCaja, tableModelUsuariosCaja, 0, FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("tableModelUsuariosCaja:" + tableModelUsuariosCaja);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al verificar caja disponible - \n" +
                    e.getMessage(), tblUsuariosCaja);
        }
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnListaUsuarioCaja);
        validaCajaOpen();
    }

    private void validaCajaOpen() {
        if (tableModelUsuariosCaja.getRowCount() <= 0) {
            log.debug("validaCajaOpen::::::");
            FarmaUtility.showMessage(this, "No hay cajeros a quien asignar dicha anulación.", null);
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {

        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();

    }

    private void this_keyPressed(KeyEvent e) {

    }

    private void jScrollPane1_keyPressed(KeyEvent e) {

    }

    private void tblUsuariosCaja_keyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            VariablesCaja.vNumCaja = tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 2).toString();
            cerrarVentana(true);

        }

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
}
