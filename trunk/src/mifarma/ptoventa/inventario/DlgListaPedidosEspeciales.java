package mifarma.ptoventa.inventario;

import com.gs.mifarma.componentes.JLabelWhite;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
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
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DlgListaPedidosEspeciales extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel; 
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();  //  @jve:decl-index=0:visual-constraint="10,504"
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaPedidos = new JScrollPane();
  private JTable tblListaPedidos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF8 = new JLabelFunction();
  private JButtonLabel btnRelacionPedidos = new JButtonLabel();
  private JLabelFunction lblF1 = new JLabelFunction();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JTextFieldSanSerif txtFecFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecIni = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel lblFecIni = new JButtonLabel();
    private JButtonLabel lblFecFin = new JButtonLabel();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelWhite lblmensaje = new JLabelWhite();

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgListaPedidosEspeciales()
  {
    this(null, "", false);
  }

  public DlgListaPedidosEspeciales(Frame parent, String title, boolean modal)
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
    jContentPane.setSize(new Dimension(453, 323));
    this.setSize(new Dimension(645, 576));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Pedidos Especiales Realizados");
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
    pnlTitle1.setBounds(new Rectangle(10, 75, 615, 20));
    scrListaPedidos.setBounds(new Rectangle(10, 100, 615, 405));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(500, 515, 125, 25));
    lblF8.setText("[ F8 ] Ver Detalle");
    lblF8.setBounds(new Rectangle(330, 515, 150, 25));
    btnRelacionPedidos.setText("Relación de Pedidos ");
    btnRelacionPedidos.setBounds(new Rectangle(10, 0, 195, 20));    
    btnRelacionPedidos.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnRelacionPedidos_keyPressed(e);
        }
      });
    btnRelacionPedidos.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionPedidos_actionPerformed(e);
        }
      });
    lblF1.setText("[ F1 ] Nuevo");
    lblF1.setBounds(new Rectangle(10, 515, 130, 25));
        jPanelHeader1.setBounds(new Rectangle(10, 5, 615, 45));
        txtFecFin.setBounds(new Rectangle(300, 15, 110, 20));
        txtFecFin.setLengthText(10);
        txtFecFin.addKeyListener(new KeyAdapter() {
        	public void keyTyped(KeyEvent e) {
        		txtFecFin_keyTyped(e);
        	}
        	
        	public void keyPressed(KeyEvent e)
            {
        		txtFecFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e)
            {
            	txtFecFin_keyReleased(e);
            }
        });
        txtFecIni.setBounds(new Rectangle(150, 15, 110, 20));
        txtFecIni.setLengthText(10);
        txtFecIni.addKeyListener(new KeyAdapter() {
        	public void keyTyped(KeyEvent e) {
        		txtFecIni_keyTyped(e);
        	}
        	
        	public void keyPressed(KeyEvent e)
            {
        		txtFecIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e)
            {
            	txtFecIni_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(465, 15, 85, 20));
        btnBuscar.setFont(new Font("Dialog", 1, 11));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnBuscar_actionPerformed(e);
        	}
        });
        lblFecIni.setText("Rango de Fechas");
        lblFecIni.setMnemonic('R');
        lblFecIni.setBounds(new Rectangle(45, 15, 100, 20));
        lblFecIni.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		lblFecIni_actionPerformed(e);
        	}
        });
        lblFecFin.setText("Hasta");
        lblFecFin.setMnemonic('H');
      lblFecFin.setVisible(false);
        lblFecFin.setBounds(new Rectangle(170, 15, 34, 20));
        lblFecFin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		lblFecFin_actionPerformed(e);
        	}
        });
        lblF2.setBounds(new Rectangle(170, 515, 140, 25));
        lblF2.setText("[ F2 ] Modificar");
        lblmensaje.setText("");
        lblmensaje.setBounds(new Rectangle(25, 55, 600, 15));
        lblmensaje.setForeground(new Color(227, 0, 0));
        lblmensaje.setFont(new Font("SansSerif", 1, 13));
        jPanelHeader1.add(lblFecFin, null);
        jPanelHeader1.add(lblFecIni, null);
        jPanelHeader1.add(btnBuscar, null);
        jPanelHeader1.add(txtFecIni, null);
        jPanelHeader1.add(txtFecFin, null);
        jContentPane.add(lblmensaje, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblEsc, null);
        scrListaPedidos.getViewport().add(tblListaPedidos, null);
        jContentPane.add(scrListaPedidos, null);
        tblListaPedidos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        chkKeyPressed(e);
                    }
                });
        pnlTitle1.add(btnRelacionPedidos, null);
        jContentPane.add(pnlTitle1, null);
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
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaPedidosEspeciales,ConstantsInventario.columnsListaPedidosEspeciales,0);
    FarmaUtility.initSimpleList(tblListaPedidos,tableModel,ConstantsInventario.columnsListaPedidosEspeciales);
    
    //obteniendo fecha actual
    Calendar calendario = Calendar.getInstance();    
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");    
    String fecFin = formato.format(calendario.getTime());
    calendario.add(Calendar.DAY_OF_YEAR, -30);//restando 30 dias
    String fecIni = formato.format(calendario.getTime());
    //System.out.println("fec_ini : "+fecIni);
    //System.out.println("fec_Fin : "+fecFin);
    
    txtFecIni.setText(fecIni);
    txtFecFin.setText(fecFin);
    
    cargaListaPedidos(fecIni,fecFin);
    String vMensaje;
    //inicioValidacionCreacionPedReposicion();
        /*try {
            vMensaje = DBInventario.obtengoMensaje(fecIni, fecFin);
            lblmensaje.setText(vMensaje);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
    
  
  private void cargaListaPedidos(String fecIni,String fecFin)
  {  
    try
    {
      DBInventario.cargaListaPedidosEspeciales(tableModel, fecIni, fecFin);
      FarmaUtility.ordenar(tblListaPedidos,tableModel,0,FarmaConstants.ORDEN_DESCENDENTE);
      
      //if( tableModel.getRowCount() > 0 ){
    	  //FarmaUtility.moveFocus(tblListaPedidos);
    	  //btnRelacionPedidos.doClick();
      //}else {
      FarmaUtility.moveFocus(txtFecIni);
      //}
      String vMensaje;
      //inicioValidacionCreacionPedReposicion();
              vMensaje = DBInventario.obtengoMensaje(fecIni, fecFin);
        System.out.println("carga lista "+vMensaje);
              lblmensaje.setText(vMensaje);
                
      
    }catch(SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de pedidos : \n " + sql.getMessage(),btnRelacionPedidos);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnRelacionPedidos_actionPerformed(ActionEvent e)
  {
    /*if(tableModel.getRowCount()>0){
    	//System.out.println("foco en tblLista pedido");
    	FarmaUtility.moveFocus(tblListaPedidos);
    }else {*/
    FarmaUtility.moveFocus(txtFecIni);
    //} 
  }

  private void btnRelacionPedidos_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  //jcallo 16.10.2008 evento del teclado 
  private void txtFecIni_keyTyped(KeyEvent e)
  {
    //chkKeyPressed(e);
  }
  
  private void txtFecIni_keyPressed(KeyEvent e)
  {
	  if (e.getKeyCode() == KeyEvent.VK_ENTER)
          FarmaUtility.moveFocus(txtFecFin);
      else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
          cerrarVentana(false);
      chkKeyPressed(e);
  }
  
  private void txtFecIni_keyReleased(KeyEvent e)
  {
	  FarmaUtility.dateComplete(txtFecIni, e);
  }
  
  private void txtFecFin_keyTyped(KeyEvent e)
  {
    //chkKeyPressed(e);
  }
  
  private void txtFecFin_keyPressed(KeyEvent e)
  {
	  if (e.getKeyCode() == KeyEvent.VK_ENTER)
          btnBuscar.doClick();
      else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
          cerrarVentana(false);
      chkKeyPressed(e);
  }
  
  private void txtFecFin_keyReleased(KeyEvent e)
  {
	  FarmaUtility.dateComplete(txtFecFin, e);
  }
  
  
  private void lblFecIni_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtFecIni);
  }
  
  private void lblFecFin_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtFecFin);
  }

  private void btnBuscar_actionPerformed(ActionEvent e)
  {
	  if(validarFechas()){
		  cargaListaPedidos(txtFecIni.getText().trim(), txtFecFin.getText().trim());
	  }
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  private void this_windowOpened(WindowEvent e)
  {
	  /*if(tableModel.getRowCount()>0){    	
		  FarmaUtility.moveFocus(tblListaPedidos);
	  }else {*/
      //lblmensaje.setText("");
		  FarmaUtility.moveFocus(txtFecIni);
	  //} 
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaPedidos,null,0);
    
    if(e.getKeyCode() == KeyEvent.VK_F1)
    {
      
      if(FarmaVariables.vEconoFar_Matriz){// && !FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR))
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionPedidos);
      }
      else{
          //verificando si ya se abrio el dialogo NUEVO PEDIDO ESPECIAL
          if( ! VariablesInventario.vFlagF2Nuevo){
              VariablesInventario.vFlagF2Nuevo = true;
              nuevoPedido();
              
          }else{
            System.out.println("ya se encuentra abierto el dialogo de ingreso de nuevo pedido especial");
          }
      }
     /*if (lblF2.isVisible()) {
         if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
          if(FarmaVariables.vEconoFar_Matriz)
            FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionPedidos);
          else
          {
          DlgPedidoReposicionNuevo dlgPedidoReposicionNuevo = new DlgPedidoReposicionNuevo(myParentFrame,"",true);
          dlgPedidoReposicionNuevo.setVisible(true);
          if(FarmaVariables.vAceptar)
            cargaListaPedidos();
          }
         }else{
                  FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opción",null);
                    }  
     }*/
    }else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      verDetallePedido();
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }else if(e.getKeyCode() == KeyEvent.VK_F2){
        if(tblListaPedidos.getSelectedRow() > -1)
        {
            modificarPedido();
        }else
        {
          FarmaUtility.showMessage(this,"Debe seleccionar un pedido",txtFecIni);
        }        
       
        
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
  
  private void verDetallePedido()
  {
    if(tblListaPedidos.getSelectedRow() > -1)
    {
      cargarCabecera();
      DlgEspecialVer dlgEspecialVer = new DlgEspecialVer(myParentFrame,"",true);
      dlgEspecialVer.setVisible(true);   
      if(FarmaVariables.vAceptar)
      {
       cargaListaPedidos(txtFecIni.getText().trim(), txtFecFin.getText().trim());
      }
    }else
    {
      FarmaUtility.showMessage(this,"Debe seleccionar un pedido",txtFecIni);
    }
  }
  
  private void cargarCabecera()
  {
    int pos = tblListaPedidos.getSelectedRow();
    if(pos>=0){
      VariablesInventario.vNumPedidoEspecial=tblListaPedidos.getValueAt(pos,0).toString();
      VariablesInventario.vFecEmi_esp=tblListaPedidos.getValueAt(pos,1).toString();
      VariablesInventario.vEstCab_esp=tblListaPedidos.getValueAt(pos,6).toString();
    }
  }
 
  /**
   * Verifica Si se puede crear los pedido de reposicion
   */
 /* private void inicioValidacionCreacionPedReposicion(){
      String pIndCreaReposicion = "";
      lblF2.setVisible(false); 
      try{
            pIndCreaReposicion = DBInventario.getIndPedReposicionAutomatico();
            
        }catch(SQLException sql){
            pIndCreaReposicion = "N";
            sql.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Ocurrió un error al cargar parametro para crear PedRep: \n " + 
                                     sql.getMessage(), btnRelacionPedidos);
        }
      
      if(pIndCreaReposicion.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
         lblF2.setVisible(true); 
  }*/
  
  /**
   * @author JCORTEZ
   * */
  private void nuevoPedido(){
        //Modificado por DVELIZ 18.10.08
          VariablesInventario.vEsModificado = true;
      System.out.println("F1 - VariablesInventario.vEsModificado "+VariablesInventario.vEsModificado);
          limpiarTableModelEspecial();
          //FIN DVELIZ
          VariablesInventario.vFModificar = false; 
      VariablesInventario.vFNuevo = true;
	  DlgResumenPedidoEspecial dglResuPedidoEspecial = new DlgResumenPedidoEspecial(myParentFrame,"",true);
	  dglResuPedidoEspecial.setVisible(true);
	  
	  if(FarmaVariables.vAceptar){
		  //obteniendo fecha actual
		  Calendar calendario = Calendar.getInstance();    
		  SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");    
		  String fecFin = formato.format(calendario.getTime());
		  calendario.add(Calendar.DAY_OF_YEAR, -30);//restando 30 dias
		  String fecIni = formato.format(calendario.getTime());
		  //System.out.println("fec_ini : "+fecIni);
		  //System.out.println("fec_Fin : "+fecFin);
		    
		  txtFecIni.setText(fecIni);
		  txtFecFin.setText(fecFin);
		    
		  cargaListaPedidos(fecIni,fecFin);
	   }
	  
	  /*DlgEspecialListaProductos dlglistaProdEspeciales=new DlgEspecialListaProductos(myParentFrame,"",true);
      dlglistaProdEspeciales.setVisible(true);
      
      if(FarmaVariables.vAceptar)
      {
        //
    	   FarmaVariables.vAceptar = false;
    	   DlgResumenPedidoEspecial dglResuPedidoEspecial = new DlgResumenPedidoEspecial(myParentFrame,"",true);
    	   dglResuPedidoEspecial.setVisible(true);
    	   
    	   if(FarmaVariables.vAceptar){
    		   cargaListaPedidos(txtFecIni.getText(),txtFecFin.getText().trim());
    	   }
      }*/
       
      System.out.println("seteando el flag de cerrado del dialogo nuevo pedido especial");
      VariablesInventario.vFlagF2Nuevo = false;
  
  }
  
  /**
   * validarFechas 
   * @author JCALLO
   * @since 16.10.2008
   * */
  private boolean validarFechas(){
	  //quitando los espacios en blanco si es que las ubiera
	  txtFecIni.setText(txtFecIni.getText().trim());
	  txtFecFin.setText(txtFecFin.getText().trim());
	  	  
	  boolean retorno = true;
      if(!FarmaUtility.validarRangoFechas(this,txtFecIni,txtFecFin,false,true,true,true))
       retorno = false;
           
     return retorno;
	  
     
  }
  private void busquedaPedidos() {
	  if (validarFechas()) {
	      txtFecIni.setText(txtFecIni.getText().trim().toUpperCase());
	      txtFecFin.setText(txtFecFin.getText().trim().toUpperCase());
	      String FechaInicio = txtFecIni.getText().trim();
	      String FechaFin = txtFecFin.getText().trim();
	      if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
	          char primerkeyCharFI = FechaInicio.charAt(0);
	          char ultimokeyCharFI = 
	              FechaInicio.charAt(FechaInicio.length() - 1);
	          char primerkeyCharFF = FechaFin.charAt(0);
	          char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);
	
	          if (!Character.isLetter(primerkeyCharFI) && 
	              !Character.isLetter(ultimokeyCharFI) && 
	              !Character.isLetter(primerkeyCharFF) && 
	              !Character.isLetter(ultimokeyCharFF)) {
	              cargaListaPedidos(FechaInicio, FechaFin);
	          } else
	              FarmaUtility.showMessage(this, 
	                                       "Ingrese un formato valido de fechas", 
	                                       txtFecIni);
	      } else
	          FarmaUtility.showMessage(this, 
	                                   "Ingrese datos para la busqueda", 
	                                   txtFecIni);
	
	  }
  }

