package mifarma.transf.daemon.service;

import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import mifarma.transf.daemon.bean.BeanLocal;
import mifarma.transf.daemon.bean.BeanTransferencia;
import mifarma.transf.daemon.bean.LgtGuiaRem;
import mifarma.transf.daemon.bean.LgtNotaEsCab;
import mifarma.transf.daemon.bean.LgtNotaEsDet;
import mifarma.transf.daemon.dao.DAODaemon;
import mifarma.transf.daemon.dao.DAOPtoVenta;
import mifarma.transf.daemon.dao.FactoryDaemon;


import mifarma.transf.daemon.dao.FactoryPtoVenta;

import mifarma.transf.daemon.util.BeanConexion;

import mifarma.transf.daemon.util.DaemonUtil;

import org.apache.ibatis.exceptions.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeDaemon {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeDaemon.class);
    
    private DAODaemon dao;
        
    public FacadeDaemon(BeanConexion matriz) throws Exception {
        super();
        dao = FactoryDaemon.getDAODaemon(mifarma.transf.daemon.dao.FactoryDaemon.Tipo.MYBATIS);
        dao.setConexionMatriz(matriz);
    }
    
    public List<BeanLocal> getLocales(String pCodGrupoCia, String pCodCia){
        List<BeanLocal> lstLocales = null;
        try{
            dao.openConnection();
            lstLocales = dao.getLocalesEnvio(pCodGrupoCia, pCodCia)    ;
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
        }
        return lstLocales;
    }
    
    public List<BeanTransferencia> getNotasPendientes(BeanLocal beanLocal){
        List<BeanTransferencia> lstNotas = null;
        try{
            dao.openConnection();
            lstNotas = dao.getNotasPendientes(beanLocal.getCodGrupoCia(), beanLocal.getCodCia(), beanLocal.getCodLocal())    ;
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
        }
        return lstNotas;
    }

    void enviaTransferencia(BeanTransferencia pLgtNotaEsCab, BeanConexion beanConexion) {
            
        /*String indLinea = "N";    
        try{
            //0.0 Abre conexion Matriz
            dao.openConnection();
            //0.1 Valida conexion con local
            indLinea = dao.validaConexionLocal(beanConexion);
            //0.2 Cierra conexion Matriz
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
            return;
        }finally{
            if(indLinea.equals("N")){
                log.warn("No hay conexion con el local");
                return;
            }
        }*/
        //ERIOS 29.08.2014 Verifica conexion desde java
        try {
            String host = beanConexion.getIPBD();
            if (host == null || "".equals(host)) {
                log.warn("IP de local, no valido.");
                return;
            } else if (!DaemonUtil.ping(host)) {
                log.warn("No hay conexion con el local: "+host);
                return;
            }
        } catch (Exception e) {
            log.error("",e);
            return;
        } 

        LgtNotaEsCab lgtNotaEsCab = null;
        List<LgtNotaEsDet> lstLgtNotaEsDet = null;
        List<LgtGuiaRem> lstLgtGuiaRem = null;
        log.info("Envia transferencias:"+pLgtNotaEsCab);
        try{
            //1.0 Abre conexion Matriz
            dao.openConnection();
            //1.1 Lee Cabecera
            lgtNotaEsCab = dao.getNotaEsCab(pLgtNotaEsCab);
            //1.2 Lee Detalle
            lstLgtNotaEsDet = dao.getNotaEsDet(pLgtNotaEsCab);
            //1.3 Lee Guias
            lstLgtGuiaRem = dao.getGuiaRem(pLgtNotaEsCab);
            //1.4 Cierra conexion Matriz
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
            return;
        }
        
        DAOPtoVenta daoPtoVenta = null;
        try{
            //2.0 Abre conexion Local
            daoPtoVenta = FactoryPtoVenta.detDAOPtoVenta(FactoryPtoVenta.Tipo.MYBATIS);
            daoPtoVenta.setConexion(beanConexion);
            daoPtoVenta.openConnection();
            //2.1 Graba Cabecera
            lgtNotaEsCab.setEst_nota_es_cab("L");
            daoPtoVenta.saveLgtNotaEsCab(lgtNotaEsCab);
            //2.2 Graba Detalle
            daoPtoVenta.saveLgtNotaEsDet(lstLgtNotaEsDet);
            //2.3 Graba Guias
            daoPtoVenta.saveLgtGuiaRem(lstLgtGuiaRem);
            //2.4 Cierra conexion Local
            daoPtoVenta.commit();
        }catch(Exception e){
            //ERIOS 29.08.2014 Verifica duplicidad de envio
            boolean continua = false;
            if(e instanceof PersistenceException){
                SQLException sqlExcep = (SQLException)e.getCause();
                if(sqlExcep.getErrorCode()==1){
                    continua = true;
                }
            }
            
            try{
                daoPtoVenta.rollback();
            }catch(NullPointerException x){
                ;
            } catch (Exception c) {
                log.error("", c);
            }
            
            if(!continua){
                log.error("",e);
                return;
            }
        }
        
        try{
            dao.openConnection();
            //3.1 Actualiza envio
            dao.actualizaEnvioDestino(pLgtNotaEsCab);
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
            return;
        }
    }

    void grabaTransfRAC(BeanTransferencia pLgtNotaEsCab) {
                log.info("Graba Transferencia:"+pLgtNotaEsCab);
        try{
            //1.0 Abre conexion
            dao.openConnection();
            //2.0 Graba transferencia BV
            dao.grabarTransfBV(pLgtNotaEsCab);
            //3.0 Actualiza envio
            dao.actualizaEnvioDestino(pLgtNotaEsCab);
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
            return;
        }
    }
}
