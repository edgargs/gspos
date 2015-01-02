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
public class DlgDetalleFaltaCero extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleFaltaCero.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrProductos = new JScrollPane();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JTable tblProductos = new JTable();
    private JLabelFunction lblF12 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDetalleFaltaCero() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgDetalleFaltaCero(Frame parent, String title, boolean modal) {
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
        this.setTitle("Detalle Producto Falta Cero");
        this.setSize(new Dimension(468, 303));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(355, 250, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(240, 250, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrProductos.setBounds(new Rectangle(10, 35, 440, 210));
        pnlTitulo.setBounds(new Rectangle(10, 15, 440, 20));
        btnListado.setText("Listado Detalle");
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
        lblF12.setBounds(new Rectangle(10, 250, 105, 20));
        lblF12.setText("[ F5 ] Borrar");
        scrProductos.getViewport();
        scrProductos.getViewport();
        pnlTitulo.add(btnListado, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(pnlTitulo, null);
        tblProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblProductos_keyPressed(e);
            }
        });
        scrProductos.getViewport().add(tblProductos, null);
        jContentPane.add(scrProductos, null);
        jContentPane.add(lblF11, null);
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
                new FarmaTableModel(ConstantsReporte.columnsListaDetFaltaCero, ConstantsReporte.defaultValuesListaDetFaltaCero,
                                    0);
        FarmaUtility.initSimpleList(tblProductos, tableModel, ConstantsReporte.columnsListaDetFaltaCero);
        cargaRegistros();
    }

    private void cargaRegistros() {
        try {
            tableModel.clearTable();
            DBReportes.cargaListaDetFaltaCero(tableModel, VariablesReporte.vCodProd, VariablesReporte.vFechaInicio,
                                              VariablesReporte.vFechaFin);

            if (tblProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblProductos, tableModel, 2, FarmaConstants.ORDEN_DESCENDENTE);
            else
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", btnListado);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar la lista del registro de ventas : \n" +
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
        if (tblProductos.getRowCount() > 0) {
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
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ||
                FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) { //JCORTEZ 19.01.10
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Está seguro que desea borrar el registro?"))
                    borrarRegistro();
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción.", null);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void borrarRegistro() {
        try {
            String vSecUsu = "" + tblProductos.getValueAt(tblProductos.getSelectedRow(), 3);
            String vFecha = "" + tblProductos.getValueAt(tblProductos.getSelectedRow(), 2);

            DBReportes.borrarRegistroDetFaltaCero(VariablesReporte.vCodProd, vSecUsu, vFecha);
            FarmaUtility.aceptarTransaccion();

            tableModel.data.remove(tblProductos.getSelectedRow());
            tableModel.fireTableDataChanged();
            tblProductos.repaint();
            FarmaUtility.moveFocusJTable(tblProductos);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error en base de datos al borrar registro.", null);
            FarmaUtility.moveFocusJTable(tblProductos);
        }
    }
}