/**
 * Evento F3 de la interfaz
 * @author DVELIZ
 * @since 18.10.08
 */
    private void modificarPedido() {
        int selRow = tblListaPedidos.getSelectedRow();
        String vEstado = tblListaPedidos.getValueAt(selRow, 3).toString().trim();
        VariablesInventario.vNumPedidoEspecial = tblListaPedidos.getValueAt(selRow, 0).toString().trim();
        VariablesInventario.vEsModificado = false;
        VariablesInventario.vFModificar = true; 
        VariablesInventario.vFNuevo = false; 
        System.out.println("F2 -         VariablesInventario.vFNuevo = false true;        \n.vEsModificado "+VariablesInventario.vEsModificado);
        if(vEstado.equals(ConstantsInventario.ESTADO_PENDIENTE_PEDIDO_ESPECIAL) 
            || vEstado.equals(ConstantsInventario.ESTADO_MODIFICADO_PEDIDO_ESPECIAL)){
            
            
            try {
                DBInventario.getDataDetallePedido(VariablesInventario.vNumPedidoEspecial);
                DBInventario.setEstadoPedidoEspecial(VariablesInventario.vNumPedidoEspecial, ConstantsInventario.EST_MODIFICADO);
                DlgResumenPedidoEspecial dglResuPedidoEspecial = new DlgResumenPedidoEspecial(myParentFrame,"",true);
                dglResuPedidoEspecial.setVisible(true);

               // if(FarmaVariables.vAceptar){
                        //obteniendo fecha actual
                        Calendar calendario = Calendar.getInstance();    
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");    
                        String fecFin = formato.format(calendario.getTime());
                        calendario.add(Calendar.DAY_OF_YEAR, -30);//restando 30 dias
                        String fecIni = formato.format(calendario.getTime());
                        //System.out.println("fec_ini : "+fecIni);
                        //System.out.println("fec_Fin : "+fecFin);
                          
                        txtFecIni.setText(fecIni);
                        txtFecFin.setText(fecFin);
                          
                        cargaListaPedidos(fecIni,fecFin);
                // }                
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void limpiarTableModelEspecial(){
        for(int i = 0; i < VariablesInventario.tableModelEspecial.data.size(); i++){
            VariablesInventario.tableModelEspecial.setValueAt(new Boolean(false),i,0);
            VariablesInventario.tableModelEspecial.setValueAt(" ",i,6);
        }
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"