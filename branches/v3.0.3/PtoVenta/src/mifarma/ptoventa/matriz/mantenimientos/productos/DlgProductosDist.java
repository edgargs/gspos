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

import java.util.ArrayList;

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

import mifarma.common.FarmaColumnData;
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
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProductosDist.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      13.11.2007   Creación<br>
 * <br>
 * @author Jorge Luis Cortez Alvarez<br>
 * @version 1.0<br>1
 *
 */

public class DlgProductosDist extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProductosDist.class);

    private JFrame myParentFrame;
    private FarmaTableModel tableModel;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JTextFieldSanSerif txtCantidad;
    private final int FILTRO = 2;


    private ArrayList arrayDescripcion = new ArrayList();

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
    private JLabelFunction lblFAceptar2 = new JLabelFunction();
    private JTextFieldSanSerif txtCantStock = new JTextFieldSanSerif();
    private JButtonLabel btnLista1 = new JButtonLabel();
    private JLabelFunction lblFAceptar3 = new JLabelFunction();

    //Agregado por DVELIZ 19.09.08
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    public DlgProductosDist() {
        this(null, "", false);
    }

    public DlgProductosDist(JFrame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(833, 446));
        this.setDefaultCloseOperation(0);
        this.setTitle("Locales por Producto");
        scrLocales.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblCantRep.setBounds(new Rectangle(540, 10, 75, 20));
        lblCantRep.setFont(new Font("Tahoma", 1, 13));
        lblCantRep.setText("0");
        lblCantRep_.setText("Cantidad total a reponer :");
        lblCantRep_.setBounds(new Rectangle(380, 10, 145, 20));
        lblCantRep_.setFont(new Font("SansSerif", 1, 11));
        lblCantRep_.setForeground(Color.white);
        lblCantRes_.setText("Cantidad restante :");
        lblCantRes_.setBounds(new Rectangle(380, 30, 105, 20));
        lblCantRes_.setFont(new Font("SansSerif", 1, 11));
        lblCantRes_.setForeground(Color.white);
        pnlTitle2.setBounds(new Rectangle(5, 65, 820, 20));
        lblCantStock_.setText("Stock Almacen:");
        lblCantStock_.setBounds(new Rectangle(5, 0, 90, 20));
        lblCantStock.setText("lblCantStock");
        lblCantStock.setBounds(new Rectangle(95, 0, 95, 20));
        lblCantStock.setFont(new Font("SansSerif", 1, 14));
        lblCantRes.setText("0");
        lblCantRes.setBounds(new Rectangle(540, 30, 75, 20));
        lblCantRes.setFont(new Font("Tahoma", 1, 13));
        lblCantRes.setForeground(Color.green);
        jPanelTitle2.setBounds(new Rectangle(5, 335, 820, 20));
        lblCantLocales.setBounds(new Rectangle(780, 0, 35, 20));
        lblCantLocales_.setBounds(new Rectangle(655, 0, 120, 20));
        lblCantLocales_.setText("Cantidad de Locales:");
        lblFAceptar2.setBounds(new Rectangle(455, 360, 155, 20));
        lblFAceptar2.setText("[ F8 ] Quitar Distribucion");
        //Agregado por DVELIZ 19.09.08
        lblF2.setBounds(new Rectangle(5, 360, 140, 20));
        lblF2.setText("[ F2 ] Mostrar Todos");
        lblF2.setToolTipText("null");
        jLabelFunction1.setBounds(new Rectangle(5, 385, 140, 20));
        jLabelFunction1.setText("[ F3 ] Ver Historico");
        txtCantStock.setBounds(new Rectangle(295, 0, 65, 20));
        txtCantStock.setLengthText(6);
        txtCantStock.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantStock_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantStock_keyTyped(e);
            }
        });
        btnLista1.setText("Stock a Trabajar:");
        btnLista1.setBounds(new Rectangle(195, 0, 100, 20));
        btnLista1.setMnemonic('S');
        btnLista1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista1_actionPerformed(e);
            }
        });
        lblFAceptar3.setBounds(new Rectangle(165, 360, 110, 20));
        lblFAceptar3.setText("[ F5 ] Ordenar");
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblFAceptar1.setBounds(new Rectangle(285, 360, 160, 20));
        lblFAceptar1.setText("[ F7 ] Distribuir Cantidad");
        jPanelHeader1.setBounds(new Rectangle(5, 5, 820, 60));
        lblProd.setText("Producto :");
        lblProd.setBounds(new Rectangle(30, 10, 70, 20));
        lblUnid.setText("Unidad Pres :");
        lblUnid.setBounds(new Rectangle(30, 30, 80, 20));
        lblProducto.setText("lblProducto");
        lblProducto.setBounds(new Rectangle(120, 10, 245, 20));
        lblUnidadP.setText("lblUnidadP");
        lblUnidadP.setBounds(new Rectangle(120, 30, 245, 20));

        scrLocales.setBounds(new Rectangle(5, 110, 820, 225));
        tblListaLocales.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLocales_keyPressed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(5, 90, 820, 20));
        btnLista.setText("Listado de Locales");
        btnLista.setBounds(new Rectangle(5, 0, 115, 20));
        btnLista.setMnemonic('L');

        lblFCerrar.setBounds(new Rectangle(730, 360, 95, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        lblFAceptar.setBounds(new Rectangle(620, 360, 105, 20));
        lblFAceptar.setText("[ F11 ] Aceptar");
        jPanelHeader1.add(lblUnidadP, null);
        jPanelHeader1.add(lblProducto, null);
        jPanelHeader1.add(lblUnid, null);
        jPanelHeader1.add(lblProd, null);
        jPanelHeader1.add(lblCantRep_, null);
        jPanelHeader1.add(lblCantRep, null);
        jPanelHeader1.add(lblCantRes, null);
        jPanelHeader1.add(lblCantRes_, null);
        pnlTitle2.add(btnLista1, null);
        pnlTitle2.add(txtCantStock, null);
        pnlTitle2.add(lblCantStock, null);
        pnlTitle2.add(lblCantStock_, null);

        //Agregado por DVELIZ 19.09.08

        jPanelTitle2.add(lblCantLocales_, null);
        jPanelTitle2.add(lblCantLocales, null);
        pnlBlanco.add(jLabelFunction1, null);
        pnlBlanco.add(lblFAceptar3, null);
        pnlBlanco.add(lblFAceptar2, null);
        pnlBlanco.add(lblF2, null);
        pnlBlanco.add(jPanelTitle2, null);
        pnlBlanco.add(pnlTitle2, null);
        pnlBlanco.add(jPanelHeader1, null);
        pnlBlanco.add(lblFAceptar1, null);
        pnlBlanco.add(lblFAceptar, null);
        pnlBlanco.add(lblFCerrar, null);
        jPanelTitle1.add(btnLista, null);
        pnlBlanco.add(jPanelTitle1, null);
        scrLocales.getViewport().add(tblListaLocales, null);
        pnlBlanco.add(scrLocales, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        lblProducto.setText(VariablesProducto.cCodProd + " - " + VariablesProducto.cDescProd);
        lblUnidadP.setText(VariablesProducto.cUnidPres);
        //txtCantStock.setText(""+VariablesProducto.vCantStock);
        lblCantStock.setText("" + VariablesProducto.vCantStock);
        lblCantRes.setText("" + VariablesProducto.vCantStock);
        initTable();
    }

    private void initTable() {
        resolverMesesUnidVend();
        tableModel =
                new FarmaTableModel(VariablesProducto.columnsListaProdLocal, VariablesProducto.defaultValuesListaProdLocal,
                                    0, VariablesProducto.editableListaLocales_Prod, null);
        // FarmaUtility.initSimpleList(tblListaLocales, tableModel,VariablesProducto.columnsListaProdLocal);

        rowEditorModel = new FarmaRowEditorModel();
        tblListaLocales.setAutoCreateColumnsFromModel(false);
        tblListaLocales.setModel(tableModel);
        tblListaLocales.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tblListaLocales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = new TableColumn(k, VariablesProducto.columnsListaProdLocal[k].m_width);
            tblListaLocales.addColumn(column);
        }
        tblListaLocales.setRowEditorModel(rowEditorModel);
        cargaCampos();

        if (VariablesProducto.vTipoParametros) { //JCORTEZ 11.09.08 mant parametros
            lblCantRep.setVisible(false);
            lblCantRes.setVisible(false);
            lblCantRep_.setVisible(false);
            lblCantRes_.setVisible(false);
        }

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtCantStock);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblListaLocales);
        moveFocusTo(tblListaLocales.getSelectedRow());
        arrayDescripcion.clear();
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
        //moveFocusTo(tblListaLocales.getSelectedRow());
    }

    /**
     * Se agrega los eventos al TextField
     * */
    private void addKeyListenerToTextField(final JTextField pJTextField, final String pTipoCampo) {
        pJTextField.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                FarmaUtility.admitirDigitos(pJTextField, e);
            }

            public void keyPressed(KeyEvent e) {
                //log.debug(pJTextField.getText());
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if ((tblListaLocales.getSelectedRow() + 1) == tblListaLocales.getRowCount()) {
                        FarmaUtility.setearRegistro(tblListaLocales, 0, null, 0);
                        log.debug("Ultima regitro...");
                    } else {
                        FarmaUtility.setearRegistro(tblListaLocales, tblListaLocales.getSelectedRow() + 1, null, 0);
                    }
                } else
                    tblListaLocales_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {

                // if(Integer.parseInt(txtCantStock.getText().trim())<=VariablesProducto.vCantStock&&
                //   ){
                //FarmaUtility.showMessage(this,"Debe ingresar una cantidad menor o igual al Stock real que existe en almacen. Verificar¡¡¡",txtCantStock);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tblListaLocales.changeSelection(tblListaLocales.getSelectedRow(),
                                                    ConstantsProducto.columnEditable_Cantidad_Distribu, false, false);
                    int vCantidad = getCantidadTotalReponer();
                    //if(vCantidad!=-1)
                    if (vCantidad >= 0)
                        setCantidades(vCantidad);
                    else {
                        log.debug("vacio");
                        if (e.getKeyCode() == KeyEvent.VK_UP) {
                            if (Integer.parseInt((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() +
                                                                                    1,
                                                                                    ConstantsProducto.columnEditable_Cantidad_Distribu)) <
                                0 ||
                                ((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() + 1, ConstantsProducto.columnEditable_Cantidad_Distribu)).equals(" ")) {
                                tblListaLocales.setValueAt("0", tblListaLocales.getSelectedRow() + 1,
                                                           ConstantsProducto.columnEditable_Cantidad_Distribu);
                            }
                        } else {
                            if (Integer.parseInt((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() -
                                                                                    1,
                                                                                    ConstantsProducto.columnEditable_Cantidad_Distribu)) <
                                0 ||
                                ((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() + 1, ConstantsProducto.columnEditable_Cantidad_Distribu)).equals(" ")) {
                                tblListaLocales.setValueAt("0", tblListaLocales.getSelectedRow() - 1,
                                                           ConstantsProducto.columnEditable_Cantidad_Distribu);
                            }
                        }
                        setCantidades(0);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {

                    int vCantidad = getCantidadTotalReponer();
                    //if(vCantidad!=-1)
                    if (vCantidad >= 0)
                        setCantidades(vCantidad);
                    else {
                        log.debug("vacio");
                        if (e.getKeyCode() == KeyEvent.VK_UP) {
                            if (Integer.parseInt((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() +
                                                                                    1,
                                                                                    ConstantsProducto.columnEditable_Cantidad_Distribu)) <
                                0 ||
                                ((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() + 1, ConstantsProducto.columnEditable_Cantidad_Distribu)).equals("")) {
                                tblListaLocales.setValueAt("0", tblListaLocales.getSelectedRow() + 1,
                                                           ConstantsProducto.columnEditable_Cantidad_Distribu);
                            }
                        } else {
                            if (Integer.parseInt((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() -
                                                                                    1,
                                                                                    ConstantsProducto.columnEditable_Cantidad_Distribu)) <
                                0 ||
                                ((String)tblListaLocales.getValueAt(tblListaLocales.getSelectedRow() + 1, ConstantsProducto.columnEditable_Cantidad_Distribu)).equals("")) {
                                tblListaLocales.setValueAt("0", tblListaLocales.getSelectedRow() - 1,
                                                           ConstantsProducto.columnEditable_Cantidad_Distribu);
                            }
                        }
                        setCantidades(0);
                    }

                }
                //}
            }
        });
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            cargaListaLocales("X");
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            mostrarHistorico();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {

            tblListaLocales.moveColumn(0, 0);
            ordenar();

        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            //if(!VariablesProducto.vTipoParametros){//JCORTEZ 11.09.08 mant parametros
            // if(!VariablesProducto.vTipoValidarFiltro.equalsIgnoreCase(ConstantsProducto.listAlmac)){
            if (isNumeric(txtCantStock.getText().trim())) {
                if (txtCantStock.getText().trim().equalsIgnoreCase("")) {
                    FarmaUtility.showMessage(this, "Debe ingresar una cantidad para distribuir a los locales",
                                             txtCantStock);
                } else {
                    if (!txtCantStock.getText().trim().equalsIgnoreCase("")) {
                        if (Integer.parseInt(txtCantStock.getText().trim()) < 1) {
                            FarmaUtility.showMessage(this, "Debe ingresar una cantidad mayor a cero", txtCantStock);
                        } else {
                            funcion_f7();
                        }
                    } else {
                        FarmaUtility.showMessage(this, "Debe ingresar una cantidad para distribuir", txtCantStock);
                    }
                }
                /*}else{
            FarmaUtility.showMessage(this,"Esta opcion no esta habilitada.",txtCantStock);
       }*/
                // }
            } else {
                FarmaUtility.showMessage(this, "Debe ingresar valores numericos solamente", txtCantStock);
                txtCantStock.setText("");
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            //if(!VariablesProducto.vTipoParametros){//JCORTEZ 11.09.08 mant parametros
            //if(!VariablesProducto.vTipoValidarFiltro.equalsIgnoreCase(ConstantsProducto.listAlmac)){
            funcionF8();
            /*}else{
        FarmaUtility.showMessage(this,"Esta opcion no esta habilitada.",txtCantStock);
       }*/
            // }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            // if(!VariablesProducto.vTipoParametros){//JCORTEZ 11.09.08 mant parametros
            //if(!VariablesProducto.vTipoValidarFiltro.equalsIgnoreCase(ConstantsProducto.listAlmac)){
            funcion_f11();
            /*}else{
       FarmaUtility.showMessage(this,"Esta opcion no esta habilitada.",txtCantStock);
      }*/
            //}
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void btnLista1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantStock);
    }

    private void txtCantStock_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantStock, e);
    }

    private void txtCantStock_keyPressed(KeyEvent e) {

        /*if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {*/
        /*if(!txtCantStock.getText().trim().equalsIgnoreCase("")){
    if(Integer.parseInt(txtCantStock.getText().trim())<0){
    FarmaUtility.showMessage(this,"Debe ingresar una cantidad mayor a cero",txtCantStock);
    }else if(Integer.parseInt(txtCantStock.getText().trim())>VariablesProducto.vCantStock){
     FarmaUtility.showMessage(this,"Debe ingresar una cantidad menor o igual al Stock real que existe en almacen. Verificar¡¡¡",txtCantStock);
    }else{
   // lblCantRes.setText(""+txtCantStock.getText().trim());
    //FarmaUtility.moveFocusJTable(tblListaLocales);
    chkKeyPressed(e);
    }
    }else{
    FarmaUtility.showMessage(this,"Debe ingresar una cantidad para distribuir",txtCantStock);
    }
    //}*/
        chkKeyPressed(e);
    }


    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * Se creal el text field para agregarlo al listado
     * */
    private void createJTextField(JTextField pJTextField, int pRow, String pTipoCampo) {
        addKeyListenerToTextField(pJTextField, pTipoCampo);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /**
     *Insercion de un caja de texto con solo 8 digitos
     * */
    private void getTxtCantidad(int pRow, String pTipoCampo) {
        txtCantidad = new JTextFieldSanSerif();
        txtCantidad.setLengthText(6);
        createJTextField(txtCantidad, pRow, pTipoCampo);
    }

    /**
     * se va creando un campo editable por registro del listado
     * */
    private void setTipoCampo() {
        for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
            getTxtCantidad(i,
                           (String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_Distribu));
        }
    }

    /**
     * cargamos el listado y creamos los editable en el campos indicado : 6
     * */
    private void cargaCampos() {
        cargaListaLocales("X");
        setTipoCampo();
        //nos posicionamos el campo editable del primer registro
        FarmaUtility.setearPrimerRegistro(tblListaLocales, txtCantidad,
                                          ConstantsProducto.columnEditable_Cantidad_Distribu);
        //si existe el producto en el pedido
        obtenerCantidadProducto(VariablesProducto.cCodProd);

        if (arrayDescripcion.size() > 0) {
            /*String local="";
    log.debug(""+arrayDescripcion);
    for (int i=0; i<tblListaLocales.getRowCount(); i++){
    local="";
    log.debug(""+arrayDescripcion.size());
    log.debug(""+tblListaLocales.getRowCount());
    if(arrayDescripcion.size()==tblListaLocales.getRowCount()){
      local=((String) ((ArrayList) arrayDescripcion.get(i)).get(0)).trim();
      log.debug("local seteado :"+local);
      log.debug("local array :"+FarmaUtility.getValueFieldJTable(tblListaLocales,i,0));
      if(local.trim().equalsIgnoreCase(FarmaUtility.getValueFieldJTable(tblListaLocales,i,0))){
        tblListaLocales.setValueAt(((String) ((ArrayList) arrayDescripcion.get(i)).get(1)).trim(),i,ConstantsProducto.columnEditable_Cantidad_Distribu);
      log.debug("local seteado :"+local);
      }else{
        tblListaLocales.setValueAt("0",i,ConstantsProducto.columnEditable_Cantidad_Distribu);
      }
    }else{
    tblListaLocales.setValueAt("0",i,ConstantsProducto.columnEditable_Cantidad_Distribu);
    }
   }*/

        } else {
            if (!VariablesProducto.vTipoParametros) { //JCORTEZ 11.09.08 mant parametros
                //setemos con cero todos los registros
                if (tblListaLocales.getRowCount() > 0) {
                    for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
                        tblListaLocales.setValueAt("0", i, ConstantsProducto.columnEditable_Cantidad_Distribu);
                    }
                }
            }

        }

        tblListaLocales.repaint();
        btnLista.doClick();
    }

    /**
     * Se carga los locales por producto para su distribuion
     * */
    private void cargaListaLocales(String TipLocal) {
        try {
            //lblCantStock.setText(""+VariablesProducto.vCantStock);
            //txtCantStock.setText(""+VariablesProducto.vCantStock);
            DBProducto.cargaListaLocalesProd(tableModel, VariablesProducto.cCodProd, TipLocal);
            if (tblListaLocales.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaLocales, tableModel, FILTRO, FarmaConstants.ORDEN_DESCENDENTE);
            }
            lblCantLocales.setText("" + tblListaLocales.getRowCount());
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", tblListaLocales);
        }
    }

    /**
     * Se mueve el foco donde se se indica
     * */
    private void moveFocusTo(int pRow) {
        FarmaUtility.setearRegistro(tblListaLocales, pRow, txtCantidad,
                                    ConstantsProducto.columnEditable_Cantidad_Distribu);
        tblListaLocales.changeSelection(pRow, ConstantsProducto.columnEditable_Cantidad_Distribu, false, false);
    }


    /**
     * Se distribuyen las cantidades a los locales
     * */
    private void funcion_f7() {
        tblListaLocales.changeSelection(0, 1, false, false);

        //int cantStock = VariablesProducto.vCantStock;
        int cantStock = Integer.parseInt(txtCantStock.getText().trim());
        int cantidad = cantStock;

        /*if(Integer.parseInt(txtCantStock.getText().trim())>VariablesProducto.vCantStock){
      FarmaUtility.showMessage(this,"Debe ingresar una cantidad menor o igual al Stock real que existe en almacen. Verificar¡¡¡",txtCantStock);
    }else{*/
        /* if((cantidad*tblListaLocales.getRowCount())<Integer.parseInt(lblCantStock.getText().trim())){//cantidad menor que el Stock*/
        if (tblListaLocales.getRowCount() > 0) {
            for (int i = 0; i < tblListaLocales.getRowCount(); i++)
                tblListaLocales.setValueAt("" + cantidad, i, ConstantsProducto.columnEditable_Cantidad_Distribu);
            tblListaLocales.repaint();
            setCantidades(getCantidadTotalReponer());
            moveFocusTo(0);
        }
        /* }else{

      funcionF8();
      tblListaLocales.changeSelection(0,1,false,false);
      int cant_loc=tblListaLocales.getRowCount();
      int row=0;

      for (int i=0; i<cant_loc;i++){
      log.debug("registro :"+i);
      log.debug("condicion :"+(cantidad*i)+"<"+Integer.parseInt(lblCantStock.getText().trim()));
        if((cantidad*i)+cantidad<=Integer.parseInt(lblCantStock.getText().trim())){
        log.debug("set registro -->"+i);
        tblListaLocales.setValueAt(""+cantidad,i,ConstantsProducto.columnEditable_Cantidad_Distribu);
        row=i;
        }
      }

      tblListaLocales.repaint();
      setCantidades(getCantidadTotalReponer());
      log.debug("Total a Reponer :"+getCantidadTotalReponer());
      FarmaUtility.showMessage(this,"No se pudo distribuir la cantidad para todos los locales. Verifique¡¡¡",null);
      moveFocusTo(row);

    }*/
        // }
    }

    /**
     * Se obtiena la cantidad total del stock distribuido
     * */
    private int getCantidadTotalReponer() {
        int vSuma = 1;

        for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
            if (isNumeric((String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_Distribu))) {
                if (FarmaUtility.isInteger("" +
                                           tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_Distribu))) {
                    if (Integer.parseInt((String)tblListaLocales.getValueAt(i,
                                                                            ConstantsProducto.columnEditable_Cantidad_Distribu)) <
                        0) {
                        vSuma = -1;
                        break;
                    } else
                        vSuma +=
                                Integer.parseInt((String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_Distribu));
                }
            } else {
                //moveFocusTo(i);
                //FarmaUtility.moveFocusJTable(tblListaLocales);
                vSuma = 0;
                break;
            }
        }

        tblListaLocales.repaint();
        return vSuma;
    }

    /**
     * Se actualiza el stock luego del calculo
     * */
    private void setCantidades(int pSumaCantidad) {
        lblCantRep.setText("" + (pSumaCantidad));
        if (!lblCantStock.getText().trim().equalsIgnoreCase("")) {
            //lblCantRes.setText(""+(Integer.parseInt(txtCantStock.getText().trim())-pSumaCantidad));
            lblCantRes.setText("" + (Integer.parseInt(lblCantStock.getText().trim()) - pSumaCantidad));
        }

    }

    /**
     * se quita la distribucion de todos los locales
     * */
    private void funcionF8() {

        tblListaLocales.changeSelection(0, 1, false, false);
        if (tblListaLocales.getRowCount() > 0) {
            for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
                tblListaLocales.setValueAt("0", i, ConstantsProducto.columnEditable_Cantidad_Distribu);
            }
            tblListaLocales.repaint();
            btnLista.doClick();
        }
        moveFocusTo(0);
    }


    /**
     * Se guarda la distribucion en una tabla temporal.
     * */
    private void funcion_f11() {
        VariablesProducto.estadoAcccion = false;
        //tblListaLocales.changeSelection(0,3,false,false);

        //JCORTEZ 11.09.08 mant parametros
        //FarmaUtility.moveFocus(tblListaLocales);
        int vCantReponer = getCantidadTotalReponer();
        log.debug("Total Distribucion " + vCantReponer);
        /*if (vCantReponer>Integer.parseInt(lblCantStock.getText().trim()))
        {
          FarmaUtility.showMessage(this,
                                   "Debe ingresar una cantidad menor o igual al Stock real que existe en almacen. Verificar¡¡¡",
                                  null);
          moveFocusTo(0);
          FarmaUtility.moveFocusJTable(tblListaLocales);
          return;
        }*/

        //JCORTEZ 11.09.08 mant parametros
        if (vCantReponer == 0) {
            vCantReponer = 0;
            FarmaUtility.showMessage(this, "Existen valores no numericos en la distribucion. Verificar!!!",
                                     txtCantidad);
            FarmaUtility.moveFocusJTable(tblListaLocales);
            //moveFocusTo(0);
            return;
        } else if (vCantReponer < 0) {
            vCantReponer = 0;
            FarmaUtility.showMessage(this, "La cantidad ingresada no puede tener valor negativo. Verifique!!!",
                                     txtCantidad);
            moveFocusTo(0);
            FarmaUtility.moveFocusJTable(tblListaLocales);
            return;
        }
        /*else if(vCantReponer==0)
    {
      if(ExistTemporalProd()){
      VariablesProducto.estadoAcccion=true;
      if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro de no realizar ninguna distribucion para este producto?"))
        {
        eliminarDistribucion();
        cerrarVentana(true);
        return;

        }
      }else{
        FarmaUtility.showMessage(this,"La cantidad total a reponer a los locales no puede ser cero.",tblListaLocales);
        moveFocusTo(0);
        FarmaUtility.moveFocusJTable(tblListaLocales);
        return;
      }
    }*/


        /*else if(vCantReponer>Integer.parseInt(txtCantStock.getText()))
    {
      FarmaUtility.showMessage(this,"La cantidad total a reponer por local supera la cantidad a trabajar.",null);
      moveFocusTo(0);
      FarmaUtility.moveFocusJTable(tblListaLocales);
      return;
    }*/

        /*setCantidades(vCantReponer);*/

        if (!VariablesProducto.estadoAcccion) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Esta seguro que desea grabar los nuevos PVM en locales?")) {
                FarmaUtility.moveFocus(txtCantStock);
                moveFocusTo(0);

                String vLocal = "", vProducto = "", vStockLocal = "", vCant = "";
                int vCantidad = 0;
                try {
                    // String cantItems=tblListaLocales.getRowCount()+"";
                    //String cantProds=getCantidadTotalReponer()+"";

                    for (int i = 0; i < tblListaLocales.getRowCount(); i++) {
                        vLocal = (String)tblListaLocales.getValueAt(i, 0);
                        /*if(!((String)tblListaLocales.getValueAt(i,ConstantsProducto.columnEditable_Cantidad_Distribu)).equals(""))
          vCantidad  =Integer.parseInt((String)tblListaLocales.getValueAt(i,ConstantsProducto.columnEditable_Cantidad_Distribu));
          else
          vCantidad=0;*/

                        vStockLocal = ((String)tblListaLocales.getValueAt(i, 2)).trim();

                        if (!((String)tblListaLocales.getValueAt(i,
                                                                 ConstantsProducto.columnEditable_Cantidad_Distribu)).equals(""))
                            vCant =
                                    (String)tblListaLocales.getValueAt(i, ConstantsProducto.columnEditable_Cantidad_Distribu);
                        else
                            vCant = "0";

                        // if(vCantidad>0){
                        log.debug("INGRESOS DETALLE--> :" + VariablesProducto.vNumPedidoDistGenerado_aux + "/ " +
                                  vLocal + "/ " + VariablesProducto.cCodProd + "/ " + vStockLocal + "/ " + vCant);

                        //JCORTEZ 11.09.08 mant parametros
                        //se genera el numero auXiliar, ya que no hay pedidos temporales pendientes
                        /*DBProducto.insertarDetallePedido(VariablesProducto.vNumPedidoDistGenerado_aux,
                                           vLocal,
                                           VariablesProducto.cCodProd,
                                           vStockLocal,
                                           vCant);*/

                        //ingresando paramatros  PVM
                        DBProducto.insertarParametroRepLocal(VariablesProducto.vNumPedidoDistGenerado_aux, vLocal,
                                                             VariablesProducto.cCodProd, vStockLocal, vCant);
                        FarmaUtility.aceptarTransaccion();
                        // }
                    }

                    FarmaUtility.showMessage(this, "La operación se realizó satisfactoriamente.", txtCantidad);
                    cerrarVentana(true);
                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this,
                                             "Error: no se puede grabar la distribución a locales." + sql.getMessage(),
                                             null);
                    FarmaUtility.moveFocusJTable(tblListaLocales);
                    moveFocusTo(0);
                    log.error("", sql);
                }
            } else {
                //FarmaUtility.showMessage(this,"Se canceló la operación",null);
                //moveFocusTo(0);
            }
        }
    }


    /**
     * Obtiene los meses de Unid. Vend.
     */
    private void resolverMesesUnidVend() {
        try {
            String[] auxArray = new String[4]; //vienen de los 4 solo 3
            ArrayList aux = new ArrayList();
            ArrayList auxList;
            int mes;
            DBProducto.getMesesUnidVend(aux);
            log.debug("MESES :" + aux);
            if (aux.size() > 0) {
                for (int i = 0; i < aux.size(); i++) {
                    auxList = (ArrayList)aux.get(i);
                    auxArray[i] = auxList.get(1).toString();
                }
                VariablesProducto.mesesUnidVend = auxArray;
                //
                /*
        FarmaColumnData auxColumnData[] = {
                    new FarmaColumnData("Código", 60, JLabel.LEFT),
                    new FarmaColumnData("Local", 220, JLabel.LEFT),
                    new FarmaColumnData("Stock", 70, JLabel.RIGHT),
                    new FarmaColumnData("Stock Transito", 83, JLabel.RIGHT),
                    new FarmaColumnData(VariablesProducto.mesesUnidVend[0], 60, JLabel.RIGHT),
                    new FarmaColumnData(VariablesProducto.mesesUnidVend[1], 60, JLabel.RIGHT),
                    new FarmaColumnData(VariablesProducto.mesesUnidVend[2], 60, JLabel.RIGHT),
                    new FarmaColumnData("Reponer", 65, JLabel.RIGHT)};*/
                //Modificado por DVELIZ 19.09.08
                FarmaColumnData auxColumnData[] = {
                    //Modificado por DVELIZ 19.09.08
                    new FarmaColumnData("Codigo", 50, JLabel.CENTER), new FarmaColumnData("Local", 205, JLabel.LEFT),
                    new FarmaColumnData("Stock", 70, JLabel.RIGHT),
                    new FarmaColumnData("Stock Transito", 83, JLabel.RIGHT),
                    new FarmaColumnData("Ind.Rep.", 83, JLabel.RIGHT),
                    new FarmaColumnData(VariablesProducto.mesesUnidVend[3], 60, JLabel.RIGHT),
                    new FarmaColumnData(VariablesProducto.mesesUnidVend[2], 60, JLabel.RIGHT),
                    new FarmaColumnData(VariablesProducto.mesesUnidVend[1], 60, JLabel.RIGHT),
                    new FarmaColumnData(VariablesProducto.mesesUnidVend[0], 60, JLabel.RIGHT),
                    new FarmaColumnData("Reponer", 70, JLabel.RIGHT) };

                VariablesProducto.columnsListaProdLocal = auxColumnData;
            }
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    /**
     * se obtiene las cantidad de distribucion por local
     * */
    private void obtenerCantidadProducto(String vCodigo) {

        try {
            arrayDescripcion.clear();
            DBProducto.obtieneCantidadProducto(arrayDescripcion, vCodigo);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener las cantidades por local" + sql.getMessage(),
                                     txtCantidad);
        }
    }

    /**
     * se elimina la distribucion del productos en todos los locales
     * */
    private void eliminarDistribucion() {
        try {
            log.debug("producto enviado: " + VariablesProducto.cCodProd);
            DBProducto.eliminaDistribucion(VariablesProducto.cCodProd);
            FarmaUtility.aceptarTransaccion();
            moveFocusTo(0);
            FarmaUtility.showMessage(this, "La operación se realizó satisfactoriamente.", tblListaLocales);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio al eliminar la distribucion del producto", tblListaLocales);
        }
    }


    /**
     * Valida la existencia de productos trabajados
     * */
    private boolean ExistTemporalProd() {

        boolean existe = false;
        try {
            String revisado = DBProducto.getExistProdTemporal(VariablesProducto.cCodProd);
            log.debug("RETORNO :" + revisado);
            if (revisado.equalsIgnoreCase("TRUE")) {
                existe = true;
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un al verificar la existencia de pedidos temporales",
                                     tblListaLocales);
        }
        return existe;
    }


    /**
     * Se ordena el listado de productos
     * */
    private void ordenar() {

        VariablesProducto.Tipo_Orden = ConstantsProducto.LOCAL;
        DlgOrdenar dlgordenar = new DlgOrdenar(myParentFrame, "", true);
        dlgordenar.setVisible(true);

        if (FarmaVariables.vAceptar) {
            cargaListaLocales(VariablesProducto.Tipo_Local);
            FarmaUtility.ordenar(tblListaLocales, tableModel, Integer.parseInt(VariablesProducto.vColumna),
                                 VariablesProducto.vOrden);
            tblListaLocales.repaint();
        }
    }

    /**
     * Se mostrara el historico del cambios de parametros por local
     * */
    private void mostrarHistorico() {

        DlgHistoricoParamLocal dlgHisto = new DlgHistoricoParamLocal(myParentFrame, "", true);
        dlgHisto.setVisible(true);
        if (FarmaVariables.vAceptar) {
        }
    }


    public static boolean isNumeric(String pcadena) {
        char vCaracter;
        if (pcadena.length() == 0)
            return false;
        for (int i = 0; i < pcadena.length(); i++) {
            vCaracter = pcadena.charAt(i);
            if (Character.isLetter(vCaracter))
                return false;
        }
        return true;
    }


}
