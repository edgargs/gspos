package mifarma.ptoventa.stocknegativo.dao;

public class FactoryStockNegativo {
    public enum Tipo {
        FRAMEWORK,
        MYBATIS
    }

    public FactoryStockNegativo() {
        super();
    }

    public static DaoStockNegativo getDaoStockNegativo(Tipo tipo) {
        DaoStockNegativo dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBStockNegativo();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
