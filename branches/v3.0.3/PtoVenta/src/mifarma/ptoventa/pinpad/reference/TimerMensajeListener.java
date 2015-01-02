package mifarma.ptoventa.pinpad.reference;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import mifarma.ptoventa.pinpad.DlgAnularTransPinpad;
import mifarma.ptoventa.pinpad.DlgInterfacePinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimerMensajeListener implements KeyListener {
    private static final Logger log = LoggerFactory.getLogger(TimerMensajeListener.class);

    public Timer timer;
    public int segundos; //manejar el valor del contador
    public boolean frozen; //manejar el estado del contador

    public JLabel lblMensaje;
    public DlgInterfacePinpad padreVenta;
    public DlgAnularTransPinpad padreAnul;
    public String indicador;

    public TimerMensajeListener() {
        frozen = false;
        timer = new Timer();
        //le asignamos una tarea al timer
        timer.schedule(new RemindTask(), 0, 1000);
        segundos = 10;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        log.debug(e.getKeyChar() + "");
        //Si el lblF11 esta activo y se pulsa cualquier tecla
        if (((JLabel)e.getSource()).isEnabled()) {
            frozen = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    //clase interna que sobre-escribe el metodo run de TimerTask

    class RemindTask extends TimerTask {
        public void run() {
            segundos--;
            lblMensaje.setText("Se continuara con el proceso de cobro en " + segundos + " segundos");
            if (segundos == 0)
                frozen = true;
            if (frozen) {
                log.debug("Terminamos la ejecucion del timer");
                timer.cancel(); //detenemos el timer
                if ("V".equalsIgnoreCase(indicador))
                    padreVenta.cerrarVentana(true);
                else
                    padreAnul.cerrarVentana(true);
            }
        }
    }
}
