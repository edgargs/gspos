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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

 
 


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoRecargaVirtual.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      05.01.2007   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgIngresoRecargaVirtual extends JDialog {

  private int recargarInic = 0;

  /** Objeto Frame de la Aplicación */
  Frame myParentFrame;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  JPanel pnlDetalleProducto = new JPanel();
   
  JTextField txtMonto = new JTextField();
  JLabel lblDescripcionT = new JLabel();
  JLabel lblCodigoT = new JLabel();
  JLabel lblLaboratorio = new JLabel();
  JLabel lblLaboratorioT = new JLabel();
  public JTextField txtNumero = new JTextField();
  JLabel lblDescripcion = new JLabel();
  JLabel lblCodigo = new JLabel();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JButtonLabel btnNumero = new JButtonLabel();
  private JButtonLabel btnMonto = new JButtonLabel();
  private int max_long_LIM ;
  private int max_long_PRV ;
  private int max_long_DTV ;

// **************************************************************************
// Constructores
// **************************************************************************
  /**
  *Constructor
  */
  public DlgIngresoRecargaVirtual() {
    this(null, "", false);
  }

  public DlgIngresoRecargaVirtual(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    myParentFrame = parent;
    try {
      jbInit();
      initialize();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

// **************************************************************************
// Método "jbInit()"
// **************************************************************************
  /**
  *Implementa la Ventana con todos sus Objetos
  */
  private void jbInit() throws Exception {
    this.setSize(new Dimension(365, 313));
    this.getContentPane().setLayout(borderLayout1);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Ingreso de Datos de Recarga");
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
    pnlDetalleProducto.setBounds(new Rectangle(15, 10, 325, 230));
    pnlDetalleProducto.setLayout(null);
    pnlDetalleProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlDetalleProducto.setFont(new Font("SansSerif", 0, 11));
    pnlDetalleProducto.setBackground(Color.white);
    txtMonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    txtMonto.setFont(new Font("SansSerif", 1, 11));
    txtMonto.setDocument(new FarmaLengthText(6));
    txtMonto.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtMonto_keyPressed(e);
        }
      });
    lblDescripcionT.setText("Descripcion");
    lblDescripcionT.setFont(new Font("SansSerif", 1, 11));
    lblCodigoT.setText("Codigo");
    lblCodigoT.setFont(new Font("SansSerif", 1, 11));
    lblLaboratorio.setText("COLLIERE S.A.");
    lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
    lblLaboratorioT.setText("Laboratorio :");
    lblLaboratorioT.setFont(new Font("SansSerif", 1, 11));
    txtNumero.setHorizontalAlignment(JTextField.LEFT);
    
    txtNumero.setFont(new Font("SansSerif", 1, 11));
    txtNumero.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtNumero_keyPressed(e);
        }
        public void keyReleased(KeyEvent e)
        {
          txtNumero_keyReleased(e);
        }
      });
    lblDescripcion.setText(" ");
    lblDescripcion.setFont(new Font("SansSerif", 0, 11));
    lblCodigo.setText(" ");
    lblCodigo.setFont(new Font("SansSerif", 0, 11));
    lblF11.setText("[ ENTER / F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(95, 250, 150, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(255, 250, 85, 20));
    btnNumero.setText("Número a Recargar :");
    btnNumero.setForeground(Color.black);
    btnNumero.setMnemonic('n');
    btnNumero.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNumero_actionPerformed(e);
        }
      });
    btnMonto.setText("Monto a Recargar : S/.");
    btnMonto.setForeground(Color.black);
    btnMonto.setMnemonic('m');
    btnMonto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnMonto_actionPerformed(e);
        }
      });
    pnlDetalleProducto.add(btnMonto);
    pnlDetalleProducto.add(btnNumero);
    pnlDetalleProducto.add(txtMonto);
    pnlDetalleProducto.add(lblDescripcionT);
    pnlDetalleProducto.add(lblCodigoT);
    pnlDetalleProducto.add(lblLaboratorio);
    pnlDetalleProducto.add(lblLaboratorioT);
    pnlDetalleProducto.add(txtNumero);
    pnlDetalleProducto.add(lblDescripcion);
    pnlDetalleProducto.add(lblCodigo);
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
  }

