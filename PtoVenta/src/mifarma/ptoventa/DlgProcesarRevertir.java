package mifarma.ptoventa;


import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgProcesarRevertir.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCHAVEZ      21.12.2009   Creación<br>
 * ASOSA        12.01.2010   Modificación<br>
 * <br>
 * @author Jenny Chávez<br>
 * @version 1.0<br>
 *
 */
public class DlgProcesarRevertir extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarRevertir.class);

    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();

    public DlgProcesarRevertir(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            FarmaVariables.vAceptar = false;
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
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        procesar();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Valida que halla conexion con Matriz
     * @author ASOSA
     * Fecha: 12.01.2010
     * @return boolean
     */
    public String validarConexionMatriz() {
        String vIndLineaMatriz = "N";
        try {
            FarmaConnectionRemoto.closeConnection();
            vIndLineaMatriz =
                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            return vIndLineaMatriz;
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
        }
        return vIndLineaMatriz;
    }

    private void procesar() {
        try {
            DBPtoVenta.reviertePruebasEnLocalNuevo(validarConexionMatriz());
            DBPtoVenta.actualizaIndicadorRevertir();
            FarmaUtility.aceptarTransaccion();
            cerrarVentana(true);
        } catch (SQLException sql) {
            if (sql.getErrorCode() == 20000) {
                FarmaUtility.liberarTransaccion();
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al revertir las pruebas. \n" +
                        sql.getMessage(), null);
                cerrarVentana(false);
            } else {
                FarmaUtility.liberarTransaccion();
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al revertir las pruebas. \n" +
                        sql.getMessage(), null);
                cerrarVentana(false);
            }
        }
    }
}
