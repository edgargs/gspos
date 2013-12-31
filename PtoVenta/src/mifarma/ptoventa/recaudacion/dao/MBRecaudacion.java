package mifarma.ptoventa.recaudacion.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.psicotropicos.dao.MapperPsicotropicos;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recaudacion.reference.VariablesRecaudacion;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.SqlSession;

public class MBRecaudacion implements DAORecaudacion{
    private static final Logger log = LoggerFactory.getLogger(MBRecaudacion.class);
    private SqlSession sqlSession = null;
    private MapperRecaudacion mapper = null;
    private MapperRecaudacionTrsscSix mapperTrsscSix = null;
    UtilityPtoVenta utilityPtoVenta =new UtilityPtoVenta();
    
    public String grabaCabRecaudacion(String strCodGrupoCia, String strCodCia, String strCodLocal, 
                                      String pNroTarjeta, String pTipoRcd, String pTipoPago, String pEstRcd, 
                                      String pEstCuenta, String pCodCliente, String pTipMoneda, String pImTotal, 
                                      String pImTotalPago, String pImMinPago, String pValTipCamb, String pFechaVencRecau, 
                                      String pNomCliente, String pFecVenTrj, String pFecCreaRecauPago, String pUsuCreaRecauPago, 
                                      String pFecModRecauPago, String pUsuModRecauPago, String pCodAutorizacion, String pSecMovCaja,
                                      String pNumPedido, String pTipoProdServ, String pNumRecibo) { 
        String tmpCodigo = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",strCodGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);
        mapParametros.put("cCod_Local_in",strCodLocal);
        mapParametros.put("cNro_Tarjeta_in",pNroTarjeta);
        mapParametros.put("cTipo_Rcd_in",pTipoRcd);
        mapParametros.put("cTipo_Pago_in",pTipoPago);
        mapParametros.put("cEst_Rcd_in",pEstRcd);
        mapParametros.put("cEst_Cuenta_in",pEstCuenta);
        mapParametros.put("cCod_Cliente_in",pCodCliente);
        mapParametros.put("cTip_Moneda_in",pTipMoneda);
        mapParametros.put("nIm_Total_in",FarmaUtility.getDecimalNumber(pImTotal));
        mapParametros.put("nIm_Total_Pago_in",FarmaUtility.getDecimalNumber(pImTotalPago));
        mapParametros.put("nIm_Min_Pago_in",FarmaUtility.getDecimalNumber(pImMinPago));
        mapParametros.put("nVal_Tip_Camb_in",FarmaUtility.getDecimalNumber(pValTipCamb));
        mapParametros.put("cFecha_Venc_Recau_in",pFechaVencRecau);
        mapParametros.put("cNom_Cliente_in",pNomCliente);
        mapParametros.put("cFec_Ven_Trj_in",pFecVenTrj);
        mapParametros.put("cFec_Crea_Recau_Pago_in",pFecCreaRecauPago);
        mapParametros.put("cUsu_Crea_Recau_Pago_in",pUsuCreaRecauPago);
        mapParametros.put("cFec_Mod_Recau_Pago_in",pFecModRecauPago);
        mapParametros.put("cUsu_Mod_Recau_Pago_in",pUsuModRecauPago);
        mapParametros.put("cCod_Autorizacion_in",pCodAutorizacion);
        mapParametros.put("cSec_Mov_Caja_in",pSecMovCaja);
        mapParametros.put("cNum_Pedido_in",pNumPedido);
        mapParametros.put("cTipo_Prod_Serv_in",pTipoProdServ);
        mapParametros.put("cNum_Recibo_in",pNumRecibo);
        try{    
            openConnection();
            mapper.grabaCabRecaudacion(mapParametros);
            tmpCodigo = (String)mapParametros.get("vIndicador");
            commit();
        }
        catch(Exception e){   
            rollback();
            log.error("",e);
        }                
        return tmpCodigo;
    }
    
