package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoCantidad.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      28.12.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgIngresoCantidad extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidad.class);

    private int cantInic = 0;
    // Bjct, 27-DIC-12, si precio es cero no Vender
    private boolean vbPrecProdOk = true;
    // Bjct, 27-DIC-12


    /** Objeto Frame de la Aplicación */
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    JPanel pnlStock = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    JLabel lblUnidades = new JLabel();
    JLabel lblStock = new JLabel();
    JLabel lblFechaHora = new JLabel();
    JLabel lblStockTexto = new JLabel();
    JPanel pnlDetalleProducto = new JPanel();
    XYLayout xYLayout1 = new XYLayout();
    JTextField txtPrecioVenta = new JTextField();
    JLabel lblUnidadT = new JLabel();
    JLabel lblDescripcionT = new JLabel();
    JLabel lblCodigoT = new JLabel();
    JLabel lblLaboratorio = new JLabel();
    JLabel lblDcto = new JLabel();
    JLabel lblLaboratorioT = new JLabel();
    JLabel lblDscto = new JLabel();
    public JTextField txtCantidad = new JTextField();
    JLabel lblUnidad = new JLabel();
    JLabel lblDescripcion = new JLabel();
    JLabel lblCodigo = new JLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JButtonLabel btnPrecioVta = new JButtonLabel();
    private JLabelOrange lblPrecVtaConv = new JLabelOrange();
    private JLabelOrange T_lblPrecVtaConv = new JLabelOrange();
    private JLabelOrange lblProdCupon = new JLabelOrange();
    private JLabelOrange lblPrecioProdCamp = new JLabelOrange();

    private double pPrecioFidelizacion = 0.0;
    private JLabel jLabel1 = new JLabel();
    private JLabel lblMensajeCampaña = new JLabel();
    private JTextField txtPrecioVentaRedondeado = new JTextField();

    private String tipoProducto;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */
    public DlgIngresoCantidad() {
        this(null, "", false);
    }

    /**
     *Constructor
     *@param parent Objeto Frame de la Aplicación.
     *@param title Título de la Ventana.
     *@param modal Tipo de Ventana.
     */
    public DlgIngresoCantidad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(530, 398));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Ingreso de Cantidad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(360, 331));
        jContentPane.setBackground(Color.white);
        pnlStock.setBounds(new Rectangle(15, 20, 500, 55));
        pnlStock.setFont(new Font("SansSerif", 0, 11));
        pnlStock.setBackground(new Color(255, 130, 14));
        pnlStock.setLayout(xYLayout2);
        pnlStock.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlStock.setForeground(Color.white);
        lblUnidades.setText("unidades");
        lblUnidades.setFont(new Font("SansSerif", 1, 14));
        lblUnidades.setForeground(Color.white);
        lblStock.setText("10");
        lblStock.setFont(new Font("SansSerif", 1, 15));
        lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStock.setForeground(Color.white);
        lblFechaHora.setText("12/01/2006 09:20:34");
        lblFechaHora.setFont(new Font("SansSerif", 0, 12));
        lblFechaHora.setForeground(Color.white);
        lblStockTexto.setText("Stock del Producto al");
        lblStockTexto.setFont(new Font("SansSerif", 0, 12));
        lblStockTexto.setForeground(Color.white);
        pnlDetalleProducto.setBounds(new Rectangle(15, 80, 500, 250));
        pnlDetalleProducto.setLayout(xYLayout1);
        pnlDetalleProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDetalleProducto.setFont(new Font("SansSerif", 0, 11));
        pnlDetalleProducto.setBackground(Color.white);
        txtPrecioVenta.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioVenta.setFont(new Font("SansSerif", 1, 11));
        txtPrecioVenta.setEnabled(false);
        txtPrecioVenta.setText("13.20");
        txtPrecioVenta.setVisible(false);
        txtPrecioVenta.setText("0");
        txtPrecioVenta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPrecioVenta_keyPressed(e);
            }
        });
        lblUnidadT.setText("Unidad");
        lblUnidadT.setFont(new Font("SansSerif", 1, 11));
        lblDescripcionT.setText("Descripcion");
        lblDescripcionT.setFont(new Font("SansSerif", 1, 11));
        lblCodigoT.setText("Codigo");
        lblCodigoT.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorio.setText("COLLIERE S.A.");
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblDcto.setText("10.00");
        lblDcto.setHorizontalAlignment(SwingConstants.LEFT);
        lblDcto.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorioT.setText("Laboratorio :");
        lblLaboratorioT.setFont(new Font("SansSerif", 1, 11));
        lblDscto.setText("% Dcto. :");
        lblDscto.setFont(new Font("SansSerif", 1, 11));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
        txtCantidad.setDocument(new FarmaLengthText(6));
        txtCantidad.setText("0");
        txtCantidad.setFont(new Font("SansSerif", 1, 11));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        txtCantidad.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtCantidad_focusLost(e);
            }
        });
        lblUnidad.setText(" ");
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setText(" ");
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setText(" ");
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblF11.setText("[ ENTER ] Aceptar");
        lblF11.setBounds(new Rectangle(110, 340, 135, 20));
        lblF11.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    lblF11_keyPressed(e);
                }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(255, 340, 85, 20));
        btnCantidad.setText("Cantidad :");
        btnCantidad.setForeground(Color.black);
        btnCantidad.setMnemonic('c');
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
        btnPrecioVta.setText("Precio Venta : S/.");
        btnPrecioVta.setForeground(Color.black);
        btnPrecioVta.setMnemonic('p');
        btnPrecioVta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPrecioVta_actionPerformed(e);
            }
        });
        lblPrecVtaConv.setForeground(Color.black);
        T_lblPrecVtaConv.setText("P. Vta. Conv.:");
        T_lblPrecVtaConv.setForeground(Color.black);
        lblPrecioProdCamp.setForeground(Color.red);
        lblPrecioProdCamp.setFont(new Font("SansSerif", 1, 15));
        jLabel1.setText("jLabel1");
        lblMensajeCampaña.setVisible(false);
        lblMensajeCampaña.setForeground(Color.red);
        lblMensajeCampaña.setFont(new Font("Dialog", 1, 13));
        lblMensajeCampaña.setText("     Producto se encuentra en campaña  \" Acumula tu Compra y Gana !\"");
        txtPrecioVentaRedondeado.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioVentaRedondeado.setFont(new Font("SansSerif", 1, 11));
        txtPrecioVentaRedondeado.setEnabled(false);
        txtPrecioVentaRedondeado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPrecioVenta_keyPressed(e);
            }
        });
        txtPrecioVentaRedondeado.setVisible(true);
        pnlStock.add(lblUnidades, new XYConstraints(230, 10, 75, 20));
        pnlStock.add(lblStock, new XYConstraints(140, 5, 80, 30));
        pnlStock.add(lblFechaHora, new XYConstraints(5, 20, 130, 20));
        pnlStock.add(lblStockTexto, new XYConstraints(5, 0, 125, 20));
        pnlDetalleProducto.add(lblPrecioProdCamp, new XYConstraints(179, 124, 305, 30));
        pnlDetalleProducto.add(lblProdCupon, new XYConstraints(199, 189, 285, 15));
        pnlDetalleProducto.add(T_lblPrecVtaConv, new XYConstraints(9, 184, 80, 15));
        pnlDetalleProducto.add(lblPrecVtaConv, new XYConstraints(99, 184, 80, 15));


        //Agregado Por FRAMIREZ 11.01.2012 oculta el precio de una venta normal
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {
            btnPrecioVta.setVisible(false);
        } else {
            btnPrecioVta.setVisible(true);
            pnlDetalleProducto.add(btnPrecioVta, new XYConstraints(9, 159, 100, 20));
        }

        pnlDetalleProducto.add(btnCantidad, new XYConstraints(184, 159, 60, 20));
        pnlDetalleProducto.add(lblUnidadT, new XYConstraints(354, 55, 50, 20));
        pnlDetalleProducto.add(lblDescripcionT, new XYConstraints(10, 55, 95, 20));
        pnlDetalleProducto.add(lblCodigoT, new XYConstraints(10, 10, 55, 20));
        pnlDetalleProducto.add(lblLaboratorio, new XYConstraints(89, 99, 280, 20));
        pnlDetalleProducto.add(lblDcto, new XYConstraints(59, 129, 40, 20));
        pnlDetalleProducto.add(lblLaboratorioT, new XYConstraints(10, 100, 80, 20));
        pnlDetalleProducto.add(lblDscto, new XYConstraints(9, 129, 50, 20));
        pnlDetalleProducto.add(txtCantidad, new XYConstraints(249, 159, 50, 20));
        pnlDetalleProducto.add(lblUnidad, new XYConstraints(354, 74, 135, 20));
        pnlDetalleProducto.add(lblDescripcion, new XYConstraints(10, 75, 270, 20));
        pnlDetalleProducto.add(lblCodigo, new XYConstraints(10, 30, 55, 20));
        pnlDetalleProducto.add(lblMensajeCampaña, new XYConstraints(-1, 224, 500, 25));


        log.debug("¿VariablesConvenioBTLMF.vCodConvenio?" + VariablesConvenioBTLMF.vCodConvenio);


        //Agregado Por FRAMIREZ 11.01.2012 oculta el precio de una venta normal
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

            //if(VariablesVentas.vEstadoProdConvenio.equals("I"))

            txtPrecioVentaRedondeado.setVisible(false);
            txtPrecioVenta.setVisible(false);
            T_lblPrecVtaConv.setVisible(true);

        } else {
            T_lblPrecVtaConv.setVisible(false);
            pnlDetalleProducto.add(txtPrecioVentaRedondeado, new XYConstraints(109, 159, 60, 20));
            pnlDetalleProducto.add(txtPrecioVenta, new XYConstraints(109, 134, 60, 20));


        }


        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlStock, null);
        jContentPane.add(pnlDetalleProducto, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        try {
            //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
            if (VariablesVentas.vEsPedidoConvenio) {
                DBVentas.obtieneInfoDetalleProducto(pArrayList, VariablesVentas.vCod_Prod);
            } else {
                DBVentas.obtieneInfoDetalleProductoVta(pArrayList, VariablesVentas.vCod_Prod);
            }

        } catch (SQLException sql) {
            log.error(null, sql.fillInStackTrace());
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto. \n" +
                    sql.getMessage(), txtCantidad);
        }
    }

    private void muestraInfoDetalleProd() {
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesVentas.vStk_Prod_Fecha_Actual = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            //DUBILLUZ 24.07.2014
            if (( /* !VariablesVentas.vEsPedidoDelivery &&  */!VariablesVentas.vEsPedidoInstitucional) ||
                !VariablesVentas.vIngresaCant_ResumenPed) {

                //JCORTEZ 11/04/08 no se actualiza el precio y descuento si es producto  oferta
                //if(!VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)||!VariablesVentas.vEsProdOferta)

                // Segun gerencia se debe seguir la misma logica para todos los productos.
                if (VariablesVentas.vVentanaListadoProductos) {
                    log.debug("SETEANDO DESCUENTO");
                    /*
                    VariablesVentas.vVal_Prec_Vta =
                            ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                    */

                    double precio =
                        FarmaUtility.getDecimalNumber(((String)((ArrayList)myArray.get(0)).get(3)).trim()) *
                        FarmaUtility.getDecimalNumber(VariablesVentas.vValorMultiplicacion); //ASOSA - 12/08/2014
                    VariablesVentas.vVal_Prec_Vta = "" + precio; //ASOSA - 12/08/2014


                    VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();

                } else {
                    if (UtilityVentas.isAplicoPrecioCampanaCupon(lblCodigo.getText().trim(),
                                                                 FarmaConstants.INDICADOR_S)) {
                        if (!VariablesVentas.vVentanaOferta) {
                            log.debug("SETEANDO DESCUENTO");
                            /*
                            VariablesVentas.vVal_Prec_Vta =
                                    ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                            */

                            double precio =
                                FarmaUtility.getDecimalNumber(((String)((ArrayList)myArray.get(0)).get(3)).trim()) *
                                FarmaUtility.getDecimalNumber(VariablesVentas.vValorMultiplicacion); //ASOSA - 12/08/2014
                            VariablesVentas.vVal_Prec_Vta = "" + precio; //ASOSA - 12/08/2014


                            VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
                        }
                    }
                }


                log.info("DlgIngresoCantidad: VariablesVentas.vPorc_Dcto_1 - " + VariablesVentas.vPorc_Dcto_1);
                log.debug("VariablesVentas.vPorc_Dcto_2 : " + VariablesVentas.vPorc_Dcto_2);
            }
            VariablesVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesVentas.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();

            setearValoresAdicionales();

        } else {
            VariablesVentas.vStk_Prod = "0";
            VariablesVentas.vDesc_Acc_Terap = "";
            VariablesVentas.vStk_Prod_Fecha_Actual = "";
            VariablesVentas.vVal_Prec_Vta = "";
            VariablesVentas.vUnid_Vta = "";
            VariablesVentas.vPorc_Dcto_1 = "";
            VariablesVentas.vVal_Prec_Lista = "";
            VariablesVentas.vNom_Lab = "";
            VariablesVentas.vDesc_Prod = "";
            VariablesVentas.vCod_Prod = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", null);
            cerrarVentana(false);
        }


        lblFechaHora.setText(VariablesVentas.vStk_Prod_Fecha_Actual);

        if (!VariablesVentas.existenValoresAdicionales) { //ASOSA - 12/08/2014
            lblStock.setText("" +
                             (Integer.parseInt(VariablesVentas.vStk_Prod) / Integer.parseInt(VariablesVentas.vValorMultiplicacion) +
                              cantInic));
        } else {
            lblStock.setText("" + (Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic));
        }

        //lblStock.setText(VariablesVentas.vStk_Prod);
        lblCodigo.setText(VariablesVentas.vCod_Prod);
        lblDescripcion.setText(VariablesVentas.vDesc_Prod);
        lblLaboratorio.setText(VariablesVentas.vNom_Lab);
        lblUnidad.setText(VariablesVentas.vUnid_Vta);
        txtPrecioVenta.setText(VariablesVentas.vVal_Prec_Vta); //JCHAVEZ 29102009 se cambio txtPrecioVenta por txtPrecioVentaOculto
        lblDcto.setText(VariablesVentas.vPorc_Dcto_1);
        txtCantidad.setText("" + cantInic);
        //JCHAVEZ 29102009 inicio
        try {
            //double precVtaRedondeadoNum = DBVentas.getPrecioRedondeado(Double.parseDouble(VariablesVentas.vVal_Prec_Vta));  antes

            // Bjct, 27-DIC-12, setar flag de precio correcto
            String vsPrecMinVta = DBVentas.getPrecioMinimoVta();
            if (vsPrecMinVta.equalsIgnoreCase("N")) {
                FarmaUtility.showMessage(this, "Error, No se puede Leer el Valor Mínimo de Venta...", txtCantidad);
                return;
            }
            double vdPrecOrigonal = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
            double vdPrecMinVta = Double.valueOf(vsPrecMinVta).doubleValue();

            if (vdPrecOrigonal < vdPrecMinVta) {
                vbPrecProdOk = false;
            }
            // Ejct, 27-DIC-12

            double precVtaRedondeadoNum =
                DBVentas.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta)); //ASOSA, 18.06.2010

            String precVtaRedondeadoStr =
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaRedondeadoNum), 3);
            if (!VariablesConvenio.vVal_Prec_Vta_Conv.trim().equalsIgnoreCase("")) {
                log.debug("VariablesConvenio.vVal_Prec_Vta_Conv: " + VariablesConvenio.vVal_Prec_Vta_Conv);
                double precVtaConvRedondeadoNum =
                    DBVentas.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesConvenio.vVal_Prec_Vta_Conv));
                String precVtaConvRedondeadoStr =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaConvRedondeadoNum), 3);
                lblPrecVtaConv.setText(precVtaConvRedondeadoStr); //JCHAVEZ 29102009 se cambio VariablesConvenio.vVal_Prec_Vta_Conv por precVtaConvRedondeadoStr

            }
            this.txtPrecioVentaRedondeado.setText(precVtaRedondeadoStr);
        } catch (SQLException sql) {
            log.error("", sql);
        }
        //JCHAVEZ 29102009 fin

        if (!VariablesVentas.vEsPedidoConvenio ||
            VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) {
            T_lblPrecVtaConv.setVisible(false);
            lblPrecVtaConv.setVisible(false);
        }


    }

    /**
     * Setea los valores adicionales en las variables si es que existen.
     * La primera vez entrara el producto porque la variable de codigo de barras tiene datos, las demas veces ya no
     * @author ASOSA
     * @since 06/08/2014
     */
    public void setearValoresAdicionales() {

        String[] list = UtilityVentas.obtenerArrayValoresBd(ConstantsVentas.TIPO_VAL_ADIC_COD_BARRA);
        String new_frac = "";
        String new_desc = "";
        String new_precio = "";
        String stockLocal = "";
        String fracProdLocal = "";
        if (!list[0].equals("THERE ISNT")) {
            new_frac = list[0];
            new_desc = list[1];
            new_precio = list[2];
            stockLocal = list[3];
            fracProdLocal = list[4];

            VariablesVentas.vStk_Prod = "" + (Integer.parseInt(stockLocal) / Integer.parseInt(new_frac));

            //Double valMult = 1.00 / Integer.parseInt(new_frac);
            VariablesVentas.vValorMultiplicacion = new_frac.trim();
            //VariablesVentas.vVal_Frac = "" + valMult;
            VariablesVentas.vDesc_Prod = VariablesVentas.vDesc_Prod + " " + new_desc;
            VariablesVentas.vVal_Prec_Vta = new_precio.trim();

            VariablesVentas.existenValoresAdicionales = true;
        } else {
            //VariablesVentas.vValorMultiplicacion = "1";
            VariablesVentas.existenValoresAdicionales = false;
        }
        VariablesVentas.vCodigoBarra = "";
        log.info("VariablesVentas.vCodigoBarra 09: " + VariablesVentas.vCodigoBarra);
        log.info("VariablesVentas.vVal_Frac" + VariablesVentas.vVal_Frac);
        log.info("VariablesVentas.vUnid_Vta" + VariablesVentas.vUnid_Vta);
        log.info("VariablesVentas.vVal_Prec_Vta" + VariablesVentas.vVal_Prec_Vta);
        log.info("VariablesVentas.vStk_Prod" + VariablesVentas.vStk_Prod);

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtPrecioVenta_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantidad);
        }

    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Bjct, 27-DIC-12, si producto tiene precio inferior al minimo then error
            if (!vbPrecProdOk) {
                FarmaUtility.showMessage(this,
                                         "No se puede Vender un Producto con Precio inferior al Mínimo Vigente...",
                                         txtCantidad);
                return;
            }
            // Ejct, 27-DIC-12, si producto tiene precio inferior al minimo then error
            aceptaCantidadIngresada();
        }

    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
        log.debug("VariablesVentas.vVal_Prec_Vta;" + VariablesVentas.vVal_Prec_Vta);
        FarmaUtility.centrarVentana(this);
        this.setLocation(this.getX(), this.getY() - 75);
        VariablesVentas.vCant_Ingresada_Temp = "0";
        cantInic = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada_Temp));

        //segun sea el flg nuevo convenios BTL cargara el detalle
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

            muestraInfoDetalleProd_Btl();

        } else {
            muestraInfoDetalleProd();
        }
        evaluaTipoPedido();
        //JCORTEZ 17.04.08
        lblDscto.setVisible(false);
        lblDcto.setVisible(false);

        /*if(!VariablesVentas.vEsPedidoDelivery && !VariablesVentas.vEsPedidoInstitucional)
        FarmaUtility.moveFocus(txtCantidad);
    else
        FarmaUtility.moveFocus(txtPrecioVenta);        */
        muestraMaxProdCupon();
        calculoNuevoPrecio();


        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

        } else {
            ///---
            if (isExisteProdCampana(VariablesVentas.vCod_Prod)) {
                lblMensajeCampaña.setVisible(true);
            } else
                lblMensajeCampaña.setVisible(false);
            ///---
        }
        FarmaUtility.moveFocus(txtCantidad);


        //if (VariablesPtoVenta.vIndTico.equals("S") && SE COMENTO X MIENTRAS PORQUE QUIEREN YAYAYAYA QUE SEA TICO Y SE COMPORTE COMO FARMA - ASOSA 23/10/2014
        //  !VariablesVentas.tipoLlamada.equals("1")) { //ASOSA - 09/10/2014
        if (false) {
            txtCantidad.setText("1");
            lblF11_keyPressed(null);
            VariablesVentas.tipoLlamada = "0";
        }

        //INI ASOSA - 06/01/2014 - RIMAC
        if (VariablesVentas.vCantMesRimac > 0 &&
            !VariablesVentas.vDniRimac.equals("")){
            txtCantidad.setText("" + VariablesVentas.vCantMesRimac);
            if (!vbPrecProdOk) {
                FarmaUtility.showMessage(this,
                                         "No se puede Vender un Producto con Precio inferior al Mínimo Vigente...",
                                         txtCantidad);
                return;
            }            
            aceptaCantidadIngresada();
        }
        //FIN ASOSA - 06/01/2014 - RIMAC
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnPrecioVta_actionPerformed(ActionEvent e) {
        if (VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional)
            FarmaUtility.moveFocus(txtPrecioVenta);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private boolean validaCantidadIngreso() {
        boolean valor = false;
        String cantIngreso = txtCantidad.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso) && Integer.parseInt(cantIngreso) > 0)
            valor = true;
        return valor;
    }

    private boolean validaPrecioIngreso() {
        boolean valor = false;
        String precioIngreso = txtPrecioVenta.getText().trim();
        if (FarmaUtility.isDouble(precioIngreso) && FarmaUtility.getDecimalNumber(precioIngreso) > 0)
            valor = true;
        return valor;
    }

    private boolean validaStockActual() {
        boolean valor = false;
        //if (!VariablesVentas.existenValoresAdicionales) {   //ASOSA - 08/08/2014
        obtieneStockProducto();
        //}
        VariablesVentas.vStk_Prod =
                "" + Integer.parseInt(VariablesVentas.vStk_Prod) / Integer.parseInt(VariablesVentas.vValorMultiplicacion);

        String cantIngreso = txtCantidad.getText().trim();

        if ((Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic) >= Integer.parseInt(cantIngreso))
            valor = true;
        //kmoncada valida si es producto virtual (balanza keito)
        if (!valor && !VariablesVentas.vIndProdControlStock) {
            valor = true;
        }

        return valor;
    }

    private void aceptaCantidadIngresada() {
        VariablesVentas.vIndAplicaPrecNuevoCampanaCupon = FarmaConstants.INDICADOR_N;
        boolean pAactivoVtaNegativa = false;


        if (!validaCantidadIngreso()) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.", txtCantidad);
            return;
        }
        if (!validaStockActual()) {
            FarmaUtility.liberarTransaccion();

            //INI ASOSA - 03/10/2014 - PANHD
            tipoProducto = VariablesVentas.tipoProducto; //ASOSA - 09/10/2014
            if (tipoProducto.trim().equals(ConstantsVentas.TIPO_PROD_FINAL)) {
                int cantidad = (int)Integer.parseInt(txtCantidad.getText().trim());
                if (!UtilityVentas.existsStockComp(VariablesVentas.vCod_Prod, cantidad)) {
                    FarmaUtility.showMessage(this, "No hay stock suficiente para vender el producto", txtCantidad);
                    lblStock.setText("" + (Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic));
                    return;
                } else {
                    pAactivoVtaNegativa = true;
                }
            } else {
                //FIN ASOSA - 03/10/2014 - PANHD
                if (!UtilityVentas.permiteVtaNegativa(myParentFrame, this, VariablesVentas.vCod_Prod,
                                                      txtCantidad.getText().trim(), VariablesVentas.vVal_Frac)) {
                    FarmaUtility.showMessage(this, "La cantidad ingresada no puede ser mayor al Stock del Producto.",
                                             txtCantidad);
                    lblStock.setText("" + (Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic));
                    return;
                } else {
                    pAactivoVtaNegativa = true;
                }
            }

        }


        VariablesVentas.vCant_Ingresada = txtCantidad.getText().trim();
        if ((VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional) && !validaPrecioIngreso()) {
            FarmaUtility.showMessage(this, "Ingrese un precio correcto.", txtPrecioVenta);
            return;
        }
        //VariablesVentas.vVal_Prec_Vta = txtPrecioVenta.getText().trim();

        VariablesVentas.vVal_Prec_Vta = getAnalizaPrecio(txtPrecioVenta.getText().trim(), pPrecioFidelizacion);


        if (VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional)
            calculoNuevoDescuento();

        /**Nueva Modificación
     * @Autor: Luis Reque Orellana
     * @Fecha: 16-03-2007
     */
        /**---INCIO----*/
        String indControlPrecio = "";
        //VariablesConvenio.vCodConvenio = "0000000001";
        if (!UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)) {

            if (!VariablesConvenio.vCodConvenio.equalsIgnoreCase("") &&
                !VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)) //Se ha seleccionado un convenio
            {
                /*try{
        indControlPrecio = DBConvenio.obtieneIndPrecioControl(VariablesVentas.vCod_Prod);
      }catch(SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Error al obtener indicador de control de precio.",txtCantidad);
      }*/
                /* 23.01.2007 ERIOS La validacion de realiza por las listas de exclusion */
                //if(indControlPrecio.equals(FarmaConstants.INDICADOR_N))
                if (true) {
                    try {
                        VariablesVentas.vVal_Prec_Vta =
                                DBConvenio.obtieneNvoPrecioConvenio(VariablesConvenio.vCodConvenio,
                                                                    VariablesVentas.vCod_Prod,
                                                                    txtPrecioVenta.getText());

                        VariablesVentas.vIndAplicaPrecNuevoCampanaCupon = FarmaConstants.INDICADOR_N;

                        log.debug("vVal_Prec_Vta: " + VariablesVentas.vVal_Prec_Vta);
                    } catch (SQLException sql) {
                        log.error("", sql);
                        FarmaUtility.showMessage(this, "Error al obtener el nuevo precio según el convenio.",
                                                 txtCantidad);
                    }
                    txtPrecioVenta.setText(VariablesVentas.vVal_Prec_Vta);
                }
            }
        } else {
            if (VariablesConvenioBTLMF.vCodConvenio != null &&
                VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {
                /*try{
                        indControlPrecio = DBConvenio.obtieneIndPrecioControl(VariablesVentas.vCod_Prod);
                      }catch(SQLException sql)
                      {
                        log.error("",sql);
                        FarmaUtility.showMessage(this,"Error al obtener indicador de control de precio.",txtCantidad);
                      }*/
                /* 23.01.2007 ERIOS La validacion de realiza por las listas de exclusion */
                //if(indControlPrecio.equals(FarmaConstants.INDICADOR_N))
                if (true) {
                    /*try {*/
                    /*VariablesVentas.vVal_Prec_Vta = DBConvenio.obtieneNvoPrecioConvenio(VariablesConvenioBTLMF.vCodConvenio,
                                                                              VariablesVentas.vCod_Prod,
                                                                              txtPrecioVenta.getText());*/
                    /*VariablesVentas.vVal_Prec_Vta=UtilityVentas.Conv_Buscar_Precio();*/


                    log.debug(">>>>>>VariablesConvenioBTLMF.vNew_Prec_Conv: " + VariablesConvenioBTLMF.vNew_Prec_Conv);
                    log.debug(">>>>>>><vVal_Prec_Vta: " + VariablesVentas.vVal_Prec_Vta);

                    if (VariablesConvenioBTLMF.vNew_Prec_Conv.trim().length() > 0) {

                        VariablesVentas.vVal_Prec_Vta =
                                /*DBConvenioBTLMF.obtieneNvoPrecioConvenio_btlmf(VariablesConvenioBTLMF.vCodConvenio,
	                                                                               VariablesVentas.vCod_Prod,
	                                                                               VariablesVentas.vVal_Prec_Pub);*/
                                /*UtilityVentas.Conv_Buscar_Precio();*/
                                VariablesConvenioBTLMF.vNew_Prec_Conv;

                    }


                    VariablesVentas.vIndAplicaPrecNuevoCampanaCupon = FarmaConstants.INDICADOR_N;

                    log.debug("vVal_Prec_Vta: " + VariablesVentas.vVal_Prec_Vta);
                    /*} catch (SQLException sql) {
                        log.error("",sql);
                        FarmaUtility.showMessage(this,
                                                 "Error al obtener el nuevo precio según el convenio.",
                                                 txtCantidad);
                    }*/

                    if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                        VariablesConvenioBTLMF.vCodConvenio != null &&
                        VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {
                        txtPrecioVenta.setEnabled(true);
                        txtPrecioVenta.setVisible(false);
                        btnPrecioVta.setText(" ");
                    } else {
                        txtPrecioVenta.setText(VariablesVentas.vVal_Prec_Vta);
                    }
                }
            }
        }
        /**---FIN----*/
        log.debug("VariablesVentas.vVal_Prec_Vta 01:" + VariablesVentas.vVal_Prec_Vta);
        log.debug("VariablesVentas.vIndAplicaPrecNuevoCampanaCupon: " +
                  VariablesVentas.vIndAplicaPrecNuevoCampanaCupon);
        if (VariablesVentas.vIndAplicaPrecNuevoCampanaCupon.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            VariablesVentas.vListaProdAplicoPrecioDescuento.add(lblCodigo.getText().trim());
        }


        // >>>>>>>>>>>>>>>>>>>>>>>>

        cerrarVentana(true);
    }

    private void obtieneStockProducto_ForUpdate(ArrayList pArrayList) {
        try {
            DBVentas.obtieneStockProducto_ForUpdate(pArrayList, VariablesVentas.vCod_Prod, VariablesVentas.vVal_Frac);
            FarmaUtility.liberarTransaccion();
            //quitar bloqueo de stock fisico
            //dubilluz 13.10.2011
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            //quitar bloqueo de stock fisico
            //dubilluz 13.10.2011
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener stock del producto. \n" +
                    sql.getMessage(), txtCantidad);
        }
    }

    private void obtieneStockProducto() {
        ArrayList myArray = new ArrayList();
        obtieneStockProducto_ForUpdate(myArray);
        if (myArray.size() == 1) {
            VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            log.info("DlgIngresoCantidad : VariablesVentas.vPorc_Dcto_1 (2) - " + VariablesVentas.vPorc_Dcto_1);
        } else {
            FarmaUtility.showMessage(this, "Error al obtener Stock del Producto", null);
            cerrarVentana(false);
        }
    }

    private void evaluaTipoPedido() {
        if (!VariablesVentas.vEsPedidoDelivery && !VariablesVentas.vEsPedidoInstitucional) {
            /*if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) && FarmaVariables.vIndHabilitado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
      }*/
            txtPrecioVenta.setEnabled(false);
            FarmaUtility.moveFocus(txtCantidad);
        } else if (VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional) {
            if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) &&
                FarmaVariables.vIndHabilitado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                txtPrecioVenta.setEnabled(true);
                FarmaUtility.moveFocus(txtPrecioVenta);
            } else {
                txtPrecioVenta.setEnabled(false);
                FarmaUtility.moveFocus(txtCantidad);
            }
        }
    }

    private void calculoNuevoDescuento() {
        double precioLista = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Lista);
        double precioVenta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
        double porcDcto = (1 - (precioVenta / precioLista));
        VariablesVentas.vPorc_Dcto_1 = FarmaUtility.formatNumber(porcDcto, 2);
    }

    private void muestraMaxProdCupon() {
        String vCodCamp;
        String vIndPordCamp;
        String vIndTipoCupon;
        double vCantProdMax;
        //ArrayList cupon = new ArrayList();
        Map mapaCupon = new HashMap();

        lblProdCupon.setVisible(false);

        try {
            for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(j);
                vCodCamp = mapaCupon.get("COD_CAMP_CUPON").toString(); //cupon.get(1).toString();
                vIndTipoCupon = mapaCupon.get("TIP_CUPON").toString(); //cupon.get(2).toString();
                vCantProdMax =
                        FarmaUtility.getDecimalNumber(mapaCupon.get("UNID_MAX_PROD").toString()); //FarmaUtility.getDecimalNumber(cupon.get(6).toString());
                vIndPordCamp = DBVentas.verificaProdCamp(vCodCamp, VariablesVentas.vCod_Prod);
                if (vIndPordCamp.equals(FarmaConstants.INDICADOR_S)) {
                    if (vIndTipoCupon.equalsIgnoreCase("P")) {
                        lblProdCupon.setText("Máximo " + vCantProdMax + " unidades para aplicar el descuento.");
                        lblProdCupon.setVisible(true);
                    }
                    break;
                }
            }
        } catch (SQLException e) {
            log.error(null, e);
        }
    }

    private boolean isCampanaFidelizacion(String pCodCupon) {
        int i = pCodCupon.trim().indexOf("F");
        if (i == -1)
            return false;
        return true;
    }

    /**
     * corregir este metodo ya que en su momento DUBILLUZ
     * hizo la logicade mostrar el primero encontrado
     ***/
    public void calculoNuevoPrecio() {
        String vCodCamp;
        String vNvoPrecio;
        String vIndTipoCupon;
        double vNvoPrecioRedondeado; //JCHAVEZ 29102009
        //ArrayList cupon = new ArrayList();
        Map mapaCupon = new HashMap();
        //boolean vIndFidelizacion =  false;
        String vIndFidelizacion = "N";
        pPrecioFidelizacion = 0.0;
        lblPrecioProdCamp.setVisible(false);

        String vPrecioVenta = "";

        try {
            for (int j = 0; j < VariablesVentas.vArrayList_Cupones.size(); j++) {
                mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(j);
                vCodCamp = mapaCupon.get("COD_CAMP_CUPON").toString(); //cupon.get(1).toString();
                vIndTipoCupon = mapaCupon.get("TIP_CUPON").toString(); //cupon.get(2).toString();
                vIndFidelizacion = mapaCupon.get("IND_FID").toString(); /*false;
                                                                     *
            														vIndFidelizacion = isCampanaFidelizacion(cupon.get(0).toString());*/
                vPrecioVenta = txtPrecioVenta.getText().trim();

                if (vIndFidelizacion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                    // DUBILLUZ 01.07.2014
                    /* if(VariablesVentas.vEsPedidoDelivery)
                   vNvoPrecio = "N";
                else */
                    vNvoPrecio = DBVentas.getNuevoPrecio(VariablesVentas.vCod_Prod, vCodCamp, vPrecioVenta,
                    //dubilluz 15.08.2015 -- 
                    VariablesFidelizacion.vDniCliente
                                                         );

                    // vNvoPrecioRedondeado= DBVentas.getPrecioRedondeado(Double.parseDouble(vNvoPrecio.trim())); //JCHAVEZ 29102009
                    if (!vNvoPrecio.equals(FarmaConstants.INDICADOR_N)) {
                        vNvoPrecioRedondeado =
                                DBVentas.getPrecioRedondeado(Double.parseDouble(vNvoPrecio.trim())); //JCHAVEZ 29102009
                        if (vIndTipoCupon.equalsIgnoreCase("P")) {
                            pPrecioFidelizacion = Double.parseDouble(vNvoPrecio.trim());
                            log.debug("JCHAVEZ pPrecioFidelizacion: sin redondeo " + pPrecioFidelizacion);
                            lblPrecioProdCamp.setText("Prec. Fidelizado. Referencial : S/. " +
                                                      vNvoPrecioRedondeado); //JCHAVEZ 29102009 se cambio vNvoPrecio por vNvoPrecioRedondeado
                            lblPrecioProdCamp.setVisible(true);
                        }
                        break;
                    }
                }


            }

            //05-DIC-13, TCT , Obtener precio Fijo de Producto y Comparar  con Fidelizado  -- Begin
            // Lectura de Datos de Campaña con el Mejor Precio de Promocion (el mas bajo)
            if (!VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
                Map mapCampPrec = new HashMap();
                try {
                    mapCampPrec = DBVentas.getDatosCampPrec(VariablesVentas.vCod_Prod);


                } catch (SQLException e) {

                    FarmaUtility.showMessage(this, "Ocurrio un error al obtener datos de la Campaña por Precio.\n" +
                            e.getMessage() +
                            "\n Inténtelo Nuevamente\n si persiste el error comuniquese con operador de sistemas.",
                            null);

                }
                log.debug("######## TCT 20: mapCampPrec:" + mapCampPrec);

                // Verificar si el Precio de Campaña x Precio es < al Precio Fidelizado => Calcular Nuevamente
                if (mapCampPrec.size() > 0) {
                    double vd_Val_Prec_Prom;
                    double vd_Prec_Fideliz = 99999.0;
                    int vi_Val_Frac_Vta = Integer.parseInt(VariablesVentas.vVal_Frac);
                    //int vi_Val_Frac_Sug = Integer.parseInt(VariablesVentas.vVal_Frac_Sug);

                    String vs_Val_Prec_Prom = (String)mapCampPrec.get("VAL_PREC_PROM_ENT");
                    String vs_Cod_Camp_Prec = (String)mapCampPrec.get("COD_CAMP_CUPON");
                    log.debug("###### TCT 21 : vs_Val_Prec_Prom: " + vs_Val_Prec_Prom);
                    /*
       if (VariablesVentas.vVal_Frac_Sug.length()>0) {
           vi_Val_Frac_Vta=Integer.parseInt(VariablesVentas.vVal_Frac_Sug);
       }
       */
                    vd_Val_Prec_Prom = FarmaUtility.getDecimalNumber(vs_Val_Prec_Prom) / vi_Val_Frac_Vta;
                    if (lblPrecioProdCamp.getText().length() > 0) {
                        vd_Prec_Fideliz =
                                pPrecioFidelizacion; //Double.parseDouble(lblPrecioProdCamp.getText().trim());
                    }

                    log.debug("###### TCT 25 : vd_Val_Prec_Prom: " + vd_Val_Prec_Prom);

                    if (vd_Val_Prec_Prom < vd_Prec_Fideliz) {
                        lblPrecioProdCamp.setText("Prec. Fijo Campaña : S/. " + vd_Val_Prec_Prom);
                        lblPrecioProdCamp.setVisible(true);
                    }


                }
            }
            //05-DIC-13, TCT , Obtener precio Fijo de Producto y Comparar  con Fidelizado  -- End


        } catch (SQLException e) {
            log.error(null, e);
        }
    }
    /*boolean vIndFidelizacion;
  // [FA0001, A0001, P, 10, 10, 0, 1, 100, N, 0000000100000001P 99990.000],
  vIndFidelizacion = isCampanaFidelizacion(cupon.get(0));
   * */

    private String getAnalizaPrecio(String pPrecioVenta, double pNvoPrecioFid) {
        String pResultado = "";

        //jcallo se quito las comas del precio de los productos
        pPrecioVenta = pPrecioVenta.replaceAll(",", "");

        double pPrecio = Double.parseDouble(pPrecioVenta.trim());
        log.debug("pPrecio:" + pPrecio);
        log.debug("pNvoPrecioFid:" + pNvoPrecioFid);
        log.debug("VariablesVentas.vIndAplicaPrecNuevoCampanaCupon:" +
                  VariablesVentas.vIndAplicaPrecNuevoCampanaCupon);
        if (pNvoPrecioFid > 0) {
            if (pPrecio >= pNvoPrecioFid) {
                /*Se comento este metodo porque no funcionaba para el caso
                 * de productos fraccionados
                 * asi que por lo visto no existe diferencia salvo
                */
                /*
                try
                {
                  pResultado = DBVentas.getPrecioNormal(VariablesVentas.vCod_Prod);

                }catch(SQLException e)
                {
                  log.error(null,e);
                } */
                VariablesVentas.vIndAplicaPrecNuevoCampanaCupon = FarmaConstants.INDICADOR_S;
                pResultado = "" + pPrecio;
            } else {
                pResultado = "" + pPrecio;

            }
        } else
            pResultado = "" + pPrecioVenta;
        pResultado = pResultado.trim();
        log.debug("pResultado:" + pResultado);

        return pResultado;
    }


    private boolean isExisteProdCampana(String pCodProd) {
        //lblMensajeCampaña.setVisible(true);
        String pRespta = "N";
        try {
            lblMensajeCampaña.setText("");
            pRespta = DBVentas.existeProdEnCampañaAcumulada(pCodProd, VariablesFidelizacion.vDniCliente);
            if (pRespta.trim().equalsIgnoreCase("E"))
                lblMensajeCampaña.setText("    Cliente ya está inscrito en campaña Acumula tu Compra y Gana");

            if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                lblMensajeCampaña.setText("  Producto se encuentra en la campaña \" Acumula tu Compra y Gana\"");
        } catch (SQLException e) {
            log.error(null, e);
            pRespta = "N";
        }

        if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;

        return true;
    }


    private void muestraInfoDetalleProd_Btl() {

        log.debug("metodo:muestraInfoDetalleProd_Btl");
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesVentas.vStk_Prod_Fecha_Actual = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            if ((!VariablesVentas.vEsPedidoDelivery && !VariablesVentas.vEsPedidoInstitucional) ||
                !VariablesVentas.vIngresaCant_ResumenPed) {

                //JCORTEZ 11/04/08 no se actualiza el precio y descuento si es producto  oferta
                //if(!VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)||!VariablesVentas.vEsProdOferta)

                // Segun gerencia se debe seguir la misma logica para todos los productos.
                if (VariablesVentas.vVentanaListadoProductos) {
                    log.debug("SETEANDO DESCUENTO");
                    VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                    VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();

                } else {
                    if (UtilityVentas.isAplicoPrecioCampanaCupon(lblCodigo.getText().trim(),
                                                                 FarmaConstants.INDICADOR_S)) {
                        if (!VariablesVentas.vVentanaOferta) {
                            log.debug("SETEANDO DESCUENTO");
                            VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                            VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
                        }
                    }
                }


                log.debug("VariablesVentas.vVal_Prec_Vta_XXXXXXX: " + VariablesVentas.vVal_Prec_Vta);
                log.debug("VariablesVentas.vVentanaListadoProductos: " + VariablesVentas.vVentanaListadoProductos);

                log.info("DlgIngresoCantidad: VariablesVentas.vPorc_Dcto_1 - " + VariablesVentas.vPorc_Dcto_1);
                log.debug("VariablesVentas.vPorc_Dcto_2 : " + VariablesVentas.vPorc_Dcto_2);
            }
            VariablesVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesVentas.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            VariablesVentas.vStk_Prod = "0";
            VariablesVentas.vDesc_Acc_Terap = "";
            VariablesVentas.vStk_Prod_Fecha_Actual = "";
            VariablesVentas.vVal_Prec_Vta = "";
            VariablesVentas.vUnid_Vta = "";
            VariablesVentas.vPorc_Dcto_1 = "";
            VariablesVentas.vVal_Prec_Lista = "";
            VariablesVentas.vNom_Lab = "";
            VariablesVentas.vDesc_Prod = "";
            VariablesVentas.vCod_Prod = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", null);
            cerrarVentana(false);
        }


        lblFechaHora.setText(VariablesVentas.vStk_Prod_Fecha_Actual);
        lblStock.setText("" + (Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic));
        //lblStock.setText(VariablesVentas.vStk_Prod);
        lblCodigo.setText(VariablesVentas.vCod_Prod);
        lblDescripcion.setText(VariablesVentas.vDesc_Prod);
        lblLaboratorio.setText(VariablesVentas.vNom_Lab);
        lblUnidad.setText(VariablesVentas.vUnid_Vta);

        String PrecioNew = VariablesConvenioBTLMF.vNew_Prec_Conv.toString();
        log.debug("Precio PrecioNew:" + PrecioNew);


        log.debug("VARIABLE EN MEMORIA CONVENIO:" + VariablesConvenio.vVal_Prec_Vta_Conv);

        log.debug("Precio Normal:" + VariablesVentas.vVal_Prec_Vta);
        log.debug("Precio Conven:" + VariablesConvenioBTLMF.vNew_Prec_Conv);

        //        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0)
        //        {
        //           if (VariablesVentas.vEstadoProdConvenio.equals("P"))
        //           {
        //         	  VariablesConvenioBTLMF.vNew_Prec_Conv = VariablesVentas.vVal_Prec_Vta;
        //           }
        //           //Valida el Monto Precio convenio
        //           if(UtilityConvenioBTLMF.esMontoPrecioCero(VariablesConvenioBTLMF.vNew_Prec_Conv,this))
        //           {
        //        	   cerrarVentana(false);
        //           }
        //        }
        //rsachun
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {
            if ((VariablesVentas.vEstadoProdConvenio.equals("P")) ||
                (VariablesVentas.vEstadoProdConvenio.equals("I"))) {
                log.debug("Estado:" + VariablesVentas.vEstadoProdConvenio);
            } else {
                VariablesConvenioBTLMF.vNew_Prec_Conv = VariablesVentas.vVal_Prec_Vta;
            }
            //Valida el Monto Precio convenio
            if (UtilityConvenioBTLMF.esMontoPrecioCero(VariablesConvenioBTLMF.vNew_Prec_Conv, this)) {
                cerrarVentana(false);
            }
        }

        txtPrecioVenta.setText(VariablesVentas.vVal_Prec_Vta); //JCHAVEZ 29102009 se cambio txtPrecioVenta por txtPrecioVentaOculto


        //lblDcto.setText(VariablesVentas.vPorc_Dcto_1);
        txtCantidad.setText("" + cantInic);
        //JCHAVEZ 29102009 inicio
        try {
            //double precVtaRedondeadoNum = DBVentas.getPrecioRedondeado(Double.parseDouble(VariablesVentas.vVal_Prec_Vta));  antes
            double precVtaRedondeadoNum =
                DBVentas.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta)); //ASOSA, 18.06.2010

            String precVtaRedondeadoStr =
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaRedondeadoNum), 3);

            VariablesConvenio.vVal_Prec_Vta_Conv = VariablesConvenioBTLMF.vNew_Prec_Conv;
            /*UtilityConvenioBTLMF.Conv_Buscar_Precio()*/;
            /*DBConvenioBTLMF.obtieneNvoPrecioConvenio_btlmf(VariablesConvenioBTLMF.vCodConvenio,
                                                                                           VariablesVentas.vCod_Prod,
                                                                                           VariablesVentas.vVal_Prec_Pub);*/

            if (VariablesConvenio.vVal_Prec_Vta_Conv != null &&
                !VariablesConvenio.vVal_Prec_Vta_Conv.trim().equalsIgnoreCase("")) {
                log.debug("VariablesConvenio.vVal_Prec_Vta_Conv: " + VariablesConvenio.vVal_Prec_Vta_Conv);
                double precVtaConvRedondeadoNum =
                    DBVentas.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesConvenio.vVal_Prec_Vta_Conv));
                String precVtaConvRedondeadoStr =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaConvRedondeadoNum), 3);

                lblPrecVtaConv.setText(precVtaConvRedondeadoStr); //JCHAVEZ 29102009 se cambio VariablesConvenio.vVal_Prec_Vta_Conv por precVtaConvRedondeadoStr

            }
            log.debug("precVtaRedondeadoStr:" + precVtaRedondeadoStr);

            this.txtPrecioVentaRedondeado.setText(precVtaRedondeadoStr);


        } catch (SQLException sql) {
            log.error("", sql);
        }
        //JCHAVEZ 29102009 fin
    }

    private void txtCantidad_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    private void lblF11_keyPressed(KeyEvent e) {
        if (!vbPrecProdOk) {
            FarmaUtility.showMessage(this, "No se puede Vender un Producto con Precio inferior al Mínimo Vigente...",
                                     txtCantidad);
            return;
        }
        // Ejct, 27-DIC-12, si producto tiene precio inferior al minimo then error
        aceptaCantidadIngresada();
    }
    
    public void ejecutarVKEnter(){
        
    }

}
