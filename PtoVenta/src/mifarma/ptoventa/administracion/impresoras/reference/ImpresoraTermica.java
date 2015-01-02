package mifarma.ptoventa.administracion.impresoras.reference;

import java.util.ArrayList;

import mifarma.common.FarmaPrintServiceTicket;


public class ImpresoraTermica {
    public ImpresoraTermica() {
        super();
    }

    private boolean imprimirGenerico(ArrayList<String> pTextos, String pRutaImpresora) {
        FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, pRutaImpresora, false);
        if (!vPrint.startPrintServiceArchivoTexto()) {
            return false;
        } else {
            for (String linea : pTextos) {
                vPrint.printLine(linea, true);
            }
            vPrint.endPrintServiceSinCompletar();
            return true;
        }
    }

    public static void main(String[] args) {
        ImpresoraTermica impresora = new ImpresoraTermica();
        //ArrayList<String> vPrint = impresora.generarPlantilla(130, 50);
        ArrayList<String> vPrint = new ArrayList<String>();
        //vPrint.add("***************************************1");
        //vPrint.add("***************************************2");
        //vPrint.add((char)29+"k"+(char)2+"2"+"0"+"5"+"0"+"0"+"0"+"3"+"6"+"3"+"6"+"6"+"4"+(char)0);
        //vPrint.add("prueba");
        //vPrint.add((char)29+"k"+"E"+(char)1+"5");

        vPrint.add("PDF417");
        vPrint.add((char)29 + "(" + "k" + (char)48 + (char)80 + "0");
        //vPrint.add((char)29+"("+"k"+(char)2+(char)2+(char)48+(char)81);
        vPrint.add((char)29 + "(" + "k" + (char)48 + (char)81);

        //correr el papel
        vPrint.add((char)27 + "d" + (char)1);
        //Cotar papel
        //vPrint.add((char)27+"i");
        vPrint.add((char)27 + "m");
        impresora.imprimirGenerico(vPrint, "\\\\10.18.1.179\\CONSEJO2");
    }
}
