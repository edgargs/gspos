package mifarma.ptoventa.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.reference.VariablesAdministracion;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import mifarma.ptoventa.caja.reference.*;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : DBPtoVenta.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* LMESIA      22.02.2006   Creación<br>
*         13.01.2010   Modificación<br>
*         05.04.2010   Modificación<br>
* <br>
* @author Luis Mesia Rivera<br>
* @version 1.0<br>
*
*/

public class DBPtoVenta 
{
  
  private static final Logger log = LoggerFactory.getLogger(DBPtoVenta.class);
  
  private static ArrayList parametros = new ArrayList();

  public DBPtoVenta()
  {
  }
  
  public static void cargaListaFiltro(FarmaTableModel pTableModel, 
                                      String pTipoFiltro,
                                      String pTipoProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(pTipoFiltro);
    parametros.add(pTipoProd);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_GRAL.LISTA_FILTROS(?,?)",parametros,false);
  }
  
  public static void ejecutaRespaldoStock(String pCodProd,
                                          String pNumPedVta,
                                          String pTipoOperacion,
                                          int pCantMov,
                                          int pValFrac,
                                          String modulo) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIpPc);
    parametros.add(pCodProd);
    parametros.add(pNumPedVta);
    parametros.add(pTipoOperacion);
    parametros.add(new Integer(pCantMov));
    parametros.add(new Integer(pValFrac));
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(modulo);
    log.debug("FARMA_UTILITY.EJECUTA_RESPALDO_STK(?,?,?,?,?,?,?,?,?,?) : "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_UTILITY.EJECUTA_RESPALDO_STK(?,?,?,?,?,?,?,?,?,?)",parametros,false);
  }

  public static void cargaListaMaestros(FarmaTableModel pTableModel, String pTipoMaestro) throws SQLException {
      System.out.println("pTipoMaestro: "+pTipoMaestro);
      System.out.println("ConstantsPtoVenta.LISTA_TRANSP_CIEGA: "+ConstantsPtoVenta.LISTA_TRANSP_CIEGA);
    // Histórico de Creación/Modificación
    // ERIOS      22.03.2006   Modificación
   if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_LOTE)) //LISTA_LOTE(cCodGrupoCia_in IN CHAR, cCodLocal_in IN CHAR, cCodProd_in IN CHAR)
   {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesInventario.vCodProd_Transf);
      System.out.println("Muestra codigo de producto para lote:" + VariablesInventario.vCodProd_Transf);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_INV.LISTA_LOTE(?,?,?)",parametros,false);
    } else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR) ||
              pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR_LOCAL) ||
              pTipoMaestro.equals(ConstantsPtoVenta.LISTA_CAJERO))
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodCia);
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pTipoMaestro);
      parametros.add(VariablesCajaElectronica.vFechaCierreDia);
      System.out.println(parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE_ERN.LISTA_TRABAJADOR(?,?,?,?,?)",parametros,false);
    } else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_NUMERO_CUENTA))
    {
      parametros = new ArrayList();
      parametros.add(VariablesCajaElectronica.vCodEntidadFinanciera);
      parametros.add(VariablesCajaElectronica.vCodTipoMoneda);
      System.out.println(parametros);
      System.out.println("parametros");
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE_ERN.CE_OBTIENE_CUENTA(?,?)",parametros,false);
    } else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_PROVEEDOR))
    {
      parametros = new ArrayList();
      parametros.add(VariablesCajaElectronica.vCodServicio);
      System.out.println(parametros);
      System.out.println("parametros");
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE.CE_OBTIENE_PROVEEDOR(?)",parametros,false);
    } else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_CAJEROS_DIA_VENTA))
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesCajaElectronica.vFechaDiaCajaTurno);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE_LMR.CE_LISTA_CAJEROS_DIA_VENTA(?,?,?)",parametros,false);
    } else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_CLIENTES_CONVENIO))
    {
      parametros = new ArrayList();
      
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CONV.CONV_LISTA_CLI_CONVENIO(?)",parametros,false);
      
    }else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRANSP_CIEGA)) //  05.03.2010
    {
       System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_RECEP_CIEGA_AS.RECEP_F_LISTA_TRANSP",parametros,false);
    }else
    {
      parametros = new ArrayList();    
      parametros.add(pTipoMaestro);
      System.out.println("pTipoMaestro" + pTipoMaestro);
      parametros.add(FarmaVariables.vCodGrupoCia);
      System.out.println("FarmaVariables.vCodGrupoCia=" + FarmaVariables.vCodGrupoCia);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_GRAL.LISTA_MAESTROS(?,?)",parametros,false);
   }
  }

  public static void buscaCodigoListaMaestro(ArrayList pArrayList, String pTipoMaestro, String pCodBusqueda) throws SQLException {
    parametros = new ArrayList();
    parametros.add(pTipoMaestro);
    parametros.add(pCodBusqueda);
    parametros.add(FarmaVariables.vCodGrupoCia);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_GRAL.BUSCA_REGISTRO_LISTA_MAESTROS(?,?,?)",parametros);
  }

 public static ArrayList obtieneDatosLocal() throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
     FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"FARMA_SECURITY.OBTIENE_DATO_LOCAL(?,?)",parametros);
    return pOutParams;
  }

 public static void cargaListaAperturasDia(FarmaTableModel pTableModel) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
     for(int i=0;i<parametros.size();i++){
    System.out.println(""+i+": "+ parametros.get(i).toString());
    }
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CAJ.CAJ_LISTA_APERTURAS_DIA(?,?)",parametros,false);
  }
  
   public static void cargaListaMovsConsult(FarmaTableModel pTableModel) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
     parametros.add(VariablesAdministracion.vFecDiaVta);
     for(int i=0;i<parametros.size();i++){
    System.out.println(""+i+": "+ parametros.get(i).toString());
    }
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CAJ.CAJ_LISTA_MOVIMIENTOS_CAJA(?,?,?)",parametros,false);
  }
  
 
  
   public static void cargaListaFormasPago(FarmaTableModel pTableModel) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesPtoVenta.vSecMovCaja.trim());//VariablesCaja.vSecMovCajaOrigen
    parametros.add(VariablesPtoVenta.vTipOpMovCaja);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CAJ.CAJ_OBTIENE_FORMAS_PAGO_ARQUEO(?,?,?,?)",parametros,false);
  }

   public static void cargaListaFormasPagoConsulta(FarmaTableModel pTableModel) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesPtoVenta.vSecMovCaja.trim()); //VariablesCaja.vSecMovCajaOrigen
     System.out.println("cargaListaFormasPagoConsulta:vSecMovCaja"+VariablesPtoVenta.vSecMovCaja);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CAJ.CAJ_DETALLES_FORM_PAGO_CONSULT(?,?,?)",parametros,false);
  }
  
    public static ArrayList obtieneDatosArqueo() throws SQLException {
      ArrayList pOutParams = new ArrayList();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia.trim());
      parametros.add(FarmaVariables.vCodLocal.trim());
      parametros.add(VariablesPtoVenta.vSecMovCaja.trim());//VariablesCaja.vSecMovCajaOrigen
      FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_CAJ.CAJ_OBTIENE_VALORES_ARQUEO(?,?,?)",parametros);
      return pOutParams;
    } 
 
  
