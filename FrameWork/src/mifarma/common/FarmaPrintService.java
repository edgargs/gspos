package mifarma.common;

import java.io.*;

import java.util.ArrayList;

import java.sql.SQLException;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicaci�n : FarmaPrintService.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA 07.01.2006 Creaci�n<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */

public class FarmaPrintService {

    /** C�digos necesarios para activar Negritas */
    private char activateBold[] = { (char)27, 'E' };

    /** C�digos necesarios para desactivar Negritas */
    private char deactivateBold[] = { (char)27, 'F' };

    /** C�digo necesario para activar Condensando */
    private char activateCondensed[] = { (char)15 };

    /** C�digo necesario para desactivar Condensando */
    private char deactivateCondensed[] = { (char)18 };

    /** C�digos necesarios para activar Subrayado */
    private char activateUnderline[] = { (char)27, (char)45, (char)49 };

    /** C�digos necesarios para desactivar Subrayado */
    private char deactivateUnderline[] = { (char)27, (char)45, (char)48 };

    /** C�digos necesarios para activar Tama�o Doble de Letra */
    private char activateDoubleWidthMode[] = { (char)27, (char)87, (char)49 };

    /** C�digos necesarios para desactivar Tama�o Doble de Letra */
    private char deactivateDoubleWidthMode[] = 
    { (char)27, (char)87, (char)48 };
    
    private char pageREMITOLines[] = { (char)27, (char)67, (char)26 };
    /** C�digos necesarios para setear tama�o de p�gina a 10 l�neas */
    private char page10Lines[] = { (char)27, (char)67, (char)10 };

    /** C�digos necesarios para setear tama�o de p�gina a 24 l�neas - BOLETAS */
    private char page24Lines[] = { (char)27, (char)67, (char)24 };

    /** C�digos necesarios para setear tama�o de p�gina a 36 l�neas - FACTURAS */
    private char page36Lines[] = { (char)27, (char)67, (char)36 };
    
    /** C�digos necesarios para setear tama�o de p�gina a 48 l�neas - GUIAS */
    private char page48Lines[] = { (char)27, (char)67, (char)48 };

    /**
     * C�digos necesarios para setear tama�o de p�gina a 66 l�neas - PAPEL
     * CONTINUO
     */
    private char page66Lines[] = { (char)27, (char)67, (char)66 };
    
    
    private char page999Lines[] = { (char)27, (char)67, (char)999 };

    /** C�digo necesario para hacer quiebre de p�gina */
    private char pageBreak[] = { (char)12 };

    /** C�digos necesarios para hacer retorno de carro */
    private char carriageReturn[] = { (char)13 };

    /** Almacena la salida que se enviar� a la Impresora Matricial */
    private PrintStream ps;

    /** Almacena el tama�o del papel expresado en n�mero de l�neas */
    private char pageSize[];

    /** Almacena el n�mero de l�neas de la p�gina */
    private int linesPerPage = 0;

    /** Almacena el n�mero de l�neas realmente disponibles de la p�gina */
    private int printArea = 0;

    /** Almacena el n�mero de l�nea actual de impresi�n */
    private int actualLine = 0;

    /** Almacena el tama�o de la Cabecera de P�gina expresado en L�neas */
    private int headerSize = 0;

    /** Almacena el tama�o del Pie de P�gina expresado en L�neas */
    private int footerSize = 0;

    /** Almacena el Puerto de Salida para la impresi�n */
    private String devicePort = "";

    /** Indicador de Impresi�n de Fecha-Hora y N�mero de P�gina */
    private boolean includeDatePage = false;

    /** Almacena la Fecha de Emisi�n del Reporte */
    private String reportDate = "";

    /** Almacena el N�mero de P�gina por Imprimir */
    private int pageNumber = 1;

    /** Almacena la Cabecera del Reporte */
    private ArrayList arrayHeader = new ArrayList();

    /** Indicador de Seteo de Cabecera del Reporte */
    private boolean settingHeader = false;

    /** Almacena el Pie de P�gina del Reporte */
    private ArrayList arrayFooter = new ArrayList();

    /** Indicador de Seteo del Pie de P�gina del Reporte */
    private boolean settingFooter = false;

    /** Almacena el Nombre del Programa que Ejecuta el Reporte */
    private String programName = "";

