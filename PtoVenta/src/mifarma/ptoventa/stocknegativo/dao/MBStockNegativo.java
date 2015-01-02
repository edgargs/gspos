package mifarma.ptoventa.stocknegativo.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBStockNegativo implements DaoStockNegativo {
    private static final Logger log = LoggerFactory.getLogger(MBStockNegativo.class);
    private SqlSession sqlSession = null;
    private MapperStockNegativo mapper = null;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    public MBStockNegativo() {
        super();
    }

    public ArrayList<ArrayList<String>> listarSolicitudes(String numSolic, String estado, String solicitante,
                                                          String aprobador, String fechaIni, String fechaFin) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;

        mapParametros.put("cnumsolic_in", numSolic);
        mapParametros.put("cestado_in", estado);
        mapParametros.put("csolicitante_in", solicitante);
        mapParametros.put("caprobador_in", aprobador);
        mapParametros.put("cfechaini_in", fechaIni);
        mapParametros.put("cfechafin_in", fechaFin);

        openConnection();
        try {
            mapper.getListarSolicitud(mapParametros);
        } finally {
            sqlSession.close();
        }

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    public ArrayList<ArrayList<String>> listarDetSolicit(String numSolic) throws SQLException {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;

        mapParametros.put("cnumsolic_in", numSolic);

        openConnection();
        try {
            mapper.listarDetSolicit(mapParametros);
        } finally {
            sqlSession.close();
        }

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    public String regularizar(String codProd, String codProdReg, String cant, String numSol) throws SQLException {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        String resultado;

        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cCodProd_in", codProd);
        mapParametros.put("cCodProdAnul_in", codProdReg);
        mapParametros.put("cNeoCant_in", cant);
        mapParametros.put("cCodSol_in", numSol);
        mapParametros.put("cUsu_in", FarmaVariables.vIdUsu);

        openConnection();
        try {
            mapper.regularizar(mapParametros);
        } finally {
            sqlSession.close();
        }

        resultado = (String)mapParametros.get("resultado");
        return resultado;
    }

    public ArrayList<ArrayList<String>> listarKardex(String codProd) throws SQLException {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;

        mapParametros.put("cCodProd", codProd);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);

        openConnection();
        try {
            mapper.listarKardex(mapParametros);
        } finally {
            sqlSession.close();
        }

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    /********************************************************************************/
    /*                                                                              */

    /********************************************************************************/
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperStockNegativo.class);
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }
}
