package mifarma.farmaPrueba;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;
import mifarma.farmaCTL.ClienteSIX;

import mifarma.farmaUtil.Constantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Prueba400 {

    private static final Logger log = LoggerFactory.getLogger(Prueba400.class);

    public static void main(String[] args) {

        ClienteSIX clienteSIX = new ClienteSIX("prueba", "prueba", "prueba", 
                                                "172.30.10.78", "2005", "FSISOINT", 
                                                "estandar", "log4j.properties");


        /******Extorno Pago Servicio Claro*****Por probar*****/
        /**Movil: Por probar; LDI por probar; DNI: Por probar; Fijo: por probar**/
        /*EntradaBean entrada5 = new EntradaBean();
        entrada5.setMonto("10.00");
        entrada5.setAuditoria("611");
        entrada5.setTerminal("02548412");
        entrada5.setComercio("EL AGUSTINO-PRUEBA");
        entrada5.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada5.setCodSucursal("08");
        entrada5.setTelefono("989101995");
        entrada5.setTipoProdServ("1"); // Móvil 1; móvil Larga distancia internacional-LDI 2; DNI 5; Telf. Fijo 6
        entrada5.setNumRecibo("0000000000308573");
        entrada5.setFechaOrig("20131025 100312");
        entrada5.setMotivoExtorno("91");
        RespuestaBean respuesta5 = clienteSIX.extornoPagoServiciosClaro(entrada5);
        if(respuesta5 == null){
            System.out.println("Excepcion SIX");
        }else if (respuesta5.getResponseCode() != null ){
            if(respuesta5.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta5.getTramaIn());
                System.out.println("trama out: " + respuesta5.getTramaOut());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta5.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }
        /*****fin Extorno Pago Servicio PostPago Claro ******/


/************************************************NO USADOS*****************************************/

        /****** EXTORNO Recarga CLARO**** Terminado********/
        /*
        EntradaBean entrada2 = new EntradaBean();
        entrada2.setMonto("21.00");
        entrada2.setAuditoria(Constantes.TRACE.toString());
        entrada2.setTerminal("00037589");
        entrada2.setComercio("5960506");
        entrada2.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada2.setTelefono("997159416");
        //Datos para anulacion
        entrada2.setAuditoriaOrig(Constantes.TRACE.toString());
        entrada2.setFechaOrig("20140527 123426");
        entrada2.setMotivoExtorno("22");
        
        RespuestaBean respuesta2 = clienteSIX.extornoRecargaClaro(entrada2);
        if(respuesta2 == null){
            log.debug("Excepcion");
        }else if (respuesta2.getResponseCode() != null ){
            if(respuesta2.getResponseCode().equals("00")){
                log.debug("Sucessfull");
                log.debug("RESPONSE CODE: " + respuesta2.getResponseCode());
                log.debug("trama in: " + respuesta2.getTramaIn());
                log.debug("trama out: " + respuesta2.getTramaOut());
            }else{
                log.debug("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta2.getResponseCode() );
            }
        }else {
            log.debug("No se pudo recuperar la respuesta del servidor");
        }
*/
        /*******fin Anular Recarga Claro*****/


        /****** EXTORNO Recarga Movistar ****Terminado******/
        /*
        EntradaBean entrada1 = new EntradaBean();
        entrada1.setMonto("9.00");
        entrada1.setAuditoria(Constantes.TRACE.toString());
        entrada1.setTerminal("00037810"); //8
        entrada1.setComercio("EL AGUSTINO-PRUEBA");
        entrada1.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada1.setTelefono("990388408");//990388408 
        //Datos para anulacion
        entrada1.setAuditoriaOrig(Constantes.TRACE.toString());
        entrada1.setFechaOrig("20140716 155539");
        entrada1.setMotivoExtorno("17");
        entrada1.setTipoRecaudacion("05");
        
        RespuestaBean respuesta1 = clienteSIX.anularRecargaMovistar(entrada1);
        if(respuesta1 == null){
            log.debug("Error Excepcion");
        }else if (respuesta1.getResponseCode() != null ){
            if(respuesta1.getResponseCode().equals("00")){
                log.debug("Sucessfull");
                log.debug("trama in: " + respuesta1.getTramaIn());
                log.debug("trama out: " + respuesta1.getTramaOut());
                log.debug("código de operacion: " + respuesta1.getCodOperacion());
            }else{
                log.debug("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta1.getResponseCode() );
            }
        }else {
            log.debug("No se pudo recuperar la respuesta del servidor");
        }
        */
        /*******fin Anular Recarga Movistar*****/
        
        //INI ASOSA - 10/11/2014 - RECAR - NEX
        /****** EXTORNO Recarga NEXTEL ****Terminado******/
        EntradaBean entrada1 = new EntradaBean();
        entrada1.setMonto("14.00");
        entrada1.setAuditoria(Constantes.TRACE.toString());
        entrada1.setTerminal("00037810"); //8
        entrada1.setComercio("EL AGUSTINO-PRUEBA");
        entrada1.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada1.setTelefono("981510217");
        //981151182 --> 2G
        //981510217 --> 3G
        //Datos para anulacion
        entrada1.setAuditoriaOrig(Constantes.TRACE.toString());
        entrada1.setFechaOrig("20141201 101835");
        entrada1.setMotivoExtorno("17");
        entrada1.setTipoRecaudacion("11");
        entrada1.setNroOperacionPpv("8496211");
        entrada1.setCodComerXCia("6001");
        entrada1.setAuditoriaOrig("808946");        
        entrada1.setNomComerXCia("Boticas Mifarma");
        
        //String trama90 = "".replaceAll(nuevoTrace, origenTrace);
        
        RespuestaBean respuesta1 = clienteSIX.extornoRecargaNextel(entrada1);
        if(respuesta1 == null){
            log.debug("Error Excepcion");
        }else if (respuesta1.getResponseCode() != null ){
            if(respuesta1.getResponseCode().equals("00")){
                log.debug("Sucessfull");
                log.debug("trama in: " + respuesta1.getTramaIn());
                log.debug("trama out: " + respuesta1.getTramaOut());
                log.debug("código de operacion: " + respuesta1.getCodOperacion());
            }else{
                log.debug("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta1.getResponseCode() );
            }
        }else {
            log.debug("No se pudo recuperar la respuesta del servidor");
        }
        /*******fin Anular Recarga NEXTEL*****/
        //FIN ASOSA - 10/11/2014 - RECAR - NEX

    }
}