    /**
     * Constructor : Inicializa el Tama�o de P�gina, el Puerto de Salida para la
     * Impresi�n e indica si se va a imprimir el N�mero de P�gina y la Fecha de
     * Generaci�n del Reporte.
     * 
     * Ejemplo : FarmaPrintService printService = new
     * FarmaPrintService(65,"LPT1",true); Imprimir� en P�ginas de 66 L�neas a
     * trav�s de LPT1 e imprimir� N�mero de P�gina y Fecha de Generaci�n. Para
     * acceder a una impresora matricial ubicada dentro de la red de Windows se
     * deber� usar la siguiente sintaxis por ejemplo : //cramirez/okidatam
     * 
     * PROCEDIMIENTO : 1.Seg�n sea el caso Setear la Cabecera del Reporte. Caso
     * 1: a.Setear Propiedades de Impresi�n (negrita, condensado, etc).
     * b.Imprimir el Texto. c.Desactivar el Seteo de Propiedades realizado.
     * Ventaja: Setear una sola vez para imprimir varios Textos Caso 2:
     * a.Imprimir el Texto indicando el Seteo de manera directa. Ventaja:
     * Imprimir de manera directa el Texto con la propiedad deseada. 2.Seg�n sea
     * el caso Setear el Pie de P�gina del Reporte. Los casos son los mismos que
     * el Procedimiento 1. 3.Imprimir el Texto del Cuerpo del Reporte. Los casos
     * son los mismos que el Procedimiento 1.
     * 
     * OBSERVACION : El Seteo de la Cabecera y Pie de P�gina solo es realizado
     * una (1) vez. El Servicio de Impresi�n detecta cuando y cuantas veces
     * imprimir la Cabecera y/o Pie de P�gina, de igual modo detecta cuando
     * hacer los correspondientes quiebre de p�gina.
     * 
     * @param pLinesPerPage
     *             Tama�o del Papel expresado en N�mero de L�neas.
     * @param pDevicePort
     *             Puerto de Salida.
     * @param pIncludeDatePage
     *             Mostrar Fecha y Numeraci�n de P�gina.
     */
    public FarmaPrintService(int pLinesPerPage, String pDevicePort, 
                             boolean pIncludeDatePage) {
        linesPerPage = pLinesPerPage;
        printArea = linesPerPage - 4;
        devicePort = pDevicePort;
        includeDatePage = pIncludeDatePage;
        
        char pageLines[] = { (char)27, (char)67, (char)pLinesPerPage };
        
        pageSize = pageLines;
        
    }

    /**
     * Setea el Inicio del Trabajo del Servicio de Impresi�n.
     */
    public boolean startPrintService() {
        boolean valorRetorno = false;
        try {
            FileOutputStream fos = new FileOutputStream(devicePort);
            ps = new PrintStream(fos);
            ps.print(deactivateCondensed);
            ps.print(pageSize);
            ps.print(carriageReturn);
            ps.println(" ");
            ps.print(carriageReturn);
            if (includeDatePage) {
                printArea -= 3;
                reportDate = 
                        FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
                printPageNumber();
            }
            valorRetorno = true;
        /*} catch (FileNotFoundException errFileNotFound) {
            errFileNotFound.printStackTrace();
        } catch (IOException errIO) {
            errIO.printStackTrace();
        } catch (SQLException errGetDateTime) {
            errGetDateTime.printStackTrace();*/
        } catch (Exception errException) {
            errException.printStackTrace();
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
              enviaErrorCorreoPorDB(errException,null);
        }
        return valorRetorno;
    }
    /** 
     * Prepara un Stream de data para imprimirlo posteriormente
     * @author ASOSA
     * @since 25.01.2010
     * @param corr
     * @param asunto
     * @param msg
     * @return
     */
    public boolean startPrintService_02(String corr, String asunto, String msg) {
        boolean valorRetorno = false;
        try {
            FileOutputStream fos = new FileOutputStream(devicePort);
            ps = new PrintStream(fos);
            ps.print(deactivateCondensed);
            ps.print(pageSize);
            ps.print(carriageReturn);
            ps.println(" ");
            ps.print(carriageReturn);
            if (includeDatePage) {
                printArea -= 3;
                reportDate = 
                        FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
                printPageNumber();
            }
            valorRetorno = true;
        } catch (Exception errException) {
            errException.printStackTrace();
            System.out.println("Hola");
            enviaErrorCorreoPorDB_02(errException,corr,asunto,msg);
            valorRetorno=false;
        }
        return valorRetorno;
    }

