package mifarma.ptoventa.pinpad.mastercard;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import mifarma.ptoventa.pinpad.DlgAnularTransPinpad;
import mifarma.ptoventa.pinpad.reference.TimerMensajeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiloProcesoAnularPinpadMC extends Thread {
    private static final Logger log = LoggerFactory.getLogger(HiloProcesoAnularPinpadMC.class);

    public DlgAnularTransPinpad padre;

    public Double monto;
    public String numReferencia;
    public boolean flagAnulPed = false;
    public JLabel lblFlagAnulDiaLoteCerrado = new JLabel();

    public JPanelWhite pnlFondo;
    public JLabelWhite lblMensajePinpad;
    public JPanelTitle pnlMensajePinpad;
    public JLabelFunction lblEsc;
    public JLabelFunction lblF11;
    public JLabelFunction lblF5;
    public JLabel lblDatoNumAutorizacion;
    public JLabel lblDatoCodVoucher;
    public JLabel lblDatoFecha;
    public JLabel lblDatoPedidoEnv = new JLabel();
    public JLabel lblTimer;

    public String formaPago;

    public void run() {
        ManejadorTramaRetornoMC mtrmc = new ManejadorTramaRetornoMC();
        mtrmc.setLblDatoPedidoEnv(lblDatoPedidoEnv.getText());
        Map<String, String> resultado = new HashMap<String, String>();
        try {
            resultado = mtrmc.anulacionCompra(monto, numReferencia);
            String txt_imprimir = resultado.get("print_data");

            //si es una anulación correcta
            if ((resultado != null) && (VariablesPinpadMC.COD_RESP_OK.equals(resultado.get("response_code")))) {
                lblDatoNumAutorizacion.setText(resultado.get("approval_code"));
                lblDatoCodVoucher.setText(numReferencia);
                if (txt_imprimir != null &&
                    !("".equals(txt_imprimir))) { //se llama dos veces a la funcion para imprimir dos veces el voucher
                    mtrmc.imprVoucher(txt_imprimir);
                    mtrmc.imprVoucher(txt_imprimir);
                }
                mtrmc.guardarTramaAnulacionBD(resultado, formaPago);
                exitoTransaccion("SE REALIZO CORRECTAMENTE LA ANULACION CON EL PINPAD");
            }
            //si el flag de anulacion de pedido es true
            else if (flagAnulPed) { //se verifica si la operacion indica que el lote no existe y que sea del mismo dia

                Calendar fecha_actual = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                String str_fecha_actual = sdf.format(fecha_actual.getTime());

                boolean flag = (str_fecha_actual.equalsIgnoreCase(lblDatoFecha.getText()) &&
                    //(VariablesPinpadMC.COD_RESP_LOTE_NO_EXISTE.equals(resultado.get("response_code")) ||
                    // VariablesPinpadMC.COD_RESP_NO_EXIST_TRX_LOTE.equals(resultado.get("response_code"))))
                    "77".equalsIgnoreCase(resultado.get("response_code")));

                if (flag) {
                    this.lblFlagAnulDiaLoteCerrado.setText("true");
                    //LLEIVA 11-Dic-2013 Se indica el flag de la transacción que no se pudo anular por que el lote se encuentra cerrado
                    preguntarTransaccion("NO SE PUDO ANULAR LA TRANSACCION DEL PINPAD - LOTE CERRADO");
                } else
                    errorTransaccion("ERROR DE TRANSACCIÓN");
            } else if (resultado == null) {
                errorTransaccion("ERROR DE TRANSACCIÓN");
            } else {
                errorTransaccion(resultado.get("message"));
            }
        } catch (Exception e) {
            errorTransaccion("ERROR DE TRANSACCIÓN");
            log.error("", e);
        }
    }

    private void errorTransaccion(String mensaje) {
        lblMensajePinpad.setText(mensaje);
        pnlMensajePinpad.setBackground(Color.RED);
        lblEsc.setEnabled(true);
        lblEsc.grabFocus();
    }

    public void exitoTransaccion(String mensaje) {
        lblMensajePinpad.setText(mensaje);
        pnlMensajePinpad.setBackground(new Color(49, 141, 43));
        lblMensajePinpad.setForeground(Color.BLACK);
        lblF11.setEnabled(true);

        //LLEIVA 18-Feb-2014 Se añade el listener para que espere 10 segundos y cierre la ventana
        TimerMensajeListener tml = new TimerMensajeListener();
        tml.lblMensaje = this.lblTimer;
        tml.padreAnul = this.padre;
        tml.indicador = "A";
        lblF11.addKeyListener(tml);
    }

    public void preguntarTransaccion(String mensaje) {
        lblMensajePinpad.setText(mensaje);
        pnlMensajePinpad.setBackground(Color.YELLOW);
        lblMensajePinpad.setForeground(Color.BLACK);
        lblF5.setEnabled(true);
        lblEsc.setEnabled(true);
        lblEsc.grabFocus();
    }
}
