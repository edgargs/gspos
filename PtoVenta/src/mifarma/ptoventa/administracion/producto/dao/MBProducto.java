package mifarma.ptoventa.administracion.producto.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaVariables;

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
 * Nombre de la Aplicación : MBProducto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CVILCA      01.10.2013   Creación<br>
 * <br>
 * @author Christian Vilca Quiros<br>
 * @version 1.0<br>
 *
 */
public class MBProducto implements DAOProducto {

    private static final Logger log = LoggerFactory.getLogger(MBProducto.class);

    private SqlSession sqlSession;
    private MapperProducto mapper;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    @Override
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    @Override
    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperProducto.class);
    }

    /**
     * Obtiene los datos básicos de los productos por descripción.
     * @author CVILCA
     * @since 01.10.2013
     */
    public ArrayList obtenerProductosPorDescripcion(String strDescripcion) throws SQLException {
        ArrayList lista = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("vDescripcion", strDescripcion);
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperProducto.class);
            mapper.obtenerProductosPorDescripcion(mapParametros);
            List<BeanResultado> listaResultado = (List<BeanResultado>)mapParametros.get("listado");
            lista = utilityPtoVenta.parsearResultadoMatriz(listaResultado);
        } catch (Exception e) {
            log.error("", e);
            lista = null;
        } finally {
            sqlSession.close();
        }
        return lista;
    }

    /**
     * Obtiene el código EPL para la impresión del sticker de un producto.
     * @author CVILCA
     * @since 02.10.2013
     */
    public String obtenerCodigoEPLPorProducto(String strCodigo) throws Exception {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cCodProd", strCodigo);
        mapper = sqlSession.getMapper(MapperProducto.class);
        mapper.obtenerCodigoEPLPorProducto(mapParametros);
            
        return mapParametros.get("epl").toString();
    }

    @Override
    public ArrayList obtenerProductoCodigoBarra(String pCodBarra) {
        ArrayList lista = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cCodBarra", pCodBarra);
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperProducto.class);
            mapper.obtenerProductoCodigobarra(mapParametros);
            List<BeanResultado> listaResultado = (List<BeanResultado>)mapParametros.get("listado");
            lista = utilityPtoVenta.parsearResultadoMatriz(listaResultado);
        } catch (Exception e) {
            log.error("", e);
            lista = null;
        } finally {
            sqlSession.close();
        }
        return lista;
    }
    
    /**
     * Obtiene cantidad de impresiones antes de mostrar mensaje de Confirmacion.
     * @return String
     * @author Desarrollo5
     * @since 22.09.2015
     */
    public String obtenerCantidadImpresiones() throws SQLException {
        String cantidadImpresiones="0";
        //ArrayList lista = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperProducto.class);
            mapper.obtenerCantidadImpresiones(mapParametros);
            //List<BeanResultado> listaResultado = (List<BeanResultado>)mapParametros.get("listado");
            cantidadImpresiones = mapParametros.get("epl").toString();
            //lista = utilityPtoVenta.parsearResultadoMatriz(listaResultado);
        } catch (Exception e) {
            log.error("", e);
            //lista = null;
        } finally {
            sqlSession.close();
        }
        return cantidadImpresiones;
    }
}
