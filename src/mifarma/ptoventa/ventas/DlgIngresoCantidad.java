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

 
 
 
 
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

 
 
import mifarma.ptoventa.ventas.reference.UtilityVentas;
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

  /** Objeto Frame de la Aplicación */
  Frame myParentFrame;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  JPanel pnlStock = new JPanel();
   
  JLabel lblUnidades = new JLabel();
  JLabel lblStock = new JLabel();
  JLabel lblFechaHora = new JLabel();
  JLabel lblStockTexto = new JLabel();
  JPanel pnlDetalleProducto = new JPanel();
   
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
    this.setSize(new Dimension(530, 398));
    this.getContentPane().setLayout(borderLayout1);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Ingreso de Cantidad");
    this.addWindowListener(new WindowAdapter()
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
    pnlStock.setBounds(new Rectangle(15, 20, 500, 55));
    pnlStock.setFont(new Font("SansSerif", 0, 11));
    pnlStock.setBackground(new Color(255, 130, 14));
    pnlStock. setLayout(null);
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
    pnlDetalleProducto. setLayout(null);
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
    txtCantidad.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
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
    lblF11.setBounds(new Rectangle(110, 340, 135, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(255, 340, 85, 20));
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
    btnPrecioVta.setMnemonic('p');
    btnPrecioVta.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
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
        pnlStock.add(lblUnidades);
    pnlStock.add(lblStock);
    pnlStock.add(lblFechaHora);
    pnlStock.add(lblStockTexto);
        pnlDetalleProducto.add(lblPrecioProdCamp);
        pnlDetalleProducto.add(lblProdCupon);
        pnlDetalleProducto.add(T_lblPrecVtaConv);
        pnlDetalleProducto.add(lblPrecVtaConv);


           btnPrecioVta.setVisible(true);
           pnlDetalleProducto.add(btnPrecioVta);
        

        pnlDetalleProducto.add(btnCantidad);
        pnlDetalleProducto.add(lblUnidadT);
    pnlDetalleProducto.add(lblDescripcionT);
    pnlDetalleProducto.add(lblCodigoT);
    pnlDetalleProducto.add(lblLaboratorio);
        pnlDetalleProducto.add(lblDcto);
        pnlDetalleProducto.add(lblLaboratorioT);
        pnlDetalleProducto.add(lblDscto);
        pnlDetalleProducto.add(txtCantidad);
    pnlDetalleProducto.add(lblUnidad);
    pnlDetalleProducto.add(lblDescripcion);
    pnlDetalleProducto.add(lblCodigo);
        pnlDetalleProducto.add(lblMensajeCampaña);

        
        
        
       
          T_lblPrecVtaConv.setVisible(false);
          pnlDetalleProducto.add(txtPrecioVentaRedondeado);
          pnlDetalleProducto.add(txtPrecioVenta);


       





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
  private void initialize()
  {
    FarmaVariables.vAceptar = false;
  }

// **************************************************************************
// Métodos de inicialización
// **************************************************************************
  private void obtieneInfoProdEnArrayList(ArrayList pArrayList)
  {
    try
    {
      //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
      if(VariablesVentas.vEsPedidoConvenio)
      {
        DBVentas.obtieneInfoDetalleProducto(pArrayList, VariablesVentas.vCod_Prod);
      }else
      {
        DBVentas.obtieneInfoDetalleProductoVta(pArrayList, VariablesVentas.vCod_Prod);
      }
      
    } catch(SQLException sql)
    {
      log.error(null,sql.fillInStackTrace());
      FarmaUtility.showMessage(this,"Error al obtener informacion del producto. \n" + sql.getMessage(),txtCantidad);
    }
  }

  private void muestraInfoDetalleProd()
  {
    ArrayList myArray = new ArrayList();
    obtieneInfoProdEnArrayList(myArray);
    if(myArray.size() == 1)
    {
      VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesVentas.vStk_Prod_Fecha_Actual = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
        if((!VariablesVentas.vEsPedidoDelivery && !VariablesVentas.vEsPedidoInstitucional) || !VariablesVentas.vIngresaCant_ResumenPed){
           
         //  11/04/08 no se actualiza el precio y descuento si es producto  oferta
         //if(!VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)||!VariablesVentas.vEsProdOferta)

         // Segun gerencia se debe seguir la misma logica para todos los productos.
                if (VariablesVentas.vVentanaListadoProductos) {
                    System.out.println("SETEANDO DESCUENTO");
                    VariablesVentas.vVal_Prec_Vta = 
                            ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                    VariablesVentas.vPorc_Dcto_1 = 
                            ((String)((ArrayList)myArray.get(0)).get(6)).trim();

                } else {
                    if (UtilityVentas.isAplicoPrecioCampanaCupon(lblCodigo.getText().trim(),FarmaConstants.INDICADOR_S)) {
                        if (!VariablesVentas.vVentanaOferta) {
                            System.out.println("SETEANDO DESCUENTO");
                            VariablesVentas.vVal_Prec_Vta = 
                                    ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                            VariablesVentas.vPorc_Dcto_1 = 
                                    ((String)((ArrayList)myArray.get(0)).get(6)).trim();
                        }
                    }
                }

                System.err.println("DlgIngresoCantidad: VariablesVentas.vPorc_Dcto_1 - " +
                                   VariablesVentas.vPorc_Dcto_1);
                System.out.println("VariablesVentas.vPorc_Dcto_2 : " +
                                   VariablesVentas.vPorc_Dcto_2);
        }
      VariablesVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
      VariablesVentas.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
      VariablesVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
    } else
    {
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
    txtPrecioVenta.setText(VariablesVentas.vVal_Prec_Vta);//  29102009 se cambio txtPrecioVenta por txtPrecioVentaOculto
    lblDcto.setText(VariablesVentas.vPorc_Dcto_1);
    txtCantidad.setText("" + cantInic);    
    //  29102009 inicio
    try{
       //double precVtaRedondeadoNum = DBVentas.getPrecioRedondeado(Double.parseDouble(VariablesVentas.vVal_Prec_Vta));  antes
       double precVtaRedondeadoNum = DBVentas.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta)); // , 18.06.2010
       String precVtaRedondeadoStr = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(""+precVtaRedondeadoNum),3) ; 
            
       this.txtPrecioVentaRedondeado.setText(precVtaRedondeadoStr);               
    }  
    catch(SQLException sql){
       sql.printStackTrace(); 
    }
    //  29102009 fin
    
    if(!VariablesVentas.vEsPedidoConvenio || VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER))
    {
      T_lblPrecVtaConv.setVisible(false);
      lblPrecVtaConv.setVisible(false);
    }
    
    
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
      aceptaCantidadIngresada();
    }
    
  }

  private void this_windowOpened(WindowEvent e)
  {
      System.out.println("VariablesVentas.vVal_Prec_Vta;"+VariablesVentas.vVal_Prec_Vta);
    FarmaUtility.centrarVentana(this);
    this.setLocation(this.getX(),this.getY()-75);
      VariablesVentas.vCant_Ingresada_Temp = "0";
    cantInic = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesVentas.vCant_Ingresada_Temp));

    
    muestraInfoDetalleProd();
       
    evaluaTipoPedido();
    //  17.04.08 
    lblDscto.setVisible(false);
    lblDcto.setVisible(false);
    
    /*if(!VariablesVentas.vEsPedidoDelivery && !VariablesVentas.vEsPedidoInstitucional)
        FarmaUtility.moveFocus(txtCantidad);
    else
        FarmaUtility.moveFocus(txtPrecioVenta);        */
    muestraMaxProdCupon();
    calculoNuevoPrecio();
    

    ///---
    if(isExisteProdCampana(VariablesVentas.vCod_Prod)){
        lblMensajeCampaña.setVisible(true);
    }
    else
        lblMensajeCampaña.setVisible(false);
      ///---
 
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(txtCantidad);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void btnPrecioVta_actionPerformed(ActionEvent e)
  {
    if(VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional)
      FarmaUtility.moveFocus(txtPrecioVenta);
  }

// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
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

  private boolean validaCantidadIngreso()
  {
    boolean valor = false;
    String cantIngreso = txtCantidad.getText().trim();
    if(FarmaUtility.isInteger(cantIngreso) && Integer.parseInt(cantIngreso) > 0) valor = true;
    return valor;
  }

  private boolean validaPrecioIngreso()
  {
    boolean valor = false;
    String precioIngreso = txtPrecioVenta.getText().trim();
    if(FarmaUtility.isDouble(precioIngreso) && FarmaUtility.getDecimalNumber(precioIngreso)  > 0) valor = true;
    return valor;
  }

  private boolean validaStockActual()
  {
    boolean valor = false;
    obtieneStockProducto();
    String cantIngreso = txtCantidad.getText().trim();
    if((Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic) >= Integer.parseInt(cantIngreso)) valor = true;
    return valor;
  }

  private void aceptaCantidadIngresada()
  {
    VariablesVentas.vIndAplicaPrecNuevoCampanaCupon = FarmaConstants.INDICADOR_N;
      
    if(!validaCantidadIngreso())
    {
      FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.",txtCantidad);
      return;
    }
    if(!validaStockActual())
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(this, "La cantidad ingresada no puede ser mayor al Stock del Producto.",txtCantidad);
      lblStock.setText("" + (Integer.parseInt(VariablesVentas.vStk_Prod) + cantInic));
      return;
    }
    VariablesVentas.vCant_Ingresada = txtCantidad.getText().trim();
    if((VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional) && !validaPrecioIngreso())
    {
      FarmaUtility.showMessage(this, "Ingrese un precio correcto.",txtPrecioVenta);
      return;
    }
    //VariablesVentas.vVal_Prec_Vta = txtPrecioVenta.getText().trim();

    VariablesVentas.vVal_Prec_Vta = getAnalizaPrecio(txtPrecioVenta.getText().trim(),pPrecioFidelizacion);
     
    
    if(VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional)
      calculoNuevoDescuento();
      
    if(VariablesVentas.vIndAplicaPrecNuevoCampanaCupon.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){        
        VariablesVentas.vListaProdAplicoPrecioDescuento.add(lblCodigo.getText().trim());
    }
    
    cerrarVentana(true);
  }

  private void obtieneStockProducto_ForUpdate(ArrayList pArrayList)
  {
    try
    {
      DBVentas.obtieneStockProducto_ForUpdate(pArrayList, VariablesVentas.vCod_Prod,
                                              VariablesVentas.vVal_Frac);
      FarmaUtility.liberarTransaccion();
      //quitar bloqueo de stock fisico 
      //  13.10.2011  
    } catch(SQLException sql)
    {
        FarmaUtility.liberarTransaccion();
        //quitar bloqueo de stock fisico 
        //  13.10.2011    
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Error al obtener stock del producto. \n" + sql.getMessage(),txtCantidad);
    }
  }

  private void obtieneStockProducto()
  {
    ArrayList myArray = new ArrayList();
    obtieneStockProducto_ForUpdate(myArray);
    if(myArray.size() == 1)
    {
      VariablesVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      VariablesVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      System.err.println("DlgIngresoCantidad : VariablesVentas.vPorc_Dcto_1 (2) - "+VariablesVentas.vPorc_Dcto_1);
    } else
    {
      FarmaUtility.showMessage(this, "Error al obtener Stock del Producto", null);
      cerrarVentana(false);
    }
  }

  private void evaluaTipoPedido()
  {
    if(!VariablesVentas.vEsPedidoDelivery && !VariablesVentas.vEsPedidoInstitucional)
    {
      /*if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) && FarmaVariables.vIndHabilitado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {        
      }*/
      txtPrecioVenta.setEnabled(false);
      FarmaUtility.moveFocus(txtCantidad);
    } else if (VariablesVentas.vEsPedidoDelivery || VariablesVentas.vEsPedidoInstitucional)
    { 
      if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) && FarmaVariables.vIndHabilitado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
        txtPrecioVenta.setEnabled(true);
        FarmaUtility.moveFocus(txtPrecioVenta);
      } else 
      {
        txtPrecioVenta.setEnabled(false);  
        FarmaUtility.moveFocus(txtCantidad);
      }
    }
  }
  
  private void calculoNuevoDescuento()
  {
    double precioLista = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Lista);
    double precioVenta = FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Prec_Vta);
    double porcDcto = ( 1 - ( precioVenta / precioLista ) );
    VariablesVentas.vPorc_Dcto_1 = FarmaUtility.formatNumber(porcDcto,2);
  }

  private void muestraMaxProdCupon()
  {
    String vCodCamp;
    String vIndPordCamp;
    String vIndTipoCupon;
    double vCantProdMax;
    //ArrayList cupon = new ArrayList();
    Map mapaCupon = new HashMap();
    
    lblProdCupon.setVisible(false);
    System.out.println("Ingreso cantidad "+ VariablesVentas.vArrayList_Cupones);
    try
    {
      for(int j=0;j<VariablesVentas.vArrayList_Cupones.size();j++)
      {
    	  mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(j);
        vCodCamp = mapaCupon.get("COD_CAMP_CUPON").toString();//cupon.get(1).toString();
        vIndTipoCupon = mapaCupon.get("TIP_CUPON").toString();//cupon.get(2).toString();
        vCantProdMax = FarmaUtility.getDecimalNumber(mapaCupon.get("UNID_MAX_PROD").toString());//FarmaUtility.getDecimalNumber(cupon.get(6).toString());
        vIndPordCamp = DBVentas.verificaProdCamp(vCodCamp,VariablesVentas.vCod_Prod);
        if(vIndPordCamp.equals(FarmaConstants.INDICADOR_S))
        {
          if(vIndTipoCupon.equalsIgnoreCase("P"))
          {
            lblProdCupon.setText("Máximo "+vCantProdMax+" unidades para aplicar el descuento.");
            lblProdCupon.setVisible(true);
          }
          break;
        }
      }
    }catch(SQLException e)
    {
      log.error(null,e);
    }
  }
  
  private boolean isCampanaFidelizacion(String pCodCupon){
      int i = pCodCupon.trim().indexOf("F");
      if(i==-1)
          return false;
      return true;
  }
  
  /**
   * corregir este metodo ya que en su momento  
   * hizo la logicade mostrar el primero encontrado 
   ***/
  public void calculoNuevoPrecio(){
      String vCodCamp;
      String vNvoPrecio;
      String vIndTipoCupon;
      double vNvoPrecioRedondeado; //  29102009
      //ArrayList cupon = new ArrayList();
      Map mapaCupon = new HashMap();
      //boolean vIndFidelizacion =  false;
      String vIndFidelizacion =  "N";
      pPrecioFidelizacion = 0.0;
      lblPrecioProdCamp.setVisible(false);
      
      String vPrecioVenta = "";
      log.debug("VariablesVentas.vArrayList_Cupones:"+VariablesVentas.vArrayList_Cupones);
      try
      {
        for(int j=0;j<VariablesVentas.vArrayList_Cupones.size();j++)
        {
            mapaCupon = (Map)VariablesVentas.vArrayList_Cupones.get(j);
            vCodCamp = mapaCupon.get("COD_CAMP_CUPON").toString();//cupon.get(1).toString();
            vIndTipoCupon = mapaCupon.get("TIP_CUPON").toString();//cupon.get(2).toString();          
            vIndFidelizacion = mapaCupon.get("IND_FID").toString(); /*false;
                                                                     * 
            														vIndFidelizacion = isCampanaFidelizacion(cupon.get(0).toString());*/  
            vPrecioVenta = txtPrecioVenta.getText().trim();
            log.debug("vIndFidelizacion:"+vIndFidelizacion);
            if( vIndFidelizacion.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){
                vNvoPrecio = 
                        DBVentas.getNuevoPrecio(VariablesVentas.vCod_Prod,vCodCamp,vPrecioVenta);
               // vNvoPrecioRedondeado= DBVentas.getPrecioRedondeado(Double.parseDouble(vNvoPrecio.trim())); //  29102009
                if (!vNvoPrecio.equals(FarmaConstants.INDICADOR_N)) {
                     vNvoPrecioRedondeado= DBVentas.getPrecioRedondeado(Double.parseDouble(vNvoPrecio.trim())); //  29102009
                    if (vIndTipoCupon.equalsIgnoreCase("P")) {
                        pPrecioFidelizacion = Double.parseDouble(vNvoPrecio.trim());
                        log.debug("  pPrecioFidelizacion: sin redondeo "+pPrecioFidelizacion);
                        lblPrecioProdCamp.setText("Prec. Fidelizado. : S/. " + vNvoPrecioRedondeado);//  29102009 se cambio vNvoPrecio por vNvoPrecioRedondeado
                        lblPrecioProdCamp.setVisible(true);
                    }
                    break;
                 }
            }
        }
      }catch(SQLException e)
      {
        log.error(null,e);
      }      
  }
  /*boolean vIndFidelizacion;
  // [FA0001, A0001, P, 10, 10, 0, 1, 100, N, 0000000100000001P 99990.000],
  vIndFidelizacion = isCampanaFidelizacion(cupon.get(0));
   * */
  private String getAnalizaPrecio(String pPrecioVenta,double pNvoPrecioFid){
      String pResultado = "";
      
      //  se quito las comas del precio de los productos
      pPrecioVenta = pPrecioVenta.replaceAll(",","");
      
      double pPrecio = Double.parseDouble(pPrecioVenta.trim());
      System.out.println("pPrecio:"+pPrecio);
      System.out.println("pNvoPrecioFid:"+pNvoPrecioFid);
      System.out.println("VariablesVentas.vIndAplicaPrecNuevoCampanaCupon:"+VariablesVentas.vIndAplicaPrecNuevoCampanaCupon);
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
            }
            else{
                pResultado = "" + pPrecio;
                
            }
        } else
            pResultado = "" + pPrecioVenta;
      pResultado = pResultado.trim();
        System.out.println("pResultado:"+pResultado);
        
        return pResultado;
  }
  
  
    private boolean isExisteProdCampana(String pCodProd){
        //lblMensajeCampaña.setVisible(true);
        String pRespta = "N";
        try
        {   
            lblMensajeCampaña.setText("");            
            pRespta = DBVentas.existeProdEnCampañaAcumulada(pCodProd,VariablesFidelizacion.vDniCliente);           
            if(pRespta.trim().equalsIgnoreCase("E") )
            lblMensajeCampaña.setText("    Cliente ya está inscrito en campaña Acumula tu Compra y Gana");
            
            if(pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                lblMensajeCampaña.setText("  Producto se encuentra en la campaña \" Acumula tu Compra y Gana\"");
        }catch(SQLException e)
        {
          log.error(null,e);
            pRespta = "N";
        } 
        
        if(pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;
        
        return true;
    }  


}
