package mifarma.ptoventa.fidelizacion.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;

import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.convenio.DlgDatosConvenio;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBFidelizacion {
    
    private static final Log log = LogFactory.getLog(DBFidelizacion.class);

    private static ArrayList parametros = new ArrayList();
    
    private static List prmtros = new ArrayList();
    
    public DBFidelizacion() {
    }
    
    /**
     * @Author DVELIZ
     * @Since  26.09.08
     * @param pTableModel
     * @throws SQLException
     */
    public static void listaCamposFidelizacion(FarmaTableModel pTableModel) 
                                                            throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(VariablesCampana.vCodCampana);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                "PTOVENTA_CAMP.CAMP_LISTA_CAMPANA_CAMPOS(?)",parametros,false);
    }
    
    public static String validaClienteLocal(String vCodTarjeta)
                                                            throws SQLException{
        
        parametros = new ArrayList();
        parametros.add(vCodTarjeta);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("jcallo: valida cliente local "+parametros);
        log.debug("invocando a  PTOVENTA_FIDELIZACION.FID_F_VAR_VALIDA_CLIENTE (?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
            "PTOVENTA_FIDELIZACION.FID_F_VAR_VALIDA_CLIENTE (?,?)", parametros);
    }

    public static String verificaCodBarraLocal(String cadena) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(cadena);
      System.out.println(parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr(
             "PTOVENTA_VTA.VALIDA_COD_BARRA(?,?,?)",parametros);
    }
    
    public static String validaTarjetaLocal(String cadena)throws SQLException{
        parametros = new ArrayList();
        parametros.add(cadena);
        parametros.add(FarmaVariables.vCodLocal);
        System.out.println(parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
               "PTOVENTA_FIDELIZACION.FID_F_VAR_VALIDA_TARJETA(?,?)",parametros);
    }
    
    
    public static String validaTarjetaMatriz(String cadena, 
                                             String pIndCloseConecction)
                                                            throws SQLException{
        parametros = new ArrayList();
        parametros.add(cadena);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_MATRIZ_FID.FID_F_VAR_VALIDA_CLIENTE(?,?)"+parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr(
               "PTOVENTA_MATRIZ_FID.FID_F_VAR_VALIDA_CLIENTE(?,?)",
               parametros,
               FarmaConstants.CONECTION_MATRIZ,
               pIndCloseConecction);
    }
    
    public static void listarCamposFidelizacion(FarmaTableModel pTableModel) 
                                                            throws SQLException{
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                "PTOVENTA_FIDELIZACION.FID_F_CUR_LISTA_FIDELIZACION",
                parametros,
                false);
    }
    
    public static void obtieneInfoCliente(String vCodTarjeta, 
                                          ArrayList array) throws SQLException{
       parametros = new ArrayList();
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(vCodTarjeta);
       System.out.println("DIEGO PTOVENTA_FIDELIZACION.FID_F_CUR_DATOS_CLIENTE(?,?,?):" + parametros);
       FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                    "PTOVENTA_FIDELIZACION.FID_F_CUR_DATOS_CLIENTE(?,?,?)",
                    parametros);
    }
    
    public static void obtieneInfoClienteMatriz(String vCodTarjeta, 
                                                ArrayList array, 
                                                String pIndCloseConecction) 
                                                            throws SQLException{
       parametros = new ArrayList();
       parametros.add(vCodTarjeta);
       parametros.add(FarmaVariables.vCodLocal);
       System.out.println(parametros);
       FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(array,
                    "PTOVENTA_MATRIZ_FID.FID_F_CUR_DATOS_CLIENTE(?,?)",
                    parametros,
                    FarmaConstants.CONECTION_MATRIZ,
                    pIndCloseConecction);
    }
    
    /**
       * Obtiene las campañas que tiene asociado la tarjeta
       * @author Dubilluz 
       * @param pArreglo
       * @param pNumTarjeta
       * @throws SQLException
       */
    public static void agregaCampañasXFidelizacion(ArrayList pArreglo,
                                    String pNumTarjeta) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pNumTarjeta.trim());
        System.out.println("invocando a PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_TARJETA(?,?,?) "+parametros);
      FarmaDBUtility.executeSQLStoredProcedureArrayList(pArreglo,
      "PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_TARJETA(?,?,?)",parametros);
    }
    
    
    /**
     * Valida si la tarjeta tiene asociado al DNI ingresado
     * @author DUBILLUZ
     * @param pDni
     * @param pTarjeta
     * @return
     * @throws SQLException
     */
    public static String buscaDNI(String pDni, 
                                  String pTarjeta) throws SQLException {
        parametros = new ArrayList();


        parametros.add(pDni.trim());
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTarjeta.trim());
        System.out.println(parametros);

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_CHAR_BUSCA_DNI(?,?,?)", 
                                                           parametros);
    }
    
    public static void insertarClienteFidelizacion()throws SQLException{
        
        parametros = new ArrayList();
        parametros.add(VariablesFidelizacion.vDniCliente.trim());
        //parametros.add(VariablesFidelizacion.vNumTarjeta);
        parametros.add(VariablesFidelizacion.vNomCliente.trim());
        parametros.add(VariablesFidelizacion.vApePatCliente.trim());
        
        parametros.add(VariablesFidelizacion.vApeMatCliente.trim());
        parametros.add(VariablesFidelizacion.vEmail.trim());
        parametros.add(VariablesFidelizacion.vTelefono.trim());
        parametros.add(VariablesFidelizacion.vSexo.trim());
        parametros.add(VariablesFidelizacion.vDireccion.trim());
        parametros.add(VariablesFidelizacion.vFecNacimiento.trim());
        parametros.add(VariablesFidelizacion.vNumTarjeta.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        parametros.add(VariablesFidelizacion.vIndEstado.trim());
        //parametros.add(FarmaVariables.vCodLocal);        
        //parametros.add(FarmaVariables.vIdUsu);
        //parametros.add(VariablesFidelizacion.vIndEstado);
        parametros.add(VariablesFidelizacion.Tip_doc.trim());
        parametros.add(VariablesFidelizacion.Usu_Confir.trim());
        System.out.println("i_u cliente DU:"+parametros);
        
        FarmaDBUtility.executeSQLStoredProcedure(null,
                "PTOVENTA_FIDELIZACION.FID_P_INSERT_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                parametros, false);
    }
    
    
    public static void buscarTarjetasXDNIMatriz(FarmaTableModel pTableModel,
                                          String pDni, 
                                          String pIndCloseConecction)throws SQLException{
        pTableModel.clearTable();                                  
        parametros = new ArrayList();
        parametros.add(pDni);
        parametros.add(FarmaVariables.vCodLocal);
        System.out.println(parametros);
        
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(pTableModel,
                "PTOVENTA_MATRIZ_FID.FID_F_CUR_TARJETA_X_DNI(?,?)", 
                parametros, 
                false,
                FarmaConstants.CONECTION_MATRIZ,
                pIndCloseConecction);
                                                       
    }
    
    public static void buscarTarjetasXDNI(FarmaTableModel pTableModel,
                                          String pDni)throws SQLException{
        pTableModel.clearTable();                                  
        parametros = new ArrayList();
        parametros.add(pDni);
        parametros.add(FarmaVariables.vCodLocal);
        System.out.println("PTOVENTA_FIDELIZACION.FID_F_CUR_TARJETA_X_DNI:"+ parametros);
        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                "PTOVENTA_FIDELIZACION.FID_F_CUR_TARJETA_X_DNI(?,?)", 
                parametros, 
                false);
                                                       
    }
    
    public static void getCamposTarjeta(ArrayList array) 
                                                            throws SQLException{
       parametros = new ArrayList();
       System.out.println(parametros);
       FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                    "PTOVENTA_FIDELIZACION.FID_F_CUR_CAMPOS_FID",
                    parametros);
    }    
    
    public static void getDatosDNI(ArrayList array,String pTarjeta) 
                                                            throws SQLException{
       parametros = new ArrayList();
       System.out.println(parametros);
       parametros.add(pTarjeta.trim());
       FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                    "PTOVENTA_FIDELIZACION.FID_F_CUR_DATOS_DNI(?)",
                    parametros);
    }
    


    
    public static void getDatosExisteDNI(ArrayList array,String pDni) 
                                                            throws SQLException{
       parametros = new ArrayList();
       //System.out.println(parametros);
       parametros.add(pDni.trim());
       log.info("PTOVENTA_FIDELIZACION.FID_F_CUR_DATOS_EXISTE_DNI(?):"+parametros);
       FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                    "PTOVENTA_FIDELIZACION.FID_F_CUR_DATOS_EXISTE_DNI(?)",
                    parametros);
    }


    public static void getDatosExisteDNI_Matriz(ArrayList array,String pDNI,String pIndCloseConecction) 
                                                            throws SQLException{
       parametros = new ArrayList();
       System.out.println(parametros);
       parametros.add(pDNI.trim());
       FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(array,
                    "PTOVENTA_MATRIZ_FID.FID_F_CUR_DATOS_DNI(?)",
                    parametros,
                    FarmaConstants.CONECTION_MATRIZ,pIndCloseConecction);
    }    
    
    
    /**
       * Inserta en la tabla FID_TARJETA_PEDIDO 
       * @Author DVELIZ
       * @since 02.10.08
       * @param pNumPed
       * @throws SQLException
       */