    /**
     * Setea el Fin del Trabajo del Servicio Impresi�n.
     */
    public void endPrintService() {
        for (int i = 1; i < getRemainingLines() + 2; i++) {
            ps.println(" ");
            ps.print(carriageReturn);
        }
        printArray(arrayFooter);
        if (includeDatePage)
            printDatePage();
        ps.print("\f");
        ps.flush();
        ps.close();
    }

    /**
     * Setea el Fin del Trabajo del Servicio Impresi�n.
     */
    public void endPrintServiceSinCompletar() {
        printArray(arrayFooter);
        if (includeDatePage)
            printDatePage();
        ps.print("\f");
        ps.flush();
        ps.close();
    }

    /**
     * Setea el Fin del Trabajo del Servicio Impresi�n.
     * Se usa para la impresion de la comanda de Delivery
     * Esto hace que no se piera hoja en la impresion.
     * Creado el 25/06/2007 PAMEGHINO.
     */
    public void endPrintServiceSinCompletarDelivery() {
        //printArray(arrayFooter);
        if (includeDatePage)
            printDatePage();
        //ps.print("\f");
        //ps.flush();
        ps.close();
    }

    /**
     * Setea el Inicio de la Cabecera de P�gina.
     */
    public void setStartHeader() {
        settingHeader = true;
    }

    /**
     * Setea el Fin de la Cabecera de P�gina.
     */
    public void setEndHeader() {
        settingHeader = false;
        headerSize += actualLine;
        actualLine = 0;
        printArray(arrayHeader);
    }

    /**
     * Setea el Inicio del Pie de P�gina.
     */
    public void setStartFooter() {
        settingFooter = true;
    }

    /**
     * Setea el Fin del Pie de P�gina.
     */
    public void setEndFooter() {
        settingFooter = false;
        footerSize += actualLine;
        actualLine = 0;
    }

    /**
     * Activa el modo de impresi�n en Negrita.
     */
    public void activateBold() {
        setProperties(activateBold);
    }

    /**
     * Desactiva el modo de impresi�n en Negrita.
     */
    public void deactivateBold() {
        setProperties(deactivateBold);
    }

    /**
     * Imprime el texto en modo Negrita.
     * 
     * @param pText
     *             Texto a imprimir.
     * @param pChangeLine
     *             Indicador de cambio de l�nea.
     */
    public void printBold(String pText, boolean pChangeLine) {
        activateBold();
        printLine(pText, pChangeLine);
        deactivateBold();
    }

    /**
     * Activa el modo de impresi�n en Condensado.
     */
    public void activateCondensed() {
        setProperties(activateCondensed);
    }

    /**
     * Desactiva el modo de impresi�n en Condensado.
     */
    public void deactivateCondensed() {
        setProperties(deactivateCondensed);
    }

    /**
     * Imprime el texto en modo Condensado.
     * 
     * @param pText
     *             Texto a imprimir.
     * @param pChangeLine
     *             Indicador de cambio de l�nea.
     */
    public void printCondensed(String pText, boolean pChangeLine) {
        activateCondensed();
        printLine(pText, pChangeLine);
        deactivateCondensed();
    }

    /**
     * Activa el modo de impresi�n Tama�o Doble de Letra.
     */
    public void activateDoubleWidthMode() {
        setProperties(activateDoubleWidthMode);
    }

    /**
     * Desactiva el modo de impresi�n Tama�o Doble de Letra.
     */
    public void deactivateDoubleWidthMode() {
        setProperties(deactivateDoubleWidthMode);
    }

    /**
     * Imprime el texto en modo Tama�o Doble de Letra.
     * 
     * @param pText
     *             Texto a imprimir.
     * @param pChangeLine
     *             Indicador de cambio de l�nea.
     */
    public void printDoubleWidthMode(String pText, boolean pChangeLine) {
        activateDoubleWidthMode();
        printLine(pText, pChangeLine);
        deactivateDoubleWidthMode();
    }

    /**
     * M�todo que envia el texto a impresora matricial.
     * 
     * @param pText
     *             Texto a imprimir.
     * @param pChangeLine
     *             Indicador de cambio de l�nea.
     */
    public void printLine(String pText, boolean pChangeLine) {
        pText = pText.replaceAll("�", "A");
        pText = pText.replaceAll("�", "E");
        pText = pText.replaceAll("�", "I");
        pText = pText.replaceAll("�", "O");
        pText = pText.replaceAll("�", "U");
        pText = pText.replaceAll("�", "a");
        pText = pText.replaceAll("�", "e");
        pText = pText.replaceAll("�", "i");
        pText = pText.replaceAll("�", "o");
        pText = pText.replaceAll("�", "u");
        pText = pText.replaceAll("�", "n");
        pText = pText.replaceAll("�", "N");
        pText = pText.replaceAll("�", "");
        setPrintLine(pText, pChangeLine);
        if (pChangeLine)
            actualLine += 1;
        if (totalLine() > printArea)
            internalPageBreak(true);
    }