    public ArrayList obtenerRcdPendiente(String strCodGrupoCia, String strCodCia, String strCodLocal, String strCodRecau){
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",strCodGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);
        mapParametros.put("cCod_Local_in",strCodLocal);
        mapParametros.put("cCod_Rcd_in",strCodRecau);

        try
        {   openConnection();
            mapper.listarRcdPedien(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        }
        catch(Exception e)
        {   log.error("",e);
            tmpArray=null;
        }
        finally
        {   sqlSession.close();
        }
               
        if(tmpArray.size() > 1){
            log.error("Se encontro mas de una recaudacion");
        }
        return tmpArray;
    }
    
    public ArrayList obtenerCodFormsPagoRCD(){
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();

        try
        {   openConnection();
            mapper.listarCodFormPago(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoColum(tmpLista); 
        }
        catch(Exception e)
        {   log.error("",e);
            tmpArray=null;
        }
        finally
        {   sqlSession.close();
        }
        return tmpArray;
    }
   
    public void grabaFormPagoRCD(String strCodGrupoCia, String strCodCia, String strCodLocal, String pCodRecau, String pCodFormaPago, String pImpTotal,
                                 String pTipMoneda, String pValTipCambio, String pValVuelto, String pImTotalPago, String pFecCreaRecauPago,
                                 String pUsuCreaRecauPago, String pFecModRecauPago, String pUsuModRecauPago) throws SQLException{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",strCodGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);
        mapParametros.put("cCod_Local_in",strCodLocal);
        mapParametros.put("cCod_Recau_in",pCodRecau);
        mapParametros.put("cCod_Forma_Pago_in",pCodFormaPago);
        mapParametros.put("cImp_Total_in",FarmaUtility.getDecimalNumber(pImpTotal));
        mapParametros.put("cTip_Moneda_in",pTipMoneda);
        mapParametros.put("cVal_Tip_Cambio_in",FarmaUtility.getDecimalNumber(pValTipCambio));
        mapParametros.put("cVal_Vuelto",FarmaUtility.getDecimalNumber(pValVuelto));
        mapParametros.put("cIm_Total_Pago_in",FarmaUtility.getDecimalNumber(pImTotalPago));
        mapParametros.put("cFec_Crea_Recau_Pago_in",pFecCreaRecauPago);
        mapParametros.put("cUsu_Crea_Recau_Pago_in",pUsuCreaRecauPago);
        mapParametros.put("cFec_Mod_Recau_Pago_in",pFecModRecauPago);
        mapParametros.put("cUsu_Mod_Recau_Pago_in",pUsuModRecauPago);
           
        try
        {   openConnection();
            mapper.grabarFormasPagoRCD(mapParametros);
            commit();
        }
        catch(Exception e)
        {   rollback();
            log.error("",e);
        }
       
    }
    
    public void cambiarIndicadorRCD(String strCodGrupoCia, String strCodCia, String strCodLocal, String pCodRecau, String pIndEstado, String pUsuModRcd, 
                                    String pFecModRcd, String pEstImpresion, Long codTrssc, String estTrsscSix, String codAutorizacion,
                                    String secMovCaja, String strFechaOrigen, 
                                    String nroCuotas, 
                                    String fechaVencCuota,
                                    String strMontoPagarCuota
                                    ){
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",strCodGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);
        mapParametros.put("cCod_Local_in",strCodLocal);
        mapParametros.put("cCod_Recau_in",pCodRecau);
        mapParametros.put("cInd_Recau_in",pIndEstado);
        mapParametros.put("cUsu_Mod_Rcd_in",pUsuModRcd);
        mapParametros.put("cSecMovCaja_in",VariablesCaja.vSecMovCaja);
        mapParametros.put("cEstado_ImpRecaudacion_In",pEstImpresion);
        mapParametros.put("cCod_Trssc_In", codTrssc != null ? codTrssc : "");
        mapParametros.put("cEst_TrsscSix_in", estTrsscSix);
        mapParametros.put("cCod_Autorizacion_in", codAutorizacion !=null ? codAutorizacion : ""); 
        mapParametros.put("cSecMovCaja_in", secMovCaja);
        mapParametros.put("cFech_Orig_in", strFechaOrigen);        
        mapParametros.put("cNro_Cuotas_in", nroCuotas);
        mapParametros.put("cFec_Venc_Cuota_in", fechaVencCuota);
        mapParametros.put("cMonto_Pagar_Cuota_in", FarmaUtility.getDecimalNumber(strMontoPagarCuota));
        
        try
        {   openConnection();
            mapper.cambiarEstadoRCD(mapParametros);
            commit();
            }
        catch(Exception e)
        {  rollback();
            log.error("",e);
         
        }
        
    }    
    
    public ArrayList obtenerRcdCanceladas(String strCodGrupoCia, String strCodCia, String strCodLocal, String pCodRcd, String pMonRcd, String pTipocobro) throws Exception{
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",strCodGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);
        mapParametros.put("cCod_Local_in",strCodLocal);       
        if(pCodRcd.equals("") && pMonRcd.equals("") ){
            mapParametros.put("cCod_Rcd_in",ConstantsRecaudacion.LISTAR_TODO);
            mapParametros.put("cMonto_Rcd_in",FarmaUtility.getDecimalNumber(pMonRcd));
        }else{
            mapParametros.put("cCod_Rcd_in",pCodRcd);       
            mapParametros.put("cMonto_Rcd_in",FarmaUtility.getDecimalNumber(pMonRcd));
        }
        mapParametros.put("cTipoCobro_in", pTipocobro);               
            
        mapper.listarRcdCance(mapParametros);
        tmpLista = (List<BeanResultado>)mapParametros.get("listado");
        tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista); 

        return tmpArray;
    }    
    
    public ArrayList obtenerMontoPagarCitiPres(String pCodCliCiti){
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCod_Cli_in",pCodCliCiti);

        try
        {   openConnection();
            mapper.obtenerMontoPagarCitiPres(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoColum(tmpLista); 
        }
        catch(Exception e)
        {   log.error("",e);
            tmpArray=null;
        }
        finally
        {   sqlSession.close();
        }
       return tmpArray;
    }

    
    public ArrayList getInfoTarjetaRecaudacion(String pCodGrupoCia, String pNroTarjeta, String pTipOrigenPago) {
             ArrayList tmpArray = new ArrayList();
       
             List<BeanResultado> tmpLista = null;
             
             Map<String, Object> mapParametros = new HashMap<String, Object>();
             mapParametros.put("cCodGrupoCia_in",pCodGrupoCia);
             mapParametros.put("cCodTarj_in",pNroTarjeta);
             mapParametros.put("cTipOrigen_in",pTipOrigenPago);
             try
             {
                openConnection();
                mapper.getInfoTarjRecaudacion(mapParametros);             
                tmpLista = (List<BeanResultado>)mapParametros.get("infoBinRecau");
                tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);        
             }catch(Exception e){
                    log.error("",e);
                    tmpArray=null;
                 }
             finally
             {   sqlSession.close();
             }
        
        return tmpArray;        
    } 
    
     
    /**
    * Arma la trama de consulta para Recaudacion Prestamos Citibank 
    * @author GFonseca
    * @since 17.Junio.2013
    */
    public String getTramaPresCiti(String opConsulta, String codConsulta, String tipoMoneda, String indTransaccion, String codLocal){   
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        String resultado="";
        mapParametros.put("cFlag_Cod_in", opConsulta);
        mapParametros.put("cCod_in", codConsulta);
        mapParametros.put("cTip_Moneda_in", tipoMoneda);
        mapParametros.put("cTip_trans_in", indTransaccion);
        mapParametros.put("cCod_local_in", codLocal);
        try{   
            openConnection();
            mapper.getTramaPresCiti(mapParametros);
            resultado = (String)mapParametros.get("trama");
        }catch(Exception e){   
            log.error("",e);
            resultado="";
        }finally{   
            sqlSession.close();
        }
        return resultado;
    }

    /**
    * Envia una trama de consulta hacia el servidor central en caso de prestamos Citibank y retorna una trama de respuesta
    * @author GFonseca
    * @since 17.Junio.2013
    */    
    public String setTramaPresCiti(String trama){   
        String resultado="";
        SqlSession sqlSessionFasa = null;
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        mapParametros.put("pentrada", trama);        
        try{   
            sqlSessionFasa = MyBatisUtil.getFasaSqlSessionFactory().openSession();
            MapperRecaudacion mapperFasa = sqlSessionFasa.getMapper(MapperRecaudacion.class);
            mapperFasa.setTramaPresCiti(mapParametros);
            resultado = (String)mapParametros.get("psalida");
        }catch(Exception e){   
            log.error("",e);
        }finally{   
            sqlSessionFasa.close();            
        }
        return resultado;        
    }
    
    /**
    * Procesa la trama recibida desde el servidor central en caso de prestamos Citibank 
    * @author GFonseca
    * @since 17.Junio.2013
    */    
    public ArrayList obtenerDatosPagarCitiPres(String trama){   
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cTrama_PresCiti",trama);
        
        try
        {   openConnection();
            mapper.obtenerDatosPagarCitiPres(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        }
        catch(Exception e)
        {   log.error("",e);
            tmpArray=null;
        }
        finally
        {   sqlSession.close();
        }
        return tmpArray;
    }

    /**
     * Impresión de comprobante de recaudación
     * @author RLLANTOY
     * @since 28.06.2013
     */
    
    public String impCompPagoRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion) {
            
            String strHtml = "";
                
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            
            mapParametros.put("cCodGrupoCia_in",pCodGrupoCia);
            mapParametros.put("cCod_Cia_in",strCodCia);
            mapParametros.put("cCod_Local_in",strCodLocal);
            mapParametros.put("cNum_Recaudacion_in",pNumeroRecaudacion);
            
            try
            {   openConnection();
                mapper.getCompPagoRecau(mapParametros);
                strHtml = (String)mapParametros.get("vIndicador");
                }
            catch(Exception e)
            {   log.error("",e);
                strHtml="";
            }
            finally
            {   sqlSession.close();
            }    
            return strHtml;
   }
    
     /**
      * Retorna el formato HTML para realizar la impresión unicamente para el caso de la recaudación VENTA CMR (06)
      * @author GFonseca
      * @since 20.08.2013
      */
    public String getCompPagoRecauVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion, String strNumCuotas, 
                                           String strMontoPagarCuota, String strFechaVencCuota, String strCodAutorizacion,
                                           String pNroCaja, String pTipCopia) {            
            String strHtml = "";                
            Map<String, Object> mapParametros = new HashMap<String, Object>();            
            mapParametros.put("cCodGrupoCia_in",pCodGrupoCia);
            mapParametros.put("cCod_Cia_in",strCodCia);
            mapParametros.put("cCod_Local_in",strCodLocal);
            mapParametros.put("cCod_Recau_in",pNumeroRecaudacion);
            mapParametros.put("cNum_Cuotas_in",strNumCuotas);
            mapParametros.put("cMonto_Pagar_Cuota_in",strMontoPagarCuota);
            mapParametros.put("cFecha_Venc_Cuota_in",strFechaVencCuota);  
            mapParametros.put("cCod_Autorizacion_in",strCodAutorizacion);          
            mapParametros.put("cCod_Caja_in",pNroCaja);  
            mapParametros.put("cTip_Copia_in",pTipCopia);  
        
            try{
                openConnection();
                mapper.getCompPagoRecauVentaCMR(mapParametros);
                strHtml = (String)mapParametros.get("vTemplateHTML");
            }catch(Exception e){   
                log.error("",e);
                strHtml="";
            }finally{   
                sqlSession.close();
            }    
            return strHtml;
    }
     
