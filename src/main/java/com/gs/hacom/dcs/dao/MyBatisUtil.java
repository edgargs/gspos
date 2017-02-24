/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.Util;

/**
 * Metodos utiles de Mybatis.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
public class MyBatisUtil {
	
	private static final Logger logger = LogManager.getLogger(MyBatisUtil.class);
    
    private static SqlSessionFactory factory = null;
                
    private MyBatisUtil() {        
    }

    /**
     * Crea una session (conexion) a la base de datos.
     * @return Session creada.
     */
    public static SqlSessionFactory getSqlSessionFactory() {        
        if(factory==null){
            synchronized(MyBatisUtil.class){
                if(factory==null){
                	logger.info("Crea nueva conexion a base de datos.");
                    Properties properties = new Properties();
                    properties.setProperty("jdbc.driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String strUrl = "jdbc:sqlserver://" + Util.IPBD + ":"+Util.PuertoBD+";databaseName=" + Util.SID;
                    properties.setProperty("jdbc.url", strUrl);
                    properties.setProperty("jdbc.username", Util.UsuarioBD);
                    properties.setProperty("jdbc.password", Util.ClaveBD);
                    
                    Reader reader = leerMyBatisConfig();
                    
                    factory = new SqlSessionFactoryBuilder().build(reader, properties);
                }
            }
        }
        return factory;
    }

    /**
     * Recupera la configuracion de Mybatis.
     * @return Configuracion de Mybatis.
     */
    public static Reader leerMyBatisConfig()
    {   Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return reader;
    }
    
}