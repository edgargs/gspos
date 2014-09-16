package mifarma.ptoventa.pinpad;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.pinpad.reference.UtilityPinpad;
import mifarma.ptoventa.pinpad.visa.HiloProcesoPinpadNoFinan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgTransaccionesPinpad extends JDialog
{
    private static final Logger log = LoggerFactory.getLogger(DlgTransaccionesPinpad.class);
    private JTextArea jTextArea1 = new JTextArea();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();

    public DlgTransaccionesPinpad() {
        this(null, "", false);
    }

    public DlgTransaccionesPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(411, 456));
        this.getContentPane().setLayout( null );
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(270, 390, 125, 30));
        lblEsc.setFocusable(false);
        lblF11.setText("[F11] Iniciar Transacción");
        lblF11.setBounds(new Rectangle(10, 390, 165, 30));
        lblF11.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblF11_keyPressed(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(10, 50, 390, 315));
        jScrollPane1.getViewport().add(jTextArea1, null);
        this.getContentPane().add(jScrollPane1, null);
        this.getContentPane().add(lblF11, null);
        this.getContentPane().add(lblEsc, null);
        this.setResizable(false);
        this.setTitle("Interface de Pinpad");
        this.addWindowListener(new WindowAdapter() {

                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        
    }

    private void lblF11_keyPressed(KeyEvent e)
    {   if(lblF11.isEnabled() && mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
        {   HiloProcesoPinpadNoFinan hilo = new HiloProcesoPinpadNoFinan();
            hilo.caja = FarmaVariables.vCodLocal;
            hilo.local = FarmaVariables.vCodLocal;
            hilo.jta = jTextArea1;
            hilo.jScrollPane1=jScrollPane1;
            hilo.start();
        }
        else if(lblF11.isEnabled() && e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
    }

    private void this_windowOpened(WindowEvent e)
    {   if(UtilityPinpad.validarLibrerias())
        {   FarmaUtility.centrarVentana(this);
            lblF11.grabFocus();
        }
        else
            cerrarVentana(false);
    }
    
    private void cerrarVentana(boolean pAceptar)
    {   FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
}
