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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgPedidoReposicionAdicionalNuevo.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DVELIZ      10.09.08   Creación<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 *
 */

public class DlgPedidoAdicional extends JDialog

{
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgPedidoAdicional.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JPanelTitle pnlTitle3 = new JPanelTitle();
    private JPanelWhite pnlWhite2 = new JPanelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JPanelWhite pnlWhite3 = new JPanelWhite();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblRotacion_T = new JLabelWhite();
    private JLabelWhite lblNoDias_T = new JLabelWhite();
    private JLabelWhite lblLaboratorio_T = new JLabelWhite();
    private JLabelWhite lblStockAlmacen = new JLabelWhite();
    private JLabelWhite lblStockAlmacen_T = new JLabelWhite();
    private JLabelWhite lbl120d = new JLabelWhite();
    private JLabelWhite lbl90d = new JLabelWhite();
    private JLabelWhite lbl60d = new JLabelWhite();
    private JLabelWhite lbl30d = new JLabelWhite();
    private JLabelWhite lbl120d_T = new JLabelWhite();
    private JLabelWhite lbl90d_T = new JLabelWhite();
    private JLabelWhite lbl60d_T = new JLabelWhite();
    private JLabelWhite lbl30d_T = new JLabelWhite();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabelWhite lblMaximoDias = new JLabelWhite();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JButtonLabel lblFCero = new JButtonLabel();
    private JLabel lblIndTipoProd = new JLabel();
    private JLabelFunction lblF3 = new JLabelFunction();

    // Constanst para las columnas de las tablas
    private int COL_ORD_LISTA = 0;
    private int COL_PROD = 1;
    private int COL_DESC_PROD = 2;
    private JLabelFunction lblF1 = new JLabelFunction();
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgPedidoAdicional() {
        this(null, "", false);
    }

