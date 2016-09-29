package mifarma.ptoventa.inventariociclico.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBInvCiclico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.10.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DBInvCiclico {
    private static final Logger log = LoggerFactory.getLogger(DBInvCiclico.class);

    private static ArrayList parametros;

    public DBInvCiclico() {
    }

    /**
     * Lista los productos de inicio de un inventario ciclico.
     * @param pTableModel
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 23.10.2006
     */
    public static void getListaProdsInicio(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.GET_LISTA_PROD_TOMA_CIC(?,?)",
                                                 parametros, false);
    }

    /**
     * Inicializa los productos para el inventario ciclico.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 24.10.2006
     */
    public static void initInventarioCiclico() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.CIC_INICIALIZA_TOMA(?,?)", parametros,
                                                 false);
    }

    /**
     * Lista los productos de restantes de un inventario ciclico.
     * @param pTableModel
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 24.10.2006
     */
    public static void getListaProdsInv(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.GET_LISTA_PRODS(?,?)", parametros,
                                                 true);
    }

    /**
     * Inserta producto a la lista de inventario ciclico.
     * @param codProd
     * @param unids
     * @param montoTotal
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 24.10.2006
     */
    public static void agregarProducto(String codProd, String unids, String montoTotal,
                                       String codLab) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(unids)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(montoTotal)));
        parametros.add(codLab);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.CIC_INSERTA_PROD(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Borra un producto a la lista de inventario ciclico.
     * @param codProd
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 24.10.2006
     */
    public static void borrarProducto(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.CIC_BORRA_PROD(?,?,?)", parametros, false);
    }


    /**
     * Graba los productos seleccionados, en una nueva toma de inventario.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 27.10.2006
     */
    public static void grabarInventarioCiclico() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        if (FarmaVariables.vEconoFar_Matriz)
            parametros.add("1");
        else
            parametros.add("0");
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.CIC_GRABA_TOM_INV(?,?,?,?)", parametros,
                                                 false);
    }
    
    /**
     * Graba la cantidad por lote de un producto
     * @throws SQLException
     * @author Rafael Bullon Mucha
     * @since 03.02.2016
     */
    public static int grabarLoteXProductoCiclico(String vLote,int vCantidad) throws SQLException {
        parametros = new ArrayList();       
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vCodProd);
        parametros.add(VariablesInvCiclico.vCodLab);
        parametros.add(VariablesInvCiclico.vSecToma);
        parametros.add(vLote);
        parametros.add(vCantidad);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
       return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_TOMA_CIC.CIC_GRABA_LOTE_PROD(?,?,?,?,?,?,?,?)", parametros);
    } 
    
    
    /**
     * Modifica la cantidad por lote de un producto
     * @throws SQLException
     * @author Rafael Bullon Mucha
     * @since 03.02.2016
     */
    public static int modificaLoteXProductoToma(String vLote,int vCantidad) throws SQLException {
        parametros = new ArrayList();       
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vCodProd);
        parametros.add(VariablesInvCiclico.vCodLab);
        parametros.add(VariablesInvCiclico.vSecToma);
        parametros.add(vLote);
        parametros.add(vCantidad);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
       return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_TOMA_CIC.CIC_MODIFICA_LOTE_PROD(?,?,?,?,?,?,?,?)", parametros);
    }    
    
    /**
     * Validar si el lote pertenece al producto
     * retorna indicador
     * @author Rafael Bullon
     * @since  29.01.2016
     * @param pCodProducto
     * @throws SQLException
     */
    public static int getProdTieneMasterPack(String pCodProducto) throws SQLException{
       
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProducto.trim());        
        log.debug("load PTOVENTA_TOMA_INV.TI_F_GET_TIENE_MASTERPACK(?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_TOMA_CIC.TI_F_GET_TIENE_MASTERPACK(?,?)",
                                                          parametros);
    } 
    
    /**
     * Obtener el valor de la variable lote del producto cuando este no lo requiera
     * retorna indicador
     * @author Rafael Bullon
     * @since  15.02.2016
     * @throws SQLException
     */
    public static String getValueProdNoRequiLote() throws SQLException{
       
        parametros = new ArrayList();
       
        log.debug("load PTOVENTA_TOMA_INV.GET_SIN_LOTE :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_INV.GET_SIN_LOTE()",
                                                          parametros);
    }     
    

    //Metodos Paulo

    public static void getListaTomasInv(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_LISTA_TOMAS_INV(?,?)", parametros,
                                                 false);
    }

    public static void getListaLabsXToma(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_LISTA_LABS_TOMA_INV(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaProdsXLabXToma(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(VariablesInvCiclico.vCodLab.trim());
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            VariablesInvCiclico.vIndTipoVentaMayorista =1;
        }else{
            VariablesInvCiclico.vIndTipoVentaMayorista=0;
        }
        
        parametros.add(VariablesInvCiclico.vIndTipoVentaMayorista);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_LISTA_PROD_LAB_TOMA_INV(?,?,?,?,?)",
                                                 parametros, false);
    }
    
    /**
     * Lista los lotes asignados a cada prodcuto.
     * retorna indicador
     * @author Rafael Bullon
     * @since  29.01.2016
     * @param 
     * @throws SQLException
     */    
  public static void getListaProdsXLoteXToma(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(VariablesInvCiclico.vCodLab.trim());
        parametros.add(VariablesInvCiclico.vCodProd.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_LISTA_PROD_LOTE_TOMA_INV(?,?,?,?,?)",
                                                 parametros, false);
    } 
    
    /**
     * Valida si existe el lote en la toma de inventarios.
     * retorna indicador
     * @author Rafael Bullon
     * @since  01.02.2016
     * @param 
     * @throws SQLException
     */    
    public static String getValidaLoteXToma(String pLote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(VariablesInvCiclico.vCodLab.trim());
        parametros.add(VariablesInvCiclico.vCodProd.trim());
        parametros.add(pLote);
        return FarmaDBUtility.executeSQLStoredProcedureStr( "PTOVENTA_TOMA_CIC.TI_VALIDA_LOTE(?,?,?,?,?,?)",
                                                 parametros);
    }   
    

    public static void ingresaCantidadProdInv(String pCantToma) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(VariablesInvCiclico.vCodLab.trim());
        parametros.add(VariablesInvCiclico.vCodProd.trim());
        parametros.add(pCantToma);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.TI_INGRESA_CANT_PROD_TI(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaMovsKardex(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vCodProd);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_LISTA_MOVS_KARDEX(?,?,?)",
                                                 parametros, false);
    }

    public static ArrayList obtieneTotalItemsToma(String pSecToma) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecToma);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_TOMA_CIC.TI_TOTAL_ITEMS_TOMA(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static ArrayList obtieneInformacionValorizada(String pSecToma) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecToma);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,
                                                          "PTOVENTA_TOMA_CIC.TI_INFORMACION_VALORIZADA(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static void listaDiferenciasConsolidado(FarmaTableModel pTableModel,
                                                   String pSecTomaInv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_DIFERENCIAS_CONSOLIDADO(?,?,?)",
                                                 parametros, false);
    }
    
