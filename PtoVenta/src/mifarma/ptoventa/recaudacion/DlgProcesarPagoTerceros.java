package mifarma.ptoventa.recaudacion;


import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.main.DlgProcesar;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.recaudacion.reference.VariablesRecaudacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesarPagoTerceros extends JDialogProgress {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesarPagoTerceros.class);

    FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();

    private String strIndProc = "";
    private String strDniUsu = "99999999";

    //Pago
    private ArrayList<Object> tmpArrayCabRcd;
    private String strCodRecau = "";
    private FarmaTableModel tableModelDetallePago;
    private JLabel lblVuelto;
    private JTextField txtMontoPagadoDolares;
    private JTextField txtMontoPagado;

    //Anulacion
    private Frame myParentFrame;
    Long codTrsscAnulTemp = null;
    String numTarjeta;
    String numTelefono;
    String codSix;
    String montoPagado;
    String tipoRcdDesc;
    String codRecauAnular;
    String estTrsscSix;
    String tipRcdCod;
    String codMoneda;
    String fechaRecauAnular;
    String codAutorizRecauAnular;
    String fechaOrigen;

    //Consulta claro
    String terminal;
    String nroTelefono;
    String tipProdServ;
    ArrayList<Object> rptSix = null;
    private boolean bProcesarCobro = false;

    private Long codTrssc = null;

    public DlgProcesarPagoTerceros() {
        super();
    }

    public DlgProcesarPagoTerceros(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            //jbInit();

        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void realizarProcesos() {
        FarmaUtility.centrarVentana(this);

        strDniUsu = facadeRecaudacion.obtenerDniUsuario(FarmaVariables.vNuSecUsu);
        //ERIOS 2.2.8 Control de errores	
        try {
            if (ConstantsRecaudacion.RCD_IND_PROCESO_PAGO.equals(strIndProc)) {
                procesarPago();
            } else if (ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION.equals(strIndProc)) {
                procesarAnulacion();
            } else if (ConstantsRecaudacion.RCD_IND_PROCESO_CONSU_CLARO.equals(strIndProc)) {
                this.setTitle("Consultando . . .");
                procesarConsultaClaro();
            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, e.getMessage(), null);
            //ERIOS 2.4.4 Anulacion automatica de recaudacion
            if (bProcesarCobro) {
                if (ConstantsRecaudacion.RCD_IND_PROCESO_PAGO.equals(strIndProc)) {
                    //kmoncada 11.06.2014 anulacion automatica de la recaudacion en casos de error.
                    try {
                        if (!facadeRecaudacion.validarConexionRAC()) {
                            log.info("No se realizo la anulacion de la recaudación porque no existe linea con el RAC.");
                            return;
                        }
                        if (codTrssc != null) {
                            facadeRecaudacion.anularRecaudaciones(codTrssc);
                            facadeRecaudacion.anularRCDPend(strCodRecau, codTrssc);
                        }
                    } catch (Exception ex) {
                        log.error("Error al realizar la anulacion automatica de la recaudacion.", ex);
                    }
                }
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public void procesarPagoTerceros(ArrayList<Object> tmpArrayCabRcd, String strCodRecau,
                                     FarmaTableModel tableModelDetallePago, JLabel lblVuelto,
                                     JTextField txtMontoPagado, JTextField txtMontoPagadoDolares) {
        this.tmpArrayCabRcd = tmpArrayCabRcd;
        this.strCodRecau = strCodRecau;
        this.tableModelDetallePago = tableModelDetallePago;
        this.lblVuelto = lblVuelto;
        this.txtMontoPagadoDolares = txtMontoPagadoDolares;
        this.txtMontoPagado = txtMontoPagado;
    }

    private void procesarPago() throws Exception {
        //Long codTrssc = null;
        codTrssc = null;
        String strTotalPagar = "";
        ArrayList<Object> rptSix = null;
        String estTrsscSix = "";
        String PCOD_AUTORIZACION_TEMP = "";
        String PFPA_NROTRACE_TEMP = "";
        boolean bRpt;
        boolean bMsj;
        String strResponseCode = "";
        String strMontoPagar = "";
        String strCodAutorizacion = tmpArrayCabRcd.get(21).toString();
        String strCodAuditoria = "";
        String strFechaOrigen = "";
        String strEstCta = tmpArrayCabRcd.get(7).toString();
        String strTipoMoneda = tmpArrayCabRcd.get(9).toString();

        String descProceso = "";
        String strTipoRecau = tmpArrayCabRcd.get(4).toString();
        //ERIOS 2.4.1 Indicador de recaudacion centralizada
        int pRecaudOnline = DlgProcesar.cargaIndRecaudacionCentralizada();

        //ERIOS 2.3.3 Valida conexion con RAC
        if (!facadeRecaudacion.validarConexionRAC()) {
            return;
        }

        if (ConstantsRecaudacion.TIPO_REC_CMR.equals(strTipoRecau) ||
            ConstantsRecaudacion.TIPO_REC_CLARO.equals(strTipoRecau) ||
            ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(strTipoRecau)) {


            if (ConstantsRecaudacion.TIPO_REC_CMR.equals(strTipoRecau)) {

                strTotalPagar = VariablesCaja.vValTotalPagar;
                String strNumComp = FarmaVariables.vCodLocal + strCodRecau;
                String nroCuotas = "1";
                descProceso = "RPC";
                double dblTotalPagar = FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
                codTrssc =
                        facadeRecaudacion.registrarTrsscPagoDeudaCMR(tmpArrayCabRcd.get(3).toString(), //numero de tarjeta
                            strTotalPagar, // monto a pagar
                            strCodRecau, //terminal: Identificamos el terminal con el numero de recaudacion
                            FarmaVariables.vDescCortaLocal, // comercio
                            FarmaVariables.vDescCortaDirLocal, //ubicacion
                            nroCuotas, //TODO Uso futuro //cuotas
                            VariablesCaja.vNumCaja, FarmaVariables.vNuSecUsu, FarmaVariables.vIdUsu, strCodRecau,
                            strDniUsu, VariablesRecaudacion.vTipoCambioVenta, strTipoMoneda, dblTotalPagar,
                            pRecaudOnline);
                //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                if (codTrssc == null) {
                    FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                    return;
                }
                rptSix =
                        facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_PAGO_SIX_CMR,
                                                              codTrssc);

            } else if (ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(strTipoRecau)) {
                //FarmaUtility.showMessage(this, "opcion no disponible." , null);
                strTotalPagar = VariablesCaja.vValTotalPagar;
                String strNumComp = FarmaVariables.vCodLocal + strCodRecau;
                String nroCuotas = "1";
                descProceso = "RPR";
                double dblTotalPagar = FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
                codTrssc =
                        facadeRecaudacion.registrarTrsscPagoDeudaRipley(tmpArrayCabRcd.get(3).toString(), //numero de tarjeta
                            strTotalPagar, // monto a pagar
                            strCodRecau, //terminal: Identificamos el terminal con el numero de recaudacion
                            FarmaVariables.vDescCortaLocal, // comercio
                            FarmaVariables.vDescCortaDirLocal, //ubicacion
                            nroCuotas, //TODO Uso futuro //cuotas
                            VariablesCaja.vNumCaja, FarmaVariables.vNuSecUsu, FarmaVariables.vIdUsu, strCodRecau,
                            strDniUsu, VariablesRecaudacion.vTipoCambioVenta, strTipoMoneda, dblTotalPagar,
                            pRecaudOnline);
                //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                if (codTrssc == null) {
                    FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                    return;
                }
                rptSix =
                        facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_PAGO_SIX_RIPLEY,
                                                              codTrssc);

            } else if (ConstantsRecaudacion.TIPO_REC_CLARO.equals(strTipoRecau)) {
                //ERIOS 23.10.2013 Se calcula el monto a abonar
                double dblTotalPagar = FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
                double dblMontoSoles =
                    FarmaUtility.getDecimalNumber(txtMontoPagado.getText()) + (FarmaUtility.getDecimalNumber(txtMontoPagadoDolares.getText()) *
                                                                               FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido));
                if (dblTotalPagar <= dblMontoSoles) {
                    strTotalPagar = FarmaUtility.formatNumber(dblTotalPagar);
                } else {
                    strTotalPagar = FarmaUtility.formatNumber(dblMontoSoles);
                }
                descProceso = "RPS";
                dblTotalPagar = FarmaUtility.getDecimalNumber(strTotalPagar);
                codTrssc =
                        facadeRecaudacion.registrarTrsscPagoDeudaClaro(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia,
                                                                       FarmaVariables.vCodLocal,
                                                                       ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200,
                                                                       ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                       ConstantsRecaudacion.TRNS_PAG_PRE_AUTORI_SRV,
                                                                       //tipo transaccion
                            strTipoRecau, //tipo recaudacion
                            strTotalPagar, //monto
                            strCodRecau, //terminal: Identificamos la transaccion con el numero de recaudacion
                            FarmaVariables.vDescCortaLocal, // comercio
                            FarmaVariables.vDescCortaDirLocal, //ubicacion
                            tmpArrayCabRcd.get(8).toString(), // telefono
                            tmpArrayCabRcd.get(24).toString(), //tipo producto/servicio
                            tmpArrayCabRcd.get(25).toString(),
                            // número de recibo de pago
                            FarmaVariables.vIdUsu, strCodRecau, strDniUsu, VariablesRecaudacion.vTipoCambioVenta,
                            strTipoMoneda, dblTotalPagar, pRecaudOnline);
                //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                if (codTrssc == null) {
                    FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                    return;
                }
                rptSix =
                        facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_PAGO_SIX_CLARO,
                                                              codTrssc);
            }

            bRpt = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
            bMsj = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
            strResponseCode = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
            strMontoPagar = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
            strCodAuditoria = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA);
            strCodAutorizacion =
                    (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ); // SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR
            codTrssc = new Long(rptSix.get(7).toString());
            strFechaOrigen = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_FECHA_ORIG);

            if (ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)) {
                estTrsscSix = ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_CORRECTA;
                PCOD_AUTORIZACION_TEMP = strCodAutorizacion;
                PFPA_NROTRACE_TEMP = UtilityRecaudacion.obtenerNroTraceConciliacion(codTrssc.toString());
                bProcesarCobro = true;
            } else {
                estTrsscSix = ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_FALLIDA;
            }

        } else { //Recaudaciones que no pasan por el six
            double dblTotalPagar = 0.0;
            PFPA_NROTRACE_TEMP = UtilityRecaudacion.obtenerNroTraceConciliacion(strCodRecau);
            if (ConstantsRecaudacion.TIPO_REC_CITI.equals(strTipoRecau)) {
                strCodAutorizacion = FarmaVariables.vCodLocal + strCodRecau;
                dblTotalPagar = FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
                descProceso = "RPT";
            } else if(ConstantsRecaudacion.TIPO_REC_RAIZ.equals(strTipoRecau)){ //ASOSA - 06/08/2015 - RAIZ
                dblTotalPagar = FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
                descProceso = "RRA";
                pRecaudOnline = 1;  //TODOS LOS PAGOS VAN A RAC PARA RAIZ
            }else {
                dblTotalPagar = FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
                descProceso = "RPP";
            }
            bProcesarCobro = true;

            //ERIOS 2.4.0 Recaudacon Citibank centralizada
            codTrssc = facadeRecaudacion.registrarTrsscPagoCitibank(strTipoRecau, //tipo recaudacion
                        dblTotalPagar, //monto
                        strCodRecau, //terminal: Identificamos la transaccion con el numero de recaudacion
                        tmpArrayCabRcd.get(8).toString(), // cod cliente
                        tmpArrayCabRcd.get(3).toString(), //numero de tarjeta
                        strCodAutorizacion, //codigoautorizacion
                        FarmaVariables.vIdUsu, strDniUsu, VariablesRecaudacion.vTipoCambioVenta, strTipoMoneda,
                        pRecaudOnline);
            //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
            if (codTrssc == null) {
                FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                return;
            }
        }

        if (bProcesarCobro) {

            //Grabar forma de pago Recaudacion
            ArrayList tmpArray = new ArrayList();
            ArrayList<Object> tmpColm = null;
            ArrayList<ArrayList<Object>> arrayFormasPago = new ArrayList<>();
            tmpArray = tableModelDetallePago.data;
            String vCodMoneda = "";
            double vTipoCambio = 0.00;
            String vMontoPagado = "0.00";
            String vTotalPagado = "0.00";
            for (int i = 0; i < tmpArray.size(); i++) {
                //ERIOS 22.11.2013 Tipo cambio compra
                vCodMoneda = (((ArrayList)tmpArray.get(i)).get(6)).toString();
                vMontoPagado = (((ArrayList)tmpArray.get(i)).get(4)).toString();
                vTipoCambio = VariablesRecaudacion.vTipoCambioVenta;
                vTotalPagado = "0.00";
                if (strEstCta.equals(ConstantsRecaudacion.EST_CTA_DOLARES) &&
                    vCodMoneda.equals(ConstantsCaja.EFECTIVO_SOLES)) {
                    vTipoCambio = VariablesRecaudacion.vTipoCambioCompra;
                }

                if (vCodMoneda.equals(ConstantsCaja.EFECTIVO_SOLES)) {
                    vTotalPagado = vMontoPagado;
                } else {
                    vTotalPagado =
                            FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(vMontoPagado) * vTipoCambio);
                }

                tmpColm = new ArrayList<>();
                tmpColm.add(FarmaVariables.vCodGrupoCia);
                tmpColm.add(FarmaVariables.vCodCia);
                tmpColm.add(FarmaVariables.vCodLocal);
                tmpColm.add(strCodRecau);
                tmpColm.add(((ArrayList)tmpArray.get(i)).get(0));
                tmpColm.add(((ArrayList)tmpArray.get(i)).get(4)); //MONTO PAGADO    cImp_Total_in
                tmpColm.add(vCodMoneda); //MONEDA
                tmpColm.add(vTipoCambio);
                tmpColm.add(((ArrayList)tmpArray.get(i)).get(7)); //VUELTO
                tmpColm.add(vTotalPagado); //TOTAL PAGADO  cIm_Total_Pago_in (total soles)
                tmpColm.add(obtenerFecha());
                tmpColm.add(FarmaVariables.vIdUsu);
                tmpColm.add(""); //12
                tmpColm.add(""); //13
                arrayFormasPago.add(tmpColm);
            }

            boolean bCobro =
                facadeRecaudacion.grabaFormPagoRecau(arrayFormasPago, lblVuelto.getText().trim(), strCodAutorizacion,
                                                     codTrssc,
                                                     ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(strTipoRecau) ||
                                                     ConstantsRecaudacion.TIPO_REC_CMR.equals(strTipoRecau) ||
                                                     ConstantsRecaudacion.TIPO_REC_CLARO.equals(strTipoRecau) ?
                                                     estTrsscSix : "", strFechaOrigen);

            if (bCobro) {
                //GFonseca 26.06.2013 Se agrega logica para actualizar el monto pagado, para el caso de prestamos citibank.
                String strMontoCobrado = "";
                String strMontoMonedaCobrado = "";
                if (ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(strTipoRecau) ||
                    ConstantsRecaudacion.TIPO_REC_RAIZ.equals(strTipoRecau) || //ASOSA - 06/08/2015 - RAIZ
                    ConstantsRecaudacion.TIPO_REC_CLARO.equals(strTipoRecau) ||
                    strEstCta.equals(ConstantsRecaudacion.EST_CTA_DOLARES)) {
                    ArrayList<String> aMontoCobrado = facadeRecaudacion.actualizarMontoCobradoPresCiti(strCodRecau);
                    strMontoCobrado = aMontoCobrado.get(0);
                    strMontoMonedaCobrado = aMontoCobrado.get(1);
                }

                //Imprimir
                facadeRecaudacion.imprimirComprobantePagoRecaudacion(strCodRecau);

                //Abrir Gabeta
                UtilityCaja.abrirGabeta(myParentFrame, false);

                FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_EXITO, null);

            }
        } else {

            if (ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);
            } else if (strResponseCode.equals("91") || strResponseCode.equals("94")) {
                if (ConstantsRecaudacion.TIPO_REC_CLARO.equals(strTipoRecau)) {
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CLARO_SERV_INACTIVO, null);
                } else if (ConstantsRecaudacion.TIPO_REC_CMR.equals(strTipoRecau)) {
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CMR_SERV_INACTIVO, null);
                } else {
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_FALLIDO, null);
                }
            } else if (!ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                //ERIOS 2.2.9 Mensaje del operador
                FarmaUtility.showMessage(this, "Mensaje Operador" + ":\n" +
                        (String)rptSix.get(12), null);
            } else {
                FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_FALLIDO, null);
            }
        }
        cerrarVentana(true);

    }

    public void procesarAnulacionTerceros(Frame myParentFrame, String numTarjeta, String numTelefono, String codSix,
                                          String montoPagado, String tipoRcdDesc, String codRecauAnular,
                                          String estTrsscSix, String tipRcdCod, String codMoneda,
                                          String fechaRecauAnular, String codAutorizRecauAnular, String fechaOrigen) {
        this.myParentFrame = myParentFrame;
        this.numTarjeta = numTarjeta;
        this.numTelefono = numTelefono;
        this.codSix = codSix;
        this.montoPagado = montoPagado;
        this.tipoRcdDesc = tipoRcdDesc;
        this.codRecauAnular = codRecauAnular;
        this.estTrsscSix = estTrsscSix;
        this.tipRcdCod = tipRcdCod;
        this.codMoneda = codMoneda;
        this.fechaRecauAnular = fechaRecauAnular;
        this.codAutorizRecauAnular = codAutorizRecauAnular;
        this.fechaOrigen = fechaOrigen;
    }


    public void procesarAnulacion() throws Exception {

        String codRecauNegativo = "";

        //VARIABLES PARA LA CONCILIACION
        //ERIOS 2.3.1 Conciliacion offline

        //String descProceso = "";

        codRecauNegativo = facadeRecaudacion.getCodRecauAnul();
        if (codRecauNegativo.equals("")) {
            FarmaUtility.showMessage(this, "Hubo un error al recuperar codigo de anulacion.", null);
            return;
        }

        double dblMontoPagado = FarmaUtility.getDecimalNumber(montoPagado);

        //ERIOS 2.4.1 Indicador de recaudacion centralizada
        int pRecaudOnline = DlgProcesar.cargaIndRecaudacionCentralizada();

        //ERIOS 2.3.3 Valida conexion con RAC
        if (!facadeRecaudacion.validarConexionRAC()) {
            return;
        }

        //Long codTrssc = null;
        codTrssc = null;
        if (ConstantsRecaudacion.TIPO_REC_CMR.equals(tipRcdCod) ||
            ConstantsRecaudacion.TIPO_REC_CLARO.equals(tipRcdCod) ||
            ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(tipRcdCod)) {


            ArrayList<Object> rptSix = null;
            boolean bRpt;
            boolean bMsj;
            String strResponseCode = "";
            String strMontoPagar = "";
            String strCodAutorizacionSix = "";
            String strCodAuditoria = "";
            String arrayDatosTrssc[] = new String[2];
            String srtEstTrssc = "";
            String tmpEst =
                facadeRecaudacion.obtenerEstadoTrssc(new Long(codSix), ConstantsRecaudacion.RCD_MODO_CONSULTA_SIX);
            arrayDatosTrssc = tmpEst.split(",");
            srtEstTrssc = arrayDatosTrssc[0].trim(); //Estado OK / FA

            if (ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_CORRECTA.equals(srtEstTrssc)) {
                if (tipRcdCod.equals(ConstantsRecaudacion.TIPO_REC_CMR)) {
                    String numCuota = "1";
                    String motivoExtorno = "85";
                    codTrssc =
                            facadeRecaudacion.anularPagoTarjetaCMR(ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                   ConstantsRecaudacion.TRNS_ANU_PAG_TARJ,
                                                                   ConstantsRecaudacion.TIPO_REC_CMR, numTarjeta,
                                                                   numCuota, montoPagado, VariablesCaja.vNumCaja,
                                                                   FarmaVariables.vNuSecUsu, motivoExtorno,
                                                                   codAutorizRecauAnular, codRecauAnular,
                                                                   FarmaVariables.vDescCortaLocal,
                                                                   FarmaVariables.vDescCortaDirLocal,
                                                                   FarmaVariables.vIdUsu, codRecauNegativo, strDniUsu,
                                                                   VariablesRecaudacion.vTipoCambioVenta, codMoneda,
                                                                   codRecauAnular,
                                                                   //Anulacion-Nro de Comprobante origen
                                fechaRecauAnular, //Anulacion-Fecha Origen del Comprobante
                                codAutorizRecauAnular, dblMontoPagado, pRecaudOnline);
                    //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                    if (codTrssc == null) {
                        FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                        return;
                    }
                    rptSix =
                            facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_ANU_PAGO_SIX_CMR,
                                                                  codTrssc);
                    bRpt = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                    bMsj = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                    strResponseCode = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                    strMontoPagar = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                    strCodAuditoria = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA);
                    strCodAutorizacionSix =
                            (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ); // SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR

                    if (bRpt && ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)) {
                        //se genera el pedido negativo
                        facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja,
                                                            FarmaVariables.vIdUsu, codTrssc, codRecauNegativo);

                        //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion
                        if (!codRecauNegativo.equals("")) {
                            facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
                        }

                        //Abrir Gabeta
                        UtilityCaja.abrirGabeta(myParentFrame, false);

                        FarmaUtility.showMessage(this, "La recaudación fue anulada.", null);

                    } else {
                        if (ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);
                        } else if (ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CMR_SERV_INACTIVO, null);
                        } else {
                            FarmaUtility.showMessage(this, "La recaudación no se pudo anular.", null);
                        }
                    }
                } //fin CMR
                else if (tipRcdCod.equals(ConstantsRecaudacion.TIPO_REC_CLARO)) {
                    codTrssc =
                            facadeRecaudacion.anularPagoServicioCLARO(codSix, FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia,
                                                                      FarmaVariables.vCodLocal,
                                                                      ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200,
                                                                      ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                      ConstantsRecaudacion.TRNS_ANU_PAG_SRV,
                                                                      ConstantsRecaudacion.TIPO_REC_CLARO, montoPagado,
                                                                      codRecauAnular, FarmaVariables.vDescCortaLocal,
                                                                      //comercio
                                FarmaVariables.vDescCortaDirLocal, FarmaVariables.vNuSecUsu, FarmaVariables.vIdUsu,
                                codRecauNegativo, strDniUsu, VariablesRecaudacion.vTipoCambioVenta, codMoneda,
                                codRecauAnular, //Anulacion-Nro de Comprobante origen
                                fechaRecauAnular, //Anulacion-Fecha Origen del Comprobante
                                codAutorizRecauAnular, dblMontoPagado, pRecaudOnline);
                    //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                    if (codTrssc == null) {
                        FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                        return;
                    }
                    rptSix =
                            facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_ANU_PAGO_SIX_CLARO,
                                                                  codTrssc);

                    bRpt = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                    bMsj = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                    strResponseCode = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                    strMontoPagar = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                    strCodAuditoria = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA);
                    strCodAutorizacionSix =
                            (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ); // SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR

                    if (ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)) {
                        //se genera el pedido negativo
                        facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja,
                                                            FarmaVariables.vIdUsu, codTrssc, codRecauNegativo);

                        //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion
                        if (!codRecauNegativo.equals("")) {
                            facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
                        }
                        FarmaUtility.showMessage(this, "La recaudación fue anulada.", null);
                    } else {
                        if (ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);
                        } else if (strResponseCode.equals("91") || strResponseCode.equals("94")) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CLARO_SERV_INACTIVO, null);
                        } else {
                            //ERIOS 2.2.9 Mensaje del operador
                            FarmaUtility.showMessage(this, "Mensaje Operador" + ":\n" +
                                    (String)rptSix.get(12), null);
                        }
                    }
                } //fin claro
                else if (tipRcdCod.equals(ConstantsRecaudacion.TIPO_REC_RIPLEY)) {
                    String numCuota = "1";
                    //String motivoExtorno="85";
                    codTrssc = facadeRecaudacion.anularPagoTarjetaRipley(codSix, numTarjeta, numCuota, montoPagado,
                                //motivoExtorno,
                                codAutorizRecauAnular, fechaOrigen, codRecauAnular, codRecauNegativo, strDniUsu,
                                VariablesRecaudacion.vTipoCambioVenta, codMoneda, codRecauAnular,
                                //Anulacion-Nro de Comprobante origen
                                fechaRecauAnular, //Anulacion-Fecha Origen del Comprobante
                                codAutorizRecauAnular, dblMontoPagado, pRecaudOnline);
                    //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                    if (codTrssc == null) {
                        FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                        return;
                    }
                    rptSix =
                            facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_ANU_PAGO_SIX_CMR,
                                                                  codTrssc);
                    bRpt = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                    bMsj = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                    strResponseCode = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                    strMontoPagar = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                    strCodAuditoria = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA);
                    strCodAutorizacionSix =
                            (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ); // SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR

                    if (bRpt && ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)) {
                        //se genera el pedido negativo
                        facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja,
                                                            FarmaVariables.vIdUsu, codTrssc, codRecauNegativo);

                        //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion
                        if (!codRecauNegativo.equals("")) {
                            facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
                        }
                        FarmaUtility.showMessage(this, "La recaudación fue anulada.", null);
                    } else {
                        if (ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);
                        } else if (ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_RIPLEY_SERV_INACTIVO, null);
                        } else {
                            FarmaUtility.showMessage(this, "La recaudación no se pudo anular.", null);
                        }
                    }
                } //fin Ripley
            } else {
                FarmaUtility.showMessage(this, "No se pudo anular la recaudación, intente nuevamente.", null);
            }

        } else { //Recaudaciones que no pasan por el six

            //ERIOS 2.4.0 Recaudacon Citibank centralizada            
            String strCodAutorizacion = FarmaVariables.vCodLocal + codRecauNegativo;
            //INI ASOSA - 11/08/2015 - RAIZ
            if (tipRcdCod.equals(ConstantsRecaudacion.TIPO_REC_RAIZ)) {
                pRecaudOnline = 1;
            }
            //FIN ASOSA - 11/08/2015 - RAIZ
            codTrssc = facadeRecaudacion.anularTrsscPagoCitibank(tipRcdCod, //tipo recaudacion
                        dblMontoPagado, //monto
                        codRecauNegativo, //terminal: Identificamos la transaccion con el numero de recaudacion
                        numTelefono, // cod cliente
                        numTarjeta, //numero de tarjeta
                        strCodAutorizacion, //codigoautorizacion
                        FarmaVariables.vIdUsu, strDniUsu, VariablesRecaudacion.vTipoCambioVenta, codMoneda,
                        codRecauAnular, //Anulacion-Nro de Comprobante origen
                        fechaRecauAnular, //Anulacion-Fecha Origen del Comprobante
                        codAutorizRecauAnular, pRecaudOnline);

            //se genera el pedido negativo
            facadeRecaudacion.anularRecaudacion(codRecauAnular, VariablesCaja.vNumCaja, FarmaVariables.vIdUsu,
                                                codTrssc, codRecauNegativo);

            //GFONSECA 27.10.2013 Imprimir anulacion de recaudacion
            if (!codRecauNegativo.equals("")) {
                facadeRecaudacion.imprimirComprobanteAnulRecaudacion(codRecauNegativo);
            }
            FarmaUtility.showMessage(this, "La recaudación fue anulada.", null);
        }

        cerrarVentana(true);
    }


    public void procesarConsultaClaro(String terminal, String nroTelefono, String tipProdServ) {
        this.terminal = terminal;
        this.nroTelefono = nroTelefono;
        this.tipProdServ = tipProdServ;
    }


    public void procesarConsultaClaro() throws Exception {
        //Long codTrssc = null;
        codTrssc = null;
        //ERIOS 2.3.3 Valida conexion con RAC
        try {
            facadeRecaudacion.validarConexionRAC();
        } catch (Exception e) {
            FarmaUtility.showMessage(this, e.getMessage(), null);
            return;
        }

        codTrssc = facadeRecaudacion.registrarTrsscConsultaDeudaClaro(terminal, nroTelefono, tipProdServ);
        //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con la consulta
        if (codTrssc == null) {
            FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
            return;
        }
        setRptSix(facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_CONSULTA_SIX,
                                                        ConstantsRecaudacion.RCD_CONSULTA_PAGO_SIX_CLARO, codTrssc));
        cerrarVentana(true);
    }


    public String obtenerFecha() {
        String fechaSys = "";
        try {
            fechaSys = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener la fecha y hora. \n " + sql.getMessage(), null);
        }
        return fechaSys;
    }

    public String obtenerObjetoPago() {
        String codObjePago = "";
        String strTipoRecau = tmpArrayCabRcd.get(4).toString();
        if (ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(strTipoRecau) ||
            ConstantsRecaudacion.TIPO_REC_CMR.equals(strTipoRecau) ||
            ConstantsRecaudacion.TIPO_REC_CITI.equals(strTipoRecau)) {
            codObjePago = tmpArrayCabRcd.get(3).toString(); //nro tarjeta
        } else if (ConstantsRecaudacion.TIPO_REC_CLARO.equals(strTipoRecau) ||
                   ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(strTipoRecau)) {
            codObjePago = tmpArrayCabRcd.get(8).toString(); //codigo de recibo o de cliente
        }
        return codObjePago;
    }


    public String getStrIndProc() {
        return strIndProc;
    }

    public void setStrIndProc(String strIndProc) {
        this.strIndProc = strIndProc;
    }

    public ArrayList<Object> getRptSix() {
        return rptSix;
    }

    public void setRptSix(ArrayList<Object> rptSix) {
        this.rptSix = rptSix;
    }

    @Override
    public void ejecutaProceso() {
        realizarProcesos();
    }
}
