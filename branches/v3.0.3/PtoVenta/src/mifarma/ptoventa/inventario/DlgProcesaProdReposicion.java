package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesaProdReposicion extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgProcesaProdReposicion.class);

    FarmaTableModel pTableModel;
    String codFil;
    String descFil;
    private boolean indStkCero = false;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgProcesaProdReposicion() {
        this(null, "", false);
        try {
            jbInit();

        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgProcesaProdReposicion(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();

        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(240, 103));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Procesando Informacion . . .");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if (codFil.equals(ConstantsInventario.IND_PORCENTAJE) && descFil.equals(ConstantsInventario.IND_PORCENTAJE)) {
            //log.debug("Todos");
            log.debug("!!!!Todos" + indStkCero);
            if (indStkCero) {
                cargaListaProductosReposicionStkCero();
                actualizaIndLinea();
            } else {
                cargaListaProductosReposicion();
                actualizaIndLinea();
            }
        } else {
            //log.debug("Filtro");
            cargaListaProductosReposicion_filtro();
            actualizaIndLinea();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public void cargaListaProductosReposicion() {
        try {
            DBInventario.cargaListaProductosReposicion(pTableModel);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos de reposición : \n" +
                    sql.getMessage(), null);
        }
        cerrarVentana(true);
    }

    public void cargaListaProductosReposicion_filtro() {
        try {
            DBInventario.cargaListaProductosReposicion_filtro(pTableModel, codFil, descFil);
            log.debug("Paso el filtro en DLG");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al cargar la lista de productos de reposición-filtro : \n" +
                    sql.getMessage(), null);
        }
        cerrarVentana(true);
    }

    public void setVariables(FarmaTableModel pTableModel, String codFil, String descFil) {
        this.pTableModel = pTableModel;
        this.codFil = codFil;
        this.descFil = descFil;
    }

    public void setIndStkCero(boolean indStkCero) {
        this.indStkCero = indStkCero;
    }

    public void cargaListaProductosReposicionStkCero() {
        try {
            DBInventario.cargaListaProductosReposicionStkCero(pTableModel);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos de reposición : \n" +
                    sql.getMessage(), null);
        }
        cerrarVentana(true);
    }


    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  11/06/2007
     * */

    public void actualizaIndLinea() {
        try {
            DBInventario.actualizaIndLinea();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al actualizar el estado en linea : \n" +
                    sql.getMessage(), null);
        }
        cerrarVentana(true);
    }
}
