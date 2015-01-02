package mifarma.ptoventa.caja;


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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgOrdenar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      14.01.2009   Creación<br>
 * <br>
 * @author Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */
public class DlgOrdenar extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgOrdenar.class);

    private Frame myParentFrame;
    private JPanelWhite pnlBlanco = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButtonLabel jButtonLabel2 = new JButtonLabel();
    private JComboBox cmbColumna = new JComboBox();
    private JComboBox cmbOrden = new JComboBox();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblESc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    // KMONCADA 19.09.2014 ORDENAR CORRECTAMENTE LOS REPORTES
    private int indOrigenReporte;

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgOrdenar(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }


    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(401, 168));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Ordenar");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(5, 5, 385, 95));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jButtonLabel1.setText("Columna:");
        jButtonLabel1.setBounds(new Rectangle(70, 15, 70, 20));
        jButtonLabel1.setBackground(new Color(255, 130, 14));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('c');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        jButtonLabel2.setText("Orden:");
        jButtonLabel2.setBounds(new Rectangle(70, 50, 50, 20));
        jButtonLabel2.setBackground(new Color(255, 130, 14));
        jButtonLabel2.setForeground(new Color(255, 130, 14));
        jButtonLabel2.setMnemonic('o');
        jButtonLabel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel2_actionPerformed(e);
            }
        });
        cmbColumna.setBounds(new Rectangle(135, 15, 170, 20));
        cmbColumna.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbColumna_keyPressed(e);
            }
        });
        cmbOrden.setBounds(new Rectangle(135, 50, 125, 20));
        cmbOrden.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                cmbOrden_keyPressed(e);
            }
        });

        lblEnter.setBounds(new Rectangle(10, 110, 120, 20));
        lblEnter.setText("[ ENTER ] Elegir");
        lblESc.setBounds(new Rectangle(290, 110, 95, 20));
        lblESc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(180, 110, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        pnlTitle.add(cmbOrden, null);
        pnlTitle.add(jButtonLabel2, null);
        pnlTitle.add(jButtonLabel1, null);
        pnlTitle.add(cmbColumna, null);
        pnlBlanco.add(lblF11, null);
        pnlBlanco.add(lblESc, null);
        pnlBlanco.add(lblEnter, null);
        pnlBlanco.add(pnlTitle, null);
        this.getContentPane().add(pnlBlanco, null);
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //KMONCADA 19.09.2014 INDICADOR PARA CARGAR LOS COMBOS SEGUN REPORTE.
        if (indOrigenReporte != 1) {
            cargaCombos();
        }
        FarmaUtility.moveFocus(cmbColumna);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            VariablesCaja.vColumna =
                    FarmaLoadCVL.getCVLCode(ConstantsCaja.NOM_HASTABLE_CMBCOLUMNAREMITO, cmbColumna.getSelectedIndex());
            VariablesCaja.vOrden =
                    FarmaLoadCVL.getCVLCode(ConstantsCaja.NOM_HASTABLE_CMBORDENREMITO, cmbOrden.getSelectedIndex());

            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbColumna);
    }

    private void cmbCampo_keyReleased(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbOrden);
    }


    private void cmbColumna_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbOrden);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (cmbColumna.isPopupVisible())
                cmbColumna.setPopupVisible(false);
            else
                chkKeyPressed(e);
        } else {
            chkKeyPressed(e);
        }
    }

    private void cmbOrden_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbColumna);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (cmbOrden.isPopupVisible())
                cmbOrden.setPopupVisible(false);
            else
                chkKeyPressed(e);
        } else {
            chkKeyPressed(e);
        }
    }


    public JComboBox getCmbCampo() {
        return this.cmbColumna;
    }

    //KMONCADA 19.09.2014 METODOS SET - GET PARA CONFIGURAR TIPO DE REPORTE

    public JComboBox getCmbOrden() {
        return this.cmbOrden;
    }

    public void setIndOrigenReporte(int indOrigenReporte) {
        this.indOrigenReporte = indOrigenReporte;
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cargaCombos() {

        log.debug("CARGA COMBOS");
        FarmaLoadCVL.loadCVLfromArrays(cmbColumna, ConstantsCaja.NOM_HASTABLE_CMBCOLUMNAREMITO,
                                       ConstantsCaja.vDescColum, ConstantsCaja.vCodColum, true);

        FarmaLoadCVL.loadCVLfromArrays(cmbOrden, ConstantsCaja.NOM_HASTABLE_CMBORDENREMITO, ConstantsCaja.vDescOrden,
                                       ConstantsCaja.vCodOrden, true);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


}
