package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recetario.DlgResumenRecetarioMagistral;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMercaderiaDirectaCantidad extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  private boolean productoFraccionado = false;
  private static final Logger log = LoggerFactory.getLogger(DlgResumenRecetarioMagistral.class);
  private Integer precepTotal = 0;
  private String totalProd = "";
  private Double precInput = 0.0;
  private String descUnidVta = "";
  String strCodigoOrdCompDetalle = "1";
  private BorderLayout borderLayout1                = new BorderLayout();
  private JPanelWhite jContentPane                  = new JPanelWhite();
  private JPanelHeader pnlHeader1                   = new JPanelHeader();
  private JLabelWhite lblDescripcion_T              = new JLabelWhite();
  private JLabelWhite lblCodeProduct                = new JLabelWhite();
  private JPanelTitle pnlTitle1                     = new JPanelTitle();
  private JButtonLabel btnRecep                     = new JButtonLabel();
  private JTextFieldSanSerif txtEntero              = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtPrecioUnitario      = new JTextFieldSanSerif();
  private JLabelWhite lblIGV                        = new JLabelWhite();
  private JTextFieldSanSerif txtIGV                 = new JTextFieldSanSerif();
  private JLabelWhite lblPrecioUnita                = new JLabelWhite();
  private JLabelFunction lblEsc                     = new JLabelFunction();
  private JLabelFunction lblF11                     = new JLabelFunction();
  private JTextFieldSanSerif txtFraccion            = new JTextFieldSanSerif();
  private JLabelOrange lblCantOC                    = new JLabelOrange();
  private JLabelOrange lblCodProducto               = new JLabelOrange();
  private JLabelOrange lblUnidVenta_T               = new JLabelOrange();
  private JLabelOrange lblDescripProd               = new JLabelOrange();
  private JLabelOrange lblUnidVenta                 = new JLabelOrange();
  private FacadeInventario facadeInventario  = new FacadeInventario();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgMercaderiaDirectaCantidad() {
      this(null, "", false);
  }

  public DlgMercaderiaDirectaCantidad(Frame parent, String title, boolean modal) {
      super(parent, title, modal);
      myParentFrame = parent;
      try {
          jbInit();
          initialize();
        }catch(Exception e) {
          log.error("",e);
        }
  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit() throws Exception {
      this.setSize(new Dimension(428, 285));
      this.getContentPane().setLayout(null);
      this.getContentPane().setLayout(borderLayout1);
      this.setTitle("Cantidad Recibida - Mercaderia Directa");
      this.setDefaultCloseOperation(0);
      this.addWindowListener(new WindowAdapter() {
        public void windowOpened(WindowEvent e) {
          this_windowOpened(e);
        }
        public void windowClosing(WindowEvent e) {
            this_windowClosing(e);
        }
      });
    jContentPane.setBounds(new Rectangle(0, 0, 326, 210));
    jContentPane.setSize(new Dimension(326, 210));
    pnlHeader1.setBounds(new Rectangle(10, 10, 400, 150));
    pnlHeader1.setBackground(Color.white);
    pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    pnlHeader1.setSize(new Dimension(400, 100));
    lblDescripcion_T.setText("Descripción:");
    lblDescripcion_T.setBounds(new Rectangle(10, 40, 75, 15));
    lblDescripcion_T.setForeground(new Color(255, 130, 14));
    lblCodeProduct.setBounds(new Rectangle(105, 15, 105, 15));
    lblCodeProduct.setFont(new Font("SansSerif", 0, 11));
    lblCodeProduct.setForeground(new Color(255, 130, 14));
    lblCodeProduct.setSize(new Dimension(105, 20));
    pnlTitle1.setBounds(new Rectangle(10, 130, 400, 80));
    pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    pnlTitle1.setBackground(Color.white);
    btnRecep.setText("Cant. Rec.");
    btnRecep.setBounds(new Rectangle(15, 20, 60, 15));
    btnRecep.setMnemonic('E');
    btnRecep.setForeground(new Color(255, 130, 14));
    btnRecep.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
          btnEntero_actionPerformed(e);
        }
      });
    txtEntero.setBounds(new Rectangle(15, 40, 60, 20));
    txtEntero.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e)
        {
            txtEntero_keyPressed(e);
          }

        public void keyTyped(KeyEvent e)
        {
          txtEntero_keyTyped(e);
        }
      });
    txtPrecioUnitario.setLengthText(9);
    txtPrecioUnitario.setHorizontalAlignment(SwingConstants.LEFT);
    txtPrecioUnitario.setBounds(new Rectangle(185, 40, 70, 20));
    txtPrecioUnitario.setEditable(false);
    txtPrecioUnitario.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e)
        {
          txtPrecioUnitario_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
          txtPrecioUnitario_keyTyped(e);
        }
      });
    txtPrecioUnitario.setHorizontalAlignment(SwingConstants.CENTER);
    lblIGV.setText("IGV");
    lblIGV.setBounds(new Rectangle(280, 20, 70, 15));
    lblIGV.setForeground(new Color(255, 130, 14));
    txtIGV.setBounds(new Rectangle(280, 40, 75, 20));
    lblPrecioUnita.setText("Precio Unit.");
    lblPrecioUnita.setBounds(new Rectangle(185, 20, 70, 15));
    lblPrecioUnita.setForeground(new Color(255, 130, 14));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(325, 230, 85, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(215, 230, 105, 20));
    txtFraccion.setBounds(new Rectangle(100, 40, 60, 20));
    txtFraccion.setLengthText(6);
    txtFraccion.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e)
          {
            txtFraccion_keyPressed(e);
          }

          public void keyTyped(KeyEvent e)
          {
            txtFraccion_keyTyped(e);
          }
        });
    txtFraccion.setHorizontalAlignment(SwingConstants.CENTER);
        lblCantOC.setBounds(new Rectangle(100, 20, 65, 15));
        lblCantOC.setText("Cant. O/C");
        lblCodProducto.setText("Cod. Producto :");
    lblCodProducto.setBounds(new Rectangle(10, 15, 95, 15));
    lblUnidVenta_T.setText("Unid. Venta:");
    lblUnidVenta_T.setBounds(new Rectangle(10, 65, 79, 15));
    lblDescripProd.setBounds(new Rectangle(95, 40, 290, 15));
    lblDescripProd.setFont(new Font("SansSerif", 0, 11));
        lblDescripProd.setSize(new Dimension(290, 20));
        lblUnidVenta.setBounds(new Rectangle(95, 65, 130, 15));
    lblUnidVenta.setFont(new Font("SansSerif", 0, 11));
        lblUnidVenta.setSize(new Dimension(130, 20));
        pnlHeader1.add(lblUnidVenta, null);
        pnlHeader1.add(lblDescripProd, null);
        pnlHeader1.add(lblUnidVenta_T, null);
        pnlHeader1.add(lblCodProducto, null);
        pnlHeader1.add(lblCodeProduct, null);
        pnlHeader1.add(lblDescripcion_T, null);
        pnlTitle1.add(lblCantOC, null);
        pnlTitle1.add(txtFraccion, null);
        pnlTitle1.add(lblPrecioUnita, null);
        pnlTitle1.add(lblIGV, null);
        pnlTitle1.add(txtPrecioUnitario, null);
        pnlTitle1.add(txtEntero, null);
    pnlTitle1.add(btnRecep, null);
        pnlTitle1.add(txtIGV, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    
    txtEntero.setLengthText(6);    
    txtIGV.setLengthText(3);
    txtIGV.setEditable(false);
    txtIGV.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                txtIGV_Pressed(e);
                    
                
            }
                public void keyTyped(KeyEvent e) {
                    txtIGV_keyTyped(e);
                }
            });
    txtIGV.setHorizontalAlignment(SwingConstants.CENTER);
    
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize() {
    initCabecera();
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initCabecera() {
     
    lblCodeProduct.setText(VariablesInventario.vCodProducto.trim());
    lblDescripProd.setText(VariablesInventario.vDescProd.trim());
    lblUnidVenta.setText(VariablesInventario.vUnidMedida.trim());
    txtFraccion.setText(VariablesInventario.vCantPedida.trim()); 
    txtPrecioUnitario.setText(VariablesInventario.vPrecUnit.trim());
    txtIGV.setText("18");
    
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
  }

  private void txtEntero_keyPressed(KeyEvent e)
  {
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        if(!txtFraccion.isEditable()) {
              FarmaUtility.moveFocus(txtPrecioUnitario);
            }else
              FarmaUtility.moveFocus(txtFraccion);
        }else
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
  
  private void txtPrecioUnitario_keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(txtIGV);
    else
      chkKeyPressed(e);    
  }
    
  private void txtPrecioUnitario_keyTyped(KeyEvent e) {
    FarmaUtility.admitirDigitosDecimales(txtPrecioUnitario,e);
  }
 
  private void txtIGV_Pressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
          FarmaUtility.moveFocus(txtIGV);
        else
          chkKeyPressed(e);
    }
  
  private void txtIGV_keyTyped(KeyEvent e){
      FarmaUtility.admitirDigitosDecimales(txtIGV,e);
  }
  
  
  private void this_windowClosing(WindowEvent e)  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e) {
    if(UtilityPtoVenta.verificaVK_F11(e)) {
      aceptaCantidadIngresada();   
      
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean vAceptar) {
      FarmaVariables.vAceptar = vAceptar;
      this.setVisible(false);
      this.dispose();
  }
  
  private void setDetalleOrdenCompraRecep(){
      VariablesInventario.vCodProdOrdComp = lblCodeProduct.getText().trim();
      VariablesInventario.vCantEntregada  = txtEntero.getText().trim();
      VariablesInventario.vCantPedOrdComp = txtFraccion.getText().trim();
      VariablesInventario.vPrecioUnit     = txtPrecioUnitario.getText().trim();
      VariablesInventario.vPrecioIGV      = txtIGV.getText().trim(); 
      VariablesInventario.vSEC            = strCodigoOrdCompDetalle.trim();         
  }

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private void aceptaCantidadIngresada() {
      //GFonseca 10.12.2013 Se agrega validacion para no permitir vacios en la cantidad
      if(txtEntero.getText().trim().equals("")){
          FarmaUtility.showMessage(this, "Ingrese cantidad", null);
          FarmaUtility.moveFocus(txtEntero);
          return;          
      }      
      int ptotalProd = Integer.parseInt(VariablesInventario.vRecepTotal) + Integer.parseInt(txtEntero.getText());
      precepTotal    = Integer.parseInt(VariablesInventario.vCantPedida);
      
      double precRecep  = FarmaUtility.getDecimalNumber(txtPrecioUnitario.getText());
      precInput         = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(Double.parseDouble(VariablesInventario.vPrecUnit)));
           
     
     if(Integer.parseInt(txtEntero.getText()) == 0){
                  FarmaUtility.showMessage(this, "Debe ingresar cantidades superiores a " + Integer.parseInt(txtEntero.getText()) + "  unidades", null);
                  FarmaUtility.moveFocus(txtEntero);
    
     }else if(ptotalProd > precepTotal){
       int difere   = ptotalProd - precepTotal;
        FarmaUtility.showMessage(this, "Verificar, no puede ingresar cantidades mayores a la solicitada", null);
        FarmaUtility.moveFocus(txtEntero);
         
     }
     else if(precRecep == 0.0 || precRecep == 0){
        /*double precdif = precRecep - precInput;
        String msng    = FarmaUtility.formatNumber(precdif,2);
        FarmaUtility.showMessage(this, "Verifique, existe un exceso en el precio de " + msng + " soles", null);*/
        FarmaUtility.showMessage(this, "Verifique, El precio es: "+ precRecep +" soles", null);
     }else{
          //GFonseca 10.12.2013 Inner pack (indica cuantas unidades por paquete) 
          int valInnerPackProd = facadeInventario.obtenerProdInnerPack(lblCodeProduct.getText().trim());
          if(ptotalProd <= precepTotal && valInnerPackProd>0){
              int cantPaquetes = 0;              
              cantPaquetes = ptotalProd%valInnerPackProd; 
              if(cantPaquetes > 0){
                  FarmaUtility.showMessage(this, "Debe ingresar cantidades multiplos de " + valInnerPackProd + "  unidades", null);
                  FarmaUtility.moveFocus(txtEntero);
              }else{
                  setDetalleOrdenCompraRecep();
                  //FarmaUtility.showMessage(this, "Se agrego correctamente ", null);
                  cerrarVentana(true);
              }          
          }else{
          setDetalleOrdenCompraRecep();
          //FarmaUtility.showMessage(this, "Se agrego correctamente ", null);
          cerrarVentana(true);
          }
      }
  }
 
}
