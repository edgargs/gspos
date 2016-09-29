package mifarma.ptoventa.administracion.impresoras.reference;

import java.util.ArrayList;

import javax.swing.JDialog;


/**
 * Estrategia para modulo de impresion
 * @author ERIOS
 * @since 20.01.2016
 */
public interface StrategyImpresion {
    
    public ArrayList<String> formatoFactura(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                               String pNumComprobante, String pValIgvComPago,
                                               String pValCopagoCompPago, String pPorcIgv, String pNumCompCoPago,
                                               String pValTotalAhorro, boolean vEsPedidoConvenio, String pImprDatAdic,
                                               String pTipoClienteConvenio, String pCodTipoConvenio, String pFechaBD,
                                               String pRefTipComp, String pValRedondeoComPago, String pRazonSocial,
                                               String pDireccionDestino, String pNumeroRUC, String vPrctBeneficiario,
                                               String vPrctEmpresa, int margen,
                                               String pValTotalBruto, String pValTotalAfecto, String pValTotalDescuento, 
                                               int numDocumento);
    
    public ArrayList<String> formatoGuia(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                            String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                            String pValIgvComCoPago, String pNumCompCoPago, String pRuta, boolean bol,
                                            String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio,
                                            String pFechaBD, String pRefTipComp, String pValRedondeoComPago,
                                            String pRazonSocial, String pDireccionDestino, String pNumeroRUC,
                                            String vPrctBeneficiario, String vPrctEmpresa, int margen); 
    
    public ArrayList<String> formatoBoleta(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                                 String pValTotalNeto, String pValRedondeo, String pNumComprobante,
                                                 String pNomImpreso, String pDirImpreso, String pValTotalAhorro,
                                                 String pRuta, boolean bol, int margen, boolean vEsPedidoConvenio,
                                                 String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio, 
                                                 String pValRedondeoCompPago, String pValIgvComPago, String pValCopagoCompPago, 
                                                 String pValIgvCompCoPago, String pNumCompRef);
    
    public ArrayList<String> formatoNotaCredito(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                               String pNumComprobante, String pValIgvComPago,
                                               String pValCopagoCompPago, String pPorcIgv, String pNumCompCoPago,
                                               String pValTotalAhorro, boolean vEsPedidoConvenio, String pImprDatAdic,
                                               String pTipoClienteConvenio, String pCodTipoConvenio, String pFechaBD,
                                               String pRefTipComp, String pValRedondeoComPago, String pRazonSocial,
                                               String pDireccionDestino, String pNumeroRUC, String vPrctBeneficiario,
                                               String vPrctEmpresa, int margen,
                                               String pValTotalBruto, String pValTotalAfecto, String pValTotalDescuento, 
                                               int numDocumento);
}
