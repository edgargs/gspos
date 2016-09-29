package mifarma.ptoventa.lectorHuella.dao;

import java.util.List;

import mifarma.ptoventa.lectorHuella.modelo.UsuarioFV;
import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAOLectorHuella extends DAOTransaccion {
    
    public List<UsuarioFV> obtenerUsuarioFV(String pCodGrupoCia, String pCodLocal, String pSecUsuLocal) throws Exception;
    
    public String registrarHuellaDactilar(String pCodGrupoCia, String pCodLocal, String pSecUsuLocal, byte[] pHuella, int posicionHuella) throws Exception;
    
    public int obtenerCantidadRepiteHuella() throws Exception;
    
    public String obtieneHuellasSolicitar()throws Exception;
}
