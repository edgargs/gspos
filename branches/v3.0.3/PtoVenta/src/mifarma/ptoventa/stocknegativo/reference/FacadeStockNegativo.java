package mifarma.ptoventa.stocknegativo.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.ptoventa.stocknegativo.dao.DaoStockNegativo;
import mifarma.ptoventa.stocknegativo.dao.FactoryStockNegativo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeStockNegativo {
    private static final Logger log = LoggerFactory.getLogger(FacadeStockNegativo.class);

    private DaoStockNegativo daoStockNegativo;

    public FacadeStockNegativo() {
        super();
        daoStockNegativo = FactoryStockNegativo.getDaoStockNegativo(FactoryStockNegativo.Tipo.MYBATIS);
    }

    public ArrayList<ArrayList<String>> listadoSolStockNeg(String numSolic, String estado, String solicitante,
                                                           String aprobador, String fechaIni, String fechaFin) {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado =
                    daoStockNegativo.listarSolicitudes(numSolic, estado, solicitante, aprobador, fechaIni, fechaFin);
        } catch (SQLException e) {
            log.error("", e);
            lstListado = null;
        }
        return lstListado;
    }

    public ArrayList<ArrayList<String>> listadoDetStockNeg(String numSolic) {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado = daoStockNegativo.listarDetSolicit(numSolic);
        } catch (SQLException e) {
            log.error("", e);
            lstListado = null;
        }
        return lstListado;
    }

    public String regularizar(String codProd, String codProdReg, String cant, String numSol) {
        String resultado = null;

        try {
            resultado = daoStockNegativo.regularizar(codProd, codProdReg, cant, numSol);
        } catch (SQLException e) {
            log.error("", e);
            resultado = "";
        }
        return resultado;
    }

    public ArrayList<ArrayList<String>> listarKardex(String codProd) {
        ArrayList<ArrayList<String>> lstListado = null;

        try {
            lstListado = daoStockNegativo.listarKardex(codProd);
        } catch (SQLException e) {
            log.error("", e);
            lstListado = null;
        }
        return lstListado;
    }
}
