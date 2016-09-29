package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesReceta;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoCantidadReceta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      06.12.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgIngresoCantidadReceta extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadReceta.class);

    private int cantInic = 0;

    /**Objeto Frame de la Aplicación
     */
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
    JLabel lblPrecio = new JLabel();
    public JTextField txtCantidad = new JTextField();
    JLabel lblUnidad = new JLabel();
    JLabel lblDescripcion = new JLabel();
    JLabel lblCodigo = new JLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JButtonLabel btnPrecioVta = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     * Constructor
     */
    public DlgIngresoCantidadReceta() {
        this(null, "", false);
    }

    /**
     * Constructor
     * @param <b>parent</b> Objeto Frame de la Aplicación.
     * @param <b>title</b> Título de la Ventana.
     * @param <b>modal</b> Tipo de Ventana.
     */
    public DlgIngresoCantidadReceta(Frame parent, String title, boolean modal) {
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
     * Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(365, 366));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Ingreso de Cantidad Receta");
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
        pnlStock.setBounds(new Rectangle(15, 20, 325, 55));
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
        pnlDetalleProducto.setBounds(new Rectangle(15, 80, 325, 220));
        pnlDetalleProducto.setLayout(xYLayout1);
        pnlDetalleProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDetalleProducto.setFont(new Font("SansSerif", 0, 11));
        pnlDetalleProducto.setBackground(Color.white);
        txtPrecioVenta.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioVenta.setFont(new Font("SansSerif", 1, 11));
        txtPrecioVenta.setEnabled(false);
        txtPrecioVenta.setText("13.20");
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
        lblPrecio.setText("9,990.00");
        lblPrecio.setHorizontalAlignment(SwingConstants.LEFT);
        lblPrecio.setFont(new Font("SansSerif", 0, 11));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
        txtCantidad.setDocument(new FarmaLengthText(6));
        txtCantidad.setText("0");
        txtCantidad.setFont(new Font("SansSerif", 1, 11));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }
        });
        lblUnidad.setText(" ");
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setText(" ");
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setText(" ");
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblF11.setText("[ ENTER ] Aceptar");
        lblF11.setBounds(new Rectangle(110, 310, 135, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(255, 310, 85, 20));
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
        pnlStock.add(lblUnidades, new XYConstraints(230, 10, 75, 20));
        pnlStock.add(lblStock, new XYConstraints(140, 5, 80, 30));
        pnlStock.add(lblFechaHora, new XYConstraints(5, 20, 130, 20));
        pnlStock.add(lblStockTexto, new XYConstraints(5, 0, 125, 20));
        pnlDetalleProducto.add(btnPrecioVta, new XYConstraints(10, 175, 100, 20));
        pnlDetalleProducto.add(btnCantidad, new XYConstraints(185, 175, 60, 20));
        pnlDetalleProducto.add(txtPrecioVenta, new XYConstraints(115, 175, 60, 20));
        pnlDetalleProducto.add(lblUnidadT, new XYConstraints(10, 145, 50, 20));
        pnlDetalleProducto.add(lblDescripcionT, new XYConstraints(10, 55, 95, 20));
        pnlDetalleProducto.add(lblCodigoT, new XYConstraints(10, 10, 55, 20));
        pnlDetalleProducto.add(lblLaboratorio, new XYConstraints(10, 120, 280, 20));
        pnlDetalleProducto.add(lblDcto, new XYConstraints(265, 145, 40, 20));
        pnlDetalleProducto.add(lblLaboratorioT, new XYConstraints(10, 100, 80, 20));
        pnlDetalleProducto.add(lblDscto, new XYConstraints(215, 145, 50, 20));
        pnlDetalleProducto.add(lblPrecio, new XYConstraints(120, 175, 55, 20));
        pnlDetalleProducto.add(txtCantidad, new XYConstraints(250, 175, 50, 20));
        pnlDetalleProducto.add(lblUnidad, new XYConstraints(70, 145, 135, 20));
        pnlDetalleProducto.add(lblDescripcion, new XYConstraints(10, 75, 270, 20));
        pnlDetalleProducto.add(lblCodigo, new XYConstraints(10, 30, 55, 20));
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
            DBVentas.obtieneInfoDetalleProducto(pArrayList, VariablesReceta.vCod_Prod);
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto. \n" +
                    sql.getMessage(), txtCantidad);
        }
    }

    private void muestraInfoDetalleProd() {
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            VariablesReceta.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesReceta.vStk_Prod_Fecha_Actual = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            //if (VariablesReceta.vIngresaCant_ResumenPed)
            //{
            VariablesReceta.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            VariablesReceta.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            //}
            VariablesReceta.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesReceta.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesReceta.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            VariablesReceta.vStk_Prod = "";
            VariablesReceta.vDesc_Acc_Terap = "";
            VariablesReceta.vStk_Prod_Fecha_Actual = "";
            VariablesReceta.vVal_Prec_Vta = "";
            VariablesReceta.vUnid_Vta = "";
            VariablesReceta.vPorc_Dcto_1 = "";
            VariablesReceta.vVal_Prec_Lista = "";
            VariablesReceta.vNom_Lab = "";
            VariablesReceta.vDesc_Prod = "";
            VariablesReceta.vCod_Prod = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", null);
            cerrarVentana(false);
        }
        lblFechaHora.setText(VariablesReceta.vStk_Prod_Fecha_Actual);
        lblStock.setText("" + (Integer.parseInt(VariablesReceta.vStk_Prod)));
        //lblStock.setText(VariablesReceta.vStk_Prod);
        lblCodigo.setText(VariablesReceta.vCod_Prod);
        lblDescripcion.setText(VariablesReceta.vDesc_Prod);
        lblLaboratorio.setText(VariablesReceta.vNom_Lab);
        lblUnidad.setText(VariablesReceta.vUnid_Vta);
        txtPrecioVenta.setText(VariablesReceta.vVal_Prec_Vta);
        lblDcto.setText(VariablesReceta.vPorc_Dcto_1);
        txtCantidad.setText("" + cantInic);
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
            aceptaCantidadIngresada();
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cantInic = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesReceta.vCant_Ingresada_Temp));
        muestraInfoDetalleProd();
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
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

    private void aceptaCantidadIngresada() {
        if (!validaCantidadIngreso()) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.", txtCantidad);
            return;
        }
        VariablesReceta.vCant_Ingresada = txtCantidad.getText().trim();
        VariablesReceta.vVal_Prec_Vta = txtPrecioVenta.getText().trim();
        cerrarVentana(true);
    }

}
