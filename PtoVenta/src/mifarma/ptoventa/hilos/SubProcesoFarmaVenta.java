package mifarma.ptoventa.hilos;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.main.FrmEconoFar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SubProcesoFarmaVenta extends Thread {

    private static final Logger log = LoggerFactory.getLogger(SubProcesoFarmaVenta.class);

    private int tiempoInactividad;
    String pNombre = "";
    boolean indTerminoProceso = false;
    String pSecUsuLocales = "";
    FrmEconoFar frmEnviar; //= new FrmEconoFar();
    // asignar nombre a subproceso, llamando al constructor de la superclase

    public SubProcesoFarmaVenta(String nombre, FrmEconoFar vFrm) {
        pSecUsuLocales = nombre;
        frmEnviar = vFrm;
    }

    // el método run es el código a ejecutar por el nuevo subproceso

    public void run() {
        //Timer timer = new Timer();
        //timer.scheduleAtFixedRate(timerTask, 0, 1000);
        /*
       Timer timer = new Timer("1000", new ActionListener ()
       {
           public void actionPerformed(ActionEvent e)
           {
                if(!pSecUsuLocales.trim().equalsIgnoreCase(FarmaVariables.vNuSecUsu)){
                    // imprimir nombre del subproceso
                    log.debug( getName() + " Cambiaaa USUU!!");
                    pSecUsuLocales = FarmaVariables.vNuSecUsu;
                    frmEnviar.verificaRolUsuNuevo();
                }
            }
       });
       timer.start();*/

        while (true) {
            //log.debug( pSecUsuLocales + " +"+pSecUsuLocales);
            // log.debug( FarmaVariables.vNuSecUsu + " +"+FarmaVariables.vNuSecUsu);
            if (!pSecUsuLocales.trim().equalsIgnoreCase(FarmaVariables.vNuSecUsu)) {
                // imprimir nombre del subproceso
                log.debug(getName() + " Cambiaaa USUU!!");
                pSecUsuLocales = FarmaVariables.vNuSecUsu;
                frmEnviar.verificaRolUsuNuevo();
                //ERIOS 2.2.8 Carga variables
                frmEnviar.obtieneInfoLocal();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }
        /*
         try{
              if(!pSecUsuLocales.trim().equalsIgnoreCase(FarmaVariables.vNuSecUsu)){
                  // imprimir nombre del subproceso
                  log.debug( getName() + " Cambiaaa USUU!!");
                  pSecUsuLocales = FarmaVariables.vNuSecUsu;
                  frmEnviar.verificaRolUsuNuevo();
              }
          }
          // si el subproceso se interrumpió durante su inactividad, imprimir rastreo de la pila
          catch ( Exception excepcion ) {
             log.error("",exception);
          }*/

    }


}
