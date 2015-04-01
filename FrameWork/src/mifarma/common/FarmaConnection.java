package mifarma.common;

import java.sql.*;

import java.util.Date;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicaci�n : FarmaConnection.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      07.01.2006   Creaci�n<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaConnection {

    /** Almacena la conexi�n actualmente vigente */
    static Connection conn;

    /** String de conexi�n modo THIN */
    public static String connect_string_thin = 
        "jdbc:oracle:thin:" + FarmaVariables.vUsuarioBD + "/" + 
        FarmaVariables.vClaveBD + "@//" + FarmaVariables.vIPBD + ":" + 
        FarmaVariables.vPUERTO + "/" + FarmaVariables.vSID;
    //public static String connect_string_thin = "jdbc:oracle:thin:ptoventa/@:1521:XE";

    /** String de conexi�n modo OCI */
    static final String connect_string_oci = 
        "jdbc:oracle:oci:ecventa/venta@edbdes00";

    /**
     *Retorna la actual conexi�n de Base de Datos.
     *@return Connection Objeto de conexi�n retornado.
     */
    public static Connection getConnection() throws SQLException {
        Date fecha1 = new Date();
        long milisegundos1 = fecha1.getTime();       
              
        if (conn == null || conn.isClosed()) {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(connect_string_thin);
            //conn = DriverManager.getConnection("jdbc:oracle:oci:@EDBDES00","ECVENTA","VENTA");
            conn.setAutoCommit(false);
            Date fecha2 = new Date();
            long milisegundos2 = fecha2.getTime();
            System.out.println("Tiempo de Abrir Conexion(ms): " + 
                               (milisegundos2 - milisegundos1));
            System.out.println("FarmaConnection: conn inicializada");
        }        
        return conn;
    }

    /**
     *Cierra la conexi�n de Base de Datos.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                //Correo
                FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                              FarmaVariables.vCodLocal,
                                              //FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                              FarmaVariables.vEmail_Destinatario_Error_Impresion,
                                              "Error al Aceptar Transaccion ",
                                              "Error de Impresi�n StartPrintService",
                                              "Se produjo un error al imprimir un pedido :<br>"+
                                              "IP : " +FarmaVariables.vIpPc+"<br>"+
                                              "Error: " + e.getMessage() ,
                                              ""); 
            }
        }
        return;
    }

    /**
     *Constructor
     */
    public FarmaConnection() {
    }

    /**
     * Anula la conexion.
     */
    public static void anularConnection() {
        conn = null;
        System.out.println("FarmaConnection: conn anulada");
    }

}