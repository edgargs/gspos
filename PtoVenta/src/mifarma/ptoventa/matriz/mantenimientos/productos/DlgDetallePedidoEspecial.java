package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaJTable;
import mifarma.common.FarmaRowEditorModel;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetallePedidoEspecial extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgDetallePedidoEspecial.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    ArrayList arrayProductos = new ArrayList();
    private String stkProd = "";
    private String indProdCong = "";

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private FarmaJTable tblListaProductos = new FarmaJTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnNuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JPanelTitle pnl2 = new JPanelTitle();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JLabelWhite lblFechaUltimoPedido = new JLabelWhite();
    private JLabelWhite lblStkAlmActual = new JLabelWhite();
    private JLabelWhite lblStkLocalActual = new JLabelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblNumDias = new JLabelWhite();
    private JLabelWhite lbl120d = new JLabelWhite();
    private JLabelWhite lbl90d = new JLabelWhite();
    private JLabelWhite lbl60d = new JLabelWhite();
    private JLabelWhite lbl30d = new JLabelWhite();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelFunction lblPedidoReposicion = new JLabelFunction();


    private FarmaRowEditorModel rowEditorModel;
    private DefaultCellEditor defaultCellEditor;
    private JTextFieldSanSerif txtCantidad;
    private JLabelWhite lblDesLab = new JLabelWhite();
    private JLabelWhite lblFecUltim = new JLabelWhite();
    private JLabelWhite lblLoc = new JLabelWhite();
    private JLabelWhite lblAlmc = new JLabelWhite();
    private JLabelWhite lblRotacion = new JLabelWhite();
    private JLabelWhite lbl120 = new JLabelWhite();
    private JLabelWhite lbl90 = new JLabelWhite();
    private JLabelWhite lbl60 = new JLabelWhite();
    private JLabelWhite lbl30 = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDetallePedidoEspecial() {
        this(null, "", false);
    }

    public DlgDetallePedidoEspecial(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(826, 466));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos Especiales");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(5, 5, 810, 45));
        pnlTitle1.setBounds(new Rectangle(5, 55, 810, 25));
        scrListaProductos.setBounds(new Rectangle(5, 80, 810, 215));
        scrListaProductos.setBackground(new Color(255, 130, 14));
        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaProductos_keyReleased(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(725, 405, 90, 20));
        btnNuscar.setText("Buscar:");
        btnNuscar.setBounds(new Rectangle(15, 15, 55, 15));
        btnNuscar.setMnemonic('B');
        btnNuscar.setVisible(false);
        btnNuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(75, 15, 330, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        txtBuscar.setVisible(false);
        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
        //btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        pnl2.setBounds(new Rectangle(5, 295, 810, 25));
        lblLaboratorio.setText("Laboratorio");
        lblLaboratorio.setBounds(new Rectangle(5, 5, 100, 15));
        lblFechaUltimoPedido.setText("Fec. Ult. Ped.");
        lblFechaUltimoPedido.setBounds(new Rectangle(505, 5, 80, 15));
        lblStkAlmActual.setText("Stk. Almacèn");
        lblStkAlmActual.setBounds(new Rectangle(715, 5, 85, 15));
        lblStkLocalActual.setText("Stk. Local Act.");
        lblStkLocalActual.setBounds(new Rectangle(610, 5, 85, 15));
        lblStkLocalActual.setToolTipText("null");
        jPanelWhite1.setBounds(new Rectangle(5, 320, 810, 20));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelTitle1.setBounds(new Rectangle(355, 345, 460, 25));
        lblNumDias.setText("Num. Dias");
        lblNumDias.setBounds(new Rectangle(5, 5, 80, 15));
        lbl120d.setText("120 d.");
        lbl120d.setBounds(new Rectangle(170, 5, 45, 15));
        lbl90d.setText("90 d.");
        lbl90d.setBounds(new Rectangle(245, 5, 60, 15));
        lbl60d.setText("60 d.");
        lbl60d.setBounds(new Rectangle(325, 5, 55, 15));
        lbl30d.setText("30 d.");
        lbl30d.setBounds(new Rectangle(405, 5, 45, 15));
        jLabelWhite1.setText("jLabelWhite1");
        jLabelWhite1.setBounds(new Rectangle(355, 370, 460, 20));
        jLabelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblF1.setBounds(new Rectangle(5, 405, 130, 20));
        lblF1.setText("[ F1 ] Aprobar (Todos)");
        lblF12.setBounds(new Rectangle(410, 405, 140, 20));
        lblF12.setText("[ F12 ] Rechazar");
        lblF4.setBounds(new Rectangle(145, 405, 140, 20));
        lblF4.setText("[ F4 ] Aprobar Urgente");
        jLabelOrange1.setText("jLabelOrange1");
        jLabelOrange1.setBounds(new Rectangle(635, 20, 79, 15));
        lblPedidoReposicion.setBounds(new Rectangle(390, 5, 210, 25));
        lblPedidoReposicion.setText("PEDIDO REPOSICION");
        lblPedidoReposicion.setFont(new Font("Arial", 1, 12));
        lblDesLab.setBounds(new Rectangle(5, 0, 370, 20));
        lblDesLab.setForeground(Color.black);
        lblDesLab.setToolTipText("null");
        lblDesLab.setText("-");
        lblFecUltim.setBounds(new Rectangle(505, 0, 75, 20));
        lblFecUltim.setForeground(Color.black);
        lblFecUltim.setToolTipText("null");
        lblFecUltim.setText("-");
        lblLoc.setBounds(new Rectangle(610, 0, 80, 20));
        lblLoc.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoc.setForeground(Color.black);
        lblLoc.setText("-");
        lblAlmc.setBounds(new Rectangle(715, 0, 75, 20));
        lblAlmc.setHorizontalAlignment(SwingConstants.CENTER);
        lblAlmc.setForeground(Color.black);
        lblAlmc.setText("-");
        lblRotacion.setBounds(new Rectangle(5, 0, 65, 20));
        lblRotacion.setText("Rotacion");
        lblRotacion.setForeground(Color.black);
        lbl120.setBounds(new Rectangle(155, 0, 60, 20));
        lbl120.setForeground(Color.black);
        lbl120.setText("1");
        lbl120.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90.setBounds(new Rectangle(230, 0, 70, 20));
        lbl90.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90.setForeground(Color.black);
        lbl90.setText("1");
        lbl60.setBounds(new Rectangle(310, 0, 70, 20));
        lbl60.setHorizontalAlignment(SwingConstants.CENTER);
        lbl60.setText("1");
        lbl60.setForeground(Color.black);
        lbl30.setBounds(new Rectangle(395, 0, 60, 20));
        lbl30.setHorizontalAlignment(SwingConstants.CENTER);
        lbl30.setToolTipText("null");
        lbl30.setText("1");
        lbl30.setForeground(Color.black);
        pnl2.add(lblStkLocalActual, null);
        pnl2.add(lblStkAlmActual, null);
        pnl2.add(lblFechaUltimoPedido, null);
        pnl2.add(lblLaboratorio, null);
        jPanelTitle1.add(lbl30d, null);
        jPanelTitle1.add(lbl60d, null);
        jPanelTitle1.add(lbl90d, null);
        jPanelTitle1.add(lbl120d, null);
        jPanelTitle1.add(lblNumDias, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF1, null);
        jLabelWhite1.add(lbl30, null);
        jLabelWhite1.add(lbl60, null);
        jLabelWhite1.add(lbl90, null);
        jLabelWhite1.add(lbl120, null);
        jLabelWhite1.add(lblRotacion, null);
        jContentPane.add(jLabelWhite1, null);
        jContentPane.add(jPanelTitle1, null);
        jPanelWhite1.add(lblAlmc, null);
        jPanelWhite1.add(lblLoc, null);
        jPanelWhite1.add(lblFecUltim, null);
        jPanelWhite1.add(lblDesLab, null);
        jContentPane.add(jPanelWhite1, null);
        jContentPane.add(pnl2, null);
        jContentPane.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(lblPedidoReposicion, null);
        pnlTitle1.add(jLabelOrange1, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlHeader1, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnNuscar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        if (VariablesProducto.vEstPedEpsc.trim().equalsIgnoreCase(ConstantsProducto.EST_PROD_PENDIENTE)) {
            tableModel =
                    new FarmaTableModel(ConstantsProducto.columnsListaDetPedEspeciales, ConstantsProducto.defaultValuesListaDetPedEspeciales,
                                        0, ConstantsProducto.editableListaPedEscDet, null);
            rowEditorModel = new FarmaRowEditorModel();
            tblListaProductos.setAutoCreateColumnsFromModel(false);
            tblListaProductos.setModel(tableModel);
            tblListaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tblListaProductos.setRowEditorModel(rowEditorModel);
            for (int k = 0; k < tableModel.getColumnCount(); k++) {
                TableColumn column = new TableColumn(k, ConstantsProducto.columnsListaDetPedEspeciales[k].m_width);
                tblListaProductos.addColumn(column);
            }
            cargaListaProductos();
            setTipoCampo();

            FarmaUtility.setearPrimerRegistro(tblListaProductos, txtCantidad,
                                              ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC);
            //FarmaUtility.moveFocus(txtCantidad);
            lblF1.setVisible(true);
            lblF4.setVisible(true);
            lblF12.setVisible(true);
        } else {
            tableModel =
                    new FarmaTableModel(ConstantsProducto.columnsListaDetPedEspeciales, ConstantsProducto.defaultValuesListaDetPedEspeciales,
                                        0);
            FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsProducto.columnsListaDetPedEspeciales);
            cargaListaProductos();
            lblF1.setVisible(false);
            lblF4.setVisible(false);
            lblF12.setVisible(false);

            FarmaUtility.moveFocusJTable(tblListaProductos);

        }

    }

    private void cargaListaProductos() {
        try {
            DBProducto.cargaListaProductosEspeciales(tableModel);
            //FarmaUtility.ordenar(tblListaProductos,tableModel,2,FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar los productos : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    /**
     * se va creando un campo editable por registro del listado
     * */
    private void setTipoCampo() {
        for (int i = 0; i < tblListaProductos.getRowCount(); i++) {
            getTxtCantidad(i, (String)tblListaProductos.getValueAt(i, ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC));
        }
    }

    /**
     *Insercion de un caja de texto con solo 6 digitos
     * */
    private void getTxtCantidad(int pRow, String pTipoCampo) {
        txtCantidad = new JTextFieldSanSerif();
        txtCantidad.setLengthText(6);
        //Agregado por DVELIZ 10.10.08
        /*txtCantidad.setInputVerifier(new InputVerifier(){
            public boolean verify(JComponent input) {
                JTextField txtInput = (JTextField)input;
                String strInput = txtInput.getText().trim();
                if(UtilityInventario.validaCerosIzquierda(strInput)){
                    //FarmaUtility.showMessage(new JFrame(), "La cantidad ingresada debe ser entera", txtInput);
                    txtInput.setText("");

                    return false;
                }else{
                    return true;
                }
            }
        });*/

        createJTextField(txtCantidad, pRow, pTipoCampo);
    }

    /**
     * Se creal el text field para agregarlo al listado
     * */
    private void createJTextField(JTextField pJTextField, int pRow, String pTipoCampo) {
        addKeyListenerToTextField(pJTextField, pTipoCampo);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /**
     * Se agrega los eventos al TextField
     * */
    private void addKeyListenerToTextField(final JTextField pJTextField, final String pTipoCampo) {
        ////log.debug("event jtex");
        pJTextField.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                ////log.debug("ESTOY EN TYPED");
                FarmaUtility.admitirDigitos(pJTextField, e);

            }

            public void keyPressed(KeyEvent e) {
                ////log.debug("ESTOY EN PRESSED");
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if ((tblListaProductos.getSelectedRow() + 1) == tblListaProductos.getRowCount()) {
                        FarmaUtility.setearRegistro(tblListaProductos, 0, null, 0);
                        //      //log.debug("Ultima regitro...");
                    } else {
                        FarmaUtility.setearRegistro(tblListaProductos, tblListaProductos.getSelectedRow() + 1, null,
                                                    0);
                    }
                } else
                    chkKeyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                ////log.debug("ESTOY EN RELEASE");
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    int row = tblListaProductos.getSelectedRow();
                    String vCantidad = pJTextField.getText().trim();
                    if (vCantidad.length() == 0) {
                        tblListaProductos.changeSelection(row - 1, ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC, false,
                                                          false);
                        pJTextField.setForeground(Color.RED);
                    } else if (UtilityInventario.validaCerosIzquierda(vCantidad)) {
                        tblListaProductos.changeSelection(row - 1, ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC, false,
                                                          false);
                        pJTextField.setForeground(Color.RED);
                    } else {
                        pJTextField.setForeground(Color.BLACK);
                        tblListaProductos.changeSelection(tblListaProductos.getSelectedRow(),
                                                          ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC, false, false);
                        tblListaProductos_keyReleased(e);

                    }


                } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int row = tblListaProductos.getSelectedRow();
                    String vCantidad = FarmaUtility.getValueFieldJTable(tblListaProductos, row - 1, 8).toString();
                    if (vCantidad.length() == 0) {
                        tblListaProductos.changeSelection(row - 1, ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC, false,
                                                          false);
                        pJTextField.setForeground(Color.RED);
                    } else if (UtilityInventario.validaCerosIzquierda(vCantidad)) {
                        tblListaProductos.changeSelection(row - 1, ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC, false,
                                                          false);
                        pJTextField.setForeground(Color.RED);
                    } else {
                        pJTextField.setForeground(Color.BLACK);
                        tblListaProductos.changeSelection(tblListaProductos.getSelectedRow(),
                                                          ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC, false, false);
                        tblListaProductos_keyReleased(e);

                    }

                }

            }
        });

    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnNuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(tblListaProductos);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        /*FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar,
                                              2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos,
                                                    txtBuscar.getText().trim(),
                                                    1, 2))) {
                    FarmaUtility.showMessage(this,
                                             "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtBuscar);
                    return;
                }
            }
        }*/

        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 1);
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //FarmaUtility.moveFocus(txtBuscar);
        //btnRelacionProductos.doClick();
        if (VariablesProducto.vEstPedEpsc.trim().equalsIgnoreCase(ConstantsProducto.EST_PROD_PENDIENTE)) {
            moveFocusTo(tblListaProductos.getSelectedRow());
            mostrarDetalles(tblListaProductos.getSelectedRow());
        } else {
            FarmaUtility.moveFocusJTable(tblListaProductos);
            mostrarDetalles(tblListaProductos.getSelectedRow());
        }


    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaUtility.moveFocus(null);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            FarmaUtility.moveFocus(tblListaProductos);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (lblF12.isVisible()) {
                //FarmaUtility.setearRegistro(tblListaProductos,
                //                               0, null, 0);

                //FarmaUtility.moveFocus(txtCantidad);
                tableModel.setValueAt(txtCantidad.getText().trim(), tblListaProductos.getSelectedRow(), 8);

                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro de Rechazar el pedido?")) {
                    rechazarPedido();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (lblF4.isVisible()) {
                //FarmaUtility.setearRegistro(tblListaProductos,tblListaProductos.getSelectedRow(), null, 0);
                tableModel.setValueAt(txtCantidad.getText().trim(), tblListaProductos.getSelectedRow(), 8);
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro de Aprobar Ugente?")) {
                    aprobarPedidoUrgente();
                }
            }

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (lblF1.isVisible()) {
                //FarmaUtility.setearRegistro(tblListaProductos,tblListaProductos.getSelectedRow(), null, 0);
                //FarmaUtility.moveFocusJTable(tblListaProductos);
                tableModel.setValueAt(txtCantidad.getText().trim(), tblListaProductos.getSelectedRow(), 8);
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro de Aprobar(Todos)?")) {
                    aprobarPedido();
                }
            }

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

    /**
     * Se mueve el foco donde se se indica
     * */
    private void moveFocusTo(int pRow) {
        FarmaUtility.setearRegistro(tblListaProductos, pRow, txtCantidad,
                                    ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC);
        tblListaProductos.changeSelection(pRow, ConstantsProducto.COLM_EDITABLE_DET_PED_ESPC, false, false);
    }


    private void rechazarPedido() {
        operaAccionPedEspc(VariablesProducto.vCodLocal, VariablesProducto.vNumPedEspecial, tblListaProductos,
                           ConstantsProducto.EST_PROD_RECHAZADA, VariablesProducto.vEstPedEpsc);
    }


    private void aprobarPedidoUrgente() {
        operaAccionPedEspc(VariablesProducto.vCodLocal, VariablesProducto.vNumPedEspecial, tblListaProductos,
                           ConstantsProducto.EST_PROD_CONF_URGENTE, VariablesProducto.vEstPedEpsc);
    }

    private void aprobarPedido() {
        operaAccionPedEspc(VariablesProducto.vCodLocal, VariablesProducto.vNumPedEspecial, tblListaProductos,
                           ConstantsProducto.EST_PROD_CONFIRMADO, VariablesProducto.vEstPedEpsc);

    }
    /*
        VariablesProducto.vCodLocal  = FarmaUtility.getValueFieldArrayList(tblListaPedidos,r,0);
        VariablesProducto.vNumPedEspecial

    public static String EST_PROD_CONFIRMADO = "C";
    public static String EST_PROD_CONF_URGENTE = "U";
    public static String EST_PROD_RECHAZADA = "N";
    public static String EST_PROD_PENDIENTE = "P";
 if (VariablesProducto.vEstPedEpsc.trim().equalsIgnoreCase(ConstantsProducto.EST_PROD_PENDIENTE)) {
}

 * */

    /**
     *
     * @param pCodLocal
     * @param pNumPedido
     * @param pTable
     * @param pProceso
     */
    private void operaAccionPedEspc(String pCodLocal, String pNumPedido, JTable pTable, String pProceso,
                                    String pEstado) {

        if (pEstado.trim().equalsIgnoreCase(ConstantsProducto.EST_PROD_PENDIENTE)) {


            if (validaIngresoProdDetalle(pTable)) {
                if (actualizaEstadoProducto(pCodLocal, pNumPedido, pTable, pProceso)) {
                    try {
                        DBProducto.operaPedidoEspecial(pNumPedido, pCodLocal, pProceso);
                        FarmaUtility.aceptarTransaccion();
                        // Se muestra el mensaje de la accion realizada
                        cerrarVentana(true);
                    } catch (SQLException e) {
                        log.error("", e);
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Error al realizar el proceso al Pedido Especial.", null);
                    }

                }
            } else
                FarmaUtility.showMessage(this, "Debe de procesar cada producto para realizar la accion.", null);

        } else
            FarmaUtility.showMessage(this, "No puede realizar la accion ya que el pedido no esta pendiente.", null);
    }

    private boolean validaIngresoProdDetalle(JTable ptable) {
        String pCantidad = "";
        for (int i = 0; i < ptable.getRowCount(); i++) {
            pCantidad = FarmaUtility.getValueFieldJTable(ptable, i, 8);
            if (pCantidad.trim().length() <= 0)
                return false;
        }
        return true;
    }

    private boolean actualizaEstadoProducto(String pCodLocal, String pNumPed, JTable ptable, String pEstado) {
        boolean pResult = false;
        String pCodProd = "";
        String pCantidad = "";
        try {

            ////log.debug("ss");
            for (int i = 0; i < ptable.getRowCount(); i++) {
                pCodProd = FarmaUtility.getValueFieldJTable(ptable, i, 0);
                pCantidad = FarmaUtility.getValueFieldJTable(ptable, i, 8);
                DBProducto.actualizaProdDetEspecial(pCodLocal, pNumPed, pCodProd, pCantidad, pEstado);
            }

            pResult = true;
        } catch (SQLException e) {
            log.error("", e);
            pResult = false;
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al realizar el proceso al detalle del Pedido Especial.", null);
        }

        return pResult;
    }


    private void tblListaProductos_keyReleased(KeyEvent e) {
        int row = tblListaProductos.getSelectedRow();
        /*if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(tblListaProductos.getSelectedRowCount()>0) row = row-1;
             String vCantidad = FarmaUtility.getValueFieldJTable(tblListaProductos, row, 8).toString();
             if(UtilityInventario.validaCerosIzquierda(vCantidad)){
                 FarmaUtility.showMessage(this, "La cantidad ingresada debe ser entera", tblListaProductos);

             }else{
                 if(row>-1)
                     mostrarDetalles(row);
             }
         }*/
        if (row > -1)
            mostrarDetalles(row);
    }

    private void mostrarDetalles(int row) {
        String CodProd = tblListaProductos.getValueAt(row, 0).toString();
        ArrayList aux = new ArrayList();

        try {
            DBProducto.getDatosProducto(CodProd, aux);
            //log.debug("Datos del producto "+aux);
            if (aux.size() > 0) {
                lblDesLab.setText(((String)((ArrayList)aux.get(0)).get(1)).trim());
                lblFecUltim.setText(((String)((ArrayList)aux.get(0)).get(2)).trim());
                lblLoc.setText(((String)((ArrayList)aux.get(0)).get(3)).trim());
                lblAlmc.setText(((String)((ArrayList)aux.get(0)).get(4)).trim());
                lbl120.setText(((String)((ArrayList)aux.get(0)).get(5)).trim());
                lbl90.setText(((String)((ArrayList)aux.get(0)).get(6)).trim());
                lbl60.setText(((String)((ArrayList)aux.get(0)).get(7)).trim());
                lbl30.setText(((String)((ArrayList)aux.get(0)).get(8)).trim());
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener datos del producto.", null);
        }


    }
}
