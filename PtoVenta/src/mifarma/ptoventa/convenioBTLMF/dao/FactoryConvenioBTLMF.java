package mifarma.ptoventa.convenioBTLMF.dao;


public class FactoryConvenioBTLMF {
    
    public enum Tipo {MYBATIS};
    
    public FactoryConvenioBTLMF() {
        super();
    }
    
    public static DAOConvenioBTLMF getDAOConvenioBTLMF(Tipo tipo) {
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
    public static DAORACConvenioBTLMF getDAORACConvenioBTLMF(Tipo tipo) {
        DAORACConvenioBTLMF dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBRACConvenioBTLMF();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
    
}
