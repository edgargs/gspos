package mifarma.ptoventa.lectorHuella.reference;

import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;

import mifarma.ptoventa.convenioBTLMF.dao.DAOConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.DAORACConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.dao.FactoryConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;

import mifarma.ptoventa.lectorHuella.dao.DAOLectorHuella;

import mifarma.ptoventa.lectorHuella.dao.FactoryLectorHuella;
import mifarma.ptoventa.lectorHuella.modelo.UsuarioFV;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadeLectorHuella {
    private static final Logger log = LoggerFactory.getLogger(FacadeLectorHuella.class);

    private DAOLectorHuella daoLectorHuella;
    
    public FacadeLectorHuella() {
        super();
    }
    
    public UsuarioFV obtenerUsuario(String pCodGrupoCia, String pCodLocal, String pSecUsuLocal)throws Exception{
        UsuarioFV usuario = null;
        String msjError = "";
        boolean isError = false;
        try{
            daoLectorHuella = FactoryLectorHuella.getDAOLectorHuella(TipoImplementacionDAO.MYBATIS);
            daoLectorHuella.openConnection();
            usuario = daoLectorHuella.obtenerUsuarioFV(pCodGrupoCia, pCodLocal, pSecUsuLocal).get(0);
            daoLectorHuella.commit();
        }catch(Exception ex){
            log.error("",ex);
            daoLectorHuella.rollback();
            usuario = null;
            isError = true;
            msjError = ex.getMessage();
        }
        if(isError){
            throw new Exception(msjError);
        }
        return usuario;
    }
    
    public List<UsuarioFV> obtenerListaUsuario(String pCodGrupoCia, String pCodLocal)throws Exception{
        List<UsuarioFV> lstUsuario = new ArrayList<UsuarioFV>();
        String msjError = "";
        boolean isError = false;
        try{
            daoLectorHuella = FactoryLectorHuella.getDAOLectorHuella(TipoImplementacionDAO.MYBATIS);
            daoLectorHuella.openConnection();
            lstUsuario = daoLectorHuella.obtenerUsuarioFV(pCodGrupoCia, pCodLocal, "*");
            daoLectorHuella.commit();
        }catch(Exception ex){
            log.error("",ex);
            daoLectorHuella.rollback();
            lstUsuario = new ArrayList<UsuarioFV>();
            isError = true;
            msjError = ex.getMessage();
        }
        if(isError){
            throw new Exception(msjError);
        }
        return lstUsuario;
    }
    
    public boolean registrarHuellaDactilar(String pCodGrupoCia, String pCodLocal, String pSecUsuLocal, byte[] pHuella, int posicionHuella)throws Exception{
        boolean isRegistrado = true;
        String msjError = "";
        try {
            daoLectorHuella = FactoryLectorHuella.getDAOLectorHuella(TipoImplementacionDAO.MYBATIS);
            daoLectorHuella.openConnection();
            daoLectorHuella.registrarHuellaDactilar(pCodGrupoCia, pCodLocal, pSecUsuLocal, pHuella, posicionHuella);
            daoLectorHuella.commit();
        } catch (Exception ex) {
            log.error("",ex);
            daoLectorHuella.rollback();
            isRegistrado = false;
            msjError = ex.getMessage();
        }
        if(!isRegistrado){
            throw new Exception(msjError);
        }
        return isRegistrado;
    }
    
    public int obtenerCantidadRepiteHuella() {
        int cantidad = 1;
        try{
            daoLectorHuella = FactoryLectorHuella.getDAOLectorHuella(TipoImplementacionDAO.MYBATIS);
            daoLectorHuella.openConnection();
            cantidad = daoLectorHuella.obtenerCantidadRepiteHuella();
            daoLectorHuella.commit();
        } catch (Exception ex) {
            log.error("",ex);
            daoLectorHuella.rollback();
            cantidad = 1;
        }
        return cantidad;
    }
    
    public String obtieneHuellasSolicitar() throws Exception {
        String resultado = "";
        try {
            daoLectorHuella = FactoryLectorHuella.getDAOLectorHuella(TipoImplementacionDAO.MYBATIS);
            daoLectorHuella.openConnection();
            resultado = daoLectorHuella.obtieneHuellasSolicitar();
            daoLectorHuella.commit();
        } catch (Exception ex) {
            log.error("", ex);
            daoLectorHuella.rollback();
        }
        return resultado;
    }
}
