package mifarma.factelec.daemon.service;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mifarma.factelec.daemon.bean.BeanLocal;
import mifarma.factelec.daemon.bean.MonVtaCompPagoE;
import mifarma.factelec.daemon.util.BeanConexion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SolicitudCia extends Thread{
    
    private static final Logger log = LoggerFactory.getLogger(SolicitudCia.class);
    
    private FacadeDaemon facade = null;
    private String pCodGrupoCia;
    private String pCodCia;
    private Properties farmadaemon;
    private BeanConexion matriz;

    public SolicitudCia(String pCodGrupoCia,String pCodCia,Properties farmadaemon,BeanConexion matriz) throws Exception {
        super();
        this.pCodGrupoCia = pCodGrupoCia;        
        this.pCodCia = pCodCia;        
        this.farmadaemon = farmadaemon;
        this.matriz = matriz;
        facade = new FacadeDaemon(matriz);
        setName("Cia:"+pCodCia);
    }

    public void run() {
        log.warn("Inicia Hilo PRINCIPAL "+pCodCia);
        String vRucCia = facade.getRucCia(pCodGrupoCia, pCodCia);
        int vCantidadRegistros = Integer.parseInt(farmadaemon.getProperty("CantidadRegistros"));
            
        try {
            while (!isInterrupted()) {

                log.debug(">>>>>>Inicia Hilo<<<<<<");
                
                List<MonVtaCompPagoE> lstDocumentos;
                List<BeanLocal> lstLocales;
                try {
                    //Obtener documentos pendientes
                    lstDocumentos = facade.getDocumentos(pCodGrupoCia, pCodCia, vCantidadRegistros);
                    int nCantDocumentos = lstDocumentos.size();                
                    if (nCantDocumentos>0) {
                                                
                        ExecutorService executor = Executors.newFixedThreadPool(1);

                        MonitorFacturacion hiloLocal = new MonitorFacturacion(lstDocumentos,facade,farmadaemon,vRucCia);
                        
                        executor.execute(hiloLocal);
                        
                        executor.shutdown();
                        while (!executor.isTerminated()) {
                        }
                    }
                    
                    //Envia informacion a locales
                    lstLocales = facade.getLocales(pCodGrupoCia, pCodCia, vCantidadRegistros);
                    int nCantLocales = lstLocales.size();                
                    if (nCantLocales>0) {
                                                
                        ExecutorService executor = Executors.newFixedThreadPool(nCantLocales);
                        
                        for (BeanLocal beanLocal : lstLocales) {
                    
                            SolicitudLocal hiloLocal = new SolicitudLocal(beanLocal,farmadaemon,matriz,vCantidadRegistros);
                            
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
