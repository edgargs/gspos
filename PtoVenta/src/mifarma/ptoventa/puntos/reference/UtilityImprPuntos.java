package mifarma.ptoventa.puntos.reference;

import java.util.List;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.imptermica.UtilPrinterPtoventa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityImprPuntos {

    private static final Logger log = LoggerFactory.getLogger(UtilityImprPuntos.class);

    
    public UtilityImprPuntos() {
    }
    
    /**
     * Impresion de Saldo de Puntos
     * @param pDialog
     * @param pObjFoco
     * @param numTarjEnmascarada
     * @param nombreCompleto
     * @param puntosAcumulados
     */
    public static void imprimeSaldo(JDialog pDialog, Object pObjFoco, String numTarjEnmascarada, String nombreCompleto,
                                    double puntosAcumulados) {
        try {
            log.info("Instancia la clase Impresora UtilPrinterPtoventa");
            boolean vExistoImpresora = false;
            UtilPrinterPtoventa vPrintTermica = null;
            try {
                vPrintTermica = new UtilPrinterPtoventa();
                vExistoImpresora = true;
            } catch (Exception e) {
                vExistoImpresora = false;
                // TODO: Add catch code
                log.error("",e);
            }
            if (vExistoImpresora) {
                log.info("Finaliza de crear Impresora UtilPrinterPtoventa ");
                if (puntosAcumulados < 0)
                    puntosAcumulados = 0.00;
                //List vListaDatos = DBPuntos.getDatosImprSaldo(numTarjEnmascarada, nombreCompleto,puntosAcumulados);
                //log.info("Obtuvo los datos de IMPRESION " + vListaDatos);
                //vPrintTermica.printList(vListaDatos,false);
                List vListaDatosNew = DBPuntos.getDatosImprSaldo(numTarjEnmascarada, nombreCompleto, puntosAcumulados);
                (new UtilityImpCompElectronico()).impresionTermica(vListaDatosNew, null);
                log.info("Finaliza Impresion");
            } else {
                FarmaUtility.showMessage(pDialog, "No esta asignada o instalada la impresora." + "\n" +
                        "Por favor de Comunicarse con Mesa de Ayuda.", pObjFoco);

                throw new Exception("No esta asignada o instalada la impresora");
            }

        } catch (Exception e) {
            log.error("",e);
            FarmaUtility.showMessage(pDialog, "Error en la Impresión de Saldo" + "\n" +
                    "*****************************************" + "\n" +
                    e.getMessage() + "\n" +
                    "*****************************************" + "\n" +
                    "Por favor de Comunicarse con Mesa de Ayuda.", pObjFoco);
            log.error("",e);
        }
    }


    public static void imprimeRecuperaPuntos(JDialog pDialog, Object pObjFoco, String numTarjEnmascarada,
                                             String nombreCompleto, String pDNI, double puntosAcumulados,
                                             boolean pIndOnline, double pPtoSaldoAct, String pNumPedVta) {
        try {
            /*log.info("Instancia la clase Impresora UtilPrinterPtoventa");
          UtilPrinterPtoventa vPrintTermica =  new UtilPrinterPtoventa();
          log.info("Finaliza de crear Impresora UtilPrinterPtoventa ");
          List vListaDatos = DBPuntos.getDatosImprRecuperaPuntos(numTarjEnmascarada, nombreCompleto,pDNI,puntosAcumulados,pIndOnline,pPtoSaldoAct,pNumPedVta);
          log.info("Obtuvo los datos de IMPRESION "+vListaDatos);
          //vPrintTermica.printList(vListaDatos,false);
          log.info("Finaliza Impresion");
           */
            log.info("Instancia la clase Impresora UtilPrinterPtoventa");
            List vListaDatos2 = DBPuntos.getDatosImprRecuperaPuntos(numTarjEnmascarada, nombreCompleto, pDNI, puntosAcumulados, pIndOnline, pPtoSaldoAct, pNumPedVta);
            (new UtilityImpCompElectronico()).impresionTermica(vListaDatos2, null);
        } catch (Exception e) {
            log.error("",e);
            log.info("ERROR - imprimeRecuperaPuntos - ");
            FarmaUtility.showMessage(pDialog, "Error en la Impresión de Recuperación de Puntos" + "\n" +
                                              "*****************************************" + "\n" +
                                              e.getMessage() + "\n" +
                                              "*****************************************" + "\n" +
                                              "Por favor de Comunicarse con Mesa de Ayuda.", pObjFoco);
            log.error("",e);
        }
    }
    
}