// **************************************************************************
// Métodos de inicialización
// **************************************************************************
  private void obtieneInfoProdVirtual()
  {
    ArrayList myArray = new ArrayList();
    try
    {
      DBVentas.obtieneInfoProductoVirtual(myArray, VariablesVentas.vCod_Prod);
    } catch(SQLException sql)
    {
      myArray.clear();
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al obtener informacion del producto virtual. \n" + sql.getMessage(),txtNumero);
    } finally
    {
      if(myArray.size() == 1)
      {
        VariablesVentas.vMontoMinRecarga = Integer.parseInt(FarmaUtility.getValueFieldArrayList(myArray,0,0));
        VariablesVentas.vMontoMaxRecarga = Integer.parseInt(FarmaUtility.getValueFieldArrayList(myArray,0,1));
        VariablesVentas.vTipoProductoRecarga = FarmaUtility.getValueFieldArrayList(myArray,0,2);
      } else
      {
        VariablesVentas.vMontoMinRecarga = 0;
        VariablesVentas.vMontoMaxRecarga = 0;
        VariablesVentas.vTipoProductoRecarga = "";
        FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto Virtual", null);
        cerrarVentana(false);
      }
    }
  }
  
  private void muestraInfoDetalleProd()
  {
    obtieneInfoProdVirtual();
    lblCodigo.setText(VariablesVentas.vCod_Prod);
    lblDescripcion.setText(VariablesVentas.vDesc_Prod);
    lblLaboratorio.setText(VariablesVentas.vNom_Lab);
    txtNumero.setText(VariablesVentas.vNumeroARecargar);
    txtMonto.setText("" + recargarInic);
  }

// **************************************************************************
// Metodos de eventos
// **************************************************************************
  private void txtMonto_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      aceptaCantidadIngresada();
    } else chkKeyPressed(e); 
  }

  private void txtNumero_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtMonto);
    } else chkKeyPressed(e); 
  }

  private void txtNumero_keyReleased(KeyEvent e)
  {
    if(VariablesVentas.vTipoProductoRecarga.equals("DTV"))
    {
      if(txtNumero.getText().trim().length() > max_long_DTV){
          //System.out.println("nuevo " + cadena.substring(0,maxima_longitud - 1));
          txtNumero.setText(txtNumero.getText().trim().substring(0,max_long_DTV).trim());
       }     
    }
    else if(isNumeroLima(txtNumero.getText().trim()))
     {
       if(txtNumero.getText().trim().length() > max_long_LIM){
           //System.out.println("nuevo " + cadena.substring(0,maxima_longitud - 1));
           txtNumero.setText(txtNumero.getText().trim().substring(0,max_long_LIM).trim());
        }     
     }
     else
     {
       if(txtNumero.getText().trim().length() > max_long_PRV){
           //System.out.println("nuevo " + cadena.substring(0,maxima_longitud - 1));
           txtNumero.setText(txtNumero.getText().trim().substring(0,max_long_PRV).trim());
        }          
       
     }


     
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    recargarInic = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar_Temp));
    muestraInfoDetalleProd();
    //FarmaUtility.moveFocus(txtMonto);
    formateaLogitudTxtNumero();
    FarmaUtility.moveFocus(txtNumero);
  }
  private void btnNumero_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNumero);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void btnMonto_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtMonto);
  }

// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    } else if(e.getKeyCode() == KeyEvent.VK_F11)
    {
      aceptaCantidadIngresada();
    }
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

