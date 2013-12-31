package mifarma.ptoventa.recaudacion;

import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import javax.swing.JLabel;

import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.FrmEconoFar;
import mifarma.ptoventa.caja.DlgDatosTarjetaPinpad;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.FacadeCaja;
import mifarma.ptoventa.caja.reference.HiloProceso;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recaudacion.reference.VariablesRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgProcesarPagoTerceros extends JDialogProgress {
    
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarVentaCMR.class);
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    
    FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    
    private String strIndProc = "";
    
    //Pago
    private ArrayList<Object> tmpArrayCabRcd;
    private String strCodRecau = "";
    private FarmaTableModel tableModelDetallePago;
    private JLabel lblVuelto;
    private JTextField txtMontoPagadoDolares;
    private JTextField txtMontoPagado;
    
    //Anulacion
    private Frame myParentFrame;
    Long codTrsscAnulTemp = null;
    String numTarjeta; 
    String numTelefono; 
    String codSix; 
    String montoPagado; 
    String tipoRcdDesc; 
    String codRecauAnular; 
    String estTrsscSix; 
    String tipRcdCod; 
    String codMoneda; 
    String fechaRecauAnular; 
    String codAutorizRecauAnular;
    String fechaOrigen;
    
    //Consulta claro
    String terminal;
    String nroTelefono;
    String tipProdServ;
    ArrayList rptSix = null;
    private JLabel lblImagen = new JLabel();
    
    public DlgProcesarPagoTerceros() {
        super();
    }

    public DlgProcesarPagoTerceros(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            //jbInit();
  
        } catch (Exception e) {
            log.error("",e);
        }
    }
    


    private void jbInit() throws Exception {
        //this.setSize(new Dimension(238, 104));
        this.setSize(new Dimension(319, 133));
        this.getContentPane().setLayout( null );
        this.setTitle("Procesando Pago . . .");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter()
            {
              public void windowOpened(WindowEvent e)
              {
                this_windowOpened(e);
              }
            });        
        ImageIcon imagen = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/cargando.gif"));
        lblImagen = new JLabel(imagen);
        lblImagen.setBounds(10, 60, 50, 50);
        this.getContentPane().add(lblImagen, null);
        this.setResizable(false);
        FarmaUtility.centrarVentana(this);
        //this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    void this_windowOpened(WindowEvent e){ 
      log.debug("******************************************");
      log.debug("*********DlgProcesarPagoTerceros**********");
      log.debug("******************************************");
        HiloProceso hiloProceso = new HiloProceso(this);
        hiloProceso.setProcesando(true);
        hiloProceso.start();
    }
    
    public void realizarProcesos(){
        FarmaUtility.centrarVentana(this);
        if(ConstantsRecaudacion.RCD_IND_PROCESO_PAGO.equals(strIndProc)){
            procesarPago();
        }else if(ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION.equals(strIndProc)){
            procesarAnulacion();
        }else if(ConstantsRecaudacion.RCD_IND_PROCESO_CONSU_CLARO.equals(strIndProc)){
            this.setTitle("Consultando . . .");
            procesarConsultaClaro();
        }
    }
    
    private void cerrarVentana(boolean pAceptar){
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }    
    
   public void procesarPagoTerceros(ArrayList<Object> tmpArrayCabRcd, String strCodRecau, FarmaTableModel tableModelDetallePago, JLabel lblVuelto, 
                            JTextField txtMontoPagado, JTextField txtMontoPagadoDolares){
       this.tmpArrayCabRcd = tmpArrayCabRcd;
       this.strCodRecau = strCodRecau;
       this.tableModelDetallePago = tableModelDetallePago;
       this.lblVuelto = lblVuelto;
       this.txtMontoPagadoDolares = txtMontoPagadoDolares;
       this.txtMontoPagado = txtMontoPagado;
   }
    
    private void procesarPago(){
        Long codTrssc = null;
        String strTotalPagar = "";
        ArrayList rptSix = null;
        String estTrsscSix = "";
        String PCOD_AUTORIZACION_TEMP = "";
        String PFPA_NROTRACE_TEMP = "";
        boolean bRpt;
        boolean bMsj;
        String strResponseCode = "";
        String strMontoPagar = "";
        String strCodAutorizacion = tmpArrayCabRcd.get(21).toString();
        String strCodAuditoria = "";
        String strFechaOrigen = "";
        String strEstCta = tmpArrayCabRcd.get(7).toString();
        boolean bProcesarCobro = false;
        String descProceso = "";
        
        if( ConstantsRecaudacion.TIPO_REC_CMR.equals(tmpArrayCabRcd.get(4).toString()) ||
                             ConstantsRecaudacion.TIPO_REC_CLARO.equals(tmpArrayCabRcd.get(4).toString()) ||  
                                             ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(tmpArrayCabRcd.get(4).toString()) ){
                      
            //ERIOS 21.11.2013 Verifica conexion
            try{
                UtilityPtoVenta.verificaConexionMatriz(FarmaConstants.CONECTION_ADMCENTRAL);
            }catch(Exception e){
                FarmaUtility.showMessage(this,e.getMessage(),null);
                return;
            }
            
            if( ConstantsRecaudacion.TIPO_REC_CMR.equals(tmpArrayCabRcd.get(4).toString()) ){
                                              
                strTotalPagar = VariablesCaja.vValTotalPagar;
                String strNumComp = FarmaVariables.vCodLocal+strCodRecau;
                String nroCuotas = "1";  
                descProceso = "RPC";
                
                codTrssc = facadeRecaudacion.registrarTrsscPagoDeudaCMR(
                                                                        tmpArrayCabRcd.get(3).toString(), //numero de tarjeta
                                                                        strTotalPagar,// monto a pagar
                                                                        strCodRecau, //terminal: Identificamos el terminal con el numero de recaudacion
                                                                        FarmaVariables.vDescCortaLocal, // comercio
                                                                        FarmaVariables.vDescCortaDirLocal, //ubicacion
                                                                        nroCuotas, //TODO Uso futuro //cuotas
                                                                        VariablesCaja.vNumCaja,
                                                                        FarmaVariables.vNuSecUsu,
                                                                        FarmaVariables.vIdUsu,
                                                                        "",//tipo comprobante
                                                                        strNumComp//numero comprobante
                                                                      );
                //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                if(codTrssc == null){
                    FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                    return;
                }
                rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX,
                                                               ConstantsRecaudacion.RCD_PAGO_SIX_CMR,
                                                               codTrssc);  
                                                
            }else if( ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(tmpArrayCabRcd.get(4).toString()) ){
                //FarmaUtility.showMessage(this, "opcion no disponible." , null);
                strTotalPagar = VariablesCaja.vValTotalPagar;
                String strNumComp = FarmaVariables.vCodLocal+strCodRecau;
                String nroCuotas = "1";  
                descProceso = "RPR";
                
                codTrssc = facadeRecaudacion.registrarTrsscPagoDeudaRipley(
                                                                        tmpArrayCabRcd.get(3).toString(), //numero de tarjeta
                                                                        strTotalPagar,// monto a pagar
                                                                        strCodRecau, //terminal: Identificamos el terminal con el numero de recaudacion
                                                                        FarmaVariables.vDescCortaLocal, // comercio
                                                                        FarmaVariables.vDescCortaDirLocal, //ubicacion
                                                                        nroCuotas, //TODO Uso futuro //cuotas
                                                                        VariablesCaja.vNumCaja,
                                                                        FarmaVariables.vNuSecUsu,
                                                                        FarmaVariables.vIdUsu,
                                                                        "",//tipo comprobante
                                                                        strNumComp//numero comprobante
                                                                      );
                //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                if(codTrssc == null){
                    FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                    return;
                }
                rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX,
                                                               ConstantsRecaudacion.RCD_PAGO_SIX_RIPLEY,
                                                               codTrssc);  

            }else if ( ConstantsRecaudacion.TIPO_REC_CLARO.equals(tmpArrayCabRcd.get(4).toString() ) ){
                //ERIOS 23.10.2013 Se calcula el monto a abonar
                double dblTotalPagar = FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
                double dblMontoSoles = FarmaUtility.getDecimalNumber(txtMontoPagado.getText()) + 
                                       ( FarmaUtility.getDecimalNumber(txtMontoPagadoDolares.getText()) * 
                                          FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido) );
                if(dblTotalPagar <= dblMontoSoles){
                    strTotalPagar = FarmaUtility.formatNumber(dblTotalPagar);
                }else{
                    strTotalPagar = FarmaUtility.formatNumber(dblMontoSoles);
                }
                descProceso = "RPS";
                
                codTrssc = facadeRecaudacion.registrarTrsscPagoDeudaClaro(tmpArrayCabRcd.get(0).toString(), 
                                                                          tmpArrayCabRcd.get(1).toString(), 
                                                                          tmpArrayCabRcd.get(2).toString(),
                                                                          ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, 
                                                                          ConstantsRecaudacion.ESTADO_SIX_PENDIENTE, 
                                                                          ConstantsRecaudacion.TRNS_PAG_PRE_AUTORI_SRV, //tipo transaccion
                                                                          tmpArrayCabRcd.get(4).toString(), //tipo recaudacion
                                                                          strTotalPagar,  //monto
                                                                          strCodRecau, //terminal: Identificamos la transaccion con el numero de recaudacion 
                                                                          FarmaVariables.vDescCortaLocal, // comercio
                                                                          FarmaVariables.vDescCortaDirLocal, //ubicacion
                                                                          tmpArrayCabRcd.get(8).toString(), // telefono
                                                                          tmpArrayCabRcd.get(24).toString(), //tipo producto/servicio
                                                                          tmpArrayCabRcd.get(25).toString(), // número de recibo de pago
                                                                          FarmaVariables.vIdUsu 
                                                                          );  
                //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                if(codTrssc == null){
                    FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                    return;
                }
                rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, 
                                                               ConstantsRecaudacion.RCD_PAGO_SIX_CLARO, 
                                                               codTrssc );
            }
        
            bRpt = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
            bMsj = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
            strResponseCode = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
            strMontoPagar = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
            strCodAuditoria = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA); 
            strCodAutorizacion = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);// SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR 
            codTrssc = new Long (rptSix.get(7).toString());
            strFechaOrigen = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_FECHA_ORIG);            

            if(ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)){ 
                 estTrsscSix = ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_CORRECTA;
                 PCOD_AUTORIZACION_TEMP = strCodAutorizacion;
                 PFPA_NROTRACE_TEMP = UtilityRecaudacion.obtenerNroTraceConciliacion(codTrssc.toString()); 
                 bProcesarCobro = true;
            }else{ 
                estTrsscSix = ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_FALLIDA;
            }
            
        }else{//Recaudaciones que no pasan por el six
                
            PFPA_NROTRACE_TEMP = UtilityRecaudacion.obtenerNroTraceConciliacion(strCodRecau); 
            if ( ConstantsRecaudacion.TIPO_REC_CITI.equals(tmpArrayCabRcd.get(4).toString() ) ){
                strCodAutorizacion = FarmaVariables.vCodLocal+strCodRecau;
                PCOD_AUTORIZACION_TEMP = strCodAutorizacion;
                strTotalPagar = VariablesCaja.vValTotalPagar;
                descProceso = "RPT";                
            }else{
                PCOD_AUTORIZACION_TEMP = strCodAutorizacion; 
                descProceso = "RPP";
            } 
            bProcesarCobro = true;
        }
        
        
        if(bProcesarCobro){
            //Grabar forma de pago Recaudacion
            ArrayList tmpArray = new ArrayList();
            ArrayList tmpColm = null;
            ArrayList arrayFormasPago = new ArrayList();
            tmpArray = tableModelDetallePago.data;
            String vCodMoneda = "";
            double vTipoCambio = 0.00;
            String vMontoPagado = "0.00";
            String vTotalPagado = "0.00";
            for(int i = 0; i < tmpArray.size(); i++){
                //ERIOS 22.11.2013 Tipo cambio compra
                vCodMoneda = (((ArrayList)tmpArray.get(i)).get(6)).toString();
                vMontoPagado = (((ArrayList)tmpArray.get(i)).get(4)).toString();
                vTipoCambio = VariablesRecaudacion.vTipoCambioVenta;
                vTotalPagado = "0.00";
                if(strEstCta.equals(ConstantsRecaudacion.EST_CTA_DOLARES) && vCodMoneda.equals(ConstantsCaja.EFECTIVO_SOLES)){
                    vTipoCambio = VariablesRecaudacion.vTipoCambioCompra;                                        
                }
                
                if(vCodMoneda.equals(ConstantsCaja.EFECTIVO_SOLES)){
                    vTotalPagado = vMontoPagado;
                }else{                    
                    vTotalPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(vMontoPagado)*vTipoCambio);
                }                
                
                tmpColm = new ArrayList();
                tmpColm.add(FarmaVariables.vCodGrupoCia);
                tmpColm.add(FarmaVariables.vCodCia);
                tmpColm.add(FarmaVariables.vCodLocal);
                tmpColm.add(strCodRecau);
                tmpColm.add(((ArrayList)tmpArray.get(i)).get(0));
                tmpColm.add(((ArrayList)tmpArray.get(i)).get(4));//MONTO PAGADO    cImp_Total_in            
                tmpColm.add(vCodMoneda);//MONEDA                                
                tmpColm.add(vTipoCambio);
                tmpColm.add(((ArrayList)tmpArray.get(i)).get(7));//VUELTO
                tmpColm.add(vTotalPagado);//TOTAL PAGADO  cIm_Total_Pago_in (total soles)
                tmpColm.add(obtenerFecha());
                tmpColm.add(FarmaVariables.vIdUsu);
                tmpColm.add("");//12
                tmpColm.add("");//13
                arrayFormasPago.add(tmpColm);
            }
            
            boolean bCobro = facadeRecaudacion.grabaFormPagoRecau(arrayFormasPago,lblVuelto.getText().trim(), strCodAutorizacion, codTrssc,                                                               
                                                                ConstantsRecaudacion.TIPO_REC_RIPLEY.equals( tmpArrayCabRcd.get(4).toString() ) ||
                                                                  ConstantsRecaudacion.TIPO_REC_CMR.equals( tmpArrayCabRcd.get(4).toString() ) ||
                                                                    ConstantsRecaudacion.TIPO_REC_CLARO.equals( tmpArrayCabRcd.get(4).toString() )  ? estTrsscSix : "", strFechaOrigen );
            if(bCobro){
                //GFonseca 26.06.2013 Se agrega logica para actualizar el monto pagado, para el caso de prestamos citibank.
                String strMontoCobrado="";
                String strMontoMonedaCobrado="";
                if( ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(tmpArrayCabRcd.get(4).toString()) ||
                      ConstantsRecaudacion.TIPO_REC_CLARO.equals(tmpArrayCabRcd.get(4).toString()) ||
                         strEstCta.equals(ConstantsRecaudacion.EST_CTA_DOLARES)    ){
                    ArrayList<String> aMontoCobrado = facadeRecaudacion.actualizarMontoCobradoPresCiti(strCodRecau);               
                    strMontoCobrado = aMontoCobrado.get(0);
                    strMontoMonedaCobrado = aMontoCobrado.get(1);
                }
                
                //Variables para la conciliacion
                String strRptConciliacion = "";
                String PLOCAL = facadeRecaudacion.getCodLocalMigra();
                String PID_VENDEDOR = facadeRecaudacion.obtenerDniUsuario(FarmaVariables.vNuSecUsu).trim();
                String PFECHA_VENTA = tmpArrayCabRcd.get(17).toString().trim();
                
                String strMontoVenta = ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(tmpArrayCabRcd.get(4).toString()) ||
                                                ConstantsRecaudacion.TIPO_REC_CLARO.equals(tmpArrayCabRcd.get(4).toString()) ?
                                                                            strMontoCobrado : strTotalPagar;
               
                if(strEstCta.equals(ConstantsRecaudacion.EST_CTA_DOLARES)){
                    if(!ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(tmpArrayCabRcd.get(4).toString())){
                        strMontoVenta = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(strTotalPagar)*VariablesRecaudacion.vTipoCambioVenta);
                    }else{
                        strMontoVenta = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(strMontoMonedaCobrado)*VariablesRecaudacion.vTipoCambioVenta);                    
                    }                
                }
                
                double PMONTO_VENTA = FarmaUtility.getDecimalNumber(strMontoVenta); 
                
                double PNUM_CUOTAS = 0; //solo en venta
                String PCOD_AUTORIZACION = PCOD_AUTORIZACION_TEMP; 
                String PTRACK2 = ""; //solo en venta
                String PCOD_AUTORIZACION_PRE = ""; //solo en venta cuando es anulacion
                int PFPA_VALORXCUOTA = 0;  //solo en venta
                int PCAJA = Integer.parseInt(VariablesCaja.vNumCaja);
                String PTIPO_TRANSACCION = ConstantsRecaudacion.RCD_MODO_PAGO; //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion
                String PCODISERV = UtilityRecaudacion.obtenerCodigoServRecau(tmpArrayCabRcd.get(4).toString()); //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank                
                String PFPA_NUM_OBJ_PAGO = obtenerObjetoPago(); //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
                String PNOMBCLIE = "";
                long PVOUCHER = Long.parseLong(strCodRecau); //Nro de Comprobante
                long PNRO_COMP_ANU = 0; //Anulacion-Nro de Comprobante origen
                String PFECH_COMP_ANU = ""; //Anulacion-Fecha Origen del Comprobante  
                String PCODIAUTOORIG = ""; //Anulacion-Codigo autorizacion Origen
                double PFPA_TIPOCAMBIO = ConstantsRecaudacion.EST_CTA_DOLARES.equals(strEstCta) ? VariablesRecaudacion.vTipoCambioVenta : 0; //FarmaConstants.MONEDA_DOLARES.equals(tmpArrayCabRcd.get(9).toString()) ? "2" : "1";
                long PFPA_NROTRACE = Long.parseLong(PFPA_NROTRACE_TEMP); // Se envia 4 ultimos digitos de la transaccion registrada
                int PCOD_ALIANZA = Integer.parseInt(UtilityRecaudacion.obtenerCodAlianza(tmpArrayCabRcd.get(4).toString()));
                int PCOD_MONEDA_TRX = Integer.parseInt(FarmaConstants.MONEDA_SOLES.equals(tmpArrayCabRcd.get(9).toString()) ? 
                                                            ConstantsRecaudacion.RCD_COD_MONEDA_SOLES : 
                                                            ConstantsRecaudacion.RCD_COD_MONEDA_DOLARES);
                String PMON_ESTPAGO = FarmaConstants.MONEDA_SOLES.equals(tmpArrayCabRcd.get(9).toString()) ? ConstantsRecaudacion.RCD_COD_MONEDA_SOLES : 
                                                                                                             ConstantsRecaudacion.RCD_COD_MONEDA_DOLARES;
                //Imprimir            
                facadeRecaudacion.imprimirComprobantePagoRecaudacion(strCodRecau);
                
                //Abrir Gabeta
                UtilityCaja.abrirGabeta(myParentFrame, false);
                
                FarmaUtility.showMessage(this,ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_EXITO, null);
                
                //Grabar conciliacion
                strRptConciliacion = facadeRecaudacion.setDatosRecauConciliacion(   PLOCAL,
                                                                                PID_VENDEDOR, 
                                                                                PFECHA_VENTA, 
                                                                                PMONTO_VENTA,
                                                                                PNUM_CUOTAS,
                                                                                PCOD_AUTORIZACION, 
                                                                                PTRACK2,
                                                                                PCOD_AUTORIZACION_PRE,
                                                                                PFPA_VALORXCUOTA,                                                                         
                                                                                PCAJA,
                                                                                PTIPO_TRANSACCION,
                                                                                PCODISERV,
                                                                                PFPA_NUM_OBJ_PAGO,                                                                          
                                                                                PNOMBCLIE,                 
                                                                                PVOUCHER, 
                                                                                PNRO_COMP_ANU,
                                                                                PFECH_COMP_ANU,
                                                                                PCODIAUTOORIG,
                                                                                PFPA_TIPOCAMBIO,
                                                                                PFPA_NROTRACE, 
                                                                                PCOD_ALIANZA, 
                                                                                PCOD_MONEDA_TRX,
                                                                                PMON_ESTPAGO,
                                                                                descProceso);
                log.info("Conciliacion en recaudacion: "+strRptConciliacion); 
            }                
        }else{
            
            if ( ConstantsRecaudacion.TIPO_REC_CLARO.equals(tmpArrayCabRcd.get(4).toString()) ){
                if(ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)){
                    FarmaUtility.showMessage(this,ConstantsRecaudacion.RCD_MSJ_CLARO_SERV_INACTIVO,null); 
                }
            }else if ( ConstantsRecaudacion.TIPO_REC_CMR.equals(tmpArrayCabRcd.get(4).toString()) ){
                if(ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)){
                    FarmaUtility.showMessage(this,ConstantsRecaudacion.RCD_MSJ_CMR_SERV_INACTIVO,null); 
                }
            }
            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_FALLIDO , null);
        }
        cerrarVentana(true);                
    }
    
    public void procesarAnulacionTerceros(Frame myParentFrame, String numTarjeta, String numTelefono, String codSix, String montoPagado, 
                                                  String tipoRcdDesc, String codRecauAnular, String estTrsscSix, String tipRcdCod, String codMoneda, 
                                                            String fechaRecauAnular, String codAutorizRecauAnular, String fechaOrigen){
        this.myParentFrame = myParentFrame;
        this.numTarjeta = numTarjeta; 
        this.numTelefono = numTelefono; 
        this.codSix = codSix;
        this.montoPagado = montoPagado;
        this.tipoRcdDesc = tipoRcdDesc;
        this.codRecauAnular = codRecauAnular;
        this.estTrsscSix = estTrsscSix;
        this.tipRcdCod = tipRcdCod;
        this.codMoneda = codMoneda;
        this.fechaRecauAnular = fechaRecauAnular;
        this.codAutorizRecauAnular = codAutorizRecauAnular;
        this.fechaOrigen = fechaOrigen;
    }
    
    
    public void procesarAnulacion(){
                              
                String codRecauNegativo="";        
                
        if(FarmaConstants.MONEDA_DOLARES.equals(codMoneda)){
            montoPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(montoPagado)*VariablesRecaudacion.vTipoCambioVenta);
        }
        
                //VARIABLES PARA LA CONCILIACION
                String PLOCAL = facadeRecaudacion.getCodLocalMigra();
                String PID_VENDEDOR = facadeRecaudacion.obtenerDniUsuario(FarmaVariables.vNuSecUsu).trim();
                String PFECHA_VENTA = ConstantsRecaudacion.FECHA_RCD;
                double PMONTO_VENTA = FarmaUtility.getDecimalNumber(montoPagado);
                double PNUM_CUOTAS = 0; //solo en venta
                String PCOD_AUTORIZACION = ""; // En anulacion el SIX no retorna codigo de autorizacion
                String PTRACK2 = ""; //solo en venta
                String PCOD_AUTORIZACION_PRE = ""; //solo en venta cuando es anulacion ??
                int PFPA_VALORXCUOTA = 0;  //solo en venta
                int PCAJA = Integer.parseInt(VariablesCaja.vNumCaja);
                String PTIPO_TRANSACCION = ConstantsRecaudacion.RCD_MODO_ANULACION; //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion
                String PCODISERV = UtilityRecaudacion.obtenerCodigoServRecau(tipRcdCod); //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank                
                String PFPA_NUM_OBJ_PAGO = ""; //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
                String PNOMBCLIE = "";
                long PVOUCHER = 0; //Nro de Comprobante
                long PNRO_COMP_ANU = Long.parseLong(codRecauAnular); //Anulacion-Nro de Comprobante origen
                String PFECH_COMP_ANU = fechaRecauAnular; //Anulacion-Fecha Origen del Comprobante  
                String PCODIAUTOORIG = codAutorizRecauAnular; //Anulacion-Codigo autorizacion Origen
                double PFPA_TIPOCAMBIO = FarmaConstants.MONEDA_DOLARES.equals(codMoneda) ? VariablesRecaudacion.vTipoCambioVenta : 0;
                long PFPA_NROTRACE = 0; // Se envia 4 ultimos digitos de la transaccion registrada
                int PCOD_ALIANZA = Integer.parseInt(UtilityRecaudacion.obtenerCodAlianza(tipRcdCod));
                int PCOD_MONEDA_TRX = Integer.parseInt(FarmaConstants.MONEDA_SOLES.equals(codMoneda) ? 
                                                            ConstantsRecaudacion.RCD_COD_MONEDA_SOLES : 
                                                            ConstantsRecaudacion.RCD_COD_MONEDA_DOLARES);
                String PMON_ESTPAGO = FarmaConstants.MONEDA_SOLES.equals(codMoneda) ? ConstantsRecaudacion.RCD_COD_MONEDA_SOLES : 
                                                                                              ConstantsRecaudacion.RCD_COD_MONEDA_DOLARES;
                                
                String descProceso = "";                        
               if ( ConstantsRecaudacion.TIPO_REC_CMR.equals(tipRcdCod) ||  ConstantsRecaudacion.TIPO_REC_CLARO.equals(tipRcdCod) ||
                            ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(tipRcdCod)){               
                   //ERIOS 21.11.2013 Verifica conexion
                   try{
                       UtilityPtoVenta.verificaConexionMatriz(FarmaConstants.CONECTION_ADMCENTRAL);
                   }catch(Exception e){
                       FarmaUtility.showMessage(this,e.getMessage(),null);
                       return;
                   }
                   
                   Long codTrssc = null;
                   ArrayList rptSix = null;
                   boolean bRpt;
                   boolean bMsj;
                   String strResponseCode = "";
                   String strMontoPagar = "";
                   String strCodAutorizacionSix = "";
                   String strCodAuditoria = "";                         
                   String arrayDatosTrssc[] = new String[2]; 
                   String srtEstTrssc = "";
                   String tmpEst = facadeRecaudacion.obtenerEstadoTrssc(new Long(codSix), ConstantsRecaudacion.RCD_MODO_CONSULTA_SIX);
                   arrayDatosTrssc = tmpEst.split(",");
                   srtEstTrssc = arrayDatosTrssc[0].trim();//Estado OK / FA
                   
                   if(ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_CORRECTA.equals(srtEstTrssc)){
                       if (tipRcdCod.equals(ConstantsRecaudacion.TIPO_REC_CMR)){  
                           String numCuota="1";
                           String motivoExtorno="85";  
                           codTrssc = facadeRecaudacion.anularPagoTarjetaCMR(ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, 
                                                                             ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                             ConstantsRecaudacion.TRNS_ANU_PAG_TARJ, 
                                                                             ConstantsRecaudacion.TIPO_REC_CMR, 
                                                                             numTarjeta, 
                                                                             numCuota, 
                                                                             montoPagado, 
                                                                             VariablesCaja.vNumCaja,
                                                                             FarmaVariables.vNuSecUsu, 
                                                                             motivoExtorno, 
                                                                             codAutorizRecauAnular,                                                                
                                                                             codRecauAnular, 
                                                                             FarmaVariables.vDescCortaLocal, 
                                                                             FarmaVariables.vDescCortaDirLocal,
                                                                             FarmaVariables.vIdUsu 
                                                                             );   
                           //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                           if(codTrssc == null){
                               FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                               return;
                           }
                           rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, 
                                                                          ConstantsRecaudacion.RCD_ANU_PAGO_SIX_CMR, 
                                                                          codTrssc 
                                                                          );                           
                           bRpt = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                           bMsj = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                           strResponseCode = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                           strMontoPagar = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                           strCodAuditoria = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA); 
                           strCodAutorizacionSix = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);// SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR 
                           
                           if(bRpt && ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)){                              
                                //se genera el pedido negativo
                                codRecauNegativo = facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja, FarmaVariables.vIdUsu, codTrssc);
                                
                                //registrar conciliacion 
                                PVOUCHER = Long.parseLong(codRecauNegativo);
                                PCOD_AUTORIZACION = FarmaVariables.vCodLocal+codRecauNegativo;  //strCodAutorizacionSix;                                 
                                PFPA_NUM_OBJ_PAGO = numTarjeta; //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
                                PFPA_NROTRACE = Long.parseLong(UtilityRecaudacion.obtenerNroTraceConciliacion(codTrssc.toString()));
                                descProceso = "ARC";
                                String vRpt = facadeRecaudacion.setDatosRecauConciliacion(  PLOCAL,
                                                                                            PID_VENDEDOR, 
                                                                                            PFECHA_VENTA, 
                                                                                            PMONTO_VENTA,
                                                                                            PNUM_CUOTAS,
                                                                                            PCOD_AUTORIZACION, 
                                                                                            PTRACK2,
                                                                                            PCOD_AUTORIZACION_PRE,
                                                                                            PFPA_VALORXCUOTA,                                                                         
                                                                                            PCAJA,
                                                                                            PTIPO_TRANSACCION,
                                                                                            PCODISERV,
                                                                                            PFPA_NUM_OBJ_PAGO,                                                                          
                                                                                            PNOMBCLIE,                 
                                                                                            PVOUCHER, 
                                                                                            PNRO_COMP_ANU,
                                                                                            PFECH_COMP_ANU,
                                                                                            PCODIAUTOORIG,
                                                                                            PFPA_TIPOCAMBIO,
                                                                                            PFPA_NROTRACE, 
                                                                                            PCOD_ALIANZA, 
                                                                                            PCOD_MONEDA_TRX,
                                                                                            PMON_ESTPAGO,
                                                                                            descProceso
                                                                                          );   
                               //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion 
                               if(!codRecauNegativo.equals("")){
                                  facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
                               } 
                               
                               //Abrir Gabeta
                               UtilityCaja.abrirGabeta(myParentFrame, false);
                               
                               FarmaUtility.showMessage(this,"La recaudación fue anulada.",null);

                           }else{
                                if(ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)){
                                     FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);  
                                }else if(ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)){
                                     FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CMR_SERV_INACTIVO, null); 
                                }else{
                                     FarmaUtility.showMessage(this,"La recaudación no se pudo anular.",null); 
                                }                                
                            }
                       }//fin CMR
                       else if (tipRcdCod.equals(ConstantsRecaudacion.TIPO_REC_CLARO)){
                           codTrssc = facadeRecaudacion.anularPagoServicioCLARO(   
                                                                                codSix,                                  
                                                                                FarmaVariables.vCodGrupoCia, 
                                                                                FarmaVariables.vCodCia, 
                                                                                FarmaVariables.vCodLocal,
                                                                                ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200,
                                                                                ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                                ConstantsRecaudacion.TRNS_ANU_PAG_SRV,
                                                                                ConstantsRecaudacion.TIPO_REC_CLARO,
                                                                                montoPagado,
                                                                                codRecauAnular,
                                                                                FarmaVariables.vDescCortaLocal, //comercio
                                                                                FarmaVariables.vDescCortaDirLocal,
                                                                                FarmaVariables.vNuSecUsu,
                                                                                FarmaVariables.vIdUsu
                                                                                );
                           //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                           if(codTrssc == null){
                               FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                               return;
                           }
                           rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX,
                                                                          ConstantsRecaudacion.RCD_ANU_PAGO_SIX_CLARO,
                                                                          codTrssc
                                                                          );

                           bRpt = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                           bMsj = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                           strResponseCode = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                           strMontoPagar = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                           strCodAuditoria = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA); 
                           strCodAutorizacionSix = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);// SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR 
                           
                           if( ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode) ){                              
                                //se genera el pedido negativo
                                codRecauNegativo = facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja, FarmaVariables.vIdUsu, codTrssc);
                                
                                //registrar conciliacion 
                                PVOUCHER = Long.parseLong(codRecauNegativo);
                                PCOD_AUTORIZACION = FarmaVariables.vCodLocal+codRecauNegativo;  //strCodAutorizacionSix;                                 
                                PFPA_NUM_OBJ_PAGO = numTelefono; //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
                                PFPA_NROTRACE = Long.parseLong(UtilityRecaudacion.obtenerNroTraceConciliacion(codTrssc.toString()));
                                descProceso = "ARS";
                                String vRpt = facadeRecaudacion.setDatosRecauConciliacion(  PLOCAL,
                                                                                            PID_VENDEDOR, 
                                                                                            PFECHA_VENTA, 
                                                                                            PMONTO_VENTA,
                                                                                            PNUM_CUOTAS,
                                                                                            PCOD_AUTORIZACION, 
                                                                                            PTRACK2,
                                                                                            PCOD_AUTORIZACION_PRE,
                                                                                            PFPA_VALORXCUOTA,                                                                         
                                                                                            PCAJA,
                                                                                            PTIPO_TRANSACCION,
                                                                                            PCODISERV,
                                                                                            PFPA_NUM_OBJ_PAGO,                                                                          
                                                                                            PNOMBCLIE,                 
                                                                                            PVOUCHER, 
                                                                                            PNRO_COMP_ANU,
                                                                                            PFECH_COMP_ANU,
                                                                                            PCODIAUTOORIG,
                                                                                            PFPA_TIPOCAMBIO,
                                                                                            PFPA_NROTRACE, 
                                                                                            PCOD_ALIANZA, 
                                                                                            PCOD_MONEDA_TRX,
                                                                                            PMON_ESTPAGO,
                                                                                            descProceso
                                                                                          );
                               //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion 
                               if(!codRecauNegativo.equals("")){
                                  facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
                               }
                               FarmaUtility.showMessage(this,"La recaudación fue anulada.",null);
                           }else{
                               if(ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)){
                                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);  
                               }else if(ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)){
                                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CLARO_SERV_INACTIVO, null); 
                               }else{
                                    FarmaUtility.showMessage(this,"La recaudación no se pudo anular.",null); 
                               }                                
                            }                          
                       }//fin claro
                       else if (tipRcdCod.equals(ConstantsRecaudacion.TIPO_REC_RIPLEY)){  
                           String numCuota="1";
                           //String motivoExtorno="85";  
                           codTrssc = facadeRecaudacion.anularPagoTarjetaRipley( codSix,
                                                                                 numTarjeta, 
                                                                                 numCuota, 
                                                                                 montoPagado,                                                                             
                                                                                 //motivoExtorno, 
                                                                                 codAutorizRecauAnular,
                                                                                 fechaOrigen,
                                                                                 codRecauAnular
                                                                                 );      
                           //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                           if(codTrssc == null){
                               FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                               return;
                           }
                           rptSix = facadeRecaudacion.obtenerRespuestaSix( ConstantsRecaudacion.RCD_MODO_PAGO_SIX, 
                                                                           ConstantsRecaudacion.RCD_ANU_PAGO_SIX_CMR, 
                                                                           codTrssc 
                                                                          );                           
                           bRpt = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                           bMsj = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                           strResponseCode = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                           strMontoPagar = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                           strCodAuditoria = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA); 
                           strCodAutorizacionSix = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);// SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR 
                                                      
                           if(bRpt && ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)){                              
                                //se genera el pedido negativo
                                codRecauNegativo = facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja, FarmaVariables.vIdUsu, codTrssc);
                                
                                //registrar conciliacion 
                                PVOUCHER = Long.parseLong(codRecauNegativo);
                                PCOD_AUTORIZACION = FarmaVariables.vCodLocal+codRecauNegativo;  //strCodAutorizacionSix;                                 
                                PFPA_NUM_OBJ_PAGO = numTarjeta; //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
                                PFPA_NROTRACE = Long.parseLong(UtilityRecaudacion.obtenerNroTraceConciliacion(codTrssc.toString()));
                                descProceso = "ARR";
                                String vRpt = facadeRecaudacion.setDatosRecauConciliacion(  PLOCAL,
                                                                                            PID_VENDEDOR, 
                                                                                            PFECHA_VENTA, 
                                                                                            PMONTO_VENTA,
                                                                                            PNUM_CUOTAS,
                                                                                            PCOD_AUTORIZACION, 
                                                                                            PTRACK2,
                                                                                            PCOD_AUTORIZACION_PRE,
                                                                                            PFPA_VALORXCUOTA,                                                                         
                                                                                            PCAJA,
                                                                                            PTIPO_TRANSACCION,
                                                                                            PCODISERV,
                                                                                            PFPA_NUM_OBJ_PAGO,                                                                          
                                                                                            PNOMBCLIE,                 
                                                                                            PVOUCHER, 
                                                                                            PNRO_COMP_ANU,
                                                                                            PFECH_COMP_ANU,
                                                                                            PCODIAUTOORIG,
                                                                                            PFPA_TIPOCAMBIO,
                                                                                            PFPA_NROTRACE, 
                                                                                            PCOD_ALIANZA, 
                                                                                            PCOD_MONEDA_TRX,
                                                                                            PMON_ESTPAGO,
                                                                                            descProceso
                                                                                          );
                               //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion 
                               if(!codRecauNegativo.equals("")){
                                  facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
                               }                                
                               FarmaUtility.showMessage(this,"La recaudación fue anulada.",null);
                           }else{
                                if(ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)){
                                     FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);  
                                }else if(ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)){
                                     FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_RIPLEY_SERV_INACTIVO, null); 
                                }else{
                                     FarmaUtility.showMessage(this,"La recaudación no se pudo anular.",null); 
                                }                                
                            }
                       }//fin Ripley
                   }else{ 
                       FarmaUtility.showMessage(this,"No se pudo anular la recaudación, intente nuevamente.",null);
                   }
               
               }else{//Recaudaciones que no pasan por el six
                    
                    //se genera el pedido negativo
                    codRecauNegativo = facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja, FarmaVariables.vIdUsu, codTrsscAnulTemp);                   
                    //registrar conciliacion 
                    PVOUCHER = Long.parseLong(codRecauNegativo);
                    PCOD_AUTORIZACION = FarmaVariables.vCodLocal+codRecauNegativo; 
                    if(  ConstantsRecaudacion.TIPO_REC_CITI.equals(tipRcdCod) ){
                        PFPA_NUM_OBJ_PAGO = numTarjeta;
                        descProceso = "ART";
                    }else if( ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(tipRcdCod) ){
                        PFPA_NUM_OBJ_PAGO = numTelefono;
                        descProceso = "ARP";
                    }      
                    PFPA_NROTRACE = Long.parseLong(UtilityRecaudacion.obtenerNroTraceConciliacion(codRecauNegativo)); // se envian los 4 ultimos digitos de la recaudacion
                   
                    String strRptConciliacion = facadeRecaudacion.setDatosRecauConciliacion(   PLOCAL,
                                                                                 PID_VENDEDOR, 
                                                                                 PFECHA_VENTA, 
                                                                                 PMONTO_VENTA,
                                                                                 PNUM_CUOTAS,
                                                                                 PCOD_AUTORIZACION, 
                                                                                 PTRACK2,
                                                                                 PCOD_AUTORIZACION_PRE,
                                                                                 PFPA_VALORXCUOTA,                                                                         
                                                                                 PCAJA,
                                                                                 PTIPO_TRANSACCION,
                                                                                 PCODISERV,
                                                                                 PFPA_NUM_OBJ_PAGO,                                                                          
                                                                                 PNOMBCLIE,                 
                                                                                 PVOUCHER, 
                                                                                 PNRO_COMP_ANU,
                                                                                 PFECH_COMP_ANU,
                                                                                 PCODIAUTOORIG,
                                                                                 PFPA_TIPOCAMBIO,
                                                                                 PFPA_NROTRACE, 
                                                                                 PCOD_ALIANZA, 
                                                                                 PCOD_MONEDA_TRX,
                                                                                 PMON_ESTPAGO,
                                                                                 descProceso
                                                                              );     
                    //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion 
                    if(!codRecauNegativo.equals("")){
                       facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
                    }
                    FarmaUtility.showMessage(this,"La recaudación fue anulada.",null);             
                } 
                        
        cerrarVentana(true);
    }
    
    
    public void procesarConsultaClaro( String terminal, String nroTelefono, String tipProdServ ){
        this.terminal = terminal;
        this.nroTelefono = nroTelefono;
        this.tipProdServ = tipProdServ;    
    }
    
    
    public void procesarConsultaClaro(){
        Long codTrssc = null;       
        
        //ERIOS 21.11.2013 Verifica conexion
        try{
            UtilityPtoVenta.verificaConexionMatriz(FarmaConstants.CONECTION_ADMCENTRAL);
        }catch(Exception e){
            FarmaUtility.showMessage(this,e.getMessage(),null);
            return;
        }
        
        codTrssc = facadeRecaudacion.registrarTrsscConsultaDeudaClaro( terminal,                                                                      
                                                                       nroTelefono,
                                                                       tipProdServ );
        //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con la consulta
        if(codTrssc == null){
            FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
            return;
        }
        setRptSix(facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_CONSULTA_SIX,
                                                 ConstantsRecaudacion.RCD_CONSULTA_PAGO_SIX_CLARO,
                                                 codTrssc ));
        cerrarVentana(true);
    }


    
    public String obtenerFecha(){
        String fechaSys = "";
        try {
            fechaSys = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener la fecha y hora. \n " + 
                                     sql.getMessage(), null);
        }
        return fechaSys;
    }
    
    public String obtenerObjetoPago(){
          String codObjePago = "";            
          if( ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(tmpArrayCabRcd.get(4).toString()) ||
                    ConstantsRecaudacion.TIPO_REC_CMR.equals(tmpArrayCabRcd.get(4).toString()) ||
                    ConstantsRecaudacion.TIPO_REC_CITI.equals(tmpArrayCabRcd.get(4).toString()) ){
                codObjePago = tmpArrayCabRcd.get(3).toString(); //nro tarjeta
          }else if( ConstantsRecaudacion.TIPO_REC_CLARO.equals(tmpArrayCabRcd.get(4).toString()) ||
                    ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(tmpArrayCabRcd.get(4).toString()) ){
                codObjePago = tmpArrayCabRcd.get(8).toString(); //codigo de recibo o de cliente
          }        
          return codObjePago;
    }


    public String getStrIndProc() {
        return strIndProc;
    }

    public void setStrIndProc(String strIndProc) {
        this.strIndProc = strIndProc;
    }

    public ArrayList getRptSix() {
        return rptSix;
    }

    public void setRptSix(ArrayList rptSix) {
        this.rptSix = rptSix;
    }

    @Override
    public void ejecutaProceso() {
        realizarProcesos();        
    }
}
