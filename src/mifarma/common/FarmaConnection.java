package mifarma.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaConnection.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      07.01.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaConnection {

    private static final Logger log = LoggerFactory.getLogger(FarmaConnection.class);
    
    /** Almacena la conexión actualmente vigente */
    static Connection conn;

    /** String de conexión modo THIN */
    public static String connect_string_thin = 
        "jdbc:oracle:thin:" + FarmaVariables.vUsuarioBD + "/" + 
        FarmaVariables.vClaveBD + "@//" + FarmaVariables.vIPBD + ":" + 
        FarmaVariables.vPUERTO + "/" + FarmaVariables.vSID;
    //public static String connect_string_thin = "jdbc:oracle:thin:ptoventa/@:1521:XE";

    /** String de conexión modo OCI */
    static final String connect_string_oci = 
        "jdbc:oracle:oci:ecventa/venta@edbdes00";

    /**
     *Retorna la actual conexión de Base de Datos.
     *@return Connection Objeto de conexión retornado.
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
            log.debug("Tiempo de Abrir Conexion(ms): " + 
                               (milisegundos2 - milisegundos1));
            
            setVersion();
            log.debug("FarmaConnection: conn inicializada");
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
                //Correo
                FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                              FarmaVariables.vCodLocal,
                                              //FarmaConstants.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                              FarmaVariables.vEmail_Destinatario_Error_Impresion,
                                              "Error al Aceptar Transaccion ",
                                              "Error de Impresión StartPrintService",
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
        log.debug("FarmaConnection: conn anulada");
    }

    /**
     * Se indica la version del sistema
     * @author ERIOS
     * @since 2.2.9
     * @throws SQLException
     */
    public static void setVersion() throws SQLException {        
        if(!FarmaVariables.vNombreModulo.equals("") &&
            !FarmaVariables.vVersion.equals("") &&
            !FarmaVariables.vCompilacion.equals("")){
            
            ArrayList parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(FarmaVariables.vNombreModulo.toUpperCase());
            parametros.add(FarmaVariables.vVersion);
            parametros.add(FarmaVariables.vCompilacion);
            
            FarmaDBUtility.executeSQLStoredProcedure(null, "FARMA_SECURITY.SET_MODULO_VERSION(?,?,?,?,?,?)", parametros,
                                                     false);
        }
    }
}
