package mifarma.common;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaCheckCellRenderer.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class FarmaCheckCellRenderer extends JCheckBox implements TableCellRenderer {

    /** Objeto que almacena el borde necesario para el JCheckBox */
    protected static Border m_noFocusBorder;

    /**
     *Constructor : Inicializa el objeto JCheckBox con el borde adecuado
     */
    public FarmaCheckCellRenderer() {
        super();
        m_noFocusBorder = new EmptyBorder(1, 2, 1, 2);
        setOpaque(true);
        setBorder(m_noFocusBorder);
    }

    /**
     *Constructor : Inicializa la estructura de tabla, incluyendo número de
     *columnas y cabecera de columnas.  También inicializa la data de la tabla
     *con valores por defecto.
     *@param table Objeto JTable que contiene al Objeto JCheckBox.
     *@param value Valor (Boolean) que tiene actualmente el Objeto JCcheckBox.
     *@param isSelected Variable boolean que determina si el row se encuentra seleccionado.
     *@param hasFocus Variable boolean que determina si el foco está en este objeto.
     *@param row Variable int que almacena el número de fila del JTable.
     *@param column Variable int que almacena el número de columna del JTable.
     *@return Component Retorna el objeto JCheckBox con check o sin check.
     */
    public Component getTableCellRendererComponent(JTable table, Object value, 
                                                   boolean isSelected, 
                                                   boolean hasFocus, int row, 
                                                   int column) {
        if (value instanceof Boolean) {
            Boolean b = (Boolean)value;
            setSelected(b.booleanValue());
        }
        setBackground(isSelected && !hasFocus ? 
                      table.getSelectionBackground() : table.getBackground());
        setForeground(isSelected && !hasFocus ? 
                      table.getSelectionForeground() : table.getForeground());
        setFont(table.getFont());
        setBorder(hasFocus ? 
                  UIManager.getBorder("Table.focusCellHighlightBorder") : 
                  m_noFocusBorder);
        return this;
    }

}
