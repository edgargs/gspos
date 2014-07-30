package mifarma.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaUtility.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 *
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */


public class FarmaUtility {

    // static ArrayList rowsWithOtherColor = new ArrayList();
    static int rowWithColor = 0;

    /**
     * Constructor
     */
    public FarmaUtility() {
    }

    /**
     * Establece formato numérico a un valor tipo double.
     *
     * @param pValue
     *            Valor tipo double de el número que será formateado.
     * @return String Retorna un valor String con el número en el formato
     *         ###,###.## .
     */
    public static String formatNumber(double pValue) {
        java.text.DecimalFormat formatDecimal = 
            new java.text.DecimalFormat(",##0.00");
        java.text.DecimalFormatSymbols symbols = 
            new java.text.DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        formatDecimal.setDecimalFormatSymbols(symbols);
        return formatDecimal.format(pValue).toString();
    }

    /**
     * Establece formato numérico a un valor tipo double
     * especificando la cantidad de decimales.
     *
     * @param pValue
     *            Valor tipo double de el número que será formateado.
     * @param pDecimales
     *            Valor tipo int que especifica la cantidad de decimales.
     * @return String Retorna un valor String con el número en el formato
     *         ###,###.## .
     */
    public static String formatNumber(double pValue, int pDecimales) {
        java.text.DecimalFormat formatDecimal = 
            new java.text.DecimalFormat(",##0." + 
                                        caracterIzquierda("", pDecimales, 
                                                          "0"));
        java.text.DecimalFormatSymbols symbols = 
            new java.text.DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        formatDecimal.setDecimalFormatSymbols(symbols);
        return formatDecimal.format(pValue).toString();
    }

    /**
     * Establece formato numérico a un valor tipo double
     * especificando el formato numérico.
     *
     * @param pValue
     *            Valor tipo double de el número que será formateado.
     * @param pFormatNumber
     *            Valor tipo String que especifica la el formato para el número.
     * @return String Retorna un valor String con el número en el formato
     *         especificado .
     */
    public static String formatNumber(double pValue, String pFormatNumber) {
        java.text.DecimalFormat formatDecimal = 
            new java.text.DecimalFormat(pFormatNumber);
        java.text.DecimalFormatSymbols symbols = 
            new java.text.DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        formatDecimal.setDecimalFormatSymbols(symbols);
        return formatDecimal.format(pValue).toString();
    }

