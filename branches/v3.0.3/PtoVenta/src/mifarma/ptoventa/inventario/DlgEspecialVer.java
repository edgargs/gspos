package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgEspecialVer extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ****************
         * ****************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgEspecialVer.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    String vEstadoNota;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblMotivo_T = new JLabelWhite();
    private JLabelWhite lblFechaEmision_T = new JLabelWhite();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelWhite lblFechaEmision = new JLabelWhite();
    private JLabelWhite lblNoPedEspecial = new JLabelWhite();
    private JLabelWhite lblFechaEmision_T1 = new JLabelWhite();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JButtonLabel lblCantProductos = new JButtonLabel();
    private JButtonLabel lblCantProductosT = new JButtonLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JButtonLabel btnProducto = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgEspecialVer() {
        this(null, "", false);
    }

    public DlgEspecialVer(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(705, 416));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ver Pedidos Especial");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnllHeader1.setBounds(new Rectangle(10, 10, 680, 55));
        pnlTitle1.setBounds(new Rectangle(10, 70, 680, 25));
        scrListaProductos.setBounds(new Rectangle(10, 95, 680, 250));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(600, 355, 85, 20));
        lblMotivo_T.setText("Estado:");
        lblMotivo_T.setBounds(new Rectangle(380, 5, 45, 15));
        lblFechaEmision_T.setText("F. Emisión:");
        lblFechaEmision_T.setBounds(new Rectangle(190, 5, 70, 15));
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
        lblF2.setText("[ F2 ] Anular");
        lblF2.setBounds(new Rectangle(15, 355, 85, 20));
        lblFechaEmision.setBounds(new Rectangle(265, 5, 110, 15));
        lblNoPedEspecial.setBounds(new Rectangle(85, 5, 90, 15));
        lblFechaEmision_T1.setText("No. Pedido:");
        lblFechaEmision_T1.setBounds(new Rectangle(10, 5, 75, 15));
        lblEstado.setBounds(new Rectangle(445, 10, 125, 20));
        lblCantProductos.setText("Cantidad de Productos");
        lblCantProductos.setBounds(new Rectangle(475, 5, 130, 15));
        lblCantProductosT.setBounds(new Rectangle(615, 5, 45, 15));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(480, 355, 105, 20));
        txtBuscar.setBounds(new Rectangle(70, 25, 330, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        btnProducto.setText("Producto:");
        btnProducto.setBounds(new Rectangle(10, 25, 55, 15));
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
        pnllHeader1.add(lblEstado, null);
        pnllHeader1.add(lblFechaEmision_T1, null);
        pnllHeader1.add(lblNoPedEspecial, null);
        pnllHeader1.add(lblFechaEmision, null);
        pnllHeader1.add(lblFechaEmision_T, null);
        pnllHeader1.add(lblMotivo_T, null);
        jContentPane.add(pnllHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        cargarDetalle();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaProductosPedEspVer, ConstantsInventario.defaultValuesListaProductosPedEspVer,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsInventario.columnsListaProductosPedEspVer);
        cargaListaProductos();
        obtenerListado();
        if (!FarmaVariables.vEconoFar_Matriz) {
            //(!VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_PENDIENTE) &&
            //!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR)){
            lblF11.setVisible(false);
            //lblF2.setVisible(false);
        }
    }

    private void cargaListaProductos() {
        try {
            DBInventario.cargaDetallePedEsp(tableModel, VariablesInventario.vNumPedidoEspecial);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), btnRelacionProductos);
        }
    }

    private void obtenerListado() {

        try {
            VariablesInventario.vArrayProductosDet.clear();
            DBInventario.obtieneDetPedido(VariablesInventario.vArrayProductosDet,
                                          VariablesInventario.vNumPedidoEspecial);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al obtener datos del detalle \n" +
                    sql.getMessage(), txtBuscar);
        }
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
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 1);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            // if(FarmaVariables.vEconoFar_Matriz){
            if (VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_PENDIENTE)) {
                //&& FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR)){
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro de anular el pedido?")) {
                    //anularProducto();
                    anularPedido();
                }
            } else {
                FarmaUtility.showMessage(this, "El pedido ya no esta pendiente", txtBuscar);
                /*if(!VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_PENDIENTE)){
           if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR)){
               FarmaUtility.showMessage(this,"El pedido ya no esta pendiente",txtBuscar);
           }else{
               FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);
           }
          }*/
            }
            //}
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F10(e)) {
            /*if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionProductos);
      else
      if(validaGuias())
        {
          if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de reimprimir?"))
          {
            FarmaVariables.vAceptar=false;
            DlgListaImpresorasInv dlgListaImpresorasInv=new DlgListaImpresorasInv(myParentFrame,"",true);
            dlgListaImpresorasInv.setVisible(true);

            if(!FarmaVariables.vAceptar){
              return;
            }
            if(reimprimir())
            {
              //FarmaUtility.showMessage(this, "Guías impresas satisfactoriamente!", btnRelacionProductos);
              cerrarVentana(true);
            }
          }
        }    */

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                if (VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_PENDIENTE) &&
                    FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR)) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "Esta seguro de confirmar el pedido?")) {
                        confimarPedido();
                    }
                } else {
                    if (!VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_PENDIENTE)) {
                        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR)) {
                            FarmaUtility.showMessage(this, "El pedido ya no esta pendiente", txtBuscar);
                        } else {
                            FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
                        }
                    }
                }
            } /*else{
        FarmaUtility.showMessage(this,ConstantsInventario.MENSAJE_MATRIZ_1,txtBuscar);
       }*/
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

    private void cargarDetalle() {

        lblNoPedEspecial.setText(VariablesInventario.vNumPedidoEspecial);
        lblFechaEmision.setText(VariablesInventario.vFecEmi_esp);
        if (VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_PENDIENTE))
            lblEstado.setText("PENDIENTE");
        else if (VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_ANULADO))
            lblEstado.setText("ANULADO");
        else if (VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_CONFIRMADO))
            lblEstado.setText("CONFIRMADO");

    }


    private boolean cargaLoginOper() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        }
        return FarmaVariables.vAceptar;
    }

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

    /**
     * anula la cantidad por producto
     * */
    private void anularProducto() {
        log.debug("anulando producto");
        if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR))
            FarmaUtility.showMessage(this, ConstantsInventario.MENSAJE_MATRIZ_1, txtBuscar);
        else {
            if (tblListaProductos.getSelectedRow() >= 0) {
                cargarDatos(tblListaProductos.getSelectedRow());
                if (VariablesInventario.vEstado.equalsIgnoreCase(ConstantsInventario.EST_ANULADO)) {
                    log.debug("1");
                    FarmaUtility.showMessage(this, "El producto ya fue anulado", txtBuscar);
                    tblListaProductos.setRowSelectionInterval(VariablesInventario.vPosi, VariablesInventario.vPosi);
                } else {
                    log.debug("2");
                    log.debug("VariablesInventario.vPosi " + VariablesInventario.vPosi);
                    agregaProductoAnulado();
                    tblListaProductos.setValueAt("ANULADO", VariablesInventario.vPosi, 5);
                    tblListaProductos.setValueAt(ConstantsInventario.EST_ANULADO, VariablesInventario.vPosi, 6);
                    tblListaProductos.repaint();
                    limpiarVariables();
                }
            }
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
     * */
    private void agregaProductoAnulado() {

        if (!validaEnArray(VariablesInventario.vCodProd_esp)) {
            log.debug("VariablesInventario.vArrayProductosDet " + VariablesInventario.vArrayProductosDet);
            /*  ArrayList array = new ArrayList();
      array.add(VariablesInventario.vCodProd_esp);
      array.add(VariablesInventario.vCantIng);
      array.add(VariablesInventario.vEstado);

      VariablesInventario.vArrayProductosDet.add(array);
      log.debug("array agregado "+array);
      log.debug("VariablesInventario.vArrayProductosDet "+VariablesInventario.vArrayProductosDet);  */
        }
    }

    /**
     *
     * */
    private boolean validaEnArray(String CodProd) {

        String aux_CodProd = "";
        boolean valor = false;
        //SETEMOS A "N"
        for (int i = 0; i < VariablesInventario.vArrayProductosDet.size(); i++) {
            aux_CodProd = ((String)((ArrayList)VariablesInventario.vArrayProductosDet.get(i)).get(0)).trim();
            if (aux_CodProd.equalsIgnoreCase(CodProd)) {
                ((ArrayList)VariablesInventario.vArrayProductosDet.get(i)).set(6, ConstantsInventario.EST_ANULADO);
                log.debug("VariablesInventario.vArrayProductosDet :" + VariablesInventario.vArrayProductosDet);
                valor = true;
            }
        }
        return valor;
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

    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (!FarmaVariables.vEconoFar_Matriz &&
                !FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR))
                //FarmaUtility.showMessage(this,ConstantsInventario.MENSAJE_MATRIZ_1,txtBuscar);
                log.info("alerta : " + ConstantsInventario.MENSAJE_MATRIZ_1);
            else {
                e.consume();

                if (tblListaProductos.getSelectedRow() >= 0) {

                    if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR)) {
                        if (!VariablesInventario.vEstCab_esp.equalsIgnoreCase(ConstantsInventario.EST_PENDIENTE)) {
                            FarmaUtility.showMessage(this, "El pedido ya no esta pendiente", txtBuscar);
                        } else {
                            cargarDatos(tblListaProductos.getSelectedRow());
                            if (VariablesInventario.vEstado.equalsIgnoreCase(ConstantsInventario.EST_ANULADO)) {
                                FarmaUtility.showMessage(this, "El producto ya fue anulado", txtBuscar);
                                tblListaProductos.setRowSelectionInterval(VariablesInventario.vPosi,
                                                                          VariablesInventario.vPosi);
                            } else if (VariablesInventario.vEstado.equalsIgnoreCase(ConstantsInventario.EST_CONFIRMADO)) {
                                FarmaUtility.showMessage(this, "El producto ya fue confirmado", txtBuscar);
                                tblListaProductos.setRowSelectionInterval(VariablesInventario.vPosi,
                                                                          VariablesInventario.vPosi);
                            } else {
                                VariablesInventario.ingresoDetalle = true;
                                DlgEspecialCantIngreso dlgEspecialCantIngreso =
                                    new DlgEspecialCantIngreso(myParentFrame, "", true);
                                dlgEspecialCantIngreso.setVisible(true);

                                if (FarmaVariables.vAceptar) {
                                    log.debug("VariablesInventario.vCantIng " + VariablesInventario.vCantIng);
                                    log.debug("VariablesInventario.vPosi " + VariablesInventario.vPosi);
                                    tblListaProductos.setValueAt(VariablesInventario.vCantIng,
                                                                 VariablesInventario.vPosi, 4);
                                    actualizarCantidad(VariablesInventario.vCodProd_esp, VariablesInventario.vCantIng);
                                    tblListaProductos.setRowSelectionInterval(VariablesInventario.vPosi,
                                                                              VariablesInventario.vPosi);
                                    tblListaProductos.repaint();
                                    limpiarVariables();
                                }
                            }
                        }
                    } else {
                        FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
                    }
                }
            }
        }
        chkKeyPressed(e);
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
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }


}
