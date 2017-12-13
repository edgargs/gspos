package mifarma.farmaCTL;

import java.util.HashMap;

import mifarma.farmaBean.EntradaBean;
import mifarma.farmaBean.RespuestaBean;


/**
 * @author ERIOS
 * @since 21.09.2016
 */
public class FacadeRecargas {
    
    private ContextRecargas context;
    
    public FacadeRecargas() {
        super();
    }
    
    public RespuestaBean recargaBitel(String codProveedor,
                                      HashMap<String,String> mpDatos,
                                      EntradaBean entrada) throws Exception{
        context = getProveedor(codProveedor, mpDatos);
        return context.recargaBitel(entrada);
    }
    
    public RespuestaBean recargaEntel(String codProveedor,
                                      HashMap<String,String> mpDatos,
                                      EntradaBean entrada) throws Exception{
        context = getProveedor(codProveedor, mpDatos);
        return context.recargaEntel(entrada);
    }
    
    public RespuestaBean anularRecargaEntel(String codProveedor,
                                      HashMap<String,String> mpDatos,
                                      EntradaBean entrada) throws Exception{
        context = getProveedor(codProveedor, mpDatos);
        return context.anularRecargaEntel(entrada);
    }
    
    public RespuestaBean anularRecargaBitel(String codProveedor,
                                      HashMap<String,String> mpDatos,
                                      EntradaBean entrada) throws Exception{
        context = getProveedor(codProveedor, mpDatos);
        return context.anularRecargaBitel(entrada);
    }

    private ContextRecargas getProveedor(String codProveedor, HashMap<String,String> mpDatos) {
        StrategyRecargas strategy;
        if(codProveedor != null && "FULL".equals(codProveedor)){
            strategy = new ClienteFullCarga(
                                        mpDatos.get("numeroPOS"), 
                                        mpDatos.get("clavePOS"), 
                                        mpDatos.get("key3Des"),
                                        mpDatos.get("servidor"),
                                        mpDatos.get("puerto"));
        }else{
            strategy = new ClienteSIX(
                                    mpDatos.get("IpPC"), // TABLA                                    
                                    mpDatos.get("usuarioSIX"), 
                                    mpDatos.get("claveSIX"),  
                                    mpDatos.get("IP_SIX"), // PROPERTIES                                    
                                    ClienteSIX.CLIENTE_SIX_CONST_1 , // CONSTANTES
                                    ClienteSIX.CLIENTE_SIX_CONST_2 , 
                                    ClienteSIX.CLIENTE_SIX_CONST_3 , 
                                    ClienteSIX.CLIENTE_SIX_CONST_4
                                    );
        } 
        return new ContextRecargas(strategy);
    }
}
