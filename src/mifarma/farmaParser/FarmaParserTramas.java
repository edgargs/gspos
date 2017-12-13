package mifarma.farmaParser;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.TramaBean;

import mifarma.farmaUtil.Constantes;
import mifarma.farmaUtil.Funciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FarmaParserTramas {

    private static final Logger log = LoggerFactory.getLogger(FarmaParserTramas.class);
    private EntradaBean entrada;
    public static MessageFactory mfact;
    public DateFormat df = new SimpleDateFormat(Constantes.FORMATO_DDMMYYYY_HHMMSS);
    private DateFormat df1 = new SimpleDateFormat(Constantes.FORMATO_YYMM);
    private DateFormat df2 = new SimpleDateFormat(Constantes.FORMATO_YYYYMMDD);
    private DateFormat df3 = new SimpleDateFormat(Constantes.FORMATO_HHMMSS);
    private DateFormat df4 = new SimpleDateFormat(Constantes.FORMATO_MMDD);
    
 
    public FarmaParserTramas(){
        
    }

    public FarmaParserTramas(EntradaBean entrada){
        this.entrada = entrada;
    }

    public IsoMessage generarTrama(EntradaBean entrada) throws Exception {
        int tipoMensaje = Integer.parseInt(entrada.getTipoMensaje());
        IsoMessage solic   = null;
        switch (tipoMensaje){
            case Constantes.MSG_200:
                ParserTrama200 parserTrama200 = new ParserTrama200(entrada);
                solic = parserTrama200.generarTramaEnvio200(); break;
            case Constantes.MSG_400:
                ParserTrama400 parserTrama400 = new ParserTrama400();
                parserTrama400.setEntrada(entrada);
                solic = parserTrama400.generarTramaExtorno0400(); break;
            case Constantes.MSG_420:
                ParserTrama420 parserTrama420 = new ParserTrama420();
                parserTrama420.setEntrada(entrada);
                solic = parserTrama420.generarTramaExtorno0420(); break;
            case Constantes.MSG_800:
                ParserTrama800 parserTrama800 = new ParserTrama800();
                parserTrama800.setEntrada(entrada);
                solic = parserTrama800.generarTramaEcho800(); break;
            default:
                log.debug("Tipo Transacción inválida"); break;
        }
        return solic;
    }

    public static void leerConfiguracion() throws IOException{
        mfact = ConfigParser.createFromClasspathConfig(Constantes.FILE_CONFIG);
        mfact.setAssignDate(true);
        mfact.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System.currentTimeMillis() % 100000)));
    }

    public String DE02(){
        String de02 = null;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
            de02 = Constantes.CLARO_NRO_TARJETA;
        }else if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR) ||
                entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_FONOYA) ||
                entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_PLJOVEN)  //ASOSA - 04/09/2014 - RECAR - PLJOVEN
                ){
            de02 = Constantes.MOVISTAR_NRO_TARJETA;
        } else if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)){   //ASOSA - 28/10/2014 - RECAR - NEX
            de02 = Constantes.NEXTEL_NRO_TARJETA;
        }else if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAR_BITEL)){
            de02 = Constantes.BITEL_NRO_TARJETA;
        }else if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR)){
            de02 = entrada.getNroTarjeta();
        }else if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
                 entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
        ){
            de02 = entrada.getNroTarjeta();
        }
        return de02;
    }

    public String DE03(){
        String de03 = entrada.getTipoTrnscc() + entrada.getTipoCuentaFrom() + 
                      entrada.getTipoCuentaTo();
        if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAR_BITEL)){
            de03 = "000000";
        }
        return de03;
    }

    public String DE12(Date hoy){
        String de12 = df3.format(hoy);
        if (entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)) {  //ASOSA - 03/11/2014 - RECAR - NEX
            if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR)){
                de12 = null;
            }
            if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
                if(entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG_SRV)){
                    de12 = null;
                }
            }            
        } else if (entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA)) {    //ASOSA - 03/11/2014 - RECAR - NEX
            if (entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA)){
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)){
                    //de12 = entrada.getFechaOrig().substring(10, 15);
                    System.out.print("VALOR 12: '" + de12 + "'" );
                }
            }
        }
        return de12;
    }

    public String DE13(Date hoy){
        String de13 = df4.format(hoy);
        if (entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)) {  //ASOSA - 03/11/2014 - RECAR - NEX
            if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR)){
                de13 = null;
            }
            if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
                if(entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG_SRV)){
                    de13 = null;
                }
            }
        } else if (entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA)) {    //ASOSA - 03/11/2014 - RECAR - NEX
            if (entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA)){
                if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)){
                    //de13 = entrada.getFechaOrig().substring(5, 8);
                    System.out.print("VALOR 13: '" + de13 + "'" );
                }
            }
        }        
        return de13;
    }

    public String DE14(String hoy){
        String de14 = null;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR)){
           if (entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_TARJ) ||
               entrada.getTipoTrnscc().equals(Constantes.TRNS_CMPRA_VNTA) ||
               entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_VNTA)){
               de14 = hoy;
           }
        }
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR)){
            de14 = hoy;
        }
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
           entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
        ){
           if (entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_TARJ) ||
               entrada.getTipoTrnscc().equals(Constantes.TRNS_CMPRA_VNTA) ){
               de14 = hoy;
           }
        }
        return de14;
    }

    public String DE18(){
        String de18 = Constantes.COD_TIPO_NEGOCIO;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR)){
            de18 = null;
        }
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG_SRV)){
                de18 = null;
            }
        }
        return de18;
    }

    public String DE22(){
       String de22 = null;
       if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
            de22 = Constantes.MODO_ING_TARJ_CLARO;
       }else if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)){   //ASOSA - 28/10/2014 - RECAR - NEX
           de22  = Constantes.MODO_ING_TARJ_NEXTEL;
        }else if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR)){
            if (entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)){
                de22 = Constantes.MODO_ING_TARJ_CLARO;
            }
       }else if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAR_BITEL)){
           de22  = Constantes.MODO_ING_TARJ_DESCO;
        }else if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR)) {
            if(entrada.getModoIngrDatos().equals(Constantes.PTOVENTA_MODO_ING_TARJ_BANDA)){
               de22 = Constantes.MODO_ING_TARJ_BANDA;
            }else{
               de22 = Constantes.MODO_ING_TARJ_MANUAL;
            }
       }else if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
                 entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
       ) {
            if(entrada.getModoIngrDatos().equals(Constantes.PTOVENTA_MODO_ING_TARJ_BANDA)){
               de22 = Constantes.MODO_ING_TARJ_BANDA;
            }else{
               de22 = Constantes.MODO_ING_TARJ_MANUAL;
            }
       }
       return de22;
    }

    public String DE35(String fechaExp, Date hoy){
        char separador = '0';
        String tarjeta = Constantes.CLARO_NRO_TARJETA;
        String strFechaExp;
        String de35 = null;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR) ||
           entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
           entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
        ){
            separador = Constantes.SEPARADOR_CAMPOS;
            tarjeta = entrada.getNroTarjeta();
        }
        if(fechaExp == null){
            strFechaExp = "0000";
        }else{
            strFechaExp  = df1.format(hoy);
        }
        de35 = tarjeta + separador + strFechaExp + 
               Constantes.COD_TRACK2_SERVICIO + Constantes.COD_TRACK2_PVKI +
               Constantes.COD_TRACK2_PVV + Constantes.COD_TRACK2_CVC1 +
               Constantes.COD_TRACK2_LIBRE;
        return de35;
    }

    public String DE37(){
        String de37 = String.format("%1$-12s", entrada.getAuditoria());
        return de37;
    }

    public String DE38(){
       String de38 = null;
       if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
            entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
       ){
           if (entrada.getCodAutorizTrans() == null){
               de38 = Constantes.COD_AUTORIZ_TRX_DEFAULT;
           }else{
               de38 = entrada.getCodAutorizTrans();
           }
       }
       return de38;
    }

    public String DE41(){
        String de41 = String.format("%1$-8s", getEntrada().getTerminal());
        return de41;
    }

    public String DE42(){
        String de42 = null;
        if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)) { //ASOSA - 28/10/2014 - RECAR - NEX
            de42 = String.format("%1$-15s", entrada.getCodComerXCia());
        } else {
            de42 = String.format("%1$-15s", entrada.getComercio());
        }            
        return de42;
    }

    public String DE43(){
        String de43 = null;
        if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)) { //ASOSA - 28/10/2014 - RECAR - NEX
            de43 = String.format("%1$-40s", getEntrada().getNomComerXCia());
        } else {
            de43 = String.format("%1$-40s", getEntrada().getUbicacion());
        }
        return de43;
    }

    public String DE48(){
        String de48 = null;
        if( entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA) || 
            entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA)) {
            
            if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR) ||
                entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_FONOYA) ||
                entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_PLJOVEN)   //ASOSA - 04/09/2014 - RECAR - PLJOVEN
                ){
                de48 =   Constantes.COD_FORMATO_ESTANDAR  + 
                         String.format("%1$-11s", Constantes.COD_PROCESADOR) + 
                         String.format("%1$-11s", Constantes.COD_TELCO) +
                         //String.format("%1$-8s", Constantes.COD_PRODUCTO_RECARGA) + 
                         String.format("%1$-8s", entrada.getCodTipoProducto()) + //ASOSA - 13/07/2014
                         String.format("%1$-10s", entrada.getTelefono())+ 
                         Constantes.TIP_DOC_ID_RECARGA + 
                         String.format("%1$-14s", Constantes.EMPTY) + 
                         String.format("%1$-50s", Constantes.EMPTY) + 
                         Constantes.COD_GEN_PPV + /*docAutoriz*/
                         String.format("%1$-16s", Constantes.EMPTY) + 
                         String.format("%1$-10s", Constantes.EMPTY) + 
                         String.format("%1$-17s", Constantes.EMPTY) + 
                         String.format("%1$-15s", Constantes.CADENA_COMERCIAL_ORIGEN) + 
                         String.format("%1$-87s", Constantes.EMPTY);
            }
            else if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
                String nroRecargaOriginal = Constantes.EMPTY;
                if (entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA)){
                    //nroRecargaOriginal = entrada.getTelefono();
                    //nroRecargaOriginal = entrada.getAuditoriaOrig();
                    nroRecargaOriginal = entrada.getNumeroRecargaOrig();
                }
                de48 =  Constantes.COD_FORMATO_REDUCIDO + 
                        String.format("%1$-11s", Constantes.COD_PROCESADOR) + 
                        String.format("%1$-11s", Constantes.COD_TELCO) +
                        //String.format("%1$-8s", Constantes.COD_PRODUCTO_RECARGA) + 
                        String.format("%1$-8s", entrada.getCodTipoProducto()) + //ASOSA - 13/07/2014
                        String.format("%1$-20s", entrada.getTelefono()) + 
                        Constantes.TIP_DOC_ID_RECARGA +  /*entrada.getTipDocId() */
                        String.format("%1$-14s", Constantes.EMPTY) +/*entrada.getNroDocId())*/
                        String.format("%1$-50s", Constantes.EMPTY) + /*entrada.getNombreRazSoc())*/
                        Constantes.COD_GEN_PPV + /*docAutoriz*/
                        String.format("%1$-16s", Constantes.EMPTY) + /*entrada.getNroDocAutoriz())*/
                        String.format("%1$-12s", Funciones.montoToString("0.00", 12)) +/*Monto real*/
                        String.format("%1$-15s", nroRecargaOriginal) +
                        String.format("%1$-15s", Constantes.EMPTY);
            }
            else if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAR_BITEL)){
                de48 =  Constantes.COD_FORMATO_REDUCIDO + 
                        String.format("%1$-11s", "hanv") + 
                        String.format("%1$-15s", entrada.getTelefono()) + 
                        String.format("%1$-15s", Funciones.formatoCeroIzq(15,entrada.getAuditoria())) + 
                        String.format("%1$-6s", Constantes.EMPTY);
            }
        }
        return de48;
    }

    public String DE49(){
        String de49 = Constantes.COD_MON_SOLES;
        if(entrada.getMoneda().equals(Constantes.COD_PTOVENTA_MON_DOL)){
           de49 = Constantes.COD_MON_DOLARES;
        }
        return de49;
    }

    public String DE52(){
        String de52 = null;
            if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
                entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
            ){
                de52 = Constantes.PIN_DEFAULT;
            }
        return de52;
    }

    public String DE90(Date FhTrans_DE07) throws Exception{
        String de90 = null;
        String auditoria = String.format("%06d", new Integer(entrada.getAuditoria()));
        String fechaAnt = Funciones.convertDateToOnlyDate(FhTrans_DE07) + 
                        Funciones.convertDateToHour(FhTrans_DE07);
        if( (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG_SRV))      ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG))      ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG))                
        ){
             auditoria = String.format("%06d", new Integer(entrada.getAuditoriaOrig()));
             fechaAnt = entrada.getFechaOrig();
         }
        if( (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA))
        ){
            auditoria = String.format("%06d", new Integer(entrada.getAuditoriaOrig()));
            fechaAnt = entrada.getFechaOrig();
        }
        if((entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR)    &&
                entrada.getTipoMensaje().equals(""+Constantes.MSG_420))       ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG_SRV))  ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA))  ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG))      ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY) &&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG))   ||
            ((entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR) ||
                entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_FONOYA) ||
                entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_PLJOVEN)) &&
                entrada.getTipoMensaje().equals(""+Constantes.MSG_400))       ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO) &&
                entrada.getTipoMensaje().equals(""+Constantes.MSG_400)) ||
            (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL) &&   //ASOSA - 27/11/2014 - RECAR - NEX
                (entrada.getTipoMensaje().equals(""+Constantes.MSG_200) ||
                 entrada.getTipoMensaje().equals(""+Constantes.MSG_400))&&
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA))
          ){
                //ASOSA - 15/09/2014 - RECAR
                if (((entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_MOVISTAR) ||
                    entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_FONOYA) ||
                    entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_PLJOVEN)) &&
                    entrada.getTipoMensaje().equals(""+Constantes.MSG_400))){
                        auditoria = String.format("%06d", new Integer(entrada.getAuditoriaOrig())); 
                 }
                if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL) &&
                    entrada.getTipoMensaje().equals(""+Constantes.MSG_200)
                     ){
                        auditoria = String.format("%06d", new Integer(entrada.getAuditoriaOrig())); 
                        fechaAnt = entrada.getFechaOrig();
                 }

                //ASOSA - 15/09/2014 - RECAR
                de90 = String.format("%04d", new Integer(""+Constantes.MSG_200)) + 
                        auditoria +
                        fechaAnt +
                        //"1201101835" +
                        String.format("%1$-11s", Constantes.COD_ID_ADQUIRIENTE) +
                        String.format("%1$-11s", Constantes.COD_ID_ADQUIRIENTE);
                
            }
        
            if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL) &&   //ASOSA - 27/11/2014 - RECAR - NEX
               entrada.getTipoMensaje().equals(""+Constantes.MSG_400)&&
               entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)){
                   if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL) &&
                        entrada.getTipoMensaje().equals(""+Constantes.MSG_400)){
                           auditoria = String.format("%06d", new Integer(entrada.getAuditoriaOrig())); 
                           fechaAnt = entrada.getFechaOrig();
                           fechaAnt = (fechaAnt.replace(" ", "")).substring(4,14);
                    }
                   de90 = String.format("%04d", new Integer(""+Constantes.MSG_200)) + 
                           auditoria +
                           fechaAnt +
                           String.format("%1$-11s", Constantes.COD_ID_ADQUIRIENTE) +
                           String.format("%1$-11s", Constantes.COD_ID_ADQUIRIENTE);
               }
        return de90;
    }
    
    public String DE98(){
        String de98 = null;
        if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)) { //ASOSA - 28/10/2014 - RECAR - NEX
            de98 = String.format("%1$-25s", getEntrada().getNomComerXCia().trim());
        }
        return de98;
    }

    public String DE102(){
        String de102 = null;
        return de102;
    }

    public String DE103(){
        String de103 = null;
        return de103;
    }

    public String DE112(){
        String de112 = null;
        String moneda = Constantes.COD_MON_SOLES;
        if(entrada.getMoneda().equals(Constantes.COD_PTOVENTA_MON_DOL)){
            moneda = Constantes.COD_MON_DOLARES;
        }
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_RIPLEY) ||
           entrada.getTipoRecaudacion().equals(Constantes.TIPO_VENTA_RIPLEY)
        ){
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_TARJ) ||
               entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG) ||
               entrada.getTipoTrnscc().equals(Constantes.TRNS_CMPRA_VNTA) ){
                de112 = /*elemento 005*/
                        Constantes.ID_SUB_ELEM_005 + 
                        Constantes.LONG_ELEM_005_SIN_MULTIP + 
                        Constantes.REQUERIMIENTO_CUOTAS + 
                        Constantes.NO_DIFERIDO + 
                        String.format("%02d", new Integer(entrada.getNroCuotas())) +
                        moneda +
                        Constantes.ISSUER;
            }
        }
       return de112;
    }
    
    public String DE122(){
        String de122 = null;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)) {
            de122 = String.format("%1$-3s", Constantes.tipoFormato) + 
                    String.format("%1$-11s", Constantes.procesador) + 
                    String.format("%1$-11s", Constantes.empresa) + 
                    String.format("%1$-8s", Constantes.prodServ);
        }        
        return de122;
    }
    
    public String DE123(){
        String de123 = null;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)) {
            de123 = String.format("%1$-2s", Constantes.tipoDocTelefono) + 
                    String.format("%1$-21s", entrada.getTelefono());
        }        
        return de123;
    }

    public String DE124(){
        String de124 = null;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
            if( entrada.getTipoTrnscc().equals(Constantes.TRNS_CNSULTA_SRV) ||
                entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_PRE_AUTORI_SRV) ||
                entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG_SRV)
            ){
                de124 = entrada.getCodTransExtend();
            }
        }
        return de124;
    }

    public String DE125(String moneda, Date hoy){
        String de125 = null;
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CLARO)){
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_CNSULTA_SRV)){
                de125 = consultaServiciosClaro();
            }
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_PRE_AUTORI_SRV)){
                de125 = pagoServiciosClaro(moneda);
            }
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_PAG_SRV)){
                de125 = anularPagoServicioClaro(moneda);
            }
            if(entrada.getTipoMensaje().equals(Constantes.MSG_400)){
                if(entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)){
                    de125 = anularPagoServicioClaro(moneda);
                }
            }
        }
        //INI ASOSA - 29/10/2014 - RECAR - NEX
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)){
            String nroPpvOrignal = (entrada.getNroOperacionPpv() == null ||     //ASOSA - 27/11/2014 - RECAR - NEX
                                    entrada.getNroOperacionPpv().trim().equals("")) ? " " : entrada.getNroOperacionPpv();
            de125 = String.format("%1$-2s", Constantes.formato125) + 
                    String.format("%1$-30s", Constantes.filler125) + 
                    String.format("%1$-2s", Constantes.filler125) + 
                    String.format("%1$-20s", Constantes.filler125) + 
                    String.format("%1$-2s", Constantes.tipoDocumento125) + 
                    String.format("%1$-20s", Constantes.nroDocumento125) + 
                    String.format("%1$-50s", Constantes.nomRazSocial125) + 
                    String.format("%1$-2s", Constantes.tipoComprobante125) + 
                    String.format("%1$-16s", " ") + 
                    String.format("%1$-27s", Constantes.filler125) + 
                    String.format("%1$-15s", Constantes.filler125) +
                    String.format("%1$-2s", Constantes.medioPago125) +                    
                    String.format("%1$-15s", nroPpvOrignal) + //NRO OPER PPV Original
                    String.format("%1$-2s",  Constantes.canal125) + //CANAL
                    String.format("%1$-3s", Constantes.giro125) + //GIRO NEGOCIO
                    String.format("%1$-8s", Constantes.ubigeo125) + //UBIGEO
                    String.format("%1$-39s", Constantes.filler125);
        }            
        //FIN ASOSA - 29/10/2014 - RECAR - NEX
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR)){
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_CNSULTA_SRV)){
                de125 = consultaCMR();
            }
            if(entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_TARJ)     || 
               entrada.getTipoTrnscc().equals(Constantes.TRNS_CMPRA_VNTA)   ||
               entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_VNTA)){
                de125 = pagoTarjetaCMR(moneda, hoy);
            }
        }
        return de125;
    }

    public String DE126(){
        String de126 = null;
        //INI ASOSA - 29/10/2014 - RECAR - NEX
        if(entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_NEXTEL)){
            de126 = String.format("%1$-4s", Constantes.formato126) + 
                    String.format("%1$-15s", entrada.getCodComerXCia()) + 
                    String.format("%1$-25s", entrada.getNomComerXCia()) + 
                    String.format("%1$-15s", "000000000000000") + 
                    String.format("%1$-40s", " ") + 
                    String.format("%1$-80s", " ") + 
                    String.format("%1$-30s", " ") + 
                    String.format("%1$-8s", Constantes.ubigeo125) + 
                    String.format("%1$-100s", Constantes.filler125);
        }            
        //FIN ASOSA - 29/10/2014 - RECAR - NEX
        return de126;
    }

    public String DE127(){
        String de127 = null;
        return de127;
    }

    public String DE128(TramaBean trama){
        StringBuilder cadena = new StringBuilder();
        String de128 = null;
        if(entrada.getTipoTrnscc().equals(Constantes.TRNS_RECARGA)     ||
           entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_RECARGA) ){
            de128 = Constantes.EMPTY;
        }else if (entrada.getTipoRecaudacion().equals(Constantes.TIPO_RECAUD_CMR) ||
                  entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_PRE_AUTORI_SRV) ){
            if(trama.getNroTarjeta_DE02() != null){
                cadena.append(trama.getNroTarjeta_DE02());
            }
            if(trama.getCodProceso_DE03() != null){
                cadena.append(trama.getCodProceso_DE03());
            }
            if(trama.getMontoTrans_DE04() != null){
                cadena.append(trama.getMontoTrans_DE04());
            }
            if(trama.getFhTrans_DE07() != null){
                cadena.append(trama.getFhTrans_DE07());
            }
            if(trama.getAuditoria_DE11() != null){
                cadena.append(trama.getAuditoria_DE11());
            }
            if(trama.getAutorizTrans_DE38() != null){
                cadena.append(trama.getAutorizTrans_DE38());
            }
            if(trama.getCodRespuesta_DE39() != null){
                cadena.append(trama.getCodRespuesta_DE39());
            }
            if(!cadena.toString().equals(Constantes.EMPTY)){
                de128 = cadena.toString();
            }
        }
        return de128;
    }

    public String consultaServiciosClaro(){
        String de125 = null;
        String nroIdDeudor = Constantes.EMPTY;
        nroIdDeudor = entrada.getTelefono();
        de125 = Constantes.COD_FORMATO_ESTANDAR + 
                String.format("%1$-11s", Constantes.BIN_PROCE_COBRANZA) +
                String.format("%1$-11s", Constantes.ID_ACREEDOR) +
                String.format("%1$-8s", entrada.getProductoServicio()) +
                String.format("%1$-4s", Constantes.COD_PZA_RECAUDADOR) +
                String.format("%1$-4s", entrada.getCodSucursal()) + /*cod agencia recaudador*/
                String.format("%1$-2s", Constantes.TIP_ID_DEUDOR) +
                String.format("%1$-21s", entrada.getPrefijo() + nroIdDeudor) +
                String.format("%1$-3s", Constantes.EMPTY) + /*cod ciudad*/
                String.format("%1$-3s", Constantes.EMPTY) + /*tipo doc pago*/
                String.format("%1$-16s", Constantes.EMPTY) + /*nro doc pago*/
                String.format("%012d", new Integer("0")) + /*nro Operación de cobranza*/
                String.format("%1$-20s", Constantes.EMPTY) + /*filler*/
                Constantes.TAMANIO_MX_BLOQUE +
                Constantes.POS_ULTIMO_DOCUMENTO +
                Constantes.PUNTERO_BD;
        return de125;
    }

    public String pagoServiciosClaro(String moneda){
        String de125 = 
        /*Cabecera del requerimiento*/
        Constantes.COD_FORMATO_ESTANDAR + 
        String.format("%1$-11s", Constantes.BIN_PROCE_COBRANZA) +
        String.format("%1$-11s", Constantes.ID_ACREEDOR) +
        String.format("%1$-8s", entrada.getProductoServicio()) +
        String.format("%1$-4s", Constantes.COD_PZA_RECAUDADOR) + 
        String.format("%1$-4s", entrada.getCodSucursal()) + /*cod agencia recaudador*/
        String.format("%1$-2s", Constantes.TIP_ID_DEUDOR) +
        String.format("%1$-21s", entrada.getPrefijo() + entrada.getTelefono()) +
        String.format("%1$-3s", Constantes.EMPTY) + /*cod ciudad*/
        String.format("%02d", new Integer("1")) + /*Nro total prod/servicios pagados*/
        String.format("%03d", new Integer("1")) + /*Nro total documentos/recibos pagados*/
        String.format("%1$-10s", Constantes.EMPTY) + /*filler*/

        /*Cabecera del pago realizado*/
        String.format(Constantes.MEDIO_PAGO_EFECTIVO) +
        Funciones.montoToString(entrada.getMonto(), 11) +
        String.format("%011d", new Integer("0")) + /*importe cargo Cta.*/
        String.format("%1$-15s", Constantes.EMPTY) + /*nro cheque 1*/
        String.format("%1$-3s", Constantes.EMPTY) + /*cod SBS Banco girador 1*/
        String.format("%011d", new Integer("0")) + /*importe cheque 1*/
        String.format("%1$-1s", Constantes.EMPTY) + /*plaza cheque 1*/
        String.format("%1$-15s", Constantes.EMPTY) + /*nro cheque 2*/
        String.format("%1$-3s", Constantes.EMPTY) + /*cod SBS Banco girador 2*/
        String.format("%011d", new Integer("0")) + /*importe cheque 2*/
        String.format("%1$-1s", Constantes.EMPTY) + /*plaza cheque 2*/
        String.format("%1$-15s", Constantes.EMPTY) + /*nro cheque 3*/
        String.format("%1$-3s", Constantes.EMPTY) + /*cod SBS Banco girador 3*/
        String.format("%011d", new Integer("0")) + /*importe cheque 3*/
        String.format("%1$-1s", Constantes.EMPTY) + /*plaza cheque 3*/
        moneda +
        Constantes.TIPO_CAMBIO + 
        Funciones.montoToString(entrada.getMonto(), 11) + /*pago total realizado*/
        String.format("%1$-10s", Constantes.EMPTY) + /*filler*/

        /*Cabecera producto/servicio i=1 */
        String.format("%1$-3s", entrada.getProductoServicio()) +
        String.format("%1$-2s", Constantes.ESTADO_DEUDOR) + /*Estado del deudor*/
        Funciones.montoToString(entrada.getMonto(), 11) + /*monto*/
        String.format("%019d", Constantes.ZERO_INTEGER) + /*nro cta abono*/
        String.format("%012d", Constantes.ZERO_INTEGER) + /*nro ref abono*/
        String.format("%02d", new Integer("1")) + /*nro docs/recibos pagados*/
        String.format("%1$-10s", Constantes.EMPTY) + /*filler*/

        //Detalle documentos/recibos pagados del Servicio i
        String.format("%1$-3s", entrada.getProductoServicio()) + /*Tipo documento/recibo de pago*/
        String.format("%1$-16s", entrada.getNumRecibo()) + 
        String.format("%1$-6s", Constantes.EMPTY) + /*periodo cotización*/
        String.format("%1$-2s", Constantes.TIP_ID_DEUDOR) +
        String.format("%1$-15s", entrada.getTelefono()) + /*Nro DocId Deudor*/
        String.format("%011d", new Integer("0")) + /*importe original del doc/recibo*/
        Funciones.montoToString(entrada.getMonto(), 11) + /*importe pagado del doc/recibo*/
        String.format("%1$-2s", Constantes.EMPTY) + /* cod concepto 1*/
        String.format("%011d", new Integer("0")) + /*importe concepto 1*/
        String.format("%1$-2s", Constantes.EMPTY) + /* cod concepto 2*/
        String.format("%011d", new Integer("0")) + /*importe concepto 2*/
        String.format("%1$-2s", Constantes.EMPTY) + /* cod concepto 3*/
        String.format("%011d", new Integer("0")) + /*importe concepto 3*/
        String.format("%1$-2s", Constantes.EMPTY) + /*cod concepto 4*/
        String.format("%011d", new Integer("0")) + /*importe concepto 4*/
        String.format("%1$-2s", Constantes.EMPTY) + /*cod concepto 5*/
        String.format("%011d", new Integer("0")) + /*importe concepto 5*/
        String.format("%1$-16s", entrada.getTelefono()) + /*ref deuda*/
        String.format("%1$-34s", Constantes.EMPTY); /*filler*/
        return de125;
    }

    public String anularPagoServicioClaro(String moneda){
        String de125 = null;
        String operacionOriginal = entrada.getIdAnular();
        de125 =
        /*Cabecera del pago*/
        Constantes.COD_FORMATO_ESTANDAR + 
        String.format("%1$-11s", Constantes.BIN_PROCE_COBRANZA) +
        String.format("%1$-11s", Constantes.ID_ACREEDOR) +
        String.format("%1$-8s", entrada.getProductoServicio()) +
        String.format("%1$-4s", Constantes.COD_PZA_RECAUDADOR) + 
        String.format("%1$-4s", entrada.getCodSucursal()) + /*cod agencia recaudador*/
        String.format("%1$-2s", Constantes.TIP_ID_DEUDOR) + 
        String.format("%1$-21s", entrada.getPrefijo() + entrada.getTelefono()) + /*nro id deudor*/
        String.format("%1$-3s", Constantes.EMPTY) + /*cod ciudad*/
        String.format("%1$-12s", Constantes.EMPTY) + /*filler*/
        String.format("%1$-50s", Constantes.EMPTY) + /*filler*/
        /*Cabecera de pago a anular*/
        entrada.getIdAnular() + /*Nro. operación de cobranza Anulación*/
        operacionOriginal ; /*Nro. operación original Anulación*/
        return de125;
    }

    /*No usado*/
    public String consultaCMR(){
        String de125 =  Constantes.COD_FORMATO_REDUCIDO + 
                        String.format("%1$-11s", Constantes.BIN_PROCE_COBRANZA) +
                        String.format("%1$-11s", Constantes.ID_ACREEDOR) +
                        String.format("%1$-8s", Constantes.EMPTY) +/*cod producto servicio*/
                        String.format("%1$-4s", Constantes.COD_PZA_RECAUDADOR) + 
                        String.format("%1$-4s", entrada.getCodSucursal()) + /*cod agencia recaudador*/
                        String.format("%1$-2s", Constantes.TIP_DOC_ID_DNI) + /*tipo id deudor*/
                        String.format("%1$-21s", entrada.getNroTarjeta()) + /*nro id deudor*/
                        String.format("%1$-3s", Constantes.EMPTY) + /*cod ciudad*/
                        String.format("%1$-3s", Constantes.EMPTY) + /*tipo doc pago*/
                        String.format("%1$-16s", Constantes.EMPTY) + /*nro doc pago*/
                        String.format("%1$-12s", Constantes.EMPTY) + /*nro Operación de cobranza*/
                        String.format("%1$-20s", Constantes.EMPTY) + /*filler*/
                        Constantes.TAMANIO_MX_BLOQUE +
                        Constantes.POS_ULTIMO_DOCUMENTO +
                        Constantes.PUNTERO_BD;
        return de125;
    }

    public String pagoTarjetaCMR(String moneda, Date hoy){
        String de125 = null;
        String fecha = df2.format(hoy);
        String hora = df3.format(hoy);
        String codFuncion = Constantes.EMPTY;
        String nroCuotas = String.format("%02d", new Integer("0"));
        String autorizTrxPrevia = String.format("%012d", new Integer("0"));
        String sucursal  = Funciones.formatoCeroIzq(3, entrada.getCodSucursal());

        if(entrada.getTipoTrnscc().equals(Constantes.TRNS_PAG_TARJ)){
            codFuncion = Constantes.COD_FUNCION_PAGO;
        }
        if(entrada.getTipoTrnscc().equals(Constantes.TRNS_CMPRA_VNTA)){
            codFuncion = Constantes.COD_FUNCION_VENTA;
            nroCuotas = String.format("%02d", new Integer(entrada.getNroCuotas()));
        }
        if(entrada.getTipoTrnscc().equals(Constantes.TRNS_ANU_VNTA)){
            autorizTrxPrevia = entrada.getIdAnular();
            codFuncion = Constantes.COD_FUNCION_ANUL;
        }
        if(entrada.getTipoMensaje().equals(Constantes.MSG_420+"")){
            codFuncion = Constantes.COD_FUNCION_EXTORNO;
        }

        de125 = /*Cabecera*/
                String.format("%06d", new Integer("0")) + 
                Constantes.APLICACION_SERVIDORA + 
                Constantes.COD_PAIS + 
                String.format("%1$-8s", Constantes.COD_COMERCIO) + 
                codFuncion + 
                Constantes.ORIGEN_TRANSACCION + 
                fecha + 
                hora + 
                Constantes.NUM_ORIGINAL_FASA + sucursal + 
                Funciones.formatoCeroIzq(4, entrada.getTerminal()) + 
                String.format("%08d", new Integer("0")) + /*Secuencia POS*/
                autorizTrxPrevia + 
                String.format("%1$-11s", Constantes.EMPTY) + 
                /*Mensaje*/
                entrada.getNroTarjeta() + 
                Constantes.TIP_DOC_ID_DNI_CMR +
                String.format("%012d", new Integer(entrada.getNroDocId())) + 
                moneda + 
                Constantes.FACTOR_CAMBIO + 
                Constantes.MODO_PAGO_NORMAL + 
                String.format("%02d", new Integer("0")) + 
                Funciones.montoToString(entrada.getMonto(), 14) +
                nroCuotas +
                String.format("%014d", new Integer("0")) + /*Otro monto*/
                Constantes.BOLETA + // entrada.getTipDocVenta() + 
                String.format("%08d", new Integer("0")) + /*entrada.getNroDocVenta()*/
                String.format("%08d", new Integer(entrada.getIdCajero())) + 
                String.format("%08d", new Integer("0")) + 
                String.format("%1$-40s", Constantes.EMPTY) + 
                String.format("%016d", new Integer("0")) + 
                Constantes.MODO_CAPTURA_PIN + 
                String.format("%1$-62s", Constantes.EMPTY);
        return de125;
    }

    public EntradaBean getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaBean entrada) {
        this.entrada = entrada;
    }
}
