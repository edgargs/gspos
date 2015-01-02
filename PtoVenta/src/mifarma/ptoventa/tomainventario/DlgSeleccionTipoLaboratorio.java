package mifarma.ptoventa.tomainventario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgSeleccionTipoLaboratorio extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgSeleccionTipoLaboratorio.class);

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JRadioButton rbtTodos = new JRadioButton();
    private JRadioButton rbtFarma = new JRadioButton();
    private JRadioButton rbtNatural2 = new JRadioButton();
    private JRadioButton rbtNoFarma = new JRadioButton();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JRadioButton rbtNatural3 = new JRadioButton();
    private JRadioButton rbtNatural = new JRadioButton();

    public DlgSeleccionTipoLaboratorio() {
        this(null, "", false);
    }

    public DlgSeleccionTipoLaboratorio(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(285, 161));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Seleccion tipo laboratorio");
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                this_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelWhite1.setLayout(null);
        jPanelHeader1.setBounds(new Rectangle(5, 10, 265, 95));
        jPanelHeader1.setBackground(Color.white);
        jPanelHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jLabelFunction1.setBounds(new Rectangle(80, 110, 95, 20));
        jLabelFunction1.setText("[ F11 ] Aceptar");
        rbtTodos.setText("TODOS");
        rbtTodos.setBounds(new Rectangle(15, 5, 95, 25));
        rbtTodos.setBackground(Color.white);
        rbtTodos.setFont(new Font("SansSerif", 1, 14));
        rbtTodos.setForeground(new Color(255, 130, 14));
        rbtTodos.setFocusPainted(false);
        rbtTodos.setRequestFocusEnabled(false);
        rbtTodos.setFocusable(false);
        rbtTodos.setSelected(true);
        rbtTodos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtTodos_keyPressed(e);
            }
        });
        rbtFarma.setText("FARMA");
        rbtFarma.setBounds(new Rectangle(15, 35, 95, 25));
        rbtFarma.setBackground(Color.white);
        rbtFarma.setFont(new Font("SansSerif", 1, 14));
        rbtFarma.setForeground(new Color(255, 130, 14));
        rbtFarma.setFocusPainted(false);
        rbtFarma.setRequestFocusEnabled(false);
        rbtFarma.setFocusable(false);
        rbtFarma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtFarma_keyPressed(e);
            }
        });
        rbtNatural2.setText("NATURAL");
        rbtNatural2.setBounds(new Rectangle(-125, -50, 95, 25));
        rbtNatural2.setBackground(Color.white);
        rbtNatural2.setFont(new Font("SansSerif", 1, 14));
        rbtNatural2.setForeground(new Color(43, 141, 39));
        rbtNatural2.setFocusPainted(false);
        rbtNatural2.setRequestFocusEnabled(false);
        rbtNatural2.setFocusable(false);
        rbtNatural2.setSelected(true);
        rbtNoFarma.setText("NO FARMA");
        rbtNoFarma.setBounds(new Rectangle(15, 65, 125, 25));
        rbtNoFarma.setBackground(Color.white);
        rbtNoFarma.setFont(new Font("SansSerif", 1, 14));
        rbtNoFarma.setForeground(new Color(255, 130, 14));
        rbtNoFarma.setFocusPainted(false);
        rbtNoFarma.setRequestFocusEnabled(false);
        rbtNoFarma.setFocusable(false);
        rbtNoFarma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtNoFarma_keyPressed(e);
            }
        });
        jLabelFunction2.setBounds(new Rectangle(180, 110, 90, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        rbtNatural3.setText("NATURAL");
        rbtNatural3.setBounds(new Rectangle(-125, -50, 95, 25));
        rbtNatural3.setBackground(Color.white);
        rbtNatural3.setFont(new Font("SansSerif", 1, 14));
        rbtNatural3.setForeground(new Color(43, 141, 39));
        rbtNatural3.setFocusPainted(false);
        rbtNatural3.setRequestFocusEnabled(false);
        rbtNatural3.setFocusable(false);
        rbtNatural3.setSelected(true);
        rbtNatural.setText("NATURAL");
        rbtNatural.setBounds(new Rectangle(140, 40, 95, 25));
        rbtNatural.setBackground(Color.white);
        rbtNatural.setFont(new Font("SansSerif", 1, 14));
        rbtNatural.setForeground(new Color(43, 141, 39));
        rbtNatural.setFocusPainted(false);
        rbtNatural.setRequestFocusEnabled(false);
        rbtNatural.setFocusable(false);
        rbtNatural.setSelected(true);
        jLabelFunction1.add(rbtNatural2, null);
        jLabelFunction2.add(rbtNatural3, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jPanelHeader1, null);
        jPanelWhite1.add(rbtNatural, null);
        jPanelHeader1.add(rbtNoFarma, null);
        jPanelHeader1.add(rbtFarma, null);
        jPanelHeader1.add(rbtTodos, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
    };

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        rbtTodos.setSelected(true);
        rbtFarma.setSelected(false);
        rbtNoFarma.setSelected(false);
        VariablesTomaInv.vLaboratorio = ConstantsTomaInv.TIPO_TODOS;
        log.debug("Variable todos" + VariablesTomaInv.vLaboratorio);
    }

    private void cambiaSeleccionLaboratorioDown() {
        if (rbtTodos.isSelected()) {
            rbtTodos.setSelected(false);
            rbtFarma.setSelected(true);
            rbtNoFarma.setSelected(false);
            VariablesTomaInv.vLaboratorio = ConstantsTomaInv.TIPO_FARMA;
        } else if (rbtFarma.isSelected()) {
            rbtTodos.setSelected(false);
            rbtFarma.setSelected(false);
            rbtNoFarma.setSelected(true);
            VariablesTomaInv.vLaboratorio = ConstantsTomaInv.TIPO_NO_FARMA;
        } else {
            rbtTodos.setSelected(true);
            rbtFarma.setSelected(false);
            rbtNoFarma.setSelected(false);
            VariablesTomaInv.vLaboratorio = ConstantsTomaInv.TIPO_TODOS;
        }

    }

    private void cambiaSeleccionLaboratorioUp() {
        if (rbtTodos.isSelected()) {
            rbtTodos.setSelected(false);
            rbtFarma.setSelected(false);
            rbtNoFarma.setSelected(true);
            VariablesTomaInv.vLaboratorio = ConstantsTomaInv.TIPO_NO_FARMA;
        } else if (rbtFarma.isSelected()) {
            rbtTodos.setSelected(true);
            rbtFarma.setSelected(false);
            rbtNoFarma.setSelected(false);
            VariablesTomaInv.vLaboratorio = ConstantsTomaInv.TIPO_TODOS;
        } else {
            rbtTodos.setSelected(false);
            rbtFarma.setSelected(true);
            rbtNoFarma.setSelected(false);
            VariablesTomaInv.vLaboratorio = ConstantsTomaInv.TIPO_FARMA;
        }

    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            cambiaSeleccionLaboratorioDown();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            cambiaSeleccionLaboratorioUp();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            log.debug(VariablesTomaInv.vLaboratorio);
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void rbtTodos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void rbtFarma_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void rbtNoFarma_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_keyReleased(KeyEvent e) {

    }

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

}
