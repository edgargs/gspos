package mifarma.electronico.impresion.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBComprobanteElectronico implements DAOComprobanteElectronico {
    private static final Logger log = LoggerFactory.getLogger(MBComprobanteElectronico.class);
    private SqlSession sqlSession = null;
    private MapperComprobanteElectronico mapper = null;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    public MBComprobanteElectronico() {
        super();
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperComprobanteElectronico.class);
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
    public ArrayList<ArrayList<String>> listaDatosCabecera(String pNumPedidoVta, String pSecCompPago,
                                                           String pTipoDocumento) throws Exception {
        ArrayList<ArrayList<String>> lstListado;
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);
        mapParametros.put("cSecCompPago", pSecCompPago);
        mapParametros.put("cTipoDocumento", pTipoDocumento);
        mapper.imprimeCabecera(mapParametros);
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }


    @Override
    public ArrayList<ArrayList<String>> listaDetalle(String pNumPedidoVta, String pSecCompPago,
                                                     String pTipoClienteConvenio) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);
        mapParametros.put("cSecCompPago", pSecCompPago);
        mapParametros.put("cTipoClienteConvenio", pTipoClienteConvenio);

        mapper.imprimeDetalle(mapParametros);

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public ArrayList<ArrayList<String>> listaMontos(String pNumPedidoVta, String pSecCompPago) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);
        mapParametros.put("cSecCompPago", pSecCompPago);

        mapper.imprimeMontos(mapParametros);

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }


    @Override
    public String listaDatosPiePagina() throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);

        mapper.imprimePiePagina(mapParametros);

        String resultado = mapParametros.get("listado").toString();
        return resultado;
    }

    @Override
    public ArrayList<ArrayList<String>> listaNotas(String pNumPedidoVta, String pSecCompPago,
                                                   String pTipoDocumento) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);
        mapParametros.put("cSecCompPago", pSecCompPago);
        mapParametros.put("cTipoDocumento", pTipoDocumento);

        mapper.imprimeNotas(mapParametros);

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }


    @Override
    public ArrayList<ArrayList<String>> listaFormasPago(String pNumPedidoVta) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);

        mapper.imprimeFormaPago(mapParametros);

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public String getDatosConvenio(String pNumPedidoVta, String pSecCompPago, String pTipoDocumento,
                                   String pTipoClienteConvenio) {

        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);
        mapParametros.put("cSecCompPago", pSecCompPago);
        mapParametros.put("cTipoDocumento", pTipoDocumento);
        mapParametros.put("cTipoClienteConvenio", pTipoClienteConvenio);
        openConnection();
        try {
            mapper.imprimeDatosConvenio(mapParametros);
        } finally {
            sqlSession.close();
        }
        String resultado = mapParametros.get("listado").toString();
        return resultado;
    }

    @Override
    public ArrayList<ArrayList<String>> listaDatosDelvery(String pNumPedidoVta) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);

        mapper.imprimeDatosDelivery(mapParametros);

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }

    @Override
    public ArrayList<ArrayList<String>> listaDatosEmpresa(String pNumPedidoVta) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<String, Object>();
        ArrayList<ArrayList<String>> lstListado;
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cNumPedidoVta", pNumPedidoVta);

        mapper.imprimeDatosMayorista(mapParametros);

        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }
}
