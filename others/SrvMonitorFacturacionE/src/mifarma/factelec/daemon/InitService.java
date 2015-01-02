package mifarma.factelec.daemon;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;

import java.util.Properties;

import mifarma.factelec.daemon.service.SolicitudCia;
import mifarma.factelec.daemon.util.BeanConexion;
import mifarma.factelec.daemon.util.FarmaMail;
import mifarma.factelec.daemon.util.MyBatisUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InitService extends Thread {

    private static final Logger log = LoggerFactory.getLogger(InitService.class);
    
    private static Properties farmadaemon;
    private static Properties servermail;
    SolicitudCia sol;
    private static String prop1;
    private static String prop2;
    private String pCodCia;
    private BeanConexion matriz = new BeanConexion();
    private String indActivo = "N";

    public InitService(String pCodCia) {
        this.pCodCia = pCodCia;
        try {
            iniciaConfiguracion();
        } catch (FileNotFoundException e) {
            log.error("",e);
        } catch (IOException e) {
            log.error("",e);
        }        
        setDaemon(true);
        //run();
    }

    public void run() {
        int contReintentos = 0;
        for (;!isInterrupted();
                    log.debug((new StringBuilder()).append("isInterrupted: ").append(isInterrupted()).toString())) {
            log.debug("Verifica estado hilos.");
            if (sol == null) {
                
                try {
                    sol = new SolicitudCia("001",pCodCia,farmadaemon,matriz);
                    sol.start();
                } catch (Exception e) {
                    sol = null;
                    log.error("Exception", e);
                }
            }            
            if (sol != null ) {
                log.debug((new StringBuilder()).append("Hilo Sol: ").append(sol.isAlive()).toString());
                if (!sol.isAlive()) {
                    sol = null;
                    contReintentos = 0;
                }
            } else if (++contReintentos >= 3 && contReintentos < 5) {
                FarmaMail.enviaMail("InitService: Se ha intentado reiniciar el servicio de recargas sin \351xito. \241Verifique!",servermail);
            }
            try {
                sleep(10000L); //10 segundos
            } catch (InterruptedException e) {
                log.error("InterruptedException", e);
            }
        }

        log.warn("Termino Proceso");
    }

    public void iniciaConfiguracion() throws FileNotFoundException, IOException {
        log.warn("Inicia Configuracion: "+pCodCia);
        InputStream fis = null;
        InputStream fis2 = null;
        File archivo = null;
        
        log.debug("Lee configuracion farmasixdaemon");
        log.debug(prop1);
        fis = this.getClass().getResourceAsStream(prop1);     
        if (fis == null) {
            archivo = new File(prop1);
            fis = new FileInputStream(archivo);
        }
        farmadaemon = new Properties();
        farmadaemon.load(fis);
        log.debug(farmadaemon.toString());
        
        log.debug("Lee configuracion servermail");
        log.debug(prop2);
        fis2 = this.getClass().getResourceAsStream(prop2);  
        if (fis2 == null) {
            archivo = new File(prop2);
            fis2 = new FileInputStream(archivo);
        }
        servermail = new Properties();
        servermail.load(fis2);
        log.debug(servermail.toString());
        
        log.debug("Lee parametros MyBatis");
        iniciaConfiguracion(farmadaemon);
        
    }

    public static void main(String args[]) {
        
        if (args.length > 0) {
            prop1 = args[0];
            prop2 = args[1];
        } else {
            prop1 = "/farmadaemon.properties";
            prop2 = "/servermail.properties";
        }
        carga();
    }
    
    public static void carga(){
        log.warn("INICIA DEMONIO TRANSFERENCIAS");
        try {            
            InitService cia001 = null;
            InitService cia002 = null;
            InitService cia003 = null;
            
            cia001 = new InitService("001");
            if(cia001.getActivo()){
                cia001.start();
            }else{
                cia001 = null;
            }
            cia002 = new InitService("002");
            if(cia002.getActivo()){
                cia002.start();
            }else{
                cia002 = null;
            }
            cia003 = new InitService("003");
            if(cia003.getActivo()){
                cia003.start();
            }else{
                cia003 = null;
            }
            
            while ((cia001 == null || cia001.isAlive()) && 
                   (cia002 == null || cia002.isAlive()) && 
                   (cia003 == null || cia003.isAlive())) {
                Thread.sleep(10000L);
            }
                
        } catch (Exception e) {
            log.error("Exception", e);
        }
        log.warn("Final");
    }

    private void iniciaConfiguracion(Properties farmasixdaemon) {
        
        String dbCIA = farmasixdaemon.getProperty("DBCIA"+pCodCia);
        matriz.setDBCIA(dbCIA);
        
        matriz.setIPBD(farmasixdaemon.getProperty("IPBD"+dbCIA));
        matriz.setPuertoBD(farmasixdaemon.getProperty("PUERTO"+dbCIA));
        matriz.setSID(farmasixdaemon.getProperty("SID"+dbCIA));
        matriz.setUsuarioBD(farmasixdaemon.getProperty("UsuarioBD"+dbCIA));
        matriz.setClaveBD(farmasixdaemon.getProperty("ClaveBD"+dbCIA));     
        
        indActivo = farmasixdaemon.getProperty("CIA"+pCodCia);
    }

    private boolean getActivo() {
        return indActivo.equals("N")?false:true;
    }
}
