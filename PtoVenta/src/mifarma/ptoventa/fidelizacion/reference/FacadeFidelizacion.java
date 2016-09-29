package mifarma.ptoventa.fidelizacion.reference;

import java.awt.Frame;

import java.util.ArrayList;
import java.util.List;

import mifarma.ptoventa.fidelizacion.dao.DAOFidelizacion;
import mifarma.ptoventa.fidelizacion.dao.MBFidelizacion;
import mifarma.ptoventa.fidelizacion.modelo.BeanTipoDocIdentidad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeFidelizacion {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeFidelizacion.class);
    private DAOFidelizacion daoFidelizacion;
    private Frame myParentFrame;
    
    public FacadeFidelizacion() {
        super();
        daoFidelizacion = new MBFidelizacion();
    }
    
    public FacadeFidelizacion(Frame myParentFrame){
        super();
        daoFidelizacion = new MBFidelizacion();
        this.myParentFrame = myParentFrame;
    }
    
    public List obtenerListadoTipoDocumento(){
        List lista = null;
        try{
            daoFidelizacion.openConnection();
            lista = daoFidelizacion.getListaTipoDocumento();
            daoFidelizacion.commit();
            for(int i=0;i<lista.size();i++){
                BeanTipoDocIdentidad td = (BeanTipoDocIdentidad)lista.get(i);
                String[] aux = null;
                if(td.getMascara()!=null){
                    aux = td.getMascara().split("#");
                }
                List lstMascaras = new ArrayList();
                if(aux!=null){
                    for(int x = 0; x < aux.length; x++){
                        lstMascaras.add(aux[x]);
                    }
                }
                ((BeanTipoDocIdentidad)lista.get(i)).setLstMascaras(lstMascaras);
            }
        }catch(Exception ex){
            daoFidelizacion.rollback();
            log.error("",ex);            
            lista = new ArrayList();
        }
        return lista;
    }
    
    public String getCodigoTipoDocumentoAfiliado(String nroDocumentoIdentidad){
        String codTipoDocumento = "";
        try{
            daoFidelizacion.openConnection();
            codTipoDocumento = daoFidelizacion.getCodigoTipoDocumentoAfiliado(nroDocumentoIdentidad);
            daoFidelizacion.commit();
        }catch(Exception ex){
            daoFidelizacion.rollback();
            codTipoDocumento = "";
        }
        if(codTipoDocumento == null){
            codTipoDocumento = "";
        }
        return codTipoDocumento.trim();
    }
}
