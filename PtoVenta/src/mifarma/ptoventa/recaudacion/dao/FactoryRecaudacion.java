package mifarma.ptoventa.recaudacion.dao;

import mifarma.ptoventa.ce.dao.DAOCajaElectronica;
import mifarma.ptoventa.ce.dao.FMCajaElectronica;
import mifarma.ptoventa.ce.dao.FactoryCajaElectronica;
import mifarma.ptoventa.ce.dao.MBCajaElectronica;

public class FactoryRecaudacion {
    public enum Tipo {MYBATIS}
    
    public FactoryRecaudacion() {
        super();
    }
    
    public static DAORecaudacion getDAORecaudacion(Tipo tipo) {
        DAORecaudacion dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBRecaudacion();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
