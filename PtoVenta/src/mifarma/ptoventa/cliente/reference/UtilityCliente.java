package mifarma.ptoventa.cliente.reference;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.cliente.modelo.BeanClienteJuridico;

import mifarma.ptoventa.reference.TipoAccionDatos;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerUtil.FarmaUtil;

public class UtilityCliente {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityCliente.class);
    
    public UtilityCliente() {
        super();
    }
    
    public void consultarClienteJuridico(JDialog pJDialog, FarmaTableModel pTableModel, String pValorBusqueda, String pTipoBusqueda){
        mantenimientoClientesJuridico(pJDialog, pTableModel, pValorBusqueda, pTipoBusqueda, null, TipoAccionDatos.CONSULTA);
    }
    
    public boolean registrarClienteJuridico(JDialog pJDialog, BeanClienteJuridico cliente){
        boolean isRegistro = true;
        isRegistro = mantenimientoClientesJuridico(pJDialog, null, null, null, cliente, TipoAccionDatos.INSERTA);
        return isRegistro;
    }
    
    public boolean actualizarClienteJuridico(JDialog pJDialog, BeanClienteJuridico cliente){
        boolean isRegistro = true;
        isRegistro = mantenimientoClientesJuridico(pJDialog, null, null, null, cliente, TipoAccionDatos.ACTUALIZA);
        return isRegistro;
    }
    
    
    private boolean mantenimientoClientesJuridico(JDialog pJDialog, FarmaTableModel pTableModel, String pValorBusqueda, String pTipoBusqueda, BeanClienteJuridico cliente, TipoAccionDatos pAccion) {
        boolean isOperarEnRac;
        isOperarEnRac = (new UtilityPtoVenta()).isCentralizaClientes();
        if (pAccion.equals(TipoAccionDatos.CONSULTA)){
            if(isOperarEnRac){
                // KMONCADA 31.05.2016 PARA CASOS DE CONSULTA DE CLIENTE POR NOMBRE CONSULTARA EN EL LOCAL.
                isOperarEnRac = ConstantsCliente.TIPO_BUSQUEDA_RUC.equalsIgnoreCase(pTipoBusqueda);
            }
            pTableModel.clearTable();
            operaConsultaClientesJuridico(pJDialog, pTableModel, pValorBusqueda, pTipoBusqueda, isOperarEnRac);
            return true;
        }else if(pAccion.equals(TipoAccionDatos.INSERTA)){
            return operaRegistroClienteJuridico(pJDialog, cliente, isOperarEnRac);
        }else if(pAccion.equals(TipoAccionDatos.ACTUALIZA)){
            return operaRegistroClienteJuridico(pJDialog, cliente, isOperarEnRac);
        }
        return false;
    }
    
    private void operaConsultaClientesJuridico(JDialog pJDialog, FarmaTableModel pTableModel, String pValorBusqueda, String pTipoBusqueda, boolean isOperarEnRac){
        //ConstantsCliente.TIPO_BUSQUEDA_RUC.equalsIgnoreCase(pTipoBusqueda)
        log.info("[CONSULTA DE CLIENTES]: CONSULTANDO EN RAC? "+isOperarEnRac);
        FacadeCliente facade = new FacadeCliente();
        List lstCliente = facade.consultarClientes(pValorBusqueda, pTipoBusqueda, isOperarEnRac);
        if(lstCliente!=null){
            if(!lstCliente.isEmpty()){
                for(int i=0; i<lstCliente.size(); i++){
                    BeanClienteJuridico cliente = (BeanClienteJuridico)lstCliente.get(i);
                    ArrayList fila = new ArrayList();
                    fila.add(cliente.getCodCliente());
                    fila.add(cliente.getNumDocumento());
                    fila.add(cliente.getRazonSocial());
                    fila.add(cliente.getDireccion());
                    fila.add(cliente);
                    pTableModel.insertRow(fila);
                    if(isOperarEnRac){
                        facade.registrarClienteJuridico(cliente, false);
                    }
                }
            }else{
                if(isOperarEnRac){
                    operaConsultaClientesJuridico(pJDialog, pTableModel, pValorBusqueda, pTipoBusqueda, false);
                }
            }
        }else{
            FarmaUtility.showMessage(pJDialog, "Se presento un error al consultar clientes.\n"+
                                               "Reintente, si problema persiste comuniquese con Mesa de Ayuda!!!", null);
        }
    }
    
    private boolean operaRegistroClienteJuridico(JDialog pJDialog, BeanClienteJuridico cliente, boolean isOperarEnRac){
        FacadeCliente facade = new FacadeCliente();
        //REGISTRO EN LOCAL
        boolean isRegistro = facade.registrarClienteJuridico(cliente, false);
        if(isRegistro){
            //REGISTRO EN RAC
            if(isOperarEnRac){
                if(!facade.registrarClienteJuridico(cliente, isOperarEnRac)){
                    log.info("[PERCEPCION]: REGISTRO DE CLIENTES JURIDOS EN RAC FALLO!!!");
                }
            }
        }else{
            FarmaUtility.showMessage(pJDialog, "Mantenimiento Cliente Juridico:\n"+
                                               "Upps!!!, No se pudo registrar la informacion.\n"+
                                               "Reintente, Si problema persiste comuniquese con Mesa de Ayuda.", null);
        }
        return isRegistro;
    }
}
