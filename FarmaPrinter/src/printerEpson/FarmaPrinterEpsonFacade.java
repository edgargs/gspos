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

import java.util.Map;
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
            this.cutPaper();
            ps.flush();
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
    
    public void printLineSimbolo(int maxPoint,String pSimbolo) {
    ps.println(util.simbolo(maxPoint,pSimbolo));
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
                if("L".equalsIgnoreCase(alineacion)){
                    ps.write(epson.setCenter());
                    printImage(texto);
                    ps.write(epson.setLefth());
                }else{
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
                        parsePrintLine(texto); //ASOSA - 18/03/2015 - PTOSYAYAYAYA
                        /*
                            ps.write(texto.getBytes());
                            ps.write((byte)(char)10);
                        */
                    }else{
                        ps.println();
                    }
                    
                    //EN CASO FORMATO NEGRITA QUITA LA CONFIGURACION.
                    if(isNegrita){
                        ps.write(epson.setOffPrintBold());
                    }
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
        if(isEan13(trama))
            ps.write(new byte[]{(byte)chr(29), (byte)'k',(byte)chr(2)}); // CODIGO TIPO 2 EAN 13
        else
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
    

    public void endService() {
        try {
            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    

    public boolean isEan13(String pTrama) {
        if (pTrama.trim().length() == 13) {
            int acum = 0;
            boolean par = false;
            String arrayDigitos = pTrama.substring(0, 12);
            for (int i = 0; i < 12; ++i) {
                if (par) {
                    acum += Integer.parseInt(arrayDigitos.charAt(i) + "") * 3;
                } else {
                    acum += Integer.parseInt(arrayDigitos.charAt(i) + "");
                }
                par = !par;
            }

            int pDigito = 0;
            if ((acum % 10) == 0)
                pDigito = 0;
            else
                pDigito = 10 - acum % 10;
            int pDig = Integer.parseInt(pTrama.charAt(12) + "");
            if (pDigito == pDig)
                return true;
            else
                return false;
        } else
            return false;
    }
    
    
    public void printMap(Map vFilaMap){

        String vIpPc       =  ((String)vFilaMap.get("IP_PC")).trim();
        String vIdDoc      =  ((String)vFilaMap.get("ID_DOC")).trim();
        int    vOrden      =  (Integer.parseInt(vFilaMap.get("ORDEN").toString().trim()));
        int    vTipoVal    =  (Integer.parseInt(vFilaMap.get("TIPO_VAL").toString().trim()));
        int    vTamanio    =  (Integer.parseInt(vFilaMap.get("TAMANIO").toString().trim()));
        int    vAlineacion =  (Integer.parseInt(vFilaMap.get("ALINEACION").toString().trim()));
        int    vNegrita    =  (Integer.parseInt(vFilaMap.get("NEGRITA").toString().trim()));
        int    vEspaciado  =  (Integer.parseInt(vFilaMap.get("ESPACIADO").toString().trim()));
        int    vInvColor   =  (Integer.parseInt(vFilaMap.get("INV_COLOR").toString().trim()));
        String vCadena     =  (vFilaMap.get("VAL_CADENA").toString().trim());
        
        /******* * TIPO DE VALOR * ********/ 
        int V_TEXTO     = 1; 
        int V_PDF417    = 2; 
        int V_BARCODE   = 3; 
        int V_QR        = 4; 
        int V_LOGO      = 5; 
        int V_REPETIR_SIMBOLO = 6;  
        int V_LINEA_BLANCO    = 7; 
        /******* * ACTIVAR NEGRITA * ********/ 
        int BOLD_A   = 1;
        /******* * ACTIVAR ESPACIADO ENTRE LINEAS * ********/ 
        int ESPACIADO_I   = 0; 
        /******* * INVERTIDO_COLOR * ********/
         int INVERTIDO_COLOR_A   = 1; 
         int INVERTIDO_COLOR_I   = 0;
        try{
            
            if(vTipoVal == V_TEXTO){
                
                vCadena = getLineReemplaza(vCadena);
                    
                if(vEspaciado==ESPACIADO_I){
                    ps.write(epson.feedLine());
                }else{
                    ps.write(epson.feedLineDouble());
                }
                ps.write(epson.setOffPrintBold());
                
                switch(vAlineacion){
                    //DERECHA
                    case 3    :   ps.write(epson.setRigth());
                                    break;
                    //CENTRO
                    case 2    :   ps.write(epson.setCenter());
                                    break;
                    //IZQUIERDA
                    case 1    :   ps.write(epson.setLefth());
                                    break;
                }
                // FORMATO NEGRITA
                if(vNegrita==BOLD_A){
                    ps.write(epson.setOnPrintBold());
                }
                
                int tamanioFuente = vTamanio;
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
                
                // SE IMPRIME EL TEXTO
                if(vCadena!=null){
                    parsePrintLine(vCadena);
                    /*                                    
                    ps.write(vCadena.getBytes());
                    ps.write((byte)(char)10);*/
                }else{
                    ps.println();
                }
                
                //EN CASO FORMATO NEGRITA QUITA LA CONFIGURACION.
                if(vNegrita==BOLD_A){
                    ps.write(epson.setOffPrintBold());
                }
            }else{
                if (vTipoVal == V_PDF417){
                
                // IMPRESION DE FORMATO PDF457
                int longitudTrama = vCadena.getBytes().length;
                System.out.print("trama long"+longitudTrama);
                int nl = vCadena.getBytes().length%256;
                int nh = vCadena.getBytes().length/256;
                ps.flush();
                ps.write(epson.setCenterPDF417());
                ps.write(epson.setNumRowsPDF417());
                ps.write(epson.setRowHeigth());
                ps.write(epson.setCorrectionLevel());
                ps.write(epson.setParameter1PDF417());
                ps.write(epson.setParameter2PDF417());
                ps.write(epson.setOption());
                ps.write(epson.setParameter3PDF417(nl,nh));
                ps.write(vCadena.getBytes());
                ps.write(epson.setParameter4PDF417());
                ps.write(epson.setDimension());
                ps.write(epson.setLefth());
                }
                else if (vTipoVal == V_BARCODE)
                printCodeBar(vCadena);
                else if (vTipoVal == V_QR)
                printCodeQR(vCadena);
                else if (vTipoVal == V_REPETIR_SIMBOLO)
                printLineSimbolo(30,vCadena);
                else if (vTipoVal == V_LINEA_BLANCO)
                ps.println();
                else if (vTipoVal == V_LOGO)
                printLogo(vCadena);
            }
            
        }catch(Exception ex){
            String message = ex.getMessage(); 
            Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, message, ex);
        }        
    }
    
    public String getLineReemplaza(String pText) {
        pText = pText.replaceAll("Á", "A");
        pText = pText.replaceAll("É", "E");
        pText = pText.replaceAll("Í", "I");
        pText = pText.replaceAll("Ó", "O");
        pText = pText.replaceAll("Ú", "U");
        pText = pText.replaceAll("á", "a");
        pText = pText.replaceAll("é", "e");
        pText = pText.replaceAll("í", "i");
        pText = pText.replaceAll("ó", "o");
        pText = pText.replaceAll("ú", "u");
        pText = pText.replaceAll("ñ", "n");
        pText = pText.replaceAll("Ñ", "N");
        pText = pText.replaceAll("°", "");
        return pText;
    }
    
    /**
     * Imprimir imagen general para epson
     * @author ASOSA
     * @since 10/03/2015
     * @kind PTOSYAYAYAYA
     * @param ruta
     * @return
     */
    public void printImageEpson(String ruta) {
    
         int[][] pixels=this.getPixelsSlow(this.getImageEpsonGeneral(ruta));
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
    
    /**
     * obtener imagen general para epson
     * @author ASOSA
     * @since 10/03/2015
     * @kind PTOSYAYAYAYA
     * @param ruta
     * @return
     */
    public BufferedImage getImageEpsonGeneral(String ruta){
        String dirName = ruta;
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

    @Override
    public void printImagenEpsonGeneral(String ruta) {
        try {
            ps.write(epson.setCenter());
            printImageEpson(ruta);
            ps.write(epson.setLefth());
            } catch (IOException ex) {
            Logger.getLogger(FarmaPrinterEpsonFacade.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void parsePrintLine(String pCadena) {
        String pCadenaCopy = pCadena;
            //"ÃiÃHola CadenaÃfÃäpor favor äÃiÃcolocar normalÃfÃ";
        try {
            if (pCadenaCopy.indexOf("ä") != -1 && pCadenaCopy.indexOf("ÃfÃ") != -1&& pCadenaCopy.indexOf("ÃiÃ") != -1) {
                String[] pLista = pCadenaCopy.trim().split("ä");
                for (int i = 0; i < pLista.length; i++) {
                    String pPalabra=pLista[i];
                    if (pPalabra.indexOf("ÃiÃ")!=-1&&pPalabra.indexOf("ÃfÃ")!=-1) {
                        //Es una cadena normal sin activar nada
                        pPalabra = pPalabra.replaceAll("ÃiÃ","");
                        pPalabra = pPalabra.replaceAll("ÃfÃ","");
                        ps.print(epson.getActivateBold());
                        ps.print(epson.setOffUnderLine());
                        ps.write(pPalabra.getBytes());                        
                    } else if(pPalabra.indexOf("ËiË")!=-1&&pPalabra.indexOf("ËfË")!=-1) {
                        //Es una cadena normal sin activar nada
                        pPalabra = pPalabra.replaceAll("ËiË","");
                        pPalabra = pPalabra.replaceAll("ËfË","");
                        ps.print(epson.setUnderLine());
                        ps.print(epson.getActivateBold());
                        ps.write(pPalabra.getBytes());                        
                    } else{
                        //Es una cadena normal sin activar nada
                        ps.print(epson.setOffUnderLine());
                        ps.print(epson.getDeactivateBold());
                        ps.write(pPalabra.getBytes());
                    }
                }
                ps.write((byte)(char)10);
            } else {
                ps.write(pCadenaCopy.getBytes());
                ps.write((byte)(char)10);
            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }
    
}
