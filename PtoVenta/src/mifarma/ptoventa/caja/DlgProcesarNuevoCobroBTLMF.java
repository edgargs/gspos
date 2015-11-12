package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityEposTrx;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.UtilityRecargaVirtual;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.FacadeConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.delivery.reference.HiloActualizarProforma;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.lealtad.reference.FacadeLealtad;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProcesarNuevoCobroBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.10.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class DlgProcesarNuevoCobroBTLMF extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesarNuevoCobroBTLMF.class);

    private Frame myParentFrame;
    //private boolean vValorProceso;
    private JTable tblFormasPago;
    private JLabel lblVuelto;
    private JTable tblDetallePago;
    private JTextField txtNroPedido;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private String indCommitBefore = "";

    private boolean indUpdateEnConvenio = false;

    private boolean indFinalizaCobro = false;
    private boolean indAnularPedido = false;
    //RHERRERA: cobro DB
    private boolean indCobroConvBD = false;

    private FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
    private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();

    public DlgProcesarNuevoCobroBTLMF() {
        this(null, "", false);
    }

    /**
     * Constructor con parametros.
     * @param parent
     * @param title
     * @param modal
     */
    public DlgProcesarNuevoCobroBTLMF(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgProcesarNuevoCobroBTLMF(Frame parent, String title, boolean modal, JTable pTableModel, JLabel pVuelto,
                                      JTable pDetallePago, JTextField pNroPedido) {
        super(parent, title, modal);
        myParentFrame = parent;
        tblFormasPago = pTableModel;
        lblVuelto = pVuelto;
        tblDetallePago = pDetallePago;
        txtNroPedido = pNroPedido;

        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(238, 104));
        this.getContentPane().setLayout(null);
        this.setTitle("Procesando Pedido Convenio . . .");
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
        boolean vRetorno = false;
        FarmaUtility.centrarVentana(this);
        //RHERRERA
        if (indCobroConvBD) {
            vRetorno = finalizaBD_conv();
        } //----//
        else if (indFinalizaCobro) {
            vRetorno = finalizaCobro();
        } else if (indAnularPedido) {
            vRetorno = anulaPedido();
        } else {
            vRetorno = procesar();
        }
        cerrarVentana(vRetorno);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean procesar() {

        if (!UtilityCaja.existeStockPedido(this, VariablesCaja.vNumPedVta))
            return false;

        //INICIO DE VALIDACIONES
        if (!UtilityCaja.validacionesCobroPedido(false, this, tblFormasPago))
            return false;

        if (!UtilityCaja.validaCajaAbierta(this))
            return false;

        //INICIO PROCESO DE COBRO
        try {
            //verificar si es pedido por convenio
            String pIndPedConvenio = DBCaja.getIndPedConvenio(VariablesCaja.vNumPedVta);
            VariablesVentas.vEsPedidoConvenio = (pIndPedConvenio.equals("S")) ? true : false;

            //obtiene la descrip de la formas de pago para la impresion
            formasPagoImpresion();
            //actualiza datos del cliente como nombre direccion ruc, etc
            if (!VariablesVentas.vCod_Cli_Local.equalsIgnoreCase("")) {
                actualizaClientePedido(VariablesCaja.vNumPedVta, VariablesVentas.vCod_Cli_Local);
            }
            /**LTAVARA
         * COPIA CODIGO DE REGISTRAR FORMAS DE PAGO
         * 22.09.2014
         * */
            //obtiene el monto del vuelto
            String vueltoPedido = lblVuelto.getText().trim();
            colocaVueltoDetallePago(vueltoPedido);

            //detalle de formas de pago
            VariablesCaja.vDescripcionDetalleFormasPago = "";
            VariablesCaja.vDescripcionDetalleFormasPago = ConstantsCaja.COLUMNAS_DETALLE_FORMA_PAGO;
            for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
                //grabar forma de pago del pedido

                //descripcion de la forma de pago en el detalle

                DBCaja.grabaFormaPagoPedido(((String)tblDetallePago.getValueAt(i, 0)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 4)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 6)).trim(),
                                            VariablesCaja.vValTipoCambioPedido,
                                            ((String)tblDetallePago.getValueAt(i, 7)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 5)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 8)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 9)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 10)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 2)).trim(), 
                                            ((String)tblDetallePago.getValueAt(i, 12)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 13)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 14)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 15)).trim());

                VariablesCaja.vDescripcionDetalleFormasPago =
                        VariablesCaja.vDescripcionDetalleFormasPago + FarmaUtility.getValueFieldJTable(tblDetallePago,
                                                                                                       i, 0) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 1) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 3) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 4) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 5) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 7) + "<BR>";
            }
            //LTAVARA

            String resultado = "";
            String resMatriz = "";

            //JMELGAR Valida linea de credito
            boolean esComprobanteCredito = false;
            //-if (Double.parseDouble(VariablesCaja.vValMontoPagado)>0)
            // kmoncada en el caso de venta institucional considerar el monto a pagar como monto de credito.
            if (ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL.equalsIgnoreCase(VariablesVentas.vTipoPedido)) {
                VariablesCaja.vValMontoCredito = VariablesCaja.vValTotalPagar;
            }
            if (FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoCredito) > 0) {
                esComprobanteCredito = true;
            }
            
            //ERIOS 26.03.2015 Recalcular puntos acumulados
            //UtilityPuntos.calculaPuntos(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, VariablesVentas.vNum_Ped_Vta, true, true);
            /*
            //2.6 ERIOS 13.02.2015 Registrar venta lealtad
            FacadeLealtad facadeLealtad = new FacadeLealtad();
            if(!facadeLealtad.registrarVenta(this,VariablesCaja.vNumPedVta)){
                return false;
            }*/

            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1") || esComprobanteCredito) {
                String indConexionRac = "N";
                try {
                    indConexionRac = (facadeRecaudacion.validarConexionRAC()) ? "S" : "N";
                } catch (Exception e) {
                    ;
                }
                if (indConexionRac.equals("S")) {
                    //cobrar pedido DEVOLVERA EXITO. si cobro correctamente
                    resultado = DBConvenioBTLMF.cobraPedido();
                    log.debug("Confirmacion del cobro convenio: " + resultado);
                    if (resultado.trim().equalsIgnoreCase("EXITO")) {

                        // 1.VALIDACIONES EN EL RAC Y SEPARA SALDO


                        List lista = new ArrayList();
                        lista = DBConvenioBTLMF.listaDocumentosVerificaRAC();

                        //CHUANES 31.03.2014
                        //EXTRAEMOS DE LOS DATOS ADICIONALES EL NOMBRE DEL BENEFICIARIO
                        boolean validado = true;
                        for (int i = 0; i < lista.size(); i++) {
                            Map datosAdicConv = (Map)lista.get(i);
                            String pCodLocal = (String)datosAdicConv.get("COD_LOCAL");
                            String pNumPedVta = (String)datosAdicConv.get("NUM_PED_VTA");
                            String pCodConvenio = (String)datosAdicConv.get("COD_CONVENIO");
                            String pCodCliente = (String)datosAdicConv.get("COD_CLIENTE");
                            String pTipoDoc = (String)datosAdicConv.get("TIPO_DOC");
                            double pMonto = FarmaUtility.getDecimalNumber(((String)datosAdicConv.get("MONTO")).trim());
                            String pVtaFin = (String)datosAdicConv.get("VTAFIN");
                            validado =
                                    validado && DBConvenioBTLMF.validacionPedidoRAC(pCodLocal, pNumPedVta, pCodConvenio,
                                                                                    pCodCliente, pTipoDoc, pMonto,
                                                                                    pVtaFin);
                        }

                        log.info(validado + "");

                        if (validado) { // 2. EPOS
                            /*
                            * @author LTAVARA
                            * Genera el Numero de comprobante electrónico
                            * @since 30.09.22
                            * */
                            if (UtilityEposTrx.validacionEmiteElectronico()) {
                                //obtiene los comprobantes del pedido
                                if (UtilityCaja.obtieneCompPago()) {
                                    for (int j = 0; j < VariablesVentas.vArray_ListaComprobante.size(); j++) {
                                        //obtener  secComprobante por documento
                                        VariablesConvenioBTLMF.vSecCompPago =
                                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(1)).trim();
                                        VariablesConvenioBTLMF.vTipoCompPago =
                                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(2)).trim();
                                        VariablesConvenioBTLMF.vTipClienConvenio =
                                                ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(3)).trim();
                                        // obtener el numero del comprobante electrónico
                                        VariablesCaja.vNumCompImprimir =
                                                UtilityEposTrx.getNumCompPagoE(VariablesConvenioBTLMF.vTipoCompPago,
                                                                               VariablesCaja.vNumPedVta,
                                                                               VariablesConvenioBTLMF.vSecCompPago,
                                                                               VariablesConvenioBTLMF.vTipClienConvenio,
                                                                               esComprobanteCredito);
                                        // KMONCADA 20.10.2014 VALIDACION DE DUPLICIDAD DE COMPROBANTES ELECTRONICOS
                                        // DUPLICADO EN LOCAL
                                        /*if("L".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                            throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya fue asignado en el local, comuniquese con Sistemas...!!");
                                        }
                                        // DUPLICADO EN RAC
                                        if("R".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                            throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya se encuentra registrado en RAC, comuniquese con Sistemas...!!");
                                        }

                                        //mensaje de error
                                        if ( VariablesCaja.vNumCompImprimir.startsWith("E")){
                                            throw new SQLException(VariablesCaja.vNumCompImprimir+"\n El pedido NO fue cobrado...!!");
                                        }*/
                                    }
                                }
                            }
                            //fin LTAVARA  validacion electronica


                            //commit  en el rac de las validaciones

                            DBConvenioBTLMF.aceptarTransaccionRempota(null, "S");
                            log.info("confirma validacion el rac");
                            // commit en el local
                            FarmaUtility.aceptarTransaccion();
                            log.info("graba cobro de pedido en el local");
                            resMatriz = "S";
                            // 3. RAC

                            //ERIOS 2.4.4 Graba conexion directa
                            /* resMatriz = facadeConvenioBTLMF.grabarTemporalesRAC(VariablesCaja.vNumPedVta);
                            if (resMatriz.equals("S")) {
                                DBConvenioBTLMF.grabarCobrarPedidoRac(FarmaConstants.INDICADOR_S);
                                DBConvenioBTLMF.actualizaFechaProcesoRac();
                            }else{
                                throw new Exception("No se grabaron los temporales en el RAC.");
                            } */
                            // REALIZAMOS EL COBRO EN RAC CON HILO
                            HiloGrabaRac hilo =
                                new HiloGrabaRac(VariablesCaja.vNumPedVta, FarmaVariables.vCodLocal, FarmaVariables.vCodGrupoCia);
                            hilo.start();
                        }
                    }
                } else {
                    FarmaUtility.showMessage(this, "No puede procesar el cobro. \n" +
                            "Porque no hay una Conexión a la Base de Datos del RAC. \n" +
                            "", tblFormasPago);
                    resultado = "ERROR";
                }
            } else {
                log.debug("cobraPedido.cobraPedido");
                resultado = DBConvenioBTLMF.cobraPedido();
                log.debug("cobraPedido.cobraPedido " + resultado);
            }

            if (resultado.trim().equalsIgnoreCase("EXITO")) {

                /*
                * @author LTAVARA
                * SI EL PROCESO DE ELECTRONICO ESTA ACTIVO TODOS LOS COMPROBANTES GENERADOS SE DEBE ACTUALIZAR EL FLAG ELECTRONICO A '1'
                * Para el caso del convenio que no es al credito
                * @since  19.09.2014
                **/
                if (UtilityEposTrx.validacionEmiteElectronico()) {
                    // ACTUALIZA TODOS LOS COMPROBANTES DEL PEDIDO MENOS LA GUIA
                    UtilityEposTrx.actualizarFlagComprobanteElectronico(VariablesCaja.vNumPedVta);
                }


                /*
                * @author LTAVARA
                * Validar si la variable COMPROBANTE ELECTRONICO esta activo, para genera el Numero de comprobante e
                * @since 22.09.2014
                * */

                //LTAVARA COPIA CODIGO
                if (!(VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1") || esComprobanteCredito)) {
                    if (UtilityEposTrx.validacionEmiteElectronico()) {

                        //obtiene los comprobantes del pedido
                        if (UtilityCaja.obtieneCompPago()) {
                            for (int j = 0; j < VariablesVentas.vArray_ListaComprobante.size(); j++) {
                                //obtener  secComprobante por documento
                                VariablesConvenioBTLMF.vSecCompPago =
                                        ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(1)).trim();
                                VariablesConvenioBTLMF.vTipoCompPago =
                                        ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(2)).trim();
                                VariablesConvenioBTLMF.vTipClienConvenio =
                                        ((String)((ArrayList)VariablesVentas.vArray_ListaComprobante.get(j)).get(3)).trim();
                                // obtener el numero del comprobante electrónico
                                VariablesCaja.vNumCompImprimir =
                                        UtilityEposTrx.getNumCompPagoE(VariablesConvenioBTLMF.vTipoCompPago,
                                                                       VariablesCaja.vNumPedVta,
                                                                       VariablesConvenioBTLMF.vSecCompPago,
                                                                       VariablesConvenioBTLMF.vTipClienConvenio,
                                                                       esComprobanteCredito);
                                // KMONCADA 20.10.2014 VALIDACION DE DUPLICIDAD DE COMPROBANTES ELECTRONICOS
                                // DUPLICADO EN LOCAL
                                /* if("L".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)){
                                   throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya fue asignado en el local, comuniquese con Sistemas...!!");
                               }
                               // DUPLICADO EN RAC
                               if("R".equalsIgnoreCase(VariablesCaja.vNumCompImpr imir)){
                                   throw new Exception("El número de comprobante electronico "+VariablesCaja.vNumCompImprimir.substring(1)+" \n ya se encuentra registrado en RAC, comuniquese con Sistemas...!!");
                               }


                               //mensaje de error
                               if ( VariablesCaja.vNumCompImprimir.startsWith("E")){
                                      throw new SQLException(VariablesCaja.vNumCompImprimir+"\n El pedido fue cobrado...!!");
                                   }*/

                            }

                        }
                    }
                } //fin validacion electronica


                if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)) {
                    FarmaUtility.liberarTransaccion();
                    return false;
                }
                VariablesCaja.vIndPedidoCobrado = true;

                try {
                    if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                        DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta, "F");
                        FarmaUtility.aceptarTransaccion();
                    } else {
                        DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta, "F");
                        FarmaUtility.aceptarTransaccion();
                    }
                    
                    /** KMONCADA 17.07.2015 SE MODIFICA EL ORDEN DEL ENVIO DE PEDIDO CON PUNTOS YA QUE DEBE HACERLO LUEGO DE REALIZAR EL COBRO **/
                    //2.6 ERIOS 13.02.2015 Registrar venta lealtad
                    FacadeLealtad facadeLealtad = new FacadeLealtad();
                    if(!facadeLealtad.registrarVenta(this,VariablesCaja.vNumPedVta)){
                       // return false;
                    }

                } catch (SQLException sql) {
                    log.error("", sql);
                    if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                        FarmaUtility.liberarTransaccion();
                    } else {
                        FarmaUtility.liberarTransaccion();
                    }
                }

                //ERIOS 10.10.2013 Parte de finalizar cobro
                //UtilityConvenioBTLMF.procesoImpresionComprobante(this, null);

            } else if (resultado.trim().equalsIgnoreCase("ERROR")) {
                if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                    FarmaUtility.liberarTransaccion();

                } else {
                    FarmaUtility.liberarTransaccion();
                }

                VariablesCaja.vIndPedidoCobrado = false;
                FarmaUtility.showMessage(this, "El pedido no puede ser cobrado. \n" +
                        "Los totales de formas de pago y cabecera no coinciden. \n" +
                        "Comuníquese con el Operador de Sistemas inmediatamente." + ". \n" +
                        "NO CIERRE LA VENTANA.", tblFormasPago);
            }

            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                if (!resMatriz.equals("S")) {
                    FarmaUtility.liberarTransaccion();
                    VariablesCaja.vIndPedidoCobrado = false;
                    FarmaUtility.showMessage(this, "Error al grabar datos en Matriz", tblFormasPago);

                }
            }

        } catch (SQLException sql) {
            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1")) {
                FarmaConnectionRemoto.closeConnection();
                FarmaUtility.liberarTransaccion();
            } else {
                FarmaUtility.liberarTransaccion();
            }

            log.error(null, sql);
            String pMensaje = sql.getMessage();
            int nIsSecCajaNull = pMensaje.indexOf("CHECK_SEC_MOV_CAJA");

            if (nIsSecCajaNull > 0) {
                FarmaUtility.showMessage(this, "No se pudo cobrar el pedido.\nInténtelo nuevamente", tblFormasPago);
            } else {
                VariablesCaja.vIndPedidoCobrado = false;
                // KMONCADA 18.09.2014 CONTROL DE ERRORES EN EL RAC.
                switch (sql.getErrorCode()) {
                case 20850: // LINEA DE CREDITO
                    FarmaUtility.showMessage(this,
                                             "El cliente no cuenta con crédito suficiente para realizar esta venta. Comuníquese con Créditos y Cobranzas",
                                             null);
                    break;
                case 20851: // DOCUMENTOS DUPLICADOS
                    FarmaUtility.showMessage(this, "Documento ya se encuentra registrado en RAC: \n" +
                            sql.getMessage().substring(sql.getMessage().indexOf("- CIA:"),
                                                       sql.getMessage().indexOf("ORA-00001:")) +
                            "\nComuniquese con Sistemas.", null);
                case 20852: // CLIENTE SIN CODIGO SAP
                    FarmaUtility.showMessage(this, "Cliente no cuenta con codigo SAP asignado en RAC.\n" +
                            sql.getMessage().substring(sql.getMessage().indexOf("RUC/DNI"),
                                                       sql.getMessage().indexOf("ORA-06512: en \"MEDCO.FACTURAC_GENERA_ASIENTO\"")).replace("RAZ.SOC",
                                                                                                                                            "\nRAZ.SOC"),
                            null);
                    break;
                case 20853: // CLIENTE NO CUENTA CON LINEA DE CREDITO EN CONVENIO MIXTO
                    FarmaUtility.showMessage(this, "Cliente no cuenta con Línea de Crédito\n" +
                            "Agregue una Forma de Pago en Efectivo y/o Tarjeta.\n" +
                            "o Comuníquese con Créditos y Cobranzas.\n", null);
                    break;
                default:
                    UtilityPtoVenta.mensajeErrorBd(this, "Error en BD al cobrar el Pedido.\n" +
                            sql.getMessage(), tblFormasPago);
                }
                /*if (sql.getErrorCode() > 20000) {
                    VariablesCaja.vIndPedidoCobrado = false;
                    FarmaUtility.showMessage(this,
                                             sql.getMessage().substring(10, sql.getMessage().indexOf("ORA-06512")),
                                             null);
                } else {*/

                //}
            }
        } catch (Exception ex) { //error inesperado        	
            log.error("", ex);
            if (indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                if (VariablesCaja.vIndPedidoConProdVirtual) {
                    //evalua indicador de impresion por error
                    String vIndImpre = "N";
                    try {
                        vIndImpre = DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);
                    } catch (Exception e) {
                        vIndImpre = "N";
                    }

                    if (!vIndImpre.equals("N")) {
                        /*
    			       * Validacion de Fecha actual del sistema contra
    			       * la fecha del cajero que cobrara
    			       * Se añadio para validar pedido Cobrado
    			       * despues de una fecha establecida al inicio
    			       * dubilluz 04.03.2009
    			       **/
                        if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)) {
                            FarmaUtility.liberarTransaccion();
                            return false;
                        }

                        //**
                        //JMIRANDA 24.06.2010
                        //VALIDAR SI HIZO UPDATE DAR COMMIT
                        if (VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                            FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0) {
                            String vIndLineaMat =
                                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);

                            if (((indUpdateEnConvenio) && VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
                                vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                FarmaUtility.aceptarTransaccion();
                                if (VariablesCaja.vIndCommitRemota) {
                                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                          FarmaConstants.INDICADOR_N);
                                }
                            } else {
                                //DUBILLUZ 25.06.2010
                                VariablesCaja.vIndPedidoCobrado = false;

                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                      FarmaConstants.INDICADOR_N);
                                FarmaUtility.showMessage(this,
                                                         "Error al actualizar el uso del Convenio.\nComuníquese con el Operador de Sistemas.",
                                                         null);
                                return false;
                            }
                        } else {
                            FarmaUtility.aceptarTransaccion();
                            if (VariablesCaja.vIndCommitRemota) {
                                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                      FarmaConstants.INDICADOR_N);
                            }
                        }
                        //**
                        /*if(VariablesCaja.vIndCommitRemota){
    					  //FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
    				  }*/
                        UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                        UtilityCaja.obtieneInfoVendedor();

                        try {
                            DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta, "F");
                            FarmaUtility.aceptarTransaccion();
                        } catch (SQLException sql) {
                            log.error("", sql);
                            FarmaUtility.liberarTransaccion();
                        }
                        //ERIOS 10.10.2013 Parte de finalizar cobro
                        /*UtilityCaja.procesoImpresionComprobante(this,
                                                                txtNroPedido);*/
                        FarmaUtility.showMessage(this, "Error en Aplicacion al cobrar el Pedido.\n" +
                                ex.getMessage(), tblFormasPago);

                        FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito", tblFormasPago);
                    } else {
                        FarmaUtility.liberarTransaccion();
                        if (VariablesCaja.vIndCommitRemota) {
                            log.debug("jcallo: liberando transaccione remota");
                            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                  FarmaConstants.INDICADOR_N);
                        }
                        FarmaUtility.showMessage(this, "Error en Aplicacion al cobrar el Pedido.\n" +
                                ex.getMessage(), tblFormasPago);

                    }
                }
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error en Aplicacion al cobrar el Pedido.\n" +
                        ex.getMessage(), tblFormasPago);
            }

            //no se pudo cobrar el pedido
            VariablesCaja.vIndPedidoCobrado = false;

        } finally {

            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            FarmaConnectionRemoto.closeConnection(); //dentro metodo solo cierra si estuvo abierta alguna conexion

        }

        return true;
    }


    private void colocaVueltoDetallePago(String pVuelto) {
        if (tblDetallePago.getRowCount() <= 0) {
            return;
        }
        boolean existeSoles = false;
        boolean existeDolares = false;
        int filaSoles = 0;
        int filaDolares = 0;
        for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
            if (((String)tblDetallePago.getValueAt(i,
                                                   0)).trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {
                existeSoles = true;
                filaSoles = i;
                break;
            } else if (((String)tblDetallePago.getValueAt(i,
                                                          0)).trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES)) {
                existeDolares = true;
                filaDolares = i;
            }
        }
        if (existeSoles) {
            tblDetallePago.setValueAt(pVuelto, filaSoles, 7);
        } else if (existeDolares && !existeSoles) {
            tblDetallePago.setValueAt(pVuelto, filaDolares, 7);
        }
        tblDetallePago.repaint();
    }

    /**
     * descripcion de las formas de pago
     * para la impresion
     */
    private void formasPagoImpresion() {
        //varificar que haya al menos una forma de pago declarado
        if (tblDetallePago.getRowCount() <= 0) {
            VariablesCaja.vFormasPagoImpresion = "";
            return;
        }
        //obtiene la descripcion de las formas de pago para la impresion
        for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
            if (i == 0) {
                VariablesCaja.vFormasPagoImpresion = ((String)tblDetallePago.getValueAt(i, 1)).trim();
            } else {
                VariablesCaja.vFormasPagoImpresion += ", " + ((String)tblDetallePago.getValueAt(i, 1)).trim();
            }
        }

        String codFormaPagoDolares = getCodFormaPagoDolares();
        String codFormaPago = "";
        if (codFormaPagoDolares.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            log.debug("La Forma de Pago Dolares esta Inactiva");
        } else {
            for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
                codFormaPago = ((String)tblDetallePago.getValueAt(i, 0)).trim();
                if (codFormaPagoDolares.equalsIgnoreCase(codFormaPago))
                    VariablesCaja.vFormasPagoImpresion =
                            VariablesCaja.vFormasPagoImpresion + "  Tipo Cambio:  " + VariablesCaja.vValTipoCambioPedido;
            }
        }
    }

    private void actualizaClientePedido(String pNumPedVta, String pCodCliLocal) throws SQLException {
        DBCaja.actualizaClientePedido(pNumPedVta, pCodCliLocal, VariablesVentas.vNom_Cli_Ped,
                                      VariablesVentas.vDir_Cli_Ped, VariablesVentas.vRuc_Cli_Ped);
    }

    private void procesaPedidoVirtual() throws Exception {
        UtilityRecargaVirtual.obtieneInfoPedidoVirtual(); // ASOSA - 13/07/2014
        //obtieneInfoPedidoVirtual();
        if (VariablesVirtual.vArrayList_InfoProdVirtual.size() != 1) {
            throw new Exception("Error al validar info del pedido virtual");
        }
        UtilityRecargaVirtual.colocaInfoPedidoVirtualGrabar();
        try {
            UtilityCaja.procesaVentaProductoVirtual(this, VariablesVirtual.vArrayList_InfoProdVirtual);
        } catch (Exception ex) {
            throw new Exception("Error al procesar el pedido virtual - \n" +
                    ex.getMessage());

        }
        /*
     * Se grabara la respuesta obtenida por el proveedor al realizar la
     * recarga virtual
     */
        DBCaja.grabaRespuestaRecargaVirtual(VariablesVirtual.respuestaTXBean.getCodigoRespuesta(),
                                            VariablesCaja.vNumPedVta);

        if (!validaCodigoRespuestaTransaccion()) {
            throw new Exception("Error al realizar la transaccion con el proveedor.\n" +
                    VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + " - " +
                    VariablesVirtual.respuestaTXBean.getDescripcion());
        }
    }

    private void evaluaMsjVentaVirtualGenerado(String pTipoProdVirtual) {
        if (ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA.equalsIgnoreCase(pTipoProdVirtual))
            muestraTarjetaVirtualGenerado();
        else if (ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA.equalsIgnoreCase(pTipoProdVirtual)) {
            if (VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL))
                FarmaUtility.showMessage(this, "La recarga automática se realizó satisfactoriamente.", null);
            else
                FarmaUtility.showMessage(this, "Verifique en su módulo de consulta la confirmación de la recargas\n" +
                        "No se pudo Obtener la respuesta del proveedor por lentitud en conexión.", null);

        }
    }

    /**
     * Obtiene el codFormaPago Dolares
     * @author dubilluz
     * @since  13.10.2007
     */
    public String getCodFormaPagoDolares() {
        String codFP = "";
        try {
            codFP = DBCaja.getCodFPDolares();
        } catch (SQLException ex) {
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al Obtener el codidgo de Forma de Pago Dolares.\n" +
                    ex.getMessage(), tblFormasPago);
        }
        log.debug("jcallo: codforma de pago dolares " + codFP);
        return codFP;
    }


    private boolean validaCodigoRespuestaTransaccion() {
        boolean result = false;
        if (VariablesVirtual.vCodigoRespuesta != null) {
            log.debug("VariablesVirtual.vCodigoRespuesta - " + VariablesVirtual.vCodigoRespuesta);
            if (VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL) ||
                VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_COBRA_REVISE_EST_TAR_VIRTUAL))
                result = true;

            log.debug("validaCodigoRespuestaTransaccion result - " + result);
        }
        return result;
    }


    private void muestraTarjetaVirtualGenerado() {
        DlgNumeroTarjetaGenerado dlgNumeroTarjetaGenerado = new DlgNumeroTarjetaGenerado(myParentFrame, "", true);
        dlgNumeroTarjetaGenerado.setVisible(true);
        FarmaVariables.vAceptar = false;
    }

    /**
     * Obti
     * @return
     */
    private String getIndCommitAntesRecargar() {
        String ind;
        try {
            ind = DBCaja.obtieneIndCommitAntesdeRecargar();
            log.debug("ind Impr Antes de Recargar" + ind);
        } catch (SQLException sql) {
            ind = "N";
            log.error("", sql);
        }

        return ind.trim();
    }

    private void ejecutaRecargaVirtual() throws Exception {
        procesaPedidoVirtual();
        log.debug("VariablesVirtual.vCodigoRespuesta 2" + VariablesVirtual.vCodigoRespuesta);
        log.debug("**** graba la respuesta obtenida... ** ");

    }

    /**
     * Se generan los cupones por pedido luego de ser cobrados
     * @author JCORTEZ
     * @since 03.07.2008
     * */
    private boolean generarPedidoCupon(String NumPed) {
        boolean valor = false;
        try {
            DBCaja.generarCuponPedido(NumPed);
            valor = true;
            log.debug("Se generaron los cupones del pedido :" + NumPed);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Se genero un error al generar los cupones\n" +
                    sql.getMessage(), tblFormasPago);
        }
        return valor;
    }


    /**
     * Actualiza los cupones en Matriz
     * @author Diego Ubilluz
     * @param pNumPedVta
     */
    private void actualizaCuponesEnMatriz(String pNumPedVta, ArrayList pListaCuponesPedido, String pIndLinea) {

        ArrayList listCupones = new ArrayList();
        listCupones = (ArrayList)pListaCuponesPedido.clone();
        String vIndLineaBD = "";
        String vCodCupon = "";
        String vResActMatriz = "";
        boolean vActCupon = false;
        int COL_COD_CUPON = 0;
        int COL_COD_FECHA_INI = 1;
        int COL_COD_FECHA_FIN = 2;
        String vEstCuponMatriz = "";
        String vRetorno = "";
        String vFechIni = "";
        String vFechFin = "";
        String indMultiUso = "";
        try {
            if (listCupones.size() > 0) {
                if (listCupones.size() == 1) {
                    String vValor = "";
                    vValor = FarmaUtility.getValueFieldArrayList(listCupones, 0, COL_COD_CUPON);
                    if (vValor.equalsIgnoreCase("NULO")) {
                        return;
                    }
                }

                //  2. Se verificara si hay linea con matriz
                //--El segundo parametro indica si se cerrara la conexion
                vIndLineaBD = pIndLinea;
                //SE ESTA FORZANDO QUE NO HAYA LINEA
                vIndLineaBD = FarmaConstants.INDICADOR_N;
                if (vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    log.debug("No existe linea se cerrara la conexion ...");
                    FarmaConnectionRemoto.closeConnection();
                }

                // 3. SE ACTUALIZA EL CUPON
                for (int i = 0; i < listCupones.size(); i++) {
                    vCodCupon = FarmaUtility.getValueFieldArrayList(listCupones, i, COL_COD_CUPON);
                    vFechIni = FarmaUtility.getValueFieldArrayList(listCupones, i, COL_COD_FECHA_INI);

                    vFechFin = FarmaUtility.getValueFieldArrayList(listCupones, i, COL_COD_FECHA_FIN);

                    indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta, vCodCupon).trim();

                    if (indMultiUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                        log.debug("Actualiza en local  ...");
                        DBCaja.actualizaCuponGeneral(vCodCupon, ConstantsCaja.CONSULTA_ACTUALIZA_CUPON_LOCAL);


                        vActCupon = true;
                        //Si hay linea se actualizara en matriz
                        /*
                         * if (vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {


                            vEstCuponMatriz =
                                    DBCaja.getEstadoCuponEnMatriz(vCodCupon,
                                                                  FarmaConstants.INDICADOR_N).trim();

                            //--Si valor de retorno es "0" es porque el cupon
                            //  no existe asi que se creara en Matriz
                            if (vEstCuponMatriz.equalsIgnoreCase("0")) {
                                log.debug("Se graba el cupon en Matriz");
                                vRetorno =
                                        DBCaja.grabaCuponEnMatriz(vCodCupon,
                                                                  vFechIni,
                                                                  vFechFin,
                                                                  FarmaConstants.INDICADOR_N).trim();
                            }

                            log.debug("Actualiza en matriz  ...");

                            vResActMatriz =
                                    DBCaja.actualizaEstadoCuponEnMatriz(vCodCupon,
                                                                        ConstantsCaja.CUPONES_USADOS,
                                                                        FarmaConstants.INDICADOR_N);



                            //--Si la actualizacion se realizo con exito se actualiza
                            //  en el local que el cupon ya se proceso en Matriz
                            if (vRetorno.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                DBCaja.actualizaCuponGeneral(vCodCupon.trim(),
                                                             ConstantsCaja.CONSULTA_ACTUALIZA_MATRIZ);
                            }

                            VariablesCaja.vIndCommitRemota = true;
                            log.debug("Fin de actualizacion");

                        }
                        */
                    } else
                        log.debug("Es un cupon multiuso..");

                }
            }
        } catch (SQLException e) {

            FarmaUtility.liberarTransaccion();
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
        }
        /*
        * Validacion de Fecha actual del sistema contra
        * la fecha del cajero que cobrara
        * Se añadio para validar pedido Cobrado
        * despues de una fecha establecida al inicio
        * dubilluz 04.03.2009
        **/
        log.debug("antes de validar");
        if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)) {
            FarmaUtility.liberarTransaccion();
            return;
        }
        if (vActCupon)
            FarmaUtility.aceptarTransaccion();

        if (VariablesCaja.vIndCommitRemota)
            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
    }

    /**
     * Valida el uso de cupones
     * @author dubilluz
     * @since  20.08.2008
     */
    private boolean validaUsoCupones(String pNumPedVta, String pIndCloseConecction, ArrayList pListaCuponesPedido,
                                     String pIndLinea) {
        log.debug("validando uso de cupones");

        ArrayList listCupones = new ArrayList();
        ArrayList ltDatosCupon = new ArrayList();
        String vCodCupon = "";
        String indMultiUso = "";
        String vIndLineaBD = "";
        String valida = "";
        String vEstCupon = "";
        boolean retorno = false;
        try {
            listCupones = (ArrayList)pListaCuponesPedido.clone();
            log.debug("listCupones " + listCupones);
            // 1. SE VERIFICA SI EL VALOR DE LA LISTA NO FUE NULO
            if (listCupones.size() > 0) {
                if (listCupones.size() == 1) {
                    String vValor = "";
                    vValor = FarmaUtility.getValueFieldArrayList(listCupones, 0, 0);
                    if (vValor.equalsIgnoreCase("NULO")) {
                        retorno = true;
                        return retorno;
                    }
                }

                //  2. Se verificara si hay linea con matriz
                //--El segundo parametro indica si se cerrara la conexion
                vIndLineaBD = pIndLinea.trim();

                // 3. SE VALIDARA CADA CUPON
                for (int i = 0; i < listCupones.size(); i++) {
                    vCodCupon = FarmaUtility.getValueFieldArrayList(listCupones, i, 0);
                    indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta, vCodCupon).trim();

                    //Se valida el Cupon en el local
                    //Modificado por DVELIZ 04.10.08
                    DBVentas.verificaCupon(vCodCupon, ltDatosCupon, indMultiUso, VariablesFidelizacion.vDniCliente, pNumPedVta);

                    //Se validara el cupon en matriz si hay linea
                    /*
                   if(vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                      log.debug("Validando en matriz  ...");
                      valida = DBVentas.verificaCuponMatriz(vCodCupon,indMultiUso,
                                                            FarmaConstants.INDICADOR_N);
                      log.debug("");
                      if(!valida.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                       if(valida.trim().equalsIgnoreCase("B"))
                         {
                           retorno = false;
                           FarmaUtility.showMessage(this,"La campaña no es valida.",tblFormasPago);
                           break;
                         }else
                         {
                           retorno = false;
                           break;
                         }
                      }
                   }
                   */
                    vEstCupon = DBCaja.getEstCuponBloqueo(pNumPedVta, vCodCupon).trim();
                    log.debug("Se bloquea el estado .. " + vEstCupon);

                }
            }

            retorno = true;


        } catch (SQLException e) {
            //cierra la conexion
            FarmaConnectionRemoto.closeConnection();
            retorno = false;
            log.error("", e);
            log.error(null, e);
            switch (e.getErrorCode()) {
            case 20003:
                FarmaUtility.showMessage(this, "La campaña no es valida.", tblFormasPago);
                break;
            case 20004:
                FarmaUtility.showMessage(this, "Local no valido para el uso del cupon.", tblFormasPago);
                break;
            case 20005:
                FarmaUtility.showMessage(this, "Local de emisión no valido.", tblFormasPago);
                break;
            case 20006:
                FarmaUtility.showMessage(this, "Local de emisión no es local de venta.", tblFormasPago);
                break;
            case 20007:
                FarmaUtility.showMessage(this, "Cupón ya fue usado.", tblFormasPago);
                break;
            case 20008:
                FarmaUtility.showMessage(this, "Cupón esta anulado.", tblFormasPago);
                break;
            case 20009:
                FarmaUtility.showMessage(this, "Campaña no valido.", tblFormasPago);
                break;
            case 20010:
                FarmaUtility.showMessage(this, "Cupon no esta vigente .", tblFormasPago);
                break;
            default:
                FarmaUtility.showMessage(this, "Error al validar el cupon.\n" +
                        e.getMessage(), tblFormasPago);
                break;

            }

        }
        log.debug("**FIN**");
        return retorno;
    }

    /**
     * metodo encargado de obtener el dni del cliente fidelizado que realizo la compra
     *
     * */
    private String obtenerDniClienteFidelizado(String nroPedido) {
        String dniClienteFid = "";
        try {
            dniClienteFid = DBCaja.obtieneDniClienteFidVenta(nroPedido).trim();
        } catch (Exception e) {
            dniClienteFid = "";
            log.error("",e);
        }

        return dniClienteFid;
    }


    /**
     * obtener todas las campañas de fidelizacion automaticas usados en el pedido
     *
     * */
    private ArrayList getCampAutomaticasPedido(String nroPedido) {
        ArrayList listaCampAutomaticas = new ArrayList();
        try {
            listaCampAutomaticas = DBCaja.getListaCampAutomaticasVta(nroPedido);
            if (listaCampAutomaticas.size() > 0) {
                listaCampAutomaticas = (ArrayList)listaCampAutomaticas.get(0);
            }
            log.debug("listaCampAutomaticas listaCampAutomaticas ===> " + listaCampAutomaticas);
        } catch (Exception e) {
            log.error("",e);
        }
        return listaCampAutomaticas;
    }

    /**
     * obtener todas las campañas de fidelizacion automaticas usados en el pedido
     *
     * */
    private ArrayList CampLimitadasUsadosDeLocalXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosLocal = new ArrayList();
        try {
            listaCampLimitUsadosLocal = DBCaja.getListaCampUsadosLocalXCliente(dniCliente);
            if (listaCampLimitUsadosLocal.size() > 0) {
                listaCampLimitUsadosLocal = (ArrayList)listaCampLimitUsadosLocal.get(0);
            }
            log.debug("listaCampLimitUsadosLocal listaCampLimitUsadosLocal ===> " + listaCampLimitUsadosLocal);
        } catch (Exception e) {
            log.error("",e);
        }
        return listaCampLimitUsadosLocal;
    }

    /**
     * obtener todas las campañas de fidelizacion automaticas usados en el pedido
     *
     * */
    private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            if (listaCampLimitUsadosMatriz.size() > 0) {
                listaCampLimitUsadosMatriz = (ArrayList)listaCampLimitUsadosMatriz.get(0);
            }
            log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> " + listaCampLimitUsadosMatriz);
        } catch (Exception e) {
            log.error("",e);
        }
        return listaCampLimitUsadosMatriz;
    }

    private boolean finalizaCobro() {
        //1. Procesa pedido virtual

        //2. Actualiza forma de pago
        try {
            /*LTAVARA 22.09.2014
           * //obtiene el monto del vuelto
            String vueltoPedido = lblVuelto.getText().trim();
            colocaVueltoDetallePago(vueltoPedido);

            //detalle de formas de pago
            VariablesCaja.vDescripcionDetalleFormasPago = "";
            VariablesCaja.vDescripcionDetalleFormasPago = ConstantsCaja.COLUMNAS_DETALLE_FORMA_PAGO;
            for (int i = 0; i < tblDetallePago.getRowCount(); i++)
            {
                    //grabar forma de pago del pedido

                //descripcion de la forma de pago en el detalle

                    DBCaja.grabaFormaPagoPedido(((String) tblDetallePago.getValueAt(i,0)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,4)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,6)).trim(),
                                           VariablesCaja.vValTipoCambioPedido,
                                           ((String) tblDetallePago.getValueAt(i,7)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,5)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,8)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,9)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,10)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,2)).trim(),

                                           ((String) tblDetallePago.getValueAt(i,12)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,13)).trim(),
                                           ((String) tblDetallePago.getValueAt(i,14)).trim());

                VariablesCaja.vDescripcionDetalleFormasPago = VariablesCaja.vDescripcionDetalleFormasPago +
                                                                                                          FarmaUtility.getValueFieldJTable(tblDetallePago, i, 0) + " , " +
                                                                                                          FarmaUtility.getValueFieldJTable(tblDetallePago, i, 1) + " , " +
                                                                                                          FarmaUtility.getValueFieldJTable(tblDetallePago, i, 3) + " , " +
                                                                                                          FarmaUtility.getValueFieldJTable(tblDetallePago, i, 4) + " , " +
                                                                                                          FarmaUtility.getValueFieldJTable(tblDetallePago, i, 5) + " , " +
                                                                                                          FarmaUtility.getValueFieldJTable(tblDetallePago, i, 7) + "<BR>";
            }*/

            //2.1 Valida montos de pedido
            String v_Resultado = DBCaja.verificaPedidoFormasPago(VariablesCaja.vNumPedVta);
            if (v_Resultado.trim().equalsIgnoreCase("ERROR")) {
                FarmaUtility.liberarTransaccion();
                //VariablesCaja.vIndPedidoCobrado = false;
                FarmaUtility.showMessage(this, "El pedido no puede ser cobrado. \n" +
                        "Los totales de formas de pago y cabecera no coinciden. \n" +
                        "Comuníquese con el Operador de Sistemas inmediatamente." + ". \n" +
                        "NO CIERRE LA VENTANA.", tblFormasPago);
                return false;
            }

            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            //No continuar con la transaccion
            log.error("", e);
            FarmaUtility.showMessage(this, "Error. No se pudo registrar una de las formas de pagos", tblFormasPago);
            return false;
        }
        
        //3. Imprimir Comprobante
        UtilityConvenioBTLMF.procesoImpresionComprobante(this, null, false);

        //4. verifica si existen pedidos pendientes de anulacion despues de N minutos
        UtilityCaja.verificaPedidosPendientes(this);

        //KMONCADA 21.11.2014
        // 5. VERIFICA PEDIDOS ACTUALIZACION PEDIDOS DELIVERY INVOCA A UN HILO
        HiloActualizarProforma hilo =
            new HiloActualizarProforma(VariablesCaja.vNumPedVta, FarmaVariables.vCodLocal, FarmaVariables.vCodGrupoCia);
        hilo.start();
        return true;
    }

    private boolean anulaPedido() {
        boolean flag = false;
        //1. Finaliza cobro
        //2. Imprimir Comprobante
        VariablesConvenioBTLMF.vFlgImprimirCompTicket = false;
        UtilityConvenioBTLMF.procesoImpresionComprobante(this, null, true);
        //3. Anula pedido
        try {
            //boolean isNuevoConvenioBTLMF = false;
            //boolean isConvenioBTLMF = UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null);

            //if(isConvenioBTLMF && indConvenioBTLMF != null && indConvenioBTLMF.equals("S"))
            {
                //isNuevoConvenioBTLMF = true;
                VariablesCaja.vNumPedVta_Anul = VariablesCaja.vNumPedVta;
                VariablesCaja.vTipComp_Anul = "%";
                VariablesCaja.vNumComp_Anul = "%";

                boolean esCompCredito = UtilityConvenioBTLMF.esCompCredito(this);
                if (esCompCredito) {
                    String indConexionRac = "N";
                    try {
                        indConexionRac = (facadeRecaudacion.validarConexionRAC()) ? "S" : "N";
                    } catch (Exception e) {
                        ;
                    }
                    if (indConexionRac.equals("S")) {
                        VariablesCaja.vNumPedVta = VariablesCaja.vNumPedVta_Anul;
                        if (UtilityConvenioBTLMF.obtieneCompPago(new JDialog(), "", null)) {

                            for (int j = 0; j < VariablesConvenioBTLMF.vArray_ListaComprobante.size(); j++) {
                                VariablesConvenioBTLMF.vTipoCompPago =
                                        ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(2)).trim();
                                VariablesConvenioBTLMF.vNumCompPago =
                                        ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(0)).trim();
                            }

                        }
                        String pedidoAnuladoRac =
                            DBConvenioBTLMF.anularPedidoRac(FarmaConstants.INDICADOR_N, FarmaConstants.INDICADOR_N); //ERIOS 12.09.2014
                        if (pedidoAnuladoRac.equals("S") || pedidoAnuladoRac.equals("P")) {
                            DBCaja.anularPedido_BTL_MF(VariablesCaja.vNumPedVta, "%", "%",
                                                       VariablesCaja.vValTotalPagar, VariablesCaja.vNumCajaImpreso,
                                                       "Anulacion", "N");

                            if (pedidoAnuladoRac.equals("S")) {
                                DBConvenioBTLMF.actualizaFechaProcesoAnulaRac();
                            }
                        } else {
                            FarmaUtility.showMessage(this, "No se pudo anular el pediddo Convenio en el RAC", null);
                            return false;

                        }
                    } else {

                        FarmaUtility.showMessage(this,
                                                 "No puede anular el pedido, porque no existe una conexion con el RAC",
                                                 null);
                        return false;
                    }
                } else {
                    DBCaja.anularPedido_BTL_MF(VariablesCaja.vNumPedVta, "%", "%", VariablesCaja.vValTotalPagar,
                                               VariablesCaja.vNumCajaImpreso, "Anulacion", "N");

                }

            }
        } catch (Exception e) {
            log.error("", e);
            flag = false;
        }

        //4. Anula Ticket
        UtilityCaja.obtieneImpresTicket(this, txtNroPedido);
        UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);

        try {
            UtilityCaja.getImpresionTicketAnulado(this, VariablesCaja.vNumPedVta, FarmaVariables.vNuSecUsu,
                                                  VariablesCaja.vNumCajaImpreso, VariablesCaja.vNumTurnoCajaImpreso);
        } catch (Exception e) {
            log.error("", e);
            flag = false;
        }
        return flag;
    }

    /**
     * Procesa nuevo cobro
     * @author RHERRERA
     * @since 02.05.2014
     */
    private boolean finalizaBD_conv() {
        log.warn("***** PROCESO FINALIZAR COBRO *****");


        // RHERRERA: metodo obtener forma de pago
        formasPagoImpresion();

        String vueltoPedido = lblVuelto.getText().trim();
        log.debug("vuelto del cobro: " + vueltoPedido);
        colocaVueltoDetallePago(vueltoPedido);

        if (!UtilityCaja.nvoCobroBD(this, VariablesCaja.vNumPedVta, tblDetallePago)) {
            return false;
        }

        VariablesCaja.vIndPedidoCobrado = true;

        //3. Imprimir Comprobante
        UtilityConvenioBTLMF.procesoImpresionComprobante(this, null, false);

        //4. verifica si existen pedidos pendientes de anulacion despues de N minutos
        UtilityCaja.verificaPedidosPendientes(this);

        return true;
    }

    public void setIndFinalizaCobro(boolean b) {
        this.indFinalizaCobro = b;
    }

    public void setIndAnularPedido(boolean b) {
        this.indAnularPedido = b;
    }

    public void setIndCobroConvBD(boolean indCobroConvBD) {
        this.indCobroConvBD = indCobroConvBD;
    }

    public boolean isIndCobroConvBD() {
        return indCobroConvBD;
    }

    class HiloGrabaRac extends Thread {
        private FacadeConvenioBTLMF facadeConvenioBTLMF = new FacadeConvenioBTLMF();
        private String pNumPedVta, pCodLocal, pCodGrupoCia;

        public HiloGrabaRac() {
        }

        public HiloGrabaRac(String pNumPedVta, String pCodLocal, String pCodGrupoCia) {
            this.pNumPedVta = pNumPedVta;
            this.pCodLocal = pCodLocal;
            this.pCodGrupoCia = pCodGrupoCia;
        }

        public void run() {
            try {
                String resMatriz = facadeConvenioBTLMF.grabarTemporalesRAC(VariablesCaja.vNumPedVta);
                if (resMatriz.equals("S")) {
                    log.info("Grabo en en los temporales");
                    resMatriz =
                            facadeConvenioBTLMF.grabarCobroPedidoRac(pNumPedVta, pCodLocal, pCodGrupoCia, FarmaConstants.INDICADOR_N);
                    log.info("Realizo el cobro en Rac -->Resultado:" + resMatriz + " - NroPedido: " + pNumPedVta +
                             " - CodLocal: " + pCodLocal + " - CodGrupoCia: " + pCodGrupoCia + " Indicador NC: " +
                             FarmaConstants.INDICADOR_N);
                    //DBConvenioBTLMF.grabarCobrarPedidoRac(FarmaConstants.INDICADOR_S);
                    if ("S".equalsIgnoreCase(resMatriz)) {
                        resMatriz = facadeConvenioBTLMF.actualizaFechaProcesoRac(pCodGrupoCia, pCodLocal, pNumPedVta);
                        //DBConvenioBTLMF.actualizaFechaProcesoRac();
                        log.info("Actulizo fecha del pedido --> Resultado: " + resMatriz + " NroPedido: " +
                                 pNumPedVta + " - CodLocal: " + pCodLocal + " - CodGrupoCia: " + pCodGrupoCia);
                        if (!"S".equalsIgnoreCase(resMatriz)) {
                            throw new Exception("Error al realizar la actualizacion de la Fecha de proceso RAC- NroPedido: " +
                                                pNumPedVta + " - CodLocal: " + pCodLocal + " - CodGrupoCia: " +
                                                pCodGrupoCia + " Indicador NC: " + FarmaConstants.INDICADOR_N);
                        }
                    } else {
                        throw new Exception("Error al realizar el cobro en el RAC - NroPedido: " + pNumPedVta +
                                            " - CodLocal: " + pCodLocal + " - CodGrupoCia: " + pCodGrupoCia +
                                            " Indicador NC: " + FarmaConstants.INDICADOR_N);
                    }
                } else {
                    throw new Exception("No se grabaron los temporales en el RAC.");
                }
            } catch (Exception ex) {
                log.info(ex.toString());
            }
        }
    }
}


