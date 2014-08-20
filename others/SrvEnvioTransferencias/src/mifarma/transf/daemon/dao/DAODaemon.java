package mifarma.transf.daemon.dao;

import java.util.List;

import mifarma.transf.daemon.bean.BeanLocal;
import mifarma.transf.daemon.bean.BeanTransferencia;
import mifarma.transf.daemon.bean.LgtGuiaRem;
import mifarma.transf.daemon.bean.LgtNotaEsCab;
import mifarma.transf.daemon.bean.LgtNotaEsDet;
import mifarma.transf.daemon.util.BeanConexion;


public interface DAODaemon extends DAOTransaccion{
   
    public List<BeanLocal> getLocalesEnvio(String pCodGrupoCia, String pCodCia) throws Exception;
    
    public List<BeanTransferencia> getNotasPendientes(String pCodGrupoCia, String pCodCia, String pCodLocal) throws Exception;

    public LgtNotaEsCab getNotaEsCab(BeanTransferencia pLgtNotaEsCab) throws Exception;

    public List<LgtNotaEsDet> getNotaEsDet(BeanTransferencia pLgtNotaEsCab) throws Exception;

    public List<LgtGuiaRem> getGuiaRem(BeanTransferencia pLgtNotaEsCab) throws Exception;

    public void actualizaEnvioDestino(BeanTransferencia pLgtNotaEsCab) throws Exception;

    public void grabarTransfBV(BeanTransferencia beanTransferencia) throws Exception;

    public void setConexionMatriz(BeanConexion beanConexion) throws Exception;

    public String validaConexionLocal(BeanConexion beanConexion) throws Exception;
}
