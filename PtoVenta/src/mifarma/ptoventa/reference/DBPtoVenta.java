package mifarma.ptoventa.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.reference.VariablesAdministracion;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBPtoVenta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      22.02.2006   Creación<br>
 * ASOSA       13.01.2010   Modificación<br>
 * ASOSA       05.04.2010   Modificación<br>
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
   if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_LOTE)) 
   {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesInventario.vCodProd_Transf);
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
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE_ERN.LISTA_TRABAJADOR(?,?,?,?,?)",parametros,false);
    } else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_NUMERO_CUENTA))
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodCia);
      parametros.add(VariablesCajaElectronica.vCodEntidadFinanciera);
      parametros.add(VariablesCajaElectronica.vCodTipoMoneda);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE_ERN.CE_OBTIENE_CUENTA(?,?,?)",parametros,false);
    } else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_PROVEEDOR_CE))
    {
      parametros = new ArrayList();
        parametros.add(VariablesCajaElectronica.vCodServicio);
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
      parametros.add(VariablesConvenio.vCodConvenio);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CONV.CONV_LISTA_CLI_CONVENIO(?)",parametros,false);      
    }else if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRANSP_CIEGA)) 
    {
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_RECEP_CIEGA_AS.RECEP_F_LISTA_TRANSP",parametros,false);
    }else
    {
      //ERIOS 06.03.2013 Se agrega el parametros vCodCia
      parametros = new ArrayList();    
      parametros.add(pTipoMaestro);
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodCia);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_GRAL.LISTA_MAESTROS(?,?,?)",parametros,false);
   }
  }

  public static void buscaCodigoListaMaestro(ArrayList pArrayList, String pTipoMaestro, String pCodBusqueda) throws SQLException {
    //ERIOS 06.03.2013 Se agrega el parametros vCodCia
      
    parametros = new ArrayList();
    parametros.add(pTipoMaestro);
    parametros.add(pCodBusqueda);
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodCia);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_GRAL.BUSCA_REGISTRO_LISTA_MAESTROS(?,?,?,?)",parametros);
  }
  //Cesar Huanes busca datos del proveedor
    public static void getDatosProveedor(ArrayList array,String pCodBusqueda) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodBusqueda);
      log.debug("invocando a PTOVENTA_GRAL.BUSCA_REGISTRO_LISTA_MAESTROS(?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedureArrayList(array,"PTOVENTA_GRAL.BUSCA_DATOS_PROVEEDOR(?,?,?)",parametros);
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
    log.debug(""+i+": "+ parametros.get(i).toString());
    }
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CAJ.CAJ_LISTA_APERTURAS_DIA(?,?)",parametros,false);
  }
  
   public static void cargaListaMovsConsult(FarmaTableModel pTableModel) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
     parametros.add(VariablesAdministracion.vFecDiaVta);
     for(int i=0;i<parametros.size();i++){
    log.debug(""+i+": "+ parametros.get(i).toString());
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

    public static void cargaListaFormasPagoConsulta(FarmaTableModel pTableModel) throws SQLException
    {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesPtoVenta.vSecMovCaja.trim()); //VariablesCaja.vSecMovCajaOrigen
        log.debug("cargaListaFormasPagoConsulta:vSecMovCaja"+VariablesPtoVenta.vSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CAJ.CAJ_DETALLES_FORM_PAGO_CONSULT(?,?,?)",parametros,false);
    }
  
    public static ArrayList obtieneDatosArqueo() throws SQLException
    {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(VariablesPtoVenta.vSecMovCaja.trim());//VariablesCaja.vSecMovCajaOrigen
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_CAJ.CAJ_OBTIENE_VALORES_ARQUEO(?,?,?)",parametros);
        return pOutParams;
    } 
  
    public static String ProcesaDatosArqueo(String pTipMov) throws SQLException 
    {
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
        
        for(int i=0;i<parametros.size();i++)
        {
            log.debug(""+i+": "+ parametros.get(i).toString());
        }
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Caj.CAJ_F_PROCESA_VALORES_ARQUEO(?,?,?,?,?,?,?,?,?)",parametros);
    }

    public static String generarArqueoCaja (String pTipMov,
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
                                            String pMonNCTot,
                                            //LLEIVA 11-Feb-2014
                                            String pCantTickFacEmi,
                                            String pMonTickFacEmi,
                                            String pCantTickFacAnul,
                                            String pMonTickFacAnul,
                                            String pCantNCTickFac,
                                            String pMonNCTickFac,
                                            String pCantTickFacTot,
                                            String pMonTickFacTot) throws SQLException
    {
        parametros = new ArrayList();
        parametros.add(pTipMov);                                                        //1
        parametros.add(FarmaVariables.vCodGrupoCia);                                    //2
        parametros.add(FarmaVariables.vCodLocal);                                       //3
        parametros.add(new Integer(VariablesPtoVenta.vNumCaja.trim()));                 //4
        parametros.add(FarmaVariables.vNuSecUsu);                                       //5
        parametros.add(FarmaVariables.vIdUsu);                                          //6
        parametros.add(new Integer(pCantBolEmi));                                       //7
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonBolEmi)));          //8
        parametros.add(new Integer(pCantFacEmi.trim()));                                //9
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontFacEmi)));         //10
        parametros.add(new Integer(pCantGuiaEmi.trim()));                               //11
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonGuiaEmi)));         //12
        parametros.add(new Integer(pCantBolAnu.trim()));                                //13
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonBolAnu)));          //14
        parametros.add(new Integer(pCantFacAnu.trim()));                                //15
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonFacAnu)));          //16
        parametros.add(new Integer(pCantGuiaAnu.trim()));                               //17
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonGuiaAnu)));         //18
        parametros.add(new Integer(pCantBolTot.trim()));                                //19
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonBolTot)));          //20
        parametros.add(new Integer(pCantFactTot.trim()));                               //21
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonFactTot)));         //22
        parametros.add(new Integer(pCantGuiaTot.trim()));                               //23
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonGuiaTot)));         //24
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTotGen)));          //25
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTotAnu)));          //26
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTot)));             //27
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pCantNCBol)));          //28
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonNCBol)));           //29
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pCantNCFact)));         //30
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonNCFact)));          //31
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonNCTot)));           //32
        parametros.add(FarmaVariables.vIpPc);                                           //33
        //LLEIVA 11-Feb-2014 Se añaden las variables para Ticket Factura
        if(pCantTickFacEmi==null)
            parametros.add(0);                                                          //34
        else
            parametros.add(new Integer(pCantTickFacEmi.trim()));                        //34
        
        if(pMonTickFacEmi==null)
            parametros.add(0);                                                          //35
        else
            parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTickFacEmi)));  //35
        
        if(pCantTickFacAnul==null)
            parametros.add(0);                                                          //36
        else
            parametros.add(new Integer(pCantTickFacAnul.trim()));                       //36

        if(pMonTickFacAnul==null)
            parametros.add(0);                                                          //37
        else        
            parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTickFacAnul))); //37

        if(pCantNCTickFac==null)
            parametros.add(0);                                                          //38
        else
            parametros.add(new Integer(pCantNCTickFac.trim()));                         //38
        
        if(pMonNCTickFac==null)
            parametros.add(0);                                                          //39
        else
            parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonNCTickFac)));   //39
        
        if(pCantTickFacTot==null)
            parametros.add(0);                                                          //40
        else
            parametros.add(new Integer(pCantTickFacTot.trim()));                        //40
        
        if(pMonTickFacTot==null)
            parametros.add(0);                                                          //41
        else
            parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTickFacTot)));  //41
        
        for(int i=0;i<parametros.size();i++)
        {
            log.debug(""+i+": "+ parametros.get(i).toString());
        }
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_REGISTRA_ARQUEO_CAJA(?,?,?,?,?,?,?,?,?,?," +
                                                                                                 "?,?,?,?,?,?,?,?,?,?," +
                                                                                                 "?,?,?,?,?,?,?,?,?,?," +
                                                                                                 "?,?,?,?,?,?,?,?,?,?," +
                                                                                                 "?)",parametros);
    }
    
  
  
    public static ArrayList cargaListaFormasPagoArray() throws SQLException
    {
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
    log.debug("guardaValoresComprobante:pSecMovCaja"+pSecMovCaja);
    log.info("guardaValoresComprobante:VariablesPtoVenta.vTipOpMovCaja:"+ valor);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CAJ.CAJ_ALMACENAR_VALORES_COMP(?,?,?,?)",parametros,false);
  }

    public static ArrayList obtieneDatosArqueoConsulta() throws SQLException
    {
        log.debug("obtieneDatosArqueoConsulta:vSecMovCaja"+VariablesPtoVenta.vSecMovCaja.trim());
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
   
    log.debug("verificaIPValida");
    log.debug(parametros.toString());
 
   return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICA_IP_VALIDA(?,?,?)",parametros);
  }
  
   public static void realizaViajero() throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add("N");
      parametros.add(FarmaVariables.vIdUsu);     
      log.debug("",parametros);
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
    * @AUTHOR JCORTEZ
    * @SINCE 04.09.09
    * */
  public static String validaCambioClave() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        vParameters.add(FarmaVariables.vNuSecUsu);
        log.debug("vParameters :"+vParameters);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_SECURITY.VALIDA_CAMBIO_CLAVE(?,?,?)",vParameters);
  }
  
    /**
     * Obtiene indicador para activar la funcionalidad recepción de almacen o recepcion ciega
     * @AUTHOR JCHAVEZ
     * @SINCE 17.12.09
     * */
    public static String obtieneIndicadorTipoRecepcionAlmacen() throws SQLException {
         ArrayList vParameters = new ArrayList();
         vParameters.add(FarmaVariables.vCodGrupoCia);
         vParameters.add(FarmaVariables.vCodLocal);         
         log.debug("vParameters :"+vParameters);
         return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECEP_CIEGA_JCG.INV_F_GET_IND_TIPO_RECEP_ALM(?,?)",vParameters);
    }
    
    /**
     * Revierte pruebas en local nuevo
     * @AUTHOR JCHAVEZ
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
     * @AUTHOR JCHAVEZ
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
     * @AUTHOR JCHAVEZ
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
     * @AUTHOR JCHAVEZ
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
     * @AUTHOR JCHAVEZ
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
     * @AUTHOR JCHAVEZ
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
     * @AUTHOR JCHAVEZ
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
     * @author  ASOSA
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
     * @AUTHOR  JCORTEZ
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
     * @author  JMIRANDA
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

    /**
     * Obtiene indicador de direccion local
     * @author ERIOS
     * @since 12.09.2013
     * @return
     * @throws SQLException
     */
    public static boolean obtieneIndDirLocal()throws SQLException{
        String ind = "";
        boolean flag = false;
        ArrayList parametros = new ArrayList();
        log.debug("PTOVENTA_GRAL.VTA_F_CHAR_IND_OBT_DIR_LOCAL()", parametros);         
        ind = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.VTA_F_CHAR_IND_OBT_DIR_LOCAL()",parametros);
        if(ind.trim().equalsIgnoreCase("S")){
            flag = true;
        }
        else{
            flag = false;
        }
        return flag;    
    }   
    
    /**
     * Obtiene indicador de registro de venta restringida
     * @author ERIOS
     * @since 12.09.2013
     * @return
     * @throws SQLException
     */
    public static boolean obtieneIndRegistroVentaRestringida()throws SQLException{
        String ind = "";
        boolean flag = false;
        ArrayList parametros = new ArrayList();
        log.debug("PTOVENTA_GRAL.GET_IND_REG_VTA_RESTRIG()", parametros);         
        ind = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_IND_REG_VTA_RESTRIG()",parametros);
        if(ind.trim().equalsIgnoreCase("S")){
            flag = true;
        }
        else{
            flag = false;
        }
        return flag;    
    }
    
    /**
     * Obtiene la ruta de la imagen.
     * @author  Luigy Terrazos
     * @since   28.02.2013
     */
    public static String obtieneRutaImagen() throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_RUTA_IMAGEN_MARCA(?,?,?)", parametros);
    }

    /**
     * Obtiene la Razon Social
     * @author  Luigy Terrazos
     * @since   28.02.2013
     */
    public static String obtieneRazSoc() throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        log.debug("invocando a PTOVENTA_GRAL.GET_RAZ_SOC_CIA: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_RAZ_SOC_CIA(?,?)", parametros);
    }

    /**
     * Obtiene la Direccion Fiscal
     * @author  Luigy Terrazos
     * @since   28.02.2013
     */
    public static String obtieneDirFis() throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        log.debug("invocando a PTOVENTA_GRAL.GET_DIR_FIS_CIA: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_DIR_FIS_CIA(?,?)", parametros);
    }

    /**
     * Obtiene el Telefono de la CIA
     * @author  Luigy Terrazos
     * @since   28.02.2013
     */
    public static String obtieneTelfCia() throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        log.debug("invocando a PTOVENTA_GRAL.GET_TELF_CIA: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_TELF_CIA(?,?)", parametros);
    }
    
    /**
     * Retorna el nombre de la marca asociada al local y cia
     * @author ERIOS
     * @since 06.03.2013
     * @return Nombre marca
     * @throws SQLException
     */
    public static String obtieneNombreMarcaCia() throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_GRAL.GET_NOMBRE_MARCA_CIA: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_NOMBRE_MARCA_CIA(?,?)", parametros);
    }
    
    /**
     * Obtiene el indicador de Nuevo Cobro
     * @author ERIOS
     * @since 01.04.2013
     * @return Indicador Nuevo Cobro
     * @throws SQLException
     */
    public static boolean getIndicadorNuevoCobro() throws SQLException{
        boolean flag=false;
        parametros =new ArrayList();
        String ind=FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_IND_NUEVO_COBRO()",parametros);
        if(ind.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) flag=true;
        return flag;
    }
    
    /**
     * Obtiene la informacion de la tarjeta seleccionada(a que forma de pago pertenece y que tipo de tarjeta es)
     * @author ASOSA
     * @since 24.02.2010
     * @param pArrayList
     * @param nrotarj
     * @throws SQLException
     * Modificación LLEIVA 13/Sep/2013
     */
    public static void obtenerInfoTarjeta(ArrayList pArrayList, String nrotarj, String tipoOrigen)throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nrotarj);
        //LLEIVA 12/Sep/2013 Identificación si es pagado con POS o Pinpad
        parametros.add(tipoOrigen);
        log.debug("PTOVENTA_GRAL.PVTA_F_OBTENER_TARJETA(?,?,?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_GRAL.PVTA_F_OBTENER_TARJETA(?,?,?,?,?)",parametros);
    }
    
    /* LLEIVA 12/Sep/2013 - NO SE UTILIZA ACTUALMENTE
     * public static void obtenerInfoTarjetaCITI(ArrayList pArrayList, String nrotarj, String pDescProd)throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDescProd);
        parametros.add(nrotarj);
        log.debug("PTOVENTA_RECAUDACION.RCD_F_OBTENER_BIN_TARJETA(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_RECAUDACION.RCD_F_OBTENER_BIN_TARJETA(?,?,?)",parametros);
    }*/
    
    /**
     * Obtiene directorio raiz
     * @author ERIOS
     * @since 25.06.2013
     * @return 
     * @throws SQLException
     */
    public static String getDirectorioRaiz() throws SQLException{
        parametros =new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_DIRECTORIO_RAIZ()",parametros);        
    }    
    
    /**
     * Obtiene directorio de imagenes
     * @author ERIOS
     * @since 25.06.2013
     * @return 
     * @throws SQLException
     */
    public static String getDirectorioImagenes() throws SQLException{
        parametros =new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_DIRECTORIO_IMAGENES()",parametros);        
    }     
    
    /**
     * Obtiene directorio de imagenes
     * @author ERIOS
     * @since 25.06.2013
     * @return 
     * @throws SQLException
     */
    public static String getDirectorioComprobantes() throws SQLException{
        parametros =new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_DIRECTORIO_IMPRESION()",parametros);        
    }

    /**
     * Obtiene el indicador de servicios FarmaSix
     * @author ERIOS
     * @since 16.07.2013
    */
    public static String getIndServicioFarmaSix() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_GRAL.GET_IND_FARMASIX:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_IND_FARMASIX(?,?,?)",
                                parametros); 
    }
    
    /**
     * Obtiene el indicador de servicios FarmaSix
     * @author ERIOS
     * @since 16.07.2013
    */
    public static String getIndPinpad() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_GRAL.GET_IND_PINPAD",parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_IND_PINPAD(?,?,?)",
                                parametros);     
    }
    
    /**
     * Obtiene el indicador de impresion url web
     * @author ERIOS
     * @since 16.07.2013
    */
    public static String getIndImprWeb() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_GRAL.GET_IND_IMPR_WEB(?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_IND_IMPR_WEB(?,?,?)",
                                parametros);     
    }    
    
    /**
     * Obtiene el indicador de conciliacion online
     * @author ERIOS
     * @since 29.11.2013
    */
    public static String getIndConciliaconOnline() throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_GRAL.GET_IND_CONCILIAC_ONLINE"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.GET_IND_CONCILIAC_ONLINE",
                                parametros);     
    } 
    
    /**
     * Se indica la version del sistema
     * @author ERIOS
     * @since 2.2.9
     * @throws SQLException
     */
    public static void setVersion() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesPtoVenta.vNombreModulo.toUpperCase());
        parametros.add(VariablesPtoVenta.vVersion);
        parametros.add(VariablesPtoVenta.vCompilacion);
        log.debug("FARMA_SECURITY.SET_MODULO_VERSION(?,?,?,?,?,?)"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_SECURITY.SET_MODULO_VERSION(?,?,?,?,?,?)",
                                parametros,false);     
    }

    /**
     * Obtiene indicador de recaudacion centralizada
     * @author ERIOS
     * @since 28.05.2014
     * @return
     * @throws SQLException
     */
    public static int getIndRecaudacionCentralizada() throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_GRAL.GET_IND_RECAUDAC_CENTRA"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_GRAL.GET_IND_RECAUDAC_CENTRA",
                                parametros);  
    }
    
    /**
     * Obtiene margen de impresion de comprobantes
     * @author ERIOS
     * @since 2.4.3
     * @return
     * @throws SQLException
     */
    public static int getMargenImpresionComp() throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_GRAL.GET_MARGEN_IMP_COMP"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_GRAL.GET_MARGEN_IMP_COMP",
                                parametros);  
    }    
}
