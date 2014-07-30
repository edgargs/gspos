package mifarma.common;

import java.sql.*;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicaci�n : FarmaConnectionMatriz.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      07.01.2006   Creaci�n<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaConnectionMatriz {

    /** Almacena la conexi�n actualmente vigente */
    static Connection conn;

    /** String de conexi�n modo THIN */
    public static
    // public static String connect_string_thin = "jdbc:oracle:thin:ecventa/venta@ptoventa:1521:";
    // TEST EN MATRIZ
    //public static String connect_string_thin = "jdbc:oracle:thin:ecventa/venta@192.168.0.121:1521:";
    // Chiclayo 2 - Desarrollo
    String connect_string_thin = 
        "jdbc:oracle:thin:ecventa/venta@192.168.0.120:1521:EDBPV000";

    // PV000 - Matriz
    //public static String connect_string_thin = "jdbc:oracle:thin:ecventa/venta@192.168.0.120:1521:";
    // Produccion
    //public static String connect_string_thin = "jdbc:oracle:thin:ecventa/venta@ptoventa:1521:";

    //public static String connect_string_thin = "jdbc:oracle:thin:ecventa/venta@:1521:";
    // public static String connect_string_thin = "jdbc:oracle:thin:delivery/delivery@ptoventa:1521:";

    /** String de conexi�n modo OCI */
    static final String connect_string_oci = 
        "jdbc:oracle:oci:ecventa/venta@edbdes00";

    /**
     *Retorna la actual conexi�n de Base de Datos.
     *@return Connection Objeto de conexi�n retornado.
     */
    public static Connection getConnection() throws SQLException {
        System.out.println("antes del conn: " + conn);
        //conn.
        if (conn == null || conn.isClosed()) {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(connect_string_thin);
            System.out.println("conn: " + conn);
            //conn = DriverManager.getConnection("jdbc:oracle:oci:@EDBDES00","ECVENTA","VENTA");
            conn.setAutoCommit(false);
        }
        return conn;
    }

    /**
     *Cierra la conexi�n de Base de Datos.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                if (!conn.isClosed())
                    conn.close();
            } catch (Exception e) {
            }
        }
        return;
    }

    /**
     *Constructor
     */
    public FarmaConnectionMatriz() {
    }

}
