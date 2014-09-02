package com.gs.pos.configuracion.control;

import com.gs.pos.configuracion.domain.DAOConfiguracion;
import com.gs.pos.configuracion.domain.Usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadeConfiguracion {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeConfiguracion.class);
    
    private DAOConfiguracion dao;
    
    public FacadeConfiguracion() {
        super();
        dao = new DAOConfiguracion();
    }
    
    public boolean validaLogin(String pLogin, String pClave) throws Exception {
        Usuario usuario;
        try {
            dao.openConnection();
            usuario = dao.validarLogin(pLogin);
            dao.commit();
        } catch (Exception e) {
            log.error("",e);
            try {
                dao.rollback();
            } catch (Exception f) {
            }
            throw new Exception("Error de conexion.");
        }
        //1.Valida exista
        if(usuario == null){
            throw new Exception("Usuario no existe.");
        }
        //2.Valida estado        
        if(!usuario.getEstado().equals("A")){
            throw new Exception("Usuario no esta activo.");
        }
        //3.Valida clave
        if(!usuario.getClave().equals(pClave)){
            throw new Exception("La clave no es correcta.");
        }
        
        return true;
    }
}
