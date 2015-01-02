package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

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


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgTransferenciasLocalVer.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      25.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgTransferenciasLocalVer extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasLocalVer.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    String vEstadoNota;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblOrigen_T = new JLabelWhite();
    private JLabelWhite lblFechaEmision_T = new JLabelWhite();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelWhite lblFechaEmision = new JLabelWhite();
    private JLabelWhite lblTipo_T = new JLabelWhite();
    private JLabelWhite lblNoTransferencia = new JLabelWhite();
    private JLabelWhite lblFechaEmision_T1 = new JLabelWhite();
    private JLabelWhite lblTipo = new JLabelWhite();
    private JLabelWhite lblDestino = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgTransferenciasLocalVer() {
        this(null, "", false);
    }

    public DlgTransferenciasLocalVer(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(705, 422));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ver Transferencia de Productos");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnllHeader1.setBounds(new Rectangle(10, 10, 680, 80));
        pnlTitle1.setBounds(new Rectangle(10, 100, 680, 25));
        scrListaProductos.setBounds(new Rectangle(10, 125, 680, 220));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(600, 355, 85, 20));
        lblOrigen_T.setText("Origen:");
        lblOrigen_T.setBounds(new Rectangle(15, 50, 50, 15));
        lblFechaEmision_T.setText("F. Emisión:");
        lblFechaEmision_T.setBounds(new Rectangle(195, 15, 70, 15));
        btnRelacionProductos.setText("Relacion de Productos Transferidos");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 205, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        lblFechaEmision.setBounds(new Rectangle(270, 15, 70, 15));
        lblTipo_T.setText("Tipo:");
        lblTipo_T.setBounds(new Rectangle(375, 10, 35, 20));
        lblNoTransferencia.setBounds(new Rectangle(130, 15, 60, 15));
        lblFechaEmision_T1.setText("No. Transferencia:");
        lblFechaEmision_T1.setBounds(new Rectangle(15, 15, 105, 15));
        lblTipo.setBounds(new Rectangle(410, 10, 125, 20));
        lblDestino.setBounds(new Rectangle(75, 50, 150, 15));
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnllHeader1.add(lblDestino, null);
        pnllHeader1.add(lblTipo, null);
        pnllHeader1.add(lblFechaEmision_T1, null);
        pnllHeader1.add(lblNoTransferencia, null);
        pnllHeader1.add(lblTipo_T, null);
        pnllHeader1.add(lblFechaEmision, null);
        pnllHeader1.add(lblFechaEmision_T, null);
        pnllHeader1.add(lblOrigen_T, null);
        jContentPane.add(pnllHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        cargarDetalle();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaProductosTransfLocalVer, ConstantsInventario.defaultValuesListaProductosTransfLocalVer,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                    ConstantsInventario.columnsListaProductosTransfLocalVer);
    }

    private void cargaListaProductos() {
        try {
            DBInventario.cargaDetalleTransfLocal(tableModel, VariablesInventario.vNumNota,
                                                 VariablesInventario.vCodOrigen);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), btnRelacionProductos);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnRelacionProductos);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnRelacionProductos);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, null, 0);

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

    private void cargarDetalle() {
        try {
            ArrayList array = new ArrayList();
            array =
                    DBInventario.cargaCabeceraTransfLocal(VariablesInventario.vNumNota, VariablesInventario.vCodOrigen);
            //log.debug(VariablesInventario.vNumNota);
            array = (ArrayList)array.get(0);
            lblNoTransferencia.setText(VariablesInventario.vNumNota);
            lblFechaEmision.setText(array.get(0).toString());
            lblTipo.setText(array.get(1).toString());
            lblDestino.setText(array.get(2).toString());

            cargaListaProductos();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener datos de la transferencia :\n" +
                    sql.getMessage(), btnRelacionProductos);
        }
    }

}
