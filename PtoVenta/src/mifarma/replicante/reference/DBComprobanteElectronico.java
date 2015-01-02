package mifarma.replicante.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import mifarma.common.FarmaDBUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBComprobanteElectronico {
    
    private static final Logger log = LoggerFactory.getLogger(DBComprobanteElectronico.class);
    
    public static List obtieneReenvioComprobantes(
                                                  String pCodGrupoCia,
                                                  String pCodCia,
                                                  String pCodLocal,
                                                  String pTipoCompE, 
                                                  String pSerieCompE,
                                                  String pNumCompE) throws SQLException {
      ArrayList parametros = new ArrayList();
      parametros.add(pCodGrupoCia);
      parametros.add(pCodCia);
      parametros.add(pCodLocal);
      parametros.add(pTipoCompE);
      parametros.add(pSerieCompE);
      parametros.add(pNumCompE);
      log.info("PTOVENTA_REPLICANTE.GET_REENVIO_COMP(?,?,?,?,?,?)"+parametros);
      
      List pArrayList;
      pArrayList = FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_REPLICANTE.GET_REENVIO_COMP(?,?,?,?,?,?)",parametros);
      return pArrayList;
    }
}
