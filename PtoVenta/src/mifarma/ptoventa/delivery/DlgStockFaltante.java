package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.ConstantsDelivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgStockFaltante.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.08.2014   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @since 2.4.5<br>
 *
 */
public class DlgStockFaltante extends JDialog {

    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */

    private static final Logger log = LoggerFactory.getLogger(DlgStockFaltante.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlHeaderListaCajas = new JPanelTitle();
    private JScrollPane scrListaCajas = new JScrollPane();
    private JTable tblListaCajas = new JTable();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();

    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */

    public DlgStockFaltante() {
        this(null, "", false);
    }

    public DlgStockFaltante(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************ */
    /*                              METODO jbInit                               */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(758, 316));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Stock Faltante");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlHeaderListaCajas.setBounds(new Rectangle(10, 10, 725, 55));
        pnlHeaderListaCajas.setBackground(Color.red);
        scrListaCajas.setBounds(new Rectangle(10, 65, 725, 175));
        scrListaCajas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                scrListaCajas_keyPressed(e);
            }
        });
        tblListaCajas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaCajas_keyPressed(e);
            }
        });
        lblSalir.setBounds(new Rectangle(610, 250, 95, 20));
        lblSalir.setText("[ESC] Cerrar");
        jButtonLabel1.setText("<html>No se puede generar el pedido. <br>Los siguientes productos no tienen stock suficiente :</html>");
        jButtonLabel1.setBounds(new Rectangle(5, 5, 405, 50));
        jButtonLabel1.setFont(new Font("SansSerif", 1, 15));
        scrListaCajas.getViewport().add(tblListaCajas, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(scrListaCajas, null);
        pnlHeaderListaCajas.add(jButtonLabel1, null);
        jContentPane.add(pnlHeaderListaCajas, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsDelivery.columnsListaStockFaltante, ConstantsDelivery.defaultListaStockFaltante,
                                    0);
        FarmaUtility.initSimpleList(tblListaCajas, tableModel, ConstantsDelivery.columnsListaStockFaltante);
    }

    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaCajas);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void scrListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaCajas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
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
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */

    void setListado(ArrayList<ArrayList<String>> listado) {
        tableModel.data = listado;
    }
}
