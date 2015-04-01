package mifarma.common;

import java.sql.*;

import java.util.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaSearch.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 *
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaSearch {

    /** Almacena los parámetros necesarios para la ejecución de un SP */
    private static ArrayList parametros = new ArrayList();

    /**
     * Constructor.
     */
    public FarmaSearch() {
    }

    /**
     * Retorna la fecha o fecha_hora de la base de datos en un formato
     * pre-determinado. 1=FORMATO_FECHA 2=FORMATO_FECHA_HORA
     *
     * @param pTipo
     *             Tipo de formato (fecha o fecha_hora).
     * @return String Fecha de la base de datos en el formato
     *         establecido.
     */
    public static String getFechaHoraBD(int pTipo) throws SQLException {
        String fechahora = "";
        String query = 
            "SELECT TO_CHAR(SYSDATE,'dd/mm/yyyy hh24:mi:ss') FROM DUAL";
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        // Ejecutar el query haciendo uso de la conexión por default
        ResultSet results = stmt.executeQuery(query);
        if (results.next())
            fechahora = results.getString(1);
        results.close();
        stmt.close();
        // Determinar formato de fecha_hora para retornar el valor
        // Valores de retorno : dd/mm/yyyy
        // dd/mm/yyyy hh24:mi:ss
        if (fechahora.trim().length() > 0 && 
            pTipo == FarmaConstants.FORMATO_FECHA)
            fechahora = fechahora.substring(0, 10);

        if (fechahora.trim().length() > 0 && 
            pTipo == FarmaConstants.FORMATO_HORA)
            fechahora = fechahora.substring(11, 19);

        return fechahora;
    }

    /**Obtiene la fecha del sistema con una variación
     * @param pTipo
     * 			Indica el formato de fecha
     * @param pVariacion
     * 			Indica la variación a aplicar
     * @return String
     * 			Fecha y hora
     * @throws SQLException
     * 			Puede generar una excepción en caso de error en la ejecución de la sentencia
     */
    public static String getFechaHoraParametroBD(int pTipo, 
                                                 int pVariacion) throws SQLException {
        String fechahora = "";
        String query = 
            "SELECT TO_CHAR(SYSDATE+" + String.valueOf(pVariacion) + ",'dd/mm/yyyy hh24:mi:ss') FROM DUAL";
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        // Ejecutar el query haciendo uso de la conexión por default
        ResultSet results = stmt.executeQuery(query);
        if (results.next())
            fechahora = results.getString(1);
        results.close();
        stmt.close();
        // Determinar formato de fecha_hora para retornar el valor
        // Valores de retorno : dd/mm/yyyy
        // dd/mm/yyyy hh24:mi:ss
        if (fechahora.trim().length() > 0 && 
            pTipo == FarmaConstants.FORMATO_FECHA)
            fechahora = fechahora.substring(0, 10);
        return fechahora;
    }

    /**Obtiene la fecha del sistema con una variación
     * @param pTipo
     * 			Indica el formato de fecha
     * @param pVariacion
     * 			Indica la variación a aplicar
     * @return String
     * 			Fecha y hora
     * @throws SQLException
     * 			Puede generar una excepción en caso de error en la ejecución de la sentencia
     */
    public static String getFechaHoraAtrasadaBD(int pTipo, 
                                                int pVariacion) throws SQLException {
        String fechahora = "";
        String query = 
            "SELECT TO_CHAR(SYSDATE-" + String.valueOf(pVariacion) + ",'dd/mm/yyyy hh24:mi:ss') FROM DUAL";
        Statement stmt = 
            ((Connection)FarmaConnection.getConnection()).createStatement();
        // Ejecutar el query haciendo uso de la conexión por default
        ResultSet results = stmt.executeQuery(query);
        if (results.next())
            fechahora = results.getString(1);
        results.close();
        stmt.close();
        // Determinar formato de fecha_hora para retornar el valor
        // Valores de retorno : dd/mm/yyyy
        // dd/mm/yyyy hh24:mi:ss
        if (fechahora.trim().length() > 0 && 
            pTipo == FarmaConstants.FORMATO_FECHA)
            fechahora = fechahora.substring(0, 10);
        return fechahora;
    }


    /**
     * Setea el Tipo de Cambio usado por toda la aplicación.
     *
     * @param pDiaVenta
     *             Fecha de Venta en el formato dd/MM/yyyy.
     */
    public static double getTipoCambio(String pDiaVenta) throws SQLException {
        ArrayList parameters = new ArrayList();
        parameters.add(FarmaVariables.vCodGrupoCia);
        parameters.add(pDiaVenta);
        return FarmaDBUtility.executeSQLStoredProcedureDouble("FARMA_UTILITY.OBTIENE_TIPO_CAMBIO(?,?)", 
                                                              parameters);
    }

    /**
     * Retorna un arreglo con los datos del local.
     *
     * @param pCodigoLocal
     *             Código del Local.
     * @return ArrayList Contiene los datos del Local.
     */
    public static ArrayList obtieneDatoLocal(String pCodigoLocal) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodigoLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, 
                                                          "Farma_SECURITY.OBTIENE_DATO_LOCAL(?,?)", 
                                                          parametros);
        return pOutParams;
    }

    /**
     * Retorna el Secuencial requerido alineado y con ceros a la izquierda.
     *
     * @param pCoNumeracion
     *             Código de la Numeración del Secuencial.
     * @param pLength
     *             Longitud del Secuencial.
     * @return String Secuencial.
     */
    public static String getNuSecNumeracion(String pCoNumeracion, 
                                            int pLength) throws SQLException {
        // Obtener Secuencial de la tabla CMTR_NUMERACION
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCoNumeracion);
        String nuSecMovimiento = 
            String.valueOf(FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_UTILITY.OBTENER_NUMERACION(?,?,?)", 
                                                                       parametros));
        return FarmaUtility.completeWithSymbol(nuSecMovimiento, pLength, "0", 
                                               "I");
    }

    /**
     * Setea Secuencial en Base de Datos.
     *
     * @param pCoNumeracion
     *             Código de la Numeración del Secuencial.
     */
    public static void setNuSecNumeracion(String pCoNumeracion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCoNumeracion);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.ACTUALIZAR_NUMERACION(?,?,?,?)", 
                                                 parametros, false);
    }

    /**
     * Setea Secuencial en Base de Datos. No realiza COMMIT
     *
     * @param pCoNumeracion
     *             Código de la Numeración del Secuencial.
     */
    public static void setNuSecNumeracionNoCommit(String pCoNumeracion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCoNumeracion);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.ACTUALIZAR_NUMERA_SIN_COMMIT(?,?,?,?)", 
                                                 parametros, false);
    }

    public static void inicializaNumeracionNoCommit(String pCoNumeracion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCoNumeracion);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.INICIALIZA_NUMERA_SIN_COMMIT(?,?,?,?)", 
                                                 parametros, false);
    }

    /**
     * Libera la ejecución de la transacción. Culmina el FOR UPDATE en Base de
     * Datos.
     */
    public static void liberarTransaccion() throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.LIBERAR_TRANSACCION", 
                                                 parametros, false);
    }
    public static void liberarTransaccionRemota(int pTipoConexion,
                                                String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null, 
                                                      "FARMA_UTILITY.LIBERAR_TRANSACCION", 
                                                      parametros,
                                                      false,
                                                      pTipoConexion,
                                                      pIndCloseConecction);
    }    
    
    /**
     * Acepta la ejecución de la transacción. Hace COMMIT en Base de Datos.
     */
    public static void aceptarTransaccion() throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.ACEPTAR_TRANSACCION", 
                                                 parametros, false);
    }
    
    public static void aceptarTransaccionRemota(int pTipoConexion,
                                                String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.ACEPTAR_TRANSACCION", 
                                                 parametros, false,
                                                 pTipoConexion,
                                                 pIndCloseConecction);
    }    

    /**
     * Retorna el valor del Impuesto General a las Ventas - IGV
     *
     * @return double Valor del IGV.
     */
    public static double getIGV() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        // parametros.add(FarmaConstants.IMPUESTO_IGV);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVTA_VENTAS.MONTOIGV(?,?)", 
                                                              parametros);
    }

    /**
     * Setea el valor del Impuesto General a las Ventas tanto en porcentaje como
     * en indicador para cálculo
     */
    public static void setIGV() throws SQLException {
        FarmaVariables.vIgvPorc = getIGV();
        FarmaVariables.vIgvCalculo = 1 + (FarmaVariables.vIgvPorc / 100);
    }

    public static String getNumeracionComprobante(String pNuImpresora) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pNuImpresora));
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.GET_NUMERACION_COMPROBANTE(?,?,?)", 
                                                           parametros);
    }

    public static void setNumeracionComprobante(String pNuImpresora) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNuImpresora);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.SET_NUMERACION_COMPROBANTE(?,?,?,?)", 
                                                 parametros, false);
    }

    /** Obtiene datos mediante un procedimiento almacenado
     * @param pTableModel
     * 				Objeto TableModel que recepcionará los datos
     * @param pStoredProcedure
     * 				Procedimiento almacenado a ejecutar
     * @param pParameters
     * 				Parametros de la operación
     * @throws SQLException
     * 				Puede generar una excepción en caso de error en la ejecución de la sentencia
     *
     */
    public static void loadDataForMaintenance(FarmaTableModel pTableModel, 
                                              String pStoredProcedure, 
                                              ArrayList pParameters) throws SQLException {
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, pStoredProcedure, 
                                                 pParameters, false);
    }

    /** Ejecuta una sentencia SQL
     * @param pSQL
     * 			Sentencia a ejecutar
     * @throws SQLException
     * 			Genera una excepción en caso de error en la ejecución de la sentencia
     */
    public static void executeSentenceForMaintenance(String pSQL) throws SQLException {
        FarmaDBUtility.executeSQLUpdate(pSQL);
    }

    public static void updateNumera(String pCod_Numera) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCod_Numera);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "FARMA_UTILITY.ACTUALIZAR_NUMERA_SIN_COMMIT(?,?,?,?)", 
                                                 parametros, false);
    }

}
