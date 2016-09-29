package mifarma.ptoventa.lectorHuella.dao;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

public class FactoryLectorHuella {
    public FactoryLectorHuella() {
        super();
    }

    public static DAOLectorHuella getDAOLectorHuella(TipoImplementacionDAO tipo) {
        DAOLectorHuella dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBLectorHuella();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
