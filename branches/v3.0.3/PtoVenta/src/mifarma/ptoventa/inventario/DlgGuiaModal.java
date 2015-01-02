package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JTextField;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgGuiaModal extends JDialog {


    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgGuiaModal.class);

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout = new BorderLayout();

    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();

    private JTextFieldSanSerif jTextFieldSanSerif1 = new JTextFieldSanSerif();
    private JTextFieldSanSerif jTextFieldSanSerif2 = new JTextFieldSanSerif();
    private JTextFieldSanSerif jTextFieldSanSerif3 = new JTextFieldSanSerif();

    private ArrayList<JTextFieldSanSerif> grilla;

    public DlgGuiaModal() {
        this(null, " ", false);
    }

    public DlgGuiaModal(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initGrilla();
        FarmaVariables.vAceptar = false;
    }

    private void initGrilla() {

        grilla = new ArrayList<JTextFieldSanSerif>();

        int cantidad = 18;
        int alto = 15;
        int distancia = 5 - alto;

        for (int i = 0; i < cantidad; i++) {
            JTextFieldSanSerif auxJTextFieldSanSerif = new JTextFieldSanSerif();
            distancia += alto;
            auxJTextFieldSanSerif.setBounds(new Rectangle(5, distancia, 705, alto));
            auxJTextFieldSanSerif.setLengthText(100);
            auxJTextFieldSanSerif.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            //auxJTextFieldSanSerif.mm(jTextFieldSanSerif2);
            auxJTextFieldSanSerif.setFont(new Font("Courier New", 0, 11));
            auxJTextFieldSanSerif.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jTextFieldSanSerif1_keyPressed(e);
                }
            });
            jPanelTitle1.add(auxJTextFieldSanSerif, null);
            grilla.add(auxJTextFieldSanSerif);
        }
        jPanelTitle1.setSize(715, cantidad * alto + 10);
        //this.setPreferredSize(new Dimension(736, 18*20+100));
        this.setSize(new Dimension(736, cantidad * alto + 100));
        lblF11.setLocation(465, cantidad * alto + 30);
        lblEsc.setLocation(565, cantidad * alto + 30);
        this.repaint();
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(736, 311));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Descripcion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });


        lblF11.setBounds(new Rectangle(465, 255, 95, 20));
        lblF11.setRequestFocusEnabled(false);
        lblF11.setText("[F11] Aceptar");

        lblEsc.setBounds(new Rectangle(565, 255, 95, 20));
        lblEsc.setRequestFocusEnabled(false);
        lblEsc.setText("[ESC] Cerrar");


        jPanelTitle1.setBounds(new Rectangle(5, 10, 715, 235));
        jPanelTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jPanelTitle1.setBackground(SystemColor.window);
        jTextFieldSanSerif1.setBounds(new Rectangle(5, 5, 700, 20));
        jTextFieldSanSerif1.setLengthText(100);
        jTextFieldSanSerif1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextFieldSanSerif1.setFont(new Font("Courier New", 0, 11));
        jTextFieldSanSerif1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jTextFieldSanSerif1_keyPressed(e);
            }
        });
        jTextFieldSanSerif2.setBounds(new Rectangle(5, 25, 700, 20));
        jTextFieldSanSerif2.setLengthText(100);
        jTextFieldSanSerif2.setFont(new Font("Courier New", 0, 11));
        jTextFieldSanSerif2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextFieldSanSerif2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jTextFieldSanSerif1_keyPressed(e);
            }
        });
        jTextFieldSanSerif3.setBounds(new Rectangle(5, 45, 700, 20));
        jTextFieldSanSerif3.setLengthText(100);
        jTextFieldSanSerif3.setFont(new Font("Courier New", 0, 11));
        jTextFieldSanSerif3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextFieldSanSerif3.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jTextFieldSanSerif1_keyPressed(e);
            }
        });
        //jPanelTitle1.add(jTextFieldSanSerif1, null);
        //jPanelTitle1.add(jTextFieldSanSerif2, null);
        //jPanelTitle1.add(jTextFieldSanSerif3, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(lblF11, "");
        jContentPane.add(lblEsc, "");
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //FarmaUtility.moveFocus(txtDescripcion);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void jTextFieldSanSerif1_keyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            agregaLineas();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
            ((JTextField)e.getSource()).transferFocus();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            ((JTextField)e.getSource()).transferFocusBackward();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de salir? Se perderán los datos.")) {
                cerrarVentana(false);
            }
        }
    }

    private void agregaLineas() {

        StringBuilder texto = new StringBuilder();
        for (JTextFieldSanSerif jTextFieldSanSerif1 : grilla) {
            texto.append(jTextFieldSanSerif1.getText() + "\n");
        }

        VariablesInventario.vTexImpr = texto.toString();

        if (VariablesInventario.vTexImpr.trim().equals("")) {
            FarmaUtility.showMessage(this, "No ha ingresado el detalle de la guía.", null);
        } else {
            cerrarVentana(true);
        }
    }
}
