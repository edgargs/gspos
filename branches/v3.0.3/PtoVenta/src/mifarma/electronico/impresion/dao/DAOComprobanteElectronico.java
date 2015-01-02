package mifarma.electronico.impresion.dao;

import java.util.ArrayList;

import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAOComprobanteElectronico extends DAOTransaccion {

    /**
     * @param pNumPedidoVta
     * @param pSecCompPago
     * @param pTipoDocumento
     * @return
     * @throws Exception
     */
    public ArrayList<ArrayList<String>> listaDatosCabecera(String pNumPedidoVta, String pSecCompPago,
                                                           String pTipoDocumento) throws Exception;

    public ArrayList<ArrayList<String>> listaDetalle(String pNumPedidoVta, String pSecCompPago,
                                                     String pTipoClienteConvenio) throws Exception;

    public ArrayList<ArrayList<String>> listaMontos(String pNumPedidoVta, String pSecCompPago) throws Exception;

    public String getDatosConvenio(String pNumPedidoVta, String pSecCompPago, String pTipoDocumento,
                                   String pTipoClienteConvenio) throws Exception;

    public ArrayList<ArrayList<String>> listaNotas(String pNumPedidoVta, String pSecCompPago,
                                                   String pTipoDocumento) throws Exception;

    public String listaDatosPiePagina() throws Exception;

    public ArrayList<ArrayList<String>> listaFormasPago(String pNumPedidoVta) throws Exception;

    public ArrayList<ArrayList<String>> listaDatosDelvery(String pNumPedidoVta) throws Exception;

    public ArrayList<ArrayList<String>> listaDatosEmpresa(String pNumPedidoVta) throws Exception;

}
