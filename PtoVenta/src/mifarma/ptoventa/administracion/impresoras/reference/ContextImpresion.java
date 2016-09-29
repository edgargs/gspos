package mifarma.ptoventa.administracion.impresoras.reference;

import java.util.ArrayList;

import javax.swing.JDialog;


/**
 * Contexto para modulo de impresion
 * @author ERIOS
 * @since 20.01.2016
 */
public class ContextImpresion {
    
    private StrategyImpresion strategy;
    
    public ContextImpresion(StrategyImpresion strategy) {
        this.strategy = strategy;
    }
    
    public ArrayList<String> formatoFactura(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                            String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                            String pPorcIgv, String pNumCompCoPago, String pValTotalAhorro,
                                            boolean vEsPedidoConvenio, String pImprDatAdic,
                                            String pTipoClienteConvenio, String pCodTipoConvenio, String pFechaBD,
                                            String pRefTipComp, String pValRedondeoComPago, String pRazonSocial,
                                            String pDireccionDestino, String pNumeroRUC, String vPrctBeneficiario,
                                            String vPrctEmpresa, int margen,
                                            String pValTotalBruto, String pValTotalAfecto, String pValTotalDescuento,
                                            int numDocumento) {
        return strategy.formatoFactura(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante, pValIgvComPago,
                                       pValCopagoCompPago, pPorcIgv, pNumCompCoPago, pValTotalAhorro,
                                       vEsPedidoConvenio, pImprDatAdic, pTipoClienteConvenio, pCodTipoConvenio,
                                       pFechaBD, pRefTipComp, pValRedondeoComPago, pRazonSocial, pDireccionDestino,
                                       pNumeroRUC, vPrctBeneficiario, vPrctEmpresa, margen,
                                       pValTotalBruto, pValTotalAfecto, pValTotalDescuento,
                                       numDocumento);
    }

    public ArrayList<String> formatoGuia(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                         String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                         String pValIgvComCoPago, String pNumCompCoPago, String pRuta, boolean bol,
                                         String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio,
                                         String pFechaBD, String pRefTipComp, String pValRedondeoComPago,
                                         String pRazonSocial, String pDireccionDestino, String pNumeroRUC,
                                         String vPrctBeneficiario, String vPrctEmpresa, int margen) {
        return strategy.formatoGuia(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante, pValIgvComPago,
                                    pValCopagoCompPago, pValIgvComCoPago, pNumCompCoPago, pRuta, bol, pImprDatAdic,
                                    pTipoClienteConvenio, pCodTipoConvenio, pFechaBD, pRefTipComp, pValRedondeoComPago,
                                    pRazonSocial, pDireccionDestino, pNumeroRUC, vPrctBeneficiario, vPrctEmpresa,
                                    margen);
    }
    
    public ArrayList<String> formatoBoleta(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                           String pValTotalNeto, String pValRedondeo, String pNumComprobante,
                                           String pNomImpreso, String pDirImpreso, String pValTotalAhorro,
                                           String pRuta, boolean bol, int margen, boolean vEsPedidoConvenio,
                                           String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio, 
                                           String pValRedondeoCompPago, String pValIgvComPago, String pValCopagoCompPago, 
                                           String pValIgvCompCoPago, String pNumCompRef) {
        return strategy.formatoBoleta(pJDialog, pFechaBD, pDetalleComprobante, pValTotalNeto, pValRedondeo,
                                      pNumComprobante, pNomImpreso, pDirImpreso, pValTotalAhorro, pRuta, bol, margen,
                                      vEsPedidoConvenio, pImprDatAdic, pTipoClienteConvenio, pCodTipoConvenio,
                                      pValRedondeoCompPago, pValIgvComPago, pValCopagoCompPago, pValIgvCompCoPago,
                                      pNumCompRef);
    }
    
    public ArrayList<String> formatoNotaCredito(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                            String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                            String pPorcIgv, String pNumCompCoPago, String pValTotalAhorro,
                                            boolean vEsPedidoConvenio, String pImprDatAdic,
                                            String pTipoClienteConvenio, String pCodTipoConvenio, String pFechaBD,
                                            String pRefTipComp, String pValRedondeoComPago, String pRazonSocial,
                                            String pDireccionDestino, String pNumeroRUC, String vPrctBeneficiario,
                                            String vPrctEmpresa, int margen,
                                            String pValTotalBruto, String pValTotalAfecto, String pValTotalDescuento,
                                            int numDocumento) {
        return strategy.formatoNotaCredito(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante, pValIgvComPago,
                                       pValCopagoCompPago, pPorcIgv, pNumCompCoPago, pValTotalAhorro,
                                       vEsPedidoConvenio, pImprDatAdic, pTipoClienteConvenio, pCodTipoConvenio,
                                       pFechaBD, pRefTipComp, pValRedondeoComPago, pRazonSocial, pDireccionDestino,
                                       pNumeroRUC, vPrctBeneficiario, vPrctEmpresa, margen,
                                       pValTotalBruto, pValTotalAfecto, pValTotalDescuento,
                                       numDocumento);
    }
}
