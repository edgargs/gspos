package mifarma.ptoventa.administracion.mantenimiento;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JNumericField;
import com.gs.mifarma.componentes.JPanelTitle;
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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import static mifarma.common.FarmaUtility.moveFocus;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.cajas.DlgListaUsuariosCaja;
import mifarma.ptoventa.administracion.cajas.reference.VariablesCajas;
import mifarma.ptoventa.administracion.mantenimiento.reference.DBMantenimiento;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2016 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosPresupuestoVentasVendedor.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      18.07.2016   Creación<br>
 * <br>
 *
 */
public class DlgDatosPresupuestoVentasVendedor extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDatosPresupuestoVentasVendedor.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlDatosCaja = new JPanelTitle();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JTextFieldSanSerif txtDesUsu = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSecUsu = new JTextFieldSanSerif();
    private JButtonLabel btnCajero = new JButtonLabel();
    private String pCodProg;
    private JNumericField txtVolumen = new JNumericField(10);
    private JNumericField txtLLEE = new JNumericField(10);
    private JButtonLabel btnVolumen = new JButtonLabel();
    private JButtonLabel btnLLEE = new JButtonLabel();
    private String pSecUsu = null;
    private boolean isModify = false;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDatosPresupuestoVentasVendedor() {
        this(null, "", false);
    }

    public DlgDatosPresupuestoVentasVendedor(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(487, 170));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Datos por Vendedor");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlDatosCaja.setBounds(new Rectangle(10, 10, 460, 95));
        lblSalir.setBounds(new Rectangle(370, 115, 100, 20));
        lblSalir.setText("[ESC] Cerrar");
        lblAceptar.setBounds(new Rectangle(260, 115, 100, 20));
        lblAceptar.setText("[F11] Aceptar");
        txtDesUsu.setBounds(new Rectangle(200, 15, 225, 20));
        txtDesUsu.setFocusable(false);
        txtDesUsu.setEditable(false);
        txtSecUsu.setBounds(new Rectangle(120, 15, 75, 20));
        txtSecUsu.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSecUsu_keyPressed(e);
            }
        });
        btnCajero.setText("Vendedor :");
        btnCajero.setBounds(new Rectangle(10, 15, 105, 20));
        btnCajero.setMnemonic('V');
        btnCajero.setActionCommand("Vendedor :");
        btnCajero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        txtVolumen.setBounds(new Rectangle(120, 40, 75, 20));
        txtVolumen.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtVolumen_keyPressed(e);
                }
            });
        txtLLEE.setBounds(new Rectangle(120, 65, 75, 20));
        txtLLEE.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtLLEE_keyPressed(e);
                }
            });
        btnVolumen.setText("Volumen :");
        btnVolumen.setBounds(new Rectangle(10, 45, 75, 15));
        btnVolumen.setMnemonic('o');
        btnVolumen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnVolumen_actionPerformed(e);
                }
            });
        btnLLEE.setText("LLEE :");
        btnLLEE.setBounds(new Rectangle(10, 70, 75, 15));
        jContentPane.add(lblAceptar, null);
        jContentPane.add(lblSalir, null);
        pnlDatosCaja.add(btnLLEE, null);
        pnlDatosCaja.add(btnVolumen, null);
        pnlDatosCaja.add(txtLLEE, null);
        pnlDatosCaja.add(txtVolumen, null);
        pnlDatosCaja.add(btnCajero, null);
        pnlDatosCaja.add(txtSecUsu, null);
        pnlDatosCaja.add(txtDesUsu, null);
        jContentPane.add(pnlDatosCaja, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;

    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        txtDesUsu.setEditable(false);
        if(isModify){
            FarmaUtility.moveFocus(txtVolumen);
        }else{
            FarmaUtility.moveFocus(txtSecUsu);
        }
    }

    private void txtSecUsu_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCodigo();
        }
        chkKeyPressed(e);
    }
    
    private void btnVolumen_actionPerformed(ActionEvent e) {
        moveFocus(txtVolumen);
    }
    
    private void txtVolumen_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moveFocus(txtLLEE);
        }
        chkKeyPressed(e);
    }
    
    private void txtLLEE_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(isModify){
                moveFocus(txtVolumen);
            }else{
                moveFocus(txtSecUsu);
            }
        }
        chkKeyPressed(e);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSecUsu);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados()){
                cerrarVentana(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean datosValidados() {

        if (txtSecUsu.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe ingresar el usuario", txtSecUsu);
            return false;
        }

        if (txtSecUsu.getText().trim().length() > 0 && txtDesUsu.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe validar el usuario", txtSecUsu);
            return false;
        }

        return true;
    }

    private void validarCodigo() {
        
        if (txtSecUsu.getText().trim().length() == 0) {
            DlgListaUsuariosCaja dlgListaUsuariosCaja = new DlgListaUsuariosCaja(myParentFrame, "", true);
            dlgListaUsuariosCaja.setCodProg(pCodProg);
            dlgListaUsuariosCaja.setVisible(true);

            if (FarmaVariables.vAceptar) {
                txtSecUsu.setText(VariablesCajas.vSecUsuAsig);
                txtDesUsu.setText(VariablesCajas.vDesUsuAsig);
                FarmaVariables.vAceptar = false;
            }
        } else {
            // se ha ingresado un codigo
            ArrayList listaUser = new ArrayList();
            ArrayList tmpElement = new ArrayList();

            try {
                listaUser = DBMantenimiento.getDatosVendedorArray(txtSecUsu.getText().trim());
            } catch (SQLException e) {
                log.error("", e);
                listaUser = new ArrayList();
                FarmaUtility.showMessage(this, "Error al obtener usuarios. \n " + e.getMessage(), txtSecUsu);
            }

            if (listaUser.size() == 0) {
                txtSecUsu.setText("");
                FarmaUtility.showMessage(this, "Usuario no encontrado o no disponible", txtSecUsu);
            }
            if (listaUser.size() > 1) {
                FarmaUtility.showMessage(this, "El criterio de búsqueda ha devuelto más de un resultado", txtSecUsu);
            }
            if (listaUser.size() == 1) {

                tmpElement = (ArrayList)listaUser.get(0);

                txtSecUsu.setText(tmpElement.get(0).toString().trim());
                txtDesUsu.setText(tmpElement.get(1).toString().trim());
                moveFocus(txtVolumen);
            }
        }

    }

    void setCodProg(String pCodProg) {
        this.pCodProg = pCodProg;
    }

    void setSecUsu(String pSecUsu, String pNomUsu) {
        this.pSecUsu = pSecUsu;
        txtSecUsu.setText(pSecUsu);
        txtDesUsu.setText(pNomUsu);
        this.isModify = true;
        txtSecUsu.setEditable(false);        
    }

    ArrayList<String> getRegistro() {
        ArrayList<String> registro = new ArrayList<>();
        registro.add(txtSecUsu.getText().trim());
        registro.add(txtDesUsu.getText().trim());
        registro.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtVolumen.getText().trim())));
        registro.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtLLEE.getText().trim())));
        registro.add(" ");
        return registro;
    }

    void setPpto(String pVolumen, String pLLEE) {
        txtVolumen.setText((int)FarmaUtility.getDecimalNumber(pVolumen)+"");
        txtLLEE.setText((int)FarmaUtility.getDecimalNumber(pLLEE)+"");
    }
}
