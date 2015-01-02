package mifarma.electronico.impresion.reference;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBComprobanteElectronico {

    private static final Logger log = LoggerFactory.getLogger(DBComprobanteElectronico.class);

    public static List getDatosImpresion(String pVersionFV, String numPedidoVta, String secCompPago,
                                         boolean isReimpresion) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedidoVta);
        parametros.add(secCompPago);
        parametros.add(pVersionFV);
        if (isReimpresion)
            parametros.add("S");
        else
            parametros.add("N");
        log.info("FARMA_EPOS.IMP_COMP_ELECT(?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_EPOS.IMP_COMP_ELECT(?,?,?,?,?,?)", parametros);

    }

    public static String getMarcaLocal(String pCodGrupoCia, String pCodLocal) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        log.info("FARMA_EPOS.GET_MARCA_LOCAL(?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.GET_MARCA_LOCAL(?,?)", parametros);
    }
}
