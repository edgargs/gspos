package mifarma.ptoventa.cnx;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;

import mifarma.ptoventa.reference.BeanConexion;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import oracle.jdbc.OracleDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FarmaVentaCnxUtility {

    private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(FarmaVentaCnxUtility.class);
    
    
    /**
     * Constructor
     */
    public FarmaVentaCnxUtility() {
    }

    
    public String getIndLineaRecarga()
       {
           String valorRetorno = "";
           Connection pCnx = getConexionRecarga();
           try {
            String vQuery = "Select sysdate from dual";
            Statement stmt = pCnx.createStatement();
            ResultSet results = stmt.executeQuery(vQuery.trim());
            while (results.next()) {
                valorRetorno = results.getString(1);
            }
            stmt.close();
            pCnx.close();
            pCnx = null;
               
            valorRetorno = "S";   
            } catch (Exception sqle) {
                try {
                    if(pCnx.isClosed())
                        pCnx.close();
                } catch (Exception e) {
                }
            pCnx = null; 
                // TODO: Add catch code
                //sqle.printStackTrace();
                log.info("Error getConexion: "+sqle);
                valorRetorno = "N";   
                 enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                                  FarmaVariables.vCodLocal,
                                  VariablesPtoVenta.vDestEmailErrorCobro,
                                  "Error de Conexion getIndLineaRecarga",
                                  "Error de Comunicacion getIndLineaRecarga",
                                  "¡No hay conexión con Recargas" + "<br>"+ 
                                  "Inténtelo nuevamente." + "<br>"+ 
                                  "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                  "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                  "Error: " + "Conexion :"+sqle.getMessage() ,
                                  "");                
            }
          
           
           return valorRetorno;        
       }     
    

    /**
     * @Author Diego Armando Ubilluz Carrillo
     * Envia la consulta de Recarga en Matriz.
     * @param connect_string_thin
     * @return
     */
    public  Connection getConexionRecarga(){ 
        
        String getDatosRecarga = getCnxRemotoRecarga();
        
        if(!getDatosRecarga.trim().equalsIgnoreCase("N")){
          try {

              String[] pDatosCnx = getDatosRecarga.split("@");
              
              String vIpServidorDB = pDatosCnx[0].trim() ;
              String vIdUsuDB = pDatosCnx[1].trim() ;
              String vClaveBD = pDatosCnx[2].trim() ;
              String vSidDB = pDatosCnx[3].trim() ;
              String vPUERTODB = pDatosCnx[4].trim() ;
              int  pTimeOut = Integer.parseInt(pDatosCnx[5].trim());
              
              String connect_string_thin =  "jdbc:oracle:thin:" + 
                                            vIdUsuDB + "/" + 
                                            vClaveBD + "@" + 
                                            vIpServidorDB + ":" + 
                                            vPUERTODB + ":" + 
                                            vSidDB;
              
              Connection con;
              DriverManager.setLoginTimeout(pTimeOut);
              DriverManager.registerDriver(new OracleDriver());     
              con = DriverManager.getConnection(connect_string_thin);
              con.setAutoCommit(false);
              log.info("getConexion "+connect_string_thin);
              return con;
          } catch (Exception e) {
              log.info("Error getConexion: "+e);
              enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                               FarmaVariables.vCodLocal,
                               VariablesPtoVenta.vDestEmailErrorCobro,
                               "Error de Conexion getConexionRecarga",
                               "Error de Comunicacion getConexionRecarga",
                               "¡No hay conexión con Recargas" + "<br>"+ 
                               "Inténtelo nuevamente." + "<br>"+ 
                               "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                               "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                               "Error: " + "Conexion :"+e.getMessage() ,
                               "");                
              return null;
          } 
        }   
        
        return null;
    }    
    
    /**
     * Obtiene La Ruta de Conexion para Recarga
     * @return
     * @throws SQLException
     */
    public  String getCnxRemotoRecarga()
    {
        parametros = new ArrayList();
        log.debug("invocando a FARMA_UTILITY.GET_FECHA_ACTUAL:"+parametros);
        String pResultado = "N";
        
        try 
        {
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            pResultado = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA.FARMA_CNX_REMOTO.F_CNX_RECARGAS(?,?)", parametros);
        } catch (Exception sqle) {
            // TODO: Add catch code
            log.info(sqle.getMessage());
            enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                             FarmaVariables.vCodLocal,
                             VariablesPtoVenta.vDestEmailErrorCobro,
                             "Error de Obtener Cadena Conexion getCnxRemotoRecarga",
                             "Error de Obtener Cadena Conexion getCnxRemotoRecarga",
                             "Error en Obtener Cadena Conexion" + "<br>"+ 
                             "Inténtelo nuevamente." + "<br>"+ 
                             "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                             "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                             "Error: " + "Conexion :"+sqle.getMessage() ,
                             ""); 
            pResultado = "N";
        }
        return pResultado;
    }     
    
    
    public static void enviaCorreoPorCnx(String pCodCia,String pCodLocal,
                                        String pReceiverAddress,
                                        String pAsunto,
                                        String pTitulo,
                                        String pMensaje,
                                        String pCCReceiverAddress){
        log.debug("Enviando Correo.. por BD");
        String  prueba = "";
        ArrayList prm = new ArrayList();
        prm.add(pCodCia);
        prm.add(pCodLocal);
        prm.add(pReceiverAddress);
        prm.add(pAsunto);
        prm.add(pTitulo);
        prm.add(pMensaje);
        prm.add(pCCReceiverAddress);
        
         try
         {
           FarmaDBUtility.executeSQLStoredProcedure(null,
                                                    "FARMA_UTILITY.envia_correo(?,?,?,?,?,?,?)",
                                                    prm,
                                                    true);
         } catch(Exception sql)
         {
           sql.printStackTrace();
           
         }    
         
    }

    /**
     * Obtiene usuario de conexion al RAC por local
     * @author ERIOS
     * @since 2.3.4
     * @return
     */
    public String getCnxRemotoRAC() {
        parametros = new ArrayList();
        String pResultado = "N";

        try {
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodCia);
            parametros.add(FarmaVariables.vCodLocal);
            pResultado =
                    FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA.FARMA_CNX_REMOTO.F_CNX_RAC_LOCAL(?,?,?)",
                                                                parametros);
        } catch (Exception sqle) {
            log.error("", sqle);
            enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                             FarmaVariables.vCodLocal,
                             VariablesPtoVenta.vDestEmailErrorCobro,
                             "Error de Obtener Cadena Conexion getCnxRemotoRAC",
                             "Error de Obtener Cadena Conexion getCnxRemotoRAC",
                             "Error en Obtener Cadena Conexion" + "<br>"+ 
                             "Inténtelo nuevamente." + "<br>"+ 
                             "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                             "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                             "Error: " + "Conexion :"+sqle.getMessage() ,
                             "");
            pResultado = "N";
        }
        return pResultado;
    }

    /**
     * Obtiene conexion generico
     * @author ERIOS
     * @since 2.3.4
     * @param pDatosConexion
     * @return
     * @throws Exception
     */
    private Connection getConexion(String pDatosConexion) throws Exception {

        if (!pDatosConexion.trim().equalsIgnoreCase("N")) {

            String[] pDatosCnx = pDatosConexion.split("@");

            String vIpServidorDB = pDatosCnx[0].trim();
            String vIdUsuDB = pDatosCnx[1].trim();
            String vClaveBD = pDatosCnx[2].trim();
            String vSidDB = pDatosCnx[3].trim();
            String vPUERTODB = pDatosCnx[4].trim();
            int pTimeOut = Integer.parseInt(pDatosCnx[5].trim());

            String connect_string_thin =
                "jdbc:oracle:thin:" + vIdUsuDB + "/" + vClaveBD + "@" + vIpServidorDB + ":" + vPUERTODB + ":" + vSidDB;

            setBeanConexionRAC(pDatosConexion);
                                                     
            Connection con;
            DriverManager.setLoginTimeout(pTimeOut);
            DriverManager.registerDriver(new OracleDriver());
            con = DriverManager.getConnection(connect_string_thin);
            con.setAutoCommit(false);
            log.info("getConexion " + connect_string_thin);
            return con;
        }

        return null;
    }

    /**
     * Verifica conexion con RAC
     * @aurhor ERIOS
     * @since 2.3.4
     * @return
     */
    public String getIndLineaRAC() throws Exception {
        String valorRetorno = "";
        Connection pCnx = null;
        Statement stmt = null;
        ResultSet results = null;
        try {
            pCnx = getConexion(getCnxRemotoRAC());
            String vQuery = "Select sysdate from dual";
            stmt = pCnx.createStatement();
            results = stmt.executeQuery(vQuery.trim());
            while (results.next()) {
                valorRetorno = results.getString(1);
            }
            results.close();
            stmt.close();
            pCnx.close();
            pCnx = null;

            valorRetorno = "S";
        } catch (Exception sqle) {
            try { if (results != null) results.close(); } catch (Exception e) { ; }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { ; }
            try { if (pCnx != null) pCnx.close(); } catch (Exception e) { ; }
            pCnx = null;
            log.error("Error getConexion: ", sqle);
            valorRetorno = "N";
            enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, 
                              FarmaVariables.vCodLocal,
                              VariablesPtoVenta.vDestEmailErrorCobro, 
                              "Error de Conexion getIndLineaRAC",
                              "Error de Comunicacion getIndLineaRAC",
                              "¡No hay conexión con RAC" + "<br>" + 
                              "Inténtelo nuevamente." + "<br>" +
                              "Si persiste el error, llame a Mesa de Ayuda." + "<br>" + 
                              "IP PC: " + FarmaVariables.vIpPc + "<br>" + 
                              "Error: " + "Conexion :" + sqle.getMessage(), 
                              "");
            throw new Exception("¡No hay conexión con RAC!\n" +
                "Inténtelo nuevamente.\n" +
                "Si persiste el error, llame a Mesa de Ayuda. ");
        }
        
        return valorRetorno;
    } 
    
    void setBeanConexionRAC(String pDatosConexion){       
        if (!pDatosConexion.trim().equalsIgnoreCase("N")) {

            String[] pDatosCnx = pDatosConexion.split("@");
            
            BeanConexion beanRAC = new BeanConexion();
            beanRAC.setUsuarioBD(pDatosCnx[1].trim());                
            beanRAC.setClaveBD(pDatosCnx[2].trim());
            beanRAC.setIPBD(pDatosCnx[0].trim());
            beanRAC.setSID(pDatosCnx[3].trim());
            beanRAC.setPORT(pDatosCnx[4].trim());
            
            VariablesPtoVenta.conexionRAC = beanRAC;
        }
    }
}
