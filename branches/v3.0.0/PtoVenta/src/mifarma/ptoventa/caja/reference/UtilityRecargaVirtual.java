package mifarma.ptoventa.caja.reference;

import bean.PeticionSolicitudCompra;
import bean.RespuestaSolicitudCompra;
import java.sql.SQLException;
import mifarma.common.*;
import mifarma.common.FarmaConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mifarma.common.FarmaVariables;
import com.gs.mifarma.RespuestaTXBean;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import oracle.jdbc.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityRecargaVirtual.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ       20.03.2014   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */

public class UtilityRecargaVirtual {

    private static final Logger log = LoggerFactory.getLogger(UtilityRecargaVirtual.class);
    private static boolean consejo = false;
    private FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();
    
    private static ArrayList parametros;

    /**
     * Constructor
     */
    public UtilityRecargaVirtual() {
    }

    public boolean validarConexionRecarga()
    {
        boolean pResultado = false;
        String pIndConexionRecarga;
        pIndConexionRecarga = cnxUtil.getIndLineaRecarga();
        if(pIndConexionRecarga.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            log.debug("No existe linea se cerrara la conexion ...");
            pResultado = false;
        }
        else
            pResultado = true;
        
        return pResultado;
    }


   
    
    // Cuantos intentos hara para la respuesta
    public static int getIntentoRespuesta(){
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        try {
            return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_RECARGA_VIRTUAL.F_NUM_INTENTOS_RESPUESTA(?,?)",
                                                               parametros);
        } catch (Exception e) {
            log.info("SQLException e " + e.getMessage());
            // Intento de Respuesta 4 Por Defecto
            return 4;
        }
    }
    
    
    // Cuanto tiempo esperar entre respuesta y respuesta
    // 3 Segundos es el mejor de los casos
    public static int leadTimeRespuesta(){
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        try {
            return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_RECARGA_VIRTUAL.F_LEAD_TIEMPO_RPTA(?,?)",
                                                               parametros);
        } catch (Exception e) {
            log.info("SQLException e " + e.getMessage());
            // Intento de Respuesta 4 Por Defecto
            return 3;
        }        
    }    
    
    public void gestionaSolicitudRecarga(String pTelefono, String pProveedor, String pMonto, String pTerminal, 
                                          String pProvincia, String codLocal, String numPedVta) throws Exception
    {
        int segundosEspera = leadTimeRespuesta();
        VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
        
        String pResInSolicitud = "N";
        // HAY CONEXION EN RECARGA
        if(validarConexionRecarga())
        {
            // Este metodo solo retornara 2 valores 
            // I si entro al inicio >> Si se qdo en el inicio o si entro algun exception.
            // E si ejecuto el Insert y dio commit ESTO SIEMPRE VA PASAR si ESTA EN E inserto en APPS
            pResInSolicitud = ingresaSolicitudDeRecarga(pTelefono, pProveedor, pMonto, pTerminal, 
                                                                     pProvincia, codLocal, numPedVta);
        try
        {
            // Procede a consultar el estado de la recarga y si hay respuesta
            // ESPERA LOS
            int intoConsulaRespuesta = getIntentoRespuesta();
            for (int i = 0; i < intoConsulaRespuesta; i++)
            {
                log.info("Consulta de Respuesta >> i>> " + i);
                // Espera los X segundos
                log.info("Espera " + segundosEspera + " " + ">> i>> " + i);
                try
                {
                    Thread.sleep(segundosEspera * 1000);
                }
                catch (InterruptedException e)
                {
                    log.info("InterruptedException>>" + e.getMessage());
                }
                catch (Exception q)
                {
                    log.info("Exception>>" + q.getMessage());
                }
                
                // Consulta Respuesta en XE_999
                if (validarConexionRecarga())
                {
                    VariablesVirtual.respuestaTXBean = respuestaSolicitudRecargaDAO(FarmaVariables.vCodGrupoCia, 
                                                                                    codLocal, 
                                                                                    numPedVta);
                }
                //Se obtuvo un Objeto y se obtuvo respuesta
                if (VariablesVirtual.respuestaTXBean != null)
                {   if (VariablesVirtual.respuestaTXBean.getCodigoRespuesta() != null)
                        break;
                }
            }
                
            String pRespuesta = "";
            try
            {
                pRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta().trim();
            }
            catch (Exception e)
            {
                log.error("",e);   
                pRespuesta = null;
            }
                
                // HAY CONEXION EN MATRIZ
            if(validarConexionRecarga())
            {
                log.info("HAY LINEA A MATRIZ");
                // SI LA RESPUESTA ES CERO >>> COBRA
                if(pRespuesta.equals("00"))
                {
                    log.info(".. RESPUESTA ES EXITO RECARGA .. COBRA PEDIDO");
                    log.info(".. RESPUESTA ES "+VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
                }
                else
                {
                    // Es null o codigo de error 
                    // NO DEBE DE COBRAR
                    
                    log.info(".. NO FUE EXITO LA RECARGA");
                    if (pRespuesta == null) 
                    {
                        if(pResInSolicitud.trim().equalsIgnoreCase("E"))
                        {
                            log.info("NO HAY RESPUESTA DE RECARGA COBRA EL PEDIDO Y AVISAR QUE VERIFIQUE LA RECARGA");
                            VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                            RespuestaTXBean bean = new RespuestaTXBean();
                            bean.setNumeroTrace(".");
                            bean.setCodigoRespuesta("000");
                            bean.setCodigoAprobacion(".");
                            Date fecha = new Date();
                            String campo12 = getCampo12(fecha);
                            String campo13 = getCampo13(fecha);
                            bean.setHoraTX(campo12);
                            bean.setFechaTX(campo13);
                            bean.setDescripcion("");
                            bean.setNumeroTarjeta("");
                            bean.setCodigoPIN("");
                            bean.setDatosImprimir("");
                            bean.setDescripcion("VERIFIQUE EN SU MODULO DE CONSULTA LA CONFIRMACION DE LA RECARGAS.");
                            VariablesVirtual.respuestaTXBean = bean;                            
                        }
                        else
                        {
                            // debe de cobrar xq no hay respuesta y no hay linea a APPS
                            log.info("NO HAY RESPUESTA DE RECARGA NO COBRA EL PEDIDO");
                            VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                            RespuestaTXBean bean = new RespuestaTXBean();
                            bean.setNumeroTrace(".");
                            bean.setCodigoRespuesta("666");
                            bean.setCodigoAprobacion(".");
                            Date fecha = new Date();
                            String campo12 = getCampo12(fecha);
                            String campo13 = getCampo13(fecha);
                            bean.setHoraTX(campo12);
                            bean.setFechaTX(campo13);
                            bean.setDescripcion("");
                            bean.setNumeroTarjeta("");
                            bean.setCodigoPIN("");
                            bean.setDatosImprimir("");
                            bean.setDescripcion("No Hay Respuesta de Recarga NO COBRA EL PEDIDO.");
                            VariablesVirtual.respuestaTXBean = bean;
                        }   
                    }
                    else
                    {
                        log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                        // Si hay Respuesta COBRAR SOLO SI ES EXITO
                        // SINO NO COBRAR
                        }
                     }
                }    
                else
                {
                    log.info("NO HAY LINEA A MATRIZ");
                    if (pRespuesta == null)
                    {
                        // debe de cobrar xq no hay respuesta y no hay linea a APPS
                        log.info("NO HAY RESPUESTA DE RECARGA COBRA EL PEDIDO Y AVISAR QUE VERIFIQUE LA RECARGA");
                        VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                        RespuestaTXBean bean = new RespuestaTXBean();
                        bean.setNumeroTrace(".");
                        bean.setCodigoRespuesta("000");
                        bean.setCodigoAprobacion(".");
                        Date fecha = new Date();
                        String campo12 = getCampo12(fecha);
                        String campo13 = getCampo13(fecha);
                        bean.setHoraTX(campo12);
                        bean.setFechaTX(campo13);
                        bean.setDescripcion("");
                        bean.setNumeroTarjeta("");
                        bean.setCodigoPIN("");
                        bean.setDatosImprimir("");
                        bean.setDescripcion("VERIFIQUE EN SU MODULO DE CONSULTA LA CONFIRMACION DE LA RECARGAS.");
                        VariablesVirtual.respuestaTXBean = bean;
                    }
                    else
                    {
                        if(pRespuesta == "00")
                        {
                            log.info(".. RESPUESTA ES EXITO RECARGA .. COBRA PEDIDO");
                        }
                        else
                        {
                            // Es null o codigo de error 
                            // NO DEBE DE COBRAR
                            log.info(".. NO FUE EXITO LA RECARGA");
                            if (pRespuesta == null)
                            {
                                if(pResInSolicitud.trim().equalsIgnoreCase("E"))
                                {
                                    log.info("NO HAY RESPUESTA DE RECARGA COBRA EL PEDIDO Y AVISAR QUE VERIFIQUE LA RECARGA");
                                    VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                                    RespuestaTXBean bean = new RespuestaTXBean();
                                    bean.setNumeroTrace(".");
                                    bean.setCodigoRespuesta("000");
                                    bean.setCodigoAprobacion(".");
                                    Date fecha = new Date();
                                    String campo12 = getCampo12(fecha);
                                    String campo13 = getCampo13(fecha);
                                    bean.setHoraTX(campo12);
                                    bean.setFechaTX(campo13);
                                    bean.setDescripcion("");
                                    bean.setNumeroTarjeta("");
                                    bean.setCodigoPIN("");
                                    bean.setDatosImprimir("");
                                    bean.setDescripcion("VERIFIQUE EN SU MODULO DE CONSULTA LA CONFIRMACION DE LA RECARGAS.");
                                    VariablesVirtual.respuestaTXBean = bean;
                                }
                                else
                                {
                                    // debe de cobrar xq no hay respuesta y no hay linea a APPS
                                    log.info("NO HAY RESPUESTA DE RECARGA NO COBRA EL PEDIDO");
                                    VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                                    RespuestaTXBean bean = new RespuestaTXBean();
                                    bean.setNumeroTrace(".");
                                    bean.setCodigoRespuesta("666");
                                    bean.setCodigoAprobacion(".");
                                    Date fecha = new Date();
                                    String campo12 = getCampo12(fecha);
                                    String campo13 = getCampo13(fecha);
                                    bean.setHoraTX(campo12);
                                    bean.setFechaTX(campo13);
                                    bean.setDescripcion("");
                                    bean.setNumeroTarjeta("");
                                    bean.setCodigoPIN("");
                                    bean.setDatosImprimir("");
                                    bean.setDescripcion("No Hay Respuesta de Recarga NO COBRA EL PEDIDO.");
                                    VariablesVirtual.respuestaTXBean = bean;
                                }
                            }
                            else
                            {
                                log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                                // Si hay Respuesta COBRAR SOLO SI ES EXITO
                                // SINO NO COBRAR
                            }
                        }
                    }
                }
                 
            } catch (Exception nfe) {
                // TODO: Add catch code
                log.info("PRINCIPAL Exception>>" + nfe.getMessage());
                if (pResInSolicitud.trim().equalsIgnoreCase("E")) {
                    log.info("El Insert a XE_999 se hizo >> Asume q es exito ya que no se toma riesgo de no cobrar..");
                    log.info("NO HAY RESPUESTA DE RECARGA COBRA EL PEDIDO Y AVISAR QUE VERIFIQUE LA RECARGA");
                    VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                    RespuestaTXBean bean = new RespuestaTXBean();
                    bean.setNumeroTrace(".");
                    bean.setCodigoRespuesta("000");
                    bean.setCodigoAprobacion(".");
                    Date fecha = new Date();
                    String campo12 = getCampo12(fecha);
                    String campo13 = getCampo13(fecha);
                    bean.setHoraTX(campo12);
                    bean.setFechaTX(campo13);
                    bean.setDescripcion("");
                    bean.setNumeroTarjeta("");
                    bean.setCodigoPIN("");
                    bean.setDatosImprimir("");
                    bean.setDescripcion("VERIFIQUE EN SU MODULO DE CONSULTA LA CONFIRMACION DE LA RECARGAS.");                    
                    VariablesVirtual.respuestaTXBean = bean;        
                }
            }
            
      }
        else {
            throw new Exception("No Hay Conexion Con el Servicio de Recarga"+"@"+
                                "Inténtelo nuevamente"+"@"+
                                "Si persiste el error, llame a Mesa de Ayuda"    
                                );
        }

        
    }
    
    /**
     * Gestionar la solicitud de anulación de recarga virtual
     * @author ASOSA
     * @since 09/07/2014
     * @param pTelefono
     * @param pProveedor
     * @param pMonto
     * @param pTerminal
     * @param pProvincia
     * @param codLocal
     * @param numPedVta
     * @throws Exception
     */
    public void gestionaSolAnulRecarga(String pTelefono, String pProveedor, String pMonto, String pTerminal, 
                                          String pProvincia, String codLocal, String numPedVta) throws Exception
    {
        int segundosEspera = leadTimeRespuesta();
        VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
        
        String pResInSolicitud = "N";
        // HAY CONEXION EN RECARGA
        if(validarConexionRecarga())
        {
            // Este metodo solo retornara 2 valores 
            // I si entro al inicio >> Si se qdo en el inicio o si entro algun exception.
            // E si ejecuto el Insert y dio commit ESTO SIEMPRE VA PASAR si ESTA EN E inserto en APPS
            pResInSolicitud = ingresaSolAnulRecarga(pTelefono, pProveedor, pMonto, pTerminal, 
                                                                     pProvincia, codLocal, numPedVta);
        try
        {
            // Procede a consultar el estado de la recarga y si hay respuesta
            // ESPERA LOS
            int intoConsulaRespuesta = getIntentoRespuesta();
            for (int i = 0; i < intoConsulaRespuesta; i++)
            {
                log.info("Consulta de Respuesta >> i>> " + i);
                // Espera los X segundos
                log.info("Espera " + segundosEspera + " " + ">> i>> " + i);
                try
                {
                    Thread.sleep(segundosEspera * 1000);
                }
                catch (InterruptedException e)
                {
                    log.info("InterruptedException>>" + e.getMessage());
                }
                catch (Exception q)
                {
                    log.info("Exception>>" + q.getMessage());
                }
                
                // Consulta Respuesta en XE_999
                if (validarConexionRecarga())
                {
                    VariablesVirtual.respuestaTXBean = respuestaSolicitudAnulacionDAO(FarmaVariables.vCodGrupoCia, 
                                                                                    codLocal, 
                                                                                    numPedVta);
                }
                //Se obtuvo un Objeto y se obtuvo respuesta
                if (VariablesVirtual.respuestaTXBean != null)
                {   if (VariablesVirtual.respuestaTXBean.getCodigoRespuesta() != null)
                        break;
                }
            }
                
            String pRespuesta = "";
            try
            {
                pRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta().trim();
                log.info("RESPUESTA DE DEMONIO: " + pRespuesta);
            }
            catch (Exception e)
            {
                log.error("",e);   
                pRespuesta = null;
            }
                
                // HAY CONEXION EN MATRIZ
            if(validarConexionRecarga())
            {
                log.info("HAY LINEA A MATRIZ");
                // SI LA RESPUESTA ES CERO >>> COBRA
                if(pRespuesta.equals("00"))
                {
                    log.info(".. RESPUESTA ES EXITO anulacion RECARGA .. PEDIDO");
                    log.info(".. RESPUESTA ES "+VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
                }
                else
                {
                    // Es null o codigo de error 
                    // NO DEBE DE COBRAR
                    
                    log.info(".. NO FUE EXITO LA anulacion de la RECARGA");
                    if (pRespuesta == null) 
                    {
                        if(pResInSolicitud.trim().equalsIgnoreCase("E"))
                        {
                            log.info("NO HAY RESPUESTA DE LA ANULACION DEL PEDIDO, AVISAR QUE VERIFIQUE LA ANULACION DE LA RECARGA");
                            VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                            RespuestaTXBean bean = new RespuestaTXBean();
                            bean.setNumeroTrace(".");
                            bean.setCodigoRespuesta("000");
                            bean.setCodigoAprobacion(".");
                            Date fecha = new Date();
                            String campo12 = getCampo12(fecha);
                            String campo13 = getCampo13(fecha);
                            bean.setHoraTX(campo12);
                            bean.setFechaTX(campo13);
                            bean.setDescripcion("");
                            bean.setNumeroTarjeta("");
                            bean.setCodigoPIN("");
                            bean.setDatosImprimir("");
                            bean.setDescripcion("VERIFIQUE LA ANULACION DE LA RECARGA.");
                            VariablesVirtual.respuestaTXBean = bean;                            
                        }
                        else
                        {
                            // debe de cobrar xq no hay respuesta y no hay linea a APPS
                            log.info("NO HAY RESPUESTA DE RECARGA NO COBRA EL PEDIDO");
                            VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                            RespuestaTXBean bean = new RespuestaTXBean();
                            bean.setNumeroTrace(".");
                            bean.setCodigoRespuesta("666");
                            bean.setCodigoAprobacion(".");
                            Date fecha = new Date();
                            String campo12 = getCampo12(fecha);
                            String campo13 = getCampo13(fecha);
                            bean.setHoraTX(campo12);
                            bean.setFechaTX(campo13);
                            bean.setDescripcion("");
                            bean.setNumeroTarjeta("");
                            bean.setCodigoPIN("");
                            bean.setDatosImprimir("");
                            bean.setDescripcion("No Hay Respuesta de Recarga NO COBRA EL PEDIDO.");
                            VariablesVirtual.respuestaTXBean = bean;
                        }   
                    }
                    else
                    {
                        log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                        // Si hay Respuesta COBRAR SOLO SI ES EXITO
                        // SINO NO COBRAR
                        }
                     }
                }    
                else
                {
                    log.info("NO HAY LINEA A MATRIZ");
                    if (pRespuesta == null)
                    {
                        // debe de cobrar xq no hay respuesta y no hay linea a APPS
                        log.info("NO HAY RESPUESTA DE LA ANULACION DEL PEDIDO, AVISAR QUE VERIFIQUE LA ANULACION DE LA RECARGA");
                        VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                        RespuestaTXBean bean = new RespuestaTXBean();
                        bean.setNumeroTrace(".");
                        bean.setCodigoRespuesta("000");
                        bean.setCodigoAprobacion(".");
                        Date fecha = new Date();
                        String campo12 = getCampo12(fecha);
                        String campo13 = getCampo13(fecha);
                        bean.setHoraTX(campo12);
                        bean.setFechaTX(campo13);
                        bean.setDescripcion("");
                        bean.setNumeroTarjeta("");
                        bean.setCodigoPIN("");
                        bean.setDatosImprimir("");
                        bean.setDescripcion("VERIFIQUE LA ANULACION DE LA RECARGA.");
                        VariablesVirtual.respuestaTXBean = bean;
                    }
                    else
                    {
                        if(pRespuesta == "00")
                        {
                            log.info(".. RESPUESTA ES EXITO ANULACION .. COBRA PEDIDO");
                        }
                        else
                        {
                            // Es null o codigo de error 
                            // NO DEBE DE COBRAR
                            log.info(".. NO FUE EXITO LA ANULACION");
                            if (pRespuesta == null)
                            {
                                if(pResInSolicitud.trim().equalsIgnoreCase("E"))
                                {
                                    log.info("NO HAY RESPUESTA DE LA ANULACION DEL PEDIDO, AVISAR QUE VERIFIQUE LA ANULACION DE LA RECARGA");
                                    VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                                    RespuestaTXBean bean = new RespuestaTXBean();
                                    bean.setNumeroTrace(".");
                                    bean.setCodigoRespuesta("000");
                                    bean.setCodigoAprobacion(".");
                                    Date fecha = new Date();
                                    String campo12 = getCampo12(fecha);
                                    String campo13 = getCampo13(fecha);
                                    bean.setHoraTX(campo12);
                                    bean.setFechaTX(campo13);
                                    bean.setDescripcion("");
                                    bean.setNumeroTarjeta("");
                                    bean.setCodigoPIN("");
                                    bean.setDatosImprimir("");
                                    bean.setDescripcion("VERIFIQUE LA ANULACION DE LA RECARGA.");
                                    VariablesVirtual.respuestaTXBean = bean;
                                }
                                else
                                {
                                    // debe de cobrar xq no hay respuesta y no hay linea a APPS
                                    log.info("NO HAY RESPUESTA DE DEL SERVIDOR");
                                    VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                                    RespuestaTXBean bean = new RespuestaTXBean();
                                    bean.setNumeroTrace(".");
                                    bean.setCodigoRespuesta("666");
                                    bean.setCodigoAprobacion(".");
                                    Date fecha = new Date();
                                    String campo12 = getCampo12(fecha);
                                    String campo13 = getCampo13(fecha);
                                    bean.setHoraTX(campo12);
                                    bean.setFechaTX(campo13);
                                    bean.setDescripcion("");
                                    bean.setNumeroTarjeta("");
                                    bean.setCodigoPIN("");
                                    bean.setDatosImprimir("");
                                    bean.setDescripcion("No Hay Respuesta de ANULACION.");
                                    VariablesVirtual.respuestaTXBean = bean;
                                }
                            }
                            else
                            {
                                log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                                // Si hay Respuesta COBRAR SOLO SI ES EXITO
                                // SINO NO COBRAR
                            }
                        }
                    }
                }
                 
            } catch (Exception nfe) {
                // TODO: Add catch code
                log.info("PRINCIPAL Exception>>" + nfe.getMessage());
                if (pResInSolicitud.trim().equalsIgnoreCase("E")) {
                    log.info("El Insert a XE_999 se hizo >> Asume q es exito ya que no se toma riesgo de no cobrar..");
                    log.info("NO HAY RESPUESTA DE LA ANULACION DEL PEDIDO, AVISAR QUE VERIFIQUE LA ANULACION DE LA RECARGA");
                    VariablesVirtual.respuestaTXBean = new RespuestaTXBean();
                    RespuestaTXBean bean = new RespuestaTXBean();
                    bean.setNumeroTrace(".");
                    bean.setCodigoRespuesta("000");
                    bean.setCodigoAprobacion(".");
                    Date fecha = new Date();
                    String campo12 = getCampo12(fecha);
                    String campo13 = getCampo13(fecha);
                    bean.setHoraTX(campo12);
                    bean.setFechaTX(campo13);
                    bean.setDescripcion("");
                    bean.setNumeroTarjeta("");
                    bean.setCodigoPIN("");
                    bean.setDatosImprimir("");
                    bean.setDescripcion("VERIFIQUE LA ANULACION DE LA RECARGA.");                    
                    VariablesVirtual.respuestaTXBean = bean;        
                }
            }
            
      }
        else {
            throw new Exception("No Hay Conexion Con el Servicio de Recarga"+"@"+
                                "Inténtelo nuevamente"+"@"+
                                "Si persiste el error, llame a Mesa de Ayuda"    
                                );
        }

        
    }
    
    
    private static String getCampo12(Date fecha)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
            formatter.setLenient(false);
            String s = formatter.format(fecha);
            return s;
        }
        catch(Exception e)
        {
            String s1 = null;
            return s1;
        }
    }
    
    private static String getCampo13(Date fecha)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
            formatter.setLenient(false);
            String s = formatter.format(fecha);
            return s;
        }
        catch(Exception e)
        {
            String s1 = null;
            return s1;
        }
    }    

    public String ingresaSolicitudDeRecarga(String pTelefono, String pProveedor, String pMonto, String pTerminal, 
                                          String pProvincia, String codLocal, String numPedVta)
    {
        String  pResultado = "I";
        
        if(validarConexionRecarga())
        {
            //// Carga Bean de Solicitud de Recarga ////
            //// Carga Bean de Solicitud de Recarga ////
            log.info(">>  ingresaSolicitudDeRecarga");
            PeticionSolicitudCompra obj_pet = new PeticionSolicitudCompra();
            Date fecha = new Date();
            String campo12 = getCampo12(fecha);
            String campo13 = getCampo13(fecha);
            String terminalID = pTerminal;
            String terminalState = pProvincia;
            
            obj_pet.set_04((int)Double.parseDouble(pMonto));
            obj_pet.set_12(campo12);
            obj_pet.set_13(campo13);
            obj_pet.set_41(pTerminal);
            obj_pet.set_43("MIFARMA               " + terminalID + terminalState + "PE");
            obj_pet.set_48(pProveedor + pTelefono);
            obj_pet.set_49("604");
            obj_pet.set_Cod_local(codLocal);
            obj_pet.set_Num_ped_vta(numPedVta);        
            //// Carga Bean de Solicitud de Recarga ////
            //// Carga Bean de Solicitud de Recarga ////
            Connection conn= cnxUtil.getConexionRecarga();
            
            // Agrega la solcitud en ADM XE_999
            try
            {
                CallableStatement stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_P_AGREGA_SOLICITUD(" +
                                                              "?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");            
                stmt.registerOutParameter(1, OracleTypes.NUMBER);
                stmt.setString(2, "200");
                stmt.setInt(3, obj_pet.get_04());            
                stmt.setString(4, ""+obj_pet.get_12());
                stmt.setString(5, ""+obj_pet.get_13());        
                stmt.setString(6, ""+obj_pet.get_41());
                stmt.setString(7, ""+obj_pet.get_43());
                stmt.setString(8, ""+obj_pet.get_48());
                stmt.setString(9, ""+obj_pet.get_49());
                stmt.setString(10, "1");
                stmt.setString(11, "SIX_JDBC");
                stmt.setString(12, ""+obj_pet.get_Cod_local());
                stmt.setString(13, ""+obj_pet.get_Num_ped_vta());
                //INI ASOSA - 13/07/2014
                stmt.setString(14, VariablesVirtual.vTipoRcd); 
                stmt.setString(15, VariablesVirtual.vCodTipoProducto); 
                //FIN ASOSA - 13/07/2014
                
                stmt.execute();
                conn.commit();    
                // EJECUTADO
                pResultado = "E";
                stmt.close(); 
                
                if(conn.isClosed())
                    conn.close();
                    
            }
            catch (SQLException e)
            {
                try {
                    if (conn.isClosed())
                        conn.close();
                    
                } catch (Exception sqle) {
                    log.error("",sqle);   
                }
                conn = null;            
                //log.debug("ERROR solicitudRecargaDAO "+e);
                log.info("ERROR solicitudRecargaDAO SQLException "+e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                     FarmaVariables.vCodLocal,
                                     VariablesPtoVenta.vDestEmailErrorCobro,
                                     "Error de ingresaSolicitudDeRecarga",
                                     "Error de ingresaSolicitudDeRecarga",
                                     "SQL EXCEPTION" + "<br>"+ 
                                     "Inténtelo nuevamente." + "<br>"+ 
                                     "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                     "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                     "NumPedVta :"+numPedVta + "<br>"+ 
                                     "Error: " + "Conexion :"+e.getMessage() ,
                                     "");                    
            }
            catch (Exception eq)
            {
                try {
                    if (conn.isClosed())
                        conn.close();
                    
                } catch (Exception sqle) {
                    log.error("",sqle);   
                }
                conn = null;                            
                pResultado = "E";
                //log.debug("ERROR solicitudRecargaDAO "+e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                 FarmaVariables.vCodLocal,
                                 VariablesPtoVenta.vDestEmailErrorCobro,
                                 "Error de ingresaSolicitudDeRecarga",
                                 "Error de ingresaSolicitudDeRecarga",
                                 "Exception" + "<br>"+ 
                                 "Inténtelo nuevamente." + "<br>"+ 
                                 "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                 "IP PC: " + FarmaVariables.vIpPc + "<br>"+
                                 "NumPedVta :"+numPedVta + "<br>"+ 
                                 "Error: " + "Conexion :"+eq.getMessage() ,
                                 "");                                    
                log.info("ERROR solicitudRecargaDAO Exception "+eq);
            }
        }
        // EJECUTADO
        return pResultado;
    }
    
    /**
     * Ingresa la solicitud de anulación de recarga
     * @author ASOSA
     * @since 09/07/2014
     * @param pTelefono
     * @param pProveedor
     * @param pMonto
     * @param pTerminal
     * @param pProvincia
     * @param codLocal
     * @param numPedVta
     * @return
     */
    public String ingresaSolAnulRecarga(String pTelefono, String pProveedor, String pMonto, String pTerminal, 
                                          String pProvincia, String codLocal, String numPedVta)
    {
        String  pResultado = "I";
        
        if(validarConexionRecarga())
        {
            //// Carga Bean de Solicitud de Recarga ////
            log.info(">>  ingresaSolAnulRecarga");
            PeticionSolicitudCompra obj_pet = new PeticionSolicitudCompra();
            Date fecha = new Date();
            String campo12 = getCampo12(fecha);
            String campo13 = getCampo13(fecha);
            String terminalID = pTerminal;
            String terminalState = pProvincia;
            
            obj_pet.set_04((int)Double.parseDouble(pMonto));
            obj_pet.set_12(campo12);
            obj_pet.set_13(campo13);
            obj_pet.set_41(pTerminal);
            obj_pet.set_43("MIFARMA               " + terminalID + terminalState + "PE");
            obj_pet.set_48(pProveedor + pTelefono);
            obj_pet.set_49("604");
            obj_pet.set_Cod_local(codLocal);
            obj_pet.set_Num_ped_vta(numPedVta);        
            //// Carga Bean de Solicitud de Recarga ////
            //// Carga Bean de Solicitud de Recarga ////
            
            Connection conn= cnxUtil.getConexionRecarga();
            
            // Agrega la solcitud en ADM XE_999
            try
            {
                CallableStatement stmt = conn.prepareCall("{call PACK_SIX_RECARGA.ADM_P_AGREGA_ANULACION(" +
                                                              "?,?,?,?) }");
                
                stmt.setString(1, FarmaVariables.vCodGrupoCia);
                stmt.setString(2, FarmaVariables.vCodLocal);            
                stmt.setString(3, VariablesCaja.vNumPedVta_Anul);
                stmt.setString(4, "200");   
                
                log.info("vCodGrupoCia: "+FarmaVariables.vCodGrupoCia);
                log.info("vCodLocal: "+FarmaVariables.vCodLocal);
                log.info("vNumPedVta_Anul: "+VariablesCaja.vNumPedVta_Anul);
                log.info("tipo_mensaje "+"200");
                
                stmt.executeUpdate();
                conn.commit();    
                // EJECUTADO
                pResultado = "E";
                stmt.close(); 
                
                if(conn.isClosed())
                    conn.close();
                    
            }
            catch (SQLException e)
            {
                try {
                    if (conn.isClosed())
                        conn.close();
                    
                } catch (Exception sqle) {
                    log.error("",sqle);   
                }
                conn = null;            
                //log.debug("ERROR solicitudRecargaDAO "+e);
                log.info("ERROR ingresarSolAnulRecarga SQLException "+e);
                log.debug("ERROR ingresarSolAnulRecarga SQLException "+e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                     FarmaVariables.vCodLocal,
                                     VariablesPtoVenta.vDestEmailErrorCobro,
                                     "Error de ingresarSolAnulRecarga",
                                     "Error de ingresarSolAnulRecarga",
                                     "SQL EXCEPTION" + "<br>"+ 
                                     "Inténtelo nuevamente." + "<br>"+ 
                                     "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                     "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                     "NumPedVta :"+numPedVta + "<br>"+ 
                                     "Error: " + "Conexion :"+e.getMessage() ,
                                     "");                    
            }
            catch (Exception eq)
            {
                try {
                    if (conn.isClosed())
                        conn.close();
                    
                } catch (Exception sqle) {
                    log.error("",sqle);   
                }
                conn = null;                            
                pResultado = "E";
                //log.debug("ERROR solicitudRecargaDAO "+e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                 FarmaVariables.vCodLocal,
                                 VariablesPtoVenta.vDestEmailErrorCobro,
                                 "Error de ingresarSolAnulRecarga",
                                 "Error de ingresarSolAnulRecarga",
                                 "Exception" + "<br>"+ 
                                 "Inténtelo nuevamente." + "<br>"+ 
                                 "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                 "IP PC: " + FarmaVariables.vIpPc + "<br>"+
                                 "NumPedVta :"+numPedVta + "<br>"+ 
                                 "Error: " + "Conexion :"+eq.getMessage() ,
                                 "");                                    
                log.info("ERROR ingresarSolAnulRecarga Exception "+eq);
            }
        }
        // EJECUTADO
        return pResultado;
    }
    
    public RespuestaTXBean respuestaSolicitudRecargaDAO(String pCodGrupoCia,String pCodLocal,String pNumPedVta)
    {
        String codigo = pCodGrupoCia+"-"+pCodLocal+"-"+pNumPedVta;
        log.info("Inicio metodo respuestaSolicitudRecargaDAO Pedido "+codigo);
        RespuestaTXBean bean= new RespuestaTXBean();
        Connection conn= cnxUtil.getConexionRecarga();
        try
        {
            //Connection conn= cnxUtil.getConexionRecarga();
            RespuestaSolicitudCompra obj_res = new RespuestaSolicitudCompra();  
            log.info("ANTES get Respuesta XE_999 EN BD respuesta Pedido "+codigo);
                CallableStatement stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_GET_ENVIA_RPTA_FV(?,?,?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, pCodGrupoCia);
            stmt.setString(3, pCodLocal);
            stmt.setString(4, pNumPedVta);
            stmt.execute();
            log.info("DESPUES get Respuesta XE_999 EN BD respuesta Pedido "+codigo);
            ResultSet results = (ResultSet)stmt.getObject(1);              
            if(results.next())
            {
                obj_res = new RespuestaSolicitudCompra();                 
                obj_res.set_37(""+results.getString(1));
                obj_res.set_38(""+results.getString(2));
                obj_res.set_39(""+results.getString(3));
                obj_res.set_48(""+results.getString(4));
                obj_res.set_62(""+results.getString(5));
                obj_res.set_Cod_local(""+results.getString(6));
                obj_res.set_Num_ped_vta(""+results.getString(7));
                
                Date fecha = new Date();
                String campo12 = getCampo12(fecha);
                String campo13 = getCampo13(fecha);
                  
                bean.setNumeroTrace(""+results.getString(1));
                bean.setCodigoRespuesta(""+results.getString(3));
                bean.setCodigoAprobacion(""+results.getString(2));
                bean.setHoraTX(campo12);
                bean.setFechaTX(campo13);
                bean.setDescripcion(""+results.getString(5));
                bean.setNumeroTarjeta(""+results.getString(5));
                bean.setCodigoPIN(""+results.getString(5));
                bean.setDatosImprimir(""+results.getString(5));
                bean.setDescripcion(""+results.getString(5));
                //INI - 17/07/2014
                VariablesVirtual.vValorMinimo = ""+results.getString(8);
                VariablesVirtual.vValorMaximo = ""+results.getString(9);
                //FIN - 17/07/2014
                //if(!bean.getCodigoRespuesta().equals("00"))
                //bean.setDescripcion(MesajeErrorBprepaid.getMensajeError(Integer.parseInt(bean.getCodigoRespuesta())));
            }
            else
            {
                obj_res=null;    
            }              
            results.close();
            stmt.close();
            if(conn.isClosed())
                conn.close();
            conn = null;
            log.info("OK respuestaSolicitudRecargaDAO Codigo :"+codigo+" - "+obj_res);
            return bean;
        }
        catch (SQLException e)
        {
            try {
                if (conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.error("",sqle);   
            }
            conn = null;                        
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                       FarmaVariables.vCodLocal,
                                       VariablesPtoVenta.vDestEmailErrorCobro,
                                       "Error de respuestaSolicitudRecargaDAO",
                                       "Error de respuestaSolicitudRecargaDAO",
                                       "Exception" + "<br>"+ 
                                       "Inténtelo nuevamente." + "<br>"+ 
                                       "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                       "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                       "NumPedVta :"+pNumPedVta + "<br>"+ 
                                       "Error: " + "Conexion :"+e.getMessage() ,
                                       "");
            log.info("ERROR SQLException respuestaSolicitudRecargaDAO Codigo : "+codigo+" <> "+e.getMessage());
            return null;
        }
        catch (Exception eq)
        {
            try {
                if (conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.error("",sqle);   
            }
            conn = null;            
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                           FarmaVariables.vCodLocal,
                           VariablesPtoVenta.vDestEmailErrorCobro,
                           "Error de respuestaSolicitudRecargaDAO",
                           "Error de respuestaSolicitudRecargaDAO",
                           "Exception" + "<br>"+ 
                           "Inténtelo nuevamente." + "<br>"+ 
                           "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                           "IP PC: " + FarmaVariables.vIpPc + "<br>"+
                           "NumPedVta :"+pNumPedVta + "<br>"+ 
                           "Error: " + "Conexion :"+eq.getMessage() ,
                           "");                 
            log.info("ERROR Exception respuestaSolicitudRecargaDAO Codigo : "+codigo+" <> "+eq.getMessage());
            return null;
        }
    }
    
    /**
     * Devuelve la respuesta de la anulacion
     * @author ASOSA
     * @since 10/07/2014
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @return
     */
    public RespuestaTXBean respuestaSolicitudAnulacionDAO(String pCodGrupoCia,String pCodLocal,String pNumPedVta)
    {
        String codigo = pCodGrupoCia+"-"+pCodLocal+"-"+pNumPedVta;
        log.info("Inicio metodo respuestaSolicitudRecargaDAO Pedido "+codigo);
        RespuestaTXBean bean= new RespuestaTXBean();
        Connection conn= cnxUtil.getConexionRecarga();
        try
        {
            //Connection conn= cnxUtil.getConexionRecarga();
            RespuestaSolicitudCompra obj_res = new RespuestaSolicitudCompra();  
            log.info("ANTES get Respuesta XE_999 EN BD respuesta Pedido "+codigo);
                CallableStatement stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_GET_RPTA_ANUL_FV(?,?,?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, pCodGrupoCia);
            stmt.setString(3, pCodLocal);
            stmt.setString(4, pNumPedVta);
            log.info("PARAM 01: " + pCodGrupoCia);
            log.info("PARAM 02: " + pCodLocal);
            log.info("PARAM 03: " + pNumPedVta);
            stmt.execute();
            log.info("DESPUES get Respuesta XE_999 EN BD respuesta Pedido "+codigo);
            ResultSet results = (ResultSet)stmt.getObject(1);              
            if(results.next())
            {
                obj_res = new RespuestaSolicitudCompra();                 
                obj_res.set_37(""+results.getString(1));
                obj_res.set_38(""+results.getString(2));
                obj_res.set_39(""+results.getString(3));
                obj_res.set_48(""+results.getString(4));
                obj_res.set_62(""+results.getString(5));
                obj_res.set_Cod_local(""+results.getString(6));
                obj_res.set_Num_ped_vta(""+results.getString(7));
                
                Date fecha = new Date();
                String campo12 = getCampo12(fecha);
                String campo13 = getCampo13(fecha);
                  
                bean.setNumeroTrace(""+results.getString(1));
                bean.setCodigoRespuesta(""+results.getString(3));
                bean.setCodigoAprobacion(""+results.getString(2));
                bean.setHoraTX(campo12);
                bean.setFechaTX(campo13);
                bean.setDescripcion(""+results.getString(5));
                bean.setNumeroTarjeta(""+results.getString(5));
                bean.setCodigoPIN(""+results.getString(5));
                bean.setDatosImprimir(""+results.getString(5));
                bean.setDescripcion(""+results.getString(5));
                //if(!bean.getCodigoRespuesta().equals("00"))
                //bean.setDescripcion(MesajeErrorBprepaid.getMensajeError(Integer.parseInt(bean.getCodigoRespuesta())));
            }
            else
            {
                obj_res=null;    
            }              
            results.close();
            stmt.close();
            if(conn.isClosed())
                conn.close();
            conn = null;
            log.info("OK respuestaSolicitudRecargaDAO Codigo :"+codigo+" - "+obj_res);
            return bean;
        }
        catch (SQLException e)
        {
            try {
                if (conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.error("",sqle);   
            }
            conn = null;                        
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                       FarmaVariables.vCodLocal,
                                       VariablesPtoVenta.vDestEmailErrorCobro,
                                       "Error de respuestaSolicitudRecargaDAO",
                                       "Error de respuestaSolicitudRecargaDAO",
                                       "Exception" + "<br>"+ 
                                       "Inténtelo nuevamente." + "<br>"+ 
                                       "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                       "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                       "NumPedVta :"+pNumPedVta + "<br>"+ 
                                       "Error: " + "Conexion :"+e.getMessage() ,
                                       "");
            log.info("ERROR SQLException respuestaSolicitudRecargaDAO Codigo : "+codigo+" <> "+e.getMessage());
            return null;
        }
        catch (Exception eq)
        {
            try {
                if (conn.isClosed())
                    conn.close();
                
            } catch (Exception sqle) {
                log.error("",sqle);   
            }
            conn = null;            
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                           FarmaVariables.vCodLocal,
                           VariablesPtoVenta.vDestEmailErrorCobro,
                           "Error de respuestaSolicitudRecargaDAO",
                           "Error de respuestaSolicitudRecargaDAO",
                           "Exception" + "<br>"+ 
                           "Inténtelo nuevamente." + "<br>"+ 
                           "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                           "IP PC: " + FarmaVariables.vIpPc + "<br>"+
                           "NumPedVta :"+pNumPedVta + "<br>"+ 
                           "Error: " + "Conexion :"+eq.getMessage() ,
                           "");                 
            log.info("ERROR Exception respuestaSolicitudRecargaDAO Codigo : "+codigo+" <> "+eq.getMessage());
            return null;
        }
    }
    
    //LLEIVA 27-Mar-2014
    public String obtieneMensajeRecarga(String pNumPedVta,
                                        String fechaPedido)
    {
        String respuesta = "";
        Connection conn;
        CallableStatement stmt;
        Object results;
        try
        {
            if(validarConexionRecarga())
            {
                //Se obtiene la conexión y retorna los datos de la recarga
                conn = cnxUtil.getConexionRecarga();
                stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_OBT_INFO_RECARGA(?,?,?,?,?,?)}");
                stmt.registerOutParameter(1, OracleTypes.VARCHAR);
                stmt.setString(2, FarmaVariables.vCodGrupoCia);
                stmt.setString(3, FarmaVariables.vCodCia);
                stmt.setString(4, FarmaVariables.vCodLocal);
                stmt.setString(5, pNumPedVta);
                stmt.setString(6, "9999999");
                stmt.setString(7, fechaPedido.substring(0, 10));
                stmt.execute();
                
                respuesta = stmt.getObject(1).toString();
    
                stmt.close();
                if(conn.isClosed())
                    conn.close();
                conn = null;
            }
            else
                respuesta = "ERROR BD: No se puede establecer conexion con el servidor central";
        }
        catch(Exception e)
        {   log.info("",e);
            respuesta = "ERROR";
            conn = null;
        }
        return respuesta;
    }
    
    //LLEIVA 27-Mar-2014
    public String obtieneMensajeRecargaAnul(String pNumPedVta,
                                        String fechaPedido)
    {
        String respuesta = "";
        Connection conn;
        CallableStatement stmt;
        Object results;
        try
        {
            if(validarConexionRecarga())
            {
                //Se obtiene la conexión y retorna los datos de la recarga
                conn = cnxUtil.getConexionRecarga();
                stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_PERMITE_ANUL_PED_RECARGA(?,?,?,?,?,?)}");
                stmt.registerOutParameter(1, OracleTypes.VARCHAR);
                stmt.setString(2, FarmaVariables.vCodGrupoCia);
                stmt.setString(3, FarmaVariables.vCodCia);
                stmt.setString(4, FarmaVariables.vCodLocal);
                stmt.setString(5, pNumPedVta);
                stmt.setString(6, "9999999");
                stmt.setString(7, fechaPedido.substring(0, 10));
                stmt.execute();
                
                respuesta = stmt.getObject(1).toString();
    
                stmt.close();
                if(conn.isClosed())
                    conn.close();
                conn = null;
            }
            else
                respuesta = "ERROR BD: No se puede establecer conexion con el servidor central";
        }
        catch(Exception e)
        {   log.info("",e);
            respuesta = "ERROR BD:"+e.getMessage();
            conn = null;
            
        }
        return respuesta;
    }
    
    /**
     * Obtiene info pedido virtual
     * @author ASOSA
     * @since 13/07/2014
     * @throws Exception
     */
    public static void obtieneInfoPedidoVirtual() throws Exception
    {
        try
        {   DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                            VariablesCaja.vNumPedVta);
            log.debug("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
        }
        catch (SQLException sql)
        {
            log.error("ERROR en obtieneInfoPedidoVirtual: ",sql);
            throw new Exception("Error al obtener informacion del pedido virtual - \n" + sql);
        }
    }
    
    /**
     * Coloca informacion de pedido virtual para registrar.
     * @author ASOSA
     * @since 13/07/2014
     * @throws Exception
     */
    public static void colocaInfoPedidoVirtualGrabar()throws Exception
    {
           ArrayList temp = VariablesVirtual.vArrayList_InfoProdVirtual;
           VariablesCaja.vCodProd = FarmaUtility.getValueFieldArrayList(temp, 0, 0);
           VariablesCaja.vTipoProdVirtual = FarmaUtility.getValueFieldArrayList(temp, 0, 1);
           VariablesCaja.vPrecioProdVirtual = FarmaUtility.getValueFieldArrayList(temp, 0, 2);
           VariablesCaja.vNumeroCelular = FarmaUtility.getValueFieldArrayList(temp, 0, 3);
           VariablesCaja.vCodigoProv = FarmaUtility.getValueFieldArrayList(temp, 0, 4);
           VariablesCaja.vTipoTarjeta = FarmaUtility.getValueFieldArrayList(temp, 0, 7);        
           VariablesCaja.vTipoRcd = FarmaUtility.getValueFieldArrayList(temp, 0, 10);
           VariablesCaja.vCodTipoProducto = FarmaUtility.getValueFieldArrayList(temp, 0, 11);
    }
    
