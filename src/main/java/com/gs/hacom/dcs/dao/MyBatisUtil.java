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

public class MyBatisUtil {
	
	private static final Logger logger = LogManager.getLogger(MyBatisUtil.class);
    
    private static SqlSessionFactory factory = null;
                
    private MyBatisUtil() {        
    }

    public static SqlSessionFactory getSqlSessionFactory() {        
        if(factory==null){
            synchronized(MyBatisUtil.class){
                if(factory==null){
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