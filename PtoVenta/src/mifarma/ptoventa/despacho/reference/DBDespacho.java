package mifarma.ptoventa.despacho.reference;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaDBUtility;

import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBDespacho {
    
    private static final Logger log = LoggerFactory.getLogger(DBDespacho.class);
    
    /**
     * Obtiene listado de impresoras por pisa de despacho
     * @author KMONCADA
     * @since 12.05.2016
     * @param pCodGrupoCia
     * @param pCodLocal
     * @param pNroProforma
     * @return ArrayList de Map, con las impresoras y piso que imprimira
     * @throws Exception
     */
    public static List obtenerImpresorasParaDespacho(String pCodGrupoCia, String pCodLocal, String pNroPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNroPedVta);
        log.info("PTOVENTA_DESPACHO.F_OBTENER_IMPR_DESPACHO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_DESPACHO.F_OBTENER_IMPR_DESPACHO(?,?,?)", parametros);
    }
    
    public static List obtenerComandaDespacho(String pCodGrupoCia, String pCodLocal, String pNroPedVta, String nroPiso, int nroComanda, int totalComanda) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNroPedVta);
        parametros.add(nroPiso);
        parametros.add(nroComanda+"");
        parametros.add(totalComanda+"");
        log.info("PTOVENTA_DESPACHO.F_OBTENER_COMANDA_DESPACHO(?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_DESPACHO.F_OBTENER_COMANDA_DESPACHO(?,?,?,?,?,?)", parametros);

    }
    
    public static void obtenerPisoDespacho(String pCodGrupoCia, String pCodLocal, ArrayList pLstPisos)throws Exception{
        ArrayList parametros = new ArrayList();
        log.info("PTOVENTA_DESPACHO.F_LISTA_PISOS_DESPACHO" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLstPisos, "PTOVENTA_DESPACHO.F_LISTA_PISOS_DESPACHO", parametros);
    }
}
