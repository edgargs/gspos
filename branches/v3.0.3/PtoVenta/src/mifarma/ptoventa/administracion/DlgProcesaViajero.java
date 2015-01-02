package mifarma.ptoventa.administracion;


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

import mifarma.ptoventa.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesaViajero extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProcesaViajero.class);

    public String vQuery = "";
    FarmaTableModel tableModelProcesar;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();


    public DlgProcesaViajero() {
        this(null, "", false);
    }

    public DlgProcesaViajero(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();

        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(238, 104));
        this.getContentPane().setLayout(null);
        this.setTitle("Procesando Información . . .");
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        try {
            DBPtoVenta.realizaViajero();
            FarmaUtility.showMessage(this, "Proceso Realizado satisfactoriamente", null);
            cerrarVentana(true);
        } catch (SQLException err) {
            log.error("", err);
            FarmaUtility.showMessage(this, "Error al reprocesar viajero. \n " + err.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

}
