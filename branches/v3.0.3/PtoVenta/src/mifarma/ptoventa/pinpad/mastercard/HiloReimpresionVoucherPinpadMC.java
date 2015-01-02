package mifarma.ptoventa.pinpad.mastercard;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiloReimpresionVoucherPinpadMC extends Thread {
    private static final Logger log = LoggerFactory.getLogger(HiloReimpresionVoucherPinpadMC.class);

    public JPanelWhite pnlFondo;
    public JLabelWhite lblMensajePinpad;
    public JPanelTitle pnlMensajePinpad;
    public JLabelFunction lblEsc;
    public JLabelFunction lblF11;
    public JTextFieldSanSerif txtNumReferencia;

    public String numReferencia;

    public void run() {
        ManejadorTramaRetornoMC mtrmc = new ManejadorTramaRetornoMC();
        Map<String, String> resultado = new HashMap<String, String>();
        try {
            resultado = mtrmc.reimpresion(numReferencia);
            String txt_imprimir = resultado.get("print_data");

            if (resultado != null && "00".equals(resultado.get("response_code"))) {
                if (txt_imprimir != null && !("".equals(txt_imprimir)))
                    mtrmc.imprVoucher(txt_imprimir);
                mtrmc.guardarTramaReimpresionBD(resultado);
                exitoTransaccion();
            } else {
                errorTransaccion();
            }
        } catch (Exception ex) {
            resultado = null;
            log.error("", ex);
        }
        pnlFondo.grabFocus();
    }

    private void exitoTransaccion() {
        lblMensajePinpad.setText("SE RETORNARON LOS DATOS PARA LA REIMPRESIÓN DEL VOUCHER");
        pnlMensajePinpad.setBackground(new Color(49, 141, 43));
        lblMensajePinpad.setForeground(Color.BLACK);
        txtNumReferencia.setEnabled(true);
        lblEsc.setEnabled(true);
    }

    private void errorTransaccion() {
        lblMensajePinpad.setText("ERROR EN EL PROCESO DE RETORNO. INTENTE NUEVAMENTE");
        pnlMensajePinpad.setBackground(Color.RED);
        txtNumReferencia.setEnabled(true);
        lblEsc.setEnabled(true);
    }
}
