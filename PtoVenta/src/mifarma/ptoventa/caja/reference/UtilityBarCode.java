package mifarma.ptoventa.caja.reference;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

import java.sql.SQLException;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;

public class UtilityBarCode
{
	private static final Logger log = LoggerFactory.getLogger(UtilityBarCode.class);
 
// private static String CONS_DIRECTORIO = "C:\\";
  /* reemplazado por metodo MostrarDirectorio*/
        
 private static String CONS_DIRECTORIO = "";
  private static String CONS_TIPO_ARCHIVO = ".jpg";
    
    
  public UtilityBarCode()
  {
  }
  public static String crea_ima() throws Exception {
      String code = "$1234501012345";
      String nombre2 = "";
      
  
      try
      {
        nombre2 = generarBarcodeCode39(code);
        log.debug("archivo creado: " + nombre2);
        //eliminaBarcode(nombre2);
      }
      catch (InvalidAtributeException e)
      {
        log.error("",e);
      }
      
      return nombre2;
  }
  public static void main(String[] args) throws Exception 
  {
   
   
    String code = "$0000400100005";
    String nombre2;
    
   
        
    try
    {
      nombre2 = generarBarcodeCode39(code);
      log.debug("archivo creado: " + nombre2);
      //eliminaBarcode(nombre2);
    }
    catch (InvalidAtributeException e)
    {
      log.error("",e);
    }
    
    
  }

  static void saveToJPEG(BufferedImage img, String fileName)
  {      
    saveToFile(img, fileName, ImageUtil.JPEG);
  }

  static void saveToPNG(BufferedImage img, String fileName)
  {
    saveToFile(img, fileName, ImageUtil.PNG);
  }

