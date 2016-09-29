package mifarma.ptoventa.cliente.reference;

import java.awt.Frame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgSeleccionTipoComprobante;
import mifarma.ptoventa.cliente.DlgBuscaClienteJuridico;
import mifarma.ptoventa.cliente.modelo.BeanClienteJuridico;

import mifarma.ptoventa.reference.TipoAccionCRUD;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerUtil.FarmaUtil;

public class UtilityCliente {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityCliente.class);
    
    public UtilityCliente() {
        super();
    }
    
    public void buscarCliente(Frame myParentFrame, JDialog pJDialog, JTextField pTxtCampoBusqueda, boolean isClienteRUC){
        pTxtCampoBusqueda.setText(pTxtCampoBusqueda.getText().trim().toUpperCase());
        String textoBusqueda = pTxtCampoBusqueda.getText().trim();
        if (textoBusqueda.length() >= 3) {
            boolean isNumero = true;
            int k=0;
            while(isNumero && k<textoBusqueda.length()){
                isNumero = isNumero && Character.isDigit(textoBusqueda.charAt(k));
                k++;
            }
            if(isNumero){
                if(isClienteRUC){
                    if(ConstantsCliente.RESULTADO_RUC_VALIDO.equalsIgnoreCase(DBCliente.verificaRucValido2(textoBusqueda))){
                        log.info("[SELECCION DE TIPO COMPROBANTE]: RUC VALIDO");
                        //VariablesCliente.vRuc = textoBusqueda;
                        buscaClienteJuridico(myParentFrame, pJDialog, ConstantsCliente.TIPO_BUSQUEDA_RUC, textoBusqueda);
                    }else{
                        FarmaUtility.showMessage(pJDialog, "Nro de RUC INVALIDO", pTxtCampoBusqueda);
                    }
                }else{
                    log.info("[SELECCION DE TIPO COMPROBANTE]: RUC INVALIDO - BOLETA SELECCIONADA");
                    buscaClienteJuridico(myParentFrame, pJDialog, ConstantsCliente.TIPO_BUSQUEDA_DNI, textoBusqueda);
                }
            } else{
                if(!isClienteRUC){
                    buscaClienteJuridico(myParentFrame, pJDialog, ConstantsCliente.TIPO_BUSQUEDA_NOMBRE, textoBusqueda);
                }else if(isClienteRUC){
                    buscaClienteJuridico(myParentFrame, pJDialog, ConstantsCliente.TIPO_BUSQUEDA_RAZSOC, textoBusqueda);
                }
            }
        } else {
            FarmaUtility.showMessage(pJDialog, "Ingrese 3 caracteres como minimo para realizar la busqueda", pTxtCampoBusqueda);
        }
    }
    
    private void buscaClienteJuridico(Frame myParentFrame, JDialog pJDialog, String pTipoBusqueda, String pBusqueda) {
        VariablesCliente.vTipoBusqueda = pTipoBusqueda;
        VariablesCliente.vRuc_RazSoc_Busqueda = pBusqueda;
        VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_S;
        
        if (pJDialog instanceof DlgSeleccionTipoComprobante){
            DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico(myParentFrame, "", true);
            dlgBuscaClienteJuridico.setVisible(true);
        }
    }
    
    public List<BeanClienteJuridico> consultarClienteJuridico(JDialog pJDialog, String pValorBusqueda, String pTipoBusqueda){
        List<BeanClienteJuridico> lstClientes = new ArrayList<BeanClienteJuridico>();
        mantenimientoClientesJuridico(pJDialog, lstClientes, pValorBusqueda, pTipoBusqueda, null, TipoAccionCRUD.CONSULTA);
        return lstClientes;
    }
    
    public boolean registrarClienteJuridico(JDialog pJDialog, BeanClienteJuridico cliente){
        boolean isRegistro = true;
        isRegistro = mantenimientoClientesJuridico(pJDialog, null, null, null, cliente, TipoAccionCRUD.INSERTA);
        return isRegistro;
    }
    
    public boolean actualizarClienteJuridico(JDialog pJDialog, BeanClienteJuridico cliente){
        boolean isRegistro = true;
        isRegistro = mantenimientoClientesJuridico(pJDialog, null, null, null, cliente, TipoAccionCRUD.ACTUALIZA);
        return isRegistro;
    }
    
    
    private boolean mantenimientoClientesJuridico(JDialog pJDialog, List<BeanClienteJuridico> lstClientes, 
                                                  String pValorBusqueda, String pTipoBusqueda, 
                                                  BeanClienteJuridico cliente, TipoAccionCRUD pAccion) {
        boolean isOperarEnRac;
        isOperarEnRac = (new UtilityPtoVenta()).isCentralizaClientes();
        if (pAccion.equals(TipoAccionCRUD.CONSULTA)){
            if(isOperarEnRac){
                // KMONCADA 31.05.2016 PARA CASOS DE CONSULTA DE CLIENTE POR NOMBRE CONSULTARA EN EL LOCAL.
                isOperarEnRac = ConstantsCliente.TIPO_BUSQUEDA_RUC.equalsIgnoreCase(pTipoBusqueda);
            }
            operaConsultaClientesJuridico(pJDialog, lstClientes, pValorBusqueda, pTipoBusqueda, isOperarEnRac);
            return true;
        }else if(pAccion.equals(TipoAccionCRUD.INSERTA)){
            return operaRegistroClienteJuridico(pJDialog, cliente, isOperarEnRac);
        }else if(pAccion.equals(TipoAccionCRUD.ACTUALIZA)){
            return operaRegistroClienteJuridico(pJDialog, cliente, isOperarEnRac);
        }
        return false;
    }
    
    private void operaConsultaClientesJuridico(JDialog pJDialog, List<BeanClienteJuridico> lstClientesJuridico, String pValorBusqueda, String pTipoBusqueda, boolean isOperarEnRac){
        //ConstantsCliente.TIPO_BUSQUEDA_RUC.equalsIgnoreCase(pTipoBusqueda)
        //ConstantsCliente.TIPO_BUSQUEDA_RUC.equalsIgnoreCase(pTipoBusqueda)
        log.info("[CONSULTA DE CLIENTES]: CONSULTANDO EN RAC? "+isOperarEnRac);
        FacadeCliente facade = new FacadeCliente();
        List lstCliente = facade.consultarClientes(pValorBusqueda, pTipoBusqueda, isOperarEnRac);
        if(lstCliente!=null){
            if(!lstCliente.isEmpty()){
                for(int i=0; i<lstCliente.size(); i++){
                    BeanClienteJuridico cliente = (BeanClienteJuridico)lstCliente.get(i);
                    lstClientesJuridico.add(cliente);
                    /* ArrayList fila = new ArrayList();
                    fila.add(cliente.getCodCliente());
                    fila.add(cliente.getNumDocumento());
                    fila.add(cliente.getRazonSocial());
                    fila.add(cliente.getDireccion());
                    fila.add(cliente);
                    pTableModel.insertRow(fila); */
                    if(isOperarEnRac){
                        facade.registrarClienteJuridico(cliente, false);
                    }
                }
            }else{
                if(isOperarEnRac){
                    operaConsultaClientesJuridico(pJDialog, lstClientesJuridico, pValorBusqueda, pTipoBusqueda, false);
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
