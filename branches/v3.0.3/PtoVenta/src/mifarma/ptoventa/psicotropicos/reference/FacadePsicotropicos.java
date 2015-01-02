package mifarma.ptoventa.psicotropicos.reference;

import java.util.ArrayList;

import mifarma.ptoventa.psicotropicos.dao.DAOPsicotropicos;
import mifarma.ptoventa.psicotropicos.dao.FactoryPsicotropicos;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadePsicotropicos {
    private static final Logger log = LoggerFactory.getLogger(FacadeRecetario.class);
    private DAOPsicotropicos dao;

    public FacadePsicotropicos() {
        super();
        dao = FactoryPsicotropicos.getDAOPsicotropicos(FactoryPsicotropicos.Tipo.MYBATIS);
    }

    public ArrayList<ArrayList<String>> listaPsicotropicos(String fecha_inicial, String fecha_final, String cod_prod) {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            lstListado = dao.obtenerListadoPsicotropicos(fecha_inicial, fecha_final, cod_prod);
        } catch (Exception e) {
            log.error("", e);
        }
        return lstListado;
    }

    public boolean verificaProdPsicotropicos(String cod_prod) {
        try {
            String temp = dao.verificaProdPsicotropicos(cod_prod);
            if ("V".equalsIgnoreCase(temp))
                return true;
            else
                return false;
        } catch (Exception e) {
            log.debug("", e);
            return false;
        }
    }
}
