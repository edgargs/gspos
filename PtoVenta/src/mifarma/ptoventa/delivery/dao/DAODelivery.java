package mifarma.ptoventa.delivery.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAODelivery extends DAOTransaccion{
    
    public void listarPedidos(FarmaTableModel farmaTableModel, String tipoVenta, String tipoOperacionProforma) throws SQLException;

    public void mostrarDetalleProforma(FarmaTableModel farmaTableModel, String string, String string1) throws SQLException;

    public void cargarTransfProforma(FarmaTableModel farmaTableModel, String string, String string1) throws SQLException;

    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal,
                                     boolean pFiltrar) throws SQLException ;

    public ArrayList obtieneProductosSeleccionTotalLote() throws SQLException;

    public void cargaListaDetProductoLote(FarmaTableModel pTableModel, String pNumPedido, String pCodProd, String pSecDetPedVta) throws SQLException;

    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta, String pNumLote) throws SQLException;

    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta) throws SQLException;

    public void agregaDetalleProductoLote(String pNumPedido, String pCodProd, String pNumLote, String pCantidad,
                                                 String pFechaVencimiento, String pSecDetPedVta, String pValFracVta) throws SQLException;
}
