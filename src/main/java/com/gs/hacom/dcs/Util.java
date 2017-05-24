/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
	2017/02/27 Edgar Rios
	  Se agrega el metodo TimeEpoch().
  =======================================================================================
*/
package com.gs.hacom.dcs;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase con metodos utiles para el programa.
 * 
 * @version 1.0
 * @since 2017/01/01
 */
public class Util {

	private static final Logger logger = LogManager.getLogger(Util.class);
	
	public static String IPBD;
	public static String SID;
	public static String UsuarioBD;
	public static String ClaveBD;
	public static String PuertoBD;
    
	//private int parseState = 2080;
	//private byte[] mobileIDBytes = null;
    //private String mobileIDString = null;
    //private int mobileIDTypeLen = -1;
    //private int mobileIDType = -1;
    
    public static String[] UNIQUEID_PREFIX = null;
    public static double MINIMUM_SPEED_KPH = 0.0;
    public static long SIMEVENT_DIGITAL_INPUTS = 255;
    public static double MAXIMUM_HDOP = -1.0;
    public static boolean ESTIMATE_ODOMETER = false;
    

	



	/**
	 * Recupera parametros de conexion a base de datos.
	 * @param properties Propiedades del sistema.
	 */
    public static void iniciaConfiguracion(Properties properties) {
        IPBD = properties.getProperty("IPDB");
        SID = properties.getProperty("NameDB");
        UsuarioBD = properties.getProperty("UserDB");
        ClaveBD = properties.getProperty("PasswordDB");        
        PuertoBD= properties.getProperty("PortDB");        
    }
    
    /**
     * Lee archivo de propiedades.
     * @param filename Nombre del archivo por defecto.
     * @return Propiedades del sistema.
     * @throws IOException Error de acceder al archivo.
     */
    public static Properties loadFromFile() throws IOException {
        
    	//2017-05-23 Recupera el parametro jvm.
    	String value = System.getProperty("config.program");
    	if (value == null) {
    		value = System.getenv("configProgram");
    	}
        if (value != null) {
        	Path configLocation = Paths.get(value);
            try (InputStream stream = Files.newInputStream(configLocation)) {
                Properties config = new Properties();
                config.load(stream);
                logger.debug(config);
                return config;
            }
        } else {
        	value = "config.properties";
        	try (Reader reader = Resources.getResourceAsReader(value)) {
                Properties config = new Properties();
                config.load(reader);
                logger.debug(config);
                return config;
            }
        }
    }
    
    
    

    
    /**
     * Se obtiene el valor de tiempo epoch
     * @return Tiempo epoch
     */
    public static Long TimeEpoch() {
		long epoch = System.currentTimeMillis() / 1000;
		return epoch;
	}
}

