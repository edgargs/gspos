package mifarma.common;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.awt.event.KeyEvent;

import java.util.ArrayList;

import java.sql.SQLException;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaMaintenance.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */

public class FarmaMaintenance {

    /** Almacena la ventana PADRE */
    JDialog jDialog;

    /** Almacena el objeto JTable donde se muestra la relación de registros */
    JTable jTable;

    /** Almacena el objeto JTextField usado para la búsqueda */
    JTextField jTextField;

    /** Almacena el tipo de ventana (PADRE O HIJO) */
    String windowType = "";

    /** Almacena el objeto FarmaTableModel que define la estructura del JTable */
    FarmaTableModel tableModelForJTable;

    /** Almacena la ventana HIJO */
    JDialog childWindow;

    /** Almacena los parametros para el Stored Procedure */
    ArrayList parametersForStoredProcedure;

    /** Almacena el nombre del Stored Procedure */
    String storedProcedure = "";

    /**
     * Constructor
     * 
     * @param pJDialog
     *             Ventana PADRE que instancia esta clase.
     * @param pJTable
     *             Objeto JTable que muestra la relación de registros.
     * @param pJTextField
     *             Objeto JTextField usado para búsqueda.
     * @param pWindowType
     *             Almacen el tipo de ventana (PADRE).
     */
    public FarmaMaintenance(JDialog pJDialog, JTable pJTable, 
                            JTextField pJTextField, String pWindowType) {
        jDialog = pJDialog;
        jTable = pJTable;
        jTextField = pJTextField;
        windowType = pWindowType;
    }

    /**
     * Constructor
     * 
     * @param pJDialog
     *             Ventana HIJA que instancia esta clase.
     * @param pWindowType
     *             Almacen el tipo de ventana (HIJA).
     */
    public FarmaMaintenance(JDialog pJDialog, String pWindowType) {
        jDialog = pJDialog;
        windowType = pWindowType;
    }

    /***************************************************************************
	 * METODOS PARA CARGAR DATA Se carga la información basándose en las
	 * columnas definidas. Puede hacer uso del envio de parámetros usando un
	 * objeto ArrayList.
	 * *************************************************************************
	 */

    /**
     * Permite poblar el objeto JTable con información retornada por el Stored
     * Procedure (puede usarse un arreglo para parámetros).
     * 
     * @param pColumnData
     *             Almacena la estructura de las columnas del JTable.
     * @param pDefaultValues
     *             Almacen los valores por defecto para el JTable.
     * @param pStoredProcedure
     *             Nombre del Stored Procedure.
     * @param pParameters
     *             Objeto ArrayList que almacena los Parámetros.
     */
    public void loadDataInTable(FarmaColumnData[] pColumnData, 
                                Object[] pDefaultValues, 
                                String pStoredProcedure, 
                                ArrayList pParameters) throws SQLException {
        if ((jTable != null)) {
            parametersForStoredProcedure = pParameters;
            storedProcedure = pStoredProcedure;
            tableModelForJTable = 
                    new FarmaTableModel(pColumnData, pDefaultValues, 0);
            FarmaUtility.initSimpleList(jTable, tableModelForJTable, 
                                        pColumnData);
            refreshDataInTable();
        }
    }

    /**
     * Permite actualizar la información del Objeto JTable de la Ventana PADRE y
     * a la vez se ubica en el primer registro de la relación.
     */
    void refreshDataInTable() throws SQLException {
        FarmaSearch.loadDataForMaintenance(tableModelForJTable, 
                                           storedProcedure, 
                                           parametersForStoredProcedure);
        if (jTable.getRowCount() > 0)
            FarmaUtility.setearPrimerRegistro(jTable, jTextField, 1);
    }

    /**
     * Permite la eliminación física en la Base de Datos de un registro. Realiza
     * la actualización del Objeto JTable con los cambios efectuados.
     */
    private void deleteRecord() {
        int row = jTable.getSelectedRow();
        if (row >= 0) {
            int rptaDialogo = 
                JOptionPane.showConfirmDialog(jDialog, "Seguro de eliminar el Registro ?", 
                                              "Mensaje del Sistema", 
                                              JOptionPane.YES_NO_OPTION, 
                                              JOptionPane.QUESTION_MESSAGE);
            if (rptaDialogo == JOptionPane.YES_OPTION) {
                try {
                    FarmaSearch.executeSentenceForMaintenance(FarmaVariables.vSQL);
                    // tableModelForJTable.deleteRow(row);
                    // jTable.repaint();
                    // if ( jTable.getRowCount()>=0 )
                    // FarmaUtility.setearPrimerRegistro(jTable,jTextField,1);
                } catch (SQLException errSQLDelete) {
                    JOptionPane.showMessageDialog(childWindow, 
                                                  "Error en ejecución del Comando de Actualizacion !!! - " + 
                                                  errSQLDelete.getErrorCode(), 
                                                  "Mensaje del Sistema", 
                                                  JOptionPane.WARNING_MESSAGE);
                    errSQLDelete.printStackTrace();
                }
            }
        }
    }

