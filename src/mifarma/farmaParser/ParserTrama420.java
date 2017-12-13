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


public class ParserTrama420 extends FarmaParserTramas{

    public ParserTrama420(){
        super();
    }
 
    public ParserTrama420(EntradaBean entrada){
        super(entrada);
    }

    private TramaBean crearTrama420() throws Exception{
        String auditoria  = Funciones.formatoCeroIzq(6, getEntrada().getAuditoria());
        getEntrada().setMoneda(Constantes.COD_PTOVENTA_MON_SOL);
        Funciones.setPrefijo_ProductoServicioClaro(getEntrada());
        DateFormat df1 = new SimpleDateFormat(Constantes.FORMATO_YYYYMMDD_HHMMSS);
        Date fechaOrigen = df1.parse(getEntrada().getFechaOrig());

        TramaBean trama = new TramaBean();
        trama.setNroTarjeta_DE02(DE02());
        trama.setCodProceso_DE03(DE03());
        trama.setMontoTrans_DE04(getEntrada().getMonto());
        trama.setFhTrans_DE07(df.format(fechaOrigen));
        trama.setAuditoria_DE11(auditoria);
        trama.setFechaTransLocal_DE12(df.format(fechaOrigen));
        trama.setHoraTransLocal_DE13(df.format(fechaOrigen));
        trama.setFechaCaptura_DE17(df.format(fechaOrigen));
        trama.setCodIdAdquiriente_DE32(Constantes.COD_ID_ADQUIRIENTE);
        trama.setNumRecupRef_DE37(DE37());
        trama.setAutorizTrans_DE38(DE38());
        trama.setCodRespuesta_DE39(getEntrada().getMotivoExtorno());
        trama.setCodInstAdquir_DE42(DE42());
        trama.setMoneda_DE49(DE49());
        trama.setReservUsoPrivado_DE125(DE125(trama.getMoneda_DE49(), fechaOrigen));
        return trama;
    }

    public IsoMessage generarTramaExtorno0420() throws Exception{
        leerConfiguracion();
        TramaBean trama = crearTrama420();
        IsoMessage solic = mfact.newMessage(Constantes.MSG_BYTE_420);
        solic.setValue(2, 
                       trama.getNroTarjeta_DE02(), 
                       IsoType.LLVAR, 16);
        solic.setValue(3,
                       trama.getCodProceso_DE03(),
                       IsoType.NUMERIC, 6);
        solic.setValue(4,
                       new BigDecimal(trama.getMontoTrans_DE04()),
                       IsoType.AMOUNT, 0);
        solic.setValue(7,
                       Funciones.convertStringToDateFull(trama.getFhTrans_DE07()),
                       IsoType.DATE10, 0);
        solic.setValue(11,
                       trama.getAuditoria_DE11(),
                       IsoType.NUMERIC, 6);
        solic.setValue(12,
                       Funciones.convertStringToDateFull(trama.getFechaTransLocal_DE12()),
                       IsoType.TIME, 0);
        solic.setValue(13,
                       Funciones.convertStringToDateFull(trama.getHoraTransLocal_DE13()),
                       IsoType.DATE4, 0);
        if(trama.getFechaExpiracion_DE14() != null){
            solic.setValue(14,             //Fecha exp
                           Funciones.convertStringToDateFull(trama.getFechaExpiracion_DE14()),
                           IsoType.DATE_EXP, 0);
        }
        solic.setValue(17,
                       Funciones.convertStringToDateFull(trama.getFechaCaptura_DE17()),
                       IsoType.DATE4, 0);
        //El campo 18 es igual para toda las marcas
        solic.setValue(32,
                       trama.getCodIdAdquiriente_DE32(),
                       IsoType.LLVAR, 0);
        solic.setValue(37, 
                       trama.getNumRecupRef_DE37(), 
                       IsoType.ALPHA, 12);
        if(trama.getAutorizTrans_DE38() != null){
            solic.setValue(38, 
                           trama.getAutorizTrans_DE38(), 
                           IsoType.ALPHA, 6);
            trama.setAutorizTrans_DE38(solic.getField(38).toString());
        }
        solic.setValue(39, 
                       trama.getCodRespuesta_DE39(), 
                       IsoType.ALPHA, 2);
        solic.setValue(42, 
                       trama.getCodInstAdquir_DE42(), 
                       IsoType.ALPHA, 15);
        solic.setValue(49,
                       trama.getMoneda_DE49(), 
                       IsoType.NUMERIC, 3);
        trama.setDatosOriginales_DE90(DE90((Date)solic.getObjectValue(7)));
        if(trama.getDatosOriginales_DE90() != null){
            solic.setValue(90,
                           trama.getDatosOriginales_DE90(), 
                           IsoType.NUMERIC, 42);
        }
        if(trama.getReservUsoPrivado_DE125() != null){
            solic.setValue(125,
                           trama.getReservUsoPrivado_DE125(), 
                          IsoType.LLLVAR, 0);
        }
        return solic;
    }

}
