package mifarma.ptoventa.pinpad.reference;

import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.pinpad.visa.HiloProcesoAnularPinpadVisa;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloImpresion extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(HiloImpresion.class);
    public String textoImpr = "";
    
    public void run()
    {   //si existe algun problema con la impresora, el flujo de cobro de pedido continua
        PrintConsejo.imprimirHtml(textoImpr.toString(),
                                  VariablesPtoVenta.vImpresoraActual,
                                  VariablesPtoVenta.vTipoImpTermicaxIp);
    }
}
