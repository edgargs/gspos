package mifarma.ptoventa.puntos.reference;

import farmapuntos.bean.Afiliado;

import java.awt.Frame;

import java.util.List;

import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.encuesta.dao.DAOEncuesta;
import mifarma.ptoventa.encuesta.dao.MBEncuesta;
import mifarma.ptoventa.encuesta.reference.FacadeEncuesta;

import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.puntos.dao.DAOPuntos;

import mifarma.ptoventa.puntos.dao.MBPuntos;

import mifarma.ptoventa.puntos.modelo.BeanAfiliado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadePuntos {
    
    private static final Logger log = LoggerFactory.getLogger(FacadePuntos.class);
    private DAOPuntos daoPuntos;
    private Frame myParentFrame;
    
    public FacadePuntos() {
        super();
        daoPuntos = new MBPuntos();
    }
    
    public FacadePuntos(Frame myParentFrame) {
        super();
        daoPuntos = new MBPuntos();
        this.myParentFrame = myParentFrame;
    }
    
    public BeanAfiliado obtenerClienteFidelizado(String pNroDni){
        BeanAfiliado afiliado = new BeanAfiliado();
        
        try{
            daoPuntos.openConnection();
            afiliado = daoPuntos.getClienteFidelizado(pNroDni);
            //afiliado.setApellidos(UtilityFidelizacion.getApellidos(afiliado.getNombre()));  //ASOSA - 20/02/2015 - PTOSYAYAYAYA
            daoPuntos.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoPuntos.rollback();
        }finally{
            return afiliado;
        }
        
    }
    
    public boolean impresionVoucherAfiliacion(String pNroDni){
        boolean resultado = true;
        try{
            daoPuntos.openConnection();
            List lista = daoPuntos.getVoucherAfiliacionPtos(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNroDni, FarmaVariables.vNuSecUsu);
            if(lista!=null){
                new UtilityImpCompElectronico().impresionTermica(lista, null);
            }else{
                log.info("IMPRESION DE VOUCHER DE FIDELIZACION: ERROR AL OBTENER COMPROBANTE DE BD");
            }
            daoPuntos.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoPuntos.rollback();
            resultado = false;
        }finally{
            return resultado;
        }
    }
    
    public boolean actualizarEstadoAfiliacion(String pNroTarjeta, String pNroDocumento, String pEstado){
        boolean resultado = true;
        try{
            daoPuntos.openConnection();
            daoPuntos.actualizarEstadoAfiliacion(pNroTarjeta, pNroDocumento, pEstado);
            daoPuntos.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoPuntos.rollback();
            resultado = false;
        }finally{
            return resultado;
        }
    }
    /*
    public boolean evaluaTipoTarjeta(String pNroTarjeta, String pTipoTarjeta){
        boolean isPertene = false;
        try{
            daoPuntos.openConnection();
            String resultado = daoPuntos.evaluaTipoTarjeta(pNroTarjeta, pTipoTarjeta);
            if("S".equalsIgnoreCase(resultado)){
                isPertene = true;
            }
            daoPuntos.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoPuntos.rollback();
        }finally{
            return isPertene;
        }
    }
    */   
    public boolean isTarjetaOtroPrograma(String pNroTarjeta, boolean isIncluidoPtos){
        boolean isPertene = false;
        try{
            daoPuntos.openConnection();
            String resultado = daoPuntos.isTarjetaOtroPrograma(pNroTarjeta, isIncluidoPtos);
            if("S".equalsIgnoreCase(resultado)){
                isPertene = true;
            }
            daoPuntos.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoPuntos.rollback();
        }finally{
            return isPertene;
        }
    }
    
    public boolean restrigueAsociarTarjetasOrbis(){
        boolean isBloquea = false;
        try{
            daoPuntos.openConnection();
            String resultado = daoPuntos.restrigueAsociarTarjetasOrbis();
            if("S".equalsIgnoreCase(resultado)){
                isBloquea = true;
            }
            daoPuntos.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoPuntos.rollback();
        }finally{
            return isBloquea;
        }
    }
}