public String getMensajeRPTRecargaPedido(String pNumPedVta,
                                        String fechaPedido)
    {
        String respuesta = "";
        Connection conn;
        CallableStatement stmt;
        Object results;
        try
        {
            if(validarConexionRecarga())
            {
                //Se obtiene la conexión y retorna los datos de la recarga
                conn = cnxUtil.getConexionRecarga();
                stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.F_RPTA_RECARGA_PEDIDO(?,?,?,?,?)}");
                stmt.registerOutParameter(1, OracleTypes.VARCHAR);
                stmt.setString(2, FarmaVariables.vCodGrupoCia);
                stmt.setString(3, FarmaVariables.vCodCia);
                stmt.setString(4, FarmaVariables.vCodLocal);
                stmt.setString(5, pNumPedVta);
                stmt.setString(6, fechaPedido.substring(0, 10));
                stmt.execute();
                
                respuesta = stmt.getObject(1).toString();
    
                stmt.close();
                if(conn.isClosed())
                    conn.close();
                conn = null;
            }
            else
                respuesta = "ERROR BD: No se puede establecer conexion con el servidor central";
        }
        catch(Exception e)
        {   log.info("",e);
            respuesta = "ERROR>"+e.getMessage();
            conn = null;
        }
        return respuesta;
    }
}