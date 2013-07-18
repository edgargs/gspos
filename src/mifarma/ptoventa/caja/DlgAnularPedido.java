package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelHeader;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
 
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.DlgConsultaXCorrelativo;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import mifarma.common.*;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelHeader;

import mifarma.ptoventa.ventas.DlgConsultaXCorrelativo;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

public class DlgAnularPedido extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  private static final Logger log = LoggerFactory.getLogger(DlgAnularPedido.class);
         
  FarmaTableModel tableModelCabeceraPedido;
  FarmaTableModel tableModelDetallePedido;
  Frame myParentFrame;
  private boolean vAceptar=false;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JPanel jPanel4 = new JPanel();
  private JButton btnDetalle = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JPanel jPanel3 = new JPanel();
  private JButton btnCabecera = new JButton();
  private JPanelHeader pnlHeader = new JPanelHeader();
  private JTextField txtMonto = new JTextField();
  private JLabel lblMonto = new JLabel();
  private JButton btnBuscar = new JButton();
  private JTextField txtCorrelativo = new JTextField();
  private JButton btnCorrelativo = new JButton();
  private JTable tblCabeceraPedido = new JTable();
  private JTable tblDetallePedido = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelOrange lblMensaje = new JLabelOrange();
    //  25.08.2011 Fijar Objeto para Focus
    private Object pObj = txtCorrelativo;
    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgAnularPedido()
  {
    this(null, "", false);
  }

  public DlgAnularPedido(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
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
    this.setSize(new Dimension(787, 390));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Anulacion de Pedidos");
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
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(513, 307));
    jContentPane.setBackground(Color.white);
    jScrollPane2.setBounds(new Rectangle(5, 190, 770, 140));
    jScrollPane2.setBackground(new Color(255, 130, 14));
    jPanel4.setBounds(new Rectangle(5, 170, 770, 20));
    jPanel4.setBackground(new Color(255, 130, 14));
    jPanel4.setLayout(null);
    btnDetalle.setText("Detalle Pedido");
    btnDetalle.setBounds(new Rectangle(15, 0, 115, 20));
    btnDetalle.setMnemonic('d');
    btnDetalle.setBackground(new Color(255, 130, 14));
    btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnDetalle.setBorderPainted(false);
    btnDetalle.setContentAreaFilled(false);
    btnDetalle.setDefaultCapable(false);
    btnDetalle.setFocusPainted(false);
    btnDetalle.setForeground(Color.white);
    btnDetalle.setFont(new Font("SansSerif", 1, 11));
    btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
    btnDetalle.setRequestFocusEnabled(false);
    btnDetalle.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnDetalle_keyPressed(e);
        }
      });
    btnDetalle.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetalle_actionPerformed(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(5, 80, 770, 80));
    jScrollPane1.setBackground(new Color(255, 130, 14));
    jPanel3.setBounds(new Rectangle(5, 60, 770, 20));
    jPanel3.setBackground(new Color(255, 130, 14));
    jPanel3.setLayout(null);
    btnCabecera.setText("Cabecera Pedido");
    btnCabecera.setBounds(new Rectangle(15, 0, 115, 20));
    btnCabecera.setMnemonic('c');
    btnCabecera.setBackground(new Color(255, 130, 14));
    btnCabecera.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnCabecera.setBorderPainted(false);
    btnCabecera.setContentAreaFilled(false);
    btnCabecera.setDefaultCapable(false);
    btnCabecera.setFocusPainted(false);
    btnCabecera.setForeground(Color.white);
    btnCabecera.setFont(new Font("SansSerif", 1, 11));
    btnCabecera.setHorizontalAlignment(SwingConstants.LEFT);
    btnCabecera.setRequestFocusEnabled(false);
    btnCabecera.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnCabecera_keyPressed(e);
        }
      });
    btnCabecera.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCabecera_actionPerformed(e);
        }
      });
    pnlHeader.setBounds(new Rectangle(5, 5, 770, 45));
    txtMonto.setBounds(new Rectangle(300, 10, 80, 25));
    txtMonto.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtMonto_keyPressed(e);
        }

        public void keyTyped(KeyEvent e)
        {
                        txtMonto_keyTyped(e);
                    }
      });
    lblMonto.setText("Monto :");
    lblMonto.setBounds(new Rectangle(245, 10, 55, 25));
    lblMonto.setFont(new Font("SansSerif", 1, 11));
    lblMonto.setForeground(Color.white);
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(480, 10, 90, 25));
    btnBuscar.setMnemonic('s');
    btnBuscar.setRequestFocusEnabled(false);
    btnBuscar.setDefaultCapable(false);
    btnBuscar.setFocusPainted(false);
    btnBuscar.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnBuscar_keyPressed(e);
        }
      });
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnBuscar_actionPerformed(e);
                    }
      });
    txtCorrelativo.setBounds(new Rectangle(90, 10, 115, 25));
    txtCorrelativo.setFont(new Font("SansSerif", 0, 12));
    txtCorrelativo.addKeyListener(new KeyAdapter()
      {

        public void keyTyped(KeyEvent e)
        {
                        txtCorrelativo_keyTyped(e);
                    }

        public void keyPressed(KeyEvent e)
        {
          txtCorrelativo_keyPressed(e);
        }
      });
    btnCorrelativo.setText("Correlativo :");
    btnCorrelativo.setBounds(new Rectangle(15, 15, 70, 15));
    btnCorrelativo.setMnemonic('r');
    btnCorrelativo.setBackground(new Color(255, 130, 14));
    btnCorrelativo.setBorderPainted(false);
    btnCorrelativo.setContentAreaFilled(false);
    btnCorrelativo.setDefaultCapable(false);
    btnCorrelativo.setFocusPainted(false);
    btnCorrelativo.setForeground(Color.white);
    btnCorrelativo.setHorizontalAlignment(SwingConstants.LEFT);
    btnCorrelativo.setFont(new Font("SansSerif", 1, 11));
    btnCorrelativo.setRequestFocusEnabled(false);
    btnCorrelativo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnCorrelativo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCorrelativo_actionPerformed(e);
        }
      });
        tblCabeceraPedido.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblCabeceraPedido_keyPressed(e);
                    }
                });
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(685, 335, 90, 20));
    lblF11.setText("[ F11 ] Anular");
    lblF11.setBounds(new Rectangle(590, 335, 85, 20));
        lblMensaje.setBounds(new Rectangle(10, 340, 475, 35));
        lblMensaje.setForeground(Color.red);
        lblMensaje.setFont(new Font("SansSerif", 1, 19));
        jScrollPane2.getViewport();
    jScrollPane1.getViewport();
        pnlHeader.add(txtMonto, null);
        pnlHeader.add(lblMonto, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtCorrelativo, null);
        pnlHeader.add(btnCorrelativo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblMensaje, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jScrollPane2.getViewport().add(tblDetallePedido, null);
        jContentPane.add(jScrollPane2, null);
        jPanel4.add(btnDetalle, null);
        jContentPane.add(jPanel4, null);
        jScrollPane1.getViewport().add(tblCabeceraPedido, null);
        jContentPane.add(jScrollPane1, null);
        jPanel3.add(btnCabecera, null);
        jContentPane.add(jPanel3, null);
        //this.getContentPane().add(jContentPane, null);
        jContentPane.add(pnlHeader, null);
        txtCorrelativo.setDocument(new FarmaLengthText(10));
  }
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTableCabeceraPedido();
    initTableDetallePedido();
    VariablesCaja.vIndAnulacionConReclamoNavsat = false;
    FarmaVariables.vAceptar=false;
      lblMensaje.setText("");
      VariablesCaja.vDescripImpr=""; 
      setearObjetoFocus();      
  }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

  private void initTableCabeceraPedido()
  {
      
    tblCabeceraPedido.getTableHeader().setReorderingAllowed(false);
    tblCabeceraPedido.getTableHeader().setResizingAllowed(false);
    
    tblDetallePedido.getTableHeader().setReorderingAllowed(false);
    tblDetallePedido.getTableHeader().setResizingAllowed(false);
  
    tableModelCabeceraPedido = new FarmaTableModel(ConstantsCaja.columnsListaPedidos,ConstantsCaja.defaultValuesListaPedidos,0);
    FarmaUtility.initSimpleList(tblCabeceraPedido,tableModelCabeceraPedido,ConstantsCaja.columnsListaPedidos);
  }
  
  private void cargaListaPedidos()
  {
    try
    {
      DBCaja.getListaCabeceraPedido(tableModelCabeceraPedido,txtCorrelativo.getText().trim());
        System.err.println("Listado:"+tableModelCabeceraPedido.data.get(0) );
    }catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al listar los pedidos  - \n" + e.getMessage(),pObj);
    }
  }
  
  private void initTableDetallePedido()
  {
    tableModelDetallePedido = new FarmaTableModel(ConstantsCaja.columnsListaProductos,ConstantsCaja.defaultValuesListaProductos,0);
    FarmaUtility.initSimpleList(tblDetallePedido,tableModelDetallePedido,ConstantsCaja.columnsListaProductos);
  }
  
  private void cargaListaProductos()
  {
    try
    {
      DBCaja.getListaDetallePedido(tableModelDetallePedido,txtCorrelativo.getText().trim(),"%","%");
    }catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al listar los productos  - \n" + e.getMessage(),pObj);
    }
  }
  
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

  private void btnCorrelativo_actionPerformed(ActionEvent e)
  {
      //  22.08.2011
      if(UtilityVentas.getIndImprimeCorrelativo()){
        txtCorrelativo.enable(true);
        txtMonto.enable(true);
        FarmaUtility.moveFocus(txtCorrelativo);
      }
      else{
          mostrarCorrelativoXComprobante();
      }    
  }

  private void txtCorrelativo_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      txtCorrelativo.setText(FarmaUtility.caracterIzquierda(txtCorrelativo.getText(),10,"0"));
      FarmaUtility.moveFocus(txtMonto);
    }
    else
      chkKeyPressed(e);
  }

  private void txtCorrelativo_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtCorrelativo,e);
  }

  private void txtMonto_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      //FarmaUtility.moveFocus(btnBuscar);
      btnBuscar.doClick();
    }
    else
      chkKeyPressed(e);
  }

  private void txtMonto_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitosDecimales(txtMonto,e);

  }
  
  private void btnBuscar_actionPerformed(ActionEvent e)
  {
      lblMensaje.setText("");
        if(validarCampos())
        {
          //Agregado por   05.01.2009
          VariablesCaja.vIndLineaADMCentral = "N";//indicador de linea en N
          evaluaPedidoProdVirtual(txtCorrelativo.getText().trim());//verifica si es un pedido virtual
          if(VariablesVirtual.vConProductoVirtual){
              validarConexionADMCentral();//VariablesCaja.vIndLineaADMCentral
          }
          log.debug(" : antes de buscar pedido VariablesCaja.vIndLineaADMCentral:"+VariablesCaja.vIndLineaADMCentral);
          if(buscarPedido())
          {
            cargaListaPedidos();
            cargaListaProductos();
            
            //btnCabecera.doClick();
          } else
          {
            tableModelCabeceraPedido.clearTable();
            tableModelDetallePedido.clearTable();
            tblCabeceraPedido.removeAll();
            tblDetallePedido.removeAll();
          }
        }

  }
  
  /**
   * @AUTHOR  
   * @SINCE 10.06.09
   * * */
  private boolean  validaImprIP(){
    
        boolean valor=true;
        String tipComp="";
    try{
       //  09.06.09  Se obtiene tipo de comrpobante de la relacion maquina - impresora
        tipComp=VariablesCaja.vTipComp.trim();
         if(tipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
           //VariablesCaja.vSecImprLocalTicket= DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);
            VariablesCaja.vSecImprLocalTicket= DBCaja.getObtieneSecImpPorOrigen(FarmaVariables.vIpPc,tipComp,
                                                VariablesCaja.vNumPedVta_Anul);
            
              log.debug("ANULANDO PEDIDO");
              log.debug(" : VariablesCaja.vTipComp--> "+VariablesCaja.vTipComp);
            log.debug(" : VariablesCaja.vSecImprLocalTicket--> "+VariablesCaja.vSecImprLocalTicket);
           if(VariablesCaja.vSecImprLocalTicket.trim().equalsIgnoreCase("N")){
               FarmaUtility.showMessage(this,"No se cuenta con una impresora asignada de ticket. Verifique!!!",pObj);
               valor=false;
           }
        }
    }catch(SQLException e){
        e.printStackTrace();
        FarmaUtility.showMessage(this,"Error al validar relacion IP - impresora. Verifique!!!",pObj);                
        valor=false;
    }
        return valor;
 }

  private void btnBuscar_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        //  22.08.2011
        if(UtilityVentas.getIndImprimeCorrelativo()){
            FarmaUtility.moveFocus(tblCabeceraPedido);}
        else{        
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        }
    }
    else
      chkKeyPressed(e);
  }
  
  private void btnCabecera_actionPerformed(ActionEvent e)
  {
    //  22.08.2011
    if(UtilityVentas.getIndImprimeCorrelativo()){
        FarmaUtility.moveFocus(tblCabeceraPedido);}
    else{        
        FarmaUtility.moveFocusJTable(tblCabeceraPedido);
    }
  }
  
  private void btnCabecera_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblCabeceraPedido,null,0);
    
    chkKeyPressed(e);
  }

  private void btnDetalle_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(btnDetalle);
  }

  private void btnDetalle_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblDetallePedido,null,0);
     chkKeyPressed(e);
  }
  
  private void this_windowOpened(WindowEvent e)
  {    
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtCorrelativo);
    cargaLogin();
        
    //Verifica si muestra Nueva pantalla
    if(!validarMostrarCorrelativo() && this.isVisible() ){
        mostrarCorrelativoXComprobante();
        FarmaUtility.moveFocus(tblCabeceraPedido);    
    }
        
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", pObj);
  }
  
  private void tblCabeceraPedido_keyPressed(KeyEvent e) {
        
        chkKeyPressed(e);
  }
  
