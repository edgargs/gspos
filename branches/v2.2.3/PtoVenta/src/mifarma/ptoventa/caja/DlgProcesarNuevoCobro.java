package mifarma.ptoventa.caja;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.EventQueue;

import java.io.File;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import mifarma.ptoventa.FrmEconoFar;
import mifarma.ptoventa.caja.reference.FacadeCaja;
import mifarma.ptoventa.caja.reference.HiloProceso;
import mifarma.ptoventa.convenio.reference.UtilityConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : DlgProcesarCobro.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      10.10.2013   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgProcesarNuevoCobro extends JDialog {
	
  private static final Logger log = LoggerFactory.getLogger(DlgProcesarNuevoCobro.class);

  private Frame myParentFrame;
  //private boolean vValorProceso;
  private JTable tblFormasPago;
  private JLabel lblVuelto;
  private JTable tblDetallePago;
  private JTextField txtNroPedido;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private String indCommitBefore = "";
  
  private boolean indUpdateEnConvenio = false;
  
    private boolean indFinalizaCobro = false;
    private boolean indAnularPedido = false;
    private boolean bProcesando = false;

    public DlgProcesarNuevoCobro()
  {
    this(null, "", false);
  }

  /**
   * Constructor con parametros.
   * @param parent
   * @param title
   * @param modal
   */
  public DlgProcesarNuevoCobro(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      log.error("",e);
    }
  }

  public DlgProcesarNuevoCobro(Frame parent, String title, boolean modal, 
                          JTable pTableModel, JLabel pVuelto, 
                          JTable pDetallePago, JTextField pNroPedido) {
	super(parent, title, modal);
	myParentFrame = parent;
	tblFormasPago = pTableModel;
	lblVuelto = pVuelto;
	tblDetallePago = pDetallePago;
	txtNroPedido = pNroPedido;
	
	try
	{
	  jbInit();
	}
	catch (Exception e)
	{
	  log.error("",e);
	}
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(238, 125));
    this.getContentPane().setLayout(null);
    this.setTitle("Procesando Informaci�n . . .");
    this.getContentPane().setLayout(borderLayout1);
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new WindowAdapter()
        {
          public void windowOpened(WindowEvent e)
          {
            this_windowOpened(e);
          }
        });


        jContentPane.setBounds(new Rectangle(0, 0, 240, 90));
       
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setResizable(false);
        FarmaUtility.centrarVentana(this);
  }
  void this_windowOpened(WindowEvent e)
  { 
      boolean vRetorno = false;
      if(indFinalizaCobro)
      {vRetorno = finalizaCobro();
        }else if(indAnularPedido){
            vRetorno = anulaPedido();
        }else{  
            vRetorno = procesar();
        }
      log.info("Termino de procesar !!!!");
      cerrarVentana(vRetorno);  
  }
  
  
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
  
    private boolean procesar()
    {
        if(!UtilityCaja.existeStockPedido(this,VariablesCaja.vNumPedVta))
          return false;
        
          //INICIO DE VALIDACIONES
          if(!UtilityCaja.validacionesCobroPedido(false,this,tblFormasPago))
              return false;
           
          if(!UtilityCaja.validaCajaAbierta(this))
              return false;
      
        //INICIO PROCESO DE COBRO
        try
        {
            //-- inicio validacion cupones
            //Se consulta para obtener los cupones usados en el pedido
            VariablesCaja.vIndLinea = "";
            VariablesCaja.listCuponesUsadosPedido = new ArrayList();
            
            //se agrego para tener un indicador si se mando realizar la recarga virtual
            VariablesCaja.vIndEnvioRecargar = false;
            
            DBCaja.getcuponesPedido(VariablesCaja.vNumPedVta,
                                    FarmaConstants.INDICADOR_N,
                                    VariablesCaja.listCuponesUsadosPedido,
                                    ConstantsCaja.CONSULTA_VALIDA_CUPONES);
        
            //validar los cupones usados en el pedido
            if(VariablesCaja.listCuponesUsadosPedido != null &&
                VariablesCaja.listCuponesUsadosPedido.size()>0)
            {   
                //validacion de cupon activos, ya que alguno pudiera estar inactivo por xmotivos
                if (VariablesCaja.vIndLinea!=null && VariablesCaja.vIndLinea.length()<1)
                {   
                    //quiere decir que no se validado aun el indicador de linea en matriz
                    VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                }
                boolean resp = validaUsoCupones(VariablesCaja.vNumPedVta,
                                                FarmaConstants.INDICADOR_N,
                                                VariablesCaja.listCuponesUsadosPedido,
                                                VariablesCaja.vIndLinea);
                //si alguno esta inactivo cancelar el proceso de cobros
                if(!resp)
                {   FarmaUtility.liberarTransaccion();
                    return false;
                }
            }
              
            //validacion de campa�as limitadas en cantidad de uso        
            //obteniendo el dni del cliente si se trata de una venta cliente fidelizado
            String dniClienteFidelizado = obtenerDniClienteFidelizado(VariablesCaja.vNumPedVta);

            if(dniClienteFidelizado!=null)
                dniClienteFidelizado = dniClienteFidelizado.trim();
            
            if(dniClienteFidelizado != null &&
                dniClienteFidelizado.length()>0)
            {   
                //quiere decir que es pedido de venta fidelizado
        	ArrayList listaCampLimitTerminados = new ArrayList();
        	ArrayList listaCampAutomaticasPedido = new ArrayList();

                //obtiene todas las campa�as automaticas usados en el pedido
        	listaCampAutomaticasPedido = getCampAutomaticasPedido(VariablesCaja.vNumPedVta);
                listaCampLimitTerminados = CampLimitadasUsadosDeLocalXCliente(dniClienteFidelizado);
                
        	boolean flag = false;
        	
        	if(listaCampLimitTerminados!=null)
                {   for(int i = 0; i<listaCampLimitTerminados.size();i++)
                    {   
                        String cod_camp = listaCampLimitTerminados.get(i).toString();
        		if( listaCampAutomaticasPedido != null &&
                            listaCampAutomaticasPedido.contains(cod_camp) )
                        {   
                            flag = true;
                            break;
        		}
                    }
                }
                
                //quiere decir que encontro al menos una campa�a que ya no deberia de aplicar, anular el pedido
        	if(flag)
                {   FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, "Error al cobrar pedido !\nEl descuento de la campa�a ya fue usado por el cliente !", tblFormasPago);
                    return false;
        	}
        }
        
        //verificar si es pedido por convenio
        String pIndPedConvenio = DBCaja.getIndPedConvenio(VariablesCaja.vNumPedVta);
        VariablesVentas.vEsPedidoConvenio = (pIndPedConvenio.equals("S")) ? true:false;
        
        //numero de lineas por BOLETA
        if ( VariablesVentas.vTip_Comp_Ped != null &&
             VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA))
        {   
            if(VariablesVentas.vEsPedidoConvenio)
            {   
                //si no devuelve nada, indicar cero filas
                String temp = DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_BOLETA_CON_CONVENIO);
                if(temp==null || "".equals(temp))
                    temp = "0";
                
                VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO = Integer.parseInt(temp);
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
            }
            else
            {   //si no devuelve nada, indicar cero filas
                String temp = DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_BOLETA_SIN_CONVENIO);
                if(temp==null || "".equals(temp))
                    temp = "0";

                VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO = Integer.parseInt(temp);
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
            }
        }
        //numero de linea por FACTURA
        else if( VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA))
        {   
            if(VariablesVentas.vEsPedidoConvenio)
            {   
                //si no devuelve nada, indicar cero filas
                String temp = DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_FACTURA_CON_CONVENIO);
                if(temp==null || "".equals(temp))
                    temp = "0";
                
                VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO = Integer.parseInt(temp);
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
            }
            else
            {   VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_FACTURA_SIN_CONVENIO));
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
            }
        }
        //numero de linea por TICKET
        else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET))
        {
             if(VariablesVentas.vEsPedidoConvenio){
                 VariablesCaja.TOTAL_LINEAS_POR_TICKET = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_TICKET));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_TICKET);
             }else{
                 VariablesCaja.TOTAL_LINEAS_POR_TICKET = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_TICKET));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_TICKET);
             }
        }
        
        //obtiene la descrip de la formas de pago para la impresion
        formasPagoImpresion();
        //actualiza datos del cliente como nombre direccion ruc, etc
        if(!VariablesVentas.vCod_Cli_Local.equalsIgnoreCase("")) {
           actualizaClientePedido(VariablesCaja.vNumPedVta,VariablesVentas.vCod_Cli_Local);
        }
        
        //cobrar pedido DEVOLVERA EXITO. si cobro correctamente
        //Se agrega DNI para guardar en cupone generados
        String resultado = DBCaja.cobraPedido(VariablesVentas.vTip_Comp_Ped,VariablesCaja.vPermiteCampa�a.trim(),dniClienteFidelizado.trim());
        
        if ( resultado.trim().equalsIgnoreCase("EXITO") ) {
            
            if (!UtilityCaja.validaAgrupacionComprobante(this,tblFormasPago)) {//el liberar transaccion esta dentro del metodo
                VariablesCaja.vIndPedidoCobrado = false;
                return false;
            }
            
            VariablesCaja.vIndPedidoCobrado = true;
            //si es pedido convenio y va usar credito de convenio
          if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) && 
              FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0) {
         
                    String vIndLinea = 
                        FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                       FarmaConstants.INDICADOR_S);
                    boolean indExisteConv = false;
                    boolean indMontoValido = false;

                    //NUEVO METODO DE CONVENIO
                    if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        String valor = 
                            DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, 
                                                        VariablesConvenio.vCodCliente, 
                                                        "" + 
                                                        VariablesConvenio.vValCredDis, 
                                                        FarmaConstants.INDICADOR_S);
                        
                        double vValCredDisponible = 
                            FarmaUtility.getDecimalNumber(valor);

                        //Paso 1 valida que exista el convenio
                        indExisteConv = 
                                UtilityConvenio.getIndClienteConvActivo(this, 
                                                                        tblFormasPago, 
                                                                        VariablesConvenio.vCodConvenio, 
                                                                        VariablesConvenio.vNumDocIdent,
                                                                        VariablesConvenio.vCodCliente);
                        
                        if (indExisteConv) {
                            //Paso 2 validar el monto disponible
                            indMontoValido = 
                                    UtilityConvenio.getIndValidaMontoConvenio(this, 
                                                                              tblFormasPago, 
                                                                              VariablesConvenio.vCodConvenio, 
                                                                              VariablesConvenio.vNumDocIdent, 
                                                                              FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago),
                                                                                VariablesConvenio.vCodCliente
                                                                              );
                            
                            if (indMontoValido) {
                                //El convenio est� activo y el monto a usar es correcto  


                                //actualiza consumo del cliente en matriz
                                
                                DBConvenio.actualizaConsumoClienteEnMatriz_v2(VariablesConvenio.vCodConvenio, 
                                                                           VariablesConvenio.vCodCliente, 
                                                                           "" +VariablesConvenio.vValCredDis, 
                                                                           FarmaConstants.INDICADOR_N, 
                                                                           VariablesCaja.vNumPedVta, 
                                                                           FarmaVariables.vIdUsu,
                                                                           VariablesConvenio.vNumDocIdent);
                                
                                //En el Metodo ActualizaConsumoClienteEnMatriz se inserta los Datos del consumo
                                //del convenio en la tabla CON_REG_VENTA
                                VariablesCaja.vIndCommitRemota = true;
                                //indica que debera hacer commit remotamente si to_do el proceso es exitoso
                                //actualizar credito disponible del cliente en local*/
                                DBConvenio.actualizarCreditoDisp(VariablesConvenio.vCodConvenio, 
                                                                 VariablesConvenio.vCodCliente, 
                                                                 VariablesCaja.vNumPedVta, 
                                                                 vValCredDisponible);
                                
                                indUpdateEnConvenio = true;
                                
                            }
                        }

                    } else { //si no hay linea con matriz
                        FarmaUtility.liberarTransaccion();
                        
                        FarmaUtility.showMessage(this, 
                                                 "Error: En este momento no hay linea con matriz.\nSi el problema persiste comunicarse con el operador de sistema !", 
                                                 tblFormasPago);
                        return false;
                    }
          }
          
          //obtener flag de IND PARA SABER SI IMPRIMIRA ANTES DE LA RECARGA VIRTUAL
          indCommitBefore =  getIndCommitAntesRecargar();

            if(dniClienteFidelizado.length()>0){//quiere decir que es pedido de venta fidelizado

                    boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada(
                                                                                            FarmaConstants.INDICADOR_N,//NO HAY LINEA
                                                                                            VariablesCaja.vNumPedVta,
                                                                                            this,
                                                                                            ConstantsCaja.ACCION_COBRO,
                                                                                            txtNroPedido,
                                                                                            FarmaConstants.INDICADOR_N
                                                                                           );
                    
                    if (!pRspCampanaAcumulad){
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                   FarmaConstants.INDICADOR_S);
                            VariablesCaja.vIndPedidoCobrado = false;
                            FarmaUtility.showMessage(this, 
                                                    "El pedido no puede ser cobrado. \n" +
                                                    "Presenta un producto regalo de campa�a que no se puede validar con Matriz. \n" +
                                                    "Int�ntelo nuevamente de lo contrario anule el pedido y gen�relo nuevamente." + 
                                                    "\n" +
                                                    "Gracias.",
                                                    tblFormasPago);
                            return false; 
                    }
            }
	          
          //flag de IND PARA SABER SI IMPRIMIRA ANTES DE LA RECARGA VIRTUAL
          if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
              //ERIOS 10.10.2013 Parte de finalizar cobro
	        /*if (VariablesCaja.vIndPedidoConProdVirtual) 
                {
	            	//viendo si tiene indicador linea matriz 
	            	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
		    	     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
	            	}
	            	
	            if (VariablesCaja.vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
	                VariablesCaja.vIndEnvioRecargar = true;//indicador de que se mando a recargar
	                //Se a�ade la validaci�n si existe algun error al enviar la informaci�n al servidor central
                        ejecutaRecargaVirtual();
                        
	            } else {
	                FarmaUtility.liberarTransaccion();
	                VariablesCaja.vIndPedidoCobrado = false;
	                FarmaUtility.showMessage(this, 
	                                         "El pedido no puede ser cobrado. \n" +	
	                                         "No hay linea com matriz.\n" +
	                                         "Int�ntelo nuevamente.", 
	                                         tblFormasPago);
	                return; 
	            }
	        }*/
	        //evalua indicador de impresion por error
	        String vIndImpre = DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);

                    if (!vIndImpre.equals("N")){
                    
                        if(VariablesCaja.listCuponesUsadosPedido.size() > 0){
                            
                            //viendo si tiene indicador linea matriz 
            	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
            	        	
            			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            	        	}

                            actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta, 
                                                     VariablesCaja.listCuponesUsadosPedido,
                                                     VariablesCaja.vIndLinea);
                        }
                        
                        
                        if( dniClienteFidelizado.length() > 0 ){//quiere decir que es pedido fidelizado
                        	ArrayList listaCampAutomaticasPedido = new ArrayList();
                        	listaCampAutomaticasPedido = getCampAutomaticasPedido(VariablesCaja.vNumPedVta);//obtiene todas las campa�as automaticas usados en el pedido
                        	for(int i=0;i< listaCampAutomaticasPedido.size();i++){
                        		String cod_camp_limit = listaCampAutomaticasPedido.get(i).toString().trim();
                        		//CAMBIAR EL A POR L
                        		if(cod_camp_limit.indexOf("L")>-1){//quiere decir que es una campa�a limitante
                        			
                        			DBCaja.registrarUsoCampLimitLocal(cod_camp_limit, dniClienteFidelizado);
                        		}
                        	}
                        }
                        
                        
                        if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
                                FarmaUtility.liberarTransaccion();
                                return false;
                        }
                        
     //VALIDAR SI HIZO UPDATE DAR COMMIT
     if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)
          &&                              
         FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0){  
             String vIndLineaMat = 
                 FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                FarmaConstants.INDICADOR_S);
         
           if(((indUpdateEnConvenio ) && 
              VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
              vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){                   
                           FarmaUtility.aceptarTransaccion();
                       if(VariablesCaja.vIndCommitRemota){
                                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                       }
            }else {
                  //DUBILLUZ 25.06.2010
                  VariablesCaja.vIndPedidoCobrado = false;
                  FarmaUtility.liberarTransaccion();
                  FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);                 
                  return false;                             
                  }
         }else{
                    FarmaUtility.aceptarTransaccion();
                     if(VariablesCaja.vIndCommitRemota){
                     FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                     }
                  }                                    

                        
                        /* DESPUES DEL MENSAJE DE COBRADO CON EXITO***/
		                //HASTA AQUI EL PEDIDO SE ENCUENTRA EN ESTADO S
		                UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
		                //obtiene informacion del vendedor
		                UtilityCaja.obtieneInfoVendedor();
		                //proceso de impresion de comprobante del pedido
		                
		                VariablesCaja.vIndLineaMatriz = VariablesCaja.vIndLinea;

                                try{
                                    DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                                    FarmaUtility.aceptarTransaccion();
                                }
                                catch(SQLException sql){
                                    log.error("",sql);
                                    FarmaUtility.liberarTransaccion();
                                }       
                                //ERIOS 10.10.2013 Parte de finalizar cobro
		                /*UtilityCaja.procesoImpresionComprobante(this, txtNroPedido); 
                                
		                if(VariablesCaja.vIndPedidoConProdVirtual) {
		                	FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
		                }*/
		                
                    }else{//si el indicador de impresion es N
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this, "Error en Aplicacion al cobrar el Pedido.",tblFormasPago);
                    }
	       // }
          } else {//quiere decir que el indicador de IMPRIMIR ANTES DE RECARGAR ES DIFERENTE DE S
		if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                    //ERIOS 10.10.2013 Parte de finalizar cobro
                    // Se mantiene la logica anterior , cobra realiza la recarga
                    // y solo si se obtuvo exito colocara el codigo de respuesta.
                    /*if (VariablesCaja.vIndPedidoConProdVirtual) {
                    	
                    	//viendo si tiene indicador linea matriz 
        	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
        			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
        	        	}
        	        	
                        if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            ejecutaRecargaVirtual();
                        } else {
                            FarmaUtility.liberarTransaccion();
                            VariablesCaja.vIndPedidoCobrado = false;
                            FarmaUtility.showMessage(this,  "El pedido no puede ser cobrado. \n" +
                                                            "No hay linea com matriz.\n" +
                                                            "Int�ntelo nuevamente.", 
                                        		    tblFormasPago);
                        }
                    }*/                        
                    
                    
                	if(VariablesCaja.listCuponesUsadosPedido.size()>0){//solo si se uso algun cupon
                    	//viendo si tiene indicador linea matriz 
        	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
        			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
        	        	}

                        actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta,
                                                VariablesCaja.listCuponesUsadosPedido,
                                                VariablesCaja.vIndLinea);
                	}
                    
                    if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
                            FarmaUtility.liberarTransaccion();
                            return false;
                    }
                    
                    if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)
                         &&                              
                        FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0){  
                            String vIndLineaMat = 
                                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                               FarmaConstants.INDICADOR_S);
                        
                          if(((indUpdateEnConvenio ) && 
                             VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
                             vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){                   
                                          FarmaUtility.aceptarTransaccion();
                                      if(VariablesCaja.vIndCommitRemota){
                                                           FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                                      }
                           }else {
                                     
                                     VariablesCaja.vIndPedidoCobrado = false;
                              
                                 FarmaUtility.liberarTransaccion();
                                 FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                                 FarmaUtility.showMessage(this,"Error al actualizar el uso del Convenio.\nComun�quese con el Operador de Sistemas.",null);
                                 return false;                             
                                 }
                        }else{
                                   FarmaUtility.aceptarTransaccion();
                                    if(VariablesCaja.vIndCommitRemota){
                                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                                    }
                                 }                                   

                    
                    UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                    UtilityCaja.obtieneInfoVendedor();

                    try{
                        DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                        FarmaUtility.aceptarTransaccion();
                        
                    }
                    catch(SQLException sql){
                        log.error("",sql);
                        FarmaUtility.liberarTransaccion();
                    }       
                    //ERIOS 10.10.2013 Parte de finalizar cobro
                    /*UtilityCaja.procesoImpresionComprobante(this, txtNroPedido); 
                    
                    if(VariablesCaja.vIndPedidoConProdVirtual)  {
                        FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
                    }*/ 
                }
            } 
          //FIN DE QUE SE HAYA COBRADO EXITOSAMENTE
        } else if (resultado.trim().equalsIgnoreCase("ERROR")) {
          FarmaUtility.liberarTransaccion();
          VariablesCaja.vIndPedidoCobrado = false;
          FarmaUtility.showMessage(this, 
                                   "El pedido no puede ser cobrado. \n" +
                                   "Los totales de formas de pago y cabecera no coinciden. \n" +
                                   "Comun�quese con el Operador de Sistemas inmediatamente." + 
                                   ". \n" +
                                   "NO CIERRE LA VENTANA.", 
                                   tblFormasPago);
        }

      } catch (SQLException sql) {//error de base de datos al cobrar
        
    	FarmaUtility.liberarTransaccion();
        log.error(null,sql);
        String pMensaje = sql.getMessage();
        
        int nIsSecCajaNull = pMensaje.indexOf("CHECK_SEC_MOV_CAJA");
        
        if(nIsSecCajaNull>0){
            FarmaUtility.showMessage(this, "No se pudo cobrar el pedido.\nInt�ntelo nuevamente", tblFormasPago);            
        }
        else{

            if ( VariablesCaja.vIndEnvioRecargar ){
                log.error("ERROR de BASE DE DATOS AL MOMENTO DE COBRAR UN RECARGAR VIRTUAL...PERO IGUAL SE MANDO A RECARGAR!");            
                try{
                    FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                                  FarmaVariables.vCodLocal,
                                                  VariablesPtoVenta.vDestEmailErrorCobro,
                                                  "Error Recarga Virtual, error de base datos",
                                                  "Error de Recarga Virtual",
                                                  "Error al realizar recarga virtual al numero : "+VariablesCaja.vNumeroCelular,
                                                  "IP PC: " + FarmaVariables.vIpPc + "<br>"+
                                                  "");
                }catch(Exception e){
                    log.error("ERROR AL TRATAR de enviar correo de alerta de recarga virtual");
                }
            }
  
                    log.error("",sql);
                    if(sql.getErrorCode()>20000)
                    {
                      VariablesCaja.vIndPedidoCobrado = false;  
                      FarmaUtility.showMessage(this,sql.getMessage().substring(10,sql.getMessage().indexOf("ORA-06512")),null);  
                      } else{
                          VariablesCaja.vIndPedidoCobrado = false;
                          FarmaUtility.showMessage(this, "Error en BD al cobrar el Pedido.\n" + sql.getMessage(), tblFormasPago);
                      }
                        
        }
      } catch (Exception ex) {//error inesperado
    	  log.error("",ex);//error inesperado
    	  if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
    		  if(VariablesCaja.vIndPedidoConProdVirtual) {
    			  //evalua indicador de impresion por error
    			  String vIndImpre="N";
    			  try {
    				  vIndImpre = DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);
    			  }catch(Exception e){
    			log.error("",e);
    				  vIndImpre = "N";
    			  }
    			
    			  
    			  if (!vIndImpre.equals("N")) {
    			
    			      if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
    			              FarmaUtility.liberarTransaccion();
    			              return false;
    			      }
                              
    			      	  if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)
    				       &&                              
    				      FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0){  
    				          String vIndLineaMat = 
    				              FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
    				                                             FarmaConstants.INDICADOR_S);
    				      
    				        if(((indUpdateEnConvenio ) && 
    				           VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
    				           vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){                   
    				                        FarmaUtility.aceptarTransaccion();
    				                    if(VariablesCaja.vIndCommitRemota){
    				                                         FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
    				                    }
    				         }else {
    				                   VariablesCaja.vIndPedidoCobrado = false;
                                            
    				               FarmaUtility.liberarTransaccion();
    				               FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
    				               FarmaUtility.showMessage(this,"Error al actualizar el uso del Convenio.\nComun�quese con el Operador de Sistemas.",null);
    				               return false;                             
    				               }
    				      }else{
    				                 FarmaUtility.aceptarTransaccion();
    				                  if(VariablesCaja.vIndCommitRemota){
    				                  FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
    				                  }
    				               }                                   

		              UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
		              
		              UtilityCaja.obtieneInfoVendedor();
    			   
    			      try{
    			          DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
    			          FarmaUtility.aceptarTransaccion();
    			          
    			      }
    			      catch(SQLException sql){
    			          log.error("",sql);
    			          FarmaUtility.liberarTransaccion();
    			          
    			      }       
    			      //ERIOS 10.10.2013 Parte de finalizar cobro
		              /*UtilityCaja.procesoImpresionComprobante(this, txtNroPedido);
    			           
		              FarmaUtility.showMessage(this, 
		                                       "Error en Aplicacion al cobrar el Pedido.\n" + 
		                                       ex.getMessage(),
		                                       tblFormasPago);
		        
		              FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);*/
    			  }else{
    				  FarmaUtility.liberarTransaccion();
    				  if(VariablesCaja.vIndCommitRemota){
    					
    					  FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      		FarmaConstants.INDICADOR_N);
    				  }
    				  FarmaUtility.showMessage(this, 
                                        "Error en Aplicacion al cobrar el Pedido.\n" + 
                                        ex.getMessage(), tblFormasPago);   
              
    			  }
    		  }
    	  } else {
    		  FarmaUtility.liberarTransaccion();
    		  FarmaUtility.showMessage(this, 
	                                    "Error en Aplicacion al cobrar el Pedido.\n" + 
	                                    ex.getMessage(), tblFormasPago);          
    	  }
    	  
    	  //no se pudo cobrar el pedido
          VariablesCaja.vIndPedidoCobrado = false;
          
      } finally {
          
         //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
    	 FarmaConnectionRemoto.closeConnection();//dentro metodo solo cierra si estuvo abierta alguna conexion
         
      }
      
      return true;
  }

    private void colocaVueltoDetallePago(String pVuelto)
    {
        if (tblDetallePago.getRowCount() <= 0)
        {   return;
        }
        boolean existeSoles = false;
        boolean existeDolares = false;
        int filaSoles = 0;
        int filaDolares = 0;
        
        for (int i = 0; i < tblDetallePago.getRowCount(); i++)
        {   
            String temp = (String)tblDetallePago.getValueAt(i, 0);
            if(temp != null)
                temp = temp.trim();
            else
                temp = "";
            
            if (ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES.equalsIgnoreCase(temp))
            {
                existeSoles = true;
                filaSoles = i;
                break;
            }
            else if(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES.equalsIgnoreCase(temp))
            {
                existeDolares = true;
                filaDolares = i;
            }
        }
        if (existeSoles)
        {   tblDetallePago.setValueAt(pVuelto, filaSoles, 7);
        }
        else if (existeDolares && !existeSoles)
        {   tblDetallePago.setValueAt(pVuelto, filaDolares, 7);
        }
        tblDetallePago.repaint();
    }

  /**
   * descripcion de las formas de pago
   * para la impresion
   */
  private void formasPagoImpresion()
  {
        //varificar que haya al menos una forma de pago declarado
        if (tblDetallePago.getRowCount() <= 0)
        {   VariablesCaja.vFormasPagoImpresion = "";
            return;
        }
        //obtiene la descripcion de las formas de pago para la impresion
        for (int i = 0; i < tblDetallePago.getRowCount(); i++)
        {   if (i == 0)
            {   VariablesCaja.vFormasPagoImpresion = ((String) tblDetallePago.getValueAt(i, 1)).trim();
            }
            else
            {   VariablesCaja.vFormasPagoImpresion += ", " + ((String) tblDetallePago.getValueAt(i, 1)).trim();
            }
        }
        
        String codFormaPagoDolares = getCodFormaPagoDolares();
        String codFormaPago = "";
        if (FarmaConstants.INDICADOR_N.equalsIgnoreCase(codFormaPagoDolares))
        {
            log.debug("La Forma de Pago Dolares esta Inactiva");
        }
        else
        {
            for (int i = 0; i < tblDetallePago.getRowCount(); i++)
            {
                codFormaPago = ((String) tblDetallePago.getValueAt(i, 0)).trim();
                if (codFormaPagoDolares.equalsIgnoreCase(codFormaPago))
                    VariablesCaja.vFormasPagoImpresion = 
                
                VariablesCaja.vFormasPagoImpresion + "  Tipo Cambio:  " + 
                VariablesCaja.vValTipoCambioPedido;
            }
        }
    }

    private void actualizaClientePedido(String pNumPedVta, 
                                        String pCodCliLocal) throws SQLException
    {
        DBCaja.actualizaClientePedido(pNumPedVta, pCodCliLocal, 
                                  VariablesVentas.vNom_Cli_Ped, 
                                  VariablesVentas.vDir_Cli_Ped, 
                                  VariablesVentas.vRuc_Cli_Ped);
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
        {   codFP = DBCaja.getCodFPDolares();
        }
        catch (SQLException ex)
        {
            log.error("",ex);
            FarmaUtility.showMessage(this, 
                                     "Error al Obtener el codidgo de Forma de Pago Dolares.\n" + 
                                    ex.getMessage(), 
                                     tblFormasPago);
        }
        log.debug("jcallo: codforma de pago dolares "+codFP);
        return codFP;
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
            ind = DBCaja.obtieneIndCommitAntesdeRecargar().trim();
            log.debug("ind Impr Antes de Recargar" +ind);
        }
        catch (SQLException sql)
        {
            ind = "N";
            log.error("",sql);
        }
        
        return ind;
    }
  
  /**
   * Se generan los cupones por pedido luego de ser cobrados 
   * @author JCORTEZ
   * @since 03.07.2008
   * */
    private boolean generarPedidoCupon(String NumPed)
    {
        boolean valor=false;
        try
        {
            DBCaja.generarCuponPedido(NumPed);
            valor=true;
            log.debug("Se generaron los cupones del pedido :" +NumPed);
        }
        catch (SQLException sql)
        {
            log.error("",sql);
            FarmaUtility.showMessage(this,"Se genero un error al generar los cupones\n"+sql.getMessage(),tblFormasPago);
        }
        return valor;
    }
  
  
  
    private void cargarHora(String men)
    {
        try
        {
            String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
            log.debug("FECHA HORA------------------------------------------------->"+sysdate+"dentro del proceso"+men);
            log.debug(sysdate);
            Date date1 = FarmaUtility.getStringToDate(sysdate,"dd/MM/yyyy HH:mm:ss");
        }
        catch(SQLException e)
        {
            log.error("",e);
        }
    }
   
   
   /**
     * Actualiza los cupones en Matriz 
     * @author Diego Ubilluz
     * @param pNumPedVta
     */
    private void actualizaCuponesEnMatriz(String pNumPedVta,
                                         ArrayList pListaCuponesPedido,
                                         String pIndLinea)
    {
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
            if(listCupones != null &&
                listCupones.size()>0)
            { 
                if(listCupones.size()==1)
                {   String vValor = "";
                    vValor = FarmaUtility.getValueFieldArrayList(listCupones,0,COL_COD_CUPON);
                    if("NULO".equalsIgnoreCase(vValor))
                    {
                        return;
                    }
                }
                
                //  2. Se verificara si hay linea con matriz
                //--El segundo parametro indica si se cerrara la conexion
                vIndLineaBD = pIndLinea;
                //SE ESTA FORZANDO QUE NO HAYA LINEA
                vIndLineaBD = FarmaConstants.INDICADOR_N;
                if(vIndLineaBD != null &&
                    FarmaConstants.INDICADOR_N.equalsIgnoreCase(vIndLineaBD.trim()))
                {
                    log.debug("No existe linea se cerrara la conexion ...");
                    FarmaConnectionRemoto.closeConnection();
                }
                
                // 3. SE ACTUALIZA EL CUPON 
               
                for(int i=0 ; i<listCupones.size() ; i++)
                {
                    vCodCupon = FarmaUtility.getValueFieldArrayList(listCupones,
                                                                    i,
                                                                    COL_COD_CUPON);
                    vFechIni  = FarmaUtility.getValueFieldArrayList(listCupones,
                                                                    i,
                                                                    COL_COD_FECHA_INI);
                    
                    vFechFin  = FarmaUtility.getValueFieldArrayList(listCupones,
                                                                    i,
                                                                    COL_COD_FECHA_FIN);                          
                    
                    indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta,
                    vCodCupon).trim();
                   
                    if( indMultiUso != null &&
                        FarmaConstants.INDICADOR_N.equalsIgnoreCase(indMultiUso.trim()))
                    {
                        log.debug("Actualiza en local  ...");
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
                                log.debug("Se graba el cupon en Matriz");
                                vRetorno = 
                                        DBCaja.grabaCuponEnMatriz(vCodCupon, 
                                                                  vFechIni, 
                                                                  vFechFin, 
                                                                  FarmaConstants.INDICADOR_N).trim();
                            }
                            
                            log.debug("Actualiza en matriz  ...");
                            
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
                            log.debug("Fin de actualizacion");

                        }
                        */
                    }
                    else
                        log.debug("Es un cupon multiuso..");
                   
                }
            }
        }
        catch(SQLException e)
        {
           
           FarmaUtility.liberarTransaccion();
           FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                 FarmaConstants.INDICADOR_N);
        }
        finally
        {
            /*
            * Validacion de Fecha actual del sistema contra
            * la fecha del cajero que cobrara
            * Se a�adio para validar pedido Cobrado 
            * despues de una fecha establecida al inicio
            * dubilluz 04.03.2009
            **/
            log.debug("antes de validar");
            if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago))
            {   FarmaUtility.liberarTransaccion();
                return;
            }
            if(vActCupon)
                FarmaUtility.aceptarTransaccion();
            
            if(VariablesCaja.vIndCommitRemota)
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                        FarmaConstants.INDICADOR_N);
        }
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
       
        try
        {
            if(pListaCuponesPedido != null)
                listCupones = (ArrayList)pListaCuponesPedido.clone();           
            log.debug("listCupones " + listCupones);
            
            // 1. SE VERIFICA SI EL VALOR DE LA LISTA NO FUE NULO
            if(listCupones != null &&
            listCupones.size()>0)
            { 
                if(listCupones.size()==1)
                {
                    String vValor = "";
                    vValor = FarmaUtility.getValueFieldArrayList(listCupones,0,0);
                    if("NULO".equalsIgnoreCase(vValor))
                    {
                        retorno =  true;
                        return retorno;
                    }
                }
                
                //  2. Se verificara si hay linea con matriz
                //--El segundo parametro indica si se cerrara la conexion
                vIndLineaBD = pIndLinea.trim();
    
                // 3. SE VALIDARA CADA CUPON 
                if(listCupones != null)
                {   for(int i=0 ; i<listCupones.size() ; i++)
                    {
                        vCodCupon = FarmaUtility.getValueFieldArrayList(listCupones,i,0);
                        indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta,vCodCupon).trim();
                       
                        //Se valida el Cupon en el local
                        //Modificado por DVELIZ 04.10.08
                        DBVentas.verificaCupon(vCodCupon,
                                                ltDatosCupon,
                                                indMultiUso,
                                                VariablesFidelizacion.vDniCliente);
                       
                        //Se validara el cupon en matriz si hay linea
                        /*
                        if(vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                          log.debug("Validando en matriz  ...");
                          valida = DBVentas.verificaCuponMatriz(vCodCupon,indMultiUso,
                                                                FarmaConstants.INDICADOR_N);
                          log.debug("");
                          if(!valida.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                           if(valida.trim().equalsIgnoreCase("B"))
                             {
                               retorno = false;
                               FarmaUtility.showMessage(this,"La campa�a no es valida.",tblFormasPago);
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
                        log.debug("Se bloquea el estado .. " + vEstCupon);
                    }
                }
            }
            retorno = true;
        }
        catch(SQLException e)
        {
            //cierra la conexion
            FarmaConnectionRemoto.closeConnection();
            retorno =  false;
            log.error("",e);
            log.error(null,e);
            switch(e.getErrorCode())
            {
                case 20003: FarmaUtility.showMessage(this,"La campa�a no es valida.",tblFormasPago); break;
                case 20004: FarmaUtility.showMessage(this,"Local no valido para el uso del cupon.",tblFormasPago); break;
                case 20005: FarmaUtility.showMessage(this,"Local de emisi�n no valido.",tblFormasPago); break;
                case 20006: FarmaUtility.showMessage(this,"Local de emisi�n no es local de venta.",tblFormasPago); break;
                case 20007: FarmaUtility.showMessage(this,"Cup�n ya fue usado.",tblFormasPago); break;
                case 20008: FarmaUtility.showMessage(this,"Cup�n esta anulado.",tblFormasPago); break;
                case 20009: FarmaUtility.showMessage(this,"Campa�a no valido.",tblFormasPago); break;
                case 20010: FarmaUtility.showMessage(this,"Cupon no esta vigente .",tblFormasPago); break;
                default: FarmaUtility.showMessage(this,"Error al validar el cupon.\n"+e.getMessage(),tblFormasPago); break;
            }
        }
        log.debug("**FIN**");
        return retorno;
    }
   
   /**
    * metodo encargado de obtener el dni del cliente fidelizado que realizo la compra
    * 
    * */
   private String obtenerDniClienteFidelizado(String nroPedido)
   {
        String dniClienteFid = "";
        try
        {   dniClienteFid = DBCaja.obtieneDniClienteFidVenta(nroPedido).trim(); 
        }
        catch (Exception e)
        {   dniClienteFid = "";
            log.error("error al obtener DNI cliente del pedido "+e);
        }
        return dniClienteFid;
    }
   
   /**
    * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
    * 
    * */
    private ArrayList getCampAutomaticasPedido(String nroPedido)
   {    ArrayList listaCampAutomaticas = new ArrayList();
        try
        {   listaCampAutomaticas = DBCaja.getListaCampAutomaticasVta(nroPedido);
            if (listaCampAutomaticas != null &&
                listaCampAutomaticas.size()>0 )
            {   
                listaCampAutomaticas = (ArrayList)listaCampAutomaticas.get(0);
            }
            log.debug("listaCampAutomaticas listaCampAutomaticas ===> "+listaCampAutomaticas);
        }
        catch (Exception e)
        {   log.debug("error al obtener campa�as automaticas usados en el pedido : "+e.getMessage());
        }
        return listaCampAutomaticas;
    }
   
   /**
    * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
    * 
    * */
    private ArrayList CampLimitadasUsadosDeLocalXCliente(String dniCliente)
    {   ArrayList listaCampLimitUsadosLocal = new ArrayList();
        try
        {   listaCampLimitUsadosLocal = DBCaja.getListaCampUsadosLocalXCliente(dniCliente);
            if (listaCampLimitUsadosLocal != null &&
                listaCampLimitUsadosLocal.size()>0 )
            {   
                listaCampLimitUsadosLocal = (ArrayList)listaCampLimitUsadosLocal.get(0);
            }
            log.debug("listaCampLimitUsadosLocal listaCampLimitUsadosLocal ===> "+listaCampLimitUsadosLocal);
        }
        catch (Exception e) 
        {   log.error("error al obtener las campa�as limitadas ya usados por cliente en LOCAL : ",e);
        }
        return listaCampLimitUsadosLocal;
    }
   
   /**
    * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
    * 
    * */
    private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente)
    {   
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try
        {   //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            if (listaCampLimitUsadosMatriz.size()>0 )
            {   listaCampLimitUsadosMatriz = (ArrayList)listaCampLimitUsadosMatriz.get(0);
            }
            log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> "+listaCampLimitUsadosMatriz);
        }
        catch (Exception e)
        {   log.debug("error al obtener las campa�as limitadas ya usados por cliente en MATRIZ : "+e.getMessage());		   
        }
        return listaCampLimitUsadosMatriz;
    }

    private boolean finalizaCobro()
    {
        
        
        //2. Actualiza forma de pago
        try
        {
            //obtiene el monto del vuelto
            String vueltoPedido = lblVuelto.getText().trim();
            colocaVueltoDetallePago(vueltoPedido);
            
            //detalle de formas de pago
            VariablesCaja.vDescripcionDetalleFormasPago = "";
            VariablesCaja.vDescripcionDetalleFormasPago = ConstantsCaja.COLUMNAS_DETALLE_FORMA_PAGO;
            for (int i = 0; i < tblDetallePago.getRowCount(); i++)
            {
                //grabar forma de pago del pedido
                //descripcion de la forma de pago en el detalle
                DBCaja.grabaFormaPagoPedido(((String) tblDetallePago.getValueAt(i,0)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,4)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,6)).trim(),
                                           VariablesCaja.vValTipoCambioPedido,
                                           ((String) tblDetallePago.getValueAt(i,7)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,5)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,8)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,9)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,10)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,2)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,12)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,13)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,14)).trim());
                
                VariablesCaja.vDescripcionDetalleFormasPago = VariablesCaja.vDescripcionDetalleFormasPago + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 0) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 1) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 3) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 4) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 5) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 7) + "<BR>";
            }
            
            //2.1 Valida montos de pedido
            String v_Resultado = DBCaja.verificaPedidoFormasPago(VariablesCaja.vNumPedVta);
            if (v_Resultado.trim().equalsIgnoreCase("ERROR")) {
              FarmaUtility.liberarTransaccion();
              //VariablesCaja.vIndPedidoCobrado = false;
              FarmaUtility.showMessage(this, 
                                       "El pedido no puede ser cobrado. \n" +
                                       "Los totales de formas de pago y cabecera no coinciden. \n" +
                                       "Comun�quese con el Operador de Sistemas inmediatamente." + 
                                       ". \n" +
                                       "NO CIERRE LA VENTANA.", 
                                       tblFormasPago);
                return false;
            }
            
            FarmaUtility.aceptarTransaccion();
        }
        catch (SQLException e)
        {   //No continuar con la transaccion
            FarmaUtility.liberarTransaccion();
            //VariablesCaja.vIndPedidoCobrado = false;
            log.error("", e);
            FarmaUtility.showMessage(this,
                                     "Error. No se pudo registrar una de las formas de pagos", 
                                     tblFormasPago);
            return false;
        }
            
        //2.5 Evalua proceso de recarga virtual
        //ERIOS 19.11.2013 Se comentar por si algun dia cambian el procedimiento
        /*if(!vProcesoRecarga){
            anulaPedido();
            return false;
        }*/
        
        //3. Imprimir Comprobante        
        UtilityCaja.procesoImpresionComprobante(this, txtNroPedido); 
        
        if(VariablesCaja.vIndPedidoConProdVirtual)
        {   FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
        }
        
        //4. verifica si existen pedidos pendientes de anulacion despues de N minutos
        UtilityCaja.verificaPedidosPendientes(this);
        return true;
    }
    
    private boolean anulaPedido()
    {
        boolean flag = false;
        //1. Finaliza cobro
        //2. Imprimir Comprobante        
        UtilityCaja.procesoImpresionComprobante(this, txtNroPedido, true); 
        //3. Anula pedido
        try
        {   DBCaja.anularPedido(VariablesCaja.vNumPedVta, 
                                "%",
                                "%", 
                                VariablesCaja.vValTotalPagar,
                                VariablesCaja.vNumCajaImpreso,
                                "Anulacion",
                                "N");
            FarmaUtility.aceptarTransaccion();
        }
        catch (Exception e)
        {   FarmaUtility.liberarTransaccion();
            log.error("", e);
            flag = false;
        }
        
        //4. Anula Ticket
        UtilityCaja.obtieneImpresTicket(this,txtNroPedido);
        UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);

        try
        {   UtilityCaja.getImpresionTicketAnulado(VariablesCaja.vNumPedVta,
                                                  FarmaVariables.vNuSecUsu,
                                                  VariablesCaja.vNumCajaImpreso,
                                                  VariablesCaja.vNumTurnoCajaImpreso);
        }
        catch (Exception e)
        {   log.error("",e);
            flag = false;
        }
        return flag;
    }

    public void setIndFinalizaCobro(boolean b)
    {   this.indFinalizaCobro = b;
    }

    public void setIndAnularPedido(boolean b)
    {   this.indAnularPedido = b;
    }

    public boolean isBProcesando() {
        return bProcesando;
    }
}