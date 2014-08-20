package mifarma.transf.daemon.service;

import java.util.List;

import java.util.Properties;

import mifarma.transf.daemon.bean.BeanLocal;
import mifarma.transf.daemon.bean.BeanTransferencia;
import mifarma.transf.daemon.bean.LgtNotaEsCab;

import mifarma.transf.daemon.util.BeanConexion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SolicitudLocal implements Runnable{
    
    private static final Logger log = LoggerFactory.getLogger(SolicitudLocal.class);        
    
    private BeanLocal beanLocal;
    private BeanConexion conexion = new BeanConexion();
    private FacadeDaemon facade;
    
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


    public SolicitudLocal(BeanLocal beanLocal,Properties farmadaemon,BeanConexion matriz) throws Exception {
        this.beanLocal = beanLocal; 
        this.farmadaemon = farmadaemon;
        facade = new FacadeDaemon(matriz);
        
        log.warn("Inicia Constructor HILO LOCAL >>> "+beanLocal.getCodLocal());        
    }


    public void run() {
        log.warn("Inicia Hilo: "+ beanLocal.getCodLocal());
        cargaConexion();
        List<BeanTransferencia> lstNotas = facade.getNotasPendientes(beanLocal);
        for( BeanTransferencia beanSol : lstNotas ){
            try{
                if(beanSol.getNomSistema().equals("FV")){
                    facade.enviaTransferencia(beanSol,conexion);
                }else{ //BV
                    facade.grabaTransfRAC(beanSol);
                }
            }catch(Exception e){
                log.error("",e);
            }      
                
        }//fin for
        
    }

    private void cargaConexion() {
        conexion.setIPBD(beanLocal.getIpServidorLocal());
        conexion.setUsuarioBD(farmadaemon.getProperty("UsuarioLocal"));
        conexion.setClaveBD(farmadaemon.getProperty("ClaveLocal"));
    }
}
