package mifarma.ptoventa.pinpad.visa;

import com.gs.mifarma.componentes.JConfirmDialog;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import mifarma.ptoventa.pinpad.visa.GeneradorTramaEnvio;
import mifarma.ptoventa.pinpad.visa.ManejadorTramaRetorno;
import mifarma.ptoventa.pinpad.visa.VariablesPinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloProcesoPinpadNoFinan extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(HiloProcesoPinpadNoFinan.class);  

    public JTextArea jta;
    public String local;
    public String caja;
    public JScrollPane jScrollPane1;
    
    public void run()
    {   GeneradorTramaEnvio gte =new GeneradorTramaEnvio();
        ManejadorTramaRetorno mtr = new ManejadorTramaRetorno();
        StringBuffer bf = new StringBuffer();
        
        gte.setCodTienda(local);
        gte.setCodCaja(caja);
        //gte.setMonto(1.1);
        //gte.setTipoOperacion(VariablesPinpad.OP_FINANCIERA);
        gte.setTipoOperacion(VariablesPinpad.OP_NO_FINANCIERA);
        
        try
        {
            String tramaEnvio = gte.generarTrama();
            //jta.append("Trama de Envío: "+tramaEnvio+"\n");
            
            if(mtr.iniciarProceso(tramaEnvio))
            {   jta.append("Se inicio correctamente el proceso de comunicación con el Pinpad\n");
                jta.append(mtr.getMensFinOperacion()+"\n");
                
                int i=0;
                String mensaje ="";
                while(i<VariablesPinpad.CICLOS_MAX)
                {   mtr.obtenerInfoProceso();
                    jta.append("Mensaje "+i+"\n");
                    jta.append(mtr.getMensFinOperacion()+"\n");
                    log.debug(mtr.getTramaRetorno());
                    i++;
                    
                    if(mtr.getTipoMensaje().equals(VariablesPinpad.TIPO_MENSJ_PINPAD_IMPR))
                    {   if(mtr.isCortePapel())
                        {   //if(JConfirmDialog.rptaConfirmDialogDefaultNo(null, "¿Desea imprimir reporte?")){
                                mensaje = mensaje.replaceAll(String.valueOf((char)10), "<br/>");
                                mtr.imprVoucher( mensaje);
                            //}
                            mensaje = null;
                        }
                        else{
                            if(mensaje==null)
                                mensaje = mtr.getMensFinOperacion()+"\n";
                            else
                                mensaje = mensaje + mtr.getMensFinOperacion()+"\n";
                        }
                    }
                    
                    if(mtr.isUltMensaje())
                    {   if(mensaje!=null && mensaje!=""){
                            //if(JConfirmDialog.rptaConfirmDialogDefaultNo(null, "¿Desea imprimir reporte?")){
                                mensaje = mensaje.replaceAll(String.valueOf((char)10), "<br/>");
                                mtr.imprVoucher( mensaje);
                            //}
                        }
                        
                        jta.append("El pinpad termino de transmitir mensajes\n");
                        break;
                    }
                    
                    Dimension tamanhoTextArea = jta.getSize();
                    java.awt.Point p = new java.awt.Point(0, tamanhoTextArea.height);
                    jScrollPane1.getViewport().setViewPosition(p);
                    
                    //esperar un tiempo para devolver el msje
                    Thread.sleep(VariablesPinpad.TIMEOUT);
                }
                mtr.finalizarProceso();
                jta.append("Se termino correctamente el proceso de comunicación con el Pinpad");
                jta.append("\n");
            }
            else
            {   jta.append("ERROR: No se inicio correctamente el proceso de comunicación con el Pinpad");
                jta.append("\n");
            }
        }
        catch(Exception ex)
        {   log.error("",ex);
        }
    }
}
