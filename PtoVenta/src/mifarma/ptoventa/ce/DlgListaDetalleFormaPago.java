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
public class DlgListaDetalleFormaPago extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaDetalleFormaPago.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblListaDetalleFormaPago = new JTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblFormaPago = new JLabelWhite();
    private JLabelWhite lblFormaPago_T = new JLabelWhite();
    private JLabelWhite lblDiaVenta = new JLabelWhite();
    private JLabelWhite lblDiaVenta_T = new JLabelWhite();
    private JLabelWhite lblTipoMoneda_T = new JLabelWhite();
    private JLabelWhite lblTipoMoneda = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaDetalleFormaPago() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListaDetalleFormaPago(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(548, 325));
        this.setDefaultCloseOperation(0);
        this.setTitle("Detalle Formas de Pago");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(430, 270, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        scrLista.setBounds(new Rectangle(10, 125, 520, 135));
        tblListaDetalleFormaPago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 105, 520, 20));
        btnLista.setText("Lista");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(10, 10, 520, 85));
        lblFormaPago.setBounds(new Rectangle(100, 30, 325, 15));
        lblFormaPago_T.setText("Forma Pago :");
        lblFormaPago_T.setBounds(new Rectangle(10, 30, 75, 15));
        lblDiaVenta.setBounds(new Rectangle(100, 5, 80, 15));
        lblDiaVenta_T.setText("Dia de Venta :");
        lblDiaVenta_T.setBounds(new Rectangle(10, 5, 85, 15));
        lblTipoMoneda_T.setText("Tipo Moneda : ");
        lblTipoMoneda_T.setBounds(new Rectangle(10, 55, 90, 15));
        lblTipoMoneda.setBounds(new Rectangle(100, 55, 165, 15));
        jPanelHeader1.add(lblTipoMoneda, null);
        jPanelHeader1.add(lblTipoMoneda_T, null);
        jPanelHeader1.add(lblFormaPago, null);
        jPanelHeader1.add(lblFormaPago_T, null);
        jPanelHeader1.add(lblDiaVenta, null);
        jPanelHeader1.add(lblDiaVenta_T, null);
        jContentPane.add(jPanelHeader1, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(pnlTitle1, null);
        scrLista.getViewport().add(tblListaDetalleFormaPago, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        lblDiaVenta.setText(VariablesCajaElectronica.vFechaCierreDia);
        lblFormaPago.setText(VariablesCajaElectronica.vCodFormaPago + " - " + VariablesCajaElectronica.vDescFormaPago);
        lblTipoMoneda.setText(VariablesCajaElectronica.vTipMoneda);
        initTableFormasPago();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableFormasPago() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columsListaDetalleFormasPago, ConstantsCajaElectronica.defaultListaDetalleFormasPago,
                                    0);
        FarmaUtility.initSimpleList(tblListaDetalleFormaPago, tableModel,
                                    ConstantsCajaElectronica.columsListaDetalleFormasPago);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        cargaListaDetalleFormasPago();
        FarmaUtility.moveFocusJTable(tblListaDetalleFormaPago);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaDetalleFormaPago);
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

    private void cargaListaDetalleFormasPago() {
        try {
            DBCajaElectronica.listaDetalleFormasPagoCierreDia(tableModel, VariablesCajaElectronica.vFechaCierreDia,
                                                              VariablesCajaElectronica.vCodFormaPago);
            if (tblListaDetalleFormaPago.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaDetalleFormaPago, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
            FarmaUtility.moveFocusJTable(tblListaDetalleFormaPago);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar los detalle de las formas de pago en Cierre Dia\n" +
                    sql, null);
        }
    }
}
