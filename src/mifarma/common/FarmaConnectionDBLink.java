package mifarma.common;

import java.sql.Connection;
import java.lang.System;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Date;

/**
 * Copyright (c) 2008 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : FarmaConnectionLocal.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DVELIZ      29.10.2008   Creación<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 *
 */
 
public class FarmaConnectionDBLink {

    static Connection conn;
    
    /** String de conexión modo THIN */
    public static String connect_string_thin = 
        "jdbc:oracle:thin:" + FarmaVariables.vUsuarioBD + "/" + 
        FarmaVariables.vClaveBD + "@" + FarmaVariables.vIPBD + ":" + 
        FarmaVariables.vPUERTO + ":" + FarmaVariables.vSID;
        
    /** String de conexión modo OCI */
    static final String connect_string_oci = 
        "jdbc:oracle:oci:ecventa/venta@edbdes00";
    
    /**
     *Retorna la actual conexión de Base de Datos.
     *@return Connection Objeto de conexión retornado.
     */
    public static Connection getConnection() throws SQLException {
        System.out.println("Cadena de conexion "+connect_string_thin);
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
     *Cierra la conexión de Base de Datos.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return;
    }

    /**
     *Constructor
     */
    public FarmaConnectionDBLink() {
    }

    /**
     * Anula la conexion.
     */
    public static void anularConnection() {
        conn = null;
        System.out.println("FarmaConnection: conn anulada");
    }

}

