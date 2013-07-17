package mifarma.ptoventa.inventario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgEspecialListaProductos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      09.09.2008   Creación<br>
 * <br>
 * @author Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */
public class DlgEspecialListaProductos extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  //FarmaTableModel tableModel;  
  private String stkProd = "";
  private String indProdCong = "";
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JButtonLabel btnNuscar = new JButtonLabel();
  private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
private JButtonLabel lblCantProd = null;
private JButtonLabel lblCantProd_T = null;

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgEspecialListaProductos()
  {
    this(null, "", false);
  }

  public DlgEspecialListaProductos(Frame parent, String title, boolean modal)
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
      e.printStackTrace();
    }
  }

  /* ************************************************************************ */
	/*                                  METODO jbInit                           */
	/* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(784, 438));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Lista de Productos Especiales");
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
    pnlHeader1.setBounds(new Rectangle(10, 15, 755, 45));
    pnlTitle1.setBounds(new Rectangle(10, 70, 755, 25));
    scrListaProductos.setBounds(new Rectangle(10, 95, 755, 270));
    scrListaProductos.setBackground(new Color(255, 130, 14));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(675, 375, 90, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(565, 375, 100, 20));
    lblEnter.setText("[ ENTER ] Seleccionar");
    lblEnter.setBounds(new Rectangle(15, 375, 195, 20));
    btnNuscar.setText("Buscar:");
    btnNuscar.setBounds(new Rectangle(15, 15, 55, 15));
    btnNuscar.setMnemonic('B');
    btnNuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNuscar_actionPerformed(e);
        }
      });
    txtBuscar.setBounds(new Rectangle(75, 15, 330, 20));
    txtBuscar.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtBuscar_keyPressed(e);
                    }

        public void keyReleased(KeyEvent e)
        {
          txtBuscar_keyReleased(e);
        }
      });
    btnRelacionProductos.setText("Relacion de Productos");
    btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
    btnRelacionProductos.setMnemonic('R');
    btnRelacionProductos.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionProductos_actionPerformed(e);
        }
      });
    scrListaProductos.getViewport().add(tblListaProductos, null);
    jContentPane.add(lblEnter, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(scrListaProductos, null);
    pnlTitle1.add(btnRelacionProductos, null);
    pnlTitle1.add(getLblCantProd(), null);
    pnlTitle1.add(getLblCantProd_T(), null);
    jContentPane.add(pnlTitle1, null);
    pnlHeader1.add(txtBuscar, null);
    pnlHeader1.add(btnNuscar, null);
    jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();    
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    //Modificado por DVELIZ 18.10.08
    /*VariablesInventario.tableModelEspecial = new FarmaTableModel(ConstantsInventario.columnsListaProductosEspeciales,
                ConstantsInventario.defaultValuesListaProductosEspeciales,0);*/
    //tableModel.data = VariablesVentas.tableModelListaGlobalProductos.data;
    FarmaUtility.initSelectList(tblListaProductos, VariablesInventario.tableModelEspecial,
                                    ConstantsInventario.columnsListaProductosEspeciales);

    for (int i=0; i<VariablesInventario.tableModelEspecial.getRowCount(); i++)
      VariablesInventario.tableModelEspecial.setValueAt(new Boolean(false),i,0);
    
    //cargaProductosSeleccionados();
    //FarmaGridUtils.showCell(tblListaProductos,0,0);
    //cargaListaProductos();
  }
  
  //Modificado por DVELIZ 18.10.08
  /*
  private void cargaListaProductos()
  {
    try
    {
      DBInventario.cargaListaProductosEspeciales(VariablesInventario.tableModelEspecial);
      FarmaUtility.ordenar(tblListaProductos, VariablesInventario.tableModelEspecial,2,FarmaConstants.ORDEN_ASCENDENTE);
    } catch(java.sql.SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar los productos : \n" + sql.getMessage(),txtBuscar);
    }
  }*/
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnNuscar_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtBuscar);
  }

  private void btnRelacionProductos_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListaProductos);
  }

  private void txtBuscar_keyPressed(KeyEvent e)
  {
    /*FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 2);
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblListaProductos.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 1, 2)) ) 
        {
          FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
          return;
        }
      }
    } 
    
    chkKeyPressed(e);*/
     FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 2);
     if (e.getKeyCode() == KeyEvent.VK_ENTER)
     {
       try{
         e.consume();
         if (tblListaProductos.getSelectedRow() >= 0)
         {
           String productoBuscar = txtBuscar.getText().trim().toUpperCase();           
           if ( productoBuscar.length()==0 )  return;
           
           String codigo = "";
           // revisando codigo de barra
           char primerkeyChar = productoBuscar.charAt(0);
           char segundokeyChar;
           
           if(productoBuscar.length() > 1)
             segundokeyChar = productoBuscar.charAt(1);
           else
             segundokeyChar = primerkeyChar;
           
           char ultimokeyChar = productoBuscar.charAt(productoBuscar.length()-1);
           
           if ( productoBuscar.length()>6 && (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) && (!Character.isLetter(ultimokeyChar))))) {
             VariablesVentas.vCodigoBarra = productoBuscar;
             productoBuscar = DBVentas.obtieneCodigoProductoBarra();
           }
           
           
           //JCORTEZ 23.07.2008
             ///if (productoBuscar.equalsIgnoreCase("000000")&&UtilityVentas.esCupon(productoBuscar,this,txtProducto)) {
             if (productoBuscar.equalsIgnoreCase("000000")) {
               FarmaUtility.showMessage(this, "No existe producto relacionado con el Codigo de Barra. Verifique!!!", txtBuscar);
               return;
             }         
           
           
           for (int k = 0; k < tblListaProductos.getRowCount(); k++) {
             codigo = ((String)tblListaProductos.getValueAt(k,1)).trim();
             if (codigo.equalsIgnoreCase(productoBuscar)) {
               FarmaGridUtils.showCell(tblListaProductos,k,0);
               break;
             }
           }
           /*
           if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 1, 2)) ) 
           {
            FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
            return;
           }*/
         }
       } catch (SQLException sql) {
         //sql.printStackTrace();
         
         FarmaUtility.showMessage(this, "Error al buscar el Producto.\n" + sql, txtBuscar);
       }
     }
      chkKeyPressed(e);
  }

  private void txtBuscar_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBuscar,2);
      if(tblListaProductos.getRowCount() >= 0 && VariablesInventario.tableModelEspecial.getRowCount()>0 && e.getKeyChar() != '+')
      {
        if(FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 2)
          || (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_PAGE_UP)
          || (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
          || e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            muestraInfoProd();
        }
        
      }  
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    cargaProductosSeleccionados();
    lblCantProd_T.setText(VariablesInventario.vArrayListaProdsEsp.size()+"");
    limpiarVariables();
    FarmaUtility.moveFocus(txtBuscar);  
      if(VariablesVentas.vKeyPress!=null)
      {
       if(VariablesVentas.vCodBarra.trim().length()>0)
       {
         txtBuscar.setText(VariablesVentas.vCodBarra.trim());
         txtBuscar_keyPressed(VariablesVentas.vKeyPress);      
       }
       else if(VariablesVentas.vCodProdBusq.trim().length()>0){
         txtBuscar.setText(VariablesVentas.vCodProdBusq.trim());
         txtBuscar_keyPressed(VariablesVentas.vKeyPress);     
       }
       else
       {
         txtBuscar.setText(VariablesVentas.vKeyPress.getKeyChar() + "");
         txtBuscar_keyReleased(VariablesVentas.vKeyPress);     
       }      
      }else{
          txtBuscar.setText("");
      }
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
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        if(tblListaProductos.getSelectedRowCount() > 0){
            seleccionarProducto();
        }
    }else if(e.getKeyCode() == KeyEvent.VK_F11)
    {
      funcion_F11();   
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      //cancelaOperacion();
        System.out.println("En lista ESCP :"+VariablesInventario.vIrResumen);
      //if(VariablesInventario.vIrResumen)
          cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar)
	{
		VariablesInventario.vArrayListaProdsEsp = new ArrayList();
	  	FarmaVariables.vAceptar = pAceptar;
                if(!pAceptar)
                   VariablesInventario.vIrResumen = true;
                else
                    VariablesInventario.vIrResumen = false;                    
                
		this.setVisible(false);
		this.dispose();
  }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void seleccionarProducto()
  {
    boolean seleccion = ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),0)).booleanValue();
    if(!seleccion)
    {
     /* if(!validaStockDisponible())
      {
        return;
      }
      if(!validaProductoTomaInventario(tblListaProductos.getSelectedRow()))
      {
        FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario", txtBuscar);
        return;
      }*/
      //validara si el producto esta activo
      
      /*
      */
      // Se validara si no excede de los item de productos de pedido especial
      if (validaItemPedEspecial(ConstantsInventario.CONS_ING_PROD_ESPC)) {
          cargaCabeceraIngreseCantidad();
          if(isProductoActivo())
          {
             // return ;
             DlgEspecialCantIngreso dlgEspecialCantIngreso = new DlgEspecialCantIngreso(myParentFrame,"",true);
             dlgEspecialCantIngreso.setVisible(true);
             if(FarmaVariables.vAceptar)
             {
              // agregarProducto();
               //seleccionaProducto();
               FarmaUtility.setCheckValue(tblListaProductos,false);
               FarmaVariables.vAceptar = false;
                //listarProductos();
                tblListaProductos.setValueAt(VariablesInventario.vCantIng,VariablesInventario.vPosi,6);
                tblListaProductos.setRowSelectionInterval(VariablesInventario.vPosi,VariablesInventario.vPosi);
                
                lblCantProd_T.setText(VariablesInventario.vArrayListaProdsEsp.size()+"");
                             
                funcion_F11();
             }              
          }
          
          
      }
    }
    else
    {
     borrarProducto();
     tblListaProductos.setValueAt("",tblListaProductos.getSelectedRow(),6);
     //System.out.println("RESTANTES :"+VariablesInventario.vArrayProductosEspeciales);
     lblCantProd_T.setText(VariablesInventario.vArrayListaProdsEsp.size()+"");
      //FarmaUtility.showMessage(this,"Se desagrego el equipo", txtEquipo);
      FarmaUtility.setCheckValue(tblListaProductos, true);
    }
    
  }
  
  private void cargaCabeceraIngreseCantidad()
  {
    VariablesInventario.vCodProd_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    VariablesInventario.vNomProd_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),2).toString();
    VariablesInventario.vUnidMed_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),3).toString();
    VariablesInventario.vNomLab_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
    VariablesInventario.vStkFisico_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
    VariablesInventario.vCantIng = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),6).toString();
    VariablesInventario.vValFrac_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),7).toString();
    VariablesInventario.vPrecVta_esp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),8).toString();
    VariablesInventario.vPosi=tblListaProductos.getSelectedRow();
    
    //Si es a matriz
    /*if(VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
    {
      try
      {
        VariablesInventario.vHistoricoLote = DBInventario.getHistoricoLote(VariablesInventario.vCodProd_Transf);
      }catch(SQLException sql)
      {
        sql.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrió un error al obtener el histórico del lote : \n"+ sql.getMessage(),txtBuscar);
      } 
    }*/
  }
  
  
  private void borrarProducto()
  {
    String cod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    
    for(int i=0;i<VariablesInventario.vArrayListaProdsEsp.size();i++)
    {
      if(((ArrayList)VariablesInventario.vArrayListaProdsEsp.get(i)).contains(cod))
      {
    	  VariablesInventario.vArrayListaProdsEsp.remove(i);
        break;
      }
    }
  }
  
  /**
   * @author jcallo
   * @since  16.10.2008
   * **/
  private void cargaProductosSeleccionados()
  {
    if(VariablesInventario.vArrayProductosEspeciales.size()>0)
    {
    	VariablesInventario.vArrayListaProdsEsp = (ArrayList)VariablesInventario.vArrayProductosEspeciales.clone();
    	String cod;
    	String Cant;
      for(int i=0;i<VariablesInventario.vArrayListaProdsEsp.size();i++)
      {
        cod = ((ArrayList)VariablesInventario.vArrayListaProdsEsp.get(i)).get(0).toString();
        Cant = ((ArrayList)VariablesInventario.vArrayListaProdsEsp.get(i)).get(5).toString();
        for(int j=0;j<tblListaProductos.getRowCount();j++)
        {
          if(((ArrayList) VariablesInventario.tableModelEspecial.getRow(j)).contains(cod))
          {
             VariablesInventario.tableModelEspecial.setValueAt(new Boolean(true),j,0);
             VariablesInventario.tableModelEspecial.setValueAt(Cant,j,6);
            break;
          }
        }
      }
    }
  }
  
  
  /*private void actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo) {
    try 
    {
      DBInventario.actualizaStkComprometidoProd(pCodigoProducto,pCantidadStk,pTipoStkComprometido);
      DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto,"", pTipoRespaldoStock, pCantidadRespaldo, Integer.parseInt(VariablesInventario.vValFrac_Transf),ConstantsPtoVenta.MODULO_TRANSFERENCIA);
      FarmaUtility.aceptarTransaccion();
    }catch (SQLException sql) 
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" + sql.getMessage() ,txtBuscar);
    }
  }*/
  
  /**
   * Limpia las Variables
   * */
  private void limpiarVariables(){
  
    VariablesInventario.flag_modificarCantidad = false;
      VariablesInventario.vIrResumen = false;  
  }
  
  
 /* private void cancelaOperacion()
  {
    String codProd = "";
    String codProdTmp = "";
    boolean existe = false;
    for(int i=0; i<arrayProductos.size(); i++) {
      codProd = (String)(((ArrayList)arrayProductos.get(i)).get(0));
      String cantidad = (String)(((ArrayList)arrayProductos.get(i)).get(4));
      for(int j=0; j<VariablesInventario.vArrayTransferenciaProductos.size(); j++) {
        codProdTmp = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(j)).get(0));
        if(codProd.equalsIgnoreCase(codProdTmp)) {
          existe = true;
          break;
        }
      }
      if(!existe) actualizaStkComprometidoProd(codProd, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)));
      existe = false;
    }
    for(int i=0; i<VariablesInventario.vArrayTransferenciaProductos.size(); i++) {
      codProd = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(0));
      String cantidad = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(4));
      String cantidadTmp = "";
      for(int j=0; j<arrayProductos.size(); j++) {
        codProdTmp = (String)(((ArrayList)arrayProductos.get(j)).get(0));
        if (codProd.equalsIgnoreCase(codProdTmp)) {
          existe = true;
          cantidadTmp = (String)(((ArrayList)arrayProductos.get(j)).get(4));
          break;
        }
      }
      if(!existe) 
        actualizaStkComprometidoProd(codProd, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),ConstantsInventario.INDICADOR_A, ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)));
      else{
        if(Integer.parseInt(cantidad) < Integer.parseInt(cantidadTmp))
          actualizaStkComprometidoProd(codProd, (Integer.parseInt(cantidadTmp) - Integer.parseInt(cantidad)),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad));
        else if(Integer.parseInt(cantidad) > Integer.parseInt(cantidadTmp))
          actualizaStkComprometidoProd(codProd, (Integer.parseInt(cantidad) - Integer.parseInt(cantidadTmp)),ConstantsInventario.INDICADOR_A, ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, Integer.parseInt(cantidad));
      }
      existe = false;
    }
  }*/
  /**
   * Se validara que la cantidad de items ingresados no sean mayores del 
   * parametrizado para el local
   * @author dubilluz
   * @since  22.09.2008
   */
  private boolean validaItemPedEspecial(int pTipoValidacion){
      
      boolean bResult = true;
      int dCantEstablecida = 0;
      try{
          dCantEstablecida = Integer.parseInt(
                                              DBInventario.obtieneCantMaxItemPedidoEspecial().trim()
                                              );
          
          if(pTipoValidacion==ConstantsInventario.CONS_ING_PED_ESPC){
              if(VariablesInventario.vArrayListaProdsEsp.size()>dCantEstablecida){
                  bResult = false;
              }              
          }
          else if(pTipoValidacion==ConstantsInventario.CONS_ING_PROD_ESPC) {
                if (dCantEstablecida == 0)
                    bResult = false;
                else if (dCantEstablecida <= 
                	VariablesInventario.vArrayListaProdsEsp.size()) {
                    bResult = false;
                }
          }
      }
      catch(SQLException e){
          bResult = false;
          e.printStackTrace();
      }
      
      if(!bResult){
          FarmaUtility.showMessage(this, 
                                   "El pedido excede el número de items establecido para el local",
                                   null);
      }
      
      return bResult;
  }

