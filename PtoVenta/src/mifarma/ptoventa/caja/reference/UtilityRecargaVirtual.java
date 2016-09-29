package mifarma.ptoventa.caja.reference;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.gs.mifarma.RespuestaTXBean;

import com.mifarma.local.ClienteIntegradorRecargas;
import com.mifarma.query.builder.ClienteIntegrador;
import com.mifarma.query.builder.QueryBuilder;
import com.mifarma.query.request.QueryParams;
import com.mifarma.query.response.QueryStatus;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import static mifarma.ptoventa.main.DlgProcesar.getIndGestorTx;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import static mifarma.ptoventa.reference.VariablesPtoVenta.conexionGTX;

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
    
    private FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();

    private static ArrayList parametros;

    /**
     * Constructor
     */
    public UtilityRecargaVirtual() {
    }

    public boolean validarConexionRecarga() {
        boolean pResultado = false;
        String pIndConexionRecarga;
        pIndConexionRecarga = cnxUtil.getIndLineaRecarga();
        if (pIndConexionRecarga.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            log.debug("No existe linea se cerrara la conexion ...");
            pResultado = false;
        } else
            pResultado = true;

        return pResultado;
    }


    // Cuantos intentos hara para la respuesta

    public static int getIntentoRespuesta() {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        try {
            return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_RECARGA_VIRTUAL.F_NUM_INTENTOS_RESPUESTA(?,?)",
                                                               parametros);
        } catch (Exception e) {
            log.error("",e);
            // Intento de Respuesta 4 Por Defecto
            return 4;
        }
    }


    // Cuanto tiempo esperar entre respuesta y respuesta
    // 3 Segundos es el mejor de los casos

    public static int leadTimeRespuesta() {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        try {
            return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_RECARGA_VIRTUAL.F_LEAD_TIEMPO_RPTA(?,?)",
                                                               parametros);
        } catch (Exception e) {
            log.error("",e);
            // Intento de Respuesta 4 Por Defecto
            return 3;
        }
    }

    
    //AW 27.05.2015 Start Realizar recarga mediante servicios web
    public void gestionaSolicitudRecargaWS(final String pTelefono, final String pProveedor, final String pMonto, final String pTerminal,
                                         final String pProvincia, final String codLocal, final String numPedVta, final String pCodComercioCia,
											final String pNomComercioCia) throws Exception {

        try{
            ObjectMapper mapper = new ObjectMapper();
    
            //AW:TODO USAR FarmaVariables.vIPBD                                                
            ClienteIntegradorRecargas clienteIntegrador = new ClienteIntegradorRecargas(conexionGTX.getIPBD()+":"+conexionGTX.getPORT());
    
            //ERIOS 22.09.2015 Se envia codComerio y nomComercio. AW debe quitar la llamada a BBDD para recuperar datos iniciales.
            Map<String, String> map = new HashMap<String, String>() {{
                put("telefono", pTelefono);
                put("proveedor", pProveedor);
                put("monto", pMonto);
                put("terminal", pTerminal);
                put("provincia", pProvincia);
                put("numPedVta", numPedVta);
                put("codLocal", codLocal);
                put("tipoRecaudacion", VariablesVirtual.vTipoRcd);
                put("codTipoProducto", VariablesVirtual.vCodTipoProducto);
                put("codGrupoCia", FarmaVariables.vCodGrupoCia);
                put("codCia", FarmaVariables.vCodCia);                
                put("codComercioCia", pCodComercioCia);
                put("nomComercioCia", pNomComercioCia);
            }};
            String jsonRequest = mapper.writeValueAsString(map);
            log.debug("Recarga Json Request: " + jsonRequest);
    
            Long i1 = System.currentTimeMillis();
            String responseJson = clienteIntegrador.recargar(jsonRequest);
            Long i2 = System.currentTimeMillis();
            log.debug("Recarga JSon Response: " + responseJson);
            log.debug("Tiempo transacurriedo: " + (i2-i1));
    
            Map<String,String> responseMap = mapper.readValue(responseJson, new TypeReference<HashMap<String,String>>(){});
    
            RespuestaTXBean bean = new RespuestaTXBean();
            bean.setNumeroTrace(responseMap.get("numeroTrace"));
            bean.setCodigoRespuesta(responseMap.get("codigoRespuesta"));
            bean.setCodigoAprobacion(responseMap.get("codigoAprobacion"));
            bean.setHoraTX(responseMap.get("horaTx"));
            bean.setFechaTX(responseMap.get("fechaTx"));
            bean.setDescripcion(responseMap.get("descripcion"));
            bean.setNumeroTarjeta(responseMap.get("numeroTarjeta"));
            bean.setCodigoPIN(responseMap.get("codigoPin"));
            bean.setDatosImprimir(responseMap.get("datosImprimir"));
            bean.setDescripcion(responseMap.get("descripcion"));
            
            VariablesVirtual.vValorMinimo = responseMap.get("valorMinimo");
            VariablesVirtual.vValorMaximo = responseMap.get("valorMaximo");
    
            //Set global object
            VariablesVirtual.respuestaTXBean = bean;
        }catch(Exception e){
            log.error("",e);
            String sysdate = obtieneHora();
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorIntegrador, "Error de ingresaSolicitudDeRecargaWS",
                                      "Error de ingresaSolicitudDeRecargaWS",
                                      "EXCEPTION" + "<br>" + 
                                      "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + numPedVta + "<br>" +
                                      "Hora :" + sysdate + "<br>" +
                                      "Error: " + e.getMessage(), "");
            throw new Exception("No se pudo realizar la transacción." + "@" +
                                      "Por favor, intente realizar el cobro nuevamente.");
        }
    }
    //AW 27.05.2015 End Realizar recarga mediante servicios web

    public void gestionaSolicitudRecarga(String pTelefono, String pProveedor, String pMonto, String pProveedorRecargas,
                                         String pProvincia, String codLocal, String numPedVta) throws Exception {
        int segundosEspera = leadTimeRespuesta();
        VariablesVirtual.respuestaTXBean = new RespuestaTXBean();

        String pResInSolicitud = "N";
        // HAY CONEXION EN RECARGA
        if (validarConexionRecarga()) {
            // Este metodo solo retornara 2 valores
            // I si entro al inicio >> Si se qdo en el inicio o si entro algun exception.
            // E si ejecuto el Insert y dio commit ESTO SIEMPRE VA PASAR si ESTA EN E inserto en APPS
            pResInSolicitud =
                    ingresaSolicitudDeRecarga(pTelefono, pProveedor, pMonto, pProveedorRecargas, pProvincia, codLocal, numPedVta);
            try {
                // Procede a consultar el estado de la recarga y si hay respuesta
                // ESPERA LOS
                int intoConsulaRespuesta = getIntentoRespuesta();
                for (int i = 0; i < intoConsulaRespuesta; i++) {
                    log.info("Consulta de Respuesta >> i>> " + i);
                    // Espera los X segundos
                    log.info("Espera " + segundosEspera + " " + ">> i>> " + i);
                    try {
                        Thread.sleep(segundosEspera * 1000);
                    } catch (InterruptedException e) {
                        log.error("",e);
                    } catch (Exception q) {
                        log.error("",q);
                    }

                    // Consulta Respuesta en XE_999
                    if (validarConexionRecarga()) {
                        VariablesVirtual.respuestaTXBean =
                                respuestaSolicitudRecargaDAO(FarmaVariables.vCodGrupoCia, codLocal, numPedVta);
                    }
                    //Se obtuvo un Objeto y se obtuvo respuesta
                    if (VariablesVirtual.respuestaTXBean != null) {
                        if (VariablesVirtual.respuestaTXBean.getCodigoRespuesta() != null)
                            break;
                    }
                }

                String pRespuesta = "";
                try {
                    pRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta().trim();
                } catch (Exception e) {
                    log.error("", e);
                    pRespuesta = null;
                }

                // HAY CONEXION EN MATRIZ
                if (validarConexionRecarga()) {
                    log.info("HAY LINEA A MATRIZ");
                    // SI LA RESPUESTA ES CERO >>> COBRA
                    if (pRespuesta.equals("00")) {
                        log.info(".. RESPUESTA ES EXITO RECARGA .. COBRA PEDIDO");
                        log.info(".. RESPUESTA ES " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
                    } else {
                        // Es null o codigo de error
                        // NO DEBE DE COBRAR

                        log.info(".. NO FUE EXITO LA RECARGA");
                        if (pRespuesta == null) {
                            if (pResInSolicitud.trim().equalsIgnoreCase("E")) {
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
                            } else {
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
                        } else {
                            log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                            // Si hay Respuesta COBRAR SOLO SI ES EXITO
                            // SINO NO COBRAR
                        }
                    }
                } else {
                    log.info("NO HAY LINEA A MATRIZ");
                    if (pRespuesta == null) {
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
                    } else {
                        if (pRespuesta == "00") {
                            log.info(".. RESPUESTA ES EXITO RECARGA .. COBRA PEDIDO");
                        } else {
                            // Es null o codigo de error
                            // NO DEBE DE COBRAR
                            log.info(".. NO FUE EXITO LA RECARGA");
                            if (pRespuesta == null) {
                                if (pResInSolicitud.trim().equalsIgnoreCase("E")) {
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
                                } else {
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
                            } else {
                                log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                                // Si hay Respuesta COBRAR SOLO SI ES EXITO
                                // SINO NO COBRAR
                            }
                        }
                    }
                }

            } catch (Exception nfe) {
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

        } else {
            throw new Exception("No Hay Conexion Con el Servicio de Recarga" + "@" + "Inténtelo nuevamente" + "@" +
                                "Si persiste el error, llame a Mesa de Ayuda");
        }


    }

    //AW 27.05.2015 Start Realizar Anulacion mediante servicios web
    public void gestionaSolAnulRecargaWS(final String pTelefono, final String pProveedor, final String pMonto, final String pTerminal,
                                        final String pProvincia, final String codLocal, final String numPedVta) throws Exception {

        try{
            ObjectMapper mapper = new ObjectMapper();
    
            ClienteIntegradorRecargas clienteIntegrador = new ClienteIntegradorRecargas(conexionGTX.getIPBD()+":"+conexionGTX.getPORT());
            Map<String, String> map = new HashMap<String, String>() {{
                put("telefono", pTelefono);
                put("proveedor", pProveedor);
                put("monto", pMonto);
                put("terminal", pTerminal);
                put("provincia", pProvincia);
                put("numPedVta", numPedVta);
                put("codLocal", codLocal);
                put("tipoRecaudacion", VariablesVirtual.vTipoRcd);
                put("codTipoProducto", VariablesVirtual.vCodTipoProducto);
                put("codGrupoCia", FarmaVariables.vCodGrupoCia);
                put("codCia", FarmaVariables.vCodCia);
                //preguntar por demas variables
            }};
            String jsonRequest = mapper.writeValueAsString(map);
            log.debug("Recarga Json Request: " + jsonRequest);
            Long i1 = System.currentTimeMillis();
            String responseJson = clienteIntegrador.anular(jsonRequest);
            Long i2 = System.currentTimeMillis();
            log.debug("Recarga JSon Response: " + responseJson);
            log.debug("Tiempo transacurriedo: " + (i2-i1));
    
            Map<String,String> responseMap = mapper.readValue(responseJson, new TypeReference<HashMap<String,String>>(){});
            RespuestaTXBean bean = new RespuestaTXBean();
            bean.setNumeroTrace(responseMap.get("numeroTrace"));
            bean.setCodigoRespuesta(responseMap.get("codigoRespuesta"));
            bean.setCodigoAprobacion(responseMap.get("codigoAprobacion"));
            bean.setHoraTX(responseMap.get("horaTx"));
            bean.setFechaTX(responseMap.get("fechaTx"));
            bean.setDescripcion(responseMap.get("descripcion"));
            bean.setNumeroTarjeta(responseMap.get("numeroTarjeta"));
            bean.setCodigoPIN(responseMap.get("codigoPin"));
            bean.setDatosImprimir(responseMap.get("datosImprimir"));
            bean.setDescripcion(responseMap.get("descripcion"));
    
            VariablesVirtual.vValorMinimo = responseMap.get("valorMinimo");
            VariablesVirtual.vValorMaximo = responseMap.get("valorMaximo");
    
            //Set global object
            VariablesVirtual.respuestaTXBean = bean;
        }catch(Exception e){
            log.error("",e);
            String sysdate = obtieneHora();
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorIntegrador, "Error de gestionaSolAnulRecargaWS",
                                      "Error de gestionaSolAnulRecargaWS",
                                      "EXCEPTION" + "<br>" + 
                                      "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + numPedVta + "<br>" +
                                    "Hora :" + sysdate + "<br>" +
                                    "Error: " + e.getMessage(), "");
            throw new Exception("Inténtelo nuevamente." + "@" +
                                      "Si persiste el error, llame a Mesa de Ayuda.");
        }
    }
    //AW 27.05.2015 End Realizar Anulacion mediante servicios web

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
                                       String pProvincia, String codLocal, String numPedVta) throws Exception {
        int segundosEspera = leadTimeRespuesta();
        VariablesVirtual.respuestaTXBean = new RespuestaTXBean();

        String pResInSolicitud = "N";
        // HAY CONEXION EN RECARGA
        if (validarConexionRecarga()) {
            // Este metodo solo retornara 2 valores
            // I si entro al inicio >> Si se qdo en el inicio o si entro algun exception.
            // E si ejecuto el Insert y dio commit ESTO SIEMPRE VA PASAR si ESTA EN E inserto en APPS
            pResInSolicitud =
                    ingresaSolAnulRecarga(pTelefono, pProveedor, pMonto, pTerminal, pProvincia, codLocal, numPedVta);
            try {
                // Procede a consultar el estado de la recarga y si hay respuesta
                // ESPERA LOS
                int intoConsulaRespuesta = getIntentoRespuesta();
                for (int i = 0; i < intoConsulaRespuesta; i++) {
                    log.info("Consulta de Respuesta >> i>> " + i);
                    // Espera los X segundos
                    log.info("Espera " + segundosEspera + " " + ">> i>> " + i);
                    try {
                        Thread.sleep(segundosEspera * 1000);
                    } catch (InterruptedException e) {
                        log.error("",e);
                    } catch (Exception q) {
                        log.error("",q);
                    }

                    // Consulta Respuesta en XE_999
                    if (validarConexionRecarga()) {
                        VariablesVirtual.respuestaTXBean =
                                respuestaSolicitudAnulacionDAO(FarmaVariables.vCodGrupoCia, codLocal, numPedVta);
                    }
                    //Se obtuvo un Objeto y se obtuvo respuesta
                    if (VariablesVirtual.respuestaTXBean != null) {
                        if (VariablesVirtual.respuestaTXBean.getCodigoRespuesta() != null)
                            break;
                    }
                }

                String pRespuesta = "";
                try {
                    pRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta().trim();
                    log.info("RESPUESTA DE DEMONIO: " + pRespuesta);
                } catch (Exception e) {
                    log.error("", e);
                    pRespuesta = null;
                }

                // HAY CONEXION EN MATRIZ
                if (validarConexionRecarga()) {
                    log.info("HAY LINEA A MATRIZ");
                    // SI LA RESPUESTA ES CERO >>> COBRA
                    if (pRespuesta.equals("00")) {
                        log.info(".. RESPUESTA ES EXITO anulacion RECARGA .. PEDIDO");
                        log.info(".. RESPUESTA ES " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
                    } else {
                        // Es null o codigo de error
                        // NO DEBE DE COBRAR

                        log.info(".. NO FUE EXITO LA anulacion de la RECARGA");
                        if (pRespuesta == null) {
                            if (pResInSolicitud.trim().equalsIgnoreCase("E")) {
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
                            } else {
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
                        } else {
                            log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                            // Si hay Respuesta COBRAR SOLO SI ES EXITO
                            // SINO NO COBRAR
                        }
                    }
                } else {
                    log.info("NO HAY LINEA A MATRIZ");
                    if (pRespuesta == null) {
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
                    } else {
                        if (pRespuesta == "00") {
                            log.info(".. RESPUESTA ES EXITO ANULACION .. COBRA PEDIDO");
                        } else {
                            // Es null o codigo de error
                            // NO DEBE DE COBRAR
                            log.info(".. NO FUE EXITO LA ANULACION");
                            if (pRespuesta == null) {
                                if (pResInSolicitud.trim().equalsIgnoreCase("E")) {
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
                                } else {
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
                            } else {
                                log.info("HAY RESPUESTA DE ERROR NO COBRA EL PEDIDO MOSTRAR MENSAJE");
                                // Si hay Respuesta COBRAR SOLO SI ES EXITO
                                // SINO NO COBRAR
                            }
                        }
                    }
                }

            } catch (Exception nfe) {
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

        } else {
            throw new Exception("No Hay Conexion Con el Servicio de Recarga" + "@" + "Inténtelo nuevamente" + "@" +
                                "Si persiste el error, llame a Mesa de Ayuda");
        }


    }


    private static String getCampo12(Date fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
            formatter.setLenient(false);
            String s = formatter.format(fecha);
            return s;
        } catch (Exception e) {
            String s1 = null;
            return s1;
        }
    }

    private static String getCampo13(Date fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
            formatter.setLenient(false);
            String s = formatter.format(fecha);
            return s;
        } catch (Exception e) {
            String s1 = null;
            return s1;
        }
    }

    public String ingresaSolicitudDeRecarga(String pTelefono, String pProveedor, String pMonto, String pProveedorRecargas,
                                            String pProvincia, String codLocal, String numPedVta) {
        String pResultado = "I";

        if (validarConexionRecarga()) {
            
            //// Carga Bean de Solicitud de Recarga ////
            log.info(">>  ingresaSolicitudDeRecarga");
            
            Date fecha = new Date();
            String campo12 = getCampo12(fecha);
            String campo13 = getCampo13(fecha);
            String terminalID = "";
            String terminalState = pProvincia;

            int set_04 = (int)Double.parseDouble(pMonto);
            
            String set_43 = "MIFARMA               " + terminalID + terminalState + "PE";
            String set_48 = pProveedor + pTelefono;
            String set_49 = "604";
            
            //// Carga Bean de Solicitud de Recarga ////
            //// Carga Bean de Solicitud de Recarga ////
            Connection conn = cnxUtil.getConexionRecarga();

            // Agrega la solcitud en ADM XE_999
            try {
                CallableStatement stmt =
                    conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_P_AGREGA_SOLICITUD(" + "?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
                stmt.registerOutParameter(1, OracleTypes.NUMBER);
                stmt.setString(2, "200");
                stmt.setInt(3, set_04);
                stmt.setString(4, "" + campo12);
                stmt.setString(5, "" + campo13);
                stmt.setString(6, "" + pProveedorRecargas);
                stmt.setString(7, "" + set_43);
                stmt.setString(8, "" + set_48);
                stmt.setString(9, "" + set_49);
                stmt.setString(10, "1");
                stmt.setString(11, "SIX_JDBC");
                stmt.setString(12, "" + codLocal);
                stmt.setString(13, "" + numPedVta);
                //INI ASOSA - 13/07/2014
                stmt.setString(14, VariablesVirtual.vTipoRcd);
                stmt.setString(15, VariablesVirtual.vCodTipoProducto);
                //FIN ASOSA - 13/07/2014

                stmt.execute();
                conn.commit();
                // EJECUTADO
                pResultado = "E";
                stmt.close();

                if (conn.isClosed())
                    conn.close();

            } catch (SQLException e) {
                try {
                    if (conn.isClosed())
                        conn.close();

                } catch (Exception sqle) {
                    log.error("", sqle);
                }
                conn = null;
                //log.debug("ERROR solicitudRecargaDAO "+e);
                log.info("ERROR solicitudRecargaDAO SQLException " + e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesPtoVenta.vDestEmailErrorCobro, "Error de ingresaSolicitudDeRecarga",
                                          "Error de ingresaSolicitudDeRecarga",
                                          "SQL EXCEPTION" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                          "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                          FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + numPedVta + "<br>" +
                                          "Error: " + "Conexion :" + e.getMessage(), "");
            } catch (Exception eq) {
                try {
                    if (conn.isClosed())
                        conn.close();

                } catch (Exception sqle) {
                    log.error("", sqle);
                }
                conn = null;
                pResultado = "E";
                //log.debug("ERROR solicitudRecargaDAO "+e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesPtoVenta.vDestEmailErrorCobro, "Error de ingresaSolicitudDeRecarga",
                                          "Error de ingresaSolicitudDeRecarga",
                                          "Exception" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                          "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                          FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + numPedVta + "<br>" +
                                          "Error: " + "Conexion :" + eq.getMessage(), "");
                log.info("ERROR solicitudRecargaDAO Exception " + eq);
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
                                        String pProvincia, String codLocal, String numPedVta) {
        String pResultado = "I";

        if (validarConexionRecarga()) {
            //// Carga Bean de Solicitud de Recarga ////
            log.info(">>  ingresaSolAnulRecarga");
            
            //// Carga Bean de Solicitud de Recarga ////
            //// Carga Bean de Solicitud de Recarga ////

            Connection conn = cnxUtil.getConexionRecarga();

            // Agrega la solcitud en ADM XE_999
            try {
                CallableStatement stmt =
                    conn.prepareCall("{call PACK_SIX_RECARGA.ADM_P_AGREGA_ANULACION(" + "?,?,?,?) }");

                stmt.setString(1, FarmaVariables.vCodGrupoCia);
                stmt.setString(2, FarmaVariables.vCodLocal);
                stmt.setString(3, VariablesCaja.vNumPedVta_Anul);
                stmt.setString(4, "200");

                log.info("vCodGrupoCia: " + FarmaVariables.vCodGrupoCia);
                log.info("vCodLocal: " + FarmaVariables.vCodLocal);
                log.info("vNumPedVta_Anul: " + VariablesCaja.vNumPedVta_Anul);
                log.info("tipo_mensaje " + "200");

                stmt.executeUpdate();
                conn.commit();
                // EJECUTADO
                pResultado = "E";
                stmt.close();

                if (conn.isClosed())
                    conn.close();

            } catch (SQLException e) {
                try {
                    if (conn.isClosed())
                        conn.close();

                } catch (Exception sqle) {
                    log.error("", sqle);
                }
                conn = null;
                //log.debug("ERROR solicitudRecargaDAO "+e);
                log.info("ERROR ingresarSolAnulRecarga SQLException " + e);
                log.debug("ERROR ingresarSolAnulRecarga SQLException " + e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesPtoVenta.vDestEmailErrorCobro, "Error de ingresarSolAnulRecarga",
                                          "Error de ingresarSolAnulRecarga",
                                          "SQL EXCEPTION" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                          "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                          FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + numPedVta + "<br>" +
                                          "Error: " + "Conexion :" + e.getMessage(), "");
            } catch (Exception eq) {
                try {
                    if (conn.isClosed())
                        conn.close();

                } catch (Exception sqle) {
                    log.error("", sqle);
                }
                conn = null;
                pResultado = "E";
                //log.debug("ERROR solicitudRecargaDAO "+e);
                cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                          VariablesPtoVenta.vDestEmailErrorCobro, "Error de ingresarSolAnulRecarga",
                                          "Error de ingresarSolAnulRecarga",
                                          "Exception" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                          "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                          FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + numPedVta + "<br>" +
                                          "Error: " + "Conexion :" + eq.getMessage(), "");
                log.info("ERROR ingresarSolAnulRecarga Exception " + eq);
            }
        }
        // EJECUTADO
        return pResultado;
    }

    //AW 17.08.2015 Start respuestaSolicitudRecargaDAO Implementación por Query Service
    public RespuestaTXBean respuestaSolicitudRecargaWS(String pCodGrupoCia, String pCodLocal, String pNumPedVta) {
        RespuestaTXBean bean = new RespuestaTXBean();
        try {
            QueryParams params = new QueryBuilder()
                .codLocalOrgDst(pCodLocal, "RECARGA")
                .qrySpCursor("{ ? = call PACK_SIX_RECARGA.ADM_GET_ENVIA_RPTA_FV(?,?,?)}", pCodGrupoCia, pCodLocal, pNumPedVta)
                .getParams();
            
            ClienteIntegrador clienteIntegrador = new ClienteIntegrador(conexionGTX.getIPBD()+":"+conexionGTX.getPORT());
            clienteIntegrador.bypassGateway();
            QueryStatus status = clienteIntegrador.queryGeneric(params);
            status.exceptionOnError();
            
            List<Map<String, Object>> resultList = status.getResult();

            if (resultList.size() == 0) {
                throw new RuntimeException("No se encontraron registros al ejecutar el SP PACK_SIX_RECARGA.ADM_GET_ENVIA_RPTA_FV ");
            }

            Map<String, Object> first = resultList.get(0);
            
            Date fecha = new Date();
            String campo12 = getCampo12(fecha);
            String campo13 = getCampo13(fecha);

            bean.setNumeroTrace("" + first.get("1"));
            bean.setCodigoRespuesta("" + first.get("3"));
            bean.setCodigoAprobacion("" + first.get("2"));
            bean.setHoraTX(campo12);
            bean.setFechaTX(campo13);
            bean.setDescripcion("" + first.get(5));
            bean.setNumeroTarjeta("" + first.get(5));
            bean.setCodigoPIN("" + first.get(5));
            bean.setDatosImprimir("" + first.get(5));
            bean.setDescripcion("" + first.get(5));
            //INI - 17/07/2014
            VariablesVirtual.vValorMinimo = "" + first.get(8);
            VariablesVirtual.vValorMaximo = "" + first.get(9);
            //FIN - 17/07/2014

            return bean;
            
        } catch (Exception e) {
            log.error("",e);
            String sysdate = obtieneHora();
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorIntegrador, "Error de respuestaSolicitudRecargaWS",
                                      "Error de respuestaSolicitudRecargaWS",
                                      "Exception" + "<br>" + 
                                      "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + 
                                    "Hora :" + sysdate + "<br>" +
                                    "Error: " + e.getMessage(), "");
            return null;
        }
    }
    //AW 17.08.2015 End respuestaSolicitudRecargaDAO Implementación por Query Service

    public RespuestaTXBean respuestaSolicitudRecargaDAO(String pCodGrupoCia, String pCodLocal, String pNumPedVta) {
        String codigo = pCodGrupoCia + "-" + pCodLocal + "-" + pNumPedVta;
        log.info("Inicio metodo respuestaSolicitudRecargaDAO Pedido " + codigo);
        RespuestaTXBean bean = new RespuestaTXBean();
        Connection conn = cnxUtil.getConexionRecarga();
        try {
            //Connection conn= cnxUtil.getConexionRecarga();
            
            log.info("ANTES get Respuesta XE_999 EN BD respuesta Pedido " + codigo);
            CallableStatement stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_GET_ENVIA_RPTA_FV(?,?,?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, pCodGrupoCia);
            stmt.setString(3, pCodLocal);
            stmt.setString(4, pNumPedVta);
            stmt.execute();
            log.info("DESPUES get Respuesta XE_999 EN BD respuesta Pedido " + codigo);
            ResultSet results = (ResultSet)stmt.getObject(1);
            if (results.next()) {
                
                Date fecha = new Date();
                String campo12 = getCampo12(fecha);
                String campo13 = getCampo13(fecha);

                bean.setNumeroTrace("" + results.getString(1));
                bean.setCodigoAprobacion("" + results.getString(2));
                bean.setCodigoRespuesta("" + results.getString(3));
                bean.setHoraTX(campo12);
                bean.setFechaTX(campo13);
                bean.setDescripcion("" + results.getString(5));
                bean.setNumeroTarjeta("" + results.getString(5));
                bean.setCodigoPIN("" + results.getString(5));
                bean.setDatosImprimir("" + results.getString(5));
                bean.setDescripcion("" + results.getString(5));
                //INI - 17/07/2014
                VariablesVirtual.vValorMinimo = "" + results.getString(8);
                VariablesVirtual.vValorMaximo = "" + results.getString(9);
                //FIN - 17/07/2014
                //if(!bean.getCodigoRespuesta().equals("00"))
                //bean.setDescripcion(MesajeErrorBprepaid.getMensajeError(Integer.parseInt(bean.getCodigoRespuesta())));
            } 
            results.close();
            stmt.close();
            if (conn.isClosed())
                conn.close();
            conn = null;
            log.info("OK respuestaSolicitudRecargaDAO Codigo :" + codigo + " - " + bean);
            return bean;
        } catch (SQLException e) {
            try {
                if (conn.isClosed())
                    conn.close();

            } catch (Exception sqle) {
                log.error("", sqle);
            }
            conn = null;
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorCobro, "Error de respuestaSolicitudRecargaDAO",
                                      "Error de respuestaSolicitudRecargaDAO",
                                      "Exception" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                      "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + "Error: " +
                                      "Conexion :" + e.getMessage(), "");
            
            return null;
        } catch (Exception eq) {
            try {
                if (conn.isClosed())
                    conn.close();

            } catch (Exception sqle) {
                log.error("", sqle);
            }
            conn = null;
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorCobro, "Error de respuestaSolicitudRecargaDAO",
                                      "Error de respuestaSolicitudRecargaDAO",
                                      "Exception" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                      "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + "Error: " +
                                      "Conexion :" + eq.getMessage(), "");
            
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
    public RespuestaTXBean respuestaSolicitudAnulacionDAO(String pCodGrupoCia, String pCodLocal, String pNumPedVta) {
        String codigo = pCodGrupoCia + "-" + pCodLocal + "-" + pNumPedVta;
        log.info("Inicio metodo respuestaSolicitudRecargaDAO Pedido " + codigo);
        RespuestaTXBean bean = new RespuestaTXBean();
        Connection conn = cnxUtil.getConexionRecarga();
        try {
            //Connection conn= cnxUtil.getConexionRecarga();
            
            log.info("ANTES get Respuesta XE_999 EN BD respuesta Pedido " + codigo);
            CallableStatement stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.ADM_GET_RPTA_ANUL_FV(?,?,?)}");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, pCodGrupoCia);
            stmt.setString(3, pCodLocal);
            stmt.setString(4, pNumPedVta);
            log.info("PARAM 01: " + pCodGrupoCia);
            log.info("PARAM 02: " + pCodLocal);
            log.info("PARAM 03: " + pNumPedVta);
            stmt.execute();
            log.info("DESPUES get Respuesta XE_999 EN BD respuesta Pedido " + codigo);
            ResultSet results = (ResultSet)stmt.getObject(1);
            if (results.next()) {
                
                Date fecha = new Date();
                String campo12 = getCampo12(fecha);
                String campo13 = getCampo13(fecha);

                bean.setNumeroTrace("" + results.getString(1));
                bean.setCodigoRespuesta("" + results.getString(3));
                bean.setCodigoAprobacion("" + results.getString(2));
                bean.setHoraTX(campo12);
                bean.setFechaTX(campo13);
                bean.setDescripcion("" + results.getString(5));
                bean.setNumeroTarjeta("" + results.getString(5));
                bean.setCodigoPIN("" + results.getString(5));
                bean.setDatosImprimir("" + results.getString(5));
                bean.setDescripcion("" + results.getString(5));
                //if(!bean.getCodigoRespuesta().equals("00"))
                //bean.setDescripcion(MesajeErrorBprepaid.getMensajeError(Integer.parseInt(bean.getCodigoRespuesta())));
            }
            results.close();
            stmt.close();
            if (conn.isClosed())
                conn.close();
            conn = null;
            log.info("OK respuestaSolicitudRecargaDAO Codigo :" + codigo + " - " + bean);
            return bean;
        } catch (SQLException e) {
            try {
                if (conn.isClosed())
                    conn.close();

            } catch (Exception sqle) {
                log.error("", sqle);
            }
            conn = null;
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorCobro, "Error de respuestaSolicitudRecargaDAO",
                                      "Error de respuestaSolicitudRecargaDAO",
                                      "Exception" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                      "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + "Error: " +
                                      "Conexion :" + e.getMessage(), "");
            
            return null;
        } catch (Exception eq) {
            try {
                if (conn.isClosed())
                    conn.close();

            } catch (Exception sqle) {
                log.error("", sqle);
            }
            conn = null;
            cnxUtil.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorCobro, "Error de respuestaSolicitudRecargaDAO",
                                      "Error de respuestaSolicitudRecargaDAO",
                                      "Exception" + "<br>" + "Inténtelo nuevamente." + "<br>" +
                                      "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + "Error: " +
                                      "Conexion :" + eq.getMessage(), "");
            
            return null;
        }
    }

    //LLEIVA 27-Mar-2014

    //AW 18.08.2015 Convertir obtieneMensajeRecarga a query service
    public String obtieneMensajeRecargaWS(String pNumPedVta, String fechaPedido) {
        String respuesta = "";
        
        try {
            QueryParams params = new QueryBuilder()
                .codLocalOrgDst(FarmaVariables.vCodLocal, "RECARGA")
                .qrySpVarchar("{ ? = call PACK_SIX_RECARGA.ADM_OBT_INFO_RECARGA(?,?,?,?,?,?)}", FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, pNumPedVta, 
                              "9999999", fechaPedido.substring(0, 10))
                .getParams();
            
            ClienteIntegrador clienteIntegrador = new ClienteIntegrador(conexionGTX.getIPBD()+":"+conexionGTX.getPORT());
            clienteIntegrador.bypassGateway();
            QueryStatus status = clienteIntegrador.queryGeneric(params);
            status.exceptionOnError();
            
            List<Map<String, Object>> result = status.getResult(); 
            Map<String, Object> first = result.get(0);

            respuesta = String.valueOf(first.get("1"));
            
        } catch (Exception e){
            log.error("", e);
            String sysdate = obtieneHora();
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorIntegrador, "Error de obtieneMensajeRecargaWS",
                                      "Error de obtieneMensajeRecargaWS",
                                      "Exception" + "<br>" + 
                                      "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + 
                                    "Hora :" + sysdate + "<br>" +
                                    "Error: " + e.getMessage(), "");
            respuesta = "ERROR";            
        }
        
        return respuesta;
    }
    //AW 18.08.2015 Convertir a query service

    public String obtieneMensajeRecargaDAO(String pNumPedVta, String fechaPedido) {
        String respuesta = "";
        Connection conn;
        CallableStatement stmt;
        //Object results;
        try {
            if (validarConexionRecarga()) {
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
                if (conn.isClosed())
                    conn.close();
                conn = null;
            } else
                respuesta = "ERROR BD: No se puede establecer conexion con el servidor central";
        } catch (Exception e) {
            log.info("", e);
            respuesta = "ERROR";
            conn = null;
        }
        return respuesta;
    }

    //LLEIVA 27-Mar-2014

    public String obtieneMensajeRecargaAnul(String pNumPedVta, String fechaPedido) {
        String respuesta = "";
        Connection conn;
        CallableStatement stmt;
        //Object results;
        try {
            if (validarConexionRecarga()) {
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
                if (conn.isClosed())
                    conn.close();
                conn = null;
            } else
                respuesta = "ERROR BD: No se puede establecer conexion con el servidor central";
        } catch (Exception e) {
            log.info("", e);
            respuesta = "ERROR BD:" + e.getMessage();
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
    public static void obtieneInfoPedidoVirtual() throws Exception {
        try {
            DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual, VariablesCaja.vNumPedVta);
            log.debug("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
        } catch (SQLException sql) {
            log.error("ERROR en obtieneInfoPedidoVirtual: ", sql);
            throw new Exception("Error al obtener informacion del pedido virtual - \n" +
                    sql);
        }
    }

    /**
     * Coloca informacion de pedido virtual para registrar.
     * @author ASOSA
     * @since 13/07/2014
     * @throws Exception
     */
    public static void colocaInfoPedidoVirtualGrabar() throws Exception {
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

    //AW 17.08.2015 Start getMensajeRPTRecargaPedido Implementacion por QueryService
    public String getMensajeRPTRecargaPedidoWS(String pNumPedVta, String fechaPedido) {
        String respuesta = "";
        
        try {
            QueryParams params = new QueryBuilder()
                .codLocalOrgDst(FarmaVariables.vCodLocal, "RECARGA")
                .qrySpVarchar("{ ? = call PACK_SIX_RECARGA.F_RPTA_RECARGA_PEDIDO(?,?,?,?,?)}", FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, pNumPedVta, fechaPedido.substring(0, 10))
                .getParams();
            
            ClienteIntegrador clienteIntegrador = new ClienteIntegrador(conexionGTX.getIPBD()+":"+conexionGTX.getPORT());
            clienteIntegrador.bypassGateway();
            QueryStatus status = clienteIntegrador.queryGeneric(params);
            status.exceptionOnError();
            
            List<Map<String, Object>> result = status.getResult(); 
            Map<String, Object> first = result.get(0);

            respuesta = String.valueOf(first.get("1"));
            
        } catch (Exception e){
            log.error("", e);
            String sysdate = obtieneHora();
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorIntegrador, "Error de getMensajeRPTRecargaPedidoWS",
                                      "Error de getMensajeRPTRecargaPedidoWS",
                                      "Exception" + "<br>" + 
                                      "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + 
                                    "Hora :" + sysdate + "<br>" +
                                    "Error: " + e.getMessage(), "");
            respuesta = "Inténtelo nuevamente." + "\n" +
                                      "Si persiste el error, llame a Mesa de Ayuda.";           
        }
        
        return respuesta;
        
    }
    //AW 17.08.2015 End getMensajeRPTRecargaPedido Implementacion por QueryService

    public String getMensajeRPTRecargaPedidoDAO(String pNumPedVta, String fechaPedido) {
        String respuesta = "";
        Connection conn;
        CallableStatement stmt;
        //Object results;
        try {
            if (validarConexionRecarga()) {
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
                if (conn.isClosed())
                    conn.close();
                conn = null;
            } else
                respuesta = "ERROR BD: No se puede establecer conexion con el servidor central";
        } catch (Exception e) {
            log.info("", e);
            respuesta = "ERROR>" + e.getMessage();
            conn = null;
        }
        return respuesta;
    }

    //AW 17.08.2015 Start getIsRecargaAnulada Implementacion por QueryService
    public boolean getIsRecargaAnuladaWS(String pCodGrupoCia, String pCodLocal, String pNumPedVta, String pCodTrassc){
        boolean respuesta = false;
        try{
            QueryParams params = new QueryBuilder()
                .codLocalOrgDst(pCodLocal, "RECARGA")
                .qrySpVarchar("{ ? = call PACK_SIX_RECARGA.GET_RECARGA_ANULADA(?,?,?,?)}", pCodGrupoCia, pCodLocal, pNumPedVta, pCodTrassc)
                .getParams();
            
            ClienteIntegrador clienteIntegrador = new ClienteIntegrador(conexionGTX.getIPBD()+":"+conexionGTX.getPORT());
            clienteIntegrador.bypassGateway();
            QueryStatus status = clienteIntegrador.queryGeneric(params);
            status.exceptionOnError();
            
            List<Map<String, Object>> result = status.getResult(); 
            Map<String, Object> first = result.get(0);
    
            String resultado = String.valueOf(first.get("1"));
    
            if ("S".equalsIgnoreCase(resultado)){
                respuesta = true;
            }
        } catch (Exception e){
            log.error("", e);
            String sysdate = obtieneHora();
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorIntegrador, "Error de getIsRecargaAnuladaWS",
                                      "Error de getIsRecargaAnuladaWS",
                                      "Exception" + "<br>" + 
                                      "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + 
                                    "Hora :" + sysdate + "<br>" +
                                    "Error: " + e.getMessage(), "");
        }
        return respuesta;
    }
    //AW 17.08.2015 End getIsRecargaAnulada Implementacion por QueryService


    /**
     * Metodo que verifica si recarga ya cuenta con un extorno o anulacion previo a la operacion en el SIX.
     * 
     * @since 19.01.2015 KMONCADA
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta nro pedido a anular
     * @param pCodTrassc nro de la transaccion a anular
     * @return
     */
    public boolean getIsRecargaAnuladaDAO(String pCodGrupoCia, String pCodLocal, String pNumPedVta, String pCodTrassc){
        boolean respuesta = false;
        String resultado = "";
        Connection conn;
        CallableStatement stmt;
        try {
            if (validarConexionRecarga()) {
                //Se obtiene la conexión y retorna los datos de la recarga
                conn = cnxUtil.getConexionRecarga();
                stmt = conn.prepareCall("{ ? = call PACK_SIX_RECARGA.GET_RECARGA_ANULADA(?,?,?,?)}");
                stmt.registerOutParameter(1, OracleTypes.VARCHAR);
                stmt.setString(2, pCodGrupoCia);
                stmt.setString(3, pCodLocal);
                stmt.setString(4, pNumPedVta);
                stmt.setString(5, pCodTrassc);
                stmt.execute();
                resultado = stmt.getObject(1).toString();
                stmt.close();
                if (conn.isClosed())
                    conn.close();
                conn = null;
                if ("S".equalsIgnoreCase(resultado)){
                    respuesta = true;
                }
            } else{
                log.info("No se pudo establecer conexion con servidor. NumPedVta:"+pNumPedVta+" nro transaccion: "+pCodTrassc);
            }
        } catch (Exception ex) {
            log.error("", ex);
            conn = null;
        }
        return respuesta;
    }

    /**
     * Determina metodo de busqueda
     * @author ERIOS
     * @since 19.08.2015
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @return
     */
    public RespuestaTXBean respuestaSolicitudRecarga(String pCodGrupoCia, String pCodLocal, String pNumPedVta) {
        RespuestaTXBean bean;
        if("S".equals(getIndGestorTx())){
            bean = respuestaSolicitudRecargaWS(pCodGrupoCia, pCodLocal, pNumPedVta);
        }else{
            if(validarConexionRecarga()){
                bean = respuestaSolicitudRecargaDAO(pCodGrupoCia, pCodLocal, pNumPedVta);
            }else{
                bean = new RespuestaTXBean();
                bean.setDescripcion("NO_CONEXION_RECARGA");
            }
        }
        return bean;
    }

    /**
     * Determina metodo de busqueda
     * @author ERIOS
     * @since 19.08.2015
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNumPedVta
     * @param pCodTrassc
     * @return
     */
    public boolean getIsRecargaAnulada(String pCodGrupoCia, String pCodLocal, String pNumPedVta, String pCodTrassc) {
        boolean rpta;
        if("S".equals(getIndGestorTx(false))){
            rpta = getIsRecargaAnuladaWS(pCodGrupoCia, pCodLocal, pNumPedVta, pCodTrassc);
        }else{
            rpta = getIsRecargaAnuladaDAO(pCodGrupoCia, pCodLocal, pNumPedVta, pCodTrassc);
        }
        return rpta;
    }

    /**
     * Determina metodo de busqueda
     * @author ERIOS
     * @since 19.08.2015
     * @param pNumPedVta
     * @param fechaPedido
     * @return
     */
    public String getMensajeRPTRecargaPedido(String pNumPedVta, String fechaPedido) {
        String respuesta;
        if ("S".equals(getIndGestorTx())) {
            respuesta = getMensajeRPTRecargaPedidoWS(pNumPedVta, fechaPedido);
        } else {
            respuesta = getMensajeRPTRecargaPedidoDAO(pNumPedVta, fechaPedido);
        }
        return respuesta;
    }
    
    /**
     * Determina metodo de busqueda
     * @author ERIOS
     * @since 20.08.2015
     * @param pNumPedVta
     * @param fechaPedido
     * @return
     */
    public String obtieneMensajeRecarga(String pNumPedVta, String fechaPedido){
        String respuesta;
        if ("S".equals(getIndGestorTx())) {
            respuesta = obtieneMensajeRecargaWS(pNumPedVta, fechaPedido);
        } else {
            respuesta = obtieneMensajeRecargaDAO(pNumPedVta, fechaPedido);
        }
        return respuesta;
    }
    
    public void marcaRespuestaRecargaWS(String pCodGrupoCia, String pCodCia, String pCodLocal, String pNumPedVta ){
        try{
            QueryParams params = new QueryBuilder()
                .codLocalOrgDst(pCodLocal, "RECARGA")
                .qrySpVarchar("{ ? = call PACK_SIX_RECARGA.RCD_OBT_ESTADO_TRSSC_RECARGA(?,?,?,?)}", pCodGrupoCia,pCodCia,pCodLocal,pNumPedVta)
                .getParams();
            
            ClienteIntegrador clienteIntegrador = new ClienteIntegrador(conexionGTX.getIPBD()+":"+conexionGTX.getPORT());
            clienteIntegrador.bypassGateway();
            QueryStatus status = clienteIntegrador.queryGeneric(params);
            status.exceptionOnError();
            
            List<Map<String, Object>> resultList = status.getResult();
    
            if (resultList.size() == 0) {
                throw new RuntimeException("No se encontraron registros al ejecutar el SP PACK_SIX_RECARGA.RCD_OBT_ESTADO_TRSSC_RECARGA ");
            }
    
            //Map<String, Object> first = resultList.get(0);
        }catch(Exception e){
            log.error("", e);
            String sysdate = obtieneHora();
            FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorIntegrador, "Error de marcaRespuestaRecargaWS",
                                      "Error de marcaRespuestaRecargaWS",
                                      "Exception" + "<br>" + 
                                      "IP PC: " +
                                      FarmaVariables.vIpPc + "<br>" + "NumPedVta :" + pNumPedVta + "<br>" + 
                                    "Hora :" + sysdate + "<br>" +
                                    "Error: " + e.getMessage(), "");
        }
    }

    /**
     * Retorna la hora actual.
     * @author ERIOS
     * @since 24.09.2015
     * @return
     */
    private String obtieneHora() {
        String sysdate;
        try {
            sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
        } catch (SQLException e) {
            Calendar calendar = Calendar.getInstance();            
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sysdate = format.format(calendar.getTime());
        }
        return sysdate;
    }
}
