package mifarma.ptoventa.puntos.dao;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.encuesta.dao.MapperEncuesta;
import mifarma.ptoventa.encuesta.modelo.BeanPreguntaEncuesta;
import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.reference.BeanImpresion;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBPuntos implements DAOPuntos {
    
    private static final Logger log = LoggerFactory.getLogger(MBPuntos.class);

    private SqlSession sqlSession = null;
    private MapperPuntos mapper = null;
    
    public MBPuntos(){
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
        mapper = sqlSession.getMapper(MapperPuntos.class);
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
    
    public List getVoucherAfiliacionPtos(String pCodGrupoCia, String pCodLocal, String pNroDni, String nSecUsu) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNumDoc_in", pNroDni);
        mapParametros.put("cSecUsu_in", nSecUsu);
        log.info("VOUCHER DE AFILIACION DE PUNTOS PTOVENTA.PTOVENTA_FIDELIZACION.F_IMPR_AFILIACION_PTOS "+mapParametros);
        mapper.getVoucherAfiliacionPtos(mapParametros);
        List<BeanImpresion> lstRetorno = (List<BeanImpresion>)mapParametros.get("listado");
        if(lstRetorno.size()>0){
            return lstRetorno;
        }else{
            return null;
        }
    }
    
    public void actualizarEstadoAfiliacion(String pNroTarjeta, String pNroDocumento, String pEstado) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNroTarjeta_in", pNroTarjeta);
        mapParametros.put("cNroDocumento_in", pNroDocumento);
        mapParametros.put("cEstadoTrsx_in", pEstado);
        mapper.actualizarEstadoAfiliacion(mapParametros);
    }
    /*
    public String evaluaTipoTarjeta(String pNroTarjeta, String pTipoTarjeta) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNumTarj_in", pNroTarjeta);
        mapParametros.put("cTipoTarjeta_in", pTipoTarjeta);
        mapper.evaluaTipoTarjeta(mapParametros);
        return mapParametros.get("listado").toString();
    }
    */
    
    public String restrigueAsociarTarjetasOrbis() throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapper.restrigueAsociarTarjetasOrbis(mapParametros);
        return mapParametros.get("listado").toString();
    }
    
    public String isTarjetaOtroPrograma(String pNroTarjeta, boolean isIncluidoPtos) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNumTarj_in", pNroTarjeta);
        if(isIncluidoPtos){
            mapParametros.put("TipoTarjeta_in", "S");
        }else{
            mapParametros.put("TipoTarjeta_in", "N");
        }
        mapper.isTarjetaOtroPrograma(mapParametros);
        return mapParametros.get("listado").toString();
    }
}
