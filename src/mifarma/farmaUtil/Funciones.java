package mifarma.farmaUtil;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import mifarma.farmaBean.EntradaBean;


public class Funciones {
 
    public Funciones() {
        
    }

    public static Date convertStringToDateFull(String strFecha) throws Exception{
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat(Constantes.FORMATO_DDMMYYYY_HHMMSS);
        Date fecha = null;
        fecha = formatoDelTexto.parse(strFecha);
        return fecha;
    }

    public static Date convertStringToDate(String strFecha) throws Exception{
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat(Constantes.FORMATO_DDMMYY);
        Date fecha = null;
        fecha = formatoDelTexto.parse(strFecha);
        return fecha;
    }

    public static String convertDateToHour(Date fecha) throws Exception{
        DateFormat df = new SimpleDateFormat(Constantes.FORMATO_HHMMSS);
        String strFecha = df.format(fecha); 
        return strFecha;
    }

    public static String convertDateToOnlyDate(Date fecha) throws Exception{
        DateFormat df = new SimpleDateFormat(Constantes.FORMATO_MMDD);
        String strFecha = df.format(fecha);
        return strFecha;
    }

    public static String formatoCeroIzq(int tamanio, String cadena){
        if(cadena.length() > tamanio){
            cadena = cadena.substring(cadena.length() - tamanio, cadena.length());
        }
        cadena = String.format("%0"+tamanio+"d", Integer.parseInt(cadena));
        return cadena;
    }

    public static String montoToString(String monto, int cantCeros){
        String strMonto = String.format("%0"+cantCeros+"d", new Integer("0"));
        if (monto != null){
            String entero = monto.substring(0, monto.indexOf("."));
            String decimal = monto.substring(monto.indexOf(".") + 1, monto.length());
            strMonto = String.format("%0"+cantCeros+"d", new Integer(entero + decimal));
        }
        return strMonto;
    }

    public static BigDecimal StringToBigDecimal(String cadena){
        BigDecimal valor = null;
        String entero = cadena.substring(0, cadena.length() - 2);
        String decimal = cadena.substring(cadena.length()- 2, cadena.length());
        valor = new BigDecimal(entero + "." + decimal);
        return valor;
    }

    public static void setPrefijo_ProductoServicioClaro(EntradaBean entrada){
        if(entrada.getTipoProdServ() == null){
            return;
        }
        switch (entrada.getTipoProdServ()){
            case "1": entrada.setProductoServicio(Constantes.PRODUCTO_SERVICIO_REC);
                      entrada.setPrefijo(Constantes.EMPTY);break;
            case "2": entrada.setProductoServicio(Constantes.PRODUCTO_SERVICIO_REC);
                      entrada.setPrefijo(Constantes.PREFIJO_NCS);break;
            case "3": entrada.setProductoServicio(Constantes.PRODUCTO_SERVICIO_RPD);
                      entrada.setPrefijo(Constantes.PREFIJO_NCD);break;
            case "4": entrada.setProductoServicio(Constantes.PRODUCTO_SERVICIO_1);
                      entrada.setPrefijo(Constantes.PREFIJO_23);break;
            case "5": entrada.setProductoServicio(Constantes.PRODUCTO_SERVICIO_101);
                      entrada.setPrefijo(Constantes.PREFIJO_23);break;
            case "6": entrada.setProductoServicio(Constantes.PRODUCTO_SERVICIO_102);
                      entrada.setPrefijo(Constantes.PREFIJO_23);break;
            case "7": entrada.setProductoServicio(Constantes.PRODUCTO_SERVICIO_103);
                      entrada.setPrefijo(Constantes.PREFIJO_23);break;
            default : entrada.setProductoServicio(null);
                      entrada.setPrefijo(null);
        }
    }

}
