package mifarma.ptoventa.delivery.dao;

import java.util.ArrayList;

import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAORACDelivery extends DAOTransaccion{
    
    public void actualizaProformaRAC(String pCodCia, String pCodLocal, String pNumProforma, String pCodLocalSap,
                                     String pNumComprobantes, String fechaEnvio) throws Exception;
    
    public ArrayList<ArrayList<String>> cargaListaStockLocalesPreferidos(String pCodprod) throws Exception;
    
    public String obtieneIndicadorStock(String pCodProd) throws Exception;

    public String obtieneStockLocal(String pCodProd, String pCodLocalDestino) throws Exception;
}
