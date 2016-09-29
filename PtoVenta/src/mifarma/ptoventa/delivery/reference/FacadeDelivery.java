package mifarma.ptoventa.delivery.reference;

import java.util.ArrayList;

import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.delivery.dao.DAORACDelivery;
import mifarma.ptoventa.delivery.dao.FactoryDelivery;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeDelivery {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeDelivery.class);
    
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
}
