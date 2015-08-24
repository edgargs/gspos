package mifarma.ptoventa.caja.reference;

import java.util.List;

import mifarma.electronico.UtilityImpCompElectronico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloImpresion extends Thread {
    
    private static final Logger log = LoggerFactory.getLogger(HiloImpresion.class);
    
    private List lstImpresion;
    private boolean pIndImpresionLogo = false;

    public HiloImpresion() {
    }

    public HiloImpresion(List lstImpresion) {
        this.lstImpresion = lstImpresion;
    }

    public void run() {
        try {
            UtilityImpCompElectronico impresion = new UtilityImpCompElectronico();
            boolean rest = impresion.impresionTermica(lstImpresion, null);
                //impresion.impresionDocumentoEnTermica(lstImpresion, pIndImpresionLogo, null, false);
        } catch (Exception ex) {
            log.info(ex.toString());
        }
    }

    public void setImprimirLogo(boolean pIndImpresionLogo) {
        this.pIndImpresionLogo = pIndImpresionLogo;
    }
}
