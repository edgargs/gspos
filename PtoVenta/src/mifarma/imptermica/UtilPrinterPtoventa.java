package mifarma.imptermica;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;

import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerFarma.FarmaPrinterFacade;
import printerUtil.FrmServicePrinter;


public class UtilPrinterPtoventa {
     
    private static final Logger log = LoggerFactory.getLogger(UtilPrinterPtoventa.class);
    private FrmServicePrinter vImpTermica;
    
    private String tipoImpTermica = "";
    private String rutaImpTermica = "";
    private String nomImpTermica  = "";
    
    public UtilPrinterPtoventa()throws Exception {
        cargarDatosImpresoras();
        log.info("tipoImpTermica>"+tipoImpTermica);
        log.info("rutaImpTermica>"+rutaImpTermica);
        vImpTermica = new FrmServicePrinter(tipoImpTermica, rutaImpTermica);
        iniServices();
    }
    
    public void iniServices() throws Exception{
            vImpTermica.iniServices();
    }
    
    private void cargarDatosImpresoras()throws Exception{
        List lstDatosImpresora = DBImpresoras.getDatosImpresoraTerminca(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, FarmaVariables.vIpPc);
        if(lstDatosImpresora.size()>0){
            Map datoImpresora  = (HashMap)lstDatosImpresora.get(0);
            tipoImpTermica = (String)datoImpresora.get("TIPO");
            nomImpTermica = (String)datoImpresora.get("IMPRESORA");
            rutaImpTermica = getRuta(nomImpTermica);
        }else{
            throw new Exception("Error al consultar datos de impresora termica.\n Verifique configuración en \"Mantenimiento de Impresoras\".");
        }
    }

    public void printList(List list, boolean pAbreGabeta) {
        vImpTermica.printList(list, pAbreGabeta);
    } 

    public String getRuta(String vNameImpresora) throws SQLException {
        String ruta = null;
        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null, null);

        if (servicio != null) {
            try {
                for (int i = 0; i < servicio.length; i++) {
                    PrintService impresora = servicio[i];
                    String pRuta = impresora.getName().toString().trim();
                    String pNombre = UtilityCaja.retornaUltimaPalabra(pRuta, "\\");

                    if (pNombre.trim().toUpperCase().equalsIgnoreCase(vNameImpresora.trim().toUpperCase())) {
                        boolean resultado = pRuta.startsWith("\\");
                        if (resultado) {
                            ruta = pRuta;
                        } else {
                            ruta = "\\" + "\\" + FarmaVariables.vIpPc + "\\" + pRuta;
                        }

                    }
                }

            } catch (Exception e) {
                log.error("", e);
            }
        }
        return ruta;
    }    
    
}
