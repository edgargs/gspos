package mifarma.ptoventa.pinpad.mastercard;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiloReporteDetalladoPinpadMC extends Thread {
    private static final Logger log = LoggerFactory.getLogger(HiloReporteDetalladoPinpadMC.class);

    public JLabelWhite lblMensaje = new JLabelWhite();
    public JPanelTitle pnlMensaje = new JPanelTitle();
    public JPanelWhite pnlFondo = new JPanelWhite();
    public JLabelFunction lblEsc = new JLabelFunction();

    public void run() {
        ManejadorTramaRetornoMC mtrmc = new ManejadorTramaRetornoMC();
        Map<String, String> resultado = new HashMap<String, String>();
        try {
            resultado = mtrmc.reporteDetallado();
            String txt_imprimir = resultado.get("print_data");

            if (resultado != null && "00".equals(resultado.get("response_code"))) {
                if (txt_imprimir != null && !("".equals(txt_imprimir)))
                    mtrmc.imprVoucher(txt_imprimir);
                mtrmc.guardarTramaRepDetalladoBD(resultado);
                exitoTransaccion();
            } else {
                errorTransaccion();
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
        pnlFondo.grabFocus();
    }

    private void exitoTransaccion() {
        lblMensaje.setText("SE RETORNARON LOS DATOS PARA EL REPORTE DE LOTE");
        pnlMensaje.setBackground(new Color(49, 141, 43));
        lblMensaje.setForeground(Color.BLACK);
        lblEsc.setEnabled(true);
    }

    private void errorTransaccion() {
        lblMensaje.setText("ERROR EN EL PROCESO DE REPORTE DE LOTE. INTENTE NUEVAMENTE");
        pnlMensaje.setBackground(Color.RED);
        lblEsc.setEnabled(true);
    }
}
