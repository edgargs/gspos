package mifarma.ptoventa.fidelizacion.reference;

import mifarma.ptoventa.fidelizacion.dao.DAOFidelizacion;
import mifarma.ptoventa.fidelizacion.dao.FMFidelizacion;
import mifarma.ptoventa.fidelizacion.dao.MBFidelizacion;
import mifarma.ptoventa.reference.TipoImplementacionDAO;

public class FactoryFidelizacion {
    
    public FactoryFidelizacion() {
        super();
    }
    
    public static DAOFidelizacion getDAOFidelizacion(TipoImplementacionDAO tipo, boolean isConecRac) {
        
        DAOFidelizacion dao;
        switch (tipo) {            
        // CONEXION AL LOCAL 
        case FRAMEWORK:
            dao = new FMFidelizacion();
            break;
        // CONEXION AL RAC MEDIANTE EL GESTOR
        case MYBATIS:
            dao = new MBFidelizacion(isConecRac);
            break;
        default:
            dao = null;
            break;
        }
        
        return dao;
    }
}
