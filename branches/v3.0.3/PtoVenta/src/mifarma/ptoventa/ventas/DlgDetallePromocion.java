package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetallePromocion extends JDialog {

    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgDetallePromocion.class);

    Frame myParentFrame;
    private FarmaTableModel tableModelListaPaquete1;
    private FarmaTableModel tableModelListaPaquete2;
    private ArrayList myArray = new ArrayList();
    private final int COL_COD = 0;
    //private final int COL_DESC=1;
    //private final int COL_DESL=2;
    //private final int COL_CODP1=3;
    //private final int COL_CODP2=4;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    JPanel pnlStock = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    JPanel pnlStock1 = new JPanel();
    XYLayout xYLayout3 = new XYLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTable jTable1 = new JTable();
    //private JTable jTable2 = new JTable();
    private JButtonLabel btnpaquete1 = new JButtonLabel();
    private JButtonLabel btnpaquete2 = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    public JTextField txtCantidad = new JTextField();
    public JTextField txtPrecioTotal = new JTextField();
    JLabel lblUnidadT5 = new JLabel();
    private JTable tblpaquete1 = new JTable();
    private JTable tblpaquete2 = new JTable();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JTextArea txtDescPromocion = new JTextArea();
    private JScrollPane jScrollPane3 = new JScrollPane();
    JPanel pnlStock2 = new JPanel();
    XYLayout xYLayout4 = new XYLayout();
    private JButtonLabel btnDescripcion = new JButtonLabel();
    private JTextField txtPrecioTotalRedondeado = new JTextField();


    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgDetallePromocion() {
        this(null, "", false);
    }

    public DlgDetallePromocion(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(749, 262));
        this.setTitle("Detalle de Pack");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

        });
        pnlStock.setBounds(new Rectangle(10, 5, 720, 5));
        pnlStock.setFont(new Font("SansSerif", 0, 11));
        pnlStock.setBackground(new Color(255, 130, 14));
        pnlStock.setLayout(xYLayout2);
        pnlStock.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlStock.setForeground(Color.white);
        pnlStock1.setBounds(new Rectangle(10, 15, 720, 5));
        pnlStock1.setFont(new Font("SansSerif", 0, 11));
        pnlStock1.setBackground(new Color(255, 130, 14));
        pnlStock1.setLayout(xYLayout3);
        pnlStock1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlStock1.setForeground(Color.white);
        jScrollPane1.setBounds(new Rectangle(10, 10, 720, 5));
        jScrollPane2.setBounds(new Rectangle(10, 20, 720, 5));
        btnpaquete1.setText("Paquete 1");
        btnpaquete1.setMnemonic('1');
        btnpaquete1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnpaquete1_actionPerformed(e);
            }
        });
        btnpaquete2.setText("Paquete 2");
        btnpaquete2.setMnemonic('2');
        btnpaquete2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnpaquete2_actionPerformed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(645, 195, 85, 20));
        lblF11.setText("[ ENTER ] Aceptar");
        lblF11.setBounds(new Rectangle(530, 195, 110, 20));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
        txtCantidad.setDocument(new FarmaLengthText(6));
        txtCantidad.setText("0");
        txtCantidad.setFont(new Font("SansSerif", 1, 11));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
                // txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        txtCantidad.setBounds(new Rectangle(245, 200, 60, 20));
        txtPrecioTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioTotal.setDocument(new FarmaLengthText(6));
        txtPrecioTotal.setFont(new Font("SansSerif", 1, 11));
        txtPrecioTotal.setVisible(false);
        txtPrecioTotal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //txtCantidad_keyPressed(e);
            }
        });
        txtPrecioTotal.setBounds(new Rectangle(35, 215, 60, 20));
        txtPrecioTotal.setEnabled(false);
        lblUnidadT5.setText("Precio Total: S/.");
        lblUnidadT5.setFont(new Font("SansSerif", 1, 11));
        lblUnidadT5.setBounds(new Rectangle(10, 195, 95, 20));
        btnCantidad.setText("Cantidad");
        btnCantidad.setMnemonic('C');
        btnCantidad.setBounds(new Rectangle(185, 195, 65, 20));
        btnCantidad.setForeground(new Color(2, 2, 2));
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
        txtDescPromocion.setEnabled(false);
        txtDescPromocion.setFont(new Font("Arial", 0, 21));
        jScrollPane3.setBounds(new Rectangle(10, 30, 720, 155));
        pnlStock2.setBounds(new Rectangle(10, 5, 720, 25));
        pnlStock2.setFont(new Font("SansSerif", 0, 11));
        pnlStock2.setBackground(new Color(255, 130, 14));
        pnlStock2.setLayout(xYLayout4);
        pnlStock2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlStock2.setForeground(Color.white);
        btnDescripcion.setText("Descripción");
        btnDescripcion.setMnemonic('1');

        txtPrecioTotalRedondeado.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioTotalRedondeado.setDocument(new FarmaLengthText(6));
        txtPrecioTotalRedondeado.setFont(new Font("SansSerif", 1, 11));
        txtPrecioTotalRedondeado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //txtCantidad_keyPressed(e);
            }
        });
        txtPrecioTotalRedondeado.setBounds(new Rectangle(110, 200, 60, 20));
        txtPrecioTotalRedondeado.setEnabled(false);

        pnlStock2.add(btnDescripcion, new XYConstraints(5, 0, 70, 20));
        jPanelWhite1.add(txtPrecioTotalRedondeado, null);
        jPanelWhite1.add(pnlStock2, null);
        jScrollPane3.getViewport().add(txtDescPromocion, null);
        jPanelWhite1.add(jScrollPane3, null);
        jPanelWhite1.add(btnCantidad, null);
        jPanelWhite1.add(lblUnidadT5, null);
        jPanelWhite1.add(txtPrecioTotal, null);
        jPanelWhite1.add(txtCantidad, null);
        jPanelWhite1.add(lblF11, null);
        jPanelWhite1.add(lblEsc, null);
        jScrollPane2.getViewport().add(tblpaquete2, null);
        jPanelWhite1.add(jScrollPane2, null);
        jPanelWhite1.add(jScrollPane1, null);
        pnlStock1.add(btnpaquete2, new XYConstraints(5, 0, 65, 20));
        jPanelWhite1.add(pnlStock1, null);
        pnlStock.add(btnpaquete1, new XYConstraints(5, 0, 60, 20));
        jPanelWhite1.add(pnlStock, null);
        jScrollPane1.getViewport().add(jTable1, null);
        jScrollPane1.getViewport().add(tblpaquete1, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableListaPaquete1();
        initTableListaPaquete2();
        listaPaquetes();
        obtieneCantidad();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaPaquete1() {
        tableModelListaPaquete1 =
                new FarmaTableModel(ConstantsVentas.columnsDetallePromocion, ConstantsVentas.defaultValuesDetallePromocions,
                                    COL_COD);
        FarmaUtility.initSimpleList(tblpaquete1, tableModelListaPaquete1, ConstantsVentas.columnsDetallePromocion);
    }

    private void initTableListaPaquete2() {
        tableModelListaPaquete2 =
                new FarmaTableModel(ConstantsVentas.columnsDetallePromocion, ConstantsVentas.defaultValuesDetallePromocions,
                                    COL_COD);
        FarmaUtility.initSimpleList(tblpaquete2, tableModelListaPaquete2, ConstantsVentas.columnsDetallePromocion);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantidad);
        cargarConf(false);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
        /**
     * Limpia los valores
     * @author : dubilluz
     * @since  : 25.06.2007
     */
        VariablesVentas.accionModificar = false;
        limpiaValores();

    }

    private void btnpaquete1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblpaquete1);
    }

    private void btnpaquete2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblpaquete2);
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //log.debug("ENTER");
            //Consultara si Existe Producto Virtual
            if (!VariablesVentas.venta_producto_virtual) {
                if (!VariablesVentas.accionModificar) {
                    if (verificaProducto()) {
                        aceptarComprometidos();
                    } else {
                        FarmaUtility.showMessage(this, "La Promocion tiene productos ya seleccionados", txtCantidad);
                    }
                } else {
                    aceptarComprometidos();
                }
            } else {
                cerrarVentana(true);
                FarmaUtility.showMessage(this,
                                         "La venta de un Producto Virtual debe ser única por pedido. Verifique!!!",
                                         null);
            }
        } else
            chkKeyPressed(e);

    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * Se lista el detalle  de los paquetes 1 y 2 de la promocion
     */
    private void listaPaquetes() {
        try {
            // cargarDatos();
            DBVentas.obtieneListadoPaquetes(myArray, VariablesVentas.vCodProm.trim());
            DBVentas.listaPaquete1(tableModelListaPaquete1, VariablesVentas.vCodProm);
            DBVentas.listaPaquete2(tableModelListaPaquete2, VariablesVentas.vCodProm);
            int cantmax = Integer.parseInt(FarmaUtility.getValueFieldJTable(tblpaquete1, 0, 10));
            VariablesVentas.vcantMaxVta = cantmax;
            log.debug("*****");
            double total1 = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblpaquete1, 0, 9));
            double total2 = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldJTable(tblpaquete2, 0, 9));
            //JCHAVEZ 29102009 inicio
            double total1Aux = total1;
            double total2Aux = total2;
            //JCHAVEZ 29102009 fin
            total2 += total1;

            //JCHAVEZ 29102009 inicio
            double totalRedondeadoNum = DBVentas.getPrecioRedondeado(total2);
            String totalRedondeadoStr =
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + totalRedondeadoNum), 3);
            txtPrecioTotalRedondeado.setText(totalRedondeadoStr);
            log.debug("totalRedondeado:" + totalRedondeadoStr);
            //JCHAVEZ 29102009 fin

            // int totalDou = Integer.parseInt(totalStr.trim());

            //JCHAVEZ 29102009 inicio
            try {
                if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                    VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();
                }

                if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                    double total1AuxRedondeado = DBVentas.getPrecioRedondeado(total1Aux);
                    double total2AuxRedondeado = DBVentas.getPrecioRedondeado(total2Aux);
                    total2AuxRedondeado += total1AuxRedondeado;
                    VariablesVentas.vVentaTotal =
                            FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + total2AuxRedondeado), 3);

                    txtPrecioTotal.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + total2), 3));
                } else if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("N")) {
                    /* 25.01.2008 ERIOS Se ajusta la precision. */
                    VariablesVentas.vVentaTotal =
                            FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + total2), 3);
                    txtPrecioTotal.setText(VariablesVentas.vVentaTotal);
                }
            } catch (SQLException ex) {
                log.error("", ex);
            }
            //JCHAVEZ 29102009 fin


            if (tblpaquete1.getRowCount() > 0 && tblpaquete2.getRowCount() > 0) {
                FarmaUtility.ordenar(tblpaquete1, tableModelListaPaquete1, COL_COD, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.ordenar(tblpaquete2, tableModelListaPaquete2, COL_COD, FarmaConstants.ORDEN_ASCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar los paquetes.\n" +
                    sql.getMessage(), tblpaquete1);
        }
    }

    /**
     * Se acepta la cantidad ingresada y se llena los arreglos con todos los datos de cada producto
     */
    private void aceptarComprometidos() {
        if (!validaCantidadIngreso()) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.", txtCantidad);
            return;
        }

        if (Integer.parseInt(txtCantidad.getText().trim()) > VariablesVentas.vcantMaxVta) {
            FarmaUtility.showMessage(this, "Se excede la cantidad maxima de ventas.", txtCantidad);
            return;
        }
        if (!validaStockActual()) {
            FarmaUtility.showMessage(this, "No hay disponibilidad de Stock", txtCantidad);
            return;
        }


        log.debug("Acepta ");
        if (tblpaquete1.getRowCount() > 1) {
            log.debug("", tblpaquete1.getValueAt(tblpaquete1.getSelectedRow(), 9));
        }


        log.debug("Acepta Comprometidos");
        log.debug("myArray xxx " + myArray);
        VariablesVentas.vCant_Ingresada = txtCantidad.getText();
        AceptarProductosComprometidos();
        cerrarVentana(true);
    }

    /**
     * Se valida el valor de la cantidad ingresada
     */
    private boolean validaCantidadIngreso() {
        boolean valor = false;
        String cantIngreso = txtCantidad.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso) && Integer.parseInt(cantIngreso) > 0)
            valor = true;
        return valor;
    }

    /**
     * Se valida el stockActual
     */
    private boolean validaStockActual() {
        boolean valor = false;
        //obtieneStockProducto();
        int cantIngreso = Integer.parseInt(txtCantidad.getText().trim());
        ArrayList a2 = new ArrayList();
        ArrayList b = new ArrayList();
        for (int i = 0; i < myArray.size(); i++) {
            a2 = (ArrayList)(myArray.get(i));
            b.add((ArrayList)(a2.clone()));
        }
        ArrayList auxNuevo = (ArrayList)agruparProdPaquete(b).clone();
        log.debug("Se agrupa para Validar Stock");
        log.debug("Prod Actuales para Validar : " + auxNuevo);

        for (int i = 0; i < auxNuevo.size(); i++) {
            String unidad = ((String)((ArrayList)auxNuevo.get(i)).get(9)).trim();
            log.debug(unidad);
            String disponible = ((String)((ArrayList)auxNuevo.get(i)).get(4)).trim();
            log.debug(disponible);


            if ((Integer.parseInt(unidad) * cantIngreso) <= Integer.parseInt(disponible))
                valor = true;
            else
                return false;
        }


        return valor;
    }

    private void AceptarProductosComprometidos() {
        ArrayList arrayProdProm = new ArrayList();
        if (VariablesVentas.accionModificar) {
            VariablesVentas.vCod_Prom = VariablesVentas.vCodProm;
            //log.debug("**COD  *Prom  "+ VariablesVentas.vCodProm);
            borraStock();
            //actualizaStockComprometido(VariablesVentas.vArrayList_Prod_Promociones,ConstantsVentas.INDICADOR_D);
            removeItemArray(VariablesVentas.vArrayList_Promociones, VariablesVentas.vCod_Prom, 0);
            //log.debug("******Prom :"+VariablesVentas.vArrayList_Promociones.size());
            //log.debug("****Prom"+VariablesVentas.vArrayList_Promociones);
            removeItemArray(VariablesVentas.vArrayList_Prod_Promociones, VariablesVentas.vCod_Prom, 18);
            //log.debug("******PrdoProm :"+VariablesVentas.vArrayList_Prod_Promociones.size());
            //log.debug("****PrdoProm"+VariablesVentas.vArrayList_Prod_Promociones);
        }
        //agrego al arreglo principal vArrayList_Promociones
        //ArrayList myArray = new ArrayList();
        log.debug("**********************");
        log.debug("Llenado de Arreglo");
        try {
            // DBVentas.obtieneListadoPaquetes(myArray, VariablesVentas.vCodProm.trim());
            log.debug("myArray.size() " + myArray.size());
            log.debug("myArray  () " + myArray);
            double igvTotal = 0;
            for (int i = 0; i < myArray.size(); i++) {
                ArrayList myArray2 = new ArrayList();
                //
                VariablesVentas.vCod_Prod = ((String)((ArrayList)myArray.get(i)).get(0)).trim();
                VariablesVentas.vDesc_Prod = ((String)((ArrayList)myArray.get(i)).get(1)).trim();
                VariablesVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(i)).get(2)).trim();
                VariablesVentas.vNom_Lab = ((String)((ArrayList)myArray.get(i)).get(3)).trim();
                VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(i)).get(4)).trim();
                VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(i)).get(5)).trim();

                //JCHAVEZ 29102009 inicio
                try {
                    if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("")) {
                        VariablesVentas.vIndAplicaRedondeo = DBVentas.getIndicadorAplicaRedondedo();
                    }

                    if (VariablesVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                        double total1AuxRedondeado =
                            DBVentas.getPrecioRedondeado(Double.parseDouble(VariablesVentas.vVal_Prec_Vta));
                        VariablesVentas.vVal_Prec_Vta =
                                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + total1AuxRedondeado), 3);

                    }

                } catch (SQLException ex) {
                    log.error("", ex);
                }
                //JCHAVEZ 29102009 fin

                VariablesVentas.vTotalPrecVtaProd =
                        FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) * FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada) *
                        Integer.parseInt((String)((ArrayList)myArray.get(i)).get(9));

                VariablesVentas.vVal_Bono = ((String)((ArrayList)myArray.get(i)).get(16)).trim();
                VariablesVentas.vVal_Frac = ((String)((ArrayList)myArray.get(i)).get(7)).trim();
                VariablesVentas.vCant_Ingresada = txtCantidad.getText();
                log.debug(" Promociones VariablesVentas.vCant_Ingresada : " + VariablesVentas.vCant_Ingresada);
                VariablesVentas.vCant_Ingresada =
                        (Integer.parseInt(VariablesVentas.vCant_Ingresada) * Integer.parseInt((String)((ArrayList)myArray.get(i)).get(9))) +
                        "";

                log.debug(" Promociones cantidad unidad: " + (String)(((ArrayList)myArray.get(i)).get(9)));
                log.debug(" Promociones VariablesVentas.vCant_Ingresada : " + VariablesVentas.vCant_Ingresada);


                VariablesVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(i)).get(10)).trim();
                VariablesVentas.vPorc_Igv_Prod = ((String)((ArrayList)myArray.get(i)).get(11)).trim();
                String valIgv =
                    FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) -
                                               (FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta) /
                                                (1 + (FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Igv_Prod) /
                                                      100)))) *
                                              FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada));
                VariablesVentas.vVal_Igv_Prod = valIgv;
                log.debug("Igv de cada uno: " + valIgv);
                igvTotal += FarmaUtility.getDecimalNumber(valIgv.trim());

                VariablesVentas.vNumeroARecargar = "";
                VariablesVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
                VariablesVentas.vTipoProductoVirtual = ((String)((ArrayList)myArray.get(i)).get(14)).trim();
                VariablesVentas.vIndProdControlStock =
                        true; //? FarmaConstants.INDICADOR_S : FarmaConstants.INDICADOR_N);//INDICADOR PROD CONTROLA STOCK
                VariablesVentas.vVal_Prec_Lista_Tmp = ""; //PRECIO DE LISTA ORIGINAL SI ES QUE SE MODIFICO
                //VariablesVentas.vVal_Prec_Pub=((String) ((ArrayList) myArray.get(i)).get(6)).trim();
                /**
         * VAriables descuentos y Precio_Publico
         * @author : dubilluz
         * @since  : 03.07.2007
         */
                VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(i)).get(17)).trim();
                VariablesVentas.vPorc_Dcto_2 = ((String)((ArrayList)myArray.get(i)).get(18)).trim();
                VariablesVentas.vVal_Prec_Pub = ((String)((ArrayList)myArray.get(i)).get(19)).trim();


                VariablesVentas.vAhorroPack = ((String)((ArrayList)myArray.get(i)).get(20)).trim(); //JCHAVEZ 20102009
                VariablesVentas.vInd_Origen_Prod_Prom =
                        ((String)((ArrayList)myArray.get(i)).get(21)).trim(); //JCHAVEZ 20102009
                double totaltemp =
                    Double.parseDouble(VariablesVentas.vCant_Ingresada.trim()) * Double.parseDouble(VariablesVentas.vVal_Prec_Vta.trim());
                // Begin 16-AGO-13, TCT, Add Precio Fijo
                VariablesVentas.vPrecFijo = ((String)((ArrayList)myArray.get(i)).get(22)).trim();
                // End 16-AGO-13, TCT, Add Precio Fijo


                /*ERIOS 10.04.2008 Datos que se agregan al arreglo de prod_promociones,
                           que se guarda en memoria para grabar el detalle.*/
                myArray2.add(VariablesVentas.vCod_Prod);
                myArray2.add(VariablesVentas.vDesc_Prod);
                myArray2.add(VariablesVentas.vUnid_Vta);
                myArray2.add(VariablesVentas.vVal_Prec_Lista);
                myArray2.add(VariablesVentas.vCant_Ingresada); //ingresa en Double
                myArray2.add("" + FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Dcto_1)); //proc_Dcto_1;
                myArray2.add(VariablesVentas.vVal_Prec_Vta);
                myArray2.add(FarmaUtility.formatNumber(totaltemp, 2) + "");
                myArray2.add(VariablesVentas.vVal_Bono); //val_Bono
                myArray2.add(VariablesVentas.vNom_Lab); //9
                myArray2.add(VariablesVentas.vVal_Frac);
                myArray2.add(VariablesVentas.vPorc_Igv_Prod);
                myArray2.add(VariablesVentas.vVal_Igv_Prod); //generado
                myArray2.add(VariablesVentas.vNumeroARecargar); //vacio
                myArray2.add(VariablesVentas.vIndProdVirtual);
                //myArray.add(VariablesVentas.vTipoProductoVirtual);//TIPO DE PRODUCTO VIRTUAL
                //myArray2.add("S");//Indicador de Stock
                myArray2.add(VariablesVentas.vVal_Prec_Lista_Tmp); //vacio
                //myArray2.add(""+FarmaUtility.getDecimalNumber( VariablesVentas.vVal_Prec_Pub));//prec_publico;
                //myArray.add(VariablesVentas.vIndOrigenProdVta); //19
                //myArray2.add("S");//20 indica q esta en una promocion
                myArray2.add("S"); //Indicador de Stock
                myArray2.add(" ");
                myArray2.add(VariablesVentas.vCod_Prom.trim()); //codigo de la promocion donde esta el producto
                myArray2.add("S"); //indica q esta en una promocion
                myArray2.add("" + FarmaUtility.getDecimalNumber(VariablesVentas.vPorc_Dcto_2)); //proc_Dcto_2;
                myArray2.add("" +
                             FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Pub)); //prec_publico; // numero 23
                //myArray2.add("N"); //ind. tratamiento
                //myArray2.add(" ");//23 cantxDia tratamiento
                //myArray2.add(" ");//24 cantxDias tratamiento

                myArray2.add("" +
                             FarmaUtility.getDecimalNumber(VariablesVentas.vAhorroPack)); //JCHAVEZ 20102009 columna 22
                myArray2.add(VariablesVentas.vInd_Origen_Prod_Prom); //JCHAVEZ 20102009 columna 23

                //agregado dubilluz 07.07.2010
                String codProd = ((String)(myArray2.get(0))).trim();
                String cantidadAux = ((String)(myArray2.get(4))).trim();
                String indControlStk = ((String)(myArray2.get(16))).trim();
                VariablesVentas.vVal_Frac = ((String)(myArray2.get(10))).trim();
                VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    !UtilityVentas.operaStkCompProdResp(codProd, //ASOSA, 07.07.2010
                        Integer.parseInt(cantidadAux), ConstantsVentas.INDICADOR_A,
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, Integer.parseInt(cantidadAux), false, this,
                        txtCantidad, "")) {
                    int numero = 1 / 0;
                    break;
                }

                myArray2.add(VariablesVentas.secRespStk); // numero 24
                // Begin 16-AGO-13, TCT, Add Precio Fijo
                myArray2.add(VariablesVentas.vPrecFijo);
                // End 16-AGO-13, TCT, Add Precio Fijo

                //agregado dubilluz 07.07.2010

                Boolean valor = new Boolean(true);
                arrayProdProm.add(myArray2);
            }
            ///agrega areglor de promociones que mete en resumen pedido
            //agregar dubilluz 07.07.2010
            Boolean valor2 = new Boolean(true);
            //llenado de arreglo de promociones
            ArrayList myArrayP = new ArrayList();
            double total = FarmaUtility.getDecimalNumber(VariablesVentas.vVentaTotal);
            double cantidad = FarmaUtility.getDecimalNumber(txtCantidad.getText().trim());
            String pagototal = FarmaUtility.formatNumber(cantidad * total, 2);

            /*ERIOS 10.04.2008 Datos que se agregan al arreglo de promociones,
                           que solo se muestran en el resumen de pedido.*/
            myArrayP.add(VariablesVentas.vCod_Prom);
            myArrayP.add(VariablesVentas.vDesc_Prom);
            myArrayP.add(VariablesVentas.vUnid_Prom); //vacio
            myArrayP.add(FarmaUtility.formatNumber(total, 3));
            myArrayP.add(txtCantidad.getText().trim());
            myArrayP.add(VariablesVentas.vDes_Prom); //vacio
            myArrayP.add(FarmaUtility.formatNumber(total,
                                                   3)); //el precio venta seria el mismo que precio , ya que no ahi descuento
            myArrayP.add("" + pagototal);
            myArrayP.add("0.00");
            myArrayP.add(" "); //9
            myArrayP.add(" ");
            myArrayP.add(" ");
            myArrayP.add(igvTotal + "");
            myArrayP.add(" "); //NUMERO TELEFONICO SI ES RECARGA AUTOMATICA
            myArrayP.add(" "); //INDICADOR DE PRODUCTO VIRTUAL
            myArrayP.add(" "); //TIPO DE PRODUCTO VIRTUAL
            myArrayP.add(" "); //INDICADOR PROD CONTROLA STOCK
            myArrayP.add(" "); //VENTA
            myArrayP.add(" "); //18 myArray.add(VariablesVentas.vVal_Prec_Pub);
            myArrayP.add(" "); //19 myArray.add(VariablesVentas.vIndOrigenProdVta);
            myArrayP.add("S"); //20 es promocion
            myArrayP.add("0"); //21 dscto 2
            myArrayP.add("N"); //22 ind. tratamiento
            myArrayP.add(" "); //23 cantxDia tratamiento
            myArrayP.add(" "); //24 cantxDias tratamiento
            // VariablesVentas.vVentaTotal
            //Boolean valor2 = new Boolean(true);
            FarmaUtility.operaListaProd(VariablesVentas.vArrayList_Promociones_temporal, myArrayP, valor2, 0);

            if (VariablesVentas.vArrayList_Promociones.size() > 0) {
                log.debug("PROMOCIONES ACTUAL---->" + VariablesVentas.vCod_Prom + "  N°PROMOCIONES " +
                          VariablesVentas.vArrayList_Promociones.size());
            }

            // fin
            ArrayList aux = new ArrayList();

            log.debug("<<TCT 100>> Inicio de Carga Promociones a Temporal..");
            for (int a = 0; a < arrayProdProm.size(); a++) {
                aux = (ArrayList)(arrayProdProm.get(a));
                /*
                log.debug("VariablesVentas.vArrayList_Prod_Promociones" +
                                   VariablesVentas.vArrayList_Prod_Promociones);
                 */
                FarmaUtility.operaListaProd(VariablesVentas.vArrayList_Prod_Promociones_temporal,
                                            (ArrayList)(aux.clone()), valor2, 0);
            }
            log.debug("<<TCT 100>> Promociones Cargadas a Temporal=>VariablesVentas.vArrayList_Prod_Promociones_temporal:" +
                      VariablesVentas.vArrayList_Prod_Promociones_temporal);
            myArray = new ArrayList();
            arrayProdProm = new ArrayList();
            if (VariablesVentas.accionModificar)
                aceptaPromocion();

            FarmaUtility.aceptarTransaccion();
            log.info("lista prod paquete:" + VariablesVentas.vArrayList_Prod_Promociones_temporal);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al comprometer los productos del PACK", txtCantidad);
            log.error("", e);
        }
    }

    /**
     * Coloca cantidad sise modificara la cantidad de Promocion
     * @author : dubilluz
     * @since  : 25.06.2007
     */
    private void obtieneCantidad() {
        if (VariablesVentas.accionModificar) {
            txtCantidad.setText(VariablesVentas.vCantidad);
        }
    }

    private void limpiaValores() {
        VariablesVentas.vCodProm = "";
        //VariablesVentas.vCodProdFiltro="";
        VariablesVentas.vCantidad = "";
        VariablesVentas.accionModificar = false;
        /**
    * SETEO DE VARIABLES
    * @author : dubilluz
    * @since  : 03.07.2007s
    */
        VariablesVentas.vPorc_Dcto_1 = "";
        VariablesVentas.vPorc_Dcto_2 = "";
        VariablesVentas.vVal_Prec_Pub = "";
        //VariablesVentas.vCodProm = "";
        //VariablesVentas.vDesc_Prom = "";

    }

    /**
     * Elimina elementos del Array
     * @author : dubilluz
     * @since  : 25.06.2007
     */
    private void removeItemArray(ArrayList array, String codProm, int pos) {
        String cod = "";
        codProm = codProm.trim();
        for (int i = 0; i < array.size(); i++) {
            cod = ((String)((ArrayList)array.get(i)).get(pos)).trim();
            log.debug(cod + "<<<<<" + codProm);
            if (cod.equalsIgnoreCase(codProm)) {
                array.remove(i);
                i = -1;
            }
        }
    }

    /**
     * Actualiza el Stock
     * @author dubilluz
     * @since  27.06.2007
     */
    private void borraStock() {
        ArrayList prod_Prom = new ArrayList();
        prod_Prom = detalle_Prom(VariablesVentas.vArrayList_Prod_Promociones, VariablesVentas.vCodProm);
        //prod_Prom = agrupar(prod_Prom); ya no porque ahora se comprometera por producto independientemente y ya no agrupado como antes
        log.debug("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD secRespaldo: " + prod_Prom);
        ArrayList aux = new ArrayList();
        String codProm = "";
        String codProd = "";
        String cantidad = ""; //((String)(tblProductos.getValueAt(filaActual,4))).trim();
        String indControlStk = ""; // ((String)(tblProductos.getValueAt(filaActual,16))).trim();
        String secRespaldo = ""; //ASOSA, 07.07.2010
        for (int j = 0; j < prod_Prom.size(); j++) {
            aux = (ArrayList)(prod_Prom.get(j));
            codProd = ((String)(aux.get(0))).trim();
            VariablesVentas.vVal_Frac = ((String)(aux.get(10))).trim();
            cantidad = ((String)(aux.get(4))).trim();
            indControlStk = ((String)(aux.get(16))).trim();
            secRespaldo = ((String)(aux.get(24))).trim(); //ASOSA, 07.07.2010
            VariablesVentas.secRespStk = ""; //ASOSA, 26.08.2010
            if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                /*!UtilityVentas.actualizaStkComprometidoProd(codProd, //Antes, ASOSA, 07.07.2010
                                                       Integer.parseInt(cantidad),
                                                       ConstantsVentas.INDICADOR_D,
                                                       ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR,
                                                       Integer.parseInt(cantidad),
                                                       true,
                                                       this,
                                                       txtCantidad))*/
                !UtilityVentas.operaStkCompProdResp(codProd, //ASOSA, 01.07.2010
                    0, ConstantsVentas.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 0, true, this,
                    txtCantidad, secRespaldo))
                return;
        }
        FarmaUtility.aceptarTransaccion();
    }

    /**
     * Retorna el detalle de una promocion
     * @author dubilluz
     * @since  03.07.2007
     */
    private ArrayList detalle_Prom(ArrayList array, String codProm) {
        ArrayList nuevo = new ArrayList();
        ArrayList aux = new ArrayList();
        String cod_p = "";
        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
            aux = (ArrayList)(VariablesVentas.vArrayList_Prod_Promociones.get(i));
            cod_p = (String)(aux.get(18));
            if (cod_p.equalsIgnoreCase(codProm)) {
                nuevo.add((ArrayList)(aux.clone()));
            }
        }
        return nuevo;
    }

    /**
     * Verifica el producto si esta comprado
     * @author : dubilluz
     * @since  : 26.06.2007
     */
    private boolean verificaProducto() {
        ArrayList aux = new ArrayList();
        ArrayList aux2 = new ArrayList();
        for (int i = 0; i < VariablesVentas.vArrayList_PedidoVenta.size(); i++) {
            aux = (ArrayList)(VariablesVentas.vArrayList_PedidoVenta.get(i));
            log.debug("Entro");
            for (int j = 0; j < myArray.size(); j++) {
                aux2 = (ArrayList)(myArray.get(j));
                if ((((String)(aux.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                    return false;
                }
                log.debug("En check" + (String)(aux.get(0)));
                log.debug("En detalle" + (String)(aux2.get(0)));
            }
        }

        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones.size(); i++) {
            aux = (ArrayList)(VariablesVentas.vArrayList_Prod_Promociones.get(i));

            for (int j = 0; j < myArray.size(); j++) {
                aux2 = (ArrayList)(myArray.get(j));
                if ((((String)(aux.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                    return false;
                }
            }
        }

        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size(); i++) {
            aux = (ArrayList)(VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i));

            for (int j = 0; j < myArray.size(); j++) {
                aux2 = (ArrayList)(myArray.get(j));
                if ((((String)(aux.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Agrupa productos que esten en ambos paquetes
     * retorna el nuevoa arreglo
     * @author : dubilluz
     * @author : 27.06.2007
     */
    private ArrayList agrupar(ArrayList array) {
        ArrayList nuevo = new ArrayList();
        ArrayList aux1 = new ArrayList();
        ArrayList aux2 = new ArrayList();
        int cantidad1 = 0;
        int cantidad2 = 0;
        int suma = 0;
        log.debug("****************************JCORTEZ **********AGRUPACION" + array);

        for (int i = 0; i < array.size(); i++) {
            aux1 = (ArrayList)(array.get(i));
            //if(aux1.size()>0){//(((String)(aux1.get(19))).trim()).equalsIgnoreCase("Revisado")){//JCHAVEZ DECIA <23 Y CAMBIE a >0
            if (aux1.size() < 25) { // JCORTEZ DICE POR NUEVO CAMPOS DE JCHAVEZ (23+2=25)
                for (int j = i + 1; j < array.size(); j++) {
                    aux2 = (ArrayList)(array.get(j));
                    //if(aux2.size()>0){               //JCHAVEZ DECIA <23 Y CAMBIE a >0
                    if (aux2.size() < 25) { //JCORTEZ DICE POR NUEVO CAMPOS DE JCHAVEZ (23+2=25)
                        if ((((String)(aux1.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                            cantidad1 = Integer.parseInt(((String)(aux1.get(4))).trim());
                            cantidad2 = Integer.parseInt(((String)(aux2.get(4))).trim());
                            suma = cantidad1 + cantidad2;
                            aux1.set(4, suma + "");
                            ((ArrayList)(array.get(j))).add("Revisado");
                        }
                    }
                }
                nuevo.add(aux1);
            }
        }
        log.debug("Agrupado :" + nuevo.size());
        log.debug("Aggrup Elment :" + nuevo);
        return nuevo;
    }

    /**
     * Acepta Modificacion de promocion
     * @author : dubilluz
     * @since  : 04.07.2007
     */
    private void aceptaPromocion() {

        for (int i = 0; i < VariablesVentas.vArrayList_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Promociones_temporal = new ArrayList();

        for (int i = 0; i < VariablesVentas.vArrayList_Prod_Promociones_temporal.size(); i++)
            VariablesVentas.vArrayList_Prod_Promociones.add((ArrayList)((ArrayList)VariablesVentas.vArrayList_Prod_Promociones_temporal.get(i)).clone());

        VariablesVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
        //log.debug(VariablesVentas.vArrayList_ResumenPedido.size());
    }

    /**
     * Agrupa productos del Paquete
     * retorna el nuevoa arreglo
     * @author : dubilluz
     * @author : 27.06.2007
     */
    private ArrayList agruparProdPaquete(ArrayList array) {
        log.debug("para unir +" + array);
        ArrayList nuevo = new ArrayList();
        ArrayList aux1 = new ArrayList();
        ArrayList aux2 = new ArrayList();
        int cantidad1 = 0;
        int cantidad2 = 0;
        int suma = 0;
        for (int i = 0; i < array.size(); i++) {
            aux1 = (ArrayList)(array.get(i));
            log.debug("Tamaño " + i + " " + aux1.size());
            if (aux1.size() <
                24) { //(((String)(aux1.get(19))).trim()).equalsIgnoreCase("Revisado")){//JCHAVEZ 20102009 DECIA <21
                for (int j = i + 1; j < array.size(); j++) {
                    aux2 = (ArrayList)(array.get(j));
                    if (aux2.size() < 24) { //JCHAVEZ 20102009 DECIA <21
                        if ((((String)(aux1.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                            cantidad1 = Integer.parseInt(((String)(aux1.get(9))).trim());
                            cantidad2 = Integer.parseInt(((String)(aux2.get(9))).trim());
                            suma = cantidad1 + cantidad2;
                            aux1.set(9, suma + "");
                            ((ArrayList)(array.get(j))).add("Revisado");
                        }
                    }
                }
                nuevo.add(aux1);
            }
        }
        return nuevo;
    }


    private void cargarConf(boolean valor) {
        txtDescPromocion.setLineWrap(true);
        txtDescPromocion.setWrapStyleWord(true);
        txtDescPromocion.setText(VariablesVentas.vDescProm);
        pnlStock.setVisible(valor);
        pnlStock1.setVisible(valor);
        jScrollPane1.setVisible(valor);
        jScrollPane2.setVisible(valor);
    }


}
