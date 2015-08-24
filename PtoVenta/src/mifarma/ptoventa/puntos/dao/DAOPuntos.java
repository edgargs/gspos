package mifarma.ptoventa.puntos.dao;

import java.util.List;

import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAOPuntos extends DAOTransaccion {
    
    public BeanAfiliado getClienteFidelizado(String pNroDni) throws Exception;
    
    public List getVoucherAfiliacionPtos(String pCodGrupoCia, String pCodLocal, String pNroDni, String nSecUsu) throws Exception;
    
    public void actualizarEstadoAfiliacion(String pNroTarjeta, String pNroDocumento, String pEstado) throws Exception;
    
    //public String evaluaTipoTarjeta(String pNroTarjeta, String pTipoTarjeta) throws Exception;
    
    public String restrigueAsociarTarjetasOrbis() throws Exception;
    
    public String isTarjetaOtroPrograma(String pNroTarjeta, boolean isIncluidoPtos) throws Exception;
}
