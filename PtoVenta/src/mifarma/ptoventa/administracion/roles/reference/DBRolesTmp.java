package mifarma.ptoventa.administracion.roles.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;

import mifarma.common.FarmaVariables;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 /**
  * Copyright (c) 2015 MIFARMA S.A.C.<br>
  * <br>
  * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
  * Nombre de la Aplicación : DBRolesTmp.java<br>
  * <br>
  * Histórico de Creación/_fModificación<br>
  * CESAR     25.02.2015   Creación<br>
  * <br>
  * @author Cesar Huanes<br>
  * @version 1.0<br>
  *
  */

public class DBRolesTmp {
    public DBRolesTmp() {
     
    }
    private static final Logger log = LoggerFactory.getLogger(DBRolesTmp.class);
    private static ArrayList parametros = new ArrayList();
    public static void lstRolesTemporal(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_USU.LISTA_ROLES_TEMP(?,?)",
                                                 parametros, false);  
    }
    
    public static void buscaDatosCliente(ArrayList pArrayList,String dni) throws SQLException {
     
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(dni);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_ADMIN_USU.BUSCA_DATOS_USUARIO(?,?,?)",
                                                 parametros);  
    }
    
    public static String registraUsuarioTmp(String secuencial,String fecInicio,String fecFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(secuencial);
        parametros.add(fecInicio);
        parametros.add(fecFin);
        parametros.add(FarmaVariables.vIdUsu);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.REGISTRA_ROL_TMP(?,?,?,?,?,?)",
                                                           parametros);
        
        
    }
    
    public static String verificaVigenciaRol(String codDni,String fecInicio,String fecFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
        parametros.add(fecInicio);
        parametros.add(fecFin);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_VIGENCIA_ROL_TMP(?,?,?,?,?)",
                                                           parametros);
        
    }
    
      public static String verificaVigenciaFuturoRol(String codDni,String fecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
        parametros.add(fecInicio);
      
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_VIGENCIA_FUTURO(?,?,?,?)",parametros);
                                                           
        
    }
    
    public static String actualizaDatosRolTmp(String codDni,String fecInicio,String fecFin,String usuario) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
        parametros.add(fecInicio);
        parametros.add(fecFin);
        parametros.add(usuario);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.ACTUALIZA_DATOS_ROL_TMP(?,?,?,?,?,?)",
                                                           parametros);
        
    }
   
    public static String eliminaRegistroRolTmp(String codDni,String fecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
        parametros.add(fecInicio);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.ELIMINA_DATOS_ROL_TMP(?,?,?,?)",
                                                           parametros);
        
    }
    
    public static String verificaNoDuplicidad(String codDni,String fecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
        parametros.add(fecInicio);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_NO_DUPLICIDAD(?,?,?,?)",
                                                           parametros);
        
    }
    public static String verificaCruceFechas(String codDni,String fecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
       parametros.add(fecInicio);
       
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_CRUCE_FECHA(?,?,?,?)",
                                                           parametros);
        
    }
        
    
        
    public static String verificaFechaInicio(String fecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(fecInicio);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_FECHA_INICIO(?)",
                                                           parametros);
        
    }
    
  
   
    public static String verificaCruceFechaUpDate(String codDni,String fecInicio,String fecFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
        parametros.add(fecInicio);
        parametros.add(fecFin);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_CRUCE_FECHA_UPDATE(?,?,?,?,?)",
                                                           parametros);
        
    }
    public static String verificaCodTrabajador(String codDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDni);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_COD_TRAB(?,?,?)",
                                                           parametros);
        
    }
  
    public static void lstAnuladosUsuario(FarmaTableModel pTableModel,String fecVenta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fecVenta);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.LSTANULADOS_USUARIO(?,?,?)",
                                                 parametros, false);  
    }
    

    public static void lstCierreTurnoSinVB(FarmaTableModel pTableModel,String fecVenta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fecVenta);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.LISTA_CIERRETURNO_SINVB(?,?,?)",
                                                 parametros, false);  
    }
    
   
    public static void lstResumenTransferencias(FarmaTableModel pTableModel,String fecVenta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fecVenta);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.LISTA_REPORTE_TRANSFERENCIA(?,?,?)",
                                                 parametros, false);  
    }
  
    public static void enviarAlertaEmail(String fecReporte) throws SQLException {
      
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fecReporte);
        log.debug("PTOVENTA_CE_LMR.ENVIA_EMAIL_ADMLOCAL(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.ENVIA_EMAIL_ADMLOCAL(?,?,?)",  parametros, false); 
                                               
    }
 
    public static void lstRolesRangoFecha(FarmaTableModel pTableModel,String fecInicio,String fecFin) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fecInicio);
        parametros.add(fecFin);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_USU.LISTA_ROLES_RANGOFECHA(?,?,?,?)",
                                                 parametros, false);  
    }

    public static String verifRangoMaxFechas(String cFecFin,String  cFecInicio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(cFecInicio);
        parametros.add(cFecFin);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.VERIFICA_RANGO_ENTRE_FECHAS(?,?)",
                                                           parametros);
        
    }
    
    public static String maxRangoFechas() throws SQLException {
        parametros = new ArrayList();
       
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.MAXIMO_RANGO_FECHAS",
                                                           parametros);
        
    }

    
    
    
    
   
    
}