    /**
     * M�todo que envia el texto a impresora matricial.
     * 
     * @param pText
     *             Texto a imprimir.
     * @param pChangeLine
     *             Indicador de cambio de l�nea.
     */
    private void setPrintLine(String pText, boolean pChangeLine) {
        if (settingHeader && pChangeLine)
            arrayHeader.add("1" + pText);
        else if (settingHeader && !pChangeLine)
            arrayHeader.add("0" + pText);
        else if (settingFooter && pChangeLine)
            arrayFooter.add("1" + pText);
        else if (settingFooter && !pChangeLine)
            arrayFooter.add("0" + pText);
        else if (pChangeLine) {
            ps.println(pText);
            ps.print(carriageReturn);
        } else
            ps.print(pText);
    }

    /**
     * Retorna el N�mero de L�neas disponibles para impresi�n.
     * 
     * @return int Retorna printArea-(headerSize+footerSize+actualLine)
     */
    public int getRemainingLines() {
        return printArea - totalLine();
    }

    /**
     * M�todo que envia una l�nea en blanco a impresora matricial.
     * 
     * @param pLineNumber
     *             N�mero de L�neas en blanco a Imprimir.
     */
    public void printBlankLine(int pLineNumber) {
        for (int i = 0; i < pLineNumber; i++)
            printLine(" ", true);
    }

    /**
     * Realiza un Quiebre de P�gina.
     */
    public void pageBreak() {
        internalPageBreak(false);
    }

    /**
     * Asigna el Nombre del Programa que ejecuta la impresi�n.
     */
    public void setProgramName(String pProgramName) {
        programName = pProgramName;
    }

    // public void print

    /***************************************************************************
	 * U T I L I T Y M E T H O D S *
	 **************************************************************************/

    /**
     * Envia c�digo de seteo a la impresora matricial.
     * 
     * @param pProperties
     *             Colecci�n de char's para seteo de propiedades.
     */
    private void setProperties(char[] pProperties) {
        if (settingHeader)
            arrayHeader.add(pProperties);
        else if (settingFooter)
            arrayFooter.add(pProperties);
        else
            ps.print(pProperties);
    }

    /**
     * Imprime el contenido del Objeto ArrayList.
     * 
     * @param pPrintArray
     *             Objeto ArrayList que almacena el Header o el Footer.
     */
    private void printArray(ArrayList pPrintArray) {
        String textToPrint = "";
        for (int i = 0; i < pPrintArray.size(); i++) {
            if (pPrintArray.get(i) instanceof String) {
                textToPrint = ((String)pPrintArray.get(i)).trim();
                if (textToPrint.substring(0, 1).equalsIgnoreCase("1")) {
                    ps.println(textToPrint.substring(1, textToPrint.length()));
                    ps.print(carriageReturn);
                } else
                    ps.print(textToPrint.substring(1, textToPrint.length()));
            } else
                ps.print((char[])pPrintArray.get(i));
        }
    }

    /**
     * Imprime el N�mero de P�gina.
     */
    private void printPageNumber() {
        if (!includeDatePage)
            return;
        ps.println(FarmaPRNUtility.fillBlanks(74) + "Pag. " + pageNumber);
        ps.print(carriageReturn);
        pageNumber += 1;
    }

    /**
     * Imprime la Fecha y Hora de Generaci�n del Reporte.
     */
    private void printDatePage() {
        if (!includeDatePage)
            return;
        ps.println("");
        ps.print(carriageReturn);
        ps.println((programName.length() > 0 ? programName + " - " : "") + 
                   "Emision : " + reportDate);
        ps.print(carriageReturn);
    }

