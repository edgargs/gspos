package mifarma.farmaParser;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;

import java.math.BigDecimal;

import java.util.Date;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.TramaBean;

import mifarma.farmaUtil.Constantes;
import mifarma.farmaUtil.Funciones;


public class ParserTrama200 extends FarmaParserTramas{

    public ParserTrama200(){
        super();
    }

    public ParserTrama200(EntradaBean entrada){
        super(entrada);
    }

    private TramaBean crearTrama200() throws Exception{
         Date hoy = new Date();
         String fechaExp;
         String auditoria  = Funciones.formatoCeroIzq(6,getEntrada().getAuditoria());
         getEntrada().setMoneda(Constantes.COD_PTOVENTA_MON_SOL);
         getEntrada().setMonto(getEntrada().getMonto().replaceAll(",", ""));
         Funciones.setPrefijo_ProductoServicioClaro(getEntrada());

         TramaBean trama = new TramaBean();
         trama.setNroTarjeta_DE02(DE02());
         trama.setCodProceso_DE03(DE03());
         trama.setMontoTrans_DE04(getEntrada().getMonto());
         trama.setAuditoria_DE11(auditoria);
         trama.setFechaTransLocal_DE12(DE12(hoy));
         trama.setHoraTransLocal_DE13(DE13(hoy));
         fechaExp = DE14(df.format(hoy));
         trama.setFechaExpiracion_DE14(fechaExp);
         trama.setFechaCaptura_DE17(df.format(hoy));
         trama.setTipoNegocio_DE18(DE18());
         trama.setModoIngrDatos_DE22(DE22());
         trama.setTipoPtoServicio_DE25(Constantes.COD_TIPO_PTO_SERVICIO);
         trama.setCodIdAdquiriente_DE32(Constantes.COD_ID_ADQUIRIENTE);
         trama.setCodRedReenvio_DE33(Constantes.COD_ID_ADQUIRIENTE);
         trama.setPista2_DE35(DE35(fechaExp, hoy));
         trama.setNumRecupRef_DE37(String.format("%1$-12s", auditoria));
         trama.setAutorizTrans_DE38(DE38());
         trama.setIdTerminal_DE41(DE41());
         trama.setCodInstAdquir_DE42(DE42());
         trama.setDatosInstAdquir_DE43(DE43());
         trama.setDatosProducto_DE48(DE48());
         trama.setMoneda_DE49(DE49());
         trama.setPin_DE52(DE52());
         trama.setCadena_DE98(DE98());  //ASOSA - 28/10/2014 - RECAR - NEX
         trama.setCtaDesde_DE102(DE102());
         trama.setCtaHasta_DE103(DE103());
         trama.setDatosAdicionales_DE112(DE112());
         
         trama.setRuteo_DE122(DE122());  //ASOSA - 28/10/2014 - RECAR - NEX
         trama.setDocIdentidad_DE123(DE123());  //ASOSA - 28/10/2014 - RECAR - NEX
         
         trama.setReservUsoPrivado_DE124(DE124());
         trama.setReservUsoPrivado_DE125(DE125(trama.getMoneda_DE49(), hoy));
         trama.setReservUsoPrivado_DE126(DE126());
         trama.setReservUsoPrivado_DE127(DE127());
         trama.setMac_DE128(DE128(trama));

         return trama;
     }

    public IsoMessage generarTramaEnvio200() throws Exception{
        leerConfiguracion();
        TramaBean trama = crearTrama200();
        IsoMessage solic = mfact.newMessage(Constantes.MSG_BYTE_200);
        if(trama.getNroTarjeta_DE02() != null){
            solic.setValue(2, 
                          trama.getNroTarjeta_DE02(), 
                          IsoType.LLVAR, 16);
        }
       solic.setValue(3, 
                      trama.getCodProceso_DE03(), 
                      IsoType.NUMERIC, 6);
       trama.setCodProceso_DE03(solic.getField(3).toString());
       solic.setValue(4, 
                      new BigDecimal(trama.getMontoTrans_DE04()),
                      IsoType.AMOUNT, 0);
       trama.setMontoTrans_DE04(solic.getField(4).toString());
       //El campo 7 se carga en automatico
       solic.setValue(11,
                      trama.getAuditoria_DE11(),
                      IsoType.NUMERIC, 6);
       trama.setAuditoria_DE11(solic.getField(11).toString());
        if(trama.getFechaTransLocal_DE12() != null){
           solic.setValue(12,
                          trama.getFechaTransLocal_DE12(),
                          IsoType.TIME, 0);
        }
        if(trama.getHoraTransLocal_DE13() != null){
           solic.setValue(13,
                          trama.getHoraTransLocal_DE13(),
                          IsoType.DATE4, 0);
        }
       if(trama.getFechaExpiracion_DE14() != null){
           solic.setValue(14,
                          Funciones.convertStringToDateFull(trama.getFechaExpiracion_DE14()),
                          IsoType.DATE_EXP, 0);   
       }
       solic.setValue(17,
                      Funciones.convertStringToDateFull(trama.getFechaCaptura_DE17()),
                      IsoType.DATE4, 0);
       //El campo 18 es igual para toda las marcas
        if(trama.getTipoNegocio_DE18() != null){
            solic.setValue(18,
                          trama.getTipoNegocio_DE18(),
                          IsoType.NUMERIC, 4);
        }
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
                      IsoType.NUMERIC, 12);
       if(trama.getAutorizTrans_DE38() != null){
           solic.setValue(38, 
                          trama.getAutorizTrans_DE38(), 
                          IsoType.ALPHA, 6);
           trama.setAutorizTrans_DE38(solic.getField(38).toString());
        }       
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
       if(trama.getPin_DE52() != null){
           solic.setValue(52,
                          trama.getPin_DE52(), 
                          IsoType.ALPHA, 16);
       }
       trama.setDatosOriginales_DE90(DE90((Date)solic.getObjectValue(7)));
       if(trama.getDatosOriginales_DE90() != null){
           solic.setValue(90,
                          trama.getDatosOriginales_DE90(), 
                          IsoType.NUMERIC, 42);
       }
       //INI ASOSA - 12/11/2014 - RECAR - NEX
        if(trama.getCadena_DE98() != null){
            solic.setValue(98,
                           trama.getCadena_DE98(), 
                           IsoType.ALPHA, 25);
        }
        //FIN ASOSA - 12/11/2014 - RECAR - NEX
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
       if(trama.getDatosAdicionales_DE112() != null){
           solic.setValue(112,
                          trama.getDatosAdicionales_DE112(), 
                          IsoType.LLLVAR, 0);
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

