package mifarma.ptoventa.lealtad.dao;

import java.util.List;
import java.util.Map;

import mifarma.ptoventa.reference.BeanResultado;
import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAOLealtad extends DAOTransaccion {
    
    public int verificaAcumulaX1(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                String pCodProd) throws Exception;
    
    public List<BeanResultado> listaAcumulaX1(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                String pCodProd) throws Exception;
    
    public List voucherAcumulaX1(String pCodGrupoCia, String strCodCia, String strCodLocal, 
                                String pCodCamp, String pDniCli, String pCodProd) throws Exception;
    
    public String registrarInscripcionX1(String pDniCli, String pCodMatrizAcu, String vIdUsu);

    public String indImpresionVoucherX1();

    public String obtenerParametrosVenta(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception;

    public void actualizarPedido(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta, String pIdTransaccion,
                                 String pNumAutorizacion, String pIdUsu) throws Exception;

    public void eliminaProdBonificacion(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception;

    public void descartarPedido(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception;

    public String getSaldoPuntos(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception;

    public Map getPuntosMaximo(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception;
    
    public String getIndicadoresPuntos(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception;

    public String verificaUsoNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta, String pTipoBusqueda) throws Exception;
    
    public String verificaFechaNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta, String pFechaNCR) throws Exception;

    public String verificaCreditoNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta, String pFechaNCR) throws Exception;

    public String getMontoNCR(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVtaNCR) throws Exception;

    public String obtenerIndicadoresVenta(String pCodGrupoCia, String strCodCia, String strCodLocal, String pNumPedVta) throws Exception;

    public String getMultiploPtos(String pCodGrupoCia, String strCodCia, String strCodLocal) throws Exception;    
    
    public void registrarInscripcionTurno(String pCodGrupoCia, String strCodCia, String strCodLocal, String pSecMovCaja, String pNroTarjetaFidelizado, String pIdUsu) throws Exception;

    public String getInscripcionTurno(String pCodGrupoCia, String strCodCia, String strCodLocal, String pSecMovCaja) throws Exception; 
}
