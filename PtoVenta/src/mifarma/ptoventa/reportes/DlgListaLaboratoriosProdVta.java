package mifarma.ptoventa.reportes;


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
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
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
 * Nombre de la Aplicación : DlgDetalleFaltaCero.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      06.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaLaboratoriosProdVta extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaLaboratoriosProdVta.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane scrProductos = new JScrollPane();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JTable tblLaboratorios = new JTable();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaLaboratoriosProdVta() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListaLaboratoriosProdVta(Frame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista  de Laboratorios por Producto");
        this.setSize(new Dimension(528, 365));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(415, 305, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        scrProductos.setBounds(new Rectangle(10, 35, 500, 255));
        pnlTitulo.setBounds(new Rectangle(10, 15, 500, 20));
        btnListado.setText("Listado Laboratorios");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListado_keyPressed(e);
            }
        });
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        scrProductos.getViewport();
        scrProductos.getViewport();
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        scrProductos.getViewport().add(tblLaboratorios, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableLista();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableLista() {
        tableModel =
                new FarmaTableModel(ConstantsReporte.columnsListaProveedoresProdVta, ConstantsReporte.defaultValuesListaProveedoresProdVta,
                                    0);
        FarmaUtility.initSimpleList(tblLaboratorios, tableModel, ConstantsReporte.columnsListaProveedoresProdVta);
        cargaRegistros();
    }

    private void cargaRegistros() {
        try {
            tableModel.clearTable();
            DBReportes.listaVentasporProductoLab(tableModel, VariablesReporte.vFechaInicio,
                                                 VariablesReporte.vFechaFin);

            if (tblLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblLaboratorios, tableModel, 1, FarmaConstants.ORDEN_DESCENDENTE);
            else
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", btnListado);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar la lista de laboratorios : \n" +
                    sql.getMessage(), btnListado);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(btnListado);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblLaboratorios.getRowCount() > 0) {
            FarmaUtility.moveFocus(btnListado);
        }
    }

    private void btnListado_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblLaboratorios, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
}
