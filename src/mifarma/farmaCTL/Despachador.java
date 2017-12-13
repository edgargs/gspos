package mifarma.farmaCTL;


import com.novatronic.components.Connection;
import com.novatronic.components.sixclient.tcp.ClienteSIXConnectionFactory;
import com.novatronic.components.sixclient.tcp.ClienteSIXExeBean;

import com.novatronic.components.sixclient.tcp.exception.ClienteSIXConnectionException;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;

import java.util.Properties;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;

import mifarma.farmaParser.FarmaParserTramas;

import mifarma.farmaUtil.Constantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Despachador {

    private Properties CONF;
    private static final Logger log = LoggerFactory.getLogger(Despachador.class);
    
    public Despachador() {
        
    }

    public Despachador(Properties CONF) {
        this.CONF = CONF; 
    }

    /**cargarRespuesta
     * Realiza el envío de una trama al switch
     * 
     */
    public RespuestaBean enviar(EntradaBean entrada) throws Exception{
        RespuestaBean respuesta = new RespuestaBean();

        FarmaParserTramas farmaParserTramas = new FarmaParserTramas();
        IsoMessage iso = new IsoMessage();
        MessageFactory mfact;
        mfact = ConfigParser.createFromClasspathConfig(Constantes.FILE_CONFIG);
        iso = farmaParserTramas.generarTrama(entrada);
        
        imprimirVariablesParseadas(iso);
        String tramaIn = new String(iso.writeData());
        respuesta.setTramaIn(tramaIn);
        
        log.debug("TAMANIO TRAMA IN COMPLETA JUSTO ANTES DE ENVIAR: " + tramaIn );
            //ERIOS 28.01.2014 Control de respuesta
            String tramaOut = enviarTrama(tramaIn);
            
            respuesta.setTramaOut(tramaOut);
            
            log.debug("TAMANIO TRAMA OUT: " + tramaOut.getBytes().length );
            log.debug("TRAMA OUT: '"+tramaOut+"'");
            byte[] byteTramaOut = tramaOut.getBytes();
            log.debug("ANTES DEL ERROR");
            IsoMessage im = mfact.parseMessage(byteTramaOut ,0);
            log.debug("DESPUES DEL ERROR");
            imprimirVariablesParseadas(im);
            respuesta.setIm(im);
            respuesta.setEntrada(entrada);
            respuesta.cargarRespuesta();
        
        return respuesta;
    }

    private String enviarTrama(String tramaIn) throws Exception{
        //ERIOS 29.01.2014 Implementacion para liberar y destroy
        String tramaOut = Constantes.EMPTY;
        Connection cn = null;
        ClienteSIXConnectionFactory factory = null;
        
            //ClienteSIXConnectionFactory factory = FactorySixCn.getInstance(CONF);
            factory = ClienteSIXConnectionFactory.createConnectionFactory(CONF,null);
            Thread.sleep(1000);
            
            ClienteSIXExeBean req = null;
            ClienteSIXExeBean res = null;
            String id = Constantes.ID_CONEXION;          
            log.warn("Parametros Conexion SIX-2:"+CONF);
            cn = factory.getConnection(id);
            req = new ClienteSIXExeBean();
            req.setId(id);
            req.setTrama(tramaIn);
            log.debug("TAMANIO TRAMA IN: "+ tramaIn.getBytes().length);
            log.debug("TRAMA IN: '"+req.getTrama()+"'");
            res = (ClienteSIXExeBean) cn.execute(req);
            tramaOut = res.getTrama();
            
            //cn.liberar();
        
        /*try {
                if (cn != null)
                    cn.liberar();
        } catch (Exception e) {
            log.error("Error Controlado al liberar conexion", e);
        }*/
        
        try {
            if (factory != null) {
                factory.destroy();
            }
        } catch (Exception e) {
            log.error("Error Controlado al destruir factory", e);
        }

        return tramaOut;
    }

    public static void imprimirVariablesParseadas(IsoMessage m)throws Exception {
        /*log.debug("TYPE: " + Integer.toHexString(m.getType()));*/
        for (int i = 0; i <= 128; i++){
            if (m.hasField(i)){
                log.debug("F " + i + " (" + m.getField(i).getType().toString() + "): " + m.getObjectValue(i) + " -> '" + m.getField(i).toString() + "'");
            }
        }
    }

}
