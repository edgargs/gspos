package mifarma.ptoventa.inventariodiario;


import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenio;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProcesaCobroInvDiario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DUBILLUZ   23.06.2009   Creación<br>
 * <br>
 * @author Diego Armando Ubilluz<br>
 * @version 1.0
 * <br>
 *
 */
public class DlgProcesaCobroInvDiario extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesaCobroInvDiario.class);

    private Frame myParentFrame;
    //private boolean vValorProceso;
    private JTable tblFormasPago;
    private JLabel lblVuelto;
    private JTable tblDetallePago;
    private JTextField txtNroPedido;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private String indCommitBefore = "";

    public DlgProcesaCobroInvDiario() {
        this(null, "", false);
    }

    /**
     * Constructor con parametros.
     * @param parent
     * @param title
     * @param modal
     */
    public DlgProcesaCobroInvDiario(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgProcesaCobroInvDiario(Frame parent, String title, boolean modal, JTable pTableModel, JLabel pVuelto,
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
        this.setTitle("Procesando Información . . .");
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
        FarmaUtility.centrarVentana(this);
        procesar();
        cerrarVentana(false);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Validaciones de Cobro de Pedido
     * @author Dubilluz
     * @since  05.03.2009
     */
    private boolean validacionesCobroPedido() {
        //valida que haya sido seleccionado un pedido.
        if (VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;

        //validando que se haya cubierta el total del monto del pedido
        if (!VariablesCaja.vIndTotalPedidoCubierto) {
            FarmaUtility.showMessage(this, "El Pedido tiene saldo pendiente de cobro.Verifique!!!", tblFormasPago);
            return false;
        }

        //validando que el pedido este en esta PENDIENTE DE COBRO
        if (!UtilityCaja.verificaEstadoPedido(this, VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_PENDIENTE, null)) {
            return false;
        }

        //Inicio Adicion Paulo. Validacion para cajeros
        if (!UtilityCaja.existeCajaUsuarioImpresora(this, null)) {
            cerrarVentana(false);
            return false;
        }
        //Fin Adicion Paulo.

        /*
        * Validacion de Fecha actual del sistema contra
        * la fecha del cajero que cobrara
        * */
        if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)) {
            FarmaUtility.liberarTransaccion();
            return false;
        }

        return true;

    }

    private void procesar() {
        long tmpIni, tmpFin, tmpT1, tmpT2, tmpT3, tmpT4;

        tmpIni = System.currentTimeMillis();
        tmpT1 = System.currentTimeMillis();

        //INICIO DE VALIDACIONES
        if (!validacionesCobroPedido())
            return;

        tmpT2 = System.currentTimeMillis();
        log.debug("t1 Validaciondes Iniciales Cobro : " + (tmpT2 - tmpT1) + " milisegundos");

        /**
       * Se valida que la caja este abierta y se bloquea al mismo tiempo.
       * @AUTHOR JCORTEZ
       * @SINCE 18.05.09
       * */
        tmpT3 = System.currentTimeMillis();
        if (!validaCajaAbierta())
            return;
        tmpT4 = System.currentTimeMillis();
        log.debug("t3 Bloqueo Caja_Pago para validar que no cobre pedido si se cierra caja : " + (tmpT3 - tmpT4) +
                  " milisegundos");

        //INICIO PROCESO DE COBRO
        try {

            //-- inicio validacion cupones
            //Se consulta para obtener los cupones usados en el pedido
            tmpT1 = System.currentTimeMillis();
            VariablesCaja.mostrarValoresVariables();
            //fin de la validacion de campañas limitadas en cantidad de usos
            //verificar si es pedido por convenio
            String pIndPedConvenio = DBCaja.getIndPedConvenio(VariablesCaja.vNumPedVta);
            VariablesVentas.vEsPedidoConvenio = (pIndPedConvenio.equals("S")) ? true : false;
            log.debug("jcallo VariablesVentas.vEsPedidoConvenio:" + VariablesVentas.vEsPedidoConvenio);
            //fin verificar si es pedido por convenio
            //numero de liner por BOLETA
            if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA)) {
                if (VariablesVentas.vEsPedidoConvenio) {
                    VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO =
                            Integer.parseInt(DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_BOLETA_CON_CONVENIO));
                    VariablesCaja.vNumSecImpresionComprobantes =
                            DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
                } else {
                    VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO =
                            Integer.parseInt(DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_BOLETA_SIN_CONVENIO));
                    VariablesCaja.vNumSecImpresionComprobantes =
                            DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
                }
            } else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA)) { //numero de linea por FACTURA
                if (VariablesVentas.vEsPedidoConvenio) {
                    VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO =
                            Integer.parseInt(DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_FACTURA_CON_CONVENIO));
                    VariablesCaja.vNumSecImpresionComprobantes =
                            DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
                } else {
                    VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO =
                            Integer.parseInt(DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_FACTURA_SIN_CONVENIO));
                    VariablesCaja.vNumSecImpresionComprobantes =
                            DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
                }
            } else if (VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) { //JCORTEZ 25.03.09 numero de linea por TICKET
                if (VariablesVentas.vEsPedidoConvenio) {
                    VariablesCaja.TOTAL_LINEAS_POR_TICKET =
                            Integer.parseInt(DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_TICKET));
                    VariablesCaja.vNumSecImpresionComprobantes =
                            DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_TICKET);
                } else {
                    VariablesCaja.TOTAL_LINEAS_POR_TICKET =
                            Integer.parseInt(DBCaja.getLineasDetalleDocumento(ConstantsVentas.LINEAS_TICKET));
                    VariablesCaja.vNumSecImpresionComprobantes =
                            DBCaja.agrupaImpresionDetallePedido(VariablesCaja.TOTAL_LINEAS_POR_TICKET);
                }
            }

            //muestra las secuencia de numero de comprobantes
            log.debug("jcallo:VariablesCaja.vNumSecImpresionComprobantes : " +
                      VariablesCaja.vNumSecImpresionComprobantes);

            //obtiene la descrip de la formas de pago para la impresion
            log.debug("jcallo: antes de la descripcion de formas de pago impresion");
            formasPagoImpresion();
            log.debug("jcallo: fin de descripcion de formas de pago");
            //actualiza datos del cliente como nombre direccion ruc, etc
            //cobrar pedido DEVOLVERA EXITO. si cobro correctamente
            String resultado =
                DBCaja.cobraPedido(VariablesVentas.vTip_Comp_Ped, VariablesCaja.vPermiteCampaña.trim(), "");
            log.debug(" verificando si el pedido es EXITOSO resultado Verifica: " + resultado);
            tmpT2 = System.currentTimeMillis();
            log.debug("t5 Proceso de Cobro: " + (tmpT2 - tmpT1) + " milisegundos");

            if (resultado.trim().equalsIgnoreCase("EXITO")) {
                tmpT1 = System.currentTimeMillis();
                if (!UtilityCaja.validaAgrupacionComprobante(this,
                                                             tblFormasPago)) { //el liberar transaccion esta dentro del metodo
                    log.debug("error al agrupar comprobantes ... !!!!");
                    VariablesCaja.vIndPedidoCobrado = false;
                    return;
                }
                tmpT2 = System.currentTimeMillis();
                log.debug("t6 Agrupa Comprobantes: " + (tmpT2 - tmpT1) + " milisegundos");

                VariablesCaja.vIndPedidoCobrado = true;
                log.debug("jcallo:VariablesCaja.vIndPedidoConvenio=" + VariablesCaja.vIndPedidoConvenio +
                          ", VariablesConvenio.vValCoPago=" + VariablesConvenio.vValCoPago);
                //si es pedido convenio y va usar credito de convenio
                tmpT1 = System.currentTimeMillis();
                log.debug("jcallo: VariablesCaja.vIndPedidoConProdVirtual=" + VariablesCaja.vIndPedidoConProdVirtual);
                //obtener flag de IND PARA SABER SI IMPRIMIRA ANTES DE LA RECARGA VIRTUAL

                /*
                         * Validacion de Fecha actual del sistema contra
                         * la fecha del cajero que cobrara
                         * Se añadio para validar pedido Cobrado
                         * despues de una fecha establecida al inicio
                         * dubilluz 04.03.2009
                         **/
                if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)) {
                    FarmaUtility.liberarTransaccion();
                    return;
                }
                tmpFin = System.currentTimeMillis();
                log.debug("t15 Fin PROCESO COBRO antes del Commit aún falta Imprimir: " + (tmpFin - tmpIni) +
                          " milisegundos");
                FarmaUtility.aceptarTransaccion(); //haciendo commit en el pedido como cobrado
                tmpT1 = System.currentTimeMillis();
                UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                //obtiene informacion del vendedor
                UtilityCaja.obtieneInfoVendedor();
                //proceso de impresion de comprobante del pedido
                //JCALLO ...corregir todo este metodo, se agrego el indicador de linea con matriz
                //pIndLineaMatriz
                tmpT2 = System.currentTimeMillis();
                log.debug("t17 Obtiene informacion de cajero y de vendedor: " + (tmpT2 - tmpT1) + " milisegundos");
                UtilityCaja.procesoImpresionComprobante(this, txtNroPedido, false);
                FarmaUtility.showMessage(this, "Comprobantes Impresos con éxito", tblFormasPago);
                VariablesCaja.vIndPedidoCobrado = true;

            } else if (resultado.trim().equalsIgnoreCase("ERROR")) {
                FarmaUtility.liberarTransaccion();
                VariablesCaja.vIndPedidoCobrado = false;
                FarmaUtility.showMessage(this, "El pedido no puede ser cobrado. \n" +
                        "Los totales de formas de pago y cabecera no coinciden. \n" +
                        "Comuníquese con el Operador de Sistemas inmediatamente." + ". \n" +
                        "NO CIERRE LA VENTANA.", tblFormasPago);
            }

        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error(null, sql);
            String pMensaje = sql.getMessage();
            FarmaUtility.showMessage(this, "Error al cobrar el pedido.\n" +
                    "" + pMensaje, tblFormasPago);
            VariablesCaja.vIndPedidoCobrado = false;
        } finally {
            log.debug("LIMPIA TODAS LAS VARIABLES");
        }

        tmpFin = System.currentTimeMillis();
        log.debug("t27 Fin de Todo el Proceso de Cobro: " + (tmpFin - tmpIni) + " milisegundos");


        cerrarVentana(false);
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

    private boolean validaCajaAbierta() {
        boolean result = true;
        String Indicador = "";
        try {
            log.debug("VariablesCaja.vNumCaja ===> " + VariablesCaja.vNumCaja);
            Indicador = DBCaja.obtieneEstadoCaja();
            if (Indicador.trim().equalsIgnoreCase("N")) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "La caja no se encuentra aperturada. Verifique!!!", null);
                result = false;
            }
            log.debug("Se válida la apertura de caja para el cobro ===> " + Indicador);
        } catch (Exception e) {
            result = false;
            log.debug("Error al obtener indicador de caja abierta : " + e.getMessage());
        }

        return result;
    }


}
