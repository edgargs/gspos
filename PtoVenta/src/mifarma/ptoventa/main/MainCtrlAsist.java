package mifarma.ptoventa.main;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainCtrlAsist extends ReadProperties{
    
    static private final Logger log = LoggerFactory.getLogger(MainCtrlAsist.class);
    
    public MainCtrlAsist() {
        init();
    }
    
    /**
     * Constructor de control de asistencia que recibe parametros de properties
     * @param arch1 Properties FarmaVenta
     * @param arch2 Properties Clave
     * @param arch3 Properties Servidor Remoto
     * @param paramComodin Solo para diferenciar constructores
     * @author ASOSA
     * @since 22/10/2015
     */
    public MainCtrlAsist(String arch1, 
                    String arch2, 
                    String arch3, 
                    String paramComodin) {
        prop1 = arch1;
        prop2 = arch2;
        prop3 = arch3;
        init();
    }

    public static void main(String[] args) {
        if (args.length == 4) {  //ASOSA - 16/10/2015 - Llamada al menú de control de asistencia.CTRLASIST
            log.debug(args[0]);
            log.debug(args[1]);
            log.debug(args[2]);
            log.debug(args[3]);
            new MainCtrlAsist(args[0], args[1], args[2], "CtrlAsist");
        }else{
            new MainCtrlAsist();
        }
    }

    @Override
    void init() {
        if (readFileProperties() && readFilePasswordProperties() && readFileServRemotosProperties()) {
            Frame frame = new FrmCtrlAsist();
            frame.setLocationRelativeTo(null);
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            frame.setVisible(true);
        }
    }
}
