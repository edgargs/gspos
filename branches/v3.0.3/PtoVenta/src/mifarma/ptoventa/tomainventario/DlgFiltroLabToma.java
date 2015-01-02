package mifarma.ptoventa.tomainventario;


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

import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltroLabToma.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * Dubilluz      30.07.2007   Creación<br>
 * <br>
 * @author Diego Ubilluz Carrillo<br>
 * @version 1.0<br>
 *
 */
public class DlgFiltroLabToma extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroLabToma.class);

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

    public DlgFiltroLabToma() {
        this(null, "", false);
    }

    public DlgFiltroLabToma(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(443, 143));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Filtrar Laboratorios");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 15, 425, 60));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        btnSeleccion.setText("Seleccione Estado:");
        btnSeleccion.setBounds(new Rectangle(10, 15, 115, 20));
        btnSeleccion.setBackground(new Color(255, 130, 14));
        btnSeleccion.setForeground(new Color(255, 130, 14));
        btnSeleccion.setMnemonic('S');
        btnSeleccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSeleccion_actionPerformed(e);
            }
        });
        cmbCampo.setBounds(new Rectangle(130, 15, 285, 20));
        cmbCampo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbCampo_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(330, 85, 95, 20));
        lblEsc.setText("[ ESC ] Escape");
        lblEnter.setBounds(new Rectangle(225, 85, 95, 20));
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
        cargaCombosFiltro();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void cargaCombosFiltro() {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesTomaInv.vSecToma.trim());
        FarmaLoadCVL.loadCVLFromSP(cmbCampo, ConstantsTomaInv.NOM_HASTABLE_CMBESTADO_LAB,
                                   "PTOVENTA_TOMA_INV.INV_GET_ESTADOS_LAB_TINV(?,?,?)", parametros, true, true);
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
            VariablesTomaInv.vCodFiltroEstado =
                    FarmaLoadCVL.getCVLCode(ConstantsTomaInv.NOM_HASTABLE_CMBESTADO_LAB, cmbCampo.getSelectedIndex());
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
