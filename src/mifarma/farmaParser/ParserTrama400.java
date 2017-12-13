package mifarma.farmaParser;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.TramaBean;

import mifarma.farmaUtil.Constantes;
import mifarma.farmaUtil.Funciones;


public class ParserTrama400 extends FarmaParserTramas{

    public ParserTrama400(){
        super();
    }
 
    public ParserTrama400(EntradaBean entrada){
        super(entrada);
    }

    private TramaBean crearTrama400() throws Exception{
        DateFormat df1 = new SimpleDateFormat(Constantes.FORMATO_YYYYMMDD_HHMMSS);
        Date hoy = df1.parse(getEntrada().getFechaOrig());
        //String auditoria  = Funciones.formatoCeroIzq(6,getEntrada().getAuditoria());
        String auditoria  = Funciones.formatoCeroIzq(6,getEntrada().getAuditoriaOrig());    //ASOSA - 12/09/2014 - RECAR
        getEntrada().setMoneda(Constantes.COD_PTOVENTA_MON_SOL);
        Funciones.setPrefijo_ProductoServicioClaro(getEntrada());
        
        TramaBean trama = new TramaBean();
        trama.setNroTarjeta_DE02(DE02());
        trama.setCodProceso_DE03(DE03());
        trama.setMontoTrans_DE04(getEntrada().getMonto());
        trama.setFhTrans_DE07(df.format(hoy));
        trama.setAuditoria_DE11(auditoria);
        //INI ASOSA - 03/11/2014 - RECAR - NEX
        trama.setFechaTransLocal_DE12(DE12(hoy));   
        trama.setHoraTransLocal_DE13(DE13(hoy));
        //FIN ASOSA - 03/11/2014 - RECAR - NEX
        trama.setFechaCaptura_DE17(df.format(hoy));
        trama.setModoIngrDatos_DE22(DE22());
        trama.setTipoPtoServicio_DE25(Constantes.COD_TIPO_PTO_SERVICIO);
        trama.setCodIdAdquiriente_DE32(Constantes.COD_ID_ADQUIRIENTE);
        trama.setCodRedReenvio_DE33(Constantes.COD_ID_ADQUIRIENTE);
        trama.setPista2_DE35(DE35(null, hoy));
        trama.setNumRecupRef_DE37(String.format("%1$-12s", auditoria));
        trama.setCodRespuesta_DE39(getEntrada().getMotivoExtorno());
        trama.setIdTerminal_DE41(DE41());
        trama.setCodInstAdquir_DE42(DE42());
        trama.setDatosInstAdquir_DE43(DE43());
        trama.setDatosProducto_DE48(DE48());
        trama.setMoneda_DE49(DE49());
        trama.setCtaDesde_DE102(DE102());
        trama.setCtaHasta_DE103(DE103());
        //INI ASOSA - 03/11/2014 - RECAR - NEX
        trama.setCadena_DE98(DE98());  
        trama.setRuteo_DE122(DE122());  
        trama.setDocIdentidad_DE123(DE123());  
        //FIN ASOSA - 03/11/2014 - RECAR - NEX
        trama.setReservUsoPrivado_DE124(DE124());
        trama.setReservUsoPrivado_DE125(DE125(trama.getMoneda_DE49(), hoy));        
        trama.setReservUsoPrivado_DE126(DE126());
        trama.setReservUsoPrivado_DE127(DE127());
        trama.setMac_DE128(DE128(trama));
        return trama;
    }

