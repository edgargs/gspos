package mifarma.ptoventa.caja.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Map;

import mifarma.ptoventa.reference.DAOTransaccion;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DAOCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface DAOCaja extends DAOTransaccion {


    /**
     * REGISTRAR UNA RECARGA MOVISTAR PARA SER PROCESADA POR EL SIX
     * @author GFonseca
     * @since 12.08.2013
     */
    public Long registrarTrsscRecarga(String pCodGrupoCia, String strCodCia, String strCodLocal, String strTipMsjRecau,
                                      String strEstTrsscRecau, String strTipoTrssc, String strTipoRcd, String strMonto,
                                      String strTerminal, String strComercio, String strUbicacion,
                                      String strNroTelefono, String strNumPedido, String strUsuario) throws Exception;

    /**
     * OBTENER EL ESTADO DE UNA RECARGA PARA MOSTRAR SUS DATOS
     * @author GFonseca
     * @since 14.08.2013
     */
    public String obtenerEstadoTrssc(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                     String strNumPedido) throws SQLException;


    /**
     * OBTENER LA DESCRIPCION DE ERRORES DEL SIX
     * @author GFonseca
     * @since 14.08.2013
     */
    public ArrayList obtenerDescErrorSix(String strCodErrorSix) throws SQLException;


    /**
     * REGISTRAR UNA VENTA CON TARJETA CMR
     * @author GFonseca
     * @since 16.08.2013
     */
    public Long registrarTrsscVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                       String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                       String strTipoRcd, String strNroTarjeta, String strMonto, String strTerminal,
                                       String strComercio, String strUbicacion, String strNroCuotas,
                                       String strIdCajero, String strNroDoc, String strUsuario) throws SQLException;

    /**
     * OBTIENE LAS OPCIONES BLOQUEADAS DEL SISTEMA
     * @author CVILCA
     * @since 18.10.2013
     */
    public ArrayList obtenerOpcionesBloqueadas() throws SQLException;

    /**
     * Obtener las formas de pago de un pedido, para abrir la gabeta en caso sea efectivo.
     * @author GFonseca
     * @since 27.Dic.2013
     */
    public ArrayList getFormasPagoPedido(String strNumPedido);

    /**
     * Proceso cobro.
     * @author RHERRERA
     * @since 04.Abr.2014
     */
    public Map grabarNuevoCobro(String pNumPedVta, ArrayList a_CodFormaPago, ArrayList a_monto, ArrayList a_CodMoneda,
                                ArrayList a_XXX, ArrayList a_ImpTotal, ArrayList a_NumTarjeta,
                                ArrayList a_FecVecTarjeta,
        //--vuelto---//falta
        ArrayList a_NomCliTarjeta, ArrayList a_CantCupon, ArrayList a_DniTarjeta,
        //-- noc q es //
        ArrayList a_CodBouch, ArrayList a_CodLote) throws SQLException;


}
