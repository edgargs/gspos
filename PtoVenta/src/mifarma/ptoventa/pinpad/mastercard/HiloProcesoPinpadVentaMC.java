package mifarma.ptoventa.pinpad.mastercard;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import mifarma.ptoventa.pinpad.reference.DBPinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloProcesoPinpadVentaMC extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(HiloProcesoPinpadVentaMC.class);

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
    public String codFormaPago;
    
    public void run()
    {   ManejadorTramaRetornoMC mtrmc = new ManejadorTramaRetornoMC();
        Map<String,String> resultado = new HashMap<String,String>();
        try
        {   resultado = mtrmc.procesoCompra(monto);
            
            String txt_imprimir = resultado.get("print_data");
            lblMensajePinpad.setText(resultado.get("message"));
            lblDatoNombreTarjeta.setText(resultado.get("client_name"));
            lblDatoNumAutorizacion.setText(resultado.get("approval_code"));
            String temp = resultado.get("message");
            Integer posTemp = temp.indexOf("REF")+3;
            lblDatoCodVoucher.setText(temp.substring(posTemp));
            lblDatoCuota.setText(resultado.get("month"));
            lblDatoMontoCuota.setText(resultado.get("amount_quota"));
            
            log.debug(txt_imprimir);
            
            if("00".equals(resultado.get("response_code")))
            {   if(txt_imprimir != null && !("".equals(txt_imprimir)))
                {   mtrmc.imprVoucher(txt_imprimir);
                }
                mtrmc.guardarTramaProcesoCompraBD(resultado, codFormaPago);
                exitoTransaccion();
            }
            else
                throw new Exception();
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
    }
    
    private void errorTransaccion()
    {   lblMensajePinpad.setText("ERROR EN EL PROCESO DE PINPAD. INTENTE NUEVAMENTE");
        pnlMensajePinpad.setBackground(Color.RED);
        lblEsc.setEnabled(true);
    }
}