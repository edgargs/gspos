package mifarma.ptoventa.pinpad.mastercard;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import java.awt.Color;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiloProcesoCierrePinpadMC extends Thread {
    private static final Logger log = LoggerFactory.getLogger(HiloProcesoCierrePinpadMC.class);

    public JLabelWhite lblMensajePinpad;
    public JPanelTitle pnlMensajePinpad;
    public JLabelFunction lblEsc;
    public JLabel lblDatoMensaje;

    public void run() {
        ManejadorTramaRetornoMC mtrmc = new ManejadorTramaRetornoMC();
        Map<String, String> resultado1 = new HashMap<String, String>();
        Map<String, String> resultado2 = new HashMap<String, String>();
        Map<String, String> resultado3 = new HashMap<String, String>();

        try { //se procesa el reporte detallado/cierre
            resultado1 = mtrmc.reporteDetalladoCierre();
            String txt_imprimir1 = resultado1.get("print_data");
            if ("00".equals(resultado1.get("response_code"))) {
                //se procesa el reporte totales/cierre
                resultado2 = mtrmc.reporteTotalesCierre(null);
                String txt_imprimir2 = resultado2.get("print_data");
                if ("00".equals(resultado2.get("response_code"))) {
                    //se procesa el cierre
                    resultado3 = mtrmc.cierre("299999949");
                    String txt_imprimir3 = resultado3.get("print_data");

                    if ("00".equals(resultado3.get("response_code"))) {
                        if (txt_imprimir1 != null && !("".equals(txt_imprimir1)) && txt_imprimir2 != null &&
                            !("".equals(txt_imprimir2)) && txt_imprimir3 != null && !("".equals(txt_imprimir3))) {
                            mtrmc.imprVoucher(txt_imprimir1);
                            mtrmc.imprVoucher(txt_imprimir2);
                            mtrmc.imprVoucher(txt_imprimir3);
                        }
                        lblDatoMensaje.setText(resultado3.get("message"));
                        mtrmc.guardarTramaCierreBD(resultado3);
                        exitoTransaccion();
                    } else {
                        errorTransaccion();
                    }
                }
            } else {
                errorTransaccion();
            }
        } catch (Exception e) {
            errorTransaccion();
            log.error("", e);
        }
    }

    public void exitoTransaccion() {
        lblMensajePinpad.setText("SE REALIZO CORRECTAMENTE EL PROCESO DE CIERRE");
        pnlMensajePinpad.setBackground(new Color(49, 141, 43));
        lblMensajePinpad.setForeground(Color.BLACK);
        lblEsc.setEnabled(true);
    }

    private void errorTransaccion() {
        lblMensajePinpad.setText("ERROR EN EL PROCESO DE CIERRE. INTENTE NUEVAMENTE");
        pnlMensajePinpad.setBackground(Color.RED);
        lblEsc.setEnabled(true);
    }
}
