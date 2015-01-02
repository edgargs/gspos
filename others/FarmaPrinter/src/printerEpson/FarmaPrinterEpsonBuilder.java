/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerEpson;
/**
 *
 * @author Cesar Huanes
 * @since  21-07-2014
 * @version  1.0
 */
public class FarmaPrinterEpsonBuilder {
 private  char activateBold[] = { (char)27, 'E' };//Activa letra en legrita
 private  char deactivateBold[] = { (char)27, 'F' };//Desactiva letra en negrita
public FarmaPrinterEpsonBuilder(){
    
} 
//NUMERO DE COLUMNAS
public byte[] setParameter1PDF417(){
byte[] param1=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(48),
(byte)chr(65), (byte)chr(22)};//23
return param1;//dimension en x
}
//NUMERO DE FILAS
public byte[] setNumRowsPDF417(){
byte[] rows=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(48),
(byte)chr(66), (byte)chr(0)};//0
return rows;//
}
//WITH DE MODULE
public byte[] setParameter2PDF417(){
byte[] param2=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(48),
(byte)chr(67), (byte)chr(1)};//CUANDO ES UNO IMPRIME HASTA  1024.                         
return param2;
}
//SET ERROR HEIGHT
public byte[] setRowHeigth(){//dimension en y
byte[] rowHeigth=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(48),
(byte)chr(68), (byte)chr(8)};//5                            
return rowHeigth;   
}
//SET ERROR CORRECTION LEVEL
public byte[] setCorrectionLevel(){
byte[] correction=new byte[]{(byte)chr(29),(byte)'(',(byte)'k',(byte)chr(4),(byte)chr(0),(byte)chr(48),(byte)chr(69),
(byte)chr(48),(byte)chr(53)};//53
return correction;
}
//SELECT THE OPTION--TIPO DE PDF417
public byte[] setOption(){
byte[] option=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(48),
(byte)chr(70), (byte)chr(0)};//0
return option;
}
//STORAGE THE DATA IN THE SYMBOL STOGARE DATA.
public byte[] setParameter3PDF417(int nl, int n2){
byte[] param3=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(nl+3), (byte)chr(n2),
(byte)chr(48), (byte)chr(80), (byte)chr(48) };//48
return param3;
}
//PRINT THE SYMBOL IN THE STORAGE DATA
public byte[] setParameter4PDF417(){
byte[] param4=new byte[]{ (byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(48),
(byte)chr(81), (byte)chr(48)};//48
return param4;
}
//PRINT THE CENTER PDF417
public byte[] setCenterPDF417(){
 byte[] center= new byte[] { (byte)chr(27), (byte)'a', (byte)chr(1) };
 return center;
}
// TRANSMIT THE SIZE INFORMATION IN THE SYMBOL DATA STORAGE AREA.
public byte[] setDimension(){
byte[] dimension=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(48),
(byte)chr(82), (byte)chr(48)};
 return dimension;   
}

public byte[] setParameter1QR(){
byte[] param1=new byte[]{(byte)chr(27), (byte)'a', (byte)chr(1)};
return param1;
}
public byte[] setParameter2QR(){
byte[] param2=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(49),
                                  (byte)chr(67), (byte)chr(4) };
return param2;
}
public byte[] setParameter3QR(){
byte[] param3=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(49),
                                  (byte)chr(69), (byte)chr(51) };
return param3;
}
public byte[] setParameter4QR(int textLength ){
byte[] param4=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(textLength + 3), (byte)chr(0),
                                  (byte)chr(49), (byte)chr(80), (byte)chr(48)};
return param4;
}
public byte[] setParameter5QR(){
byte[] param5=new byte[]{(byte)chr(29), (byte)'(', (byte)'k', (byte)chr(3), (byte)chr(0), (byte)chr(49),
                                  (byte)chr(81), (byte)chr(48)};
return param5;
}
    
    public byte[] setCutPaper() {
        byte[] cutPaper = new byte[] { (byte)chr(29), (byte)'V', (byte)chr(66), (byte)chr(3) };
        return cutPaper;
    }
    
    // KMONCADA 17.12.2014 TAMANIO DE FUENTES
    public byte[] setFont1() {
        byte[] fontA = new byte[] { (byte)chr(29), (byte)'!', (byte)chr(17) }; //M-48
        return fontA;
    }
    
    public byte[] setFontCompElectronico() {
        byte[] fontB = new byte[] { (byte)chr(27), (byte)'!', (byte)chr(1) };
        return fontB;
    }
    
    public byte[] setFont0() {
        byte[] fontB = new byte[] { (byte)chr(27), (byte)'!', (byte)chr(0) };
        return fontB;
    }
    
    public byte[] setFont2() {
        byte[] fontA = new byte[] { (byte)chr(29), (byte)'!', (byte)chr(34) }; //M-48
        return fontA;
    }
    
    public byte[] setFont3() {
        byte[] fontA = new byte[] { (byte)chr(29), (byte)'!', (byte)chr(51) }; //M-48
        return fontA;
    }
    
    public byte[] setFont4() {
        byte[] fontA = new byte[] { (byte)chr(29), (byte)'!', (byte)chr(68) }; //M-48
        return fontA;
    }
    public byte[] setFont5() {
        byte[] fontA = new byte[] { (byte)chr(29), (byte)'!', (byte)chr(85) }; //M-48
        return fontA;
    }
    public byte[] setFont6() {
        byte[] fontA = new byte[] { (byte)chr(29), (byte)'!', (byte)chr(102) }; //M-48
        return fontA;
    }
    
    public byte[] setFont7() {
        byte[] fontA = new byte[] { (byte)chr(29), (byte)'!', (byte)chr(119) }; //M-48
        return fontA;
    }
    
    
public byte[] setUnderLine(){
byte[] underLine=new byte[]{ (byte)chr(27), (byte)'-', (byte)chr(2)};    
return underLine;
}
public byte[] setOffUnderLine(){
byte[] offUnderLine=new byte[]{ (byte)chr(27), (byte)'-', (byte)chr(0)};    
return offUnderLine;
}
public byte[] setCenter(){
byte[] center=new byte[]{ (byte)chr(27), (byte)'a', (byte)chr(1) };    
return center;
}
public byte[] setLefth(){
byte[] lefth=new byte[]{ (byte)chr(27), (byte)'a', (byte)chr(0)};    
return lefth;
}
public byte[] setRigth(){
byte[] rigth=new byte[]{ (byte)chr(27), (byte)'a', (byte)chr(2)};    
return rigth;
}
    
    public byte[] feedLine() {
        byte[] feedLine = new byte[] { (byte)chr(27), (byte)chr(51), (byte)chr(40) }; //espacio entre linea
        return feedLine;
    }
    
    public byte[] feedLineDouble() {
        byte[] feedLine = new byte[] { (byte)chr(27), (byte)chr(50)};
        return feedLine;
    }

    public byte[] inicializate() {
        byte[] inicializate = new byte[] { (byte)chr(29), (byte)chr(64) };
        return inicializate;
    }
    
    public byte[] setOnPrintBold() {
        byte[] bold = new byte[] { (byte)chr(27), 'G', (byte)chr(1) };
        return bold;
    }
    
    public byte[] setOffPrintBold() {
        byte[] bold = new byte[] { (byte)chr(27), 'G', (byte)chr(0) };
        return bold;
    }
private static char chr(int i){
return (char)i;
}

    /**
     * @return the activateBold
     */
    public char[] getActivateBold() {
        return activateBold;
    }

    /**
     * @return the deactivateBold
     */
    public char[] getDeactivateBold() {
        return deactivateBold;
    }

    
}
