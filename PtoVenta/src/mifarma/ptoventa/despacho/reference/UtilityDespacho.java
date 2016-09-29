package mifarma.ptoventa.despacho.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityDespacho {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityDespacho.class);
    
    /**
     *
     * @param pJDialog
     * @param pNroPedVta
     * @throws Exception
     */
    public void imprimirComandaDespacho(JDialog pJDialog, String pNroPedVta)throws Exception{
        List lstPisos = DBDespacho.obtenerImpresorasParaDespacho(FarmaVariables.vCodGrupoCia, 
                                                                 FarmaVariables.vCodLocal, 
                                                                 pNroPedVta);
        log.info("[IMPRESION DE COMANDA DESPACHO] CANTIDAD DE PISOS A REPORTAR "+lstPisos.size());
        for(int i=0; i<lstPisos.size(); i++){
            Map piso = (HashMap)lstPisos.get(i);
            String nroPiso = (String)piso.get("PISO");
            log.info("[IMPRESION DE COMANDA DESPACHO] OBTENER PRODUCTOS DEL PISO : " + nroPiso);
            List lstComanda = DBDespacho.obtenerComandaDespacho(FarmaVariables.vCodGrupoCia, 
                                                                FarmaVariables.vCodLocal, 
                                                                pNroPedVta, 
                                                                nroPiso, 
                                                                (i+1), 
                                                                lstPisos.size());
            try {
                imprimeComandaDespacho(lstComanda, (HashMap)lstPisos.get(i));
            }catch(Exception ex){
                FarmaUtility.showMessage(pJDialog, ex.getMessage(), null);
            }
            
            /*if (lstComanda != null) {
                String tipoImpTermica = (String)piso.get("TIPO");
                String nomImpTermica = (String)piso.get("IMPRESORA");
                log.info("[PROYECTO M] COMANDA A IMPRIMIR : TIPO DE IMPRESORA" + tipoImpTermica+" NOMBRE DE IMP.TERMICA: "+nomImpTermica);
                if((tipoImpTermica != null && tipoImpTermica.trim().length()>0)&& (nomImpTermica != null && nomImpTermica.trim().length()>0)){
                    try {
                        new UtilityImpCompElectronico(tipoImpTermica, nomImpTermica).impresionTermica(lstComanda, null);
                    } catch (Exception ex) {
                        log.info("[PROYECTO M] ERROR EN LA IMPRESIO DE COMANDA PARA DESPACHO : " + ex.getMessage());
                        log.error("", ex);
                        FarmaUtility.showMessage(pJDialog, "IMPRESION DE COMANDA DESPACHO:\n"+
                                                           "IMPRESORA : "+nomImpTermica+"\n"+
                                                           "NO SE PUDO IMPRIMIR LA COMANDAD DEL PISO "+nroPiso+".\n"+
                                                           ex.getMessage()+"\n"+
                                                           "REALICE LA IMPRESION EN EL MODULO DE DESPACHO.", null);
                    }
                }
            } else {
                FarmaUtility.showMessage(pJDialog, "IMPRESION DE COMANDA DE DESPACHO:\n"+
                                                   "NO SE PUDO IMPRIMIR LA COMANDAD DEL PISO "+nroPiso+".\n"+
                                                   "DEBIDO A QUE EL PISO NO TIENE IMPRESORA TERMICA ASIGNADA.\n\n"+
                                                   "REALICE LA IMPRESION EN EL MODULO DE DESPACHO.", null);
            }*/
        }
    }
    
    private void imprimeComandaDespacho(List lstComanda, Map piso) throws Exception{
        String nroPiso = (String)piso.get("PISO");
        if (lstComanda != null) {
            String tipoImpTermica = (String)piso.get("TIPO");
            String nomImpTermica = (String)piso.get("IMPRESORA");
            log.info("[IMPRESION COMANDA DE DESPACHO] COMANDA A IMPRIMIR : TIPO DE IMPRESORA" + tipoImpTermica+" NOMBRE DE IMP.TERMICA: "+nomImpTermica);
            if((tipoImpTermica != null && tipoImpTermica.trim().length()>0)&& (nomImpTermica != null && nomImpTermica.trim().length()>0)){
                try {
                    UtilityImpCompElectronico impresoraTermica = new UtilityImpCompElectronico(tipoImpTermica, nomImpTermica);
                    impresoraTermica.impresionTermica(lstComanda, null);
                } catch (Exception ex) {
                    log.info("[IMPRESION COMANDA DE DESPACHO] ERROR EN LA IMPRESION DE COMANDA: " + ex.getMessage());
                    log.error("", ex);
                    throw new Exception("IMPRESION DE COMANDA DESPACHO:\n"+
                                        "IMPRESORA : "+nomImpTermica+"\n"+
                                        "NO SE PUDO IMPRIMIR LA COMANDAD DEL PISO "+nroPiso+".\n"+
                                        ex.getMessage()+"\n");
                }
            }
        } else {
            throw new Exception("IMPRESION DE COMANDA DE DESPACHO:\n"+
                                "NO SE PUDO IMPRIMIR LA COMANDAD DEL PISO "+nroPiso+".\n"+
                                "DEBIDO A QUE EL PISO NO TIENE IMPRESORA TERMICA ASIGNADA.");
        }
    }
    
    public ArrayList obtenerPisoDespacho(JDialog pJDialog){
        ArrayList lstPisos = new ArrayList();
        try{
            DBDespacho.obtenerPisoDespacho(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, lstPisos);
        }catch(Exception ex){
            FarmaUtility.showMessage(pJDialog, "Error al cargar Pisos para despacho de productos.", null);
            lstPisos = new ArrayList();
            log.error("", ex);
        }
        return lstPisos;
    }
}
