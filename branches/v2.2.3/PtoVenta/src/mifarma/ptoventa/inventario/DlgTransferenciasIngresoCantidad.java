package mifarma.ptoventa.inventario;
import java.awt.Frame;
import java.awt.Dimension;
import java.sql.*;
import java.util.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import java.awt.Rectangle;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import java.awt.Font;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import com.gs.mifarma.componentes.JButtonLabel;
import mifarma.common.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import mifarma.ptoventa.*;
import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgTransferenciasIngresoCantidad extends JDialog
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasIngresoCantidad.class);

  Frame myParentFrame;
  FarmaTableModel tableModel;
  //private boolean matriz = false;
  private int cantInic = 0;
  private boolean productoFraccionado = false;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelWhite lblStock_T = new JLabelWhite();
  private JLabelWhite lblFechaHoraActual = new JLabelWhite();
  private JLabelWhite lblUnidades_T = new JLabelWhite();
  private JLabelWhite lblUnidades = new JLabelWhite();
  private JLabelWhite lblCodigo_T = new JLabelWhite();
  private JLabelWhite lblDescripcion_T = new JLabelWhite();
  private JLabelWhite lblCodigo = new JLabelWhite();
  private JLabelWhite lblDescripcion = new JLabelWhite();
  private JLabelWhite lblUnidad = new JLabelWhite();
  private JLabelWhite lblUnidad_T = new JLabelWhite();
  private JLabelWhite lblValorFrac = new JLabelWhite();
  private JLabelWhite lblValorFrac_T = new JLabelWhite();
  private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtFechaVenc = new JTextFieldSanSerif();
  private JLabelWhite lblFechaVenc_T = new JLabelWhite();
  private JLabelWhite lblLaboratorio_T = new JLabelWhite();
  private JLabelWhite lblLaboratorio = new JLabelWhite();
  private JButtonLabel btnCantidad = new JButtonLabel();
  private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
  private JLabelWhite lblLote_T = new JLabelWhite();
  private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
  private JLabelWhite lblFraccion = new JLabelWhite();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgTransferenciasIngresoCantidad()
  {
    this(null, "", false);
  }

  public DlgTransferenciasIngresoCantidad(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(435, 296));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingreso de Cantidad");
    this.setDefaultCloseOperation(0);
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
    pnlHeader1.setBounds(new Rectangle(10, 10, 410, 40));
    pnlHeader1.setBackground(Color.white);
    pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    pnlTitle1.setBounds(new Rectangle(10, 60, 410, 170));
    pnlTitle1.setBackground(Color.white);
    pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(330, 240, 90, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(205, 240, 110, 20));
    lblStock_T.setText("Stock del Producto al:");
    lblStock_T.setBounds(new Rectangle(15, 10, 110, 15));
    lblStock_T.setFont(new Font("SansSerif", 0, 11));
    lblStock_T.setForeground(new Color(255, 130, 14));
    lblFechaHoraActual.setBounds(new Rectangle(125, 10, 100, 15));
    lblFechaHoraActual.setForeground(new Color(255, 130, 14));
    lblUnidades_T.setText("Unidades:");
    lblUnidades_T.setBounds(new Rectangle(255, 10, 55, 15));
    lblUnidades_T.setFont(new Font("SansSerif", 0, 11));
    lblUnidades_T.setForeground(new Color(255, 130, 14));
    lblUnidades.setBounds(new Rectangle(305, 10, 85, 15));
    lblUnidades.setForeground(new Color(255, 130, 14));
    lblCodigo_T.setText("Código");
    lblCodigo_T.setBounds(new Rectangle(15, 15, 70, 15));
    lblCodigo_T.setForeground(new Color(255, 130, 14));
    lblDescripcion_T.setText("Descripción");
    lblDescripcion_T.setBounds(new Rectangle(90, 15, 70, 15));
    lblDescripcion_T.setForeground(new Color(255, 130, 14));
    lblCodigo.setBounds(new Rectangle(15, 39, 70, 15));
    lblCodigo.setFont(new Font("SansSerif", 0, 11));
    lblCodigo.setForeground(new Color(255, 130, 14));
    lblDescripcion.setBounds(new Rectangle(90, 40, 285, 15));
    lblDescripcion.setFont(new Font("SansSerif", 0, 11));
    lblDescripcion.setForeground(new Color(255, 130, 14));
    lblUnidad.setBounds(new Rectangle(15, 85, 125, 15));
    lblUnidad.setFont(new Font("SansSerif", 0, 11));
    lblUnidad.setForeground(new Color(255, 130, 14));
    lblUnidad_T.setText("Unidad");
    lblUnidad_T.setBounds(new Rectangle(15, 62, 70, 15));
    lblUnidad_T.setForeground(new Color(255, 130, 14));
    lblValorFrac.setBounds(new Rectangle(145, 85, 70, 15));
    lblValorFrac.setFont(new Font("SansSerif", 0, 11));
    lblValorFrac.setForeground(new Color(255, 130, 14));
    lblValorFrac_T.setText("Valor Frac.");
    lblValorFrac_T.setBounds(new Rectangle(145, 60, 70, 15));
    lblValorFrac_T.setForeground(new Color(255, 130, 14));
    txtCantidad.setBounds(new Rectangle(20, 135, 60, 20));
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
    txtFechaVenc.setBounds(new Rectangle(285, 135, 100, 20));
    txtFechaVenc.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaVenc_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaVenc_keyReleased(e);
        }
      });
    lblFechaVenc_T.setText("Fecha Venc.");
    lblFechaVenc_T.setBounds(new Rectangle(285, 115, 85, 15));
    lblFechaVenc_T.setForeground(new Color(255, 130, 14));
    lblLaboratorio_T.setText("Laboratorio");
    lblLaboratorio_T.setBounds(new Rectangle(220, 60, 75, 15));
    lblLaboratorio_T.setForeground(new Color(255, 130, 14));
    lblLaboratorio.setBounds(new Rectangle(220, 85, 175, 15));
    lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
    lblLaboratorio.setForeground(new Color(255, 130, 14));
    btnCantidad.setText("Entero");
    btnCantidad.setBounds(new Rectangle(20, 115, 60, 15));
    btnCantidad.setMnemonic('E');
    btnCantidad.setForeground(new Color(255, 130, 14));
    btnCantidad.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnEntero_actionPerformed(e);
        }
      });
    txtLote.setBounds(new Rectangle(175, 135, 95, 20));
    txtLote.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtLote_keyPressed(e);
                    }
      });
    lblLote_T.setText("Lote");
    lblLote_T.setBounds(new Rectangle(180, 115, 60, 15));
    lblLote_T.setForeground(new Color(255, 130, 14));
    txtFraccion.setBounds(new Rectangle(95, 135, 60, 20));
    txtFraccion.addKeyListener(new java.awt.event.KeyAdapter()
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
    txtFraccion.setLengthText(6);
    lblFraccion.setText("Fraccion");
    lblFraccion.setBounds(new Rectangle(95, 115, 60, 15));
    lblFraccion.setForeground(new Color(255, 130, 14));
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    pnlTitle1.add(lblFraccion, null);
    pnlTitle1.add(txtFraccion, null);
    pnlTitle1.add(lblLote_T, null);
    pnlTitle1.add(txtLote, null);
    pnlTitle1.add(btnCantidad, null);
    pnlTitle1.add(lblLaboratorio, null);
    pnlTitle1.add(lblLaboratorio_T, null);
    pnlTitle1.add(lblFechaVenc_T, null);
    pnlTitle1.add(txtFechaVenc, null);
    pnlTitle1.add(txtCantidad, null);
    pnlTitle1.add(lblValorFrac_T, null);
    pnlTitle1.add(lblValorFrac, null);
    pnlTitle1.add(lblUnidad_T, null);
    pnlTitle1.add(lblUnidad, null);
    pnlTitle1.add(lblDescripcion, null);
    pnlTitle1.add(lblCodigo, null);
    pnlTitle1.add(lblDescripcion_T, null);
    pnlTitle1.add(lblCodigo_T, null);
    jContentPane.add(pnlTitle1, null);
    pnlHeader1.add(lblUnidades, null);
    pnlHeader1.add(lblUnidades_T, null);
    pnlHeader1.add(lblFechaHoraActual, null);
    pnlHeader1.add(lblStock_T, null);
    jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    //
    txtCantidad.setLengthText(6);
    txtLote.setLengthText(15);
    txtFechaVenc.setLengthText(10);
  }

  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initCabecera();
    cantInic = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesInventario.vCant_Transf));
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initCabecera()
  {
    lblFechaHoraActual.setText(VariablesInventario.vFechaHora_Transf);
    lblUnidades.setText(VariablesInventario.vStkFisico_Transf);
    lblCodigo.setText(VariablesInventario.vCodProd_Transf);
    lblDescripcion.setText(VariablesInventario.vNomProd_Transf);
    lblUnidad.setText(VariablesInventario.vUnidMed_Transf);
    lblValorFrac.setText(VariablesInventario.vValFrac_Transf);
    lblLaboratorio.setText(VariablesInventario.vNomLab_Transf);

    if(!VariablesInventario.vValFrac_Transf.equals("1"))
      productoFraccionado = true;
      
    if(productoFraccionado)
    {
      int cant = 0;
      if(!VariablesInventario.vCant_Transf.trim().equals(""))
      {
        cant= Integer.parseInt(VariablesInventario.vCant_Transf.trim());
      }
      int frac = Integer.parseInt(VariablesInventario.vValFrac_Transf.trim());
      int valFrac = cant%frac;
      int valCant = cant/frac;
      txtCantidad.setText(valCant+"");
      txtFraccion.setText(valFrac+"");
    }else
    {
    txtCantidad.setText(VariablesInventario.vCant_Transf);
      txtFraccion.setText("0");
    }
    txtLote.setText(VariablesInventario.vLote_Transf);
    //txtFechaVenc.setText(VariablesInventario.vFechaVec_Transf);
    
    //Jquispe 21.04.2010 cambio para obtener la fecha de vencimiento
    txtFechaVenc.setText(devFechaVto());
    
    //Si es a matriz
    //if(VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
    if(VariablesInventario.vTransfMatriz)
    {
      //matriz = true;
      txtFechaVenc.setEditable(false);
      txtLote.setEditable(false);
      /**
       * Habilita el text Fraccion
       * @author dubilluz
       * @since  15.10.2007
       */    
       VariablesInventario.vIndTextFraccion = VariablesInventario.vIndTextFraccion.trim();
       
       log.debug(" VariablesInventario.vIndTextFraccion : " +   VariablesInventario.vIndTextFraccion);
       
       if(VariablesInventario.vIndTextFraccion.length() > 0){
         if(VariablesInventario.vIndTextFraccion.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            txtFraccion.setEditable(true);
         else
            txtFraccion.setEditable(false);
       }
       else{
        txtFraccion.setEditable(false);         
       }
    }
    //JMIRANDA 25.03.2010 Deshabilita modificar lote si es para modificar
    else{
       /* if (VariablesInventario.vIndModProdTransf) {
            txtFechaVenc.setEditable(false);
            txtLote.setEditable(false);
        }*/
    }
  }

  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */


  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    if(!productoFraccionado)
    {
      txtFraccion.setEditable(false);
      log.debug("Aqui ta");
    }

    FarmaUtility.moveFocus(txtCantidad);
    //VariablesInventario.vIndTextFraccion = "";
  }

  private void txtCantidad_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      
      if(VariablesInventario.vTransfMatriz)
      {
        //Se movera el foco a valor fraccion si 
        //el campo de cantidad este vacio y si el campo de fraccion este habilitado
        //dubilluz 15.10.2007
       //if(txtCantidad.getText().trim().length()>0)
        //{
          if(txtFraccion.isEditable())
          FarmaUtility.moveFocus(txtFraccion);
        //}
       else{
          //JMIRANDA 25.03.2010
          if (!VariablesInventario.vIndModProdTransf) {
              VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOTE;
              txtLote.setText(txtLote.getText().toUpperCase());
              validaCodigo(txtLote, txtFechaVenc, VariablesPtoVenta.vTipoMaestro);  
          }
        }
      }
      else        
        if(!txtFraccion.isEditable())
          FarmaUtility.moveFocus(txtLote);
        else FarmaUtility.moveFocus(txtFraccion);
    }
    else
      chkKeyPressed(e);
  }

   private void txtCantidad_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtCantidad,e);
  }

  private void txtLote_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(VariablesInventario.vTransfMatriz)
      {
        VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOTE;
        txtLote.setText(txtLote.getText().toUpperCase());
        validaCodigo(txtLote, txtFechaVenc, VariablesPtoVenta.vTipoMaestro);  
      }
      else
      {
        FarmaUtility.moveFocus(txtFechaVenc);
        txtLote.setText(txtLote.getText().toUpperCase());
      }
    }
    else
      chkKeyPressed(e);
  }

  private void txtFechaVenc_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(txtCantidad);
    else
      chkKeyPressed(e);
  }

  private void txtFechaVenc_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtFechaVenc,e);
  }

  private void btnCantidad_actionPerformed(ActionEvent e)
  {
    
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtCantidad);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
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
    if(txtCantidad.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar la Cantidad.",txtCantidad);
      retorno = false;
    }
    else if(txtFraccion.getText().trim().length() == 0 && 
            !VariablesInventario.vTransfMatriz &&
            productoFraccionado)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar la fraccion.",txtFraccion);
      retorno = false;
    }
    else if(!VariablesInventario.vTransfMatriz)
    {
      if(txtLote.getText().trim().length() == 0)
      {
        FarmaUtility.showMessage(this,"Debe Ingresar el Lote.",txtLote);
        retorno = false;
      }else if(txtFechaVenc.getText().trim().length() == 0)
      {
        FarmaUtility.showMessage(this,"Debe Ingresar la Fecha de Vencimiento.",txtFechaVenc);
        retorno = false;
      }else if(!FarmaUtility.validaFecha(txtFechaVenc.getText().trim(),"dd/MM/yyyy"))
      {
        FarmaUtility.showMessage(this,"Corrija la Fecha de Vencimiento.",txtFechaVenc);
        retorno = false;
      }
    }else if(VariablesInventario.vTransfMatriz)
    {
      if(VariablesInventario.vHistoricoLote)
      {
        if(txtLote.getText().trim().length() == 0 && txtFechaVenc.getText().trim().length() == 0)
        {
          FarmaUtility.showMessage(this,"Debe Seleccionar un registro del Maestro de Lotes.",txtLote);
          retorno = false;
        }else if(txtFechaVenc.getText().trim().length() != 0)
        {
          if(!FarmaUtility.validaFecha(txtFechaVenc.getText().trim(),"dd/MM/yyyy"))
          {
            FarmaUtility.showMessage(this,"Corrija la Fecha de Vencimiento.",txtFechaVenc);
            retorno = false;
          }
        }
      }
    }
  
    return retorno;
  }

  private void cargarDatos()
  {
    VariablesInventario.vCant_Transf = ""+VariablesInventario.vCantIngreso;
    //VariablesInventario.vCantIngreso;
    VariablesInventario.vLote_Transf = txtLote.getText().trim();
    VariablesInventario.vFechaVec_Transf = txtFechaVenc.getText().trim();
    int cant = Integer.parseInt(VariablesInventario.vCant_Transf);
    //double prec = Double.parseDouble(VariablesInventario.vPrecVta_Transf);
    double prec = FarmaUtility.getDecimalNumber(VariablesInventario.vPrecVta_Transf);
    VariablesInventario.vTotal_Transf = (cant*prec)+"";
  }

  private void aceptaCantidadIngresada()
  {
    if(validarCampos())
    {
      log.debug("Validar Campos OK");  
      if(!validaCantidadIngreso())
      {        
        FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.",txtCantidad);
        return;
      }
      if(!validaStockActual())
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(this, "La cantidad ingresada no puede ser mayor al Stock del Producto.",txtCantidad);
        lblUnidades.setText("" + (Integer.parseInt(VariablesInventario.vStk_Prod) + cantInic));
        return;
      }
      log.debug("Antes Cargar Datos OK"); 
      //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro de guardar los datos?"))
      {
        cargarDatos();
          log.debug("Despues Cargar Datos OK"); 
        cerrarVentana(true);
      }
    }
  }

  private boolean validaCantidadIngreso()
  {
    boolean valor = false;
    String cantIngreso = txtCantidad.getText().trim();
    String fraccion = txtFraccion.getText().trim();
    if(fraccion.equals(""))
      fraccion = "0";
    if(FarmaUtility.isInteger(cantIngreso) && Integer.parseInt(cantIngreso) >= 0) 
      valor = true;
    if(FarmaUtility.isInteger(fraccion) && Integer.parseInt(fraccion) >= 0) 
     valor = true; 
    if( Integer.parseInt(cantIngreso) == 0 && Integer.parseInt(fraccion) ==0)
     valor = false;
    if (!VariablesInventario.vTransfMatriz){ //si es transferencia a local
    if ((!fraccion.equals(""))&&(Integer.parseInt(fraccion) > Integer.parseInt(lblValorFrac.getText())))
      valor = false;
    }
    else //(VariablesInventario.vTransfMatriz)//si es transferencia a matriz
    {
      int cant = Integer.parseInt(cantIngreso);
      int frac = Integer.parseInt(lblValorFrac.getText());
      int result = cant * frac ;
      if(result%frac != 0)
      {
        valor = false;
        FarmaUtility.showMessage(this,"Al enviar una Transferencia a Matriz, debe ingresar \nla cantidad expresada en Unidad de Presentacion del Producto.\nEjemplo:"+frac*1+","+frac*2+","+frac*10+"...",txtCantidad);
      }
    }
    return valor;
  }

  private boolean validaStockActual()
  {
    boolean valor = false;
    obtieneStockProducto();
    String cantidad, fraccion; 
    cantidad = txtCantidad.getText().trim();
    fraccion = txtFraccion.getText().trim();
    if(fraccion.equals(""))
      fraccion = "0";
    /*if (VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
    {
      VariablesInventario.vCantIngreso = Integer.parseInt(cantidad) * Integer.parseInt(lblValorFrac.getText());    
    } else*/ VariablesInventario.vCantIngreso = Integer.parseInt(cantidad) * Integer.parseInt(lblValorFrac.getText()) + Integer.parseInt(fraccion);  
    
    log.debug("VariablesInventario.vCant_Transf : " + VariablesInventario.vCant_Transf);
    log.debug(" cantInic : " +  cantInic);
    log.debug("VariablesInventario.vCantIngreso : " + VariablesInventario.vCantIngreso);
    int ss = (Integer.parseInt(VariablesInventario.vStk_Prod) + cantInic);
    log.debug("Suma : " + ss);
    log.debug("VariablesInventario.vCantIngreso : "+VariablesInventario.vCantIngreso);
    
    if((Integer.parseInt(VariablesInventario.vStk_Prod) + cantInic) >= VariablesInventario.vCantIngreso) valor = true;
    return valor;
  }

  private void obtieneStockProducto()
  {
    ArrayList myArray = new ArrayList();
    obtieneStockProducto_ForUpdate(myArray);
    if(myArray.size() == 1)
    {
      VariablesInventario.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
    } else
    {
      FarmaUtility.showMessage(this, "Error al obtener Stock del Producto", null);
      cerrarVentana(false);
    }
  }

   private void obtieneStockProducto_ForUpdate(ArrayList pArrayList)
  {
    try
    {
      DBInventario.obtieneStockProducto_ForUpdate(pArrayList, VariablesInventario.vCodProd_Transf);
        FarmaUtility.liberarTransaccion();
        //dubilluz 13.10.2011
    } catch(SQLException sql)
    {
        FarmaUtility.liberarTransaccion();
        //dubilluz 13.10.2011
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ha ocurrido un error al obtener el stock del producto : \n" + sql.getMessage(),txtCantidad);
    }
  }
  
  private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro)
  {
    cargaListaMaestros(pTipoMaestro,pJTextField_Cod, pJTextField_Desc);
  }
  
  private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc)
  {
    VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
    VariablesPtoVenta.vTipListaMaestros = ConstantsPtoVenta.TIP_LIST_MAESTRO_TRANSF;
    VariablesPtoVenta.vTituloListaMaestros="Seleccione el Lote y Fecha de Vencimiento";
    
    DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
    try{    //INI ASOSA, 13.04.2010  
        dlgListaMaestros.ind=DBInventario.getIndIngresarLote();   
    }catch(SQLException e){
        if(e.getErrorCode()==20000){
            FarmaUtility.showMessage(this,"No existe el dato solicitado: "+e.getMessage(),null);
        }else{
            FarmaUtility.showMessage(this,e.getMessage(),null);
        }
    }    
    dlgListaMaestros.codprodx=lblCodigo.getText();
    dlgListaMaestros.indtrans="S";
    dlgListaMaestros.setVisible(true);
      //FIN ASOSA, 14.04.2010
    if(FarmaVariables.vAceptar)
    {
      pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
      //pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
      //Jquispe 21.04.2010 cambio para obtener la fecha de vencimiento
      pJTextField_Desc.setText(devFechaVto());
    }
  }



  private void txtFraccion_keyPressed(KeyEvent e)
  { 
    //modificado para que el foco se coloque donde el campo esta habilitado
    //dubilluz 15.10.2007
    if(e.getKeyCode() == KeyEvent.VK_ENTER){
      if(txtLote.isEditable())
      FarmaUtility.moveFocus(txtLote);
      else if(txtFechaVenc.isEditable())
           FarmaUtility.moveFocus(txtFechaVenc);
           else if(txtCantidad.isEditable())
                FarmaUtility.moveFocus(txtCantidad);
     }
    else
      chkKeyPressed(e);
  }

  private void btnEntero_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtCantidad);
  }

  private void txtFraccion_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtFraccion,e);
  }
  
  //jquispe 21.04.2010 metodo que busca la fecha de vencimiento de un lote
  private String devFechaVto()
  {   String fechaVto="";
      try
      {
          fechaVto=DBInventario.devFechaVto(lblCodigo.getText().trim(),txtLote.getText().trim());          
      }
      catch(SQLException sql)
      {log.error("",sql);       
      }
      
      return fechaVto;
  }
}