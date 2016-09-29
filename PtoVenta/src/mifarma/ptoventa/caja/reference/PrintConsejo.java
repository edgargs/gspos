package mifarma.ptoventa.caja.reference;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.PrintService;

import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.reference.BeanImpresion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerFarma.FarmaPrinterFacade;


public class PrintConsejo {
    private static UtilityBarCode uBCode = new UtilityBarCode();
    private static final Logger log = LoggerFactory.getLogger(PrintConsejo.class);
    private static final int num_imp = 2; //Indica el numero de impresiones de sobres parciales

    public static void imprimir(String pConsejos, String pTipImpr, String pCodCupon) {
        DocumentRendererConsejo dr = new DocumentRendererConsejo();
        JEditorPane editor = new JEditorPane();

        try {
            // Se crea la imagen
            //createImageCode(pCodCupon, 1);
            // Marcamos el editor para que use HTML

            editor.setContentType("text/html");
            editor.setText(pConsejos);

            dr.print(editor, pTipImpr);

            // Elimina la imagen creada
            deleteImage(pCodCupon);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void imprimirEan13(String pConsejos, PrintService pImpresora, String pTipoImprConsejo,
                                     String pCodCupon) {

        imprimirCupon(pConsejos, pImpresora, pTipoImprConsejo, pCodCupon, "ean13");
    }

    public static void imprimirEan8(String pConsejos, PrintService pImpresora, String pTipoImprConsejo,
                                    String pCodCupon) {

        imprimirCupon(pConsejos, pImpresora, pTipoImprConsejo, pCodCupon, "ean8");
    }


    public static void imprimirCode128(String pConsejos, PrintService pImpresora, String pTipoImprConsejo,
                                       String pCodCupon, String tipo) {
        if (tipo.equals("PTOS")) {
            imprimirCuponPtos(pConsejos, pImpresora, pTipoImprConsejo, pCodCupon, "code39");
        } else {
            imprimirCupon(pConsejos, pImpresora, pTipoImprConsejo, pCodCupon, "code128");
        }
    }


    /**
     * metodo encargado de imprimirCupon
     * @param pConsejos asfa
     * @param pImpresora asfaf
     * @param pTipoImprConsejo
     * @param pCodCupon
     * @param tipoCode ean13, ean8, code128, code39
     */
    private static void imprimirCupon(String pConsejos, PrintService pImpresora, String pTipoImprConsejo,
                                      String pCodCupon, String tipoCode) {

        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        JEditorPane editor = new JEditorPane();

        CodigoBarra codigoBarra = new CodigoBarra();
        String codeBarra = codigoBarra.generarCodeBarra(pCodCupon, tipoCode);

        if (codeBarra == null) {
            log.info("Error al generar el codigo de barras. Tipo de Code: " + tipoCode);
        }
        try {

            String htmlTabla = "";
            if (codeBarra != null) {
                HTMLEditorKit cssKit = new HTMLEditorKit();
                //seteamos al JEditorPane el editor html
                editor.setEditorKit(cssKit);
                //obtenemos sus reglas de estilos CSS.
                StyleSheet styleSheet = cssKit.getStyleSheet();
                //styleSheet.addRule("body{ margin: 0; width: 196px; padding: 0;}");
                styleSheet.addRule("table.barra { border-width: 0px; border-spacing: 0px; border-style: none; border-color: gray; border-collapse: separate; background-color: white; padding: 0px; overflow = auto;}");
                styleSheet.addRule("table.barra th { border-width: 0px; padding: 0px; border-style: none; border-color: gray; background-color: white; }");
                styleSheet.addRule("table.barra td { border-width: 0px; padding: 0px; border-style: none; border-color: gray; background-color: white; font-size: 10px;}");

                //styleSheet.addRule("table.barra td.center { text-align: center; font-size: 14px;}");

                // generamos la tabla con el codigo de barra segun el codigo de cupon asignado.
                htmlTabla = "<table align=\"center\" class=\"barra\" >" + codeBarra + "</table>";

                javax.swing.text.Document doc = cssKit.createDefaultDocument();
                editor.setDocument(doc);

            }
            // reemplazamos la etiqueta que se genera en la base de datos por el codigo de barra generado.
            pConsejos = pConsejos.replace("<<codigoBarra>>", htmlTabla);
            //pConsejos = htmlTabla;
            log.info(pConsejos + "codigo " + tipoCode);
            // Marcamos el editor para que use HTML
            editor.setText(pConsejos);
            editor.setContentType("text/html");
            dr.print(editor, pTipoImprConsejo);

            // Elimina la imagen creada
            // deleteImage(pCodCupon);
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    /**
     * metodo encargado de imprimirCupon
     * @param pConsejos asfa
     * @param pImpresora asfaf
     * @param pTipoImprConsejo
     * @param pCodCupon
     * @param tipoCode ean13, ean8, code128, code39
     */
    private static void imprimirCuponPtos(String pConsejos, PrintService pImpresora, String pTipoImprConsejo,
                                      String pCodCupon, String tipoCode) {

        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        JEditorPane editor = new JEditorPane();

        CodigoBarra codigoBarra = new CodigoBarra();
        String codeBarra = codigoBarra.generarCodeBarraPtos(pCodCupon, tipoCode);

        if (codeBarra == null) {
            log.info("Error al generar el codigo de barras. Tipo de Code: " + tipoCode);
        }
        try {

            String htmlTabla = "";
            if (codeBarra != null) {
                HTMLEditorKit cssKit = new HTMLEditorKit();
                //seteamos al JEditorPane el editor html
                editor.setEditorKit(cssKit);
                //obtenemos sus reglas de estilos CSS.
                StyleSheet styleSheet = cssKit.getStyleSheet();
                //styleSheet.addRule("body{ margin: 0; width: 196px; padding: 0;}");
                styleSheet.addRule("table.barra { border-width: 0px; border-spacing: 0px; border-style: none; border-color: gray; border-collapse: separate; background-color: white; padding: 0px; overflow = auto;}");
                styleSheet.addRule("table.barra th { border-width: 0px; padding: 0px; border-style: none; border-color: gray; background-color: white; }");
                styleSheet.addRule("table.barra td { border-width: 0px; padding: 0px; border-style: none; border-color: gray; background-color: white; font-size: 10px;}");

                //styleSheet.addRule("table.barra td.center { text-align: center; font-size: 14px;}");

                // generamos la tabla con el codigo de barra segun el codigo de cupon asignado.
                htmlTabla = "<table align=\"center\" class=\"barra\" >" + codeBarra + "</table>";

                javax.swing.text.Document doc = cssKit.createDefaultDocument();
                editor.setDocument(doc);

            }
            // reemplazamos la etiqueta que se genera en la base de datos por el codigo de barra generado.
            pConsejos = pConsejos.replace("<<codigoBarra>>", htmlTabla);
            //pConsejos = htmlTabla;
            log.info(pConsejos + "codigo " + tipoCode);
            // Marcamos el editor para que use HTML
            editor.setText(pConsejos);
            editor.setContentType("text/html");
            dr.print(editor, pTipoImprConsejo);

            // Elimina la imagen creada
            // deleteImage(pCodCupon);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void imprimirContrasenia(String pConsejos, PrintService pImpresora, String pTipoImprConsejo,
                                           String pCodCupon, int cantIntentosLectura) {
        /*DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
      JEditorPane editor = new JEditorPane();

      try
      {
        // Se crea la imagen
        createImageCode(htmlstore, cantIntentosLectura);

        // Marcamos el editor para que use HTML
        editor.setContentType("text/html");
        editor.setText(htmlstore);
        dr.print(editor,pTipoImprConsejo);

        // Elimina la imagen creada
        deleteImage(pCodCupon);
      }
      catch(Exception e)
      {
        log.error("",e);
      }*/
    }

    /**
     * metodo encargado de imprimirHtml
     * @param pHtml
     * @param pImpresora
     * @param pTipoImprConsejo
     */
    public static void imprimirHtml(String pHtml, PrintService pImpresora, String pTipoImprConsejo) {
        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        JEditorPane editor = new JEditorPane();
        try {
            // Marcamos el editor para que use HTML

            editor.setContentType("text/html");
            editor.setText(pHtml);
            dr.print(editor, pTipoImprConsejo);


        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * Elimina la imagen
     * @param pNameImage
     * @author Diego Ubilluz Carrillo
     * @since  03.07.2008
     */
    private static void deleteImage(String pNameImage) {
        if (pNameImage != null) {
            /*if(pNameImage.trim().length()>0)
             uBCode.eliminaBarcode(pNameImage.trim());*/
        }
    }

    public static void main(String[] args) {

        DocumentRendererConsejo dr = new DocumentRendererConsejo();
        JEditorPane editor = new JEditorPane();

        try {
            // Marcamos el editor para que use HTML
            UtilityBarCode u = new UtilityBarCode();
            String nombre = u.crea_ima();
            String nombre2 = nombre;
            nombre = nombre.substring(3);

            editor.setContentType("text/html");
            // molde 3
            editor.setText("<html>" + "<head>" + "</head>" + "<body>" + "<table width=\"337\" border=\"0\">" +
                           "  <tr>" + "    <td width=\"8\">&nbsp;&nbsp;</td>" +
                           "    <td width=\"319\"><table width=\"316\" height=\"293\" border=\"0\">" + "      <tr>" +
                           "        <td height=\"50\" colspan=\"3\"><div align=\"center\" class=\"style8\"><img src=file://///10.11.1.58/MiFarma2/mifarma.jpg width=\"246\" height=\"48\" class=\"style3\"></div></td>" +
                           "      </tr>" + "      <tr>" + "        <td height=\"13\" colspan=\"3\">         </td>" +
                           "      </tr>        " + "      <tr>" +
                           "        <td height=\"50\" colspan=\"3\"><div align=\"center\" class=\"style8\"><img src=file://///C:/" +
                           nombre + " width=\"300\" height=\"107\" class=\"style3\"></div></td>       " +
                           "      </tr>" + "      <tr>" + "        <td height=\"12\" colspan=\"3\">         </td>" +
                           "      </tr>        " + "      <tr>" +
                           "        <td height=\"30\" colspan=\"3\"  align=\"center\" style=\"font:Arial, Helvetica, sans-serif;font-size:30px \">" +
                           "         <B>5% descuento </B>    </td>" + "      </tr>" + "      <tr>" +
                           "        <td height=\"30\" colspan=\"3\"  align=\"left\" style=\"font:Arial, Helvetica, sans-serif\">" +
                           "         <B>FLUANXOL 5 MG CJA 20 GRAG</B></td>" + "      </tr>        " + "      <tr>" +
                           "        <td height=\"21\" colspan=\"3\" align=\"left\" style=\"font:Arial, Helvetica, sans-serif\">En su proxima compra </td>" +
                           "      </tr>      " + "      <tr>" +
                           "        <td height=\"44\" colspan=\"3\" align=\"center\" style=\"font:oblique, Helvetica, sans-serif\">" +
                           "         &quot;En Mifarma nos preocupamos por tu salud&quot;</td>" + "      </tr>" +
                           "      <tr>" +
                           "        <td width=\"50\" height=\"38\"><div align=\"center\" class=\"style1 style3 style8\">10541</div></td>" +
                           "        <td width=\"160\"><div align=\"center\" class=\"style1 style3 style8\">" +
                           "            <div align=\"center\">09/05/2008 12:08:25 pm</div>" + "        </div></td>" +
                           "        <td width=\"92\"><div align=\"center\" class=\"style1 style3 style8\">017-HABI</div></td>" +
                           "      </tr>" + "    </table></td>" + "  </tr>" + "</table>" + "</body>" + "</html>");

            dr.print(editor, "01");

            // u.eliminaBarcode(nombre2);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * metodo encargado de imprimirCupon
     * @param pConsejos
     * @param pImpresora
     * @param pTipoImprConsejo
     * @param pCodCupon
     */
    public static void imprimirCuponStick(String pConsejos, PrintService pImpresora, String pTipoImprConsejo,
                                          String pCodCupon, int cantIntentosLectura, double cantFilas) {
        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        JEditorPane editor = new JEditorPane();

        try {
            // Se crea la imagen
            //createImageCode(pCodCupon, cantIntentosLectura);

            // Marcamos el editor para que use HTML
            editor.setContentType("text/html");
            editor.setText(pConsejos);
            dr.printSticket(editor, pTipoImprConsejo, cantFilas);

            // Elimina la imagen creada
            //deleteImage(pCodCupon);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * Realiza la impresión de los tickets en código EPL
     * @AUTHOR Christian Vilca Quiros
     * @SINCE 20.09.2013
     */
    public static void imprimirStickerEnLote(String pConsejos, PrintService pImpresora) {
        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        try {
            dr.printFlejera(pConsejos, pImpresora);
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    static void impresionTermica(String tipoImpTermica, String rutaImpTermica, List<BeanImpresion> listDocumento) throws Exception {
        
        FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); //manda imprimir segun el modelo de impresora
        
        if (!printer.startPrintService()) {
            throw new Exception("No se pudo iniciar la Impresión del Documento.\nVerifique su impresora Termica por favor.");
        } else {
            //INICIALIZAR LA IMPRESORA--VALORES POR DEFECTO
            printer.inicializate(); 
            
            for (int i = 0; i < listDocumento.size(); i++) {
                log.info("LINEA --> "+(HashMap)listDocumento.get(i));
                printer.printer((HashMap)listDocumento.get(i));
                if ("-".equalsIgnoreCase(((String)((HashMap)listDocumento.get(i)).get("VALOR")))) {
                    printer.printLineDotted(30);
                    
                } else {
                    ;
                }
            }
            printer.endService();
            
        }
    }
}
