package mifarma.ptoventa.mayorista.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBMayorista implements DAOMayorista {
    
    private static final Logger log = LoggerFactory.getLogger(MBMayorista.class);
    private SqlSession sqlSession = null;
    private MapperMayorista mapper = null;
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
        mapper = sqlSession.getMapper(MapperMayorista.class);
    }

    @Override
    public ArrayList<ArrayList<String>> getListaProformas(String pTipoVenta, String tipoOperacionProforma) throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno = null;
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cTipoVenta_in", pTipoVenta);
        mapParametros.put("cEstadoProforma_in", tipoOperacionProforma);
        mapper.getListaProformas(mapParametros);
            
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public ArrayList<ArrayList<String>> getDetalleProforma(String pNumPedido, String pCodLocal) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno = null;
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cCodLocalAtencion_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedido_in", pNumPedido);
        mapper.getDetalleProforma(mapParametros);
            
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal, boolean pFiltrar) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        List<BeanResultado> lstRetorno = null;
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cCodLocalAtencion_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedido_in", pNumPedido);
        mapParametros.put("cFlagFiltro_in", pFiltrar?"S":"N");
        mapper.cargaListaDetPedidos(mapParametros);
            
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        utilityPtoVenta.parsearResultado(lstRetorno, pTableModel, true);
        
    }

    @Override
    public ArrayList<ArrayList<String>> obtieneProductosSeleccionTotalLote(String pCodLocal, String pNumPedido) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno = null;
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNumPedido_in", pNumPedido);
        mapParametros.put("cCodLocalAtencion_in", FarmaVariables.vCodLocal);
        mapper.obtieneProductosSeleccionTotalLote(mapParametros);
            
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public ArrayList<ArrayList<String>> cargaListaDetProductoLote(String pNumPedido, String pCodProd, String pSecDetPedVta) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        List<BeanResultado> lstRetorno = null;
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedido_in", pNumPedido);
        mapParametros.put("cCodProd_in", pCodProd);
        mapParametros.put("nSecDetPed_in", pSecDetPedVta);
        mapper.cargaListaDetProductoLote(mapParametros);
            
        lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta, String pNumLote) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedido_in", pNumPedido);
        mapParametros.put("cCodProd_in", pCodProd);
        mapParametros.put("nSecDetPed_in", pSecDetPedVta);
        mapParametros.put("cNumLote_in", pNumLote);
        mapper.eliminaDetalleProducto(mapParametros);
    }

    @Override
    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedido_in", pNumPedido);
        mapParametros.put("cCodProd_in", pCodProd);
        mapParametros.put("nSecDetPed_in", pSecDetPedVta);
        mapper.eliminaDetalleProductoLotes(mapParametros);
    }

    @Override
    public void agregaDetalleProductoLote(String pNumPedido, String pCodProd, String pNumLote, String pCantidad,
                                          String pFechaVencimiento, String pSecDetPedVta, String pValFracVta) {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedido_in", pNumPedido);
        mapParametros.put("cCodProd_in", pCodProd);
        mapParametros.put("cNumLote_in", pNumLote);
        mapParametros.put("cCant_in", new Integer(pCantidad));
        mapParametros.put("cUsuCrea_in", FarmaVariables.vIdUsu);
        mapParametros.put("cFechaVencimiento_in", pFechaVencimiento);
        mapParametros.put("nSecDetPed_in", pSecDetPedVta);
        mapParametros.put("nValFracVta_in", pValFracVta);
        mapper.agregaDetalleProductoLote(mapParametros);
    }
    
    public void anularProforma(String pCodGrupoCia, String pCodLocal, String pNumProforma, String pUsuario)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCodLocal_in", pCodLocal);
        mapParametros.put("cNumPedido_in", pNumProforma);
        mapParametros.put("cUsuCrea_in", pUsuario);
        mapper.anularProforma(mapParametros);   
    }
    
    public String obtienePrecioMinimoVenta(String pCodGrupoCia, String pCodLocal, String pCodProd)throws Exception{
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", pCodGrupoCia);
        mapParametros.put("cCod_Local_in", pCodLocal);
        mapParametros.put("cCodProd_in", pCodProd);
        mapper.obtienePrecioMinimoVenta(mapParametros);
        return mapParametros.get("resultado").toString();
    }
    
}
