package mifarma.ptoventa.caja.dao;

import mifarma.ptoventa.reference.TipoImplementacionDAO;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FactoryCaja.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      16.07.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FactoryCaja {
    public static DAOCaja getDAOCaja(TipoImplementacionDAO tipo) {
        DAOCaja dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBCaja();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
