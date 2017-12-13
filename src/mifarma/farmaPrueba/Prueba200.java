package mifarma.farmaPrueba;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;

import mifarma.farmaCTL.ClienteSIX;

import mifarma.farmaUtil.Constantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Prueba200 {

    private static final Logger log = LoggerFactory.getLogger(Prueba200.class);
    
    public static void recargaBitel(ClienteSIX clienteSIX){
        EntradaBean entrada1 = new EntradaBean();
        
        entrada1.setMonto("5.00");
        entrada1.setAuditoria(Constantes.TRACE.toString());
        entrada1.setTerminal("02548412");
        entrada1.setComercio("EL AGUSTINO-PRUEBA");
        entrada1.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada1.setTelefono("999999999");
        
        /*entrada1.setCodTipoProducto("00000011");
        
        entrada1.setCodComerXCia("6001");
        entrada1.setNomComerXCia("Boticas Mifarma");
        entrada1.setNumPedVenta("0000123456");
        entrada1.setCodLocal("304");*/
        
        
        RespuestaBean respuesta1;
        try {
            respuesta1 = clienteSIX.recargaBitel(entrada1);
        } catch (Exception e) {
            respuesta1 = null;
            System.out.println("INI Excepcion");
            System.out.println("TEXTO EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            System.out.println("FIN Excepcion");
        }
        if(respuesta1 == null){
            System.out.println("Exception");
        }else if (respuesta1.getResponseCode() != null ){
            if(respuesta1.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("código de autorización: " + respuesta1.getCodAutorizacion());
                System.out.println("trama in: " + respuesta1.getTramaIn());
                System.out.println("trama out: " + respuesta1.getTramaOut());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta1.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }
    }
    
    public static void main(String[] args) {

    ClienteSIX clienteSIX = new ClienteSIX("prueba", "prueba", "prueba", 
                                          "172.30.10.78", "2005", "FSISOINT", 
                                           "estandar", "log4j.properties");
    
        recargaBitel(clienteSIX);
        
        //INI ASOSA - 10/11/2014 - RECAR - NEX
        /****** Recarga NEXTEL ****Terminado******/ 
        /*
            EntradaBean entrada1 = new EntradaBean();
            entrada1.setMonto("11.00");
            entrada1.setAuditoria(Constantes.TRACE.toString());
            entrada1.setTerminal("02548412");
            entrada1.setComercio("EL AGUSTINO-PRUEBA");
            entrada1.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
            entrada1.setTelefono("981510217");
            //981151182 --> 2G
            //981510217 --> 3G
            entrada1.setCodTipoProducto("00000011");
            entrada1.setTipoRecaudacion("11");
            entrada1.setCodComerXCia("6001");
            entrada1.setNomComerXCia("Boticas Mifarma");
            entrada1.setNumPedVenta("0000123456");
            entrada1.setCodLocal("304");
            
            
            RespuestaBean respuesta1;
            try {
                respuesta1 = clienteSIX.recargaNextel(entrada1);
            } catch (Exception e) {
                respuesta1 = null;
                System.out.println("INI Excepcion");
                System.out.println("TEXTO EXCEPTION: " + e.getMessage());
                e.printStackTrace();
                System.out.println("FIN Excepcion");
            }
            if(respuesta1 == null){
                System.out.println("Exception");
            }else if (respuesta1.getResponseCode() != null ){
                if(respuesta1.getResponseCode().equals("00")){
                    System.out.println("Sucessfull");
                    System.out.println("código de autorización: " + respuesta1.getCodAutorizacion());
                    System.out.println("trama in: " + respuesta1.getTramaIn());
                    System.out.println("trama out: " + respuesta1.getTramaOut());
                }else{
                    System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta1.getResponseCode() );
                }
            }else {
                System.out.println("No se pudo recuperar la respuesta del servidor");
            }
           */
        /*******fin Recarga NEXTEL*****/
            
            
        /****** ANULACION Recarga NEXTEL Terminado********/
        
        /*EntradaBean entrada2 = new EntradaBean();
        entrada2.setMonto("9.00");
        entrada2.setAuditoria(Constantes.TRACE.toString());
        entrada2.setTerminal("02548412");
        entrada2.setComercio("EL AGUSTINO-PRUEBA");
        entrada2.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada2.setTelefono("981510217");
        //981151182 --> 2G
        //981510217 --> 3G
        //Datos para anulacion
        entrada2.setAuditoriaOrig("604");
        entrada2.setFechaOrig("1127110955"); //Formato: MMDDHHMMSS
        entrada2.setNumeroRecargaOrig("981510217");
        entrada2.setCodTipoProducto("00000011"); //ASOSA - 14/07/2014
        entrada2.setTipoRecaudacion("11");    //ASOSA - 15/07/2014
        
        entrada2.setCodComerXCia("6001");
        entrada2.setNomComerXCia("Boticas Mifarma");
        entrada2.setNroOperacionPpv("8496011");
        
        RespuestaBean respuesta2 = clienteSIX.anularRecargaNextel(entrada2);
        if(respuesta2 == null){
            log.debug("Excepcion");
        }else if (respuesta2.getResponseCode() != null ){
            if(respuesta2.getResponseCode().equals("00")){
                log.debug("Sucessfull");
                log.debug("trama in: " + respuesta2.getTramaIn());
                log.debug("trama out: " + respuesta2.getTramaOut());
            }else{
                log.debug("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta2.getResponseCode() );
            }
        }else {
            log.debug("No se pudo recuperar la respuesta del servidor");
        }
        
        /*******fin Anular Recarga NEXTEL*****/
            
            
        //FIN ASOSA - 10/11/2014 - RECAR - NEX



    /****** Recarga Movistar ****Terminado******/
    /*
        EntradaBean entrada1 = new EntradaBean();
        entrada1.setMonto("9.00");
        entrada1.setAuditoria(Constantes.TRACE.toString());
        entrada1.setTerminal("02548412");
        entrada1.setComercio("EL AGUSTINO-PRUEBA");
        entrada1.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada1.setTelefono("990388408");
        entrada1.setCodTipoProducto("00000001");
        entrada1.setTipoRecaudacion("05");
        RespuestaBean respuesta1;
        try {
            respuesta1 = clienteSIX.recargaMovistar(entrada1);
        } catch (Exception e) {
            respuesta1 = null;
        }
        if(respuesta1 == null){
            System.out.println("Excepcion");
        }else if (respuesta1.getResponseCode() != null ){
            if(respuesta1.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("código de autorización: " + respuesta1.getCodAutorizacion());
                System.out.println("trama in: " + respuesta1.getTramaIn());
                System.out.println("trama out: " + respuesta1.getTramaOut());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta1.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }
       */
    /*******fin Recarga Movistar*****/


    /****** Recarga Claro*****Terminado*****/
    /*
        EntradaBean entrada2 = new EntradaBean();
        entrada2.setMonto("10.00");
        entrada2.setAuditoria(Constantes.TRACE.toString());
        entrada2.setTerminal("02548412");
        entrada2.setComercio("5960506");
        entrada2.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada2.setTelefono("997159416");
        entrada2.setCodTipoProducto("00000001");
        entrada2.setTipoRecaudacion("03");
        RespuestaBean respuesta2;
        try {
            respuesta2 = clienteSIX.recargaClaro(entrada2);
        } catch (Exception e) {
            respuesta2 = null;
        }
        if(respuesta2 == null){
            System.out.println("Excepción");
        }else if (respuesta2.getResponseCode() != null ){
            if(respuesta2.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("código de autorización: " + respuesta2.getCodAutorizacion());
                System.out.println("trama in: " + respuesta2.getTramaIn());
                System.out.println("trama out: " + respuesta2.getTramaOut());
                System.out.println("numero recarga: " + respuesta2.getNumeroRecarga());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta2.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }
       */
    /*******fin Recarga Claro*****/
        
    /****** ANULACION Recarga CLARO**** Terminado********/
    /*
    EntradaBean entrada2 = new EntradaBean();
    entrada2.setMonto("10.00");
    entrada2.setAuditoria(Constantes.TRACE.toString());
    entrada2.setTerminal("02548412");
    entrada2.setComercio("EL AGUSTINO-PRUEBA");
    entrada2.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
    entrada2.setTelefono("997159416");
    //Datos para anulacion
    entrada2.setAuditoriaOrig("604"); //campo 11
    entrada2.setFechaOrig("0162930307"); //campo 07
    entrada2.setNumeroRecargaOrig("62930307");
    
    RespuestaBean respuesta2 = clienteSIX.anularRecargaClaro(entrada2);
    if(respuesta2 == null){
        log.debug("Excepcion");
    }else if (respuesta2.getResponseCode() != null ){
        if(respuesta2.getResponseCode().equals("00")){
            log.debug("Sucessfull");
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

    /****** CONSULTA Deuda CLARO******Terminado****/
    /*EntradaBean entrada4 = new EntradaBean();
        entrada4.setAuditoria(Constantes.TRACE.toString());
        entrada4.setTerminal("02548412");
        entrada4.setComercio("EL AGUSTINO-PRUEBA");
        entrada4.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada4.setCodSucursal("08");
        entrada4.setTelefono("950203291");
        entrada4.setTipoProdServ("1");// Móvil 1; móvil Larga distancia internacional-LDI 2; DNI 5; Telf. Fijo 6
        RespuestaBean respuesta4 = clienteSIX.consultaClaroPostPago(entrada4);
        if(respuesta4 == null){
            System.out.println("Excepcion");
        }else if (respuesta4.getResponseCode() != null ){
            if(respuesta4.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta4.getTramaIn());
                System.out.println("trama out: " + respuesta4.getTramaOut());
                //Solo caso consulta servicio:
                System.out.println("Deuda Total: " + respuesta4.getDeudaTotal());
                System.out.println("Num Recibo a pagar: " + respuesta4.getNumRecibo());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta4.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }*/
    /*******fin consulta Deuda Claro*****/

 
    /****** PAGO Servicio Facturación PostPago CLARO **********/
    /**Movil: Por probar; LDI por probar; DNI: Por probar; Fijo: por probar**/
        /*EntradaBean entrada5 = new EntradaBean();
        entrada5.setMonto("10.00");
        entrada5.setAuditoria(Constantes.TRACE.toString());
        entrada5.setTerminal("02548412");
        entrada5.setComercio("EL AGUSTINO-PRUEBA");
        entrada5.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada5.setCodSucursal("08");
        entrada5.setTelefono("989101995");
        entrada5.setTipoProdServ("1");  // Móvil 1; móvil Larga distancia internacional-LDI 2; DNI 5; Telf. Fijo 6
        entrada5.setNumRecibo("070100831736");
        RespuestaBean respuesta5 = clienteSIX.pagarServicioClaroPostPago(entrada5);
        if(respuesta5 == null){
            System.out.println("Excepcion SIX");
        }else if (respuesta5.getResponseCode() != null ){
            if(respuesta5.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta5.getTramaIn());
                System.out.println("trama out: " + respuesta5.getTramaOut());
                //usado en anulacion
                System.out.println("Código de Transacción: " + respuesta5.getNumOperacCobranza());// se usara como codigo de autorizacion
                System.out.println("Número operación acreedor: " + respuesta5.getNumOperacAcreedor());
                System.out.println("Fecha Original: " + respuesta5.getFechaOrigen());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta5.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }*/
    /*****fin Pago Facturación PostPago Claro ******/


    /**********ANULAR PAGO CLARO****************/
    /**Movil: Por probar; LDI por probar; DNI: Por probar; Fijo: por probar**/
        /*EntradaBean entrada9 = new EntradaBean();
        entrada9.setMonto("20.00");
        entrada9.setAuditoria(Constantes.TRACE.toString());
        entrada9.setTerminal("02548412");
        entrada9.setComercio("EL AGUSTINO-PRUEBA");
        entrada9.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada9.setCodSucursal("08");
        entrada9.setTelefono("987807978");
        entrada9.setTipoProdServ("1"); // Móvil 1; móvil Larga distancia internacional-LDI 2; DNI 5; Telf. Fijo 6
        entrada9.setIdAnular("000083165164");// nro operación de cobranza
        entrada9.setAuditoriaOrig("543");
        entrada9.setFechaOrig("1025100312");
        RespuestaBean respuesta9 = clienteSIX.anularFacturacionPostPago(entrada9);
        if(respuesta9 == null){
            System.out.println("Excepcion SIX");
        }else if (respuesta9.getResponseCode() != null ){
            if(respuesta9.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("Trama in: " + respuesta9.getTramaIn());
                System.out.println("Trama out: " + respuesta9.getTramaOut());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta9.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }*/
    /********FIN ANULAR PAGO CLARO**************/


    /****** PAGO CMR****Terminado******/
       /*EntradaBean entrada3 = new EntradaBean();
        entrada3.setNroTarjeta("6271807032535269");
        entrada3.setMonto("10.50");
        entrada3.setAuditoria(Constantes.TRACE.toString());
        entrada3.setTerminal("02548412");
        entrada3.setComercio("EL AGUSTINO-PRUEBA");
        entrada3.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada3.setCodSucursal("08");
        entrada3.setIdCajero("60606060");
        entrada3.setNroDocId("06975120");
        RespuestaBean respuesta3 = clienteSIX.pagoCuotaTarjetaCMR(entrada3);
        if(respuesta3 == null){
            System.out.println("Excepcion SIX");
        }else if (respuesta3.getResponseCode() != null ){
            if(respuesta3.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta3.getTramaIn());
                System.out.println("trama out: " + respuesta3.getTramaOut());
                System.out.println("código autorización: " + respuesta3.getCodAutorizacion());
                System.out.println("Fecha Original: " + respuesta3.getFechaOrig());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta3.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }*/
    /******* Pago CMR*****/


    /****** Anulación Pago CMR****Terminado******/
    /*EntradaBean entrada4 = new EntradaBean();
    entrada4.setNroTarjeta("6271807032535269");
    entrada4.setMonto("10.50");
    entrada4.setAuditoria(Constantes.TRACE.toString());
    entrada4.setTerminal("02548412");
    entrada4.setComercio("EL AGUSTINO-PRUEBA");
    entrada4.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
    entrada4.setCodSucursal("08");
    entrada4.setIdCajero("66666666");
    entrada4.setNroDocId("06975120");
    //nuevo campo anulacion
    entrada4.setIdAnular("000000100746");//cod autorizacion
    RespuestaBean respuesta4 = clienteSIX.anularTransaccionCMR(entrada4);
    if(respuesta4 == null){
        System.out.println("Error Excepcion");
    }else if (respuesta4.getResponseCode() != null ){
        if(respuesta4.getResponseCode().equals("00")){
            System.out.println("Sucessfull");
            System.out.println("trama in: " + respuesta4.getTramaIn());
            System.out.println("trama out: " + respuesta4.getTramaOut());
        }else{
            System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta4.getResponseCode() );
        }
    }else {
        System.out.println("No se pudo recuperar la respuesta del servidor");
    }*/
    /******* fin Anulación Pago CMR*****/


    /****** VENTA CMR****Terminado******/
        /*EntradaBean entrada6 = new EntradaBean();
        entrada6.setNroTarjeta("6271807038889157");
        entrada6.setMonto("10.00"); 
        entrada6.setAuditoria(Constantes.TRACE.toString());
        entrada6.setTerminal("02548412");
        entrada6.setComercio("EL AGUSTINO-PRUEBA");
        entrada6.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada6.setNroCuotas("2");
        entrada6.setCodSucursal("08");
        entrada6.setIdCajero("05050505");
        entrada6.setNroDocId("06997210");
        RespuestaBean respuesta6 = clienteSIX.ventaCreditoCMR(entrada6);
        if(respuesta6 == null){
            System.out.println("Excepcion");
        }else if (respuesta6.getResponseCode() != null ){
            if(respuesta6.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta6.getTramaIn());
                System.out.println("trama out: " + respuesta6.getTramaOut());
                System.out.println("código autorización: " + respuesta6.getCodAutorizacion());
                System.out.println("monto de cuota: " + respuesta6.getValorCuota());
            }else{
                System.out.println("monto de cuota: " + respuesta6.getValorCuota());
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta6.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }*/
    /******* Venta CMR*****/


    /******Anulación Venta CMR*******Terminado******/
    /*EntradaBean entrada7 = new EntradaBean();
    entrada7.setNroTarjeta("6271807023783837");
    entrada7.setMonto("20.00");
    entrada7.setAuditoria(Constantes.TRACE.toString());
    entrada7.setTerminal("02548412");//02548412
    entrada7.setComercio("EL AGUSTINO-PRUEBA");
    entrada7.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
    entrada7.setNroCuotas("2");
    entrada7.setCodSucursal("08");
    entrada7.setIdCajero("05050505");
    entrada7.setNroDocId("22304715");
    //nuevo campo anulacion
    entrada7.setIdAnular("000000100404");
    RespuestaBean respuesta7 = clienteSIX.anularTransaccionCMR(entrada7);
    if(respuesta7 == null){
        System.out.println("Excepcion");
    }else if (respuesta7.getResponseCode() != null ){
        if(respuesta7.getResponseCode().equals("00")){
            System.out.println("Sucessfull");
            System.out.println("trama in: " + respuesta7.getTramaIn());
            System.out.println("trama out: " + respuesta7.getTramaOut());
        }else{
            System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta7.getResponseCode() );
        }
    }else {
        System.out.println("No se pudo recuperar la respuesta del servidor");
    }*/
    /******* fin Anulación Venta CMR*****/


    /****** Pago Ripley*******Terminado*********/
    /*EntradaBean entrada8 = new EntradaBean();
        entrada8.setNroTarjeta("9604100226266426");
        entrada8.setMonto("20.00");
        entrada8.setAuditoria(Constantes.TRACE.toString());
        entrada8.setTerminal("02548412");
        entrada8.setComercio("EL AGUSTINO-PRUEBA");
        entrada8.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada8.setNroCuotas("1");
        entrada8.setCodSucursal("08");
        entrada8.setNroCaja("4321");
        entrada8.setIdCajero("66666666");
        RespuestaBean respuesta8 = clienteSIX.pagoCuotaTarjetaRipley(entrada8);
        if(respuesta8 == null){
            System.out.println("Error Excepcion");
            System.out.println("Excepcion");
        }else if (respuesta8.getResponseCode() != null ){
            if(respuesta8.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta8.getTramaIn());
                System.out.println("trama out: " + respuesta8.getTramaOut());
                System.out.println("código autorización: " + respuesta8.getCodAutorizacion());
                System.out.println("Fecha Original: " + respuesta8.getFechaOrigen());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta8.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }
    /******* Pago Ripley*****/
        
    
    /****** Anulación Pago Ripley*********Terminado*****/
    /*EntradaBean entrada9 = new EntradaBean();
        entrada9.setNroTarjeta("9604100012360037");
        entrada9.setMonto("20.00");
        entrada9.setAuditoria(Constantes.TRACE.toString());
        entrada9.setTerminal("02548412");
        entrada9.setComercio("EL AGUSTINO-PRUEBA");
        entrada9.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada9.setNroCuotas("1");
        entrada9.setCodSucursal("08");
        entrada9.setNroCaja("4321");
        entrada9.setIdCajero("66666666");
        //original
        entrada9.setAuditoriaOrig("000609");
        entrada9.setFechaOrig("1029174051");
        entrada9.setCodAutorizTrans("003669");
        RespuestaBean respuesta9 = clienteSIX.anulacionPagoRipley(entrada9);
        if(respuesta9 == null){
            System.out.println("Excepcion");
        }else if (respuesta9.getResponseCode() != null ){
            if(respuesta9.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta9.getTramaIn());
                System.out.println("trama out: " + respuesta9.getTramaOut());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta9.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }*/
    /******* Anulación Pago Ripley*****/

    /****** VENTA Ripley****Terminado******/
        /*EntradaBean entrada6 = new EntradaBean();
        entrada6.setNroTarjeta("9604100326534608");
        entrada6.setMonto("50.00"); 
        entrada6.setAuditoria(Constantes.TRACE.toString());
        entrada6.setTerminal("02548412");
        entrada6.setComercio("EL AGUSTINO-PRUEBA");
        entrada6.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada6.setNroCuotas("2");
        entrada6.setCodSucursal("08");
        //entrada6.setIdCajero("05050505");
        //entrada6.setNroDocId("06997210");
        RespuestaBean respuesta6 = clienteSIX.ventaCreditoRipley(entrada6);
        if(respuesta6 == null){
            System.out.println("Excepcion");
        }else if (respuesta6.getResponseCode() != null ){
            if(respuesta6.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta6.getTramaIn());
                System.out.println("trama out: " + respuesta6.getTramaOut());
                System.out.println("código autorización: " + respuesta6.getCodAutorizacion());
                System.out.println("monto de cuota: " + respuesta6.getValorCuota());
                System.out.println("primer vencimiento: " + respuesta6.getFechaPrimerVenc());
            }else{
                System.out.println("monto de cuota: " + respuesta6.getValorCuota());
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta6.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }
    /******* Venta Ripley*****/

    /******Anulación Venta Ripley *******Terminado******/
    /*EntradaBean entrada7 = new EntradaBean();
    entrada7.setNroTarjeta("9604100326534608");
    entrada7.setMonto("50.00");
    entrada7.setAuditoria(Constantes.TRACE.toString());
    entrada7.setTerminal("02548412");//02548412
    entrada7.setComercio("EL AGUSTINO-PRUEBA");
    entrada7.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
    entrada7.setNroCuotas("2");
    entrada7.setCodSucursal("08");
    //entrada7.setIdCajero("05050505");
    //entrada7.setNroDocId("22304715");
    //nuevo campo anulacion
    entrada7.setAuditoriaOrig("1271");
    entrada7.setFechaOrig("0331121717");
    entrada7.setCodAutorizTrans("194857");
    RespuestaBean respuesta7 = clienteSIX.anulacionVentaRipley(entrada7);
    if(respuesta7 == null){
        System.out.println("Excepcion");
    }else if (respuesta7.getResponseCode() != null ){
        if(respuesta7.getResponseCode().equals("00")){
            System.out.println("Sucessfull");
            System.out.println("trama in: " + respuesta7.getTramaIn());
            System.out.println("trama out: " + respuesta7.getTramaOut());
        }else{
            System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta7.getResponseCode() );
        }
    }else {
        System.out.println("No se pudo recuperar la respuesta del servidor");
    }
    /******* fin Anulación Venta Ripley*****/
        
    /************************************************ NO USAR *****************************************************/
    
    /***** CONSULTA Deuda CMR*******************/
        /*EntradaBean entrada4 = new EntradaBean();
        entrada4.setNroTarjeta("6271807023783837");
        entrada4.setAuditoria(Constantes.TRACE.toString());
        entrada4.setTerminal("10474349");
        entrada4.setComercio("071");
        entrada4.setCodSucursal("0286");
        entrada4.setUbicacion("LIMA");
        entrada4.setNroCaja("0978");
        entrada4.setIdCajero("12345678");
        RespuestaBean respuesta4 = clienteSIX.consultaDeudaCMR(entrada4);
        if(respuesta4 == null){
            System.out.println("Excepcion");
        }else if (respuesta4.getResponseCode() != null ){
            if(respuesta4.getResponseCode().equals("00")){
                System.out.println("Sucessfull");
                System.out.println("trama in: " + respuesta4.getTramaIn());
                System.out.println("trama out: " + respuesta4.getTramaOut());
                //Solo caso consulta servicio:
                System.out.println("trama out: " + respuesta4.getDeudaTotal());
            }else{
                System.out.println("VER TABLA CODIGO DE MENSAJES DE ERROR - ResponseCode : "+  respuesta4.getResponseCode() );
            }
        }else {
            System.out.println("No se pudo recuperar la respuesta del servidor");
        }*/
        /*******fin consulta CMR*****/

    }

}
