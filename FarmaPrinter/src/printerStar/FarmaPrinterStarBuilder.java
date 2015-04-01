/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerStar;

/**
 * @author Cesar Huanes
 * @since  21-07-2014
 * @version  1.0
 */
public class FarmaPrinterStarBuilder {
     private char activateBold[] = { (char)27, 'E' };//Activa letra en legrita
    private char deactivateBold[] = { (char)27, 'F' };//Desactiva letra en negrita
    private char activateUnderline[] = { (char)27, (char)45, (char)49 };//Activa letra subyarado
    private char deactivateUnderline[] = { (char)27, (char)45, (char)48 };//Desactiva letra subrayado
    //private char activateDoubleWidthMode[] = { (char)27, (char)87, (char)51 };//Códigos necesarios para activar Tamaño Doble de Letra 
    private char activateDoubleWidthMode[] = { (char)27, (char)87, (char)1 };//Códigos necesarios para activar Tamaño Doble de Letra 
    private char deactivateDoubleWidthMode[] = { (char)27, (char)87, (char)48 };//Códigos necesarios para desactivar Tamaño Doble de Letra 


    public FarmaPrinterStarBuilder(){
        
    }
   //SET BARCODE SIZE
    public byte[] setModelCodeBarraPDF417(){  
    byte[] modelCodeBarraPDF417=new  byte[]{(byte)chr(27), (byte)chr(29), (byte)'x', (byte)'S', (byte)'0', (byte)chr(1), (byte)chr(40), (byte)chr(30)};//40-30
    return modelCodeBarraPDF417;
    }
  //SET SECURITY LEVEL
    public byte[] setEscaleNivelPDF417(){
    byte[] nivelCodeBarraPDF417 = new byte[]{(byte)chr(27), (byte)chr(29), (byte)'x', (byte)'S', (byte)'1', (byte)chr(5)}; //5
    return nivelCodeBarraPDF417;
    }
    // SET MODULE X DIRECTION SIZE
    public byte[] setDirectionXPDF417(){
    byte[] directionXPDF417=new byte[]{(byte)chr(27), (byte)chr(29), (byte)'x', (byte)'S', (byte)'2', (byte)chr(1)}; //SIEMPRE DEBE SER  1
    return directionXPDF417;
    }
    //SET MODULE ASPECT RATIO
    public byte[] setRatioPDF417(){
    byte[] ratioPDF417=new byte[]{(byte)chr(27), (byte)chr(29), (byte)'x', (byte)'S', (byte)'3', (byte)chr(10)}; //:MAXIMO  10-6
    return ratioPDF417;
    }
    //SET BARCODE DATA
    public byte[] setDataPDF417(int nl,int nh){ 
    byte[]  dataPDF417 = new byte[]{(byte)chr(27), (byte)chr(29), (byte)'x', (byte)'D', (byte)chr(nl),(byte)chr(nh)}; 
    return  dataPDF417;
    }
    //SET EXPANTION INFORMATION CHECK
    public byte[] setBarCodeExpantionPDF417(){
    byte[] expantionPDF417=new byte[]{(byte)chr(27), (byte)chr(29), (byte)'x', (byte)'I',(byte)chr(0)};
    return expantionPDF417;
    }
    //PRINT BARCODE DATA
    public byte[] setPrintPDF417(){
    byte[] printPDF417=  new byte[]{(byte)chr(27), (byte)chr(29), (byte)'x', (byte)'P'}; 
    return printPDF417;
    }
   
    public byte[] setModelCodeBarraQR(){
    byte[]  modelCodeBarraQR=new byte[]{(byte)chr(27), (byte)chr(29), (byte)'y', (byte)'S', (byte)'0', (byte)chr(2)};
    return  modelCodeBarraQR;
    }
    
    
    public byte[] setEscaleNivelQR(){
     byte[]   nivelCodeBarraQR=new byte[]{(byte)chr(27), (byte)chr(29), (byte)'y', (byte)'S', (byte)'1', (byte)chr(3)};
     return   nivelCodeBarraQR;
    }
    public byte[] setDirectionXQR(){
    byte[] directionXQR= new byte[]{(byte)chr(27), (byte)chr(29), (byte)'y', (byte)'S', (byte)'2', (byte)chr(4)};  
    return directionXQR;
    }
   
    public byte[] setDataQR(int nBytesCodeData){
    byte[]   dataQR= new byte[]{(byte)chr(27), (byte)chr(29), (byte)'y', (byte)'D',(byte)'1', (byte)chr(0), (byte)chr(nBytesCodeData), (byte)chr(0)};
    return   dataQR;
    }
   
    
    public byte[]setBarCodeExpantionQR(){
    byte[]   expantionQR= new byte[]{(byte)chr(27), (byte)chr(29), (byte)'y', (byte)'I'};
    return   expantionQR;
    }
    
    public byte[] setPrintQR(){
     byte[]  printQR =new byte[]{(byte)chr(27), (byte)chr(29), (byte)'y', (byte)'P'};
     return  printQR; 
    }
    
    public byte[] cutePaper(){
    byte[] cutPaper=new byte[]{(byte)chr(27), (byte)'d', (byte)'0'} ; 
    return cutPaper;
    }
    public byte[] setPrintCenter(){
    byte[] printCenter=new byte[]{(byte)chr(27),(byte)chr(29),(byte)'a', (byte)chr(49)};
    return printCenter;    
    }
    public byte[] setPrintLefth(){
    byte[] printCenter=new byte[]{(byte)chr(27),(byte)chr(29),(byte)'a', (byte)chr(48)};
    return printCenter;    
    }
    public byte[] setPrintRigth(){
    byte[] printCenter=new byte[]{(byte)chr(27),(byte)chr(29),(byte)'a', (byte)chr(50)};
    return printCenter;    
    }
    
