package mifarma.ptoventa.ce.dao;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Map;

import mifarma.ptoventa.reference.DAOTransaccion;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DAOCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.03.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public interface DAOCajaElectronica extends DAOTransaccion {

    public void respaldoFormaPagoPedido(String strNumPedido) throws SQLException;

    public void borraFormaPagoPedido(String strNumPedido) throws SQLException;

    public void grabaFormaPagoPedido(String strNumPedido, String pCodFormaPago, String pImPago, String pTipMoneda,
                                     String pTipoCambio, String pVuelto, String pImTotalPago, String pNumTarj,
                                     String pFecVencTarj, String pNomCliTarj, String pCantCupon, String strDni,
                                     String strCodVoucher, String strLote) throws SQLException;

    public int grabarReciboPagoSencillo(String folio, BigDecimal pTotal, BigDecimal pMonto, BigDecimal pDiferencia, char pTipFondoSencillo,
                                         char pCodETV) throws SQLException;

    public Map verificaReciboSencillo(char pCodETV);

    public int getETV();

    public String imprimirFondoSencillo(int codFonSencillo);
    
    public ArrayList getListaETVs();

    public ArrayList<ArrayList<String>> getListaCajasAperturadas(String pFechaDiaVenta) throws Exception;    
}
