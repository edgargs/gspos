package mifarma.ptoventa.convenioBTLMF.dao;

import mifarma.ptoventa.reference.TipoImplementacionDAO;


public class FactoryConvenioBTLMF {

    public FactoryConvenioBTLMF() {
        super();
    }

    public static DAOConvenioBTLMF getDAOConvenioBTLMF(TipoImplementacionDAO tipo) {
        DAOConvenioBTLMF dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBConvenioBTLMF();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }

    /**
     * Obtiene conexion al RAC
     * @author ERIOS
     * @since 2.4.4
     */
    public static DAORACConvenioBTLMF getDAORACConvenioBTLMF(TipoImplementacionDAO tipo) {
        DAORACConvenioBTLMF dao;
        switch (tipo) {            
        case FRAMEWORK:
            dao = new FMRACConvenioBTLMF();
            break;
        case MYBATIS:
            dao = new MBRACConvenioBTLMF();
            break;
        case GESTORTX:
            dao = new GTRACConvenioBTLMF();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }

}
