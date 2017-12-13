package mifarma.farmaPrueba;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;
import mifarma.farmaCTL.ClienteSIX;
import mifarma.farmaUtil.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Prueba800 {
    
    private static final Logger log = LoggerFactory.getLogger(Prueba800.class);
    
    public static void main(String[] args) {
     
    ClienteSIX clienteSIX = new ClienteSIX("prueba", "prueba", "prueba", 
                                            "172.30.10.78", "2005", "FSISOINT", 
                                            "estandar", "log4j.properties");


    /*******************PRUEBA 800*****Por probar*****************/
    EntradaBean entrada = new EntradaBean();
    entrada.setAuditoria(Constantes.TRACE.toString());
    RespuestaBean respuesta = clienteSIX.test800(entrada);
    if(respuesta == null){
        log.debug("Excepcion");
    }else if (respuesta.getResponseCode() != null ){
        if(respuesta.getResponseCode().equals("00")){
            log.debug("Sucessfull");
            log.debug("trama in: " + respuesta.getTramaIn());
            log.debug("trama out: " + respuesta.getTramaOut());
        }else{
            log.debug("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta.getResponseCode() );
        }
    }else {
        log.debug("No se pudo recuperar la respuesta del servidor");
    }
    /*******************FIN PRUEBA 800*******************/

    }

}
