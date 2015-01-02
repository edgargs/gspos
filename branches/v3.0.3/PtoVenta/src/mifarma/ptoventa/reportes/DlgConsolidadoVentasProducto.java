package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgConsolidadoVentasProducto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      07.09.2006   Creación<br>
 * <br>
 * @author PAULO CESAR AMEGHINO ROJAS<br>
 * @version 1.0<br>
 *
 */

public class DlgConsolidadoVentasProducto extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgConsolidadoVentasProducto.class);

    FarmaTableModel tableModelConsolidado;
    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JScrollPane srcConsolidadoVtaProd = new JScrollPane();
    private JTable tblConsolidadoProd = new JTable();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelWhite lblUni = new JLabelWhite();
    private JLabelWhite lblTotot = new JLabelWhite();
    private JLabelWhite lblTotales = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgConsolidadoVentasProducto() {
        this(null, "", false);
    }

    public DlgConsolidadoVentasProducto(Frame parent, String title, boolean modal) {
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
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(455, 362));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Lista Consolidada");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        srcConsolidadoVtaProd.setBounds(new Rectangle(10, 35, 415, 240));
        tblConsolidadoProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblConsolidadoProd_keyPressed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 10, 415, 25));
        btnLista.setText("Consolidado de Ventas por Producto");
        btnLista.setBounds(new Rectangle(5, 5, 260, 15));
        btnLista.setMnemonic('C');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        btnLista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnLista_keyPressed(e);
            }
        });
        jPanelTitle2.setBounds(new Rectangle(10, 275, 415, 20));
        jLabelFunction1.setBounds(new Rectangle(310, 305, 117, 19));
        jLabelFunction1.setText("[ ESC ] Escape");
        lblUni.setText("0.00");
        lblUni.setBounds(new Rectangle(230, 0, 75, 20));
        lblUni.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblUni.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotot.setText("0.00");
        lblTotot.setBounds(new Rectangle(320, 0, 85, 20));
        lblTotot.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblTotot.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotales.setText("Totales : ");
        lblTotales.setBounds(new Rectangle(10, 0, 75, 20));
        lblTotales.setHorizontalTextPosition(SwingConstants.LEFT);
        lblTotales.setHorizontalAlignment(SwingConstants.LEFT);
        jPanelTitle1.add(btnLista, null);
        jPanelTitle2.add(lblTotales, null);
        jPanelTitle2.add(lblTotot, null);
        jPanelTitle2.add(lblUni, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jPanelTitle2, null);
        jPanelWhite1.add(jPanelTitle1, null);
        srcConsolidadoVtaProd.getViewport().add(tblConsolidadoProd, null);
        jPanelWhite1.add(srcConsolidadoVtaProd, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaConsolidado();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaConsolidado() {
        tableModelConsolidado =
                new FarmaTableModel(ConstantsReporte.columnsListaConsolidadoVtasProducto, ConstantsReporte.defaultValuesListaComprobantesDetalle,
                                    0);
        FarmaUtility.initSimpleList(tblConsolidadoProd, tableModelConsolidado,
                                    ConstantsReporte.columnsListaConsolidadoVtasProducto);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblConsolidadoProd);
        listaConsolidadoVtasProducto();
    }

    private void btnLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblConsolidadoProd_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void listaConsolidadoVtasProducto() {
        try {
            DBReportes.cargaListaConsolidadoVtasProd(tableModelConsolidado, VariablesReporte.vFechaInicio,
                                                     VariablesReporte.vFechaFin);
            FarmaUtility.ordenar(tblConsolidadoProd, tableModelConsolidado, 1, FarmaConstants.ORDEN_DESCENDENTE);
            if (tblConsolidadoProd.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultado", btnLista);
            } else {
                lblUni.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblConsolidadoProd, 1)));
                lblTotot.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblConsolidadoProd, 2)));
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar el consolidado \n" +
                    sql.getMessage(), btnLista);
        }
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblConsolidadoProd);
    }


}
