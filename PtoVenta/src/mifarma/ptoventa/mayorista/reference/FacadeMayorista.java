package mifarma.ptoventa.mayorista.reference;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.mayorista.dao.DAOMayorista;
import mifarma.ptoventa.mayorista.dao.FactoryMayorista;
import mifarma.ptoventa.proforma.reference.StrategyProforma;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeMayorista implements StrategyProforma {

    private static final Logger log = LoggerFactory.getLogger(FacadeMayorista.class);

    private DAOMayorista daoMayorista;

    public FacadeMayorista() {
        this.daoMayorista = FactoryMayorista.getDAOMayorista(TipoImplementacionDAO.MYBATIS);
    }

    @Override
    public void listarPedidos(FarmaTableModel pTableModel, String tipoVenta, String tipoOperacionProforma) {
        ArrayList<ArrayList<String>> listado;
        try {
            daoMayorista.openConnection();
            listado = daoMayorista.getListaProformas(tipoVenta, tipoOperacionProforma);
            daoMayorista.commit();

            pTableModel.clearTable();
            pTableModel.data = listado;
        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("", ex);
        }
    }

    @Override
    public void mostrarDetalleProforma(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal) {
        ArrayList<ArrayList<String>> listado;
        try {
            daoMayorista.openConnection();
            listado = daoMayorista.getDetalleProforma(pNumPedido, pCodLocal);
            daoMayorista.commit();

            pTableModel.clearTable();
            pTableModel.data = listado;
        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("", ex);
        }
    }

    @Override
    public void cargarTransfProforma(FarmaTableModel pTableModel, String pCodLocal, String pNumPedido) {
        //ERIOS 11.01.2016 Las proformas de local-mayorista no tienen transferencias.
    }

    @Override
    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal,
                                     boolean pFiltrar) {
        try {
            daoMayorista.openConnection();
            daoMayorista.cargaListaDetPedidos(pTableModel, pNumPedido, pCodLocal, pFiltrar);
            daoMayorista.commit();
        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("", ex);
        }
    }

    @Override
    public ArrayList obtieneProductosSeleccionTotalLote() {
        ArrayList<ArrayList<String>> listado = null;
        try {
            daoMayorista.openConnection();
            listado =
                    daoMayorista.obtieneProductosSeleccionTotalLote(VariablesDelivery.vCodLocalOrigen, VariablesDelivery.vNumeroPedido);
            daoMayorista.commit();

        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("", ex);
        }
        return listado;
    }

    @Override
    public void cargaListaDetProductoLote(FarmaTableModel pTableModel, String pNumPedido, String pCodProd,
                                          String pSecDetPedVta) {
        ArrayList<ArrayList<String>> listado;
        try {
            daoMayorista.openConnection();
            listado = daoMayorista.cargaListaDetProductoLote(pNumPedido, pCodProd, pSecDetPedVta);
            daoMayorista.commit();

            pTableModel.clearTable();
            pTableModel.data = listado;
        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("", ex);
            throw ex;
        }
    }

    @Override
    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta, String pNumLote) {
        try {
            daoMayorista.openConnection();
            daoMayorista.eliminaDetalleProducto(pNumPedido, pCodProd, pSecDetPedVta, pNumLote);
            daoMayorista.commit();
        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("", ex);
        }
    }

    @Override
    public void agregaDetalleProductoLote(JTable tblListaIngresoLote, String pSecDetPedVta, String pValFracVta) {
        try {
            daoMayorista.openConnection();
            daoMayorista.eliminaDetalleProducto(VariablesDelivery.vNumeroPedido, VariablesDelivery.vCodProducto,
                                                pSecDetPedVta);
            for (int i = 0; i < tblListaIngresoLote.getRowCount(); i++) {
                daoMayorista.agregaDetalleProductoLote(FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 2),
                                                       FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 3),
                                                       FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 1),
                                                       FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 0),
                                                       FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 5),
                                                       pSecDetPedVta, pValFracVta);
            }
            daoMayorista.commit();
        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("", ex);
            throw ex;
        }
    }

    @Override
    public void generarPedidoVenta() {

    }


    public boolean anularProforma(JDialog pJDialog, String pNumProforma) {
        boolean anulado = true;
        try {
            daoMayorista.openConnection();
            daoMayorista.anularProforma(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pNumProforma,
                                        FarmaVariables.vIdUsu);
            daoMayorista.commit();
        } catch (Exception ex) {
            anulado = false;
            daoMayorista.rollback();
            log.error("", ex);
            FarmaUtility.showMessage(pJDialog, "Venta Mayorista:\n" +
                    ex.toString(), null);
        }
        return anulado;
    }

    public double obtienePrecioMinimoVenta(JDialog pJDialog, String codProd) {
        double vMonto = 0;
        String cadMonto = "";
        try {
            daoMayorista.openConnection();
            cadMonto = daoMayorista.obtienePrecioMinimoVenta(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, codProd);
            vMonto = Double.parseDouble(cadMonto);
            daoMayorista.commit();
        } catch (Exception ex) {
            daoMayorista.rollback();
            log.error("[VENTA MAYORISTA]: OBTENER PRECIO MINIMO DE VENTA: "+cadMonto+" - "+vMonto);
            log.error("", ex);
            FarmaUtility.showMessage(pJDialog, "Venta Mayorista: Error al obtener el precio minimo de venta", null);
            vMonto = -1;
        }
        return vMonto;
    }
}
