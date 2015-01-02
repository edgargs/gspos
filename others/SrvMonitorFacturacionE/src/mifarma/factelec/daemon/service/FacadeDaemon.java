package mifarma.factelec.daemon.service;

import java.sql.SQLException;

import java.util.Date;
import java.util.List;

import mifarma.factelec.daemon.bean.BeanLocal;
import mifarma.factelec.daemon.bean.MonVtaCompPagoE;
import mifarma.factelec.daemon.dao.DAODaemon;
import mifarma.factelec.daemon.dao.DAOPtoVenta;
import mifarma.factelec.daemon.dao.FactoryDaemon;
import mifarma.factelec.daemon.dao.FactoryPtoVenta;
import mifarma.factelec.daemon.util.BeanConexion;

import mifarma.factelec.daemon.util.DaemonUtil;

import org.apache.ibatis.exceptions.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeDaemon {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeDaemon.class);
    
    private DAODaemon dao;
        
    public FacadeDaemon(BeanConexion matriz) throws Exception {
        super();
        dao = FactoryDaemon.getDAODaemon(mifarma.factelec.daemon.dao.FactoryDaemon.Tipo.MYBATIS);
        dao.setConexionMatriz(matriz);
    }
    
    public List<MonVtaCompPagoE> getDocumentos(String pCodGrupoCia, String pCodCia, int pCantidad){
        List<MonVtaCompPagoE> lstDocumentos = null;
        try{
            dao.openConnection();
            lstDocumentos = dao.getDocumentosPendientes(pCodGrupoCia, pCodCia, pCantidad);
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
        }
        return lstDocumentos;
    }

    void grabaEnvio(List<MonVtaCompPagoE> lstDocumentos) {
                
        try{
            //1.0 Abre conexion
            dao.openConnection();
            //2.0 Actualiza envio
            for(MonVtaCompPagoE documento:lstDocumentos){
                documento.setEstado("C");
                documento.setUsu_mod("MON_CONSULTA");
                documento.setFec_mod(new Date());
                dao.actualizaDocumentoE(documento);
            }
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
            return;
        }
    }

    String getRucCia(String pCodGrupoCia, String pCodCia) {
        String vRucCia = "";
        try{
            dao.openConnection();
            vRucCia = dao.getRucCia(pCodGrupoCia, pCodCia)    ;
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
        }
        return vRucCia;
    }

    List<BeanLocal> getLocales(String pCodGrupoCia, String pCodCia, int pCantidad) {
        List<BeanLocal> lstLocales = null;
        try{
            dao.openConnection();
            lstLocales = dao.getLocales(pCodGrupoCia, pCodCia, pCantidad);
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
        }
        return lstLocales;
    }

    List<MonVtaCompPagoE> getDocsPendientes(BeanLocal beanLocal, int vCantidadRegistros) {
        List<MonVtaCompPagoE> lstDocumentos = null;
        try{
            dao.openConnection();
            lstDocumentos = dao.getDocsPendientes(beanLocal.getCodGrupoCia(), beanLocal.getCodCia(), beanLocal.getCodLocal(), vCantidadRegistros);
            dao.commit();
        }catch(Exception e){
            dao.rollback();
            log.error("",e);
        }
        return lstDocumentos;
    }

    void enviaDocumentoE(MonVtaCompPagoE monVtaCompPagoE, BeanConexion beanConexion) {
        
        monVtaCompPagoE.setEstado("E");
        monVtaCompPagoE.setUsu_mod("MON_ENVIO");
        monVtaCompPagoE.setFec_mod(new Date());
        
                DAOPtoVenta daoPtoVenta = null;
                try{
                    //2.0 Abre conexion Local
                    daoPtoVenta = FactoryPtoVenta.detDAOPtoVenta(FactoryPtoVenta.Tipo.MYBATIS);
                    daoPtoVenta.setConexion(beanConexion);
                    daoPtoVenta.openConnection();
                    //2.1 Graba Cabecera
                    daoPtoVenta.saveMonVtaCompPagoE(monVtaCompPagoE);                    
                    //2.4 Cierra conexion Local
                    daoPtoVenta.commit();
                }catch(Exception e){
                    //ERIOS 29.08.2014 Verifica duplicidad de envio
                    boolean continua = false;
                    /*if(e instanceof PersistenceException){
                        SQLException sqlExcep = (SQLException)e.getCause();
                        if(sqlExcep.getErrorCode()==1){
                            continua = true;
                        }
                    }*/
                    
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
                    dao.actualizaDocumentoE(monVtaCompPagoE);
                    dao.commit();
                }catch(Exception e){
                    dao.rollback();
                    log.error("",e);
                    return;
                }
        String codLocal = monVtaCompPagoE.getCod_local();
        Integer pTipoDocumento = new Integer(monVtaCompPagoE.getTip_doc_sunat());
        String pDocumento = monVtaCompPagoE.getNum_comp_pago_e();
        log.info(String.format("Documento enviado loc: [%s|%s|%s]",codLocal,pTipoDocumento,pDocumento));
    }
}
