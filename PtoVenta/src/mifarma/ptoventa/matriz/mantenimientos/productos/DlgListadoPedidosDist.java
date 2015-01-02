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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
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
 * Nombre de la Aplicación : DlgListadoPedidosDist.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      08.11.2007   Creación<br>
 * <br>
 * @author Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */

public class DlgListadoPedidosDist extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgListadoPedidosDist.class);

    private JFrame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private FarmaTableModel tableModel;
    private ArrayList arrayDetalle = new ArrayList();

    private final int COL_EST = 6;
    private final int COL_NUM = 0;

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JScrollPane scrListaPedidos = new JScrollPane();
    private JTable tblListaPedidos = new JTable();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelTitle lblTitle2 = new JPanelTitle();
    private JLabelWhite lblCantProd_ = new JLabelWhite();
    private JLabelWhite lblCantPedidos = new JLabelWhite();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelWhite lblZona = new JLabelWhite();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JButtonLabel btnFechaInicio = new JButtonLabel();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JButtonLabel btnFechaInicio1 = new JButtonLabel();

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgListadoPedidosDist() {
        this(null, "", false);
    }

    public DlgListadoPedidosDist(JFrame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(623, 397));
        this.setDefaultCloseOperation(0);
        this.setTitle("Listado de Pedidos de Distribución");
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        scrListaPedidos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlHeader.setBounds(new Rectangle(5, 10, 600, 40));
        lblTitle2.setBounds(new Rectangle(5, 305, 600, 20));
        lblCantProd_.setText("Cantidad de Pedidos:");
        lblCantProd_.setBounds(new Rectangle(420, 0, 125, 20));
        lblCantPedidos.setBounds(new Rectangle(550, 0, 45, 20));
        lblF1.setBounds(new Rectangle(10, 335, 115, 20));
        lblF1.setText("[F1] Nuevo Pedido");
        lblZona.setBounds(new Rectangle(15, 0, 120, 20));
        lblF2.setBounds(new Rectangle(130, 335, 105, 20));
        lblF2.setText("[F2] Ver Detalle");
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(440, 10, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnBuscar_keyPressed(e);
            }
        });

        txtFechaFin.setBounds(new Rectangle(275, 10, 100, 20));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFechaFin_keyTyped(e);
            }
        });

        txtFechaIni.setBounds(new Rectangle(90, 10, 100, 20));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFechaIni_keyTyped(e);
            }
        });

        btnFechaInicio.setText("Fecha Inicio :");
        btnFechaInicio.setBounds(new Rectangle(10, 10, 80, 20));
        btnFechaInicio.setMnemonic('I');
        btnFechaInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        lblF3.setBounds(new Rectangle(400, 335, 100, 20));
        lblF3.setText("[F3] Ver Todos");
        lblF4.setBounds(new Rectangle(240, 335, 115, 20));
        lblF4.setText("[F5] Anular Pedido");
        btnFechaInicio1.setText("Fecha Fin :");
        btnFechaInicio1.setBounds(new Rectangle(210, 10, 70, 20));
        btnFechaInicio1.setMnemonic('F');
        btnFechaInicio1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });

        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);

        scrListaPedidos.setBounds(new Rectangle(5, 75, 600, 230));
        jPanelTitle1.setBounds(new Rectangle(5, 55, 600, 20));
        btnLista.setText("Listado de Pedidos");
        btnLista.setBounds(new Rectangle(5, 0, 135, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblFCerrar.setBounds(new Rectangle(505, 335, 100, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        lblTitle2.add(lblZona, null);
        lblTitle2.add(lblCantPedidos, null);
        lblTitle2.add(lblCantProd_, null);
        pnlBlanco.add(lblF4, null);
        pnlBlanco.add(lblF3, null);
        pnlBlanco.add(lblF2, null);
        pnlBlanco.add(lblF1, null);
        pnlBlanco.add(lblTitle2, null);
        pnlHeader.add(btnFechaInicio1, null);
        pnlHeader.add(btnFechaInicio, null);
        pnlHeader.add(txtFechaIni, null);
        pnlHeader.add(txtFechaFin, null);
        pnlHeader.add(btnBuscar, null);
        pnlBlanco.add(pnlHeader, null);
        pnlBlanco.add(lblFCerrar, null);
        jPanelTitle1.add(btnLista, null);
        pnlBlanco.add(jPanelTitle1, null);
        scrListaPedidos.getViewport().add(tblListaPedidos, null);
        pnlBlanco.add(scrListaPedidos, null);
        tblListaPedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        /**
    * Se setea la variable FarmaVariables para trabajar los pedidos de distribucion
    * @author JCORTEZ
    * @since  03/12/2007
    * */
        FarmaVariables.vCodLocal = "010";
        log.debug("FarmaVariables.vCodLocal = " + FarmaVariables.vCodLocal);

        initTable();
        cargarListaPedidos();
        lblF3.setVisible(false);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {

        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaPedidosDistribucion, ConstantsProducto.defaultValuesListaPedidosDistribucion,
                                    0);
        FarmaUtility.initSimpleList(tblListaPedidos, tableModel, ConstantsProducto.columnsListaPedidosDistribucion);

        /*if(VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Nuevos))
    {
      tableModel = new FarmaTableModel(ConstantsProducto.columnsListaProductosNvos,
                                          ConstantsProducto.defaultValuesListaProductosNvos,
                                          0);
      FarmaUtility.initSimpleList(tblListaPedidos,tableModel,
                                  ConstantsProducto.columnsListaProductosNvos);
    }else if(VariablesProducto.vOpcion.equalsIgnoreCase(VariablesProducto.vOpcion_Prod_Adicionales))
    {
      tableModel = new FarmaTableModel(ConstantsProducto.columnsListaProductosAdic,
                                        ConstantsProducto.defaultValuesListaLocales_ProdAdic,
                                        0);
      FarmaUtility.initSimpleList(tblListaPedidos,tableModel,
                                  ConstantsProducto.columnsListaProductosAdic);
    }*/
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        lblCantPedidos.setText("" + tblListaPedidos.getRowCount());

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPedidos);
    }

    private void txtProducto_keyReleased(KeyEvent e) {

    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            nuevoPedido();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (tblListaPedidos.getRowCount() > 0) {
                int row = tblListaPedidos.getSelectedRow();
                if (row > -1) {
                    verDetalle();
                } else {
                    FarmaUtility.showMessage(this, "Debe selecionar un pedido", tblListaPedidos);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            //todos
            cargarListaPedidos();
            lblF3.setVisible(false);
        } /*else if (e.getKeyCode() == KeyEvent.VK_F4){

        int row=tblListaPedidos.getSelectedRow();
      if(row>-1){
      validarEstadoAceptado();
      }else{
      FarmaUtility.showMessage(this,"Debe seleccionar un pedido para realizar la accion",txtFechaIni);
      }

    }*/ else if (e.getKeyCode() == KeyEvent.VK_F5) {
            int row = tblListaPedidos.getSelectedRow();
            if (row > -1) {
                validarEstadoAnulado();
            } else {
                FarmaUtility.showMessage(this, "Debe seleccionar un pedido para realizar la accion", txtFechaIni);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFechaIni.getText().trim().length() > 9) {
                FarmaUtility.moveFocus(txtFechaFin);
            }
        }
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFechaFin.getText().trim().length() > 9) {
                FarmaUtility.moveFocus(btnBuscar);
            }
        }
        chkKeyPressed(e);
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
     * Se lista los pedidos existentes de distribucion
     * */
    private void cargarListaPedidos() {

        try {
            DBProducto.cargaPedidosDistribucion(tableModel);
            if (tblListaPedidos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaPedidos, tableModel, COL_NUM, FarmaConstants.ORDEN_DESCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar los pedidos de distribucion.\n" +
                    sql.getMessage(), txtFechaIni);
        }
    }

    private void cargarPedidosPorFecha() {

        try {
            DBProducto.cargaPedidosPorFecha(tableModel, txtFechaIni.getText().trim(), txtFechaFin.getText().trim());
            if (tblListaPedidos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaPedidos, tableModel, COL_NUM, FarmaConstants.ORDEN_DESCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar los pedidos de distribucion.\n" +
                    sql.getMessage(), txtFechaIni);
        }
    }

    /**
     * Se genera un nuevo pedido de Distribucion
     * */
    private void nuevoPedido() {

        DlgListaProductosDist dlgproductos = new DlgListaProductosDist(myParentFrame, "", true);
        dlgproductos.setVisible(true);

        if (FarmaVariables.vAceptar) {
            cargarListaPedidos();
            lblCantPedidos.setText(tblListaPedidos.getRowCount() + "");
        }

    }


    /**
     * Se muestra el detalle del pedido seleccionado
     * */
    private void verDetalle() {

        VariablesProducto.vNumPedidoDist = tblListaPedidos.getValueAt(tblListaPedidos.getSelectedRow(), 0).toString();
        VariablesProducto.vFechaPedido = tblListaPedidos.getValueAt(tblListaPedidos.getSelectedRow(), 1).toString();
        VariablesProducto.vEstadoPedido = tblListaPedidos.getValueAt(tblListaPedidos.getSelectedRow(), 4).toString();

        DlgDetallePedido dlgdetalle = new DlgDetallePedido(myParentFrame, "", true);
        dlgdetalle.setVisible(true);

    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void txtFechaIni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFechaIni, e);
    }

    private void txtFechaFin_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFechaFin, e);
    }

    private void btnBuscar_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (validarCampos()) {
                cargarPedidosPorFecha();
                FarmaUtility.moveFocus(txtFechaIni);
                lblF3.setVisible(true);
                txtFechaIni.setText("");
                txtFechaFin.setText("");
            }
        } else
            chkKeyPressed(e);
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (txtFechaIni.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Inicio.", txtFechaIni);
        } else if (txtFechaFin.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Fin.", txtFechaFin);
        } else if (!FarmaUtility.validaFecha(txtFechaIni.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha.", txtFechaIni);
        } else if (!FarmaUtility.validaFecha(txtFechaFin.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha.", txtFechaFin);
        } else if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }


    private void validarEstadoAceptado() {

        if (tblListaPedidos.getRowCount() > 0) {

            String estado = tblListaPedidos.getValueAt(tblListaPedidos.getSelectedRow(), COL_EST).toString();
            String numero = tblListaPedidos.getValueAt(tblListaPedidos.getSelectedRow(), COL_NUM).toString();

            log.debug("ESTADO :" + estado);
            if (!estado.equalsIgnoreCase("")) {
                if (estado.equalsIgnoreCase(ConstantsProducto.EST_ACEPTADO)) {
                    FarmaUtility.showMessage(this, "Este pedido ya ha sido aceptado", tblListaPedidos);
                } else if (estado.equalsIgnoreCase(ConstantsProducto.EST_ANULADO)) {
                    FarmaUtility.showMessage(this, "Este pedido no se puede confirmar, ya que ha sido anulado",
                                             tblListaPedidos);
                } else if (estado.equalsIgnoreCase(ConstantsProducto.EST_PENDIENTE)) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Esta seguro de aceptar el pedido?")) {

                        if (validarEstado(numero)) {
                            int row = tblListaPedidos.getSelectedRow();
                            if (row > -1) {
                                aceptarPedido();
                                cargarListaPedidos();
                            }
                        }
                    }
                }
            }
        } else {
            FarmaUtility.showMessage(this, "No existe ningun pedido para realizar esta acción.", tblListaPedidos);
        }

    }

    /**
     * Se valida antes de anular un pedido
     * */
    private void validarEstadoAnulado() {

        if (tblListaPedidos.getRowCount() > 0) {

            String estado = tblListaPedidos.getValueAt(tblListaPedidos.getSelectedRow(), COL_EST).toString();
            String numero = tblListaPedidos.getValueAt(tblListaPedidos.getSelectedRow(), COL_NUM).toString();

            log.debug("ESTADO :" + estado);
            if (!estado.equalsIgnoreCase(" ")) {
                if (estado.equalsIgnoreCase(ConstantsProducto.EST_ACEPTADO)) {
                    FarmaUtility.showMessage(this, "Este pedido no se puede anular, ya que ha sido aceptado",
                                             tblListaPedidos);
                } else if (estado.equalsIgnoreCase(ConstantsProducto.EST_ANULADO)) {
                    FarmaUtility.showMessage(this, "Este pedido ya ha sido anulado", tblListaPedidos);
                } else if (estado.equalsIgnoreCase(ConstantsProducto.EST_PENDIENTE)) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Esta seguro de anular el pedido?")) {
                        if (validarEstado(numero)) {
                            int row = tblListaPedidos.getSelectedRow();
                            if (row > -1) {
                                anularPedido();
                                cargarListaPedidos();
                            }
                        }
                    }
                }
            }
        } else {
            FarmaUtility.showMessage(this, "No existe ningun pedido para realizar esta acción.", tblListaPedidos);
        }
    }

    /**
     * Se valida el estado  del pedido en BBDD al querer anularla
     */
    private boolean validarEstado(String NumPedido) {

        boolean estado = true;
        try {
            DBProducto.validaEstado(NumPedido);
        } catch (SQLException e) {
            estado = false;
            if (e.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "Este pedido ya ha sido aceptado", tblListaPedidos);
            } else if (e.getErrorCode() == 20002) {
                FarmaUtility.showMessage(this, "Este pedido ya ha sido anulado.¡No puede anular este pedido!",
                                         tblListaPedidos);
            } else if (e.getErrorCode() == 20003) {
                FarmaUtility.showMessage(this, "El numero de pedido no existe", tblListaPedidos);
            } else {
                log.error("", e);
                FarmaUtility.showMessage(this, "ocurrio un error al validar el pedido" + e.getMessage(),
                                         tblListaPedidos);
            }
        }
        return estado;
    }

    /**
     * Se acepta el pedido
     */
    private void aceptarPedido() {
        int row = tblListaPedidos.getSelectedRow();

        if (row > -1) {
            String numero = tblListaPedidos.getValueAt(row, COL_NUM).toString();
            try {
                DBProducto.aceptarPedido(numero);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se acepto el pedido con exito", tblListaPedidos);
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrio un error al aceptar el pedido.\n" +
                        sql.getMessage(), tblListaPedidos);
            }
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un pedido para aceptarlo", null);
        }
    }

    /**
     * Se Anula el Pedido
     */
    private void anularPedido() {
        int row = tblListaPedidos.getSelectedRow();

        if (row > -1) {
            String numero = tblListaPedidos.getValueAt(row, COL_NUM).toString();
            try {
                DBProducto.anularPedido(numero);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se realizo la anulacion con exito", tblListaPedidos);
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrio un error al anular el pedido.\n" +
                        sql.getMessage(), tblListaPedidos);
            }
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un pedido para anular", null);
        }
    }


}
