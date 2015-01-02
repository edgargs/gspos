package mifarma.ptoventa.inventario;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgOrdCompExceso.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      26/08/2014   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgOrdCompExceso extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgOrdCompExceso.class);

    private Frame myParentFrame;
    private FarmaTableModel tableDevolucion;
    private JTable tblListaDevoluciones = new JTable();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JScrollPane scrListaDevoluciones = new JScrollPane();
    private JButtonLabel btnRelacionDevoluciones = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();

    //Variables
    transient FacadeInventario facadeInventario = new FacadeInventario();

    private final int COL_COD_ESTADO = 8;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgOrdCompExceso() {
        this(null, "", false);
    }

    public DlgOrdCompExceso(Frame parent, String title, boolean modal) {
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

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {

        this.setSize(new Dimension(790, 378));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ordenes de compra pendientes de regularizar");
        this.setDefaultCloseOperation(0);
        this.setBounds(new Rectangle(10, 10, 790, 394));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        btnRelacionDevoluciones.setText("Relaci\u00f3n de Ordenes de Compra con excesode mercaderia, pendientes de regularizar.");
        btnRelacionDevoluciones.setBounds(new Rectangle(5, 5, 515, 15));
        btnRelacionDevoluciones.setMnemonic('R');
        btnRelacionDevoluciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionDevoluciones_actionPerformed(e);
            }
        });


        pnlTitle1.add(tblListaDevoluciones, null);
        pnlTitle1.setBounds(new Rectangle(10, 10, 770, 25));
        scrListaDevoluciones.setBounds(new Rectangle(10, 35, 770, 260));
        scrListaDevoluciones.getViewport().add(tblListaDevoluciones, null);
        tblListaDevoluciones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaDevoluciones_keyPressed(e);
            }
        });

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(690, 315, 90, 20));

        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrListaDevoluciones, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(btnRelacionDevoluciones, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableDevolucion =
                new FarmaTableModel(ConstantsInventario.columnsListaOrdenPendRegul, ConstantsInventario.defaultListaOrdenPendRegul,
                                    0);
        FarmaUtility.initSimpleList(tblListaDevoluciones, tableDevolucion,
                                    ConstantsInventario.columnsListaOrdenPendRegul);

        cargaListaOrdenes();
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaDevoluciones);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnRelacionDevoluciones_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaDevoluciones);
    }

    private void tblListaDevoluciones_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargaListaOrdenes() {
        try {
            DBInventario.listarOrdCompPendRegularizar(tableDevolucion);

        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error en listarOrdCompPendRegularizar: \n" +
                    sql.getMessage(), null);
        }
        tblListaDevoluciones.repaint();
    }


}
