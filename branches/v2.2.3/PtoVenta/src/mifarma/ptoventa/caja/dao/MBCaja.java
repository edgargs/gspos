package mifarma.ptoventa.caja.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.producto.dao.MapperProducto;
import mifarma.ptoventa.recaudacion.dao.MapperRecaudacionTrsscSix;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MBCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class MBCaja implements DAOCaja {
    
    private static final Logger log = LoggerFactory.getLogger(MBCaja.class);
    
    private SqlSession sqlSession;
    private MapperCaja mapper;
    UtilityPtoVenta utilityPtoVenta =new UtilityPtoVenta();
        
    @Override
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    @Override
    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperCaja.class);
    }
    
    
    /**
     * REGISTRAR UNA RECARGA MOVISTAR PARA SER PROCESADA POR EL SIX
     * @author GFonseca
     * @since 12.08.2013
     * @param mapParametros
     */ 
    public Long registrarTrsscRecarga(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strMonto, String strTerminal, String strComercio, 
                                           String strUbicacion, String strNroTelefono, String strNumPedido,
                                           String strUsuario
                                           )throws Exception {
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
        mapParametros.put("cTelefono_in", strNroTelefono);
        
        mapParametros.put("cNumPedido_in", strNumPedido);
        mapParametros.put("cUsu_Crea_in", strUsuario);
        
        sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperCaja.class);
        mapper.registrarTrsscRecarga(mapParametros);
        tmpCodigo = (Long) mapParametros.get("tmpCodigo");
        
        return tmpCodigo;  
    }
    
    /**
     * OBTENER EL ESTADO DE UNA RECARGA PARA MOSTRAR SUS DATOS
     * @author GFonseca
     * @since 14.08.2013
     */
    public String obtenerEstadoTrssc(String pCodGrupoCia, String strCodCia, String strCodLocal, String strNumPedido){
        String tmpEst = "";
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",pCodGrupoCia); 
        mapParametros.put("cCod_Cia_in",strCodCia);
        mapParametros.put("cCod_Local_in",strCodLocal);
        mapParametros.put("cNum_Pedido_in",strNumPedido);
        try{   
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.obtenerEstTrsscRecarga(mapParametros);
            tmpEst = (String) mapParametros.get("tmpEst");
        }catch(Exception e){   
            sqlSession.rollback(true);
            log.error("",e);                    
        }finally{   
            sqlSession.close();
        }
        return tmpEst;
    } 
    
    /**
     * OBTENER LA DESCRIPCION DE ERRORES DEL SIX
     * @author GFonseca
     * @since 14.08.2013
     */    
    public ArrayList obtenerDescErrorSix(String strCodErrorSix){   
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCod_error_six",strCodErrorSix);

        try{               
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.obtenerDescErrorSix(mapParametros);
            tmpLista = (List<BeanResultado>) mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);            
        }catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }finally{   
            sqlSession.close();
        }
        return tmpArray;
    }
    
    public Long registrarTrsscVentaCMR(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                           String strTipMsjRecau, String strEstTrsscRecau, String strTipoTrssc,
                                           String strTipoRcd, String strNroTarjeta, String strMonto,
                                           String strTerminal, String strComercio, String strUbicacion,
                                           String strNroCuotas, String strIdCajero, String strNroDoc,
                                           String strUsuario
                                           ) {
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
        mapParametros.put("cId_cajero_in", strIdCajero);
        mapParametros.put("cNro_Doc", strNroDoc);   
        
        mapParametros.put("cUsu_Crea_in", strUsuario); 
    
        try{   
            sqlSession = MyBatisUtil.getAdmSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.registrarTrsscVentaCMR(mapParametros);
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
    
    /**
     * OBTIENE LAS OPCIONES BLOQUEADAS DEL SISTEMA
     * @author CVILCA
     * @since 18.10.2013
     */
    public ArrayList obtenerOpcionesBloqueadas()throws SQLException{
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in",FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in",FarmaVariables.vCodCia);
        log.info("" + mapParametros);
        try{               
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperCaja.class);
            mapper.obtenerOpcionesBloqueadas(mapParametros);
            tmpLista = (List<BeanResultado>) mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);   
            
        }catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }finally{   
            sqlSession.close();
        }
        return tmpArray;
    }  
}
