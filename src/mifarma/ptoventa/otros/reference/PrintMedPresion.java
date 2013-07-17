package mifarma.ptoventa.otros.reference;

import javax.print.PrintService;
import javax.swing.JEditorPane;

public class PrintMedPresion {
	
	public static void imprimir(String HtmlImpr,PrintService pImpresora,String pTipImpr) {
	    DocumentRendererMedPresion dr = new DocumentRendererMedPresion(pImpresora);
	    JEditorPane editor = new JEditorPane();
	    try
	    {
	    	editor.setContentType("text/html");
	    	editor.setText(HtmlImpr);
	    	dr.print(editor,pTipImpr);        
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	}

}
