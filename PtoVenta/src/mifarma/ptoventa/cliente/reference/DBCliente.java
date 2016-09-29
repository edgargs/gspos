package mifarma.ptoventa.cliente.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBCliente.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA         23/02/2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DBCliente {
    private static final Logger log = LoggerFactory.getLogger(DBCliente.class);

    private static ArrayList parametros = new ArrayList();

    public DBCliente() {
    }

    /* public static void cargaListaClienteJuridico(FarmaTableModel pTableModel, String pBusqueda,
                                                 String pTipoBusqueda) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pBusqueda);
        parametros.add(pTipoBusqueda);
        parametros.add(VariablesPtoVenta.vTipoLocalVenta);
        log.debug("PTOVENTA_CLI.CLI_BUSCA_CLI_JURIDICO(?,?,?,?,?)", parametros);
        if (UtilityPtoVenta.isLocalVentaMayorista()){
            parametros.add(VariablesPtoVenta.vTipoLocalVenta);
            parametros.add(VariablesFidelizacion.vFlgDigemid);   
            
        }else{
            FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CLI.CLI_BUSCA_CLI_JURIDICO(?,?,?,?,?)",
                                                     parametros, false);
        }
        log.info("### pTableModel: " + pTableModel.getRowCount());
    } */

/*     public static String agragaClienteJuridico(String pRazonSocial, String pTipoDocIdent, String pRuc,
                                               String pDirCliente) throws SQLException {
        String sResultado = "";
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaConstants.COD_NUMERA_CLIENTE_LOCAL);
        parametros.add(pRazonSocial);
        parametros.add(pTipoDocIdent);
        parametros.add(pRuc);
        parametros.add(pDirCliente);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesPtoVenta.vTipoLocalVenta);
        parametros.add(VariablesCliente.vDigemid);
        sResultado = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_AGREGA_CLI_LOCAL(?,?,?,?,?,?,?,?,?,?)", parametros);
        
        log.debug("", parametros);
        return sResultado;
    } */

    /* public static String agragaClienteNatural(String pNombre, String pAPellidoPat, String pApellidoMat,
                                              String pTipoDocIdent, String pDni,
                                              String pDirCliente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaConstants.COD_NUMERA_CLIENTE_LOCAL);
        parametros.add(pNombre);
        parametros.add(pAPellidoPat);
        parametros.add(pApellidoMat);
        parametros.add(pTipoDocIdent);
        parametros.add(pDni);
        parametros.add(pDirCliente);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_AGREGA_CLI_NATURAL(?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    } */

    public static String verificaRucValido(String pRUC) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pRUC);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.VERIFICA_RUC_VALIDO(?)", parametros);
    }
    
    /**
     * REALIZA LA VALIDACION DEL NRO DE RUC 
     * @author KMONCADA
     * @since 08.04.2015
     * @param pRUC
     * @return
     */
    public static String verificaRucValido2(String pRUC){
        String resultado = "";
        try {
            resultado = verificaRucValido(pRUC);
            return resultado;
        } catch (SQLException sql) {
            log.error("", sql);
            return ConstantsCliente.RESULTADO_RUC_INVALIDO;
        }
    }

    public static String actualizaClienteJuridico(String pCodCliente, String pRazonSocial, String pRuc,
                                                  String pDirCliente, String vFlagDigemin) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCliente);
        parametros.add(pRazonSocial);
        parametros.add(pRuc);
        parametros.add(pDirCliente);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(vFlagDigemin);
        log.debug("", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_ACTUALIZA_CLI_LOCAL(?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void obtieneInfo_Cli_Natural(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();
        //parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCliente.vCodigo);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CLI.CLI_OBTIENE_INFO_CLI_NATURAL(?,?,?)",
                                                          parametros);
    }

    public static void obtieneTarjetas_Cliente(FarmaTableModel pTableModel, String pCodigo) throws SQLException {

        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodigo);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA.PTOVENTA_CLI.CLI_OBTIENE_TARJETAS_CLIENTE(?,?,?)",
                                                 parametros, false);
    }

    public static String actualizaClienteNatural(String pCodCliente, String pNombreCliente, String pApellidoPat,
                                                 String pApellidoMat, String pDni,
                                                 String pDirCliente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCliente);
        parametros.add(pNombreCliente);
        parametros.add(pApellidoPat);
        parametros.add(pApellidoMat);
        parametros.add(pDni);
        parametros.add(pDirCliente);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        log.debug("realizo la actualizacion");
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_ACTUALIZA_CLI_NATURAL(?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String agragaTarjetaCliente(String pCodOperadorN, String pNumeroTarjeta, String pFechaVencimiento,
                                              String pPropietario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCliente.vCodigo);
        parametros.add(pCodOperadorN);
        parametros.add(pFechaVencimiento);
        parametros.add(pNumeroTarjeta);
        parametros.add(pPropietario);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_AGREGA_TARJETAS_CLI(?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String actualizaTarjetaCliente(String pCodCliente, String pCodOperadorN, String pCodOperadorA,
                                                 String pFecVencimiento, String pNumeroTarjeta,
                                                 String pNombreTarjeta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCodCliente);
        parametros.add(pCodOperadorN);
        parametros.add(pCodOperadorA);
        parametros.add(pFecVencimiento);
        parametros.add(pNumeroTarjeta);
        parametros.add(pNombreTarjeta);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        log.debug("realizo la actualizacion");
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_ACTUALIZA_TARJETA(?,?,?,?,?,?,?)",
                                                           parametros);
    }


}