/* ************************************************************************ */
/*                     METODOS AUXILIARES DE EVENTOS                        */
/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_F11)
    {
      if(vAceptar)
      {
       if(tblCabeceraPedido.getRowCount()>0)
       {
    	  VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
          
             anularPedido();
          
      }
      }
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar){
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void cargaLogin()
  {
    DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
    dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
    dlgLogin.setVisible(true);
  
            
    if ( FarmaVariables.vAceptar ) {
      FarmaVariables.dlgLogin = dlgLogin;
      FarmaVariables.vAceptar = false;
    } else  cerrarVentana(false);
  }

  private boolean validarCampos()
  {
   boolean retorno=true;
   if(txtCorrelativo.getText().trim().equals(""))
   {
     FarmaUtility.showMessage(this,"Debe ingresar el Correlativo.",txtCorrelativo);
     retorno = false;
   }else if(txtMonto.getText().trim().equals(""))
   {
     FarmaUtility.showMessage(this,"Debe ingresar el Monto.",txtMonto);
     retorno = false;
   }else if(!FarmaUtility.validateDecimal(this,txtMonto,"Debe ingresar un Monto Válido.",false))
   {
     retorno = false;
   }
   return retorno;
  }
  
  private boolean buscarPedido()
  {
    
    VariablesCaja.vIndAnulacionConReclamoNavsat = false;
    boolean retorno=false;
    vAceptar = false;
    try
    {
    	log.debug(" : antes de verificarPedido");
      DBCaja.verificaPedido(txtCorrelativo.getText().trim(),txtMonto.getText().trim());
      	log.debug(" : despues de verificarpedido");
      //AGREGADO POR   30.12.2008
      //validarConexionADMCentral();
      //obtieneInfoPedidoVirtual();
      log.debug(" : VariablesVirtual.vConProductoVirtual:"+VariablesVirtual.vConProductoVirtual);
      if(VariablesVirtual.vConProductoVirtual){
    	  log.debug(" : ... VariablesCaja.vIndLineaADMCentral : "+VariablesCaja.vIndLineaADMCentral);
          if(VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_N)){
              if(FarmaUtility.rptaConfirmDialog(this, "Para anular un pedido sin conexion a MATRIZ logearse como OPERADOR DEL SISTEMA, desea continuar?")){
                  if( cargaLoginAdmLocal() )
                  {
                    retorno = true;
                    vAceptar = true;
                    VariablesCaja.vIndAnulacionConReclamoNavsat = true;
                    obtieneInfoPedidoVirtual(); 
                    VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
                    return retorno;
                  } else
                  {
                    FarmaUtility.showMessage(this,"No es posible realizar la operación.",pObj);
                    return retorno;
                  }
              }
              /* FarmaUtility.showMessage(this, "No existe conexion con MATRIZ. Intentelo mas tarde.", null);
               retorno = false;
               vAceptar = false;
              return retorno;*/
          }else{
               obtieneInfoPedidoVirtual(); 
               VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim(); 
               VariablesCaja.vRespuestaExito = obtieneRespuestaRecarga().trim(); 
               retorno = true;
               vAceptar = true;
               return retorno;
          }

           //FIN   
      }else{
          retorno = true;
          vAceptar = true;
          return retorno;
      }
       

    }catch(SQLException e)
    {
    
       /* //Agregado por   15.12.2008
        validarConexionADMCentral();
        obtieneInfoPedidoVirtual();
        if(VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_N)){
            FarmaUtility.showMessage(this, "No existe conexion con MATRIZ. Intentelo mas tarde.", null);
            retorno = false;
            vAceptar = false;
            return retorno;
        }
        //Fin  */
        
        retorno = false;
        vAceptar = false;
      if(e.getErrorCode()==20001)
      {
        FarmaUtility.showMessage(this,"No se encuentra ningun Pedido con estos datos. ¡Verifique!",pObj);
      }else if(e.getErrorCode()==20002)
      {
        FarmaUtility.showMessage(this,"El Pedido no ha sido cobrado. ¡No puede Anular este Pedido!",pObj);
      }else if(e.getErrorCode()==20003)
      {
        FarmaUtility.showMessage(this,"El Pedido ya está anulado. ¡No puede Anular este Pedido!",pObj);
      } else if(e.getErrorCode()==20004)
      {
        if(FarmaUtility.rptaConfirmDialog(this,"El pedido no pueder ser anulado porque cuenta con un producto del tipo Tarjeta Virtual.\nEsta seguro de anular de todas formas?."))
        {
          if( cargaLoginAdmLocal() )
          {
            retorno = true;
            vAceptar = true;
            VariablesCaja.vIndAnulacionConReclamoNavsat = true;
          } else
          {
            FarmaUtility.showMessage(this,"No es posible realizar la operación.",pObj);
          }
        }
      } else if(e.getErrorCode()==20005)
      {        
        /**
         * SE OBTIENE EL VALOR DE TIEMPO MAXIMO QUE PASO PARA ANULAR UNA RECARGA VIRTUAL
         * @AUTHOR  
         * @SINCE  09.11.2007
         * @modificado   05.01.2009
         */
        VariablesCaja.vIndPedidoRecargaVirtual = FarmaConstants.INDICADOR_S; 
        VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
        String tiempo_maximo  = time_max(txtCorrelativo.getText().trim()).trim();        
        String indExitoRecarga = obtieneRespuestaRecarga().trim();
        //  String indExitoRecarga = "00";
        log.debug("  : indExitoRecarga : "+indExitoRecarga);
        if(indExitoRecarga.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
            retorno = false;
            vAceptar = false;    
            FarmaUtility.showMessage(this,"No es posible realizar la operación.\n"+
                                          "No se pudo obtener la respuesta de la recarga.",pObj);            
        }
        else{
            if(indExitoRecarga.trim().equals("00")||indExitoRecarga.trim().equals("-2")){
                       //Se anulara el pedido si es Exito o Si no hay respuesta
                        //FarmaUtility.showMessage(this,"No se puede anular este pedido el tiempo es mayor a "+tiempo_maximo+" minutos, y la recarga se realizo con exito" ,txtCorrelativo);
                           if(FarmaUtility.rptaConfirmDialog(this,"El pedido cuenta con un producto del tipo Recarga Virtual\ny fue cobrado hace más de "+tiempo_maximo+" minutos.\nEsta seguro de anular de todas formas?."))     //AGREGADO POR   15.12.2008
                           {
                             if( cargaLoginAdmLocal() )
                             {
                               retorno = true;
                               vAceptar = true;
                               VariablesCaja.vIndAnulacionConReclamoNavsat = true;
                               VariablesCaja.vRespuestaExito = indExitoRecarga;
                             } else
                             {
                                 retorno = false;
                                 vAceptar = false;    
                               FarmaUtility.showMessage(this,"No es posible realizar la operación.",pObj);
                             }
                           } 
                   }else{
                       retorno = true;
                       vAceptar = true;
                       VariablesCaja.vRespuestaExito = indExitoRecarga;
                   }            
        }
        log.debug("  : indExitoRecarga : "+indExitoRecarga);
        VariablesCaja.vIndPedidoRecargaVirtual = "";        
        return retorno;
      }else if(e.getErrorCode()==20015)
      {
        FarmaUtility.showMessage(this,"El pedido generó cupones y ya fueron usados. No puede anular el comprobante.",pObj);
      }
      else
      {
        e.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrió un error al buscar un pedido  - \n" +e.getMessage(),pObj);
      }
    }
    return retorno;
  }
  
  private void anularPedidoBTLMF()
  {
    cargarVariables();

    int vCantCuponesEmitidosUsados = 0;
     //--Obtiene los cupones emitidos usados
    vCantCuponesEmitidosUsados = UtilityCaja.getCuponesEmitidosUsados(txtCorrelativo.getText().trim(), this, pObj);
    if(vCantCuponesEmitidosUsados > 0)
     {
              FarmaUtility.showMessage(this,"Los cupones que generó este pedido han sido usados."+
             " Por favor verifique si se debe anular dicho canje.",pObj);
     }



		    if(comprobarFecha())// || comprobarDocumento())
		    {
		      if(!comprobarDocumentoNotaCredito())
		      {
		        FarmaUtility.showMessage(this, "No se puede anular un pedido de tipo Nota de Crédito. Verifique!!!",pObj);
		      } else
		      {

		        /**
		        * @AUTHOR  
		        * @SINCE 10.06.09
		        * Se evitara anulara si es que la ip no cuenta con impresora
		        * */
		        if (validaImprIP()){

		          /**
		           * Valida pedido Fidelizado
		           * @author:  
		           * @since: 18/12/2008
		           * */
		           System.out.println("VariablesCaja.vIndPedFidelizado : " +VariablesCaja.vIndPedFidelizado);
		           if(VariablesCaja.vIndPedFidelizado.equalsIgnoreCase("S")){
		        	   System.err.println("es un pedido de un cliente fidelizado ...");
		           //if(validaExistProd()){
		        	   log.debug("aqui verificara si hay linea con matriz...ELLO IMPLICA CUALQUIER CONEXION REMOTA que se tenga se perdera" +
		        	   		"por lo que es PROBABLE que hayas problemas al eliminar recargas virtuales que compro un cliente fidelizado");
		              /*
		              if(ExistLineaMatriz()){//aqui al momento de preguntar por si hay linea con matriz
		                  System.err.println("ANULA PRUEBA anularPedidofidelizadoMatriz");
		                  if(anularPedidofidelizadoMatriz(FarmaConstants.INDICADOR_N))
		                      //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
		                        anular();
		              }else{
		                   System.err.println("ANULA PRUEBA anularPedidofidelizado");
		                 if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
		                  anular();
		               }
		              */
		              //////////
		              System.err.println("  - 30.11.2009 - ANULA PRUEBA anularPedidofidelizado");
		              //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
		              //  18.03.10 se valida pedido antes de procesarlo
		               if(validaPedido(FarmaConstants.INDICADOR_S))
		                 anular();

		           //}
		           }else {
		             //No es fidelizado
		              System.out.println("No es fidelizado");
		              anular();
		           }
		        }
		      }
		    }
		    else
		    {
		      if(comprobarProductosRestantes())
		      {
		          if (validaImprIP()){   //  10.07.2009.n
		              FarmaUtility.showMessage(this,"Este Pedido correponde a un periodo anterior. \nNo se anulará dicho pedido, pero se generará una solicitud de Nota de Crédito.",pObj);
		              DlgNotaCreditoNueva dlgNotaCreditoNueva = new DlgNotaCreditoNueva(myParentFrame,"",true);
		              dlgNotaCreditoNueva.setVisible(true);
		              if(FarmaVariables.vAceptar)
		              {
		                cerrarVentana(true);
		              }
		          }  //  10.07.2009.n
		      }
		    }

    }



  private void anularPedido()
  {
    cargarVariables();
    
    int vCantCuponesEmitidosUsados = 0;
    
    /*
    try
    {
      vCantCuponesUsados = DBCaja.cuponesUsados(txtCorrelativo.getText().trim());
      if(vCantCuponesUsados > 0)
      {
        FarmaUtility.showMessage(this,"Los cupones que generó este pedido han sido usados."+ 
       " Por favor verifique si se debe anular dicho canje.",txtCorrelativo);
      }
    }
    catch (SQLException e)
    {
      log.error(null,e);
    }
    */
    //--Obtiene los cupones emitidos usados
    vCantCuponesEmitidosUsados = UtilityCaja.getCuponesEmitidosUsados(txtCorrelativo.getText().trim(),
                                                                      this,
                                                                      pObj);
    
    if(vCantCuponesEmitidosUsados > 0)
            {
              FarmaUtility.showMessage(this,"Los cupones que generó este pedido han sido usados."+ 
             " Por favor verifique si se debe anular dicho canje.",pObj);
            }
    
    if(comprobarFecha())// || comprobarDocumento())
    {
      if(!comprobarDocumentoNotaCredito())
      {
        FarmaUtility.showMessage(this, "No se puede anular un pedido de tipo Nota de Crédito. Verifique!!!",pObj);
      } else 
      {
      
        /**
        * @AUTHOR  
        * @SINCE 10.06.09
        * Se evitara anulara si es que la ip no cuenta con impresora
        * */
        if (validaImprIP()){   
        
          /**
           * Valida pedido Fidelizado
           * @author:  
           * @since: 18/12/2008
           * */
           System.out.println("VariablesCaja.vIndPedFidelizado : " +VariablesCaja.vIndPedFidelizado);
           if(VariablesCaja.vIndPedFidelizado.equalsIgnoreCase("S")){
        	   System.err.println("es un pedido de un cliente fidelizado ...");
           //if(validaExistProd()){
        	   log.debug("aqui verificara si hay linea con matriz...ELLO IMPLICA CUALQUIER CONEXION REMOTA que se tenga se perdera" +
        	   		"por lo que es PROBABLE que hayas problemas al eliminar recargas virtuales que compro un cliente fidelizado");
              /*
              if(ExistLineaMatriz()){//aqui al momento de preguntar por si hay linea con matriz
                  System.err.println("ANULA PRUEBA anularPedidofidelizadoMatriz");                  
                  if(anularPedidofidelizadoMatriz(FarmaConstants.INDICADOR_N))
                      //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
                        anular(); 
              }else{
                   System.err.println("ANULA PRUEBA anularPedidofidelizado");                  
                 if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
                  anular();
               }
              */
              //////////
              System.err.println("  - 30.11.2009 - ANULA PRUEBA anularPedidofidelizado");
              //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
              //  18.03.10 se valida pedido antes de procesarlo
               if(validaPedido(FarmaConstants.INDICADOR_S))
                 anular();
              
           //}
           }else {
             //No es fidelizado
              System.out.println("No es fidelizado");
              anular();
           }
        }
      }
    }
    else
    {
      if(comprobarProductosRestantes())
      {
          if (validaImprIP()){   //  10.07.2009.n
              FarmaUtility.showMessage(this,"Este Pedido correponde a un periodo anterior. \nNo se anulará dicho pedido, pero se generará una solicitud de Nota de Crédito.",pObj);
              DlgNotaCreditoNueva dlgNotaCreditoNueva = new DlgNotaCreditoNueva(myParentFrame,"",true);
              dlgNotaCreditoNueva.setVisible(true);
              if(FarmaVariables.vAceptar)
              {
                cerrarVentana(true);
              }
          }  //  10.07.2009.n              
      }
    }
  }
  
  private void cargarVariables()
  {
    VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
    VariablesCaja.vTipComp_Anul = "%";
    VariablesCaja.vNumComp_Anul = "%";
    VariablesCaja.vMonto_Anul = txtMonto.getText().trim(); 
    /*VariablesCaja.vTipComp = tblCabeceraPedido.getValueAt(0,7).toString();
    VariablesVentas.vTip_Ped_Vta = FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,8);
    VariablesCaja.vIndDeliveryAutomatico = FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,9);*/
    
    System.out.println("::::::::::::SE OBTIENE DATOS ESCONDIDOS::::::::::.");
    VariablesCaja.vTipComp=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,7);
      VariablesVentas.vTip_Ped_Vta=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,8);
      VariablesCaja.vIndDeliveryAutomatico=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,9);
    
    //  18.12.08
    /*
    VariablesCaja.vIndPedFidelizado= FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,10);
    VariablesCaja.vDniCli=FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,11);
    */
    
    VariablesCaja.vIndPedFidelizado= FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,10);
    VariablesCaja.vDniCli=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,11);
    //
    System.out.println("tipo de pedido : " + VariablesVentas.vTip_Ped_Vta);
    System.out.println("  - 30.11.2009:VariablesCaja.vIndPedFidelizado : " + VariablesCaja.vIndPedFidelizado);
  }

  private boolean comprobarFecha()
  {
    boolean retorno=true;
    try
    {
      String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      String fecha = tblCabeceraPedido.getValueAt(0,1).toString().substring(0,10);
      System.out.println(sysdate+"/"+fecha);
      
      Date date1 = FarmaUtility.getStringToDate(sysdate,"dd/MM/yyyy");
      GregorianCalendar calendario1 = new GregorianCalendar();
      //calendario1.setGregorianChange(date1);
      calendario1.setTime(date1);
      
      Date date2 = FarmaUtility.getStringToDate(fecha,"dd/MM/yyyy");
      GregorianCalendar calendario2 = new GregorianCalendar();
      //calendario2.setGregorianChange(date2);
      calendario2.setTime(date2);
      
      if(calendario1.get(Calendar.YEAR)==calendario2.get(Calendar.YEAR)
          && calendario1.get(Calendar.MONTH)==calendario2.get(Calendar.MONTH))
        retorno = true;
      else
        retorno = false;
      
    }catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al comprobar la fecha - \n" +e.getMessage(),pObj);
    }
    return retorno;
  }
  
  private boolean comprobarDocumento()
  {
    boolean retorno = true;
    String documento = tblCabeceraPedido.getValueAt(0,7).toString();
    if(documento.equals(ConstantsVentas.TIPO_COMP_FACTURA))
    {
      retorno = false;
    }
    return retorno;
  }
  
  private boolean comprobarDocumentoNotaCredito()
  {
    boolean retorno = true;
    String documento = FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,7);
    if(documento.equals(ConstantsVentas.TIPO_COMP_NOTA_CREDITO))
    {
      retorno = false;
    }
    return retorno;
  }
  
  
  private boolean comprobarProductosRestantes()
  {
    boolean retorno = true;
    try
    {
      int cant = DBCaja.getProductosRestantes(txtCorrelativo.getText().trim(),"%");
      if(cant == 0)
      {
        retorno = false;
        FarmaUtility.showMessage(this,"Este Pedido corresponde a un periodo anterior. \nSin embargo, no tiene productos pendientes.",pObj);
      }
    }catch(SQLException e)
    {
      retorno = false;
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al comprobar productos restantes  - \n" + e.getMessage(),pObj);
    }
    return retorno;
  }

  private boolean cargaLoginAdmLocal()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      //Se validara por el rol dependiendo del tipo de pedido
      //14.11.2007    modificacion
      if(VariablesCaja.vIndPedidoRecargaVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
         dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
         System.out.println("Pedido Recarga Virtual..Solo el operador podra anular");
      }
      else{
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        System.out.println("Pedido normal..");
      }
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrió un error al validar rol de usuariario \n : " + e.getMessage(),pObj);
    }
    return FarmaVariables.vAceptar;
  }

