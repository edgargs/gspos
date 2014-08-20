package mifarma.transf.daemon.dao;

import java.util.List;

import mifarma.transf.daemon.bean.LgtGuiaRem;
import mifarma.transf.daemon.bean.LgtNotaEsCab;
import mifarma.transf.daemon.bean.LgtNotaEsDet;
import mifarma.transf.daemon.util.BeanConexion;

public interface DAOPtoVenta extends DAOTransaccion {
    
    public void setConexion(BeanConexion conexion);

    public void saveLgtNotaEsCab(LgtNotaEsCab lgtNotaEsCab) throws Exception;

    public void saveLgtNotaEsDet(List<LgtNotaEsDet> lstNotaEsDet) throws Exception;

    public void saveLgtGuiaRem(List<LgtGuiaRem> lstGuiaRem) throws Exception;
}
