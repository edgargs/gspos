package mifarma.ptoventa.ce;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaEliminacionCuadratura.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      03.10.2006   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */
public class DlgListaDetalleOtrasCuadraturas extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaDetalleOtrasCuadraturas.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblListaDetalle = new JTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblFechaDiaVenta = new JLabelWhite();
    private JLabelWhite lblCuadratura = new JLabelWhite();
    private JLabelWhite lblDescripcionCuadratura = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaDetalleOtrasCuadraturas() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListaDetalleOtrasCuadraturas(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(682, 312));
        this.setDefaultCloseOperation(0);
        this.setTitle("Detalle de Cuadraturas");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(565, 260, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        scrLista.setBounds(new Rectangle(10, 105, 650, 145));
        tblListaDetalle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 85, 650, 20));
        btnLista.setText("Lista");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(10, 10, 645, 60));
        jLabelWhite1.setText("Dia de Venta :");
        jLabelWhite1.setBounds(new Rectangle(10, 5, 85, 15));
        lblFechaDiaVenta.setBounds(new Rectangle(100, 5, 80, 15));
        lblCuadratura.setText("Cuadratura :");
        lblCuadratura.setBounds(new Rectangle(10, 30, 75, 15));
        lblDescripcionCuadratura.setBounds(new Rectangle(100, 30, 305, 15));
        jPanelHeader1.add(lblDescripcionCuadratura, null);
        jPanelHeader1.add(lblCuadratura, null);
        jPanelHeader1.add(lblFechaDiaVenta, null);
        jPanelHeader1.add(jLabelWhite1, null);
        jContentPane.add(jPanelHeader1, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(pnlTitle1, null);
        scrLista.getViewport().add(tblListaDetalle, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        // this.setTitle(VariablesCajaElectronica.vFechaCierreDia + "   " + VariablesCajaElectronica.vCodCuadratura + " - "+ VariablesCajaElectronica.vDescCuadratura);
        lblFechaDiaVenta.setText(VariablesCajaElectronica.vFechaCierreDia);
        lblDescripcionCuadratura.setText(VariablesCajaElectronica.vCodCuadratura + " - " +
                                         VariablesCajaElectronica.vDescCuadratura);
        if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DINERO_FALSO)) {
            initTableDineroFalso();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_ROBO)) {
            initTableRobo();
        } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DEFICIT_CAJERO)) {
            initTableDeficitCajero();
        } else
            initTableOtrasCuadraturas();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableDineroFalso() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaDetalleDineroFalso, ConstantsCajaElectronica.defaultListaDetalleDineroFalso,
                                    0);
        FarmaUtility.initSimpleList(tblListaDetalle, tableModel,
                                    ConstantsCajaElectronica.columsListaDetalleDineroFalso);
    }

    private void initTableRobo() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaDetalleRobo, ConstantsCajaElectronica.defaultListaDetalleRobo,
                                    0);
        FarmaUtility.initSimpleList(tblListaDetalle, tableModel, ConstantsCajaElectronica.columsListaDetalleRobo);
    }

    private void initTableDeficitCajero() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaDetalleDeficitCajero, ConstantsCajaElectronica.defaultListaDetalleDeficitCajero,
                                    0);
        FarmaUtility.initSimpleList(tblListaDetalle, tableModel,
                                    ConstantsCajaElectronica.columsListaDetalleDeficitCajero);
    }

    private void initTableOtrasCuadraturas() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaDetalleOtrasCuadraturas, ConstantsCajaElectronica.defaultListaDetalleOtrasCuadraturas,
                                    0);
        FarmaUtility.initSimpleList(tblListaDetalle, tableModel,
                                    ConstantsCajaElectronica.columsListaDetalleOtrasCuadraturas);
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        cargaListaDetalleCuadraturas();
        FarmaUtility.moveFocusJTable(tblListaDetalle);
        FarmaUtility.centrarVentana(this);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaDetalle);
    }

    private void tblLista_keyPressed(KeyEvent e) {
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

    private void cargaListaDetalleCuadraturas() {
        try {
            DBCajaElectronica.listaDetalleCuadraturasCierreDia(tableModel, VariablesCajaElectronica.vFechaCierreDia,
                                                               VariablesCajaElectronica.vCodCuadratura);
            if (tblListaDetalle.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaDetalle, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
            FarmaUtility.moveFocusJTable(tblListaDetalle);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar los detalle de las cuadraturas en Cierre Dia\n" +
                    sql, null);
        }
    }
}
