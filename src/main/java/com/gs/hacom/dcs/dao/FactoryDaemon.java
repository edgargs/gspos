/* 
  =======================================================================================
    Copyright 2017, HACOM S.A.C.
    Proyecto: MATRIX - Sistema de Optimizacion de Transporte Urbano.
  =======================================================================================
	Change History:
  =======================================================================================
*/
package com.gs.hacom.dcs.dao;

import java.util.Properties;

/**
 * Factoria de comunicacion con base de datos.
 * 
 * @version 1.0
 * @since 2017/01/01
 * @see com.gs.hacom.dcs.dao.JDBCDaemon
 * @see com.gs.hacom.dcs.dao.MBDaemon
 */
public class FactoryDaemon {
    public enum Tipo {MYBATIS, JDBC}
    
    public FactoryDaemon() {
        super();
    }
    
    /**
     * Obtiene la forma de comunicacion a la base de datos.
     * @param tipo Tipo de conexion.
     * @param propDatabase Propiedades del sistema.
     * @return Comunicacion con la base de datos.
     */
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
