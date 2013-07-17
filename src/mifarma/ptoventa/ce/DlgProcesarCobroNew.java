package mifarma.ptoventa.ce;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gs.mifarma.componentes.JPanelWhite;

import mifarma.ptoventa.caja.reference.BeanDetaPago;
import mifarma.ptoventa.caja.reference.VariablesNewCobro;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.DlgNumeroTarjetaGenerado;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.reference.VariablesPtoVenta;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProcesarCobroNew.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      25.02.2010   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgProcesarCobroNew extends JDialog {
	
  private static final Log log = LogFactory.getLog(DlgProcesarCobroNew.class);

  private Frame myParentFrame;
  //private boolean vValorProceso;
  private JTable tblFormasPago;
  private JLabel lblVuelto;
  private JTable tblDetallePago;
  private JTextField txtNroPedido;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private String indCommitBefore = "";
  
  private ArrayList detaPago=new ArrayList(); //ASOSA 19.02.2010
  
  public DlgProcesarCobroNew()
  {
    this(null, "", false);
  }

  /**
   * Constructor con parametros.
   * @param parent
   * @param title
   * @param modal
   */
  public DlgProcesarCobroNew(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public DlgProcesarCobroNew(Frame parent, String title, boolean modal, 
                          JTable pTableModel, JLabel pVuelto, 
                          JTable pDetallePago, JTextField pNroPedido, ArrayList detaPago) {
	super(parent, title, modal);
	myParentFrame = parent;
	tblFormasPago = pTableModel;
	lblVuelto = pVuelto;
	tblDetallePago = pDetallePago;
	txtNroPedido = pNroPedido;
        this.detaPago=detaPago;//ASOSA 19.02.2010
	
	try
	{
	  jbInit();
	}
	catch (Exception e)
	{
	  e.printStackTrace();
	}
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(238, 104));
    this.getContentPane().setLayout(null);
    this.setTitle("Procesando Información . . .");
    this.getContentPane().setLayout(borderLayout1);
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new WindowAdapter()
        {
          public void windowOpened(WindowEvent e)
          {
            this_windowOpened(e);
          }
        });
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  void this_windowOpened(WindowEvent e)
  { 
    FarmaUtility.centrarVentana(this);
    //muestraprincipio();
      if(!procesar())
       {   FarmaUtility.showMessage(this,"Error al procesar el cambio de Forma de Pago",null);                            
       }else
       {   VariablesCajaElectronica.indExitoCambioFP=true;
       }
      cerrarVentana(false);
         
    //System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  /**
   * Validaciones de Cobro de Pedido
   * @author Dubilluz
   * @since  05.03.2009
   */
  private boolean validacionesCobroPedido(){
      //valida que haya sido seleccionado un pedido.
       if (VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                 return false;

       //validando que se haya cubierta el total del monto del pedido
       if (!VariablesCaja.vIndTotalPedidoCubierto){
               FarmaUtility.showMessage(this, "El Pedido tiene saldo pendiente de cobro.Verifique!!!", tblFormasPago);
               return false;
       }

       //validando que el pedido este en esta PENDIENTE DE COBRO
       if (!UtilityCaja.verificaEstadoPedido(this, VariablesCaja.vNumPedVta,ConstantsCaja.ESTADO_PENDIENTE, null)){
           return false;
       } 

       //Inicio Adicion Paulo. Validacion para cajeros
       if (!UtilityCaja.existeCajaUsuarioImpresora(this, null)){
               cerrarVentana(false);
           return false;
       }
       //Fin Adicion Paulo.
       
       /*
        * Validacion de Fecha actual del sistema contra
        * la fecha del cajero que cobrara
        * */
       if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
               FarmaUtility.liberarTransaccion();
           return false;
       }
       
       //valida que exista RUC si es FACTURA
       if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)){
               if (VariablesVentas.vRuc_Cli_Ped.trim().equalsIgnoreCase("")){
                       FarmaUtility.liberarTransaccion();
                       FarmaUtility.showMessage(this, "Debe ingresar el numero de RUC!!!", tblFormasPago);
                   return false;
               }
       }
       
       /**
        * 
        */     
       if(!UtilityFidelizacion.validaPedidoFidelizado(VariablesCaja.vNumPedVta,
                                                     VariablesVentas.vRuc_Cli_Ped,
                                                      this,
                                                     tblFormasPago,
                                                      // dubilluz 01.06.2012
                                                      VariablesFidelizacion.vNumTarjeta))
       {
           return false;
           
       }
       /**
        * Bloqueo de caja
        */
       return true;
       
       
  }
      
   /***************************/
  
  private boolean procesar()  {    
       //salvo las antiguas formas de pago
       try{
      //salvo las antiguas formas de pago    
       DBCajaElectronica.grabaFormaPagoPedidoSave();   
      //borro los antiguos       
       DBCajaElectronica.delFormaPagoPedidoSave();                               
      //grabo los nuevos
     
      for (int i = 0; i < detaPago.size(); i++)
              {
                      BeanDetaPago objDPB=(BeanDetaPago)detaPago.get(i);                
                      //grabar forma de pago del pedido
                      DBCajaElectronica.grabaFormaPagoPedidoChange(objDPB.getCod_fp(),
                                                     objDPB.getMonto(),
                                                     objDPB.getCod_moneda(),
                                                     VariablesCaja.vValTipoCambioPedido,
                                                     lblVuelto.getText(),
                                                     objDPB.getTotal(),
                                                     objDPB.getNrotarj(),
                                                     objDPB.getFecventarj(),
                                                     objDPB.getNomclitarj(),
                                                     objDPB.getCant(),
                                                     objDPB.getDnix(),
                                                     objDPB.getCodvou(),
                                                     objDPB.getLote());          
              //Se manda el vuelto pero solo se asigna la primero registro por default por eso se pone en 0 despues de grabarlo
              lblVuelto.setText("0.0");
          }
      
      FarmaUtility.aceptarTransaccion();
      VariablesCajaElectronica.indExitoCambioFP=true;
      return true;   
      }catch(SQLException ex)
      {
      System.out.println("Error Grabar FP Nuevo");   
      System.out.println(ex.getMessage());   
      VariablesCajaElectronica.indExitoCambioFP=false; 
      FarmaUtility.liberarTransaccion();            
      return false;
      }
     
      
  }
  
  private void colocaVueltoDetallePago(String pVuelto) {
    if (detaPago.size() <= 0){
      return;
    }
    boolean existeSoles = false;
    boolean existeDolares = false;
    boolean existeTarjeta = false; //ASOSA, 26.05.2010
    int filaSoles = 0;
    int filaDolares = 0;
    int filaTarjeta=0; //ASOSA, 26.05.2010
    for (int i = 0; i < detaPago.size(); i++) {
        BeanDetaPago objDPB02=(BeanDetaPago)detaPago.get(i);
      if ( ( objDPB02.getCod_fp() ).trim()
    		  .equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {          
        existeSoles = true;
        filaSoles = i;
        break;
      } else if ((objDPB02.getCod_fp()).trim()
    		  .equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES)) {          
        existeDolares = true;
        filaDolares = i;
      } else {
          existeTarjeta=true;
          filaTarjeta=i;
      }
    }
    ArrayList detaPago02=new ArrayList();
    if (existeSoles){
        //tblDetallePago.setValueAt(pVuelto, filaSoles, 7); ASOSA
        for(int c=0;c<detaPago.size();c++){
            BeanDetaPago objx=(BeanDetaPago)detaPago.get(c);
            if(c==filaSoles){
                objx.setComodin(pVuelto);
            }
            detaPago02.add(objx);
        }
    } else if (existeDolares && !existeSoles){
        //tblDetallePago.setValueAt(pVuelto, filaDolares, 7); ASOSA
        for(int c=0;c<detaPago.size();c++){
            BeanDetaPago objx=(BeanDetaPago)detaPago.get(c);
            if(c==filaDolares){
                objx.setComodin(pVuelto);
            }
            detaPago02.add(objx);
        }
    } else if(existeTarjeta && !existeDolares && !existeSoles){
        for(int c=0;c<detaPago.size();c++){
            BeanDetaPago objx=(BeanDetaPago)detaPago.get(c);
            if(c==filaTarjeta){
                objx.setComodin(pVuelto);
            }
            detaPago02.add(objx);
        }
    }
    if(!(!existeSoles && !existeDolares) || existeTarjeta){
        ArrayList detaPago03=new ArrayList();
        detaPago03=detaPago02;
        detaPago.clear();
        detaPago=detaPago03;
        //tblDetallePago.repaint();
    }
  }

  /**
   * descripcion de las formas de pago
   * para la impresion
   */
  private void formasPagoImpresion()
  {
	  //varificar que haya al menos una forma de pago declarado
	  if (detaPago.size() <= 0) {
		  VariablesCaja.vFormasPagoImpresion = "";
		  return;
	  }
	  //obtiene la descripcion de las formas de pago para la impresion
      for (int i = 0; i < detaPago.size(); i++) {
          BeanDetaPago objDPB03=(BeanDetaPago)detaPago.get(i);
    	  if (i == 0) {
    		  VariablesCaja.vFormasPagoImpresion = (objDPB03.getDesc_fp()).trim();
    	  } else {
    		  VariablesCaja.vFormasPagoImpresion += ", " + (objDPB03.getDesc_fp()).trim();
    	  }
      }
      
      String codFormaPagoDolares = getCodFormaPagoDolares();
      String codFormaPago = "";
      if (codFormaPagoDolares.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
    	  log.debug("La Forma de Pago Dolares esta Inactiva");
      } else {
	      for (int i = 0; i < detaPago.size(); i++)
	      {
                  BeanDetaPago objDPB04=(BeanDetaPago)detaPago.get(i);
	        codFormaPago = (objDPB04.getCod_fp()).trim();
	        if (codFormaPagoDolares.equalsIgnoreCase(codFormaPago))
	          VariablesCaja.vFormasPagoImpresion = 
	              VariablesCaja.vFormasPagoImpresion + "  Tipo Cambio:  " + 
	              VariablesCaja.vValTipoCambioPedido;
	      }
      }
  }

  private void actualizaClientePedido(String pNumPedVta, 
                                      String pCodCliLocal) throws SQLException {
    DBCaja.actualizaClientePedido(pNumPedVta, pCodCliLocal, 
                                  VariablesVentas.vNom_Cli_Ped, 
                                  VariablesVentas.vDir_Cli_Ped, 
                                  VariablesVentas.vRuc_Cli_Ped);
  }

  private void procesaPedidoVirtual() throws Exception{
    obtieneInfoPedidoVirtual();
    if (VariablesVirtual.vArrayList_InfoProdVirtual.size() != 1)
    {
      throw new Exception("Error al validar info del pedido virtual");
    }
    colocaInfoPedidoVirtual();
    try
    {
      UtilityCaja.procesaVentaProductoVirtual(this, txtNroPedido);
    }
    catch (Exception ex)
    {
      throw new Exception("Error al procesar el pedido virtual - \n" + 
                          ex.getMessage());

    }
    /*
     * Se grabara la respuesta obtenida por el proveedor al realizar la
     * recarga virtual
     */
    DBCaja.grabaRespuestaRecargaVirtual(VariablesVirtual.respuestaTXBean.getCodigoRespuesta(),
                                        VariablesCaja.vNumPedVta);
    
    if (!validaCodigoRespuestaTransaccion())
    {
      throw new Exception("Error al realizar la transaccion con el proveedor.\n" + 
                          VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + 
                          " - " + 
                          VariablesVirtual.respuestaTXBean.getDescripcion());
    }
  }

  private void evaluaMsjVentaVirtualGenerado(String pTipoProdVirtual)
  {
    if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA))
      muestraTarjetaVirtualGenerado();
    else if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA))
      FarmaUtility.showMessage(this, 
                               "La recarga automática se realizó satisfactoriamente.", 
                               null);
  }

  /**
   * Obtiene el codFormaPago Dolares
   * @author dubilluz
   * @since  13.10.2007
   */
  public String getCodFormaPagoDolares()
  {
    String codFP = "";
    try
    {
      codFP = DBCaja.getCodFPDolares();
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
      FarmaUtility.showMessage(this, "Error al Obtener el codidgo de Forma de Pago Dolares.\n" + 
                               ex.getMessage(), tblFormasPago);
    }
    log.debug("jcallo: codforma de pago dolares "+codFP);
    return codFP;
  }

  private void obtieneInfoPedidoVirtual()
    throws Exception
  {
    try
    {
      DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                      VariablesCaja.vNumPedVta);
      System.out.println("vArrayList_InfoProdVirtual : " + 
                         VariablesVirtual.vArrayList_InfoProdVirtual);
    }
    catch (SQLException sql)
    {
      sql.printStackTrace();
      throw new Exception("Error al obtener informacion del pedido virtual - \n" + 
                          sql);
    }
  }

  private void colocaInfoPedidoVirtual()
  {
    VariablesCaja.vCodProd = 
        FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            0, 0);
    VariablesCaja.vTipoProdVirtual = 
        FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            0, 1);
    VariablesCaja.vPrecioProdVirtual = 
        FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            0, 2);
    VariablesCaja.vNumeroCelular = 
        FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            0, 3);
    VariablesCaja.vCodigoProv = 
        FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            0, 4);
    VariablesCaja.vTipoTarjeta = 
        FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            0, 7);
  }

  private boolean validaCodigoRespuestaTransaccion()
  {
    boolean result = false;
    System.out.println("VariablesVirtual.vCodigoRespuesta 1" + 
                       VariablesVirtual.vCodigoRespuesta);
    if (VariablesVirtual.vCodigoRespuesta.equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL))
      result = true;
    return result;
  }

  private void muestraTarjetaVirtualGenerado()
  {
    DlgNumeroTarjetaGenerado dlgNumeroTarjetaGenerado = 
      new DlgNumeroTarjetaGenerado(myParentFrame, "", true);
    dlgNumeroTarjetaGenerado.setVisible(true);
    FarmaVariables.vAceptar = false;
  }
  
  /**
     * Obti
     * @return
     */
  private String getIndCommitAntesRecargar()
  {
    String ind;  
    try
    {
       ind = DBCaja.obtieneIndCommitAntesdeRecargar();
       System.out.println("ind Impr Antes de Recargar" +ind);
    }
    catch (SQLException sql)
    {
        ind = "N";
        sql.printStackTrace();
    }
      
     return ind.trim(); 
  }
         
  private void ejecutaRecargaVirtual() throws Exception{
      procesaPedidoVirtual();
      System.out.println("VariablesVirtual.vCodigoRespuesta 2" + 
                         VariablesVirtual.vCodigoRespuesta);
      System.out.println("**** graba la respuesta obtenida... ** ");

  }
  
  /**
   * Se generan los cupones por pedido luego de ser cobrados 
   * @author JCORTEZ
   * @since 03.07.2008
   * */
  private boolean generarPedidoCupon(String NumPed){
    boolean valor=false;
   try
    {
       DBCaja.generarCuponPedido(NumPed);
       valor=true;
       System.out.println("Se generaron los cupones del pedido :" +NumPed);
    }
    catch (SQLException sql)
    {
      sql.printStackTrace();
      FarmaUtility.showMessage(this,"Se genero un error al generar los cupones\n"+sql.getMessage(),tblFormasPago);
    }
  return valor;
  }
  
  
  
  private void cargarHora(String men){
     try{
      String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
      System.out.println("FECHA HORA------------------------------------------------->"+sysdate+"dentro del proceso"+men);
      System.out.println(sysdate);
      Date date1 = FarmaUtility.getStringToDate(sysdate,"dd/MM/yyyy HH:mm:ss");
    }catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
   
   
   /**
     * Actualiza los cupones en Matriz 
     * @author Diego Ubilluz
     * @param pNumPedVta
     */
   private void actualizaCuponesEnMatriz(String pNumPedVta,
                                         ArrayList pListaCuponesPedido,
                                         String pIndLinea
                                         ){
       
       ArrayList listCupones = new ArrayList(); 
       listCupones = (ArrayList)pListaCuponesPedido.clone(); 
       String vIndLineaBD = "";
       String vCodCupon  = "";
       String vResActMatriz = "";
       boolean vActCupon = false;
       int COL_COD_CUPON = 0;
       int COL_COD_FECHA_INI = 1;
       int COL_COD_FECHA_FIN = 2;   
       String vEstCuponMatriz = "";
       String vRetorno  = "";
       String vFechIni ="";
       String vFechFin ="";
       String indMultiUso ="";       
       try
       {
           if(listCupones.size()>0)
           { 
               if(listCupones.size()==1){
                   String vValor = "";
                   vValor = FarmaUtility.getValueFieldArrayList(listCupones,0,COL_COD_CUPON);
                   if(vValor.equalsIgnoreCase("NULO")){
                      return;
                   }
               }
                
               //  2. Se verificara si hay linea con matriz
               //--El segundo parametro indica si se cerrara la conexion
               vIndLineaBD = pIndLinea;
               //SE ESTA FORZANDO QUE NO HAYA LINEA
               vIndLineaBD = FarmaConstants.INDICADOR_N;
               if(vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                   System.out.println("No existe linea se cerrara la conexion ...");
                   FarmaConnectionRemoto.closeConnection();
               }
               
               // 3. SE ACTUALIZA EL CUPON 
               for(int i=0 ; i<listCupones.size() ; i++){
                   vCodCupon = 
                       FarmaUtility.getValueFieldArrayList(listCupones,
                                                           i,COL_COD_CUPON);
                   vFechIni  =
                       FarmaUtility.getValueFieldArrayList(listCupones,
                                                           i,COL_COD_FECHA_INI);
                   
                   vFechFin  =
                       FarmaUtility.getValueFieldArrayList(listCupones,
                                                           i,COL_COD_FECHA_FIN);                          
                   
                   indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta,
                                                               vCodCupon).trim();
                   
                   if(indMultiUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                   {
                        System.out.println("Actualiza en local  ...");
                        DBCaja.actualizaCuponGeneral(vCodCupon, 
                                                     ConstantsCaja.CONSULTA_ACTUALIZA_CUPON_LOCAL);


                        vActCupon = true;
                        //Si hay linea se actualizara en matriz
                        /*
                         * if (vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {


                            vEstCuponMatriz = 
                                    DBCaja.getEstadoCuponEnMatriz(vCodCupon, 
                                                                  FarmaConstants.INDICADOR_N).trim();
                            
                            //--Si valor de retorno es "0" es porque el cupon
                            //  no existe asi que se creara en Matriz
                            if (vEstCuponMatriz.equalsIgnoreCase("0")) {
                                System.out.println("Se graba el cupon en Matriz");
                                vRetorno = 
                                        DBCaja.grabaCuponEnMatriz(vCodCupon, 
                                                                  vFechIni, 
                                                                  vFechFin, 
                                                                  FarmaConstants.INDICADOR_N).trim();
                            }
                            
                            System.out.println("Actualiza en matriz  ...");
                            
                            vResActMatriz = 
                                    DBCaja.actualizaEstadoCuponEnMatriz(vCodCupon, 
                                                                        ConstantsCaja.CUPONES_USADOS, 
                                                                        FarmaConstants.INDICADOR_N);
                            
                            

                            //--Si la actualizacion se realizo con exito se actualiza
                            //  en el local que el cupon ya se proceso en Matriz
                            if (vRetorno.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                DBCaja.actualizaCuponGeneral(vCodCupon.trim(), 
                                                             ConstantsCaja.CONSULTA_ACTUALIZA_MATRIZ);
                            }
                            
                            VariablesCaja.vIndCommitRemota = true;
                            System.out.println("Fin de actualizacion");

                        }
                        */
                   }
                   else
                       System.out.println("Es un cupon multiuso..");
                   
               }
             }           
       }
       catch(SQLException e)
       {
           
           FarmaUtility.liberarTransaccion();
           FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                 FarmaConstants.INDICADOR_N);
       }
       /*
        * Validacion de Fecha actual del sistema contra
        * la fecha del cajero que cobrara
        * Se añadio para validar pedido Cobrado 
        * despues de una fecha establecida al inicio
        * dubilluz 04.03.2009
        **/
       log.debug("antes de validar");
       if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
               FarmaUtility.liberarTransaccion();
               return;
       }
       if(vActCupon)
       FarmaUtility.aceptarTransaccion();
       
       if(VariablesCaja.vIndCommitRemota)
           FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                 FarmaConstants.INDICADOR_N);
   }
    
   /**
    * Valida el uso de cupones
    * @author dubilluz
    * @since  20.08.2008
    */
   private boolean validaUsoCupones(String pNumPedVta,String pIndCloseConecction,
                                    ArrayList pListaCuponesPedido,
                                    String  pIndLinea)
   {
       log.debug("validando uso de cupones");
       
       ArrayList listCupones = new ArrayList(); 
       ArrayList ltDatosCupon = new ArrayList();
       String vCodCupon = "";
       String indMultiUso = "";       
       String vIndLineaBD = "";
       String valida = "";
       String vEstCupon = "";
       boolean retorno = false;
       try{
           listCupones = (ArrayList)pListaCuponesPedido.clone();           
           System.out.println("listCupones " + listCupones);
           // 1. SE VERIFICA SI EL VALOR DE LA LISTA NO FUE NULO
           if(listCupones.size()>0)
           { 
               if(listCupones.size()==1){
                   String vValor = "";
                   vValor = FarmaUtility.getValueFieldArrayList(listCupones,0,0);
                   if(vValor.equalsIgnoreCase("NULO")){
                      retorno =  true;
                      return retorno;
                   }
               }
                
               //  2. Se verificara si hay linea con matriz
               //--El segundo parametro indica si se cerrara la conexion
               vIndLineaBD = pIndLinea.trim();
    
               // 3. SE VALIDARA CADA CUPON 
               for(int i=0 ; i<listCupones.size() ; i++){
                   vCodCupon = FarmaUtility.getValueFieldArrayList(listCupones,i,0);
                   indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta,vCodCupon).trim();
                   
                   //Se valida el Cupon en el local
                    //Modificado por DVELIZ 04.10.08
                   DBVentas.verificaCupon(vCodCupon,ltDatosCupon,indMultiUso,
                   VariablesFidelizacion.vDniCliente);
                   /*
                   //Se validara el cupon en matriz si hay linea
                   if(vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                      System.out.println("Validando en matriz  ...");
                      valida = DBVentas.verificaCuponMatriz(vCodCupon,indMultiUso,
                                                            FarmaConstants.INDICADOR_N);
                      System.out.println("");
                      if(!valida.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                       if(valida.trim().equalsIgnoreCase("B"))
                         {
                           retorno = false;
                           FarmaUtility.showMessage(this,"La campaña no es valida.",tblFormasPago);
                           break;
                         }else
                         {
                           retorno = false;
                           break;
                         }
                      }
                   }
                   */
                   
                   vEstCupon = DBCaja.getEstCuponBloqueo(pNumPedVta,vCodCupon).trim();
                   System.out.println("Se bloquea el estado .. " + vEstCupon);
                   
               }
           }
           
           retorno = true;
           
           
       }
       catch(SQLException e)
       {
           //cierra la conexion
           FarmaConnectionRemoto.closeConnection();
           retorno =  false;
          e.printStackTrace();
           log.error(null,e);
           switch(e.getErrorCode())
           {
               case 20003: FarmaUtility.showMessage(this,"La campaña no es valida.",tblFormasPago); break;
               case 20004: FarmaUtility.showMessage(this,"Local no valido para el uso del cupon.",tblFormasPago); break;
               case 20005: FarmaUtility.showMessage(this,"Local de emisión no valido.",tblFormasPago); break;
               case 20006: FarmaUtility.showMessage(this,"Local de emisión no es local de venta.",tblFormasPago); break;
               case 20007: FarmaUtility.showMessage(this,"Cupón ya fue usado.",tblFormasPago); break;
               case 20008: FarmaUtility.showMessage(this,"Cupón esta anulado.",tblFormasPago); break;
               case 20009: FarmaUtility.showMessage(this,"Campaña no valido.",tblFormasPago); break;
               case 20010: FarmaUtility.showMessage(this,"Cupon no esta vigente .",tblFormasPago); break;
               default: FarmaUtility.showMessage(this,"Error al validar el cupon.\n"+e.getMessage(),tblFormasPago); break;

           }
           
       }
       System.out.println("**FIN**");
       return retorno;           
   }
   
   /**
    * metodo encargado de obtener el dni del cliente fidelizado que realizo la compra
    * 
    * */
   private String obtenerDniClienteFidelizado(String nroPedido){
	   String dniClienteFid = "";
	   try {
		   dniClienteFid = DBCaja.obtieneDniClienteFidVenta(nroPedido).trim(); 
	   } catch (Exception e) {
		   dniClienteFid = "";
		   log.debug("error al obtener DNI cliente del pedido : "+e.getMessage());
	   }
	   
	   return dniClienteFid;
   }
   
   
   
   /**
    * obtener todas las campañas de fidelizacion automaticas usados en el pedido
    * 
    * */
   private ArrayList getCampAutomaticasPedido(String nroPedido){
	   ArrayList listaCampAutomaticas = new ArrayList();
	   try {
		   listaCampAutomaticas = DBCaja.getListaCampAutomaticasVta(nroPedido);
		   if (listaCampAutomaticas.size()>0 ){
			   listaCampAutomaticas = (ArrayList)listaCampAutomaticas.get(0);
		   }
		   log.debug("listaCampAutomaticas listaCampAutomaticas ===> "+listaCampAutomaticas);
	   } catch (Exception e) {
		   log.debug("error al obtener campañas automaticas usados en el pedido : "+e.getMessage());
	   }
	   return listaCampAutomaticas;
   }
   
   /**
    * obtener todas las campañas de fidelizacion automaticas usados en el pedido
    * 
    * */
   private ArrayList CampLimitadasUsadosDeLocalXCliente(String dniCliente){
	   ArrayList listaCampLimitUsadosLocal = new ArrayList();
	   try {
		   listaCampLimitUsadosLocal = DBCaja.getListaCampUsadosLocalXCliente(dniCliente);
		   if (listaCampLimitUsadosLocal.size()>0 ){
			   listaCampLimitUsadosLocal = (ArrayList)listaCampLimitUsadosLocal.get(0);
		   }
		   log.debug("listaCampLimitUsadosLocal listaCampLimitUsadosLocal ===> "+listaCampLimitUsadosLocal);
	   } catch (Exception e) {
		   log.debug("error al obtener las campañas limitadas ya usados por cliente en LOCAL : "+e.getMessage());
	   }
	   return listaCampLimitUsadosLocal;
   }
   
   /**
    * obtener todas las campañas de fidelizacion automaticas usados en el pedido
    * 
    * */
   private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente){
	   ArrayList listaCampLimitUsadosMatriz = new ArrayList();
	   try {
		   //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
		   listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
		   if (listaCampLimitUsadosMatriz.size()>0 ){
			  listaCampLimitUsadosMatriz = (ArrayList)listaCampLimitUsadosMatriz.get(0);
		   }
		   log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> "+listaCampLimitUsadosMatriz);
	   } catch (Exception e) {
		   log.debug("error al obtener las campañas limitadas ya usados por cliente en MATRIZ : "+e.getMessage());		   
	   }
	   return listaCampLimitUsadosMatriz;
   }
   
   
   

    private boolean validaCajaAbierta(){

    boolean result=true;
    String Indicador="";
        try {
                //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
                 log.debug("VariablesCaja.vNumCaja ===> "+VariablesCaja.vNumCaja);
                Indicador = DBCaja.obtieneEstadoCaja();
                if (Indicador.trim().equalsIgnoreCase("N")){
                            FarmaUtility.showMessage(this, "La caja no se encuentra aperturada. Verifique!!!", null);
                        result=false;
                }
                log.debug("Se valida apertura de caja para el cobro ===> "+Indicador);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            result=false;
                log.debug("error al obtener indicador de caja abierta : "+e.getMessage());                 
        }
        
        //bloque de caja
         return result;
    }
    
    private void muestraprincipio(){
        System.out.println("VariablesCaja.vIndPedidoSeleccionado: "+VariablesCaja.vIndPedidoSeleccionado);
        System.out.println("VariablesCaja.vIndTotalPedidoCubierto: "+VariablesCaja.vIndTotalPedidoCubierto);
        System.out.println("VariablesCaja.vNumPedVta: "+VariablesCaja.vNumPedVta);
        System.out.println("VariablesCaja.vValTipoCambioPedido: "+VariablesCaja.vValTipoCambioPedido);
        System.out.println("VariablesCaja.vPermiteCampaña: "+VariablesCaja.vPermiteCampaña);
        System.out.println("VariablesCaja.vIndPedidoCobrado: "+VariablesCaja.vIndPedidoCobrado);
        System.out.println("VariablesCaja.vIndPedidoConvenio: "+VariablesCaja.vIndPedidoConvenio);
        System.out.println("VariablesCaja.usoConvenioCredito: "+VariablesCaja.usoConvenioCredito);
        System.out.println("VariablesCaja.vIndPedidoConProdVirtual: "+VariablesCaja.vIndPedidoConProdVirtual);
        System.out.println("VariablesCaja.vSecMovCaja: "+VariablesCaja.vSecMovCaja);

        System.out.println("VariablesVirtual.vCodigoRespuesta: "+VariablesVirtual.vCodigoRespuesta);

        System.out.println("VariablesConvenio.vValCoPago: "+VariablesConvenio.vValCoPago);
        System.out.println("VariablesConvenio.vValCredDis: "+VariablesConvenio.vValCredDis);
        System.out.println("VariablesConvenio.vCodConvenio: "+VariablesConvenio.vCodConvenio);
        System.out.println("VariablesConvenio.vCodCliente: "+VariablesConvenio.vCodCliente);

        System.out.println("VariablesVentas.vTip_Comp_Ped: "+VariablesVentas.vTip_Comp_Ped);
        System.out.println("VariablesVentas.vRuc_Cli_Ped: "+VariablesVentas.vRuc_Cli_Ped);
        System.out.println("VariablesVentas.vNom_Cli_Ped: "+VariablesVentas.vNom_Cli_Ped);
        System.out.println("VariablesVentas.vDir_Cli_Ped: "+VariablesVentas.vDir_Cli_Ped);
        System.out.println("VariablesVentas.vRuc_Cli_Ped: "+VariablesVentas.vRuc_Cli_Ped);
    }
   
}
