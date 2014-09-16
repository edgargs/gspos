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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

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

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación :  DlgIngresoCantidadTarjetaVirtual .java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      29.08.2007   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */

public class  DlgIngresoCantidadTarjetaVirtual  extends JDialog {

  private int cantInic = 0;
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadTarjetaVirtual.class);

  /** Objeto Frame de la Aplicación */
  Frame myParentFrame;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  JPanel pnlDetalleProducto = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JTextField txtPrecioVenta = new JTextField();
  JLabel lblUnidadT = new JLabel();
  JLabel lblDescripcionT = new JLabel();
  JLabel lblCodigoT = new JLabel();
  JLabel lblLaboratorio = new JLabel();
  JLabel lblLaboratorioT = new JLabel();
  JLabel lblPrecio = new JLabel();
  public JTextField txtCantidad = new JTextField();
  JLabel lblUnidad = new JLabel();
  JLabel lblDescripcion = new JLabel();
  JLabel lblCodigo = new JLabel();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JButtonLabel btnCantidad = new JButtonLabel();
  private JButtonLabel btnPrecioVta = new JButtonLabel();
  
  ArrayList datos_producto = new ArrayList();

// **************************************************************************
// Constructores
// **************************************************************************
  /**
  *Constructor
  */
  public  DlgIngresoCantidadTarjetaVirtual () {
    this(null, "", false);
  }

  /**
  *Constructor
  *@param <b>parent</b> Objeto Frame de la Aplicación.
  *@param <b>title</b> Título de la Ventana.
  *@param <b>modal</b> Tipo de Ventana.
  */
  public  DlgIngresoCantidadTarjetaVirtual (Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    myParentFrame = parent;
    try {
      jbInit();
      initialize();

    } catch(Exception e) {
      log.error("",e);
    }
  }

// **************************************************************************
// Método "jbInit()"
// **************************************************************************
  /**
  *Implementa la Ventana con todos sus Objetos
  */
  private void jbInit() throws Exception {
    this.setSize(new Dimension(365, 304));
    this.getContentPane().setLayout(borderLayout1);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Ingreso de Cantidad  Tarjeta Virtual");
    this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
        
        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(360, 331));
    jContentPane.setBackground(Color.white);
    pnlDetalleProducto.setBounds(new Rectangle(15, 15, 325, 220));
    pnlDetalleProducto.setLayout(xYLayout1);
    pnlDetalleProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlDetalleProducto.setFont(new Font("SansSerif", 0, 11));
    pnlDetalleProducto.setBackground(Color.white);
    txtPrecioVenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    txtPrecioVenta.setFont(new Font("SansSerif", 1, 11));
    txtPrecioVenta.setEnabled(false);
    txtPrecioVenta.setText("13.20");
    txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
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
    lblLaboratorioT.setText("Laboratorio :");
    lblLaboratorioT.setFont(new Font("SansSerif", 1, 11));
    lblPrecio.setText("9,990.00");
    lblPrecio.setHorizontalAlignment(SwingConstants.LEFT);
    lblPrecio.setFont(new Font("SansSerif", 0, 11));
    txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    txtCantidad.setDocument(new FarmaLengthText(6));
    txtCantidad.setText("1");
    txtCantidad.setFont(new Font("SansSerif", 1, 11));
    txtCantidad.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtCantidad_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtCantidad_keyTyped(e);
        }
      });
    lblUnidad.setText(" ");
    lblUnidad.setFont(new Font("SansSerif", 0, 11));
    lblDescripcion.setText(" ");
    lblDescripcion.setFont(new Font("SansSerif", 0, 11));
    lblCodigo.setText(" ");
    lblCodigo.setFont(new Font("SansSerif", 0, 11));
    lblF11.setText("[ ENTER ] Aceptar");
    lblF11.setBounds(new Rectangle(110, 245, 135, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(255, 245, 85, 20));
    btnCantidad.setText("Cantidad :");
    btnCantidad.setForeground(Color.black);
    btnCantidad.setMnemonic('c');
    btnCantidad.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCantidad_actionPerformed(e);
        }
      });
    btnPrecioVta.setText("Precio Venta : S/.");
    btnPrecioVta.setForeground(Color.black);
    
    pnlDetalleProducto.add(btnPrecioVta, new XYConstraints(10, 175, 100, 20));
    pnlDetalleProducto.add(btnCantidad, new XYConstraints(185, 175, 60, 20));
    pnlDetalleProducto.add(txtPrecioVenta, new XYConstraints(115, 175, 60, 20));
    pnlDetalleProducto.add(lblUnidadT, new XYConstraints(10, 145, 50, 20));
    pnlDetalleProducto.add(lblDescripcionT, new XYConstraints(10, 55, 95, 20));
    pnlDetalleProducto.add(lblCodigoT, new XYConstraints(10, 10, 55, 20));
    pnlDetalleProducto.add(lblLaboratorio, new XYConstraints(10, 120, 280, 20));
    pnlDetalleProducto.add(lblLaboratorioT, new XYConstraints(10, 100, 80, 20));
    pnlDetalleProducto.add(lblPrecio, new XYConstraints(120, 175, 55, 20));
    pnlDetalleProducto.add(txtCantidad, new XYConstraints(250, 175, 50, 20));
    pnlDetalleProducto.add(lblUnidad, new XYConstraints(70, 145, 135, 20));
    pnlDetalleProducto.add(lblDescripcion, new XYConstraints(10, 75, 270, 20));
    pnlDetalleProducto.add(lblCodigo, new XYConstraints(10, 30, 55, 20));
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(pnlDetalleProducto, null);
    //this.getContentPane().add(jContentPane, null);
  }

