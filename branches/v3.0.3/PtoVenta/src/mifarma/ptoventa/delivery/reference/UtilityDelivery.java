package mifarma.ptoventa.delivery.reference;

import java.awt.Frame;

import java.util.ArrayList;

import mifarma.ptoventa.reportes.DlgDetalleRegistroVentas;
import mifarma.ptoventa.reportes.reference.VariablesReporte;


public class UtilityDelivery {

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
}
