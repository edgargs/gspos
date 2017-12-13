package mifarma.farmaPrueba;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;
import mifarma.farmaCTL.ClienteSIX;

import mifarma.farmaUtil.Constantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Prueba420 {
    
    private static final Logger log = LoggerFactory.getLogger(Prueba420.class);
    
    public static void main(String[] args) {
     
    ClienteSIX clienteSIX = new ClienteSIX("prueba", "prueba", "prueba", 
                                            "172.30.10.78", "2005", "FSISOINT", 
                                            "estandar", "log4j.properties");
    
        /****** Extorno Pago CMR******Terminado****/
        EntradaBean entrada4 = new EntradaBean();
        entrada4.setNroTarjeta("6271807032535269");
        entrada4.setMonto("10.50");
        entrada4.setAuditoria("612");
        entrada4.setTerminal("02548412");
        entrada4.setComercio("EL AGUSTINO-PRUEBA"); 
        entrada4.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada4.setCodSucursal("08");//071
        entrada4.setIdCajero("60606060");
        entrada4.setNroDocId("06975120");
        entrada4.setMotivoExtorno("91");
        entrada4.setFechaOrig("20131016 102558");
        RespuestaBean respuesta4 = clienteSIX.extornarPagoCMR(entrada4);
        if(respuesta4 == null){
            System.out.println("Excepcion");
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
        }
        /******* fin Extorno Pago CMR*****/


        /****** Extorno Venta CMR*******Terminado*********/
        /*EntradaBean entrada4 = new EntradaBean();
        entrada4.setNroTarjeta("6271807023783837");
        entrada4.setMonto("10.50");
        entrada4.setAuditoria("613");
        entrada4.setTerminal("02548412");
        entrada4.setComercio("EL AGUSTINO-PRUEBA"); 
        entrada4.setUbicacion("AV. CESAR VALLEJO NRO. 1387 LIMA LIMA EL AGUSTINO");
        entrada4.setCodSucursal("08");
        entrada4.setIdCajero("60606060");
        entrada4.setNroDocId("22304715");
        entrada4.setNroCuotas("2");
        entrada4.setMotivoExtorno("91");
        entrada4.setFechaOrig("20131016 102558");
        RespuestaBean respuesta4 = clienteSIX.extornarVentaCMR(entrada4);
        if(respuesta4 == null){
            System.out.println("Excepcion");
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
        /******* fin Extorno Venta CMR*****/

    }

}
