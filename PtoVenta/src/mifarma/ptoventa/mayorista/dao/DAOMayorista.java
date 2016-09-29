package mifarma.ptoventa.mayorista.dao;

import java.util.ArrayList;

import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAOMayorista extends DAOTransaccion{
    
    ArrayList<ArrayList<String>> getListaProformas(String tipoVenta, String tipoOperacionProforma) throws Exception;

    public ArrayList<ArrayList<String>> getDetalleProforma(String pNumPedido, String pCodLocal) throws Exception;

    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal, boolean pFiltrar);

    public ArrayList<ArrayList<String>> obtieneProductosSeleccionTotalLote(String pCodLocal, String pNumPedido);

    public ArrayList<ArrayList<String>> cargaListaDetProductoLote(String pNumPedido, String pCodProd, String pSecDetPedVta);

    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta, String pNumLote);

    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta);

    public void agregaDetalleProductoLote(String pNumPedido, String pCodProd, String pNumLote, String pCantidad,
                                                 String pFechaVencimiento, String pSecDetPedVta, String pValFracVta);
    
    public void anularProforma(String pCodGrupoCia, String pCodLocal, String pNumProforma, String pUsuario)throws Exception;
    
    public String obtienePrecioMinimoVenta(String pCodGrupoCia, String pCodLocal, String pCodProd)throws Exception;
}
