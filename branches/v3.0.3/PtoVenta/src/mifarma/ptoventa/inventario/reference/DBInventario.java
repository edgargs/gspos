package mifarma.ptoventa.inventario.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo     : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.02.2006   Creación<br>
 * ASOSA      18.01.2010   Modificación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DBInventario {
    private static final Logger log = LoggerFactory.getLogger(DBInventario.class);
    private static ArrayList parametros = new ArrayList();


    public DBInventario() {
    }

    //DlgGuiasIngresosRecibidas

    public static void cargaListaGuiaIngresos(FarmaTableModel pTableModel, String fechaIni, String fechaFin,
                                              String filtro) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fechaIni);
        parametros.add(fechaFin);
        parametros.add(filtro);
        //log.debug(parametros);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_GUIA_INGRESO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_GUIA_INGRESO(?,?,?,?,?,?)",
                                                 parametros, false);
        // FarmaConnection.getConnection().close();
    }

    //DlgGuiaIngresoProductos

    public static void cargaListaProductosGuiaIngreso(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_PROD_GUIA_INGRESO(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_PROD_GUIA_INGRESO(?,?)",
                                                 parametros, true);
    }

    public static String getStkDisponibleProd(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        log.debug("invocando a PTOVENTA_INV.INV_GET_STK_DISP_PROD(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_GET_STK_DISP_PROD(?,?,?)", parametros);
    }
    //DlgGuiaIngresoResumen

    public static String agregarCabeceraGuiaIngreso(String fechaGuia, String tipDoc, String numDoc, String tipOrigen,
                                                    String codOrigen, String cantItems, String valTotal,
                                                    String nomTienda, String ciudadTienda,
                                                    String rucTienda) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        parametros.add(fechaGuia);
        parametros.add(tipDoc);
        parametros.add(numDoc);
        parametros.add(tipOrigen);
        parametros.add(codOrigen);
        parametros.add(new Integer(cantItems));
        parametros.add(new Double(valTotal));

        parametros.add(nomTienda);
        parametros.add(ciudadTienda);
        parametros.add(rucTienda);

        parametros.add(FarmaVariables.vIdUsu);

        log.debug("invocando a PTOVENTA_INV.INV_AGREGA_CAB_GUIA_INGRESO(?,?,?,?,?,?,?,?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_AGREGA_CAB_GUIA_INGRESO(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void agregarDetalleGuiaIngreso(String numGuia, String tipOrigen, String codProd, String valUnit,
                                                 String valTotal, String cantMov, String fecGuia, String fecVcto,
                                                 String numLote, String pValFrac) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numGuia);
        parametros.add(tipOrigen);
        parametros.add(codProd);
        parametros.add(new Double(valUnit));
        parametros.add(new Double(valTotal));
        parametros.add(new Integer(cantMov));
        parametros.add(fecGuia);
        parametros.add(fecVcto);
        parametros.add(numLote);
        parametros.add(VariablesInventario.vTipoMotivoKardex);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(new Integer(pValFrac));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_AGREGA_DET_GUIA_INGRESO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_INV.INV_AGREGA_DET_GUIA_INGRESO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaDetalleGuiaIngreso(String numNota, String numDoc, String tipNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(numDoc);
        parametros.add(tipNota);
        ArrayList array = new ArrayList();
        log.debug("invocando a PTOVENTA_INV.INV_GET_DET_GUIA_INGRESO(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_DET_GUIA_INGRESO(?,?,?,?,?)",
                                                          parametros);
        VariablesInventario.vArrayGuiaIngresoProductos = array;
    }

    public static ArrayList cargaCabeceraGuiaIngreso(String numNota, String numDoc) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(numDoc);
        ArrayList array = new ArrayList();
        log.debug("invocando a PTOVENTA_INV.INV_GET_CAB_GUIA_INGRESO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_CAB_GUIA_INGRESO(?,?,?,?)",
                                                          parametros);
        return array;
    }

    /*TRANSFERENCIAS*/
    //DlgTransferenciasRealizadas

    public static void cargaListaTransferencias(FarmaTableModel pTableModel, String filtro) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(filtro);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_TRANSFERENCIA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_TRANSFERENCIA(?,?,?,?)",
                                                 parametros, false);
    }

    //DlgTransferenciasListaProductos

    public static void cargaListaProductosTransferencia(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_PROD_TRANSFERENCIA(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_PROD_TRANSFERENCIA(?,?)",
                                                 parametros, true);
    }

    public static void obtieneInfoProducto(ArrayList pArrayList, String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        log.debug("invocando a PTOVENTA_INV.INV_GET_INFO_PROD(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_INV.INV_GET_INFO_PROD(?,?,?)",
                                                          parametros);
    }

    //DlgTransferenciaNueva

    public static String agregarCabeceraTransferencia(String tipDestino, String codDestino, String motivo,
                                                      String destino, String rucDestino, String dirDestino,
                                                      String transp, String rucTransp, String dirTransp, String placa,
                                                      String cantItems, String valTotal,
                                                      String codTransInterno) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        parametros.add(tipDestino);
        parametros.add(codDestino);
        parametros.add(motivo);
        parametros.add(destino);
        parametros.add(rucDestino);
        parametros.add(dirDestino);
        parametros.add(transp); //7
        parametros.add(rucTransp); //8
        parametros.add(dirTransp); //9
        parametros.add(placa); //10

        parametros.add(new Integer(cantItems));
        parametros.add(new Double(valTotal));

        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(codTransInterno);
        log.debug("invocando a PTOVENTA_INV.INV_AGREGA_CAB_TRANSFERENCIA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):" +
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_AGREGA_CAB_TRANSFERENCIA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void agregarDetalleTransferencia(String numGuia, String codProd, String valUnit, String valTotal,
                                                   String cantMov, String fecVcto, String numLote, String tipDestino,
                                                   String codDestino, String pValFrac, String pIndFrac,
                                                   String pMotivo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numGuia);

        parametros.add(codProd);
        parametros.add(new Double(valUnit));
        parametros.add(new Double(valTotal));
        //revisar error de framework dubilluz 27.06.20//
        //parametros.add(""+FarmaUtility.getDecimalNumber(valUnit));
        //parametros.add(""+FarmaUtility.getDecimalNumber(valTotal));
        parametros.add(new Integer(cantMov));
        parametros.add(fecVcto);
        parametros.add(numLote);
        //parametros.add(VariablesInventario.vMotivo_Transf);
        parametros.add(pMotivo.trim());
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(new Integer(pValFrac));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(tipDestino);
        parametros.add(codDestino);
        parametros.add(pIndFrac);
        log.debug("invocando a PTOVENTA_INV.INV_AGREGA_DET_TRANSFERENCIA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):" +
                  parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_INV.INV_AGREGA_DET_TRANSFERENCIA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
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
        log.debug("invocando a PTOVENTA_INV.INV_GENERA_GUIA_TRANSFERENCIA(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_GENERA_GUIA_TRANSFERENCIA(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    //DlgTransferenciasVer

    public static ArrayList cargaCabeceraTransferencia(String numNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        ArrayList array = new ArrayList();
        log.debug("invocando a PTOVENTA_INV.INV_GET_CAB_TRANSFERENCIA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_CAB_TRANSFERENCIA(?,?,?,?)",
                                                          parametros);
        return array;
    }

    public static void cargaDetalleTransferencia(FarmaTableModel pTableModel, String numNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        log.debug("invocando a PTOVENTA_INV.INV_GET_DET_TRANSFERENCIA(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_GET_DET_TRANSFERENCIA(?,?,?)",
                                                 parametros, false);
    }

    //JCHAVEZ

    public static void anularTransferenciaMatriz(String numNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(ConstantsPtoVenta.MOT_KARDEX_ANULACION_TRANSFERENCIA);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_MATRIZ_TRANSF.TRANS_P_ANULAR_TRANSFERENCIA(?,?,?,?,?,?):" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA_MATRIZ_TRANSF.TRANS_P_ANULAR_TRANSFERENCIA(?,?,?,?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_MATRIZ,
                                                       FarmaConstants.INDICADOR_N); //JCHAVEZ 27112009 nuevo modo de anular transferencia

    }

    public static void anularTransferencia(String numNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(ConstantsPtoVenta.MOT_KARDEX_ANULACION_TRANSFERENCIA);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_ANULA_TRANSFERENCIA(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_ANULA_TRANSFERENCIA(?,?,?,?,?,?)", parametros,
                                                 false); //JCHAVEZ 29122009
    }


    public static void obtieneStockProducto_ForUpdate(ArrayList pArrayList,
                                                      String codProd_Transf) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd_Transf);
        log.debug("invocando a PTOVENTA_INV.INV_GET_STK_PROD_FORUPDATE(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_INV.INV_GET_STK_PROD_FORUPDATE(?,?,?)",
                                                          parametros);
    }

    public static void actualizaStkComprometidoProd(String pCodProd, int pCantMov,
                                                    String pTipoOperacion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(new Integer(pCantMov));
        parametros.add(pTipoOperacion);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_VTA.VTA_ACTUALIZA_STK_COMPROMETIDO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_VTA.VTA_ACTUALIZA_STK_COMPROMETIDO(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaPagina() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vNumNotaEs);
        parametros.add(VariablesInventario.vNumPag);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_ACT_PAG_GUIA_RECEP(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_ACT_PAG_GUIA_RECEP(?,?,?,?,?)", parametros,
                                                 false);
    }

    /*public static void actualizaGuiaPedido() throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesInventario.vNumNotaEs);
		parametros.add(VariablesInventario.vNumPag);
		parametros.add(FarmaVariables.vIdUsu);
    log.debug(parametros);
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_INV.INV_ACT_GUIA_RECEP(?,?,?,?,?)", parametros,false);
	}*/

    /*public static void actualizaGuia() throws SQLException {
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
		parametros.add(VariablesInventario.vNumNotaEs);
		parametros.add(FarmaVariables.vIdUsu);
		FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_INV.INV_ACT_GUIA_RECEP(?,?,?,?)", parametros, false);
	}*/

    public static void getListaGuiasRecepPedido(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //parametros.add(VariablesInventario.vNumNotaEs);
        parametros.add("%");
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_GUIA_RECEP(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_GUIA_RECEP(?,?,?)", parametros,
                                                 true);
    }

    public static void getListaDetGuiasRecep(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vNumNotaEs);
        parametros.add(VariablesInventario.vNumGuia);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_DET_GUIA_RECEP(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_DET_GUIA_RECEP(?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaRegistroUnico() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vNumNotaEs);
        parametros.add(VariablesInventario.vSecDetNota);
        parametros.add(VariablesInventario.vNumPag);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_ACT_REG_GUIA_RECEP(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_ACT_REG_GUIA_RECEP(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void getListaGuiasRecep(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_GUIA_RECEP(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_GUIA_RECEP(?,?)", parametros,
                                                 false);
    }

    public static void getDatosTransporte(ArrayList array, String codDestino) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDestino);
        log.debug("invocando a PTOVENTA_INV.INV_GET_DATOS_TRANSPORTE(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_DATOS_TRANSPORTE(?,?,?)",
                                                          parametros);
    }


    public static void getDatosTransporteDelivery(ArrayList array, String codDestino) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDestino);
        log.debug("invocando a PTOVENTA_INV.INV_GET_DATOS_TRANS_DELIVERY(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_DATOS_TRANS_DELIVERY(?,?,?)",
                                                          parametros);
    }

    public static void anularGuiaIngreso(String numNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(ConstantsPtoVenta.MOT_KARDEX_ANULACION_INGRESO);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_ANULA_GUIA_INGRESO(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_ANULA_GUIA_INGRESO(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void getListaProdsAK(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //parametros.add(tipo_reposicion);    //rherrera 06.08.2014
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_ITEMS_AK(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_ITEMS_AK(?,?)", parametros,
                                                 false);
    }

    public static void getListaProdsInsum(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //parametros.add(tipo_reposicion);    //rherrera 06.08.2014
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_INSUMOS(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_INSUMOS(?,?)", parametros,
                                                 false);
    }


    public static void getListaMovsProdsAK(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vCodProd);
        parametros.add(VariablesInventario.vFecIniMovKardex);
        parametros.add(VariablesInventario.vFecFinMovKardex);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_ITEMS_AK(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_ITEMS_AK(?,?)", parametros,
                                                 false);
    }

    public static void getListaMovsKardex(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vCodProd);
        parametros.add(VariablesInventario.vFecIniMovKardex);
        parametros.add(VariablesInventario.vFecFinMovKardex);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_MOVS_KARDEX(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_MOVS_KARDEX(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void listaAjusteporFecha(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vFecIniMovKardex);
        parametros.add(VariablesInventario.vFecFinMovKardex);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_AJUSTES_KARDEX(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_AJUSTES_KARDEX(?,?,?,?)",
                                                 parametros, false);
    }


    public static void cargaListaProductosKardex_Filtro(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesPtoVenta.vTipoFiltro);
        parametros.add(VariablesPtoVenta.vCodFiltro);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_PROD_FILTRO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_PROD_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }

    public static void ingresaAjusteKardex(String pCodMotKardex, String pNeoCant, String pGlosa) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vCodProd);
        parametros.add(pCodMotKardex);
        parametros.add(pNeoCant);
        parametros.add(pGlosa);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_AJUSTE);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_INGRESA_AJUSTE_KARDEX(?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_INGRESA_AJUSTE_KARDEX(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /*PEDIDO REPOSICION*/
    //DlgPedidoReposicionReposicion

    public static void cargaListaPedidoReposicion(FarmaTableModel pTableModel, String fecIni, String fecFin,
                                                  String tipoFiltro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fecIni);
        parametros.add(fecFin);
        parametros.add(tipoFiltro);
        log.debug("invocando a PTOVENTA_REP.INV_LISTA_PEDIDO_REPOSICION(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_LISTA_PEDIDO_REPOSICION(?,?,?,?,?)",
                                                 parametros, false);
    }

    //DlgPedidoReposicionNuevo

    public static void cargaListaProductosReposicion(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_LISTA_PROD_REPOSICION(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_LISTA_PROD_REPOSICION(?,?)",
                                                 parametros, false);
    }

    public static void cargaListaProductosReposicion_filtro(FarmaTableModel pTableModel, String tipo,
                                                            String codigo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(tipo);
        parametros.add(codigo);
        log.debug("invocando a PTOVENTA_REP.INV_LISTA_PROD_REP_FILTRO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_LISTA_PROD_REP_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }

    public static void getCabeceraReposicion(ArrayList array) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_GET_CAB_REPOSICION(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_REP.INV_GET_CAB_REPOSICION(?,?)",
                                                          parametros);
    }

    public static void getUltimoPedidoReposicion(ArrayList array) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_GET_ULT_PED_REP(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_REP.INV_GET_ULT_PED_REP(?,?)", parametros);
    }

    public static void actualizaMaxMin() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_CALCULA_MAX_MIN(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.INV_CALCULA_MAX_MIN(?,?)", parametros, false);
    }

    public static String getIndPedRep() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_GET_PED_REP(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_GET_PED_REP(?,?)", parametros);
    }
    //DlgPedidoReposicionIngresoCantidad

    public static void guardarCantPedRepTemp(String codProd, String cant) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(new Integer(cant));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_REP.INV_SET_CANT_PEDREP_TMP(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.INV_SET_CANT_PEDREP_TMP(?,?,?,?,?)", parametros,
                                                 false);
    }

    //DlgDPedidoReposicionVer

    public static void getPedidoActualReposicion(ArrayList array) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_GET_PED_ACT_REP(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_REP.INV_GET_PED_ACT_REP(?,?)", parametros);
    }

    public static void cargaListaPedidoReposicionDetalle(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_LISTA_PROD_REP_VER(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_LISTA_PROD_REP_VER(?,?)", parametros,
                                                 false);
    }

    public static void generarPedidoReposicion(String items, String productos) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(items));
        parametros.add(new Integer(productos));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_REP.INV_GENERAR_PED_REP(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.INV_GENERAR_PED_REP(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void cargaListaPedidoReposicionDetalle_filtro(FarmaTableModel pTableModel, String tipo,
                                                                String codigo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(tipo);
        parametros.add(codigo);
        log.debug("invocando a PTOVENTA_REP.INV_LISTA_PROD_REP_VER_FILTRO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_LISTA_PROD_REP_VER_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }

    //DlgPedidoReposicionDetalle

    public static void cargaDetallePedidoReposicion(FarmaTableModel pTableModel,
                                                    String nroPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroPedido);
        log.debug("invocando a PTOVENTA_REP.INV_GET_DET_REP_VER(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_GET_DET_REP_VER(?,?,?)", parametros,
                                                 false);
    }

    public static void cargaDetallePedidoReposicionFiltro(FarmaTableModel pTableModel, String nroPedido,
                                                          String pCodFiltro) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroPedido);
        parametros.add(pCodFiltro);
        log.debug("invocando a PTOVENTA_REP.INV_GET_DET_REP_FILTRO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_GET_DET_REP_FILTRO(?,?,?,?)",
                                                 parametros, false);
    }


    //DlgTransferenciaNueva

    public static void obtieneInfoDetalleImpresionGuia(ArrayList array, String numNotaEs,
                                                       String secGuia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs);
        parametros.add(new Integer(secGuia));
        log.debug("invocando a PTOVENTA_INV.INV_GET_DET_GUIA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_DET_GUIA(?,?,?,?)", parametros);
    }

    public static void actualizaGuiaImpreso(String numNotaEs, String secGuia, String numGuia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs);
        parametros.add(new Integer(secGuia));
        parametros.add(numGuia);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_SET_IND_IMPRESO_GUIA(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_SET_IND_IMPRESO_GUIA(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static ArrayList getSecuencialGuia(String numNotaEs, String pSecImprLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs);
        parametros.add(pSecImprLocal);
        ArrayList array = new ArrayList();
        log.debug("invocando a PTOVENTA_INV.INV_GET_SEC_GUIA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_SEC_GUIA(?,?,?,?)", parametros);
        return array;
    }

    public static void getListaImpresorasDisp(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_IMPRESORAS(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_IMPRESORAS(?,?)", parametros,
                                                 false);
    }


    /*public static void efectuaOpAnexasProd(String pCodProd,String pCantMov)throws SQLException{

  	parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesInventario.vNumNotaEs);
    parametros.add(pCodProd);
		parametros.add(new Integer(pCantMov));
    parametros.add(ConstantsPtoVenta.MOT_KARDEX_INGRESO_MATRIZ);
    parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
    parametros.add(FarmaVariables.vIdUsu);

    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_INV.INV_OPERAC_ANEXAS_PROD(?,?,?,?,?,?,?,?)", parametros, false);
  }*/

    public static void obtieneNumComp_ForUpdate(ArrayList pArrayList, String pSecImprLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecImprLocal);
        log.debug("invocando a PTOVENTA_CAJ.INV_OBTIENE_NUM_COMP_PAGO_IMPR(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.INV_OBTIENE_NUM_COMP_PAGO_IMPR(?,?,?)",
                                                          parametros);
    }

    public static void actualizaNumComp_Impresora(String pSecImprLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecImprLocal);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_ACTUALIZA_IMPR_NUM_COMP(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_ACTUALIZA_IMPR_NUM_COMP(?,?,?,?)", parametros,
                                                 false);
    }

    public static void reConfiguraCaja(String pSecImprLocal, String pNumComprob) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(new Integer(pSecImprLocal.trim()));
        parametros.add(pNumComprob.trim());
        log.debug("invocando a PTOVENTA_INV.INV_RECONFIG_IMPRESORA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_RECONFIG_IMPRESORA(?,?,?,?)", parametros,
                                                 false);
    }

    //------------------------------------

    public static boolean getHistoricoLote(String codProd) throws SQLException {
        boolean retorno;
        int cant;
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);

        log.debug("invocando a PTOVENTA_INV.INV_GET_HISTORICO_LOTE(?,?,?):" + parametros);
        cant = FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_INV.INV_GET_HISTORICO_LOTE(?,?,?)", parametros);
        if (cant == 0)
            retorno = false;
        else
            retorno = true;
        return retorno;
    }
    //---------------------------------

    public static void efectuaOpAnexasProd(String pCodProd, String secGuia, String pCantMov, int pTotal,
                                           int pDif) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vNumNotaEs);
        parametros.add(new Integer(secGuia));
        parametros.add(pCodProd);
        parametros.add(new Integer(pCantMov));
        parametros.add(ConstantsPtoVenta.MOT_KARDEX_INGRESO_MATRIZ);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(new Integer(pTotal));
        parametros.add(new Integer(pDif));
        log.debug("invocando a PTOVENTA_INV.INV_OPERAC_ANEXAS_PROD(?,?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_OPERAC_ANEXAS_PROD(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static int getCantNumEntrega(String numeroEntrega) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numeroEntrega);
        log.debug("invocando a PTOVENTA_INV.INV_GET_NUM_ENTREGA(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_INV.INV_GET_NUM_ENTREGA(?,?,?)", parametros);
    }

    public static void agregarExcesoProducto(String codProd, String numeroEntrega, String numLote, String fecVenc,
                                             String cantExcedente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numeroEntrega);
        parametros.add(numLote);
        parametros.add(fecVenc);
        parametros.add(codProd);
        parametros.add(new Integer(cantExcedente));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_AGREGA_EXCESO_PROD(?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_AGREGA_EXCESO_PROD(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    //modificarExcesoProducto(VariablesInventario.vSecExcProd,VariablesInventario.vCodProd,txtNumeroEntrega.getText(),txtNumLote.getText(),txtFecVenc.getText(),txtCantExcedente.getText());

    public static void modificarExcesoProducto(String secExceso, String codProd, String numeroEntrega, String numLote,
                                               String fecVenc, String cantExcedente) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(secExceso);
        parametros.add(numeroEntrega);
        parametros.add(numLote);
        parametros.add(fecVenc);
        parametros.add(codProd);
        parametros.add(new Integer(cantExcedente));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_MODIFICA_EXCESO_PROD(?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_MODIFICA_EXCESO_PROD(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaExcesos(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_EXCESO(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_EXCESO(?,?)", parametros, false);
    }

    public static void setearValores() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vNumNotaEs);
        parametros.add(VariablesInventario.vSecDetNota);
        parametros.add(VariablesInventario.vCantMov);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_SETEAR_VALORES(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_SETEAR_VALORES(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void listaFiltroMvsKardex(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vCodProd);
        parametros.add(VariablesInventario.vFecIniMovKardex);
        parametros.add(VariablesInventario.vFecFinMovKardex);
        parametros.add(VariablesInventario.vCodFiltro);
        log.debug("invocando a PTOVENTA_INV.INV_FILTRO_LISTA_MOVS_KARDEX(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_FILTRO_LISTA_MOVS_KARDEX(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaNumGuiaKardex(String numNotaEs, String secGuia, String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs);
        parametros.add(new Integer(secGuia));
        parametros.add(codProd);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_ACT_KARDEX_GUIA_REC(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_ACT_KARDEX_GUIA_REC(?,?,?,?,?,?)", parametros,
                                                 false); //JCHAVEZ 29122009
    }

    public static String getEstadoProcesoSap(String numNotaEs, String tipoNotaEs) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs);
        parametros.add(tipoNotaEs);
        log.debug("invocando a PTOVENTA_INV.INV_GET_ESTADO_PROC_SAP(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_GET_ESTADO_PROC_SAP(?,?,?,?)",
                                                           parametros);
    }

    public static ArrayList getCabeceraGuiaTrans(String numNota) throws SQLException {
        ArrayList array = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        log.debug("invocando a PTOVENTA_INV.INV_GET_CABECERA_TRANSF(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_CABECERA_TRANSF(?,?,?)",
                                                          parametros);
        return array;
    }
    //DlgGuiasRemision

    public static void getListaGuiasRangos(FarmaTableModel pTableModel) throws SQLException {
        ArrayList array = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCaja.vFecIniVerComp);
        parametros.add(VariablesCaja.vFecFinVerComp);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_GUIAS_REM(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_GUIAS_REM(?,?,?,?)", parametros,
                                                 false);
    }

    public static int verificaGuias(String pSecInicial, String pSecFinal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecInicial);
        parametros.add(pSecFinal);
        parametros.add(VariablesCaja.vTipComp);
        log.debug("invocando a PTOVENTA_INV.INV_VERIFICAR_GUIAS(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_INV.INV_VERIFICAR_GUIAS(?,?,?,?,?)", parametros);
    }

    public static int verificaCorrecionGuia(String pSecInicial, String pSecFinal, String pCantidad,
                                            String pDireccion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecInicial);
        parametros.add(pSecFinal);
        parametros.add(new Integer(pCantidad.trim()));
        parametros.add(pDireccion);
        parametros.add(VariablesCaja.vTipComp);
        log.debug("invocando a PTOVENTA_INV.INV_VERIFICAR_CORRECCION_GUIA(?,?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_INV.INV_VERIFICAR_CORRECCION_GUIA(?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void corregirGuias(String pSecIni, String pSecFin, String pCant, String pInd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecIni.trim());
        parametros.add(pSecFin.trim());
        parametros.add(pCant.trim());
        parametros.add(pInd.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesCaja.vTipComp);
        log.debug("invocando a PTOVENTA_INV.INV_CORRIGE_GUIAS(?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_CORRIGE_GUIAS(?,?,?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void corregirGuia(String numNotaEs, String secGuia, String numGuia, String numGuiaNuevo,
                                    String tipNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs.trim());
        parametros.add(new Integer(secGuia.trim()));
        parametros.add(numGuia.trim());
        parametros.add(tipNota.trim());
        parametros.add(numGuiaNuevo.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_CORREGIR_NUM_GUIA(?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_CORREGIR_NUM_GUIA(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    //DlgPedidoReposicionNuevo

    public static void cargaListaProductosReposicionStkCero(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_LISTA_PROD_REP_STK_CERO(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_REP.INV_LISTA_PROD_REP_STK_CERO(?,?)",
                                                 parametros, false);
    }

    //DlgTransferenciasPorConfirmar

    public static void cargaListaTransfPorConfirmar(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_TRANSF_CONFIRMAR(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_TRANSF_CONFIRMAR(?,?,?)",
                                                 parametros, false);
    }

    public static void confirmarTransferencia(String numTransf) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numTransf);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_CONFIRMAR_TRANSF(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_CONFIRMAR_TRANSF(?,?,?,?)", parametros,
                                                 false);
    }

    //DlgTransferenciasLocal

    public static void cargaListaTransfLocal(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_TRANSF.LISTA_TRANSF_LOCAL(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TRANSF.LISTA_TRANSF_LOCAL(?,?,?)", parametros,
                                                 false);
    }

    //DlgTransferenciasLocalVer

    public static ArrayList cargaCabeceraTransfLocal(String numNota, String codLocalOrigen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(codLocalOrigen);
        ArrayList array = new ArrayList();
        log.debug("invocando a PTOVENTA_TRANSF.GET_CAB_TRANSF_LOCAL(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_TRANSF.GET_CAB_TRANSF_LOCAL(?,?,?,?,?)",
                                                          parametros);
        return array;
    }

    public static void cargaDetalleTransfLocal(FarmaTableModel pTableModel, String numNota,
                                               String codLocalOrigen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(codLocalOrigen);
        log.debug("invocando a PTOVENTA_TRANSF.GET_DET_TRANSF_LOCAL(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_TRANSF.GET_DET_TRANSF_LOCAL(?,?,?,?)",
                                                 parametros, false);
    }

    public static void aceptaTransfLocal(String numNota, String codLocalOrigen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codLocalOrigen);
        parametros.add(numNota);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_TRANSF.GENERAR_GUIA_INGRESO_LOCAL(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TRANSF.GENERAR_GUIA_INGRESO_LOCAL(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String getEstadoProcesoCierreDia(String numNotaEs, String tipoNotaEs) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs);
        parametros.add(tipoNotaEs);
        log.debug("invocando a PTOVENTA_INV.INV_GET_EST_PROC_CIERRE_DIA(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_GET_EST_PROC_CIERRE_DIA(?,?,?,?)",
                                                           parametros);
    }

    public static String getDireccionOrigenLocal() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.GET_DIREC_ORIGEN_LOCAL(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.GET_DIREC_ORIGEN_LOCAL(?,?)", parametros);
    }

    public static String getNumUltimoPedRep() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_GET_ULTIMO_PED_REP(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_GET_ULTIMO_PED_REP(?,?)", parametros);
    }

    //DlgPedidoReposicionCantidadMatriz

    /**
     * Se modifica la cantidad solicida del producto, se guarda un historico del cambio
     * */
    public static void guardarCantPedRepMatriz(String numPedido, String codProd, String cant) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedido);
        parametros.add(codProd);
        //modificando para realizae un trim cuando guardara
        //23.10.2007 dubilluz modificacion
        parametros.add(new Integer(cant.trim()));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_SET_CANT_PEDREP_MATRIZ(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_SET_CANT_PEDREP_MATRIZ(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void getDatosTransportista(ArrayList array, String codDestino) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDestino);
        log.debug("invocando a PTOVENTA_INV.INV_GET_DATOS_TRANSPORTISTA(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_DATOS_TRANSPORTISTA(?,?,?)",
                                                          parametros);
    }

    /**
     *
     * @param array
     * @param numeroEntrega
     * @param codProd
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 02.11.2006
     */
    public static void consultaNumeroEntrega(ArrayList array, String numeroEntrega,
                                             String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(numeroEntrega);
        log.debug("invocando a PTOVENTA_INV.INV_GET_CANT_RECEP(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_CANT_RECEP(?,?,?,?)",
                                                          parametros);
    }

    /**
     * @param pCodMotKardex
     * @param pCantAjuste
     * @param pNumEntrega
     * @param pNumNotaEs
     * @param pPos
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 02.11.2006
     */
    public static void ingresaAjusteDiferencia(String pCodMotKardex, int pCantAjuste, String pNumEntrega,
                                               String pNumNotaEs, String pPos) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesInventario.vCodProd);
        parametros.add(pCodMotKardex);
        parametros.add(new Integer(pCantAjuste));
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_AJUSTE);
        parametros.add(pNumEntrega);
        parametros.add(pNumNotaEs);
        parametros.add(new Integer(pPos));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_INGRESA_AJUSTE_DIFERENCIA(?,?,?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_INV.INV_INGRESA_AJUSTE_DIFERENCIA(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    //DlgStockLocales

    public static void cargaListaStockLocalesPreferidos(FarmaTableModel pTableModel,
                                                        String pCodprod) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodprod);
        log.debug("invocando a PTOVENTA_INV.INV_STOCK_LOCALES_PREFERIDOS(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_STOCK_LOCALES_PREFERIDOS(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaStockDemasLocales(FarmaTableModel pTableModel, String pCodprod) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodprod);
        log.debug("invocando a PTOVENTA_INV.INV_STOCK_LOCAL_NO_PREFERIDOS(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_STOCK_LOCAL_NO_PREFERIDOS(?,?,?)",
                                                 parametros, false);
    }

    public static String obtieneInfoStock(String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesInventario.vCodLocalDestino);
        parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_INV.INV_CONECTA_MATRIZ(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_CONECTA_MATRIZ(?,?,?)", parametros);
    }

    public static String obtieneIndicadorStock(String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_REP.INV_OBTIENE_IND_STOCK(?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_OBTIENE_IND_STOCK(?)", parametros);
    }


    public static String obtieneFechaCalculoMaxMin() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_OBTIENE_FECHA_REPOSICION(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_OBTIENE_FECHA_REPOSICION(?,?)",
                                                           parametros);
    }

    public static String obtieneCantidad(String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        //log.debug(parametros);
        log.debug("invocando a PTOVENTA_REP.INV_GET_CANT_ANT_PED_REP(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_GET_CANT_ANT_PED_REP(?,?,?)", parametros);
    }

    /**Nuevo
     * @Autor: Luis Reque Orellana
     * @Fecha:  20/04/2007
     * */
    public static void guardarCantAdicPedRepTemp(String codProd, String cant) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(new Integer(cant));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_REP.INV_SET_CANT_ADIC_TMP(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.INV_SET_CANT_ADIC_TMP(?,?,?,?,?)", parametros,
                                                 false);
    }

    /**Nuevo
     * @Autor: Luis Reque Orellana
     * @Fecha:  25/04/2007
     * */
    public static void verProductosCantAdic(ArrayList pArreglo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_LISTA_PROD_REP_VER_ADIC(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArreglo, "PTOVENTA_REP.INV_LISTA_PROD_REP_VER_ADIC(?,?)",
                                                          parametros);
    }

    /**Nuevo
     * @Autor: Luis Reque Orellana
     * @Fecha:  25/04/2007
     * */
    public static void getPedidoActualReposicionAdic(ArrayList array) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.INV_GET_PED_ACT_REP_ADIC(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_REP.INV_GET_PED_ACT_REP_ADIC(?,?)",
                                                          parametros);
    }

    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  11/06/2007
     * */
    public static void actualizaIndLinea() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("invocando a PTOVENTA_REP.INV_ACTUALIZA_IND_LINEA(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.INV_ACTUALIZA_IND_LINEA(?,?,?)", parametros,
                                                 false);
    }

    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  11/06/2007
     * */
    public static String obtieneIndLinea() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("invocando a PTOVENTA_REP.INV_OBTIENE_IND_LINEA(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_OBTIENE_IND_LINEA(?,?)", parametros);
    }

    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  11/06/2007
     * */
    public static void actualizaIndCalMaxMin(String pCodProd, String pCantAtendida,
                                             String pNumPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(new Integer(pCantAtendida));
        parametros.add(pNumPedido);
        parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_INV.INV_ACTUALIZA_IND_CAL_MAX_MIN(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_ACTUALIZA_IND_CAL_MAX_MIN(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  02/08/2007
     * */
    public static String obtieneFechaLimiteIngresoCC(String pFechaIngreso) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaIngreso);
        log.debug("invocando a PTOVENTA_INV.INV_VALIDA_FECHA_COT_COMP(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_VALIDA_FECHA_COT_COMP(?,?,?)",
                                                           parametros);
    }

    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  08/08/2007
     * */
    public static void actualizaIndStkPedidoReposicion() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_REP.REP_PROCESO_IND_STOCK(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.REP_PROCESO_IND_STOCK(?,?,?)", parametros, false);
    }

    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  08/08/2007
     * */
    public static void actualizaIndStkPedidoReposicionNull() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_REP.REP_ACTUALIZA_INDS_STOCK_NULL(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.REP_ACTUALIZA_INDS_STOCK_NULL(?,?,?)", parametros,
                                                 false);
    }

    /**
     * Retorna el valor actual del IndLinea Actual
     * @author dubilluz
     * @since  24.09.2007
     */
    public static String getIndLinea() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_REP.REP_GET_INDLINEA(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.REP_GET_INDLINEA(?,?)", parametros);
    }

    /**
     * Obtiene el Time Estimado
     * @Autor dubilluz
     * @Fecha 12.09.2007
     * */
    public static String getTimeEstimado() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("invocando a PTOVENTA_REP.REP_GET_TIEMPOESTIMADO(?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.REP_GET_TIEMPOESTIMADO(?)", parametros);
    }

    /**
     * Realiza una consulta a Delivery
     * @Autor dubilluz
     * @Fecha 12.09.2007
     * */
    public static String consulta_Delivery(String vQuery, String pIndCloseConecction) throws SQLException {
        log.debug("invocando a " + vQuery + parametros);
        return FarmaDBUtilityRemoto.executeSQLQuery(vQuery, FarmaConstants.CONECTION_DELIVERY, pIndCloseConecction);
    }

    /**
     * Actualiza el IndLinea directamente
     * @author dubilluz
     * @sice   24.09.2007
     * */
    public static void actualizaIndLinea(String vIndicador, int vTiempoConexion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(vIndicador.trim());
        parametros.add(vTiempoConexion + "");
        log.debug("invocando a PTOVENTA_REP.INV_ACTUALIZA_IND_LINEA(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_REP.INV_ACTUALIZA_IND_LINEA(?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Consultara el motivo Seleccionado
     * para saber si se habilitara el text Fraccion
     * @author dubilluz
     * @since  15.10.2007
     */
    public static String consultaMotivo(String pCodMotivo, String pCodTipo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodTipo.trim());
        parametros.add(pCodMotivo.trim());
        log.debug("invocando a PTOVENTA_INV.INV_GET_IND_TXTFRACC_MOTIVO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_GET_IND_TXTFRACC_MOTIVO(?,?,?)",
                                                           parametros);
    }

    /**
     * Se lista las competencias
     * @author dubilluz
     * @since  12.11.2007
     */
    public static void cargaListaCompetencias(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_INV.INV_LISTA_COMPETENCIAS(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INV.INV_LISTA_COMPETENCIAS(?,?)", parametros,
                                                 false);
    }

    /**
     * Valida que el numero de comprobante de la competencia no exista
     * @author dubilluz
     * @since  12.11.2007
     */
    public static String validaNumeroDocCompetencia(String pNumDoc, String pCodTipo,
                                                    String pRucEmpresa) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumDoc.trim());
        parametros.add(pCodTipo.trim());
        parametros.add(pRucEmpresa.trim());
        log.debug("invocando a PTOVENTA_INV.INV_VALIDA_NUM_COMPETENCIA(?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_VALIDA_NUM_COMPETENCIA(?,?,?,?,?)",
                                                           parametros);
    }

    /**
     * Se lista las competencias
     * @author dubilluz
     * @since  12.11.2007
     */
    public static void buscaEmpresa(ArrayList pArreglo, String pRuc) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pRuc.trim());
        log.debug("invocando a PTOVENTA_INV.INV_BUSCA_EMPRESA(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArreglo, "PTOVENTA_INV.INV_BUSCA_EMPRESA(?,?)", parametros);
    }

    /**
     * Obtiene los datos de cotizacion
     * @author dubilluz
     * @since  26.11.2007
     */
    public static void getDatosCotizacion(ArrayList pArreglo, String pNumDoc, String pCodTipo,
                                          String pRucEmpresa) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumDoc.trim());
        parametros.add(pCodTipo.trim());
        parametros.add(pRucEmpresa.trim());
        log.debug("invocando a PTOVENTA_INV.REP_GET_DATOS_COTIZACION(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArreglo, "PTOVENTA_INV.REP_GET_DATOS_COTIZACION(?,?,?,?,?)",
                                                          parametros);
    }

    /**
     * Obtiene la confirmacion de la revision del producto por parte del RDM
     * @author JCORTEZ
     * @sice   15.10.2007
     * */

    public static String getRevisadoRDM(String vCodProd, String NroPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(NroPedido);
        parametros.add(vCodProd);
        log.debug("invocando a PTOVENTA_REP.DIST_OBTIENE_PROD_REVISION(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.DIST_OBTIENE_PROD_REVISION(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Lista los productos Adicionales
     * @param pTableModel
     * @param nroPedido
     * @auhtor DUbilluz
     * @throws SQLException
     */
    public static void cargaParametrosProductosLocal(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_CUR_PARAM_PROD_LOCAL(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_PED_ADICIONAL.PED_F_CUR_PARAM_PROD_LOCAL(?,?)",
                                                 parametros, false);
    }

    /**
     * Se Obtiene el indicador de hacer pedido reposicion de modo automatico
     * @return
     * @throws SQLException
     * @author Dubilluz
     */
    public static String getIndPedReposicionAutomatico() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a Farma_Gral.GET_IND_PEDIDO_REP_AUTOMATICO(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.GET_IND_PEDIDO_REP_AUTOMATICO(?,?)",
                                                           parametros);
    }

    /**
     * @author DVELIZ
     * @since 10.09.08
     * @param pTableModel
     * @throws SQLException
     */
    public static void cargaListaProductosPedidoAdicional(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PROD_PED(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PROD_PED(?,?)",
                                                 parametros, false);
    }

    /**
     * @author DVELIZ
     * @since 10.09.08
     * @param lista
     * @param pCodProd
     * @throws SQLException
     */
    public static void cargaDetalleProductoPedidoAdicional(ArrayList lista, String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_CUR_DET_PROD_PED(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(lista,
                                                          "PTOVENTA_PED_ADICIONAL.PED_F_CUR_DET_PROD_PED(?,?,?)",
                                                          parametros);
    }

    /**
     * @author DVELIZ
     * @since 10.09.08
     * @param codProd
     * @param cCantSol
     * @param cCantAuto
     * @param cIndAutori
     * @throws SQLException
     */
    public static void guardarCantidadPedidoAdicionalLocal(String codProd, String cCantSol, String cCantAuto,
                                                           String cIndAutori) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(cCantSol);
        parametros.add(cCantAuto);
        parametros.add(cIndAutori);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_P_INSER_PED_REP_ADI_LOCAL(?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_PED_ADICIONAL.PED_P_INSER_PED_REP_ADI_LOCAL(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @author DVELIZ
     * @since 23.09.08
     * @param codProd
     * @param cCantAuto
     * @param cIndAutori
     * @throws SQLException
     */
    public static void guardarCantidadPedidoAdicionalMatriz(String codProd, String cCantAuto,
                                                            String cIndAutori) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesProducto.vCodLocal_PedAdic);
        parametros.add(codProd);
        parametros.add(cCantAuto);
        parametros.add(cIndAutori);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_P_INSER_PED_REP_ADI_MATRIZ(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_PED_ADICIONAL.PED_P_INSER_PED_REP_ADI_MATRIZ(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * @Author DVELIZ
     * @Since 12.09.08
     * @param pTableModel
     * @param cCodProd
     * @throws SQLException
     */
    public static void cargaHistorialPedidoAdicional(FarmaTableModel pTableModel,
                                                     String cCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cCodProd);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PED_BK(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PED_BK(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Lista el historial de PVM por productos en Matriz
     * @author DVELIZ
     * @since 23.09.08
     * @param pTableModel
     * @param cCodProd
     * @throws SQLException
     */
    public static void cargaHistorialPedidoAdicionalMatriz(FarmaTableModel pTableModel,
                                                           String cCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesProducto.vCodLocal_PedAdic);
        parametros.add(cCodProd);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PED_BK(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_PED_ADICIONAL.PED_F_CUR_LISTA_PED_BK(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se Lista los pedidos especiales realizados
     * @throws SQLException
     * @author JCORTEZ
     * @since 09.09.2008
     */
    public static void cargaListaPedidosEspeciales(FarmaTableModel pTableModel, String pFecIni,
                                                   String pFecFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecIni);
        parametros.add(pFecFin);
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PEDIDOS(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PEDIDOS(?,?,?,?)", parametros,
                                                 false);
    }


    /**
     * Se Lista los productos para el pedido especial
     * @throws SQLException
     * @author JCORTEZ
     * @since 09.09.2008
     */
    public static void cargaListaProductosEspeciales(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PROD(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PROD(?,?)",
                                                 parametros, true);
    }

    /**
     * Se guarda la cabecera del pedido Especial
     * @throws SQLException
     * @author JCORTEZ
     * @since 09.09.2008
     */
    public static void agregarCabeceraPedEsp(String NumPedEsp, String CantItems) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumPedEsp);
        parametros.add(CantItems.trim());
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_P_ING_CAB_PED_ESP(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PED_ESPC.PED_ESPC_P_ING_CAB_PED_ESP(?,?,?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se guarda el detalle del pedido Especial
     * @throws SQLException
     * @author JCORTEZ
     * @since 09.09.2008
     */
    public static void agregarDetallePedEsp(ArrayList ArrayProd, String NumPedEsp) throws SQLException {
        int sec = 1;
        for (int i = 0; i < ArrayProd.size(); i++) {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(FarmaVariables.vIdUsu);
            parametros.add(NumPedEsp);
            parametros.add("" + sec);
            sec++;
            parametros.add(((String)((ArrayList)ArrayProd.get(i)).get(0)).trim());
            parametros.add(((String)((ArrayList)ArrayProd.get(i)).get(5)).trim());
            log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_P_ING_DET_PED_ESP(?,?,?,?,?,?,?):" + parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null,
                                                     "PTOVENTA_PED_ESPC.PED_ESPC_P_ING_DET_PED_ESP(?,?,?,?,?,?,?)",
                                                     parametros, false);
            log.debug("INGRESO EXITOSO...................................");
        }
    }


    public static void cargaDetallePedEsp(FarmaTableModel pTableModel, String numPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPed);
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PED_DET(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PED_DET(?,?,?)",
                                                 parametros, false);
    }


    /**
     * Se guarda la confirmacion del pedido especial Det
     * @throws SQLException
     * @author JCORTEZ
     * @since 09.09.2008
     */
    public static void confirmarPedidoDet(String NumPedEsp, ArrayList ArrayProd) throws SQLException {
        for (int i = 0; i < ArrayProd.size(); i++) {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(FarmaVariables.vIdUsu);
            parametros.add(NumPedEsp);
            parametros.add(((String)((ArrayList)ArrayProd.get(i)).get(0)).trim());
            parametros.add(((String)((ArrayList)ArrayProd.get(i)).get(4)).trim());
            parametros.add(((String)((ArrayList)ArrayProd.get(i)).get(6)).trim());
            log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_P_UPT_PROD_DET(?,?,?,?,?,?,?):" + parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PED_ESPC.PED_ESPC_P_UPT_PROD_DET(?,?,?,?,?,?,?)",
                                                     parametros, false);
        }
    }

    /**
     * Se guarda la confirmacion del pedido especial Cab
     * @throws SQLException
     * @author JCORTEZ
     * @since 09.09.2008
     */
    public static void confirmarPedidoCab(String NumPedEsp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumPedEsp);
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_UPT_CAB(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PED_ESPC.PED_ESPC_UPT_CAB(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se obtiene el detalle del pedido
     * @author JCORTEZ
     * @since  09.09.2008
     */
    public static void obtieneDetPedido(ArrayList pArreglo, String NumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(NumPed.trim());
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PED_DET(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArreglo,
                                                          "PTOVENTA_PED_ESPC.PED_ESPC_F_CUR_LISTA_PED_DET(?,?,?)",
                                                          parametros);
    }


    /**
     * Retorna la cantidad Maxima de Item de Productos Especiales
     * @author Dubilluz
     * @since  22.09.2008
     * @return
     * @throws SQLException
     */
    public static String obtieneCantMaxItemPedidoEspecial() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_F_CHAR_MAX_ITEM_PED(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PED_ESPC.PED_ESPC_F_CHAR_MAX_ITEM_PED(?,?)",
                                                           parametros);
    }

    /**
     * Se anula el pedido especial generado
     * @throws SQLException
     * @author JCORTEZ
     * @since 25.09.2008
     */
    public static void anularPedidoEspecial(String NumPedEsp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumPedEsp);
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_ANULA(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PED_ESPC.PED_ESPC_ANULA(?,?,?,?)", parametros, false);

    }

    public static void cargarMeses(ArrayList pArray) throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_CUR_CARGAR_MESES:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,

                "PTOVENTA_PED_ADICIONAL.PED_F_CUR_CARGAR_MESES", parametros);
    }

    public static String getTransito(String pCodProd) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd.trim());
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_CHAR_GET_TRANSITO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PED_ADICIONAL.PED_F_CHAR_GET_TRANSITO(?,?,?)",
                                                           parametros);
    }

    /**
     * Modifica el estado del pedido especial
     * @author DVELIZ
     * @since 18.10.08
     * @param vEstado
     * @param vNumPedVta
     * @return
     * @throws SQLException
     */
    public static void setEstadoPedidoEspecial(String vNumPedVta, String vEstado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(vNumPedVta);
        parametros.add(vEstado);
        parametros.add(FarmaVariables.vIpPc);

        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_P_MOD_EST_PEDIDO (?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PED_ESPC.PED_ESPC_P_MOD_EST_PEDIDO (?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Carga el detalle del pedido selecciona para modificarlo
     * @param vNumPedVta
     * @throws SQLException
     */
    public static void getDataDetallePedido(String vNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vNumPedVta);

        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_P_CUR_CARGA_DETALLE (?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(VariablesInventario.vArrayProductosEspeciales,
                                                          "PTOVENTA_PED_ESPC.PED_ESPC_P_CUR_CARGA_DETALLE (?,?,?)",
                                                          parametros);

    }

    public static void delDetallePedido(String vNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vNumPedVta);

        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_P_DEL_DET_PEDIDO (?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PED_ESPC.PED_ESPC_P_DEL_DET_PEDIDO (?,?,?)",
                                                 parametros, false);
    }

    public static String obtengoMensaje(String vFechIni, String vFechFin) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vFechIni);
        parametros.add(vFechFin);

        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_F_VAR_MENSAJE (?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PED_ESPC.PED_ESPC_F_VAR_MENSAJE (?,?,?,?)",
                                                           parametros);

    }

    public static String getEstadoActualPedido(String vNumPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vNumPedVta.trim());
        log.debug("invocando a PTOVENTA_PED_ESPC.PED_ESPC_F_GET_EST_PEDIDO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PED_ESPC.PED_ESPC_F_GET_EST_PEDIDO(?,?,?)",
                                                           parametros);

    }

    public static String realizarTransfDestino(String numTransf, String codLocalDestino,
                                               String pIndCloseConecction) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codLocalDestino);
        parametros.add(numTransf);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_MATRIZ_TRANSF.TRANSF_F_CHAR_LLEVAR_DESTINO(?,?,?,?,?): " + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_TRANSF.TRANSF_F_CHAR_LLEVAR_DESTINO(?,?,?,?,?)",
                                                                 parametros, FarmaConstants.CONECTION_MATRIZ,
                                                                 pIndCloseConecction);

    }

    public static void actualizarEstadoTransf(String numTransf, String pEstado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numTransf);
        parametros.add(pEstado);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_TRANSF.TRANSF_P_ACTUALIZAR_ESTADO(?,?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TRANSF.TRANSF_P_ACTUALIZAR_ESTADO (?,?,?,?,?)",
                                                 parametros, false);

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
        log.debug("invocando a PTOVENTA_TRANSF.GET_FRACCION_LOCAL(?,?,?,?):" + parametros);
        //  FarmaDBUtilityRemoto.executeSQLStoredProcedureStrOut("PTOVENTA_TRANSF.INV_P_VERI_FRAC_ENTRE_LOC(?,?,?,?,?)", parametros, false);
        log.debug("FarmaConstants.CONECTION_MATRIZ: " + FarmaConstants.CONECTION_MATRIZ);
        log.debug(" pIndCloseConecction: " + pIndCloseConecction);
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
        log.debug("invocando a PTOVENTA_INV.INV_F_GET_VAL_FRAC_PROD(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_INV.INV_F_GET_VAL_FRAC_PROD(?,?,?)", parametros);
    }

    /**
     * Se valida guias emitidas e impresas por transferencia
     * @throws SQLException
     * @author JCORTEZ
     * @since 28.10.2009
     */
    public static String validaGuiasTransf(String numNotaEs) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNotaEs);
        log.debug("invocando a PTOVENTA_INV.INV_F_EXISTS_GUIAS_TRANSF(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_F_EXISTS_GUIAS_TRANSF(?,?,?)",
                                                           parametros);
    }

    //JMIRANDA 10.12.09

    public static String getObtieneDescLargaTransf(String pLlaveTabGral, String pDescCorta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pLlaveTabGral);
        parametros.add(pDescCorta);
        log.debug("invocando a PTOVENTA_INV.TRANSF_F_DESC_LARGA_TRANS(?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.TRANSF_F_DESC_LARGA_TRANS(?,?)", parametros);

    }

    /**
     * Graba inicio y fin del proceso de creacion de transferencia
     * @author JCHAVEZ
     * @since 10.12.2009
     */
    public static void grabaInicioFinCreaTransferencia(String pNumNotaEs, String pTipoTmp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumNotaEs);
        parametros.add(pTipoTmp);
        log.debug("invocando a PTOVENTA_INV.INV_P_REG_TMP_INIFIN_CREATRANS(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_P_REG_TMP_INIFIN_CREATRANS(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Graba inicio y fin del proceso de confirmacion de transferencia
     * @author JCHAVEZ
     * @since 10.12.2009
     */
    public static void grabaInicioFinConfirmacionTransferencia(String pNumNotaEs, String pTipoTmp,
                                                               String ptipoConf) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumNotaEs);
        parametros.add(ptipoConf);
        parametros.add(pTipoTmp);
        log.debug("invocando a PTOVENTA_INV.INV_P_REG_TMP_INIFIN_CONFTRANS(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_P_REG_TMP_INIFIN_CONFTRANS(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Graba inicio y fin del proceso de impresión de guías en una transferencia
     * @author JCHAVEZ
     * @since 10.12.2009
     */
    public static void grabaInicioFinGuiasTransferencia(String pNumNotaEs, String pTipoTmp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumNotaEs);
        parametros.add(pTipoTmp);
        log.debug("invocando a PTOVENTA_INV.INV_P_REG_TMP_INIFIN_GUIATRANS(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_P_REG_TMP_INIFIN_GUIATRANS(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Graba inicio y fin del proceso de anulación de una transferencia
     * @author JCHAVEZ
     * @since 10.12.2009
     */
    public static void grabaInicioFinAnulaTransferencia(String pNumNotaEs, String pTipoTmp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumNotaEs);
        parametros.add(pTipoTmp);
        log.debug("invocando a PTOVENTA_INV.INV_P_REG_TMP_INIFIN_ANUTRANS(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_P_REG_TMP_INIFIN_ANUTRANS(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     *
     * @author ASOSA
     * @since 18.01.2010
     * @param codcia
     * @param codloc
     * @param secusu
     * @return
     * @throws SQLException
     */
    public static String validarAsistenteAuditoria(String codcia, String codloc, String secusu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(codcia);
        parametros.add(codloc);
        parametros.add(secusu);
        log.debug("invocando a PTOVENTA_INV.INV_F_VALIDA_ASIST_AUDIT: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_F_VALIDA_ASIST_AUDIT(?,?,?)", parametros);
    }

    /**
     * @author JMIRANDA
     * @since 09.02.2010
     * @throws SQLException
     */
    public static void asociaRecepEntrega(String pNumRecepcion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumRecepcion.trim());
        parametros.add(VariablesInventario.vNumEntAfectar);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_INV.INV_P_ASOCIA_ENT_RECEP(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_P_ASOCIA_ENT_RECEP(?,?,?,?,?)", parametros,
                                                 false);

    }

    public static String creaRecepEntrega() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesRecepCiega.vNombreTrans);
        parametros.add(VariablesRecepCiega.vHoraTrans);
        parametros.add(VariablesRecepCiega.vPlacaUnidTrans);
        parametros.add(VariablesRecepCiega.vCantBultos);
        parametros.add(VariablesRecepCiega.vCantPrecintos);
        //JMIRANDA 05.03.2010 agrega Glosa
        parametros.add(VariablesRecepCiega.vGlosa);
        log.debug("invocando a PTOVENTA_INV.INV_F_INS_RECEP_CAB(?,?,?,?,?,?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_F_INS_RECEP_CAB(?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void actualizaCantGuias(int pCantGuias, String pNroRecepcion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroRecepcion);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add("" + pCantGuias);
        log.debug("invocando a PTOVENTA_INV.INV_P_ACT_CANT_GUIAS(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INV.INV_P_ACT_CANT_GUIAS(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static String getIndNuevaTransf() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_INV.INV_GET_IND_NUEVA_TRANSF:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_GET_IND_NUEVA_TRANSF", parametros);
    }

    /**
     * Inserta un lote nuevo en la transferencia de mercaderia
     * @author ASOSA
     * @since 14.04.2010
     * @param codprod
     * @param numlote
     * @throws SQLException
     */
    public static void insertarLote(String codprod, String numlote, String fechvenc) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(codprod.trim());
        parametros.add(numlote.trim());
        parametros.add(fechvenc.trim());
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_TRANSF.TRANS_P_INS_LOTE(?,?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Retorna si esta activada o no la funcioalidad de ingreso de lote
     * @return
     * @throws SQLException
     */
    public static String getIndIngresarLote() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_TRANSF.TRANS_F_GET_IND_LOTE:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TRANSF.TRANS_F_GET_IND_LOTE", parametros);
    }

    /**
     * Elimina un lote, solo lo eliminara si fue el quien lo ingreso
     * @author ASOSA
     * @since 05.04.2010
     * @param codprod
     * @param numlote
     * @throws SQLException
     */
    public static String eliminarLote(String codprod, String numlote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(codprod.trim());
        parametros.add(numlote.trim());
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_TRANSF.TRANS_P_DEL_LOTE(): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_TRANSF.TRANS_P_DEL_LOTE(?,?,?,?,?)", parametros);
    }
    //JQUISPE 20.04.2010

    public static String devFechaVto(String codprod, String numlote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codprod.trim());
        parametros.add(numlote.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_F_GET_FECHAVTO_LOTE(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Consulta si el producto esta inactivo
     * @author JQUISPE
     * @since 05.05.2010
     * @param codprod
     * @throws SQLException
     */

    public static String isProductoActivo(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd.trim());
        log.debug("PTOVENTA_INV.INV_F_ACTIVO_PROD: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_F_ACTIVO_PROD(?,?,?)", parametros);
    }

    /**
     * Consulta si debe pedir el lote para transferencia
     * @author JMIRANDA
     * @since 14.05.2010
     * @throws SQLException
     */

    public static String getIndPideLoteTransf() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("PTOVENTA_INV.INV_GET_PIDE_LOTE_TRANSF: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_GET_PIDE_LOTE_TRANSF(?)", parametros);
    }

    /**
     * Consulta si debe pedir el fecha de vencimiento para transferencia
     * @author JMIRANDA
     * @since 14.05.2010
     * @throws SQLException
     */

    public static String getIndPideFechaVencTransf() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("PTOVENTA_INV.INV_GET_PID_FEC_VTO_TRANSF: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_GET_PID_FEC_VTO_TRANSF(?)", parametros);
    }

    /**
     * Agrega el detalle de una guia de ingreso.
     * @author ASOSA
     * @since 15.07.2010
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
     * @param pMotivo
     * @param secRespaldo
     * @throws SQLException
     */
    public static void agregarDetalleTransferencia_02(String numGuia, String codProd, String valUnit, String valTotal,
                                                      String cantMov, String fecVcto, String numLote,
                                                      String tipDestino, String codDestino, String pValFrac,
                                                      String pIndFrac, String pMotivo,
                                                      String secRespaldo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numGuia);

        parametros.add(codProd);
        parametros.add(new Double(valUnit));
        parametros.add(new Double(valTotal));
        //revisar error de framework dubilluz 27.06.20//
        //parametros.add(""+FarmaUtility.getDecimalNumber(valUnit));
        //parametros.add(""+FarmaUtility.getDecimalNumber(valTotal));
        parametros.add(new Integer(cantMov));
        parametros.add(fecVcto);
        parametros.add(numLote);
        //parametros.add(VariablesInventario.vMotivo_Transf);
        parametros.add(pMotivo.trim());
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(new Integer(pValFrac));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(tipDestino);
        parametros.add(codDestino);
        parametros.add(pIndFrac);
        parametros.add(secRespaldo.trim()); //ASOSA, 15.07.2010
        log.debug("PTOVTA_RESPALDO_STK.INV_AGREGA_DET_TRANSFERENCIA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + parametros);

        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVTA_RESPALDO_STK.INV_AGREGA_DET_TRANSFERENCIA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Anula de una transferencia.
     * @author ASOSA
     * @since 15.07.2010
     * @param numNota
     * @throws SQLException
     */
    public static void anularTransferencia_02(String numNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numNota);
        parametros.add(ConstantsPtoVenta.MOT_KARDEX_ANULACION_TRANSFERENCIA);
        parametros.add(ConstantsPtoVenta.TIP_DOC_KARDEX_GUIA_ES);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVTA_RESPALDO_STK.INV_ANULA_TRANSFERENCIA(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVTA_RESPALDO_STK.INV_ANULA_TRANSFERENCIA(?,?,?,?,?,?)",
                                                 parametros, false); //JCHAVEZ 29122009
    }


    /*  public static void getDatosTransportista(ArrayList array,
                                           String codDestino)
    throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(codDestino);
    log.debug("invocando a PTOVENTA_INV.INV_GET_DATOS_TRANSPORTISTA(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(array,
                                                      "PTOVENTA_INV.INV_GET_DATOS_TRANSPORTISTA(?,?,?)",
                                                      parametros);
  }
     * */

    /**
     * Busca el Ruc y la Direccion por codigo de transporte.
     * @author CHUANES
     * @since 26.11.2013
     * @param codTransp
     * @throws SQLException
     */
    public static void getDatosTranspRecepCiega(ArrayList array, String codDestino) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codDestino);
        log.debug("PTOVENTA_INV.INV_GET_DATOS_TRANSPORTE(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(array, "PTOVENTA_INV.INV_GET_DATOS_TRANSP_CIEGA(?,?,?)",
                                                          parametros);
    }

    public static ArrayList<ArrayList<String>> getDetalleGuiaRemision(String pNumNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumNota);
        log.debug("PTOVENTA_INV.GET_TEXTO_IMPR(?,?,?)" + parametros);
        String pCadena = FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INV.GET_TEXTO_IMPR(?,?,?)", parametros);
        String[] aResultado = pCadena.split("Ã");
        ArrayList<ArrayList<String>> vResultado = new ArrayList<ArrayList<String>>();
        for (String linea : aResultado) {
            ArrayList<String> vDetalleGuia = new ArrayList<String>();
            vDetalleGuia.add(linea);
            vResultado.add(vDetalleGuia);
        }
        return vResultado;
    }

    public static String getStockProducto(String cCodProd, String cantTrans) throws SQLException {
        String flag;
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cCodProd);
        parametros.add(cantTrans);
        log.info("PTOVENTA_MDIRECTA.GET_VALIDA_STOCK_PRODUCTO(?,?,?,?)" + parametros);
        flag =
FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MDIRECTA.GET_VALIDA_STOCK_PRODUCTO(?,?,?,?)", parametros);
        flag = flag.trim();
        return flag;
    }

    /**
     * Listar detalle de orden de compra de la cual se espera devolver algunos productos por haber ingresado previamente sobrantes.
     *@author ASOSA
     * @since 19/08/2014
     * @param codCompra
     * @param pTableModel
     * @throws SQLException
     */
    public static void listarProdOrdenCompraSobrante(String codCompra,
                                                     FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codCompra);
        log.debug("PTOVENTA_MDIRECTA.GET_DET_OC_SOBRANTE(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MDIRECTA.GET_DET_OC_SOBRANTE(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Obtiene el codigo de proveedor de una ordern de compra.
     *@author ASOSA
     * @since 25/08/2014
     * @param codCompra
     * @param pTableModel
     * @return
     * @throws SQLException
     */
    public static String getProvOrdenCompra(String codCompra) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codCompra);
        log.debug("invocando a PTOVENTA_MDIRECTA.GET_PROV_OC(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MDIRECTA.GET_PROV_OC(?,?,?,?)", parametros);
    }

    /**
     * Obtiene parametro general string
     * @author ASOSA
     * @since 25/08/2014
     * @param numeroTabGral
     * @return
     * @throws SQLException
     */
    public static String getValGralString(int numeroTabGral) throws SQLException {
        parametros = new ArrayList();
        parametros.add(numeroTabGral);
        log.debug("invocando a PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MDIRECTA.GET_VAL_GEN_STRING(?)", parametros);
    }

    /**
     * Obtiene parametro general string (dos valores string)
     * @author ASOSA
     * @since 28/08/2014
     * @param numeroTabGral
     * @return
     * @throws SQLException
     */
    public static String getValGralTwoString(int numeroTabGral) throws SQLException {
        parametros = new ArrayList();
        parametros.add(numeroTabGral);
        log.debug("invocando a PTOVENTA_MDIRECTA.GET_VAL_GEN_TWO_STRG(?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MDIRECTA.GET_VAL_GEN_TWO_STRG(?)", parametros);
    }


    /**
     * Obtiene parametro general INTEGER
     * @param numeroTabGral
     * @return
     * @throws SQLException
     */
    public static String getValGralInteger(int numeroTabGral) throws SQLException {
        parametros = new ArrayList();
        parametros.add(numeroTabGral);
        log.debug("invocando a PTOVENTA_MDIRECTA.GET_VAL_GEN_INTEGER(?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MDIRECTA.GET_VAL_GEN_INTEGER(?)", parametros);
    }

    /**
     * Obtiene las ordenes de compra pendientes de regularizar por exceso de mercaderia.
     * @author ASOSA
     * @since 26/08/2014
     * @param pTableModel
     * @throws SQLException
     */
    public static void listarOrdCompPendRegularizar(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_MDIRECTA.GET_ORD_COMP_PEND_REG(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MDIRECTA.GET_ORD_COMP_PEND_REG(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Determina si una OC ya fue regularizada..
     * @author ASOSA
     * @since 26/08/2014
     * @param codCompra
     * @return
     * @throws SQLException
     */
    public static String getIndOcRegularizada(String codCompra) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codCompra);
        log.debug("invocando a PTOVENTA_MDIRECTA.GET_IND_OC_REGULARIZADO(?,?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_MDIRECTA.GET_IND_OC_REGULARIZADO(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene cantidades de venta en los ultimos meses por producto
     * @author RHERRERA
     * @since 22/09/2014
     * @param cCodProd
     * @return
     * @throws SQLException
     */
    public static void cargaListaProductosPedido(ArrayList cLista, String cCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cCodProd);
        log.debug("invocando a PTOVENTA_PED_ADICIONAL.PED_F_PROD_PED_INS(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(cLista, "PTOVENTA_PED_ADICIONAL.PED_F_PROD_PED_INS(?,?,?)",
                                                          parametros);
    }

}
