package mifarma.ptoventa.inventario;


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
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionDetalleOC.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionDetalleOC extends JDialog implements KeyListener {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionDetalleOC.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JButtonLabel btnBuscarOC = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblBuscar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private ArrayList listaDetOrdenesCompra = new ArrayList();

    private FacadeInventario facadeInventario = null;
    //private static ArrayList arrayProductos = new ArrayList();
    private NotaEsCabDTO notaEsCabDTO = null;

    String stkProd = "";
    String indProdCong = "";
    ArrayList arrayProductos = new ArrayList();
    ArrayList listaProd = new ArrayList();
    ArrayList array = new ArrayList();

    //GFonseca 16.12.2013 mostrar mensaje de que no se puede buscar.
    private int contBus = 1;
    private final int COL_COD_PROD = 0;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDevolucionDetalleOC() {
        this(null, "", false);
    }

    public DlgDevolucionDetalleOC(Frame parent, String title, boolean modal) {

        super(parent, title, modal);
        myParentFrame = parent;

        try {
            this.addKeyListener(this);
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
        this.setTitle("Lista de Productos por Orden de Compra");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        btnBuscarOC.setText("Buscar producto:");
        btnBuscarOC.setBounds(new Rectangle(15, 15, 160, 15));
        btnBuscarOC.setMnemonic('B');
        btnBuscarOC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscarOC_actionPerformed(e);
            }
        });

        pnlHeader.setBounds(new Rectangle(10, 15, 745, 45));
        txtBuscar.setBounds(new Rectangle(160, 13, 330, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });


        pnlTitle1.setBounds(new Rectangle(10, 70, 745, 25));

        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });

        btnRelacionProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        scrListaProductos.setBounds(new Rectangle(10, 95, 745, 270));
        scrListaProductos.setBackground(new Color(255, 130, 14));
        scrListaProductos.getViewport().add(tblListaProductos, null);
        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });


        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 375, 145, 20));

        lblBuscar.setText("[ F3 ] Buscar OC");
        lblBuscar.setBounds(new Rectangle(170, 375, 145, 20));

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(665, 375, 90, 20));


        pnlHeader.add(btnBuscarOC, null);
        pnlHeader.add(txtBuscar, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEnter, null);
        // jContentPane.add(lblBuscar, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
        VariablesInventario.vFlagCantMov = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tblListaProductos.getTableHeader().setReorderingAllowed(false);
        tblListaProductos.getTableHeader().setResizingAllowed(false);
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaMaestroProductos, ConstantsInventario.defaultValuesListaMaestroProducto,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsInventario.columnsListaMaestroProductos);
        cargaDatosMemoria();
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        listaMaestroProductos();
        FarmaUtility.moveFocus(this.txtBuscar);
        if (UtilityInventario.indNuevaTransf()) {
            if (VariablesInventario.vKeyPress != null) {
                if (VariablesInventario.vBusquedaProdTransf.trim().length() > 0) {
                    txtBuscar.setText(VariablesInventario.vBusquedaProdTransf.trim());
                    log.debug("Entra busca: " + VariablesInventario.vKeyPress.toString());
                    txtBuscar_keyReleased(VariablesInventario.vKeyPress);
                    txtBuscar_keyPressed(VariablesInventario.vKeyPress);
                } else {
                    txtBuscar.setText(VariablesInventario.vKeyPress.getKeyChar() + "");
                    //txtBuscar.selectAll();
                    txtBuscar_keyReleased(VariablesInventario.vKeyPress);
                    txtBuscar_keyPressed(VariablesInventario.vKeyPress);
                }

            }
        }


    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnBuscarOC_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }


    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 1);

    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);

    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        String cadena = txtBuscar.getText().trim();
        cadena = UtilityPtoVenta.getCadenaAlfanumerica(cadena);
        cadena = UtilityPtoVenta.getCodBarraSinCarControl(cadena);
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 1);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();

            if (tblListaProductos.getSelectedRow() >= 0) {

                if (UtilityVentas.isNumerico(cadena) && cadena.length() > 7) {
                    buscarProductoCodigoBarra(cadena);

                } else {

                    if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 1))) {
                        FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                 txtBuscar);
                        return;
                    }
                }
            }
            if (FarmaVariables.vAceptar)
                cargaDatosMemoria();

            if (tblListaProductos.getRowCount() == 0) {
                cargaDatosMemoria();
                tblListaProductos.clearSelection();

            }

        }

        chkKeyPressed(e);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //FarmaUtility.moveFocus(tblListaProductos);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // FarmaUtility.moveFocus(tblListaProductos);
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            //if (tblListaProductos.getSelectedRow() != -1){
            seleccionarProducto();
            // }else{
            //FarmaUtility.showMessage(this, "Seleccione un registro!!!", txtBuscar);
            //}

        } else if (e.getKeyCode() == KeyEvent.VK_F3) {

            // buscarProducto();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro de cerrar la ventana.\n" +
                    "Asegúrese de presionar F11 para grabar todos los datos antes de salir?")) {
                if (!UtilityInventario.indNuevaTransf()) {
                    //cancelaOperacion();
                }

                VariablesInventario.vArrayProductos = new ArrayList();
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

    private void seleccionarProducto() {
        boolean seleccion = tblListaProductos.getSelectedRow() == -1 ? true : false;
        if (!seleccion) {
            cargaDatosModal();
            if (!validarStockDisponible()) {
                return;
            }
            if (!validaProductoTomaInventario()) {
                FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario", txtBuscar);
                return;
            }
            if (!validarCodigo()) {
                return;
            }
            DlgDevolucionIngresoCantidad dlgDevolucionIngresoCantidad =
                new DlgDevolucionIngresoCantidad(myParentFrame, "", true);
            dlgDevolucionIngresoCantidad.setVisible(true);
            if (FarmaVariables.vAceptar) {
                agregarProductos();
                FarmaVariables.vAceptar = false;
                VariablesInventario.vArrayTransferenciaProductos = VariablesInventario.vArrayProductos;
                cargaDatosMemoria();
                cerrarVentana(true);
            } else {
                cargaDatosMemoria();
            }

        }


    }

    private void agregarProductos() {
        ArrayList<String> array = new ArrayList<String>();
        array.add(VariablesInventario.vCodigo);
        array.add(VariablesInventario.vDescrip);
        array.add(VariablesInventario.vDescUniPrese);
        array.add(VariablesInventario.vLaborat);
        array.add(Integer.toString(VariablesInventario.vCantMovi));
        array.add(VariablesInventario.vPrecVtaVig); //precio de venta
        // array.add(VariablesInventario.vFechaVec);//fecha de vencimiento del producto
        array.add(VariablesInventario.vFechVentprod);
        array.add(VariablesInventario.vMunLote); //numero de lote del producto
        array.add(VariablesInventario.vStockDispo); //stock del producto
        array.add(VariablesInventario.vValFrac); //valor fracc tranf
        array.add(FarmaUtility.formatNumber(VariablesInventario.vValPrectotal)); //valor precio total
        array.add(VariablesInventario.vfechaStock); //fecha y hora de tranferencia
        array.add(VariablesInventario.secResStock + ""); //stock de respaldo
        VariablesInventario.vArrayProductos.add(array);

    }


    private void cargaDatosModal()

    {
        int filaSelec = tblListaProductos.getSelectedRow();
        VariablesInventario.vfechaStock = tblListaProductos.getValueAt(filaSelec, 8).toString();
        VariablesInventario.vStockDispo = tblListaProductos.getValueAt(filaSelec, 3).toString();
        VariablesInventario.vDescUniPrese = tblListaProductos.getValueAt(filaSelec, 2).toString();
        VariablesInventario.vCodigo = tblListaProductos.getValueAt(filaSelec, 0).toString();
        VariablesInventario.vDescrip = tblListaProductos.getValueAt(filaSelec, 1).toString();
        VariablesInventario.vLaborat = tblListaProductos.getValueAt(filaSelec, 6).toString();
        VariablesInventario.vUnidLab = tblListaProductos.getValueAt(filaSelec, 4).toString();
        VariablesInventario.vValFrac = tblListaProductos.getValueAt(filaSelec, 10).toString();
        VariablesInventario.vIndProdConf = tblListaProductos.getValueAt(filaSelec, 7).toString();
        VariablesInventario.vPrecVtaVig = tblListaProductos.getValueAt(filaSelec, 11).toString();
        VariablesInventario.vCantUniPrese = tblListaProductos.getValueAt(filaSelec, 12).toString();
    }

    private boolean validarStockDisponible() {
        if (Integer.parseInt(VariablesInventario.vStockDispo) > 0) {
            return true;
        } else
            FarmaUtility.showMessage(this, "No puede realizar una devolucíon Stock Cero.", null);
        return false;
    }

    private boolean validaProductoTomaInventario() {
        if (VariablesInventario.vIndProdConf.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return true;
        else
            return false;

    }

    /**
     * Lista todo el maestro de productos
     */
    public void listaMaestroProductos() {
        ArrayList listaProd = new ArrayList();
        tableModel.clearTable();
        try {
            listaProd = facadeInventario.listarMaestroProductos(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal);
            tableModel.data = listaProd;
            VariablesInventario.vArrayDevol = listaProd; //se guarda los datos en un array temporal
            if (listaProd != null) {

                if (tableModel.data.size() > 0) {
                    tblListaProductos.setRowSelectionInterval(0, 0);
                    FarmaUtility.ordenar(tblListaProductos, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);


                }
            }

        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar Productos: \n" +
                    sql.getMessage(), tblListaProductos);
        }


    }

    private void cargaDatosMemoria() {
        tableModel.clearTable();
        try {

            tableModel.data = VariablesInventario.vArrayDevol;
            if (tableModel.data.size() > 0) {
                tblListaProductos.setRowSelectionInterval(0, 0);
                FarmaUtility.ordenar(tblListaProductos, tableModel, 1, FarmaConstants.ORDEN_ASCENDENTE);
            }

        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar Productos en Memoria: \n" +
                    e.getMessage(), tblListaProductos);
        }
    }

    /**
     * Busca un producto por codigo de prodcuto  a nivel de base de datos, este metodo no esta implementado.
     * */
    private void buscarProducto() {
        tableModel.clearTable();
        tableModel.data = new ArrayList();
        if (txtBuscar.getText().trim().length() >= 3) {
            try {
                ArrayList lista = facadeInventario.buscaProductosPorDescripcion(txtBuscar.getText().trim());
                if (lista.size() > 0) {
                    tableModel.data = lista;

                    // tblListaProductos.setRowSelectionInterval(0,0);
                    FarmaUtility.ordenar(tblListaProductos, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
                } else {
                    FarmaUtility.showMessage(this, "No se encontraron productos con la descripción ingresada.", null);
                }
            } catch (Exception e) {
                log.error("ocurrió el siguiente error al buscar productos : ", e);
                FarmaUtility.showMessage(this, "Error al buscar los productos. \n " + e.getMessage(), txtBuscar);
            }

        }
        if (txtBuscar.getText().trim().length() == 0 || txtBuscar.getText().trim() == null) {
            FarmaUtility.showMessage(this, "Debe Ingresar la Descripcion del Producto", null);
        }

        limpiarCampo();
    }

    /**
     *Busca un producto por codigo de barra a nivel de base de datos
     * @param pCodBarra
     */
    private void buscarProductoCodigoBarra(String pCodBarra) {


        tableModel.clearTable();
        tableModel.data = new ArrayList();
        if (pCodBarra != null) {
            try {
                ArrayList lista = null;
                lista = facadeInventario.obtenerProductosCodigoBarra(pCodBarra);
                if (lista.size() > 0) {
                    tableModel.data = lista;
                    tblListaProductos.setRowSelectionInterval(0, 0); //selecciona el registro buscado
                }

                else {
                    FarmaUtility.showMessage(this, "No se encontraron productos con El codigo de Barra ingresada.",
                                             null);

                }
            } catch (Exception e) {
                log.error("ocurrió el siguiente error al buscar productos : ", e);
                FarmaUtility.showMessage(this, "Error al buscar los productos. \n " + e.getMessage(), txtBuscar);
            }

            limpiarCampo();
        }
    }

    private void limpiarCampo() {
        txtBuscar.setText("");
    }


    /**valida la no repeticion de productos  cuando se realiza una revolucion
     *
     */
    public boolean validarCodigo() {
        String codigo;
        ArrayList lista = new ArrayList();

        array = tableModel.data;

        lista = VariablesInventario.vArrayTransferenciaProductos;
        int nRow = tblListaProductos.getSelectedRow();

        codigo = FarmaUtility.getValueFieldJTable(tblListaProductos, nRow, COL_COD_PROD);

        for (int j = 0; j < lista.size(); j++) {
            if (((ArrayList)lista.get(j)).get(0).toString().contains(codigo)) {
                FarmaUtility.showMessage(this, "El Producto ya se encuentra en la lista de Devoluciones", null);
                //break;
                return false;
            }

        }
        return true;
    }


    void setFacadeInventario(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }

    void setNotaEsCabDTO(NotaEsCabDTO notaEsCabDTO) {
        this.notaEsCabDTO = notaEsCabDTO;
    }

}
