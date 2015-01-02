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
import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaJTable;
import mifarma.common.FarmaRowEditorModel;
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
 * Nombre de la Aplicación : DlgDistribucionLocales.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LREQUE      14.02.2007   Creación<br>
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class DlgMantProdAdic extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantProdAdic.class);

    private JFrame myParentFrame;
    private FarmaTableModel tableModel;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JTextFieldSanSerif txtCantidad;

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JScrollPane scrLocales = new JScrollPane();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JLabelFunction lblFAceptar = new JLabelFunction();
    private FarmaJTable tblListaLocales = new FarmaJTable();

    private FarmaRowEditorModel rowEditorModel;
    private DefaultCellEditor defaultCellEditor;
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblProd = new JLabelWhite();
    private JLabelWhite lblUnid = new JLabelWhite();
    private JLabelWhite lblProducto = new JLabelWhite();
    private JLabelWhite lblUnidadP = new JLabelWhite();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lblCantLocales_ = new JLabelWhite();
    private JLabelWhite lblCantLocales = new JLabelWhite();

    public DlgMantProdAdic(JFrame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(406, 423));
        this.setDefaultCloseOperation(0);
        scrLocales.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        jPanelTitle2.setBounds(new Rectangle(10, 330, 375, 20));
        lblCantLocales_.setBounds(new Rectangle(185, 0, 125, 20));
        lblCantLocales_.setText("Cantidad de Locales:");
        lblCantLocales.setBounds(new Rectangle(310, 0, 55, 20));
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(10, 15, 375, 60));
        lblProd.setText("Producto:");
        lblProd.setBounds(new Rectangle(25, 10, 70, 20));
        lblUnid.setText("Unidad P.:");
        lblUnid.setBounds(new Rectangle(25, 30, 70, 20));
        lblProducto.setText("lblProducto");
        lblProducto.setBounds(new Rectangle(100, 10, 255, 20));
        lblUnidadP.setText("lblUnidadP");
        lblUnidadP.setBounds(new Rectangle(100, 30, 255, 20));

        scrLocales.setBounds(new Rectangle(10, 105, 375, 225));
        tblListaLocales.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 85, 375, 20));
        btnLista.setText("Listado de Locales");
        btnLista.setBounds(new Rectangle(5, 0, 115, 20));
        btnLista.setMnemonic('L');

        lblFCerrar.setBounds(new Rectangle(285, 360, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        lblFAceptar.setBounds(new Rectangle(10, 360, 110, 20));
        lblFAceptar.setText("[ F11 ] Aceptar");
        jPanelHeader1.add(lblUnidadP, null);
        jPanelHeader1.add(lblProducto, null);
        jPanelHeader1.add(lblUnid, null);
        jPanelHeader1.add(lblProd, null);
        jPanelTitle2.add(lblCantLocales, null);
        jPanelTitle2.add(lblCantLocales_, null);

        pnlBlanco.add(jPanelTitle2, null);
        pnlBlanco.add(jPanelHeader1, null);
        pnlBlanco.add(lblFAceptar, null);
        pnlBlanco.add(lblFCerrar, null);
        jPanelTitle1.add(btnLista, null);
        pnlBlanco.add(jPanelTitle1, null);
        scrLocales.getViewport().add(tblListaLocales, null);
        pnlBlanco.add(scrLocales, null);
    }

    /*METODOS DE INICIALIZACION*/

    private void initialize() {
        lblProducto.setText(VariablesProducto.vCodProducto + " - " + VariablesProducto.vDescProducto);
        lblUnidadP.setText(VariablesProducto.vUnidVenta);
        initTable();
    }

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaLocales_ProdAdic, ConstantsProducto.defaultValuesListaLocales_ProdAdic,
                                    0, ConstantsProducto.editableListaLocales_ProdAdic, null);

        rowEditorModel = new FarmaRowEditorModel();
        tblListaLocales.setAutoCreateColumnsFromModel(false);
        tblListaLocales.setModel(tableModel);
        tblListaLocales.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblListaLocales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = new TableColumn(k, ConstantsProducto.columnsListaProductosAdic[k].m_width);
            tblListaLocales.addColumn(column);
        }
        tblListaLocales.setRowEditorModel(rowEditorModel);
        cargaCampos();
    }

    /*METODOS DE MANEJO DE EVENTOS*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocusJTable(tblListaLocales);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblListaLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaLocales);
        moveFocusTo(tblListaLocales.getSelectedRow());
    }

    private void addKeyListenerToTextField(final JTextField pJTextField, final String pTipoCampo) {
        pJTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                FarmaUtility.admitirDigitos(pJTextField, e);
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if ((tblListaLocales.getSelectedRow() + 1) == tblListaLocales.getRowCount()) {
                        FarmaUtility.setearRegistro(tblListaLocales, 0, null, 2);
                    } else {
                        FarmaUtility.setearRegistro(tblListaLocales, tblListaLocales.getSelectedRow() + 1, null, 2);
                    }
                    //txtCantidad.setText(txtCantidad.getText());
                } else
                    tblListaLocales_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tblListaLocales.changeSelection(tblListaLocales.getSelectedRow(),
                                                    ConstantsProducto.columnEditable_Cantidad_ProdAdic, false, false);
                }
            }
        });
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
            funcion_f11();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

    /*METODOS DE LOGICA DE NEGOCIO*/

    private void createJTextField(JTextField pJTextField, int pRow, String pTipoCampo) {
        addKeyListenerToTextField(pJTextField, pTipoCampo);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    private void getTxtCantidad(int pRow, String pTipoCampo) {
        txtCantidad = new JTextFieldSanSerif();
        txtCantidad.setLengthText(8);
        createJTextField(txtCantidad, pRow, pTipoCampo);
    }

    private void setTipoCampo() {
        for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
            getTxtCantidad(i,
                           (String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdAdic));
        }
    }

    private void cargaCampos() {
        cargaListaLocales();
        setTipoCampo();
        //FarmaUtility.setearPrimerRegistro(tblListaLocales,null,0);
        FarmaUtility.setearPrimerRegistro(tblListaLocales, txtCantidad, 2);
        if (tblListaLocales.getRowCount() > 0) {
            for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
                tblListaLocales.setValueAt("0", i, ConstantsProducto.columnEditable_Cantidad_ProdAdic);
            }
        }
        tblListaLocales.repaint();

        btnLista.doClick();
    }

    private void cargaListaLocales() {
        try {
            DBProducto.cargaListaLocalesXZona(tableModel, VariablesProducto.vCodZona);
            FarmaUtility.ordenar(tblListaLocales, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);

            lblCantLocales.setText("" + tblListaLocales.getRowCount());
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", tblListaLocales);
        }
    }

    private void moveFocusTo(int pRow) {
        FarmaUtility.setearRegistro(tblListaLocales, pRow, txtCantidad, 2);
        tblListaLocales.changeSelection(pRow, ConstantsProducto.columnEditable_Cantidad_ProdAdic, false, false);
    }

    private int getCantidadTotalReponer() {
        int vSuma = 0;
        tblListaLocales.changeSelection(0, 1, false, false);
        for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
            if (tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdAdic).equals(""))
                tblListaLocales.setValueAt("0", i, ConstantsProducto.columnEditable_Cantidad_ProdAdic);

            if (FarmaUtility.isInteger("" +
                                       tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdAdic))) {
                vSuma +=
                        Integer.parseInt((String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdAdic));
            } else {
                vSuma = -1;
                break;
            }
        }
        tblListaLocales.repaint();
        return vSuma;
    }

    private void funcion_f11() {
        tblListaLocales.changeSelection(0, 2, false, false);

        int vCantPedida = getCantidadTotalReponer();

        if (vCantPedida == -1) {
            FarmaUtility.showMessage(this, "La cantidad es inválida.", null);
            moveFocusTo(0);
            return;
        } else if (vCantPedida == 0) {
            FarmaUtility.showMessage(this, "La cantidad total a reponer a los locales no puede ser cero.", null);
            moveFocusTo(0);
            return;
        }

        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                        "¿Esta seguro que desea grabar la distribucion a locales?")) {
            String vLocal = "", vProducto = "", vCantidad = "";
            try {
                for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
                    vLocal = (String)tblListaLocales.getValueAt(i, 0);
                    vCantidad =
                            (String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdAdic);
                    DBProducto.insertaProdAdicionales(FarmaVariables.vCodGrupoCia, vLocal,
                                                      VariablesProducto.vCodProducto, vCantidad,
                                                      FarmaVariables.vIdUsu);
                }
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "La operación se realizó satisfactoriamente.", null);
                cerrarVentana(false);
            } catch (SQLException sql) {
                FarmaUtility.liberarTransaccion();
                if (sql.getErrorCode() == 1)
                    FarmaUtility.showMessage(this, "Ya se ha registrado el producto actual.", null);
                else
                    FarmaUtility.showMessage(this, "Error: no se puede grabar la distribución a locales.", null);

                FarmaUtility.moveFocusJTable(tblListaLocales);
                moveFocusTo(0);
            }
        } else {
            FarmaUtility.showMessage(this, "Se canceló la operación", null);
            moveFocusTo(0);
        }
    }

}
