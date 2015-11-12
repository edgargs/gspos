package mifarma.ptoventa.puntos.dao;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.encuesta.dao.MapperEncuesta;
import mifarma.ptoventa.encuesta.modelo.BeanPreguntaEncuesta;
import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.puntos.modelo.BeanListaProductos;
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
    
    public List getListaProductoQuote(String pCodGrupoCia, String pCodLocal, String pNumPedVta, boolean isIncluyeBonificado, boolean devuelveMapper, boolean isAnulaPedido) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNumPedVta_in", pNumPedVta);
        if(isIncluyeBonificado){
            mapParametros.put("cConBonificado_in", "S");
        }else{
            mapParametros.put("cConBonificado_in", "N");
        }
        if(devuelveMapper){
            mapParametros.put("cFlag_in", "S");
        }else{
            mapParametros.put("cFlag_in", "N");
        }
        if(isAnulaPedido){
            mapParametros.put("cIndAnulaPed_in", "S");
        }else{
            mapParametros.put("cIndAnulaPed_in", "N");
        }
        log.info("PTOVENTA.FARMA_PUNTOS.F_CUR_V_LISTA_PRODUCTO () "+mapParametros);
        mapper.getListaProductoQuote(mapParametros);
        List<BeanListaProductos> lstRetorno = (List<BeanListaProductos>)mapParametros.get("listado");
        if(lstRetorno.size()>0){
            return lstRetorno;
        }else{
            return null;
        }
    }
    
    public String isSolicitaClaveQuimicoBloqueoTarjeta() throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapper.isSolicitaClaveQuimicoBloqueoTarjeta(mapParametros);
        return mapParametros.get("resultado").toString();
    }
    
    public void bloqueoTarjeta(String pNroTarjeta, String pLoginUsu) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNroTarjeta_in", pNroTarjeta);
        mapParametros.put("cLoginUsu_in", pLoginUsu);
        mapper.bloqueoTarjeta(mapParametros);
        
    }
    
    public void registroEntregaTarjetaPtos(String pCodGrupoCia, String pCodLocal, String pNumTarjeta, String pNumDocumento, String pUsuCrea)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNumTarjeta_in", pNumTarjeta);
        mapParametros.put("cNumDocumento_in", pNumDocumento);
        mapParametros.put("cUsuCrea_in", pUsuCrea);
        mapper.registroEntregaTarjetaPtos(mapParametros);
    }
    
    public String getMsjTarjBloqueaRedimir() throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapper.getMsjTarjBloqueaRedimir(mapParametros);
        return mapParametros.get("resultado").toString();
    }
    
    public String validaClienteBloqueadoRedime(String cNumDocCliente) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cNumDocumento_in", cNumDocCliente);
        mapper.validaClienteBloqueadoRedime(mapParametros);
        return mapParametros.get("resultado").toString();
    }
    
    @Override
    public String validacionDigitaDNI()throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapper.validacionDigitaDNI(mapParametros);
        return mapParametros.get("resultado").toString();
    }
    
}
