package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
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

import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LREQUE      22.02.2007   Creación<br>
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class DlgFiltroZonas extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroZonas.class);

    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JComboBox cmbCampo = new JComboBox();
    private JLabelFunction lblFCerrar = new JLabelFunction();
    private JLabelFunction lblFElegir = new JLabelFunction();

    private JFrame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();

    //public DlgFiltro(JFrame parent, String title, boolean modal, String nameHasTable, String[] codigo, String[] nombre)

    public DlgFiltroZonas(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(365, 136));
        this.setDefaultCloseOperation(0);
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        jPanelTitle1.setBounds(new Rectangle(10, 15, 335, 50));
        jPanelTitle1.setBackground(Color.white);
        jPanelTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jButtonLabel1.setText("Seleccione la opción");
        jButtonLabel1.setBounds(new Rectangle(15, 15, 135, 20));
        jButtonLabel1.setBackground(new Color(255, 130, 14));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('c');

        cmbCampo.setBounds(new Rectangle(145, 15, 170, 20));
        cmbCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbCampo_keyPressed(e);
            }
        });

        lblFCerrar.setBounds(new Rectangle(235, 75, 110, 20));
        lblFCerrar.setText("[ ESC ] Cerrar");
        lblFElegir.setBounds(new Rectangle(110, 75, 115, 20));
        lblFElegir.setText("[ ENTER ] Elegir");
        jPanelTitle1.add(jButtonLabel1, null);
        jPanelTitle1.add(cmbCampo, null);
        pnlBlanco.add(lblFElegir, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(jPanelTitle1, null);
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
    }

    public void initialize() {
        cargaCombo();
    }

    /*MÉTODOS DE MANEJO DE EVENTOS*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(cmbCampo);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cmbCampo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            VariablesProducto.vCodZona =
                    FarmaLoadCVL.getCVLCode(VariablesProducto.vNombreInHashtable, cmbCampo.getSelectedIndex());
            VariablesProducto.vDescZona = (String)cmbCampo.getSelectedItem();

            if (VariablesProducto.vAbreNueva) {
                cerrarVentana(false);
                VariablesProducto.vAbreNueva = false;
                DlgListaProductos dlgProd = new DlgListaProductos(myParentFrame, "Listado de Productos", true);
                dlgProd.setVisible(true);
            } else
                cerrarVentana(true);

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /*MÉTODOS DE LOGICA DE NEGOCIO*/

    private void cargaCombo() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbCampo, VariablesProducto.vNombreInHashtable,
                                   "PTOVENTA_MATRIZ_PROD_ADIC.P_ADIC_OBTIENE_ZONAS", parametros, true);
        /*
    FarmaLoadCVL.loadCVLfromArrays(cmbCampo,
                                    VariablesDistribucion.vNombreInHashtable,
                                    VariablesDistribucion.IND_VALOR_FILTRO,
                                    VariablesDistribucion.IND_DESC_FILTRO,
                                    true);*/
    }
}
