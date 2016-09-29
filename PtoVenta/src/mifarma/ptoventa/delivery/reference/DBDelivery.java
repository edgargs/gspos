package mifarma.ptoventa.delivery.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
public class DBDelivery {
    private static final Logger log = LoggerFactory.getLogger(DBDelivery.class);

    private static ArrayList parametros = new ArrayList();

    public DBDelivery() {
    }

    public static void obtieneNombreDniCliente(FarmaTableModel pTableModel, String pTelefono,
                                               String pTipoTelefono) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTelefono);
        parametros.add(pTipoTelefono);
        log.debug("PTOVENTA_DEL_CLI.CLI_OBTIENE_CLI_NOMB_DNI(?,?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_OBTIENE_CLI_NOMB_DNI(?,?,?,?)",
                                                 parametros, false);
    }

    public static String buscaTelefono(String pTelefono, String pTipoTelefono) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTelefono);
        parametros.add(pTipoTelefono);
        log.debug("PTOVENTA_DEL_CLI.cli_valida_num_tel(?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.cli_valida_num_tel(?,?,?,?)", parametros);
    }

    /**
     * Lista direcciones de telefono
     * @author ERIOS
     * @since 2.4.4
     * @param pTableModel
     * @param pTelefono
     * @param pTipoTelefono
     * @throws SQLException
     */
    public static void obtieneTelefonoDireccion(FarmaTableModel pTableModel, String pTelefono, String pTipoTelefono,
                                                String pCodCli) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTelefono);
        parametros.add(pTipoTelefono);
        parametros.add(pCodCli);
        log.debug("PTOVENTA_DEL_CLI.CLI_OBTIENE_CLI_TELF_DIR(?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_OBTIENE_CLI_TELF_DIR(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaClientes(FarmaTableModel pTableModel, String pBusqueda,
                                          String pTipoBusqueda) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pBusqueda);
        parametros.add(pTipoBusqueda);
        log.debug("PTOVENTA_DEL_CLI.CLI_BUSCA_DNI_APELLIDO_CLI(?,?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_BUSCA_DNI_APELLIDO_CLI(?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneClientesEnArreglo(ArrayList pArrayList, String pBusqueda,
                                                String pTipoBusqueda) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pBusqueda);
        parametros.add(pTipoBusqueda);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_DEL_CLI.CLI_BUSCA_DNI_APELLIDO_CLI(?,?,?,?)",
                                                          parametros);
    }

    public static void agregaDetalleClienteDireccion(String pCodDir, String pCodCli) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodDir);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCli);
        log.debug("PTOVENTA_DEL_CLI.CLI_AGREGA_DETALLE_DIRECCION(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_AGREGA_DETALLE_DIRECCION(?,?,?,?)",
                                                 parametros, false);
    }

    public static String agregaCliente(String pNomCli, String pApePatCli, String pApeMatCli, String pTipDoc,
                                       String pNumDoc, String pIndCli, String pTelefono,
                                       String pObs) throws SQLException {
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
        parametros.add(pTelefono);
        parametros.add(pObs);
        log.debug("PTOVENTA_DEL_CLI.CLI_AGREGA_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String agregaDireccion(String pTipCalle, String pNomCalle, String pNumCalle, String pNomUrb,
                                         String pNomDist, String pNumInt, String pRefer, String pCodCli,
                                         String pCodCliNuevo, String pCodDep, String pCodProv, String pCodDistr,
                                         String pUrban) throws SQLException {
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
        parametros.add(pCodCli);
        parametros.add(pCodCliNuevo);

        parametros.add(pCodDep);
        parametros.add(pCodProv);
        parametros.add(pCodDistr);
        parametros.add(pUrban);

        log.debug("PTOVENTA_DEL_CLI.CLI_AGREGA_DIRECCION_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_DIRECCION_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String agregaTelefono() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesDelivery.vCodigoDireccion);
        parametros.add(FarmaConstants.COD_NUMERA_SEC_TELEFONO);
        parametros.add(VariablesDelivery.vNumeroTelefono);
        parametros.add(VariablesDelivery.vCampo);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_DEL_CLI.CLI_AGREGA_TELEFONO_CLIENTE(?,?,?,?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_TELEFONO_CLIENTE(?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String actualizaCliente(String pCodCliente, String pNombreCliente, String pApellidoPat,
                                          String pApellidoMat, String pDni, String pObs) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCliente);
        parametros.add(pNombreCliente);
        parametros.add(pApellidoPat);
        parametros.add(pApellidoMat);
        parametros.add(pDni);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pObs);
        log.debug("PTOVENTA_DEL_CLI.cli_actualiza_cliente(?,?,?,?,?,?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.cli_actualiza_cliente(?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String actualizaDireccion(String pCodDir, String pTipCalle, String pNomCalle, String pNumCalle,
                                            String pNomUrb, String pNomDist, String pNumInt, String pRefer,

        String pCodDep, String pCodProv, String pCodDistr, String pCodUrb, String pUrban) throws SQLException {
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

        parametros.add(pCodDep);
        parametros.add(pCodProv);
        parametros.add(pCodDistr);
        parametros.add(pUrban);
        parametros.add(pCodUrb);
        log.debug("PTOVENTA_DEL_CLI.cli_actualiza_direccion(?,?,?,?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.cli_actualiza_direccion(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void cargaListaClientesConsulta(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_LISTA_CLIENTES_CONS(?,?)",
                                                 parametros, false);
    }

    public static void obtieneTarjetas_Cliente(FarmaTableModel pTableModel, String pCodigo) throws SQLException {

        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodigo);
        log.debug("PTOVENTA_DEL_CLI.CLI_OBTIENE_TARJETAS_CLIENTE(?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_OBTIENE_TARJETAS_CLIENTE(?,?,?)",
                                                 parametros, false);
    }

    public static String agragaTarjetaCliente(String pCodOperadorN, String pNumeroTarjeta, String pFechaVencimiento,
                                              String pPropietario, String pDni) throws SQLException {
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
        log.debug("PTOVENTA_DEL_CLI.CLI_AGREGA_TARJETAS_CLI(?,?,?,?,?,?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_AGREGA_TARJETAS_CLI(?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String actualizaTarjetaCliente(String pCodCliente, String pCodOperadorN, String pCodOperadorA,
                                                 String pFecVencimiento, String pNumeroTarjeta,
                                                 String pNombreTarjeta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCodCliente);
        parametros.add(pCodOperadorN);
        parametros.add(pCodOperadorA);
        parametros.add(pFecVencimiento);
        parametros.add(pNumeroTarjeta);
        parametros.add(pNombreTarjeta);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_DEL_CLI.CLI_ACTUALIZA_TARJETA(?,?,?,?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_ACTUALIZA_TARJETA(?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String activaInactivaTarjeta() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesDelivery.vCodCli);
        parametros.add(VariablesDelivery.vCodSecTarj);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_DEL_CLI.cli_actualiza_estado_tarjeta(?,?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.cli_actualiza_estado_tarjeta(?,?,?,?,?)",
                                                           parametros);
    }

    public static void cargaListaDireccionesConsulta(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesDelivery.vCodCli);

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_LISTA_DIRECC_CONS(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaCabUltimosPedidos(FarmaTableModel pTableModel, String tipoVenta, String tipoOperacionProforma) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(tipoVenta);
        parametros.add(tipoOperacionProforma);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_LISTA_CAB_PEDIDOS(?,?,?,?)",
                                                 parametros, false);
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
        log.debug("PTOVENTA_DEL_CLI.CLI_GENERA_PEDIDO_DA(?,?,?,?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_GENERA_PEDIDO_DA(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualuizaValoresDa() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesVentas.vNum_Ped_Vta);
        log.debug("PTOVENTA_DEL_CLI.CLI_ACTUALIZA_VALORES_PD(?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_ACTUALIZA_VALORES_PD(?,?,?)", parametros,
                                                 false);
    }

    public static String obtieneEstadoPedido_ForUpdate(String pNumeroPedido, String pCodLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumeroPedido);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_OBTIENE_ESTADO_PEDIDO(?,?,?)",
                                                           parametros);
    }

    public static void cargaFormaPagoPedidoDelAutomatico(ArrayList pArrayList, String pNumPedido) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_DEL_CLI.CLI_LISTA_FORMA_PAGO_PED(?,?,?)",
                                                          parametros);
    }

    public static void anulaPedidoDeliveryAutomatico(String pCodLocal, String pNumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedido);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_ANULA_DELIVERY_AUTOMATICO(?,?,?,?)",
                                                 parametros, false);
    }

    public static String obtieneLocalOrigen(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed);
        log.debug("PTOVENTA_DEL_CLI.CLI_OBTIENE_LOCAL_ORIGEN(?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_OBTIENE_LOCAL_ORIGEN(?,?,?)",
                                                           parametros);
    }

    public static void cargaListaDetUltimosPedidos(FarmaTableModel pTableModel, String pNumPedVta,
                                                   String pCodLocal) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(VariablesPtoVenta.vTipoLocalVenta);
        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_LISTA_DETALLE_PEDIDOS(?,?,?,?,?)",
                                                 parametros, false);
    }


    public static void cargaListaDetPedidos(FarmaTableModel pTableModel, String pNumPedVta, String pCodLocal,
                                            boolean filtrar) throws SQLException {

        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        // kmoncada 22.08.2014 filtro de prod pend.lote
        if (filtrar) {
            parametros.add("S");
        } else {
            parametros.add("N");
        }
        log.debug("PTOVENTA_DEL_CLI.CLI_LISTA_DETALLE_PEDIDOS_INST(?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_DEL_CLI.CLI_LISTA_DETALLE_PEDIDOS_INST(?,?,?,?,?)",
                                                 parametros, true);
    }
    
    /**
     * @since 04.08.2016 [PROYECTO M] SE REALIZA EL CAMBIO EN EL PROCEDIMIENTO PARA QUE DEVUELVA EL STOCK DEL LOTE 
     * EN CASO DE NO EXISTIR DEVUELVE -1
     * @param pCodProd
     * @param pNumLote
     * @return -1 EN CASO DE QUE NO EXISTE EL LOTE, OTRO VALOR: STOCK DEL LOTE.
     * @throws SQLException
     */
    public static String obtieneIndicadorExisteLote(String pCodProd, String pNumLote, String nroProforma) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(pNumLote);
        parametros.add(nroProforma);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_VERIFICA_LOTE_PROD(?,?,?,?,?)",
                                                           parametros);
    }

    public static void agregaDetalleProductoLote(String pNumPedido, String pCodProd, String pNumLote, String pCantidad,
                                                 String pFechaVencimiento, String pSecDetPedVta, String pValFracVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        parametros.add(pCodProd);
        parametros.add(pNumLote);
        parametros.add(new Integer(pCantidad));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pFechaVencimiento);
        parametros.add(pSecDetPedVta);
        parametros.add(pValFracVta);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_AGREGA_VTA_INSTI_DET(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        parametros.add(pCodProd);
        parametros.add(pSecDetPedVta);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_ELIMINA_VTA_INSTI_DET(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaDetalleProducto(String pNumPedido, String pCodProd, String pSecDetPedVta,
                                              String pNumLote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        parametros.add(pCodProd);
        parametros.add(pSecDetPedVta);
        parametros.add(pNumLote);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_ELIMINA_PED_INST_PROD_LOTE(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaDetProductoLote(FarmaTableModel pTableModel, String pNumPedido,
                                                 String pCodProd, String pSecDetPedVta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        parametros.add(pCodProd);
        parametros.add(pSecDetPedVta);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_LISTA_INST_DET_PROD_LOTE(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void generaPedidoInstitucionalAutomatico(boolean vIndProformaMenorStock, boolean isFlujoB) throws SQLException {
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
        parametros.add(vIndProformaMenorStock?"S":"N");
        parametros.add(isFlujoB?"S":"N");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_GENERA_PEDIDO_INST_A(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneProductosSeleccionTotalLote(ArrayList pArrayList) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesDelivery.vCodLocalOrigen);
        parametros.add(VariablesDelivery.vNumeroPedido);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_DEL_CLI.CLI_LISTA_PROD_LOTE_SEL(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_DEL_CLI.CLI_LISTA_PROD_LOTE_SEL(?,?,?,?)",
                                                          parametros);
    }

    public static String obtieneFechaVencimientoLote(String pCodProd, String pNumLote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(pNumLote);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_OBTIENE_FECHA_VENCIMIENTO(?,?,?,?)",
                                                           parametros);
    }

    public static String isContienLotesProductos(String pLocalDelivery,
                                                 String pNumPedidoDelivery) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pLocalDelivery.trim());
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoDelivery.trim());
        log.debug("PTOVENTA_DEL_CLI.CLI_F_VAR_EXIST_LOTE_PED(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_F_VAR_EXIST_LOTE_PED(?,?,?,?)",
                                                           parametros);
    }

    public static void cambiaDirPrincipalCli(String pCodDir, String pCodCli) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodDir);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCli);
        log.debug("PTOVENTA_DEL_CLI.CLI_CAMBIA_DIR_PRINCIPAL(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_CAMBIA_DIR_PRINCIPAL(?,?,?,?)",
                                                 parametros, false);
    }


    public static String getUbigeoLocal() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_DEL_CLI.C_GET_UBIGEO_LOCAL(?,?,?,?,?,?,?,?,?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.C_GET_UBIGEO_LOCAL(?,?)", parametros);
    }

    public static void cargarListaDetTransferenciaPedido(FarmaTableModel pTableModel, String pCodLocal,
                                                         String pNumPedVta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_DEL_CLI.CLI_LISTA_TRANSFERENCIA(?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author ERIOS
     * @since 2.4.5
     * @throws SQLException
     */
    public static void cargaLotesNumeroEntrega() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesDelivery.vCodLocalOrigen);
        parametros.add(VariablesDelivery.vNumeroPedido);
        parametros.add(FarmaVariables.vCodLocal);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CARGA_LOTES_ENTREGA(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * @author RHERRERA
     * @since 2.4.5
     * @throws SQLException
     */
    public static void recuperarPedidos() throws SQLException {
        parametros = new ArrayList();
        log.debug("PTOVENTA_DEL_CLI.RECUPERAR_PEDIDO");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.RECUPERAR_PEDIDO", parametros, false);
    }

    public static List getComprobanteProforma(String pCodGrupoCia, String pCodLocal,
                                              String pNumPedVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        log.info("PTOVENTA_DEL_CLI.VALIDA_ACTUALIZA_PROFORMA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_DEL_CLI.VALIDA_ACTUALIZA_PROFORMA(?,?,?)",
                                                               parametros);

    }

    public static void getFaltanteStkDLV(ArrayList pTableModel, String pNumPedVta,
                                         String pCodLocal) throws SQLException {
        //pTableModel = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal.trim());
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.info("PTOVENTA_DEL_CLI.VALIDA_STK_PEDIDO(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pTableModel, "PTOVENTA_DEL_CLI.VALIDA_STK_PEDIDO(?,?,?,?)",
                                                          parametros);
    }
    
    public static boolean validadConvenioLocal(String pCodConvenio){
        boolean flag = true;
        try{
            ArrayList parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pCodConvenio);
            log.info("PTOVENTA_DEL_CLI.F_CHAR_VALIDA_CONVENIO_LOCAL(?,?,?)" + parametros);
            String resultado = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.F_CHAR_VALIDA_CONVENIO_LOCAL(?,?,?)",parametros);
            if("S".equalsIgnoreCase(resultado))
                flag = true;
            else
                flag = false;
        }catch(Exception ex){
            log.error(" ",ex);
            flag = false;
        }
        return flag;
    }

}
