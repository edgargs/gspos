package mifarma.factelec.daemon.service;

import java.util.List;

import java.util.Properties;

import java.util.StringTokenizer;

import mifarma.factelec.daemon.bean.BeanLocal;

import mifarma.factelec.daemon.bean.MonVtaCompPagoE;
import mifarma.factelec.daemon.util.BeanConexion;

import mifarma.factelec.daemon.util.DaemonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SolicitudLocal implements Runnable{
    
    private static final Logger log = LoggerFactory.getLogger(SolicitudLocal.class);        
    
    private BeanLocal beanLocal;
    private BeanConexion conexion = new BeanConexion();
    private FacadeDaemon facade;
    private int vCantidadRegistros;
    
    String IpPC=""; 
    String usuarioSIX="";
    String claveSIX="";
    
    String strCodAutorizacion = "";
    String strMontoResp = "";
    String strFechaVencCuota = "";
    String strResponseCode = "";
    String strTramaOut = "";
    
    String strTramaIn = "";
    private Properties farmadaemon;


    public SolicitudLocal(BeanLocal beanLocal,Properties farmadaemon,BeanConexion matriz,int vCantidadRegistros) throws Exception {
        this.beanLocal = beanLocal; 
        this.farmadaemon = farmadaemon;
        this.facade = new FacadeDaemon(matriz);
        this.vCantidadRegistros = vCantidadRegistros;
        
        log.warn("Inicia Constructor HILO LOCAL >>> "+beanLocal.getCodLocal());        
    }


    public void run() {
        log.warn("Inicia Hilo: " + beanLocal.getCodLocal());
        if (!beanLocal.getIpServidorLocal().trim().equalsIgnoreCase("10.11.1.250")) 
        if (beanLocal.getIpServidorLocal().trim().length() > 0) {
            
            cargaConexion();
            //ERIOS 29.08.2014 Verifica conexion desde java
            try {
                String host = conexion.getIPBD();
                if (host == null || "".equals(host)) {
                    log.warn("IP de local, no valido.");
                    return;
                } else if (!DaemonUtil.ping(host)) {
                    log.warn("No hay conexion con el local");
                    //ERIOS 29.01.2015 Marca los registros del local, para intentar posteriormente.
                    facade.actualizaDocumentoEPendientes(beanLocal);
                    return;
                }
            } catch (Exception e) {
                log.error("",e);
                return;
            }
            
            List<MonVtaCompPagoE> lstNotas = facade.getDocsPendientes(beanLocal,vCantidadRegistros);
            //for (MonVtaCompPagoE beanSol : lstNotas) {
                try {
                
                    facade.enviaDocumentoE(lstNotas, conexion);
                
                } catch (Exception e) {
                    log.error("<<ERROR>>"+ beanLocal.getCodLocal()+"-"+ beanLocal.getIpServidorLocal(), e);
                }

            //} //fin for

    
        }
        
    }

    private void cargaConexion() {
        conexion.setIPBD(beanLocal.getIpServidorLocal());
        conexion.setUsuarioBD(farmadaemon.getProperty("UsuarioLocal"));
        conexion.setClaveBD(farmadaemon.getProperty("ClaveLocal"));
    }
        
}
