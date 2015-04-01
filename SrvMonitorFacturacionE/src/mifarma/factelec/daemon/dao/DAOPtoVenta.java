package mifarma.factelec.daemon.dao;

import mifarma.factelec.daemon.bean.MonVtaCompPagoE;
import mifarma.factelec.daemon.util.BeanConexion;

public interface DAOPtoVenta extends DAOTransaccion {
    
    public void setConexion(BeanConexion conexion);

    public void saveMonVtaCompPagoE(MonVtaCompPagoE monVtaCompPagoE) throws Exception;
}
