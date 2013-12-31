package mifarma.ptoventa.caja;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;

import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.FacadeCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.pinpad.DlgAnularTransPinpad;
import mifarma.ptoventa.pinpad.reference.DBPinpad;
import mifarma.ptoventa.recaudacion.DlgProcesarVentaCMR;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgProcesarProductoVirtual extends JDialogProgress {
    
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarProductoVirtual.class);
    
    private FacadeCaja facadeCaja = new FacadeCaja();
    private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    private FacadeRecetario facadeRecetario = new FacadeRecetario();
    
    private Frame myParentFrame;
    private JTable tblFormasPago;
    private JTextField txtNroPedido;
    private boolean vProcesoRecarga;
    
    public DlgProcesarProductoVirtual(Frame frame, String string, boolean b) {
        super(frame, string, b);
        myParentFrame = frame;
    }

    public DlgProcesarProductoVirtual() {
        super();
    }

    @Override
    public void ejecutaProceso() {
        //ERIOS 04.11.2013 Procesa anulacion automatica de recarga virtual
        vProcesoRecarga = true;
        
        //1. Procesa pedido virtual
        if (VariablesCaja.vIndPedidoConProdVirtual)
        {   //viendo si tiene indicador linea matriz 
            if (VariablesCaja.vIndLinea != null &&
                VariablesCaja.vIndLinea.length()<1)
            {   
                //quiere decir que no se validado aun el indicador de linea en matriz
                VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            }
                    
            if(VariablesCaja.vIndLinea != null &&
                FarmaConstants.INDICADOR_S.equalsIgnoreCase(VariablesCaja.vIndLinea.trim()))
            {
                try
                {   ejecutaRecargaVirtual();
                    evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);
                }
                catch (Exception e)
                {
                    FarmaUtility.liberarTransaccion();
                    //VariablesCaja.vIndPedidoCobrado = false;
                    FarmaUtility.showMessage(this,
                                             e.getMessage(), 
                                             tblFormasPago);
                    //ERIOS 19.11.2013 Se comenta por si algun dia cambian el procedimiento
                    DlgMsjeErrorPedidoProdVirtual dlg = new DlgMsjeErrorPedidoProdVirtual(myParentFrame,"",true);
                    dlg.setVisible(true);
                    
                    boolean flagAnulTrans = false;
                    //si se pago con tarjeta Pinpad, anular la transacción
                    if(VariablesCaja.vIndDatosTarjeta)
                    {   
                        //LLEIVA 02/Dic/2013 hay q tener cuidado con las excepciones de Recetario Magistral
                        
                        HashMap<String,String> resultado = new HashMap<String,String>();
                        DBPinpad.consFormaPagoPedido2(VariablesCaja.vNumPedVta, resultado);
                        
                        //anulacion visa
                        if(ConstantsPtoVenta.FORPAG_VISA_PINPAD.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED")) ||
                            ConstantsPtoVenta.FORPAG_MC_PINPAD.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED")) || 
                            ConstantsPtoVenta.FORPAG_DINERS.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED")) || 
                            ConstantsPtoVenta.FORPAG_AMEX.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED")))
                        {   
                            //si posee transaccion VISA o MC, abrir ventana
                            FarmaVariables.vAceptar = false;
                            while(!FarmaVariables.vAceptar)
                            {
                                FarmaUtility.showMessage(this, 
                                                        "Es obligatoria la anulación de la transacción debido a que no se pudo realizar la recarga virtual",
                                                        null);
                                
                                DlgAnularTransPinpad dlgAnularTransPinpad = new DlgAnularTransPinpad(myParentFrame,"",true);
                                dlgAnularTransPinpad.setValores(VariablesCaja.vNumPedVta, 
                                                                VariablesCaja.vValMontoPagadoTarj, 
                                                                resultado.get("FECHA_TRANS"), 
                                                                resultado.get("NUM_REF_TRANS"),
                                                                resultado.get("FORMA_PAGO_PED"),
                                                                true);
                                dlgAnularTransPinpad.setVisible(true);
                                //se recoge el resultado de la anulacion de la transaccion
                            } 
                            //vProcesoRecarga = true;
                            //return;
                        }
                        //VariablesCaja.vCodVoucher
                        //pasar flag de indicador para que no se cierre la ventana hasta q no se anule la transaccion
                        //se setea flagAnulTrans como true
                        
                        
                        //Si se paga con tarjeta CMR, anular la transacción
                        //if(boolean)
                        //{ //se obtienen datos de la transacción
                        //  //pasar flag de indicador para que no se cierre la ventana hasta q no se anule la transaccion
                        //  //se setea flagAnulTrans como true
                        //}
                        
                        //GFONSECA 03/12/2013 Si se paga con tarjeta CMR, anular la transacción
                        if(VariablesCaja.vIndDatosTarjetaCMR){
                            //Anulacion de venta CMR con el six, retorna true si anula correctamente.
                            ArrayList arrayTmpDatosRCD = facadeRecaudacion.getDatosAnulacionVentaCMR(VariablesCaja.vNumPedVta);
                            //Si el pedido existe en la tabla de cabecera de recaudacion se realiza el proceso de anulacion con el six
                            if(arrayTmpDatosRCD != null && arrayTmpDatosRCD.size() > 0){            
                                    DlgProcesarVentaCMR dlgProcesarVentaCMR = null;                                                    
                                    dlgProcesarVentaCMR = new DlgProcesarVentaCMR(myParentFrame,"",true);            
                                    dlgProcesarVentaCMR.procesarAnulacionVentaCMR(myParentFrame, arrayTmpDatosRCD);
                                    dlgProcesarVentaCMR.setStrIndProc(ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION);                            
                                    dlgProcesarVentaCMR.mostrar();
                            }
                            VariablesCaja.vIndDatosTarjetaCMR = false;
                            /*if( !dlgProcesarVentaCMR.isBRptTrsscAnul()){
                              return;//Si el proceso de anulacion con el Six falla, no tiene que seguir la rutina de anulacion.
                            }*/                                                 
                        }
                    }
                    vProcesoRecarga = false;
                }
            }
            else
            {   FarmaUtility.liberarTransaccion();
                //VariablesCaja.vIndPedidoCobrado = false;
                FarmaUtility.showMessage(this,  "El pedido no puede ser cobrado. \n" +
                                                "No hay linea con matriz.\n" +
                                                "Inténtelo nuevamente.", 
                                                tblFormasPago);
                vProcesoRecarga = false;
            }
        }
    }
    
    private void ejecutaRecargaVirtual() throws Exception
    {
        procesaPedidoVirtual();
    }
    

    private void evaluaMsjVentaVirtualGenerado(String pTipoProdVirtual)
    {
        if(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA.equalsIgnoreCase(pTipoProdVirtual))
            muestraTarjetaVirtualGenerado();
        else if (ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA.equalsIgnoreCase(pTipoProdVirtual))
            FarmaUtility.showMessage(this, 
                                    "La recarga automática se realizó satisfactoriamente.", 
                                    null);
    }
    
    private void procesaPedidoVirtual() throws Exception
    {   //ERIOS 30.05.2013 Envia el pedido de preparado hacia el sistema Recetario Magistral
        //ERIOS 16.07.2013 Implementacion de recargas FarmaSix
        obtieneInfoPedidoVirtual();
        if (VariablesVirtual.vArrayList_InfoProdVirtual != null &&
            VariablesVirtual.vArrayList_InfoProdVirtual.size() != 1)
        {   
            throw new Exception("Error al validar info del pedido virtual");
        }
        colocaInfoPedidoVirtual();
        if(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA.equalsIgnoreCase(VariablesCaja.vTipoProdVirtual) ||
            ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA.equalsIgnoreCase(VariablesCaja.vTipoProdVirtual))
        {   
            if(FarmaConstants.INDICADOR_S.equalsIgnoreCase(VariablesPtoVenta.vIndFarmaSix))
            {             
                //ERIOS 21.11.2013 Verifica conexion
                UtilityPtoVenta.verificaConexionMatriz(FarmaConstants.CONECTION_ADMCENTRAL);
                                      
                //GFonseca 14/08/2013. Se añade logica para realizar recargas virtuales con el SIX
                String codProd = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 0);
                String monto = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 2);
                String telefono = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 3);    
                String terminal = VariablesCaja.vNumPedVta.substring(2);
                String comercio = ConstantsRecaudacion.ID_RECARGAS+""+facadeRecaudacion.getCodLocalMigra().substring(1);//FarmaVariables.vDescCortaLocal;
                String ubicacion = FarmaVariables.vDescCortaDirLocal;            
                ArrayList rptSix = null;
                Long codTrssc = null;            
                boolean bRpt;
                boolean bMsj;
                String strResponseCode = "";
                String strCodAutorizacion = "";
                String strConcentrador="";
                
                if(ConstantsVentas.TARJ_RECARGA_MOVISTAR_VIRTUAL.equals(codProd))
                {        
                    codTrssc = facadeCaja.registrarTrsscRecarga(ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, 
                                                                ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                ConstantsRecaudacion.TRNS_RECARGA, 
                                                                ConstantsRecaudacion.TIPO_REC_MOVISTAR, 
                                                                monto, 
                                                                terminal, 
                                                                comercio, 
                                                                ubicacion, 
                                                                telefono, 
                                                                VariablesCaja.vNumPedVta,
                                                                FarmaVariables.vIdUsu); 
                    //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                    if(codTrssc == null){
                        FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                        throw new Exception("Ocurrio un error al registrar la transacción.");
                        //return;
                    }
                    rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_RECARGA_SIX, 
                                                                    ConstantsRecaudacion.RCD_PAGO_SIX_RECARGA_VIRTUAL_MOVISTAR, 
                                                                    codTrssc );
                    strConcentrador = ConstantsRecaudacion.COD_CONCENTRADOR_MOVISTAR;        
                }
                else if (ConstantsVentas.TARJ_RECARGA_CLARO_VIRTUAL.equals(codProd))
                {
                    codTrssc = facadeCaja.registrarTrsscRecarga(ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, 
                                                                ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                ConstantsRecaudacion.TRNS_RECARGA, 
                                                                ConstantsRecaudacion.TIPO_REC_CLARO, 
                                                                monto, 
                                                                terminal, 
                                                                comercio, 
                                                                ubicacion, 
                                                                telefono, 
                                                                VariablesCaja.vNumPedVta,
                                                                FarmaVariables.vIdUsu);                    
                    //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                    if(codTrssc == null){
                        FarmaUtility.showMessage(this,"Ocurrio un error al registrar la transacción.",null);
                        throw new Exception("Ocurrio un error al registrar la transacción.");
                    }        
                    rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_RECARGA_SIX, 
                                                                    ConstantsRecaudacion.RCD_PAGO_SIX_RECARGA_VIRTUAL_CLARO, 
                                                                    codTrssc );
                    strConcentrador = ConstantsRecaudacion.COD_CONCENTRADOR_CLARO;
                }                   
        
                bRpt = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                bMsj = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                strResponseCode = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                //strMontoPagar = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                //strCodAuditoria = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA); 
                // SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR, EN RECARGAS SE GUARDA EN ADM 
                strCodAutorizacion = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);
                
                //UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
                //utilityRecaudacion.initMensajesVentana(this, null, null);
                try
                {   DBCaja.grabaRespuestaRecargaVirtual(strResponseCode, VariablesCaja.vNumPedVta); 
                }
                catch(Exception e)
                {   throw new Exception(ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_FALLIDO);
                }
                
                //GFonseca 23/10/2013. Se modifican los mensajes
                //ERIOS 07.10.2013 Si existe error, no continua con el proceso.
                //if( bMsj )
                //{   //FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO , null);
                //    throw new Exception(ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_FALLIDO);
                //}         
        
                VariablesVirtual.vCodigoAprobacion = strCodAutorizacion;
                VariablesVirtual.vNumTrace = codTrssc.toString();
                UtilityCaja.actualizaInfoPedidoVirtual(this);
                
                if( bRpt && ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode))
                {
                    try
                    {
                        //INICIO CONCILIACION
                        //ERIOS 11.10.2013 Cambio en parametro PCL_COD_AUTORIZACION
                        String PCL_COD_ID_CONCENTRADOR = strConcentrador; //052 Claro y 055 Movistar
                        String PCL_NUMERO_TELEFONO = telefono;
                        String PCL_COD_AUTORIZACION = strCodAutorizacion; //Codigo de Autorizacion
                        String PCL_COD_VENDEDOR = FarmaVariables.vNuSecUsu; //Codigo de Vendedor
                        String PCL_NUMERO_DOCUMENTO= VariablesCaja.vNumPedVta; //Comprobante
                        String PCL_COD_COMERCIO = facadeRecaudacion.getCodLocalMigra(); //Ccodigo de Local
                        String PCL_COD_TERMINAL= VariablesCaja.vNumCaja; //Nro de Caja
                        String PCL_MONTO_VENTA = monto; //monto recarga
                        String PCL_ID_TRANSACCION = codTrssc.toString(); //ID enviado por la Empresa telefonica
                        
                        (new UtilityRecaudacion()).initMensajesVentana(this, null, null, "00");
                        
                        String PCL_FECHA_VENTA=ConstantsRecaudacion.FECHA_RCD;
                        String PCL_HORA_VENTA=ConstantsRecaudacion.HORA_RCD;
                        
                        String vSalida = facadeRecaudacion.setDatosRecargaConciliacion(PCL_COD_ID_CONCENTRADOR, 
                                                                                        PCL_NUMERO_TELEFONO, 
                                                                                        PCL_COD_AUTORIZACION, 
                                                                                        PCL_COD_VENDEDOR, 
                                                                                        PCL_FECHA_VENTA, 
                                                                                        PCL_HORA_VENTA, 
                                                                                        PCL_NUMERO_DOCUMENTO, 
                                                                                        PCL_COD_COMERCIO, 
                                                                                        PCL_COD_TERMINAL, 
                                                                                        PCL_MONTO_VENTA, 
                                                                                        PCL_ID_TRANSACCION
                                                                                        );
                        log.info("Respuesta conciliacion recargas: "+vSalida);
                        //FIN CONCILIACION            
                    }
                    catch(Exception e)
                    {   log.debug("", e);
                    }
                }
                else
                {
                    //GFonseca 23/10/2013. Se añade mensaje cuando el servicio de Claro del SIX esta inactivo.
                    if(ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode))
                    {
                        throw new Exception(ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA); 
                    }
                    else  if(ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode))
                    {
                        if (ConstantsVentas.TARJ_RECARGA_CLARO_VIRTUAL.equals(codProd))
                        {   throw new Exception(ConstantsRecaudacion.RCD_MSJ_CLARO_SERV_INACTIVO);
                        }
                        else
                        {   throw new Exception(ConstantsRecaudacion.RCD_MSJ_RECARGA_MOVISTAR_SERV_INACTIVO);                        
                        }                         
                    }else{
                        //TODO ERIOS 21.11.2013 Mejorar con mensaje del operador
                        throw new Exception("Codigo de Error: "+strResponseCode);
                    }
                }
            }
            else
            {   try
                {   UtilityCaja.procesaVentaProductoVirtual(this, txtNroPedido);
                }
                catch (Exception ex)
                {   throw new Exception("Error al procesar el pedido virtual - \n" + 
                    ex.getMessage());
                }
                /*
                * Se grabara la respuesta obtenida por el proveedor al realizar la
                * recarga virtual
                */
                try
                {
                    DBCaja.grabaRespuestaRecargaVirtual(VariablesVirtual.respuestaTXBean.getCodigoRespuesta(),
                                                        VariablesCaja.vNumPedVta);
                }
                catch(Exception e)
                {   throw new Exception("Error al realizar la transaccion con el proveedor.\n");
                }
            
                if (!validaCodigoRespuestaTransaccion())
                {
                    throw new Exception("Error al realizar la transaccion con el proveedor.\n" + 
                                        VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + 
                                        " - " + 
                                        VariablesVirtual.respuestaTXBean.getDescripcion());
                }
            }
        }
        else if(ConstantsVentas.TIPO_PROD_VIRTUAL_MAGISTRAL.equalsIgnoreCase(VariablesCaja.vTipoProdVirtual) &&
                FarmaConstants.INDICADOR_S.equalsIgnoreCase(VariablesPtoVenta.vIndVerReceMagis))
        {
            HashMap<String,String> hRecetario = new HashMap<String,String>(); 
            
            DBRecetario.getNumeroRecetario(VariablesCaja.vNumPedVta, hRecetario);
            
            String numRecetario = "";
            String estRecetario = "";
            
            if(hRecetario != null)
            {   numRecetario = hRecetario.get("NUM_RECETARIO");
                estRecetario = hRecetario.get("EST_RECETARIO");
            }
            
            if(!"".equals(numRecetario))
            {   if(estRecetario.equals(ConstantsRecetario.Estado.PENDIENTE.getValor()))
                {   
                    String tramaRecetario = DBRecetario.getTramaRecetario(numRecetario);
            
                    //Envia la trama al sistema de Fasa
                    String rptaRecetario = facadeRecetario.enviaTramaRecetario(tramaRecetario);
            
                    if("OK".equalsIgnoreCase(rptaRecetario))
                    {
                        DBRecetario.actualizaEstadoRecetario(numRecetario,ConstantsRecetario.Estado.ENVIADO);                    
                    }
                    else
                    {
                        //indCommitBefore = "N";
                        log.error("Trama resp: "+rptaRecetario);
                        throw new Exception("Se ha presentado un error al enviar el recetario.\n");
                    }
                }
                else if(ConstantsRecetario.Estado.GUIA.getValor().equalsIgnoreCase(estRecetario))
                {
                    //Los recetarios que se generan a partir de [G]uias, no se envian.
                    DBRecetario.actualizaEstadoRecetario(numRecetario,ConstantsRecetario.Estado.COBRADO);                    
                }
            }
            else
            {   throw new Exception("No se encuentra el numero de Recetario.");
            }
        }
    }
    
    private void muestraTarjetaVirtualGenerado()
    {
        DlgNumeroTarjetaGenerado dlgNumeroTarjetaGenerado = new DlgNumeroTarjetaGenerado(myParentFrame, "", true);
        dlgNumeroTarjetaGenerado.setVisible(true);
        FarmaVariables.vAceptar = false;
    }

    private void obtieneInfoPedidoVirtual() throws Exception
    {
        try
        {
            DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            VariablesCaja.vNumPedVta);
            log.debug("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
        }
        catch (SQLException sql)
        {
            log.error("",sql);
            throw new Exception("Error al obtener informacion del pedido virtual - \n" + sql);
        }
    }

    private void colocaInfoPedidoVirtual()
    {
        try
        {   ArrayList temp = VariablesVirtual.vArrayList_InfoProdVirtual;
            VariablesCaja.vCodProd = FarmaUtility.getValueFieldArrayList(temp, 0, 0);
            VariablesCaja.vTipoProdVirtual = FarmaUtility.getValueFieldArrayList(temp, 0, 1);
            VariablesCaja.vPrecioProdVirtual = FarmaUtility.getValueFieldArrayList(temp, 0, 2);
            VariablesCaja.vNumeroCelular = FarmaUtility.getValueFieldArrayList(temp, 0, 3);
            VariablesCaja.vCodigoProv = FarmaUtility.getValueFieldArrayList(temp, 0, 4);
            VariablesCaja.vTipoTarjeta = FarmaUtility.getValueFieldArrayList(temp, 0, 7);
        }
        catch(Exception e)
        {   log.error("", e);
        }
    }

    private boolean validaCodigoRespuestaTransaccion()
    {
        boolean result = false;
        log.debug("VariablesVirtual.vCodigoRespuesta 1" + 
        VariablesVirtual.vCodigoRespuesta);
        if (VariablesVirtual.vCodigoRespuesta.equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL))
            result = true;
        return result;
    }

    public void setTblFormasPago(JTable tblFormasPago) {
        this.tblFormasPago = tblFormasPago;
    }

    public JTable getTblFormasPago() {
        return tblFormasPago;
    }

    public void setTxtNroPedido(JTextField txtNroPedido) {
        this.txtNroPedido = txtNroPedido;
    }

    public JTextField getTxtNroPedido() {
        return txtNroPedido;
    }

    public void setVProcesoRecarga(boolean vProcesoRecarga) {
        this.vProcesoRecarga = vProcesoRecarga;
    }

    public boolean isVProcesoRecarga() {
        return vProcesoRecarga;
    }
}
