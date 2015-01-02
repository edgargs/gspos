package mifarma.ptoventa.hilos;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Fidelizacion extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Fidelizacion.class);

    private int tiempoInactividad;
    String pDNI_in = "";
    Object tipoObjeto = null;
    JDialog pDlg = null;
    boolean indTerminoProceso = false;
    Object pLblMensaje = null;
    // asignar nombre a subproceso, llamando al constructor de la superclase

    public Fidelizacion(String vDNI, JDialog pDialogo, Object pObjFoco, Object pLblMensaje_in) {
        pDNI_in = vDNI;
        tipoObjeto = pObjFoco;
        pDlg = pDialogo;
        pLblMensaje = pLblMensaje_in;
    }

    // el método run es el código a ejecutar por el nuevo subproceso

    public void run() {


        try {
            if (indTerminoProceso) {
                Thread.sleep(0);
            }

            operaproceso(pDNI_in);

        }
        // si el subproceso se interrumpió durante su inactividad, imprimir rastreo de la pila
        catch (InterruptedException excepcion) {
            log.error("", excepcion);
        }

        // imprimir nombre del subproceso
        log.info(getName() + " termino su inactividad");

    }

    public void operaproceso(String pDNI_in) {
        /*
       SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA" );
       SubProcesos subproceso2 = new SubProcesos("GET_PROD_ESPECIALES" );
       */
        boolean pResultado = false;

        if (pDNI_in.trim().length() > 0) {

            try {

                pResultado = UtilityFidelizacion.existeDNIenRENIEC(pDNI_in);

                if (!pResultado) {
                    VariablesFidelizacion.vSIN_COMISION_X_DNI = true;
                    ((JLabel)pLblMensaje).setVisible(true);
                    FarmaUtility.showMessage(pDlg, "El DNI no es válido,NO TENDRA COMISION POR LA VENTA.",
                                             (JTextField)tipoObjeto);
                } else {
                    VariablesFidelizacion.vSIN_COMISION_X_DNI = false;
                    ((JLabel)pLblMensaje).setVisible(false);
                }

                indTerminoProceso = true;

            } catch (Exception e) {
                log.error("", e);
                indTerminoProceso = true;
            } finally {
                indTerminoProceso = true;
            }
        } else {
            indTerminoProceso = true;
            VariablesFidelizacion.vSIN_COMISION_X_DNI = false;
            ((JLabel)pLblMensaje).setVisible(false);
        }

    }

    private void cargaIndImpresionRojoTicket() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getIndImprimeRojo();
            log.info("pResultado:" + pResultado);
            if (pResultado.trim().equalsIgnoreCase("S"))
                VariablesPtoVenta.vIndImprimeRojo = true;
            else
                VariablesPtoVenta.vIndImprimeRojo = false;
        } catch (SQLException err) {
            log.error("", err);
            VariablesPtoVenta.vIndImprimeRojo = false;
        }
    }
}
