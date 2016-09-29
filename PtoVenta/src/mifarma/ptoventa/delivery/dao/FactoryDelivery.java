package mifarma.ptoventa.delivery.dao;

import mifarma.ptoventa.reference.TipoImplementacionDAO;

public class FactoryDelivery {
    public static DAORACDelivery getDAORACDelivery(TipoImplementacionDAO tipo) {
        DAORACDelivery dao;
        switch (tipo) {            
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBRACDelivery();
            break;
        case GESTORTX:
            dao = new GTRACDelivery();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
