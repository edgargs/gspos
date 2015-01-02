package mifarma.electronico.impresion.dao;


public class FactoryDocumentoElectronico {

    public enum Tipo {
        FRAMEWORK,
        MYBATIS
    }

    public FactoryDocumentoElectronico() {
        super();
    }

    public static DAOComprobanteElectronico getDAOComprobanteElectronico(Tipo tipo) {
        DAOComprobanteElectronico dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBComprobanteElectronico();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
