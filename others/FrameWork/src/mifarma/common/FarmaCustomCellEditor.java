package mifarma.common;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaCustomCellEditor.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaCustomCellEditor extends DefaultCellEditor implements TableCellEditor {

    /**
     * Componente que se usará como Editor de la Celda.
     */
    final JTextField mytext = new JTextField();

    /**
     * Clase que implementa el manejador del Foco.
     * @author Luis Mesia Rivera
     * @version 1.0
     *
     */
    public class FocusEventHandler implements java.awt.event.FocusListener {

        /**
         * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
         */
        public void focusGained(java.awt.event.FocusEvent e) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            mytext.selectAll();
                        }
                    });
        }

        /**
         * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
         */
        public void focusLost(java.awt.event.FocusEvent e) {
        }

    }

    /**
     * Constructor
     */
    FarmaCustomCellEditor() {
        super(new JTextField());
        this.editorComponent = mytext;
        this.clickCountToStart = 0;
        mytext.addFocusListener(new FocusEventHandler());
    }

    /**
     * Obtiene el valor del CellEditor.
     * @return Valor.
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        return new String(mytext.getText());
    }

    /**
     * Obtiene el Componente Editor.
     * @param table Tabla
     * @param value Objeto
     * @param isSelected
     * @param row Fila
     * @param column Columna
     * @return Componente.
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value, 
                                                 boolean isSelected, int row, 
                                                 int column) {

        mytext.setText(value + "");
        mytext.selectAll();
        editorComponent.requestFocus();
        mytext.requestFocus();
        return editorComponent;

    }

}
