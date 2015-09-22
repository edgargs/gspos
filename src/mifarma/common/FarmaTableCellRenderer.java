package mifarma.common;

import java.awt.*;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaTableCellRenderer.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */


public class FarmaTableCellRenderer extends JLabel implements TableCellRenderer {

    protected Border noFocusBorder;

    private ArrayList rowsWithOtherColor;

    private Color backgroundColor;

    private Color foregroundColor;

    private boolean bold = false;

    int rowWithColor = 0;

    /**Establece los colores de un conjunto de celdas en una tabla
     * @param pRowsWithOtherColor
     * 			Conjunto de flas con color distinto
     * @param pBackgroundColor
     * 			Color de fondo
     * @param pForegroundColor
     * 			Color de fondo
     * @param pBold
     * 			Indicador de función Negrita
     */
    public FarmaTableCellRenderer(ArrayList pRowsWithOtherColor, 
                                  Color pBackgroundColor, 
                                  Color pForegroundColor, boolean pBold) {
        rowsWithOtherColor = pRowsWithOtherColor;
        if (pBackgroundColor == null)
            backgroundColor = Color.white;
        else
            backgroundColor = pBackgroundColor;
        if (pForegroundColor == null)
            foregroundColor = Color.black;
        else
            foregroundColor = pForegroundColor;
        bold = pBold;
        noFocusBorder = new EmptyBorder(1, 2, 1, 2);
        setOpaque(true);
        setBorder(noFocusBorder);
    }


    /**Obtiene el editor de celdas de una tabla especifica
     * @param table
     * 			Tabla a operar
     * @param value
     * 			Valor
     * @param isSelected
     * 			Indica si esta seeccionado
     * @param hasFocus
     * 			Indica si tiene el foco
     * @param row
     * 			Indica el indice de fila
     * @param column
     * 			Indica el indice de columna
     * @return Component
     * 			Retorna el componente editor de la tabla
     * 			
     */
    public Component getTableCellRendererComponent(JTable table, Object value, 
                                                   boolean isSelected, 
                                                   boolean hasFocus, int row, 
                                                   int column) {
        if (isSelected) {
            setBackground(new Color(10, 36, 106));
            setForeground(Color.white);
            setFont(new Font("SansSerif", 0, 11));
        } else {
            if (rowsWithOtherColor.size() > 0) {
                if (column == 1) {
                    for (int i = 0; i < rowsWithOtherColor.size(); i++) {
                        if (row == 
                            Integer.parseInt((String)rowsWithOtherColor.get(i))) {
                            rowWithColor = row;
                            break;
                        }
                    }
                }
                if (row == rowWithColor) {
                    setBackground(backgroundColor);
                    setForeground(foregroundColor);
                    if (bold)
                        setFont(new Font("SansSerif", 1, 11));
                    else
                        setFont(new Font("SansSerif", 0, 11));
                } else {
                    setBackground(Color.white);
                    setForeground(Color.black);
                    setFont(new Font("SansSerif", 0, 11));
                }
                // for (int i=0; i<rowsWithOtherColor.size(); i++) {
                // if ( row==Integer.parseInt((String)rowsWithOtherColor.get(i))
                // ) {
                // setBackground(backgroundColor);
                // setForeground(foregroundColor);
                // if ( bold ) setFont(new Font("sansserif",1,11));
                // else setFont(new Font("sansserif",0,11));
                // break;
                // } else {
                // setBackground(Color.white);
                // setForeground(Color.black);
                // setFont(new Font("sansserif",0,11));
                // }
                // }
            } else {
                setBackground(Color.white);
                setForeground(Color.black);
                setFont(new Font("SansSerif", 0, 11));
            }
        }
        this.setBorder(noFocusBorder);
        this.setText((String)value);
        return this;
    }

}
