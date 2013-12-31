package mifarma.ptoventa.recaudacion.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.TimerTask;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.TimerRecarga;

import mifarma.ptoventa.caja.reference.VariablesVirtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : TimerRecaudacion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * GFonseca      09.07.2013   Creación<br>
 * <br>
 * @author Gilder Fonseca S.<br>
 * @version 1.0<br>
 * 
 */
public class TimerRecaudacion extends TimerTask{
    
    private static final Logger log = LoggerFactory.getLogger(TimerRecaudacion.class);
    String estTsscRecau="";
    int cant = 1;
    String indicador = ConstantsRecaudacion.ESTADO_INICIO_TAREA; 
    int cantidadIntentos = 0;    
    Long codigoTrssc = null;
    String modoRecau = "";
    FacadeRecaudacion  facadeRecaudacion = new FacadeRecaudacion();

    public void run() {
        log.info("intento de respuesta de Recaudacion nro: " + cant);
        
        if (cant++ > cantidadIntentos) {
            log.info("termino el timer de intento de obtener la respuesta de recaudacion");
            indicador = ConstantsRecaudacion.ESTADO_FIN_TAREA;
            cancel();
        }try{            
            estTsscRecau = facadeRecaudacion.obtenerEstTrsscReacudacion(codigoTrssc, modoRecau);            
            if ( !estTsscRecau.equals("") &&  ConstantsRecaudacion.ESTADO_SIX_TERMINADO.equals(estTsscRecau)) {              
                ConstantsRecaudacion.vCOD_ESTADO_TRSSC_RECAU = estTsscRecau;
                log.info("Se obtuvo respuesta de transaccion de Recaudacion " + estTsscRecau);                   
                indicador = ConstantsRecaudacion.ESTADO_RESPUESTA_DISPONIBLE;
                cancel();
            }        
        }catch (Exception e) {
            log.error("",e);
        }          
    }

    public void setCantidadIntentos(int cantidadIntentos) {
        this.cantidadIntentos = cantidadIntentos;
    }

    public void setCodigoTrssc(Long codigoTrssc) {
        this.codigoTrssc = codigoTrssc;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setModoRecau(String modoRecau) {
        this.modoRecau = modoRecau;
    }

    public String getModoRecau() {
        return modoRecau;
    }
}
