package mifarma.ptoventa.otros.reference;

import javax.print.PrintService;

import javax.swing.JEditorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrintMedPresion {
    private static final Logger log = LoggerFactory.getLogger(PrintMedPresion.class);

    public static void imprimir(String HtmlImpr, PrintService pImpresora, String pTipImpr) {
        DocumentRendererMedPresion dr = new DocumentRendererMedPresion(pImpresora);
        JEditorPane editor = new JEditorPane();
        try {
            editor.setContentType("text/html");
            editor.setText(HtmlImpr);
            dr.print(editor, pTipImpr);
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
