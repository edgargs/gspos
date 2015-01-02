package mifarma.ptoventa.hilos;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableComparator;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubProcesosBTLMF extends JDialogProgress {
    private static final Logger log = LoggerFactory.getLogger(SubProcesosBTLMF.class);  

    private int tiempoInactividad;
    String pNombre = "";
    boolean indTerminoProceso = false;
    JFrame pdialog = new JFrame();
    JDialog dialog;


    // asignar nombre a subproceso, llamando al constructor de la superclase

    public SubProcesosBTLMF(String nombre, Frame pdialog, JDialog jdialog) {
        pNombre = nombre;
        dialog = jdialog;
    }

    // el método run es el código a ejecutar por el nuevo subproceso

    public void run() {


        try {
            if (indTerminoProceso) {
                Thread.sleep(0);
            }

            operaproceso(pNombre);


        }
        // si el subproceso se interrumpió durante su inactividad, imprimir rastreo de la pila
        catch (InterruptedException excepcion) {
            log.error("",excepcion);
        }

        // imprimir nombre del subproceso
        log.info(getName() + " termino su inactividad");

    }


    public void operaproceso(String pNombre_2) {
        try {


            if (pNombre_2.trim().toUpperCase().equalsIgnoreCase("CONS_SALD_CREDITO_BENIF")) {
                log.info("inicio Consulta Saldo Credito Benificiario");

                if (UtilityConvenioBTLMF.consultarSaldCreditoBenif(pdialog).equals("S")) {
                    FarmaUtility.showMessage(pdialog,
                                             "El Saldo Crédito del Benificiario es Insuficiente!!",
                                             "");

                }
                log.info("fin Consulta Saldo Credito Benificiario");
                indTerminoProceso = true;
            } else {
                if (pNombre_2.trim().toUpperCase().equalsIgnoreCase("CARGA_LISTA_PRECIOS_CONV")) {
                    //DJARA 07.04.2014 , Comentado porque no debe cargar precios de convenios
                    /*log.debug("inicio Consulta cargar precios por conv");
                    //metodo que carga los precios de convenios
                    cargarListaPrecios();
                    log.debug("fin Consulta cargar precios por conv");*/
                    indTerminoProceso = true;
                    /*if(VariablesConvenioBTLMF.vDatosPreciosConv.size()==0)
                        FarmaUtility.showMessage(pdialog,
                                                 "No se cargo los precios de Convenio "+VariablesConvenioBTLMF.vCodConvenio+",Verificar!!",
                                                 "");*/
                }
            }
        } catch (Exception e) {
            log.error("",e);
            indTerminoProceso = true;
        } finally {
            indTerminoProceso = true;
        }
    }


//DJARA 07.04.2014 , Comentado porque no debe cargar precios de convenios
/*public void cargarListaPrecios() {
        try {

            VariablesConvenioBTLMF.vDatosPreciosConv.clear();

            log.debug("Entro a cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio);
            DBConvenioBTLMF.cargarListaPrecios(VariablesConvenioBTLMF.vDatosPreciosConv);
            log.debug("Termino de cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio +
                               ", Tamaño: " +
                               VariablesConvenioBTLMF.vDatosPreciosConv.size());


        } catch (SQLException e) {
            log.debug("Error a cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio +
                               ", Error-->" + e.getMessage());
            log.error("",e);
        }
    }
*/


    @Override
    public void ejecutaProceso() {
        operaproceso(pNombre);
    }
}
