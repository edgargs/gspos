package mifarma.ptoventa.caja.reference;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import javax.print.PrintService;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.text.html.HTMLDocument;

import mifarma.ptoventa.caja.reference.UtilityBarCode;

import org.jbarcode.encode.InvalidAtributeException;

public class PrintConsejo
{
  private static UtilityBarCode uBCode = new UtilityBarCode();

  public static void imprimir(String pConsejos,String pTipImpr,String pCodCupon)
  {
    DocumentRendererConsejo dr = new DocumentRendererConsejo();
    JEditorPane editor = new JEditorPane();
    
    try
    {
      // Se crea la imagen
      createImageCode(pCodCupon, 1);
      // Marcamos el editor para que use HTML 
      
      editor.setContentType("text/html");
      editor.setText(pConsejos);

      dr.print(editor,pTipImpr);

      // Elimina la imagen creada
      deleteImage(pCodCupon);        
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  
  /**
   * metodo encargado de imprimirCupon
   * @param pConsejos
   * @param pImpresora
   * @param pTipoImprConsejo
   * @param pCodCupon
   */
  public static void imprimirCupon(String pConsejos, PrintService pImpresora,
                              String pTipoImprConsejo,String pCodCupon, int cantIntentosLectura)
  {
    DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
    JEditorPane editor = new JEditorPane();
    
    try
    {
      // Se crea la imagen
      createImageCode(pCodCupon, cantIntentosLectura);
        
      // Marcamos el editor para que use HTML 
      editor.setContentType("text/html");
      editor.setText(pConsejos);
      dr.print(editor,pTipoImprConsejo);
      
      // Elimina la imagen creada
      deleteImage(pCodCupon);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * metodo encargado de imprimirHtml
   * @param pConsejos
   * @param pImpresora
   * @param pTipoImprConsejo
   * @param pCodCupon
   */
  public static void imprimirHtml(String pHtml, PrintService pImpresora,
                              String pTipoImprConsejo)
  {
    DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
    JEditorPane editor = new JEditorPane();
    try
    {
    	// Marcamos el editor para que use HTML 
    	editor.setContentType("text/html");
    	editor.setText(pHtml);
    	dr.print(editor,pTipoImprConsejo);
       
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
  }
  
  
  /**
     * Crea imagen de codigo de barra
     * @param pNameImage
     * @author Diego Ubilluz Carrillo
     * @since  03.07.2008
     */
  private static void createImageCode(String pNameImage, int cantIntentoLectura)
                            throws InvalidAtributeException{
      if(pNameImage!=null)
      {
         if(pNameImage.trim().length()>0)
            //uBCode.generarBarcodeCode39(pNameImage);   
            uBCode.generarBarcode(pNameImage, cantIntentoLectura);   
      }
  }
  /**
     * Elimina la imagen
     * @param pNameImage
     * @author Diego Ubilluz Carrillo
     * @since  03.07.2008
     */
  private static void deleteImage(String pNameImage){
      if(pNameImage!=null)
      {
         /*if(pNameImage.trim().length()>0)
             uBCode.eliminaBarcode(pNameImage.trim());*/
      }     
  }
  
  public static void main(String[] args)
  {
    
    DocumentRendererConsejo dr = new DocumentRendererConsejo();
    JEditorPane editor = new JEditorPane();
    
    try
    {
      // Marcamos el editor para que use HTML 
      UtilityBarCode u = new  UtilityBarCode();
      String nombre = u.crea_ima();
      String nombre2 = nombre;
      nombre  = nombre.substring(3);
        
      editor.setContentType("text/html");
      // molde 3
            editor.setText("<html>" + 
            "<head>" + 
            "</head>" + 
            "<body>" + 
            "<table width=\"337\" border=\"0\">" + 
            "  <tr>" + 
            "    <td width=\"8\">&nbsp;&nbsp;</td>" + 
            "    <td width=\"319\"><table width=\"316\" height=\"293\" border=\"0\">" + 
            "      <tr>" + 
            "        <td height=\"50\" colspan=\"3\"><div align=\"center\" class=\"style8\"><img src=file://///10.11.1.58/MiFarma2/mifarma.jpg width=\"246\" height=\"48\" class=\"style3\"></div></td>" + 
            "      </tr>" + 
            "      <tr>" + 
            "        <td height=\"13\" colspan=\"3\">         </td>" + 
            "      </tr>        " + 
            "      <tr>" + 
                           "        <td height=\"50\" colspan=\"3\"><div align=\"center\" class=\"style8\"><img src=file://///C:/"+nombre+" width=\"300\" height=\"107\" class=\"style3\"></div></td>       " + 
            "      </tr>" + 
            "      <tr>" + 
            "        <td height=\"12\" colspan=\"3\">         </td>" + 
            "      </tr>        " + 
            "      <tr>" + 
            "        <td height=\"30\" colspan=\"3\"  align=\"center\" style=\"font:Arial, Helvetica, sans-serif;font-size:30px \">" + 
            "         <B>5% descuento </B>    </td>" + 
            "      </tr>" + 
            "      <tr>" + 
            "        <td height=\"30\" colspan=\"3\"  align=\"left\" style=\"font:Arial, Helvetica, sans-serif\">" + 
            "         <B>FLUANXOL 5 MG CJA 20 GRAG</B></td>" + 
            "      </tr>        " + 
            "      <tr>" + 
            "        <td height=\"21\" colspan=\"3\" align=\"left\" style=\"font:Arial, Helvetica, sans-serif\">En su proxima compra </td>" + 
            "      </tr>      " + 
            "      <tr>" + 
            "        <td height=\"44\" colspan=\"3\" align=\"center\" style=\"font:oblique, Helvetica, sans-serif\">" + 
            "         &quot;En Mifarma nos preocupamos por tu salud&quot;</td>" + 
            "      </tr>" + 
            "      <tr>" + 
            "        <td width=\"50\" height=\"38\"><div align=\"center\" class=\"style1 style3 style8\">10541</div></td>" + 
            "        <td width=\"160\"><div align=\"center\" class=\"style1 style3 style8\">" + 
            "            <div align=\"center\">09/05/2008 12:08:25 pm</div>" + 
            "        </div></td>" + 
            "        <td width=\"92\"><div align=\"center\" class=\"style1 style3 style8\">017-HABI</div></td>" + 
            "      </tr>" + 
            "    </table></td>" + 
            "  </tr>" + 
            "</table>" + 
            "</body>" + 
            "</html>");

      dr.print(editor,"01");
        
       // u.eliminaBarcode(nombre2);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  
  }
  
    /**
     * metodo encargado de imprimirCupon
     * @param pConsejos
     * @param pImpresora
     * @param pTipoImprConsejo
     * @param pCodCupon
     */
    public static void imprimirCuponStick(String pConsejos, PrintService pImpresora,
                                String pTipoImprConsejo,String pCodCupon, int cantIntentosLectura,
                                          double cantFilas)
    {
      DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
      JEditorPane editor = new JEditorPane();
      
      try
      {
        // Se crea la imagen
        //createImageCode(pCodCupon, cantIntentosLectura);
          
        // Marcamos el editor para que use HTML 
        editor.setContentType("text/html");
        editor.setText(pConsejos);
        dr.printSticket(editor,pTipoImprConsejo,cantFilas);
        
        // Elimina la imagen creada
        //deleteImage(pCodCupon);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
    

}