  static void saveToFile(BufferedImage img, String fileName, String format)
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(fileName);        
      ImageUtil.encodeAndWrite(img, format, fos);        
      fos.close();
    }
    catch (Exception e)
    {
      log.error("",e);
    }
  }
  
  public static String generarBarcode(String codigo, int cantIntentosLectura)
    throws InvalidAtributeException
  {
        try {
            obtenerDirectorio();
        } catch (Exception e) {
            log.error("",e);
        }
       JBarcode jbcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
     
      
    //Modificado por dveliz 19.08.08
    //Se cambio los valores de los metodos setBarHeigth y setXDimension para 
    //reducir el tamaño de la imagen de codigo de barra que se genera en esta
    //clase, los valores originales han sido comentados.
    
    //jbcode.setBarHeigth(5); //167
    //bcode.setXDimension(0.1); //333
    
    //modificado por mfajardo 13/05/09
    //jbcode.setBarHeigth(3); //100
    //jbcode.setXDimension(0.06); //222
    
    //modificado por jmiranda 06/07/09
    //jbcode.setBarHeigth(2); //67 actual
    //jbcode.setXDimension(0.06); //222 actual
             
    int tamano = codigo.length();
    if(tamano == 13)
    {
    	jbcode.setCheckDigit(false);
    }
    //String nombreArchivo = "C:\\"+codigo+".jpg";    
    String nombreArchivo = CONS_DIRECTORIO + codigo + CONS_TIPO_ARCHIVO;
    log.debug("CONS_DIRECTORIO " + CONS_DIRECTORIO);
    log.debug("nombreArchivo " + nombreArchivo);
      
    BufferedImage img = jbcode.createBarcode(codigo);
          
    //Agregado por mfajardo 13/05/09 : reducir tamaño de imagen      
      int altura =  img.getHeight();
      int ancho=  img.getWidth();
/*
      BufferedImage enlargedImage = new BufferedImage(ancho,altura , img.getType());     
      for (int y=0; y < altura; ++y)
      for (int x=0; x < ancho; ++x)
        enlargedImage.setRGB(x, y, img.getRGB(x, y)); 
      */
      long tmpT1,tmpT2;                  
      tmpT1 = System.currentTimeMillis();
//      saveToJPEG(enlargedImage, nombreArchivo);
      
      saveToJPEG(img, nombreArchivo);
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo Imagen: Graba la imagen:"+(tmpT2 - tmpT1)+" milisegundos");
    /*
    log.debug("1 - Instancia de Tarea Timer");
    tmpT1 = System.currentTimeMillis();
    TareaTimer timerTask = new TareaTimer();
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo Instancia de Tarea Timer:"+(tmpT2 - tmpT1)+" milisegundos");
    log.debug("Fin Instancia de Tarea Timer");
      
      log.debug("2 -setNombreArchivo");
      tmpT1 = System.currentTimeMillis();
      timerTask.setNombreArchivo(nombreArchivo);
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo setNombreArchivo:"+(tmpT2 - tmpT1)+" milisegundos");
      log.debug("Fin setNombreArchivo");
      
      log.debug("3 -setCantidadIntentos");
      tmpT1 = System.currentTimeMillis();
      timerTask.setCantidadIntentos(cantIntentosLectura);
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo setCantidadIntentos:"+(tmpT2 - tmpT1)+" milisegundos");
      log.debug("Fin setCantidadIntentos");
      
      log.debug("4 -Instancia Timer");
      tmpT1 = System.currentTimeMillis();
      Timer timer = new Timer();
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo Timer:"+(tmpT2 - tmpT1)+" milisegundos");
      log.debug("Fin Timer");
      
      log.debug("5 - scheduleAtFixedRate");
      tmpT1 = System.currentTimeMillis();
      timer.scheduleAtFixedRate(timerTask, 0, 1000);
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo scheduleAtFixedRate:"+(tmpT2 - tmpT1)+" milisegundos");
      log.debug("Fin scheduleAtFixedRate");
    
    log.debug("6 - do while");
    tmpT1 = System.currentTimeMillis();
    do{
    	//log.debug("indicador TIMER :"+timerTask.getIndicador());
    }while(timerTask.getIndicador().trim().equalsIgnoreCase("I"));
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo do while:"+(tmpT2 - tmpT1)+" milisegundos");
    log.debug("Fin do while");
    
      
    log.debug("termino el TIMER");
    
    log.debug("timerTask.getIndicador():"+timerTask.getIndicador());
    	
    if(timerTask.getIndicador().equals("T")){
    	new Exception("ERROR : el codigo de barra de cupon a generar no esta disponible!");
    	log.debug("IMAGENO NO DISPONIBLE O NO GENERADO");
    }    
   */
    return nombreArchivo;  
  }

    public static void obtenerDirectorio() throws Exception {

        try {
            CONS_DIRECTORIO = UtilityPtoVenta.obtieneDirectorioImagenes().trim();
        }
        catch (SQLException sql) {
            log.error("",sql);
            throw new Exception("Error al obtener información del directorio - \n" +
                    sql);
        }
    }
  
  public static void eliminaBarcode(String nombreArchivo)
  {
    String  ruta_archivo = CONS_DIRECTORIO + nombreArchivo + CONS_TIPO_ARCHIVO;
    File archivo = new File(ruta_archivo);
    archivo.delete();
  }
  
  public static String generarBarcodeCode39(String codigo)
    throws InvalidAtributeException
  {

        try {
            obtenerDirectorio();
        } catch (Exception e) {
            log.error("",e);
            // TODO
        }
        JBarcode jbcode = new JBarcode(Code39Encoder.getInstance(), WideRatioCodedPainter.getInstance(),BaseLineTextPainter.getInstance());
    //jbcode.setBarHeigth(18); //600
    jbcode.setBarHeight(3); //
    //jbcode.setXDimension(0.3); //1110
    //jbcode.setXDimension(0.065); //
    //jmiranda 
    jbcode.setXDimension(0.06);
    //jbcode.setWideRatio(3.0);
    jbcode.setCheckDigit(false);
    //jbcode.setShowCheckDigit(false);
    
    log.info("CONS_DIRECTORIO " + CONS_DIRECTORIO);
    String nombreArchivo = CONS_DIRECTORIO + codigo + CONS_TIPO_ARCHIVO;
    log.debug("nombreArchivo " + nombreArchivo);
    
    BufferedImage img = jbcode.createBarcode(codigo);
    saveToJPEG(img, nombreArchivo);
    
    File archivo = new File(nombreArchivo);
    if(!archivo.exists())
    {
      new Exception("Error al generar el archivo.");
    }
    
    return nombreArchivo;
  }


  /**
   * Dubilluz
   * @param codigo
   * @param cantIntentosLectura
   * @return
   * @throws InvalidAtributeException
   */

  public static String generarBarcode128(String codigo, int cantIntentosLectura)

    throws InvalidAtributeException

  {

        try {

            obtenerDirectorio();

        } catch (Exception e) {

            log.error("",e);

        }

      JBarcode jbcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());

    String nombreArchivo = CONS_DIRECTORIO + codigo + CONS_TIPO_ARCHIVO;

    log.debug("CONS_DIRECTORIO " + CONS_DIRECTORIO);

    log.debug("nombreArchivo " + nombreArchivo);

    BufferedImage img = jbcode.createBarcode(codigo);



      int altura =  img.getHeight();

      int ancho=  img.getWidth();

      long tmpT1,tmpT2;

      tmpT1 = System.currentTimeMillis();

      saveToJPEG(img, nombreArchivo);

      tmpT2 = System.currentTimeMillis();

      log.debug("Tiempo Imagen: Graba la imagen:"+(tmpT2 - tmpT1)+" milisegundos");

    return nombreArchivo;

  }



}