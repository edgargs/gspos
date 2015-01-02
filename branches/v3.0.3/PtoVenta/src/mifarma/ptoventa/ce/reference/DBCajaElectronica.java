package mifarma.ptoventa.ce.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaDBUtilityRemoto;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      31.07.2006   Creación<br>
 * LMESIA     07.08/2006   Modificacion<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class DBCajaElectronica {

    private static final Logger log = LoggerFactory.getLogger(DBCajaElectronica.class);

    private static ArrayList parametros;

    public DBCajaElectronica() {
    }

    /******************************PAULO*********************************/
    public static String agregaFormaPagoEntrega(String pSecMovCaja, String pCodFormaPago, String pCantVoucher,
                                                String pTipMoneda, String pMontEntrega, String pMontEntregaTot,
                                                String pNumLote) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pCodFormaPago);
        parametros.add(new Integer(pCantVoucher));
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntrega)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontEntregaTot)));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pNumLote);
        log.debug("Agrega Forma Pago:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_GRABA_FORMA_PAGO_ENTREGA(?,?,?,?,?,?,?,?,?,?)",
                                                           parametros).trim();
    }

    public static void eliminaFormaPagoEntega(String pSecMovCaja, String pCodFormaPago, String pTipMoneda,
                                              String pLote, String pSecFormaPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pCodFormaPago);
        parametros.add(pTipMoneda);
        parametros.add(pLote);
        parametros.add(pSecFormaPago.trim());
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("invocando a PTOVENTA_CE.CE_ELIMINA_FORMA_PAGO(?,?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_ELIMINA_FORMA_PAGO(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaFormaPagoEntegaSL(String pSecMovCaja, String pCodFormaPago, String pTipMoneda,
                                                String pSecFormaPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pCodFormaPago);
        parametros.add(pTipMoneda);
        parametros.add(pSecFormaPago.trim());
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("invocando a PTOVENTA_CE.CE_ELIMINA_FORMA_PAGO_SL(?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_ELIMINA_FORMA_PAGO_SL(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    public static void cargaListaDetalleFormasPago(FarmaTableModel pTableModel,
                                                   String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        log.debug("PTOVENTA_CE.CE_LISTA_DETALLE_FORMAS_PAGO(?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_DETALLE_FORMAS_PAGO(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaIngresoCuadraturas(FarmaTableModel pTableModel, String pSecMovCaja,
                                                    String pCodCuadratura) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pCodCuadratura);
        log.debug("PTOVENTA_CE.CE_LISTA_ANUL_PENDIENTES(?,?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_ANUL_PENDIENTES(?,?,?,?)",
                                                 parametros, true);
    }

    public static void agregaCuadraturaCajaLista(String pSecMovCaja, String pCodCuadratura, String pNumSerie,
                                                 String pTipComp, String pNumCompPago, String pMonMonedaOrig,
                                                 String pMonTotal, String pMonVuelto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pCodCuadratura);
        parametros.add(pNumSerie);
        parametros.add(pTipComp);
        parametros.add(pNumCompPago);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonMonedaOrig)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonTotal)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonVuelto)));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_CE.CE_INSERTA_LISTA_CUADRA_ING(?,?,?,?,?,?,?,?,?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE.CE_INSERTA_LISTA_CUADRA_ING(?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaCuadratura(String pCodCuadratura, String pSecMovCaja,
                                         String pSecCuadratura) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCuadratura);
        parametros.add(pSecMovCaja);
        parametros.add(pSecCuadratura);
        log.debug("elimina cuadratura " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_ELIMINA_CUADRATURA(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void listaEliminacionCuadratura(FarmaTableModel pTableModel, String pSecMovCaja,
                                                  String pCodCuadratura) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pCodCuadratura);
        log.debug("PTOVENTA_CE.CE_LISTA_ELIMINA_CUADRATURA(?,?,?,?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_ELIMINA_CUADRATURA(?,?,?,?)",
                                                 parametros, true);
    }

    public static int validacionEliminacion(String pCodCuadratura, String pSecMovCaja,
                                            String pSecCuadratura) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pSecCuadratura);
        parametros.add(pCodCuadratura);
        log.debug("arreglo " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE.CE_VALIDA_ELIMINACION(?,?,?,?,?)", parametros);
    }

    public static ArrayList obtieneComprobanteMinMax(String pSecMovCaja) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_CE.CE_OBTIENE_RANGO_COMP(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static ArrayList obtieneComprobanteMinMaxCD(String pFechaCierreDV) throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDV);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_CE.CE_OBTIENE_RANGO_COMP_CD(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static void listaCuadraturasCierreDia(FarmaTableModel pTableModel,
                                                 String pFechaCierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierre);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_CUADRATURAS(?,?,?)", parametros,
                                                 false);
    }


    public static void listaCotizacionCompetencia(FarmaTableModel pTableModel, String pFecha) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecha);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_COT_COMPETENCIA(?,?,?)",
                                                 parametros, true);
    }

    public static void agregaCotizacionCompetencia(String pCodCuadratura, String pFechaCierreDV, String pNumeroSec,
                                                   String pMonMonedaOrig, String pGlosa) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDV);
        parametros.add(pCodCuadratura);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonMonedaOrig)));
        parametros.add(pNumeroSec);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pGlosa);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_AGREGA_COT_COMPETENCIA(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void listaEliminacionCuadraturaCierreDia(FarmaTableModel pTableModel, String pCodCuadratura,
                                                           String pFechaCierreCD) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCuadratura);
        parametros.add(pFechaCierreCD);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_ANUL_CUADRATURA_CD(?,?,?,?)",
                                                 parametros, true);
    }

    public static void eliminaCuadraturaCierreDia(String pFechaCierreCD, String pCodCuadratura,
                                                  String pSecCuadraturaCD) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreCD);
        parametros.add(pCodCuadratura);
        parametros.add(pSecCuadraturaCD);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_ELIMINA_CUADRATURA_CD(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void actualizaFechaGuiaCotizaComp(String pFechaCierreCD, String pNumeroNota) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumeroNota);
        parametros.add(pFechaCierreCD);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_ACTUALIZA_GUIA_COT_COMP(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static ArrayList obtenerNumeroCuenta() throws SQLException {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(VariablesCajaElectronica.vCodEntidadFinanciera);
        parametros.add(VariablesCajaElectronica.vCodTipoMoneda);
        log.debug("PTOVENTA_CE_ERN.CE_OBTIENE_CUENTA(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_CE_ERN.CE_OBTIENE_CUENTA(?,?,?)",
                                                          parametros);
        return pOutParams;
    }

    public static void listaDetalleCuadraturasCierreDia(FarmaTableModel pTableModel, String pFechaCierreCD,
                                                        String pCodCuadratura) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreCD);
        parametros.add(pCodCuadratura);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_DETTALLE_CUADRATURA(?,?,?,?)",
                                                 parametros, false);
    }

    public static void listaDetalleFormasPagoCierreDia(FarmaTableModel pTableModel, String pFechaCierreCD,
                                                       String pCodFormaPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreCD);
        parametros.add(pCodFormaPago);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_DETTALLE_FORMAS_PAGO(?,?,?,?)",
                                                 parametros, false);
    }

    public static void asignaVBContable(String pFechaCierreCD) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreCD);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_ASIGNA_VB_CONTABLE(?,?,?,?)", parametros,
                                                 false);
    }

    public static String obtieneVBQF(String pFechaCierreCD) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreCD);
        log.debug("", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_OBTIENE_VB_QF(?,?,?)", parametros);
    }

    /******************************PAULO*********************************/

    /*-----BEGIN ERIOS BLOCK-----*/
    //DlgCuadraturas
    public static void listaCuadraturas(FarmaTableModel pTableModel, String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_ERN.CE_LISTA_CUADRATURAS(?,?,?)",
                                                 parametros, false);
    }

    //DlgCuadraturaCampos

    public static void listaCuadraturaCampos(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(VariablesCajaElectronica.vCodCuadratura);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_ERN.CE_LISTA_CUADRATURA_CAMPOS(?,?)",
                                                 parametros, false);
    }

    public static void agregaDatosCuadratura(String serie, String tipoComp, String numeroComp, double monto,
                                             double vuelto, String tipoBillete, String tipoMoneda, String formaPago,
                                             String serieBillete) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCajaElectronica.vSecMovCaja);
        parametros.add(VariablesCajaElectronica.vCodCuadratura);
        parametros.add(serie);
        parametros.add(tipoComp);
        parametros.add(numeroComp);
        parametros.add(new Double(monto));
        parametros.add(new Double(vuelto));
        parametros.add(tipoBillete);
        parametros.add(tipoMoneda);
        parametros.add(formaPago);
        parametros.add(serieBillete);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesCajaElectronica.vFechaCuadratura);
        parametros.add(VariablesCajaElectronica.vMotivoCuadratura);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE_ERN.CE_AGREGA_DATOS_CUADRATURA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void agregaDatosEfectivoRendido(String tipoMoneda, double monto, String secEntidadFinan,
                                                  String fechaOperacion, String numeroOperacion, String agencia,
                                                  String fechaEmision, String serie, String tipoComp,
                                                  String numeroComp, String titular, String codAutorizacion,
                                                  String cCodTrabajador, String motivo, String formaPago,
                                                  String serieBillete, String tipoBillete, double montoParcial,
                                                  String ruc, String razonSocial, String localNuevo,
                                                  String codServicio, String numDocumento, String proveedor,
                                                  String fechaVencimiento, String serieDocumento, double montoPerdido,
                                                  String numeroPedido) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCajaElectronica.vFechaCierreDia);
        parametros.add(VariablesCajaElectronica.vCodCuadratura);
        parametros.add(tipoMoneda);
        parametros.add(new Double(monto));
        parametros.add(secEntidadFinan);
        parametros.add(fechaOperacion);
        parametros.add(numeroOperacion);
        parametros.add(agencia);
        parametros.add(fechaEmision);
        parametros.add(serie);
        parametros.add(tipoComp);
        parametros.add(numeroComp);
        parametros.add(titular);
        parametros.add(codAutorizacion);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(cCodTrabajador);
        parametros.add(motivo);
        parametros.add(formaPago);
        parametros.add(serieBillete);
        parametros.add(tipoBillete);
        parametros.add(new Double(montoParcial));
        parametros.add(ruc);
        parametros.add(razonSocial);
        parametros.add(localNuevo);
        parametros.add(codServicio);
        parametros.add(numDocumento);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(proveedor);
        parametros.add(fechaVencimiento);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(serieDocumento);
        parametros.add(new Double(montoPerdido));
        parametros.add(numeroPedido);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE_ERN.CE_AGREGA_DATOS_EFECTIVO_REND(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }
    /*------END ERIOS BLOCK------*/

    /**********LMESIA***************/

    /****************CIERRE DE TURNO********************/

    public static void buscaCajasAsignadasUsuario(ArrayList pArrayList, String pDiaVenta,
                                                  String pCodCajero) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDiaVenta);
        parametros.add(pCodCajero);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_BUSCA_CAJAS_USU_DIAVENTA(?,?,?,?)",
                                                          parametros);
    }

    public static void buscaTurnosCajaAsignadaUsuario(ArrayList pArrayList, String pDiaVenta, String pCodCajero,
                                                      String pNumCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDiaVenta);
        parametros.add(pCodCajero);
        parametros.add(new Integer(pNumCaja));
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_LISTA_TURNOS_CAJA_USU_DIA(?,?,?,?,?)",
                                                          parametros);
    }

    public static String validaDatosCierreCajaTurno(String pDiaVenta, String pCodCajero, String pNumCaja,
                                                    String pNumTurno) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDiaVenta);
        parametros.add(pCodCajero);
        parametros.add(new Integer(pNumCaja));
        parametros.add(new Integer(pNumTurno));
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_VALIDA_DATO_CAJ(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static void obtieneFechaAperturaCierreCaja(ArrayList pArrayList, String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CE_LMR.CE_OBTIENE_FEC_APER_CER(?,?,?)",
                                                          parametros);
    }

    public static String obtenerNombreCompletoUsuario(String pSecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_OBTIENE_NOMBRE_USUARIO(?,?,?)",
                                                           parametros);
    }

    public static void cargaListaFormasPagoCierre(FarmaTableModel pTableModel,
                                                  String pSecMovCaja) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_LISTA_FORMA_PAGO_CIERRE(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaCuadraturasCierre(FarmaTableModel pTableModel,
                                                   String pSecMovCaja) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_LISTA_CUADRATURA_CIERRE(?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneIndVB_ForUpadte(ArrayList pArrayList, String pSecMovCaja,
                                              String pTipVB) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pTipVB);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_OBTIENE_IND_VB_FOR_UPDATE(?,?,?,?)",
                                                          parametros);
    }

    public static void actualizaIndicadorVB(String pSecMovCaja, String pIndicadorVB,
                                            String pTipVB) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pIndicadorVB);
        parametros.add(pTipVB);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vNuSecUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ACTUALIZA_VB(?,?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static String obtenerMontoTotalSistema(String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_OBTIENE_MONTO_TOTAL_SISTEMA(?,?,?)",
                                                           parametros);
    }

    public static void evaluaDeficitAsumidoCajero(String pSecMovCaja, double pMontoDeficit) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(new Double(pMontoDeficit));
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_EVALUA_DEFICIT_ASUMIDO_CAJ(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static int evaluaEliminacionVBCajero(String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE_LMR.CE_EVALUA_ELIMINACION_VB_CAJ(?,?,?)",
                                                           parametros);
    }

    public static void obtieneComprobantesValidosMovCaja(ArrayList pArrayList,
                                                         String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_COMPROBANTES_VALIDOS_CT(?,?,?)",
                                                          parametros);
    }

    public static String obtenerIndicadorComprobantesValidosUsuario(String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_IND_COMP_VALIDOS_USUARIO(?,?,?)",
                                                           parametros);
    }

    public static void actualizaComprobantesCierreTurno(String pSecMovCaja, String pBoletaIni, String pBoletaFin,
                                                        String pFacturaIni, String pFacturaFin,
                                                        String pIndCompValidos) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pBoletaIni);
        parametros.add(pBoletaFin);
        parametros.add(pFacturaIni);
        parametros.add(pFacturaFin);
        parametros.add(pIndCompValidos);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE_LMR.CE_ACTUALIZA_COMPROBANTES_CT(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerObservacionCierreTurno(String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_OBTIENE_OBS_CIERRE_TURNO(?,?,?)",
                                                           parametros);
    }

    public static void actualizaObservacionCierreTurno(String pSecMovCaja, String pObservacion) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pObservacion);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ACT_OBSERV_CIERRE_TURNO(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaIndicadorCompValidosCierreTurno(String pSecMovCaja,
                                                                String pIndCompValidos) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pIndCompValidos);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ACT_IND_COMP_VALIDO_CIERRET(?,?,?,?,?)",
                                                 parametros, false);
    }

    /****************CIERRE DE DIA********************/

    public static void cargaListaHistoricoCierreDia(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_LISTA_HIST_CIERRE_DIA(?,?)",
                                                 parametros, false);
    }

    public static void registraCierreDiaVenta(String pCierreDiaVenta, String pSecUsuLogeado,
                                              String pTipoCambio) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCierreDiaVenta);
        parametros.add(pSecUsuLogeado);
        parametros.add(new Double(pTipoCambio));
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_REGISTRA_CIERRE_DIA(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static int validaFechaCierreDiaRegistrar(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE_LMR.CE_VALIDA_REGISTRO_CIERRE_DIA(?,?,?)",
                                                           parametros);
    }

    public static int obtieneCantCajasAperturadasDia(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE_LMR.CE_OBTIENE_CAJ_APERTURADAS_DIA(?,?,?)",
                                                           parametros);
    }

    public static int validaCajasConVBCajeroCierreDia(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE_LMR.CE_VALIDA_CAJA_CON_VB_CAJERO(?,?,?)",
                                                           parametros);
    }

    public static void cargaConsolidadoFormaPagoCierreDia(FarmaTableModel pTableModel,
                                                          String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_CONSO_FOR_PAG_ENT_CIER_DIA(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaConsolidadoCuadraturaCierreDia(FarmaTableModel pTableModel,
                                                           String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_CONSO_CUADRATURA_CIER_DIA(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaConsolidadoEfecRecaudadoCierre(FarmaTableModel pTableModel,
                                                           String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_CONSO_EFEC_RECAUDADO_CIERRE(?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerMontoTotalSistemaCierreDia(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_MONTO_TOTAL_SIST_CIERRE_DIA(?,?,?)",
                                                           parametros);
    }

    public static void cargaConsolidadoEfecRendidoCierre(FarmaTableModel pTableModel,
                                                         String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_CONSO_EFEC_RENDIDO_CIERRE(?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneIndVBCierreDia_ForUpadte(ArrayList pArrayList,
                                                       String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_IND_VB_CIERRE_DIA_FORUPDATE(?,?,?)",
                                                          parametros);
    }

    public static void actualizaIndicadorVBCierreDia(String pFechaCierreDia, String pIndicadorVBCierreDia,
                                                     String pDescObs) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        parametros.add(pIndicadorVBCierreDia);
        parametros.add(pDescObs);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ACTUALIZA_VB_CIERRE_DIA(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaInfoHistVBCierreDia(String pFechaCierreDia, String pDescMotivo) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(pDescMotivo);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ACT_INFO_HIST_VB_CIER_DIA(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtenerInfoCierreDia(ArrayList pArrayList, String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_OBTIENE_OBS_USU_VB_CD(?,?,?)",
                                                          parametros);
    }

    public static void obtieneComprobantesValidosCierreDia(ArrayList pArrayList,
                                                           String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_COMPROBANTES_VALIDOS_CD(?,?,?)",
                                                          parametros);
    }

    public static String obtenerIndicadorComprobantesValidosCierreDia(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_IND_COMP_VALIDOS_DIA(?,?,?)",
                                                           parametros);
    }

    public static void actualizaComprobantesCierreDia(String pFechaCierreDia, String pBoletaIni, String pBoletaFin,
                                                      String pFacturaIni, String pFacturaFin,
                                                      String pIndCompValidos) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        parametros.add(pBoletaIni);
        parametros.add(pBoletaFin);
        parametros.add(pFacturaIni);
        parametros.add(pFacturaFin);
        parametros.add(pIndCompValidos);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE_LMR.CE_ACTUALIZA_COMPROBANTES_CD(?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void actualizaIndicadorCompValidosCierreDia(String pFechaCierreDia,
                                                              String pIndCompValidos) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        parametros.add(pIndCompValidos);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ACT_IND_COMP_VALIDO_CIERRED(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void evaluaDeficitAsumidoQF(String pFechaCierreDia, double pMontoDeficit) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        log.debug("pFechaCierreDia : " + pFechaCierreDia);
        parametros.add(new Double(pMontoDeficit));
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_EVALUA_DEFICIT_ASUMIDO_QF(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static int validaVBCierreDia(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE_LMR.CE_VALIDA_CIERRE_DIA_CON_VB(?,?,?)",
                                                           parametros);
    }

    public static int validaCajasConVBQFDiaVenta(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE_LMR.CE_VALIDA_CAJA_CON_VB_QF(?,?,?)",
                                                           parametros);
    }

    public static int validaCompConReclamosNavsat(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_CE.CE_OBT_CANT_RECLAMOS_NAVSAT(?,?,?)",
                                                           parametros);
    }

    public static void cargaComprobantesReclamosNavsat(FarmaTableModel pTableModel,
                                                       String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_COMP_RECLAMOS_NAVSAT(?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneListaReclamosProvCab(FarmaTableModel pTableModel,
                                                   String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_COMP_RECLAMOS_NAVSAT(?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneListaReclamosProvDet(FarmaTableModel pTableModel,
                                                   String pSecCompPago) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecCompPago);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_DET_RECLAMOS_NAVSAT(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaRangoCompMovCaja(FarmaTableModel pTableModel,
                                                  String pSecMovCaja) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_LISTA_RANGO_COMP_MOV_CAJ(?,?,?)",
                                                 parametros, false);
    }

    public static void cargaListaRangoCompCierreDia(FarmaTableModel pTableModel,
                                                    String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_LISTA_RANGO_COMP_CIE_DIA(?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaRangoCompMovCaja(String pSecMovCaja, String pTipComp,
                                               String pNumSerie) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(pTipComp);
        parametros.add(pNumSerie);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ELIMINA_RANGO_COMP_MOV_CAJ(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void eliminaRangoCompCierreDia(String pFechaCierreDia, String pTipComp,
                                                 String pNumSerie) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        parametros.add(pTipComp);
        parametros.add(pNumSerie);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_ELIMINA_RANGO_COMP_CIE_DIA(?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneRangoComprobantesMovCaja(ArrayList pArrayList, String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_OBTIENE_RANGO_COMP_MOV_CAJ(?,?,?)",
                                                          parametros);
    }

    public static void obtieneRangoComprobantesCierreDia(ArrayList pArrayList,
                                                         String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_OBTIENE_RANGO_COMP_CIE_DIA(?,?,?)",
                                                          parametros);
    }

    public static void insertaRangoCompMovCaja(String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(VariablesCajaElectronica.vTipCompUsuario);
        parametros.add(VariablesCajaElectronica.vNumSerieUsuario);
        parametros.add(VariablesCajaElectronica.vNumeroMinUsuario);
        parametros.add(VariablesCajaElectronica.vNumeroMaxUsuario);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_CE_LMR.CE_INSERTA_RANGO_COMP_MOV_CAJ(?,?,?,?,?,?,?,?)\n" +
                parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE_LMR.CE_INSERTA_RANGO_COMP_MOV_CAJ(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void insertaRangoCompCierreDia(String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        parametros.add(VariablesCajaElectronica.vTipCompDia);
        parametros.add(VariablesCajaElectronica.vNumSerieDia);
        parametros.add(VariablesCajaElectronica.vNumeroMinDia);
        parametros.add(VariablesCajaElectronica.vNumeroMaxDia);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(VariablesCajaElectronica.vMontoMin);
        parametros.add(VariablesCajaElectronica.vMontoMAx);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE_LMR.CE_INSERTA_RANGO_COMP_CIE_DIA(?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public static void obtieneListaRangoCompMovCajaUsuario(ArrayList pArrayList,
                                                           String pSecMovCaja) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        log.debug("PTOVENTA_CE_LMR.CE_LISTA_RANGO_COMP_MOV_CAJ(?,?,?) >> " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_LISTA_RANGO_COMP_MOV_CAJ(?,?,?)",
                                                          parametros);
    }

    public static void obtieneListaRangoCompCierreDiaUsuario(ArrayList pArrayList,
                                                             String pFechaCierreDia) throws SQLException {
        pArrayList.clear();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CE_LMR.CE_LISTA_RANGO_COMP_CIE_DIA(?,?,?)",
                                                          parametros);
    }

    /**********LMESIA***************/

    /**
     * NUEVO
     * @Autor: Luis Reque
     * @modify: ASOSA, 18.06.2010
     * @Fecha: 20-03-2007
     * */
    public static void obtieneFormasPago(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PARAMETROS FORMA PAGOOOOOOOOOOOO: " + parametros);
        //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CE.CE_OBTIENE_FORMAS_PAGO(?,?)",parametros, false);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_OBTIENE_FORMAS_PAGO_AS(?,?)", parametros,
                                                 false); //ASOSA, 18.06.2010
    }

    /**Devuelve un listado de los pedidos del dia  para buscar desfasados
     * @author: JCORTEZ
     * @since : 09/07/07
     */
    public static void getListaPedidosCompRangosCierre(ArrayList pArrayList, String vFechaHoy) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vFechaHoy);
        parametros.add(vFechaHoy);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "PTOVENTA_CAJ.CAJ_LISTA_COMPROBANT_COMPROBAR(?,?,?,?)",
                                                          parametros);
    }

    /**Devuelve un listado de los productos virtuales del dia
     * @author: JCORTEZ
     * @since : 11/07/07
     */
    public static void getListaProductosVirtuales(ArrayList pArrayList, String vFechaHoy) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vFechaHoy);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,
                                                          "Ptoventa_ce.CE_OBTIENE_RESUMEN_VIRTUALES(?,?,?)",
                                                          parametros);
    }

    public static void insertaCotizacionCompetenciaAutomatico(String pFechaCierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierre);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_AGREGA_COTIZ_COMP_AUTO(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Carga la lista de Trabajadores con el codigo de RRHH
     * @author dubilluz
     * @since  22.11.2007
     */
    public static void cargaListaTrabajadores(FarmaTableModel pTableModel, String pTipoMaestro) throws SQLException {
        if (pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR) ||
            pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR_LOCAL) ||
            pTipoMaestro.equals(ConstantsPtoVenta.LISTA_CAJERO)) {
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodCia);
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pTipoMaestro);
            parametros.add(VariablesCajaElectronica.vFechaCierreDia);
            log.debug("", parametros);
            FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_ERN.LISTA_TRABAJADOR(?,?,?,?,?)",
                                                     parametros, false);
        }
    }

    /**
     * Verifica si la cuadratura tiene
     * @author dubilluz
     * @since  01.12.2008
     * @param pCodCuadratura
     * @return
     * @throws SQLException
     */
    public static String isMotivoCuadratura(String pCodCuadratura) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCuadratura.trim());
        return FarmaDBUtility.executeSQLStoredProcedureStr("Farma_Gral.F_IS_MOTIVO_CUADRATURA(?,?)", parametros);
    }

    /**
     * Obtiene el indicador de Ce Seguridad (PROSEGUR)
     * @author DUBILLUZ
     * @since  13.01.2009
     * @return
     * @throws SQLException
     */
    public static String getIndCESeguridad(String pCodigoFormaPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodigoFormaPago.trim());
        log.debug("indProsegur:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_IND_SEGUR_LOCAL(?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Agrega el Sobre a la forma de pago
     * @author DUBILLUZ
     * @param pSecMovCaja
     * @param pSecFPago
     * @throws SQLException
     */
    public static void agregaSobre(String pSecMovCaja, String pSecFPago, String CodSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        parametros.add(pSecFPago.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(CodSobre);
        log.debug("Agrega Sobre:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_SEGURIDAD.SEG_P_INSERT_SOBRE(?,?,?,?,?,?)",
                                                 parametros, false);
    }

    //

    public static void eliminaSobreRegistrado(String pCodigoSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodigoSobre.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.debug("Elimina Sobre:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_SEGURIDAD.SEG_P_ELIMINA_SOBRE(?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * obtiene el html a imprimir de los sobrs declarados
     * **/
    public static String getHtmlSobreDeclarados(String pSecMovCaja, String pCodSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        parametros.add(pCodSobre.trim());
        log.debug("obtener html a imprimir invocando a PTOVENTA_CE_SEGURIDAD.SEG_F_VAR_IMP_HTML_SOBRES(?,?,?,?)" +
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_VAR_IMP_HTML_SOBRES(?,?,?,?)",
                                                           parametros);
    }

    public static void getSobreDeclarados(String pSecMovCaja, ArrayList pListaSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        log.debug("obtener los sobres declarados PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_GET_SOBRES(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaSobre,
                                                          "PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_GET_SOBRES(?,?,?)",
                                                          parametros);
    }

    /**
     * Obtiene el indicador de Prosegur
     * @return
     * @author DUBILLUZ
     * @throws SQLException
     */
    public static String getIndProsegur() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("obtener html a imprimir invocando a PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_IND_PROSEGUR(?,?)" +
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_IND_PROSEGUR(?,?)",
                                                           parametros);
    }

    /**
     * Obtiene el indicador tipo Local
     * @return
     * @author JCORTEZ
     * @throws SQLException
     */
    public static String getTipoLocal() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.GET_TIPO_CAJA(?,?)", parametros);
    }

    /**
     * Obtiene el indicador de poder cambiar el combo de SOBRE de forma de entrega efectivo
     * @return String
     * @author JCALLO
     * @throws SQLException
     */
    public static String getIndChangeComboSobre() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_IND_CHCB_SOBRE()" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_IND_CHCB_SOBRE()",
                                                           parametros);
    }

    /**
     * Obtiene la cantidad de veces que puede modificar los sobres declarados por los cajeros
     * @return String
     * @author JCALLO
     * @throws SQLException
     */
    public static String getCantModificacionesSobre() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_CANT_MOD_SOBRE()" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_CANT_MOD_SOBRE()",
                                                           parametros);
    }

    /**
     * Obtiene la cantidad de veces que puede modificar los sobres declarados por los cajeros
     * @return String
     * @author JCALLO
     * @throws SQLException
     */
    public static String getCantSobresEliminados(String pFecCierreCaja, String pSecMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecCierreCaja);
        parametros.add(pSecMovCaja);
        log.debug("invocando a PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_CANT_MOD_SOBRE(?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_SOBRES_ELI(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Obtiene la cantidad de veces que puede modificar los sobres declarados por los cajeros
     * @return String
     * @author JCALLO
     * @throws SQLException
     */
    public static void enviarCorreoSobresModificados(String pFecCierreCaja, String pSecMovCaja, String TipoMensaje,
                                                     String codSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecCierreCaja);
        parametros.add(pSecMovCaja);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(TipoMensaje);
        parametros.add(codSobre);
        log.debug("invocando a PTOVENTA_CE_SEGURIDAD.SEG_P_ENVIAR_CORREO_ALERTA(?,?,?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE_SEGURIDAD.SEG_P_ENVIAR_CORREO_ALERTA(?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Se obtiene tipo de cambio deacuerdo a la fecha
     * @author JCORTEZ
     * @since  19.05.09
     */
    public static double getTipoCambio(String pDiaVenta) throws SQLException {
        ArrayList parameters = new ArrayList();
        parameters.add(FarmaVariables.vCodGrupoCia);
        parameters.add(FarmaVariables.vCodCia);
        parameters.add(pDiaVenta);
        parameters.add("V");
        log.debug("FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(?,?,?,?)" + parameters);
        return FarmaDBUtility.executeSQLStoredProcedureDouble("FARMA_UTILITY.OBTIENE_TIPO_CAMBIO3(?,?,?,?)",
                                                              parameters);
    }

    /*****************************CAMBIO DE FORMA DE PAGO**************************************************/

    public static void cargaListaRegistroVentas(FarmaTableModel pTableModel, String pFechaInicio, String pFechaFin,
                                                String secMovCaja, String NumPed, String Monto,
                                                String tipPago) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio);
        parametros.add(pFechaFin);
        parametros.add(secMovCaja);
        parametros.add(NumPed);
        if (Monto.trim().length() > 0) {
            parametros.add(new Double(Monto));
        } else {
            parametros.add(Monto);
        }
        parametros.add(tipPago);
        log.debug("PTOVENTA_CE.CE_REGISTRO_VENTAS(?,?,?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_REGISTRO_VENTAS(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /*****************************CAMBIO DE FORMA DE PAGO**************************************************/

    /**
     * Se obtiene la lista de las ventas para el cierre de caja x cajero
     * @AUTHOR JQUISPE
     * @SINCE  26.02.10
     * */
    public static void cargaListaRegVentas(FarmaTableModel pTableModel, String secMovCaja, String NumPed, String Monto,
                                           String tipPago) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(secMovCaja);
        parametros.add(NumPed);
        if (Monto.trim().length() > 0) {
            parametros.add(new Double(Monto));
        } else {
            parametros.add(Monto);
        }
        parametros.add(tipPago);
        log.debug("PTOVENTA_CE.CE_LIST_DET_VENTAS(?,?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LIST_DET_VENTAS(?,?,?,?,?,?)",
                                                 parametros, false);
    }


    /** Listado de formas de pago por pedido
     * @AUTHOR JCORTEZ
     * @SINCE  26.02.10
     */
    public static void cargaListaFormasPago(FarmaTableModel pTableModel, String nroPedido) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(nroPedido);
        log.debug("carga lista de fornmas de pago " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_DETALLE_FORMAS_PAGO(?,?,?)", parametros,
                                                 false);
    }

    /**
     * Se obtiene los sobres de efectivos para cierre de turnos
     * @AUTHOR JQUISPE
     * @SINCE  13.03.10
     * */
    /*
    public static void cargaListaSobresTurno(ArrayList pListaSobres) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesCajaElectronica.vCodTurno);
    parametros.add(VariablesCajaElectronica.vCodCaja);
    parametros.add(VariablesCajaElectronica.vFechaTurno);
    parametros.add(VariablesCajaElectronica.vTipoEfectivo);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaSobres,"PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_DET_EFECTIVO_TURNO(?,?,?,?,?,?)",parametros);
    }
    */

    /**
     * Se obtiene los sobres de efectivos para el cierre dia
     * @AUTHOR JQUISPE
     * @SINCE  13.03.10
     * */
    /*
    public static void cargaListaSobresDia(ArrayList pListaSobres) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesCajaElectronica.vFechaTurno);
    parametros.add(VariablesCajaElectronica.vTipoEfectivo);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaSobres,"PTOVENTA_CE_SEGURIDAD.SEG_F_CUR_DET_EFECTIVO_DIA(?,?,?,?)",parametros);
    }
    */

    /**
     * Valida  el caso de visa manual
     * @AUTHOR JQUISPE
     * @SINCE  14.04.10
     * */
    public static String verificaVisaManual() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesCajaElectronica.vFechaCierreDia);
        log.debug("invocando a PTOVENTA_CE_LMR.CE_F_VALIDA_VISA_MANUAL(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_F_VALIDA_VISA_MANUAL(?,?,?)",
                                                           parametros);
    }

    /** Se guarda la nueva forma de pago
     * @AUTHOR JCORTEZ
     * @SINCE  26.02.10
     */
    public static void grabaFormaPagoPedidoBackup(String pCodFormaPago, String pNumPed) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodFormaPago);
        parametros.add(pNumPed);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_FORMA_PAGO_PEDIDO_BK(?,?,?,?,?)", parametros,
                                                 false);
    }

    /** Se guarda la nueva forma de pago
     * @AUTHOR JCORTEZ
     * @SINCE  26.02.10
     */

    public static void grabaNuevaFormaPagoPedido(String pCodFormaPago, String pNumPedVta, String pImPago,
                                                 String pTipMoneda, String pTipoCambio, String pVuelto,
                                                 String pImTotalPago, String pNumTarj, String pFecVencTarj,
                                                 String pNomCliTarj, String pdnix, String pCodvou, String pLote,
                                                 String pCantCupon) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodFormaPago);
        parametros.add(pNumPedVta);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImPago)));
        parametros.add(pTipMoneda);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pTipoCambio)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pVuelto)));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pImTotalPago)));
        parametros.add(pNumTarj);
        parametros.add(pFecVencTarj);
        parametros.add(pNomCliTarj);
        parametros.add(pdnix);
        parametros.add(pCodvou);
        parametros.add(pLote);
        parametros.add(new Integer(pCantCupon));
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_CE.CE_NEW_FORMA_PAGO_PEDIDO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Obtiene la informacion de la tarjeta seleccionada(a que forma de pago pertenece y que tipo de tarjeta es)
     * @AUTHOR JCORTEZ
     * @SINCE 01.03.2010
     * Modificación LLEIVA 13/Sep/2013
     */
    public static void obtenerInfoTarjeta(ArrayList pArrayList, String nrotarj,
                                          String tipoOrigen) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(nrotarj);
        parametros.add(tipoOrigen);
        log.debug("parametros obtenerInfoTarjeta: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CE.CE_F_OBTENER_TARJETA(?,?,?)",
                                                          parametros);
    }


    /** Se valida montos efectivo y tarjeta segun lo declarado
     * @AUTHOR JCORTEZ
     * @SINCE  01.03.10
     */
    public static void validaMontoDeclarado(String pSecMovCajaCierre, String pFecCierre,
                                            String montoCuadratura) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCajaCierre);
        parametros.add(pFecCierre);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(montoCuadratura)));
        log.debug(":::parametros validaMontoDeclarado: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_VAL_MONTO_CIERRE_TURNO(?,?,?,?,?)", parametros,
                                                 false);
    }

    public static String getIndCambioFormaPago() throws SQLException {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_CE.CE_VALIDA_CAMBIO_FORM_PAGO()" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_VALIDA_CAMBIO_FORM_PAGO()", parametros);
    }

    /*****************************PROCESO INGRESO SOBRES**************************************************/

    /**
     * Se obtiene si esta aprobado el sobre creado.
     * @AUTHOR JCORTEZ
     * @SINCE  29.03.2010
     */
    public static String getSobreAprobado(String pCodigoSobre, String CodFormaPago,
                                          String FechaVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(CodFormaPago);
        parametros.add(pCodigoSobre.trim());
        parametros.add(FechaVta.trim());
        log.debug("Prosegur: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_VALIDA_APROBACION(?,?,?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Se valida que el sobre no este asociado remito
     * @AUTHOR JCORTEZ
     * @SINCE  30.03.2010
     */
    public static String getExistSobreRemito(String diaCierre, String ceMovCaja, String codSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(diaCierre.trim());
        parametros.add(ceMovCaja.trim());
        parametros.add(codSobre.trim());
        log.debug("getExistSobreRemito : " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_VALIDA_SOBRE_REMITO(?,?,?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Se obtiene el indicador que determina si el local tiene o no la opcion de prosegur activa
     * @author ASOSA
     * @since 09.04.2010
     * @return
     * @throws SQLException
     */
    public static String getIND_PROSEGUR() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("parametros getIND_PROSEGUR: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_F_GET_IND_PROSEGUR(?,?)", parametros);
    }

    /**
     * Lista los sobres de este local siempre y cuando se hallan aprobado y se halla hecho el cierre de dia por el QF
     * @author ASOSA
     * @since 09.04.2010
     * @param ftable
     * @param fecha
     * @throws SQLException
     */
    public static void getLISTA_SOBRE_AS(FarmaTableModel ftable, String fecha) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(fecha);
        log.debug("parametros getLISTA_SOBRE_AS: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(ftable, "PTOVENTA_CE.CE_F_LISTA_SOBRE_AS(?,?,?)", parametros, false);
    }

    /**
     * Agrega remitos actualizando con su codigo los sobres que contiene
     * @author ASOSA
     * @since 09.04.2010
     * @param numremi
     * @param list
     * @throws SQLException
     */
    public static void agregaRemito_AS(String numremi, ArrayList list, String precinto) throws SQLException {
        for (int i = 0; i < list.size(); i++) {
            String fecha = ((String)((ArrayList)list.get(i)).get(0)).trim();
            String codSobre = ((String)((ArrayList)list.get(i)).get(2)).trim();
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(FarmaVariables.vIdUsu);
            parametros.add(numremi.trim());
            parametros.add(fecha);
            parametros.add(codSobre.trim());
            parametros.add(precinto.trim());
            /*
            log.debug("parametros agregaRemito_AS: "+parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CE.CE_P_AGREGA_REMITO(?,?,?,?,?)",parametros,false);
            */
            //dubilluz 27.07.2010
            log.debug("parametros agregaRemito_DU: " + parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_REMITO.CE_P_AGREGA_REMITO_DU(?,?,?,?,?,?,?)",
                                                     parametros, false);
        }
    }

    /**
     * Retorna el html que se imprimira en el voucher del remito
     * @author ASOSA
     * @since 09.04.2010
     * @param codremi
     * @return
     * @throws SQLException
     */
    public static String getHTML_VOUCHER_REMITO(String codremi) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codremi.trim());
        parametros.add(FarmaVariables.vIPBD);
        log.debug("parametros getHTML_VOUCHER_REMITO: " + parametros);
        //return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_F_HTML_VOUCHER_REMITO(?,?,?,?)",parametros);
        //dubilluz 27.07.2010 NUEVO FORMATO
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_REMITO.CE_F_HTML_VOUCHER_REMITO_DU(?,?,?,?)",
                                                           parametros);
    }

    /**
     * graba el historial de remito
     * @author ASOSA
     * @since 22.04.2010
     * @param codremi
     */
    public static void saveHistorialRemito(String codremi) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codremi.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("parametros saveHistorialRemito: " + parametros);
        //dubilluz 27.07.2010
        //FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CE.CE_P_SAVE_HIST_REMI(?,?,?,?)",parametros,false);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_REMITO.CE_P_SAVE_HIST_REMI(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Lista de el detalle que se podra eliminar delas cuadraturas
     * @author ASOSA
     * @since 25/04/2010
     * @param pTableModel
     * @param pCodCuadratura
     * @param pFechaCierreCD
     * @throws SQLException
     */
    public static void listaEliminacionCuadraturaCierreDia_02(FarmaTableModel pTableModel, String pCodCuadratura,
                                                              String pFechaCierreCD) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCuadratura);
        parametros.add(pFechaCierreCD);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_ANUL_CUADRATURA_CD_AS(?,?,?,?)",
                                                 parametros, true);
    }

    /**
     * Lista el consolidado de efectivos rendidos por el QF
     * @author ASOSA
     * @since 25/04/2010
     * @param pTableModel
     * @param pFechaCierreDia
     * @throws SQLException
     */
    public static void cargaConsolidadoEfecRendidoCierre_02(FarmaTableModel pTableModel,
                                                            String pFechaCierreDia) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_CONSO_EFEC_RENDIDO_CIERRE(?,?,?)",
                                                 parametros, false);
    }

    /**
     * Valida que no halla diferencia entre rendido y recaudado
     * @author ASOSA
     * @since 25.04.2010
     * @param cierredia
     * @return
     * @throws SQLException
     */
    public static String getValidarMontoRecRen(String cierredia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cierredia);
        log.debug("parametros PTOVENTA_CE.CE_F_VALIDAR_MONTO_SD: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_F_VALIDAR_MONTO_SD(?,?,?)", parametros);
    }

    /**
     * lISTA LAS CUADRATURAS CON SU MONTO DECLARADO CORRESPONDIENTE
     * @author ASOSA
     * @since 29.04.2010
     * @param pTableModel
     * @param pFechaCierre
     * @throws SQLException
     */
    public static void listaCuadraturasCierreDia_02(FarmaTableModel pTableModel,
                                                    String pFechaCierre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierre);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_CUADRATURAS_AS(?,?,?)", parametros,
                                                 false);

    }

    /**
     * Si el indicador de concepto de sobres esta en 'S' y la forma de pago puede ponerse en sobres entonces devuelve 'S' sino 'N'
     * @author ASOSA
     * @since 31.05.2010
     * @param pCodigoFormaPago
     * @return
     * @throws SQLException
     */
    public static String obtenerIndSobresXFPago(String pCodigoFormaPago) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodigoFormaPago.trim());
        log.debug("indProsegur:" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SEGURIDAD.SEG_F_CHAR_IND_SOBRES(?,?,?)",
                                                           parametros).trim();
    }


    /**
     * Metodo para verificar la longitud de una tarjeta de credito
     * @author JQUISPE
     * @since 04.06.2010
     * @param pNroTarj Numero de tarjeta de credito
     * @return
     * @throws SQLException
     */
    public static String validarLongitudTarj(String pNroTarj) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNroTarj);
        log.debug("PTOVENTA_CE.CE_P_VERIFICAR_LONGITUD(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_P_VERIFICAR_LONGITUD(?,?,?)",
                                                           parametros).trim();
    }

    /**
     * Metodo para obtener el pedido a modificar
     * @author JQUISPE
     * @since 07.06.2010
     * @param pNroTarj Numero de tarjeta de credito
     * @return
     * @throws SQLException
     */

    public static void obtienePedidoaCambiar(ArrayList pArrayList, String pNumPedDiario,
                                             String pFecPedVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedDiario);
        parametros.add(pFecPedVta);
        log.debug("PTOVENTA_CE.CE_CAMBIO_FORMA_PAGO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList, "PTOVENTA_CE.CE_CAMBIO_FORMA_PAGO(?,?,?,?)",
                                                          parametros);
    }

    /**
     * GRABA LOS SOBRES PARCIALES Y LOS APRUEBA(GRABA EN FORMA PAGO ENTREGA Y EN CE_SOBRE
     * @author ASOSA
     * @since 03.06.2010
     * @param secMovCaja
     * @throws SQLException
     */
    public static void grabarSobresAutomaticamente(String secMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(secMovCaja.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PARAMETROS grabarSobresAutomaticamente: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE.CE_P_INS_SOBRES_AUTOMATICO(?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * lISTO TODOS LOS SOBRES PARCIALES QUE NO HAN SIDO GRABADOS
     * @author ASOSA
     * @since 07.06.2010
     * @param pTableModel
     * @throws SQLException
     */
    public static void listarSobresParcialesXAprobar(FarmaTableModel pTableModel) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_F_LISTA_SOBRES_PARCIALES(?,?,?)",
                                                 parametros, false);
    }


    /***********************************ASOSA, 26.07.2010**********************************************/

    public static void getSobreDeclarados_02(String pSecMovCaja, ArrayList pListaSobre,
                                             String codsobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        parametros.add(codsobre.trim());
        log.debug("obtener los sobres declarados PTOVENTA_CE_SOBRES.SEG_F_CUR_GET_SOBRES(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaSobre,
                                                          "PTOVENTA_CE_SOBRES.SEG_F_CUR_GET_SOBRES(?,?,?,?)",
                                                          parametros);
    }

    /**
     * obtiene el html a imprimir de los sobrs declarados
     * **/
    public static String getHtmlSobreDeclarados_02(String pSecMovCaja, String pCodSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        parametros.add(pCodSobre.trim());
        log.debug("obtener html a imprimir invocando a PTOVENTA_CE_SOBRES.SEG_F_VAR_IMP_HTML_SOBRES(?,?,?,?)" +
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SOBRES.SEG_F_VAR_IMP_HTML_SOBRES(?,?,?,?)",
                                                           parametros);
    }
    /*
    public static void getSobreDeclarados_03(String pSecMovCaja,
                                          ArrayList pListaSobre)throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pSecMovCaja.trim());
      log.debug("obtener los sobres declarados PTOVENTA_CE_SOBRES.SEG_F_CUR_GET_SOBRES_02(?,?,?)"+parametros);
      FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaSobre,"PTOVENTA_CE_SOBRES.SEG_F_CUR_GET_SOBRES_02(?,?,?)",parametros);
    }
    */

    /**
     * Solo obtiene sobre para reimpresion
     * @author ASOSA
     * @since 04.08.2010
     * @param pSecMovCaja
     * @param pCodSobre
     * @return
     * @throws SQLException
     */
    public static String getHtmlSobreDeclarados_03(String pSecMovCaja, String pCodSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        parametros.add(pCodSobre.trim());
        log.debug("obtener html a imprimir invocando a PTOVENTA_CE_SOBRES.SEG_F_VAR_IMP_HTML_SOBRES_03(?,?,?,?)" +
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SOBRES.SEG_F_VAR_IMP_HTML_SOBRES_03(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Cambiar codigo de remito
     * @author ASOSA
     * @since 09.08.2010
     * @param codremold
     * @param codremnew
     */
    public static void cambiarCodigoRemito(String codremold, String codremnew, String precinto) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codremnew.trim());
        parametros.add(codremold.trim());
        parametros.add(precinto.trim());
        log.debug("CHANGE CODIGO REMITO PTOVENTA_CE_REMITO.CE_P_CAMBIAR_COD_REMITO(?,?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_REMITO.CE_P_CAMBIAR_COD_REMITO(?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Solo obtiene sobre para reimpresion en forma de pago entrega
     * @author ASOSA
     * @since 04.08.2010
     * @param pSecMovCaja
     * @param pCodSobre
     * @return
     * @throws SQLException
     */
    public static String getHtmlSobreDeclarados_04(String pSecMovCaja, String pCodSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        parametros.add(pCodSobre.trim());
        log.debug("obtener html a imprimir invocando a PTOVENTA_CE_SOBRES.SEG_F_VAR_IMP_HTML_SOBRES_04(?,?,?,?)" +
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_SOBRES.SEG_F_VAR_IMP_HTML_SOBRES_04(?,?,?,?)",
                                                           parametros);
    }

    /**
     * Aprueba sobres
     * @author ASOSA
     * @since 11.08.2010
     * @param pSecMovCaja
     * @throws SQLException
     */
    public static void aprobarSobres(String pSecMovCaja, String idUsu, String numSecUsu) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja);
        parametros.add(idUsu.trim());
        parametros.add(numSecUsu.trim());
        log.debug("parametros aprobarSobres: " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_LMR.CE_APROBAR_SOBRES(?,?,?,?,?)", parametros,
                                                 false);
    }

    /**
     * Registrar la cotizacion de competencia en el cierre de turno
     * @author ASOSA
     * @since 12.08.2010
     * @param pCodCuadratura
     * @param pNumeroSec
     * @param pMonMonedaOrig
     * @param pGlosa
     * @param secMovCaja
     * @throws SQLException
     */
    public static void agregaCotizacionCompetenciaCTURNO(String pCodCuadratura, String pNumeroSec,
                                                         String pMonMonedaOrig, String pGlosa,
                                                         String secMovCaja) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCuadratura.trim());
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonMonedaOrig)));
        parametros.add(pNumeroSec.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pGlosa.trim());
        parametros.add(secMovCaja.trim());
        log.debug("agregaCotizacionCompetencia de cierre turno PTOVENTA_CE_ERN.CE_P_INS_CUADRA_COTI_COMP: " +
                  parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CE_ERN.CE_P_INS_CUADRA_COTI_COMP(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    /**
     * Lista los documentos para la cotizacion competencia de turno
     * @author ASOSA
     * @since 12.08.2010
     * @param pTableModel
     * @param codCuadratura
     * @throws SQLException
     */
    public static void listaCotizacionCompetenciaTURNO(FarmaTableModel pTableModel,
                                                       String codCuadra) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codCuadra.trim());
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE.CE_LISTA_COT_COMPET_TURNO(?,?,?)",
                                                 parametros, true);
    }


    /**
     * Leer el indicador de cierre de turno para locales
     * @author JQUISPE
     * @since 20.12.2010
     * @throws SQLException
     */
    public static String getIndicadorCierreCajQuimico() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("parametros PTOVENTA_CE.GET_IND_CIERRE_CAJ_QF: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.GET_IND_CIERRE_CAJ_QF(?,?)", parametros);
    }


    /**
     * Verifico si es un adm del lcoal
     * @author JQUISPE
     * @since 23.12.2010
     * @throws SQLException
     */
    public static String isAdmLocal(String pSecUsu) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecUsu);
        log.debug("parametros PTOVENTA_CE.IS_ADMIN_CAJ_QF: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.IS_ADMIN_CAJ_QF(?,?,?)", parametros);
    }

    /**
     * @author Dubilluz
     * @since  02.05.2012
     * @param pCodRemito
     * @return
     * @throws SQLException
     */
    public static String getDatosRemitoMatricial(String pCodRemito) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodRemito);
        log.debug("parametros PTOVENTA_CE_REMITO.GET_DAT_RMT_MATRICIAL: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_REMITO.GET_DAT_RMT_MATRICIAL(?,?,?)",
                                                           parametros);
    }


    public static String getIndImpreRemitoMatricial() throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("parametros PTOVENTA_CE_REMITO.GET_IND_IMPR_MATRI: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_REMITO.GET_IND_IMPR_MATRI(?)", parametros);
    }

    /**
     * Obtiene datos de las tarjetas registradas para ser comparadas al momento de ser agregadas
     * @author   Luigy Terrazos
     * @since    22/03/2013
     */
    public static String validarTarjeta(String pSecMovCaja, String pCodFormPago, String pCodLote, String pTipMon,
                                        String pCantidad, String pMont, String pMontTol) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pSecMovCaja.trim());
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodFormPago.trim());
        parametros.add(pCodLote.trim());
        parametros.add(pTipMon.trim());
        parametros.add(pCantidad.trim());
        parametros.add(pMont.trim());
        parametros.add(pMontTol.trim());
        log.debug("invocando a PTOVENTA_CE.CE_VALIDAR_MONT_TARJ(?,?,?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE.CE_VALIDAR_MONT_TARJ(?,?,?,?,?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String getMontoMinimoTipCompPagoDia(String pTipComp, String pFechaCierreDia, String pTipMonto,
                                                      String pSerie) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipComp);
        parametros.add(pFechaCierreDia);
        parametros.add(pTipMonto);
        parametros.add(pSerie);
        log.debug("PTOVENTA_CE_LMR.CE_GET_MONTO_COMP(?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_GET_MONTO_COMP(?,?,?,?,?,?)",
                                                           parametros);
    }

    public static String getImagenBoleta() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_GET_IMG_BOLETA(?,?)", parametros);
    }

    public static String getImagenFactura() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CE_LMR.CE_GET_IMG_FACTURA(?,?)", parametros);
    }

    /**
     * Lista diferencia de cuadraturas(Anulaciones pendientes)
     * @author CHUANES
     * @throws SQLException
     */
    public static void cargaHistoricoCierreDia(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_CE_LMR.LISTA_HIST_CIERRE_DIA(?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.LISTA_HIST_CIERRE_DIA(?,?)", parametros,
                                                 false);
    }

    //---@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //////RHERRERA : REMITO MARKET/

    /**
     * métodod indica si el local es market o farma
     * @author Rherrera
     * @since
     * @return
     * @throws SQLException
     */
    public static String getIndTico() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_SOBRE_TICO.GET_IND_TICO(?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_SOBRE_TICO.GET_IND_TICO(?)", parametros);
    }

    /**
     * métodod indica si el local cuenta con market asociados
     * @author Rherrera
     * @since
     * @return
     * @throws SQLException
     */
    public static String getLocalPadre() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        log.info("PTOVENTA_SOBRE_TICO.GET_IND_SIN_MARKET(?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_SOBRE_TICO.GET_IND_SIN_MARKET(?)", parametros);
    }

    /**
     * Sí es un local Market - obtiene la IP de su local FARMA - Padre
     * @author Rherrera
     * @since
     * @return
     * @throws SQLException
     */
    public static String getLocalTico() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_SOBRE_TICO.GET_IP_PADRE(?)", parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_SOBRE_TICO.GET_IP_PADRE(?)", parametros);

    }

    /**
     * Lista de sobres // PRUEBA MARKET - SOBRES
     * @author RHERRERA  28.08.2014
     * @throws SQLException
     */
    public static void obtieneSobrePendientes(ArrayList pListaSobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("obtener todos sobres declarados PTOVENTA_SOBRE_TICO.F_LISTAR_SOBRES(?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaSobre, "PTOVENTA_SOBRE_TICO.F_LISTAR_SOBRES(?,?)",
                                                          parametros);
    }

    /**
     * Inserta sobres al local FARMA - Padre
     * @author RHERRERA  15.09.2014
     * @throws SQLException
     */
    public static void insertSobresTICO(String indConexion, ArrayList arrayList) throws SQLException {
        for (int i = 0; i < arrayList.size(); i++) {
            String FecVta = ((String)((ArrayList)arrayList.get(i)).get(0)).trim();
            String cCodSobre = ((String)((ArrayList)arrayList.get(i)).get(1)).trim();
            String cMoneda = ((String)((ArrayList)arrayList.get(i)).get(2)).trim();
            String cMontoTotal = ((String)((ArrayList)arrayList.get(i)).get(3)).trim();
            String cFormaPago = ((String)((ArrayList)arrayList.get(i)).get(4)).trim();
            String cUsuCrea = ((String)((ArrayList)arrayList.get(i)).get(5)).trim();
            String cMonSol = ((String)((ArrayList)arrayList.get(i)).get(7)).trim();
            String cMonDol = ((String)((ArrayList)arrayList.get(i)).get(8)).trim();
            String cIndSol = ((String)((ArrayList)arrayList.get(i)).get(9)).trim();
            String cIndDol = ((String)((ArrayList)arrayList.get(i)).get(10)).trim();

            parametros = new ArrayList();
            parametros.add(FecVta);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(cCodSobre);
            parametros.add(cMoneda);
            parametros.add(new Double(FarmaUtility.getDecimalNumber(cMontoTotal)));
            parametros.add(cFormaPago);
            parametros.add(cUsuCrea);
            parametros.add("TICO-" + FarmaVariables.vCodLocal);
            parametros.add(new Double(FarmaUtility.getDecimalNumber(cMonSol)));
            parametros.add(new Double(FarmaUtility.getDecimalNumber(cMonDol)));
            parametros.add(cIndSol);
            parametros.add(cIndDol);

            log.info("PTOVENTA.PTOVENTA_SOBRE_TICO.P_INSERT_SOBRE_TICO(?,?,?,?,?,?,?,?" + ",?,?,?,?)" + parametros);
            log.info("IP conexion: " + FarmaVariables.vIpServidorTico);

            FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                           "PTOVENTA.PTOVENTA_SOBRE_TICO.P_INSERT_SOBRE_TICO(?,?,?,?,?,?,?,?" +
                                                           ",?,?,?,?)", parametros, false,
                                                           FarmaConstants.CONECTION_TICO, indConexion);

        }

    }

    /**
     * Desactiva sobre enviado a FARMA - Padre
     * @author RHERRERA  15.09.2014
     * @throws SQLException
     */
    public static String eliminarSobresTICO(String indConexion, String cSobre, String cFecVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(cFecVta.trim());
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cSobre.trim());
        parametros.add(FarmaVariables.vIdUsu.trim());
        log.info("PTOVENTA.PTOVENTA_SOBRE_TICO.P_DELETE_SOBRE(?,?,?,?)" + parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA.PTOVENTA_SOBRE_TICO.P_DELETE_SOBRE(?,?,?,?)",
                                                                 parametros, FarmaConstants.CONECTION_TICO,
                                                                 indConexion);
    }

    /**
     * Obtiene la fecha de creación del sobre como parametro
     * @author RHERRERA  15.09.2014
     * @throws SQLException
     */
    public static String obtenerFecVta(String codsobre) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codsobre.trim());
        log.info("PTOVENTA_SOBRE_TICO.F_OBTENER_FECVTA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_SOBRE_TICO.F_OBTENER_FECVTA(?,?,?)", parametros);
    }

    /**
     * Envia dato de cierre de dia al local Farma
     * @author RHERRERA  15.09.2014
     * @throws SQLException
     */
    public static void insertCierreDiaMarket(String indConexion, String pCierreDiaVenta,
                                             String pSecUsuLogeado) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pCierreDiaVenta);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add("TICO");
        parametros.add(FarmaVariables.vIdUsu);
        log.info("PTOVENTA.PTOVENTA_SOBRE_TICO.P_INSERT_CIERRE_DIA_MARKET(?,?,?,?)" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA.PTOVENTA_SOBRE_TICO.P_INSERT_CIERRE_DIA_MARKET(?,?,?,?)",
                                                       parametros, false, FarmaConstants.CONECTION_TICO, indConexion);
    }

    /**
     * Obtener locales TICO e IP
     * @author RHERRERA  15.09.2014
     * @throws SQLException
     */
    public static void getLocalTicoIP(ArrayList listaTicos) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodLocal);
        log.info("PTOVENTA_SOBRE_TICO.GET_IPS_TICOS(?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(listaTicos, "PTOVENTA_SOBRE_TICO.GET_IPS_TICOS(?)",
                                                          parametros);

    }

    public static void obtieneSobreTicoPendiente(String indConexion, ArrayList pListaSobre,
                                                 String cCodLocal) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(cCodLocal);
        log.debug("obtener todos sobres declarados ptoventa.PTOVENTA_SOBRE_TICO.F_LISTAR_SOBRES(?,?)" + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(pListaSobre,
                                                                "PTOVENTA.PTOVENTA_SOBRE_TICO.F_LISTAR_SOBRES(?,?)",
                                                                parametros, FarmaConstants.CONECTION_TICO,
                                                                indConexion);
    }


    public static void insertSobresTICOPendiente(ArrayList arrayList, String cCodLocal) throws SQLException {
        for (int i = 0; i < arrayList.size(); i++) {
            String FecVta = ((String)((ArrayList)arrayList.get(i)).get(0)).trim();
            String cCodSobre = ((String)((ArrayList)arrayList.get(i)).get(1)).trim();
            String cMoneda = ((String)((ArrayList)arrayList.get(i)).get(2)).trim();
            String cMontoTotal = ((String)((ArrayList)arrayList.get(i)).get(3)).trim();
            String cFormaPago = ((String)((ArrayList)arrayList.get(i)).get(4)).trim();
            String cUsuCrea = ((String)((ArrayList)arrayList.get(i)).get(5)).trim();
            String cMonSol = ((String)((ArrayList)arrayList.get(i)).get(7)).trim();
            String cMonDol = ((String)((ArrayList)arrayList.get(i)).get(8)).trim();
            String cIndSol = ((String)((ArrayList)arrayList.get(i)).get(9)).trim();
            String cIndDol = ((String)((ArrayList)arrayList.get(i)).get(10)).trim();

            parametros = new ArrayList();
            parametros.add(FecVta);
            parametros.add(cCodLocal);
            parametros.add(cCodSobre);
            parametros.add(cMoneda);
            parametros.add(new Double(FarmaUtility.getDecimalNumber(cMontoTotal)));
            parametros.add(cFormaPago);
            parametros.add(cUsuCrea);
            parametros.add("TICO-" + cCodLocal);
            parametros.add(new Double(FarmaUtility.getDecimalNumber(cMonSol)));
            parametros.add(new Double(FarmaUtility.getDecimalNumber(cMonDol)));
            parametros.add(cIndSol);
            parametros.add(cIndDol);
            log.info("PTOVENTA_SOBRE_TICO.P_INSERT_SOBRE_TICO(?,?,?,?,?,?,?,?" + ",?,?,?,?)" + parametros);
            FarmaDBUtility.executeSQLStoredProcedure(null,
                                                     "PTOVENTA_SOBRE_TICO.P_INSERT_SOBRE_TICO(?,?,?,?,?,?,?,?" + ",?,?,?,?)",
                                                     parametros, false);


        }

    }

    public static void updateSobresTico(String indConexion, ArrayList pSobresTico, String remito, String precinto,
                                        String cCodLocal) throws SQLException {
        for (int i = 0; i < pSobresTico.size(); i++) {
            String cCodSobre = ((String)((ArrayList)pSobresTico.get(i)).get(2)).trim();

            String FecVta = ((String)((ArrayList)pSobresTico.get(i)).get(0)).trim();

            parametros = new ArrayList();
            parametros.add(cCodSobre);
            parametros.add(cCodLocal);
            parametros.add(FecVta);
            parametros.add(remito);
            parametros.add(precinto);
            parametros.add(FarmaVariables.vIdUsu.trim());
            log.info("PTOVENTA.PTOVENTA_SOBRE_TICO.P_UPDATE_REMITO(?,?,?,?,?,?)" + parametros);
            FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                           "PTOVENTA.PTOVENTA_SOBRE_TICO.P_UPDATE_REMITO(?,?,?,?,?,?)",
                                                           parametros, false, FarmaConstants.CONECTION_TICO,
                                                           indConexion);
        }

    }

    public static void actualizarProce(String indProceso) throws SQLException {
        parametros = new ArrayList();
        parametros.add(indProceso);
        log.info("PTOVENTA_SOBRE_TICO.P_UPDATE_IND_PROCESO(?)", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_SOBRE_TICO.P_UPDATE_IND_PROCESO(?)", parametros,
                                                 false);

    }

    public static void cargaComprobantesElectronicos(FarmaTableModel pTableModel,
                                                     String pFechaCierreDia) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaCierreDia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_CE_LMR.CE_OBTIENE_RANGO_COMP_ELECT(?,?,?)",
                                                 parametros, false);

    }

}
