package mifarma.transf.daemon.service;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mifarma.transf.daemon.bean.BeanLocal;


import mifarma.transf.daemon.util.BeanConexion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SolicitudCia extends Thread{
    
    private static final Logger log = LoggerFactory.getLogger(SolicitudCia.class);
    
    private FacadeDaemon facade = null;
    private String pCodCia;
    private Properties farmadaemon;
    private BeanConexion matriz;

    public SolicitudCia(String pCodCia,Properties farmadaemon,BeanConexion matriz) throws Exception {
        super();
        this.pCodCia = pCodCia;        
        this.farmadaemon = farmadaemon;
        this.matriz = matriz;
        facade = new FacadeDaemon(matriz);
        setName("Cia:"+pCodCia);
    }

    public void run() {
        log.warn("Inicia Hilo PRINCIPAL "+pCodCia);
        
        try {
            while (!isInterrupted()) {

                log.debug(">>>>>>Inicia Hilo<<<<<<");
                
                List<BeanLocal> lstLocales;
                try {
                    //Obtener todas las solicitudes pendientes
                    lstLocales = facade.getLocales("001", pCodCia);
                    int nCantLocales = lstLocales.size();                
                    if (nCantLocales>0) {
                                                
                        ExecutorService executor = Executors.newFixedThreadPool(nCantLocales);
                        
                        for (BeanLocal beanLocal : lstLocales) {
    
                            SolicitudLocal hiloLocal = new SolicitudLocal(beanLocal,farmadaemon,matriz);
                            
                            executor.execute(hiloLocal);
                            
                        }
                        executor.shutdown();
                        while (!executor.isTerminated()) {
                        }
                    }
                    
                    log.debug("Finished all threads");
                } 
                finally {
                    log.debug("Me voy a dormir");
                    sleep(1000); 
                }

            }
        } catch (Exception e) {
            log.error("Exception", e);
        }
        log.debug("Termino proceso.");
    }         

}
