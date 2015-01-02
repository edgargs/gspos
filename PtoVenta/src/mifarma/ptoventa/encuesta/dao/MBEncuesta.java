package mifarma.ptoventa.encuesta.dao;

import java.util.HashMap;
import java.util.List;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.encuesta.modelo.BeanEncuesta;
import mifarma.ptoventa.encuesta.modelo.BeanPreguntaEncuesta;
import mifarma.ptoventa.reference.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBEncuesta implements DAOEncuesta {

    private static final Logger log = LoggerFactory.getLogger(MBEncuesta.class);

    private SqlSession sqlSession = null;
    private MapperEncuesta mapper = null;

    public MBEncuesta() {
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
        mapper = sqlSession.getMapper(MapperEncuesta.class);
    }

    public List<BeanEncuesta> verificaEncuesta(String pCodprod) throws Exception {
        List<BeanEncuesta> lst = null;
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cCodProd_in", pCodprod);

        mapper.verificaEncuesta(mapParametros);

        lst = (List<BeanEncuesta>)mapParametros.get("listado");

        return lst;
    }

    @Override
    public List<BeanPreguntaEncuesta> obtenerPreguntas(int pCodigoEncueta) {
        List<BeanPreguntaEncuesta> lst = null;
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("nCodEncuesta_in", pCodigoEncueta);

        mapper.obtenerPreguntas(mapParametros);

        lst = (List<BeanPreguntaEncuesta>)mapParametros.get("listado");

        return lst;
    }

    public void grabaDetalleEncuesta(String pCodGrupoCia, String strCodCia, String strCodLocal, int cSecuencial,
                                     String pCodProd, int vencuesta, int vsecpregunta, String vrespuesta) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cSecuencial", cSecuencial);
        mapParametros.put("cCodProd_in", pCodProd);
        mapParametros.put("cCodEncuesta_in", vencuesta);
        mapParametros.put("cSecPregunta_in", vsecpregunta);
        mapParametros.put("cRespuesta", vrespuesta);

        log.info("grabaEncuesta : " + mapParametros);

        mapper.grabaDetalleEncuesta(mapParametros);


    }

    public int grabaCabEncuesta(String pCodGrupoCia, String strCodCia, String strCodLocal, String pUsuario,
                                String pCodProd, int vencuesta) {

        int vsecuencial;

        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cUsuId_in", pUsuario);
        mapParametros.put("cCodProd_in", pCodProd);
        mapParametros.put("cCodEncuesta_in", vencuesta);

        log.info("grabaEncuesta : " + mapParametros);

        mapper.grabarCabeceraEncuesta(mapParametros);


        return new Integer(mapParametros.get("vSecuencial").toString());
    }

}