    /**
     * Realiza un Quiebre de P�gina - M�todo de uso Interno.
     * 
     * @param pIsNormalPageBreak
     *             Indica si el quibre de p�gina es natural.
     */
    private void internalPageBreak(boolean pIsNormalPageBreak) {
        if (!pIsNormalPageBreak) {
            for (int i = 1; i < getRemainingLines() + 2; i++)
                ps.println(" ");
            ps.print(carriageReturn);
        }
        actualLine = 0;
        printArray(arrayFooter);
        printDatePage();
        setProperties(pageBreak);
        if (pageNumber == 2)
            printArea += 1;
        // printBlankLine(2);
        
        printBlankLine(1);
        
        printPageNumber();
        printArray(arrayHeader);
    }

    /**
     * Retorna el total de l�neas consideradas IMPRESAS.
     * 
     * @return int Retorna headerSize + footerSize + actualLine
     */
    private int totalLine() {
        return headerSize + footerSize + actualLine;
    }

    /***************************************************************************
     * P R I N T S E R V I C E D E M O *
     **************************************************************************/
    public static

    void main(String[] args) {
        FarmaPrintService mainPRN = new FarmaPrintService(36, "LPT1", false);
        // Asignando el Nombre del Programa que genera la impresi�n.
        // mainPRN.setProgramName("Prueba.class");
        // Iniciando Servicio de Impresi�n
        mainPRN.startPrintService();
        /*
		 * // Seteando el inicio de la Cabecera del Reporte.
		 * mainPRN.setStartHeader(); // Caso 1: mainPRN.activateCondensed();
		 * mainPRN.printLine("PRUEBA DE HEADER CONDENSADO",true);
		 * mainPRN.deactivateCondensed(); // Caso 2:
		 * mainPRN.printCondensed("HEADER CONDENSADO",true);
		 * mainPRN.printDoubleWidthMode("TEXTO DOBLE",true); // Seteando el
		 * final de la Cabecera del Reporte. mainPRN.setEndHeader();
		 * mainPRN.setStartFooter(); mainPRN.printLine("PIE DE PAGINA
		 * NORMAL",true); mainPRN.setEndFooter();
		 */
        // Imprimiendo el Cuerpo del Reporte.
        mainPRN.activateCondensed();
        for (int i = 1; i < 90; i++) {
            /*
			 * if ( mainPRN.getRemainingLines()<10 ) {
			 * mainPRN.printLine("*QUEDABAN MENOS DE 10 LINEAS",true);
			 * mainPRN.pageBreak(); }
			 */
            mainPRN.printLine("            " + String.valueOf(i), true);
            // mainPRN.printLine("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",true);
        }
        mainPRN.deactivateCondensed();
        // Finalizando Servicio de Impresi�n.
        mainPRN.endPrintService();
    }

    /**
     * Setea el Inicio del Trabajo del Servicio de Impresi�n.
     */
    public boolean startPrintServiceArchivoTexto() {
        boolean valorRetorno = false;
        try {
            FileOutputStream fos = new FileOutputStream(devicePort);

            ps = new PrintStream(fos);
            ps.print(deactivateCondensed);

            if (includeDatePage) {
                printArea -= 3;
                reportDate = 
                        FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
                printPageNumber();
            }
            valorRetorno = true;
        } catch (FileNotFoundException errFileNotFound) {
            errFileNotFound.printStackTrace();            
        } catch (IOException errIO) {
            errIO.printStackTrace();
        } catch (SQLException errGetDateTime) {
            errGetDateTime.printStackTrace();
        } catch (Exception errException) {
            errException.printStackTrace();
        }
        return valorRetorno;
    }

    /**Finaliza el servicio de impresi�n para un archivo de texto
     * 
     */
    public void endPrintServiceArchivoTexto() {
        if (includeDatePage)
            printDatePage();
        ps.print("\f");
        ps.flush();
        ps.close();
    }

    /**Imprime una l�nea sin espacio
     * @param pText
     * 			Texto a imprimir
     * @param pChangeLine
     * 			Indica si se debe cambiar de l�nea
     */
    public void printLineSinEspacio(String pText, boolean pChangeLine) {
        setPrintLineSinEspacio(pText, pChangeLine);
        if (pChangeLine)
            actualLine += 1;
        if (totalLine() > printArea)
            internalPageBreak(true);
    }

    /**Imprime un conjunto de l�neas sin espacios interlineales
     * @param pText
     * 				Texto a imprimir
     * @param pChangeLine
     * 				Indicador de cambio de l�nea
     */
    private void setPrintLineSinEspacio(String pText, boolean pChangeLine) {
        if (settingHeader && pChangeLine)
            arrayHeader.add("1" + pText);
        else if (settingHeader && !pChangeLine)
            arrayHeader.add("0" + pText);
        else if (settingFooter && pChangeLine)
            arrayFooter.add("1" + pText);
        else if (settingFooter && !pChangeLine)
            arrayFooter.add("0" + pText);
        else if (pChangeLine) {
            ps.print(pText);
            ps.print(carriageReturn);
        } else
            ps.print(pText);
    }

