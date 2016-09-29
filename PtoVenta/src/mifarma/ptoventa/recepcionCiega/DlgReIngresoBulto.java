package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgReIngresoBulto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      09.06.2015   Creacion<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgReIngresoBulto extends JDialog {
    private Frame myParentFrame;

    private static final Logger log = LoggerFactory.getLogger(DlgReIngresoBulto.class);

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader jPanelTitleBuscar = new JPanelHeader();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtCodBulto = new JTextFieldSanSerif();
    private JLabelFunction lblEliminar = new JLabelFunction();
    private JLabelFunction lblGuardarConteo = new JLabelFunction();
    private JButtonLabel lblReingresar = new JButtonLabel();
    private JTextFieldSanSerif txtCodBulto2 = new JTextFieldSanSerif();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgReIngresoBulto() {
        this(null, "", false);
    }

    public DlgReIngresoBulto(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(362, 112));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Lectura de c\u00f3digo de Bulto");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelTitleBuscar.setBounds(new Rectangle(5, 5, 350, 75));
        btnBuscar.setText("Ingresar C\u00f3digo Bulto:");
        btnBuscar.setMnemonic('i');
        btnBuscar.setBounds(new Rectangle(15, 10, 135, 20));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });

        txtCodBulto.setBounds(new Rectangle(165, 10, 170, 20));
        txtCodBulto.setLengthText(15);
        //**--
        //---
        txtCodBulto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCodBulto_keyPressed(e);
                }
            });
        lblEliminar.setBounds(new Rectangle(280, 385, 95, 20));
        lblEliminar.setText("[ F5 ] Eliminar");
        lblEliminar.setVisible(false);
        lblGuardarConteo.setBounds(new Rectangle(145, 385, 130, 20));
        lblGuardarConteo.setText("[ F1 ] Guardar Conteo");
        lblGuardarConteo.setVisible(false);


        lblReingresar.setText("Reingresar C\u00f3digo Bulto:");
        lblReingresar.setBounds(new Rectangle(15, 45, 150, 15));
        txtCodBulto2.setBounds(new Rectangle(165, 40, 170, 20));
        txtCodBulto2.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCodBulto2_keyPressed(e);
                }
            });
        jPanelTitleBuscar.add(txtCodBulto2, null);
        jPanelTitleBuscar.add(lblReingresar, null);
        jPanelTitleBuscar.add(txtCodBulto, null);
        jPanelTitleBuscar.add(btnBuscar, null);
        jContentPane.add(lblGuardarConteo, null);
        jContentPane.add(lblEliminar, null);

        jContentPane.add(jPanelTitleBuscar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodBulto);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCodBulto);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void txtCodBulto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String codBulto = txtCodBulto.getText().trim();
            if (codBulto.equals("")) {
                FarmaUtility.showMessage(this, 
                                         "Ingrese código de bulto", 
                                         txtCodBulto);
            } else {
                FarmaUtility.moveFocus(txtCodBulto2);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtCodBulto2_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String codBulto = txtCodBulto.getText().trim();
            String codBulto2 = txtCodBulto2.getText().trim();
            if (codBulto2.equals("")) {
                FarmaUtility.showMessage(this, 
                                         "Reingrese código de bulto", 
                                         txtCodBulto2);
            } else {
                if (codBulto.equals(codBulto2)) {
                    VariablesRecepCiega.codigoBultoAuxPucha = codBulto;
                    cerrarVentana(true);
                } else {
                    txtCodBulto.setText("");
                    txtCodBulto2.setText("");
                    FarmaUtility.showMessage(this, 
                                             "Los códigos ingresados no coinciden, vuelva a ingresarlos",
                                             txtCodBulto);
                }
            }
        } else {
            chkKeyPressed(e);
        }
    }
}
