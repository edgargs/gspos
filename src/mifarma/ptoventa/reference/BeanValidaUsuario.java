package mifarma.ptoventa.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanValidaUsuario {
    private static final Logger log = LoggerFactory.getLogger(BeanValidaUsuario.class);
    public BeanValidaUsuario() {
        
    }
    /**
     * Se valida cambio de clave por usuario
     * @AUTHOR JCORTEZ
     * @SINCE 04.09.09
     * */
    public static String validaCambioClave() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(FarmaVariables.vNuSecUsu);
        log.debug("vParameters :" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.VALIDA_CAMBIO_CLAVE(?,?,?)", vParameters);
    }
    /**
     * Obtiene la cantidad de dias que falta por vencer a partir de un parametro.
     * @AUTHOR CHUANES
     * @SINCE 25.02.2015
     * */
    public static String recFecVenClave(String numSecUsu) throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(numSecUsu);
        log.debug("vParameters :" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.VERIFICA_VENCIMIENTO_CLAVE(?,?,?)", vParameters);
    }
    /**
     * Valida si un usuario debe clambiar su clave o no de acuerdo al numero de secuencial.
     * @AUTHOR CHUANES
     * @SINCE 11.03.2015
     * */
    
    public static String usuDebeCambiarClave() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vNuSecUsu);
        log.debug("vParameters :" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.VALIDA_USUARIO_CAMBIO_CLAVE(?)", vParameters);
    }
    /**
     * El sistema debe recordad solo una vez al dia el cambio de clave.
     * @AUTHOR CHUANES
     * @SINCE 12.03.2015
     * */
    public static String recodCambioClave() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(FarmaVariables.vNuSecUsu);
        log.debug("vParameters :" + vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.RECORD_CAMBIO_CLAVE(?,?,?)", vParameters);
    }
    
    /**
     * 
     *CESAR HUANES
     * 12.03.2015
     * Devuelve las ultimas claves segun el tab gral.
     * **/
    
    public static String maxUltimasClaves() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters = new ArrayList();
       
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.MAXIMO_ULTIMAS_CLAVES",
                                                           vParameters);
        
    }  
    
}
