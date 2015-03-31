package mifarma.ptoventa.recaudacion;


import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.worker.JDialogProgress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgProcesar;
import mifarma.ptoventa.caja.reference.FacadeCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recaudacion.reference.UtilityRecaudacion;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesarVentaCMR extends JDialogProgress {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesarVentaCMR.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();

    private Frame myParentFrame;
    FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    Long codTrssc = null;
    private String strIndProc = "";

    ArrayList rptSix = null;
    private String strCodAutorizacion = "";
    private String strNumCuotas = "", strNroTarjBruto = "", strNumDocIden = "", strDebitoCredito = "";

    //Anulacion
    Long codTrsscAnul = null;
    ArrayList arrayTmpDatosRCD;
    boolean bRptTrsscAnul = false;

    //Conciliacion
    String strNumTarjeta = "";
    String strMonto = "";
    String strIdAnular = ""; //referencia al COD_AUTORIZACION de pago CMR
    String strFechaRecau = "";
    String strNumPedNegativo = "";
    private String vConciliacionOnline = DlgProcesar.cargaIndConciliaconOnline();

    public DlgProcesarVentaCMR() {
        this(null, "", false);
    }

    public DlgProcesarVentaCMR(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            //jbInit();

        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(238, 104));
        this.getContentPane().setLayout(null);
        this.setTitle("Procesando Pago . . .");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }


    void this_windowOpened(WindowEvent e) {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if (FarmaVariables.vTipCambio == 0) {
            FarmaUtility.showMessage(this,
                                     "ATENCIÓN: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acción",
                                     null);
            cerrarVentana();
        } else {
            FarmaUtility.centrarVentana(this);
            if (ConstantsRecaudacion.RCD_IND_PROCESO_PAGO.equals(strIndProc)) {
                procesarVenta();
            } else if (ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION.equals(strIndProc)) {
                procesarAnulacion();
            }
        }
    }

    private void cerrarVentana() {
        this.setVisible(false);
        this.dispose();
    }


    public void procesarVentaCMR(Frame myParentFrame, String strNumCuotas, String strNroTarjBruto,
                                 String strNumDocIden, String strDebitoCredito) {
        this.myParentFrame = myParentFrame;
        this.strNumCuotas = strNumCuotas;
        this.strNroTarjBruto = strNroTarjBruto;
        this.strNumDocIden = strNumDocIden;
        this.strDebitoCredito = strDebitoCredito;
    }

    private void procesarVenta() {
        try {
            String strCodRecau = "";
            Long codTrssc = null;
            ArrayList rptSix = null;
            //boolean bExitoTrsscCmr = true;
            boolean bRpt;
            boolean bMsj;
            String estTrsscSix = "";
            String strResponseCode = "";
            String strMontoPagar = null;

            String strCodAuditoria = "";
            String strFechaVencCuota = "";
            String strNumPedido = VariablesVentas.vNum_Ped_Vta.substring(2);
            UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
            utilityRecaudacion.initMensajesVentana(this, null, null,
                                                   ConstantsRecaudacion.TIPO_REC_VENTA_CMR); //carga la fecha en ConstantsRecaudacion.FECHA_RCD
            FacadeCaja facadeCaja = new FacadeCaja();
            FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();

            //GRABANDO CABECERA EN RECAUDACION
            ArrayList<Object> arrayCabecera = new ArrayList<Object>();
            /*0*/arrayCabecera.add(FarmaVariables.vCodGrupoCia);
            /*1*/arrayCabecera.add(FarmaVariables.vCodCia);
            /*2*/arrayCabecera.add(FarmaVariables.vCodLocal);
            ///*3*/arrayCabecera.add(txtNroTarjeta.getText().trim());
            /*3*/arrayCabecera.add(strNroTarjBruto);
            /*4*/arrayCabecera.add(ConstantsRecaudacion.TIPO_REC_VENTA_CMR);
            /*5*/arrayCabecera.add(""); // TIPO PAGO //FarmaLoadCVL.getCVLCode(ConstantsRecaudacion.NOM_HASTABLE_CMB_TIPO_PAGO, cmbTipoPago.getSelectedIndex())
            /*6*/arrayCabecera.add(ConstantsRecaudacion.ESTADO_PENDIENTE); // ESTADO
            /*7*/arrayCabecera.add(""); //ESTADO DE CUENTA
            /*8*/arrayCabecera.add(strNumDocIden); //CODIGO DEL CLIENTE
            /*9*/arrayCabecera.add(FarmaConstants.MONEDA_SOLES); //TIPO DE MONEDA
            /*10*/arrayCabecera.add(VariablesCaja.vValMontoPagado); //Monto total(moneda original)
            /*11*/arrayCabecera.add(VariablesCaja.vValMontoPagado); //Monto soles
            /*12*/arrayCabecera.add(VariablesCaja.vValMontoPagado); //Monto minimo
            /*13*/arrayCabecera.add(FarmaVariables.vTipCambio); //Tipo cambio
            /*14*/arrayCabecera.add(""); //Fecha DE VENCIMIENTO DE RECAUDACION
            /*15*/arrayCabecera.add(""); //NOMBRE DEL CLIENTE
            /*16*/arrayCabecera.add(""); //Fecha Venc Tarj (CMR)
            /*17*/arrayCabecera.add(ConstantsRecaudacion.FECHA_RCD); //FECHA DE CREACION
            /*18*/arrayCabecera.add(FarmaVariables.vIdUsu); // USUARIO CREADOR
            /*19*/arrayCabecera.add(""); //FECHA DE MODIFICACION
            /*20*/arrayCabecera.add(""); //USUARIO MODIFICADOR
            /*21*/arrayCabecera.add(""); //codigo de autorizacion
            /*22*/arrayCabecera.add(""); //mov de caja // NO GRABAR ESTE DATO
            /*23*/arrayCabecera.add(VariablesVentas.vNum_Ped_Vta); //numero de pedido solo valido para VENTA CMR
            /*24*/arrayCabecera.add(""); //tipo producto servicio
            /*25*/arrayCabecera.add(""); //numero de recibo de pago
            strCodRecau = facadeRecaudacion.grabaCabeRecau(arrayCabecera);

            //ERIOS 2.3.3 Valida conexion con RAC
            try {
                facadeRecaudacion.validarConexionRAC();
            } catch (Exception e) {
                FarmaUtility.showMessage(this, e.getMessage(), null);
                return;
            }

            codTrssc =
                    facadeCaja.registrarTrsscVentaCMR(strNroTarjBruto, VariablesCaja.vValMontoPagado, strNumPedido, //terminal: Identificamos la transaccion con el numero de pedido
                        FarmaVariables.vDescCortaLocal, // comercio
                        FarmaVariables.vDescCortaDirLocal, //ubicacion
                        strNumCuotas, //(con 1 cuota temporalmente) strNumCuotas, // nro de cuotas
                        FarmaVariables.vNuSecUsu, //id cajero
                        strNumDocIden, // dni
                        FarmaVariables.vIdUsu);
            //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
            if (codTrssc == null) {
                FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                return;
            }
            rptSix =
                    facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_PAGO_SIX_VENTA_CMR,
                                                          codTrssc);

            //GFONSECA 03/12/2013 Indicada si la venta se realizo con CMR
            VariablesCaja.vIndDatosTarjetaCMR = true;

            bRpt = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
            bMsj = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
            strResponseCode = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
            strMontoPagar = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
            strCodAuditoria = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA);
            strCodAutorizacion =
                    (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ); // SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR
            strFechaVencCuota =
                    (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_FECHA_VENC_CUOTA); // solo para venta CMR

            if (ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)) {
                estTrsscSix = ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_CORRECTA;

                //actualiza la cabecera de recaudacion por venta CMR
                facadeRecaudacion.actualizarCabRcdEstadoVentaCMR(strCodRecau, codTrssc, estTrsscSix,
                                                                 strCodAutorizacion,
                                                                 String.valueOf(Integer.parseInt(strNumCuotas)),
                                                                 strFechaVencCuota, strMontoPagar);
                //imprimir
                //ERIOS 30.12.2013 No comentar, ni para desarrollo.
                facadeRecaudacion.imprimirComprobantePagoRecauVentaCMR(strCodRecau,
                                                                       utilityRecaudacion.formatoNroCuotas(strNumCuotas),
                                                                       strMontoPagar.trim(), strFechaVencCuota,
                                                                       strCodAutorizacion);

                FarmaVariables.vAceptar = true;

                //conciliacion
                //ERIOS 2.2.8 Conciliacion offline
                if (vConciliacionOnline.equals(FarmaConstants.INDICADOR_N)) {
                    log.debug("Conciliacilion offline");
                    cerrarVentana();
                    return;
                }

                String PLOCAL = ""; //facadeRecaudacion.getCodLocalMigra();
                String PID_VENDEDOR = ""; //facadeRecaudacion.obtenerDniUsuario(FarmaVariables.vNuSecUsu).trim();
                String PFECHA_VENTA = arrayCabecera.get(17).toString().trim();
                double PMONTO_VENTA = FarmaUtility.getDecimalNumber(arrayCabecera.get(10).toString());
                double PNUM_CUOTAS = FarmaUtility.getDecimalNumber(strNumCuotas); //solo en venta
                String PCOD_AUTORIZACION = strCodAutorizacion;
                String PTRACK2 = ""; //solo en venta
                String PCOD_AUTORIZACION_PRE = ""; //solo en venta cuando es anulacion
                double PFPA_VALORXCUOTA = FarmaUtility.getDecimalNumber(strMontoPagar); //solo en venta
                int PCAJA = Integer.parseInt(VariablesCaja.vNumCaja);
                String PTIPO_TRANSACCION =
                    ConstantsRecaudacion.RCD_MODO_VENTA; //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion  //MAL OK
                String PCODISERV =
                    ""; //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank
                String PFPA_NUM_OBJ_PAGO =
                    strNroTarjBruto; //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
                String PNOMBCLIE = "";
                long PVOUCHER = Long.parseLong(VariablesVentas.vNum_Ped_Vta); //Nro de Comprobante
                long PNRO_COMP_ANU = 0; //Anulacion-Nro de Comprobante origen
                String PFECH_COMP_ANU = ""; //Anulacion-Fecha Origen del Comprobante
                String PCODIAUTOORIG = ""; //Anulacion-Codigo autorizacion Origen
                double PFPA_TIPOCAMBIO = FarmaVariables.vTipCambio;
                long PFPA_NROTRACE =
                    Long.parseLong(UtilityRecaudacion.obtenerNroTraceConciliacion(codTrssc.toString())); //Se envia 4 ultimos digitos de la transaccion registrada
                int PCOD_ALIANZA = Integer.parseInt(ConstantsRecaudacion.RCD_COD_ALIANZA_CMR);
                int PCOD_MONEDA_TRX = Integer.parseInt(strDebitoCredito.trim().equals("Credito") ? "5" : "6");
                String PMON_ESTPAGO = ConstantsRecaudacion.RCD_COD_MONEDA_SOLES;
                String descProceso = "VPC";
                String vSalida =
                    facadeRecaudacion.setDatosRecauConciliacion(PLOCAL, PID_VENDEDOR, PFECHA_VENTA, PMONTO_VENTA,
                                                                PNUM_CUOTAS, PCOD_AUTORIZACION, PTRACK2,
                                                                PCOD_AUTORIZACION_PRE, PFPA_VALORXCUOTA, PCAJA,
                                                                PTIPO_TRANSACCION, PCODISERV, PFPA_NUM_OBJ_PAGO,
                                                                PNOMBCLIE, PVOUCHER, PNRO_COMP_ANU, PFECH_COMP_ANU,
                                                                PCODIAUTOORIG, PFPA_TIPOCAMBIO, PFPA_NROTRACE,
                                                                PCOD_ALIANZA, PCOD_MONEDA_TRX, PMON_ESTPAGO,
                                                                descProceso);
                log.info("Respuesta conciliacion venta CMR: " + vSalida);
            } else {
                //GFONSECA 23.10.2013 Se implementaron mensajes de respuesta.
                if (ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);
                } else if (strResponseCode.equals("91") || strResponseCode.equals("94")) {
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CMR_SERV_INACTIVO, null);
                    //}else if(ConstantsRecaudacion.COD_NRO_DOC_ERRADO.equals(strResponseCode)){
                    //     FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CMR_NRO_DOC_ERRADO, null);
                } else {
                    //ERIOS 2.2.9 Mensaje del operador
                    FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_CMR_VENTA + ":\n" +
                            (String)rptSix.get(12), null);
                }
                FarmaVariables.vAceptar = false;
            }
            cerrarVentana();
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, e.getMessage(), null);
        }

    }

    private void procesarAnulacion() {

        ArrayList rptSix = null;
        boolean bRpt;
        boolean bMsj;
        //String strNumPedido = VariablesCaja.vNumPedVta_Anul;
        String codRecauNegativo = "";

        //Obteniendo datos de la recaudacion venta CMR a eliminar
        //ArrayList arrayTmpDatosRCD = facadeRecaudacion.getDatosAnulacionVentaCMR(strNumPedido);

        if (arrayTmpDatosRCD != null &&
            arrayTmpDatosRCD.size() > 0) { // si el pedido existe en la tabla de cabecera de recaudacion se realiza el proceso de anulacion con el six
            //Datos de respuesta de anulacion con el six
            String strResponseCode = "";
            //String strMontoPagar = "";
            //String strCodAuditoria = "";
            //String strCodAutorizacion = "";

            //Datos de respuesta de recaudacion venta CMR a eliminar
            String strEstTrsscSix = "";
            String strCodRecau = "";
            String strCodTrsscSix = "";
            String strTipoMoneda = "";
            String strCodAutorizacon = "";
            //String strFechaRecau = "";

            //Datos desde ADM
            //String strNumTarjeta = "";
            //String strMonto = "";
            String strAuditoria = "";
            String strTerminal = "";
            String strComercio = "";
            String strUbicacion = "";
            String strNumCuotas = "";
            String strCodSucursal = "";
            String strIdCajero = "";
            String strNroDoc = "";
            String strMotExtorno = "";
            //String strIdAnular = ""; //referencia al COD_AUTORIZACION de pago CMR
            String strUsuario = "";

            strEstTrsscSix = ((ArrayList)arrayTmpDatosRCD.get(0)).get(0).toString();
            strCodRecau = ((ArrayList)arrayTmpDatosRCD.get(0)).get(1).toString();
            strCodTrsscSix = ((ArrayList)arrayTmpDatosRCD.get(0)).get(2).toString();
            strTipoMoneda = ((ArrayList)arrayTmpDatosRCD.get(0)).get(3).toString();
            strCodAutorizacon = ((ArrayList)arrayTmpDatosRCD.get(0)).get(4).toString();
            this.strFechaRecau = ((ArrayList)arrayTmpDatosRCD.get(0)).get(5).toString();

            UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
            utilityRecaudacion.initMensajesVentana(this, null, null,
                                                   ConstantsRecaudacion.TIPO_REC_VENTA_CMR); //carga la fecha en ConstantsRecaudacion.FECHA_RCD

            try {

                //ERIOS 2.3.3 Valida conexion con RAC
                try {
                    facadeRecaudacion.validarConexionRAC();
                } catch (Exception e) {
                    FarmaUtility.showMessage(this, e.getMessage(), null);
                    return;
                }

                //Obteniendo los datos registrados de la operacion venta CMR, para usarlos en su anulacion
                ArrayList arrayTmpDatosSIX = facadeRecaudacion.getDatosAnulacionVentaCMR_SIX(strCodTrsscSix);

                this.strNumTarjeta =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(0).toString(); // (String) arrayTmpDatosSIX.get(0);
                this.strMonto =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(1).toString(); //(String) arrayTmpDatosSIX.get(1);
                strAuditoria =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(2).toString(); //(String) arrayTmpDatosSIX.get(2);
                strTerminal =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(3).toString(); //(String) arrayTmpDatosSIX.get(3);
                strComercio =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(4).toString(); //(String) arrayTmpDatosSIX.get(4);
                strUbicacion =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(5).toString(); //(String) arrayTmpDatosSIX.get(5);
                strNumCuotas =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(6).toString(); //(String) arrayTmpDatosSIX.get(6);
                strCodSucursal =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(7).toString(); //(String) arrayTmpDatosSIX.get(7);
                strIdCajero =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(8).toString(); //(String) arrayTmpDatosSIX.get(8);
                strNroDoc = ((ArrayList)arrayTmpDatosSIX.get(0)).get(9).toString(); //(String) arrayTmpDatosSIX.get(9);
                strMotExtorno =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(10).toString(); //(String) arrayTmpDatosSIX.get(10);
                this.strIdAnular =
                        ((ArrayList)arrayTmpDatosSIX.get(0)).get(11).toString(); //(String) arrayTmpDatosSIX.get(11);
                strUsuario = FarmaVariables.vIdUsu;

                if (ConstantsRecaudacion.RCD_PAGO_SIX_EST_TRSSC_CORRECTA.equals(strEstTrsscSix)) {

                    this.codTrsscAnul =
                            facadeRecaudacion.anularPagoTarjetaVentaCMR(ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200,
                                                                        ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                                                        ConstantsRecaudacion.TRNS_ANU_CMR,
                                                                        ConstantsRecaudacion.TIPO_REC_VENTA_CMR,
                                                                        this.strNumTarjeta.trim(), strMonto.trim(),
                                                                        strTerminal.trim(), strComercio.trim(),
                                                                        strUbicacion.trim(), strNumCuotas.trim(),
                                                                        strIdCajero.trim(), strNroDoc.trim(),
                                                                        strIdAnular.trim(), strUsuario.trim());
                    //GFonseca 21/11/2013 Si falla el insert de la peticion, ya no continua con el pago
                    if (this.codTrsscAnul == null) {
                        FarmaUtility.showMessage(this, "Ocurrio un error al registrar la transacción.", null);
                        return;
                    }
                    rptSix =
                            facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_PAGO_SIX, ConstantsRecaudacion.RCD_PAGO_SIX_CMR,
                                                                  this.codTrsscAnul);

                    bRpt = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
                    bMsj = (Boolean)rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
                    strResponseCode = (String)rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
                    //strMontoPagar = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
                    //strCodAuditoria = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA);
                    //strCodAutorizacion = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);// PARA COMPRA Y VENTA CMR, para anulacion no se toma en cuenta

                    if (ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode)) {
                        //se genera el pedido negativo en recaudacion
                        //OJO: este tipo de recaudacion solo se registra en la cabecera de las recaudaciones, por eso no se puede usar el mismo metodo que se usa
                        //en el resto de recaudaciones, para este caso se creo otro metodo que genere el negativo solo a nivel de cabecera.
                        codRecauNegativo =
                                facadeRecaudacion.anularRecaudacionVentaCMR(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia,
                                                                            FarmaVariables.vCodLocal, strCodRecau,
                                                                            VariablesCaja.vNumCaja,
                                                                            FarmaVariables.vIdUsu, this.codTrsscAnul);

                        //Imprimir voucher de anulacion venta CMR
                        //ERIOS 30.12.2013 No comentar, ni para desarrollo.
                        facadeRecaudacion.imprimirComprobanteAnulRecauVentaCMR(codRecauNegativo);

                        log.info("Se anulo pedido de venta CMR correctamente. Codigo de pedido negativo generado : " +
                                 codRecauNegativo);
                        //FarmaUtility.showMessage(this,"Se anulo el pedido.",null);
                        this.bRptTrsscAnul = true;

                    } else {
                        log.info("No se pudo anular pedido de venta CMR.");
                        if (ConstantsRecaudacion.COD_NO_RESPUESTA.equals(strResponseCode)) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_NO_RESPUESTA, null);
                        } else if (strResponseCode.equals("91")) {
                            FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CMR_SERV_INACTIVO, null);
                        } else {
                            //ERIOS 2.2.9 Mensaje del operador
                            FarmaUtility.showMessage(this, "No se pudo anular el pedido." + ":\n" +
                                    (String)rptSix.get(12), null);
                        }
                    }

                } else {
                    FarmaUtility.showMessage(this,
                                             "No se puede anular el pedido, ya que el pago nó se realizó correctamente.",
                                             null);
                }
                cerrarVentana();
                return;
            } catch (Exception e) {
                log.error("", e);
                FarmaUtility.showMessage(this, e.getMessage(), null);
            }
        }

    }


    public void procesarConciliacionAnul() {
        //ERIOS 2.2.8 Control de errores
        try {

            //ERIOS 2.2.8 Conciliacion offline
            if (vConciliacionOnline.equals(FarmaConstants.INDICADOR_N)) {
                log.debug("Conciliacilion offline");
                cerrarVentana();
                return;
            }

            String PLOCAL = ""; //facadeRecaudacion.getCodLocalMigra();
            String PID_VENDEDOR = ""; //facadeRecaudacion.obtenerDniUsuario(FarmaVariables.vNuSecUsu).trim();
            String PFECHA_VENTA = ConstantsRecaudacion.FECHA_RCD;
            double PMONTO_VENTA = FarmaUtility.getDecimalNumber(strMonto.trim());
            double PNUM_CUOTAS = FarmaUtility.getDecimalNumber(strNumCuotas); //solo en venta
            String PCOD_AUTORIZACION = this.strIdAnular;
            ; // Codigo de autorizacion de la venta  //Codigo autorizacion de la venta.
            String PTRACK2 = "0"; //solo en venta
            String PCOD_AUTORIZACION_PRE = "0"; //solo en venta cuando es anulacion
            double PFPA_VALORXCUOTA = 0; //solo en venta
            int PCAJA = Integer.parseInt(VariablesCaja.vNumCaja);
            String PTIPO_TRANSACCION =
                "3"; //solo en venta 1 Venta y 3 venta Anulacion 8 Pago y 9 Pago Anulacion  //MAL OK
            String PCODISERV =
                "0"; //solo en Recaudacion Pago 02 EstaCta Citibank, 04 Ripley, 07 CMR, 14 Financiero, 15 Claro, 18 Prest Terc. Citibank
            String PFPA_NUM_OBJ_PAGO = strNumTarjeta; //Nro de Celular(nro recibo) o Nro de Tarjeta o Nro de Cuenta
            String PNOMBCLIE = "";
            long PVOUCHER = Long.parseLong(this.strNumPedNegativo); //Nro de Comprobante  //Codigo de pedido negativo.
            long PNRO_COMP_ANU =
                Long.parseLong(VariablesCaja.vNumPedVta_Anul); //Anulacion-Nro de Comprobante origen  //Codigo de pedido a anular.
            String PFECH_COMP_ANU = strFechaRecau; //Anulacion-Fecha Origen del Comprobante
            String PCODIAUTOORIG =
                this.strIdAnular; //Anulacion-Codigo autorizacion Origen   //Codigo autorizacion de la venta.
            double PFPA_TIPOCAMBIO = FarmaVariables.vTipCambio;
            long PFPA_NROTRACE =
                Long.parseLong(UtilityRecaudacion.obtenerNroTraceConciliacion(this.codTrsscAnul.toString())); //Se envia 4 ultimos digitos de la transaccion registrada
            int PCOD_ALIANZA = Integer.parseInt(ConstantsRecaudacion.RCD_COD_ALIANZA_CMR);
            int PCOD_MONEDA_TRX = 0;
            String PMON_ESTPAGO = ConstantsRecaudacion.RCD_COD_MONEDA_SOLES;

            String vSalida =
                facadeRecaudacion.setDatosRecauConciliacion(PLOCAL, PID_VENDEDOR, PFECHA_VENTA, PMONTO_VENTA,
                                                            PNUM_CUOTAS, PCOD_AUTORIZACION, PTRACK2,
                                                            PCOD_AUTORIZACION_PRE, PFPA_VALORXCUOTA, PCAJA,
                                                            PTIPO_TRANSACCION, PCODISERV, PFPA_NUM_OBJ_PAGO, PNOMBCLIE,
                                                            PVOUCHER, PNRO_COMP_ANU, PFECH_COMP_ANU, PCODIAUTOORIG,
                                                            PFPA_TIPOCAMBIO, PFPA_NROTRACE, PCOD_ALIANZA,
                                                            PCOD_MONEDA_TRX, PMON_ESTPAGO, "APC");
            log.info("Respuesta Conciliacion Anulacion venta CMR: " + vSalida);
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al grabar conciliacion.\n" +
                    e.getMessage(), null);
        }
        cerrarVentana();
    }


    public ArrayList getRptSix() {
        return rptSix;
    }

    public void setRptSix(ArrayList rptSix) {
        this.rptSix = rptSix;
    }

    public String getNumAutorizacion() {
        return strCodAutorizacion;
    }

    public String getStrIndProc() {
        return strIndProc;
    }

    public void setStrIndProc(String strIndProc) {
        this.strIndProc = strIndProc;
    }

    public void procesarAnulacionVentaCMR(Frame myParentFrame, ArrayList arrayTmpDatosRCD) {
        this.myParentFrame = myParentFrame;
        this.arrayTmpDatosRCD = arrayTmpDatosRCD;
    }

    public boolean isBRptTrsscAnul() {
        return bRptTrsscAnul;
    }

    public void setBRptTrsscAnul(boolean bRptTrsscAnul) {
        this.bRptTrsscAnul = bRptTrsscAnul;
    }

    public String getStrNumPedNegativo() {
        return strNumPedNegativo;
    }

    public void setStrNumPedNegativo(String strNumPedNegativo) {
        this.strNumPedNegativo = strNumPedNegativo;
    }

    @Override
    public void ejecutaProceso() {
        if (ConstantsRecaudacion.RCD_IND_PROCESO_PAGO.equals(strIndProc)) {
            procesarVenta();
        } else if (ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION.equals(strIndProc)) {
            procesarAnulacion();
        }
    }
}
