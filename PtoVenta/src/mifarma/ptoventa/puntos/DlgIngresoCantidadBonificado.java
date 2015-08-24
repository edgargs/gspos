package mifarma.ptoventa.puntos;

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
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesReceta;

import mifarma.ptoventa.ventas.reference.VariablesVentas;

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
public class DlgIngresoCantidadBonificado extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadBonificado.class);
    private int cantInic = 0;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel pnlStock = new JPanel();
    private XYLayout xYLayout2 = new XYLayout();
    private JLabel lblUnidades = new JLabel();
    private JLabel lblStock = new JLabel();
    private JLabel lblFechaHora = new JLabel();
    private JLabel lblStockTexto = new JLabel();
    private JPanel pnlDetalleProducto = new JPanel();
    private XYLayout xYLayout1 = new XYLayout();
    private JLabel lblUnidadT = new JLabel();
    private JLabel lblDescripcionT = new JLabel();
    private JLabel lblCodigoT = new JLabel();
    private JLabel lblLaboratorio = new JLabel();
    private JLabel lblLaboratorioT = new JLabel();
    private JTextField txtCantidad = new JTextField();
    private JLabel lblUnidad = new JLabel();
    private JLabel lblDescripcion = new JLabel();
    private JLabel lblCodigo = new JLabel();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnCantidad = new JButtonLabel();
    
    private String fechaStock;
    private String stockProd;
    private String unidadVenta;
    private String cantidadIngresada;
    
    private String codProducto;
    private String descripcionProducto;
    private String nombreLaboratorio;
    private String cantidadInicial;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     * Constructor
     */
    public DlgIngresoCantidadBonificado() {
        this(null, "", false);
    }

    /**
     * Constructor
     * @param parent Objeto Frame de la Aplicación.
     * @param title Título de la Ventana.
     * @param modal Tipo de Ventana.
     */
    public DlgIngresoCantidadBonificado(Frame parent, String title, boolean modal) {
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
        lblUnidadT.setText("Unidad");
        lblUnidadT.setFont(new Font("SansSerif", 1, 11));
        lblDescripcionT.setText("Descripcion");
        lblDescripcionT.setFont(new Font("SansSerif", 1, 11));
        lblCodigoT.setText("Codigo");
        lblCodigoT.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorio.setText("COLLIERE S.A.");
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorioT.setText("Laboratorio :");
        lblLaboratorioT.setFont(new Font("SansSerif", 1, 11));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
        txtCantidad.setDocument(new FarmaLengthText(6));
        txtCantidad.setText("0");
        txtCantidad.setFont(new Font("SansSerif", 1, 11));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidadKeyPressed(e);
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
                btnCantidadActionPerformed(e);
            }
        });
        pnlStock.add(lblUnidades, new XYConstraints(230, 10, 75, 20));
        pnlStock.add(lblStock, new XYConstraints(140, 5, 80, 30));
        pnlStock.add(lblFechaHora, new XYConstraints(5, 20, 130, 20));
        pnlStock.add(lblStockTexto, new XYConstraints(5, 0, 125, 20));
        pnlDetalleProducto.add(btnCantidad, new XYConstraints(184, 145, 60, 20));
        pnlDetalleProducto.add(lblUnidadT, new XYConstraints(10, 145, 50, 20));
        pnlDetalleProducto.add(lblDescripcionT, new XYConstraints(10, 55, 95, 20));
        pnlDetalleProducto.add(lblCodigoT, new XYConstraints(10, 10, 55, 20));
        pnlDetalleProducto.add(lblLaboratorio, new XYConstraints(10, 120, 280, 20));
        pnlDetalleProducto.add(lblLaboratorioT, new XYConstraints(10, 100, 80, 20));
        pnlDetalleProducto.add(txtCantidad, new XYConstraints(184, 169, 70, 20));
        pnlDetalleProducto.add(lblUnidad, new XYConstraints(9, 169, 135, 20));
        pnlDetalleProducto.add(lblDescripcion, new XYConstraints(10, 75, 270, 20));
        pnlDetalleProducto.add(lblCodigo, new XYConstraints(10, 30, 55, 20));
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlStock, null);
        jContentPane.add(pnlDetalleProducto, null);
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
            DBVentas.obtieneInfoDetalleProducto(pArrayList, codProducto);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto. \n" +
                    sql.getMessage(), txtCantidad);
        }
    }
    
    
    
    private void muestraInfoDetalleProd() {
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            stockProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            fechaStock = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            unidadVenta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();

        } else {
            stockProd = "";
            fechaStock = "";
            unidadVenta = "";
            nombreLaboratorio = "";
            descripcionProducto = "";
            codProducto = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", null);
            cerrarVentana(false);
        }
        lblFechaHora.setText(fechaStock);
        lblStock.setText("" + (Integer.parseInt(stockProd)));
        lblCodigo.setText(codProducto);
        lblDescripcion.setText(descripcionProducto);
        lblLaboratorio.setText(nombreLaboratorio);
        lblUnidad.setText(unidadVenta);
        txtCantidad.setText("" + cantInic);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtCantidadKeyPressed(KeyEvent e) {
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            aceptaCantidadIngresada();
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cantInic = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidadInicial));
        muestraInfoDetalleProd();
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void btnCantidadActionPerformed(ActionEvent e) {
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
        // valida stock
        int cantidad = (int)Integer.parseInt(txtCantidad.getText().trim());
        int cantInicial = (int)Integer.parseInt(cantidadInicial);
        if(cantidad>cantInicial){
            FarmaUtility.showMessage(this, "Cantidad Maxima de productos a llevar: "+cantInicial, txtCantidad);
            return;
        }
        if (!UtilityVentas.existsStockComp(codProducto, cantidad)) {
            FarmaUtility.showMessage(this, "No hay stock suficiente del producto", txtCantidad);
            lblStock.setText("" + (Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic));
            return;
        }
        cantidadIngresada = txtCantidad.getText().trim();
        
        cerrarVentana(true);
    }
    
    public void setDescripcionProducto(String descripcionProducto){
        this.descripcionProducto = descripcionProducto;
    }
    
    public void setNombreLaboratorio(String nombreLaboratorio){
        this.nombreLaboratorio = nombreLaboratorio;
    }
    public void setCantidadInicial(String cantidadInicial){
        this.cantidadInicial = cantidadInicial;
    }
    
    public void setCodProducto(String codProducto){
        this.codProducto = codProducto;
    }
    
    public String getCantidadIngresada(){
        return this.cantidadIngresada;
    }
}
