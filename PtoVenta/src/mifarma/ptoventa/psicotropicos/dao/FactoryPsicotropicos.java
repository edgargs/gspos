package mifarma.ptoventa.psicotropicos.dao;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FactoryPsicotropicos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LLEIVA      23.Sep.2013   Creación<br>
 * <br>
 * @author Luis Leiva Bazán<br>
 * @version 1.0<br>
 *
 */
public class FactoryPsicotropicos {
    public enum Tipo {
        FRAMEWORK,
        MYBATIS
    }

    public static DAOPsicotropicos getDAOPsicotropicos(Tipo tipo) {
        DAOPsicotropicos dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBPsicotropicos();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