    public byte[] setFont(){
        byte[] font=new byte[]{(byte)chr(27),(byte)chr(30),(byte)'F',(byte)chr(1)};
        return font;
    }
    
    public byte[] feedLine() {
        byte[] feedLine = new byte[] { (byte)chr(27), (byte)'z', (byte)chr(0) }; //espacio entre linea
        return feedLine;
    }
    public byte[] inicializate(){
        byte[] inicializate=new byte[]{(byte)chr(27),(byte)'@'};
        return inicializate;
    }
   
    private static char chr(int i){
    return (char)i;
    }  
   
    public char[] getActivateBold() {
        return activateBold;
    }

    /**
     * @param activateBold the activateBold to set
     */
    public void setActivateBold(char[] activateBold) {
        this.setActivateBold(activateBold);
    }

    /**
     * @return the deactivateBold
     */
    public char[] getDeactivateBold() {
        return deactivateBold;
    }

    /**
     * @param deactivateBold the deactivateBold to set
     */
    public void setDeactivateBold(char[] deactivateBold) {
        this.setDeactivateBold(deactivateBold);
    }


    /**
     * @return the activateUnderline
     */
    public char[] getActivateUnderline() {
        return activateUnderline;
    }

    /**
     * @param activateUnderline the activateUnderline to set
     */
    public void setActivateUnderline(char[] activateUnderline) {
        this.activateUnderline = activateUnderline;
    }

    /**
     * @return the deactivateUnderline
     */
    public char[] getDeactivateUnderline() {
        return deactivateUnderline;
    }

    /**
     * @param deactivateUnderline the deactivateUnderline to set
     */
    public void setDeactivateUnderline(char[] deactivateUnderline) {
        this.deactivateUnderline = deactivateUnderline;
    }

    /**
     * @return the activateDoubleWidthMode
     */
    public char[] getActivateDoubleWidthMode() {
        return activateDoubleWidthMode;
    }

    /**
     * @param activateDoubleWidthMode the activateDoubleWidthMode to set
     */
    public void setActivateDoubleWidthMode(char[] activateDoubleWidthMode) {
        this.activateDoubleWidthMode = activateDoubleWidthMode;
    }

    /**
     * @return the deactivateDoubleWidthMode
     */
    public char[] getDeactivateDoubleWidthMode() {
        return deactivateDoubleWidthMode;
    }

    /**
     * @param deactivateDoubleWidthMode the deactivateDoubleWidthMode to set
     */
    public void setDeactivateDoubleWidthMode(char[] deactivateDoubleWidthMode) {
        this.deactivateDoubleWidthMode = deactivateDoubleWidthMode;
    }
    
    public char[] setFontCompElectronico(){
        char[] font=new char[]{(char)27,(char)30,(char)'F',(char)1};
        return font;
    }
    
    public byte[] setFontDefault(){
        byte[] font=new byte[]{(byte)chr(27),(byte)chr(30),(byte)'F',(byte)chr(0)};    
        return font;
    }
    
    public byte[] feedLineDouble() {
        byte[] feedLine = new byte[] { (byte)chr(27), (byte)'z', (byte)chr(1) }; //espacio entre linea
        return feedLine;
    }
    
    public char[] setFont0(){
        char[] font = new char[]{(char)27,(char)105,(char)0, (char)0};
        return font;
    }
    
/*     public byte[] setFont0Y(){
        byte[] font = new byte[]{(byte)chr(27),(byte)chr(104),(byte)0};
        return font;
    } */
    
    public char[] setFont1(){
        char[] font = new char[]{(char)27,(char)105, (char)1, (char)1};
        return font;
    }
    
/*     public byte[] setFont1Y(){
        byte[] font = new byte[]{(byte)chr(27),(byte)chr(104),(byte)1};
        return font;
    } */
    
    
    public char[] setFont2(){
        char[] font = new char[]{(char)27,(char)105,(char)2, (char)2};
        return font;
    }
    
/*     public byte[] setFont2Y(){
        byte[] font = new byte[]{(byte)chr(27),(byte)chr(104),(byte)2};
        return font;
    } */

    public char[] setFont3(){
        char[] font = new char[]{(char)27,(char)105, (char)3, (char)3};
        return font;
    }
    
/*     public byte[] setFont3Y(){
        byte[] font = new byte[]{(byte)chr(27),(byte)chr(104),(byte)3};
        return font;
    } */
    
    public char[] setFont4(){
        char[] font=new char[]{ (char)27, (char)105, (char)4, (char)4}; 
        return font;
    }
    
/*     public char[] setFont4Y(){
        char[] font=new char[]{ (char)27, (char)104, (char)4}; 
        return font;
    } */
    
    public char[] setFont5(){
        char[] font=new char[]{ (char)27, (char)105, (char)5, (char)5}; 
        return font;
    }
    
/*     public char[] setFont5Y(){
        char[] font=new char[]{ (char)27, (char)104, (char)5}; 
        return font;
    } */
    
    public char[] setInvertido(){
        char[] font=new char[]{ (char)27, (char)52}; 
        return font;
    }
    
    public char[] setRaster(){
        char[] font=new char[]{ (char)27, (char)42, (char)114, (char)81, (char)2, (char)0}; 
        return font;
    }
}
