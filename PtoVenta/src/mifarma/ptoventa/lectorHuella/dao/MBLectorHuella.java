
package mifarma.ptoventa.lectorHuella.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.lectorHuella.modelo.UsuarioFV;
import mifarma.ptoventa.reference.MyBatisUtil;

import mifarma.ptoventa.reportes.modelo.ConcursoGigante.BeanResumenReporteGigante;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBLectorHuella implements DAOLectorHuella {
    
    private static final Logger log = LoggerFactory.getLogger(MBLectorHuella.class);

    private SqlSession sqlSession = null;
    private MapperLectorHuella mapper = null;
    
    public MBLectorHuella() {
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
        mapper = sqlSession.getMapper(MapperLectorHuella.class);
    }
    
    @Override
    public List<UsuarioFV> obtenerUsuarioFV(String pCodGrupoCia, String pCodLocal, String pSecUsuLocal) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("pCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("pCodLocal_in", pCodLocal);
        mapParametros.put("pSecUsu_in", pSecUsuLocal);
        mapper.obtenerUsuarioFV(mapParametros);
        List<UsuarioFV> lista = (List<UsuarioFV>)mapParametros.get("listado");
        return lista;
    }

    @Override
    public String registrarHuellaDactilar(String pCodGrupoCia, String pCodLocal, String pSecUsuLocal, byte[] pHuella, int posicionHuella) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("pCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("pCodLocal_in", pCodLocal);
        mapParametros.put("pSecUsu_in", pSecUsuLocal);
        mapParametros.put("pHuella_in", pHuella);
        mapParametros.put("pUsuMod_in", "SISTEMAS");
        mapParametros.put("pPosicionHuella_in", posicionHuella);
        mapper.grabarHuellaDactilar(mapParametros);
        String resultado = (String)mapParametros.get("respuesta");
        return resultado;
    }
    
    @Override
    public int obtenerCantidadRepiteHuella() throws Exception{
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapper.obtenerCantidadRepiteHuella(mapParametros);
        String resultado = (String)mapParametros.get("respuesta");
        int cantidad = Integer.parseInt(resultado);
        return cantidad;
    }
    
    @Override
    public String obtieneHuellasSolicitar() throws Exception {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapper.obtieneHuellasSolicitar(mapParametros);
        String resultado = (String)mapParametros.get("respuesta");
        return resultado;
    }

    
}
