package mifarma.farmaCTL;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;

/**
 * @author ERIOS
 * @since 21.09.2016
 */
public class ContextRecargas {
    
    private StrategyRecargas strategy;
    
    public ContextRecargas(StrategyRecargas strategy) {
        this.strategy = strategy;
    }
    
    public RespuestaBean recargaBitel(EntradaBean entrada) throws Exception{
        return strategy.recargaBitel(entrada);
    }

    RespuestaBean recargaEntel(EntradaBean entrada) throws Exception{
        return strategy.recargaEntel(entrada);
    }

    RespuestaBean anularRecargaEntel(EntradaBean entrada) throws Exception{
        return strategy.anularRecargaEntel(entrada);
    }

    RespuestaBean anularRecargaBitel(EntradaBean entrada) throws Exception{
        return strategy.anularRecargaBitel(entrada);
    }
}
