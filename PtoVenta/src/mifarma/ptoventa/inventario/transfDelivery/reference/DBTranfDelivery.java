package mifarma.ptoventa.inventario.transfDelivery.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo     : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DBTranfDelivery.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO    11.10.2008   Creación<br>
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */
public class DBTranfDelivery {
    private static final Logger log = LoggerFactory.getLogger(DBTranfDelivery.class);

    private static ArrayList parametros = new ArrayList();

    public DBTranfDelivery() {
    }


    /**
     * @autor JCALLO
     * @since 06.11.2008
     * */
    public static void cargaListaTransfDeliveryPendientes(FarmaTableModel pTableModel,
                                                          String filtro) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //parametros.add(filtro);
        log.info("PTOVENTA_TRANSF_DEL.TRANF_F_CUR_DEL_PEND(?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TRANSF_DEL.TRANF_F_CUR_DEL_PEND(?,?)",
                                                 parametros, false);
    }

    /**
     * @autor JCALLO
     * @since 06.11.2008
     * */
    public static void cargaDetalleTransfDelivery(FarmaTableModel pTableModel, String pCodLocalDel, String pNumPedido,
                                                  String pSecGrupo, String pCodLocalDest,
                                                  String pSecTrans) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocalDel);
        parametros.add(pNumPedido);
        parametros.add(new Integer(pSecGrupo));
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodLocalDest);
        parametros.add(new Integer(pSecTrans));
        log.info("INVOCANDO A PTOVENTA_TRANSF_DEL.TRANF_F_CUR_DET_PEDIDO(?,?,?,?,?,?,?) : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_TRANSF_DEL.TRANF_F_CUR_DET_PEDIDO(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @autor JCALLO
     * @since 06.11.2008
     * */
    public static void actualizarPedidoTransfDelivery(String pCodLocalDel, String pNumPedido, String pSecGrupo,
                                                      String pCodLocalDest, String pEstado,
                                                      String pSecTrans) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocalDel);
        parametros.add(pNumPedido);
        parametros.add(new Integer(pSecGrupo));
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodLocalDest);
        parametros.add(new Integer(pSecTrans));
        parametros.add(pEstado);
        parametros.add(FarmaVariables.vIdUsu);
        log.info("PTOVENTA_TRANSF_DEL.TRANF_P_UPDATE_CAB_PEDIDO(?,?,?,?,?,?,?,?,?) : " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_TRANSF_DEL.TRANF_P_UPDATE_CAB_PEDIDO(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    //DlgTransferenciaNueva

    public static String agregarCabeceraTransferencia(String tipDestino, String codDestino, String motivo,
                                                      String destino, String rucDestino, String dirDestino,
                                                      String transp, String rucTransp, String dirTransp, String placa,
                                                      String cantItems, String valTotal, String codTransInterno,
        //los 4 campos adicionales
        String pCodLocalDel, String pNumPedidoTransf, String pSecGrupo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        parametros.add(tipDestino);
        parametros.add(codDestino);
        parametros.add(motivo);
        parametros.add(destino);
        parametros.add(rucDestino);
        parametros.add(dirDestino);
        parametros.add(transp);
        parametros.add(rucTransp);
        parametros.add(dirTransp);
        parametros.add(placa);

        parametros.add(new Integer(cantItems));
        parametros.add(new Double(valTotal));

        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(codTransInterno);

        parametros.add(pCodLocalDel);
        parametros.add(pNumPedidoTransf);
        parametros.add(new Integer(pSecGrupo));

        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TRANSF_DEL.TRANSF_F_CHAR_AGREGA_CABECERA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void agregarDetalleTransferencia(String numGuia, String codProd, String valUnit, String valTotal,
                                                   String cantMov, String fecVcto, String numLote, String tipDestino,
                                                   String codDestino, String pValFrac,
                                                   String pIndFrac) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numGuia);
        parametros.add(codProd);
        parametros.add(new Double(valUnit));
        parametros.add(new Double(valTotal));
        parametros.add(new Integer(cantMov));
        parametros.add(fecVcto);
        parametros.add(numLote);
        parametros.add(VariablesInventario.vMotivo_Transf);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(new Integer(pValFrac));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(tipDestino);
        parametros.add(codDestino);
        parametros.add(pIndFrac);
        log.info("PTOVENTA_TRANSF_DEL.TRANSF_P_AGREGA_DETALLE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) parametros :" +
                 parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_TRANSF_DEL.TRANSF_P_AGREGA_DETALLE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    public static void generarGuiasTransferencia(String numGuia, int cantDetGuia,
                                                 String cantItems) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numGuia);

        parametros.add(new Integer(cantDetGuia));
        parametros.add(new Integer(cantItems));

        parametros.add(FarmaVariables.vIdUsu);
        //log.debug(parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TRANSF_DEL.TRANSF_P_GENERA_GUIA(?,?,?,?,?,?)",
                                                 parametros, false);
    }


    public static void confirmarTransferencia(String numTransf) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numTransf);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_TRANSF_DEL.TRANSF_P_CONFIRMAR(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TRANSF_DEL.TRANSF_P_CONFIRMAR(?,?,?,?)", parametros,
                                                 false);
    }

    public static String validarEstadoPedido(String pCodLocalDel, String pNumPedido, String pSecGrupo,
                                             String pCodLocalDest, String pSecTrans) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodLocalDel);
        parametros.add(pNumPedido);
        parametros.add(new Integer(pSecGrupo));
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodLocalDest);
        parametros.add(new Integer(pSecTrans));
        log.debug("PTOVENTA_TRANSF_DEL.TRANF_F_CHAR_VAL_ESTADO_PED(?,?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TRANSF_DEL.TRANF_F_CHAR_VAL_ESTADO_PED(?,?,?,?,?,?,?)",
                                                           parametros);
    }


    /**
     * Se otiene indicador del tipo fraccionamiento del producto en el local destino,conexion a matriz
     * @throws SQLException
     * @author JCHAVEZ
     * @since 25.08.2009
     */
    public static String obtieneIndFraccLocalDestino(String pCodDestino, String pCodProd, String pCantMov,
                                                     int pValFrac, String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodDestino);
        parametros.add(pCodProd);
        parametros.add(new Integer(pCantMov));
        parametros.add(new Integer(pValFrac));

        //  FarmaDBUtilityRemoto.executeSQLStoredProcedureStrOut("PTOVENTA_TRANSF.INV_P_VERI_FRAC_ENTRE_LOC(?,?,?,?,?)", parametros, false);

        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStrOut("PTOVENTA_TRANSF.GET_FRACCION_LOCAL(?,?,?,?,?,?)",
                                                                    parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                    pIndCloseConecction);
    }

    /**
     * Se otiene el fraccionamiento del producto en el local origen
     * @throws SQLException
     * @author JCHAVEZ
     * @since 25.08.2009
     */
    public static int getValFracProducto(String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        log.debug("Ptoventa_Inv.INV_F_GET_VAL_FRAC_PROD(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("Ptoventa_Inv.INV_F_GET_VAL_FRAC_PROD(?,?,?)", parametros);
    }

    /**
     * Agregar detalle de la transferencia delivery
     * @author ASOSA
     * @since 31.08.2010
     * @param numGuia
     * @param codProd
     * @param valUnit
     * @param valTotal
     * @param cantMov
     * @param fecVcto
     * @param numLote
     * @param tipDestino
     * @param codDestino
     * @param pValFrac
     * @param pIndFrac
     * @param secResp
     * @throws SQLException
     */
    public static void agregarDetalleTransferencia_02(String numGuia, String codProd, String valUnit, String valTotal,
                                                      String cantMov, String fecVcto, String numLote,
                                                      String tipDestino, String codDestino, String pValFrac,
                                                      String pIndFrac, String secResp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numGuia);
        parametros.add(codProd);
        parametros.add(new Double(valUnit));
        parametros.add(new Double(valTotal));
        parametros.add(new Integer(cantMov));
        parametros.add(fecVcto);
        parametros.add(numLote);
        parametros.add(VariablesInventario.vMotivo_Transf);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(new Integer(pValFrac));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(tipDestino);
        parametros.add(codDestino);
        parametros.add(pIndFrac);
        parametros.add(secResp);
        log.info("PTOVTA_RESPALDO_STK.TRANSF_P_AGREGA_DETALLE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) parametros :" +
                 parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVTA_RESPALDO_STK.TRANSF_P_AGREGA_DETALLE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

}