// **************************************************************************
// Metodos de lógica de negocio
// **************************************************************************

  private boolean validaNumeroRecargar()
  {
    boolean valor = false;
    String numeroRecarga = txtNumero.getText().trim();
    int long_maxima =0;
    
    if(VariablesVentas.vTipoProductoRecarga.equals("DTV"))
    {
      long_maxima = max_long_DTV;
    }
    else if(isNumeroLima(numeroRecarga))
    {
      long_maxima = max_long_LIM;
    }
    else
      long_maxima = max_long_PRV;
        
    if(FarmaUtility.isDouble(numeroRecarga) && Double.parseDouble(numeroRecarga) > 0 && numeroRecarga.length() == long_maxima)
      valor = true;
    return valor;
  }

  private boolean validaMontoRecargar()
  {
    boolean valor = true;
    String montoRecarga = txtMonto.getText().trim();
    if(!FarmaUtility.isInteger(montoRecarga) || FarmaUtility.getDecimalNumber(montoRecarga)  <= 0){
      valor = false;
      FarmaUtility.showMessage(this, "Ingrese un monto a recargar correcto.",txtMonto);
      return valor;
    }
    if( Integer.parseInt(montoRecarga) < VariablesVentas.vMontoMinRecarga || Integer.parseInt(montoRecarga) > VariablesVentas.vMontoMaxRecarga){
      valor = false;
      FarmaUtility.showMessage(this, "El monto a recargar debe ser entre " + VariablesVentas.vMontoMinRecarga + " y " + VariablesVentas.vMontoMaxRecarga + " para este proveedor. Verifique!!!",txtMonto);
      return valor;
    }
    return valor;
  }

  private void aceptaCantidadIngresada()
  { 
    //SE DESCOMENTO PARA QUE VALIDE EL NUMERO
    //30.10.2007   
    if(!validaNumeroRecargar())
    {
      FarmaUtility.showMessage(this, "Ingrese un número a recargar correcto.",txtNumero);
      return;
    }

    if(!validaMontoRecargar())
    {
      return;
    }
    // Se cambio la cantidad ingresada sera el monto q coloque
    // 31.10.2007     modificacion
    VariablesVentas.vCant_Ingresada  = txtMonto.getText().trim();//"1";    
    VariablesVentas.vNumeroARecargar = txtNumero.getText().trim();
    VariablesVentas.vMontoARecargar  = txtMonto.getText().trim();
    cerrarVentana(true);
  }
  

  /**
   *  Se formatea la longitud maxima  del numero de recarga virtual
   *  @author  
   *  @since  26.03.2008
   */
  private void formateaLogitudTxtNumero()
  { 
    //txtNumero.setDocument(new FarmaLengthText(getMaxLongitud()));
    max_long_LIM = getMaxLongitud("LIM");
    max_long_PRV = getMaxLongitud("PROV");
    max_long_DTV = getMaxLongitud("DTV");
    
    if(max_long_LIM >= max_long_PRV)
    {
      if (max_long_LIM >= max_long_DTV)
        txtNumero.setDocument(new FarmaLengthText(max_long_LIM));
      else
        txtNumero.setDocument(new FarmaLengthText(max_long_DTV));
    }    
    else 
    {
      if (max_long_PRV >= max_long_DTV)
        txtNumero.setDocument(new FarmaLengthText(max_long_PRV));
      else
        txtNumero.setDocument(new FarmaLengthText(max_long_DTV));
    }
    
  }

  private int getMaxLongitud(String tipo_numero)
  {
    String max_long = "";
    try
    {
       max_long = DBVentas.obtieneMaxLongNumTelf();

    }catch(Exception e)
    {
       max_long = "10,10,12";
       System.out.println(e.getMessage());
    }
    String[] ar = max_long.split(",");
    if(tipo_numero.equalsIgnoreCase("LIM"))
    {
      max_long = ar[0];
    }
    else if(tipo_numero.equalsIgnoreCase("PROV"))
    {
      max_long = ar[1];
    }
    else if(tipo_numero.equalsIgnoreCase("DTV"))
    {
      max_long = ar[2];
    }
    System.out.println(" --lon "+ tipo_numero +" " +max_long);
    
    double m = FarmaUtility.getDecimalNumber(max_long);
    
    return (int)m;
  }
  
  /**
   * Verifica si el numero es de provincia
   * @author  
   * @since  01.04.2008
   */
  private boolean isNumeroLima(String cadena)
  {
    //System.out.println("cadena "+ cadena);
    boolean valor = false;
    if(cadena.length()>2)
    {
      //System.out.println("cadena serie "+ cadena.substring(0,2));
      String serie = cadena.substring(0,2);
      if(serie.equalsIgnoreCase("01"))
         valor = true;
    }
    
    return valor;
  }
  

}
