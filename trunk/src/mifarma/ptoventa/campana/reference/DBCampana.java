package mifarma.ptoventa.campana.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;

import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBCampana {

    private static final Log log = LogFactory.getLog(DBConvenio.class);
    
    private static ArrayList parametros = new ArrayList();

    public DBCampana() {
    }
    
    /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  22.08.08
     * @param pTableModel
     * @throws SQLException
     */
    public static void listaCamposCampana(FarmaTableModel pTableModel) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(VariablesCampana.vCodCampana);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                "PTOVENTA_CAMP.CAMP_LISTA_CAMPANA_CAMPOS(?)",parametros,false);
    }
    
    /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  22.08.08
     * @param pDniCliente
     * @param pNomcliente
     * @param pApePatCliente
     * @param pApeMatCliente
     * @param pTelefono
     * @param pNumCMP
     * @param pMedico
     * @param pSexo
     * @param pFecNacimiento
     * @throws SQLException
     */
    public static void agregarClienteCampana(String pCodCampana,
                                             String pCodCupon,
                                             String pCodCli,
                                             String pDniCliente,
                                             String pNomcliente,
                                             String pApePatCliente,
                                             String pApeMatCliente,
                                             String pTelefono,
                                             String pNumCMP,
                                             String pMedico,
                                             String pSexo, 
                                             String pFecNacimiento) throws SQLException
    {
      parametros = new ArrayList();
      
      //Codigos
      parametros.add(VariablesCampana.vCodGrupoCia);
      parametros.add(pCodCampana);
      parametros.add(VariablesVentas.vNum_Ped_Vta);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodCupon);
      parametros.add(pCodCli);
      
      //Datos del cliente
      parametros.add(pDniCliente);
      parametros.add(pNomcliente);
      parametros.add(pApePatCliente);
      parametros.add(pApeMatCliente);
      parametros.add(pTelefono);
      parametros.add(pNumCMP);
      parametros.add(pMedico);
      parametros.add(pSexo);
      parametros.add(pFecNacimiento);
      
      //Usuario creador
       parametros.add(FarmaVariables.vNomUsu);
      FarmaDBUtility.executeSQLStoredProcedure(null,
         "PTOVENTA_CAMP.CAMP_AGREGAR_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
         ,parametros,false);
    }
}
