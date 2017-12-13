package mifarma.farmaCTL;


import com.novatronic.components.sixclient.tcp.exception.ClienteSIXConnectionException;

import java.util.Properties;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;

import mifarma.farmaUtil.Constantes;
import mifarma.farmaUtil.Funciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClienteSIX implements StrategyRecargas{

    private static final Logger log = LoggerFactory.getLogger(ClienteSIX.class);
    
    public static String CLIENTE_SIX_CONST_1  = "2005";
    public static String CLIENTE_SIX_CONST_2  = "FSISOINT";
    public static String CLIENTE_SIX_CONST_3  = "estandar";
    public static String CLIENTE_SIX_CONST_4  = "log4j.properties";
    
    private Despachador despachador;

    public ClienteSIX(String hostname, String username, String password, 
                      String hostdest, String portdest, String procdest,
                      String typesixdrv, String loggerFile){
        Properties CONF = new Properties();
        //Datos del servidor
        CONF.setProperty("hostname", hostname);
        CONF.setProperty("username", username);
        CONF.setProperty("password", password);
        CONF.setProperty("hostdest", hostdest);
        CONF.setProperty("portdest", portdest);
        CONF.setProperty("procdest", procdest);
        CONF.setProperty("typesixdrv", typesixdrv);
        CONF.setProperty("loggerFile", loggerFile);
        despachador = new Despachador(CONF);
    }

    public void iniciarConexion(){
        
    }
    
    /***********************************NEXTEL***************************************/
    
    /**
     * Recarga Nextel
     * @author ASOSA
     * @since 28/10/2014
     * @type RECAR - NEX
     * @param entrada
     * @return
     * @throws Exception
     */
    @Override
    public RespuestaBean recargaEntel(EntradaBean entrada) throws Exception {
        RespuestaBean respuesta = new RespuestaBean();
        
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoTrnscc(Constantes.TRNS_RECARGA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            respuesta = despachador.enviar(entrada);
        
        return respuesta;
    }

    /***********************************CLARO***************************************/

    /**
     * <pre>
     * Envía una recarga Claro<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE04-monto; DE11-auditoria; DE41-terminal; DE42-comercio; 
     * DE43-ubicacion; DE48-telefono
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut; DE39-responseCode;
     * </pre>
     */
    public RespuestaBean recargaClaro(EntradaBean entrada) throws Exception {
        RespuestaBean respuesta = new RespuestaBean();
        
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            //entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);            
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_RECARGA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            respuesta = despachador.enviar(entrada);
        
        return respuesta;
    }

    /**
     * <pre>
     * Envía una consulta de la deuda del servicio Claro PostPago<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE11-auditoria; DE41-terminal; DE42-comercio; 
     * DE43-ubicacion; DE48-telefono; DE125-codSucursal; DE125-codPzaRecaudador
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut; DE39-responseCode; DE125:deudaTotal
     * </pre>
     */ 
    public RespuestaBean consultaClaroPostPago(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_CNSULTA_SRV);
            entrada.setTipoCuentaFrom(Constantes.CTA_SERVICIOS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 4
            entrada.setMonto("0000000000.00");
            //campo 124
            entrada.setCodTransExtend(Constantes.CTE_VARIOS_SERV_VARIOS_DOCS);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
            
            log.error("", e);
        }
        return respuesta;
    }

    /**
     * <pre>
     * Envía un pago de servicio Claro PostPago<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE04-monto; DE11-auditoria; DE41-terminal; DE42-comercio; 
     * DE43-ubicacion; DE48-telefono; DE125-codSucursal; DE125-codPzaRecaudador
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut; DE39-responseCode;
     * </pre>
     */
    public RespuestaBean pagarServicioClaroPostPago(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_PAG_PRE_AUTORI_SRV);
            entrada.setTipoCuentaFrom(Constantes.CTA_SERVICIOS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 124
            entrada.setCodTransExtend(Constantes.CTE_UN_SERV_VARIOS_DOCS);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);
        }
        return respuesta;
    }

    /**
     * <pre>
     * Anula un pago de servicio Claro PostPago<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE04-monto; DE11-auditoria; DE41-terminal; DE42-comercio; 
     * DE43-ubicacion; DE48-telefono; DE125-codSucursal; DE125-codPzaRecaudador;
     * <b>Anulación:</b> DE39-motivoExtorno;
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut; DE39-responseCode;
     * </pre>
     */
    public RespuestaBean anularFacturacionPostPago(EntradaBean entrada){
       RespuestaBean respuesta = new RespuestaBean();
       try{
           entrada.setTipoMensaje(Constantes.MSG_200+"");
           entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);
           //campo 3
           entrada.setTipoTrnscc(Constantes.TRNS_ANU_PAG_SRV);
           entrada.setTipoCuentaFrom(Constantes.CTA_SERVICIOS);
           entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
           //campo 124
           entrada.setCodTransExtend(Constantes.CTE_VARIOS_SERV_VARIOS_DOCS);
           //125
           //sentrada.setIdAnular(String.format("%1$-12s", Constantes.EMPTY));
           respuesta = despachador.enviar(entrada);
       }catch(ClienteSIXConnectionException e){
           
           log.error("ClienteSIXConnectionException:", e);
       }catch(Exception e){
          
          log.error("", e);
       }
       return respuesta;
    }

    /**
     * <pre>
     * Envía un extorno de servicio Claro PostPago<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE04-monto; DE11-auditoria; DE41-terminal; DE42-comercio; 
     * DE43-ubicacion; DE48-telefono; DE125-codSucursal; DE125-codPzaRecaudador
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut; DE39-responseCode;
     * </pre>
     */
    public RespuestaBean extornoPagoServiciosClaro(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_400+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_PAG_PRE_AUTORI_SRV);
            entrada.setTipoCuentaFrom(Constantes.CTA_SERVICIOS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 124
            entrada.setCodTransExtend(Constantes.CTE_UN_SERV_VARIOS_DOCS);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);                 
        }
        return respuesta;
    }

    /***********************************MOVISTAR***************************************/
    
    /**
     * <pre>
     * Envía una recarga Movistar<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE04-monto; DE11-auditoria; DE41-terminal; DE42-comercio; 
     * DE43-ubicacion; DE48-telefono
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut; DE39-responseCode;
     * </pre>
     */
    public RespuestaBean recargaMovistar(EntradaBean entrada) throws Exception {
        RespuestaBean respuesta = new RespuestaBean();
        
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            //entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_MOVISTAR);            
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_RECARGA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            respuesta = despachador.enviar(entrada);
        
        return respuesta;
    }

    /***********************************BITEL***************************************/
    @Override
    public RespuestaBean recargaBitel(EntradaBean entrada) throws Exception {
        RespuestaBean respuesta = new RespuestaBean();
        
        entrada.setTipoMensaje(Constantes.MSG_200+"");
        entrada.setTipoRecaudacion(Constantes.TIPO_RECAR_BITEL);
        entrada.setTipoTrnscc(Constantes.TRNS_RECARGA);
        
        respuesta = despachador.enviar(entrada);
        
        return respuesta;
    }
    
    /***********************************CMR***************************************/
    
    /**
    * <pre>
    * Envía un pago de tarjeta CMR<br>
    * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
    * DE02-nroTarjeta; DE04-monto; DE11-auditoria; DE41-setTerminal; 
    * DE42-comercio; DE43-ubicacion; DE125-setNroCuotas; DE125-codSucursal; 
    * DE125-nroCaja; DE125-idCajero; DE125-nroDocId
    * </pre>
    * @param entrada Objeto de datos de entrada, tipo EntradaBean
    * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
    * <pre>
    * Los atributos de <b>RespuestaBean</b> que se utilizan:
    * tramaIn; tramaOut, DE39-responseCode;
    * </pre>
    */
    public RespuestaBean pagoCuotaTarjetaCMR(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CMR);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_PAG_TARJ);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
            
            log.error("", e);
        }
        return respuesta;
    }

    /**
    * <pre>
    * Envía un venta con crédito CMR<br>
    * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
    * DE02-nroTarjeta; DE04-monto; DE11-auditoria; DE41-setTerminal; 
    * DE42-comercio; DE43-ubicacion; DE125-setNroCuotas; DE125-codSucursal; 
    * DE125-nroCaja; DE125-idCajero; DE125-nroDocId
    * </pre>
    * @param entrada Objeto de datos de entrada, tipo EntradaBean
    * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
    * <pre>
    * Los atributos de <b>RespuestaBean</b> que se utilizan:
    * tramaIn; tramaOut, DE39-responseCode;
    * </pre>
    */
    public RespuestaBean ventaCreditoCMR(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CMR);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_CMPRA_VNTA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);
        }
        return respuesta;
    }

   
     /**
     * <pre>
     * Anula un pago de tarjeta CMR, sea venta o recaudación <br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE02-nroTarjeta; DE04-monto; DE11-auditoria; DE41-setTerminal; 
     * DE42-comercio; DE43-ubicacion; DE125-setNroCuotas; DE125-codSucursal; 
     * DE125-idCajero; DE125-idAnular; DE125-nroDocId
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut, DE39-responseCode;
     * </pre>
     */
    public RespuestaBean anularTransaccionCMR(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CMR);
            //campo 
            entrada.setTipoTrnscc(Constantes.TRNS_ANU_VNTA);            
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            //campo 125
            entrada.setIdAnular(Funciones.formatoCeroIzq(12,entrada.getIdAnular()));
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);                 
        }
        return respuesta;
    }


    public RespuestaBean extornarPagoCMR(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_420+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CMR);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_PAG_TARJ);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                        
            log.error("", e);
        }
        return respuesta;
    }
    
    public RespuestaBean extornarVentaCMR(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_420+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CMR);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_CMPRA_VNTA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                        
            log.error("", e);                 
        }
        return respuesta;
    }
    
    

    /***********************************RIPLEY***************************************/

    /**
    * <pre>
    * Envía un pago de tarjeta CMR<br>
    * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
    * DE02-nroTarjeta; DE04-monto; DE11-auditoria; DE41-setTerminal; 
    * DE42-comercio; DE43-ubicacion; DE125-setNroCuotas; DE125-codSucursal; 
    * DE125-nroCaja; DE125-idCajero; DE125-nroDocId
    * </pre>
    * @param entrada Objeto de datos de entrada, tipo EntradaBean
    * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
    * <pre>
    * Los atributos de <b>RespuestaBean</b> que se utilizan:
    * tramaIn; tramaOut, DE39-responseCode;
    * </pre>
    */
    public RespuestaBean pagoCuotaTarjetaRipley(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_RIPLEY);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_PAG_TARJ);
            entrada.setTipoCuentaFrom(Constantes.CTE_VARIOS_SERV_VARIOS_DOCS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);                 
        }
        return respuesta;
    }


    /**
    * <pre>
    * Envía un pago de tarjeta CMR<br>
    * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
    * DE02-nroTarjeta; DE04-monto; DE11-auditoria; DE41-setTerminal; 
    * DE42-comercio; DE43-ubicacion; DE125-setNroCuotas; DE125-codSucursal; 
    * DE125-nroCaja; DE125-idCajero; DE125-nroDocId
    * </pre>
    * @param entrada Objeto de datos de entrada, tipo EntradaBean
    * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
    * <pre>
    * Los atributos de <b>RespuestaBean</b> que se utilizan:
    * tramaIn; tramaOut, DE39-responseCode;
    * </pre>
    */
    public RespuestaBean anulacionPagoRipley(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_RIPLEY);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_ANU_PAG);
            entrada.setTipoCuentaFrom(Constantes.CTE_VARIOS_SERV_VARIOS_DOCS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);                 
        }
        return respuesta;
    }

    /**
     * Venta Ripley
     * @author ERIOS
     * @since 25.03.2014
     * @param entrada
     * @return
     */
    public RespuestaBean ventaCreditoRipley(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_VENTA_RIPLEY);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_CMPRA_VNTA);
            entrada.setTipoCuentaFrom(Constantes.CTE_VARIOS_SERV_VARIOS_DOCS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);
        }
        return respuesta;
    }
    
    /**
     * Anulacion venta Ripley
     * @author ERIOS
     * @since 25.03.2014
     * @param entrada
     * @return
     */
    public RespuestaBean anulacionVentaRipley(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_VENTA_RIPLEY);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_ANU_VNTA);
            entrada.setTipoCuentaFrom(Constantes.CTE_VARIOS_SERV_VARIOS_DOCS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);                 
        }
        return respuesta;
    }
    
    /***********************************TEST***************************************/
    
    /**
     * <pre>
     * Envía un test de conexión con el SIX<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE11-auditoria
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut, DE39-responseCode;
     * </pre>
     */
    public RespuestaBean test800(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_800+"");
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);                 
        }
        return respuesta;
    }

    /*************************NO USADOS*************************************/

    /**
     * <pre>
     * Anula una recarga Claro<br>
     * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
     * DE04-monto; DE11-auditoria; DE41-terminal; DE42-comercio; 
     * DE43-ubicacion; DE48-telefono; <b>Anulación:</b> DE39-motivoExtorno;
     * </pre>
     * @param entrada Objeto de datos de entrada, tipo EntradaBean
     * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
     * <pre>
     * Los atributos de <b>RespuestaBean</b> que se utilizan:
     * tramaIn; tramaOut, DE39-responseCode;
     * </pre>
     */
     public RespuestaBean anularRecargaClaro(EntradaBean entrada){
         RespuestaBean respuesta = new RespuestaBean();
         try{
             entrada.setTipoMensaje(Constantes.MSG_200+"");
             //entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);             
             //campo 3
             entrada.setTipoTrnscc(Constantes.TRNS_ANU_RECARGA);
             entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
             entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
             //campo 125
             //entrada.setIdAnular(String.format("%1$-12s", entrada.getIdAnular()));
             respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
             
             log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                          
             log.error("", e);
        }
        return respuesta;
     }
    
    /**
     * Anular recarga nextel
     * @author ASOSA
     * @since 31/10/2014
     * @type RECAR - NEX
     * @param entrada
     * @return
     */
    public RespuestaBean anularRecargaEntel(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            //entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);             
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_ANU_RECARGA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 125
            //entrada.setIdAnular(String.format("%1$-12s", entrada.getIdAnular()));
            respuesta = despachador.enviar(entrada);
       }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);      
       }catch(Exception e){
           log.info("ERORR: ", e.getMessage());
            log.error("", e);
       }
       return respuesta;
    }
    
    
    
    /**
     * Envio extorno recarga nextel
     * @author ASOSA
     * @since 31/10/2014
     * @type RECAR - NEX
     * @param entrada
     * @return
     */
    public RespuestaBean extornoRecargaNextel(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_400+"");
            //entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);            
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_RECARGA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
           entrada.setMotivoExtorno("22");
            //campo 125
            //entrada.setIdAnular(String.format("%1$-12s", entrada.getIdAnular()));
            respuesta = despachador.enviar(entrada);
       }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
       }catch(Exception e){
                         
            log.error("", e);
       }
       return respuesta;
    }

    public RespuestaBean extornoRecargaClaro(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_400+"");
            //entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CLARO);            
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_RECARGA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 125
            //entrada.setIdAnular(String.format("%1$-12s", entrada.getIdAnular()));
            respuesta = despachador.enviar(entrada);
       }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
       }catch(Exception e){
                         
            log.error("", e);
       }
       return respuesta;
    }
    
     /**
      * <pre>
      * Anula una recarga de Movistar<br>
      * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
      * DE04-monto; DE11-auditoria; DE41-terminal; DE42-comercio; 
      * DE43-ubicacion; DE48-telefono; <b>Anulación:</b> DE39-motivoExtorno;
      * </pre>
      * @param entrada Objeto de datos de entrada, tipo EntradaBean
      * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
      * <pre>
      * Los atributos de <b>RespuestaBean</b> que se utilizan:
      * tramaIn; tramaOut, DE39-responseCode;
      * </pre>
      */
     public RespuestaBean anularRecargaMovistar(EntradaBean entrada){
         RespuestaBean respuesta = new RespuestaBean();
         try{
             entrada.setTipoMensaje(Constantes.MSG_400+"");
             //entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_MOVISTAR);             
             //campo 3
             entrada.setTipoTrnscc(Constantes.TRNS_RECARGA);
             entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
             entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
             log.info("HITO 10: anularRecargaMovistar de ClienteSIX");
             respuesta = despachador.enviar(entrada);
             
         }catch(ClienteSIXConnectionException e){
             
             log.error("ClienteSIXConnectionException:", e);
         }catch(Exception e){
             
             log.error("", e);
         }
         return respuesta;
     }

    public RespuestaBean anularRecargaBitel(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_ANU_RECARGA);
            entrada.setTipoCuentaFrom(Constantes.CTA_UNIVERSAL);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 125
            //entrada.setIdAnular(String.format("%1$-12s", entrada.getIdAnular()));
            respuesta = despachador.enviar(entrada);
       }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);      
       }catch(Exception e){
           log.info("ERORR: ", e.getMessage());
            log.error("", e);
       }
       return respuesta;
    }
    
    /**
    * <pre>
    * Envía una consulta de la deuda CMR<br>
    * Los atributos de <b>entrada</b> se utilizan en los siguientes Data Elements:
    * DE02-nroTarjeta; DE11-auditoria; DE41-setTerminal; DE42-comercio;
    * DE43-ubicacion; DE125-setNroCuotas; DE125-codSucursal; DE125-nroCaja;
    * DE125-idCajero; DE125-codPzaRecaudador
    * </pre>
    * @param entrada Objeto de datos de entrada, tipo EntradaBean
    * @return respuestaBean - Datos de respuesta del SIX, tipo RespuestaBean
    * <pre>
    * Los atributos de <b>RespuestaBean</b> que se utilizan:
    * tramaIn; tramaOut; DE39-responseCode; DE125:deudaTotal
    * </pre>
    */
    /*public RespuestaBean consultaDeudaCMR(EntradaBean entrada){
        RespuestaBean respuesta = new RespuestaBean();
        try{
            entrada.setTipoMensaje(Constantes.MSG_200+"");
            entrada.setTipoRecaudacion(Constantes.TIPO_RECAUD_CMR);
            //campo 3
            entrada.setTipoTrnscc(Constantes.TRNS_CNSULTA_SRV);
            entrada.setTipoCuentaFrom(Constantes.CTA_SERVICIOS);
            entrada.setTipoCuentaTo(Constantes.CTA_UNIVERSAL);
            //campo 4
            entrada.setMonto("0000000000.00");
            //campo 22
            entrada.setModoIngrDatos(Constantes.PTOVENTA_MODO_ING_TARJ_MANUAL);
            //campo 124
            entrada.setCodTransExtend(Constantes.CTE_VARIOS_SERV_VARIOS_DOCS);
            respuesta = despachador.enviar(entrada);
        }catch(ClienteSIXConnectionException e){
            
            log.error("ClienteSIXConnectionException:", e);
        }catch(Exception e){
                         
            log.error("", e);
        }
        return respuesta;
    }*/

}