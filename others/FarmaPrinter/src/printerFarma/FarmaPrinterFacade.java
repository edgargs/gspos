/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerFarma;

import printerEpson.FarmaPrinterEpsonFacade;
import printerStar.FarmaPrinterStarFacade;

import printerUtil.FarmaPrinterConstants;

/**
 * @author Cesar Huanes
 * @since  21-07-2014
 * @version  1.0
 */
public class FarmaPrinterFacade implements FarmaPrinterInterface  {
private String modelo;
private String ruta;
FarmaPrinterStarFacade  star;
FarmaPrinterEpsonFacade epson;
 public FarmaPrinterFacade(){
 }

    public FarmaPrinterFacade(String mod, String rut,
    boolean pIndImpPuertoActivo,String pIpImp,String pNameImp
                              ) {
        this.modelo = mod;
        this.ruta = rut;
        if(this.modelo.equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)) {
            epson = new FarmaPrinterEpsonFacade(this.ruta,pIndImpPuertoActivo,pIpImp,pNameImp);
        }
        if(this.modelo.equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)) {
            star = new FarmaPrinterStarFacade(this.ruta,pIndImpPuertoActivo,pIpImp,pNameImp);
        }
    }
 
    @Override
    public boolean startPrintService() {
        boolean flag = false;
        if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)) {
            //epson=new FarmaPrinterEpsonFacade(this.getRuta()) ;
            flag = getEpson().startPrintService();
            System.out.println("Ingreso al Epson printer" + flag);
        }
        if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)) {
            //star=new FarmaPrinterStarFacade(this.getRuta());
            flag = getStar().startPrintService();
            System.out.println("Ingreso al star printer" + flag);
        }
   
    return flag;
    }

    @Override
    public void printCodePDF417(String trama) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
     getEpson().printCodePDF417(trama);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
     getStar().printCodePDF417(trama);
    }
      
    }

    @Override
    public void printCodeQR(String trama) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
    getEpson().printCodeQR(trama);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    getStar().printCodeQR(trama);
    }    
    }

    @Override
    public void endPrintService(boolean vCobro) {
        if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)) {
            if(vCobro){
                getEpson().abrirGabeta();
            }
            getEpson().endPrintService();
        }
        if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)) {
            if(vCobro){
                getStar().abrirGabeta();
            }
            getStar().endPrintService();
        }
    }

    @Override
    public void cutPaper() {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
     getEpson().cutPaper();
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
     getStar().cutPaper();
    }    
    }

    @Override
    public void stepLine() {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
     getEpson().stepLine();
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    getStar().stepLine();
    }    
    }

    @Override
    public void printLineBold(String texto) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
    getEpson().printLineBold(texto);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
     getStar().printLineBold(texto);
    }    
    }

    @Override
    public void printUnderLine(String texto) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
     getEpson().printUnderLine(texto);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    getStar().printUnderLine(texto);
    }    
    }

    @Override
    public void printLine(String texto) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
       getEpson().printLine(texto);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
       getStar().printLine(texto);
    }    
    }

    @Override
    public void printLineDoubleSize(String texto) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
      getEpson().printLineDoubleSize(texto);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
      getStar().printLineDoubleSize(texto);
    }    
    }

    @Override
    public String printLineCenter(String texto) {
    String valor=null;
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
    valor=  getEpson().printLineCenter(texto);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    valor=  getStar().printLineCenter(texto);
    }
    return valor;
    }

    @Override
    public String printEspacioBlanco(int numEspacio) {
    String espacio=null;
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
    espacio=getEpson().printEspacioBlanco(numEspacio);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    espacio=getStar().printEspacioBlanco(numEspacio);
    }
    return espacio;
    }

    @Override
    public void printLineDotted(int maxPoint) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
     getEpson().printLineDotted(maxPoint);
     }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
     getStar().printLineDotted(maxPoint);
    }    
    }
 
    @Override
    public void printLineRigth(String texto) {
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
    getEpson().printLineRigth(texto);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    getStar().printLineRigth(texto);
    }    
    }

    @Override
    public String alineaMontos(String texto) {
    String ajuste=null;
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
    ajuste= getEpson().alineaMontos(texto);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    ajuste= getStar().alineaMontos(texto);
    }
    return ajuste;
    }

    @Override
    public String alineaDetalle(String codigo, String descripcion, String cant, String preUni,String desc, String importe) {
    String alinea=null;
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
    alinea= getEpson().alineaDetalle(codigo, descripcion, cant, preUni,desc, importe);
    }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
    alinea= getStar().alineaDetalle(codigo, descripcion, cant, preUni,desc, importe);
    }
    return alinea;
    }

    @Override
    public void inicializate() {
        if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)) {
            getEpson().inicializate();
        }
        if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)) {
            getStar().inicializate();
        }
    }
   /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the ruta
     */
     
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the star
     */
    public FarmaPrinterStarFacade getStar() {
        return star;
    }

    /**
     * @return the epson
     */
    public FarmaPrinterEpsonFacade getEpson() {
        return epson;
    }

   @Override
    public void printLogo(String codMarca) {
     if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
      getEpson().printLogo(codMarca);
     }
    if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
     // getStar().printLogo(codMarca);
    getStar().printLogo2(codMarca);
   
    }       
    
    }

    public void printCodeBar(String codMarca) {
         if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOEPSON)){   
          //getEpson().printLogo(codMarca);
          getEpson().printCodeBar(codMarca);
         }
        if(this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)){
         // getStar().printLogo(codMarca);
        getStar().printCodeBar(codMarca);
        }       
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
        if(FarmaPrinterConstants.MODELOEPSON.equalsIgnoreCase(modelo)){   
            epson.printGeneral(texto, tamanio, alineacion, isNegrita, lineado);
        }
        if(FarmaPrinterConstants.MODELOSTAR.equalsIgnoreCase(modelo)){
            star.printGeneral(texto, tamanio, alineacion, isNegrita, lineado);
        }                  
    }
    
}
