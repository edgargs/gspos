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
import java.awt.Font;
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
import javax.swing.JLabel;
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

public class DlgMantProdNuevos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantProdNuevos.class);

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
    private JLabelFunction lblFAceptar1 = new JLabelFunction();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblProd = new JLabelWhite();
    private JLabelWhite lblUnid = new JLabelWhite();
    private JLabelWhite lblProducto = new JLabelWhite();
    private JLabelWhite lblUnidadP = new JLabelWhite();
    private JPanelWhite pnlBlanco3 = new JPanelWhite();
    private JLabel lblCantRep = new JLabel();
    private JLabel lblCantRep_ = new JLabel();
    private JLabel lblCantRes_ = new JLabel();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JLabelWhite lblCantStock_ = new JLabelWhite();
    private JLabelWhite lblCantStock = new JLabelWhite();
    private JLabel lblCantRes = new JLabel();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lblCantLocales = new JLabelWhite();
    private JLabelWhite lblCantLocales_ = new JLabelWhite();

    public DlgMantProdNuevos(JFrame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(428, 522));
        this.setDefaultCloseOperation(0);
        scrLocales.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        pnlBlanco3.setBounds(new Rectangle(10, 95, 400, 55));
        pnlBlanco3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblCantRep.setBounds(new Rectangle(170, 5, 75, 25));
        lblCantRep.setFont(new Font("Tahoma", 1, 13));
        lblCantRep.setText("0");
        lblCantRep_.setText("Cantidad total a reponer:");
        lblCantRep_.setBounds(new Rectangle(20, 5, 145, 25));
        lblCantRes_.setText("Cantidad restante:");
        lblCantRes_.setBounds(new Rectangle(20, 25, 105, 25));
        pnlTitle2.setBounds(new Rectangle(10, 75, 400, 20));
        lblCantStock_.setText("Cantidad Stock:");
        lblCantStock_.setBounds(new Rectangle(135, 0, 90, 20));
        lblCantStock.setText("lblCantStock");
        lblCantStock.setBounds(new Rectangle(230, 0, 105, 20));
        lblCantStock.setFont(new Font("SansSerif", 1, 14));
        lblCantRes.setText("0");
        lblCantRes.setBounds(new Rectangle(170, 25, 80, 25));
        lblCantRes.setFont(new Font("Tahoma", 1, 13));
        lblCantRes.setForeground(new Color(255, 9, 9));
        jPanelTitle2.setBounds(new Rectangle(10, 405, 400, 20));
        lblCantLocales.setBounds(new Rectangle(325, 0, 55, 20));
        lblCantLocales_.setBounds(new Rectangle(200, 0, 125, 20));
        lblCantLocales_.setText("Cantidad de Locales:");
        pnlBlanco3.add(lblCantRes, null);
        pnlBlanco3.add(lblCantRes_, null);
        pnlBlanco3.add(lblCantRep, null);
        pnlBlanco3.add(lblCantRep_, null);
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblFAceptar1.setBounds(new Rectangle(10, 435, 170, 20));
        lblFAceptar1.setText("[ F7 ] Distribuir Cantidades");
        jPanelHeader1.setBounds(new Rectangle(10, 15, 400, 60));
        lblProd.setText("Producto:");
        lblProd.setBounds(new Rectangle(45, 10, 70, 20));
        lblUnid.setText("Unidad P.:");
        lblUnid.setBounds(new Rectangle(45, 30, 70, 20));
        lblProducto.setText("lblProducto");
        lblProducto.setBounds(new Rectangle(120, 10, 255, 20));
        lblUnidadP.setText("lblUnidadP");
        lblUnidadP.setBounds(new Rectangle(120, 30, 255, 20));

        scrLocales.setBounds(new Rectangle(10, 180, 400, 225));
        tblListaLocales.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 160, 400, 20));
        btnLista.setText("Listado de Locales");
        btnLista.setBounds(new Rectangle(5, 0, 115, 20));
        btnLista.setMnemonic('L');

        lblFCerrar.setBounds(new Rectangle(310, 435, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        lblFAceptar.setBounds(new Rectangle(10, 460, 170, 20));
        lblFAceptar.setText("[ F11 ] Aceptar");
        jPanelHeader1.add(lblUnidadP, null);
        jPanelHeader1.add(lblProducto, null);
        jPanelHeader1.add(lblUnid, null);
        jPanelHeader1.add(lblProd, null);
        pnlTitle2.add(lblCantStock, null);
        pnlTitle2.add(lblCantStock_, null);

        jPanelTitle2.add(lblCantLocales_, null);
        jPanelTitle2.add(lblCantLocales, null);
        pnlBlanco.add(jPanelTitle2, null);
        pnlBlanco.add(pnlTitle2, null);
        pnlBlanco.add(pnlBlanco3, null);
        pnlBlanco.add(jPanelHeader1, null);
        pnlBlanco.add(lblFAceptar1, null);
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
        lblCantRes.setText("" + VariablesProducto.vCantidadStock);
        initTable();
    }

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaLocales_ProdNvos, ConstantsProducto.defaultValuesListaLocales_ProdNvos,
                                    0, ConstantsProducto.editableListaLocales_ProdNvos, null);

        rowEditorModel = new FarmaRowEditorModel();
        tblListaLocales.setAutoCreateColumnsFromModel(false);
        tblListaLocales.setModel(tableModel);
        tblListaLocales.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblListaLocales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = new TableColumn(k, ConstantsProducto.columnsListaLocales_ProdNvos[k].m_width);
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
                    if ((tblListaLocales.getSelectedRow() + 1) == tblListaLocales.getRowCount())
                        FarmaUtility.setearRegistro(tblListaLocales, 0, txtCantidad, 3);
                    else
                        FarmaUtility.setearRegistro(tblListaLocales, tblListaLocales.getSelectedRow() + 1, txtCantidad,
                                                    3);
                } else
                    tblListaLocales_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tblListaLocales.changeSelection(tblListaLocales.getSelectedRow(),
                                                    ConstantsProducto.columnEditable_Cantidad_ProdNvo, false, false);
                    int vCantidad = getCantidadTotalReponer();
                    if (vCantidad != -1)
                        setCantidades(vCantidad);
                    else
                        setCantidades(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int vCantidad = getCantidadTotalReponer();
                    if (vCantidad != -1)
                        setCantidades(vCantidad);
                    else
                        setCantidades(0);
                }
            }
        });
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F7)
            funcion_f7();
        else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
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
                           (String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdNvo));
        }
    }

    private void cargaCampos() {
        cargaListaLocales();
        setTipoCampo();
        //FarmaUtility.setearPrimerRegistro(tblListaLocales,null,0);
        FarmaUtility.setearPrimerRegistro(tblListaLocales, txtCantidad, 3);
        if (tblListaLocales.getRowCount() > 0) {
            for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
                tblListaLocales.setValueAt("0", i, ConstantsProducto.columnEditable_Cantidad_ProdNvo);
            }
        }
        tblListaLocales.repaint();

        btnLista.doClick();
    }

    private void cargaListaLocales() {
        try {
            lblCantStock.setText("" + VariablesProducto.vCantidadStock);
            DBProducto.cargaListaLocalesXProd(tableModel, VariablesProducto.vCodProducto);
            FarmaUtility.ordenar(tblListaLocales, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);

            lblCantLocales.setText("" + tblListaLocales.getRowCount());
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", tblListaLocales);
        }
    }

    private void moveFocusTo(int pRow) {
        FarmaUtility.setearRegistro(tblListaLocales, pRow, txtCantidad, 3);
        tblListaLocales.changeSelection(pRow, ConstantsProducto.columnEditable_Cantidad_ProdNvo, false, false);
    }

    private void funcion_f7() {
        tblListaLocales.changeSelection(0, 1, false, false);

        int cantStock = VariablesProducto.vCantidadStock;
        int cantidad = cantStock;

        if (cantidad >= tblListaLocales.getRowCount()) {
            cantidad = cantidad / tblListaLocales.getRowCount();
            for (int i = 0; i < tblListaLocales.getRowCount(); i++)
                tblListaLocales.setValueAt("" + cantidad, i, ConstantsProducto.columnEditable_Cantidad_ProdNvo);

            tblListaLocales.repaint();
            setCantidades(getCantidadTotalReponer());
        } else {
            FarmaUtility.showMessage(this, "La cantidad a distribuir es menor a la cantidad de locales.", null);
        }
        moveFocusTo(0);
    }

    private int getCantidadTotalReponer() {
        int vSuma = 0;

        for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
            if (FarmaUtility.isInteger("" +
                                       tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdNvo)))
                vSuma +=
                        Integer.parseInt((String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdNvo));
            else {
                vSuma = -1;
                break;
            }
        }

        tblListaLocales.repaint();
        return vSuma;
    }

    private void setCantidades(int pSumaCantidad) {
        lblCantRep.setText("" + (pSumaCantidad));
        lblCantRes.setText("" + (VariablesProducto.vCantidadStock - pSumaCantidad));
    }

    private void funcion_f11() {
        tblListaLocales.changeSelection(0, 3, false, false);

        int vCantReponer = getCantidadTotalReponer();

        if (vCantReponer == -1) {
            vCantReponer = 0;
            FarmaUtility.showMessage(this, "La cantidad es inválida.", null);
            moveFocusTo(0);
            return;
        } else if (vCantReponer == 0) {
            FarmaUtility.showMessage(this, "La cantidad total a reponer a los locales no puede ser cero.", null);
            moveFocusTo(0);
            return;
        } else if (vCantReponer > Integer.parseInt(lblCantStock.getText())) {
            FarmaUtility.showMessage(this, "La cantidad total a reponer por local supera la cantidad de stock.", null);
            moveFocusTo(0);
            return;
        }

        setCantidades(vCantReponer);
        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                        "¿Esta seguro que desea grabar la distribucion a locales?")) {
            String vLocal = "", vProducto = "", vCantidad = "";
            try {
                for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
                    vLocal = (String)tblListaLocales.getValueAt(i, 0);
                    vCantidad =
                            (String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_ProdNvo);
                    DBProducto.insertaProdNuevos(FarmaVariables.vCodGrupoCia, vLocal, VariablesProducto.vCodProducto,
                                                 vCantidad, FarmaVariables.vIdUsu);
                }
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "La operación se realizó satisfactoriamente.", null);
                cerrarVentana(false);
            } catch (SQLException sql) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error: no se puede grabar la distribución a locales.", null);
                FarmaUtility.moveFocusJTable(tblListaLocales);
                moveFocusTo(0);
                log.error("", sql);
            }
        } else {
            FarmaUtility.showMessage(this, "Se canceló la operación", null);
            moveFocusTo(0);
        }
    }

}
