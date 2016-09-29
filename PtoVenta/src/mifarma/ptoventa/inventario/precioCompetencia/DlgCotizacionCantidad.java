package mifarma.ptoventa.inventario.precioCompetencia;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import mifarma.common.*;

import mifarma.ptoventa.inventario.precioCompetencia.reference.ConstantsPrecioCompetencia;
import mifarma.ptoventa.inventario.precioCompetencia.reference.UtilityPrecioCompetencia;
import mifarma.ptoventa.inventario.precioCompetencia.reference.VariablesPrecioCompetencia;
import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgCotizacionCantidad extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgCotizacionCantidad.class);

  Frame myParentFrame;
  private boolean productoFraccionado = false;
  
  private String descUnidPres = "";
  private String stkProd = "";
  private String valFracProd = "";
  private String descUnidVta = "";
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JLabelWhite lblDescripcion_T = new JLabelWhite();
  private JLabelWhite lblStockActual_T = new JLabelWhite();
  private JLabelWhite lblLaboratorio_T = new JLabelWhite();
  private JLabelWhite lblStockActual = new JLabelWhite();
  private JLabelWhite lblLaboratorio = new JLabelWhite();
  private JLabelWhite lblDescripcion = new JLabelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtPrecioUnitario = new JTextFieldSanSerif();
  private JLabelWhite lblPrecioUnitario_T = new JLabelWhite();
  private JTextFieldSanSerif txtImagen = new JTextFieldSanSerif();
  private JLabelWhite lblImagen = new JLabelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JTextFieldSanSerif txtFraccion = 
    new JTextFieldSanSerif();
    private JLabelOrange lblValorFrac_T = new JLabelOrange();
  private JLabelOrange lblValorFrac = new JLabelOrange();
  private JLabelOrange lblUnidPresent_T = new JLabelOrange();
  private JLabelOrange lblUnidVenta_T = new JLabelOrange();
  private JLabelOrange lblUnidPresent = new JLabelOrange();
  private JLabelOrange lblUnidVenta = new JLabelOrange();
  private JButton btnImagen = new JButton();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgCotizacionCantidad()
  {
    this(null, "", false);
  }

  public DlgCotizacionCantidad(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      FarmaUtility.centrarVentana(this);
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(428, 335));
    this.getContentPane().setLayout(null);
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingrese Cantidad Solicitada");
    this.setDefaultCloseOperation(0);
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
    pnlHeader1.setBounds(new Rectangle(10, 10, 400, 150));
    pnlHeader1.setBackground(Color.white);
    pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    lblDescripcion_T.setText("Descripción:");
    lblDescripcion_T.setBounds(new Rectangle(10, 15, 75, 15));
    lblDescripcion_T.setForeground(new Color(255, 130, 14));
    lblStockActual_T.setText("Stock Actual:");
    lblStockActual_T.setBounds(new Rectangle(10, 90, 75, 15));
    lblStockActual_T.setForeground(new Color(255, 130, 14));
    lblLaboratorio_T.setText("Laboratorio:");
    lblLaboratorio_T.setBounds(new Rectangle(10, 65, 70, 15));
    lblLaboratorio_T.setForeground(new Color(255, 130, 14));
    lblStockActual.setBounds(new Rectangle(90, 90, 55, 15));
    lblStockActual.setFont(new Font("SansSerif", 0, 11));
    lblStockActual.setForeground(new Color(255, 130, 14));
    lblLaboratorio.setBounds(new Rectangle(90, 65, 280, 15));
    lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
    lblLaboratorio.setForeground(new Color(255, 130, 14));
    lblDescripcion.setBounds(new Rectangle(90, 15, 280, 15));
    lblDescripcion.setFont(new Font("SansSerif", 0, 11));
    lblDescripcion.setForeground(new Color(255, 130, 14));
    pnlTitle1.setBounds(new Rectangle(10, 175, 400, 85));
    pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    pnlTitle1.setBackground(Color.white);
        txtEntero.setBounds(new Rectangle(15, 40, 60, 20));
    txtEntero.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
            txtEntero_keyPressed(e);
          }

        public void keyTyped(KeyEvent e)
        {
          txtEntero_keyTyped(e);
        }
      });
    txtEntero.setVisible(false);
    txtPrecioUnitario.setBounds(new Rectangle(55, 40, 70, 20));
    txtPrecioUnitario.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtPrecioUnitario_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtPrecioUnitario_keyTyped(e);
        }
      });
    lblPrecioUnitario_T.setText("Precio Entero");
    lblPrecioUnitario_T.setBounds(new Rectangle(55, 20, 90, 15));
    lblPrecioUnitario_T.setForeground(new Color(255, 130, 14));
    txtImagen.setBounds(new Rectangle(175, 40, 150, 20));
    txtImagen.setLengthText(15);
    txtImagen.setEditable(false);
    txtImagen.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtImagen_keyPressed(e);
        }
      });
        lblImagen.setText("Adjuntar Imagen");
        lblImagen.setBounds(new Rectangle(175, 20, 110, 15));
        lblImagen.setForeground(new Color(255, 130, 14));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(325, 275, 85, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(215, 275, 105, 20));
    txtFraccion.setBounds(new Rectangle(85, 40, 60, 20));
    txtFraccion.setLengthText(6);
    txtFraccion.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtFraccion_keyPressed(e);
          }

          public void keyTyped(KeyEvent e)
          {
            txtFraccion_keyTyped(e);
          }
        });
      txtFraccion.setVisible(false);
        lblValorFrac_T.setText("Valor Frac:");
    lblValorFrac_T.setBounds(new Rectangle(10, 115, 70, 15));
    lblValorFrac.setBounds(new Rectangle(90, 115, 55, 15));
    lblValorFrac.setFont(new Font("SansSerif", 0, 11));
    lblUnidPresent_T.setText("Presentacion:");
    lblUnidPresent_T.setBounds(new Rectangle(170, 40, 79, 15));
    lblUnidVenta_T.setText("Unid. Venta:");
    lblUnidVenta_T.setBounds(new Rectangle(170, 90, 79, 15));
    lblUnidPresent.setBounds(new Rectangle(260, 40, 125, 15));
    lblUnidPresent.setFont(new Font("SansSerif", 0, 11));
    lblUnidVenta.setBounds(new Rectangle(260, 90, 125, 15));
    lblUnidVenta.setFont(new Font("SansSerif", 0, 11));
    this.btnImagen.setText("...");
    this.btnImagen.setBounds(new Rectangle(330, 40, 35, 20));
        btnImagen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnImagen_actionPerformed(e);
                }
            });
        btnImagen.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnImagen_keyPressed(e);
                }
            });
        pnlHeader1.add(lblUnidVenta, null);
    pnlHeader1.add(lblUnidPresent, null);
    pnlHeader1.add(lblUnidVenta_T, null);
    pnlHeader1.add(lblUnidPresent_T, null);
    pnlHeader1.add(lblValorFrac, null);
    pnlHeader1.add(lblValorFrac_T, null);
    pnlHeader1.add(lblDescripcion, null);
    pnlHeader1.add(lblLaboratorio, null);
    pnlHeader1.add(lblStockActual, null);
    pnlHeader1.add(lblLaboratorio_T, null);
    pnlHeader1.add(lblStockActual_T, null);
    pnlHeader1.add(lblDescripcion_T, null);
    pnlTitle1.add(btnImagen, null);
        pnlTitle1.add(txtFraccion, null);
        pnlTitle1.add(lblImagen, null);
        pnlTitle1.add(txtImagen, null);
        pnlTitle1.add(lblPrecioUnitario_T, null);
        pnlTitle1.add(txtPrecioUnitario, null);
        pnlTitle1.add(txtEntero, null);
        jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    //
    txtEntero.setLengthText(6);
    txtPrecioUnitario.setLengthText(9);//<100000.000
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    initCabecera();
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initCabecera()
  {
    muestraInfoProd();
    
    lblDescripcion.setText(VariablesPrecioCompetencia.vNomProd);
    try
    {
      lblStockActual.setText(DBInventario.getStkDisponibleProd(VariablesPrecioCompetencia.vCodProd));
    }catch(SQLException sql)
    {
      lblStockActual.setText(VariablesPrecioCompetencia.vStkFisico);  
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener el stock disponible del producto : \n" + sql.getMessage(),txtEntero);
    }
    
    lblLaboratorio.setText(VariablesPrecioCompetencia.vNomLab);
    lblValorFrac.setText(VariablesPrecioCompetencia.vValFrac);
    
    if(!VariablesPrecioCompetencia.vValFrac.equals("1"))
      productoFraccionado = true;
      
    if(productoFraccionado)
    {
      int cant = 0;
      if(!VariablesPrecioCompetencia.vCant.trim().equals(""))
      {
        cant= Integer.parseInt(VariablesPrecioCompetencia.vCant.trim());
      }
      int frac = Integer.parseInt(VariablesPrecioCompetencia.vValFrac.trim());
      
      int valFrac = cant%frac;
      int valCant = cant/frac;
      txtEntero.setText(valCant+"");
      txtFraccion.setText(valFrac+"");
    }else
    {
      txtEntero.setText(VariablesPrecioCompetencia.vCant);
      txtFraccion.setText("0");
      txtFraccion.setEditable(false); 
    }
    
    txtImagen.setText(VariablesPrecioCompetencia.vImagen);

    
    if(VariablesPrecioCompetencia.vPrecUnit.trim().equals(""))
    {
     txtPrecioUnitario.setText("0.00");
    }
    else
    {
    txtPrecioUnitario.setText(VariablesPrecioCompetencia.vPrecUnit);
  }
   
      FarmaUtility.moveFocus(txtPrecioUnitario);
      
  }
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void btnEntero_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtEntero);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    if(!productoFraccionado)
    {
      txtFraccion.setEditable(false);
    }
    FarmaUtility.moveFocus(txtEntero);
    
    if (VariablesPrecioCompetencia.vCodTipoCotizacion.equals(ConstantsPrecioCompetencia.TIPO_COTIZACION_NOVENDE)){
      cargarDatos();
      cerrarVentana(true);
    }
  }

  private void txtEntero_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(!txtFraccion.isEditable())
      {
        FarmaUtility.moveFocus(txtPrecioUnitario);
      }else
      {
        FarmaUtility.moveFocus(txtFraccion);
      }
    }
    else
      chkKeyPressed(e);  
  }

  private void txtEntero_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtEntero,e);
  }
  
  private void txtFraccion_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtPrecioUnitario);
    }
    else
      chkKeyPressed(e); 
  }
  
  private void txtFraccion_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtFraccion,e);
  }
  
  private void txtImagen_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      
    }
    else
      chkKeyPressed(e);
  }

  private void txtPrecioUnitario_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(btnImagen);
    else
      chkKeyPressed(e);    
  }
    
  private void txtPrecioUnitario_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitosDecimales(txtPrecioUnitario,e);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(UtilityPtoVenta.verificaVK_F11(e))
    {
      aceptaCantidadIngresada();      
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private boolean validarCampos()
  {
    boolean retorno = true;
    if(txtEntero.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar la Cantidad.",txtEntero);
      retorno = false;
    }else if(txtFraccion.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar la fraccion.",txtFraccion);
      retorno = false;
    }else if (VariablesPrecioCompetencia.vCodTipoCotizacion.equals(ConstantsPrecioCompetencia.TIPO_COTIZACION_CONPRECIO) && 
              Double.parseDouble(txtFraccion.getText().trim()) == 0 && 
              Double.parseDouble(txtEntero.getText().trim()) == 0){
      FarmaUtility.showMessage(this,"La Cantidad y Fracción no deben ser cero a la vez .",txtEntero);
      retorno = false;
    }else if(txtPrecioUnitario.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar el Precio Unitario.",txtPrecioUnitario);
      retorno = false;
    }else if(!FarmaUtility.validateDecimal(this,txtPrecioUnitario,"Corrija el Precio Unitario.",false))
      retorno = false;
    else if(Double.parseDouble(txtPrecioUnitario.getText().trim()) >= 100000.000)
    {
      FarmaUtility.showMessage(this,"El Precio Unitario, no debe ser mayor de 99,999.999 .",txtPrecioUnitario);
      retorno = false;
    }else if (VariablesPrecioCompetencia.vCodTipoCotizacion.equals(ConstantsPrecioCompetencia.TIPO_COTIZACION_CONPRECIO) 
              && Double.parseDouble(txtPrecioUnitario.getText().trim()) <= 0){
            FarmaUtility.showMessage(this,"El Precio Unitario, no debe ser cero .",txtPrecioUnitario);
            retorno = false;
    }else{
        VariablesPrecioCompetencia.vCantIngreso = 1;
        double totalEntero = VariablesPrecioCompetencia.vCantIngreso*FarmaUtility.getDecimalNumber(txtPrecioUnitario.getText().trim());
        
        if( 
            ( VariablesPrecioCompetencia.vCodTipoCotizacion.equals(ConstantsPrecioCompetencia.TIPO_COTIZACION_CONPRECIO) 
              && !UtilityPrecioCompetencia.cumpleMargenMinimoCotizacion(totalEntero, VariablesPrecioCompetencia.vCodProd)
            )||
            ( VariablesPrecioCompetencia.vCodTipoCotizacion.equals(ConstantsPrecioCompetencia.TIPO_COTIZACION_SIN_STOCK)  
              && totalEntero > 0 
              && !UtilityPrecioCompetencia.cumpleMargenMinimoCotizacion(totalEntero, VariablesPrecioCompetencia.vCodProd)
            )
          ){
            if (!JConfirmDialog.rptaConfirmDialog(this, "Precio ingresado no válido, reconfirme precio de la competencia. El valor precio ingresado debe ser de la caja y/o frasco entero.\n\nIgual desea grabar?")){
                retorno = false;
            }
        }            
    }
    
    
    return retorno;
  }
  
  private void cargarDatos()
  {
    VariablesPrecioCompetencia.vCant = ""+VariablesPrecioCompetencia.vCantIngreso;
    VariablesPrecioCompetencia.vImagen = txtImagen.getText().trim();
    VariablesPrecioCompetencia.vPrecUnit = txtPrecioUnitario.getText().trim();
    
    int cant = Integer.parseInt(VariablesPrecioCompetencia.vCant);
    double precio = Double.parseDouble(VariablesPrecioCompetencia.vPrecUnit);
    VariablesPrecioCompetencia.vTotal = (cant*precio)+"";
  }

  private void aceptaCantidadIngresada()
  {
    txtEntero.setText("1");  
    if(validarCampos())
      {
        if(!validaCantIngreso())
        {
          FarmaUtility.showMessage(this,"La cantidad a ingresar excede el valor máximo de ingreso.",txtEntero);          
          return;
        }
        cargarDatos();
        cerrarVentana(true);  
      }
  }
  
  private boolean validaCantIngreso()
  {
    boolean valor = true;
    
    String cantidad, fraccion; 
    cantidad = txtEntero.getText().trim();
    fraccion = txtFraccion.getText().trim();
    
    if(fraccion.equals(""))
      fraccion = "0";
    
    VariablesPrecioCompetencia.vCantIngreso = 1;
        //Integer.parseInt(cantidad) * Integer.parseInt(VariablesPrecioCompetencia.vValFrac.trim()) + Integer.parseInt(fraccion);  
    
    if(VariablesPrecioCompetencia.vCantIngreso > 999999)
    {
      valor = false;
    }
    
    return valor;
  }

  private void muestraInfoProd()
  {
    
    ArrayList myArray = new ArrayList();
    obtieneInfoProdEnArrayList(myArray);
    
    if(myArray.size() == 1)
    {
      stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      descUnidVta = ((String)((ArrayList)myArray.get(0)).get(6)).trim();    
    } else
    {
      stkProd = "";
      descUnidPres = "";
      valFracProd = "";
      descUnidVta = "";      
      FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtEntero);
    }
    
    lblStockActual.setText(stkProd);    
    lblUnidPresent.setText(descUnidPres);
    VariablesPrecioCompetencia.vValFrac = valFracProd;
    //lblValorFrac.setText(valFracProd);
    lblUnidVenta.setText(descUnidVta);
  }
  
  private void obtieneInfoProdEnArrayList(ArrayList pArrayList)
  {
    String codProd = VariablesPrecioCompetencia.vCodProd;
    try
    {
      DBVentas.obtieneInfoProducto(pArrayList, codProd);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener informacion del producto en Arreglo. \n" + sql.getMessage(),txtEntero);
    }
  }
  
    /**
     * Se muestra el dialogo para seleccionar el archivo.
     */
    private void cargarArchivo() {
        File lfFile = new File("C:\\");
        JFileChooser filechooser = new JFileChooser(lfFile);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes JPG, JPEG, GIF, BMP y PNG", "jpeg", "jpg","gif","bmp","png");
        filechooser.setFileFilter(filter);
        filechooser.setDialogTitle("Seleccione imagen");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (filechooser.showSaveDialog(this.myParentFrame) != JFileChooser.APPROVE_OPTION)
            return;
        File fileChoosen = filechooser.getSelectedFile();
        String cade = String.valueOf(fileChoosen.length());
        if(fileChoosen.length() <= 512000){//500KB
            VariablesPrecioCompetencia.vFileImagen = fileChoosen;
            String ruta = fileChoosen.getAbsolutePath();
            txtImagen.setText(ruta);
        }else{
            FarmaUtility.showMessage(this,"No se cargó la imagen, excede el tamaño máximo de 500KB.",btnImagen);
            }
        
    }

    private void btnImagen_actionPerformed(ActionEvent e) {
        cargarArchivo();
    }

    private void btnImagen_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            cargarArchivo();
        else
          chkKeyPressed(e);
    }
}
