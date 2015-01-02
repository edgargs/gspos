package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltroGuias.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.10.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgFiltroGuias extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgFiltroGuias.class);

    private Frame myParentFrame;
    ArrayList parametros;

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private JPanelWhite jContentPane = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnSeleccion = new JButtonLabel();
    private JComboBox cmbCampo = new JComboBox();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    private String TipoPadre;

    public DlgFiltroGuias() {
        this(null, "", false);
    }

    public DlgFiltroGuias(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        TipoPadre = title;
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
        this.setSize(new Dimension(406, 143));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Filtrar");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 15, 380, 60));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        btnSeleccion.setText("Seleccione:");
        btnSeleccion.setBounds(new Rectangle(10, 15, 70, 20));
        btnSeleccion.setBackground(new Color(255, 130, 14));
        btnSeleccion.setForeground(new Color(255, 130, 14));
        btnSeleccion.setMnemonic('S');
        btnSeleccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSeleccion_actionPerformed(e);
            }
        });
        cmbCampo.setBounds(new Rectangle(85, 15, 285, 20));
        cmbCampo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbCampo_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(290, 85, 95, 20));
        lblEsc.setText("[ ESC ] Escape");
        lblEnter.setBounds(new Rectangle(185, 85, 95, 20));
        lblEnter.setText("[ ENTER ] Elegir");
        pnlTitle1.add(btnSeleccion, null);
        pnlTitle1.add(cmbCampo, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        if (VariablesInventario.vTipoNota.equals(ConstantsPtoVenta.TIP_NOTA_INGRESO))
            cargaComboIngreso();
        else if (VariablesInventario.vTipoNota.equals(ConstantsPtoVenta.TIP_NOTA_SALIDA))
            cargaCombosTransf();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void cargaComboIngreso() {

    }

    private void cargaCombosTransf() {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbCampo, VariablesInventario.vNomInHashtableGuias,
                                   "PTOVENTA_INV.INV_GET_FILTRO_TRANSF(?)", parametros, true, true);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbCampo);
    }

    private void btnSeleccion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbCampo);
    }

    private void cmbCampo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesInventario.vCodFiltro =
                    FarmaLoadCVL.getCVLCode(VariablesInventario.vNomInHashtableGuias, cmbCampo.getSelectedIndex());
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
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

}