    /**Obtiene la linea actual
     * @return
     * 			Linea actual
     */
    public int getActualLine() {
        return actualLine;
    }

    /**Realiza un salto de p�gina en el archivo actual
     * 
     */
    public void pageBreakArchivo() {
        internalPageBreak(true);
    }

    /**Devuelve el numero de p�gina del archivo
     * @return
     * 			N�mero p�gina del archivo
     */
    public String printPageNumberArchivo() {
        return "Folio: " + (pageNumber++);
    }

    /**Obtiene el n�mero de la p�gina actual
     * @return
     * 		N�mero de p�gina
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * M�todo que imprime un texto cortado segun tama�o de linea.
     * 
     * @param pText
     *             Texto a imprimir.
     * @param pCharPerLine
     *             N�mero de Caracteres por linea a Imprimir.
     * @param pLeftWhiteSpace
     *             N�mero espacios a imprimir a la izquierda por linea.
     */
    public void printCutLine(String pText, int pCharPerLine, 
                             int pLeftWhiteSpace) {
        int lengthText = pText.length();
        int linesText = lengthText / pCharPerLine;
        for (int i = 0; i <= linesText; i++) {
            if (i == linesText)
                printLine(FarmaPRNUtility.llenarBlancos(pLeftWhiteSpace) + 
                          pText.substring(i * pCharPerLine, lengthText), true);
            else
                printLine(FarmaPRNUtility.llenarBlancos(pLeftWhiteSpace) + 
                          pText.substring(i * pCharPerLine, 
                                          ((i + 1) * pCharPerLine)), true);
        }
    }
    
    /**
     * M�todo que env�a e-mail cuando ocurre Error al Imprimir con StartPrintService.
     * 
     * @param pMessage
     *             Error que se produce.
     * @param pCorrelativo
     *             Numero de Correlativo.
     */
    //JMIRANDA 24/07/09
    public static void enviaErrorCorreoPorDB(Exception pMessage, String pCorrelativo)  {
        //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime 
        if (FarmaVariables.vEmail_Destinatario_Error_Impresion.trim().equalsIgnoreCase("")){
            FarmaVariables.vEmail_Destinatario_Error_Impresion = FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION;
            System.out.println("NO ENTRA IMPR: "+FarmaVariables.vEmail_Destinatario_Error_Impresion);
        }
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                      FarmaVariables.vCodLocal,
                                      //FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                      FarmaVariables.vEmail_Destinatario_Error_Impresion,
                                      "Error al Imprimir Pedido Completo ",
                                      "Error de Impresi�n StartPrintService",
                                      "Se produjo un error al imprimir un pedido :<br>"+
                                      //"Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                      "Correlativo : " +pCorrelativo+"<br>"+
                                      "IP : " +FarmaVariables.vIpPc+"<br>"+
                                      "Error: " + pMessage ,
                                      //FarmaConstants.EMAIL_DESTINATARIO_CC_ERROR_IMPRESION
                                      "");  
            System.out.println("Error Impresion: "+FarmaVariables.vEmail_Destinatario_Error_Impresion);
        
    }
    
    /**
     * Envia un correo indicando que la impresion fue truncada
     * @author ASOSA
     * @since 25.01.2010
     * @param objExc
     * @param corr
     * @param asunto
     * @param msg
     */
    public static void enviaErrorCorreoPorDB_02(Exception objExc, String corr, String asunto, String msg){
        if(FarmaVariables.vEmail_Destinatario_Error_Impresion.trim().equalsIgnoreCase("")){
            FarmaVariables.vEmail_Destinatario_Error_Impresion=FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION;
        }
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                      FarmaVariables.vCodLocal,
                                      FarmaVariables.vEmail_Destinatario_Error_Impresion,
                                      asunto,
                                      "Error de Impresion StartPrintService",
                                      (((String)corr).equalsIgnoreCase("null"))?msg+" : <br>IP : "+FarmaVariables.vIpPc+"<br>Error : "+objExc:msg+":<br>Correlativo : "+corr+"<br>IP : "+FarmaVariables.vIpPc+"<br>Error : "+objExc,
                                      "");
    }

}
