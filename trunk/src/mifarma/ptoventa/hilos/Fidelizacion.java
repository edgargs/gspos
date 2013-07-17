package mifarma.ptoventa.hilos;

import java.sql.SQLException;

import java.util.Collections;

import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableComparator;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

public class Fidelizacion extends Thread {
    private int tiempoInactividad;
    String pDNI_in = "";
    Object tipoObjeto = null;
    JDialog pDlg = null;
    boolean indTerminoProceso = false;
    Object pLblMensaje = null;
    // asignar nombre a subproceso, llamando al constructor de la superclase

    public Fidelizacion(String vDNI,JDialog pDialogo,Object pObjFoco,Object pLblMensaje_in) {
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
            excepcion.printStackTrace();
        }

        // imprimir nombre del subproceso
        System.err.println(getName() + " termino su inactividad");

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
                    FarmaUtility.showMessage(pDlg, 
                                             "El DNI no es válido,NO TENDRA COMISION POR LA VENTA.", 
                                             (JTextField)tipoObjeto);
                } else {
                    VariablesFidelizacion.vSIN_COMISION_X_DNI = false;
                    ((JLabel)pLblMensaje).setVisible(false);
                }

                indTerminoProceso = true;

            } catch (Exception e) {
                e.printStackTrace();
                indTerminoProceso = true;
            } finally {
                indTerminoProceso = true;
            }
        }
        else{
            indTerminoProceso = true;
            VariablesFidelizacion.vSIN_COMISION_X_DNI = false;
            ((JLabel)pLblMensaje).setVisible(false);
        }

    }

    private void cargaIndImpresionRojoTicket() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getIndImprimeRojo();
            System.err.println("pResultado:" + pResultado);
            if (pResultado.trim().equalsIgnoreCase("S"))
                VariablesPtoVenta.vIndImprimeRojo = true;
            else
                VariablesPtoVenta.vIndImprimeRojo = false;
        } catch (SQLException err) {
            err.printStackTrace();
            VariablesPtoVenta.vIndImprimeRojo = false;
        }
    }
}
