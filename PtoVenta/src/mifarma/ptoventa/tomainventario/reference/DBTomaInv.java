package mifarma.ptoventa.tomainventario.reference;

import java.sql.SQLException;
import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.tomainventario.DlgIngresoCodigoBarra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBTomaInv {
	private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(DBTomaInv.class);
	public DBTomaInv() {
	}

	public static void getListaLabs(FarmaTableModel pTableModel) throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_LABS", parametros, true);
	}

	public static String generarCabTomaInv(String pCantLab, String pTipTomaInv, String pIdUsu) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pCantLab.trim());
		parametros.add(pTipTomaInv.trim());
    /**
     * Modificado : usuario quien se logueo
     * @author : dubilluz
     * @since  : 20.07.2007
     */
     parametros.add(pIdUsu.trim());
     log.debug("valor "+parametros);
		//parametros.add(FarmaVariables.vIdUsu.trim());
		return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_INV.TI_GUARDAR_CAB_TI(?,?,?,?,?)", parametros);
	}
  
	public static void generarDetTotTomaInv(String pSecToma , String pIdUsu) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pSecToma.trim());
    /**
     * Modificado : usuario quien se logueo
     * @author : dubilluz
     * @since  : 20.07.2007
     */
     parametros.add(pIdUsu.trim());
     log.debug("valor "+parametros);
		//parametros.add(FarmaVariables.vIdUsu.trim());
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_GUARDAR_DET_TOT_TI(?,?,?,?)", parametros,false);
	}
  
	public static void generarDetTomaInv(String pSecToma, String pCodLab , String pIdUsu) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pSecToma.trim());
		parametros.add(pCodLab.trim());
    /**
     * Modificado : usuario quien se logueo
     * @author : dubilluz
     * @since  : 20.07.2007
     */
     parametros.add(pIdUsu.trim());    
     log.debug("valor "+parametros + pIdUsu);
		//parametros.add(FarmaVariables.vIdUsu.trim());
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_GUARDAR_DET_TI(?,?,?,?,?)", parametros,false);
	}

	public static void getListaTomasHist(FarmaTableModel pTableModel) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_HIST_TOMAS_INV(?,?)", parametros,false);
	}

	public static void getListaTomasInv(FarmaTableModel pTableModel) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_TOMAS_INV(?,?)", parametros, false);
	}

	public static void getListaLabsXToma(FarmaTableModel pTableModel) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_LABS_TOMA_INV(?,?,?)", parametros,false);
	}

	public static void getListaProdsXLabXToma(FarmaTableModel pTableModel) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		parametros.add(VariablesTomaInv.vCodLab.trim());
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_PROD_LAB_TOMA_INV(?,?,?,?)",parametros, false);
	}

	public static void ingresaCantidadProdInv(String pCantToma) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		parametros.add(VariablesTomaInv.vCodLab.trim());
		parametros.add(VariablesTomaInv.vCodProd.trim());
		parametros.add(pCantToma);
    log.debug("Parametros"+parametros);
		FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_INV.TI_INGRESA_CANT_PROD_TI(?,?,?,?,?,?)", parametros, false);

	}

	public static void getListaProdsDiferencias(FarmaTableModel pTableModel) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		parametros.add(VariablesTomaInv.vCodLab.trim());
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_DIF_PROD_LAB_TOMA_INV(?,?,?,?)",parametros, false);
	}

  public static void getListaProdsDiferenciasHist(FarmaTableModel pTableModel) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		parametros.add(VariablesTomaInv.vCodLab.trim());
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_DIF_PROD_LAB_TOMA_I_H(?,?,?,?)",parametros, false);
	}

	public static void rellenaCantNoIngresadas() throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		parametros.add(VariablesTomaInv.vCodLab.trim());
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_RELLENA_CERO_LAB_TOMA_INV(?,?,?,?)",parametros, false);
	}

	public static void cargarToma() throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		parametros.add(FarmaVariables.vIdUsu.trim());
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_CARGA_TOMA_INV(?,?,?,?)", parametros,false);
	}

	public static void anularToma() throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		parametros.add(FarmaVariables.vIdUsu.trim());
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_ANULA_TOMA_INV(?,?,?,?)", parametros,false);
	}

	public static String obtenerIndTomaIncompleta() throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_INV.TI_OBTIENE_IND_TOMA_INCOMPLETA(?,?,?)",parametros);
	}

	public static ArrayList getListaLabsTomaIncompleta() throws SQLException {
		parametros = new ArrayList();
		ArrayList rpta = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
		FarmaDBUtility.executeSQLStoredProcedureArrayList(rpta,"PTOVENTA_TOMA_INV.TI_LISTA_LABS_TOMA_INCOMPLETA(?,?,?)",parametros);
		return rpta;
	}

	public static void getListaLabsItemsLab(FarmaTableModel pTableModel) throws SQLException {
		parametros = new ArrayList();
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_LABS_ITEMS_LAB", parametros, false);
	}

	public static void getListaItemsxLab(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vCodLab.trim());
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_ITEMS_LAB(?,?,?)", parametros,false);
	}

  public static ArrayList obtieneCodigoLaboratorios()throws SQLException
  {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_TOMA_INV.TI_LISTA_COD_LABORATORIOS(?,?)",parametros);
    return pOutParams;
  }
  
  public static ArrayList obtieneProductosPorLaboratotio(String pCodLab)throws SQLException
  {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodLab);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_TOMA_INV.TI_LISTA_PROD_IMPRESION(?,?,?)",parametros);
    return pOutParams;   
  }

  public static ArrayList obtieneTotalItemsToma(String pSecToma)throws SQLException
  {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecToma);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_TOMA_INV.TI_TOTAL_ITEMS_TOMA(?,?,?)",parametros);
    return pOutParams;   
  }  
  
  public static ArrayList obtieneInformacionValorizada(String pSecToma)throws SQLException
  {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecToma);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_TOMA_INV.TI_INFORMACION_VALORIZADA(?,?,?)",parametros);
    return pOutParams;   
  }  

  public static void listaDiferenciasConsolidado(FarmaTableModel pTableModel,
                                                 String pSecTomaInv) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecTomaInv);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_DIFERENCIAS_CONSOLIDADO(?,?,?)",parametros, false);
	}
	 public static void listaDiferenciasConsolidadoDiario(FarmaTableModel pTableModel,
                                                 String pSecTomaInv) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecTomaInv);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_DIA.TI_DIFERENCIAS_CONSOLIDADO(?,?,?)",parametros, false);
	}

    public static void listaDiferenciasConsolidados(FarmaTableModel pTableModel,
                                                   String pSecTomaInv) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pSecTomaInv);
      log.debug("",parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_DIA.TI_DIFERENCIAS_CONTEO(?,?,?)",parametros, false);
          }


    public static void listaDiferenciasHistorico(FarmaTableModel pTableModel) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      log.debug("",parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_DIA.TI_DIFERENCIAS_HISTORICO(?,?)",parametros, true);
    
    }
 

  public static void listaDiferenciasConsolidadoFiltro(FarmaTableModel pTableModel,
                                                       String pSecTomaInv) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pSecTomaInv);
		parametros.add(VariablesPtoVenta.vCodFiltro);
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_DIF_CONSOLIDADO_FILTRO(?,?,?,?)",parametros, false);
	}
  
  public static void obtieneIndTomaInvForUpdate(ArrayList pArrayList,
                                                String pSecTomaInv,
                                                String pIndProceso) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecTomaInv);
    parametros.add(pIndProceso);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_TOMA_INV.TI_OBTIENE_IND_FOR_UPDATE(?,?,?,?)",parametros);
  }
  
	public static void getListaItemsxLabMovimiento(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vCodLab.trim());
    log.debug("",parametros);
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_OBTIENE_PROD_MOVIMIENTO(?,?,?)", parametros,false);
	}
  /**
   * Lista los laboratorios segun el estado
   * @author dubilluz
   * @since  08.01.2008
   */
	public static void cargaLabxTomaFiltro(FarmaTableModel pTableModel,String pfiltroEstado) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesTomaInv.vSecToma.trim());
    parametros.add(pfiltroEstado.trim());
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_TOMA_INV.TI_LISTA_LABS_TOMA_FILTRO(?,?,?,?)", parametros,false);
	}

    public static String obtieneCodigoProductoBarra(String pCodBarra) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(pCodBarra);
        log.debug("invocando  a PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?)",parametros);
    }


    /**
     * retorna los datos del producto para ingresar cantidad y Fraccion.
     * @author dubilluz
     * @since  21.12.2009
     * @param pArrayList
     * @param pCodProducto
     * @throws SQLException
     */
    public static void getInfoProd(ArrayList pArrayList,
                                   String pCodProducto) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodProducto.trim());
      log.debug("load PTOVENTA_TOMA_INV.TI_F_VAR_DATOS_PROD(?,?,?) :"+parametros);
      FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_TOMA_INV.TI_F_VAR_DATOS_PROD(?,?,?)",parametros);
    }

    /**
     * INSERTA LOS DATOS PARA EL CONTEO DE PRODUCTOS EN LA TOMA TRADICIONAL
     * @author JMIRANDA
     * @since  21.12.2009
     * @param pSecTomaInv
     * @param pSecAuxConteo
     * @param pCodProd
     * @param pCodBarra
     * @param pCant
     * @param pValFracConteo
     * @param pIndNoFound
     * @throws SQLException
     */
    public static void insertAuxConteo(String pSecTomaInv,
                                       int pSecAuxConteo,
                                       String pCodProd,
                                       String pCodBarra,                                      
                                       String pCant,
                                       String pValFracConteo,
                                       String pIndNoFound                                       
                                      ) throws SQLException {
        
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);    
       parametros.add(pSecTomaInv.trim());
       parametros.add(pCodProd.trim());
       parametros.add(pCodBarra.trim());
       parametros.add(pCant.trim());
       parametros.add(pValFracConteo);     
     parametros.add(FarmaVariables.vIdUsu);
     parametros.add(FarmaVariables.vIpPc);     
     parametros.add(pIndNoFound);
        parametros.add(new Integer(pSecAuxConteo));  
     log.debug("invocando a PTOVENTA_TOMA_INV.TI_P_INS_AUX_CONTEO_TOMA(?,?,?,?,?,?,?,?,?,?,?):"+parametros);
       FarmaDBUtility.executeSQLStoredProcedure(null,
                       "PTOVENTA_TOMA_INV.TI_P_INS_AUX_CONTEO_TOMA(?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
    } 
    
    //JMIRANDA 23.12.09
    public static String getSecAuxConteo(String pSecTomaInv) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal); 
        parametros.add(pSecTomaInv);
        log.debug("PTOVENTA_TOMA_INV.TI_F_GET_SEC_AUX_CONTEO(?,?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_INV.TI_F_GET_SEC_AUX_CONTEO(?,?,?)",parametros);
    }

    public static void obtieneListaConteoToma(FarmaTableModel pTableModel,
                                              String pSecTomaInv
                                              ) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal); 
        parametros.add(pSecTomaInv);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_TOMA_INV.TI_F_CUR_LIS_CONTEO_TOMA(?,?,?,?):"+parametros);      
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_TOMA_INV.TI_F_CUR_LIS_CONTEO_TOMA(?,?,?,?)", 
                                                 parametros, 
                                                 false);
    }
    
    public static void updateProdConteo(String pSecTomaInv,
                                        String pAuxSecConteo,
                                        String pCantEnt,
                                        String pCantFraccion
                                        ) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal); 
        parametros.add(pSecTomaInv);        
        parametros.add(pAuxSecConteo);
        parametros.add(pCantEnt);
        parametros.add(pCantFraccion);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc); 
        parametros.add("N");                              
          log.debug("invocando a PTOVENTA_TOMA_INV.TI_P_UPD_CONTEO_TOMA(?,?,?,?,?,?,?,?,?):"+parametros);                                                      
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_P_UPD_CONTEO_TOMA(?,?,?,?,?,?,?,?,?)",parametros,false);
        
    }
    
    public static void delProdConteo( String pSecTomaInv,
                                      String pAuxSecConteo        
                                    ) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal); 
        parametros.add(pSecTomaInv);        
        parametros.add(pAuxSecConteo);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc); 
        parametros.add("S");  
          log.debug("invocando a PTOVENTA_TOMA_INV.TI_P_DEL_CONTEO_TOMA(?,?,?,?,?,?,?):"+parametros);                                                      
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_P_DEL_CONTEO_TOMA(?,?,?,?,?,?,?)",parametros,false);        
    }

    public static void rellenaTomaConCeros() throws SQLException {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(VariablesTomaInv.vSecToma.trim());            
            log.debug("invocando a PTOVENTA_TOMA_INV.TI_RELLENA_CERO_TOMA_INV(?,?,?):"+parametros);                                                      
            FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_RELLENA_CERO_TOMA_INV(?,?,?)",parametros, false);
    }


    public static void getListaProdsDiferenciasTotales(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesTomaInv.vSecToma.trim());
        log.debug("invocando a PTOVENTA_TOMA_INV.TI_LISTA_DIF_TOMA_INV(?,?,?):"+parametros);                                                              
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                                 "PTOVENTA_TOMA_INV.TI_LISTA_DIF_TOMA_INV(?,?,?)", 
                                                 parametros, false);
    }    

    public static void getLabTomaInv(ArrayList pArrayLista) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesTomaInv.vSecToma.trim());
        log.debug("invocando a PTOVENTA_TOMA_INV.TI_lista_LAB_TOMA(?,?,?):"+parametros);                                                              
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayLista, 
                                                 "PTOVENTA_TOMA_INV.TI_lista_LAB_TOMA(?,?,?)", 
                                                 parametros);
    }        
    
    public static void getProductoLabTomaInv(ArrayList getProductoLabTomaInv,String pCodLab) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesTomaInv.vSecToma.trim());
        parametros.add(pCodLab.trim());
        log.debug("invocando a PTOVENTA_TOMA_INV.TI_LISTA_DIF_TOMA_LAB_INV(?,?,?,?):"+parametros);                                                              
        FarmaDBUtility.executeSQLStoredProcedureArrayList(getProductoLabTomaInv, 
                                                 "PTOVENTA_TOMA_INV.TI_LISTA_DIF_TOMA_LAB_INV(?,?,?,?)", 
                                                 parametros);
    }  
    
    public static void enviaCorreoCodBarraNoExiste(String pSecTomaInv,String pCodBarra,String pGlosa)throws SQLException
    {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        parametros.add(pCodBarra.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pGlosa.trim());
        log.debug("PTOVENTA_TOMA_INV.TI_P_ENVIA_EMAIL_NO_FOUND(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_TOMA_INV.TI_P_ENVIA_EMAIL_NO_FOUND(?,?,?,?,?,?)",parametros,false);
    }
    

    public static void actualizarInidices()throws SQLException
    {
        parametros = new ArrayList();
        log.debug("Ptoventa_Toma_Inv_AUX.ti_p_actualiza_indices:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"Ptoventa_Toma_Inv_AUX.ti_p_actualiza_indices",parametros,false);
    }    
        
}
