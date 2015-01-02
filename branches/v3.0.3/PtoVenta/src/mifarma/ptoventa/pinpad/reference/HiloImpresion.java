package mifarma.ptoventa.pinpad.reference;

import java.util.ArrayList;
import java.util.List;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiloImpresion extends Thread {
    private static final Logger log = LoggerFactory.getLogger(HiloImpresion.class);
    public String textoImpr = "";
    public List lstimpresion = new ArrayList();

    public void run() { //si existe algun problema con la impresora, el flujo de cobro de pedido continua
        log.debug("Texto a IMPRIMIR POR TRANSACCION:---->   ", textoImpr.toString());

        if (textoImpr.length() > 0) {
            PrintConsejo.imprimirHtml(textoImpr.toString(), VariablesPtoVenta.vImpresoraActual,
                                      VariablesPtoVenta.vTipoImpTermicaxIp);
            log.debug("******----Termino imprimir voucher----******");
        }
        try {
            log.info("IMPRESION DE VOUCHER NUEVO");
            if (lstimpresion != null && lstimpresion.size() > 0) {
                UtilityImpCompElectronico impresion = new UtilityImpCompElectronico();
                boolean rest =
                    impresion.impresionDocumentoEnTermica(lstimpresion, VariablesPtoVenta.vTipoImpTermicaxIp,
                                                          VariablesPtoVenta.vImpresoraActual.getName(), false, null,
                                                          false);
            }
            log.info("IMPRESION DE VOUCHER NUEVO FIN");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
