package mifarma.ptoventa.reportes.dao;

import java.util.List;

import mifarma.ptoventa.reference.DAOTransaccion;

public interface DAOReportes extends DAOTransaccion {
    
    public List obtenerPeriodoReporteGigantes(String pCodGrupoCia, String pCodLocal)throws Exception;
    public String obtenerInfoComisionGigante(String tipoComision) throws Exception;
    public List obtenerResumenComisionGigante(String pCodGrupoCia, String pCodLocal, String pMes)throws Exception;
    public void procesarComisionGigante(String pCodGrupoCia, String pCodLocal, String pMes)throws Exception;
}
