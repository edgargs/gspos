package mifarma.ptoventa.cnx;


import com.gs.encripta.FarmaEncripta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import java.util.StringTokenizer;

import javax.swing.JDialog;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.epos.reference.EposVariables;

import mifarma.ptoventa.caja.reference.DBCaja;

import mifarma.ptoventa.reference.BeanConexion;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import oracle.jdbc.OracleDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FarmaVentaCnxUtility {

    private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(FarmaVentaCnxUtility.class);
    private static final String CONNECT_STRING_SERVICENAME = "jdbc:oracle:thin:%s/%s@//%s:%s/%s";
    private static ArrayList prm;
    
    /**
     * Constructor
     */
    public FarmaVentaCnxUtility() {
    }

    
    public String getIndLineaRecarga()
       {
           String valorRetorno = "";
           Connection pCnx = getConexionRecarga();
		   //ERIOS 19.11.2014 Valida que exista conexion
           if(pCnx == null){
               valorRetorno = "N";   
           }else{
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
                log.error("",sqle);   
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
              
              String connect_string_thin = String.format(CONNECT_STRING_SERVICENAME, vIdUsuDB,vClaveBD,vIpServidorDB,vPUERTODB,vSidDB);
              
              Connection con;
              DriverManager.setLoginTimeout(pTimeOut);
              DriverManager.registerDriver(new OracleDriver());     
              con = DriverManager.getConnection(connect_string_thin);
              con.setAutoCommit(false);
              log.info("getConexion "+connect_string_thin);
              return con;
          } catch (Exception e) {
              log.error("Error getConexion: ",e);
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
        
		prm = new ArrayList();
        prm.add(pCodCia);
        prm.add(pCodLocal);
        prm.add(pReceiverAddress);
        prm.add(pAsunto);
        prm.add(pTitulo);
        prm.add(pMensaje);
        prm.add(pCCReceiverAddress);
        
         //ERIOS 19.11.2014 Envia correo por hilo
         new Thread(){
             public void run(){                                 
                  try
                  {
                    FarmaDBUtility.executeSQLStoredProcedure(null,
                                                             "FARMA_UTILITY.envia_correo(?,?,?,?,?,?,?)",
                                                             prm,
                                                             true);
                  } catch(Exception sql)
                  {
                    log.error("",sql);
                  }
             }
         }.start();
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
     * Obtiene usuario de conexion al TICO por local
     * @author RHERRERA
     * @since 2.4.6
     * @return
     */
    public String getCnxRemotoTICO() {
        parametros = new ArrayList();
        String pResultado = "N";

        try {
            pResultado =
                    FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA.FARMA_CNX_REMOTO.F_CNX_LOCAL_MARKET",
                                                                parametros);
            /*            
            pResultado = ""+FarmaVariables.vIpServidorTico+"@" +
                            FarmaVariables.vIdUsuTico+"@" +
                            FarmaVariables.vClaveTico+"@"+
                            FarmaVariables.vSidTico+"@"+
                            "1521@2";*/
            String[] pDatosCnx = pResultado.split("@");
            //RHERRERA 15.09.2014 lee datos del servidor TICO
            FarmaVariables.vIdUsuTico = pDatosCnx[1].trim();
            FarmaVariables.vClaveTico = pDatosCnx[2].trim();
            FarmaVariables.vSidTico = pDatosCnx[3].trim();
            
            
        } catch (Exception sqle) {
            log.error("", sqle);
            enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia,
                             FarmaVariables.vCodLocal,
                             VariablesPtoVenta.vDestEmailErrorCobro,
                             "Error de Obtener Cadena Conexion getCnxRemotoTICO",
                             "Error de Obtener Cadena Conexion getCnxRemotoTICO",
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

            String connect_string_thin = String.format(CONNECT_STRING_SERVICENAME, vIdUsuDB,vClaveBD,vIpServidorDB,vPUERTODB,vSidDB);
            
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
    
    private Connection getConexionTICO(String pDatosConexion) throws Exception {

        if (!pDatosConexion.trim().equalsIgnoreCase("N")) {

            String[] pDatosCnx = pDatosConexion.split("@");
            
            String vIpServidorDB = FarmaVariables.vIpServidorTico;//pDatosCnx[0].trim();//rherrera 22.09.2014
            String vIdUsuDB = pDatosCnx[1].trim();
            String vClaveBD = pDatosCnx[2].trim();
            String vSidDB = pDatosCnx[3].trim();
            String vPUERTODB = pDatosCnx[4].trim();
            int pTimeOut = Integer.parseInt(pDatosCnx[5].trim());

            String connect_string_thin = String.format(CONNECT_STRING_SERVICENAME, vIdUsuDB,vClaveBD,vIpServidorDB,vPUERTODB,vSidDB);
            
            setBeanConexionTICO(pDatosConexion);
            FarmaConnectionRemoto.CON_TICO = connect_string_thin ; 
            log.warn("Conexion Remota:"+FarmaConnectionRemoto.CON_TICO);
                                                                 
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
    
    /**
     * Verifica conexion con TICO
     * @aurhor RHERRERA
     * @since 2.6
     * @return
     */
    public String getIndLineaTICO() throws Exception {
        String valorRetorno = "";
        Connection pCnx = null;
        Statement stmt = null;
        ResultSet results = null;
        try {
            pCnx = getConexionTICO(getCnxRemotoTICO());
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
    
    /**
     * Verifica conexion con TICO
     * @aurhor RHERRERA
     * @since 2.6
     * @return
     */
    void setBeanConexionTICO(String pDatosConexion){       
        if (!pDatosConexion.trim().equalsIgnoreCase("N")) {

            String[] pDatosCnx = pDatosConexion.split("@");
            
            BeanConexion beanRAC = new BeanConexion();
            beanRAC.setUsuarioBD(pDatosCnx[1].trim());                
            beanRAC.setClaveBD(pDatosCnx[2].trim());
            //beanRAC.setIPBD(pDatosCnx[0].trim());
            beanRAC.setIPBD(FarmaVariables.vIpServidorTico);//rherrera 22.09.2014
            beanRAC.setSID(pDatosCnx[3].trim());
            beanRAC.setPORT(pDatosCnx[4].trim());
            
            VariablesPtoVenta.conexionTICO = beanRAC;
        }
    }  
   
    
    public boolean validarPingIP(String ip) {
        boolean val = true;
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("ping -n 1 " + ip);
            int status = p.waitFor();
            /*log.debug("***********VALIDANDO IP*************");
            log.debug(ip + " is " + "/ " + status + "-->" + 
                               (status == 0 ? "alive" : "dead"));*/
            if (status == 1)
                val = false;
            if(val)
                log.info("IP."+ip+" SI VALIDO PING ");
            else
                log.info("IP."+ip+" NO VALIDO PING ");
        } catch (Exception l) {
            log.error("Error al verificar conexcion de IP.");
            //log.error("",l);
            val = false;
        }
        return val;
    }
    public boolean esUnaDireccionIPValida(String dir) {
        StringTokenizer st = new StringTokenizer(dir, ".");
        if (st.countTokens() != 4) {
            return false;
        }
        while (st.hasMoreTokens()) {
            String nro = st.nextToken();
            if ((nro.length() > 3) || (nro.length() < 1)) {
                return false;
            }
            int nroInt = 0;
            try {
                nroInt = Integer.parseInt(nro);
            } catch (NumberFormatException s) {
                return false;
            }
            if ((nroInt < 0) || (nroInt > 255)) {
                return false;
            }
        }
        return true;
    }    
}
