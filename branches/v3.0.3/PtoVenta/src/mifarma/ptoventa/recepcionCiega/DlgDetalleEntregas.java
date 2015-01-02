package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : dlgdetalle.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ 16.11.2009 Creación<br>
 * <br>
 *
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgDetalleEntregas extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleEntregas.class);

    private Frame myParentFrame;

    private FarmaTableModel tableModelEntregas;
    private FarmaTableModel tableModelDetEntregas;
    private JTable myJTable;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();

    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JLabelWhite lblGuia_T = new JLabelWhite();


    private JLabelWhite lblGuia = new JLabelWhite();

    private JLabelWhite lblFecha = new JLabelWhite();

    private JLabelWhite lblProductos_T = new JLabelWhite();

    private JLabelWhite lblItems_T = new JLabelWhite();

    private JLabelWhite lblItems = new JLabelWhite();

    private JLabelWhite lblProductos = new JLabelWhite();


    private JLabelWhite lblEstado_T = new JLabelWhite();

    private JLabelWhite lblEstado = new JLabelWhite();


    private JButtonLabel btnBuscar = new JButtonLabel();

    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JScrollPane scrListaProductos = new JScrollPane();

    private JLabelFunction lblEsc = new JLabelFunction();


    private JButtonLabel btnRelacionProductos = new JButtonLabel();

    private JTable tblListaProductos = new JTable();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblNumRecep = new JLabelWhite();
    private JButtonLabel btnListadoGuias = new JButtonLabel();
    private JScrollPane srcGuias = new JScrollPane();
    private JTable tblGuias = new JTable();
    private JLabelWhite lblFecha_T = new JLabelWhite();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JLabelWhite lblFechaRecep = new JLabelWhite();
    private JLabelWhite jLabelWhite4 = new JLabelWhite();
    private JLabelWhite lblCantGuias = new JLabelWhite();
    private JLabelWhite jLabelWhite3 = new JLabelWhite();
    private JLabel lblBultos = new JLabel();
    private JLabel lblPrecintos = new JLabel();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();

    //**
    //JMIRANDA 20.03.2010
    private static final int COL_GUIA = 0;
    private static final int COL_ENTREGA = 1;
    private static final int COL_FECHA = 2;
    private static final int COL_NUM_NOTA = 3;
    private JLabelWhite lblEntrega_T = new JLabelWhite();
    private JLabelWhite lblEntrega = new JLabelWhite();
    //**
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDetalleEntregas() {
        this(null, "", false);
    }

    public DlgDetalleEntregas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(660, 571));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle de Guía");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(5, 265, 645, 75));
        lblGuia_T.setText("Guía:");
        lblGuia_T.setBounds(new Rectangle(10, 5, 40, 15));
        lblGuia.setBounds(new Rectangle(55, 5, 105, 15));
        lblGuia.setFont(new Font("SansSerif", 0, 11));
        lblFecha.setBounds(new Rectangle(55, 25, 110, 15));
        lblFecha.setFont(new Font("SansSerif", 0, 11));
        lblProductos_T.setText("Productos:");
        lblProductos_T.setBounds(new Rectangle(175, 25, 65, 15));
        lblItems_T.setText("Items:");
        lblItems_T.setBounds(new Rectangle(175, 5, 60, 15));
        lblItems.setBounds(new Rectangle(225, 5, 70, 15));
        lblItems.setFont(new Font("SansSerif", 0, 11));
        lblProductos.setBounds(new Rectangle(240, 25, 70, 15));
        lblProductos.setFont(new Font("SansSerif", 0, 11));
        lblEstado_T.setText("Estado:");
        lblEstado_T.setBounds(new Rectangle(325, 25, 50, 15));
        lblEstado.setBounds(new Rectangle(370, 25, 70, 15));
        lblEstado.setFont(new Font("SansSerif", 0, 11));
        btnBuscar.setText("Buscar :");
        btnBuscar.setBounds(new Rectangle(10, 45, 55, 15));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(65, 45, 295, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 345, 645, 25));
        scrListaProductos.setBounds(new Rectangle(5, 370, 645, 130));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(565, 505, 85, 20));
        btnRelacionProductos.setText("Relación de Productos Seleccionados");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 215, 15));
        jPanelHeader1.setBounds(new Rectangle(5, 10, 645, 65));
        jPanelTitle1.setBounds(new Rectangle(5, 80, 645, 25));
        jLabelWhite1.setText("Nro Ingreso :");
        jLabelWhite1.setBounds(new Rectangle(10, 5, 85, 20));
        lblNumRecep.setText("000000");
        lblNumRecep.setBounds(new Rectangle(100, 5, 90, 20));
        lblNumRecep.setFont(new Font("SansSerif", 0, 11));
        btnListadoGuias.setText("Listado de Guías :");
        btnListadoGuias.setBounds(new Rectangle(10, 0, 240, 25));
        btnListadoGuias.setMnemonic('L');
        btnListadoGuias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListadoGuias_actionPerformed(e);
            }
        });
        srcGuias.setBounds(new Rectangle(5, 105, 645, 155));
        tblGuias.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblGuias_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblGuias_keyPressed(e);
            }
        });
        pnlHeader1.add(lblEntrega, null);
        pnlHeader1.add(lblEntrega_T, null);
        pnlHeader1.add(jLabelWhite3, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnBuscar, null);
        pnlHeader1.add(lblEstado, null);
        pnlHeader1.add(lblEstado_T, null);
        pnlHeader1.add(lblProductos, null);
        pnlHeader1.add(lblItems, null);
        pnlHeader1.add(lblItems_T, null);
        pnlHeader1.add(lblProductos_T, null);
        pnlHeader1.add(lblFecha, null);
        pnlHeader1.add(lblGuia, null);
        pnlHeader1.add(lblGuia_T, null);
        jPanelWhite1.add(jLabelWhite4, null);
        jPanelWhite1.add(lblCantGuias, null);
        jPanelHeader1.add(jPanelWhite1, null);
        jPanelHeader1.add(lblPrecintos, null);
        jPanelHeader1.add(lblBultos, null);
        jPanelHeader1.add(lblFechaRecep, null);
        jPanelHeader1.add(jLabelWhite2, null);
        jPanelHeader1.add(lblNumRecep, null);
        jPanelHeader1.add(jLabelWhite1, null);
        srcGuias.getViewport().add(tblGuias, null);
        lblF1.setText("[ F1 ] Asociar Entregas");
        lblF2.setText("[ F2 ] Desasociar Entregas");
        lblF8.setText("[ F8 ] Acta Recepción");
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(srcGuias, null);
        jPanelTitle1.add(btnListadoGuias, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jLabelWhite2.setText("Fecha :");
        jLabelWhite2.setBounds(new Rectangle(465, 5, 50, 20));
        lblFechaRecep.setBounds(new Rectangle(515, 5, 100, 20));
        lblFechaRecep.setFont(new Font("SansSerif", 0, 11));
        jLabelWhite4.setText("Nro. Entregas Asociadas :");
        jLabelWhite4.setBounds(new Rectangle(15, 5, 180, 20));
        jLabelWhite4.setForeground(new Color(0, 132, 0));
        jLabelWhite4.setFont(new Font("SansSerif", 1, 14));
        lblCantGuias.setText("0");
        lblCantGuias.setBounds(new Rectangle(205, 5, 45, 20));
        lblCantGuias.setFont(new Font("SansSerif", 1, 14));
        lblCantGuias.setForeground(new Color(0, 132, 0));
        jLabelWhite3.setText("Fecha :");
        jLabelWhite3.setBounds(new Rectangle(10, 25, 40, 15));
        lblBultos.setText("Nº Bultos : ");
        lblBultos.setBounds(new Rectangle(10, 40, 175, 20));
        lblBultos.setForeground(Color.white);
        lblBultos.setFont(new Font("Dialog", 1, 11));
        lblPrecintos.setText("Nº Precintos : ");
        lblPrecintos.setBounds(new Rectangle(190, 40, 185, 20));
        lblPrecintos.setFont(new Font("Dialog", 1, 11));
        lblPrecintos.setForeground(Color.white);
        jPanelWhite1.setBounds(new Rectangle(170, 5, 250, 35));
        lblF1.setBounds(new Rectangle(5, 505, 140, 20));
        lblF2.setBounds(new Rectangle(150, 505, 155, 20));
        lblF8.setBounds(new Rectangle(325, 505, 130, 20));
        lblEntrega_T.setText("Entrega:");
        lblEntrega_T.setBounds(new Rectangle(325, 5, 50, 15));
        lblEntrega.setText("");
        lblEntrega.setBounds(new Rectangle(380, 5, 85, 15));
        lblEntrega.setFont(new Font("SansSerif", 0, 11));


    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModelEntregas =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaGuiasEntre, ConstantsRecepCiega.defaultValuesListaGuiasEntre,
                                    0);
        FarmaUtility.initSimpleList(tblGuias, tableModelEntregas, ConstantsRecepCiega.columnsListaGuiasEntre);

        tableModelDetEntregas =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaDetalleGuia, ConstantsRecepCiega.defaultValuesListaDetalleGuia,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModelDetEntregas,
                                    ConstantsRecepCiega.columnsListaDetalleGuia);
        //**
        tblGuias.getTableHeader().setReorderingAllowed(false);
        tblGuias.getTableHeader().setResizingAllowed(false);
        tblListaProductos.getTableHeader().setReorderingAllowed(false);
        tblListaProductos.getTableHeader().setResizingAllowed(false);
        //**
        cargaListaGuias();

        setTableDetalleProductos();


        /*if(tblGuias.getSelectedRow()>-1)
              cargaListaProductos(tblGuias.getValueAt(tblGuias.getSelectedRow(),3).toString(),lblGuia.getText().trim());*/

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void btnBuscar_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(txtBuscar);
        setJTable(tblListaProductos);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);

        lblNumRecep.setText(VariablesRecepCiega.vNumIngreso);
        lblCantGuias.setText(VariablesRecepCiega.vCantGuias);
        lblFechaRecep.setText(VariablesRecepCiega.vFechIngreso);
        if (tblGuias.getRowCount() < 1) {
            FarmaUtility.showMessage(this, "No existen guias!!!", txtBuscar);
            cerrarVentana(true);
        } else {
            FarmaUtility.moveFocusJTable(tblGuias);
            FarmaGridUtils.showCell(tblGuias, 0, 0);
        }
        lblBultos.setText(lblBultos.getText() + " " + VariablesRecepCiega.vCantBultos);
        lblPrecintos.setText(lblPrecintos.getText() + " " + VariablesRecepCiega.vCantPrecintos);

        //VariablesRecepCiega.vCantBultos
        // VariablesRecepCiega.vCantPrecintos
        //mostrarDatos();
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaUtility.ponerMayuscula(e, txtBuscar);
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 1);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

        } else if (UtilityPtoVenta.verificaVK_F1(e)) {
            e.consume();
            funcionF1();
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            e.consume();
            funcionF2();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            e.consume();
            funcionF8();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //this.setVisible(false);
            cerrarVentana(false);
        }
    }

    private void btnListadoGuias_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblGuias);
    }

    private void tblGuias_keyReleased(KeyEvent e) {
        //FarmaGridUtils.showCell(tblGuias, 0, 0);
        mostrarDatos();
        /*if (tblGuias.getSelectedRow()>-1){
            lblGuia.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),0).toString());
            lblFecha.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),2).toString());
            lblItems.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),5).toString());
            //lblProductos.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),6).toString());
            lblEstado.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),7).toString());
            cargaListaProductos(tblGuias.getValueAt(tblGuias.getSelectedRow(),3).toString(),lblGuia.getText().trim());
            lblProductos.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaProductos,4), 0));
            //JMIRANDA 20.03.2010
            lblEntrega.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),1).toString());
        }*/
    }

    private void tblGuias_keyPressed(KeyEvent e) {

        chkKeyPressed(e);
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        txtBuscar.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtBuscar, 1);
        }
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void cargaListaGuias() {
        try {
            log.debug("VariablesRecepCiega.vNumIngreso " + VariablesRecepCiega.vNumIngreso);
            DBRecepCiega.getListaDetGuiasEntrega(tableModelEntregas, VariablesRecepCiega.vNumIngreso);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar el detalle de la guia : \n", txtBuscar);
            ;
        }
    }

    private void cargaListaProductos(String numNota, String numGuia) {
        try {
            DBRecepCiega.getListaDetGuias(tableModelDetEntregas, numNota, numGuia);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar el detalle de la guia : \n", txtBuscar);
            ;
        }
    }


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void setTableDetalleProductos() {
        FarmaGridUtils.showCell(tblGuias, 0, 0);
        mostrarDatos();
        /*if (tblGuias.getSelectedRow()>-1){
            lblGuia.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),0).toString());
            lblFecha.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),2).toString());
            lblItems.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),5).toString());
            //lblProductos.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),6).toString());
            lblEstado.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),7).toString());
            cargaListaProductos(tblGuias.getValueAt(tblGuias.getSelectedRow(),3).toString(),lblGuia.getText().trim());
            lblProductos.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaProductos,4), 0));
            //JMIRANDA 20.03.2010
            lblEntrega.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),1).toString());
        }*/
    }

    private void funcionF1() {
        if (!VariablesRecepCiega.vCod_Estado.equalsIgnoreCase("T")) {
            if (cargaLogin()) {
                VariablesRecepCiega.vTipoIngrEntrega = "01";
                mostrarGuias();
                if (FarmaVariables.vAceptar) {
                    cerrarVentana(true);
                }
            }
        } //
        else {
            FarmaUtility.showMessage(this, "No puede asociar Entregas a una Recepción que ya ha sido Afectada.",
                                     tblGuias);
        }
    }

    private void funcionF2() {
        if (tblGuias.getSelectedRow() == -1)
            return;
        if (!VariablesRecepCiega.vCod_Estado.equalsIgnoreCase("T")) {
            if (cargaLogin()) {
                try {
                    //
                    int vFila = tblGuias.getSelectedRow();

                    String vNumEntrega = FarmaUtility.getValueFieldArrayList(tableModelEntregas.data, vFila, 1);

                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                         "¿Está seguro de Desasociar la entrega: " + vNumEntrega +
                                                         " ?")) {
                        log.debug("DESASOCIA vNumEntrega: " + vNumEntrega);
                        String vRpta =
                            DBRecepCiega.desasociaEntrega(VariablesRecepCiega.vNumIngreso, vNumEntrega).trim();

                        FarmaUtility.aceptarTransaccion();
                        cargaListaGuias();
                        lblCantGuias.setText("" + tblGuias.getRowCount());
                        mostrarDatos();
                        if (vRpta.equalsIgnoreCase("S")) {
                            FarmaUtility.showMessage(this, "Entrega desasociada correctamente.", tblGuias);
                            if (tblGuias.getRowCount() <= 0) {
                                cerrarVentana(true);
                            } else {
                                FarmaGridUtils.showCell(tblGuias, 0, 0);
                            }
                        }
                    }

                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, "Error al Desasociar la entrega.", tblGuias);
                }
            }
        } //
        else {
            FarmaUtility.showMessage(this, "No puede desasociar Entregas a una Recepción que ya ha sido Afectada.",
                                     tblGuias);
        }
    }

    private void mostrarGuias() {
        DlgGuiasPendientes dlgGuias = new DlgGuiasPendientes(myParentFrame, "", true);
        dlgGuias.setVisible(true);
    }

    private void mostrarDatos() {
        if (tblGuias.getSelectedRow() > -1) {
            lblGuia.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(), COL_GUIA).toString());
            lblFecha.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(), COL_FECHA).toString());
            lblItems.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(), 5).toString());
            //lblProductos.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(),6).toString());
            lblEstado.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(), 7).toString());
            cargaListaProductos(tblGuias.getValueAt(tblGuias.getSelectedRow(), 3).toString(),
                                lblGuia.getText().trim());
            lblProductos.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaProductos, 4), 0));
            //JMIRANDA 20.03.2010
            lblEntrega.setText(tblGuias.getValueAt(tblGuias.getSelectedRow(), COL_ENTREGA).toString());
        }
    }

    private boolean cargaLogin() {
        boolean flag = true;
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu = FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu = FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu = FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu = FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu = FarmaVariables.vMatUsu;

        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar) {
            flag = true;

            // se sete datos originales
            FarmaVariables.vNuSecUsu = VariablesRecepCiega.vNuSecUsu;
            FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
            FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
            FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
            FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;

            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else {
            flag = false;
        }
        //cerrarVentana(false);
        return flag;
    }

    private void funcionF8() {

        String pEstado = "";

        int row = tblGuias.getSelectedRow();
        if (row > -1) {


            pEstado = VariablesRecepCiega.vCod_Estado;
            /*  UtilityRecepCiega.pEstadoRecepcion(FarmaUtility.getValueFieldJTable(tblGuias,
                                                                                            row,
                                                                                            0).trim()); */


            if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoTotal) ||
                pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoParcial)) {

                String vRecepcion = VariablesRecepCiega.vNumIngreso;
                String vNumEntrega = FarmaUtility.getValueFieldJTable(tblGuias, row, 1);
                String vFecha = FarmaUtility.getValueFieldJTable(tblGuias, row, 2);


                DlgActaRecepcionMercaderia dlgActaRecepcion = new DlgActaRecepcionMercaderia(myParentFrame, "", true);
                dlgActaRecepcion.setCodRecepcion(vRecepcion, vNumEntrega, vFecha);
                dlgActaRecepcion.cargarLista();
                dlgActaRecepcion.setVisible(true);


            } else {
                FarmaUtility.showMessage(this, "La Recepción aun no ha sido AFECTADA.", tblGuias);
                FarmaUtility.moveFocus(null);
            }


        } else {
            if (tblGuias.getRowCount() < 1)
                FarmaUtility.moveFocus(null);
            else
                FarmaUtility.showMessage(this, "Debe seleccionar un registro", tblGuias);
        }


    }

}
