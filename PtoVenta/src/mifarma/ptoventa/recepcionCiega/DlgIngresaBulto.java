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

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresaBulto.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      05.06.2015   Creacion<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgIngresaBulto extends JDialog {
    private Frame myParentFrame;

    FarmaTableModel tableModelProductosConteo;

    private static final Logger log = LoggerFactory.getLogger(DlgIngresaBulto.class);

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader jPanelTitleBuscar = new JPanelHeader();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabelFunction lblEliminar = new JLabelFunction();
    private JLabelFunction lblGuardarConteo = new JLabelFunction();
    
    long tmpT1,tmpT2;
    boolean isLectoraLazer = false;
    boolean isEnter = false;
    double vTiempoMaquina = 400; // MILISEGUNDOS

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresaBulto() {
        this(null, "", false);
    }

    public DlgIngresaBulto(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(362, 75));
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
        jPanelTitleBuscar.setBounds(new Rectangle(5, 5, 350, 40));
        btnBuscar.setText("Ingresar C\u00f3digo Bulto:");
        btnBuscar.setMnemonic('i');
        btnBuscar.setBounds(new Rectangle(15, 10, 135, 20));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });

        txtBuscar.setBounds(new Rectangle(165, 10, 170, 20));
        txtBuscar.setLengthText(15);
        //**--
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }
        });
        //---
        txtBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //|txtBuscar_actionPerformed(e);
            }
        });
        lblEliminar.setBounds(new Rectangle(280, 385, 95, 20));
        lblEliminar.setText("[ F5 ] Eliminar");
        lblEliminar.setVisible(false);
        lblGuardarConteo.setBounds(new Rectangle(145, 385, 130, 20));
        lblGuardarConteo.setText("[ F1 ] Guardar Conteo");
        lblGuardarConteo.setVisible(false);


        jPanelTitleBuscar.add(txtBuscar, null);
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
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
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

    private void txtBuscar_keyPressed(KeyEvent e) {
        isEnter = false;
        isLectoraLazer = false;

        if ((txtBuscar.getText().length() == 0))
                tmpT1 = System.currentTimeMillis();
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        
            isLectoraLazer = false;
            tmpT2 = System.currentTimeMillis();
            log.info("Tiem 2 " + (tmpT2));
            log.info("Tiem 1 " + (tmpT1));
            log.info("Tiempo de ingreso y ENTER " + (tmpT2 - tmpT1));
            if ((tmpT2 - tmpT1) <= vTiempoMaquina && txtBuscar.getText().length() > 0) {
                isLectoraLazer = true;
                tmpT1 = 0;
            } else {
                isLectoraLazer = false;
                tmpT1 = 0;
            }
            isEnter = true; 
            setearVariablesBulto();
            
        } else {
            isEnter = false;
            chkKeyPressed(e);
        }        
    }
    
    private void setearVariablesBulto() {
        String codBulto = txtBuscar.getText().trim();
        
        if (isLectoraLazer) {
            VariablesRecepCiega.vIndLectoraBulto = "S";
        } else {
            VariablesRecepCiega.vIndLectoraBulto = "N";
        }        
        
        if (UtilityRecepCiega.isBultoExists(VariablesRecepCiega.vNumIngreso, 
                                            codBulto)) {
            UtilityRecepCiega.getCorrBulto(codBulto,
                                           VariablesRecepCiega.vNumIngreso);
            cerrarVentana(true);
        } else {
            if (UtilityInventario.obtenerParametroString(ConstantsRecepCiega.COD_ALLOW_BULTO_NOTEXISTS).equals("S")) {
                DlgReIngresoBulto dlgReIngresoBulto = new DlgReIngresoBulto(myParentFrame, "", true);
                dlgReIngresoBulto.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    if (VariablesRecepCiega.codigoBultoAuxPucha.equals(codBulto)) {
                        UtilityRecepCiega.getCorrBulto(codBulto,
                                                       VariablesRecepCiega.vNumIngreso);
                        cerrarVentana(true);
                    } else {
                        txtBuscar.setText("");
                        FarmaUtility.showMessage(this, 
                                                 "El código de bulto no existe como recepcionado.\nNi coincide con los códigos verificados\n" + 
                                                 "No se aceptará dicho bulto", 
                                                 txtBuscar);
                        cerrarVentana(false);
                    }
                }
            } else {
                txtBuscar.setText("");
                FarmaUtility.showMessage(this, 
                                         "El código de bulto no existe como recepcionado.\nNo se aceptará dicho bulto", 
                                         txtBuscar);
                cerrarVentana(false);
            }
        }
        
    }

}
