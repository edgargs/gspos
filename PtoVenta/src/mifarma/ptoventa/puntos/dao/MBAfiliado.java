package mifarma.ptoventa.puntos.dao;

import java.util.HashMap;

import java.util.List;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.encuesta.dao.MapperEncuesta;
import mifarma.ptoventa.encuesta.modelo.BeanPreguntaEncuesta;
import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.reference.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBAfiliado implements DAOAfiliado {
    
    private static final Logger log = LoggerFactory.getLogger(MBAfiliado.class);

    private SqlSession sqlSession = null;
    private MapperAfiliado mapper = null;
    
    public MBAfiliado(){
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
        mapper = sqlSession.getMapper(MapperAfiliado.class);
    }
    
    @Override
    public BeanAfiliado getClienteFidelizado(String pNroDni) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cDniCliente_in", pNroDni);
        mapper.obtenerClienteAfiliado(mapParametros);
        log.info("bean afiliado "+mapParametros);
        List<BeanAfiliado> lst = (List<BeanAfiliado>)mapParametros.get("listado");
        if(lst.size()>0){
            return lst.get(0);    
        }else{
            return null;
        }
    }
}
