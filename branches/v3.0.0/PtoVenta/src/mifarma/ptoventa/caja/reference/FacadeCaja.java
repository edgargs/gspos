package mifarma.ptoventa.caja.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import java.util.Map;

import javax.swing.JTable;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.dao.DAOCaja;
import mifarma.ptoventa.caja.dao.FactoryCaja;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import mifarma.ptoventa.reference.UtilityPtoVenta;

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
    
    @Deprecated
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

    public ArrayList getFormasPagoPedido(String strNumPedido){
        ArrayList listaFormaPago = null;
        try{
           listaFormaPago = daoCaja.getFormasPagoPedido(strNumPedido);
        }catch(Exception e){
            log.error("",e);
        }
        return listaFormaPago;
    }
    
    //RHERRERA : MODIFICADO 04/04/2014

    /**
     * @param pnumpedvta
     * @param tblDetallePago
     * @return
     */
    public int pruebaCobro (String pnumpedvta,
    ArrayList a_CodFormaPago,
    ArrayList a_monto ,
    ArrayList a_CodMoneda,
    ArrayList a_XXX ,
    ArrayList a_ImpTotal ,
    ArrayList a_NumTarjeta ,
    ArrayList a_FecVecTarjeta ,
    //--vuelto---//falta
    ArrayList a_NomCliTarjeta,
    ArrayList a_CantCupon ,
    ArrayList a_DniTarjeta ,
    //-- noc q es //
    ArrayList a_CodBouch,
    ArrayList a_CodLote 
    ){
        
        //ArrayList<ArrayList<String>> lstListado = null;
       // ArrayList myArray = new ArrayList();
        String secCompPago = "";
        int v_resultado=0;
        try{
                     
            
           // Map mapParametros = daoCaja.grabarNuevoCobro(pnumpedvta,myArray);
            /*
            Map mapParametros = daoCaja.grabarNuevoCobro(pnumpedvta,
             a_CodFormaPago,
             a_monto ,
             a_CodMoneda,
             a_XXX ,
             a_ImpTotal ,
             a_NumTarjeta ,
             a_FecVecTarjeta ,
            //--vuelto---//falta
             a_NomCliTarjeta,
             a_CantCupon ,
             a_DniTarjeta ,
            //-- noc q es //
             a_CodBouch,
             a_CodLote 
           );  
            */
            Map mapParametros = DBCobroBD.grabarNuevoCobro(pnumpedvta,
             a_CodFormaPago,
             a_monto ,
             a_CodMoneda,
             a_XXX ,
             a_ImpTotal ,
             a_NumTarjeta ,
             a_FecVecTarjeta ,
            //--vuelto---//falta
             a_NomCliTarjeta,
             a_CantCupon ,
             a_DniTarjeta ,
            //-- noc q es //
             a_CodBouch,
             a_CodLote 
           );  
        
            v_resultado = new Integer(mapParametros.get("valor_out").toString());
            VariablesCaja.vmensaje_out = mapParametros.get("V_ERROR_MENSAJE_out").toString();
            VariablesCaja.vNumSecImpresionComprobantes = new Integer(mapParametros.get("V_NUC_SEC_out").toString());
        /*    
             List<BeanResultado> lstRetorno =(List<BeanResultado>)mapParametros.get("V_SEC_COM_PAGO_out");
             lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
            for(int j=0; j<lstListado.size(); j++)
            {
              secCompPago = ((String)((ArrayList)lstListado.get(j)).get(1)).trim();
              
              VariablesCaja.vArrayList_SecCompPago.add(secCompPago);
            }     
                     
        */
        }catch(Exception e){
            log.error("",e);
            
        }
        return v_resultado;
    } 
    
    
}
