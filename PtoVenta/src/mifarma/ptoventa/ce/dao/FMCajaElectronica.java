package mifarma.ptoventa.ce.dao;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Map;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.caja.reference.DBCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : JDBCCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.03.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FMCajaElectronica implements DAOCajaElectronica {
    private static final Logger log = LoggerFactory.getLogger(FMCajaElectronica.class);

    public FMCajaElectronica() {
        super();
    }

    public void respaldoFormaPagoPedido(String strNumPedido) throws SQLException {
        DBCaja.saveHistFormPago(strNumPedido);
    }

    public void borraFormaPagoPedido(String strNumPedido) throws SQLException {
        DBCaja.elimiFormaPagoPedido(strNumPedido);
    }

    public void grabaFormaPagoPedido(String strNumPedido, String pCodFormaPago, String pImPago, String pTipMoneda,
                                     String pTipoCambio, String pVuelto, String pImTotalPago, String pNumTarj,
                                     String pFecVencTarj, String pNomCliTarj, String pCantCupon, String strDni,
                                     String strCodVoucher, String strLote) throws SQLException {
        DBCaja.grabaFormaPagoPedido(pCodFormaPago, pImPago, pTipMoneda, pTipoCambio, pVuelto, pImTotalPago, pNumTarj,
                                    pFecVencTarj, pNomCliTarj, pCantCupon, strDni, strCodVoucher, strLote);
    }

    public int grabarReciboPagoSencillo(String pFolio, BigDecimal pTotal, BigDecimal pMonto, BigDecimal pDiferencia,
                                        char pTipFondoSencillo, char pCodETV) {
        return 0;
    }

    public Map verificaReciboSencillo(char pCodETV) {
        return null;
    }

    public int getETV() {
        return 0;
    }

    public String imprimirFondoSencillo(int codFonSencillo) {
        return null;
    }

    @Override
    public ArrayList getListaETVs() {
        ArrayList array = new ArrayList();
        try {
            DBCaja.getListaETV(array);
        } catch (Exception ex) {
            log.debug("", ex);
        }
        return array;
    }

    public void commit() {
        FarmaUtility.aceptarTransaccion();
    }

    public void rollback() {
        FarmaUtility.liberarTransaccion();
    }

    @Override
    public void openConnection() {
        //TODO
    }

    @Override
    public ArrayList<ArrayList<String>> getListaCajasAperturadas(String pFechaDiaVenta) {
        return null;
    }
}
