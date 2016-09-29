package mifarma.ptoventa.mayorista.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2016 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DBMayorista.java<br>
 * <br>
 * Histórico de Creación/Creacion<br>
 * Rafael Bullon Mucha     24/02/2016   Creación<br>
 * <br>
 * @author Rafael Bullon Mucha<br>
 * @version 1.0<br>
 *
 */
public class DBMayorista {
    
    private static final Logger log = LoggerFactory.getLogger(DBMayorista.class);

    private static ArrayList parametros;
    
    public DBMayorista() {
    }
    
    /**
     * Validar si el lote pertenece al producto
     * retorna indicador
     * @author Rafael Bullon
     * @since  29.01.2016
     * @param pCodProducto
     * @throws SQLException
     */
    public static String getValidaCodigoDevImport(String pNumPedidoVenta) throws SQLException{
       
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVenta.trim());        
        log.debug("load PTOVENTA_VTA_MAYORISTA.TI_F_GET_VALIDA_DEV_IMPORT(?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA_MAYORISTA.TI_F_GET_VALIDA_DEV_IMPORT(?,?,?)",
                                                          parametros);
    }   
    
    /**
     * Validar si el lote pertenece al producto
     * retorna indicador
     * @author Rafael Bullon
     * @since  29.01.2016
     * @param pCodProducto
     * @throws SQLException
     */
    public static int transacDevolucionImporte(String pNumPedidoVenta) throws SQLException{
       
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pNumPedidoVenta.trim());        
        log.debug("load PTOVENTA_VTA_MAYORISTA.F_MODIFICA_DEV_IMPORTE(?,?,?,?,?) :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_VTA_MAYORISTA.F_MODIFICA_DEV_IMPORTE(?,?,?,?,?)",
                                                          parametros);
    }      
    
    /**
     * [PROYECTO M] INDICADOR SI PERMITE REALIZAR AJUSTE AUTOMATICO CUANDO SE REALIZA EL DESPACHO
     * @author KMONCADA
     * @since 04.08.2016
     * @return
     * @throws Exception
     */
    public static String getPermiteAjusteAutomaticoDespacho()throws Exception{
        ArrayList parametros = new ArrayList();
        log.info("load PTOVENTA_VTA_MAYORISTA.F_PERMITE_AJUSTE_AUTOMATICO :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA_MAYORISTA.F_PERMITE_AJUSTE_AUTOMATICO", parametros);
    }
}
