package mifarma.common;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaGridUtils.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaGridUtils {

    /**
     * Constructor
     */
    public FarmaGridUtils() {
    }

    /**
     *Muestra una fila del Objeto JTable como seleccionado (background diferente color)
     *@param table Objeto JTable donde será seleccionada la fila.
     *@param row Variable que almacena el valor de la fila.
     *@param column Variable que almacena el valor de la columna.
     */
    public static void showCell(JTable table, int row, int column) {
        if (row >= table.getRowCount())
            row = table.getRowCount() - 1;
        Rectangle rect = table.getCellRect(row, column, true);
        table.scrollRectToVisible(rect);
        table.clearSelection();
        table.setRowSelectionInterval(row, row);
    }

    /**
     *Búsqueda de texto en un campo del JTable
     *@param e Objeto KeyEvent.
     *@param tableModel Modelo para el JTable.
     *@param dialog Ventana tipo Dialog.
     *@param table Objeto JTable.
     *@param txtFindText Objeto JTextField.
     *@param cmbFindOption Objeto JComboBox.
     *@param dataSelected Arreglo.
     */
    public static void findText_keyReleased(KeyEvent e, 
                                            FarmaTableModel tableModel, 
                                            JDialog dialog, JTable table, 
                                            JTextField txtFindText, 
                                            JComboBox cmbFindOption, 
                                            ArrayList dataSelected) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            dialog.setVisible(false);
            dialog.dispose();
            assignRowSelected(tableModel, dialog, table, dataSelected);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (table.getRowCount() - 1 > table.getSelectedRow())
                showCell(table, table.getSelectedRow() + 1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (table.getSelectedRow() > 0)
                showCell(table, table.getSelectedRow() - 1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            int vNum = 
                (int)java.lang.Math.round(table.getVisibleRect().getHeight() / 
                                          (table.getRowHeight() + 
                                           table.getRowMargin()));
            if (table.getRowCount() > table.getSelectedRow())
                showCell(table, table.getSelectedRow() + vNum, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            int vNum = 
                (int)java.lang.Math.round(table.getVisibleRect().getHeight() / 
                                          (table.getRowHeight() + 
                                           table.getRowMargin()));
            if (table.getSelectedRow() > 0)
                showCell(table, table.getSelectedRow() - vNum, 0);
        } else {
            String vFindText = txtFindText.getText().toUpperCase();
            for (int k = 0; k < table.getRowCount(); k++) {
                String vProduct = 
                    ((String)table.getValueAt(k, cmbFindOption.getSelectedIndex())).toUpperCase();
                if (vFindText.compareTo(vProduct) <= 0) {
                    showCell(table, k, 0);
                    break;
                }
            }
        }
    }

    /**
     * Permite controlar el evento click del mouse en un JTable
     * @param e
     * @param tableModel
     * @param dialog
     * @param table
     * @param dataSelected
     */
    public static void table_mouseClicked(MouseEvent e, 
                                          FarmaTableModel tableModel, 
                                          JDialog dialog, JTable table, 
                                          ArrayList dataSelected) {
        // get most recently selected row index
        int row = table.getSelectedRow();
        // get most recently selected column index
        int column = table.getSelectedColumn();
        if (row == -1 || column == -1)
            return; // can't determine selected cell
        else {
            if (e.getClickCount() == 1) {
                assignRowSelected(tableModel, dialog, table, dataSelected);
            } else if (e.getClickCount() == 2) {
                dialog.setVisible(false);
                dialog.dispose();
                assignRowSelected(tableModel, dialog, table, dataSelected);
            }
        }
    }

    /**
     * Permite controlar la tecla presionada en un JTable
     * @param e
     * @param tableModel
     * @param dialog
     * @param table
     * @param dataSelected
     */
    public static void table_keyPressed(KeyEvent e, FarmaTableModel tableModel, 
                                        JDialog dialog, JTable table, 
                                        ArrayList dataSelected) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            dialog.setVisible(false);
            dialog.dispose();
            assignRowSelected(tableModel, dialog, table, dataSelected);
        }
    }

    /**
     * Adiciona los datos seleccionados del JTable en el ArrayList
     * @param tableModel
     * @param dialog
     * @param table
     * @param dataSelected
     */
    static void assignRowSelected(FarmaTableModel tableModel, JDialog dialog, 
                                  JTable table, ArrayList dataSelected) {
        dataSelected.add(tableModel.getRow(table.getSelectedRow()));
    }

    /**
     *Permite controlar el avance o retroceso de la barra que ilumina la selección
     *de un registro.
     *@param e Objeto KeyEvent que almacena características de la tecla presionada.
     *@param pTabla Objeto JTable sobre el cual se realizará el desplazamiento
     *                     de la barra que ilumina la selección del registro.
     *@param pTextoDeBusqueda Objeto JTextField que refleja la data del registro.
     *@param pColumna Variable int que almacena el valor de la columna cuyo valor será
     *                     asignado (mostrado) en el JTextField pTextoDeBusqueda.
     *
     *Ejemplo : Si queremos que conforme avanza o retrocede la barra de selección, el valor de
     *          determinado campo se muestre en un JTextField.
     *
     * void txtBusqueda_keyPressed(KeyEvent e) {
     *   GridUtils.aceptarTeclaPresionada(e, tblProductos, txtBusqueda, 2)
     * }
     *
     * ==> la tecla que se presione será capturada por este método (_keyPressed o _keyReleased)
     * ==> el valor que exista en la columna 2 del JTable "tblProductos" será mostrado en el
     *     JTextField "txtProducto" ... "e" controla la tecla presionada (UP, DOWN, PAGEUP, 
     *     PAGEDOWN).
     * ==> si no se desea que el valor del Objeto JTextField sea actualizado ponemos :
     *     parámetro pTextoDeBusqueda = null
     *     parámetro pColumna =0
     */
    public static void aceptarTeclaPresionada(KeyEvent e, JTable pTabla, 
                                              JTextField pTextoDeBusqueda, 
                                              int pColumna) {
        if (pTabla.getRowCount() <= 0)
            return;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (pTabla.getRowCount() - 1 > pTabla.getSelectedRow()) {
                showCell(pTabla, pTabla.getSelectedRow() + 1, 0);
                if (pTextoDeBusqueda != null)
                    FarmaUtility.setearTextoDeBusqueda(pTabla, 
                                                       pTextoDeBusqueda, 
                                                       pColumna);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (pTabla.getSelectedRow() > 0) {
                showCell(pTabla, pTabla.getSelectedRow() - 1, 0);
                if (pTextoDeBusqueda != null)
                    FarmaUtility.setearTextoDeBusqueda(pTabla, 
                                                       pTextoDeBusqueda, 
                                                       pColumna);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            int vNum = 
                (int)java.lang.Math.round(pTabla.getVisibleRect().getHeight() / 
                                          (pTabla.getRowHeight() + 
                                           pTabla.getRowMargin()));
            if ((pTabla.getRowCount() > pTabla.getSelectedRow()) && 
                ((pTabla.getSelectedRow() + vNum) <= pTabla.getRowCount()))
                showCell(pTabla, pTabla.getSelectedRow() + vNum, 0);
            else
                showCell(pTabla, pTabla.getRowCount() - 1, 0);
            if (pTextoDeBusqueda != null)
                FarmaUtility.setearTextoDeBusqueda(pTabla, pTextoDeBusqueda, 
                                                   pColumna);
        } else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            int vNum = 
                (int)java.lang.Math.round(pTabla.getVisibleRect().getHeight() / 
                                          (pTabla.getRowHeight() + 
                                           pTabla.getRowMargin()));
            if ((pTabla.getSelectedRow() > 0) && 
                ((pTabla.getSelectedRow() - vNum) >= 0))
                showCell(pTabla, pTabla.getSelectedRow() - vNum, 0);
            else
                showCell(pTabla, 0, 0);
            if (pTextoDeBusqueda != null)
                FarmaUtility.setearTextoDeBusqueda(pTabla, pTextoDeBusqueda, 
                                                   pColumna);
        }
    }

    /**
     * Determina si el texto buscado se encuentra en la tabla o no.
     * @param e Evento de Teclado
     * @param pTabla Tabla
     * @param pTextoDeBusqueda Texto de Búsqueda
     * @param pColumna Columna
     * @return True o False.
     */
    public static boolean buscarDescripcion(KeyEvent e, JTable pTabla, 
                                            JTextField pTextoDeBusqueda, 
                                            int pColumna) {
        boolean findRecord = false;
        if ((e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) && 
            ((e.getKeyCode() != KeyEvent.VK_ENTER) && 
             (e.getKeyCode() != KeyEvent.VK_ESCAPE))) {
            String vFindText = pTextoDeBusqueda.getText().toUpperCase();
            String vDescrip = "";
            for (int k = 0; k < pTabla.getRowCount(); k++) {
                vDescrip = 
                        ((String)pTabla.getValueAt(k, pColumna)).toUpperCase().trim();
                if (vDescrip.length() >= vFindText.length()) {
                    vDescrip = vDescrip.substring(0, vFindText.length());
                    if (vFindText.equalsIgnoreCase(vDescrip)) {
                        showCell(pTabla, k, 0);
                        findRecord = true;
                        break;
                    }
                }
            }
        }
        return findRecord;
    }

    /**
     * Selecciona la Fila indicada.
     * @param pJTable Tabla
     * @param pRow Fila
     */
    public static void moveRowSelection(JTable pJTable, int pRow) {
        Rectangle rect = pJTable.getCellRect(pRow, 0, true);
        pJTable.scrollRectToVisible(rect);
        pJTable.clearSelection();
        pJTable.setRowSelectionInterval(pRow, pRow);
    }

    /**
     * Determina si el codigo buscado se encuentra en la tabla o no.
     * @param e Evento KeyPressed de Teclado
     * @param pTabla Tabla
     * @param pCodigoDeBusqueda Codigo de Búsqueda
     * @param pColumna Columna
     * @return True o False.
     */
    public static boolean buscarCodigo_KeyPressed(KeyEvent e, JDialog pDialog, 
                                                  JTable pTabla, 
                                                  JTextField pCodigoDeBusqueda, 
                                                  int pColumnaCodigo) {
        boolean findRecord = true;
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            findRecord = false;
            String vFindCodigo = pCodigoDeBusqueda.getText().trim();
            String vCodigo = "";
            for (int k = 0; k < pTabla.getRowCount(); k++) {
                vCodigo = 
                        ((String)pTabla.getValueAt(k, pColumnaCodigo)).trim();
                if (vFindCodigo.equalsIgnoreCase(vCodigo)) {
                    showCell(pTabla, k, 0);
                    findRecord = true;
                    break;
                }
            }
            if (!findRecord)
                FarmaUtility.showMessage(pDialog, "Codigo No Encontrado", 
                                         pCodigoDeBusqueda);
        }
        return findRecord;
    }

}
