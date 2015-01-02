package mifarma.ptoventa.administracion.producto.dao;

import mifarma.ptoventa.reference.TipoImplementacionDAO;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FactoryProducto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CVILCA      01.10.2013   Creación<br>
 * <br>
 * @author Christian Vilca Quiros<br>
 * @version 1.0<br>
 *
 */
public class FactoryProducto {
    public static DAOProducto getDAOCaja(TipoImplementacionDAO tipo) {
        DAOProducto dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBProducto();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
