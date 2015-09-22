package mifarma.common;

import javax.swing.table.*;

import java.util.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicaci�n : FarmaRowEditorModel.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA 07.01.2006 Creaci�n<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */

public class FarmaRowEditorModel {

    private Hashtable data;

    public FarmaRowEditorModel() {
        data = new Hashtable();
    }

    /**A�ade un editor a una fila especificada
     * @param row
     * 			Fila a operar
     * @param e
     * 			Evento realizado
     */
    public void addEditorForRow(int row, TableCellEditor e) {
        data.put(new Integer(row), e);
    }

    /**Elimina el editor de una fila especifica
     * @param row
     * 			Fila a operar
     */
    public void removeEditorForRow(int row) {
        data.remove(new Integer(row));
    }

    /**Obtiene el editor de una fila espec�fica
     * @param row
     * 		Fila a operar
     * @return TableCellEditor
     * 			Editor recuperado
     * 
     */
    public TableCellEditor getEditor(int row) {
        return (TableCellEditor)data.get(new Integer(row));
    }

}
