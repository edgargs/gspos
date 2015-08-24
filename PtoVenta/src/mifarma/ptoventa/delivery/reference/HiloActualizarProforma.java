package mifarma.ptoventa.delivery.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiloActualizarProforma extends Thread {

    private static final Logger log = LoggerFactory.getLogger(HiloActualizarProforma.class);
    private FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
    private String pNumPedVta, pCodLocal, pCodGrupoCia;

    public HiloActualizarProforma() {
    }

    public HiloActualizarProforma(String pNumPedVta, String pCodLocal, String pCodGrupoCia) {
        this.pNumPedVta = pNumPedVta;
        this.pCodLocal = pCodLocal;
        this.pCodGrupoCia = pCodGrupoCia;
    }

    public void run() {
        try {
            //String resMatriz = facadeConvenioBTLMF.grabarTemporalesRAC(VariablesCaja.vNumPedVta);
            //if (resMatriz.equals("S")) {
            List listaComprobantes = new ArrayList();
            listaComprobantes = DBDelivery.getComprobanteProforma(pCodGrupoCia, pCodLocal, pNumPedVta);

            if (listaComprobantes.size() > 0) {
                Map mapComprobante = new HashMap();
                String cia, local, proforma, localSap, comprobante, fecha;

                for (int i = 0; i < listaComprobantes.size(); i++) {
                    mapComprobante = (HashMap)listaComprobantes.get(i);
                    cia = ((String)mapComprobante.get("CIA"));
                    local = ((String)mapComprobante.get("LOCAL"));
                    proforma = ((String)mapComprobante.get("PROFORMA"));
                    localSap = ((String)mapComprobante.get("LOCAL_SAP"));
                    comprobante = ((String)mapComprobante.get("COMPROBANTES"));
                    fecha = ((String)mapComprobante.get("FECHA"));
                    if (!comprobante.equalsIgnoreCase("N")) {
                        log.info("graba estado de la proforma ");
                        facadeConvenioBTLMF.actualizaProformaRAC(cia, local, proforma, localSap, comprobante,
                                                                 fecha); //pNumPedVta, pCodLocal, pCodGrupoCia);
                        log.info("termino de grabar ");
                    } else {
                        log.info("NO INGRESA ES MENOR AL TAB GRAL");
                    }
                }
            }
        } catch (Exception ex) {
            log.info(ex.toString());
        }
    }
}
