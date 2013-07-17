package mifarma.ptoventa.ventas;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextArea;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgListaCupones.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * JCORTEZ      04.08.2009   Creaci�n<br>
 * <br>
 * @author  JORGE LUIS CORTEZ ALVAREZ <br>
 * @version 1.0<br>
 *
 */
public class DlgListaCupones extends JDialog{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModelCupon;
   //FarmaTableModel tableModelCupon;
  ArrayList arrayEquipos = new ArrayList();
  private String stkProd = "";
  private String indProdCong = "";
  private boolean todos=false;
  
  private final int COL_CHECK=0;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JButtonLabel btnNuscar = new JButtonLabel();
  private JTextFieldSanSerif txtLocal = new JTextFieldSanSerif();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JScrollPane scrListaLocales = new JScrollPane();
  private JTable tblListaCupones = new JTable();
    private JScrollPane srcMensaje = new JScrollPane();
    private JTextArea txtMensaje = new JTextArea();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblDescrip = new JLabelWhite();

    /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgListaCupones()
  {
    this(null, "", false);
  }

  public DlgListaCupones(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      //FarmaUtility.centrarVentana(this);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit()
    throws Exception
  {
    this.setSize(new Dimension(641, 410));
    this.getContentPane().setLayout(borderLayout1);
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
    pnlHeader1.setBounds(new Rectangle(5, 10, 625, 30));
    pnlTitle1.setBounds(new Rectangle(5, 45, 625, 25));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(540, 355, 90, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(435, 355, 100, 20));
    lblF2.setText("[ F2 ] Todos/ Ninguno");
    lblF2.setBounds(new Rectangle(5, 355, 130, 20));
    btnNuscar.setText("Buscar:");
    btnNuscar.setBounds(new Rectangle(10, 5, 55, 20));
    btnNuscar.setMnemonic('B');
    btnNuscar.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
          btnNuscar_actionPerformed(e);
          }
        });
    txtLocal.setBounds(new Rectangle(70, 5, 355, 20));
    txtLocal.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtLocal_keyPressed(e);
                    }

        public void keyReleased(KeyEvent e)
        {
                        txtLocal_keyReleased(e);
                    }
      });
    btnRelacionProductos.setText("Relaci�n de Cupones :");
    btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
    btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.setActionCommand("Relacion de Cupones");
        btnRelacionProductos.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
          btnRelacionProductos_actionPerformed(e);
          }
        });
        
    scrListaLocales.setBounds(new Rectangle(5, 70, 625, 135));
    scrListaLocales.setBackground(new Color(255, 130, 14));
    tblListaCupones.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
        }
      });
        srcMensaje.setBounds(new Rectangle(5, 235, 625, 115));
        txtMensaje.setEditable(false);
        jPanelTitle1.setBounds(new Rectangle(5, 210, 625, 25));
        lblDescrip.setText("Descripcion :");
        lblDescrip.setBounds(new Rectangle(5, 0, 190, 25));
        scrListaLocales.getViewport().add(tblListaCupones, null);
        jPanelTitle1.add(lblDescrip, null);
        jContentPane.add(jPanelTitle1, null);
        srcMensaje.getViewport().add(txtMensaje, null);
        jContentPane.add(srcMensaje, null);
        jContentPane.add(scrListaLocales, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(txtLocal, null);
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
     FarmaUtility.moveFocus(txtLocal);
     //obtieneCupones(VariablesVentas.dniListCupon);
     cargaLista(VariablesVentas.dniListCupon);
      if(VariablesVentas.vArrayListCuponesCliente.size()>0)
      restarCupones();
  
     FarmaUtility.moveFocus(txtLocal); 
     FarmaUtility.ponerCheckJTable(tblListaCupones,1,VariablesVentas.vArrayListCuponesClienteAux,0);    
         
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initTable()
  {
   tableModelCupon = new FarmaTableModel(ConstantsVentas.columnsListaCuponesUsados ,ConstantsVentas.defaultValuesListaCuponesUsados,0);
   FarmaUtility.initSelectList(tblListaCupones,tableModelCupon,ConstantsVentas.columnsListaCuponesUsados);
  // FarmaUtility.initSimpleList(tblListaCupones,tableModelCupon,ConstantsVentas.columnsListaCuponesUsados);
  }
  
  
  /*private void obtieneCupones(String vDni){

          try{
            VariablesVentas.vArrayListCuponesClienteAux.clear();
            DBVentas.obtieneCuponesCliente(VariablesVentas.vArrayListCuponesClienteAux,vDni);
              System.out.println("Cupones cargados "+ VariablesVentas.vArrayListCuponesClienteAux);
            if(VariablesVentas.vArrayListCuponesClienteAux.size()<1){
                FarmaUtility.showMessage(this,"No existen cupones para el cliente.",tblListaCupones);
                cerrarVentana(false);
            }
          }catch(SQLException sql){
             sql.printStackTrace();
             FarmaUtility.showMessage(this,"Ocurrio un error cargar cupones de cliente ",tblListaCupones);
            }   
  }*/
  
    private void cargaLista(String Dni)
    {
        try
         {
         DBVentas.obtieneCuponesCliente(tableModelCupon,Dni);
         }catch(SQLException sql)
         {
          sql.printStackTrace();
          FarmaUtility.showMessage(this,"Ocurrio un error al listar cupones.\n"+sql.getMessage(),txtLocal);
         }   
    
     }
  
   /* private void cargaLista()
    {
      // borramos todo de la tabla
      while(tableModelCupon.getRowCount()>0){
        tableModelCupon.deleteRow(0);
      }
        System.out.println("Cargando cupones "+ VariablesVentas.vArrayListCuponesClienteAux);
      for(int i=0; i<VariablesVentas.vArrayListCuponesClienteAux.size(); i++) {
          tableModelCupon.insertRow((ArrayList)(VariablesVentas.vArrayListCuponesClienteAux.get(i)));
      }
      tableModelCupon.fireTableDataChanged();
      tblListaCupones.repaint();    
    }
  */
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void btnNuscar_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtLocal);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    VariablesVentas.vArrayListCuponesClienteAux.clear();
    FarmaUtility.moveFocus(txtLocal);
    
      if(tblListaCupones.getRowCount()<1){
       FarmaUtility.showMessage(this,"No existen cupones para el clientes.",tblListaCupones);
       cerrarVentana(false);
      }
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(tblListaCupones.getRowCount()>0){
      if(txtLocal.getText().length()>0){
          System.out.println("a");
         SeleccionarCupon();
      }else{
        FarmaUtility.showMessage(this,"Debe seleccionar un cupon", txtLocal);
      }
      }
    }
    
    else if(e.getKeyCode() == KeyEvent.VK_F2){
    todos=!todos;
    seleccionarTodosCupones(todos);
    }else if (e.getKeyCode() == KeyEvent.VK_F11)
    {
        if(VariablesVentas.vArrayListCuponesCliente.size()<1 && !todos){
         FarmaUtility.showMessage(this,"Debe seleccionar al menos un cupon.", txtLocal);
        }else{
            if(FarmaUtility.rptaConfirmDialog(this,"Esta seguro de aplicar los cupones seleccionados?"))
            {
                if(todos){
                 cargarCupones();//todos seleccionados
                }
                    System.out.println("CUPONES ALMACENADOS: "+VariablesVentas.vArrayListCuponesCliente);
                    System.out.println("CANTIDAD : "+VariablesVentas.vArrayListCuponesCliente.size());
                    //cargarCuponesInactivos();//no seleccionados
                    //asignarLocales();
                    cerrarVentana(true);
            }
        }
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
     cerrarVentana(false);
    }
            
  }
  
  private void txtLocal_keyPressed(KeyEvent e)
  {
   FarmaGridUtils.aceptarTeclaPresionada(e,tblListaCupones, txtLocal,4);
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblListaCupones.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblListaCupones, txtLocal.getText().trim(),4, 4)) ) 
        {
          FarmaUtility.showMessage(this,"Cupon No Encontrado seg�n Criterio de B�squeda !!!", txtLocal);
          return;
        }
      }
    } 
    chkKeyPressed(e);
  
  }
  
  private void btnRelacionProductos_actionPerformed(ActionEvent e)
  {
  FarmaUtility.moveFocus(tblListaCupones);
  }
  
  private void txtLocal_keyReleased(KeyEvent e)
  {
  FarmaGridUtils.buscarDescripcion(e,tblListaCupones,txtLocal,4);
      SetMensaje();
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
  
  /**
   *  seleccionamos un equipo para agregarlo a la transferencia
   */
  private void SeleccionarCupon()
  {
      System.out.println("b");
      boolean seleccion = ((Boolean) tblListaCupones.getValueAt(tblListaCupones.getSelectedRow(),0)).booleanValue();
      System.out.println("seleccion"+seleccion);
     if (!seleccion)
    {
      borrarCupon();
        System.out.println("2");
      cargarCupon();
      //System.out.println("Locales Agregados :"+VariablesAdministracion.vArrayListaLocales);
      System.out.println("Cupones relacinados :"+VariablesVentas.vArrayListCuponesCliente);
      FarmaUtility.setCheckValue(tblListaCupones,false);
      tblListaCupones.setRowSelectionInterval(VariablesVentas.cPos,VariablesVentas.cPos);
    }
    else
    {
    
    System.out.println("c");
      borrarCupon();
      //VariablesAdministracion.cPos=tblListaCupones.getSelectedRow();
      //actualizaAsignacion(VariablesAdministracion.cPos);///
      // System.out.println("POSICION  :"+VariablesAdministracion.cPos);
      System.out.println("CUPONES RESTANTES :"+VariablesVentas.vArrayListCuponesCliente);
      FarmaUtility.setCheckValue(tblListaCupones, true);
    }
  }

  /**
   *  Cargamos el local relacionado a la forma de pago
   */
  private void cargarCupon()
  {
  //cambio de posicion
  String codCamp="";
    VariablesVentas.codCupon=tblListaCupones.getValueAt(tblListaCupones.getSelectedRow(),4).toString();//chek
    codCamp=VariablesVentas.codCupon.substring(0,5);
    VariablesVentas.cPos= tblListaCupones.getSelectedRow();
      
    System.out.println("VariablesVentas.codCupon--> "+VariablesVentas.codCupon);
    System.out.println("codCamp--> "+codCamp);
      
    ArrayList array=new ArrayList();
      array.add(codCamp);
    array.add(VariablesVentas.codCupon);
    //array.add("A");
    VariablesVentas.vArrayListCuponesCliente.add(array);
      System.out.println("VariablesVentas.vArrayListCuponesCliente +1--> "+VariablesVentas.vArrayListCuponesCliente);
  }
  

  /**
   * Elimina el equipo Seleccionado del conjunto
   * */
  private void borrarCupon()
  {
    
      String cod = tblListaCupones.getValueAt(tblListaCupones.getSelectedRow(),4).toString();
      
      for(int i=0;i< VariablesVentas.vArrayListCuponesCliente.size();i++)
      {
        if(((ArrayList) VariablesVentas.vArrayListCuponesCliente.get(i)).contains(cod))
        {
          VariablesVentas.vArrayListCuponesCliente.remove(i);
          break;
        }
      }
  }
  
  
  private void restarCupones(){
  
      String cod = "";
      String codaux="";
      
      System.out.println("VariablesVentas.vArrayListCuponesCliente-->"+VariablesVentas.vArrayListCuponesCliente);
      for(int i=0;i< VariablesVentas.vArrayListCuponesCliente.size() ;i++)
      {
        cod= ((String) ((ArrayList) VariablesVentas.vArrayListCuponesCliente.get(i)).get(1)).trim();
          System.out.println("cod-->"+cod);
        for(int a=0;a< tblListaCupones.getRowCount();a++)
        {
            codaux=((String) tblListaCupones.getValueAt(a,4)).trim();
            System.out.println("codaux-->"+codaux);
            if(codaux.equalsIgnoreCase(cod))
            {
                //VariablesVentas.vArrayListCuponesCliente.remove(i);
                 System.out.println("del-->"+codaux+"-->"+a);
                tableModelCupon.deleteRow(a);
                tblListaCupones.repaint();
                
                break;
            }
        }
      }
  
  }
  
  /**
   * Actualizamos el estado del local almacenado
   * */
 /* private void actualizaAsignacion(int pos){
  
    if(VariablesAdministracion.vArrayLocalesRelacionados.size()>-1 
    ){
    ((ArrayList)VariablesAdministracion.vArrayLocalesRelacionados.get(pos)).set(2,ConstantsAdministracion.INACTIVO);
    }else{
       FarmaUtility.showMessage(this,"ERROR POR DATOS(ARREGLO) VACIOS",null);
      }
  }
*/

  /**
   * Asignamos la forma de pago a los locales almacenados
   * */
 /* private void asignarLocales(){
   try
        {
        DBAdministracion.AsignarLocales(VariablesAdministracion.vArrayLocalesRelacionados);
        DBAdministracion.AsignarLocales(VariablesAdministracion.vArrayLocalesRelacionadosInactivos);
        FarmaUtility.aceptarTransaccion();
        FarmaUtility.showMessage(this, "Se asigno los locales con exito", txtLocal);
        }catch(SQLException sql)
        {
         //sql.printStackTrace();
         if(sql.getErrorCode() == 20002){
      FarmaUtility.showMessage(this,"El numero de Forma de Pago ya existe���",txtLocal);  
      }else FarmaUtility.showMessage(this,"Ocurrio un error al asignar los locales.\n"+sql.getMessage(),txtLocal);
        }   
  }*/
  
  
/**
 * Seleccionamos todos los locales disponibles  
 * */
  private void seleccionarTodosCupones(boolean valor)
  {
    for (int i = 0; i < tableModelCupon.getRowCount(); i++)
    {
      tableModelCupon.setValueAt(new Boolean(valor), i, 0);
    }
    tblListaCupones.repaint();
  }
  
/**
 * Almacenamos todos los locales para asignarlos a la forma de pago.
 * */
  private void cargarCupones()
  {
    VariablesVentas.vArrayListCuponesCliente = new ArrayList();
    String codCamp="";
    boolean valor;
    for (int i = 0; i < tblListaCupones.getRowCount(); i++)
    {
      valor = ((Boolean) tableModelCupon.getValueAt(i, 0)).booleanValue();
        codCamp=tblListaCupones.getValueAt(i,4).toString().substring(0,5);
      if (valor){
      ArrayList array=new ArrayList();
      array.add(codCamp); 
      array.add(tblListaCupones.getValueAt(i,4).toString());
      VariablesVentas.vArrayListCuponesCliente.add(array);
      }
    }
    
    System.out.println("CUPONES ALMACENADOS: "+VariablesVentas.vArrayListCuponesCliente);
    System.out.println("CANTIDAD :"+VariablesVentas.vArrayListCuponesCliente.size());
  }

    private void SetMensaje(){
        String DescpCupon="";
        if(tblListaCupones.getSelectedRow()>-1){
            DescpCupon=tblListaCupones.getValueAt(tblListaCupones.getSelectedRow(),5).toString();
            txtMensaje.setLineWrap(true);
            txtMensaje.setWrapStyleWord(true);
            txtMensaje.setFont(new Font("SansSerif", Font.BOLD, 16));
            txtMensaje.setForeground(new Color(7,133,7));
            txtMensaje.setText(DescpCupon.trim());
            //FarmaUtility.moveFocus(txtMensaje);   
        }
    
    
    }
 /*private void cargarCuponesInactivos(){
  
  VariablesAdministracion.vArrayLocalesRelacionadosInactivos = new ArrayList();
    boolean valor;
    for (int i = 0; i < tblListaCupones.getRowCount(); i++)
    {
      valor = ((Boolean) tableModelCupon.getValueAt(i, 0)).booleanValue();
      if (!valor){
      ArrayList array=new ArrayList();
      array.add(tblListaCupones.getValueAt(i,1).toString());//1
      array.add(VariablesAdministracion.cCodFormaPago);
      array.add(ConstantsAdministracion.INACTIVO);
      VariablesAdministracion.vArrayLocalesRelacionadosInactivos.add(array);
      }
    }
    
    System.out.println("LOCALES INACTIVOS ALMACENADOS: "+VariablesAdministracion.vArrayLocalesRelacionadosInactivos);
    System.out.println("CANTIDAD :"+VariablesAdministracion.vArrayLocalesRelacionadosInactivos.size());
  
  }
  */

  /*
  private void validarOrigen(){   
      if(tblListaCupones.getRowCount()<1){
         FarmaUtility.showMessage(this,"No existe equipos en el origen seleccionado", txtLocal);
         txtLocal.setEditable(false);
      }else{
        txtLocal.setEditable(true);
         lblEnter.setVisible(true);
      }
  }
  */

}