    /**
     * Retorna el formato HTML para realizar la impresión anulacion venta CMR (06)
     * @author GFonseca
     * @since 25.10.2013
     */
    public String getCompAnulRecauVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal, String strCodRecau,
                                          String pNroCaja, String pTipCopia) {            
           String strHtml = "";                
           Map<String, Object> mapParametros = new HashMap<String, Object>();            
           mapParametros.put("cCodGrupoCia_in",pCodGrupoCia);
           mapParametros.put("cCod_Cia_in",strCodCia);
           mapParametros.put("cCod_Local_in",strCodLocal);
           mapParametros.put("cCod_Recau_in",strCodRecau);     
           mapParametros.put("cCod_Caja_in",pNroCaja);  
           mapParametros.put("cTip_Copia_in",pTipCopia);  
       
           try{
               openConnection();
               mapper.getCompAnulRecauVentaCMR(mapParametros);
               strHtml = (String)mapParametros.get("vTemplateHTML");
           }catch(Exception e){   
               log.error("",e);
               strHtml="";
           }finally{   
               sqlSession.close();
           }    
           return strHtml;
    }
    
    /**
     * Obtiene estado de impresión de comprobante de recaudación
     * @author RLLANTOY
     * @since 28.06.2013
     */
    public String getEstadoImpRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion) {
            String estado="";
                
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            
            mapParametros.put("cCodGrupoCia_in",pCodGrupoCia);
            mapParametros.put("cCod_Cia_in",strCodCia);
            mapParametros.put("cCod_Local_in",strCodLocal);
            mapParametros.put("cNum_Recaudacion_in",pNumeroRecaudacion);
            
            try
            {   openConnection();
                mapper.getEstImpRecaudacion(mapParametros);
                estado = (String)mapParametros.get("vIndicador");
            }
            catch(Exception e)
            {   log.error("",e);
                estado="";
            }
            finally
            {   sqlSession.close();
            }    
            return estado;
        }
    
    /**
     * Actualiza estado de impresión de recaudación
     * @author RLLANTOY
     * @since 28.06.2013
     */
    
    public void updateEstadoImpRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion, String pEstImpRecaudacion) {
           
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            
            mapParametros.put("cCodGrupoCia_in",pCodGrupoCia);
            mapParametros.put("cCod_Cia_in",strCodCia);
            mapParametros.put("cCod_Local_in",strCodLocal);
            mapParametros.put("cNum_Recaudacion_in",pNumeroRecaudacion);
            mapParametros.put("cEstado_ImpRecaudacion_In",pEstImpRecaudacion);
            try
            {   openConnection();
                mapper.updateEstImpresionRecaudacion(mapParametros);
                commit();
            }
            catch(Exception e)
            {   log.error("",e);
                rollback();
            }
                    
        }
    
   public String anularRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion, String vNumCaja, 
                                   String pUsuModRecau, Long codTrsscAnul) {
            HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            String resultado="";
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("cCod_Cia_in", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("cCodReca_in", pNumeroRecaudacion);
            mapParametros.put("nNumCajaPago_in", vNumCaja); //vNumCaja
            mapParametros.put("nUsuModRecauPago_in", pUsuModRecau);
            mapParametros.put("nCod_TrsscAnul_in", codTrsscAnul != null ? codTrsscAnul : "" );
            try{   
            openConnection();
            mapper.anularRecaudacion(mapParametros);
            resultado = (String)mapParametros.get("vCod_Recau");
            commit();
            }catch(Exception e){   
                log.error("",e);
                resultado="";
                rollback();
            }
           return resultado;          
    }
   
   
    /**
     * REALIZA LA ANULACION DE UNA RECAUDACION DE VENTA CMR (NO TIENE FORMAS DE PAGO)
     * @author GFonseca
     * @since 25.06.2013
     */
    public String anularRecaudacionVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion, String vNumCaja, 
                                                String pUsuModRecau, Long codTrsscAnul) {
             HashMap<String,Object> mapParametros = new HashMap<String,Object>();
             String resultado="";
             mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
             mapParametros.put("cCod_Cia_in", strCodCia);
             mapParametros.put("cCod_Local_in", strCodLocal);
             mapParametros.put("cCodReca_in", pNumeroRecaudacion);
             mapParametros.put("nNumCajaPago_in", vNumCaja);
             mapParametros.put("nUsuModRecauPago_in", pUsuModRecau);
             mapParametros.put("nCod_TrsscAnul_in", codTrsscAnul != null ? codTrsscAnul : "" );
             try{   
             openConnection();
             mapper.anularRecaudacionVentaCMR(mapParametros);
             resultado = (String)mapParametros.get("vCod_Recau");
             commit();
             }catch(Exception e){   
                 log.error("",e);
                 resultado="";
                 rollback();
             }
            return resultado;          
     }
    
    /**
     * Obtiene estado de una recaudación
     * @author RLLANTOY
     * @since 09.07.2013
     */
    public String getEstadoRecaudacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion) {
            HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            String resultado="";
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("Ccod_Cia_In", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("Cnum_Recaudacion_In", pNumeroRecaudacion);
            try{   openConnection();
            mapper.getEstRecaudacion(mapParametros);
            resultado = (String)mapParametros.get("Vest_Recau");
            }    catch(Exception e)
                {   log.error("",e);
                    resultado="";
                }
                finally
                {   sqlSession.close();
                }    
           return resultado;          
    }
    
    /**
    * Obtiene el tiempo de anulación de una recaudación
    * @author RLLantoy
    * @since 11.07.2013
    */ 
    
    public String valTiempoAnulacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion) {
            HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            String resultado="";
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("cCod_Cia_In", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("cNum_Recaudacion_In", pNumeroRecaudacion);
            try{   openConnection();
            mapper.valTiempoAnulRecaudacion(mapParametros);
            resultado = (String)mapParametros.get("cRepta");
            }    catch(Exception e)
                {   log.error("",e);
                    resultado="";
                }
                finally
                {   sqlSession.close();
                }    
           return resultado;          
    }
    
    
    /**
    * Obtiene el tiempo máximo de anulación de una recaudación
    * @author RLLantoy
    * @param pTipoLlave -- llave para el "TIEMPO_ANULACION" de cualquier recaudación = "RCD"
    * @since 11.07.2013
    */ 
    
    public String tiempoMaxAnulacionRecau(String pTipoLlave) {
            HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            String resultado="";
            mapParametros.put("cTipo_llave", pTipoLlave);
           try{   openConnection();
            mapper.tiempoMaximoAnulRecaudacion(mapParametros);
            resultado = (String)mapParametros.get("vTiempo");
            }    catch(Exception e)
                {   log.error("",e);
                    resultado="";
                }
                finally
                {   sqlSession.close();
                }    
           return resultado;          
    }
    
    
        
    public String actualizarMontoCobradoPresCiti(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumeroRecaudacion) {
            String resultado="";
            HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("cCod_Cia_in", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("cCodRecau_in", pNumeroRecaudacion);
        try
        {   openConnection();
            mapper.actualizarMontoCobradoPresCiti(mapParametros);
            resultado = (String)mapParametros.get("strMontoPagado");
            commit();
        }
        catch(Exception e)
        {   log.error("",e);
            rollback();          
        }
        return resultado;
         
    }  
    
    
    public void actualizarEstRecauTrsscSix(String pCodGrupoCia, String strCodCia, String strCodLocal, String codRecaudacion,
                                           String estRecauTrsscSix, String strCodAutoriz){
            HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("cCod_Cia_in", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("cCodRecau_in", codRecaudacion);
            mapParametros.put("cEstTrsscSix", estRecauTrsscSix);
            mapParametros.put("cCodAutoriz", strCodAutoriz);        
            try{   
                openConnection();
                mapper.actualizarEstRecauTrsscSix(mapParametros);
                commit();
            }catch(Exception e){   
                log.error("",e);
                rollback();            
            }        
    }
    
    
    /**
     * Obtener el DNI de un usuario
     * @author GFonseca
     * @since 19.08.2013  FarmaVariables.vNuSecUsu
     */
    public String obtenerDniUsuario(String numSecUsu){
        String strDniUsuario = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cSecUsuLocal_in", numSecUsu);
        try{    
            openConnection();
            mapper.obtenerDniUsuario(mapParametros);
            strDniUsuario = (String)mapParametros.get("vDNI");
            commit();
        }catch(Exception e){   
            rollback();
            log.error("",e);
        }                
        return strDniUsuario;
        
    
    }
    
    /**
    * Registrar datos de recaudacion en Fasa, para la conciliación
    * @author GFonseca
    * @since 19.Agosto.2013
    */ 
    
    /*
    "#{PLOCAL, jdbcType=NUMERIC}," +                            ok
    "#{PID_VENDEDOR, jdbcType=NUMERIC}," + //DNI                ok
    "#{PFECHA_VENTA, jdbcType=VARCHAR}," +                      ok
    "#{PMONTO_VENTA, jdbcType=NUMERIC}," +                      ok              
    "#{PNUM_CUOTAS, jdbcType=NUMERIC}," + //solo en venta
    "#{PCOD_AUTORIZACION, jdbcType=VARCHAR}," +
    "#{PTRACK2, jdbcType=VARCHAR}," + //solo en venta
    "#{PCOD_AUTORIZACION_PRE, jdbcType=VARCHAR}," +     //solo en venta cuando es anulacion
    "#{PFPA_VALORXCUOTA, jdbcType=NUMERIC}," + //solo en venta
    "#{PCAJA, jdbcType=NUMERIC}," +
    "#{PTIPO_TRANSACCION, jdbcType=NUMERIC}," + //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion
    "#{PCODISERV, jdbcType=VARCHAR}," + //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank                
    "#{PFPA_NUM_OBJ_PAGO, jdbcType=VARCHAR}," +
    "#{PNOMBCLIE, jdbcType=VARCHAR}," +
    "#{PVOUCHER, jdbcType=NUMERIC}," + //Nro de Comprobante 
    "#{PNRO_COMP_ANU, jdbcType=NUMERIC}," + //Anulacion-Nro de Comprobante origen
    "#{PFECH_COMP_ANU, jdbcType=VARCHAR}," + //Anulacion-Fecha Origen del Comprobante  
    "#{PCODIAUTOORIG, jdbcType=VARCHAR}," + //Anulacion-Codigo autorizacion Origen    
    "#{PFPA_TIPOCAMBIO, jdbcType=NUMERIC}," + //solo en recaudacion si es 2 Dolares 
    "#{PFPA_NROTRACE, jdbcType=NUMERIC}," + //trace de la venta o Pago 
    "#{PCOD_ALIANZA, jdbcType=NUMERIC}," +
    "#{PCOD_MONEDA_TRX, jdbcType=NUMERIC}," + //Recaudacion 1 Soles , 2 Dolares, en venta tipo 7(CMR),9 Ripley, 5 Tcredit, 6 Tdebito , 
    "#{PMON_ESTPAGO, jdbcType=VARCHAR}," +
    "#{Salida, mode=OUT, jdbcType=VARCHAR}" +
    */  

    public String setDatosRecauConciliacion(String PLOCAL,
                                            String PID_VENDEDOR, 
                                            String PFECHA_VENTA, 
                                            double PMONTO_VENTA,
                                            double PNUM_CUOTAS,
                                            String PCOD_AUTORIZACION, 
                                            String PTRACK2,
                                            String PCOD_AUTORIZACION_PRE,
                                            double PFPA_VALORXCUOTA,                                                                         
                                            int PCAJA,
                                            String PTIPO_TRANSACCION,
                                            String PCODISERV,
                                            String PFPA_NUM_OBJ_PAGO,                                                                          
                                            String PNOMBCLIE,                 
                                            long PVOUCHER, 
                                            long PNRO_COMP_ANU,
                                            String PFECH_COMP_ANU,
                                            String PCODIAUTOORIG,
                                            double PFPA_TIPOCAMBIO,
                                            long PFPA_NROTRACE, 
                                            int PCOD_ALIANZA, 
                                            int PCOD_MONEDA_TRX,
                                            String PMON_ESTPAGO
                                                                ){   
        SqlSession sqlSessionFasa = null;
        String vRpta = "";
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();

        try{
            mapParametros.put("PLOCAL", UtilityRecaudacion.convertirStringToInt(PLOCAL)); //NUMERIC
            mapParametros.put("PID_VENDEDOR", UtilityRecaudacion.convertirStringToInt(PID_VENDEDOR)); //NUMERIC
            mapParametros.put("PFECHA_VENTA", PFECHA_VENTA); 
            mapParametros.put("PMONTO_VENTA", PMONTO_VENTA); //NUMERIC
            mapParametros.put("PNUM_CUOTAS", PNUM_CUOTAS); //NUMERIC
            mapParametros.put("PCOD_AUTORIZACION", PCOD_AUTORIZACION); //VARCHAR
            mapParametros.put("PTRACK2", PTRACK2); //VARCHAR
            mapParametros.put("PCOD_AUTORIZACION_PRE", PCOD_AUTORIZACION_PRE); //VARCHAR     
            mapParametros.put("PFPA_VALORXCUOTA", PFPA_VALORXCUOTA); //NUMERIC
            mapParametros.put("PCAJA", PCAJA); //NUMERIC
            mapParametros.put("PTIPO_TRANSACCION", UtilityRecaudacion.convertirStringToInt(PTIPO_TRANSACCION)); //NUMERIC 
            mapParametros.put("PCODISERV", PCODISERV); //VARCHAR  
            mapParametros.put("PFPA_NUM_OBJ_PAGO", PFPA_NUM_OBJ_PAGO); //VARCHAR                   
            mapParametros.put("PNOMBCLIE", PNOMBCLIE);   //VARCHAR                    
            mapParametros.put("PVOUCHER", PVOUCHER); //NUMERIC         
            mapParametros.put("PNRO_COMP_ANU", PNRO_COMP_ANU);  //NUMERIC                  
            mapParametros.put("PFECH_COMP_ANU", PFECH_COMP_ANU);   //VARCHAR            
            mapParametros.put("PCODIAUTOORIG", PCODIAUTOORIG);   //VARCHAR 
            mapParametros.put("PFPA_TIPOCAMBIO", PFPA_TIPOCAMBIO); //NUMERIC 
            mapParametros.put("PFPA_NROTRACE", PFPA_NROTRACE);  //NUMERIC maximo 4 (el six genera)
            mapParametros.put("PCOD_ALIANZA", PCOD_ALIANZA);    //NUMERIC         
            mapParametros.put("PCOD_MONEDA_TRX", new Integer(PCOD_MONEDA_TRX));  //NUMERIC
            mapParametros.put("PMON_ESTPAGO", PMON_ESTPAGO);  //VARCHAR 
            
            sqlSessionFasa = MyBatisUtil.getFasaSqlSessionFactory().openSession();
            MapperRecaudacion mapperFasa = sqlSessionFasa.getMapper(MapperRecaudacion.class);
            mapperFasa.setDatosRecauConciliacion(mapParametros);  
            vRpta = mapParametros.get("Salida").toString();
            log.info("vRpta : "+vRpta);
        }catch(Exception e){   
            log.error("",e);
        }finally{   
            sqlSessionFasa.close();            
        }
        return vRpta;
    }
    
    
    /**
    * Registrar datos de recaudacion en Fasa, para la conciliación de recargas
    * @author GFonseca
    * @since 27.Agosto.2013
    */    

    public String setDatosRecargaConciliacion(String PCL_COD_ID_CONCENTRADOR, 
                                          String PCL_NUMERO_TELEFONO, 
                                          String PCL_COD_AUTORIZACION,
                                              
                                          String PCL_COD_VENDEDOR,
                                          String PCL_FECHA_VENTA,
                                          String PCL_HORA_VENTA,
                                          String PCL_NUMERO_DOCUMENTO,
                                          String PCL_COD_COMERCIO,
                                          String PCL_COD_TERMINAL,
                                          String PCL_MONTO_VENTA,
                                          String PCL_ID_TRANSACCION
                                          ){   
        SqlSession sqlSessionFasa = null;
        String vRpta = "";
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();

        try{
            mapParametros.put("PCL_COD_ID_CONCENTRADOR", PCL_COD_ID_CONCENTRADOR);   
            mapParametros.put("PCL_NUMERO_TELEFONO", PCL_NUMERO_TELEFONO); 
            mapParametros.put("PCL_COD_AUTORIZACION", PCL_COD_AUTORIZACION); 
            
            mapParametros.put("PCL_COD_VENDEDOR", PCL_COD_VENDEDOR); 
            mapParametros.put("PCL_FECHA_VENTA", PCL_FECHA_VENTA); 
            mapParametros.put("PCL_HORA_VENTA", PCL_HORA_VENTA); 

            mapParametros.put("PCL_NUMERO_DOCUMENTO", UtilityRecaudacion.convertirStringToInt(PCL_NUMERO_DOCUMENTO)); 
            mapParametros.put("PCL_COD_COMERCIO", PCL_COD_COMERCIO); 
            mapParametros.put("PCL_COD_TERMINAL", PCL_COD_TERMINAL); 
            
            mapParametros.put("PCL_MONTO_VENTA", new Double(PCL_MONTO_VENTA).intValue()); 
            mapParametros.put("PCL_ID_TRANSACCION", PCL_ID_TRANSACCION); 
            
            sqlSessionFasa = MyBatisUtil.getFasaSqlSessionFactory().openSession();
            MapperRecaudacion mapperFasa = sqlSessionFasa.getMapper(MapperRecaudacion.class);
            mapperFasa.setDatosRecargaConciliacion(mapParametros);  
            vRpta = mapParametros.get("Salida").toString();           
        }catch(Exception e){   
            log.error("",e);
        }finally{   
            sqlSessionFasa.close();            
        }
        return vRpta;
    }
    
    
    
    /**
    * Obtener el código homologo de un local
    * @author GFonseca
    * @since 19.Agosto.2013
    */              
    public String getCodLocalMigra(String strCodGrupoCia, String strCodCia, String strCodLocal) { 
        String tmpCodigo = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", strCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);
        try{    
            openConnection();
            mapper.getCodLocalMigra(mapParametros);
            tmpCodigo = (String)mapParametros.get("vCodLocal");
            commit();
        }
        catch(Exception e){   
            rollback();
            log.error("",e);
        }                
        return tmpCodigo;
    }
    
    /**
    * Retorna el monto total de recaudaciones asociadas a un movimiento de caja
    * @author GFonseca
    * @since 21.Agosto.2013
    */              
    public String getMontoTotalRecaudacionByMovCaja(String strCodGrupoCia, String strCodCia, String strCodLocal,
                                                    String strSecMovCaja) { 
        String montoTot = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", strCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cSecMovCaja_in", strSecMovCaja);        
        try{    
            openConnection();
            mapper.getMontoTotalRecaudacionByMovCaja(mapParametros);
            montoTot = (String)mapParametros.get("vMontoTot");
            commit();
        }
        catch(Exception e){   
            rollback();
            log.error("",e);
        }                
        return montoTot;
    }

    /**
     * RETORNA DATOS DE UNA RECAUDACION VENTA CMR, PARA SU ANULACION
     * @author GFonseca
     * @since 04.09.2013
     */
    public ArrayList getDatosAnulacionVentaCMR(String strCodGrupoCia, String strCodCia, String strCodLocal, String strNumPedido){
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",strCodGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);
        mapParametros.put("cCod_Local_in",strCodLocal);       
        mapParametros.put("cNum_Pedido_in",strNumPedido);       
        try{   
            openConnection();
            mapper.getDatosAnulacionVentaCMR(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista); 
        }
        catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }finally{   
            sqlSession.close();
        }
        return tmpArray;
    }    
    
       
    /* ----------- TRANSACCIONES CON EL SIX -----------*/  
    
    public Long registrarTrsscConsultaDeudaClaro(String codGrupoCia, String strCodCia, String strCodLocal, String strEstTrsscRecau, String strTipMsjRecau, 
                                                        String tipoTrssc, String tipoRcd, 
                                                    
                                                        String terminal,
                                                        String comercio,    
                                                        String sucursal,
                                                        String ubicacion,
                                                        
                                                        String telefono,
                                                 
                                                        String tipoProdServ,
                                                        String strUsuario) { 
        Long tmpCodigo = null;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in",codGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);    
        mapParametros.put("cCod_Local_in",strCodLocal);    
        mapParametros.put("cTip_Msj_in",strTipMsjRecau);
        mapParametros.put("cEst_Trscc_in",strEstTrsscRecau);    
        mapParametros.put("cTipo_Trssc_in",tipoTrssc);     
        mapParametros.put("cTipo_Rcd_in",tipoRcd);
        
       
        mapParametros.put("cTerminal_in",terminal);
        mapParametros.put("cComercio_in",comercio);
        mapParametros.put("cSucursal_in",sucursal);
        mapParametros.put("cUbicacion_in",ubicacion);
        
        mapParametros.put("cTelefono_in",telefono);
        
        mapParametros.put("cTipoProdServ_in",tipoProdServ);
        
        mapParametros.put("cUsu_Crea_in", strUsuario); 
        
                        
        try{    
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.registrarTrsscConsultaDeudaClaro(mapParametros);
            tmpCodigo = (Long) mapParametros.get("tmpCodigo");
        }catch(Exception e){   
            sqlSession.rollback(true);
            log.error("",e);
        }finally{   
            sqlSession.close();            
        }
        return tmpCodigo;
    }        
     
    public Long registrarTrsscConsultaDeudaCMR(String codGrupoCia, String strCodCia, String strCodLocal, String strEstTrsscRecau, String strTipMsjRecau, 
                                                  String tipoTrssc, String tipoRcd, String strNroTarjeta, int numCuotas) { 
        Long tmpCodigo = null;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in",codGrupoCia);
        mapParametros.put("cCod_Cia_in",strCodCia);    
        mapParametros.put("cCod_Local_in",strCodLocal);    
        mapParametros.put("cTip_Msj_in",strTipMsjRecau);
        mapParametros.put("cEst_Trscc_in",strEstTrsscRecau);    
        mapParametros.put("cTipo_Trssc_in",tipoTrssc);     
        mapParametros.put("cTipo_Rcd_in",tipoRcd);
        mapParametros.put("cNro_Tarjeta_in",strNroTarjeta);
        mapParametros.put("cNro_Cuotas_in",numCuotas);
        try{    
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.registrarTrsscConsultaDeudaCMR(mapParametros);
            tmpCodigo = (Long) mapParametros.get("tmpCodigo");
        }catch(Exception e){   
            sqlSession.rollback(true);
            log.error("",e);
        }finally{   
            sqlSession.close();            
        }
        return tmpCodigo;
    }         
        
    public String obtenerEstTrsscReacudacion(Long strCodRecauTrssc, String strModoRecau) { 
        String tmpCodigo = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCod_Trscc_in",strCodRecauTrssc);
        mapParametros.put("cModo_Trscc_in",strModoRecau);
        try{    
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.obtenerEstTrsscReacudacion(mapParametros);
            tmpCodigo = (String) mapParametros.get("estTrssc");
        }catch(Exception e){   
            log.error("",e);
        }finally{   
            sqlSession.close();            
        }
        return tmpCodigo;
    }  
        
    public ArrayList obtenerDatosTrsscSix(Long strCodRecauTrssc, String strModoRecau){        
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCod_Trscc_in",strCodRecauTrssc);
        mapParametros.put("cModo_Trscc_in",strModoRecau);
        try{   
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.obtenerDatosTrsscSix(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("datosTrsscClaro");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        }catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }finally{   
            sqlSession.close();
        }
        return tmpArray;
    } 

    public Long registrarTrsscPagoDeudaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strNroTarjeta, String strMonto,
                                           String strTerminal, String strComercio, String strUbicacion,
                                           String strNroCuotas, String strNroCaja, String strIdCajero,                                           
                                           String strUsuario, String strTipoComp, String strNroComp) {
        Long tmpCodigo = null;
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);
        
        mapParametros.put("cTip_Msj_in", strTipMsjRecau);
        mapParametros.put("cEst_Trscc_in", strEstTrsscRecau);
        mapParametros.put("cTipo_Trssc_in", strTipoTrssc);
        
        mapParametros.put("cTipo_Rcd_in", strTipoRcd);
        mapParametros.put("cNro_Trjt_in", strNroTarjeta);
        mapParametros.put("cMonto_in", FarmaUtility.getDecimalNumber(strMonto));
        
        mapParametros.put("cTerminal_in", strTerminal);
        mapParametros.put("cComercio_in", strComercio);
        mapParametros.put("cUbicacion_in", strUbicacion);
        
        mapParametros.put("cNro_Cuotas_in", strNroCuotas);
        mapParametros.put("cNro_Caja_in", strNroCaja);
        mapParametros.put("cId_cajero_in", strIdCajero);        
        
        mapParametros.put("cUsu_Crea_in", strUsuario);        
        //mapParametros.put("cTip_Comp_in", strTipoComp);  
        //mapParametros.put("cNum_Comp_in", strNroComp);  

        try{   
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.registrarTrsscPagoDeudaCMR(mapParametros);
            tmpCodigo = (Long) mapParametros.get("tmpCodigo");
        }
        catch(Exception e){   
            sqlSession.rollback(true);
            log.error("",e);                    
        }finally{   
            sqlSession.close();
        }
        return tmpCodigo;  
    }    
    
    
    public Long registrarTrsscPagoDeudaRipley(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strNroTarjeta, String strMonto,
                                           String strTerminal, String strComercio, String strUbicacion,
                                           String strNroCuotas, String strNroCaja, String strIdCajero,                                           
                                           String strUsuario, String strTipoComp, String strNroComp) {
        Long tmpCodigo = null;
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);
        
        mapParametros.put("cTip_Msj_in", strTipMsjRecau);
        mapParametros.put("cEst_Trscc_in", strEstTrsscRecau);
        mapParametros.put("cTipo_Trssc_in", strTipoTrssc);
        
        mapParametros.put("cTipo_Rcd_in", strTipoRcd);
        mapParametros.put("cNro_Trjt_in", strNroTarjeta);
        mapParametros.put("cMonto_in", FarmaUtility.getDecimalNumber(strMonto));
        
        mapParametros.put("cTerminal_in", strTerminal);
        mapParametros.put("cComercio_in", strComercio);
        mapParametros.put("cUbicacion_in", strUbicacion);
        
        mapParametros.put("cNro_Cuotas_in", strNroCuotas);
        mapParametros.put("cNro_Caja_in", strNroCaja);
        mapParametros.put("cId_cajero_in", strIdCajero);        
        mapParametros.put("cUsu_Crea_in", strUsuario);        

        try{   
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.registrarTrsscPagoDeudaRipley(mapParametros);
            tmpCodigo = (Long) mapParametros.get("tmpCodigo");
        }
        catch(Exception e){   
            sqlSession.rollback(true);
            log.error("",e);                    
        }finally{   
            sqlSession.close();
        }
        return tmpCodigo;  
    }    

    public Long registrarTrsscPagoDeudaClaro(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strMonto,
                                           String strTerminal, String strComercio, String strUbicacion,
                                           String strTelefono, String tipoProdServ, String numRecibo,
                                           String strUsuario) {
        Long tmpCodigo = null;
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Cia_in", strCodCia);
        mapParametros.put("cCod_Local_in", strCodLocal);

        mapParametros.put("cTip_Msj_in", strTipMsjRecau);
        mapParametros.put("cEst_Trscc_in", strEstTrsscRecau);
        mapParametros.put("cTipo_Trssc_in", strTipoTrssc);

        mapParametros.put("cTipo_Rcd_in", strTipoRcd);
        mapParametros.put("cMonto_in", FarmaUtility.getDecimalNumber(strMonto));

        mapParametros.put("cTerminal_in", strTerminal);
        mapParametros.put("cComercio_in", strComercio);
        mapParametros.put("cUbicacion_in", strUbicacion);

        mapParametros.put("cTelefono_in", strTelefono);
        mapParametros.put("cTipoProdServ_in", tipoProdServ);
        mapParametros.put("cNumRecibo_in", numRecibo);

        mapParametros.put("cUsu_Crea_in", strUsuario);

        try{
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.registrarTrsscPagoDeudaClaro(mapParametros);
            tmpCodigo = (Long) mapParametros.get("tmpCodigo");
        }
        catch(Exception e){
            sqlSession.rollback(true);
            log.error("",e);
        }finally{
            sqlSession.close();
        }
        return tmpCodigo;
    }         

    public Long anularPagoTarjetaCMR(String codGrupoCia, String strCodCia, String strCodLocal, String strTipMsjRecau, String strEstTrsscRecau,
                                   String tipoTrssc, String tipoRcd, String strNroTarjeta, String numCuotas, String strMonto, String strNumCaja, 
                                   String strIdCajero, String strMotExtorno, String strCodTrsscSix,
                                   String terminal, String comercio, String ubicacion, String strUsuario) { 
            Long tmpCodigo = null;
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            
            mapParametros.put("cCodGrupoCia_in",codGrupoCia);
            mapParametros.put("cCod_Cia_in",strCodCia);    
            mapParametros.put("cCod_Local_in",strCodLocal);    
            mapParametros.put("cTip_Msj_in",strTipMsjRecau);
            mapParametros.put("cEst_Trscc_in",strEstTrsscRecau); 
            mapParametros.put("cTipo_Trssc_in",tipoTrssc);     
            mapParametros.put("cTipo_Rcd_in",tipoRcd);
            mapParametros.put("cNro_Tarjeta_in",strNroTarjeta);
            mapParametros.put("cNro_Cuotas_in",numCuotas);
            mapParametros.put("cMonto_in",FarmaUtility.getDecimalNumber(strMonto));
            mapParametros.put("cNum_Caja_in",strNumCaja);
            mapParametros.put("cId_Cajero_in",strIdCajero);
            mapParametros.put("cMotvExtorno_in",strMotExtorno);
            mapParametros.put("cCod_Trssc_Six_in",strCodTrsscSix);
            
            mapParametros.put("cTerminal_in",terminal);
            mapParametros.put("cComercio_in",comercio);
            mapParametros.put("cUbicacion_in",ubicacion);
            mapParametros.put("cUsu_Crea_in", strUsuario); 
            
            try{    
                sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
                mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
                mapperTrsscSix.anularPagoTarjetaCMR(mapParametros);
                tmpCodigo = (Long) mapParametros.get("tmpCodigo");
                
            }catch(Exception e){   
                sqlSession.rollback(true);
                log.error("",e);
            }finally{   
                sqlSession.close();            
            }
            return tmpCodigo;
        }     
    
    
    public Long anularPagoTarjetaRipley(String codGrupoCia, 
                                        String strCodCia, 
                                        String strCodLocal, 
                                        String strTipMsjRecau, 
                                        String strEstTrsscRecau, 
                                        String tipoTrssc, 
                                        String tipoRcd, 
                                        String strNroTarjeta, 
                                        String numCuotas, 
                                        String strMonto, 
                                        String strNumCaja, 
                                        String strIdCajero, 
                                        //String strMotExtorno, 
                                        String strCodTrsscSix,
                                        String fechaOrigen,
                                        String codAuditoria,
                                        String terminal, 
                                        String comercio, 
                                        String ubicacion, 
                                        String strUsuario) { 
            Long tmpCodigo = null;
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            
            mapParametros.put("cCodGrupoCia_in",codGrupoCia);
            mapParametros.put("cCod_Cia_in",strCodCia);    
            mapParametros.put("cCod_Local_in",strCodLocal);    
            mapParametros.put("cTip_Msj_in",strTipMsjRecau);
            mapParametros.put("cEst_Trscc_in",strEstTrsscRecau); 
            mapParametros.put("cTipo_Trssc_in",tipoTrssc);     
            mapParametros.put("cTipo_Rcd_in",tipoRcd);
            mapParametros.put("cNro_Tarjeta_in",strNroTarjeta);
            mapParametros.put("cNro_Cuotas_in",numCuotas);
            mapParametros.put("cMonto_in",FarmaUtility.getDecimalNumber(strMonto));
            mapParametros.put("cNum_Caja_in",strNumCaja);
            mapParametros.put("cId_Cajero_in",strIdCajero);
            //mapParametros.put("cMotvExtorno_in","");
            mapParametros.put("cCod_Trssc_Six_in",strCodTrsscSix);
            mapParametros.put("cFecha_Origen_in",fechaOrigen);
            mapParametros.put("cCod_auditoria_in",codAuditoria);
            
            mapParametros.put("cTerminal_in",terminal);
            mapParametros.put("cComercio_in",comercio);
            mapParametros.put("cUbicacion_in",ubicacion);
            mapParametros.put("cUsu_Crea_in", strUsuario); 
            
            try{    
                sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
                mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
                mapperTrsscSix.anularPagoTarjetaRipley(mapParametros);
                tmpCodigo = (Long) mapParametros.get("tmpCodigo");
                
            }catch(Exception e){   
                sqlSession.rollback(true);
                log.error("",e);
            }finally{   
                sqlSession.close();            
            }
            return tmpCodigo;
        }

    /**
    * REALIZAR UNA ANULACION DE VENTA CMR
    * @author GFONSECA
    * @since 05.09.2013
    * @param mapParametros
    */    
    public Long anularPagoTarjetaVentaCMR(String codGrupoCia, 
                                          String strCodCia, 
                                          String strCodLocal, 
                                          String strTipMsjRecau, 
                                          String strEstTrsscRecau,
                                          String tipoTrssc, 
                                          String tipoRcd, 
                                          
                                          String strNroTarjeta, 
                                          String strMonto, 
                                          String terminal, 
                                          String comercio, 
                                          String ubicacion, 
                                          String numCuotas , 
                                          String strIdCajero, 
                                          String strNumDoc, 
                                          String strIdAnular, 
                                          String strUsuario) { 
            Long tmpCodigo = null;
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            
            mapParametros.put("cCodGrupoCia_in",codGrupoCia);
            mapParametros.put("cCod_Cia_in",strCodCia);    
            mapParametros.put("cCod_Local_in",strCodLocal);    
            mapParametros.put("cTip_Msj_in",strTipMsjRecau);
            mapParametros.put("cEst_Trscc_in",strEstTrsscRecau); 
            mapParametros.put("cTipo_Trssc_in",tipoTrssc);     
            mapParametros.put("cTipo_Rcd_in",tipoRcd);
            
            mapParametros.put("cNro_Tarjeta_in",strNroTarjeta);
            mapParametros.put("cMonto_in",UtilityRecaudacion.formatNumberDouble(strMonto));
            mapParametros.put("cTerminal_in",terminal);
            mapParametros.put("cComercio_in",comercio);
            mapParametros.put("cUbicacion_in",ubicacion);            
            mapParametros.put("cNro_Cuotas_in",numCuotas);
            mapParametros.put("cId_Cajero_in",strIdCajero);            
            mapParametros.put("cNum_Doc_in",strNumDoc);
            mapParametros.put("cId_Anular_in",strIdAnular);                    
            mapParametros.put("cUsu_Crea_in", strUsuario); 
            
            try{    
                sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
                mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
                mapperTrsscSix.anularPagoTarjetaVentaCMR(mapParametros);
                tmpCodigo = (Long) mapParametros.get("idTrssc");
                
            }catch(Exception e){   
                sqlSession.rollback(true);
                log.error("",e);
            }finally{   
                sqlSession.close();            
            }
            return tmpCodigo;
        }    


        public Long anularPagoServicioCLARO(String codAuditoriaPago, String codGrupoCia, String strCodCia, String strCodLocal, String strTipMsjRecau, String strEstTrsscRecau,
                                            String tipoTrssc, String tipoRcd, String strMonto, String strTerminal, String strComercio, String strUbicacion,
                                            String strCodSucursal, String strUsuCrea) { 
                Long tmpCodigo = null;
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("cAuditoriaOrig_in",codAuditoriaPago);
                mapParametros.put("cCodGrupoCia_in",codGrupoCia);
                mapParametros.put("cCod_Cia_in",strCodCia);    
                mapParametros.put("cCod_Local_in",strCodLocal);    
                mapParametros.put("cTip_Msj_in",strTipMsjRecau);
                mapParametros.put("cEst_Trscc_in",strEstTrsscRecau); 
                mapParametros.put("cTipo_Trssc_in",tipoTrssc);     
                mapParametros.put("cTipo_Rcd_in",tipoRcd);
                mapParametros.put("cMonto_in",FarmaUtility.getDecimalNumber(strMonto));
                mapParametros.put("cTerminal_in",strTerminal);
                mapParametros.put("cComercio_in",strComercio);
                mapParametros.put("cUbicacion_in",strUbicacion);
                mapParametros.put("cCod_sucursal_in",strCodSucursal);
                mapParametros.put("cUsuCrea_in",strUsuCrea);               
                try{    
                    sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
                    mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
                    mapperTrsscSix.anularPagoServicioCLARO(mapParametros);
                    tmpCodigo = (Long) mapParametros.get("tmpCodigo");
                    
                }catch(Exception e){   
                    sqlSession.rollback(true);
                    log.error("",e);
                }finally{   
                    sqlSession.close();            
                }
                return tmpCodigo;
            }     
               
        public String obtenerEstadoTrssc(Long strCodTrssc, String strModoRecau){
            String tmpEst = "";
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            mapParametros.put("cCod_Trscc_in",strCodTrssc); 
            mapParametros.put("cModo_Trscc_in",strModoRecau);             
            try{    
                sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
                mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
                mapperTrsscSix.obtenerEstadoTrssc(mapParametros);
                tmpEst = (String) mapParametros.get("vEstado");
                
            }catch(Exception e){   
                log.error("",e);
            }finally{   
                sqlSession.close();            
            }
            return tmpEst;
        }    
    
    /**
     * RETORNA DATOS DE UNA RECAUDACION VENTA CMR DESDE ADM, PARA SU ANULACION
     * @author GFonseca
     * @since 04.09.2013
     */
    public ArrayList getDatosAnulacionVentaCMR_SIX(String strCodTrsscSix){
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCod_TrsscSix_in",strCodTrsscSix);   
        try{  
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperRecaudacionTrsscSix.class);
            mapperTrsscSix.getDatosAnulacionVentaCMR_SIX(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista); 
        }
        catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }finally{   
            sqlSession.close();
        }
        return tmpArray;
    }  
    
    
    /**
     * Impresión para anulacion de recaudación
     * @author GFonseca
     * @since 27.10.2013
     */
    public String imprimirComprobanteAnulRecaudacion(String pCodGrupoCia, 
                                                     String strCodCia, 
                                                     String strCodLocal,
                                                     String pNroCaja, 
                                                     String strCodRecau,
                                                     String pTipCopia
                                                     ){
            String strHtml = "";                
            Map<String, Object> mapParametros = new HashMap<String, Object>();            
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("cCod_Cia_in", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("cCod_Caja_in", pNroCaja); 
            mapParametros.put("cCod_Recau_in", strCodRecau);                  
            mapParametros.put("cTip_Copia_in", pTipCopia);              
            try{
                openConnection();
                mapper.getCompAnulRecau(mapParametros);
                strHtml = (String)mapParametros.get("vTemplateHTML");
            }catch(Exception e){   
                log.error("",e);
                strHtml="";
            }finally{   
                sqlSession.close();
            }    
        return strHtml;        
    }
    
    
    
    /**
     * RETORNA UN LISTADO CONSOLIDADO DE LAS RECAUDACIONES PERTENECIENTES A UNA SECUENCIA DE MOVIMIENTO DE CAJA
     * @author LLEIVA
     * @since 25.Sep.2013
     */
    @Override
    public ArrayList listarReporteCierreTurno(String strSecMovCaja)
    {   HashMap<String,Object> mapParametros = new HashMap<String,Object>();
        ArrayList<ArrayList<String>> lstListado = null;
        
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in",FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cSecMovCaja_in",strSecMovCaja);

        try
        {   openConnection();
            mapper.listarReporteCierreTurno(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        }
        catch(Exception e)
        {   log.error("",e);
        }
        finally
        {   sqlSession.close();
        }
        return lstListado;
    }
    
    /* ----------- FIN TRANSACCIONES CON EL SIX -----------*/  

    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }
    
    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperRecaudacion.class);
    }
    
    /**
     * Obtiene el monto total de recaudacion por cierre de dia
     * @author ERIOS
     * @since 30.09.2013
     * @param pCodGrupoCia
     * @param strCodCia
     * @param strCodLocal
     * @param strFechaCierreDia
     * @return
     * @throws Exception
     */
    public String getMontoTotalRecaudacionByCierreDia(String pCodGrupoCia, String strCodCia, String strCodLocal, String strFechaCierreDia) throws Exception{
        String montoTot = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cFecCierreDia_in", strFechaCierreDia);        
        
        mapper.getMontoTotalRecaudacionByCierreDia(mapParametros);
        montoTot = (String)mapParametros.get("vMontoTot");
        
        return montoTot;        
    }

    public void guardarLogConciliacion(String codProceso,
                                        String descProceso,
                                        String estConciliacion)
    {   openConnection();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in",FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in",FarmaVariables.vCodLocal);
        mapParametros.put("cCodProceso_in",codProceso);
        mapParametros.put("cDescConcepto_in",descProceso);
        mapParametros.put("cEstConciliacion_in",estConciliacion);
        mapParametros.put("cUsuCreador_in",FarmaVariables.vIdUsu);
        
        mapper.guardarLogConciliacion(FarmaVariables.vCodGrupoCia, 
                                      FarmaVariables.vCodCia, 
                                      FarmaVariables.vCodLocal, 
                                      codProceso, 
                                      descProceso, 
                                      estConciliacion, 
                                      FarmaVariables.vIdUsu);
        
    }
    
    public void guardarLogConciliacion2(String dniVendedor,
                                        String fechaVenta,
                                        double monto,
                                        double numCuotas,
                                        String codAutoriz,
                                        String track2,
                                        String codAutorizPre,
                                        double valorPorCuota,
                                        int caja,
                                        String tipoTrans,
                                        String codServ,
                                        String numObjPago,
                                        String nomCliente,
                                        long codVoucher,
                                        long numComprobAnulado,
                                        String fechaComprobAnulado,
                                        String codAutorizOrig,
                                        double tipoCambio,
                                        long numTrace,
                                        int codAlianza,
                                        int codMoneda,
                                        String monEstPago,
                                        String descProceso,
                                        String estConciliacion,
                                        String codLocalMigra) throws Exception
    {   
        
            mapper.guardarLogConciliacion2(FarmaVariables.vCodGrupoCia, 
                                           FarmaVariables.vCodCia, 
                                           FarmaVariables.vCodLocal, 
                                           dniVendedor, 
                                           fechaVenta, 
                                           monto, 
                                           numCuotas, 
                                           codAutoriz, 
                                           track2, 
                                           codAutorizPre, 
                                           valorPorCuota, 
                                           caja, 
                                           tipoTrans, 
                                           codServ, 
                                           numObjPago, 
                                           nomCliente, 
                                           codVoucher, 
                                           numComprobAnulado, 
                                           fechaComprobAnulado, 
                                           codAutorizOrig, 
                                           tipoCambio, 
                                           numTrace, 
                                           codAlianza, 
                                           codMoneda, 
                                           monEstPago, 
                                           descProceso, 
                                           estConciliacion, 
                                           FarmaVariables.vIdUsu,
                                           codLocalMigra);
        
    }
    
    public ArrayList<ArrayList<String>> getListaConciliacionNOK()
    {   openConnection();
        
        HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
            
        ArrayList<ArrayList<String>> lstListado = null;
        try
        {   openConnection();
            mapper.getListaConciliacionNOK(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        }
        catch(Exception e)
        {   log.error("",e);
        }
        finally
        {   sqlSession.close();
        }
        return lstListado;
    }
    
    public void actualizaRegConciliacion(String codConciliacion)
    {   try
        {
            openConnection();
            mapper.actualizaRegConciliacion(codConciliacion);
        }
        catch(Exception e)
        {   log.error("",e);
        }
        finally
        {   sqlSession.close();
        }
    }

    public ArrayList obtenerDetallePedidoFomasPago(String strNumPedVta){
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia",FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal",FarmaVariables.vCodLocal);
        mapParametros.put("vNumPedVta",strNumPedVta);

        try{   
            openConnection();
            mapper.obtenerDetallePedidoFomasPago(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        }
        catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }
        finally{   
            sqlSession.close();
        }
        return tmpArray;
    }
    
    public String getCodigoAlianzaConciliacion(String formaPago)
    {   String codAlianza = "";
        
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cFormaPago_in", formaPago);
            
        try
        {   openConnection();
            mapper.getCodigoAlianzaConciliacion(mapParametros);
            codAlianza = (String)mapParametros.get("vCodAlianza");
        }
        catch(Exception e)
        {   log.error("",e);
        }
        finally
        {   sqlSession.close();
        }
        return codAlianza;
    }
    
    public ArrayList getNumTarjCodAutoriz(String numPedido)
    {   
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNumPedido_in", numPedido);
            
        try
        {   openConnection();
            mapper.getNumTarjCodAutoriz(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("resultado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        }
        catch(Exception e)
        {   log.error("",e);
        }
        finally
        {   sqlSession.close();
        }
        return tmpArray;
    }
    
    public String getNumPedidoNegativo(String numPedido)
    {   String numPedNegativo = "";
        
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNumPedido_in", numPedido);
            
        try
        {   openConnection();
            mapper.getNumPedidoNegativo(mapParametros);
            numPedNegativo = (String)mapParametros.get("vNumPedNegativo");
        }
        catch(Exception e)
        {   log.error("",e);
        }
        finally
        {   sqlSession.close();
        }
        return numPedNegativo;
    }
    
    /**
     * Obtiene tipos de cambio
     * @author ERIOS
     * @since 18.11.2013
     */
    public double getTipoCambio(String pFecha, String pTipo){
        double vTipoCambio = 0.0;
        
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cFecCambio_in", pFecha);
        mapParametros.put("cTipo", pTipo);
            
        try
        {   openConnection();
            mapper.getTipoCambio3(mapParametros);
            vTipoCambio = FarmaUtility.getDecimalNumber(mapParametros.get("nValorTipoCambio").toString());
        }
        catch(Exception e)
        {   log.error("",e);
        }
        finally
        {   sqlSession.close();
        }
        
        return vTipoCambio;
    }
    
    
    /**
     * Actualiza el estado de conciliacion en la tabla log
     * @author GFONSECA
     * @since 18.11.2013
     */
    public void actualizarEstLogConciliacion(String pCodGrupoCia, 
                                             String strCodCia, 
                                             String strCodLocal, 
                                             String strCodAutoriz,
                                             String strFechaVenta, 
                                             String strTipTrssc,
                                             String strPvoucher,
                                             String strEstConcili){
            HashMap<String,Object> mapParametros = new HashMap<String,Object>();
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("cCod_Cia_in", strCodCia);
            mapParametros.put("cCod_Local_in", strCodLocal);
            mapParametros.put("cCod_Autoriz_in", strCodAutoriz);
            mapParametros.put("cFecha_Venta_in", strFechaVenta);
            mapParametros.put("cTip_Trssc_in", strTipTrssc);        
            mapParametros.put("cPvoucher_in", strPvoucher);        
            mapParametros.put("cEst_Concili_in", strEstConcili);        
            try{   
                openConnection();
                mapper.actualizarEstLogConciliacion(mapParametros);
                commit();
            }catch(Exception e){   
                log.error("",e);
                rollback();            
            }        
    }
    
    
    
}