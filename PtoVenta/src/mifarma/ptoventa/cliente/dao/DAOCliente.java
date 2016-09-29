package mifarma.ptoventa.cliente.dao;

import java.util.List;

import mifarma.ptoventa.cliente.modelo.BeanClienteJuridico;
import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAOCliente extends DAOTransaccion {
    
    public List busquedaClientes(String pCodGrupoCia, String pCodLocal, String pValorBusqueda, String pTipoBusqueda)throws Exception; 
    public void registrarCliente(String pCodGrupoCia, String pCodLocal, String pTipoDocumento, BeanClienteJuridico cliente)throws Exception;
}