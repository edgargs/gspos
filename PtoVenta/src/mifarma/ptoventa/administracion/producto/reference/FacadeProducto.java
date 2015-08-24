package mifarma.ptoventa.administracion.producto.reference;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.administracion.producto.dao.DAOProducto;
import mifarma.ptoventa.administracion.producto.dao.FactoryProducto;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeProducto {
    private static final Logger log = LoggerFactory.getLogger(FacadeProducto.class);
    private DAOProducto daoProducto;

    public FacadeProducto() {
        super();
        daoProducto = FactoryProducto.getDAOCaja(TipoImplementacionDAO.MYBATIS);
    }

    public ArrayList obtenerProductosPorDescripcion(String vDescripcion) throws Exception {
        ArrayList lista = daoProducto.obtenerProductosPorDescripcion(vDescripcion);
        return lista;
    }

    public String obtenerCodigoEPLPorProducto(JDialog pJDialog, String strCodigo) throws Exception {
        String respuesta = "";
        try{
            daoProducto.openConnection();
            respuesta = daoProducto.obtenerCodigoEPLPorProducto(strCodigo);
            daoProducto.commit();
        }catch(Exception ex){
            daoProducto.rollback();
            log.error("",ex);
            FarmaUtility.showMessage(pJDialog, "Error obteniendo codigo EPL de producto", null);
        }
        return respuesta;
    }

    public ArrayList obtenerProductosCodigoBarra(String pCodBarra) throws Exception {
        ArrayList lista = daoProducto.obtenerProductoCodigoBarra(pCodBarra);
        return lista;
    }
}
