package mifarma.ptoventa.ce.dao;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.MyBatisUtil;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MBCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.03.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class MBCajaElectronica implements DAOCajaElectronica {

    private static final Logger log = LoggerFactory.getLogger(MBCajaElectronica.class);
    private SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    private MapperCajaElectronica mapper = sqlSession.getMapper(MapperCajaElectronica.class);
    private UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();

    public MBCajaElectronica() {
        super();
    }

    public void respaldoFormaPagoPedido(String strNumPedido) throws SQLException {

        log.debug("Ejecuta respaldoFormaPagoPedido");
        mapper.saveHistFormPago(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, strNumPedido,
                                FarmaVariables.vIdUsu);

    }

    public void borraFormaPagoPedido(String strNumPedido) throws SQLException {
        mapper.elimiFormaPagoPedido(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, strNumPedido);
    }

    public void grabaFormaPagoPedido(String strNumPedido, String pCodFormaPago, String pImPago, String pTipMoneda,
                                     String pTipoCambio, String pVuelto, String pImTotalPago, String pNumTarj,
                                     String pFecVencTarj, String pNomCliTarj, String pCantCupon, String strDni,
                                     String strCodVoucher, String strLote) {
        mapper.grabaFormaPagoPedido(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pCodFormaPago, strNumPedido,
                                    FarmaUtility.getDecimalNumber(pImPago), pTipMoneda,
                                    FarmaUtility.getDecimalNumber(pTipoCambio), FarmaUtility.getDecimalNumber(pVuelto),
                                    FarmaUtility.getDecimalNumber(pImTotalPago), pNumTarj, pFecVencTarj, pNomCliTarj,
                                    new Integer(pCantCupon), FarmaVariables.vIdUsu, strDni, strCodVoucher, strLote);
    }

    public int grabarReciboPagoSencillo(String pFolio, BigDecimal pTotal, BigDecimal pMonto, BigDecimal pDiferencia,
                                        char pTipFondoSencillo, char pCodETV) {
        openConnection();
        int codFonSencillo;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cFolio_in", pFolio);
        mapParametros.put("cTotal_in", pTotal);
        mapParametros.put("cMonto_in", pMonto);
        mapParametros.put("cDiferencia_in", pDiferencia);
        mapParametros.put("cTipFondoSencillo_in", pTipFondoSencillo);
        mapParametros.put("cCodETV_in", pCodETV);
        mapParametros.put("cIdUsu_in", FarmaVariables.vIdUsu);
        mapper.grabarReciboPagoSencillo(mapParametros);
        codFonSencillo = new Integer(mapParametros.get("vCodFonSencillo_out").toString());
        sqlSession.commit(true);
        return codFonSencillo;
    }

    public Map verificaReciboSencillo(char pCodETV) {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cCodETV_in", pCodETV);
        mapper.verificaReciboSencillo(mapParametros);
        return mapParametros;
    }

    public int getETV() {
        int pCodETV;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodCia_in", FarmaVariables.vCodCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapper.getETV(mapParametros);
        pCodETV = new Integer(mapParametros.get("vCodETV_out").toString());
        return pCodETV;
    }

    public String imprimirFondoSencillo(int codFonSencillo) {
        openConnection();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodFonSencillo", codFonSencillo);
        mapper.imprimirFondoSencillo(mapParametros);
        String mensaje = mapParametros.get("vMensaje").toString();
        return mensaje;
    }

    public ArrayList getListaETVs() {
        ArrayList<ArrayList<String>> lstListado = null;
        try {
            openConnection();
            Map<String, Object> mapParametros = new HashMap<String, Object>();
            mapper.getListaETVs(mapParametros);
            List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
            lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            sqlSession.close();
        }
        return lstListado;
    }

    public void commit() {
        sqlSession.commit(true);
        sqlSession.close();
    }

    public void rollback() {
        sqlSession.rollback(true);
        sqlSession.close();
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperCajaElectronica.class);
    }

    /**
     * Lista las cajas aperturadas
     * @author ERIOS
     * @since 2.2.8
     * @param pFechaDiaVenta
     * @return
     * @throws Exception
     */
    public ArrayList<ArrayList<String>> getListaCajasAperturadas(String pFechaDiaVenta) throws Exception {
        ArrayList<ArrayList<String>> lstListado = null;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia_in", FarmaVariables.vCodGrupoCia);
        mapParametros.put("cCodLocal_in", FarmaVariables.vCodLocal);
        mapParametros.put("cCierreDia_in", pFechaDiaVenta);
        mapper.getListaCajasAperturadas(mapParametros);
        List<BeanResultado> lstRetorno = (List<BeanResultado>)mapParametros.get("listado");
        lstListado = utilityPtoVenta.parsearResultadoMatriz(lstRetorno);
        return lstListado;
    }
}
