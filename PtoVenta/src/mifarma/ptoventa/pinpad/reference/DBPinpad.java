package mifarma.ptoventa.pinpad.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBPinpad
{
    private static final Logger log = LoggerFactory.getLogger(DBPinpad.class);
        
    private static ArrayList<String> parametros;
    
    /**
     * Guarda la trama enviada por el pinpad visa
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static boolean guardarTramaPinpadVisa (String codRespuesta, 
                                                String msjeFinOperacion, 
                                                String nomCliente, 
                                                String numAutorizacion,
                                                String numReferencia,
                                                String numTarjeta,
                                                String fecExpiracion,
                                                String fecTransaccion,
                                                String horaTransaccion,
                                                String codOperacion,
                                                String cortePapel,
                                                String montoPropina,
                                                String numMozo,
                                                String empresa,
                                                String numTerminal,
                                                String cantCuotas,
                                                String pagoDiferido,
                                                String flagPidePin,
                                                String idUnico,
                                                String montoCuota,
                                                String codPedido,
                                                String tipoRegistro,
                                                String codFormaPago) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codRespuesta);
        parametros.add(msjeFinOperacion);
        parametros.add(nomCliente);
        parametros.add(numAutorizacion);
        parametros.add(numReferencia);
        parametros.add(numTarjeta);
        parametros.add(fecExpiracion);
        parametros.add(fecTransaccion);
        parametros.add(horaTransaccion);
        parametros.add(codOperacion);
        parametros.add(cortePapel);
        parametros.add(montoPropina);
        parametros.add(numMozo);
        parametros.add(empresa);
        parametros.add(numTerminal);
        parametros.add(cantCuotas);
        parametros.add(pagoDiferido);
        parametros.add(flagPidePin);
        parametros.add(idUnico);
        parametros.add(montoCuota);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(codPedido);
        parametros.add(tipoRegistro);
        parametros.add(codFormaPago);
        
        log.debug("PTOVENTA_PINPAD.GRAB_TRAMA_PINPAD_VISA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                        parametros);
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_TRAMA_PINPAD_VISA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
    
    /**
     * Guarda la trama enviada por el pinpad Mastercard
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static boolean guardarTramaPinpadMC (String codRespuesta, 
                                                String monto, 
                                                String numAutorizacion,
                                                String mensaje,
                                                String numTarjeta,
                                                String idTarjeta,
                                                String cantCuotas,
                                                String montoCuota,
                                                String tipoCredito,
                                                String nomCliente, 
                                                String codMoneda,
                                                String codAplicacion,
                                                String codTransaccion,
                                                String codComercio,
                                                String printData,
                                                String codPedido,
                                                String codFormaPago) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codRespuesta==null ? "" : codRespuesta);
        parametros.add(monto==null ? "" : monto);
        parametros.add(numAutorizacion==null ? "" : numAutorizacion);
        parametros.add(mensaje==null ? "" : mensaje);
        parametros.add(numTarjeta==null ? "" : numTarjeta);
        parametros.add(idTarjeta==null ? "" : idTarjeta);
        parametros.add(cantCuotas==null ? "" : cantCuotas);
        parametros.add(montoCuota==null ? "" : montoCuota);
        parametros.add(tipoCredito==null ? "" : tipoCredito);
        parametros.add(nomCliente==null ? "" : nomCliente);
        parametros.add(codMoneda==null ? "" : codMoneda);
        parametros.add(codAplicacion==null ? "" : codAplicacion);
        parametros.add(codTransaccion==null ? "" : codTransaccion);
        parametros.add(codComercio==null ? "" : codComercio);
        parametros.add(printData==null ? "" : printData);
        parametros.add(codPedido==null ? "" : codPedido);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(codFormaPago);
        log.debug("PTOVENTA_PINPAD.GRAB_TRAMA_PINPAD_MC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"+
                                                                parametros);
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_TRAMA_PINPAD_MC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
    
    /**
     * Consulta la forma de pago de un determinado pedido
     * @author Luis Leiva
     * @since 08.Ago.2013
     */
    public static void consFormaPagoPedido(String numPedido, HashMap<String,String> pResulta)
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedido);
        
        try
        {   String vResulta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.CONS_INF_TRANS_PINPAD(?,?,?,?)",
                                                                parametros);
            String[] dividido = vResulta.split("Ã");
            pResulta.put("FORMA_PAGO_PED", dividido[0].trim());
            pResulta.put("FECHA_TRANS", dividido[1].trim());
            pResulta.put("NUM_REF_TRANS", dividido[2].trim());
            pResulta.put("MONTO_TARJ", dividido[3].trim());
        }
        catch(Exception e)
        {   log.error("",e);
        }
    }
    
    /**
     * Obtiene el texto de impresión de un voucher de pinpad VISA
     * @author Luis Leiva
     * @since 08.Ago.2013
     */
    public static String impVoucherTransVisa(String numPedido, String isCopia)
    {   String vResulta = "";
        parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedido);
        parametros.add(isCopia);
        
        try
        {   vResulta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.IMP_VOUCHER_TRANSAC_VISA(?,?,?,?)",
                                                                parametros);
        }
        catch(Exception e)
        {   log.error("",e);
        }
        return vResulta;
    }

    /**
     * Guarda la trama enviada por el pinpad Mastercard
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static boolean guardarTramaAnulPinpadMC (String codRespuesta, 
                                                String monto, 
                                                String numAutorizacion,
                                                String mensaje,
                                                String numTarjeta,
                                                String cantCuotas,
                                                String montoCuota,
                                                String tipoCredito,
                                                String nomCliente, 
                                                String codMoneda,
                                                String codAplicacion,
                                                String codTransaccion,
                                                String codComercio,
                                                String printData,
                                                String codPedido) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codRespuesta==null ? "" : codRespuesta);
        parametros.add(monto==null ? "" : monto);
        parametros.add(numAutorizacion==null ? "" : numAutorizacion);
        parametros.add(mensaje==null ? "" : mensaje);
        parametros.add(numTarjeta==null ? "" : numTarjeta);
        parametros.add(cantCuotas==null ? "" : cantCuotas);
        parametros.add(montoCuota==null ? "" : montoCuota);
        parametros.add(tipoCredito==null ? "" : tipoCredito);
        parametros.add(nomCliente==null ? "" : nomCliente);
        parametros.add(codMoneda==null ? "" : codMoneda);
        parametros.add(codAplicacion==null ? "" : codAplicacion);
        parametros.add(codTransaccion==null ? "" : codTransaccion);
        parametros.add(codComercio==null ? "" : codComercio);
        parametros.add(printData==null ? "" : printData);
        parametros.add(codPedido==null ? "" : codPedido);
        parametros.add(FarmaVariables.vIdUsu);
        
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_TRAMA_ANUL_PINPAD_MC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
    
    /**
     * Guarda la trama enviada por el pinpad Mastercard
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static boolean guardarTramaReimpPinpadMC (String codRespuesta, 
                                                String mensaje,
                                                String codMoneda,
                                                String codAplicacion,
                                                String codTransaccion,
                                                String codComercio,
                                                String printData,
                                                String codPedido) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codRespuesta==null ? "" : codRespuesta);
        parametros.add(mensaje==null ? "" : mensaje);
        parametros.add(codMoneda==null ? "" : codMoneda);
        parametros.add(codAplicacion==null ? "" : codAplicacion);
        parametros.add(codTransaccion==null ? "" : codTransaccion);
        parametros.add(codComercio==null ? "" : codComercio);
        parametros.add(printData==null ? "" : printData);
        parametros.add(codPedido==null ? "" : codPedido);
        parametros.add(FarmaVariables.vIdUsu);
        
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_TRAMA_REIMP_PINPAD_MC(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
    
    /**
     * Guarda la trama enviada por el pinpad Mastercard
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static boolean guardarTramaRepDetPinpadMC (String codRespuesta, 
                                                String mensaje,
                                                String codMoneda,
                                                String codAplicacion,
                                                String codTransaccion,
                                                String codComercio,
                                                String printData,
                                                String codPedido) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codRespuesta==null ? "" : codRespuesta);
        parametros.add(mensaje==null ? "" : mensaje);
        parametros.add(codMoneda==null ? "" : codMoneda);
        parametros.add(codAplicacion==null ? "" : codAplicacion);
        parametros.add(codTransaccion==null ? "" : codTransaccion);
        parametros.add(codComercio==null ? "" : codComercio);
        parametros.add(printData==null ? "" : printData);
        parametros.add(codPedido==null ? "" : codPedido);
        parametros.add(FarmaVariables.vIdUsu);
        
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_TRAMA_REPD_PINPAD_MC(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
    
    /**
     * Guarda la trama enviada por el pinpad Mastercard
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static boolean guardarTramaRepTotPinpadMC (String codRespuesta, 
                                                String mensaje,
                                                String codMoneda,
                                                String codAplicacion,
                                                String codTransaccion,
                                                String codComercio,
                                                String printData,
                                                String codPedido) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codRespuesta==null ? "" : codRespuesta);
        parametros.add(mensaje==null ? "" : mensaje);
        parametros.add(codMoneda==null ? "" : codMoneda);
        parametros.add(codAplicacion==null ? "" : codAplicacion);
        parametros.add(codTransaccion==null ? "" : codTransaccion);
        parametros.add(codComercio==null ? "" : codComercio);
        parametros.add(printData==null ? "" : printData);
        parametros.add(codPedido==null ? "" : codPedido);
        parametros.add(FarmaVariables.vIdUsu);
        
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_TRAMA_REPT_PINPAD_MC(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
    
    /**
     * Guarda la trama enviada por el pinpad Mastercard
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static boolean guardarTramaCierrePinpadMC (String codRespuesta, 
                                                String mensaje,
                                                String codMoneda,
                                                String codAplicacion,
                                                String codTransaccion,
                                                String codComercio,
                                                String printData,
                                                String codPedido) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codRespuesta==null ? "" : codRespuesta);
        parametros.add(mensaje==null ? "" : mensaje);
        parametros.add(codMoneda==null ? "" : codMoneda);
        parametros.add(codAplicacion==null ? "" : codAplicacion);
        parametros.add(codTransaccion==null ? "" : codTransaccion);
        parametros.add(codComercio==null ? "" : codComercio);
        parametros.add(printData==null ? "" : printData);
        parametros.add(codPedido==null ? "" : codPedido);
        parametros.add(FarmaVariables.vIdUsu);
        
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_TRAMA_CIER_PINPAD_MC(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
    
    /**
     * Obtener el monto de una transaccion de pinpad para su anulacion sin depender de un pedido
     * @author Luis Leiva
     * @since 08.Julio.2013
     */
    public static String obtenerMontoTransaccion(String tipoTarjeta, 
                                                String numReferencia,
                                                String numAutorizacion,
                                                String fecha) 
    {   String res;
        parametros = new ArrayList<String>();
        try
        {   parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(tipoTarjeta==null ? "" : tipoTarjeta);
            parametros.add(numReferencia==null ? "" : numReferencia);
            parametros.add(numAutorizacion==null ? "" : numAutorizacion);
            parametros.add(fecha==null ? "" : fecha);
            log.debug("PTOVENTA_PINPAD.CONS_TRANSACCION_PINPAD(?,?,?,?,?,?,?)",
                                                                    parametros);
            res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.CONS_TRANSACCION_PINPAD(?,?,?,?,?,?,?)",
                                                                    parametros);
        }
        catch (Exception e)
        {   res = "FALSE";
            log.error("",e);
        }
        return res;
    }
    
    /**
     * Obtener la cabecera de un voucher MC
     * @author Luis Leiva
     * @since 26.Sept.2013
     */
    public static String obtenerCabeceraVoucher(String pTipoOperador) 
    {   String res;
        parametros = new ArrayList<String>();
        try
        {   parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pTipoOperador);
            log.debug("PTOVENTA_PINPAD.OBTENER_CABECERA_VOUCHERS(?,?,?)"+parametros);
            res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.OBTENER_CABECERA_VOUCHERS(?,?,?)",
                                                                    parametros);
        }
        catch (Exception e)
        {   res = "FALSE";
            log.error("",e);
        }
        return res;
    }
    
    /**
     * Consulta la forma de pago de un determinado pedido
     * @author Luis Leiva
     * @since 08.Ago.2013
     */
    public static void consFormaPagoPedido2(String numPedido, HashMap<String,String> pResulta)
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedido);
        
        try
        {   String vResulta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.CONS_INF_TRANS_PINPAD2(?,?,?,?)",
                                                                parametros);
            String[] dividido = vResulta.split("Ã");
            pResulta.put("FORMA_PAGO_PED", dividido[0].trim());
            pResulta.put("FECHA_TRANS", dividido[1].trim());
            pResulta.put("NUM_REF_TRANS", dividido[2].trim());
        }
        catch(Exception e)
        {   log.error("",e);
        }
    }
    
    /**
     * Actualiza el flag de la transacción realizada para indicar si no se pudo anular por que el lote se encontraba cerrado
     * @author Luis Leiva
     * @since 11.Dic.2013
     */
    public static boolean guardarIndAnulTransCerr (String codPedido, String tipoTarj) throws SQLException
    {   parametros = new ArrayList<String>();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codPedido==null ? "" : codPedido);
        parametros.add(tipoTarj);
        
        String res = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PINPAD.GRAB_IND_ANUL_TRANS_CERR(?,?,?,?,?)",
                                                                parametros);
        if("FALSE".equals(res.toUpperCase()))
            return false;
        else
            return true;
    }
}