    public DlgPedidoAdicional(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(809, 434));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedido Adicional");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 790, 50));
        pnlTitle1.setBounds(new Rectangle(10, 65, 790, 20));
        scrListaProductos.setBounds(new Rectangle(10, 85, 790, 180));
        pnlTitle2.setBounds(new Rectangle(260, 270, 540, 20));
        pnlTitle3.setBounds(new Rectangle(425, 315, 375, 20));
        pnlWhite2.setBounds(new Rectangle(260, 290, 540, 20));
        pnlWhite2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblLaboratorio.setBounds(new Rectangle(5, 5, 420, 15));
        lblLaboratorio.setForeground(Color.black);
        pnlWhite3.setBounds(new Rectangle(425, 335, 375, 20));
        pnlWhite3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        btnRelacionProductos.setText("Relación de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 135, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(710, 370, 90, 20));
        lblRotacion_T.setText("Rotación");
        lblRotacion_T.setBounds(new Rectangle(5, 5, 70, 15));
        lblRotacion_T.setForeground(Color.black);
        lblNoDias_T.setText("No Días");
        lblNoDias_T.setBounds(new Rectangle(5, 5, 70, 15));
        lblLaboratorio_T.setText("Laboratorio");
        lblLaboratorio_T.setBounds(new Rectangle(5, 5, 70, 15));
        lblStockAlmacen.setText("Stk. Almacen");
        lblStockAlmacen.setBounds(new Rectangle(435, 5, 100, 15));
        lblStockAlmacen.setHorizontalAlignment(SwingConstants.CENTER);
        lblStockAlmacen_T.setBounds(new Rectangle(460, 5, 55, 15));
        lblStockAlmacen_T.setForeground(Color.black);
        lblStockAlmacen_T.setHorizontalAlignment(SwingConstants.CENTER);
        lblStockAlmacen_T.setText("0");
        lbl120d.setBounds(new Rectangle(105, 5, 55, 15));
        lbl120d.setForeground(Color.black);
        lbl120d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90d.setBounds(new Rectangle(160, 5, 55, 15));
        lbl90d.setForeground(Color.black);
        lbl90d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl60d.setBounds(new Rectangle(225, 5, 55, 15));
        lbl60d.setForeground(Color.black);
        lbl60d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl30d.setBounds(new Rectangle(285, 5, 60, 15));
        lbl30d.setForeground(Color.black);
        lbl30d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl120d_T.setText("120 d.");
        lbl120d_T.setBounds(new Rectangle(100, 5, 60, 15));
        lbl120d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90d_T.setText("90 d.");
        lbl90d_T.setBounds(new Rectangle(160, 5, 60, 15));
        lbl90d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl60d_T.setText("60 d.");
        lbl60d_T.setBounds(new Rectangle(220, 5, 60, 15));
        lbl60d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl30d_T.setText("30 d.");
        lbl30d_T.setBounds(new Rectangle(285, 5, 60, 15));
        lbl30d_T.setHorizontalAlignment(SwingConstants.CENTER);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(15, 15, 55, 15));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(75, 15, 375, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        lblMaximoDias.setBounds(new Rectangle(545, 25, 35, 15));
        lblMaximoDias.setFont(new Font("SansSerif", 0, 11));
        lblEnter.setText("[ Enter ] Ingresar Cantidad");
        lblEnter.setBounds(new Rectangle(10, 370, 175, 20));

        lblF3.setText("[F3] Ver Historial");
        lblF3.setBounds(new Rectangle(385, 370, 175, 20));

        lblF1.setBounds(new Rectangle(195, 370, 180, 20));
        lblF1.setText("[ F1 ] Afecta PVM");
        lblFCero.setText("- FALTA CERO");
        lblFCero.setVisible(false);
        lblFCero.setBounds(new Rectangle(135, 0, 80, 20));
        lblIndTipoProd.setBounds(new Rectangle(500, 0, 140, 20));
        lblIndTipoProd.setFont(new Font("SansSerif", 1, 12));
        lblIndTipoProd.setBackground(new Color(44, 146, 24));
        lblIndTipoProd.setOpaque(true);
        lblIndTipoProd.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblIndTipoProd.setForeground(Color.white);
        lblIndTipoProd.setHorizontalAlignment(SwingConstants.CENTER);
        lblIndTipoProd.setHorizontalTextPosition(SwingConstants.CENTER);
        pnlWhite2.add(lblStockAlmacen_T, null);
        pnlWhite2.add(lblLaboratorio, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlWhite3, null);
        jContentPane.add(pnlWhite2, null);
        jContentPane.add(pnlTitle3, null);
        jContentPane.add(pnlTitle2, null);
        pnlWhite3.add(lbl30d, null);
        pnlWhite3.add(lbl60d, null);
        pnlWhite3.add(lbl90d, null);
        pnlWhite3.add(lbl120d, null);
        pnlWhite3.add(lblRotacion_T, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle3.add(lbl30d_T, null);
        pnlTitle3.add(lbl60d_T, null);
        pnlTitle3.add(lbl90d_T, null);
        pnlTitle3.add(lbl120d_T, null);
        pnlTitle3.add(lblNoDias_T, null);
        pnlTitle2.add(lblStockAlmacen, null);
        pnlTitle2.add(lblLaboratorio_T, null);
        pnlTitle1.add(lblIndTipoProd, null);
        pnlTitle1.add(lblFCero, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlHeader1, null);
        pnlHeader1.add(lblMaximoDias, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnBuscar, null);
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
        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaProductosPedidoAdicionalMatriz, ConstantsProducto.defaultValuesProductosPedidoAdicionalMatriz,
                                    0);

        FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                    ConstantsProducto.columnsListaProductosPedidoAdicionalMatriz);
        cargaListaProductos();
        if (tblListaProductos.getRowCount() > 0)
            mostrarDetalles(tblListaProductos.getSelectedRow());
    }

    private void cargaListaProductos() {
        try {
            DBProducto.cargaListaProductosPedidoAdicionalMatriz(tableModel);
            FarmaUtility.ordenar(tblListaProductos, tableModel, 6, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar " + "la lista de productos : \n" +
                    sql.getMessage(), txtBuscar);
        }
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 2))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según " + "Criterio de Búsqueda !!!",
                                             txtBuscar);
                    return;
                }
                mostrarDetalles(tblListaProductos.getSelectedRow());
            }
        }

        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 2);
        if (tableModel.getRowCount() > 0 && tblListaProductos.getSelectedRow() > -1) {
            mostrarDetalles(tblListaProductos.getSelectedRow());
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresarCantidad();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            afectarCantidadSolicitada();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            cargarHistorialProducto();
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

    private void mostrarDetalles(int row) {
        String pCodProd = tblListaProductos.getValueAt(row, 0).toString();
        ArrayList lista = new ArrayList();
        try {
            ArrayList mylist = new ArrayList();
            DBProducto.cargaDetalleProductoPVMMatriz(mylist, pCodProd);
            if (mylist.size() == 0) {
                lblStockAlmacen_T.setText("0");
                lblLaboratorio.setText(tblListaProductos.getValueAt(row, 8).toString());
                lbl30d.setText("0.000");
                lbl60d.setText("0.000");
                lbl90d.setText("0.000");
                lbl120d.setText("0.000");
            } else {
                lista = (ArrayList)mylist.get(0);
                lblLaboratorio.setText(tblListaProductos.getValueAt(row, 8).toString());
                lblStockAlmacen_T.setText(lista.get(1).toString());
                lbl30d.setText(lista.get(2).toString());
                lbl60d.setText(lista.get(3).toString());
                lbl90d.setText(lista.get(4).toString());
                lbl120d.setText(lista.get(5).toString());
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la cantidad del producto", txtBuscar);
        }
    }

    private void ingresarCantidad() {
        cargarCabecera();
        DlgPedidoAdicionalIngresarCantidad dlgPedidoReposicionIngresoCantidad =
            new DlgPedidoAdicionalIngresarCantidad(myParentFrame, "", true);
        dlgPedidoReposicionIngresoCantidad.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargarCantidadIngresada();
            FarmaVariables.vAceptar = false;
            tblListaProductos.setRowSelectionInterval(VariablesProducto.vPos_PedAdic, VariablesProducto.vPos_PedAdic);
        }
    }

    private void cargarCabecera() {
        try {
            VariablesProducto.vFechaHora_PedAdic = FarmaSearch.getFechaHoraBD(2);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la fecha del sistema : \n" +
                    sql.getMessage(), txtBuscar);

        }
        int pos = tblListaProductos.getSelectedRow();
        VariablesProducto.vPos_PedAdic = pos;
        VariablesProducto.vCodProd_PedAdic = tblListaProductos.getValueAt(pos, 0).toString();
        VariablesProducto.vNomProd_PedAdic = tblListaProductos.getValueAt(pos, 2).toString();
        VariablesProducto.vUnidMed_PedAdic = tblListaProductos.getValueAt(pos, 3).toString();
        VariablesProducto.vNomLab_PedAdic = tblListaProductos.getValueAt(pos, 8).toString();
        VariablesProducto.vValFrac_PedAdic = tblListaProductos.getValueAt(pos, 9).toString();
    }

    private void cargarCantidadIngresada() {
        int pos = tblListaProductos.getSelectedRow();
        if (FarmaVariables.vEconoFar_Matriz) {
            tblListaProductos.setValueAt(VariablesProducto.vCantAutorizado, pos, 7) /*6*/ /*8*/;
            tblListaProductos.repaint();
        }
    }

    private void afectarCantidadSolicitada() {

        int pos = tblListaProductos.getSelectedRow();

        //JCORTEZ 27.10.09 Se sete al mismo valor autorizado y se permite 0 como autorizado
        VariablesProducto.vCantAutorizado = tblListaProductos.getValueAt(pos, 6).toString();
        VariablesProducto.vCodProd_PedAdic = tblListaProductos.getValueAt(pos, 0).toString();
        // if(Integer.parseInt(VariablesProducto.vCantAutorizado) > 0){

        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                        "¿Está seguro que desea realizar la operación?")) {
            try {
                log.debug("Actualizando desde Matriz");
                UtilityInventario.guardarCantidadPedidoAdicionalMatriz(VariablesProducto.vCodProd_PedAdic,
                                                                       VariablesProducto.vCantAutorizado, "S");

                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se realizó la operación satisfactoriamente.", txtBuscar);
            } catch (SQLException sql) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al guardar la cantidad adicional: " + sql, txtBuscar);
            }
            tblListaProductos.setValueAt(VariablesProducto.vCantAutorizado, pos, 7) /*6*/ /*8*/;
            tblListaProductos.repaint();
        } else {
            FarmaUtility.showMessage(this, "Se canceló la operación.", txtBuscar);
        }
        /* }else{
            FarmaUtility.showMessage(this,"El valor solicitado es 0, use la opcion Enter", txtBuscar);
          }*/

        //tblListaProductos.setValueAt(VariablesProducto.vCantAutorizado,pos,7/*6*//*8*/);
        //tblListaProductos.repaint();

    }


    private void mostrarDetallesBlanco() {
        //
        lblLaboratorio.setText("");
        lblStockAlmacen_T.setText("");
        //
        lbl30d.setText("");
        lbl60d.setText("");
        lbl90d.setText("");
        lbl120d.setText("");
    }

    private void cargarHistorialProducto() {
        int pos = tblListaProductos.getSelectedRow();
        VariablesProducto.vCodProd_PedAdic = tblListaProductos.getValueAt(pos, 0).toString();
        VariablesProducto.vNomProd_PedAdic = tblListaProductos.getValueAt(pos, 2).toString();

        DlgPedidoAdicionalHistorial DlgHistorial = new DlgPedidoAdicionalHistorial(myParentFrame, "", true);
        DlgHistorial.setVisible(true);
    }
}


