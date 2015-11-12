package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JMessageAlert;
import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Color;
import java.awt.Frame;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.ImpresoraTicket;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.FarmaRecargaException;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.UtilityRecargaVirtual;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;
import mifarma.ptoventa.recaudacion.DlgProcesarVentaCMR;
import mifarma.ptoventa.recaudacion.reference.ConstantsRecaudacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesarProductoVirtual extends JDialogProgress {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesarProductoVirtual.class);

    private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    private FacadeRecetario facadeRecetario = new FacadeRecetario();

    private Frame myParentFrame;
    private JTable tblFormasPago;
    private JTextField txtNroPedido;
    private boolean vProcesoRecarga;

    public DlgProcesarProductoVirtual(Frame frame, String string, boolean b) {
        super(frame, string, b);
        myParentFrame = frame;
    }

    public DlgProcesarProductoVirtual() {
        super();
    }

    @Override
    public void ejecutaProceso() {

        //INICIO DE VALIDACIONES
        if (!UtilityCaja.validacionesCobroPedido(false, this, tblFormasPago))
            return;

        if (!UtilityCaja.validaCajaAbierta(this))
            return;

        //ERIOS 04.11.2013 Procesa anulacion automatica de recarga virtual
        vProcesoRecarga = true;

        //1. Procesa pedido virtual
        if (VariablesCaja.vIndPedidoConProdVirtual) {

            try {
                procesaPedidoVirtual();
                evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);
            } catch (Exception e) {
                log.error("",e);
                FarmaUtility.liberarTransaccion();
                if(e instanceof FarmaRecargaException){
                    String strNomOperador = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 14);
                    String strTitulo = ImpresoraTicket.centrarLinea("MENSAJE DE "+strNomOperador+":", " ", 40);
                    //ERIOS 23.09.2015 Mensaje del operador
                    JMessageAlert.showMessage(myParentFrame, "Recarga Virtual",
                                              strTitulo,
                                              e.getMessage(),
                                              "Si se realizó el pago con tarjeta, es obligatorio realizar" + "\n" +
                            "la anulación de la transacción", true, Color.GRAY);    
                }else{
                    //ERIOS 19.11.2013 Se comenta por si algun dia cambian el procedimiento
                    JMessageAlert.showMessage(myParentFrame, "Recarga Virtual",
                                              "ERROR: No se pudo realizar la recarga virtual",
                                              e.getMessage(),
                                              "Si se realizó el pago con tarjeta, es obligatorio realizar" + "\n" +
                            "la anulación de la transacción", true);
                }
                vProcesoRecarga = false;
                
                //si se pago con tarjeta Pinpad, anular la transacción
                if (VariablesCaja.vIndDatosTarjeta) {
                    //LLEIVA 09-Ene-2014 Se agrupo to_do el procedimiento de obligar a anular la transaccion de pinpad
                    UtilityPinpad.obligarAnularTransaccionPinpad(myParentFrame,
                                                                 "Es obligatoria la anulación de la transacción debido a que no se pudo realizar la recarga virtual");

                    //GFONSECA 03/12/2013 Si se paga con tarjeta CMR, anular la transacción
                    if (VariablesCaja.vIndDatosTarjetaCMR) {
                        //Anulacion de venta CMR con el six, retorna true si anula correctamente.
                        ArrayList<ArrayList<String>> arrayTmpDatosRCD =
                            facadeRecaudacion.getDatosAnulacionVentaCMR(VariablesCaja.vNumPedVta);
                        //Si el pedido existe en la tabla de cabecera de recaudacion se realiza el proceso de anulacion con el six
                        if (arrayTmpDatosRCD != null && arrayTmpDatosRCD.size() > 0) {
                            DlgProcesarVentaCMR dlgProcesarVentaCMR = null;
                            dlgProcesarVentaCMR = new DlgProcesarVentaCMR(myParentFrame, "", true);
                            dlgProcesarVentaCMR.procesarAnulacionVentaCMR(myParentFrame, arrayTmpDatosRCD);
                            dlgProcesarVentaCMR.setStrIndProc(ConstantsRecaudacion.RCD_IND_PROCESO_ANULACION);
                            dlgProcesarVentaCMR.mostrar();
                        }
                        VariablesCaja.vIndDatosTarjetaCMR = false;
                        /*if( !dlgProcesarVentaCMR.isBRptTrsscAnul()){
                          return;//Si el proceso de anulacion con el Six falla, no tiene que seguir la rutina de anulacion.
                        }*/
                    }
                }
                vProcesoRecarga = false;
            }
        }
    }

    private void evaluaMsjVentaVirtualGenerado(String pTipoProdVirtual) {
        if (ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA.equalsIgnoreCase(pTipoProdVirtual))
            muestraTarjetaVirtualGenerado();
        else if (ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA.equalsIgnoreCase(pTipoProdVirtual)) {
            if (VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL)) {
                log.info("La recarga automática se realizó satisfactoriamente.");
            } else {
                FarmaUtility.showMessage(this, "Verifique en su módulo de consulta la confirmación de la recargas\n" +
                        "No se pudo Obtener la respuesta del proveedor por lentitud en conexión.", null);
            }
        }
    }

    private void procesaPedidoVirtual() throws Exception { //ERIOS 30.05.2013 Envia el pedido de preparado hacia el sistema Recetario Magistral
        //ERIOS 16.07.2013 Implementacion de recargas FarmaSix
        UtilityRecargaVirtual.obtieneInfoPedidoVirtual();
        if (VariablesVirtual.vArrayList_InfoProdVirtual != null &&
            VariablesVirtual.vArrayList_InfoProdVirtual.size() != 1) {
            throw new Exception("Error al validar info del pedido virtual");
        }
        UtilityRecargaVirtual.colocaInfoPedidoVirtualGrabar();
        if (ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA.equalsIgnoreCase(VariablesCaja.vTipoProdVirtual) ||
            ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA.equalsIgnoreCase(VariablesCaja.vTipoProdVirtual)) {

                UtilityCaja.procesaVentaProductoVirtual(this, VariablesVirtual.vArrayList_InfoProdVirtual);
                
                /*
                * Se grabara la respuesta obtenida por el proveedor al realizar la
                * recarga virtual
                */
                try {
                    DBCaja.grabaRespuestaRecargaVirtual(VariablesVirtual.respuestaTXBean.getCodigoRespuesta(),
                                                        VariablesCaja.vNumPedVta);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }

                if (!validaCodigoRespuestaTransaccion()) {
                    throw new Exception(VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + " - " +
                                        VariablesVirtual.respuestaTXBean.getDescripcion());
                }
            
        } else if (ConstantsVentas.TIPO_PROD_VIRTUAL_MAGISTRAL.equalsIgnoreCase(VariablesCaja.vTipoProdVirtual) &&
                   FarmaConstants.INDICADOR_S.equalsIgnoreCase(VariablesPtoVenta.vIndVerReceMagis)) {
            HashMap<String, String> hRecetario = new HashMap<String, String>();

            DBRecetario.getNumeroRecetario(VariablesCaja.vNumPedVta, hRecetario);

            String numRecetario = "";
            String estRecetario = "";

            if (hRecetario != null) {
                numRecetario = hRecetario.get("NUM_RECETARIO");
                estRecetario = hRecetario.get("EST_RECETARIO");
            }

            if (!"".equals(numRecetario)) {
                if (estRecetario.equals(ConstantsRecetario.Estado.PENDIENTE.getValor())) {
                    String tramaRecetario = DBRecetario.getTramaRecetario(numRecetario);

                    //Envia la trama al sistema de Fasa
                    String rptaRecetario = facadeRecetario.enviaTramaRecetario(tramaRecetario);

                    if ("OK".equalsIgnoreCase(rptaRecetario)) {
                        DBRecetario.actualizaEstadoRecetario(numRecetario, ConstantsRecetario.Estado.ENVIADO);
                    } else {
                        //indCommitBefore = "N";
                        log.error("Trama resp: " + rptaRecetario);
                        throw new Exception("Se ha presentado un error al enviar el recetario.\n");
                    }
                } else if (ConstantsRecetario.Estado.GUIA.getValor().equalsIgnoreCase(estRecetario)) {
                    //Los recetarios que se generan a partir de [G]uias, no se envian.
                    DBRecetario.actualizaEstadoRecetario(numRecetario, ConstantsRecetario.Estado.COBRADO);
                }
            } else {
                throw new Exception("No se encuentra el numero de Recetario.");
            }
        }
    }

    private void muestraTarjetaVirtualGenerado() {
        DlgNumeroTarjetaGenerado dlgNumeroTarjetaGenerado = new DlgNumeroTarjetaGenerado(myParentFrame, "", true);
        dlgNumeroTarjetaGenerado.setVisible(true);
        FarmaVariables.vAceptar = false;
    }


    private boolean validaCodigoRespuestaTransaccion() {
        boolean result = false;
        log.debug("VariablesVirtual.vCodigoRespuesta - " + VariablesVirtual.vCodigoRespuesta);
        if (VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL) ||
            VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_COBRA_REVISE_EST_TAR_VIRTUAL))
            result = true;

        log.debug("validaCodigoRespuestaTransaccion result - " + result);
        return result;
    }

    public void setTblFormasPago(JTable tblFormasPago) {
        this.tblFormasPago = tblFormasPago;
    }

    public JTable getTblFormasPago() {
        return tblFormasPago;
    }

    public void setTxtNroPedido(JTextField txtNroPedido) {
        this.txtNroPedido = txtNroPedido;
    }

    public JTextField getTxtNroPedido() {
        return txtNroPedido;
    }

    public void setVProcesoRecarga(boolean vProcesoRecarga) {
        this.vProcesoRecarga = vProcesoRecarga;
    }

    public boolean isVProcesoRecarga() {
        return vProcesoRecarga;
    }

    public void actualizaFechaRespuesta() {
        UtilityRecargaVirtual utilRecaVirtual = new UtilityRecargaVirtual();
        if(!"S".equals(utilRecaVirtual.getIndGestorTx(false))){
            //ERIOS 2.4.5 Valida conexion
            if (utilRecaVirtual.validarConexionRecarga()) {
                ArrayList<Object> rptSix = null;
                Long codTrssc = null;
                if (!VariablesVirtual.vNumTrace.equals(".")) {
                    try {
                        codTrssc = Long.parseLong(VariablesVirtual.vNumTrace);                        
                        rptSix =
                                facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_RECARGA_SIX, "", codTrssc);
                    } catch (Exception e) {
                        log.error("ERROR en actualizaFechaRespuesta codTrssc = " + codTrssc, e);
                    }
                }
            }
        }else{
            utilRecaVirtual.marcaRespuestaRecargaWS(FarmaVariables.vCodGrupoCia,FarmaVariables.vCodCia,FarmaVariables.vCodLocal,VariablesCaja.vNumPedVta);
        }
    }
}
