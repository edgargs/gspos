package mifarma.ptoventa.cliente.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cliente.modelo.BeanClienteJuridico;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MBCliente implements DAOCliente {
    
    private static final Logger log = LoggerFactory.getLogger(MBCliente.class);
    
    private SqlSession sqlSession = null;
    private MapperCliente mapper = null;
    private UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    private boolean isConexionRac = false;
    
    public MBCliente(){
        super();
    }
    
    public MBCliente(boolean isConexionRac){
        super();
        this.isConexionRac = isConexionRac;
    }
    
    @Override
    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    @Override
    public void rollback() {
        if (sqlSession != null) {
            sqlSession.rollback(true);
            sqlSession.close();
        }
    }

    @Override
    public void openConnection() {
        if(isConexionRac){
            sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
        }else{
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        }
        mapper = sqlSession.getMapper(MapperCliente.class);
    }
    
    /**
     * Realiza la busqueda de clientes
     * @author KMONCADA
     * @since 13.04.2016
     * @version 1.0
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pValorBusqueda
     * @param pTipoBusqueda
     * @return
     * @throws Exception
     */
    public List busquedaClientes(String pCodGrupoCia, String pCodLocal, String pValorBusqueda, String pTipoBusqueda)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cValorBusqueda_in", pValorBusqueda);
        mapParametros.put("cTipoBusqueda_in", pTipoBusqueda);
        mapper.busquedaClientes(mapParametros);
        List<BeanClienteJuridico> lst = (List<BeanClienteJuridico>)mapParametros.get("listado");
        if(lst.size()>0){
            return lst;
        }else{
            return new ArrayList<BeanClienteJuridico>();
        }
    }
    
    public void registrarCliente(String pCodGrupoCia, String pCodLocal, String pTipoDocumento, BeanClienteJuridico cliente)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("pCodLocal_in", pCodLocal);
        mapParametros.put("pTipoDocumento_in", pTipoDocumento);
        mapParametros.put("pNumDocumento_in", cliente.getNumDocumento());
        mapParametros.put("pRazonSocial_in", cliente.getRazonSocial());
        mapParametros.put("pDireccion_in", cliente.getDireccion());
        mapParametros.put("pIsAgenteRetencion_in", cliente.getAgenteRetencion());
        mapParametros.put("pIsAgentePercepcion_in", cliente.getAgentePercepcion());
        mapParametros.put("pClienteDigemid_in", cliente.getClienteDigemid());
        mapParametros.put("pUser_in", FarmaVariables.vNuSecUsu);
/*        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cCodNumera_in", FarmaConstants.COD_NUMERA_CLIENTE_LOCAL);
        mapParametros.put("cCodCliente_in", cliente.getCodCliente());
        mapParametros.put("cRazSoc_in", cliente.getRazonSocial());
        mapParametros.put("cTipDocIdent_in", pTipoDocumento);
        mapParametros.put("cNumDocIdent_in", cliente.getNumDocumento());
        mapParametros.put("cDirCliLocal_in", cliente.getDireccion());
        mapParametros.put("cUsuCreaCliLocal_in", FarmaVariables.vIdUsu);
        mapParametros.put("cAgenteRetencion_in", cliente.getAgenteRetencion());
        mapParametros.put("cAgentePercepcion_in", cliente.getAgentePercepcion());
*/
        mapper.registrarCliente(mapParametros);
        
    }
}