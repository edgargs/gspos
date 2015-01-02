package mifarma.ptoventa.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.reference.VariablesAdministracion;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBAlertUp 
{
  
  private final Logger log = LoggerFactory.getLogger(DBAlertUp.class);
  
  private ArrayList parametros = new ArrayList();

  public DBAlertUp()
  {
  }
  
 public ArrayList getAlertaMensajes() throws Exception {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"FARMA_ALERTUP.F_CUR_ALERTA_MENSAJES(?,?)",parametros);
    return pOutParams;
  }
}
