package mifarma.ptoventa.caja.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.dao.DAOCaja;
import mifarma.ptoventa.caja.dao.FactoryCaja;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FacadeCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FacadeCaja {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeCaja.class);
    
    private DAOCaja daoCaja;
    
    public FacadeCaja() {
        super();
        daoCaja = FactoryCaja.getDAOCaja(TipoImplementacionDAO.MYBATIS);
    }

    public String procesarRecargaVirtual() {
        return null;
    }
    
    
    public Long registrarTrsscRecarga(String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                              String strTipoRcd, String strMonto, String strTerminal, String strComercio, 
                                              String strUbicacion, String strNroTelefono, String strNumPedido,
                                              String strUsuario) throws Exception {        
        Long tmpCodigo = null;
        
        daoCaja.openConnection();
        try{
            tmpCodigo = daoCaja.registrarTrsscRecarga(FarmaVariables.vCodGrupoCia,FarmaVariables.vCodCia,FarmaVariables.vCodLocal, 
                                                            strTipMsjRecau, strEstTrsscRecau, strTipoTrssc,
                                                            strTipoRcd, strMonto, strTerminal, strComercio, 
                                                            strUbicacion, strNroTelefono, strNumPedido,
                                                            strUsuario);
            daoCaja.commit();
        }catch(Exception e){
            daoCaja.rollback();
            log.error("",e);
            throw e;
        }
        
        return tmpCodigo;
    }
    
    
    public String obtenerEstadoTrssc(String strNumPedido){        
        String tmpEst = null;
        try{
            tmpEst = daoCaja.obtenerEstadoTrssc(FarmaVariables.vCodGrupoCia,FarmaVariables.vCodCia,FarmaVariables.vCodLocal, 
                                              strNumPedido);
        }catch(SQLException sqlE){
            log.error("ERROR ES => "+sqlE.getMessage());
        }
        return tmpEst;
    }
    
    
    public ArrayList obtenerDescErrorSix(String strCodErrorSix){        
        ArrayList tmpDataDesc = null;
        try{
            tmpDataDesc = daoCaja.obtenerDescErrorSix(strCodErrorSix);
        }catch(SQLException sqlE){
            log.error("ERROR ES => "+sqlE.getMessage());
        }
        return tmpDataDesc;
    }  
    
    

    public Long registrarTrsscVentaCMR(  String strNroTarjeta, String strMonto, String strTerminal, String strComercio, 
                                         String strUbicacion, String strNroCuotas, String strIdCajero, String strNroDoc,
                                         String strUsuario){        
        Long tmpCodigo = null;
        try{
            tmpCodigo = daoCaja.registrarTrsscVentaCMR( FarmaVariables.vCodGrupoCia,
                                                        FarmaVariables.vCodCia,
                                                        FarmaVariables.vCodLocal,
                                                        ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, 
                                                        ConstantsRecaudacion.ESTADO_SIX_PENDIENTE, 
                                                        ConstantsRecaudacion.TRNS_CMPRA_VNTA,
                                                        ConstantsRecaudacion.TIPO_REC_VENTA_CMR,                                                      
                                                        strNroTarjeta, 
                                                        strMonto,
                                                        strTerminal, 
                                                        strComercio, 
                                                        strUbicacion,
                                                        strNroCuotas, 
                                                        strIdCajero, 
                                                        strNroDoc,
                                                        strUsuario);             
        }catch(SQLException sqlE){
            log.error("ERROR ES => "+sqlE.getMessage());
        }
        return tmpCodigo;
    }
    
    public ArrayList obtenerOpcionesBloqueadas(){
        ArrayList listaOpciones = null;
        try{
            listaOpciones = daoCaja.obtenerOpcionesBloqueadas();
        }catch(SQLException sqlE){
            log.error("ERROR ES => "+sqlE.getMessage());
        }
        return listaOpciones;
    }   
    
    
}
