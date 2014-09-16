package mifarma.ptoventa.pinpad.mastercard;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.Timer;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.pinpad.DlgInterfacePinpad;
import mifarma.ptoventa.pinpad.reference.DBPinpad;

import mifarma.ptoventa.pinpad.reference.TimerMensajeListener;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloProcesoPinpadVentaMC extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(HiloProcesoPinpadVentaMC.class);

    public DlgInterfacePinpad padre;
    
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
    public JLabel lblTimer;
    public Date fechaExp;
    public String nombreCliente;
    public String voucher;
    public String codFormaPago;
    
    
    public void run()
    {   ManejadorTramaRetornoMC mtrmc = new ManejadorTramaRetornoMC();
        mtrmc.setNroTarjetaBruto(padre.getNroTarjetaBruto());
        Map<String,String> resultado = new HashMap<String,String>();
        try
        {   
            log.debug("Se comenzo a procesar la transacción de venta al pinpad MC");
            resultado = mtrmc.procesoCompra(monto);
            log.debug("Se retorno la información de venta del pinpad MC");
            log.info("resultado:"+resultado);
            String txt_imprimir = resultado.get("print_data");
            lblMensajePinpad.setText(resultado.get("message"));
            lblDatoNombreTarjeta.setText(resultado.get("client_name"));
            lblDatoNumAutorizacion.setText(resultado.get("approval_code"));
            String temp = resultado.get("message");
            Integer posTemp = 0;
            try{
                posTemp = temp.indexOf("REF")+3;
                lblDatoCodVoucher.setText(temp.substring(posTemp));
            }catch(NullPointerException e){
                temp = "";                
            }            
            lblDatoCuota.setText(resultado.get("month"));
            lblDatoMontoCuota.setText(resultado.get("amount_quota"));
            
            log.debug(txt_imprimir);
            
            if("00".equals(resultado.get("response_code")))
            {   
                //ERIOS 2.2.8 Valida codigo aprobacion
                if(lblDatoNumAutorizacion.getText().trim().matches("^[0-9|A-Z]{4,6}$")){
					mtrmc.guardarTramaProcesoCompraBD(resultado, codFormaPago);
                    if(txt_imprimir != null && !("".equals(txt_imprimir)))
                    {   mtrmc.imprVoucher(txt_imprimir);
                    }
                    //mtrmc.guardarTramaProcesoCompraBD(resultado, codFormaPago);
                    exitoTransaccion();
                }else{
                    lblDatoCodVoucher.setText(temp);
                    errorTransaccion();
                }
            }
            else{
                //Correo
                if (resultado.get("rpt").equals("0") && 
                    resultado.get("response_code") == null){
                FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                              FarmaVariables.vCodLocal,
                                              VariablesPtoVenta.vDestEmailErrorCobro,
                                              "Error Proceso Pinpad MC ",
                                              "Error al procesar venta con pinpad MC",
                                              "Codigo Rpta Trama : "+resultado.get("rpt")+ "<br>"+
                                              "Response Code : "+resultado.get("response_code")+ "<br>"+
                                              "Mensaje: "+resultado.get("message")+ "<br>"+
                                              "IP PC: " + FarmaVariables.vIpPc + "<br>",
                                              "");
                    }
                throw new Exception("Respuesta invalida: "+resultado.get("response_code"));                
            }
        }
        catch(Exception e)
        {   errorTransaccion();
            log.error("",e);
        }
        pnlFondo.grabFocus();
    }
    
    public void exitoTransaccion()
    {   lblMensajePinpad.setText("SE REALIZO CORRECTAMENTE EL PROCESO CON EL PINPAD");
        pnlMensajePinpad.setBackground(new Color(49, 141, 43));
        lblMensajePinpad.setForeground(Color.BLACK);
        lblF11.setEnabled(true);
        
        //LLEIVA 18-Feb-2014 Se añade el listener para que espere 10 segundos y cierre la ventana
        TimerMensajeListener tml = new TimerMensajeListener();
        tml.lblMensaje = this.lblTimer;
        tml.padreVenta = this.padre;
        tml.indicador = "V";
        lblF11.addKeyListener(tml);
    }
    
    private void errorTransaccion()
    {   lblMensajePinpad.setText("ERROR EN EL PROCESO DE PINPAD. INTENTE NUEVAMENTE");
        pnlMensajePinpad.setBackground(Color.RED);
        lblEsc.setEnabled(true);
    }
}