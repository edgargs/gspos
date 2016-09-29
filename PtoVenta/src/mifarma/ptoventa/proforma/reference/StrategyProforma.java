package mifarma.ptoventa.proforma.reference;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;


public interface StrategyProforma {
    
    public void listarPedidos(FarmaTableModel pTableModel, String tipoVenta, String tipoOperacionProforma) throws Exception;

    public void mostrarDetalleProforma(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal) throws Exception;

    public void cargarTransfProforma(FarmaTableModel pTableModel, String pCodLocal, String pNumPedido);

    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal, boolean pFiltrar);

    public ArrayList obtieneProductosSeleccionTotalLote();

    public void cargaListaDetProductoLote(FarmaTableModel pTableModel, String pNumPedido, String pCodProd, String pSecDetPedVta);

    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta, String pNumLote);

    public void agregaDetalleProductoLote(JTable tblListaIngresoLote, String pSecDetPedVta, String pValFracVta);

    public void generarPedidoVenta();
    
    public boolean anularProforma(JDialog pJDialog, String pNumProforma);
}
