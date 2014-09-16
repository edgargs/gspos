package mifarma.ptoventa.pinpad.visa;


import com.mifarma.jni.PinPadJava;

import java.io.File;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.pinpad.reference.DBPinpad;
import mifarma.ptoventa.pinpad.reference.HiloImpresion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ManejadorTramaRetorno
{
    
    private static final Logger log = LoggerFactory.getLogger(ManejadorTramaRetorno.class);
    
    private String inicioTrama="";
    private boolean ultMensaje=false;
    private Integer longSegDatos=0;
    private String tipoMensaje="";
    private String finDatos="";
    private String tramaDatos="";
    private String segDatos = "";
    private String digitoChequeo="";
    
    private String tramaEnvio = "";
    private String tramaRetorno = "";
    
    private String codRespuesta = "";
    private String mensFinOperacion = "";
    private String nombreCliente = "";
    private String numAutorizacion = "";
    private String numReferencia = "";
    private String numTarjeta = "";
    private Date fechaExpiracion = null;
    private String fechaExpiracionStr = "";
    private Date fechaTransaccion = null;
    private String fechaTransaccionStr = "";
    private String horaTransaccion = "";
    private String codOperacion = "";
    private boolean cortePapel = false;
    private String cortePapelStr = "";
    private Double propina = 0.0;
    private String propinaStr = "";
    private String numMozo = "";
    private String empresa = "";
    private String terminal = "";
    private Integer cuotas = 0;
    private String pagoDiferido = "";
    private String pidePin = "";
    private String idUnico = "";
    private Double montoCuota = 0.0;
    private String montoCuotaStr = "";
    //TODO leer de BBDD
    //private String carpeta = "C:\\farmaventa\\pinpad\\visa";
    private int BUF_SIZE = 512;
    private int OP_TIMEOUT = 2;
    private int resp = 0;
    
    private PinPadJava pinPadJava = new PinPadJava();
    private StringBuffer buffer = new StringBuffer(BUF_SIZE);
    private String nroTarjetaBruto;
    private String lblDatoPedidoEnv = "";

    /**
     * Inicia el proceso de comunicación y envia la trama inicial al pinpad Visa
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public boolean iniciarProceso(String trama)
    {   
        log.debug("Inicia Proceso PinPad - Visa");
        this.tramaEnvio = trama;
        try
        {   //se configura la comunicación con el pinpad
            loadLibrary();
            log.debug("carga libreria");
            //se inicia la operación enviando la trama presente
            resp = pinPadJava.fiStartOperation(tramaEnvio,OP_TIMEOUT, buffer);
            log.debug("Obtiene respuesta del metodo fiStartOperation:--->    "+resp+" y  "+buffer);
            if(resp == VariablesPinpad.RET_OK && buffer.toString().length()>1)
            {   tramaRetorno = buffer.toString();
                resp = traducirTramaRetorno(buffer);
                log.debug("Trama Retorno:inicia proceso------>    "+tramaRetorno);
                log.debug("Respuesta al traduccion trama:inicia proceso---->    "+resp);
            }
            if(resp == VariablesPinpad.RET_OK)
                return true;
            else
                return false;
        }
        catch(Exception e)
        {   log.debug("HORROR iniciar Proceso VISA");
            log.error("",e);
            return false;
        }
    }
    
    /**
     * Recupera y traduce el mensaje mas reciente enviado por el pinpad Visa
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public boolean obtenerInfoProceso()
    {   try
        {   reiniciarVariables();
            buffer = new StringBuffer(BUF_SIZE);
            resp = pinPadJava.fiGetStatus(buffer, BUF_SIZE);
            if(resp == VariablesPinpad.RET_OK)
            {   tramaRetorno = buffer.toString();
                log.debug("Trama Retorno:Obtener Infor Proceso------>    "+tramaRetorno);
             
                if(tramaRetorno.length()>1)
                {   traducirTramaRetorno(buffer);
                }
            }
            if(resp == VariablesPinpad.RET_OK)
                return true;
            else
                return false;
        }
        catch(Exception e)
        {   log.error("",e);
            return false;
        }
    }
    
    /**
     * Finaliza el proceso de comunicación con el pinpad Visa
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public boolean finalizarProceso()
    {   try
        {   resp = pinPadJava.fiClosePort();
            log.debug("Finaliza Proceso:    --->    "+resp);
            
            if(resp == VariablesPinpad.RET_OK)
                return true;
            else
                return false;
        }
        catch(Exception e)
        {   log.error("",e);
            return false;
        }
    }

    /**
     * Traduce la trama de retorno para mantenerlos en la clase
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    private Integer traducirTramaRetorno(StringBuffer sb)
    {   log.debug(" -----traducirTramaRetorno----- ");
        String tramaTemp = sb.toString();
        byte[] bytes = tramaTemp.getBytes();
        try
        {   tramaDatos="";
            if(tramaTemp.length()>0)
            {   inicioTrama = tramaTemp.substring(0, 1);
                tramaTemp = tramaTemp.substring(1, tramaTemp.length());
            }
            
            if(tramaTemp.length()>0)
            {   longSegDatos = Integer.parseInt(tramaTemp.substring(0,3));
                tramaTemp = tramaTemp.substring(3, tramaTemp.length());
            }
            
            if(tramaTemp.length()>0)
            {   segDatos = tramaTemp.substring(0,longSegDatos);
                tramaTemp = tramaTemp.substring(longSegDatos, tramaTemp.length());
                log.debug("VISA- segDatos: "+segDatos);
            }
            
            if(tramaTemp.length()>0)
            {   finDatos = tramaTemp.substring(0,1);
                tramaTemp = tramaTemp.substring(1, tramaTemp.length());
            }
            
            if(tramaTemp.length()>0)
            {   digitoChequeo = tramaTemp.substring(0,1);
                tramaTemp = tramaTemp.substring(1, tramaTemp.length());
            }    
        
                log.debug("VISA-MENSAJE tramaTemp:  "+tramaTemp);
            
            log.debug("*************** SE PROCESA EL SEGMENTO DE DATOS ***************");
            /*************** SE PROCESA EL SEGMENTO DE DATOS ***************/
            if(segDatos.length()>0)
            {   tipoMensaje = segDatos.substring(0,1);
                segDatos = segDatos.substring(1, segDatos.length());
                log.debug("VISA- tipoMensaje: "+tipoMensaje);
            }
            
            String tmp=null;
            if(segDatos.length()>0)
            {   tmp = segDatos.substring(0,1);
                segDatos = segDatos.substring(1, segDatos.length());
            }
            if(tmp==null)
                ultMensaje = false;
            else if("1".equals(tmp))
                ultMensaje = true;
            
            if(segDatos.length()>0)
            {   tramaDatos = segDatos.substring(0, segDatos.length());
            }
                    
                log.debug("VISA- tramaDatos: "+tipoMensaje);
                    
            //Si el mensaje es enviado a CAJA
            if(tipoMensaje!=null && tipoMensaje.equals(VariablesPinpad.TIPO_MENSJ_PINPAD_CAJA))
            {   log.debug("Mensaje enviado a PINPAD CAJA");
                //se valida el ingreso
                //se traduce la tramaDatos
                String[] tramaDividida = tramaDatos.split(VariablesPinpad.SEPARADOR);
                log.debug("Ingresa a un FOR con cantidad de repeticiones:  ---> "+tramaDividida.length);
                
                for(int i=0;i<tramaDividida.length;i++)
                {   String temp = tramaDividida[i];
                    String prefijo = temp.substring(0, 1);
                    String dato = temp.substring(1, temp.length());
                    
                    //se valida la información obtenida anteriormente
                    Integer longDato = VariablesPinpad.longSubCamposRetorno.get(prefijo);
                    if(VariablesPinpad.PREF_RETOR_COD_RESP.equals(prefijo))
                    {   codRespuesta = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_MENS_FIN_OPE.equals(prefijo))
                    {   mensFinOperacion = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_NOMBRE_CLIENTE.equals(prefijo))
                    {   nombreCliente= dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_NUM_AUTORIZ.equals(prefijo))
                    {   numAutorizacion = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_NUM_REFERENCIA.equals(prefijo))
                    {   numReferencia = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_NUM_TARJETA.equals(prefijo))
                    {   numTarjeta = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_FECHA_EXP.equals(prefijo))
                    {   SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyMM");
                        try
                        {   fechaExpiracion = formatoDelTexto.parse(dato);
                        }
                        catch (ParseException ex) 
                        {   log.error("",ex);log.error("",ex);     }
                        fechaExpiracionStr = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_FECHA_TRANSACC.equals(prefijo))
                    {   SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yy");
                        try
                        {   fechaTransaccion = formatoDelTexto.parse(dato);
                        }
                        catch (ParseException ex) 
                        {   log.error("",ex);log.error("",ex);     }
                        fechaTransaccionStr = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_HORA_TRANSACC.equals(prefijo))
                    {   horaTransaccion = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_COD_OPERACION.equals(prefijo))
                    {   codOperacion = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_CORTE_PAPEL.equals(prefijo))
                    {   cortePapelStr = dato;
                        if("1".equals(cortePapelStr))
                            cortePapel = true;
                        else
                            cortePapel = false;
                    }
                    else if(VariablesPinpad.PREF_RETOR_PROPINA.equals(prefijo))
                    {   propina = Double.parseDouble(dato)/100;
                        propinaStr = dato;
                    }
                    else if(VariablesPinpad.PREF_RETOR_NUM_MOZO.equals(prefijo))
                    {   numMozo = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_EMPRESA.equals(prefijo))
                    {   empresa = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_TERMINAL.equals(prefijo))
                    {   terminal = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_CUOTAS.equals(prefijo))
                    {   cuotas = Integer.parseInt(dato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_PAGO_DIFERIDO.equals(prefijo))
                    {   pagoDiferido = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_FLAG_PIDEPIN.equals(prefijo))
                    {   pidePin = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_ID_UNICO.equals(prefijo))
                    {   idUnico = dato;//.substring(0, longDato);
                    }
                    else if(VariablesPinpad.PREF_RETOR_MONTO_CUOTA.equals(prefijo))
                    {   montoCuota = Double.parseDouble(dato)/100;
                        montoCuotaStr = dato;
                    }
                            log.debug("Prefijo:"+i+":"+prefijo);
                            log.debug("Dato:"+i+":"+dato);
                }
                
            }
            //SI EL MENSAJE ES ENVIADO A LA IMPRESORA
            else if(tipoMensaje!=null && tipoMensaje.equals(VariablesPinpad.TIPO_MENSJ_PINPAD_IMPR) )
            {  log.debug("VISA-Mensaje enviado a IMPRESORA PINPAD");
                mensFinOperacion = tramaDatos;
                log.debug("ELSE : trama:        "+mensFinOperacion);
            }
         
        }
        catch(Exception e)
        {   log.debug("HORROR TRADUCIR TRAMA RETORO: -->",e);
            //log.error("",e);
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }
    
    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarTramaBD(String numPed, String formaPago)
    {   
        int pRespuesta = VariablesPinpad.RET_OK;
        try
        {   if(!DBPinpad.guardarTramaPinpadVisa(codRespuesta, 
                                            mensFinOperacion, 
                                            nombreCliente, 
                                            numAutorizacion, 
                                            numReferencia, 
                                            nroTarjetaBruto, 
                                            fechaExpiracionStr, 
                                            fechaTransaccionStr, 
                                            horaTransaccion, 
                                            codOperacion, 
                                            cortePapelStr, 
                                            propinaStr, 
                                            numMozo, 
                                            empresa, 
                                            terminal, 
                                            cuotas.toString(), 
                                            pagoDiferido, 
                                            pidePin, 
                                            idUnico, 
                                            montoCuotaStr,
                                            numPed,
                                            "COM",
                                            formaPago))
                pRespuesta =  VariablesPinpad.RET_NOK;
                        
             
        }
        catch(Exception e){   
            log.error("",e);
            pRespuesta = VariablesPinpad.RET_NOK;
        }finally{
            log.debug("Respuesta al grabar trama pinpad:"+pRespuesta);
        }
        return pRespuesta;
    }
    
    public Integer guardarTramaAnulacionBD(String numPed, String formaPago)
    {   
        int pRespuesta = VariablesPinpad.RET_OK;
        try
        {   if(!DBPinpad.guardarTramaPinpadVisa(codRespuesta, 
                                            mensFinOperacion, 
                                            nombreCliente, 
                                            numAutorizacion, 
                                            numReferencia, 
                                            numTarjeta, 
                                            fechaExpiracionStr, 
                                            fechaTransaccionStr, 
                                            horaTransaccion, 
                                            codOperacion, 
                                            cortePapelStr, 
                                            propinaStr, 
                                            numMozo, 
                                            empresa, 
                                            terminal, 
                                            cuotas.toString(), 
                                            pagoDiferido, 
                                            pidePin, 
                                            idUnico, 
                                            montoCuotaStr,
                                            numPed,
                                            "ANU",
                                            formaPago))
                pRespuesta =  VariablesPinpad.RET_NOK;
                        
             
        }
        catch(Exception e){   
            log.error("",e);
            pRespuesta = VariablesPinpad.RET_NOK;
        }finally{
            log.debug("Respuesta al grabar trama pinpad:"+pRespuesta);
        }
        return pRespuesta;
    }
    
    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarMsjeImprBD(String msjeImpr, String codRespuesta, String numPed)
    {   try
        {   if(!DBPinpad.guardarTramaPinpadVisa(codRespuesta, 
                                            msjeImpr, 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            cortePapelStr, 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "", 
                                            "",
                                            numPed,
                                            "I",
                                            ""))
                return VariablesPinpad.RET_NOK;
        }
        catch(Exception e)
        {   log.error("HORROR Guardo mensaje TRAMA----->",e);
            //log.error("",e);
            return VariablesPinpad.RET_NOK;
        }
            log.debug("Guardo mensaje");
        return VariablesPinpad.RET_OK;
    }

    /**
     * Se realiza la impresión del mensaje enviado en la ticketera correspondiente
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    
    public boolean imprVoucher(String trama)
    {   //String[] segmentos = trama.split("0x0D");
        StringBuffer textoImpr = new StringBuffer();
        textoImpr.append("<html>\n" + 
                        "<head>\n" + 
                        "       <style>\n" + 
                        "               .tipoLetraA\n" + 
                        "               {       color: black;\n" + 
                        "                       font: 11px Consolas;\n" + 
                        "                       width: 240px;\n" + 
                        "               }\n" + 
                        "       </style>\n" + 
                        "</head>" +
                        "<body><br/><br/><br/>");
        String seg = trama;
        log.info("CUERPO VOUCHER:"+trama);
        String temp = seg.substring(0, seg.length());
        
        temp = temp.replaceAll(" ", "&nbsp;");
        textoImpr.append("<div class=\"tipoLetraA\">"+temp+"</div>");
        textoImpr.append("<br/><br/>.</body>" +
                        "</html>");
        
        HiloImpresion hilo = new HiloImpresion();
        hilo.textoImpr = textoImpr.toString();
        hilo.start();

        return true;
    } 
    
    /**
     * Se actualiza el indicador si la transacción no se anulo, pero se anula el pedido
     * @author LLEIVA
     * @since 19-Dic-2013
     */
    public Integer guardarIndAnulTransCerr(String numPed, String tipoTarj)
    {   try
        {   if(!DBPinpad.guardarIndAnulTransCerr(numPed, tipoTarj))
                return VariablesPinpad.RET_NOK;
        }
        catch(Exception e)
        {   log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }
    
    /**
     * Se cargan las librerias para iniciar la comunicación con el pinpadVisa
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    private void loadLibrary()
    {   //Cargar la libreria intermedia
        log.debug("Configurando PinPadJava...");
        String apiFullPath = VariablesPinpad.carpeta + "\\PinPadJava.dll";
        File f = new File(apiFullPath);
        String libreria = f.getAbsolutePath();
        try
        {   System.load(libreria);
        }
        catch (Throwable e)
        {   log.debug("HORROR CARGAR LIB");
            log.error("",e);
        }
        //se conecta a los DLL necesarios y se abre el puerto
        resp = pinPadJava.fiLoadLibrary(VariablesPinpad.carpeta + "\\CAJA_PINPAD.dll");
        if(resp == VariablesPinpad.RET_OK)
            resp = pinPadJava.fiOpenPort(VariablesPinpad.carpeta +"\\DLL3500.ini");
    }
    
    private  void reiniciarVariables()
    {   this.codRespuesta = "";
        this.mensFinOperacion = "";
        this.nombreCliente = "";
        this.numAutorizacion = "";
        this.numReferencia = "";
        this.numTarjeta = "";
        this.fechaExpiracion = null;
        this.fechaExpiracionStr = "";
        this.fechaTransaccion = null;
        this.fechaTransaccionStr = "";
        this.horaTransaccion = "";
        this.codOperacion = "";
        this.cortePapel = false;
        this.cortePapelStr = "";
        this.propina = 0.0;
        this.propinaStr = "";
        this.numMozo = "";
        this.empresa = "";
        this.terminal = "";
        this.cuotas = 0;
        this.pagoDiferido = "";
        this.pidePin = "";
        this.idUnico = "";
        this.montoCuota = 0.0;
        this.tipoMensaje = "";
        this.montoCuotaStr = "";
    }
    
    /************************ SETTER Y GETTERS ******************************/
    public void setCortePapel(boolean c)
    {   cortePapel = c;
    }
    
    /***********************************************************************/
    
    public String getTramaEnvio() {
        return tramaEnvio;
    }

    public String getTramaRetorno() {
        return tramaRetorno;
    }
    
    public String getCodRespuesta() {
        return codRespuesta;
    }

    public String getMensFinOperacion() {
        return mensFinOperacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNumAutorizacion() {
        return numAutorizacion;
    }

    public String getNumReferencia() {
        return numReferencia;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public String getFechaExpiracionStr() {
        return fechaExpiracionStr;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }
    
    public String getFechaTransaccionStr() {
        return fechaTransaccionStr;
    }

    public String getHoraTransaccion() {
        return horaTransaccion;
    }

    public String getCodOperacion() {
        return codOperacion;
    }

    public boolean isCortePapel() {
        return cortePapel;
    }

    public Double getPropina() {
        return propina;
    }
    
    public String getPropinaStr() {
        return propinaStr;
    }

    public String getNumMozo() {
        return numMozo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getTerminal() {
        return terminal;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public String getPagoDiferido() {
        return pagoDiferido;
    }

    public String getPidePin() {
        return pidePin;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public Double getMontoCuota() {
        return montoCuota;
    }
    
    public String getMontoCuotaStr() {
        return montoCuotaStr;
    }
    
    public String getTipoMensaje()
    {   return tipoMensaje;
    }
    
    public boolean isUltMensaje()
    {   return ultMensaje;
    }

    public void setNroTarjetaBruto(String nroTarjetaBruto) {
        this.nroTarjetaBruto = nroTarjetaBruto;
    }

    public String getNroTarjetaBruto() {
        return nroTarjetaBruto;
    }

    public void setLblDatoPedidoEnv(String lblDatoPedidoEnv) {
        this.lblDatoPedidoEnv = lblDatoPedidoEnv;
    }

    public String getLblDatoPedidoEnv() {
        return lblDatoPedidoEnv;
    }
}