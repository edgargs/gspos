package printerUtil;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import printerFarma.FarmaPrinterFacade;

public class FrmServicePrinter {
    
    private FarmaPrinterFacade vImpTermica;
    

    public FrmServicePrinter(String tipoImpTermica, String rutaImpTermica) {
        vImpTermica = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", "");
    }
    
    /**
     * Inicio de Servicio de la Impresora
     * @throws Exception
     */
    public void iniServices() throws Exception{
        if (!vImpTermica.startPrintService()) 
            throw new Exception("No se pudo iniciar la Impresión del Documento.\nVerifique su impresora Térmica por favor.");
        else 
            vImpTermica.inicializate();
    }    
    
    public void printList(List vLista,boolean indAbreGabeta){
        Map mapFila = new HashMap();
        for (int i = 0; i < vLista.size(); i++) {
            mapFila = (HashMap)vLista.get(i);
            vImpTermica.printMap(mapFila);
        }
        finalizaServicio(indAbreGabeta);        
    }
    
    public void cortarPapel(){
        vImpTermica.cutPaper();
    }
    
    public void finalizaServicio(boolean indAbreGabeta){
          vImpTermica.endPrintService(indAbreGabeta);
    }
}
