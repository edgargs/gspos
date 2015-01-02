package mifarma.ptoventa.matriz.mantenimientos.productos;


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

import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      20.11.2007   Creación<br>
 * <br>
 * @author Jorge Luis Cortez<br>
 * @version 1.0<br>
 *
 */

public class DlgFiltro extends JDialog {
    /* ************************************************************************ */
    /*                         DECLARACION PROPIEDADES                          */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgFiltro.class);

    private Frame myParentFrame;
    ArrayList parametros;

    private JPanelWhite jContentPane = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel btnSeleccion = new JButtonLabel();
    private JComboBox cmbTipo = new JComboBox();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgFiltro() {
        this(null, "", false);
    }

    public DlgFiltro(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(395, 130));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Filtrar Listado");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        cmbTipo.setBounds(new Rectangle(175, 15, 180, 20));
        cmbTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipo_keyPressed(e);
            }
        });

        btnSeleccion.setText("Seleccione Tipo :");
        btnSeleccion.setBounds(new Rectangle(10, 15, 160, 20));
        btnSeleccion.setBackground(new Color(255, 130, 14));
        btnSeleccion.setForeground(new Color(255, 130, 14));
        btnSeleccion.setMnemonic('S');
        btnSeleccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSeleccion_actionPerformed(e);
            }
        });


        pnlTitle.setBounds(new Rectangle(5, 10, 375, 50));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblEnter.setBounds(new Rectangle(95, 70, 135, 20));
        lblEnter.setText("[ ENTER ] Elegir");
        lblEsc.setBounds(new Rectangle(240, 70, 140, 20));
        lblEsc.setText("[ ESC ] Escape");


        pnlTitle.add(btnSeleccion, null);
        pnlTitle.add(cmbTipo, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(pnlTitle, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, null);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        cargar_cmbTipo();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    /**
     * Carga los Items  al cmbTipo
     */
    private void cargar_cmbTipo() {
        //Ubicacion
        FarmaLoadCVL.loadCVLfromArrays(cmbTipo, ConstantsProducto.NOM_HASTABLE_CMBFILTROPROD,
                                       ConstantsProducto.vCodColumna, ConstantsProducto.vDescColumna, true);
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbTipo);
    }

    private void btnSeleccion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipo);
    }

    private void cmbTipo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesProducto.vIndNuevo =
                    FarmaLoadCVL.getCVLCode(ConstantsProducto.NOM_HASTABLE_CMBFILTROPROD, cmbTipo.getSelectedIndex());
            log.debug("filtro : " + VariablesProducto.vIndNuevo);
            cerrarVentana(true);

            //VariablesProducto.vIndNuevo=ConstantsProducto.IND_NO_NUEVO;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (cmbTipo.isPopupVisible())
                cmbTipo.setPopupVisible(false);
            else
                chkKeyPressed(e);
        } else {
            chkKeyPressed(e);
        }

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

}
