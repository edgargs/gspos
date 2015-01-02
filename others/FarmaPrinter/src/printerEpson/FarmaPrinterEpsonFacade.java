/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerEpson;

import com.itextpdf.text.pdf.codec.Base64;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.JBarcode;

import org.jbarcode.JBarcodeComponent;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;

import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;

import org.jbarcode.paint.WidthCodedPainter;

import printerStar.FarmaPrinterStarFacade;

import printerUtil.FarmaCodes;
import printerUtil.FarmaUtil;

/**
 * @author Cesar Huanes
 * @since  21-07-2014
 * @version  1.0
 */
public class FarmaPrinterEpsonFacade implements FarmaPrinterEpsonInterface{
   private final static char ESC_CHAR = 0x1B;
    private final static char GS = 0x1D;
    private final static byte[] LINE_FEED = new byte[]{0x0A};
    private static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 32};
    //private final static byte[] SET_LINE_SPACE_24 = new byte[]{ESC_CHAR, 0x33, 24};
    private final static byte[] SET_LINE_SPACE_24 = new byte[]{ESC_CHAR, 0x33, 24};
    private String ruta=null;
    private PrintStream ps=null;
    private final FarmaUtil util=new FarmaUtil();
    private final FarmaPrinterEpsonBuilder epson=new FarmaPrinterEpsonBuilder();
    
    boolean pIndImpPuertoActivo =false;
    String pIpImp = "";
    String pNameImp = "";
    
    
    public FarmaPrinterEpsonFacade(){
     
    
    }

    public FarmaPrinterEpsonFacade(String ruta,boolean pIndImpPuertoActivo,String pIpImp,String pNameImp) {
        this.ruta = ruta;
        this.pIndImpPuertoActivo = pIndImpPuertoActivo;
        this.pIpImp = pIpImp;
        this.pNameImp = pNameImp;
    }

    @Override
    public boolean startPrintService() {
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
        
    } catch (Exception ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);       
    }
    return flag;
    }
    
   

    @Override
    public void printCodePDF417(String trama ) {
    try {
       
    int longitudTrama=trama.getBytes().length;
    System.out.print("trama long"+longitudTrama);
    int nl=trama.getBytes().length%256;
    int nh=trama.getBytes().length/256;
    ps.write(epson.setCenterPDF417());
    ps.write(epson.setNumRowsPDF417());
    ps.write(epson.setRowHeigth());
    ps.write(epson.setCorrectionLevel());
    ps.write(epson.setParameter1PDF417());
    ps.write(epson.setParameter2PDF417());
    ps.write(epson.setOption());
    ps.write(epson.setParameter3PDF417(nl,nh));
    ps.write(trama.getBytes());
    ps.write(epson.setParameter4PDF417());
    ps.write(epson.setDimension());
    ps.write(epson.setLefth());
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
    }

    @Override
    public void printCodeQR(String trama) {
    try {
    int longitudTrama=trama.getBytes().length;
    ps.write(epson.setParameter1QR());
    ps.write(epson.setParameter2QR());
    ps.write(epson.setParameter3QR());
    ps.write(epson.setParameter4QR(longitudTrama));
    ps.write(trama.getBytes("utf8"));  
    ps.write(epson.setParameter5QR());
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
    }

    @Override
    public void cutPaper() {
        try {
            ps.write(epson.setCutPaper());
        } catch (IOException ex) {
            String message;
            message = ex.getMessage();
            Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
        }
    }

    @Override
    public void endPrintService() {
        try {
            ps.flush();
            /*for (int i = 0; i < 3; i++) {
            }*/
            this.cutPaper();
            ps.close();
        } catch (Exception ex) {
            String message;
            message = ex.getMessage();
            Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
        }
    }

    @Override
    public void printUnderLine(String texto) {
    try {
    ps.write(epson.feedLine());     
    ps.write(epson.setFontCompElectronico());
    ps.write(epson.setUnderLine());
    ps.write(texto.getBytes());
    ps.write(epson.setOffUnderLine());
    ps.write((byte)(char)10);
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();     
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
    }

    @Override
    public void printLine(String texto) {
    try {
    ps.write(epson.feedLine());
    ps.write(epson.setFontCompElectronico());
    ps.write(texto.getBytes());        
    ps.write((byte)(char)10);  
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();    
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
    }

    @Override
    public String printLineCenter(String texto) {
    try {
    ps.write(epson.feedLine());    
    ps.write(epson.setCenter());
    ps.write(epson.setFontCompElectronico());
    ps.write(texto.getBytes());
    ps.write((byte)(char)10);
    ps.write(epson.setLefth());
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();   
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
    return texto.getBytes().toString();
    }

    @Override
    public void stepLine() {
    try{
    ps.println();
    }catch(Exception ex){
    String message;
    message = ex.getMessage();    
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }    
    }

    @Override
    public void printLineBold(String texto) {
    try{
    ps.write(epson.feedLine());
    ps.write(epson.setCenter());
    ps.write(epson.setOnPrintBold());//pendiente no funciona
    //ps.println(texto);
    ps.write(texto.getBytes());
    ps.write((byte)(char)10);
    ps.write(epson.setOffPrintBold());
   
    }catch (IOException ex) {
    String message;
    message = ex.getMessage(); 
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
    }

    @Override
    public void printLineDoubleSize(String texto) {
    try {
    ps.write(epson.feedLine());
    ps.write(epson.setCenter());//
    ps.write(epson.setOnPrintBold());//
    ps.write(epson.setFont1());
    ps.write(texto.getBytes());
    ps.write(epson.setOffPrintBold());//
    ps.write(epson.setFontCompElectronico());
    ps.write((byte)(char)10);
    ps.write(epson.setLefth());//
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();     
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
        
    }

    @Override
    public String printEspacioBlanco(int numEspacio) {
    return util.espacioBlanco(numEspacio);
    }

    @Override
    public void printLineDotted(int maxPoint) {
    ps.println(util.guion(maxPoint));
    }

    @Override
    public void printLineRigth(String texto) {
    try {
    ps.write(epson.setFontCompElectronico());
    ps.write(epson.setRigth());
    ps.write(texto.getBytes());
    ps.write((byte)(char)10);
    ps.write(epson.setLefth());
    } catch (IOException ex) {
    String message;
    message = ex.getMessage();    
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
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
            ps.write(epson.inicializate());
        } catch (IOException ex) {
            String message;
            message = ex.getMessage();
            Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
        }
    }
    
    @Override
    public void printLogo(String codMarca) {
    try {
        ps.write(epson.setCenter());
        printImage(codMarca);
        ps.write(epson.setLefth());
        } catch (IOException ex) {
        Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, null, ex);
    } 
    }
    /***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
    
    /**
     *
     * @author :Cesar Huanes
     * @param codMarca
     * @return
     * @since :23/10/2014
     */
    public BufferedImage getImage(String codMarca){
    String dirName=util.getPathLogo(codMarca);
    ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
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
     } catch (IOException ex) {
        Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, null, ex);
     
     
    }
     return imag;  
    }
    /***
    * @author:Cesar Huanes
     * @param image
     * @return 
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
     * @param codMarca
    * @since :23/10/2014
    */
    public void printImage(String codMarca) {
    
     int[][] pixels=this.getPixelsSlow(this.getImage(codMarca));
     try {
       
        ps.write(SET_LINE_SPACE_24);
        ps.write(epson.setCenter()); 
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
    public void printGeneral(String texto, String tamanio, String alineacion, boolean isNegrita, int lineado){
        
        try{
            // DIFERENTE A IMPRESION PDF457
            if(!"P".equalsIgnoreCase(alineacion)){
                if(lineado==0){
                    ps.write(epson.feedLine());
                }else{
                    ps.write(epson.feedLineDouble());
                }
                
                ps.write(epson.setOffPrintBold());
                
                
                switch(alineacion){
                    //DERECHA
                    case "D"    :   ps.write(epson.setRigth());
                                    break;
                    //CENTRO
                    case "C"    :   ps.write(epson.setCenter());
                                    break;
                    //IZQUIERDA
                    case "I"    :   ps.write(epson.setLefth());
                                    break;
                    
                }
                // FORMATO NEGRITA
                if(isNegrita){
                    ps.write(epson.setOnPrintBold());
                }
                
                int tamanioFuente = Integer.parseInt(tamanio);
                switch(tamanioFuente){
                    case 0    :   ps.write(epson.setFont0());
                                  break;
                    case 1    :   ps.write(epson.setFont1());
                                  break;
                    case 2    :   ps.write(epson.setFont2());
                                  break;
                    case 3    :   ps.write(epson.setFont3());
                                  break;
                    case 4    :   ps.write(epson.setFont4());
                                  break;
                    case 5    :   ps.write(epson.setFont5());
                                  break;
                    case 6    :   ps.write(epson.setFont6());
                                  break;
                    case 7    :   ps.write(epson.setFont7());
                                  break;
                    case 9    :   ps.write(epson.setFontCompElectronico());
                                  break;
                    default   :   ps.write(epson.setFontCompElectronico());
                                    
                }
                
                //ps.write(epson.setFontA());
                // SE IMPRIME EL TEXTO
                if(texto!=null){
                    ps.write(texto.getBytes());
                    ps.write((byte)(char)10);
                }else{
                    ps.println();
                }
                
                //EN CASO FORMATO NEGRITA QUITA LA CONFIGURACION.
                if(isNegrita){
                    ps.write(epson.setOffPrintBold());
                }

                
            }else{
                // IMPRESION DE FORMATO PDF457
                int longitudTrama = texto.getBytes().length;
                System.out.print("trama long"+longitudTrama);
                int nl = texto.getBytes().length%256;
                int nh = texto.getBytes().length/256;
                ps.flush();
                ps.write(epson.setCenterPDF417());
                ps.write(epson.setNumRowsPDF417());
                ps.write(epson.setRowHeigth());
                ps.write(epson.setCorrectionLevel());
                ps.write(epson.setParameter1PDF417());
                ps.write(epson.setParameter2PDF417());
                ps.write(epson.setOption());
                ps.write(epson.setParameter3PDF417(nl,nh));
                ps.write(texto.getBytes());
                ps.write(epson.setParameter4PDF417());
                ps.write(epson.setDimension());
                ps.write(epson.setLefth());
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
            jbcode.setBarHeight(25);
            jbcode.setWideRatio(6);
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
         printCodeBarImpEpson(pCodigo);
         /*
         // Impresion x Imagen
         ps.write(epson.setCenter());
         
         int[][] pixels=this.getPixelsSlow(getCodeBar(pCodigo));
         try {
           
            ps.write(SET_LINE_SPACE_24);
            ps.write(epson.setCenter()); 
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
         
         ps.write(epson.setLefth());        */ 
         
     //} catch (IOException ex) {
     } catch (Exception ex) {
         Logger.getLogger(FarmaPrinterStarFacade.class.getName()).log(Level.SEVERE, null, ex);
      } 
    }   
    
    public void writelnDU(String linea, BufferedWriter _bufOutput){
    try
    {

    /*
    // Create file
    //FileWriter fstream = new FileWriter("LPT1");
    BufferedWriter out = new BufferedWriter(fstream);
    out.write("Hello Java");
    //Close the output stream
    out.close();
    */

    // Create file
    //FileWriter fstream = new FileWriter("out.txt");
    /*BufferedWriter _bufOutput = new BufferedWriter(fstream);

    _bufOutput.write(linea, 0, linea.length());
    _bufOutput.newLine(); */

    _bufOutput.write(linea, 0, linea.length());
    _bufOutput.newLine();
    }catch (Exception e) {
    e.printStackTrace();
    }

    }
    
    public void abrirGabeta(){
        ps.println((char)27+"p"+(char)0+(char)25+(char)250);
    }
    
    
    public void printCodeBarImpEpson(String trama) {
    try {
        ps.write(new byte[] {(byte)chr(27), (byte)'a', (byte)chr(1)});
        ps.write(new byte[]{(byte)chr(29), (byte)'h',(byte)chr(80)});
        ps.write(new byte[]{(byte)chr(29), (byte)'w',(byte)chr(3)});
        ps.write(new byte[]{(byte)chr(29), (byte)'k',(byte)chr(5)}); // CODIGO TIPO 5 ITF
        ps.write(trama.getBytes());
        ps.write(new byte[]{(byte)chr(0)});
        ps.println("");
        ps.println(trama.trim());
    } catch (Exception ex) {
    String message;
    message = ex.getMessage();
    Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
    }
    }

    public char chr(int i){
    return (char)i;
    }
    
}
