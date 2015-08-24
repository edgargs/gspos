package mifarma.ptoventa.convenio.reference;

import java.sql.SQLException;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityConvenio {
    public UtilityConvenio() {
    }

    private static final Logger log = LoggerFactory.getLogger(UtilityConvenio.class);

    public static boolean getIndClienteConvActivo(JDialog pDialog, Object pObj, String pCodConvenio,
                                                  String pDniCliente, String pCodCli) throws SQLException {
        String vRpta = "";
        boolean flag = false;
        //Devuelve E: error, S: activo, N: inactivo
        vRpta = DBConvenio.getIndClienteConvenioActivo(pCodConvenio, pDniCliente, "S", pCodCli.trim());

        if (vRpta.equalsIgnoreCase("E")) {
            flag = false;
            //FarmaUtility.showMessage(pDialog,"Error al verificar el estado del convenio.\nVolver a Intentar.",pObj);
        } else if (vRpta.equalsIgnoreCase("S")) {
            flag = true;
        } else if (vRpta.equalsIgnoreCase("N")) {
            flag = false;
            FarmaUtility.showMessage(pDialog, "El Convenio no está Activo.", pObj);
        }

        return flag;
    }


    public static boolean getIndValidaMontoConvenio(JDialog pDialog, Object pObj, String pCodConvenio,
                                                    String pDniCliente, double pMonto,
                                                    String pCodCli) throws SQLException {
        String vRpta = "";
        boolean flag = false;
        vRpta = DBConvenio.getIndValidaMontoConvenio(pCodConvenio, pDniCliente, pMonto, "S", pCodCli);

        if (vRpta.equalsIgnoreCase("E")) {
            flag = false;
            //FarmaUtility.showMessage(pDialog,"Error al verificar el monto disponible.\nVolver a Intentar.",pObj);
        } else if (vRpta.equalsIgnoreCase("S")) {
            //Monto OK
            flag = true;
        } else if (vRpta.equalsIgnoreCase("N")) {
            flag = false;
            FarmaUtility.showMessage(pDialog, "Crédito insuficiente.", pObj);
        }

        return flag;

    }

}
