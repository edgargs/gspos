package mifarma.ptoventa.fidelizacion.dao;

import java.util.ArrayList;
import java.util.List;

import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAOFidelizacion extends DAOTransaccion {
    
    public List getListaTipoDocumento() throws Exception;
    public String getCodigoTipoDocumentoAfiliado(String nroDocumentoIdentidad) throws Exception;
}