/**
  * Obtiene el tiempo maximo para la anulacion de un pedido recarga virtual
  * @author  
  * @since  09.11.2007
  */
  private String time_max(String pNum_ped)
  {
    String valor = "";
    String num_pedido = pNum_ped;
    try
      {
         valor = DBCaja.getTimeMaxAnulacion(num_pedido);
      
      }catch(SQLException e)
      {
        e.printStackTrace();
        FarmaUtility.showMessage(this,"Ocurrió un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" + e.getMessage(),pObj);
      }
     return valor; 
  }   
  
    /**
     * Metodo que sirve para validar que existe conexion en admCentral
     * @Author  
     * @Since 15.12.2008
     * @param pCadena
     * @param pParent
     */
    public void validarConexionADMCentral(){
        VariablesCaja.vIndLineaADMCentral = FarmaUtility.getIndLineaOnLine(
                                     FarmaConstants.CONECTION_ADMCENTRAL,
                                     FarmaConstants.INDICADOR_N);
                                        
        if(VariablesCaja.vIndLineaADMCentral.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
           //System.out.println("No existe linea se cerrara la conexion en ADMCentral ...");
           System.err.println("***cerrar toda conexion remota ... " +
           		"debido a que no encontro linea con admcentral");
           FarmaConnectionRemoto.closeConnection();
        }
    }
    
   /**
    * Metodo que sirve para validar que la recarga se realizo con exito.
    * @Author  
    * @Since 15.12.2008
    * @param pCadena
    * @param pParent
    */
    public String obtieneRespuestaRecarga(){
        String retorno = FarmaConstants.INDICADOR_N;
        try{
            retorno = DBCaja.obtCodigoRptaProdVirtual();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return retorno;
    }

    private void obtieneInfoPedidoVirtual()
    {
      try
      {
        DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual,
                                        txtCorrelativo.getText().trim());
        System.out.println("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
        //Agregado por   15.12.2008
        if(VariablesVirtual.vArrayList_InfoProdVirtual.size() > 0){
            colocaInfoPedidoVirtual();
        }
      } catch(SQLException sql)
      {
        VariablesVirtual.vArrayList_InfoProdVirtual.clear();
        sql.printStackTrace();
        FarmaUtility.showMessage(this, "Error al obtener informacion de pedido virtual - \n" + sql.getMessage(), pObj);
      }
    }
    
    private void colocaInfoPedidoVirtual()
    {
      VariablesCaja.vTipoProdVirtual = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 1);
      VariablesCaja.vPrecioProdVirtual = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 2);
      VariablesCaja.vNumeroCelular = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 3);
      VariablesCaja.vCodigoProv = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 4);
      VariablesCaja.vNumeroTraceOriginal = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 5);
      VariablesCaja.vCodAprobacionOriginal = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 6);
      /* 27.09.2007 ERIOS Datos necesarios para Bprepaid */
      VariablesCaja.vFechaTX = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 8);
      VariablesCaja.vHoraTX = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 9);
    }
    
    /**
     * Se sigue la secuencia de anulacion
     * */
    private void anular(){
        if(validarMostrarCorrelativo()){
            FarmaUtility.moveFocus(txtCorrelativo);  
        }else 
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        
        DlgDetalleAnularPedido dlgDetalleAnularPedido = new DlgDetalleAnularPedido(myParentFrame,"",true);
        dlgDetalleAnularPedido.setVisible(true);
        
        System.out.println("FarmaVariables.vAceptar="+FarmaVariables.vAceptar);
        if(FarmaVariables.vAceptar){
          tableModelCabeceraPedido.clearTable();
          tableModelDetallePedido.clearTable();
          
          tblCabeceraPedido.removeAll();
          tblDetallePedido.removeAll();
          
          txtCorrelativo.setText("");
          txtMonto.setText("");
          System.out.println("ok");
          
          //  16.06.09 imprime mensaje para recoger comprobante de anulacion
          /* if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
              if(!VariablesCaja.vDescripImpr.equalsIgnoreCase("X")||VariablesCaja.vDescripImpr.equalsIgnoreCase(""))
              lblMensaje.setText("Recoger comprobante en : "+VariablesCaja.vDescripImpr);
           }*/
          
        }         
    }

       /**
        * Valida que exista productos canje y regalos en pedido
        * @author:  
        * @since: 18.12.08
        * */
    private boolean validaExistProd(){
    
    boolean result=false;
       String ind;
       try {
          ind = DBCaja.VerificaProdFidel(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli);
          if(ind.equalsIgnoreCase("S"))
              result=true;
        } catch(SQLException ex)
        {
          ex.printStackTrace();
          FarmaUtility.showMessage(this,"Error al validar productos.\n" + ex.getMessage(), pObj);
        }
        //DBCaja.anularPedido(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vTipComp_Anul,VariablesCaja.vNumComp_Anul,VariablesCaja.vMonto_Anul,tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),2).toString(),VariablesCaja.vMotivoAnulacion);
         return result;
    }
    
    /**
    * Verifica si existe linea con PtoventaMatriz
    * @author:  
    * @since: 18.12.08
    * */
    private boolean ExistLineaMatriz(){
    
       VariablesCaja.vIndLineaPtoventaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                     FarmaConstants.INDICADOR_N);
        if(VariablesCaja.vIndLineaPtoventaMatriz.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
          System.out.println("Existe linea con PtoVenta Matriz ...");
          VariablesCaja.vIndLineaPtoventaMatriz = FarmaConstants.INDICADOR_S;
           return true;// cambiar
        }else
        return false;
    }
    
    /**
    * Verifica existe productos canje
    * @author:  
    * @since: 18.12.08
    * */
    private boolean existProdCanje(){
    int cant=0;
     boolean result=false;
       try {
          cant = DBCaja.ExistProdCanje(VariablesCaja.vNumPedVta_Anul);
          if(cant>0)  result= true;
          else result= false;
        } catch(SQLException ex)
        {
          ex.printStackTrace();
          FarmaUtility.showMessage(this,"Error al validar productos canje en pedido.\n" + ex.getMessage(), pObj);
        }
        return result;
    }
    
    /**
     * Se valida pedido antes de procesar anulacion
     * @SUTHOR  
     * @SINCE 18.03.10
     * */
    private boolean validaPedido(String IndLocal){
        
        boolean result=false;
            try {
                System.err.println(":::::::::::::::::::::::VALIDANDO PEDIDO FIDELIZADO:::::::::::::::::::::");
               DBCaja.anulaPedidoFidelizado(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli,IndLocal);
                 result=true;
             } catch(SQLException ex){ 
                 FarmaUtility.liberarTransaccion();
                if(ex.getErrorCode() == 20004){ 
                  FarmaUtility.showMessage(this,"Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",pObj);
                }else if(ex.getErrorCode() == 20006){ 
                 FarmaUtility.showMessage(this,"No se puede anular, ya que hay canjes asociados.\n",pObj);
                }else if(ex.getErrorCode() == 20007){ 
                 FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de un canje.\n",pObj);
                }else{
                    ex.printStackTrace(); 
                    FarmaUtility.showMessage(this,"Error al validar pedido fidelizado.\n" + ex.getMessage(), pObj);
                }
             }
             return result;
    }
    
    /**
     * se anula el pedido en Local
     * @author:  
     * @since: 18.12.08
     * */
    private boolean anularPedidofidelizado(String IndLocal){
    boolean result=false;
        try {
           DBCaja.anulaPedidoFidelizado(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli,IndLocal);
             result=true;
         } catch(SQLException ex){ 
             FarmaUtility.liberarTransaccion();
            if(ex.getErrorCode() == 20001){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que no hay canjes relacionados.\n",pObj);
            }else if(ex.getErrorCode() == 20004){ 
              FarmaUtility.showMessage(this,"Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",pObj);
            }else if(ex.getErrorCode() == 20005){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de su propio canje.\n",pObj);
            }else if(ex.getErrorCode() == 20006){ 
             FarmaUtility.showMessage(this,"No se puede anular, ya que hay canjes asociados.\n",pObj);
            }else if(ex.getErrorCode() == 20007){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de un canje.\n",pObj);
            }else{
            ex.printStackTrace(); 
            FarmaUtility.showMessage(this,"Error al anular pedido.\n" + ex.getMessage(), pObj);
            }
         }
         return result;
    }
    
    
    /**
     * Se anula el pedido en Matriz
     * @author:  
     * @since: 18.12.08
     * */
    private boolean anularPedidofidelizadoMatriz(String IndLocal){
    boolean result=false;
        try {
           DBCaja.anulaPedidoFidelizadoMatriz(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli,IndLocal,FarmaConstants.INDICADOR_N);
             result=true;
             VariablesCaja.vIndCommitRemotaAnul=result;
         } catch(SQLException ex){ 
            VariablesCaja.vIndCommitRemotaAnul=result;
            if(ex.getErrorCode() == 20001){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que no hay canjes relacionados.\n",pObj);
            }else if(ex.getErrorCode() == 20004){ 
              System.out.println("Error en PtoVenta Matriz ..."+VariablesCaja.vNumPedVta_Anul+" "+" "+VariablesCaja.vDniCli);
              FarmaUtility.showMessage(this,"Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",pObj);
            }else if(ex.getErrorCode() == 20005){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de su propio canje.\n",pObj);
            }else if(ex.getErrorCode() == 20006){ 
             FarmaUtility.showMessage(this,"No se puede anular, ya que hay canjes asociados.\n",pObj);
            }else if(ex.getErrorCode() == 20007){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de un canje.\n",pObj);
            }else{
            ex.printStackTrace(); 
            FarmaUtility.showMessage(this,"Error al anular pedido.\n" + ex.getMessage(), pObj);
            }
         }
         return result;
    }

