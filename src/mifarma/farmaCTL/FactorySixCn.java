package mifarma.farmaCTL;

import com.novatronic.components.sixclient.tcp.ClienteSIXConnectionFactory;
import com.novatronic.components.sixclient.tcp.exception.ClienteSIXConnectionException;

import java.util.Properties;

class FactorySixCn{
    
    private static Properties POOL;
   // private static Properties CONF;
      
    static{
        POOL = new Properties();
        POOL.setProperty("name", "SIX"); 
        POOL.setProperty("max", "1");
        POOL.setProperty("min", "1");
        POOL.setProperty("interval", "10");
        POOL.setProperty("validate", "true");
        POOL.setProperty("containment", "true");
        POOL.setProperty("containment_timeout", "30");
        POOL.setProperty("timeDestroyConnect", "10");
        
        
       /* CONF = new Properties();
        //CONF.setProperty("cifrado", "true");
       // CONF.setProperty("masterkey", "010IsoType.LLVAR, 160405060708");
        //CONF.setProperty("masterkey", "010IsoType.LLVAR, 1604050607080910111213141516");
        //Datos del servidor
        CONF.setProperty("hostname", "prueba");
        CONF.setProperty("username", "prueba");
        CONF.setProperty("password", "prueba");
        CONF.setProperty("hostdest", "172.30.10.78");
        CONF.setProperty("portdest", "2005");
        CONF.setProperty("procdest", "FSISOINT");
        CONF.setProperty("typesixdrv", "estandar");
        CONF.setProperty("loggerFile", "log4j.properties");*/
    }
    
    static private ClienteSIXConnectionFactory factory;
    
    public static ClienteSIXConnectionFactory getInstance(Properties CONF) throws ClienteSIXConnectionException{
        if(factory==null){
            // En la línea siguente debe proporcionar la variable pool en null
            // para crear conexiones en demanda
            factory = ClienteSIXConnectionFactory.createConnectionFactory(CONF,null);
        }
        return factory;
    }

}