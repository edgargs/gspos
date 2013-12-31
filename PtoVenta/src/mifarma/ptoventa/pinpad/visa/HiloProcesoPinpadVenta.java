package mifarma.ptoventa.pinpad.visa;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.pinpad.reference.DBPinpad;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloProcesoPinpadVenta extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(HiloProcesoPinpadVenta.class); 
    
    public Double monto;
    public Double propina;
    public String tipoMoneda;
    public String codTienda;
    public String codCaja;
    
    public JPanelWhite pnlFondo;
    public JLabelWhite lblMensajePinpad;
    public JPanelTitle pnlMensajePinpad;
    public JLabelFunction lblEsc;
    public JLabelFunction lblF11;
    public JLabel lblDatoNombreTarjeta;
    public JLabel lblDatoNumAutorizacion;
    public JLabel lblDatoCodVoucher;
    public JLabel lblDatoCuota;
    public JLabel lblDatoMontoCuota;
    public Date fechaExp;
    public String nombreCliente;
    public String voucher;
    public String lote;
    
    public void run()
    {   GeneradorTramaEnvio gte =new GeneradorTramaEnvio();
        ManejadorTramaRetorno mtr = new ManejadorTramaRetorno();
        
        gte.setMonto(monto);
        //gte.setPropina(propina);
        gte.setTipoMoneda(tipoMoneda);
        gte.setCodTienda(codTienda);
        gte.setCodCaja(codCaja);
        gte.setTipoOperacion(VariablesPinpad.OP_FINANCIERA);
        
        try
        {   String tramaEnvio = gte.generarTrama();
            log.debug("Trama Envio: "+tramaEnvio);
            
            //se inicia la comunicación al pinpad y se envia la trama de inicio
            if(mtr.iniciarProceso(tramaEnvio))
            {   int i=0;
                String mensajeImpr ="";
                while(i++<VariablesPinpad.CICLOS_MAX)
                {   
                    pnlFondo.grabFocus();
                    //se obtiene la información que envia el pinpad continuamente
                    mtr.obtenerInfoProceso();
                    log.debug("Trama Retorno "+i+": "+mtr.getTramaRetorno());
                    
                    /************************** PROCESOS DE COBRO ********************/
                    if(mtr.isCortePapel())
                    {   mtr.guardarMsjeImprBD(mensajeImpr, VariablesPinpad.RETOR_COD_OPERACION_VTA);
                        //mtr.imprVoucher( mensajeImpr );
                        mtr.setCortePapel(false);
                        mensajeImpr = null;
                    }
                    
                    //si se envia a imprimir a la impresora
                    if(mtr.getTipoMensaje().equals(VariablesPinpad.TIPO_MENSJ_PINPAD_IMPR))
                    {   if(mtr.getMensFinOperacion()!= null && !"".equals(mtr.getMensFinOperacion()))
                        {   if(mensajeImpr==null)
                                mensajeImpr = mtr.getMensFinOperacion()+"\n";
                            else
                                mensajeImpr = mensajeImpr + mtr.getMensFinOperacion()+"\n";
                        }
                    }
                    else
                    {   //si se envio la indicación de ultimo mensaje, se imprime los mensaje restantes 
                        //destinados a la impresora y se termina el while
                        if(mtr.isUltMensaje())
                        {   
                            //imprimir todos los mensajes que faltan
                            if(mensajeImpr!=null && mensajeImpr!="")
                            {   mtr.guardarMsjeImprBD(mensajeImpr, VariablesPinpad.RETOR_COD_OPERACION_VTA);
                                //mtr.imprVoucher( mensajeImpr );
                            }
                            
                            //Si es una venta correcta
                            if(VariablesPinpad.RETOR_COD_OPERACION_VTA.equals(mtr.getCodOperacion()) || 
                               VariablesPinpad.RETOR_COD_OPERACION_VTA_CASHBACK.equals(mtr.getCodOperacion()) || 
                               VariablesPinpad.RETOR_COD_OPERACION_VTA_CUOTAS.equals(mtr.getCodOperacion()))
                            {   
                                lblDatoNombreTarjeta.setText(mtr.getNombreCliente());
                                lblDatoNumAutorizacion.setText(mtr.getNumAutorizacion());
                                lblDatoCodVoucher.setText(mtr.getNumReferencia());
                                lblDatoCuota.setText(mtr.getCuotas().toString());
                                lblDatoMontoCuota.setText(mtr.getMontoCuota().toString());
                                
                                nombreCliente=mtr.getNombreCliente();
                                voucher=mtr.getNumReferencia();
                                
                                //se imprimen los vouchers original
                                String textoImpr = DBPinpad.impVoucherTransVisa(VariablesCaja.vNumPedVta,"O");
                                log.debug(textoImpr);
                                PrintConsejo.imprimirHtml(textoImpr,
                                                          VariablesPtoVenta.vImpresoraActual,
                                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                                
                                //se imprimen los vouchers copia
                                textoImpr = DBPinpad.impVoucherTransVisa(VariablesCaja.vNumPedVta,"C");
                                log.debug(textoImpr);
                                PrintConsejo.imprimirHtml(textoImpr,
                                                          VariablesPtoVenta.vImpresoraActual,
                                                          VariablesPtoVenta.vTipoImpTermicaxIp);
                                mtr.guardarTramaBD();
                                exitoTransaccion();
                            }
                            else
                                errorTransaccion();
                            break;
                        }
                    }
                    Thread.sleep(VariablesPinpad.TIMEOUT);
                }
                mtr.finalizarProceso();
                if(i>VariablesPinpad.CICLOS_MAX)
                    errorTransaccion();
            }
            else
                errorTransaccion();
        }
        catch(Exception e)
        {   log.error("",e);
            errorTransaccion();
        }
        pnlFondo.grabFocus();
    }
    
    public void exitoTransaccion()
    {   lblMensajePinpad.setText("SE REALIZO CORRECTAMENTE EL PROCESO CON EL PINPAD");
        pnlMensajePinpad.setBackground(new Color(49, 141, 43));
        lblMensajePinpad.setForeground(Color.BLACK);
        lblF11.setEnabled(true);
    }
    
    private void errorTransaccion()
    {   lblMensajePinpad.setText("ERROR EN EL PROCESO DE PINPAD. INTENTE NUEVAMENTE");
        pnlMensajePinpad.setBackground(Color.RED);
        lblEsc.setEnabled(true);
    }
}