//    public static void insertarTarjetaPedido() throws SQLException{
//        
//        parametros = new ArrayList();
//        parametros.add(FarmaVariables.vCodGrupoCia);
//        parametros.add(FarmaVariables.vCodLocal);
//        parametros.add(VariablesVentas.vNum_Ped_Vta);
//        parametros.add(VariablesFidelizacion.vNumTarjeta);
//        parametros.add(VariablesFidelizacion.vDniCliente);
//        parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Dcto_Ped)));
//        parametros.add(FarmaVariables.vIdUsu);
//        //dubilluz - 21.05.2012
//
////        // dubilluz 21.05.2012
////        if(VariablesFidelizacion.vSIN_COMISION_X_DNI)
////          parametros.add(FarmaConstants.INDICADOR_N);
////        else
////          parametros.add(FarmaConstants.INDICADOR_S);
////        
////        parametros.add(VariablesFidelizacion.V_NUM_CMP);
////        parametros.add(VariablesFidelizacion.V_NOMBRE);
////        parametros.add(VariablesFidelizacion.V_DESC_TIP_COLEGIO);
////        parametros.add(VariablesFidelizacion.V_TIPO_COLEGIO);
////        parametros.add(VariablesFidelizacion.V_COD_MEDICO);
//            
//        System.out.println(parametros);
//        FarmaDBUtility.executeSQLStoredProcedure(null, 
//              "PTOVENTA_FIDELIZACION.FID_P_INSERT_TARJ_PED(?,?,?,?,?,?,?,?,?,?,?,?,?)",
//              parametros, false);
//        
//        
//    }
    
    
    /**
           * Inserta en la tabla FID_TARJETA_PEDIDO 
           * @Author DVELIZ
           * @since 02.10.08
           * @param pNumPed
           * @throws SQLException
           */
        public static void insertarTarjetaPedido() throws SQLException{
            
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(VariablesVentas.vNum_Ped_Vta);
            parametros.add(VariablesFidelizacion.vNumTarjeta);
            parametros.add(VariablesFidelizacion.vDniCliente);
            parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesVentas.vVal_Dcto_Ped)));
            parametros.add(FarmaVariables.vIdUsu);
            System.out.println(parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null, 
                  "PTOVENTA_FIDELIZACION.FID_P_INSERT_TARJ_PED(?,?,?,?,?,?,?)",
                  parametros, false);
            
            
        }
    
    public static String obtieneDatosClienteImpresion(String pNumPed)
                                                            throws SQLException{
        
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed);
        System.out.println(parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr( 
              "PTOVENTA_FIDELIZACION.FID_F_CUR_OBTIENE_CLI_IMPR(?,?,?)",
              parametros);
    }
    
    public static void insertarClienteLocal()throws SQLException{
        
        parametros = new ArrayList();
        parametros.add(VariablesFidelizacion.vDniCliente);
        parametros.add(VariablesFidelizacion.vNomCliente);
        parametros.add(VariablesFidelizacion.vApePatCliente);
        parametros.add(VariablesFidelizacion.vApeMatCliente);
        parametros.add(VariablesFidelizacion.vEmail);
        parametros.add(VariablesFidelizacion.vTelefono);
        parametros.add(VariablesFidelizacion.vSexo);
        parametros.add(VariablesFidelizacion.vDireccion);
        parametros.add(VariablesFidelizacion.vFecNacimiento);
        parametros.add(VariablesFidelizacion.vNumTarjeta);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesFidelizacion.vIndEstado);
        parametros.add(VariablesFidelizacion.Tip_doc.trim());
        parametros.add(VariablesFidelizacion.Usu_Confir.trim());
        System.out.println("i_u CLI Matriz Local:"+parametros);
        
        FarmaDBUtility.executeSQLStoredProcedure(null,
                "PTOVENTA_FIDELIZACION.FID_P_INSERT_CLI_LOCAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                parametros, false);
    }    
    
    /**
       * Obtiene parametro de validacion de longitud de doc de identificacion
       * @Author JCALLO
       * @since 06.10.2008
       * @throws SQLException
       */
    public static String obtenerParamDocIden() throws SQLException{
        
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr(
               "PTOVENTA_FIDELIZACION.FID_F_VAR_VAL_DOC_IDEN()",parametros);
        
    }

    /**
     * Metodo encargado de obtener si la tarjeta es valido y disponible para ser usado
     * @Author JCALLO
     * @since  18.12.2008
     * @param cadena
     * @return
     * @throws SQLException
     */
    public static String isTarjetaDisponibleLocal(String cadena)throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cadena);
        System.out.println("invocando a PTOVENTA_FIDELIZACION.FID_F_CHAR_VALIDA_TARJETA(?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
               "PTOVENTA_FIDELIZACION.FID_F_CHAR_VALIDA_TARJETA(?,?,?)",parametros);
    }
    
    
    
   /**
    * Metodo encargado de obtener el nuevo codigo ean de una nueva tarjeta de
    * fidelizacion creada en local.
    * @author   dveliz
    * @since    13.02.2009
    */
    public static String generaNuevaTarjetaFidelizacion(String vConcatenado) throws SQLException{
        parametros = new ArrayList();   
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vConcatenado);
        parametros.add(FarmaVariables.vIdUsu);
        System.out.println("invocando a PTOVENTA_FIDELIZACION.FID_F_CHAR_CREA_NEW_TARJ_FID(?,?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
               "PTOVENTA_FIDELIZACION.FID_F_CHAR_CREA_NEW_TARJ_FID(?,?,?,?)",parametros);
    }
    
    /**
     * Metodo encargado inserta en matriz una nueva tarjeta de
     * fidelizacion creada en local.
     * @author   dveliz
     * @since    14.02.2009
     */
     public static void insertarNuevaTarjetaFidelizacionMatriz(
                                                               String vTarjeta,
                                                               String vDNICliente,
                                                               String pIndCloseConecction
                                                               ) throws SQLException{
         parametros = new ArrayList();   
         
         parametros.add(vTarjeta);
         parametros.add(FarmaVariables.vCodLocal);
         parametros.add(FarmaVariables.vIdUsu);
         parametros.add(vDNICliente);
         parametros.add(VariablesCampAcumulada.vNomCliente);
         parametros.add(VariablesCampAcumulada.vApePatCliente);
         parametros.add(VariablesCampAcumulada.vApeMatCliente);
         parametros.add(VariablesCampAcumulada.vEmail);
         parametros.add(VariablesCampAcumulada.vTelefono);
         parametros.add(VariablesCampAcumulada.vSexo);
         parametros.add(VariablesCampAcumulada.vDireccion);
         parametros.add(VariablesCampAcumulada.vFecNacimiento);
         System.out.println("invocando a PTOVENTA_MATRIZ_FID.FID_P_INSERT_TARJ_FID_MATRIZ(?,?,?,?,?," +
                                                                                          "?,?,?,?,?):"+parametros);
         FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                "PTOVENTA_MATRIZ_FID.FID_P_INSERT_TARJ_FID_MATRIZ(?,?,?,?,?,?,?,?,?,?,?,?)",
                parametros,false,FarmaConstants.CONECTION_MATRIZ,pIndCloseConecction);
     }
    
    public static void getDatosTarjeta(ArrayList array,String pTarjeta)throws SQLException{
       parametros = new ArrayList();
       
       parametros.add(pTarjeta.trim());
       parametros.add(FarmaVariables.vCodLocal);
       System.out.println("Consulta Tarjeta Matriz:"+parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(
                                                                array,
                                                                "PTOVENTA_MATRIZ_FID.FID_F_CUR_TARJETA(?,?)",
                                                                parametros,FarmaConstants.CONECTION_MATRIZ,
                                                                FarmaConstants.INDICADOR_N
                                                                );       
    }
    
    public static void insertTarjeta(String pTarjeta,
                                     String pDNI ,
                                     String pCodLocal)throws SQLException{
       parametros = new ArrayList();
       
       parametros.add(pTarjeta.trim());
       parametros.add(pCodLocal.trim());
       parametros.add(FarmaVariables.vIdUsu);
       parametros.add(pDNI.trim());
       System.out.println("Insert tarjeta Matriz a Local:"+parametros);
       FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_FIDELIZACION.FID_P_INSERTA_TARJETA_FID(?,?,?,?)",
                                                               parametros,false
                                                               );
    }
    
    /**
     * Retorna el valor S o N 
     * Si existe o no la tarjeta
     * @return
     */
    public static String getExisteTarjeta(String pCodTarjeta)throws SQLException{
        parametros = new ArrayList();
        parametros.add(pCodTarjeta.trim());
        log.debug("Consulta si existe la tarjeta:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_CHAR_BUSCA_TARJETA(?)",
                                                    parametros);
    }
    
    
    /**
     * Obtiene todas la campañas automaticas asociadas a la tarjetas de fidelizacion de acuerdo al cliente
     * @author JCALLO
     * @since JCALLO
     * @param pNumTarjeta
     * @throws SQLException
     */
    public static List obtenerCampaniasXFidelizacion(String pNumTarjeta) throws SQLException {
    	prmtros = new ArrayList();
    	prmtros.add(FarmaVariables.vCodGrupoCia);
    	prmtros.add(FarmaVariables.vCodLocal);
    	prmtros.add(pNumTarjeta.trim());
        //datos de forma de PAGO
        prmtros.add(VariablesFidelizacion.vIndUsoEfectivo.trim());
        prmtros.add(VariablesFidelizacion.vIndUsoTarjeta.trim());
        prmtros.add(VariablesFidelizacion.vCodFPagoTarjeta.trim());
    	log.debug("invocando a PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_TARJETA_NEW(?,?,?,?,?,?):"+prmtros);
    	return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_TARJETA_NEW(?,?,?,?,?,?)",prmtros);
  }
    
   /*
   \**
    * Obtiene todas la campañas automaticas sin fidelizar.
    * @author JQUISPE 23.04.2010
    * @since JQUISPE  23.04.2010 
    * @throws SQLException
    *\
   public static List obtenerCampaniasAutSinFidelizacion() throws SQLException {
       prmtros = new ArrayList();
       prmtros.add(FarmaVariables.vCodGrupoCia);
       prmtros.add(FarmaVariables.vCodLocal);
       
       log.debug("invocando a PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_SIN_FID(?,?):"+prmtros);
       return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_SIN_FID(?,?)",prmtros);
   }
   
    * */
          
   
   /**
     * Metodo que verifica si el DNI esta en la Lista NEGRA
     * @author DUBILLUZ
     * @since  25.05.2009
     * @param  pCodTarjeta
     * @return
     * @throws SQLException
     */
   public static String isDNI_Anulado(String pDNI)throws SQLException{
       parametros = new ArrayList();
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(pDNI.trim());
       log.debug("Consulta si DNI es valido PTOVENTA_FIDELIZACION.FID_F_CHAR_IS_DNI_ANULADO(?,?,?):"+parametros);
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_CHAR_IS_DNI_ANULADO(?,?,?)",
                                                   parametros);
  }

  public static String isRUC_Anulado(String pRUC)throws SQLException{
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pRUC.trim());
      log.debug("Consulta si DNI es valido PTOVENTA_FIDELIZACION.FID_F_CHAR_IS_RUC_ANULADO(?,?,?) :"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_CHAR_IS_RUC_ANULADO(?,?,?)",
                                                    parametros);
  }
  
  /**
     * Retorna el ahorro acumulado del DNI segun periodo
     * @author DUBILLUZ
     * @since  28.05.2009
     * @param  pDNI
     * @return
     * @throws SQLException
     */
  public static double getAhorroDNIxPeriodo(String pDNI,String pTarjeta)throws SQLException{
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pDNI.trim());
      parametros.add(pTarjeta.trim());
      log.debug("Obtiene el ahorro de DNI segun el periodo:"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_FIDELIZACION.FID_F_NUM_AHR_PER_DNI(?,?,?,?)",
                                                            parametros);
  }
  
  /**
     * Retorna el maximo ahorro x DNI segun Periodo
     * @author DUBILLUZ
     * @since  28.05.2009
     * @return
     * @throws SQLException
     */
  public static double getMaximoAhorroDNIxPeriodo(String pDni)throws SQLException{
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pDni.trim());
      log.debug("Obtiene el Maximo Ahorro segun periodo:"+parametros);
      log.debug("PTOVENTA_FIDELIZACION.FID_F_NUM_MAX_AHR_PER_DNI(?,?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_FIDELIZACION.FID_F_NUM_MAX_AHR_PER_DNI(?,?,?)",
                                                              parametros);
 }

  public static double getMaxUnidDctoProdCampana(String pCodCampana,String pCodProd)throws SQLException{
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodCampana.trim());
      parametros.add(pCodProd.trim());
      log.debug("Obtiene el maximo de unidades descuento Producto :"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_FIDELIZACION.FID_F_NUM_MAX_UNID_PROD_CAMP(?,?,?,?)",
                                                                parametros);
  }
  
    public static String isValidaPedidoFidelizado(String pNumPed,String pRuc,String pTarj)throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pRuc);
        parametros.add(pTarj.trim());
        log.debug("valida Pedido Fidelizado PTOVENTA_FIDELIZACION.FID_F_CHAR_VALIDA_PED_FID(?,?,?,?) :"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_CHAR_VALIDA_PED_FID(?,?,?,?)",
                                                                  parametros);
    }
    
    //JCORTEZ 02.07.09
    public static void buscarTarjetasDni(ArrayList array,
                                          String pDni)throws SQLException{      
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        System.out.println(parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array,"PTOVENTA_FIDELIZACION.FID_F_CUR_OBTIENE_TARJ_CLI(?,?)",parametros);
                                    
    }
    /**
     * @author DUBILLUZ 03.10.2009
     * @param pNumPed
     * @param pRuc
     * @return
     * @throws SQLException
     */
    public static boolean isValidaDocumento(String pDni,String pFecha)throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni.trim());
        parametros.add(pFecha);
        //crear esl paquete y  el procedimiento
        log.debug("valida Pedido Fidelizado PTOVENTA_FID_RENIEC.FID_F_VALIDA_DNI_REC(?,?,?,?) :"+parametros);
        String pValor =FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.FID_F_VALIDA_DNI_REC(?,?,?,?)",parametros);
        
      if(pValor.trim().equalsIgnoreCase("S"))
          return true;
      else
          return false;//Documento no valido no esta en Reniec.//Validara y pediraq clave de Quimico del local.
    }
    
    /**
     * Se obtiene mensaje de confirmacion
     * @AUTHOR JCORTEZ
     * @SINCE 06.10.09
     * */   
    public static String getMsg() throws SQLException {
       ArrayList vParameters = new ArrayList();
       vParameters.add(FarmaVariables.vCodGrupoCia);
       vParameters.add(FarmaVariables.vCodLocal);
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.F_VAR2_GET_MENSAJE(?,?)",vParameters);
    }

    /**
     * Se envia correo de confirmaciony se crea o actualiza cliente
     * @AUTHOR JCORTEZ
     * @SINCE 06.10.09
     * */   
    public static void enviaCorreoConfirmacion(String DescDoc,String NumDoc,String Nomcli,String FecNac ,String pCodTarjeta) throws SQLException {
       ArrayList vParameters = new ArrayList();
       vParameters.add(FarmaVariables.vCodGrupoCia);
       vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(FarmaVariables.vIdUsu);
        vParameters.add(FarmaVariables.vNuSecUsu);
        vParameters.add(DescDoc);
        vParameters.add(NumDoc);
        vParameters.add(Nomcli);
        vParameters.add(FecNac);
        vParameters.add(pCodTarjeta);
        log.debug("parametros INSERT "+vParameters);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_FID_RENIEC.ENVIA_CORREO_CONFIRMACION(?,?,?,?,?,?,?,?,?)",
                                                                vParameters,false);
    }        

    /**
     * Se obtiene rol confirmacion
     * @AUTHOR JCORTEZ
     * @SINCE 06.10.09
     * */   
    public static String obtieneRolConfirmacin() throws SQLException {
       ArrayList vParameters = new ArrayList();
       vParameters.add(FarmaVariables.vCodGrupoCia);
       vParameters.add(FarmaVariables.vCodLocal);
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.F_GET_ROL_CONFIRMACION(?,?)",vParameters);
    }
    
    /**
     * @author DUbilluz
     * @return
     * @throws SQLException
     */
    public static String getIndValidaReniec() throws SQLException {
       ArrayList vParameters = new ArrayList();
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.F_VAR2_GET_IND_VALIDA_RENIEC",vParameters);
    }

    /**
     * @author dubilluz
     * @return
     * @throws SQLException
     */
    public static String getIndConfClaveReniec() throws SQLException {
       ArrayList vParameters = new ArrayList();
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.F_VAR2_GET_IND_CLAVE_CONF",vParameters);
    }
    
        
    /**
     * @author dubilluz
     * @since  20.10.2009
     * @return
     * @throws SQLException
     */
    public static String validacionTerceraDNI(
                                              String pFrmDni_in,
                                              String pFrmNombre_in,
                                              String pFrmFechNacimiento_in,
                                              String pValidaDni_in,
                                              String pValidaDniTercero_in
                                             ) throws SQLException {
       ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(FarmaVariables.vIdUsu);
        vParameters.add(pFrmDni_in.trim());
        vParameters.add(pFrmNombre_in.trim());
        vParameters.add(pFrmFechNacimiento_in.trim());
        vParameters.add(pValidaDni_in.trim());
        vParameters.add(pValidaDniTercero_in.trim());
        log.info("PTOVENTA_FID_RENIEC.F_VALIDACION_FINAL_DNI(?,?,?,?,?,?,?,?):"+vParameters);
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FID_RENIEC.F_VALIDACION_FINAL_DNI(?,?,?,?,?,?,?,?)",vParameters);
    }


    public static double getDctoPersonalizadoCampanaProd(String pCodCampana,String pCodProd)throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCampana.trim());
        parametros.add(pCodProd.trim());
        log.debug("Obtiene el descuento personalizado por producto en campana PTOVENTA_CAMPANA.GET_NUM_DSCTO_PROD_USO_CAMP:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_CAMPANA.GET_NUM_DSCTO_PROD_USO_CAMP(?,?,?,?)",
                                                              parametros);
    }
    
    /**
     * Carga de Las formas de Pago de campana
     * 09.06.2011 - DUbilluz
     * @param array
     * @throws SQLException
     */
    public static void cargaFormasPagoUsoXCampana(ArrayList array,String pCuponesIngresados)throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesFidelizacion.tmpCodCampanaCupon.trim());
        parametros.add(pCuponesIngresados.trim());
        System.out.println("PTOVENTA_FIDELIZACION.FID_F_FPAGO_USO_X_CAMPANA(?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array,"PTOVENTA_FIDELIZACION.FID_F_FPAGO_USO_X_CAMPANA(?,?,?,?)",parametros);
                                    
    }
    
    /**
     * Dubilluz - 17/06/2011 
     * @param cCodCampana
     * @return
     * @throws SQLException
     */
    public static String isValidaFormaPagoUso_x_Campana(
                                              String cCodCampana
                                             ) throws SQLException {
       ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(cCodCampana.trim());
        log.info("PTOVENTA_FIDELIZACION.FID_IS_FP_X_USO(?,?,?):"+vParameters);
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_IS_FP_X_USO(?,?,?)",vParameters);
    }

    public static String isTarjetaFp_CampAutomatica(String vCodTarjeta)
                                                            throws SQLException{
        
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vCodTarjeta);
        log.debug("invocando a  PTOVENTA_FIDELIZACION.FID_IS_TARJETA_APLICA_CAMPANA(?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
            "PTOVENTA_FIDELIZACION.FID_IS_TARJETA_APLICA_CAMPANA(?,?,?)", parametros);
    }
    
    /**
     * Graba la Tarjeta de PEDIDO
     * @param pTarjeta
     * @param pDni
     * @throws SQLException
     */
    public static void grabaTarjetaUnica(String pTarjeta,String pDni)throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTarjeta.trim());
        parametros.add(pDni.trim());
        System.out.println("PTOVENTA_FIDELIZACION.FID_P_INSERT_TARJ_UNICA(?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_FIDELIZACION.FID_P_INSERT_TARJ_UNICA(?,?,?,?)",
                                                                parametros,false);
    }
    
    public static String getDatosTarjetaIngresada(String vCodTarjeta)
                                                            throws SQLException{
        
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vCodTarjeta);
        log.debug("invocando a  PTOVENTA_FIDELIZACION.FID_F_DATO_TARJETA_INGRESADA(?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
            "PTOVENTA_FIDELIZACION.FID_F_DATO_TARJETA_INGRESADA(?,?,?)", parametros);
    }    
    
    public static String isValidaMatrizDescuento(String pNumPedido)throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido.trim());
        log.debug("invocando a  PTOVENTA_FIDELIZACION.FID_F_IS_VALIDA_MATRIZ(?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
            "PTOVENTA_FIDELIZACION.FID_F_IS_VALIDA_MATRIZ(?,?,?)", parametros);        
    }
    
      public static String isValidaPedidoFidelizadoMatriz(String pNumPed,String pRuc,String pDni)throws SQLException{
          parametros = new ArrayList();
          parametros.add(FarmaVariables.vCodGrupoCia);
          parametros.add(FarmaVariables.vCodLocal);
          parametros.add(pNumPed.trim());
          parametros.add(pRuc);
          parametros.add(pDni.trim());
          log.debug("valida Pedido Fidelizado PTOVENTA_MATRIZ_FID.FID_F_CHAR_VALIDA_PED_FID(?,?,?,?,?) :"+parametros);
          return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr(
                 "PTOVENTA_MATRIZ_FID.FID_F_CHAR_VALIDA_PED_FID(?,?,?,?,?)",
                 parametros,
                 FarmaConstants.CONECTION_MATRIZ,
                 "N");
      }   
    
    public static String existeTarjetaDNI(String pTarjeta)throws SQLException{
        parametros = new ArrayList();        
        parametros.add(pTarjeta);
        log.debug("invocando a  PTOVENTA_FIDELIZACION.fid_f_existe_dni_asociado(?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
            "PTOVENTA_FIDELIZACION.fid_f_existe_dni_asociado(?)", parametros);        
    }      
    //JQUISPE 26.07.2011 
    //devuelve el indicador de no fidelizacion para la campaña
    public static String obtenerIndFidUso(String pCodCampania) throws SQLException
    {   parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(pCodCampania);     
        System.out.println("invocando a PTOVENTA_FIDELIZACION.FID_F_GET_FID_USO(?,?) "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
        "PTOVENTA_FIDELIZACION.FID_F_GET_FID_USO(?,?)",parametros);    
    }
    
    /**
     * dubilluz 17.10.2011
     * @param pCodCampania
     * @return
     * @throws SQLException
     */
    public static String getCadenaInvierteDNI(String pCodCampania,String pCodTarjeta) throws SQLException
    {   parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCampania.trim());
        parametros.add(pCodTarjeta.trim());     
        System.out.println("invocando a PTOVENTA_FIDELIZACION.FID_F_INI_NUEVO_DNI(?,?,?,?) "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
        "PTOVENTA_FIDELIZACION.FID_F_INI_NUEVO_DNI(?,?,?,?)",parametros);    
    }

    /**
     * dubilluz  06.12.2011
     * @return
     * @throws SQLException
     */
    public static String permiteIngresoMedico()throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a  PTOVENTA_FIDELIZACION.FID_F_EXIST_CAMP_COLEGIO_MED(?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
            "PTOVENTA_FIDELIZACION.FID_F_EXIST_CAMP_COLEGIO_MED(?,?)", parametros);        
    }     
    
    /**
     * dubilluz 06.12.2011
     * @param pCodMedico
     * @return
     * @throws SQLException
     */
    public static String existeCodigoMedico(String pCodMedico)throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodMedico.trim().toUpperCase());
        log.debug("invocando a  PTOVENTA_FIDELIZACION.FID_F_EXIST_COD_MED(?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr(
            "PTOVENTA_FIDELIZACION.FID_F_EXIST_COD_MED(?,?,?)", parametros);        
    }

    /**
     * Dubilluz 06.12.2011
     * @param pNumTarjeta
     * @param pCodMedico
     * @return
     * @throws SQLException
     */
    public static List getCampanaMedicoAuto(String pNumTarjeta,String pCodMedico) throws SQLException {
        prmtros = new ArrayList();
        prmtros.add(FarmaVariables.vCodGrupoCia);
        prmtros.add(FarmaVariables.vCodLocal);
        prmtros.add(pNumTarjeta.trim());
        prmtros.add(pCodMedico.trim());
        log.debug("invocando a PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_COD_MEDICO(?,?,?,?):"+prmtros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_COD_MEDICO(?,?,?,?)",prmtros);
    }    
    
    
// dubilluz 01.06.2012 
    public static double getMaximoAhorroDNI_NEW(String pDni,String pTarjeta)throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni.trim());
        parametros.add(pTarjeta.trim());
        log.debug("Obtiene el Maximo Ahorro segun dni getMaximoAhorroDNI_NEW: "+parametros);
        log.debug("PTOVENTA_FIDELIZACION.FID_F_NUM_MAX_AHR_PER_DNI(?,?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_FIDELIZACION.FID_F_MAX_AHORRO_DIARIO(?,?,?,?)",
                                                                parametros);
    }

    // dubilluz 01.06.2012 
    public static void getCampTarjetaEspecial(ArrayList listaCamp,String pDni,String pTarjeta)throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTarjeta.trim());
        log.debug("PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_TARJ_ESPECIAL(?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaCamp,"PTOVENTA_FIDELIZACION.FID_F_CUR_CAMP_X_TARJ_ESPECIAL(?,?,?)",
                                                                parametros);
    }        


/**
     * Obtiene Datos DNI 
     * @author dubilluz
     * @since  2012.05.18
     * @param pDni
     * @return
     * @throws SQLException
     */
    public static String getDatoDNIReniec(String pDni) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        System.out.println("utility_dni_reniec.aux_existe_dni_reniec(?,?,?) :: "+ parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("utility_dni_reniec.aux_existe_dni_reniec(?,?,?)",parametros);
    }
    
    public static List busquedaMedicos(String pCodMedico) throws SQLException {
        prmtros = new ArrayList();
        prmtros.add(FarmaVariables.vCodGrupoCia);
        prmtros.add(FarmaVariables.vCodLocal);
        prmtros.add(pCodMedico.trim());
        log.debug("invocando a utility_mae_medico:"+prmtros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("utility_mae_medico.aux_cursor_num_cmp(?,?,?)",prmtros);
    }       
    
    /**
     * Obtiene Datos DNI 
     * @author dubilluz
     * @since  2012.05.18
     * @param pDni
     * @return
     * @throws SQLException
     */
    public static String getIndicadorComision() throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);        
        System.out.println(" PTOVENTA_FIDELIZACION.get_indicador_comision(?,?) :: "+ parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.get_indicador_comision(?,?)",parametros);
    }
    
}
