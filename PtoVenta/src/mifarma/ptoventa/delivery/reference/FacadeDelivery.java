package mifarma.ptoventa.delivery.reference;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTable;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.delivery.dao.DAODelivery;
import mifarma.ptoventa.delivery.dao.DAORACDelivery;
import mifarma.ptoventa.delivery.dao.FactoryDelivery;
import mifarma.ptoventa.proforma.reference.StrategyProforma;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeDelivery implements StrategyProforma{
    
    private static final Logger log = LoggerFactory.getLogger(FacadeDelivery.class);
    
    private DAODelivery daoDelivery;
    
    public FacadeDelivery(){
        this.daoDelivery = FactoryDelivery.getDAODelivery(TipoImplementacionDAO.FRAMEWORK);
    }
    
    public void actualizaProformaRAC(String pCodCia, String pCodLocal, String pNumProforma, String pCodLocalSap,
                                     String pNumComprobantes,
                                     String fechaEnvio) {
        
        DAORACDelivery daoRACDelivery = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.MYBATIS;
        //ERIOS 26.11.2015 Activar cuando se implemente el usuario GTDLV en el RAC
        /*if(DlgProcesar.getIndGestorTx().equals(FarmaConstants.INDICADOR_S)){
            tipo = TipoImplementacionDAO.GESTORTX;
        }*/
        try {
            //Abre conexion RAC
            daoRACDelivery = FactoryDelivery.getDAORACDelivery(tipo);
            daoRACDelivery.openConnection();
            daoRACDelivery.actualizaProformaRAC(pCodCia, pCodLocal, pNumProforma, pCodLocalSap, pNumComprobantes,
                                                     fechaEnvio);

            daoRACDelivery.commit();
        } catch (Exception ex) {
            daoRACDelivery.rollback();
            log.error("",ex);
        }
    }

    public void stockLocalesPreferidos(FarmaTableModel farmaTableModel, String pCodProd) {
        DAORACDelivery daoRACDelivery = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.GESTORTX;
        ArrayList<ArrayList<String>> lstListado = null;
        try {            
            daoRACDelivery = FactoryDelivery.getDAORACDelivery(tipo);
            daoRACDelivery.openConnection();
            lstListado = daoRACDelivery.cargaListaStockLocalesPreferidos(pCodProd);
            daoRACDelivery.commit();
            farmaTableModel.clearTable();
            farmaTableModel.data = lstListado;
        } catch (Exception ex) {
            daoRACDelivery.rollback();
            log.error("",ex);
        }
    }

    public String obtenerStockAlmacen(String pCodProd) {
        DAORACDelivery daoRACDelivery = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.GESTORTX;
        String strRetorno = "";
        try {            
            daoRACDelivery = FactoryDelivery.getDAORACDelivery(tipo);
            daoRACDelivery.openConnection();
            strRetorno = daoRACDelivery.obtieneIndicadorStock(pCodProd);
            daoRACDelivery.commit();
        } catch (Exception ex) {
            daoRACDelivery.rollback();
            log.error("",ex);
        }
        return strRetorno;
    }

    public String obtieneStockLocal(String pCodProd, String pCodLocalDestino) {
        DAORACDelivery daoRACDelivery = null;
        TipoImplementacionDAO tipo = TipoImplementacionDAO.GESTORTX;
        String strRetorno = "";
        try {            
            daoRACDelivery = FactoryDelivery.getDAORACDelivery(tipo);
            daoRACDelivery.openConnection();
            strRetorno = daoRACDelivery.obtieneStockLocal(pCodProd, pCodLocalDestino);
            daoRACDelivery.commit();
        } catch (Exception ex) {
            daoRACDelivery.rollback();
            log.error("",ex);
        }
        return strRetorno;
    }

    @Override
    public void listarPedidos(FarmaTableModel pTableModel, String tipoVenta, String tipoOperacionProforma) {
        try{
            daoDelivery.openConnection();
            daoDelivery.listarPedidos(pTableModel, tipoVenta, tipoOperacionProforma);
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
    }

    @Override
    public void mostrarDetalleProforma(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal) {
        try{
            daoDelivery.openConnection();
            daoDelivery.mostrarDetalleProforma(pTableModel, pNumPedido, pCodLocal);
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
    }

    @Override
    public void cargarTransfProforma(FarmaTableModel pTableModel, String pCodLocal, String pNumPedido) {
        try{
            daoDelivery.openConnection();
            daoDelivery.cargarTransfProforma(pTableModel, pCodLocal, pNumPedido);
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
    }

    @Override
    public void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedido, String pCodLocal,
                                     boolean pFiltrar) {
        try{
            daoDelivery.openConnection();
            daoDelivery.cargaListaDetPedidos(pTableModel, pNumPedido, pCodLocal, pFiltrar);
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
    }

    @Override
    public ArrayList obtieneProductosSeleccionTotalLote() {
        ArrayList myArray = null;
        try{
            daoDelivery.openConnection();
            myArray = daoDelivery.obtieneProductosSeleccionTotalLote();
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
        return myArray;
    }

    @Override
    public void cargaListaDetProductoLote(FarmaTableModel pTableModel, String pNumPedido, String pCodProd, String pSecDetPedVta) {
        try{
            daoDelivery.openConnection();
            daoDelivery.cargaListaDetProductoLote(pTableModel, pNumPedido, pCodProd, pSecDetPedVta);
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
    }

    @Override
    public void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta, String pNumLote) {
        try{
            daoDelivery.openConnection();
            daoDelivery.eliminaDetalleProducto(pNumPedido, pCodProd, pSecDetPedVta, pNumLote);
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
    }

    @Override
    public void agregaDetalleProductoLote(JTable tblListaIngresoLote, String pSecDetPedVta, String pValFracVta) {
        try{
            daoDelivery.openConnection();
            daoDelivery.eliminaDetalleProducto(VariablesDelivery.vNumeroPedido, VariablesDelivery.vCodProducto, pSecDetPedVta);
            for (int i = 0; i < tblListaIngresoLote.getRowCount(); i++) {
                daoDelivery.agregaDetalleProductoLote(FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 2),
                                                     FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 3),
                                                     FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 1),
                                                     FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 0),
                                                     FarmaUtility.getValueFieldJTable(tblListaIngresoLote, i, 5),
                                                      pSecDetPedVta, pValFracVta);
            }
            daoDelivery.commit();
        } catch (Exception ex) {
            daoDelivery.rollback();
            log.error("",ex);
        }
    }

    @Override
    public void generarPedidoVenta() {
        //ERIOS 12.01.2016 El pedido se genera en la pantalla de ultimos pedido.
    }

    @Override
    public boolean anularProforma(JDialog pJDialog, String pNumProforma) {
        return false;
    }
}
