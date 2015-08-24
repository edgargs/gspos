package mifarma.ptoventa.puntos.reference;

import farmapuntos.bean.Afiliado;

import java.awt.Frame;

import mifarma.ptoventa.encuesta.dao.DAOEncuesta;
import mifarma.ptoventa.encuesta.dao.MBEncuesta;
import mifarma.ptoventa.encuesta.reference.FacadeEncuesta;

import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.puntos.dao.DAOAfiliado;

import mifarma.ptoventa.puntos.dao.MBAfiliado;

import mifarma.ptoventa.puntos.modelo.BeanAfiliado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadeAfiliado {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeAfiliado.class);
    private DAOAfiliado daoAfiliado;
    private Frame myParentFrame;
    
    public FacadeAfiliado() {
        super();
        daoAfiliado = new MBAfiliado();
    }
    
    public FacadeAfiliado(Frame myParentFrame) {
        super();
        daoAfiliado = new MBAfiliado();
        this.myParentFrame = myParentFrame;
    }
    
    public BeanAfiliado obtenerClienteFidelizado(String pNroDni){
        BeanAfiliado afiliado = new BeanAfiliado();
        
        try{
            daoAfiliado.openConnection();
            afiliado = daoAfiliado.getClienteFidelizado(pNroDni);
            //afiliado.setApellidos(UtilityFidelizacion.getApellidos(afiliado.getNombre()));  //ASOSA - 20/02/2015 - PTOSYAYAYAYA
            daoAfiliado.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoAfiliado.rollback();
        }finally{
            return afiliado;
        }
        
    }
}
