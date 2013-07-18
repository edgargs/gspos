package mifarma.ptoventa.caja;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;

import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import com.gs.mifarma.componentes.JLabelFunction;

import java.text.SimpleDateFormat;

import java.util.Date;


import mifarma.ptoventa.ventas.reference.DBVentas;




public class DlgDetalleAnularPedido extends JDialog 
{
  
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgDetalleAnularPedido.class);
  
  FarmaTableModel tableModelUsuariosCaja;
  Frame myParentFrame;
  private boolean esNotaCredito = false;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JPanel jPanel1 = new JPanel();
  private JButton btnListaUsuarioCaja = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private static JTable tblUsuariosCaja = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  
  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgDetalleAnularPedido()
  {
    this(null, "", false);
  }

  public DlgDetalleAnularPedido(Frame parent, String title, boolean modal)
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

  public DlgDetalleAnularPedido(Frame parent, String title, boolean modal, boolean esNotaCredito)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    this.esNotaCredito = esNotaCredito;
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
    this.setSize(new Dimension(446, 241));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Detalle Anulacion de Pedido");
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
    jContentPane.setBackground(Color.white);
    jContentPane.setSize(new Dimension(435, 208));
    jContentPane.setLayout(null);
    jPanel1.setBounds(new Rectangle(20, 20, 390, 30));
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanel1.setBackground(new Color(255, 130, 14));
    jPanel1.setLayout(null);
    btnListaUsuarioCaja.setText("Lista de Usuarios y Cajas Disponibles");
    btnListaUsuarioCaja.setBounds(new Rectangle(10, 5, 220, 20));
    btnListaUsuarioCaja.setMnemonic('l');
    btnListaUsuarioCaja.setBackground(new Color(255, 130, 14));
    btnListaUsuarioCaja.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnListaUsuarioCaja.setBorderPainted(false);
    btnListaUsuarioCaja.setContentAreaFilled(false);
    btnListaUsuarioCaja.setDefaultCapable(false);
    btnListaUsuarioCaja.setFocusPainted(false);
    btnListaUsuarioCaja.setHorizontalAlignment(SwingConstants.LEFT);
    btnListaUsuarioCaja.setRequestFocusEnabled(false);
    btnListaUsuarioCaja.setForeground(Color.white);
    btnListaUsuarioCaja.setFont(new Font("SansSerif", 1, 11));
    btnListaUsuarioCaja.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        btnListaUsuarioCaja_keyPressed(e);
                    }
      });
    btnListaUsuarioCaja.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnListaUsuarioCaja_actionPerformed(e);
                    }
      });
    jScrollPane1.setBounds(new Rectangle(20, 50, 390, 125));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(320, 185, 85, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(210, 185, 100, 20));
    jPanel1.add(btnListaUsuarioCaja, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    jScrollPane1.getViewport();
    jScrollPane1.getViewport();
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(jPanel1, null);
    jScrollPane1.getViewport().add(tblUsuariosCaja, null);
    jContentPane.add(jScrollPane1, null);
    //this.getContentPane().add(jContentPane, null);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */
  
  private void initialize()
  {
    FarmaVariables.vAceptar=false;
    initTableUsuariosCaja();
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTableUsuariosCaja()
  {
    tableModelUsuariosCaja = new FarmaTableModel(ConstantsCaja.columnsUsuariosCaja,ConstantsCaja.defaultUsuariosCaja,0);
    FarmaUtility.initSimpleList(tblUsuariosCaja,tableModelUsuariosCaja,ConstantsCaja.columnsUsuariosCaja);
    cargaUsuariosCajaDisponibles();
      
  }
  
  private void cargaUsuariosCajaDisponibles()
  {
    try
    {
      DBCaja.getListaCajaUsuario(tableModelUsuariosCaja);
      FarmaUtility.ordenar(tblUsuariosCaja,tableModelUsuariosCaja, 0,FarmaConstants.ORDEN_ASCENDENTE);
        System.out.println("tableModelUsuariosCaja:" + tableModelUsuariosCaja);
    }catch(SQLException e)
    {
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Ocurrio un error al verificar caja disponible - \n" + e.getMessage(),tblUsuariosCaja);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnListaUsuarioCaja_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(btnListaUsuarioCaja);
  }
  
  private void btnListaUsuarioCaja_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(btnListaUsuarioCaja);
    validaCajaOpen();// 
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */


    /**
     * Valida que no halla usuarios con caja abierta
     * @author  
     * @since  19.01.2010
     */
  private void validaCajaOpen(){
      if(tableModelUsuariosCaja.getRowCount()<=0){
          System.out.println("validaCajaOpen::::::");
          FarmaUtility.showMessage(this,"No hay cajeros a quien asignar dicha anulación.",null);
          cerrarVentana(false);
      }
  }

  private void chkKeyPressed(KeyEvent e) {
      boolean vIndAnular = true; //  23122009
        FarmaGridUtils.aceptarTeclaPresionada(e, tblUsuariosCaja, null, 0);
        System.out.println("esNotaCredito:" + esNotaCredito);

        if (e.getKeyCode() == KeyEvent.VK_F11) {
            log.debug(" :EVENTO clic en tecla F11");
            //  - 01.03.2010
            /*
            cargaUsuariosCajaDisponibles(); // 
            validaCajaOpen(); // 
            if(tableModelUsuariosCaja.getRowCount()<=0) return;// 
            */
            if (esNotaCredito) {
                log.debug(" :ES nota de credito");
                VariablesCaja.vNumCaja = 
                        tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 
                                                   2).toString();
                //  10.07.2009.n
                VariablesCaja.vNumTurnoCaja=tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(), 
                                                   3).toString();
                //  10.07.2009.n
                /** Agregado por   01/12/2008 **/
                //  10.07.2009.n
                obtieneImpresTicket();
                //  10.07.2009.n
                cargaMotivo(); //cargar descripcion del motivo de la anulacion del pedido
                
                 //  13.01.2010 Se valida que la caja este abierta y se bloquea al mismo tiempo.
                 VariablesCaja.vNumCaja= tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),2).toString();
                 log.debug(":::::: : validando caja aperturada:::::");
                 if(!validaCajaAbierta())
                  return;
                  
                if (FarmaVariables.vAceptar) {
                    cerrarVentana(true);
                }
            } else {
            
            //  30.06.09
            obtieneImpresTicket();//antes de anular se sabe donde se imprimira el comprobante de anulacion ticket.
            
                log.debug(" :NO es nota de credito");
                log.debug(" : antes de cargar motivo /VariablesCaja.vSecImprLocalTicket--> "+VariablesCaja.vSecImprLocalTicket);
                cargaMotivo(); //cargar descripcion del motivo de la anulacion del pedido            
                
                 //  13.01.2010 Se valida que la caja este abierta y se bloquea al mismo tiempo.
                 VariablesCaja.vNumCaja= tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),2).toString();
                 log.debug(":::::: : validando caja aperturada:::::");
                 if(!validaCajaAbierta())
                  return;
                
                if (FarmaVariables.vAceptar) {
                    //verifica si es un pedido virtual
                    log.debug("  VariablesCaja.vIndPedidoConProdVirtual :" + 
                              VariablesCaja.vIndPedidoConProdVirtual);
                    evaluaPedidoProdVirtual(VariablesCaja.vNumPedVta_Anul); //obtiene VariablesCaja.vIndPedidoConProdVirtual
                    log.debug("  VariablesCaja.vIndPedidoConProdVirtual :" + 
                              VariablesCaja.vIndPedidoConProdVirtual);

                    String vFlagValidarMinMatriz = 
                        FarmaConstants.INDICADOR_S; //  flag que indicara si se validara o no los minutos transcurridos
                    //no quitar esta variable
                    System.err.println("  VariablesCaja.vIndPedidoConProdVirtual :" + 
                                       VariablesCaja.vIndPedidoConProdVirtual);
                    System.err.println("  VariablesCaja.vIndAnulacionConReclamoNavsat :" + 
                                       VariablesCaja.vIndAnulacionConReclamoNavsat);

                    
                        /**************INICIO RECARGA VIRTUALES*****************/
                        if (VariablesCaja.vIndPedidoConProdVirtual && 
                            !VariablesCaja.vIndAnulacionConReclamoNavsat) {
                            
                            
                                //  23122009 inicio   
                                log.info("VariablesCaja.vIndLineaADMCentral antes: "+VariablesCaja.vIndLineaADMCentral);
                                VariablesCaja.vIndLineaADMCentral=FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_ADMCENTRAL,FarmaConstants.INDICADOR_S);
                                
                                log.info("VariablesCaja.vIndLineaADMCentral despues: "+VariablesCaja.vIndLineaADMCentral);
                            if (VariablesCaja.vIndLineaADMCentral.equalsIgnoreCase("N")){
                                vIndAnular=false; 
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_ADMCENTRAL,
                                                                      FarmaConstants.INDICADOR_N);
                                FarmaConnectionRemoto.closeConnection();
                                FarmaUtility.showMessage(this, "No se puede anular el pedido, no hay conexion con Matriz", tblUsuariosCaja);
                                cerrarVentana(false);
                                System.out.println("Despues de cerrar en el virtual"); 
                            }
                            else if (VariablesCaja.vIndLineaADMCentral.equalsIgnoreCase("S"))
                            {
                                obtieneInfoPedidoVirtual(); //obtiene  VariablesVirtual.vArrayList_InfoProdVirtual
                                log.info("  VariablesVirtual.vArrayList_InfoProdVirtual :" + 
                                          VariablesVirtual.vArrayList_InfoProdVirtual);
                                if (VariablesVirtual.vArrayList_InfoProdVirtual.size() > 
                                    0) {
                                    log.info("  seteando variables ...");
                                    colocaInfoPedidoVirtual(); //setea las variables
                                } else {
                                    log.info(" : no encontro informacion del pedido !");
                                    return; //sale del metodo
                                }
                                log.info(" : CODIGO DE EXITO RECARGA :**" + 
                                          VariablesCaja.vRespuestaExito + "**");
                                VariablesCaja.vRespuestaExito = 
                                        obtieneRespuestaRecarga().trim();

                                log.info(" : CODIGO DE EXITO RECARGA :**" + 
                                          VariablesCaja.vRespuestaExito + "**");

                                if (VariablesCaja.vRespuestaExito.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                                    FarmaUtility.showMessage(this, 
                                                             "No es posible realizar la operación.\n" +
                                            "No se pudo obtener la respuesta de la recarga.", 
                                            tblUsuariosCaja);
                                    VariablesCaja.vMotivoAnulacion = "";
                                    cerrarVentana(false);
                                    return;
                                } else {
                                    if (VariablesCaja.vRespuestaExito.trim().equals("00") || 
                                        VariablesCaja.vRespuestaExito.trim().equals("-2")) { //00:exito , -2:no hay Respuesta

                                        System.err.println("VariablesCaja.vRespuestaExito :" + VariablesCaja.vRespuestaExito.trim());

                                        if (!validaTiempoAnulacionRecarga(VariablesCaja.vNumPedVta_Anul)) {
                                            String tiempo_maximo = 
                                                time_max(VariablesCaja.vNumPedVta_Anul.trim()).trim();
                                            FarmaUtility.showMessage(this, 
                                                                     "No se puede anular este pedido el tiempo es mayor a " + 
                                                                     tiempo_maximo + 
                                                                     " minutos.", 
                                                                     tblUsuariosCaja);
                                            VariablesCaja.vMotivoAnulacion = "";
                                            System.out.println("Escape :" + 
                                                               "Ventana DlgDetalleAnularPedido");
                                            cerrarVentana(false);
                                            return;
                                        }

                                      ///  Busca Recarga antes de anularlo                         
                                       if (BuscarRecargaVirtual()) {
                                        
                                        try {
                                            log.info(" :Inicio de anulacion de recarga");
                                            UtilityCaja.procesaAnulacionVentaProductoVirtual(this, 
                                                                                             tblUsuariosCaja);
                                            log.info(" :finalizacion de anulacion de recarga");
                                        } catch (Exception ex) {
                                            FarmaUtility.showMessage(this, 
                                                                     "Error en la aplicación al anular el pedido virtual.\n" +
                                                    ex.getMessage(), tblUsuariosCaja);
                                            return;
                                        }
                                        log.info("lero lero despues del proceso procesaAnulacionVentaProductoVirtual");
                                        
                                    
                                        
                                        if (!validaCodigoRespuestaTransaccion()) { 
                                            
                                            System.err.println("VariablesVirtual.vCodigoRespuesta :" + VariablesVirtual.vCodigoRespuesta);

                                            
                                            //Obtener la descripción del Error 
                                              try {
                                                  
                                                  //validar primero que el mensaje de VariablesVirtual.vCodigoRespuesta != null  
                                                  //Respuesta Codigo de Error
                                                  if (VariablesVirtual.vCodigoRespuesta != null){
                                                      //Respuesta Mensaje de Error
                                                        VariablesVirtual.vDescripcionRespuesta =  DBVentas.mostrarMensajeError(VariablesVirtual.vCodigoRespuesta);
                                                      
                                                    if (VariablesVirtual.vCodigoRespuesta != null &&  VariablesVirtual.vDescripcionRespuesta != null) {
                                                        FarmaUtility.showMessage(this,"Error al realizar la transacción con el proveedor.\n" + VariablesVirtual.vCodigoRespuesta +  " - " + VariablesVirtual.vDescripcionRespuesta, tblUsuariosCaja);
                                                      
                                                    } 
                                                      
                                                    else {
                                                               log.debug("VariablesVirtual.vCodigoRespuesta :" + VariablesVirtual.vCodigoRespuesta);
                                                               log.debug("VariablesVirtual.vDescripcionRespuesta :" + VariablesVirtual.vDescripcionRespuesta);
                                                        
                                                              FarmaUtility.showMessage(this, "Error al realizar la transacción con el proveedor.\n", tblUsuariosCaja);
                                                          
                                                           }
                                                  
                                                  }
                                                  else {
                                                      
                                                          log.debug("VariablesVirtual.vDescripcionRespuesta :" + VariablesVirtual.vDescripcionRespuesta);
                                                          FarmaUtility.showMessage(this, "Error al realizar la transacción con el proveedor.\n", tblUsuariosCaja);
                                                         
                                                      
                                                      }
                                                      
                                                   
                                                  
                                                } catch (SQLException sql) {

                                                    FarmaUtility.showMessage( this, 
                                                                             "Error al obtener Mensaje de Error.\n" +
                                                                              sql.getMessage(), tblUsuariosCaja);
                                                    
                                                   
                                                }
                                     
                                
                                            return;
                                              
                                              
                                        }
                                
                                        ///
                                    }
                                       //no existe la recarga
                                       else
                                        FarmaUtility.showMessage(this, "No existe la Recarga Virtual. \n", tblUsuariosCaja);
                                        
                                } 
                                    
                                    else {
                                        vFlagValidarMinMatriz = 
                                                FarmaConstants.INDICADOR_N; //no validar los minutos transcurridos en matriz 
                                                
                                    }
                                }
                            }
                        
                            
                        }
                  
                     
                    
                    if (vIndAnular){
                        /***************F I N*******************/
                        if (anular(vFlagValidarMinMatriz)) { //anulacion de todo pedido(cebecera y detalle)
                            /**
                               * Nueva funcionalidad para pedido con producto virtual
                               * agregado x LMesia 22/01/2007
                               */

                            if (VariablesCaja.vIndPedidoConProdVirtual && 
                                !VariablesCaja.vIndAnulacionConReclamoNavsat) {
                                try {
                                    UtilityCaja.actualizaInfoPedidoVirtualAnulado(VariablesCaja.vNumPedVta_Anul);
                                    FarmaUtility.aceptarTransaccion();
                                } catch (SQLException sql) {
                                    FarmaUtility.liberarTransaccion();
                                    FarmaUtility.showMessage(this, 
                                                             "Error al actualizar informacion del pedido virtual anulado.\n" +
                                            sql.getMessage(), tblUsuariosCaja);
                                }
                            }

                            cerrarVentana(true);
                        }else
                        {  
                           cerrarVentana(false);//  2312009
                           System.out.println("Despues de cerrar en el convenio"); 
                        }
                    }
                    //  23122009 fin 

                        
                }

            }
            log.debug("*** FI evento clic en tecla F11");
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                VariablesCaja.vMotivoAnulacion = "";
                System.out.println("Escape :" + 
                                   "Ventana DlgDetalleAnularPedido");
                cerrarVentana(false);
            }            
        }
    }

    private void cerrarVentana(boolean pAceptar){
        
          //  30.12.2008
          //ROLLBACK remotamente si es que no se anulo)
          if(VariablesCaja.vIndCommitRemotaAnul&&VariablesCaja.vIndLineaPtoventaMatriz.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
              FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
              System.out.println("ROLLBACK REMOTO EN ESC.......................");                                          
          }      
            FarmaVariables.vAceptar = pAceptar;
            this.setVisible(false);
            this.dispose();

    }
    
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */
  
    /**
    * metodo encargado de anular los pedidos
    * @param vFlagValidarMinMatriz
    * @return
    */
    private boolean anular(String vFlagValidarMinMatriz){
      boolean retorno=false,vResultado=false;
      String pDatosDel = FarmaConstants.INDICADOR_N;
      String ruta="";
      
      try
      {
    	  //  19.03.10 Se anula datos de campaña acumulada
           if(anularPedidofidelizado(FarmaConstants.INDICADOR_S)) {
               System.out.println("::::::::::::::::::::::::.ANULANDO REGISTROS DE CAMPAÑA ACUMULADA::::::::::::::::::::::::::::::");
		   
			  //obtiene datos de pedido delivery si es que lo fuera    	  
			  pDatosDel = DBCaja.getDatosPedDelivery(VariablesCaja.vNumPedVta_Anul);
			  System.out.println("Datos delivery-->"+pDatosDel);
			
			  //Activa los cupones usados esta decision fue tomada por gerencia   02.12.2008
			  DBCaja.activarCuponesUsados(VariablesCaja.vNumPedVta_Anul);
			  if(log.isDebugEnabled()){//ver si debug esta activo
				  VariablesCaja.mostrarValoresVariables();//imprimiendo todos los valores de la clase VariablesCaja
			  }
			  log.debug("anulando pedido ... 0% ");
			              log.debug("VariablesCaja.vNumPedVta_Anul:"+VariablesCaja.vNumPedVta_Anul);
			              log.debug("VariablesCaja.vNumComp_Anul:"+VariablesCaja.vNumComp_Anul);
			              log.debug("Usuario:"+tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),2).toString());


                          

			  DBCaja.anularPedido( VariablesCaja.vNumPedVta_Anul, VariablesCaja.vTipComp_Anul,
								   VariablesCaja.vNumComp_Anul, VariablesCaja.vMonto_Anul,
								   tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),2).toString(),
								   VariablesCaja.vMotivoAnulacion,vFlagValidarMinMatriz);
                          
                         //fin

			  log.debug("anulando pedido ... 100% ...haciendo commit");
			
			  //  08/09/2009 
			  //valida si el producto anulado es por convenio y actualiza consumo cliente.
			  //Metodo que obtiene todos los datos del Convenio (REMOTO)
			  String vConvenioCliAnula =  "N";

                          
			  log.debug("vConvenioCliAnula: "+ vConvenioCliAnula);
			  boolean vindCommitCredito = false;
		   
			  if (!vConvenioCliAnula.equalsIgnoreCase("N")) 
			  { 

			   // abrir una conexion remota 
			   String[] val;
			   val = vConvenioCliAnula.split("Ã");
			   FarmaConnectionRemoto.closeConnection();
			   
			   //  231220009 inicio
			   log.debug("VariablesCaja.vIndLineaPtoventaMatriz antes de conestar: " +VariablesCaja.vIndLineaPtoventaMatriz);
			   VariablesCaja.vIndLineaPtoventaMatriz =FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);   
				  log.debug("VariablesCaja.vIndLineaPtoventaMatriz despues de conestar: " +VariablesCaja.vIndLineaPtoventaMatriz);
			
			   
			   
			  }
			
				  FarmaUtility.aceptarTransaccion();
				 
				  if (vindCommitCredito){
					  FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
														  FarmaConstants.INDICADOR_S);
					  FarmaConnectionRemoto.closeConnection();
					  log.error("Anula PEDIDO convenio REMOTO");
				  }

				  String mensaje = ( !VariablesCaja.vTipComp_Anul.equals("%") ) ? "Comprobante Anulado correctamente.":"Pedido Anulado correctamente";
				  
				  FarmaUtility.showMessage(this,mensaje,btnListaUsuarioCaja);
				  retorno = true;
				  
				  //System.out.println("ss"+VariablesVentas.vTip_Comp_Ped);
				  
				  //  24/04/09 metodo imprimir ticket de anulacion            
				   if (!esNotaCredito) {
					   System.err.println("1-vResultado:"+vResultado);
					   
					   getImpresionTicketAnulado();
					   
					   FarmaUtility.aceptarTransaccion();
					   vResultado = false;
					   System.err.println("2-vResultado:"+vResultado);
				   }
				   
				   //}
				   
					//Se vuelve a obtener datos delivery
					 String[] pDatosDely = pDatosDel.trim().split("%");
					 String indAutoma="",tipVta="";
					 if(pDatosDely.length==5){
						 indAutoma = pDatosDely[3].trim();
						 tipVta = pDatosDely[4].trim();
						 VariablesCaja.vIndDeliveryAutomatico=indAutoma.trim();
						 VariablesVentas.vTip_Ped_Vta=tipVta.trim();
					 }
					 
					System.err.println("**************** ******************************");
					System.err.println("VariablesCaja.vTipComp_Anul-->"+VariablesCaja.vTipComp_Anul);
					System.err.println("2-VariablesVentas.vTip_Ped_Vta-->"+VariablesVentas.vTip_Ped_Vta);
					System.err.println("VariablesCaja.vIndDeliveryAutomatico-->"+VariablesCaja.vIndDeliveryAutomatico);
					
					
				  if(VariablesCaja.vTipComp_Anul.equals("%")) {
						  if( !VariablesVentas.vTip_Ped_Vta.equals(ConstantsVentas.TIPO_PEDIDO_MESON) && 
										  VariablesCaja.vIndDeliveryAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){
								  UtilityCaja.alertaPedidoDelivery(pDatosDel.trim());
								  reinicializaPedidoAutomatico(FarmaVariables.vCodLocal, VariablesCaja.vNumPedVta_Anul);
						  }
				  }
				  //21.08.2008   Se modifico el procedimiento
				  UtilityCaja.anulaCuponesPedido(VariablesCaja.vNumPedVta_Anul,this,btnListaUsuarioCaja);
				  //Activa los cupones en matriz 03.12.2008  
				  UtilityCaja.activaCuponesMatriz(VariablesCaja.vNumPedVta_Anul,this,btnListaUsuarioCaja);
				  
				  //  22.12.2008
				  //commit remotamente si es que es Campaña acumulada)
				  if( VariablesCaja.vIndCommitRemotaAnul && 
								  VariablesCaja.vIndLineaPtoventaMatriz.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){
						  FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
																FarmaConstants.INDICADOR_N);
						  log.debug("haciendo commit remoto a matriz"); 
				  }
			  
			  
			}
	}catch(Exception e)
    {

	  e.printStackTrace();

      FarmaUtility.liberarTransaccion();

            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                          FarmaVariables.vCodLocal,
                                          //" ",
                                          VariablesPtoVenta.vDestEmailErrorAnulacion,
                                          "Error al Anular Pedido Completo",
                                          "Error de Anulación",
                                          "Se produjo un error al anular un pedido :<br>"+
                                          "IP PC: " + FarmaVariables.vIpPc + "<br>"+ //  30/07/09
                                          "Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                          "Error: " + e,
                                          //"daubilluz@gmail.com");
                                          "");
            log.info("Error anular pedido : "+e);
       
      //  22.12.2008
      //commit remotamente si es que es Campaña acumulada)
      
      
      if( !VariablesCaja.vIndCommitRemotaAnul &&
    		  VariablesCaja.vIndLineaPtoventaMatriz.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
    	  FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                FarmaConstants.INDICADOR_S);
          log.debug("haciendo rollback en matriz");                                          
      }
      System.err.println("vResultado:"+vResultado);
      if(!vResultado)
      {
         retorno = true;
         FarmaUtility.showMessage(
                                  this, 
                                  "No se imprimió el ticket anulado.\nIntente por Reimpresión de Ticket Anulado.\nGracias", 
                                  btnListaUsuarioCaja
                                 );
      }
      else
      {
          if(((SQLException)e).getErrorCode() == 20002) {
            FarmaUtility.showMessage(this,"La Fracción Actual no permite anular esta Venta.\n"+e,btnListaUsuarioCaja);
          } else if(((SQLException)e).getErrorCode() == 20016) {
            FarmaUtility.showMessage(this,"No puede anular este pedido porque el cupon ya fue utilizado.\n"+e.getMessage(),btnListaUsuarioCaja);
          } else if(((SQLException)e).getErrorCode() == 20028){//agregado para validar la correcta anulacion de pedido      
            FarmaUtility.showMessage(this,"No se logró anular el pedido\nFecha actual no pertenece al turno asignado.\n",btnListaUsuarioCaja);
          } else if(((SQLException)e).getErrorCode() == 20005){//para mostrar un mensaje mas personalizado      
              FarmaUtility.showMessage(this,"No se logró anular el pedido\nEl pedido fue cobrado hace mas de 5 minutos.",btnListaUsuarioCaja);
          } else {
              e.printStackTrace(); 
              FarmaUtility.showMessage(this,"Ocurrio un error al anular - \n" + e.getMessage(),btnListaUsuarioCaja);
          }
          retorno = false;
      }
    } finally {
        //  22.12.2008
    	log.debug("cierra conexion remota matriz, delivery, adm, etc");
        FarmaConnectionRemoto.closeConnection();
    }
    
    return retorno;
  }
  
  private void reinicializaPedidoAutomatico(String pCod_Local,
                                            String pNumPedVta)
  {
    try
    {
      DBCaja.reinicializaPedidoAutomatico(pCod_Local,pNumPedVta);
      FarmaUtility.aceptarTransaccion();
      /**
       * MOdificado 
       * @author :  
       * @since  : 24.08.2007
       */
        FarmaUtility.showMessage(this,
               "EL Pedido Anulado se Encuentra Pendiente , para su posterior generación y cobro.",null);
      //FarmaUtility.showMessage(this,"El pedido se reinicializó correctamente. Ingrese a la opción\nDelivery Automático del módulo de Ventas para generar el pedido.",null);
    } catch(SQLException e)
    {
      FarmaUtility.liberarTransaccion();
      e.printStackTrace();
      FarmaUtility.showMessage(this,"Error al reinicializar el pedido automatico - \n" + e.getMessage(),null);
    } 
  }

  private void obtieneInfoPedidoVirtual()
  {
    try
    {
      DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual,
                                      VariablesCaja.vNumPedVta_Anul);
      System.out.println("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
    } catch(SQLException sql)
    {
      VariablesVirtual.vArrayList_InfoProdVirtual.clear();
      sql.printStackTrace();
      FarmaUtility.showMessage(this, "Error al obtener informacion de pedido virtual - \n" + sql.getMessage(), tblUsuariosCaja);
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
  
  private void evaluaPedidoProdVirtual(String pNumPedido)
  {
    int cantProdVirtualesPed = 0;
    cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
    if( cantProdVirtualesPed <= 0 )
    {
      VariablesCaja.vIndPedidoConProdVirtual = false;
    } else
    {
      VariablesCaja.vIndPedidoConProdVirtual = true;
    }
    System.out.println("VariablesCaja.vIndPedidoConProdVirtual : " + VariablesCaja.vIndPedidoConProdVirtual);
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
      FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), tblUsuariosCaja);
    }
    return cant;
  }
  
  private boolean validaCodigoRespuestaTransaccion() {
    boolean result = false;
    if(VariablesVirtual.vCodigoRespuesta==null)
        result = false;
    else
        if(VariablesVirtual.vCodigoRespuesta.equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL))
          result = true;
    return result;
	}
  
    private void cargaMotivo()
    {
        
      System.out.println("DlgMotivoAnuladoPEDIDO");
      
      DlgMotivoAnularPedido dlgMotivoAnularPedido = new DlgMotivoAnularPedido(myParentFrame,"",true);
      dlgMotivoAnularPedido.setVisible(true);
  }
    
  /**
   * Metodo encargado de obtener el codigo de respuesta de la recarga
   * @Author  
   * @Since  06.01.2009
   */
  private String obtieneRespuestaRecarga(){
	  String retorno = FarmaConstants.INDICADOR_N;
	  try{
		retorno = DBCaja.obtCodigoRptaProdVirtual();
	  }catch(SQLException e){
		e.printStackTrace();
	  }
	  return retorno;
  }
  //validaTiempoAnulacion
  private boolean validaTiempoAnulacionRecarga(String pNumPedido){
          String retorno = "";
          try{
                retorno = DBCaja.validaTiempoAnulacion(pNumPedido);
          }catch(SQLException e){
                e.printStackTrace();
          }
          System.out.println("RESPUESTA:"+retorno);
          if(retorno.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
              return false;
          
          return true;
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
            FarmaUtility.showMessage(this,"Ocurrio un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" + e.getMessage(),null);
          }
         return valor; 
      }
    
    /**@author   
     * @fecha  05-02-09
     * Busca recarga virtual
     * @throws SQLException
     **/
    private  boolean BuscarRecargaVirtual() {
        
      boolean pResultado = false;
      int cantidad = 0;

      try {
             cantidad  = DBCaja.ExisteRecargaVirtual();
              
              if (cantidad > 0)
              pResultado = true;
              
              else
              pResultado = false;
              
          } 
          catch (SQLException e) {
              log.error(null,e);
              FarmaUtility.showMessage(this,"Error al obtener existencia de Producto Recarga Virtual.\n" + e.getMessage(),null);
              pResultado = false;
          }
      
      
       return  pResultado;
    }
    
    /**
     * Se obtiene impresora disponible 
     * @AUTHOR  
     * @SINCE 30.06.09
     * */
    private void obtieneImpresTicket(){
        
        log.debug(" : =VariablesCaja.vTipComp--> "+VariablesCaja.vTipComp);
        //  09.06.09  Se valida relacion maquina - impresora
          if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
          try{
                String DescImpr="";
             String result= DBCaja.getObtieneSecImpPorIP( FarmaVariables.vIpPc);
                log.debug("a");
                String result2= DBCaja.getObtieneTipoCompPorIP( FarmaVariables.vIpPc,VariablesCaja.vTipComp);
                log.debug("b");
                //verifica que el secuencial exista, caso contrario, se validara la asignada y se mostrara 
                //que no tiene niguna asignacion si es el caso. 16.06.09
             String exist="";
                exist=DBCaja.getExistSecImp(VariablesCaja.vSecImprLocalTicket,FarmaVariables.vIpPc);
            
                log.debug("c");
                log.debug(" : Secuencia Impr--> "+result);
                //if(exist.equalsIgnoreCase("S")){    --existe en local para usar.
                
                if(exist.trim().equalsIgnoreCase("2")){   
                    log.debug(" :SecImp por IP--> "+result); 
                    /*if(VariablesCaja.vTipComp.trim().equalsIgnoreCase(result2.trim()))
                     VariablesCaja.vSecImprLocalTicket=result;
                    else{
                     FarmaUtility.showMessage(this,"El tipo de comprobante es distinto al de la impresora asignada. Verifique!!!",btnListaUsuarioCaja);
                     return;
                    }*/
                     VariablesCaja.vSecImprLocalTicket=result;
                 }else if (exist.equalsIgnoreCase("X")){
                     FarmaUtility.showMessage(this,"No existen impresoras disponibles. No se imprimira comprobante.!!!",tblUsuariosCaja);
                     VariablesCaja.vSecImprLocalTicket=exist.trim();
                     //return;
                 }else if(exist.equalsIgnoreCase("1")){
                     log.debug(" : Se encontro impresora origen--> "+exist); 
                 }else {
                     log.debug(" : Sec disponible--> "+exist); 
                     VariablesCaja.vSecImprLocalTicket=exist.trim();
                 }
                 
               
                
                if(!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")){
                 DescImpr=DBCaja.getNombreImpresora(VariablesCaja.vSecImprLocalTicket);
                 VariablesCaja.vDescripImpr=DescImpr;
                }else{
                    VariablesCaja.vDescripImpr="X";
                }
                
                 
             
            }catch(Exception e){
                //sql.printStackTrace();
                             FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                                           FarmaVariables.vCodLocal,
                                                           //" ",
                                                           VariablesPtoVenta.vDestEmailErrorAnulacion,
                                                           "Error Anular Get Ruta Ip",
                                                           "Error Anular Pedido",
                                                           "Se obtuvo un errror al obtener la ruta de Ticketera Metodo obtieneImpresTicket() :<br>"+
                                                           "IP : " +FarmaVariables.vIpPc+"<br>"+
                                                           "Error: " + e,
                                                           //"joliva;operador;daubilluz@gmail.com"
                                                           "");
                            log.info("Error al obtener la ruta Ticketera para IP : "+e);
                    //              
              
            }
             
           }
    
    }
    
 /*   private void obtieneImpresTicket(){        
        log.debug(" : =VariablesCaja.vTipComp--> "+VariablesCaja.vTipComp);
        if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
          try{
             String DescImpr="";
             String result= DBCaja.getObtieneSecImpPorIP( FarmaVariables.vIpPc);
             log.debug("a");
             String result2= DBCaja.getObtieneTipoCompPorIP( FarmaVariables.vIpPc,VariablesCaja.vTipComp);
             log.debug("b");
             //verifica que el secuencial exista, caso contrario, se validara la asignada y se mostrara 
             //que no tiene niguna asignacion si es el caso. 16.06.09
             String exist="";
             exist=DBCaja.getExistSecImp(VariablesCaja.vSecImprLocalTicket,FarmaVariables.vIpPc);
             log.debug("c");
             log.debug(" : Secuencia Impr--> "+result);
             if(exist.trim().equalsIgnoreCase("2")){   
                log.debug(" :SecImp por IP--> "+result); 
                VariablesCaja.vSecImprLocalTicket=result;
             }
             else if (exist.equalsIgnoreCase("X")){
                VariablesCaja.vSecImprLocalTicket=exist.trim();                
             }else if(exist.equalsIgnoreCase("1")){
                log.debug(" : Se encontro impresora origen--> "+exist); 
             }else {
                log.debug(" : Sec disponible--> "+exist); 
                VariablesCaja.vSecImprLocalTicket=exist.trim();
             }
             if(!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")){
                DescImpr=DBCaja.getNombreImpresora(VariablesCaja.vSecImprLocalTicket);
                VariablesCaja.vDescripImpr=DescImpr;
             }else{
                VariablesCaja.vDescripImpr="X";
             }  
            }catch(SQLException sql){
                sql.printStackTrace();
            }
             
        }  
    }
  */ 
 


 public static void getImpresionTicketAnulado()throws Exception  
 {
       boolean vResultado = false,bRes1=false,bRes2=false;
        //para agregar Fecha Creacion
        Date vFecImpr = new Date();
        String fechaImpresion;
        String ruta="";      
        String DATE_FORMAT = "yyyyMMdd";
           SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            // System.out.println("Today is " + sdf.format(vFecImpr));
           fechaImpresion =  sdf.format(vFecImpr);                
                    
        //----
        
        ruta=DBCaja.ObtieneDirectorio();
        //ruta=ruta+"T_"+VariablesCaja.vNumPedVta_Anul+"_Anul";
        
        //  08/07/09
        ruta=ruta+fechaImpresion+"_"+"T_"+VariablesCaja.vNumPedVta_Anul+"_Anul";
        
           VariablesCaja.vSecuenciaUsoUsuario=tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),0).toString();
           //Solo se emitira comprobante de anulacion si existe secuencial.
           if(!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")){
               
               
               //para montos afectos
               bRes1= UtilityCaja.imprimeMensajeTicketAnulacion(tblUsuariosCaja.getValueAt(0,
                                                                              2).toString(), 
                                                   tblUsuariosCaja.getValueAt(0, 
                                                                              3).toString(), 
                                                   VariablesCaja.vNumPedVta_Anul, 
                                                   "00", ruta + "_1.TXT", 
                                                   false, "N");
               //para montos inafectos
               bRes2= UtilityCaja.imprimeMensajeTicketAnulacion(tblUsuariosCaja.getValueAt(0,
                                                                              2).toString(), 
                                                   tblUsuariosCaja.getValueAt(0, 
                                                                              3).toString(), 
                                                   VariablesCaja.vNumPedVta_Anul, 
                                                   "01", ruta + "_2.TXT", 
                                                   false, "N");
               
         //  06.07.09 Se genera archivo de anulacion
         /*
          * Este proceso no es lo correcto.
          * No se pidio que se haga de esta forma
          * SIno que en paralelo linea x linea se imprima y se guarde en el archivo.
          *   14.07.2009
         UtilityCaja.imprimeMensajeTicketAnulacion(tblUsuariosCaja.getValueAt(0, 
                                                                              2).toString(), 
                                                   tblUsuariosCaja.getValueAt(0, 
                                                                              3).toString(), 
                                                   VariablesCaja.vNumPedVta_Anul, 
                                                   "00", 
                                                   ruta + "_1.TXT", 
                                                   true, "N");
         UtilityCaja.imprimeMensajeTicketAnulacion(tblUsuariosCaja.getValueAt(0, 
                                                                              2).toString(), 
                                                   tblUsuariosCaja.getValueAt(0, 
                                                                              3).toString(), 
                                                   VariablesCaja.vNumPedVta_Anul, 
                                                   "01", 
                                                   ruta + "_2.TXT", 
                                                   true, "N");
           */
           }    
        
        if(bRes1||bRes2)
            vResultado = true;
        else
            vResultado = false;
        //return vResultado;
    }
    
    
     /**
      * @AUTHOR:  
      * @SINCE: 13.01.09
      * */
     private boolean validaCajaAbierta(){

     boolean result=true;
     String Indicador="";
         try {
                  log.debug("VariablesCaja.vNumCaja ===> "+VariablesCaja.vNumCaja);
                 Indicador = DBCaja.obtieneEstadoCaja();
                 if (Indicador.trim().equalsIgnoreCase("N")){
                    FarmaUtility.showMessage(this, "La caja no se encuentra aperturada. Verifique!!!", null);
                    result=false;
                 }
             FarmaUtility.liberarTransaccion();
            log.debug("Se valida apertura de caja para la anulacion ===> "+Indicador);
         } catch (Exception e) {
             FarmaUtility.liberarTransaccion();
                 log.debug("error al obtener indicador de caja abierta : "+e.getMessage());                 
             result=false;
         }
         
         //bloque de caja
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
            if(ex.getErrorCode() == 20001){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que no hay canjes relacionados.\n",tblUsuariosCaja);
            }else if(ex.getErrorCode() == 20004){ 
              FarmaUtility.showMessage(this,"Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",tblUsuariosCaja);
            }else if(ex.getErrorCode() == 20005){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de su propio canje.\n",tblUsuariosCaja);
            }else if(ex.getErrorCode() == 20006){ 
             FarmaUtility.showMessage(this,"No se puede anular, ya que hay canjes asociados.\n",tblUsuariosCaja);
            }else if(ex.getErrorCode() == 20007){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de un canje.\n",tblUsuariosCaja);
            }else{
            ex.printStackTrace(); 
            FarmaUtility.showMessage(this,"Error al anular pedido.\n" + ex.getMessage(), tblUsuariosCaja);
            }
         }
         return result;
    }
    
}