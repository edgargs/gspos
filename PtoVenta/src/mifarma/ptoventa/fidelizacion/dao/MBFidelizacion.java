package mifarma.ptoventa.fidelizacion.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mifarma.ptoventa.fidelizacion.modelo.BeanTipoDocIdentidad;
import mifarma.ptoventa.puntos.dao.DAOPuntos;
import mifarma.ptoventa.puntos.dao.MBPuntos;
import mifarma.ptoventa.puntos.dao.MapperPuntos;

import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.reference.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBFidelizacion implements DAOFidelizacion {
    
    private static final Logger log = LoggerFactory.getLogger(MBFidelizacion.class);

    private SqlSession sqlSession = null;
    private MapperFidelizacion mapper = null;
    
    public MBFidelizacion() {
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
        mapper = sqlSession.getMapper(MapperFidelizacion.class);
    }
    
    public List getListaTipoDocumento() throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapper.getListaTipoDocumento(mapParametros);
        log.info("OBTENIENDO LISTADO DE TIPO DE DOCUMENTO IDENTIDAD ");
        List<BeanTipoDocIdentidad> lst = (List<BeanTipoDocIdentidad>)mapParametros.get("listado");
        if(lst.size()>0){
            return lst;
        }else{
            return new ArrayList<BeanTipoDocIdentidad>();
        }
    }
    
    public String getCodigoTipoDocumentoAfiliado(String nroDocumentoIdentidad) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNroDocumento_in", nroDocumentoIdentidad);
        mapper.getCodigoTipoDocumentoAfiliado(mapParametros);
        return mapParametros.get("resultado").toString();
    }
    
}
