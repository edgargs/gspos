package mifarma.ptoventa.hilos;

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

public class SubProcesosBTLMF extends Thread {
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

    // el m�todo run es el c�digo a ejecutar por el nuevo subproceso

    public void run() {


        try {
            if (indTerminoProceso) {
                Thread.sleep(0);
            }

            operaproceso(pNombre);


        }
        // si el subproceso se interrumpi� durante su inactividad, imprimir rastreo de la pila
        catch (InterruptedException excepcion) {
            excepcion.printStackTrace();
        }

        // imprimir nombre del subproceso
        System.err.println(getName() + " termino su inactividad");

    }


    public void operaproceso(String pNombre_2) {
        try {


            if (pNombre_2.trim().toUpperCase().equalsIgnoreCase("CONS_SALD_CREDITO_BENIF")) {
                System.err.println("inicio Consulta Saldo Credito Benificiario");

                if (UtilityConvenioBTLMF.consultarSaldCreditoBenif(pdialog).equals("S")) {
                    FarmaUtility.showMessage(pdialog,
                                             "El Saldo Cr�dito del Benificiario es Insuficiente!!",
                                             "");

                }
                System.err.println("fin Consulta Saldo Credito Benificiario");
                indTerminoProceso = true;
            } else {
                if (pNombre_2.trim().toUpperCase().equalsIgnoreCase("CARGA_LISTA_PRECIOS_CONV")) {
                    System.out.println("inicio Consulta cargar precios por conv");
                    //metodo que carga los precios de convenios
                    cargarListaPrecios();
                    System.out.println("fin Consulta cargar precios por conv");
                    indTerminoProceso = true;
                    /*if(VariablesConvenioBTLMF.vDatosPreciosConv.size()==0)
                        FarmaUtility.showMessage(pdialog,
                                                 "No se cargo los precios de Convenio "+VariablesConvenioBTLMF.vCodConvenio+",Verificar!!",
                                                 "");*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            indTerminoProceso = true;
        } finally {
            indTerminoProceso = true;
        }
    }


    public void cargarListaPrecios() {
        try {

            VariablesConvenioBTLMF.vDatosPreciosConv.clear();

            System.out.println("Entro a cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio);
            DBConvenioBTLMF.cargarListaPrecios(VariablesConvenioBTLMF.vDatosPreciosConv);
            System.out.println("Termino de cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio +
                               ", Tama�o: " +
                               VariablesConvenioBTLMF.vDatosPreciosConv.size());


        } catch (SQLException e) {
            System.out.println("Error a cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio +
                               ", Error-->" + e.getMessage());
            e.printStackTrace();
        }
    }


}
