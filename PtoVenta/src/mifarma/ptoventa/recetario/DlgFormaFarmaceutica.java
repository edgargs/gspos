package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgFormaFarmaceutica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      12.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgFormaFarmaceutica extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgFormaFarmaceutica.class);
    private FacadeRecetario facadeRecetario;

    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel pnlFarmaceutica = new JPanel();
    private JButton lblEsterilT = new JButton();
    private JButton lblFormaT = new JButton();
    private JButton lblContenidoT = new JButton();
    private JButton lblPreparadoT = new JButton();
    private JComboBox<String> cmbEsteril = new JComboBox<String>();
    private JComboBox<String> cmbForma = new JComboBox<String>();
    private JTextFieldSanSerif txtContenido = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPreparados = new JTextFieldSanSerif();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblAceptar = new JLabelFunction();

    ArrayList<ArrayList<String>> lstFormaFarmac = new ArrayList<ArrayList<String>>();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgFormaFarmaceutica() {
        this(null, "", false);
    }

    public DlgFormaFarmaceutica(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(371, 259));
        this.getContentPane().setLayout(borderLayout1);
        this.setForeground(Color.white);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Forma Farmacéutica");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setLayout(null);

        jContentPane.setForeground(Color.white);
        jContentPane.setBackground(Color.white);
        pnlFarmaceutica.setFont(new Font("SansSerif", 0, 12));
        pnlFarmaceutica.setLayout(null);
        pnlFarmaceutica.setBackground(new Color(255, 130, 14));
        pnlFarmaceutica.setBounds(new Rectangle(15, 10, 335, 165));

        pnlFarmaceutica.setForeground(new Color(255, 130, 14));
        pnlFarmaceutica.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(260, 185, 90, 20));
        lblEsc.setFocusable(false);
        lblAceptar.setText("[ F11 ] Aceptar");
        lblAceptar.setBounds(new Rectangle(155, 185, 90, 20));

        lblAceptar.setFocusable(false);
        lblEsterilT.setText("* Est\u00e9ril : ");
        lblEsterilT.setDefaultCapable(false);
        lblEsterilT.setRequestFocusEnabled(false);
        lblEsterilT.setBorderPainted(false);
        lblEsterilT.setFocusPainted(false);
        lblEsterilT.setContentAreaFilled(false);
        lblEsterilT.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lblEsterilT.setHorizontalAlignment(SwingConstants.LEFT);
        lblEsterilT.setMnemonic('E');
        lblEsterilT.setFont(new Font("SansSerif", 1, 11));
        lblEsterilT.setBounds(new Rectangle(20, 25, 45, 20));

        lblEsterilT.setForeground(new Color(247, 247, 247));
        lblEsterilT.setFocusable(false);
        lblEsterilT.setSize(new Dimension(85, 25));
        lblEsterilT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblEsterilT_actionPerformed(e);
            }
        });
        lblFormaT.setText("* Forma: ");
        lblFormaT.setDefaultCapable(false);
        lblFormaT.setRequestFocusEnabled(false);
        lblFormaT.setBorderPainted(false);
        lblFormaT.setFocusPainted(false);
        lblFormaT.setContentAreaFilled(false);
        lblFormaT.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lblFormaT.setHorizontalAlignment(SwingConstants.LEFT);
        lblFormaT.setMnemonic('F');
        lblFormaT.setFont(new Font("SansSerif", 1, 11));
        lblFormaT.setBounds(new Rectangle(20, 55, 45, 20));

        lblFormaT.setForeground(new Color(247, 247, 247));
        lblFormaT.setFocusable(false);
        lblFormaT.setSize(new Dimension(85, 25));
        lblFormaT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFormaT_actionPerformed(e);
            }
        });
        lblContenidoT.setText("* Contenido: ");
        lblContenidoT.setDefaultCapable(false);
        lblContenidoT.setRequestFocusEnabled(false);
        lblContenidoT.setBorderPainted(false);
        lblContenidoT.setFocusPainted(false);
        lblContenidoT.setContentAreaFilled(false);
        lblContenidoT.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lblContenidoT.setHorizontalAlignment(SwingConstants.LEFT);
        lblContenidoT.setMnemonic('C');
        lblContenidoT.setFont(new Font("SansSerif", 1, 11));
        lblContenidoT.setBounds(new Rectangle(20, 85, 63, 25));

        lblContenidoT.setForeground(new Color(247, 247, 247));
        lblContenidoT.setFocusable(false);
        lblContenidoT.setSize(new Dimension(85, 25));
        lblContenidoT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblContenidoT_actionPerformed(e);
            }
        });
        lblPreparadoT.setText("* Preparados: ");
        lblPreparadoT.setDefaultCapable(false);
        lblPreparadoT.setRequestFocusEnabled(false);
        lblPreparadoT.setBorderPainted(false);
        lblPreparadoT.setFocusPainted(false);
        lblPreparadoT.setContentAreaFilled(false);
        lblPreparadoT.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lblPreparadoT.setHorizontalAlignment(SwingConstants.LEFT);
        lblPreparadoT.setMnemonic('P');
        lblPreparadoT.setFont(new Font("SansSerif", 1, 11));
        lblPreparadoT.setBounds(new Rectangle(20, 115, 85, 25));

        lblPreparadoT.setForeground(new Color(247, 247, 247));
        lblPreparadoT.setFocusable(false);
        lblPreparadoT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblPreparadoT_actionPerformed(e);
            }
        });
        cmbEsteril.setBounds(new Rectangle(105, 25, 80, 20));
        cmbEsteril.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbEsteril_keyPressed(e);
            }
        });
        cmbForma.setBounds(new Rectangle(105, 55, 180, 20));
        cmbForma.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbForma_keyPressed(e);
            }
        });
        txtContenido.setBounds(new Rectangle(105, 85, 45, 20));
        txtContenido.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtContenido_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtContenido_keyTyped(e);
            }
        });
        txtPreparados.setBounds(new Rectangle(105, 115, 85, 20));
        txtPreparados.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPreparados_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtPreparados_keyTyped(e);
            }
        });
        pnlFarmaceutica.add(txtPreparados, null);
        pnlFarmaceutica.add(txtContenido, null);
        pnlFarmaceutica.add(cmbForma, null);
        pnlFarmaceutica.add(cmbEsteril, null);
        pnlFarmaceutica.add(lblPreparadoT, null);
        pnlFarmaceutica.add(lblContenidoT, null);
        pnlFarmaceutica.add(lblFormaT, null);
        pnlFarmaceutica.add(lblEsterilT, null);
        jContentPane.add(pnlFarmaceutica, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblAceptar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        txtContenido.setLengthText(3);
        txtPreparados.setLengthText(3);

        FarmaUtility.centrarVentana(this);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        cargaComboIndEsteril();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void cargaComboIndEsteril() {
        String codigo[] = { "S", "N" }, valor[] = { "SI", "NO" };
        FarmaLoadCVL.loadCVLfromArrays(cmbEsteril, "Esteril", codigo, valor, true);
    }

    private void cargaValoresTemporales() {
        if (VariablesRecetario.vArrayEsteril != null) {
            cmbEsteril.setSelectedIndex(new Integer(VariablesRecetario.vArrayEsteril.get(6).toString()));
            cmbForma.setSelectedIndex(new Integer(VariablesRecetario.vArrayEsteril.get(7).toString()));
            txtContenido.setText(VariablesRecetario.vArrayEsteril.get(2).toString());
            txtPreparados.setText(VariablesRecetario.vArrayEsteril.get(3).toString());
        }
    }

    private void cargarComboFormaFarmac() {
        this.lstFormaFarmac = facadeRecetario.listaFormaFarmac();
        FarmaLoadCVL.loadCVLFromArrayList(cmbForma, "FormaFarmaceutica", lstFormaFarmac, true);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void lblEsterilT_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbEsteril);
    }

    private void lblFormaT_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbForma);
    }

    private void lblContenidoT_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtContenido);
    }

    private void lblPreparadoT_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPreparados);
    }

    private void cmbEsteril_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbForma);
        } else
            chkKeyPressed(e);
    }

    private void cmbForma_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtContenido);
        } else
            chkKeyPressed(e);
    }

    private void txtContenido_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPreparados);
        } else
            chkKeyPressed(e);
    }

    private void txtContenido_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtContenido, e);
    }

    private void txtPreparados_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbEsteril);
        } else
            chkKeyPressed(e);
    }

    private void txtPreparados_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtPreparados, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        cargarComboFormaFarmac();
        cargaValoresTemporales();

        FarmaUtility.moveFocus(cmbEsteril);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            ArrayList<Object> tmpArrayFormas = new ArrayList<Object>();
            if (!cmbEsteril.getSelectedItem().toString().equals("") &&
                !cmbForma.getSelectedItem().toString().equals("") && !txtContenido.getText().trim().equals("") &&
                !txtPreparados.getText().trim().equals("")) {
                tmpArrayFormas.add(cmbEsteril.getSelectedItem().toString());
                tmpArrayFormas.add(cmbForma.getSelectedItem().toString());
                tmpArrayFormas.add(txtContenido.getText().trim());
                tmpArrayFormas.add(txtPreparados.getText().trim());
                tmpArrayFormas.add(FarmaLoadCVL.getCVLCode("Esteril", cmbEsteril.getSelectedIndex()));
                tmpArrayFormas.add(FarmaLoadCVL.getCVLCode("FormaFarmaceutica", cmbForma.getSelectedIndex()));
                tmpArrayFormas.add(cmbEsteril.getSelectedIndex());
                tmpArrayFormas.add(cmbForma.getSelectedIndex());

                String costo_form_farm_sel =
                    ((ArrayList)this.lstFormaFarmac.get(cmbForma.getSelectedIndex())).get(2).toString();
                tmpArrayFormas.add(costo_form_farm_sel);
                VariablesRecetario.vArrayEsteril = tmpArrayFormas;
                cerrarVentana(true);
            } else
                FarmaUtility.showMessage(this, "ERROR: Alguno de los campos se encuentra vacio", null);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //VariablesRecetario.vArrayEsteril = null;
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

    public void setFacade(FacadeRecetario facadeRecetario) {
        this.facadeRecetario = facadeRecetario;
    }
}
