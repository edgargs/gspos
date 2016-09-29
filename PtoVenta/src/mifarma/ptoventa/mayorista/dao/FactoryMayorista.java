package mifarma.ptoventa.mayorista.dao;

import mifarma.ptoventa.reference.TipoImplementacionDAO;

public class FactoryMayorista {
    public static DAOMayorista getDAOMayorista(TipoImplementacionDAO tipo) {
        DAOMayorista dao;
        switch(tipo){
            case FRAMEWORK: 
                dao = null; 
                break;
            case MYBATIS:
                dao = new MBMayorista(); 
                break;
            case GESTORTX:
                dao = null; 
                break;
            default:
                dao = null;
                break;
        }
        return dao;
    }
}
