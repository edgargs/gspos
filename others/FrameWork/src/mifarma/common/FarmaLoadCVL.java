package mifarma.common;

import javax.swing.*;

import java.util.*;

import java.sql.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaLoadCVL.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaLoadCVL {

    /** Almacena todos los objetos y datos asociados a un ComboBox */
    private static Hashtable tableList = new Hashtable();

    /**
     * Constructor
     */
    public FarmaLoadCVL() {
    }

    /**
     * Carga el combo según los parámetros señalados.
     * @param combo Objeto JComboBox
     * @param tableName Nombre de la Tabla
     * @param fieldCode Código
     * @param fieldValue Valor
     * @param isMandatory
     * @param whereCondition
     */
    public static void loadCVL(JComboBox combo, String tableName, 
                               String fieldCode, String fieldValue, 
                               boolean isMandatory, String whereCondition) {
        loadCVL(combo, tableName, fieldCode, fieldValue, isMandatory, 
                whereCondition, null, null);
    }

    /**
     * Carga el combo según los parámetros señalados.
     * @param combo Objeto JComboBox
     * @param tableName Nombre de la Tabla
     * @param fieldCode Código
     * @param fieldValue Valor
     * @param isMandatory
     * @param whereCondition
     * @param nameInHashTable Nombre de la Tabla
     */
    public static void loadCVL(JComboBox combo, String tableName, 
                               String fieldCode, String fieldValue, 
                               boolean isMandatory, String whereCondition, 
                               String nameInHashTable) {
        loadCVL(combo, tableName, fieldCode, fieldValue, isMandatory, 
                whereCondition, null, nameInHashTable);
    }

    /*
	 public static void loadCVL(JComboBox combo,
	 String tableName,
	 String fieldCode,
	 String fieldValue,
	 boolean isMandatory,
	 String whereCondition,
	 String orderByFields,
	 String nameInHashTable) {
	 if(nameInHashTable == null) {
	 if(tableList.containsKey(tableName))  tableList.remove(tableName);
	 nameInHashTable = tableName;
	 loadNewCVL(combo,tableName,fieldCode,fieldValue,isMandatory,whereCondition, orderByFields, nameInHashTable);
	 } else {
	 if (!tableList.containsKey(nameInHashTable))
	 loadNewCVL(combo,tableName,fieldCode,fieldValue,isMandatory,whereCondition, orderByFields, nameInHashTable);
	 }
	 ArrayList list = (ArrayList)tableList.get(nameInHashTable);
	 for (int i = 0 ; i< list.size(); i++) {
	 ArrayList data = (ArrayList)list.get(i);
	 combo.addItem(data.get(1));
	 }
	 }
	 */

    /**
     * Carga el combo según los parámetros señalados.
     * @param combo Objeto JComboBox
     * @param tableName Nombre de la Tabla
     * @param fieldCode Código
     * @param fieldValue Valor
     * @param isMandatory
     * @param whereCondition
     * @param orderByFields Ordena según el valor
     * @param nameInHashTable Nombre de la Tabla
     */
    public static void loadCVL(JComboBox combo, String tableName, 
                               String fieldCode, String fieldValue, 
                               boolean isMandatory, String whereCondition, 
                               String orderByFields, String nameInHashTable) {
        loadCVL(combo, tableName, fieldCode, fieldValue, isMandatory, 
                whereCondition, orderByFields, nameInHashTable, false);
    }

    /**
     * Carga el combo según los parámetros señalados.
     * @param combo Objeto JComboBox
     * @param tableName Nombre de la Tabla
     * @param fieldCode Código
     * @param fieldValue Valor
     * @param isMandatory
     * @param whereCondition
     * @param orderByFields Ordena según el valor
     * @param nameInHashTable Nombre de la Tabla
     * @param rewriteInHashTable
     */
    public static void loadCVL(JComboBox combo, String tableName, 
                               String fieldCode, String fieldValue, 
                               boolean isMandatory, String whereCondition, 
                               String orderByFields, String nameInHashTable, 
                               boolean rewriteInHashTable) {
        if (nameInHashTable == null) {
            if (tableList.containsKey(tableName))
                tableList.remove(tableName);
            nameInHashTable = tableName;
            loadNewCVL(combo, tableName, fieldCode, fieldValue, isMandatory, 
                       whereCondition, orderByFields, nameInHashTable);
        } else {
            //if(tableList.containsKey(tableName))  tableList.remove(tableName);
            if (!tableList.containsKey(nameInHashTable) || rewriteInHashTable)
                loadNewCVL(combo, tableName, fieldCode, fieldValue, 
                           isMandatory, whereCondition, orderByFields, 
                           nameInHashTable);
        }
        ArrayList list = (ArrayList)tableList.get(nameInHashTable);
        for (int i = 0; i < list.size(); i++) {
            ArrayList data = (ArrayList)list.get(i);
            combo.addItem(data.get(1));
        }
    }

    /**
     * Carga el combo consultando la Base de Datos.
     * @param combo Objeto JComboBox
     * @param tableName Nombre de la Tabla
     * @param fieldCode Código
     * @param fieldValue Valor
     * @param isMandatory
     * @param whereCondition
     * @param orderByFields Ordena según el valor
     * @param nameInHashTable Nombre de la Tabla
     */
    public static void loadNewCVL(JComboBox combo, String tableName, 
                                  String fieldCode, String fieldValue, 
                                  boolean isMandatory, String whereCondition, 
                                  String orderByFields, 
                                  String nameInHashTable) {
        ArrayList arrayCVL = new ArrayList();
        String query = "";
        if (whereCondition == null)
            query = 
                    "SELECT " + fieldCode + ", " + fieldValue + " FROM " + tableName;
        else
            query = 
                    "SELECT " + fieldCode + ", " + fieldValue + " FROM " + tableName + 
                    " WHERE " + whereCondition;

        if (orderByFields == null) {
            query += " ORDER BY " + fieldValue;
        } else {
            query += " ORDER BY " + orderByFields;
        }

        try {
            Statement stmt = 
                ((Connection)FarmaConnection.getConnection()).createStatement();
            ResultSet results = stmt.executeQuery(query);
            ArrayList myArray;
            if (!isMandatory) {
                myArray = new ArrayList();
                myArray.add("");
                myArray.add("");
                arrayCVL.add(myArray);
            }
            while (results.next()) {
                myArray = new ArrayList();
                myArray.add(results.getString(1));
                myArray.add(results.getString(2));
                arrayCVL.add(myArray);
            }
            results.close();
            stmt.close();
            tableList.put(nameInHashTable, arrayCVL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Devuelve el código según la posición.
     * @param tableName Nombre de la Tabla
     * @param index Índice
     * @return codigo Código
     */
    public static String getCVLCode(String tableName, int index) {
        String code = new String("");
        if (tableList.containsKey(tableName)) {
            ArrayList list = (ArrayList)tableList.get(tableName);
            ArrayList data = (ArrayList)list.get(index);
            code = (String)data.get(0);
        }
        return code;
    }

    /**
     * Carga el combo desde un arreglo.
     * @param combo Objeto JComboBox
     * @param nameInHashTable Nombre de la Tabla
     * @param fieldCode Arreglo de Códigos
     * @param fieldValue Arreglo de Datos
     * @param isMandatory
     */
    public static void loadCVLfromArrays(JComboBox combo, 
                                         String nameInHashTable, 
                                         String[] fieldCode, 
                                         String[] fieldValue, 
                                         boolean isMandatory) {
        if (!tableList.containsKey(nameInHashTable)) {
            ArrayList arrayCVL = new ArrayList();
            ArrayList myArray;
            if (!isMandatory) {
                myArray = new ArrayList();
                myArray.add("");
                myArray.add("");
                arrayCVL.add(myArray);
            }
            for (int i = 0; i < fieldCode.length; i++) {
                myArray = new ArrayList();
                myArray.add(fieldCode[i]);
                myArray.add(fieldValue[i]);
                arrayCVL.add(myArray);
            }
            tableList.put(nameInHashTable, arrayCVL);
        }

        ArrayList list = (ArrayList)tableList.get(nameInHashTable);
        for (int i = 0; i < list.size(); i++) {
            ArrayList data = (ArrayList)list.get(i);
            combo.addItem(data.get(1));
        }
    }

    /**
     * Selecciona un item del combo.
     * @param combo Objeto JComboBox
     * @param nameInHashTable Nombre de la Tabla
     * @param codigoBusqueda Código de búsqueda
     */
    public static void setSelectedValueInComboBox(JComboBox combo, 
                                                  String nameInHashTable, 
                                                  String codigoBusqueda) {
        String codigo;
        for (int i = 0; i < combo.getItemCount(); i++) {
            codigo = getCVLCode(nameInHashTable, i);
            if (codigo.equalsIgnoreCase(codigoBusqueda)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    /**
     * Carga el combo según los parámetros señalados, desde un Stored Procedure.
     * @param combo Objeto JComboBox
     * @param nameInHashTable Nombre de la Tabla
     * @param pSP Stored Procedure
     * @param parametros Parametros
     * @param isMandatory 
     */
    public static void loadCVLFromSP(JComboBox combo, String nameInHashTable, 
                                     String pSP, ArrayList parametros, 
                                     boolean isMandatory) {
        loadCVLFromSP(combo, nameInHashTable, pSP, parametros, isMandatory, 
                      false);
    }

    /**
     * Carga el combo según los parámetros señalados, desde un Stored Procedure.
     * @param combo Objeto JComboBox
     * @param nameInHashTable Nombre de la Tabla
     * @param pSP Stored Procedure
     * @param parametros Parametros
     * @param isMandatory 
     * @param replaceCVL 
     */
    public static void loadCVLFromSP(JComboBox combo, String nameInHashTable, 
                                     String pSP, ArrayList parametros, 
                                     boolean isMandatory, boolean replaceCVL) {
        try {
            ArrayList myArrayList = new ArrayList();
            FarmaDBUtility.executeSQLStoredProcedureArrayList(myArrayList, pSP, 
                                                              parametros);
            if (replaceCVL) {
                if (tableList.containsKey(nameInHashTable))
                    tableList.remove(nameInHashTable);
                loadDataToCVLFromSP(myArrayList, nameInHashTable, isMandatory);
            } else if (!tableList.containsKey(nameInHashTable))
                loadDataToCVLFromSP(myArrayList, nameInHashTable, isMandatory);
            ArrayList list = (ArrayList)tableList.get(nameInHashTable);
            for (int i = 0; i < list.size(); i++) {
                ArrayList data = (ArrayList)list.get(i);
                combo.addItem(data.get(1));
            }
        } catch (SQLException errCVLFromSP) {
            errCVLFromSP.printStackTrace();
        }
    }

    /**
     * Carga el combo de datsos desde un Stored Procedure.
     * @param pArrayList Parametros
     * @param pNameInHashTable Nombre de la Tabla
     * @param pIsMandatory
     */
    public static void loadDataToCVLFromSP(ArrayList pArrayList, 
                                           String pNameInHashTable, 
                                           boolean pIsMandatory) {
        ArrayList arrayCVL = new ArrayList();
        ArrayList myArray;
        if (!pIsMandatory) {
            myArray = new ArrayList();
            myArray.add("");
            myArray.add("");
            arrayCVL.add(myArray);
        }
        for (int i = 0; i < pArrayList.size(); i++) {
            myArray = new ArrayList();
            myArray.add((String)((ArrayList)pArrayList.get(i)).get(0));
            myArray.add((String)((ArrayList)pArrayList.get(i)).get(1));
            arrayCVL.add(myArray);
        }
        tableList.put(pNameInHashTable, arrayCVL);
    }

    /**
     * Devuelve la descripción de un item, según el índice.
     * @param pTableName Nombre de la Tabla
     * @param pCode Codigo a buscar
     * @return Descripción del item.
     */
    public static String getCVLDescription(String pTableName, String pCode) {
        String description = new String("");
        if (tableList.containsKey(pTableName)) {
            ArrayList list = (ArrayList)tableList.get(pTableName);
            //ArrayList data = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                if (((String)((ArrayList)list.get(i)).get(0)).trim().equalsIgnoreCase(pCode))
                    description = 
                            ((String)((ArrayList)list.get(i)).get(1)).trim();
            }
        }
        return description;
    }

    /**
     * Carga el combo según los parámetros señalados, desde un ArrayList.
     * @param combo Objeto JComboBox
     * @param nameInHashTable Nombre de la Tabla
     * @param pArrayList Arreglo de Datos
     * @param isMandatory 
     */
    public static void loadCVLFromArrayList(JComboBox combo, 
                                            String nameInHashTable, 
                                            ArrayList dataArrayList, 
                                            boolean isMandatory) {
        if (tableList.containsKey(nameInHashTable))
            tableList.remove(nameInHashTable);
        loadDataToCVLFromSP(dataArrayList, nameInHashTable, isMandatory);
        ArrayList list = (ArrayList)tableList.get(nameInHashTable);
        for (int i = 0; i < list.size(); i++) {
            ArrayList data = (ArrayList)list.get(i);
            combo.addItem(data.get(1));
        }
    }

    /**
     * Descarga el combo dado y limpia la tabla.
     * @param combo Objeto JComboBox
     * @param nameInHashTable Nombre de la Tabla
     */
    public static void unloadCVL(JComboBox combo, String nameInHashTable) {
        if (tableList.containsKey(nameInHashTable))
            tableList.remove(nameInHashTable);
        combo.removeAllItems();
    }

}
