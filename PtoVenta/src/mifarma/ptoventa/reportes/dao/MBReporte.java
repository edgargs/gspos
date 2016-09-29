package mifarma.ptoventa.reportes.dao;

import java.util.HashMap;
import java.util.List;

import mifarma.ptoventa.reference.MyBatisUtil;

import mifarma.ptoventa.reportes.modelo.ConcursoGigante.BeanPeriodoReporteGigante;

import mifarma.ptoventa.reportes.modelo.ConcursoGigante.BeanResumenReporteGigante;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBReporte implements DAOReportes {
    
    private static final Logger log = LoggerFactory.getLogger(MBReporte.class);

    private SqlSession sqlSession = null;
    private MapperReporte mapper = null;
    
    public MBReporte() {
        super();
    }

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
        mapper = sqlSession.getMapper(MapperReporte.class);
    }
    
    @Override
    public List obtenerPeriodoReporteGigantes(String pCodGrupoCia, String pCodLocal) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapper.obtenerPeriodoReporteGigante(mapParametros);
        List<BeanPeriodoReporteGigante> lista = (List<BeanPeriodoReporteGigante>)mapParametros.get("listado");
        return lista;
    }
    
    @Override
    public String obtenerInfoComisionGigante(String tipoComision)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("ctipomensaje_in", tipoComision);
        mapper.obtenerInfoComisionGigante(mapParametros);
        return mapParametros.get("listado").toString();
    }
    
    @Override
    public List obtenerResumenComisionGigante(String pCodGrupoCia, String pCodLocal, String pMes)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cMesId_in", pMes);
        mapper.obtenerResumenReporteGigante(mapParametros);
        List<BeanResumenReporteGigante> lista = (List<BeanResumenReporteGigante>)mapParametros.get("listado");
        return lista;
    }
    
    @Override
    public void procesarComisionGigante(String pCodGrupoCia, String pCodLocal, String pMes)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("an_Mes_Id", Integer.parseInt(pMes));
        mapper.procesarVentasComisionGigante(mapParametros);
    }
}
