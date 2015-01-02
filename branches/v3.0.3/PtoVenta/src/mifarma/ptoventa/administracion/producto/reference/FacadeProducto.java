package mifarma.ptoventa.administracion.producto.reference;

import java.util.ArrayList;

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

    public String obtenerCodigoEPLPorProducto(String strCodigo) throws Exception {
        return daoProducto.obtenerCodigoEPLPorProducto(strCodigo);
    }

    public ArrayList obtenerProductosCodigoBarra(String pCodBarra) throws Exception {
        ArrayList lista = daoProducto.obtenerProductoCodigoBarra(pCodBarra);
        return lista;
    }
}
