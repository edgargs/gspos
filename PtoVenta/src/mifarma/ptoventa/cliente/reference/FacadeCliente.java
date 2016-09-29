package mifarma.ptoventa.cliente.reference;

import java.awt.Frame;

import java.util.List;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cliente.dao.DAOCliente;

import mifarma.ptoventa.cliente.dao.MBCliente;

import mifarma.ptoventa.cliente.modelo.BeanClienteJuridico;
import mifarma.ptoventa.fidelizacion.dao.MBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadeCliente {

    private static final Logger log = LoggerFactory.getLogger(FacadeCliente.class);
    private DAOCliente daoCliente;
    
    public FacadeCliente() {
        super();
    }
    
    public List consultarClientes(String pValorBusqueda, String pTipoBusqueda, boolean isOperarEnRac){
        List lstResultado = null;
        daoCliente = null;
        try{
            daoCliente = new MBCliente(isOperarEnRac);
            daoCliente.openConnection();
            lstResultado = daoCliente.busquedaClientes(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, pValorBusqueda, pTipoBusqueda);
            daoCliente.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoCliente.rollback();
            lstResultado = null;
        }
        return lstResultado;
    }
    
    public boolean registrarClienteJuridico(BeanClienteJuridico cliente, boolean isOperarEnRac){
        boolean isProceso = true;
        daoCliente = null;
        try{
            daoCliente = new MBCliente(isOperarEnRac);
            daoCliente.openConnection();
            daoCliente.registrarCliente(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, ConstantsFidelizacion.COD_TIPO_DOC_RUC, cliente);
            daoCliente.commit();
        }catch(Exception ex){
            log.error("", ex);
            daoCliente.rollback();
            isProceso = false;
        }
        return isProceso;
    }
}
