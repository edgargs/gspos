package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgPruebaImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgPruebaImpresora extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgPruebaImpresora.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblImpresora = new JLabelWhite();
    private JComboBox cmbImpresoras = new JComboBox();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgPruebaImpresora() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgPruebaImpresora(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Prueba Impresora");
        this.setSize(new Dimension(351, 148));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(240, 90, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(125, 90, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        pnlTitle1.setBounds(new Rectangle(10, 15, 325, 60));
        lblImpresora.setText("Impresora:");
        lblImpresora.setBounds(new Rectangle(10, 20, 95, 20));
        cmbImpresoras.setBounds(new Rectangle(110, 20, 200, 20));
        cmbImpresoras.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbImpresoras_keyPressed(e);
            }
        });
        pnlTitle1.add(cmbImpresoras);
        pnlTitle1.add(lblImpresora);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initCmbImpresora();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCmbImpresora() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaLoadCVL.loadCVLFromSP(cmbImpresoras, ConstantsCaja.NOM_HASHTABLE_CMBIMPRESORA,
                                   "PTOVENTA_CAJ_ANUL.CAJ_GET_IMPRESORAS(?,?)", parametros, true);
        parametros = null;
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(cmbImpresoras);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cmbImpresoras_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void funcionF11() {
        if (validarCampo())
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¡Asegúrese de cambiar de Papel!"))
                realizarPruebaImpresion();
    }

    private boolean validarCampo() {
        boolean retorno = true;

        if (cmbImpresoras.getSelectedIndex() == -1) {
            FarmaUtility.showMessage(this, "Debe seleccionar una impresora.", cmbImpresoras);
            retorno = false;
        }

        return retorno;
    }

    private void realizarPruebaImpresion() {
        VariablesCaja.vRutaImpresora =
                FarmaLoadCVL.getCVLCode(ConstantsCaja.NOM_HASHTABLE_CMBIMPRESORA, cmbImpresoras.getSelectedIndex());
        VariablesCaja.vTipComp = cmbImpresoras.getSelectedItem().toString();
        try {
            UtilityCaja.imprimePrueba();
            FarmaUtility.showMessage(this, "Prueba enviada.", cmbImpresoras);
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un erro al intentar imprimir.\n" +
                    e, cmbImpresoras);
        }
        VariablesCaja.vTipComp = "";
    }
}
