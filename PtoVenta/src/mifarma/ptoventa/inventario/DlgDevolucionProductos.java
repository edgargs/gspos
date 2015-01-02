package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.dto.NotaEsCabDetDTO;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionProductos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionProductos extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionProductos.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JButtonLabel btnBuscarOC = new JButtonLabel();
    private JTextField txtBuscar = new JTextField();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductosDev = new JTable();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblBorrar = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel lblCantProductos = new JButtonLabel();
    private JButtonLabel lblCantProductosT = new JButtonLabel();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JPanel jPanel = new JPanel();
    boolean indCabecera = false;
    boolean indOrdenCompra = false;
    private FacadeInventario facadeInventario = null;
    private NotaEsCabDTO notaEsCabDTO = null;


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDevolucionProductos() {
        this(null, "", false);
    }

    public DlgDevolucionProductos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            setFocusable(true);
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

        this.setSize(new Dimension(766, 438));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos de Devolucion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        btnBuscarOC.setText("Buscar:");
        btnBuscarOC.setBounds(new Rectangle(15, 15, 160, 15));
        btnBuscarOC.setMnemonic('B');

        jPanel.setBounds(new Rectangle(10, 15, 745, 45));
        jPanel.setBackground(new Color(43, 141, 39));
        jPanel.setLayout(null);
        jPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        txtBuscar.setBounds(new Rectangle(100, 13, 330, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }


        });
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 70, 745, 25));

        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
        btnRelacionProductos.setMnemonic('R');
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

        scrListaProductos.setBounds(new Rectangle(10, 95, 745, 270));
        scrListaProductos.setBackground(new Color(255, 130, 14));

        scrListaProductos.getViewport().add(tblListaProductosDev, null);

        lblF3.setText("[ F3 ] Insertar");
        lblF3.setBounds(new Rectangle(15, 375, 100, 20));

        lblBorrar.setText("[ F5 ] Borrar");
        lblBorrar.setBounds(new Rectangle(125, 375, 100, 20));

        lblF4.setText("[ F4 ] Editar");
        lblF4.setBounds(new Rectangle(235, 375, 100, 20));

        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(555, 375, 100, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(665, 375, 90, 20));


        lblCantProductos.setText("Cantidad de Productos");
        lblCantProductos.setBounds(new Rectangle(475, 5, 130, 15));
        lblCantProductosT.setBounds(new Rectangle(615, 5, 45, 15));

        lblF9.setBounds(new Rectangle(395, 375, 145, 20));
        lblF9.setText("[ F9 ] Mostrar Cabecera");
        jPanel.add(btnBuscarOC, null);
        jPanel.add(txtBuscar, null);
        pnlTitle1.add(btnRelacionProductos, null);
        pnlTitle1.add(lblCantProductos, null);
        pnlTitle1.add(lblCantProductosT, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(jPanel, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblBorrar, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        notaEsCabDTO = new NotaEsCabDTO();
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tblListaProductosDev.getTableHeader().setReorderingAllowed(false);
        tblListaProductosDev.getTableHeader().setResizingAllowed(false);
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaProductosDevolucionNueva, ConstantsInventario.defaultValuesListaProductosDevolucionNueva,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductosDev, tableModel,
                                    ConstantsInventario.columnsListaProductosDevolucionNueva);

        if (tblListaProductosDev.getRowCount() > 0) {
            FarmaGridUtils.showCell(tblListaProductosDev, 0, 0);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
        mostrarCabecera(true);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    private void btnRelacionProductos_actionPerformed(ActionEvent e) {

        FarmaUtility.moveFocus(txtBuscar);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    //Cesar Huanes busca por descripcíon y codigo de producto

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductosDev, txtBuscar, 1);
        String vCadIngresada = txtBuscar.getText();
        if (!UtilityVentas.isNumerico(vCadIngresada)) {
            chkKeyPressed(e);
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String codProd = vCadIngresada.trim();
                if (UtilityVentas.isNumerico(codProd) && codProd.length() == 6) {
                    VariablesInventario.vBusquedaProdTransf = codProd;
                    VariablesInventario.vKeyPress = e;
                    agregarProducto();
                    if (FarmaVariables.vAceptar) {
                        cargaListaProductos();
                        lblCantProductosT.setText("" + tblListaProductosDev.getRowCount());
                    }
                    VariablesInventario.vBusquedaProdTransf = "";
                    VariablesInventario.vKeyPress = null;
                    txtBuscar.setText("");
                }
            }
        }
    }
    //Cesar Huanes busqueda por codigo de barra

    private void txtBuscar_keyReleased(KeyEvent e) {
        String cadena = txtBuscar.getText().trim();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            cadena = UtilityPtoVenta.getCadenaAlfanumerica(cadena); //valida caracteres extraños
            cadena = UtilityPtoVenta.getCodBarraSinCarControl(cadena); //valida los EAN
            if (UtilityVentas.isNumerico(cadena)) {
                e.consume();
                txtBuscar.setText(cadena);
                VariablesInventario.vBusquedaProdTransf = cadena;
                VariablesInventario.vKeyPress = e;
                agregarProducto();
                if (FarmaVariables.vAceptar) {
                    cargaListaProductos();
                    lblCantProductosT.setText("" + tblListaProductosDev.getRowCount());
                }
                VariablesInventario.vBusquedaProdTransf = "";
                VariablesInventario.vKeyPress = null;
                FarmaUtility.moveFocus(txtBuscar);
                txtBuscar.setText("");
            }

        }
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (Character.isLetter(e.getKeyChar())) {

            if (indCabecera)
                if (VariablesInventario.vKeyPress == null) {
                    VariablesInventario.vKeyPress = e;
                    agregarProducto();
                    //mostrarListadoProductos();//cesar huanes
                    if (FarmaVariables.vAceptar) {
                        cargaListaProductos();
                        lblCantProductosT.setText("" + tblListaProductosDev.getRowCount());
                    }

                } else
                    FarmaUtility.showMessage(this, "Debe Ingresar primero la Cabecera.", txtBuscar);

        }

        if (e.getKeyCode() == KeyEvent.VK_F3) {
            mostrarListadoProductos();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tblListaProductosDev.getSelectedRow() > -1) {
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Esta seguro de borrar el producto?"))
                    borrarProducto();
            } else {
                FarmaUtility.showMessage(this, "Debe seleccionar un producto", txtBuscar);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {

            if (tblListaProductosDev.getSelectedRow() > -1) {
                cargaCabecera();
                VariablesInventario.vIndModProdTransf = true;
                DlgDevolucionIngresoCantidad dlgDevolucionIngresoCantidad =
                    new DlgDevolucionIngresoCantidad(myParentFrame, "", true);
                dlgDevolucionIngresoCantidad.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    actualizarProducto();
                    FarmaVariables.vAceptar = false;
                }
                VariablesInventario.vIndModProdTransf = false;
            } else {
                FarmaUtility.showMessage(this, "Debe seleccionar un producto", txtBuscar);
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            mostrarCabecera(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (indCabecera) {
                if (validaDatos()) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Está seguro de generar la devolucion?")) {
                        FarmaVariables.vAceptar = false;
                        DlgListaImpresorasInv dlgListaImpresorasInv =
                            new DlgListaImpresorasInv(this.myParentFrame, "", true);
                        dlgListaImpresorasInv.setVisible(true);

                        if (!FarmaVariables.vAceptar) {
                            return;
                        }
                        //CHUANES 14.03.2014
                        //Verificamos la ruta y el acceso ala impresora correspondiente a imprimir
                        if (!UtilityCaja.verificaImpresora(this, null, ConstantsPtoVenta.TIP_COMP_GUIA)) {
                            return;
                        }
                        if (grabar()) {
                            //GFonseca 16.12.2013 inicializa el array de datos antes de cerrar la ventana.
                            VariablesInventario.vArrayProductos = new ArrayList();
                            cerrarVentana(true);
                        }

                    }
                }
            } else {
                FarmaUtility.showMessage(this, "Debe Ingresar primero la Cabecera.", btnRelacionProductos);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro de cerrar la ventana.\n" +
                    "Asegúrese de presionar F11 para grabar todos los datos antes de salir?")) {

                //GFonseca 16.12.2013 inicializa el array de datos antes de cerrar la ventana.
                VariablesInventario.vArrayProductos = new ArrayList();
                //CHUANES 31.12.2013 inicializa el array de productos transferidos con el fin de limpiar los datos en memoria.
                VariablesInventario.vArrayTransferenciaProductos = new ArrayList();
                cerrarVentana(false);
            }

        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesInventario.vKeyPress = null;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
    //Cesar Huanes Carga cabecera al editar

    private void cargaCabecera() {
        int filaSelec = tblListaProductosDev.getSelectedRow();

        VariablesInventario.vCodigo = tblListaProductosDev.getValueAt(filaSelec, 0).toString();
        VariablesInventario.vDescrip = tblListaProductosDev.getValueAt(filaSelec, 1).toString();
        VariablesInventario.vDescUniPrese = tblListaProductosDev.getValueAt(filaSelec, 2).toString();
        VariablesInventario.vLaborat = tblListaProductosDev.getValueAt(filaSelec, 3).toString();
        //CHUANES 27.02.2014
        // al editar se muestra tbm la cantidad
        VariablesInventario.vCantMovi = Integer.parseInt(tblListaProductosDev.getValueAt(filaSelec, 4).toString());
        VariablesInventario.vPrecVtaVig = tblListaProductosDev.getValueAt(filaSelec, 5).toString();
        VariablesInventario.vFechVentprod = tblListaProductosDev.getValueAt(filaSelec, 6).toString();
        VariablesInventario.vMunLote = tblListaProductosDev.getValueAt(filaSelec, 7).toString();
        VariablesInventario.vStockDispo = tblListaProductosDev.getValueAt(filaSelec, 8).toString();
        VariablesInventario.vValFrac = tblListaProductosDev.getValueAt(filaSelec, 9).toString();
        // VariablesInventario.vValPrectotal=Double.parseDouble(tblListaProductosDev.getValueAt(filaSelec,10).toString());
        VariablesInventario.vfechaStock = tblListaProductosDev.getValueAt(filaSelec, 11).toString();
        VariablesInventario.secResStock = Integer.parseInt(tblListaProductosDev.getValueAt(filaSelec, 12).toString());
        //CHUANES 27.02.2014
        //indica si se realiza un editar
        VariablesInventario.vFlagCantMov = true;

    }
    //Cesar Huanes actualiza los datos al editar

    private void actualizarProducto() {
        ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductosDev.getSelectedRow()))).set(4,
                                                                                                                       Integer.toString(VariablesInventario.vCantMovi));
        ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductosDev.getSelectedRow()))).set(7,
                                                                                                                       VariablesInventario.vMunLote);
        ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductosDev.getSelectedRow()))).set(6,
                                                                                                                       VariablesInventario.vFechVentprod);
        ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductosDev.getSelectedRow()))).set(10,
                                                                                                                       FarmaUtility.formatNumber(VariablesInventario.vValPrectotal));

        cargaListaProductos();
    }

    private void borrarProducto() {
        VariablesInventario.vArrayProductos.remove(tblListaProductosDev.getSelectedRow());

        tableModel.deleteRow(tblListaProductosDev.getSelectedRow());


        tblListaProductosDev.repaint();
        if (tblListaProductosDev.getRowCount() > 0) {
            FarmaGridUtils.moveRowSelection(tblListaProductosDev, 0);
        }

        lblCantProductosT.setText("" + tblListaProductosDev.getRowCount());
    }


    private void mostrarListadoProductos() {
        DlgDevolucionDetalleOC dlgDevolucionDetalle = new DlgDevolucionDetalleOC(myParentFrame, "", true);
        dlgDevolucionDetalle.setFacadeInventario(facadeInventario);
        dlgDevolucionDetalle.setNotaEsCabDTO(notaEsCabDTO);
        dlgDevolucionDetalle.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaProductos();
            indOrdenCompra = true;
            FarmaVariables.vAceptar = false;
        }
        lblCantProductosT.setText("" + tblListaProductosDev.getRowCount());
    }


    private boolean grabar() {
        boolean retorno = false;
        String numera;
        notaEsCabDTO.setCantItem(tblListaProductosDev.getRowCount());
        notaEsCabDTO.setValorTotalNotaEsCab(FarmaUtility.sumColumTable(tblListaProductosDev, 10));
        ArrayList<NotaEsCabDetDTO> lstNotaEsCabDetDTO = new ArrayList<NotaEsCabDetDTO>();
        for (int i = 0; i < tblListaProductosDev.getRowCount(); i++) {
            NotaEsCabDetDTO notaEsCabDetDTO = new NotaEsCabDetDTO();
            notaEsCabDetDTO.setCodProd(tblListaProductosDev.getValueAt(i, 0).toString());
            notaEsCabDetDTO.setPrecVta(tblListaProductosDev.getValueAt(i, 5).toString());
            notaEsCabDetDTO.setValPrecioTotal(tblListaProductosDev.getValueAt(i, 10).toString());
            notaEsCabDetDTO.setCantMov(new Integer(tblListaProductosDev.getValueAt(i, 4).toString()));
            notaEsCabDetDTO.setFecVtoProd(tblListaProductosDev.getValueAt(i, 6).toString());
            notaEsCabDetDTO.setNumLoteProd(tblListaProductosDev.getValueAt(i, 7).toString());
            notaEsCabDetDTO.setValFraccion(new Integer(tblListaProductosDev.getValueAt(i, 9).toString()));
            notaEsCabDetDTO.setSecRespalStock(new Integer(tblListaProductosDev.getValueAt(i, 12).toString()));
            lstNotaEsCabDetDTO.add(notaEsCabDetDTO);
        }

        try {
            for (int k = 0; k < tblListaProductosDev.getRowCount(); k++) {
                String mensaje =
                    DBInventario.getStockProducto(tblListaProductosDev.getValueAt(k, 0).toString(), tblListaProductosDev.getValueAt(k,
                                                                                                                                    4).toString());
                if (mensaje.trim().equals("FALSE")) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, "El producto  no cuenta con Stock Diponible \n" +
                            tblListaProductosDev.getValueAt(k, 0).toString() + " - \t" +
                            tblListaProductosDev.getValueAt(k, 1).toString(), null);
                    retorno = false;
                    return retorno;
                }
            }

            numera = facadeInventario.grabarDevolucionOC(notaEsCabDTO, lstNotaEsCabDetDTO);
            if (!numera.equals("")) {
                retorno = true;
                FarmaUtility.showMessage(this, "Devolución generada!", tblListaProductosDev);

                //ERIOS 17.12.2013 Se cargan las variables de impresion
                UtilityInventario.cargaCabecera(this, btnRelacionProductos, numera);
                VariablesInventario.vNumNotaEs = numera;
                //UtilityInventario.procesoImpresionGuias(this ,tblListaProductosDev , VariablesInventario.vTipoFormatoImpresion);
                //notaEsCabDTO.getNumNotaEs()
                if (UtilityInventario.reimprimir(this, tblListaProductosDev, btnRelacionProductos,
                                                 VariablesInventario.vNumNotaEs)) {
                    facadeInventario.confirmarDevolucionMercaderia(numera);
                }

            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, e.getMessage(), tblListaProductosDev);
        }

        return retorno;
    }

    private boolean validaDatos() {
        boolean retorno = true;
        if (tblListaProductosDev.getRowCount() == 0) {
            FarmaUtility.showMessage(this, "No ha ingresado productos a esta guia.", btnRelacionProductos);
            retorno = false;
        }

        return retorno;
    }

    public void cargaListaProductos() {
        tableModel.clearTable();
        if (VariablesInventario.vArrayTransferenciaProductos.size() > 0) {
            ArrayList prods = VariablesInventario.vArrayTransferenciaProductos;
            for (int i = 0; i < prods.size(); i++) {
                tableModel.insertRow(((ArrayList)prods.get(i)));
            }
            prods = null;
            FarmaGridUtils.moveRowSelection(tblListaProductosDev, 0);
        }
    }

    private void agregarProducto() {
        DlgDevolucionDetalleOC dlgDevolucionDetalle = new DlgDevolucionDetalleOC(myParentFrame, "", true);
        dlgDevolucionDetalle.setFacadeInventario(facadeInventario);
        dlgDevolucionDetalle.setNotaEsCabDTO(notaEsCabDTO);
        dlgDevolucionDetalle.setVisible(true);
        txtBuscar.setText("");


    }

    private void mostrarCabecera(boolean bSalir) {
        DlgDevolucionNueva dlgDevolucionNueva = new DlgDevolucionNueva(myParentFrame, "", true);
        dlgDevolucionNueva.setNotaEsCabDTO(notaEsCabDTO);
        dlgDevolucionNueva.setVisible(true);
        if (FarmaVariables.vAceptar) {
            indCabecera = true;
            FarmaVariables.vAceptar = false;
            mostrarListadoProductos();
        } else if (bSalir) {
            cerrarVentana(false);
        }
    }


    public void setFacadeInventario(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }
}

