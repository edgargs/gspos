package mifarma.ptoventa.psicotropicos.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.dao.MBRecetario;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MBPsicotropicos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LLEIVA      23.Sep.2013   Creación<br>
 * <br>
 * @author Luis Leiva Bazán<br>
 * @version 1.0<br>
 *
 */
public class MBPsicotropicos implements DAOPsicotropicos {
    private static final Logger log = LoggerFactory.getLogger(MBRecetario.class);
    private SqlSession sqlSession = null;
    private MapperPsicotropicos mapper = null;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }

    @Override
    public ArrayList obtenerListadoPsicotropicos(String fecha_inicial, String fecha_final, String cod_prod) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado = null;

        mapParametros.put("pCodGruCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("pCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("pCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("pFecInicial_in", fecha_inicial);
        mapParametros.put("pFecFinal_in", fecha_final);
        mapParametros.put("pCodProd_in", cod_prod);

        try {
            openConnection();
            mapper.obtenerListadoPsicotropicos(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstListado;
    }

    public String verificaProdPsicotropicos(String cod_prod) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado = null;
        mapParametros.put("pCodProd_in", cod_prod);

        String lstRetorno = "";
        try {
            openConnection();
            mapper.verificaProdPsicotropicos(mapParametros);
            lstRetorno = (String)mapParametros.get("vIndicador");
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstRetorno;
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperPsicotropicos.class);
    }

}
