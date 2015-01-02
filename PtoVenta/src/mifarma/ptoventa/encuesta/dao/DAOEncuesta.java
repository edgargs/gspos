package mifarma.ptoventa.encuesta.dao;

import java.util.List;

import mifarma.ptoventa.encuesta.modelo.BeanEncuesta;
import mifarma.ptoventa.encuesta.modelo.BeanPreguntaEncuesta;
import mifarma.ptoventa.reference.DAOTransaccion;


public interface DAOEncuesta extends DAOTransaccion {

    public List<BeanEncuesta> verificaEncuesta(String pCodProd) throws Exception;

    public List<BeanPreguntaEncuesta> obtenerPreguntas(int pCodigoEncueta) throws Exception;

    public void grabaDetalleEncuesta(String pCodGrupoCia, String strCodCia, String strCodLocal, int secuencial,
                                     String pCodProd, int vencuesta, int vsecpregunta,
                                     String vrespuesta) throws Exception;

    public int grabaCabEncuesta(String pCodGrupoCia, String strCodCia, String strCodLocal, String pUsuario,
                                String pCodProd, int vencuesta) throws Exception;


}
