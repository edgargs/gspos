package mifarma.ptoventa.recepcionCiega;


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

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgGuiasPendientes.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ    16.11.2009   Creación<br>
 * <br>
 * @author  JORGE LUIS CORTEZ ALVAREZ <br>
 * @version 1.0<br>
 *
 */
public class DlgGuiasPendientes extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgGuiasPendientes.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelGuias;
    private FarmaTableModel tableModelGuiasAso;
    //FarmaTableModel tableModelGuias;
    ArrayList arrayEquipos = new ArrayList();
    private String stkProd = "";
    private String indProdCong = "";
    private boolean todos = false;
    private JTable myJTable;

    private final int COL_TIP = 1;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JButtonLabel btnNuscar = new JButtonLabel();
    private JTextFieldSanSerif txtLocal = new JTextFieldSanSerif();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JScrollPane scrListaLocales = new JScrollPane();
    private JTable tblListaGuias = new JTable();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JEditorPane epnMensaje = new JEditorPane();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblCant = new JLabelWhite();

    //**
    //JMIRANDA 19.03.2010
    private static final int COL_CHECK = 0;
    private static final int COL_GUIA = 1;
    private static final int COL_ENTREGA = 2;
    private static final int COL_FECHA = 3;
    private static final int COL_NUM_NOTA = 4;
    private static final int COL_ESTADO = 5;
    private static final int COL_PAG = 6;
    private static final int COL_ITEMS = 7;
    private static final int COL_PRODS = 8;

    private static final int COL_2_GUIA = 0;
    private static final int COL_2_ENTREGA = 1;
    private static final int COL_2_FECHA = 2;
    private static final int COL_2_NUM_NOTA = 3;
    private static final int COL_2_ESTADO = 4;
    private static final int COL_2_NUM_GUIA_REM = 5;
    private static final int COL_2_PAG = 6;
    private static final int COL_2_ITEMS = 7;
    private static final int COL_2_PRODS = 8;

    //**

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgGuiasPendientes() {
        this(null, "", false);
    }

    public DlgGuiasPendientes(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            //FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(441, 472));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(0);
        this.setTitle("Guías Pendientes");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(5, 10, 425, 50));
        pnlTitle1.setBounds(new Rectangle(5, 135, 425, 25));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(340, 420, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(235, 420, 100, 20));
        lblF5.setText("[ F5 ]  Todos/ Ninguno");
        lblF5.setBounds(new Rectangle(290, 395, 140, 20));
        lblF5.setToolTipText("null");
        btnNuscar.setText("Buscar:");
        btnNuscar.setBounds(new Rectangle(15, 15, 55, 15));
        btnNuscar.setMnemonic('B');
        btnNuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNuscar_actionPerformed(e);
            }
        });
        txtLocal.setBounds(new Rectangle(75, 15, 340, 20));
        txtLocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLocal_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtLocal_keyReleased(e);
            }
        });
        btnRelacionProductos.setText("Relación de Guias Seleccionadas :");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 195, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });

        scrListaLocales.setBounds(new Rectangle(5, 160, 425, 230));
        scrListaLocales.setBackground(new Color(255, 130, 14));
        tblListaGuias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            }
        });
        lblF3.setBounds(new Rectangle(5, 395, 115, 20));
        lblF3.setText("[ F3 ] Ver Detalle");
        lblF4.setBounds(new Rectangle(125, 395, 100, 20));
        lblF4.setText("[ F4 ] Ordenar");
        epnMensaje.setBounds(new Rectangle(5, 65, 365, 65));
        epnMensaje.setForeground(Color.red);
        //epnMensaje.setText("DEBE SELECCIONAR SÓLO LAS ENTREGAS FÍSICAS QUE HAN LLEGADO AL LOCAL.");
        epnMensaje.setFont(new Font("Dialog", 1, 14));
        jLabelWhite1.setText("Cant:");
        jLabelWhite1.setBounds(new Rectangle(325, 0, 35, 25));
        lblCant.setText("0");
        lblCant.setBounds(new Rectangle(365, 0, 55, 25));
        jContentPane.add(epnMensaje, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        scrListaLocales.getViewport().add(tblListaGuias, null);
        jContentPane.add(scrListaLocales, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(lblCant, null);
        pnlTitle1.add(jLabelWhite1, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(txtLocal, null);
        pnlHeader1.add(btnNuscar, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        log.debug("TipoEntrega :" + VariablesRecepCiega.vTipoIngrEntrega);
        FarmaVariables.vAceptar = false;
        FarmaUtility.moveFocus(txtLocal);
        tblListaGuias.getTableHeader().setReorderingAllowed(false);
        tblListaGuias.getTableHeader().setResizingAllowed(false);

        mostraMensaje();
        if (VariablesRecepCiega.vTipoIngrEntrega.trim().equalsIgnoreCase("01")) {
            initTableSelect();
            cargaListaGuias();
        } else if (VariablesRecepCiega.vTipoIngrEntrega.trim().equalsIgnoreCase("02")) {
            initTableSimple();
            cargaListaEntregas();
            lblF5.setVisible(false);
            lblF11.setVisible(false);
        }
        setJTable(tblListaGuias);
        //FarmaUtility.ponerCheckJTable(tblListaLocales,1,VariablesAdministracion.vArrayLocalesRelacionados,0);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableSelect() {
        //LISTA LAS GUIAS PENDIENTES
        tableModelGuias =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaGuias, ConstantsRecepCiega.defaultValuesListaGuias,
                                    0);
        FarmaUtility.initSelectList(tblListaGuias, tableModelGuias, ConstantsRecepCiega.columnsListaGuias);
        tblListaGuias.getTableHeader().setReorderingAllowed(false);
        tblListaGuias.getTableHeader().setResizingAllowed(false);
    }

    private void initTableSimple() {
        //LISTA LOS DETALLES DE LAS GUIAS
        tableModelGuiasAso =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaGuias2, ConstantsRecepCiega.defaultValuesListaGuias2,
                                    0);
        FarmaUtility.initSimpleList(tblListaGuias, tableModelGuiasAso, ConstantsRecepCiega.columnsListaGuias2);
        tblListaGuias.getTableHeader().setReorderingAllowed(false);
        tblListaGuias.getTableHeader().setResizingAllowed(false);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnNuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtLocal);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        VariablesRecepCiega.vArrayListaGuias.clear();
        FarmaUtility.moveFocus(txtLocal);
        VariablesRecepCiega.vArrayListaGuias.clear();
        lblCant.setText("" + tblListaGuias.getRowCount());
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaGuias.getRowCount() > 0) {
                if (txtLocal.getText().length() > 0) {
                    SeleccionarGuia(tblListaGuias.getSelectedRow());
                } else {
                    FarmaUtility.showMessage(this, "Debe seleccionar una Guía", txtLocal);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            //por fila
            int row = tblListaGuias.getSelectedRow();
            if (row > -1) {
                getDatos(row);
                verDetalleGuia();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            ordenar();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            todos = !todos;
            if (!todos) {
                log.debug("CANTIDAD1 : " + VariablesRecepCiega.vArrayListaGuias);
            } else {
                VariablesRecepCiega.vArrayListaGuias.clear();
                log.debug("CANTIDAD2 : " + VariablesRecepCiega.vArrayListaGuias);
            }

            seleccionarTodosLocales(todos);

            //dubilluz  - 12.01.2010
            /*VariablesRecepCiega.vArrayListaGuias = new ArrayList();
        for (int i = 0; i < tableModelGuias.getRowCount(); i++)
        {
            cargarGuia_DU(i);
            tableModelGuias.setValueAt(new Boolean(true), i, 0);
        }*/
            tblListaGuias.repaint();

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }

    /**
     * Se ordena el listado de remitos
     * */
    private void ordenar() {

        DlgOrdenar dlgordenar = new DlgOrdenar(myParentFrame, "", true);
        dlgordenar.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaUtility.ordenar(tblListaGuias, tableModelGuias, Integer.parseInt(VariablesRecepCiega.vColumna),
                                 VariablesRecepCiega.vOrden);
            tblListaGuias.repaint();
            FarmaUtility.setearPrimerRegistro(tblListaGuias, txtLocal, 1);
        }
    }


    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        txtLocal.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtLocal, 2);
        }
        FarmaUtility.moveFocus(txtLocal);
    }


    private void txtLocal_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaGuias, txtLocal, 2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaGuias.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaGuias, txtLocal.getText().trim(), 2, 2))) {
                    FarmaUtility.showMessage(this, "Guía No Encontrado según Criterio de Búsqueda !!!", txtLocal);
                    return;
                }
            }
        }
        chkKeyPressed(e);

    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaGuias);
    }

    private void txtLocal_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaGuias, txtLocal, 1);
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
     *  Cargamos el listado Guias
     */
    private void cargaListaGuias() {
        try {
            DBRecepCiega.getListaGuias(tableModelGuias);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar las Guias.\n" +
                    sql.getMessage(), txtLocal);
        }
    }

    /**
     *  Cargamos guias asociadas a la recepcion
     */
    private void cargaListaEntregas() {
        try {
            DBRecepCiega.getListaGuiaAso(tableModelGuiasAso, VariablesRecepCiega.vNumIngreso);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar las asociadas.\n" +
                    sql.getMessage(), txtLocal);
        }
    }

    private void getDatos(int row) {

        if (VariablesRecepCiega.vTipoIngrEntrega.trim().equalsIgnoreCase("01")) {
            VariablesRecepCiega.vNumNotaEst =
                    FarmaUtility.getValueFieldArrayList(tableModelGuias.data, row, COL_NUM_NOTA);
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,4);
            VariablesRecepCiega.vNumGuia = FarmaUtility.getValueFieldArrayList(tableModelGuias.data, row, COL_GUIA);
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,1);
            VariablesRecepCiega.vFecCreaNota =
                    FarmaUtility.getValueFieldArrayList(tableModelGuias.data, row, COL_FECHA);
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,3);

            /*VariablesRecepCiega.vEstado=FarmaUtility.getValueFieldJTable(tblListaGuias,row,5);
        VariablesRecepCiega.vCantItems=tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),7).toString();
        VariablesRecepCiega.vCantProds=tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),8).toString();
        VariablesRecepCiega.vEstado=tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),5).toString();*/

            VariablesRecepCiega.vEstado =
                    FarmaUtility.getValueFieldArrayList(tableModelGuias.data, row, COL_ESTADO); //5
            VariablesRecepCiega.vCantItems =
                    FarmaUtility.getValueFieldArrayList(tableModelGuias.data, row, COL_ITEMS); //7
            VariablesRecepCiega.vCantProds =
                    FarmaUtility.getValueFieldArrayList(tableModelGuias.data, row, COL_PRODS); //8

            log.debug("VariablesRecepCiega.vNumNotaEst " + VariablesRecepCiega.vNumNotaEst);
            log.debug("VariablesRecepCiega.vNumGuia " + VariablesRecepCiega.vNumGuia);
        } else if (VariablesRecepCiega.vTipoIngrEntrega.trim().equalsIgnoreCase("02")) {
            VariablesRecepCiega.vNumNotaEst =
                    FarmaUtility.getValueFieldArrayList(tableModelGuiasAso.data, row, COL_2_NUM_NOTA); //3
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,3);
            VariablesRecepCiega.vNumGuia =
                    FarmaUtility.getValueFieldArrayList(tableModelGuiasAso.data, row, COL_2_GUIA); //0
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,0);
            VariablesRecepCiega.vCantProds =
                    FarmaUtility.getValueFieldArrayList(tableModelGuiasAso.data, row, COL_2_PRODS); //6
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,6);
            VariablesRecepCiega.vFecCreaNota =
                    FarmaUtility.getValueFieldArrayList(tableModelGuiasAso.data, row, COL_2_FECHA); //2
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,2);
            VariablesRecepCiega.vEstado =
                    FarmaUtility.getValueFieldArrayList(tableModelGuiasAso.data, row, COL_2_ESTADO); //4
            //FarmaUtility.getValueFieldJTable(tblListaGuias,row,4);

            log.debug("VariablesRecepCiega.vNumNotaEst " + VariablesRecepCiega.vNumNotaEst);
            log.debug("VariablesRecepCiega.vNumGuia " + VariablesRecepCiega.vNumGuia);
        }

    }

    private void verDetalleGuia() {

        log.debug("DETALLE GUIA");
        DlgDetalleGuia dlgdetalle = new DlgDetalleGuia(myParentFrame, "", true);
        dlgdetalle.setVisible(true);
        if (FarmaVariables.vAceptar) {

        }
    }

    /**
     *  seleccionamos un equipo para agregarlo a la transferencia
     */
    private void SeleccionarGuia(int pFila) {
        log.debug("2");
        //boolean seleccion = ((Boolean) tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),0)).booleanValue();
        boolean seleccion = ((Boolean)tblListaGuias.getValueAt(pFila, COL_CHECK)).booleanValue();
        log.debug("seleccion:" + seleccion);
        if (!seleccion) {
            borrarGuia_DU(pFila);
            cargarGuia_DU(pFila);
            log.debug("GUIAS SELECCIONADAS :" + VariablesRecepCiega.vArrayListaGuias);
            FarmaUtility.setCheckValue(tblListaGuias, false);
            tblListaGuias.setRowSelectionInterval(VariablesRecepCiega.cPos, VariablesRecepCiega.cPos);
        } else {
            borrarGuia_DU(pFila);
            log.debug("GUIAS SELECCIONADAS :" + VariablesRecepCiega.vArrayListaGuias);
            FarmaUtility.setCheckValue(tblListaGuias, true);
        }
    }

    /*
  private void cargarGuia()
  {
  //cambio de posicion
    VariablesRecepCiega.vNumGuia=tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),1).toString();//chek
    VariablesRecepCiega.vNumEntrega=tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),2).toString();

      VariablesRecepCiega.vNumNotaEst=tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),4).toString();
      VariablesRecepCiega.vSecGuia=tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),6).toString();

    VariablesRecepCiega.cPos= tblListaGuias.getSelectedRow();
    ArrayList array=new ArrayList();
    array.add(VariablesRecepCiega.vNumGuia);
    array.add(VariablesRecepCiega.vNumEntrega);
    array.add(VariablesRecepCiega.vNumNotaEst);
    array.add(VariablesRecepCiega.vSecGuia);
    array.add("A");
    VariablesRecepCiega.vArrayListaGuias.add(array);
    log.debug("GUIAS ASOCIADAS :" +VariablesRecepCiega.vArrayListaGuias);
  }
  */

    /**
     *  Cargamos el local relacionado a la forma de pago
     */
    private void cargarGuia_DU(int pFila) {
        //cambio de posicion
        VariablesRecepCiega.vNumGuia = FarmaUtility.getValueFieldArrayList(tableModelGuias.data, pFila, COL_GUIA);
        //tblListaGuias.getValueAt(pFila, 1).toString(); //chek
        VariablesRecepCiega.vNumEntrega =
                FarmaUtility.getValueFieldArrayList(tableModelGuias.data, pFila, COL_ENTREGA);
        //tblListaGuias.getValueAt(pFila, 2).toString();
        VariablesRecepCiega.vNumNotaEst =
                FarmaUtility.getValueFieldArrayList(tableModelGuias.data, pFila, COL_NUM_NOTA);
        //tblListaGuias.getValueAt(pFila, 4).toString();
        VariablesRecepCiega.vSecGuia = FarmaUtility.getValueFieldArrayList(tableModelGuias.data, pFila, COL_PAG);
        //tblListaGuias.getValueAt(pFila, 6).toString();

        VariablesRecepCiega.cPos = pFila;
        ArrayList array = new ArrayList();
        array.add(VariablesRecepCiega.vNumGuia);
        array.add(VariablesRecepCiega.vNumEntrega);
        array.add(VariablesRecepCiega.vNumNotaEst);
        array.add(VariablesRecepCiega.vSecGuia);
        array.add("A");
        VariablesRecepCiega.vArrayListaGuias.add(array);
        log.debug("GUIAS ASOCIADAS :" + VariablesRecepCiega.vArrayListaGuias);
    }

    /**
     * Elimina la guia seleccionada del conjunto
     * */

    /*
  private void borrarGuia()
  {
    String cod = tblListaGuias.getValueAt(tblListaGuias.getSelectedRow(),COL_TIP).toString();

    for(int i=0;i< VariablesRecepCiega.vArrayListaGuias.size();i++)
    {
      if(((ArrayList) VariablesRecepCiega.vArrayListaGuias.get(i)).contains(cod))
      {
        VariablesRecepCiega.vArrayListaGuias.remove(i);
        break;
      }
    }
  }
  */
    private void borrarGuia_DU(int pFila) {
        String cod = tblListaGuias.getValueAt(pFila, COL_TIP).toString();

        for (int i = 0; i < VariablesRecepCiega.vArrayListaGuias.size(); i++) {
            if (((ArrayList)VariablesRecepCiega.vArrayListaGuias.get(i)).contains(cod)) {
                VariablesRecepCiega.vArrayListaGuias.remove(i);
                break;
            }
        }
    }

    /**
   * Actualizamos el estado del local almacenado
   * */
    /* private void actualizaAsignacion(int pos){

    if(VariablesAdministracion.vArrayLocalesRelacionados.size()>-1
    ){
    ((ArrayList)VariablesAdministracion.vArrayLocalesRelacionados.get(pos)).set(2,ConstantsAdministracion.INACTIVO);
    }else{
       FarmaUtility.showMessage(this,"ERROR POR DATOS(ARREGLO) VACIOS",null);
      }
  }*/

    /**
     * Se asocian las guias a la nueva recepcion.
     * */


    /*

    private void asignarGuias(int pCantGuias) {

        String MsjError="";

        try {
            //if (ConstantsRecepCiega.error_du) {
            if  (UtilityRecepCiega.indHabDatosTransp()){
                log.debug(" VariablesRecepCiega.vNumIngreso : "+ VariablesRecepCiega.vNumIngreso);
                DBRecepCiega.asignarGuias(VariablesRecepCiega.vArrayListaGuias,
                                          VariablesRecepCiega.vNumIngreso);
                //JMIRANDA 19.03.2010
                //Actualizar la cant guias
                int vCantGuias = DBRecepCiega.getCantGuias(VariablesRecepCiega.vNumIngreso);
                DBInventario.actualizaCantGuias(vCantGuias+pCantGuias,VariablesRecepCiega.vNumIngreso.trim());
            }
            else {
                //antiguo

                String nroRecep =
                    DBRecepCiega.agregarRecepcion(VariablesRecepCiega.vArrayListaGuias.size());
                log.debug("Nueva Recepcion :" + nroRecep);
                DBRecepCiega.asignarGuias(VariablesRecepCiega.vArrayListaGuias,
                                          nroRecep);
            }

            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Se asignaron guías a la recepción con éxito",
                                     txtLocal);
            cerrarVentana(true);
        } catch (SQLException sql) {
            //DUBILLUZ  - 14.01.2010
            FarmaUtility.liberarTransaccion();
            if (sql.getErrorCode() == 20003) {
                FarmaUtility.showMessage(this,
                                         "El número de ingreso ya existe. Verifique!!!",
                                         txtLocal);
            } else if (sql.getErrorCode() == 20004) {
                FarmaUtility.showMessage(this,
                                         "La guía ya esta asociada a una recepción. Verifique!!!",
                                         txtLocal);
            } else {
                log.error("",sql);
                FarmaUtility.showMessage(this,
                                         "Ocurrió un error al asignar guias.\n" +
                        sql.getMessage(), txtLocal);
            }
        }
    }

     */

    //JQUISPE 15.04.2010 CAMBIOS PARA VALIDAR RECEPCION CIEGA
    private void asignarGuias(int pCantGuias) {

        String MsjError = "";

        try {
            //if (ConstantsRecepCiega.error_du) {
            if (UtilityRecepCiega.indHabDatosTransp()) {
                log.debug(" VariablesRecepCiega.vNumIngreso : " + VariablesRecepCiega.vNumIngreso);
                DBRecepCiega.asignarGuias(VariablesRecepCiega.vArrayListaGuias, VariablesRecepCiega.vNumIngreso);
                //JMIRANDA 19.03.2010
                //Actualizar la cant guias
                int vCantGuias = DBRecepCiega.getCantGuias(VariablesRecepCiega.vNumIngreso);
                DBInventario.actualizaCantGuias(vCantGuias + pCantGuias, VariablesRecepCiega.vNumIngreso.trim());
            } else {
                //antiguo

                String nroRecep = DBRecepCiega.agregarRecepcion(VariablesRecepCiega.vArrayListaGuias.size());
                log.debug("Nueva Recepcion :" + nroRecep);
                DBRecepCiega.asignarGuias(VariablesRecepCiega.vArrayListaGuias, nroRecep);
            }

            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se asignaron guías a la recepción con éxito", txtLocal);
            cerrarVentana(true);
        } catch (SQLException sql) {
            //DUBILLUZ  - 14.01.2010
            FarmaUtility.liberarTransaccion();
            if (sql.getErrorCode() == 20003) {
                FarmaUtility.showMessage(this, "El número de ingreso ya existe. Verifique!!!", txtLocal);
            } else if (sql.getErrorCode() == 20004) {
                FarmaUtility.showMessage(this, "La guía ya esta asociada a una recepción. Verifique!!!", txtLocal);
            } else if (sql.getErrorCode() == 20010) {
                FarmaUtility.showMessage(this, sql.getMessage().substring(11, sql.getMessage().indexOf("ORA-06512")),
                                         txtLocal);
            } else {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al asignar guias.\n" +
                        sql.getMessage(), txtLocal);
            }
        }
    }
    /*
 *
 *      parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumRecep);
        parametros.add(((String) ((ArrayList) arrayGuias.get(i)).get(2)).trim());//NumNotaEst
        parametros.add(((String) ((ArrayList) arrayGuias.get(i)).get(0)).trim());//numGuia
        parametros.add(((String) ((ArrayList) arrayGuias.get(i)).get(1)).trim());//numEntrega
        parametros.add(new Integer(((String) ((ArrayList) arrayGuias.get(i)).get(3)).trim()));//Sec
 *
 * */

    /**
     * Seleccionamos todos los locales disponibles
     * */
    private void seleccionarTodosLocales(boolean valor) {
        for (int i = 0; i < tableModelGuias.getRowCount(); i++) {
            tableModelGuias.setValueAt(new Boolean(valor), i, 0);
        }
        tblListaGuias.repaint();
    }

    /**
     * Asociamos todas la guias a la recepcion
     * */
    private void cargarGuias() {
        //JCORTEZ 25.01.10
        VariablesRecepCiega.vArrayListaGuias = new ArrayList();
        boolean valor;
        for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
            valor = ((Boolean)tableModelGuias.getValueAt(i, 0)).booleanValue();
            if (valor) {
                ArrayList array = new ArrayList();
                /*array.add(tblListaGuias.getValueAt(i,
                                                   1).toString()); //vNumGuia
                array.add(tblListaGuias.getValueAt(i,
                                                   2).toString()); //vNumEntrega
                array.add(tblListaGuias.getValueAt(i,
                                                   4).toString()); //vNumNotaEst
                array.add(tblListaGuias.getValueAt(i, 6).toString()); //sec*/
                array.add(FarmaUtility.getValueFieldArrayList(tableModelGuias.data, i, COL_GUIA));
                array.add(FarmaUtility.getValueFieldArrayList(tableModelGuias.data, i, COL_ENTREGA));
                array.add(FarmaUtility.getValueFieldArrayList(tableModelGuias.data, i, COL_NUM_NOTA));
                array.add(FarmaUtility.getValueFieldArrayList(tableModelGuias.data, i, COL_PAG)); //6
                array.add("A");
                VariablesRecepCiega.vArrayListaGuias.add(array);
            }
        }
        log.debug("GUIAS ALMACENADAS: " + VariablesRecepCiega.vArrayListaGuias);
        log.debug("CANTIDAD :" + VariablesRecepCiega.vArrayListaGuias.size());
    }


    private void mostraMensaje() {

        String mensaje = "";
        try {
            mensaje = DBRecepCiega.getMensajePendientes();
            epnMensaje.setText(mensaje);
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar el Mensaje.\n" +
                    e.getMessage(), txtLocal);
        }
    }

    private void funcionF11() {
        log.debug("seleccion todos-->" + todos);
        log.debug("En arreglo cant-->" + VariablesRecepCiega.vArrayListaGuias.size());
        if (todos && VariablesRecepCiega.vArrayListaGuias.size() == 0) {
            boolean valor;
            int cant = 0;
            for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                valor = ((Boolean)tableModelGuias.getValueAt(i, 0)).booleanValue();
                if (valor) {
                    cant++;
                }
            }
            //en caso de que seleccionen todas y a mano deseleccionen todas.
            if (cant == 0)
                todos = !todos;
            //en caso de que vuelva a seleccionar el ya deseleccionado
            if (todos)
                VariablesRecepCiega.vArrayListaGuias.clear();

            log.debug("Cant-->" + cant);
            log.debug("En arreglo-->" + VariablesRecepCiega.vArrayListaGuias);
        }


        if (VariablesRecepCiega.vArrayListaGuias.size() > 0 || todos) {
            int cantAsoc = 0;
            boolean valorAsoc;
            cantAsoc = VariablesRecepCiega.vArrayListaGuias.size();
            if (todos) {
                for (int i = 0; i < tblListaGuias.getRowCount(); i++) {
                    valorAsoc = ((Boolean)tableModelGuias.getValueAt(i, 0)).booleanValue();
                    if (valorAsoc) {
                        cantAsoc++;
                    }
                }
                cantAsoc = cantAsoc;
            }

            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "Esta seguro de asociar " + cantAsoc +
                                                                            " entrega(s)?")) {
                if (todos) {
                    cargarGuias(); //todos seleccionados
                }
                log.debug("GUIAS ALMACENADOS: " + VariablesRecepCiega.vArrayListaGuias);
                log.debug("CANTIDAD : " + VariablesRecepCiega.vArrayListaGuias.size());
                asignarGuias(VariablesRecepCiega.vArrayListaGuias.size());
                //cerrarVentana(true);
            }
        } else
            FarmaUtility.showMessage(this, "Debe seleccionar una Guía", txtLocal);
    }

}
