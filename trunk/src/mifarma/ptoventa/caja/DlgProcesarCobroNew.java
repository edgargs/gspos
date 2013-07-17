package mifarma.ptoventa.caja;
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
	
  private static final Log log = LogFactory.getLog(DlgProcesarCobro.class);

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
    muestraprincipio();
    procesar();
    System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    cerrarVentana(false);
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
  private void procesar()
  {
      long tmpIni,tmpFin,tmpT1,tmpT2,tmpT3,tmpT4;
      tmpIni = System.currentTimeMillis();
      tmpT1 = System.currentTimeMillis();
      
     //INICIO DE VALIDACIONES
      if(!validacionesCobroPedido())
          return;
      
      tmpT2 = System.currentTimeMillis();      
      log.debug("t1 Validaciondes Iniciales Cobro : "+(tmpT2 - tmpT1)+" milisegundos");
      
      
      tmpT1 = System.currentTimeMillis();      
      //UtilityCaja.bloqueoCajaApertura(VariablesCaja.vSecMovCaja);    
      tmpT2 = System.currentTimeMillis();      
      log.debug("t2 Bloqueo a una tabla para validar que se cierre la caja en otra PC : "+(tmpT2 - tmpT1)+" milisegundos");
      System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
      /**
       * Se valida que la caja este abierta y se bloquea al mismo tiempo.
       * @AUTHOR JCORTEZ 
       * @SINCE 18.05.09
       * */
      tmpT3 = System.currentTimeMillis();      
      if(!validaCajaAbierta())
       return;
      tmpT4 = System.currentTimeMillis(); 
      log.debug("t3 Bloqueo Caja_Pago para validar que no cobre pedido si se cierra caja : "+(tmpT3 - tmpT4)+" milisegundos");
      
    //INICIO PROCESO DE COBRO
    try {
        
    	//-- inicio validacion cupones
        //Se consulta para obtener los cupones usados en el pedido
        tmpT1 = System.currentTimeMillis();      
        
        VariablesCaja.vIndLinea = "";
        VariablesCaja.listCuponesUsadosPedido = new ArrayList();
        VariablesCaja.vIndEnvioRecargar = false;//se agrego para tener un indicador si se mando realizar la recarga virtual
        
        DBCaja.getcuponesPedido(VariablesCaja.vNumPedVta,
                                FarmaConstants.INDICADOR_N,
                                VariablesCaja.listCuponesUsadosPedido,
                                ConstantsCaja.CONSULTA_VALIDA_CUPONES);
        
        log.debug("JCALLO VariablesCaja.listCuponesUsadosPedido:"+VariablesCaja.listCuponesUsadosPedido);
        //validar los cupones usados en el pedido
        if(VariablesCaja.listCuponesUsadosPedido.size()>0){
        	//validacion de cupon activos, ya que alguno pudiera estar inactivo por xmotivos
            log.debug("entro a validar cupones");
            if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
            	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            }
            boolean resp = validaUsoCupones(VariablesCaja.vNumPedVta,
                                            FarmaConstants.INDICADOR_N,
                                            VariablesCaja.listCuponesUsadosPedido,
                                            VariablesCaja.vIndLinea);
            //si alguno esta inactivo cancelar el proceso de cobros
            if(!resp){
            	FarmaUtility.liberarTransaccion();
                return;
            }
        }
        
          tmpT2 = System.currentTimeMillis();      
          log.debug("t3 Validacion de cupones Usados: "+(tmpT2 - tmpT1)+" milisegundos");
          tmpT1 = System.currentTimeMillis();      
        log.debug("jcallo:  validacion de campañas limitadas en cantidad de uso");
        //validacion de campañas limitadas en cantidad de uso        
        //obteniendo el dni del cliente si se trata de una venta cliente fidelizado
        String dniClienteFidelizado = obtenerDniClienteFidelizado(VariablesCaja.vNumPedVta).trim();
        log.debug("jcallo :dniClienteFidelizado:"+dniClienteFidelizado);
        if(dniClienteFidelizado.length()>0){//quiere decir que es pedido de venta fidelizado
        	ArrayList listaCampLimitTerminados = new ArrayList();
        	ArrayList listaCampAutomaticasPedido = new ArrayList();
        	listaCampAutomaticasPedido = getCampAutomaticasPedido(VariablesCaja.vNumPedVta);//obtiene todas las campañas automaticas usados en el pedido
        	log.debug("jcallo :listaCampAutomaticasPedido:"+listaCampAutomaticasPedido);
                //DUBILLUZ - 01.12.2009
                //Correccion reportado por Jose.
        	/*
        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
            	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                }
                */
        	
        	/*if(VariablesCaja.vIndLinea.equals(FarmaConstants.INDICADOR_S)){//si hay linea con matriz
        		//traer todas las campañas limitadas que no deben aplicar para el cliente
        		listaCampLimitTerminados = CampLimitadasUsadosDeMatrizXCliente(dniClienteFidelizado);
        		log.debug("jcallo :listaCampLimitTerminados de MATRIZ:"+listaCampLimitTerminados);
        	}else{
        		listaCampLimitTerminados = CampLimitadasUsadosDeLocalXCliente(dniClienteFidelizado);
        		log.debug("jcallo :listaCampLimitTerminados de LOCAL:"+listaCampLimitTerminados);
        	}
                */
                //Valida localmente
                //14.04.2009 DUBILLUZ
                listaCampLimitTerminados = CampLimitadasUsadosDeLocalXCliente(dniClienteFidelizado);
                log.debug("jcallo :listaCampLimitTerminados de LOCAL:"+listaCampLimitTerminados);
        	
        	
        	boolean flag = false;
        	
        	for(int i = 0; i<listaCampLimitTerminados.size();i++){
        		String cod_camp = listaCampLimitTerminados.get(i).toString();
        		log.debug(" jcallo : cod_camp :"+cod_camp);
        		if( listaCampAutomaticasPedido.contains(cod_camp) ){
        			log.debug(" jcallo : cod_camp ENCONTRADO UNO QUE NO DEBERIA PODER USARSE:"+cod_camp);
        			flag = true;
        			break;
        		}
        	}
        	
        	if(flag){//quiere decir que encontro al menos una campaña que ya no deberia de aplicar anular el pedido
        		FarmaUtility.liberarTransaccion();
        		FarmaUtility.showMessage(this, "Error al cobrar pedido !\nEl descuento de la campaña ya fue usado por el cliente !", tblFormasPago);
                return;
        	}
        	
        }
          tmpT2 = System.currentTimeMillis();      
          log.debug("t4 Validaciondes de Fidelizados.Uso de campañas Automatica: "+(tmpT2 - tmpT1)+" milisegundos");        
        
        log.debug("jcallo:  fin de validacion de campañas limitadas en cantidad de uso");
          tmpT1 = System.currentTimeMillis();      
        VariablesCaja.mostrarValoresVariables();
        //fin de la validacion de campañas limitadas en cantidad de usos
        
        //verificar si es pedido por convenio
        String pIndPedConvenio = DBCaja.getIndPedConvenio(VariablesCaja.vNumPedVta);
        VariablesVentas.vEsPedidoConvenio = (pIndPedConvenio.equals("S")) ? true:false;
        
        log.debug("jcallo VariablesVentas.vEsPedidoConvenio:"+VariablesVentas.vEsPedidoConvenio);
        //fin verificar si es pedido por convenio

        //numero de liner por BOLETA
        if ( VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)){
        	if(VariablesVentas.vEsPedidoConvenio){
        		VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO = Integer.parseInt(
        				DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_BOLETA_CON_CONVENIO));
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
            }else{
                VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO = Integer.parseInt(
                		DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_BOLETA_SIN_CONVENIO));
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
            }
        } else if( VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) { //numero de linea por FACTURA
             if(VariablesVentas.vEsPedidoConvenio){
                 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_FACTURA_CON_CONVENIO));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
             }else{
                 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_FACTURA_SIN_CONVENIO));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
             }
        }else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) { //JCORTEZ 25.03.09 numero de linea por TICKET
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
        
        //muestra las secuencia de numero de comprobantes
        log.debug("VariablesCaja.vNumSecImpresionComprobantes : " + VariablesCaja.vNumSecImpresionComprobantes);
        //obtiene el monto del vuelto
        String vueltoPedido = lblVuelto.getText().trim();        
        colocaVueltoDetallePago(vueltoPedido);        
        log.debug("despues de colocar vuelto");
        //detalle de formas de pago
        VariablesCaja.vDescripcionDetalleFormasPago = "";
        VariablesCaja.vDescripcionDetalleFormasPago = ConstantsCaja.COLUMNAS_DETALLE_FORMA_PAGO;
        for (int i = 0; i < detaPago.size(); i++)
        {
            
                BeanDetaPago objDPB=(BeanDetaPago)detaPago.get(i);
            /*private String cod_fp; 0
            private String desc_fp; 1
            private String cant; 2
            private String moneda; 3
            private String monto; 4
            private String total; 5
            private String cod_moneda; 6
            private String comodin; 7
            private String nrotarj; 8
            private String fecventarj; 9
            private String nomclitarj; 10
            private String codconv; 11
            private String dnix; 12
            private String codvou; 13
            private String lote; 14*/
        	//grabar forma de pago del pedido
        	DBCaja.grabaFormaPagoPedidoNew(objDPB.getCod_fp(),
                                               objDPB.getMonto(),
                                               objDPB.getCod_moneda(),
                                               VariablesCaja.vValTipoCambioPedido,
                                               objDPB.getComodin(),
                                               objDPB.getTotal(),
                                               objDPB.getNrotarj(),
                                               objDPB.getFecventarj(),
                                               objDPB.getNomclitarj(),
                                               objDPB.getCant(),
                                               objDPB.getDnix(),
                                               objDPB.getCodvou(),
                                               objDPB.getLote());
        	
        	//descripcion de la forma de pago en el detalle
            System.out.println("OBJETO: "+objDPB.getCod_fp());            
        	VariablesCaja.vDescripcionDetalleFormasPago = VariablesCaja.vDescripcionDetalleFormasPago + 
											              objDPB.getCod_fp() + " , " + 
											              objDPB.getDesc_fp() + " , " + 
											              objDPB.getMoneda() + " , " + 
											              objDPB.getMonto() + " , " + 
											              objDPB.getTotal() + " , " + 
											              objDPB.getComodin() + "<BR>";
        }
        
        //obtiene la descrip de la formas de pago para la impresion
        log.debug("jcallo: antes de la descripcion de formas de pago impresion");
        formasPagoImpresion();
        log.debug("jcallo: fin de descripcion de formas de pago");
        //actualiza datos del cliente como nombre direccion ruc, etc
        if(!VariablesVentas.vCod_Cli_Local.equalsIgnoreCase("")) {
           log.debug("Actualizando datos del cliente en local ...");
           actualizaClientePedido(VariablesCaja.vNumPedVta,VariablesVentas.vCod_Cli_Local);
        }
        
        //cobrar pedido DEVOLVERA EXITO. si cobro correctamente
        //JCORTEZ 18.08.09 Se agrega DNI para guardar en cupone generados
        System.out.println("pdadwddddddddddddddddddd: "+VariablesVentas.vTip_Comp_Ped+","+VariablesCaja.vPermiteCampaña.trim()+","+dniClienteFidelizado.trim());
        String resultado = DBCaja.cobraPedido(VariablesVentas.vTip_Comp_Ped,VariablesCaja.vPermiteCampaña.trim(),dniClienteFidelizado.trim());
        log.debug(" verificando si el pedido es EXITOSO resultado Verifica: " + resultado);
        
        
          tmpT2 = System.currentTimeMillis();      
          log.debug("t5 Proceso de Cobro: "+(tmpT2 - tmpT1)+" milisegundos");        
        
        if ( resultado.trim().equalsIgnoreCase("EXITO") ) {
            
            tmpT1 = System.currentTimeMillis();      
            if (!UtilityCaja.validaAgrupacionComprobante(this,tblFormasPago)) {//el liberar transaccion esta dentro del metodo
            //FarmaUtility.liberarTransaccion();
                log.debug("error al agrupar comprobantes ... !!!!");
                VariablesCaja.vIndPedidoCobrado = false;
                return;
            }
            tmpT2 = System.currentTimeMillis();      
            log.debug("t6 Agrupa Comprobantes: "+(tmpT2 - tmpT1)+" milisegundos");        
            
            VariablesCaja.vIndPedidoCobrado = true;
            log.debug("jcallo:VariablesCaja.vIndPedidoConvenio=" + VariablesCaja.vIndPedidoConvenio + ", VariablesConvenio.vValCoPago=" +VariablesConvenio.vValCoPago);
            //si es pedido convenio y va usar credito de convenio
          if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) && 
              FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0) {
              tmpT1 = System.currentTimeMillis();       
        	  log.debug("jcallo: actualizar el monto del cliente. VariablesConvenio.vValCredDis=" + 
                            	  	VariablesConvenio.vValCredDis + " convenio=" + VariablesCaja.usoConvenioCredito);
        	  //uso convenio credito
        	  if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
        		  //validar credito del cliente si es que hay linea con matriz
                        if (VariablesCaja.vIndLinea.length() < 
                            1) { //quiere decir que no se validado aun el indicador de linea en matriz
                            VariablesCaja.vIndLinea = 
                                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                                   FarmaConstants.INDICADOR_S);
                        }
        	    
        		  if(VariablesCaja.vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                            String valor = 
                                DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, 
                                                            VariablesConvenio.vCodCliente, 
                                                            "" + 
                                                            VariablesConvenio.vValCredDis, 
                                                            FarmaConstants.INDICADOR_S);
                            log.debug("credito disponible que tendria despues del pedido : " + 
                                      valor);
                            double vValCredDisponible = 
                                FarmaUtility.getDecimalNumber(valor);
                            if (vValCredDisponible < 
                                0) { //quiere decir que no tiene saldo suficiente
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(this, 
                                                         "Cliente no tiene saldo suficiente.\nSe excede en S/." + 
                                                         vValCredDisponible + 
                                                         " soles !", 
                                                         tblFormasPago);
                                return;
                            } else { //quiere decir que tiene saldo suficiente
                                //actualiza consumo del cliente en matriz
                               /* JOptionPane.showMessageDialog(null,"VariablesConvenio.vCodConvenio: " + VariablesConvenio.vCodConvenio +"\n" +
                                                                   "VariablesConvenio.vCodCliente: " + VariablesConvenio.vCodCliente +"\n" +
                                                                   "VariablesConvenio.vValCredDis: " + VariablesConvenio.vValCredDis +"\n" +
                                                                   "FarmaConstants.INDICADOR_N: " + FarmaConstants.INDICADOR_N +"\n" +
                                                                   "VariablesCaja.vNumPedVta: " + VariablesCaja.vNumPedVta +"\n" +
                                                                   "FarmaVariables.vIdUsu: "+ FarmaVariables.vIdUsu);*/
                                DBConvenio.actualizaConsumoClienteEnMatriz(VariablesConvenio.vCodConvenio, 
                                                                           VariablesConvenio.vCodCliente, 
                                                                           "" + 
                                                                           VariablesConvenio.vValCredDis, 
                                                                           FarmaConstants.INDICADOR_N, 
                                                                           VariablesCaja.vNumPedVta, 
                                                                           FarmaVariables.vIdUsu);
                                //JMIRANDA 25/08/2009
                                //En el Metodo ActualizaConsumoClienteEnMatriz se inserta los Datos del consumo
                                //del convenio en la tabla CON_REG_VENTA


                                VariablesCaja.vIndCommitRemota = 
                                        true; //indica que debera hacer commit remotamente si todo el proceso es exitoso
                                //actualizar credito disponible del cliente en local*/
                                DBConvenio.actualizarCreditoDisp(VariablesConvenio.vCodConvenio, 
                                                                 VariablesConvenio.vCodCliente, 
                                                                 VariablesCaja.vNumPedVta, 
                                                                 vValCredDisponible);

                            }
        		  }else{//si no hay linea con matriz
        			  FarmaUtility.liberarTransaccion();
        			  log.error("jcallo:no hay conexion a matriz, para validar y actualizar pedido por convenio");
    				  FarmaUtility.showMessage(this, 
                              "Error: En este momento no hay linea con matriz.\nSi el problema persiste comunicarse con el operador de sistema !",
                              tblFormasPago);
    				  return;
        		  }
        	  }
              tmpT2 = System.currentTimeMillis();      
              log.debug("t7 Validaciones de convenio: "+(tmpT2 - tmpT1)+" milisegundos");        
          }
            tmpT1 = System.currentTimeMillis();      
          log.debug("jcallo: VariablesCaja.vIndPedidoConProdVirtual="+VariablesCaja.vIndPedidoConProdVirtual);
          //obtener flag de IND PARA SABER SI IMPRIMIRA ANTES DE LA RECARGA VIRTUAL
          indCommitBefore =  getIndCommitAntesRecargar();
            tmpT2 = System.currentTimeMillis();      
            log.debug("t8 Obtiene Indicador de Commit antes de Recargar: "+(tmpT2 - tmpT1)+" milisegundos");        
     
            if(dniClienteFidelizado.length()>0){//quiere decir que es pedido de venta fidelizado
                    tmpT1 = System.currentTimeMillis();      
                    boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada(
                                                                                            FarmaConstants.INDICADOR_N,//NO HAY LINEA
                                                                                            VariablesCaja.vNumPedVta,
                                                                                            this,
                                                                                            ConstantsCaja.ACCION_COBRO,
                                                                                            txtNroPedido,
                                                                                            FarmaConstants.INDICADOR_N
                                                                                           );
                    log.debug("jcallo: pRspCampanaAcumulad="+pRspCampanaAcumulad);
                    if (!pRspCampanaAcumulad){
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                   FarmaConstants.INDICADOR_S);
                            VariablesCaja.vIndPedidoCobrado = false;
                            FarmaUtility.showMessage(this, 
                                                    "El pedido no puede ser cobrado. \n" +
                                                    "Presenta un producto regalo de campaña que no se puede validar con Matriz. \n" +
                                                    "Inténtelo nuevamente de lo contrario anule el pedido y genérelo nuevamente." + 
                                                    "\n" +
                                                    "Gracias.",
                                                    tblFormasPago);
                            return; //se olvido dubilluz
                    }
            
            
            tmpT2 = System.currentTimeMillis();
            log.debug("t8 Obtiene Indicador de Commit antes de Recargar: "+(tmpT2 - tmpT1)+" milisegundos");
            
            }//FIN DE LOGICA DE CAMPABIAAS ACUMULADAS            
	          
          //flag de IND PARA SABER SI IMPRIMIRA ANTES DE LA RECARGA VIRTUAL
          if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
	        log.debug("indCommitBefore : S ");
	        log.debug("###VariablesCaja.vIndPedidoConProdVirtual  "+VariablesCaja.vIndPedidoConProdVirtual);
	        if (VariablesCaja.vIndPedidoConProdVirtual) {
	            tmpT1 = System.currentTimeMillis();     	
	            	//viendo si tiene indicador linea matriz 
	            	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
		    	     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
	            	}
	            	
	            if (VariablesCaja.vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
	                VariablesCaja.vIndEnvioRecargar = true;//indicador de que se mando a recargar
	                ejecutaRecargaVirtual();
	            } else {
	                FarmaUtility.liberarTransaccion();
	                VariablesCaja.vIndPedidoCobrado = false;
	                FarmaUtility.showMessage(this, 
	                                         "El pedido no puede ser cobrado. \n" +	
	                                         "No hay linea com matriz.\n" +
	                                         "Intentelo nuevamente.", 
	                                         tblFormasPago);
	                return; //se olvido dubilluz
	            }
	            tmpT2 = System.currentTimeMillis();      
	            log.debug("t9 Ejecuta la Recarga Virtual: "+(tmpT2 - tmpT1)+" milisegundos");
	        }
	        //evalua indicador de impresion por error
	        tmpT1 = System.currentTimeMillis();      	        
	        String vIndImpre = DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);
	        log.debug("vIndImpre :"+vIndImpre);
                tmpT2 = System.currentTimeMillis();      
                log.debug("t10 Obtiene Indicador de Impresion de Recarga: "+(tmpT2 - tmpT1)+" milisegundos");
                
                    if (!vIndImpre.equals("N")){
                    
                        if(VariablesCaja.listCuponesUsadosPedido.size() > 0){
                            log.debug("antes de actualizar los cupones en matriz");
                            //viendo si tiene indicador linea matriz 
            	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
            	        	tmpT1 = System.currentTimeMillis();      
            			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            	        	    tmpT2 = System.currentTimeMillis();      
            	        	    log.debug("t11 Obtiene IND_LINEA para actualizar cupones en Matriz: "+(tmpT2 - tmpT1)+" milisegundos");
            	        	}
                            /*
                            if(VariablesCaja.vIndLinea.equals(FarmaConstants.INDICADOR_S)){
                                tmpT1 = System.currentTimeMillis();      
                                actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta, 
                                                         VariablesCaja.listCuponesUsadosPedido,
                                                         VariablesCaja.vIndLinea);
                                tmpT2 = System.currentTimeMillis();      
                                log.debug("t12 Actualiza Cupones en Matriz: "+(tmpT2 - tmpT1)+" milisegundos");
                            }
                            else{
                                log.debug("No actualiza cupones en Matriz");
                            }*/
                            tmpT1 = System.currentTimeMillis();      
                            actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta, 
                                                     VariablesCaja.listCuponesUsadosPedido,
                                                     VariablesCaja.vIndLinea);
                            tmpT2 = System.currentTimeMillis();      
                            log.debug("t12 Actualiza Cupones en Matriz: "+(tmpT2 - tmpT1)+" milisegundos dubilluz 27.04.2010");
                        }
                        
                        /*** JCALLO ****/
                        if( dniClienteFidelizado.length() > 0 ){//quiere decir que es pedido fidelizado
                        	ArrayList listaCampAutomaticasPedido = new ArrayList();
                                tmpT1 = System.currentTimeMillis();      
                        	listaCampAutomaticasPedido = getCampAutomaticasPedido(VariablesCaja.vNumPedVta);//obtiene todas las campañas automaticas usados en el pedido
                        	tmpT2 = System.currentTimeMillis();                                      
                        	log.debug("t13 Lista campañas Acumuladas inscritas: "+(tmpT2 - tmpT1)+" milisegundos");
                                
                                tmpT1 = System.currentTimeMillis();                                      
                        	for(int i=0;i< listaCampAutomaticasPedido.size();i++){
                        		String cod_camp_limit = listaCampAutomaticasPedido.get(i).toString().trim();
                        		//CAMBIAR EL A POR L
                        		if(cod_camp_limit.indexOf("L")>-1){//quiere decir que es una campaña limitante
                        			log.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        			DBCaja.registrarUsoCampLimitLocal(cod_camp_limit, dniClienteFidelizado);
                        		}
                        	}
                                
                                tmpT2 = System.currentTimeMillis();                                      
                                log.debug("t14 Fin de proceso de Campañas Acumuladas: "+(tmpT2 - tmpT1)+" milisegundos");
                                
                        }
                        /*** FIN JCALLO ***/
                        
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
                        tmpFin = System.currentTimeMillis();
                        log.debug("t15 Fin PROCESO COBRO antes del Commit aún falta Imprimir: "+(tmpFin - tmpIni)+" milisegundos");                        
                        FarmaUtility.aceptarTransaccion();//haciendo commit en el pedido como cobrado
                        
                        log.debug("despues de supuestamente actualizar cupones en matriz");
                        log.info("VariablesCaja.vIndCommitRemota:"+VariablesCaja.vIndCommitRemota);
                        if(VariablesCaja.vIndCommitRemota){
                            log.debug("entroa  hacer commmit remota");
                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                            log.debug("despues de hacer commmit remota");
                        }
                        
                        if(VariablesCaja.vIndPedidoConProdVirtual) {
                            tmpT1 = System.currentTimeMillis();
                            log.debug("indicador de prodcutos virtual");
                            evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);//MUESTRA MENSAJE SI SE RECARGO O NO
                            tmpT2 = System.currentTimeMillis();
                            log.debug("t16 Evalua Mensaje de Recarga Virtual: "+(tmpT2 - tmpT1)+" milisegundos");
                        }
                        
                        log.debug("***VariablesCaja.vIndPedidoConProdVirtual***:"+VariablesCaja.vIndPedidoConProdVirtual);
                        
                        if(!VariablesCaja.vIndPedidoConProdVirtual){
                            log.debug("mostrando mensaje");
                            //FarmaUtility.showMessage(this, "Pedido Cobrado con Exito", txtNroPedido);
                            log.info("Pedido Cobrado con Exito");
                            log.debug("mostrando mensaje");
                        }
                        log.debug("...VariablesCaja.vIndPedidoConProdVirtual:"+VariablesCaja.vIndPedidoConProdVirtual);
                        /** DESPUES DEL MENSAJE DE COBRADO CON EXITO***/
		                //HASTA AQUI EL PEDIDO SE ENCUENTRA EN ESTADO S
		                tmpT1 = System.currentTimeMillis();
		                UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
		                //obtiene informacion del vendedor
		                UtilityCaja.obtieneInfoVendedor();
		                //proceso de impresion de comprobante del pedido
		                //JCALLO ...corregir todo este metodo, se agrego el indicador de linea con matriz
		                //pIndLineaMatriz
		                VariablesCaja.vIndLineaMatriz = VariablesCaja.vIndLinea;
                                tmpT2 = System.currentTimeMillis();
                                log.debug("t17 Obtiene informacion de cajero y de vendedor: "+(tmpT2 - tmpT1)+" milisegundos");
                                
                                
                                //tmpT1 = System.currentTimeMillis();
                                //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro
                                
                                try{
                                    DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                                    FarmaUtility.aceptarTransaccion();
                                    System.out.println("Grabo el tiempo de fin de cobro");
                                    
                                }
                                catch(SQLException sql){
                                    sql.printStackTrace();
                                    FarmaUtility.liberarTransaccion();
                                    System.out.println("Error al grabar el tiempo de fin de cobro");
                                }       
                                //JCHAVEZ 09.07.2009.en graba el tiempo de fin de cobro
		                UtilityCaja.procesoImpresionComprobante(this, txtNroPedido); 
                                
		                if(VariablesCaja.vIndPedidoConProdVirtual) {
		                	//evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);
		                	FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
		                }
		                
                    }else{//si el indicador de impresion es N
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this, "Error en Aplicacion al cobrar el Pedido.",tblFormasPago);
                    }
	       // }
          } else {//quiere decir que el indicador de IMPRIMIR ANTES DE RECARGAR ES DIFERENTE DE S
		if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                    log.debug("indCommitBefore : N ");
                    // Se mantiene la logica anterior , cobra realiza la recarga
                    // y solo si se obtuvo exito colocara el codigo de respuesta.
                    if (VariablesCaja.vIndPedidoConProdVirtual) {
                    	
                    	//viendo si tiene indicador linea matriz 
        	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
        			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
        	        	}
        	        	
                        if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            tmpT1 = System.currentTimeMillis();
                            ejecutaRecargaVirtual();
                            tmpT2 = System.currentTimeMillis();
                            log.debug("t18 Proceso de Recarga Virtual: "+(tmpT2 - tmpT1)+" milisegundos");                            
                        } else {
                            FarmaUtility.liberarTransaccion();
                            VariablesCaja.vIndPedidoCobrado = false;
                            FarmaUtility.showMessage(this,  "El pedido no puede ser cobrado. \n" +
                                                            "No hay linea com matriz.\n" +
                                                            "Intentelo nuevamente.", 
                                        		    tblFormasPago);
                        }
                    }                        
                    
                    
                	if(VariablesCaja.listCuponesUsadosPedido.size()>0){//solo si se uso algun cupon
                    	//viendo si tiene indicador linea matriz 
        	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
        			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
        	        	}
                	    tmpT1 = System.currentTimeMillis();
                        actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta,
                                                VariablesCaja.listCuponesUsadosPedido,
                                                VariablesCaja.vIndLinea);
                	    tmpT2 = System.currentTimeMillis();
                	    log.debug("t19 Actualiza Cupones en Matriz: "+(tmpT2 - tmpT1)+" milisegundos");                            
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
                    tmpFin = System.currentTimeMillis();
                    log.debug("t19 Fin de Proceso de Cobro antes de commit: "+(tmpFin - tmpIni)+" milisegundos");
                    FarmaUtility.aceptarTransaccion();
                    if(VariablesCaja.vIndCommitRemota)
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_N);
                
                    if(VariablesCaja.vIndPedidoConProdVirtual) {
                        tmpT1 = System.currentTimeMillis();
                        evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);
                        tmpT2 = System.currentTimeMillis();
                        log.debug("t20 Evalua Mensaje Virtual: "+(tmpT2 - tmpT1)+" milisegundos");
                    }
                
                    //FarmaUtility.showMessage(this, "Pedido Cobrado con Exito",txtNroPedido);
                    log.info("Pedido Cobrado con Exito");
                    
                    tmpT1 = System.currentTimeMillis();
                    UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                    UtilityCaja.obtieneInfoVendedor();
                    tmpT2 = System.currentTimeMillis();
                    log.debug("t21 Obtiene info. cajero y vendedor: "+(tmpT2 - tmpT1)+" milisegundos");
                    
                    
                    tmpT1 = System.currentTimeMillis();
                    //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro
                  
                    try{
                        DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                        FarmaUtility.aceptarTransaccion();
                        System.out.println("Grabo el tiempo de fin de cobro");
                        
                    }
                    catch(SQLException sql){
                        sql.printStackTrace();
                        FarmaUtility.liberarTransaccion();
                        System.out.println("Error al grabar el tiempo de fin de cobro");
                    }       
                    //JCHAVEZ 09.07.2009.en graba el tiempo de fin de cobro
                    UtilityCaja.procesoImpresionComprobante(this, txtNroPedido); 
                    tmpT2 = System.currentTimeMillis();
                    log.debug("t22 Proceso de Impresion de Comprobante: "+(tmpT2 - tmpT1)+" milisegundos");
                
                    if(VariablesCaja.vIndPedidoConProdVirtual)  {
                        FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
                    }
                    
                    
                        
                }
            } 
          //FIN DE QUE SE HAYA COBRADO EXITOSAMENTE
        } else if (resultado.trim().equalsIgnoreCase("ERROR")) {
          FarmaUtility.liberarTransaccion();
          VariablesCaja.vIndPedidoCobrado = false;
          FarmaUtility.showMessage(this, 
                                   "El pedido no puede ser cobrado. \n" +
                                   "Los totales de formas de pago y cabecera no coinciden. \n" +
                                   "Comuníquese con el Operador de Sistemas inmediatamente." + 
                                   ". \n" +
                                   "NO CIERRE LA VENTANA.", 
                                   tblFormasPago);
        }

      } catch (SQLException sql) {//error de base de datos al cobrar
        
    	FarmaUtility.liberarTransaccion();
        //sql.printStackTrace();
        log.error(null,sql);
        String pMensaje = sql.getMessage();
        
        int nIsSecCajaNull = pMensaje.indexOf("CHECK_SEC_MOV_CAJA");
        
        System.out.println("nIsSecCajaNull:"+nIsSecCajaNull);
        if(nIsSecCajaNull>0){
            FarmaUtility.showMessage(this, "No se pudo cobrar el pedido.\nInténtelo nuevamente", tblFormasPago);            
        }
        else{

            if ( VariablesCaja.vIndEnvioRecargar ){
                log.error("ERROR de BASE DE DATOS AL MOMENTO DE COBRAR UN RECARGAR VIRTUAL...PERO IGUAL SE MANDO A RECARGAR!");            
                try{
                    FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                                  FarmaVariables.vCodLocal,
                                                  //"jcallo",
                                                  VariablesPtoVenta.vDestEmailErrorCobro, //JMIRANDA 04/08/09
                                                  "Error Recarga Virtual, error de base datos",
                                                  "Error de Recarga Virtual",
                                                  "Error al realizar recarga virtual al numero : "+VariablesCaja.vNumeroCelular,
                                                  "IP PC: " + FarmaVariables.vIpPc + "<br>"+ //JMIRANDA 30/07/09
                                                  //"dubilluz"
                                                  "");
                }catch(Exception e){
                    log.error("ERROR AL TRATAR de enviar correo de alerta de recarga virtual");
                }
            }
            VariablesCaja.vIndPedidoCobrado = false;
            FarmaUtility.showMessage(this, "Error en BD al cobrar el Pedido.\n" + sql.getMessage(), tblFormasPago);
                        
        }
      } catch (Exception ex) {//error inesperado
    	  log.error(ex);//error inesperado
    	  if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
    		  if(VariablesCaja.vIndPedidoConProdVirtual) {
    			  //evalua indicador de impresion por error
    			  String vIndImpre="N";
    			  try {
    				  vIndImpre = DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);
    			  }catch(Exception e){
    				  log.error("jcallo: no pudo obtener el indicador de impresion de recarga");
    				  vIndImpre = "N";
    			  }
    			  log.debug("vIndImpre :"+vIndImpre);
    			  
    			  if (!vIndImpre.equals("N")) {
    				  log.error("imprimir si se trata de un producto virtual a pesar del error");
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
                              
    			      tmpFin = System.currentTimeMillis();
    			      log.debug("t23 Finaliza el Proceso de Cobro antes de Commit: "+(tmpFin - tmpIni)+" milisegundos");
                              
    				  FarmaUtility.aceptarTransaccion();
    				  if(VariablesCaja.vIndCommitRemota){
    					  FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
	                                                        FarmaConstants.INDICADOR_N);
    				  }
    			      tmpT1 = System.currentTimeMillis();
		              log.error("jcallo:obtiene informacion de cajero");
		              UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
		              log.error("jcallo:obtiene informacion del vendedor");
		              UtilityCaja.obtieneInfoVendedor();
		              log.error("jcallo:proceso de impresion de comprobante");
    			      tmpT2 = System.currentTimeMillis();
    			      log.debug("t24 Obtiene Info. de Vendedor y Cajero: "+(tmpT2 - tmpT1)+" milisegundos");
                              
    			      tmpT1 = System.currentTimeMillis();
    			      //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro
    			   
    			      try{
    			          DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
    			          FarmaUtility.aceptarTransaccion();
    			          System.out.println("Grabo el tiempo de fin de cobro");
    			         
    			      }
    			      catch(SQLException sql){
    			          sql.printStackTrace();
    			          FarmaUtility.liberarTransaccion();
    			          System.out.println("Error al grabar el tiempo de fin de cobro");
    			      }       
    			      //JCHAVEZ 09.07.2009.en graba el tiempo de fin de cobro
		              UtilityCaja.procesoImpresionComprobante(this, txtNroPedido);
    			      tmpT2 = System.currentTimeMillis();
    			      log.debug("t25 Finaliza Proceso de Impresion de Comprobantes: "+(tmpT2 - tmpT1)+" milisegundos");
                              
		              log.error("jcallo:fin proceso de impresion de comprobante");
		              log.error("FIN imprimir si se trata de un producto virtual a pesar del error");
		
			           
		              FarmaUtility.showMessage(this, 
		                                       "Error en Aplicacion al cobrar el Pedido.\n" + 
		                                       ex.getMessage(),
		                                       tblFormasPago);
		        
		              FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
    			  }else{
    				  FarmaUtility.liberarTransaccion();
    				  if(VariablesCaja.vIndCommitRemota){
    					  log.debug("jcallo: liberando transaccione remota");
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
    	 log.debug("VariablesCaja.vIndLinea"+VariablesCaja.vIndLinea);
         System.err.println("cerrando conexion remota sea matriz, adm, delivery, vta institucional, etc");
         FarmaConnectionRemoto.closeConnection();//dentro metodo solo cierra si estuvo abierta alguna conexion
         //verifica si existen pedidos pendientes de anulacion despues de N minutos
         tmpT1 = System.currentTimeMillis();
         UtilityCaja.verificaPedidosPendientes(this);
          tmpT2 = System.currentTimeMillis();
          log.debug("t26 Verifica Pedidos Pendientes Anular: "+(tmpT2 - tmpT1)+" milisegundos");
      }
      
      tmpFin = System.currentTimeMillis();
      log.debug("t27 Fin de Todo el Proceso de Cobro: "+(tmpFin - tmpIni)+" milisegundos");
      VariablesVentas.vProductoVirtual=false;//ASOSA 01.02.2010
      
      String esCredito="";      
        try{
            esCredito = DBConvenio.obtenerPorcentajeCopago(VariablesNewCobro.codconv);
        }catch(SQLException e){
            System.out.println("HOla ERORR en obtener ind credito");
            e.printStackTrace();
        }
        if(esCredito.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            UtilityCaja.imprimeDatoConvenio(this,VariablesCaja.vNumPedVta,
                                            VariablesConvenio.vCodConvenio,
                                            VariablesConvenio.vCodCliente);


        }
      cerrarVentana(false);
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
