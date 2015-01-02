/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerUtil;

import javax.swing.JOptionPane;
import printerEpson.FarmaPrinterEpsonBuilder;
import printerStar.FarmaPrinterStarBuilder;


/**
 * @author Cesar Huanes
 * @since  21-07-2014
 * @version  1.0
 */
public class FarmaUtil implements FarmaInterfaceUtil {
     private final String pathMifarma="C:\\farmaventa\\imagenes\\LogoEMifarma.jpg";    
    private final String pathBtl="C:\\farmaventa\\imagenes\\LogoEBTL.jpg";
    private final String pathFasa="C:\\farmaventa\\imagenes\\LogoEFasa.jpg";
    private final String pathMiMarket="C:\\farmaventa\\imagenes\\LogoEMimarket.jpg";
    FarmaPrinterEpsonBuilder epson=new FarmaPrinterEpsonBuilder();
    FarmaPrinterStarBuilder star=new FarmaPrinterStarBuilder();
    public FarmaUtil(){    
     }

    /**
     *
     * @param numEspacio
     * @return
     */
    @Override
    public String espacioBlanco(int numEspacio){
    StringBuilder builder =new StringBuilder();
    for(int i=0;i<numEspacio;i++){
        builder.append(" ");
    }
    return  builder.toString();        
    }
    
    @Override
    public String alineaMontos(String texto){
    StringBuilder builder=new StringBuilder();
    int longitudTexto=texto.length();
    if(longitudTexto<12){
    for(int i=0;i<12-longitudTexto;i++){
     builder.append(" ");
    }
    }
    return builder.toString().concat(texto);
    }
    
    @Override
    public String alineaDetalle(String codigo, String descripcion, String cant, String preUni,String desc, String importe){
    int longCodigo=codigo.length();
     int longDescri=descripcion.length();
     int longCantid=cant.length();
     int longPrecio=preUni.length();
     int longImport=importe.length();
     int longDesc=desc.length();
     StringBuilder builderDesc=new StringBuilder();
     StringBuilder builderCant=new StringBuilder();
     StringBuilder builderPrecio=new StringBuilder();
     StringBuilder builderDescu=new StringBuilder();
     StringBuilder builderImporte=new StringBuilder();
     StringBuilder espacio=new StringBuilder();
     String cod=null;
     String des=null;
     String can=null;
     String pre=null;
     String descuento=null;
     String imp=null;
     for(int i=0;i<2;i++){
     espacio.append(" ");
     }
     if(longCodigo<=6){    
     cod=codigo;
     }
    //25
     if(longDescri<=21){
      for(int i=0;i<21-longDescri;i++){   
     builderDesc.append(" ");
     }
     des=descripcion.concat(builderDesc.toString());
     }else{
     des=descripcion.substring(0,21);
     }
     
     if(longCantid<8){
     for(int k=0;k<8-longCantid;k++){
     builderCant.append(" ");
     }
     can=builderCant.toString().concat(cant);
     }else{
     can=cant;    
     }
    
     if(longPrecio<8){
      for(int j=0;j<8-longPrecio;j++){
     builderPrecio.append(" ");
     }
     pre=builderPrecio.toString().concat(preUni);
     }else{
     pre=preUni;   
     }
     
     if(longDesc<8){
      for(int j=0;j<8-longDesc;j++){
     builderDescu.append(" ");
     }
     descuento=builderDescu.toString().concat(desc);
     }else{
     descuento=desc;     
     }
    
     if(longImport<9){
     for(int m=0;m<9-longImport ;m++){
     builderImporte.append(" ");
     }
     imp=builderImporte.toString().concat(importe);
     }else{
     imp=importe;    
     }
     return cod+espacio.toString()+des+can+espacio.toString()+pre+descuento+imp;
        
    }

    @Override
    public String guion(int maxPoint) {
    StringBuilder builder=new StringBuilder();
    String guion=null;
    for(int i=0;i<=maxPoint;i++){
    builder.append("-");
    guion= builder.append("-").toString();
    }
    return guion;         
    }

    @Override
    public String getPathLogo(String codMarca) {
     String pathLogo=null;   
     
     if(codMarca.equalsIgnoreCase(FarmaPrinterConstants.MIFARMA)){
      pathLogo=this.getPathMifarma();
     }else if(codMarca.equalsIgnoreCase(FarmaPrinterConstants.FASA)){
     pathLogo=this.getPathFasa();
     }else if(codMarca.equalsIgnoreCase(FarmaPrinterConstants.BTL)){
     pathLogo=this.getPathBtl();
     }else if(codMarca.equalsIgnoreCase(FarmaPrinterConstants.MIMARKET)){
     pathLogo=this.getPathMiMarket();
     }else{
     JOptionPane.showMessageDialog (null, FarmaPrinterConstants.ERRORLOGO, FarmaPrinterConstants.ERRORIMP, JOptionPane.ERROR_MESSAGE);
    
     }
     
    return pathLogo;
    }

    /**
     * @return the pathMifarma
     */
    public String getPathMifarma() {
        return pathMifarma;
    }

    /**
     * @return the pathMiBtl
     */
    public String getPathBtl() {
        return pathBtl;
    }

    /**
     * @return the pathFasa
     */
    public String getPathFasa() {
        return pathFasa;
    }

    /**
     * @return the pathMiMarket
     */
    public String getPathMiMarket() {
        return pathMiMarket;
    }

      
}
