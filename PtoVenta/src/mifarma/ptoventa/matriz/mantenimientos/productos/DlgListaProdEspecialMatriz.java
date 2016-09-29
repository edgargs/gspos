package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextField;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.DlgListaMaestros;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProdEspecialMatriz.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ   04.01.10   Creación<br>
 * <br>
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgListaProdEspecialMatriz extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaProdEspecialMatriz.class);

    Frame myParentFrame;
    //FarmaTableModel tableModel;
    private String stkProd = "";
    private String indProdCong = "";

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JButtonLabel btnNuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JButtonLabel lblCantProd = null;
    private JButtonLabel lblCantProd_T = null;
    private JTextFieldSanSerif txtCodLocal = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescLocal = new JTextFieldSanSerif();
    private JButtonLabel lblLocal = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaProdEspecialMatriz() {
        this(null, "", false);
    }

    public DlgListaProdEspecialMatriz(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(784, 438));
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
        pnlHeader1.setBounds(new Rectangle(10, 15, 755, 45));
        pnlTitle1.setBounds(new Rectangle(10, 70, 755, 25));
        scrListaProductos.setBounds(new Rectangle(10, 95, 755, 270));
        scrListaProductos.setBackground(new Color(255, 130, 14));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(675, 375, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(565, 375, 100, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 375, 195, 20));
        btnNuscar.setText("Buscar:");
        btnNuscar.setBounds(new Rectangle(15, 15, 55, 15));
        btnNuscar.setMnemonic('B');
        btnNuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(75, 15, 320, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        btnRelacionProductos.setText("Relación de Productos");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.setActionCommand("Relacion de Productos");
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        txtCodLocal.setBounds(new Rectangle(455, 15, 50, 20));
        txtCodLocal.setLengthText(3);
        txtCodLocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodLocal_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCodLocal_keyTyped(e);
            }
        });
        txtDescLocal.setBounds(new Rectangle(510, 15, 240, 20));
        lblLocal.setText("Local :");
        lblLocal.setBounds(new Rectangle(405, 15, 45, 20));
        lblLocal.setMnemonic('L');
        lblLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblLocal_actionPerformed(e);
            }
        });
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
        pnlTitle1.add(getLblCantProd(), null);
        pnlTitle1.add(getLblCantProd_T(), null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(txtDescLocal, null);
        pnlHeader1.add(txtCodLocal, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnNuscar, null);
        pnlHeader1.add(lblLocal, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
        txtDescLocal.setEnabled(false);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        log.debug("::::JCORTEZ:::::CARGA PRODUCTOS ESPECIALES");
        tblListaProductos.getTableHeader().setReorderingAllowed(false);
        tblListaProductos.getTableHeader().setResizingAllowed(false);
        FarmaUtility.initSelectList(tblListaProductos, VariablesProducto.tableModelEspecialMatriz,
                                    ConstantsProducto.columnsListaProductosEspeciales);

        for (int i = 0; i < VariablesProducto.tableModelEspecialMatriz.getRowCount(); i++)
            VariablesProducto.tableModelEspecialMatriz.setValueAt(new Boolean(false), i, 0);

    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnNuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        /*FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 2);

    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblListaProductos.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 1, 2)) )
        {
          FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
          return;
        }
      }
    }

    chkKeyPressed(e);*/
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //ERIOS 03.07.2013 Limpia la caja de texto
            limpiaCadenaAlfanumerica(txtBuscar);
            try {
                e.consume();
                if (tblListaProductos.getSelectedRow() >= 0) {
                    String productoBuscar = txtBuscar.getText().trim().toUpperCase();
                    if (productoBuscar.length() == 0)
                        return;

                    String codigo = "";
                    // revisando codigo de barra
                    char primerkeyChar = productoBuscar.charAt(0);
                    char segundokeyChar;

                    if (productoBuscar.length() > 1)
                        segundokeyChar = productoBuscar.charAt(1);
                    else
                        segundokeyChar = primerkeyChar;

                    char ultimokeyChar = productoBuscar.charAt(productoBuscar.length() - 1);

                    if (productoBuscar.length() > 6 &&
                        (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) &&
                                                                (!Character.isLetter(ultimokeyChar))))) {
                        VariablesVentas.vCodigoBarra = productoBuscar;
                        productoBuscar = DBVentas.obtieneCodigoProductoBarra();
                    }


                    if (productoBuscar.equalsIgnoreCase("000000")) {
                        FarmaUtility.showMessage(this,
                                                 "No existe producto relacionado con el Codigo de Barra. Verifique!!!",
                                                 txtBuscar);
                        return;
                    }


                    for (int k = 0; k < tblListaProductos.getRowCount(); k++) {
                        codigo = ((String)tblListaProductos.getValueAt(k, 1)).trim();
                        if (codigo.equalsIgnoreCase(productoBuscar)) {
                            FarmaGridUtils.showCell(tblListaProductos, k, 0);
                            break;
                        }
                    }

                    if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 1, 2))) {
                        FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                 txtBuscar);
                        return;
                    }
                }
            } catch (SQLException sql) {
                //log.error("",sql);

                FarmaUtility.showMessage(this, "Error al buscar el Producto.\n" +
                        sql, txtBuscar);
            }
        }
        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 2);
        if (tblListaProductos.getRowCount() >= 0 && VariablesInventario.tableModelEspecial.getRowCount() > 0 &&
            e.getKeyChar() != '+') {
            if (FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 2) ||
                (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_PAGE_UP) ||
                (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) ||
                e.getKeyCode() == KeyEvent.VK_ENTER) {
                muestraInfoProd();
            }

        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if (VariablesProducto.vCodLocalDestino.length() > 0) {
            txtCodLocal.setText(VariablesProducto.vCodLocalDestino);
            txtDescLocal.setText(VariablesProducto.vDescLocalDestino);
        }
        cargaProductosSeleccionados();
        lblCantProd_T.setText(VariablesInventario.vArrayListaProdsEsp.size() + "");
        limpiarVariables();
        FarmaUtility.moveFocus(txtBuscar);
        if (VariablesVentas.vKeyPress != null) {
            if (VariablesVentas.vCodBarra.trim().length() > 0) {
                txtBuscar.setText(VariablesVentas.vCodBarra.trim());
                txtBuscar_keyPressed(VariablesVentas.vKeyPress);
            } else if (VariablesVentas.vCodProdBusq.trim().length() > 0) {
                txtBuscar.setText(VariablesVentas.vCodProdBusq.trim());
                txtBuscar_keyPressed(VariablesVentas.vKeyPress);
            } else {
                txtBuscar.setText(VariablesVentas.vKeyPress.getKeyChar() + "");
                txtBuscar_keyReleased(VariablesVentas.vKeyPress);
            }
        } else {
            txtBuscar.setText("");
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaProductos.getSelectedRowCount() > 0) {
                seleccionarProducto();
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (txtCodLocal.getText().trim().length() < 1) {
                txtDescLocal.setText("");
                FarmaUtility.showMessage(this, "Seleccione un local", txtCodLocal);
                return;
            }
            validaLocal();
            if (FarmaVariables.vAceptar)
                funcion_F11();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //cancelaOperacion();
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Desea salir del pedido?")) {

                //arreglos clone. evitar repetidos
                VariablesProducto.vArrayProductosEspeciales.clear();
                VariablesProducto.vArrayProductosEspeciales = new ArrayList();

                VariablesProducto.vArrayListaProdsEsp.clear();
                VariablesProducto.vArrayListaProdsEsp = new ArrayList();

                log.debug("En lista ESCP :" + VariablesInventario.vIrResumen);
                //if(VariablesInventario.vIrResumen)
                cerrarVentana(false);
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        VariablesProducto.vArrayListaProdsEsp = new ArrayList();
        FarmaVariables.vAceptar = pAceptar;
        if (!pAceptar)
            VariablesProducto.vIrResumen = true;
        else
            VariablesProducto.vIrResumen = false;

        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void seleccionarProducto() {
        boolean seleccion =
            ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0)).booleanValue();
        if (!seleccion) {

            //en Matriz no se debe validar maximo de items por pedido
            // Se validara si no excede de los item de productos de pedido especial
            // if (validaItemPedEspecial(ConstantsInventario.CONS_ING_PROD_ESPC)) {
            cargaCabeceraIngreseCantidad();

            DlgEspecialCantIngresoMatriz dlgEspecialCantIngreso =
                new DlgEspecialCantIngresoMatriz(myParentFrame, "", true);
            dlgEspecialCantIngreso.setVisible(true);

            if (FarmaVariables.vAceptar) {
                FarmaUtility.setCheckValue(tblListaProductos, false);
                FarmaVariables.vAceptar = false;
                //listarProductos();
                tblListaProductos.setValueAt(VariablesProducto.vCantIng, VariablesProducto.vPosi, 6);
                tblListaProductos.setRowSelectionInterval(VariablesProducto.vPosi, VariablesProducto.vPosi);

                lblCantProd_T.setText(VariablesProducto.vArrayListaProdsEsp.size() + "");


                //funcion_F11();
            }
            //}
        } else {
            borrarProducto();
            tblListaProductos.setValueAt("", tblListaProductos.getSelectedRow(), 6);
            lblCantProd_T.setText(VariablesProducto.vArrayListaProdsEsp.size() + "");
            FarmaUtility.setCheckValue(tblListaProductos, true);
        }

    }

    private void cargaCabeceraIngreseCantidad() {
        VariablesProducto.vCodProd_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString();
        VariablesProducto.vNomProd_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 2).toString();
        VariablesProducto.vUnidMed_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 3).toString();
        VariablesProducto.vNomLab_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 4).toString();
        VariablesProducto.vStkFisico_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 5).toString();
        VariablesProducto.vCantIng = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 6).toString();
        VariablesProducto.vValFrac_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 7).toString();
        VariablesProducto.vPrecVta_esp =
                tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 8).toString();
        VariablesProducto.vPosi = tblListaProductos.getSelectedRow();

    }


    private void borrarProducto() {
        String cod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString();

        for (int i = 0; i < VariablesProducto.vArrayListaProdsEsp.size(); i++) {
            if (((ArrayList)VariablesProducto.vArrayListaProdsEsp.get(i)).contains(cod)) {
                VariablesProducto.vArrayListaProdsEsp.remove(i);
                break;
            }
        }
    }

    /**
     ***/
    private void cargaProductosSeleccionados() {
        log.debug(":::::JCORTEZ:::CARGANDO SELECCIONADOS");
        if (VariablesProducto.vArrayProductosEspeciales.size() > 0) {
            VariablesProducto.vArrayListaProdsEsp.clear();
            VariablesProducto.vArrayListaProdsEsp = new ArrayList();


            VariablesProducto.vArrayListaProdsEsp = (ArrayList)VariablesProducto.vArrayProductosEspeciales.clone();
            String cod;
            String Cant;
            for (int i = 0; i < VariablesProducto.vArrayListaProdsEsp.size(); i++) {
                cod = ((ArrayList)VariablesProducto.vArrayListaProdsEsp.get(i)).get(0).toString();
                Cant = ((ArrayList)VariablesProducto.vArrayListaProdsEsp.get(i)).get(5).toString();
                for (int j = 0; j < tblListaProductos.getRowCount(); j++) {
                    if (((ArrayList)VariablesProducto.tableModelEspecialMatriz.getRow(j)).contains(cod)) {
                        VariablesProducto.tableModelEspecialMatriz.setValueAt(new Boolean(true), j, 0);
                        VariablesProducto.tableModelEspecialMatriz.setValueAt(Cant, j, 6);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Limpia las Variables
     * */
    private void limpiarVariables() {

        VariablesProducto.flag_modificarCantidad = false;
        VariablesProducto.vIrResumen = false;
    }


    /**
     * Se validara que la cantidad de items ingresados no sean mayores del
     * parametrizado para el local
     */
    private boolean validaItemPedEspecial(int pTipoValidacion) {

        boolean bResult = true;
        int dCantEstablecida = 0;
        try {
            dCantEstablecida = Integer.parseInt(DBInventario.obtieneCantMaxItemPedidoEspecial().trim());

            if (pTipoValidacion == ConstantsInventario.CONS_ING_PED_ESPC) {
                if (VariablesInventario.vArrayListaProdsEsp.size() > dCantEstablecida) {
                    bResult = false;
                }
            } else if (pTipoValidacion == ConstantsInventario.CONS_ING_PROD_ESPC) {
                if (dCantEstablecida == 0)
                    bResult = false;
                else if (dCantEstablecida <= VariablesInventario.vArrayListaProdsEsp.size()) {
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

    /**
     * This method initializes lblCantProd	
     * @return com.gs.mifarma.componentes.JButtonLabel	
     */
    private JButtonLabel getLblCantProd() {
        if (lblCantProd == null) {
            lblCantProd = new JButtonLabel();
            lblCantProd.setBounds(new Rectangle(660, 5, 45, 15));
            lblCantProd.setText("Items:");
        }
        return lblCantProd;
    }

    /**
     * This method initializes lblCantProd_T	
     * @return com.gs.mifarma.componentes.JButtonLabel	
     */
    private JButtonLabel getLblCantProd_T() {
        if (lblCantProd_T == null) {
            lblCantProd_T = new JButtonLabel();
            lblCantProd_T.setBounds(new Rectangle(701, 6, 53, 15));
            lblCantProd_T.setText("0");
        }
        return lblCantProd_T;
    }

    /**
     * realiza la funcion F11
     */
    public void funcion_F11() {
        if (VariablesProducto.vArrayListaProdsEsp.size() < 1) {
            FarmaUtility.showMessage(this, "Seleccione los productos del pedido.!", null);
        } else if (txtCodLocal.getText().trim().length() != 3) {
            FarmaUtility.showMessage(this, "Seleccione un local destino.!", txtCodLocal);
        } else {

            // if(validaItemPedEspecial(ConstantsInventario.CONS_ING_PED_ESPC)){
            /*if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"Esta seguro de generar el pedido?"))
               {
                generaPedido();
               }*/
            VariablesProducto.vArrayProductosEspeciales = (ArrayList)VariablesProducto.vArrayListaProdsEsp.clone();
            VariablesProducto.vCodLocalDestino = txtCodLocal.getText().trim();
            VariablesProducto.vDescLocalDestino = txtDescLocal.getText().trim();
            cerrarVentana(true);

            //}

        }
        //VariablesInventario.vArrayTransferenciaProductos = arrayProductos;
        //cerrarVentana(true);
    }

    private void muestraInfoProd() {
        if (tblListaProductos.getRowCount() <= 0)
            return;

        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        //log.debug(myArray);
        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
        } else {
            stkProd = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtBuscar);
        }

        tblListaProductos.setValueAt(stkProd, tblListaProductos.getSelectedRow(), 5);
        tblListaProductos.repaint();
    }

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        int vFila = tblListaProductos.getSelectedRow();
        String codProd = FarmaUtility.getValueFieldJTable(tblListaProductos, vFila, 1);
        try {
            DBVentas.obtieneInfoProductoVta(pArrayList, codProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private void txtCodLocal_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            validaLocal();
        } else {
            chkKeyPressed(e);
        }

    }

    private void validaLocal() {

        VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOCAL;
        log.debug("codigo de maestro::" + VariablesProducto.vTipoMaestro);
        if (txtCodLocal.getText().trim().length() > 0)
            txtCodLocal.setText(FarmaUtility.completeWithSymbol(txtCodLocal.getText(), 3, "0", "I"));

        // if (txtCodLocal.getText().trim().length()!=3){

        validaCodigo(txtCodLocal, txtDescLocal, VariablesPtoVenta.vTipoMaestro);

        if (FarmaVariables.vAceptar) {
            if (txtCodLocal.getText().trim().length() == 3)
                FarmaUtility.moveFocus(txtBuscar);
        } else
            FarmaUtility.moveFocus(txtCodLocal);
    }

    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro) {
        if (pJTextField_Cod.getText().trim().length() > 0) {
            VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
            ArrayList myArray = new ArrayList();
            buscaCodigoListaMaestro(myArray);

            if (myArray.size() == 0) {
                FarmaUtility.showMessage(this, "Código No Encontrado", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            } else if (myArray.size() == 1) {
                String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                pJTextField_Cod.setText(codigo);
                pJTextField_Desc.setText(descripcion);
                VariablesPtoVenta.vCodMaestro = codigo;
                FarmaVariables.vAceptar = true;
            } else {
                FarmaUtility.showMessage(this, "Se encontro más de un registro.Verificar!!!", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            }
        } else {
            cargaListaMaestros(pTipoMaestro, pJTextField_Cod, pJTextField_Desc);
        }
    }

    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc) {
        VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
        VariablesPtoVenta.vTipListaMaestros = ConstantsPtoVenta.TIP_LIST_MAESTRO_ORD;
        DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
        dlgListaMaestros.setVisible(true);
        if (FarmaVariables.vAceptar) {

            pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
            pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);

        }
    }

    private void buscaCodigoListaMaestro(ArrayList pArrayList) {
        try {
            DBPtoVenta.buscaCodigoListaMaestro(pArrayList, VariablesPtoVenta.vTipoMaestro,
                                               VariablesPtoVenta.vCodMaestro);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al buscar código en maestros :\n" +
                    sql.getMessage(), txtCodLocal);
        }
    }

    private void lblLocal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodLocal);
    }

    private void txtCodLocal_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCodLocal, e);
    }
}
