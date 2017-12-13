package mifarma.farmaCTL;

import java.rmi.RemoteException;

import java.util.ArrayList;

import jorsa.botica.remoto.RemotoRecarga;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ERIOS
 * @since 21.04.2016
 */
public class ClienteFullCarga implements StrategyRecargas{
    
    private static final Logger log = LoggerFactory.getLogger(ClienteFullCarga.class);
    
    //Canal
    String claveCanal = "FULLCARGA";
    //Producto                
    String tipoProductoBitel = "VIET0001";
    String tipoProductoEntel = "NEXTEL00";
    
    //Datos por local
    /*CODIGO POS    CLAVE 1 CLAVE 2 HEX*/
    String numeroPOS = "";
    String clavePOS = "";
    String key3Des = "";
    String ipServidor = "";
    int portServidor;
    
    public ClienteFullCarga(String numeroPOS, String clavePOS, String key3Des, String[] servidor) {
        super();
        this.numeroPOS = numeroPOS; 
        this.clavePOS = clavePOS;
        this.key3Des = key3Des;
        this.ipServidor = servidor[0];
        this.portServidor = Integer.parseInt(servidor[1]);
    }
    
    public ClienteFullCarga(String numeroPOS, String clavePOS, String key3Des, String servidor, String puerto) {
        super();
        this.numeroPOS = numeroPOS; 
        this.clavePOS = clavePOS;
        this.key3Des = key3Des;
        this.ipServidor = servidor;
        this.portServidor = Integer.parseInt(puerto);
    }
    
    public RespuestaBean recargaBitel(EntradaBean entrada) throws Exception {
        return recarga(entrada,tipoProductoBitel);
    }
    
    /**
     * @author ERIOS
     * @since 25.05.2016
     * @param entrada
     * @return
     * @throws Exception
     */
    public RespuestaBean recargaEntel(EntradaBean entrada) throws Exception {
        return recarga(entrada,tipoProductoEntel);
    }
        
    private RespuestaBean recarga(EntradaBean entrada, String pTipoProducto) throws Exception {
        RespuestaBean respuesta = new RespuestaBean();
        
        RemotoRecarga objRemoto = null;

        try {
            objRemoto = new RemotoRecarga();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
        java.util.ArrayList res = new ArrayList();
        
        String nroTlfRecarga = entrada.getTelefono();
        String monto = entrada.getMonto();
        String trace = ":"+entrada.getCodLocal()+entrada.getNumPedVenta();
                                            
            res = objRemoto.realizarRecarga(claveCanal, 
                                            numeroPOS,
                                            clavePOS,
                                            pTipoProducto, 
                                            monto,
                                            nroTlfRecarga,
                                            key3Des,
                                            trace.split(":"),
                                            ipServidor,
                                            portServidor);

        //PARA PRUEBAS
        //res.add(0,"VentaOK");res.add(1,"789456");res.add(2,null);res.add(3,"00");res.add(4,null);res.add(5,null);
                                                                                        
        String responseCode = res.get(3)!=null?res.get(3).toString():"";
        String tramaIn = res.get(4)!=null?res.get(4).toString():"null";
        String tramaOut = res.get(5)!=null?res.get(5).toString():"null";
        
        respuesta.setResponseCode(responseCode); 
        respuesta.setTramaIn(tramaIn);
        respuesta.setTramaOut(tramaOut);
        respuesta.setFechaTrsscPago("");//beanSolicitud.getFechaOrig()
        
        if( res.get(0)!=null && res.get(0).toString().trim().equals("VentaOK")){
            String atributo = res.get(1)!=null?res.get(1).toString().trim():"null";
            //ERIOS 05.05.2016 Carga codigo respuesta original
            respuesta.setCodAutorizacion(atributo.substring(atributo.length()-6));
            respuesta.setNumeroRecarga(atributo);
        }
        
        return respuesta;
    }
    
    public RespuestaBean anularRecargaBitel(EntradaBean entrada) throws Exception {
        return anularRecarga(entrada,tipoProductoBitel);
    }
    
    /**
     * @author ERIOS
     * @since 25.05.2016
     * @param entrada
     * @return
     * @throws Exception
     */
    @Override
    public RespuestaBean anularRecargaEntel(EntradaBean entrada) throws Exception {
        return anularRecarga(entrada,tipoProductoBitel);
    }
    
    private RespuestaBean anularRecarga(EntradaBean entrada, String pTipoProducto) throws Exception {        
        RespuestaBean respuesta = new RespuestaBean();
        
        RemotoRecarga objRemoto = null;

        try {
            objRemoto = new RemotoRecarga();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
        java.util.ArrayList res = new ArrayList();
        
        String nroTlfRecarga = entrada.getTelefono();
        String monto = entrada.getMonto();
        String trace = ":"+entrada.getCodLocal()+entrada.getNumPedVenta();
        
       res = objRemoto.realizarAnulacion(claveCanal, 
                                       numeroPOS,
                                       clavePOS,
                                       pTipoProducto, 
                                       monto,
                                       nroTlfRecarga,
                                       key3Des,
                                       trace.split(":"),
                                       ipServidor,
                                       portServidor);

       
       String responseCode = res.get(3)!=null?res.get(3).toString().trim():"";
       String tramaIn = res.get(4)!=null?res.get(4).toString():"null";
       String tramaOut = res.get(5)!=null?res.get(5).toString():"null";
       
       respuesta.setResponseCode(responseCode); 
       respuesta.setTramaIn(tramaIn);
       respuesta.setTramaOut(tramaOut);
       respuesta.setFechaTrsscPago("");//beanSolicitud.getFechaOrig()
       
       if( res.get(0)!=null && res.get(0).toString().trim().equals("VentaOK")){
           String atributo = res.get(1)!=null?res.get(1).toString():"null";
           //String cod_respuesta = res.get(2)!=null?res.get(2).toString():"null";               
           respuesta.setCodAutorizacion(atributo);            
       }
       
       return respuesta;
    }
}