    public IsoMessage generarTramaExtorno0400()throws Exception{
        leerConfiguracion();
        TramaBean trama = crearTrama400();
        IsoMessage solic = mfact.newMessage(Constantes.MSG_BYTE_400);
        solic.setValue(2, 
                       trama.getNroTarjeta_DE02(), 
                       IsoType.LLVAR, 16);
        trama.setNroTarjeta_DE02(solic.getField(2).toString());
        solic.setValue(3,
                       trama.getCodProceso_DE03(),
                       IsoType.NUMERIC, 6);
        trama.setCodProceso_DE03(solic.getField(3).toString());
        solic.setValue(4,
                       new BigDecimal(trama.getMontoTrans_DE04()),
                       IsoType.AMOUNT, 0);
        solic.setValue(7,
                       Funciones.convertStringToDateFull(trama.getFhTrans_DE07()),
                       IsoType.DATE10, 0);
        trama.setMontoTrans_DE04(solic.getField(4).toString());
        solic.setValue(11,
                       trama.getAuditoria_DE11(),
                       IsoType.NUMERIC, 6);
        trama.setAuditoria_DE11(solic.getField(11).toString());
        solic.setValue(17,
                       Funciones.convertStringToDateFull(trama.getFechaCaptura_DE17()),
                       IsoType.DATE4, 0);
        solic.setValue(22,
                       trama.getModoIngrDatos_DE22(),
                       IsoType.NUMERIC, 3);
        solic.setValue(25,
                       trama.getTipoPtoServicio_DE25(),
                       IsoType.NUMERIC, 2);
        solic.setValue(32,
                       trama.getCodIdAdquiriente_DE32(),
                       IsoType.LLVAR, 0);
        solic.setValue(33, 
                       trama.getCodRedReenvio_DE33(), 
                       IsoType.LLVAR, 0);
        solic.setValue(35, 
                       trama.getPista2_DE35(), 
                       IsoType.LLVAR, 0);
        solic.setValue(37, 
                       trama.getNumRecupRef_DE37(), 
                       IsoType.ALPHA, 12);
        solic.setValue(39, 
                       trama.getCodRespuesta_DE39(),
                       IsoType.ALPHA, 2);
        solic.setValue(41, 
                       trama.getIdTerminal_DE41(), 
                       IsoType.ALPHA, 8);
        solic.setValue(42, 
                       trama.getCodInstAdquir_DE42(), 
                       IsoType.ALPHA, 15);
        solic.setValue(43, 
                       trama.getDatosInstAdquir_DE43(), 
                       IsoType.ALPHA, 40);
        if(trama.getDatosProducto_DE48() != null){
            solic.setValue(48,
                           trama.getDatosProducto_DE48(), 
                           IsoType.LLLVAR, 0);
        }
        solic.setValue(49,
                       trama.getMoneda_DE49(), 
                       IsoType.NUMERIC, 3);
        trama.setDatosOriginales_DE90(DE90((Date)solic.getObjectValue(7)));        
        //INI ASOSA - 12/09/2014
        String trama90 = "";
        if (!getEntrada().getTipoRecaudacion().equals(Constantes.TRNS_ANU_RECARGA)) {        
            trama90 = (trama.getDatosOriginales_DE90() == null) ? "" : trama.getDatosOriginales_DE90();
            String nuevoTrace = getEntrada().getAuditoria();
            String origenTrace = getEntrada().getAuditoriaOrig();
            trama90 = trama90.replaceAll(nuevoTrace, origenTrace);
        }        
        //FIN ASOSA - 12/09/2014
        
        if(trama.getDatosOriginales_DE90() != null){
            solic.setValue(90,
                           //trama.getDatosOriginales_DE90(), 
                           trama90, //ASOSA - 12/09/2014
                           IsoType.NUMERIC, 42);
        }
        //INI ASOSA - 12/11/2014 - RECAR - NEX
        if(trama.getCadena_DE98() != null){
            solic.setValue(98,
                           trama.getCadena_DE98(), 
                           IsoType.ALPHA, 25);
        }
        //INI ASOSA - 12/11/2014 - RECAR - NEX
        if(trama.getCtaDesde_DE102() != null){
            solic.setValue(102,
                           trama.getCtaDesde_DE102(), 
                           IsoType.ALPHA, 28);
        }
        if(trama.getCtaHasta_DE103() != null){
            solic.setValue(103,
                           trama.getCtaHasta_DE103(), 
                           IsoType.ALPHA, 28);
        }
        //INI ASOSA - 11/11/2014 - RECAR - NEX
         if(trama.getRuteo_DE122() != null){
             solic.setValue(122,
                            trama.getRuteo_DE122(), 
                           IsoType.LLLVAR, 0);
         }
         if(trama.getDocIdentidad_DE123() != null){
             solic.setValue(123,
                            trama.getDocIdentidad_DE123(), 
                           IsoType.LLLVAR, 0);
         }
         //FIN ASOSA - 11/11/2014 - RECAR - NEX
        if(trama.getReservUsoPrivado_DE124() != null){
            solic.setValue(124,
                           trama.getReservUsoPrivado_DE124(), 
                          IsoType.LLLVAR, 0);
        }
        if(trama.getReservUsoPrivado_DE125() != null){
            solic.setValue(125,
                           trama.getReservUsoPrivado_DE125(), 
                          IsoType.LLLVAR, 0);
        }
        if(trama.getReservUsoPrivado_DE126() != null){
            solic.setValue(126,
                           trama.getReservUsoPrivado_DE126(), 
                          IsoType.LLLVAR, 0);
        }
        if(trama.getReservUsoPrivado_DE127() != null){
            solic.setValue(127,
                           trama.getReservUsoPrivado_DE127(), 
                          IsoType.LLLVAR, 0);
        }
        trama.setMac_DE128(DE128(trama));
        if(trama.getMac_DE128() != null){
            solic.setValue(128, 
                           trama.getMac_DE128(), 
                           IsoType.ALPHA, 16);
        }
        return solic;
    }

}
