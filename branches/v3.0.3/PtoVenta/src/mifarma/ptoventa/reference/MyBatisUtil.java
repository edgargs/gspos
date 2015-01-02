package mifarma.ptoventa.reference;

import java.io.IOException;
import java.io.Reader;

import java.util.Properties;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaVariables;

import static mifarma.ptoventa.reference.VariablesPtoVenta.conexionFasa;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : MyBatisUtil.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.03.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class MyBatisUtil {
    private static SqlSessionFactory factory;
    //private static final String FarmaConstants.CONNECT_STRING_SID_W = "jdbc:oracle:thin:@//%s:%s/%s";
    //dubilluz 08.12.2014


    private MyBatisUtil() {
    }
    static {
        Reader reader = leerMyBatisConfig();

        Properties properties = new Properties();
        properties.setProperty("jdbc.driverClassName", "oracle.jdbc.driver.OracleDriver");
        String strUrl =
            String.format(FarmaConstants.CONNECT_STRING_SID_W, FarmaVariables.vIPBD, "1521", FarmaVariables.vSID);
        properties.setProperty("jdbc.url", strUrl);
        properties.setProperty("jdbc.username", FarmaVariables.vUsuarioBD);
        properties.setProperty("jdbc.password", FarmaVariables.vClaveBD);

        factory = new SqlSessionFactoryBuilder().build(reader, properties);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return factory;
    }

    public static SqlSessionFactory getFasaSqlSessionFactory() {
        Reader reader = leerMyBatisConfig();

        Properties properties = new Properties();
        properties.setProperty("jdbcFasa.driverClassName", "oracle.jdbc.driver.OracleDriver");
        String strUrl =
            String.format(FarmaConstants.CONNECT_STRING_SID_W, conexionFasa.getIPBD(), conexionFasa.getPORT(),
                          conexionFasa.getSID());
        properties.setProperty("jdbcFasa.url", strUrl);
        properties.setProperty("jdbcFasa.username", conexionFasa.getUsuarioBD());
        properties.setProperty("jdbcFasa.password", conexionFasa.getClaveBD());

        return new SqlSessionFactoryBuilder().build(reader, "fasa", properties);
    }

    public static SqlSessionFactory getAdmSqlSessionFactory() {
        Reader reader = leerMyBatisConfig();

        Properties properties = new Properties();
        properties.setProperty("jdbcApps.driverClassName", "oracle.jdbc.driver.OracleDriver");

        properties.setProperty("jdbcApps.url", FarmaVariables.conexionAPPS.getCadenaConexionW());
        properties.setProperty("jdbcApps.username", FarmaVariables.conexionAPPS.getUsuarioBD());
        properties.setProperty("jdbcApps.password", FarmaVariables.conexionAPPS.getClaveBD());

        return new SqlSessionFactoryBuilder().build(reader, "adm", properties);
    }

    private static Reader leerMyBatisConfig() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return reader;
    }

    /**
     * Factory conexion RAC
     * @autohr ERIOS
     * @since 2.3.4
     * @return
     */
    public static SqlSessionFactory getRACSqlSessionFactory() {
        Reader reader = leerMyBatisConfig();

        Properties properties = new Properties();
        properties.setProperty("jdbcRAC.driverClassName", "oracle.jdbc.driver.OracleDriver");

        properties.setProperty("jdbcRAC.url", FarmaVariables.conexionRAC.getCadenaConexionW());
        properties.setProperty("jdbcRAC.username", FarmaVariables.conexionRAC.getUsuarioBD());
        properties.setProperty("jdbcRAC.password", FarmaVariables.conexionRAC.getClaveBD());

        return new SqlSessionFactoryBuilder().build(reader, "rac", properties);
    }
}
