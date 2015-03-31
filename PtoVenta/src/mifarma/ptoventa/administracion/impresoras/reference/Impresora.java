package mifarma.ptoventa.administracion.impresoras.reference;

import java.io.PrintStream;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import mifarma.common.FarmaPrintService;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Impresora {

    private static final Logger log = LoggerFactory.getLogger(Impresora.class);
    private PrintStream ps;

    public Impresora() {
        super();
    }

    private ArrayList<String> generarPlantilla(int pCols, int pFilas) {
        ArrayList<String> vPrint = new ArrayList<>();
        String linea = "";
        StringBuilder strB = new StringBuilder();
        int k = 0;
        for (int j = 1; j <= pCols; j++) {
            ++k;
            if (k == 10)
                k = 0;
            strB.append((k == 1 || k == 0) ? k : ".");
        }
        linea = strB.toString();
        for (int i = 1; i <= pFilas; i++) {
            vPrint.add(linea);
        }
        return vPrint;
    }

    private boolean imprimirGenerico(ArrayList<String> pTextos, String pRutaImpresora, int vlinea) {
        FarmaPrintService vPrint = new FarmaPrintService(vlinea, pRutaImpresora, false);

        if (!vPrint.startPrintService()) {
            return false;
        } else {
            vPrint.activateCondensed();
            for (String linea : pTextos) {
                vPrint.printLine(linea, true);
            }
            vPrint.deactivateCondensed();

            vPrint.endPrintService();

            //vPrint.endPrintServiceSinCompletar();


            return true;
        }
    }

    private boolean imprimirTestigo(ArrayList<String> pTextos, String pNumComprobante, String pIndActualizaImpr,
                                    String tipoComp) {
        String ruta;
        try {
            ruta = UtilityPtoVenta.obtieneDirectorioComprobantes();
        } catch (SQLException e) {
            ruta = "";
        }
        Date vFecImpr = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaImpresion = sdf.format(vFecImpr);

        String vNombreTestigo;
        if (pIndActualizaImpr.equals("C")) {
            if ("06".equalsIgnoreCase(tipoComp))
                vNombreTestigo =
                        ruta + fechaImpresion + "_TF_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
            else
                vNombreTestigo =
                        ruta + fechaImpresion + "_T_" + VariablesCaja.vNumPedVta + "_" + pNumComprobante + ".TXT";
        } else if (pIndActualizaImpr.equals("A")) {
            if ("06".equalsIgnoreCase(tipoComp))
                vNombreTestigo =
                        ruta + fechaImpresion + "_TF_" + VariablesCaja.vNumPedVta_Anul + "_" + pNumComprobante +
                        "_Anul.TXT";
            else
                vNombreTestigo =
                        ruta + fechaImpresion + "_T_" + VariablesCaja.vNumPedVta_Anul + "_" + pNumComprobante +
                        "_Anul.TXT";
        } else {
            vNombreTestigo = ruta + fechaImpresion + "_T_Comprobante.TXT";
        }

        return imprimirGenerico(pTextos, vNombreTestigo, 66); //
    }

    public static void main(String[] args) {
        Impresora impresora = new Impresora();
        ArrayList<String> vPrint = impresora.generarPlantilla(130, 50);
        impresora.imprimirGenerico(vPrint, "\\\\10.18.1.176\\GUIAS08", 66);
    }

    public void imprimir(ArrayList<String> pTextos, String pRutaImpresora, boolean vImprimeTestigo,
                         String strNumPedido, String pNumComprobante, String pIndActualizaImpr, String pRuta,
                         int vlinea) {
        imprimirGenerico(pTextos, pRutaImpresora, vlinea);
        if (vImprimeTestigo) {
            try {
                /*String strNumPedido="";
                if(pIndActualizaImpr.equals("C")){
                    strNumPedido=VariablesCaja.vNumPedVta;
                }else if(pIndActualizaImpr.equals("A")){
                    strNumPedido=VariablesCaja.vNumPedVta_Anul;
                }*/
                DBCaja.actualizaFechaImpr(strNumPedido, pNumComprobante, pIndActualizaImpr);
            } catch (SQLException e) {
                log.error("", e);
            }
            imprimirGenerico(pTextos, pRuta, vlinea);
        }
    }
}