/**
 * @author:  
 * @since:  29/12/2008
 */
   /* private void mostrarMensajeRecargaExito() {
        String vRetorno = "";
        validarConexionADMCentral();
        if(VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_S)){
            try{
                vRetorno = DBCaja.getIndicadorRecargaExito();
                if (vRetorno.equals("1")){
                    if(!FarmaUtility.rptaConfirmDialog(this, "La recarga se realizo con exito, desea continuar?")){
                        
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }*/
    
    private void evaluaPedidoProdVirtual(String pNumPedido)
    {
      int cantProdVirtualesPed = 0;
      cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
      if( cantProdVirtualesPed <= 0 )
      {
        VariablesVirtual.vConProductoVirtual = false;
        
      } else
      {
        VariablesVirtual.vConProductoVirtual = true;
        
      }
      System.err.println(" : VariablesVirtual.vConProductoVirtual :"+VariablesVirtual.vConProductoVirtual);
    }
    
    private int cantidadProductosVirtualesPedido(String pNumPedido)
    {
      int cant = 0;
      try
      {
        cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
      } catch(SQLException ex)
      {
        ex.printStackTrace();
        cant = 0;
        FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), pObj);
      }
      return cant;
    }

    /**
     * Valida si se muestra la nueva versión para Imprimir o 
     * no Imprimir Correlativo, así como usar pantalla para Ingresar Numero Comprobante y Monto Neto si es Negativo
     * @author  
     * @since 22.08.2011
     * @return true si imprime correlativo
     */
    private boolean validarMostrarCorrelativo(){
        boolean flag = true;
        //si la validacion siguiente es falsa no imprime y debe ingresar nro comprobante
        if(!UtilityVentas.getIndImprimeCorrelativo()){
            txtCorrelativo.enable(false);
            txtMonto.enable(false);
            flag = false;
        }
        return flag;
    }
  
    private void mostrarCorrelativoXComprobante(){
        DlgConsultaXCorrelativo dlgConsulta = new DlgConsultaXCorrelativo(myParentFrame,"",true);
        dlgConsulta.setVisible(true);
        
        if(FarmaVariables.vAceptar){
            txtCorrelativo.setText(VariablesVentas.vNumPedVta_new);
            txtMonto.setText(VariablesVentas.vMontoNeto_new);
            btnBuscar.doClick();
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        }else
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
    }
    
    //  25.08.2011 Setear el Objeto para enfocar después de los mensajes.
    private void setearObjetoFocus(){
        //  25.08.2011 verificar si se utiliza funcionalidad nueva
               if(validarMostrarCorrelativo())
                   pObj = txtCorrelativo;
               else 
                   pObj = tblCabeceraPedido;
    }
}
