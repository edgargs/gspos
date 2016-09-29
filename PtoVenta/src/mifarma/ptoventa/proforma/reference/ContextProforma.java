package mifarma.ptoventa.proforma.reference;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;


public class ContextProforma {
    
    private StrategyProforma strategy;
    
    public ContextProforma(StrategyProforma strategy) {
        this.strategy = strategy;
    }

    public void listarProformas(FarmaTableModel pTableModel, String tipoVenta, String tipoOperacionProforma) throws Exception {
        strategy.listarPedidos(pTableModel, tipoVenta, tipoOperacionProforma);
    }

    public void mostrarDetalleProforma(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal) throws Exception{
        strategy.mostrarDetalleProforma(pTableModel, pNumPedido, pCodLocal);
    }

    public void cargarTransfProforma(FarmaTableModel pTableModel, String pCodLocal, String pNumPedido) {
        strategy.cargarTransfProforma(pTableModel, pCodLocal, pNumPedido);
    }

    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal, boolean pFiltrar) {
        strategy.cargaListaDetPedidos(pTableModel, pNumPedido, pCodLocal, pFiltrar);
    }

    public ArrayList obtieneProductosSeleccionTotalLote() {
        return strategy.obtieneProductosSeleccionTotalLote();        
    }

    public void cargaListaDetProductoLote(FarmaTableModel pTableModel, String pNumPedido, String pCodProd, String pSecDetPedVta) {
        strategy.cargaListaDetProductoLote(pTableModel, pNumPedido, pCodProd, pSecDetPedVta);
    }

    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta,
                                              String pNumLote) {
        strategy.eliminaDetalleProducto(pNumPedido, pCodProd, pSecDetPedVta, pNumLote);
    }

    public void agregaDetalleProductoLote(JTable tblListaIngresoLote, String pSecDetPedVta, String pValFracVta) {
        strategy.agregaDetalleProductoLote(tblListaIngresoLote, pSecDetPedVta, pValFracVta);
    }

    public void generarPedidoVenta() {
        strategy.generarPedidoVenta();
    }
    
    public StrategyProforma getStrategyProforma(){
        return this.strategy;
    }
    
    public boolean anularProforma(JDialog pJDialog, String pNumProforma){
        return strategy.anularProforma(pJDialog, pNumProforma);
    }
}
