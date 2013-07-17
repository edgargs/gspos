package mifarma.ptoventa.administracion.mantenimiento.reference;

import java.sql.SQLException;
import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

public class DBMantenimiento {
	public DBMantenimiento() {
	}

	private static ArrayList parametros = new ArrayList();

	public static void getParametrosLocal(ArrayList parametrosLocal) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
	  
		FarmaDBUtility.executeSQLStoredProcedureArrayList(parametrosLocal,"PTOVENTA_ADMIN_MANT.GET_PARAMETROS_LOCAL(?,?)", parametros);
	}

  public static void actualizaParametrosLocal(String impReporte, String minPendientes,
                                              String  pIndCambioPrecio ,String pIndCambioModeloImpresora) throws SQLException
  {
    parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
    parametros.add(impReporte);
    parametros.add(new Integer(minPendientes));
    parametros.add(pIndCambioPrecio);
    parametros.add(pIndCambioModeloImpresora);
    parametros.add(FarmaVariables.vIdUsu);
    
   
      
    System.out.println(parametros);
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_MANT.ACTUALIZAR_PARAMETROS_LOCAL(?,?,?,?,?,?,?)", parametros,false);
  }

  public static void cargaListaControlHoras(FarmaTableModel pTableModel) throws SQLException 
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vNuSecUsu);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_ADMIN_MANT.ADMMANT_OBTIENE_CONTROL_HORAS(?,?,?)",parametros,false);                                                   
  } 
  
  public static void grabaControlHoras(String pCodMotivo, String pObservaciones) throws SQLException
  {
    parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vNuSecUsu);
    parametros.add(pCodMotivo);
    parametros.add(pObservaciones);
    System.out.println("grabaControlHoras: "+parametros);
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_MANT.ADMMANT_INGRESA_CONTROL(?,?,?,?,?)", parametros,false);
  }
  
  public static String verificaIngresoControlHoras(String pCodMotivo) throws SQLException
  {
    parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodMotivo);
    parametros.add(FarmaVariables.vNuSecUsu);
    System.out.println("veerificaIngresoControlHoras: "+parametros);
		return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_MANT.ADMMANT_VERIFICA_INGRESO_CTRL(?,?,?,?)", parametros);   
  }
}
