package mifarma.farmaBean;

import mifarma.farmaUtil.Constantes;

public class RecargaMovistarBean extends EntradaBean {
    
    public RecargaMovistarBean(String pMonto, String pMensajeID, String pTelefono,
                                String pTerminal, String pComercio, String pUbicacion) {
        super();
        setTipoMensaje(Constantes.MSG_200+"");
        setTipoRecaudacion(Constantes.TIPO_RECAUD_MOVISTAR);
        
        //Campo 4 
        setMonto(pMonto);
        //Campo 11
        setAuditoria(pMensajeID);
        //Campo 41
        setTerminal(pTerminal);
        //Campo 42
        setComercio(pComercio);
        //Campo 43
        setUbicacion(pUbicacion);
        //Campo 48
        setTelefono(pTelefono);
    }
}
