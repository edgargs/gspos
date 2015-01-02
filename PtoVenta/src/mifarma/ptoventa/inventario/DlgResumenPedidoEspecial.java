package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgResumenPedidoEspecial extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgResumenPedidoEspecial.class);

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

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgResumenPedidoEspecial() {
        this(null, "", false);
    }

    public DlgResumenPedidoEspecial(Frame parent, String title, boolean modal) {
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
        lblEnter.setBounds(new Rectangle(10, 330, 180, 20));
        lblEnter.setText("[ ENTER ] Modificar Cantidad");
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
        pnllHeader1.setBounds(new Rectangle(10, 10, 710, 43));
        pnlTitle1.setBounds(new Rectangle(11, 65, 710, 25));
        scrListaProductos.setBounds(new Rectangle(11, 90, 710, 235));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(635, 330, 85, 20));
        btnRelacionProductos.setText("Relacion de Productos Especiales");
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
        txtBuscar.setBounds(new Rectangle(75, 10, 330, 20));
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
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaResumenPedidoEsp, ConstantsInventario.defaultValuesListaResumenPedidoEsp,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsInventario.columnsListaResumenPedidoEsp);
    }

    /**
     * metodo encargado de cargas el resumen de pedido especial
     * @author jcallo
     * @since  16.10.2008
     * **/
    private void cargarResumenPedidoEsp() {
        tableModel.clearTable();
        if (VariablesInventario.vArrayProductosEspeciales.size() > 0) {
            for (int i = 0; i < VariablesInventario.vArrayProductosEspeciales.size(); i++) {
                tableModel.insertRow((ArrayList)VariablesInventario.vArrayProductosEspeciales.get(i));
            }
            //FarmaUtility.moveFocusJTable(tblListaProductos);
        }

        lblCantProductosT.setText(VariablesInventario.vArrayProductosEspeciales.size() + "");
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
        lblCantProductosT.setText("" + tblListaProductos.getRowCount());
        log.debug("Open - VariablesInventario.vEsModificado " + VariablesInventario.vEsModificado);
        if (VariablesInventario.vEsModificado) {
            DlgEspecialListaProductos dlglistaProdEspeciales = new DlgEspecialListaProductos(myParentFrame, "", true);
            dlglistaProdEspeciales.setVisible(true);

            if (FarmaVariables.vAceptar) {
                log.debug(" cargan los productos seleccionados");
                cargarResumenPedidoEsp();
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
            if (VariablesVentas.vKeyPress == null) {
                //VariablesVentas.vLetraBusqueda = e.getKeyChar() + "";;
                //log.debug("VariablesVentas.vLetraBusqueda  " + VariablesVentas.vLetraBusqueda);
                VariablesVentas.vKeyPress = e;
                agregarProducto();
                VariablesVentas.vKeyPress = null;
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
                    VariablesInventario.vArrayProductosEspeciales.remove(rowSel);
                    lblCantProductosT.setText(VariablesInventario.vArrayProductosEspeciales.size() + "");


                    if (VariablesInventario.vArrayProductosEspeciales.size() > 0) {
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

            if (!VariablesInventario.flag_F3) {
                VariablesInventario.flag_F3 = true;
                DlgEspecialListaProductos dlglistaProdEspeciales =
                    new DlgEspecialListaProductos(myParentFrame, "", true);
                dlglistaProdEspeciales.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    log.debug(" cargan los productos seleccionados");
                    cargarResumenPedidoEsp();
                }
                VariablesInventario.flag_F3 = false;
                VariablesInventario.vIrResumen = false;
            } else {
                log.debug("Ya se tiene abierto al ventana de listado de productos especiales !");
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            //add jcallo validacion de que se especifique al menos un producto por pedido
            if (VariablesInventario.vArrayProductosEspeciales.size() < 1) {
                FarmaUtility.showMessage(this, "Seleccione los productos del pedido. !", null);
            } else {
                // Validara que el pedido este en estado M si esta modificando el pedido.
                if (VariablesInventario.vFModificar && !VariablesInventario.vFNuevo)
                    if (!validaAccionModificacion())
                        return;

                if (validaItemPedEspecial(ConstantsInventario.CONS_ING_PED_ESPC)) {
                    String mensaje = "Esta seguro de generar el pedido?";
                    if (VariablesInventario.vFModificar && !VariablesInventario.vFNuevo)
                        mensaje = "Esta seguro de concluir la modificación?";
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, mensaje)) {
                        generaPedido();
                    }
                }

            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            log.debug("indCierraVenta:" + indCierraVenta);
            log.debug("VariablesInventario.vIrResumen:" + VariablesInventario.vIrResumen);
            log.debug("VariablesInventario.vFModificar:" + VariablesInventario.vFModificar);
            log.debug("VariablesInventario.vFNuevo:" + VariablesInventario.vFNuevo);
            //log.debug("validaAccionModificacion:"+validaAccionModificacion);

            if (indCierraVenta) {
                if (!VariablesInventario.vIrResumen) {
                    // Esto solo lo debe de hacer cuando ha hecho la opcion de Modificar
                    // No cuando es un pedido Nuevo o esta en Ver Pedido.
                    // Dubilluz 19.10.2008
                    // Validara que el pedido este en estado M si esta modificando el pedido.
                    if (VariablesInventario.vFModificar && !VariablesInventario.vFNuevo)
                        if (!validaAccionModificacion())
                            return;

                    if (VariablesInventario.vFModificar) {
                        try {
                            DBInventario.setEstadoPedidoEspecial(VariablesInventario.vNumPedidoEspecial,
                                                                 ConstantsInventario.EST_PENDIENTE);
                            VariablesInventario.vEsModificado = false;
                            log.debug("Close - VariablesInventario.vEsModificado " +
                                      VariablesInventario.vEsModificado);
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

    /**
     *
     **/
    private void confimarPedido() {
        try {
            //
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
            VariablesInventario.vCodProd_esp = tblListaProductos.getValueAt(row, 0).toString();
            VariablesInventario.vNomProd_esp = tblListaProductos.getValueAt(row, 1).toString();
            VariablesInventario.vUnidMed_esp = tblListaProductos.getValueAt(row, 2).toString();
            VariablesInventario.vNomLab_esp = tblListaProductos.getValueAt(row, 3).toString();
            VariablesInventario.vCantIng =
                    tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 4).toString();
            VariablesInventario.vEstado =
                    tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 6).toString();
            VariablesInventario.vPosi = tblListaProductos.getSelectedRow();
        }
    }


    /**
     *
     * */
    private void limpiarVariables() {

        VariablesInventario.vCodProd_esp = "";
        VariablesInventario.vNomProd_esp = "";
        VariablesInventario.vUnidMed_esp = "";
        VariablesInventario.vNomLab_esp = "";
        VariablesInventario.vStkFisico_esp = "";
        VariablesInventario.vCantIng = "";
        VariablesInventario.vValFrac_esp = "";
        VariablesInventario.vPrecVta_esp = "";
        VariablesInventario.vArrayProductosEspeciales = new ArrayList();
        VariablesInventario.vIrResumen = false;
        VariablesInventario.vFModificar = false;
        VariablesInventario.vFNuevo = false;
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaProductos.getSelectedRow() >= 0) {

                e.consume();
                /*//Se validara si no excede de los item de productos de pedido especial
          if (validaItemPedEspecial(ConstantsInventario.CONS_ING_PROD_ESPC)) {*/
                cargaCabeceraIngreseCantidad();
                DlgEspecialCantIngreso dlgEspecialCantIngreso = new DlgEspecialCantIngreso(myParentFrame, "", true);
                dlgEspecialCantIngreso.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    FarmaVariables.vAceptar = false;
                    tblListaProductos.setValueAt(VariablesInventario.vCantIng, VariablesInventario.vPosi, 5);
                    tblListaProductos.setRowSelectionInterval(VariablesInventario.vPosi, VariablesInventario.vPosi);
                    tblListaProductos.repaint();
                }
                // }
                indCierraVenta = false;
                VariablesInventario.vIrResumen = false;
            } else {
                FarmaUtility.showMessage(this, "Seleccione El Producto a Cambiar la Cantidad", txtBuscar);
            }

        } else {
            chkKeyPressed(e);
            indCierraVenta = true;
        }
    }

    private void actualizarCantidad(String CodProd, String CantIngre) {

        String aux_CodProd = "";
        for (int i = 0; i < VariablesInventario.vArrayProductosDet.size(); i++) {
            aux_CodProd = ((String)((ArrayList)VariablesInventario.vArrayProductosDet.get(i)).get(0)).trim();
            if (aux_CodProd.equalsIgnoreCase(CodProd)) {
                ((ArrayList)VariablesInventario.vArrayProductosDet.get(i)).set(4, CantIngre);
                log.debug("VariablesInventario.vArrayProductosDet :" + VariablesInventario.vArrayProductosDet);
            }
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
     * metodo que genera el pedido especial
     * @author jcallo
     * @since  16.10.2008
     * */
    private void generaPedido() {

        try {
            String vMensaje = "";
            int cantItem = VariablesInventario.vArrayProductosEspeciales.size();

            if (VariablesInventario.vEsModificado) {
                VariablesInventario.vNumPedidoEspecial =
                        FarmaSearch.getNuSecNumeracion(ConstantsInventario.SEC_PED_ESP, 10);
                log.debug("Numero de pedido generado :" + VariablesInventario.vNumPedidoEspecial);
                DBInventario.agregarCabeceraPedEsp(VariablesInventario.vNumPedidoEspecial, cantItem + "");
                DBInventario.agregarDetallePedEsp(VariablesInventario.vArrayProductosEspeciales,
                                                  VariablesInventario.vNumPedidoEspecial);
                FarmaSearch.updateNumera(ConstantsInventario.SEC_PED_ESP);
                vMensaje = "Se guardo el pedido especial exitosamente.";
            } else {
                DBInventario.agregarCabeceraPedEsp(VariablesInventario.vNumPedidoEspecial, cantItem + "");
                DBInventario.delDetallePedido(VariablesInventario.vNumPedidoEspecial);
                DBInventario.agregarDetallePedEsp(VariablesInventario.vArrayProductosEspeciales,
                                                  VariablesInventario.vNumPedidoEspecial);
                DBInventario.setEstadoPedidoEspecial(VariablesInventario.vNumPedidoEspecial,
                                                     ConstantsInventario.EST_PENDIENTE);
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

    /**
     * Se validara que la cantidad de items ingresados no sean mayores del
     * parametrizado para el local
     * @author dubilluz
     * @since  22.09.2008
     */
    private boolean validaItemPedEspecial(int pTipoValidacion) {

        boolean bResult = true;
        int dCantEstablecida = 0;
        try {
            dCantEstablecida = Integer.parseInt(DBInventario.obtieneCantMaxItemPedidoEspecial().trim());

            if (pTipoValidacion == ConstantsInventario.CONS_ING_PED_ESPC) {
                if (VariablesInventario.vArrayProductosEspeciales.size() > dCantEstablecida) {
                    bResult = false;
                }
            } else if (pTipoValidacion == ConstantsInventario.CONS_ING_PROD_ESPC) {
                if (dCantEstablecida == 0)
                    bResult = false;
                else if (dCantEstablecida <= VariablesInventario.vArrayProductosEspeciales.size()) {
                    bResult = false;
                }
            }
        } catch (SQLException e) {
            bResult = false;
            log.error("", e);
        }

        if (!bResult) {
            FarmaUtility.showMessage(this, "El pedido excede el número de items establecido para el local", null);
        }

        return bResult;
    }

    private void cargaCabeceraIngreseCantidad() {
        VariablesInventario.vCodProd_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0).toString();
        VariablesInventario.vNomProd_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString();
        VariablesInventario.vUnidMed_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 2).toString();
        VariablesInventario.vNomLab_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 3).toString();
        //VariablesInventario.vStkFisico_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
        VariablesInventario.vCantIng = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 5).toString();
        //VariablesInventario.vValFrac_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),7).toString();
        //VariablesInventario.vPrecVta_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),8).toString();
        VariablesInventario.vPosi = tblListaProductos.getSelectedRow();
        VariablesInventario.flag_modificarCantidad = true;
    }


    public void borrarSeleccionListProducto(int rowSel) {
        String vCod = ((ArrayList)VariablesInventario.vArrayProductosEspeciales.get(rowSel)).get(0).toString();
        for (int i = 0; i < VariablesInventario.tableModelEspecial.data.size(); i++) {
            if (((ArrayList)VariablesInventario.tableModelEspecial.getRow(i)).contains(vCod)) {
                VariablesInventario.tableModelEspecial.setValueAt(new Boolean(false), i, 0);
                VariablesInventario.tableModelEspecial.setValueAt(" ", i, 6);
                break;
            }
        }
    }

    private void agregarProducto() {
        /* DlgEspecialListaProductos dlgListaProductos = new DlgEspecialListaProductos(myParentFrame,"",true);
       dlgListaProductos.setVisible(true);

       FarmaVariables.vAceptar = false;

       if(VariablesVentas.vIndDireccionarResumenPed){
        if(!VariablesVentas.vIndF11){
        /*if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Desea agregar más productos al pedido?"))
             {
             agregarProducto();
             }
         }
       }*/
        VariablesInventario.flag_F3 = true;
        DlgEspecialListaProductos dlglistaProdEspeciales = new DlgEspecialListaProductos(myParentFrame, "", true);
        dlglistaProdEspeciales.setVisible(true);

        if (FarmaVariables.vAceptar) {
            log.debug(" cargan los productos seleccionados");
            cargarResumenPedidoEsp();
        }
        VariablesInventario.flag_F3 = false;
        VariablesInventario.vIrResumen = false;
        txtBuscar.setText("");
        VariablesVentas.vCodFiltro = "";
        VariablesVentas.vIndF11 = false;
    }

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
