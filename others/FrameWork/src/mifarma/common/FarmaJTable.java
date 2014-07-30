package mifarma.common;

import javax.swing.*;
import javax.swing.table.*;

import java.util.Vector;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaJTable.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaJTable extends JTable {

    /**
     * Editor de Fila
     */
    protected FarmaRowEditorModel rowEditorModel;

    /**
     * Constructor.
     */
    public FarmaJTable() {
        super();
        rowEditorModel = null;
    }

    /**
     * Constructor que inicializa con un Modelo de Tabla.
     * @param pTableModel Modelo de Tabla
     */
    public FarmaJTable(TableModel pTableModel) {
        super(pTableModel);
        rowEditorModel = null;
    }

    /**
     * Constructor que inicializa con un Modelo de Tabla y Modelo de Columna.
     * @param pTableModel Modelo de Tabla
     * @param pTableColumnModel Modelo de Columna
     */
    public FarmaJTable(TableModel pTableModel, 
                       TableColumnModel pTableColumnModel) {
        super(pTableModel, pTableColumnModel);
        rowEditorModel = null;
    }

    /**
     * Constructor que inicializa con un Modelo de Tabla, Modelo de Columna y un Modelo de Lista de Seleccion.
     * @param pTableModel Modelo de Tabla
     * @param pTableColumnModel Modelo de Columna
     * @param pListSelectionModel Modelo de Lista de Seleccion
     */
    public FarmaJTable(TableModel pTableModel, 
                       TableColumnModel pTableColumnModel, 
                       ListSelectionModel pListSelectionModel) {
        super(pTableModel, pTableColumnModel, pListSelectionModel);
        rowEditorModel = null;
    }

    /**
     * Constructor que inicializa con un tamaño de Filas y Columnas indicadas.
     * @param pRows Filas 
     * @param pCols Columnas
     */
    public FarmaJTable(int pRows, int pCols) {
        super(pRows, pCols);
        rowEditorModel = null;
    }

    /**
     * Constructor que inicializa con Datos y Cabecera del tipo Vector.
     * @param pRowData Datos
     * @param pColumnNames Cabecera
     */
    public FarmaJTable(final Vector pRowData, final Vector pColumnNames) {
        super(pRowData, pColumnNames);
        rowEditorModel = null;
    }

    /**
     * Constructor que inicializa con Datos y Cabecera del tipo arreglo de Object.
     * @param pRowData Datos
     * @param pColumnNames Cabecera
     */
    public FarmaJTable(final Object[][] pRowData, 
                       final Object[] pColumnNames) {
        super(pRowData, pColumnNames);
        rowEditorModel = null;
    }

    /**
     * Nuevo Constructor para definir CellEditor de cada Row.
     * @param pTableModel Modelo de Tabla
     * @param pRowEditorModel Editor de Fila
     */
    public FarmaJTable(TableModel pTableModel, 
                       FarmaRowEditorModel pRowEditorModel) {
        super(pTableModel, null, null);
        this.rowEditorModel = pRowEditorModel;
    }

    /**
     * Establece el Editor de Fila.
     * @param pRowEditorModel Edtor de Fila
     */
    public void setRowEditorModel(FarmaRowEditorModel pRowEditorModel) {
        this.rowEditorModel = pRowEditorModel;
    }

    /**
     * Obtiene el Editor de Fila.
     * @return Editor de Fila.
     */
    public FarmaRowEditorModel getRowEditorModel() {
        return rowEditorModel;
    }

    /**
     * Obtiene el Editor de Celda según la Fila y Columna.
     * @param pRow Fila
     * @param pCol Columna
     * @return Editor de Fila.
     * @see javax.swing.JTable#getCellEditor(int, int)
     */
    public TableCellEditor getCellEditor(int pRow, int pCol) {
        TableCellEditor tmpEditor = null;
        if (rowEditorModel != null)
            tmpEditor = rowEditorModel.getEditor(pRow);
        if (tmpEditor != null)
            return tmpEditor;
        return super.getCellEditor(pRow, pCol);
    }

    /**
     * Cambia la Selección.
     * @param pRow Fila
     * @param pColumn Columna
     * @param pToggle
     * @param pExtend
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     */
    public void changeSelection(final int pRow, final int pColumn, 
                                boolean pToggle, boolean pExtend) {
        super.changeSelection(pRow, pColumn, pToggle, pExtend);
        //if ( editCellAt(pRow,pColumn) && rowEditorModel.getEditor(pRow)==null )
        //if ( editCellAt(pRow,pColumn) )
        if (editCellAt(pRow, pColumn) && 
            (super.getEditorComponent() instanceof JTextField))
            super.getEditorComponent().requestFocus();
    }

}
