package mifarma.factelec.daemon.dao;

import java.util.List;

import mifarma.factelec.daemon.bean.BeanLocal;
import mifarma.factelec.daemon.bean.MonVtaCompPagoE;
import mifarma.factelec.daemon.util.BeanConexion;


public interface DAODaemon extends DAOTransaccion{
   
    public List<MonVtaCompPagoE> getDocumentosPendientes(String pCodGrupoCia, String pCodCia, int pCantidad) throws Exception;
    
    public void actualizaDocumentoE(MonVtaCompPagoE pDocumento) throws Exception;

    public void setConexionMatriz(BeanConexion beanConexion) throws Exception;

    public String getRucCia(String pCodGrupoCia, String pCodCia) throws Exception;

    public List<BeanLocal> getLocales(String pCodGrupoCia, String pCodCia, int pCantidad) throws Exception;

    public List<MonVtaCompPagoE> getDocsPendientes(String pCodGrupoCia, String pCodCia, String pCodLocal,int pCantidad) throws Exception;
    
    public void updateMonVtaCompPagoEPend(String pCodGrupoCia, String pCodCia, String pCodLocal) throws Exception;
}
