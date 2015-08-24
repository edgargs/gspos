package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMercaderiaDirectaProducto extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgMercaderiaDirectaProducto.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    String strCodigoOrdCompDetalle = "1";
    ArrayList arrayProductos = new ArrayList();
    ArrayList arrayCab = new ArrayList();
    ArrayList arrayclone = new ArrayList();
    ArrayList arrayProdOrdComp = new ArrayList();
    private Pattern pattern;
    private Matcher matcher;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JPanel pnlHeader = new JPanel();
    private JButton btnListaProducto = new JButton();
    private JPanel pnlTitle = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JButton btnProducto = new JButton();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lblTotal_l = new JLabelWhite();
    private JLabelWhite lblTotal = new JLabelWhite();
    private JFormattedTextField txtRedondeo = new JFormattedTextField();
    private JLabelFunction lblF4 = new JLabelFunction();
    private FacadeInventario facadeInventario = new FacadeInventario();
    private static final String REDONDEO_PATTERN = "^[+-]?[0-9]+(?:\\.[0-9]+)?$";
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JLabelFunction lblF5 = new JLabelFunction();

    private final int COL_VAL_FRAC = 8;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMercaderiaDirectaProducto() {
        this(null, "", false);
    }

    public DlgMercaderiaDirectaProducto(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(720, 460));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Producto Ord. Comp. - Mercaderia Directa");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(594, 405));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(10, 370, 140, 20));
        lblF5.setText("[F5] Deseleccionar");
        lblF5.setBounds(new Rectangle(165, 370, 120, 20));
        scrListaProductos.setBounds(new Rectangle(10, 90, 695, 240));
        scrListaProductos.setBackground(new Color(255, 130, 14));
        pnlHeader.setBounds(new Rectangle(10, 65, 695, 25));
        pnlHeader.setBackground(new Color(255, 130, 14));
        pnlHeader.setLayout(null);
        btnListaProducto.setText("Lista de Productos");
        btnListaProducto.setBounds(new Rectangle(10, 0, 145, 25));
        btnListaProducto.setBackground(new Color(255, 130, 14));
        btnListaProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnListaProducto.setBorderPainted(false);
        btnListaProducto.setContentAreaFilled(false);
        btnListaProducto.setDefaultCapable(false);
        btnListaProducto.setFocusPainted(false);
        btnListaProducto.setFont(new Font("SansSerif", 1, 11));
        btnListaProducto.setForeground(Color.white);
        btnListaProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnListaProducto.setRequestFocusEnabled(false);
        pnlTitle.setBounds(new Rectangle(10, 20, 695, 40));
        pnlTitle.setBackground(new Color(43, 141, 39));
        pnlTitle.setLayout(null);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(425, 10, 115, 25));
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setMnemonic('b');
        btnBuscar.setRequestFocusEnabled(false);
        txtProducto.setBounds(new Rectangle(110, 10, 295, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }
        });
        btnProducto.setText("Producto :");
        btnProducto.setBounds(new Rectangle(25, 10, 70, 20));
        btnProducto.setFont(new Font("SansSerif", 1, 12));
        btnProducto.setForeground(Color.white);
        btnProducto.setBackground(new Color(43, 141, 39));
        btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnProducto.setBorderPainted(false);
        btnProducto.setContentAreaFilled(false);
        btnProducto.setDefaultCapable(false);
        btnProducto.setFocusPainted(false);
        btnProducto.setMnemonic('p');
        btnProducto.setRequestFocusEnabled(false);
        btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        btnProducto.addContainerListener(new ContainerAdapter() {
            public void componentRemoved(ContainerEvent e) {
                btnProducto_componentRemoved(e);
            }
        });
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(480, 370, 105, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(605, 370, 85, 20));
        jPanelTitle1.setBounds(new Rectangle(10, 345, 660, 20));
        jPanelTitle2.setBounds(new Rectangle(10, 330, 695, 20));
        jPanelTitle2.setSize(new Dimension(695, 20));
        lblTotal_l.setText("TOTAL S/.");
        lblTotal_l.setBounds(new Rectangle(545, 0, 70, 20));
        lblTotal.setText("0.00");
        lblTotal.setBounds(new Rectangle(625, 0, 55, 20));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        txtRedondeo.setBounds(new Rectangle(475, 0, 50, 20));
        txtRedondeo.setText("0.00");
        txtRedondeo.setHorizontalAlignment(SwingConstants.RIGHT);
        txtRedondeo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pattern = Pattern.compile(REDONDEO_PATTERN, Pattern.CANON_EQ);
                txtRedondeo_keyPressed(e);
            }

        });
        lblF4.setBounds(new Rectangle(295, 370, 170, 20));
        lblF4.setText("[ F4 ] Modificar Cabecera");
        jButtonLabel1.setText("Redondeo:");
        jButtonLabel1.setBounds(new Rectangle(405, 0, 65, 20));
        jButtonLabel1.setMnemonic('r');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        scrListaProductos.getViewport();
        pnlHeader.add(btnListaProducto, null);
        pnlTitle.add(btnBuscar, null);
        pnlTitle.add(txtProducto, null);
        pnlTitle.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //  jPanelTitle2.add(jButtonLabel1, null);Cesar Huanes se quito el redondeo y el total
        // jPanelTitle2.add(txtRedondeo, null);
        // jPanelTitle2.add(lblTotal, null);
        //jPanelTitle2.add(lblTotal_l, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(jPanelTitle2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEnter, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlTitle, null);

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
        tblListaProductos.getTableHeader().setReorderingAllowed(false);
        tblListaProductos.getTableHeader().setResizingAllowed(false);
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaItemOrden, ConstantsInventario.defaultListaItemOrden,
                                    0);
        FarmaUtility.initSelectList(tblListaProductos, tableModel, ConstantsInventario.columnsListaItemOrden);

        mostrarDetalleOC();

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProducto);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void txtRedondeo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (validateText(txtRedondeo.getText().trim())) {
                addRedondeo();
                FarmaUtility.moveFocus(txtProducto);
            } else {
                FarmaUtility.showMessage(this, "Debe ingresar solo números decimales.", null);
                FarmaUtility.moveFocus(txtRedondeo);
            }

        }
    }


    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtProducto, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtProducto.getText().trim(), 1, 2))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtProducto);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtProducto, 2);
    }

    private void btnProducto_componentRemoved(ContainerEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private boolean validateText(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtRedondeo);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionarProducto();
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (arrayProductos.size() == 0) {
                FarmaUtility.showMessage(this, "Debe seleccionar un producto para su ingreso.", null);
            } else {
                cargaProductosSeleccionados();
                cabeceraRecepcion();
                guardaDB();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de salir? Se perderá los datos.")) {
                arrayclone.clear();
                cerrarVentana(false);
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            DlgMercaderiaDirectaCabecera dlgModifyHead = new DlgMercaderiaDirectaCabecera(myParentFrame, "", true);
            dlgModifyHead.modifyHead(e);
            dlgModifyHead.setVisible(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            deseleccionarProducto();
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

    private void seleccionarProducto() {
        boolean seleccion =
            ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0)).booleanValue();

        if (!verificaCantIngresada()) { //validamos la cantidad
            return;
        }
        cargaCabeceraIngreseCantidad();
        // kmoncada 15.07.2014 obtiene cantidad adicional permitida por OC y valor de innerpack del producto
        int rowSelec = tblListaProductos.getSelectedRow();
        int cantAdicPermitidaOCProd =
            FarmaUtility.trunc(FarmaUtility.getValueFieldArrayList(tableModel.data, rowSelec, 9));
        int valInnerPackProd = FarmaUtility.trunc(FarmaUtility.getValueFieldArrayList(tableModel.data, rowSelec, 10));

        DlgMercaderiaDirectaCantidad dlgMercaderiadirectaCantidad =
            new DlgMercaderiaDirectaCantidad(myParentFrame, "", true);
        dlgMercaderiadirectaCantidad.setCantAdicPermitidaOCProd(cantAdicPermitidaOCProd);
        dlgMercaderiadirectaCantidad.setValInnerPackProd(valInnerPackProd);
        dlgMercaderiadirectaCantidad.setVisible(true);
        if (FarmaVariables.vAceptar) {
            // si esta marcado borramos el registro anterior adicionado para que no se repita en el reporte.
            if (seleccion == true) {
                borrarProducto();
            }
            agregarProducto();
            calculoTotal();
            cargaNewProduct();
            cargarIGV();
            if (seleccion == false) { // si esta deselecionado selecciona caso contrario lo mantiene seleccionado
                FarmaUtility.setCheckValue(tblListaProductos, false);
            }
            FarmaVariables.vAceptar = false;
            tblListaProductos.setRowSelectionInterval(VariablesInventario.vPos, VariablesInventario.vPos);

        }
    }

    private void cargaCabeceraIngreseCantidad() {
        int row = tblListaProductos.getSelectedRow();

        VariablesInventario.vCodProducto = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 1);
        VariablesInventario.vDescProd = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 2);
        VariablesInventario.vUnidMedida = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 6);
        VariablesInventario.vCantPedida = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 3);
        //VariablesInventario.vRecepTotal = ((ArrayList)arrayProdOrdComp.get(row)).get(4).toString();
        // VariablesInventario.vRecepTotal = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 4);
        VariablesInventario.vRecepTotal =
                FarmaUtility.getValueFieldArrayList(tableModel.data, row, 12); //ASOSA - 30/09/2014
        VariablesInventario.vEstadoItem =
                FarmaUtility.getValueFieldArrayList(tableModel.data, row, 11); //ASOSA - 24/09/2014
        VariablesInventario.vCantEntregada = "";
        VariablesInventario.vPrecioIGV = "";
        VariablesInventario.vRedondeo = "";
        VariablesInventario.vPrecUnit = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 5);
        VariablesInventario.vPos = row;
        VariablesInventario.vValFrac = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 8);

    }

    private void agregarProducto() {
        ArrayList array = new ArrayList();
        //   arrayProductos = new ArrayList();
        double total =
            (((FarmaUtility.getDecimalNumber(VariablesInventario.vCantEntregada))) * FarmaUtility.getDecimalNumber(VariablesInventario.vPrecioUnit));
        VariablesInventario.vImportRecep = FarmaUtility.formatNumber(total, 2);
        array.add(VariablesInventario.vCodProducto.trim());
        array.add(VariablesInventario.vCantPedida.trim());
        array.add(VariablesInventario.vCantEntregada.trim());
        array.add(VariablesInventario.vPrecioUnit.trim());
        array.add(VariablesInventario.vPrecioIGV.trim());
        array.add(VariablesInventario.vImportRecep.trim());
        array.add(strCodigoOrdCompDetalle.toString().trim());
        array.add(VariablesInventario.vValFrac);
        arrayProductos.add(array);
    }

    private void borrarProducto() {
        double subTotal = Double.parseDouble(VariablesInventario.vImportRecep);
        String cod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString();
        for (int i = 0; i < arrayProductos.size(); i++) {
            if (((ArrayList)arrayProductos.get(i)).contains(cod)) {
                //restarSubtotal
                subTotal = subTotal - (Double.parseDouble(((ArrayList)arrayProductos.get(i)).get(5).toString()));
                lblTotal.setText(FarmaUtility.formatNumber(subTotal, 2));
                VariablesInventario.vImportRecep = lblTotal.getText();

                int newValu =
                    Integer.parseInt(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 4).toString()) -
                    Integer.parseInt(((ArrayList)arrayProductos.get(i)).get(2).toString());

                tblListaProductos.setValueAt(String.valueOf(newValu), tblListaProductos.getSelectedRow(), 4);
                //Actualiza IGV DataBase
                tblListaProductos.setValueAt(((ArrayList)arrayclone.get(i)).get(6).toString(),
                                             tblListaProductos.getSelectedRow(), 6);
                //Elimina Registro
                arrayProductos.remove(i);
                break;
            }
        }
    }
    //columnsListaGuiaIngresoProductos

    private void cargaProductosSeleccionados() {
        if (VariablesInventario.vArrayIngresoMercaderiaDirecta.size() > 0) {
            arrayProductos = VariablesInventario.vArrayIngresoMercaderiaDirecta;
            String cod;
            for (int i = 0; i < arrayProductos.size(); i++) {
                cod = ((ArrayList)arrayProductos.get(i)).get(0).toString();
                for (int j = 0; j < tblListaProductos.getRowCount(); j++) {
                    if (((ArrayList)tableModel.getRow(j)).contains(cod)) {
                        tableModel.setValueAt(new Boolean(true), j, 0);
                        break;
                    }
                }
            }
        }
    }

    private void calculoTotal() {
        double total = 0.0;
        for (int i = 0; i < arrayProductos.size(); i++) {
            total =
                    total + (((FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayProductos, i, 2)))) *
                             FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(arrayProductos, i, 3)));
        }
        lblTotal.setText(FarmaUtility.formatNumber(total, 2));
        VariablesInventario.vImportRecep = lblTotal.getText();
    }

    private void addRedondeo() {
        double vRedon = Double.parseDouble(txtRedondeo.getText().trim());
        double vTotal = Double.parseDouble(lblTotal.getText().trim());
        lblTotal.setText(FarmaUtility.formatNumber(vTotal + vRedon, 2));
        VariablesInventario.vRedondeo = txtRedondeo.getText();
        VariablesInventario.vImportRecep = lblTotal.getText();
    }

    private void cargaNewProduct() {
        //CHUANES 27.02.2014
        //Refrescamos el array para obtener el primer valor de la cant recibida
        //arrayProdOrdComp = facadeInventario.listarProdOrdenCompra(VariablesInventario.vNumOrdenCompra);
        String vall = ((ArrayList)arrayProdOrdComp.get(tblListaProductos.getSelectedRow())).get(4).toString();
        int cantPed = Integer.parseInt(vall) + Integer.parseInt(VariablesInventario.vCantEntregada);
        tableModel.setValueAt(String.valueOf(cantPed), tblListaProductos.getSelectedRow(), 4);
        tblListaProductos.setSize(5, 120);
        //tblListaProductos.show();

    }

    private void cargarIGV() {
        String igv = VariablesInventario.vPrecioIGV;
        VariablesInventario.vIGVDB = tableModel.getValueAt(tblListaProductos.getSelectedRow(), 6).toString();
        tableModel.setValueAt(String.valueOf(igv), tblListaProductos.getSelectedRow(), 6);
        tblListaProductos.setSize(5, 120);
        //tblListaProductos.show();
    }

    private void cabeceraRecepcion() {
        arrayCab.add(FarmaVariables.vCodGrupoCia.trim());
        arrayCab.add(FarmaVariables.vCodCia.trim());
        arrayCab.add(FarmaVariables.vCodLocal.trim());
        arrayCab.add(FarmaVariables.vIdUsu.trim());
        arrayCab.add(VariablesInventario.vNumOrdenCompra.trim());
        arrayCab.add(VariablesInventario.vFechIngreso.trim());
        int tipoDoc = Integer.valueOf(VariablesInventario.vIdeDocumento.trim()) + 1; //CHUANES  27.01.2014
        arrayCab.add('0' + String.valueOf(tipoDoc)); //correcion del tipo de documento
        arrayCab.add(VariablesInventario.vSerieDocument.trim());
        arrayCab.add(VariablesInventario.vNumeroDocument.trim());
        arrayCab.add(VariablesInventario.vCantItem.trim());
        arrayCab.add(VariablesInventario.vCodProveedor.trim());
        arrayCab.add(VariablesInventario.vImporteTotal.trim());
        arrayCab.add(VariablesInventario.vImportRecep.trim());
        arrayCab.add(VariablesInventario.vRedondeo.trim());
    }

    private void guardaDB() {

        boolean vRecepcion;
        try {
            vRecepcion = facadeInventario.guardarRecepcionOC(arrayCab, arrayProductos);
            //CHUANES 27.02.2014
            // No tiene sentido la condicion porque  siempre ingresa el mismo numero de documento.
            /*  if(!vRecepcion){

                FarmaUtility.showMessage(this, "El Documento: " + VariablesInventario.vDescripDocum +
                                               " Serie: " + VariablesInventario.vSerieDocument +
                                               " Numero: " + VariablesInventario.vNumeroDocument +
                                               " ya fue ingresado.", null);
            }else{*/
            FarmaUtility.showMessage(this,
                                     "Se agregaron sus producto ala Ord. de Compra " + VariablesInventario.vNumOrdenCompra.trim(),
                                     null);
            cerrarVentana(true);
            //   }

        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, e.getMessage(), txtProducto);
        }
        arrayclone.clear();

        boolean vCierre =
            facadeInventario.cierreOC(FarmaVariables.vCodGrupoCia.trim(), FarmaVariables.vCodLocal.trim(),
                                      VariablesInventario.vNumOrdenCompra.trim());
        if (vCierre) {
            FarmaUtility.showMessage(this,
                                     "La orden de compra " + VariablesInventario.vNumeroDocument + " Se cerro correctamente.",
                                     null);
            cerrarVentana(true);
        } else {
            ;
        }

    }

    void setFacade(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }

    private void mostrarDetalleOC() {

        arrayProdOrdComp = facadeInventario.listarProdOrdenCompra(VariablesInventario.vNumOrdenCompra);
        tableModel.data = arrayProdOrdComp;
        arrayclone = (ArrayList)arrayProdOrdComp.clone();

        for (int i = 0; i < tblListaProductos.getRowCount(); i++)
            tblListaProductos.setValueAt(new Boolean(false), i, 0);
        cargaProductosSeleccionados();
        FarmaGridUtils.showCell(tblListaProductos, 0, 0);
    }
    //Cesar Huanes-26.02.2014

    private void deseleccionarProducto() {
        boolean seleccion =
            ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0)).booleanValue();
        if (seleccion) {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Desea deseleccionar? Se perderá los datos ingresados.")) {
                borrarProducto();
                FarmaUtility.setCheckValue(tblListaProductos, false);
            }
        }
    }
    //Cesar Huanes-03.03.2014
    //Se verifica a nivel de base de datos la cant. Ped con la cant Rec.

    private boolean verificaCantIngresada() {
        int row = tblListaProductos.getSelectedRow();
        //arrayProdOrdComp = facadeInventario.listarProdOrdenCompra(VariablesInventario.vNumOrdenCompra);
        //String cantRec = ((ArrayList)arrayProdOrdComp.get(row)).get(3).toString();
        //String cantPed = ((ArrayList)arrayProdOrdComp.get(row)).get(4).toString();
        // KMONCADA
        String estadoProdCompleto = ((ArrayList)arrayProdOrdComp.get(row)).get(11).toString();
        //if (cantRec.equals(cantPed)) {
        if ("T".equalsIgnoreCase(estadoProdCompleto)) { // T --> TRABAJADA A --> PENDIENTE --> N --> Nuevo
            FarmaUtility.showMessage(this, "Ya se ingresó la cantidad solicitada del producto", null);
            return false;
        } else
            return true;
    }
}
