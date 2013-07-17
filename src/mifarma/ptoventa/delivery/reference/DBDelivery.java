package mifarma.ptoventa.delivery.reference;

import java.util.ArrayList;
import java.sql.SQLException;
import mifarma.common.FarmaVariables;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaDBUtility;
import mifarma.common.*;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ventas.reference.*;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : DBVentas.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* ERIOS      11.04.2006   Creación<br>
* <br>
* @author Edgar Rios Navarro<br>
* @version 1.0<br>
*
*/
public class DBDelivery
{
  private static ArrayList parametros = new ArrayList();

  public DBDelivery()
  {
  }

  public static void obtieneNombreDniCliente(FarmaTableModel pTableModel,
                                                String pTelefono,String pTipoTelefono)throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pTelefono);
      parametros.add(pTipoTelefono);
      System.out.println(parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_OBTIENE_CLI_NOMB_DNI(?,?,?,?)",parametros,false);
    }

    public static ArrayList obtieneTelefonoDireccion(String pTelefono, String pTipoTelefono)throws SQLException {
      ArrayList pOutParams = new ArrayList();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pTelefono);
      parametros.add(pTipoTelefono);
      System.out.println(parametros);
      FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_DEL_CLI.CLI_OBTIENE_CLI_TELF_DIR(?,?,?,?)",parametros);
      return pOutParams;
    }

     public static void cargaListaClientes(FarmaTableModel pTableModel,
                                                 String pBusqueda,
                                                 String pTipoBusqueda) throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pBusqueda);
      parametros.add(pTipoBusqueda);
      System.out.println(FarmaVariables.vCodGrupoCia + "," + FarmaVariables.vCodLocal + "," + pBusqueda + ","+pTipoBusqueda  );
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_BUSCA_DNI_APELLIDO_CLI(?,?,?,?)",parametros,false);
    }

    public static void obtieneClientesEnArreglo(ArrayList pArrayList,String pBusqueda,
                                                     String pTipoBusqueda) throws SQLException {
      pArrayList.clear();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pBusqueda);
      parametros.add(pTipoBusqueda);
      FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_DEL_CLI.CLI_BUSCA_DNI_APELLIDO_CLI(?,?,?,?)",parametros);
    }

    public static void agregaDetalleClienteDireccion(String pCodDir, String  pCodCli)throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodDir);
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(pCodCli);
      System.out.println(parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_DEL_CLI.CLI_AGREGA_DETALLE_DIRECCION(?,?,?,?)",parametros,false);
    }
    public static String agregaCliente(String pNomCli,String pApePatCli,String pApeMatCli,
                                       String pTipDoc,String pNumDoc,String pIndCli)throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(FarmaConstants.COD_NUMERA_CLIENTE_LOCAL);
      parametros.add(pNomCli);
      parametros.add(pApePatCli);
      parametros.add(pApeMatCli);
      parametros.add(pTipDoc);
      parametros.add(pIndCli);
      parametros.add(pNumDoc);
      parametros.add(FarmaVariables.vIdUsu);
      System.out.println(parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_CLIENTE(?,?,?,?,?,?,?,?,?,?)",parametros);
    }

    public static String agregaDireccion(String pTipCalle,String pNomCalle,String pNumCalle,
                                         String pNomUrb,String pNomDist,String pNumInt,String pRefer)throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(FarmaConstants.COD_NUMERA_SEC_DIRECCION);
      parametros.add(pTipCalle);
      parametros.add(pNomCalle);
      parametros.add(pNumCalle);
      parametros.add(pNomUrb);
      parametros.add(pNomDist);
      parametros.add(pNumInt);
      parametros.add(pRefer);
      parametros.add(FarmaVariables.vIdUsu);
      System.out.println(parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_DIRECCION_CLIENTE(?,?,?,?,?,?,?,?,?,?,?)",parametros);
    }

    public static String agregaTelefono()throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesDelivery.vCodigoDireccion);
      parametros.add(FarmaConstants.COD_NUMERA_SEC_TELEFONO);
      parametros.add(VariablesDelivery.vNumeroTelefono);
      parametros.add(VariablesDelivery.vCampo);
      parametros.add(FarmaVariables.vIdUsu);
      System.out.println(parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_TELEFONO_CLIENTE(?,?,?,?,?,?,?)",parametros);
    }

    public static String actualizaCliente(String pCodCliente,
                                          String pNombreCliente,
                                          String pApellidoPat,
                                          String pApellidoMat,
                                          String pDni) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodCliente);
      parametros.add(pNombreCliente);
      parametros.add(pApellidoPat);
      parametros.add(pApellidoMat);
      parametros.add(pDni);
      parametros.add(FarmaVariables.vIdUsu);
      System.out.println(parametros);
      System.out.println("realizo la actualizacion");
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.cli_actualiza_cliente(?,?,?,?,?,?,?,?)",parametros);
    }

    public static String actualizaDireccion(String pCodDir,
                                            String pTipCalle,
                                            String pNomCalle,
                                            String pNumCalle,
                                            String pNomUrb,
                                            String pNomDist,
                                            String pNumInt,
                                            String pRefer)throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodDir);
      parametros.add(pTipCalle);
      parametros.add(pNomCalle);
      parametros.add(pNumCalle);
      parametros.add(pNomUrb);
      parametros.add(pNomDist);
      parametros.add(pNumInt);
      parametros.add(pRefer);
      parametros.add(FarmaVariables.vIdUsu);
      System.out.println(parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.cli_actualiza_direccion(?,?,?,?,?,?,?,?,?,?,?)",parametros);
    }

      public static void cargaListaClientesConsulta(FarmaTableModel pTableModel) throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_LISTA_CLIENTES_CONS(?,?)",parametros,false);
    }

    public static void obtieneTarjetas_Cliente(FarmaTableModel pTableModel,
                                               String pCodigo) throws SQLException {

      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodigo);
      System.out.println(parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_OBTIENE_TARJETAS_CLIENTE(?,?,?)",parametros,false);
    }

    public static String agragaTarjetaCliente(String pCodOperadorN,
                                              String pNumeroTarjeta,
                                              String pFechaVencimiento,
                                              String pPropietario,
                                              String pDni
                                             ) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesDelivery.vCodCli);
      parametros.add(pCodOperadorN);
      parametros.add(pFechaVencimiento);
      parametros.add(pNumeroTarjeta);
      parametros.add(pPropietario);
      parametros.add(FarmaVariables.vIdUsu);
      parametros.add(pDni);
      System.out.println("En DB : " + parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_TARJETAS_CLI(?,?,?,?,?,?,?,?,?)",parametros);
    }

     public static String actualizaTarjetaCliente(String pCodCliente,
                                                  String pCodOperadorN,
                                                  String pCodOperadorA,
                                                  String pFecVencimiento,
                                                  String pNumeroTarjeta,
                                                  String pNombreTarjeta) throws SQLException {
      parametros = new ArrayList();
      parametros.add(pCodCliente);
      parametros.add(pCodOperadorN);
      parametros.add(pCodOperadorA);
      parametros.add(pFecVencimiento);
      parametros.add(pNumeroTarjeta);
      parametros.add(pNombreTarjeta);
      parametros.add(FarmaVariables.vIdUsu);
      System.out.println(parametros);
      System.out.println("realizo la actualizacion");
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_ACTUALIZA_TARJETA(?,?,?,?,?,?,?)",parametros);
    }

    public static String activaInactivaTarjeta()throws SQLException {

      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesDelivery.vCodCli);
      parametros.add(VariablesDelivery.vCodSecTarj);
      parametros.add(FarmaVariables.vIdUsu);
      System.out.println(parametros);
      System.out.println("realizo la actualizacion de la tarjeta");
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.cli_actualiza_estado_tarjeta(?,?,?,?,?)",parametros);
    }

   public static void cargaListaDireccionesConsulta(FarmaTableModel pTableModel) throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesDelivery.vCodCli);

      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_LISTA_DIRECC_CONS(?,?,?)",parametros,false);
    }

  public static void cargaListaCabUltimosPedidos(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_LISTA_CAB_PEDIDOS(?,?)",parametros,false);
  }

  public static void cargaListaDetUltimosPedidos(FarmaTableModel pTableModel,
                                                 String pNumPedVta,
                                                 String pCodLocal) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodLocal);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_LISTA_DETALLE_PEDIDOS(?,?,?,?)",parametros,false);
  }
  
  public static void generaPedidoDeliveryAutomatico() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesDelivery.vCodLocal);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesDelivery.vNumeroPedido);
    parametros.add(VariablesVentas.vNum_Ped_Vta);
    parametros.add(VariablesVentas.vNum_Ped_Diario);
    parametros.add(FarmaVariables.vIpPc);
    parametros.add(FarmaVariables.vNuSecUsu);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(VariablesCaja.vNumCaja);
    System.out.println("CLI_GENERA_PEDIDO_DA -->"+VariablesDelivery.vCodLocal);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_DEL_CLI.CLI_GENERA_PEDIDO_DA(?,?,?,?,?,?,?,?,?,?)",parametros,false);
  }
  
  public static void actualuizaValoresDa() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesVentas.vNum_Ped_Vta);
    System.out.println("parametros : " + parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_DEL_CLI.CLI_ACTUALIZA_VALORES_PD(?,?,?)",parametros,false);
  }
    
  public static String obtieneEstadoPedido_ForUpdate() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesDelivery.vCodLocal);
    parametros.add(VariablesDelivery.vNumeroPedido);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_OBTIENE_ESTADO_PEDIDO(?,?,?)",parametros);
  }
  
  public static void cargaFormaPagoPedidoDelAutomatico(ArrayList pArrayList,
                                                       String pNumPedido) throws SQLException {
    pArrayList.clear();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedido);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_DEL_CLI.CLI_LISTA_FORMA_PAGO_PED(?,?,?)",parametros);
  }
  
  public static void anulaPedidoDeliveryAutomatico(String pCodLocal, String pNumPedido) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodLocal);
    parametros.add(pNumPedido);
    parametros.add(FarmaVariables.vIdUsu);
    FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_ANULA_DELIVERY_AUTOMATICO(?,?,?,?)",parametros, false);
  }

  public static String obtieneLocalOrigen(String pNumPed)throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPed);
    System.out.println(parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_OBTIENE_LOCAL_ORIGEN(?,?,?)",parametros);
  }
  
  public static void cargaListaDetPedidos(FarmaTableModel pTableModel,
                                          String pNumPedVta,
                                          String pCodLocal) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodLocal);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_LISTA_DETALLE_PEDIDOS_INST(?,?,?,?)",parametros,true);
  }
  
  public static String obtieneIndicadorExisteLote(String pCodProd,
                                                  String pNumLote)throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
    parametros.add(pNumLote);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_VERIFICA_LOTE_PROD(?,?,?)",parametros);
  }
  
  public static void agregaDetalleProductoLote(String pNumPedido,
                                               String pCodProd,
                                               String pNumLote,
                                               String pCantidad,
                                               String pFechaVencimiento)throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedido);
    parametros.add(pCodProd);
    parametros.add(pNumLote);
    parametros.add(new Integer(pCantidad));
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(pFechaVencimiento);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_DEL_CLI.CLI_AGREGA_VTA_INSTI_DET(?,?,?,?,?,?,?,?)",parametros, false);
  }
  
  public static void eliminaDetalleProducto(String pNumPedido,
                                            String pCodProd)throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedido);
    parametros.add(pCodProd);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_DEL_CLI.CLI_ELIMINA_VTA_INSTI_DET(?,?,?,?)",parametros, false);
  }
  
  public static void eliminaDetalleProducto(String pNumPedido,
                                            String pCodProd,
                                            String pNumLote)throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedido);
    parametros.add(pCodProd);
    parametros.add(pNumLote);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_DEL_CLI.CLI_ELIMINA_PED_INST_PROD_LOTE(?,?,?,?,?)",parametros, false);
  }
  
  public static void cargaListaDetProductoLote(FarmaTableModel pTableModel,
                                               String pNumPedido,
                                               String pCodProd) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedido);
    parametros.add(pCodProd);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_DEL_CLI.CLI_LISTA_INST_DET_PROD_LOTE(?,?,?,?)",parametros,false);
  }
  
  public static void generaPedidoInstitucionalAutomatico() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesDelivery.vCodLocal);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesDelivery.vNumeroPedido);
    parametros.add(VariablesVentas.vNum_Ped_Vta);
    parametros.add(VariablesVentas.vNum_Ped_Diario);
    parametros.add(FarmaVariables.vIpPc);
    parametros.add(FarmaVariables.vNuSecUsu);
    parametros.add(FarmaVariables.vIdUsu);
     System.out.println("CLI_GENERA_PEDIDO_INST_A -->"+VariablesDelivery.vCodLocal);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_DEL_CLI.CLI_GENERA_PEDIDO_INST_A(?,?,?,?,?,?,?,?,?)",parametros,false);
  }
  
  public static void obtieneProductosSeleccionTotalLote(ArrayList pArrayList) throws SQLException {
    pArrayList.clear();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesDelivery.vCodLocalOrigen);
    parametros.add(VariablesDelivery.vNumeroPedido);
    parametros.add(FarmaVariables.vCodLocal);
    System.out.println(parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_DEL_CLI.CLI_LISTA_PROD_LOTE_SEL(?,?,?,?)",parametros);
  }
  
  public static String obtieneFechaVencimientoLote(String pCodProd,
                                                   String pNumLote)throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
    parametros.add(pNumLote);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_OBTIENE_FECHA_VENCIMIENTO(?,?,?)",parametros);
  }

  public static String isContienLotesProductos(String pLocalDelivery,String pNumPedidoDelivery)throws SQLException
  {

    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pLocalDelivery.trim());
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedidoDelivery.trim());
    System.out.println("PTOVENTA_DEL_CLI.CLI_F_VAR_EXIST_LOTE_PED(?,?,?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_F_VAR_EXIST_LOTE_PED(?,?,?,?)",parametros);
  }
  
}