package mifarma.common;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import oracle.jdbc.driver.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaDBUtility.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 * 
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 * 
 */

public class FarmaDBUtility {

    //private static ArrayList parametros = new ArrayList();

    /**
     * Retorna la colección de datos del Query ejecutado. Luego de usar el
     * Objeto retornado se deberá necesariamente invocar el Método closeSQL para
     * evitar congestionar los recursos de la Base de Datos.
     * 
     * @param pSQLSelect Query ha ejecutar.
     * @return String Colección de datos - resultado del Query.
     */
    public static ResultSet startSQLSelect(String pSQLSelect) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pSQLSelect == null || pSQLSelect.trim().length() == 0)
            throw new SQLException("Expresion del Query no Definido", 
                                   "FarmaError", 9000);
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        // Retornamos el ResultSet resultado de ejecutar el Query.
        return stmt.executeQuery(pSQLSelect);
    }

    /**
     * Ejecuta un Select y carga un Modelo de Tabla con los datos devueltos.
     * @param pSQLSelect Sentencia de Select
     * @param pTableModel Modelo de Tabla
     * @param pWithCheck
     * @throws SQLException
     */
    public static void startSQLSelect(String pSQLSelect, 
                                      FarmaTableModel pTableModel, 
                                      boolean pWithCheck) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        java.util.Date t1 = new java.util.Date();
        if (pSQLSelect == null || pSQLSelect.trim().length() == 0)
            throw new SQLException("Expresion del Query no Definido", 
                                   "FarmaError", 9000);
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        // Retornamos el ResultSet resultado de ejecutar el Query.
        ResultSet results = stmt.executeQuery(pSQLSelect);
        java.util.Date t2 = new java.util.Date();
        System.out.println("tiempo parcial : " + 
                           (t2.getTime() - t1.getTime()));
        pTableModel.clearTable();
        // (ResultSet)stmt.getObject(1);
        ArrayList myArray = null;
        String myRow = "";
        StringTokenizer st = null;
        pTableModel.clearTable();
        while (results.next()) {
            myRow = results.getString(1);
                myArray = new ArrayList();
                st = new StringTokenizer(myRow, "Ã");
                if (pWithCheck)
                    myArray.add(new Boolean(false));
                while (st.hasMoreTokens()) {
                    myArray.add(st.nextToken());
                }
                pTableModel.insertRow(myArray);
        }
        results.close();
        stmt.close();
    }

    /**
     * Cierra el Statement y el ResultSet usados para la ejecución de un Query.
     * 
     * @param pResultSet Colección de datos a cerrar. Por medio de este objeto
     *            obtenemos el Statement usado el cual también es cerrado.
     */
    public static void closeSQL(ResultSet pResultSet) throws SQLException {
        Statement stmt = pResultSet.getStatement();
        pResultSet.close();
        stmt.close();
    }

    /**
     * Ejecuta un sentencia SQL.
     * @param pSQLUpdate Sentencia SQL
     * @throws SQLException
     */
    public static void executeSQLUpdate(String pSQLUpdate) throws SQLException {
        executeSQLUpdate(pSQLUpdate, true);
    }

    /**
     * Ejecuta un SQL encardado de realizar Update a la Base de Datos.
     * 
     * @param pSQLUpdate
     *             Contiene la sentencia Update a ser ejecutada.
     * @param pWithCommit
     */
    public static void executeSQLUpdate(String pSQLUpdate, 
                                        boolean pWithCommit) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pSQLUpdate == null || pSQLUpdate.trim().length() == 0)
            throw new SQLException("Expresion del Update no Definido", 
                                   "FarmaError", 9001);
        // Controla el COMMIT automático
        ((Connection)FarmaConnection.getConnection()).setAutoCommit(pWithCommit);
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        int registros = stmt.executeUpdate(pSQLUpdate);
        ((Connection)FarmaConnection.getConnection()).setAutoCommit(false);
        //
        stmt.close();
    }

    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * Método usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin parámetros y estos retornan un objeto del tipo OracleTypes.CURSOR
     * 2. Ejecutar Stored Procedures con o sin parámetros que no retornan nada,
     * sólo son ejecutados en el Servidor de Base de Datos Se asume que cuando
     * el parámetro pTableModel es nulo se trata de un procedimiento que no
     * retornará nada.
     * 
     * @param pTableModel
     *             Table Model usado con el JTable como modelo.
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (parámetros) usados en el Stored
     *            Procedure.
     * @param pWithCheck
     *             Variable boolean que indica si en el JTable existirá una
     *            columna de Selección.
     */
    public static void executeSQLStoredProcedure(FarmaTableModel pTableModel, 
                                                 String pStoredProcedure, 
                                                 ArrayList pParameters, 
                                                 boolean pWithCheck) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int numeroParametro = 2;
        CallableStatement stmt;
        if (pTableModel != null) {
            stmt = 
((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
                                                          pStoredProcedure + 
                                                          " }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
        } else {
            numeroParametro = 1;
            stmt = 
((Connection)FarmaConnection.getConnection()).prepareCall("{ call " + 
                                                          pStoredProcedure + 
                                                          " }");
        }
        // Setear los parámetros según el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como parámetro.
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
            // Carga la Data en el TableModel para ser usado como modelo para el
            // JTable.
            // El OracleTypes.CURSOR obtenido es "depurado" para obtener cada
            // una de las
            // columnas ... cada registro viene como un String en el que los
            // campos están
            // concatenados por el character "Ã".
            pTableModel.clearTable();
            ResultSet results = (ResultSet)stmt.getObject(1);
            ArrayList myArray = null;
            String myRow = "";
            StringTokenizer st = null;
            pTableModel.clearTable();
            while (results.next()) {
                myRow = results.getString(1);
                myArray = new ArrayList();
                if(myRow!=null)
                {
                    st = new StringTokenizer(myRow, "Ã");
                    if (pWithCheck)
                        myArray.add(new Boolean(false));
                    while (st.hasMoreTokens()) {
                        myArray.add(st.nextToken());
                    }
                    pTableModel.insertRow(myArray);
                }
            }
            results.close();
        }
        stmt.close();
    }

    /**
     * metodo que retorna un resultset proveniendo de una query sin concatenacion ||Ã||
     * pudiendo utilizar group by en el select origen
     * @param pTipoProcedimiento
     * @param pStoredProcedure
     * @param pParameters
     * @return ResultSet
     * @throws SQLException
     */
    public static ResultSet executeSQLStoredProcedure(String pTipoProcedimiento,
                                                      String pStoredProcedure,
                                                      ArrayList pParameters ) throws SQLException{
                                                     
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int numeroParametro = 2;
        CallableStatement stmt;
        if (pTipoProcedimiento.equals("FUNCTION")) {
            stmt = 
        ((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " +
                                                          pStoredProcedure + 
                                                          " }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
        } else {
            numeroParametro = 1;
            stmt = 
        ((Connection)FarmaConnection.getConnection()).prepareCall("{ call " +
                                                          pStoredProcedure + 
                                                          " }");
        }
    
        // Setear los parámetros según el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como parámetro.
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
               
        //stmt.close();
    
        return results;
    }


    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * Método usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin parámetros y estos retornan un objeto del tipo OracleTypes.CHAR
     * 
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (parámetros) usados en el Stored
     *            Procedure.
     * @return String Objeto String devuelto por la ejecución del Stored
     *         Procedure.
     */
    public static String executeSQLStoredProcedureStr(String pStoredProcedure, 
                                                      ArrayList pParameters) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        String valorRetorno = null;
        int numeroParametro = 2;
        CallableStatement stmt = 
            ((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
                                                                      pStoredProcedure + 
                                                                      " }");
        //stmt.registerOutParameter(1, OracleTypes.CHAR);
        stmt.registerOutParameter(1, OracleTypes.VARCHAR);
        // Setear los parámetros según el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como parámetro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof String[])
            {
               ArrayDescriptor arrayDesc = ArrayDescriptor.createDescriptor("PTOVENTA_CONV_BTLMF.TYP_ARR_VARCHAR", FarmaConnection.getConnection());
               stmt.setArray(numeroParametro, (Array)pParameters.get(i));
               ARRAY array = new ARRAY(arrayDesc, FarmaConnection.getConnection(),(String[])pParameters.get(i));
               stmt.setArray(i, array);
            }


            numeroParametro += 1;
        }
        stmt.execute();
        valorRetorno = (String)stmt.getObject(1);
        stmt.close();
        return valorRetorno;
    }

    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * Método usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin parámetros y estos retornan un objeto del tipo OracleTypes.INTEGER
     * 
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (parámetros) usados en el Stored
     *            Procedure.
     * @return int integer devuelto por la ejecución del Stored
     *         Procedure.
     */
    public static int executeSQLStoredProcedureInt(String pStoredProcedure, 
                                                   ArrayList pParameters) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int valorRetorno = 0;
        int numeroParametro = 2;
        CallableStatement stmt = 
            ((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
                                                                      pStoredProcedure + 
                                                                      " }");
        stmt.registerOutParameter(1, OracleTypes.INTEGER);
        // Setear los parámetros según el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como parámetro.
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
        return valorRetorno;
    }

    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * Método usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin parámetros y estos retornan un objeto del tipo OracleTypes.DOUBLE
     * 
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (parámetros) usados en el Stored
     *            Procedure.
     * @return double double devuelto por la ejecución del Stored
     *         Procedure.
     */
    public static double executeSQLStoredProcedureDouble(String pStoredProcedure, 
                                                         ArrayList pParameters) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        double valorRetorno = 0.00;
        int numeroParametro = 2;
        CallableStatement stmt = 
            ((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
                                                                      pStoredProcedure + 
                                                                      " }");
        stmt.registerOutParameter(1, OracleTypes.DOUBLE);
        // Setear los parámetros según el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como parámetro.
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
     * Ejecuta un Stored Procedure de la Base de Datos.
     * @param pArrayList Arreglo de Respuesta
     * @param pStoredProcedure Stored Procedure
     * @param pParameters Arreglo de Parámetros
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
((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
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
            //Se coloco este myRow porque si entraba fallaba y no finaliza el close.
            //y dejaba los cursores abiertos
            if (myRow != null) {
                st = new StringTokenizer(myRow, "Ã");
                while (st.hasMoreTokens())
                    myArray.add(st.nextToken());
                pArrayList.add(myArray);
            }
        }
        results.close();
        stmt.close();
    }


    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * Método usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin parámetros.Pero este tiene un parametro del tipo OracleTypes.CHAR
     * 
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (parámetros) usados en el Stored
     *            Procedure.
     * @return String Objeto String devuelto por la ejecución del Stored
     *         Procedure.
     * @author Diego Ubilluz
     * @since  20.08.2008
     */
    public static String executeSQLStoredProcedureStrOut(String pStoredProcedure, 
                                                      ArrayList pParameters) throws SQLException {
        // Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        String valorRetorno = null;
        int numeroParametro = 1;
        CallableStatement stmt = 
            ((Connection)FarmaConnection.getConnection()).prepareCall("{ call  " + 
                                                                       pStoredProcedure + 
                                                                      " }");
        
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
        
        stmt.registerOutParameter(numeroParametro, OracleTypes.CHAR);
        stmt.execute();
        valorRetorno = (String)stmt.getObject(numeroParametro);
        stmt.close();
        return valorRetorno;
    }


    /**
     * Obtiene un valor de la Base de Datos.
     * @param tableName Nombre de la Tabla
     * @param fieldCode Campo
     * @param whereCondition Condición
     * @return String - Valor de la Consulta.
     * @throws SQLException
     */
    public static String getValueAt(String tableName, String fieldCode, 
                                    String whereCondition) throws SQLException {
        String valor = "";
        String query = 
            "SELECT " + fieldCode + " FROM " + tableName + " WHERE " + 
            whereCondition + "";
        // System.out.println("QUERY="+query);
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        ResultSet results = stmt.executeQuery(query);
        if (results.next()) {
            valor = results.getString(1);
        }
        if (results.next())
            throw new SQLException("Existe más de 1 registro", "FarmaError", 
                                   9000);
        results.close();
        stmt.close();
        if (valor == null)
            valor = "";
        return valor;
    }

    /**
     * Constructor.
     */
    public FarmaDBUtility() {
    }
    
    
    
    /**
     * METODO ENCARGADO DE OBTENER UN LIST con un conjunto de Map
     * a partir de un cursor retornado por una funcion de base de datos
     * 
     * @author JCALLO
     * @since  03/03/2009
     * @param pStoredProcedure Stored Procedure
     * @param pParameters Arreglo de Parámetros
     * @throws SQLException
     */
    public static List executeSQLStoredProcedureListMap(String pStoredProcedure, 
                                                        List pParameters) throws SQLException {
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido","FarmaError", 9002);
        
        int numeroParametro = 2;
        CallableStatement stmt;
        
        stmt = ((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
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
        
        //ejecutando el estoredProcedure
        stmt.execute();
        //Obteniendo el cursor con el resultado
        ResultSet results = (ResultSet)stmt.getObject(1);
        
        //obteniendo el metadato del resulset
        ResultSetMetaData metaDatos = results.getMetaData();
        // Se obtiene el número de columnas.
        int numeroColumnas = metaDatos.getColumnCount();
        // Se crea un array de etiquetas para rellenar
        Object[] etiquetas = new Object[numeroColumnas];
        // Se obtiene cada una de las etiquetas para cada columna
        for (int i = 0; i < numeroColumnas; i++)
        {
           // Nuevamente, para ResultSetMetaData la primera columna es la 1.
           etiquetas[i] = metaDatos.getColumnLabel(i + 1);
        }
        
        
        List listaResultado = new  ArrayList();
        Map mapaFila;       
        while (results.next()) {
        	mapaFila = new HashMap();
        	for(int i=0 ; i < numeroColumnas; i++){
        		mapaFila.put(etiquetas[i], results.getString(etiquetas[i].toString()));
        	}
        	listaResultado.add(mapaFila);
        }
        results.close();
        stmt.close();
        
        return listaResultado;
    }
    
    /**
     * METODO ENCARGADO DE OBTENER UN REGISTRO DE BASE DE DATOS en MAP
     * a partir de un cursor retornado por una funcion de base de datos
     * 
     * @author JCALLO
     * @since  03/03/2009
     * @param  pStoredProcedure Stored Procedure
     * @param  pParameters Arreglo de Parámetros
     * @return Map
     * @throws SQLException
     */
    public static Map executeSQLStoredProcedureMap(String pStoredProcedure, 
                                                   List pParameters) throws SQLException {
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido","FarmaError", 9002);
        
        int numeroParametro = 2;
        CallableStatement stmt;
        
        stmt = ((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
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
        
        //ejecutando el estoredProcedure
        stmt.execute();
        //Obteniendo el cursor con el resultado
        ResultSet results = (ResultSet)stmt.getObject(1);
        
        //obteniendo el metadato del resulset
        ResultSetMetaData metaDatos = results.getMetaData();
        // Se obtiene el número de columnas.
        int numeroColumnas = metaDatos.getColumnCount();
        // Se crea un array de etiquetas para rellenar
        Object[] etiquetas = new Object[numeroColumnas];
        // Se obtiene cada una de las etiquetas para cada columna
        for (int i = 0; i < numeroColumnas; i++)
        {
           // Nuevamente, para ResultSetMetaData la primera columna es la 1.
           etiquetas[i] = metaDatos.getColumnLabel(i + 1);
        }
        
        Map mapaFila = new HashMap();      
        if (results.next()) {
        	for(int i=0 ; i < numeroColumnas; i++){
        		mapaFila.put(etiquetas[i], results.getString(etiquetas[i].toString()));
        	}
        }
        results.close();
        stmt.close();
        
        return mapaFila;
    }
    
    
    /**
     * METODO ENCARGADO DE retornar un tipo de dato STRING
     * 
     * @author JCALLO
     * @since  04/03/2009
     * @param  pStoredProcedure Stored Procedure
     * @param  pParameters Arreglo de Parámetros
     * @return Map
     * @throws SQLException
     */
    public static String executeSQLStoredProcedureString(String pStoredProcedure, 
                                                         List pParameters) throws SQLException {
    	if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido","FarmaError", 9002);
        
        int numeroParametro = 2;
        CallableStatement stmt;
        
        stmt = ((Connection)FarmaConnection.getConnection()).prepareCall("{ call ? := " + 
        																	pStoredProcedure +
        																 " }");
        stmt.registerOutParameter(1, OracleTypes.VARCHAR);
        
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
        
        String resultado = (String)stmt.getObject(1);
        stmt.close();
        
        return resultado;
    }
    
    /**
     * Retorna un List de Map, invocando un query SQL
     * 
     * @param pSQLSelect Query ha ejecutar.
     * @return String Colección de datos - resultado del Query.
     */
    public static List selectSQLList(String pSQLSelect, List parametros) throws SQLException {
    	
    	// Disparamos una excepción si el query que llega como parámetro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.        
        if (pSQLSelect == null || pSQLSelect.trim().length() == 0)
            throw new SQLException("Expresion del Query no Definido", 
                                   "FarmaError", 9000);
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        // Retornamos el ResultSet resultado de ejecutar el Query.
        ResultSet results = stmt.executeQuery(pSQLSelect);
    	
        //obteniendo el metadato del resulset
        ResultSetMetaData metaDatos = results.getMetaData();
        // Se obtiene el número de columnas.
        int numeroColumnas = metaDatos.getColumnCount();
        // Se crea un array de etiquetas para rellenar
        Object[] etiquetas = new Object[numeroColumnas];
        // Se obtiene cada una de las etiquetas para cada columna
        for (int i = 0; i < numeroColumnas; i++)
        {
           // Nuevamente, para ResultSetMetaData la primera columna es la 1.
           etiquetas[i] = metaDatos.getColumnLabel(i + 1);
        }
        
        List listaResultado = new ArrayList();        
        Map mapaFila;        
        while (results.next()) {
        	mapaFila = new HashMap();
        	for(int i=0 ; i < numeroColumnas; i++){        		
        		mapaFila.put(etiquetas[i], results.getString(etiquetas[i].toString()));
        	}
        	listaResultado.add(mapaFila);
        }
        results.close();
        stmt.close();
        
        return listaResultado;
    }
}