    /***************************************************************************
	 * METODOS PARA CONTROLAR TECLAS Se identifican las teclas asignadas para
	 * cada tipo de ventana, es decir, Ventana Padre o Ventana Hijo
	 * *************************************************************************
	 */


    /**Permite controlar las teclas presionadas por el usuario (evento
     * Key_Pressed). Dependiendo de la ventana que haya instanciado esta clase
     * se controlarán las teclas.
     * 
     * @param e
     *		Evento generado 
     *
     */
    public void chkKeyPressed(KeyEvent e) {
        if (windowType.equalsIgnoreCase(FarmaConstants.MANTENIMIENTO_VENTANA_PADRE))
            keyPressedForParent(e);
        else if (windowType.equalsIgnoreCase(FarmaConstants.MANTENIMIENTO_VENTANA_HIJO))
            keyPressedForChild(e);
    }

    /**
	
	 */


    /**Permite controlar las teclas presionadas por el usuario (evento
     * Key_Pressed) en la ventana PADRE.
     * 
     * @param e
     * 		Evento generado
     */
    private void keyPressedForParent(KeyEvent e) {
        if ((jTable != null) && (jTextField != null))
            FarmaGridUtils.aceptarTeclaPresionada(e, jTable, jTextField, 1);
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            FarmaVariables.vMant = FarmaConstants.MANTENIMIENTO_CREAR;
            openChildWindow();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            FarmaVariables.vMant = FarmaConstants.MANTENIMIENTO_MODIFICAR;
            openChildWindow();
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (jTable.getSelectedRow() >= 0)
                deleteRecord();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            closeWindow();
    }


    /**Permite controlar las teclas presionadas por el usuario (evento
     * Key_Pressed) en la ventana HIJO.
     * @param e
     * 		Evento realizado
     */
    private void keyPressedForChild(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_F10)) {
            FarmaVariables.vAceptar = true;
            closeWindow();
        } else if ((e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
            FarmaVariables.vAceptar = false;
            closeWindow();
        }
    }


    /**Permite controlar las teclas presionadas por el usuario (evento
     * Key_Released). Dependiendo de la ventana que haya instanciado esta clase
     * se controlarán las teclas.
     * @param e
     * 		Evento realizado
     */
    public void chkKeyReleased(KeyEvent e) {
        if (windowType.equalsIgnoreCase(FarmaConstants.MANTENIMIENTO_VENTANA_PADRE)) {
            if ((jTable != null) && (jTextField != null))
                FarmaGridUtils.buscarDescripcion(e, jTable, jTextField, 1);
        } else if (windowType.equalsIgnoreCase(FarmaConstants.MANTENIMIENTO_VENTANA_HIJO))
            keyReleasedForChild(e);
    }

    /**
     * Permite controlar las teclas presionadas por el usuario (evento
     * Key_Released) en la ventana PADRE.
     *  @param e
     * 		Evento realizado
     */
    private void keyReleasedForParent(KeyEvent e) {
    }

    /**
     * Permite controlar las teclas presionadas por el usuario (evento
     * Key_Released) en la ventana HIJO.
     */
    private void keyReleasedForChild(KeyEvent e) {
    }

    /***************************************************************************
	 * METODOS PARA CONTROLAR LAS VENTANAS (PADRE E HIJO)
	 * *************************************************************************
	 */


    /**Permite setear la ventana HIJO como Objeto JDialog.
     * @param pChildWindow
     * 			Ventana Hija
     */
    public void setChildWindow(Object pChildWindow) {
        childWindow = (JDialog)pChildWindow;
    }

    /**
     * Permite abrir la ventana HIJO. Carga el registro actualmente seleccionado
     * en un ArrayList para luego poder accesar a esos datos desde la ventana
     * abierta.
     */
    private void openChildWindow() {
        if (childWindow != null) {
            if (jTable.getRowCount() > 0) {
                FarmaVariables.vDataMant = new ArrayList();
                for (int col = 0; col < jTable.getColumnCount(); col++)
                    FarmaVariables.vDataMant.add(jTable.getValueAt(jTable.getSelectedRow(), 
                                                                   col));
            }
            childWindow.setVisible(true);
            if (FarmaVariables.vAceptar) {
                try {
                    if (FarmaVariables.vMant.equalsIgnoreCase(FarmaConstants.MANTENIMIENTO_CREAR) || 
                        FarmaVariables.vMant.equalsIgnoreCase(FarmaConstants.MANTENIMIENTO_MODIFICAR)) {
                        FarmaSearch.executeSentenceForMaintenance(FarmaVariables.vSQL);
                        refreshDataInTable();
                    }
                } catch (SQLException errSQLSentence) {
                    JOptionPane.showMessageDialog(childWindow, 
                                                  "Error en ejecución del Comando de Actualizacion !!! - " + 
                                                  errSQLSentence.getErrorCode(), 
                                                  "Mensaje del Sistema", 
                                                  JOptionPane.WARNING_MESSAGE);
                    errSQLSentence.printStackTrace();
                }
            }
        }
    }

    /**
     * Cierra la ventana actualmente activa.
     */
    private void closeWindow() {
        jDialog.setVisible(false);
        jDialog.dispose();
    }

}