/**
 * This method initializes lblCantProd	
 * 	
 * @return com.gs.mifarma.componentes.JButtonLabel	
 */
private JButtonLabel getLblCantProd() {
	if (lblCantProd == null) {
		lblCantProd = new JButtonLabel();
		lblCantProd.setBounds(new Rectangle(660, 5, 45, 15));
		lblCantProd.setText("Items:");		
	}
	return lblCantProd;
}

    /**
     * This method initializes lblCantProd_T	
     * 	
     * @return com.gs.mifarma.componentes.JButtonLabel	
     */
    private JButtonLabel getLblCantProd_T() {
            if (lblCantProd_T == null) {
                    lblCantProd_T = new JButtonLabel();
                    lblCantProd_T.setBounds(new Rectangle(701, 6, 53, 15));
                    lblCantProd_T.setText("0");
            }
            return lblCantProd_T;
    }
    
    /**
     * realiza la funcion F11
     * @auhtor DVELIZ
     * @since  18.10.08
     */
    public void funcion_F11(){
        //add jcallo validacion de que se especifique al menos un producto por pedido
        if(VariablesInventario.vArrayListaProdsEsp.size()<1){
            FarmaUtility.showMessage(this, 
                                     "Seleccione los productos del pedido. !",
                                     null);            
        }else {
        
            if(validaItemPedEspecial(ConstantsInventario.CONS_ING_PED_ESPC)){
             /*if(FarmaUtility.rptaConfirmDialog(this,"Esta seguro de generar el pedido?"))
               {
                generaPedido();
               }*/
                VariablesInventario.vArrayProductosEspeciales = (ArrayList)VariablesInventario.vArrayListaProdsEsp.clone();
                cerrarVentana(true);
                
            }
            
        }
        //VariablesInventario.vArrayTransferenciaProductos = arrayProductos;
        //cerrarVentana(true);
    }
    
     private void muestraInfoProd()
     {
       if(tblListaProductos.getRowCount() <= 0) return;
       
       ArrayList myArray = new ArrayList();
       obtieneInfoProdEnArrayList(myArray);
       //System.out.println(myArray);      
       if(myArray.size() == 1)
       {
         stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
       } else
       {
         stkProd = "";
         FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtBuscar);
       }
       
       tblListaProductos.setValueAt(stkProd, tblListaProductos.getSelectedRow(), 5);
       tblListaProductos.repaint();
     }
     
    private void obtieneInfoProdEnArrayList(ArrayList pArrayList)
    {
      int vFila = tblListaProductos.getSelectedRow();
      String codProd = FarmaUtility.getValueFieldJTable(tblListaProductos,vFila,1);
      try
      {
         DBVentas.obtieneInfoProductoVta(pArrayList, codProd);        
      } catch(SQLException sql)
      {
        sql.printStackTrace();
        FarmaUtility.showMessage(this,"Error al obtener informacion del producto en Arreglo. \n" + sql.getMessage(),txtBuscar);
      }
    }
    //JQUISPE 05.05.2010 metodo que verifica que un producto este inactivo
    private boolean isProductoActivo()
    {  String Activo="";
        
        try
        {Activo  = DBInventario.isProductoActivo(VariablesInventario.vCodProd_esp);
        }
        catch(SQLException sql)
        {sql.printStackTrace();
         //FarmaUtility.showMessage(this,"El producto");
         FarmaUtility.showMessage(this,
                                  "Error al obtener informacion del producto buscado. \n" + sql.getMessage(),
                                  txtBuscar);
        }
        System.out.println("---------------------------");
        System.out.println("Su estado es:"+Activo);
        System.out.println("---------------------------");
        if(Activo.toString().trim().equals("A"))
        {return true;
        }
        else
        {   FarmaUtility.showMessage(this,"Este Producto esta Inactivo",txtBuscar);
            return false;
        }
    }
}