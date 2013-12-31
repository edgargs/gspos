package mifarma.ptoventa.caja.dao;

import java.sql.SQLException;

import java.util.ArrayList;

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
public interface DAOCaja extends DAOTransaccion{
    
    
    /**
     * REGISTRAR UNA RECARGA MOVISTAR PARA SER PROCESADA POR EL SIX
     * @author GFonseca
     * @since 12.08.2013
     */ 
    public Long registrarTrsscRecarga(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strMonto, String strTerminal, String strComercio, 
                                           String strUbicacion, String strNroTelefono, String strNumPedido,
                                           String strUsuario
                                           )throws Exception;
    
    /**
     * OBTENER EL ESTADO DE UNA RECARGA PARA MOSTRAR SUS DATOS
     * @author GFonseca
     * @since 14.08.2013
     */
    public String obtenerEstadoTrssc(String pCodGrupoCia, String strCodCia, String strCodLocal, String strNumPedido
                                    )throws SQLException;
    
    
    /**
     * OBTENER LA DESCRIPCION DE ERRORES DEL SIX
     * @author GFonseca
     * @since 14.08.2013
     */
    public ArrayList obtenerDescErrorSix(String strCodErrorSix)throws SQLException;
    
    
    /**
     * REGISTRAR UNA VENTA CON TARJETA CMR
     * @author GFonseca
     * @since 16.08.2013
     */
    public Long registrarTrsscVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strNroTarjeta, String strMonto,
                                           String strTerminal, String strComercio, String strUbicacion,
                                           String strNroCuotas, String strIdCajero, String strNroDoc,                                           
                                           String strUsuario)throws SQLException;

    /**
     * OBTIENE LAS OPCIONES BLOQUEADAS DEL SISTEMA
     * @author CVILCA
     * @since 18.10.2013
     */
    public ArrayList obtenerOpcionesBloqueadas()throws SQLException;    
}
