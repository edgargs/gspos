package mifarma.common;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.StringTokenizer;

import oracle.jdbc.OracleTypes;


/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicaci�n : FarmaDBUtilityMatriz.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      07.01.2006   Creaci�n<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaDBUtilityMatriz {

    //private static ArrayList parametros = new ArrayList();

    /**
     *Retorna la colecci�n de datos del Query ejecutado.  Luego de usar el Objeto
     *retornado se deber� necesariamente invocar el M�todo closeSQL para evitar 
     *congestionar los recursos de la Base de Datos.
     *@param pSQLSelect Query ha ejecutar.
     *@return String Colecci�n de datos - resultado del Query.
     */
    public static ResultSet startSQLSelect(String pSQLSelect) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pSQLSelect == null || pSQLSelect.trim().length() == 0)
            throw new SQLException("Expresion del Query no Definido", 
                                   "FarmaError", 9000);
        Statement stmt = 
            ((Connection)FarmaConnectionMatriz.getConnection()).createStatement();
        // Retornamos el ResultSet resultado de ejecutar el Query.
        return stmt.executeQuery(pSQLSelect);
    }

    /**
     *Cierra el Statement y el ResultSet usados para la ejecuci�n de un Query.
     *@param pResultSet Colecci�n de datos a cerrar.  Por medio de este
     *                         objeto obtenemos el Statement usado el cual tambi�n
     *                         es cerrado.
     */
    public static void closeSQL(ResultSet pResultSet) throws SQLException {
        Statement stmt = pResultSet.getStatement();
        pResultSet.close();
        stmt.close();
    }

    /**
     * Ejecuta una sentencia SQL.
     * @param pSQLUpdate Sentencia SQL
     * @throws SQLException
     */
    public static void executeSQLUpdate(String pSQLUpdate) throws SQLException {
        executeSQLUpdate(pSQLUpdate, true);
    }

    /**
     *Ejecuta un SQL encardado de realizar Update a la Base de Datos.
     *@param pSQLUpdate Contiene la sentencia Update a ser ejecutada.
     *@param pWithCommit
     */
    public static void executeSQLUpdate(String pSQLUpdate, 
                                        boolean pWithCommit) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pSQLUpdate == null || pSQLUpdate.trim().length() == 0)
            throw new SQLException("Expresion del Update no Definido", 
                                   "FarmaError", 9001);
        // Controla el COMMIT autom�tico
        ((Connection)FarmaConnectionMatriz.getConnection()).setAutoCommit(pWithCommit);
        Statement stmt = 
            ((Connection)FarmaConnectionMatriz.getConnection()).createStatement();
        int registros = stmt.executeUpdate(pSQLUpdate);
        ((Connection)FarmaConnectionMatriz.getConnection()).setAutoCommit(false);
        //
        stmt.close();
    }

    /**
     *Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     *M�todo usado en los siguientes casos :
     *   1. Ejecutar Stored Procedures con o sin par�metros y estos retornan un objeto
     *      del tipo OracleTypes.CURSOR
     *   2. Ejecutar Stored Procedures con o sin par�metros que no retornan nada, s�lo
     *      son ejecutados en el Servidor de Base de Datos
     *Se asume que cuando el par�metro pTableModel es nulo se trata de un procedimiento
     *que no retornar� nada.
     *@param pTableModel Table Model usado con el JTable como modelo.
     *@param pStoredProcedure Nombre del Stored Procedure ha ser ejecutado
     *@param pParameters Arreglo de Objetos (par�metros) usados en el Stored
     *                          Procedure.
     *@param pWithCheck Variable boolean que indica si en el JTable existir� una
     *                         columna de Selecci�n.
     */
    public static void executeSQLStoredProcedure(FarmaTableModel pTableModel, 
                                                 String pStoredProcedure, 
                                                 ArrayList pParameters, 
                                                 boolean pWithCheck) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int numeroParametro = 2;
        CallableStatement stmt;
        if (pTableModel != null) {
            stmt = 
((Connection)FarmaConnectionMatriz.getConnection()).prepareCall("{ call ? := " + 
                                                                pStoredProcedure + 
                                                                " }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
        } else {
            numeroParametro = 1;
            stmt = 
((Connection)FarmaConnectionMatriz.getConnection()).prepareCall("{ call " + 
                                                                pStoredProcedure + 
                                                                " }");
        }
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el ArrayList
        // que viene como par�metro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        if (pTableModel != null) {
            // Carga la Data en el TableModel para ser usado como modelo para el JTable.
            // El OracleTypes.CURSOR obtenido es "depurado" para obtener cada una de las
            // columnas ... cada registro viene como un String en el que los campos est�n
            // concatenados por el character "�".    
            pTableModel.clearTable();
            ResultSet results = (ResultSet)stmt.getObject(1);
            ArrayList myArray = null;
            String myRow = "";
            StringTokenizer st = null;
            pTableModel.clearTable();
            while (results.next()) {
                myRow = results.getString(1);
                myArray = new ArrayList();
                st = new StringTokenizer(myRow, "�");
                if (pWithCheck)
                    myArray.add(new Boolean(false));
                while (st.hasMoreTokens()) {
                    myArray.add(st.nextToken());
                }
                pTableModel.insertRow(myArray);
            }
            results.close();
        }
        stmt.close();
    }

    /**
     *Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     *M�todo usado en los siguientes casos :
     *   1. Ejecutar Stored Procedures con o sin par�metros y estos retornan un objeto
     *      del tipo OracleTypes.CHAR
     *@param pStoredProcedure Nombre del Stored Procedure ha ser ejecutado
     *@param pParameters Arreglo de Objetos (par�metros) usados en el Stored
     *                          Procedure.
     *@return String Objeto String devuelto por la ejecuci�n del Stored Procedure.
     */
    public static String executeSQLStoredProcedureStr(String pStoredProcedure, 
                                                      ArrayList pParameters) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        String valorRetorno = null;
        int numeroParametro = 2;
        CallableStatement stmt = 
            ((Connection)FarmaConnectionMatriz.getConnection()).prepareCall("{ call ? := " + 
                                                                            pStoredProcedure + 
                                                                            " }");
        stmt.registerOutParameter(1, OracleTypes.CHAR);
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el ArrayList
        // que viene como par�metro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        valorRetorno = (String)stmt.getObject(1);
        stmt.close();
        FarmaConnectionMatriz.closeConnection();
        return valorRetorno;
    }

    /**
     *Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     *M�todo usado en los siguientes casos :
     *   1. Ejecutar Stored Procedures con o sin par�metros y estos retornan un objeto
     *      del tipo OracleTypes.INTEGER
     *@param pStoredProcedure Nombre del Stored Procedure ha ser ejecutado
     *@param pParameters Arreglo de Objetos (par�metros) usados en el Stored
     *                          Procedure.
     *@return int integer devuelto por la ejecuci�n del Stored Procedure.
     */
    public static int executeSQLStoredProcedureInt(String pStoredProcedure, 
                                                   ArrayList pParameters) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int valorRetorno = 0;
        int numeroParametro = 2;
        CallableStatement stmt = 
            ((Connection)FarmaConnectionMatriz.getConnection()).prepareCall("{ call ? := " + 
                                                                            pStoredProcedure + 
                                                                            " }");
        //stmt.getQueryTimeout();
        //stmt.setQueryTimeout()
        stmt.registerOutParameter(1, OracleTypes.INTEGER);
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el ArrayList
        // que viene como par�metro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();

        Object obj = stmt.getObject(1);
        if (obj instanceof BigDecimal)
            valorRetorno = ((BigDecimal)stmt.getObject(1)).intValue();
        else if (obj instanceof Integer)
            valorRetorno = ((Integer)stmt.getObject(1)).intValue();
        stmt.close();
        FarmaConnectionMatriz.closeConnection();
        return valorRetorno;
    }

    /**
     *Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     *M�todo usado en los siguientes casos :
     *   1. Ejecutar Stored Procedures con o sin par�metros y estos retornan un objeto
     *      del tipo OracleTypes.DOUBLE
     *@param pStoredProcedure Nombre del Stored Procedure ha ser ejecutado
     *@param pParameters Arreglo de Objetos (par�metros) usados en el Stored
     *                          Procedure.
     *@return double double devuelto por la ejecuci�n del Stored Procedure.
     */
    public static double executeSQLStoredProcedureDouble(String pStoredProcedure, 
                                                         ArrayList pParameters) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        double valorRetorno = 0.00;
        int numeroParametro = 2;
        CallableStatement stmt = 
            ((Connection)FarmaConnectionMatriz.getConnection()).prepareCall("{ call ? := " + 
                                                                            pStoredProcedure + 
                                                                            " }");
        stmt.registerOutParameter(1, OracleTypes.DOUBLE);
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el ArrayList
        // que viene como par�metro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        Object obj = stmt.getObject(1);
        if (obj instanceof BigDecimal)
            valorRetorno = ((BigDecimal)stmt.getObject(1)).doubleValue();
        else if (obj instanceof Double)
            valorRetorno = ((Double)stmt.getObject(1)).doubleValue();
        stmt.close();
        return valorRetorno;
    }

    /**
     * Ejecuta un Stored Procedure de la Base de Datos (Matriz).
     * @param pArrayList Arreglo de Respuesta
     * @param pStoredProcedure Stored Procedure
     * @param pParameters Arreglo de Par�metros
     * @throws SQLException
     */
    public static void executeSQLStoredProcedureArrayList(ArrayList pArrayList, 
                                                          String pStoredProcedure, 
                                                          ArrayList pParameters) throws SQLException {
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int numeroParametro = 2;
        CallableStatement stmt;
        stmt = 
((Connection)FarmaConnectionMatriz.getConnection()).prepareCall("{ call ? := " + 
                                                                pStoredProcedure + 
                                                                " }");
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        ResultSet results = (ResultSet)stmt.getObject(1);
        ArrayList myArray = null;
        String myRow = "";
        StringTokenizer st = null;
        while (results.next()) {
            myRow = results.getString(1);
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "�");
            while (st.hasMoreTokens())
                myArray.add(st.nextToken());
            pArrayList.add(myArray);
        }
        results.close();
        stmt.close();
    }

    /**
     * Obtiene un valor de la Base de Datos (Matriz).
     * @param tableName Nombre de la Tabla
     * @param fieldCode Campo
     * @param whereCondition Condici�n
     * @return String - Valor de la Consulta.
     * @throws SQLException
     */
    public static String getValueAt(String tableName, String fieldCode, 
                                    String whereCondition) throws SQLException {
        String valor = "";
        String query = 
            "SELECT " + fieldCode + " FROM " + tableName + " WHERE " + 
            whereCondition + "";
        //System.out.println("QUERY="+query);
        Statement stmt = 
            ((Connection)FarmaConnectionMatriz.getConnection()).createStatement();
        ResultSet results = stmt.executeQuery(query);
        if (results.next()) {
            valor = results.getString(1);
        }
        if (results.next())
            throw new SQLException("Existe m�s de 1 registro", "FarmaError", 
                                   9000);
        results.close();
        stmt.close();
        if (valor == null)
            valor = "";
        return valor;
    }

    /**
     * Constructor
     */
    public FarmaDBUtilityMatriz() {
    }

}
