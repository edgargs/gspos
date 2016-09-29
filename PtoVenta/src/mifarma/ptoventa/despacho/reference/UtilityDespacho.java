package mifarma.ptoventa.despacho.reference;

import java.awt.Frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.administracion.impresoras.DlgListaImpresoraTermica;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.delivery.DlgUltimosPedidos.ConstantsOperacionProforma;
import mifarma.ptoventa.matriz.mantenimientos.productos.DlgListado;
import mifarma.ptoventa.mayorista.reference.DBMayorista;
import mifarma.ptoventa.proforma.reference.DBProforma;
import mifarma.ptoventa.proforma.reference.UtilityProforma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityDespacho {
    
    private final int COMANDA_DESPACHO = 1;
    private final int COMANDA_VENTA = 2;
    private final int COMANDA_TRANSFERENCIA = 3;
    
    private static final Logger log = LoggerFactory.getLogger(UtilityDespacho.class);
    
    /**
     * @author KMONCADA
     * @since 08.06.2016 SE AGREGA LA IMPRESION DE CONSTANCIA DE PAGO
     * @param pJDialog
     * @param nroProforma
     * @param pEstadoProforma
     * @throws Exception
     */
    public void enviarProformaADespacho(JDialog pJDialog, String nroProforma, String pEstadoProforma)throws Exception{
        DBProforma.asignarLoteProductoProforma(nroProforma);
        DBProforma.reservarStockTemporal(nroProforma);
        imprimirComandaDespacho(pJDialog, nroProforma, COMANDA_DESPACHO);
        DBProforma.actualizarEstadoProforma(nroProforma, pEstadoProforma);
    }
    
    public void imprimirComandaDespachoVenta(JDialog pJDialog, String pNroPedVta)throws Exception{
        imprimirComandaDespacho(pJDialog, pNroPedVta, COMANDA_VENTA);
    }
    
    public void imprimirComandaTransferenciaDespacho(JDialog pJDialog, String pNroPedVta)throws Exception{
        imprimirComandaDespacho(pJDialog, pNroPedVta, COMANDA_TRANSFERENCIA);
    }
    
    /**
     * @author KMONCADA
     * @since 08.06.2016
     * @update KMONCADA 08.08.2016 [PROYECTO M] SE AGREGA PARAMETRO TIPOCOMANDA PARA IDENTIFICAR QUE TIPO SE VA A IMPRIMIR
     * @param pJDialog
     * @param pNroPedVta
     * @param tipoComanda
     * @throws Exception
     */
    public void imprimirComandaDespacho(JDialog pJDialog, String pNroPedVta, int tipoComanda)throws Exception{
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
                                                                lstPisos.size(), 
                                                                tipoComanda);
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
    
    public void reimprimirComandaDespacho(Frame myParentFrame, JDialog pJDialog, String nroProforma, String tipoOperacionProforma){
        int tipoComanda = 0;
        if(ConstantsOperacionProforma.VERIFICA_PROD_PROFORMA.equalsIgnoreCase(tipoOperacionProforma)){
            tipoComanda = COMANDA_DESPACHO;
        }
        if(ConstantsOperacionProforma.COBRO_PROFORMA.equalsIgnoreCase(tipoOperacionProforma)){
            tipoComanda = COMANDA_TRANSFERENCIA;
        }
        try{
            List lstPisos = DBDespacho.obtenerImpresorasParaDespacho(FarmaVariables.vCodGrupoCia, 
                                                                     FarmaVariables.vCodLocal, 
                                                                     nroProforma);
            DlgListado dlgListado = new DlgListado(myParentFrame, "Listado de Comandas", true, 1);
            dlgListado.cargarDatosTabla(lstPisos);
            dlgListado.setVisible(true);
            if(FarmaVariables.vAceptar){
                ArrayList select = dlgListado.getRowSelected();
                
                DlgListaImpresoraTermica dlglstImpresora = new DlgListaImpresoraTermica(myParentFrame, "", true);
                dlglstImpresora.setIndTipoListado(1);
                dlglstImpresora.setVisible(true);
                if(FarmaVariables.vAceptar){
                    ArrayList selectImpresora = dlglstImpresora.getRowSelected();
                    log.info("[PROYECTO M - IMPRESION DE COMANDA ALMACEN] CANTIDAD DE PISOS A REPORTAR ");
                    //1 [nombre]-3[tipo]
                    int pisoSeleccion = Integer.parseInt((String)select.get(2));
                    Map piso = (HashMap)lstPisos.get(pisoSeleccion);
                    piso.put("TIPO",(String)selectImpresora.get(3));
                    piso.put("IMPRESORA",(String)selectImpresora.get(1));
                    
                    log.info("[IMPRESION DE COMANDA DESPACHO] OBTENER PRODUCTOS DEL PISO : " + pisoSeleccion);
                    List lstComanda = DBDespacho.obtenerComandaDespacho(FarmaVariables.vCodGrupoCia, 
                                                                        FarmaVariables.vCodLocal, 
                                                                        nroProforma, 
                                                                        (String)piso.get("PISO"), 
                                                                        (pisoSeleccion+1), 
                                                                        lstPisos.size(),
                                                                        tipoComanda);
                    try {
                        imprimeComandaDespacho(lstComanda, (HashMap)lstPisos.get(pisoSeleccion));
                    }catch(Exception ex){
                        FarmaUtility.showMessage(pJDialog, ex.getMessage(), null);
                    }
                }
            }
        }catch(Exception ex){
            log.error("", ex);
        }
    }
    
    /**
     * [PROYECTO M] PERMITIRA REGISTRO DE AJUSTE AUTOMATICO DE KARDEX EN EL DESPACHO
     * @return
     */
    public boolean isPermiteAjusteAutomaticoDespacho(){
        boolean isPermite = false;
        try{
            String rspta = DBMayorista.getPermiteAjusteAutomaticoDespacho();
            isPermite = "S".equalsIgnoreCase(rspta);
        }catch(Exception ex){
            log.error("", ex);
            isPermite = false;
        }
        return isPermite;
    }
}
