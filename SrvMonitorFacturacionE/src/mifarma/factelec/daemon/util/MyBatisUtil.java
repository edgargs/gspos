package mifarma.factelec.daemon.util;

import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MyBatisUtil.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class MyBatisUtil {
    private static Logger log = LoggerFactory.getLogger(MyBatisUtil.class);
    private static final String CONNECT_STRING_SERVICENAME = "jdbc:oracle:thin:@//%s:%s/%s";
    
    private static SqlSessionFactory factory = null;
    private static HashMap<String,SqlSessionFactory> lstFactory = new HashMap<>();
    private static String IPBD;
    private static String SID;
    private static String UsuarioBD;
    private static String ClaveBD;
    private static String PuertoBD;
            
    private MyBatisUtil() {        
    }

    public static SqlSessionFactory getSqlSessionFactory() {        
        if(factory==null){
            synchronized(MyBatisUtil.class){
                if(factory==null){
                    Properties properties = new Properties();
                    properties.setProperty("jdbc.driverClassName", "oracle.jdbc.driver.OracleDriver");
                    String strUrl = String.format(CONNECT_STRING_SERVICENAME, IPBD , PuertoBD , SID);
                    properties.setProperty("jdbc.url", strUrl);
                    properties.setProperty("jdbc.username", UsuarioBD);
                    properties.setProperty("jdbc.password", ClaveBD);
                    
                    Reader reader = leerMyBatisConfig();
                    
                    factory = new SqlSessionFactoryBuilder().build(reader, properties);
                }
            }
        }
        return factory;
    }

    public static Reader leerMyBatisConfig()
    {   Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return reader;
    }
    
    public static void iniciaConfiguracion(Properties farmasixdaemon) {
        IPBD = farmasixdaemon.getProperty("IPBD");
        SID = farmasixdaemon.getProperty("SID");
        UsuarioBD = farmasixdaemon.getProperty("UsuarioBD");
        ClaveBD = farmasixdaemon.getProperty("ClaveBD");        
        PuertoBD= farmasixdaemon.getProperty("PUERTO");        
    }
    
    public static SqlSessionFactory getSqlSessionFactory(BeanConexion conexion) {        
     
        return getSqlSessionFactory(conexion, null);
    }

    public static SqlSessionFactory getSqlSessionFactory(String pCodCia,BeanConexion conexion) {        
        SqlSessionFactory factory = null;
        if(!lstFactory.containsKey(pCodCia)){            
            synchronized(MyBatisUtil.class){
                if(!lstFactory.containsKey(pCodCia)){
                    Properties properties = new Properties();
                    properties.setProperty("jdbc.driverClassName", "oracle.jdbc.driver.OracleDriver");
                    String strUrl = String.format(CONNECT_STRING_SERVICENAME, conexion.getIPBD() , conexion.getPuertoBD() , conexion.getSID());
                    properties.setProperty("jdbc.url", strUrl);
                    properties.setProperty("jdbc.username", conexion.getUsuarioBD());
                    properties.setProperty("jdbc.password", conexion.getClaveBD());
                    
                    Reader reader = leerMyBatisConfig();
                    
                    factory = new SqlSessionFactoryBuilder().build(reader, properties);
                    lstFactory.put(pCodCia, factory);
                }
            }            
        }

        factory = lstFactory.get(pCodCia);                   
        
        return factory;
    }

    public static SqlSessionFactory getSqlSessionFactory(BeanConexion conexion, String pEnviroment) {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driverClassName", "oracle.jdbc.driver.OracleDriver");
        String strUrl = String.format(CONNECT_STRING_SERVICENAME, conexion.getIPBD() , conexion.getPuertoBD() , conexion.getSID());
        properties.setProperty("jdbc.url", strUrl);
        properties.setProperty("jdbc.username", conexion.getUsuarioBD());
        properties.setProperty("jdbc.password", conexion.getClaveBD());
        
        Reader reader = leerMyBatisConfig();
        SqlSessionFactory factory;
        if(pEnviroment == null){
             factory = new SqlSessionFactoryBuilder().build(reader, properties);
        }else{
            factory = new SqlSessionFactoryBuilder().build(reader, pEnviroment, properties);
        }
        return factory;
    }
    
    public static SqlSessionFactory getSqlPtoVentaSessionFactory(BeanConexion conexion) {        
     
        Properties properties = new Properties();
        properties.setProperty("jdbcPto.driverClassName", "oracle.jdbc.driver.OracleDriver");
        String strUrl = String.format(CONNECT_STRING_SERVICENAME, conexion.getIPBD() , conexion.getPuertoBD() , conexion.getSID());
        properties.setProperty("jdbcPto.url", strUrl);
        properties.setProperty("jdbcPto.username", conexion.getUsuarioBD());
        properties.setProperty("jdbcPto.password", conexion.getClaveBD());
        
        Reader reader = leerMyBatisConfig();
        String pEnviroment = "ptoventa";
        
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, pEnviroment, properties);
        return factory;
    }    
}
