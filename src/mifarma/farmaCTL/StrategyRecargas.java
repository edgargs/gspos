package mifarma.farmaCTL;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;

/**
 * @author ERIOS
 * @since 21.09.2016
 */
public interface StrategyRecargas {
    public RespuestaBean recargaBitel(EntradaBean entradaBean) throws Exception ;

    public RespuestaBean recargaEntel(EntradaBean entradaBean) throws Exception ;

    public RespuestaBean anularRecargaEntel(EntradaBean entradaBean) throws Exception;

    public RespuestaBean anularRecargaBitel(EntradaBean entradaBean) throws Exception;
}
