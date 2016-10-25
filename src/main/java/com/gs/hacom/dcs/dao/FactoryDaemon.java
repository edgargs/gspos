package com.gs.hacom.dcs.dao;

import java.util.Properties;

public class FactoryDaemon {
    public enum Tipo {MYBATIS, JDBC}
    
    public FactoryDaemon() {
        super();
    }
    
    public static DAODaemon getDAODaemon(Tipo tipo, Properties propDatabase) {
        DAODaemon dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBDaemon(propDatabase);
            break;
        case JDBC:
            dao = new JDBCDaemon(propDatabase);
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