    /**
     * Retorna un valor numerico redondeado a 2
     * decimales.
     *
     * @param pDecimal
     *            Valor tipo double de el número que será formateado.
     * @return double Retorna un valor double con el número redondeado y
     *         formateado .
     */
    public static double getDecimalNumberRedondeado(double pDecimal) {
        return FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(pDecimal));
    }


    /**Obtiene el valor decimal de una representación en cadena
     * @param pDecimal
     * 		Representación del decimal en tipo String
     * @return
     * 		Valor decimal generado
     */
    public static double getDecimalNumber(String pDecimal) {
        double decimalNumber = 0.00;
        try {
            java.text.DecimalFormat formatDecimal = 
                new java.text.DecimalFormat("###,##0.00");
            java.text.DecimalFormatSymbols symbols = 
                new java.text.DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
            formatDecimal.setDecimalFormatSymbols(symbols);
            decimalNumber = formatDecimal.parse(pDecimal).doubleValue();
        } catch (ParseException errParse) {
        }
        return decimalNumber;
    }

    /**
     * Permite inicializar un JTable.
     * @param table
     *            Objeto tipo JTable que sera inicializado.
     * @param tableModel
     *            Objeto FarmaTableModel que establecerá el Modelo para el
     *            JTable (columnas:título,longitud,alineación).
     * @param columnsPriceList
     *            Objeto ColumnData que almacena información de las columnas..
     */
    public static void initPriceList(JTable table, FarmaTableModel tableModel, 
                                     FarmaColumnData[] columnsPriceList) {
        table.setAutoCreateColumnsFromModel(false);
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableCellRenderer renderer;
            if (k == 0) {
                renderer = new FarmaCheckCellRenderer();
            } else {
                DefaultTableCellRenderer textRenderer = 
                    new DefaultTableCellRenderer();
                textRenderer.setHorizontalAlignment(columnsPriceList[k].m_alignment);
                renderer = textRenderer;
            }
            TableCellEditor editor;
            if (k == 0)
                editor = new DefaultCellEditor(new JCheckBox());
            else
                editor = new DefaultCellEditor(new JTextField());
            TableColumn column = 
                new TableColumn(k, columnsPriceList[k].m_width, renderer, 
                                editor);
            table.addColumn(column);
        }
    }

    /**
     * Establece el estado de los objetos JCheckBox de una lista de selección
     *
     * @param tableModel
     *           Objeto FarmaTableModel que establecerá el modelo para la tabla
     * @param table
     *           Objeto tipo JTable que sera operado.
     * @param arrayList
     *            Arreglo de valores tipo boolean para esteblacer os estados de los objetos JCheckBox
     */
    public static void setCheckBox(FarmaTableModel tableModel, JTable table, 
                                   ArrayList arrayList) {
        Boolean valor = 
            (Boolean)tableModel.getValueAt(table.getSelectedRow(), 0);
        tableModel.setValueAt(new Boolean(!valor.booleanValue()), 
                              table.getSelectedRow(), 0);
        table.repaint();
        operaListaProd(arrayList, tableModel.getRow(table.getSelectedRow()), 
                       new Boolean(!valor.booleanValue()), 1);
    }

    /**
     * Coloca un objeto JDialog en el centro de la pantalla.
     *
     * @param pDialog
     *            Objeto JDialog a centrar.
     */
    public static void centrarVentana(JDialog pDialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension objectSize = pDialog.getSize();
        if (objectSize.height > screenSize.height) {
            objectSize.height = screenSize.height;
        }
        if (objectSize.width > screenSize.width) {
            objectSize.width = screenSize.width;
        }
        pDialog.setLocation((screenSize.width - objectSize.width) / 2, 
                            (screenSize.height - objectSize.height) / 2);
    }

    /**
     * Establece el valor de uan celda específica en un objeto JTextField
     *
     * @param pTabla
     *            Tabla desde la cual se obtendrán los datos
     * @param pTextoDeBusqueda
     *            Caja de texto que recibirá el valor
     * @param pColumna
     *            Indice de la columna que se utilizará
     *
     */
    public static void setearTextoDeBusqueda(JTable pTabla, 
                                             JTextField pTextoDeBusqueda, 
                                             int pColumna) {
        if (pTabla.getSelectedRow() >= 0) {
            FarmaGridUtils.showCell(pTabla, pTabla.getSelectedRow(), 0);
            pTextoDeBusqueda.setText(((String)pTabla.getValueAt(pTabla.getSelectedRow(), 
                                                                pColumna)).trim().toUpperCase());
            pTextoDeBusqueda.selectAll();
        }
    }

    /**
     * Coloca el primer registro de una tabla como texto de búsqueda en un objeto JTextField
     *
     * @param pTabla
     *            Tabla de la que se obtendrán los datos
     * @param pTextoDeBusqueda
     *            Caja de texto que recepcionará el valor
     * @param pColumna
     *            Indice de la columna a leer
     */
    public static void setearPrimerRegistro(JTable pTabla, 
                                            JTextField pTextoDeBusqueda, 
                                            int pColumna) {
        if (pTabla.getRowCount() > 0) {
            FarmaGridUtils.showCell(pTabla, 0, 0);
            if (pTextoDeBusqueda != null)
                setearTextoDeBusqueda(pTabla, pTextoDeBusqueda, pColumna);
        }
    }

    /**
     * Coloca el registro seleccionado de una tabla como texto de búsqueda en un objeto JTextField
     *
     * @param pTabla
     *           Tabla de la que se obtendrán los datos
     * @param pTextoDeBusqueda
     *           Caja de texto que recepcionará el valor
     * @param pColumna
     *             Indice de la columna a leer
     */
    public static void setearActualRegistro(JTable pTabla, 
                                            JTextField pTextoDeBusqueda, 
                                            int pColumna) {
        if (pTabla.getRowCount() > 0) {
            FarmaGridUtils.showCell(pTabla, pTabla.getSelectedRow(), 0);
            if (pTextoDeBusqueda != null)
                setearTextoDeBusqueda(pTabla, pTextoDeBusqueda, pColumna);
        }
    }

    /**
     * Coloca el registro especificado de una tabla como texto de búsqueda en un objeto JTextField
     *
     * @param pTabla
     *           Tabla de la que se obtendrán los datos
     * @param pTextoDeBusqueda
     *           Caja de texto que recepcionará el valor
     * @param pColumna
     *             Indice de la columna a leer
     *
     */
    public static void setearRegistro(JTable pTabla, int pRowSelected, 
                                      JTextField pTextoDeBusqueda, 
                                      int pColumna) {
        if (pTabla.getRowCount() > 0) {
            FarmaGridUtils.showCell(pTabla, pRowSelected, 0);
            if (pTextoDeBusqueda != null)
                setearTextoDeBusqueda(pTabla, pTextoDeBusqueda, pColumna);
        }
    }

    /**
     * Permite restringir la digitación a sólo dígitos.
     *
     * @param e
     *            Objeto tipo KeyEvent que almacena el valor de la tecla.
     * @param textField
     *            Objeto JTextField sobre el cual se digita.
     * @param longitudTexto
     *            Valor de la longitud del Texto antes de la última tecla
     *            presionada.
     * @param dialog
     *            Objeto JDialog que contiene al objeto JTextField.
     */
    public static void admitirSoloDigitos(KeyEvent e, JTextField textField, 
                                          int longitudTexto, JDialog dialog) {
        // Comparamos la longitud del texto antes y despues de presionar una
        // tecla
        // El objetivo es controlar los caracteres no válidos. Para las teclas
        // Bloq.Mayús,
        // Insert, Supr, Inicio, Fin, etc ... son teclas que no generan un
        // caracter pero sí
        // un objeto KeyEvent ...
        if (longitudTexto < textField.getText().length()) {
            char keyChar = e.getKeyChar();
            if (!Character.isDigit(keyChar)) {
                e.consume();
                admitirMensaje(keyChar, textField, 
                               "La tecla presionada no es válida !!!.  Solo se aceptan Números.", 
                               dialog);
            }
        }
    }

    /**
     * Define validación numérica en un objeto JTextField.
     *
     * @param e
     *            Objeto Frame de la Aplicación.
     * @param textField
     *            Objeto JTextFielden el que se realizará la validación.
     * @param longitudTexto
     *            Longitud permitida para el texto a validar.
     * @param dialog
     *            Ventana tipo JDialog en la cual se realiza la validción.
     * @return boolean Tipo de Dato devuelto
     */
    public static boolean esDigito(KeyEvent e, JTextField textField, 
                                   int longitudTexto, JDialog dialog) {
        boolean digitoValido = true;
        if (longitudTexto < textField.getText().length()) {
            char keyChar = e.getKeyChar();
            if (!Character.isDigit(keyChar)) {
                digitoValido = false;
                e.consume();
                admitirMensaje(keyChar, textField, 
                               "La tecla presionada no es válida !!!.  Solo se aceptan Números.", 
                               dialog);
            }
        }
        return digitoValido;
    }

    /**
     * Permite restringir la digitación a sólo dígitos (decimales).
     *
     * @param e
     *            Objeto tipo KeyEvent que almacena el valor de la tecla.
     * @param textField
     *            Objeto JTextField sobre el cual se digita.
     * @param longitudTexto
     *            Valor de la longitud del Texto antes de la última tecla
     *            presionada.
     * @param dialog
     *            Objeto JDialog que contiene al objeto JTextField.
     */
    public static void admitirSoloDecimales(KeyEvent e, JTextField textField, 
                                            int longitudTexto, 
                                            JDialog dialog) {
        // Comparamos la longitud del texto antes y despues de presionar una
        // tecla
        // El objetivo es controlar los caracteres no válidos. Para las teclas
        // Bloq.Mayús,
        // Insert, Supr, Inicio, Fin, etc ... son teclas que no generan un
        // caracter pero sí
        // un objeto KeyEvent ...
        if (longitudTexto < textField.getText().length()) {
            char keyChar = e.getKeyChar();
            // if (!Character.isDigit(keyChar) &&
            // (e.getKeyCode()!=KeyEvent.VK_DECIMAL)) {
            if (!Character.isDigit(keyChar) && 
                (e.getKeyCode() != KeyEvent.VK_DECIMAL) && 
                (e.getKeyCode() != 46)) {
                e.consume();
                admitirMensaje(keyChar, textField, 
                               "La tecla presionada no es válida !!!.  Solo se aceptan Números.", 
                               dialog);
            } else {
                int posPunto = textField.getText().indexOf(".") + 1;
                int lenTexto = textField.getText().length();
                if ((posPunto > 0) && ((lenTexto - posPunto) > 2)) {
                    admitirMensaje(keyChar, textField, 
                                   "Solo se aceptan dos (2) decimales !!!", 
                                   dialog);
                }
            }
        }
    }

    /**
     * Permite restringir la digitación del último dígito de la parte decimal.
     *
     * @param pMonto
     *            Objeto tipo JTextField que almacena el monto digitado.
     * @return boolean Si es valido el dígito devuelve true caso contrario
     *         devuelve false.
     */
    public static boolean validaCentimos(JTextField pMonto) {
        boolean valorRetorno = true;
        if (pMonto.getText().indexOf(".") >= 0) {
            if ((pMonto.getText().indexOf(".") + 3) > 
                pMonto.getText().length())
                pMonto.setText(pMonto.getText() + "0");
            if (Integer.parseInt(pMonto.getText().trim().substring(pMonto.getText().trim().length() - 
                                                                   1, 
                                                                   pMonto.getText().trim().length())) != 
                0)
                valorRetorno = false;
        }
        return valorRetorno;
    }

    /**
     * Permite restringir la digitación a sólo letras.
     *
     * @param e
     *            Objeto tipo KeyEvent que almacena el valor de la tecla.
     * @param textField
     *            Objeto JTextField sobre el cual se digita.
     * @param longitudTexto
     *            Valor de la longitud del Texto antes de la última tecla
     *            presionada.
     * @param dialog
     *            Objeto JDialog que contiene al objeto JTextField.
     */
    public static void admitirSoloLetras(KeyEvent e, JTextField textField, 
                                         int longitudTexto, JDialog dialog) {
        // Comparamos la longitud del texto antes y despues de presionar una
        // tecla
        // El objetivo es controlar los caracteres no válidos. Para las teclas
        // Bloq.Mayús,
        // Insert, Supr, Inicio, Fin, etc ... son teclas que no generan un
        // caracter pero sí
        // un objeto KeyEvent ...
        if (!(e.getKeyCode() == KeyEvent.VK_SHIFT) && 
            !(e.getKeyCode() == KeyEvent.VK_ALT)) {
            if (longitudTexto < textField.getText().length()) {
                char keyChar = e.getKeyChar();
                if (!Character.isLetter(keyChar) && 
                    !Character.isWhitespace(keyChar)) {
                    e.consume();
                    admitirMensaje(keyChar, textField, 
                                   "La tecla presionada no es válida !!!.  Solo se aceptan letras y espacios en blanco.", 
                                   dialog);
                }
            }
        }
    }

    /**Convierte un texto a mayúsculas
     * @param e
     * 			Evento realizado
     * @param textField
     * 			Objeto JTextField desde el cual se invoca la acción
     */
    public static void ponerMayuscula(KeyEvent e, JTextField textField) {
        char keyChar = e.getKeyChar();
        if (Character.isLetter(keyChar))
            textField.setText(textField.getText().trim().toUpperCase());
    }

    /**
     * Permite mostrar un mensaje de error en caso el tipo de dato digitado no
     * sea el que corresponda.
     *
     * @param keyChar
     *            Objeto tipo char que almacena el valor digitado.
     * @param textField
     *            Objeto JTextField sobre el cual se digita.
     * @param mensaje
     *            String que almacena el mensaje a mostrar.
     * @param dialog
     *            Objeto JDialog que contiene al objeto JTextField.
     */
    private static void admitirMensaje(char keyChar, JTextField textField, 
                                       String mensaje, JDialog dialog) {
        if (!Character.isISOControl(keyChar)) {
            java.awt.Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(dialog, mensaje, 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
            // en caso de haber digitado un caracter no válido, este será
            // borrado luego de aceptar
            // el mensaje del sistema
            textField.setText(textField.getText().substring(0, 
                                                            textField.getText().length() - 
                                                            1));
            textField.requestFocus();
        }
    }

    /**
     * Permite determinar la validez de un número.
     *
     * @param pJDialog
     *            Objeto JDialog en donde se realiza la llamada.
     * @param pJTextField
     *            Objeto JTextField desde el cual se obtendrá el valor numérico
     *            a evaluar.
     * @param pMessage
     *            Mensaje a mostrar en caso de que el número no sea válido.
     * @param pIsMandatory
     *            Indica si es obligatorio que el número sea válido.
     * @return boolean Boolean que indica la validez del número.
     */
    public static boolean validateNumber(JDialog pJDialog, 
                                         JTextField pJTextField, 
                                         String pMessage, 
                                         boolean pIsMandatory) {
        boolean returnValue = false;
        String tempNumber = pJTextField.getText().trim();
        if (!isLong(tempNumber))
            showMessage(pJDialog, pMessage, pJTextField);
        else {
            if (pIsMandatory) {
                if (Long.parseLong(tempNumber) <= 0)
                    showMessage(pJDialog, pMessage, pJTextField);
                else
                    returnValue = true;
            } else
                returnValue = true;
        }
        return returnValue;
    }

    /**
     * Permite determinar la validez de un número que incluye
     * decimales.
     *
     * @param pJDialog
     *            Objeto JDialog en donde se realiza la llamada.
     * @param pJTextField
     *            Objeto JTextField desde el cual se obtendrá el valor numérico
     *            a evaluar.
     * @param pMessage
     *            Mensaje a mostrar en caso de que el número no sea válido.
     * @param pIsMandatory
     *            Indica si es obligatorio que el número sea válido.
     * @return boolean Boolean que indica la validez del número.
     */
    public static boolean validateDecimal(JDialog pJDialog, 
                                          JTextField pJTextField, 
                                          String pMessage, 
                                          boolean pIsMandatory) {
        boolean returnValue = false;
        String tempNumber = pJTextField.getText();
        if (!isDouble(tempNumber))
            showMessage(pJDialog, pMessage, pJTextField);
        else {
            if (pIsMandatory) {
                tempNumber = 
                        String.valueOf(getDecimalNumber(pJTextField.getText().trim()));
                if (Double.parseDouble(tempNumber) <= 0.00)
                    showMessage(pJDialog, pMessage, pJTextField);
                else
                    returnValue = true;
            } else
                returnValue = true;
        }
        return returnValue;
    }

    /**
     * Verifica si una cadena es apta para la conversión al tipo de dato Long.
     * @param pValue
     * 				Valor a evaluar
     * @return boolean
     * 				Inicador de la validez de la cadena evaluada
     */
    public static boolean isLong(String pValue) {
        try {
            if (!pValue.equals("")) {
                long valor = Long.parseLong(pValue);
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Determina si un valor es del tipo Double.
     * @param pValue
     * 			 Valor a Evaluar
     * @return boolean
     * 			Indicador del resultado de la validación
     */
    public static boolean isDouble(String pValue) {
        try {
            if (!pValue.equals("")) {
                double valor = Double.parseDouble(pValue);
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Muestra un diálogo de mensaje.
     * @param pJDialog
     * 				Objeto JDialog desde el cual se invocó la acción
     * @param pMessage
     * 				Mensaje a mostrar
     * @param pObject
     * 				Objeto
     */
    public static void showMessage(JDialog pJDialog, String pMessage, 
                                   Object pObject) {
        System.err.println(pMessage);
        JOptionPane.showMessageDialog(pJDialog, pMessage, 
                                      "Mensaje del Sistema", 
                                      JOptionPane.WARNING_MESSAGE);
        if (pObject != null)
            moveFocus(pObject);
    }

    /**
     * Permite inicializar un JTable.
     * @param pTable
     *            Objeto tipo JTable que sera inicializado.
     * @param pTableModel
     *            Objeto FarmaTableModel que establecerá el Modelo para el
     *            JTable (columnas:título,longitud,alineación).
     * @param pColumnsList
     *            Objeto ColumnData que almacena información de las columnas..
     */
    public static void initSimpleList(JTable pTable, 
                                      FarmaTableModel pTableModel, 
                                      FarmaColumnData[] pColumnsList) {
        initSimpleList(pTable, pTableModel, pColumnsList, new ArrayList(), 
                       null, null, false);
    }

    /**
     * Permite inicializar un JTable.
     * @param table
     *            Objeto tipo JTable que sera inicializado.
     * @param tableModel
     *            Objeto FarmaTableModel que establecerá el Modelo para el
     *            JTable (columnas:título,longitud,alineación).
     * @param columnsList
     *            Objeto ColumnData que almacena información de las columnas..
     * @param pRowsWithOtherColor
     *
     * @param pBackgroundColor
     * 			Color de fondo
     * @param pForegroundColor
     * 			Color frontal
     * @param pBold
     * 			Especifica si se utilizará el formato de Negrita
     */
    public static void initSimpleList(JTable table, FarmaTableModel tableModel, 
                                      FarmaColumnData[] columnsList, 
                                      ArrayList pRowsWithOtherColor, 
                                      Color pBackgroundColor, 
                                      Color pForegroundColor, boolean pBold) {
        /*FarmaColumnData columnsDefault[] = {new
		 FarmaColumnData("",0,JLabel.CENTER)};
		Object[] defaultValues = {""};
		table.setModel(new FarmaTableModel(columnsList,defaultValues,0));*/
         /*int indice = table.getColumnModel().getColumnCount();
         while( indice > 0 ){
             indice--;
             table.removeColumn(table.getColumnModel().getColumn(0));
         }
*/
        table.setAutoCreateColumnsFromModel(false);
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            //DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

            AttributiveCellRenderer renderer = 
                new AttributiveCellRenderer(pRowsWithOtherColor, 
                                            pBackgroundColor, pForegroundColor, 
                                            pBold, false);

            renderer.setHorizontalAlignment(columnsList[k].m_alignment);
            TableCellEditor editor = new FarmaCustomCellEditor();
            TableColumn column = 
                new TableColumn(k, columnsList[k].m_width, renderer, editor);
            table.addColumn(column);
            
        }
    }

    /**
     * @author      dveliz
     * @since       27.01.2009
     * Permite inicializar un JTable limpiando previamente las columnas.
     * @param table
     *            Objeto tipo JTable que sera inicializado.
     * @param tableModel
     *            Objeto FarmaTableModel que establecerá el Modelo para el
     *            JTable (columnas:título,longitud,alineación).
     * @param columnsList
     *            Objeto ColumnData que almacena información de las columnas..
     * @param pRowsWithOtherColor
     *
     * @param pBackgroundColor
     *                  Color de fondo
     * @param pForegroundColor
     *                  Color frontal
     * @param pBold
     *                  Especifica si se utilizará el formato de Negrita
     */
    public static void initSimpleListCleanColumns(JTable table, FarmaTableModel tableModel, 
                                      FarmaColumnData[] columnsList, 
                                      ArrayList pRowsWithOtherColor, 
                                      Color pBackgroundColor, 
                                      Color pForegroundColor, boolean pBold) {

        int indice = table.getColumnModel().getColumnCount();
        while( indice > 0 ){
            indice--;
            table.removeColumn(table.getColumnModel().getColumn(0));
        }

        table.setAutoCreateColumnsFromModel(false);
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            //DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

            AttributiveCellRenderer renderer = 
                new AttributiveCellRenderer(pRowsWithOtherColor, 
                                            pBackgroundColor, pForegroundColor, 
                                            pBold, false);

            renderer.setHorizontalAlignment(columnsList[k].m_alignment);
            TableCellEditor editor = new FarmaCustomCellEditor();
            TableColumn column = 
                new TableColumn(k, columnsList[k].m_width, renderer, editor);
            table.addColumn(column);
            
        }
    }
    
    /**
     * Inicia una lista con la primera columna acondicionada para objetos JCheckBox
     * @param pTable
     * 			Tabla que mostrará el resultado de la operación
     * @param pTableModel
     * 			Objeto tablemodel que contiene los datos a mostrar
     * @param pColumnsList
     * 			Lista de columnas a mostrar
     */
    public static void initSelectList(JTable pTable, 
                                      FarmaTableModel pTableModel, 
                                      FarmaColumnData[] pColumnsList) {
        initSelectList(pTable, pTableModel, pColumnsList, new ArrayList(), 
                       null, null, false);
    }


    /**
     * Permite inicializar un JTable con una columna de Selección (JCheckBox).
     * @param pTable
     * 			Objeto tipo JTable que sera inicializado.
     * @param pTableModel
     * 			Objeto FarmaTableModel que establecerá el Modelo para el
     *            JTable (columnas:título,longitud,alineación).
     * @param pColumnsList
     * 			Objeto ColumnData que almacena información de las columnas.
     * @param pRowsWithOtherColor
     * 			Objeto ArrayList que contiene filas con colores diferentes
     * @param pBackgroundColor
     * 			Color de fondo
     * @param pForegroundColor
     * 			Color frontal
     * @param pBold
     * 			Indica si se usarán negritas
     */
    public static void initSelectList(JTable pTable, 
                                      FarmaTableModel pTableModel, 
                                      FarmaColumnData[] pColumnsList, 
                                      ArrayList pRowsWithOtherColor, 
                                      Color pBackgroundColor, 
                                      Color pForegroundColor, boolean pBold) {
        pTable.setAutoCreateColumnsFromModel(false);
        pTable.setModel(pTableModel);
        pTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        pTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int k = 0; k < pTableModel.getColumnCount(); k++) {
            TableCellRenderer renderer;
            if (k == 0) {
                TableCellRenderer rendererCheckBox = 
                    new FarmaCheckCellRenderer();
                TableCellEditor editorCheckBox = 
                    new DefaultCellEditor(new JCheckBox());
                TableColumn columnCheckBox = 
                    new TableColumn(k, pColumnsList[k].m_width, 
                                    rendererCheckBox, editorCheckBox);
                pTable.addColumn(columnCheckBox);
            } else {
                DefaultTableCellRenderer textRenderer = 
                    new DefaultTableCellRenderer();
                /*
				 * AttributiveCellRenderer rendererText = new
				 * AttributiveCellRenderer(pRowsWithOtherColor,
				 * pBackgroundColor, pForegroundColor, pBold, true);
				 */
                textRenderer.setHorizontalAlignment(pColumnsList[k].m_alignment);
                // TableCellEditor editorText = new FarmaCustomCellEditor();
                TableCellEditor editorText = null;
                TableColumn columnText = 
                    new TableColumn(k, pColumnsList[k].m_width, textRenderer, 
                                    editorText);
                pTable.addColumn(columnText);
            }
        }
    }

    /**
     * Permite seleccionar o deseleccionar una celda del tipo JCheckBox.
     *
     * @param pTable
     *            Objeto tipo JTable que contiene la celda tipo JCheckBox.
     * @param pOnlyCurrentRow
     *            Variable boolean que indica si solamente se permitirá
     *            seleccionar el actual registro, es decir, un registro a la vez
     *            (multiselección o no ...);
     */
    public static void setCheckValue(JTable pTable, boolean pOnlyCurrentRow) {
        // Asignación de valores antes de proceder o no con la inicialización
        // de la celda JCheckBox de cada uno de los registros del JTable.
        int selectedRow = pTable.getSelectedRow();
        Boolean valor = (Boolean)pTable.getValueAt(selectedRow, 0);
        if (!valor.booleanValue()) {
            if (pOnlyCurrentRow) {
                // Si no es multiselección se eliminan todos los check.
                for (int i = 0; i < pTable.getRowCount(); i++)
                    pTable.setValueAt(new Boolean(false), i, 0);
            }
        }
        pTable.setValueAt(new Boolean(!valor.booleanValue()), selectedRow, 0);
        // Es necesario pintar nuevamente este objeto para poder mostrar los
        // cambios efectuados.
        pTable.repaint();
    }

    /**
     * Permite seleccionar o deseleccionar una celda del tipo JCheckBox y a la
     * vez establecer o no el modo multiselección. Adicionalmente permite
     * realizar una búsqueda antes de la selección, sino está activo el flag de
     * búsqueda entonces todo el proceso se realizará sobre el registro
     * actualmente seleccionado del JTable.
     *
     * @param pJTable
     *            Objeto tipo JTable que contiene la celda tipo JCheckBox.
     * @param pValor
     *            Almacena el actual valor del JCheckBox.
     * @param pIsMultiSelect
     *            Variable boolean que indica si solamente se permitirá
     *            seleccionar el actual registro, es decir, un registro a la vez
     *            (multiselección o no ...).
     * @param pFindData
     *            Variable boolean que indica si se realizará una busqueda en el
     *            objeto JTable.
     * @param pValorABuscar
     *            Si pFindData=true entonces debería existir este parámetro para
     *            poder determinar el valor que se deberá buscar.
     * @param numeroColumna
     *            Si pFindData=true entonces deberá existir este parámetro para
     *            poder determinar la columna con la cual se deberá comparar el
     *            pValorABuscar.
     */
    public static void setearCheckInRow(JTable pJTable, Boolean pValor, 
                                        boolean pIsMultiSelect, 
                                        boolean pFindData, 
                                        String pValorABuscar, 
                                        int numeroColumna) {
        // Almacenar el actual registro antes de procesar
        int actualRow = pJTable.getSelectedRow();
        // debe existir el elemento actualmente seleccionado
        if (actualRow >= 0) {
            String myValor = "";
            // Si no es multiselección se eliminan todos los check.
            if (!pIsMultiSelect) {
                for (int i = 0; i < pJTable.getRowCount(); i++)
                    pJTable.setValueAt(new Boolean(false), i, 0);
            }
            // Si se debe buscar data son necesarios los parametros :
            // - pValorABuscar
            // - numeroColumna
            if (pFindData) {
                if ((pValorABuscar != null) && (numeroColumna >= 0)) {
                    for (int i = 0; i < pJTable.getRowCount(); i++) {
                        myValor = (String)pJTable.getValueAt(i, numeroColumna);
                        if (myValor.equalsIgnoreCase(pValorABuscar)) {
                            pJTable.setValueAt(new Boolean(!pValor.booleanValue()), 
                                               i, 0);
                            break;
                        }
                    }
                }
            } else {
                pJTable.setValueAt(new Boolean(!pValor.booleanValue()), 
                                   actualRow, 0);
            }
            // Es necesario pintar nuevamente este objeto para poder mostrar los
            // cambios efectuados.
            pJTable.repaint();
            // Iluminar el registro que estaba seleccionado antes de realizar
            // todas estas operaciones
            FarmaGridUtils.showCell(pJTable, actualRow, 0);
        }
    }

    /**
     * Muestra un diálogo de confirmación
     *
     * @param pJDialog
     *            Objeto JDialog desde el cual se invocó a la función
     * @param pMensaje
     *            Título de la Ventana.
     * @return boolean Respuesta obtenida de el dialog de confirmación.
     */
    public static boolean rptaConfirmDialog(JDialog pJDialog, 
                                            String pMensaje) {
        int rptaDialogo = 
            JOptionPane.showConfirmDialog(pJDialog, pMensaje, "Mensaje del Sistema", 
                                          JOptionPane.YES_NO_OPTION, 
                                          JOptionPane.QUESTION_MESSAGE);
        if (rptaDialogo == JOptionPane.YES_OPTION)
            return true;
        else
            return false;
    }

    /**
     * Elimina un registro en una tabla.
     *
     * @param pJDialog Objeto JDIalog desde el cual se inició la acción.
     * @param pFarmaTableModel TableModel que será afectado
     * @param pJTable Objeto JTable que mostrará el resultado de la operación
     * @return Retorna un indicador de éxito o fracaso de la operación
     */
    public static boolean eliminarRegistroEnJTable(JDialog pJDialog, 
                                                   FarmaTableModel pFarmaTableModel, 
                                                   JTable pJTable) {
        boolean eliminarRegistro = false;
        int row = pJTable.getSelectedRow();
        if (row >= 0) {
            if (rptaConfirmDialog(pJDialog, 
                                  "Seguro de eliminar el Registro ?")) {
                pFarmaTableModel.deleteRow(row);
                pJTable.repaint();
                if (pFarmaTableModel.getRowCount() > 0) {
                    if (row > 0)
                        row--;
                    FarmaGridUtils.showCell(pJTable, row, 0);
                }
                eliminarRegistro = true;
            }
        } else {
            JOptionPane.showMessageDialog(pJDialog, 
                                          "No existe Registro seleccionado !!!", 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
        }
        return eliminarRegistro;
    }

    /**
     * Elimina un registro en una tabla sin pedir confirmación.
     *
     * @param pJDialog
     *            Objeto JDialog desde el cual se invocó a la función
     * @param pFarmaTableModel
     *            Título de la Ventana.
     * @param pJTable
     *            Tipo de Ventana.
     * @return boolean Tipo de Dato devuelto
     */
    public static boolean eliminarRegistroEnJTableSinPreguntar(JDialog pJDialog, 
                                                               FarmaTableModel pFarmaTableModel, 
                                                               JTable pJTable) {
        boolean eliminarRegistro = false;
        int row = pJTable.getSelectedRow();
        if (row >= 0) {
            pFarmaTableModel.deleteRow(row);
            pJTable.repaint();
            if (pFarmaTableModel.getRowCount() > 0) {
                if (row > 0)
                    row--;
                FarmaGridUtils.showCell(pJTable, row, 0);
            }
            eliminarRegistro = true;
        } else {
            JOptionPane.showMessageDialog(pJDialog, 
                                          "No existe Registro seleccionado !!!", 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
        }
        return eliminarRegistro;
    }

    /**
     * Completa una fecha en el formato dd/mm/yyyy.
     * @param pJTextField Caja de texto desde la cual se invocó la acción
     * @param e Evento Realizado
     */
    public static void dateComplete(JTextField pJTextField, KeyEvent e) {
        try {
            String anhoBD = 
                FarmaSearch.getFechaHoraBD(1).trim().substring(6, 10);
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                if ((pJTextField.getText().trim().length() == 2) || 
                    (pJTextField.getText().trim().length() == 5)) {
                    pJTextField.setText(pJTextField.getText().trim() + "/");
                    if (pJTextField.getText().trim().length() == 6)
                        pJTextField.setText(pJTextField.getText().trim() + 
                                            anhoBD);
                }
            }
        } catch (SQLException errAnhoBD) {
            errAnhoBD.printStackTrace();
        }
    }

    /**
     * Completa una fecha en el formato dd/mm/yyyy.
     *
     * @param pJTextField Caja de texto desde la cual se invocó la acción
     * @param e Evento Realizado
     */
    public static void dateCompleteOnlyFormat(JTextField pJTextField, 
                                              KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if ((pJTextField.getText().trim().length() == 2) || 
                (pJTextField.getText().trim().length() == 5))
                pJTextField.setText(pJTextField.getText().trim() + "/");
        }
    }

    /**
     * Establece un valor boolean para un objeto en una lista de selección
     * @param pJTable
     * 				Tabla a operar
     * @param pCampoEnJTable
     * 				Campo en la tabla
     * @param pArrayList
     * 				Lista de campos
     * @param pCampoEnArrayList
     * 				Indice del campo en la lista
     */
    public static void ponerCheckJTable(JTable pJTable, int pCampoEnJTable, 
                                        ArrayList pArrayList, 
                                        int pCampoEnArrayList) {
        String myCodigo = "";
        String myCodigoTmp = "";
        for (int i = 0; i < pJTable.getRowCount(); i++) {
            myCodigo = ((String)pJTable.getValueAt(i, pCampoEnJTable)).trim();
            for (int j = 0; j < pArrayList.size(); j++) {
                myCodigoTmp = 
                        ((String)(((ArrayList)pArrayList.get(j)).get(pCampoEnArrayList))).trim();
                if (myCodigo.equalsIgnoreCase(myCodigoTmp))
                    pJTable.setValueAt(new Boolean(true), i, 0);
            }
        }
        pJTable.repaint();
    }

    /**
     * Redondea un decimal.
     * @param pTotal Decimal a redondear.
     * @return double Decimal redondeado
     */
    public static double getRedondeo(double pTotal) {
        if (pTotal > 0.00) {
            String myTotal = formatNumber(pTotal);
            int myRedondeo = 
                Integer.parseInt(myTotal.substring(myTotal.length() - 1, 
                                                   myTotal.length()));
            // if(myRedondeo >= 5) return
            // Utility.formatNumber((10-myRedondeo)/100);
            // else return Utility.formatNumber(-1*(myRedondeo/100));
            if (myRedondeo >= 5)
                return (0.01 * (10 - myRedondeo));
            else
                return (-1 * 0.01 * myRedondeo);
        } else {
            return 0.00;
        }
    }

    /**
     * Redondea un decimal.
     * @param pTotal Decimal a redondear.
     * @return double Decimal redondeado
     */
    public static double getNuevoRedondeo(double pTotal) {
        if (pTotal > 0.00) {
            String myTotal = formatNumber(pTotal);
            int myRedondeo = 
                Integer.parseInt(myTotal.substring(myTotal.length() - 1, 
                                                   myTotal.length()));
            if (myRedondeo >= 5)
                return (-1 * 0.01 * (myRedondeo - 5));
            else
                return (-1 * 0.01 * myRedondeo);
        } else {
            return 0.00;
        }
    }

    /**
     * Redondea un monto en formato decimal.
     * @param pTotal Monto a redondear
     * @return Monto redondeado
     */
    public static double getMontoDolarRedondeado(double pTotal) {
        if (pTotal > 0.00) {
            String myTotal = formatNumber(pTotal);
            // System.out.println("myTotal="+myTotal);
            pTotal = Double.parseDouble(myTotal);
            int myRedondeo = 
                Integer.parseInt(myTotal.substring(myTotal.length() - 1, 
                                                   myTotal.length()));
            if (myRedondeo < 5)
                return (pTotal + 0.01 * (5 - myRedondeo));
            else if (myRedondeo > 5)
                return (pTotal + 0.01 * (10 - myRedondeo));
            else
                return pTotal;
        } else {
            return 0.00;
        }
    }

    /**
     * Valida una fecha.
     * @param pJDialog
     * 				Objeto JDialog desde el cual se invocó la acción
     * @param pFecha
     * 				Fecha a evaluar
     * @param pMensaje
     * 				Mensaje a mostrar en caso de error
     * @return boolean
     * 				Indicador de la validez de la fecha evaluada
     *
     */
    public static boolean isFechaValida(Object pJDialog, String pFecha, 
                                        String pMensaje) {
        pFecha = pFecha.trim();
        boolean fechaCorrecta = true;
        try {
            if (pFecha.equalsIgnoreCase("") || (pFecha.trim().length() < 10) || 
                !validaFecha(pFecha, "dd/MM/yyyy")) {
                JOptionPane.showMessageDialog((JDialog)pJDialog, pMensaje, 
                                              "Mensaje del Sistema", 
                                              JOptionPane.WARNING_MESSAGE);
                fechaCorrecta = false;
            } else {
                Date fecha = getStringToDate(pFecha, "dd/MM/yyyy");
                Date fechaActual = 
                    getStringToDate(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA), 
                                    "dd/MM/yyyy");
                if (fecha.after(fechaActual)) {
                    JOptionPane.showMessageDialog((JDialog)pJDialog, 
                                                  pMensaje + " - " + 
                                                  "Fecha es mayor que la Fecha de hoy", 
                                                  "Mensaje del Sistema", 
                                                  JOptionPane.WARNING_MESSAGE);
                    fechaCorrecta = false;
                }
            }
        } catch (SQLException errValidarFecha) {
            JOptionPane.showMessageDialog((JDialog)pJDialog, 
                                          "Error en ejecución del Comando !!! - " + 
                                          errValidarFecha.getErrorCode(), 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
            errValidarFecha.printStackTrace();
        }
        return fechaCorrecta;
    }

    /**
     * Mueve el foco a un objeto determinado.
     * @param pObject Objeto que recibirá el foco
     */
    public static void moveFocus(Object pObject) {
        if (pObject instanceof JTextField) {
            ((JTextField)pObject).selectAll();
            ((JTextField)pObject).requestFocus();
        } else if (pObject instanceof JComboBox)
            ((JComboBox)pObject).requestFocus();
        else if (pObject instanceof JList)
            ((JList)pObject).requestFocus();
        else if (pObject instanceof JRadioButton)
            ((JRadioButton)pObject).requestFocus();
        else if (pObject instanceof JTable)
            ((JTable)pObject).requestFocus();
        else if (pObject instanceof JRadioButton)
            ((JRadioButton)pObject).requestFocus();
        else if (pObject instanceof JTextArea)
            ((JTextArea)pObject).requestFocus();
        else if (pObject instanceof JCheckBox)
            ((JCheckBox)pObject).requestFocus();
        else if (pObject instanceof JButton)
            ((JButton)pObject).requestFocus();
    }

    /**
     * Valida una fecha en base a un formato especificado.
     * @param pValue
     * 			Cadena con la fecha a evaluar
     * @param pDateFormat
     * 			Formato de fecha a emplear
     * @return boolean
     * 			Indicador de la validez de la fecha evaluada
     */
    public static boolean validaFecha(String pValue, String pDateFormat) {
        try {
            if (!pValue.equals("")) {
                int contaSlash = 0;
                char[] fecha = pValue.toCharArray();
                for (int i = 0; i < fecha.length; i++) {
                    if (fecha[i] == '/')
                        contaSlash += 1;
                }
                if (contaSlash > 2)
                    return false;
                else {
                    SimpleDateFormat formatter = 
                        new SimpleDateFormat(pDateFormat);
                    formatter.setLenient(false);
                    String dateString = 
                        formatter.format(formatter.parse(pValue));
                    return true;
                }
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }


    /**
     * Verifica si una cadena es apta para la conversión al tipo de dato Integer.
     * @param pValue
     * 				Valor a evaluar
     * @return boolean
     * 				Inicador de la validez de la cadena evaluada
     */
    public static boolean isInteger(String pValue) {
        try {
            if (!pValue.equals("")) {
                int valor = Integer.parseInt(pValue);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // Método que verifica si la cadena de busqueda

    /**
     * Encuentra un texto específico en una tabla.
     * @param pTable Tabla donde se realizará la úsqueda
     * @param pFindText Texto a ubicar
     * @param positionCode Ubicación del texto en la tabla
     * @param positionDescription Descripción de la posición
     * @return
     * 		Indica si se encontró el texto
     */
    public static boolean findTextInJTable(JTable pTable, String pFindText, 
                                           int positionCode, 
                                           int positionDescription) {
        String vCodigo;
        for (int k = 0; k < pTable.getRowCount(); k++) {
            vCodigo = (String)(pTable.getValueAt(k, positionCode));
            if (vCodigo.trim().equalsIgnoreCase(pFindText)) {
                FarmaGridUtils.showCell(pTable, k, 0);
                break;
            }
        }
        String currentCodigo = 
            (String)(pTable.getValueAt(pTable.getSelectedRow(), positionCode));
        String currentDescription = 
            ((String)(pTable.getValueAt(pTable.getSelectedRow(), 
                                        positionDescription))).toUpperCase();
        int i = 0;
        int longitud = 
            (pFindText.length() >= currentDescription.length()) ? currentDescription.length() : 
            pFindText.length();
        // Comparando codigo y descripc0ion del currentrow con el txtBusqueda
        if ((currentCodigo.trim().equalsIgnoreCase(pFindText) || 
             currentDescription.substring(0, 
                                          longitud).equalsIgnoreCase(pFindText))) {
            return true;
        }
        return false;
    }

    /**
     * Determina si un valor es del tipo Double y si es obligatorio.
     * @param pValue
     * 				Valor a Evaluar
     * @param pIsMandatory
     * 				Determina si es un valor obligatorio
     * @return boolean
     * 				Indicador del resultado de la validación
     *
     */
    public static boolean isDouble(String pValue, boolean pIsMandatory) {
        try {
            if (!pValue.equals("")) {
                double valor = Double.parseDouble(pValue);
                return true;
            } else {
                if (pIsMandatory)
                    return false;
                else
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Convierte una cadena en fecha.
     * @param pValue
     * 			Valor cadena con la representación de la fecha
     * @param pDateFormat
     * 			Formato a emplear para la conversión
     * @return Date
     * 			Valor tipo Date con la fecha especificada
     *
     */
    public static Date getStringToDate(String pValue, String pDateFormat) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pDateFormat);
            formatter.setLenient(false);
            return formatter.parse(pValue);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Muestra un texto en formato decimal.
     * @param pJTextField
     */
    public static void showInDecimalFormat(JTextField pJTextField) {
        pJTextField.setText(formatNumber(getDecimalNumber(pJTextField.getText().trim())));
    }

    /**
     * Completa una cadena con un simbolo para una longitud dada
     *
     * @param pValue
     *            Variable String que contiene el valor a formatear.
     * @param pLength
     *            Variable int que determina la longitud q tendrá la cadena.
     * @param pSymbol
     *            Variable String que contiene el simbolo a completar.
     * @param pAlign
     *            Variable String que determina la alineación del Simbolo.
     * @return String Cadena de blancos.
     */
    public static String completeWithSymbol(String pValue, int pLength, 
                                            String pSymbol, String pAlign) {
        String tempString = pValue;
        for (int i = pValue.length(); i < pLength; i++) {
            if (pAlign.trim().equalsIgnoreCase("I"))
                tempString = pSymbol + tempString;
            else
                tempString += pSymbol;
        }
        return tempString;
    }

    /**
     * Parte una cadena en un arreglo dada la longitud de las subcadenas
     *
     * @param pValue
     *            Variable String que contiene el valor a formatear.
     * @param pLength
     *            Variable int que determina la longitud q tendrá las
     *            sub-cadenas.
     * @return ArrayList Arreglo con las subcadenas
     */
    public static ArrayList splitString(String pValue, int pLength) {
        ArrayList pList = new ArrayList();
        String line = new String("");
        String word = new String("");
        StringTokenizer st = new StringTokenizer(pValue, " \n");
        while (st.hasMoreTokens()) {
            word = st.nextToken();
            if (line.length() + word.length() <= pLength) {
                line += word + " ";
            } else {
                pList.add(line.trim());
                line = word + " ";
            }
        }
        if (line.length() > 0)
            pList.add(line.trim());
        return pList;
    }

    /**
     * Alinea la variable int hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     *
     * @param parmint
     *            Variable int que será alineada.
     * @param parmLen
     *            Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *            String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(int parmint, int parmLen, 
                                           String parmCaracter) {
        return caracterIzquierda(String.valueOf(parmint), parmLen, 
                                 parmCaracter);
    }

    /**
     * Alinea la variable long hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     *
     * @param parmint
     *            Variable long que será alineada.
     * @param parmLen
     *            Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *            String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(long parmint, int parmLen, 
                                           String parmCaracter) {
        return caracterIzquierda(String.valueOf(parmint), parmLen, 
                                 parmCaracter);
    }

    /**
     * Alinea la variable double hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     *
     * @param parmint
     *            Variable double que será alineada.
     * @param parmLen
     *            Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *            String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(double parmint, int parmLen, 
                                           String parmCaracter) {
        return caracterIzquierda(String.valueOf(parmint), parmLen, 
                                 parmCaracter);
    }

    /**
     * Alinea la variable String hacia la derecha colocando CARACTERES a la
     * izquierda según la longitud que se establezca y el CARACTER dado.
     *
     * @param parmString
     *            Variable String que será alineada.
     * @param parmLen
     *            Longitud dentro de la cual será la alineación.
     * @param parmCaracter
     *            String de relleno para la alineación.
     * @return String String alineado a la derecha con el CARACTER.
     */
    public static String caracterIzquierda(String parmString, int parmLen, 
                                           String parmCaracter) {

        String tempString = parmString;

        if (tempString.length() > parmLen)
            tempString = 
                    tempString.substring(tempString.length() - parmLen, tempString.length());
        else {
            while (tempString.length() < parmLen)
                tempString = parmCaracter + tempString;
        }

        return tempString;

    }

    /**
     * Opera con elementos entre Listas (añade o retira elementos).
     * @param lista
     * 			Lista a operar
     * @param elemento
     * 			Elemento de la lista
     * @param valor
     * 			Indica si se realizara una adición o una sustracción a la lista
     * @param campo
     * 			Indice del campo a recuperar
     */
    public static void operaListaProd(ArrayList lista, ArrayList elemento, 
                                      Boolean valor, int campo) {
        if (!valor.booleanValue()) {
            String valorCampo = "";
            String valorCampoTmp = (String)(elemento.get(campo));
            for (int i = 0; i < lista.size(); i++) {
                valorCampo = (String)(((ArrayList)lista.get(i)).get(campo));
                if (valorCampo.equalsIgnoreCase(valorCampoTmp)) {
                    lista.remove(i);
                }
            }
        } else {
            lista.add(elemento);
        }
    }

    /**
     * Cancela una transacción SQL.
     */
    public static void liberarTransaccion() {
        try {
            System.out.println("*** LIBERANDO TRANSACCION !!! ***");
            FarmaSearch.liberarTransaccion();
        } catch (Exception sqlException) {
            //Log
            sqlException.printStackTrace();
            //Correo
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                          FarmaVariables.vCodLocal,
                                          //FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                          FarmaVariables.vEmail_Destinatario_Error_Impresion,
                                          "Error en FarmaUtility ",
                                          "Error al Liberar Transaccion ",
                                          "IP : " +FarmaVariables.vIpPc+"<br>"+
                                          "Error: " + sqlException.getMessage() ,
                                          ""); 
            FarmaConnection.closeConnection();
            try {
                FarmaConnection.getConnection();
            } catch (Exception sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
                //Correo
                FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                              FarmaVariables.vCodLocal,
                                              //FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                              FarmaVariables.vEmail_Destinatario_Error_Impresion,
                                              "Error en FarmaUtility ",
                                              "Error al Abrir Conexion ",
                                              "IP : " +FarmaVariables.vIpPc+"<br>"+
                                              "Error: " + sqlException.getMessage() ,
                                              "");                 
            }
        }
    }

    public static void liberarTransaccionRemota(int pTipoConexion,
                                                String pIndCloseConecction) {
        try {
            System.out.println("*** LIBERANDO TRANSACCION REMOTA!!! ***");
            FarmaSearch.liberarTransaccionRemota(pTipoConexion,
                                                 pIndCloseConecction);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }    

    /**
     *  Acepta la transaccion SQL.
     */
    public static void aceptarTransaccion() {
        try {
            System.out.println("*** ACEPTANDO TRANSACCION !!! ***");
            FarmaSearch.aceptarTransaccion();
        } catch (Exception sqlException) {
            //Log
            sqlException.printStackTrace();
            //Correo
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                          FarmaVariables.vCodLocal,
                                          //FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                          FarmaVariables.vEmail_Destinatario_Error_Impresion,
                                          "Error en FarmaUtility ",
                                          "Error al Aceptar Transaccion ",
                                          "IP : " +FarmaVariables.vIpPc+"<br>"+
                                          "Error: " + sqlException.getMessage() ,
                                          "");
            liberarTransaccion();
        }
    }

    public static void aceptarTransaccionRemota(int pTipoConexion,
                                                String pIndCloseConecction) {
        try {
            System.out.println("*** ACEPTANDO TRANSACCION REMOTA!!! ***");
            FarmaSearch.aceptarTransaccionRemota(pTipoConexion,
                                                 pIndCloseConecction);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    /**
	 * Determina los totales del comprobante.
	 * @param pTotales  Datos de los totales
	 * @param pJTable Tabla de totales
	 * @param pIGV Valor del IGV
	 */
    /*
	public static void totalesComprobante(ArrayList pTotales, JTable pJTable,
			double pIGV) {
		ArrayList myDatos = new ArrayList();
		double items = 0;
		int pagComprobante = 1;
		double total = 0.00;
		double totalSinImpuesto = 0.00;
		double totalSoles = 0.00;
		double totalSolesSinImpuesto = 0.00;
		for (int i = 0; i < pJTable.getRowCount(); i++) {
			if (items == FarmaConstants.ITEMS_POR_COMPROBANTE) {
				myDatos = new ArrayList();
				// Número de página del Comprobante
				myDatos.add(String.valueOf(pagComprobante));
				// Bruto
				myDatos.add(FarmaUtility.formatNumber((total / pIGV)
						+ totalSinImpuesto));
				// Dscto
				myDatos.add(FarmaUtility.formatNumber(total / pIGV - totalSoles
						/ pIGV + (totalSinImpuesto - totalSolesSinImpuesto)));
				// IGV
				myDatos.add(FarmaUtility.formatNumber(totalSoles
						- (totalSoles / pIGV)));
				// Total
				totalSoles += totalSolesSinImpuesto;
				myDatos.add(FarmaUtility.formatNumber(totalSoles));
				// Redondeo
				myDatos.add("0.00");
				// Arreglo de Totales
				pTotales.add(myDatos);
				// inicializando
				items = 0;
				pagComprobante += 1;
				total = 0.00;
				totalSinImpuesto = 0.00;
				totalSoles = 0.00;
				totalSolesSinImpuesto = 0.00;
				items += 1;
				if (((String) pJTable.getValueAt(i, 11))
						.equalsIgnoreCase(FarmaConstants.IMPUESTO_EXONERADO)) {
					totalSinImpuesto += (FarmaUtility.getDecimalNumber(pJTable
							.getValueAt(i, 3).toString()) * Double
							.parseDouble(pJTable.getValueAt(i, 6).toString()));
					totalSolesSinImpuesto += FarmaUtility
							.getDecimalNumber(pJTable.getValueAt(i, 7)
									.toString());
				} else {
					total += (FarmaUtility.getDecimalNumber(pJTable.getValueAt(
							i, 3).toString()) * Double.parseDouble(pJTable
							.getValueAt(i, 6).toString()));
					totalSoles += FarmaUtility.getDecimalNumber(pJTable
							.getValueAt(i, 7).toString());
				}
			} else {
				items += 1;
				if (((String) pJTable.getValueAt(i, 11))
						.equalsIgnoreCase(FarmaConstants.IMPUESTO_EXONERADO)) {
					totalSinImpuesto += (FarmaUtility.getDecimalNumber(pJTable
							.getValueAt(i, 3).toString()) * Double
							.parseDouble(pJTable.getValueAt(i, 6).toString()));
					totalSolesSinImpuesto += FarmaUtility
							.getDecimalNumber(pJTable.getValueAt(i, 7)
									.toString());
				} else {
					total += (FarmaUtility.getDecimalNumber(pJTable.getValueAt(
							i, 3).toString()) * Double.parseDouble(pJTable
							.getValueAt(i, 6).toString()));
					totalSoles += FarmaUtility.getDecimalNumber(pJTable
							.getValueAt(i, 7).toString());
				}
			}
		}
		myDatos = new ArrayList();
		// Número de página del Comprobante
		myDatos.add(String.valueOf(pagComprobante));
		// Bruto
		myDatos.add(FarmaUtility
				.formatNumber((total / pIGV) + totalSinImpuesto));
		// Dscto
		myDatos.add(FarmaUtility.formatNumber(total / pIGV - totalSoles / pIGV
				+ (totalSinImpuesto - totalSolesSinImpuesto)));
		// IGV
		myDatos
				.add(FarmaUtility
						.formatNumber(totalSoles - (totalSoles / pIGV)));
		totalSoles += totalSolesSinImpuesto;
		double redondeo = FarmaUtility.getRedondeo(totalSoles);
		totalSoles += redondeo;
		// Total
		myDatos.add(FarmaUtility.formatNumber(totalSoles));
		// Redondeo
		if (redondeo == 0.00)
			myDatos.add("0.00");
		else
			myDatos.add(String.valueOf(redondeo));
		// Arreglo de Totales
		pTotales.add(myDatos);
		/*
		 * for (int j=0; j<pTotales.size(); j++ )
		 * System.out.println((String)((ArrayList)pTotales.get(j)).get(0)+"..."+
		 * (String)((ArrayList)pTotales.get(j)).get(1)+"..."+
		 * (String)((ArrayList)pTotales.get(j)).get(2)+"..."+
		 * (String)((ArrayList)pTotales.get(j)).get(3)+"..."+
		 * (String)((ArrayList)pTotales.get(j)).get(4)+"..."+
		 * (String)((ArrayList)pTotales.get(j)).get(5));
		 *
	}
		 */

    /**
     * Establece el formato de fecha a una cadena.
     * @param pValue Valor a formatear
     * @param pDateFormat Formato a emplea
     * @return Valor formateado.
     */
    public static String getStringInFormatDate(String pValue, 
                                               String pDateFormat) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pDateFormat);
            formatter.setLenient(false);
            return formatter.format(formatter.parse(pValue));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Reinicializa la lista.
     * @param lista Limpia la lista
     */
    public static void removeAllListaProd(ArrayList lista) {
        lista = new ArrayList();
    }

    /**
     * Completar Vencimiento.
     * @param pJTextField Caja de texto desde la cual se invocó la accion.
     * @param e Evento Realizado
     */
    public static void completarVencimiento(JTextField pJTextField, 
                                            KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if ((pJTextField.getText().trim().length() == 2))
                pJTextField.setText(pJTextField.getText().trim() + "/");
        }
    }

    /**
     * Completa una hora en el formato "HH:mm".
     * @param pJTextField
     * 			Caja de texto desde la cual se invocó la acción
     * @param e
     * 			Evento realizado
     * @param withSeconds
     * 			Indica si se deben añadir segundos
     */
    public static void completarHora(JTextField pJTextField, KeyEvent e, 
                                     boolean withSeconds) {
        char keyChar = e.getKeyChar();
        if (e.getKeyCode() != KeyEvent.VK_ENTER) {
            if (Character.isDigit(keyChar)) {
                if (pJTextField.getText().trim().length() == 2 || 
                    (withSeconds && 
                     (pJTextField.getText().trim().length() == 5)))
                    pJTextField.setText(pJTextField.getText().trim() + ":");
            }
        }
    }

    /**
     * Valida una cadena en el formato "HH:mm"(hora y minuto)
     * @param pJDialog
     * 				Objeto JDialog desde el cual se invocó la acción
     * @param pHoraMinuto
     * 				Cadena a validar
     * @param pMensaje
     * 				Mensaje a mostrar en caso de error
     * @return boolean
     * 				Indicador de la validez de la cadena evaluada
     */
    public static boolean isHoraMinutoValida(Object pJDialog, 
                                             String pHoraMinuto, 
                                             String pMensaje) {
        if (pHoraMinuto.equalsIgnoreCase("") || 
            (pHoraMinuto.trim().length() < 5) || !validaTiempo(pHoraMinuto)) {
            JOptionPane.showMessageDialog((JDialog)pJDialog, pMensaje, 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Valida una cadena en el formato "HH:mm:ss"(hora , minuto y segundo)
     * @param pJDialog
     * 				Objeto JDialog desde el cual se invocó la acción
     * @param pHoraMinutoSegundo
     * 				Cadena a validar
     * @param pMensaje
     * 				Mensaje a mostrar en caso de error
     * @return boolean
     * 				Indicador de la validez de la cadena evaluada
     */
    public static boolean isHoraMinutoSegundoValida(Object pJDialog, 
                                                    String pHoraMinutoSegundo, 
                                                    String pMensaje) {
        if (pHoraMinutoSegundo.equalsIgnoreCase("") || 
            (pHoraMinutoSegundo.trim().length() < 8) || 
            !validaTiempo(pHoraMinutoSegundo)) {
            JOptionPane.showMessageDialog((JDialog)pJDialog, pMensaje, 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Valida una cadena en el formato HH:mm (hora y minuto)
     * @param pHora
     * 				Cadena a evaluar
     * @return boolean
     * 				Indicador de la validez de la cadena evaluada
     */
    public static boolean validaTiempo(String pHora) {
        if (!pHora.substring(2, 3).equalsIgnoreCase(":"))
            return false;
        if (pHora.trim().length() == 3)
            pHora = pHora + "00";
        if (pHora.trim().length() == 4)
            pHora = pHora + "0";
        String hora = pHora.substring(0, 2);
        String minuto = pHora.substring(3, 5);
        if (!isInteger(hora) || Integer.parseInt(hora) > 23)
            return false;
        if (!isInteger(minuto) || Integer.parseInt(minuto) > 59)
            return false;
        // if ( Integer.parseInt(hora)==0 && Integer.parseInt(minuto)==0 )
        // return false;
        if (pHora.length() > 5) {
            String segundo = pHora.substring(6, 8);
            if (!isInteger(segundo) || Integer.parseInt(segundo) > 59)
                return false;
        }
        return true;
    }

    /**
     * Devuelve la diferencia en días entre dos fechas
     * @param cal1 Fecha 1
     * @param cal2 Fecha 2
     * @return Diferencia en días.
     */
    public static long diferenciaEnDias(Calendar cal1, Calendar cal2) {
        long diferencia = 0;
        diferencia = cal1.getTime().getTime() - cal2.getTime().getTime();
        diferencia /= 86400000;
        return diferencia;
    }

    /**
     * Completa una cadena para formato "HH:mm" (horas y minutos)
     * @param pJTextField
     * 			Objeto JTextField desde el cual se efectuó la llamada a la acción
     * @param e
     * 			Evento realizado
     */
    public static void timeComplete(JTextField pJTextField, KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if ((pJTextField.getText().trim().length() == 2)) {
                pJTextField.setText(pJTextField.getText().trim() + ":");
            }
        }
    }

    static class AttributiveCellRenderer extends JLabel implements TableCellRenderer {

        protected Border noFocusBorder;
        private ArrayList rowsWithOtherColor;
        private Color backgroundColor;
        private Color foregroundColor;
        private boolean isBold = false;
        private boolean isWithCheck = false;

        public AttributiveCellRenderer(ArrayList pRowsWithOtherColor, 
                                       Color pBackgroundColor, 
                                       Color pForegroundColor, boolean pIsBold, 
                                       boolean pIsWithCheck) {
            rowsWithOtherColor = new ArrayList();
            rowsWithOtherColor = pRowsWithOtherColor;
            if (pBackgroundColor == null)
                backgroundColor = Color.white;
            else
                backgroundColor = pBackgroundColor;
            if (pForegroundColor == null)
                foregroundColor = Color.black;
            else
                foregroundColor = pForegroundColor;
            isBold = pIsBold;
            isWithCheck = pIsWithCheck;
            noFocusBorder = new EmptyBorder(1, 2, 1, 2);
            setOpaque(true);
            setBorder(noFocusBorder);
            rowWithColor = -1;
        }

        public Component getTableCellRendererComponent(JTable table, 
                                                       Object value, 
                                                       boolean isSelected, 
                                                       boolean hasFocus, 
                                                       int row, int column) {
            if (isSelected) {
                setBackground(new Color(10, 36, 106));
                //setForeground(Color.white);
                if (rowsWithOtherColor.size() > 0) {
                    verifyColumnNumber(column, row);
                    if ((rowWithColor >= 0) && (row == rowWithColor)) {
                        setForeground(Color.yellow);
                        setFont(new Font("sansserif", 1, 12));
                    } else {
                        setForeground(Color.white);
                        setFont(new Font("sansserif", 0, 12));
                    }
                } else {
                    setForeground(Color.white);
                    setFont(new Font("sansserif", 0, 12));
                }
            } else {
                if (rowsWithOtherColor.size() > 0) {
                    verifyColumnNumber(column, row);
                    /*
          if ( column==1 ) {
            for (int i=0; i<rowsWithOtherColor.size(); i++) {
              if ( row==Integer.parseInt((String)rowsWithOtherColor.get(i)) ) {
                rowWithColor = row;
                break;
              }
            }
          }
          */
                    if ((rowWithColor >= 0) && (row == rowWithColor)) {
                        setBackground(backgroundColor);
                        setForeground(foregroundColor);
                        if (isBold)
                            setFont(new Font("sansserif", 1, 12));
                        else
                            setFont(new Font("sansserif", 0, 12));
                    } else {
                        setBackground(Color.white);
                        setForeground(Color.black);
                        setFont(new Font("sansserif", 0, 12));
                    }
                } else {
                    setBackground(Color.white);
                    setForeground(Color.black);
                    setFont(new Font("sansserif", 0, 12));
                }
            }
            this.setBorder(noFocusBorder);
            this.setText((String)value);
            return this;
        }

        void verifyColumnNumber(int pColumn, int pRow) {
            boolean isRowWithColor = false;
            if (isWithCheck) {
                if (pColumn == 1)
                    isRowWithColor = true;
            } else {
                if (pColumn == 0)
                    isRowWithColor = true;
            }
            if (isRowWithColor) {
                for (int i = 0; i < rowsWithOtherColor.size(); i++) {
                    if (pRow == 
                        Integer.parseInt((String)rowsWithOtherColor.get(i))) {
                        rowWithColor = pRow;
                        break;
                    }
                }
            }
        }

    }

    /*
	 * static class AttributiveCellRenderer extends JLabel implements
	 * TableCellRenderer {
	 *
	 * protected Border noFocusBorder; private ArrayList rowsWithOtherColor;
	 * private Color backgroundColor; private Color foregroundColor; private
	 * boolean isBold = false; private boolean isWithCheck = false;
	 *
	 * public AttributiveCellRenderer(ArrayList pRowsWithOtherColor, Color
	 * pBackgroundColor, Color pForegroundColor, boolean pIsBold, boolean
	 * pIsWithCheck) { rowsWithOtherColor = new ArrayList(); rowsWithOtherColor =
	 * pRowsWithOtherColor; if ( pBackgroundColor==null ) backgroundColor =
	 * Color.white; else backgroundColor = pBackgroundColor; if (
	 * pForegroundColor==null ) foregroundColor = Color.black; else
	 * foregroundColor = pForegroundColor; isBold = pIsBold; isWithCheck =
	 * pIsWithCheck; noFocusBorder = new EmptyBorder(1, 2, 1, 2);
	 * setOpaque(true); setBorder(noFocusBorder); rowWithColor = -1; }
	 *
	 * public Component getTableCellRendererComponent(JTable table, Object
	 * value, boolean isSelected, boolean hasFocus, int row, int column) { if (
	 * isSelected ) { setBackground(new Color(10,36,106));
	 * //setForeground(Color.white); if ( rowsWithOtherColor.size()>0 ) {
	 * verifyColumnNumber(column,row); if ( (rowWithColor>=0) &&
	 * (row==rowWithColor) ) { setForeground(Color.yellow); setFont(new
	 * Font("sansserif",1,12)); } else { setForeground(Color.white); setFont(new
	 * Font("sansserif",0,12)); } } else { setForeground(Color.white);
	 * setFont(new Font("sansserif",0,12)); } } else { if (
	 * rowsWithOtherColor.size()>0 ) { verifyColumnNumber(column,row);
	 *
	 * //if ( column==1 ) { // for (int i=0; i<rowsWithOtherColor.size(); i++) { //
	 * if ( row==Integer.parseInt((String)rowsWithOtherColor.get(i)) ) { //
	 * rowWithColor = row; // break; // } // } //}
	 *
	 * if ( (rowWithColor>=0) && (row==rowWithColor) ) {
	 * setBackground(backgroundColor); setForeground(foregroundColor); if (
	 * isBold ) setFont(new Font("sansserif",1,12)); else setFont(new
	 * Font("sansserif",0,12)); } else { setBackground(Color.white);
	 * setForeground(Color.black); setFont(new Font("sansserif",0,12)); } } else {
	 * setBackground(Color.white); setForeground(Color.black); setFont(new
	 * Font("sansserif",0,12)); } } this.setBorder(noFocusBorder);
	 * this.setText((String)value); return this; }
	 *
	 * void verifyColumnNumber(int pColumn,int pRow) { boolean isRowWithColor =
	 * false; if ( isWithCheck ) { if ( pColumn==1 ) isRowWithColor = true; }
	 * else { if ( pColumn==0 ) isRowWithColor = true; } if ( isRowWithColor ) {
	 * for (int i=0; i<rowsWithOtherColor.size(); i++) { if (
	 * pRow==Integer.parseInt((String)rowsWithOtherColor.get(i)) ) {
	 * rowWithColor = pRow; break; } } } } }
	 */

    /**
     * Muestra un diálogo para guardar el contenido de una tabla en un archivo
     * @param pParentFrame
     * 				Objeto JFrame desde el cual se invocó la acción
     * @param pColumns
     * 				Columnas que se desaen salvar
     * @param pTable
     * 				Tabla con los datos a salvar
     * @param pAnchoTexto
     * 				Arreglo con los anchos de las columnas
     */
    public static void saveFile(Frame pParentFrame, FarmaColumnData[] pColumns, 
                                JTable pTable, int[] pAnchoTexto) {
        File lfFile = new File("C:\\");
        JFileChooser filechooser = new JFileChooser(lfFile);
        if (filechooser.showSaveDialog(pParentFrame) != 
            JFileChooser.APPROVE_OPTION)
            return;
        File fileChoosen = filechooser.getSelectedFile();
        try {
            FileWriter outFile = new FileWriter(fileChoosen);
            String linea = "";
            for (int i = 0; i < pTable.getColumnCount(); i++) {
                String lsCabecera = ((FarmaColumnData)pColumns[i]).m_title;
                lsCabecera = 
                        FarmaPRNUtility.alinearIzquierda(lsCabecera, pAnchoTexto[i]);
                linea += lsCabecera + "\t";
            }
            outFile.write(linea + "\n");
            for (int i = 0; i < pTable.getRowCount(); i++) {
                linea = "";
                for (int j = 0; j < pTable.getColumnCount(); j++) {
                    Object loValor = (Object)pTable.getValueAt(i, j);
                    String lscadena = "";
                    if (loValor instanceof String)
                        lscadena = 
                                FarmaPRNUtility.alinearIzquierda((String)loValor, 
                                                                 pAnchoTexto[j]);
                    else
                        lscadena = 
                                FarmaPRNUtility.alinearDerecha((String)loValor, 
                                                               pAnchoTexto[j]);
                    linea += lscadena + "\t";
                }
                outFile.write(linea + "\n");
            }
            outFile.close();
        } catch (IOException ioerr) {
            ioerr.printStackTrace();
        }
    }

    /**
     * Guarda el contenido de una tabla en un archivo especificado
     * @param pParentFrame
     * 				Objeto JFrame desde el cual se invocó la acción
     * @param pColumns
     * 				Columnas que se desaen salvar
     * @param pTable
     * 				Tabla con los datos a salvar
     * @param pAnchoTexto
     * 				Arreglo con los anchos de las columnas
     */
    public static void saveFileRuta(String pRutaArchivo, 
                                    FarmaColumnData[] pColumns, JTable pTable, 
                                    int[] pAnchoTexto) {

        File fileChoosen = new File(pRutaArchivo);

        try {
            FileWriter outFile = new FileWriter(fileChoosen);
            String linea = "";
            for (int i = 0; i < pTable.getColumnCount(); i++) {
                String lsCabecera = ((FarmaColumnData)pColumns[i]).m_title;
                lsCabecera = 
                        FarmaPRNUtility.alinearIzquierda(lsCabecera, pAnchoTexto[i]);
                linea += lsCabecera + "\t";
            }
            outFile.write(linea + "\n");
            for (int i = 0; i < pTable.getRowCount(); i++) {
                linea = "";
                for (int j = 0; j < pTable.getColumnCount(); j++) {
                    Object loValor = (Object)pTable.getValueAt(i, j);
                    String lscadena = "";
                    if (loValor instanceof String)
                        lscadena = 
                                FarmaPRNUtility.alinearIzquierda((String)loValor, 
                                                                 pAnchoTexto[j]);
                    else
                        lscadena = 
                                FarmaPRNUtility.alinearDerecha((String)loValor, 
                                                               pAnchoTexto[j]);
                    linea += lscadena + "\t";
                }
                outFile.write(linea + "\n");
            }
            outFile.close();
        } catch (IOException ioerr) {
            ioerr.printStackTrace();
        }
    }


    /**
     * Elimina los decimales de un valor double, convirtiéndolo a int
     * @param pDato
     * 			Valor a convertir
     * @return int
     * 			Valor converito a int
     */
    public static int trunc(double pDato) {
        String dato = String.valueOf(pDato);
        return Integer.parseInt(dato.substring(0, dato.lastIndexOf(".")));
    }

    /**
     * Elimina los decimales de un valor double(recibido como cadena), convirtiéndolo a int
     * @param pDato
     * 			Valor a convertir
     * @return int
     * 			Valor converito a int
     */
    public static int trunc(String pDato) {
        return trunc(FarmaUtility.getDecimalNumber(pDato));
    }

    /**
     * Filtra caracteres numéricos para una caja de texto
     * @param jtext Caja de texto a validar
     * @param e Evento realizado
     */
    public static void admitirDigitos(JTextField jtext, KeyEvent e) {
        char c = e.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || 
              (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_ENTER))) {
            // jtext.getToolkit().beep();
            e.consume();
        }
    }

    /**
     * Filtra numeros con formato decimal en una caja de texto
     * @param jtext Caja de texto a validar
     * @param e Evento realizado
     */
    public static void admitirDigitosDecimales(JTextField jtext, KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) && !(c == KeyEvent.VK_BACK_SPACE) && 
            !(c == KeyEvent.VK_DELETE) && !(c == KeyEvent.VK_ENTER) && 
            !(c == '.') && !(c == KeyEvent.VK_ALT)) {
            e.consume();
        }
    }

    /**
     * Filtra solo letras para una caja de texto
     * @param jtext Caja de texto a validar
     * @param e Evento Realizado
     */
    public static void admitirLetras(JTextField jtext, KeyEvent e) {
        char c = e.getKeyChar();
        if (!(Character.isLetter(c) || (Character.isSpaceChar(c)) || 
              (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || 
              (c == KeyEvent.VK_ENTER))) {
            // jtext.getToolkit().beep();
            e.consume();
        }
    }
    
    /**
     * Filtra letras, numero y espacio en blanco
     * @autor jcallo
     * @since 15.12.2008
     * @param jtext Caja de texto a validar
     * @param e Evento Realizado
     */
    public static void admitirLetrasNumeros(JTextField jtext, KeyEvent e) {
        char c = e.getKeyChar();
        if (!(Character.isLetter(c) || (Character.isSpaceChar(c)) || Character.isDigit(c)||
              (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || 
              (c == KeyEvent.VK_ENTER))) {
            // jtext.getToolkit().beep();
            e.consume();
        }
    }

    /**
     * Devuelve el mes en letras.
     * @param mes
     *            Mes(numero del mes en formato MM)
     */
    public static

    String devuelveMesEnLetras(int mes) {
        String mesletras = "";
        switch (mes) {
        case 1:
            mesletras = "Enero";
            break;
        case 2:
            mesletras = "Febrero";
            break;
        case 3:
            mesletras = "Marzo";
            break;
        case 4:
            mesletras = "Abril";
            break;
        case 5:
            mesletras = "Mayo";
            break;
        case 6:
            mesletras = "Junio";
            break;
        case 7:
            mesletras = "Julio";
            break;
        case 8:
            mesletras = "Agosto";
            break;
        case 9:
            mesletras = "Septiembre";
            break;
        case 10:
            mesletras = "Octubre";
            break;
        case 11:
            mesletras = "Noviembre";
            break;
        case 12:
            mesletras = "Diciembre";
            break;
        }
        return mesletras;
    }

    /**
     * Completa una fecha en el formato dd/mm/yyyy.
     * @param pJTextField
     * 				Caja de texto desde la cual se invocó la acción
     * @param e
     * 				Evento Realizado
     */
    public static void dateCompleteMes(JTextField pJTextField, KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if ((pJTextField.getText().trim().length() == 2)) {
                pJTextField.setText(pJTextField.getText().trim() + "/");
            }
        }
    }

    /**
     * Valida una fecha.
     * @param pJDialog
     * 				Objeto JDialog desde el cual se invocó la acción
     * @param pFecha
     * 				Fecha a validar
     * @param pMensaje
     * 				Mensaje a mostrar en caso de error
     * @return
     * 			Indica si la fecha es válida
     */
    public static boolean isFechaMesValida(Object pJDialog, String pFecha, 
                                           String pMensaje) {
        pFecha = pFecha.trim();
        boolean fechaCorrecta = true;

        if ((pFecha.trim().length() != 7) || 
            !validaFechaMes(pFecha, "MM/yyyy")) {
            JOptionPane.showMessageDialog((JDialog)pJDialog, pMensaje, 
                                          "Mensaje del Sistema", 
                                          JOptionPane.WARNING_MESSAGE);
            fechaCorrecta = false;
        }
        return fechaCorrecta;
    }

    /**
     * Valida una fecha en base a un formato especificado.
     * @param pValue
     * 			Cadena con la fecha a evaluar
     * @param pDateFormat
     * 			Formato de fecha a emplear
     * @return boolean
     * 			Indicador de la validez de la fecha evaluada
     */
    public static boolean validaFechaMes(String pValue, String pDateFormat) {
        try {
            if (!(pValue.length() != 7)) {
                SimpleDateFormat formatter = new SimpleDateFormat(pDateFormat);
                formatter.setLenient(false);
                String dateString = formatter.format(formatter.parse(pValue));
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Reemplaza una cadena específica por otra.
     * @param p_strMain
     * 			Cadena donde se buscará el texto a reemplazar
     * @param p_strSearch
     * 			Texto a reemplazar
     * @param p_strReplace
     * 			Texto de reemplazo
     * @return String
     * 			Texto modificado
     */
    public static String replaceString(String p_strMain, String p_strSearch, 
                                       String p_strReplace) {
        if (p_strMain == null || p_strMain.trim().length() == 0)
            return "";
        int i;
        int v_posstartsearch;
        int v_posendsearch;
        int v_posini = 0;
        StringBuffer v_strBuff = new StringBuffer();
        int len = p_strMain.length();
        boolean v_end = false;
        while (!v_end) {
            v_posstartsearch = p_strMain.indexOf(p_strSearch, v_posini);
            if (v_posstartsearch >= 0) {
                v_posendsearch = v_posstartsearch + p_strSearch.length();
                v_strBuff.append(p_strMain.substring(v_posini, 
                                                     v_posstartsearch));
                v_strBuff.append(p_strReplace);
                v_posini = v_posendsearch;
            } else {
                v_strBuff.append(p_strMain.substring(v_posini));
                v_end = true;
            }
        }
        return v_strBuff.toString();
    }

    /**
     * Realiza la Suma de toda una Colunma de un Jtable y lo retorna como String
     * con formato.
     *
     * @param pTable
     *            Objeto tipo JTable que contiene los registros a sumar
     * @param pColumna
     *            Número entero que especifica la Columna a Suma
     */
    public static

    double sumColumTable(JTable pTable, int pColumna) {
        double suma = 0.00;
        for (int p = 0; p < pTable.getRowCount(); p++) {
            // System.out.println(p+"="+suma);
            suma += 
                    getDecimalNumber(((String)pTable.getValueAt(p, pColumna)).trim());
            suma = getDecimalNumber(formatNumber(suma));
        }
        return suma;
    }

    public static double sumColumTableModel(FarmaTableModel pTable, int pColumna)
    {
        double suma = 0.00;
        for (int p = 0; p < pTable.getRowCount(); p++)
        {
            // System.out.println(p+"="+suma);
            suma += 
                    getDecimalNumber(((String)pTable.getValueAt(p, pColumna)).trim());
            suma = getDecimalNumber(formatNumber(suma));
        }
        return suma;
    }

    /**
     * Obtiene la dirección IP de la máquina desde la cual se ejecuta la petición
     * @return Dirección IP
     */
    public static String getHostAddress() {
        String strIP = "";
        try {
            InetAddress thisIp = InetAddress.getLocalHost();
            strIP = thisIp.getHostAddress();
            System.out.println("IP = " + strIP);
        } catch (Exception e) {
            e.printStackTrace();
            strIP = "";
        }
        return strIP;
    }

    /**
     * Obtiene la el nombre de la máquina desde la cual se ejecuta la petición
     * @return Nombre de la máquina
     */
    public static String getHostName() {
        String hostName = "";
        try {
            InetAddress thisIp = InetAddress.getLocalHost();
            hostName = thisIp.getHostName();
            System.out.println("Host Name = " + hostName);
        } catch (Exception e) {
            e.printStackTrace();
            hostName = "";
        }
        return hostName;
    }

    /**
     * Ordena el TableModel, según la columna especificada y el tipo de ordenamiento.
     * @param table Tabla
     * @param tableModel Tabla Modelo
     * @param posColumna Posición de Columna
     * @param orden Orden:"asc"-"desc"
     */
    public static void ordenar(JTable table, FarmaTableModel tableModel, 
                               int posColumna, String orden) {
        Collections.sort(tableModel.data, 
                         new FarmaTableComparator(posColumna, true));
        if (orden.equalsIgnoreCase(FarmaConstants.ORDEN_DESCENDENTE))
            Collections.reverse(tableModel.data);
        if (table.getRowCount() > 0)
            FarmaGridUtils.showCell(table, 0, 0);
    }

    /**
     * Ordena el Arreglo, según la columna especificada y el tipo de ordenamiento.
     * @param ArrayList Array
     * @param posColumna Posición de Columna
     * @param orden Orden:"asc"-"desc"
     */
    public static void ordenar(ArrayList pArrayList, int posColumna, 
                               String orden) {
        Collections.sort(pArrayList, 
                         new FarmaTableComparator(posColumna, true));
        if (orden.equalsIgnoreCase(FarmaConstants.ORDEN_DESCENDENTE))
            Collections.reverse(pArrayList);
    }


    /**
     * Valida un rango de fechas; tenga el formato válido y si permite rango abierto.
     * @param dialogo JDialog
     * @param txtFechaInicial JTextField que contiene la cadena de fecha inicial
     * @param txtFechaFinal JTextField que contiene la cadena de fecha final
     * @param aceptaRangoAbierto true permite; false, no
     * @param aceptaFechasIguales true permite; false, no
     * @param aceptaMismoDia true permite; false, no - que la fecha de inicio sea igual a la fecha de sistema
     */
    public static boolean validarRangoFechas(JDialog dialogo, 
                                             JTextField txtFechaInicial, 
                                             JTextField txtFechaFinal, 
                                             boolean aceptaRangoAbierto, 
                                             boolean aceptaFechasIguales, 
                                             boolean aceptaMismoDia) {

        if (!FarmaUtility.validaFecha(txtFechaInicial.getText(), 
                                      "dd/MM/yyyy") || 
            txtFechaInicial.getText().length() != 10) {
            FarmaUtility.showMessage(dialogo, 
                                     "Ingrese la Fecha Inicial correctamente", 
                                     txtFechaInicial);
            return false;
        }

        if (!aceptaRangoAbierto || txtFechaFinal.getText().length() != 0)
            if (!FarmaUtility.validaFecha(txtFechaFinal.getText(), 
                                          "dd/MM/yyyy") || 
                txtFechaFinal.getText().length() != 10) {
                FarmaUtility.showMessage(dialogo, 
                                         "Ingrese la Fecha Final correctamente", 
                                         txtFechaFinal);
                return false;
            }

        Date fecIni = 
            FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(), 
                                         "dd/MM/yyyy");
        Date fecSys = fecIni;

        try {
            fecSys = 
                    FarmaUtility.getStringToDate(FarmaSearch.getFechaHoraBD(1), 
                                                 "dd/MM/yyyy");
        } catch (Exception e) {
            System.out.println("" + e.getStackTrace());
        }

        if (fecSys.after(fecIni)) {
            FarmaUtility.showMessage(dialogo, 
                                     "La Fecha Inicial no puede ser anterior a la fecha actual", 
                                     txtFechaInicial);
            return false;
        }

        if (!aceptaRangoAbierto || txtFechaFinal.getText().length() != 0) {
            Date fecFin = 
                FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(), 
                                             "dd/MM/yyyy");

            if (fecSys.after(fecFin)) {
                FarmaUtility.showMessage(dialogo, 
                                         "La Fecha Final no puede ser anterior a la fecha actual", 
                                         txtFechaFinal);
                return false;
            }

            if (fecIni.after(fecFin)) {
                FarmaUtility.showMessage(dialogo, 
                                         "La Fecha Inicial no puede ser posterior a la Fecha Final", 
                                         txtFechaInicial);
                return false;
            }

            if (!aceptaFechasIguales) {
                if (fecIni.equals(fecFin)) {
                    FarmaUtility.showMessage(dialogo, 
                                             "La Fecha Final no puede ser igual a la Fecha Inicial", 
                                             txtFechaFinal);
                    return false;
                }
            }
        }

        if (!aceptaMismoDia) {
            if (fecIni.equals(fecSys)) {
                FarmaUtility.showMessage(dialogo, 
                                         "La Fecha Inicial no puede ser igual a la Fecha de Sistema", 
                                         txtFechaInicial);
                return false;
            }
        }

        return true;
    }

    //---------------------------------------------------------------------------------------------
/*
 *       if(!FarmaUtility.validarRangoFechas(this,txtFech_Exp,txtFech_Venc,false,true,true,true))
           
 * */
    public static boolean validarRangoFechas(JDialog dialogo, 
                                             JTextField txtFechaInicial, 
                                             JTextField txtFechaFinal, 
                                             boolean aceptaRangoAbierto, 
                                             boolean aceptaFechasIguales, 
                                             boolean aceptaMismoDia, 
                                             boolean aceptaFechaAnterior) {

        if (!FarmaUtility.validaFecha(txtFechaInicial.getText(), 
                                      "dd/MM/yyyy") || 
            txtFechaInicial.getText().length() != 10) {
            FarmaUtility.showMessage(dialogo, 
                                     "Ingrese la Fecha Inicial correctamente", 
                                     txtFechaInicial);
            return false;
        }

        if (!aceptaRangoAbierto || txtFechaFinal.getText().length() != 0)
            if (!FarmaUtility.validaFecha(txtFechaFinal.getText(), 
                                          "dd/MM/yyyy") || 
                txtFechaFinal.getText().length() != 10) {
                FarmaUtility.showMessage(dialogo, 
                                         "Ingrese la Fecha Final correctamente", 
                                         txtFechaFinal);
                return false;
            }

        Date fecIni = 
            FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(), 
                                         "dd/MM/yyyy");
        Date fecSys = fecIni;

        try {
            fecSys = 
                    FarmaUtility.getStringToDate(FarmaSearch.getFechaHoraBD(1), 
                                                 "dd/MM/yyyy");
        } catch (Exception e) {
            System.out.println("" + e.getStackTrace());
        }

        if (!aceptaFechaAnterior) {
            if (fecSys.after(fecIni)) {
                FarmaUtility.showMessage(dialogo, 
                                         "La Fecha Inicial no puede ser anterior a la fecha actual", 
                                         txtFechaInicial);
                return false;
            }
        }
        if (!aceptaRangoAbierto || txtFechaFinal.getText().length() != 0) {
            Date fecFin = 
                FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(), 
                                             "dd/MM/yyyy");

            if (!aceptaFechaAnterior) {
                if (fecSys.after(fecFin)) {
                    FarmaUtility.showMessage(dialogo, 
                                             "La Fecha Final no puede ser anterior a la fecha actual", 
                                             txtFechaFinal);
                    return false;
                }
            }

            if (fecIni.after(fecFin)) {
                FarmaUtility.showMessage(dialogo, 
                                         "La Fecha Inicial no puede ser posterior a la Fecha Final", 
                                         txtFechaInicial);
                return false;
            }

            if (!aceptaFechasIguales) {
                if (fecIni.equals(fecFin)) {
                    FarmaUtility.showMessage(dialogo, 
                                             "La Fecha Final no puede ser igual a la Fecha Inicial", 
                                             txtFechaFinal);
                    return false;
                }
            }
        }

        if (!aceptaMismoDia) {
            if (fecIni.equals(fecSys)) {
                FarmaUtility.showMessage(dialogo, 
                                         "La Fecha Inicial no puede ser igual a la Fecha de Sistema", 
                                         txtFechaInicial);
                return false;
            }
        }

        return true;
    }


    public static void moveFocusJTable(JTable pTable) {
        moveFocus(pTable);
        if (pTable.getRowCount() > 0)
            FarmaGridUtils.showCell(pTable, 0, 0);
    }

    /**
     * Muestra un diálogo de mensaje en un Frame.
     * @param pJFrame
     * 				Objeto JFrame desde el cual se invocó la acción
     * @param pMessage
     * 				Mensaje a mostrar
     * @param pObject
     * 				Objeto
     */
    public static void showMessage(JFrame pJFrame, String pMessage, 
                                   Object pObject) {
        System.err.println(pMessage);
        JOptionPane.showMessageDialog(pJFrame, pMessage, "Mensaje del Sistema", 
                                      JOptionPane.WARNING_MESSAGE);
        if (pObject != null)
            moveFocus(pObject);
    }

    /**
     * Coloca un objeto JFrame en el centro de la pantalla.
     *
     * @param pDialog
     *            Objeto JFrame a centrar.
     */
    public static void centrarVentana(JFrame pDialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension objectSize = pDialog.getSize();
        if (objectSize.height > screenSize.height) {
            objectSize.height = screenSize.height;
        }
        if (objectSize.width > screenSize.width) {
            objectSize.width = screenSize.width;
        }
        pDialog.setLocation((screenSize.width - objectSize.width) / 2, 
                            (screenSize.height - objectSize.height) / 2);
    }

    /**
     * Coloca un objeto JFrame en el centro de la pantalla.
     *
     * @param pJTable  Objeto JTable donde se busca el valor del campo.
     * @param pRow     fila donde se busca el valor del campo.
     * @param pColumn  columna donde se busca el valor del campo.
     */
    public static String getValueFieldJTable(JTable pJTable, int pRow, 
                                             int pColumn) {
        String value = "";
        if (pJTable == null || pRow < 0 || pJTable.getRowCount() <= pRow || 
            pColumn < 0 || pJTable.getColumnCount() <= pColumn)
            return value;
        value = ((String)pJTable.getValueAt(pRow, pColumn)).trim();
        return value;
    }

    /**
     * Obtiene el nombre de máquina de una IP dada
     * @return Nombre de la máquina
     */
    public static String getHostNameRemotePC(String pIpRemotePC) {
        String hostName = "";
        try {
            InetAddress remoteIp = InetAddress.getByName(pIpRemotePC);
            hostName = remoteIp.getHostName();
            System.out.println("Host Name Remote IP= " + hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostName = pIpRemotePC;
        } catch (Exception e) {
            e.printStackTrace();
            hostName = pIpRemotePC;
        }
        return hostName;
    }

    /**
     * Muestra un diálogo de confirmación con respuesta por defecto NO
     *
     * @param pJDialog
     *            Objeto JDialog desde el cual se invocó a la función
     * @param pMensaje
     *            Título de la Ventana.
     * @return boolean Respuesta obtenida de el dialog de confirmación.
     */
    public static boolean rptaConfirmDialogDefaultNo(JDialog pJDialog, 
                                                     String pMensaje) {
        String strSI = "Si";
        String strNO = "No";
        Object[] options = { strSI, strNO };
        int rptaDialogo = 
            JOptionPane.showOptionDialog(pJDialog, pMensaje, "Mensaje del Sistema", 
                                         JOptionPane.YES_NO_OPTION, 
                                         JOptionPane.QUESTION_MESSAGE, null, 
                                         options, strNO);
        if (rptaDialogo == JOptionPane.YES_OPTION)
            return true;
        else
            return false;
    }

    /**
     * Coloca un objeto JFrame en el centro de la pantalla.
     *
     * @param pArrayList  Objeto ArrayList, que contiene objetos ArrayList, donde se busca el valor del campo.
     * @param pRow     fila donde se busca el valor del campo.
     * @param pColumn  columna donde se busca el valor del campo.
     */
    public static String getValueFieldArrayList(ArrayList pArrayList, int pRow, 
                                                int pColumn) {
        String value = "";
        if (pArrayList == null || pRow < 0 || pArrayList.size() <= pRow || 
            pColumn < 0 || ((ArrayList)pArrayList.get(pRow)).size() <= pColumn)
            return value;
        value = 
                ((String)((ArrayList)pArrayList.get(pRow)).get(pColumn)).trim();
        return value;
    }

    /**
     * Completa una fecha en el formato dd/mm/yyyy.
     * @param pJTextField Caja de texto desde la cual se invocó la acción
     * @param e Evento Realizado
     */
    public static void dateHourComplete(JTextField pJTextField, KeyEvent e) {
        try {
            String anhoBD = 
                FarmaSearch.getFechaHoraBD(1).trim().substring(6, 10);
            anhoBD = anhoBD + " ";
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                if ((pJTextField.getText().trim().length() == 2) || 
                    (pJTextField.getText().trim().length() == 5)) {
                    pJTextField.setText(pJTextField.getText().trim() + "/");
                    if (pJTextField.getText().trim().length() == 6)
                        pJTextField.setText(pJTextField.getText().trim() + 
                                            anhoBD);
                }
                if ((pJTextField.getText().trim().length() == 13) || 
                    (pJTextField.getText().trim().length() == 16)) {
                    pJTextField.setText(pJTextField.getText().trim() + ":");
                    if (pJTextField.getText().trim().length() == 17)
                        pJTextField.setText(pJTextField.getText().trim() + 
                                            "00");
                }
            }
        } catch (SQLException errAnhoBD) {
            errAnhoBD.printStackTrace();
        }
    }

    /**
     * Muestra un diálogo de confirmación
     * @param pJFrame
     * @param pMensaje
     * @return <b>true</b> operacion aceptada por el usuario
     * @author Edgar Rios Navarro
     * @since 27.09.2007
     */
    public static boolean rptaConfirmDialog(JFrame pJFrame, String pMensaje) {
        int rptaDialogo = 
            JOptionPane.showConfirmDialog(pJFrame, pMensaje, "Mensaje del Sistema", 
                                          JOptionPane.YES_NO_OPTION, 
                                          JOptionPane.QUESTION_MESSAGE);
        if (rptaDialogo == JOptionPane.YES_OPTION)
            return true;
        else
            return false;
    }

    /**
     * Dialogo Input de Texto
     * @param pJDialog
     * 				Objeto JDialog desde el cual se invocó la acción
     * @param pTexto
     * 				Cadena de Titulo para el Dialogo
     * @param pObject
     * 				Objeto
     * @author dubilluz
     * @since  26.10.2007
     */
    public static String ShowInput(JDialog pJDialog, String pTexto) {

        String resultado = 
            (String)JOptionPane.showInputDialog(pJDialog, pTexto + " :\n", 
                                                "MiFarma", 
                                                JOptionPane.PLAIN_MESSAGE);
        if (resultado == null)
            resultado = "";

        resultado = resultado.trim().toUpperCase();

        return resultado.trim();

    }


   /**
     * Obtiene el indicador en Linea con la BDConexion indicada
     * @author dubilluz
     * @since  19.08.2008
     */
     public static String getIndLineaOnLine(int pBDConexion,
                                            String pIndCloseConecction)
      {
       
       /*
       int t_estimado = Integer.parseInt(time_estimado_consulta().trim())*1000;
       int t_actual   = Integer.parseInt(time_diferencia_actual(pBDConexion,
                                                                pIndCloseConecction).trim());
       System.out.println("t_actual 1 : " + t_actual);
       //Se toma en cuenta el segundo valor dado que 
       //La segunda vez es el tiempo optimo para obtener el valor
       t_actual   = Integer.parseInt(time_diferencia_actual(pBDConexion,
                                                            pIndCloseConecction).trim());
       System.out.println("t_actual 2 : " + t_actual);        
       System.out.println("Valor Estimado : " + t_estimado);
       System.out.println("Valor Obtenido : " + t_actual);
        if(t_actual > t_estimado) 
           return FarmaConstants.INDICADOR_N;
        else       
           return FarmaConstants.INDICADOR_S;
       */

       int t_estimado = 0;


    	 t_estimado = Integer.parseInt(time_out_conn().trim());


       String vRes =  consulta_Prueba(pBDConexion,pIndCloseConecction,t_estimado);
       return vRes.trim();
      }     
    

    /**
     * Obtiene el Tiempo que demora la consulta a la BDConexion indicada
     * mediante una consulta de prueba el resultado retorna el tiempo en 
     * milisegundos
     * @author dubilluz
     * @since  18.08.2008
     */
    private static String time_diferencia_actual(int pBDConexion,
                                                 String pIndCloseConecction) 
    { 
      Date fecha1 = new Date();
      long milisegundos1 = fecha1.getTime();
      //System.out.println("Consulta a delivery :"+ consulta_Prueba(pBDConexion,
        //                                                          pIndCloseConecction));
      Date fecha2 = new Date();
      long milisegundos2 = fecha2.getTime();   
      return "" + (milisegundos2-milisegundos1);   
    }   
    /**
     * Tiempo estimado de consulta a Matriz
     * @author dubilluz
     * @since  18.08.2008
     */
     private static String time_estimado_consulta()
     {
      String time = "";
       try
       {
         ArrayList parametros = new ArrayList();
         parametros.add(FarmaVariables.vCodGrupoCia);
         time = FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Utility.GET_TIEMPOESTIMADO_CONEXION(?)",
                                                            parametros);
       } catch(SQLException sql)
       {
         sql.printStackTrace();
       }
       
       return time.trim();
       
     }  

    private static String time_out_conn()
    {
     String time = "";
      try
      {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        time = FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Utility.GET_TIME_OUT_CONN_REMOTA(?)",
                                                           parametros);
      } catch(SQLException sql)
      {
        sql.printStackTrace();
      }
      
      return time.trim();

    }

    private static String time_out_conn_rac()
    {
     String time = "";
      try
      {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        time = FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Utility.GET_TIME_OUT_CONN_REMOTA_RAC(?)",
                                                           parametros);
      } catch(SQLException sql)
      {
        sql.printStackTrace();
      }

      return time.trim();

    }

    /**
     * Realiza la Consulta a BDConexion indicada
     * @author dubilluz
     * @since  18.08.2008
     */
     private static String consulta_Prueba(int pBDConexion,
                                           String pIndCloseConecction,
                                           int pTimeOut)
     {
      System.out.println("Consultando a BD indicada");
      String  prueba = "";
       try
       {
         prueba = FarmaDBUtilityRemoto.executeSQLQueryWithTimeOut("Select sysdate from dual",
                                                       pBDConexion,
                                                       pIndCloseConecction,
                                                       pTimeOut);
           prueba = "S";  
           
       } catch(SQLException sql)
       {
         sql.printStackTrace();         
         return "N";
       }    
       return prueba;
     }
    
    public static void enviaCorreoPorBD(String pCodCia,String pCodLocal,
                                        String pReceiverAddress,
                                        String pAsunto,
                                        String pTitulo,
                                        String pMensaje,
                                        String pCCReceiverAddress){
        System.out.println("Enviando Correo.. por BD");
        String  prueba = "";
        ArrayList prm = new ArrayList();
        prm.add(pCodCia);
        prm.add(pCodLocal);
        prm.add(pReceiverAddress);
        prm.add(pAsunto);
        prm.add(pTitulo);
        prm.add(pMensaje);
        prm.add(pCCReceiverAddress);
        
         try
         {
           FarmaDBUtility.executeSQLStoredProcedure(null,
                                                    "FARMA_UTILITY.envia_correo(?,?,?,?,?,?,?)",
                                                    prm,
                                                    true);
         } catch(SQLException sql)
         {
           sql.printStackTrace();
           
         }    
         
    }
    
    public static boolean validarEmail( String email ) { 
        
        boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);
        
        return b;
       }
    
    public static boolean validarHora (String pHora){
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", pHora);
    }
    
    public static Date obtiene_fecha(String fecha , String hora)
    { 
      //System.out.println("Fecha a Convertir >>> "+fecha+ "  "+ hora );
      int año = Integer.parseInt(fecha.substring(6,fecha.length()));
      int mes = Integer.parseInt(fecha.substring(3,5));
      int dia = Integer.parseInt(fecha.substring(0,2)); 
      
      int horas   = Integer.parseInt(hora.substring(0,2)); 
      int minutos = Integer.parseInt(hora.substring(3,5)); 
      int segundos = Integer.parseInt(hora.substring(6,8));    
      
      Date fecha_convertida = new Date(año - 1901,mes + 11,dia,horas,minutos,segundos);
      //System.out.println("Luego de COnversion >> "+fecha_convertida.toString());
     
     return fecha_convertida;
    }
    
    public static boolean valida_fecha_Inicial_Final(String fec1, String fec2){
        boolean flag = false;
        Date f1 = null, f2 = null;
        if(isFechaValida(fec1) && isFechaValida(fec2)){
            f1 = obtiene_fecha(fec1,"00:00:00");
            f2 = obtiene_fecha(fec2,"00:00:00");                
        
            if(f1.before(f2)){
                flag = true;
            }
            if(f1.equals(f2)){
                flag = true;
            }
        }
        else {
            flag = false;
        }
        return flag;    
    }
    
    public static boolean isFechaValida(String fechax) {
    try {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy",
        Locale.getDefault());
        formatoFecha.setLenient(false);
        formatoFecha.parse(fechax);
    } catch (ParseException e) {
        return false;
    }
        return true;
    } 
    /*
     * jquispe 04.08.2010
     * 
     * */
    
    private static String AlgoritmoRC4(String mensaje,String key)
    {
            int estados[] = new int [256];
            int x=0;
            int y=0;
            int index1=0;
            int index2=0;
            int nMen;
            int i;
            String mensajeCifrado="";
            /*******variables auxiliares********/
            int aux;
            
            for(i=0;i<256;i++) estados[i]=i;
            for(i=0;i<256;i++){
                    index2=  (getAscii(getPos(key,index1))+ estados[i]+index2)%256;
                aux= estados[i];
                estados[i]=estados[index2];
                estados[index2]=aux;
                index1=(index1+1)%key.length();
                }
            
            for(i=0;i<mensaje.length();i++){
                    x=(x+1)%256;
                y=(estados[x]+y)%256;
                aux= estados[x];
                estados[x]=estados[y];
                estados[y]=aux;
                nMen=xor(getAscii(getPos(mensaje,i)),estados[(estados[x]+estados[y])%256]);
                mensajeCifrado=mensajeCifrado+"-"+((String.valueOf(Integer.toHexString(nMen)).length()>1)? Integer.toHexString(nMen):"0"+Integer.toHexString(nMen));
                }
            return mensajeCifrado.substring(1,mensajeCifrado.length());
    }
    /***********Funciones Auxiliares RC4****************/
    private static int getAscii(char pChar)
    {
            return (int)pChar;
    }
    
    private static char getPos(String pCadena,int pIndice)
    {
            return pCadena.charAt(pIndice);
    }
    
    private static int xor(int val01,int val02)
    {
            return val01^val02;
    }
    
    /*****************Funciones Auxiliares Verhoeff**************************/
    private static String invertirCadena(String cadena)
    {
            String sCadenaInvertida ="";
            for(int j=cadena.length()-1;j>=0;j--)
            {
                    sCadenaInvertida=sCadenaInvertida+cadena.charAt(j);
            }
            return sCadenaInvertida;
    }
    /********************Algoritmo Base64***********************************/
    
    private static String ObtenerBase64(long Numero) { 
      
      char Diccionario[] =     {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
                               'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
                               'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
                               'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
                               'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
                               'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
                               'y', 'z', '+', '/'};
      
      long Cociente = 1L;
      int Resto=0; 
      String Palabra = ""; 
     
      while (Cociente > 0) {
     
      Cociente = Numero/64; 
      Resto = (int)Numero%64;          
      Palabra = Diccionario[Resto] + Palabra;      
      //System.out.println(Resto);
      Numero = Cociente;         
      }
     
      return Palabra;          
    }
     
    
    /********************Algoritmo Verhoeff***********************************/
    private static int Verhoeff(String numero)
    {
    String cifra="";        
    
    int Mul[][] ={
                    {0,1,2,3,4,5,6,7,8,9},
                    {1,2,3,4,0,6,7,8,9,5},
                    {2,3,4,0,1,7,8,9,5,6},
                    {3,4,0,1,2,8,9,5,6,7},
                    {4,0,1,2,3,9,5,6,7,8},
                    {5,9,8,7,6,0,4,3,2,1},
                    {6,5,9,8,7,1,0,4,3,2},
                    {7,6,5,9,8,2,1,0,4,3},
                    {8,7,6,5,9,3,2,1,0,4},
                    {9,8,7,6,5,4,3,2,1,0}           
            };      
    int Per[][] = { 
                    {0,1,2,3,4,5,6,7,8,9},
                    {1,5,7,6,2,8,3,0,9,4},
                    {5,8,0,3,7,9,6,1,4,2},
                    {8,9,1,6,0,4,3,5,2,7},
                    {9,4,5,3,1,2,6,8,7,0},
                    {4,2,8,6,5,7,3,9,0,1},
                    {2,7,9,3,8,0,6,4,1,5},
                    {7,0,4,6,9,1,3,2,5,8} 
                    };
    
    int inv[] = {0,4,3,2,1,5,6,7,8,9}; 
    int Check = 0;
    int i;
    
    cifra  = numero;
    String NumeroInvertido;
    
    NumeroInvertido=invertirCadena(cifra);
    
    for(i=0;i<NumeroInvertido.length();i++)
            {Check = Mul[Check][Per[((i+1)%8)][Integer.parseInt(String.valueOf(getPos(NumeroInvertido,i)))]];
            }
    return inv[Check];
    }
    
    /*********************Digito de Control******************************/
    
    public static String Digito_Control(){
       //agrega digitos verif Verhoeff
       //paso1      
       long suma=0L;
       long suma2Ver=0L;
       FarmaVariables.N_Factura=setDigitoVerhoeff(FarmaConstants.N_Factura,2);
       FarmaVariables.N_Nit=setDigitoVerhoeff(FarmaConstants.N_Nit,2);
       FarmaVariables.F_Fecha_Tr=setDigitoVerhoeff(FarmaConstants.F_Fecha_Tr,2);
       FarmaVariables.N_Monto_tr=setDigitoVerhoeff(FarmaConstants.N_Monto_tr,2);
       FarmaVariables.N_Autorizacion=setDigitoVerhoeff(FarmaConstants.N_Autorizacion,2);
       //suma 
      
       suma=FarmaVariables.N_Factura+FarmaVariables.N_Nit+FarmaVariables.F_Fecha_Tr+FarmaVariables.N_Monto_tr;
       suma2Ver=setDigitoVerhoeff(suma,5);                
       
       long digitos=suma2Ver%100000;
       
       String strDigitos=String.valueOf(digitos);
       strDigitos=completarConCeros(strDigitos,5);
       
       ArrayList array=new ArrayList();
       
       int posini=0;
       int lon=0;
       for(int i=0;i<strDigitos.length();i++)
       {
               lon=Integer.parseInt(String.valueOf(getPos(strDigitos,i)))+1;
               array.add(FarmaConstants.S_Llave_Dosif.substring(posini,posini+lon));
               posini=posini+lon;
       }           
       long cteNit=FarmaConstants.N_Nit;
       
       String CadConcatenada=FarmaConstants.N_Autorizacion+array.get(0).toString();
       CadConcatenada+=FarmaVariables.N_Factura+array.get(1).toString();
       CadConcatenada+=((cteNit!=0)?FarmaVariables.N_Nit+"":"0"+FarmaVariables.N_Nit)+array.get(2).toString();
       CadConcatenada+=FarmaVariables.F_Fecha_Tr+array.get(3).toString();
       CadConcatenada+=FarmaVariables.N_Monto_tr+array.get(4).toString();
       CadConcatenada=CadConcatenada.trim();   
       
       String KeyForCifrada=FarmaConstants.S_Llave_Dosif+strDigitos;
       //System.out.println(KeyForCifrada);
       String cadResu=AlgoritmoRC4(CadConcatenada,KeyForCifrada).toUpperCase();
       //cadResu=cadResu.replace("-","");
        cadResu=cadResu.replaceAll("-","");
        
        
        
        
        
        
       //cadResu=cadResu.replace(" ","");
       //System.out.println(cadResu.toString().trim());
       
       long ST=0L;
       long SP1=0L;
       long SP2=0L;
       long SP3=0L;
       long SP4=0L;
       long SP5=0L;
       
       int k=0;
       for(k=0;k<cadResu.length();k++)
          { 
               ST=ST+getAscii(getPos(cadResu,k));              
           }
       
       for(k=0;k<cadResu.length();k=k+5)
           SP1=SP1+getAscii(getPos(cadResu,k));

       
       for(k=1;k<cadResu.length();k=k+5)
           SP2=SP2+getAscii(getPos(cadResu,k));
    
       for(k=2;k<cadResu.length();k=k+5)
           SP3=SP3+getAscii(getPos(cadResu,k));
       
       for(k=3;k<cadResu.length();k=k+5)
           SP4=SP4+getAscii(getPos(cadResu,k));
       
       for(k=4;k<cadResu.length();k=k+5)
           SP5=SP5+getAscii(getPos(cadResu,k));
       
       long []arreglo = new long[5];
        arreglo[0]=SP1;
        arreglo[1]=SP2;
        arreglo[2]=SP3;
        arreglo[3]=SP4;
        arreglo[4]=SP5;
        
           
       long auxTotal=0L;

       for(int i=0;i<strDigitos.length();i++)
       {
               lon=Integer.parseInt(String.valueOf(getPos(strDigitos,i)))+1;
               auxTotal=auxTotal+(long)(ST*arreglo[i]/lon);                   
       }
       
      String codB64=ObtenerBase64(auxTotal);
      System.out.println("NRO VERHOEFF: "+strDigitos);
      System.out.println("SUMATORIA TOTAL: "+auxTotal);
      System.out.println("BASE 64: "+codB64);
      String pCodigoControl = AlgoritmoRC4(codB64,FarmaConstants.S_Llave_Dosif.concat(strDigitos)).toUpperCase();
      //System.out.println("CODIGO CONTROL: "+AlgoritmoRC4(codB64,FarmaConstants.S_Llave_Dosif.concat(strDigitos)).toUpperCase());
      System.out.println("CODIGO CONTROL:"+pCodigoControl);
       
      return pCodigoControl;
    } 
    /****************Completar con Ceros************************/
    private static String completarConCeros(String cadena,int nro)
    {
        if(cadena.length()==nro)
        {return cadena;
         }else{
         return completarConCeros("0"+cadena,nro);
         }
        
    }
    
    private static long setDigitoVerhoeff(long valor1,int n)
    {  String strVal2=String.valueOf(valor1);
        for(int j=0;j<n;j++)
          {strVal2=strVal2.concat(String.valueOf(Verhoeff(strVal2)));
          }
        valor1=Long.parseLong(strVal2);        
            return valor1;
    }
    
}
