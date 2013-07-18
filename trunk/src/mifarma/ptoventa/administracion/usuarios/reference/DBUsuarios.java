package mifarma.ptoventa.administracion.usuarios.reference;

import java.sql.SQLException;
import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.VariablesVentas;

public class DBUsuarios {

	private static ArrayList parametros = new ArrayList();

	public DBUsuarios() {
	}

	public static void getListaUsuarios(FarmaTableModel pTableModel,String pActivo) throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pActivo);
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_ADMIN_USU.USU_LISTA_USUARIOS_LOCAL(?,?,?)", parametros, false);
	}
  //27.11.2007   modificado
	public static void ingresaUsuario(String pCodTrab, String pNomUsu, String pApePat, String pApeMat,
			String pLoginUsu, String pClaveUsu, String pTelefUsu,
			String pDireccUsu, String pFecNac, String pDni,String pCodRRHH) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pCodTrab);
		parametros.add(pNomUsu);
		parametros.add(pApePat);
		parametros.add(pApeMat);
		parametros.add(pLoginUsu);
		parametros.add(pClaveUsu);
		parametros.add(pTelefUsu);
		parametros.add(pDireccUsu);
		parametros.add(pFecNac);
		parametros.add(FarmaVariables.vIdUsu);
    parametros.add(pDni);
    parametros.add(pCodRRHH);
    System.out.println("Ingreso " + parametros);
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_USU.USU_INGRESA_USUARIO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros, false);
	}
	public static void actualizaUsuario(String pSecUsuLocal,String pCodTrab,String pNomUsu, String pApePat,
			String pApeMat, String pLoginUsu, String pClaveUsu,
			String pTelefUsu, String pDireccUsu, String pFecNac,String pDni)
			throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pSecUsuLocal);
    parametros.add(pCodTrab);
		parametros.add(pNomUsu);
		parametros.add(pApePat);
		parametros.add(pApeMat);
		parametros.add(pLoginUsu);
		parametros.add(pClaveUsu);
		parametros.add(pTelefUsu);
		parametros.add(pDireccUsu);
		parametros.add(pFecNac);
		parametros.add(FarmaVariables.vIdUsu);
    parametros.add(pDni);
    System.out.println("Actualiza : " + parametros);
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_USU.USU_MODIFICA_USUARIO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros, false);
	}

	public static void eliminaUsuario(String pSecUsuLocal) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pSecUsuLocal);
    parametros.add(FarmaVariables.vIdUsu);
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_USU.USU_CAMBIA_ESTADO_USU(?,?,?,?)", parametros,false);
	}

	public static void getListaRolesAsignados(FarmaTableModel pTableModel) throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(VariablesUsuarios.vCoLocal);
		parametros.add(VariablesUsuarios.vSecUsuLocal);
		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_ADMIN_USU.USU_LISTA_ROLES_USUARIO(?,?,?)", parametros,false);
	}

	public static void getListaRoles(FarmaTableModel pTableModel)
			throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();

		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
				"PTOVENTA_ADMIN_USU.USU_LISTA_ROLES", parametros, true);
	}

	public static void limpiaAsignacionUsuario(String pSecUsuLocal) throws SQLException {

		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pSecUsuLocal);
		FarmaDBUtility.executeSQLStoredProcedure(null,
				"PTOVENTA_ADMIN_USU.USU_LIMPIA_ROLES_USUARIO(?,?,?)", parametros,
				false);

	}

	public static void agregaAsignacionUsuario(String pSecUsuLocal, String pCodRol)
			throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pSecUsuLocal);
		parametros.add(pCodRol);
		parametros.add(FarmaVariables.vIdUsu);
		FarmaDBUtility.executeSQLStoredProcedure(null,
				"PTOVENTA_ADMIN_USU.USU_AGREGA_ROL_USUARIO(?,?,?,?,?)", parametros,
				false);
	}

	public static void getDatosTrab(ArrayList datosUsuario) throws SQLException {

		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodCia);
		parametros.add(VariablesUsuarios.vSecTrab);
    System.out.println("GEt Datos Tab " + parametros);
		FarmaDBUtility.executeSQLStoredProcedureArrayList(datosUsuario,
				"PTOVENTA_ADMIN_USU.USU_OBTIENE_DATA_TRABAJADOR(?,?)", parametros);

	}

	public static String verificaDuplicidadNumUsuario(String pCodTrab) throws SQLException {

		String rpta = "";
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
    		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pCodTrab);
    System.out.println("Duplicado "+ parametros);
		rpta = FarmaDBUtility.executeSQLStoredProcedureStr(
				"PTOVENTA_ADMIN_USU.USU_EXISTE_DUPLICADO(?,?,?)", parametros);
		return rpta;

	}

  public static String verificaDuplicidadLoginUsuario(String pCodTrab) throws SQLException {

		String rpta = "";
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
    		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(pCodTrab);
		rpta = FarmaDBUtility.executeSQLStoredProcedureStr(
				"PTOVENTA_ADMIN_USU.USU_EXISTE_LOGIN_DUPLICADO(?,?,?)", parametros);
		return rpta;

	}

  public static void obtieneListaTrabajadores(FarmaTableModel pTableModel)throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodCia);
    
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_USU.USU_LISTA_TRABAJADORES(?)",parametros, false);
  }

    public static void obtieneListaTrabajadoresLocal(FarmaTableModel pTableModel)throws SQLException
    {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_USU.USU_LISTA_TRABAJADORES_LOCAL(?,?)",parametros, false);
    }


  /**
   * Busca si el usuario tiene caja asignada
   * @author :  
   * @since  : 12.07.2007
   */
  public static String buscaCajaAsignada(String  sec_usuario)throws SQLException
  {
    parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(sec_usuario.trim());
		return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_EXISTE_CAJA(?,?,?)", parametros).trim();
   }
  /**
   * Busca si existen usuarios sin cajas asignadas
   * @author :  
   * @since  : 12.07.2007
   */
  public static String buscaUsuSinCajaAsignada()throws SQLException
  {
    parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_EXISTE_USU_SIN_CAJA(?,?)", parametros).trim();
   }
  /**
   * Modifica el Estado caja de usuario
   * @author :  
   * @since  : 12.07.2007
   */   
  public static void cambiaEstadoCaja(String pNumCaja) throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
    parametros.add(new Integer(pNumCaja.trim()));		
    parametros.add(FarmaVariables.vIdUsu);
		FarmaDBUtility.executeSQLStoredProcedure(null,
				"PTOVENTA_ADMIN_CAJA.CAJ_CAMBIAESTADO_CAJA(?,?,?,?)", parametros,
				false);
  }  
  
  /**
   * Obtiene el estado de la Caja
   * @author :  
   * @since  : 12.07.2007
   */
  public static String obtieneEstadoCaja(String pNumCaja)throws SQLException
  {
    parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(new Integer(pNumCaja.trim()));		
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_CAJA.CAJ_OBTIENE_ESTADO_CAJA(?,?,?)", parametros).trim();
   }  
  

  /** Permite Validar la cantidad maxima de dias para modificar el codigo de trabajador
   * @author:  
   * @since:  03/07/2007
   */
    public static String  validaDiasMaximos(String vLogin)throws SQLException
  {
   String  rspt=""; 
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(vLogin);
     	rspt = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_VALIDA_ACTULIZACION(?,?,?)",parametros);
      return rspt;
  }
  
 /**
  * Verifica si el usuario existe
  * @author  
  * @since  27.11.2007
  */
 
  public static String  getExisteUsuario(String vSecTrab)throws SQLException
  {
    String  rspt=""; 
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(vSecTrab.trim());
    rspt = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_EXISTE_USUARIO(?,?)",parametros);
    return rspt;
  }  
  /**
   * Obtiene el indicador de validar al usuario
   * @author  
   * @since  27.11.2007
   */
  public static String getIndValidarUsuario()throws SQLException
  {
    String  rspt=""; 
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    rspt = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_GET_IND_VALIDA(?,?)",parametros);
    return rspt;
  }    
  
  /**
   * Se obtiene el mensaje personaizado por usuario.
   * @param pArray
   * @param pSecUsu
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 17.07.2008
   */
  public static void getMensajeUsuario(ArrayList pArray, String pSecUsu) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecUsu);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PTOVENTA_ADMIN_USU.GET_MENSAJE_USU(?,?,?)",parametros);
  }
  
  /**
   * Actualiza la cantidad de veces que se muestra el mensaje.
   * @param pSecUsu
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 17.07.2008
   */
  public static void actCantVeces(String pSecUsu) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecUsu);
    parametros.add(FarmaVariables.vIdUsu);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_USU.ACT_MENSAJE_USU(?,?,?,?)",parametros,false);
  }
  
  /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  02.09.08
     * @param  pSecUsu
     * @return
     * @throws SQLException
     */
  public static String obtenerClave(String pSecUsu) throws SQLException{
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pSecUsu);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.OBTENER_CLAVE(?,?,?)",parametros);
  }
  
  
    /**
       * @Author Asols
       * @Since  16.02.09
       * @param  pSecUsu
       * @return
       * @throws SQLException
       */



    public static void getDatosTrabLocal(ArrayList datosUsuario) throws SQLException {
       
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodCia);
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(VariablesUsuarios.vSecTrab);
            System.out.println("GEt Datos Tab Local " + parametros);
            FarmaDBUtility.executeSQLStoredProcedureArrayList(datosUsuario,
                            "PTOVENTA_ADMIN_USU.USU_OBTIENE_DATA_TRAB_LOCAL(?,?,?,?)", parametros);

    }
    
    
    /**
       * @Author Asols
       * @Since  16.02.09
       * @param  pSecUsu
       * @return
       * @throws SQLException
       */
    public static String verificaExistenciaUsuarioCarne(String pCodTrab) throws SQLException {

            String rpta = "";
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pCodTrab);
    System.out.println("verificaExistenciaUsuarioCarne "+ parametros);
            rpta = FarmaDBUtility.executeSQLStoredProcedureStr(
                            "PTOVENTA_ADMIN_USU.USU_EXISTE_USUARIO_CARNE(?,?,?)", parametros);
            return rpta;

    }
    
    
    
    /**
       * @Author  
       * @Since  17.02.09
       * @description Ingresar Carne Sanidad
       * @throws SQLException
       */
 
    public static void insertarCarneUsuario() throws SQLException {
      parametros = new ArrayList();
      
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesUsuarios.vCodTrab);
      parametros.add(VariablesUsuarios.vCodTrab_RRHH);
      parametros.add( VariablesUsuarios.vNroCarne);
      parametros.add(VariablesUsuarios.vFechaExpedicion);
      parametros.add(VariablesUsuarios.vFechaVencimiento);
        
      System.out.println("****///////Antes de Actualizar"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_USU.USU_INGRESA_CARNE_USUARIO(?,?,?,?,?,?,?,?)",parametros,false);
    }
    
    /**
       * @Author  
       * @Since  20.02.09
       * @description Envia Alerta de Registro de Carne Sanidad
       * @throws SQLException
       * verificar xq no envia.
       */
    public static void enviaAlertaRegistroCarneUsuario() throws SQLException {
      parametros = new ArrayList();
      
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesUsuarios.vCodTrab);
        parametros.add(VariablesUsuarios.vCodTrab_RRHH);
        parametros.add( VariablesUsuarios.vNroCarne);
        parametros.add(VariablesUsuarios.vFechaExpedicion);
        parametros.add(VariablesUsuarios.vFechaVencimiento);
        
      System.out.println("****///////ENVIAR ALERTA"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_USU.USU_ALERTA_INSR_CARNE_USUARIO(?,?,?,?,?,?,?,?)",parametros,false);
    }
    
    
    
    /**
       * @Author Asols
       * @Since  16.02.09
       * @param  pSecUsu
       * @return
       * @throws SQLException
       */
    public static String verificaFechaVenUsuarioCarne(String pCodTrabRRhh) throws SQLException {

            String rpta = "";
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pCodTrabRRhh);
           
            System.out.println("verificaFechaVenUsuarioCarne"+ parametros);
            rpta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_OBTIENE_FECVENC_PROX(?,?,?)", parametros);
            return rpta;

    }
    
    
    
    /**
       * @Author Asols
       * @Since  23.02.09
       * @param  pSecUsu
       * @return
       * @throws SQLException
       */
    public static String verificaUsuarioCarnedUPLICADO(String pCodTrabRRhh,String pNumCarne) throws SQLException {

            String rpta = "";
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pCodTrabRRhh);
            parametros.add(pNumCarne);
            System.out.println("verificaUsuarioCarnedUPLICADO:"+ parametros);
            rpta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_EXISTE_DUPLICADO_CARNE(?,?,?,?)", parametros);
            return rpta;

    }
    
   
   
    /**
       * @Author Asols
       * @Since  16.02.09
       * @param  pSecUsu
       * @return
       * @throws SQLException
       */
    public static String verificaFechaVenUsuarioCarneControlIngreso(String pCodTrab) throws SQLException {

            String rpta = "";
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pCodTrab);
           
            System.out.println("verificaFechaVenUsuarioCarneControlIngreso"+ parametros);
            rpta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_OBTIENE_FECVENC_PROX_CARNE(?,?,?)", parametros);
            return rpta;

    }
   
    /**
       * @Author Asols
       * @Since  26.02.09
       * @param  pSecUsu
       * @return
       * @throws SQLException
       */
    public static String verificaExistenciaCarne(String pCodTrab) throws SQLException {

            String rpta = "";
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pCodTrab);
           
            System.out.println("verificaExistenciaCarne"+ parametros);
            rpta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_VERIFICA_EXISTENCIA_CARNE(?,?,?)", parametros);
            return rpta;

    }
    
    
    /**
       * @Author  
       * @Since  20.02.09
       * @description Envia Alerta de Cuando el Usuario Sin Carne Marca Ingreso al Local
       * @throws SQLException
       */
    public static void enviaAlertaCarneUsuarioMarcaIngreso(String pCodTrab) throws SQLException {
      parametros = new ArrayList();
      
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodTrab);
        
      System.out.println("****///////ENVIAR ALERTA"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_USU.USU_ALERTA_TRAB_S_CARNE_M_ING(?,?,?)",parametros,false);
    }
}