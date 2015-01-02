package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgResumenPedEspecialMatriz.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ   04.01.10   Creación<br>
 * <br>
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgResumenPedEspecialMatriz extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgResumenPedEspecialMatriz.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    //String vEstadoNota;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JButtonLabel lblCantProductos = new JButtonLabel();
    private JButtonLabel lblCantProductosT = new JButtonLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JButtonLabel btnProducto = new JButtonLabel();
    private JLabelFunction lblF3 = null;
    private JLabelFunction lblEnter = null;

    private boolean indCierraVenta = true;
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblDescLocal = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgResumenPedEspecialMatriz() {
        this(null, "", false);
    }

    public DlgResumenPedEspecialMatriz(Frame parent, String title, boolean modal) {
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
        lblEnter = new JLabelFunction();
        lblEnter.setBounds(new Rectangle(5, 330, 180, 20));
        lblEnter.setText("[ ENTER ] Modificar Cantidad");
        jLabelWhite1.setText("Local Destino :");
        jLabelWhite1.setBounds(new Rectangle(370, 10, 80, 25));
        lblDescLocal.setBounds(new Rectangle(460, 5, 245, 30));
        lblDescLocal.setFont(new Font("SansSerif", 1, 13));
        lblF3 = new JLabelFunction();
        lblF3.setBounds(new Rectangle(195, 330, 170, 20));
        lblF3.setText("[ F3 ] Agregar Producto");
        this.setSize(new Dimension(739, 387));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Resumen Pedido Especial");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnllHeader1.setBounds(new Rectangle(5, 10, 725, 45));
        pnlTitle1.setBounds(new Rectangle(5, 65, 725, 25));
        scrListaProductos.setBounds(new Rectangle(5, 90, 725, 235));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(645, 330, 85, 20));
        btnRelacionProductos.setText("Relación de Productos Especiales");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 205, 15));
        btnRelacionProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        lblF2.setText("[ F5 ] Borrar Producto");
        lblF2.setBounds(new Rectangle(370, 330, 150, 20));
        lblCantProductos.setText("Items:");
        lblCantProductos.setBounds(new Rectangle(614, 5, 44, 15));
        lblCantProductosT.setBounds(new Rectangle(655, 5, 51, 15));
        lblCantProductosT.setText("0");
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(525, 330, 105, 20));
        txtBuscar.setBounds(new Rectangle(75, 10, 275, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        btnProducto.setText("Producto:");
        btnProducto.setBounds(new Rectangle(15, 13, 55, 15));
        btnProducto.setMnemonic('P');
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(lblCantProductosT, null);
        pnlTitle1.add(lblCantProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
        pnllHeader1.add(lblDescLocal, null);
        pnllHeader1.add(jLabelWhite1, null);
        pnllHeader1.add(btnProducto, null);
        pnllHeader1.add(txtBuscar, null);
        jContentPane.add(pnllHeader1, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblEnter, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.setContentPane(jContentPane);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        cargarResumenPedidoEsp();
        log.debug("Productos agregados " + VariablesProducto.vArrayProductosEspeciales.size());
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {

        tblListaProductos.getTableHeader().setReorderingAllowed(false);
        tblListaProductos.getTableHeader().setResizingAllowed(false);

        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaResumenPedidoEspMatriz, ConstantsProducto.defaultValuesListaResumenPedidoEspMatriz,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                    ConstantsProducto.columnsListaResumenPedidoEspMatriz);
    }

    /**
     * metodo encargado de cargas el resumen de pedido especial
     * **/
    private void cargarResumenPedidoEsp() {
        tableModel.clearTable();
        if (VariablesProducto.vArrayProductosEspeciales.size() > 0) {
            for (int i = 0; i < VariablesProducto.vArrayProductosEspeciales.size(); i++) {
                tableModel.insertRow((ArrayList)VariablesProducto.vArrayProductosEspeciales.get(i));
            }
            FarmaUtility.moveFocusJTable(tblListaProductos);
        }

        lblCantProductosT.setText(VariablesProducto.vArrayProductosEspeciales.size() + "");
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnRelacionProductos);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
        tblListaProductos.repaint();

        lblCantProductosT.setText("" + tblListaProductos.getRowCount());
        log.debug("Open - VariablesInventario.vEsModificado " + VariablesProducto.vEsModificado);
        if (VariablesProducto.vEsModificado) {
            DlgListaProdEspecialMatriz dlglistaProdEspeciales =
                new DlgListaProdEspecialMatriz(myParentFrame, "", true);
            dlglistaProdEspeciales.setVisible(true);

            if (FarmaVariables.vAceptar) {
                lblDescLocal.setText(VariablesProducto.vCodLocalDestino + " - " + VariablesProducto.vDescLocalDestino);
                log.debug(" cargan los productos seleccionados:::::");
                cargarResumenPedidoEsp();
            } else {
                log.debug(" CERRARRRR:::::");
                cerrarVentana(false);
            }

            VariablesInventario.vIrResumen = false;

        }

        //
        // btnProducto.doClick();

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);

    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 1);

        if (Character.isLetter(e.getKeyChar())) {
            //log.debug("Presiono una letra");
            if (VariablesProducto.vKeyPress == null) {
                //VariablesVentas.vLetraBusqueda = e.getKeyChar() + "";;
                //log.debug("VariablesVentas.vLetraBusqueda  " + VariablesVentas.vLetraBusqueda);
                VariablesProducto.vKeyPress = e;
                agregarProducto();
                VariablesProducto.vKeyPress = null;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            int rowSel = tblListaProductos.getSelectedRow();

            if (rowSel >= 0) {
                indCierraVenta = false;
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro de quitar el producto del pedido ?")) {

                    //Agregado por DVELIZ 18.10.08
                    borrarSeleccionListProducto(rowSel);
                    tableModel.deleteRow(rowSel);
                    VariablesProducto.vArrayProductosEspeciales.remove(rowSel);
                    if (VariablesProducto.vArrayListaProdsEsp.size() > 0)
                        VariablesProducto.vArrayListaProdsEsp.remove(rowSel);

                    log.debug("4");
                    lblCantProductosT.setText(VariablesProducto.vArrayProductosEspeciales.size() + "");


                    if (VariablesProducto.vArrayProductosEspeciales.size() > 0) {
                        FarmaUtility.moveFocusJTable(tblListaProductos);
                    } else {
                        txtBuscar.setText("");
                    }
                    FarmaUtility.moveFocus(txtBuscar);
                }
            } else {
                FarmaUtility.showMessage(this, "Seleccione el Producto que desea Eliminar", txtBuscar);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {

            if (!VariablesProducto.flag_F3) {
                VariablesProducto.flag_F3 = true;
                DlgListaProdEspecialMatriz dlglistaProdEspeciales =
                    new DlgListaProdEspecialMatriz(myParentFrame, "", true);
                dlglistaProdEspeciales.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    lblDescLocal.setText(VariablesProducto.vCodLocalDestino + " - " +
                                         VariablesProducto.vDescLocalDestino);
                    log.debug(" cargan los productos seleccionados");
                    tblListaProductos.repaint();
                    cargarResumenPedidoEsp();
                } else {
                    log.debug("VariablesProducto.vArrayProductosEspeciales-->" +
                              VariablesProducto.vArrayProductosEspeciales);
                    log.debug("VariablesProducto.vArrayListaProdsEsp-->" + VariablesProducto.vArrayListaProdsEsp);

                    cargarResumenPedidoEsp();
                    tblListaProductos.repaint();
                    cerrarVentana(false);
                }

                VariablesProducto.flag_F3 = false;
                VariablesProducto.vIrResumen = false;
            } else {
                log.debug("Ya se tiene abierto al ventana de listado de productos especiales !");
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (VariablesProducto.vArrayProductosEspeciales.size() < 1) {
                FarmaUtility.showMessage(this, "Seleccione productos para el pedido. !", null);
            } else {
                // Validara que el pedido este en estado M si esta modificando el pedido.
                if (VariablesProducto.vFModificar && !VariablesProducto.vFNuevo)
                    if (!validaAccionModificacion())
                        return;

                //NO SE VALIDARA CANTIDAD MAXIMA DE PRODUCTOS EN EL PEDIDO ESPECIAL MATRIZ
                //if(validaItemPedEspecial(ConstantsInventario.CONS_ING_PED_ESPC)){
                String mensaje = "Usted. esta confirmando el ingreso. Esta seguro de generar el pedido?";
                if (VariablesProducto.vFModificar && !VariablesProducto.vFNuevo)
                    mensaje = "Esta seguro de concluir la modificación?";
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, mensaje)) {
                    generaPedido();
                }
                //}

            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            log.debug("indCierraVenta:" + indCierraVenta);
            log.debug("VariablesProducto.vIrResumen:" + VariablesProducto.vIrResumen);
            log.debug("VariablesProducto.vFModificar:" + VariablesProducto.vFModificar);
            log.debug("VariablesProducto.vFNuevo:" + VariablesProducto.vFNuevo);
            //log.debug("validaAccionModificacion:"+validaAccionModificacion);

            if (indCierraVenta) {
                if (!VariablesProducto.vIrResumen) {
                    // Esto solo lo debe de hacer cuando ha hecho la opcion de Modificar
                    // No cuando es un pedido Nuevo o esta en Ver Pedido.
                    // Validara que el pedido este en estado M si esta modificando el pedido.
                    if (VariablesProducto.vFModificar && !VariablesInventario.vFNuevo)
                        if (!validaAccionModificacion())
                            return;

                    if (VariablesProducto.vFModificar) {
                        try {
                            DBInventario.setEstadoPedidoEspecial(VariablesInventario.vNumPedidoEspecial,
                                                                 ConstantsInventario.EST_PENDIENTE);
                            VariablesProducto.vEsModificado = false;
                            log.debug("Close - VariablesProducto.vEsModificado " + VariablesProducto.vEsModificado);
                        } catch (SQLException f) {
                            log.error("", f);
                        }
                    }
                    cerrarVentana(false);
                }
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        limpiarVariables();
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void confimarPedido() {
        try {
            DBInventario.confirmarPedidoDet(VariablesInventario.vNumPedidoEspecial,
                                            VariablesInventario.vArrayProductosDet);
            DBInventario.confirmarPedidoCab(VariablesInventario.vNumPedidoEspecial);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se confirmo el pedido especial correctamente.", null);
            cerrarVentana(true);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Ocurrio un error al confirmar el pedido.\n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void anularPedido() {

        try {
            DBInventario.anularPedidoEspecial(VariablesInventario.vNumPedidoEspecial);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "El Pedido fue anulado exitosamente", txtBuscar);
            cerrarVentana(true);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Ocurrio un error al confirmar el pedido.\n" +
                    e.getMessage(), txtBuscar);
        }


    }

    /**
     * */
    private void cargarDatos(int row) {
        if (row >= 0) {
            VariablesProducto.vCodProd_esp = tblListaProductos.getValueAt(row, 0).toString();
            VariablesProducto.vNomProd_esp = tblListaProductos.getValueAt(row, 1).toString();
            VariablesProducto.vUnidMed_esp = tblListaProductos.getValueAt(row, 2).toString();
            VariablesProducto.vNomLab_esp = tblListaProductos.getValueAt(row, 3).toString();
            VariablesProducto.vCantIng =
                    tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 4).toString();
            VariablesProducto.vEstado = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 6).toString();
            VariablesProducto.vPosi = tblListaProductos.getSelectedRow();
        }
    }


    /**
     *
     * */
    private void limpiarVariables() {

        VariablesProducto.vCodProd_esp = "";
        VariablesProducto.vNomProd_esp = "";
        VariablesProducto.vUnidMed_esp = "";
        VariablesProducto.vNomLab_esp = "";
        VariablesProducto.vStkFisico_esp = "";
        VariablesProducto.vCantIng = "";
        VariablesProducto.vValFrac_esp = "";
        VariablesProducto.vPrecVta_esp = "";
        VariablesProducto.vArrayProductosEspeciales = new ArrayList();
        VariablesProducto.vIrResumen = false;
        VariablesProducto.vFModificar = false;
        VariablesProducto.vFNuevo = false;
        VariablesProducto.vCodLocalDestino = "";
        VariablesProducto.vDescLocalDestino = "";
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaProductos.getSelectedRow() >= 0) {

                e.consume();
                /*//Se validara si no excede de los item de productos de pedido especial
          if (validaItemPedEspecial(ConstantsInventario.CONS_ING_PROD_ESPC)) {*/
                cargaCabeceraIngreseCantidad();
                DlgEspecialCantIngresoMatriz dlgEspecialCantIngreso =
                    new DlgEspecialCantIngresoMatriz(myParentFrame, "", true);
                dlgEspecialCantIngreso.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    FarmaVariables.vAceptar = false;
                    tblListaProductos.setValueAt(VariablesProducto.vCantIng, VariablesProducto.vPosi, 5);
                    tblListaProductos.setRowSelectionInterval(VariablesProducto.vPosi, VariablesProducto.vPosi);
                    tblListaProductos.repaint();
                }
                // }
                indCierraVenta = false;
                VariablesProducto.vIrResumen = false;
            } else {
                FarmaUtility.showMessage(this, "Seleccione El Producto a Cambiar la Cantidad", txtBuscar);
            }
        } else {
            chkKeyPressed(e);
            indCierraVenta = true;
        }
    }


    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 1);
        //chkKeyPressed(e);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    /**
     * metodo que genera el pedido especial Matriz
     * */
    private void generaPedido() {

        try {
            String vMensaje = "";
            int cantItem = VariablesProducto.vArrayProductosEspeciales.size();

            log.debug("VariablesProducto.vEsModificado (false-->nuevo; true-->modificado) :" +
                      VariablesProducto.vEsModificado);
            if (VariablesProducto.vEsModificado) { //actualmente deshabilitado
                //POR DB SE LE AGREGARA UN 9 a la IZQ
                VariablesProducto.vNumPedidoEspecialMatriz =
                        FarmaSearch.getNuSecNumeracion(VariablesProducto.SEC_PED_ESP, 9);
                VariablesProducto.vNumPedidoEspecialMatriz = "9" + VariablesProducto.vNumPedidoEspecialMatriz.trim();
                log.debug("Numero de pedido Matriz generado :" + VariablesProducto.vNumPedidoEspecialMatriz);
                DBProducto.agregarCabeceraPedEsp(VariablesProducto.vNumPedidoEspecialMatriz, cantItem + "",
                                                 VariablesProducto.vCodLocalDestino);
                DBProducto.agregarDetallePedEsp(VariablesProducto.vArrayProductosEspeciales,
                                                VariablesProducto.vNumPedidoEspecialMatriz,
                                                VariablesProducto.vCodLocalDestino);
                FarmaSearch.updateNumera(ConstantsInventario.SEC_PED_ESP);
                vMensaje = "Se guardo el pedido especial exitosamente.";
            } else {
                /*DBInventario.agregarCabeceraPedEsp(VariablesInventario.vNumPedidoEspecial,cantItem+"");
          DBInventario.delDetallePedido(VariablesInventario.vNumPedidoEspecial);
          DBInventario.agregarDetallePedEsp(VariablesInventario.vArrayProductosEspeciales,VariablesInventario.vNumPedidoEspecial);
          DBInventario.setEstadoPedidoEspecial(VariablesInventario.vNumPedidoEspecial, ConstantsInventario.EST_PENDIENTE);*/
                vMensaje = "Se modifico el pedido especial exitosamente.";
            }

            FarmaUtility.aceptarTransaccion();
            //FarmaUtility.showMessage(this,vMensaje,null);
            limpiarVariables();
            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error: no se puede generar el pedido especial" + sql.getMessage(), null);
            FarmaUtility.moveFocus(txtBuscar);
            log.error("", sql);
        }

    }

    private void cargaCabeceraIngreseCantidad() {
        VariablesProducto.vCodProd_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0).toString();
        VariablesProducto.vNomProd_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString();
        VariablesProducto.vUnidMed_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 2).toString();
        VariablesProducto.vNomLab_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 3).toString();
        //VariablesInventario.vStkFisico_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
        VariablesProducto.vCantIng = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 5).toString();
        //VariablesInventario.vValFrac_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),7).toString();
        //VariablesInventario.vPrecVta_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),8).toString();
        VariablesProducto.vPosi = tblListaProductos.getSelectedRow();
        VariablesProducto.flag_modificarCantidad = true;
    }


    public void borrarSeleccionListProducto(int rowSel) {
        String vCod = ((ArrayList)VariablesProducto.vArrayProductosEspeciales.get(rowSel)).get(0).toString();
        for (int i = 0; i < VariablesProducto.tableModelEspecialMatriz.data.size(); i++) {
            if (((ArrayList)VariablesProducto.tableModelEspecialMatriz.getRow(i)).contains(vCod)) {
                VariablesProducto.tableModelEspecialMatriz.setValueAt(new Boolean(false), i, 0);
                VariablesProducto.tableModelEspecialMatriz.setValueAt(" ", i, 6);
                break;
            }
        }
    }

    private void agregarProducto() {
        VariablesProducto.flag_F3 = true;
        DlgListaProdEspecialMatriz dlglistaProdEspeciales = new DlgListaProdEspecialMatriz(myParentFrame, "", true);
        dlglistaProdEspeciales.setVisible(true);

        if (FarmaVariables.vAceptar) {
            log.debug(" cargan los productos seleccionados");
            cargarResumenPedidoEsp();

        } else {
            log.debug(" CERRARRRR:::::");
            cerrarVentana(false);
        }


        VariablesProducto.flag_F3 = false;
        VariablesProducto.vIrResumen = false;
        txtBuscar.setText("");
        VariablesProducto.vCodFiltro = "";
        VariablesProducto.vIndF11 = false;
    }


    /**
     * Por ahora ni habra opcion de modificacion, se confirma
     * */
    public boolean validaAccionModificacion() {
        boolean bResp = false;
        String pRpta = "";
        try {
            pRpta = DBInventario.getEstadoActualPedido(VariablesInventario.vNumPedidoEspecial);
            if (pRpta.trim().equalsIgnoreCase("M"))
                bResp = true;
            else if (pRpta.trim().equalsIgnoreCase("P")) {
                bResp = false;
                FarmaUtility.showMessage(this, "El pedido ya fue modificado.No puede realizar la operación.",
                                         txtBuscar);
            } else if (pRpta.trim().equalsIgnoreCase("N")) {
                bResp = false;
                FarmaUtility.showMessage(this, "El pedido se encuentre anulado.No puede realizar la operación.",
                                         txtBuscar);
            }


        } catch (SQLException f) {
            log.error("", f);
            bResp = false;
        }

        if (!bResp)
            cerrarVentana(false);

        return bResp;
    }
}
