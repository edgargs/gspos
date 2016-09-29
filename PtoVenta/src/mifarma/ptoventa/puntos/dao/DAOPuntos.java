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
    
    public List getListaProductoQuote(String pCodGrupoCia, String pCodLocal, String pNumPedVta, boolean isIncluyeBonificado, boolean devuelveMapper, boolean isAnulaPedido) throws Exception;
    
    public String isSolicitaClaveQuimicoBloqueoTarjeta() throws Exception;
    
    public void bloqueoTarjeta(String pNroTarjeta, String pLoginUsu) throws Exception;
    
    public void registroEntregaTarjetaPtos(String pCodGrupoCia, String pCodLocal, String pNumTarjeta, String pNumDocumento, String pUsuCrea)throws Exception;
    
    public String getMsjTarjBloqueaRedimir() throws Exception;
    
    public String validaClienteBloqueadoRedime(String cNumDocCliente) throws Exception;
    
    public String validacionDigitaDNI()throws Exception;
}
