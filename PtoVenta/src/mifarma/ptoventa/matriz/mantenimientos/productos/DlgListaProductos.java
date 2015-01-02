package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProductos_PedN.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LREQUE      14.02.2007   Creación<br>
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 *

 */

public class DlgListaProductos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaProductos.class);

    private JFrame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private FarmaTableModel tableModel;

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblFAceptar = new JLabelFunction();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JLabelWhite lblProd = new JLabelWhite();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JLabelWhite lblProd1 = new JLabelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JPanelTitle lblTitle2 = new JPanelTitle();
    private JLabelWhite lblCantProd_ = new JLabelWhite();
    private JLabelWhite lblCantProd = new JLabelWhite();
    private JLabelFunction lblFCambiarZona = new JLabelFunction();
    private JLabelWhite lblZona = new JLabelWhite();

    public DlgListaProductos(JFrame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(559, 397));
        this.setDefaultCloseOperation(0);
        this.setTitle("Listado de Productos");
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        scrListaProductos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlHeader.setBounds(new Rectangle(10, 10, 530, 40));
        lblProd.setText("Producto:");
        lblProd.setBounds(new Rectangle(20, 10, 75, 20));
        txtProducto.setBounds(new Rectangle(85, 10, 350, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }
        });
        lblProd1.setText("Laboratorio:");
        lblProd1.setBounds(new Rectangle(225, 0, 75, 20));
        lblLaboratorio.setBounds(new Rectangle(310, 0, 200, 20));
        lblTitle2.setBounds(new Rectangle(10, 305, 530, 20));
        lblCantProd_.setText("Cantidad de Productos:");
        lblCantProd_.setBounds(new Rectangle(325, 0, 135, 20));
        lblCantProd.setBounds(new Rectangle(465, 0, 50, 20));
        lblFCambiarZona.setBounds(new Rectangle(10, 335, 135, 20));
        lblFCambiarZona.setText("[ F2 ] Cambiar Zona");
        lblZona.setBounds(new Rectangle(15, 0, 120, 20));
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);

        scrListaProductos.setBounds(new Rectangle(10, 80, 530, 225));
        jPanelTitle1.setBounds(new Rectangle(10, 60, 530, 20));
        btnLista.setText("Listado de Productos");
        btnLista.setBounds(new Rectangle(15, 0, 135, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblFAceptar.setBounds(new Rectangle(335, 335, 100, 20));
        lblFAceptar.setText("[ F11 ] Aceptar");
        lblFCerrar.setBounds(new Rectangle(440, 335, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        pnlHeader.add(txtProducto, null);
        pnlHeader.add(lblProd, null);
        lblTitle2.add(lblZona, null);
        lblTitle2.add(lblCantProd, null);
        lblTitle2.add(lblCantProd_, null);
        pnlBlanco.add(lblFCambiarZona, null);
        pnlBlanco.add(lblTitle2, null);
        pnlBlanco.add(pnlHeader, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(lblFAceptar, null);
        pnlBlanco.add(jPanelTitle1, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        pnlBlanco.add(scrListaProductos, null);
        jPanelTitle1.add(lblLaboratorio, null);
        jPanelTitle1.add(lblProd1, null);
        jPanelTitle1.add(btnLista, null);
        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });
    }

    /*METODOS DE INICIALIZACION*/

    private void initialize() {
        initTable();
        cargaListaProductos();
    }

    private void initTable() {
        if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Nuevos)) {
            tableModel =
                    new FarmaTableModel(ConstantsProducto.columnsListaProductosNvos, ConstantsProducto.defaultValuesListaProductosNvos,
                                        0);
            FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsProducto.columnsListaProductosNvos);
        } else if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Adicionales)) {
            tableModel =
                    new FarmaTableModel(ConstantsProducto.columnsListaProductosAdic, ConstantsProducto.defaultValuesListaLocales_ProdAdic,
                                        0);
            FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsProducto.columnsListaProductosAdic);
        }
    }

    /*METODOS DE MANEJO DE EVENTOS*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtProducto);
        FarmaUtility.centrarVentana(this);
        setLaboratorio();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtProducto, 1);
        setLaboratorio();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtProducto.getText().trim(), 1, 1))) {
                    FarmaUtility.showMessage(this, "Produdcto no encontrado según criterio de búsqueda.", txtProducto);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void setLaboratorio() {
        if (tblListaProductos.getRowCount() > 0) {
            int filaSelect = tblListaProductos.getSelectedRow();
            lblLaboratorio.setText((String)tblListaProductos.getValueAt(filaSelect, 5));
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Adicionales))
                funcion_F2();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
            funcion_F11();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesProducto.vAbreNueva = true;
            cerrarVentana(false);
        }
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtProducto, 1);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /*METODOS DE LOGICA DE NEGOCIO*/

    private void setZona() {
        if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Nuevos)) {
            lblFCambiarZona.setVisible(false);
            lblZona.setText("");
        } else if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Adicionales)) {
            lblFCambiarZona.setVisible(true);
            lblZona.setText("Zona:   " + VariablesProducto.vDescZona);
        }
    }

    private void cargaListaProductos() {
        try {
            if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Nuevos)) {
                DBProducto.cargaListaProductosNvos(tableModel);
            } else if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Adicionales)) {
                DBProducto.cargaListaProductosAdic(tableModel, FarmaVariables.vCodGrupoCia,
                                                   VariablesProducto.vCodZona);
            }

            FarmaUtility.ordenar(tblListaProductos, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
            lblCantProd.setText("" + tblListaProductos.getRowCount());
            setZona();

        } catch (SQLException sql) {
            FarmaUtility.showMessage(this, "Error al listar los productos.", null);
            log.error("", sql);
        }
        FarmaUtility.moveFocusJTable(tblListaProductos);
    }

    private void funcion_F2() {
        DlgFiltroZonas dlgFiltroZonas = new DlgFiltroZonas(myParentFrame, "Filtrar Locales por Zona", true);
        dlgFiltroZonas.setVisible(true);

        if (FarmaVariables.vAceptar)
            cargaListaProductos();

    }

    private void funcion_F11() {
        if (tblListaProductos.getRowCount() > 0) {
            int filaSelect = tblListaProductos.getSelectedRow();
            VariablesProducto.vCodProducto = (String)tblListaProductos.getValueAt(filaSelect, 0);
            VariablesProducto.vDescProducto = (String)tblListaProductos.getValueAt(filaSelect, 1);
            VariablesProducto.vUnidVenta = (String)tblListaProductos.getValueAt(filaSelect, 2);
            VariablesProducto.vCantidadStock = Integer.parseInt((String)tblListaProductos.getValueAt(filaSelect, 3));

            if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Nuevos)) {
                DlgMantProdNuevos dlgDistLocales = new DlgMantProdNuevos(myParentFrame, "Productos Nuevos", true);
                dlgDistLocales.setVisible(true);
            } else if (VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Adicionales)) {
                DlgMantProdAdic dlgDistLocales = new DlgMantProdAdic(myParentFrame, "Productos Adicionales", true);
                dlgDistLocales.setVisible(true);
            }
        } else {
            FarmaUtility.showMessage(this, "No existen registros de productos.", txtProducto);
        }
    }

}
