package mifarma.ptoventa.inventario;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;

import java.sql.*;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import com.gs.mifarma.componentes.JPanelWhite;
import java.awt.Rectangle;
import com.gs.mifarma.componentes.JPanelTitle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JButtonLabel;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import javax.swing.JTextArea;

import javax.swing.JTextField;

import mifarma.common.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.inventario.reference.*;
import mifarma.ptoventa.reference.*;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.ventas.DlgListaProductos;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgTransferenciasNueva.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      22.03.2005   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgTransferenciasNueva extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel;
  boolean indCabecera = false;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JButtonLabel lblCantProductos = new JButtonLabel();
  private JButtonLabel lblCantProductosT = new JButtonLabel();
    private JPanel jPanel1 = new JPanel();
    private JTextField txtBusqueda = new JTextField();
    private JButtonLabel btnBuscar = new JButtonLabel();

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgTransferenciasNueva()
  {
    this(null, "", false);
  }

  public DlgTransferenciasNueva(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(797, 423));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Nueva Transferencia de Productos");
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
    pnlTitle1.setBounds(new Rectangle(10, 50, 770, 25));
    scrListaProductos.setBounds(new Rectangle(10, 75, 770, 260));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(695, 345, 85, 20));
    btnRelacionProductos.setText("Relacion de Productos a Transferir");
    btnRelacionProductos.setBounds(new Rectangle(5, 5, 205, 15));
    btnRelacionProductos.setMnemonic('R');
    btnRelacionProductos.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnRelacionProductos_keyPressed(e);
        }
      });
    btnRelacionProductos.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionProductos_actionPerformed(e);
        }
      });
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(550, 345, 135, 20));
    lblF3.setText("[ F3 ] Insertar");
    lblF3.setBounds(new Rectangle(165, 345, 85, 20));
    lblF5.setText("[ F5 ] Borrar");
    lblF5.setBounds(new Rectangle(255, 345, 100, 20));
    lblF1.setText("[ F1 ] Ingresar Cabecera");
    lblF1.setBounds(new Rectangle(10, 345, 150, 20));
    lblCantProductos.setText("Cantidad de Productos");
    lblCantProductos.setBounds(new Rectangle(475, 5, 130, 15));
    lblCantProductosT.setBounds(new Rectangle(615, 5, 45, 15));
        jPanel1.setBounds(new Rectangle(10, 10, 770, 40));
        jPanel1.setBackground(new Color(43, 141, 39));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        txtBusqueda.setBounds(new Rectangle(75, 10, 370, 20));
        txtBusqueda.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtBusqueda_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtBusqueda_keyReleased(e);
                    }
                });
        btnBuscar.setText("Buscar");
        btnBuscar.setMnemonic('B');
        btnBuscar.setBounds(new Rectangle(20, 10, 55, 20));
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setForeground(Color.white);
        jPanel1.add(btnBuscar, null);
        jPanel1.add(txtBusqueda, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(lblCantProductosT, null);
        pnlTitle1.add(lblCantProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
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
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaProductosTransferenciasNueva,ConstantsInventario.defaultValuesListaProductosTransferenciasNueva,0);
    FarmaUtility.initSimpleList(tblListaProductos,tableModel,ConstantsInventario.columnsListaProductosTransferenciasNueva);
    //JMIRANDA 25.03.2010 BLOQUEA COLUMNAS Y REDIMENSIONAR
    tblListaProductos.getTableHeader().setReorderingAllowed(false);
    tblListaProductos.getTableHeader().setResizingAllowed(false);
  }
  
   public void cargaListaProductos()
  {
     System.out.println("entra cargarLista");  
    //if(!ConstantsInventario.IND_CAMBIO_DU){
    if(!UtilityInventario.indNuevaTransf()){
        tableModel.clearTable();
          
        if(VariablesInventario.vArrayTransferenciaProductos.size()>0)
        {
          ArrayList prods = VariablesInventario.vArrayTransferenciaProductos;
          for(int i=0;i<prods.size();i++)
          {
            tableModel.insertRow(((ArrayList)prods.get(i)));    
          }
          prods = null;
          FarmaGridUtils.moveRowSelection(tblListaProductos,0);
        }
    }
    else{             
        if(VariablesInventario.vArrayTransferenciaProductos.size()>0)
        {
          ArrayList prods = VariablesInventario.vArrayTransferenciaProductos;
          for(int i=0;i<prods.size();i++)
          {
            tableModel.insertRow(((ArrayList)prods.get(i)));    
          }
        VariablesInventario.vArrayTransferenciaProductos = null;
        prods = null;
        FarmaGridUtils.moveRowSelection(tblListaProductos,0);
        }
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnRelacionProductos_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtBusqueda);
  }

  private void btnRelacionProductos_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    //FarmaUtility.moveFocus(btnRelacionProductos);  
    FarmaUtility.moveFocus(txtBusqueda);  
    mostrarCabecera();
    lblCantProductosT.setText(""+tblListaProductos.getRowCount());
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtBusqueda);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
//    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtbusqueda,1);
    if (Character.isLetter(e.getKeyChar())) {  
        if(indCabecera)
            if (VariablesInventario.vKeyPress == null) {
                VariablesInventario.vKeyPress = e;
                agregarProducto();
                if(FarmaVariables.vAceptar){
                    cargaListaProductos();
//                    tblListaProductos.repaint();
                    lblCantProductosT.setText(""+tblListaProductos.getRowCount());
                }               
                 
            }          
        else
          FarmaUtility.showMessage(this, "Debe Ingresar primero la Cabecera.", txtBusqueda); 
       
    }
    else if(e.getKeyCode() == KeyEvent.VK_F1)
    {
      mostrarCabecera();
    }else if(e.getKeyCode() == KeyEvent.VK_F3)
    {
      if(indCabecera)
        mostrarListadoProductos(); 
      else
        FarmaUtility.showMessage(this, "Debe Ingresar primero la Cabecera.", txtBusqueda);
    }else if(e.getKeyCode() == KeyEvent.VK_F5)
    {
      if(tblListaProductos.getSelectedRow()>-1)
      {  
        if (FarmaUtility.rptaConfirmDialog(this, "Esta seguro de borrar el producto de la guia?"))
          borrarProducto();
      }
      else
        FarmaUtility.showMessage(this,"Debe seleccionar un producto",txtBusqueda);
    }else if(e.getKeyCode() == KeyEvent.VK_F11)
    {
      if(indCabecera)
      {
        if(validaDatos())
        {
          if (FarmaUtility.rptaConfirmDialog(this, "�Est� seguro de generar la transferencia?")) 
          {
          FarmaVariables.vAceptar=false;
          DlgListaImpresorasInv dlgListaImpresorasInv=new DlgListaImpresorasInv(this.myParentFrame,"",true);
          dlgListaImpresorasInv.setVisible(true);          
          
          if(!FarmaVariables.vAceptar){
          return;
          }          
            if(grabar())
            {
              //FarmaUtility.showMessage(this, "Transferencia generada!", btnRelacionProductos);
              cerrarVentana(true);
            }  
          }
        }
      }else
      {
        FarmaUtility.showMessage(this, "Debe Ingresar primero la Cabecera.", btnRelacionProductos);
      }
    }else if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(tblListaProductos.getSelectedRow()>-1)
      {
        cargarCabecera();
        //JMIRANDA 25.03.2010 INDICADOR PARA CONFIRMAR
        VariablesInventario.vIndModProdTransf = true;
        DlgTransferenciasIngresoCantidad dlgTransferenciasIngresoCantidad = new DlgTransferenciasIngresoCantidad(myParentFrame,"",true);
        dlgTransferenciasIngresoCantidad.setVisible(true);
        if(FarmaVariables.vAceptar)
        {
          actualizarProducto();
          FarmaVariables.vAceptar = false;
        }
        VariablesInventario.vIndModProdTransf = false;
      }else
        FarmaUtility.showMessage(this,"Debe seleccionar un producto",txtBusqueda);
      
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
        if (FarmaUtility.rptaConfirmDialog(this, "�Est� seguro de cerrar la ventana.\n" +
                                           "CUIDADO que no podr� recuperar todo lo trabajado.?")) 
        {
            cancelaOperacion();
            cerrarVentana(false);
        }
    }
  }
  
  private void cerrarVentana(boolean pAceptar)
	{
		FarmaVariables.vAceptar = pAceptar;
    VariablesInventario.vIndTextFraccion = "";
		this.setVisible(false);
    this.dispose();
  }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void mostrarListadoProductos() 
  {
    DlgTransferenciasListaProductos dlgTransferenciasListaProductos = new DlgTransferenciasListaProductos(myParentFrame,"",true);
    dlgTransferenciasListaProductos.setVisible(true);
    if(FarmaVariables.vAceptar)
      cargaListaProductos();
      lblCantProductosT.setText(""+tblListaProductos.getRowCount());

  }
  // JQuispe 13.04.2010 Se cambio para transferencias 
  /*

  private void borrarProducto()
  {
    String cod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),0).toString();
    String cantidad = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
    actualizaStkComprometidoProd(cod,Integer.parseInt(cantidad),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad));
    
        if(!UtilityInventario.indNuevaTransf()){
            for(int i=0;i<VariablesInventario.vArrayTransferenciaProductos.size();i++)
            {
              if(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).contains(cod))
              {
                VariablesInventario.vArrayTransferenciaProductos.remove(i);
                break;
              }
            }
            cargaListaProductos();
        }
        else{
            tableModel.deleteRow(tblListaProductos.getSelectedRow());
            tblListaProductos.repaint();
            
            FarmaGridUtils.moveRowSelection(tblListaProductos,0);
        }
        
        tblListaProductos.repaint();
        lblCantProductosT.setText(""+tblListaProductos.getRowCount());        
  }
  
  */
  
  private void borrarProducto()
  {
    String cod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),0).toString();
    String cantidad = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
    System.out.println("AAAAAAALLLL"+VariablesInventario.vArrayTransferenciaProductos);
    //JMIRANDA 20.08.2010
    //String secRespaldo=((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductos.getSelectedRow())).get(12).toString().trim();
      //JMIRANDA 20.08.2010 obtener el Sec con el tableModel          
      String secRespaldo = ((ArrayList)tableModel.data.get(tblListaProductos.getSelectedRow())).get(12).toString().trim(); 
      System.out.println("JMIRANDA AAAAAAAAAAA: "+secRespaldo);
    if(/*actualizaStkComprometidoProd(cod,
                                    Integer.parseInt(cantidad),
                                    ConstantsInventario.INDICADOR_D, 
                                    ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                    Integer.parseInt(cantidad)))*/
      actualizaStkComprometidoProd_02(cod,      //-ASOSA, 14.07.2010
                                    0,
                                    ConstantsInventario.INDICADOR_D, 
                                    ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                    0,
                                    secRespaldo))
    {
        if(!UtilityInventario.indNuevaTransf()){
            for(int i=0;i<VariablesInventario.vArrayTransferenciaProductos.size();i++)
            {
              if(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).contains(cod))
              {
                VariablesInventario.vArrayTransferenciaProductos.remove(i);
                break;
              }
            }
            cargaListaProductos();
        }
        else{
            tableModel.deleteRow(tblListaProductos.getSelectedRow());
            tblListaProductos.repaint();
            
            FarmaGridUtils.moveRowSelection(tblListaProductos,0);
        }
        
        tblListaProductos.repaint();
        lblCantProductosT.setText(""+tblListaProductos.getRowCount());    
    }
    else{
        btnRelacionProductos.doClick();
    }
    
  }
  
  private void cargarCabecera()
  {
    VariablesInventario.vCodProd_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),0).toString();
    VariablesInventario.vNomProd_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    VariablesInventario.vUnidMed_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),2).toString();
    VariablesInventario.vNomLab_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),3).toString();
    
    VariablesInventario.vCant_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
    VariablesInventario.vFechaVec_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),6).toString();
    VariablesInventario.vLote_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),7).toString();
    
    VariablesInventario.vPrecVta_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
    VariablesInventario.vStkFisico_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),8).toString();
    VariablesInventario.vValFrac_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),9).toString();
    System.out.println("VariablesInventario.vValFrac_Transf :"+VariablesInventario.vValFrac_Transf );
    VariablesInventario.vFechaHora_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),11).toString();
    
    VariablesInventario.vCant_Ingresada_Temp = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
  }
  
  private void actualizarProducto()
  {
      if(UtilityInventario.indNuevaTransf()){
       ((ArrayList)(tableModel.data.get(tblListaProductos.getSelectedRow()))).set(4,VariablesInventario.vCant_Transf);
       ((ArrayList)(tableModel.data.get(tblListaProductos.getSelectedRow()))).set(7,VariablesInventario.vLote_Transf);
       ((ArrayList)(tableModel.data.get(tblListaProductos.getSelectedRow()))).set(6,VariablesInventario.vFechaVec_Transf);
       ((ArrayList)(tableModel.data.get(tblListaProductos.getSelectedRow()))).set(10,VariablesInventario.vTotal_Transf);  
       System.out.println("1. Antes Actualizar Productos OK");
       seleccionaProducto(tblListaProductos.getSelectedRow());
       tblListaProductos.repaint();
      }
      else{
          ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductos.getSelectedRow()))).set(4,VariablesInventario.vCant_Transf);
          ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductos.getSelectedRow()))).set(7,VariablesInventario.vLote_Transf);
          ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductos.getSelectedRow()))).set(6,VariablesInventario.vFechaVec_Transf);
          ((ArrayList)(VariablesInventario.vArrayTransferenciaProductos.get(tblListaProductos.getSelectedRow()))).set(10,VariablesInventario.vTotal_Transf);
          seleccionaProducto(tblListaProductos.getSelectedRow());  
          cargaListaProductos();
      }
 
  }
  
  private boolean validaDatos()
  {
    boolean retorno = true;
    if(tblListaProductos.getRowCount()==0)
    {
      FarmaUtility.showMessage(this,"No ha ingresado productos a esta guia.",btnRelacionProductos);
      retorno = false;
    }
    
    return retorno;
  }
  
  private boolean grabar()
  {
    boolean retorno;
    int valFracProd; //JCHAVEZ 25082009.n
    String indFraccionamiento="";
    try
    {  
      String numera = DBInventario.agregarCabeceraTransferencia(VariablesInventario.vTipoDestino_Transf, 
                                                                VariablesInventario.vCodDestino_Transf, 
                                                                VariablesInventario.vMotivo_Transf,
                                                                VariablesInventario.vDestino_Transf,
                                                                VariablesInventario.vRucDestino_Transf,
                                                                VariablesInventario.vDirecDestino_Transf,
                                                                VariablesInventario.vTransportista_Transf,
                                                                VariablesInventario.vRucTransportista_Transf,
                                                                VariablesInventario.vDirecTransportista_Transf,
                                                                VariablesInventario.vPlacaTransportista_Transf, 
                                                                tblListaProductos.getRowCount()+"", 
                                                                FarmaUtility.sumColumTable(tblListaProductos,10)+"",
                                                                VariablesInventario.vMotivo_Transf_Interno);
      //System.out.println(numera);
      for(int i=0;i<tblListaProductos.getRowCount();i++)
      {
        //JCHAVEZG 25082009.sn          
          if (VariablesInventario.vTipoDestino_Transf.equals("01")) { //si la transferencia es local
            
            valFracProd= DBInventario.getValFracProducto(tblListaProductos.getValueAt(i,0).toString()); 
            indFraccionamiento=DBInventario.obtieneIndFraccLocalDestino(VariablesInventario.vCodDestino_Transf,
                                                             tblListaProductos.getValueAt(i,0).toString(),
                                                             tblListaProductos.getValueAt(i,4).toString(),
                                                             valFracProd,
                                                             FarmaConstants.INDICADOR_N);
            if  (indFraccionamiento.equals("V") ){
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Ha ocurrido un error, algunos productos no pueden ser transferidos debido a la fraccion actual del local destino.\n",null);
                retorno = false;
                return retorno;
            }
          }
        //JCHAVEZG 25082009.en  
        
        //String secRespaldo = ((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(12).toString(); //ASOSA, 15.07.2010
        //JMIRANDA 20.08.2010 obtener el Sec con el tableModel          
        String secRespaldo = ((ArrayList)tableModel.data.get(i)).get(12).toString().trim();           
        //DBInventario.agregarDetalleTransferencia(numera,tblListaProductos.getValueAt(i,0).toString(), // antes- ASOSA, 15.07.2010          
        DBInventario.agregarDetalleTransferencia_02(numera,tblListaProductos.getValueAt(i,0).toString(),
        tblListaProductos.getValueAt(i,5).toString(), tblListaProductos.getValueAt(i,10).toString(), 
        tblListaProductos.getValueAt(i,4).toString(), tblListaProductos.getValueAt(i,6).toString(),
        tblListaProductos.getValueAt(i,7).toString(),VariablesInventario.vTipoDestino_Transf,
        VariablesInventario.vCodDestino_Transf,tblListaProductos.getValueAt(i,9).toString(),indFraccionamiento,VariablesInventario.vMotivo_Transf,secRespaldo); //ASOSA, 15.07.2010, secRespaldo
        //actualizaStkComprometidoProd_sinCommit(tblListaProductos.getValueAt(i,0).toString(),Integer.parseInt(tblListaProductos.getValueAt(i,4).toString()),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(tblListaProductos.getValueAt(i,4).toString()));
      }
      
      DBInventario.grabaInicioFinCreaTransferencia(numera,"F");//JCHAVEZ 10122009 registra fecha fin de creacion de transferencia  
      
      System.out.println("VariablesInventario.vTipoFormatoImpresion : " +  VariablesInventario.vTipoFormatoImpresion );
      
      DBInventario.grabaInicioFinGuiasTransferencia(numera,"I");//JCHAVEZ 10122009 registra fecha inicio de generar guias e imprimirlas
      DBInventario.generarGuiasTransferencia(numera,VariablesInventario.vTipoFormatoImpresion,tblListaProductos.getRowCount()+"");
      FarmaUtility.aceptarTransaccion();
      //FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(this, "Transferencia generada!", tblListaProductos);
      //Imprimir Comprobantes
      VariablesInventario.vNumNotaEs = numera;
        
      //UtilityInventario.procesoImpresionComprobante(this, tblListaProductos);
        // JQUISPE 11.05.2010
      UtilityInventario.procesoImpresionGuias(this ,tblListaProductos , VariablesInventario.vTipoFormatoImpresion);  
        System.out.println("ERRRRRRRRRRRRR f");
      DBInventario.grabaInicioFinGuiasTransferencia(numera,"F");//JCHAVEZ 10122009 registra fecha fin de generar guias e imprimirlas
      FarmaUtility.aceptarTransaccion();//JCHAVEZ 10122009
      
      retorno = true;
    }catch(SQLException sql)
    {

        FarmaUtility.liberarTransaccion();
        sql.printStackTrace();
        FarmaUtility.showMessage(this,"Ha ocurrido un error al grabar la transferencia.\n"+sql.getMessage(),btnRelacionProductos);
        retorno = false;
    
      
    }catch(Exception exc)
    {
      FarmaUtility.liberarTransaccion();
      exc.printStackTrace();
      FarmaUtility.showMessage(this, "Error en la aplicacion al grabar la transferencia.\n"+exc.getMessage(),btnRelacionProductos);
      retorno = false;
    }
    return retorno;
  }
  
  private void seleccionaProducto(int pFila)
  {
    int cantIngresada = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesInventario.vCant_Transf));
    int cantIngresada_old = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesInventario.vCant_Ingresada_Temp));
      String secRespaldo = "";
    if(UtilityInventario.indNuevaTransf()){
      secRespaldo=((ArrayList)(tableModel.data.get(tblListaProductos.getSelectedRow()))).get(12).toString(); //JMIRANDA 16.09.2010
      }else{
      secRespaldo=((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(pFila)).get(12).toString(); //ASOSA, 14.07.2010
      }
    System.out.println("NOOOOOOOOO, secRespaldo: "+secRespaldo);
    boolean valor = false;
    if ( cantIngresada_old > cantIngresada ) {
      //valor = actualizaStkComprometidoProd(VariablesInventario.vCodProd_Transf,(cantIngresada_old-cantIngresada),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR, cantIngresada);  antes
      valor = actualizaStkComprometidoProd_02(VariablesInventario.vCodProd_Transf,      //-ASOSA, 14.07.2010
                                    cantIngresada,
                                    ConstantsInventario.INDICADOR_D, 
                                    ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR, 
                                    cantIngresada,
                                    secRespaldo);
      if(!valor)
          btnRelacionProductos.doClick();
    } else if ( cantIngresada_old<cantIngresada ) {
      if(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesInventario.vStk_Prod)) == 0)          
        FarmaUtility.showMessage(this, "No existe Stock disponible. Verifique!!!", btnRelacionProductos);
      else {
        //valor = actualizaStkComprometidoProd(VariablesInventario.vCodProd_Transf,(cantIngresada-cantIngresada_old),ConstantsInventario.INDICADOR_A, ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR, cantIngresada);
        valor = actualizaStkComprometidoProd_02(VariablesInventario.vCodProd_Transf,      //-ASOSA, 14.07.2010
                                      cantIngresada,
                                      ConstantsInventario.INDICADOR_A, 
                                      ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                      cantIngresada,
                                      secRespaldo);
          if(!valor)
              btnRelacionProductos.doClick();
      }
    }
    
    FarmaUtility.liberarTransaccion();
  }
  
  private boolean actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo) {
    try 
    {
      DBInventario.actualizaStkComprometidoProd(pCodigoProducto,pCantidadStk,pTipoStkComprometido);
      DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto,"", pTipoRespaldoStock, pCantidadRespaldo, Integer.parseInt(VariablesInventario.vValFrac_Transf),ConstantsPtoVenta.MODULO_TRANSFERENCIA);
      FarmaUtility.aceptarTransaccion();
      return true;
    } catch (SQLException sql) 
    {
      FarmaUtility.liberarTransaccion();
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\nPonganse en contacto con el area de Sistemas\n"+sql.getMessage(), btnRelacionProductos);
      return false;
    }
  }
  
  private void actualizaStkComprometidoProd_sinCommit(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo) throws SQLException
  {
    DBInventario.actualizaStkComprometidoProd(pCodigoProducto,pCantidadStk,pTipoStkComprometido);
    DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto,"", pTipoRespaldoStock, pCantidadRespaldo, Integer.parseInt(VariablesInventario.vValFrac_Transf),ConstantsPtoVenta.MODULO_TRANSFERENCIA);
  }
  
  private void cancelaOperacion() {
    String codProd = "";
    String cantidad = "";

    if(UtilityInventario.indNuevaTransf()){
        if(tableModel.data.size()>=0){
            for(int i=0; i<tableModel.data.size(); i++){
              codProd = FarmaUtility.getValueFieldArrayList(tableModel.data,i,0).trim();
              cantidad = FarmaUtility.getValueFieldArrayList(tableModel.data,i,4).trim();
                //String secRespaldo=((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(12).toString(); //ASOSA, 14.07.2010
                //JMIRANDA 20.08.2010 obtener el Sec con el tableModel          
                String secRespaldo = ((ArrayList)tableModel.data.get(i)).get(12).toString().trim(); 
              //actualizaStkComprometidoProd(codProd,Integer.parseInt(cantidad),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad));
              actualizaStkComprometidoProd_02(codProd,      //-ASOSA, 14.07.2010
                                            0,
                                            ConstantsInventario.INDICADOR_D, 
                                            ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                            0,
                                            secRespaldo);
            }  
        }
    }  
    else{
        if(tblListaProductos.getRowCount()>=0){
            for(int i=0; i<tblListaProductos.getRowCount(); i++){
              codProd = ((String)(tblListaProductos.getValueAt(i,0))).trim();
              cantidad = ((String)(tblListaProductos.getValueAt(i,4))).trim();
                //String secRespaldo=((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(12).toString(); //ASOSA, 14.07.2010
                //JMIRANDA 20.08.2010 obtener el Sec con el tableModel          
                String secRespaldo = ((ArrayList)tableModel.data.get(i)).get(12).toString().trim(); 
              //actualizaStkComprometidoProd(codProd,Integer.parseInt(cantidad),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad));
                actualizaStkComprometidoProd_02(codProd,      //-ASOSA, 14.07.2010
                                              0,
                                              ConstantsInventario.INDICADOR_D, 
                                              ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                              0,
                                              secRespaldo);
            }
        }
    }
    inicializaArrayList();
  }
  
  private void inicializaArrayList() {
    VariablesInventario.vArrayTransferenciaProductos = new ArrayList();
    
  }
  
  private void mostrarCabecera()
  {
    if(!indCabecera) 
    {
      VariablesInventario.vIndTextFraccion = "";
      DlgTransferenciasTransporte dlgTransferenciasTransporte = new DlgTransferenciasTransporte(myParentFrame,"",true);
      dlgTransferenciasTransporte.setVisible(true);
      if(FarmaVariables.vAceptar) 
      {
        indCabecera = true;
        if(!UtilityInventario.indNuevaTransf()){
            mostrarListadoProductos();
        }
      }
    }
    /*else
      FarmaUtility.showMessage(this, "Debe Ingresar primero la Cabecera.", btnRelacionProductos);*/
  }

    private void txtBusqueda_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBusqueda,1);
        //**
        
        String vCadIngresada = txtBusqueda.getText();
        if(!UtilityVentas.isNumerico(vCadIngresada))
           chkKeyPressed(e);
        
        else{
          if (e.getKeyCode() == KeyEvent.VK_ENTER){
           //JCORTEZ 25/04/08
             String codProd=txtBusqueda.getText().trim();
             if(UtilityVentas.isNumerico(codProd)){
                 
                    String cadena = codProd.trim();
                    String formato = "";
                    if(cadena.trim().length()>6)
                       formato = cadena.substring(0, 5);
                    
                    if(cadena.trim().length()>6)
                       formato = cadena.substring(0, 5);
                    if (formato.equals("99999")) 
                        return;
                    
               if(codProd.length()==6){
                   VariablesInventario.vBusquedaProdTransf = codProd;
                  
                  ArrayList myArray = new ArrayList();
                  obtieneInfoProdEnArrayList(myArray,codProd);
                   System.out.println("Lista Prod Transferencia "+myArray);
                   if(myArray.size()==1){
                    VariablesInventario.vKeyPress = e;
                    agregarProducto();
                    if(FarmaVariables.vAceptar){                        
                        cargaListaProductos();
                        lblCantProductosT.setText(""+tblListaProductos.getRowCount());
                    } 
                    VariablesInventario.vBusquedaProdTransf = "";
                    VariablesInventario.vKeyPress = null;
                   }else{
                    FarmaUtility.showMessage(this,"Producto No Encontrado seg�n Criterio de B�squeda !!!",txtBusqueda);
                   }
                }
              }
          }
        }       
           // ****
       // chkKeyPressed(e);
    }

    private void txtBusqueda_keyReleased(KeyEvent e) {
       // FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBusqueda,1);
       String cadena = txtBusqueda.getText().trim();
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {     
           if(UtilityVentas.isNumerico(cadena))
          {
            //System.out.println("presiono enter");
            e.consume();
            String productoBuscar = txtBusqueda.getText().trim().toUpperCase();
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
            
            if( productoBuscar.length()>6 && 
                ( !Character.isLetter(primerkeyChar) && 
                  (!Character.isLetter(segundokeyChar) && 
                    (!Character.isLetter(ultimokeyChar))) )) 
            {
              String cod_barra = productoBuscar + "";
              txtBusqueda.setText(cod_barra);
              // System.out.println("cod_barra "+cod_barra);
              VariablesInventario.vBusquedaProdTransf = cod_barra;
              VariablesInventario.vKeyPress = e;
              agregarProducto();
              if(FarmaVariables.vAceptar){
                  cargaListaProductos();
                  lblCantProductosT.setText(""+tblListaProductos.getRowCount());   
              }                    
              VariablesInventario.vKeyPress = null;
            }
            
            FarmaUtility.moveFocus(txtBusqueda);
            txtBusqueda.setText("");                
          }
            
        }
    }
    
    private void agregarProducto() 
    {
        DlgTransferenciasListaProductos DlgTransfLista = new DlgTransferenciasListaProductos(myParentFrame,"",true);
        DlgTransfLista.setVisible(true);
      
        txtBusqueda.setText("");     
    }
    
    private void obtieneInfoProdEnArrayList(ArrayList pArrayList,String codProd)
    {
     try
     {
        DBVentas.obtieneInfoProducto(pArrayList, codProd);
     }
     catch (SQLException sql)
     {
       sql.printStackTrace();
       FarmaUtility.showMessage(this,"Error al obtener informacion del producto en Arreglo. \n" +sql.getMessage(), txtBusqueda);
     }
    }
    
    //-------------------------------------------=ASOSA=------------------------------------------->>>>><<<<
    
    private boolean actualizaStkComprometidoProd_02(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo, String secRespaldo) {
        VariablesInventario.secRespStk="0";
        boolean flag=true;
        return flag;
      /*try 
      {
      
        VariablesInventario.secRespStk=""; //ASOSA, 26.08.2010
          VariablesInventario.secRespStk=DBVentas.operarResStkAntesDeCobrar(pCodigoProducto,
                                                                            String.valueOf(pCantidadStk),
                                                                            VariablesInventario.vValFrac_Transf,
                                                                            secRespaldo,
                                                                            ConstantsPtoVenta.MODULO_TRANSFERENCIA);
          boolean flag=true;
          if(VariablesInventario.secRespStk.trim().equalsIgnoreCase("N")){
              FarmaUtility.liberarTransaccion();
              flag=false;
          }else{
              FarmaUtility.aceptarTransaccion();
              flag=true;
          }
          return flag;
      } catch (SQLException sql) 
      {
        FarmaUtility.liberarTransaccion();
        sql.printStackTrace();
        FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\nPonganse en contacto con el area de Sistemas\n"+sql.getMessage(), btnRelacionProductos);
        return false;
      }
        */
    }
    

}
