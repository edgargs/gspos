package com.gs.pos.util;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
    
    private static SqlSessionFactory factory;
    
    public MyBatisUtil() {
        super();
    }
    
    static {
        Reader reader = leerMyBatisConfig();
        
        String vIPBD = "localhost";
        String vPORT = "5432";
        String vSID = "gsPos";
        String vUsuarioBD = "gsPos";
        String vClaveBD = "gsPos";
        
        Properties properties = new Properties();
        properties.setProperty("jdbc.driverClassName", "org.postgresql.Driver");
        String strUrl = "jdbc:postgresql://" + vIPBD + ":"+vPORT+"/" + vSID;
        properties.setProperty("jdbc.url", strUrl);
        properties.setProperty("jdbc.username", vUsuarioBD);
        properties.setProperty("jdbc.password", vClaveBD);

        factory = new SqlSessionFactoryBuilder().build(reader, properties);
    }
    
    private static Reader leerMyBatisConfig()
    {   Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return reader;
    }
    
    public static SqlSessionFactory getSqlSessionFactory() {
        return factory;
    }
}
