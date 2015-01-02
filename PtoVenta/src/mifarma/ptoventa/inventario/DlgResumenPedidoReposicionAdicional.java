package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgResumenPedidoReposicionAdicional extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgResumenPedidoReposicionAdicional.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JScrollPane scrListaPedidos = new JScrollPane();
    private JTable tblHistorial = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelHeader pnlCabecera = new JPanelHeader();
    private JLabelWhite lblCodigo = new JLabelWhite();
    private JLabelWhite lblValFrac = new JLabelWhite();
    private JLabelWhite lblCodigo_1 = new JLabelWhite();
    private JLabelWhite lblUValFrac_1 = new JLabelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblHistorico = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgResumenPedidoReposicionAdicional() {
        this(null, "", false);
    }

    public DlgResumenPedidoReposicionAdicional(Frame parent, String title, boolean modal) {
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

    /* ********************************************************************** */
    /*                                  METODO jbInit                         */
    /* ********************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(564, 356));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Historial del Promedio de Ventas Mensual (PVM)");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });


        tblHistorial.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                tblHistorial_keyPressed(e);
            }
        });

        scrListaPedidos.setBounds(new Rectangle(10, 120, 535, 180));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(230, 305, 85, 20));

        pnlCabecera.setBounds(new Rectangle(10, 5, 535, 85));
        lblCodigo.setText("Producto:");
        lblCodigo.setBounds(new Rectangle(5, 15, 55, 20));
        lblValFrac.setText("Laboratorio :");
        lblValFrac.setBounds(new Rectangle(5, 45, 75, 20));
        lblCodigo_1.setText("jLabelWhite1");
        lblCodigo_1.setBounds(new Rectangle(70, 15, 460, 20));
        lblCodigo_1.setFont(new Font("SansSerif", 0, 11));
        lblUValFrac_1.setText("jLabelWhite1");
        lblUValFrac_1.setBounds(new Rectangle(85, 45, 225, 20));
        lblUValFrac_1.setFont(new Font("SansSerif", 0, 11));
        jPanelTitle1.setBounds(new Rectangle(10, 95, 535, 20));
        lblHistorico.setText("Historico de PVM");
        lblHistorico.setBounds(new Rectangle(5, 0, 120, 15));
        pnlCabecera.add(lblValFrac, null);
        pnlCabecera.add(lblCodigo, null);
        pnlCabecera.add(lblCodigo_1, null);
        pnlCabecera.add(lblUValFrac_1, null);
        jPanelTitle1.add(lblHistorico, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(pnlCabecera, null);
        jContentPane.add(lblEsc, null);
        scrListaPedidos.getViewport().add(tblHistorial, null);
        jContentPane.add(scrListaPedidos, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                                  METODO initialize                     */
    /* ********************************************************************** */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
        FarmaUtility.moveFocus(tblHistorial);
    }

    /* ********************************************************************** */
    /*                            METODOS INICIALIZACION                      */
    /* ********************************************************************** */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsHistorialPedidoAdicional, ConstantsInventario.defaultHistorialPedidoAdicional,
                                    3);
        FarmaUtility.initSimpleList(tblHistorial, tableModel, ConstantsInventario.columnsHistorialPedidoAdicional);
        mostrarDatos();
        cargaHistorial();
    }

    private void tblHistorial_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cargaHistorial() {
        try {
            DBInventario.cargaHistorialPedidoAdicional(tableModel, VariablesInventario.vCodProdHist);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de pedidos : \n " + sql.getMessage(),
                                     null);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblHistorial, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void mostrarDatos() {
        lblCodigo_1.setText(VariablesInventario.vCodProd_PedRep + " - " + VariablesInventario.vNomProd_PedRep + " " +
                            VariablesInventario.vUnidMed_PedRep);
        lblUValFrac_1.setText(VariablesInventario.vNomLab_PedRep);
        //lblUValFrac_1.setText(VariablesInventario.vValFrac_PedRep);
    }
}
