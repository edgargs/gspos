package mifarma.transf.daemon.service;

import java.util.List;

import java.util.Properties;

import java.util.StringTokenizer;

import mifarma.transf.daemon.bean.BeanLocal;
import mifarma.transf.daemon.bean.BeanTransferencia;
import mifarma.transf.daemon.bean.LgtNotaEsCab;

import mifarma.transf.daemon.util.BeanConexion;

import mifarma.transf.daemon.util.DaemonUtil;

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
        log.warn("Inicia Hilo: " + beanLocal.getCodLocal());
        if (!beanLocal.getIpServidorLocal().trim().equalsIgnoreCase("10.11.1.250")) 
        if (beanLocal.getIpServidorLocal().trim().length() > 0) {
            if (esUnaDireccionIPValida(beanLocal.getIpServidorLocal().trim())) {
                String host = beanLocal.getIpServidorLocal().trim();
                    if (!DaemonUtil.ping(host)) {
                        log.warn("No hay conexion con el local: "+host);
                        return;
                    }
                    
                    cargaConexion();
                    List<BeanTransferencia> lstNotas = facade.getNotasPendientes(beanLocal);
                    for (BeanTransferencia beanSol : lstNotas) {
                        try {
                            if (beanSol.getNomSistema().equals("FV")) {
                                facade.enviaTransferencia(beanSol, conexion);
                            } else { //BV
                                facade.grabaTransfRAC(beanSol);
                            }
                        } catch (Exception e) {
                            log.error("<<ERROR>>"+ beanLocal.getCodLocal()+"-"+ beanLocal.getIpServidorLocal());
                            log.error("", e);
                        }

                    } //fin for
                
            }
        }
        
    }

    private void cargaConexion() {
        conexion.setIPBD(beanLocal.getIpServidorLocal());
        conexion.setUsuarioBD(farmadaemon.getProperty("UsuarioLocal"));
        conexion.setClaveBD(farmadaemon.getProperty("ClaveLocal"));
    }
    

    public boolean validarPingIP(String ip) {
        boolean val = true;
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("ping -n 1 " + ip);
            int status = p.waitFor();
            /*log.debug("***********VALIDANDO IP*************");
            log.debug(ip + " is " + "/ " + status + "-->" + 
                               (status == 0 ? "alive" : "dead"));*/
            if (status == 1)
                val = false;
            if(val)
                log.info("IP."+ip+" SI VALIDO PING ");
            else
                log.info("IP."+ip+" NO VALIDO PING ");
        } catch (Exception l) {
            log.error("Error al verificar conexcion de IP.");
            //log.error("",l);
            val = false;
        }
        return val;
    }
    public boolean esUnaDireccionIPValida(String dir) {
        StringTokenizer st = new StringTokenizer(dir, ".");
        if (st.countTokens() != 4) {
            return false;
        }
        while (st.hasMoreTokens()) {
            String nro = st.nextToken();
            if ((nro.length() > 3) || (nro.length() < 1)) {
                return false;
            }
            int nroInt = 0;
            try {
                nroInt = Integer.parseInt(nro);
            } catch (NumberFormatException s) {
                return false;
            }
            if ((nroInt < 0) || (nroInt > 255)) {
                return false;
            }
        }
        return true;
    }    
        
}
