package mifarma.ptoventa.pinpad.visa;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.swing.JLabel;

import mifarma.ptoventa.pinpad.DlgAnularTransPinpad;
import mifarma.ptoventa.pinpad.reference.TimerMensajeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiloProcesoAnularPinpadVisa extends Thread {
    private static final Logger log = LoggerFactory.getLogger(HiloProcesoAnularPinpadVisa.class);

    public DlgAnularTransPinpad padre;

    public String tipoMoneda;
    public String codTienda;
    public String codCaja;
    public boolean flagAnulPed;

    public JPanelWhite pnlFondo;
    public JLabelWhite lblMensajePinpad;
    public JPanelTitle pnlMensajePinpad;
    public JLabelFunction lblEsc;
    public JLabelFunction lblF11;
    public JLabelFunction lblF5;
    public JLabel lblDatoNumAutorizacion;
    public JLabel lblDatoCodVoucher;
    public JLabel lblFlagAnulDiaLoteCerrado = new JLabel();
    public JLabel lblDatoFecha;
    public JLabel lblDatoPedidoEnv;
    public JLabel lblTimer;

    public String numReferencia;
    public String formaPago;


    private void impresionVoucher() {

    }

    public void run() {
        GeneradorTramaEnvio gte = new GeneradorTramaEnvio();
        ManejadorTramaRetorno mtr = new ManejadorTramaRetorno();
        mtr.setLblDatoPedidoEnv(lblDatoPedidoEnv.getText());
        //gte.setMonto(monto);
        //gte.setPropina(propina);
        gte.setTipoMoneda(tipoMoneda);
        gte.setCodTienda(codTienda);
        gte.setCodCaja(codCaja);
        gte.setTipoOperacion(VariablesPinpad.OP_FINANCIERA);

        try {
            String tramaEnvio = gte.generarTrama();
            log.debug("Trama Envio: " + tramaEnvio);

            //se inicia la comunicación al pinpad y se envia la trama de inicio
            if (mtr.iniciarProceso(tramaEnvio)) {
                int i = 0;
                String mensajeImpr = "";
                while (i <= VariablesPinpad.CICLOS_MAX) { //se obtiene la información que envia el pinpad continuamente
                    mtr.obtenerInfoProceso();
                    log.debug("Trama Retorno " + i + ": " + mtr.getTramaRetorno());
                    i++;

                    /************************** PROCESOS DE ANULACION ********************/
                    if (mtr.isCortePapel()) {
                        mtr.guardarMsjeImprBD(mensajeImpr, VariablesPinpad.RETOR_COD_OPERACION_ANULACION,
                                              lblDatoPedidoEnv.getText());
                        log.info("hito 7-->" + mensajeImpr);
                        mtr.imprVoucher(mensajeImpr);
                        mtr.setCortePapel(false);
                        mensajeImpr = null;
                    }

                    //si se envia a imprimir a la impresora
                    if (mtr.getTipoMensaje().equals(VariablesPinpad.TIPO_MENSJ_PINPAD_IMPR)) {
                        if (mtr.getMensFinOperacion() != null && !"".equals(mtr.getMensFinOperacion())) {
                            if (mensajeImpr == null)
                                mensajeImpr = mtr.getMensFinOperacion() + "\n";
                            else
                                mensajeImpr = mensajeImpr + mtr.getMensFinOperacion() + "\n";
                        }
                        log.warn("mensaje de getMensFinOperacion voucher: ", mensajeImpr);
                    } else { //si se envio la indicación de ultimo mensaje, se imprime los mensaje restantes
                        //destinados a la impresora y se termina el while
                        if (mtr.isUltMensaje()) {
                            //imprimir todos los mensajes que faltan
                            if (mensajeImpr != null && mensajeImpr != "") {
                                mtr.guardarMsjeImprBD(mensajeImpr, VariablesPinpad.RETOR_COD_OPERACION_ANULACION,
                                                      lblDatoPedidoEnv.getText());
                                log.info("hito 6-->" + mensajeImpr);
                                mtr.imprVoucher(mensajeImpr);
                            }

                            //Si es una anulación correcta
                            if (VariablesPinpad.RETOR_COD_OPERACION_ANULACION.equals(mtr.getCodOperacion())) {
                                //lblDatoNombreTarjeta.setText(mtr.getNombreCliente());
                                lblDatoNumAutorizacion.setText(mtr.getNumAutorizacion());
                                lblDatoCodVoucher.setText(mtr.getNumReferencia());

                                //nombreCliente=mtr.getNombreCliente();
                                //voucher=mtr.getNumReferencia();
                                if (numReferencia.equals(mtr.getNumReferencia())) {
                                    log.debug("Se anulo correctamente el pedido");
                                    lblMensajePinpad.setText("SE REALIZO CORRECTAMENTE LA ANULACION CON EL PINPAD");
                                    pnlMensajePinpad.setBackground(new Color(49, 141, 43));
                                    lblMensajePinpad.setForeground(Color.BLACK);
                                    lblF11.setEnabled(true);

                                    //LLEIVA 18-Feb-2014 Se añade el listener para que espere 10 segundos y cierre la ventana
                                    TimerMensajeListener tml = new TimerMensajeListener();
                                    tml.lblMensaje = this.lblTimer;
                                    tml.padreAnul = this.padre;
                                    tml.indicador = "A";
                                    lblF11.addKeyListener(tml);
                                } else
                                    errorTransaccion(1, mtr);

                                mtr.guardarTramaAnulacionBD(lblDatoPedidoEnv.getText(), formaPago);
                            } else
                                errorTransaccion(2, mtr);
                            break;
                        }
                    }
                    Thread.sleep(VariablesPinpad.TIMEOUT);
                }
                mtr.finalizarProceso();
                if (i > VariablesPinpad.CICLOS_MAX)
                    errorTransaccion(3, mtr);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void errorTransaccion(int tipoError, ManejadorTramaRetorno mtr) {
        log.error("ERROR DE TRANSACCION - Proceso Pinpad");
        //si el flag de anulacion de pedido es true
        if (flagAnulPed) { //se verifica si la operacion indica que el lote no existe y que sea del mismo dia

            Calendar fecha_actual = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            String str_fecha_actual = sdf.format(fecha_actual.getTime());

            boolean flag = (str_fecha_actual.equalsIgnoreCase(lblDatoFecha.getText())) //&&
                //(VariablesPinpadMC.COD_RESP_LOTE_NO_EXISTE.equals(resultado.get("response_code")) ||
                // VariablesPinpadMC.COD_RESP_NO_EXIST_TRX_LOTE.equals(resultado.get("response_code"))))
                //"77".equalsIgnoreCase(resultado.get("response_code"))
            ;

            if (flag) {
                this.lblFlagAnulDiaLoteCerrado.setText("true");
            }
        }

        if (tipoError == 1)
            lblMensajePinpad.setText("EL NUM. DE REFERENCIA ANULADO NO ES EL MISMO AL DEL PEDIDO");
        else if (tipoError == 2)
            lblMensajePinpad.setText("ERROR EN EL PROCESO DE PINPAD. INTENTE NUEVAMENTE");
        else if (tipoError == 3)
            lblMensajePinpad.setText("EL TIEMPO DE ESPERA SOBREPASO LO PERMITIDO. INTENTE NUEVAMENTE");

        pnlMensajePinpad.setBackground(Color.RED);
        lblEsc.setEnabled(true);
        lblF5.setEnabled(flagAnulPed);
        lblEsc.grabFocus();
    }
}
