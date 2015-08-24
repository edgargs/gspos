package mifarma.ptoventa.lealtad.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recaudacion.dao.MapperRecaudacionTrsscSix;
import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author ERIOS
 * @since 05.02.2015
 */
public class MBLealtad implements DAOLealtad{
    
    private static final Logger log = LoggerFactory.getLogger(MBLealtad.class);
    
    private SqlSession sqlSession = null;
    private MapperLealtad mapper = null;
    
    public MBLealtad() {
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
        mapper = sqlSession.getMapper(MapperLealtad.class);
    }

    @Override
    public int verificaAcumulaX1(String pCodGrupoCia, String strCodCia, String strCodLocal, String pCodProd) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cCodProd_in", pCodProd);

        mapper.verificaAcumulaX1(mapParametros);

        return new Integer(mapParametros.get("nRetorno").toString());
    }


    @Override
    public List<BeanResultado> listaAcumulaX1(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                                       String pCodProd)  throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cCodProd_in", pCodProd);
        
        mapper.listaAcumulaX1(mapParametros);
        
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        return lstRetorno;
    }

    @Override
    public List voucherAcumulaX1(String pCodGrupoCia, String strCodCia, String strCodLocal, String pCodCamp, String pDniCli, String pCodProd) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(strCodCia);
        parametros.add(strCodLocal);
        parametros.add(pDniCli);
        parametros.add(pCodCamp);
        parametros.add(pCodProd);
        log.debug("FARMA_LEALTAD.VOUCHER_ACUMULA_X1(?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_LEALTAD.VOUCHER_ACUMULA_X1(?,?,?,?,?,?)",
                                                               parametros);        
    }
    
    public String registrarInscripcionX1(String pDniCli, String pCodMatrizAcu, String vIdUsu){
        String vRetorno="";
        SqlSession sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
        Map<String, Object> mapParametros = new HashMap<String, Object>();

        mapParametros.put("vDniCli_in", pDniCli);
        mapParametros.put("vCodMatrizAcu_in", pCodMatrizAcu);
        mapParametros.put("vIdUsu_in", vIdUsu);
        
        try {
            
            MapperLealtadMatriz mapper = sqlSession.getMapper(MapperLealtadMatriz.class);
            mapper.registrarInscripcionX1(mapParametros);
            
            sqlSession.commit(true);
            vRetorno = "1";
        } catch (Exception e) {
            sqlSession.rollback(true);
            log.error("", e);
            vRetorno = e.getMessage();
        } finally {
            sqlSession.close();
        }
        return vRetorno;
    }

    @Override
    public String indImpresionVoucherX1() {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();

        mapper.indImpresionVoucherX1(mapParametros);

        return mapParametros.get("vRetorno").toString();
    }

    @Override
    public String obtenerParametrosVenta(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(strCodCia);
        parametros.add(strCodLocal);
        parametros.add(pNumPedVta);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_LEALTAD.GET_PARAMETROS_VENTA(?,?,?,?)", parametros);
    }

    @Override
    public void actualizarPedido(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta,
                                 String pIdTransaccion, String pNumAutorizacion, String pIdUsu) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(strCodCia);
        parametros.add(strCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIdTransaccion);
        parametros.add(pNumAutorizacion);
        parametros.add(pIdUsu);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_LEALTAD.GET_ACTUALIZAR_VENTA(?,?,?,?,?,?,?)", parametros, false);        
    }

    @Override
    public void eliminaProdBonificacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(strCodCia);
        parametros.add(strCodLocal);
        parametros.add(pNumPedVta);

        FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_LEALTAD.GET_ELIMINA_BONIFICA(?,?,?,?)", parametros, false);                                                                  
    }

    @Override
    public void descartarPedido(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(strCodCia);
        parametros.add(strCodLocal);
        parametros.add(pNumPedVta);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_LEALTAD.GET_DESCARTAR_PEDIDO(?,?,?,?)", parametros, false);
    }

    @Override
    public String getSaldoPuntos(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cNumPedVta_in", pNumPedVta);
        
        mapper.getSaldoPuntos(mapParametros);
        
        return mapParametros.get("vRetorno").toString();
    }
    
   

    @Override
    public Map getPuntosMaximo(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception{
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(strCodLocal);
        parametros.add(pNumPedVta);
        
        Map executeSQLStoredProcedureMap = FarmaDBUtility.executeSQLStoredProcedureMap("FARMA_PUNTOS.F_LST_MONTOS_REDENCION(?, ?, ?)", parametros);
        return executeSQLStoredProcedureMap;
    }
    
    @Override
    public String getIndicadoresPuntos(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cNumPedVta_in", pNumPedVta);
        
        mapper.getIndicadoresPuntos(mapParametros);
        
        return mapParametros.get("vRetorno").toString();
    }

    @Override
    public String verificaUsoNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta, String pTipoBusqueda) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cNumPedVta_in", pNumPedVta);
        mapParametros.put("cTipoBusqueda_in",pTipoBusqueda);
        
        mapper.verificaUsoNCR(mapParametros);
        
        return mapParametros.get("vRetorno").toString();
    }

    @Override
    public String verificaFechaNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta,
                                   String pFechaNCR) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cNumPedVta_in", pNumPedVta);
        mapParametros.put("cFechaNCR_in", pFechaNCR);
        
        mapper.verificaFechaNCR(mapParametros);
        
        return mapParametros.get("vRetorno").toString();
    }

    @Override
    public String verificaCreditoNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta,
                                     String pFechaNCR) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cNumPedVta_in", pNumPedVta);
        mapParametros.put("cFechaNCR_in", pFechaNCR);
        
        mapper.verificaCreditoNCR(mapParametros);
        
        return mapParametros.get("vRetorno").toString();
    }

    @Override
    public String getMontoNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVtaNCR) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodCia_in", strCodCia);
        mapParametros.put("cCodLocal_in", strCodLocal);
        mapParametros.put("cNumPedVta_in", pNumPedVtaNCR);
        
        mapper.getMontoNCR(mapParametros);
        
        return mapParametros.get("vRetorno").toString();
    }

    @Override
    public String obtenerIndicadoresVenta(String pCodGrupoCia, String strCodCia, String strCodLocal,
                                          String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(strCodCia);
        parametros.add(strCodLocal);
        parametros.add(pNumPedVta);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_LEALTAD.GET_INDICADORES_VENTA(?,?,?,?)", parametros);
    }

    public String getMultiploPtos(String pCodGrupoCia, String strCodCia, String strCodLocal) throws Exception{
            HashMap<String, Object> mapParametros = new HashMap<String, Object>();
            
            mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
            mapParametros.put("cCodCia_in", strCodCia);
            mapParametros.put("cCodLocal_in", strCodLocal);
            mapper.getMultiploPtos(mapParametros);
            return mapParametros.get("vRetorno").toString();
        
    }
}
