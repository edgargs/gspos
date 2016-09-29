package mifarma.ptoventa.reference;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBAlertUp {

    private final Logger log = LoggerFactory.getLogger(DBAlertUp.class);

    private ArrayList parametros = new ArrayList();

    public DBAlertUp() {
    }

    public ArrayList getAlertaMensajes() throws Exception {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "FARMA_ALERTUP.F_CUR_ALERTA_MENSAJES(?,?)",
                                                          parametros);
        return pOutParams;
    }
    
    public int getPermiteAlerta() throws Exception {
        
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_ALERTUP.F_PERMITE_ALERTA_IP(?,?)",
                                                          parametros);
    }
}
