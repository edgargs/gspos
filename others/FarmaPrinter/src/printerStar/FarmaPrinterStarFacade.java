

package printerStar;

import com.itextpdf.text.pdf.codec.Base64;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;

import printerEpson.FarmaPrinterEpsonBuilder;
import printerEpson.FarmaPrinterEpsonFacade;

import printerUtil.FarmaCodes;
import printerUtil.FarmaPrinterConstants;
import printerUtil.FarmaUtil;


/**
 *
 * @author Cesar Huanes
 * @since 21-07-2014
 * @version 1.0
 */
public class FarmaPrinterStarFacade  extends FarmaCodes implements FarmaPrinterStarInterface{
  
  
    private final static char ESC_CHAR = 0x1B;
    private final static char GS = 0x1D;
    private final static byte[] LINE_FEED = new byte[]{0x0A};
    private static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 32};//2A
    private final static byte[] SET_LINE_SPACE_24 = new byte[]{ESC_CHAR, 0x33, 24};
  
  /**OTRA FORMA*/
    public static final byte[] INITSEQUENCE = {0x1B, 0x7A, 0x01};
    private static final byte[] IMAGE_BEGIN = {0x1B, 0x30};//30
    private static final byte[] IMAGE_END = {0x1B, 0x7A, 0x01};
    private static final byte[] IMAGE_HEADER = {0x1B, 0x4B};
    private static final byte[] NEW_LINE = {0x0D, 0x0A}; // Print and carriage return

    boolean pIndImpPuertoActivo =false;
    String pIpImp = "";
    String pNameImp = "";   
    
    String ruta=null;
    PrintStream ps=null;
    private final FarmaUtil util=new FarmaUtil();
    private final FarmaPrinterStarBuilder star=new FarmaPrinterStarBuilder();
    FarmaPrinterEpsonBuilder epson=new FarmaPrinterEpsonBuilder();
    public FarmaPrinterStarFacade(){
    
    }
    
    public FarmaPrinterStarFacade(String ruta,boolean pIndImpPuertoActivo,String pIpImp,String pNameImp) {
    this.ruta=ruta;
    this.pIndImpPuertoActivo = pIndImpPuertoActivo;
    this.pIpImp = pIpImp;
    this.pNameImp = pNameImp;
    }
    
    @Override
    public boolean startPrintService(){
    boolean flag=false;
    FileOutputStream fos=null;
    try {
    
        /////////////////////////////////////////////////////////////////////////        
        if(pIndImpPuertoActivo){
            System.out.println("Set al PUERTO para usar ");
             try {
                 Runtime rt = Runtime.getRuntime();
                 Process p = null;
                 try{
                   p = rt.exec("cmd.exe /C NET USE LPT1: /DELETE");
                 } catch (Exception ioe) {
                   ioe.printStackTrace();
                 }
                 p = rt.exec("cmd.exe /C NET USE LPT1: \\\\"+pIpImp+"\\"+pNameImp+" /PERSISTENT:YES");
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }  
            System.out.println("FIN Set al PUERTO para usar ");
            fos=new FileOutputStream("LPT1:");
        }
        ////////////////////////////////////////////////////////////////////////
        else    
        fos=new FileOutputStream(this.getRuta());
        
        
    ps=new PrintStream(fos);
    flag=true;
    } catch (FileNotFoundException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);       
    }
    return flag;
    }
    
    @Override
    public void printCodePDF417(String trama){
  
    try {
  
    int nBytesCodeData=trama.getBytes().length;
    System.out.print("trama long"+nBytesCodeData);
    int nl=trama.getBytes().length%256;//254
    int nh=(trama.getBytes().length/256);//254
    ps.flush();
    ps.write(star.setPrintCenter());
    ps.write(star.setModelCodeBarraPDF417());
    ps.write(star.setEscaleNivelPDF417());
    ps.write(star.setDirectionXPDF417());
    ps.write(star.setRatioPDF417());
    ps.write(star.setDataPDF417(nl,nh));
    ps.write(trama.getBytes());
    ps.write(star.setBarCodeExpantionPDF417());//OPCIONAL
    ps.write(star.setPrintPDF417());
    ps.write(star.setPrintLefth());
    } catch (IOException ex) {
     String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }
    
    @Override
    public void printCodeQR(String trama){
        
    try {
    int nBytesCodeData=trama.getBytes().length;
    ps.flush();
    ps.write(star.setModelCodeBarraQR());
    ps.write(star.setEscaleNivelQR());
    ps.write(star.setDirectionXQR());
    ps.write(star.setDataQR(nBytesCodeData));
    ps.write(trama.getBytes());
    ps.write(star.setBarCodeExpantionQR());
    ps.write(star.setPrintQR());
    ps.println();  
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }
    
    @Override
    public void endPrintService() {
    try{    
    ps.flush();
    for(int i=0;i<5;i++){
    ps.println();}
    this.cutPaper();
    ps.close();
    }catch(Exception ex){
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }
    
    @Override
    public void cutPaper() {
    try {
    ps.write(star.cutePaper());
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }

    @Override
    public void stepLine() {
    try{
    ps.println();
    }catch(Exception ex){
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }

    @Override
    public void printLineBold(String texto) {
    try{
    ps.write(star.feedLine());
    
    ps.print(star.getActivateBold());
    ps.println(texto);
    ps.print(star.getDeactivateBold());
    }catch(IOException ex){
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
   
    }

    @Override
    public void printUnderLine(String texto) {
    try{
    ps.write(star.feedLine());
    ps.write(star.setFont());
    ps.print(star.getActivateUnderline());
    ps.println(texto);
    ps.print(star.getDeactivateUnderline());
    }
    catch(IOException ex){
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }
    
    @Override
    public void printLine(String texto) {
   
    try{  
    ps.write(star.feedLine());    
    ps.write(star.setFont());
    ps.write(texto.getBytes());
    ps.write((byte)(char)10);
    }catch(IOException ex){
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }

    @Override
    public void printLineDoubleSize(String texto) {
    try{
    ps.write(star.feedLine());
    ps.write(star.setPrintCenter());//
    ps.print(star.getActivateDoubleWidthMode());
    ps.println(texto);
    ps.write(star.setPrintLefth());  
    ps.print(star.getDeactivateDoubleWidthMode());
   
    }catch(IOException ex){
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }   
    }

    @Override
    public String printLineCenter(String texto) {
    try {
    ps.write(star.feedLine());       
    ps.write(star.setFont());
    ps.write(star.setPrintCenter());
    ps.write(texto.getBytes());
    ps.write((byte)(char)10);
    ps.write(star.setPrintLefth());   
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    return texto.getBytes().toString();
    }

    @Override
    public String printEspacioBlanco(int numEspacio) {
    return  util.espacioBlanco(numEspacio);
    }

    @Override
    public void printLineDotted(int maxPoint) {
    ps.println(util.guion(maxPoint));
   }

    @Override
    public void printLineRigth(String texto) {
    try {
    ps.write(star.feedLine());    
    ps.write(star.setFont());
    ps.write(star.setPrintRigth());
    ps.write(texto.getBytes());
    ps.write((byte)(char)10);
    ps.write(star.setPrintLefth());
    } 
    catch (IOException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }

    @Override
    public String alineaMontos(String texto) {
    return util.alineaMontos(texto);
    }

    @Override
    public String alineaDetalle(String codigo, String descripcion, String cant, String preUni,String desc, String importe) {
    return util.alineaDetalle(codigo, descripcion, cant, preUni,desc, importe);     
    }
    
    
     @Override
    public void inicializate() {
    try {
    ps.write(star.inicializate());
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, message, ex);  
    }
    }
    
     @Override
    public void printLogo(String codMarca){
        printImage(codMarca);
      
    }

   /***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
 
      public BufferedImage getImage(String codMarca){
        String dirName=util.getPathLogo(codMarca);
        ByteArrayOutputStream baos=new ByteArrayOutputStream(2000);
        BufferedImage img=null;
        BufferedImage imag = null;
        try {
            img = ImageIO.read(new File(dirName));
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            String base64String=Base64.encodeBytes(baos.toByteArray());
            baos.close();
            byte[] bytearray = Base64.decode(base64String);
            imag=ImageIO.read(new ByteArrayInputStream(bytearray));
            if(codMarca.equalsIgnoreCase(FarmaPrinterConstants.BTL)){
            imag=this.resize(imag, 130, 50);//130-30    
            }else{
            imag=this.resize(imag, 130, 30);//130-30
            }
         } catch (IOException ex) {
            Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
         return imag;  
    }
    /***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
       public  BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, BufferedImage.TYPE_USHORT_555_RGB);//bufferedImage.getType()
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }
   /***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
      public int[][] getPixelsSlow(BufferedImage image) {
       int width = image.getWidth();
       int height = image.getHeight();
       int[][] result = new int[height][width];
       for (int row = 0; row < height; row++) {
           for (int col = 0; col < width; col++) {
               result[row][col] = image.getRGB(col, row);
           }
       }

       return result;
   }
      
 
 
 
    /***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
 
    public void printImage(String codMarca) {
    
     int[][] pixels=this.getPixelsSlow(this.getImage(codMarca));
     try {
        
        ps.write(SET_LINE_SPACE_24);
        
        for (int y = 0; y < pixels.length; y += 24) {
        ps.write(SELECT_BIT_IMAGE_MODE); 
        ps.write(new byte[]{(byte)(0x00ff & pixels[y].length)
                                      , (byte)((0xff00 & pixels[y].length) >> 8)});
           for (int x = 0; x < pixels[y].length; x++) {
         ps.write(recollectSlice(y, x, pixels));
         
         }
         ps.write(LINE_FEED); 
        
        } 
         pixels=null;
    } catch (IOException ex) {
        Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, null, ex);
    }

    
    }
     /***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
    private byte[] recollectSlice(int y, int x, int[][] img) {
        byte[] slices = new byte[] {0, 0, 0};
        for (int yy = y, i = 0; yy < y + 24 && i < 3; yy += 8, i++) {
        byte slice = 0;
        for (int b = 0; b < 8; b++) {
        int yyy = yy + b;
        if (yyy >= img.length) {
                 continue;
        }
        int col = img[yyy][x]; 
        boolean v = shouldPrintColor(col);
        slice |= (byte) ((v ? 1 : 0) << (7 - b));
         }
        slices[i] = slice;
        }
        return slices;
    }
    /***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
    private boolean shouldPrintColor(int col) {
        final int threshold = 127;
        int a, r, g, b, luminance;
        a = (col >> 24) & 0xff;
        if (a != 0xff) {// Ignore transparencies
            return false;
        }
        r = (col >> 16) & 0xff;
        g = (col >> 8) & 0xff;
        b = col & 0xff;

        luminance = (int) (0.299 * r + 0.587 * g + 0.114 * b);

        return luminance < threshold;
    }
    
    @Override
     public void printLogo2(String codMarca){
      try {
          ps.write(this.transImage(this.getImage(codMarca)));
      } catch (IOException ex) {
          Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, null, ex);
       }
     }
   
    
    @Override
    public byte[] transImage(BufferedImage image) {

    FarmaCodes.CenteredImage centeredimage = new FarmaCodes.CenteredImage(image, getImageWidth());             
    int iWidth = centeredimage.getWidth();
    int iHeight = (centeredimage.getHeight() + 7) / 8; 
    byte[] bData = new byte[
            IMAGE_BEGIN.length +
            (getImageHeader().length + 2 + iWidth + getNewLine().length) * iHeight +
            IMAGE_END.length];
    int index = 0;
    System.arraycopy(IMAGE_BEGIN, 0, bData, index, IMAGE_BEGIN.length);
    index += IMAGE_BEGIN.length;

        int p;
        for (int i = 0; i < centeredimage.getHeight(); i += 8) {
            System.arraycopy(getImageHeader(), 0, bData, index, getImageHeader().length);
            index += getImageHeader().length;
            bData[index ++] = (byte) (iWidth % 256);
            bData[index ++] = (byte) (iWidth / 256);           
            
            for (int j = 0; j < centeredimage.getWidth(); j++) {
                p = 0x00;
                for (int d = 0; d < 8; d ++) {
                    p = p << 1;
                   if (centeredimage.isBlack(j, i + d)) {
                        p = p | 0x01;
                    }
                }
                
                bData[index ++] = (byte) p;
            }
    System.arraycopy(getNewLine(), 0, bData, index, getNewLine().length);
    index += getNewLine().length;
        
        }

    System.arraycopy(IMAGE_END, 0, bData, index, IMAGE_END.length);
    index += IMAGE_END.length;
    return bData;
    }
    
  @Override
   public byte[] getInitSequence() { return INITSEQUENCE; }
  @Override
    public byte[] getNewLine() { return NEW_LINE; } 
  @Override
    public byte[] getImageHeader() { return IMAGE_HEADER; }     
  @Override
   public int getImageWidth() { return 192; }//192

     public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * METODO QUE REALIZA LA IMPRESION EN TERMICA
     * CREACION     - KENNY MONCADA     - 12.11.2014
     * 
     * @param texto texto que se mostrara
     * @param tamanio tipo de tamanio de texto; 1: TAMANIO NORMAL, 2: DOBLE TAMANIO
     * @param alineacion tipo de alineacion; D:DERECHA, I:IZQUIERDA, C:CENTRO
     * @param isNegrita indicador de linea en negrita
     * @since 12.11.2014 v1.0.0
     */
    public void printGeneral(String texto, String tamanio, String alineacion, boolean isNegrita, int lineado ){
        
        try{
            int tamanioFuenta = Integer.parseInt(tamanio);
            // DIFERENTE A IMPRESION PDF457
            if(!"P".equalsIgnoreCase(alineacion)){
                
                // KMONCADA 17.12.2014 LINEADO DOBLE
                if(lineado==0)
                    ps.write(star.feedLine());
                else
                    ps.write(star.feedLineDouble());
                
                // KMONCADA 17.12.2014 REINICIA LOS FORMATOS
                ps.print(star.getDeactivateBold());
                ps.print(star.getDeactivateDoubleWidthMode());
                
                // FORMATO NEGRITA
                if(isNegrita){
                    ps.print(star.getActivateBold());
                }
                
                ps.write(star.setFontDefault()); 
                //ps.print(star.setFontEstilo());
                // KMONCADA 17.12.2014 TAMANIOS DE FUENTES
                switch(tamanioFuenta){
                    case 0 :    ps.print(star.setFont0());
                                break;
                    case 1 :    ps.print(star.setFont1());
                                break;
                    case 2 :    ps.print(star.setFont2());
                                break;
                    case 3 :    ps.print(star.setFont3());
                                break;
                    case 4 :    ps.print(star.setFont4());
                                break;
                    case 5 :    ps.print(star.setFont5());
                                break;
                    case 9:     ps.print(star.setFontCompElectronico());
                                break;
                    default:    ps.print(star.setFontCompElectronico());
                                break;
                }
                
                
                
                switch(alineacion){
                    //DERECHA
                    case "D"    :   ps.write(star.setPrintRigth());
                                    break;
                    //CENTRO
                    case "C"    :   ps.write(star.setPrintCenter());
                                    break;
                    //IZQUIERDA
                    case "I"    :   ps.write(star.setPrintLefth());
                                    break;
                    
                }
                
                // SE IMPRIME EL TEXTO
                if(texto!=null){
                    ps.write(texto.getBytes());
                    ps.write((byte)(char)10);
                }else{
                    ps.println();
                }
                
            }else{
                // IMPRESION DE FORMATO PDF457
                int nBytesCodeData = texto.getBytes().length;
                System.out.print("trama long"+nBytesCodeData);
                int nl = texto.getBytes().length%256;//254
                int nh = (texto.getBytes().length/256);//254
                ps.flush();
                ps.write(star.setPrintCenter());
                ps.write(star.setModelCodeBarraPDF417());
                ps.write(star.setEscaleNivelPDF417());
                ps.write(star.setDirectionXPDF417());
                ps.write(star.setRatioPDF417());
                ps.write(star.setDataPDF417(nl,nh));
                ps.write(texto.getBytes());
                ps.write(star.setBarCodeExpantionPDF417());//OPCIONAL
                ps.write(star.setPrintPDF417());
                ps.write(star.setPrintLefth()); 
            }
        }catch(Exception ex){
            String message = ex.getMessage(); 
            Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
        }
        
    }
   
    
    public BufferedImage getCodeBar(String codBarra){        
        BufferedImage bufferedImage = null;
        try {
            //JBarcode jbcode = new JBarcode(Code39Encoder.getInstance(), WideRatioCodedPainter.getInstance(),BaseLineTextPainter.getInstance());
            JBarcode jbcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
            //JBarcode jbcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
            jbcode.setShowText(false);
            /*
            jbcode.setBarHeight(5);
            jbcode.setWideRatio(1);
            */
            jbcode.setBarHeight(9);
            jbcode.setXDimension(0.29);
            //jbcode.setWideRatio(1);
            /*jbcode.setBarHeight(25);
            jbcode.setWideRatio(6);*/
            
            jbcode.setCheckDigit(false);
            bufferedImage = jbcode.createBarcode(codBarra);
        } catch (Exception iae) {
            // TODO: Add catch code
            iae.printStackTrace();
        }
        return bufferedImage;
        
    }
    
    
    public void printCodeBar(String pCodigo){
        
        try {
            ps.write(this.transImage(this.getCodeBar(pCodigo)));
        } catch (IOException ex) {
            Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }    
    
    public void abrirGabeta(){
        ps.println(""+(char)27+(char)7+(char)11+(char)55+(char)7);
    }
    
}