// **************************************************************************
// Método "initialize()"
// **************************************************************************
  private void initialize()
  {
    FarmaVariables.vAceptar = false;
  };

// **************************************************************************
// Métodos de inicialización
// **************************************************************************

  private void muestraInfoDetalleProd()
  {
    datos_producto = new ArrayList();
    datos_producto = VariablesVentas.vArrayList_Prod_Tarjeta_Virtual;
    lblCodigo.setText(datos_producto.get(0).toString());
    lblDescripcion.setText(datos_producto.get(1).toString());
    lblLaboratorio.setText(datos_producto.get(2).toString());
    lblUnidad.setText(datos_producto.get(3).toString());
    txtPrecioVenta.setText(datos_producto.get(4).toString());
    //CANTIDAD MINIMA 
    if(VariablesVentas.vArrayList_ResumenPedido.size()>0)
    txtCantidad.setText(VariablesVentas.cantidad_tarjeta_virtual + "");
    else
    txtCantidad.setText("1");
  }

// **************************************************************************
// Metodos de eventos
// **************************************************************************
  private void txtPrecioVenta_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        FarmaUtility.moveFocus(txtCantidad);
    }
    
  }

  private void txtCantidad_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      /*if(!validaCantidadIngresada())
        FarmaUtility.showMessage(this,"Ingrese una cantidad valida", txtCantidad);
      else*/
        aceptaCantidadIngresada();
    }
  }
  
  private void txtCantidad_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtCantidad,e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    muestraInfoDetalleProd();
    FarmaUtility.moveFocus(txtCantidad);
    txtCantidad.selectAll();
  }

  private void btnCantidad_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtCantidad);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }


// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      //mfajardo 29/04/09 validar ingreso de productos virtuales 
      VariablesVentas.vProductoVirtual=false;
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    lblCodigo.setText("");
    lblDescripcion.setText("");
    lblLaboratorio.setText("");
    lblUnidad.setText("");
    txtPrecioVenta.setText("");
    //CANTIDAD MINIMA 
    txtCantidad.setText("");       
    this.setVisible(false);
    this.dispose();
  }

// **************************************************************************
// Metodos de lógica de negocio
// **************************************************************************


  private void aceptaCantidadIngresada()
  {
    if(txtCantidad.getText().trim().length()!=0){
      int cantidad =Integer.parseInt(txtCantidad.getText().trim());
      if(cantidad<=ConstantsVentas.CANT_PROD_TARJ_VIRTUAL_MAXIMO){
         VariablesVentas.cantidad_tarjeta_virtual = cantidad;
         cerrarVentana(true);
      }
      else
      FarmaUtility.showMessage(this,"No puede ingresar una cantidad mayor a "+
            ConstantsVentas.CANT_PROD_TARJ_VIRTUAL_MAXIMO, txtCantidad);
    }
    else
    cerrarVentana(false);
  }
  
  /*private boolean validaCantidadIngresada()
  {
    int cant = Integer.parseInt(txtCantidad.getText().trim()) ;
    if (cant > 1)
      return false;
    else if (cant < 1)
      return false;
    else return true ;    
  }*/

}
