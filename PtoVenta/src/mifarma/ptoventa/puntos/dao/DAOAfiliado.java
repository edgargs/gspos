package mifarma.ptoventa.puntos.dao;

import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAOAfiliado extends DAOTransaccion {
    
    public BeanAfiliado getClienteFidelizado(String pNroDni) throws Exception;
}
