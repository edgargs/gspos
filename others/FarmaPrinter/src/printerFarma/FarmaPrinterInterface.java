/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerFarma;

/**
 *
 * @author Cesar Huanes
 * @since  21-07-2014
 * @version  1.0
 */
public interface FarmaPrinterInterface {
  /**Verifica que se inicialice la impresion
     * @return .*/
    public boolean startPrintService();
    /**Genera codigo de barra en formato PDF417. Dimensiones 
     * @param trama
     */
    public void printCodePDF417(String trama);
    /**Genera codigo de barra en formato QR
     * @param trama.*/
    public void printCodeQR(String trama);
    /**Finaliza la Impresion.*/
    public void endPrintService(boolean vCobro);
    /**Corta del Papel cuando finaliza la impresion*/
    public void cutPaper();
    /**Salto de Linea.*/
    public void stepLine();
    /**Texto en letra negrita
     * @param texto.*/
    public void printLineBold(String texto);
    /**Texto subrayado
     * @param texto.*/
    public void printUnderLine(String texto);
    /**Texto en fuente normal
     * @param texto.*/
   public void printLine(String texto);
    /**Texto en fuente doble tamaño
     * @param texto.*/
    public void printLineDoubleSize(String texto);
    /**Justifica el texto centrado
     * @param texto.
     * @return */
    public String printLineCenter(String texto);
    /**Deja espacio en blanco segun el parametro
     * @param numEspacio.
     * @return */
    public String printEspacioBlanco(int numEspacio);
    /**Imprime linea punteada ,MaxPoint range 20-30
     * @param maxPoint.*/
    public void printLineDotted(int maxPoint);
    /**Justifica el texto a la derecha,usar de preferencia cuando imprime  monto totales.Ejm:printer.printLineRigth(gravadas+printer.ajusteAutomatico("33.56")).
     * @param texto */
    public void printLineRigth(String texto);
    /**Imprime la descripcion de los montos totales con su respectivo monto.Ejm:printer.printLineRigth(gravadas+printer.ajusteAutomatico("33.56"))
     * @param texto.
     * @return */
    public String alineaMontos(String texto);
    /**Imprime el cuerpo de la descripción alineando y justificando automaticamente
     * @param codigo.
     * @param descripcion
     * @param cant
     * @param preUni
     * @param desc
     * @param importe
     * @return */
    public String alineaDetalle(String codigo,String descripcion,String cant,String preUni,String desc,String importe);
    /**Resetea la impresora y establece los valores por default*/
    public void inicializate(); 
       /**Imprime el logo de la marc
     * @param codMarcaa*/
    public void printLogo(String codMarca);
    
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
    public void printGeneral(String texto, String tamanio, String alineacion, boolean isNegrita, int lineado);
}
