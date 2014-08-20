package mifarma.transf.daemon.dao;


public class FactoryPtoVenta {
    public enum Tipo {MYBATIS}
    
    public FactoryPtoVenta() {
        super();
    }
    
    public static DAOPtoVenta detDAOPtoVenta(Tipo tipo){
        DAOPtoVenta dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBPtoVenta();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
