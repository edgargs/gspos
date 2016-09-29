package mifarma.ptoventa.delivery.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.delivery.reference.DBDelivery;


public class FMDelivery implements DAODelivery {
    
    @Override
    public void commit() {
        FarmaUtility.aceptarTransaccion();
    }

    @Override
    public void rollback() {
        FarmaUtility.liberarTransaccion();
    }

    @Override
    public void openConnection() {
    }

    @Override
    public void listarPedidos(FarmaTableModel pTableModel, String tipoVenta, String tipoOperacionProforma) throws SQLException {
        DBDelivery.cargaListaCabUltimosPedidos(pTableModel, tipoVenta, tipoOperacionProforma);
    }

    @Override
    public void mostrarDetalleProforma(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal) throws SQLException {
        DBDelivery.cargaListaDetUltimosPedidos(pTableModel, pNumPedido, pCodLocal);
    }

    @Override
    public void cargarTransfProforma(FarmaTableModel pTableModel, String pCodLocal, String pNumPedVta) throws SQLException {
        DBDelivery.cargarListaDetTransferenciaPedido(pTableModel, pCodLocal, pNumPedVta);
    }

    @Override
    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal,
                                     boolean pFiltrar) throws SQLException {
        DBDelivery.cargaListaDetPedidos(pTableModel, pNumPedido, pCodLocal, pFiltrar);
    }

    @Override
    public ArrayList obtieneProductosSeleccionTotalLote() throws SQLException {
        ArrayList myArray = new ArrayList();
        DBDelivery.obtieneProductosSeleccionTotalLote(myArray);
        return myArray;
    }

    @Override
    public void cargaListaDetProductoLote(FarmaTableModel pTableModel, String pNumPedido, String pCodProd, String pSecDetPedVta) throws SQLException {
        DBDelivery.cargaListaDetProductoLote(pTableModel, pNumPedido, pCodProd, pSecDetPedVta);
    }

    @Override
    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta, String pNumLote) throws SQLException {
        DBDelivery.eliminaDetalleProducto(pNumPedido, pCodProd, pSecDetPedVta, pNumLote);
    }

    @Override
    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta) throws SQLException {
        DBDelivery.eliminaDetalleProducto(pNumPedido, pCodProd, pSecDetPedVta);
    }

    @Override
    public void agregaDetalleProductoLote(String pNumPedido, String pCodProd, String pNumLote, String pCantidad,
                                          String pFechaVencimiento, String pSecDetPedVta, String pValFracVta) throws SQLException {
        DBDelivery.agregaDetalleProductoLote(pNumPedido, pCodProd, pNumLote, pCantidad,
                                          pFechaVencimiento, pSecDetPedVta, pValFracVta);
    }
}
