package mifarma.common;

import java.sql.*;

/**
 * Copyright (c) 2007 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaConnectionRemoto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaConnectionRemoto {

    /** Almacena la conexión actualmente vigente */
    static Connection conn;

    /** String de conexión Remota */
    public static String connect_string_remota = "";

    public static String CON_MATRIZ = 
        "jdbc:oracle:thin:" + FarmaVariables.vIdUsuDBMatriz + "/" + 
        FarmaVariables.vClaveBDMatriz + "@" + 
        FarmaVariables.vIpServidorDBMatriz + ":" + FarmaVariables.vPUERTO + 
        ":" + FarmaVariables.vSidDBMatriz;
    public static String CON_DELIVERY = 
        "jdbc:oracle:thin:" + FarmaVariables.vIdUsuDBDelivery + "/" + 
        FarmaVariables.vClaveBDDelivery + "@" + 
        FarmaVariables.vIpServidorDBDelivery + ":" + FarmaVariables.vPUERTO + 
        ":" + FarmaVariables.vSidDBDelivery;

    //Agregado por DVELIZ 15.12.2008
    public static String CON_ADMCentral = 
        "jdbc:oracle:thin:" + FarmaVariables.vIdUsuDBADMCentral + "/" + 
        FarmaVariables.vClaveBDADMCentral + "@" + 
        FarmaVariables.vIpServidorDBADMCentral + ":" + FarmaVariables.vPUERTO + 
        ":" + FarmaVariables.vSidDBADMCentral;

   //Agregado por FRAMIREZ 16.12.2011
    public static String CON_RAC =
        "jdbc:oracle:thin:" + FarmaVariables.vIdUsuDBRac + "/" +
        FarmaVariables.vClaveBDRac + "@" +
        FarmaVariables.vIpServidorDBRac + ":" + FarmaVariables.vPUERTO +
        ":" + FarmaVariables.vSidDBRac;

    /**
     *Retorna la actual conexión de Base de Datos.
     *@return Connection Objeto de conexión retornado.
     */
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed() ) {
            System.out.println("Abre conexion Remota..");
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            
            //DriverManager.setLoginTimeout(3);
            //conn.
            conn = DriverManager.getConnection(connect_string_remota);
            //conn = DriverManager.getConnection("jdbc:oracle:oci:@EDBDES00","ECVENTA","VENTA");
            
            conn.setAutoCommit(false);
        }
        return conn;
    }


    public static Connection getConnection(int pTimeConection) throws SQLException {
        //String connect_string_thin = "jdbc:oracle:thin:ptoventa/ptoventa_prueba@10.11.1.99:1521:XE";
        System.out.println("Get Connection 2");
        if (conn == null) {
            System.out.println("pTimeConection:"+pTimeConection);
           // System.out.println("connect_string_thin:"+connect_string_thin);
            DriverManager.setLoginTimeout(pTimeConection);
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(connect_string_remota);
            conn.setAutoCommit(false);
        }
        return conn;
    }
    /**
     *Cierra la conexión de Base de Datos.
     */
    public static void closeConnection() {
        if (conn != null) {
            
            System.out.println("Cierre de conexion Remota..");
            
            try {
                conn.close();
                conn = null;
            } catch (Exception e) {
            }
        }
        return;
    }

    /**
     *Constructor
     */
    public FarmaConnectionRemoto(int ind_conexion) {
        /** String de conexión modo THIN */

        connect_string_remota = 
                cadena_conexion_seleccionada(ind_conexion).trim();
        //System.out.println("Conexion elegida" + connect_string_remota);

    }

    private String cadena_conexion_seleccionada(int indConnection) {
        switch (indConnection) {
        case 0:
            return CON_MATRIZ;
        case 1:
            return CON_DELIVERY;
        case 2:
            return CON_ADMCentral;
        case 3:
	        return CON_RAC;
	    }
        return "";
    }

}
