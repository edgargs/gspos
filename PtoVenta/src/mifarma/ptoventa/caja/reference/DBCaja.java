package mifarma.ptoventa.caja.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaIPSImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCHAVEZ 30.06.2009 Modificación<br>
 * ASOSA   17.02.2010 Modificación<br>
 * LTAVARA  03.09.2010 Modificación<br>
 * <br>
 * @version 1.0<br>
 *
 */
public class DBCaja {

    private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(DBCaja.class);


    public DBCaja() {
    }

    public static String obtenerCajaUsuario() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("PTOVENTA_CAJ.CAJ_OBTIENE_CAJAS_DISP_USUARIO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTIENE_CAJAS_DISP_USUARIO(?,?,?)",
                                                           parametros);
    }

    public static void registraMovimientoCajaAper(String pNumCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pNumCaja.trim()));
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vIpPc);
        for (int i = 0; i < parametros.size(); i++) {
            log.debug(i + ": " + parametros.get(i).toString());
        }
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_REGISTRA_MOVIMIENTO_APER(?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static String obtenerTurnoActualCaja(String pNumCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pNumCaja.trim()));
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTENER_TURNO_ACTUAL_CAJA(?,?,?)",
                                                           parametros);
    }

    public static String obtenerFechaApertura(String pNumCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pNumCaja.trim()));
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTENER_FECHA_APERTURA(?,?,?)",
                                                           parametros);
    }

    public static void validaUsuarioOpCaja(String pOp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pOp);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_VALIDA_OPERADOR_CAJA(?,?,?,?)", parametros,
                                                 false);
    }

    public static void verificaAperturaCaja() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CE_INVALIDA_APER_CAJ(?,?)", parametros, false);
    }

    public static void getListaImpresorasUsuario(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_IMPRESORAS_CAJAS_USU(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaImpresorasLocal(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_IMPRESORAS_CAJAS_LOC(?,?)",
                                                 parametros, false);
    }

    public static void configuraCaja(String pSecImprLocal, String pnumSerieLocal,
                                     String pNumComprob) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pSecImprLocal.trim()));
        parametros.add(pnumSerieLocal.trim());
        parametros.add(pNumComprob.trim());
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_RECONFIG_IMPRESORA(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void getListaPedidosPendientes(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("PTOVENTA_CAJ.CAJ_LISTA_CAB_PEDIDOS_PENDIENT(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_CAB_PEDIDOS_PENDIENT(?,?,?)",
                                                 parametros, false);
    }

    public static void getListaDetallePedido(FarmaTableModel pTableModel, String pNumPedVta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pNumPedVta);

        for (int i = 0; i < parametros.size(); i++) {
            log.debug(i + " : " + parametros.get(i).toString());

        }

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_DET_PEDIDOS_PENDIENT(?,?,?)",
                                                 parametros, false);
    }

    public static void anularPedidoPendiente(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("Anula Pedido Pendiente:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_ANULAR_PEDIDO_PENDIENTE(?,?,?,?)",
                                                 parametros, false);
    }

    //

    public static void anularPedidoPendienteSinRespaldo(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("Anula Pedido Pendiente sin Respaldo:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_P_ANULA_PED_SIN_RESPALDO(?,?,?,?)",
                                                 parametros, false);
    }


    public static void cambiarEstadoPed(String pNumPed, String pEst) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pEst.trim());

        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_CAMBIA_ESTADO_PEDIDO(?,?,?,?)", parametros,
                                                 false);

    }

    public static void getListaPedidosComp(FarmaTableModel pTableModel) throws SQLException {
        log.debug("Entra en DBCaja.getListaPedidosComp=");
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vSecMovCajaOrigen);
        //JMIRANDA 12.01.2011 PARA OCULTAR MONTO COMPROBANTE EN FORMA DE PAGO
        parametros.add(VariablesCaja.vMostrarMontoComprobante);
        log.debug("PTOVENTA_CAJ.CAJ_LISTA_RELACION_PEDIDO_COMP(?,?,?,?)");
        for (int i = 0; i < parametros.size(); i++) {
            log.debug(i + " : " + parametros.get(i).toString());
        }
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_RELACION_PEDIDO_COMP(?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaPedidosCompRangos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vFecIniVerComp);
        parametros.add(VariablesCaja.vFecFinVerComp);

        for (int i = 0; i < parametros.size(); i++) {
            log.debug(i + " : " + parametros.get(i).toString());

        }

        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_COMPROBANT_RANGO_FEC(?,?,?,?)",
                                                 parametros, false);
    }

    public static void corregirComprobantes(String pSecIni, String pSecFin, String pCant,
                                            String pInd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecIni.trim());
        parametros.add(pSecFin.trim());
        parametros.add(VariablesCaja.vTipComp);
        parametros.add(pCant.trim());
        parametros.add(pInd.trim());
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_CORRIGE_COMPROBANTES(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static int verificaCorrecion(String pSecInicial, String pSecFinal, String pTipo, String pCantidad,
                                        String pDireccion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecInicial);
        parametros.add(pSecFinal);
        parametros.add(pTipo);
        parametros.add(new Integer(pCantidad.trim()));
        parametros.add(pDireccion);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICAR_CORRECCION_COMP(?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void obtieneInfoCobrarPedido(ArrayList pArrayList, String pNumPedDiario,
                                               String pFecPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedDiario);
        parametros.add(pFecPedVta);
        // KMONCADA 01.09.2014 MODIFICACION PARA MOSTRAR LOS DOC A IMPRIMIR EN CASO DE CONVENIOS
        parametros.add(VariablesConvenioBTLMF.vValorSelCopago);
        log.debug("invoca PTOVENTA_CAJ.CAJ_OBTIENE_INFO_PEDIDO(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_OBTIENE_INFO_PEDIDO(?,?,?,?,?)",
                                                          parametros);
    }


    public static int verificaComprobantes(String pSecInicial, String pSecFinal, String pTipo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecInicial);
        parametros.add(pSecFinal);
        parametros.add(pTipo);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICAR_COMPROBANTES(?,?,?,?,?)",
                                                           parametros);
    }

    public static String obtenerMovApertura(String pNumCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pNumCaja.trim()));
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTENER_SEC_MOV_APERTURA(?,?,?)",
                                                           parametros);
    }

    /**
     * Lista las Formas de Pago
     * @author : dubilluz
     * @since  : 06.09.2007
     */

    /*
  public static void obtieneFormasPago(FarmaTableModel pTableModel,
                                       String  indConvenio,
                                       String  codConvenio,
                                       String  codCliente,
                                       String  numPed) throws SQLException{
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);

    //log.debug("VariablesVentas.vEsPedidoConvenio >>" + VariablesVentas.vEsPedidoConvenio);
    //log.debug("VariablesCaja.vIndPedidoConvenio  >>"  + VariablesCaja.vIndPedidoConvenio);
    //log.debug("VariablesConvenio.vCodConvenio    >>"    + VariablesConvenio.vCodConvenio);
    parametros.add(indConvenio);
    parametros.add(codConvenio);
    parametros.add(codCliente);
    parametros.add(numPed);
    log.debug("Parameter for listado Formas de Pago " + parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CAJ.CAJ_OBTIENE_FORMAS_PAGO(?,?,?,?,?,?)",parametros, false);


  }
 */


    public static String verificaRelacionCajaImpresoras() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim())); //numero de caja
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_VERIFICA_CAJA_IMPRESORAS(?,?,?)",
                                                           parametros);
    }

    public static int verificaCajaAbierta() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICA_CAJA_ABIERTA(?,?,?)",
                                                           parametros);
    }

    public static int obtieneNumeroCajaUsuario() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("invoca PTOVENTA_CAJ.CAJ_OBTIENE_CAJA_USUARIO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_OBTIENE_CAJA_USUARIO(?,?,?)", parametros);
    }

    public static void obtieneSecuenciaImpresorasVenta(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("num caja" + VariablesCaja.vNumCaja.trim());
        //VariablesCaja.vNumCaja="1";
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        log.debug("invoca PTOVENTA_CAJ.CAJ_SECUENCIA_IMPRESORAS_VENTA(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_SECUENCIA_IMPRESORAS_VENTA(?,?,?)",
                                                          parametros);
    }

    public static String obtieneRutaImpresoraVenta(String pSecImprLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pSecImprLocal.trim()));
        log.debug("invoca PTOVENTA_CAJ.CAJ_OBTIENE_RUTA_IMPRESORA(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTIENE_RUTA_IMPRESORA(?,?,?)",
                                                           parametros);
    }

    public static String obtieneEstadoPedido(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVENTA_CAJ.CAJ_OBTIENE_ESTADO_PEDIDO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTIENE_ESTADO_PEDIDO(?,?,?)",
                                                           parametros);
    }

    public static String obtieneIndCajaAbierta_ForUpdate(String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pSecMovCaja);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_IND_CAJA_ABIERTA_FORUPDATE(?,?,?,?)",
                                                           parametros);
    }

    public static String obtieneSecuenciaMovCaja() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTIENE_SEC_MOV_CAJA(?,?,?)", parametros);
    }

    public static String obtieneFechaMovCaja() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTIENE_FECHA_MOV_CAJA(?,?,?)",
                                                           parametros);
    }

    public static int agrupaImpresionDetallePedido(int pCantMaxImpresion) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(new Integer(pCantMaxImpresion));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesVentas.vTip_Comp_Ped);  //ASOSA - 24/07/2015 - IGVAZNIA
        
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_AGRUPA_IMPRESION_DETALLE(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String cobraPedido(String pTipComp, String vPermiteCampaña, String Dni) throws SQLException {
        log.debug("VariablesVentas.vTipoPedido : " + VariablesVentas.vTipoPedido);
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(VariablesCaja.vSecMovCaja);
        parametros.add(FarmaConstants.COD_NUMERA_SEC_COMP_PAGO);
        parametros.add(pTipComp);
        if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON))
            parametros.add(ConstantsPtoVenta.MOT_KARDEX_VENTA_NORMAL);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_DELIVERY))
            parametros.add(ConstantsPtoVenta.MOT_KARDEX_VENTA_DELIVERY);
        else if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL))
            parametros.add(ConstantsPtoVenta.MOT_KARDEX_VENTA_ESPECIAL);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_VENTA);
        parametros.add(FarmaConstants.COD_NUMERA_SEC_KARDEX);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesCaja.vDescripcionDetalleFormasPago);
        parametros.add(vPermiteCampaña); //jcortez
        parametros.add(Dni); //jcortez 18.08.09
        log.debug("PTOVENTA_CAJ.CAJ_COBRA_PEDIDO(?,?,?,?,?,?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_COBRA_PEDIDO(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
        //FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CAJ.CAJ_COBRA_PEDIDO(?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
    }

    public static void grabaFormaPagoPedido(String pCodFormaPago, String pImPago, String pTipMoneda,
                                            String pTipoCambio, String pVuelto, String pImTotalPago, String pNumTarj,
                                            String pFecVencTarj, String pNomCliTarj,
                                            String pCantCupon) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodFormaPago);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImPago)));
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pTipoCambio)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pVuelto)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImTotalPago)));
        parametros.add(pNumTarj);
        parametros.add(pFecVencTarj);
        parametros.add(pNomCliTarj);
        parametros.add(new Integer(pCantCupon));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("CAJ_GRABAR_FORMA_PAGO_PEDIDO: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CAJ.CAJ_GRABAR_FORMA_PAGO_PEDIDO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneInfoDetalleAgrupacion(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_INFO_DETALLE_AGRUPACION(?,?,?)",
                                                          parametros);
    }

    public static void obtieneNumComp_ForUpdate(ArrayList pArrayList, String pSecImprLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecImprLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_OBTIENE_NUM_COMP_PAGO_IMPR(?,?,?)",
                                                          parametros);
    }

    public static void obtieneInfoDetalleImpresion(ArrayList pArrayList, String pSecGrupoImpr) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pSecGrupoImpr);
        
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_INFO_DETALLE_IMPRESION (?,?,?,?)",
                                                          parametros);
    }

    public static void obtieneInfoTotalesComprobante(ArrayList pArrayList, String pSecCompPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pSecCompPago);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_INFO_TOTALES_COMPROBANTE(?,?,?,?)",
                                                          parametros);
    }

    public static void actualizaComprobanteImpreso(String pSecCompPago, String pTipCompPago,
                                                   String pNumCompPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pSecCompPago);
        parametros.add(pTipCompPago);
        parametros.add(pNumCompPago);
        parametros.add(FarmaVariables.vIdUsu);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_ACTUALIZA_COMPROBANTE_IMPR(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaNumComp_Impresora(String pSecImprLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecImprLocal);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_ACTUALIZA_IMPR_NUM_COMP(?,?,?,?)", parametros,
                                                 false);
    }

    public static void verificaPedido(String correlativo, String monto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(correlativo);
        parametros.add(new Double(monto));
        parametros.add(VariablesCaja.vIndAnulacionConReclamoNavsat ? FarmaConstants.INDICADOR_S :
                       FarmaConstants.INDICADOR_N);
        log.debug("jcallo : PTOVENTA_CAJ_ANUL.CAJ_VERIFICA_PEDIDO parametros : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_VERIFICA_PEDIDO(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void getListaCabeceraPedido(FarmaTableModel pTableModel, String correlativo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(correlativo);
        //CHUANES 21.04.2014
        //SE AÑADE numCompPago PARA OBTENER EL TIPO DE COMPROBANTE CORRECTO
        parametros.add(VariablesVentas.numCompPago);
        parametros.add(VariablesVentas.vCodTipProcPago == null ? "0" :
                       VariablesVentas.vCodTipProcPago); //LTAVARA 03.09.2014 VALIDA SI EL DOCUMENTO GENERADO FUE CON EL PROCESO ELECTRONICO
        log.debug("PTOVENTA_CAJ_ANUL.CAJ_LISTA_CABECERA_PEDIDO(?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ_ANUL.CAJ_LISTA_CABECERA_PEDIDO(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void getListaDetallePedido(FarmaTableModel pTableModel, String correlativo, String tipoComp,
                                             String numComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(correlativo);
        parametros.add(tipoComp);
        parametros.add(numComp);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ_ANUL.CAJ_LISTA_DETALLE_PEDIDO(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String verificaComprobante(String tipoComp, String numComp, String monto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(tipoComp);
        parametros.add(numComp);
        parametros.add(new Double(monto));
        parametros.add(VariablesCaja.vIndAnulacionConReclamoNavsat ? FarmaConstants.INDICADOR_S :
                       FarmaConstants.INDICADOR_N);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJ_VERIFICA_COMPROBANTE(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void getListaCajaUsuario(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ_ANUL.CAJ_LISTA_CAJA_USUARIO(?,?)",
                                                 parametros, false);
    }


    public static void anularPedido(String numPedVta, String tipComp, String numComp, String monto, String numCajaPago,
                                    String motivoAnulacion, String vValidarMin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedVta);
        parametros.add(tipComp);
        parametros.add(numComp);
        parametros.add(new Double(monto));
        parametros.add(numCajaPago);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesCaja.vIndAnulacionConReclamoNavsat ? FarmaConstants.INDICADOR_S :
                       FarmaConstants.INDICADOR_N);
        parametros.add(motivoAnulacion);
        parametros.add(vValidarMin); //add jcallo
        log.debug("invocando PTOVENTA_CAJ_ANUL.CAJ_ANULAR_PEDIDO(?,?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_ANULAR_PEDIDO(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void buscarPedidoUnir(ArrayList array, String numeroPedido, String fechaPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numeroPedido);
        parametros.add(fechaPedido);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_CAJ_ANUL.CAJ_BUSCAR_PEDIDO_UNIR(?,?,?,?)",
                                                          parametros);
    }

    public static void getDetallePedidoUnir(FarmaTableModel pTableModel, String numPedPendiente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedPendiente);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ_ANUL.CAJ_GET_DETALLE_PEDIDO_UNIR(?,?,?)",
                                                 parametros, false);
    }

    public static void getNumeroPedidoUnir(ArrayList array, String totalSoles, String tipoCambio, String tipComp,
                                           String tipPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Double(totalSoles));
        parametros.add(new Double(tipoCambio));
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        parametros.add(tipComp);
        parametros.add(tipPed);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                                                          "PTOVENTA_CAJ_ANUL.CAJ_GET_NUMERO_PEDIDO_UNIR(?,?,?,?,?,?,?,?)",
                                                          parametros);
    }

    public static void unirComprobante(String numPedNuevo, String numPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedNuevo);
        parametros.add(numPed);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_GET_UNIR_COMPROBANTE_UNIR(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaEstadoPedido(String pNumPedVta, String pEstPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pEstPedVta);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_CAJ.CAJ_ACTUALIZA_ESTADO_PEDIDO(?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_ACTUALIZA_ESTADO_PEDIDO(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneListaPedidosNoImpresos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_CAB_PEDIDOS_ESTADO_S(?,?)",
                                                 parametros, false);
    }

    public static void obtieneDetallePedidoNoImpreso(FarmaTableModel pTableModel,
                                                     String pNumPedVta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_DET_PEDIDO_ESTADO_S(?,?,?)",
                                                 parametros, false);
    }

    public static void obtenerInfoCajero(ArrayList pArrayList, String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CAJ.CAJ_OBTIENE_INFO_CAJERO(?,?,?)",
                                                          parametros);
    }

    public static void getListaDetalleNotaCredito(FarmaTableModel pTableModel, String correlativo, String tipoComp,
                                                  String numComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(correlativo);
        parametros.add(tipoComp);
        parametros.add(numComp);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CAJ_ANUL.CAJ_LISTA_DETALLE_NOTA_CREDITO(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String agregarCabeceraNotaCredito(String numPedVta, String tipCambio,
                                                    String motivoAnulacion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedVta);
        parametros.add(new Double(tipCambio));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        parametros.add(motivoAnulacion);
        log.debug("agregarCabeceraNotaCredito MotivoAnulacion : PTOVENTA_CAJ_ANUL.CAJ_AGREGAR_CAB_NOTA_CREDITO " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJ_AGREGAR_CAB_NOTA_CREDITO(?,?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Agrega el detalle del producto a devolver como Nota de Credito.
     * @param numPedVta
     * @param numera
     * @param codProd
     * @param cant
     * @param total
     * @param pSecDetPed
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 29.05.2008
     */
    public static String agregarDetalleNotaCredito(String numPedVta, String numera, String codProd, String cant,
                                                   String total, String pSecDetPed) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedVta);
        parametros.add(numera);
        parametros.add(codProd);
        parametros.add(new Integer(cant.trim()));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(total)));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(new Integer(pSecDetPed.trim()));
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        parametros.add(UtilityEposTrx.validacionEmiteElectronico() == true ? "1" : "0"); //LTAVARA 04.09.2014 FLAG PROCESO ELECTRONICO
        parametros.add(VariablesCaja.vTipoMotivoAnulacion); //LTAVARA 15.10.2014 ENVIAR TIPO MOTIVO ANULACION NC
        
        // KMONCADA 22.10.2014 DEVUELVE EL NUMERO DE LA NC GENERADA.        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJ_AGREGAR_DET_NC(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void actualizaClientePedido(String pNumPedVta, String pCodCliLocal, String pNomCliPed,
                                              String pDirCliPed, String pRucCliPed) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pCodCliLocal);
        parametros.add(pNomCliPed);
        parametros.add(pDirCliPed);
        parametros.add(pRucCliPed);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_ACTUALIZA_CLI_PEDIDO(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    //

    public static void cobraNotaCredito(String pTipComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        log.debug("Numero Ped Vta: " + VariablesCaja.vNumPedVta);
        parametros.add(VariablesCaja.vNumPedVta_Anul);
        log.debug("Numero Ped Vta Anul: " + VariablesCaja.vNumPedVta_Anul);
        parametros.add(VariablesCaja.vSecMovCaja);
        log.debug("Secuencial Mov Caja: " + VariablesCaja.vSecMovCaja);
        parametros.add(FarmaConstants.COD_NUMERA_SEC_COMP_PAGO);
        parametros.add(pTipComp);
        parametros.add(ConstantsPtoVenta.MOT_KARDEX_DEVOLUCION_VENTA);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_VENTA);
        parametros.add(FarmaConstants.COD_NUMERA_SEC_KARDEX);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        parametros.add(VariablesCaja.vTipComp);
        log.debug("vTipComp: " + VariablesCaja.vTipComp);
        parametros.add(VariablesCaja.vNumComp_Anul);
        log.debug("vNumComp_Anul: " + VariablesCaja.vNumComp_Anul);
        log.debug("parametros---> " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CAJ_ANUL.CAJ_COBRA_NOTA_CREDITO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static ArrayList getListaPedidosCompDatosCab() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vSecMovCajaOrigen);

        for (int i = 0; i < parametros.size(); i++) {
            log.debug(i + " : " + parametros.get(i).toString());
        }
        ArrayList array = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_CAJ.CAJ_LISTA_PEDIDOS_COMPROB_CAB(?,?,?)",
                                                          parametros);
        return array;
    }

    public static int cantidadPedidosPendAnulMasivo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vMinutosPedidosPendientes);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICA_PED_PEND_ANUL(?,?,?)",
                                                           parametros);
    }

    public static void anulaPedidosPendientesMasivo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vMinutosPedidosPendientes);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("parametros anulaPedidosPendientesMasivo: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_ANULA_PED_PEND_MASIVO(?,?,?,?)", parametros,
                                                 false);
    }

    public static String verificaComprobantePago(String secImpresora, int cantCantComprobantes, String pNumPedVta) throws SQLException {

        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(secImpresora);
        parametros.add(new Integer(cantCantComprobantes));
        parametros.add(pNumPedVta);
        log.debug("Invocando a PTOVENTA_CAJ.CAJ_VERIFICA_NUM_COMP(?,?,?,?,?) : " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_VERIFICA_NUM_COMP(?,?,?,?,?)", parametros);

    }

    public static int getProductosRestantes(String numeroPedido, String numeroComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numeroPedido);
        parametros.add(numeroComp);
        //parametros.add(ConstantsVentas.TIPO_COMP_FACTURA);
        parametros.add(VariablesCaja.vTipComp);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ_ANUL.CAJ_GET_PRODS_REST(?,?,?,?,?)",
                                                           parametros);
    }

    public static void obtenerInfoVendedor(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CAJ.CAJ_OBTIENE_INFO_VENDEDOR(?,?,?)",
                                                          parametros);

    }
    //27/09/2007  DUBILLUZ MODIFICADO

    public static void intercambiarComprobante(String numDocA, String monDocA, String numDocB, String monDocB,
                                               String tipComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numDocA.trim());
        parametros.add(new Double(monDocA.trim()));
        parametros.add(numDocB.trim());
        parametros.add(new Double(monDocB.trim()));
        parametros.add(tipComp.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.debug("Para cambio : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CAJ_ANUL.CAJ_INTERCAMBIO_COMPROBANTE(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaMontosCabeceraNC(String numera, double totalBruto, double totalNeto, double totalIGV,
                                                 double totalDscto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numera);
        parametros.add(new Double(totalBruto));
        parametros.add(new Double(totalNeto));
        parametros.add(new Double(totalIGV));
        parametros.add(new Double(totalDscto));
        parametros.add(FarmaVariables.vIdUsu);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_ACT_CABECERA_NC(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerValorMaximoConfigCaja(String pTipoDocumento) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipoDocumento);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_VALOR_MAX_CONFIG_COMP(?,?,?)",
                                                           parametros);
    }

    public static double obtieneMontoFormaPagoCupon(String pCodFormaPago, String pCantCupon) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pCodFormaPago);
        parametros.add(new Integer(pCantCupon));
        return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_CAJ.CAJ_VALIDA_FORMA_PAGO_CUPON(?,?,?,?,?)",
                                                              parametros);
    }

    public static void obtieneSecuenciaImprVtaInstitucional(ArrayList pArrayList,
                                                            String pTipoComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim()));
        parametros.add(pTipoComp);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CAJ.CAJ_SECUENCIA_IMPR_INSTI(?,?,?,?)",
                                                          parametros);
    }

    /************************/

    public static void reinicializaPedidoAutomatico(String pCodLocal, String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CLI_REINICIALIZA_PEDIDO_AUTO(?,?,?)",
                                                 parametros, false);
    }

    public static int obtieneCantProdVirtualesPedido(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICA_PROD_VIRTUALES(?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene informacion del pedido.
     * @param pArrayList
     * @param pNumPedVta
     * @throws SQLException
     */
    public static void obtieneInfoPedidoVirtual(ArrayList pArrayList, String pNumPedVta) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        // Modificado para Bprepaid
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CAJ.CAJ_OBT_INFO_PROD_VIRTUAL(?,?,?)",
                                                          parametros);
    }

    /**
     * Actualiza valores del pedido.
     * @throws SQLException
     */
    public static void actualizaInfoPedidoVirtual() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(VariablesCaja.vCodProd);
        parametros.add(VariablesVirtual.vNumTrace);
        parametros.add(VariablesVirtual.vCodigoAprobacion);
        parametros.add(VariablesVirtual.vNumeroTarjeta);
        parametros.add(VariablesVirtual.vNumeroPin);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesVirtual.vFechaTX);
        parametros.add(VariablesVirtual.vHoraTX);
        parametros.add(VariablesVirtual.vDatosImprimir);
        // Modificado para Bprepaid
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CAJ.CAJ_ACT_INFO_DET_PED_VIRTUAL(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     *
     * @return
     * @throws SQLException
     * @deprecated
     */
    public static ArrayList obtieneDatosNavsatRecarga() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_CAJ.CAJ_OBTIENE_VALORES_RECARGA",
                                                          parametros);
        return pOutParams;
    }

    /**
     *
     * @return
     * @throws SQLException
     * @deprecated
     */
    public static ArrayList obtieneDatosNavsatTarjeta() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_CAJ.CAJ_OBTIENE_VALORES_TARJETA",
                                                          parametros);
        return pOutParams;
    }

    public static void actualizaInfoPedidoAnuladoVirtual(String pNumPedOrigen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedOrigen);
        parametros.add(VariablesVirtual.vNumTrace);
        parametros.add(VariablesVirtual.vCodigoAprobacion);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_ACT_INFO_DET_PED_VIR_ANUL(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneListaPedidosVirtualesCab(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vSecMovCajaOrigen);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_CAB_PED_VIRTUALES(?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneListaPedidosVirtualesDet(FarmaTableModel pTableModel,
                                                       String pNumPedVta) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_LISTA_DET_PED_VIRTUALES(?,?,?)",
                                                 parametros, false);
    }

    public static String obtieneTipoProductoVirtualPedido(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTIENE_TIPO_PROD_VIR_PED(?,?,?)",
                                                           parametros);
    }

    public static void cargaFormaPagoPedidoConvenio(ArrayList pArrayList, String pNumPedido) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CAJ.CAJ_LISTA_FORMA_PAGO_CONV(?,?,?)",
                                                          parametros);
    }

    /**
     * LLena el Array con las Forma de Pago del Pedido Delivery
     * @author : dubilluz
     * @since  : 26.07.2007
     */
    public static void colocaFormaPagoDeliveryArray(ArrayList pArrayList, String pNumPedido) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_DEL_CLI.CLI_LISTA_FORMA_PAGO_PED(?,?,?)",
                                                          parametros);
    }

    /**
     *Obtien el Codigo de la Forma de Pago del Convenio
     * @author : dubilluz
     * @since  : 26.07.2007
     */
    public static String cargaFormaPagoConvenio(String vConvenio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(vConvenio.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.OBTIENE_COD_FORMA_PAGO(?,?)",
                                                           parametros).trim();
    }

    /**
     * Retorna el Codigo de Forma de PAgo  , del COnvenio si tiene Credito
     * @author  dubilluz
     * @since   08.09.2007
     */
    public static String verifica_Credito_Convenio(String cod_Convenio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cod_Convenio.trim());
        log.debug(cod_Convenio);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_VERIFICA_CREDITO_CONVENIO(?,?,?)",
                                                           parametros);
    }

    /**
     *obtiene el saldo actual del cliente en matriz
     * @author  jcallo
     * @since   08.01.2009
     */
    public static String getSaldoCredClienteMatriz(String vCodCliente, String vCodConvenio,
                                                   String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(vCodConvenio.trim());
        parametros.add(vCodCliente.trim());
        log.debug("validando credito de cliente .. invocando  PTOVENTA_MATRIZ_CONV.CON_F_CHAR_SALD_CLIENTE(?,?):" +
                  parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CONV.CON_F_CHAR_SALDO_CLIENTE(?,?)",
                                                                 parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                 pIndCloseConecction);
    }

    /**
     * Obtiene los datos de conexion con el servidor Bprepaid.
     * @return arreglo de datos
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 27.09.2007
     */
    public static ArrayList obtieneDatostRecargaBprepaid() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_CAJ.CAJ_OBTIENE_VALORES_BPREPAID(?,?)",
                                                          parametros);
        return pOutParams;
    }

    /**
     * Retorna el Cod FP Dolares
     * @author  dubilluz
     * @since   13.10.2007
     */
    public static String getCodFPDolares() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_OBTIENE_FP_DOLARES(?,?)",
                                                           parametros).trim();
    }

    /**
     * Obtiene Informacion de los datos para la boleta de Recarga Virtual
     * @author dubilluz
     * @since  02.11.2007
     */
    public static void obtieneInfImpresionRecarga(ArrayList pArrayList, String pNumped,
                                                  String pCodProd) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(pNumped.trim());
        parametros.add(pCodProd.trim());
        log.debug("Parametros :" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "FARMA_GRAL.CAJ_OBTIENE_MSJ_PROD_VIRT(?,?,?,?)",
                                                          parametros);
    }
    
    /**
     * obtieneInfImpresionRimac
     * @author ASOSA
     * @since 12/01/2015
     * @type rimac
     * @param pArrayList
     * @param pNumped
     * @param pCodProd
     * @throws SQLException
     */
    public static void obtieneInfImpresionRimac(ArrayList pArrayList, String pNumped,
                                                  String pCodProd) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(pNumped.trim());
        parametros.add(pCodProd.trim());
        log.debug("PTOVENTA_RIMAC.CAJ_OBTIENE_MSJ_PROD_RIMAC(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_RIMAC.CAJ_OBTIENE_MSJ_PROD_RIMAC(?,?,?,?)",
                                                          parametros);
    }
    
    


    /**
     * Retorna el tiempo maximo para anular un pedido de recarga virtual.
     * @author  dubilluz
     * @since   09.11.2007
     */
    public static String getTimeMaxAnulacion(String pNum_ped) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        // Se modifico para enviar el numero de pedido
        // y extraer el tiempo maximo para poder anular el pedido
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNum_ped.trim());
        //return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJ_GET_TIEMPO_MAX_ANULACION(?,?,?)", parametros).trim();
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.GET_TIEMPO_MAX_ANULACION(?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Retorna el numero de recarga
     * @author  dubilluz
     * @since   14.11.2007
     */
    public static String getNumeroRecarga(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed);
        log.debug("Param. Numeros recarga  :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_GET_NUMERO_RECARGA(?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Retorna el numero de Pedido
     * @author  dubilluz
     * @since   31.03.2008
     */
    public static String getNumeroPedido(String pnumeroComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pnumeroComp);
        log.debug("Param.ver el numero de Pedido :" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJ_GET_NUMERO_PEDIDO(?,?,?)",
                                                           parametros).trim();
    }

    public static String obtieneTipoPedido() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        log.debug("Param.ver el tipo pedido :" + parametros);
        //FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_CAJ.CAJ_OBTIENE_INFO_PEDIDO(?,?,?,?)",parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.GET_TIPO_PEDIDO(?,?,?)", parametros).trim();
    }

    public static String obtieneFormaPagoPedido() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        log.debug("Param.ver Formas Pago pedido :" + parametros);
        //FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_CAJ.CAJ_OBTIENE_INFO_PEDIDO(?,?,?,?)",parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.GET_FORMAS_PAGO_PEDIDO(?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Se obtiene los consejos a imprimir.
     * @param pNumPedVta
     * @param pIpServ
     * @return cadena a imprimir
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 09.05.2008
     */
    public static String obtieneConsejos(String pNumPedVta, String pIpServ) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega COD_CIA.
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIpServ);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vCodCia);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_IMP_CONSEJOS.IMP_PROCESA_CONSEJOS(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String obtieneNameImpConsejos() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //JCHAVEZ 30.06.2009.sn se obtiene el IP de la pc del cliente, para pasar como parámetro a la funcion
        /*
        * InetAddress ip=null;
        try {
              ip = InetAddress.getLocalHost();
              log.debug("IP:"+ip.getHostAddress() );
        }
        catch (Exception e) {
           log.error("",e);
        }
        parametros.add( ip.getHostAddress());
        */
        log.debug("Antes de obtener impresora consejo    PTOVENTA_IMP_CONSEJOS.IMP_GET_NAME_IMP_CONSEJO(?,?)\":");
        //JCHAVEZ 30.06.2009.en
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_IMP_CONSEJOS.IMP_GET_NAME_IMP_CONSEJO(?,?)",
                                                           parametros);
    }

    //JSANTIVANEZ 10.01.2011

    public static String obtieneNameImpSticker() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //JCHAVEZ 30.06.2009.sn se obtiene el IP de la pc del cliente, para pasar como parámetro a la funcion
        /*
        * InetAddress ip=null;
        try {
              ip = InetAddress.getLocalHost();
              log.debug("IP:"+ip.getHostAddress() );
        }
        catch (Exception e) {
           log.error("",e);
        }
        parametros.add( ip.getHostAddress());
        */
        log.debug("Antes de obtener impresora consejo    PTOVENTA_IMP_CONSEJOS.IMP_GET_NAME_IMP_STICKER(?,?)\":");
        //JCHAVEZ 30.06.2009.en
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_IMP_CONSEJOS.IMP_GET_NAME_IMP_STICKER(?,?)",
                                                           parametros);
    }


    public static String obtieneIndCommitAntesdeRecargar() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_IND_CMM_ANTES_RECARGA", parametros);
    }

    /**
     * Graba la respuesta de Recaraga Virtual
     * @param pCodRespuesta
     * @param pNumPed
     * @throws SQLException
     */
    public static void grabaRespuestaRecargaVirtual(String pCodRespuesta, String pNumPed) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(pNumPed.trim());
        parametros.add(pCodRespuesta.trim());
        log.debug("invocando a  PTOVENTA_CAJ.CAJ_GRABA_RESPTA_RECARGA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_GRABA_RESPTA_RECARGA(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se obtiene los datos de delivery
     * @author Jorge Cortez Alvarez
     * @since 13.06.2008
     */
    public static String obtieneDatosDelivery(String pNumPedVta, String pIpServ) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIpServ);
        log.debug("invocando a PTOVENTA_DEL_CLI.IMP_DATOS_DELIVERY(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.IMP_DATOS_DELIVERY(?,?,?,?)", parametros);
    }

    /**
     * Se valida el pago del pedido de campaña por su forma de pago (Cupon)
     * @author Jorge Cortez Alvarez
     * @since 24.06.2008
     */
    public static void obtieneMontoFormaPagoCuponCampaña(ArrayList pArrayList, String pCodFormaPago,
                                                         String pCantCupon) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pCodFormaPago);
        parametros.add(new Integer(pCantCupon));
        log.debug("invocando a PTOVENTA_CAJ.CAJ_VALIDA_CANT_CUPON_CAMP(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_VALIDA_CANT_CUPON_CAMP(?,?,?,?,?)",
                                                          parametros);
    }

    public static String obtieneTipoImprConsejo() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a FARMA_GRAL.GET_TIPO_IMPR_CONSEJO(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_TIPO_IMPR_CONSEJO(?,?)", parametros);
    }


    /**
     * Se obtiene indicador de impresion de comanda
     * @author JCORTEZ
     * @since 27.06.2008
     */
    public static String obtieneIndImpresion() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_IMP_CONSEJOS.IMP_GET_IND_IMP_CUPON(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_IMP_CONSEJOS.IMP_GET_IND_IMP_CUPON(?,?)",
                                                           parametros);
    }

    /**
     * Se obtiene indicador de impresion por error en recarga virtual
     * @author JCORTEZ
     * @since 27.06.2008
     */
    public static String obtieneIndImpresionRecarga(String pCodError) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodError);
        log.debug("invocando a PTOVENTA_CAJ.IMP_GET_IND_IMP_RECARGA(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.IMP_GET_IND_IMP_RECARGA(?,?)", parametros);
    }

    /**
     * Se obtiene cupones que se imprimiran
     * @author Diego Ubilluz Carrillo
     * @since 03.07.2008
     */
    public static void obtieneCuponesPedidoImpr(ArrayList pArrayList, String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("invocando a PTOVENTA_IMP_CUPON.IMP_GET_CUPONES_PEDIDO(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_IMP_CUPON.IMP_GET_CUPONES_PEDIDO(?,?,?)",
                                                          parametros);
    }


    /**
     * Se obtiene datos de cupona para imprimir.
     * @param pNumPedVta
     * @param pIpServ
     * @return cadena a imprimir
     * @throws SQLException
     * @author Diego Ubilluz Carrillo
     * @since 03.07.2008
     */
    public static String obtieneImprCupon(String pNumPedVta, String pIpServ, String pCodCupon) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega COD_CIA.
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIpServ);
        parametros.add(pCodCupon.trim());
        parametros.add(FarmaVariables.vCodCia);

        log.debug("invocando a PTOVENTA_IMP_CUPON.IMP_PROCESA_CUPON(?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_IMP_CUPON.IMP_PROCESA_CUPON(?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Cambia el indicador de impresion del cupon
     * @param pNumPedVta
     * @param pCodCupon
     * @throws SQLException
     * @author Diego Ubilluz
     * @since  03.07.2008
     */
    public static void cambiaIndImpresionCupon(String pNumPedVta, String pCodCupon) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(pNumPedVta.trim());
        parametros.add(pCodCupon.trim());
        log.debug("invocando a PTOVENTA_IMP_CUPON.IMP_UPDATE_IND_IMP(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_IMP_CUPON.IMP_UPDATE_IND_IMP(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se valida la existencia de forma de pago por campaña
     * @author  JCORTEZ
     * @since   02.07.2008
     */
    public static void getFormaPagoCampaña(ArrayList array, String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed);
        log.debug("invocando a PTOVENTA_CAJ.GET_VERIFICA_CAMP_FP(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_CAJ.GET_VERIFICA_CAMP_FP(?,?,?)",
                                                          parametros);
    }

    /**
     * actualiza estado de pedido cupon
     * @author JCORTEZ
     * @since 03.07.2008
     */
    public static void actualizaIndImpre(String CodCamp, String pNumPed, String estado,
                                         String todos) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(CodCamp.trim());
        parametros.add(pNumPed.trim());
        parametros.add(estado.trim());
        parametros.add(todos.trim());
        log.debug("invocando a PTOVENTA_CAJ.CAJ_UPDATE_IND_IMP(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_UPDATE_IND_IMP(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se genera los cupones que se aceptaron al cobrar pedido
     * @author JCORTEZ
     * @since 03.07.2008
     */
    public static void generarCuponPedido(String pNumPed) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(pNumPed.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_GENERA_CUPON(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_GENERA_CUPON(?,?,?,?)", parametros, false);
    }

    /**
     * Se verifica si el pedido contiene productos de campaña
     * @author JCORTEZ
     * @since 03.07.2008
     */
    public static String verificaPedidoCamp(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(pNumPed.trim());
        log.debug("invocando a PTOVENTA_CAJ.GET_VERIFICA_PED_CAMP(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.GET_VERIFICA_PED_CAMP(?,?,?)", parametros);
    }


    /**
     * Se obtiene forma pago pedido campaña
     * @author  JCORTEZ
     * @since   07.07.2008
     */
    public static void getDetalleFormaPagoCampaña(ArrayList array, String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed);
        log.debug("invocando a PTOVENTA_CAJ.GET_FORMA_PAGO_PED_CUPON(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_CAJ.GET_FORMA_PAGO_PED_CUPON(?,?,?)",
                                                          parametros);
    }

    /**
     * Se procesan las campañas que no tengan forma de pago para el pedido
     * @author JCORTEZ
     * @since 10.07.2008
     */
    public static void procesaCampSinFormaPago(String pNumPed) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia.trim());
        parametros.add(FarmaVariables.vCodLocal.trim());
        parametros.add(pNumPed.trim());
        log.debug("invocando a PTOVENTA_CAJ.CAJ_UPDATE_IND_IMP_SIN_FP(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_UPDATE_IND_IMP_SIN_FP(?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se obtiene obtiene el numero de pedido delivery
     * @author JCORTEZ
     * @since 16.07.2008
     */
    public static String obtieneNumPedDelivery(String NumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(NumPed);
        log.debug("invocando a PTOVENTA_DEL_CLI.GET_NUM_PED_DELIVERY(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.GET_NUM_PED_DELIVERY(?,?,?)", parametros);
    }

    /**
     * Intenta actualizar los cupones en matriz.
     * @param pNumPedVta
     * @param pIndLineMatriz
     * @throws SQLException
     * @author Diego Ubilluz
     * @since 20.08.2008
     */
    public static void getcuponesPedido(String pNumPedVta, String pIndLineMatriz, ArrayList pListOut,
                                        String pTipConsulta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIndLineMatriz.trim());
        parametros.add(pTipConsulta.trim());
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_CUR_CUP_PED(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pListOut, "PTOVENTA_CUPON.CUP_F_CUR_CUP_PED(?,?,?,?,?)",
                                                          parametros);
    }

    /**
     * Obtiene el estado del cupon y bloque el cupon para su operacion
     * @param pNumPedVta
     * @param pCodCupon
     * @throws SQLException
     * @author Diego Ubilluz
     * @since  20.08.2008
     */
    public static String getEstCuponBloqueo(String pNumPedVta, String pCodCupon) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCupon.trim());
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_CHAR_BLOQ_EST(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CUPON.CUP_F_CHAR_BLOQ_EST(?,?)", parametros);
    }

    /**
     * Retorna el indicador de Multiplo Uso de la campaña que tiene asignada el cupon
     * @param pNumPedVta
     * @param pCodCupon
     * @throws SQLException
     * @author Diego Ubilluz
     * @since  20.08.2008
     */
    public static String getIndCuponMultiploUso(String pNumPedVta, String pCodCupon) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCupon.trim());
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_CHAR_IND_MULTIPLO_CUP(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CUPON.CUP_F_CHAR_IND_MULTIPLO_CUP(?,?)",
                                                           parametros);
    }

    public static String grabaCuponEnMatriz(String pCodCupon, String pFechaIni, String pFechaFin,
                                            String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCupon.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pFechaIni.trim());
        parametros.add(pFechaFin.trim());
        log.debug("invocando a PTOVENTA_MATRIZ_CUPON.GRABAR_CUPON(?,?,?,?,?,?):" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStrOut("PTOVENTA_MATRIZ_CUPON.GRABAR_CUPON(?,?,?,?,?,?)",
                                                                    parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                    pIndCloseConecction);
    }

    public static void actualizaCuponGeneral(String pCodCupon, String pTipoConsulta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCupon);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pTipoConsulta.trim());
        log.debug("invocando a PTOVENTA_CUPON.CUP_P_ACT_GENERAL_CUPON(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CUPON.CUP_P_ACT_GENERAL_CUPON(?,?,?,?)", parametros,
                                                 true);
    }

    public static String actualizaEstadoCuponEnMatriz(String pCodCupon, String pEstado,
                                                      String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCupon.trim());
        parametros.add(pEstado.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_MATRIZ_CUPON.ACT_ESTADO_CUPON(?,?,?,?,?):" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStrOut("PTOVENTA_MATRIZ_CUPON.ACT_ESTADO_CUPON(?,?,?,?,?)",
                                                                    parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                    pIndCloseConecction);
    }

    public static String getEstadoCuponEnMatriz(String pCodCupon, String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCupon.trim());
        log.debug("invocando a PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON(?,?,?):" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStrOut("PTOVENTA_MATRIZ_CUPON.CONSULTA_ESTADO_CUPON(?,?,?)",
                                                                    parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                    pIndCloseConecction);
    }


    /**
     * Obtiene el indicador si tiene el pedido cupones que usa
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String getIndTieneCupones(String pNumPedVta, String pTipo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pTipo.trim());
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_CHAR_CUP_PED(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CUPON.CUP_F_CHAR_CUP_PED(?,?,?,?)", parametros);
    }


    public static String getLineasDetalleDocumento(String cIdTabGral) throws SQLException {
        parametros = new ArrayList();
        parametros.add(cIdTabGral);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_F_VAR_LINEA_DOC(?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_VAR_LINEA_DOC(?)", parametros);
    }

    public static String getIndPedConvenio(String vNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vNumPedVta);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_F_VAR_IND_PED_CONVENIO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_VAR_IND_PED_CONVENIO(?,?,?)",
                                                           parametros);

    }

    /**
     * @author Dubilluz
     * @since  25.11.2008
     * @param pSecIni
     * @param pSecFin
     * @throws SQLException
     */
    public static String isExistCompProcesoSAP(String pSecIni, String pSecFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecIni.trim());
        parametros.add(pSecFin.trim());
        parametros.add(VariablesCaja.vTipComp);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_F_IS_COMP_PROCESO_SAP(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_IS_COMP_PROCESO_SAP(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Retorna la cadena con el numero de pedido de Delivery
     * Si el pedido no es de Delivery retorna el parametros "N"
     * @author DUBILLUZ
     * @since  26.11.2008
     * @param pNumPed
     * @return
     * @throws SQLException
     */
    public static String getDatosPedDelivery(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        log.debug("invocando a Ptoventa_Del_Cli.CLI_F_GET_DATOS_PED_DELIVERY(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Del_Cli.CLI_F_GET_DATOS_PED_DELIVERY(?,?,?)",
                                                           parametros);
    }

    public static void enviaAlertaDelivery(String pCodCiaDel, String pCodLocalDel,
                                           String pNumPedDel) throws SQLException {
        parametros = new ArrayList();

        parametros.add(pCodCiaDel);
        parametros.add(pCodLocalDel);
        parametros.add(pNumPedDel);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a Ptoventa_Del_Cli.CLI_P_ENVIA_ALERTA_DELIVERY(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "Ptoventa_Del_Cli.CLI_P_ENVIA_ALERTA_DELIVERY(?,?,?,?)",
                                                 parametros, true);
    }


    /**
     *
     * @author dubilluz
     * @since  27.11.2008
     * @param pFechaCierreDia
     * @return
     * @throws SQLException
     */
    public static String validaCompDesfase(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia.trim());
        log.debug("invocando a Farma_Gral.F_EXISTE_DESFASE_COMP(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_EXISTE_DESFASE_COMP(?,?,?)", parametros);
    }

    /**
     * Valida si existen delivery pendiente sin regularizar
     * @author Dubilluz
     * @return
     * @throws SQLException
     */
    public static String validaDelPendSinReg(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        log.debug("invocando a Farma_Gral.F_EXISTE_DEL_PEND_SIN_REG(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_EXISTE_DEL_PEND_SIN_REG(?,?,?)", parametros);
    }

    public static String validaAnulPeddSinReg(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        log.debug("invocando a Farma_Gral.F_EXISTE_ANUL_PED_PEND_SIN_REG(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_EXISTE_ANUL_PED_PEND_SIN_REG(?,?,?)",
                                                           parametros);
    }

    /**
     * Valida si existen comprobantes manuales declarados sin regularizar
     * @author Dubilluz
     * @since  01.12.2008
     * @return
     * @throws SQLException
     */
    public static String validaPedidosManualesSinReg(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        log.debug("invocando a Farma_Gral.F_EXISTE_PED_MANUAL_SIN_REG(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_EXISTE_PED_MANUAL_SIN_REG(?,?,?)",
                                                           parametros);
    }

    public static String pruebaImpresoraTermica(String pCodCupon) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega parametro COD_CIA.
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(pCodCupon.trim());
        parametros.add(FarmaVariables.vCodCia);
        log.debug("Farma_Gral.F_PRUEBA_IMPR_TERMICA(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_PRUEBA_IMPR_TERMICA(?,?,?,?,?)", parametros);
    }

    /**
     * Activa los cupones que se activaron
     * @author Dubilluz
     * @since  02.12.2008
     * @param pNumPedido
     * @throws SQLException
     */
    public static void activarCuponesUsados(String pNumPedido) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_CUPON.CUP_P_ACTIVA_CUPONES(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CUPON.CUP_P_ACTIVA_CUPONES(?,?,?,?)", parametros,
                                                 true);
    }

    //getcuponesUsadosPedido

    public static void getcuponesUsadosPedido(String pNumPedVta, ArrayList pListOut) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_CUR_CUP_USADOS(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pListOut, "PTOVENTA_CUPON.CUP_F_CUR_CUP_USADOS(?,?,?)",
                                                          parametros);
    }

    public static void activaCuponenMatriz(String pCodCupon, String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCupon.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_MATRIZ_CUPON.CUP_P_ACTIVA_CUPONES(?,?,?,?):" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_CUPON.CUP_P_ACTIVA_CUPONES(?,?,?,?)",
                                                       parametros, true, FarmaConstants.CONECTION_MATRIZ,
                                                       pIndCloseConecction);

    }

    /**
     * Lista las Formas de Pago Convenio
     * @author : asolis
     * @since  : 11.12.2008
     * */
    public static void obtieneFormasPagoConvenio(FarmaTableModel pTableModel, String indConvenio, String codConvenio,
                                                 String codCliente, String numPed,
                                                 String valorCredito) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(indConvenio);
        parametros.add(codConvenio);
        parametros.add(codCliente);
        parametros.add(numPed);
        parametros.add(valorCredito);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_OBTIENE_FORMAS_PAG_CONV(?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CAJ.CAJ_OBTIENE_FORMAS_PAG_CONV(?,?,?,?,?,?,?)", parametros,
                                                 false);


    }


    /**
     * Lista las Formas de Pago Sin Convenio
     * @author : asolis
     * @since  : 11.12.2008
     * */
    public static void obtieneFormasPagoSinConvenio(FarmaTableModel pTableModel, String indConvenio,
                                                    String codConvenio, String codCliente,
                                                    String numPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(indConvenio);
        parametros.add(codConvenio);
        parametros.add(codCliente);
        parametros.add(numPed);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_OBTIENE_FORMAS_PAG_SINCONV(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CAJ.CAJ_OBTIENE_FORMAS_PAG_SINCONV(?,?,?,?,?,?)",
                                                 parametros, false);


    }

    /* *********************************************************************** */

    public static void analizaCanjeLocal(String pDni, String pNumPed, String pAccion,
                                         String pIndEliminaRespaldo) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pAccion);
        parametros.add(pIndEliminaRespaldo.trim());
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_P_ANALIZA_CANJE(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CA_CLIENTE.CA_P_ANALIZA_CANJE(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void analizaCanjeMatriz(String pDni, String pNumPed, String pAccion) throws SQLException {
        parametros = new ArrayList();
        log.debug("15/07/09 Analiza Cupon");
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pDni.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pAccion);
        log.debug("invocando a PTOVENTA_MATRIZ_CA_CLI.CA_P_ANALIZA_CANJE(?,?,?,?,?,?):" + parametros);
        /*
      FarmaDBUtilityRemoto.executeSQLStoredProcedure(
                                              null,
                                              "PTOVENTA_MATRIZ_CA_CLI.CA_P_ANALIZA_CANJE(?,?,?,?,?,?)",
                                              parametros, false,
                                              FarmaConstants.CONECTION_MATRIZ,
                                              FarmaConstants.INDICADOR_N);
      */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MATRIZ_CA_CLI.CA_P_ANALIZA_CANJE(?,?,?,?,?,?)",
                                                 parametros, false);

    }

    public static String getDniFidPedidoCampana(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CHAR_GET_DNI_PED(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CA_CLIENTE.CA_F_CHAR_GET_DNI_PED(?,?,?)",
                                                           parametros).trim();
    }

    public static String getExistRegaloCampanaAcumulada(String pDni, String pNumPed) throws SQLException {
        parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pDni.trim());
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CHAR_EXIST_REGALO(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CA_CLIENTE.CA_F_CHAR_EXIST_REGALO(?,?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Metodo encargado de obtener el DNI si se trata de venta que acumula ventas
     * @param pNumPed
     * @return
     * @throws SQLException
     */
    public static String getDniPedidoAcumulaVenta(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed);
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CHAR_GET_DNI_IMPRIMIR(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CA_CLIENTE.CA_F_CHAR_GET_DNI_IMPRIMIR(?,?,?)",
                                                           parametros);
    }


    /**
     * metodo encargado de obtener la cantidad  de unidades acumulados en la venta
     * @param listaMatriz
     * @param pDni
     * @throws SQLException
     */
    public static void getListCampRestPremioXCliente(ArrayList listaMatriz, String pDni,
                                                     String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pNumPedVta);
        log.debug("invocando a PTOVENTA_MATRIZ_CA_CLI.CA_F_CUR_CAMP_MATRIZ_REST(?,?,?,?):" + parametros);
        /*FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(listaMatriz,
                "PTOVENTA_MATRIZ_CA_CLI.CA_F_CUR_CAMP_MATRIZ_REST(?,?,?,?)",
                parametros, FarmaConstants.CONECTION_MATRIZ,
                FarmaConstants.INDICADOR_N); */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaMatriz,
                                                          "PTOVENTA_MATRIZ_CA_CLI.CA_F_CUR_CAMP_MATRIZ_REST(?,?,?,?)",
                                                          parametros);

    }

    /**
     * metodo encargado de obtener la cantidad  de unidades acumulados en la venta
     * @param listaLocal
     * @param pDni
     * @throws SQLException
     */
    public static void getListCampAcumuladaXCliente(ArrayList listaLocal, String pDni,
                                                    String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pNumPedVta);
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CUR_CAMP_SUM_LOCAL_PED(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaLocal,
                                                          "PTOVENTA_CA_CLIENTE.CA_F_CUR_CAMP_SUM_LOCAL_PED(?,?,?,?)",
                                                          parametros);
    }

    /**
     * metodo encargado de todas las campañas donde gano premio
     * @param listaPremios
     * @param pDni
     * @param pNumPedVta
     * @throws SQLException
     */
    public static void getListCampPremiosPedidoCliente(ArrayList listaPremios, String pDni,
                                                       String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pDni);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pNumPedVta);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CUR_CAMP_PREMIO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaPremios,
                                                          "PTOVENTA_CA_CLIENTE.CA_F_CUR_CAMP_PREMIO(?,?,?,?)",
                                                          parametros);
    }

    /**
     * metodo encargado de obtener la cabecera del doc html a generar y posteriormente a imprimir
     * @param pDni
     * @return
     * @throws SQLException
     */
    public static String getCabHtmlCampAcumXCliente(String pDni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(pDni);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CA_CLIENTE.CA_F_VAR_IMP_CAB_HTML(?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CA_CLIENTE.CA_F_VAR_IMP_CAB_HTML(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * metodo encargado de obtener la cabecera del doc html a generar y posteriormente a imprimir
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String getPieHtmlCampAcumXCliente(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_VAR_GET_PIE_HTML(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CA_CLIENTE.CA_F_VAR_GET_PIE_HTML(?,?,?)",
                                                           parametros);
    }

    /**
     *
     * Valida que el pedido fidelizado tenga relacion con productos canje y regalo
     * @author:  JCORTEZ
     * @since: 18.12.2008
     * */
    public static String VerificaProdFidel(String NumPed, String Dni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(NumPed);
        parametros.add(Dni);
        log.debug("invocando a PTOVENTA_CAJ_ANUL.CAJ_EXIST_PROD_CANJ_REG(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJ_EXIST_PROD_CANJ_REG(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Valida que el pedido fidelizado productos canje
     * @author:  JCORTEZ
     * @since: 18.12.2008
     * */
    public static int ExistProdCanje(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("invocando a PTOVENTA_CAJ_ANUL.CAJ_EXISTE_PROD_CANJE(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ_ANUL.CAJ_EXISTE_PROD_CANJE(?,?,?)",
                                                           parametros);
    }

    /**
     * Se anula Pedido Fidelizado y Canje si es que hubiera linea
     * @author:  JCORTEZ
     * @since: 18.12.2008
     * */
    public static void anulaPedidoFidelizado(String numPedVta, String Dni, String IndLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(numPedVta);
        parametros.add(Dni);
        parametros.add(IndLocal);
        log.debug("invocando a PTOVENTA_CAJ_ANUL.CAJ_ANULA_PED_FIDELIZADO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_ANULA_PED_FIDELIZADO(?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se anula Pedido Fidelizado y Canje en Matriz
     * @author:  JCORTEZ
     * @since: 18.12.2008
     * */
    public static void anulaPedidoFidelizadoMatriz(String numPedVta, String Dni, String IndLocal,
                                                   String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(numPedVta);
        parametros.add(Dni);
        parametros.add(IndLocal);
        log.debug("invocando a PTOVENTA_MATRIZ_CAJ_ANUL.CAJ_ANULA_PED_FIDELIZADO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA_MATRIZ_CAJ_ANUL.CAJ_ANULA_PED_FIDELIZADO(?,?,?,?,?,?)",
                                                       parametros, true, FarmaConstants.CONECTION_MATRIZ,
                                                       pIndCloseConecction);
    }

    public static void getPedidosCanj(String pDni, String pNumPed, ArrayList pLista) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pDni.trim());
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CUR_CANJ_PEDIDO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_CA_CLIENTE.CA_F_CUR_CANJ_PEDIDO(?,?,?,?)",
                                                          parametros);
    }

    public static void getOrigenPedCanj(String pDni, String pNumPed, ArrayList pLista) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pDni.trim());
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CUR_ORIG_PEDIDO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_CA_CLIENTE.CA_F_CUR_ORIG_PEDIDO(?,?,?,?)",
                                                          parametros);
    }


    public static void insertCanjMatriz(String pDni, String pNumPed, String pCodCampana, String pSecPedVta,
                                        String pCodProd, String pCantPedido, String pValFrac, String pEstado,
                                        String pFechaPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pFechaPedido.trim());
        parametros.add(pDni.trim());

        parametros.add(pCodCampana.trim());
        parametros.add(pSecPedVta.trim());
        parametros.add(pCodProd.trim());
        parametros.add(pCantPedido.trim());

        parametros.add(pValFrac.trim());
        parametros.add(pEstado.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CANJ_PED_CLI(?,?,?,?,?,?,?,?,?,?,?,?):" +
                  parametros);
        /*FarmaDBUtilityRemoto.executeSQLStoredProcedure(
                                                     null,
                                                     "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CANJ_PED_CLI(?,?,?,?,?," +
                                                                                                  "?,?,?,?,?," +
                                                                                                  "?,?)",
                                                     parametros,true,FarmaConstants.CONECTION_MATRIZ,
                                                     FarmaConstants.INDICADOR_N
                                                     );
      */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_CANJ_PED_CLI(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void insertOrigenMatriz(String pDni, String pNumPed, String pCodCampana, String pSecPedVta,
                                          String pCodProd, String pValFrac, String pEstado, String pCodLocalOrigen,
                                          String pNumPedOrige, String pSecProdOirgen, String pCodProdOrigen,
                                          String pCantUsoOrigen, String pFechaPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pFechaPedido.trim());
        parametros.add(pDni.trim());

        parametros.add(pCodCampana.trim());
        parametros.add(pSecPedVta.trim());
        parametros.add(pCodProd.trim());

        parametros.add(pValFrac.trim());
        parametros.add(pEstado.trim());
        parametros.add(FarmaVariables.vIdUsu);

        parametros.add(pCodLocalOrigen.trim());
        parametros.add(pNumPedOrige.trim());
        parametros.add(pSecProdOirgen.trim());
        parametros.add(pCodProdOrigen.trim());
        parametros.add(pCantUsoOrigen.trim());

        log.debug("invocando a PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_ORIG_PED_CLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):" +
                  parametros);
        /* FarmaDBUtilityRemoto.executeSQLStoredProcedure(
                                                     null,
                                                     "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_ORIG_PED_CLI(?,?,?,?,?," +
                                                                                                  "?,?,?,?,?," +
                                                                                                  "?,?,?,?,?," +
                                                                                                  "?)",
                                                     parametros,true,FarmaConstants.CONECTION_MATRIZ,
                                                     FarmaConstants.INDICADOR_N
                                                     );
      */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_ORIG_PED_CLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);

    }

    /**
     * Se lista los remitos registrados
     * @author JCORTEZ
     * @since 13.01.09
     * */
    public static void getListaRemitos(FarmaTableModel pTableModel, String FechaIni,
                                       String FechaFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FechaIni);
        parametros.add(FechaFin);
        //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_REMITOS(?,?,?,?)", parametros, false);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_REMITO.SEG_F_CUR_REMITOS_DU(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se lista fechas no asociadas a remitos
     * @author JCORTEZ
     * @since 13.01.09
     * */

    public static void getFecSinRemito(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_DIA_SIN_REMITO(?,?)",
                                                 parametros, false); //true
    }

    /**
     * Se lista las fechas asociadas al remito
     * @author JCORTEZ
     * @since 14.01.09
     * */

    public static void getFecRemito(FarmaTableModel pTableModel, String vCodRemito) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vCodRemito);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_FEC_REMITO(?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se lista los sobres de las fechas asociadas al remito
     * @author JCORTEZ
     * @since 14.01.09
     * */

    public static void getSobresFec(FarmaTableModel pTableModel, String vFechaVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vFechaVta.trim());
        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_SOBRE_FECHA_DET(?,?,?)",
                                                 parametros, false);
    }


    public static void getSobresRemito(FarmaTableModel pTableModel, String vCodremito) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vCodremito.trim());
        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_REMITO.SEG_F_CUR_SOBRE_REMITO_DU(?,?,?)",
                                                 parametros, false);
    }

    public static void getSobresFecNuevoRemito(FarmaTableModel pTableModel, String vFechaVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vFechaVta.trim());
        
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_SOBRE_FECHA(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Realiza los cambios de la relacion remito Fecha Venta
     * @author JCORTEZ
     * @since  14.01.2009
     */
    public static void AsignarRemito(ArrayList arrayLocales) throws SQLException {
        for (int i = 0; i < arrayLocales.size(); i++) {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(FarmaVariables.vIdUsu);
            parametros.add(VariablesCaja.NumRemito);
            parametros.add(((String)((ArrayList)arrayLocales.get(i)).get(0)).trim());
            log.debug("INGRESANDO.." + parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_SEGURIDAD.SEG_P_AGREGA_REMITO(?,?,?,?,?)",
                                                     parametros, false);
            log.debug("INGRESO EXITOSO...................................");
        }
    }

    /**
     * Se obtiene datos VAUCHER
     * @author JCORTEZ
     * @since 14.01.09
     */
    public static String obtieneDatosVoucherRem(String pNumPedVta, String pIpServ) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIpServ);
        log.debug("invocando a PTOVENTA_CE_SEGURIDAD.SEG_F_VAR2_IMP_DATOS_VOUCHER(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_VAR2_IMP_DATOS_VOUCHER(?,?,?,?)",
                                                           parametros);
    }


    /**
     * Se valida codigo remito
     * @author JCORTEZ
     * @since 14.01.09
     */
    public static String validarCodigo(String CodRemito) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(CodRemito);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_VAR2_EXISTE_REMITO(?,?,?)",
                                                           parametros);
    }

    public static String validaTiempoAnulacion(String pNumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido.trim());
        log.debug("invocando a Ptoventa_Recarga.RE_F_IS_PERMITE_ANULACION(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Recarga.RE_F_IS_PERMITE_ANULACION(?,?,?)",
                                                           parametros);
    }

    public static String obtieneCantIntentosLecturaImg() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_IMP_CUPON.IMP_GET_TIME_CAN_READ():" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_IMP_CUPON.IMP_GET_TIME_CAN_READ()", parametros);
    }


    /**
     * @author asolis
     * @fecha  01-02-09
     * Obtiene valor secuencial de comprobantes :Boleta y Factura
     * @throws SQLException
     * */

    public static ArrayList ObtieneValorComprobantes() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "Ptoventa_Caj.CAJ_F_VALOR_COMPROBANTES(?,?)",
                                                          parametros);
        return pOutParams;
    }


    /**
     *
     * @author asolis
     * @fecha  02-02-09
     * Obtiene el valor máximo de diferencia para los comprobantes :Boleta y Factura
     * @throws SQLException
     * */

    public static String ObtieneMaximaDiferencia() throws SQLException {
        parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Caj.CAJ_F_VALOR_MAXIMA_DIFERENCIA()", parametros);
    }

    /**@author asolis
     * @fecha  05-02-09
     * Cantidad de intentos para obtener respuesta de  recarga virtual
     * @throws SQLException
     **/

    public static String cantidadIntentosRespuestaRecarga() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a Ptoventa_Recarga.RE_F_CANT_INT_RECARGA_VIRTUAL():" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Recarga.RE_F_CANT_INT_RECARGA_VIRTUAL()",
                                                           parametros);
    }


    /**
     * @author asolis
     * @fecha  06-02-09
     * Obtiene valor secuencial de comprobantes :Boleta
     * @throws SQLException
     * */

    public static ArrayList ObtieneValorComprobanteBoleta() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumSerieLocalBoleta);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,
                                                          "Ptoventa_Caj.CAJ_F_VALOR_COMPROBANTE_BOLETA(?,?,?)",
                                                          parametros);
        return pOutParams;
    }


    /**
     * @author asolis
     * @fecha  06-02-09
     * Obtiene valor secuencial de comprobantes :Factura
     * @throws SQLException
     * */

    public static ArrayList ObtieneValorComprobanteFactura() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumSerieLocalFactura);

        log.debug("invocando a Ptoventa_Caj.CAJ_F_VALOR_COMP_FACTURA(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "Ptoventa_Caj.CAJ_F_VALOR_COMP_FACTURA(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    /**
     *metodo encargado de obtener el dniClienteFidVenta
     *
     * */
    public static String obtieneDniClienteFidVenta(String nroPedido) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroPedido);
        log.debug("jcallo : invocando a Ptoventa_Caj.CAJ_F_CHAR_DNI_CLIENTE(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Caj.CAJ_F_CHAR_DNI_CLIENTE(?,?,?)", parametros);
    }

    /**
     *metodo encargado de obtener las campañas de fidelizacion automaticas de la venta
     *
     * */
    public static ArrayList getListaCampAutomaticasVta(String nroPedido) throws SQLException {
        ArrayList listaCampVenta = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroPedido);
        log.debug("jcallo : invocando a Ptoventa_Caj.CAJ_F_LISTA_CAMP_AUTOMATIC(?,?,?): " + parametros);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaCampVenta,
                                                          "Ptoventa_Caj.CAJ_F_LISTA_CAMP_AUTOMATIC(?,?,?)",
                                                          parametros);

        return listaCampVenta;
    }

    /**
     * metodo encargado de obtener el listado
     *
     * */
    public static ArrayList getListaCampUsadosLocalXCliente(String dniCliente) throws SQLException {
        ArrayList listaCampLimitUsoLocal = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(dniCliente);
        log.debug("jcallo : invocando a Ptoventa_FIDELIZACION.FID_F_LISTA_CAMPCL_USADOS(?,?,?): " + parametros);

        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaCampLimitUsoLocal,
                                                          "Ptoventa_FIDELIZACION.FID_F_LISTA_CAMPCL_USADOS(?,?,?)",
                                                          parametros);

        return listaCampLimitUsoLocal;
    }

    /**
     * metodo encargado de obtener el listado
     *
     * */
    public static ArrayList getListaCampUsadosMatrizXCliente(String dniCliente) throws SQLException {
        ArrayList listaCampLimitUsoMatriz = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(dniCliente);
        log.debug("jcallo : invocando a PTOVENTA_MATRIZ_FID.FID_F_LISTA_CAMPCL_USADOS(?,?,?): " + parametros);

        FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(listaCampLimitUsoMatriz,
                                                                "PTOVENTA_MATRIZ_FID.FID_F_LISTA_CAMPCL_USADOS(?,?,?)",
                                                                parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                FarmaConstants.INDICADOR_N);

        return listaCampLimitUsoMatriz;
    }


    /**
     * metodo encargado registrar o actualizar la cantidad de veces que se uso cada campaña limitante
     * en local
     *
     * */
    public static void registrarUsoCampLimitLocal(String codCampLimit, String dniCliente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codCampLimit);
        parametros.add(dniCliente);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_REG_CAMP_LIM_CLIENTE(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_REG_CAMP_LIM_CLIENTE(?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * metodo encargado registrar o actualizar la cantidad de veces que se uso cada campaña limitante
     * en matriz si hubiera linea
     *
     * */
    public static void registrarUsoCampLimitMatriz(String codCampLimit, String dniCliente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codCampLimit);
        parametros.add(dniCliente);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_REG_CAMP_LIM_CLIENTE(?,?,?,?,?) de matriz:" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null, "Ptoventa_MATRIZ_Caj.CAJ_REG_CAMP_LIM_CLIENTE(?,?,?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_MATRIZ,
                                                       FarmaConstants.INDICADOR_N);
    }


    /**
     * Se obtiene datos Ticket al consultar Recarga Virtual
     * @author ASOLIS
     * @since 11.02.09
     */
    //  public static String obtieneDatosTicket(String pNumPedVta,String pFechaVenta,String pProveedor,String pTelefono,int pMonto,String pRespRecarga ,String pComunicado) throws SQLException{

    public static String obtieneDatosTicket(String pNumPedVta, int pMonto) throws SQLException {

        parametros = new ArrayList();
        //parametros.add(FarmaVariables.vCodGrupoCia);
        //parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        //parametros.add(pFechaVenta);
        //parametros.add(pProveedor);
        //parametros.add(pTelefono);
        parametros.add(new Double(pMonto));
        // parametros.add(pRespRecarga);
        //parametros.add(pComunicado);
        //parametros.add(VariablesCaja.vSec_usu_local);
        /*Ptoventa_Recarga
       * RE_F_IMP_HTML_RECARGA_COMPROB(  cNumPedVta_in     IN CHAR,
                                         cFechaPedido      IN CHAR,
                                         cProveedor        IN CHAR,
                                         cTelefono         IN CHAR,
                                         nMontoVta_in      IN NUMBER,
                                         cRespuestaRecarga IN CHAR,
                                         cComunicado       IN CHAR)
       *
       * */
        /*
      log.debug("invocando a Ptoventa_Recarga.RE_F_IMP_HTML_RECARGA_PEDIDO(?,?,?,?,?,?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Recarga.RE_F_IMP_HTML_RECARGA_PEDIDO(?,?,?,?,?,?,?)",parametros);
    */

        log.debug("invocando a Ptoventa_Recarga.RE_F_IMP_HTML_RECARGA_PEDIDO(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Recarga.RE_F_IMP_HTML_RECARGA_PEDIDO(?,?)",
                                                           parametros);
    }

    public static void bloqueoCaja(String vSecCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vSecCaja.trim());
        log.debug("invocando a PTOVENTA_CAJ.CAJ_P_FOR_UPDATE_MOV_CAJA(?,?,?) de matriz:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "Ptoventa_Caj.CAJ_P_FOR_UPDATE_MOV_CAJA(?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se obtiene el tipo de comprobante actual de la caja
     * @AUTHOR JCORTEZ
     * @SINCE 25.03.09
     * */
    public static String getObtieneTipoComp(String numCaja, String tipComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numCaja.trim());
        parametros.add(tipComp.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Caj.CAJ_GET_TIPO_COMPR(?,?,?,?)", parametros);
    }

    /**
     * SE OBTIENE EL MENSAJE DE LA CAMPAÑA ASOCIADA
     * @AUTHOR MFAJARDO
     * @SINCE 13.04.09
     * */
    public static String ObtieneCampanas(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAMPANA.CAMP_F_VAR_MSJ_CAMPANA(?,?,?)",
                                                           parametros);
    }

    /**
     * SE OBTIENE EL MENSAJE DE anulacion de ticket y La caja del usuario
     * @AUTHOR MFAJARDO
     * @SINCE 24.04.09
     * */
    //Mfajardo 24/04/09 metodo imprimir ticket de anulacion
    public static String ImprimeMensajeAnulacion(String cajero, String turno, String numpedido, String cod_igv,
                                                 String pIndReimpresion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cajero);
        parametros.add(turno);
        parametros.add(numpedido);
        parametros.add(cod_igv);
        parametros.add(pIndReimpresion); //JCHAVEZ 08.07.2009.n parámetro indicador del tipo de impresión de anulación: impresión o reimpesión
        log.debug("Parametros " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TICKETERA.CAMP_F_VAR_MSJ_ANULACION(?,?,?,?,?,?,?)",
                                                           parametros);
    }
    //Mfajardo 24/04/09 metodo imprimir ticket de anulacion

    public static int obtieneNumeroCajaUsuarioAux() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vSecuenciaUsoUsuario);
        log.debug("invoca PTOVENTA_CAJ.CAJ_OBTIENE_CAJA_USUARIO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_OBTIENE_CAJA_USUARIO(?,?,?)", parametros);
    }

    //JCORTEZ 14/05/2009 Setea numero de caja al antes cobrar pedido

    public static int obtieneNumeroCajaUsuarioAux2() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("invoca PTOVENTA_CAJ.CAJ_OBTIENE_CAJA_USUARIO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_OBTIENE_CAJA_USUARIO(?,?,?)", parametros);
    }


    /**
     * Se obtiene el tipo de comprobante actual de la caja
     * @AUTHOR JCORTEZ
     * @SINCE 25.03.09
     * */
    public static String getObtieneTipoComp2(String numCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numCaja.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Caj.CAJ_GET_TIPO_COMPR2(?,?,?)", parametros);
    }

    /**
     * Se obtiene el secuencial de comprobante por
     * @AUTHOR JCORTEZ
     * @SINCE 28.04.09
     * */
    public static void obtieneNumCompTip_ForUpdate(ArrayList pArrayList, String pSecImprLocal,
                                                   String NumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecImprLocal);
        parametros.add(NumPed);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_OBTIENE_NUM_COMP_PAGO_IMPR(?,?,?,?)",
                                                          parametros);
    }

    /**
     * Se valida y/o bloquea caja pago para el cobro
     * @AUTHOR JCORTEZ
     * @SINCE 18.05.09
     * */

    public static String obtieneEstadoCaja() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(VariablesCaja.vNumCaja.trim())); //nNumCaj_in
        log.debug("invoca PTOVENTA_CAJ.CAJ_VALIDA_CAJA_APERTURA(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Caj.CAJ_VALIDA_CAJA_APERTURA(?,?,?)", parametros);
    }


    /**
     * Se valida la ip desde donde se emite impresion ticket
     * @AUTHOR JCORTEZ
     * @SINCE 09.06.09
     * */
    public static String validaImpresioPorIP(String IP, String TipComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(IP);
        parametros.add(TipComp);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_VALIDA_IP(?,?,?,?)", parametros);
    }

    /**
     * Se obtiene el tipo de comprobante por IP
     * @AUTHOR JCORTEZ
     * @SINCE 09.06.09
     * */
    public static String getObtieneTipoCompPorIP(String IP, String tipComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(IP.trim());
        parametros.add(tipComp.trim());
        log.debug("invoca PTOVENTA_ADMIN_IMP.IMP_GET_TIPCOMP_IP(?,?,?,?):" + parametros);
        //return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_TIPCOMP_IP(?,?,?,?)",parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_TIPCOMP_IP(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Se obtiene secuencial de impresora por IP
     * @AUTHOR JCORTEZ
     * @SINCE 09.06.09
     * */
    public static String getObtieneSecImpPorIP(String IP, String pTipComp, String pIndPedManual) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(IP.trim());
        parametros.add(pTipComp);
        parametros.add(pIndPedManual);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_SECIMPR_IP(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Se obtiene secuencial de facturas de impresora por IP
     * @AUTHOR LLEIVA
     * @SINCE 31-Ene-2014
     * */
    public static String getObtieneSecFacImpPorIP(String IP) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(IP.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_SECIMPR_FAC_IP(?,?,?)",
                                                           parametros);
    }

    public static void obtieneSecuenciaImpresorasVenta(ArrayList pArrayList, String IP) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(IP);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_ADMIN_IMP.CAJ_SECUENCIA_IMPRESORAS_VENTA(?,?,?)",
                                                          parametros);
    }


    /**
     * Se obtiene secuencial de impresora por origen de anulacion
     * @AUTHOR JCORTEZ
     * @SINCE 15.06.09
     * */
    public static String getObtieneSecImpPorOrigen(String IP, String tipComp, String NumPedAnul) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(tipComp.trim());
        parametros.add(NumPedAnul.trim());
        parametros.add(IP.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_SECIMPR_ORIGEN(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Se obtiene secuencial de impresora por IP
     * @AUTHOR JCORTEZ
     * @SINCE 09.06.09
     * */
    public static String getExistSecImp(String SecImpr, String IP) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecImpr.trim());
        parametros.add(IP.trim());
        log.debug("parametros-->" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_EXIST_SECIMPR(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Se obtiene descripcion de impresora
     * @AUTHOR JCORTEZ
     * @SINCE 15.06.09
     * */
    public static String getNombreImpresora(String SecImpr) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecImpr.trim());
        log.debug("parametros-->" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_DESC(?,?,?)", parametros);
    }

    /**
     * Se obtiene descripcion de impresora
     * @AUTHOR JCHAVEZ
     * @SINCE 03.07.2009
     * */
    public static String obtieneTipoImprConsejoXIp() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("parametros-->" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_TIPO_IMPR_CONSEJO_X_IP(?,?)", parametros);
    }

    /**
     * Se verifica si el pedido está anulado
     * @author JCHAVEZ
     * @since 06.07.2009
     */
    public static void verificaPedidoAnulado(String pCorrelativo, String pMonto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCorrelativo);
        parametros.add(new Double(pMonto));
        parametros.add(ConstantsPtoVenta.TIP_COMP_TICKET);
        log.debug("jcallo : PTOVENTA_CAJ_ANUL.CAJ_VERIFICA_PEDIDO parametros : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_VERIFICA_PEDIDO_ANU(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Lista el detalle del pedido anulado
     * @author JCHAVEZ
     * @since 06.07.2009
     */
    public static void getListaDetallePedidoAnulado(FarmaTableModel pTableModel, String correlativo, String tipoComp,
                                                    String numComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(correlativo);
        parametros.add(tipoComp);
        parametros.add(numComp);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CAJ_ANUL.CAJ_LISTA_DETALLE_PEDIDO_ANU(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Lista el detalle del pedido anulado
     * @author JCHAVEZ
     * @since 06.07.2009
     */
    public static String obtieneCajaTurnoPedidoAnulado(String pCorrelativo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCorrelativo);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJA_TURNO_PEDIDO_ANULADO(?,?,?)",
                                                           parametros);
    }

    /**@author jmiranda
     * @fecha  08-07-09
     * Obtiene Cantidad de Dias
     * @throws SQLException
     **/

    public static String ObtieneNroDiasEliminar() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a Farma_Gral.F_OBT_DIAS_ELIMINAR_TXT():" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_CHAR_OBT_DIAS_ELIMINAR_TXT()", parametros);
    }

    /**
     * Graba inicio y fin del proceso de cobro del pedido
     * @author JCHAVEZ
     * @since 09.07.2009
     */
    public static void grabaInicioFinProcesoCobroPedido(String pCorrelativo, String pTipoTmp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCorrelativo);
        parametros.add(pTipoTmp);
        log.debug("Parametros para grabar Tiempos INI-FIN :" + parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null, "Farma_Gral.CAJ_REGISTRA_TMP_INI_FIN_COBRO(?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se actualiza campo fech impresion ANULACION
     * @AUTHOR JCORTEZ
     * @SINCE 17.07.09
     * */
    public static void actualizaFechaImpr(String pNumPedVta, String PNumCompPago, String Ind) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(PNumCompPago);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(Ind);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_P_ACT_COMP_ANUL(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Actualiza datos delivery (Aun por confirmar datos)
     * @author JCORTEZ
     * @since 07.08.09
     */
    public static void actualizaDatosDelivery(String pNumPedVta, String pEstPedVta, String CodCli, String nombreCli,
                                              String TelCli, String DirCli, String NroDocCli) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pEstPedVta);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(CodCli);
        parametros.add(nombreCli);
        parametros.add(TelCli);
        parametros.add(DirCli);
        parametros.add(NroDocCli);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_DEL_CLI.CAJ_P_UP_DATOS_DELIVERY(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene los datos par tipo de pedido Delivery
     * @author JCORTEZ
     * @since 07.08.09
     */
    public static String obtieneDatosDeliveryLocal(String pNumPedVta, String pIpServ) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIpServ);
        log.debug("invocando a PTOVENTA_DEL_CLI.IMP_DATOS_DELIVERY_LOCAL(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.IMP_DATOS_DELIVERY_L(?,?,?,?)",
                                                           parametros);
    }


    /**
     * Se obtiene y elimina productos regalo por encarte
     * @author JCORTEZ
     * @since 13.08.09
     */
    public static void eliminaProdRegalo(String pNumPed, String pAccion,
                                         String pIndEliminaRespaldo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pNumPed.trim());
        parametros.add(pAccion);
        parametros.add(pIndEliminaRespaldo.trim());
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_P_UPDATE_ELIMINA_REGALO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CA_CLIENTE.CA_P_UPDATE_ELIMINA_REGALO(?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se obtiene datos de cupona regalo para imprimir.
     * @return cadena a imprimir
     * @AUGTHOR JCORTEZ
     * @SINCE 18.08.09
     */
    public static String obtieneImprCuponRegalo(String pIpServ, String pCodCupon, String Dni) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pIpServ);
        parametros.add(pCodCupon.trim());
        parametros.add(Dni.trim());
        log.debug("invocando a PTOVENTA_IMP_CUPON.IMP_PROCESA_CUPON_REGALO(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_IMP_CUPON.IMP_PROCESA_CUPON_REGALO(?,?,?,?,?)",
                                                           parametros);
    }

    public static String obtieneConvenioCliAnula(String numPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedVta);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando PTOVENTA_CAJ_ANUL.CAJ_F_OBT_DAT_CONV_ANUL(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.CAJ_F_OBT_DAT_CONV_ANUL(?,?,?,?)",
                                                           parametros);

    }

    /**
     * Se obtiene mensaje de ahorro si es fidelizado o no
     * @return mensaje de ahorro
     * @AUGTHOR JCORTEZ
     * @SINCE 03.09.09
     */
    public static String obtieneMensajeAhorro(String indFid) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(indFid);
        log.debug("invocando a PTOVENTA_VTA.OBTIENE_MENSAJE_AHORRO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.OBTIENE_MENSAJE_AHORRO(?,?,?)", parametros);
    }

    /**
     * Obtiene el mensaje de central delivery.
     * @author ERIOS
     * @since 12.09.2013
     * @return
     * @throws SQLException
     */
    public static String obtieneMensajeTicket() throws SQLException {
        ArrayList vParameters = new ArrayList();
        vParameters.add(FarmaVariables.vCodGrupoCia);
        vParameters.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_GRAL.VTA_F_GET_MENS_TICKET(?,?)", vParameters);
    }

    /**
     * Se valida si existen guis pendientes por confirmar hacia almacen
     * @AUTHOR JCORTEZ
     * @SINCE  27.10.09
     * */
    public static String ExistsGuiasPendAlmc() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_EXIST_GUIAS_PEND(?,?)", parametros);
    }

    /**
     * Se valida Se ha superado un monto minimo para generar sober
     * @AUTHOR JCORTEZ
     * @SINCE  03.10.09
     * */
    public static String permiteIngreSobre(String SecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecMovCaja);
        log.debug("PARAMETROS: " + parametros); //ASOSA, 03.06.2010
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_PERMITE_INGRESO_SOBRE(?,?,?)",
                                                           parametros);
    }

    /**
     * Se elimina el sobre fisicamente del temporal, aun no registrado
     * @AUTHOR JCORTEZ
     * @SINCE  03.11.09
     * */
    public static void eliminaSobreRegistrado(String SecMov, String CodForm, String TipMon,
                                              String Sec) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecMov.trim());
        parametros.add(CodForm.trim());
        parametros.add(TipMon.trim());
        parametros.add(Sec.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.debug("Elimina Sobre:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_P_ELIMINA_SOBRE(?,?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se agregan sobres temporalmente
     * @AUTHOR JCORTEZ
     * @SINCE  03.11.09
     * */
    public static String agregaSobre(String pSecMovCaja, String pCodFormaPago, String pTipMoneda, String pMontEntrega,
                                     String pMontEntregaTot) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pCodFormaPago);
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntrega)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntregaTot)));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("Agrega Forma Pago:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_GRABA_SOBRE(?,?,?,?,?,?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Se lista los sobres registrados
     * @author JCORTEZ
     * @since 04.11.09
     * */
    public static void getListaSobres(FarmaTableModel pTableModel, String SecMovCaj) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecMovCaj);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_F_CUR_SOBRES(?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se lista los sobres registrados para formas pago entrega
     * @author JCORTEZ
     * @since 04.11.09
     * */
    public static void getListaSobresEntrega(FarmaTableModel pTableModel, String SecMovCaj) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecMovCaj);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CAJ.CAJ_F_CUR_SOBRES_ENTREGA(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene lista sobres entrega
     * @author JCORTEZ
     * @since 04.11.09
     * */
    public static void getListaSobresEntrega(ArrayList pArrayList, String SecMovCaj) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecMovCaj);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_F_CUR_SOBRES_ENTREGA(?,?,?)");
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CAJ.CAJ_F_CUR_SOBRES_ENTREGA(?,?,?)",
                                                          parametros);
    }

    /**
     * Se obtiene venta del turno
     * @author JCORTEZ
     * @since 04.11.09
     * */
    public static String getMontoVentas(String SecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecMovCaja);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_F_GET_MONTOVENTAS():" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_GET_MONTOVENTAS(?,?,?)", parametros);
    }

    /**
     * Se envia correo del sobre declarado
     * @AUTHOR JCORTEZ
     * @SINCE  04.11.09
     * */
    public static void enviaCorreoSobre(String codSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(codSobre.trim());
        log.debug("envia correo sobre creado:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.CAJ_P_ENVIA_SOBRE(?,?,?,?)", parametros, false);
    }

    /**
     * Se valida si existen guis pendientes por confirmar hacia local
     * @AUTHOR JMIRANDA
     * @SINCE  15.12.09
     * */
    public static String ExisteGuiasXConfirmarLocal() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_CE.CE_EXIST_GUIAS_PEND_LOCAL(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_EXIST_GUIAS_PEND_LOCAL(?,?)", parametros);
    }

    /**
     * Busca el Punto de Partida y Llegada para la venta institucional
     * @AUTHOR JMIRANDA
     * @SINCE  15.12.09
     * */
    public static String getPuntoPartidaLlegada() throws SQLException {
        String mens = "";
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesVentas.vNum_Ped_Vta);
        log.debug("invocando a PTOVENTA_DEL_CLI.CLI_F_VAR_PARTIDA_LLEGADA(?,?,?):" + parametros);
        mens =
FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_DEL_CLI.CLI_F_VAR_PARTIDA_LLEGADA(?,?,?)", parametros);
        log.error("mens PuntoPartidaLlegada: " + mens);
        return mens;
    }

    /**
     * Se obtiene los datos de convenio
     * @AUTHOR JCORTEZ
     * @SINCE 07.03.10
     */
    public static String obtieneDatosConvenio(String pNumPedVta, String CodConvenio, String CodCli,
                                              String pIpServ) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(CodConvenio);
        parametros.add(CodCli);
        parametros.add(pIpServ);
        log.debug("invocando a PTOVENTA_CONV.IMP_DATOS_CONVENIO(?,?,?,?,?,?):" + parametros);
        log.debug("invocando a PTOVENTA_CONV.IMP_DATOS_CONVENIO(?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV.IMP_DATOS_CONVENIO(?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Se VALIDA Pedido Fidelizado y Canje
     * @author:  JCORTEZ
     * @since: 18.03.2010
     * */
    public static void validarPedidoFidelizado(String numPedVta, String Dni, String IndLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(numPedVta);
        parametros.add(Dni);
        parametros.add(IndLocal);
        log.debug("invocando a PTOVENTA_CAJ_ANUL.CAJ_ANULA_VAL_FIDELIZADO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ_ANUL.CAJ_ANULA_VAL_FIDELIZADO(?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se valida si se permite el ingreo de uno o mas sobres
     * @AUTHOR JCORTEZ
     * @SINCE  28.03.2010
     * */
    public static String permiteIngreMasSobre() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_PERMITE_MAS_SOBRES(?,?)", parametros);
    }

    /**
     * Se lista los sobres pendientes por aprobar
     * @author JCORTEZ
     * @since 08.04.2010
     * */
    public static void getListaSobresPorAprobar(FarmaTableModel pTableModel, String FecIni,
                                                String fecFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FecIni);
        parametros.add(fecFin);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_GET_SOBRES_APROBAR(?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se valida el estado del sobre antes de aprobarlo
     * @author JCORTEZ
     * @since 08.04.2010
     * */
    public static void validaEstadosobre(String FecVta, String cCodSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FecVta);
        parametros.add(cCodSobre);
        log.debug("VALIDA ESTADO DEL SOBRE");
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_SEGURIDAD.SEG_P_VALIDA_EST_SOBRE(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se aprueba el sobre seleccionado
     * @author JCORTEZ
     * @since 08.04.2010
     * */
    public static void aprobarSobre(String FecVta, String CodSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FecVta);
        parametros.add(CodSobre);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("APROBAR SOBRE........." + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_SEGURIDAD.SEG_P_VALIDA_APRUEBA_SOBRE(?,?,?,?,?,?)",
                                                 parametros, false);
        log.debug("APROBACION EXITOSA...............................");
    }


    /**
     * Se valida rol de usuario
     * @author JCORTEZ
     * @since 08.04.2010
     */
    public static String verificaRolUsuario(String SecUsu, String CodRol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRol);
        log.debug("verifica que el usuario tenga el rol adecuado: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_VERIFICA_ROL_USU(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Valida indicador de control de sobres
     * @AUTHOR JCORTEZ
     * @SINCE 09.04.2010
     * */
    public static boolean obtieneIndicadorControlSobres() throws SQLException {
        String pResultado = "";
        ArrayList parametros = new ArrayList(); //rherrera 12.09.2014 se crea la variable local
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_F_PERMTE_CONTROL_SOBRE(?,?):" + parametros);
        pResultado =
                FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_PERMTE_CONTROL_SOBRE(?,?)", parametros);
        log.debug("pResultado-->" + pResultado);
        if (pResultado.equalsIgnoreCase("S")) {
            return true;
        }
        return false;
    }

    /**
     * Valida indicador Prosegur
     * @AUTHOR JCORTEZ
     * @SINCE 09.04.2010
     * */
    public static boolean obtieneIndicadorProsegur() throws SQLException {
        String pResultado = "";
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_CAJ.CAJ_F_PERMTE_CONTROL_PROSEGUR(?,?):" + parametros);
        pResultado =
                FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_PERMTE_CONTROL_PROSEGUR(?,?)", parametros);
        log.debug("pResultado-->" + pResultado);
        if (pResultado.equalsIgnoreCase("S")) {
            return true;
        }
        return false;
    }

    /**
     * @author Dubilluz
     * @since  07.06.2010
     * @param pSecMovCaja
     * @param pCodFormaPago
     * @param pTipMoneda
     * @param pMontEntrega
     * @param pMontEntregaTot
     * @return
     * @throws SQLException
     */
    public static String getRealizaAccionSobreTMP(String pTipoAccion, String pSec, String pCodigoSobre,
                                                  String pSecMovCaja, String pCodFormaPago, String pTipMoneda,
                                                  String pMontEntrega, String pMontEntregaTot) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSec);
        parametros.add(pSecMovCaja);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodigoSobre);
        parametros.add(pCodFormaPago);
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntrega)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntregaTot)));
        parametros.add(pTipoAccion);
        log.debug("PTOVENTA_CE_SEGURIDAD.SEG_F_ACCION_SOBRE_TMP(?,?,?,?,?,?,?,?,?,?,?):" + parametros);

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_ACCION_SOBRE_TMP(?,?,?,?,?," +
                                                           "?,?,?,?,?,?)", parametros);
    }

    public static String getEstadoBloqueo(String pTipoAccion, String pSec, String pCodigoSobre,
                                          String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSec);
        parametros.add(pSecMovCaja);
        parametros.add(pCodigoSobre);
        parametros.add(pTipoAccion);
        log.debug("PTOVENTA_CE_SEGURIDAD.SEG_F_BLOQUEO_ESTADO(?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_BLOQUEO_ESTADO(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String getSecMovCaja() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("PTOVENTA_CE_SEGURIDAD.CAJ_F_OBTIENE_SEC_MOV_CAJA(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.CAJ_F_OBTIENE_SEC_MOV_CAJA(?,?,?)",
                                                           parametros);
    }

    public static double getTipoCambioDU() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("PTOVENTA_CE_SEGURIDAD.SEG_F_GET_TIPO_CAMBIO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureDouble("PTOVENTA_CE_SEGURIDAD.SEG_F_GET_TIPO_CAMBIO(?,?,?)",
                                                              parametros);
    }

    /**
     * Anula los pedidos pendientes despues de X minutos
     * @author ASOSA
     * @since 12.07.2010
     * @throws SQLException
     */
    public static void anulaPedidosPendientesMasivo_02() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vMinutosPedidosPendientes);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("parametros anulaPedidosPendientesMasivo_02 PTOVTA_RESPALDO_STK.CAJ_ANULA_PED_PEND_MASIVO(?,?,?,?): " +
                  parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVTA_RESPALDO_STK.CAJ_ANULA_PED_PEND_MASIVO(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author ASOSA
     * @since  26.07.2010
     * @param pSecMovCaja
     * @param pCodFormaPago
     * @param pTipMoneda
     * @param pMontEntrega
     * @param pMontEntregaTot
     * @return
     * @throws SQLException
     */
    public static String getRealizaAccionSobreTMP_02(String pTipoAccion, String pSec, String pCodigoSobre,
                                                     String pSecMovCaja, String pCodFormaPago, String pTipMoneda,
                                                     String pMontEntrega, String pMontEntregaTot,
                                                     String pSecUsuQf) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSec);
        parametros.add(pSecMovCaja);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodigoSobre);
        parametros.add(pCodFormaPago);
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntrega)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntregaTot)));
        parametros.add(pTipoAccion);
        parametros.add(pSecUsuQf.trim());
        log.debug("PTOVENTA_CE_SOBRES.SEG_F_ACCION_SOBRE_TMP(?,?,?,?,?,?,?,?,?,?,?,?):" + parametros);

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SOBRES.SEG_F_ACCION_SOBRE_TMP(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }


    /**
     * Anula Pedido Pendiente.
     * @author ASOSA
     * @since 13.07.2010
     * @param pNumPed
     * @throws SQLException
     */
    public static void anularPedidoPendiente_02(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando PTOVTA_RESPALDO_STK.CAJ_DELIVERY_GENERA_PENDIENTE(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVTA_RESPALDO_STK.CAJ_DELIVERY_GENERA_PENDIENTE(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene y elimina productos regalo por encarte
     * @author ASOSA
     * @since 13.07.2010
     * @param pNumPed
     * @param pAccion
     * @param pIndEliminaRespaldo
     * @throws SQLException
     */
    public static void eliminaProdRegalo_02(String pNumPed, String pAccion,
                                            String pIndEliminaRespaldo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pNumPed.trim());
        parametros.add(pAccion);
        parametros.add(pIndEliminaRespaldo.trim());
        log.debug("invocando a PTOVTA_RESPALDO_STK.CA_P_UPDATE_ELIMINA_REGALO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVTA_RESPALDO_STK.CA_P_UPDATE_ELIMINA_REGALO(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Anula los regalos de campaña acumulada y automatico
     * @author ASOSA
     * @since 13.07.2010
     * @param pNumPed
     * @throws SQLException
     */
    public static void anularPedidoPendienteSinRespaldo_02(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVTA_RESPALDO_STK.CAJ_P_ANULA_PED_SIN_RESPALDO(?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVTA_RESPALDO_STK.CAJ_P_ANULA_PED_SIN_RESPALDO(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Imprime stickers
     * @author JQUISPE
     * @since 28.12.2010
     * @param pCodCupon
     * @throws SQLException
     */
    public static String pruebaImpresoraTermStick(String pCodCupon, int r_inicio, int r_fin) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega parametro COD_CIA.
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(pCodCupon.trim());
        parametros.add(String.valueOf(r_inicio));
        parametros.add(String.valueOf(r_fin));
        parametros.add(FarmaVariables.vCodCia);
        log.debug("Farma_Gral.F_IMPR_TERMICA_STICK(?,?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_IMPR_TERMICA_STICK(?,?,?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Valida si Permite cobrar por stock negativo
     * dubilluz 22.06.2011
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String getPermiteCobrarPedido(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVTA_RESPALDO_STK.F_EXISTE_STOCK_PEDIDO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVTA_RESPALDO_STK.F_EXISTE_STOCK_PEDIDO(?,?,?)",
                                                           parametros);
    }
    
    /**
     * MENSAJE DE FIDEICOMIZO PARA EL CASO DE AMAZONIA
     * 
     * @author KMONCADA
     * @since 24.07.2015
     * @param isObligado
     * @return
     * @throws SQLException
     */
    public static String getMensajeFideicomizo(boolean isObligado) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        if(isObligado){
            parametros.add("S");
        }else{
            parametros.add("N");
        }
        log.debug("PTOVENTA_MENSAJES.GET_MSG_FIDEICOMIZO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MENSAJES.GET_MSG_FIDEICOMIZO(?,?,?)", parametros);
    }
    //dubilluz 2011.09.16
    public static String getMensajeFideicomizo() throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_MENSAJES.GET_MSG_FIDEICOMIZO(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MENSAJES.GET_MSG_FIDEICOMIZO(?,?)", parametros);
    }

    public static void anularPedido_BTL_MF(String numPedVta, String tipComp, String numComp, String monto,
                                           String numCajaPago, String motivoAnulacion,
                                           String vValidarMin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedVta);
        parametros.add(tipComp);
        parametros.add(numComp);
        parametros.add(new Double(monto));
        parametros.add(numCajaPago);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesCaja.vIndAnulacionConReclamoNavsat ? FarmaConstants.INDICADOR_S :
                       FarmaConstants.INDICADOR_N);
        parametros.add(motivoAnulacion);
        parametros.add(vValidarMin); //add jcallo
        log.debug("invocando PTOVENTA_CAJ_ANUL.CAJ_ANULAR_PEDIDO_BTL_MF(?,?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CAJ_ANUL.CAJ_ANULAR_PEDIDO_BTL_MF(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    public static String getIndPedConvMFBTL(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        log.debug("PTOVENTA_CAJ.CAJ_F_IS_PED_CONV_MF_BTL(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_IS_PED_CONV_MF_BTL(?,?,?)", parametros);
    }

    /**
     * Inserta en las formas de pago con montos, incluye datos de tarjeta y es correcion de grabaFormaPagoPedido
     * @author Luigy Terrazos
     * @since 20/03/2013
     * @param Codigo de forma de pago - codFormPago
     * @throws -
     */
    public static void grabaFormaPagoPedido(String pCodFormaPago, String pImPago, String pTipMoneda,
                                            String pTipoCambio, String pVuelto, String pImTotalPago, String pNumTarj,
                                            String pFecVencTarj, String pNomCliTarj, String pCantCupon,
                                            String pDNITarj, String pCodAutori, String pCodLote,
                                            String pNumPedVtaNCR) throws SQLException {
        //ERIOS 21.01.2014 Se deshabilita la fecha de vencimiento. No todas las tarjetas tienen el mismo estandar.
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodFormaPago);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImPago))); //
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pTipoCambio)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pVuelto)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImTotalPago))); //se pagacon esta cantdad
        parametros.add(pNumTarj);
        parametros.add(""); //pFecVencTarj
        parametros.add(pNomCliTarj);
        parametros.add(new Integer(pCantCupon));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pDNITarj);
        parametros.add(pCodAutori);
        parametros.add(pCodLote);
        parametros.add(pNumPedVtaNCR);
        log.debug("PTOVENTA_CAJ.CAJ_GRAB_NEW_FORM_PAGO_PEDIDO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CAJ.CAJ_GRAB_NEW_FORM_PAGO_PEDIDO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Guarda el historial de las formas de pago para ser actualizadas
     * @author Luigy Terrazos
     * @since 26.03.2013
     * @param Numero de pedido - strNumPedido
     * @throws -
     */
    public static void saveHistFormPago(String pNumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_CAJ.SAVE_HIST_FORM_PAGO(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.SAVE_HIST_FORM_PAGO(?,?,?,?)", parametros, false);
    }

    /**
     * Eliina las formas de pago para ser actualizadas
     * @author Luigy Terrazos
     * @since 26.03.2013
     * @param Numero de pedido - strNumPedido
     * @throws -
     */
    public static void elimiFormaPagoPedido(String pNumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedido);
        log.debug("PTOVENTA_CAJ.DEL_FORM_PAGO_PED(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.DEL_FORM_PAGO_PED(?,?,?)", parametros, false);
    }

    /**
     * Imprime stickers
     * @author CVILCA
     * @since 20.09.2013
     * @param lista - Lista que almacenaro los registros retornados con los comandos para crecion de las etiquetas.
     * @param vCodigos - Codigos de los productos a imprimirse separados por comas.
     * @throws SQLException
     */
    public static void imprimirStickerPorLote(ArrayList lista, String vCodigos) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vCodigos);
        log.debug("Farma_Gral.F_IMPR_TERMICA_STICKER_PROD(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lista, "Farma_Gral.F_IMPR_TERMICA_STICKER_PROD(?,?,?)",
                                                          parametros);
    }

    /**
     * Valida montos de formas de pago
     * @author ERIOS
     * @since 18.10.2013
     * @param pNumPed
     * @return
     * @throws SQLException
     */
    public static String verificaPedidoFormasPago(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        log.debug("PTOVENTA_CAJ.CAJ_F_VERIFICA_PED_FOR_PAG(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.CAJ_F_VERIFICA_PED_FOR_PAG(?,?,?)",
                                                           parametros);
    }

    /**
     * Valida montos de formas de pago
     * @author DUBILLUZ
     * @since 09.05.2014
     * @param pNumPed
     * @return
     * @throws SQLException
     */
    public static String verificaPagoUsoCampana(String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        log.debug("PTOVENTA_FIDELIZACION.FID_F_VALIDA_COBRO_PEDIDO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_VALIDA_COBRO_PEDIDO(?,?,?)",
                                                           parametros);
    }

    /**
     * Se obtiene listado de ETV totales en la empresa
     * @author LLEIVA
     * @since 27.Ene.2014
     * */
    public static void getListaETV(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_CE.CE_F_GET_LISTA_ETV");
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CE.CE_F_GET_LISTA_ETV", parametros);
    }

    /**
     * Obtiene la fecha actual del sistema en formato DD/MM/YYYY
     * @author LLEIVA
     * @since 07.Feb.2014
     * @throws SQLException
     */
    public static String fechaActualDDMMYYYY() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a FARMA_UTILITY.GET_FECHA_ACTUAL:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.GET_FECHA_ACTUAL", parametros);
    }

    /**
     * Obtiene el secuencial de impresion de boleta y de guia
     * @author CHUANES
     * @since 15.04.2014
     * @throws SQLException
     */
    public static String getSecImprBoletaGuia(String tipComp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(tipComp);
        parametros.add(FarmaVariables.vIpPc);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.GET_SEC_IMPR_GUIA_BOLETA(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene mensaje a imprimir en voucher de confirmacion de numero de recarga para el cliente
     * @author ASOSA
     * @since 03/07/2014
     * @param descProducto
     * @param numTelefonia
     * @param monto
     * @return
     * @throws SQLException
     */
    public static List imprVouVerifRecarga(String descProducto, String numTelefonia,
                                           String monto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(descProducto);
        parametros.add(numTelefonia);
        parametros.add(monto);
        log.debug("Farma_Gral.F_IMPR_VOU_VERIF_RECARGA(?,?,?,?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("Farma_Gral.F_IMPR_VOU_VERIF_RECARGA(?,?,?,?,?,?,?)",
                                                               parametros);
    }
    
        
    /**
     * Imprimir voucher de verificacion de recarga
     * @author ASOSA
     * @since 12/01/2015
     * @kind RIMAC
     * @param descProducto
     * @param numDni
     * @param cantMeses
     * @param monto
     * @return
     * @throws SQLException
     */
    public static List imprVouVerifRimac(String descProducto, 
                                           String numDni,
                                           String cantMeses,
                                           String monto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(descProducto);
        parametros.add(numDni);
        parametros.add(cantMeses);
        parametros.add(monto);
        log.debug("PTOVENTA_RIMAC.F_IMPR_VOU_VERIF_RIMAC(?,?,?,?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_RIMAC.F_IMPR_VOU_VERIF_RIMAC(?,?,?,?,?,?,?,?)",
                                                               parametros);
    }

    public static void grabaProformaDelivery(String pNumPed) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(VariablesDelivery.vDptosCodigo);
        parametros.add(VariablesDelivery.vProviCodigo);
        parametros.add(VariablesDelivery.vDistCodigo);
        parametros.add(VariablesDelivery.vUrbanizacion);
        log.debug("ptoventa_del_cli.p_crea_proforma(?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "ptoventa_del_cli.p_crea_proforma(?,?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void updMotorizadoDLV(String pNumPed, String pCodMoto, String pDescripMoto, String pObservPedVta,
                                        String pNomCliDel, String pDirCliDel, String pNumtel,
                                        String pObsePed) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPed.trim());
        parametros.add(pCodMoto.trim());
        parametros.add(pDescripMoto.trim());
        parametros.add(pObservPedVta);
        parametros.add(pNomCliDel);
        parametros.add(pDirCliDel);
        parametros.add(pNumtel);
        parametros.add(pObsePed);
        log.debug("ptoventa_del_cli.P_UPD_MOTORIZADO(?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "ptoventa_del_cli.P_UPD_MOTORIZADO(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Obtiene la cantidad de recargas vitruales que tiene un pedido
     * @author  ASOSA
     * @since   08/07/2014
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static int obtieneCantRecVirtualesPedido(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.CAJ_VERIFICA_REC_VIRTUAL(?,?,?)", parametros);
    }

    /**
     *  Obtiene los numeros de comprobantes de pago del pedido
     *  @author kmoncada 09.07.20014
     *  @return arraylist de serie y numero de comp_pago
     *  @throws SQLException
     */
    public static ArrayList obtieneComprobantePedido() throws SQLException {
        ArrayList comprobantes = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(comprobantes,
                                                          "PTOVENTA_CAJ.CAJ_NUM_COMP_PAGO_IMPR_PEDIDO(?,?,?)",
                                                          parametros);
        return comprobantes;
    }


    public static List obtieneDatosDeliveryComanda(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("invocando a PTOVENTA_DEL_CLI.IMP_DATOS_DELIVERY_COMANDA(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_DEL_CLI.IMP_DATOS_DELIVERY_COMANDA(?,?,?)",
                                                               parametros);
    }


    public static String obtieneDescripcionComprobante(String pTipoComprobante) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pTipoComprobante);
        log.debug("parametros anulaPedidosPendientesMasivo: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.GET_DESC_COMPROBANTE(?,?)", parametros);
    }

    /**
     *  Obtiene los comprobantes del pedido
     *  @author LTAVARA 25.08.2014
     *  @return arraylist de VTA_COMP_PAGO
     *  @throws SQLException
     */
    public static void obtieneCompPagos(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        log.debug("parametros PTOVENTA_CAJ.CAJ_F_CUR_LISTA_COMP_PAGO(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CAJ.CAJ_F_CUR_LISTA_COMP_PAGO (?,?,?)",
                                                          parametros);
    }

    public static boolean isConvenioMixto() throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        
        String resultado =
            FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.IS_CONVENIO_MIXTO(?,?,?)", parametros);
        if ("1".equalsIgnoreCase(resultado)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Metodo que valida en el cobro si existe la cantidad suficiente de componentes para el pedido de producto final.
     * @author ASOSA
     * @since 06/10/2014
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String getPermiteCobrarPedProdFinal(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVTA_RESPALDO_STK.F_EXISTE_STK_PED_PROD_FINAL(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVTA_RESPALDO_STK.F_EXISTE_STK_PED_PROD_FINAL(?,?,?)",
                                                           parametros);
    }

    /**
     * Dubilluz 14.10.2014
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String getIndIngresoCompManual(String pNumPedVta) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega parametro COD_CIA.
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVENTA_CAJ.IND_GET_PED_ING_MANUAL(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.IND_GET_PED_ING_MANUAL(?,?,?)", parametros);
    }

    public static String getCadenaCompManual(String pNumPedVta) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega parametro COD_CIA.
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVENTA_CAJ.IND_GET_CADENA_MANUAL(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.IND_GET_CADENA_MANUAL(?,?,?)", parametros);
    }
    
    public static String getValidaCompManual(String pTip, String pSerie, String pNumComp) throws SQLException {
        return getValidaCompManual(pTip, pSerie, pNumComp, null);
    }
    
    public static String getValidaCompManual(String pTip, String pSerie, String pNumComp, String nroPedVta) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega parametro COD_CIA.
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTip);
        parametros.add(pSerie);
        parametros.add(pNumComp);
        if(nroPedVta != null && nroPedVta.trim().length()>0){
            parametros.add(nroPedVta.trim());
        }else{
            parametros.add(" ");
        }
        log.debug("PTOVENTA_CAJ.IND_VALIDA_COMP_MANUAL(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.IND_VALIDA_COMP_MANUAL(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static boolean isExisteComprobanteElectronico(String pCodGrupoCia, String pCodLocal,
                                                         String pTipoComprobante,
                                                         String pNumComprobante) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pTipoComprobante);
        parametros.add(pNumComprobante);
        
        int resultado =
            FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.FN_EXISTE_COMPROBANTE_E(?,?,?,?)", parametros);
        if (resultado == 0)
            return false;
        else
            return true;
    }

    public static String getIndExisteCompElectronico() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("farma_utility.getemitiocompelectronico(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("farma_utility.getemitiocompelectronico(?,?)", parametros);
    }

    public static void actualizaFechaImpresion(String pSecCompPago, String pTipCompPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        parametros.add(pTipCompPago);
        parametros.add(pSecCompPago);
        parametros.add(FarmaVariables.vIdUsu);
        
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.P_ACT_FCH_IMPRESION(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     *  Obtiene los detallde del pedido Guia
     *  @author Rherrera 18.11.2014
     *  @return arraylist de VTA_PEDIDO_VTA_CAB
     *  @throws SQLException
     */
    public static void obtieneCompPagos_gui(ArrayList pArrayList) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vNumPedVta);
        log.debug("parametros PTOVENTA_CAJ.CAJ_F_CUR_LISTA_COMP_PAGO_GUI(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_F_CUR_LISTA_COMP_PAGO_GUI (?,?,?)",
                                                          parametros);
    }

    public static String getNombreFileComprobante(String pCodGrupoCia, String pCodLocal, String pNumPedVta,
                                                  String pSecCompPago) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pSecCompPago);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.GET_NOMBRE_FILE_COMP_ELEC(?,?,?,?)",
                                                           parametros);
    }
    
    /**
     * Listar pedido con producto rimac.
     * @author ASOSA
     * @since 08/01/2015
     * @kind RIMAC
     * @param pArrayList
     * @param pNumPedVenta
     * @throws SQLException
     */
    public static void getDetaVentaRimac(ArrayList pArrayList, 
                                         String pNumPedVenta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        //parametros.add(pNumPedVenta);
        log.debug("invoca PTOVENTA_RIMAC.LIST_PEDIDO_RIMAC_COBRADO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_RIMAC.LIST_PEDIDO_RIMAC_COBRADO(?,?,?)",
                                                          parametros);
    }
    
    /**
     * Insertar pedido con un producto RIMAC.
     * @author ASOSA
     * @since 08/01/2015
     * @kind RIMAC
     * @param numPedVta
     * @param numCompPago
     * @param codProd
     * @param cantMeses
     * @param valPrecVta
     * @param valPrecTotal
     * @param dniRimac
     * @param fecPedVta
     * @param pIndCloseConecction
     * @return
     * @throws SQLException
     */
    public static String insertVentaRimacMatriz(PedVtaRimacDTO pedVtaRimacDTO,
                                                String pIndCloseConecction
                                                ) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pedVtaRimacDTO.getNumPedVta());
        parametros.add(pedVtaRimacDTO.getNumCompPago());
        parametros.add(pedVtaRimacDTO.getCodProd());
        parametros.add(pedVtaRimacDTO.getCantMeses());
        parametros.add(pedVtaRimacDTO.getValPrecVta());
        parametros.add(pedVtaRimacDTO.getValPrecTotal());
        parametros.add(pedVtaRimacDTO.getDniRimac());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pedVtaRimacDTO.getFecPedVta());
        log.debug("invocando a PTOVENTA_RIMAC_MATRIZ.INS_VTA_PED_RIMAC(?,?,?,?,?,?):" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStrOut("PTOVENTA_RIMAC_MATRIZ.INS_VTA_PED_RIMAC(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                    parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                    pIndCloseConecction);
    }
    
    
       /**
     * dterminar si el producto rimac fue activado.
     * @author ASOSA
     * @since 11/01/2015
     * @kind RIMAC
     
     */ 
    public static String getInd_prod_rimac_activado(String pNumPedVta,
                                                    String pIndCloseConecction) throws SQLException {
       
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVENTA.PTOVENTA_RIMAC_MATRIZ.GET_IND_EXISTE_PEDIDO_ACT(?,?,?,?):" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA.PTOVENTA_RIMAC_MATRIZ.GET_IND_EXISTE_PEDIDO_ACT(?,?,?,?)",    
                                                                parametros, 
                                                                FarmaConstants.CONECTION_RAC,
                                                                pIndCloseConecction);
    }
       
    /**
    * dterminar si el pedido tiene un producto rimac.
    * @author ASOSA
    * @since 11/01/2015
    * @kind RIMAC
    
    */
    public static String getInd_prod_rimac_in_ped(String pNumPedVta) throws SQLException {
    
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pNumPedVta);
     log.debug("PTOVENTA_RIMAC.GET_IND_EXISTE_rimac_ped(?,?,?,?):" + parametros);
     return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RIMAC.GET_IND_EXISTE_rimac_ped(?,?,?,?)",    
                                                             parametros);
    }
    
    /** 
     * Insertar producto rimac como regalo
     * @author ASOSA
     * @since 15/01/2015
     * @kind RIMAC
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String insProdRegaloRimac(String pNumPedVta) throws SQLException {
    
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVENTA_RIMAC.INS_PROD_RIMAC_REGALO(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RIMAC.INS_PROD_RIMAC_REGALO(?,?,?,?)",    
                                                             parametros);
    }
    
    /**
     * @author ASOSA
     * @since 19/01/2015
     * @kind RIMAC
     * @param codProd
     * @return
     * @throws SQLException
     */
    public static String getStkProdRegalo(String codProd) throws SQLException {
    
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(codProd);
     log.debug("PTOVENTA_RIMAC.GET_STOCK_PROD_REGALO(?,?,?,?):" + parametros);
     return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RIMAC.GET_STOCK_PROD_REGALO(?,?,?,?)",    
                                                             parametros);
    }
    
    public static String getNumeroComprobante(String pCodGrupoCia, String pCodLocal, String pNumPedVta,
                                              String pSecCompPago) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pSecCompPago);
        
        String nroComprobante = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.GET_NRO_COMPROBANTE_PAGO(?,?,?,?)", parametros);
        if (nroComprobante != null) {
            if (nroComprobante.trim().length() == 0)
                nroComprobante = null;
        }
        return nroComprobante;
    }
    
    /**
     * Determinar si un pedido tiene varios comprobantes
     * @author ASOSA
     * @since 10/02/2015
     * @kind PTOSYAYAYAYA
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String getIndVariosCompPedido(String pNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_GET_IND_VARIOS_COMP(?,?,?)",
                                                           parametros);
    }
    
    /**
     * Devolver cursor para imprimir voucher con datos de puntos fuera del comprobante.
     * @author ASOSA
     * @since 10/02/2015
     * @kind PTOSYAYAYAYA
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static List imprVouPtosCliente(String pNumPedVta) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);        
        parametros.add(pNumPedVta);
        if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean()!=null)
            parametros.add(VariablesPuntos.frmPuntos.getTarjetaBean().getAhorroTotal());
        else
            parametros.add(0);
        //KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
        if(VariablesPuntos.frmPuntos != null){
            if(VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                parametros.add(VariablesPuntos.frmPuntos.getTarjetaBean().getDni());
            }else{
                parametros.add(" ");
            }
        }else{
            parametros.add(" ");
        }
        
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_FIDELIZACION.F_IMPR_VOU_INFO_PTOS(?,?,?,?,?,?)",
                                                               parametros);
    }
    
    /**
     *
     * Parametro para indicar si permite anulacion con cupon usado
     * @return
     * @throws SQLException
     */
    public static boolean getIndAnularConCuponUsado() throws SQLException {
        ArrayList parametros = new ArrayList();
        
        String respuesta = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ_ANUL.F_IND_ANULA_CON_CUPONES_USADOS",parametros);
        if("S".equalsIgnoreCase(respuesta)){
            return true;
        }else
            return false;
    }
    
    /**
     * Marca las NCR como usadas
     * @author ERIOS
     * @since 15.05.2015
     */
    public static void marcaUsoNCR(String pNumPedVta, String pNumPedVtaOrigen) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pNumPedVtaOrigen);
        log.debug("PTOVENTA_CAJ.MARCA_USO_NCR(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CAJ.MARCA_USO_NCR(?,?,?,?)",
                                                 parametros, false);
    }    
    
    public static List obtieneImprCupon2(String pNumPedVta, String pIpServ, String pCodCupon) throws SQLException {
        //LTERRAZOS 01.03.2013 Se agrega COD_CIA.
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pIpServ);
        parametros.add(pCodCupon.trim());
        parametros.add(FarmaVariables.vCodCia);

        log.debug("invocando a PTOVENTA_IMP_CUPON.IMP_PROCESA_CUPON_NEW(?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_IMP_CUPON.IMP_PROCESA_CUPON_NEW(?,?,?,?,?,?)",
                                                           parametros);
    }
    
    /**
     * Obtener partida arancelaria
     * @author ASOSA
     * @since 20/07/2015
     * @type IGVAZNIA
     * @param codProd
     * @return
     * @throws SQLException
     */
    public static String obtenerPartidaArancelaria(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(codProd);
        log.debug("PTOVENTA_CAJ.F_GET_PARTIDA_ARANCELARIA(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CAJ.F_GET_PARTIDA_ARANCELARIA(?,?,?,?)",
                                                           parametros);
    }
    
    public static int getCantUltimosDigitosTarjetasASolicitar(){
        int cantidad = 4;
        try{
            ArrayList parametros = new ArrayList();
            cantidad = FarmaDBUtility.executeSQLStoredProcedureInt("FARMA_GRAL.F_GET_CANT_DIGT_TARJ_SOLICITA", parametros);
        }catch(Exception ex){
            log.error("", ex);
        }
        return cantidad;
    }
    
    public static boolean nuevaVentanaDatosTarjetaPOS(){
        boolean activo = false;
        try{
            ArrayList parametros = new ArrayList();
            String indicador = FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.F_GET_NVA_VENTANA_DATO_TARJ", parametros);
            if("S".equalsIgnoreCase(indicador)){
                activo = true;
            }
        }catch(Exception ex){
            log.error("", ex);
        }
        return activo;
    }
    
    /**
     * Metodo que devuelve la cantidad de intentos que se realizara para obtener nro de comprobante manual
     * @author KMONCADA
     * @since 14.09.2015
     * @return
     */
    public static int obtieneCantidadIntentoComprobante(){
        int cantidad = 0;
        try{
            ArrayList parametros = new ArrayList();
            return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CAJ.F_GET_NRO_INTENTO_COMPROBANTE", parametros);
        }catch(Exception ex){
            cantidad = 1;
        }
        return cantidad;
    }

}