/*
 * Descripcion: Mostrar el consolidado de diferencias por lote
 * Por: Rafael Bullon Mucha
 * Fecha: 15/12/2016
 * */    
    public static void listaDiferenciasConsolidadoLote(FarmaTableModel pTableModel,
                                                   String pSecTomaInv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_DIFERENCIAS_CONSOL_LOTE(?,?,?)",
                                                 parametros, false);
    }    
    

    public static void listaDiferenciasConsolidadoFiltro(FarmaTableModel pTableModel,
                                                         String pSecTomaInv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        parametros.add(VariablesPtoVenta.vCodFiltro);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_DIF_CONSOLIDADO_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }
    
 /*
  * Descripcion: Agregar el campo lote para el listado del filtro
  * Por. Rafael Bullon mucha
  * Fecha. 18/02/2015
  * */   
    public static void listaDiferenciasConsolidadoFiltroLote(FarmaTableModel pTableModel,
                                                         String pSecTomaInv) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        parametros.add(VariablesPtoVenta.vCodFiltro);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TOMA_CIC.TI_DIF_CONSOLIDADO_FILTRO_LT(?,?,?,?)",
                                                 parametros, false);
    }    

    public static void cargarToma() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.TI_CARGA_TOMA_INV(?,?,?,?)", parametros,
                                                 false);
    }

    public static String obtenerIndTomaIncompleta() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TOMA_CIC.TI_OBTIENE_IND_TOMA_INCOMPLETA(?,?,?)",
                                                           parametros);
    }

    public static void obtieneIndTomaInvForUpdate(ArrayList pArrayList, String pSecTomaInv,
                                                  String pIndProceso) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecTomaInv);
        parametros.add(pIndProceso);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_TOMA_CIC.TI_OBTIENE_IND_FOR_UPDATE(?,?,?,?)",
                                                          parametros);
    }

    public static void anularToma() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.TI_ANULA_TOMA_INV(?,?,?,?)", parametros,
                                                 false);
    }

    public static ArrayList getListaLabsTomaIncompleta() throws SQLException {
        parametros = new ArrayList();
        ArrayList rpta = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        FarmaDBUtility.executeSQLStoredProcedureArrayList(rpta,
                                                          "PTOVENTA_TOMA_CIC.TI_LISTA_LABS_TOMA_INCOMPLETA(?,?,?)",
                                                          parametros);
        return rpta;
    }

    public static void rellenaCantNoIngresadas() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(VariablesInvCiclico.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.TI_RELLENA_CERO_LAB_TOMA_INV(?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaProdsDiferencias(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(VariablesInvCiclico.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_TOMA_CIC.TI_LISTA_DIF_PROD_LAB_TOMA_INV(?,?,?,?)",
                                                 parametros, false);
    }
    
    /*
     * Descripcion: Muestra la diferencia de productos por lote
     * Por: Rafael Bullon Mucha
     * Fecha: 15/02/2016
     * */     
    public static void getListaProdsDiferenciasLote(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        parametros.add(VariablesInvCiclico.vCodLab.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_TOMA_CIC.TI_LISTA_DIF_PROD_INV_LOTE(?,?,?,?)",
                                                 parametros, false);
    }    
    

    public static ArrayList obtieneCodigoLaboratorios() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,
                                                          "PTOVENTA_TOMA_CIC.TI_LISTA_COD_LABORATORIOS(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static ArrayList obtieneProductosPorLaboratotio(String pCodLab) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodLab);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,
                                                          "PTOVENTA_TOMA_CIC.TI_LISTA_PROD_IMPRESION(?,?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static void envioTomaLocal(String pSecToma) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInvCiclico.vSecToma.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TOMA_CIC.TI_ENVIA_TOMA_LOCAL(?,?,?)", parametros,
                                                 false);
    }

    //Metodos Paulo

}
