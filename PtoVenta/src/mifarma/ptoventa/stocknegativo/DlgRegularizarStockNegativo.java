package mifarma.ptoventa.stocknegativo;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.stocknegativo.reference.ConstantsStockNegativo;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRegularizarStockNegativo extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRegularizarStockNegativo.class);
    private Frame myParent;
    private final int COL_ORD_LISTA = 18;
    private Integer cantProdSolicitud = 0;

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JLabelWhite lblProducto = new JLabelWhite();
    private JTextField txtProducto = new JTextField();

    private FarmaTableModel tableModelListaPrecioProductos;
    private JTable tblProductos = new JTable();
    private String codProdSelecc = "";
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite lblTitle = new JLabelWhite();

    public DlgRegularizarStockNegativo() {
        this(null, "", false);
    }

    public DlgRegularizarStockNegativo(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParent = parent;
        try {
            jbInit();
            initTableListaPreciosProductos();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(705, 445));
        this.getContentPane().setLayout(cardLayout1);
        this.setDefaultCloseOperation(0);

        pnlFondo.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 85, 680, 295));
        jScrollPane1.setFocusable(false);

        tblProductos.setFocusable(false);

        pnlTitle.setBounds(new Rectangle(10, 60, 680, 25));
        lblTitle.setText("Listado de Productos");
        lblTitle.setBounds(new Rectangle(20, 0, 380, 25));
        lblEsc.setText("[ Esc ] Cerrar");
        lblEsc.setBounds(new Rectangle(570, 385, 120, 30));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        pnlHeader.setBounds(new Rectangle(10, 5, 680, 50));
        pnlHeader.setFocusable(false);
        lblProducto.setText("Producto:");
        lblProducto.setBounds(new Rectangle(30, 15, 70, 20));
        lblProducto.setFocusable(false);
        txtProducto.setBounds(new Rectangle(100, 15, 380, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }
        });
        lblF11.setText("[ F11 ] Seleccionar");
        lblF11.setBounds(new Rectangle(10, 385, 125, 30));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setHorizontalTextPosition(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(pnlHeader, null);
        pnlFondo.add(lblEsc, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        pnlFondo.add(jScrollPane1, null);
        pnlHeader.add(txtProducto, null);
        pnlHeader.add(lblProducto, null);
        this.getContentPane().add(pnlFondo, "pnlHeader");
        this.setResizable(false);
        this.setTitle("Seleccionar producto para regularizar stock negativo");
        FarmaUtility.centrarVentana(this);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaPreciosProductos() {
        tableModelListaPrecioProductos =
                new FarmaTableModel(ConstantsStockNegativo.columnsListaProductos, ConstantsStockNegativo.defaultValuesListaProductos,
                                    0);
        clonarListadoProductos();

        FarmaUtility.initSelectList(tblProductos, tableModelListaPrecioProductos,
                                    ConstantsStockNegativo.columnsListaProductos);

        tblProductos.setName(ConstantsVentas.NAME_TABLA_PRODUCTOS);
        if (tableModelListaPrecioProductos.getRowCount() > 0)
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, COL_ORD_LISTA,
                                 FarmaConstants.ORDEN_ASCENDENTE);
    }

    public void setValores(Integer cantProdSolicitud) {
        this.cantProdSolicitud = cantProdSolicitud;
    }

    /**
     * Para clonar el listado de productos original.
     * @author Edgar Rios Navarro
     * @since 29.05.2008
     */
    private void clonarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; i < VariablesVentas.tableModelListaGlobalProductos.data.size(); i++) {
            ArrayList aux = (ArrayList)((ArrayList)VariablesVentas.tableModelListaGlobalProductos.data.get(i)).clone();
            arrayClone.add(aux);
        }
        tableModelListaPrecioProductos.data = arrayClone;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void regularizar() {
        Integer stockProdTemp =
            Integer.parseInt(FarmaUtility.getValueFieldJTable(tblProductos, tblProductos.getSelectedRow(), 5));

        if (stockProdTemp == 0)
            FarmaUtility.showMessage(this, "ATENCIÓN: El producto elegido no posee stock", this);
        else if ((stockProdTemp - this.cantProdSolicitud) < 1)
            FarmaUtility.showMessage(this,
                                     "ATENCIÓN: El producto elegido no posee suficiente stock \npara regularizar el detalle de la solicitud correctamente",
                                     this);
        else {
            if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                          "ATENCION: El regularizar la solicitud con este producto actualizara \nel stock del mismo de " +
                                                          stockProdTemp + " a " +
                                                          (stockProdTemp - this.cantProdSolicitud) +
                                                          " unidades\n¿Esta seguro de continuar?")) {
                DlgLogin dlgLogin = new DlgLogin(myParent, ConstantsPtoVenta.MENSAJE_LOGIN, true);
                dlgLogin.setRolUsuario(FarmaConstants.ROL_AUDITORIA);
                dlgLogin.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    codProdSelecc = FarmaUtility.getValueFieldJTable(tblProductos, tblProductos.getSelectedRow(), 1);
                    cerrarVentana(true);
                }
            }
        }
    }


    private String buscaCodBarra() {
        UtilityPtoVenta.limpiaCadenaAlfanumerica(txtProducto);
        //pCodigoBarra = productoBuscar;

        String productoBuscar = "";
        try {
            productoBuscar = DBVentas.obtieneCodigoProductoBarra(txtProducto.getText());
            return productoBuscar;
        } catch (SQLException q) {
            log.error("", q);
            return "000000";
        }
    }

    public String getCodProdSelecc() {
        return this.codProdSelecc;
    }

    //**************************************************************************
    //                        Metodos auxiliares de eventos
    //**************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos, txtProducto, 2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //ERIOS 03.07.2013 Limpia la caja de texto
            e.consume();
            if (tblProductos.getSelectedRow() >= 0) {
                String productoBuscar = txtProducto.getText().trim().toUpperCase();
                String pCodigoBusqueda = txtProducto.getText().trim();

                //si se pulsa ENTER se verifica si la caja de texto tiene el mismo texto que la descripcion del registro seleccionado
                if (FarmaUtility.findTextInJTable(tblProductos, pCodigoBusqueda, 2,
                                                  2)) { //si se encuentra en la descripcion, mostrar el detalle
                    //seleccionarProducto();
                }
                //si no son iguales, buscar por codigo
                else if (FarmaUtility.findTextInJTable(tblProductos, pCodigoBusqueda, 1,
                                                       2)) { //si se encuentra en el codigo, mostrar el detalle
                    //seleccionarProducto();
                }
                //si no se enceuntra codigo, buscar por codigo de barras
                else if (!"000000".equalsIgnoreCase(buscaCodBarra())) { //si se encuentra el cod de barras, ubicarlo en la tabla y mostrar el detalle
                    String temp = buscaCodBarra();
                    FarmaUtility.findTextInJTable(tblProductos, temp, 1, 2);
                    //seleccionarProducto();
                }
                //si no se encuentra codigo de barras, buscar por otro metodo
                //si no se encuentra mostrar error o mostrar la ventanita del seleccionado?
                else {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtProducto);
                }
                FarmaUtility.moveFocus(txtProducto);
            }
        }
        //if (e.getKeyCode() == KeyEvent.VK_F11) {
        if(UtilityPtoVenta.verificaVK_F11(e)){
            regularizar();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblProductos, txtProducto, 2);
        muestraInfoProd();
    }

    private void muestraInfoProd() {
        String descUnidPres = "";
        String stkProd = "";
        String valPrecPres = "";
        String valFracProd = "";
        String indProdCong = "";
        String valPrecVta = "";
        String descUnidVta = "";
        String indProdHabilVta = "";
        String porcDscto_1 = "";
        String tipoProd = "";
        String bonoProd = "";


        if (tblProductos.getRowCount() <= 0)
            return;

        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        log.debug("Tamaño en muestra info" + myArray.size());

        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            valPrecPres = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            indProdCong = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            valPrecVta = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            descUnidVta = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            indProdHabilVta = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
            porcDscto_1 = ((String)((ArrayList)myArray.get(0)).get(8)).trim();
            //log.info("DLGLISTAPRODUCTOS : porcDscto_1 - " + porcDscto_1);
            tipoProd = ((String)((ArrayList)myArray.get(0)).get(9)).trim();
            bonoProd = ((String)((ArrayList)myArray.get(0)).get(10)).trim();

        } else {
            stkProd = "";
            descUnidPres = "";
            valFracProd = "";
            valPrecPres = "";
            indProdCong = "";
            valPrecVta = "";
            descUnidVta = "";
            indProdHabilVta = "";
            porcDscto_1 = "";
            tipoProd = "";
            bonoProd = "";

            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtProducto);
        }

        tblProductos.setValueAt(stkProd, tblProductos.getSelectedRow(), 5);
        tblProductos.setValueAt(valPrecVta, tblProductos.getSelectedRow(), 6);
        tblProductos.setValueAt(descUnidVta, tblProductos.getSelectedRow(), 3);
        tblProductos.setValueAt(bonoProd, tblProductos.getSelectedRow(), 7);

        tblProductos.repaint();
    }

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        int vFila = tblProductos.getSelectedRow();
        String codProd = FarmaUtility.getValueFieldJTable(tblProductos, vFila, 1);

        try {
            DBVentas.obtieneInfoProductoVta(pArrayList, codProd);
        } catch (SQLException sql) {
            log.error(null, sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtProducto);
        }
    }
}