public static String ProcesaDatosArqueo(String pTipMov)throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pTipMov.trim());
    parametros.add(new Integer(VariablesPtoVenta.vNumCaja.trim()));//nNumCaj_in  
    parametros.add(FarmaVariables.vNuSecUsu);//cSecUsu_in    
    parametros.add(FarmaVariables.vIdUsu);//cIdUsu_in  
    parametros.add(VariablesPtoVenta.vSecMovCaja.trim());//cSecMovCaja_in      
    //parametros.add(pTipoComprobante);
    parametros.add(FarmaVariables.vIpPc);//cIpMovCaja 
    parametros.add(VariablesPtoVenta.vTipOpMovCaja);  //cTipOp_in
  
  for(int i=0;i<parametros.size();i++){
    System.out.println(""+i+": "+ parametros.get(i).toString());
  }
 return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Caj.CAJ_F_PROCESA_VALORES_ARQUEO(?,?,?,?,?,?,?,?,?)",parametros);
}

  public static String generarArqueoCaja(String pTipMov,
                                         String pCantBolEmi,      
                                         String pMonBolEmi,
                                         String pCantFacEmi,
                                         String pMontFacEmi,
                                         String pCantGuiaEmi,
                                         String pMonGuiaEmi,
                                         String pCantBolAnu,
                                         String pMonBolAnu,
                                         String pCantFacAnu,
                                         String pMonFacAnu,
                                         String pCantGuiaAnu,
                                         String pMonGuiaAnu,
                                         String pCantBolTot,
                                         String pMonBolTot,
                                         String pCantFactTot,
                                         String pMonFactTot,
                                         String pCantGuiaTot,
                                         String pMonGuiaTot,
                                         String pMonTotGen,
                                         String pMonTotAnu,
                                         String pMonTot,
                                         String pCantNCBol,
                                         String pMonNCBol,
                                         String pCantNCFact,
                                         String pMonNCFact,
                                         String pMonNCTot) throws SQLException {
      
    
    parametros = new ArrayList();
    parametros.add(pTipMov);
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(new Integer(VariablesPtoVenta.vNumCaja.trim()));
    parametros.add(FarmaVariables.vNuSecUsu);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(new Integer(pCantBolEmi));    
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonBolEmi)));
    parametros.add(new Integer(pCantFacEmi.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontFacEmi)));
    parametros.add(new Integer(pCantGuiaEmi.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonGuiaEmi)));
    parametros.add(new Integer(pCantBolAnu.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonBolAnu)));
    parametros.add(new Integer(pCantFacAnu.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonFacAnu)));
    parametros.add(new Integer(pCantGuiaAnu.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonGuiaAnu)));
    parametros.add(new Integer(pCantBolTot.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonBolTot)));
    parametros.add(new Integer(pCantFactTot.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonFactTot)));
    parametros.add(new Integer(pCantGuiaTot.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonGuiaTot)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTotGen)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTotAnu)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTot)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pCantNCBol)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonNCBol)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pCantNCFact)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonNCFact)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonNCTot)));
    parametros.add(FarmaVariables.vIpPc);
    for(int i=0;i<parametros.size();i++){
      System.out.println(""+i+": "+ parametros.get(i).toString());
    }
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_REGISTRA_ARQUEO_CAJA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros);
  }
    
  
  
       public static ArrayList cargaListaFormasPagoArray() throws SQLException {
    ArrayList arrayFP=new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesPtoVenta.vSecMovCaja.trim());
     parametros.add(VariablesPtoVenta.vTipOpMovCaja);

    FarmaDBUtility.executeSQLStoredProcedureArrayList(arrayFP,"PTOVENTA_CAJ.CAJ_OBTIENE_FORMAS_PAGO_ARQUEO(?,?,?,?)",parametros);
    
    return arrayFP;
  }

  public static void guardaValoresComprobante(String pSecMovCaja) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pSecMovCaja);
    parametros.add(VariablesPtoVenta.vTipOpMovCaja);
    String valor = VariablesPtoVenta.vTipOpMovCaja;
    System.out.println("guardaValoresComprobante:pSecMovCaja"+pSecMovCaja);
    System.err.println("guardaValoresComprobante:VariablesPtoVenta.vTipOpMovCaja:"+ valor);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CAJ.CAJ_ALMACENAR_VALORES_COMP(?,?,?,?)",parametros,false);
  }

   public static ArrayList obtieneDatosArqueoConsulta() throws SQLException {
    System.out.println("obtieneDatosArqueoConsulta:vSecMovCaja"+VariablesPtoVenta.vSecMovCaja.trim());
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(VariablesPtoVenta.vSecMovCaja.trim());//VariablesCaja.vSecMovCajaOrigen.trim()
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_CAJ.CAJ_OBTIENE_VAL_ARQUEO_CONSULT(?,?,?)",parametros);
    return pOutParams;
  }
   public static int verificaIPValida() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIpPc);
   
    System.out.println("verificaIPValida");
    System.out.println(parametros.toString());
 
   return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICA_IP_VALIDA(?,?,?)",parametros);
  }
  
   public static void realizaViajero() throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add("N");
      parametros.add(FarmaVariables.vIdUsu);     
      System.out.println(parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VIAJERO.VIAJ_PROCESAR_VIAJERO(?,?,?,?)",parametros,false); 
    }

   public static ArrayList obtieneInfoDelivery() throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(VariablesPtoVenta.vSecMovCajaOrigen.trim());
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_CAJ.CAJ_PEDIDO_DEL_CAJ(?,?,?)",parametros);
    return pOutParams;
  }
  
  public static int obtieneCantidadSesiones(String pNombrePC, String pUsuarioConexion) throws SQLException {
    parametros = new ArrayList();
    parametros.add(pNombrePC);
    parametros.add(pUsuarioConexion);
    return FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_UTILITY.OBTIENE_CANTIDAD_SESIONES(?,?)",parametros);
  }

  
   /**
    * Se valida cambio de clave por usuario
    * @AUTHOR  
    * @SINCE 04.09.09
    * */
  public static String validaCambioClave() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(FarmaVariables.vNuSecUsu);
        System.out.println("vParameters :"+vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.VALIDA_CAMBIO_CLAVE(?,?,?)",vParameters);
  }
  
    /**
     * Obtiene indicador para activar la funcionalidad recepción de almacen o recepcion ciega
     * @AUTHOR  
     * @SINCE 17.12.09
     * */
    public static String obtieneIndicadorTipoRecepcionAlmacen() throws SQLException {
         ArrayList vParameters = new ArrayList();
         vParameters.add(FarmaVariables.vCodGrupoCia);
         vParameters.add(FarmaVariables.vCodLocal);         
         System.out.println("vParameters :"+vParameters);
         return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.INV_F_GET_IND_TIPO_RECEP_ALM(?,?)",vParameters);
    }
    
    /**
     * Revierte pruebas en local nuevo
     * @AUTHOR  
     * @SINCE 18.12.09
     * */
     public static void reviertePruebasEnLocalNuevo(String indCN) throws SQLException
      {
          parametros = new ArrayList();          
          parametros.add(FarmaVariables.vCodLocal);  
          parametros.add(indCN.trim());
          log.debug("invocando a PTOVENTA_CARGA_INICIAL.CARGA_INICIAL_P_REVERTIR(?,?):"+parametros);
          FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CARGA_INICIAL.CARGA_INICIAL_P_REVERTIR(?,?)", parametros, false);
      }
    
    /**
     * Actualiza el indicador de revertir en 'S' en el local
     * @AUTHOR  
     * @SINCE 18.12.09
     * */
     public static void actualizaIndicadorRevertir() throws SQLException
      {
          parametros = new ArrayList();          
          parametros.add(FarmaVariables.vCodGrupoCia); 
          parametros.add(FarmaVariables.vCodLocal);                 
          parametros.add(FarmaVariables.vIdUsu);  
          log.debug("invocando a PTOVENTA_CARGA_INICIAL.P_ACT_REVERTIR_LOCAL(?,?,?):"+parametros);
          FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CARGA_INICIAL.P_ACT_REVERTIR_LOCAL(?,?,?)", parametros, false);
      }
    /**
     * Actualiza el indicador de revertir en 'S' en el local
     * @AUTHOR  
     * @SINCE 18.12.09
     * */
     public static boolean obtieneIndicadorRevertirLocal() throws SQLException
      {
          String pResultado="";
          parametros = new ArrayList();          
          parametros.add(FarmaVariables.vCodGrupoCia); 
          parametros.add(FarmaVariables.vCodLocal);                  
          log.debug("invocando a PTOVENTA_CARGA_INICIAL.F_GET_IND_REVERTIR_LOCAL(?,?):"+parametros);
          pResultado=FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CARGA_INICIAL.F_GET_IND_REVERTIR_LOCAL(?,?)", parametros);
          if (pResultado.equalsIgnoreCase("N")){
              return true;
          }
          return false;
      } 
    
    /**
     * Graba fecha inicio y fin de pruebas
     * @AUTHOR  
     * @SINCE 07.01.10
     * */
    public static void grabaInicioFinPrueba(String tipo) throws SQLException {        
        parametros = new ArrayList();          
        parametros.add(FarmaVariables.vCodGrupoCia); 
        parametros.add(FarmaVariables.vCodLocal); 
        parametros.add(tipo);
        parametros.add(FarmaVariables.vIdUsu); 
        log.debug("invocando a PTOVENTA_CARGA_INICIAL.P_GRABA_INICIO_FIN_PRUEBA(?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CARGA_INICIAL.P_GRABA_INICIO_FIN_PRUEBA(?,?,?,?)", parametros,false);
    }
    
    /**
     * Obtiene cantidad de pruebas iniciada
     * @AUTHOR  
     * @SINCE 07.01.10
     * */
    public static int obtieneCantidadPruebas() throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia); 
      parametros.add(FarmaVariables.vCodLocal); 
      log.debug("invocando a PTOVENTA_CARGA_INICIAL.F_NUM_GET_CANT_PRUEBAS(?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CARGA_INICIAL.F_NUM_GET_CANT_PRUEBAS(?,?)",parametros);
    }
    
    /**
     * Obtiene cantidad de pruebas finalizadas, es decir con inicio y fin
     * @AUTHOR  
     * @SINCE 07.01.10
     * */
    public static int obtieneCantidadPruebasCompletas() throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia); 
      parametros.add(FarmaVariables.vCodLocal); 
      log.debug("invocando a PTOVENTA_CARGA_INICIAL.F_NUM_GET_CANT_PRUEBAS_COMP(?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CARGA_INICIAL.F_NUM_GET_CANT_PRUEBAS_COMP(?,?)",parametros);
    }
    
    /**
     * Obtiene fecha inicio de pruebas
     * @AUTHOR  
     * @SINCE 07.01.10
     * */
    public static String obtieneFechaInicioDePruebas() throws SQLException {
         ArrayList vParameters = new ArrayList();
         vParameters.add(FarmaVariables.vCodGrupoCia);
         vParameters.add(FarmaVariables.vCodLocal);         
        log.debug("invocando a PTOVENTA_CARGA_INICIAL.F_GET_CHR_FECHA_INICIO_PRUEBAS(?,?):"+parametros);
         return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CARGA_INICIAL.F_GET_CHR_FECHA_INICIO_PRUEBAS(?,?)",vParameters);
    }
    
    /**
     * Obtiene el indicador si se debe o no revertir los cambios en local
     * @author   
     * @since   13.01.2010
     */
    public static String obtenerIndReverPermitido()throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_CARGA_INICIAL.F_GET_CHR_REVER_VALIDO "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CARGA_INICIAL.F_GET_CHR_REVER_VALIDO(?,?)",parametros);
    }
    
    /**
     * Indicador de proceso de reversion
     * @AUTHOR   
     * @SINCE  19.01.10
     */
    public static String obtenerIndReverLocal()throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_CARGA_INICIAL.F_IND_PROCE_REVERTIR "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CARGA_INICIAL.F_IND_PROCE_REVERTIR(?,?)",parametros);
    
    }
    
    /**
     * Obtiene la direccion Domicilio Fiscal
     * @author ERIOS
     * @since 06.06.2013
     * @return
     * @throws SQLException
     */
    public static ArrayList obtieneDireccionMatriz()throws SQLException{
        ArrayList retorno = new ArrayList();
        ArrayList parametros = new ArrayList();      
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal); 
        FarmaDBUtility.executeSQLStoredProcedureArrayList(retorno,"PTOVENTA_GRAL.GET_DIRECCION_FISCAL(?,?,?)",parametros);
        return retorno;
    }
    
    /**
     * Obtiene indicador para dirección Fiscal De Matriz.
     * @author   
     * @since   19.01.2010
     */    
    public static boolean obtieneIndDirMatriz()throws SQLException{
        String ind = "";
        boolean flag = false;
        ArrayList parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_VTA.VTA_F_CHAR_IND_OBT_DIR_MATRIZ():");         
        ind = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_F_CHAR_IND_OBT_DIR_MATRIZ()",parametros);
        if(ind.trim().equalsIgnoreCase("S")){
            flag = true;
            log.debug("VariablesPtoVenta.vIndDirMatriz obt: "+flag);
        }
        else{
            flag = false;
            log.debug("VariablesPtoVenta.vIndDirMatriz obt: "+flag);
        }
        return flag;    
    }    
    
}