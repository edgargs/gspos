package mifarma.common;

import java.util.ArrayList;

import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 9.0.4.0<br>
 * Nombre de la Aplicación : FarmaSecurity.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA 07.01.2006 Creación<br>
 * <br>
 *
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FarmaSecurity {

    /** Almacena el Secuencial del Usuario Logueado */
    private String vNuSecUsuario = new String("");

    /** Almacena el Id (Usuario) Logueado */
    private String vIdUsuario = new String("");

    /** Almacena la clave del Usuario Logueado */
    private String vDeClaveUsuario = new String("");

    /** Almacena el Apellido Paterno del Usuario */
    private String vPaternoUsuario = new String("");

    /** Almacena el Apellido Materno del Usuario */
    private String vMaternoUsuario = new String("");

    /** Almacena los Nombres del Usuario */
    private String vNoUsuario = new String("");

    /** Almacena la nueva clave del Usuario */
    private String vNuevaClave = new String("");

    /** Almacena los Roles */
    private ArrayList vRoleList = new ArrayList();

    /** Almacena la información del Usuario */
    private ArrayList vUserInfo = new ArrayList();

    /** Almacena el estado de acceso del Usuario a la Aplicación */
    private String vStatus = new String("");

    /** Almacena la información del Local */
    private ArrayList vLocalInfo = new ArrayList();

    /**
     * Constructor
     *
     * @param pIdUsuario
     *             Id (usuario) que está accesando a la Aplicación
     * @param pDeClaveUsuario
     *             Clave del usuario
     */
    public FarmaSecurity(String pIdUsuario, String pDeClaveUsuario) {
        vIdUsuario = pIdUsuario;
        vDeClaveUsuario = pDeClaveUsuario;
        checkUserValid();
    }

    /**
     * Constructor <br>
     *
     * @param pIdUsuario
     *             Id (Usuario) que está accesando a la Aplicación
     * @param pDeClaveUsuario
     *             Clave del Usuario
     * @param pNuevaClave
     *             Nueva Clave para el Usuario
     */
    public FarmaSecurity(String pIdUsuario, String pDeClaveUsuario, 
                         String pNuevaClave) {
        vIdUsuario = pIdUsuario.trim();
        vDeClaveUsuario = pDeClaveUsuario.trim();
        vNuevaClave = pNuevaClave.trim();
        try {
            vStatus = verifyUserLogin();
            if (vStatus.equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_OK)) {
                getUserInfo();
                changePassword();
            }
        } catch (SQLException err) {
           if(err.getErrorCode()==20010){
             vStatus =FarmaConstants.LOGIN_CLAVE_IGUAL;
         }else {
                if(err.getErrorCode()==20000){
                 
                    vStatus =FarmaConstants.LOGIN_CUATRO_ULTIMA;
                                                   
                }else{
                err.printStackTrace();
                vStatus = FarmaConstants.ERROR_CONEXION_BD;
                }
            }
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el estado de la Validación de Login <br>
     *
     * @return String String que representa el estado de Login <br>
     *         Valores 01 : Usuario OK <br>
     *         02 : Usuario Inactivo en el Local <br>
     *         03 : Usuario no registrado en el Local <br>
     *         04 : Clave Errada <br>
     *         05 : Usuario No Existe <br>
     *         06 : Error en BD <br>
     *         98 : Version de aplicacion no valida
     */
    public String getLoginStatus() {
        return vStatus;
    }

    /**
     * Devuelve el secuencial de Usuario <br>
     *
     * @return String String que representa el Secuencial de Usuario
     */
    public String getLoginSequential() {
        return vNuSecUsuario;
    }

    /**
     * Devuelve el código de Login de Usuario <br>
     *
     * @return String String que representa el Login de Usuario
     */
    public String getLoginCode() {
        return vIdUsuario;
    }

    /**
     * Devuelve el Nombre de Usuario Logueado <br>
     *
     * @return String String que representa el Nombre de Usuario
     *         Logueado
     */
    public String getLoginNombre() {
        return vNoUsuario;
    }

    /**
     * Devuelve el Apellido Paterno del Usuario Logueado <br>
     *
     * @return String String que representa el Apellido Paterno del
     *         Usuario Logueado
     */
    public String getLoginPaterno() {
        return vPaternoUsuario;
    }

    /**
     * Devuelve el Apellido Materno del Usuario Logueado <br>
     *
     * @return String String que representa el Apellido Materno del
     *         Usuario Logueado
     */
    public String getLoginMaterno() {
        return vMaternoUsuario;
    }

    /**
     * Devuelve los datos del Usuario Logueado ( paterno + materno + nombres )
     */
    public String getLoginUsuario() {
        return vPaternoUsuario.trim() + " " + vMaternoUsuario.trim() + ", " + 
            vNoUsuario.trim();
    }

    /**
     * Indica si el usuario es válido <br>
     *
     * @return boolean Devuelve true si el usuario es válido, caso
     *         contrario false.
     */
    public boolean isUserValid() {
        return (vStatus.equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_OK));
    }

    /**
     * Devuelve el estado de la Validación de Login <br>
     *
     * @param pRoleCode
     *             Variable String que define el Código de Rol a Verificar
     *            <br>
     * @return boolean Indica si el usuario cuenta con el rol ingresado.
     */
    public boolean haveRole(String pRoleCode) {
        String roleCode = new String("");
        for (int i = 0; i < vRoleList.size(); i++) {
            roleCode = ((String)((ArrayList)vRoleList.get(i)).get(0)).trim();
            if (roleCode.equalsIgnoreCase(pRoleCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Realiza el proceso de Validación del Usuario. Además si es válido el
     * usuario <br>
     * se obtiene los datos y roles q tiene asignado el usuario.
     */
    private void checkUserValid() {
        try {
            vStatus = verifyUserLogin();
            if (vStatus.equalsIgnoreCase(FarmaConstants.LOGIN_USUARIO_OK)) {
                getUserInfo();
                vRoleList = getRoleList();
            }
        } catch (SQLException err) {
            err.printStackTrace();
            vStatus = FarmaConstants.ERROR_CONEXION_BD;
        }
    }

    /**
     * Obtiene los datos del Usuario Logueado y los almacena en variables a
     * nivel de Clase
     */
    private void getUserInfo() throws SQLException {
        vUserInfo = getUserInfoList();
        if (vUserInfo.size() > 0) {
            vNuSecUsuario = 
                    ((String)((ArrayList)vUserInfo.get(0)).get(0)).trim();
            vPaternoUsuario = 
                    ((String)((ArrayList)vUserInfo.get(0)).get(1)).trim();
            vMaternoUsuario = 
                    ((String)((ArrayList)vUserInfo.get(0)).get(2)).trim();
            vNoUsuario = ((String)((ArrayList)vUserInfo.get(0)).get(3)).trim();
            vIdUsuario = ((String)((ArrayList)vUserInfo.get(0)).get(4)).trim();
            FarmaVariables.vIdUsuCE = 
                    ((String)((ArrayList)vUserInfo.get(0)).get(4)).trim();
            /*FarmaVariables.vIndHabilitado = ((String) ((ArrayList) vUserInfo
					.get(0)).get(5)).trim();
     System.out.println("FarmaVariables.vIndHabilitado: " + FarmaVariables.vIndHabilitado);*/

        }

    }

    /**
     * Ejecuta la consulta a la BD, verifica los datos de login del Usuario
     *
     * @return String Devuelve el estado de la validación de loguin.
     */
    private String verifyUserLogin() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        System.out.println("FarmaVariables.vCodGrupoCia=" + 
                           FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        System.out.println("FarmaVariables.vCodLocal=" + 
                           FarmaVariables.vCodLocal);
        vParameters.add(vIdUsuario);
        System.out.println("vIdUsuario=" + vIdUsuario);
        vParameters.add(vDeClaveUsuario);
        //System.out.println("vDeClaveUsuario=" + vDeClaveUsuario);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.VERIFICA_USUARIO_LOGIN(?,?,?,?)", 
                                                           vParameters);
    }

    /**
     * Ejecuta la consulta a la BD, obtiene la lista de Roles Asignados al
     * Usuario
     *
     * @return ArrayList Devuelve una lista con los roles del Usuario
     */
    private ArrayList getRoleList() throws SQLException {
        ArrayList vArrayList = new ArrayList();
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(vNuSecUsuario);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vArrayList, 
                                                          "FARMA_SECURITY.LISTA_ROL(?,?,?)", 
                                                          vParameters);
        return vArrayList;
    }

    /**
     * Ejecuta la consulta a la BD, obtiene la información del Usuario
     *
     * @return ArrayList Devuelve una lista con los datos del Usuario
     */
    private ArrayList getUserInfoList() throws SQLException {
        ArrayList vArrayList = new ArrayList();
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(vIdUsuario);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vArrayList, 
                                                          "FARMA_SECURITY.OBTIENE_DATO_USUARIO_LOGIN(?,?,?)", 
                                                          vParameters);
        return vArrayList;
    }

    /**
     * Actualiza el password del Usuario
     */
    private void changePassword() throws SQLException {
        /*String query = 
            "UPDATE PBL_USU_LOCAL" + "   SET CLAVE_USU = '" + vNuevaClave.toLowerCase() + 
            "'" + " WHERE COD_GRUPO_CIA = '" + FarmaVariables.vCodGrupoCia + 
            "'" + "   AND COD_LOCAL = '" + FarmaVariables.vCodLocal + "'" + 
            "   AND SEC_USU_LOCAL = '" + FarmaVariables.vNuSecUsu + "'";
        System.out.println(query);
        FarmaDBUtility.executeSQLUpdate(query);*/
        
         /**
          * Se cambia el password del usuario
          * @AUTHOR JCORTEZ
          * @SINCE 04.09.09
          * */
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(vIdUsuario);
        vParameters.add(FarmaVariables.vNuSecUsu);
        vParameters.add(vNuevaClave);
        System.out.println("::::::::CAMBIO CLAVE :::::::"+vParameters);
        FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_SECURITY.CAMBIO_CLAVE(?,?,?,?,?)",vParameters,false);
    }


}
