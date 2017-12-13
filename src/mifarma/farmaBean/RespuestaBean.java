package mifarma.farmaBean;


import com.solab.iso8583.IsoMessage;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mifarma.farmaUtil.Constantes;
import mifarma.farmaUtil.Funciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RespuestaBean { 
    
    private static final Logger log = LoggerFactory.getLogger(RespuestaBean.class);
    
    private IsoMessage im;
    private EntradaBean entrada;
    /*Los tamaños estan junto a los campos String*/
    private String responseCode;                    /*2*/
    private String codAutorizacion;                 /*12*/
    private String codOperacion;                    /*15*/
    private String tramaIn;                         /*1000*/
    private String tramaOut;                        /*1000*/
    private BigDecimal deudaTotal; /*2 decimales*/
    private BigDecimal valorCuota; /*2 decimales*/
    private String fechaPrimerVenc;                 /*8*/
    private String numRecibo;                       /*16*/
    private String numOperacCobranza;               /*12*/
    private String numOperacAcreedor;               /*12*/
    private String fechaTrsscPago;                  /*15*/
    private String fechaOrigen;                     /*10*/


    /***Campos no utilizados************************************************************/

    private BigDecimal monto;  
    private Date fecha;
    private BigDecimal tipoCambio;
    private String codAutorizTrans; //6

    /*campo 48 */
    private String telefono; //20
    private String monedaRecarga; //3
    private BigDecimal importe;
    private String minutos; //6
    private String origenRpta;//1
    private String codRptaExtendido;//2
    private String descrError;//30

    /*campo 54*/
    private String tipoCuentaCont;//2   valor:30 Tarjeta de Credito
    private String tipoSaldoCont;//2
    private String monedaSaldoCont;//7
    private String signoSaldoCont;//1
    private BigDecimal saldoCont;
    private String tipoCuentaDisp;//2   valor:30 Tarjeta de Credito
    private String tipoSaldoDisp;//2
    private String monedaSaldoDisp;//7
    private String signoSaldoDisp;//1
    private BigDecimal saldoDisp;

    /*campo 121*/
    private String nroRecarga;//15
    private BigDecimal valorRecarga;
    private BigDecimal valorVenta;
    private BigDecimal descuento;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private String nroDocAutorizAnul;// 16  Solo caso anulaciones
    private String mensajeCelular; //160


    /********campo 125 Claro - Anexo 7 ******/
    private String tipoFormato;
    /*Consulta*/
    private String nombreDeudor; //20
    private Integer nroServicDevuelt;
    private Integer masDocumentos; //
    private Integer TamanioMaxBloq;
    private Integer posUltDocum;
    private BigDecimal punteroBD;
    private Integer origRpta;
    private List<ServicioBean> servicios;
    /*Fin Consulta*/

    /*Pago*/    
        /*cabecera respuesta*/
    /*********************/
    private Integer numPrdServPagado;
    private String origenRptaCasoTrans; //1
    private String codRptaExtendCasoTrans; //3
    private String descrRptaAplicat; //30
        /*cabecera pago realizado*/
    private String nombreDeudorCasoTrans; //20
    private String rucDeudor;//15
    private String rucAcreedor;//15
    private String codZonaDeudor;//6
    private List<ProductoBean> productos;
    /*Fin Pago*/
    
    /*Anulacion*/
    private Integer numServAnul;
    private String nombreDeudorAnu; //20
    private String rucDeudorAnu; //15
    private String rucAcreedorAnu;//15
    private String numOperacCobranzaAnu;//12
    private String numOperacAcreedorAnu; //12
    private String origenRptaCasoTransAnu; //1
    private String codRptaExtendCasoTransAnu; //3
    private List<ProductoBean> productosAnulados;
    /*Fin Anulacion*/
    /********************************************/

     /********campo 125 CMR - Anexo 11 ******/
     private Integer numeroCuotas;
     private BigDecimal valorCuota_12;
     private Integer motivo;
     private BigDecimal valorCuota2_21;
     private String tipoCliente; //1
     private String claveEmisor;//2
     private String estadoMsjeVisorPOS;//1
     private String msjeVisorPOS;// 45
     private String estadoMsjeCMRVoucher;//1
     private String msjeCMRVoucher;// 45
     private String estadoMsjeCamp;//1
     private String msjeCampLinea1;// 45     
     private String msjeCampLinea2;// 45     
     private String msjeCampLinea3;// 45     
     private String codPromocVigentes; // 6
     private String reservado;
    private String numeroRecarga; //96
    
    //INI ASOSA - 06/11/2014 - RECAR - NEX
    private String nroOperacionPpv; //18
    //FIN ASOSA - 06/11/2014 - RECAR - NEX

    public RespuestaBean() {
        
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTramaIn() {
        return tramaIn;
    }

    public void setTramaIn(String tramaIn) {
        this.tramaIn = tramaIn;
    }

    public String getTramaOut() {
        return tramaOut;
    }

    public void setTramaOut(String tramaOut) {
        this.tramaOut = tramaOut;
    }

    public void cargarRespuesta() throws Exception{
        if (im.hasField(7)){
            setFechaTrsscPago(im.getField(7).toString());
            setFechaOrigen(im.getField(7).toString());
        }
        if (im.hasField(38)){
            setCodAutorizacion(im.getObjectValue(38).toString());
        }
        if (im.hasField(39)){
            setResponseCode(im.getObjectValue(39).toString());
        }
        if (im.hasField(48) ){
            String rptaDE48 = im.getObjectValue(48).toString().trim();
            int i = 216;
            if(entrada.getTipoMensaje().equals(Constantes.MSG_400)){
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR)){
                    if(entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)){
                        setCodOperacion(rptaDE48.substring(i, rptaDE48.length()));
                    }
                }
            }
        }
        if (im.hasField(121) ){
            String rptaDE121 = im.getObjectValue(121).toString().trim();
            int i = 2;
            //if(entrada.getTipoMensaje().equals(Constantes.MSG_200)){
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
                    if(entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)){
                        setNumeroRecarga(rptaDE121.substring(i, i+15));
                    }
                }
            //}
        }
        if (im.hasField(112)){
            String rptaDE112 = im.getObjectValue(112).toString();
            if(!rptaDE112.equals(Constantes.EMPTY)){
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
                   entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
                ){
                    if( entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_TARJ) ||
                        entrada.getTipoTrnscc().equals(Constantes.TRNS_CMPRA_VNTA) ||
                        entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG) ){
                        parser112Ripley(rptaDE112);
                    }
                }
           }
        }
        if (im.hasField(125)){
            String rptaDE125 = im.getObjectValue(125).toString();
            if(!rptaDE125.equals(Constantes.EMPTY)){
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
                    parser125Claro(rptaDE125);
                }
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR)){
                    parser125CMR(rptaDE125);
                }
                //INI ASOSA - 06/11/2014 - RECAR - NEX
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)){    
                    parset125Nextel(rptaDE125);
                }
                //FIN ASOSA - 06/11/2014 - RECAR - NEX
            }
        }
    }

    public void parser112Ripley(String rptaDE112)throws Exception{
        int pos005 = 14;
        //ERIOS 25.03.2014 Respuesta
        int pos006 = pos005 + 10;
        int idx = pos006;
        if(rptaDE112.length() >= idx + 8){
            String strValorCuota = rptaDE112.substring(idx, idx + 8);
            BigDecimal valorCuota = Funciones.StringToBigDecimal(strValorCuota);
            setValorCuota(valorCuota);
        }
        idx = pos006 + 8;
        if(rptaDE112.length() >= idx + 6){
            String strFechaVencCuota = rptaDE112.substring(idx, idx + 6);
            Date fechaVenc = Funciones.convertStringToDate(strFechaVencCuota);
            setFechaPrimerVenc(Funciones.convertDateToOnlyDate(fechaVenc));
        }
    }

    public void parser125Claro(String rptaDE125){
        String tipoFormato = rptaDE125.substring(0, 2);
        if(tipoFormato.equals(Constantes.COD_FORMATO_ESTANDAR)){
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_CNSULTA_SRV)){
                consultaClaroFormato01(rptaDE125);
            }
            if( entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_PRE_AUTORI_SRV)){
                casoTransaccionPago(rptaDE125);
            }
        }
    }

    public void consultaClaroFormato01(String rptaDE125){
        int cabecera = 192;
        int servicio = 150;
        int documento = 206;
        int posIniDoc = 0;
        Integer nroDocsServ = 0;
        int posIniServ = cabecera +138;
        int idx = posIniServ;
        if(getDeudaTotal() == null){
            setDeudaTotal(BigDecimal.ZERO);
        }        
        if(rptaDE125.length() >= idx + 2){
            nroDocsServ = Integer.parseInt(rptaDE125.substring(idx, idx + 2).trim());
        }
        posIniDoc = cabecera + servicio;
        for(int i=0; i < 1/*nroDocsServ*/ ;i++){
            idx = posIniDoc;
            idx = idx + 3;
            if(rptaDE125.length() >= idx + 16){
                String strNumRecibo = rptaDE125.substring(idx, idx + 16).trim();
                setNumRecibo(strNumRecibo);
            }
            idx = idx + 181;
            if(rptaDE125.length() >= idx + 11){
                String strDeudaRecibo = rptaDE125.substring(idx, idx + 11).trim();
                if(!strDeudaRecibo.equals(Constantes.EMPTY)){
                    BigDecimal bigDeudaRecibo = Funciones.StringToBigDecimal(strDeudaRecibo);
                    setDeudaTotal(getDeudaTotal().add(bigDeudaRecibo));
                }
            }
            posIniDoc = posIniDoc + documento;
        }
    }

    public void casoTransaccionPago(String rptaDE125){
        /*cabecera respuesta*/
        setNumOperacCobranza(rptaDE125.substring(66, 78));
        setNumOperacAcreedor(rptaDE125.substring(78, 90));

        /*setNumPrdServPagado(Integer.parseInt(rptaDE125.substring(90,92)));
        setOrigenRptaCasoTrans(rptaDE125.substring(105, 106));
        setCodRptaExtendCasoTrans(rptaDE125.substring(106,109 ));
        setDescrRptaAplicat(rptaDE125.substring(109, 139));
        //cabecera pago realizado
        setNombreDeudorCasoTrans(rptaDE125.substring(139, 159));
        setRucDeudor(rptaDE125.substring(159, 174));
        setRucAcreedor(rptaDE125.substring(174, 189));
        setCodZonaDeudor(rptaDE125.substring(189, 195));*/

        /* productos/servicios */
        /*int posIniServ = 215;
        List<ProductoBean> listaProductos = new ArrayList<ProductoBean>();
        for(int i = 0; i<getNroServicDevuelt();i++){
            int idx = posIniServ;
            ProductoBean producto = new ProductoBean();
            producto.setCodProd(rptaDE125.substring(idx, idx + 3));
            idx = idx + 3;
            producto.setDescrProd(rptaDE125.substring(idx,15));
            idx = idx + 15;
            producto.setImporeTotal(new BigDecimal(rptaDE125.substring(idx,11)));
            idx = idx + 11;
            producto.setMensaje1(rptaDE125.substring(idx,40));
            idx = idx + 40;
            producto.setMensaje2(rptaDE125.substring(idx,40));
            idx = idx + 40;
            producto.setNroDocumentos(Integer.parseInt(rptaDE125.substring(idx,2)));
            posIniServ = posIniServ + 131;*/

            /*documentos/recibos */
                /*int posIniDoc = posIniServ;
                List<DetalleProductoBean> detalles = new ArrayList<DetalleProductoBean>();
                for(i=0; i<producto.getNroDocumentos();i++){
                    idx = posIniDoc;
                    DetalleProductoBean detalle= new DetalleProductoBean();
                    detalle.setTipoDocumento(rptaDE125.substring(idx, idx + 3));
                    idx = idx + 3;
                    detalle.setDescrDocumento(rptaDE125.substring(idx, idx + 15));
                    idx = idx + 15;
                    detalle.setNroDocPago(rptaDE125.substring(idx, idx + 16));
                    idx = idx + 16;
                    detalle.setPeriodoCotiz(rptaDE125.substring(idx, idx + 6));
                    idx = idx + 6;
                    detalle.setTipDocIdDeudor(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setNroDocIdDeudor(rptaDE125.substring(idx, idx + 15));
                    idx = idx + 15;
                    detalle.setFechaEmision(rptaDE125.substring(idx, idx + 8));
                    idx = idx + 8;
                    detalle.setFechaVenc(rptaDE125.substring(idx, idx + 8));
                    idx = idx + 8;
                    detalle.setImporteDocum(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto1(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto1(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto2(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto2(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto3(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto3(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto4(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto4(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto5(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto5(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setIndicFacturacion(Integer.parseInt(rptaDE125.substring(idx, idx + 1)));
                    idx = idx + 1;
                    detalle.setNroFactura(rptaDE125.substring(idx, idx + 11));
                    detalles.add(detalle);
                    posIniDoc = posIniDoc + 211;
                }
            producto.setDetalleProductos(detalles);*/
            /*fin documentos/recibos*/
            /*listaProductos.add(producto);
            posIniServ = posIniDoc;
        } 
        setProductos(listaProductos);*/
    }
    
    /**
     * Parsear dato 125 para nextel
     * @author ASOSA
     * @kind RECAR - NEX
     * @since 06/11/2014
     * @param rptaDE125
     */
    public void parset125Nextel(String rptaDE125){
        String rpta = "";
        log.debug("CADENA 125 del 210: '" + rptaDE125 + "'");
        log.info("CADENA 125 del 210: '" + rptaDE125 + "'");
        if (rptaDE125.length() > 265) {
            rpta = rptaDE125.substring(250, 265);
        }        
        log.debug("SUBSTR 125 del 210: '" + rpta + "'");
        log.info("SUBSTR 125 del 210: '" + rpta + "'");
        setNroOperacionPpv(rpta);
    }

    public void parser125CMR(String rptaDE125){
        int cabecera = 80;
        int idx = cabecera + 62;
        int idx2= cabecera + 65;
        String codRpta = rptaDE125.substring(idx, idx + 1);
        idx++;
        String motivoCodRpta = rptaDE125.substring(idx, idx + 2);
        if(getResponseCode() != null){
            if(getResponseCode().equals("00") && codRpta.equals("7")){
                setResponseCode(motivoCodRpta);
            }
        }
        setCodAutorizacion(rptaDE125.substring(idx2, idx2+12));
        //GFonseca 24/09/2013. Se añade el monto a pagar por la cuota y fecha de vencimiento
        int idx3 = cabecera + 77;
        String strMontoCuota = rptaDE125.substring(idx3, idx3 + 14);
        if(strMontoCuota.trim().equals("00000000000000") ){//Cuando no se realizo correctamente el pago
            setValorCuota(null);
        }else if(strMontoCuota!=null && !strMontoCuota.trim().equals("")){
            setValorCuota(Funciones.StringToBigDecimal(strMontoCuota));
        }
        //Fecha de vencimiento
        int idx4 = cabecera + 91;
        String strFecVen = rptaDE125.substring(idx4, idx4 + 8);
        if(strFecVen!=null && !strFecVen.trim().equals("") && !strFecVen.equals("00000000")){
            setFechaPrimerVenc(strFecVen);
        }else{
            setFechaPrimerVenc(null);
        }
    }


    /***********************METODOS NO UTILIZADOS*******************************************/

    public RespuestaBean casoAnulacion(RespuestaBean respuesta, String rptaDE125, EntradaBean entrada){
        /*cabecera respuesta*/
        setNumServAnul(Integer.parseInt(rptaDE125.substring(66, 68)));
        setNombreDeudorAnu(rptaDE125.substring(68,88));
        setRucDeudorAnu(rptaDE125.substring(88, 103));
        setRucAcreedorAnu(rptaDE125.substring(103, 118));
        setNumOperacCobranzaAnu(rptaDE125.substring(118, 130));
        setNumOperacAcreedorAnu(rptaDE125.substring(130, 142));
        setOrigenRptaCasoTransAnu(rptaDE125.substring(172, 173));
        setCodRptaExtendCasoTransAnu(rptaDE125.substring(173,176 ));

        /*productos/servicios anulados*/
        int posIniServ = 206;
        List<ProductoBean> listaProductosAnulados = new ArrayList<ProductoBean>();
        for(int i = 0; i<getNumServAnul();i++){
            int idx = posIniServ;
            ProductoBean productoAnulado = new ProductoBean();
            productoAnulado.setCodProd(rptaDE125.substring(idx, idx + 3));
            idx = idx + 3;
            productoAnulado.setDescrProd(rptaDE125.substring(idx,15));
            idx = idx + 15;
            productoAnulado.setImporeTotal(new BigDecimal(rptaDE125.substring(idx,11)));
            idx = idx + 11;
            productoAnulado.setMensaje1(rptaDE125.substring(idx,40));
            idx = idx + 40;
            productoAnulado.setMensaje2(rptaDE125.substring(idx,40));
            idx = idx + 40;
            productoAnulado.setNroDocumentos(Integer.parseInt(rptaDE125.substring(idx,2)));
            posIniServ = posIniServ + 131;

            /*detalle producto/servicio anulado*/
                int posIniDoc = posIniServ;
                List<DetalleProductoBean> detalles = new ArrayList<DetalleProductoBean>();
                for(i=0; i<productoAnulado.getNroDocumentos();i++){
                    idx = posIniDoc;
                    DetalleProductoBean detalle= new DetalleProductoBean();
                    detalle.setTipoDocumento(rptaDE125.substring(idx, idx + 3));
                    idx = idx + 3;
                    detalle.setDescrDocumento(rptaDE125.substring(idx, idx + 15));
                    idx = idx + 15;
                    detalle.setNroDocPago(rptaDE125.substring(idx, idx + 16));
                    idx = idx + 16;
                    detalle.setPeriodoCotiz(rptaDE125.substring(idx, idx + 6));
                    idx = idx + 6;
                    detalle.setTipDocIdDeudor(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setNroDocIdDeudor(rptaDE125.substring(idx, idx + 15));
                    idx = idx + 15;
                    detalle.setFechaEmision(rptaDE125.substring(idx, idx + 8));
                    idx = idx + 8;
                    detalle.setFechaVenc(rptaDE125.substring(idx, idx + 8));
                    idx = idx + 8;
                    detalle.setImporteDocum(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto1(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto1(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto2(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto2(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto3(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto3(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto4(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto4(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setCodConcepto5(rptaDE125.substring(idx, idx + 2));
                    idx = idx + 2;
                    detalle.setImporteConcepto5(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                    idx = idx + 11;
                    detalle.setIndicFacturacion(Integer.parseInt(rptaDE125.substring(idx, idx + 1)));
                    idx = idx + 1;
                    detalle.setNroFactura(rptaDE125.substring(idx, idx + 11));
                    detalles.add(detalle);
                    posIniDoc = posIniDoc + 211;
                }
            productoAnulado.setDetalleProductos(detalles);
            /*fin detalle servicio anulado*/
            listaProductosAnulados.add(productoAnulado);
            posIniServ = posIniDoc;
        } 
        setProductosAnulados(listaProductosAnulados);
        
        return respuesta;
    }

     public RespuestaBean casoConsultaFormato02(RespuestaBean respuesta, String rptaDE125){
         /*cabecera*/
         setDeudaTotal(new BigDecimal(0));
         setNombreDeudor(rptaDE125.substring(59,79 ));        
         setNroServicDevuelt(Integer.parseInt(rptaDE125.substring(79,81)));
         setMasDocumentos(Integer.parseInt(rptaDE125.substring(93, 94)));
         setTamanioMaxBloq(Integer.parseInt(rptaDE125.substring(94, 99)));
         setPosUltDocum(Integer.parseInt(rptaDE125.substring(99, 102)));
         setPunteroBD(new BigDecimal(rptaDE125.substring(102, 112)));
         setOrigRpta(Integer.parseInt(rptaDE125.substring(112, 113)));
         /*productos/servicios*/
         int posIniServ = 126;
         List<ServicioBean> listaServicios = new ArrayList<ServicioBean>();
         for(int i = 0; i<getNroServicDevuelt();i++){
             int idx = posIniServ;
             ServicioBean servicio = new ServicioBean();
             servicio.setCodServicio(rptaDE125.substring(idx, idx + 3));
             idx = idx + 3;
             servicio.setMonedaServ(rptaDE125.substring(idx,idx+3));
             idx = idx + 3;
             servicio.setEstadoDeudor(rptaDE125.substring(idx,idx+2));
             idx = idx + 2;
             servicio.setMensaje1(rptaDE125.substring(idx,idx+40));
             idx = idx + 40;
             servicio.setMensaje2(rptaDE125.substring(idx,idx+40));
             idx = idx + 40;
             servicio.setIndiCronolPago(Integer.parseInt(rptaDE125.substring(idx,idx+1)));
             idx = idx + 1;
             servicio.setPermitePagVenc(Integer.parseInt(rptaDE125.substring(idx,idx+1)));
             idx = idx + 1;
             servicio.setRestricPago(Integer.parseInt(rptaDE125.substring(idx,idx+1)));
             idx = idx + 1;
             Integer nroDocsServicio = Constantes.ZERO_INTEGER;
             if(219 <= rptaDE125.length()){
                 String cadena = rptaDE125.substring(217,2).trim();
                 if(!cadena.equals(Constantes.EMPTY)){
                     nroDocsServicio = (Integer.parseInt(cadena));
                 }
             }
             posIniServ = posIniServ + 98;
             /*documentos/recibos*/
                 int posIniDoc = posIniServ;
                 List<DocumentoBean> documentos = new ArrayList<DocumentoBean>();
                 for(int j=0; j < nroDocsServicio ;j++){
                     idx = posIniDoc;
                     DocumentoBean documento= new DocumentoBean();
                     documento.setTipDocPago(rptaDE125.substring(idx, idx + 3));
                     idx = idx + 3;
                     documento.setNumDocPago(rptaDE125.substring(idx, idx + 16));
                     idx = idx + 16;
                     documento.setReferDeuda(rptaDE125.substring(idx, idx + 16));
                     idx = idx + 16;
                     documento.setFechaVenc(rptaDE125.substring(idx, idx + 8));
                     idx = idx + 8;
                     documento.setImporteMinPago(new BigDecimal(rptaDE125.substring(idx, idx + 11)));
                     idx = idx + 11;
                     idx = idx + 54;
                     if(idx <= rptaDE125.length()){
                         String monto = rptaDE125.substring(idx, idx + 11).trim();
                         if(!monto.equals(Constantes.EMPTY)){
                             BigDecimal saldoTotalPagar = Funciones.StringToBigDecimal(monto);
                             setDeudaTotal(getDeudaTotal().add(saldoTotalPagar));
                         }
                     }
                     documentos.add(documento);
                     posIniDoc = posIniDoc + 65;
                 }
             servicio.setDocumentos(documentos);
             /*fin documentos/recibos*/
             listaServicios.add(servicio);
             posIniServ = posIniDoc;
         } 
         setServicios(listaServicios);
         return respuesta;
     }


    public String getNroRecarga() {
        return nroRecarga;
    }

    public void setNroRecarga(String nroRecarga) {
        this.nroRecarga = nroRecarga;
    }

    public BigDecimal getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(BigDecimal valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public BigDecimal getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(BigDecimal valorVenta) {
        this.valorVenta = valorVenta;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNroDocAutorizAnul() {
        return nroDocAutorizAnul;
    }

    public void setNroDocAutorizAnul(String nroDocAutorizAnul) {
        this.nroDocAutorizAnul = nroDocAutorizAnul;
    }

    public String getMensajeCelular() {
        return mensajeCelular;
    }

    public void setMensajeCelular(String mensajeCelular) {
        this.mensajeCelular = mensajeCelular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMonedaRecarga() {
        return monedaRecarga;
    }

    public void setMonedaRecarga(String monedaRecarga) {
        this.monedaRecarga = monedaRecarga;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String getOrigenRpta() {
        return origenRpta;
    }

    public void setOrigenRpta(String origenRpta) {
        this.origenRpta = origenRpta;
    }

    public String getCodRptaExtendido() {
        return codRptaExtendido;
    }

    public void setCodRptaExtendido(String codRptaExtendido) {
        this.codRptaExtendido = codRptaExtendido;
    }

    public String getDescrError() {
        return descrError;
    }

    public void setDescrError(String descrError) {
        this.descrError = descrError;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getCodAutorizTrans() {
        return codAutorizTrans;
    }

    public void setCodAutorizTrans(String codAutorizTrans) {
        this.codAutorizTrans = codAutorizTrans;
    }

    public String getTipoCuentaCont() {
        return tipoCuentaCont;
    }

    public void setTipoCuentaCont(String tipoCuentaCont) {
        this.tipoCuentaCont = tipoCuentaCont;
    }

    public String getTipoSaldoCont() {
        return tipoSaldoCont;
    }

    public void setTipoSaldoCont(String tipoSaldoCont) {
        this.tipoSaldoCont = tipoSaldoCont;
    }

    public String getMonedaSaldoCont() {
        return monedaSaldoCont;
    }

    public void setMonedaSaldoCont(String monedaSaldoCont) {
        this.monedaSaldoCont = monedaSaldoCont;
    }

    public String getSignoSaldoCont() {
        return signoSaldoCont;
    }

    public void setSignoSaldoCont(String signoSaldoCont) {
        this.signoSaldoCont = signoSaldoCont;
    }

    public BigDecimal getSaldoCont() {
        return saldoCont;
    }

    public void setSaldoCont(BigDecimal saldoCont) {
        this.saldoCont = saldoCont;
    }

    public String getTipoCuentaDisp() {
        return tipoCuentaDisp;
    }

    public void setTipoCuentaDisp(String tipoCuentaDisp) {
        this.tipoCuentaDisp = tipoCuentaDisp;
    }

    public String getTipoSaldoDisp() {
        return tipoSaldoDisp;
    }

    public void setTipoSaldoDisp(String tipoSaldoDisp) {
        this.tipoSaldoDisp = tipoSaldoDisp;
    }

    public String getMonedaSaldoDisp() {
        return monedaSaldoDisp;
    }

    public void setMonedaSaldoDisp(String monedaSaldoDisp) {
        this.monedaSaldoDisp = monedaSaldoDisp;
    }

    public String getSignoSaldoDisp() {
        return signoSaldoDisp;
    }

    public void setSignoSaldoDisp(String signoSaldoDisp) {
        this.signoSaldoDisp = signoSaldoDisp;
    }

    public BigDecimal getSaldoDisp() {
        return saldoDisp;
    }

    public void setSaldoDisp(BigDecimal saldoDisp) {
        this.saldoDisp = saldoDisp;
    }

    public String getNombreDeudor() {
        return nombreDeudor;
    }

    public void setNombreDeudor(String nombreDeudor) {
        this.nombreDeudor = nombreDeudor;
    }

    public Integer getNroServicDevuelt() {
        return nroServicDevuelt;
    }

    public void setNroServicDevuelt(Integer nroServicDevuelt) {
        this.nroServicDevuelt = nroServicDevuelt;
    }

    public Integer getMasDocumentos() {
        return masDocumentos;
    }

    public void setMasDocumentos(Integer masDocumentos) {
        this.masDocumentos = masDocumentos;
    }

    public Integer getTamanioMaxBloq() {
        return TamanioMaxBloq;
    }

    public void setTamanioMaxBloq(Integer TamanioMaxBloq) {
        this.TamanioMaxBloq = TamanioMaxBloq;
    }

    public Integer getPosUltDocum() {
        return posUltDocum;
    }

    public void setPosUltDocum(Integer posUltDocum) {
        this.posUltDocum = posUltDocum;
    }

    public BigDecimal getPunteroBD() {
        return punteroBD;
    }

    public void setPunteroBD(BigDecimal punteroBD) {
        this.punteroBD = punteroBD;
    }

    public Integer getOrigRpta() {
        return origRpta;
    }

    public void setOrigRpta(Integer origRpta) {
        this.origRpta = origRpta;
    }

    public List<ServicioBean> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioBean> servicios) {
        this.servicios = servicios;
    }

    public String getNumOperacCobranza() {
        return numOperacCobranza;
    }

    public void setNumOperacCobranza(String numOperacCobranza) {
        this.numOperacCobranza = numOperacCobranza;
    }

    public String getNumOperacAcreedor() {
        return numOperacAcreedor;
    }

    public void setNumOperacAcreedor(String numOperacAcreedor) {
        this.numOperacAcreedor = numOperacAcreedor;
    }

    public Integer getNumPrdServPagado() {
        return numPrdServPagado;
    }

    public void setNumPrdServPagado(Integer numPrdServPagado) {
        this.numPrdServPagado = numPrdServPagado;
    }

    public String getOrigenRptaCasoTrans() {
        return origenRptaCasoTrans;
    }

    public void setOrigenRptaCasoTrans(String origenRptaCasoTrans) {
        this.origenRptaCasoTrans = origenRptaCasoTrans;
    }

    public String getCodRptaExtendCasoTrans() {
        return codRptaExtendCasoTrans;
    }

    public void setCodRptaExtendCasoTrans(String codRptaExtendCasoTrans) {
        this.codRptaExtendCasoTrans = codRptaExtendCasoTrans;
    }

    public String getDescrRptaAplicat() {
        return descrRptaAplicat;
    }

    public void setDescrRptaAplicat(String descrRptaAplicat) {
        this.descrRptaAplicat = descrRptaAplicat;
    }

    public String getNombreDeudorCasoTrans() {
        return nombreDeudorCasoTrans;
    }

    public void setNombreDeudorCasoTrans(String nombreDeudorCasoTrans) {
        this.nombreDeudorCasoTrans = nombreDeudorCasoTrans;
    }

    public String getRucDeudor() {
        return rucDeudor;
    }

    public void setRucDeudor(String rucDeudor) {
        this.rucDeudor = rucDeudor;
    }

    public String getRucAcreedor() {
        return rucAcreedor;
    }

    public void setRucAcreedor(String rucAcreedor) {
        this.rucAcreedor = rucAcreedor;
    }

    public String getCodZonaDeudor() {
        return codZonaDeudor;
    }

    public void setCodZonaDeudor(String codZonaDeudor) {
        this.codZonaDeudor = codZonaDeudor;
    }

    public List<ProductoBean> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoBean> productos) {
        this.productos = productos;
    }

    public Integer getNumServAnul() {
        return numServAnul;
    }

    public void setNumServAnul(Integer numServAnul) {
        this.numServAnul = numServAnul;
    }

    public String getNombreDeudorAnu() {
        return nombreDeudorAnu;
    }

    public void setNombreDeudorAnu(String nombreDeudorAnu) {
        this.nombreDeudorAnu = nombreDeudorAnu;
    }

    public String getRucDeudorAnu() {
        return rucDeudorAnu;
    }

    public void setRucDeudorAnu(String rucDeudorAnu) {
        this.rucDeudorAnu = rucDeudorAnu;
    }

    public String getRucAcreedorAnu() {
        return rucAcreedorAnu;
    }

    public void setRucAcreedorAnu(String rucAcreedorAnu) {
        this.rucAcreedorAnu = rucAcreedorAnu;
    }

    public String getNumOperacCobranzaAnu() {
        return numOperacCobranzaAnu;
    }

    public void setNumOperacCobranzaAnu(String numOperacCobranzaAnu) {
        this.numOperacCobranzaAnu = numOperacCobranzaAnu;
    }

    public String getNumOperacAcreedorAnu() {
        return numOperacAcreedorAnu;
    }

    public void setNumOperacAcreedorAnu(String numOperacAcreedorAnu) {
        this.numOperacAcreedorAnu = numOperacAcreedorAnu;
    }

    public String getOrigenRptaCasoTransAnu() {
        return origenRptaCasoTransAnu;
    }

    public void setOrigenRptaCasoTransAnu(String origenRptaCasoTransAnu) {
        this.origenRptaCasoTransAnu = origenRptaCasoTransAnu;
    }

    public String getCodRptaExtendCasoTransAnu() {
        return codRptaExtendCasoTransAnu;
    }

    public void setCodRptaExtendCasoTransAnu(String codRptaExtendCasoTransAnu) {
        this.codRptaExtendCasoTransAnu = codRptaExtendCasoTransAnu;
    }

    public List<ProductoBean> getProductosAnulados() {
        return productosAnulados;
    }

    public void setProductosAnulados(List<ProductoBean> productosAnulados) {
        this.productosAnulados = productosAnulados;
    }

    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public BigDecimal getValorCuota_12() {
        return valorCuota_12;
    }

    public void setValorCuota_12(BigDecimal valorCuota_12) {
        this.valorCuota_12 = valorCuota_12;
    }

    public Integer getMotivo() {
        return motivo;
    }

    public void setMotivo(Integer motivo) {
        this.motivo = motivo;
    }

    public BigDecimal getValorCuota2_21() {
        return valorCuota2_21;
    }

    public void setValorCuota2_21(BigDecimal valorCuota2_21) {
        this.valorCuota2_21 = valorCuota2_21;
    }

    public String getFechaPrimerVenc() {
        return fechaPrimerVenc;
    }

    public void setFechaPrimerVenc(String fechaPrimerVenc) {
        this.fechaPrimerVenc = fechaPrimerVenc;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getClaveEmisor() {
        return claveEmisor;
    }

    public void setClaveEmisor(String claveEmisor) {
        this.claveEmisor = claveEmisor;
    }

    public String getEstadoMsjeVisorPOS() {
        return estadoMsjeVisorPOS;
    }

    public void setEstadoMsjeVisorPOS(String estadoMsjeVisorPOS) {
        this.estadoMsjeVisorPOS = estadoMsjeVisorPOS;
    }

    public String getMsjeVisorPOS() {
        return msjeVisorPOS;
    }

    public void setMsjeVisorPOS(String msjeVisorPOS) {
        this.msjeVisorPOS = msjeVisorPOS;
    }

    public String getEstadoMsjeCMRVoucher() {
        return estadoMsjeCMRVoucher;
    }

    public void setEstadoMsjeCMRVoucher(String estadoMsjeCMRVoucher) {
        this.estadoMsjeCMRVoucher = estadoMsjeCMRVoucher;
    }

    public String getMsjeCMRVoucher() {
        return msjeCMRVoucher;
    }

    public void setMsjeCMRVoucher(String msjeCMRVoucher) {
        this.msjeCMRVoucher = msjeCMRVoucher;
    }

    public String getEstadoMsjeCamp() {
        return estadoMsjeCamp;
    }

    public void setEstadoMsjeCamp(String estadoMsjeCamp) {
        this.estadoMsjeCamp = estadoMsjeCamp;
    }

    public String getMsjeCampLinea1() {
        return msjeCampLinea1;
    }

    public void setMsjeCampLinea1(String msjeCampLinea1) {
        this.msjeCampLinea1 = msjeCampLinea1;
    }

    public String getMsjeCampLinea2() {
        return msjeCampLinea2;
    }

    public void setMsjeCampLinea2(String msjeCampLinea2) {
        this.msjeCampLinea2 = msjeCampLinea2;
    }

    public String getMsjeCampLinea3() {
        return msjeCampLinea3;
    }

    public void setMsjeCampLinea3(String msjeCampLinea3) {
        this.msjeCampLinea3 = msjeCampLinea3;
    }

    public String getCodPromocVigentes() {
        return codPromocVigentes;
    }

    public void setCodPromocVigentes(String codPromocVigentes) {
        this.codPromocVigentes = codPromocVigentes;
    }

    public String getReservado() {
        return reservado;
    }

    public void setReservado(String reservado) {
        this.reservado = reservado;
    }

    public BigDecimal getDeudaTotal() {
        return deudaTotal;
    }

    public void setDeudaTotal(BigDecimal deudaTotal) {
        this.deudaTotal = deudaTotal;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public String getCodOperacion() {
        return codOperacion;
    }

    public void setCodOperacion(String codOperacion) {
        this.codOperacion = codOperacion;
    }

    public BigDecimal getValorCuota() {
        return valorCuota;
    } 

    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    public String getNumRecibo() {
        return numRecibo;
    } 

    public void setNumRecibo(String numRecibo) {
        this.numRecibo = numRecibo;
    }

    public String getFechaTrsscPago() {
        return fechaTrsscPago;
    }

    public void setFechaTrsscPago(String fechaTrsscPago) {
        this.fechaTrsscPago = fechaTrsscPago;
    }

    public String getFechaOrigen() {
        return fechaOrigen;
    }

    public void setFechaOrigen(String fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    public IsoMessage getIm() {
        return im;
    }

    public void setIm(IsoMessage im) {
        this.im = im;
    }

    public EntradaBean getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaBean entrada) {
        this.entrada = entrada;
    }

    public String getNumeroRecarga(){
        return numeroRecarga;
    }
    
    public void setNumeroRecarga(String numeroRecarga) {
        this.numeroRecarga = numeroRecarga;
    }

    public String getNroOperacionPpv() {
        return nroOperacionPpv;
    }

    public void setNroOperacionPpv(String nroOperacionPpv) {
        this.nroOperacionPpv = nroOperacionPpv;
    }
}
