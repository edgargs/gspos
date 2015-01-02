package mifarma.ptoventa.recaudacion.dao;


public class FactoryRecaudacion {
    public enum Tipo {
        MYBATIS
    }

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
