package mifarma.ptoventa.delivery.reference;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.caja.DlgNuevoCobro;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.DlgUltimosPedidos;
import mifarma.ptoventa.proforma.reference.DBProforma;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.DlgDetalleRegistroVentas;
import mifarma.ptoventa.reportes.reference.VariablesReporte;
import mifarma.ptoventa.ventas.DlgNumeroPedidoGenerado;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityDelivery {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityDelivery.class);

    public UtilityDelivery() {
        super();
    }

    public static String resumenPedidoVentas(Frame myParentFrame, String pCorrelativo, String pCliente, String pRuc,
                                             String pFecha, String pHora, String pUsuario, String pEstado,
                                             ArrayList<ArrayList<String>> formasPagoDelivery, String pComprobante,
                                             String pMotorizado, boolean verDelivery, String pObservaciones) {
        VariablesReporte.vCorrelativo = pCorrelativo;
        VariablesReporte.vCliente = pCliente;
        //VariablesReporte.vDireccion = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),7)).trim();
        VariablesReporte.vRuc = pRuc;
        VariablesReporte.vFecha = pFecha;
        VariablesReporte.vHora = pHora;
        VariablesReporte.vUsuario = pUsuario;
        VariablesReporte.vEstado = pEstado;

        /*
        VariablesDelivery.vNombreCliente
        VariablesDelivery.vDireccion
        VariablesDelivery.vReferencia
        */

        DlgDetalleRegistroVentas dlgDetalleRegistroVentas = new DlgDetalleRegistroVentas(myParentFrame, "", true);
        dlgDetalleRegistroVentas.setIndResumenDelivery(true);
        dlgDetalleRegistroVentas.setLstFormasPago(formasPagoDelivery);
        dlgDetalleRegistroVentas.setLblComprobante(pComprobante);
        dlgDetalleRegistroVentas.setLblMotorizado(pMotorizado);
        dlgDetalleRegistroVentas.setIndVerDelivery(verDelivery);
        dlgDetalleRegistroVentas.setObserPedidoVta(pObservaciones);
        dlgDetalleRegistroVentas.setVisible(true);

        return dlgDetalleRegistroVentas.getObserPedidoVta();
    }
    
    /**
     * @author ERIOS
     * @since 09.02.2016
     * @param myParentFrame
     * @param pJDialog
     * @param pObject
     * @param pNumeroPedido
     * @param pCodLocal
     * @param tipoVenta
     * @param pValNetoPed
     * @param isFlujoB
     * @param indConv
     * @param CodConvPedVta
     * @param dlg
     * @return
     */
    public static boolean generaPedidoInstitucionalAutomatico(Frame myParentFrame, JDialog pJDialog, Object pObject, 
                                                              String pNumeroPedido, String pCodLocal, String tipoVenta, String pValNetoPed, boolean isFlujoB,
                                                              String indConv, String CodConvPedVta, DlgUltimosPedidos dlg) {
        String estadoPedido = "";
        VariablesDelivery.vNumeroPedido = pNumeroPedido;
        VariablesDelivery.vCodLocal = pCodLocal;
        try {
            estadoPedido = obtieneEstadoPedido_ForUpdate(pJDialog, pObject, pNumeroPedido, pCodLocal);
            if(UtilityPtoVenta.isLocalVentaMayorista() && ConstantsVentas.TIPO_PEDIDO_MESON.equals(tipoVenta) && !isFlujoB){
                if (!estadoPedido.equalsIgnoreCase(ConstantsVentas.ESTADO_PEDIDO_SIN_COMPROBANTE)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente. Verifique!!!", pObject);
                    //actualizaListaPedidos();
                    return false;
                }
                DBCaja.reinicializaProformaAutomatico(VariablesDelivery.vCodLocal, VariablesDelivery.vNumeroPedido);
            }else{
                if (!estadoPedido.equalsIgnoreCase(ConstantsVentas.ESTADO_PEDIDO_PENDIENTE)) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente. Verifique!!!", pObject);
                    //actualizaListaPedidos();
                    return false;
                }
            }
            
            
            VariablesVentas.vNum_Ped_Vta = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO, 10);
            VariablesVentas.vNum_Ped_Diario = obtieneNumeroPedidoDiario();
            
            VariablesVentas.vVal_Neto_Ped = pValNetoPed;
            DBDelivery.generaPedidoInstitucionalAutomatico(VariablesDelivery.vIndProformaMenorStock,isFlujoB);
            //DBDelivery.actualuizaValoresDa(); // KMONCADA 10.07.14 pedido vta empresa sin redondeo
            FarmaUtility.aceptarTransaccion();
            //KMONCADA 03.07.2014 NO BORRE EL CONTENIDO DE LA TABLA DE LOTES TEMPORALES
            //eliminaVtaInstiProd();

            // -- Se modifico para caja multifuncional
            // dubilluz  02/05/2008
            if (FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) ||
                    ConstantsVentas.TIPO_PEDIDO_MESON.equals(tipoVenta)) {
                VariablesCaja.vNumPedPendiente = VariablesVentas.vNum_Ped_Diario;
                if(isFlujoB){
                    mostrarNuevoCobro(myParentFrame,dlg);
                }else{
                    muestraCobroPedido(myParentFrame,tipoVenta,indConv,CodConvPedVta,dlg);
                }
            } else {
                muestraPedidoRapido(myParentFrame);
            }
            return true;
        } catch (SQLException sqlex) {
            FarmaUtility.liberarTransaccion();
            log.error("", sqlex);
            FarmaUtility.showMessage(pJDialog, "Error al grabar el pedido institucional automático - \n" +
                    sqlex, pObject);
            return false;
        }
    }
    
    /**
     * @author ERIOS
     * @since 09.02.2016
     * @param pJDialog
     * @param pObject
     * @param pNumeroPedido
     * @param pCodLocal
     * @return
     */
    private static String obtieneEstadoPedido_ForUpdate(JDialog pJDialog, Object pObject, String pNumeroPedido, String pCodLocal) {
        String estadoPedido = "";
        try {
            estadoPedido = DBDelivery.obtieneEstadoPedido_ForUpdate(pNumeroPedido, pCodLocal);
        } catch (SQLException sqlex) {
            FarmaUtility.liberarTransaccion();
            log.error("", sqlex);
            estadoPedido = "";
            FarmaUtility.showMessage(pJDialog, "Error al obtener estado del pedido - \n" +
                    sqlex, pObject);
        }
        return estadoPedido;
    }
    
    /**
     * @author ERIOS
     * @since 09.02.2016
     * @return
     * @throws SQLException
     */
    public static String obtieneNumeroPedidoDiario() throws SQLException {

        String feModNumeracion = DBVentas.obtieneFecModNumeraPed();
        String feHoyDia = "";
        String numPedDiario = "";
        if (!(feModNumeracion.trim().length() > 0))
            throw new SQLException("Ultima Fecha Modificacion de Numeración Diaria del Pedido NO ES VALIDA !!!",
                                   "Error", 0001);
        else {
            feHoyDia = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            feHoyDia = feHoyDia.trim().substring(0, 2);
            feModNumeracion = feModNumeracion.trim().substring(0, 2);
            if (Integer.parseInt(feHoyDia) != Integer.parseInt(feModNumeracion)) {
                FarmaSearch.inicializaNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
                numPedDiario = "0001";
            } else {
                // Obtiene el Numero de Atencion del Día y hace SELECT FOR UPDATE.
                numPedDiario = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO, 4);
            }
        }
        return numPedDiario;
    }
    
    /**
     * @author ERIOS
     * @since 09.02.2016
     * @param myParentFrame
     */
    public static void muestraPedidoRapido(Frame myParentFrame) {
        DlgNumeroPedidoGenerado dlgNumeroPedidoGenerado = new DlgNumeroPedidoGenerado(myParentFrame, "", true);
        dlgNumeroPedidoGenerado.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            //actualizaListaPedidos();
        }
    }
    
    /**
     * @author ERIOS
     * @since 09.02.2016
     * @param myParentFrame
     * @param tipoVenta
     * @param indConv
     * @param CodConvPedVta
     * @param dlg
     */
    public static void muestraCobroPedido(Frame myParentFrame, String tipoVenta, String indConv, String CodConvPedVta, DlgUltimosPedidos dlg) {
        
        if (indConv.equalsIgnoreCase("S")) {
            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
            VariablesConvenioBTLMF.vCodConvenio = CodConvPedVta;
        }

        DlgFormaPago dlgFormaPago = new DlgFormaPago(myParentFrame, "", true);
        dlgFormaPago.setIndPedirLogueo(false);
        dlgFormaPago.setIndPantallaCerrarAnularPed(true);
        dlgFormaPago.setIndPantallaCerrarCobrarPed(true);
        // KMONCADA 19.01.2015 SE REALIZA EL COBRO AUTOMATICAMENTE SIMULA PRESIONAR F11
        dlgFormaPago.setCobrarAutomaticamente(true);
        //ERIOS 12.01.2016 Proformas de local-mayorista
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            dlgFormaPago.setEntregaProforma(true);
        }
        dlgFormaPago.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            if(dlg != null){
                dlg.cerrarVentana(true);
            }
        }else{
            try{
                DBProforma.actualizarEstadoProforma(VariablesDelivery.vNumeroPedido, "S");
                FarmaUtility.aceptarTransaccion();
            }catch(Exception ex){
                log.error("", ex);
            }
            
            
        }
    }
    
    /**
     * @author ERIOS
     * @since 09.02.2016
     * @param myParentFrame
     * @param dlg
     */
    public static void mostrarNuevoCobro(Frame myParentFrame, DlgUltimosPedidos dlg) {
        DlgNuevoCobro dlgNuevoCobro = new DlgNuevoCobro(myParentFrame, "", true);

        dlgNuevoCobro.setIndPedirLogueo(false);
        dlgNuevoCobro.setIndPantallaCerrarAnularPed(true);
        dlgNuevoCobro.setIndPantallaCerrarCobrarPed(true);
        dlgNuevoCobro.setEntregaProforma(true);
        dlgNuevoCobro.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            if(dlg != null){
                dlg.cerrarVentana(true);
            }
        }
    }
}
