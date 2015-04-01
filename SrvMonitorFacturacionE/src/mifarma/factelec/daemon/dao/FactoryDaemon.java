package mifarma.factelec.daemon.dao;


public class FactoryDaemon {
    public enum Tipo {MYBATIS}
    
    public FactoryDaemon() {
        super();
    }
    
    public static DAODaemon getDAODaemon(Tipo tipo) {
        DAODaemon dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBDaemon();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
