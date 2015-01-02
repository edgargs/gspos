package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
 * Nombre de la Aplicación : DlgDetalleTransferencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      16.07.2007   Creación<br>
 * <br>
 * @author  Jorge Cortez Alvarez <br>
 * @version 1.0<br>
 *
 */

public class DlgDetallePedido extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgDetallePedido.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    FarmaTableModel tableModel2;
    //private boolean matriz = false;

    private final int COL_EST = 5;
    private final int COL_COD = 0;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblnumero = new JLabelWhite();
    private JLabelWhite lblNumNota = new JLabelWhite();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JLabelWhite lblestado = new JLabelWhite();
    private JLabelWhite lblFechaTrans = new JLabelWhite();
    private JLabelWhite lblfecha = new JLabelWhite();
    private JScrollPane scrListaItems = new JScrollPane();
    private JTable tblItems = new JTable();
    private JScrollPane scrListaLocales = new JScrollPane();
    private JTable tblLocales = new JTable();
    private JPanelTitle lblTitle3 = new JPanelTitle();
    private JLabelWhite lblCantLoc = new JLabelWhite();
    private JButtonLabel btnLista = new JButtonLabel();
    private JPanelTitle lblTitle4 = new JPanelTitle();
    private JButtonLabel btnProductos = new JButtonLabel();
    private JLabelWhite lblCantProd = new JLabelWhite();
    private JLabelFunction lblF5 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */


    public DlgDetallePedido() {
        this(null, "", false);
    }

    public DlgDetallePedido(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(538, 376));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle de Pedido Distribucion ");
        this.setDefaultCloseOperation(0);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //this_keyPressed(e);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 10, 520, 305));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(430, 320, 90, 20));
        lblnumero.setBounds(new Rectangle(245, 30, 115, 15));
        lblnumero.setFont(new Font("SansSerif", 0, 11));
        lblnumero.setForeground(new Color(255, 130, 14));
        lblNumNota.setText("Numero de Pedido : ");
        lblNumNota.setBounds(new Rectangle(245, 10, 125, 15));
        lblNumNota.setForeground(new Color(255, 130, 14));
        lblEstado.setBounds(new Rectangle(140, 10, 85, 15));
        lblEstado.setForeground(new Color(255, 130, 14));
        lblEstado.setText("Estado :");
        lblestado.setBounds(new Rectangle(140, 30, 85, 20));
        lblestado.setFont(new Font("SansSerif", 0, 11));
        lblestado.setForeground(new Color(255, 130, 14));
        lblFechaTrans.setText("Fecha  de Pedido :");
        lblFechaTrans.setBounds(new Rectangle(10, 10, 145, 15));
        lblFechaTrans.setForeground(new Color(255, 130, 14));
        lblfecha.setBounds(new Rectangle(10, 30, 105, 15));
        lblfecha.setFont(new Font("SansSerif", 0, 11));
        lblfecha.setForeground(new Color(255, 130, 14));
        scrListaItems.setBounds(new Rectangle(5, 80, 510, 95));
        tblItems.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblItems_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblItems_keyReleased(e);
            }
        });
        scrListaLocales.setBounds(new Rectangle(5, 200, 510, 100));
        tblLocales.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLocales_keyPressed(e);
            }
        });
        lblTitle3.setBounds(new Rectangle(5, 180, 510, 20));
        lblCantLoc.setBounds(new Rectangle(135, 0, 50, 20));
        btnLista.setText("Listado de Locales :");
        btnLista.setBounds(new Rectangle(5, 0, 120, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblTitle4.setBounds(new Rectangle(5, 60, 510, 20));
        btnProductos.setText("Listado de Productos :");
        btnProductos.setBounds(new Rectangle(5, 0, 140, 20));
        btnProductos.setMnemonic('P');
        btnProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProductos_actionPerformed(e);
            }
        });
        lblCantProd.setBounds(new Rectangle(145, 0, 50, 20));
        lblF5.setText("[ F5 ] Anular Pedido en Local");
        lblF5.setBounds(new Rectangle(250, 320, 175, 20));
        scrListaItems.getViewport();
        scrListaLocales.getViewport();
        scrListaLocales.getViewport();
        lblTitle4.add(btnProductos, null);
        lblTitle4.add(lblCantProd, null);
        lblTitle3.add(btnLista, null);
        lblTitle3.add(lblCantLoc, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(lblTitle4, null);
        pnlTitle1.add(lblTitle3, null);
        scrListaLocales.getViewport().add(tblLocales, null);
        pnlTitle1.add(scrListaLocales, null);
        scrListaItems.getViewport().add(tblItems, null);
        pnlTitle1.add(scrListaItems, null);
        pnlTitle1.add(lblfecha, null);
        pnlTitle1.add(lblFechaTrans, null);
        pnlTitle1.add(lblestado, null);
        pnlTitle1.add(lblEstado, null);
        pnlTitle1.add(lblNumNota, null);
        pnlTitle1.add(lblnumero, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCabecera();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {
        initTable();
        cargarProductos();
        cargarDetalle();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaDetalleProductos, ConstantsProducto.defaultValuesListaDetalleProductos,
                                    0);
        FarmaUtility.initSimpleList(tblItems, tableModel, ConstantsProducto.columnsListaDetalleProductos);

        tableModel2 =
                new FarmaTableModel(ConstantsProducto.columnsListaDetalleLocales, ConstantsProducto.defaultValuesListaDetalleLocales,
                                    0);
        FarmaUtility.initSimpleList(tblLocales, tableModel2, ConstantsProducto.columnsListaDetalleLocales);
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblItems);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tblItems.getRowCount() > 0) {
                if (tblLocales.getRowCount() > 0) {
                    int row = tblLocales.getSelectedRow();
                    if (row > -1) {
                        //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"Esta seguro de anular la distribucion para este local?")){
                        validarEstadoAnulado();
                        //}
                    } else {
                        FarmaUtility.showMessage(this, "Debe selecionar un local para anular la distribucion",
                                                 tblLocales);
                    }
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void btndestino_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblItems_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblItems_keyReleased(KeyEvent e) {
        int filaSelect = tblItems.getSelectedRow();
        if (filaSelect > -1) {
            String codprod = (String)tblItems.getValueAt(filaSelect, 0);
            cargarLocales(codprod);
            lblCantLoc.setText("" + tblLocales.getRowCount());
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
     * Cargamos el detalle del equipo a mostrar.
     * */
    private void cargarDetalle() {

        lblnumero.setText(VariablesProducto.vNumPedidoDist);
        lblfecha.setText(VariablesProducto.vFechaPedido);
        lblestado.setText(VariablesProducto.vEstadoPedido);

    }

    private void cargarProductos() {
        try {
            DBProducto.cargaDetalleProductos(tableModel, VariablesProducto.vNumPedidoDist);
            lblCantProd.setText(tblItems.getRowCount() + "");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar detalle de transferencia", null);
        }
    }

    private void cargarLocales(String CodProd) {
        try {
            DBProducto.cargaDetalleLocales(tableModel2, VariablesProducto.vNumPedidoDist, CodProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar detalle de transferencia", null);
        }
    }


    private void validarEstadoAnulado() {

        if (tblItems.getRowCount() > 0) {

            String estado = tblLocales.getValueAt(tblLocales.getSelectedRow(), COL_EST).toString();
            String producto = tblItems.getValueAt(tblItems.getSelectedRow(), COL_COD).toString();
            String localdist = tblLocales.getValueAt(tblLocales.getSelectedRow(), COL_COD).toString();

            log.debug("ESTADO :" + estado);
            if (!estado.equalsIgnoreCase(" ")) {
                if (estado.equalsIgnoreCase(ConstantsProducto.EST_ACEPTADO)) {
                    FarmaUtility.showMessage(this, "La distribucion no se puede anular, ya que ha sido aceptada.",
                                             tblLocales);
                } else if (estado.equalsIgnoreCase(ConstantsProducto.EST_ANULADO)) {
                    FarmaUtility.showMessage(this, "La distribucion ya ha sido anulada.", tblLocales);
                } else if (estado.equalsIgnoreCase(ConstantsProducto.EST_PENDIENTE)) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Esta seguro de anular la distribucion para este local?")) {
                        if (validarEstado(VariablesProducto.vNumPedidoDist, producto, localdist)) {
                            int row = tblLocales.getSelectedRow();
                            if (row > -1) {
                                anularPedidoLocal(VariablesProducto.vNumPedidoDist, producto, localdist);
                            }
                        }
                    }
                }
            }
        } else {
            FarmaUtility.showMessage(this, "No existe ningun producto para realizar esta accion.", tblItems);
        }

    }

    /**
     * Se valida el estado de la distribucion para el local
     */
    private boolean validarEstado(String NumPedido, String producto, String local) {

        boolean estado = true;
        try {
            DBProducto.validaEstadoProdLocal(NumPedido, producto, local);
        } catch (SQLException e) {
            estado = false;
            if (e.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "La distribucion ya ha sido aceptada.", tblLocales);
            } else if (e.getErrorCode() == 20002) {
                FarmaUtility.showMessage(this, "Este distribucion ya ha sido anulada.¡No puede anular!", tblLocales);
            } else if (e.getErrorCode() == 20003) {
                FarmaUtility.showMessage(this, "El numero de pedido no existe.", tblLocales);
            } else {
                log.error("", e);
                FarmaUtility.showMessage(this, "ocurrio un error al validar la distribucion" + e.getMessage(),
                                         tblLocales);
            }
        }
        return estado;
    }


    /**
     * Se anula el pedido a nivel de local
     * @author: JCORTEZ
     * @since: 04/02/2007
     * */
    private void anularPedidoLocal(String vNumPedidoDist, String producto, String localdist) {
        try {

            DBProducto.anularPedidoLocal(VariablesProducto.vNumPedidoDist, producto, localdist);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se realizo la anulacion con exito", tblLocales);
            cargarLocales(producto);
            FarmaUtility.findTextInJTable(tblLocales, localdist, 0, 0);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al anular pedido en local", null);
        }

    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblItems);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLocales);
    }

    private void tblLocales_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


}
